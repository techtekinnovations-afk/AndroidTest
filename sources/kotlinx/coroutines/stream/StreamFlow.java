package kotlinx.coroutines.stream;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.stream.Stream;
import kotlin.Metadata;
import kotlinx.coroutines.flow.Flow;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0015\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\u001c\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\fH@¢\u0006\u0002\u0010\rR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0004¢\u0006\u0002\n\u0000R\t\u0010\u0007\u001a\u00020\bX\u0004¨\u0006\u000e"}, d2 = {"Lkotlinx/coroutines/stream/StreamFlow;", "T", "Lkotlinx/coroutines/flow/Flow;", "stream", "Ljava/util/stream/Stream;", "<init>", "(Ljava/util/stream/Stream;)V", "consumed", "Lkotlinx/atomicfu/AtomicBoolean;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: Stream.kt */
final class StreamFlow<T> implements Flow<T> {
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicIntegerFieldUpdater consumed$volatile$FU = AtomicIntegerFieldUpdater.newUpdater(StreamFlow.class, "consumed$volatile");
    private volatile /* synthetic */ int consumed$volatile = 0;
    private final Stream<T> stream;

    private final /* synthetic */ int getConsumed$volatile() {
        return this.consumed$volatile;
    }

    private final /* synthetic */ void setConsumed$volatile(int i) {
        this.consumed$volatile = i;
    }

    public StreamFlow(Stream<T> stream2) {
        this.stream = stream2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x005c A[Catch:{ all -> 0x0079 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object collect(kotlinx.coroutines.flow.FlowCollector<? super T> r10, kotlin.coroutines.Continuation<? super kotlin.Unit> r11) {
        /*
            r9 = this;
            boolean r0 = r11 instanceof kotlinx.coroutines.stream.StreamFlow$collect$1
            if (r0 == 0) goto L_0x0014
            r0 = r11
            kotlinx.coroutines.stream.StreamFlow$collect$1 r0 = (kotlinx.coroutines.stream.StreamFlow$collect$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.stream.StreamFlow$collect$1 r0 = new kotlinx.coroutines.stream.StreamFlow$collect$1
            r0.<init>(r9, r11)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 1
            switch(r3) {
                case 0: goto L_0x003d;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L_0x002d:
            java.lang.Object r10 = r0.L$2
            java.util.Iterator r10 = (java.util.Iterator) r10
            java.lang.Object r3 = r0.L$1
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
            java.lang.Object r5 = r0.L$0
            kotlinx.coroutines.stream.StreamFlow r5 = (kotlinx.coroutines.stream.StreamFlow) r5
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0079 }
            goto L_0x006f
        L_0x003d:
            kotlin.ResultKt.throwOnFailure(r1)
            r5 = r9
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r3 = consumed$volatile$FU
            r6 = 0
            boolean r3 = r3.compareAndSet(r5, r6, r4)
            if (r3 == 0) goto L_0x0080
            java.util.stream.Stream<T> r3 = r5.stream     // Catch:{ all -> 0x0079 }
            java.util.Iterator r3 = r3.iterator()     // Catch:{ all -> 0x0079 }
            r8 = r3
            r3 = r10
            r10 = r8
        L_0x0056:
            boolean r6 = r10.hasNext()     // Catch:{ all -> 0x0079 }
            if (r6 == 0) goto L_0x0070
            java.lang.Object r6 = r10.next()     // Catch:{ all -> 0x0079 }
            r0.L$0 = r5     // Catch:{ all -> 0x0079 }
            r0.L$1 = r3     // Catch:{ all -> 0x0079 }
            r0.L$2 = r10     // Catch:{ all -> 0x0079 }
            r0.label = r4     // Catch:{ all -> 0x0079 }
            java.lang.Object r7 = r3.emit(r6, r0)     // Catch:{ all -> 0x0079 }
            if (r7 != r2) goto L_0x006f
            return r2
        L_0x006f:
            goto L_0x0056
        L_0x0070:
            java.util.stream.Stream<T> r10 = r5.stream
            r10.close()
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        L_0x0079:
            r10 = move-exception
            java.util.stream.Stream<T> r2 = r5.stream
            r2.close()
            throw r10
        L_0x0080:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r2 = "Stream.consumeAsFlow can be collected only once"
            java.lang.String r2 = r2.toString()
            r10.<init>(r2)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.stream.StreamFlow.collect(kotlinx.coroutines.flow.FlowCollector, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
