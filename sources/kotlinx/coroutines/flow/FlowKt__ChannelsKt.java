package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.internal.ChannelFlowKt;

@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a,\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005H@¢\u0006\u0002\u0010\u0006\u001a6\u0010\u0007\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\b\u001a\u00020\tH@¢\u0006\u0004\b\n\u0010\u000b\u001a\u001c\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\r\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0005\u001a\u001c\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\r\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0005\u001a$\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011¨\u0006\u0012"}, d2 = {"emitAll", "", "T", "Lkotlinx/coroutines/flow/FlowCollector;", "channel", "Lkotlinx/coroutines/channels/ReceiveChannel;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "emitAllImpl", "consume", "", "emitAllImpl$FlowKt__ChannelsKt", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlinx/coroutines/channels/ReceiveChannel;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "receiveAsFlow", "Lkotlinx/coroutines/flow/Flow;", "consumeAsFlow", "produceIn", "scope", "Lkotlinx/coroutines/CoroutineScope;", "kotlinx-coroutines-core"}, k = 5, mv = {2, 0, 0}, xi = 48, xs = "kotlinx/coroutines/flow/FlowKt")
/* compiled from: Channels.kt */
final /* synthetic */ class FlowKt__ChannelsKt {
    public static final <T> Object emitAll(FlowCollector<? super T> $this$emitAll, ReceiveChannel<? extends T> channel, Continuation<? super Unit> $completion) {
        Object emitAllImpl$FlowKt__ChannelsKt = emitAllImpl$FlowKt__ChannelsKt($this$emitAll, channel, true, $completion);
        return emitAllImpl$FlowKt__ChannelsKt == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? emitAllImpl$FlowKt__ChannelsKt : Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006c, code lost:
        r0.L$0 = r8;
        r0.L$1 = r3;
        r0.L$2 = r4;
        r0.Z$0 = r10;
        r0.label = 1;
        r5 = r4.hasNext(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x007b, code lost:
        if (r5 != r2) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x007d, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x007e, code lost:
        r7 = r5;
        r5 = r8;
        r8 = r10;
        r10 = r9;
        r9 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r1;
        r1 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x008d, code lost:
        if (((java.lang.Boolean) r1).booleanValue() == false) goto L_0x00ad;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x008f, code lost:
        r1 = r9.next();
        r0.L$0 = r5;
        r0.L$1 = r4;
        r0.L$2 = r9;
        r0.Z$0 = r8;
        r0.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00a2, code lost:
        if (r5.emit(r1, r0) != r3) goto L_0x00a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a4, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a5, code lost:
        r1 = r2;
        r2 = r3;
        r3 = r4;
        r4 = r9;
        r9 = r10;
        r10 = r8;
        r8 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00ad, code lost:
        if (r8 == false) goto L_0x00b2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00af, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00b5, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00b6, code lost:
        r9 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00b7, code lost:
        r1 = r2;
        r3 = r4;
     */
    /* JADX WARNING: Incorrect type for immutable var: ssa=kotlinx.coroutines.flow.FlowCollector<? super T>, code=kotlinx.coroutines.flow.FlowCollector, for r8v0, types: [kotlinx.coroutines.flow.FlowCollector, kotlinx.coroutines.flow.FlowCollector<? super T>] */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.lang.Object emitAllImpl$FlowKt__ChannelsKt(kotlinx.coroutines.flow.FlowCollector r8, kotlinx.coroutines.channels.ReceiveChannel<? extends T> r9, boolean r10, kotlin.coroutines.Continuation<? super kotlin.Unit> r11) {
        /*
            boolean r0 = r11 instanceof kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAllImpl$1
            if (r0 == 0) goto L_0x0014
            r0 = r11
            kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAllImpl$1 r0 = (kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAllImpl$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAllImpl$1 r0 = new kotlinx.coroutines.flow.FlowKt__ChannelsKt$emitAllImpl$1
            r0.<init>(r11)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x005f;
                case 1: goto L_0x0045;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L_0x002c:
            boolean r8 = r0.Z$0
            java.lang.Object r9 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r9 = (kotlinx.coroutines.channels.ChannelIterator) r9
            r10 = 0
            java.lang.Object r3 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r3 = (kotlinx.coroutines.channels.ReceiveChannel) r3
            java.lang.Object r4 = r0.L$0
            kotlinx.coroutines.flow.FlowCollector r4 = (kotlinx.coroutines.flow.FlowCollector) r4
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x005c }
            r7 = r10
            r10 = r8
            r8 = r4
            r4 = r9
            r9 = r7
            goto L_0x00ac
        L_0x0045:
            boolean r8 = r0.Z$0
            java.lang.Object r9 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r9 = (kotlinx.coroutines.channels.ChannelIterator) r9
            r10 = 0
            java.lang.Object r3 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r3 = (kotlinx.coroutines.channels.ReceiveChannel) r3
            java.lang.Object r4 = r0.L$0
            kotlinx.coroutines.flow.FlowCollector r4 = (kotlinx.coroutines.flow.FlowCollector) r4
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x005c }
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            goto L_0x0087
        L_0x005c:
            r9 = move-exception
            goto L_0x00bf
        L_0x005f:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r9
            kotlinx.coroutines.flow.FlowKt.ensureActive(r8)
            r9 = 0
            kotlinx.coroutines.channels.ChannelIterator r4 = r3.iterator()     // Catch:{ all -> 0x00ba }
        L_0x006c:
            r0.L$0 = r8     // Catch:{ all -> 0x00ba }
            r0.L$1 = r3     // Catch:{ all -> 0x00ba }
            r0.L$2 = r4     // Catch:{ all -> 0x00ba }
            r0.Z$0 = r10     // Catch:{ all -> 0x00ba }
            r5 = 1
            r0.label = r5     // Catch:{ all -> 0x00ba }
            java.lang.Object r5 = r4.hasNext(r0)     // Catch:{ all -> 0x00ba }
            if (r5 != r2) goto L_0x007e
            return r2
        L_0x007e:
            r7 = r5
            r5 = r8
            r8 = r10
            r10 = r9
            r9 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            r1 = r7
        L_0x0087:
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ all -> 0x00b6 }
            boolean r1 = r1.booleanValue()     // Catch:{ all -> 0x00b6 }
            if (r1 == 0) goto L_0x00ad
            java.lang.Object r1 = r9.next()     // Catch:{ all -> 0x00b6 }
            r0.L$0 = r5     // Catch:{ all -> 0x00b6 }
            r0.L$1 = r4     // Catch:{ all -> 0x00b6 }
            r0.L$2 = r9     // Catch:{ all -> 0x00b6 }
            r0.Z$0 = r8     // Catch:{ all -> 0x00b6 }
            r6 = 2
            r0.label = r6     // Catch:{ all -> 0x00b6 }
            java.lang.Object r6 = r5.emit(r1, r0)     // Catch:{ all -> 0x00b6 }
            if (r6 != r3) goto L_0x00a5
            return r3
        L_0x00a5:
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r9
            r9 = r10
            r10 = r8
            r8 = r5
        L_0x00ac:
            goto L_0x006c
        L_0x00ad:
            if (r8 == 0) goto L_0x00b2
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r10)
        L_0x00b2:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        L_0x00b6:
            r9 = move-exception
            r1 = r2
            r3 = r4
            goto L_0x00bf
        L_0x00ba:
            r8 = move-exception
            r7 = r9
            r9 = r8
            r8 = r10
            r10 = r7
        L_0x00bf:
            r10 = r9
            throw r9     // Catch:{ all -> 0x00c2 }
        L_0x00c2:
            r9 = move-exception
            if (r8 == 0) goto L_0x00c8
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r3, r10)
        L_0x00c8:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ChannelsKt.emitAllImpl$FlowKt__ChannelsKt(kotlinx.coroutines.flow.FlowCollector, kotlinx.coroutines.channels.ReceiveChannel, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final <T> Flow<T> receiveAsFlow(ReceiveChannel<? extends T> $this$receiveAsFlow) {
        return new ChannelAsFlow<>($this$receiveAsFlow, false, (CoroutineContext) null, 0, (BufferOverflow) null, 28, (DefaultConstructorMarker) null);
    }

    public static final <T> Flow<T> consumeAsFlow(ReceiveChannel<? extends T> $this$consumeAsFlow) {
        return new ChannelAsFlow<>($this$consumeAsFlow, true, (CoroutineContext) null, 0, (BufferOverflow) null, 28, (DefaultConstructorMarker) null);
    }

    public static final <T> ReceiveChannel<T> produceIn(Flow<? extends T> $this$produceIn, CoroutineScope scope) {
        return ChannelFlowKt.asChannelFlow($this$produceIn).produceImpl(scope);
    }
}
