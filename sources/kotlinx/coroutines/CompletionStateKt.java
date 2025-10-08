package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;

@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001f\u0010\u0000\u001a\u0004\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0000¢\u0006\u0002\u0010\u0004\u001a+\u0010\u0000\u001a\u0004\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006H\u0000¢\u0006\u0002\u0010\u0007\u001a1\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u00022\b\u0010\t\u001a\u0004\u0018\u00010\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u000bH\u0000¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"toState", "", "T", "Lkotlin/Result;", "(Ljava/lang/Object;)Ljava/lang/Object;", "caller", "Lkotlinx/coroutines/CancellableContinuation;", "(Ljava/lang/Object;Lkotlinx/coroutines/CancellableContinuation;)Ljava/lang/Object;", "recoverResult", "state", "uCont", "Lkotlin/coroutines/Continuation;", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: CompletionState.kt */
public final class CompletionStateKt {
    public static final <T> Object toState(Object $this$toState) {
        Throwable it = Result.m9exceptionOrNullimpl($this$toState);
        if (it == null) {
            return $this$toState;
        }
        return new CompletedExceptionally(it, false, 2, (DefaultConstructorMarker) null);
    }

    public static final <T> Object toState(Object $this$toState, CancellableContinuation<?> caller) {
        Throwable th;
        Throwable it = Result.m9exceptionOrNullimpl($this$toState);
        if (it == null) {
            return $this$toState;
        }
        if (!DebugKt.getRECOVER_STACK_TRACES() || !(caller instanceof CoroutineStackFrame)) {
            th = it;
        } else {
            th = StackTraceRecoveryKt.recoverFromStackFrame(it, (CoroutineStackFrame) caller);
        }
        return new CompletedExceptionally(th, false, 2, (DefaultConstructorMarker) null);
    }

    public static final <T> Object recoverResult(Object state, Continuation<? super T> uCont) {
        if (state instanceof CompletedExceptionally) {
            Result.Companion companion = Result.Companion;
            Throwable exception$iv = ((CompletedExceptionally) state).cause;
            if (DebugKt.getRECOVER_STACK_TRACES() && (uCont instanceof CoroutineStackFrame)) {
                exception$iv = StackTraceRecoveryKt.recoverFromStackFrame(exception$iv, (CoroutineStackFrame) uCont);
            }
            return Result.m6constructorimpl(ResultKt.createFailure(exception$iv));
        }
        Result.Companion companion2 = Result.Companion;
        return Result.m6constructorimpl(state);
    }
}
