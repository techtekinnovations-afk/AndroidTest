package com.google.firebase.firestore.local;

import com.google.common.base.Supplier;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldIndex;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.Logger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class IndexBackfiller {
    /* access modifiers changed from: private */
    public static final long INITIAL_BACKFILL_DELAY_MS = TimeUnit.SECONDS.toMillis(15);
    private static final String LOG_TAG = "IndexBackfiller";
    private static final int MAX_DOCUMENTS_TO_PROCESS = 50;
    /* access modifiers changed from: private */
    public static final long REGULAR_BACKFILL_DELAY_MS = TimeUnit.MINUTES.toMillis(1);
    private final Supplier<IndexManager> indexManagerOfCurrentUser;
    private final Supplier<LocalDocumentsView> localDocumentsViewOfCurrentUser;
    private int maxDocumentsToProcess;
    private final Persistence persistence;
    private final Scheduler scheduler;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public IndexBackfiller(com.google.firebase.firestore.local.Persistence r3, com.google.firebase.firestore.util.AsyncQueue r4, com.google.firebase.firestore.local.LocalStore r5) {
        /*
            r2 = this;
            java.util.Objects.requireNonNull(r5)
            com.google.firebase.firestore.local.IndexBackfiller$$ExternalSyntheticLambda0 r0 = new com.google.firebase.firestore.local.IndexBackfiller$$ExternalSyntheticLambda0
            r0.<init>(r5)
            java.util.Objects.requireNonNull(r5)
            com.google.firebase.firestore.local.IndexBackfiller$$ExternalSyntheticLambda1 r1 = new com.google.firebase.firestore.local.IndexBackfiller$$ExternalSyntheticLambda1
            r1.<init>(r5)
            r2.<init>(r3, r4, r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.local.IndexBackfiller.<init>(com.google.firebase.firestore.local.Persistence, com.google.firebase.firestore.util.AsyncQueue, com.google.firebase.firestore.local.LocalStore):void");
    }

    public IndexBackfiller(Persistence persistence2, AsyncQueue asyncQueue, Supplier<IndexManager> indexManagerOfCurrentUser2, Supplier<LocalDocumentsView> localDocumentsViewOfCurrentUser2) {
        this.maxDocumentsToProcess = 50;
        this.persistence = persistence2;
        this.scheduler = new Scheduler(asyncQueue);
        this.indexManagerOfCurrentUser = indexManagerOfCurrentUser2;
        this.localDocumentsViewOfCurrentUser = localDocumentsViewOfCurrentUser2;
    }

    public class Scheduler implements Scheduler {
        private final AsyncQueue asyncQueue;
        private AsyncQueue.DelayedTask backfillTask;

        public Scheduler(AsyncQueue asyncQueue2) {
            this.asyncQueue = asyncQueue2;
        }

        public void start() {
            scheduleBackfill(IndexBackfiller.INITIAL_BACKFILL_DELAY_MS);
        }

        public void stop() {
            if (this.backfillTask != null) {
                this.backfillTask.cancel();
            }
        }

        private void scheduleBackfill(long delay) {
            this.backfillTask = this.asyncQueue.enqueueAfterDelay(AsyncQueue.TimerId.INDEX_BACKFILL, delay, new IndexBackfiller$Scheduler$$ExternalSyntheticLambda0(this));
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$scheduleBackfill$0$com-google-firebase-firestore-local-IndexBackfiller$Scheduler  reason: not valid java name */
        public /* synthetic */ void m1840lambda$scheduleBackfill$0$comgooglefirebasefirestorelocalIndexBackfiller$Scheduler() {
            Logger.debug(IndexBackfiller.LOG_TAG, "Documents written: %s", Integer.valueOf(IndexBackfiller.this.backfill()));
            scheduleBackfill(IndexBackfiller.REGULAR_BACKFILL_DELAY_MS);
        }
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }

    public int backfill() {
        return ((Integer) this.persistence.runTransaction("Backfill Indexes", new IndexBackfiller$$ExternalSyntheticLambda2(this))).intValue();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$backfill$0$com-google-firebase-firestore-local-IndexBackfiller  reason: not valid java name */
    public /* synthetic */ Integer m1839lambda$backfill$0$comgooglefirebasefirestorelocalIndexBackfiller() {
        return Integer.valueOf(writeIndexEntries());
    }

    private int writeIndexEntries() {
        IndexManager indexManager = this.indexManagerOfCurrentUser.get();
        Set<String> processedCollectionGroups = new HashSet<>();
        int documentsRemaining = this.maxDocumentsToProcess;
        while (documentsRemaining > 0) {
            String collectionGroup = indexManager.getNextCollectionGroupToUpdate();
            if (collectionGroup == null || processedCollectionGroups.contains(collectionGroup)) {
                break;
            }
            Logger.debug(LOG_TAG, "Processing collection: %s", collectionGroup);
            documentsRemaining -= writeEntriesForCollectionGroup(collectionGroup, documentsRemaining);
            processedCollectionGroups.add(collectionGroup);
        }
        return this.maxDocumentsToProcess - documentsRemaining;
    }

    private int writeEntriesForCollectionGroup(String collectionGroup, int documentsRemainingUnderCap) {
        IndexManager indexManager = this.indexManagerOfCurrentUser.get();
        FieldIndex.IndexOffset existingOffset = indexManager.getMinOffset(collectionGroup);
        LocalDocumentsResult nextBatch = this.localDocumentsViewOfCurrentUser.get().getNextDocuments(collectionGroup, existingOffset, documentsRemainingUnderCap);
        indexManager.updateIndexEntries(nextBatch.getDocuments());
        FieldIndex.IndexOffset newOffset = getNewOffset(existingOffset, nextBatch);
        Logger.debug(LOG_TAG, "Updating offset: %s", newOffset);
        indexManager.updateCollectionGroup(collectionGroup, newOffset);
        return nextBatch.getDocuments().size();
    }

    private FieldIndex.IndexOffset getNewOffset(FieldIndex.IndexOffset existingOffset, LocalDocumentsResult lookupResult) {
        FieldIndex.IndexOffset maxOffset = existingOffset;
        Iterator<Map.Entry<DocumentKey, Document>> it = lookupResult.getDocuments().iterator();
        while (it.hasNext()) {
            FieldIndex.IndexOffset newOffset = FieldIndex.IndexOffset.fromDocument(it.next().getValue());
            if (newOffset.compareTo(maxOffset) > 0) {
                maxOffset = newOffset;
            }
        }
        return FieldIndex.IndexOffset.create(maxOffset.getReadTime(), maxOffset.getDocumentKey(), Math.max(lookupResult.getBatchId(), existingOffset.getLargestBatchId()));
    }

    /* access modifiers changed from: package-private */
    public void setMaxDocumentsToProcess(int newMax) {
        this.maxDocumentsToProcess = newMax;
    }
}
