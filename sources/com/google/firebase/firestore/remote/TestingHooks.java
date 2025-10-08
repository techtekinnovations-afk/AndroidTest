package com.google.firebase.firestore.remote;

import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.model.DatabaseId;
import com.google.firebase.firestore.remote.WatchChangeAggregator;
import com.google.firebase.firestore.util.Preconditions;
import com.google.firestore.v1.BloomFilter;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

final class TestingHooks {
    private static final TestingHooks instance = new TestingHooks();
    private final CopyOnWriteArrayList<AtomicReference<ExistenceFilterMismatchListener>> existenceFilterMismatchListeners = new CopyOnWriteArrayList<>();

    interface ExistenceFilterMismatchListener {
        void onExistenceFilterMismatch(ExistenceFilterMismatchInfo existenceFilterMismatchInfo);
    }

    private TestingHooks() {
    }

    static TestingHooks getInstance() {
        return instance;
    }

    /* access modifiers changed from: package-private */
    public void notifyOnExistenceFilterMismatch(ExistenceFilterMismatchInfo info) {
        Iterator<AtomicReference<ExistenceFilterMismatchListener>> it = this.existenceFilterMismatchListeners.iterator();
        while (it.hasNext()) {
            ExistenceFilterMismatchListener listener = it.next().get();
            if (listener != null) {
                listener.onExistenceFilterMismatch(info);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public ListenerRegistration addExistenceFilterMismatchListener(ExistenceFilterMismatchListener listener) {
        Preconditions.checkNotNull(listener, "a null listener is not allowed");
        AtomicReference<ExistenceFilterMismatchListener> listenerRef = new AtomicReference<>(listener);
        this.existenceFilterMismatchListeners.add(listenerRef);
        return new TestingHooks$$ExternalSyntheticLambda0(this, listenerRef);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$addExistenceFilterMismatchListener$0$com-google-firebase-firestore-remote-TestingHooks  reason: not valid java name */
    public /* synthetic */ void m1924lambda$addExistenceFilterMismatchListener$0$comgooglefirebasefirestoreremoteTestingHooks(AtomicReference listenerRef) {
        listenerRef.set((Object) null);
        this.existenceFilterMismatchListeners.remove(listenerRef);
    }

    static abstract class ExistenceFilterMismatchInfo {
        /* access modifiers changed from: package-private */
        public abstract ExistenceFilterBloomFilterInfo bloomFilter();

        /* access modifiers changed from: package-private */
        public abstract String databaseId();

        /* access modifiers changed from: package-private */
        public abstract int existenceFilterCount();

        /* access modifiers changed from: package-private */
        public abstract int localCacheCount();

        /* access modifiers changed from: package-private */
        public abstract String projectId();

        ExistenceFilterMismatchInfo() {
        }

        static ExistenceFilterMismatchInfo create(int localCacheCount, int existenceFilterCount, String projectId, String databaseId, ExistenceFilterBloomFilterInfo bloomFilter) {
            return new AutoValue_TestingHooks_ExistenceFilterMismatchInfo(localCacheCount, existenceFilterCount, projectId, databaseId, bloomFilter);
        }

        static ExistenceFilterMismatchInfo from(int localCacheCount, ExistenceFilter existenceFilter, DatabaseId databaseId, BloomFilter bloomFilter, WatchChangeAggregator.BloomFilterApplicationStatus bloomFilterStatus) {
            return create(localCacheCount, existenceFilter.getCount(), databaseId.getProjectId(), databaseId.getDatabaseId(), ExistenceFilterBloomFilterInfo.from(bloomFilter, bloomFilterStatus, existenceFilter));
        }
    }

    static abstract class ExistenceFilterBloomFilterInfo {
        /* access modifiers changed from: package-private */
        public abstract boolean applied();

        /* access modifiers changed from: package-private */
        public abstract int bitmapLength();

        /* access modifiers changed from: package-private */
        public abstract BloomFilter bloomFilter();

        /* access modifiers changed from: package-private */
        public abstract int hashCount();

        /* access modifiers changed from: package-private */
        public abstract int padding();

        ExistenceFilterBloomFilterInfo() {
        }

        static ExistenceFilterBloomFilterInfo create(BloomFilter bloomFilter, boolean applied, int hashCount, int bitmapLength, int padding) {
            return new AutoValue_TestingHooks_ExistenceFilterBloomFilterInfo(bloomFilter, applied, hashCount, bitmapLength, padding);
        }

        static ExistenceFilterBloomFilterInfo from(BloomFilter bloomFilter, WatchChangeAggregator.BloomFilterApplicationStatus bloomFilterStatus, ExistenceFilter existenceFilter) {
            BloomFilter unchangedNames = existenceFilter.getUnchangedNames();
            if (unchangedNames == null) {
                return null;
            }
            return create(bloomFilter, bloomFilterStatus == WatchChangeAggregator.BloomFilterApplicationStatus.SUCCESS, unchangedNames.getHashCount(), unchangedNames.getBits().getBitmap().size(), unchangedNames.getBits().getPadding());
        }
    }
}
