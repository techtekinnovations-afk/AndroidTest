package com.google.firebase.firestore.remote;

import io.grpc.ManagedChannel;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class GrpcCallProvider$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ GrpcCallProvider f$0;
    public final /* synthetic */ ManagedChannel f$1;

    public /* synthetic */ GrpcCallProvider$$ExternalSyntheticLambda2(GrpcCallProvider grpcCallProvider, ManagedChannel managedChannel) {
        this.f$0 = grpcCallProvider;
        this.f$1 = managedChannel;
    }

    public final void run() {
        this.f$0.m1918lambda$onConnectivityStateChange$2$comgooglefirebasefirestoreremoteGrpcCallProvider(this.f$1);
    }
}
