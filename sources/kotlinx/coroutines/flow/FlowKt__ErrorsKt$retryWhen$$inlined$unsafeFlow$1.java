package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function4;

@Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@¢\u0006\u0002\u0010\u0006¨\u0006\u0007¸\u0006\u0000"}, d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1 implements Flow<T> {
    final /* synthetic */ Function4 $predicate$inlined;
    final /* synthetic */ Flow $this_retryWhen$inlined;

    public FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1(Flow flow, Function4 function4) {
        this.$this_retryWhen$inlined = flow;
        this.$predicate$inlined = function4;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0066, code lost:
        r7 = 0;
        r8 = r3.$this_retryWhen$inlined;
        r0.L$0 = r3;
        r0.L$1 = r14;
        r0.L$2 = null;
        r0.J$0 = r5;
        r0.I$0 = 0;
        r0.label = 1;
        r8 = kotlinx.coroutines.flow.FlowKt.catchImpl(r8, r14, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x007b, code lost:
        if (r8 != r2) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x007d, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x007e, code lost:
        r6 = r14;
        r14 = r4;
        r4 = r5;
        r11 = r2;
        r2 = r1;
        r1 = r8;
        r8 = r3;
        r3 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0087, code lost:
        r1 = (java.lang.Throwable) r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0089, code lost:
        if (r1 == null) goto L_0x00bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x008b, code lost:
        r7 = r8.$predicate$inlined;
        r9 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r4);
        r0.L$0 = r8;
        r0.L$1 = r6;
        r0.L$2 = r1;
        r0.J$0 = r4;
        r0.label = 2;
        r7 = r7.invoke(r6, r1, r9, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00a0, code lost:
        if (r7 != r3) goto L_0x00a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00a2, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00a3, code lost:
        r11 = r6;
        r6 = r1;
        r1 = r7;
        r7 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00ad, code lost:
        if (((java.lang.Boolean) r1).booleanValue() == false) goto L_0x00bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00af, code lost:
        r5 = r4 + 1;
        r4 = r14;
        r14 = r7;
        r7 = 1;
        r1 = r2;
        r2 = r3;
        r3 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00bc, code lost:
        throw r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00bd, code lost:
        r11 = r4;
        r4 = r14;
        r14 = r6;
        r5 = r11;
        r1 = r2;
        r2 = r3;
        r3 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00c4, code lost:
        if (r7 != 0) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00c9, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Incorrect type for immutable var: ssa=kotlinx.coroutines.flow.FlowCollector<? super T>, code=kotlinx.coroutines.flow.FlowCollector, for r14v0, types: [kotlinx.coroutines.flow.FlowCollector<? super T>] */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector r14, kotlin.coroutines.Continuation<? super kotlin.Unit> r15) {
        /*
            r13 = this;
            boolean r0 = r15 instanceof kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1.AnonymousClass1
            if (r0 == 0) goto L_0x0014
            r0 = r15
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1$1 r0 = (kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1$1 r0 = new kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1$1
            r0.<init>(r13, r15)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x005b;
                case 1: goto L_0x0046;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r14.<init>(r0)
            throw r14
        L_0x002c:
            r14 = 0
            long r3 = r0.J$0
            java.lang.Object r5 = r0.L$2
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
            java.lang.Object r7 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1 r7 = (kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1) r7
            kotlin.ResultKt.throwOnFailure(r1)
            r8 = r7
            r7 = r6
            r6 = r5
            r4 = r3
            r3 = r2
            r2 = r1
            goto L_0x00a7
        L_0x0046:
            r14 = 0
            int r3 = r0.I$0
            long r4 = r0.J$0
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
            java.lang.Object r7 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1 r7 = (kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1) r7
            kotlin.ResultKt.throwOnFailure(r1)
            r8 = r7
            r7 = r3
            r3 = r2
            r2 = r1
            goto L_0x0087
        L_0x005b:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r13
            r4 = r0
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r4 = 0
            r5 = 0
        L_0x0066:
            r7 = 0
            kotlinx.coroutines.flow.Flow r8 = r3.$this_retryWhen$inlined
            r0.L$0 = r3
            r0.L$1 = r14
            r9 = 0
            r0.L$2 = r9
            r0.J$0 = r5
            r0.I$0 = r7
            r9 = 1
            r0.label = r9
            java.lang.Object r8 = kotlinx.coroutines.flow.FlowKt.catchImpl(r8, r14, r0)
            if (r8 != r2) goto L_0x007e
            return r2
        L_0x007e:
            r11 = r5
            r6 = r14
            r14 = r4
            r4 = r11
            r11 = r2
            r2 = r1
            r1 = r8
            r8 = r3
            r3 = r11
        L_0x0087:
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            if (r1 == 0) goto L_0x00bd
            kotlin.jvm.functions.Function4 r7 = r8.$predicate$inlined
            java.lang.Long r9 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r4)
            r0.L$0 = r8
            r0.L$1 = r6
            r0.L$2 = r1
            r0.J$0 = r4
            r10 = 2
            r0.label = r10
            java.lang.Object r7 = r7.invoke(r6, r1, r9, r0)
            if (r7 != r3) goto L_0x00a3
            return r3
        L_0x00a3:
            r11 = r6
            r6 = r1
            r1 = r7
            r7 = r11
        L_0x00a7:
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L_0x00bc
            r1 = 1
            r9 = 1
            long r4 = r4 + r9
            r5 = r4
            r4 = r14
            r14 = r7
            r7 = r1
            r1 = r2
            r2 = r3
            r3 = r8
            goto L_0x00c4
        L_0x00bc:
            throw r6
        L_0x00bd:
            r11 = r4
            r4 = r14
            r14 = r6
            r5 = r11
            r1 = r2
            r2 = r3
            r3 = r8
        L_0x00c4:
            if (r7 != 0) goto L_0x0066
            kotlin.Unit r14 = kotlin.Unit.INSTANCE
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ErrorsKt$retryWhen$$inlined$unsafeFlow$1.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
