package io.grpc.stub;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.StatusRuntimeException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public final class ClientCalls {
    static final CallOptions.Key<StubType> STUB_TYPE_OPTION = CallOptions.Key.create("internal-stub-type");
    private static final Logger logger = Logger.getLogger(ClientCalls.class.getName());
    static boolean rejectRunnableOnExecutor = (!Strings.isNullOrEmpty(System.getenv("GRPC_CLIENT_CALL_REJECT_RUNNABLE")) && Boolean.parseBoolean(System.getenv("GRPC_CLIENT_CALL_REJECT_RUNNABLE")));

    enum StubType {
        BLOCKING,
        FUTURE,
        ASYNC
    }

    private ClientCalls() {
    }

    public static <ReqT, RespT> void asyncUnaryCall(ClientCall<ReqT, RespT> call, ReqT req, StreamObserver<RespT> responseObserver) {
        Preconditions.checkNotNull(responseObserver, "responseObserver");
        asyncUnaryRequestCall(call, req, responseObserver, false);
    }

    public static <ReqT, RespT> void asyncServerStreamingCall(ClientCall<ReqT, RespT> call, ReqT req, StreamObserver<RespT> responseObserver) {
        Preconditions.checkNotNull(responseObserver, "responseObserver");
        asyncUnaryRequestCall(call, req, responseObserver, true);
    }

    public static <ReqT, RespT> StreamObserver<ReqT> asyncClientStreamingCall(ClientCall<ReqT, RespT> call, StreamObserver<RespT> responseObserver) {
        Preconditions.checkNotNull(responseObserver, "responseObserver");
        return asyncStreamingRequestCall(call, responseObserver, false);
    }

    public static <ReqT, RespT> StreamObserver<ReqT> asyncBidiStreamingCall(ClientCall<ReqT, RespT> call, StreamObserver<RespT> responseObserver) {
        Preconditions.checkNotNull(responseObserver, "responseObserver");
        return asyncStreamingRequestCall(call, responseObserver, true);
    }

    public static <ReqT, RespT> RespT blockingUnaryCall(ClientCall<ReqT, RespT> call, ReqT req) {
        try {
            return getUnchecked(futureUnaryCall(call, req));
        } catch (Error | RuntimeException e) {
            throw cancelThrow(call, e);
        }
    }

    /* JADX WARNING: type inference failed for: r7v0, types: [io.grpc.MethodDescriptor, io.grpc.MethodDescriptor<ReqT, RespT>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <ReqT, RespT> RespT blockingUnaryCall(io.grpc.Channel r6, io.grpc.MethodDescriptor<ReqT, RespT> r7, io.grpc.CallOptions r8, ReqT r9) {
        /*
            io.grpc.stub.ClientCalls$ThreadlessExecutor r0 = new io.grpc.stub.ClientCalls$ThreadlessExecutor
            r0.<init>()
            r1 = 0
            io.grpc.CallOptions$Key<io.grpc.stub.ClientCalls$StubType> r2 = STUB_TYPE_OPTION
            io.grpc.stub.ClientCalls$StubType r3 = io.grpc.stub.ClientCalls.StubType.BLOCKING
            io.grpc.CallOptions r2 = r8.withOption(r2, r3)
            io.grpc.CallOptions r2 = r2.withExecutor(r0)
            io.grpc.ClientCall r2 = r6.newCall(r7, r2)
            com.google.common.util.concurrent.ListenableFuture r3 = futureUnaryCall(r2, r9)     // Catch:{ RuntimeException -> 0x0041, Error -> 0x003f }
        L_0x001a:
            boolean r4 = r3.isDone()     // Catch:{ RuntimeException -> 0x0041, Error -> 0x003f }
            if (r4 != 0) goto L_0x002c
            r0.waitAndDrain()     // Catch:{ InterruptedException -> 0x0024 }
        L_0x0023:
            goto L_0x001a
        L_0x0024:
            r4 = move-exception
            r1 = 1
            java.lang.String r5 = "Thread interrupted"
            r2.cancel(r5, r4)     // Catch:{ RuntimeException -> 0x0041, Error -> 0x003f }
            goto L_0x0023
        L_0x002c:
            r0.shutdown()     // Catch:{ RuntimeException -> 0x0041, Error -> 0x003f }
            java.lang.Object r4 = getUnchecked(r3)     // Catch:{ RuntimeException -> 0x0041, Error -> 0x003f }
            if (r1 == 0) goto L_0x003c
            java.lang.Thread r5 = java.lang.Thread.currentThread()
            r5.interrupt()
        L_0x003c:
            return r4
        L_0x003d:
            r3 = move-exception
            goto L_0x0047
        L_0x003f:
            r3 = move-exception
            goto L_0x0042
        L_0x0041:
            r3 = move-exception
        L_0x0042:
            java.lang.RuntimeException r4 = cancelThrow(r2, r3)     // Catch:{ all -> 0x003d }
            throw r4     // Catch:{ all -> 0x003d }
        L_0x0047:
            if (r1 == 0) goto L_0x0050
            java.lang.Thread r4 = java.lang.Thread.currentThread()
            r4.interrupt()
        L_0x0050:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.stub.ClientCalls.blockingUnaryCall(io.grpc.Channel, io.grpc.MethodDescriptor, io.grpc.CallOptions, java.lang.Object):java.lang.Object");
    }

    public static <ReqT, RespT> Iterator<RespT> blockingServerStreamingCall(ClientCall<ReqT, RespT> call, ReqT req) {
        BlockingResponseStream<RespT> result = new BlockingResponseStream<>(call);
        asyncUnaryRequestCall(call, req, result.listener());
        return result;
    }

    public static <ReqT, RespT> Iterator<RespT> blockingServerStreamingCall(Channel channel, MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, ReqT req) {
        ClientCall<ReqT, RespT> call = channel.newCall(method, callOptions.withOption(STUB_TYPE_OPTION, StubType.BLOCKING));
        BlockingResponseStream<RespT> result = new BlockingResponseStream<>(call);
        asyncUnaryRequestCall(call, req, result.listener());
        return result;
    }

    public static <ReqT, RespT> ListenableFuture<RespT> futureUnaryCall(ClientCall<ReqT, RespT> call, ReqT req) {
        GrpcFuture<RespT> responseFuture = new GrpcFuture<>(call);
        asyncUnaryRequestCall(call, req, new UnaryStreamToFuture(responseFuture));
        return responseFuture;
    }

    private static <V> V getUnchecked(Future<V> future) {
        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw Status.CANCELLED.withDescription("Thread interrupted").withCause(e).asRuntimeException();
        } catch (ExecutionException e2) {
            throw toStatusRuntimeException(e2.getCause());
        }
    }

    private static StatusRuntimeException toStatusRuntimeException(Throwable t) {
        Throwable cause = (Throwable) Preconditions.checkNotNull(t, "t");
        while (cause != null) {
            if (cause instanceof StatusException) {
                StatusException se = (StatusException) cause;
                return new StatusRuntimeException(se.getStatus(), se.getTrailers());
            } else if (cause instanceof StatusRuntimeException) {
                StatusRuntimeException se2 = (StatusRuntimeException) cause;
                return new StatusRuntimeException(se2.getStatus(), se2.getTrailers());
            } else {
                cause = cause.getCause();
            }
        }
        return Status.UNKNOWN.withDescription("unexpected exception").withCause(t).asRuntimeException();
    }

    private static RuntimeException cancelThrow(ClientCall<?, ?> call, Throwable t) {
        try {
            call.cancel((String) null, t);
        } catch (Error | RuntimeException e) {
            logger.log(Level.SEVERE, "RuntimeException encountered while closing call", e);
        }
        if (t instanceof RuntimeException) {
            throw ((RuntimeException) t);
        } else if (t instanceof Error) {
            throw ((Error) t);
        } else {
            throw new AssertionError(t);
        }
    }

    private static <ReqT, RespT> void asyncUnaryRequestCall(ClientCall<ReqT, RespT> call, ReqT req, StreamObserver<RespT> responseObserver, boolean streamingResponse) {
        asyncUnaryRequestCall(call, req, new StreamObserverToCallListenerAdapter(responseObserver, new CallToStreamObserverAdapter(call, streamingResponse)));
    }

    private static <ReqT, RespT> void asyncUnaryRequestCall(ClientCall<ReqT, RespT> call, ReqT req, StartableListener<RespT> responseListener) {
        startCall(call, responseListener);
        try {
            call.sendMessage(req);
            call.halfClose();
        } catch (Error | RuntimeException e) {
            throw cancelThrow(call, e);
        }
    }

    private static <ReqT, RespT> StreamObserver<ReqT> asyncStreamingRequestCall(ClientCall<ReqT, RespT> call, StreamObserver<RespT> responseObserver, boolean streamingResponse) {
        CallToStreamObserverAdapter<ReqT> adapter = new CallToStreamObserverAdapter<>(call, streamingResponse);
        startCall(call, new StreamObserverToCallListenerAdapter(responseObserver, adapter));
        return adapter;
    }

    private static <ReqT, RespT> void startCall(ClientCall<ReqT, RespT> call, StartableListener<RespT> responseListener) {
        call.start(responseListener, new Metadata());
        responseListener.onStart();
    }

    private static abstract class StartableListener<T> extends ClientCall.Listener<T> {
        /* access modifiers changed from: package-private */
        public abstract void onStart();

        private StartableListener() {
        }
    }

    private static final class CallToStreamObserverAdapter<ReqT> extends ClientCallStreamObserver<ReqT> {
        private boolean aborted = false;
        /* access modifiers changed from: private */
        public boolean autoRequestEnabled = true;
        private final ClientCall<ReqT, ?> call;
        private boolean completed = false;
        private boolean frozen;
        /* access modifiers changed from: private */
        public int initialRequest = 1;
        /* access modifiers changed from: private */
        public Runnable onReadyHandler;
        /* access modifiers changed from: private */
        public final boolean streamingResponse;

        CallToStreamObserverAdapter(ClientCall<ReqT, ?> call2, boolean streamingResponse2) {
            this.call = call2;
            this.streamingResponse = streamingResponse2;
        }

        /* access modifiers changed from: private */
        public void freeze() {
            this.frozen = true;
        }

        public void onNext(ReqT value) {
            Preconditions.checkState(!this.aborted, "Stream was terminated by error, no further calls are allowed");
            Preconditions.checkState(!this.completed, "Stream is already completed, no further calls are allowed");
            this.call.sendMessage(value);
        }

        public void onError(Throwable t) {
            this.call.cancel("Cancelled by client with StreamObserver.onError()", t);
            this.aborted = true;
        }

        public void onCompleted() {
            this.call.halfClose();
            this.completed = true;
        }

        public boolean isReady() {
            return this.call.isReady();
        }

        public void setOnReadyHandler(Runnable onReadyHandler2) {
            if (!this.frozen) {
                this.onReadyHandler = onReadyHandler2;
                return;
            }
            throw new IllegalStateException("Cannot alter onReadyHandler after call started. Use ClientResponseObserver");
        }

        public void disableAutoInboundFlowControl() {
            disableAutoRequestWithInitial(1);
        }

        public void disableAutoRequestWithInitial(int request) {
            if (!this.frozen) {
                Preconditions.checkArgument(request >= 0, "Initial requests must be non-negative");
                this.initialRequest = request;
                this.autoRequestEnabled = false;
                return;
            }
            throw new IllegalStateException("Cannot disable auto flow control after call started. Use ClientResponseObserver");
        }

        public void request(int count) {
            if (this.streamingResponse || count != 1) {
                this.call.request(count);
            } else {
                this.call.request(2);
            }
        }

        public void setMessageCompression(boolean enable) {
            this.call.setMessageCompression(enable);
        }

        public void cancel(@Nullable String message, @Nullable Throwable cause) {
            this.call.cancel(message, cause);
        }
    }

    private static final class StreamObserverToCallListenerAdapter<ReqT, RespT> extends StartableListener<RespT> {
        private final CallToStreamObserverAdapter<ReqT> adapter;
        private boolean firstResponseReceived;
        private final StreamObserver<RespT> observer;

        StreamObserverToCallListenerAdapter(StreamObserver<RespT> observer2, CallToStreamObserverAdapter<ReqT> adapter2) {
            super();
            this.observer = observer2;
            this.adapter = adapter2;
            if (observer2 instanceof ClientResponseObserver) {
                ((ClientResponseObserver) observer2).beforeStart(adapter2);
            }
            adapter2.freeze();
        }

        public void onHeaders(Metadata headers) {
        }

        public void onMessage(RespT message) {
            if (!this.firstResponseReceived || this.adapter.streamingResponse) {
                this.firstResponseReceived = true;
                this.observer.onNext(message);
                if (this.adapter.streamingResponse && this.adapter.autoRequestEnabled) {
                    this.adapter.request(1);
                    return;
                }
                return;
            }
            throw Status.INTERNAL.withDescription("More than one responses received for unary or client-streaming call").asRuntimeException();
        }

        public void onClose(Status status, Metadata trailers) {
            if (status.isOk()) {
                this.observer.onCompleted();
            } else {
                this.observer.onError(status.asRuntimeException(trailers));
            }
        }

        public void onReady() {
            if (this.adapter.onReadyHandler != null) {
                this.adapter.onReadyHandler.run();
            }
        }

        /* access modifiers changed from: package-private */
        public void onStart() {
            if (this.adapter.initialRequest > 0) {
                this.adapter.request(this.adapter.initialRequest);
            }
        }
    }

    private static final class UnaryStreamToFuture<RespT> extends StartableListener<RespT> {
        private boolean isValueReceived = false;
        private final GrpcFuture<RespT> responseFuture;
        private RespT value;

        UnaryStreamToFuture(GrpcFuture<RespT> responseFuture2) {
            super();
            this.responseFuture = responseFuture2;
        }

        public void onHeaders(Metadata headers) {
        }

        public void onMessage(RespT value2) {
            if (!this.isValueReceived) {
                this.value = value2;
                this.isValueReceived = true;
                return;
            }
            throw Status.INTERNAL.withDescription("More than one value received for unary call").asRuntimeException();
        }

        public void onClose(Status status, Metadata trailers) {
            if (status.isOk()) {
                if (!this.isValueReceived) {
                    this.responseFuture.setException(Status.INTERNAL.withDescription("No value received for unary call").asRuntimeException(trailers));
                }
                this.responseFuture.set(this.value);
                return;
            }
            this.responseFuture.setException(status.asRuntimeException(trailers));
        }

        /* access modifiers changed from: package-private */
        public void onStart() {
            this.responseFuture.call.request(2);
        }
    }

    private static final class GrpcFuture<RespT> extends AbstractFuture<RespT> {
        /* access modifiers changed from: private */
        public final ClientCall<?, RespT> call;

        GrpcFuture(ClientCall<?, RespT> call2) {
            this.call = call2;
        }

        /* access modifiers changed from: protected */
        public void interruptTask() {
            this.call.cancel("GrpcFuture was cancelled", (Throwable) null);
        }

        /* access modifiers changed from: protected */
        public boolean set(@Nullable RespT resp) {
            return super.set(resp);
        }

        /* access modifiers changed from: protected */
        public boolean setException(Throwable throwable) {
            return super.setException(throwable);
        }

        /* access modifiers changed from: protected */
        public String pendingToString() {
            return MoreObjects.toStringHelper((Object) this).add("clientCall", (Object) this.call).toString();
        }
    }

    private static final class BlockingResponseStream<T> implements Iterator<T> {
        /* access modifiers changed from: private */
        public final BlockingQueue<Object> buffer = new ArrayBlockingQueue(3);
        /* access modifiers changed from: private */
        public final ClientCall<?, T> call;
        private Object last;
        private final StartableListener<T> listener = new QueuingListener();

        BlockingResponseStream(ClientCall<?, T> call2) {
            this.call = call2;
        }

        /* access modifiers changed from: package-private */
        public StartableListener<T> listener() {
            return this.listener;
        }

        private Object waitForNext() {
            Object take;
            boolean interrupt = false;
            while (true) {
                try {
                    take = this.buffer.take();
                    break;
                } catch (InterruptedException ie) {
                    interrupt = true;
                    this.call.cancel("Thread interrupted", ie);
                } catch (Throwable th) {
                    if (1 != 0) {
                        Thread.currentThread().interrupt();
                    }
                    throw th;
                }
            }
            if (interrupt) {
                Thread.currentThread().interrupt();
            }
            return take;
        }

        public boolean hasNext() {
            while (this.last == null) {
                this.last = waitForNext();
            }
            if (!(this.last instanceof StatusRuntimeException)) {
                return this.last != this;
            }
            StatusRuntimeException e = (StatusRuntimeException) this.last;
            throw e.getStatus().asRuntimeException(e.getTrailers());
        }

        public T next() {
            if (!(this.last instanceof StatusRuntimeException) && this.last != this) {
                this.call.request(1);
            }
            if (hasNext()) {
                T tmp = this.last;
                this.last = null;
                return tmp;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        private final class QueuingListener extends StartableListener<T> {
            private boolean done = false;

            QueuingListener() {
                super();
            }

            public void onHeaders(Metadata headers) {
            }

            public void onMessage(T value) {
                Preconditions.checkState(!this.done, "ClientCall already closed");
                BlockingResponseStream.this.buffer.add(value);
            }

            public void onClose(Status status, Metadata trailers) {
                Preconditions.checkState(!this.done, "ClientCall already closed");
                if (status.isOk()) {
                    BlockingResponseStream.this.buffer.add(BlockingResponseStream.this);
                } else {
                    BlockingResponseStream.this.buffer.add(status.asRuntimeException(trailers));
                }
                this.done = true;
            }

            /* access modifiers changed from: package-private */
            public void onStart() {
                BlockingResponseStream.this.call.request(1);
            }
        }
    }

    private static final class ThreadlessExecutor extends ConcurrentLinkedQueue<Runnable> implements Executor {
        private static final Object SHUTDOWN = new Object();
        private static final Logger log = Logger.getLogger(ThreadlessExecutor.class.getName());
        private volatile Object waiter;

        ThreadlessExecutor() {
        }

        public void waitAndDrain() throws InterruptedException {
            Runnable runnable;
            throwIfInterrupted();
            Runnable runnable2 = (Runnable) poll();
            if (runnable2 == null) {
                this.waiter = Thread.currentThread();
                while (true) {
                    try {
                        Runnable runnable3 = (Runnable) poll();
                        runnable2 = runnable3;
                        if (runnable3 != null) {
                            break;
                        }
                        LockSupport.park(this);
                        throwIfInterrupted();
                    } finally {
                        this.waiter = null;
                    }
                }
            }
            do {
                runQuietly(runnable2);
                runnable = (Runnable) poll();
                runnable2 = runnable;
            } while (runnable != null);
        }

        public void shutdown() {
            this.waiter = SHUTDOWN;
            while (true) {
                Runnable runnable = (Runnable) poll();
                Runnable runnable2 = runnable;
                if (runnable != null) {
                    runQuietly(runnable2);
                } else {
                    return;
                }
            }
        }

        private static void runQuietly(Runnable runnable) {
            try {
                runnable.run();
            } catch (Throwable t) {
                log.log(Level.WARNING, "Runnable threw exception", t);
            }
        }

        private static void throwIfInterrupted() throws InterruptedException {
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
        }

        public void execute(Runnable runnable) {
            add(runnable);
            Object waiter2 = this.waiter;
            if (waiter2 != SHUTDOWN) {
                LockSupport.unpark((Thread) waiter2);
            } else if (remove(runnable) && ClientCalls.rejectRunnableOnExecutor) {
                throw new RejectedExecutionException();
            }
        }
    }
}
