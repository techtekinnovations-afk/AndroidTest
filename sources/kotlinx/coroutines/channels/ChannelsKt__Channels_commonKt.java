package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.ExceptionsKt;

@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\u001a$\u0010\u0002\u001a\u0004\u0018\u0001H\u0003\"\b\b\u0000\u0010\u0003*\u00020\u0004*\b\u0012\u0004\u0012\u0002H\u00030\u0005H@¢\u0006\u0002\u0010\u0006\u001a$\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00030\b\"\b\b\u0000\u0010\u0003*\u00020\u0004*\b\u0012\u0004\u0012\u0002H\u00030\u0005H\u0007\u001aP\u0010\t\u001a\u0002H\n\"\u0004\b\u0000\u0010\u0003\"\u0004\b\u0001\u0010\n*\b\u0012\u0004\u0012\u0002H\u00030\u00052\u001d\u0010\u000b\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0005\u0012\u0004\u0012\u0002H\n0\f¢\u0006\u0002\b\rH\b\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u000e\u001a2\u0010\u000f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u00052\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\u00100\fHH¢\u0006\u0002\u0010\u0012\u001a$\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0014\"\u0004\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0005H@¢\u0006\u0002\u0010\u0006\u001a\u001a\u0010\u0015\u001a\u00020\u0010*\u0006\u0012\u0002\b\u00030\u00052\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"DEFAULT_CLOSE_MESSAGE", "", "receiveOrNull", "E", "", "Lkotlinx/coroutines/channels/ReceiveChannel;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onReceiveOrNull", "Lkotlinx/coroutines/selects/SelectClause1;", "consume", "R", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "consumeEach", "", "action", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toList", "", "cancelConsumed", "cause", "", "kotlinx-coroutines-core"}, k = 5, mv = {2, 0, 0}, xi = 48, xs = "kotlinx/coroutines/channels/ChannelsKt")
/* compiled from: Channels.common.kt */
final /* synthetic */ class ChannelsKt__Channels_commonKt {
    public static final <E, R> R consume(ReceiveChannel<? extends E> $this$consume, Function1<? super ReceiveChannel<? extends E>, ? extends R> block) {
        Throwable cause;
        try {
            R invoke = block.invoke($this$consume);
            ChannelsKt.cancelConsumed($this$consume, (Throwable) null);
            return invoke;
        } catch (Throwable e) {
            ChannelsKt.cancelConsumed($this$consume, cause);
            throw e;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r0.L$0 = r7;
        r0.L$1 = r6;
        r0.L$2 = r4;
        r0.label = 1;
        r8 = r4.hasNext(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006b, code lost:
        if (r8 != r2) goto L_0x006e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006d, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x006e, code lost:
        r9 = r2;
        r2 = r1;
        r1 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r4;
        r4 = r3;
        r3 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x007d, code lost:
        if (((java.lang.Boolean) r1).booleanValue() == false) goto L_0x008e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x007f, code lost:
        r8.invoke(r5.next());
        r1 = r2;
        r2 = r3;
        r3 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r7;
        r7 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x008e, code lost:
        r1 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0091, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0098, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0099, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x009a, code lost:
        r5 = r2;
        r2 = r1;
        r1 = r5;
        r5 = r6;
        r6 = r7;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object consumeEach(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r10, kotlin.jvm.functions.Function1<? super E, kotlin.Unit> r11, kotlin.coroutines.Continuation<? super kotlin.Unit> r12) {
        /*
            boolean r0 = r12 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1
            if (r0 == 0) goto L_0x0014
            r0 = r12
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$consumeEach$1
            r0.<init>(r12)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x004a;
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
            r11 = 0
            r3 = 0
            java.lang.Object r4 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
            r5 = 0
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r7 = r0.L$0
            kotlin.jvm.functions.Function1 r7 = (kotlin.jvm.functions.Function1) r7
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x0047 }
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r1
            goto L_0x0077
        L_0x0047:
            r2 = move-exception
            goto L_0x00a3
        L_0x004a:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = 0
            r6 = r10
            r10 = 0
            r5 = 0
            r4 = r6
            r7 = 0
            kotlinx.coroutines.channels.ChannelIterator r8 = r4.iterator()     // Catch:{ all -> 0x00a0 }
            r4 = r11
            r11 = r10
            r10 = r3
            r3 = r7
            r7 = r4
            r4 = r8
        L_0x005e:
            r0.L$0 = r7     // Catch:{ all -> 0x0047 }
            r0.L$1 = r6     // Catch:{ all -> 0x0047 }
            r0.L$2 = r4     // Catch:{ all -> 0x0047 }
            r8 = 1
            r0.label = r8     // Catch:{ all -> 0x0047 }
            java.lang.Object r8 = r4.hasNext(r0)     // Catch:{ all -> 0x0047 }
            if (r8 != r2) goto L_0x006e
            return r2
        L_0x006e:
            r9 = r2
            r2 = r1
            r1 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            r3 = r9
        L_0x0077:
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ all -> 0x0099 }
            boolean r1 = r1.booleanValue()     // Catch:{ all -> 0x0099 }
            if (r1 == 0) goto L_0x008e
            java.lang.Object r1 = r5.next()     // Catch:{ all -> 0x0099 }
            r8.invoke(r1)     // Catch:{ all -> 0x0099 }
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            goto L_0x005e
        L_0x008e:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0099 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r6)
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        L_0x0099:
            r1 = move-exception
            r5 = r2
            r2 = r1
            r1 = r5
            r5 = r6
            r6 = r7
            goto L_0x00a3
        L_0x00a0:
            r2 = move-exception
            r11 = r10
            r10 = r3
        L_0x00a3:
            r3 = r2
            throw r2     // Catch:{ all -> 0x00a6 }
        L_0x00a6:
            r2 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.consumeEach(kotlinx.coroutines.channels.ReceiveChannel, kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private static final <E> Object consumeEach$$forInline(ReceiveChannel<? extends E> $this$consumeEach, Function1<? super E, Unit> action, Continuation<? super Unit> $completion) {
        Throwable cause$iv;
        ReceiveChannel<? extends E> $this$consumeEach_u24lambda_u240 = $this$consumeEach;
        try {
            ChannelIterator it = $this$consumeEach_u24lambda_u240.iterator();
            while (((Boolean) it.hasNext((Continuation<? super Boolean>) null)).booleanValue()) {
                action.invoke(it.next());
            }
            Unit unit = Unit.INSTANCE;
            ChannelsKt.cancelConsumed($this$consumeEach_u24lambda_u240, (Throwable) null);
            return Unit.INSTANCE;
        } catch (Throwable e$iv) {
            ChannelsKt.cancelConsumed($this$consumeEach_u24lambda_u240, cause$iv);
            throw e$iv;
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r2.L$0 = r12;
        r2.L$1 = r11;
        r2.L$2 = r10;
        r2.L$3 = r8;
        r2.label = 1;
        r13 = r8.hasNext(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0082, code lost:
        if (r13 != r0) goto L_0x0085;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0084, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0085, code lost:
        r16 = r4;
        r4 = r3;
        r3 = r13;
        r13 = r12;
        r12 = r11;
        r11 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r7;
        r7 = r6;
        r6 = r5;
        r5 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0099, code lost:
        if (((java.lang.Boolean) r3).booleanValue() == false) goto L_0x00b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x009b, code lost:
        r12.add(r9.next());
        r3 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r7;
        r7 = r8;
        r8 = r9;
        r9 = r10;
        r10 = r11;
        r11 = r12;
        r12 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00b0, code lost:
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b3, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r11, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00be, code lost:
        return kotlin.collections.CollectionsKt.build(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00bf, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00c0, code lost:
        r3 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r7;
        r9 = r10;
        r10 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00c7, code lost:
        r0 = th;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0055  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0027  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <E> java.lang.Object toList(kotlinx.coroutines.channels.ReceiveChannel<? extends E> r17, kotlin.coroutines.Continuation<? super java.util.List<? extends E>> r18) {
        /*
            r1 = r18
            boolean r0 = r1 instanceof kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toList$1
            if (r0 == 0) goto L_0x0016
            r0 = r1
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toList$1 r0 = (kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toList$1) r0
            int r2 = r0.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r2 = r0.label
            int r2 = r2 - r3
            r0.label = r2
            goto L_0x001b
        L_0x0016:
            kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toList$1 r0 = new kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt$toList$1
            r0.<init>(r1)
        L_0x001b:
            r2 = r0
            java.lang.Object r3 = r2.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r2.label
            switch(r4) {
                case 0: goto L_0x0055;
                case 1: goto L_0x002f;
                default: goto L_0x0027;
            }
        L_0x0027:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x002f:
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            java.lang.Object r8 = r2.L$3
            kotlinx.coroutines.channels.ChannelIterator r8 = (kotlinx.coroutines.channels.ChannelIterator) r8
            r9 = 0
            java.lang.Object r10 = r2.L$2
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r11 = r2.L$1
            java.util.List r11 = (java.util.List) r11
            java.lang.Object r12 = r2.L$0
            java.util.List r12 = (java.util.List) r12
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0052 }
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = r3
            goto L_0x0093
        L_0x0052:
            r0 = move-exception
            goto L_0x00cd
        L_0x0055:
            kotlin.ResultKt.throwOnFailure(r3)
            r4 = r17
            java.util.List r5 = kotlin.collections.CollectionsKt.createListBuilder()
            r6 = r5
            r7 = 0
            r8 = 0
            r10 = r4
            r4 = 0
            r9 = 0
            r11 = r10
            r12 = 0
            kotlinx.coroutines.channels.ChannelIterator r13 = r11.iterator()     // Catch:{ all -> 0x00c9 }
            r11 = r6
            r6 = r4
            r4 = r7
            r7 = r12
            r12 = r5
            r5 = r8
            r8 = r13
        L_0x0073:
            r2.L$0 = r12     // Catch:{ all -> 0x00c7 }
            r2.L$1 = r11     // Catch:{ all -> 0x00c7 }
            r2.L$2 = r10     // Catch:{ all -> 0x00c7 }
            r2.L$3 = r8     // Catch:{ all -> 0x00c7 }
            r13 = 1
            r2.label = r13     // Catch:{ all -> 0x00c7 }
            java.lang.Object r13 = r8.hasNext(r2)     // Catch:{ all -> 0x00c7 }
            if (r13 != r0) goto L_0x0085
            return r0
        L_0x0085:
            r16 = r4
            r4 = r3
            r3 = r13
            r13 = r12
            r12 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r16
        L_0x0093:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x00bf }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x00bf }
            if (r3 == 0) goto L_0x00b0
            java.lang.Object r3 = r9.next()     // Catch:{ all -> 0x00bf }
            r14 = r3
            r15 = 0
            r12.add(r14)     // Catch:{ all -> 0x00bf }
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r13
            goto L_0x0073
        L_0x00b0:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00bf }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r11, r10)
            java.util.List r0 = kotlin.collections.CollectionsKt.build(r13)
            return r0
        L_0x00bf:
            r0 = move-exception
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r9 = r10
            r10 = r11
            goto L_0x00cd
        L_0x00c7:
            r0 = move-exception
            goto L_0x00cd
        L_0x00c9:
            r0 = move-exception
            r6 = r4
            r4 = r7
            r5 = r8
        L_0x00cd:
            r7 = r0
            throw r0     // Catch:{ all -> 0x00d0 }
        L_0x00d0:
            r0 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__Channels_commonKt.toList(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final void cancelConsumed(ReceiveChannel<?> $this$cancelConsumed, Throwable cause) {
        CancellationException cancellationException = null;
        if (cause != null) {
            Throwable it = cause;
            if (it instanceof CancellationException) {
                cancellationException = (CancellationException) it;
            }
            if (cancellationException == null) {
                cancellationException = ExceptionsKt.CancellationException("Channel was consumed, consumer had failed", it);
            }
        }
        $this$cancelConsumed.cancel(cancellationException);
    }
}
