package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;

@Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@¢\u0006\u0002\u0010\u0006¨\u0006\u0007¸\u0006\b"}, d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$$inlined$unsafeFlow$1"}, k = 1, mv = {2, 0, 0}, xi = 176)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__TransformKt$filterNot$$inlined$unsafeTransform$1 implements Flow<T> {
    final /* synthetic */ Function2 $predicate$inlined;
    final /* synthetic */ Flow $this_unsafeTransform$inlined;

    public FlowKt__TransformKt$filterNot$$inlined$unsafeTransform$1(Flow flow, Function2 function2) {
        this.$this_unsafeTransform$inlined = flow;
        this.$predicate$inlined = function2;
    }

    public Object collect(FlowCollector collector, Continuation $completion) {
        final FlowCollector $this$unsafeTransform_u24lambda_u240 = collector;
        Continuation continuation = $completion;
        Flow flow = this.$this_unsafeTransform$inlined;
        final Function2 function2 = this.$predicate$inlined;
        Object collect = flow.collect(new FlowCollector() {
            /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
            /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
            /* JADX WARNING: Removed duplicated region for block: B:12:0x003f  */
            /* JADX WARNING: Removed duplicated region for block: B:18:0x0064  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(T r10, kotlin.coroutines.Continuation<? super kotlin.Unit> r11) {
                /*
                    r9 = this;
                    boolean r0 = r11 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$filterNot$$inlined$unsafeTransform$1.AnonymousClass2.AnonymousClass1
                    if (r0 == 0) goto L_0x0014
                    r0 = r11
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filterNot$$inlined$unsafeTransform$1$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$filterNot$$inlined$unsafeTransform$1.AnonymousClass2.AnonymousClass1) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r1 = r0.label
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filterNot$$inlined$unsafeTransform$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$filterNot$$inlined$unsafeTransform$1$2$1
                    r0.<init>(r9, r11)
                L_0x0019:
                    java.lang.Object r1 = r0.result
                    java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r3 = r0.label
                    switch(r3) {
                        case 0: goto L_0x003f;
                        case 1: goto L_0x0031;
                        case 2: goto L_0x002c;
                        default: goto L_0x0024;
                    }
                L_0x0024:
                    java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                    java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                    r10.<init>(r0)
                    throw r10
                L_0x002c:
                    r10 = 0
                    kotlin.ResultKt.throwOnFailure(r1)
                    goto L_0x0073
                L_0x0031:
                    r10 = 0
                    java.lang.Object r3 = r0.L$1
                    kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
                    java.lang.Object r4 = r0.L$0
                    kotlin.ResultKt.throwOnFailure(r1)
                    r5 = r4
                    r4 = r3
                    r3 = r1
                    goto L_0x005c
                L_0x003f:
                    kotlin.ResultKt.throwOnFailure(r1)
                    r3 = r9
                    kotlinx.coroutines.flow.FlowCollector r4 = r0
                    r5 = r0
                    kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                    r5 = 0
                    kotlin.jvm.functions.Function2 r6 = r5
                    r0.L$0 = r10
                    r0.L$1 = r4
                    r7 = 1
                    r0.label = r7
                    java.lang.Object r3 = r6.invoke(r10, r0)
                    if (r3 != r2) goto L_0x0059
                    return r2
                L_0x0059:
                    r8 = r5
                    r5 = r10
                    r10 = r8
                L_0x005c:
                    java.lang.Boolean r3 = (java.lang.Boolean) r3
                    boolean r3 = r3.booleanValue()
                    if (r3 != 0) goto L_0x0074
                    r3 = 0
                    r0.L$0 = r3
                    r0.L$1 = r3
                    r3 = 2
                    r0.label = r3
                    java.lang.Object r3 = r4.emit(r5, r0)
                    if (r3 != r2) goto L_0x0073
                    return r2
                L_0x0073:
                    goto L_0x0075
                L_0x0074:
                L_0x0075:
                    kotlin.Unit r10 = kotlin.Unit.INSTANCE
                    return r10
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$filterNot$$inlined$unsafeTransform$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }

            public final Object emit$$forInline(Object value, Continuation $completion) {
                new ContinuationImpl(this, $completion) {
                    Object L$0;
                    Object L$1;
                    int label;
                    /* synthetic */ Object result;
                    final /* synthetic */ AnonymousClass2 this$0;

                    {
                        this.this$0 = r1;
                    }

                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return this.this$0.emit(null, this);
                    }
                };
                FlowCollector $this$filterNot_u24lambda_u241 = $this$unsafeTransform_u24lambda_u240;
                Object value2 = value;
                Continuation continuation = $completion;
                if (!((Boolean) r5.invoke(value2, $completion)).booleanValue()) {
                    $this$filterNot_u24lambda_u241.emit(value2, $completion);
                }
                return Unit.INSTANCE;
            }
        }, $completion);
        if (collect == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            return collect;
        }
        return Unit.INSTANCE;
    }

    public Object collect$$forInline(FlowCollector collector, Continuation $completion) {
        new ContinuationImpl(this, $completion) {
            int label;
            /* synthetic */ Object result;
            final /* synthetic */ FlowKt__TransformKt$filterNot$$inlined$unsafeTransform$1 this$0;

            {
                this.this$0 = r1;
            }

            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return this.this$0.collect((FlowCollector) null, this);
            }
        };
        final FlowCollector $this$unsafeTransform_u24lambda_u240 = collector;
        Continuation continuation = $completion;
        Flow flow = this.$this_unsafeTransform$inlined;
        final Function2 function2 = this.$predicate$inlined;
        flow.collect(new FlowCollector() {
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(T r10, kotlin.coroutines.Continuation<? super kotlin.Unit> r11) {
                /*
                    r9 = this;
                    boolean r0 = r11 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$filterNot$$inlined$unsafeTransform$1.AnonymousClass2.AnonymousClass1
                    if (r0 == 0) goto L_0x0014
                    r0 = r11
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filterNot$$inlined$unsafeTransform$1$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$filterNot$$inlined$unsafeTransform$1.AnonymousClass2.AnonymousClass1) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r1 = r0.label
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filterNot$$inlined$unsafeTransform$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$filterNot$$inlined$unsafeTransform$1$2$1
                    r0.<init>(r9, r11)
                L_0x0019:
                    java.lang.Object r1 = r0.result
                    java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r3 = r0.label
                    switch(r3) {
                        case 0: goto L_0x003f;
                        case 1: goto L_0x0031;
                        case 2: goto L_0x002c;
                        default: goto L_0x0024;
                    }
                L_0x0024:
                    java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                    java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                    r10.<init>(r0)
                    throw r10
                L_0x002c:
                    r10 = 0
                    kotlin.ResultKt.throwOnFailure(r1)
                    goto L_0x0073
                L_0x0031:
                    r10 = 0
                    java.lang.Object r3 = r0.L$1
                    kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
                    java.lang.Object r4 = r0.L$0
                    kotlin.ResultKt.throwOnFailure(r1)
                    r5 = r4
                    r4 = r3
                    r3 = r1
                    goto L_0x005c
                L_0x003f:
                    kotlin.ResultKt.throwOnFailure(r1)
                    r3 = r9
                    kotlinx.coroutines.flow.FlowCollector r4 = r0
                    r5 = r0
                    kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                    r5 = 0
                    kotlin.jvm.functions.Function2 r6 = r5
                    r0.L$0 = r10
                    r0.L$1 = r4
                    r7 = 1
                    r0.label = r7
                    java.lang.Object r3 = r6.invoke(r10, r0)
                    if (r3 != r2) goto L_0x0059
                    return r2
                L_0x0059:
                    r8 = r5
                    r5 = r10
                    r10 = r8
                L_0x005c:
                    java.lang.Boolean r3 = (java.lang.Boolean) r3
                    boolean r3 = r3.booleanValue()
                    if (r3 != 0) goto L_0x0074
                    r3 = 0
                    r0.L$0 = r3
                    r0.L$1 = r3
                    r3 = 2
                    r0.label = r3
                    java.lang.Object r3 = r4.emit(r5, r0)
                    if (r3 != r2) goto L_0x0073
                    return r2
                L_0x0073:
                    goto L_0x0075
                L_0x0074:
                L_0x0075:
                    kotlin.Unit r10 = kotlin.Unit.INSTANCE
                    return r10
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$filterNot$$inlined$unsafeTransform$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }

            public final Object emit$$forInline(Object value, Continuation $completion) {
                new ContinuationImpl(this, $completion) {
                    Object L$0;
                    Object L$1;
                    int label;
                    /* synthetic */ Object result;
                    final /* synthetic */ AnonymousClass2 this$0;

                    {
                        this.this$0 = r1;
                    }

                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return this.this$0.emit(null, this);
                    }
                };
                FlowCollector $this$filterNot_u24lambda_u241 = $this$unsafeTransform_u24lambda_u240;
                Object value2 = value;
                Continuation continuation = $completion;
                if (!((Boolean) function2.invoke(value2, $completion)).booleanValue()) {
                    $this$filterNot_u24lambda_u241.emit(value2, $completion);
                }
                return Unit.INSTANCE;
            }
        }, $completion);
        return Unit.INSTANCE;
    }
}
