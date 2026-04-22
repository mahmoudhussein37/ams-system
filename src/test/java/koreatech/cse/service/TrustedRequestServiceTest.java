package koreatech.cse.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TrustedRequestServiceTest {

    private TrustedRequestService trustedRequestService;

    @Before
    public void setUp() {
        trustedRequestService = new TrustedRequestService();
        trustedRequestService.setTrustedProxyCidrs("127.0.0.1/32,10.0.0.0/8");
    }

    @Test
    public void trustedProxyMayForwardClientIp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("10.1.2.3");
        request.addHeader("X-Forwarded-For", "198.51.100.20");

        assertEquals("198.51.100.20", trustedRequestService.resolveClientIp(request));
    }

    @Test
    public void untrustedRemoteAddressCannotSpoofForwardedClientIp() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("203.0.113.50");
        request.addHeader("X-Forwarded-For", "198.51.100.20");

        assertEquals("203.0.113.50", trustedRequestService.resolveClientIp(request));
    }

    @Test
    public void trustedProxyMayMarkRequestSecure() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("127.0.0.1");
        request.setScheme("http");
        request.addHeader("X-Forwarded-Proto", "https");

        assertTrue(trustedRequestService.isSecureRequest(request));
    }

    @Test
    public void spoofedForwardedProtoFromUntrustedRemoteAddressIsIgnored() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("203.0.113.60");
        request.setScheme("http");
        request.addHeader("X-Forwarded-Proto", "https");

        assertFalse(trustedRequestService.isSecureRequest(request));
    }
}
