package com.google.firebase.firestore.local;

import android.database.Cursor;
import com.google.firebase.firestore.util.BackgroundQueue;
import com.google.firebase.firestore.util.Consumer;
import java.util.Map;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SQLiteDocumentOverlayCache$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ SQLiteDocumentOverlayCache f$0;
    public final /* synthetic */ BackgroundQueue f$1;
    public final /* synthetic */ Map f$2;

    public /* synthetic */ SQLiteDocumentOverlayCache$$ExternalSyntheticLambda3(SQLiteDocumentOverlayCache sQLiteDocumentOverlayCache, BackgroundQueue backgroundQueue, Map map) {
        this.f$0 = sQLiteDocumentOverlayCache;
        this.f$1 = backgroundQueue;
        this.f$2 = map;
    }

    public final void accept(Object obj) {
        this.f$0.m1865lambda$getOverlays$4$comgooglefirebasefirestorelocalSQLiteDocumentOverlayCache(this.f$1, this.f$2, (Cursor) obj);
    }
}
