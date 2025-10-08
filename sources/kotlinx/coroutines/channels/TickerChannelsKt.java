package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a4\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tH\u0007\u001a,\u0010\n\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\fH@¢\u0006\u0002\u0010\r\u001a,\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\fH@¢\u0006\u0002\u0010\r¨\u0006\u000f"}, d2 = {"ticker", "Lkotlinx/coroutines/channels/ReceiveChannel;", "", "delayMillis", "", "initialDelayMillis", "context", "Lkotlin/coroutines/CoroutineContext;", "mode", "Lkotlinx/coroutines/channels/TickerMode;", "fixedPeriodTicker", "channel", "Lkotlinx/coroutines/channels/SendChannel;", "(JJLkotlinx/coroutines/channels/SendChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fixedDelayTicker", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: TickerChannels.kt */
public final class TickerChannelsKt {
    public static /* synthetic */ ReceiveChannel ticker$default(long j, long j2, CoroutineContext coroutineContext, TickerMode tickerMode, int i, Object obj) {
        if ((i & 2) != 0) {
            j2 = j;
        }
        if ((i & 4) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        if ((i & 8) != 0) {
            tickerMode = TickerMode.FIXED_PERIOD;
        }
        return ticker(j, j2, coroutineContext, tickerMode);
    }

    public static final ReceiveChannel<Unit> ticker(long delayMillis, long initialDelayMillis, CoroutineContext context, TickerMode mode) {
        boolean z = true;
        if (delayMillis >= 0) {
            if (initialDelayMillis < 0) {
                z = false;
            }
            if (z) {
                long j = initialDelayMillis;
                long j2 = j;
                return ProduceKt.produce(GlobalScope.INSTANCE, Dispatchers.getUnconfined().plus(context), 0, new TickerChannelsKt$ticker$3(mode, delayMillis, j, (Continuation<? super TickerChannelsKt$ticker$3>) null));
            }
            CoroutineContext coroutineContext = context;
            throw new IllegalArgumentException(("Expected non-negative initial delay, but has " + initialDelayMillis + " ms").toString());
        }
        long j3 = initialDelayMillis;
        CoroutineContext coroutineContext2 = context;
        throw new IllegalArgumentException(("Expected non-negative delay, but has " + delayMillis + " ms").toString());
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x008b, code lost:
        r3 = kotlinx.coroutines.EventLoop_commonKt.delayToNanos(r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x008f, code lost:
        r10 = r10 + r3;
        r12 = kotlin.Unit.INSTANCE;
        r0.L$0 = r14;
        r0.J$0 = r10;
        r0.J$1 = r3;
        r0.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00a0, code lost:
        if (r14.send(r12, r0) != r2) goto L_0x00a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00a2, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00a3, code lost:
        r12 = r10;
        r10 = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00a5, code lost:
        r3 = kotlinx.coroutines.AbstractTimeSourceKt.timeSource;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00a9, code lost:
        if (r3 == null) goto L_0x00b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00ab, code lost:
        r3 = r3.nanoTime();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00b0, code lost:
        r3 = java.lang.System.nanoTime();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00b4, code lost:
        r5 = kotlin.ranges.RangesKt.coerceAtLeast(r12 - r3, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00be, code lost:
        if (r5 != 0) goto L_0x00e2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00c2, code lost:
        if (r10 == 0) goto L_0x00e2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00c4, code lost:
        r5 = r10 - ((r3 - r12) % r10);
        r12 = r3 + r5;
        r3 = kotlinx.coroutines.EventLoop_commonKt.delayNanosToMillis(r5);
        r0.L$0 = r14;
        r0.J$0 = r12;
        r0.J$1 = r10;
        r0.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00dc, code lost:
        if (kotlinx.coroutines.DelayKt.delay(r3, r0) != r2) goto L_0x00df;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00de, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00df, code lost:
        r3 = r10;
        r10 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00e2, code lost:
        r3 = kotlinx.coroutines.EventLoop_commonKt.delayNanosToMillis(r5);
        r0.L$0 = r14;
        r0.J$0 = r12;
        r0.J$1 = r10;
        r0.label = 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00f3, code lost:
        if (kotlinx.coroutines.DelayKt.delay(r3, r0) != r2) goto L_0x00f6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00f5, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00f6, code lost:
        r3 = r10;
        r10 = r12;
     */
    /* JADX WARNING: Incorrect type for immutable var: ssa=kotlinx.coroutines.channels.SendChannel<? super kotlin.Unit>, code=kotlinx.coroutines.channels.SendChannel, for r14v0, types: [kotlinx.coroutines.channels.SendChannel<? super kotlin.Unit>, java.lang.Object] */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0056  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object fixedPeriodTicker(long r10, long r12, kotlinx.coroutines.channels.SendChannel r14, kotlin.coroutines.Continuation<? super kotlin.Unit> r15) {
        /*
            boolean r0 = r15 instanceof kotlinx.coroutines.channels.TickerChannelsKt$fixedPeriodTicker$1
            if (r0 == 0) goto L_0x0014
            r0 = r15
            kotlinx.coroutines.channels.TickerChannelsKt$fixedPeriodTicker$1 r0 = (kotlinx.coroutines.channels.TickerChannelsKt$fixedPeriodTicker$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.TickerChannelsKt$fixedPeriodTicker$1 r0 = new kotlinx.coroutines.channels.TickerChannelsKt$fixedPeriodTicker$1
            r0.<init>(r15)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x0062;
                case 1: goto L_0x0056;
                case 2: goto L_0x004a;
                case 3: goto L_0x003b;
                case 4: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x002c:
            long r10 = r0.J$1
            long r12 = r0.J$0
            java.lang.Object r14 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r14 = (kotlinx.coroutines.channels.SendChannel) r14
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r10
            r10 = r12
            goto L_0x00f8
        L_0x003b:
            long r10 = r0.J$1
            long r12 = r0.J$0
            java.lang.Object r14 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r14 = (kotlinx.coroutines.channels.SendChannel) r14
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r10
            r10 = r12
            goto L_0x00e1
        L_0x004a:
            long r10 = r0.J$1
            long r12 = r0.J$0
            java.lang.Object r14 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r14 = (kotlinx.coroutines.channels.SendChannel) r14
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x00a5
        L_0x0056:
            long r10 = r0.J$1
            long r12 = r0.J$0
            java.lang.Object r14 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r14 = (kotlinx.coroutines.channels.SendChannel) r14
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x008b
        L_0x0062:
            kotlin.ResultKt.throwOnFailure(r1)
            kotlinx.coroutines.AbstractTimeSource r3 = kotlinx.coroutines.AbstractTimeSourceKt.timeSource
            if (r3 == 0) goto L_0x0070
            long r3 = r3.nanoTime()
            goto L_0x0074
        L_0x0070:
            long r3 = java.lang.System.nanoTime()
        L_0x0074:
            long r5 = kotlinx.coroutines.EventLoop_commonKt.delayToNanos(r12)
            long r3 = r3 + r5
            r0.L$0 = r14
            r0.J$0 = r10
            r0.J$1 = r3
            r5 = 1
            r0.label = r5
            java.lang.Object r12 = kotlinx.coroutines.DelayKt.delay(r12, r0)
            if (r12 != r2) goto L_0x0089
            return r2
        L_0x0089:
            r12 = r10
            r10 = r3
        L_0x008b:
            long r3 = kotlinx.coroutines.EventLoop_commonKt.delayToNanos(r12)
        L_0x008f:
            long r10 = r10 + r3
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            r0.L$0 = r14
            r0.J$0 = r10
            r0.J$1 = r3
            r13 = 2
            r0.label = r13
            java.lang.Object r12 = r14.send(r12, r0)
            if (r12 != r2) goto L_0x00a3
            return r2
        L_0x00a3:
            r12 = r10
            r10 = r3
        L_0x00a5:
            kotlinx.coroutines.AbstractTimeSource r3 = kotlinx.coroutines.AbstractTimeSourceKt.timeSource
            if (r3 == 0) goto L_0x00b0
            long r3 = r3.nanoTime()
            goto L_0x00b4
        L_0x00b0:
            long r3 = java.lang.System.nanoTime()
        L_0x00b4:
            long r5 = r12 - r3
            r7 = 0
            long r5 = kotlin.ranges.RangesKt.coerceAtLeast((long) r5, (long) r7)
            int r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r9 != 0) goto L_0x00e2
            int r7 = (r10 > r7 ? 1 : (r10 == r7 ? 0 : -1))
            if (r7 == 0) goto L_0x00e2
            long r5 = r3 - r12
            long r5 = r5 % r10
            long r5 = r10 - r5
            long r12 = r3 + r5
            long r3 = kotlinx.coroutines.EventLoop_commonKt.delayNanosToMillis(r5)
            r0.L$0 = r14
            r0.J$0 = r12
            r0.J$1 = r10
            r7 = 3
            r0.label = r7
            java.lang.Object r3 = kotlinx.coroutines.DelayKt.delay(r3, r0)
            if (r3 != r2) goto L_0x00df
            return r2
        L_0x00df:
            r3 = r10
            r10 = r12
        L_0x00e1:
            goto L_0x008f
        L_0x00e2:
            long r3 = kotlinx.coroutines.EventLoop_commonKt.delayNanosToMillis(r5)
            r0.L$0 = r14
            r0.J$0 = r12
            r0.J$1 = r10
            r7 = 4
            r0.label = r7
            java.lang.Object r3 = kotlinx.coroutines.DelayKt.delay(r3, r0)
            if (r3 != r2) goto L_0x00f6
            return r2
        L_0x00f6:
            r3 = r10
            r10 = r12
        L_0x00f8:
            goto L_0x008f
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.TickerChannelsKt.fixedPeriodTicker(long, long, kotlinx.coroutines.channels.SendChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x006c A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x007a A[RETURN] */
    public static final java.lang.Object fixedDelayTicker(long r4, long r6, kotlinx.coroutines.channels.SendChannel<? super kotlin.Unit> r8, kotlin.coroutines.Continuation<? super kotlin.Unit> r9) {
        /*
            boolean r0 = r9 instanceof kotlinx.coroutines.channels.TickerChannelsKt$fixedDelayTicker$1
            if (r0 == 0) goto L_0x0014
            r0 = r9
            kotlinx.coroutines.channels.TickerChannelsKt$fixedDelayTicker$1 r0 = (kotlinx.coroutines.channels.TickerChannelsKt$fixedDelayTicker$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.TickerChannelsKt$fixedDelayTicker$1 r0 = new kotlinx.coroutines.channels.TickerChannelsKt$fixedDelayTicker$1
            r0.<init>(r9)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x004a;
                case 1: goto L_0x0040;
                case 2: goto L_0x0036;
                case 3: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L_0x002c:
            long r4 = r0.J$0
            java.lang.Object r6 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r6 = (kotlinx.coroutines.channels.SendChannel) r6
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x007b
        L_0x0036:
            long r4 = r0.J$0
            java.lang.Object r6 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r6 = (kotlinx.coroutines.channels.SendChannel) r6
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x006d
        L_0x0040:
            long r4 = r0.J$0
            java.lang.Object r6 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r6 = (kotlinx.coroutines.channels.SendChannel) r6
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x005c
        L_0x004a:
            kotlin.ResultKt.throwOnFailure(r1)
            r0.L$0 = r8
            r0.J$0 = r4
            r3 = 1
            r0.label = r3
            java.lang.Object r6 = kotlinx.coroutines.DelayKt.delay(r6, r0)
            if (r6 != r2) goto L_0x005b
            return r2
        L_0x005b:
            r6 = r8
        L_0x005c:
        L_0x005d:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            r0.L$0 = r6
            r0.J$0 = r4
            r8 = 2
            r0.label = r8
            java.lang.Object r7 = r6.send(r7, r0)
            if (r7 != r2) goto L_0x006d
            return r2
        L_0x006d:
            r0.L$0 = r6
            r0.J$0 = r4
            r7 = 3
            r0.label = r7
            java.lang.Object r7 = kotlinx.coroutines.DelayKt.delay(r4, r0)
            if (r7 != r2) goto L_0x007b
            return r2
        L_0x007b:
            goto L_0x005d
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.TickerChannelsKt.fixedDelayTicker(long, long, kotlinx.coroutines.channels.SendChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
