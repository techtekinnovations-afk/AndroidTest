package kotlinx.coroutines.channels;

import kotlin.jvm.functions.Function1;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class BroadcastKt$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ ReceiveChannel f$0;

    public /* synthetic */ BroadcastKt$$ExternalSyntheticLambda0(ReceiveChannel receiveChannel) {
        this.f$0 = receiveChannel;
    }

    public final Object invoke(Object obj) {
        return BroadcastKt.broadcast$lambda$1(this.f$0, (Throwable) obj);
    }
}
