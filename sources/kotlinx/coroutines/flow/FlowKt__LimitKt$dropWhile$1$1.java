package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: Limit.kt */
final class FlowKt__LimitKt$dropWhile$1$1<T> implements FlowCollector {
    final /* synthetic */ Ref.BooleanRef $matched;
    final /* synthetic */ Function2<T, Continuation<? super Boolean>, Object> $predicate;
    final /* synthetic */ FlowCollector<T> $this_flow;

    FlowKt__LimitKt$dropWhile$1$1(Ref.BooleanRef booleanRef, FlowCollector<? super T> flowCollector, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> function2) {
        this.$matched = booleanRef;
        this.$this_flow = flowCollector;
        this.$predicate = function2;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Incorrect type for immutable var: ssa=T, code=java.lang.Object, for r8v0, types: [T, java.lang.Object] */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation<? super kotlin.Unit> r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1$emit$1
            if (r0 == 0) goto L_0x0014
            r0 = r9
            kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1$emit$1 r0 = (kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1$emit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1$emit$1 r0 = new kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1$emit$1
            r0.<init>(r7, r9)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 1
            switch(r3) {
                case 0: goto L_0x003c;
                case 1: goto L_0x0038;
                case 2: goto L_0x002d;
                case 3: goto L_0x0038;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r0)
            throw r8
        L_0x002d:
            java.lang.Object r8 = r0.L$1
            java.lang.Object r3 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1 r3 = (kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1) r3
            kotlin.ResultKt.throwOnFailure(r1)
            r5 = r1
            goto L_0x0064
        L_0x0038:
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x0051
        L_0x003c:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r7
            kotlin.jvm.internal.Ref$BooleanRef r5 = r3.$matched
            boolean r5 = r5.element
            if (r5 == 0) goto L_0x0054
            kotlinx.coroutines.flow.FlowCollector<T> r5 = r3.$this_flow
            r0.label = r4
            java.lang.Object r8 = r5.emit(r8, r0)
            if (r8 != r2) goto L_0x0051
            return r2
        L_0x0051:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        L_0x0054:
            kotlin.jvm.functions.Function2<T, kotlin.coroutines.Continuation<? super java.lang.Boolean>, java.lang.Object> r5 = r3.$predicate
            r0.L$0 = r3
            r0.L$1 = r8
            r6 = 2
            r0.label = r6
            java.lang.Object r5 = r5.invoke(r8, r0)
            if (r5 != r2) goto L_0x0064
            return r2
        L_0x0064:
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 != 0) goto L_0x0081
            kotlin.jvm.internal.Ref$BooleanRef r5 = r3.$matched
            r5.element = r4
            kotlinx.coroutines.flow.FlowCollector<T> r4 = r3.$this_flow
            r5 = 0
            r0.L$0 = r5
            r0.L$1 = r5
            r5 = 3
            r0.label = r5
            java.lang.Object r8 = r4.emit(r8, r0)
            if (r8 != r2) goto L_0x0051
            return r2
        L_0x0081:
            goto L_0x0051
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$1$1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
