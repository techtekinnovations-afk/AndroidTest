package androidx.datastore.core;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0010\u0000\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0002\u0012\u0004\u0012\u00020\u00040\u0001\"\u0004\b\u0000\u0010\u00032\u0006\u0010\u0005\u001a\u00020\u0004H@"}, d2 = {"<anonymous>", "Lkotlin/Pair;", "Landroidx/datastore/core/State;", "T", "", "locked"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "androidx.datastore.core.DataStoreImpl$readDataAndUpdateCache$4", f = "DataStoreImpl.kt", i = {0, 1}, l = {306, 309}, m = "invokeSuspend", n = {"locked", "locked"}, s = {"Z$0", "Z$0"})
/* compiled from: DataStoreImpl.kt */
final class DataStoreImpl$readDataAndUpdateCache$4 extends SuspendLambda implements Function2<Boolean, Continuation<? super Pair<? extends State<T>, ? extends Boolean>>, Object> {
    final /* synthetic */ int $cachedVersion;
    Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ DataStoreImpl<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DataStoreImpl$readDataAndUpdateCache$4(DataStoreImpl<T> dataStoreImpl, int i, Continuation<? super DataStoreImpl$readDataAndUpdateCache$4> continuation) {
        super(2, continuation);
        this.this$0 = dataStoreImpl;
        this.$cachedVersion = i;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        DataStoreImpl$readDataAndUpdateCache$4 dataStoreImpl$readDataAndUpdateCache$4 = new DataStoreImpl$readDataAndUpdateCache$4(this.this$0, this.$cachedVersion, continuation);
        dataStoreImpl$readDataAndUpdateCache$4.Z$0 = ((Boolean) obj).booleanValue();
        return dataStoreImpl$readDataAndUpdateCache$4;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return invoke(((Boolean) obj).booleanValue(), (Continuation) obj2);
    }

    public final Object invoke(boolean z, Continuation<? super Pair<? extends State<T>, Boolean>> continuation) {
        return ((DataStoreImpl$readDataAndUpdateCache$4) create(Boolean.valueOf(z), continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r11 = (androidx.datastore.core.State) r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0051, code lost:
        r11 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0052, code lost:
        r9 = r5;
        r5 = r11;
        r11 = r1;
        r1 = r4;
        r4 = r9;
     */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0092  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r10.label
            r2 = 0
            r3 = 1
            switch(r1) {
                case 0: goto L_0x002d;
                case 1: goto L_0x0023;
                case 2: goto L_0x0013;
                default: goto L_0x000b;
            }
        L_0x000b:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r0)
            throw r11
        L_0x0013:
            r0 = r10
            boolean r1 = r0.Z$0
            java.lang.Object r4 = r0.L$0
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            kotlin.ResultKt.throwOnFailure(r11)
            r5 = r4
            r4 = r1
            r1 = r0
            r0 = r11
            goto L_0x0075
        L_0x0023:
            r1 = r10
            boolean r4 = r1.Z$0
            kotlin.ResultKt.throwOnFailure(r11)     // Catch:{ all -> 0x0058 }
            r5 = r4
            r4 = r1
            r1 = r11
            goto L_0x004e
        L_0x002d:
            kotlin.ResultKt.throwOnFailure(r11)
            r1 = r10
            boolean r4 = r1.Z$0
            androidx.datastore.core.DataStoreImpl<T> r5 = r1.this$0     // Catch:{ all -> 0x0058 }
            if (r4 == 0) goto L_0x003a
            r6 = r3
            goto L_0x003b
        L_0x003a:
            r6 = r2
        L_0x003b:
            r7 = r1
            kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7     // Catch:{ all -> 0x0058 }
            r1.Z$0 = r4     // Catch:{ all -> 0x0058 }
            r1.label = r3     // Catch:{ all -> 0x0058 }
            java.lang.Object r5 = r5.readDataOrHandleCorruption(r6, r7)     // Catch:{ all -> 0x0058 }
            if (r5 != r0) goto L_0x0049
            return r0
        L_0x0049:
            r9 = r1
            r1 = r11
            r11 = r5
            r5 = r4
            r4 = r9
        L_0x004e:
            androidx.datastore.core.State r11 = (androidx.datastore.core.State) r11     // Catch:{ all -> 0x0051 }
            goto L_0x0090
        L_0x0051:
            r11 = move-exception
            r9 = r5
            r5 = r11
            r11 = r1
            r1 = r4
            r4 = r9
            goto L_0x0059
        L_0x0058:
            r5 = move-exception
        L_0x0059:
            if (r4 == 0) goto L_0x0080
            androidx.datastore.core.DataStoreImpl<T> r6 = r1.this$0
            androidx.datastore.core.InterProcessCoordinator r6 = r6.getCoordinator()
            r7 = r1
            kotlin.coroutines.Continuation r7 = (kotlin.coroutines.Continuation) r7
            r1.L$0 = r5
            r1.Z$0 = r4
            r8 = 2
            r1.label = r8
            java.lang.Object r6 = r6.getVersion(r7)
            if (r6 != r0) goto L_0x0073
            return r0
        L_0x0073:
            r0 = r11
            r11 = r6
        L_0x0075:
            java.lang.Number r11 = (java.lang.Number) r11
            int r11 = r11.intValue()
            r9 = r1
            r1 = r0
            r0 = r4
            r4 = r9
            goto L_0x0087
        L_0x0080:
            int r0 = r1.$cachedVersion
            r9 = r1
            r1 = r11
            r11 = r0
            r0 = r4
            r4 = r9
        L_0x0087:
            androidx.datastore.core.ReadException r6 = new androidx.datastore.core.ReadException
            r6.<init>(r5, r11)
            r11 = r6
            androidx.datastore.core.State r11 = (androidx.datastore.core.State) r11
            r5 = r0
        L_0x0090:
            if (r5 == 0) goto L_0x0094
            r2 = r3
        L_0x0094:
            java.lang.Boolean r0 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r2)
            kotlin.Pair r11 = kotlin.TuplesKt.to(r11, r0)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl$readDataAndUpdateCache$4.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
