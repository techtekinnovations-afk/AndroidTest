package kotlinx.coroutines.sync;

import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.sync.MutexImpl;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class MutexImpl$CancellableContinuationWithOwner$$ExternalSyntheticLambda0 implements Function3 {
    public final /* synthetic */ MutexImpl f$0;
    public final /* synthetic */ MutexImpl.CancellableContinuationWithOwner f$1;

    public /* synthetic */ MutexImpl$CancellableContinuationWithOwner$$ExternalSyntheticLambda0(MutexImpl mutexImpl, MutexImpl.CancellableContinuationWithOwner cancellableContinuationWithOwner) {
        this.f$0 = mutexImpl;
        this.f$1 = cancellableContinuationWithOwner;
    }

    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return MutexImpl.CancellableContinuationWithOwner.tryResume$lambda$3(this.f$0, this.f$1, (Throwable) obj, (Unit) obj2, (CoroutineContext) obj3);
    }
}
