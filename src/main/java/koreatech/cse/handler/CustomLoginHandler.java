package koreatech.cse.handler;

import koreatech.cse.domain.User;
import koreatech.cse.domain.constant.Role;
import koreatech.cse.repository.AuthorityMapper;
import koreatech.cse.repository.UserMapper;
import koreatech.cse.service.AuditService;
import koreatech.cse.service.LoginAttemptService;
import koreatech.cse.util.RequestIpUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CustomLoginHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Inject
    private AuthorityMapper authorityMapper;
    @Inject
    private UserMapper userMapper;
    @Inject
    private LoginAttemptService loginAttemptService;
    @Inject
    private AuditService auditService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication) throws IOException {
        String ip = RequestIpUtil.getClientIp(request);
        loginAttemptService.loginSucceeded(authentication.getName(), ip);
        auditService.logEvent("LOGIN_SUCCESS", authentication.getName(), ip, "authentication succeeded");
        User user = User.current();

        handle(request, response, user);
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request,
            HttpServletResponse response, User user) throws IOException {
        String targetUrl = determineTargetUrl(request, user);

        if (response.isCommitted()) {
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /**
     * Builds the target URL according to the logic defined in the main class
     * Javadoc.
     */
    protected String determineTargetUrl(HttpServletRequest request, User user) {
        String lang = null;
        if (request.getCookies() != null) {
            for (javax.servlet.http.Cookie c : request.getCookies()) {
                if ("lang".equals(c.getName())) {
                    lang = c.getValue();
                    break;
                }
            }
        }
        if (lang != null && !lang.isEmpty()) {
            return "/?lang=" + lang;
        }
        return "/";
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        session.setMaxInactiveInterval(3600);
    }
}