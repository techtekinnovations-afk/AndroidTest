package com.google.firebase.firestore;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@¢\u0006\u0002\u0010\u0006¨\u0006\u0007¸\u0006\t"}, d2 = {"kotlinx/coroutines/flow/internal/SafeCollector_commonKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__EmittersKt$unsafeTransform$$inlined$unsafeFlow$1", "kotlinx/coroutines/flow/FlowKt__TransformKt$map$$inlined$unsafeTransform$1"}, k = 1, mv = {2, 0, 0}, xi = 176)
/* compiled from: SafeCollector.common.kt */
public final class FirestoreKt$dataObjects$$inlined$map$2 implements Flow<T> {
    final /* synthetic */ Flow $this_unsafeTransform$inlined;

    public FirestoreKt$dataObjects$$inlined$map$2(Flow flow) {
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
            public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                /*
                    r8 = this;
                    boolean r0 = r10 instanceof com.google.firebase.firestore.FirestoreKt$dataObjects$$inlined$map$2.AnonymousClass2.AnonymousClass1
                    if (r0 == 0) goto L_0x0014
                    r0 = r10
                    com.google.firebase.firestore.FirestoreKt$dataObjects$$inlined$map$2$2$1 r0 = (com.google.firebase.firestore.FirestoreKt$dataObjects$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r1 = r0.label
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L_0x0019
                L_0x0014:
                    com.google.firebase.firestore.FirestoreKt$dataObjects$$inlined$map$2$2$1 r0 = new com.google.firebase.firestore.FirestoreKt$dataObjects$$inlined$map$2$2$1
                    r0.<init>(r8, r10)
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
                    java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                    java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                    r9.<init>(r0)
                    throw r9
                L_0x002c:
                    r9 = 0
                    kotlin.ResultKt.throwOnFailure(r1)
                    goto L_0x0058
                L_0x0031:
                    kotlin.ResultKt.throwOnFailure(r1)
                    r3 = r8
                    kotlinx.coroutines.flow.FlowCollector r3 = r0
                    r4 = 0
                    r5 = r0
                    kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                    com.google.firebase.firestore.DocumentSnapshot r9 = (com.google.firebase.firestore.DocumentSnapshot) r9
                    r5 = 0
                    r6 = 4
                    java.lang.String r7 = "T"
                    kotlin.jvm.internal.Intrinsics.reifiedOperationMarker(r6, r7)
                    java.lang.Class<java.lang.Object> r6 = java.lang.Object.class
                    r7 = r6
                    java.lang.Class r7 = (java.lang.Class) r7
                    java.lang.Object r9 = r9.toObject(r6)
                    r5 = 1
                    r0.label = r5
                    java.lang.Object r9 = r3.emit(r9, r0)
                    if (r9 != r2) goto L_0x0057
                    return r2
                L_0x0057:
                    r9 = r4
                L_0x0058:
                    kotlin.Unit r9 = kotlin.Unit.INSTANCE
                    return r9
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.FirestoreKt$dataObjects$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
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
                        return this.this$0.emit((Object) null, this);
                    }
                };
                FlowCollector $this$map_u24lambda_u245 = $this$unsafeTransform_u24lambda_u240;
                Continuation continuation = $completion;
                Continuation continuation2 = $completion;
                Intrinsics.reifiedOperationMarker(4, "T");
                Class<Object> cls = Object.class;
                Class cls2 = cls;
                $this$map_u24lambda_u245.emit(((DocumentSnapshot) value).toObject(cls), $completion);
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
            final /* synthetic */ FirestoreKt$dataObjects$$inlined$map$2 this$0;

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
            public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                /*
                    r8 = this;
                    boolean r0 = r10 instanceof com.google.firebase.firestore.FirestoreKt$dataObjects$$inlined$map$2.AnonymousClass2.AnonymousClass1
                    if (r0 == 0) goto L_0x0014
                    r0 = r10
                    com.google.firebase.firestore.FirestoreKt$dataObjects$$inlined$map$2$2$1 r0 = (com.google.firebase.firestore.FirestoreKt$dataObjects$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                    int r1 = r0.label
                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                    r1 = r1 & r2
                    if (r1 == 0) goto L_0x0014
                    int r1 = r0.label
                    int r1 = r1 - r2
                    r0.label = r1
                    goto L_0x0019
                L_0x0014:
                    com.google.firebase.firestore.FirestoreKt$dataObjects$$inlined$map$2$2$1 r0 = new com.google.firebase.firestore.FirestoreKt$dataObjects$$inlined$map$2$2$1
                    r0.<init>(r8, r10)
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
                    java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                    java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                    r9.<init>(r0)
                    throw r9
                L_0x002c:
                    r9 = 0
                    kotlin.ResultKt.throwOnFailure(r1)
                    goto L_0x0058
                L_0x0031:
                    kotlin.ResultKt.throwOnFailure(r1)
                    r3 = r8
                    kotlinx.coroutines.flow.FlowCollector r3 = r0
                    r4 = 0
                    r5 = r0
                    kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                    com.google.firebase.firestore.DocumentSnapshot r9 = (com.google.firebase.firestore.DocumentSnapshot) r9
                    r5 = 0
                    r6 = 4
                    java.lang.String r7 = "T"
                    kotlin.jvm.internal.Intrinsics.reifiedOperationMarker(r6, r7)
                    java.lang.Class<java.lang.Object> r6 = java.lang.Object.class
                    r7 = r6
                    java.lang.Class r7 = (java.lang.Class) r7
                    java.lang.Object r9 = r9.toObject(r6)
                    r5 = 1
                    r0.label = r5
                    java.lang.Object r9 = r3.emit(r9, r0)
                    if (r9 != r2) goto L_0x0057
                    return r2
                L_0x0057:
                    r9 = r4
                L_0x0058:
                    kotlin.Unit r9 = kotlin.Unit.INSTANCE
                    return r9
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.FirestoreKt$dataObjects$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
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
                        return this.this$0.emit((Object) null, this);
                    }
                };
                FlowCollector $this$map_u24lambda_u245 = $this$unsafeTransform_u24lambda_u240;
                Continuation continuation = $completion;
                Continuation continuation2 = $completion;
                Intrinsics.reifiedOperationMarker(4, "T");
                Class<Object> cls = Object.class;
                Class cls2 = cls;
                $this$map_u24lambda_u245.emit(((DocumentSnapshot) value).toObject(cls), $completion);
                return Unit.INSTANCE;
            }
        }, $completion);
        return Unit.INSTANCE;
    }
}
