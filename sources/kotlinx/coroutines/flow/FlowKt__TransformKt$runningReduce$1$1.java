package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: Transform.kt */
final class FlowKt__TransformKt$runningReduce$1$1<T> implements FlowCollector {
    final /* synthetic */ Ref.ObjectRef<Object> $accumulator;
    final /* synthetic */ Function3<T, T, Continuation<? super T>, Object> $operation;
    final /* synthetic */ FlowCollector<T> $this_flow;

    FlowKt__TransformKt$runningReduce$1$1(Ref.ObjectRef<Object> objectRef, Function3<? super T, ? super T, ? super Continuation<? super T>, ? extends Object> function3, FlowCollector<? super T> flowCollector) {
        this.$accumulator = objectRef;
        this.$operation = function3;
        this.$this_flow = flowCollector;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0069, code lost:
        r4.element = r10;
        r10 = r3.$this_flow;
        r4 = r3.$accumulator.element;
        r0.L$0 = null;
        r0.L$1 = null;
        r0.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x007d, code lost:
        if (r10.emit(r4, r0) != r2) goto L_0x0080;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x007f, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0082, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Incorrect type for immutable var: ssa=T, code=java.lang.Object, for r10v0, types: [T, java.lang.Object] */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation<? super kotlin.Unit> r11) {
        /*
            r9 = this;
            boolean r0 = r11 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$runningReduce$1$1$emit$1
            if (r0 == 0) goto L_0x0014
            r0 = r11
            kotlinx.coroutines.flow.FlowKt__TransformKt$runningReduce$1$1$emit$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$runningReduce$1$1$emit$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__TransformKt$runningReduce$1$1$emit$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$runningReduce$1$1$emit$1
            r0.<init>(r9, r11)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x003e;
                case 1: goto L_0x0030;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L_0x002c:
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x0080
        L_0x0030:
            java.lang.Object r10 = r0.L$1
            kotlin.jvm.internal.Ref$ObjectRef r10 = (kotlin.jvm.internal.Ref.ObjectRef) r10
            java.lang.Object r3 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__TransformKt$runningReduce$1$1 r3 = (kotlinx.coroutines.flow.FlowKt__TransformKt$runningReduce$1$1) r3
            kotlin.ResultKt.throwOnFailure(r1)
            r4 = r3
            r3 = r1
            goto L_0x0065
        L_0x003e:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r9
            kotlin.jvm.internal.Ref$ObjectRef<java.lang.Object> r4 = r3.$accumulator
            kotlin.jvm.internal.Ref$ObjectRef<java.lang.Object> r5 = r3.$accumulator
            T r5 = r5.element
            kotlinx.coroutines.internal.Symbol r6 = kotlinx.coroutines.flow.internal.NullSurrogateKt.NULL
            if (r5 != r6) goto L_0x004d
            goto L_0x0069
        L_0x004d:
            kotlin.jvm.functions.Function3<T, T, kotlin.coroutines.Continuation<? super T>, java.lang.Object> r5 = r3.$operation
            kotlin.jvm.internal.Ref$ObjectRef<java.lang.Object> r6 = r3.$accumulator
            T r6 = r6.element
            r0.L$0 = r3
            r0.L$1 = r4
            r7 = 1
            r0.label = r7
            java.lang.Object r10 = r5.invoke(r6, r10, r0)
            if (r10 != r2) goto L_0x0061
            return r2
        L_0x0061:
            r8 = r3
            r3 = r10
            r10 = r4
            r4 = r8
        L_0x0065:
            r8 = r4
            r4 = r10
            r10 = r3
            r3 = r8
        L_0x0069:
            r4.element = r10
            kotlinx.coroutines.flow.FlowCollector<T> r10 = r3.$this_flow
            kotlin.jvm.internal.Ref$ObjectRef<java.lang.Object> r4 = r3.$accumulator
            T r4 = r4.element
            r5 = 0
            r0.L$0 = r5
            r0.L$1 = r5
            r5 = 2
            r0.label = r5
            java.lang.Object r10 = r10.emit(r4, r0)
            if (r10 != r2) goto L_0x0080
            return r2
        L_0x0080:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$runningReduce$1$1.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
