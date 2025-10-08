package com.google.firebase.firestore.local;

import android.database.Cursor;
import com.google.firebase.firestore.util.BackgroundQueue;
import com.google.firebase.firestore.util.Consumer;
import com.google.firebase.firestore.util.Function;
import java.util.Map;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SQLiteRemoteDocumentCache$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ SQLiteRemoteDocumentCache f$0;
    public final /* synthetic */ BackgroundQueue f$1;
    public final /* synthetic */ Map f$2;
    public final /* synthetic */ Function f$3;
    public final /* synthetic */ QueryContext f$4;

    public /* synthetic */ SQLiteRemoteDocumentCache$$ExternalSyntheticLambda1(SQLiteRemoteDocumentCache sQLiteRemoteDocumentCache, BackgroundQueue backgroundQueue, Map map, Function function, QueryContext queryContext) {
        this.f$0 = sQLiteRemoteDocumentCache;
        this.f$1 = backgroundQueue;
        this.f$2 = map;
        this.f$3 = function;
        this.f$4 = queryContext;
    }

    public final void accept(Object obj) {
        this.f$0.m1882lambda$getAll$1$comgooglefirebasefirestorelocalSQLiteRemoteDocumentCache(this.f$1, this.f$2, this.f$3, this.f$4, (Cursor) obj);
    }
}
