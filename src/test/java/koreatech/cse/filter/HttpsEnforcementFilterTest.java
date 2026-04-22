package koreatech.cse.filter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HttpsEnforcementFilterTest {

    private HttpsEnforcementFilter httpsEnforcementFilter;

    @Before
    public void setUp() {
        httpsEnforcementFilter = new HttpsEnforcementFilter();
    }

    @Test
    public void redirectsHttpRequestsToHttpsForNonLocalhost() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/signin");
        request.setScheme("http");
        request.setServerName("example.com");
        request.setServerPort(80);
        request.setQueryString("lang=en");
        MockHttpServletResponse response = new MockHttpServletResponse();

        httpsEnforcementFilter.doFilter(request, response, new MockFilterChain());

        assertEquals(301, response.getStatus());
        assertEquals("https://example.com/signin?lang=en", response.getHeader("Location"));
    }

    @Test
    public void skipsRedirectForLocalhostInDevelopment() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/signin");
        request.setScheme("http");
        request.setServerName("localhost");
        MockHttpServletResponse response = new MockHttpServletResponse();

        httpsEnforcementFilter.doFilter(request, response, new MockFilterChain());

        assertEquals(200, response.getStatus());
        assertNull(response.getHeader("Location"));
    }

    @Test
    public void respectsForwardedProtoHttpsAndAvoidsRedirectLoop() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/student/register/basic");
        request.setScheme("http");
        request.setServerName("internal-proxy");
        request.setRemoteAddr("127.0.0.1");
        request.addHeader("X-Forwarded-Proto", "https");
        request.addHeader("X-Forwarded-Host", "portal.example.com");
        MockHttpServletResponse response = new MockHttpServletResponse();

        httpsEnforcementFilter.doFilter(request, response, new MockFilterChain());

        assertEquals(200, response.getStatus());
        assertNull(response.getHeader("Location"));
    }

    @Test
    public void usesForwardedHostWhenRedirecting() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/feedback");
        request.setScheme("http");
        request.setServerName("internal-host");
        request.setRemoteAddr("127.0.0.1");
        request.addHeader("X-Forwarded-Host", "portal.example.com");
        MockHttpServletResponse response = new MockHttpServletResponse();

        httpsEnforcementFilter.doFilter(request, response, new MockFilterChain());

        assertEquals(301, response.getStatus());
        assertEquals("https://portal.example.com/feedback", response.getHeader("Location"));
    }

    @Test
    public void ignoresSpoofedForwardedHeadersFromUntrustedRemoteAddress() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/feedback");
        request.setScheme("http");
        request.setServerName("example.com");
        request.setRemoteAddr("203.0.113.10");
        request.addHeader("X-Forwarded-Proto", "https");
        request.addHeader("X-Forwarded-Host", "evil.example");
        MockHttpServletResponse response = new MockHttpServletResponse();

        httpsEnforcementFilter.doFilter(request, response, new MockFilterChain());

        assertEquals(301, response.getStatus());
        assertEquals("https://example.com/feedback", response.getHeader("Location"));
    }
}
