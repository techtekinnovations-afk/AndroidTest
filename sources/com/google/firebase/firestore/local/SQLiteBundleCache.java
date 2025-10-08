package com.google.firebase.firestore.local;

import android.database.Cursor;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.bundle.BundleMetadata;
import com.google.firebase.firestore.bundle.NamedQuery;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.util.Assert;
import com.google.firestore.bundle.BundledQuery;
import com.google.protobuf.InvalidProtocolBufferException;

class SQLiteBundleCache implements BundleCache {
    private final SQLitePersistence db;
    private final LocalSerializer serializer;

    SQLiteBundleCache(SQLitePersistence persistence, LocalSerializer serializer2) {
        this.db = persistence;
        this.serializer = serializer2;
    }

    public BundleMetadata getBundleMetadata(String bundleId) {
        return (BundleMetadata) this.db.query("SELECT schema_version, create_time_seconds, create_time_nanos, total_documents,  total_bytes FROM bundles WHERE bundle_id = ?").binding(bundleId).firstValue(new SQLiteBundleCache$$ExternalSyntheticLambda0(bundleId));
    }

    static /* synthetic */ BundleMetadata lambda$getBundleMetadata$0(String bundleId, Cursor row) {
        if (row == null) {
            String str = bundleId;
            return null;
        }
        return new BundleMetadata(bundleId, row.getInt(0), new SnapshotVersion(new Timestamp(row.getLong(1), row.getInt(2))), row.getInt(3), row.getLong(4));
    }

    public void saveBundleMetadata(BundleMetadata metadata) {
        this.db.execute("INSERT OR REPLACE INTO bundles (bundle_id, schema_version, create_time_seconds, create_time_nanos, total_documents, total_bytes) VALUES (?, ?, ?, ?, ?, ?)", metadata.getBundleId(), Integer.valueOf(metadata.getSchemaVersion()), Long.valueOf(metadata.getCreateTime().getTimestamp().getSeconds()), Integer.valueOf(metadata.getCreateTime().getTimestamp().getNanoseconds()), Integer.valueOf(metadata.getTotalDocuments()), Long.valueOf(metadata.getTotalBytes()));
    }

    public NamedQuery getNamedQuery(String queryName) {
        return (NamedQuery) this.db.query("SELECT read_time_seconds, read_time_nanos, bundled_query_proto FROM named_queries WHERE name = ?").binding(queryName).firstValue(new SQLiteBundleCache$$ExternalSyntheticLambda1(this, queryName));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$getNamedQuery$1$com-google-firebase-firestore-local-SQLiteBundleCache  reason: not valid java name */
    public /* synthetic */ NamedQuery m1861lambda$getNamedQuery$1$comgooglefirebasefirestorelocalSQLiteBundleCache(String queryName, Cursor row) {
        if (row == null) {
            return null;
        }
        try {
            return new NamedQuery(queryName, this.serializer.decodeBundledQuery(BundledQuery.parseFrom(row.getBlob(2))), new SnapshotVersion(new Timestamp(row.getLong(0), row.getInt(1))));
        } catch (InvalidProtocolBufferException e) {
            throw Assert.fail("NamedQuery failed to parse: %s", e);
        }
    }

    public void saveNamedQuery(NamedQuery query) {
        this.db.execute("INSERT OR REPLACE INTO named_queries (name, read_time_seconds, read_time_nanos, bundled_query_proto) VALUES (?, ?, ?, ?)", query.getName(), Long.valueOf(query.getReadTime().getTimestamp().getSeconds()), Integer.valueOf(query.getReadTime().getTimestamp().getNanoseconds()), this.serializer.encodeBundledQuery(query.getBundledQuery()).toByteArray());
    }
}
