package koreatech.cse.handler;

import koreatech.cse.service.TrustedRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(AuditAccessDeniedHandler.class);

    @Inject
    private TrustedRequestService trustedRequestService;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null) ? authentication.getName() : "anonymous";
        String ip = sanitize(trustedRequestService.resolveClientIp(request));
        String url = sanitize(buildRequestUrl(request));

        logger.warn("SECURITY_EVENT | type=ACCESS_DENIED | user={} | url={} | ip={}", username, url, ip);
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    private String buildRequestUrl(HttpServletRequest request) {
        String url = request.getRequestURI();
        if (request.getQueryString() != null) {
            url = url + "?" + request.getQueryString();
        }
        return url;
    }

    private String sanitize(String value) {
        if (value == null) {
            return "n/a";
        }
        return value.replace('\r', '_').replace('\n', '_');
    }
}
