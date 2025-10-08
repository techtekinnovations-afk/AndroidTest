package kotlinx.coroutines.sync;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function3;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class MutexImpl$$ExternalSyntheticLambda0 implements Function3 {
    public final /* synthetic */ MutexImpl f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ MutexImpl$$ExternalSyntheticLambda0(MutexImpl mutexImpl, Object obj) {
        this.f$0 = mutexImpl;
        this.f$1 = obj;
    }

    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return MutexImpl.onSelectCancellationUnlockConstructor$lambda$1$lambda$0(this.f$0, this.f$1, (Throwable) obj, obj2, (CoroutineContext) obj3);
    }
}
