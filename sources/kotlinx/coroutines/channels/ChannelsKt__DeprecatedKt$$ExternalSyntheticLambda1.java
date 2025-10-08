package kotlinx.coroutines.channels;

import kotlin.jvm.functions.Function1;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class ChannelsKt__DeprecatedKt$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ ReceiveChannel[] f$0;

    public /* synthetic */ ChannelsKt__DeprecatedKt$$ExternalSyntheticLambda1(ReceiveChannel[] receiveChannelArr) {
        this.f$0 = receiveChannelArr;
    }

    public final Object invoke(Object obj) {
        return ChannelsKt__DeprecatedKt.consumesAll$lambda$2$ChannelsKt__DeprecatedKt(this.f$0, (Throwable) obj);
    }
}
