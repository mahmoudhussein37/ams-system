package koreatech.cse.filter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class SecurityHeadersFilterTest {

    private SecurityHeadersFilter securityHeadersFilter;

    @Before
    public void setUp() {
        securityHeadersFilter = new SecurityHeadersFilter();
    }

    @Test
    public void addsCoreSecurityHeadersForAllResponses() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/signin");
        request.setScheme("http");
        MockHttpServletResponse response = new MockHttpServletResponse();

        securityHeadersFilter.doFilter(request, response, noOpFilterChain());

        assertEquals("SAMEORIGIN", response.getHeader("X-Frame-Options"));
        assertEquals("nosniff", response.getHeader("X-Content-Type-Options"));
        assertEquals("strict-origin-when-cross-origin", response.getHeader("Referrer-Policy"));
        assertEquals("camera=(), microphone=(), geolocation=(), payment=(), usb=()", response.getHeader("Permissions-Policy"));
        assertNotNull(response.getHeader("Content-Security-Policy"));
    }

    @Test
    public void addsHstsForSecureRequests() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/student/register/basic");
        request.setScheme("http");
        request.setRemoteAddr("127.0.0.1");
        request.addHeader("X-Forwarded-Proto", "https");
        MockHttpServletResponse response = new MockHttpServletResponse();

        securityHeadersFilter.doFilter(request, response, noOpFilterChain());

        assertEquals("max-age=31536000; includeSubDomains", response.getHeader("Strict-Transport-Security"));
    }

    @Test
    public void hardensSessionCookieAttributes() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/signin");
        request.setScheme("https");
        MockHttpServletResponse response = new MockHttpServletResponse();

        securityHeadersFilter.doFilter(request, response, new FilterChain() {
            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse)
                    throws IOException, ServletException {
                ((HttpServletResponse) servletResponse).addHeader("Set-Cookie", "JSESSIONID=abc123; Path=/");
            }
        });

        String sessionCookie = response.getHeader("Set-Cookie");
        assertNotNull(sessionCookie);
        assertTrue(sessionCookie.contains("JSESSIONID=abc123"));
        assertTrue(sessionCookie.toLowerCase().contains("httponly"));
        assertTrue(sessionCookie.toLowerCase().contains("secure"));
        assertTrue(sessionCookie.toLowerCase().contains("samesite=lax"));
    }

    @Test
    public void setsSecureSessionCookieWhenBehindHttpsProxy() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/student/register/basic");
        request.setScheme("http");
        request.setRemoteAddr("127.0.0.1");
        request.addHeader("X-Forwarded-Proto", "https");
        MockHttpServletResponse response = new MockHttpServletResponse();

        securityHeadersFilter.doFilter(request, response, new FilterChain() {
            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse)
                    throws IOException, ServletException {
                ((HttpServletResponse) servletResponse).addHeader("Set-Cookie", "JSESSIONID=proxy123; Path=/");
            }
        });

        String sessionCookie = response.getHeader("Set-Cookie");
        assertNotNull(sessionCookie);
        assertTrue(sessionCookie.toLowerCase().contains("secure"));
    }

    @Test
    public void ignoresSpoofedForwardedProtoFromUntrustedRemoteAddress() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/student/register/basic");
        request.setScheme("http");
        request.setRemoteAddr("203.0.113.20");
        request.addHeader("X-Forwarded-Proto", "https");
        MockHttpServletResponse response = new MockHttpServletResponse();

        securityHeadersFilter.doFilter(request, response, noOpFilterChain());

        assertNull(response.getHeader("Strict-Transport-Security"));
    }

    @Test
    public void doesNotForceSecureCookieForLocalHttpDevelopment() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/signin");
        request.setScheme("http");
        MockHttpServletResponse response = new MockHttpServletResponse();

        securityHeadersFilter.doFilter(request, response, new FilterChain() {
            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse)
                    throws IOException, ServletException {
                ((HttpServletResponse) servletResponse).addHeader("Set-Cookie", "JSESSIONID=dev123; Path=/");
            }
        });

        String sessionCookie = response.getHeader("Set-Cookie");
        assertNotNull(sessionCookie);
        assertTrue(sessionCookie.toLowerCase().contains("httponly"));
        assertTrue(sessionCookie.toLowerCase().contains("samesite=lax"));
        assertTrue(!sessionCookie.toLowerCase().contains("secure"));
    }

    @Test
    public void appliesNoStoreCacheHeadersForAuthenticatedDynamicRequests() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/student/register/basic");
        request.setSession(authenticatedSession());
        MockHttpServletResponse response = new MockHttpServletResponse();

        securityHeadersFilter.doFilter(request, response, noOpFilterChain());

        assertEquals("no-store, no-cache, must-revalidate", response.getHeader("Cache-Control"));
        assertEquals("no-cache", response.getHeader("Pragma"));
        assertEquals("0", response.getHeader("Expires"));
    }

    @Test
    public void doesNotApplyNoStoreCacheHeadersToStaticAssets() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/resources/js/app.js");
        request.setSession(authenticatedSession());
        MockHttpServletResponse response = new MockHttpServletResponse();

        securityHeadersFilter.doFilter(request, response, noOpFilterChain());

        assertNull(response.getHeader("Cache-Control"));
        assertNull(response.getHeader("Pragma"));
        assertNull(response.getHeader("Expires"));
    }

    private MockHttpSession authenticatedSession() {
        MockHttpSession session = new MockHttpSession();
        SecurityContextImpl securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(
                "student@example.com",
                "n/a",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_STUDENT"))));
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        return session;
    }

    private FilterChain noOpFilterChain() {
        return new FilterChain() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response) {
            }
        };
    }
}
