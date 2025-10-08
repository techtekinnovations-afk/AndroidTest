package com.google.firebase.firestore.util;

import java.util.concurrent.Callable;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class AsyncQueue$SynchronizedShutdownAwareExecutor$$ExternalSyntheticLambda0 implements Callable {
    public final /* synthetic */ Runnable f$0;

    public /* synthetic */ AsyncQueue$SynchronizedShutdownAwareExecutor$$ExternalSyntheticLambda0(Runnable runnable) {
        this.f$0 = runnable;
    }

    public final Object call() {
        return this.f$0.run();
    }
}
