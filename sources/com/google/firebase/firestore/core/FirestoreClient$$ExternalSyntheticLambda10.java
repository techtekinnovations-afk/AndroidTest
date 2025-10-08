package com.google.firebase.firestore.core;

import android.content.Context;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.remote.GrpcMetadataProvider;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FirestoreClient$$ExternalSyntheticLambda10 implements Runnable {
    public final /* synthetic */ FirestoreClient f$0;
    public final /* synthetic */ TaskCompletionSource f$1;
    public final /* synthetic */ Context f$2;
    public final /* synthetic */ ComponentProvider f$3;
    public final /* synthetic */ GrpcMetadataProvider f$4;

    public /* synthetic */ FirestoreClient$$ExternalSyntheticLambda10(FirestoreClient firestoreClient, TaskCompletionSource taskCompletionSource, Context context, ComponentProvider componentProvider, GrpcMetadataProvider grpcMetadataProvider) {
        this.f$0 = firestoreClient;
        this.f$1 = taskCompletionSource;
        this.f$2 = context;
        this.f$3 = componentProvider;
        this.f$4 = grpcMetadataProvider;
    }

    public final void run() {
        this.f$0.m1823lambda$new$0$comgooglefirebasefirestorecoreFirestoreClient(this.f$1, this.f$2, this.f$3, this.f$4);
    }
}
