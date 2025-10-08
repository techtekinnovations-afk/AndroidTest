package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.intrinsics.UndispatchedKt;

@Metadata(d1 = {"\u0000D\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001aR\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u00020\u00032'\u0010\u0004\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0005¢\u0006\u0002\b\tH@\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001¢\u0006\u0002\u0010\n\u001aT\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u000b\u001a\u00020\f2'\u0010\u0004\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0005¢\u0006\u0002\b\tH@\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001¢\u0006\u0004\b\r\u0010\n\u001aG\u0010\u000e\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u00020\u00032'\u0010\u0004\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0005¢\u0006\u0002\b\tH@¢\u0006\u0002\u0010\n\u001aI\u0010\u000e\u001a\u0004\u0018\u0001H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u000b\u001a\u00020\f2'\u0010\u0004\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0005¢\u0006\u0002\b\tH@¢\u0006\u0004\b\u000f\u0010\n\u001a\\\u0010\u0010\u001a\u0004\u0018\u00010\b\"\u0004\b\u0000\u0010\u0011\"\b\b\u0001\u0010\u0001*\u0002H\u00112\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u0002H\u0011\u0012\u0004\u0012\u0002H\u00010\u00132'\u0010\u0004\u001a#\b\u0001\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0005¢\u0006\u0002\b\tH\u0002¢\u0006\u0002\u0010\u0014\u001a \u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0012\u001a\u00020\u001aH\u0000¨\u0006\u001b"}, d2 = {"withTimeout", "T", "timeMillis", "", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/CoroutineScope;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(JLkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "timeout", "Lkotlin/time/Duration;", "withTimeout-KLykuaI", "withTimeoutOrNull", "withTimeoutOrNull-KLykuaI", "setupTimeout", "U", "coroutine", "Lkotlinx/coroutines/TimeoutCoroutine;", "(Lkotlinx/coroutines/TimeoutCoroutine;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "TimeoutCancellationException", "Lkotlinx/coroutines/TimeoutCancellationException;", "time", "delay", "Lkotlinx/coroutines/Delay;", "Lkotlinx/coroutines/Job;", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: Timeout.kt */
public final class TimeoutKt {
    public static final <T> Object withTimeout(long timeMillis, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block, Continuation<? super T> $completion) {
        if (timeMillis > 0) {
            Object obj = setupTimeout(new TimeoutCoroutine(timeMillis, $completion), block);
            if (obj == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended($completion);
            }
            return obj;
        }
        throw new TimeoutCancellationException("Timed out immediately");
    }

    /* renamed from: withTimeout-KLykuaI  reason: not valid java name */
    public static final <T> Object m1517withTimeoutKLykuaI(long timeout, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block, Continuation<? super T> $completion) {
        return withTimeout(DelayKt.m1515toDelayMillisLRDsOJo(timeout), block, $completion);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0080 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0081  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.lang.Object withTimeoutOrNull(long r8, kotlin.jvm.functions.Function2<? super kotlinx.coroutines.CoroutineScope, ? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> r10, kotlin.coroutines.Continuation<? super T> r11) {
        /*
            boolean r0 = r11 instanceof kotlinx.coroutines.TimeoutKt$withTimeoutOrNull$1
            if (r0 == 0) goto L_0x0014
            r0 = r11
            kotlinx.coroutines.TimeoutKt$withTimeoutOrNull$1 r0 = (kotlinx.coroutines.TimeoutKt$withTimeoutOrNull$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.TimeoutKt$withTimeoutOrNull$1 r0 = new kotlinx.coroutines.TimeoutKt$withTimeoutOrNull$1
            r0.<init>(r11)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 0
            switch(r3) {
                case 0: goto L_0x003e;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L_0x002d:
            long r8 = r0.J$0
            java.lang.Object r8 = r0.L$1
            kotlin.jvm.internal.Ref$ObjectRef r8 = (kotlin.jvm.internal.Ref.ObjectRef) r8
            java.lang.Object r9 = r0.L$0
            kotlin.jvm.functions.Function2 r9 = (kotlin.jvm.functions.Function2) r9
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ TimeoutCancellationException -> 0x003c }
            r9 = r1
            goto L_0x0077
        L_0x003c:
            r9 = move-exception
            goto L_0x007a
        L_0x003e:
            kotlin.ResultKt.throwOnFailure(r1)
            r5 = 0
            int r3 = (r8 > r5 ? 1 : (r8 == r5 ? 0 : -1))
            if (r3 > 0) goto L_0x0048
            return r4
        L_0x0048:
            kotlin.jvm.internal.Ref$ObjectRef r3 = new kotlin.jvm.internal.Ref$ObjectRef
            r3.<init>()
            r0.L$0 = r10     // Catch:{ TimeoutCancellationException -> 0x0078 }
            r0.L$1 = r3     // Catch:{ TimeoutCancellationException -> 0x0078 }
            r0.J$0 = r8     // Catch:{ TimeoutCancellationException -> 0x0078 }
            r5 = 1
            r0.label = r5     // Catch:{ TimeoutCancellationException -> 0x0078 }
            r5 = r0
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5     // Catch:{ TimeoutCancellationException -> 0x0078 }
            r6 = 0
            kotlinx.coroutines.TimeoutCoroutine r7 = new kotlinx.coroutines.TimeoutCoroutine     // Catch:{ TimeoutCancellationException -> 0x0078 }
            r7.<init>(r8, r5)     // Catch:{ TimeoutCancellationException -> 0x0078 }
            r3.element = r7     // Catch:{ TimeoutCancellationException -> 0x0078 }
            java.lang.Object r8 = setupTimeout(r7, r10)     // Catch:{ TimeoutCancellationException -> 0x0078 }
            java.lang.Object r9 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()     // Catch:{ TimeoutCancellationException -> 0x0078 }
            if (r8 != r9) goto L_0x0072
            r9 = r0
            kotlin.coroutines.Continuation r9 = (kotlin.coroutines.Continuation) r9     // Catch:{ TimeoutCancellationException -> 0x0078 }
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r9)     // Catch:{ TimeoutCancellationException -> 0x0078 }
        L_0x0072:
            if (r8 != r2) goto L_0x0075
            return r2
        L_0x0075:
            r9 = r8
            r8 = r3
        L_0x0077:
            return r9
        L_0x0078:
            r9 = move-exception
            r8 = r3
        L_0x007a:
            kotlinx.coroutines.Job r10 = r9.coroutine
            T r2 = r8.element
            if (r10 != r2) goto L_0x0081
            return r4
        L_0x0081:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.TimeoutKt.withTimeoutOrNull(long, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* renamed from: withTimeoutOrNull-KLykuaI  reason: not valid java name */
    public static final <T> Object m1518withTimeoutOrNullKLykuaI(long timeout, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block, Continuation<? super T> $completion) {
        return withTimeoutOrNull(DelayKt.m1515toDelayMillisLRDsOJo(timeout), block, $completion);
    }

    private static final <U, T extends U> Object setupTimeout(TimeoutCoroutine<U, ? super T> coroutine, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block) {
        JobKt.disposeOnCompletion(coroutine, DelayKt.getDelay(coroutine.uCont.getContext()).invokeOnTimeout(coroutine.time, coroutine, coroutine.getContext()));
        return UndispatchedKt.startUndispatchedOrReturnIgnoreTimeout(coroutine, coroutine, block);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0017, code lost:
        if (r0 == null) goto L_0x0019;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final kotlinx.coroutines.TimeoutCancellationException TimeoutCancellationException(long r3, kotlinx.coroutines.Delay r5, kotlinx.coroutines.Job r6) {
        /*
            boolean r0 = r5 instanceof kotlinx.coroutines.DelayWithTimeoutDiagnostics
            if (r0 == 0) goto L_0x0008
            r0 = r5
            kotlinx.coroutines.DelayWithTimeoutDiagnostics r0 = (kotlinx.coroutines.DelayWithTimeoutDiagnostics) r0
            goto L_0x0009
        L_0x0008:
            r0 = 0
        L_0x0009:
            if (r0 == 0) goto L_0x0019
            kotlin.time.Duration$Companion r1 = kotlin.time.Duration.Companion
            kotlin.time.DurationUnit r1 = kotlin.time.DurationUnit.MILLISECONDS
            long r1 = kotlin.time.DurationKt.toDuration((long) r3, (kotlin.time.DurationUnit) r1)
            java.lang.String r0 = r0.m1516timeoutMessageLRDsOJo(r1)
            if (r0 != 0) goto L_0x0032
        L_0x0019:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Timed out waiting for "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.String r1 = " ms"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
        L_0x0032:
            kotlinx.coroutines.TimeoutCancellationException r1 = new kotlinx.coroutines.TimeoutCancellationException
            r1.<init>(r0, r6)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.TimeoutKt.TimeoutCancellationException(long, kotlinx.coroutines.Delay, kotlinx.coroutines.Job):kotlinx.coroutines.TimeoutCancellationException");
    }
}
