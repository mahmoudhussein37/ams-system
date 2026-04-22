package koreatech.cse.filter;

import koreatech.cse.service.AuthenticationAttemptService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("loginRateLimitFilter")
public class LoginRateLimitFilter extends OncePerRequestFilter {
    @Inject
    private AuthenticationAttemptService authenticationAttemptService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!isLoginRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authenticationAttemptService.isLoginBlocked(request)) {
            response.sendRedirect(request.getContextPath() + "/signin?msg=too_many_attempts");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isLoginRequest(HttpServletRequest request) {
        return "POST".equalsIgnoreCase(request.getMethod())
                && request.getRequestURI().equals(request.getContextPath() + "/j_spring_security_check");
    }
}
