package androidx.datastore.core;

import kotlin.Metadata;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.sync.Mutex;

@Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001JA\u0010\u0002\u001a\u00028\u000021\u0010\u0003\u001a-\b\u0001\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0004H@¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"androidx/datastore/core/DataStoreImpl$InitDataStore$doRun$initData$1$api$1", "Landroidx/datastore/core/InitializerApi;", "updateData", "transform", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "t", "Lkotlin/coroutines/Continuation;", "", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "datastore-core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: DataStoreImpl.kt */
public final class DataStoreImpl$InitDataStore$doRun$initData$1$api$1 implements InitializerApi<T> {
    final /* synthetic */ Ref.ObjectRef<T> $currentData;
    final /* synthetic */ Ref.BooleanRef $initializationComplete;
    final /* synthetic */ Mutex $updateLock;
    final /* synthetic */ DataStoreImpl<T> this$0;

    DataStoreImpl$InitDataStore$doRun$initData$1$api$1(Mutex $updateLock2, Ref.BooleanRef $initializationComplete2, Ref.ObjectRef<T> $currentData2, DataStoreImpl<T> $receiver) {
        this.$updateLock = $updateLock2;
        this.$initializationComplete = $initializationComplete2;
        this.$currentData = $currentData2;
        this.this$0 = $receiver;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x007a  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00aa A[Catch:{ all -> 0x005b }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object updateData(kotlin.jvm.functions.Function2<? super T, ? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> r12, kotlin.coroutines.Continuation<? super T> r13) {
        /*
            r11 = this;
            boolean r0 = r13 instanceof androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$initData$1$api$1$updateData$1
            if (r0 == 0) goto L_0x0014
            r0 = r13
            androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$initData$1$api$1$updateData$1 r0 = (androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$initData$1$api$1$updateData$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r13 = r0.label
            int r13 = r13 - r2
            r0.label = r13
            goto L_0x0019
        L_0x0014:
            androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$initData$1$api$1$updateData$1 r0 = new androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$initData$1$api$1$updateData$1
            r0.<init>(r11, r13)
        L_0x0019:
            java.lang.Object r13 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            switch(r2) {
                case 0: goto L_0x007a;
                case 1: goto L_0x0060;
                case 2: goto L_0x0041;
                case 3: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L_0x002c:
            r12 = 0
            r1 = 0
            java.lang.Object r2 = r0.L$2
            java.lang.Object r3 = r0.L$1
            kotlin.jvm.internal.Ref$ObjectRef r3 = (kotlin.jvm.internal.Ref.ObjectRef) r3
            r4 = 0
            java.lang.Object r5 = r0.L$0
            kotlinx.coroutines.sync.Mutex r5 = (kotlinx.coroutines.sync.Mutex) r5
            kotlin.ResultKt.throwOnFailure(r13)     // Catch:{ all -> 0x003e }
            goto L_0x00e4
        L_0x003e:
            r1 = move-exception
            goto L_0x0103
        L_0x0041:
            r12 = 0
            r2 = 0
            java.lang.Object r3 = r0.L$2
            androidx.datastore.core.DataStoreImpl r3 = (androidx.datastore.core.DataStoreImpl) r3
            java.lang.Object r4 = r0.L$1
            kotlin.jvm.internal.Ref$ObjectRef r4 = (kotlin.jvm.internal.Ref.ObjectRef) r4
            r5 = 0
            java.lang.Object r6 = r0.L$0
            kotlinx.coroutines.sync.Mutex r6 = (kotlinx.coroutines.sync.Mutex) r6
            kotlin.ResultKt.throwOnFailure(r13)     // Catch:{ all -> 0x005b }
            r7 = r4
            r4 = r3
            r3 = r7
            r7 = r6
            r6 = r5
            r5 = r13
            goto L_0x00c6
        L_0x005b:
            r1 = move-exception
            r4 = r5
            r5 = r6
            goto L_0x0103
        L_0x0060:
            r12 = 0
            java.lang.Object r2 = r0.L$4
            androidx.datastore.core.DataStoreImpl r2 = (androidx.datastore.core.DataStoreImpl) r2
            java.lang.Object r3 = r0.L$3
            kotlin.jvm.internal.Ref$ObjectRef r3 = (kotlin.jvm.internal.Ref.ObjectRef) r3
            java.lang.Object r4 = r0.L$2
            kotlin.jvm.internal.Ref$BooleanRef r4 = (kotlin.jvm.internal.Ref.BooleanRef) r4
            r5 = 0
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.sync.Mutex r6 = (kotlinx.coroutines.sync.Mutex) r6
            java.lang.Object r7 = r0.L$0
            kotlin.jvm.functions.Function2 r7 = (kotlin.jvm.functions.Function2) r7
            kotlin.ResultKt.throwOnFailure(r13)
            goto L_0x00a4
        L_0x007a:
            kotlin.ResultKt.throwOnFailure(r13)
            r2 = r11
            r7 = r12
            kotlinx.coroutines.sync.Mutex r12 = r2.$updateLock
            kotlin.jvm.internal.Ref$BooleanRef r4 = r2.$initializationComplete
            kotlin.jvm.internal.Ref$ObjectRef<T> r3 = r2.$currentData
            androidx.datastore.core.DataStoreImpl<T> r5 = r2.this$0
            r2 = 0
            r6 = 0
            r0.L$0 = r7
            r0.L$1 = r12
            r0.L$2 = r4
            r0.L$3 = r3
            r0.L$4 = r5
            r8 = 1
            r0.label = r8
            java.lang.Object r8 = r12.lock(r2, r0)
            if (r8 != r1) goto L_0x009e
            return r1
        L_0x009e:
            r10 = r6
            r6 = r12
            r12 = r10
            r10 = r5
            r5 = r2
            r2 = r10
        L_0x00a4:
            r8 = 0
            boolean r4 = r4.element     // Catch:{ all -> 0x005b }
            if (r4 != 0) goto L_0x00f6
            T r4 = r3.element     // Catch:{ all -> 0x005b }
            r0.L$0 = r6     // Catch:{ all -> 0x005b }
            r0.L$1 = r3     // Catch:{ all -> 0x005b }
            r0.L$2 = r2     // Catch:{ all -> 0x005b }
            r9 = 0
            r0.L$3 = r9     // Catch:{ all -> 0x005b }
            r0.L$4 = r9     // Catch:{ all -> 0x005b }
            r9 = 2
            r0.label = r9     // Catch:{ all -> 0x005b }
            java.lang.Object r4 = r7.invoke(r4, r0)     // Catch:{ all -> 0x005b }
            if (r4 != r1) goto L_0x00c1
            return r1
        L_0x00c1:
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r2
            r2 = r8
        L_0x00c6:
            T r8 = r3.element     // Catch:{ all -> 0x00f2 }
            boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r5, (java.lang.Object) r8)     // Catch:{ all -> 0x00f2 }
            if (r8 != 0) goto L_0x00e8
            r0.L$0 = r7     // Catch:{ all -> 0x00f2 }
            r0.L$1 = r3     // Catch:{ all -> 0x00f2 }
            r0.L$2 = r5     // Catch:{ all -> 0x00f2 }
            r8 = 3
            r0.label = r8     // Catch:{ all -> 0x00f2 }
            r8 = 0
            java.lang.Object r4 = r4.writeData$datastore_core_release(r5, r8, r0)     // Catch:{ all -> 0x00f2 }
            if (r4 != r1) goto L_0x00e0
            return r1
        L_0x00e0:
            r1 = r2
            r2 = r5
            r4 = r6
            r5 = r7
        L_0x00e4:
            r3.element = r2     // Catch:{ all -> 0x003e }
            r2 = r1
            goto L_0x00ea
        L_0x00e8:
            r4 = r6
            r5 = r7
        L_0x00ea:
            T r1 = r3.element     // Catch:{ all -> 0x003e }
            r5.unlock(r4)
            return r1
        L_0x00f2:
            r1 = move-exception
            r4 = r6
            r5 = r7
            goto L_0x0103
        L_0x00f6:
            r1 = 0
            java.lang.String r2 = "InitializerApi.updateData should not be called after initialization is complete."
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException     // Catch:{ all -> 0x005b }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x005b }
            r1.<init>(r2)     // Catch:{ all -> 0x005b }
            throw r1     // Catch:{ all -> 0x005b }
        L_0x0103:
            r5.unlock(r4)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$initData$1$api$1.updateData(kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
