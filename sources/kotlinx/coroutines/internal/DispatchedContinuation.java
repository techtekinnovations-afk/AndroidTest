package kotlinx.coroutines.internal;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CompletionStateKt;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.DispatchedTask;
import kotlinx.coroutines.EventLoop;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.ThreadLocalEventLoop;
import kotlinx.coroutines.UndispatchedCoroutine;

@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0004j\u0002`\u00032\b\u0012\u0004\u0012\u0002H\u00010\u0005B\u001d\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0004\b\t\u0010\nJ\u0015\u0010\u0012\u001a\n\u0018\u00010\u0014j\u0004\u0018\u0001`\u0013H\u0016¢\u0006\u0002\u0010\u0015J\r\u0010\u001d\u001a\u00020\u001eH\u0000¢\u0006\u0002\b\u001fJ\r\u0010 \u001a\u00020!H\u0000¢\u0006\u0002\b\"J\r\u0010#\u001a\u00020!H\u0000¢\u0006\u0002\b$J\u0015\u0010%\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u001aH\u0000¢\u0006\u0002\b&J\u001b\u0010'\u001a\u0004\u0018\u00010(2\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030)H\u0000¢\u0006\u0002\b*J\u0015\u0010+\u001a\u00020\u001e2\u0006\u0010,\u001a\u00020(H\u0000¢\u0006\u0002\b-J\u000f\u0010.\u001a\u0004\u0018\u00010\fH\u0010¢\u0006\u0002\b/J\u001b\u00103\u001a\u00020!2\f\u00104\u001a\b\u0012\u0004\u0012\u00028\u000005H\u0016¢\u0006\u0002\u00106J\u001e\u00107\u001a\u00020!2\f\u00104\u001a\b\u0012\u0004\u0012\u00028\u000005H\b¢\u0006\u0004\b8\u00106J\u0018\u00109\u001a\u00020\u001e2\b\u0010:\u001a\u0004\u0018\u00010\fH\b¢\u0006\u0002\b;J\u001e\u0010<\u001a\u00020!2\f\u00104\u001a\b\u0012\u0004\u0012\u00028\u000005H\b¢\u0006\u0004\b=\u00106J\u001f\u0010>\u001a\u00020!2\u0006\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00028\u0000H\u0000¢\u0006\u0004\bB\u0010CJ\b\u0010D\u001a\u00020EH\u0016R\u0010\u0010\u0006\u001a\u00020\u00078\u0000X\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00058\u0006X\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u0004\u0018\u00010\f8\u0000@\u0000X\u000e¢\u0006\b\n\u0000\u0012\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\n\u0018\u00010\u0004j\u0004\u0018\u0001`\u00038VX\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0010\u0010\u0016\u001a\u00020\f8\u0000X\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0018X\u0004R\u001a\u0010\u0019\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001a8BX\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u001a\u00100\u001a\b\u0012\u0004\u0012\u00028\u00000\u00058PX\u0004¢\u0006\u0006\u001a\u0004\b1\u00102R\t\u0010?\u001a\u00020@X\u0005¨\u0006F"}, d2 = {"Lkotlinx/coroutines/internal/DispatchedContinuation;", "T", "Lkotlinx/coroutines/DispatchedTask;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlin/coroutines/Continuation;", "dispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "continuation", "<init>", "(Lkotlinx/coroutines/CoroutineDispatcher;Lkotlin/coroutines/Continuation;)V", "_state", "", "get_state$kotlinx_coroutines_core$annotations", "()V", "callerFrame", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "getStackTraceElement", "Lkotlinx/coroutines/internal/StackTraceElement;", "Ljava/lang/StackTraceElement;", "()Ljava/lang/StackTraceElement;", "countOrElement", "_reusableCancellableContinuation", "Lkotlinx/atomicfu/AtomicRef;", "reusableCancellableContinuation", "Lkotlinx/coroutines/CancellableContinuationImpl;", "getReusableCancellableContinuation", "()Lkotlinx/coroutines/CancellableContinuationImpl;", "isReusable", "", "isReusable$kotlinx_coroutines_core", "awaitReusability", "", "awaitReusability$kotlinx_coroutines_core", "release", "release$kotlinx_coroutines_core", "claimReusableCancellableContinuation", "claimReusableCancellableContinuation$kotlinx_coroutines_core", "tryReleaseClaimedContinuation", "", "Lkotlinx/coroutines/CancellableContinuation;", "tryReleaseClaimedContinuation$kotlinx_coroutines_core", "postponeCancellation", "cause", "postponeCancellation$kotlinx_coroutines_core", "takeState", "takeState$kotlinx_coroutines_core", "delegate", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "resumeWith", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "resumeCancellableWith", "resumeCancellableWith$kotlinx_coroutines_core", "resumeCancelled", "state", "resumeCancelled$kotlinx_coroutines_core", "resumeUndispatchedWith", "resumeUndispatchedWith$kotlinx_coroutines_core", "dispatchYield", "context", "Lkotlin/coroutines/CoroutineContext;", "value", "dispatchYield$kotlinx_coroutines_core", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;)V", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: DispatchedContinuation.kt */
public final class DispatchedContinuation<T> extends DispatchedTask<T> implements CoroutineStackFrame, Continuation<T> {
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicReferenceFieldUpdater _reusableCancellableContinuation$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(DispatchedContinuation.class, Object.class, "_reusableCancellableContinuation$volatile");
    private volatile /* synthetic */ Object _reusableCancellableContinuation$volatile;
    public Object _state = DispatchedContinuationKt.UNDEFINED;
    public final Continuation<T> continuation;
    public final Object countOrElement = ThreadContextKt.threadContextElements(getContext());
    public final CoroutineDispatcher dispatcher;

    private final /* synthetic */ Object get_reusableCancellableContinuation$volatile() {
        return this._reusableCancellableContinuation$volatile;
    }

    public static /* synthetic */ void get_state$kotlinx_coroutines_core$annotations() {
    }

    private final /* synthetic */ void loop$atomicfu(Object obj, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, Function1<Object, Unit> function1) {
        while (true) {
            function1.invoke(atomicReferenceFieldUpdater.get(obj));
        }
    }

    private final /* synthetic */ void set_reusableCancellableContinuation$volatile(Object obj) {
        this._reusableCancellableContinuation$volatile = obj;
    }

    public CoroutineContext getContext() {
        return this.continuation.getContext();
    }

    public DispatchedContinuation(CoroutineDispatcher dispatcher2, Continuation<? super T> continuation2) {
        super(-1);
        this.dispatcher = dispatcher2;
        this.continuation = continuation2;
    }

    public CoroutineStackFrame getCallerFrame() {
        Continuation<T> continuation2 = this.continuation;
        if (continuation2 instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation2;
        }
        return null;
    }

    public StackTraceElement getStackTraceElement() {
        return null;
    }

    private final CancellableContinuationImpl<?> getReusableCancellableContinuation() {
        Object obj = _reusableCancellableContinuation$volatile$FU.get(this);
        if (obj instanceof CancellableContinuationImpl) {
            return (CancellableContinuationImpl) obj;
        }
        return null;
    }

    public final boolean isReusable$kotlinx_coroutines_core() {
        return _reusableCancellableContinuation$volatile$FU.get(this) != null;
    }

    public final void awaitReusability$kotlinx_coroutines_core() {
        do {
        } while (_reusableCancellableContinuation$volatile$FU.get(this) == DispatchedContinuationKt.REUSABLE_CLAIMED);
    }

    public final void release$kotlinx_coroutines_core() {
        awaitReusability$kotlinx_coroutines_core();
        CancellableContinuationImpl<?> reusableCancellableContinuation = getReusableCancellableContinuation();
        if (reusableCancellableContinuation != null) {
            reusableCancellableContinuation.detachChild$kotlinx_coroutines_core();
        }
    }

    public final CancellableContinuationImpl<T> claimReusableCancellableContinuation$kotlinx_coroutines_core() {
        AtomicReferenceFieldUpdater handler$atomicfu$iv = _reusableCancellableContinuation$volatile$FU;
        while (true) {
            Object state = handler$atomicfu$iv.get(this);
            if (state == null) {
                _reusableCancellableContinuation$volatile$FU.set(this, DispatchedContinuationKt.REUSABLE_CLAIMED);
                return null;
            } else if (state instanceof CancellableContinuationImpl) {
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_reusableCancellableContinuation$volatile$FU, this, state, DispatchedContinuationKt.REUSABLE_CLAIMED)) {
                    return (CancellableContinuationImpl) state;
                }
            } else if (state != DispatchedContinuationKt.REUSABLE_CLAIMED && !(state instanceof Throwable)) {
                throw new IllegalStateException(("Inconsistent state " + state).toString());
            }
        }
    }

    public final Throwable tryReleaseClaimedContinuation$kotlinx_coroutines_core(CancellableContinuation<?> continuation2) {
        AtomicReferenceFieldUpdater handler$atomicfu$iv = _reusableCancellableContinuation$volatile$FU;
        do {
            Object state = handler$atomicfu$iv.get(this);
            if (state != DispatchedContinuationKt.REUSABLE_CLAIMED) {
                if (!(state instanceof Throwable)) {
                    throw new IllegalStateException(("Inconsistent state " + state).toString());
                } else if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_reusableCancellableContinuation$volatile$FU, this, state, (Object) null)) {
                    return (Throwable) state;
                } else {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
            }
        } while (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_reusableCancellableContinuation$volatile$FU, this, DispatchedContinuationKt.REUSABLE_CLAIMED, continuation2));
        return null;
    }

    public final boolean postponeCancellation$kotlinx_coroutines_core(Throwable cause) {
        AtomicReferenceFieldUpdater handler$atomicfu$iv = _reusableCancellableContinuation$volatile$FU;
        while (true) {
            Object state = handler$atomicfu$iv.get(this);
            if (Intrinsics.areEqual(state, (Object) DispatchedContinuationKt.REUSABLE_CLAIMED)) {
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_reusableCancellableContinuation$volatile$FU, this, DispatchedContinuationKt.REUSABLE_CLAIMED, cause)) {
                    return true;
                }
            } else if (state instanceof Throwable) {
                return true;
            } else {
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_reusableCancellableContinuation$volatile$FU, this, state, (Object) null)) {
                    return false;
                }
            }
        }
    }

    public Object takeState$kotlinx_coroutines_core() {
        Object state = this._state;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(state != DispatchedContinuationKt.UNDEFINED)) {
                throw new AssertionError();
            }
        }
        this._state = DispatchedContinuationKt.UNDEFINED;
        return state;
    }

    public Continuation<T> getDelegate$kotlinx_coroutines_core() {
        return this;
    }

    public void resumeWith(Object result) {
        Object state = CompletionStateKt.toState(result);
        if (this.dispatcher.isDispatchNeeded(getContext())) {
            this._state = state;
            this.resumeMode = 0;
            this.dispatcher.dispatch(getContext(), this);
            Object obj = result;
            return;
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
        }
        EventLoop eventLoop$iv = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
        if (eventLoop$iv.isUnconfinedLoopActive()) {
            this._state = state;
            this.resumeMode = 0;
            eventLoop$iv.dispatchUnconfined(this);
            Object obj2 = result;
            return;
        }
        DispatchedTask $this$runUnconfinedEventLoop$iv$iv = this;
        eventLoop$iv.incrementUseCount(true);
        try {
            CoroutineContext context$iv = getContext();
            Object oldValue$iv = ThreadContextKt.updateThreadContext(context$iv, this.countOrElement);
            try {
                try {
                    this.continuation.resumeWith(result);
                    Unit unit = Unit.INSTANCE;
                    ThreadContextKt.restoreThreadContext(context$iv, oldValue$iv);
                    do {
                    } while (eventLoop$iv.processUnconfinedEvent());
                } catch (Throwable th) {
                    e$iv$iv = th;
                    try {
                        $this$runUnconfinedEventLoop$iv$iv.handleFatalException$kotlinx_coroutines_core(e$iv$iv);
                        eventLoop$iv.decrementUseCount(true);
                    } catch (Throwable th2) {
                        eventLoop$iv.decrementUseCount(true);
                        throw th2;
                    }
                }
                eventLoop$iv.decrementUseCount(true);
            } catch (Throwable th3) {
                th = th3;
                Object obj3 = result;
                ThreadContextKt.restoreThreadContext(context$iv, oldValue$iv);
                throw th;
            }
        } catch (Throwable th4) {
            e$iv$iv = th4;
            Object obj4 = result;
            $this$runUnconfinedEventLoop$iv$iv.handleFatalException$kotlinx_coroutines_core(e$iv$iv);
            eventLoop$iv.decrementUseCount(true);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00f1, code lost:
        if (r17.clearThreadContext() != false) goto L_0x00f3;
     */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00ac  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x010d A[Catch:{ all -> 0x00f9, all -> 0x011d }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void resumeCancellableWith$kotlinx_coroutines_core(java.lang.Object r22) {
        /*
            r21 = this;
            r1 = r21
            r2 = 0
            java.lang.Object r3 = kotlinx.coroutines.CompletionStateKt.toState(r22)
            kotlinx.coroutines.CoroutineDispatcher r0 = r1.dispatcher
            kotlin.coroutines.CoroutineContext r4 = r1.getContext()
            boolean r0 = r0.isDispatchNeeded(r4)
            r4 = 1
            if (r0 == 0) goto L_0x002c
            r1._state = r3
            r1.resumeMode = r4
            kotlinx.coroutines.CoroutineDispatcher r0 = r1.dispatcher
            kotlin.coroutines.CoroutineContext r4 = r1.getContext()
            r5 = r1
            java.lang.Runnable r5 = (java.lang.Runnable) r5
            r0.dispatch(r4, r5)
            r18 = r2
            r20 = r3
            r3 = r22
            goto L_0x0131
        L_0x002c:
            r0 = 1
            r5 = r21
            r6 = r0
            r7 = 0
            r8 = 0
            boolean r0 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()
            if (r0 == 0) goto L_0x003b
            r0 = 0
        L_0x003b:
            kotlinx.coroutines.ThreadLocalEventLoop r0 = kotlinx.coroutines.ThreadLocalEventLoop.INSTANCE
            kotlinx.coroutines.EventLoop r9 = r0.getEventLoop$kotlinx_coroutines_core()
            boolean r0 = r9.isUnconfinedLoopActive()
            if (r0 == 0) goto L_0x005a
            r5._state = r3
            r5.resumeMode = r6
            r0 = r5
            kotlinx.coroutines.DispatchedTask r0 = (kotlinx.coroutines.DispatchedTask) r0
            r9.dispatchUnconfined(r0)
            r18 = r2
            r20 = r3
            r3 = r22
            goto L_0x0130
        L_0x005a:
            r10 = r5
            kotlinx.coroutines.DispatchedTask r10 = (kotlinx.coroutines.DispatchedTask) r10
            r11 = 0
            r9.incrementUseCount(r4)
            r12 = 0
            r0 = r21
            r13 = 0
            kotlin.coroutines.CoroutineContext r14 = r0.getContext()     // Catch:{ all -> 0x011f }
            kotlinx.coroutines.Job$Key r15 = kotlinx.coroutines.Job.Key     // Catch:{ all -> 0x011f }
            kotlin.coroutines.CoroutineContext$Key r15 = (kotlin.coroutines.CoroutineContext.Key) r15     // Catch:{ all -> 0x011f }
            kotlin.coroutines.CoroutineContext$Element r14 = r14.get(r15)     // Catch:{ all -> 0x011f }
            kotlinx.coroutines.Job r14 = (kotlinx.coroutines.Job) r14     // Catch:{ all -> 0x011f }
            if (r14 == 0) goto L_0x00a7
            boolean r15 = r14.isActive()     // Catch:{ all -> 0x009e }
            if (r15 != 0) goto L_0x00a7
            java.util.concurrent.CancellationException r15 = r14.getCancellationException()     // Catch:{ all -> 0x009e }
            r4 = r15
            java.lang.Throwable r4 = (java.lang.Throwable) r4     // Catch:{ all -> 0x009e }
            r0.cancelCompletedResult$kotlinx_coroutines_core(r3, r4)     // Catch:{ all -> 0x009e }
            r4 = r0
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4     // Catch:{ all -> 0x009e }
            kotlin.Result$Companion r16 = kotlin.Result.Companion     // Catch:{ all -> 0x009e }
            r16 = r15
            java.lang.Throwable r16 = (java.lang.Throwable) r16     // Catch:{ all -> 0x009e }
            java.lang.Object r16 = kotlin.ResultKt.createFailure(r16)     // Catch:{ all -> 0x009e }
            r17 = r0
            java.lang.Object r0 = kotlin.Result.m6constructorimpl(r16)     // Catch:{ all -> 0x009e }
            r4.resumeWith(r0)     // Catch:{ all -> 0x009e }
            r0 = 1
            goto L_0x00aa
        L_0x009e:
            r0 = move-exception
            r18 = r2
            r20 = r3
            r3 = r22
            goto L_0x0126
        L_0x00a7:
            r17 = r0
            r0 = 0
        L_0x00aa:
            if (r0 != 0) goto L_0x010d
            r4 = r21
            r13 = 0
            kotlin.coroutines.Continuation<T> r0 = r4.continuation     // Catch:{ all -> 0x011f }
            java.lang.Object r14 = r4.countOrElement     // Catch:{ all -> 0x011f }
            r15 = r0
            r16 = 0
            kotlin.coroutines.CoroutineContext r0 = r15.getContext()     // Catch:{ all -> 0x011f }
            r17 = r0
            r1 = r17
            java.lang.Object r0 = kotlinx.coroutines.internal.ThreadContextKt.updateThreadContext(r1, r14)     // Catch:{ all -> 0x011f }
            r17 = r0
            kotlinx.coroutines.internal.Symbol r0 = kotlinx.coroutines.internal.ThreadContextKt.NO_THREAD_ELEMENTS     // Catch:{ all -> 0x011f }
            r18 = r2
            r2 = r17
            if (r2 == r0) goto L_0x00d7
            kotlinx.coroutines.UndispatchedCoroutine r0 = kotlinx.coroutines.CoroutineContextKt.updateUndispatchedCompletion(r15, r1, r2)     // Catch:{ all -> 0x00d1 }
            goto L_0x00d8
        L_0x00d1:
            r0 = move-exception
            r20 = r3
            r3 = r22
            goto L_0x0126
        L_0x00d7:
            r0 = 0
        L_0x00d8:
            r17 = r0
            r0 = 0
            r19 = r0
            kotlin.coroutines.Continuation<T> r0 = r4.continuation     // Catch:{ all -> 0x00fb }
            r20 = r3
            r3 = r22
            r0.resumeWith(r3)     // Catch:{ all -> 0x00f9 }
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00f9 }
            if (r17 == 0) goto L_0x00f3
            boolean r0 = r17.clearThreadContext()     // Catch:{ all -> 0x011d }
            if (r0 == 0) goto L_0x00f6
        L_0x00f3:
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r1, r2)     // Catch:{ all -> 0x011d }
        L_0x00f6:
            goto L_0x0113
        L_0x00f9:
            r0 = move-exception
            goto L_0x0100
        L_0x00fb:
            r0 = move-exception
            r20 = r3
            r3 = r22
        L_0x0100:
            if (r17 == 0) goto L_0x0108
            boolean r19 = r17.clearThreadContext()     // Catch:{ all -> 0x011d }
            if (r19 == 0) goto L_0x010b
        L_0x0108:
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r1, r2)     // Catch:{ all -> 0x011d }
        L_0x010b:
            throw r0     // Catch:{ all -> 0x011d }
        L_0x010d:
            r18 = r2
            r20 = r3
            r3 = r22
        L_0x0113:
        L_0x0115:
            boolean r0 = r9.processUnconfinedEvent()     // Catch:{ all -> 0x011d }
            if (r0 != 0) goto L_0x0115
            goto L_0x0129
        L_0x011d:
            r0 = move-exception
            goto L_0x0126
        L_0x011f:
            r0 = move-exception
            r18 = r2
            r20 = r3
            r3 = r22
        L_0x0126:
            r10.handleFatalException$kotlinx_coroutines_core(r0)     // Catch:{ all -> 0x0132 }
        L_0x0129:
            r1 = 1
            r9.decrementUseCount(r1)
        L_0x0130:
        L_0x0131:
            return
        L_0x0132:
            r0 = move-exception
            r1 = 1
            r9.decrementUseCount(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.DispatchedContinuation.resumeCancellableWith$kotlinx_coroutines_core(java.lang.Object):void");
    }

    public final boolean resumeCancelled$kotlinx_coroutines_core(Object state) {
        Job job = (Job) getContext().get(Job.Key);
        if (job == null || job.isActive()) {
            return false;
        }
        CancellationException cause = job.getCancellationException();
        cancelCompletedResult$kotlinx_coroutines_core(state, cause);
        Result.Companion companion = Result.Companion;
        resumeWith(Result.m6constructorimpl(ResultKt.createFailure(cause)));
        return true;
    }

    public final void resumeUndispatchedWith$kotlinx_coroutines_core(Object result) {
        UndispatchedCoroutine undispatchedCompletion$iv;
        Continuation continuation$iv = this.continuation;
        Object countOrElement$iv = this.countOrElement;
        CoroutineContext context$iv = continuation$iv.getContext();
        Object oldValue$iv = ThreadContextKt.updateThreadContext(context$iv, countOrElement$iv);
        if (oldValue$iv != ThreadContextKt.NO_THREAD_ELEMENTS) {
            undispatchedCompletion$iv = CoroutineContextKt.updateUndispatchedCompletion(continuation$iv, context$iv, oldValue$iv);
        } else {
            undispatchedCompletion$iv = null;
        }
        try {
            this.continuation.resumeWith(result);
            Unit unit = Unit.INSTANCE;
        } finally {
            if (undispatchedCompletion$iv == null || undispatchedCompletion$iv.clearThreadContext()) {
                ThreadContextKt.restoreThreadContext(context$iv, oldValue$iv);
            }
        }
    }

    public final void dispatchYield$kotlinx_coroutines_core(CoroutineContext context, T value) {
        this._state = value;
        this.resumeMode = 1;
        this.dispatcher.dispatchYield(context, this);
    }

    public String toString() {
        return "DispatchedContinuation[" + this.dispatcher + ", " + DebugStringsKt.toDebugString(this.continuation) + ']';
    }
}
