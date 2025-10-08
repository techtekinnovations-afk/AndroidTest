package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.intrinsics.UndispatchedKt;

@Metadata(d1 = {"\u0000J\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0003\n\u0002\b\u0004\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u0006\u0010\u0004\u001a\u00020\u0001\u001aJ\u0010\n\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u000b2'\u0010\f\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000b0\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\r¢\u0006\u0002\b\u0010H@\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u0011\u001a\u000e\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a!\u0010\u0013\u001a\u00020\u0014*\u00020\u00012\u0010\b\u0002\u0010\u0015\u001a\n\u0018\u00010\u0017j\u0004\u0018\u0001`\u0016¢\u0006\u0002\u0010\u0018\u001a\u001e\u0010\u0013\u001a\u00020\u0014*\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u001a2\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u001b\u001a\n\u0010\u001c\u001a\u00020\u0014*\u00020\u0001\u001a\u000e\u0010\u001d\u001a\u00020\u0003HH¢\u0006\u0002\u0010\u001e\"\u001b\u0010\u0005\u001a\u00020\u0006*\u00020\u00018F¢\u0006\f\u0012\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\t¨\u0006\u001f"}, d2 = {"plus", "Lkotlinx/coroutines/CoroutineScope;", "context", "Lkotlin/coroutines/CoroutineContext;", "MainScope", "isActive", "", "isActive$annotations", "(Lkotlinx/coroutines/CoroutineScope;)V", "(Lkotlinx/coroutines/CoroutineScope;)Z", "coroutineScope", "R", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "CoroutineScope", "cancel", "", "cause", "Lkotlinx/coroutines/CancellationException;", "Ljava/util/concurrent/CancellationException;", "(Lkotlinx/coroutines/CoroutineScope;Ljava/util/concurrent/CancellationException;)V", "message", "", "", "ensureActive", "currentCoroutineContext", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: CoroutineScope.kt */
public final class CoroutineScopeKt {
    public static /* synthetic */ void isActive$annotations(CoroutineScope coroutineScope) {
    }

    public static final CoroutineScope plus(CoroutineScope $this$plus, CoroutineContext context) {
        return new ContextScope($this$plus.getCoroutineContext().plus(context));
    }

    public static final CoroutineScope MainScope() {
        return new ContextScope(SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null).plus(Dispatchers.getMain()));
    }

    public static final boolean isActive(CoroutineScope $this$isActive) {
        Job job = (Job) $this$isActive.getCoroutineContext().get(Job.Key);
        if (job != null) {
            return job.isActive();
        }
        return true;
    }

    public static final <R> Object coroutineScope(Function2<? super CoroutineScope, ? super Continuation<? super R>, ? extends Object> block, Continuation<? super R> $completion) {
        Continuation uCont = $completion;
        ScopeCoroutine coroutine = new ScopeCoroutine(uCont.getContext(), uCont);
        Object startUndispatchedOrReturn = UndispatchedKt.startUndispatchedOrReturn(coroutine, coroutine, block);
        if (startUndispatchedOrReturn == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return startUndispatchedOrReturn;
    }

    public static final CoroutineScope CoroutineScope(CoroutineContext context) {
        return new ContextScope(context.get(Job.Key) != null ? context : context.plus(JobKt.Job$default((Job) null, 1, (Object) null)));
    }

    public static /* synthetic */ void cancel$default(CoroutineScope coroutineScope, CancellationException cancellationException, int i, Object obj) {
        if ((i & 1) != 0) {
            cancellationException = null;
        }
        cancel(coroutineScope, cancellationException);
    }

    public static final void cancel(CoroutineScope $this$cancel, CancellationException cause) {
        Job job = (Job) $this$cancel.getCoroutineContext().get(Job.Key);
        if (job != null) {
            job.cancel(cause);
            return;
        }
        throw new IllegalStateException(("Scope cannot be cancelled because it does not have a job: " + $this$cancel).toString());
    }

    public static final void cancel(CoroutineScope $this$cancel, String message, Throwable cause) {
        cancel($this$cancel, ExceptionsKt.CancellationException(message, cause));
    }

    public static /* synthetic */ void cancel$default(CoroutineScope coroutineScope, String str, Throwable th, int i, Object obj) {
        if ((i & 2) != 0) {
            th = null;
        }
        cancel(coroutineScope, str, th);
    }

    public static final void ensureActive(CoroutineScope $this$ensureActive) {
        JobKt.ensureActive($this$ensureActive.getCoroutineContext());
    }

    public static final Object currentCoroutineContext(Continuation<? super CoroutineContext> $completion) {
        return $completion.getContext();
    }

    /* JADX WARNING: type inference failed for: r0v1, types: [java.lang.Throwable, kotlin.coroutines.Continuation] */
    private static final Object currentCoroutineContext$$forInline(Continuation<? super CoroutineContext> $completion) {
        ? r0 = 0;
        r0.getContext();
        throw r0;
    }
}
