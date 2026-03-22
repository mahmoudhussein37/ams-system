package koreatech.cse.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request);
        if (response.isCommitted()) {
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(@SuppressWarnings("unused") HttpServletRequest request) {
        // Suppress CodeQL unused-parameter: required by framework contract
        Objects.toString(request); // no-op reference
        return "/signin?msg=fail";
    }
}