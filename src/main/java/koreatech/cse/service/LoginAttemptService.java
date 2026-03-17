package koreatech.cse.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class LoginAttemptService {
    private static final int MAX_FAILED_USERNAME_ATTEMPTS = 5;
    private static final int MAX_FAILED_IP_ATTEMPTS = 20;
    private static final long LOCK_DURATION_MILLIS = 15L * 60L * 1000L;

    private final ConcurrentMap<String, Integer> attempts = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Long> lockTime = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Integer> ipAttempts = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Long> ipLockTime = new ConcurrentHashMap<>();

    @javax.inject.Inject
    private AuditService auditService;

    public boolean isBlocked(String username) {
        String key = normalize(username);
        if (key == null) {
            return false;
        }

        Long lockedUntil = lockTime.get(key);
        if (lockedUntil == null) {
            return false;
        }

        long now = System.currentTimeMillis();
        if (now < lockedUntil) {
            return true;
        }

        lockTime.remove(key);
        attempts.remove(key);
        return false;
    }

    public boolean isIpBlocked(String ip) {
        String key = normalizeIp(ip);
        if (key == null) {
            return false;
        }

        Long lockedUntil = ipLockTime.get(key);
        if (lockedUntil == null) {
            return false;
        }

        long now = System.currentTimeMillis();
        if (now < lockedUntil) {
            return true;
        }

        ipLockTime.remove(key);
        ipAttempts.remove(key);
        return false;
    }

    public void loginFailed(String username, String ip) {
        String key = normalize(username);
        if (key != null && !isBlocked(key)) {
            int failed = attempts.merge(key, 1, Integer::sum);
            if (failed >= MAX_FAILED_USERNAME_ATTEMPTS) {
                lockTime.put(key, System.currentTimeMillis() + LOCK_DURATION_MILLIS);
                auditService.logEvent("ACCOUNT_LOCKED", key, normalizeIp(ip), "failedAttempts=" + failed);
            }
        }

        String ipKey = normalizeIp(ip);
        if (ipKey != null && !isIpBlocked(ipKey)) {
            int ipFailed = ipAttempts.merge(ipKey, 1, Integer::sum);
            if (ipFailed >= MAX_FAILED_IP_ATTEMPTS) {
                ipLockTime.put(ipKey, System.currentTimeMillis() + LOCK_DURATION_MILLIS);
                auditService.logEvent("IP_BLOCKED", key, ipKey, "failedIpAttempts=" + ipFailed);
            }
        }
    }

    public void loginSucceeded(String username, String ip) {
        String key = normalize(username);
        if (key != null) {
            attempts.remove(key);
            lockTime.remove(key);
        }

        String ipKey = normalizeIp(ip);
        if (ipKey != null) {
            ipAttempts.remove(ipKey);
            ipLockTime.remove(ipKey);
        }
    }

    private String normalize(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }
        return username.trim().toLowerCase();
    }

    private String normalizeIp(String ip) {
        if (StringUtils.isBlank(ip)) {
            return null;
        }
        return ip.trim();
    }
}
