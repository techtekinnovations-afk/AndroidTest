package kotlinx.coroutines.flow;

import kotlin.jvm.functions.Function1;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FlowKt__DelayKt$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ Function1 f$0;

    public /* synthetic */ FlowKt__DelayKt$$ExternalSyntheticLambda0(Function1 function1) {
        this.f$0 = function1;
    }

    public final Object invoke(Object obj) {
        return Long.valueOf(FlowKt__DelayKt.debounce$lambda$2$FlowKt__DelayKt(this.f$0, obj));
    }
}
