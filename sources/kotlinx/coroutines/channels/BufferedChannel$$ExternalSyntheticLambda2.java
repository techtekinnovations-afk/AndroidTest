package kotlinx.coroutines.channels;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class BufferedChannel$$ExternalSyntheticLambda2 implements Function3 {
    public final /* synthetic */ Function1 f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BufferedChannel$$ExternalSyntheticLambda2(Function1 function1, Object obj) {
        this.f$0 = function1;
        this.f$1 = obj;
    }

    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return BufferedChannel.bindCancellationFun$lambda$89(this.f$0, this.f$1, (Throwable) obj, obj2, (CoroutineContext) obj3);
    }
}
