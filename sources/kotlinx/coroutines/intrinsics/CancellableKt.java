package kotlinx.coroutines.intrinsics;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.internal.DispatchedContinuationKt;

@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\u001a;\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00032\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004H\u0007¢\u0006\u0002\u0010\u0007\u001aO\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\b\"\u0004\b\u0001\u0010\u0002*\u001e\b\u0001\u0012\u0004\u0012\u0002H\b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00050\t2\u0006\u0010\n\u001a\u0002H\b2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004H\u0000¢\u0006\u0002\u0010\u000b\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u00042\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\u0004H\u0000\u001a#\u0010\r\u001a\u00020\u00012\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u00042\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000fH\b\u001a\u001c\u0010\u0010\u001a\u00020\u00012\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0011\u001a\u00020\u0012H\u0002¨\u0006\u0013"}, d2 = {"startCoroutineCancellable", "", "T", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "completion", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)V", "R", "Lkotlin/Function2;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)V", "fatalCompletion", "runSafely", "block", "Lkotlin/Function0;", "dispatcherFailure", "e", "", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: Cancellable.kt */
public final class CancellableKt {
    public static final <T> void startCoroutineCancellable(Function1<? super Continuation<? super T>, ? extends Object> $this$startCoroutineCancellable, Continuation<? super T> completion) {
        try {
            Continuation<Unit> intercepted = IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted($this$startCoroutineCancellable, completion));
            Result.Companion companion = Result.Companion;
            DispatchedContinuationKt.resumeCancellableWith(intercepted, Result.m6constructorimpl(Unit.INSTANCE));
        } catch (Throwable e$iv) {
            dispatcherFailure(completion, e$iv);
        }
    }

    public static final <R, T> void startCoroutineCancellable(Function2<? super R, ? super Continuation<? super T>, ? extends Object> $this$startCoroutineCancellable, R receiver, Continuation<? super T> completion) {
        try {
            Continuation<Unit> intercepted = IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted($this$startCoroutineCancellable, receiver, completion));
            Result.Companion companion = Result.Companion;
            DispatchedContinuationKt.resumeCancellableWith(intercepted, Result.m6constructorimpl(Unit.INSTANCE));
        } catch (Throwable e$iv) {
            dispatcherFailure(completion, e$iv);
        }
    }

    public static final void startCoroutineCancellable(Continuation<? super Unit> $this$startCoroutineCancellable, Continuation<?> fatalCompletion) {
        try {
            Continuation<? super Unit> intercepted = IntrinsicsKt.intercepted($this$startCoroutineCancellable);
            Result.Companion companion = Result.Companion;
            DispatchedContinuationKt.resumeCancellableWith(intercepted, Result.m6constructorimpl(Unit.INSTANCE));
        } catch (Throwable e$iv) {
            dispatcherFailure(fatalCompletion, e$iv);
        }
    }

    private static final void runSafely(Continuation<?> completion, Function0<Unit> block) {
        try {
            block.invoke();
        } catch (Throwable e) {
            dispatcherFailure(completion, e);
        }
    }

    private static final void dispatcherFailure(Continuation<?> completion, Throwable e) {
        Result.Companion companion = Result.Companion;
        completion.resumeWith(Result.m6constructorimpl(ResultKt.createFailure(e)));
        throw e;
    }
}
