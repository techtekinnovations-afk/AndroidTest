package com.google.firebase.firestore.local;

import android.database.Cursor;
import com.google.firebase.firestore.util.Consumer;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SQLiteSchema$$ExternalSyntheticLambda13 implements Consumer {
    public final /* synthetic */ SQLiteSchema f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ SQLiteSchema$$ExternalSyntheticLambda13(SQLiteSchema sQLiteSchema, String str) {
        this.f$0 = sQLiteSchema;
        this.f$1 = str;
    }

    public final void accept(Object obj) {
        this.f$0.m1894lambda$removeAcknowledgedMutations$1$comgooglefirebasefirestorelocalSQLiteSchema(this.f$1, (Cursor) obj);
    }
}
