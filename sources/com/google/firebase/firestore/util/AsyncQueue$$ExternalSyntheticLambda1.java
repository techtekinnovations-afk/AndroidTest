package com.google.firebase.firestore.util;

import com.google.firebase.firestore.util.AsyncQueue;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class AsyncQueue$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ AsyncQueue f$0;
    public final /* synthetic */ AsyncQueue.TimerId f$1;

    public /* synthetic */ AsyncQueue$$ExternalSyntheticLambda1(AsyncQueue asyncQueue, AsyncQueue.TimerId timerId) {
        this.f$0 = asyncQueue;
        this.f$1 = timerId;
    }

    public final void run() {
        this.f$0.m1925lambda$runDelayedTasksUntil$6$comgooglefirebasefirestoreutilAsyncQueue(this.f$1);
    }
}
