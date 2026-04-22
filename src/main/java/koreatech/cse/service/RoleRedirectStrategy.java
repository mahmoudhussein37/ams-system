package koreatech.cse.service;

import koreatech.cse.domain.constant.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class RoleRedirectStrategy {
    private final Map<String, String> roleEntryPoints;

    public RoleRedirectStrategy() {
        Map<String, String> entryPoints = new LinkedHashMap<>();
        entryPoints.put(Role.admin.toCode(), "/admin/studentManagement/studentInformation");
        entryPoints.put(Role.professor.toCode(), "/professor/studentGuidance/studentLookup");
        entryPoints.put(Role.student.toCode(), "/student/register/basic");
        this.roleEntryPoints = Collections.unmodifiableMap(entryPoints);
    }

    public String resolveTargetUrl(Authentication authentication) {
        if (authentication == null || authentication.getAuthorities() == null) {
            return "/";
        }

        for (String prioritizedRole : roleEntryPoints.keySet()) {
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if (prioritizedRole.equals(grantedAuthority.getAuthority())) {
                    return roleEntryPoints.get(prioritizedRole);
                }
            }
        }
        return "/";
    }
}
