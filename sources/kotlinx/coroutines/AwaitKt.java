package kotlinx.coroutines;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;

@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a:\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u001e\u0010\u0003\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0004\"\b\u0012\u0004\u0012\u0002H\u00020\u0005H@¢\u0006\u0002\u0010\u0006\u001a*\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0007H@¢\u0006\u0002\u0010\b\u001a\"\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0\u0004\"\u00020\fH@¢\u0006\u0002\u0010\r\u001a\u0018\u0010\t\u001a\u00020\n*\b\u0012\u0004\u0012\u00020\f0\u0007H@¢\u0006\u0002\u0010\b¨\u0006\u000e"}, d2 = {"awaitAll", "", "T", "deferreds", "", "Lkotlinx/coroutines/Deferred;", "([Lkotlinx/coroutines/Deferred;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "", "(Ljava/util/Collection;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "joinAll", "", "jobs", "Lkotlinx/coroutines/Job;", "([Lkotlinx/coroutines/Job;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: Await.kt */
public final class AwaitKt {
    public static final <T> Object awaitAll(Deferred<? extends T>[] deferreds, Continuation<? super List<? extends T>> $completion) {
        return deferreds.length == 0 ? CollectionsKt.emptyList() : new AwaitAll(deferreds).await($completion);
    }

    public static final <T> Object awaitAll(Collection<? extends Deferred<? extends T>> $this$awaitAll, Continuation<? super List<? extends T>> $completion) {
        return $this$awaitAll.isEmpty() ? CollectionsKt.emptyList() : new AwaitAll((Deferred[]) $this$awaitAll.toArray(new Deferred[0])).await($completion);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object joinAll(kotlinx.coroutines.Job[] r9, kotlin.coroutines.Continuation<? super kotlin.Unit> r10) {
        /*
            boolean r0 = r10 instanceof kotlinx.coroutines.AwaitKt$joinAll$1
            if (r0 == 0) goto L_0x0014
            r0 = r10
            kotlinx.coroutines.AwaitKt$joinAll$1 r0 = (kotlinx.coroutines.AwaitKt$joinAll$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.AwaitKt$joinAll$1 r0 = new kotlinx.coroutines.AwaitKt$joinAll$1
            r0.<init>(r10)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 1
            switch(r3) {
                case 0: goto L_0x003b;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r0)
            throw r9
        L_0x002d:
            r9 = 0
            r3 = 0
            int r5 = r0.I$1
            int r6 = r0.I$0
            java.lang.Object r7 = r0.L$0
            kotlinx.coroutines.Job[] r7 = (kotlinx.coroutines.Job[]) r7
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x0058
        L_0x003b:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = 0
            int r5 = r9.length
            r6 = 0
            r7 = r9
            r9 = r3
        L_0x0043:
            if (r6 >= r5) goto L_0x005a
            r3 = r7[r6]
            r8 = 0
            r0.L$0 = r7
            r0.I$0 = r6
            r0.I$1 = r5
            r0.label = r4
            java.lang.Object r3 = r3.join(r0)
            if (r3 != r2) goto L_0x0057
            return r2
        L_0x0057:
            r3 = r8
        L_0x0058:
            int r6 = r6 + r4
            goto L_0x0043
        L_0x005a:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.AwaitKt.joinAll(kotlinx.coroutines.Job[], kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object joinAll(java.util.Collection<? extends kotlinx.coroutines.Job> r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.AwaitKt$joinAll$3
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.AwaitKt$joinAll$3 r0 = (kotlinx.coroutines.AwaitKt$joinAll$3) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.AwaitKt$joinAll$3 r0 = new kotlinx.coroutines.AwaitKt$joinAll$3
            r0.<init>(r8)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0036;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r0)
            throw r7
        L_0x002c:
            r7 = 0
            r3 = 0
            java.lang.Object r4 = r0.L$0
            java.util.Iterator r4 = (java.util.Iterator) r4
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x005b
        L_0x0036:
            kotlin.ResultKt.throwOnFailure(r1)
            java.lang.Iterable r7 = (java.lang.Iterable) r7
            r3 = 0
            java.util.Iterator r4 = r7.iterator()
            r7 = r3
        L_0x0041:
            boolean r3 = r4.hasNext()
            if (r3 == 0) goto L_0x005c
            java.lang.Object r3 = r4.next()
            kotlinx.coroutines.Job r3 = (kotlinx.coroutines.Job) r3
            r5 = 0
            r0.L$0 = r4
            r6 = 1
            r0.label = r6
            java.lang.Object r3 = r3.join(r0)
            if (r3 != r2) goto L_0x005a
            return r2
        L_0x005a:
            r3 = r5
        L_0x005b:
            goto L_0x0041
        L_0x005c:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.AwaitKt.joinAll(java.util.Collection, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
