package com.google.firebase.firestore.remote;

import com.google.firebase.firestore.remote.AbstractStream;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class AbstractStream$StreamObserver$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ AbstractStream.StreamObserver f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ AbstractStream$StreamObserver$$ExternalSyntheticLambda0(AbstractStream.StreamObserver streamObserver, int i, Object obj) {
        this.f$0 = streamObserver;
        this.f$1 = i;
        this.f$2 = obj;
    }

    public final void run() {
        this.f$0.m1905lambda$onNext$1$comgooglefirebasefirestoreremoteAbstractStream$StreamObserver(this.f$1, this.f$2);
    }
}
