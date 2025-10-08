package kotlinx.coroutines.intrinsics;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.CompletedExceptionally;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.JobSupportKt;
import kotlinx.coroutines.TimeoutCancellationException;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.ThreadContextKt;

@Metadata(d1 = {"\u0000<\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aO\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00042\u0006\u0010\u0007\u001a\u0002H\u00022\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0005H\u0000¢\u0006\u0002\u0010\t\u001aV\u0010\n\u001a\u0004\u0018\u00010\u0006\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00030\u000b2\u0006\u0010\u0007\u001a\u0002H\u00022'\u0010\f\u001a#\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004¢\u0006\u0002\b\rH\u0000¢\u0006\u0002\u0010\u000e\u001aV\u0010\u000f\u001a\u0004\u0018\u00010\u0006\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00030\u000b2\u0006\u0010\u0007\u001a\u0002H\u00022'\u0010\f\u001a#\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004¢\u0006\u0002\b\rH\u0000¢\u0006\u0002\u0010\u000e\u001a?\u0010\u0010\u001a\u0004\u0018\u00010\u0006\"\u0004\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u000b2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00140\u00122\u000e\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0016H\b¨\u0006\u0017"}, d2 = {"startCoroutineUndispatched", "", "R", "T", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "receiver", "completion", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)V", "startUndispatchedOrReturn", "Lkotlinx/coroutines/internal/ScopeCoroutine;", "block", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/internal/ScopeCoroutine;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "startUndispatchedOrReturnIgnoreTimeout", "undispatchedResult", "shouldThrow", "Lkotlin/Function1;", "", "", "startBlock", "Lkotlin/Function0;", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: Undispatched.kt */
public final class UndispatchedKt {
    public static final <R, T> void startCoroutineUndispatched(Function2<? super R, ? super Continuation<? super T>, ? extends Object> $this$startCoroutineUndispatched, R receiver, Continuation<? super T> completion) {
        CoroutineContext context$iv;
        Object oldValue$iv;
        Continuation actualCompletion = DebugProbesKt.probeCoroutineCreated(completion);
        try {
            context$iv = actualCompletion.getContext();
            oldValue$iv = ThreadContextKt.updateThreadContext(context$iv, (Object) null);
            DebugProbesKt.probeCoroutineResumed(actualCompletion);
            Object value = ($this$startCoroutineUndispatched instanceof BaseContinuationImpl) == 0 ? IntrinsicsKt.wrapWithContinuationImpl($this$startCoroutineUndispatched, receiver, actualCompletion) : ((Function2) TypeIntrinsics.beforeCheckcastToFunctionOfArity($this$startCoroutineUndispatched, 2)).invoke(receiver, actualCompletion);
            ThreadContextKt.restoreThreadContext(context$iv, oldValue$iv);
            if (value != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                Result.Companion companion = Result.Companion;
                actualCompletion.resumeWith(Result.m6constructorimpl(value));
            }
        } catch (Throwable e) {
            Result.Companion companion2 = Result.Companion;
            actualCompletion.resumeWith(Result.m6constructorimpl(ResultKt.createFailure(e)));
        }
    }

    public static final <T, R> Object startUndispatchedOrReturn(ScopeCoroutine<? super T> $this$startUndispatchedOrReturn, R receiver, Function2<? super R, ? super Continuation<? super T>, ? extends Object> block) {
        Object result$iv;
        ScopeCoroutine $this$undispatchedResult$iv = $this$startUndispatchedOrReturn;
        try {
            result$iv = !(block instanceof BaseContinuationImpl) ? IntrinsicsKt.wrapWithContinuationImpl(block, receiver, $this$startUndispatchedOrReturn) : ((Function2) TypeIntrinsics.beforeCheckcastToFunctionOfArity(block, 2)).invoke(receiver, $this$startUndispatchedOrReturn);
        } catch (Throwable e$iv) {
            result$iv = new CompletedExceptionally(e$iv, false, 2, (DefaultConstructorMarker) null);
        }
        if (result$iv == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
        Object state$iv = $this$undispatchedResult$iv.makeCompletingOnce$kotlinx_coroutines_core(result$iv);
        if (state$iv == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
        if (!(state$iv instanceof CompletedExceptionally)) {
            return JobSupportKt.unboxState(state$iv);
        }
        Throwable th = ((CompletedExceptionally) state$iv).cause;
        Throwable exception$iv$iv = ((CompletedExceptionally) state$iv).cause;
        Continuation<T> continuation = $this$undispatchedResult$iv.uCont;
        if (DebugKt.getRECOVER_STACK_TRACES() && (continuation instanceof CoroutineStackFrame)) {
            exception$iv$iv = StackTraceRecoveryKt.recoverFromStackFrame(exception$iv$iv, (CoroutineStackFrame) continuation);
        }
        throw exception$iv$iv;
    }

    public static final <T, R> Object startUndispatchedOrReturnIgnoreTimeout(ScopeCoroutine<? super T> $this$startUndispatchedOrReturnIgnoreTimeout, R receiver, Function2<? super R, ? super Continuation<? super T>, ? extends Object> block) {
        Object result$iv;
        Object state$iv;
        ScopeCoroutine $this$undispatchedResult$iv = $this$startUndispatchedOrReturnIgnoreTimeout;
        boolean z = false;
        try {
            result$iv = !(block instanceof BaseContinuationImpl) ? IntrinsicsKt.wrapWithContinuationImpl(block, receiver, $this$startUndispatchedOrReturnIgnoreTimeout) : ((Function2) TypeIntrinsics.beforeCheckcastToFunctionOfArity(block, 2)).invoke(receiver, $this$startUndispatchedOrReturnIgnoreTimeout);
        } catch (Throwable e$iv) {
            result$iv = new CompletedExceptionally(e$iv, false, 2, (DefaultConstructorMarker) null);
        }
        if (result$iv == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
        Object state$iv2 = $this$undispatchedResult$iv.makeCompletingOnce$kotlinx_coroutines_core(result$iv);
        if (state$iv2 == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
        if (state$iv2 instanceof CompletedExceptionally) {
            Throwable e = ((CompletedExceptionally) state$iv2).cause;
            if (!(e instanceof TimeoutCancellationException) || ((TimeoutCancellationException) e).coroutine != $this$startUndispatchedOrReturnIgnoreTimeout) {
                z = true;
            }
            if (z) {
                Throwable exception$iv$iv = ((CompletedExceptionally) state$iv2).cause;
                Continuation<T> continuation = $this$undispatchedResult$iv.uCont;
                if (DebugKt.getRECOVER_STACK_TRACES() && (continuation instanceof CoroutineStackFrame)) {
                    exception$iv$iv = StackTraceRecoveryKt.recoverFromStackFrame(exception$iv$iv, (CoroutineStackFrame) continuation);
                }
                throw exception$iv$iv;
            } else if (result$iv instanceof CompletedExceptionally) {
                Throwable exception$iv$iv2 = ((CompletedExceptionally) result$iv).cause;
                Continuation<T> continuation2 = $this$undispatchedResult$iv.uCont;
                if (DebugKt.getRECOVER_STACK_TRACES() && (continuation2 instanceof CoroutineStackFrame)) {
                    exception$iv$iv2 = StackTraceRecoveryKt.recoverFromStackFrame(exception$iv$iv2, (CoroutineStackFrame) continuation2);
                }
                throw exception$iv$iv2;
            } else {
                state$iv = result$iv;
            }
        } else {
            state$iv = JobSupportKt.unboxState(state$iv2);
        }
        return state$iv;
    }

    private static final <T> Object undispatchedResult(ScopeCoroutine<? super T> $this$undispatchedResult, Function1<? super Throwable, Boolean> shouldThrow, Function0<? extends Object> startBlock) {
        Object result;
        try {
            result = startBlock.invoke();
        } catch (Throwable e) {
            result = new CompletedExceptionally(e, false, 2, (DefaultConstructorMarker) null);
        }
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
        Object state = $this$undispatchedResult.makeCompletingOnce$kotlinx_coroutines_core(result);
        if (state == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
        if (!(state instanceof CompletedExceptionally)) {
            return JobSupportKt.unboxState(state);
        }
        if (shouldThrow.invoke(((CompletedExceptionally) state).cause).booleanValue()) {
            Throwable exception$iv = ((CompletedExceptionally) state).cause;
            Continuation<T> continuation = $this$undispatchedResult.uCont;
            if (DebugKt.getRECOVER_STACK_TRACES() && (continuation instanceof CoroutineStackFrame)) {
                exception$iv = StackTraceRecoveryKt.recoverFromStackFrame(exception$iv, (CoroutineStackFrame) continuation);
            }
            throw exception$iv;
        } else if (!(result instanceof CompletedExceptionally)) {
            return result;
        } else {
            Throwable exception$iv2 = ((CompletedExceptionally) result).cause;
            Continuation<T> continuation2 = $this$undispatchedResult.uCont;
            if (DebugKt.getRECOVER_STACK_TRACES() && (continuation2 instanceof CoroutineStackFrame)) {
                exception$iv2 = StackTraceRecoveryKt.recoverFromStackFrame(exception$iv2, (CoroutineStackFrame) continuation2);
            }
            throw exception$iv2;
        }
    }
}
