package kotlinx.coroutines.flow;

import kotlin.jvm.functions.Function1;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FlowKt__DelayKt$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ long f$0;

    public /* synthetic */ FlowKt__DelayKt$$ExternalSyntheticLambda1(long j) {
        this.f$0 = j;
    }

    public final Object invoke(Object obj) {
        return Long.valueOf(FlowKt__DelayKt.debounce$lambda$1$FlowKt__DelayKt(this.f$0, obj));
    }
}
