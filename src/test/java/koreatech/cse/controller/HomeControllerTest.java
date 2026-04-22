package koreatech.cse.controller;

import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.Role;
import koreatech.cse.domain.dto.SignupRequest;
import koreatech.cse.domain.constant.SigninMessage;
import koreatech.cse.domain.constant.SignupResult;
import koreatech.cse.service.AuthenticationAttemptService;
import koreatech.cse.service.AuthenticationAuditService;
import koreatech.cse.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.bind.support.SimpleSessionStatus;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private AuthenticationAttemptService authenticationAttemptService;
    @Mock
    private AuthenticationAuditService authenticationAuditService;

    private HomeController homeController;

    @Before
    public void setUp() {
        homeController = new HomeController();
        ReflectionTestUtils.setField(homeController, "userService", userService);
        ReflectionTestUtils.setField(homeController, "authenticationAttemptService", authenticationAttemptService);
        ReflectionTestUtils.setField(homeController, "authenticationAuditService", authenticationAuditService);
    }

    @Test
    public void signupRedirectsUsingSafeFailureCode() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("127.0.0.1");

        when(authenticationAttemptService.checkSignupAllowed(request, null)).thenReturn(true);
        when(userService.signup(org.mockito.Mockito.any(User.class), org.mockito.Mockito.eq(Role.student),
                org.mockito.Mockito.eq("secret")))
                .thenReturn(SignupResult.IDENTITY_MISMATCH);

        String viewName = homeController.signup(new SignupRequest(), Role.student, "secret", new SimpleSessionStatus(), request);

        assertEquals("redirect:/signin?msg=fail", viewName);
        verify(authenticationAttemptService).recordSignupResult(request, null, SignupResult.IDENTITY_MISMATCH);
        verify(authenticationAuditService).log(koreatech.cse.domain.constant.SecurityEventType.SIGNUP_FAILED, null,
                request, SignupResult.IDENTITY_MISMATCH.name());
    }

    @Test
    public void signupRedirectsWhenRateLimited() {
        SignupRequest signupReq = new SignupRequest();
        signupReq.setNumber("2024001");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("127.0.0.1");

        when(authenticationAttemptService.checkSignupAllowed(request, "2024001")).thenReturn(false);

        String viewName = homeController.signup(signupReq, Role.student, "secret", new SimpleSessionStatus(), request);

        assertEquals("redirect:/signin?msg=too_many_attempts", viewName);
        verify(userService, never()).signup(org.mockito.Mockito.any(User.class), org.mockito.Mockito.any(Role.class),
                org.mockito.Mockito.anyString());
        verify(authenticationAttemptService, never()).recordSignupResult(org.mockito.Mockito.any(javax.servlet.http.HttpServletRequest.class),
                org.mockito.Mockito.anyString(), org.mockito.Mockito.any(SignupResult.class));
    }

    @Test
    public void logoutRedirectsToSpringSecuritySignout() {
        String viewName = homeController.logout(null);

        assertEquals("redirect:/signout", viewName);
    }

    @Test
    public void signinMapsAllSignupResultsToMessageKeys() {
        for (SignupResult value : SignupResult.values()) {
            ExtendedModelMap model = new ExtendedModelMap();

            String viewName = homeController.signin(model, value.getCode());

            assertEquals("signin", viewName);
            assertEquals(SigninMessage.fromCode(value.getCode()).getMessageKey(), model.get("msgKey"));
        }
    }

    @Test
    public void signinMapsGenericLoginFailureToAuthMessageKey() {
        ExtendedModelMap model = new ExtendedModelMap();

        String viewName = homeController.signin(model, "fail");

        assertEquals("signin", viewName);
        assertEquals("auth.failure.generic", model.get("msgKey"));
    }

    @Test
    public void signinMapsTooManyAttemptsToBlockedMessageKey() {
        ExtendedModelMap model = new ExtendedModelMap();

        String viewName = homeController.signin(model, SignupResult.TOO_MANY_ATTEMPTS.getCode());

        assertEquals("signin", viewName);
        assertEquals("auth.failure.blocked", model.get("msgKey"));
    }

    @Test
    public void signinIgnoresInvalidMessageCodes() {
        ExtendedModelMap model = new ExtendedModelMap();

        String viewName = homeController.signin(model, "common.signin.success");

        assertEquals("signin", viewName);
        assertEquals(null, model.get("msgKey"));
    }
}
