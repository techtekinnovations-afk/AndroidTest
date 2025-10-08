package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.DefaultExecutorKt;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.DisposableHandle;

@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002:\u0001/B!\u0012\u0006\u0010\u0003\u001a\u00020\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\b\u0010\tJ\u001a\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J!\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\n\u0010\u0019\u001a\u00060\u000fj\u0002`\u000eH\u0016¢\u0006\u0002\u0010\u001aJ!\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\n\u0010\u0019\u001a\u00060\u000fj\u0002`\u000eH\u0017¢\u0006\u0002\u0010\u001aJ2\u0010\u001c\u001a\u00020\u00162\n\u0010\u0019\u001a\u00060\u000fj\u0002`\u000e2\u0016\u0010\u001d\u001a\u0012\u0012\b\u0012\u00060\u001fR\u00020\u0000\u0012\u0004\u0012\u00020\u00160\u001eH\b¢\u0006\u0002\u0010 J\b\u0010!\u001a\u00020\"H\u0002J\u0015\u0010#\u001a\n\u0018\u00010\u000fj\u0004\u0018\u0001`\u000eH\u0002¢\u0006\u0002\u0010$J\b\u0010%\u001a\u00020\u0007H\u0016J\u0011\u0010&\u001a\u00020\u00162\u0006\u0010'\u001a\u00020(HAJ%\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020(2\n\u0010\u0019\u001a\u00060\u000fj\u0002`\u000e2\u0006\u0010\u0017\u001a\u00020\u0018H\u0001J\u001f\u0010,\u001a\u00020\u00162\u0006\u0010+\u001a\u00020(2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00160.H\u0001R\u000e\u0010\u0003\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0004¢\u0006\u0002\n\u0000R\t\u0010\n\u001a\u00020\u000bX\u0004R\u0018\u0010\f\u001a\f\u0012\b\u0012\u00060\u000fj\u0002`\u000e0\rX\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00060\u0012j\u0002`\u0011X\u0004¢\u0006\u0004\n\u0002\u0010\u0013¨\u00060"}, d2 = {"Lkotlinx/coroutines/internal/LimitedDispatcher;", "Lkotlinx/coroutines/CoroutineDispatcher;", "Lkotlinx/coroutines/Delay;", "dispatcher", "parallelism", "", "name", "", "<init>", "(Lkotlinx/coroutines/CoroutineDispatcher;ILjava/lang/String;)V", "runningWorkers", "Lkotlinx/atomicfu/AtomicInt;", "queue", "Lkotlinx/coroutines/internal/LockFreeTaskQueue;", "Lkotlinx/coroutines/Runnable;", "Ljava/lang/Runnable;", "workerAllocationLock", "Lkotlinx/coroutines/internal/SynchronizedObject;", "", "Ljava/lang/Object;", "limitedParallelism", "dispatch", "", "context", "Lkotlin/coroutines/CoroutineContext;", "block", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Runnable;)V", "dispatchYield", "dispatchInternal", "startWorker", "Lkotlin/Function1;", "Lkotlinx/coroutines/internal/LimitedDispatcher$Worker;", "(Ljava/lang/Runnable;Lkotlin/jvm/functions/Function1;)V", "tryAllocateWorker", "", "obtainTaskOrDeallocateWorker", "()Ljava/lang/Runnable;", "toString", "delay", "time", "", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "Worker", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: LimitedDispatcher.kt */
public final class LimitedDispatcher extends CoroutineDispatcher implements Delay {
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicIntegerFieldUpdater runningWorkers$volatile$FU = AtomicIntegerFieldUpdater.newUpdater(LimitedDispatcher.class, "runningWorkers$volatile");
    private final /* synthetic */ Delay $$delegate_0;
    /* access modifiers changed from: private */
    public final CoroutineDispatcher dispatcher;
    private final String name;
    private final int parallelism;
    private final LockFreeTaskQueue<Runnable> queue;
    private volatile /* synthetic */ int runningWorkers$volatile;
    private final Object workerAllocationLock;

    private final /* synthetic */ int getRunningWorkers$volatile() {
        return this.runningWorkers$volatile;
    }

    private final /* synthetic */ void setRunningWorkers$volatile(int i) {
        this.runningWorkers$volatile = i;
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Deprecated without replacement as an internal method never intended for public use")
    public Object delay(long j, Continuation<? super Unit> continuation) {
        return this.$$delegate_0.delay(j, continuation);
    }

    public DisposableHandle invokeOnTimeout(long j, Runnable runnable, CoroutineContext coroutineContext) {
        return this.$$delegate_0.invokeOnTimeout(j, runnable, coroutineContext);
    }

    public void scheduleResumeAfterDelay(long j, CancellableContinuation<? super Unit> cancellableContinuation) {
        this.$$delegate_0.scheduleResumeAfterDelay(j, cancellableContinuation);
    }

    public LimitedDispatcher(CoroutineDispatcher dispatcher2, int parallelism2, String name2) {
        Delay delay = dispatcher2 instanceof Delay ? (Delay) dispatcher2 : null;
        this.$$delegate_0 = delay == null ? DefaultExecutorKt.getDefaultDelay() : delay;
        this.dispatcher = dispatcher2;
        this.parallelism = parallelism2;
        this.name = name2;
        this.queue = new LockFreeTaskQueue<>(false);
        this.workerAllocationLock = new Object();
    }

    public CoroutineDispatcher limitedParallelism(int parallelism2, String name2) {
        LimitedDispatcherKt.checkParallelism(parallelism2);
        if (parallelism2 >= this.parallelism) {
            return LimitedDispatcherKt.namedOrThis(this, name2);
        }
        return super.limitedParallelism(parallelism2, name2);
    }

    public void dispatch(CoroutineContext context, Runnable block) {
        Runnable task$iv;
        this.queue.addLast(block);
        if (runningWorkers$volatile$FU.get(this) < this.parallelism && tryAllocateWorker() && (task$iv = obtainTaskOrDeallocateWorker()) != null) {
            this.dispatcher.dispatch(this, new Worker(task$iv));
        }
    }

    public void dispatchYield(CoroutineContext context, Runnable block) {
        Runnable task$iv;
        this.queue.addLast(block);
        if (runningWorkers$volatile$FU.get(this) < this.parallelism && tryAllocateWorker() && (task$iv = obtainTaskOrDeallocateWorker()) != null) {
            this.dispatcher.dispatchYield(this, new Worker(task$iv));
        }
    }

    private final void dispatchInternal(Runnable block, Function1<? super Worker, Unit> startWorker) {
        Runnable task;
        this.queue.addLast(block);
        if (runningWorkers$volatile$FU.get(this) < this.parallelism && tryAllocateWorker() && (task = obtainTaskOrDeallocateWorker()) != null) {
            startWorker.invoke(new Worker(task));
        }
    }

    private final boolean tryAllocateWorker() {
        synchronized (this.workerAllocationLock) {
            if (runningWorkers$volatile$FU.get(this) >= this.parallelism) {
                return false;
            }
            runningWorkers$volatile$FU.incrementAndGet(this);
            return true;
        }
    }

    /* access modifiers changed from: private */
    public final Runnable obtainTaskOrDeallocateWorker() {
        while (true) {
            Runnable nextTask = this.queue.removeFirstOrNull();
            if (nextTask != null) {
                return nextTask;
            }
            synchronized (this.workerAllocationLock) {
                runningWorkers$volatile$FU.decrementAndGet(this);
                if (this.queue.getSize() == 0) {
                    return null;
                }
                runningWorkers$volatile$FU.incrementAndGet(this);
            }
        }
    }

    public String toString() {
        String str = this.name;
        return str == null ? this.dispatcher + ".limitedParallelism(" + this.parallelism + ')' : str;
    }

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\b\u0004\u0018\u00002\u00060\u0002j\u0002`\u0001B\u0013\u0012\n\u0010\u0003\u001a\u00060\u0002j\u0002`\u0001¢\u0006\u0004\b\u0004\u0010\u0005J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0014\u0010\u0003\u001a\u00060\u0002j\u0002`\u0001X\u000e¢\u0006\u0004\n\u0002\u0010\u0006¨\u0006\t"}, d2 = {"Lkotlinx/coroutines/internal/LimitedDispatcher$Worker;", "Lkotlinx/coroutines/Runnable;", "Ljava/lang/Runnable;", "currentTask", "<init>", "(Lkotlinx/coroutines/internal/LimitedDispatcher;Ljava/lang/Runnable;)V", "Ljava/lang/Runnable;", "run", "", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: LimitedDispatcher.kt */
    private final class Worker implements Runnable {
        private Runnable currentTask;

        public Worker(Runnable currentTask2) {
            this.currentTask = currentTask2;
        }

        public void run() {
            int fairnessCounter = 0;
            while (true) {
                try {
                    this.currentTask.run();
                } catch (Throwable e) {
                    CoroutineExceptionHandlerKt.handleCoroutineException(EmptyCoroutineContext.INSTANCE, e);
                }
                Runnable access$obtainTaskOrDeallocateWorker = LimitedDispatcher.this.obtainTaskOrDeallocateWorker();
                if (access$obtainTaskOrDeallocateWorker != null) {
                    this.currentTask = access$obtainTaskOrDeallocateWorker;
                    fairnessCounter++;
                    if (fairnessCounter >= 16 && LimitedDispatcher.this.dispatcher.isDispatchNeeded(LimitedDispatcher.this)) {
                        LimitedDispatcher.this.dispatcher.dispatch(LimitedDispatcher.this, this);
                        return;
                    }
                } else {
                    return;
                }
            }
        }
    }
}
