package koreatech.cse.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * In-process, bounded attempt tracking store backed by a ConcurrentHashMap.
 *
 * <h3>Restart behavior</h3>
 * All counters and lockout state are held in JVM heap. A JVM restart (application
 * redeploy, container restart, OS reboot) clears all state. This is intentional for
 * single-node deployments: a restart inherently disrupts any in-progress brute-force
 * attempt. Operators should be aware that a deliberate restart can be used to clear
 * lockouts — log the restart event at WARN level.
 *
 * <h3>Multi-node deployments</h3>
 * This implementation is NOT suitable for deployments behind a load balancer with
 * multiple application nodes. Each node maintains an independent counter, which means
 * an attacker alternating requests between N nodes receives N × MAX_ATTEMPTS attempts
 * before any single node triggers a lockout.
 *
 * <h3>Migration path</h3>
 * For multi-node deployments, replace this implementation with a Redis-backed store
 * implementing {@link AttemptTrackingStore}. Configure via:
 * {@code ams.rateLimit.store=redis} and {@code ams.rateLimit.redis.uri=redis://host:6379}.
 *
 * @see AttemptTrackingStore
 */
@Service
public class InMemoryAttemptTrackingStore implements AttemptTrackingStore {
    private static final int DEFAULT_MAX_ENTRIES_PER_NAMESPACE = 5_000;
    private static final long DEFAULT_STALE_ENTRY_RETENTION_MS = TimeUnit.HOURS.toMillis(24);

    private final Object monitor = new Object();
    private final Map<String, LinkedHashMap<String, AttemptState>> namespaces = new HashMap<>();

    private int maxEntriesPerNamespace = DEFAULT_MAX_ENTRIES_PER_NAMESPACE;
    private long staleEntryRetentionMs = DEFAULT_STALE_ENTRY_RETENTION_MS;

    @Override
    public AttemptState get(String namespace, String key) {
        synchronized (monitor) {
            LinkedHashMap<String, AttemptState> store = namespaces.get(namespace);
            if (store == null) {
                return null;
            }

            pruneNamespace(store);
            AttemptState state = store.get(key);
            removeNamespaceIfEmpty(namespace, store);
            return state;
        }
    }

    @Override
    public AttemptState compute(String namespace, String key, AttemptStateUpdater updater) {
        synchronized (monitor) {
            LinkedHashMap<String, AttemptState> store = namespaceStore(namespace);
            pruneNamespace(store);

            AttemptState current = store.get(key);
            AttemptState nextState = updater.update(current);
            if (nextState == null) {
                store.remove(key);
            } else {
                store.put(key, nextState);
            }

            pruneNamespace(store);
            removeNamespaceIfEmpty(namespace, store);
            return nextState;
        }
    }

    @Override
    public void remove(String namespace, String key) {
        synchronized (monitor) {
            LinkedHashMap<String, AttemptState> store = namespaces.get(namespace);
            if (store == null) {
                return;
            }

            store.remove(key);
            removeNamespaceIfEmpty(namespace, store);
        }
    }

    private LinkedHashMap<String, AttemptState> namespaceStore(String namespace) {
        LinkedHashMap<String, AttemptState> store = namespaces.get(namespace);
        if (store != null) {
            return store;
        }

        LinkedHashMap<String, AttemptState> createdStore = new LinkedHashMap<String, AttemptState>(16, 0.75f, true);
        namespaces.put(namespace, createdStore);
        return createdStore;
    }

    private void pruneNamespace(LinkedHashMap<String, AttemptState> store) {
        long now = System.currentTimeMillis();
        Iterator<Map.Entry<String, AttemptState>> iterator = store.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, AttemptState> entry = iterator.next();
            if (isStale(entry.getValue(), now)) {
                iterator.remove();
            }
        }

        while (store.size() > maxEntriesPerNamespace) {
            Iterator<Map.Entry<String, AttemptState>> eldestIterator = store.entrySet().iterator();
            if (!eldestIterator.hasNext()) {
                break;
            }
            eldestIterator.next();
            eldestIterator.remove();
        }
    }

    private boolean isStale(AttemptState state, long now) {
        if (state == null) {
            return true;
        }

        long lastRelevantTimestamp = Math.max(state.getLastFailureTimestamp(), state.getCurrentBlockUntil());
        return lastRelevantTimestamp + staleEntryRetentionMs < now;
    }

    private void removeNamespaceIfEmpty(String namespace, LinkedHashMap<String, AttemptState> store) {
        if (store.isEmpty()) {
            namespaces.remove(namespace);
        }
    }
}
