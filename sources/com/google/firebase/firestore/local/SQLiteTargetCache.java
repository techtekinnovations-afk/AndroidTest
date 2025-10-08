package com.google.firebase.firestore.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.util.SparseArray;
import com.google.firebase.Timestamp;
import com.google.firebase.database.collection.ImmutableSortedSet;
import com.google.firebase.firestore.core.Target;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Consumer;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.Iterator;

final class SQLiteTargetCache implements TargetCache {
    private final SQLitePersistence db;
    private int highestTargetId;
    private long lastListenSequenceNumber;
    private SnapshotVersion lastRemoteSnapshotVersion = SnapshotVersion.NONE;
    private final LocalSerializer localSerializer;
    private long targetCount;

    SQLiteTargetCache(SQLitePersistence db2, LocalSerializer localSerializer2) {
        this.db = db2;
        this.localSerializer = localSerializer2;
    }

    /* access modifiers changed from: package-private */
    public void start() {
        boolean z = true;
        if (this.db.query("SELECT highest_target_id, highest_listen_sequence_number, last_remote_snapshot_version_seconds, last_remote_snapshot_version_nanos, target_count FROM target_globals LIMIT 1").first(new SQLiteTargetCache$$ExternalSyntheticLambda3(this)) != 1) {
            z = false;
        }
        Assert.hardAssert(z, "Missing target_globals entry", new Object[0]);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$start$0$com-google-firebase-firestore-local-SQLiteTargetCache  reason: not valid java name */
    public /* synthetic */ void m1900lambda$start$0$comgooglefirebasefirestorelocalSQLiteTargetCache(Cursor row) {
        this.highestTargetId = row.getInt(0);
        this.lastListenSequenceNumber = (long) row.getInt(1);
        this.lastRemoteSnapshotVersion = new SnapshotVersion(new Timestamp(row.getLong(2), row.getInt(3)));
        this.targetCount = row.getLong(4);
    }

    public int getHighestTargetId() {
        return this.highestTargetId;
    }

    public long getHighestListenSequenceNumber() {
        return this.lastListenSequenceNumber;
    }

    public long getTargetCount() {
        return this.targetCount;
    }

    public void forEachTarget(Consumer<TargetData> consumer) {
        this.db.query("SELECT target_proto FROM targets").forEach(new SQLiteTargetCache$$ExternalSyntheticLambda1(this, consumer));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$forEachTarget$1$com-google-firebase-firestore-local-SQLiteTargetCache  reason: not valid java name */
    public /* synthetic */ void m1897lambda$forEachTarget$1$comgooglefirebasefirestorelocalSQLiteTargetCache(Consumer consumer, Cursor row) {
        consumer.accept(decodeTargetData(row.getBlob(0)));
    }

    public SnapshotVersion getLastRemoteSnapshotVersion() {
        return this.lastRemoteSnapshotVersion;
    }

    public void setLastRemoteSnapshotVersion(SnapshotVersion snapshotVersion) {
        this.lastRemoteSnapshotVersion = snapshotVersion;
        writeMetadata();
    }

    private void saveTargetData(TargetData targetData) {
        int targetId = targetData.getTargetId();
        String canonicalId = targetData.getTarget().getCanonicalId();
        Timestamp version = targetData.getSnapshotVersion().getTimestamp();
        this.db.execute("INSERT OR REPLACE INTO targets (target_id, canonical_id, snapshot_version_seconds, snapshot_version_nanos, resume_token, last_listen_sequence_number, target_proto) VALUES (?, ?, ?, ?, ?, ?, ?)", Integer.valueOf(targetId), canonicalId, Long.valueOf(version.getSeconds()), Integer.valueOf(version.getNanoseconds()), targetData.getResumeToken().toByteArray(), Long.valueOf(targetData.getSequenceNumber()), this.localSerializer.encodeTargetData(targetData).toByteArray());
    }

    private boolean updateMetadata(TargetData targetData) {
        boolean wasUpdated = false;
        if (targetData.getTargetId() > this.highestTargetId) {
            this.highestTargetId = targetData.getTargetId();
            wasUpdated = true;
        }
        if (targetData.getSequenceNumber() <= this.lastListenSequenceNumber) {
            return wasUpdated;
        }
        this.lastListenSequenceNumber = targetData.getSequenceNumber();
        return true;
    }

    public void addTargetData(TargetData targetData) {
        saveTargetData(targetData);
        updateMetadata(targetData);
        this.targetCount++;
        writeMetadata();
    }

    public void updateTargetData(TargetData targetData) {
        saveTargetData(targetData);
        if (updateMetadata(targetData)) {
            writeMetadata();
        }
    }

    private void writeMetadata() {
        this.db.execute("UPDATE target_globals SET highest_target_id = ?, highest_listen_sequence_number = ?, last_remote_snapshot_version_seconds = ?, last_remote_snapshot_version_nanos = ?, target_count = ?", Integer.valueOf(this.highestTargetId), Long.valueOf(this.lastListenSequenceNumber), Long.valueOf(this.lastRemoteSnapshotVersion.getTimestamp().getSeconds()), Integer.valueOf(this.lastRemoteSnapshotVersion.getTimestamp().getNanoseconds()), Long.valueOf(this.targetCount));
    }

    private void removeTarget(int targetId) {
        removeMatchingKeysForTargetId(targetId);
        this.db.execute("DELETE FROM targets WHERE target_id = ?", Integer.valueOf(targetId));
        this.targetCount--;
    }

    public void removeTargetData(TargetData targetData) {
        removeTarget(targetData.getTargetId());
        writeMetadata();
    }

    /* access modifiers changed from: package-private */
    public int removeQueries(long upperBound, SparseArray<?> activeTargetIds) {
        int[] count = new int[1];
        this.db.query("SELECT target_id FROM targets WHERE last_listen_sequence_number <= ?").binding(Long.valueOf(upperBound)).forEach(new SQLiteTargetCache$$ExternalSyntheticLambda0(this, activeTargetIds, count));
        writeMetadata();
        return count[0];
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$removeQueries$2$com-google-firebase-firestore-local-SQLiteTargetCache  reason: not valid java name */
    public /* synthetic */ void m1899lambda$removeQueries$2$comgooglefirebasefirestorelocalSQLiteTargetCache(SparseArray activeTargetIds, int[] count, Cursor row) {
        int targetId = row.getInt(0);
        if (activeTargetIds.get(targetId) == null) {
            removeTarget(targetId);
            count[0] = count[0] + 1;
        }
    }

    public TargetData getTargetData(Target target) {
        String canonicalId = target.getCanonicalId();
        TargetDataHolder result = new TargetDataHolder();
        this.db.query("SELECT target_proto FROM targets WHERE canonical_id = ?").binding(canonicalId).forEach(new SQLiteTargetCache$$ExternalSyntheticLambda4(this, target, result));
        return result.targetData;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$getTargetData$3$com-google-firebase-firestore-local-SQLiteTargetCache  reason: not valid java name */
    public /* synthetic */ void m1898lambda$getTargetData$3$comgooglefirebasefirestorelocalSQLiteTargetCache(Target target, TargetDataHolder result, Cursor row) {
        TargetData found = decodeTargetData(row.getBlob(0));
        if (target.equals(found.getTarget())) {
            result.targetData = found;
        }
    }

    private static class TargetDataHolder {
        TargetData targetData;

        private TargetDataHolder() {
        }
    }

    private TargetData decodeTargetData(byte[] bytes) {
        try {
            return this.localSerializer.decodeTargetData(com.google.firebase.firestore.proto.Target.parseFrom(bytes));
        } catch (InvalidProtocolBufferException e) {
            throw Assert.fail("TargetData failed to parse: %s", e);
        }
    }

    public void addMatchingKeys(ImmutableSortedSet<DocumentKey> keys, int targetId) {
        SQLiteStatement inserter = this.db.prepare("INSERT OR IGNORE INTO target_documents (target_id, path) VALUES (?, ?)");
        ReferenceDelegate delegate = this.db.getReferenceDelegate();
        Iterator<DocumentKey> it = keys.iterator();
        while (it.hasNext()) {
            DocumentKey key = it.next();
            this.db.execute(inserter, Integer.valueOf(targetId), EncodedPath.encode(key.getPath()));
            delegate.addReference(key);
        }
    }

    public void removeMatchingKeys(ImmutableSortedSet<DocumentKey> keys, int targetId) {
        SQLiteStatement deleter = this.db.prepare("DELETE FROM target_documents WHERE target_id = ? AND path = ?");
        ReferenceDelegate delegate = this.db.getReferenceDelegate();
        Iterator<DocumentKey> it = keys.iterator();
        while (it.hasNext()) {
            DocumentKey key = it.next();
            this.db.execute(deleter, Integer.valueOf(targetId), EncodedPath.encode(key.getPath()));
            delegate.removeReference(key);
        }
    }

    public void removeMatchingKeysForTargetId(int targetId) {
        this.db.execute("DELETE FROM target_documents WHERE target_id = ?", Integer.valueOf(targetId));
    }

    public ImmutableSortedSet<DocumentKey> getMatchingKeysForTargetId(int targetId) {
        DocumentKeysHolder holder = new DocumentKeysHolder();
        this.db.query("SELECT path FROM target_documents WHERE target_id = ?").binding(Integer.valueOf(targetId)).forEach(new SQLiteTargetCache$$ExternalSyntheticLambda2(holder));
        return holder.keys;
    }

    private static class DocumentKeysHolder {
        /* access modifiers changed from: package-private */
        public ImmutableSortedSet<DocumentKey> keys;

        private DocumentKeysHolder() {
            this.keys = DocumentKey.emptyKeySet();
        }
    }

    public boolean containsKey(DocumentKey key) {
        return !this.db.query("SELECT target_id FROM target_documents WHERE path = ? AND target_id != 0 LIMIT 1").binding(EncodedPath.encode(key.getPath())).isEmpty();
    }
}
