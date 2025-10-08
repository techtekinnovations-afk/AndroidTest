package com.google.firebase.firestore.core;

import com.google.firebase.firestore.ListenerRegistration;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class ActivityScope$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ ListenerRegistration f$0;

    public /* synthetic */ ActivityScope$$ExternalSyntheticLambda0(ListenerRegistration listenerRegistration) {
        this.f$0 = listenerRegistration;
    }

    public final void run() {
        this.f$0.remove();
    }
}
