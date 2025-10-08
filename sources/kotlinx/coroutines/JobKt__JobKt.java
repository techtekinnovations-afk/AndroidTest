package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.sequences.Sequence;
import kotlinx.coroutines.Job;

@Metadata(d1 = {"\u0000H\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0006\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0000\u001a\u0012\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0002\u001a\u0019\u0010\n\u001a\u00020\u00022\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0002H\u0007¢\u0006\u0002\b\u0007\u001a\u0014\u0010\u000b\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\f\u001a\u00020\u0001H\u0000\u001a\u0012\u0010\r\u001a\u00020\u000e*\u00020\u0002H@¢\u0006\u0002\u0010\u000f\u001a!\u0010\u0010\u001a\u00020\u000e*\u00020\u00022\u0010\b\u0002\u0010\u0011\u001a\n\u0018\u00010\u0013j\u0004\u0018\u0001`\u0012¢\u0006\u0002\u0010\u0014\u001a\f\u0010\u0010\u001a\u00020\u000e*\u00020\u0002H\u0007\u001a\u0018\u0010\u0010\u001a\u00020\u000e*\u00020\u00022\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0015H\u0007\u001a!\u0010\u0019\u001a\u00020\u000e*\u00020\u00172\u0010\b\u0002\u0010\u0011\u001a\n\u0018\u00010\u0013j\u0004\u0018\u0001`\u0012¢\u0006\u0002\u0010\u001a\u001a\f\u0010\u0019\u001a\u00020\u000e*\u00020\u0017H\u0007\u001a\n\u0010\u001b\u001a\u00020\u000e*\u00020\u0002\u001a\n\u0010\u001b\u001a\u00020\u000e*\u00020\u0017\u001a\u001e\u0010\u0019\u001a\u00020\u000e*\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u001d2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0015\u001a\u0018\u0010\u0019\u001a\u00020\u0004*\u00020\u00172\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0015H\u0007\u001a!\u0010\u0010\u001a\u00020\u000e*\u00020\u00172\u0010\b\u0002\u0010\u0011\u001a\n\u0018\u00010\u0013j\u0004\u0018\u0001`\u0012¢\u0006\u0002\u0010\u001a\u001a\f\u0010\u0010\u001a\u00020\u000e*\u00020\u0017H\u0007\u001a\u0018\u0010\u0010\u001a\u00020\u000e*\u00020\u00172\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0015H\u0007\u001a\u001b\u0010!\u001a\u00020\u0015*\u0004\u0018\u00010\u00152\u0006\u0010\u001e\u001a\u00020\u0002H\u0002¢\u0006\u0002\b\"\"\u0015\u0010\u0016\u001a\u00020\u0004*\u00020\u00178F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0018\"\u0015\u0010\u001e\u001a\u00020\u0002*\u00020\u00178F¢\u0006\u0006\u001a\u0004\b\u001f\u0010 ¨\u0006#"}, d2 = {"invokeOnCompletion", "Lkotlinx/coroutines/DisposableHandle;", "Lkotlinx/coroutines/Job;", "invokeImmediately", "", "handler", "Lkotlinx/coroutines/JobNode;", "Job", "Lkotlinx/coroutines/CompletableJob;", "parent", "Job0", "disposeOnCompletion", "handle", "cancelAndJoin", "", "(Lkotlinx/coroutines/Job;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cancelChildren", "cause", "Lkotlinx/coroutines/CancellationException;", "Ljava/util/concurrent/CancellationException;", "(Lkotlinx/coroutines/Job;Ljava/util/concurrent/CancellationException;)V", "", "isActive", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/CoroutineContext;)Z", "cancel", "(Lkotlin/coroutines/CoroutineContext;Ljava/util/concurrent/CancellationException;)V", "ensureActive", "message", "", "job", "getJob", "(Lkotlin/coroutines/CoroutineContext;)Lkotlinx/coroutines/Job;", "orCancellation", "orCancellation$JobKt__JobKt", "kotlinx-coroutines-core"}, k = 5, mv = {2, 0, 0}, xi = 48, xs = "kotlinx/coroutines/JobKt")
/* compiled from: Job.kt */
final /* synthetic */ class JobKt__JobKt {
    public static /* synthetic */ DisposableHandle invokeOnCompletion$default(Job job, boolean z, JobNode jobNode, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return JobKt.invokeOnCompletion(job, z, jobNode);
    }

    public static final DisposableHandle invokeOnCompletion(Job $this$invokeOnCompletion, boolean invokeImmediately, JobNode handler) {
        if ($this$invokeOnCompletion instanceof JobSupport) {
            return ((JobSupport) $this$invokeOnCompletion).invokeOnCompletionInternal$kotlinx_coroutines_core(invokeImmediately, handler);
        }
        return $this$invokeOnCompletion.invokeOnCompletion(handler.getOnCancelling(), invokeImmediately, new JobKt__JobKt$invokeOnCompletion$1(handler));
    }

    public static final CompletableJob Job(Job parent) {
        return new JobImpl(parent);
    }

    public static final DisposableHandle disposeOnCompletion(Job $this$disposeOnCompletion, DisposableHandle handle) {
        return invokeOnCompletion$default($this$disposeOnCompletion, false, new DisposeOnCompletion(handle), 1, (Object) null);
    }

    public static final Object cancelAndJoin(Job $this$cancelAndJoin, Continuation<? super Unit> $completion) {
        Job.DefaultImpls.cancel$default($this$cancelAndJoin, (CancellationException) null, 1, (Object) null);
        Object join = $this$cancelAndJoin.join($completion);
        return join == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? join : Unit.INSTANCE;
    }

    public static /* synthetic */ void cancelChildren$default(Job job, CancellationException cancellationException, int i, Object obj) {
        if ((i & 1) != 0) {
            cancellationException = null;
        }
        JobKt.cancelChildren(job, cancellationException);
    }

    public static final void cancelChildren(Job $this$cancelChildren, CancellationException cause) {
        for (Job it : $this$cancelChildren.getChildren()) {
            it.cancel(cause);
        }
    }

    public static /* synthetic */ void cancelChildren$default(Job job, Throwable th, int i, Object obj) {
        if ((i & 1) != 0) {
            th = null;
        }
        cancelChildren(job, th);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public static final /* synthetic */ void cancelChildren(Job $this$cancelChildren, Throwable cause) {
        for (Job it : $this$cancelChildren.getChildren()) {
            JobSupport jobSupport = it instanceof JobSupport ? (JobSupport) it : null;
            if (jobSupport != null) {
                jobSupport.cancelInternal(orCancellation$JobKt__JobKt(cause, $this$cancelChildren));
            }
        }
    }

    public static final boolean isActive(CoroutineContext $this$isActive) {
        Job job = (Job) $this$isActive.get(Job.Key);
        if (job != null) {
            return job.isActive();
        }
        return true;
    }

    public static /* synthetic */ void cancel$default(CoroutineContext coroutineContext, CancellationException cancellationException, int i, Object obj) {
        if ((i & 1) != 0) {
            cancellationException = null;
        }
        JobKt.cancel(coroutineContext, cancellationException);
    }

    public static final void cancel(CoroutineContext $this$cancel, CancellationException cause) {
        Job job = (Job) $this$cancel.get(Job.Key);
        if (job != null) {
            job.cancel(cause);
        }
    }

    public static final void ensureActive(Job $this$ensureActive) {
        if (!$this$ensureActive.isActive()) {
            throw $this$ensureActive.getCancellationException();
        }
    }

    public static final void ensureActive(CoroutineContext $this$ensureActive) {
        Job job = (Job) $this$ensureActive.get(Job.Key);
        if (job != null) {
            JobKt.ensureActive(job);
        }
    }

    public static final void cancel(Job $this$cancel, String message, Throwable cause) {
        $this$cancel.cancel(ExceptionsKt.CancellationException(message, cause));
    }

    public static /* synthetic */ void cancel$default(Job job, String str, Throwable th, int i, Object obj) {
        if ((i & 2) != 0) {
            th = null;
        }
        JobKt.cancel(job, str, th);
    }

    public static /* synthetic */ boolean cancel$default(CoroutineContext coroutineContext, Throwable th, int i, Object obj) {
        if ((i & 1) != 0) {
            th = null;
        }
        return cancel(coroutineContext, th);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public static final /* synthetic */ boolean cancel(CoroutineContext $this$cancel, Throwable cause) {
        CoroutineContext.Element element = $this$cancel.get(Job.Key);
        JobSupport job = element instanceof JobSupport ? (JobSupport) element : null;
        if (job == null) {
            return false;
        }
        job.cancelInternal(orCancellation$JobKt__JobKt(cause, job));
        return true;
    }

    public static /* synthetic */ void cancelChildren$default(CoroutineContext coroutineContext, CancellationException cancellationException, int i, Object obj) {
        if ((i & 1) != 0) {
            cancellationException = null;
        }
        JobKt.cancelChildren(coroutineContext, cancellationException);
    }

    public static final void cancelChildren(CoroutineContext $this$cancelChildren, CancellationException cause) {
        Sequence<Job> $this$forEach$iv;
        Job job = (Job) $this$cancelChildren.get(Job.Key);
        if (job != null && ($this$forEach$iv = job.getChildren()) != null) {
            for (Job it : $this$forEach$iv) {
                it.cancel(cause);
            }
        }
    }

    public static final Job getJob(CoroutineContext $this$job) {
        Job job = (Job) $this$job.get(Job.Key);
        if (job != null) {
            return job;
        }
        throw new IllegalStateException(("Current context doesn't contain Job in it: " + $this$job).toString());
    }

    public static /* synthetic */ void cancelChildren$default(CoroutineContext coroutineContext, Throwable th, int i, Object obj) {
        if ((i & 1) != 0) {
            th = null;
        }
        cancelChildren(coroutineContext, th);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public static final /* synthetic */ void cancelChildren(CoroutineContext $this$cancelChildren, Throwable cause) {
        Job job = (Job) $this$cancelChildren.get(Job.Key);
        if (job != null) {
            for (Job it : job.getChildren()) {
                JobSupport jobSupport = it instanceof JobSupport ? (JobSupport) it : null;
                if (jobSupport != null) {
                    jobSupport.cancelInternal(orCancellation$JobKt__JobKt(cause, job));
                }
            }
        }
    }

    private static final Throwable orCancellation$JobKt__JobKt(Throwable $this$orCancellation, Job job) {
        return $this$orCancellation == null ? new JobCancellationException("Job was cancelled", (Throwable) null, job) : $this$orCancellation;
    }
}
