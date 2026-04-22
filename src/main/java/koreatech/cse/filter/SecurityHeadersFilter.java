package koreatech.cse.filter;

import koreatech.cse.service.TrustedRequestService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

/**
 * Adds baseline enterprise security headers and hardens session cookie attributes.
 */
public class SecurityHeadersFilter implements Filter {
    private static final String CSP_POLICY =
            "default-src 'self'; "
                    + "base-uri 'self'; "
                    + "form-action 'self'; "
                    + "frame-ancestors 'self'; "
                    + "object-src 'none'; "
                + "script-src 'self' 'unsafe-inline'; "
                    + "style-src 'self' 'unsafe-inline' https://fonts.googleapis.com; "
                    + "font-src 'self' data: https://fonts.gstatic.com https://fonts.googleapis.com; "
                    + "img-src 'self' data: blob: https:; "
                    + "connect-src 'self'";

    private TrustedRequestService trustedRequestService = new TrustedRequestService();

    @Override
    public void init(FilterConfig filterConfig) {
        WebApplicationContext webApplicationContext =
                WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        if (webApplicationContext != null) {
            trustedRequestService = webApplicationContext.getBean(TrustedRequestService.class);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        boolean secureRequest = trustedRequestService.isSecureRequest(httpRequest);
        HttpServletResponse wrappedResponse = new SameSiteSessionCookieResponseWrapper(httpResponse, secureRequest);

        applySecurityHeaders(wrappedResponse, secureRequest);
        applySensitiveCacheHeaders(httpRequest, wrappedResponse);
        chain.doFilter(request, wrappedResponse);
    }

    @Override
    public void destroy() {
    }

    private void applySecurityHeaders(HttpServletResponse response, boolean secureRequest) {
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
        response.setHeader("Permissions-Policy", "camera=(), microphone=(), geolocation=(), payment=(), usb=()");
        response.setHeader("Content-Security-Policy", CSP_POLICY);
        if (secureRequest) {
            response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
        }
    }

    private void applySensitiveCacheHeaders(HttpServletRequest request, HttpServletResponse response) {
        if (isStaticResourceRequest(request) || !isAuthenticatedRequest(request)) {
            return;
        }

        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
    }

    private boolean isStaticResourceRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (uri == null) {
            return false;
        }

        String contextPath = request.getContextPath();
        String resourcesPrefix = (contextPath == null ? "" : contextPath) + "/resources/";
        return uri.startsWith(resourcesPrefix);
    }

    private boolean isAuthenticatedRequest(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }

        Object securityContext = session.getAttribute("SPRING_SECURITY_CONTEXT");
        if (!(securityContext instanceof SecurityContext)) {
            return false;
        }

        Authentication authentication = ((SecurityContext) securityContext).getAuthentication();
        return authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }

    private static final class SameSiteSessionCookieResponseWrapper extends HttpServletResponseWrapper {
        private final boolean secureRequest;

        private SameSiteSessionCookieResponseWrapper(HttpServletResponse response, boolean secureRequest) {
            super(response);
            this.secureRequest = secureRequest;
        }

        @Override
        public void addHeader(String name, String value) {
            if ("Set-Cookie".equalsIgnoreCase(name)) {
                super.addHeader(name, hardenSessionCookie(value));
                return;
            }
            super.addHeader(name, value);
        }

        @Override
        public void setHeader(String name, String value) {
            if ("Set-Cookie".equalsIgnoreCase(name)) {
                super.setHeader(name, hardenSessionCookie(value));
                return;
            }
            super.setHeader(name, value);
        }

        private String hardenSessionCookie(String cookieHeader) {
            if (cookieHeader == null) {
                return null;
            }

            String trimmedCookie = cookieHeader.trim();
            if (!trimmedCookie.regionMatches(true, 0, "JSESSIONID=", 0, 11)) {
                return cookieHeader;
            }

            String lowerValue = trimmedCookie.toLowerCase(Locale.ROOT);
            String hardened = trimmedCookie;

            if (!lowerValue.contains("httponly")) {
                hardened += "; HttpOnly";
            }
            if (secureRequest && !lowerValue.contains("secure")) {
                hardened += "; Secure";
            }
            if (!lowerValue.contains("samesite=")) {
                hardened += "; SameSite=Lax";
            }
            return hardened;
        }
    }
}
