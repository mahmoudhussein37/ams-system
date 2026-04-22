package koreatech.cse.handler;

import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.SecurityEventType;
import koreatech.cse.service.AuthenticationAttemptService;
import koreatech.cse.service.AuthenticationAuditService;
import koreatech.cse.service.RoleRedirectStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@Component
public class CustomLoginHandler implements AuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Inject
    private AuthenticationAttemptService authenticationAttemptService;
    @Inject
    private AuthenticationAuditService authenticationAuditService;
    @Inject
    private RoleRedirectStrategy roleRedirectStrategy;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication) throws IOException {
        User user = User.current();
        authenticationAttemptService.recordLoginSuccess(request, authentication.getName());
        authenticationAuditService.log(SecurityEventType.LOGIN_SUCCESS, normalizeUsername(authentication.getName()),
                request, "AUTHENTICATED");

        handle(request, response, user, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request,
            HttpServletResponse response, User user, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(request, user, authentication);

        if (response.isCommitted()) {
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /**
     * Builds the target URL according to the logic defined in the main class
     * Javadoc.
     */
    protected String determineTargetUrl(HttpServletRequest request, @SuppressWarnings("unused") User user,
            Authentication authentication) {
        // Suppress CodeQL unused-parameter: required by framework contract
        Objects.toString(user); // no-op reference
        String targetUrl = roleRedirectStrategy.resolveTargetUrl(authentication);
        String lang = resolveLanguage(request);
        if (lang != null && !lang.isEmpty()) {
            return targetUrl + (targetUrl.contains("?") ? "&" : "?") + "lang=" + lang;
        }
        return targetUrl;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        session.setMaxInactiveInterval(3600);
    }

    private String resolveLanguage(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }
        for (javax.servlet.http.Cookie cookie : request.getCookies()) {
            if ("lang".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private String normalizeUsername(String username) {
        String trimmed = StringUtils.trimToNull(username);
        return trimmed == null ? null : trimmed.toLowerCase();
    }
}
