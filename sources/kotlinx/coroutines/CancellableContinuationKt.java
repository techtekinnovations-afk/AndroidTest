package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.internal.DispatchedContinuation;

@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0000\u001a0\u0010\u0006\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u00022\u001a\b\u0004\u0010\u0007\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0003\u0012\u0004\u0012\u00020\u00010\bHH¢\u0006\u0002\u0010\t\u001a0\u0010\n\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u00022\u001a\b\u0004\u0010\u0007\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u000b\u0012\u0004\u0012\u00020\u00010\bHH¢\u0006\u0002\u0010\t\u001a\"\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u000b\"\u0004\b\u0000\u0010\u00022\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00020\u000eH\u0000\u001a\u0018\u0010\u000f\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010\u0010\u001a\u00020\u0011H\u0007¨\u0006\u0012"}, d2 = {"invokeOnCancellation", "", "T", "Lkotlinx/coroutines/CancellableContinuation;", "handler", "Lkotlinx/coroutines/CancelHandler;", "suspendCancellableCoroutine", "block", "Lkotlin/Function1;", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "suspendCancellableCoroutineReusable", "Lkotlinx/coroutines/CancellableContinuationImpl;", "getOrCreateCancellableContinuation", "delegate", "Lkotlin/coroutines/Continuation;", "disposeOnCancellation", "handle", "Lkotlinx/coroutines/DisposableHandle;", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: CancellableContinuation.kt */
public final class CancellableContinuationKt {
    public static final <T> void invokeOnCancellation(CancellableContinuation<? super T> $this$invokeOnCancellation, CancelHandler handler) {
        if ($this$invokeOnCancellation instanceof CancellableContinuationImpl) {
            ((CancellableContinuationImpl) $this$invokeOnCancellation).invokeOnCancellationInternal$kotlinx_coroutines_core(handler);
            return;
        }
        throw new UnsupportedOperationException("third-party implementation of CancellableContinuation is not supported");
    }

    public static final <T> Object suspendCancellableCoroutine(Function1<? super CancellableContinuation<? super T>, Unit> block, Continuation<? super T> $completion) {
        CancellableContinuationImpl cancellable = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 1);
        cancellable.initCancellability();
        block.invoke(cancellable);
        Object result = cancellable.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result;
    }

    private static final <T> Object suspendCancellableCoroutine$$forInline(Function1<? super CancellableContinuation<? super T>, Unit> block, Continuation<? super T> $completion) {
        CancellableContinuationImpl cancellable = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 1);
        cancellable.initCancellability();
        block.invoke(cancellable);
        Object result = cancellable.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result;
    }

    public static final <T> Object suspendCancellableCoroutineReusable(Function1<? super CancellableContinuationImpl<? super T>, Unit> block, Continuation<? super T> $completion) {
        CancellableContinuationImpl cancellable = getOrCreateCancellableContinuation(IntrinsicsKt.intercepted($completion));
        try {
            block.invoke(cancellable);
            Object result = cancellable.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended($completion);
            }
            return result;
        } catch (Throwable e) {
            cancellable.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
            throw e;
        }
    }

    private static final <T> Object suspendCancellableCoroutineReusable$$forInline(Function1<? super CancellableContinuationImpl<? super T>, Unit> block, Continuation<? super T> $completion) {
        CancellableContinuationImpl cancellable = getOrCreateCancellableContinuation(IntrinsicsKt.intercepted($completion));
        try {
            block.invoke(cancellable);
            Object result = cancellable.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended($completion);
            }
            return result;
        } catch (Throwable e) {
            cancellable.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
            throw e;
        }
    }

    public static final <T> CancellableContinuationImpl<T> getOrCreateCancellableContinuation(Continuation<? super T> delegate) {
        if (!(delegate instanceof DispatchedContinuation)) {
            return new CancellableContinuationImpl<>(delegate, 1);
        }
        CancellableContinuationImpl it = ((DispatchedContinuation) delegate).claimReusableCancellableContinuation$kotlinx_coroutines_core();
        if (it != null) {
            if (!it.resetStateReusable()) {
                it = null;
            }
            if (it != null) {
                return it;
            }
        }
        return new CancellableContinuationImpl<>(delegate, 2);
    }

    public static final void disposeOnCancellation(CancellableContinuation<?> $this$disposeOnCancellation, DisposableHandle handle) {
        invokeOnCancellation($this$disposeOnCancellation, new DisposeOnCancel(handle));
    }
}
