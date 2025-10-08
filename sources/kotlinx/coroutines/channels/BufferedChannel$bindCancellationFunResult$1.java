package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: BufferedChannel.kt */
/* synthetic */ class BufferedChannel$bindCancellationFunResult$1 extends FunctionReferenceImpl implements Function3<Throwable, ChannelResult<? extends E>, CoroutineContext, Unit> {
    BufferedChannel$bindCancellationFunResult$1(Object obj) {
        super(3, obj, BufferedChannel.class, "onCancellationChannelResultImplDoNotCall", "onCancellationChannelResultImplDoNotCall-5_sEAP8(Ljava/lang/Throwable;Ljava/lang/Object;Lkotlin/coroutines/CoroutineContext;)V", 0);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1, Object p2, Object p3) {
        m1532invoke5_sEAP8((Throwable) p1, ((ChannelResult) p2).m1552unboximpl(), (CoroutineContext) p3);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke-5_sEAP8  reason: not valid java name */
    public final void m1532invoke5_sEAP8(Throwable p0, Object p1, CoroutineContext p2) {
        ((BufferedChannel) this.receiver).m1525onCancellationChannelResultImplDoNotCall5_sEAP8(p0, p1, p2);
    }
}
