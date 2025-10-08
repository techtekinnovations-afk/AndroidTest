package kotlinx.coroutines.channels;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.selects.SelectInstance;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class BufferedChannel$$ExternalSyntheticLambda0 implements Function3 {
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ BufferedChannel f$1;
    public final /* synthetic */ SelectInstance f$2;

    public /* synthetic */ BufferedChannel$$ExternalSyntheticLambda0(Object obj, BufferedChannel bufferedChannel, SelectInstance selectInstance) {
        this.f$0 = obj;
        this.f$1 = bufferedChannel;
        this.f$2 = selectInstance;
    }

    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return BufferedChannel.onUndeliveredElementReceiveCancellationConstructor$lambda$57$lambda$56$lambda$55(this.f$0, this.f$1, this.f$2, (Throwable) obj, obj2, (CoroutineContext) obj3);
    }
}
