package kotlinx.coroutines.sync;

import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.selects.SelectInstance;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class MutexImpl$$ExternalSyntheticLambda1 implements Function3 {
    public final /* synthetic */ MutexImpl f$0;

    public /* synthetic */ MutexImpl$$ExternalSyntheticLambda1(MutexImpl mutexImpl) {
        this.f$0 = mutexImpl;
    }

    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return MutexImpl.onSelectCancellationUnlockConstructor$lambda$1(this.f$0, (SelectInstance) obj, obj2, obj3);
    }
}
