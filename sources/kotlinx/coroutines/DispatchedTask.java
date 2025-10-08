package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.scheduling.Task;

@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\f\b \u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\u00060\u0003j\u0002`\u0002B\u0011\b\u0000\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u000f\u0010\f\u001a\u0004\u0018\u00010\rH ¢\u0006\u0002\b\u000eJ\u001f\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0012\u001a\u00020\u0013H\u0010¢\u0006\u0002\b\u0014J\u001f\u0010\u0015\u001a\u0002H\u0001\"\u0004\b\u0001\u0010\u00012\b\u0010\u0016\u001a\u0004\u0018\u00010\rH\u0010¢\u0006\u0004\b\u0017\u0010\u0018J\u0019\u0010\u0019\u001a\u0004\u0018\u00010\u00132\b\u0010\u0016\u001a\u0004\u0018\u00010\rH\u0010¢\u0006\u0002\b\u001aJ\u0006\u0010\u001b\u001a\u00020\u0010J\u0015\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u0013H\u0000¢\u0006\u0002\b\u001eR\u0012\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tX \u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u001f"}, d2 = {"Lkotlinx/coroutines/DispatchedTask;", "T", "Lkotlinx/coroutines/SchedulerTask;", "Lkotlinx/coroutines/scheduling/Task;", "resumeMode", "", "<init>", "(I)V", "delegate", "Lkotlin/coroutines/Continuation;", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "takeState", "", "takeState$kotlinx_coroutines_core", "cancelCompletedResult", "", "takenState", "cause", "", "cancelCompletedResult$kotlinx_coroutines_core", "getSuccessfulResult", "state", "getSuccessfulResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Object;", "getExceptionalResult", "getExceptionalResult$kotlinx_coroutines_core", "run", "handleFatalException", "exception", "handleFatalException$kotlinx_coroutines_core", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: DispatchedTask.kt */
public abstract class DispatchedTask<T> extends Task {
    public int resumeMode;

    public abstract Continuation<T> getDelegate$kotlinx_coroutines_core();

    public abstract Object takeState$kotlinx_coroutines_core();

    public DispatchedTask(int resumeMode2) {
        this.resumeMode = resumeMode2;
    }

    public void cancelCompletedResult$kotlinx_coroutines_core(Object takenState, Throwable cause) {
    }

    public <T> T getSuccessfulResult$kotlinx_coroutines_core(Object state) {
        return state;
    }

    public Throwable getExceptionalResult$kotlinx_coroutines_core(Object state) {
        CompletedExceptionally completedExceptionally = state instanceof CompletedExceptionally ? (CompletedExceptionally) state : null;
        if (completedExceptionally != null) {
            return completedExceptionally.cause;
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00e6, code lost:
        if (r10.clearThreadContext() != false) goto L_0x00e8;
     */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00e2 A[SYNTHETIC, Splitter:B:51:0x00e2] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r20 = this;
            r1 = r20
            boolean r0 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()
            if (r0 == 0) goto L_0x001a
            r0 = 0
            int r2 = r1.resumeMode
            r3 = -1
            if (r2 == r3) goto L_0x0010
            r2 = 1
            goto L_0x0011
        L_0x0010:
            r2 = 0
        L_0x0011:
            if (r2 == 0) goto L_0x0014
            goto L_0x001a
        L_0x0014:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x001a:
            r2 = 0
            kotlin.coroutines.Continuation r0 = r1.getDelegate$kotlinx_coroutines_core()     // Catch:{ all -> 0x0104 }
            java.lang.String r3 = "null cannot be cast to non-null type kotlinx.coroutines.internal.DispatchedContinuation<T of kotlinx.coroutines.DispatchedTask>"
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0, r3)     // Catch:{ all -> 0x0104 }
            kotlinx.coroutines.internal.DispatchedContinuation r0 = (kotlinx.coroutines.internal.DispatchedContinuation) r0     // Catch:{ all -> 0x0104 }
            r3 = r0
            kotlin.coroutines.Continuation<T> r0 = r3.continuation     // Catch:{ all -> 0x0104 }
            r4 = r0
            java.lang.Object r0 = r3.countOrElement     // Catch:{ all -> 0x0104 }
            r5 = r0
            r6 = 0
            kotlin.coroutines.CoroutineContext r0 = r4.getContext()     // Catch:{ all -> 0x0104 }
            r7 = r0
            java.lang.Object r0 = kotlinx.coroutines.internal.ThreadContextKt.updateThreadContext(r7, r5)     // Catch:{ all -> 0x0104 }
            r8 = r0
            kotlinx.coroutines.internal.Symbol r0 = kotlinx.coroutines.internal.ThreadContextKt.NO_THREAD_ELEMENTS     // Catch:{ all -> 0x0104 }
            r9 = 0
            if (r8 == r0) goto L_0x0048
            kotlinx.coroutines.UndispatchedCoroutine r0 = kotlinx.coroutines.CoroutineContextKt.updateUndispatchedCompletion(r4, r7, r8)     // Catch:{ all -> 0x0043 }
            goto L_0x0049
        L_0x0043:
            r0 = move-exception
            r19 = r2
            goto L_0x0107
        L_0x0048:
            r0 = r9
        L_0x0049:
            r10 = r0
            r0 = 0
            kotlin.coroutines.CoroutineContext r11 = r4.getContext()     // Catch:{ all -> 0x00f2 }
            java.lang.Object r12 = r1.takeState$kotlinx_coroutines_core()     // Catch:{ all -> 0x00f2 }
            java.lang.Throwable r13 = r1.getExceptionalResult$kotlinx_coroutines_core(r12)     // Catch:{ all -> 0x00f2 }
            if (r13 != 0) goto L_0x0072
            int r14 = r1.resumeMode     // Catch:{ all -> 0x006d }
            boolean r14 = kotlinx.coroutines.DispatchedTaskKt.isCancellableMode(r14)     // Catch:{ all -> 0x006d }
            if (r14 == 0) goto L_0x0072
            kotlinx.coroutines.Job$Key r9 = kotlinx.coroutines.Job.Key     // Catch:{ all -> 0x006d }
            kotlin.coroutines.CoroutineContext$Key r9 = (kotlin.coroutines.CoroutineContext.Key) r9     // Catch:{ all -> 0x006d }
            kotlin.coroutines.CoroutineContext$Element r9 = r11.get(r9)     // Catch:{ all -> 0x006d }
            kotlinx.coroutines.Job r9 = (kotlinx.coroutines.Job) r9     // Catch:{ all -> 0x006d }
            goto L_0x0072
        L_0x006d:
            r0 = move-exception
            r19 = r2
            goto L_0x00f5
        L_0x0072:
            if (r9 == 0) goto L_0x00bb
            boolean r14 = r9.isActive()     // Catch:{ all -> 0x00f2 }
            if (r14 != 0) goto L_0x00bb
            java.util.concurrent.CancellationException r14 = r9.getCancellationException()     // Catch:{ all -> 0x00f2 }
            r15 = r14
            java.lang.Throwable r15 = (java.lang.Throwable) r15     // Catch:{ all -> 0x00f2 }
            r1.cancelCompletedResult$kotlinx_coroutines_core(r12, r15)     // Catch:{ all -> 0x00f2 }
            r15 = r4
            r16 = 0
            kotlin.Result$Companion r17 = kotlin.Result.Companion     // Catch:{ all -> 0x00f2 }
            r17 = 0
            boolean r18 = kotlinx.coroutines.DebugKt.getRECOVER_STACK_TRACES()     // Catch:{ all -> 0x00f2 }
            if (r18 == 0) goto L_0x00a7
            r18 = r0
            boolean r0 = r15 instanceof kotlin.coroutines.jvm.internal.CoroutineStackFrame     // Catch:{ all -> 0x00f2 }
            if (r0 != 0) goto L_0x009a
            r19 = r2
            goto L_0x00ab
        L_0x009a:
            r0 = r14
            java.lang.Throwable r0 = (java.lang.Throwable) r0     // Catch:{ all -> 0x00f2 }
            r19 = r2
            r2 = r15
            kotlin.coroutines.jvm.internal.CoroutineStackFrame r2 = (kotlin.coroutines.jvm.internal.CoroutineStackFrame) r2     // Catch:{ all -> 0x00f0 }
            java.lang.Throwable r0 = kotlinx.coroutines.internal.StackTraceRecoveryKt.recoverFromStackFrame(r0, r2)     // Catch:{ all -> 0x00f0 }
            goto L_0x00ae
        L_0x00a7:
            r18 = r0
            r19 = r2
        L_0x00ab:
            r0 = r14
            java.lang.Throwable r0 = (java.lang.Throwable) r0     // Catch:{ all -> 0x00f0 }
        L_0x00ae:
            java.lang.Object r0 = kotlin.ResultKt.createFailure(r0)     // Catch:{ all -> 0x00f0 }
            java.lang.Object r0 = kotlin.Result.m6constructorimpl(r0)     // Catch:{ all -> 0x00f0 }
            r15.resumeWith(r0)     // Catch:{ all -> 0x00f0 }
            goto L_0x00dc
        L_0x00bb:
            r18 = r0
            r19 = r2
            if (r13 == 0) goto L_0x00cf
            kotlin.Result$Companion r0 = kotlin.Result.Companion     // Catch:{ all -> 0x00f0 }
            java.lang.Object r0 = kotlin.ResultKt.createFailure(r13)     // Catch:{ all -> 0x00f0 }
            java.lang.Object r0 = kotlin.Result.m6constructorimpl(r0)     // Catch:{ all -> 0x00f0 }
            r4.resumeWith(r0)     // Catch:{ all -> 0x00f0 }
            goto L_0x00dc
        L_0x00cf:
            kotlin.Result$Companion r0 = kotlin.Result.Companion     // Catch:{ all -> 0x00f0 }
            java.lang.Object r0 = r1.getSuccessfulResult$kotlinx_coroutines_core(r12)     // Catch:{ all -> 0x00f0 }
            java.lang.Object r0 = kotlin.Result.m6constructorimpl(r0)     // Catch:{ all -> 0x00f0 }
            r4.resumeWith(r0)     // Catch:{ all -> 0x00f0 }
        L_0x00dc:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00f0 }
            if (r10 == 0) goto L_0x00e8
            boolean r0 = r10.clearThreadContext()     // Catch:{ all -> 0x0102 }
            if (r0 == 0) goto L_0x00eb
        L_0x00e8:
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r7, r8)     // Catch:{ all -> 0x0102 }
        L_0x00eb:
            r2 = r19
            goto L_0x010e
        L_0x00f0:
            r0 = move-exception
            goto L_0x00f5
        L_0x00f2:
            r0 = move-exception
            r19 = r2
        L_0x00f5:
            if (r10 == 0) goto L_0x00fd
            boolean r2 = r10.clearThreadContext()     // Catch:{ all -> 0x0102 }
            if (r2 == 0) goto L_0x0100
        L_0x00fd:
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r7, r8)     // Catch:{ all -> 0x0102 }
        L_0x0100:
            throw r0     // Catch:{ all -> 0x0102 }
        L_0x0102:
            r0 = move-exception
            goto L_0x0107
        L_0x0104:
            r0 = move-exception
            r19 = r2
        L_0x0107:
            r2 = r0
            r0 = r2
            r3 = 0
            r1.handleFatalException$kotlinx_coroutines_core(r0)
        L_0x010e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.DispatchedTask.run():void");
    }

    public final void handleFatalException$kotlinx_coroutines_core(Throwable exception) {
        CoroutineExceptionHandlerKt.handleCoroutineException(getDelegate$kotlinx_coroutines_core().getContext(), new CoroutinesInternalError("Fatal exception in coroutines machinery for " + this + ". Please read KDoc to 'handleFatalException' method and report this incident to maintainers", exception));
    }
}
