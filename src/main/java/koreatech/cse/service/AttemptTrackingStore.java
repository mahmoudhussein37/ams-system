package koreatech.cse.service;

/**
 * Contract for storing and querying login attempt state for rate-limiting purposes.
 *
 * <h3>Persistence contract</h3>
 * Implementations are NOT required to persist state across JVM restarts.
 * The in-memory implementation loses all state on restart; this is acceptable for
 * single-node deployments. Implementations intended for multi-node deployments
 * MUST provide cross-node consistency (e.g., via Redis or a shared DB).
 *
 * <h3>Atomicity</h3>
 * The {@link #compute} method must be atomic with respect to concurrent calls
 * for the same (namespace, key) pair. Implementations using non-atomic structures
 * (e.g., HashMap) are not acceptable in production.
 *
 * <h3>TTL semantics</h3>
 * Lockout expiry is managed by the {@link AttemptState} record, not by the store.
 * Stores may optionally evict expired entries for memory efficiency, but must never
 * evict entries before their expiry time.
 */
public interface AttemptTrackingStore {

    AttemptState get(String namespace, String key);

    AttemptState compute(String namespace, String key, AttemptStateUpdater updater);

    void remove(String namespace, String key);

    interface AttemptStateUpdater {
        AttemptState update(AttemptState current);
    }

    final class AttemptState {
        private final int failureCount;
        private final long firstFailureTimestamp;
        private final long lastFailureTimestamp;
        private final long currentBlockUntil;

        public AttemptState(int failureCount, long firstFailureTimestamp, long lastFailureTimestamp, long currentBlockUntil) {
            this.failureCount = failureCount;
            this.firstFailureTimestamp = firstFailureTimestamp;
            this.lastFailureTimestamp = lastFailureTimestamp;
            this.currentBlockUntil = currentBlockUntil;
        }

        public int getFailureCount() {
            return failureCount;
        }

        public long getFirstFailureTimestamp() {
            return firstFailureTimestamp;
        }

        public long getLastFailureTimestamp() {
            return lastFailureTimestamp;
        }

        public long getCurrentBlockUntil() {
            return currentBlockUntil;
        }

        public boolean isBlocked(long now) {
            return currentBlockUntil > now;
        }
    }
}
