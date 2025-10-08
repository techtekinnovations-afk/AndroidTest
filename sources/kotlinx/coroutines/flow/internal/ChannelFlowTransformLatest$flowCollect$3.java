package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 0, 0}, xi = 48)
@DebugMetadata(c = "kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3", f = "Merge.kt", i = {}, l = {23}, m = "invokeSuspend", n = {}, s = {})
/* compiled from: Merge.kt */
final class ChannelFlowTransformLatest$flowCollect$3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ FlowCollector<R> $collector;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ChannelFlowTransformLatest<T, R> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelFlowTransformLatest$flowCollect$3(ChannelFlowTransformLatest<T, R> channelFlowTransformLatest, FlowCollector<? super R> flowCollector, Continuation<? super ChannelFlowTransformLatest$flowCollect$3> continuation) {
        super(2, continuation);
        this.this$0 = channelFlowTransformLatest;
        this.$collector = flowCollector;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChannelFlowTransformLatest$flowCollect$3 channelFlowTransformLatest$flowCollect$3 = new ChannelFlowTransformLatest$flowCollect$3(this.this$0, this.$collector, continuation);
        channelFlowTransformLatest$flowCollect$3.L$0 = obj;
        return channelFlowTransformLatest$flowCollect$3;
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((ChannelFlowTransformLatest$flowCollect$3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                final CoroutineScope $this$coroutineScope = (CoroutineScope) this.L$0;
                final Ref.ObjectRef previousFlow = new Ref.ObjectRef();
                Flow flow = this.this$0.flow;
                final ChannelFlowTransformLatest<T, R> channelFlowTransformLatest = this.this$0;
                final FlowCollector<R> flowCollector = this.$collector;
                this.label = 1;
                if (flow.collect(new FlowCollector() {
                    /* JADX WARNING: Can't fix incorrect switch cases order */
                    /* JADX WARNING: Incorrect type for immutable var: ssa=T, code=java.lang.Object, for r11v0, types: [T, java.lang.Object] */
                    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
                    /* JADX WARNING: Removed duplicated region for block: B:11:0x003b  */
                    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public final java.lang.Object emit(final java.lang.Object r11, kotlin.coroutines.Continuation<? super kotlin.Unit> r12) {
                        /*
                            r10 = this;
                            boolean r0 = r12 instanceof kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1$emit$1
                            if (r0 == 0) goto L_0x0014
                            r0 = r12
                            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1$emit$1 r0 = (kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1$emit$1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r1 = r1 & r2
                            if (r1 == 0) goto L_0x0014
                            int r1 = r0.label
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L_0x0019
                        L_0x0014:
                            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1$emit$1 r0 = new kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1$emit$1
                            r0.<init>(r10, r12)
                        L_0x0019:
                            java.lang.Object r1 = r0.result
                            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                            int r3 = r0.label
                            switch(r3) {
                                case 0: goto L_0x003b;
                                case 1: goto L_0x002c;
                                default: goto L_0x0024;
                            }
                        L_0x0024:
                            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                            r11.<init>(r0)
                            throw r11
                        L_0x002c:
                            r11 = 0
                            java.lang.Object r2 = r0.L$2
                            kotlinx.coroutines.Job r2 = (kotlinx.coroutines.Job) r2
                            java.lang.Object r2 = r0.L$1
                            java.lang.Object r3 = r0.L$0
                            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1 r3 = (kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3.AnonymousClass1) r3
                            kotlin.ResultKt.throwOnFailure(r1)
                            goto L_0x0065
                        L_0x003b:
                            kotlin.ResultKt.throwOnFailure(r1)
                            r3 = r10
                            kotlin.jvm.internal.Ref$ObjectRef<kotlinx.coroutines.Job> r4 = r3
                            T r4 = r4.element
                            kotlinx.coroutines.Job r4 = (kotlinx.coroutines.Job) r4
                            if (r4 == 0) goto L_0x0067
                            r5 = r4
                            r6 = 0
                            kotlinx.coroutines.flow.internal.ChildCancelledException r7 = new kotlinx.coroutines.flow.internal.ChildCancelledException
                            r7.<init>()
                            java.util.concurrent.CancellationException r7 = (java.util.concurrent.CancellationException) r7
                            r5.cancel((java.util.concurrent.CancellationException) r7)
                            r0.L$0 = r3
                            r0.L$1 = r11
                            r0.L$2 = r4
                            r4 = 1
                            r0.label = r4
                            java.lang.Object r4 = r5.join(r0)
                            if (r4 != r2) goto L_0x0063
                            return r2
                        L_0x0063:
                            r2 = r11
                            r11 = r6
                        L_0x0065:
                            r11 = r2
                        L_0x0067:
                            kotlin.jvm.internal.Ref$ObjectRef<kotlinx.coroutines.Job> r2 = r3
                            kotlinx.coroutines.CoroutineScope r4 = r2
                            kotlinx.coroutines.CoroutineStart r6 = kotlinx.coroutines.CoroutineStart.UNDISPATCHED
                            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1$2 r5 = new kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3$1$2
                            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest<T, R> r7 = r6
                            kotlinx.coroutines.flow.FlowCollector<R> r8 = r7
                            r9 = 0
                            r5.<init>(r7, r8, r11, r9)
                            r7 = r5
                            kotlin.jvm.functions.Function2 r7 = (kotlin.jvm.functions.Function2) r7
                            r8 = 1
                            r5 = 0
                            kotlinx.coroutines.Job r4 = kotlinx.coroutines.BuildersKt__Builders_commonKt.launch$default(r4, r5, r6, r7, r8, r9)
                            r2.element = r4
                            kotlin.Unit r2 = kotlin.Unit.INSTANCE
                            return r2
                        */
                        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest$flowCollect$3.AnonymousClass1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }, this) != coroutine_suspended) {
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
