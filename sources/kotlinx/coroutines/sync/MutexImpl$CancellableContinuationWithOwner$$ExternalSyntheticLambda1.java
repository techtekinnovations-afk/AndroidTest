package kotlinx.coroutines.sync;

import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.sync.MutexImpl;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class MutexImpl$CancellableContinuationWithOwner$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ MutexImpl f$0;
    public final /* synthetic */ MutexImpl.CancellableContinuationWithOwner f$1;

    public /* synthetic */ MutexImpl$CancellableContinuationWithOwner$$ExternalSyntheticLambda1(MutexImpl mutexImpl, MutexImpl.CancellableContinuationWithOwner cancellableContinuationWithOwner) {
        this.f$0 = mutexImpl;
        this.f$1 = cancellableContinuationWithOwner;
    }

    public final Object invoke(Object obj) {
        return MutexImpl.CancellableContinuationWithOwner.resume$lambda$6(this.f$0, this.f$1, (Throwable) obj);
    }
}
