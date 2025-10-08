package androidx.datastore.core;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b \u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\u0005H@¢\u0006\u0002\u0010\tJ\u000e\u0010\n\u001a\u00020\u0005H¤@¢\u0006\u0002\u0010\tJ\u000e\u0010\u000b\u001a\u00020\u0005H@¢\u0006\u0002\u0010\tR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Landroidx/datastore/core/RunOnce;", "", "()V", "didRun", "Lkotlinx/coroutines/CompletableDeferred;", "", "runMutex", "Lkotlinx/coroutines/sync/Mutex;", "awaitComplete", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "doRun", "runIfNeeded", "datastore-core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: DataStoreImpl.kt */
public abstract class RunOnce {
    private final CompletableDeferred<Unit> didRun = CompletableDeferredKt.CompletableDeferred$default((Job) null, 1, (Object) null);
    private final Mutex runMutex = MutexKt.Mutex$default(false, 1, (Object) null);

    /* access modifiers changed from: protected */
    public abstract Object doRun(Continuation<? super Unit> continuation);

    public final Object awaitComplete(Continuation<? super Unit> $completion) {
        Object await = this.didRun.await($completion);
        return await == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? await : Unit.INSTANCE;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x007f A[Catch:{ all -> 0x00ab }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0085 A[SYNTHETIC, Splitter:B:30:0x0085] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object runIfNeeded(kotlin.coroutines.Continuation<? super kotlin.Unit> r10) {
        /*
            r9 = this;
            boolean r0 = r10 instanceof androidx.datastore.core.RunOnce$runIfNeeded$1
            if (r0 == 0) goto L_0x0014
            r0 = r10
            androidx.datastore.core.RunOnce$runIfNeeded$1 r0 = (androidx.datastore.core.RunOnce$runIfNeeded$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r10 = r0.label
            int r10 = r10 - r2
            r0.label = r10
            goto L_0x0019
        L_0x0014:
            androidx.datastore.core.RunOnce$runIfNeeded$1 r0 = new androidx.datastore.core.RunOnce$runIfNeeded$1
            r0.<init>(r9, r10)
        L_0x0019:
            java.lang.Object r10 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            switch(r2) {
                case 0: goto L_0x004f;
                case 1: goto L_0x003e;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L_0x002c:
            r1 = 0
            r2 = 0
            r3 = 0
            java.lang.Object r4 = r0.L$1
            kotlinx.coroutines.sync.Mutex r4 = (kotlinx.coroutines.sync.Mutex) r4
            java.lang.Object r5 = r0.L$0
            androidx.datastore.core.RunOnce r5 = (androidx.datastore.core.RunOnce) r5
            kotlin.ResultKt.throwOnFailure(r10)     // Catch:{ all -> 0x003b }
            goto L_0x0097
        L_0x003b:
            r1 = move-exception
            goto L_0x00af
        L_0x003e:
            r2 = 0
            java.lang.Object r3 = r0.L$1
            kotlinx.coroutines.sync.Mutex r3 = (kotlinx.coroutines.sync.Mutex) r3
            java.lang.Object r4 = r0.L$0
            androidx.datastore.core.RunOnce r4 = (androidx.datastore.core.RunOnce) r4
            kotlin.ResultKt.throwOnFailure(r10)
            r5 = 0
            r8 = r5
            r5 = r4
            r4 = r8
            goto L_0x0075
        L_0x004f:
            kotlin.ResultKt.throwOnFailure(r10)
            r2 = r9
            kotlinx.coroutines.CompletableDeferred<kotlin.Unit> r3 = r2.didRun
            boolean r3 = r3.isCompleted()
            if (r3 == 0) goto L_0x005e
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        L_0x005e:
            kotlinx.coroutines.sync.Mutex r3 = r2.runMutex
            r5 = 0
            r4 = 0
            r0.L$0 = r2
            r0.L$1 = r3
            r6 = 1
            r0.label = r6
            java.lang.Object r6 = r3.lock(r5, r0)
            if (r6 != r1) goto L_0x0071
            return r1
        L_0x0071:
            r8 = r5
            r5 = r2
            r2 = r4
            r4 = r8
        L_0x0075:
            r6 = 0
            kotlinx.coroutines.CompletableDeferred<kotlin.Unit> r7 = r5.didRun     // Catch:{ all -> 0x00ab }
            boolean r7 = r7.isCompleted()     // Catch:{ all -> 0x00ab }
            if (r7 == 0) goto L_0x0085
            kotlin.Unit r1 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00ab }
            r3.unlock(r4)
            return r1
        L_0x0085:
            r0.L$0 = r5     // Catch:{ all -> 0x00a6 }
            r0.L$1 = r3     // Catch:{ all -> 0x00a6 }
            r7 = 2
            r0.label = r7     // Catch:{ all -> 0x00a6 }
            java.lang.Object r7 = r5.doRun(r0)     // Catch:{ all -> 0x00a6 }
            if (r7 != r1) goto L_0x0093
            return r1
        L_0x0093:
            r1 = r4
            r4 = r3
            r3 = r1
            r1 = r6
        L_0x0097:
            kotlinx.coroutines.CompletableDeferred<kotlin.Unit> r6 = r5.didRun     // Catch:{ all -> 0x003b }
            kotlin.Unit r7 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x003b }
            r6.complete(r7)     // Catch:{ all -> 0x003b }
            r4.unlock(r3)
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        L_0x00a6:
            r1 = move-exception
            r8 = r4
            r4 = r3
            r3 = r8
            goto L_0x00af
        L_0x00ab:
            r1 = move-exception
            r8 = r4
            r4 = r3
            r3 = r8
        L_0x00af:
            r4.unlock(r3)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.RunOnce.runIfNeeded(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
