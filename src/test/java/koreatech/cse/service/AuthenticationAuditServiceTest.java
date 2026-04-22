package koreatech.cse.service;

import koreatech.cse.domain.constant.SecurityEventType;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import java.time.Instant;

import static org.junit.Assert.assertEquals;

public class AuthenticationAuditServiceTest {

    @Test
    public void formatEventUsesExpectedSecurityLogShape() {
        AuthenticationAuditService authenticationAuditService = new AuthenticationAuditService();

        String message = authenticationAuditService.formatEvent(SecurityEventType.LOGIN_FAILED,
                "john", "10.0.0.1", "ABC123", "Mozilla/5.0", "BAD_CREDENTIALS",
                Instant.parse("2026-04-03T09:00:00Z"));

        assertEquals(
                "SECURITY_EVENT | type=LOGIN_FAILED | user=john | ip=10.0.0.1 | session=ABC123 | agent=Mozilla/5.0 | timestamp=2026-04-03T09:00:00Z | reason=BAD_CREDENTIALS",
                message);
    }

    @Test
    public void requestAwareLoggingUsesSessionAndUserAgentWhenAvailable() {
        AuthenticationAuditService authenticationAuditService = new AuthenticationAuditService();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("10.0.0.1");
        request.addHeader("User-Agent", "Mozilla/5.0");
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);

        String message = authenticationAuditService.formatEvent(SecurityEventType.LOGIN_SUCCESS,
                "john", "10.0.0.1", session.getId(), "Mozilla/5.0", "AUTHENTICATED",
                Instant.parse("2026-04-03T09:00:00Z"));

        assertEquals(
                "SECURITY_EVENT | type=LOGIN_SUCCESS | user=john | ip=10.0.0.1 | session=" + session.getId()
                        + " | agent=Mozilla/5.0 | timestamp=2026-04-03T09:00:00Z | reason=AUTHENTICATED",
                message);
    }
}
