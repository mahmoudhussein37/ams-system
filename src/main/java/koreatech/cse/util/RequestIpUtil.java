package koreatech.cse.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public final class RequestIpUtil {
    private RequestIpUtil() {
    }

    public static String getClientIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(forwardedFor)) {
            String[] parts = forwardedFor.split(",");
            if (parts.length > 0) {
                String firstIp = parts[0].trim();
                if (StringUtils.isNotBlank(firstIp)) {
                    return firstIp;
                }
            }
        }
        return request.getRemoteAddr() != null ? request.getRemoteAddr().trim() : null;
    }
}