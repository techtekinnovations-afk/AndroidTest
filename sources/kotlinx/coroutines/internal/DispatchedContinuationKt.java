package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DispatchedTask;
import kotlinx.coroutines.EventLoop;
import kotlinx.coroutines.ThreadLocalEventLoop;

@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a+\u0010\u0003\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0005*\b\u0012\u0004\u0012\u0002H\u00050\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00050\bH\u0007¢\u0006\u0002\u0010\t\u001a\u0012\u0010\n\u001a\u00020\u000b*\b\u0012\u0004\u0012\u00020\u00040\fH\u0000\u001a;\u0010\r\u001a\u00020\u000b*\u0006\u0012\u0002\b\u00030\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00040\u0014H\b\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000\"\u0010\u0010\u0002\u001a\u00020\u00018\u0000X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"UNDEFINED", "Lkotlinx/coroutines/internal/Symbol;", "REUSABLE_CLAIMED", "resumeCancellableWith", "", "T", "Lkotlin/coroutines/Continuation;", "result", "Lkotlin/Result;", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;)V", "yieldUndispatched", "", "Lkotlinx/coroutines/internal/DispatchedContinuation;", "executeUnconfined", "contState", "", "mode", "", "doYield", "block", "Lkotlin/Function0;", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: DispatchedContinuation.kt */
public final class DispatchedContinuationKt {
    public static final Symbol REUSABLE_CLAIMED = new Symbol("REUSABLE_CLAIMED");
    /* access modifiers changed from: private */
    public static final Symbol UNDEFINED = new Symbol("UNDEFINED");

    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00f6, code lost:
        if (r17.clearThreadContext() != false) goto L_0x00f8;
     */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00b2  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0117 A[Catch:{ all -> 0x00fd, all -> 0x0127 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> void resumeCancellableWith(kotlin.coroutines.Continuation<? super T> r22, java.lang.Object r23) {
        /*
            r1 = r22
            boolean r0 = r1 instanceof kotlinx.coroutines.internal.DispatchedContinuation
            if (r0 == 0) goto L_0x0143
            r2 = r1
            kotlinx.coroutines.internal.DispatchedContinuation r2 = (kotlinx.coroutines.internal.DispatchedContinuation) r2
            r3 = 0
            java.lang.Object r4 = kotlinx.coroutines.CompletionStateKt.toState(r23)
            kotlinx.coroutines.CoroutineDispatcher r0 = r2.dispatcher
            kotlin.coroutines.CoroutineContext r5 = r2.getContext()
            boolean r0 = r0.isDispatchNeeded(r5)
            r5 = 1
            if (r0 == 0) goto L_0x0033
            r2._state = r4
            r2.resumeMode = r5
            kotlinx.coroutines.CoroutineDispatcher r0 = r2.dispatcher
            kotlin.coroutines.CoroutineContext r5 = r2.getContext()
            r6 = r2
            java.lang.Runnable r6 = (java.lang.Runnable) r6
            r0.dispatch(r5, r6)
            r1 = r23
            r18 = r2
            r19 = r3
            goto L_0x013b
        L_0x0033:
            r6 = 1
            r7 = r2
            r8 = 0
            r9 = 0
            boolean r0 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()
            if (r0 == 0) goto L_0x0040
            r0 = 0
        L_0x0040:
            kotlinx.coroutines.ThreadLocalEventLoop r0 = kotlinx.coroutines.ThreadLocalEventLoop.INSTANCE
            kotlinx.coroutines.EventLoop r10 = r0.getEventLoop$kotlinx_coroutines_core()
            boolean r0 = r10.isUnconfinedLoopActive()
            if (r0 == 0) goto L_0x005f
            r7._state = r4
            r7.resumeMode = r6
            r0 = r7
            kotlinx.coroutines.DispatchedTask r0 = (kotlinx.coroutines.DispatchedTask) r0
            r10.dispatchUnconfined(r0)
            r1 = r23
            r18 = r2
            r19 = r3
            goto L_0x013a
        L_0x005f:
            r11 = r7
            kotlinx.coroutines.DispatchedTask r11 = (kotlinx.coroutines.DispatchedTask) r11
            r12 = 0
            r10.incrementUseCount(r5)
            r13 = 0
            r0 = r2
            r14 = 0
            kotlin.coroutines.CoroutineContext r15 = r0.getContext()     // Catch:{ all -> 0x0129 }
            kotlinx.coroutines.Job$Key r16 = kotlinx.coroutines.Job.Key     // Catch:{ all -> 0x0129 }
            r5 = r16
            kotlin.coroutines.CoroutineContext$Key r5 = (kotlin.coroutines.CoroutineContext.Key) r5     // Catch:{ all -> 0x0129 }
            kotlin.coroutines.CoroutineContext$Element r5 = r15.get(r5)     // Catch:{ all -> 0x0129 }
            kotlinx.coroutines.Job r5 = (kotlinx.coroutines.Job) r5     // Catch:{ all -> 0x0129 }
            if (r5 == 0) goto L_0x00ad
            boolean r15 = r5.isActive()     // Catch:{ all -> 0x00a4 }
            if (r15 != 0) goto L_0x00ad
            java.util.concurrent.CancellationException r15 = r5.getCancellationException()     // Catch:{ all -> 0x00a4 }
            r1 = r15
            java.lang.Throwable r1 = (java.lang.Throwable) r1     // Catch:{ all -> 0x00a4 }
            r0.cancelCompletedResult$kotlinx_coroutines_core(r4, r1)     // Catch:{ all -> 0x00a4 }
            r1 = r0
            kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1     // Catch:{ all -> 0x00a4 }
            kotlin.Result$Companion r16 = kotlin.Result.Companion     // Catch:{ all -> 0x00a4 }
            r16 = r15
            java.lang.Throwable r16 = (java.lang.Throwable) r16     // Catch:{ all -> 0x00a4 }
            java.lang.Object r16 = kotlin.ResultKt.createFailure(r16)     // Catch:{ all -> 0x00a4 }
            r17 = r0
            java.lang.Object r0 = kotlin.Result.m6constructorimpl(r16)     // Catch:{ all -> 0x00a4 }
            r1.resumeWith(r0)     // Catch:{ all -> 0x00a4 }
            r0 = 1
            goto L_0x00b0
        L_0x00a4:
            r0 = move-exception
            r1 = r23
            r18 = r2
            r19 = r3
            goto L_0x0130
        L_0x00ad:
            r17 = r0
            r0 = 0
        L_0x00b0:
            if (r0 != 0) goto L_0x0117
            r1 = r2
            r5 = 0
            kotlin.coroutines.Continuation<T> r0 = r1.continuation     // Catch:{ all -> 0x0129 }
            java.lang.Object r14 = r1.countOrElement     // Catch:{ all -> 0x0129 }
            r15 = r0
            r16 = 0
            kotlin.coroutines.CoroutineContext r0 = r15.getContext()     // Catch:{ all -> 0x0129 }
            r17 = r0
            r18 = r2
            r2 = r17
            java.lang.Object r0 = kotlinx.coroutines.internal.ThreadContextKt.updateThreadContext(r2, r14)     // Catch:{ all -> 0x0111 }
            r17 = r0
            kotlinx.coroutines.internal.Symbol r0 = kotlinx.coroutines.internal.ThreadContextKt.NO_THREAD_ELEMENTS     // Catch:{ all -> 0x0111 }
            r19 = r3
            r3 = r17
            if (r3 == r0) goto L_0x00dc
            kotlinx.coroutines.UndispatchedCoroutine r0 = kotlinx.coroutines.CoroutineContextKt.updateUndispatchedCompletion(r15, r2, r3)     // Catch:{ all -> 0x00d8 }
            goto L_0x00dd
        L_0x00d8:
            r0 = move-exception
            r1 = r23
            goto L_0x0130
        L_0x00dc:
            r0 = 0
        L_0x00dd:
            r17 = r0
            r0 = 0
            r20 = r0
            kotlin.coroutines.Continuation<T> r0 = r1.continuation     // Catch:{ all -> 0x00ff }
            r21 = r1
            r1 = r23
            r0.resumeWith(r1)     // Catch:{ all -> 0x00fd }
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00fd }
            if (r17 == 0) goto L_0x00f8
            boolean r0 = r17.clearThreadContext()     // Catch:{ all -> 0x0127 }
            if (r0 == 0) goto L_0x00fb
        L_0x00f8:
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r2, r3)     // Catch:{ all -> 0x0127 }
        L_0x00fb:
            goto L_0x011d
        L_0x00fd:
            r0 = move-exception
            goto L_0x0104
        L_0x00ff:
            r0 = move-exception
            r21 = r1
            r1 = r23
        L_0x0104:
            if (r17 == 0) goto L_0x010c
            boolean r20 = r17.clearThreadContext()     // Catch:{ all -> 0x0127 }
            if (r20 == 0) goto L_0x010f
        L_0x010c:
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r2, r3)     // Catch:{ all -> 0x0127 }
        L_0x010f:
            throw r0     // Catch:{ all -> 0x0127 }
        L_0x0111:
            r0 = move-exception
            r1 = r23
            r19 = r3
            goto L_0x0130
        L_0x0117:
            r1 = r23
            r18 = r2
            r19 = r3
        L_0x011d:
        L_0x011f:
            boolean r0 = r10.processUnconfinedEvent()     // Catch:{ all -> 0x0127 }
            if (r0 != 0) goto L_0x011f
            goto L_0x0133
        L_0x0127:
            r0 = move-exception
            goto L_0x0130
        L_0x0129:
            r0 = move-exception
            r1 = r23
            r18 = r2
            r19 = r3
        L_0x0130:
            r11.handleFatalException$kotlinx_coroutines_core(r0)     // Catch:{ all -> 0x013d }
        L_0x0133:
            r2 = 1
            r10.decrementUseCount(r2)
        L_0x013a:
        L_0x013b:
            goto L_0x0148
        L_0x013d:
            r0 = move-exception
            r2 = 1
            r10.decrementUseCount(r2)
            throw r0
        L_0x0143:
            r1 = r23
            r22.resumeWith(r23)
        L_0x0148:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.DispatchedContinuationKt.resumeCancellableWith(kotlin.coroutines.Continuation, java.lang.Object):void");
    }

    public static final boolean yieldUndispatched(DispatchedContinuation<? super Unit> $this$yieldUndispatched) {
        Object contState$iv = Unit.INSTANCE;
        DispatchedContinuation<? super Unit> dispatchedContinuation = $this$yieldUndispatched;
        if (DebugKt.getASSERTIONS_ENABLED()) {
        }
        EventLoop eventLoop$iv = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
        if (eventLoop$iv.isUnconfinedQueueEmpty()) {
            return false;
        }
        if (eventLoop$iv.isUnconfinedLoopActive()) {
            dispatchedContinuation._state = contState$iv;
            dispatchedContinuation.resumeMode = 1;
            eventLoop$iv.dispatchUnconfined(dispatchedContinuation);
            return true;
        }
        DispatchedTask $this$runUnconfinedEventLoop$iv$iv = dispatchedContinuation;
        eventLoop$iv.incrementUseCount(true);
        try {
            $this$yieldUndispatched.run();
            do {
            } while (eventLoop$iv.processUnconfinedEvent());
        } catch (Throwable th) {
            eventLoop$iv.decrementUseCount(true);
            throw th;
        }
        eventLoop$iv.decrementUseCount(true);
        return false;
    }

    static /* synthetic */ boolean executeUnconfined$default(DispatchedContinuation $this$executeUnconfined_u24default, Object contState, int mode, boolean doYield, Function0 block, int i, Object obj) {
        if ((i & 4) != 0) {
            doYield = false;
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if ((mode != -1 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        EventLoop eventLoop = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
        if (doYield && eventLoop.isUnconfinedQueueEmpty()) {
            return false;
        }
        if (eventLoop.isUnconfinedLoopActive()) {
            $this$executeUnconfined_u24default._state = contState;
            $this$executeUnconfined_u24default.resumeMode = mode;
            eventLoop.dispatchUnconfined($this$executeUnconfined_u24default);
            return true;
        }
        DispatchedTask $this$runUnconfinedEventLoop$iv = $this$executeUnconfined_u24default;
        eventLoop.incrementUseCount(true);
        try {
            block.invoke();
            do {
            } while (eventLoop.processUnconfinedEvent());
        } catch (Throwable th) {
            eventLoop.decrementUseCount(true);
            throw th;
        }
        eventLoop.decrementUseCount(true);
        return false;
    }

    private static final boolean executeUnconfined(DispatchedContinuation<?> $this$executeUnconfined, Object contState, int mode, boolean doYield, Function0<Unit> block) {
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if ((mode != -1 ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        EventLoop eventLoop = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
        if (doYield && eventLoop.isUnconfinedQueueEmpty()) {
            return false;
        }
        if (eventLoop.isUnconfinedLoopActive()) {
            $this$executeUnconfined._state = contState;
            $this$executeUnconfined.resumeMode = mode;
            eventLoop.dispatchUnconfined($this$executeUnconfined);
            return true;
        }
        DispatchedTask $this$runUnconfinedEventLoop$iv = $this$executeUnconfined;
        eventLoop.incrementUseCount(true);
        try {
            block.invoke();
            do {
            } while (eventLoop.processUnconfinedEvent());
        } catch (Throwable th) {
            eventLoop.decrementUseCount(true);
            throw th;
        }
        eventLoop.decrementUseCount(true);
        return false;
    }
}
