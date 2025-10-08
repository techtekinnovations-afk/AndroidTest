package androidx.datastore.core;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;

@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002H@"}, d2 = {"<anonymous>", "Landroidx/datastore/core/Data;", "T"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$initData$1", f = "DataStoreImpl.kt", i = {0, 0, 0, 1, 1, 1, 1, 2, 2, 2}, l = {437, 458, 546, 468}, m = "invokeSuspend", n = {"updateLock", "initializationComplete", "currentData", "updateLock", "initializationComplete", "currentData", "api", "initializationComplete", "currentData", "$this$withLock_u24default$iv"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2"})
/* compiled from: DataStoreImpl.kt */
final class DataStoreImpl$InitDataStore$doRun$initData$1 extends SuspendLambda implements Function1<Continuation<? super Data<T>>, Object> {
    int I$0;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    int label;
    final /* synthetic */ DataStoreImpl<T> this$0;
    final /* synthetic */ DataStoreImpl<T>.InitDataStore this$1;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DataStoreImpl$InitDataStore$doRun$initData$1(DataStoreImpl<T> dataStoreImpl, DataStoreImpl<T>.InitDataStore initDataStore, Continuation<? super DataStoreImpl$InitDataStore$doRun$initData$1> continuation) {
        super(1, continuation);
        this.this$0 = dataStoreImpl;
        this.this$1 = initDataStore;
    }

    public final Continuation<Unit> create(Continuation<?> continuation) {
        return new DataStoreImpl$InitDataStore$doRun$initData$1(this.this$0, this.this$1, continuation);
    }

    public final Object invoke(Continuation<? super Data<T>> continuation) {
        return ((DataStoreImpl$InitDataStore$doRun$initData$1) create(continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x00a9, code lost:
        r8.element = ((androidx.datastore.core.Data) r6).getValue();
        r6 = new androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$initData$1$api$1(r11, r10, r9, r2.this$0);
        r8 = androidx.datastore.core.DataStoreImpl.InitDataStore.access$getInitTasks$p(r2.this$1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x00be, code lost:
        if (r8 == null) goto L_0x00fc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x00c0, code lost:
        r16 = r10;
        r10 = r6;
        r6 = r7;
        r12 = r16;
        r16 = r11;
        r11 = r9;
        r9 = r8.iterator();
        r13 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x00d8, code lost:
        if (r9.hasNext() == false) goto L_0x00f8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x00da, code lost:
        r2.L$0 = r13;
        r2.L$1 = r12;
        r2.L$2 = r11;
        r2.L$3 = r10;
        r2.L$4 = r9;
        r2.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00f2, code lost:
        if (((kotlin.jvm.functions.Function2) r9.next()).invoke(r10, r2) != r0) goto L_0x00f5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00f4, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00f8, code lost:
        r7 = r6;
        r10 = r11;
        r11 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00fc, code lost:
        r12 = r10;
        r10 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00fe, code lost:
        androidx.datastore.core.DataStoreImpl.InitDataStore.access$setInitTasks$p(r2.this$1, (java.util.List) null);
        r9 = r11;
        r8 = null;
        r2.L$0 = r12;
        r2.L$1 = r10;
        r2.L$2 = r9;
        r2.L$3 = null;
        r2.L$4 = null;
        r2.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x011b, code lost:
        if (r9.lock((java.lang.Object) null, r2) != r0) goto L_0x011e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x011d, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x011e, code lost:
        r6 = r7;
        r11 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        r11.element = true;
        r4 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0129, code lost:
        r9.unlock(r8);
        r4 = r10.element;
        r7 = r10.element;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0133, code lost:
        if (r7 == null) goto L_0x013a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0135, code lost:
        r3 = r7.hashCode();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x013a, code lost:
        r2.L$0 = r4;
        r2.L$1 = null;
        r2.L$2 = null;
        r2.I$0 = r3;
        r2.label = 4;
        r5 = androidx.datastore.core.DataStoreImpl.access$getCoordinator(r2.this$0).getVersion(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0152, code lost:
        if (r5 != r0) goto L_0x0155;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0154, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0155, code lost:
        r0 = r2;
        r2 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0162, code lost:
        return new androidx.datastore.core.Data(r4, r3, ((java.lang.Number) r2).intValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0163, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0164, code lost:
        r9.unlock(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0167, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r18) {
        /*
            r17 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            r1 = r17
            int r2 = r1.label
            r3 = 0
            r4 = 1
            r5 = 0
            switch(r2) {
                case 0: goto L_0x0077;
                case 1: goto L_0x005a;
                case 2: goto L_0x003b;
                case 3: goto L_0x0024;
                case 4: goto L_0x0016;
                default: goto L_0x000e;
            }
        L_0x000e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0016:
            r0 = r17
            r2 = r18
            int r3 = r0.I$0
            java.lang.Object r4 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r2)
            r6 = r2
            goto L_0x0157
        L_0x0024:
            r2 = r17
            r6 = r18
            r7 = 0
            r8 = 0
            java.lang.Object r9 = r2.L$2
            kotlinx.coroutines.sync.Mutex r9 = (kotlinx.coroutines.sync.Mutex) r9
            java.lang.Object r10 = r2.L$1
            kotlin.jvm.internal.Ref$ObjectRef r10 = (kotlin.jvm.internal.Ref.ObjectRef) r10
            java.lang.Object r11 = r2.L$0
            kotlin.jvm.internal.Ref$BooleanRef r11 = (kotlin.jvm.internal.Ref.BooleanRef) r11
            kotlin.ResultKt.throwOnFailure(r6)
            goto L_0x0122
        L_0x003b:
            r2 = r17
            r6 = r18
            r7 = 0
            r8 = 0
            java.lang.Object r9 = r2.L$4
            java.util.Iterator r9 = (java.util.Iterator) r9
            java.lang.Object r10 = r2.L$3
            androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$initData$1$api$1 r10 = (androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$initData$1$api$1) r10
            java.lang.Object r11 = r2.L$2
            kotlin.jvm.internal.Ref$ObjectRef r11 = (kotlin.jvm.internal.Ref.ObjectRef) r11
            java.lang.Object r12 = r2.L$1
            kotlin.jvm.internal.Ref$BooleanRef r12 = (kotlin.jvm.internal.Ref.BooleanRef) r12
            java.lang.Object r13 = r2.L$0
            kotlinx.coroutines.sync.Mutex r13 = (kotlinx.coroutines.sync.Mutex) r13
            kotlin.ResultKt.throwOnFailure(r6)
            goto L_0x00f6
        L_0x005a:
            r2 = r17
            r6 = r18
            java.lang.Object r7 = r2.L$3
            kotlin.jvm.internal.Ref$ObjectRef r7 = (kotlin.jvm.internal.Ref.ObjectRef) r7
            java.lang.Object r8 = r2.L$2
            kotlin.jvm.internal.Ref$ObjectRef r8 = (kotlin.jvm.internal.Ref.ObjectRef) r8
            java.lang.Object r9 = r2.L$1
            kotlin.jvm.internal.Ref$BooleanRef r9 = (kotlin.jvm.internal.Ref.BooleanRef) r9
            java.lang.Object r10 = r2.L$0
            kotlinx.coroutines.sync.Mutex r10 = (kotlinx.coroutines.sync.Mutex) r10
            kotlin.ResultKt.throwOnFailure(r6)
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            goto L_0x00a9
        L_0x0077:
            kotlin.ResultKt.throwOnFailure(r18)
            r2 = r17
            r6 = r18
            kotlinx.coroutines.sync.Mutex r10 = kotlinx.coroutines.sync.MutexKt.Mutex$default(r3, r4, r5)
            kotlin.jvm.internal.Ref$BooleanRef r7 = new kotlin.jvm.internal.Ref$BooleanRef
            r7.<init>()
            r9 = r7
            kotlin.jvm.internal.Ref$ObjectRef r7 = new kotlin.jvm.internal.Ref$ObjectRef
            r7.<init>()
            androidx.datastore.core.DataStoreImpl<T> r8 = r2.this$0
            r11 = r2
            kotlin.coroutines.Continuation r11 = (kotlin.coroutines.Continuation) r11
            r2.L$0 = r10
            r2.L$1 = r9
            r2.L$2 = r7
            r2.L$3 = r7
            r2.label = r4
            java.lang.Object r8 = r8.readDataOrHandleCorruption(r4, r11)
            if (r8 != r0) goto L_0x00a3
            return r0
        L_0x00a3:
            r11 = r10
            r10 = r9
            r9 = r7
            r7 = r6
            r6 = r8
            r8 = r9
        L_0x00a9:
            androidx.datastore.core.Data r6 = (androidx.datastore.core.Data) r6
            java.lang.Object r6 = r6.getValue()
            r8.element = r6
            androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$initData$1$api$1 r6 = new androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$initData$1$api$1
            androidx.datastore.core.DataStoreImpl<T> r8 = r2.this$0
            r6.<init>(r11, r10, r9, r8)
            androidx.datastore.core.DataStoreImpl<T>$InitDataStore r8 = r2.this$1
            java.util.List r8 = r8.initTasks
            if (r8 == 0) goto L_0x00fc
            java.lang.Iterable r8 = (java.lang.Iterable) r8
            r12 = 0
            java.util.Iterator r13 = r8.iterator()
            r16 = r10
            r10 = r6
            r6 = r7
            r7 = r12
            r12 = r16
            r16 = r11
            r11 = r9
            r9 = r13
            r13 = r16
        L_0x00d4:
            boolean r8 = r9.hasNext()
            if (r8 == 0) goto L_0x00f8
            java.lang.Object r8 = r9.next()
            kotlin.jvm.functions.Function2 r8 = (kotlin.jvm.functions.Function2) r8
            r14 = 0
            r2.L$0 = r13
            r2.L$1 = r12
            r2.L$2 = r11
            r2.L$3 = r10
            r2.L$4 = r9
            r15 = 2
            r2.label = r15
            java.lang.Object r8 = r8.invoke(r10, r2)
            if (r8 != r0) goto L_0x00f5
            return r0
        L_0x00f5:
            r8 = r14
        L_0x00f6:
            goto L_0x00d4
        L_0x00f8:
            r7 = r6
            r10 = r11
            r11 = r13
            goto L_0x00fe
        L_0x00fc:
            r12 = r10
            r10 = r9
        L_0x00fe:
            androidx.datastore.core.DataStoreImpl<T>$InitDataStore r6 = r2.this$1
            r6.initTasks = r5
            r9 = r11
            r8 = 0
            r6 = 0
            r11 = r2
            kotlin.coroutines.Continuation r11 = (kotlin.coroutines.Continuation) r11
            r2.L$0 = r12
            r2.L$1 = r10
            r2.L$2 = r9
            r2.L$3 = r5
            r2.L$4 = r5
            r13 = 3
            r2.label = r13
            java.lang.Object r11 = r9.lock(r8, r11)
            if (r11 != r0) goto L_0x011e
            return r0
        L_0x011e:
            r11 = r7
            r7 = r6
            r6 = r11
            r11 = r12
        L_0x0122:
            r12 = 0
            r11.element = r4     // Catch:{ all -> 0x0163 }
            kotlin.Unit r4 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0163 }
            r9.unlock(r8)
            T r4 = r10.element
            T r7 = r10.element
            if (r7 == 0) goto L_0x013a
            int r3 = r7.hashCode()
        L_0x013a:
            androidx.datastore.core.DataStoreImpl<T> r7 = r2.this$0
            androidx.datastore.core.InterProcessCoordinator r7 = r7.getCoordinator()
            r8 = r2
            kotlin.coroutines.Continuation r8 = (kotlin.coroutines.Continuation) r8
            r2.L$0 = r4
            r2.L$1 = r5
            r2.L$2 = r5
            r2.I$0 = r3
            r5 = 4
            r2.label = r5
            java.lang.Object r5 = r7.getVersion(r8)
            if (r5 != r0) goto L_0x0155
            return r0
        L_0x0155:
            r0 = r2
            r2 = r5
        L_0x0157:
            java.lang.Number r2 = (java.lang.Number) r2
            int r2 = r2.intValue()
            androidx.datastore.core.Data r5 = new androidx.datastore.core.Data
            r5.<init>(r4, r3, r2)
            return r5
        L_0x0163:
            r0 = move-exception
            r9.unlock(r8)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$initData$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
