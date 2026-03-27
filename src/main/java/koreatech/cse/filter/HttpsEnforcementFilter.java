package koreatech.cse.filter;

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

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String scheme = httpRequest.getScheme();
        String serverName = httpRequest.getServerName();

        boolean isLocalhost = "localhost".equals(serverName)
                || "127.0.0.1".equals(serverName)
                || "0:0:0:0:0:0:0:1".equals(serverName);

        if ("http".equals(scheme) && !isLocalhost) {
            String redirectUrl = "https://" + serverName + httpRequest.getRequestURI();
            String queryString = httpRequest.getQueryString();
            if (queryString != null) {
                redirectUrl += "?" + queryString;
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
