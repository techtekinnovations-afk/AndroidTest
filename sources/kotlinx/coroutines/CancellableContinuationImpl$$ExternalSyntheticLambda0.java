package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class CancellableContinuationImpl$$ExternalSyntheticLambda0 implements Function3 {
    public final /* synthetic */ Function1 f$0;

    public /* synthetic */ CancellableContinuationImpl$$ExternalSyntheticLambda0(Function1 function1) {
        this.f$0 = function1;
    }

    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return CancellableContinuationImpl.resume$lambda$13$lambda$12(this.f$0, (Throwable) obj, obj2, (CoroutineContext) obj3);
    }
}
