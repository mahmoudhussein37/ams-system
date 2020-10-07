package koreatech.cse.service;

import koreatech.cse.domain.User;
import koreatech.cse.repository.AuthorityMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class AuthorityService {
    @Inject
    private UserService userService;
    @Inject
    private AuthorityMapper authorityMapper;

    public void authenticateUserAndSetSession(User user) {
        if(user != null) {
            UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
    }
}
