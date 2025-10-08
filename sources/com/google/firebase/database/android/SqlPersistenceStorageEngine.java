package com.google.firebase.database.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.core.CompoundWrite;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.UserWriteRecord;
import com.google.firebase.database.core.persistence.PersistenceStorageEngine;
import com.google.firebase.database.core.persistence.PruneForest;
import com.google.firebase.database.core.persistence.TrackedQuery;
import com.google.firebase.database.core.utilities.ImmutableTree;
import com.google.firebase.database.core.utilities.NodeSizeEstimator;
import com.google.firebase.database.core.utilities.Pair;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.view.QuerySpec;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.ChildrenNode;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.util.JsonMapper;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class SqlPersistenceStorageEngine implements PersistenceStorageEngine {
    private static final int CHILDREN_NODE_SPLIT_SIZE_THRESHOLD = 16384;
    private static final String CREATE_SERVER_CACHE = "CREATE TABLE serverCache (path TEXT PRIMARY KEY, value BLOB);";
    private static final String CREATE_TRACKED_KEYS = "CREATE TABLE trackedKeys (id INTEGER, key TEXT);";
    private static final String CREATE_TRACKED_QUERIES = "CREATE TABLE trackedQueries (id INTEGER PRIMARY KEY, path TEXT, queryParams TEXT, lastUse INTEGER, complete INTEGER, active INTEGER);";
    private static final String CREATE_WRITES = "CREATE TABLE writes (id INTEGER, path TEXT, type TEXT, part INTEGER, node BLOB, UNIQUE (id, part));";
    private static final String FIRST_PART_KEY = ".part-0000";
    private static final String LOGGER_COMPONENT = "Persistence";
    private static final String PART_KEY_FORMAT = ".part-%04d";
    private static final String PART_KEY_PREFIX = ".part-";
    private static final String PATH_COLUMN_NAME = "path";
    private static final String ROW_ID_COLUMN_NAME = "rowid";
    private static final int ROW_SPLIT_SIZE = 262144;
    private static final String SERVER_CACHE_TABLE = "serverCache";
    private static final String TRACKED_KEYS_ID_COLUMN_NAME = "id";
    private static final String TRACKED_KEYS_KEY_COLUMN_NAME = "key";
    private static final String TRACKED_KEYS_TABLE = "trackedKeys";
    private static final String TRACKED_QUERY_ACTIVE_COLUMN_NAME = "active";
    private static final String TRACKED_QUERY_COMPLETE_COLUMN_NAME = "complete";
    private static final String TRACKED_QUERY_ID_COLUMN_NAME = "id";
    private static final String TRACKED_QUERY_LAST_USE_COLUMN_NAME = "lastUse";
    private static final String TRACKED_QUERY_PARAMS_COLUMN_NAME = "queryParams";
    private static final String TRACKED_QUERY_PATH_COLUMN_NAME = "path";
    private static final String TRACKED_QUERY_TABLE = "trackedQueries";
    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    private static final String VALUE_COLUMN_NAME = "value";
    private static final String WRITES_TABLE = "writes";
    private static final String WRITE_ID_COLUMN_NAME = "id";
    private static final String WRITE_NODE_COLUMN_NAME = "node";
    private static final String WRITE_PART_COLUMN_NAME = "part";
    private static final String WRITE_TYPE_COLUMN_NAME = "type";
    private static final String WRITE_TYPE_MERGE = "m";
    private static final String WRITE_TYPE_OVERWRITE = "o";
    private final SQLiteDatabase database;
    private boolean insideTransaction;
    private final LogWrapper logger;
    private long transactionStart = 0;

    private static class PersistentCacheOpenHelper extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 2;

        public PersistentCacheOpenHelper(Context context, String cacheId) {
            super(context, cacheId, (SQLiteDatabase.CursorFactory) null, 2);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SqlPersistenceStorageEngine.CREATE_SERVER_CACHE);
            db.execSQL(SqlPersistenceStorageEngine.CREATE_WRITES);
            db.execSQL(SqlPersistenceStorageEngine.CREATE_TRACKED_QUERIES);
            db.execSQL(SqlPersistenceStorageEngine.CREATE_TRACKED_KEYS);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Utilities.hardAssert(newVersion == 2, "Why is onUpgrade() called with a different version?");
            if (oldVersion <= 1) {
                dropTable(db, SqlPersistenceStorageEngine.SERVER_CACHE_TABLE);
                db.execSQL(SqlPersistenceStorageEngine.CREATE_SERVER_CACHE);
                dropTable(db, SqlPersistenceStorageEngine.TRACKED_QUERY_COMPLETE_COLUMN_NAME);
                db.execSQL(SqlPersistenceStorageEngine.CREATE_TRACKED_KEYS);
                db.execSQL(SqlPersistenceStorageEngine.CREATE_TRACKED_QUERIES);
                return;
            }
            throw new AssertionError("We don't handle upgrading to " + newVersion);
        }

        private void dropTable(SQLiteDatabase db, String table) {
            db.execSQL("DROP TABLE IF EXISTS " + table);
        }
    }

    public SqlPersistenceStorageEngine(Context context, com.google.firebase.database.core.Context firebaseContext, String cacheId) {
        try {
            String sanitizedCacheId = URLEncoder.encode(cacheId, "utf-8");
            this.logger = firebaseContext.getLogger(LOGGER_COMPONENT);
            this.database = openDatabase(context, sanitizedCacheId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUserOverwrite(Path path, Node node, long writeId) {
        verifyInsideTransaction();
        long start = System.currentTimeMillis();
        saveWrite(path, writeId, WRITE_TYPE_OVERWRITE, serializeObject(node.getValue(true)));
        long duration = System.currentTimeMillis() - start;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format(Locale.US, "Persisted user overwrite in %dms", new Object[]{Long.valueOf(duration)}), new Object[0]);
        }
    }

    public void saveUserMerge(Path path, CompoundWrite children, long writeId) {
        verifyInsideTransaction();
        long start = System.currentTimeMillis();
        saveWrite(path, writeId, WRITE_TYPE_MERGE, serializeObject(children.getValue(true)));
        long duration = System.currentTimeMillis() - start;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format(Locale.US, "Persisted user merge in %dms", new Object[]{Long.valueOf(duration)}), new Object[0]);
        }
    }

    public void removeUserWrite(long writeId) {
        verifyInsideTransaction();
        long start = System.currentTimeMillis();
        int count = this.database.delete(WRITES_TABLE, "id = ?", new String[]{String.valueOf(writeId)});
        long duration = System.currentTimeMillis() - start;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format(Locale.US, "Deleted %d write(s) with writeId %d in %dms", new Object[]{Integer.valueOf(count), Long.valueOf(writeId), Long.valueOf(duration)}), new Object[0]);
        }
    }

    public List<UserWriteRecord> loadUserWrites() {
        byte[] serialized;
        UserWriteRecord record;
        int i = 1;
        int i2 = 2;
        String[] columns = {"id", "path", WRITE_TYPE_COLUMN_NAME, WRITE_PART_COLUMN_NAME, WRITE_NODE_COLUMN_NAME};
        long start = System.currentTimeMillis();
        Cursor cursor = this.database.query(WRITES_TABLE, columns, (String) null, (String[]) null, (String) null, (String) null, "id, part");
        List<UserWriteRecord> writes = new ArrayList<>();
        while (cursor.moveToNext()) {
            try {
                long writeId = cursor.getLong(0);
                Path path = new Path(cursor.getString(i));
                String type = cursor.getString(i2);
                if (cursor.isNull(3)) {
                    serialized = cursor.getBlob(4);
                } else {
                    List<byte[]> parts = new ArrayList<>();
                    do {
                        parts.add(cursor.getBlob(4));
                        if (!cursor.moveToNext() || cursor.getLong(0) != writeId) {
                            cursor.moveToPrevious();
                            serialized = joinBytes(parts);
                        }
                        parts.add(cursor.getBlob(4));
                        break;
                    } while (cursor.getLong(0) != writeId);
                    cursor.moveToPrevious();
                    serialized = joinBytes(parts);
                }
                Object writeValue = JsonMapper.parseJsonValue(new String(serialized, UTF8_CHARSET));
                if (WRITE_TYPE_OVERWRITE.equals(type)) {
                    long j = writeId;
                    Path path2 = path;
                    record = new UserWriteRecord(writeId, path, NodeUtilities.NodeFromJSON(writeValue), true);
                } else {
                    long writeId2 = writeId;
                    Path path3 = path;
                    if (WRITE_TYPE_MERGE.equals(type)) {
                        record = new UserWriteRecord(writeId2, path3, CompoundWrite.fromValue((Map) writeValue));
                    } else {
                        throw new IllegalStateException("Got invalid write type: " + type);
                    }
                }
                writes.add(record);
                i = 1;
                i2 = 2;
            } catch (IOException e) {
                throw new RuntimeException("Failed to load writes", e);
            } catch (Throwable th) {
                cursor.close();
                throw th;
            }
        }
        long duration = System.currentTimeMillis() - start;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format(Locale.US, "Loaded %d writes in %dms", new Object[]{Integer.valueOf(writes.size()), Long.valueOf(duration)}), new Object[0]);
        }
        cursor.close();
        return writes;
    }

    private void saveWrite(Path path, long writeId, String type, byte[] serializedWrite) {
        String str = type;
        byte[] bArr = serializedWrite;
        verifyInsideTransaction();
        this.database.delete(WRITES_TABLE, "id = ?", new String[]{String.valueOf(writeId)});
        if (bArr.length >= 262144) {
            List<byte[]> parts = splitBytes(bArr, 262144);
            for (int i = 0; i < parts.size(); i++) {
                ContentValues values = new ContentValues();
                values.put("id", Long.valueOf(writeId));
                values.put("path", pathToKey(path));
                values.put(WRITE_TYPE_COLUMN_NAME, str);
                values.put(WRITE_PART_COLUMN_NAME, Integer.valueOf(i));
                values.put(WRITE_NODE_COLUMN_NAME, parts.get(i));
                this.database.insertWithOnConflict(WRITES_TABLE, (String) null, values, 5);
            }
            return;
        }
        ContentValues values2 = new ContentValues();
        values2.put("id", Long.valueOf(writeId));
        values2.put("path", pathToKey(path));
        values2.put(WRITE_TYPE_COLUMN_NAME, str);
        Integer num = null;
        values2.put(WRITE_PART_COLUMN_NAME, (Integer) null);
        values2.put(WRITE_NODE_COLUMN_NAME, bArr);
        this.database.insertWithOnConflict(WRITES_TABLE, (String) null, values2, 5);
    }

    public Node serverCache(Path path) {
        return loadNested(path);
    }

    public void overwriteServerCache(Path path, Node node) {
        verifyInsideTransaction();
        updateServerCache(path, node, false);
    }

    public void mergeIntoServerCache(Path path, Node node) {
        verifyInsideTransaction();
        updateServerCache(path, node, true);
    }

    private void updateServerCache(Path path, Node node, boolean merge) {
        int removedRows;
        int removedRows2;
        long start = System.currentTimeMillis();
        if (!merge) {
            removedRows2 = removeNested(SERVER_CACHE_TABLE, path);
            removedRows = saveNested(path, node);
        } else {
            int removedRows3 = 0;
            int savedRows = 0;
            Iterator it = node.iterator();
            while (it.hasNext()) {
                NamedNode child = (NamedNode) it.next();
                removedRows3 += removeNested(SERVER_CACHE_TABLE, path.child(child.getName()));
                savedRows += saveNested(path.child(child.getName()), child.getNode());
            }
            removedRows2 = removedRows3;
            removedRows = savedRows;
        }
        long duration = System.currentTimeMillis() - start;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format(Locale.US, "Persisted a total of %d rows and deleted %d rows for a set at %s in %dms", new Object[]{Integer.valueOf(removedRows), Integer.valueOf(removedRows2), path.toString(), Long.valueOf(duration)}), new Object[0]);
        }
    }

    public void mergeIntoServerCache(Path path, CompoundWrite children) {
        verifyInsideTransaction();
        long start = System.currentTimeMillis();
        int savedRows = 0;
        int removedRows = 0;
        Iterator<Map.Entry<Path, Node>> it = children.iterator();
        while (it.hasNext()) {
            Map.Entry<Path, Node> entry = it.next();
            removedRows += removeNested(SERVER_CACHE_TABLE, path.child(entry.getKey()));
            savedRows += saveNested(path.child(entry.getKey()), entry.getValue());
        }
        long duration = System.currentTimeMillis() - start;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format(Locale.US, "Persisted a total of %d rows and deleted %d rows for a merge at %s in %dms", new Object[]{Integer.valueOf(savedRows), Integer.valueOf(removedRows), path.toString(), Long.valueOf(duration)}), new Object[0]);
        }
    }

    public long serverCacheEstimatedSizeInBytes() {
        Cursor cursor = this.database.rawQuery(String.format("SELECT sum(length(%s) + length(%s)) FROM %s", new Object[]{"value", "path", SERVER_CACHE_TABLE}), (String[]) null);
        try {
            if (cursor.moveToFirst()) {
                return cursor.getLong(0);
            }
            throw new IllegalStateException("Couldn't read database result!");
        } finally {
            cursor.close();
        }
    }

    public void saveTrackedQuery(TrackedQuery trackedQuery) {
        verifyInsideTransaction();
        long start = System.currentTimeMillis();
        ContentValues values = new ContentValues();
        values.put("id", Long.valueOf(trackedQuery.id));
        values.put("path", pathToKey(trackedQuery.querySpec.getPath()));
        values.put(TRACKED_QUERY_PARAMS_COLUMN_NAME, trackedQuery.querySpec.getParams().toJSON());
        values.put(TRACKED_QUERY_LAST_USE_COLUMN_NAME, Long.valueOf(trackedQuery.lastUse));
        values.put(TRACKED_QUERY_COMPLETE_COLUMN_NAME, Boolean.valueOf(trackedQuery.complete));
        values.put(TRACKED_QUERY_ACTIVE_COLUMN_NAME, Boolean.valueOf(trackedQuery.active));
        this.database.insertWithOnConflict(TRACKED_QUERY_TABLE, (String) null, values, 5);
        long duration = System.currentTimeMillis() - start;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format(Locale.US, "Saved new tracked query in %dms", new Object[]{Long.valueOf(duration)}), new Object[0]);
        }
    }

    public void deleteTrackedQuery(long trackedQueryId) {
        verifyInsideTransaction();
        String trackedQueryIdStr = String.valueOf(trackedQueryId);
        this.database.delete(TRACKED_QUERY_TABLE, "id = ?", new String[]{trackedQueryIdStr});
        this.database.delete(TRACKED_KEYS_TABLE, "id = ?", new String[]{trackedQueryIdStr});
    }

    public List<TrackedQuery> loadTrackedQueries() {
        String[] columns = {"id", "path", TRACKED_QUERY_PARAMS_COLUMN_NAME, TRACKED_QUERY_LAST_USE_COLUMN_NAME, TRACKED_QUERY_COMPLETE_COLUMN_NAME, TRACKED_QUERY_ACTIVE_COLUMN_NAME};
        long start = System.currentTimeMillis();
        Cursor cursor = this.database.query(TRACKED_QUERY_TABLE, columns, (String) null, (String[]) null, (String) null, (String) null, "id");
        List<TrackedQuery> queries = new ArrayList<>();
        while (cursor.moveToNext()) {
            try {
                queries.add(new TrackedQuery(cursor.getLong(0), QuerySpec.fromPathAndQueryObject(new Path(cursor.getString(1)), JsonMapper.parseJson(cursor.getString(2))), cursor.getLong(3), cursor.getInt(4) != 0, cursor.getInt(5) != 0));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Throwable th) {
                cursor.close();
                throw th;
            }
        }
        long duration = System.currentTimeMillis() - start;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format(Locale.US, "Loaded %d tracked queries in %dms", new Object[]{Integer.valueOf(queries.size()), Long.valueOf(duration)}), new Object[0]);
        }
        cursor.close();
        return queries;
    }

    public void resetPreviouslyActiveTrackedQueries(long lastUse) {
        verifyInsideTransaction();
        long start = System.currentTimeMillis();
        ContentValues values = new ContentValues();
        values.put(TRACKED_QUERY_ACTIVE_COLUMN_NAME, false);
        values.put(TRACKED_QUERY_LAST_USE_COLUMN_NAME, Long.valueOf(lastUse));
        this.database.updateWithOnConflict(TRACKED_QUERY_TABLE, values, "active = 1", new String[0], 5);
        long duration = System.currentTimeMillis() - start;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format(Locale.US, "Reset active tracked queries in %dms", new Object[]{Long.valueOf(duration)}), new Object[0]);
        }
    }

    public void saveTrackedQueryKeys(long trackedQueryId, Set<ChildKey> keys) {
        verifyInsideTransaction();
        long start = System.currentTimeMillis();
        String trackedQueryIdStr = String.valueOf(trackedQueryId);
        this.database.delete(TRACKED_KEYS_TABLE, "id = ?", new String[]{trackedQueryIdStr});
        for (ChildKey addedKey : keys) {
            ContentValues values = new ContentValues();
            values.put("id", Long.valueOf(trackedQueryId));
            values.put(TRACKED_KEYS_KEY_COLUMN_NAME, addedKey.asString());
            this.database.insertWithOnConflict(TRACKED_KEYS_TABLE, (String) null, values, 5);
        }
        long duration = System.currentTimeMillis() - start;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format(Locale.US, "Set %d tracked query keys for tracked query %d in %dms", new Object[]{Integer.valueOf(keys.size()), Long.valueOf(trackedQueryId), Long.valueOf(duration)}), new Object[0]);
        }
    }

    public void updateTrackedQueryKeys(long trackedQueryId, Set<ChildKey> added, Set<ChildKey> removed) {
        verifyInsideTransaction();
        long start = System.currentTimeMillis();
        String trackedQueryIdStr = String.valueOf(trackedQueryId);
        for (ChildKey removedKey : removed) {
            this.database.delete(TRACKED_KEYS_TABLE, "id = ? AND key = ?", new String[]{trackedQueryIdStr, removedKey.asString()});
        }
        for (ChildKey addedKey : added) {
            ContentValues values = new ContentValues();
            values.put("id", Long.valueOf(trackedQueryId));
            values.put(TRACKED_KEYS_KEY_COLUMN_NAME, addedKey.asString());
            this.database.insertWithOnConflict(TRACKED_KEYS_TABLE, (String) null, values, 5);
        }
        long duration = System.currentTimeMillis() - start;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format(Locale.US, "Updated tracked query keys (%d added, %d removed) for tracked query id %d in %dms", new Object[]{Integer.valueOf(added.size()), Integer.valueOf(removed.size()), Long.valueOf(trackedQueryId), Long.valueOf(duration)}), new Object[0]);
        }
    }

    public Set<ChildKey> loadTrackedQueryKeys(long trackedQueryId) {
        return loadTrackedQueryKeys((Set<Long>) Collections.singleton(Long.valueOf(trackedQueryId)));
    }

    public Set<ChildKey> loadTrackedQueryKeys(Set<Long> trackedQueryIds) {
        long start = System.currentTimeMillis();
        Cursor cursor = this.database.query(true, TRACKED_KEYS_TABLE, new String[]{TRACKED_KEYS_KEY_COLUMN_NAME}, "id IN (" + commaSeparatedList(trackedQueryIds) + ")", (String[]) null, (String) null, (String) null, (String) null, (String) null);
        Set<ChildKey> keys = new HashSet<>();
        while (cursor.moveToNext()) {
            try {
                keys.add(ChildKey.fromString(cursor.getString(0)));
            } finally {
                cursor.close();
            }
        }
        long duration = System.currentTimeMillis() - start;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format(Locale.US, "Loaded %d tracked queries keys for tracked queries %s in %dms", new Object[]{Integer.valueOf(keys.size()), trackedQueryIds.toString(), Long.valueOf(duration)}), new Object[0]);
        }
        return keys;
    }

    public void pruneCache(Path root, PruneForest pruneForest) {
        Path path = root;
        PruneForest pruneForest2 = pruneForest;
        if (pruneForest2.prunesAnything()) {
            verifyInsideTransaction();
            long start = System.currentTimeMillis();
            int i = 0;
            int i2 = 1;
            Cursor cursor = loadNestedQuery(path, new String[]{ROW_ID_COLUMN_NAME, "path"});
            ImmutableTree<Long> rowIdsToPrune = new ImmutableTree<>(null);
            ImmutableTree<Long> rowIdsToPrune2 = rowIdsToPrune;
            ImmutableTree<Long> rowIdsToKeep = new ImmutableTree<>(null);
            while (cursor.moveToNext()) {
                long rowId = cursor.getLong(i);
                Path rowPath = new Path(cursor.getString(i2));
                if (!path.contains(rowPath)) {
                    this.logger.warn("We are pruning at " + path + " but we have data stored higher up at " + rowPath + ". Ignoring.");
                } else {
                    Path relativePath = Path.getRelative(path, rowPath);
                    if (pruneForest2.shouldPruneUnkeptDescendants(relativePath)) {
                        rowIdsToPrune2 = rowIdsToPrune2.set(relativePath, Long.valueOf(rowId));
                    } else if (pruneForest2.shouldKeep(relativePath)) {
                        rowIdsToKeep = rowIdsToKeep.set(relativePath, Long.valueOf(rowId));
                    } else {
                        this.logger.warn("We are pruning at " + path + " and have data at " + rowPath + " that isn't marked for pruning or keeping. Ignoring.");
                    }
                }
                i2 = 1;
                i = 0;
            }
            int prunedCount = 0;
            int resavedCount = 0;
            if (!rowIdsToPrune2.isEmpty()) {
                List<Pair<Path, Node>> rowsToResave = new ArrayList<>();
                pruneTreeRecursive(path, Path.getEmptyPath(), rowIdsToPrune2, rowIdsToKeep, pruneForest2, rowsToResave);
                Collection<Long> rowIdsToDelete = rowIdsToPrune2.values();
                this.database.delete(SERVER_CACHE_TABLE, "rowid IN (" + commaSeparatedList(rowIdsToDelete) + ")", (String[]) null);
                for (Pair<Path, Node> node : rowsToResave) {
                    saveNested(path.child(node.getFirst()), node.getSecond());
                }
                prunedCount = rowIdsToDelete.size();
                resavedCount = rowsToResave.size();
            }
            long duration = System.currentTimeMillis() - start;
            if (this.logger.logsDebug()) {
                this.logger.debug(String.format(Locale.US, "Pruned %d rows with %d nodes resaved in %dms", new Object[]{Integer.valueOf(prunedCount), Integer.valueOf(resavedCount), Long.valueOf(duration)}), new Object[0]);
            }
        }
    }

    private void pruneTreeRecursive(Path pruneRoot, Path relativePath, ImmutableTree<Long> rowIdsToPrune, ImmutableTree<Long> rowIdsToKeep, PruneForest pruneForest, List<Pair<Path, Node>> rowsToResaveAccumulator) {
        final ImmutableTree<Long> immutableTree = rowIdsToKeep;
        PruneForest pruneForest2 = pruneForest;
        if (rowIdsToPrune.getValue() != null) {
            int nodesToResave = ((Integer) pruneForest2.foldKeptNodes(0, new ImmutableTree.TreeVisitor<Void, Integer>() {
                public Integer onNodeValue(Path keepPath, Void ignore, Integer nodesToResave) {
                    return Integer.valueOf(immutableTree.get(keepPath) == null ? nodesToResave.intValue() + 1 : nodesToResave.intValue());
                }
            })).intValue();
            if (nodesToResave > 0) {
                Path absolutePath = pruneRoot.child(relativePath);
                if (this.logger.logsDebug()) {
                    this.logger.debug(String.format(Locale.US, "Need to rewrite %d nodes below path %s", new Object[]{Integer.valueOf(nodesToResave), absolutePath}), new Object[0]);
                }
                final Node currentNode = loadNested(absolutePath);
                final Path path = relativePath;
                final List<Pair<Path, Node>> list = rowsToResaveAccumulator;
                ImmutableTree<Long> immutableTree2 = immutableTree;
                pruneForest2.foldKeptNodes(null, new ImmutableTree.TreeVisitor<Void, Void>() {
                    public Void onNodeValue(Path keepPath, Void ignore, Void ignore2) {
                        if (immutableTree.get(keepPath) != null) {
                            return null;
                        }
                        list.add(new Pair(path.child(keepPath), currentNode.getChild(keepPath)));
                        return null;
                    }
                });
                return;
            }
            ImmutableTree<Long> immutableTree3 = immutableTree;
            return;
        }
        ImmutableTree<Long> immutableTree4 = immutableTree;
        Iterator<Map.Entry<ChildKey, ImmutableTree<Long>>> it = rowIdsToPrune.getChildren().iterator();
        while (it.hasNext()) {
            Map.Entry<ChildKey, ImmutableTree<Long>> entry = it.next();
            ChildKey childKey = entry.getKey();
            Path path2 = pruneRoot;
            pruneTreeRecursive(path2, relativePath.child(childKey), entry.getValue(), immutableTree4.getChild(childKey), pruneForest2.child(entry.getKey()), rowsToResaveAccumulator);
        }
    }

    public void removeAllUserWrites() {
        verifyInsideTransaction();
        long start = System.currentTimeMillis();
        int count = this.database.delete(WRITES_TABLE, (String) null, (String[]) null);
        long duration = System.currentTimeMillis() - start;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format(Locale.US, "Deleted %d (all) write(s) in %dms", new Object[]{Integer.valueOf(count), Long.valueOf(duration)}), new Object[0]);
        }
    }

    public void purgeCache() {
        verifyInsideTransaction();
        this.database.delete(SERVER_CACHE_TABLE, (String) null, (String[]) null);
        this.database.delete(WRITES_TABLE, (String) null, (String[]) null);
        this.database.delete(TRACKED_QUERY_TABLE, (String) null, (String[]) null);
        this.database.delete(TRACKED_KEYS_TABLE, (String) null, (String[]) null);
    }

    public void beginTransaction() {
        Utilities.hardAssert(!this.insideTransaction, "runInTransaction called when an existing transaction is already in progress.");
        if (this.logger.logsDebug()) {
            this.logger.debug("Starting transaction.", new Object[0]);
        }
        this.database.beginTransaction();
        this.insideTransaction = true;
        this.transactionStart = System.currentTimeMillis();
    }

    public void endTransaction() {
        this.database.endTransaction();
        this.insideTransaction = false;
        long elapsed = System.currentTimeMillis() - this.transactionStart;
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format(Locale.US, "Transaction completed. Elapsed: %dms", new Object[]{Long.valueOf(elapsed)}), new Object[0]);
        }
    }

    public void setTransactionSuccessful() {
        this.database.setTransactionSuccessful();
    }

    public void close() {
        this.database.close();
    }

    private SQLiteDatabase openDatabase(Context context, String cacheId) {
        try {
            SQLiteDatabase database2 = new PersistentCacheOpenHelper(context, cacheId).getWritableDatabase();
            database2.rawQuery("PRAGMA locking_mode = EXCLUSIVE", (String[]) null).close();
            database2.beginTransaction();
            database2.endTransaction();
            return database2;
        } catch (SQLiteException e) {
            if (e instanceof SQLiteDatabaseLockedException) {
                throw new DatabaseException("Failed to gain exclusive lock to Firebase Database's offline persistence. This generally means you are using Firebase Database from multiple processes in your app. Keep in mind that multi-process Android apps execute the code in your Application class in all processes, so you may need to avoid initializing FirebaseDatabase in your Application class. If you are intentionally using Firebase Database from multiple processes, you can only enable offline persistence (i.e. call setPersistenceEnabled(true)) in one of them.", e);
            }
            throw e;
        }
    }

    private void verifyInsideTransaction() {
        Utilities.hardAssert(this.insideTransaction, "Transaction expected to already be in progress.");
    }

    private int saveNested(Path path, Node node) {
        long estimatedSize = NodeSizeEstimator.estimateSerializedNodeSize(node);
        if (!(node instanceof ChildrenNode) || estimatedSize <= 16384) {
            saveNode(path, node);
            return 1;
        }
        if (this.logger.logsDebug()) {
            this.logger.debug(String.format(Locale.US, "Node estimated serialized size at path %s of %d bytes exceeds limit of %d bytes. Splitting up.", new Object[]{path, Long.valueOf(estimatedSize), 16384}), new Object[0]);
        }
        int sum = 0;
        Iterator it = node.iterator();
        while (it.hasNext()) {
            NamedNode child = (NamedNode) it.next();
            sum += saveNested(path.child(child.getName()), child.getNode());
        }
        if (!node.getPriority().isEmpty()) {
            saveNode(path.child(ChildKey.getPriorityKey()), node.getPriority());
            sum++;
        }
        saveNode(path, EmptyNode.Empty());
        return sum + 1;
    }

    private String partKey(Path path, int i) {
        return pathToKey(path) + String.format(Locale.US, PART_KEY_FORMAT, new Object[]{Integer.valueOf(i)});
    }

    private void saveNode(Path path, Node node) {
        byte[] serialized = serializeObject(node.getValue(true));
        if (serialized.length >= 262144) {
            List<byte[]> parts = splitBytes(serialized, 262144);
            if (this.logger.logsDebug()) {
                this.logger.debug("Saving huge leaf node with " + parts.size() + " parts.", new Object[0]);
            }
            for (int i = 0; i < parts.size(); i++) {
                ContentValues values = new ContentValues();
                values.put("path", partKey(path, i));
                values.put("value", parts.get(i));
                this.database.insertWithOnConflict(SERVER_CACHE_TABLE, (String) null, values, 5);
            }
            return;
        }
        ContentValues values2 = new ContentValues();
        values2.put("path", pathToKey(path));
        values2.put("value", serialized);
        this.database.insertWithOnConflict(SERVER_CACHE_TABLE, (String) null, values2, 5);
    }

    /* JADX INFO: finally extract failed */
    private Node loadNested(Path path) {
        boolean z;
        Path savedPath;
        Node savedNode;
        int splitNodeRunLength;
        Path path2 = path;
        List<String> pathStrings = new ArrayList<>();
        List<byte[]> payloads = new ArrayList<>();
        long queryStart = System.currentTimeMillis();
        int i = 0;
        boolean z2 = true;
        Cursor cursor = loadNestedQuery(path2, new String[]{"path", "value"});
        long queryDuration = System.currentTimeMillis() - queryStart;
        long loadingStart = System.currentTimeMillis();
        while (cursor.moveToNext()) {
            try {
                pathStrings.add(cursor.getString(0));
                payloads.add(cursor.getBlob(1));
            } catch (Throwable th) {
                cursor.close();
                throw th;
            }
        }
        cursor.close();
        long loadingDuration = System.currentTimeMillis() - loadingStart;
        long serializingStart = System.currentTimeMillis();
        Node node = EmptyNode.Empty();
        HashMap hashMap = new HashMap();
        int i2 = 0;
        boolean sawDescendant = false;
        while (i2 < payloads.size()) {
            if (pathStrings.get(i2).endsWith(FIRST_PART_KEY)) {
                String pathString = pathStrings.get(i2);
                savedPath = new Path(pathString.substring(i, pathString.length() - FIRST_PART_KEY.length()));
                int splitNodeRunLength2 = splitNodeRunLength(savedPath, pathStrings, i2);
                z = z2;
                if (this.logger.logsDebug()) {
                    String str = pathString;
                    splitNodeRunLength = splitNodeRunLength2;
                    this.logger.debug("Loading split node with " + splitNodeRunLength2 + " parts.", new Object[0]);
                } else {
                    splitNodeRunLength = splitNodeRunLength2;
                }
                savedNode = deserializeNode(joinBytes(payloads.subList(i2, i2 + splitNodeRunLength)));
                i2 = (i2 + splitNodeRunLength) - 1;
            } else {
                z = z2;
                savedNode = deserializeNode(payloads.get(i2));
                savedPath = new Path(pathStrings.get(i2));
            }
            if (savedPath.getBack() != null && savedPath.getBack().isPriorityChildName()) {
                hashMap.put(savedPath, savedNode);
            } else if (savedPath.contains(path2)) {
                Utilities.hardAssert(!sawDescendant, "Descendants of path must come after ancestors.");
                node = savedNode.getChild(Path.getRelative(savedPath, path2));
            } else if (path2.contains(savedPath)) {
                node = node.updateChild(Path.getRelative(path2, savedPath), savedNode);
                sawDescendant = true;
            } else {
                throw new IllegalStateException(String.format("Loading an unrelated row with path %s for %s", new Object[]{savedPath, path2}));
            }
            i2++;
            z2 = z;
            i = 0;
        }
        for (Map.Entry<Path, Node> entry : hashMap.entrySet()) {
            node = node.updateChild(Path.getRelative(path2, entry.getKey()), entry.getValue());
        }
        long serializeDuration = System.currentTimeMillis() - serializingStart;
        long duration = System.currentTimeMillis() - queryStart;
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            Locale locale = Locale.US;
            HashMap hashMap2 = hashMap;
            Locale locale2 = locale;
            Node node2 = node;
            logWrapper.debug(String.format(locale2, "Loaded a total of %d rows for a total of %d nodes at %s in %dms (Query: %dms, Loading: %dms, Serializing: %dms)", new Object[]{Integer.valueOf(payloads.size()), Integer.valueOf(NodeSizeEstimator.nodeCount(node)), path2, Long.valueOf(duration), Long.valueOf(queryDuration), Long.valueOf(loadingDuration), Long.valueOf(serializeDuration)}), new Object[0]);
            return node2;
        }
        HashMap hashMap3 = hashMap;
        return node;
    }

    private int splitNodeRunLength(Path path, List<String> pathStrings, int startPosition) {
        int endPosition = startPosition + 1;
        String pathPrefix = pathToKey(path);
        if (pathStrings.get(startPosition).startsWith(pathPrefix)) {
            while (endPosition < pathStrings.size() && pathStrings.get(endPosition).equals(partKey(path, endPosition - startPosition))) {
                endPosition++;
            }
            if (endPosition >= pathStrings.size() || !pathStrings.get(endPosition).startsWith(pathPrefix + PART_KEY_PREFIX)) {
                return endPosition - startPosition;
            }
            throw new IllegalStateException("Run did not finish with all parts");
        }
        throw new IllegalStateException("Extracting split nodes needs to start with path prefix");
    }

    private Cursor loadNestedQuery(Path path, String[] columns) {
        String pathPrefixStart = pathToKey(path);
        String pathPrefixEnd = pathPrefixStartToPrefixEnd(pathPrefixStart);
        String[] arguments = new String[(path.size() + 3)];
        String whereClause = buildAncestorWhereClause(path, arguments) + " OR (path > ? AND path < ?)";
        arguments[path.size() + 1] = pathPrefixStart;
        arguments[path.size() + 2] = pathPrefixEnd;
        return this.database.query(SERVER_CACHE_TABLE, columns, whereClause, arguments, (String) null, (String) null, "path");
    }

    private static String pathToKey(Path path) {
        if (path.isEmpty()) {
            return "/";
        }
        return path.toString() + "/";
    }

    private static String pathPrefixStartToPrefixEnd(String prefix) {
        Utilities.hardAssert(prefix.endsWith("/"), "Path keys must end with a '/'");
        return prefix.substring(0, prefix.length() - 1) + '0';
    }

    private static String buildAncestorWhereClause(Path path, String[] arguments) {
        boolean z = true;
        if (arguments.length < path.size() + 1) {
            z = false;
        }
        Utilities.hardAssert(z);
        int count = 0;
        StringBuilder whereClause = new StringBuilder("(");
        while (!path.isEmpty()) {
            whereClause.append("path");
            whereClause.append(" = ? OR ");
            arguments[count] = pathToKey(path);
            path = path.getParent();
            count++;
        }
        whereClause.append("path");
        whereClause.append(" = ?)");
        arguments[count] = pathToKey(Path.getEmptyPath());
        return whereClause.toString();
    }

    private int removeNested(String table, Path path) {
        String pathPrefixStart = pathToKey(path);
        String pathPrefixEnd = pathPrefixStartToPrefixEnd(pathPrefixStart);
        return this.database.delete(table, "path >= ? AND path < ?", new String[]{pathPrefixStart, pathPrefixEnd});
    }

    private static List<byte[]> splitBytes(byte[] bytes, int size) {
        int parts = ((bytes.length - 1) / size) + 1;
        List<byte[]> partList = new ArrayList<>(parts);
        for (int i = 0; i < parts; i++) {
            int length = Math.min(size, bytes.length - (i * size));
            byte[] part = new byte[length];
            System.arraycopy(bytes, i * size, part, 0, length);
            partList.add(part);
        }
        return partList;
    }

    private byte[] joinBytes(List<byte[]> payloads) {
        int totalSize = 0;
        for (byte[] payload : payloads) {
            totalSize += payload.length;
        }
        byte[] buffer = new byte[totalSize];
        int currentBytePosition = 0;
        for (byte[] payload2 : payloads) {
            System.arraycopy(payload2, 0, buffer, currentBytePosition, payload2.length);
            currentBytePosition += payload2.length;
        }
        return buffer;
    }

    private byte[] serializeObject(Object object) {
        try {
            return JsonMapper.serializeJsonValue(object).getBytes(UTF8_CHARSET);
        } catch (IOException e) {
            throw new RuntimeException("Could not serialize leaf node", e);
        }
    }

    private Node deserializeNode(byte[] value) {
        try {
            return NodeUtilities.NodeFromJSON(JsonMapper.parseJsonValue(new String(value, UTF8_CHARSET)));
        } catch (IOException e) {
            throw new RuntimeException("Could not deserialize node: " + new String(value, UTF8_CHARSET), e);
        }
    }

    private String commaSeparatedList(Collection<Long> items) {
        StringBuilder list = new StringBuilder();
        boolean first = true;
        for (Long longValue : items) {
            long item = longValue.longValue();
            if (!first) {
                list.append(",");
            }
            first = false;
            list.append(item);
        }
        return list.toString();
    }
}
