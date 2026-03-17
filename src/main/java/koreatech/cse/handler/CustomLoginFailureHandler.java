package koreatech.cse.handler;

import koreatech.cse.service.LoginAttemptService;
import koreatech.cse.service.AuditService;
import koreatech.cse.util.RequestIpUtil;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Inject
    private LoginAttemptService loginAttemptService;
    @Inject
    private AuditService auditService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("j_username");
        String ip = RequestIpUtil.getClientIp(request);
        loginAttemptService.loginFailed(username, ip);
        auditService.logEvent("LOGIN_FAILURE", username, ip, "authentication failed");
        String targetUrl = determineTargetUrl(request);
        if (response.isCommitted()) {
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request) {
        return "/signin?msg=fail";
    }
}