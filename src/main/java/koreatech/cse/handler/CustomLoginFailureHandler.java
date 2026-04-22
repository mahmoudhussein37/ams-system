package koreatech.cse.handler;

import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.AccountState;
import koreatech.cse.domain.constant.AuthenticationFailureReason;
import koreatech.cse.domain.constant.SecurityEventType;
import koreatech.cse.domain.constant.SigninMessage;
import koreatech.cse.repository.UserMapper;
import koreatech.cse.service.AuthenticationAttemptService;
import koreatech.cse.service.AuthenticationAuditService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
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
import java.util.Objects;

@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Inject
    private AuthenticationAttemptService authenticationAttemptService;
    @Inject
    private AuthenticationAuditService authenticationAuditService;
    @Inject
    private UserMapper userMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = normalizeUsername(request.getParameter("j_username"));
        AuthenticationFailureReason failureReason = resolveFailureReason(exception, username);

        authenticationAuditService.log(SecurityEventType.LOGIN_FAILED, username, request, failureReason.name());
        authenticationAttemptService.recordLoginFailure(request);
        String targetUrl = determineTargetUrl(request, failureReason);
        if (response.isCommitted()) {
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, AuthenticationFailureReason failureReason) {
        Objects.requireNonNull(request, "request must not be null");

        if (authenticationAttemptService.isLoginBlocked(request)) {
            return "/signin?msg=" + SigninMessage.TOO_MANY_ATTEMPTS.getCode();
        }

        if (failureReason == AuthenticationFailureReason.NOT_ACTIVATED) {
            return "/signin?msg=" + SigninMessage.LOGIN_FAILED_ASSISTANCE.getCode();
        }

        return "/signin?msg=" + SigninMessage.LOGIN_FAILED_GENERIC.getCode();
    }

    AuthenticationFailureReason resolveFailureReason(AuthenticationException exception, String username) {
        if (exception instanceof DisabledException || exception instanceof LockedException) {
            User storedUser = username == null ? null : userMapper.findByUsername(username);
            if (storedUser != null) {
                AccountState accountState = storedUser.getAccountState();
                if (accountState == AccountState.PENDING) {
                    return AuthenticationFailureReason.NOT_ACTIVATED;
                }
                if (accountState == AccountState.DISABLED) {
                    return AuthenticationFailureReason.ACCOUNT_DISABLED;
                }
            }
            return AuthenticationFailureReason.ACCOUNT_DISABLED;
        }
        if (exception instanceof BadCredentialsException) {
            return AuthenticationFailureReason.BAD_CREDENTIALS;
        }
        return AuthenticationFailureReason.AUTHENTICATION_FAILURE;
    }

    private String normalizeUsername(String username) {
        if (username == null) {
            return null;
        }
        String trimmed = username.trim();
        return trimmed.isEmpty() ? null : trimmed.toLowerCase();
    }
}
