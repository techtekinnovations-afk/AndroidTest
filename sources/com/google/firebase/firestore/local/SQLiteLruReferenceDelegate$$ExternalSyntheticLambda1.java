package com.google.firebase.firestore.local;

import android.database.Cursor;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.util.Consumer;
import java.util.List;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SQLiteLruReferenceDelegate$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ SQLiteLruReferenceDelegate f$0;
    public final /* synthetic */ int[] f$1;
    public final /* synthetic */ List f$2;
    public final /* synthetic */ ResourcePath[] f$3;

    public /* synthetic */ SQLiteLruReferenceDelegate$$ExternalSyntheticLambda1(SQLiteLruReferenceDelegate sQLiteLruReferenceDelegate, int[] iArr, List list, ResourcePath[] resourcePathArr) {
        this.f$0 = sQLiteLruReferenceDelegate;
        this.f$1 = iArr;
        this.f$2 = list;
        this.f$3 = resourcePathArr;
    }

    public final void accept(Object obj) {
        this.f$0.m1871lambda$removeOrphanedDocuments$2$comgooglefirebasefirestorelocalSQLiteLruReferenceDelegate(this.f$1, this.f$2, this.f$3, (Cursor) obj);
    }
}
