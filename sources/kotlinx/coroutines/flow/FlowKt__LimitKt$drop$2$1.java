package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.internal.Ref;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: Limit.kt */
final class FlowKt__LimitKt$drop$2$1<T> implements FlowCollector {
    final /* synthetic */ int $count;
    final /* synthetic */ Ref.IntRef $skipped;
    final /* synthetic */ FlowCollector<T> $this_flow;

    FlowKt__LimitKt$drop$2$1(Ref.IntRef intRef, int i, FlowCollector<? super T> flowCollector) {
        this.$skipped = intRef;
        this.$count = i;
        this.$this_flow = flowCollector;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object emit(T r8, kotlin.coroutines.Continuation<? super kotlin.Unit> r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof kotlinx.coroutines.flow.FlowKt__LimitKt$drop$2$1$emit$1
            if (r0 == 0) goto L_0x0014
            r0 = r9
            kotlinx.coroutines.flow.FlowKt__LimitKt$drop$2$1$emit$1 r0 = (kotlinx.coroutines.flow.FlowKt__LimitKt$drop$2$1$emit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__LimitKt$drop$2$1$emit$1 r0 = new kotlinx.coroutines.flow.FlowKt__LimitKt$drop$2$1$emit$1
            r0.<init>(r7, r9)
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
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r0)
            throw r8
        L_0x002c:
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x0048
        L_0x0030:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r7
            kotlin.jvm.internal.Ref$IntRef r4 = r3.$skipped
            int r4 = r4.element
            int r5 = r3.$count
            r6 = 1
            if (r4 < r5) goto L_0x004b
            kotlinx.coroutines.flow.FlowCollector<T> r4 = r3.$this_flow
            r0.label = r6
            java.lang.Object r8 = r4.emit(r8, r0)
            if (r8 != r2) goto L_0x0048
            return r2
        L_0x0048:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        L_0x004b:
            kotlin.jvm.internal.Ref$IntRef r8 = r3.$skipped
            kotlin.jvm.internal.Ref$IntRef r2 = r3.$skipped
            int r2 = r2.element
            int r2 = r2 + r6
            r8.element = r2
            kotlin.jvm.internal.Ref$IntRef r8 = r3.$skipped
            int r8 = r8.element
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__LimitKt$drop$2$1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
