package com.google.firebase.firestore.local;

import com.google.protobuf.ByteString;

public class SQLiteGlobalsCache implements GlobalsCache {
    private static final String SESSION_TOKEN = "sessionToken";
    private final SQLitePersistence db;

    public SQLiteGlobalsCache(SQLitePersistence persistence) {
        this.db = persistence;
    }

    public ByteString getSessionsToken() {
        byte[] bytes = get(SESSION_TOKEN);
        return bytes == null ? ByteString.EMPTY : ByteString.copyFrom(bytes);
    }

    public void setSessionToken(ByteString value) {
        set(SESSION_TOKEN, value.toByteArray());
    }

    private byte[] get(String name) {
        return (byte[]) this.db.query("SELECT value FROM globals WHERE name = ?").binding(name).firstValue(new SQLiteGlobalsCache$$ExternalSyntheticLambda0());
    }

    private void set(String name, byte[] value) {
        this.db.execute("INSERT OR REPLACE INTO globals (name, value) VALUES (?, ?)", name, value);
    }
}
