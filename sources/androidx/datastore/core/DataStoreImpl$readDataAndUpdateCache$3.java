package androidx.datastore.core;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;

@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\u0010\u0000\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0002\u0012\u0004\u0012\u00020\u00040\u0001\"\u0004\b\u0000\u0010\u0003H@"}, d2 = {"<anonymous>", "Lkotlin/Pair;", "Landroidx/datastore/core/State;", "T", ""}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "androidx.datastore.core.DataStoreImpl$readDataAndUpdateCache$3", f = "DataStoreImpl.kt", i = {}, l = {298, 300}, m = "invokeSuspend", n = {}, s = {})
/* compiled from: DataStoreImpl.kt */
final class DataStoreImpl$readDataAndUpdateCache$3 extends SuspendLambda implements Function1<Continuation<? super Pair<? extends State<T>, ? extends Boolean>>, Object> {
    Object L$0;
    int label;
    final /* synthetic */ DataStoreImpl<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DataStoreImpl$readDataAndUpdateCache$3(DataStoreImpl<T> dataStoreImpl, Continuation<? super DataStoreImpl$readDataAndUpdateCache$3> continuation) {
        super(1, continuation);
        this.this$0 = dataStoreImpl;
    }

    public final Continuation<Unit> create(Continuation<?> continuation) {
        return new DataStoreImpl$readDataAndUpdateCache$3(this.this$0, continuation);
    }

    public final Object invoke(Continuation<? super Pair<? extends State<T>, Boolean>> continuation) {
        return ((DataStoreImpl$readDataAndUpdateCache$3) create(continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        r9 = (androidx.datastore.core.State) r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0041, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0042, code lost:
        r7 = r3;
        r3 = r9;
        r9 = r1;
        r1 = r7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r8.label
            r2 = 1
            switch(r1) {
                case 0: goto L_0x0027;
                case 1: goto L_0x001e;
                case 2: goto L_0x0012;
                default: goto L_0x000a;
            }
        L_0x000a:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L_0x0012:
            r0 = r8
            java.lang.Object r1 = r0.L$0
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            kotlin.ResultKt.throwOnFailure(r9)
            r3 = r1
            r1 = r0
            r0 = r9
            goto L_0x005d
        L_0x001e:
            r1 = r8
            kotlin.ResultKt.throwOnFailure(r9)     // Catch:{ all -> 0x0025 }
            r3 = r1
            r1 = r9
            goto L_0x003e
        L_0x0025:
            r3 = move-exception
            goto L_0x0046
        L_0x0027:
            kotlin.ResultKt.throwOnFailure(r9)
            r1 = r8
            androidx.datastore.core.DataStoreImpl<T> r3 = r1.this$0     // Catch:{ all -> 0x0025 }
            r4 = r1
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4     // Catch:{ all -> 0x0025 }
            r1.label = r2     // Catch:{ all -> 0x0025 }
            java.lang.Object r3 = r3.readDataOrHandleCorruption(r2, r4)     // Catch:{ all -> 0x0025 }
            if (r3 != r0) goto L_0x003a
            return r0
        L_0x003a:
            r7 = r1
            r1 = r9
            r9 = r3
            r3 = r7
        L_0x003e:
            androidx.datastore.core.State r9 = (androidx.datastore.core.State) r9     // Catch:{ all -> 0x0041 }
            goto L_0x006d
        L_0x0041:
            r9 = move-exception
            r7 = r3
            r3 = r9
            r9 = r1
            r1 = r7
        L_0x0046:
            androidx.datastore.core.DataStoreImpl<T> r4 = r1.this$0
            androidx.datastore.core.InterProcessCoordinator r4 = r4.getCoordinator()
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r1.L$0 = r3
            r6 = 2
            r1.label = r6
            java.lang.Object r4 = r4.getVersion(r5)
            if (r4 != r0) goto L_0x005b
            return r0
        L_0x005b:
            r0 = r9
            r9 = r4
        L_0x005d:
            java.lang.Number r9 = (java.lang.Number) r9
            int r9 = r9.intValue()
            androidx.datastore.core.ReadException r4 = new androidx.datastore.core.ReadException
            r4.<init>(r3, r9)
            r9 = r4
            androidx.datastore.core.State r9 = (androidx.datastore.core.State) r9
            r3 = r1
            r1 = r0
        L_0x006d:
            java.lang.Boolean r0 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r2)
            kotlin.Pair r9 = kotlin.TuplesKt.to(r9, r0)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl$readDataAndUpdateCache$3.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
