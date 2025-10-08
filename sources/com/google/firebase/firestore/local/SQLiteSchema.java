package com.google.firebase.firestore.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;
import com.google.firebase.firestore.local.MemoryIndexManager;
import com.google.firebase.firestore.local.SQLitePersistence;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.proto.Target;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Consumer;
import com.google.firebase.firestore.util.Logger;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.ArrayList;
import java.util.List;

class SQLiteSchema {
    static final int MIGRATION_BATCH_SIZE = 100;
    static final int VERSION = 18;
    private final SQLiteDatabase db;
    private final LocalSerializer serializer;

    SQLiteSchema(SQLiteDatabase db2, LocalSerializer serializer2) {
        this.db = db2;
        this.serializer = serializer2;
    }

    /* access modifiers changed from: package-private */
    public void runSchemaUpgrades() {
        runSchemaUpgrades(0);
    }

    /* access modifiers changed from: package-private */
    public void runSchemaUpgrades(int fromVersion) {
        runSchemaUpgrades(fromVersion, 18);
    }

    /* access modifiers changed from: package-private */
    public void runSchemaUpgrades(int fromVersion, int toVersion) {
        long startTime = System.currentTimeMillis();
        if (fromVersion < 1 && toVersion >= 1) {
            createV1MutationQueue();
            createV1TargetCache();
            createV1RemoteDocumentCache();
        }
        if (fromVersion < 3 && toVersion >= 3 && fromVersion != 0) {
            dropV1TargetCache();
            createV1TargetCache();
        }
        if (fromVersion < 4 && toVersion >= 4) {
            ensureTargetGlobal();
            addTargetCount();
        }
        if (fromVersion < 5 && toVersion >= 5) {
            addSequenceNumber();
        }
        if (fromVersion < 6 && toVersion >= 6) {
            removeAcknowledgedMutations();
        }
        if (fromVersion < 7 && toVersion >= 7) {
            ensureSequenceNumbers();
        }
        if (fromVersion < 8 && toVersion >= 8) {
            createV8CollectionParentsIndex();
        }
        if (fromVersion < 9 && toVersion >= 9) {
            if (!hasReadTime()) {
                addReadTime();
            } else {
                dropLastLimboFreeSnapshotVersion();
            }
        }
        if (fromVersion == 9 && toVersion >= 10) {
            dropLastLimboFreeSnapshotVersion();
        }
        if (fromVersion < 11 && toVersion >= 11) {
            rewriteCanonicalIds();
        }
        if (fromVersion < 12 && toVersion >= 12) {
            createBundleCache();
        }
        if (fromVersion < 13 && toVersion >= 13) {
            addPathLength();
            ensurePathLength();
        }
        if (fromVersion < 14 && toVersion >= 14) {
            createOverlays();
            createDataMigrationTable();
            addPendingDataMigration(Persistence.DATA_MIGRATION_BUILD_OVERLAYS);
        }
        if (fromVersion < 15 && toVersion >= 15) {
            ensureReadTime();
        }
        if (fromVersion < 16 && toVersion >= 16) {
            createFieldIndex();
        }
        if (fromVersion < 17 && toVersion >= 17) {
            createGlobalsTable();
        }
        if (fromVersion < 18 && toVersion >= 18) {
            addDocumentType();
        }
        Logger.debug("SQLiteSchema", "Migration from version %s to %s took %s milliseconds", Integer.valueOf(fromVersion), Integer.valueOf(toVersion), Long.valueOf(System.currentTimeMillis() - startTime));
    }

    private void ifTablesDontExist(String[] tables, Runnable fn) {
        String msg;
        boolean tablesFound = false;
        String allTables = "[" + TextUtils.join(", ", tables) + "]";
        for (int i = 0; i < tables.length; i++) {
            String table = tables[i];
            boolean tableFound = tableExists(table);
            if (i == 0) {
                tablesFound = tableFound;
            } else if (tableFound != tablesFound) {
                String msg2 = "Expected all of " + allTables + " to either exist or not, but ";
                if (tablesFound) {
                    msg = msg2 + tables[0] + " exists and " + table + " does not";
                } else {
                    msg = msg2 + tables[0] + " does not exist and " + table + " does";
                }
                throw new IllegalStateException(msg);
            }
        }
        if (!tablesFound) {
            fn.run();
        } else {
            Logger.debug("SQLiteSchema", "Skipping migration because all of " + allTables + " already exist", new Object[0]);
        }
    }

    private void createV1MutationQueue() {
        ifTablesDontExist(new String[]{"mutation_queues", "mutations", "document_mutations"}, new SQLiteSchema$$ExternalSyntheticLambda5(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$createV1MutationQueue$0$com-google-firebase-firestore-local-SQLiteSchema  reason: not valid java name */
    public /* synthetic */ void m1889lambda$createV1MutationQueue$0$comgooglefirebasefirestorelocalSQLiteSchema() {
        this.db.execSQL("CREATE TABLE mutation_queues (uid TEXT PRIMARY KEY, last_acknowledged_batch_id INTEGER, last_stream_token BLOB)");
        this.db.execSQL("CREATE TABLE mutations (uid TEXT, batch_id INTEGER, mutations BLOB, PRIMARY KEY (uid, batch_id))");
        this.db.execSQL("CREATE TABLE document_mutations (uid TEXT, path TEXT, batch_id INTEGER, PRIMARY KEY (uid, path, batch_id))");
    }

    private void removeAcknowledgedMutations() {
        new SQLitePersistence.Query(this.db, "SELECT uid, last_acknowledged_batch_id FROM mutation_queues").forEach(new SQLiteSchema$$ExternalSyntheticLambda3(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$removeAcknowledgedMutations$2$com-google-firebase-firestore-local-SQLiteSchema  reason: not valid java name */
    public /* synthetic */ void m1895lambda$removeAcknowledgedMutations$2$comgooglefirebasefirestorelocalSQLiteSchema(Cursor mutationQueueEntry) {
        String uid = mutationQueueEntry.getString(0);
        new SQLitePersistence.Query(this.db, "SELECT batch_id FROM mutations WHERE uid = ? AND batch_id <= ?").binding(uid, Long.valueOf(mutationQueueEntry.getLong(1))).forEach(new SQLiteSchema$$ExternalSyntheticLambda13(this, uid));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$removeAcknowledgedMutations$1$com-google-firebase-firestore-local-SQLiteSchema  reason: not valid java name */
    public /* synthetic */ void m1894lambda$removeAcknowledgedMutations$1$comgooglefirebasefirestorelocalSQLiteSchema(String uid, Cursor value) {
        removeMutationBatch(uid, value.getInt(0));
    }

    private void removeMutationBatch(String uid, int batchId) {
        SQLiteStatement mutationDeleter = this.db.compileStatement("DELETE FROM mutations WHERE uid = ? AND batch_id = ?");
        boolean z = true;
        mutationDeleter.bindString(1, uid);
        mutationDeleter.bindLong(2, (long) batchId);
        if (mutationDeleter.executeUpdateDelete() == 0) {
            z = false;
        }
        Assert.hardAssert(z, "Mutation batch (%s, %d) did not exist", uid, Integer.valueOf(batchId));
        this.db.execSQL("DELETE FROM document_mutations WHERE uid = ? AND batch_id = ?", new Object[]{uid, Integer.valueOf(batchId)});
    }

    private void createV1TargetCache() {
        ifTablesDontExist(new String[]{"targets", "target_globals", "target_documents"}, new SQLiteSchema$$ExternalSyntheticLambda9(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$createV1TargetCache$3$com-google-firebase-firestore-local-SQLiteSchema  reason: not valid java name */
    public /* synthetic */ void m1891lambda$createV1TargetCache$3$comgooglefirebasefirestorelocalSQLiteSchema() {
        this.db.execSQL("CREATE TABLE targets (target_id INTEGER PRIMARY KEY, canonical_id TEXT, snapshot_version_seconds INTEGER, snapshot_version_nanos INTEGER, resume_token BLOB, last_listen_sequence_number INTEGER,target_proto BLOB)");
        this.db.execSQL("CREATE INDEX query_targets ON targets (canonical_id, target_id)");
        this.db.execSQL("CREATE TABLE target_globals (highest_target_id INTEGER, highest_listen_sequence_number INTEGER, last_remote_snapshot_version_seconds INTEGER, last_remote_snapshot_version_nanos INTEGER)");
        this.db.execSQL("CREATE TABLE target_documents (target_id INTEGER, path TEXT, PRIMARY KEY (target_id, path))");
        this.db.execSQL("CREATE INDEX document_targets ON target_documents (path, target_id)");
    }

    private void dropV1TargetCache() {
        if (tableExists("targets")) {
            this.db.execSQL("DROP TABLE targets");
        }
        if (tableExists("target_globals")) {
            this.db.execSQL("DROP TABLE target_globals");
        }
        if (tableExists("target_documents")) {
            this.db.execSQL("DROP TABLE target_documents");
        }
    }

    private void createV1RemoteDocumentCache() {
        ifTablesDontExist(new String[]{"remote_documents"}, new SQLiteSchema$$ExternalSyntheticLambda17(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$createV1RemoteDocumentCache$4$com-google-firebase-firestore-local-SQLiteSchema  reason: not valid java name */
    public /* synthetic */ void m1890lambda$createV1RemoteDocumentCache$4$comgooglefirebasefirestorelocalSQLiteSchema() {
        this.db.execSQL("CREATE TABLE remote_documents (path TEXT PRIMARY KEY, contents BLOB)");
    }

    private void createFieldIndex() {
        ifTablesDontExist(new String[]{"index_configuration", "index_state", "index_entries"}, new SQLiteSchema$$ExternalSyntheticLambda8(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$createFieldIndex$5$com-google-firebase-firestore-local-SQLiteSchema  reason: not valid java name */
    public /* synthetic */ void m1886lambda$createFieldIndex$5$comgooglefirebasefirestorelocalSQLiteSchema() {
        this.db.execSQL("CREATE TABLE index_configuration (index_id INTEGER, collection_group TEXT, index_proto BLOB, PRIMARY KEY (index_id))");
        this.db.execSQL("CREATE TABLE index_state (index_id INTEGER, uid TEXT, sequence_number INTEGER, read_time_seconds INTEGER, read_time_nanos INTEGER, document_key TEXT, largest_batch_id INTEGER, PRIMARY KEY (index_id, uid))");
        this.db.execSQL("CREATE TABLE index_entries (index_id INTEGER, uid TEXT, array_value BLOB, directional_value BLOB, document_key TEXT, PRIMARY KEY (index_id, uid, array_value, directional_value, document_key))");
        this.db.execSQL("CREATE INDEX read_time ON remote_documents(read_time_seconds, read_time_nanos)");
    }

    private void ensureTargetGlobal() {
        if (!(DatabaseUtils.queryNumEntries(this.db, "target_globals") == 1)) {
            this.db.execSQL("INSERT INTO target_globals (highest_target_id, highest_listen_sequence_number, last_remote_snapshot_version_seconds, last_remote_snapshot_version_nanos) VALUES (?, ?, ?, ?)", new String[]{"0", "0", "0", "0"});
        }
    }

    private void addTargetCount() {
        if (!tableContainsColumn("target_globals", "target_count")) {
            this.db.execSQL("ALTER TABLE target_globals ADD COLUMN target_count INTEGER");
        }
        long count = DatabaseUtils.queryNumEntries(this.db, "targets");
        ContentValues cv = new ContentValues();
        cv.put("target_count", Long.valueOf(count));
        this.db.update("target_globals", cv, (String) null, (String[]) null);
    }

    private void addSequenceNumber() {
        if (!tableContainsColumn("target_documents", "sequence_number")) {
            this.db.execSQL("ALTER TABLE target_documents ADD COLUMN sequence_number INTEGER");
        }
    }

    private void addPathLength() {
        if (!tableContainsColumn("remote_documents", "path_length")) {
            this.db.execSQL("ALTER TABLE remote_documents ADD COLUMN path_length INTEGER");
        }
    }

    private void addDocumentType() {
        if (!tableContainsColumn("remote_documents", "document_type")) {
            this.db.execSQL("ALTER TABLE remote_documents ADD COLUMN document_type INTEGER");
        }
    }

    private boolean hasReadTime() {
        boolean hasReadTimeSeconds = tableContainsColumn("remote_documents", "read_time_seconds");
        boolean hasReadTimeNanos = tableContainsColumn("remote_documents", "read_time_nanos");
        Assert.hardAssert(hasReadTimeSeconds == hasReadTimeNanos, "Table contained just one of read_time_seconds or read_time_nanos", new Object[0]);
        if (!hasReadTimeSeconds || !hasReadTimeNanos) {
            return false;
        }
        return true;
    }

    private void addReadTime() {
        this.db.execSQL("ALTER TABLE remote_documents ADD COLUMN read_time_seconds INTEGER");
        this.db.execSQL("ALTER TABLE remote_documents ADD COLUMN read_time_nanos INTEGER");
    }

    private void dropLastLimboFreeSnapshotVersion() {
        new SQLitePersistence.Query(this.db, "SELECT target_id, target_proto FROM targets").forEach(new SQLiteSchema$$ExternalSyntheticLambda16(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$dropLastLimboFreeSnapshotVersion$6$com-google-firebase-firestore-local-SQLiteSchema  reason: not valid java name */
    public /* synthetic */ void m1893lambda$dropLastLimboFreeSnapshotVersion$6$comgooglefirebasefirestorelocalSQLiteSchema(Cursor cursor) {
        int targetId = cursor.getInt(0);
        try {
            this.db.execSQL("UPDATE targets SET target_proto = ? WHERE target_id = ?", new Object[]{((Target) ((Target.Builder) Target.parseFrom(cursor.getBlob(1)).toBuilder()).clearLastLimboFreeSnapshotVersion().build()).toByteArray(), Integer.valueOf(targetId)});
        } catch (InvalidProtocolBufferException e) {
            throw Assert.fail("Failed to decode Query data for target %s", Integer.valueOf(targetId));
        }
    }

    private void ensureSequenceNumbers() {
        Long boxedSequenceNumber = (Long) new SQLitePersistence.Query(this.db, "SELECT highest_listen_sequence_number FROM target_globals LIMIT 1").firstValue(new SQLiteSchema$$ExternalSyntheticLambda6());
        Assert.hardAssert(boxedSequenceNumber != null, "Missing highest sequence number", new Object[0]);
        long sequenceNumber = boxedSequenceNumber.longValue();
        SQLiteStatement tagDocument = this.db.compileStatement("INSERT INTO target_documents (target_id, path, sequence_number) VALUES (0, ?, ?)");
        SQLitePersistence.Query untaggedDocumentsQuery = new SQLitePersistence.Query(this.db, "SELECT RD.path FROM remote_documents AS RD WHERE NOT EXISTS (SELECT TD.path FROM target_documents AS TD WHERE RD.path = TD.path AND TD.target_id = 0) LIMIT ?").binding(100);
        boolean[] resultsRemaining = new boolean[1];
        do {
            resultsRemaining[0] = false;
            untaggedDocumentsQuery.forEach(new SQLiteSchema$$ExternalSyntheticLambda7(resultsRemaining, tagDocument, sequenceNumber));
        } while (resultsRemaining[0]);
    }

    static /* synthetic */ void lambda$ensureSequenceNumbers$8(boolean[] resultsRemaining, SQLiteStatement tagDocument, long sequenceNumber, Cursor row) {
        boolean z = true;
        resultsRemaining[0] = true;
        tagDocument.clearBindings();
        tagDocument.bindString(1, row.getString(0));
        tagDocument.bindLong(2, sequenceNumber);
        if (tagDocument.executeInsert() == -1) {
            z = false;
        }
        Assert.hardAssert(z, "Failed to insert a sentinel row", new Object[0]);
    }

    private void createV8CollectionParentsIndex() {
        ifTablesDontExist(new String[]{"collection_parents"}, new SQLiteSchema$$ExternalSyntheticLambda0(this));
        Consumer<ResourcePath> addEntry = new SQLiteSchema$$ExternalSyntheticLambda10(new MemoryIndexManager.MemoryCollectionParentIndex(), this.db.compileStatement("INSERT OR REPLACE INTO collection_parents (collection_id, parent) VALUES (?, ?)"));
        new SQLitePersistence.Query(this.db, "SELECT path FROM remote_documents").forEach(new SQLiteSchema$$ExternalSyntheticLambda11(addEntry));
        new SQLitePersistence.Query(this.db, "SELECT path FROM document_mutations").forEach(new SQLiteSchema$$ExternalSyntheticLambda12(addEntry));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$createV8CollectionParentsIndex$9$com-google-firebase-firestore-local-SQLiteSchema  reason: not valid java name */
    public /* synthetic */ void m1892lambda$createV8CollectionParentsIndex$9$comgooglefirebasefirestorelocalSQLiteSchema() {
        this.db.execSQL("CREATE TABLE collection_parents (collection_id TEXT, parent TEXT, PRIMARY KEY(collection_id, parent))");
    }

    static /* synthetic */ void lambda$createV8CollectionParentsIndex$10(MemoryIndexManager.MemoryCollectionParentIndex cache, SQLiteStatement addIndexEntry, ResourcePath collectionPath) {
        if (cache.add(collectionPath)) {
            String collectionId = collectionPath.getLastSegment();
            addIndexEntry.clearBindings();
            addIndexEntry.bindString(1, collectionId);
            addIndexEntry.bindString(2, EncodedPath.encode((ResourcePath) collectionPath.popLast()));
            addIndexEntry.execute();
        }
    }

    private boolean tableContainsColumn(String table, String column) {
        return getTableColumns(table).indexOf(column) != -1;
    }

    /* access modifiers changed from: package-private */
    public List<String> getTableColumns(String table) {
        Cursor c = null;
        List<String> columns = new ArrayList<>();
        try {
            c = this.db.rawQuery("PRAGMA table_info(" + table + ")", (String[]) null);
            int nameIndex = c.getColumnIndex("name");
            while (c.moveToNext()) {
                columns.add(c.getString(nameIndex));
            }
            return columns;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    private void rewriteCanonicalIds() {
        new SQLitePersistence.Query(this.db, "SELECT target_id, target_proto FROM targets").forEach(new SQLiteSchema$$ExternalSyntheticLambda18(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$rewriteCanonicalIds$13$com-google-firebase-firestore-local-SQLiteSchema  reason: not valid java name */
    public /* synthetic */ void m1896lambda$rewriteCanonicalIds$13$comgooglefirebasefirestorelocalSQLiteSchema(Cursor cursor) {
        int targetId = cursor.getInt(0);
        try {
            this.db.execSQL("UPDATE targets SET canonical_id  = ? WHERE target_id = ?", new Object[]{this.serializer.decodeTargetData(Target.parseFrom(cursor.getBlob(1))).getTarget().getCanonicalId(), Integer.valueOf(targetId)});
        } catch (InvalidProtocolBufferException e) {
            throw Assert.fail("Failed to decode Query data for target %s", Integer.valueOf(targetId));
        }
    }

    private void ensurePathLength() {
        SQLitePersistence.Query documentsToMigrate = new SQLitePersistence.Query(this.db, "SELECT path FROM remote_documents WHERE path_length IS NULL LIMIT ?").binding(100);
        SQLiteStatement insertKey = this.db.compileStatement("UPDATE remote_documents SET path_length = ? WHERE path = ?");
        boolean[] resultsRemaining = new boolean[1];
        do {
            resultsRemaining[0] = false;
            documentsToMigrate.forEach(new SQLiteSchema$$ExternalSyntheticLambda1(resultsRemaining, insertKey));
        } while (resultsRemaining[0]);
    }

    static /* synthetic */ void lambda$ensurePathLength$14(boolean[] resultsRemaining, SQLiteStatement insertKey, Cursor row) {
        boolean z = true;
        resultsRemaining[0] = true;
        String encodedPath = row.getString(0);
        ResourcePath decodedPath = EncodedPath.decodeResourcePath(encodedPath);
        insertKey.clearBindings();
        insertKey.bindLong(1, (long) decodedPath.length());
        insertKey.bindString(2, encodedPath);
        if (insertKey.executeUpdateDelete() == -1) {
            z = false;
        }
        Assert.hardAssert(z, "Failed to update document path", new Object[0]);
    }

    private void ensureReadTime() {
        this.db.execSQL("UPDATE remote_documents SET read_time_seconds = 0, read_time_nanos = 0 WHERE read_time_seconds IS NULL");
    }

    private void createBundleCache() {
        ifTablesDontExist(new String[]{"bundles", "named_queries"}, new SQLiteSchema$$ExternalSyntheticLambda2(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$createBundleCache$15$com-google-firebase-firestore-local-SQLiteSchema  reason: not valid java name */
    public /* synthetic */ void m1884lambda$createBundleCache$15$comgooglefirebasefirestorelocalSQLiteSchema() {
        this.db.execSQL("CREATE TABLE bundles (bundle_id TEXT PRIMARY KEY, create_time_seconds INTEGER, create_time_nanos INTEGER, schema_version INTEGER, total_documents INTEGER, total_bytes INTEGER)");
        this.db.execSQL("CREATE TABLE named_queries (name TEXT PRIMARY KEY, read_time_seconds INTEGER, read_time_nanos INTEGER, bundled_query_proto BLOB)");
    }

    private void createOverlays() {
        ifTablesDontExist(new String[]{"document_overlays"}, new SQLiteSchema$$ExternalSyntheticLambda4(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$createOverlays$16$com-google-firebase-firestore-local-SQLiteSchema  reason: not valid java name */
    public /* synthetic */ void m1888lambda$createOverlays$16$comgooglefirebasefirestorelocalSQLiteSchema() {
        this.db.execSQL("CREATE TABLE document_overlays (uid TEXT, collection_path TEXT, document_id TEXT, collection_group TEXT, largest_batch_id INTEGER, overlay_mutation BLOB, PRIMARY KEY (uid, collection_path, document_id))");
        this.db.execSQL("CREATE INDEX batch_id_overlay ON document_overlays (uid, largest_batch_id)");
        this.db.execSQL("CREATE INDEX collection_group_overlay ON document_overlays (uid, collection_group)");
    }

    private void createDataMigrationTable() {
        ifTablesDontExist(new String[]{"data_migrations"}, new SQLiteSchema$$ExternalSyntheticLambda14(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$createDataMigrationTable$17$com-google-firebase-firestore-local-SQLiteSchema  reason: not valid java name */
    public /* synthetic */ void m1885lambda$createDataMigrationTable$17$comgooglefirebasefirestorelocalSQLiteSchema() {
        this.db.execSQL("CREATE TABLE data_migrations (migration_name TEXT, PRIMARY KEY (migration_name))");
    }

    private void addPendingDataMigration(String migration) {
        this.db.execSQL("INSERT OR IGNORE INTO data_migrations (migration_name) VALUES (?)", new String[]{migration});
    }

    private void createGlobalsTable() {
        ifTablesDontExist(new String[]{"globals"}, new SQLiteSchema$$ExternalSyntheticLambda15(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$createGlobalsTable$18$com-google-firebase-firestore-local-SQLiteSchema  reason: not valid java name */
    public /* synthetic */ void m1887lambda$createGlobalsTable$18$comgooglefirebasefirestorelocalSQLiteSchema() {
        this.db.execSQL("CREATE TABLE globals (name TEXT PRIMARY KEY, value BLOB)");
    }

    private boolean tableExists(String table) {
        return !new SQLitePersistence.Query(this.db, "SELECT 1=1 FROM sqlite_master WHERE tbl_name = ?").binding(table).isEmpty();
    }
}
