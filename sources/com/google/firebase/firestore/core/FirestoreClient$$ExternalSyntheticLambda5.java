package com.google.firebase.firestore.core;

import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FirestoreClient$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ FirestoreClient f$0;
    public final /* synthetic */ TaskCompletionSource f$1;

    public /* synthetic */ FirestoreClient$$ExternalSyntheticLambda5(FirestoreClient firestoreClient, TaskCompletionSource taskCompletionSource) {
        this.f$0 = firestoreClient;
        this.f$1 = taskCompletionSource;
    }

    public final void run() {
        this.f$0.m1832lambda$waitForPendingWrites$17$comgooglefirebasefirestorecoreFirestoreClient(this.f$1);
    }
}
