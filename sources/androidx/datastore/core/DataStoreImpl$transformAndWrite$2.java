package androidx.datastore.core;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

@Metadata(d1 = {"\u0000\u0004\n\u0002\b\u0002\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001H@"}, d2 = {"<anonymous>", "T"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "androidx.datastore.core.DataStoreImpl$transformAndWrite$2", f = "DataStoreImpl.kt", i = {1, 2}, l = {330, 331, 337}, m = "invokeSuspend", n = {"curData", "newData"}, s = {"L$0", "L$0"})
/* compiled from: DataStoreImpl.kt */
final class DataStoreImpl$transformAndWrite$2 extends SuspendLambda implements Function1<Continuation<? super T>, Object> {
    final /* synthetic */ CoroutineContext $callerContext;
    final /* synthetic */ Function2<T, Continuation<? super T>, Object> $transform;
    Object L$0;
    int label;
    final /* synthetic */ DataStoreImpl<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DataStoreImpl$transformAndWrite$2(DataStoreImpl<T> dataStoreImpl, CoroutineContext coroutineContext, Function2<? super T, ? super Continuation<? super T>, ? extends Object> function2, Continuation<? super DataStoreImpl$transformAndWrite$2> continuation) {
        super(1, continuation);
        this.this$0 = dataStoreImpl;
        this.$callerContext = coroutineContext;
        this.$transform = function2;
    }

    public final Continuation<Unit> create(Continuation<?> continuation) {
        return new DataStoreImpl$transformAndWrite$2(this.this$0, this.$callerContext, this.$transform, continuation);
    }

    public final Object invoke(Continuation<? super T> continuation) {
        return ((DataStoreImpl$transformAndWrite$2) create(continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0043, code lost:
        r10 = (androidx.datastore.core.Data) r10;
        r3.L$0 = r10;
        r3.label = 2;
        r4 = kotlinx.coroutines.BuildersKt.withContext(r3.$callerContext, new androidx.datastore.core.DataStoreImpl$transformAndWrite$2$newData$1(r3.$transform, r10, (kotlin.coroutines.Continuation<? super androidx.datastore.core.DataStoreImpl$transformAndWrite$2$newData$1>) null), r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x005d, code lost:
        if (r4 != r0) goto L_0x0060;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x005f, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0060, code lost:
        r8 = r4;
        r4 = r10;
        r10 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0063, code lost:
        r4.checkHashCode();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x006f, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual(r4.getValue(), r10) != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0071, code lost:
        r3.L$0 = r10;
        r3.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x007f, code lost:
        if (r3.this$0.writeData$datastore_core_release(r10, true, r3) != r0) goto L_0x0082;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0081, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0082, code lost:
        r0 = r1;
        r1 = r10;
        r10 = r0;
        r0 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0086, code lost:
        r3 = r1;
        r1 = r10;
        r10 = r3;
        r3 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        return r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return r10;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r9.label
            r2 = 1
            switch(r1) {
                case 0: goto L_0x002d;
                case 1: goto L_0x0026;
                case 2: goto L_0x001a;
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
            kotlin.ResultKt.throwOnFailure(r10)
            goto L_0x0086
        L_0x001a:
            r1 = r9
            java.lang.Object r3 = r1.L$0
            androidx.datastore.core.Data r3 = (androidx.datastore.core.Data) r3
            kotlin.ResultKt.throwOnFailure(r10)
            r4 = r3
            r3 = r1
            r1 = r10
            goto L_0x0063
        L_0x0026:
            r1 = r9
            kotlin.ResultKt.throwOnFailure(r10)
            r3 = r1
            r1 = r10
            goto L_0x0043
        L_0x002d:
            kotlin.ResultKt.throwOnFailure(r10)
            r1 = r9
            androidx.datastore.core.DataStoreImpl<T> r3 = r1.this$0
            r4 = r1
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r1.label = r2
            java.lang.Object r3 = r3.readDataOrHandleCorruption(r2, r4)
            if (r3 != r0) goto L_0x003f
            return r0
        L_0x003f:
            r8 = r1
            r1 = r10
            r10 = r3
            r3 = r8
        L_0x0043:
            androidx.datastore.core.Data r10 = (androidx.datastore.core.Data) r10
            kotlin.coroutines.CoroutineContext r4 = r3.$callerContext
            androidx.datastore.core.DataStoreImpl$transformAndWrite$2$newData$1 r5 = new androidx.datastore.core.DataStoreImpl$transformAndWrite$2$newData$1
            kotlin.jvm.functions.Function2<T, kotlin.coroutines.Continuation<? super T>, java.lang.Object> r6 = r3.$transform
            r7 = 0
            r5.<init>(r6, r10, r7)
            kotlin.jvm.functions.Function2 r5 = (kotlin.jvm.functions.Function2) r5
            r6 = r3
            kotlin.coroutines.Continuation r6 = (kotlin.coroutines.Continuation) r6
            r3.L$0 = r10
            r7 = 2
            r3.label = r7
            java.lang.Object r4 = kotlinx.coroutines.BuildersKt.withContext(r4, r5, r6)
            if (r4 != r0) goto L_0x0060
            return r0
        L_0x0060:
            r8 = r4
            r4 = r10
            r10 = r8
        L_0x0063:
            r4.checkHashCode()
            java.lang.Object r5 = r4.getValue()
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r5, (java.lang.Object) r10)
            if (r5 != 0) goto L_0x008a
            androidx.datastore.core.DataStoreImpl<T> r4 = r3.this$0
            r5 = r3
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r3.L$0 = r10
            r6 = 3
            r3.label = r6
            java.lang.Object r2 = r4.writeData$datastore_core_release(r10, r2, r5)
            if (r2 != r0) goto L_0x0082
            return r0
        L_0x0082:
            r0 = r1
            r1 = r10
            r10 = r0
            r0 = r3
        L_0x0086:
            r3 = r1
            r1 = r10
            r10 = r3
            r3 = r0
        L_0x008a:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl$transformAndWrite$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
