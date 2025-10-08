package com.google.firebase.firestore.remote;

import com.google.firebase.firestore.remote.Stream;
import com.google.firebase.firestore.remote.Stream.StreamCallback;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.ExponentialBackoff;
import com.google.firebase.firestore.util.Logger;
import com.google.firebase.firestore.util.Util;
import io.grpc.ClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

abstract class AbstractStream<ReqT, RespT, CallbackT extends Stream.StreamCallback> implements Stream<CallbackT> {
    private static final long BACKOFF_CLIENT_NETWORK_FAILURE_MAX_DELAY_MS = TimeUnit.SECONDS.toMillis(10);
    private static final double BACKOFF_FACTOR = 1.5d;
    private static final long BACKOFF_INITIAL_DELAY_MS = TimeUnit.SECONDS.toMillis(1);
    private static final long BACKOFF_MAX_DELAY_MS = TimeUnit.MINUTES.toMillis(1);
    private static final long HEALTHY_TIMEOUT_MS = TimeUnit.SECONDS.toMillis(10);
    private static final long IDLE_TIMEOUT_MS = TimeUnit.MINUTES.toMillis(1);
    final ExponentialBackoff backoff;
    private ClientCall<ReqT, RespT> call;
    /* access modifiers changed from: private */
    public long closeCount = 0;
    private final FirestoreChannel firestoreChannel;
    private AsyncQueue.DelayedTask healthCheck;
    private final AsyncQueue.TimerId healthTimerId;
    private final AbstractStream<ReqT, RespT, CallbackT>.IdleTimeoutRunnable idleTimeoutRunnable;
    private AsyncQueue.DelayedTask idleTimer;
    private final AsyncQueue.TimerId idleTimerId;
    final CallbackT listener;
    private final MethodDescriptor<ReqT, RespT> methodDescriptor;
    private Stream.State state = Stream.State.Initial;
    /* access modifiers changed from: private */
    public final AsyncQueue workerQueue;

    public abstract void onFirst(RespT respt);

    public abstract void onNext(RespT respt);

    class CloseGuardedRunner {
        private final long initialCloseCount;

        CloseGuardedRunner(long initialCloseCount2) {
            this.initialCloseCount = initialCloseCount2;
        }

        /* access modifiers changed from: package-private */
        public void run(Runnable task) {
            AbstractStream.this.workerQueue.verifyIsCurrentThread();
            if (AbstractStream.this.closeCount == this.initialCloseCount) {
                task.run();
            } else {
                Logger.debug(AbstractStream.this.getClass().getSimpleName(), "stream callback skipped by CloseGuardedRunner.", new Object[0]);
            }
        }
    }

    class StreamObserver implements IncomingStreamObserver<RespT> {
        private final AbstractStream<ReqT, RespT, CallbackT>.CloseGuardedRunner dispatcher;
        private int responseCount = 0;

        StreamObserver(AbstractStream<ReqT, RespT, CallbackT>.CloseGuardedRunner dispatcher2) {
            this.dispatcher = dispatcher2;
        }

        public void onHeaders(Metadata headers) {
            this.dispatcher.run(new AbstractStream$StreamObserver$$ExternalSyntheticLambda1(this, headers));
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onHeaders$0$com-google-firebase-firestore-remote-AbstractStream$StreamObserver  reason: not valid java name */
        public /* synthetic */ void m1904lambda$onHeaders$0$comgooglefirebasefirestoreremoteAbstractStream$StreamObserver(Metadata headers) {
            if (Logger.isDebugEnabled()) {
                Map<String, String> allowlistedHeaders = new HashMap<>();
                for (String header : headers.keys()) {
                    if (Datastore.WHITE_LISTED_HEADERS.contains(header.toLowerCase(Locale.ENGLISH))) {
                        allowlistedHeaders.put(header, (String) headers.get(Metadata.Key.of(header, Metadata.ASCII_STRING_MARSHALLER)));
                    }
                }
                if (!allowlistedHeaders.isEmpty()) {
                    Logger.debug(AbstractStream.this.getClass().getSimpleName(), "(%x) Stream received headers: %s", Integer.valueOf(System.identityHashCode(AbstractStream.this)), allowlistedHeaders);
                }
            }
        }

        public void onNext(RespT response) {
            int currentResponseCount = this.responseCount + 1;
            this.dispatcher.run(new AbstractStream$StreamObserver$$ExternalSyntheticLambda0(this, currentResponseCount, response));
            this.responseCount = currentResponseCount;
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onNext$1$com-google-firebase-firestore-remote-AbstractStream$StreamObserver  reason: not valid java name */
        public /* synthetic */ void m1905lambda$onNext$1$comgooglefirebasefirestoreremoteAbstractStream$StreamObserver(int currentResponseCount, Object response) {
            if (Logger.isDebugEnabled()) {
                Logger.debug(AbstractStream.this.getClass().getSimpleName(), "(%x) Stream received (%s): %s", Integer.valueOf(System.identityHashCode(AbstractStream.this)), Integer.valueOf(currentResponseCount), response);
            }
            if (currentResponseCount == 1) {
                AbstractStream.this.onFirst(response);
            } else {
                AbstractStream.this.onNext(response);
            }
        }

        public void onOpen() {
            this.dispatcher.run(new AbstractStream$StreamObserver$$ExternalSyntheticLambda2(this));
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onOpen$2$com-google-firebase-firestore-remote-AbstractStream$StreamObserver  reason: not valid java name */
        public /* synthetic */ void m1906lambda$onOpen$2$comgooglefirebasefirestoreremoteAbstractStream$StreamObserver() {
            Logger.debug(AbstractStream.this.getClass().getSimpleName(), "(%x) Stream is open", Integer.valueOf(System.identityHashCode(AbstractStream.this)));
            AbstractStream.this.onOpen();
        }

        public void onClose(Status status) {
            this.dispatcher.run(new AbstractStream$StreamObserver$$ExternalSyntheticLambda3(this, status));
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$onClose$3$com-google-firebase-firestore-remote-AbstractStream$StreamObserver  reason: not valid java name */
        public /* synthetic */ void m1903lambda$onClose$3$comgooglefirebasefirestoreremoteAbstractStream$StreamObserver(Status status) {
            if (status.isOk()) {
                Logger.debug(AbstractStream.this.getClass().getSimpleName(), "(%x) Stream closed.", Integer.valueOf(System.identityHashCode(AbstractStream.this)));
            } else {
                Logger.warn(AbstractStream.this.getClass().getSimpleName(), "(%x) Stream closed with status: %s.", Integer.valueOf(System.identityHashCode(AbstractStream.this)), status);
            }
            AbstractStream.this.handleServerClose(status);
        }
    }

    class IdleTimeoutRunnable implements Runnable {
        IdleTimeoutRunnable() {
        }

        public void run() {
            AbstractStream.this.handleIdleCloseTimer();
        }
    }

    AbstractStream(FirestoreChannel channel, MethodDescriptor<ReqT, RespT> methodDescriptor2, AsyncQueue workerQueue2, AsyncQueue.TimerId connectionTimerId, AsyncQueue.TimerId idleTimerId2, AsyncQueue.TimerId healthTimerId2, CallbackT listener2) {
        this.firestoreChannel = channel;
        this.methodDescriptor = methodDescriptor2;
        AsyncQueue asyncQueue = workerQueue2;
        this.workerQueue = asyncQueue;
        this.idleTimerId = idleTimerId2;
        this.healthTimerId = healthTimerId2;
        this.listener = listener2;
        this.idleTimeoutRunnable = new IdleTimeoutRunnable();
        this.backoff = new ExponentialBackoff(asyncQueue, connectionTimerId, BACKOFF_INITIAL_DELAY_MS, 1.5d, BACKOFF_MAX_DELAY_MS);
    }

    public boolean isStarted() {
        this.workerQueue.verifyIsCurrentThread();
        return this.state == Stream.State.Starting || this.state == Stream.State.Backoff || isOpen();
    }

    public boolean isOpen() {
        this.workerQueue.verifyIsCurrentThread();
        return this.state == Stream.State.Open || this.state == Stream.State.Healthy;
    }

    public void start() {
        this.workerQueue.verifyIsCurrentThread();
        boolean z = true;
        Assert.hardAssert(this.call == null, "Last call still set", new Object[0]);
        Assert.hardAssert(this.idleTimer == null, "Idle timer still set", new Object[0]);
        if (this.state == Stream.State.Error) {
            performBackoff();
            return;
        }
        if (this.state != Stream.State.Initial) {
            z = false;
        }
        Assert.hardAssert(z, "Already started", new Object[0]);
        this.call = this.firestoreChannel.runBidiStreamingRpc(this.methodDescriptor, new StreamObserver(new CloseGuardedRunner(this.closeCount)));
        this.state = Stream.State.Starting;
    }

    private void close(Stream.State finalState, Status status) {
        Assert.hardAssert(isStarted(), "Only started streams should be closed.", new Object[0]);
        Assert.hardAssert(finalState == Stream.State.Error || status.isOk(), "Can't provide an error when not in an error state.", new Object[0]);
        this.workerQueue.verifyIsCurrentThread();
        if (Datastore.isMissingSslCiphers(status)) {
            Util.crashMainThread(new IllegalStateException("The Cloud Firestore client failed to establish a secure connection. This is likely a problem with your app, rather than with Cloud Firestore itself. See https://bit.ly/2XFpdma for instructions on how to enable TLS on Android 4.x devices.", status.getCause()));
        }
        cancelIdleCheck();
        cancelHealthCheck();
        this.backoff.cancel();
        this.closeCount++;
        Status.Code code = status.getCode();
        if (code == Status.Code.OK) {
            this.backoff.reset();
        } else if (code == Status.Code.RESOURCE_EXHAUSTED) {
            Logger.debug(getClass().getSimpleName(), "(%x) Using maximum backoff delay to prevent overloading the backend.", Integer.valueOf(System.identityHashCode(this)));
            this.backoff.resetToMax();
        } else if (code == Status.Code.UNAUTHENTICATED && this.state != Stream.State.Healthy) {
            this.firestoreChannel.invalidateToken();
        } else if (code == Status.Code.UNAVAILABLE && ((status.getCause() instanceof UnknownHostException) || (status.getCause() instanceof ConnectException))) {
            this.backoff.setTemporaryMaxDelay(BACKOFF_CLIENT_NETWORK_FAILURE_MAX_DELAY_MS);
        }
        if (finalState != Stream.State.Error) {
            Logger.debug(getClass().getSimpleName(), "(%x) Performing stream teardown", Integer.valueOf(System.identityHashCode(this)));
            tearDown();
        }
        if (this.call != null) {
            if (status.isOk()) {
                Logger.debug(getClass().getSimpleName(), "(%x) Closing stream client-side", Integer.valueOf(System.identityHashCode(this)));
                try {
                    this.call.halfClose();
                } catch (IllegalStateException e) {
                    Logger.debug(getClass().getSimpleName(), "(%x) Closing stream client-side result in exception: [%s]", Integer.valueOf(System.identityHashCode(this)), e);
                }
            }
            this.call = null;
        }
        this.state = finalState;
        this.listener.onClose(status);
    }

    /* access modifiers changed from: protected */
    public void tearDown() {
    }

    public void stop() {
        if (isStarted()) {
            close(Stream.State.Initial, Status.OK);
        }
    }

    public void inhibitBackoff() {
        Assert.hardAssert(!isStarted(), "Can only inhibit backoff after in a stopped state", new Object[0]);
        this.workerQueue.verifyIsCurrentThread();
        this.state = Stream.State.Initial;
        this.backoff.reset();
    }

    /* access modifiers changed from: protected */
    public void writeRequest(ReqT message) {
        this.workerQueue.verifyIsCurrentThread();
        Logger.debug(getClass().getSimpleName(), "(%x) Stream sending: %s", Integer.valueOf(System.identityHashCode(this)), message);
        cancelIdleCheck();
        this.call.sendMessage(message);
    }

    /* access modifiers changed from: private */
    public void handleIdleCloseTimer() {
        if (isOpen()) {
            close(Stream.State.Initial, Status.OK);
        }
    }

    /* access modifiers changed from: package-private */
    public void handleServerClose(Status status) {
        Assert.hardAssert(isStarted(), "Can't handle server close on non-started stream!", new Object[0]);
        close(Stream.State.Error, status);
    }

    /* access modifiers changed from: private */
    public void onOpen() {
        this.state = Stream.State.Open;
        this.listener.onOpen();
        if (this.healthCheck == null) {
            this.healthCheck = this.workerQueue.enqueueAfterDelay(this.healthTimerId, HEALTHY_TIMEOUT_MS, new AbstractStream$$ExternalSyntheticLambda1(this));
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$onOpen$0$com-google-firebase-firestore-remote-AbstractStream  reason: not valid java name */
    public /* synthetic */ void m1901lambda$onOpen$0$comgooglefirebasefirestoreremoteAbstractStream() {
        if (isOpen()) {
            this.state = Stream.State.Healthy;
        }
    }

    private void performBackoff() {
        Assert.hardAssert(this.state == Stream.State.Error, "Should only perform backoff in an error state", new Object[0]);
        this.state = Stream.State.Backoff;
        this.backoff.backoffAndRun(new AbstractStream$$ExternalSyntheticLambda0(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$performBackoff$1$com-google-firebase-firestore-remote-AbstractStream  reason: not valid java name */
    public /* synthetic */ void m1902lambda$performBackoff$1$comgooglefirebasefirestoreremoteAbstractStream() {
        Assert.hardAssert(this.state == Stream.State.Backoff, "State should still be backoff but was %s", this.state);
        this.state = Stream.State.Initial;
        start();
        Assert.hardAssert(isStarted(), "Stream should have started", new Object[0]);
    }

    /* access modifiers changed from: package-private */
    public void markIdle() {
        if (isOpen() && this.idleTimer == null) {
            this.idleTimer = this.workerQueue.enqueueAfterDelay(this.idleTimerId, IDLE_TIMEOUT_MS, this.idleTimeoutRunnable);
        }
    }

    private void cancelIdleCheck() {
        if (this.idleTimer != null) {
            this.idleTimer.cancel();
            this.idleTimer = null;
        }
    }

    private void cancelHealthCheck() {
        if (this.healthCheck != null) {
            this.healthCheck.cancel();
            this.healthCheck = null;
        }
    }
}
