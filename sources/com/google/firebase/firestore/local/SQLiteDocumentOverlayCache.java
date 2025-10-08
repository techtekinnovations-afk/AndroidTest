package com.google.firebase.firestore.local;

import android.database.Cursor;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.local.SQLitePersistence;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.Overlay;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.BackgroundQueue;
import com.google.firebase.firestore.util.Executors;
import com.google.firebase.firestore.util.Preconditions;
import com.google.firestore.v1.Write;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

public class SQLiteDocumentOverlayCache implements DocumentOverlayCache {
    private final SQLitePersistence db;
    private final LocalSerializer serializer;
    private final String uid;

    public SQLiteDocumentOverlayCache(SQLitePersistence db2, LocalSerializer serializer2, User user) {
        this.db = db2;
        this.serializer = serializer2;
        this.uid = user.isAuthenticated() ? user.getUid() : "";
    }

    public Overlay getOverlay(DocumentKey key) {
        return (Overlay) this.db.query("SELECT overlay_mutation, largest_batch_id FROM document_overlays WHERE uid = ? AND collection_path = ? AND document_id = ?").binding(this.uid, EncodedPath.encode((ResourcePath) key.getPath().popLast()), key.getPath().getLastSegment()).firstValue(new SQLiteDocumentOverlayCache$$ExternalSyntheticLambda4(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$getOverlay$0$com-google-firebase-firestore-local-SQLiteDocumentOverlayCache  reason: not valid java name */
    public /* synthetic */ Overlay m1862lambda$getOverlay$0$comgooglefirebasefirestorelocalSQLiteDocumentOverlayCache(Cursor row) {
        return decodeOverlay(row.getBlob(0), row.getInt(1));
    }

    public Map<DocumentKey, Overlay> getOverlays(SortedSet<DocumentKey> keys) {
        Assert.hardAssert(keys.comparator() == null, "getOverlays() requires natural order", new Object[0]);
        Map<DocumentKey, Overlay> result = new HashMap<>();
        BackgroundQueue backgroundQueue = new BackgroundQueue();
        ResourcePath currentCollection = ResourcePath.EMPTY;
        List<Object> accumulatedDocumentIds = new ArrayList<>();
        for (DocumentKey key : keys) {
            if (!currentCollection.equals(key.getCollectionPath())) {
                processSingleCollection(result, backgroundQueue, currentCollection, accumulatedDocumentIds);
                currentCollection = key.getCollectionPath();
                accumulatedDocumentIds.clear();
            }
            accumulatedDocumentIds.add(key.getDocumentId());
        }
        processSingleCollection(result, backgroundQueue, currentCollection, accumulatedDocumentIds);
        backgroundQueue.drain();
        return result;
    }

    private void processSingleCollection(Map<DocumentKey, Overlay> result, BackgroundQueue backgroundQueue, ResourcePath collectionPath, List<Object> documentIds) {
        if (!documentIds.isEmpty()) {
            SQLitePersistence.LongQuery longQuery = new SQLitePersistence.LongQuery(this.db, "SELECT overlay_mutation, largest_batch_id FROM document_overlays WHERE uid = ? AND collection_path = ? AND document_id IN (", Arrays.asList(new Object[]{this.uid, EncodedPath.encode(collectionPath)}), documentIds, ")");
            while (longQuery.hasMoreSubqueries()) {
                longQuery.performNextSubquery().forEach(new SQLiteDocumentOverlayCache$$ExternalSyntheticLambda5(this, backgroundQueue, result));
            }
        }
    }

    private void saveOverlay(int largestBatchId, DocumentKey key, Mutation mutation) {
        this.db.execute("INSERT OR REPLACE INTO document_overlays (uid, collection_group, collection_path, document_id, largest_batch_id, overlay_mutation) VALUES (?, ?, ?, ?, ?, ?)", this.uid, key.getCollectionGroup(), EncodedPath.encode((ResourcePath) key.getPath().popLast()), key.getPath().getLastSegment(), Integer.valueOf(largestBatchId), this.serializer.encodeMutation(mutation).toByteArray());
    }

    public void saveOverlays(int largestBatchId, Map<DocumentKey, Mutation> overlays) {
        for (Map.Entry<DocumentKey, Mutation> entry : overlays.entrySet()) {
            DocumentKey key = entry.getKey();
            saveOverlay(largestBatchId, key, (Mutation) Preconditions.checkNotNull(entry.getValue(), "null value for key: %s", key));
        }
    }

    public void removeOverlaysForBatchId(int batchId) {
        this.db.execute("DELETE FROM document_overlays WHERE uid = ? AND largest_batch_id = ?", this.uid, Integer.valueOf(batchId));
    }

    public Map<DocumentKey, Overlay> getOverlays(ResourcePath collection, int sinceBatchId) {
        Map<DocumentKey, Overlay> result = new HashMap<>();
        BackgroundQueue backgroundQueue = new BackgroundQueue();
        this.db.query("SELECT overlay_mutation, largest_batch_id FROM document_overlays WHERE uid = ? AND collection_path = ? AND largest_batch_id > ?").binding(this.uid, EncodedPath.encode(collection), Integer.valueOf(sinceBatchId)).forEach(new SQLiteDocumentOverlayCache$$ExternalSyntheticLambda0(this, backgroundQueue, result));
        backgroundQueue.drain();
        return result;
    }

    public Map<DocumentKey, Overlay> getOverlays(String collectionGroup, int sinceBatchId, int count) {
        Map<DocumentKey, Overlay> result = new HashMap<>();
        String[] lastCollectionPath = new String[1];
        String[] lastDocumentPath = new String[1];
        int[] lastLargestBatchId = new int[1];
        BackgroundQueue backgroundQueue = new BackgroundQueue();
        String str = collectionGroup;
        this.db.query("SELECT overlay_mutation, largest_batch_id, collection_path, document_id  FROM document_overlays WHERE uid = ? AND collection_group = ? AND largest_batch_id > ? ORDER BY largest_batch_id, collection_path, document_id LIMIT ?").binding(this.uid, str, Integer.valueOf(sinceBatchId), Integer.valueOf(count)).forEach(new SQLiteDocumentOverlayCache$$ExternalSyntheticLambda2(this, lastLargestBatchId, lastCollectionPath, lastDocumentPath, backgroundQueue, result));
        if (lastCollectionPath[0] == null) {
            return result;
        }
        this.db.query("SELECT overlay_mutation, largest_batch_id FROM document_overlays WHERE uid = ? AND collection_group = ? AND (collection_path > ? OR (collection_path = ? AND document_id > ?)) AND largest_batch_id = ?").binding(this.uid, str, lastCollectionPath[0], lastCollectionPath[0], lastDocumentPath[0], Integer.valueOf(lastLargestBatchId[0])).forEach(new SQLiteDocumentOverlayCache$$ExternalSyntheticLambda3(this, backgroundQueue, result));
        backgroundQueue.drain();
        return result;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$getOverlays$3$com-google-firebase-firestore-local-SQLiteDocumentOverlayCache  reason: not valid java name */
    public /* synthetic */ void m1864lambda$getOverlays$3$comgooglefirebasefirestorelocalSQLiteDocumentOverlayCache(int[] lastLargestBatchId, String[] lastCollectionPath, String[] lastDocumentPath, BackgroundQueue backgroundQueue, Map result, Cursor row) {
        lastLargestBatchId[0] = row.getInt(1);
        lastCollectionPath[0] = row.getString(2);
        lastDocumentPath[0] = row.getString(3);
        m1867lambda$processSingleCollection$1$comgooglefirebasefirestorelocalSQLiteDocumentOverlayCache(backgroundQueue, result, row);
    }

    /* access modifiers changed from: private */
    /* renamed from: processOverlaysInBackground */
    public void m1867lambda$processSingleCollection$1$comgooglefirebasefirestorelocalSQLiteDocumentOverlayCache(BackgroundQueue backgroundQueue, Map<DocumentKey, Overlay> results, Cursor row) {
        (row.isLast() ? Executors.DIRECT_EXECUTOR : backgroundQueue).execute(new SQLiteDocumentOverlayCache$$ExternalSyntheticLambda1(this, row.getBlob(0), row.getInt(1), results));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$processOverlaysInBackground$5$com-google-firebase-firestore-local-SQLiteDocumentOverlayCache  reason: not valid java name */
    public /* synthetic */ void m1866lambda$processOverlaysInBackground$5$comgooglefirebasefirestorelocalSQLiteDocumentOverlayCache(byte[] rawMutation, int largestBatchId, Map results) {
        Overlay overlay = decodeOverlay(rawMutation, largestBatchId);
        synchronized (results) {
            results.put(overlay.getKey(), overlay);
        }
    }

    private Overlay decodeOverlay(byte[] rawMutation, int largestBatchId) {
        try {
            return Overlay.create(largestBatchId, this.serializer.decodeMutation(Write.parseFrom(rawMutation)));
        } catch (InvalidProtocolBufferException e) {
            throw Assert.fail("Overlay failed to parse: %s", e);
        }
    }
}
