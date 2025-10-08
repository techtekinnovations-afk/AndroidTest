package androidx.datastore.core;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;

@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H@"}, d2 = {"<anonymous>", "", "T", "Landroidx/datastore/core/WriteScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "androidx.datastore.core.DataStoreImpl$writeData$2", f = "DataStoreImpl.kt", i = {0}, l = {352, 353}, m = "invokeSuspend", n = {"$this$writeScope"}, s = {"L$0"})
/* compiled from: DataStoreImpl.kt */
final class DataStoreImpl$writeData$2 extends SuspendLambda implements Function2<WriteScope<T>, Continuation<? super Unit>, Object> {
    final /* synthetic */ T $newData;
    final /* synthetic */ Ref.IntRef $newVersion;
    final /* synthetic */ boolean $updateCache;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ DataStoreImpl<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DataStoreImpl$writeData$2(Ref.IntRef intRef, DataStoreImpl<T> dataStoreImpl, T t, boolean z, Continuation<? super DataStoreImpl$writeData$2> continuation) {
        super(2, continuation);
        this.$newVersion = intRef;
        this.this$0 = dataStoreImpl;
        this.$newData = t;
        this.$updateCache = z;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        DataStoreImpl$writeData$2 dataStoreImpl$writeData$2 = new DataStoreImpl$writeData$2(this.$newVersion, this.this$0, this.$newData, this.$updateCache, continuation);
        dataStoreImpl$writeData$2.L$0 = obj;
        return dataStoreImpl$writeData$2;
    }

    public final Object invoke(WriteScope<T> writeScope, Continuation<? super Unit> continuation) {
        return ((DataStoreImpl$writeData$2) create(writeScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: androidx.datastore.core.WriteScope} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r8.label
            switch(r1) {
                case 0: goto L_0x0027;
                case 1: goto L_0x0016;
                case 2: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L_0x0011:
            r0 = r8
            kotlin.ResultKt.throwOnFailure(r9)
            goto L_0x006d
        L_0x0016:
            r1 = r8
            java.lang.Object r2 = r1.L$1
            kotlin.jvm.internal.Ref$IntRef r2 = (kotlin.jvm.internal.Ref.IntRef) r2
            java.lang.Object r3 = r1.L$0
            androidx.datastore.core.WriteScope r3 = (androidx.datastore.core.WriteScope) r3
            kotlin.ResultKt.throwOnFailure(r9)
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r9
            goto L_0x004f
        L_0x0027:
            kotlin.ResultKt.throwOnFailure(r9)
            r1 = r8
            java.lang.Object r2 = r1.L$0
            r3 = r2
            androidx.datastore.core.WriteScope r3 = (androidx.datastore.core.WriteScope) r3
            kotlin.jvm.internal.Ref$IntRef r2 = r1.$newVersion
            androidx.datastore.core.DataStoreImpl<T> r4 = r1.this$0
            androidx.datastore.core.InterProcessCoordinator r4 = r4.getCoordinator()
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r1.L$0 = r3
            r1.L$1 = r2
            r6 = 1
            r1.label = r6
            java.lang.Object r4 = r4.incrementAndGetVersion(r5)
            if (r4 != r0) goto L_0x0049
            return r0
        L_0x0049:
            r7 = r1
            r1 = r9
            r9 = r4
            r4 = r3
            r3 = r2
            r2 = r7
        L_0x004f:
            java.lang.Number r9 = (java.lang.Number) r9
            int r9 = r9.intValue()
            r3.element = r9
            T r9 = r2.$newData
            r3 = r2
            kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
            r5 = 0
            r2.L$0 = r5
            r2.L$1 = r5
            r5 = 2
            r2.label = r5
            java.lang.Object r9 = r4.writeData(r9, r3)
            if (r9 != r0) goto L_0x006b
            return r0
        L_0x006b:
            r9 = r1
            r0 = r2
        L_0x006d:
            boolean r1 = r0.$updateCache
            if (r1 == 0) goto L_0x0091
            androidx.datastore.core.DataStoreImpl<T> r1 = r0.this$0
            androidx.datastore.core.DataStoreInMemoryCache r1 = r1.inMemoryCache
            androidx.datastore.core.Data r2 = new androidx.datastore.core.Data
            T r3 = r0.$newData
            T r4 = r0.$newData
            if (r4 == 0) goto L_0x0084
            int r4 = r4.hashCode()
            goto L_0x0085
        L_0x0084:
            r4 = 0
        L_0x0085:
            kotlin.jvm.internal.Ref$IntRef r5 = r0.$newVersion
            int r5 = r5.element
            r2.<init>(r3, r4, r5)
            androidx.datastore.core.State r2 = (androidx.datastore.core.State) r2
            r1.tryUpdate(r2)
        L_0x0091:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl$writeData$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
