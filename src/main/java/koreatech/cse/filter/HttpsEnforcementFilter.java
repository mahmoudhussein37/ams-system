package koreatech.cse.filter;

import koreatech.cse.service.TrustedRequestService;
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
import java.io.IOException;

/**
 * Application-level HTTPS enforcement filter.
 * Redirects HTTP requests to HTTPS as a safety net in case
 * Nginx misconfiguration allows plain HTTP through.
 *
 * Skips enforcement for localhost requests (dev environment).
 */
public class HttpsEnforcementFilter implements Filter {
    private TrustedRequestService trustedRequestService = new TrustedRequestService();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
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

        String serverName = trustedRequestService.resolveServerName(httpRequest);
        boolean secureRequest = trustedRequestService.isSecureRequest(httpRequest);

        boolean isLocalhost = "localhost".equals(serverName)
                || "127.0.0.1".equals(serverName)
                || "0:0:0:0:0:0:0:1".equals(serverName);

        if (!secureRequest && !isLocalhost) {
            // Strip CR/LF to prevent HTTP response splitting via Location header injection
            String rawUri = httpRequest.getRequestURI().replaceAll("[\r\n]", "");
            // Block protocol-relative paths that could become open redirects
            if (rawUri.startsWith("//") || rawUri.contains("://")) {
                rawUri = "/";
            }
            String redirectUrl = "https://" + trustedRequestService.resolveHostForRedirect(httpRequest) + rawUri;
            String queryString = httpRequest.getQueryString();
            if (queryString != null) {
                redirectUrl += "?" + queryString.replaceAll("[\r\n]", "");
            }
            httpResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            httpResponse.setHeader("Location", redirectUrl);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
