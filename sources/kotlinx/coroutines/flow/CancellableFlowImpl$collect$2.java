package kotlinx.coroutines.flow;

import kotlin.Metadata;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: Context.kt */
final class CancellableFlowImpl$collect$2<T> implements FlowCollector {
    final /* synthetic */ FlowCollector<T> $collector;

    CancellableFlowImpl$collect$2(FlowCollector<? super T> flowCollector) {
        this.$collector = flowCollector;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object emit(T r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof kotlinx.coroutines.flow.CancellableFlowImpl$collect$2$emit$1
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.flow.CancellableFlowImpl$collect$2$emit$1 r0 = (kotlinx.coroutines.flow.CancellableFlowImpl$collect$2$emit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.CancellableFlowImpl$collect$2$emit$1 r0 = new kotlinx.coroutines.flow.CancellableFlowImpl$collect$2$emit$1
            r0.<init>(r6, r8)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0030;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r0)
            throw r7
        L_0x002c:
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x0048
        L_0x0030:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r6
            r4 = 0
            kotlin.coroutines.CoroutineContext r5 = r0.getContext()
            kotlinx.coroutines.JobKt.ensureActive((kotlin.coroutines.CoroutineContext) r5)
            kotlinx.coroutines.flow.FlowCollector<T> r4 = r3.$collector
            r5 = 1
            r0.label = r5
            java.lang.Object r7 = r4.emit(r7, r0)
            if (r7 != r2) goto L_0x0048
            return r2
        L_0x0048:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.CancellableFlowImpl$collect$2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
