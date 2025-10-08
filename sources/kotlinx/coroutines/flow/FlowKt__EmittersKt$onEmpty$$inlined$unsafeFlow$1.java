package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;

@Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@¢\u0006\u0002\u0010\u0006¨\u0006\u0007¸\u0006\u0000"}, d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1 implements Flow<T> {
    final /* synthetic */ Function2 $action$inlined;
    final /* synthetic */ Flow $this_onEmpty$inlined;

    public FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1(Flow flow, Function2 function2) {
        this.$this_onEmpty$inlined = flow;
        this.$action$inlined = function2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0075  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector<? super T> r11, kotlin.coroutines.Continuation<? super kotlin.Unit> r12) {
        /*
            r10 = this;
            boolean r0 = r12 instanceof kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1.AnonymousClass1
            if (r0 == 0) goto L_0x0014
            r0 = r12
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1 r0 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1 r0 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1$1
            r0.<init>(r10, r12)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0048;
                case 1: goto L_0x0037;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r0)
            throw r11
        L_0x002c:
            r11 = 0
            java.lang.Object r2 = r0.L$0
            kotlinx.coroutines.flow.internal.SafeCollector r2 = (kotlinx.coroutines.flow.internal.SafeCollector) r2
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0035 }
            goto L_0x0094
        L_0x0035:
            r3 = move-exception
            goto L_0x009c
        L_0x0037:
            r11 = 0
            java.lang.Object r3 = r0.L$2
            kotlin.jvm.internal.Ref$BooleanRef r3 = (kotlin.jvm.internal.Ref.BooleanRef) r3
            java.lang.Object r4 = r0.L$1
            kotlinx.coroutines.flow.FlowCollector r4 = (kotlinx.coroutines.flow.FlowCollector) r4
            java.lang.Object r5 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1 r5 = (kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1) r5
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x0071
        L_0x0048:
            kotlin.ResultKt.throwOnFailure(r1)
            r5 = r10
            r3 = r0
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            r4 = r11
            r11 = 0
            kotlin.jvm.internal.Ref$BooleanRef r3 = new kotlin.jvm.internal.Ref$BooleanRef
            r3.<init>()
            r6 = 1
            r3.element = r6
            kotlinx.coroutines.flow.Flow r7 = r5.$this_onEmpty$inlined
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$1$1 r8 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$1$1
            r8.<init>(r3, r4)
            kotlinx.coroutines.flow.FlowCollector r8 = (kotlinx.coroutines.flow.FlowCollector) r8
            r0.L$0 = r5
            r0.L$1 = r4
            r0.L$2 = r3
            r0.label = r6
            java.lang.Object r6 = r7.collect(r8, r0)
            if (r6 != r2) goto L_0x0071
            return r2
        L_0x0071:
            boolean r6 = r3.element
            if (r6 == 0) goto L_0x00a0
            kotlinx.coroutines.flow.internal.SafeCollector r3 = new kotlinx.coroutines.flow.internal.SafeCollector
            r6 = 0
            kotlin.coroutines.CoroutineContext r7 = r0.getContext()
            r3.<init>(r4, r7)
            kotlin.jvm.functions.Function2 r4 = r5.$action$inlined     // Catch:{ all -> 0x0098 }
            r0.L$0 = r3     // Catch:{ all -> 0x0098 }
            r6 = 0
            r0.L$1 = r6     // Catch:{ all -> 0x0098 }
            r0.L$2 = r6     // Catch:{ all -> 0x0098 }
            r6 = 2
            r0.label = r6     // Catch:{ all -> 0x0098 }
            java.lang.Object r4 = r4.invoke(r3, r0)     // Catch:{ all -> 0x0098 }
            if (r4 != r2) goto L_0x0093
            return r2
        L_0x0093:
            r2 = r3
        L_0x0094:
            r2.releaseIntercepted()
            goto L_0x00a0
        L_0x0098:
            r2 = move-exception
            r9 = r3
            r3 = r2
            r2 = r9
        L_0x009c:
            r2.releaseIntercepted()
            throw r3
        L_0x00a0:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__EmittersKt$onEmpty$$inlined$unsafeFlow$1.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
