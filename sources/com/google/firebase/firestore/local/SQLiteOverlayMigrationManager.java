package com.google.firebase.firestore.local;

import android.database.Cursor;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.mutation.MutationBatch;
import com.google.firebase.firestore.util.Assert;
import java.util.HashSet;
import java.util.Set;

public class SQLiteOverlayMigrationManager implements OverlayMigrationManager {
    private final SQLitePersistence db;

    public SQLiteOverlayMigrationManager(SQLitePersistence persistence) {
        this.db = persistence;
    }

    public void run() {
        buildOverlays();
    }

    private void buildOverlays() {
        this.db.runTransaction("build overlays", (Runnable) new SQLiteOverlayMigrationManager$$ExternalSyntheticLambda0(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$buildOverlays$0$com-google-firebase-firestore-local-SQLiteOverlayMigrationManager  reason: not valid java name */
    public /* synthetic */ void m1880lambda$buildOverlays$0$comgooglefirebasefirestorelocalSQLiteOverlayMigrationManager() {
        if (hasPendingOverlayMigration()) {
            Set<String> userIds = getAllUserIds();
            RemoteDocumentCache remoteDocumentCache = this.db.getRemoteDocumentCache();
            for (String uid : userIds) {
                User user = new User(uid);
                MutationQueue mutationQueue = this.db.getMutationQueue(user, this.db.getIndexManager(user));
                Set<DocumentKey> allDocumentKeys = new HashSet<>();
                for (MutationBatch batch : mutationQueue.getAllMutationBatches()) {
                    allDocumentKeys.addAll(batch.getKeys());
                }
                new LocalDocumentsView(remoteDocumentCache, mutationQueue, this.db.getDocumentOverlayCache(user), this.db.getIndexManager(user)).recalculateAndSaveOverlays(allDocumentKeys);
            }
            removePendingOverlayMigrations();
        }
    }

    private Set<String> getAllUserIds() {
        Set<String> uids = new HashSet<>();
        this.db.query("SELECT DISTINCT uid FROM mutation_queues").forEach(new SQLiteOverlayMigrationManager$$ExternalSyntheticLambda2(uids));
        return uids;
    }

    /* access modifiers changed from: package-private */
    public boolean hasPendingOverlayMigration() {
        Boolean[] result = {false};
        this.db.query("SELECT migration_name FROM data_migrations").forEach(new SQLiteOverlayMigrationManager$$ExternalSyntheticLambda1(result));
        return result[0].booleanValue();
    }

    static /* synthetic */ void lambda$hasPendingOverlayMigration$2(Boolean[] result, Cursor row) {
        try {
            if (Persistence.DATA_MIGRATION_BUILD_OVERLAYS.equals(row.getString(0))) {
                result[0] = true;
            }
        } catch (IllegalArgumentException e) {
            throw Assert.fail("SQLitePersistence.DataMigration failed to parse: %s", e);
        }
    }

    private void removePendingOverlayMigrations() {
        this.db.execute("DELETE FROM data_migrations WHERE migration_name = ?", Persistence.DATA_MIGRATION_BUILD_OVERLAYS);
    }
}
