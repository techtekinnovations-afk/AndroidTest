package com.google.firebase.firestore.util;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

class ThrottledForwardingExecutor implements Executor {
    private final Semaphore availableSlots;
    private final Executor executor;

    ThrottledForwardingExecutor(int maximumConcurrency, Executor executor2) {
        this.availableSlots = new Semaphore(maximumConcurrency);
        this.executor = executor2;
    }

    public void execute(Runnable command) {
        if (this.availableSlots.tryAcquire()) {
            try {
                this.executor.execute(new ThrottledForwardingExecutor$$ExternalSyntheticLambda0(this, command));
            } catch (RejectedExecutionException e) {
                command.run();
            }
        } else {
            command.run();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$execute$0$com-google-firebase-firestore-util-ThrottledForwardingExecutor  reason: not valid java name */
    public /* synthetic */ void m1929lambda$execute$0$comgooglefirebasefirestoreutilThrottledForwardingExecutor(Runnable command) {
        command.run();
        this.availableSlots.release();
    }
}
