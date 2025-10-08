package kotlinx.coroutines.sync;

import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function3;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SemaphoreAndMutexImpl$$ExternalSyntheticLambda0 implements Function3 {
    public final /* synthetic */ SemaphoreAndMutexImpl f$0;

    public /* synthetic */ SemaphoreAndMutexImpl$$ExternalSyntheticLambda0(SemaphoreAndMutexImpl semaphoreAndMutexImpl) {
        this.f$0 = semaphoreAndMutexImpl;
    }

    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return SemaphoreAndMutexImpl.onCancellationRelease$lambda$2(this.f$0, (Throwable) obj, (Unit) obj2, (CoroutineContext) obj3);
    }
}
