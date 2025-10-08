package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.sync.Semaphore;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: Merge.kt */
final class ChannelFlowMerge$collectTo$2<T> implements FlowCollector {
    final /* synthetic */ SendingCollector<T> $collector;
    final /* synthetic */ Job $job;
    final /* synthetic */ ProducerScope<T> $scope;
    final /* synthetic */ Semaphore $semaphore;

    ChannelFlowMerge$collectTo$2(Job job, Semaphore semaphore, ProducerScope<? super T> producerScope, SendingCollector<T> sendingCollector) {
        this.$job = job;
        this.$semaphore = semaphore;
        this.$scope = producerScope;
        this.$collector = sendingCollector;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=kotlinx.coroutines.flow.Flow<? extends T>, code=kotlinx.coroutines.flow.Flow, for r11v0, types: [kotlinx.coroutines.flow.Flow<? extends T>, java.lang.Object] */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object emit(final kotlinx.coroutines.flow.Flow r11, kotlin.coroutines.Continuation<? super kotlin.Unit> r12) {
        /*
            r10 = this;
            boolean r0 = r12 instanceof kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2$emit$1
            if (r0 == 0) goto L_0x0014
            r0 = r12
            kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2$emit$1 r0 = (kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2$emit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2$emit$1 r0 = new kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2$emit$1
            r0.<init>(r10, r12)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0038;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r0)
            throw r11
        L_0x002c:
            java.lang.Object r11 = r0.L$1
            kotlinx.coroutines.flow.Flow r11 = (kotlinx.coroutines.flow.Flow) r11
            java.lang.Object r2 = r0.L$0
            kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2 r2 = (kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2) r2
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x0054
        L_0x0038:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r10
            kotlinx.coroutines.Job r4 = r3.$job
            if (r4 == 0) goto L_0x0043
            kotlinx.coroutines.JobKt.ensureActive((kotlinx.coroutines.Job) r4)
        L_0x0043:
            kotlinx.coroutines.sync.Semaphore r4 = r3.$semaphore
            r0.L$0 = r3
            r0.L$1 = r11
            r5 = 1
            r0.label = r5
            java.lang.Object r4 = r4.acquire(r0)
            if (r4 != r2) goto L_0x0053
            return r2
        L_0x0053:
            r2 = r3
        L_0x0054:
            kotlinx.coroutines.channels.ProducerScope<T> r3 = r2.$scope
            r4 = r3
            kotlinx.coroutines.CoroutineScope r4 = (kotlinx.coroutines.CoroutineScope) r4
            kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2$1 r3 = new kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2$1
            kotlinx.coroutines.flow.internal.SendingCollector<T> r5 = r2.$collector
            kotlinx.coroutines.sync.Semaphore r6 = r2.$semaphore
            r7 = 0
            r3.<init>(r11, r5, r6, r7)
            r7 = r3
            kotlin.jvm.functions.Function2 r7 = (kotlin.jvm.functions.Function2) r7
            r8 = 3
            r9 = 0
            r5 = 0
            r6 = 0
            kotlinx.coroutines.Job unused = kotlinx.coroutines.BuildersKt__Builders_commonKt.launch$default(r4, r5, r6, r7, r8, r9)
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.ChannelFlowMerge$collectTo$2.emit(kotlinx.coroutines.flow.Flow, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
