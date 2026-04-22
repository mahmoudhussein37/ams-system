package koreatech.cse.service;

import koreatech.cse.domain.constant.SecurityEventType;
import koreatech.cse.domain.constant.SignupResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class AuthenticationAttemptService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationAttemptService.class);

    private static final String LOGIN_NAMESPACE = "login";
    private static final String SIGNUP_NAMESPACE = "signup";

    private static final int MAX_LOGIN_ATTEMPTS_BY_IP       = 5;
    private static final int MAX_LOGIN_ATTEMPTS_BY_USERNAME = 20;
    private static final int MAX_SIGNUP_FAILURES = 5;
    private static final int MAX_FAILURE_COUNT = 50;
    private static final long LOGIN_WINDOW_MS = TimeUnit.MINUTES.toMillis(15);
    private static final long SIGNUP_WINDOW_MS = TimeUnit.MINUTES.toMillis(15);
    private static final long LOGIN_BLOCK_10_MINUTES_MS = TimeUnit.MINUTES.toMillis(10);
    private static final long LOGIN_BLOCK_30_MINUTES_MS = TimeUnit.MINUTES.toMillis(30);
    private static final long LOGIN_BLOCK_60_MINUTES_MS = TimeUnit.MINUTES.toMillis(60);
    private static final long SIGNUP_BLOCK_MS = TimeUnit.MINUTES.toMillis(10);

    @Inject
    private AttemptTrackingStore attemptTrackingStore;
    @Inject
    private AuthenticationAuditService authenticationAuditService;
    @Inject
    private TrustedRequestService trustedRequestService = new TrustedRequestService();

    public boolean isLoginBlocked(HttpServletRequest request) {
        return isLoginBlocked(request.getParameter("j_username"), resolveClientIp(request));
    }

    public boolean isLoginBlocked(String username, String clientIp) {
        String normalizedUsername = normalizeUsername(username);
        long now = currentTimeMillis();

        boolean ipBlocked = isLoginKeyBlocked(buildLoginKey(normalizedUsername, clientIp), now);
        boolean userBlocked = isLoginKeyBlocked(buildUsernameOnlyKey(normalizedUsername), now);

        boolean blocked = ipBlocked || userBlocked;
        if (blocked) {
            UUID requestId = UUID.randomUUID();
            logger.warn("[{}] event=LOGIN_BLOCKED username={} ip={} ipBlocked={} userBlocked={}",
                    requestId, safeValue(normalizedUsername), safeValue(clientIp), ipBlocked, userBlocked);
            authenticationAuditService.log(SecurityEventType.LOGIN_BLOCKED, normalizedUsername, clientIp, "RATE_LIMITED");
        }
        return blocked;
    }

    public void recordLoginFailure(HttpServletRequest request) {
        recordLoginFailure(request.getParameter("j_username"), resolveClientIp(request));
    }

    public void recordLoginFailure(String username, String clientIp) {
        String normalizedUsername = normalizeUsername(username);
        String ipKey = buildLoginKey(normalizedUsername, clientIp);
        String userKey = buildUsernameOnlyKey(normalizedUsername);
        long now = currentTimeMillis();

        AttemptTrackingStore.AttemptState ipState = attemptTrackingStore.compute(LOGIN_NAMESPACE, ipKey,
                current -> nextState(current, now, LOGIN_WINDOW_MS, true));
        attemptTrackingStore.compute(LOGIN_NAMESPACE, userKey,
                current -> nextUsernameOnlyState(current, now));

        logAttemptEvent("LOGIN_FAILURE_RECORDED", normalizedUsername, clientIp, ipState);
    }

    public void recordLoginSuccess(HttpServletRequest request, String username) {
        recordLoginSuccess(username, resolveClientIp(request));
    }

    public void recordLoginSuccess(String username, String clientIp) {
        String normalizedUsername = normalizeUsername(username);
        attemptTrackingStore.remove(LOGIN_NAMESPACE, buildLoginKey(normalizedUsername, clientIp));
        attemptTrackingStore.remove(LOGIN_NAMESPACE, buildUsernameOnlyKey(normalizedUsername));

        UUID requestId = UUID.randomUUID();
        logger.info("[{}] event=LOGIN_SUCCESS_RESET username={} ip={}", requestId, safeValue(normalizedUsername), safeValue(clientIp));
    }

    public boolean checkSignupAllowed(HttpServletRequest request, String number) {
        return checkSignupAllowed(number, resolveClientIp(request));
    }

    public boolean checkSignupAllowed(String number, String clientIp) {
        String normalizedNumber = normalizeNumber(number);
        String key = buildSignupKey(normalizedNumber, clientIp);
        long now = currentTimeMillis();

        AttemptTrackingStore.AttemptState state = attemptTrackingStore.get(SIGNUP_NAMESPACE, key);
        if (isExpired(state, now, SIGNUP_WINDOW_MS)) {
            attemptTrackingStore.remove(SIGNUP_NAMESPACE, key);
            return true;
        }

        if (hasExpiredBlock(state, now)) {
            clearExpiredBlock(SIGNUP_NAMESPACE, key, now, SIGNUP_WINDOW_MS);
            return true;
        }

        boolean allowed = state == null || !state.isBlocked(now);
        if (!allowed) {
            UUID requestId = UUID.randomUUID();
            logger.warn("[{}] event=SIGNUP_BLOCKED username={} ip={} number={}",
                    requestId, "n/a", safeValue(clientIp), safeValue(normalizedNumber));
            authenticationAuditService.log(SecurityEventType.SIGNUP_BLOCKED, normalizedNumber, clientIp, "RATE_LIMITED");
        }
        return allowed;
    }

    public void recordSignupResult(HttpServletRequest request, String number, SignupResult result) {
        recordSignupResult(number, resolveClientIp(request), result);
    }

    public void recordSignupResult(String number, String clientIp, SignupResult result) {
        String normalizedNumber = normalizeNumber(number);
        String key = buildSignupKey(normalizedNumber, clientIp);

        if (result == SignupResult.SUCCESS) {
            attemptTrackingStore.remove(SIGNUP_NAMESPACE, key);
            UUID requestId = UUID.randomUUID();
            logger.info("[{}] event=SIGNUP_SUCCESS_RESET username={} ip={} number={}",
                    requestId, "n/a", safeValue(clientIp), safeValue(normalizedNumber));
            return;
        }

        long now = currentTimeMillis();
        AttemptTrackingStore.AttemptState state = attemptTrackingStore.compute(SIGNUP_NAMESPACE, key,
                current -> nextState(current, now, SIGNUP_WINDOW_MS, false));

        logAttemptEvent("SIGNUP_FAILURE_RECORDED", null, clientIp, state, normalizedNumber);
    }

    protected long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    protected String resolveClientIp(HttpServletRequest request) {
        return trustedRequestService.resolveClientIp(request);
    }

    private boolean isLoginKeyBlocked(String key, long now) {
        AttemptTrackingStore.AttemptState state = attemptTrackingStore.get(LOGIN_NAMESPACE, key);
        if (isExpired(state, now, LOGIN_WINDOW_MS)) {
            attemptTrackingStore.remove(LOGIN_NAMESPACE, key);
            return false;
        }
        if (hasExpiredBlock(state, now)) {
            clearExpiredBlock(LOGIN_NAMESPACE, key, now, LOGIN_WINDOW_MS);
            return false;
        }
        return state != null && state.isBlocked(now);
    }

    private AttemptTrackingStore.AttemptState nextUsernameOnlyState(
            AttemptTrackingStore.AttemptState current, long now) {
        AttemptTrackingStore.AttemptState normalizedCurrent = normalizeCurrentState(current, now, LOGIN_WINDOW_MS);
        if (normalizedCurrent == null) {
            return new AttemptTrackingStore.AttemptState(1, now, now, 0L);
        }
        if (normalizedCurrent.isBlocked(now)) {
            return normalizedCurrent;
        }
        int nextFailureCount = Math.min(normalizedCurrent.getFailureCount() + 1, MAX_FAILURE_COUNT);
        long blockDuration = usernameOnlyBlockDuration(nextFailureCount);
        long nextBlockUntil;
        if (blockDuration == 0L) {
            nextBlockUntil = normalizedCurrent.getCurrentBlockUntil();
        } else {
            long proposed = now + blockDuration;
            nextBlockUntil = Math.max(normalizedCurrent.getCurrentBlockUntil(), proposed);
        }
        return new AttemptTrackingStore.AttemptState(
                nextFailureCount,
                normalizedCurrent.getFirstFailureTimestamp(),
                now,
                nextBlockUntil);
    }

    private AttemptTrackingStore.AttemptState nextState(AttemptTrackingStore.AttemptState current, long now,
            long windowMs, boolean loginFlow) {
        AttemptTrackingStore.AttemptState normalizedCurrent = normalizeCurrentState(current, now, windowMs);
        if (normalizedCurrent == null) {
            return createState(1, now, loginFlow);
        }

        if (normalizedCurrent.isBlocked(now)) {
            return normalizedCurrent;
        }

        int nextFailureCount = Math.min(normalizedCurrent.getFailureCount() + 1, MAX_FAILURE_COUNT);
        long nextBlockUntil = calculateBlockUntil(normalizedCurrent, nextFailureCount, now, loginFlow);
        return new AttemptTrackingStore.AttemptState(
                nextFailureCount,
                normalizedCurrent.getFirstFailureTimestamp(),
                now,
                nextBlockUntil);
    }

    private AttemptTrackingStore.AttemptState createState(int failureCount, long now, boolean loginFlow) {
        long blockUntil = calculateBlockUntil(null, failureCount, now, loginFlow);
        return new AttemptTrackingStore.AttemptState(failureCount, now, now, blockUntil);
    }

    private long calculateBlockUntil(AttemptTrackingStore.AttemptState current, int failureCount, long now,
            boolean loginFlow) {
        long blockDuration = loginFlow ? loginBlockDuration(failureCount) : signupBlockDuration(failureCount);
        if (blockDuration == 0L) {
            return current == null ? 0L : current.getCurrentBlockUntil();
        }

        long proposedBlockUntil = now + blockDuration;
        return current == null ? proposedBlockUntil : Math.max(current.getCurrentBlockUntil(), proposedBlockUntil);
    }

    private long loginBlockDuration(int failureCount) {
        if (failureCount >= 20) {
            return LOGIN_BLOCK_60_MINUTES_MS;
        }
        if (failureCount >= 10) {
            return LOGIN_BLOCK_30_MINUTES_MS;
        }
        if (failureCount >= MAX_LOGIN_ATTEMPTS_BY_IP) {
            return LOGIN_BLOCK_10_MINUTES_MS;
        }
        return 0L;
    }

    private long usernameOnlyBlockDuration(int failureCount) {
        return failureCount >= MAX_LOGIN_ATTEMPTS_BY_USERNAME ? LOGIN_BLOCK_60_MINUTES_MS : 0L;
    }

    private long signupBlockDuration(int failureCount) {
        return failureCount >= MAX_SIGNUP_FAILURES ? SIGNUP_BLOCK_MS : 0L;
    }

    private boolean isExpired(AttemptTrackingStore.AttemptState state, long now, long windowMs) {
        if (state == null) {
            return true;
        }

        if (hasExpiredBlock(state, now)) {
            return false;
        }

        if (state.isBlocked(now)) {
            return false;
        }

        return shouldResetSlidingWindow(state, now, windowMs);
    }

    private AttemptTrackingStore.AttemptState normalizeCurrentState(AttemptTrackingStore.AttemptState current, long now,
            long windowMs) {
        if (current == null || shouldResetSlidingWindow(current, now, windowMs)) {
            return null;
        }

        if (hasExpiredBlock(current, now)) {
            return new AttemptTrackingStore.AttemptState(
                    current.getFailureCount(),
                    current.getFirstFailureTimestamp(),
                    current.getLastFailureTimestamp(),
                    0L);
        }

        return current;
    }

    private boolean shouldResetSlidingWindow(AttemptTrackingStore.AttemptState state, long now, long windowMs) {
        return now - state.getLastFailureTimestamp() > windowMs;
    }

    private boolean hasExpiredBlock(AttemptTrackingStore.AttemptState state, long now) {
        return state != null && state.getCurrentBlockUntil() > 0L && state.getCurrentBlockUntil() <= now;
    }

    private void clearExpiredBlock(String namespace, String key, long now, long windowMs) {
        attemptTrackingStore.compute(namespace, key, current -> {
            if (current == null) {
                return null;
            }
            if (shouldResetSlidingWindow(current, now, windowMs)) {
                return null;
            }
            if (hasExpiredBlock(current, now)) {
                return new AttemptTrackingStore.AttemptState(
                        current.getFailureCount(),
                        current.getFirstFailureTimestamp(),
                        current.getLastFailureTimestamp(),
                        0L);
            }
            return current;
        });
    }

    private String buildLoginKey(String username, String clientIp) {
        String normalizedIp = normalizeIp(clientIp);
        if (username == null) {
            return "anonymous:" + safeValue(normalizedIp);
        }
        return username + ":" + safeValue(normalizedIp);
    }

    private String buildUsernameOnlyKey(String username) {
        String normalized = StringUtils.trimToNull(username);
        return "username_only:" + (normalized != null ? normalized.toLowerCase(Locale.ROOT) : "anonymous");
    }

    private String buildSignupKey(String number, String clientIp) {
        String normalizedIp = normalizeIp(clientIp);
        if (number == null) {
            return "anonymous:" + safeValue(normalizedIp);
        }
        return number + ":" + safeValue(normalizedIp);
    }

    private String normalizeUsername(String username) {
        String trimmed = StringUtils.trimToNull(username);
        return trimmed == null ? null : trimmed.toLowerCase(Locale.ROOT);
    }

    private String normalizeNumber(String number) {
        return StringUtils.trimToNull(number);
    }

    private String normalizeIp(String ipAddress) {
        return StringUtils.trimToNull(ipAddress);
    }

    private void logAttemptEvent(String eventType, String username, String clientIp,
            AttemptTrackingStore.AttemptState state) {
        logAttemptEvent(eventType, username, clientIp, state, null);
    }

    private void logAttemptEvent(String eventType, String username, String clientIp,
            AttemptTrackingStore.AttemptState state, String number) {
        UUID requestId = UUID.randomUUID();
        logger.warn("[{}] event={} username={} ip={} number={} failures={} blockedUntil={}",
                requestId,
                eventType,
                safeValue(username),
                safeValue(clientIp),
                safeValue(number),
                state == null ? 0 : state.getFailureCount(),
                state == null ? 0L : state.getCurrentBlockUntil());
    }

    private String safeValue(String value) {
        return value == null ? "n/a" : value;
    }
}
