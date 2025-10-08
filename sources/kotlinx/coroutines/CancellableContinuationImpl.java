package kotlinx.coroutines;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancelHandler;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;

@Metadata(d1 = {"\u0000Ú\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0003\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\n\b\u0011\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00060\u0005j\u0002`\u00042\u00020\u0006B\u001d\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0004\b\u000b\u0010\fJ\b\u0010)\u001a\u00020*H\u0016J\b\u0010+\u001a\u00020!H\u0002J\b\u0010,\u001a\u00020!H\u0001J\u0015\u00100\u001a\n\u0018\u000102j\u0004\u0018\u0001`1H\u0016¢\u0006\u0002\u00103J\u000f\u00104\u001a\u0004\u0018\u00010\u0017H\u0010¢\u0006\u0002\b5J\u001f\u00106\u001a\u00020*2\b\u00107\u001a\u0004\u0018\u00010\u00172\u0006\u00108\u001a\u000209H\u0010¢\u0006\u0002\b:J\u0010\u0010;\u001a\u00020!2\u0006\u00108\u001a\u000209H\u0002J\u0012\u0010<\u001a\u00020!2\b\u00108\u001a\u0004\u0018\u000109H\u0016J\u0015\u0010=\u001a\u00020*2\u0006\u00108\u001a\u000209H\u0000¢\u0006\u0002\b>J\u0017\u0010?\u001a\u00020*2\f\u0010@\u001a\b\u0012\u0004\u0012\u00020*0AH\bJ\u0018\u0010B\u001a\u00020*2\u0006\u0010C\u001a\u00020D2\b\u00108\u001a\u0004\u0018\u000109J\u001e\u0010E\u001a\u00020*2\n\u0010F\u001a\u0006\u0012\u0002\b\u00030G2\b\u00108\u001a\u0004\u0018\u000109H\u0002Jn\u0010H\u001a\u00020*\"\u0004\b\u0001\u0010I2K\u0010J\u001aG\u0012\u0013\u0012\u001109¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(8\u0012\u0013\u0012\u0011HI¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(N\u0012\u0013\u0012\u00110\u0010¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020*0K2\u0006\u00108\u001a\u0002092\u0006\u0010N\u001a\u0002HI¢\u0006\u0002\u0010OJ\u0010\u0010P\u001a\u0002092\u0006\u0010Q\u001a\u00020RH\u0016J\b\u0010S\u001a\u00020!H\u0002J\b\u0010T\u001a\u00020!H\u0002J\n\u0010U\u001a\u0004\u0018\u00010\u0017H\u0001J\n\u0010V\u001a\u0004\u0018\u00010\u0019H\u0002J\r\u0010W\u001a\u00020*H\u0000¢\u0006\u0002\bXJ\u001b\u0010Y\u001a\u00020*2\f\u0010Z\u001a\b\u0012\u0004\u0012\u00028\u00000[H\u0016¢\u0006\u0002\u0010\\J:\u0010]\u001a\u00020*2\u0006\u0010N\u001a\u00028\u00002#\u0010J\u001a\u001f\u0012\u0013\u0012\u001109¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(8\u0012\u0004\u0012\u00020*\u0018\u00010^H\u0016¢\u0006\u0002\u0010_Jn\u0010]\u001a\u00020*\"\b\b\u0001\u0010I*\u00028\u00002\u0006\u0010N\u001a\u0002HI2M\u0010J\u001aI\u0012\u0013\u0012\u001109¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(8\u0012\u0013\u0012\u0011HI¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(N\u0012\u0013\u0012\u00110\u0010¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020*\u0018\u00010KH\u0016¢\u0006\u0002\u0010`J\u001c\u0010a\u001a\u00020*2\n\u0010F\u001a\u0006\u0012\u0002\b\u00030G2\u0006\u0010b\u001a\u00020\nH\u0016J6\u0010a\u001a\u00020*2'\u0010C\u001a#\u0012\u0015\u0012\u0013\u0018\u000109¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(8\u0012\u0004\u0012\u00020*0^j\u0002`cH\u0016¢\u0006\u0002\u0010dJ\u0015\u0010e\u001a\u00020*2\u0006\u0010C\u001a\u00020DH\u0000¢\u0006\u0002\bfJ\u0010\u0010g\u001a\u00020*2\u0006\u0010C\u001a\u00020\u0017H\u0002J\u001a\u0010h\u001a\u00020*2\u0006\u0010C\u001a\u00020\u00172\b\u0010\u001d\u001a\u0004\u0018\u00010\u0017H\u0002J\u0010\u0010i\u001a\u00020*2\u0006\u0010j\u001a\u00020\nH\u0002J\u0001\u0010k\u001a\u0004\u0018\u00010\u0017\"\u0004\b\u0001\u0010I2\u0006\u0010\u001d\u001a\u00020l2\u0006\u0010m\u001a\u0002HI2\u0006\u0010\t\u001a\u00020\n2M\u0010J\u001aI\u0012\u0013\u0012\u001109¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(8\u0012\u0013\u0012\u0011HI¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(N\u0012\u0013\u0012\u00110\u0010¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020*\u0018\u00010K2\b\u0010n\u001a\u0004\u0018\u00010\u0017H\u0002¢\u0006\u0002\u0010oJv\u0010p\u001a\u00020*\"\u0004\b\u0001\u0010I2\u0006\u0010m\u001a\u0002HI2\u0006\u0010\t\u001a\u00020\n2O\b\u0002\u0010J\u001aI\u0012\u0013\u0012\u001109¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(8\u0012\u0013\u0012\u0011HI¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(N\u0012\u0013\u0012\u00110\u0010¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020*\u0018\u00010KH\u0000¢\u0006\u0004\bq\u0010rJv\u0010s\u001a\u0004\u0018\u00010t\"\u0004\b\u0001\u0010I2\u0006\u0010m\u001a\u0002HI2\b\u0010n\u001a\u0004\u0018\u00010\u00172M\u0010J\u001aI\u0012\u0013\u0012\u001109¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(8\u0012\u0013\u0012\u0011HI¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(N\u0012\u0013\u0012\u00110\u0010¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020*\u0018\u00010KH\u0002¢\u0006\u0002\u0010uJ\u0012\u0010v\u001a\u00020w2\b\u0010m\u001a\u0004\u0018\u00010\u0017H\u0002J\b\u0010x\u001a\u00020*H\u0002J\r\u0010y\u001a\u00020*H\u0000¢\u0006\u0002\bzJ!\u0010T\u001a\u0004\u0018\u00010\u00172\u0006\u0010N\u001a\u00028\u00002\b\u0010n\u001a\u0004\u0018\u00010\u0017H\u0016¢\u0006\u0002\u0010{Jz\u0010T\u001a\u0004\u0018\u00010\u0017\"\b\b\u0001\u0010I*\u00028\u00002\u0006\u0010N\u001a\u0002HI2\b\u0010n\u001a\u0004\u0018\u00010\u00172M\u0010J\u001aI\u0012\u0013\u0012\u001109¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(8\u0012\u0013\u0012\u0011HI¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(N\u0012\u0013\u0012\u00110\u0010¢\u0006\f\bL\u0012\b\bM\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020*\u0018\u00010KH\u0016¢\u0006\u0002\u0010|J\u0012\u0010}\u001a\u0004\u0018\u00010\u00172\u0006\u0010~\u001a\u000209H\u0016J\u0011\u0010\u001a\u00020*2\u0007\u0010\u0001\u001a\u00020\u0017H\u0016J\u001c\u0010\u0001\u001a\u00020**\u00030\u00012\u0006\u0010N\u001a\u00028\u0000H\u0016¢\u0006\u0003\u0010\u0001J\u0016\u0010\u0001\u001a\u00020**\u00030\u00012\u0006\u0010~\u001a\u000209H\u0016J\"\u0010\u0001\u001a\u0002H\u0001\"\u0004\b\u0001\u0010\u00012\b\u0010\u001d\u001a\u0004\u0018\u00010\u0017H\u0010¢\u0006\u0006\b\u0001\u0010\u0001J\u001b\u0010\u0001\u001a\u0004\u0018\u0001092\b\u0010\u001d\u001a\u0004\u0018\u00010\u0017H\u0010¢\u0006\u0003\b\u0001J\t\u0010\u0001\u001a\u00020&H\u0016J\t\u0010\u0001\u001a\u00020&H\u0014R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bX\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u0010X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\t\u0010\u0013\u001a\u00020\u0014X\u0004R\u0011\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00170\u0016X\u0004R\u0011\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00190\u0016X\u0004R\u0016\u0010\u001a\u001a\u0004\u0018\u00010\u00198BX\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0016\u0010\u001d\u001a\u0004\u0018\u00010\u00178@X\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010 \u001a\u00020!8VX\u0004¢\u0006\u0006\u001a\u0004\b \u0010\"R\u0014\u0010#\u001a\u00020!8VX\u0004¢\u0006\u0006\u001a\u0004\b#\u0010\"R\u0014\u0010$\u001a\u00020!8VX\u0004¢\u0006\u0006\u001a\u0004\b$\u0010\"R\u0014\u0010%\u001a\u00020&8BX\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R\u001c\u0010-\u001a\n\u0018\u00010\u0005j\u0004\u0018\u0001`\u00048VX\u0004¢\u0006\u0006\u001a\u0004\b.\u0010/¨\u0006\u0001"}, d2 = {"Lkotlinx/coroutines/CancellableContinuationImpl;", "T", "Lkotlinx/coroutines/DispatchedTask;", "Lkotlinx/coroutines/CancellableContinuation;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/Waiter;", "delegate", "Lkotlin/coroutines/Continuation;", "resumeMode", "", "<init>", "(Lkotlin/coroutines/Continuation;I)V", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "_decisionAndIndex", "Lkotlinx/atomicfu/AtomicInt;", "_state", "Lkotlinx/atomicfu/AtomicRef;", "", "_parentHandle", "Lkotlinx/coroutines/DisposableHandle;", "parentHandle", "getParentHandle", "()Lkotlinx/coroutines/DisposableHandle;", "state", "getState$kotlinx_coroutines_core", "()Ljava/lang/Object;", "isActive", "", "()Z", "isCompleted", "isCancelled", "stateDebugRepresentation", "", "getStateDebugRepresentation", "()Ljava/lang/String;", "initCancellability", "", "isReusable", "resetStateReusable", "callerFrame", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "getStackTraceElement", "Lkotlinx/coroutines/internal/StackTraceElement;", "Ljava/lang/StackTraceElement;", "()Ljava/lang/StackTraceElement;", "takeState", "takeState$kotlinx_coroutines_core", "cancelCompletedResult", "takenState", "cause", "", "cancelCompletedResult$kotlinx_coroutines_core", "cancelLater", "cancel", "parentCancelled", "parentCancelled$kotlinx_coroutines_core", "callCancelHandlerSafely", "block", "Lkotlin/Function0;", "callCancelHandler", "handler", "Lkotlinx/coroutines/CancelHandler;", "callSegmentOnCancellation", "segment", "Lkotlinx/coroutines/internal/Segment;", "callOnCancellation", "R", "onCancellation", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "value", "(Lkotlin/jvm/functions/Function3;Ljava/lang/Throwable;Ljava/lang/Object;)V", "getContinuationCancellationCause", "parent", "Lkotlinx/coroutines/Job;", "trySuspend", "tryResume", "getResult", "installParentHandle", "releaseClaimedReusableContinuation", "releaseClaimedReusableContinuation$kotlinx_coroutines_core", "resumeWith", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)V", "resume", "Lkotlin/Function1;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)V", "invokeOnCancellation", "index", "Lkotlinx/coroutines/CompletionHandler;", "(Lkotlin/jvm/functions/Function1;)V", "invokeOnCancellationInternal", "invokeOnCancellationInternal$kotlinx_coroutines_core", "invokeOnCancellationImpl", "multipleHandlersError", "dispatchResume", "mode", "resumedState", "Lkotlinx/coroutines/NotCompleted;", "proposedUpdate", "idempotent", "(Lkotlinx/coroutines/NotCompleted;Ljava/lang/Object;ILkotlin/jvm/functions/Function3;Ljava/lang/Object;)Ljava/lang/Object;", "resumeImpl", "resumeImpl$kotlinx_coroutines_core", "(Ljava/lang/Object;ILkotlin/jvm/functions/Function3;)V", "tryResumeImpl", "Lkotlinx/coroutines/internal/Symbol;", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/internal/Symbol;", "alreadyResumedError", "", "detachChildIfNonResuable", "detachChild", "detachChild$kotlinx_coroutines_core", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "tryResumeWithException", "exception", "completeResume", "token", "resumeUndispatched", "Lkotlinx/coroutines/CoroutineDispatcher;", "(Lkotlinx/coroutines/CoroutineDispatcher;Ljava/lang/Object;)V", "resumeUndispatchedWithException", "getSuccessfulResult", "getSuccessfulResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Object;", "getExceptionalResult", "getExceptionalResult$kotlinx_coroutines_core", "toString", "nameString", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: CancellableContinuationImpl.kt */
public class CancellableContinuationImpl<T> extends DispatchedTask<T> implements CancellableContinuation<T>, CoroutineStackFrame, Waiter {
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicIntegerFieldUpdater _decisionAndIndex$volatile$FU;
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicReferenceFieldUpdater _parentHandle$volatile$FU;
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicReferenceFieldUpdater _state$volatile$FU;
    private volatile /* synthetic */ int _decisionAndIndex$volatile;
    private volatile /* synthetic */ Object _parentHandle$volatile;
    private volatile /* synthetic */ Object _state$volatile;
    private final CoroutineContext context;
    private final Continuation<T> delegate;

    static {
        Class<CancellableContinuationImpl> cls = CancellableContinuationImpl.class;
        _decisionAndIndex$volatile$FU = AtomicIntegerFieldUpdater.newUpdater(cls, "_decisionAndIndex$volatile");
        _state$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_state$volatile");
        _parentHandle$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_parentHandle$volatile");
    }

    private final /* synthetic */ int get_decisionAndIndex$volatile() {
        return this._decisionAndIndex$volatile;
    }

    private final /* synthetic */ Object get_parentHandle$volatile() {
        return this._parentHandle$volatile;
    }

    private final /* synthetic */ Object get_state$volatile() {
        return this._state$volatile;
    }

    private final /* synthetic */ void loop$atomicfu(Object obj, AtomicIntegerFieldUpdater atomicIntegerFieldUpdater, Function1<? super Integer, Unit> function1) {
        while (true) {
            function1.invoke(Integer.valueOf(atomicIntegerFieldUpdater.get(obj)));
        }
    }

    private final /* synthetic */ void loop$atomicfu(Object obj, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, Function1<Object, Unit> function1) {
        while (true) {
            function1.invoke(atomicReferenceFieldUpdater.get(obj));
        }
    }

    private final /* synthetic */ void set_decisionAndIndex$volatile(int i) {
        this._decisionAndIndex$volatile = i;
    }

    private final /* synthetic */ void set_parentHandle$volatile(Object obj) {
        this._parentHandle$volatile = obj;
    }

    private final /* synthetic */ void set_state$volatile(Object obj) {
        this._state$volatile = obj;
    }

    private final /* synthetic */ void update$atomicfu(Object obj, AtomicIntegerFieldUpdater atomicIntegerFieldUpdater, Function1<? super Integer, Integer> function1) {
        int i;
        do {
            i = atomicIntegerFieldUpdater.get(obj);
        } while (!atomicIntegerFieldUpdater.compareAndSet(obj, i, function1.invoke(Integer.valueOf(i)).intValue()));
    }

    public final Continuation<T> getDelegate$kotlinx_coroutines_core() {
        return this.delegate;
    }

    public CancellableContinuationImpl(Continuation<? super T> delegate2, int resumeMode) {
        super(resumeMode);
        this.delegate = delegate2;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(resumeMode != -1)) {
                throw new AssertionError();
            }
        }
        this.context = this.delegate.getContext();
        this._decisionAndIndex$volatile = (0 << 29) + 536870911;
        this._state$volatile = Active.INSTANCE;
    }

    public CoroutineContext getContext() {
        return this.context;
    }

    private final DisposableHandle getParentHandle() {
        return (DisposableHandle) _parentHandle$volatile$FU.get(this);
    }

    public final Object getState$kotlinx_coroutines_core() {
        return _state$volatile$FU.get(this);
    }

    public boolean isActive() {
        return getState$kotlinx_coroutines_core() instanceof NotCompleted;
    }

    public boolean isCompleted() {
        return !(getState$kotlinx_coroutines_core() instanceof NotCompleted);
    }

    public boolean isCancelled() {
        return getState$kotlinx_coroutines_core() instanceof CancelledContinuation;
    }

    private final String getStateDebugRepresentation() {
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        if (state$kotlinx_coroutines_core instanceof NotCompleted) {
            return "Active";
        }
        if (state$kotlinx_coroutines_core instanceof CancelledContinuation) {
            return "Cancelled";
        }
        return "Completed";
    }

    public void initCancellability() {
        DisposableHandle handle = installParentHandle();
        if (handle != null && isCompleted()) {
            handle.dispose();
            _parentHandle$volatile$FU.set(this, NonDisposableHandle.INSTANCE);
        }
    }

    private final boolean isReusable() {
        if (DispatchedTaskKt.isReusableMode(this.resumeMode)) {
            Continuation<T> continuation = this.delegate;
            Intrinsics.checkNotNull(continuation, "null cannot be cast to non-null type kotlinx.coroutines.internal.DispatchedContinuation<*>");
            if (((DispatchedContinuation) continuation).isReusable$kotlinx_coroutines_core()) {
                return true;
            }
        }
        return false;
    }

    public final boolean resetStateReusable() {
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if ((this.resumeMode == 2 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if ((getParentHandle() != NonDisposableHandle.INSTANCE ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        Object state = _state$volatile$FU.get(this);
        if (DebugKt.getASSERTIONS_ENABLED() && (state instanceof NotCompleted)) {
            throw new AssertionError();
        } else if (!(state instanceof CompletedContinuation) || ((CompletedContinuation) state).idempotentResume == null) {
            _decisionAndIndex$volatile$FU.set(this, (0 << 29) + 536870911);
            _state$volatile$FU.set(this, Active.INSTANCE);
            return true;
        } else {
            detachChild$kotlinx_coroutines_core();
            return false;
        }
    }

    public CoroutineStackFrame getCallerFrame() {
        Continuation<T> continuation = this.delegate;
        if (continuation instanceof CoroutineStackFrame) {
            return (CoroutineStackFrame) continuation;
        }
        return null;
    }

    public StackTraceElement getStackTraceElement() {
        return null;
    }

    public Object takeState$kotlinx_coroutines_core() {
        return getState$kotlinx_coroutines_core();
    }

    public void cancelCompletedResult$kotlinx_coroutines_core(Object takenState, Throwable cause) {
        AtomicReferenceFieldUpdater handler$atomicfu$iv = _state$volatile$FU;
        while (true) {
            Object state = handler$atomicfu$iv.get(this);
            if (state instanceof NotCompleted) {
                throw new IllegalStateException("Not completed".toString());
            } else if (!(state instanceof CompletedExceptionally)) {
                if (state instanceof CompletedContinuation) {
                    if (!((CompletedContinuation) state).getCancelled()) {
                        if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, state, CompletedContinuation.copy$default((CompletedContinuation) state, (Object) null, (CancelHandler) null, (Function3) null, (Object) null, cause, 15, (Object) null))) {
                            ((CompletedContinuation) state).invokeHandlers(this, cause);
                            return;
                        }
                        Throwable th = cause;
                    } else {
                        Throwable th2 = cause;
                        throw new IllegalStateException("Must be called at most once".toString());
                    }
                } else if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, state, new CompletedContinuation(state, (CancelHandler) null, (Function3) null, (Object) null, cause, 14, (DefaultConstructorMarker) null))) {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private final boolean cancelLater(Throwable cause) {
        if (!isReusable()) {
            return false;
        }
        Continuation<T> continuation = this.delegate;
        Intrinsics.checkNotNull(continuation, "null cannot be cast to non-null type kotlinx.coroutines.internal.DispatchedContinuation<*>");
        return ((DispatchedContinuation) continuation).postponeCancellation$kotlinx_coroutines_core(cause);
    }

    public boolean cancel(Throwable cause) {
        Object state;
        boolean z;
        Continuation continuation;
        AtomicReferenceFieldUpdater handler$atomicfu$iv = _state$volatile$FU;
        do {
            state = handler$atomicfu$iv.get(this);
            z = false;
            if (!(state instanceof NotCompleted)) {
                return false;
            }
            continuation = this;
            if ((state instanceof CancelHandler) || (state instanceof Segment)) {
                z = true;
            }
        } while (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, state, new CancelledContinuation(continuation, cause, z)));
        NotCompleted notCompleted = (NotCompleted) state;
        if (notCompleted instanceof CancelHandler) {
            callCancelHandler((CancelHandler) state, cause);
        } else if (notCompleted instanceof Segment) {
            callSegmentOnCancellation((Segment) state, cause);
        }
        detachChildIfNonResuable();
        dispatchResume(this.resumeMode);
        return true;
    }

    public final void parentCancelled$kotlinx_coroutines_core(Throwable cause) {
        if (!cancelLater(cause)) {
            cancel(cause);
            detachChildIfNonResuable();
        }
    }

    private final void callCancelHandlerSafely(Function0<Unit> block) {
        try {
            block.invoke();
        } catch (Throwable ex) {
            CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), new CompletionHandlerException("Exception in invokeOnCancellation handler for " + this, ex));
        }
    }

    public final void callCancelHandler(CancelHandler handler, Throwable cause) {
        try {
            handler.invoke(cause);
        } catch (Throwable ex$iv) {
            CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), new CompletionHandlerException("Exception in invokeOnCancellation handler for " + this, ex$iv));
        }
    }

    private final void callSegmentOnCancellation(Segment<?> segment, Throwable cause) {
        int index = _decisionAndIndex$volatile$FU.get(this) & 536870911;
        if (index != 536870911) {
            try {
                segment.onCancellation(index, cause, getContext());
            } catch (Throwable ex$iv) {
                CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), new CompletionHandlerException("Exception in invokeOnCancellation handler for " + this, ex$iv));
            }
        } else {
            throw new IllegalStateException("The index for Segment.onCancellation(..) is broken".toString());
        }
    }

    public final <R> void callOnCancellation(Function3<? super Throwable, ? super R, ? super CoroutineContext, Unit> onCancellation, Throwable cause, R value) {
        try {
            onCancellation.invoke(cause, value, getContext());
        } catch (Throwable ex) {
            CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), new CompletionHandlerException("Exception in resume onCancellation handler for " + this, ex));
        }
    }

    public Throwable getContinuationCancellationCause(Job parent) {
        return parent.getCancellationException();
    }

    private final boolean trySuspend() {
        int cur;
        AtomicIntegerFieldUpdater handler$atomicfu$iv = _decisionAndIndex$volatile$FU;
        do {
            cur = handler$atomicfu$iv.get(this);
            switch (cur >> 29) {
                case 0:
                    break;
                case 2:
                    return false;
                default:
                    throw new IllegalStateException("Already suspended".toString());
            }
        } while (!_decisionAndIndex$volatile$FU.compareAndSet(this, cur, (1 << 29) + (cur & 536870911)));
        return true;
    }

    private final boolean tryResume() {
        int cur;
        AtomicIntegerFieldUpdater handler$atomicfu$iv = _decisionAndIndex$volatile$FU;
        do {
            cur = handler$atomicfu$iv.get(this);
            switch (cur >> 29) {
                case 0:
                    break;
                case 1:
                    return false;
                default:
                    throw new IllegalStateException("Already resumed".toString());
            }
        } while (!_decisionAndIndex$volatile$FU.compareAndSet(this, cur, (2 << 29) + (cur & 536870911)));
        return true;
    }

    public final Object getResult() {
        Job job;
        Throwable th;
        boolean isReusable = isReusable();
        if (trySuspend()) {
            if (getParentHandle() == null) {
                installParentHandle();
            }
            if (isReusable) {
                releaseClaimedReusableContinuation$kotlinx_coroutines_core();
            }
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
        if (isReusable) {
            releaseClaimedReusableContinuation$kotlinx_coroutines_core();
        }
        Object state = getState$kotlinx_coroutines_core();
        if (state instanceof CompletedExceptionally) {
            Throwable exception$iv = ((CompletedExceptionally) state).cause;
            if (DebugKt.getRECOVER_STACK_TRACES() && (this instanceof CoroutineStackFrame)) {
                exception$iv = StackTraceRecoveryKt.recoverFromStackFrame(exception$iv, this);
            }
            throw exception$iv;
        } else if (!DispatchedTaskKt.isCancellableMode(this.resumeMode) || (job = (Job) getContext().get(Job.Key)) == null || job.isActive()) {
            return getSuccessfulResult$kotlinx_coroutines_core(state);
        } else {
            CancellationException cause = job.getCancellationException();
            cancelCompletedResult$kotlinx_coroutines_core(state, cause);
            if (!DebugKt.getRECOVER_STACK_TRACES() || !(this instanceof CoroutineStackFrame)) {
                th = cause;
            } else {
                th = StackTraceRecoveryKt.recoverFromStackFrame(cause, this);
            }
            throw th;
        }
    }

    private final DisposableHandle installParentHandle() {
        Job parent = (Job) getContext().get(Job.Key);
        if (parent == null) {
            return null;
        }
        DisposableHandle handle = JobKt__JobKt.invokeOnCompletion$default(parent, false, new ChildContinuation(this), 1, (Object) null);
        AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_parentHandle$volatile$FU, this, (Object) null, handle);
        return handle;
    }

    public final void releaseClaimedReusableContinuation$kotlinx_coroutines_core() {
        Throwable cancellationCause;
        Continuation<T> continuation = this.delegate;
        DispatchedContinuation dispatchedContinuation = continuation instanceof DispatchedContinuation ? (DispatchedContinuation) continuation : null;
        if (dispatchedContinuation != null && (cancellationCause = dispatchedContinuation.tryReleaseClaimedContinuation$kotlinx_coroutines_core(this)) != null) {
            detachChild$kotlinx_coroutines_core();
            cancel(cancellationCause);
        }
    }

    public void resumeWith(Object result) {
        resumeImpl$kotlinx_coroutines_core$default(this, CompletionStateKt.toState(result, this), this.resumeMode, (Function3) null, 4, (Object) null);
    }

    /* access modifiers changed from: private */
    public static final Unit resume$lambda$13$lambda$12(Function1 $onCancellation, Throwable cause, Object obj, CoroutineContext coroutineContext) {
        $onCancellation.invoke(cause);
        return Unit.INSTANCE;
    }

    public void resume(T value, Function1<? super Throwable, Unit> onCancellation) {
        CancellableContinuationImpl$$ExternalSyntheticLambda0 cancellableContinuationImpl$$ExternalSyntheticLambda0;
        int i = this.resumeMode;
        if (onCancellation != null) {
            Function1<? super Throwable, Unit> function1 = onCancellation;
            cancellableContinuationImpl$$ExternalSyntheticLambda0 = new CancellableContinuationImpl$$ExternalSyntheticLambda0(onCancellation);
        } else {
            cancellableContinuationImpl$$ExternalSyntheticLambda0 = null;
        }
        resumeImpl$kotlinx_coroutines_core(value, i, cancellableContinuationImpl$$ExternalSyntheticLambda0);
    }

    public <R extends T> void resume(R value, Function3<? super Throwable, ? super R, ? super CoroutineContext, Unit> onCancellation) {
        resumeImpl$kotlinx_coroutines_core(value, this.resumeMode, onCancellation);
    }

    public void invokeOnCancellation(Segment<?> segment, int index) {
        int i;
        int it;
        AtomicIntegerFieldUpdater handler$atomicfu$iv = _decisionAndIndex$volatile$FU;
        do {
            i = handler$atomicfu$iv.get(this);
            it = i;
            if (!((it & 536870911) == 536870911)) {
                throw new IllegalStateException("invokeOnCancellation should be called at most once".toString());
            }
        } while (!handler$atomicfu$iv.compareAndSet(this, i, ((it >> 29) << 29) + index));
        invokeOnCancellationImpl(segment);
    }

    public void invokeOnCancellation(Function1<? super Throwable, Unit> handler) {
        CancellableContinuationKt.invokeOnCancellation(this, new CancelHandler.UserSupplied(handler));
    }

    public final void invokeOnCancellationInternal$kotlinx_coroutines_core(CancelHandler handler) {
        invokeOnCancellationImpl(handler);
    }

    private final void invokeOnCancellationImpl(Object handler) {
        Object obj = handler;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!((obj instanceof CancelHandler) || (obj instanceof Segment))) {
                throw new AssertionError();
            }
        }
        AtomicReferenceFieldUpdater handler$atomicfu$iv = _state$volatile$FU;
        while (true) {
            Object state = handler$atomicfu$iv.get(this);
            if (state instanceof Active) {
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, state, obj)) {
                    return;
                }
            } else if ((state instanceof CancelHandler) || (state instanceof Segment)) {
                multipleHandlersError(obj, state);
            } else if (state instanceof CompletedExceptionally) {
                if (!((CompletedExceptionally) state).makeHandled()) {
                    multipleHandlersError(obj, state);
                }
                if (state instanceof CancelledContinuation) {
                    Throwable cause = null;
                    CompletedExceptionally completedExceptionally = state instanceof CompletedExceptionally ? (CompletedExceptionally) state : null;
                    if (completedExceptionally != null) {
                        cause = completedExceptionally.cause;
                    }
                    if (obj instanceof CancelHandler) {
                        callCancelHandler((CancelHandler) obj, cause);
                        return;
                    }
                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlinx.coroutines.internal.Segment<*>");
                    callSegmentOnCancellation((Segment) obj, cause);
                    return;
                }
                return;
            } else if (state instanceof CompletedContinuation) {
                if (((CompletedContinuation) state).cancelHandler != null) {
                    multipleHandlersError(obj, state);
                }
                if (!(obj instanceof Segment)) {
                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlinx.coroutines.CancelHandler");
                    CancelHandler cancelHandler = (CancelHandler) obj;
                    if (((CompletedContinuation) state).getCancelled()) {
                        callCancelHandler((CancelHandler) obj, ((CompletedContinuation) state).cancelCause);
                        return;
                    } else {
                        if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, state, CompletedContinuation.copy$default((CompletedContinuation) state, (Object) null, (CancelHandler) obj, (Function3) null, (Object) null, (Throwable) null, 29, (Object) null))) {
                            return;
                        }
                    }
                } else {
                    return;
                }
            } else if (!(obj instanceof Segment)) {
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlinx.coroutines.CancelHandler");
                CancelHandler cancelHandler2 = (CancelHandler) obj;
                if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_state$volatile$FU, this, state, new CompletedContinuation(state, (CancelHandler) obj, (Function3) null, (Object) null, (Throwable) null, 28, (DefaultConstructorMarker) null))) {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private final void multipleHandlersError(Object handler, Object state) {
        throw new IllegalStateException(("It's prohibited to register multiple handlers, tried to register " + handler + ", already has " + state).toString());
    }

    private final void dispatchResume(int mode) {
        if (!tryResume()) {
            DispatchedTaskKt.dispatch(this, mode);
        }
    }

    private final <R> Object resumedState(NotCompleted state, R proposedUpdate, int resumeMode, Function3<? super Throwable, ? super R, ? super CoroutineContext, Unit> onCancellation, Object idempotent) {
        if (proposedUpdate instanceof CompletedExceptionally) {
            boolean z = true;
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if ((idempotent == null ? 1 : 0) == 0) {
                    throw new AssertionError();
                }
            }
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if (onCancellation != null) {
                    z = false;
                }
                if (!z) {
                    throw new AssertionError();
                }
            }
        } else if ((DispatchedTaskKt.isCancellableMode(resumeMode) || idempotent != null) && !(onCancellation == null && !(state instanceof CancelHandler) && idempotent == null)) {
            return new CompletedContinuation(proposedUpdate, state instanceof CancelHandler ? (CancelHandler) state : null, onCancellation, idempotent, (Throwable) null, 16, (DefaultConstructorMarker) null);
        }
        R r = proposedUpdate;
        R r2 = r;
        Function3<? super Throwable, ? super R, ? super CoroutineContext, Unit> function3 = onCancellation;
        Object obj = idempotent;
        return r;
    }

    public static /* synthetic */ void resumeImpl$kotlinx_coroutines_core$default(CancellableContinuationImpl cancellableContinuationImpl, Object obj, int i, Function3 function3, int i2, Object obj2) {
        if (obj2 == null) {
            if ((i2 & 4) != 0) {
                function3 = null;
            }
            cancellableContinuationImpl.resumeImpl$kotlinx_coroutines_core(obj, i, function3);
            return;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: resumeImpl");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=R, code=java.lang.Object, for r12v0, types: [R] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <R> void resumeImpl$kotlinx_coroutines_core(java.lang.Object r12, int r13, kotlin.jvm.functions.Function3<? super java.lang.Throwable, ? super R, ? super kotlin.coroutines.CoroutineContext, kotlin.Unit> r14) {
        /*
            r11 = this;
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = _state$volatile$FU
            r1 = r11
        L_0x0005:
            java.lang.Object r2 = r0.get(r11)
            r3 = 0
            boolean r4 = r2 instanceof kotlinx.coroutines.NotCompleted
            if (r4 == 0) goto L_0x0030
            r6 = r2
            kotlinx.coroutines.NotCompleted r6 = (kotlinx.coroutines.NotCompleted) r6
            r10 = 0
            r5 = r11
            r7 = r12
            r8 = r13
            r9 = r14
            java.lang.Object r12 = r5.resumedState(r6, r7, r8, r9, r10)
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r13 = _state$volatile$FU
            boolean r13 = androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(r13, r11, r2, r12)
            if (r13 == 0) goto L_0x002c
            r11.detachChildIfNonResuable()
            r11.dispatchResume(r8)
            return
        L_0x002c:
            r12 = r7
            r13 = r8
            r14 = r9
            goto L_0x0005
        L_0x0030:
            r5 = r11
            r7 = r12
            r8 = r13
            r9 = r14
            boolean r12 = r2 instanceof kotlinx.coroutines.CancelledContinuation
            if (r12 == 0) goto L_0x004e
            r12 = r2
            kotlinx.coroutines.CancelledContinuation r12 = (kotlinx.coroutines.CancelledContinuation) r12
            boolean r12 = r12.makeResumed()
            if (r12 == 0) goto L_0x004e
            if (r9 == 0) goto L_0x004d
            r12 = r9
            r13 = 0
            r14 = r2
            kotlinx.coroutines.CancelledContinuation r14 = (kotlinx.coroutines.CancelledContinuation) r14
            java.lang.Throwable r14 = r14.cause
            r11.callOnCancellation(r12, r14, r7)
        L_0x004d:
            return
        L_0x004e:
            r11.alreadyResumedError(r7)
            kotlin.KotlinNothingValueException r12 = new kotlin.KotlinNothingValueException
            r12.<init>()
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.CancellableContinuationImpl.resumeImpl$kotlinx_coroutines_core(java.lang.Object, int, kotlin.jvm.functions.Function3):void");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=R, code=java.lang.Object, for r12v0, types: [R] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final <R> kotlinx.coroutines.internal.Symbol tryResumeImpl(java.lang.Object r12, java.lang.Object r13, kotlin.jvm.functions.Function3<? super java.lang.Throwable, ? super R, ? super kotlin.coroutines.CoroutineContext, kotlin.Unit> r14) {
        /*
            r11 = this;
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = _state$volatile$FU
            r1 = r11
        L_0x0005:
            java.lang.Object r2 = r0.get(r11)
            r3 = 0
            boolean r4 = r2 instanceof kotlinx.coroutines.NotCompleted
            if (r4 == 0) goto L_0x0030
            r6 = r2
            kotlinx.coroutines.NotCompleted r6 = (kotlinx.coroutines.NotCompleted) r6
            int r8 = r11.resumeMode
            r5 = r11
            r7 = r12
            r10 = r13
            r9 = r14
            java.lang.Object r12 = r5.resumedState(r6, r7, r8, r9, r10)
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r13 = _state$volatile$FU
            boolean r13 = androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(r13, r11, r2, r12)
            if (r13 == 0) goto L_0x002c
            r11.detachChildIfNonResuable()
            kotlinx.coroutines.internal.Symbol r13 = kotlinx.coroutines.CancellableContinuationImplKt.RESUME_TOKEN
            return r13
        L_0x002c:
            r12 = r7
            r14 = r9
            r13 = r10
            goto L_0x0005
        L_0x0030:
            r5 = r11
            r7 = r12
            r10 = r13
            r9 = r14
            boolean r12 = r2 instanceof kotlinx.coroutines.CompletedContinuation
            r13 = 0
            if (r12 == 0) goto L_0x0060
            if (r10 == 0) goto L_0x005e
            r12 = r2
            kotlinx.coroutines.CompletedContinuation r12 = (kotlinx.coroutines.CompletedContinuation) r12
            java.lang.Object r12 = r12.idempotentResume
            if (r12 != r10) goto L_0x005e
            boolean r12 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()
            if (r12 == 0) goto L_0x005b
            r12 = 0
            r13 = r2
            kotlinx.coroutines.CompletedContinuation r13 = (kotlinx.coroutines.CompletedContinuation) r13
            R r13 = r13.result
            boolean r12 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r13, (java.lang.Object) r7)
            if (r12 == 0) goto L_0x0055
            goto L_0x005b
        L_0x0055:
            java.lang.AssertionError r12 = new java.lang.AssertionError
            r12.<init>()
            throw r12
        L_0x005b:
            kotlinx.coroutines.internal.Symbol r13 = kotlinx.coroutines.CancellableContinuationImplKt.RESUME_TOKEN
            goto L_0x005f
        L_0x005e:
        L_0x005f:
            return r13
        L_0x0060:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.CancellableContinuationImpl.tryResumeImpl(java.lang.Object, java.lang.Object, kotlin.jvm.functions.Function3):kotlinx.coroutines.internal.Symbol");
    }

    private final Void alreadyResumedError(Object proposedUpdate) {
        throw new IllegalStateException(("Already resumed, but proposed with update " + proposedUpdate).toString());
    }

    private final void detachChildIfNonResuable() {
        if (!isReusable()) {
            detachChild$kotlinx_coroutines_core();
        }
    }

    public final void detachChild$kotlinx_coroutines_core() {
        DisposableHandle handle = getParentHandle();
        if (handle != null) {
            handle.dispose();
            _parentHandle$volatile$FU.set(this, NonDisposableHandle.INSTANCE);
        }
    }

    public Object tryResume(T value, Object idempotent) {
        return tryResumeImpl(value, idempotent, (Function3) null);
    }

    public <R extends T> Object tryResume(R value, Object idempotent, Function3<? super Throwable, ? super R, ? super CoroutineContext, Unit> onCancellation) {
        return tryResumeImpl(value, idempotent, onCancellation);
    }

    public Object tryResumeWithException(Throwable exception) {
        return tryResumeImpl(new CompletedExceptionally(exception, false, 2, (DefaultConstructorMarker) null), (Object) null, (Function3) null);
    }

    public void completeResume(Object token) {
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(token == CancellableContinuationImplKt.RESUME_TOKEN)) {
                throw new AssertionError();
            }
        }
        dispatchResume(this.resumeMode);
    }

    public void resumeUndispatched(CoroutineDispatcher $this$resumeUndispatched, T value) {
        Continuation<T> continuation = this.delegate;
        CoroutineDispatcher coroutineDispatcher = null;
        DispatchedContinuation dc = continuation instanceof DispatchedContinuation ? (DispatchedContinuation) continuation : null;
        if (dc != null) {
            coroutineDispatcher = dc.dispatcher;
        }
        resumeImpl$kotlinx_coroutines_core$default(this, value, coroutineDispatcher == $this$resumeUndispatched ? 4 : this.resumeMode, (Function3) null, 4, (Object) null);
    }

    public void resumeUndispatchedWithException(CoroutineDispatcher $this$resumeUndispatchedWithException, Throwable exception) {
        Continuation<T> continuation = this.delegate;
        CoroutineDispatcher coroutineDispatcher = null;
        DispatchedContinuation dc = continuation instanceof DispatchedContinuation ? (DispatchedContinuation) continuation : null;
        CompletedExceptionally completedExceptionally = new CompletedExceptionally(exception, false, 2, (DefaultConstructorMarker) null);
        if (dc != null) {
            coroutineDispatcher = dc.dispatcher;
        }
        resumeImpl$kotlinx_coroutines_core$default(this, completedExceptionally, coroutineDispatcher == $this$resumeUndispatchedWithException ? 4 : this.resumeMode, (Function3) null, 4, (Object) null);
    }

    public <T> T getSuccessfulResult$kotlinx_coroutines_core(Object state) {
        if (state instanceof CompletedContinuation) {
            return ((CompletedContinuation) state).result;
        }
        return state;
    }

    public Throwable getExceptionalResult$kotlinx_coroutines_core(Object state) {
        Throwable it = super.getExceptionalResult$kotlinx_coroutines_core(state);
        if (it == null) {
            return null;
        }
        Continuation<T> continuation = this.delegate;
        if (!DebugKt.getRECOVER_STACK_TRACES() || !(continuation instanceof CoroutineStackFrame)) {
            return it;
        }
        return StackTraceRecoveryKt.recoverFromStackFrame(it, (CoroutineStackFrame) continuation);
    }

    public String toString() {
        return nameString() + '(' + DebugStringsKt.toDebugString(this.delegate) + "){" + getStateDebugRepresentation() + "}@" + DebugStringsKt.getHexAddress(this);
    }

    /* access modifiers changed from: protected */
    public String nameString() {
        return "CancellableContinuation";
    }
}
