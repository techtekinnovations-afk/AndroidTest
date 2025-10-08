package kotlinx.coroutines;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0002\u000e\u000fB\u001d\u0012\u0014\u0010\u0003\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u00028\u00000\u00050\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\fH@¢\u0006\u0002\u0010\rR\u001e\u0010\u0003\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u00028\u00000\u00050\u0004X\u0004¢\u0006\u0004\n\u0002\u0010\bR\t\u0010\t\u001a\u00020\nX\u0004¨\u0006\u0010"}, d2 = {"Lkotlinx/coroutines/AwaitAll;", "T", "", "deferreds", "", "Lkotlinx/coroutines/Deferred;", "<init>", "([Lkotlinx/coroutines/Deferred;)V", "[Lkotlinx/coroutines/Deferred;", "notCompletedCount", "Lkotlinx/atomicfu/AtomicInt;", "await", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "DisposeHandlersOnCancel", "AwaitAllNode", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: Await.kt */
final class AwaitAll<T> {
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicIntegerFieldUpdater notCompletedCount$volatile$FU = AtomicIntegerFieldUpdater.newUpdater(AwaitAll.class, "notCompletedCount$volatile");
    /* access modifiers changed from: private */
    public final Deferred<T>[] deferreds;
    private volatile /* synthetic */ int notCompletedCount$volatile = this.deferreds.length;

    private final /* synthetic */ int getNotCompletedCount$volatile() {
        return this.notCompletedCount$volatile;
    }

    private final /* synthetic */ void setNotCompletedCount$volatile(int i) {
        this.notCompletedCount$volatile = i;
    }

    public AwaitAll(Deferred<? extends T>[] deferreds2) {
        this.deferreds = deferreds2;
    }

    public final Object await(Continuation<? super List<? extends T>> $completion) {
        int $i$f$suspendCancellableCoroutine = 0;
        Continuation uCont$iv = $completion;
        int i = false;
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted(uCont$iv), 1);
        cancellable$iv.initCancellability();
        CancellableContinuation cont = cancellable$iv;
        int length = this.deferreds.length;
        AwaitAllNode[] nodes = new AwaitAllNode[length];
        int i2 = 0;
        while (i2 < length) {
            Deferred deferred = this.deferreds[i2];
            deferred.start();
            AwaitAllNode awaitAllNode = new AwaitAllNode(cont);
            AwaitAllNode $this$await_u24lambda_u242_u24lambda_u240 = awaitAllNode;
            int $i$f$suspendCancellableCoroutine2 = $i$f$suspendCancellableCoroutine;
            $this$await_u24lambda_u242_u24lambda_u240.setHandle(JobKt__JobKt.invokeOnCompletion$default(deferred, false, $this$await_u24lambda_u242_u24lambda_u240, 1, (Object) null));
            Unit unit = Unit.INSTANCE;
            nodes[i2] = awaitAllNode;
            i2++;
            $i$f$suspendCancellableCoroutine = $i$f$suspendCancellableCoroutine2;
            uCont$iv = uCont$iv;
            i = i;
        }
        Continuation continuation = uCont$iv;
        int i3 = i;
        DisposeHandlersOnCancel disposer = new DisposeHandlersOnCancel(nodes);
        for (AwaitAllNode it : nodes) {
            it.setDisposer(disposer);
        }
        if (cont.isCompleted()) {
            disposer.disposeAll();
        } else {
            CancellableContinuationKt.invokeOnCancellation(cont, disposer);
        }
        Object result = cancellable$iv.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result;
    }

    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0004\u0018\u00002\u00020\u0001B\u001f\u0012\u0016\u0010\u0002\u001a\u0012\u0012\u000e\u0012\f0\u0004R\b\u0012\u0004\u0012\u00028\u00000\u00050\u0003¢\u0006\u0004\b\u0006\u0010\u0007J\u0006\u0010\t\u001a\u00020\nJ\u0012\u0010\u000b\u001a\u00020\n2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016R \u0010\u0002\u001a\u0012\u0012\u000e\u0012\f0\u0004R\b\u0012\u0004\u0012\u00028\u00000\u00050\u0003X\u0004¢\u0006\u0004\n\u0002\u0010\b¨\u0006\u0010"}, d2 = {"Lkotlinx/coroutines/AwaitAll$DisposeHandlersOnCancel;", "Lkotlinx/coroutines/CancelHandler;", "nodes", "", "Lkotlinx/coroutines/AwaitAll$AwaitAllNode;", "Lkotlinx/coroutines/AwaitAll;", "<init>", "(Lkotlinx/coroutines/AwaitAll;[Lkotlinx/coroutines/AwaitAll$AwaitAllNode;)V", "[Lkotlinx/coroutines/AwaitAll$AwaitAllNode;", "disposeAll", "", "invoke", "cause", "", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: Await.kt */
    private final class DisposeHandlersOnCancel implements CancelHandler {
        private final AwaitAll<T>.AwaitAllNode[] nodes;

        public DisposeHandlersOnCancel(AwaitAll<T>.AwaitAllNode[] nodes2) {
            this.nodes = nodes2;
        }

        public final void disposeAll() {
            for (AwaitAll<T>.AwaitAllNode it : this.nodes) {
                it.getHandle().dispose();
            }
        }

        public void invoke(Throwable cause) {
            disposeAll();
        }

        public String toString() {
            return "DisposeHandlersOnCancel[" + this.nodes + ']';
        }
    }

    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0004\u0018\u00002\u00020\u0001B\u001b\u0012\u0012\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00040\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u0012\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016R\u001a\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00040\u0003X\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\bX.¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001b\u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0018\u00010\u000fR\b\u0012\u0004\u0012\u00028\u00000\u00100\u000eX\u0004R<\u0010\u0012\u001a\u000e\u0018\u00010\u000fR\b\u0012\u0004\u0012\u00028\u00000\u00102\u0012\u0010\u0011\u001a\u000e\u0018\u00010\u000fR\b\u0012\u0004\u0012\u00028\u00000\u00108F@FX\u000e¢\u0006\f\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00188VX\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001f"}, d2 = {"Lkotlinx/coroutines/AwaitAll$AwaitAllNode;", "Lkotlinx/coroutines/JobNode;", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "", "<init>", "(Lkotlinx/coroutines/AwaitAll;Lkotlinx/coroutines/CancellableContinuation;)V", "handle", "Lkotlinx/coroutines/DisposableHandle;", "getHandle", "()Lkotlinx/coroutines/DisposableHandle;", "setHandle", "(Lkotlinx/coroutines/DisposableHandle;)V", "_disposer", "Lkotlinx/atomicfu/AtomicRef;", "Lkotlinx/coroutines/AwaitAll$DisposeHandlersOnCancel;", "Lkotlinx/coroutines/AwaitAll;", "value", "disposer", "getDisposer", "()Lkotlinx/coroutines/AwaitAll$DisposeHandlersOnCancel;", "setDisposer", "(Lkotlinx/coroutines/AwaitAll$DisposeHandlersOnCancel;)V", "onCancelling", "", "getOnCancelling", "()Z", "invoke", "", "cause", "", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: Await.kt */
    private final class AwaitAllNode extends JobNode {
        /* access modifiers changed from: private */
        public static final /* synthetic */ AtomicReferenceFieldUpdater _disposer$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(AwaitAllNode.class, Object.class, "_disposer$volatile");
        private volatile /* synthetic */ Object _disposer$volatile;
        private final CancellableContinuation<List<? extends T>> continuation;
        public DisposableHandle handle;

        private final /* synthetic */ Object get_disposer$volatile() {
            return this._disposer$volatile;
        }

        private final /* synthetic */ void set_disposer$volatile(Object obj) {
            this._disposer$volatile = obj;
        }

        public AwaitAllNode(CancellableContinuation<? super List<? extends T>> continuation2) {
            this.continuation = continuation2;
        }

        public final DisposableHandle getHandle() {
            DisposableHandle disposableHandle = this.handle;
            if (disposableHandle != null) {
                return disposableHandle;
            }
            Intrinsics.throwUninitializedPropertyAccessException("handle");
            return null;
        }

        public final void setHandle(DisposableHandle disposableHandle) {
            this.handle = disposableHandle;
        }

        public final AwaitAll<T>.DisposeHandlersOnCancel getDisposer() {
            return (DisposeHandlersOnCancel) _disposer$volatile$FU.get(this);
        }

        public final void setDisposer(AwaitAll<T>.DisposeHandlersOnCancel value) {
            _disposer$volatile$FU.set(this, value);
        }

        public boolean getOnCancelling() {
            return false;
        }

        public void invoke(Throwable cause) {
            if (cause != null) {
                Object token = this.continuation.tryResumeWithException(cause);
                if (token != null) {
                    this.continuation.completeResume(token);
                    AwaitAll<T>.DisposeHandlersOnCancel disposer = getDisposer();
                    if (disposer != null) {
                        disposer.disposeAll();
                    }
                }
            } else if (AwaitAll.notCompletedCount$volatile$FU.decrementAndGet(AwaitAll.this) == 0) {
                Continuation continuation2 = this.continuation;
                Deferred[] access$getDeferreds$p = AwaitAll.this.deferreds;
                Collection destination$iv$iv = new ArrayList(access$getDeferreds$p.length);
                for (Deferred it : access$getDeferreds$p) {
                    destination$iv$iv.add(it.getCompleted());
                }
                Result.Companion companion = Result.Companion;
                continuation2.resumeWith(Result.m6constructorimpl((List) destination$iv$iv));
            }
        }
    }
}
