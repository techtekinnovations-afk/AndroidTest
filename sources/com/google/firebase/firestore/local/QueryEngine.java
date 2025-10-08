package com.google.firebase.firestore.local;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.collection.ImmutableSortedSet;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.core.Target;
import com.google.firebase.firestore.local.IndexManager;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldIndex;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Logger;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public class QueryEngine {
    private static final int DEFAULT_INDEX_AUTO_CREATION_MIN_COLLECTION_SIZE = 100;
    private static final double DEFAULT_RELATIVE_INDEX_READ_COST_PER_DOCUMENT = 2.0d;
    private static final String LOG_TAG = "QueryEngine";
    private boolean indexAutoCreationEnabled = false;
    private int indexAutoCreationMinCollectionSize = 100;
    private IndexManager indexManager;
    private boolean initialized;
    private LocalDocumentsView localDocumentsView;
    private double relativeIndexReadCostPerDocument = DEFAULT_RELATIVE_INDEX_READ_COST_PER_DOCUMENT;

    public void initialize(LocalDocumentsView localDocumentsView2, IndexManager indexManager2) {
        this.localDocumentsView = localDocumentsView2;
        this.indexManager = indexManager2;
        this.initialized = true;
    }

    public void setIndexAutoCreationEnabled(boolean isEnabled) {
        this.indexAutoCreationEnabled = isEnabled;
    }

    public ImmutableSortedMap<DocumentKey, Document> getDocumentsMatchingQuery(Query query, SnapshotVersion lastLimboFreeSnapshotVersion, ImmutableSortedSet<DocumentKey> remoteKeys) {
        Assert.hardAssert(this.initialized, "initialize() not called", new Object[0]);
        ImmutableSortedMap<DocumentKey, Document> result = performQueryUsingIndex(query);
        if (result != null) {
            return result;
        }
        ImmutableSortedMap<DocumentKey, Document> result2 = performQueryUsingRemoteKeys(query, remoteKeys, lastLimboFreeSnapshotVersion);
        if (result2 != null) {
            return result2;
        }
        QueryContext context = new QueryContext();
        ImmutableSortedMap<DocumentKey, Document> result3 = executeFullCollectionScan(query, context);
        if (result3 != null && this.indexAutoCreationEnabled) {
            createCacheIndexes(query, context, result3.size());
        }
        return result3;
    }

    private void createCacheIndexes(Query query, QueryContext context, int resultSize) {
        if (context.getDocumentReadCount() < this.indexAutoCreationMinCollectionSize) {
            Logger.debug(LOG_TAG, "SDK will not create cache indexes for query: %s, since it only creates cache indexes for collection contains more than or equal to %s documents.", query.toString(), Integer.valueOf(this.indexAutoCreationMinCollectionSize));
            return;
        }
        Logger.debug(LOG_TAG, "Query: %s, scans %s local documents and returns %s documents as results.", query.toString(), Integer.valueOf(context.getDocumentReadCount()), Integer.valueOf(resultSize));
        if (((double) context.getDocumentReadCount()) > this.relativeIndexReadCostPerDocument * ((double) resultSize)) {
            this.indexManager.createTargetIndexes(query.toTarget());
            Logger.debug(LOG_TAG, "The SDK decides to create cache indexes for query: %s, as using cache indexes may help improve performance.", query.toString());
        }
    }

    @Nullable
    private ImmutableSortedMap<DocumentKey, Document> performQueryUsingIndex(Query query) {
        if (query.matchesAllDocuments()) {
            return null;
        }
        Target target = query.toTarget();
        IndexManager.IndexType indexType = this.indexManager.getIndexType(target);
        if (indexType.equals(IndexManager.IndexType.NONE)) {
            return null;
        }
        if (query.hasLimit() && indexType.equals(IndexManager.IndexType.PARTIAL)) {
            return performQueryUsingIndex(query.limitToFirst(-1));
        }
        List<DocumentKey> keys = this.indexManager.getDocumentsMatchingTarget(target);
        Assert.hardAssert(keys != null, "index manager must return results for partial and full indexes.", new Object[0]);
        ImmutableSortedMap<DocumentKey, Document> indexedDocuments = this.localDocumentsView.getDocuments(keys);
        FieldIndex.IndexOffset offset = this.indexManager.getMinOffset(target);
        ImmutableSortedSet<Document> previousResults = applyQuery(query, indexedDocuments);
        if (needsRefill(query, keys.size(), previousResults, offset.getReadTime())) {
            return performQueryUsingIndex(query.limitToFirst(-1));
        }
        return appendRemainingResults(previousResults, query, offset);
    }

    @Nullable
    private ImmutableSortedMap<DocumentKey, Document> performQueryUsingRemoteKeys(Query query, ImmutableSortedSet<DocumentKey> remoteKeys, SnapshotVersion lastLimboFreeSnapshotVersion) {
        if (query.matchesAllDocuments() || lastLimboFreeSnapshotVersion.equals(SnapshotVersion.NONE)) {
            return null;
        }
        ImmutableSortedSet<Document> previousResults = applyQuery(query, this.localDocumentsView.getDocuments(remoteKeys));
        if (needsRefill(query, remoteKeys.size(), previousResults, lastLimboFreeSnapshotVersion)) {
            return null;
        }
        if (Logger.isDebugEnabled()) {
            Logger.debug(LOG_TAG, "Re-using previous result from %s to execute query: %s", lastLimboFreeSnapshotVersion.toString(), query.toString());
        }
        return appendRemainingResults(previousResults, query, FieldIndex.IndexOffset.createSuccessor(lastLimboFreeSnapshotVersion, -1));
    }

    private ImmutableSortedSet<Document> applyQuery(Query query, ImmutableSortedMap<DocumentKey, Document> documents) {
        ImmutableSortedSet<Document> queryResults = new ImmutableSortedSet<>(Collections.emptyList(), query.comparator());
        Iterator<Map.Entry<DocumentKey, Document>> it = documents.iterator();
        while (it.hasNext()) {
            Document document = it.next().getValue();
            if (query.matches(document)) {
                queryResults = queryResults.insert(document);
            }
        }
        return queryResults;
    }

    private boolean needsRefill(Query query, int expectedDocumentCount, ImmutableSortedSet<Document> sortedPreviousResults, SnapshotVersion limboFreeSnapshotVersion) {
        Document documentAtLimitEdge;
        if (!query.hasLimit()) {
            return false;
        }
        if (expectedDocumentCount != sortedPreviousResults.size()) {
            return true;
        }
        if (query.getLimitType() == Query.LimitType.LIMIT_TO_FIRST) {
            documentAtLimitEdge = sortedPreviousResults.getMaxEntry();
        } else {
            documentAtLimitEdge = sortedPreviousResults.getMinEntry();
        }
        if (documentAtLimitEdge == null) {
            return false;
        }
        if (documentAtLimitEdge.hasPendingWrites() || documentAtLimitEdge.getVersion().compareTo(limboFreeSnapshotVersion) > 0) {
            return true;
        }
        return false;
    }

    private ImmutableSortedMap<DocumentKey, Document> executeFullCollectionScan(Query query, QueryContext context) {
        if (Logger.isDebugEnabled()) {
            Logger.debug(LOG_TAG, "Using full collection scan to execute query: %s", query.toString());
        }
        return this.localDocumentsView.getDocumentsMatchingQuery(query, FieldIndex.IndexOffset.NONE, context);
    }

    private ImmutableSortedMap<DocumentKey, Document> appendRemainingResults(Iterable<Document> indexedResults, Query query, FieldIndex.IndexOffset offset) {
        ImmutableSortedMap<DocumentKey, Document> remainingResults = this.localDocumentsView.getDocumentsMatchingQuery(query, offset);
        for (Document entry : indexedResults) {
            remainingResults = remainingResults.insert(entry.getKey(), entry);
        }
        return remainingResults;
    }

    /* access modifiers changed from: package-private */
    public void setIndexAutoCreationMinCollectionSize(int newMin) {
        this.indexAutoCreationMinCollectionSize = newMin;
    }

    /* access modifiers changed from: package-private */
    public void setRelativeIndexReadCostPerDocument(double newCost) {
        this.relativeIndexReadCostPerDocument = newCost;
    }
}
