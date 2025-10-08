package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;
import kotlinx.coroutines.selects.SelectBuilder;
import kotlinx.coroutines.selects.SelectImplementation;

@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H\n"}, d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/CoroutineScope;", "downstream", "Lkotlinx/coroutines/flow/FlowCollector;"}, k = 3, mv = {2, 0, 0}, xi = 48)
@DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__DelayKt$sample$2", f = "Delay.kt", i = {0, 0, 0, 0}, l = {412}, m = "invokeSuspend", n = {"downstream", "values", "lastValue", "ticker"}, s = {"L$0", "L$1", "L$2", "L$3"})
/* compiled from: Delay.kt */
final class FlowKt__DelayKt$sample$2 extends SuspendLambda implements Function3<CoroutineScope, FlowCollector<? super T>, Continuation<? super Unit>, Object> {
    final /* synthetic */ long $periodMillis;
    final /* synthetic */ Flow<T> $this_sample;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    Object L$2;
    Object L$3;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__DelayKt$sample$2(long j, Flow<? extends T> flow, Continuation<? super FlowKt__DelayKt$sample$2> continuation) {
        super(3, continuation);
        this.$periodMillis = j;
        this.$this_sample = flow;
    }

    public final Object invoke(CoroutineScope coroutineScope, FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) {
        FlowKt__DelayKt$sample$2 flowKt__DelayKt$sample$2 = new FlowKt__DelayKt$sample$2(this.$periodMillis, this.$this_sample, continuation);
        flowKt__DelayKt$sample$2.L$0 = coroutineScope;
        flowKt__DelayKt$sample$2.L$1 = flowCollector;
        return flowKt__DelayKt$sample$2.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        FlowCollector downstream;
        ReceiveChannel values;
        Ref.ObjectRef lastValue;
        ReceiveChannel values2;
        FlowKt__DelayKt$sample$2 flowKt__DelayKt$sample$2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                flowKt__DelayKt$sample$2 = this;
                CoroutineScope $this$scopedFlow = (CoroutineScope) flowKt__DelayKt$sample$2.L$0;
                ReceiveChannel values3 = ProduceKt.produce$default($this$scopedFlow, (CoroutineContext) null, -1, new FlowKt__DelayKt$sample$2$values$1(flowKt__DelayKt$sample$2.$this_sample, (Continuation<? super FlowKt__DelayKt$sample$2$values$1>) null), 1, (Object) null);
                lastValue = new Ref.ObjectRef();
                values = values3;
                values2 = FlowKt.fixedPeriodTicker($this$scopedFlow, flowKt__DelayKt$sample$2.$periodMillis);
                downstream = (FlowCollector) flowKt__DelayKt$sample$2.L$1;
                break;
            case 1:
                flowKt__DelayKt$sample$2 = this;
                values2 = (ReceiveChannel) flowKt__DelayKt$sample$2.L$3;
                lastValue = (Ref.ObjectRef) flowKt__DelayKt$sample$2.L$2;
                values = (ReceiveChannel) flowKt__DelayKt$sample$2.L$1;
                downstream = (FlowCollector) flowKt__DelayKt$sample$2.L$0;
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        while (lastValue.element != NullSurrogateKt.DONE) {
            SelectImplementation $this$select_u24lambda_u240$iv = new SelectImplementation(flowKt__DelayKt$sample$2.getContext());
            SelectBuilder $this$invokeSuspend_u24lambda_u240 = $this$select_u24lambda_u240$iv;
            $this$invokeSuspend_u24lambda_u240.invoke(values.getOnReceiveCatching(), new FlowKt__DelayKt$sample$2$1$1(lastValue, values2, (Continuation<? super FlowKt__DelayKt$sample$2$1$1>) null));
            $this$invokeSuspend_u24lambda_u240.invoke(values2.getOnReceive(), new FlowKt__DelayKt$sample$2$1$2(lastValue, downstream, (Continuation<? super FlowKt__DelayKt$sample$2$1$2>) null));
            flowKt__DelayKt$sample$2.L$0 = downstream;
            flowKt__DelayKt$sample$2.L$1 = values;
            flowKt__DelayKt$sample$2.L$2 = lastValue;
            flowKt__DelayKt$sample$2.L$3 = values2;
            flowKt__DelayKt$sample$2.label = 1;
            if ($this$select_u24lambda_u240$iv.doSelect(flowKt__DelayKt$sample$2) == coroutine_suspended) {
                return coroutine_suspended;
            }
        }
        return Unit.INSTANCE;
    }
}
