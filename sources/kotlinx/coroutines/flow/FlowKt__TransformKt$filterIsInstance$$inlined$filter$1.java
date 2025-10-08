package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@¢\u0006\u0002\u0010\u0006¨\u0006\u0007¸\u0006\t"}, d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$$inlined$unsafeFlow$1", "kotlinx/coroutines/flow/FlowKt__TransformKt$filter$$inlined$unsafeTransform$1"}, k = 1, mv = {2, 0, 0}, xi = 176)
/* compiled from: SafeCollector.common.kt */
public final class FlowKt__TransformKt$filterIsInstance$$inlined$filter$1 implements Flow<Object> {
    final /* synthetic */ Flow $this_unsafeTransform$inlined;

    public FlowKt__TransformKt$filterIsInstance$$inlined$filter$1(Flow flow) {
        this.$this_unsafeTransform$inlined = flow;
    }

    public Object collect(FlowCollector collector, Continuation $completion) {
        final FlowCollector $this$unsafeTransform_u24lambda_u240 = collector;
        Continuation continuation = $completion;
        Flow flow = this.$this_unsafeTransform$inlined;
        Intrinsics.needClassReification();
        Object collect = flow.collect(new FlowCollector() {
            /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
            /* JADX WARNING: Removed duplicated region for block: B:11:0x0031  */
            /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                /*
                    r9 = this;
                    boolean r0 = r11 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                    if (r0 == 0) goto L_0x0014
                    r0 = r11
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r1 = r0.label
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1$2$1
                    r0.<init>(r9, r11)
                L_0x0019:
                    java.lang.Object r1 = r0.result
                    java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r3 = r0.label
                    switch(r3) {
                        case 0: goto L_0x0031;
                        case 1: goto L_0x002c;
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
                    goto L_0x0052
                L_0x0031:
                    kotlin.ResultKt.throwOnFailure(r1)
                    r3 = r9
                    kotlinx.coroutines.flow.FlowCollector r3 = r0
                    r4 = 0
                    r5 = r0
                    kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                    r5 = r10
                    r6 = 0
                    r7 = 3
                    java.lang.String r8 = "R"
                    kotlin.jvm.internal.Intrinsics.reifiedOperationMarker(r7, r8)
                    boolean r5 = r5 instanceof java.lang.Object
                    if (r5 == 0) goto L_0x0053
                    r5 = 1
                    r0.label = r5
                    java.lang.Object r10 = r3.emit(r10, r0)
                    if (r10 != r2) goto L_0x0051
                    return r2
                L_0x0051:
                    r10 = r4
                L_0x0052:
                    goto L_0x0054
                L_0x0053:
                L_0x0054:
                    kotlin.Unit r10 = kotlin.Unit.INSTANCE
                    return r10
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
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
                        return this.this$0.emit((Object) null, this);
                    }
                };
                FlowCollector $this$filter_u24lambda_u240 = $this$unsafeTransform_u24lambda_u240;
                Object value2 = value;
                Continuation continuation = $completion;
                Continuation continuation2 = $completion;
                Intrinsics.reifiedOperationMarker(3, "R");
                if (value2 instanceof Object) {
                    $this$filter_u24lambda_u240.emit(value2, $completion);
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
            final /* synthetic */ FlowKt__TransformKt$filterIsInstance$$inlined$filter$1 this$0;

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
        Intrinsics.needClassReification();
        flow.collect(new FlowCollector() {
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final java.lang.Object emit(java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                /*
                    r9 = this;
                    boolean r0 = r11 instanceof kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                    if (r0 == 0) goto L_0x0014
                    r0 = r11
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1$2$1 r0 = (kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r1 = r0.label
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L_0x0019
                L_0x0014:
                    kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1$2$1 r0 = new kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1$2$1
                    r0.<init>(r9, r11)
                L_0x0019:
                    java.lang.Object r1 = r0.result
                    java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                    int r3 = r0.label
                    switch(r3) {
                        case 0: goto L_0x0031;
                        case 1: goto L_0x002c;
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
                    goto L_0x0052
                L_0x0031:
                    kotlin.ResultKt.throwOnFailure(r1)
                    r3 = r9
                    kotlinx.coroutines.flow.FlowCollector r3 = r0
                    r4 = 0
                    r5 = r0
                    kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                    r5 = r10
                    r6 = 0
                    r7 = 3
                    java.lang.String r8 = "R"
                    kotlin.jvm.internal.Intrinsics.reifiedOperationMarker(r7, r8)
                    boolean r5 = r5 instanceof java.lang.Object
                    if (r5 == 0) goto L_0x0053
                    r5 = 1
                    r0.label = r5
                    java.lang.Object r10 = r3.emit(r10, r0)
                    if (r10 != r2) goto L_0x0051
                    return r2
                L_0x0051:
                    r10 = r4
                L_0x0052:
                    goto L_0x0054
                L_0x0053:
                L_0x0054:
                    kotlin.Unit r10 = kotlin.Unit.INSTANCE
                    return r10
                */
                throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
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
                        return this.this$0.emit((Object) null, this);
                    }
                };
                FlowCollector $this$filter_u24lambda_u240 = $this$unsafeTransform_u24lambda_u240;
                Object value2 = value;
                Continuation continuation = $completion;
                Continuation continuation2 = $completion;
                Intrinsics.reifiedOperationMarker(3, "R");
                if (value2 instanceof Object) {
                    $this$filter_u24lambda_u240.emit(value2, $completion);
                }
                return Unit.INSTANCE;
            }
        }, $completion);
        return Unit.INSTANCE;
    }
}
