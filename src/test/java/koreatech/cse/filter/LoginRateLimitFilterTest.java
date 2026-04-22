package koreatech.cse.filter;

import koreatech.cse.service.AuthenticationAttemptService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginRateLimitFilterTest {

    @Mock
    private AuthenticationAttemptService authenticationAttemptService;

    private LoginRateLimitFilter loginRateLimitFilter;

    @Before
    public void setUp() {
        loginRateLimitFilter = new LoginRateLimitFilter();
        ReflectionTestUtils.setField(loginRateLimitFilter, "authenticationAttemptService", authenticationAttemptService);
    }

    @Test
    public void blockedLoginRedirectsBeforeAuthentication() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/j_spring_security_check");
        request.setParameter("j_username", "student@example.com");
        request.setRemoteAddr("10.0.0.1");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        when(authenticationAttemptService.isLoginBlocked(request)).thenReturn(true);

        loginRateLimitFilter.doFilter(request, response, filterChain);

        assertEquals("/signin?msg=too_many_attempts", response.getRedirectedUrl());
    }

    @Test
    public void nonBlockedLoginContinuesFilterChain() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/j_spring_security_check");
        request.setParameter("j_username", "student@example.com");
        request.setRemoteAddr("10.0.0.1");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        when(authenticationAttemptService.isLoginBlocked(request)).thenReturn(false);

        loginRateLimitFilter.doFilter(request, response, filterChain);

        assertEquals(200, response.getStatus());
        verify(authenticationAttemptService).isLoginBlocked(request);
    }

    @Test
    public void nonLoginRequestSkipsRateLimitCheck() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/signin");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        loginRateLimitFilter.doFilter(request, response, filterChain);

        verify(authenticationAttemptService, never()).isLoginBlocked(org.mockito.Mockito.any(javax.servlet.http.HttpServletRequest.class));
    }
}
