package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.internal.AbortFlowException;
import kotlinx.coroutines.flow.internal.FlowExceptions_commonKt;

@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a$\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004\u001aE\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\"\u0010\u0006\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0007¢\u0006\u0002\u0010\u000b\u001a$\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004\u001a0\u0010\r\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000f2\u0006\u0010\u0010\u001a\u0002H\u00022\u0006\u0010\u0011\u001a\u00020\nH@¢\u0006\u0004\b\u0012\u0010\u0013\u001aE\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\"\u0010\u0006\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0007¢\u0006\u0002\u0010\u000b\u001am\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0016*\b\u0012\u0004\u0012\u0002H\u00020\u00012D\b\u0001\u0010\u0017\u001a>\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00160\u000f\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0019\u0012\b\b\u001a\u0012\u0004\b\b(\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0018¢\u0006\u0002\b\u001b¢\u0006\u0002\u0010\u001c\u001aS\u0010\u001d\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000123\b\u0004\u0010\u0006\u001a-\b\u0001\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0019\u0012\b\b\u001a\u0012\u0004\b\b(\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0007HH¢\u0006\u0002\u0010\u001e¨\u0006\u001f"}, d2 = {"drop", "Lkotlinx/coroutines/flow/Flow;", "T", "count", "", "dropWhile", "predicate", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/flow/Flow;", "take", "emitAbort", "", "Lkotlinx/coroutines/flow/FlowCollector;", "value", "ownershipMarker", "emitAbort$FlowKt__LimitKt", "(Lkotlinx/coroutines/flow/FlowCollector;Ljava/lang/Object;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "takeWhile", "transformWhile", "R", "transform", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "collectWhile", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 5, mv = {2, 0, 0}, xi = 48, xs = "kotlinx/coroutines/flow/FlowKt")
/* compiled from: Limit.kt */
final /* synthetic */ class FlowKt__LimitKt {
    public static final <T> Flow<T> drop(Flow<? extends T> $this$drop, int count) {
        if (count >= 0) {
            return new FlowKt__LimitKt$drop$$inlined$unsafeFlow$1($this$drop, count);
        }
        throw new IllegalArgumentException(("Drop count should be non-negative, but had " + count).toString());
    }

    public static final <T> Flow<T> dropWhile(Flow<? extends T> $this$dropWhile, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        return new FlowKt__LimitKt$dropWhile$$inlined$unsafeFlow$1($this$dropWhile, predicate);
    }

    public static final <T> Flow<T> take(Flow<? extends T> $this$take, int count) {
        if (count > 0) {
            return new FlowKt__LimitKt$take$$inlined$unsafeFlow$1($this$take, count);
        }
        throw new IllegalArgumentException(("Requested element count " + count + " should be positive").toString());
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.lang.Object emitAbort$FlowKt__LimitKt(kotlinx.coroutines.flow.FlowCollector<? super T> r4, T r5, java.lang.Object r6, kotlin.coroutines.Continuation<? super kotlin.Unit> r7) {
        /*
            boolean r0 = r7 instanceof kotlinx.coroutines.flow.FlowKt__LimitKt$emitAbort$1
            if (r0 == 0) goto L_0x0014
            r0 = r7
            kotlinx.coroutines.flow.FlowKt__LimitKt$emitAbort$1 r0 = (kotlinx.coroutines.flow.FlowKt__LimitKt$emitAbort$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__LimitKt$emitAbort$1 r0 = new kotlinx.coroutines.flow.FlowKt__LimitKt$emitAbort$1
            r0.<init>(r7)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0032;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L_0x002c:
            java.lang.Object r4 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x0042
        L_0x0032:
            kotlin.ResultKt.throwOnFailure(r1)
            r0.L$0 = r6
            r3 = 1
            r0.label = r3
            java.lang.Object r4 = r4.emit(r5, r0)
            if (r4 != r2) goto L_0x0041
            return r2
        L_0x0041:
            r4 = r6
        L_0x0042:
            kotlinx.coroutines.flow.internal.AbortFlowException r5 = new kotlinx.coroutines.flow.internal.AbortFlowException
            r5.<init>(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__LimitKt.emitAbort$FlowKt__LimitKt(kotlinx.coroutines.flow.FlowCollector, java.lang.Object, java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final <T> Flow<T> takeWhile(Flow<? extends T> $this$takeWhile, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        return new FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1($this$takeWhile, predicate);
    }

    public static final <T, R> Flow<R> transformWhile(Flow<? extends T> $this$transformWhile, Function3<? super FlowCollector<? super R>, ? super T, ? super Continuation<? super Boolean>, ? extends Object> transform) {
        return FlowKt.flow(new FlowKt__LimitKt$transformWhile$1($this$transformWhile, transform, (Continuation<? super FlowKt__LimitKt$transformWhile$1>) null));
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.lang.Object collectWhile(kotlinx.coroutines.flow.Flow<? extends T> r6, kotlin.jvm.functions.Function2<? super T, ? super kotlin.coroutines.Continuation<? super java.lang.Boolean>, ? extends java.lang.Object> r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.flow.FlowKt__LimitKt$collectWhile$1
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.flow.FlowKt__LimitKt$collectWhile$1 r0 = (kotlinx.coroutines.flow.FlowKt__LimitKt$collectWhile$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__LimitKt$collectWhile$1 r0 = new kotlinx.coroutines.flow.FlowKt__LimitKt$collectWhile$1
            r0.<init>(r8)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0037;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L_0x002c:
            r6 = 0
            java.lang.Object r7 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__LimitKt$collectWhile$collector$1 r7 = (kotlinx.coroutines.flow.FlowKt__LimitKt$collectWhile$collector$1) r7
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ AbortFlowException -> 0x0035 }
            goto L_0x0052
        L_0x0035:
            r2 = move-exception
            goto L_0x0056
        L_0x0037:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = 0
            kotlinx.coroutines.flow.FlowKt__LimitKt$collectWhile$collector$1 r4 = new kotlinx.coroutines.flow.FlowKt__LimitKt$collectWhile$collector$1
            r4.<init>(r7)
            r7 = r4
            kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7     // Catch:{ AbortFlowException -> 0x0053 }
            r0.L$0 = r4     // Catch:{ AbortFlowException -> 0x0053 }
            r5 = 1
            r0.label = r5     // Catch:{ AbortFlowException -> 0x0053 }
            java.lang.Object r7 = r6.collect(r7, r0)     // Catch:{ AbortFlowException -> 0x0053 }
            if (r7 != r2) goto L_0x0050
            return r2
        L_0x0050:
            r6 = r3
            r7 = r4
        L_0x0052:
            goto L_0x0059
        L_0x0053:
            r2 = move-exception
            r6 = r3
            r7 = r4
        L_0x0056:
            kotlinx.coroutines.flow.internal.FlowExceptions_commonKt.checkOwnership(r2, r7)
        L_0x0059:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__LimitKt.collectWhile(kotlinx.coroutines.flow.Flow, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private static final <T> Object collectWhile$$forInline(Flow<? extends T> $this$collectWhile, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> predicate, Continuation<? super Unit> $completion) {
        FlowKt__LimitKt$collectWhile$collector$1 collector = new FlowKt__LimitKt$collectWhile$collector$1(predicate);
        try {
            $this$collectWhile.collect(collector, $completion);
        } catch (AbortFlowException e) {
            FlowExceptions_commonKt.checkOwnership(e, collector);
        }
        return Unit.INSTANCE;
    }
}
