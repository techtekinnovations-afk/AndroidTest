package kotlinx.coroutines.flow;

import androidx.lifecycle.LifecycleKt$$ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowKt;
import kotlinx.coroutines.flow.internal.AbstractSharedFlowSlot;
import kotlinx.coroutines.internal.Concurrent_commonKt;

@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0002\u0018\u00002\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0014\u0010\n\u001a\u00020\u000b2\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0016J'\u0010\r\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000f0\u000e2\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\u0002H\u0016¢\u0006\u0002\u0010\u0011J\u0006\u0010\u0012\u001a\u00020\u0010J\u0006\u0010\u0013\u001a\u00020\u000bJ\u000e\u0010\u0014\u001a\u00020\u0010H@¢\u0006\u0002\u0010\u0015R$\u0010\u0005\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u00070\bj\n\u0012\u0006\u0012\u0004\u0018\u00010\u0007`\u0006X\u0004¢\u0006\u0004\n\u0002\u0010\t¨\u0006\u0016"}, d2 = {"Lkotlinx/coroutines/flow/StateFlowSlot;", "Lkotlinx/coroutines/flow/internal/AbstractSharedFlowSlot;", "Lkotlinx/coroutines/flow/StateFlowImpl;", "<init>", "()V", "_state", "Lkotlinx/coroutines/internal/WorkaroundAtomicReference;", "", "Ljava/util/concurrent/atomic/AtomicReference;", "Ljava/util/concurrent/atomic/AtomicReference;", "allocateLocked", "", "flow", "freeLocked", "", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/flow/StateFlowImpl;)[Lkotlin/coroutines/Continuation;", "makePending", "takePending", "awaitPending", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: StateFlow.kt */
final class StateFlowSlot extends AbstractSharedFlowSlot<StateFlowImpl<?>> {
    /* access modifiers changed from: private */
    public final AtomicReference<Object> _state = new AtomicReference<>((Object) null);

    public boolean allocateLocked(StateFlowImpl<?> flow) {
        if (Concurrent_commonKt.getValue(this._state) != null) {
            return false;
        }
        Concurrent_commonKt.setValue(this._state, StateFlowKt.NONE);
        return true;
    }

    public Continuation<Unit>[] freeLocked(StateFlowImpl<?> flow) {
        Concurrent_commonKt.setValue(this._state, null);
        return AbstractSharedFlowKt.EMPTY_RESUMES;
    }

    public final void makePending() {
        AtomicReference<Object> atomicReference = this._state;
        while (true) {
            Object state = Concurrent_commonKt.getValue(atomicReference);
            AtomicReference<Object> atomicReference2 = atomicReference;
            if (state != null && state != StateFlowKt.PENDING) {
                if (state == StateFlowKt.NONE) {
                    if (LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this._state, state, StateFlowKt.PENDING)) {
                        return;
                    }
                } else if (LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this._state, state, StateFlowKt.NONE)) {
                    Result.Companion companion = Result.Companion;
                    ((CancellableContinuationImpl) state).resumeWith(Result.m6constructorimpl(Unit.INSTANCE));
                    return;
                }
            } else {
                return;
            }
        }
    }

    public final boolean takePending() {
        Object state = this._state.getAndSet(StateFlowKt.NONE);
        Intrinsics.checkNotNull(state);
        if (!DebugKt.getASSERTIONS_ENABLED() || !(state instanceof CancellableContinuationImpl)) {
            return state == StateFlowKt.PENDING;
        }
        throw new AssertionError();
    }

    public final Object awaitPending(Continuation<? super Unit> $completion) {
        boolean z = true;
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 1);
        cancellable$iv.initCancellability();
        CancellableContinuation cont = cancellable$iv;
        if (!DebugKt.getASSERTIONS_ENABLED() || !(Concurrent_commonKt.getValue(this._state) instanceof CancellableContinuationImpl)) {
            if (!LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this._state, StateFlowKt.NONE, cont)) {
                if (DebugKt.getASSERTIONS_ENABLED()) {
                    if (Concurrent_commonKt.getValue(this._state) != StateFlowKt.PENDING) {
                        z = false;
                    }
                    if (!z) {
                        throw new AssertionError();
                    }
                }
                Result.Companion companion = Result.Companion;
                cont.resumeWith(Result.m6constructorimpl(Unit.INSTANCE));
            }
            Object result = cancellable$iv.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended($completion);
            }
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                return result;
            }
            return Unit.INSTANCE;
        }
        throw new AssertionError();
    }
}
