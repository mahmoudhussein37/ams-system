package koreatech.cse.handler;

import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.AuthenticationFailureReason;
import koreatech.cse.domain.constant.SecurityEventType;
import koreatech.cse.service.AuthenticationAttemptService;
import koreatech.cse.service.AuthenticationAuditService;
import koreatech.cse.service.RoleRedirectStrategy;
import koreatech.cse.repository.UserMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomLoginHandlersTest {

    @Mock
    private AuthenticationAttemptService authenticationAttemptService;
    @Mock
    private AuthenticationAuditService authenticationAuditService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private RoleRedirectStrategy roleRedirectStrategy;
    @Mock
    private Authentication authentication;

    private CustomLoginFailureHandler customLoginFailureHandler;
    private CustomLoginHandler customLoginHandler;

    @Before
    public void setUp() {
        customLoginFailureHandler = new CustomLoginFailureHandler();
        customLoginHandler = new CustomLoginHandler();
        ReflectionTestUtils.setField(customLoginFailureHandler, "authenticationAttemptService", authenticationAttemptService);
        ReflectionTestUtils.setField(customLoginFailureHandler, "authenticationAuditService", authenticationAuditService);
        ReflectionTestUtils.setField(customLoginFailureHandler, "userMapper", userMapper);
        ReflectionTestUtils.setField(customLoginHandler, "authenticationAttemptService", authenticationAttemptService);
        ReflectionTestUtils.setField(customLoginHandler, "authenticationAuditService", authenticationAuditService);
        ReflectionTestUtils.setField(customLoginHandler, "roleRedirectStrategy", roleRedirectStrategy);
    }

    @Test
    public void failureHandlerRecordsLoginFailure() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("j_username", "student@example.com");
        request.setRemoteAddr("10.0.0.1");

        customLoginFailureHandler.onAuthenticationFailure(request, new MockHttpServletResponse(),
                new org.springframework.security.core.AuthenticationException("fail") {});

        verify(authenticationAttemptService).recordLoginFailure(request);
        verify(authenticationAuditService).log(SecurityEventType.LOGIN_FAILED, "student@example.com", request,
                AuthenticationFailureReason.AUTHENTICATION_FAILURE.name());
    }

    @Test
    public void failureHandlerRedirectsPendingAccountToAssistanceMessage() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("j_username", "student@example.com");
        request.setRemoteAddr("10.0.0.1");
        MockHttpServletResponse response = new MockHttpServletResponse();

        User user = new User();
        user.setConfirm(false);
        when(userMapper.findByUsername("student@example.com")).thenReturn(user);

        customLoginFailureHandler.onAuthenticationFailure(request, response, new DisabledException("disabled"));

        assertEquals("/signin?msg=assist", response.getRedirectedUrl());
    }

    @Test
    public void successHandlerResetsLoginAttemptsAndRedirectsStudent() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("10.0.0.1");
        request.setSession(new MockHttpSession());
        MockHttpServletResponse response = new MockHttpServletResponse();
        when(authentication.getName()).thenReturn("student@example.com");
        when(roleRedirectStrategy.resolveTargetUrl(authentication)).thenReturn("/student/register/basic");

        customLoginHandler.onAuthenticationSuccess(request, response, authentication);

        verify(authenticationAttemptService).recordLoginSuccess(request, "student@example.com");
        verify(authenticationAuditService).log(SecurityEventType.LOGIN_SUCCESS, "student@example.com", request,
                "AUTHENTICATED");
        verify(roleRedirectStrategy).resolveTargetUrl(authentication);
        assertEquals("/student/register/basic", response.getRedirectedUrl());
    }

    @Test
    public void successHandlerPrioritizesAdminAndPreservesLanguage() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("10.0.0.1");
        request.setCookies(new javax.servlet.http.Cookie("lang", "ar"));
        request.setSession(new MockHttpSession());
        MockHttpServletResponse response = new MockHttpServletResponse();
        when(authentication.getName()).thenReturn("admin@example.com");
        when(roleRedirectStrategy.resolveTargetUrl(authentication)).thenReturn("/admin/studentManagement/studentInformation");

        customLoginHandler.onAuthenticationSuccess(request, response, authentication);

        assertEquals("/admin/studentManagement/studentInformation?lang=ar", response.getRedirectedUrl());
    }

    @Test
    public void successHandlerRedirectsProfessorToProfessorEntryPoint() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("10.0.0.1");
        request.setSession(new MockHttpSession());
        MockHttpServletResponse response = new MockHttpServletResponse();
        when(authentication.getName()).thenReturn("prof@example.com");
        when(roleRedirectStrategy.resolveTargetUrl(authentication)).thenReturn("/professor/studentGuidance/studentLookup");

        customLoginHandler.onAuthenticationSuccess(request, response, authentication);

        assertEquals("/professor/studentGuidance/studentLookup", response.getRedirectedUrl());
    }

    @Test
    public void failureReasonMapsDisabledUnconfirmedUserToNotActivated() {
        User user = new User();
        user.setConfirm(false);
        when(userMapper.findByUsername("student@example.com")).thenReturn(user);

        AuthenticationFailureReason reason = customLoginFailureHandler.resolveFailureReason(
                new DisabledException("disabled"), "student@example.com");

        assertEquals(AuthenticationFailureReason.NOT_ACTIVATED, reason);
    }

    @Test
    public void failureReasonMapsBadCredentialsSafely() {
        AuthenticationFailureReason reason = customLoginFailureHandler.resolveFailureReason(
                new BadCredentialsException("bad"), "student@example.com");

        assertEquals(AuthenticationFailureReason.BAD_CREDENTIALS, reason);
    }
}
