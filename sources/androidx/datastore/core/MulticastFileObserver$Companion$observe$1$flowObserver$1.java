package androidx.datastore.core;

import java.io.File;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.channels.ChannelsKt;
import kotlinx.coroutines.channels.ProducerScope;

@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "fileName", "", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* compiled from: MulticastFileObserver.android.kt */
final class MulticastFileObserver$Companion$observe$1$flowObserver$1 extends Lambda implements Function1<String, Unit> {
    final /* synthetic */ ProducerScope<Unit> $$this$channelFlow;
    final /* synthetic */ File $file;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    MulticastFileObserver$Companion$observe$1$flowObserver$1(File file, ProducerScope<? super Unit> producerScope) {
        super(1);
        this.$file = file;
        this.$$this$channelFlow = producerScope;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
        invoke((String) p1);
        return Unit.INSTANCE;
    }

    public final void invoke(String fileName) {
        if (Intrinsics.areEqual((Object) fileName, (Object) this.$file.getName())) {
            ChannelsKt.trySendBlocking(this.$$this$channelFlow, Unit.INSTANCE);
        }
    }
}
