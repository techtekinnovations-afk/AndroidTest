package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;

@Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@¢\u0006\u0002\u0010\u0006¨\u0006\u0007¸\u0006\b"}, d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$$inlined$unsafeFlow$1"}, k = 1, mv = {2, 0, 0}, xi = 176)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1 implements Flow<R> {
    final /* synthetic */ Flow $this_unsafeTransform$inlined;
    final /* synthetic */ Function2 $transform$inlined$1;

    public FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1(Flow flow, Function2 function2) {
        this.$this_unsafeTransform$inlined = flow;
        this.$transform$inlined$1 = function2;
    }

    public Object collect(FlowCollector collector, Continuation $completion) {
        final FlowCollector $this$unsafeTransform_u24lambda_u240 = collector;
        Continuation continuation = $completion;
        Flow flow = this.$this_unsafeTransform$inlined;
        final Function2 function2 = this.$transform$inlined$1;
        Object collect = flow.collect(new FlowCollector() {
            /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
            /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
            /* JADX WARNING: Removed duplicated region for block: B:12:0x003c  */
            /* JADX WARNING: Removed duplicated region for block: B:17:0x0058  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(T r9, kotlin.coroutines.Continuation<? super kotlin.Unit> r10) {
                /*
                    r8 = this;
                    boolean r0 = r10 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1.AnonymousClass2.AnonymousClass1
                    if (r0 == 0) goto L_0x0014
                    r0 = r10
                    kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1.AnonymousClass2.AnonymousClass1) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r1 = r0.label
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1$2$1
                    r0.<init>(r8, r10)
                L_0x0019:
                    java.lang.Object r1 = r0.result
                    java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r3 = r0.label
                    switch(r3) {
                        case 0: goto L_0x003c;
                        case 1: goto L_0x0031;
                        case 2: goto L_0x002c;
                        default: goto L_0x0024;
                    }
                L_0x0024:
                    java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                    java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                    r9.<init>(r0)
                    throw r9
                L_0x002c:
                    r9 = 0
                    kotlin.ResultKt.throwOnFailure(r1)
                    goto L_0x0066
                L_0x0031:
                    r9 = 0
                    java.lang.Object r3 = r0.L$0
                    kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
                    kotlin.ResultKt.throwOnFailure(r1)
                    r5 = r9
                    r9 = r1
                    goto L_0x0055
                L_0x003c:
                    kotlin.ResultKt.throwOnFailure(r1)
                    r3 = r8
                    kotlinx.coroutines.flow.FlowCollector r4 = r0
                    r5 = r0
                    kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                    r5 = 0
                    kotlin.jvm.functions.Function2 r6 = r5
                    r0.L$0 = r4
                    r7 = 1
                    r0.label = r7
                    java.lang.Object r9 = r6.invoke(r9, r0)
                    if (r9 != r2) goto L_0x0054
                    return r2
                L_0x0054:
                    r3 = r4
                L_0x0055:
                    if (r9 != 0) goto L_0x0058
                    goto L_0x0067
                L_0x0058:
                    r4 = 0
                    r0.L$0 = r4
                    r4 = 2
                    r0.label = r4
                    java.lang.Object r9 = r3.emit(r9, r0)
                    if (r9 != r2) goto L_0x0065
                    return r2
                L_0x0065:
                    r9 = r5
                L_0x0066:
                L_0x0067:
                    kotlin.Unit r9 = kotlin.Unit.INSTANCE
                    return r9
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }

            public final Object emit$$forInline(Object value, Continuation $completion) {
                new ContinuationImpl(this, $completion) {
                    Object L$0;
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
                FlowCollector $this$mapNotNull_u24lambda_u246 = $this$unsafeTransform_u24lambda_u240;
                Continuation continuation = $completion;
                Object transformed = r5.invoke(value, $completion);
                if (transformed != null) {
                    $this$mapNotNull_u24lambda_u246.emit(transformed, $completion);
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
            final /* synthetic */ FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1 this$0;

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
        final Function2 function2 = this.$transform$inlined$1;
        flow.collect(new FlowCollector() {
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(T r9, kotlin.coroutines.Continuation<? super kotlin.Unit> r10) {
                /*
                    r8 = this;
                    boolean r0 = r10 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1.AnonymousClass2.AnonymousClass1
                    if (r0 == 0) goto L_0x0014
                    r0 = r10
                    kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1.AnonymousClass2.AnonymousClass1) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r1 = r0.label
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1$2$1
                    r0.<init>(r8, r10)
                L_0x0019:
                    java.lang.Object r1 = r0.result
                    java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r3 = r0.label
                    switch(r3) {
                        case 0: goto L_0x003c;
                        case 1: goto L_0x0031;
                        case 2: goto L_0x002c;
                        default: goto L_0x0024;
                    }
                L_0x0024:
                    java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                    java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                    r9.<init>(r0)
                    throw r9
                L_0x002c:
                    r9 = 0
                    kotlin.ResultKt.throwOnFailure(r1)
                    goto L_0x0066
                L_0x0031:
                    r9 = 0
                    java.lang.Object r3 = r0.L$0
                    kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
                    kotlin.ResultKt.throwOnFailure(r1)
                    r5 = r9
                    r9 = r1
                    goto L_0x0055
                L_0x003c:
                    kotlin.ResultKt.throwOnFailure(r1)
                    r3 = r8
                    kotlinx.coroutines.flow.FlowCollector r4 = r0
                    r5 = r0
                    kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                    r5 = 0
                    kotlin.jvm.functions.Function2 r6 = r5
                    r0.L$0 = r4
                    r7 = 1
                    r0.label = r7
                    java.lang.Object r9 = r6.invoke(r9, r0)
                    if (r9 != r2) goto L_0x0054
                    return r2
                L_0x0054:
                    r3 = r4
                L_0x0055:
                    if (r9 != 0) goto L_0x0058
                    goto L_0x0067
                L_0x0058:
                    r4 = 0
                    r0.L$0 = r4
                    r4 = 2
                    r0.label = r4
                    java.lang.Object r9 = r3.emit(r9, r0)
                    if (r9 != r2) goto L_0x0065
                    return r2
                L_0x0065:
                    r9 = r5
                L_0x0066:
                L_0x0067:
                    kotlin.Unit r9 = kotlin.Unit.INSTANCE
                    return r9
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$mapNotNull$$inlined$unsafeTransform$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
            }

            public final Object emit$$forInline(Object value, Continuation $completion) {
                new ContinuationImpl(this, $completion) {
                    Object L$0;
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
                FlowCollector $this$mapNotNull_u24lambda_u246 = $this$unsafeTransform_u24lambda_u240;
                Continuation continuation = $completion;
                Object transformed = function2.invoke(value, $completion);
                if (transformed != null) {
                    $this$mapNotNull_u24lambda_u246.emit(transformed, $completion);
                }
                return Unit.INSTANCE;
            }
        }, $completion);
        return Unit.INSTANCE;
    }
}
