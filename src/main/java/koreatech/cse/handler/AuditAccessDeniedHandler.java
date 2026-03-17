package koreatech.cse.handler;

import koreatech.cse.service.AuditService;
import koreatech.cse.util.RequestIpUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuditAccessDeniedHandler implements AccessDeniedHandler {
    @Inject
    private AuditService auditService;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null) ? authentication.getName() : null;
        String ip = RequestIpUtil.getClientIp(request);
        String url = request.getRequestURI();
        if (request.getQueryString() != null) {
            url = url + "?" + request.getQueryString();
        }

        auditService.logEvent("ACCESS_DENIED", username, ip, "url=" + url);
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
