package koreatech.cse.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class RoleRedirectStrategyTest {

    private RoleRedirectStrategy roleRedirectStrategy;

    @Before
    public void setUp() {
        roleRedirectStrategy = new RoleRedirectStrategy();
    }

    @Test
    public void resolvesAdminEntryPointWithHighestPriority() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                "admin@example.com",
                "n/a",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_STUDENT"), new SimpleGrantedAuthority("ROLE_ADMIN")));

        assertEquals("/admin/studentManagement/studentInformation", roleRedirectStrategy.resolveTargetUrl(authentication));
    }

    @Test
    public void resolvesProfessorEntryPoint() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                "prof@example.com",
                "n/a",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_PROFESSOR")));

        assertEquals("/professor/studentGuidance/studentLookup", roleRedirectStrategy.resolveTargetUrl(authentication));
    }

    @Test
    public void resolvesStudentEntryPoint() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                "student@example.com",
                "n/a",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_STUDENT")));

        assertEquals("/student/register/basic", roleRedirectStrategy.resolveTargetUrl(authentication));
    }
}
