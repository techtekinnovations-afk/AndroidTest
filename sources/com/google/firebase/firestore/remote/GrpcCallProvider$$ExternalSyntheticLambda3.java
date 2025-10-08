package com.google.firebase.firestore.remote;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import io.grpc.MethodDescriptor;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class GrpcCallProvider$$ExternalSyntheticLambda3 implements Continuation {
    public final /* synthetic */ GrpcCallProvider f$0;
    public final /* synthetic */ MethodDescriptor f$1;

    public /* synthetic */ GrpcCallProvider$$ExternalSyntheticLambda3(GrpcCallProvider grpcCallProvider, MethodDescriptor methodDescriptor) {
        this.f$0 = grpcCallProvider;
        this.f$1 = methodDescriptor;
    }

    public final Object then(Task task) {
        return this.f$0.m1914lambda$createClientCall$0$comgooglefirebasefirestoreremoteGrpcCallProvider(this.f$1, task);
    }
}
