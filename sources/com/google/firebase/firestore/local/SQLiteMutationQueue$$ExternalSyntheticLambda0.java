package com.google.firebase.firestore.local;

import android.database.Cursor;
import com.google.firebase.firestore.util.Consumer;
import java.util.List;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SQLiteMutationQueue$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ SQLiteMutationQueue f$0;
    public final /* synthetic */ List f$1;

    public /* synthetic */ SQLiteMutationQueue$$ExternalSyntheticLambda0(SQLiteMutationQueue sQLiteMutationQueue, List list) {
        this.f$0 = sQLiteMutationQueue;
        this.f$1 = list;
    }

    public final void accept(Object obj) {
        this.f$0.m1873lambda$getAllMutationBatchesAffectingDocumentKey$7$comgooglefirebasefirestorelocalSQLiteMutationQueue(this.f$1, (Cursor) obj);
    }
}
