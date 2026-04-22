package koreatech.cse.service;

import koreatech.cse.domain.constant.SecurityEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;

@Service
public class AuthenticationAuditService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationAuditService.class);

    @Inject
    private TrustedRequestService trustedRequestService = new TrustedRequestService();

    public void log(SecurityEventType type, String username, String ipAddress, String reason) {
        logger.info(formatEvent(type, username, ipAddress, null, null, reason, Instant.now()));
    }

    public void log(SecurityEventType type, String username, HttpServletRequest request, String reason) {
        logger.info(formatEvent(type, username, resolveClientIp(request), resolveSessionId(request),
                resolveUserAgent(request), reason, Instant.now()));
    }

    String formatEvent(SecurityEventType type, String username, String ipAddress, String sessionId, String userAgent,
            String reason, Instant timestamp) {
        return "SECURITY_EVENT | type=" + type.name()
                + " | user=" + safeValue(username)
                + " | ip=" + safeValue(ipAddress)
                + " | session=" + safeValue(sessionId)
                + " | agent=" + safeValue(userAgent)
                + " | timestamp=" + timestamp.toString()
                + " | reason=" + safeValue(reason);
    }

    private String safeValue(String value) {
        if (value == null) return "n/a";
        return value.replace('\r', '_').replace('\n', '_');
    }

    private String resolveClientIp(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return safeTrim(trustedRequestService.resolveClientIp(request));
    }

    private String resolveSessionId(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        HttpSession session = request.getSession(false);
        return session == null ? null : safeTrim(session.getId());
    }

    private String resolveUserAgent(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return safeTrim(request.getHeader("User-Agent"));
    }

    private String safeTrim(String value) {
        return value == null ? null : value.trim();
    }
}
