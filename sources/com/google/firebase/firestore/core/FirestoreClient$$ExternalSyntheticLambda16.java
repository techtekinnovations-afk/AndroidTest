package com.google.firebase.firestore.core;

import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.List;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FirestoreClient$$ExternalSyntheticLambda16 implements Runnable {
    public final /* synthetic */ FirestoreClient f$0;
    public final /* synthetic */ Query f$1;
    public final /* synthetic */ List f$2;
    public final /* synthetic */ TaskCompletionSource f$3;

    public /* synthetic */ FirestoreClient$$ExternalSyntheticLambda16(FirestoreClient firestoreClient, Query query, List list, TaskCompletionSource taskCompletionSource) {
        this.f$0 = firestoreClient;
        this.f$1 = query;
        this.f$2 = list;
        this.f$3 = taskCompletionSource;
    }

    public final void run() {
        this.f$0.m1827lambda$runAggregateQuery$16$comgooglefirebasefirestorecoreFirestoreClient(this.f$1, this.f$2, this.f$3);
    }
}
