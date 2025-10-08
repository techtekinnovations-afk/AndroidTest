package com.google.firebase.firestore.remote;

import com.google.firebase.database.collection.ImmutableSortedSet;
import com.google.firebase.firestore.core.DocumentViewChange;
import com.google.firebase.firestore.core.Target;
import com.google.firebase.firestore.local.QueryPurpose;
import com.google.firebase.firestore.local.TargetData;
import com.google.firebase.firestore.model.DatabaseId;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MutableDocument;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.remote.BloomFilter;
import com.google.firebase.firestore.remote.TestingHooks;
import com.google.firebase.firestore.remote.WatchChange;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Logger;
import com.google.firestore.v1.BloomFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WatchChangeAggregator {
    private static final String LOG_TAG = "WatchChangeAggregator";
    private final DatabaseId databaseId;
    private Map<DocumentKey, Set<Integer>> pendingDocumentTargetMapping = new HashMap();
    private Map<DocumentKey, MutableDocument> pendingDocumentUpdates = new HashMap();
    private Map<Integer, QueryPurpose> pendingTargetResets = new HashMap();
    private final TargetMetadataProvider targetMetadataProvider;
    private final Map<Integer, TargetState> targetStates = new HashMap();

    enum BloomFilterApplicationStatus {
        SUCCESS,
        SKIPPED,
        FALSE_POSITIVE
    }

    public interface TargetMetadataProvider {
        ImmutableSortedSet<DocumentKey> getRemoteKeysForTarget(int i);

        TargetData getTargetDataForTarget(int i);
    }

    public WatchChangeAggregator(DatabaseId databaseId2, TargetMetadataProvider targetMetadataProvider2) {
        this.databaseId = databaseId2;
        this.targetMetadataProvider = targetMetadataProvider2;
    }

    public void handleDocumentChange(WatchChange.DocumentChange documentChange) {
        MutableDocument document = documentChange.getNewDocument();
        DocumentKey documentKey = documentChange.getDocumentKey();
        for (Integer intValue : documentChange.getUpdatedTargetIds()) {
            int targetId = intValue.intValue();
            if (document == null || !document.isFoundDocument()) {
                removeDocumentFromTarget(targetId, documentKey, document);
            } else {
                addDocumentToTarget(targetId, document);
            }
        }
        for (Integer intValue2 : documentChange.getRemovedTargetIds()) {
            removeDocumentFromTarget(intValue2.intValue(), documentKey, documentChange.getNewDocument());
        }
    }

    public void handleTargetChange(WatchChange.WatchTargetChange targetChange) {
        for (Integer intValue : getTargetIds(targetChange)) {
            int targetId = intValue.intValue();
            TargetState targetState = ensureTargetState(targetId);
            switch (targetChange.getChangeType()) {
                case NoChange:
                    if (!isActiveTarget(targetId)) {
                        break;
                    } else {
                        targetState.updateResumeToken(targetChange.getResumeToken());
                        break;
                    }
                case Added:
                    targetState.recordTargetResponse();
                    if (!targetState.isPending()) {
                        targetState.clearChanges();
                    }
                    targetState.updateResumeToken(targetChange.getResumeToken());
                    break;
                case Removed:
                    targetState.recordTargetResponse();
                    if (!targetState.isPending()) {
                        removeTarget(targetId);
                    }
                    Assert.hardAssert(targetChange.getCause() == null, "WatchChangeAggregator does not handle errored targets", new Object[0]);
                    break;
                case Current:
                    if (!isActiveTarget(targetId)) {
                        break;
                    } else {
                        targetState.markCurrent();
                        targetState.updateResumeToken(targetChange.getResumeToken());
                        break;
                    }
                case Reset:
                    if (!isActiveTarget(targetId)) {
                        break;
                    } else {
                        resetTarget(targetId);
                        targetState.updateResumeToken(targetChange.getResumeToken());
                        break;
                    }
                default:
                    throw Assert.fail("Unknown target watch change state: %s", targetChange.getChangeType());
            }
        }
    }

    private Collection<Integer> getTargetIds(WatchChange.WatchTargetChange targetChange) {
        List<Integer> targetIds = targetChange.getTargetIds();
        if (!targetIds.isEmpty()) {
            return targetIds;
        }
        List<Integer> activeIds = new ArrayList<>();
        for (Integer id : this.targetStates.keySet()) {
            if (isActiveTarget(id.intValue())) {
                activeIds.add(id);
            }
        }
        return activeIds;
    }

    public void handleExistenceFilter(WatchChange.ExistenceFilterWatchChange watchChange) {
        BloomFilterApplicationStatus status;
        QueryPurpose purpose;
        int targetId = watchChange.getTargetId();
        int expectedCount = watchChange.getExistenceFilter().getCount();
        TargetData targetData = queryDataForActiveTarget(targetId);
        if (targetData != null) {
            Target target = targetData.getTarget();
            if (!target.isDocumentQuery()) {
                int currentSize = getCurrentDocumentCountForTarget(targetId);
                if (currentSize != expectedCount) {
                    BloomFilter bloomFilter = parseBloomFilter(watchChange);
                    if (bloomFilter != null) {
                        status = applyBloomFilter(bloomFilter, watchChange, currentSize);
                    } else {
                        status = BloomFilterApplicationStatus.SKIPPED;
                    }
                    if (status != BloomFilterApplicationStatus.SUCCESS) {
                        resetTarget(targetId);
                        if (status == BloomFilterApplicationStatus.FALSE_POSITIVE) {
                            purpose = QueryPurpose.EXISTENCE_FILTER_MISMATCH_BLOOM;
                        } else {
                            purpose = QueryPurpose.EXISTENCE_FILTER_MISMATCH;
                        }
                        this.pendingTargetResets.put(Integer.valueOf(targetId), purpose);
                    }
                    TestingHooks.getInstance().notifyOnExistenceFilterMismatch(TestingHooks.ExistenceFilterMismatchInfo.from(currentSize, watchChange.getExistenceFilter(), this.databaseId, bloomFilter, status));
                }
            } else if (expectedCount == 0) {
                DocumentKey key = DocumentKey.fromPath(target.getPath());
                removeDocumentFromTarget(targetId, key, MutableDocument.newNoDocument(key, SnapshotVersion.NONE));
            } else {
                boolean z = true;
                if (expectedCount != 1) {
                    z = false;
                }
                Assert.hardAssert(z, "Single document existence filter with count: %d", Integer.valueOf(expectedCount));
            }
        }
    }

    private BloomFilter parseBloomFilter(WatchChange.ExistenceFilterWatchChange watchChange) {
        BloomFilter unchangedNames = watchChange.getExistenceFilter().getUnchangedNames();
        if (unchangedNames == null || !unchangedNames.hasBits()) {
            return null;
        }
        try {
            BloomFilter bloomFilter = BloomFilter.create(unchangedNames.getBits().getBitmap(), unchangedNames.getBits().getPadding(), unchangedNames.getHashCount());
            if (bloomFilter.getBitCount() == 0) {
                return null;
            }
            return bloomFilter;
        } catch (BloomFilter.BloomFilterCreateException e) {
            Logger.warn(LOG_TAG, "Applying bloom filter failed: (" + e.getMessage() + "); ignoring the bloom filter and falling back to full re-query.", new Object[0]);
            return null;
        }
    }

    private BloomFilterApplicationStatus applyBloomFilter(BloomFilter bloomFilter, WatchChange.ExistenceFilterWatchChange watchChange, int currentCount) {
        if (watchChange.getExistenceFilter().getCount() == currentCount - filterRemovedDocuments(bloomFilter, watchChange.getTargetId())) {
            return BloomFilterApplicationStatus.SUCCESS;
        }
        return BloomFilterApplicationStatus.FALSE_POSITIVE;
    }

    private int filterRemovedDocuments(BloomFilter bloomFilter, int targetId) {
        int removalCount = 0;
        String rootDocumentsPath = "projects/" + this.databaseId.getProjectId() + "/databases/" + this.databaseId.getDatabaseId() + "/documents/";
        Iterator<DocumentKey> it = this.targetMetadataProvider.getRemoteKeysForTarget(targetId).iterator();
        while (it.hasNext()) {
            DocumentKey key = it.next();
            if (!bloomFilter.mightContain(rootDocumentsPath + key.getPath().canonicalString())) {
                removeDocumentFromTarget(targetId, key, (MutableDocument) null);
                removalCount++;
            }
        }
        return removalCount;
    }

    public RemoteEvent createRemoteEvent(SnapshotVersion snapshotVersion) {
        Map<Integer, TargetChange> targetChanges = new HashMap<>();
        for (Map.Entry<Integer, TargetState> entry : this.targetStates.entrySet()) {
            int targetId = entry.getKey().intValue();
            TargetState targetState = entry.getValue();
            TargetData targetData = queryDataForActiveTarget(targetId);
            if (targetData != null) {
                if (targetState.isCurrent() && targetData.getTarget().isDocumentQuery()) {
                    DocumentKey key = DocumentKey.fromPath(targetData.getTarget().getPath());
                    if (this.pendingDocumentUpdates.get(key) == null && !targetContainsDocument(targetId, key)) {
                        removeDocumentFromTarget(targetId, key, MutableDocument.newNoDocument(key, snapshotVersion));
                    }
                }
                if (targetState.hasChanges()) {
                    targetChanges.put(Integer.valueOf(targetId), targetState.toTargetChange());
                    targetState.clearChanges();
                }
            }
        }
        Set<DocumentKey> resolvedLimboDocuments = new HashSet<>();
        for (Map.Entry<DocumentKey, Set<Integer>> entry2 : this.pendingDocumentTargetMapping.entrySet()) {
            DocumentKey key2 = entry2.getKey();
            boolean isOnlyLimboTarget = true;
            Iterator<Integer> it = entry2.getValue().iterator();
            while (true) {
                if (it.hasNext()) {
                    TargetData targetData2 = queryDataForActiveTarget(it.next().intValue());
                    if (targetData2 != null && !targetData2.getPurpose().equals(QueryPurpose.LIMBO_RESOLUTION)) {
                        isOnlyLimboTarget = false;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (isOnlyLimboTarget) {
                resolvedLimboDocuments.add(key2);
            }
        }
        for (MutableDocument document : this.pendingDocumentUpdates.values()) {
            document.setReadTime(snapshotVersion);
        }
        RemoteEvent remoteEvent = new RemoteEvent(snapshotVersion, Collections.unmodifiableMap(targetChanges), Collections.unmodifiableMap(this.pendingTargetResets), Collections.unmodifiableMap(this.pendingDocumentUpdates), Collections.unmodifiableSet(resolvedLimboDocuments));
        this.pendingDocumentUpdates = new HashMap();
        this.pendingDocumentTargetMapping = new HashMap();
        this.pendingTargetResets = new HashMap();
        return remoteEvent;
    }

    private void addDocumentToTarget(int targetId, MutableDocument document) {
        DocumentViewChange.Type changeType;
        if (isActiveTarget(targetId)) {
            if (targetContainsDocument(targetId, document.getKey())) {
                changeType = DocumentViewChange.Type.MODIFIED;
            } else {
                changeType = DocumentViewChange.Type.ADDED;
            }
            ensureTargetState(targetId).addDocumentChange(document.getKey(), changeType);
            this.pendingDocumentUpdates.put(document.getKey(), document);
            ensureDocumentTargetMapping(document.getKey()).add(Integer.valueOf(targetId));
        }
    }

    private void removeDocumentFromTarget(int targetId, DocumentKey key, MutableDocument updatedDocument) {
        if (isActiveTarget(targetId)) {
            TargetState targetState = ensureTargetState(targetId);
            if (targetContainsDocument(targetId, key)) {
                targetState.addDocumentChange(key, DocumentViewChange.Type.REMOVED);
            } else {
                targetState.removeDocumentChange(key);
            }
            ensureDocumentTargetMapping(key).add(Integer.valueOf(targetId));
            if (updatedDocument != null) {
                this.pendingDocumentUpdates.put(key, updatedDocument);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void removeTarget(int targetId) {
        this.targetStates.remove(Integer.valueOf(targetId));
    }

    private int getCurrentDocumentCountForTarget(int targetId) {
        TargetChange targetChange = ensureTargetState(targetId).toTargetChange();
        return (this.targetMetadataProvider.getRemoteKeysForTarget(targetId).size() + targetChange.getAddedDocuments().size()) - targetChange.getRemovedDocuments().size();
    }

    /* access modifiers changed from: package-private */
    public void recordPendingTargetRequest(int targetId) {
        ensureTargetState(targetId).recordPendingTargetRequest();
    }

    private TargetState ensureTargetState(int targetId) {
        TargetState targetState = this.targetStates.get(Integer.valueOf(targetId));
        if (targetState != null) {
            return targetState;
        }
        TargetState targetState2 = new TargetState();
        this.targetStates.put(Integer.valueOf(targetId), targetState2);
        return targetState2;
    }

    private Set<Integer> ensureDocumentTargetMapping(DocumentKey key) {
        Set<Integer> targetMapping = this.pendingDocumentTargetMapping.get(key);
        if (targetMapping != null) {
            return targetMapping;
        }
        Set<Integer> targetMapping2 = new HashSet<>();
        this.pendingDocumentTargetMapping.put(key, targetMapping2);
        return targetMapping2;
    }

    private boolean isActiveTarget(int targetId) {
        return queryDataForActiveTarget(targetId) != null;
    }

    private TargetData queryDataForActiveTarget(int targetId) {
        TargetState targetState = this.targetStates.get(Integer.valueOf(targetId));
        if (targetState == null || !targetState.isPending()) {
            return this.targetMetadataProvider.getTargetDataForTarget(targetId);
        }
        return null;
    }

    private void resetTarget(int targetId) {
        Assert.hardAssert(this.targetStates.get(Integer.valueOf(targetId)) != null && !this.targetStates.get(Integer.valueOf(targetId)).isPending(), "Should only reset active targets", new Object[0]);
        this.targetStates.put(Integer.valueOf(targetId), new TargetState());
        Iterator<DocumentKey> it = this.targetMetadataProvider.getRemoteKeysForTarget(targetId).iterator();
        while (it.hasNext()) {
            removeDocumentFromTarget(targetId, it.next(), (MutableDocument) null);
        }
    }

    private boolean targetContainsDocument(int targetId, DocumentKey key) {
        return this.targetMetadataProvider.getRemoteKeysForTarget(targetId).contains(key);
    }
}
