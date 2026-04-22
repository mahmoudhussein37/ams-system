package koreatech.cse.service;

import koreatech.cse.domain.constant.SignupResult;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthenticationAttemptServiceTest {

    private TestableAuthenticationAttemptService authenticationAttemptService;
    private InMemoryAttemptTrackingStore attemptTrackingStore;

    @Before
    public void setUp() {
        authenticationAttemptService = new TestableAuthenticationAttemptService();
        attemptTrackingStore = new InMemoryAttemptTrackingStore();
        ReflectionTestUtils.setField(authenticationAttemptService, "attemptTrackingStore", attemptTrackingStore);
        ReflectionTestUtils.setField(authenticationAttemptService, "authenticationAuditService", new AuthenticationAuditService());
        ReflectionTestUtils.setField(authenticationAttemptService, "trustedRequestService", new TrustedRequestService());
        ReflectionTestUtils.setField(attemptTrackingStore, "staleEntryRetentionMs", Long.MAX_VALUE / 4);
        authenticationAttemptService.setNow(1_000L);
    }

    @Test
    public void sameIpDifferentUsersAreTrackedIndependently() {
        for (int i = 0; i < 5; i++) {
            authenticationAttemptService.recordLoginFailure("alice@example.com", "10.0.0.1");
        }

        assertTrue(authenticationAttemptService.isLoginBlocked("alice@example.com", "10.0.0.1"));
        assertFalse(authenticationAttemptService.isLoginBlocked("bob@example.com", "10.0.0.1"));
    }

    @Test
    public void anonymousLoginAttemptsUseAnonymousCompositeKey() {
        for (int i = 0; i < 5; i++) {
            authenticationAttemptService.recordLoginFailure("   ", "10.0.0.1");
        }

        assertTrue(authenticationAttemptService.isLoginBlocked(null, "10.0.0.1"));
        assertFalse(authenticationAttemptService.isLoginBlocked("student@example.com", "10.0.0.1"));
    }

    @Test
    public void loginPenaltyEscalatesAtFiveAndTenFailures() {
        for (int i = 0; i < 5; i++) {
            authenticationAttemptService.recordLoginFailure("student@example.com", "10.0.0.1");
        }
        assertTrue(authenticationAttemptService.isLoginBlocked("student@example.com", "10.0.0.1"));

        long[] retryMinutes = {10L, 20L, 30L, 40L, 50L};
        for (long retryMinute : retryMinutes) {
            authenticationAttemptService.setNow(1_000L + TimeUnit.MINUTES.toMillis(retryMinute));
            authenticationAttemptService.recordLoginFailure("student@example.com", "10.0.0.1");
        }

        authenticationAttemptService.setNow(1_000L + TimeUnit.MINUTES.toMillis(79));
        assertTrue(authenticationAttemptService.isLoginBlocked("student@example.com", "10.0.0.1"));
        authenticationAttemptService.setNow(1_000L + TimeUnit.MINUTES.toMillis(80));
        assertFalse(authenticationAttemptService.isLoginBlocked("student@example.com", "10.0.0.1"));
    }

    @Test
    public void loginAttemptsResetOnSuccess() {
        for (int i = 0; i < 3; i++) {
            authenticationAttemptService.recordLoginFailure("student@example.com", "10.0.0.1");
        }

        authenticationAttemptService.recordLoginSuccess("student@example.com", "10.0.0.1");

        assertFalse(authenticationAttemptService.isLoginBlocked("student@example.com", "10.0.0.1"));
        assertTrue(storeNamespace("login").isEmpty());
    }

    @Test
    public void signupThrottlingIsKeyedByNumberAndIpAndResetsOnSuccess() {
        for (int i = 0; i < 5; i++) {
            authenticationAttemptService.recordSignupResult("2024001", "10.0.0.1", SignupResult.INVALID_INPUT);
        }

        assertFalse(authenticationAttemptService.checkSignupAllowed("2024001", "10.0.0.1"));
        assertTrue(authenticationAttemptService.checkSignupAllowed("2024002", "10.0.0.1"));

        authenticationAttemptService.recordSignupResult("2024001", "10.0.0.1", SignupResult.SUCCESS);

        assertTrue(authenticationAttemptService.checkSignupAllowed("2024001", "10.0.0.1"));
    }

    @Test
    public void slidingWindowResetTreatsLaterFailureAsFreshSession() {
        for (int i = 0; i < 3; i++) {
            authenticationAttemptService.recordLoginFailure("student@example.com", "10.0.0.1");
        }

        authenticationAttemptService.setNow(1_000L + TimeUnit.MINUTES.toMillis(15) + 1L);
        authenticationAttemptService.recordLoginFailure("student@example.com", "10.0.0.1");

        AttemptTrackingStore.AttemptState state = loginState("student@example.com:10.0.0.1");
        assertEquals(1, state.getFailureCount());
        assertEquals(0L, state.getCurrentBlockUntil());
    }

    @Test
    public void failureCountIsCappedAtFifty() {
        attemptTrackingStore.compute("login", "student@example.com:10.0.0.1",
                current -> new AttemptTrackingStore.AttemptState(49, 1_000L, 1_000L, 0L));

        authenticationAttemptService.recordLoginFailure("student@example.com", "10.0.0.1");
        authenticationAttemptService.recordLoginFailure("student@example.com", "10.0.0.1");

        AttemptTrackingStore.AttemptState state = loginState("student@example.com:10.0.0.1");
        assertEquals(50, state.getFailureCount());
    }

    @Test
    public void expiredBlockAllowsAttemptsAgainWithoutStaleBlockState() {
        for (int i = 0; i < 5; i++) {
            authenticationAttemptService.recordLoginFailure("student@example.com", "10.0.0.1");
        }

        authenticationAttemptService.setNow(1_000L + TimeUnit.MINUTES.toMillis(10));

        assertFalse(authenticationAttemptService.isLoginBlocked("student@example.com", "10.0.0.1"));
        assertEquals(0L, loginState("student@example.com:10.0.0.1").getCurrentBlockUntil());

        authenticationAttemptService.recordLoginFailure("student@example.com", "10.0.0.1");

        assertEquals(6, loginState("student@example.com:10.0.0.1").getFailureCount());
    }

    @Test
    public void expiredEntriesAreRemovedLazilyOnAccess() {
        for (int i = 0; i < 5; i++) {
            authenticationAttemptService.recordSignupResult("2024001", "10.0.0.1", SignupResult.INVALID_INPUT);
        }

        authenticationAttemptService.setNow(1_000L + TimeUnit.MINUTES.toMillis(16));

        assertTrue(authenticationAttemptService.checkSignupAllowed("2024001", "10.0.0.1"));
        assertTrue(storeNamespace("signup").isEmpty());
    }

    @Test
    public void concurrentLoginFailuresRemainThreadSafe() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(6);

        for (int i = 0; i < 6; i++) {
            executorService.submit(() -> {
                try {
                    startLatch.await();
                    authenticationAttemptService.recordLoginFailure("student@example.com", "10.0.0.1");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        startLatch.countDown();
        assertTrue(doneLatch.await(5, TimeUnit.SECONDS));
        executorService.shutdownNow();

        assertTrue(authenticationAttemptService.isLoginBlocked("student@example.com", "10.0.0.1"));
    }

    @Test
    public void usernameThrottleBlocksAfterThresholdRegardlessOfIp() {
        String username = "victim@example.com";
        for (int i = 0; i < 20; i++) {
            authenticationAttemptService.recordLoginFailure(username, "192.168.1." + i);
        }
        assertTrue(authenticationAttemptService.isLoginBlocked(username, "10.99.99.99"));
    }

    @Test
    public void resolveClientIpIgnoresSpoofedForwardedHeaderForUntrustedRemoteAddress() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("203.0.113.30");
        request.addHeader("X-Forwarded-For", "198.51.100.10");

        assertEquals("203.0.113.30", authenticationAttemptService.exposeClientIp(request));
    }

    private AttemptTrackingStore.AttemptState loginState(String key) {
        return storeNamespace("login").get(key);
    }

    @SuppressWarnings("unchecked")
    private Map<String, AttemptTrackingStore.AttemptState> storeNamespace(String namespace) {
        Map<String, Map<String, AttemptTrackingStore.AttemptState>> namespaces =
                (Map<String, Map<String, AttemptTrackingStore.AttemptState>>) ReflectionTestUtils.getField(attemptTrackingStore, "namespaces");
        Map<String, AttemptTrackingStore.AttemptState> namespaceStore = namespaces.get(namespace);
        return namespaceStore == null ? Collections.<String, AttemptTrackingStore.AttemptState>emptyMap() : namespaceStore;
    }

    private static final class TestableAuthenticationAttemptService extends AuthenticationAttemptService {
        private long now;

        private void setNow(long now) {
            this.now = now;
        }

        @Override
        protected long currentTimeMillis() {
            return now;
        }

        private String exposeClientIp(MockHttpServletRequest request) {
            return resolveClientIp(request);
        }
    }
}
