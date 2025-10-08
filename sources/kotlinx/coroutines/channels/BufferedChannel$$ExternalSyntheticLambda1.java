package kotlinx.coroutines.channels;

import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.selects.SelectInstance;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class BufferedChannel$$ExternalSyntheticLambda1 implements Function3 {
    public final /* synthetic */ BufferedChannel f$0;

    public /* synthetic */ BufferedChannel$$ExternalSyntheticLambda1(BufferedChannel bufferedChannel) {
        this.f$0 = bufferedChannel;
    }

    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return BufferedChannel.onUndeliveredElementReceiveCancellationConstructor$lambda$57$lambda$56(this.f$0, (SelectInstance) obj, obj2, obj3);
    }
}
