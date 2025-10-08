package com.google.firebase.firestore;

import androidx.core.util.Consumer;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.Function;
import java.util.concurrent.Executor;

final class FirestoreClientProvider {
    private AsyncQueue asyncQueue = new AsyncQueue();
    private FirestoreClient client;
    private final Function<AsyncQueue, FirestoreClient> clientFactory;

    FirestoreClientProvider(Function<AsyncQueue, FirestoreClient> clientFactory2) {
        this.clientFactory = clientFactory2;
    }

    /* access modifiers changed from: package-private */
    public boolean isConfigured() {
        return this.client != null;
    }

    /* access modifiers changed from: package-private */
    public synchronized void ensureConfigured() {
        if (!isConfigured()) {
            this.client = this.clientFactory.apply(this.asyncQueue);
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized <T> T call(Function<FirestoreClient, T> call) {
        ensureConfigured();
        return call.apply(this.client);
    }

    /* access modifiers changed from: package-private */
    public synchronized void procedure(Consumer<FirestoreClient> call) {
        ensureConfigured();
        call.accept(this.client);
    }

    /* access modifiers changed from: package-private */
    public synchronized <T> T executeIfShutdown(Function<Executor, T> callIf, Function<Executor, T> callElse) {
        Executor executor = new FirestoreClientProvider$$ExternalSyntheticLambda0(this);
        if (this.client != null) {
            if (!this.client.isTerminated()) {
                return callElse.apply(executor);
            }
        }
        return callIf.apply(executor);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$executeIfShutdown$0$com-google-firebase-firestore-FirestoreClientProvider  reason: not valid java name */
    public /* synthetic */ void m1797lambda$executeIfShutdown$0$comgooglefirebasefirestoreFirestoreClientProvider(Runnable command) {
        this.asyncQueue.enqueueAndForgetEvenAfterShutdown(command);
    }

    /* access modifiers changed from: package-private */
    public synchronized Task<Void> terminate() {
        Task<Void> terminate;
        ensureConfigured();
        terminate = this.client.terminate();
        this.asyncQueue.shutdown();
        return terminate;
    }

    /* access modifiers changed from: package-private */
    public AsyncQueue getAsyncQueue() {
        return this.asyncQueue;
    }
}
