package androidx.datastore.core;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H@"}, d2 = {"<anonymous>", "Landroidx/datastore/core/Data;", "T", "locked", ""}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "androidx.datastore.core.DataStoreImpl$readDataOrHandleCorruption$2", f = "DataStoreImpl.kt", i = {0, 1}, l = {370, 371}, m = "invokeSuspend", n = {"locked", "data"}, s = {"Z$0", "L$0"})
/* compiled from: DataStoreImpl.kt */
final class DataStoreImpl$readDataOrHandleCorruption$2 extends SuspendLambda implements Function2<Boolean, Continuation<? super Data<T>>, Object> {
    final /* synthetic */ int $preLockVersion;
    Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ DataStoreImpl<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DataStoreImpl$readDataOrHandleCorruption$2(DataStoreImpl<T> dataStoreImpl, int i, Continuation<? super DataStoreImpl$readDataOrHandleCorruption$2> continuation) {
        super(2, continuation);
        this.this$0 = dataStoreImpl;
        this.$preLockVersion = i;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        DataStoreImpl$readDataOrHandleCorruption$2 dataStoreImpl$readDataOrHandleCorruption$2 = new DataStoreImpl$readDataOrHandleCorruption$2(this.this$0, this.$preLockVersion, continuation);
        dataStoreImpl$readDataOrHandleCorruption$2.Z$0 = ((Boolean) obj).booleanValue();
        return dataStoreImpl$readDataOrHandleCorruption$2;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return invoke(((Boolean) obj).booleanValue(), (Continuation) obj2);
    }

    public final Object invoke(boolean z, Continuation<? super Data<T>> continuation) {
        return ((DataStoreImpl$readDataOrHandleCorruption$2) create(Boolean.valueOf(z), continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x006d  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0072  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r8.label
            switch(r1) {
                case 0: goto L_0x0024;
                case 1: goto L_0x001a;
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
            java.lang.Object r1 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r9)
            r2 = r1
            r1 = r9
            goto L_0x005b
        L_0x001a:
            r1 = r8
            boolean r2 = r1.Z$0
            kotlin.ResultKt.throwOnFailure(r9)
            r3 = r2
            r2 = r1
            r1 = r9
            goto L_0x0040
        L_0x0024:
            kotlin.ResultKt.throwOnFailure(r9)
            r1 = r8
            boolean r2 = r1.Z$0
            androidx.datastore.core.DataStoreImpl<T> r3 = r1.this$0
            r4 = r1
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r1.Z$0 = r2
            r5 = 1
            r1.label = r5
            java.lang.Object r3 = r3.readDataFromFileOrDefault(r4)
            if (r3 != r0) goto L_0x003b
            return r0
        L_0x003b:
            r7 = r1
            r1 = r9
            r9 = r3
            r3 = r2
            r2 = r7
        L_0x0040:
            if (r3 == 0) goto L_0x0062
            androidx.datastore.core.DataStoreImpl<T> r4 = r2.this$0
            androidx.datastore.core.InterProcessCoordinator r4 = r4.getCoordinator()
            r5 = r2
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r2.L$0 = r9
            r6 = 2
            r2.label = r6
            java.lang.Object r3 = r4.getVersion(r5)
            if (r3 != r0) goto L_0x0058
            return r0
        L_0x0058:
            r0 = r2
            r2 = r9
            r9 = r3
        L_0x005b:
            java.lang.Number r9 = (java.lang.Number) r9
            int r9 = r9.intValue()
            goto L_0x0068
        L_0x0062:
            int r0 = r2.$preLockVersion
            r7 = r2
            r2 = r9
            r9 = r0
            r0 = r7
        L_0x0068:
            androidx.datastore.core.Data r3 = new androidx.datastore.core.Data
            if (r2 == 0) goto L_0x0072
            int r4 = r2.hashCode()
            goto L_0x0073
        L_0x0072:
            r4 = 0
        L_0x0073:
            r3.<init>(r2, r4, r9)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl$readDataOrHandleCorruption$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
