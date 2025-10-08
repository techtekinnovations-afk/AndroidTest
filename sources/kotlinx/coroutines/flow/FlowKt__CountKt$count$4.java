package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: Count.kt */
final class FlowKt__CountKt$count$4<T> implements FlowCollector {
    final /* synthetic */ Ref.IntRef $i;
    final /* synthetic */ Function2<T, Continuation<? super Boolean>, Object> $predicate;

    FlowKt__CountKt$count$4(Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> function2, Ref.IntRef intRef) {
        this.$predicate = function2;
        this.$i = intRef;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object emit(T r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof kotlinx.coroutines.flow.FlowKt__CountKt$count$4$emit$1
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.flow.FlowKt__CountKt$count$4$emit$1 r0 = (kotlinx.coroutines.flow.FlowKt__CountKt$count$4$emit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__CountKt$count$4$emit$1 r0 = new kotlinx.coroutines.flow.FlowKt__CountKt$count$4$emit$1
            r0.<init>(r6, r8)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 1
            switch(r3) {
                case 0: goto L_0x0037;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r0)
            throw r7
        L_0x002d:
            java.lang.Object r7 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__CountKt$count$4 r7 = (kotlinx.coroutines.flow.FlowKt__CountKt$count$4) r7
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r7
            r7 = r1
            goto L_0x0048
        L_0x0037:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r6
            kotlin.jvm.functions.Function2<T, kotlin.coroutines.Continuation<? super java.lang.Boolean>, java.lang.Object> r5 = r3.$predicate
            r0.L$0 = r3
            r0.label = r4
            java.lang.Object r7 = r5.invoke(r7, r0)
            if (r7 != r2) goto L_0x0048
            return r2
        L_0x0048:
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            if (r7 == 0) goto L_0x005d
            kotlin.jvm.internal.Ref$IntRef r7 = r3.$i
            kotlin.jvm.internal.Ref$IntRef r2 = r3.$i
            int r2 = r2.element
            int r2 = r2 + r4
            r7.element = r2
            kotlin.jvm.internal.Ref$IntRef r7 = r3.$i
            int r7 = r7.element
        L_0x005d:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__CountKt$count$4.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
