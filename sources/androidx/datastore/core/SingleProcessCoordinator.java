package androidx.datastore.core;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;

@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u000e\u001a\u00020\u000fH@¢\u0006\u0002\u0010\u0010J\u000e\u0010\u0011\u001a\u00020\u000fH@¢\u0006\u0002\u0010\u0010J2\u0010\u0012\u001a\u0002H\u0013\"\u0004\b\u0000\u0010\u00132\u001c\u0010\u0014\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00130\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u00170\u0015H@¢\u0006\u0002\u0010\u0018J8\u0010\u0019\u001a\u0002H\u0013\"\u0004\b\u0000\u0010\u00132\"\u0010\u0014\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020\u001b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00130\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u00170\u001aH@¢\u0006\u0002\u0010\u001cR\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Landroidx/datastore/core/SingleProcessCoordinator;", "Landroidx/datastore/core/InterProcessCoordinator;", "filePath", "", "(Ljava/lang/String;)V", "mutex", "Lkotlinx/coroutines/sync/Mutex;", "updateNotifications", "Lkotlinx/coroutines/flow/Flow;", "", "getUpdateNotifications", "()Lkotlinx/coroutines/flow/Flow;", "version", "Landroidx/datastore/core/AtomicInt;", "getVersion", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "incrementAndGetVersion", "lock", "T", "block", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "tryLock", "Lkotlin/Function2;", "", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "datastore-core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: SingleProcessCoordinator.kt */
public final class SingleProcessCoordinator implements InterProcessCoordinator {
    private final String filePath;
    private final Mutex mutex = MutexKt.Mutex$default(false, 1, (Object) null);
    private final Flow<Unit> updateNotifications = FlowKt.flow(new SingleProcessCoordinator$updateNotifications$1((Continuation<? super SingleProcessCoordinator$updateNotifications$1>) null));
    private final AtomicInt version = new AtomicInt(0);

    public SingleProcessCoordinator(String filePath2) {
        Intrinsics.checkNotNullParameter(filePath2, "filePath");
        this.filePath = filePath2;
    }

    public Flow<Unit> getUpdateNotifications() {
        return this.updateNotifications;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0076 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0077  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> java.lang.Object lock(kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> r9, kotlin.coroutines.Continuation<? super T> r10) {
        /*
            r8 = this;
            boolean r0 = r10 instanceof androidx.datastore.core.SingleProcessCoordinator$lock$1
            if (r0 == 0) goto L_0x0014
            r0 = r10
            androidx.datastore.core.SingleProcessCoordinator$lock$1 r0 = (androidx.datastore.core.SingleProcessCoordinator$lock$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r10 = r0.label
            int r10 = r10 - r2
            r0.label = r10
            goto L_0x0019
        L_0x0014:
            androidx.datastore.core.SingleProcessCoordinator$lock$1 r0 = new androidx.datastore.core.SingleProcessCoordinator$lock$1
            r0.<init>(r8, r10)
        L_0x0019:
            java.lang.Object r10 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x004b;
                case 1: goto L_0x003a;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L_0x002d:
            r9 = 0
            r1 = 0
            java.lang.Object r2 = r0.L$0
            kotlinx.coroutines.sync.Mutex r2 = (kotlinx.coroutines.sync.Mutex) r2
            kotlin.ResultKt.throwOnFailure(r10)     // Catch:{ all -> 0x0038 }
            r4 = r10
            goto L_0x007b
        L_0x0038:
            r1 = move-exception
            goto L_0x0084
        L_0x003a:
            r9 = 0
            r2 = 0
            java.lang.Object r4 = r0.L$1
            kotlinx.coroutines.sync.Mutex r4 = (kotlinx.coroutines.sync.Mutex) r4
            java.lang.Object r5 = r0.L$0
            kotlin.jvm.functions.Function1 r5 = (kotlin.jvm.functions.Function1) r5
            kotlin.ResultKt.throwOnFailure(r10)
            r7 = r4
            r4 = r2
            r2 = r7
            goto L_0x0067
        L_0x004b:
            kotlin.ResultKt.throwOnFailure(r10)
            r2 = r8
            r5 = r9
            kotlinx.coroutines.sync.Mutex r9 = r2.mutex
            r2 = 0
            r4 = 0
            r0.L$0 = r5
            r0.L$1 = r9
            r6 = 1
            r0.label = r6
            java.lang.Object r6 = r9.lock(r2, r0)
            if (r6 != r1) goto L_0x0063
            return r1
        L_0x0063:
            r7 = r2
            r2 = r9
            r9 = r4
            r4 = r7
        L_0x0067:
            r6 = 0
            r0.L$0 = r2     // Catch:{ all -> 0x0082 }
            r0.L$1 = r3     // Catch:{ all -> 0x0082 }
            r3 = 2
            r0.label = r3     // Catch:{ all -> 0x0082 }
            java.lang.Object r3 = r5.invoke(r0)     // Catch:{ all -> 0x0082 }
            if (r3 != r1) goto L_0x0077
            return r1
        L_0x0077:
            r1 = r4
            r4 = r3
            r3 = r1
            r1 = r6
        L_0x007b:
            r2.unlock(r3)
            return r4
        L_0x0082:
            r1 = move-exception
            r3 = r4
        L_0x0084:
            r2.unlock(r3)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.SingleProcessCoordinator.lock(kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0068, code lost:
        if (r5 == false) goto L_0x006e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006a, code lost:
        r3.unlock(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x006e, code lost:
        return r10;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> java.lang.Object tryLock(kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> r10, kotlin.coroutines.Continuation<? super T> r11) {
        /*
            r9 = this;
            boolean r0 = r11 instanceof androidx.datastore.core.SingleProcessCoordinator$tryLock$1
            if (r0 == 0) goto L_0x0014
            r0 = r11
            androidx.datastore.core.SingleProcessCoordinator$tryLock$1 r0 = (androidx.datastore.core.SingleProcessCoordinator$tryLock$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r11 = r0.label
            int r11 = r11 - r2
            r0.label = r11
            goto L_0x0019
        L_0x0014:
            androidx.datastore.core.SingleProcessCoordinator$tryLock$1 r0 = new androidx.datastore.core.SingleProcessCoordinator$tryLock$1
            r0.<init>(r9, r11)
        L_0x0019:
            java.lang.Object r11 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            switch(r2) {
                case 0: goto L_0x003e;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x002c:
            r10 = 0
            r1 = 0
            boolean r2 = r0.Z$0
            java.lang.Object r3 = r0.L$0
            kotlinx.coroutines.sync.Mutex r3 = (kotlinx.coroutines.sync.Mutex) r3
            r4 = 0
            kotlin.ResultKt.throwOnFailure(r11)     // Catch:{ all -> 0x003c }
            r5 = r2
            r2 = r10
            r10 = r11
            goto L_0x0066
        L_0x003c:
            r1 = move-exception
            goto L_0x0072
        L_0x003e:
            kotlin.ResultKt.throwOnFailure(r11)
            r2 = r9
            kotlinx.coroutines.sync.Mutex r3 = r2.mutex
            r4 = 0
            r2 = 0
            boolean r5 = r3.tryLock(r4)
            r6 = r5
            r7 = 0
            r8 = 1
            if (r6 == 0) goto L_0x0053
            r6 = r8
            goto L_0x0054
        L_0x0053:
            r6 = 0
        L_0x0054:
            java.lang.Boolean r6 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r6)     // Catch:{ all -> 0x006f }
            r0.L$0 = r3     // Catch:{ all -> 0x006f }
            r0.Z$0 = r5     // Catch:{ all -> 0x006f }
            r0.label = r8     // Catch:{ all -> 0x006f }
            java.lang.Object r10 = r10.invoke(r6, r0)     // Catch:{ all -> 0x006f }
            if (r10 != r1) goto L_0x0065
            return r1
        L_0x0065:
            r1 = r7
        L_0x0066:
            if (r5 == 0) goto L_0x006d
            r3.unlock(r4)
        L_0x006d:
            return r10
        L_0x006f:
            r1 = move-exception
            r10 = r2
            r2 = r5
        L_0x0072:
            if (r2 == 0) goto L_0x0077
            r3.unlock(r4)
        L_0x0077:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.SingleProcessCoordinator.tryLock(kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public Object getVersion(Continuation<? super Integer> $completion) {
        return Boxing.boxInt(this.version.get());
    }

    public Object incrementAndGetVersion(Continuation<? super Integer> $completion) {
        return Boxing.boxInt(this.version.incrementAndGet());
    }
}
