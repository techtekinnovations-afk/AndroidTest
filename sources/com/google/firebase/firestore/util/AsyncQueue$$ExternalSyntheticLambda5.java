package com.google.firebase.firestore.util;

import java.util.concurrent.Semaphore;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class AsyncQueue$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ Runnable f$0;
    public final /* synthetic */ Throwable[] f$1;
    public final /* synthetic */ Semaphore f$2;

    public /* synthetic */ AsyncQueue$$ExternalSyntheticLambda5(Runnable runnable, Throwable[] thArr, Semaphore semaphore) {
        this.f$0 = runnable;
        this.f$1 = thArr;
        this.f$2 = semaphore;
    }

    public final void run() {
        AsyncQueue.lambda$runSync$4(this.f$0, this.f$1, this.f$2);
    }
}
