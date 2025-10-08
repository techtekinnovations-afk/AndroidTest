package com.google.common.cache;

import com.google.common.base.Equivalence;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import com.google.common.cache.AbstractCache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.AbstractSequentialIterator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractQueue;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;

class LocalCache<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V> {
    static final int CONTAINS_VALUE_RETRIES = 3;
    static final Queue<?> DISCARDING_QUEUE = new AbstractQueue<Object>() {
        public boolean offer(Object o) {
            return true;
        }

        @CheckForNull
        public Object peek() {
            return null;
        }

        @CheckForNull
        public Object poll() {
            return null;
        }

        public int size() {
            return 0;
        }

        public Iterator<Object> iterator() {
            return ImmutableSet.of().iterator();
        }
    };
    static final int DRAIN_MAX = 16;
    static final int DRAIN_THRESHOLD = 63;
    static final int MAXIMUM_CAPACITY = 1073741824;
    static final int MAX_SEGMENTS = 65536;
    static final ValueReference<Object, Object> UNSET = new ValueReference<Object, Object>() {
        @CheckForNull
        public Object get() {
            return null;
        }

        public int getWeight() {
            return 0;
        }

        @CheckForNull
        public ReferenceEntry<Object, Object> getEntry() {
            return null;
        }

        public ValueReference<Object, Object> copyFor(ReferenceQueue<Object> referenceQueue, @CheckForNull Object value, ReferenceEntry<Object, Object> referenceEntry) {
            return this;
        }

        public boolean isLoading() {
            return false;
        }

        public boolean isActive() {
            return false;
        }

        @CheckForNull
        public Object waitForValue() {
            return null;
        }

        public void notifyNewValue(Object newValue) {
        }
    };
    static final Logger logger = Logger.getLogger(LocalCache.class.getName());
    final int concurrencyLevel;
    @CheckForNull
    final CacheLoader<? super K, V> defaultLoader;
    final EntryFactory entryFactory;
    @CheckForNull
    @LazyInit
    Set<Map.Entry<K, V>> entrySet;
    final long expireAfterAccessNanos;
    final long expireAfterWriteNanos;
    final AbstractCache.StatsCounter globalStatsCounter;
    final Equivalence<Object> keyEquivalence;
    @CheckForNull
    @LazyInit
    Set<K> keySet;
    final Strength keyStrength;
    final long maxWeight;
    final long refreshNanos;
    final RemovalListener<K, V> removalListener;
    final Queue<RemovalNotification<K, V>> removalNotificationQueue;
    final int segmentMask;
    final int segmentShift;
    final Segment<K, V>[] segments;
    final Ticker ticker;
    final Equivalence<Object> valueEquivalence;
    final Strength valueStrength;
    @CheckForNull
    @LazyInit
    Collection<V> values;
    final Weigher<K, V> weigher;

    enum Strength {
        STRONG {
            /* access modifiers changed from: package-private */
            public <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V value, int weight) {
                if (weight == 1) {
                    return new StrongValueReference(value);
                }
                return new WeightedStrongValueReference(value, weight);
            }

            /* access modifiers changed from: package-private */
            public Equivalence<Object> defaultEquivalence() {
                return Equivalence.equals();
            }
        },
        SOFT {
            /* access modifiers changed from: package-private */
            public <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> entry, V value, int weight) {
                if (weight == 1) {
                    return new SoftValueReference(segment.valueReferenceQueue, value, entry);
                }
                return new WeightedSoftValueReference(segment.valueReferenceQueue, value, entry, weight);
            }

            /* access modifiers changed from: package-private */
            public Equivalence<Object> defaultEquivalence() {
                return Equivalence.identity();
            }
        },
        WEAK {
            /* access modifiers changed from: package-private */
            public <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> entry, V value, int weight) {
                if (weight == 1) {
                    return new WeakValueReference(segment.valueReferenceQueue, value, entry);
                }
                return new WeightedWeakValueReference(segment.valueReferenceQueue, value, entry, weight);
            }

            /* access modifiers changed from: package-private */
            public Equivalence<Object> defaultEquivalence() {
                return Equivalence.identity();
            }
        };

        /* access modifiers changed from: package-private */
        public abstract Equivalence<Object> defaultEquivalence();

        /* access modifiers changed from: package-private */
        public abstract <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> referenceEntry, V v, int i);
    }

    interface ValueReference<K, V> {
        ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, @CheckForNull V v, ReferenceEntry<K, V> referenceEntry);

        @CheckForNull
        V get();

        @CheckForNull
        ReferenceEntry<K, V> getEntry();

        int getWeight();

        boolean isActive();

        boolean isLoading();

        void notifyNewValue(@CheckForNull V v);

        V waitForValue() throws ExecutionException;
    }

    LocalCache(CacheBuilder<? super K, ? super V> builder, @CheckForNull CacheLoader<? super K, V> loader) {
        Queue<RemovalNotification<K, V>> queue;
        this.concurrencyLevel = Math.min(builder.getConcurrencyLevel(), 65536);
        this.keyStrength = builder.getKeyStrength();
        this.valueStrength = builder.getValueStrength();
        this.keyEquivalence = builder.getKeyEquivalence();
        this.valueEquivalence = builder.getValueEquivalence();
        this.maxWeight = builder.getMaximumWeight();
        this.weigher = builder.getWeigher();
        this.expireAfterAccessNanos = builder.getExpireAfterAccessNanos();
        this.expireAfterWriteNanos = builder.getExpireAfterWriteNanos();
        this.refreshNanos = builder.getRefreshNanos();
        this.removalListener = builder.getRemovalListener();
        if (this.removalListener == CacheBuilder.NullListener.INSTANCE) {
            queue = discardingQueue();
        } else {
            queue = new ConcurrentLinkedQueue<>();
        }
        this.removalNotificationQueue = queue;
        CacheBuilder<? super K, ? super V> cacheBuilder = builder;
        this.ticker = cacheBuilder.getTicker(recordsTime());
        this.entryFactory = EntryFactory.getFactory(this.keyStrength, usesAccessEntries(), usesWriteEntries());
        this.globalStatsCounter = (AbstractCache.StatsCounter) cacheBuilder.getStatsCounterSupplier().get();
        this.defaultLoader = loader;
        int initialCapacity = Math.min(cacheBuilder.getInitialCapacity(), 1073741824);
        if (evictsBySize() && !customWeigher()) {
            initialCapacity = (int) Math.min((long) initialCapacity, this.maxWeight);
        }
        int segmentShift2 = 0;
        int segmentCount = 1;
        while (segmentCount < this.concurrencyLevel && (!evictsBySize() || ((long) segmentCount) * 20 <= this.maxWeight)) {
            segmentShift2++;
            segmentCount <<= 1;
        }
        this.segmentShift = 32 - segmentShift2;
        this.segmentMask = segmentCount - 1;
        this.segments = newSegmentArray(segmentCount);
        int segmentCapacity = initialCapacity / segmentCount;
        int segmentSize = 1;
        while (segmentSize < (segmentCapacity * segmentCount < initialCapacity ? segmentCapacity + 1 : segmentCapacity)) {
            segmentSize <<= 1;
        }
        if (evictsBySize()) {
            long j = 1;
            long maxSegmentWeight = (this.maxWeight / ((long) segmentCount)) + 1;
            long remainder = this.maxWeight % ((long) segmentCount);
            int i = 0;
            while (i < this.segments.length) {
                long j2 = j;
                if (((long) i) == remainder) {
                    maxSegmentWeight -= j2;
                }
                this.segments[i] = createSegment(segmentSize, maxSegmentWeight, (AbstractCache.StatsCounter) cacheBuilder.getStatsCounterSupplier().get());
                i++;
                j = j2;
            }
            return;
        }
        for (int i2 = 0; i2 < this.segments.length; i2++) {
            this.segments[i2] = createSegment(segmentSize, -1, (AbstractCache.StatsCounter) cacheBuilder.getStatsCounterSupplier().get());
        }
    }

    /* access modifiers changed from: package-private */
    public boolean evictsBySize() {
        return this.maxWeight >= 0;
    }

    /* access modifiers changed from: package-private */
    public boolean customWeigher() {
        return this.weigher != CacheBuilder.OneWeigher.INSTANCE;
    }

    /* access modifiers changed from: package-private */
    public boolean expires() {
        return expiresAfterWrite() || expiresAfterAccess();
    }

    /* access modifiers changed from: package-private */
    public boolean expiresAfterWrite() {
        return this.expireAfterWriteNanos > 0;
    }

    /* access modifiers changed from: package-private */
    public boolean expiresAfterAccess() {
        return this.expireAfterAccessNanos > 0;
    }

    /* access modifiers changed from: package-private */
    public boolean refreshes() {
        return this.refreshNanos > 0;
    }

    /* access modifiers changed from: package-private */
    public boolean usesAccessQueue() {
        return expiresAfterAccess() || evictsBySize();
    }

    /* access modifiers changed from: package-private */
    public boolean usesWriteQueue() {
        return expiresAfterWrite();
    }

    /* access modifiers changed from: package-private */
    public boolean recordsWrite() {
        return expiresAfterWrite() || refreshes();
    }

    /* access modifiers changed from: package-private */
    public boolean recordsAccess() {
        return expiresAfterAccess();
    }

    /* access modifiers changed from: package-private */
    public boolean recordsTime() {
        return recordsWrite() || recordsAccess();
    }

    /* access modifiers changed from: package-private */
    public boolean usesWriteEntries() {
        return usesWriteQueue() || recordsWrite();
    }

    /* access modifiers changed from: package-private */
    public boolean usesAccessEntries() {
        return usesAccessQueue() || recordsAccess();
    }

    /* access modifiers changed from: package-private */
    public boolean usesKeyReferences() {
        return this.keyStrength != Strength.STRONG;
    }

    /* access modifiers changed from: package-private */
    public boolean usesValueReferences() {
        return this.valueStrength != Strength.STRONG;
    }

    enum EntryFactory {
        STRONG {
            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, @CheckForNull ReferenceEntry<K, V> next) {
                return new StrongEntry(key, hash, next);
            }
        },
        STRONG_ACCESS {
            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, @CheckForNull ReferenceEntry<K, V> next) {
                return new StrongAccessEntry(key, hash, next);
            }

            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext, K key) {
                ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext, key);
                copyAccessEntry(original, newEntry);
                return newEntry;
            }
        },
        STRONG_WRITE {
            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, @CheckForNull ReferenceEntry<K, V> next) {
                return new StrongWriteEntry(key, hash, next);
            }

            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext, K key) {
                ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext, key);
                copyWriteEntry(original, newEntry);
                return newEntry;
            }
        },
        STRONG_ACCESS_WRITE {
            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, @CheckForNull ReferenceEntry<K, V> next) {
                return new StrongAccessWriteEntry(key, hash, next);
            }

            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext, K key) {
                ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext, key);
                copyAccessEntry(original, newEntry);
                copyWriteEntry(original, newEntry);
                return newEntry;
            }
        },
        WEAK {
            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, @CheckForNull ReferenceEntry<K, V> next) {
                return new WeakEntry(segment.keyReferenceQueue, key, hash, next);
            }
        },
        WEAK_ACCESS {
            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, @CheckForNull ReferenceEntry<K, V> next) {
                return new WeakAccessEntry(segment.keyReferenceQueue, key, hash, next);
            }

            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext, K key) {
                ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext, key);
                copyAccessEntry(original, newEntry);
                return newEntry;
            }
        },
        WEAK_WRITE {
            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, @CheckForNull ReferenceEntry<K, V> next) {
                return new WeakWriteEntry(segment.keyReferenceQueue, key, hash, next);
            }

            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext, K key) {
                ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext, key);
                copyWriteEntry(original, newEntry);
                return newEntry;
            }
        },
        WEAK_ACCESS_WRITE {
            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, @CheckForNull ReferenceEntry<K, V> next) {
                return new WeakAccessWriteEntry(segment.keyReferenceQueue, key, hash, next);
            }

            /* access modifiers changed from: package-private */
            public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext, K key) {
                ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext, key);
                copyAccessEntry(original, newEntry);
                copyWriteEntry(original, newEntry);
                return newEntry;
            }
        };
        
        static final int ACCESS_MASK = 1;
        static final int WEAK_MASK = 4;
        static final int WRITE_MASK = 2;
        static final EntryFactory[] factories = null;

        /* access modifiers changed from: package-private */
        public abstract <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K k, int i, @CheckForNull ReferenceEntry<K, V> referenceEntry);

        static {
            factories = new EntryFactory[]{STRONG, STRONG_ACCESS, STRONG_WRITE, STRONG_ACCESS_WRITE, WEAK, WEAK_ACCESS, WEAK_WRITE, WEAK_ACCESS_WRITE};
        }

        static EntryFactory getFactory(Strength keyStrength, boolean usesAccessQueue, boolean usesWriteQueue) {
            int i = 0;
            int i2 = (keyStrength == Strength.WEAK ? 4 : 0) | usesAccessQueue;
            if (usesWriteQueue) {
                i = 2;
            }
            return factories[i2 | i];
        }

        /* access modifiers changed from: package-private */
        public <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext, K key) {
            return newEntry(segment, key, original.getHash(), newNext);
        }

        /* access modifiers changed from: package-private */
        public <K, V> void copyAccessEntry(ReferenceEntry<K, V> original, ReferenceEntry<K, V> newEntry) {
            newEntry.setAccessTime(original.getAccessTime());
            LocalCache.connectAccessOrder(original.getPreviousInAccessQueue(), newEntry);
            LocalCache.connectAccessOrder(newEntry, original.getNextInAccessQueue());
            LocalCache.nullifyAccessOrder(original);
        }

        /* access modifiers changed from: package-private */
        public <K, V> void copyWriteEntry(ReferenceEntry<K, V> original, ReferenceEntry<K, V> newEntry) {
            newEntry.setWriteTime(original.getWriteTime());
            LocalCache.connectWriteOrder(original.getPreviousInWriteQueue(), newEntry);
            LocalCache.connectWriteOrder(newEntry, original.getNextInWriteQueue());
            LocalCache.nullifyWriteOrder(original);
        }
    }

    static <K, V> ValueReference<K, V> unset() {
        return UNSET;
    }

    private enum NullEntry implements ReferenceEntry<Object, Object> {
        INSTANCE;

        @CheckForNull
        public ValueReference<Object, Object> getValueReference() {
            return null;
        }

        public void setValueReference(ValueReference<Object, Object> valueReference) {
        }

        @CheckForNull
        public ReferenceEntry<Object, Object> getNext() {
            return null;
        }

        public int getHash() {
            return 0;
        }

        @CheckForNull
        public Object getKey() {
            return null;
        }

        public long getAccessTime() {
            return 0;
        }

        public void setAccessTime(long time) {
        }

        public ReferenceEntry<Object, Object> getNextInAccessQueue() {
            return this;
        }

        public void setNextInAccessQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        public ReferenceEntry<Object, Object> getPreviousInAccessQueue() {
            return this;
        }

        public void setPreviousInAccessQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        public long getWriteTime() {
            return 0;
        }

        public void setWriteTime(long time) {
        }

        public ReferenceEntry<Object, Object> getNextInWriteQueue() {
            return this;
        }

        public void setNextInWriteQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }

        public ReferenceEntry<Object, Object> getPreviousInWriteQueue() {
            return this;
        }

        public void setPreviousInWriteQueue(ReferenceEntry<Object, Object> referenceEntry) {
        }
    }

    static abstract class AbstractReferenceEntry<K, V> implements ReferenceEntry<K, V> {
        AbstractReferenceEntry() {
        }

        public ValueReference<K, V> getValueReference() {
            throw new UnsupportedOperationException();
        }

        public void setValueReference(ValueReference<K, V> valueReference) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getNext() {
            throw new UnsupportedOperationException();
        }

        public int getHash() {
            throw new UnsupportedOperationException();
        }

        public K getKey() {
            throw new UnsupportedOperationException();
        }

        public long getAccessTime() {
            throw new UnsupportedOperationException();
        }

        public void setAccessTime(long time) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getNextInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public long getWriteTime() {
            throw new UnsupportedOperationException();
        }

        public void setWriteTime(long time) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getNextInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }
    }

    static <K, V> ReferenceEntry<K, V> nullEntry() {
        return NullEntry.INSTANCE;
    }

    static <E> Queue<E> discardingQueue() {
        return DISCARDING_QUEUE;
    }

    static class StrongEntry<K, V> extends AbstractReferenceEntry<K, V> {
        final int hash;
        final K key;
        @CheckForNull
        final ReferenceEntry<K, V> next;
        volatile ValueReference<K, V> valueReference = LocalCache.unset();

        StrongEntry(K key2, int hash2, @CheckForNull ReferenceEntry<K, V> next2) {
            this.key = key2;
            this.hash = hash2;
            this.next = next2;
        }

        public K getKey() {
            return this.key;
        }

        public ValueReference<K, V> getValueReference() {
            return this.valueReference;
        }

        public void setValueReference(ValueReference<K, V> valueReference2) {
            this.valueReference = valueReference2;
        }

        public int getHash() {
            return this.hash;
        }

        public ReferenceEntry<K, V> getNext() {
            return this.next;
        }
    }

    static final class StrongAccessEntry<K, V> extends StrongEntry<K, V> {
        volatile long accessTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();

        StrongAccessEntry(K key, int hash, @CheckForNull ReferenceEntry<K, V> next) {
            super(key, hash, next);
        }

        public long getAccessTime() {
            return this.accessTime;
        }

        public void setAccessTime(long time) {
            this.accessTime = time;
        }

        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        public void setNextInAccessQueue(ReferenceEntry<K, V> next) {
            this.nextAccess = next;
        }

        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> previous) {
            this.previousAccess = previous;
        }
    }

    static final class StrongWriteEntry<K, V> extends StrongEntry<K, V> {
        ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
        volatile long writeTime = Long.MAX_VALUE;

        StrongWriteEntry(K key, int hash, @CheckForNull ReferenceEntry<K, V> next) {
            super(key, hash, next);
        }

        public long getWriteTime() {
            return this.writeTime;
        }

        public void setWriteTime(long time) {
            this.writeTime = time;
        }

        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        public void setNextInWriteQueue(ReferenceEntry<K, V> next) {
            this.nextWrite = next;
        }

        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> previous) {
            this.previousWrite = previous;
        }
    }

    static final class StrongAccessWriteEntry<K, V> extends StrongEntry<K, V> {
        volatile long accessTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
        volatile long writeTime = Long.MAX_VALUE;

        StrongAccessWriteEntry(K key, int hash, @CheckForNull ReferenceEntry<K, V> next) {
            super(key, hash, next);
        }

        public long getAccessTime() {
            return this.accessTime;
        }

        public void setAccessTime(long time) {
            this.accessTime = time;
        }

        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        public void setNextInAccessQueue(ReferenceEntry<K, V> next) {
            this.nextAccess = next;
        }

        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> previous) {
            this.previousAccess = previous;
        }

        public long getWriteTime() {
            return this.writeTime;
        }

        public void setWriteTime(long time) {
            this.writeTime = time;
        }

        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        public void setNextInWriteQueue(ReferenceEntry<K, V> next) {
            this.nextWrite = next;
        }

        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> previous) {
            this.previousWrite = previous;
        }
    }

    static class WeakEntry<K, V> extends WeakReference<K> implements ReferenceEntry<K, V> {
        final int hash;
        @CheckForNull
        final ReferenceEntry<K, V> next;
        volatile ValueReference<K, V> valueReference = LocalCache.unset();

        WeakEntry(ReferenceQueue<K> queue, K key, int hash2, @CheckForNull ReferenceEntry<K, V> next2) {
            super(key, queue);
            this.hash = hash2;
            this.next = next2;
        }

        public K getKey() {
            return get();
        }

        public long getAccessTime() {
            throw new UnsupportedOperationException();
        }

        public void setAccessTime(long time) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getNextInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        public void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public long getWriteTime() {
            throw new UnsupportedOperationException();
        }

        public void setWriteTime(long time) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getNextInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        public void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry) {
            throw new UnsupportedOperationException();
        }

        public ValueReference<K, V> getValueReference() {
            return this.valueReference;
        }

        public void setValueReference(ValueReference<K, V> valueReference2) {
            this.valueReference = valueReference2;
        }

        public int getHash() {
            return this.hash;
        }

        public ReferenceEntry<K, V> getNext() {
            return this.next;
        }
    }

    static final class WeakAccessEntry<K, V> extends WeakEntry<K, V> {
        volatile long accessTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();

        WeakAccessEntry(ReferenceQueue<K> queue, K key, int hash, @CheckForNull ReferenceEntry<K, V> next) {
            super(queue, key, hash, next);
        }

        public long getAccessTime() {
            return this.accessTime;
        }

        public void setAccessTime(long time) {
            this.accessTime = time;
        }

        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        public void setNextInAccessQueue(ReferenceEntry<K, V> next) {
            this.nextAccess = next;
        }

        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> previous) {
            this.previousAccess = previous;
        }
    }

    static final class WeakWriteEntry<K, V> extends WeakEntry<K, V> {
        ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
        volatile long writeTime = Long.MAX_VALUE;

        WeakWriteEntry(ReferenceQueue<K> queue, K key, int hash, @CheckForNull ReferenceEntry<K, V> next) {
            super(queue, key, hash, next);
        }

        public long getWriteTime() {
            return this.writeTime;
        }

        public void setWriteTime(long time) {
            this.writeTime = time;
        }

        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        public void setNextInWriteQueue(ReferenceEntry<K, V> next) {
            this.nextWrite = next;
        }

        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> previous) {
            this.previousWrite = previous;
        }
    }

    static final class WeakAccessWriteEntry<K, V> extends WeakEntry<K, V> {
        volatile long accessTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();
        ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
        volatile long writeTime = Long.MAX_VALUE;

        WeakAccessWriteEntry(ReferenceQueue<K> queue, K key, int hash, @CheckForNull ReferenceEntry<K, V> next) {
            super(queue, key, hash, next);
        }

        public long getAccessTime() {
            return this.accessTime;
        }

        public void setAccessTime(long time) {
            this.accessTime = time;
        }

        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        public void setNextInAccessQueue(ReferenceEntry<K, V> next) {
            this.nextAccess = next;
        }

        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> previous) {
            this.previousAccess = previous;
        }

        public long getWriteTime() {
            return this.writeTime;
        }

        public void setWriteTime(long time) {
            this.writeTime = time;
        }

        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        public void setNextInWriteQueue(ReferenceEntry<K, V> next) {
            this.nextWrite = next;
        }

        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> previous) {
            this.previousWrite = previous;
        }
    }

    static class WeakValueReference<K, V> extends WeakReference<V> implements ValueReference<K, V> {
        final ReferenceEntry<K, V> entry;

        WeakValueReference(ReferenceQueue<V> queue, V referent, ReferenceEntry<K, V> entry2) {
            super(referent, queue);
            this.entry = entry2;
        }

        public int getWeight() {
            return 1;
        }

        public ReferenceEntry<K, V> getEntry() {
            return this.entry;
        }

        public void notifyNewValue(V v) {
        }

        public ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, ReferenceEntry<K, V> entry2) {
            return new WeakValueReference(queue, value, entry2);
        }

        public boolean isLoading() {
            return false;
        }

        public boolean isActive() {
            return true;
        }

        public V waitForValue() {
            return get();
        }
    }

    static class SoftValueReference<K, V> extends SoftReference<V> implements ValueReference<K, V> {
        final ReferenceEntry<K, V> entry;

        SoftValueReference(ReferenceQueue<V> queue, V referent, ReferenceEntry<K, V> entry2) {
            super(referent, queue);
            this.entry = entry2;
        }

        public int getWeight() {
            return 1;
        }

        public ReferenceEntry<K, V> getEntry() {
            return this.entry;
        }

        public void notifyNewValue(V v) {
        }

        public ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, ReferenceEntry<K, V> entry2) {
            return new SoftValueReference(queue, value, entry2);
        }

        public boolean isLoading() {
            return false;
        }

        public boolean isActive() {
            return true;
        }

        public V waitForValue() {
            return get();
        }
    }

    static class StrongValueReference<K, V> implements ValueReference<K, V> {
        final V referent;

        StrongValueReference(V referent2) {
            this.referent = referent2;
        }

        public V get() {
            return this.referent;
        }

        public int getWeight() {
            return 1;
        }

        public ReferenceEntry<K, V> getEntry() {
            return null;
        }

        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, V v, ReferenceEntry<K, V> referenceEntry) {
            return this;
        }

        public boolean isLoading() {
            return false;
        }

        public boolean isActive() {
            return true;
        }

        public V waitForValue() {
            return get();
        }

        public void notifyNewValue(V v) {
        }
    }

    static final class WeightedWeakValueReference<K, V> extends WeakValueReference<K, V> {
        final int weight;

        WeightedWeakValueReference(ReferenceQueue<V> queue, V referent, ReferenceEntry<K, V> entry, int weight2) {
            super(queue, referent, entry);
            this.weight = weight2;
        }

        public int getWeight() {
            return this.weight;
        }

        public ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, ReferenceEntry<K, V> entry) {
            return new WeightedWeakValueReference(queue, value, entry, this.weight);
        }
    }

    static final class WeightedSoftValueReference<K, V> extends SoftValueReference<K, V> {
        final int weight;

        WeightedSoftValueReference(ReferenceQueue<V> queue, V referent, ReferenceEntry<K, V> entry, int weight2) {
            super(queue, referent, entry);
            this.weight = weight2;
        }

        public int getWeight() {
            return this.weight;
        }

        public ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, ReferenceEntry<K, V> entry) {
            return new WeightedSoftValueReference(queue, value, entry, this.weight);
        }
    }

    static final class WeightedStrongValueReference<K, V> extends StrongValueReference<K, V> {
        final int weight;

        WeightedStrongValueReference(V referent, int weight2) {
            super(referent);
            this.weight = weight2;
        }

        public int getWeight() {
            return this.weight;
        }
    }

    static int rehash(int h) {
        int h2 = h + ((h << 15) ^ -12931);
        int h3 = h2 ^ (h2 >>> 10);
        int h4 = h3 + (h3 << 3);
        int h5 = h4 ^ (h4 >>> 6);
        int h6 = h5 + (h5 << 2) + (h5 << 14);
        return (h6 >>> 16) ^ h6;
    }

    /* access modifiers changed from: package-private */
    public ReferenceEntry<K, V> newEntry(K key, int hash, @CheckForNull ReferenceEntry<K, V> next) {
        Segment<K, V> segment = segmentFor(hash);
        segment.lock();
        try {
            return segment.newEntry(key, hash, next);
        } finally {
            segment.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
        return segmentFor(original.getHash()).copyEntry(original, newNext);
    }

    /* access modifiers changed from: package-private */
    public ValueReference<K, V> newValueReference(ReferenceEntry<K, V> entry, V value, int weight) {
        return this.valueStrength.referenceValue(segmentFor(entry.getHash()), entry, Preconditions.checkNotNull(value), weight);
    }

    /* access modifiers changed from: package-private */
    public int hash(@CheckForNull Object key) {
        return rehash(this.keyEquivalence.hash(key));
    }

    /* access modifiers changed from: package-private */
    public void reclaimValue(ValueReference<K, V> valueReference) {
        ReferenceEntry<K, V> entry = valueReference.getEntry();
        int hash = entry.getHash();
        segmentFor(hash).reclaimValue(entry.getKey(), hash, valueReference);
    }

    /* access modifiers changed from: package-private */
    public void reclaimKey(ReferenceEntry<K, V> entry) {
        int hash = entry.getHash();
        segmentFor(hash).reclaimKey(entry, hash);
    }

    /* access modifiers changed from: package-private */
    public boolean isLive(ReferenceEntry<K, V> entry, long now) {
        return segmentFor(entry.getHash()).getLiveValue(entry, now) != null;
    }

    /* access modifiers changed from: package-private */
    public Segment<K, V> segmentFor(int hash) {
        return this.segments[(hash >>> this.segmentShift) & this.segmentMask];
    }

    /* access modifiers changed from: package-private */
    public Segment<K, V> createSegment(int initialCapacity, long maxSegmentWeight, AbstractCache.StatsCounter statsCounter) {
        return new Segment<>(this, initialCapacity, maxSegmentWeight, statsCounter);
    }

    /* access modifiers changed from: package-private */
    @CheckForNull
    public V getLiveValue(ReferenceEntry<K, V> entry, long now) {
        V value;
        if (entry.getKey() == null || (value = entry.getValueReference().get()) == null || isExpired(entry, now)) {
            return null;
        }
        return value;
    }

    /* access modifiers changed from: package-private */
    public boolean isExpired(ReferenceEntry<K, V> entry, long now) {
        Preconditions.checkNotNull(entry);
        if (expiresAfterAccess() && now - entry.getAccessTime() >= this.expireAfterAccessNanos) {
            return true;
        }
        if (!expiresAfterWrite() || now - entry.getWriteTime() < this.expireAfterWriteNanos) {
            return false;
        }
        return true;
    }

    static <K, V> void connectAccessOrder(ReferenceEntry<K, V> previous, ReferenceEntry<K, V> next) {
        previous.setNextInAccessQueue(next);
        next.setPreviousInAccessQueue(previous);
    }

    static <K, V> void nullifyAccessOrder(ReferenceEntry<K, V> nulled) {
        ReferenceEntry<K, V> nullEntry = nullEntry();
        nulled.setNextInAccessQueue(nullEntry);
        nulled.setPreviousInAccessQueue(nullEntry);
    }

    static <K, V> void connectWriteOrder(ReferenceEntry<K, V> previous, ReferenceEntry<K, V> next) {
        previous.setNextInWriteQueue(next);
        next.setPreviousInWriteQueue(previous);
    }

    static <K, V> void nullifyWriteOrder(ReferenceEntry<K, V> nulled) {
        ReferenceEntry<K, V> nullEntry = nullEntry();
        nulled.setNextInWriteQueue(nullEntry);
        nulled.setPreviousInWriteQueue(nullEntry);
    }

    /* access modifiers changed from: package-private */
    public void processPendingNotifications() {
        while (true) {
            RemovalNotification<K, V> poll = this.removalNotificationQueue.poll();
            RemovalNotification<K, V> notification = poll;
            if (poll != null) {
                try {
                    this.removalListener.onRemoval(notification);
                } catch (Throwable e) {
                    logger.log(Level.WARNING, "Exception thrown by removal listener", e);
                }
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final Segment<K, V>[] newSegmentArray(int ssize) {
        return new Segment[ssize];
    }

    static class Segment<K, V> extends ReentrantLock {
        final Queue<ReferenceEntry<K, V>> accessQueue;
        volatile int count;
        @CheckForNull
        final ReferenceQueue<K> keyReferenceQueue;
        final LocalCache<K, V> map;
        final long maxSegmentWeight;
        int modCount;
        final AtomicInteger readCount = new AtomicInteger();
        final Queue<ReferenceEntry<K, V>> recencyQueue;
        final AbstractCache.StatsCounter statsCounter;
        @CheckForNull
        volatile AtomicReferenceArray<ReferenceEntry<K, V>> table;
        int threshold;
        long totalWeight;
        @CheckForNull
        final ReferenceQueue<V> valueReferenceQueue;
        final Queue<ReferenceEntry<K, V>> writeQueue;

        Segment(LocalCache<K, V> map2, int initialCapacity, long maxSegmentWeight2, AbstractCache.StatsCounter statsCounter2) {
            this.map = map2;
            this.maxSegmentWeight = maxSegmentWeight2;
            this.statsCounter = (AbstractCache.StatsCounter) Preconditions.checkNotNull(statsCounter2);
            initTable(newEntryArray(initialCapacity));
            ReferenceQueue<V> referenceQueue = null;
            this.keyReferenceQueue = map2.usesKeyReferences() ? new ReferenceQueue<>() : null;
            this.valueReferenceQueue = map2.usesValueReferences() ? new ReferenceQueue<>() : referenceQueue;
            this.recencyQueue = map2.usesAccessQueue() ? new ConcurrentLinkedQueue<>() : LocalCache.discardingQueue();
            this.writeQueue = map2.usesWriteQueue() ? new WriteQueue<>() : LocalCache.discardingQueue();
            this.accessQueue = map2.usesAccessQueue() ? new AccessQueue<>() : LocalCache.discardingQueue();
        }

        /* access modifiers changed from: package-private */
        public AtomicReferenceArray<ReferenceEntry<K, V>> newEntryArray(int size) {
            return new AtomicReferenceArray<>(size);
        }

        /* access modifiers changed from: package-private */
        public void initTable(AtomicReferenceArray<ReferenceEntry<K, V>> newTable) {
            this.threshold = (newTable.length() * 3) / 4;
            if (!this.map.customWeigher() && ((long) this.threshold) == this.maxSegmentWeight) {
                this.threshold++;
            }
            this.table = newTable;
        }

        /* access modifiers changed from: package-private */
        public ReferenceEntry<K, V> newEntry(K key, int hash, @CheckForNull ReferenceEntry<K, V> next) {
            return this.map.entryFactory.newEntry(this, Preconditions.checkNotNull(key), hash, next);
        }

        /* access modifiers changed from: package-private */
        @CheckForNull
        public ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
            K key = original.getKey();
            if (key == null) {
                return null;
            }
            ValueReference<K, V> valueReference = original.getValueReference();
            V value = valueReference.get();
            if (value == null && valueReference.isActive()) {
                return null;
            }
            ReferenceEntry<K, V> newEntry = this.map.entryFactory.copyEntry(this, original, newNext, key);
            newEntry.setValueReference(valueReference.copyFor(this.valueReferenceQueue, value, newEntry));
            return newEntry;
        }

        /* access modifiers changed from: package-private */
        public void setValue(ReferenceEntry<K, V> entry, K key, V value, long now) {
            ValueReference<K, V> previous = entry.getValueReference();
            int weight = this.map.weigher.weigh(key, value);
            Preconditions.checkState(weight >= 0, "Weights must be non-negative");
            entry.setValueReference(this.map.valueStrength.referenceValue(this, entry, value, weight));
            recordWrite(entry, weight, now);
            previous.notifyNewValue(value);
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Removed duplicated region for block: B:36:0x0073 A[Catch:{ all -> 0x0088 }] */
        /* JADX WARNING: Removed duplicated region for block: B:41:0x007f A[Catch:{ all -> 0x0088 }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public V get(K r10, int r11, com.google.common.cache.CacheLoader<? super K, V> r12) throws java.util.concurrent.ExecutionException {
            /*
                r9 = this;
                com.google.common.base.Preconditions.checkNotNull(r10)
                com.google.common.base.Preconditions.checkNotNull(r12)
                int r0 = r9.count     // Catch:{ ExecutionException -> 0x0065, all -> 0x005e }
                if (r0 == 0) goto L_0x004f
                com.google.common.cache.ReferenceEntry r0 = r9.getEntry(r10, r11)     // Catch:{ ExecutionException -> 0x0065, all -> 0x005e }
                r2 = r0
                if (r2 == 0) goto L_0x004a
                com.google.common.cache.LocalCache<K, V> r0 = r9.map     // Catch:{ ExecutionException -> 0x0065, all -> 0x005e }
                com.google.common.base.Ticker r0 = r0.ticker     // Catch:{ ExecutionException -> 0x0065, all -> 0x005e }
                long r6 = r0.read()     // Catch:{ ExecutionException -> 0x0065, all -> 0x005e }
                java.lang.Object r5 = r9.getLiveValue(r2, r6)     // Catch:{ ExecutionException -> 0x0065, all -> 0x005e }
                if (r5 == 0) goto L_0x0034
                r9.recordRead(r2, r6)     // Catch:{ ExecutionException -> 0x0065, all -> 0x005e }
                com.google.common.cache.AbstractCache$StatsCounter r0 = r9.statsCounter     // Catch:{ ExecutionException -> 0x0065, all -> 0x005e }
                r1 = 1
                r0.recordHits(r1)     // Catch:{ ExecutionException -> 0x0065, all -> 0x005e }
                r1 = r9
                r3 = r10
                r4 = r11
                r8 = r12
                java.lang.Object r10 = r1.scheduleRefresh(r2, r3, r4, r5, r6, r8)     // Catch:{ ExecutionException -> 0x005b }
                r9.postReadCleanup()
                return r10
            L_0x0034:
                r1 = r9
                r3 = r10
                r4 = r11
                r8 = r12
                com.google.common.cache.LocalCache$ValueReference r10 = r2.getValueReference()     // Catch:{ ExecutionException -> 0x005b }
                boolean r11 = r10.isLoading()     // Catch:{ ExecutionException -> 0x005b }
                if (r11 == 0) goto L_0x0053
                java.lang.Object r11 = r9.waitForLoadingValue(r2, r3, r10)     // Catch:{ ExecutionException -> 0x005b }
                r9.postReadCleanup()
                return r11
            L_0x004a:
                r1 = r9
                r3 = r10
                r4 = r11
                r8 = r12
                goto L_0x0053
            L_0x004f:
                r1 = r9
                r3 = r10
                r4 = r11
                r8 = r12
            L_0x0053:
                java.lang.Object r10 = r9.lockedGetOrLoad(r3, r4, r8)     // Catch:{ ExecutionException -> 0x005b }
                r9.postReadCleanup()
                return r10
            L_0x005b:
                r0 = move-exception
                r10 = r0
                goto L_0x006b
            L_0x005e:
                r0 = move-exception
                r1 = r9
                r3 = r10
                r4 = r11
                r8 = r12
                r10 = r0
                goto L_0x008a
            L_0x0065:
                r0 = move-exception
                r1 = r9
                r3 = r10
                r4 = r11
                r8 = r12
                r10 = r0
            L_0x006b:
                java.lang.Throwable r11 = r10.getCause()     // Catch:{ all -> 0x0088 }
                boolean r12 = r11 instanceof java.lang.Error     // Catch:{ all -> 0x0088 }
                if (r12 != 0) goto L_0x007f
                boolean r12 = r11 instanceof java.lang.RuntimeException     // Catch:{ all -> 0x0088 }
                if (r12 == 0) goto L_0x007d
                com.google.common.util.concurrent.UncheckedExecutionException r12 = new com.google.common.util.concurrent.UncheckedExecutionException     // Catch:{ all -> 0x0088 }
                r12.<init>((java.lang.Throwable) r11)     // Catch:{ all -> 0x0088 }
                throw r12     // Catch:{ all -> 0x0088 }
            L_0x007d:
                throw r10     // Catch:{ all -> 0x0088 }
            L_0x007f:
                com.google.common.util.concurrent.ExecutionError r12 = new com.google.common.util.concurrent.ExecutionError     // Catch:{ all -> 0x0088 }
                r0 = r11
                java.lang.Error r0 = (java.lang.Error) r0     // Catch:{ all -> 0x0088 }
                r12.<init>((java.lang.Error) r0)     // Catch:{ all -> 0x0088 }
                throw r12     // Catch:{ all -> 0x0088 }
            L_0x0088:
                r0 = move-exception
                r10 = r0
            L_0x008a:
                r9.postReadCleanup()
                throw r10
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.get(java.lang.Object, int, com.google.common.cache.CacheLoader):java.lang.Object");
        }

        /* access modifiers changed from: package-private */
        @CheckForNull
        public V get(Object key, int hash) {
            Throwable th;
            try {
                if (this.count != 0) {
                    long now = this.map.ticker.read();
                    ReferenceEntry<K, V> e = getLiveEntry(key, hash, now);
                    if (e == null) {
                        postReadCleanup();
                        return null;
                    }
                    V value = e.getValueReference().get();
                    if (value != null) {
                        recordRead(e, now);
                        try {
                            V scheduleRefresh = scheduleRefresh(e, e.getKey(), hash, value, now, this.map.defaultLoader);
                            postReadCleanup();
                            return scheduleRefresh;
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } else {
                        tryDrainReferenceQueues();
                    }
                }
                postReadCleanup();
                return null;
            } catch (Throwable th3) {
                int i = hash;
                th = th3;
                postReadCleanup();
                throw th;
            }
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x004a, code lost:
            r16 = r4.getValueReference();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0050, code lost:
            if (r16.isLoading() == false) goto L_0x0059;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0052, code lost:
            r9 = false;
            r12 = r4;
            r2 = r16;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0059, code lost:
            r2 = r16.get();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x005d, code lost:
            if (r2 != null) goto L_0x0074;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x005f, code lost:
            r6 = r4;
            r4 = r2;
            r2 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
            r12 = r6;
            enqueueNotification(r2, r3, r4, r16.getWeight(), com.google.common.cache.RemovalCause.COLLECTED);
            r3 = r20;
            r5 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0074, code lost:
            r12 = r4;
            r4 = r2;
            r2 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x007d, code lost:
            if (r1.map.isExpired(r12, r10) == false) goto L_0x009b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0086, code lost:
            r3 = r20;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            enqueueNotification(r2, r3, r4, r16.getWeight(), com.google.common.cache.RemovalCause.EXPIRED);
            r5 = r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x008c, code lost:
            r1.writeQueue.remove(r12);
            r1.accessQueue.remove(r12);
            r1.count = r0;
            r2 = r16;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x009b, code lost:
            r3 = r20;
            r5 = r2;
            recordLockedRead(r12, r10);
            r1.statsCounter.recordHits(1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x00a7, code lost:
            unlock();
            postWriteCleanup();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x00ae, code lost:
            return r4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x00af, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x00b0, code lost:
            r3 = r20;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x00b3, code lost:
            r0 = th;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x00b4, code lost:
            r5 = r21;
            r2 = r16;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:61:0x00fa, code lost:
            r0 = th;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public V lockedGetOrLoad(K r19, int r20, com.google.common.cache.CacheLoader<? super K, V> r21) throws java.util.concurrent.ExecutionException {
            /*
                r18 = this;
                r1 = r18
                r7 = r19
                r3 = r20
                r2 = 0
                r8 = 0
                r9 = 1
                r1.lock()
                com.google.common.cache.LocalCache<K, V> r0 = r1.map     // Catch:{ all -> 0x010d }
                com.google.common.base.Ticker r0 = r0.ticker     // Catch:{ all -> 0x010d }
                long r4 = r0.read()     // Catch:{ all -> 0x010d }
                r10 = r4
                r1.preWriteCleanup(r10)     // Catch:{ all -> 0x010d }
                int r0 = r1.count     // Catch:{ all -> 0x010d }
                r12 = 1
                int r0 = r0 - r12
                java.util.concurrent.atomic.AtomicReferenceArray<com.google.common.cache.ReferenceEntry<K, V>> r4 = r1.table     // Catch:{ all -> 0x010d }
                r13 = r4
                int r4 = r13.length()     // Catch:{ all -> 0x010d }
                int r4 = r4 - r12
                r14 = r3 & r4
                java.lang.Object r4 = r13.get(r14)     // Catch:{ all -> 0x010d }
                com.google.common.cache.ReferenceEntry r4 = (com.google.common.cache.ReferenceEntry) r4     // Catch:{ all -> 0x010d }
                r15 = r4
            L_0x002e:
                if (r4 == 0) goto L_0x00c3
                java.lang.Object r5 = r4.getKey()     // Catch:{ all -> 0x010d }
                int r6 = r4.getHash()     // Catch:{ all -> 0x010d }
                if (r6 != r3) goto L_0x00bb
                if (r5 == 0) goto L_0x00bb
                com.google.common.cache.LocalCache<K, V> r6 = r1.map     // Catch:{ all -> 0x010d }
                com.google.common.base.Equivalence<java.lang.Object> r6 = r6.keyEquivalence     // Catch:{ all -> 0x010d }
                boolean r6 = r6.equivalent(r7, r5)     // Catch:{ all -> 0x010d }
                if (r6 == 0) goto L_0x00b9
                com.google.common.cache.LocalCache$ValueReference r6 = r4.getValueReference()     // Catch:{ all -> 0x010d }
                r16 = r6
                boolean r2 = r16.isLoading()     // Catch:{ all -> 0x00b3 }
                if (r2 == 0) goto L_0x0059
                r2 = 0
                r9 = r2
                r12 = r4
                r2 = r16
                goto L_0x00c4
            L_0x0059:
                java.lang.Object r2 = r16.get()     // Catch:{ all -> 0x00b3 }
                if (r2 != 0) goto L_0x0074
                r6 = r4
                r4 = r2
                r2 = r5
                int r5 = r16.getWeight()     // Catch:{ all -> 0x00af }
                r17 = r6
                com.google.common.cache.RemovalCause r6 = com.google.common.cache.RemovalCause.COLLECTED     // Catch:{ all -> 0x00af }
                r12 = r17
                r1.enqueueNotification(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x00af }
                r3 = r20
                r5 = r2
                goto L_0x008c
            L_0x0074:
                r12 = r4
                r4 = r2
                r2 = r5
                com.google.common.cache.LocalCache<K, V> r3 = r1.map     // Catch:{ all -> 0x00af }
                boolean r3 = r3.isExpired(r12, r10)     // Catch:{ all -> 0x00af }
                if (r3 == 0) goto L_0x009b
                int r5 = r16.getWeight()     // Catch:{ all -> 0x00af }
                com.google.common.cache.RemovalCause r6 = com.google.common.cache.RemovalCause.EXPIRED     // Catch:{ all -> 0x00af }
                r3 = r20
                r1.enqueueNotification(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x00b3 }
                r5 = r2
            L_0x008c:
                java.util.Queue<com.google.common.cache.ReferenceEntry<K, V>> r2 = r1.writeQueue     // Catch:{ all -> 0x00b3 }
                r2.remove(r12)     // Catch:{ all -> 0x00b3 }
                java.util.Queue<com.google.common.cache.ReferenceEntry<K, V>> r2 = r1.accessQueue     // Catch:{ all -> 0x00b3 }
                r2.remove(r12)     // Catch:{ all -> 0x00b3 }
                r1.count = r0     // Catch:{ all -> 0x00b3 }
                r2 = r16
                goto L_0x00c4
            L_0x009b:
                r3 = r20
                r5 = r2
                r1.recordLockedRead(r12, r10)     // Catch:{ all -> 0x00b3 }
                com.google.common.cache.AbstractCache$StatsCounter r2 = r1.statsCounter     // Catch:{ all -> 0x00b3 }
                r6 = 1
                r2.recordHits(r6)     // Catch:{ all -> 0x00b3 }
                r1.unlock()
                r1.postWriteCleanup()
                return r4
            L_0x00af:
                r0 = move-exception
                r3 = r20
                goto L_0x00b4
            L_0x00b3:
                r0 = move-exception
            L_0x00b4:
                r5 = r21
                r2 = r16
                goto L_0x0110
            L_0x00b9:
                r12 = r4
                goto L_0x00bc
            L_0x00bb:
                r12 = r4
            L_0x00bc:
                com.google.common.cache.ReferenceEntry r4 = r12.getNext()     // Catch:{ all -> 0x010d }
                r12 = 1
                goto L_0x002e
            L_0x00c3:
                r12 = r4
            L_0x00c4:
                if (r9 == 0) goto L_0x00de
                com.google.common.cache.LocalCache$LoadingValueReference r4 = new com.google.common.cache.LocalCache$LoadingValueReference     // Catch:{ all -> 0x010d }
                r4.<init>()     // Catch:{ all -> 0x010d }
                r8 = r4
                if (r12 != 0) goto L_0x00d9
                com.google.common.cache.ReferenceEntry r4 = r1.newEntry(r7, r3, r15)     // Catch:{ all -> 0x010d }
                r4.setValueReference(r8)     // Catch:{ all -> 0x010d }
                r13.set(r14, r4)     // Catch:{ all -> 0x010d }
                goto L_0x00df
            L_0x00d9:
                r12.setValueReference(r8)     // Catch:{ all -> 0x010d }
                r4 = r12
                goto L_0x00df
            L_0x00de:
                r4 = r12
            L_0x00df:
                r1.unlock()
                r1.postWriteCleanup()
                if (r9 == 0) goto L_0x0106
                monitor-enter(r4)     // Catch:{ all -> 0x00fc }
                r5 = r21
                java.lang.Object r0 = r1.loadSync(r7, r3, r8, r5)     // Catch:{ all -> 0x00f7 }
                monitor-exit(r4)     // Catch:{ all -> 0x00f7 }
                com.google.common.cache.AbstractCache$StatsCounter r6 = r1.statsCounter
                r10 = 1
                r6.recordMisses(r10)
                return r0
            L_0x00f7:
                r0 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x00f7 }
                throw r0     // Catch:{ all -> 0x00fa }
            L_0x00fa:
                r0 = move-exception
                goto L_0x00ff
            L_0x00fc:
                r0 = move-exception
                r5 = r21
            L_0x00ff:
                com.google.common.cache.AbstractCache$StatsCounter r6 = r1.statsCounter
                r10 = 1
                r6.recordMisses(r10)
                throw r0
            L_0x0106:
                r5 = r21
                java.lang.Object r0 = r1.waitForLoadingValue(r4, r7, r2)
                return r0
            L_0x010d:
                r0 = move-exception
                r5 = r21
            L_0x0110:
                r1.unlock()
                r1.postWriteCleanup()
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.lockedGetOrLoad(java.lang.Object, int, com.google.common.cache.CacheLoader):java.lang.Object");
        }

        /* access modifiers changed from: package-private */
        public V waitForLoadingValue(ReferenceEntry<K, V> e, K key, ValueReference<K, V> valueReference) throws ExecutionException {
            if (valueReference.isLoading()) {
                Preconditions.checkState(!Thread.holdsLock(e), "Recursive load of: %s", (Object) key);
                try {
                    V value = valueReference.waitForValue();
                    if (value != null) {
                        recordRead(e, this.map.ticker.read());
                        return value;
                    }
                    throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + key + ".");
                } finally {
                    this.statsCounter.recordMisses(1);
                }
            } else {
                throw new AssertionError();
            }
        }

        /* access modifiers changed from: package-private */
        public V loadSync(K key, int hash, LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> loader) throws ExecutionException {
            return getAndRecordStats(key, hash, loadingValueReference, loadingValueReference.loadFuture(key, loader));
        }

        /* access modifiers changed from: package-private */
        public ListenableFuture<V> loadAsync(K key, int hash, LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> loader) {
            ListenableFuture<V> loadingFuture = loadingValueReference.loadFuture(key, loader);
            loadingFuture.addListener(new LocalCache$Segment$$ExternalSyntheticLambda0(this, key, hash, loadingValueReference, loadingFuture), MoreExecutors.directExecutor());
            return loadingFuture;
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$loadAsync$0$com-google-common-cache-LocalCache$Segment  reason: not valid java name */
        public /* synthetic */ void m1742lambda$loadAsync$0$comgooglecommoncacheLocalCache$Segment(Object key, int hash, LoadingValueReference loadingValueReference, ListenableFuture loadingFuture) {
            try {
                getAndRecordStats(key, hash, loadingValueReference, loadingFuture);
            } catch (Throwable t) {
                LocalCache.logger.log(Level.WARNING, "Exception thrown during refresh", t);
                loadingValueReference.setException(t);
            }
        }

        /* access modifiers changed from: package-private */
        public V getAndRecordStats(K key, int hash, LoadingValueReference<K, V> loadingValueReference, ListenableFuture<V> newValue) throws ExecutionException {
            V value = null;
            try {
                value = Uninterruptibles.getUninterruptibly(newValue);
                if (value != null) {
                    this.statsCounter.recordLoadSuccess(loadingValueReference.elapsedNanos());
                    storeLoadedValue(key, hash, loadingValueReference, value);
                    return value;
                }
                throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + key + ".");
            } finally {
                if (value == null) {
                    this.statsCounter.recordLoadException(loadingValueReference.elapsedNanos());
                    removeLoadingValue(key, hash, loadingValueReference);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public V scheduleRefresh(ReferenceEntry<K, V> entry, K key, int hash, V oldValue, long now, CacheLoader<? super K, V> loader) {
            V newValue;
            if (!this.map.refreshes() || now - entry.getWriteTime() <= this.map.refreshNanos || entry.getValueReference().isLoading() || (newValue = refresh(key, hash, loader, true)) == null) {
                return oldValue;
            }
            return newValue;
        }

        /* access modifiers changed from: package-private */
        @CheckForNull
        public V refresh(K key, int hash, CacheLoader<? super K, V> loader, boolean checkTime) {
            LoadingValueReference<K, V> loadingValueReference = insertLoadingValueReference(key, hash, checkTime);
            if (loadingValueReference == null) {
                return null;
            }
            ListenableFuture<V> result = loadAsync(key, hash, loadingValueReference, loader);
            if (result.isDone()) {
                try {
                    return Uninterruptibles.getUninterruptibly(result);
                } catch (Throwable th) {
                }
            }
            return null;
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: package-private */
        @CheckForNull
        public LoadingValueReference<K, V> insertLoadingValueReference(K key, int hash, boolean checkTime) {
            lock();
            try {
                long now = this.map.ticker.read();
                preWriteCleanup(now);
                AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
                int index = (table2.length() - 1) & hash;
                ReferenceEntry<K, V> first = table2.get(index);
                ReferenceEntry<K, V> e = first;
                while (e != null) {
                    K entryKey = e.getKey();
                    if (e.getHash() != hash || entryKey == null || !this.map.keyEquivalence.equivalent(key, entryKey)) {
                        e = e.getNext();
                    } else {
                        ValueReference<K, V> valueReference = e.getValueReference();
                        if (!valueReference.isLoading()) {
                            if (!checkTime || now - e.getWriteTime() >= this.map.refreshNanos) {
                                this.modCount++;
                                LoadingValueReference<K, V> loadingValueReference = new LoadingValueReference<>(valueReference);
                                e.setValueReference(loadingValueReference);
                                unlock();
                                postWriteCleanup();
                                return loadingValueReference;
                            }
                        }
                        unlock();
                        postWriteCleanup();
                        return null;
                    }
                }
                this.modCount++;
                LoadingValueReference<K, V> loadingValueReference2 = new LoadingValueReference<>();
                ReferenceEntry<K, V> e2 = newEntry(key, hash, first);
                e2.setValueReference(loadingValueReference2);
                table2.set(index, e2);
                unlock();
                postWriteCleanup();
                return loadingValueReference2;
            } catch (Throwable th) {
                unlock();
                postWriteCleanup();
                throw th;
            }
        }

        /* access modifiers changed from: package-private */
        public void tryDrainReferenceQueues() {
            if (tryLock()) {
                try {
                    drainReferenceQueues();
                } finally {
                    unlock();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void drainReferenceQueues() {
            if (this.map.usesKeyReferences()) {
                drainKeyReferenceQueue();
            }
            if (this.map.usesValueReferences()) {
                drainValueReferenceQueue();
            }
        }

        /* access modifiers changed from: package-private */
        public void drainKeyReferenceQueue() {
            int i = 0;
            do {
                Reference<? extends K> poll = this.keyReferenceQueue.poll();
                Reference<? extends K> ref = poll;
                if (poll != null) {
                    this.map.reclaimKey((ReferenceEntry) ref);
                    i++;
                } else {
                    return;
                }
            } while (i != 16);
        }

        /* access modifiers changed from: package-private */
        public void drainValueReferenceQueue() {
            int i = 0;
            do {
                Reference<? extends V> poll = this.valueReferenceQueue.poll();
                Reference<? extends V> ref = poll;
                if (poll != null) {
                    this.map.reclaimValue((ValueReference) ref);
                    i++;
                } else {
                    return;
                }
            } while (i != 16);
        }

        /* access modifiers changed from: package-private */
        public void clearReferenceQueues() {
            if (this.map.usesKeyReferences()) {
                clearKeyReferenceQueue();
            }
            if (this.map.usesValueReferences()) {
                clearValueReferenceQueue();
            }
        }

        /* access modifiers changed from: package-private */
        public void clearKeyReferenceQueue() {
            do {
            } while (this.keyReferenceQueue.poll() != null);
        }

        /* access modifiers changed from: package-private */
        public void clearValueReferenceQueue() {
            do {
            } while (this.valueReferenceQueue.poll() != null);
        }

        /* access modifiers changed from: package-private */
        public void recordRead(ReferenceEntry<K, V> entry, long now) {
            if (this.map.recordsAccess()) {
                entry.setAccessTime(now);
            }
            this.recencyQueue.add(entry);
        }

        /* access modifiers changed from: package-private */
        public void recordLockedRead(ReferenceEntry<K, V> entry, long now) {
            if (this.map.recordsAccess()) {
                entry.setAccessTime(now);
            }
            this.accessQueue.add(entry);
        }

        /* access modifiers changed from: package-private */
        public void recordWrite(ReferenceEntry<K, V> entry, int weight, long now) {
            drainRecencyQueue();
            this.totalWeight += (long) weight;
            if (this.map.recordsAccess()) {
                entry.setAccessTime(now);
            }
            if (this.map.recordsWrite()) {
                entry.setWriteTime(now);
            }
            this.accessQueue.add(entry);
            this.writeQueue.add(entry);
        }

        /* access modifiers changed from: package-private */
        public void drainRecencyQueue() {
            while (true) {
                ReferenceEntry<K, V> poll = this.recencyQueue.poll();
                ReferenceEntry<K, V> e = poll;
                if (poll == null) {
                    return;
                }
                if (this.accessQueue.contains(e)) {
                    this.accessQueue.add(e);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void tryExpireEntries(long now) {
            if (tryLock()) {
                try {
                    expireEntries(now);
                } finally {
                    unlock();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void expireEntries(long now) {
            ReferenceEntry<K, V> e;
            ReferenceEntry<K, V> e2;
            drainRecencyQueue();
            do {
                ReferenceEntry<K, V> peek = this.writeQueue.peek();
                e = peek;
                if (peek == null || !this.map.isExpired(e, now)) {
                    do {
                        ReferenceEntry<K, V> peek2 = this.accessQueue.peek();
                        e2 = peek2;
                        if (peek2 == null || !this.map.isExpired(e2, now)) {
                            return;
                        }
                    } while (removeEntry(e2, e2.getHash(), RemovalCause.EXPIRED));
                    throw new AssertionError();
                }
            } while (removeEntry(e, e.getHash(), RemovalCause.EXPIRED));
            throw new AssertionError();
        }

        /* access modifiers changed from: package-private */
        public void enqueueNotification(@CheckForNull K key, int hash, @CheckForNull V value, int weight, RemovalCause cause) {
            this.totalWeight -= (long) weight;
            if (cause.wasEvicted()) {
                this.statsCounter.recordEviction();
            }
            if (this.map.removalNotificationQueue != LocalCache.DISCARDING_QUEUE) {
                this.map.removalNotificationQueue.offer(RemovalNotification.create(key, value, cause));
            }
        }

        /* access modifiers changed from: package-private */
        public void evictEntries(ReferenceEntry<K, V> newest) {
            if (this.map.evictsBySize()) {
                drainRecencyQueue();
                if (((long) newest.getValueReference().getWeight()) <= this.maxSegmentWeight || removeEntry(newest, newest.getHash(), RemovalCause.SIZE)) {
                    while (this.totalWeight > this.maxSegmentWeight) {
                        ReferenceEntry<K, V> e = getNextEvictable();
                        if (!removeEntry(e, e.getHash(), RemovalCause.SIZE)) {
                            throw new AssertionError();
                        }
                    }
                    return;
                }
                throw new AssertionError();
            }
        }

        /* access modifiers changed from: package-private */
        public ReferenceEntry<K, V> getNextEvictable() {
            for (ReferenceEntry<K, V> e : this.accessQueue) {
                if (e.getValueReference().getWeight() > 0) {
                    return e;
                }
            }
            throw new AssertionError();
        }

        /* access modifiers changed from: package-private */
        public ReferenceEntry<K, V> getFirst(int hash) {
            AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
            return table2.get((table2.length() - 1) & hash);
        }

        /* access modifiers changed from: package-private */
        @CheckForNull
        public ReferenceEntry<K, V> getEntry(Object key, int hash) {
            for (ReferenceEntry<K, V> e = getFirst(hash); e != null; e = e.getNext()) {
                if (e.getHash() == hash) {
                    K entryKey = e.getKey();
                    if (entryKey == null) {
                        tryDrainReferenceQueues();
                    } else if (this.map.keyEquivalence.equivalent(key, entryKey)) {
                        return e;
                    }
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        @CheckForNull
        public ReferenceEntry<K, V> getLiveEntry(Object key, int hash, long now) {
            ReferenceEntry<K, V> e = getEntry(key, hash);
            if (e == null) {
                return null;
            }
            if (!this.map.isExpired(e, now)) {
                return e;
            }
            tryExpireEntries(now);
            return null;
        }

        /* access modifiers changed from: package-private */
        public V getLiveValue(ReferenceEntry<K, V> entry, long now) {
            if (entry.getKey() == null) {
                tryDrainReferenceQueues();
                return null;
            }
            V value = entry.getValueReference().get();
            if (value == null) {
                tryDrainReferenceQueues();
                return null;
            } else if (!this.map.isExpired(entry, now)) {
                return value;
            } else {
                tryExpireEntries(now);
                return null;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean containsKey(Object key, int hash) {
            try {
                boolean z = false;
                if (this.count != 0) {
                    ReferenceEntry<K, V> e = getLiveEntry(key, hash, this.map.ticker.read());
                    if (e == null) {
                        return false;
                    }
                    if (e.getValueReference().get() != null) {
                        z = true;
                    }
                    postReadCleanup();
                    return z;
                }
                postReadCleanup();
                return false;
            } finally {
                postReadCleanup();
            }
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: package-private */
        public boolean containsValue(Object value) {
            try {
                if (this.count != 0) {
                    long now = this.map.ticker.read();
                    AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
                    int length = table2.length();
                    for (int i = 0; i < length; i++) {
                        for (ReferenceEntry<K, V> e = table2.get(i); e != null; e = e.getNext()) {
                            V entryValue = getLiveValue(e, now);
                            if (entryValue != null) {
                                if (this.map.valueEquivalence.equivalent(value, entryValue)) {
                                    postReadCleanup();
                                    return true;
                                }
                            }
                        }
                    }
                }
                postReadCleanup();
                return false;
            } catch (Throwable th) {
                postReadCleanup();
                throw th;
            }
        }

        /* access modifiers changed from: package-private */
        @CheckForNull
        public V put(K key, int hash, V value, boolean onlyIfAbsent) {
            long now;
            ReferenceEntry<K, V> e;
            int newCount;
            Segment segment = this;
            K k = key;
            int i = hash;
            segment.lock();
            try {
                long now2 = segment.map.ticker.read();
                segment.preWriteCleanup(now2);
                if (segment.count + 1 > segment.threshold) {
                    segment.expand();
                    int newCount2 = segment.count + 1;
                }
                AtomicReferenceArray<ReferenceEntry<K, V>> table2 = segment.table;
                int index = i & (table2.length() - 1);
                ReferenceEntry<K, V> referenceEntry = table2.get(index);
                ReferenceEntry<K, V> first = referenceEntry;
                ReferenceEntry<K, V> e2 = referenceEntry;
                while (e2 != null) {
                    try {
                        K entryKey = e2.getKey();
                        if (e2.getHash() != i || entryKey == null) {
                            now = now2;
                            e = e2;
                        } else if (segment.map.keyEquivalence.equivalent(k, entryKey)) {
                            ValueReference<K, V> valueReference = e2.getValueReference();
                            V entryValue = valueReference.get();
                            if (entryValue == null) {
                                segment.modCount++;
                                if (valueReference.isActive()) {
                                    segment.enqueueNotification(k, i, entryValue, valueReference.getWeight(), RemovalCause.COLLECTED);
                                    V v = entryValue;
                                    segment = this;
                                    long now3 = now2;
                                    ReferenceEntry<K, V> e3 = e2;
                                    segment.setValue(e3, key, value, now3);
                                    newCount = segment.count;
                                    e2 = e3;
                                    long j = now3;
                                } else {
                                    segment.setValue(e2, key, value, now2);
                                    newCount = segment.count + 1;
                                }
                                segment.count = newCount;
                                segment.evictEntries(e2);
                                segment.unlock();
                                segment.postWriteCleanup();
                                return null;
                            }
                            V entryValue2 = entryValue;
                            if (onlyIfAbsent) {
                                segment.recordLockedRead(e2, now2);
                                segment.unlock();
                                segment.postWriteCleanup();
                                return entryValue2;
                            }
                            try {
                                segment.modCount++;
                                K k2 = key;
                                try {
                                    segment.enqueueNotification(k2, hash, entryValue2, valueReference.getWeight(), RemovalCause.REPLACED);
                                    segment = this;
                                    ReferenceEntry<K, V> e4 = e2;
                                    segment.setValue(e4, key, value, now2);
                                    segment.evictEntries(e4);
                                    segment.unlock();
                                    segment.postWriteCleanup();
                                    return entryValue2;
                                } catch (Throwable th) {
                                    th = th;
                                    K k3 = k2;
                                    int i2 = hash;
                                    segment.unlock();
                                    segment.postWriteCleanup();
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                K k4 = key;
                                int i22 = hash;
                                segment.unlock();
                                segment.postWriteCleanup();
                                throw th;
                            }
                        } else {
                            now = now2;
                            e = e2;
                        }
                        e2 = e.getNext();
                        i = hash;
                        now2 = now;
                    } catch (Throwable th3) {
                        th = th3;
                        int i222 = hash;
                        segment.unlock();
                        segment.postWriteCleanup();
                        throw th;
                    }
                }
                long now4 = now2;
                int i3 = i;
                ReferenceEntry<K, V> referenceEntry2 = e2;
                try {
                    segment.modCount++;
                    ReferenceEntry<K, V> newEntry = segment.newEntry(k, i3, first);
                    segment.setValue(newEntry, k, value, now4);
                    table2.set(index, newEntry);
                    segment.count++;
                    segment.evictEntries(newEntry);
                    segment.unlock();
                    segment.postWriteCleanup();
                    return null;
                } catch (Throwable th4) {
                    th = th4;
                    segment.unlock();
                    segment.postWriteCleanup();
                    throw th;
                }
            } catch (Throwable th5) {
                th = th5;
                int i4 = i;
                segment.unlock();
                segment.postWriteCleanup();
                throw th;
            }
        }

        /* access modifiers changed from: package-private */
        public void expand() {
            AtomicReferenceArray<ReferenceEntry<K, V>> oldTable = this.table;
            int oldCapacity = oldTable.length();
            if (oldCapacity < 1073741824) {
                int newCount = this.count;
                AtomicReferenceArray<ReferenceEntry<K, V>> newTable = newEntryArray(oldCapacity << 1);
                this.threshold = (newTable.length() * 3) / 4;
                int newMask = newTable.length() - 1;
                for (int oldIndex = 0; oldIndex < oldCapacity; oldIndex++) {
                    ReferenceEntry<K, V> head = oldTable.get(oldIndex);
                    if (head != null) {
                        ReferenceEntry<K, V> next = head.getNext();
                        int headIndex = head.getHash() & newMask;
                        if (next == null) {
                            newTable.set(headIndex, head);
                        } else {
                            ReferenceEntry<K, V> tail = head;
                            int tailIndex = headIndex;
                            for (ReferenceEntry<K, V> e = next; e != null; e = e.getNext()) {
                                int newIndex = e.getHash() & newMask;
                                if (newIndex != tailIndex) {
                                    tailIndex = newIndex;
                                    tail = e;
                                }
                            }
                            newTable.set(tailIndex, tail);
                            for (ReferenceEntry<K, V> e2 = head; e2 != tail; e2 = e2.getNext()) {
                                int newIndex2 = e2.getHash() & newMask;
                                ReferenceEntry<K, V> newFirst = copyEntry(e2, newTable.get(newIndex2));
                                if (newFirst != null) {
                                    newTable.set(newIndex2, newFirst);
                                } else {
                                    removeCollectedEntry(e2);
                                    newCount--;
                                }
                            }
                        }
                    }
                }
                this.table = newTable;
                this.count = newCount;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean replace(K key, int hash, V oldValue, V newValue) {
            ReferenceEntry<K, V> first;
            long now;
            ReferenceEntry<K, V> first2;
            int i = hash;
            lock();
            try {
                long now2 = this.map.ticker.read();
                preWriteCleanup(now2);
                AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
                int index = i & (table2.length() - 1);
                ReferenceEntry<K, V> e = table2.get(index);
                ReferenceEntry<K, V> e2 = e;
                while (e2 != null) {
                    K entryKey = e2.getKey();
                    if (e2.getHash() != i || entryKey == null) {
                        V v = oldValue;
                        first = e;
                        first2 = e2;
                        K k = entryKey;
                        now = now2;
                    } else {
                        K k2 = key;
                        if (this.map.keyEquivalence.equivalent(k2, entryKey)) {
                            ValueReference<K, V> valueReference = e2.getValueReference();
                            ReferenceEntry<K, V> entryValue = valueReference.get();
                            if (entryValue == null) {
                                if (valueReference.isActive()) {
                                    int i2 = this.count - 1;
                                    this.modCount++;
                                    K k3 = entryKey;
                                    int i3 = i;
                                    ReferenceEntry<K, V> e3 = e2;
                                    K entryKey2 = k3;
                                    ReferenceEntry<K, V> newFirst = removeValueFromChain(e, e3, entryKey2, i3, entryValue, valueReference, RemovalCause.COLLECTED);
                                    ReferenceEntry<K, V> referenceEntry = e3;
                                    K k4 = entryKey2;
                                    V v2 = entryValue;
                                    ValueReference<K, V> valueReference2 = valueReference;
                                    ReferenceEntry<K, V> referenceEntry2 = e;
                                    table2.set(index, newFirst);
                                    this.count--;
                                } else {
                                    K k5 = entryKey;
                                    ReferenceEntry<K, V> e4 = entryValue;
                                    ValueReference<K, V> valueReference3 = valueReference;
                                    ReferenceEntry<K, V> referenceEntry3 = e;
                                }
                                unlock();
                                postWriteCleanup();
                                return false;
                            }
                            ReferenceEntry<K, V> e5 = e2;
                            K k6 = entryKey;
                            ReferenceEntry<K, V> entryValue2 = entryValue;
                            ValueReference<K, V> valueReference4 = valueReference;
                            ReferenceEntry<K, V> referenceEntry4 = e;
                            try {
                                if (this.map.valueEquivalence.equivalent(oldValue, entryValue2)) {
                                    this.modCount++;
                                    enqueueNotification(k2, hash, entryValue2, valueReference4.getWeight(), RemovalCause.REPLACED);
                                    ReferenceEntry<K, V> referenceEntry5 = entryValue2;
                                    ReferenceEntry<K, V> e6 = e5;
                                    setValue(e6, key, newValue, now2);
                                    evictEntries(e6);
                                    unlock();
                                    postWriteCleanup();
                                    return true;
                                }
                                recordLockedRead(e5, now2);
                                unlock();
                                postWriteCleanup();
                                return false;
                            } catch (Throwable th) {
                                th = th;
                                unlock();
                                postWriteCleanup();
                                throw th;
                            }
                        } else {
                            V v3 = oldValue;
                            first = e;
                            first2 = e2;
                            K k7 = entryKey;
                            now = now2;
                        }
                    }
                    e2 = first2.getNext();
                    i = hash;
                    now2 = now;
                    e = first;
                }
                unlock();
                postWriteCleanup();
                return false;
            } catch (Throwable th2) {
                th = th2;
                V v4 = oldValue;
                unlock();
                postWriteCleanup();
                throw th;
            }
        }

        /* access modifiers changed from: package-private */
        @CheckForNull
        public V replace(K key, int hash, V newValue) {
            ReferenceEntry<K, V> e;
            ReferenceEntry<K, V> first;
            long now;
            Segment segment = this;
            int i = hash;
            segment.lock();
            try {
                long now2 = segment.map.ticker.read();
                segment.preWriteCleanup(now2);
                AtomicReferenceArray<ReferenceEntry<K, V>> table2 = segment.table;
                int index = i & (table2.length() - 1);
                ReferenceEntry<K, V> first2 = table2.get(index);
                ReferenceEntry<K, V> e2 = first2;
                while (e2 != null) {
                    K entryKey = e2.getKey();
                    if (e2.getHash() != i || entryKey == null) {
                        first = first2;
                        e = e2;
                        K k = entryKey;
                        now = now2;
                    } else {
                        K k2 = key;
                        if (segment.map.keyEquivalence.equivalent(k2, entryKey)) {
                            ValueReference<K, V> valueReference = e2.getValueReference();
                            ReferenceEntry<K, V> entryValue = valueReference.get();
                            if (entryValue == null) {
                                if (valueReference.isActive()) {
                                    int i2 = segment.count - 1;
                                    segment.modCount++;
                                    K k3 = entryKey;
                                    int i3 = i;
                                    ReferenceEntry<K, V> e3 = e2;
                                    K entryKey2 = k3;
                                    ReferenceEntry<K, V> newFirst = segment.removeValueFromChain(first2, e3, entryKey2, i3, entryValue, valueReference, RemovalCause.COLLECTED);
                                    ReferenceEntry<K, V> referenceEntry = e3;
                                    K k4 = entryKey2;
                                    V v = entryValue;
                                    ValueReference<K, V> valueReference2 = valueReference;
                                    ReferenceEntry<K, V> referenceEntry2 = first2;
                                    table2.set(index, newFirst);
                                    segment.count--;
                                } else {
                                    K k5 = entryKey;
                                    ReferenceEntry<K, V> e4 = entryValue;
                                    ValueReference<K, V> valueReference3 = valueReference;
                                    ReferenceEntry<K, V> referenceEntry3 = first2;
                                }
                                return null;
                            }
                            ReferenceEntry<K, V> e5 = e2;
                            K k6 = entryKey;
                            ReferenceEntry<K, V> entryValue2 = entryValue;
                            ValueReference<K, V> valueReference4 = valueReference;
                            ReferenceEntry<K, V> referenceEntry4 = first2;
                            segment.modCount++;
                            segment.enqueueNotification(k2, hash, entryValue2, valueReference4.getWeight(), RemovalCause.REPLACED);
                            ReferenceEntry<K, V> entryValue3 = entryValue2;
                            segment = this;
                            segment.setValue(e5, key, newValue, now2);
                            segment.evictEntries(e5);
                            segment.unlock();
                            segment.postWriteCleanup();
                            return entryValue3;
                        }
                        first = first2;
                        e = e2;
                        K k7 = entryKey;
                        now = now2;
                    }
                    e2 = e.getNext();
                    i = hash;
                    now2 = now;
                    first2 = first;
                }
                segment.unlock();
                segment.postWriteCleanup();
                return null;
            } finally {
                segment.unlock();
                segment.postWriteCleanup();
            }
        }

        /* access modifiers changed from: package-private */
        @CheckForNull
        public V remove(Object key, int hash) {
            V cause;
            int i = hash;
            lock();
            try {
                preWriteCleanup(this.map.ticker.read());
                int i2 = this.count - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
                int index = i & (table2.length() - 1);
                ReferenceEntry<K, V> first = table2.get(index);
                ReferenceEntry<K, V> e = first;
                while (e != null) {
                    K entryKey = e.getKey();
                    if (e.getHash() != i || entryKey == null) {
                        Object obj = key;
                    } else {
                        try {
                            if (this.map.keyEquivalence.equivalent(key, entryKey)) {
                                ValueReference<K, V> valueReference = e.getValueReference();
                                V entryValue = valueReference.get();
                                if (entryValue != null) {
                                    cause = RemovalCause.EXPLICIT;
                                } else if (valueReference.isActive()) {
                                    cause = RemovalCause.COLLECTED;
                                } else {
                                    unlock();
                                    postWriteCleanup();
                                    return null;
                                }
                                this.modCount++;
                                V entryValue2 = entryValue;
                                V entryValue3 = cause;
                                K entryKey2 = entryKey;
                                ReferenceEntry<K, V> newFirst = removeValueFromChain(first, e, entryKey2, i, entryValue2, valueReference, entryValue3);
                                K k = entryKey2;
                                V v = entryValue3;
                                V entryValue4 = entryValue2;
                                table2.set(index, newFirst);
                                this.count--;
                                unlock();
                                postWriteCleanup();
                                return entryValue4;
                            }
                        } catch (Throwable th) {
                            th = th;
                            unlock();
                            postWriteCleanup();
                            throw th;
                        }
                    }
                    e = e.getNext();
                    i = hash;
                }
                Object obj2 = key;
                unlock();
                postWriteCleanup();
                return null;
            } catch (Throwable th2) {
                th = th2;
                Object obj3 = key;
                unlock();
                postWriteCleanup();
                throw th;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean remove(Object key, int hash, Object value) {
            boolean z;
            RemovalCause cause;
            int i = hash;
            lock();
            try {
                preWriteCleanup(this.map.ticker.read());
                boolean z2 = true;
                int i2 = this.count - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
                int index = i & (table2.length() - 1);
                ReferenceEntry<K, V> first = table2.get(index);
                ReferenceEntry<K, V> e = first;
                while (e != null) {
                    K entryKey = e.getKey();
                    if (e.getHash() != i || entryKey == null) {
                        Object obj = key;
                        z = z2;
                        Object obj2 = value;
                    } else {
                        try {
                            if (this.map.keyEquivalence.equivalent(key, entryKey)) {
                                ValueReference<K, V> valueReference = e.getValueReference();
                                V entryValue = valueReference.get();
                                boolean z3 = z2;
                                try {
                                    if (this.map.valueEquivalence.equivalent(value, entryValue)) {
                                        cause = RemovalCause.EXPLICIT;
                                    } else if (entryValue != null || !valueReference.isActive()) {
                                        unlock();
                                        postWriteCleanup();
                                        return false;
                                    } else {
                                        cause = RemovalCause.COLLECTED;
                                    }
                                    this.modCount++;
                                    table2.set(index, removeValueFromChain(first, e, entryKey, i, entryValue, valueReference, cause));
                                    this.count--;
                                    if (cause != RemovalCause.EXPLICIT) {
                                        z3 = false;
                                    }
                                    unlock();
                                    postWriteCleanup();
                                    return z3;
                                } catch (Throwable th) {
                                    th = th;
                                    unlock();
                                    postWriteCleanup();
                                    throw th;
                                }
                            } else {
                                z = z2;
                                Object obj3 = value;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            Object obj4 = value;
                            unlock();
                            postWriteCleanup();
                            throw th;
                        }
                    }
                    e = e.getNext();
                    i = hash;
                    z2 = z;
                }
                Object obj5 = key;
                Object obj6 = value;
                unlock();
                postWriteCleanup();
                return false;
            } catch (Throwable th3) {
                th = th3;
                Object obj7 = key;
                Object obj42 = value;
                unlock();
                postWriteCleanup();
                throw th;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean storeLoadedValue(K key, int hash, LoadingValueReference<K, V> oldValueReference, V newValue) {
            int i;
            long now;
            ReferenceEntry<K, V> e;
            RemovalCause cause;
            Segment segment = this;
            ReferenceEntry<K, V> referenceEntry = key;
            int i2 = hash;
            segment.lock();
            try {
                long now2 = segment.map.ticker.read();
                segment.preWriteCleanup(now2);
                int newCount = segment.count + 1;
                if (newCount > segment.threshold) {
                    segment.expand();
                    newCount = segment.count + 1;
                }
                AtomicReferenceArray<ReferenceEntry<K, V>> table2 = segment.table;
                int index = i2 & (table2.length() - 1);
                ReferenceEntry<K, V> referenceEntry2 = table2.get(index);
                ReferenceEntry<K, V> first = referenceEntry2;
                ReferenceEntry<K, V> e2 = referenceEntry2;
                while (e2 != null) {
                    K entryKey = e2.getKey();
                    if (e2.getHash() != i2 || entryKey == null) {
                        now = now2;
                        e = e2;
                        i = i2;
                    } else if (segment.map.keyEquivalence.equivalent(referenceEntry, entryKey)) {
                        ValueReference<K, V> valueReference = e2.getValueReference();
                        V entryValue = valueReference.get();
                        if (oldValueReference == valueReference || (entryValue == null && valueReference != LocalCache.UNSET)) {
                            try {
                                segment.modCount++;
                                if (oldValueReference.isActive()) {
                                    if (entryValue == null) {
                                        cause = RemovalCause.COLLECTED;
                                    } else {
                                        try {
                                            cause = RemovalCause.REPLACED;
                                        } catch (Throwable th) {
                                            th = th;
                                            segment = this;
                                            int i3 = hash;
                                            segment.unlock();
                                            segment.postWriteCleanup();
                                            throw th;
                                        }
                                    }
                                    segment.enqueueNotification(key, hash, entryValue, oldValueReference.getWeight(), cause);
                                    newCount--;
                                }
                                segment = this;
                                ReferenceEntry<K, V> referenceEntry3 = key;
                                long now3 = now2;
                                ReferenceEntry<K, V> e3 = e2;
                                int i4 = hash;
                                try {
                                    segment.setValue(e3, referenceEntry3, newValue, now3);
                                    ReferenceEntry<K, V> e4 = e3;
                                    ReferenceEntry<K, V> e5 = referenceEntry3;
                                    try {
                                        segment.count = newCount;
                                        segment.evictEntries(e4);
                                        segment.unlock();
                                        segment.postWriteCleanup();
                                        return true;
                                    } catch (Throwable th2) {
                                        th = th2;
                                        segment.unlock();
                                        segment.postWriteCleanup();
                                        throw th;
                                    }
                                } catch (Throwable th3) {
                                    th = th3;
                                    ReferenceEntry<K, V> referenceEntry4 = referenceEntry3;
                                    segment.unlock();
                                    segment.postWriteCleanup();
                                    throw th;
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                K k = key;
                                int i32 = hash;
                                segment.unlock();
                                segment.postWriteCleanup();
                                throw th;
                            }
                        } else {
                            try {
                                segment.enqueueNotification(referenceEntry, i2, newValue, 0, RemovalCause.REPLACED);
                                segment.unlock();
                                segment.postWriteCleanup();
                                return false;
                            } catch (Throwable th5) {
                                th = th5;
                            }
                        }
                    } else {
                        now = now2;
                        e = e2;
                        i = i2;
                    }
                    e2 = e.getNext();
                    i2 = i;
                    now2 = now;
                }
                ReferenceEntry<K, V> referenceEntry5 = e2;
                segment.modCount++;
                ReferenceEntry<K, V> newEntry = referenceEntry;
                ReferenceEntry<K, V> newEntry2 = segment.newEntry(referenceEntry, i2, first);
                segment.setValue(newEntry2, newEntry, newValue, now2);
                table2.set(index, newEntry2);
                segment.count = newCount;
                segment.evictEntries(newEntry2);
                segment.unlock();
                segment.postWriteCleanup();
                return true;
            } catch (Throwable th6) {
                th = th6;
                int i5 = i2;
                segment.unlock();
                segment.postWriteCleanup();
                throw th;
            }
        }

        /* access modifiers changed from: package-private */
        public void clear() {
            RemovalCause cause;
            if (this.count != 0) {
                lock();
                try {
                    preWriteCleanup(this.map.ticker.read());
                    AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
                    for (int i = 0; i < table2.length(); i++) {
                        for (ReferenceEntry<K, V> e = table2.get(i); e != null; e = e.getNext()) {
                            if (e.getValueReference().isActive()) {
                                K key = e.getKey();
                                V value = e.getValueReference().get();
                                if (key != null) {
                                    if (value != null) {
                                        cause = RemovalCause.EXPLICIT;
                                        enqueueNotification(key, e.getHash(), value, e.getValueReference().getWeight(), cause);
                                    }
                                }
                                cause = RemovalCause.COLLECTED;
                                try {
                                    enqueueNotification(key, e.getHash(), value, e.getValueReference().getWeight(), cause);
                                } catch (Throwable th) {
                                    th = th;
                                    unlock();
                                    postWriteCleanup();
                                    throw th;
                                }
                            }
                        }
                    }
                    for (int i2 = 0; i2 < table2.length(); i2++) {
                        table2.set(i2, (Object) null);
                    }
                    clearReferenceQueues();
                    this.writeQueue.clear();
                    this.accessQueue.clear();
                    this.readCount.set(0);
                    this.modCount++;
                    this.count = 0;
                    unlock();
                    postWriteCleanup();
                } catch (Throwable th2) {
                    th = th2;
                    unlock();
                    postWriteCleanup();
                    throw th;
                }
            }
        }

        /* access modifiers changed from: package-private */
        @CheckForNull
        public ReferenceEntry<K, V> removeValueFromChain(ReferenceEntry<K, V> first, ReferenceEntry<K, V> entry, @CheckForNull K key, int hash, V value, ValueReference<K, V> valueReference, RemovalCause cause) {
            enqueueNotification(key, hash, value, valueReference.getWeight(), cause);
            this.writeQueue.remove(entry);
            this.accessQueue.remove(entry);
            if (!valueReference.isLoading()) {
                return removeEntryFromChain(first, entry);
            }
            valueReference.notifyNewValue(null);
            return first;
        }

        /* access modifiers changed from: package-private */
        @CheckForNull
        public ReferenceEntry<K, V> removeEntryFromChain(ReferenceEntry<K, V> first, ReferenceEntry<K, V> entry) {
            int newCount = this.count;
            ReferenceEntry<K, V> newFirst = entry.getNext();
            for (ReferenceEntry<K, V> e = first; e != entry; e = e.getNext()) {
                ReferenceEntry<K, V> next = copyEntry(e, newFirst);
                if (next != null) {
                    newFirst = next;
                } else {
                    removeCollectedEntry(e);
                    newCount--;
                }
            }
            this.count = newCount;
            return newFirst;
        }

        /* access modifiers changed from: package-private */
        public void removeCollectedEntry(ReferenceEntry<K, V> entry) {
            enqueueNotification(entry.getKey(), entry.getHash(), entry.getValueReference().get(), entry.getValueReference().getWeight(), RemovalCause.COLLECTED);
            this.writeQueue.remove(entry);
            this.accessQueue.remove(entry);
        }

        /* access modifiers changed from: package-private */
        public boolean reclaimKey(ReferenceEntry<K, V> entry, int hash) {
            Throwable th;
            lock();
            try {
                int i = this.count - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
                int index = (table2.length() - 1) & hash;
                ReferenceEntry<K, V> referenceEntry = table2.get(index);
                ReferenceEntry<K, V> first = referenceEntry;
                ReferenceEntry<K, V> e = referenceEntry;
                while (e != null) {
                    if (e == entry) {
                        this.modCount++;
                        try {
                            table2.set(index, removeValueFromChain(first, e, e.getKey(), hash, e.getValueReference().get(), e.getValueReference(), RemovalCause.COLLECTED));
                            this.count--;
                            unlock();
                            postWriteCleanup();
                            return true;
                        } catch (Throwable th2) {
                            th = th2;
                            unlock();
                            postWriteCleanup();
                            throw th;
                        }
                    } else {
                        int hash2 = hash;
                        e = e.getNext();
                        hash = hash2;
                    }
                }
                int i2 = hash;
                unlock();
                postWriteCleanup();
                return false;
            } catch (Throwable th3) {
                int i3 = hash;
                th = th3;
                unlock();
                postWriteCleanup();
                throw th;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean reclaimValue(K key, int hash, ValueReference<K, V> valueReference) {
            int i = hash;
            lock();
            try {
                int i2 = this.count - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
                int index = i & (table2.length() - 1);
                ReferenceEntry<K, V> first = table2.get(index);
                ReferenceEntry<K, V> e = first;
                while (e != null) {
                    K entryKey = e.getKey();
                    if (e.getHash() != i || entryKey == null) {
                    } else if (this.map.keyEquivalence.equivalent(key, entryKey)) {
                        ValueReference<K, V> valueReference2 = valueReference;
                        if (e.getValueReference() == valueReference2) {
                            this.modCount++;
                            K entryKey2 = entryKey;
                            ReferenceEntry<K, V> newFirst = removeValueFromChain(first, e, entryKey2, i, valueReference2.get(), valueReference2, RemovalCause.COLLECTED);
                            K k = entryKey2;
                            table2.set(index, newFirst);
                            this.count--;
                            return true;
                        }
                        unlock();
                        if (!isHeldByCurrentThread()) {
                            postWriteCleanup();
                        }
                        return false;
                    }
                    e = e.getNext();
                    i = hash;
                }
                unlock();
                if (!isHeldByCurrentThread()) {
                    postWriteCleanup();
                }
                return false;
            } finally {
                unlock();
                if (!isHeldByCurrentThread()) {
                    postWriteCleanup();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public boolean removeLoadingValue(K key, int hash, LoadingValueReference<K, V> valueReference) {
            lock();
            try {
                AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
                int index = (table2.length() - 1) & hash;
                ReferenceEntry<K, V> first = table2.get(index);
                ReferenceEntry<K, V> e = first;
                while (e != null) {
                    K entryKey = e.getKey();
                    if (e.getHash() != hash || entryKey == null || !this.map.keyEquivalence.equivalent(key, entryKey)) {
                        e = e.getNext();
                    } else if (e.getValueReference() == valueReference) {
                        if (valueReference.isActive()) {
                            e.setValueReference(valueReference.getOldValue());
                        } else {
                            table2.set(index, removeEntryFromChain(first, e));
                        }
                        return true;
                    } else {
                        unlock();
                        postWriteCleanup();
                        return false;
                    }
                }
                unlock();
                postWriteCleanup();
                return false;
            } finally {
                unlock();
                postWriteCleanup();
            }
        }

        /* access modifiers changed from: package-private */
        public boolean removeEntry(ReferenceEntry<K, V> entry, int hash, RemovalCause cause) {
            int i = this.count - 1;
            AtomicReferenceArray<ReferenceEntry<K, V>> table2 = this.table;
            int index = hash & (table2.length() - 1);
            ReferenceEntry<K, V> first = table2.get(index);
            for (ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
                if (e == entry) {
                    this.modCount++;
                    table2.set(index, removeValueFromChain(first, e, e.getKey(), hash, e.getValueReference().get(), e.getValueReference(), cause));
                    this.count--;
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        public void postReadCleanup() {
            if ((this.readCount.incrementAndGet() & 63) == 0) {
                cleanUp();
            }
        }

        /* access modifiers changed from: package-private */
        public void preWriteCleanup(long now) {
            runLockedCleanup(now);
        }

        /* access modifiers changed from: package-private */
        public void postWriteCleanup() {
            runUnlockedCleanup();
        }

        /* access modifiers changed from: package-private */
        public void cleanUp() {
            runLockedCleanup(this.map.ticker.read());
            runUnlockedCleanup();
        }

        /* access modifiers changed from: package-private */
        public void runLockedCleanup(long now) {
            if (tryLock()) {
                try {
                    drainReferenceQueues();
                    expireEntries(now);
                    this.readCount.set(0);
                } finally {
                    unlock();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void runUnlockedCleanup() {
            if (!isHeldByCurrentThread()) {
                this.map.processPendingNotifications();
            }
        }
    }

    static class LoadingValueReference<K, V> implements ValueReference<K, V> {
        final SettableFuture<V> futureValue;
        volatile ValueReference<K, V> oldValue;
        final Stopwatch stopwatch;

        public LoadingValueReference() {
            this(LocalCache.unset());
        }

        public LoadingValueReference(ValueReference<K, V> oldValue2) {
            this.futureValue = SettableFuture.create();
            this.stopwatch = Stopwatch.createUnstarted();
            this.oldValue = oldValue2;
        }

        public boolean isLoading() {
            return true;
        }

        public boolean isActive() {
            return this.oldValue.isActive();
        }

        public int getWeight() {
            return this.oldValue.getWeight();
        }

        public boolean set(@CheckForNull V newValue) {
            return this.futureValue.set(newValue);
        }

        public boolean setException(Throwable t) {
            return this.futureValue.setException(t);
        }

        private ListenableFuture<V> fullyFailedFuture(Throwable t) {
            return Futures.immediateFailedFuture(t);
        }

        public void notifyNewValue(@CheckForNull V newValue) {
            if (newValue != null) {
                set(newValue);
            } else {
                this.oldValue = LocalCache.unset();
            }
        }

        public ListenableFuture<V> loadFuture(K key, CacheLoader<? super K, V> loader) {
            try {
                this.stopwatch.start();
                V previousValue = this.oldValue.get();
                if (previousValue == null) {
                    V newValue = loader.load(key);
                    return set(newValue) ? this.futureValue : Futures.immediateFuture(newValue);
                }
                V newValue2 = loader.reload(key, previousValue);
                if (newValue2 == null) {
                    return Futures.immediateFuture(null);
                }
                return Futures.transform(newValue2, new LocalCache$LoadingValueReference$$ExternalSyntheticLambda0(this), MoreExecutors.directExecutor());
            } catch (Throwable t) {
                ListenableFuture<V> result = setException(t) ? this.futureValue : fullyFailedFuture(t);
                if (t instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
                return result;
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$loadFuture$0$com-google-common-cache-LocalCache$LoadingValueReference  reason: not valid java name */
        public /* synthetic */ Object m1741lambda$loadFuture$0$comgooglecommoncacheLocalCache$LoadingValueReference(Object newResult) {
            set(newResult);
            return newResult;
        }

        public long elapsedNanos() {
            return this.stopwatch.elapsed(TimeUnit.NANOSECONDS);
        }

        public V waitForValue() throws ExecutionException {
            return Uninterruptibles.getUninterruptibly(this.futureValue);
        }

        public V get() {
            return this.oldValue.get();
        }

        public ValueReference<K, V> getOldValue() {
            return this.oldValue;
        }

        public ReferenceEntry<K, V> getEntry() {
            return null;
        }

        public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, @CheckForNull V v, ReferenceEntry<K, V> referenceEntry) {
            return this;
        }
    }

    static final class WriteQueue<K, V> extends AbstractQueue<ReferenceEntry<K, V>> {
        final ReferenceEntry<K, V> head = new AbstractReferenceEntry<K, V>(this) {
            ReferenceEntry<K, V> nextWrite = this;
            ReferenceEntry<K, V> previousWrite = this;

            public long getWriteTime() {
                return Long.MAX_VALUE;
            }

            public void setWriteTime(long time) {
            }

            public ReferenceEntry<K, V> getNextInWriteQueue() {
                return this.nextWrite;
            }

            public void setNextInWriteQueue(ReferenceEntry<K, V> next) {
                this.nextWrite = next;
            }

            public ReferenceEntry<K, V> getPreviousInWriteQueue() {
                return this.previousWrite;
            }

            public void setPreviousInWriteQueue(ReferenceEntry<K, V> previous) {
                this.previousWrite = previous;
            }
        };

        WriteQueue() {
        }

        public boolean offer(ReferenceEntry<K, V> entry) {
            LocalCache.connectWriteOrder(entry.getPreviousInWriteQueue(), entry.getNextInWriteQueue());
            LocalCache.connectWriteOrder(this.head.getPreviousInWriteQueue(), entry);
            LocalCache.connectWriteOrder(entry, this.head);
            return true;
        }

        @CheckForNull
        public ReferenceEntry<K, V> peek() {
            ReferenceEntry<K, V> next = this.head.getNextInWriteQueue();
            if (next == this.head) {
                return null;
            }
            return next;
        }

        @CheckForNull
        public ReferenceEntry<K, V> poll() {
            ReferenceEntry<K, V> next = this.head.getNextInWriteQueue();
            if (next == this.head) {
                return null;
            }
            remove(next);
            return next;
        }

        public boolean remove(Object o) {
            ReferenceEntry<K, V> e = (ReferenceEntry) o;
            ReferenceEntry<K, V> previous = e.getPreviousInWriteQueue();
            ReferenceEntry<K, V> next = e.getNextInWriteQueue();
            LocalCache.connectWriteOrder(previous, next);
            LocalCache.nullifyWriteOrder(e);
            return next != NullEntry.INSTANCE;
        }

        public boolean contains(Object o) {
            return ((ReferenceEntry) o).getNextInWriteQueue() != NullEntry.INSTANCE;
        }

        public boolean isEmpty() {
            return this.head.getNextInWriteQueue() == this.head;
        }

        public int size() {
            int size = 0;
            for (ReferenceEntry<K, V> e = this.head.getNextInWriteQueue(); e != this.head; e = e.getNextInWriteQueue()) {
                size++;
            }
            return size;
        }

        public void clear() {
            ReferenceEntry<K, V> e = this.head.getNextInWriteQueue();
            while (e != this.head) {
                ReferenceEntry<K, V> next = e.getNextInWriteQueue();
                LocalCache.nullifyWriteOrder(e);
                e = next;
            }
            this.head.setNextInWriteQueue(this.head);
            this.head.setPreviousInWriteQueue(this.head);
        }

        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractSequentialIterator<ReferenceEntry<K, V>>(peek()) {
                /* access modifiers changed from: protected */
                @CheckForNull
                public ReferenceEntry<K, V> computeNext(ReferenceEntry<K, V> previous) {
                    ReferenceEntry<K, V> next = previous.getNextInWriteQueue();
                    if (next == WriteQueue.this.head) {
                        return null;
                    }
                    return next;
                }
            };
        }
    }

    static final class AccessQueue<K, V> extends AbstractQueue<ReferenceEntry<K, V>> {
        final ReferenceEntry<K, V> head = new AbstractReferenceEntry<K, V>(this) {
            ReferenceEntry<K, V> nextAccess = this;
            ReferenceEntry<K, V> previousAccess = this;

            public long getAccessTime() {
                return Long.MAX_VALUE;
            }

            public void setAccessTime(long time) {
            }

            public ReferenceEntry<K, V> getNextInAccessQueue() {
                return this.nextAccess;
            }

            public void setNextInAccessQueue(ReferenceEntry<K, V> next) {
                this.nextAccess = next;
            }

            public ReferenceEntry<K, V> getPreviousInAccessQueue() {
                return this.previousAccess;
            }

            public void setPreviousInAccessQueue(ReferenceEntry<K, V> previous) {
                this.previousAccess = previous;
            }
        };

        AccessQueue() {
        }

        public boolean offer(ReferenceEntry<K, V> entry) {
            LocalCache.connectAccessOrder(entry.getPreviousInAccessQueue(), entry.getNextInAccessQueue());
            LocalCache.connectAccessOrder(this.head.getPreviousInAccessQueue(), entry);
            LocalCache.connectAccessOrder(entry, this.head);
            return true;
        }

        @CheckForNull
        public ReferenceEntry<K, V> peek() {
            ReferenceEntry<K, V> next = this.head.getNextInAccessQueue();
            if (next == this.head) {
                return null;
            }
            return next;
        }

        @CheckForNull
        public ReferenceEntry<K, V> poll() {
            ReferenceEntry<K, V> next = this.head.getNextInAccessQueue();
            if (next == this.head) {
                return null;
            }
            remove(next);
            return next;
        }

        public boolean remove(Object o) {
            ReferenceEntry<K, V> e = (ReferenceEntry) o;
            ReferenceEntry<K, V> previous = e.getPreviousInAccessQueue();
            ReferenceEntry<K, V> next = e.getNextInAccessQueue();
            LocalCache.connectAccessOrder(previous, next);
            LocalCache.nullifyAccessOrder(e);
            return next != NullEntry.INSTANCE;
        }

        public boolean contains(Object o) {
            return ((ReferenceEntry) o).getNextInAccessQueue() != NullEntry.INSTANCE;
        }

        public boolean isEmpty() {
            return this.head.getNextInAccessQueue() == this.head;
        }

        public int size() {
            int size = 0;
            for (ReferenceEntry<K, V> e = this.head.getNextInAccessQueue(); e != this.head; e = e.getNextInAccessQueue()) {
                size++;
            }
            return size;
        }

        public void clear() {
            ReferenceEntry<K, V> e = this.head.getNextInAccessQueue();
            while (e != this.head) {
                ReferenceEntry<K, V> next = e.getNextInAccessQueue();
                LocalCache.nullifyAccessOrder(e);
                e = next;
            }
            this.head.setNextInAccessQueue(this.head);
            this.head.setPreviousInAccessQueue(this.head);
        }

        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractSequentialIterator<ReferenceEntry<K, V>>(peek()) {
                /* access modifiers changed from: protected */
                @CheckForNull
                public ReferenceEntry<K, V> computeNext(ReferenceEntry<K, V> previous) {
                    ReferenceEntry<K, V> next = previous.getNextInAccessQueue();
                    if (next == AccessQueue.this.head) {
                        return null;
                    }
                    return next;
                }
            };
        }
    }

    public void cleanUp() {
        for (Segment<K, V> cleanUp : this.segments) {
            cleanUp.cleanUp();
        }
    }

    public boolean isEmpty() {
        long sum = 0;
        Segment<K, V>[] segments2 = this.segments;
        for (Segment<K, V> segment : segments2) {
            if (segment.count != 0) {
                return false;
            }
            sum += (long) segment.modCount;
        }
        if (sum == 0) {
            return true;
        }
        for (Segment<K, V> segment2 : segments2) {
            if (segment2.count != 0) {
                return false;
            }
            sum -= (long) segment2.modCount;
        }
        if (sum == 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public long longSize() {
        long sum = 0;
        for (Segment<K, V> segment : this.segments) {
            sum += (long) Math.max(0, segment.count);
        }
        return sum;
    }

    public int size() {
        return Ints.saturatedCast(longSize());
    }

    @CheckForNull
    public V get(@CheckForNull Object key) {
        if (key == null) {
            return null;
        }
        int hash = hash(key);
        return segmentFor(hash).get(key, hash);
    }

    /* access modifiers changed from: package-private */
    public V get(K key, CacheLoader<? super K, V> loader) throws ExecutionException {
        int hash = hash(Preconditions.checkNotNull(key));
        return segmentFor(hash).get(key, hash, loader);
    }

    @CheckForNull
    public V getIfPresent(Object key) {
        int hash = hash(Preconditions.checkNotNull(key));
        V value = segmentFor(hash).get(key, hash);
        if (value == null) {
            this.globalStatsCounter.recordMisses(1);
        } else {
            this.globalStatsCounter.recordHits(1);
        }
        return value;
    }

    @CheckForNull
    public V getOrDefault(@CheckForNull Object key, @CheckForNull V defaultValue) {
        V result = get(key);
        return result != null ? result : defaultValue;
    }

    /* access modifiers changed from: package-private */
    public V getOrLoad(K key) throws ExecutionException {
        return get(key, this.defaultLoader);
    }

    /* access modifiers changed from: package-private */
    public ImmutableMap<K, V> getAllPresent(Iterable<?> keys) {
        int hits = 0;
        int misses = 0;
        ImmutableMap.Builder<K, V> result = ImmutableMap.builder();
        for (Object next : keys) {
            V value = get(next);
            if (value == null) {
                misses++;
            } else {
                result.put(next, value);
                hits++;
            }
        }
        this.globalStatsCounter.recordHits(hits);
        this.globalStatsCounter.recordMisses(misses);
        return result.buildKeepingLast();
    }

    /* access modifiers changed from: package-private */
    public ImmutableMap<K, V> getAll(Iterable<? extends K> keys) throws ExecutionException {
        int hits = 0;
        int misses = 0;
        Map<K, V> result = Maps.newLinkedHashMap();
        Set<K> keysToLoad = Sets.newLinkedHashSet();
        for (K key : keys) {
            V value = get(key);
            if (!result.containsKey(key)) {
                result.put(key, value);
                if (value == null) {
                    misses++;
                    keysToLoad.add(key);
                } else {
                    hits++;
                }
            }
        }
        try {
            if (!keysToLoad.isEmpty()) {
                Map<K, V> newEntries = loadAll(Collections.unmodifiableSet(keysToLoad), this.defaultLoader);
                for (K key2 : keysToLoad) {
                    V value2 = newEntries.get(key2);
                    if (value2 != null) {
                        result.put(key2, value2);
                    } else {
                        throw new CacheLoader.InvalidCacheLoadException("loadAll failed to return a value for " + key2);
                    }
                }
            }
        } catch (CacheLoader.UnsupportedLoadingOperationException e) {
            for (K key3 : keysToLoad) {
                misses--;
                result.put(key3, get(key3, this.defaultLoader));
            }
        } catch (Throwable th) {
            this.globalStatsCounter.recordHits(hits);
            this.globalStatsCounter.recordMisses(misses);
            throw th;
        }
        ImmutableMap<K, V> copyOf = ImmutableMap.copyOf(result);
        this.globalStatsCounter.recordHits(hits);
        this.globalStatsCounter.recordMisses(misses);
        return copyOf;
    }

    /* access modifiers changed from: package-private */
    @CheckForNull
    public Map<K, V> loadAll(Set<? extends K> keys, CacheLoader<? super K, V> loader) throws ExecutionException {
        Preconditions.checkNotNull(loader);
        Preconditions.checkNotNull(keys);
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            Map<? super K, V> loadAll = loader.loadAll(keys);
            if (1 == 0) {
                this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
            }
            if (loadAll != null) {
                stopwatch.stop();
                boolean nullsPresent = false;
                for (Map.Entry<K, V> entry : loadAll.entrySet()) {
                    K key = entry.getKey();
                    V value = entry.getValue();
                    if (key == null || value == null) {
                        nullsPresent = true;
                    } else {
                        put(key, value);
                    }
                }
                if (!nullsPresent) {
                    this.globalStatsCounter.recordLoadSuccess(stopwatch.elapsed(TimeUnit.NANOSECONDS));
                    return loadAll;
                }
                this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
                throw new CacheLoader.InvalidCacheLoadException(loader + " returned null keys or values from loadAll");
            }
            this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
            throw new CacheLoader.InvalidCacheLoadException(loader + " returned null map from loadAll");
        } catch (CacheLoader.UnsupportedLoadingOperationException e) {
            throw e;
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            throw new ExecutionException(e2);
        } catch (RuntimeException e3) {
            throw new UncheckedExecutionException((Throwable) e3);
        } catch (Exception e4) {
            throw new ExecutionException(e4);
        } catch (Error e5) {
            throw new ExecutionError(e5);
        } catch (Throwable th) {
            if (0 == 0) {
                this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
            }
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    @CheckForNull
    public ReferenceEntry<K, V> getEntry(@CheckForNull Object key) {
        if (key == null) {
            return null;
        }
        int hash = hash(key);
        return segmentFor(hash).getEntry(key, hash);
    }

    /* access modifiers changed from: package-private */
    public void refresh(K key) {
        int hash = hash(Preconditions.checkNotNull(key));
        segmentFor(hash).refresh(key, hash, this.defaultLoader, false);
    }

    public boolean containsKey(@CheckForNull Object key) {
        if (key == null) {
            return false;
        }
        int hash = hash(key);
        return segmentFor(hash).containsKey(key, hash);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0060, code lost:
        r18 = r2;
        r9 = r9 + ((long) r13.modCount);
        r12 = r12 + 1;
        r5 = r16;
        r2 = r17;
        r3 = r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean containsValue(@javax.annotation.CheckForNull java.lang.Object r22) {
        /*
            r21 = this;
            r0 = r21
            r1 = r22
            r2 = 0
            if (r1 != 0) goto L_0x0008
            return r2
        L_0x0008:
            com.google.common.base.Ticker r3 = r0.ticker
            long r3 = r3.read()
            com.google.common.cache.LocalCache$Segment<K, V>[] r5 = r0.segments
            r6 = -1
            r8 = 0
        L_0x0013:
            r9 = 3
            if (r8 >= r9) goto L_0x0086
            r9 = 0
            int r11 = r5.length
            r12 = r2
        L_0x001a:
            if (r12 >= r11) goto L_0x0071
            r13 = r5[r12]
            int r14 = r13.count
            java.util.concurrent.atomic.AtomicReferenceArray<com.google.common.cache.ReferenceEntry<K, V>> r15 = r13.table
            r16 = 0
            r17 = r2
            r2 = r16
        L_0x0028:
            r16 = r5
            int r5 = r15.length()
            if (r2 >= r5) goto L_0x0060
            java.lang.Object r5 = r15.get(r2)
            com.google.common.cache.ReferenceEntry r5 = (com.google.common.cache.ReferenceEntry) r5
        L_0x0036:
            if (r5 == 0) goto L_0x0057
            r18 = r2
            java.lang.Object r2 = r13.getLiveValue(r5, r3)
            if (r2 == 0) goto L_0x004c
            r19 = r3
            com.google.common.base.Equivalence<java.lang.Object> r3 = r0.valueEquivalence
            boolean r3 = r3.equivalent(r1, r2)
            if (r3 == 0) goto L_0x004e
            r3 = 1
            return r3
        L_0x004c:
            r19 = r3
        L_0x004e:
            com.google.common.cache.ReferenceEntry r5 = r5.getNext()
            r2 = r18
            r3 = r19
            goto L_0x0036
        L_0x0057:
            r18 = r2
            r19 = r3
            int r2 = r18 + 1
            r5 = r16
            goto L_0x0028
        L_0x0060:
            r18 = r2
            r19 = r3
            int r2 = r13.modCount
            long r2 = (long) r2
            long r9 = r9 + r2
            int r12 = r12 + 1
            r5 = r16
            r2 = r17
            r3 = r19
            goto L_0x001a
        L_0x0071:
            r17 = r2
            r19 = r3
            r16 = r5
            int r2 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
            if (r2 != 0) goto L_0x007c
            goto L_0x008c
        L_0x007c:
            r6 = r9
            int r8 = r8 + 1
            r5 = r16
            r2 = r17
            r3 = r19
            goto L_0x0013
        L_0x0086:
            r17 = r2
            r19 = r3
            r16 = r5
        L_0x008c:
            return r17
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.containsValue(java.lang.Object):boolean");
    }

    @CheckForNull
    public V put(K key, V value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        int hash = hash(key);
        return segmentFor(hash).put(key, hash, value, false);
    }

    @CheckForNull
    public V putIfAbsent(K key, V value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        int hash = hash(key);
        return segmentFor(hash).put(key, hash, value, true);
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @CheckForNull
    public V remove(@CheckForNull Object key) {
        if (key == null) {
            return null;
        }
        int hash = hash(key);
        return segmentFor(hash).remove(key, hash);
    }

    public boolean remove(@CheckForNull Object key, @CheckForNull Object value) {
        if (key == null || value == null) {
            return false;
        }
        int hash = hash(key);
        return segmentFor(hash).remove(key, hash, value);
    }

    public boolean replace(K key, @CheckForNull V oldValue, V newValue) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(newValue);
        if (oldValue == null) {
            return false;
        }
        int hash = hash(key);
        return segmentFor(hash).replace(key, hash, oldValue, newValue);
    }

    @CheckForNull
    public V replace(K key, V value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        int hash = hash(key);
        return segmentFor(hash).replace(key, hash, value);
    }

    public void clear() {
        for (Segment<K, V> segment : this.segments) {
            segment.clear();
        }
    }

    /* access modifiers changed from: package-private */
    public void invalidateAll(Iterable<?> keys) {
        for (Object key : keys) {
            remove(key);
        }
    }

    public Set<K> keySet() {
        Set<K> ks = this.keySet;
        if (ks != null) {
            return ks;
        }
        KeySet keySet2 = new KeySet();
        this.keySet = keySet2;
        return keySet2;
    }

    public Collection<V> values() {
        Collection<V> vs = this.values;
        if (vs != null) {
            return vs;
        }
        Values values2 = new Values();
        this.values = values2;
        return values2;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> es = this.entrySet;
        if (es != null) {
            return es;
        }
        EntrySet entrySet2 = new EntrySet();
        this.entrySet = entrySet2;
        return entrySet2;
    }

    abstract class HashIterator<T> implements Iterator<T> {
        @CheckForNull
        Segment<K, V> currentSegment;
        @CheckForNull
        AtomicReferenceArray<ReferenceEntry<K, V>> currentTable;
        @CheckForNull
        LocalCache<K, V>.WriteThroughEntry lastReturned;
        @CheckForNull
        ReferenceEntry<K, V> nextEntry;
        @CheckForNull
        LocalCache<K, V>.WriteThroughEntry nextExternal;
        int nextSegmentIndex;
        int nextTableIndex = -1;

        public abstract T next();

        HashIterator() {
            this.nextSegmentIndex = LocalCache.this.segments.length - 1;
            advance();
        }

        /* access modifiers changed from: package-private */
        public final void advance() {
            this.nextExternal = null;
            if (!nextInChain() && !nextInTable()) {
                while (this.nextSegmentIndex >= 0) {
                    Segment<K, V>[] segmentArr = LocalCache.this.segments;
                    int i = this.nextSegmentIndex;
                    this.nextSegmentIndex = i - 1;
                    this.currentSegment = segmentArr[i];
                    if (this.currentSegment.count != 0) {
                        this.currentTable = this.currentSegment.table;
                        this.nextTableIndex = this.currentTable.length() - 1;
                        if (nextInTable()) {
                            return;
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public boolean nextInChain() {
            if (this.nextEntry == null) {
                return false;
            }
            do {
                this.nextEntry = this.nextEntry.getNext();
                if (this.nextEntry == null) {
                    return false;
                }
            } while (!advanceTo(this.nextEntry));
            return true;
        }

        /* access modifiers changed from: package-private */
        public boolean nextInTable() {
            while (this.nextTableIndex >= 0) {
                AtomicReferenceArray<ReferenceEntry<K, V>> atomicReferenceArray = this.currentTable;
                int i = this.nextTableIndex;
                this.nextTableIndex = i - 1;
                ReferenceEntry<K, V> referenceEntry = atomicReferenceArray.get(i);
                this.nextEntry = referenceEntry;
                if (referenceEntry != null && (advanceTo(this.nextEntry) || nextInChain())) {
                    return true;
                }
            }
            return false;
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: package-private */
        public boolean advanceTo(ReferenceEntry<K, V> entry) {
            try {
                long now = LocalCache.this.ticker.read();
                K key = entry.getKey();
                V value = LocalCache.this.getLiveValue(entry, now);
                if (value != null) {
                    this.nextExternal = new WriteThroughEntry(key, value);
                    this.currentSegment.postReadCleanup();
                    return true;
                }
                this.currentSegment.postReadCleanup();
                return false;
            } catch (Throwable th) {
                this.currentSegment.postReadCleanup();
                throw th;
            }
        }

        public boolean hasNext() {
            return this.nextExternal != null;
        }

        /* access modifiers changed from: package-private */
        public LocalCache<K, V>.WriteThroughEntry nextEntry() {
            if (this.nextExternal != null) {
                this.lastReturned = this.nextExternal;
                advance();
                return this.lastReturned;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            Preconditions.checkState(this.lastReturned != null);
            LocalCache.this.remove(this.lastReturned.getKey());
            this.lastReturned = null;
        }
    }

    final class KeyIterator extends LocalCache<K, V>.HashIterator<K> {
        KeyIterator(LocalCache this$0) {
            super();
        }

        public K next() {
            return nextEntry().getKey();
        }
    }

    final class ValueIterator extends LocalCache<K, V>.HashIterator<V> {
        ValueIterator(LocalCache this$0) {
            super();
        }

        public V next() {
            return nextEntry().getValue();
        }
    }

    final class WriteThroughEntry implements Map.Entry<K, V> {
        final K key;
        V value;

        WriteThroughEntry(K key2, V value2) {
            this.key = key2;
            this.value = value2;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public boolean equals(@CheckForNull Object object) {
            if (!(object instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> that = (Map.Entry) object;
            if (!this.key.equals(that.getKey()) || !this.value.equals(that.getValue())) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return this.key.hashCode() ^ this.value.hashCode();
        }

        public V setValue(V newValue) {
            V oldValue = LocalCache.this.put(this.key, newValue);
            this.value = newValue;
            return oldValue;
        }

        public String toString() {
            return getKey() + "=" + getValue();
        }
    }

    final class EntryIterator extends LocalCache<K, V>.HashIterator<Map.Entry<K, V>> {
        EntryIterator(LocalCache this$0) {
            super();
        }

        public Map.Entry<K, V> next() {
            return nextEntry();
        }
    }

    abstract class AbstractCacheSet<T> extends AbstractSet<T> {
        AbstractCacheSet() {
        }

        public int size() {
            return LocalCache.this.size();
        }

        public boolean isEmpty() {
            return LocalCache.this.isEmpty();
        }

        public void clear() {
            LocalCache.this.clear();
        }

        public Object[] toArray() {
            return LocalCache.toArrayList(this).toArray();
        }

        public <E> E[] toArray(E[] a) {
            return LocalCache.toArrayList(this).toArray(a);
        }
    }

    /* access modifiers changed from: private */
    public static <E> ArrayList<E> toArrayList(Collection<E> c) {
        ArrayList<E> result = new ArrayList<>(c.size());
        Iterators.addAll(result, c.iterator());
        return result;
    }

    final class KeySet extends LocalCache<K, V>.AbstractCacheSet<K> {
        KeySet() {
            super();
        }

        public Iterator<K> iterator() {
            return new KeyIterator(LocalCache.this);
        }

        public boolean contains(Object o) {
            return LocalCache.this.containsKey(o);
        }

        public boolean remove(Object o) {
            return LocalCache.this.remove(o) != null;
        }
    }

    final class Values extends AbstractCollection<V> {
        Values() {
        }

        public int size() {
            return LocalCache.this.size();
        }

        public boolean isEmpty() {
            return LocalCache.this.isEmpty();
        }

        public void clear() {
            LocalCache.this.clear();
        }

        public Iterator<V> iterator() {
            return new ValueIterator(LocalCache.this);
        }

        public boolean contains(Object o) {
            return LocalCache.this.containsValue(o);
        }

        public Object[] toArray() {
            return LocalCache.toArrayList(this).toArray();
        }

        public <E> E[] toArray(E[] a) {
            return LocalCache.toArrayList(this).toArray(a);
        }
    }

    final class EntrySet extends LocalCache<K, V>.AbstractCacheSet<Map.Entry<K, V>> {
        EntrySet() {
            super();
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator(LocalCache.this);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:3:0x0006, code lost:
            r0 = (java.util.Map.Entry) r7;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean contains(java.lang.Object r7) {
            /*
                r6 = this;
                boolean r0 = r7 instanceof java.util.Map.Entry
                r1 = 0
                if (r0 != 0) goto L_0x0006
                return r1
            L_0x0006:
                r0 = r7
                java.util.Map$Entry r0 = (java.util.Map.Entry) r0
                java.lang.Object r2 = r0.getKey()
                if (r2 != 0) goto L_0x0010
                return r1
            L_0x0010:
                com.google.common.cache.LocalCache r3 = com.google.common.cache.LocalCache.this
                java.lang.Object r3 = r3.get(r2)
                if (r3 == 0) goto L_0x0027
                com.google.common.cache.LocalCache r4 = com.google.common.cache.LocalCache.this
                com.google.common.base.Equivalence<java.lang.Object> r4 = r4.valueEquivalence
                java.lang.Object r5 = r0.getValue()
                boolean r4 = r4.equivalent(r5, r3)
                if (r4 == 0) goto L_0x0027
                r1 = 1
            L_0x0027:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.EntrySet.contains(java.lang.Object):boolean");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:3:0x0006, code lost:
            r0 = (java.util.Map.Entry) r6;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean remove(java.lang.Object r6) {
            /*
                r5 = this;
                boolean r0 = r6 instanceof java.util.Map.Entry
                r1 = 0
                if (r0 != 0) goto L_0x0006
                return r1
            L_0x0006:
                r0 = r6
                java.util.Map$Entry r0 = (java.util.Map.Entry) r0
                java.lang.Object r2 = r0.getKey()
                if (r2 == 0) goto L_0x001c
                com.google.common.cache.LocalCache r3 = com.google.common.cache.LocalCache.this
                java.lang.Object r4 = r0.getValue()
                boolean r3 = r3.remove(r2, r4)
                if (r3 == 0) goto L_0x001c
                r1 = 1
            L_0x001c:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.EntrySet.remove(java.lang.Object):boolean");
        }
    }

    static class ManualSerializationProxy<K, V> extends ForwardingCache<K, V> implements Serializable {
        private static final long serialVersionUID = 1;
        final int concurrencyLevel;
        @CheckForNull
        transient Cache<K, V> delegate;
        final long expireAfterAccessNanos;
        final long expireAfterWriteNanos;
        final Equivalence<Object> keyEquivalence;
        final Strength keyStrength;
        final CacheLoader<? super K, V> loader;
        final long maxWeight;
        final RemovalListener<? super K, ? super V> removalListener;
        @CheckForNull
        final Ticker ticker;
        final Equivalence<Object> valueEquivalence;
        final Strength valueStrength;
        final Weigher<K, V> weigher;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        ManualSerializationProxy(com.google.common.cache.LocalCache<K, V> r18) {
            /*
                r17 = this;
                r0 = r18
                com.google.common.cache.LocalCache$Strength r2 = r0.keyStrength
                com.google.common.cache.LocalCache$Strength r3 = r0.valueStrength
                com.google.common.base.Equivalence<java.lang.Object> r4 = r0.keyEquivalence
                com.google.common.base.Equivalence<java.lang.Object> r5 = r0.valueEquivalence
                long r6 = r0.expireAfterWriteNanos
                long r8 = r0.expireAfterAccessNanos
                long r10 = r0.maxWeight
                com.google.common.cache.Weigher<K, V> r12 = r0.weigher
                int r13 = r0.concurrencyLevel
                com.google.common.cache.RemovalListener<K, V> r14 = r0.removalListener
                com.google.common.base.Ticker r15 = r0.ticker
                com.google.common.cache.CacheLoader<? super K, V> r1 = r0.defaultLoader
                r16 = r1
                r1 = r17
                r1.<init>(r2, r3, r4, r5, r6, r8, r10, r12, r13, r14, r15, r16)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.ManualSerializationProxy.<init>(com.google.common.cache.LocalCache):void");
        }

        private ManualSerializationProxy(Strength keyStrength2, Strength valueStrength2, Equivalence<Object> keyEquivalence2, Equivalence<Object> valueEquivalence2, long expireAfterWriteNanos2, long expireAfterAccessNanos2, long maxWeight2, Weigher<K, V> weigher2, int concurrencyLevel2, RemovalListener<? super K, ? super V> removalListener2, Ticker ticker2, CacheLoader<? super K, V> loader2) {
            Ticker ticker3 = ticker2;
            this.keyStrength = keyStrength2;
            this.valueStrength = valueStrength2;
            this.keyEquivalence = keyEquivalence2;
            this.valueEquivalence = valueEquivalence2;
            this.expireAfterWriteNanos = expireAfterWriteNanos2;
            this.expireAfterAccessNanos = expireAfterAccessNanos2;
            this.maxWeight = maxWeight2;
            this.weigher = weigher2;
            this.concurrencyLevel = concurrencyLevel2;
            this.removalListener = removalListener2;
            this.ticker = (ticker3 == Ticker.systemTicker() || ticker3 == CacheBuilder.NULL_TICKER) ? null : ticker3;
            this.loader = loader2;
        }

        /* access modifiers changed from: package-private */
        public CacheBuilder<K, V> recreateCacheBuilder() {
            CacheBuilder<K1, V1> removalListener2 = CacheBuilder.newBuilder().setKeyStrength(this.keyStrength).setValueStrength(this.valueStrength).keyEquivalence(this.keyEquivalence).valueEquivalence(this.valueEquivalence).concurrencyLevel(this.concurrencyLevel).removalListener(this.removalListener);
            removalListener2.strictParsing = false;
            if (this.expireAfterWriteNanos > 0) {
                removalListener2.expireAfterWrite(this.expireAfterWriteNanos, TimeUnit.NANOSECONDS);
            }
            if (this.expireAfterAccessNanos > 0) {
                removalListener2.expireAfterAccess(this.expireAfterAccessNanos, TimeUnit.NANOSECONDS);
            }
            if (this.weigher != CacheBuilder.OneWeigher.INSTANCE) {
                CacheBuilder<K1, V1> weigher2 = removalListener2.weigher(this.weigher);
                if (this.maxWeight != -1) {
                    removalListener2.maximumWeight(this.maxWeight);
                }
            } else if (this.maxWeight != -1) {
                removalListener2.maximumSize(this.maxWeight);
            }
            if (this.ticker != null) {
                removalListener2.ticker(this.ticker);
            }
            return removalListener2;
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();
            this.delegate = recreateCacheBuilder().build();
        }

        private Object readResolve() {
            return this.delegate;
        }

        /* access modifiers changed from: protected */
        public Cache<K, V> delegate() {
            return this.delegate;
        }
    }

    static final class LoadingSerializationProxy<K, V> extends ManualSerializationProxy<K, V> implements LoadingCache<K, V>, Serializable {
        private static final long serialVersionUID = 1;
        @CheckForNull
        transient LoadingCache<K, V> autoDelegate;

        LoadingSerializationProxy(LocalCache<K, V> cache) {
            super(cache);
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();
            this.autoDelegate = recreateCacheBuilder().build(this.loader);
        }

        public V get(K key) throws ExecutionException {
            return this.autoDelegate.get(key);
        }

        public V getUnchecked(K key) {
            return this.autoDelegate.getUnchecked(key);
        }

        public ImmutableMap<K, V> getAll(Iterable<? extends K> keys) throws ExecutionException {
            return this.autoDelegate.getAll(keys);
        }

        public V apply(K key) {
            return this.autoDelegate.apply(key);
        }

        public void refresh(K key) {
            this.autoDelegate.refresh(key);
        }

        private Object readResolve() {
            return this.autoDelegate;
        }
    }

    static class LocalManualCache<K, V> implements Cache<K, V>, Serializable {
        private static final long serialVersionUID = 1;
        final LocalCache<K, V> localCache;

        LocalManualCache(CacheBuilder<? super K, ? super V> builder) {
            this(new LocalCache(builder, (CacheLoader) null));
        }

        private LocalManualCache(LocalCache<K, V> localCache2) {
            this.localCache = localCache2;
        }

        @CheckForNull
        public V getIfPresent(Object key) {
            return this.localCache.getIfPresent(key);
        }

        public V get(K key, final Callable<? extends V> valueLoader) throws ExecutionException {
            Preconditions.checkNotNull(valueLoader);
            return this.localCache.get(key, new CacheLoader<Object, V>(this) {
                public V load(Object key) throws Exception {
                    return valueLoader.call();
                }
            });
        }

        public ImmutableMap<K, V> getAllPresent(Iterable<?> keys) {
            return this.localCache.getAllPresent(keys);
        }

        public void put(K key, V value) {
            this.localCache.put(key, value);
        }

        public void putAll(Map<? extends K, ? extends V> m) {
            this.localCache.putAll(m);
        }

        public void invalidate(Object key) {
            Preconditions.checkNotNull(key);
            this.localCache.remove(key);
        }

        public void invalidateAll(Iterable<?> keys) {
            this.localCache.invalidateAll(keys);
        }

        public void invalidateAll() {
            this.localCache.clear();
        }

        public long size() {
            return this.localCache.longSize();
        }

        public ConcurrentMap<K, V> asMap() {
            return this.localCache;
        }

        public CacheStats stats() {
            AbstractCache.SimpleStatsCounter aggregator = new AbstractCache.SimpleStatsCounter();
            aggregator.incrementBy(this.localCache.globalStatsCounter);
            for (Segment<K, V> segment : this.localCache.segments) {
                aggregator.incrementBy(segment.statsCounter);
            }
            return aggregator.snapshot();
        }

        public void cleanUp() {
            this.localCache.cleanUp();
        }

        /* access modifiers changed from: package-private */
        public Object writeReplace() {
            return new ManualSerializationProxy(this.localCache);
        }

        private void readObject(ObjectInputStream in) throws InvalidObjectException {
            throw new InvalidObjectException("Use ManualSerializationProxy");
        }
    }

    static class LocalLoadingCache<K, V> extends LocalManualCache<K, V> implements LoadingCache<K, V> {
        private static final long serialVersionUID = 1;

        LocalLoadingCache(CacheBuilder<? super K, ? super V> builder, CacheLoader<? super K, V> loader) {
            super();
        }

        public V get(K key) throws ExecutionException {
            return this.localCache.getOrLoad(key);
        }

        public V getUnchecked(K key) {
            try {
                return get(key);
            } catch (ExecutionException e) {
                throw new UncheckedExecutionException(e.getCause());
            }
        }

        public ImmutableMap<K, V> getAll(Iterable<? extends K> keys) throws ExecutionException {
            return this.localCache.getAll(keys);
        }

        public void refresh(K key) {
            this.localCache.refresh(key);
        }

        public final V apply(K key) {
            return getUnchecked(key);
        }

        /* access modifiers changed from: package-private */
        public Object writeReplace() {
            return new LoadingSerializationProxy(this.localCache);
        }

        private void readObject(ObjectInputStream in) throws InvalidObjectException {
            throw new InvalidObjectException("Use LoadingSerializationProxy");
        }
    }
}
