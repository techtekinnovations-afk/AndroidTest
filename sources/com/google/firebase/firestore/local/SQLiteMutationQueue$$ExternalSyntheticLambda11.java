package com.google.firebase.firestore.local;

import android.database.Cursor;
import com.google.firebase.firestore.util.Consumer;
import java.util.List;
import java.util.Set;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SQLiteMutationQueue$$ExternalSyntheticLambda11 implements Consumer {
    public final /* synthetic */ SQLiteMutationQueue f$0;
    public final /* synthetic */ Set f$1;
    public final /* synthetic */ List f$2;

    public /* synthetic */ SQLiteMutationQueue$$ExternalSyntheticLambda11(SQLiteMutationQueue sQLiteMutationQueue, Set set, List list) {
        this.f$0 = sQLiteMutationQueue;
        this.f$1 = set;
        this.f$2 = list;
    }

    public final void accept(Object obj) {
        this.f$0.m1874lambda$getAllMutationBatchesAffectingDocumentKeys$8$comgooglefirebasefirestorelocalSQLiteMutationQueue(this.f$1, this.f$2, (Cursor) obj);
    }
}
