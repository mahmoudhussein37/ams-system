package koreatech.cse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuditService {
    private static final Logger logger = LoggerFactory.getLogger(AuditService.class);

    public void logEvent(String eventType, String username, String ip, String details) {
        logger.info("[AUDIT] event={} user={} ip={} details={} timestamp={}",
                safe(eventType),
                safe(username),
                safe(ip),
                safe(details),
                Instant.now().toString());
    }

    private String safe(String value) {
        if (value == null) {
            return "-";
        }
        return value.replaceAll("[\\n\\r\\t]", "_");
    }
}
