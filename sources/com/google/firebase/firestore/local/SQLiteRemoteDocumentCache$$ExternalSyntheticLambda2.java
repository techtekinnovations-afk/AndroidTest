package com.google.firebase.firestore.local;

import android.database.Cursor;
import com.google.firebase.firestore.util.BackgroundQueue;
import com.google.firebase.firestore.util.Consumer;
import java.util.Map;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SQLiteRemoteDocumentCache$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ SQLiteRemoteDocumentCache f$0;
    public final /* synthetic */ BackgroundQueue f$1;
    public final /* synthetic */ Map f$2;

    public /* synthetic */ SQLiteRemoteDocumentCache$$ExternalSyntheticLambda2(SQLiteRemoteDocumentCache sQLiteRemoteDocumentCache, BackgroundQueue backgroundQueue, Map map) {
        this.f$0 = sQLiteRemoteDocumentCache;
        this.f$1 = backgroundQueue;
        this.f$2 = map;
    }

    public final void accept(Object obj) {
        this.f$0.m1881lambda$getAll$0$comgooglefirebasefirestorelocalSQLiteRemoteDocumentCache(this.f$1, this.f$2, (Cursor) obj);
    }
}
