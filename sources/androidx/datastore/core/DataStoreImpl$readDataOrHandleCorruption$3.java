package androidx.datastore.core;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref;

@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002H@"}, d2 = {"<anonymous>", "", "T"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "androidx.datastore.core.DataStoreImpl$readDataOrHandleCorruption$3", f = "DataStoreImpl.kt", i = {}, l = {387, 388, 390}, m = "invokeSuspend", n = {}, s = {})
/* compiled from: DataStoreImpl.kt */
final class DataStoreImpl$readDataOrHandleCorruption$3 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
    final /* synthetic */ Ref.ObjectRef<T> $newData;
    final /* synthetic */ Ref.IntRef $version;
    Object L$0;
    int label;
    final /* synthetic */ DataStoreImpl<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DataStoreImpl$readDataOrHandleCorruption$3(Ref.ObjectRef<T> objectRef, DataStoreImpl<T> dataStoreImpl, Ref.IntRef intRef, Continuation<? super DataStoreImpl$readDataOrHandleCorruption$3> continuation) {
        super(1, continuation);
        this.$newData = objectRef;
        this.this$0 = dataStoreImpl;
        this.$version = intRef;
    }

    public final Continuation<Unit> create(Continuation<?> continuation) {
        return new DataStoreImpl$readDataOrHandleCorruption$3(this.$newData, this.this$0, this.$version, continuation);
    }

    public final Object invoke(Continuation<? super Unit> continuation) {
        return ((DataStoreImpl$readDataOrHandleCorruption$3) create(continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r4.element = r10;
        r10 = r3.$version;
        r3.L$0 = r10;
        r3.label = 2;
        r4 = r3.this$0.getCoordinator().getVersion(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0068, code lost:
        if (r4 != r0) goto L_0x006b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006a, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x006b, code lost:
        r8 = r4;
        r4 = r10;
        r10 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x006e, code lost:
        r4.element = ((java.lang.Number) r10).intValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0078, code lost:
        r10 = r1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0092 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0093  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r9.label
            r2 = 1
            switch(r1) {
                case 0: goto L_0x0036;
                case 1: goto L_0x002a;
                case 2: goto L_0x001e;
                case 3: goto L_0x0012;
                default: goto L_0x000a;
            }
        L_0x000a:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L_0x0012:
            r0 = r9
            java.lang.Object r1 = r0.L$0
            kotlin.jvm.internal.Ref$IntRef r1 = (kotlin.jvm.internal.Ref.IntRef) r1
            kotlin.ResultKt.throwOnFailure(r10)
            r3 = r0
            r0 = r10
            goto L_0x0095
        L_0x001e:
            r1 = r9
            java.lang.Object r3 = r1.L$0
            kotlin.jvm.internal.Ref$IntRef r3 = (kotlin.jvm.internal.Ref.IntRef) r3
            kotlin.ResultKt.throwOnFailure(r10)     // Catch:{ CorruptionException -> 0x007a }
            r4 = r3
            r3 = r1
            r1 = r10
            goto L_0x006e
        L_0x002a:
            r1 = r9
            java.lang.Object r3 = r1.L$0
            kotlin.jvm.internal.Ref$ObjectRef r3 = (kotlin.jvm.internal.Ref.ObjectRef) r3
            kotlin.ResultKt.throwOnFailure(r10)     // Catch:{ CorruptionException -> 0x007a }
            r4 = r3
            r3 = r1
            r1 = r10
            goto L_0x0052
        L_0x0036:
            kotlin.ResultKt.throwOnFailure(r10)
            r1 = r9
            kotlin.jvm.internal.Ref$ObjectRef<T> r3 = r1.$newData     // Catch:{ CorruptionException -> 0x007a }
            androidx.datastore.core.DataStoreImpl<T> r4 = r1.this$0     // Catch:{ CorruptionException -> 0x007a }
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5     // Catch:{ CorruptionException -> 0x007a }
            r1.L$0 = r3     // Catch:{ CorruptionException -> 0x007a }
            r1.label = r2     // Catch:{ CorruptionException -> 0x007a }
            java.lang.Object r4 = r4.readDataFromFileOrDefault(r5)     // Catch:{ CorruptionException -> 0x007a }
            if (r4 != r0) goto L_0x004d
            return r0
        L_0x004d:
            r8 = r1
            r1 = r10
            r10 = r4
            r4 = r3
            r3 = r8
        L_0x0052:
            r4.element = r10     // Catch:{ CorruptionException -> 0x0077 }
            kotlin.jvm.internal.Ref$IntRef r10 = r3.$version     // Catch:{ CorruptionException -> 0x0077 }
            androidx.datastore.core.DataStoreImpl<T> r4 = r3.this$0     // Catch:{ CorruptionException -> 0x0077 }
            androidx.datastore.core.InterProcessCoordinator r4 = r4.getCoordinator()     // Catch:{ CorruptionException -> 0x0077 }
            r5 = r3
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5     // Catch:{ CorruptionException -> 0x0077 }
            r3.L$0 = r10     // Catch:{ CorruptionException -> 0x0077 }
            r6 = 2
            r3.label = r6     // Catch:{ CorruptionException -> 0x0077 }
            java.lang.Object r4 = r4.getVersion(r5)     // Catch:{ CorruptionException -> 0x0077 }
            if (r4 != r0) goto L_0x006b
            return r0
        L_0x006b:
            r8 = r4
            r4 = r10
            r10 = r8
        L_0x006e:
            java.lang.Number r10 = (java.lang.Number) r10     // Catch:{ CorruptionException -> 0x0077 }
            int r10 = r10.intValue()     // Catch:{ CorruptionException -> 0x0077 }
            r4.element = r10     // Catch:{ CorruptionException -> 0x0077 }
            goto L_0x009e
        L_0x0077:
            r10 = move-exception
            r10 = r1
            goto L_0x007c
        L_0x007a:
            r3 = move-exception
            r3 = r1
        L_0x007c:
            kotlin.jvm.internal.Ref$IntRef r1 = r3.$version
            androidx.datastore.core.DataStoreImpl<T> r4 = r3.this$0
            kotlin.jvm.internal.Ref$ObjectRef<T> r5 = r3.$newData
            T r5 = r5.element
            r6 = r3
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
            r3.L$0 = r1
            r7 = 3
            r3.label = r7
            java.lang.Object r2 = r4.writeData$datastore_core_release(r5, r2, r6)
            if (r2 != r0) goto L_0x0093
            return r0
        L_0x0093:
            r0 = r10
            r10 = r2
        L_0x0095:
            java.lang.Number r10 = (java.lang.Number) r10
            int r10 = r10.intValue()
            r1.element = r10
            r1 = r0
        L_0x009e:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl$readDataOrHandleCorruption$3.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
