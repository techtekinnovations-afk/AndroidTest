package kotlinx.coroutines.internal;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.DefaultExecutorKt;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.DisposableHandle;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J!\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000b2\n\u0010\u000e\u001a\u00060\u0010j\u0002`\u000fH\u0016¢\u0006\u0002\u0010\u0011J!\u0010\u0012\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000b2\n\u0010\u000e\u001a\u00060\u0010j\u0002`\u000fH\u0017¢\u0006\u0002\u0010\u0011J\b\u0010\u0013\u001a\u00020\u0005H\u0016J\u0011\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u0016HAJ%\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00162\n\u0010\u000e\u001a\u00060\u0010j\u0002`\u000f2\u0006\u0010\n\u001a\u00020\u000bH\u0001J\u001f\u0010\u001a\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u00162\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\r0\u001cH\u0001R\u000e\u0010\u0003\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lkotlinx/coroutines/internal/NamedDispatcher;", "Lkotlinx/coroutines/CoroutineDispatcher;", "Lkotlinx/coroutines/Delay;", "dispatcher", "name", "", "<init>", "(Lkotlinx/coroutines/CoroutineDispatcher;Ljava/lang/String;)V", "isDispatchNeeded", "", "context", "Lkotlin/coroutines/CoroutineContext;", "dispatch", "", "block", "Lkotlinx/coroutines/Runnable;", "Ljava/lang/Runnable;", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Runnable;)V", "dispatchYield", "toString", "delay", "time", "", "invokeOnTimeout", "Lkotlinx/coroutines/DisposableHandle;", "timeMillis", "scheduleResumeAfterDelay", "continuation", "Lkotlinx/coroutines/CancellableContinuation;", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: NamedDispatcher.kt */
public final class NamedDispatcher extends CoroutineDispatcher implements Delay {
    private final /* synthetic */ Delay $$delegate_0;
    private final CoroutineDispatcher dispatcher;
    private final String name;

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

    public NamedDispatcher(CoroutineDispatcher dispatcher2, String name2) {
        Delay delay = dispatcher2 instanceof Delay ? (Delay) dispatcher2 : null;
        this.$$delegate_0 = delay == null ? DefaultExecutorKt.getDefaultDelay() : delay;
        this.dispatcher = dispatcher2;
        this.name = name2;
    }

    public boolean isDispatchNeeded(CoroutineContext context) {
        return this.dispatcher.isDispatchNeeded(context);
    }

    public void dispatch(CoroutineContext context, Runnable block) {
        this.dispatcher.dispatch(context, block);
    }

    public void dispatchYield(CoroutineContext context, Runnable block) {
        this.dispatcher.dispatchYield(context, block);
    }

    public String toString() {
        return this.name;
    }
}
