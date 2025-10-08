package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;

@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H\n"}, d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/CoroutineScope;", "downStream", "Lkotlinx/coroutines/flow/FlowCollector;"}, k = 3, mv = {2, 0, 0}, xi = 48)
@DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__DelayKt$timeoutInternal$1", f = "Delay.kt", i = {0, 0}, l = {413}, m = "invokeSuspend", n = {"downStream", "values"}, s = {"L$0", "L$1"})
/* compiled from: Delay.kt */
final class FlowKt__DelayKt$timeoutInternal$1 extends SuspendLambda implements Function3<CoroutineScope, FlowCollector<? super T>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Flow<T> $this_timeoutInternal;
    final /* synthetic */ long $timeout;
    long J$0;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__DelayKt$timeoutInternal$1(long j, Flow<? extends T> flow, Continuation<? super FlowKt__DelayKt$timeoutInternal$1> continuation) {
        super(3, continuation);
        this.$timeout = j;
        this.$this_timeoutInternal = flow;
    }

    public final Object invoke(CoroutineScope coroutineScope, FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) {
        FlowKt__DelayKt$timeoutInternal$1 flowKt__DelayKt$timeoutInternal$1 = new FlowKt__DelayKt$timeoutInternal$1(this.$timeout, this.$this_timeoutInternal, continuation);
        flowKt__DelayKt$timeoutInternal$1.L$0 = coroutineScope;
        flowKt__DelayKt$timeoutInternal$1.L$1 = flowCollector;
        return flowKt__DelayKt$timeoutInternal$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0094  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r17) {
        /*
            r16 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            r1 = r16
            int r2 = r1.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x002b;
                case 1: goto L_0x0014;
                default: goto L_0x000c;
            }
        L_0x000c:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0014:
            r2 = r16
            r4 = r17
            r5 = 0
            r6 = 0
            r7 = 0
            long r8 = r2.J$0
            java.lang.Object r10 = r2.L$1
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r11 = r2.L$0
            kotlinx.coroutines.flow.FlowCollector r11 = (kotlinx.coroutines.flow.FlowCollector) r11
            kotlin.ResultKt.throwOnFailure(r4)
            r1 = r4
            goto L_0x0098
        L_0x002b:
            kotlin.ResultKt.throwOnFailure(r17)
            r2 = r16
            r4 = r17
            java.lang.Object r5 = r2.L$0
            kotlinx.coroutines.CoroutineScope r5 = (kotlinx.coroutines.CoroutineScope) r5
            java.lang.Object r6 = r2.L$1
            kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
            long r7 = r2.$timeout
            kotlin.time.Duration$Companion r9 = kotlin.time.Duration.Companion
            long r9 = r9.m1441getZEROUwyO8pc()
            int r7 = kotlin.time.Duration.m1337compareToLRDsOJo(r7, r9)
            if (r7 <= 0) goto L_0x00aa
            kotlinx.coroutines.flow.Flow<T> r7 = r2.$this_timeoutInternal
            r8 = 0
            r9 = 2
            kotlinx.coroutines.flow.Flow r7 = kotlinx.coroutines.flow.FlowKt__ContextKt.buffer$default(r7, r8, r3, r9, r3)
            kotlinx.coroutines.channels.ReceiveChannel r5 = kotlinx.coroutines.flow.FlowKt.produceIn(r7, r5)
            long r7 = r2.$timeout
            r9 = 0
            r10 = r5
            r11 = r6
            r5 = r9
            r8 = r7
        L_0x005b:
            r6 = 0
            kotlinx.coroutines.selects.SelectImplementation r7 = new kotlinx.coroutines.selects.SelectImplementation
            kotlin.coroutines.CoroutineContext r12 = r2.getContext()
            r7.<init>(r12)
            r12 = 0
            r13 = r7
            kotlinx.coroutines.selects.SelectBuilder r13 = (kotlinx.coroutines.selects.SelectBuilder) r13
            r14 = 0
            kotlinx.coroutines.selects.SelectClause1 r15 = r10.getOnReceiveCatching()
            kotlinx.coroutines.flow.FlowKt__DelayKt$timeoutInternal$1$1$1 r1 = new kotlinx.coroutines.flow.FlowKt__DelayKt$timeoutInternal$1$1$1
            r1.<init>(r11, r3)
            kotlin.jvm.functions.Function2 r1 = (kotlin.jvm.functions.Function2) r1
            r13.invoke(r15, r1)
            kotlinx.coroutines.flow.FlowKt__DelayKt$timeoutInternal$1$1$2 r1 = new kotlinx.coroutines.flow.FlowKt__DelayKt$timeoutInternal$1$1$2
            r1.<init>(r8, r3)
            kotlin.jvm.functions.Function1 r1 = (kotlin.jvm.functions.Function1) r1
            kotlinx.coroutines.selects.OnTimeoutKt.m1608onTimeout8Mi8wO0(r13, r8, r1)
            r2.L$0 = r11
            r2.L$1 = r10
            r2.J$0 = r8
            r1 = 1
            r2.label = r1
            java.lang.Object r1 = r7.doSelect(r2)
            if (r1 != r0) goto L_0x0094
            return r0
        L_0x0094:
            r7 = r4
            r4 = r1
            r1 = r7
            r7 = r12
        L_0x0098:
            java.lang.Boolean r4 = (java.lang.Boolean) r4
            boolean r4 = r4.booleanValue()
            if (r4 != 0) goto L_0x00a6
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x00a6:
            r4 = r1
            r1 = r16
            goto L_0x005b
        L_0x00aa:
            kotlinx.coroutines.TimeoutCancellationException r0 = new kotlinx.coroutines.TimeoutCancellationException
            java.lang.String r1 = "Timed out immediately"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__DelayKt$timeoutInternal$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
