package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;

@Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@¢\u0006\u0002\u0010\u0006¨\u0006\u0007¸\u0006\u0000"}, d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1 implements Flow<T> {
    final /* synthetic */ Function2 $predicate$inlined;
    final /* synthetic */ Flow $this_takeWhile$inlined;

    public FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1(Flow flow, Function2 function2) {
        this.$this_takeWhile$inlined = flow;
        this.$predicate$inlined = function2;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector<? super T> r11, kotlin.coroutines.Continuation<? super kotlin.Unit> r12) {
        /*
            r10 = this;
            boolean r0 = r12 instanceof kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1.AnonymousClass1
            if (r0 == 0) goto L_0x0014
            r0 = r12
            kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1$1 r0 = (kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1$1 r0 = new kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1$1
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
            r11 = 0
            r2 = 0
            java.lang.Object r3 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$lambda$6$$inlined$collectWhile$1 r3 = (kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$lambda$6$$inlined$collectWhile$1) r3
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ AbortFlowException -> 0x0036 }
            goto L_0x005e
        L_0x0036:
            r4 = move-exception
            goto L_0x0065
        L_0x0038:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r10
            r4 = r0
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r4 = 0
            kotlinx.coroutines.flow.Flow r5 = r3.$this_takeWhile$inlined
            r6 = r0
            r7 = 0
            kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$lambda$6$$inlined$collectWhile$1 r8 = new kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$lambda$6$$inlined$collectWhile$1
            kotlin.jvm.functions.Function2 r9 = r3.$predicate$inlined
            r8.<init>(r9, r11)
            r11 = r8
            kotlinx.coroutines.flow.FlowCollector r11 = (kotlinx.coroutines.flow.FlowCollector) r11     // Catch:{ AbortFlowException -> 0x005f }
            r0.L$0 = r8     // Catch:{ AbortFlowException -> 0x005f }
            r3 = 1
            r0.label = r3     // Catch:{ AbortFlowException -> 0x005f }
            java.lang.Object r11 = r5.collect(r11, r6)     // Catch:{ AbortFlowException -> 0x005f }
            if (r11 != r2) goto L_0x005b
            return r2
        L_0x005b:
            r11 = r4
            r2 = r7
            r3 = r8
        L_0x005e:
            goto L_0x0068
        L_0x005f:
            r11 = move-exception
            r2 = r4
            r4 = r11
            r11 = r2
            r2 = r7
            r3 = r8
        L_0x0065:
            kotlinx.coroutines.flow.internal.FlowExceptions_commonKt.checkOwnership(r4, r3)
        L_0x0068:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
