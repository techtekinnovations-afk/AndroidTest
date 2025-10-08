package com.google.firebase.firestore.local;

import android.database.Cursor;
import android.util.SparseArray;
import com.google.firebase.firestore.util.Consumer;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SQLiteTargetCache$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ SQLiteTargetCache f$0;
    public final /* synthetic */ SparseArray f$1;
    public final /* synthetic */ int[] f$2;

    public /* synthetic */ SQLiteTargetCache$$ExternalSyntheticLambda0(SQLiteTargetCache sQLiteTargetCache, SparseArray sparseArray, int[] iArr) {
        this.f$0 = sQLiteTargetCache;
        this.f$1 = sparseArray;
        this.f$2 = iArr;
    }

    public final void accept(Object obj) {
        this.f$0.m1899lambda$removeQueries$2$comgooglefirebasefirestorelocalSQLiteTargetCache(this.f$1, this.f$2, (Cursor) obj);
    }
}
