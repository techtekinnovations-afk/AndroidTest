package kotlinx.coroutines.sync;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Waiter;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectClause2Impl;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectInstanceInternal;

@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0010\u0018\u00002\u00020\u00012\u00020\u0002:\u0002-.B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006J\u0010\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\tH\u0016J\u0012\u0010\u001a\u001a\u00020\u001b2\b\u0010\u0007\u001a\u0004\u0018\u00010\tH\u0002J\u0018\u0010\u001c\u001a\u00020\u00152\b\u0010\u0007\u001a\u0004\u0018\u00010\tH@¢\u0006\u0002\u0010\u001dJ\u0018\u0010\u001e\u001a\u00020\u00152\b\u0010\u0007\u001a\u0004\u0018\u00010\tH@¢\u0006\u0002\u0010\u001dJ\u0012\u0010\u001f\u001a\u00020\u00042\b\u0010\u0007\u001a\u0004\u0018\u00010\tH\u0016J\u0012\u0010 \u001a\u00020\u001b2\b\u0010\u0007\u001a\u0004\u0018\u00010\tH\u0002J\u0012\u0010!\u001a\u00020\u00152\b\u0010\u0007\u001a\u0004\u0018\u00010\tH\u0016J\u001e\u0010(\u001a\u00020\u00152\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\r2\b\u0010\u0007\u001a\u0004\u0018\u00010\tH\u0014J\u001e\u0010)\u001a\u0004\u0018\u00010\t2\b\u0010\u0007\u001a\u0004\u0018\u00010\t2\b\u0010*\u001a\u0004\u0018\u00010\tH\u0014J\b\u0010+\u001a\u00020,H\u0016R\u0011\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0004R{\u0010\n\u001am\u0012\u0017\u0012\u0015\u0012\u0002\b\u00030\r¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0015\u0012\u0013\u0018\u00010\t¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0011\u0012\u0015\u0012\u0013\u0018\u00010\t¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0012\u0012\u001e\u0012\u001c\u0012\u0004\u0012\u00020\u0013\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\f0\fj\u0002`\u000bX\u0004¢\u0006\u0004\n\u0002\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00048VX\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R(\u0010\"\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0004\u0012\u00020\u00020#8VX\u0004¢\u0006\f\u0012\u0004\b$\u0010%\u001a\u0004\b&\u0010'¨\u0006/"}, d2 = {"Lkotlinx/coroutines/sync/MutexImpl;", "Lkotlinx/coroutines/sync/SemaphoreAndMutexImpl;", "Lkotlinx/coroutines/sync/Mutex;", "locked", "", "<init>", "(Z)V", "owner", "Lkotlinx/atomicfu/AtomicRef;", "", "onSelectCancellationUnlockConstructor", "Lkotlinx/coroutines/selects/OnCancellationConstructor;", "Lkotlin/Function3;", "Lkotlinx/coroutines/selects/SelectInstance;", "Lkotlin/ParameterName;", "name", "select", "param", "internalResult", "", "Lkotlin/coroutines/CoroutineContext;", "", "Lkotlin/jvm/functions/Function3;", "isLocked", "()Z", "holdsLock", "holdsLockImpl", "", "lock", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "lockSuspend", "tryLock", "tryLockImpl", "unlock", "onLock", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnLock$annotations", "()V", "getOnLock", "()Lkotlinx/coroutines/selects/SelectClause2;", "onLockRegFunction", "onLockProcessResult", "result", "toString", "", "CancellableContinuationWithOwner", "SelectInstanceWithOwner", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: Mutex.kt */
public class MutexImpl extends SemaphoreAndMutexImpl implements Mutex {
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicReferenceFieldUpdater owner$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(MutexImpl.class, Object.class, "owner$volatile");
    private final Function3<SelectInstance<?>, Object, Object, Function3<Throwable, Object, CoroutineContext, Unit>> onSelectCancellationUnlockConstructor;
    private volatile /* synthetic */ Object owner$volatile;

    public static /* synthetic */ void getOnLock$annotations() {
    }

    private final /* synthetic */ Object getOwner$volatile() {
        return this.owner$volatile;
    }

    private final /* synthetic */ void setOwner$volatile(Object obj) {
        this.owner$volatile = obj;
    }

    public Object lock(Object obj, Continuation<? super Unit> continuation) {
        return lock$suspendImpl(this, obj, continuation);
    }

    public MutexImpl(boolean locked) {
        super(1, locked);
        this.owner$volatile = locked ? null : MutexKt.NO_OWNER;
        this.onSelectCancellationUnlockConstructor = new MutexImpl$$ExternalSyntheticLambda1(this);
    }

    /* access modifiers changed from: private */
    public static final Function3 onSelectCancellationUnlockConstructor$lambda$1(MutexImpl this$0, SelectInstance selectInstance, Object owner, Object obj) {
        return new MutexImpl$$ExternalSyntheticLambda0(this$0, owner);
    }

    /* access modifiers changed from: private */
    public static final Unit onSelectCancellationUnlockConstructor$lambda$1$lambda$0(MutexImpl this$0, Object $owner, Throwable th, Object obj, CoroutineContext coroutineContext) {
        this$0.unlock($owner);
        return Unit.INSTANCE;
    }

    public boolean isLocked() {
        return getAvailablePermits() == 0;
    }

    public boolean holdsLock(Object owner) {
        return holdsLockImpl(owner) == 1;
    }

    private final int holdsLockImpl(Object owner) {
        while (isLocked()) {
            Object curOwner = owner$volatile$FU.get(this);
            if (curOwner != MutexKt.NO_OWNER) {
                return curOwner == owner ? 1 : 2;
            }
        }
        return 0;
    }

    static /* synthetic */ Object lock$suspendImpl(MutexImpl $this, Object owner, Continuation<? super Unit> $completion) {
        if ($this.tryLock(owner)) {
            return Unit.INSTANCE;
        }
        Object lockSuspend = $this.lockSuspend(owner, $completion);
        return lockSuspend == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? lockSuspend : Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    public final Object lockSuspend(Object owner, Continuation<? super Unit> $completion) {
        CancellableContinuationImpl cancellable$iv = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted($completion));
        try {
            acquire((CancellableContinuation<? super Unit>) new CancellableContinuationWithOwner(cancellable$iv, owner));
            Object result = cancellable$iv.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended($completion);
            }
            return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
        } catch (Throwable e$iv) {
            cancellable$iv.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
            throw e$iv;
        }
    }

    public boolean tryLock(Object owner) {
        switch (tryLockImpl(owner)) {
            case 0:
                return true;
            case 1:
                return false;
            case 2:
                throw new IllegalStateException(("This mutex is already locked by the specified owner: " + owner).toString());
            default:
                throw new IllegalStateException("unexpected".toString());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:1:0x0001 A[LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int tryLockImpl(java.lang.Object r6) {
        /*
            r5 = this;
        L_0x0001:
            boolean r0 = r5.tryAcquire()
            r1 = 1
            if (r0 == 0) goto L_0x0032
            boolean r0 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()
            r2 = 0
            if (r0 == 0) goto L_0x002a
            r0 = 0
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r3 = owner$volatile$FU
            java.lang.Object r3 = r3.get(r5)
            kotlinx.coroutines.internal.Symbol r4 = kotlinx.coroutines.sync.MutexKt.NO_OWNER
            if (r3 != r4) goto L_0x0020
            goto L_0x0021
        L_0x0020:
            r1 = r2
        L_0x0021:
            if (r1 == 0) goto L_0x0024
            goto L_0x002a
        L_0x0024:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x002a:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = owner$volatile$FU
            r0.set(r5, r6)
            return r2
        L_0x0032:
            if (r6 != 0) goto L_0x0035
            return r1
        L_0x0035:
            int r0 = r5.holdsLockImpl(r6)
            switch(r0) {
                case 0: goto L_0x0040;
                case 1: goto L_0x003e;
                case 2: goto L_0x003d;
                default: goto L_0x003c;
            }
        L_0x003c:
            goto L_0x0040
        L_0x003d:
            return r1
        L_0x003e:
            r0 = 2
            return r0
        L_0x0040:
            goto L_0x0001
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.sync.MutexImpl.tryLockImpl(java.lang.Object):int");
    }

    public void unlock(Object owner) {
        while (isLocked()) {
            Object curOwner = owner$volatile$FU.get(this);
            if (curOwner != MutexKt.NO_OWNER) {
                if (!(curOwner == owner || owner == null)) {
                    throw new IllegalStateException(("This mutex is locked by " + curOwner + ", but " + owner + " is expected").toString());
                } else if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(owner$volatile$FU, this, curOwner, MutexKt.NO_OWNER)) {
                    release();
                    return;
                }
            }
        }
        throw new IllegalStateException("This mutex is not locked".toString());
    }

    public SelectClause2<Object, Mutex> getOnLock() {
        MutexImpl$onLock$1 mutexImpl$onLock$1 = MutexImpl$onLock$1.INSTANCE;
        Intrinsics.checkNotNull(mutexImpl$onLock$1, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = \"clauseObject\")] kotlin.Any, @[ParameterName(name = \"select\")] kotlinx.coroutines.selects.SelectInstance<*>, @[ParameterName(name = \"param\")] kotlin.Any?, kotlin.Unit>");
        MutexImpl$onLock$2 mutexImpl$onLock$2 = MutexImpl$onLock$2.INSTANCE;
        Intrinsics.checkNotNull(mutexImpl$onLock$2, "null cannot be cast to non-null type kotlin.Function3<@[ParameterName(name = \"clauseObject\")] kotlin.Any, @[ParameterName(name = \"param\")] kotlin.Any?, @[ParameterName(name = \"clauseResult\")] kotlin.Any?, kotlin.Any?>");
        return new SelectClause2Impl<>(this, (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(mutexImpl$onLock$1, 3), (Function3) TypeIntrinsics.beforeCheckcastToFunctionOfArity(mutexImpl$onLock$2, 3), this.onSelectCancellationUnlockConstructor);
    }

    /* access modifiers changed from: protected */
    public void onLockRegFunction(SelectInstance<?> select, Object owner) {
        if (owner == null || !holdsLock(owner)) {
            Intrinsics.checkNotNull(select, "null cannot be cast to non-null type kotlinx.coroutines.selects.SelectInstanceInternal<*>");
            onAcquireRegFunction(new SelectInstanceWithOwner((SelectInstanceInternal) select, owner), owner);
            return;
        }
        select.selectInRegistrationPhase(MutexKt.ON_LOCK_ALREADY_LOCKED_BY_OWNER);
    }

    /* access modifiers changed from: protected */
    public Object onLockProcessResult(Object owner, Object result) {
        if (!Intrinsics.areEqual(result, (Object) MutexKt.ON_LOCK_ALREADY_LOCKED_BY_OWNER)) {
            return this;
        }
        throw new IllegalStateException(("This mutex is already locked by the specified owner: " + owner).toString());
    }

    @Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u001f\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\b\u0010\tJz\u0010\n\u001a\u0004\u0018\u00010\u0007\"\b\b\u0000\u0010\u000b*\u00020\u00022\u0006\u0010\f\u001a\u0002H\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u00072M\u0010\u000e\u001aI\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u0013\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\u0014¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u000fH\u0016¢\u0006\u0002\u0010\u0016Jn\u0010\u0017\u001a\u00020\u0002\"\b\b\u0000\u0010\u000b*\u00020\u00022\u0006\u0010\f\u001a\u0002H\u000b2M\u0010\u000e\u001aI\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u0013\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\u0014¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u000fH\u0016¢\u0006\u0002\u0010\u0018J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0010H\u0001J\u0011\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u0007H\u0001J\t\u0010\u001d\u001a\u00020\u0002H\u0001J2\u0010\u001e\u001a\u00020\u00022'\u0010\u001f\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0010¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\u00020!j\u0002` H\u0001J\u001d\u0010\u001e\u001a\u00020\u00022\n\u0010\"\u001a\u0006\u0012\u0002\b\u00030#2\u0006\u0010$\u001a\u00020%H\u0001J6\u0010\u0017\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u00022#\u0010\u000e\u001a\u001f\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\u0002\u0018\u00010!H\u0001J\u0015\u0010&\u001a\u00020\u0002*\u00020'2\u0006\u0010\f\u001a\u00020\u0002H\u0001J\u0015\u0010(\u001a\u00020\u0002*\u00020'2\u0006\u0010)\u001a\u00020\u0010H\u0001J\u0017\u0010*\u001a\u00020\u00022\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00020,H\u0001J\u001d\u0010\n\u001a\u0004\u0018\u00010\u00072\u0006\u0010\f\u001a\u00020\u00022\b\u0010\r\u001a\u0004\u0018\u00010\u0007H\u0001J\u0013\u0010-\u001a\u0004\u0018\u00010\u00072\u0006\u0010)\u001a\u00020\u0010H\u0001R\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u00058\u0006X\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0006X\u0004¢\u0006\u0002\n\u0000R\t\u0010\u0015\u001a\u00020\u0014X\u0005R\t\u0010.\u001a\u00020\u001aX\u0005R\t\u0010/\u001a\u00020\u001aX\u0005R\t\u00100\u001a\u00020\u001aX\u0005¨\u00061"}, d2 = {"Lkotlinx/coroutines/sync/MutexImpl$CancellableContinuationWithOwner;", "Lkotlinx/coroutines/CancellableContinuation;", "", "Lkotlinx/coroutines/Waiter;", "cont", "Lkotlinx/coroutines/CancellableContinuationImpl;", "owner", "", "<init>", "(Lkotlinx/coroutines/sync/MutexImpl;Lkotlinx/coroutines/CancellableContinuationImpl;Ljava/lang/Object;)V", "tryResume", "R", "value", "idempotent", "onCancellation", "Lkotlin/Function3;", "", "Lkotlin/ParameterName;", "name", "cause", "Lkotlin/coroutines/CoroutineContext;", "context", "(Lkotlin/Unit;Ljava/lang/Object;Lkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "resume", "(Lkotlin/Unit;Lkotlin/jvm/functions/Function3;)V", "cancel", "", "completeResume", "token", "initCancellability", "invokeOnCancellation", "handler", "Lkotlinx/coroutines/CompletionHandler;", "Lkotlin/Function1;", "segment", "Lkotlinx/coroutines/internal/Segment;", "index", "", "resumeUndispatched", "Lkotlinx/coroutines/CoroutineDispatcher;", "resumeUndispatchedWithException", "exception", "resumeWith", "result", "Lkotlin/Result;", "tryResumeWithException", "isActive", "isCancelled", "isCompleted", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: Mutex.kt */
    private final class CancellableContinuationWithOwner implements CancellableContinuation<Unit>, Waiter {
        public final CancellableContinuationImpl<Unit> cont;
        public final Object owner;

        public boolean cancel(Throwable th) {
            return this.cont.cancel(th);
        }

        public void completeResume(Object obj) {
            this.cont.completeResume(obj);
        }

        public CoroutineContext getContext() {
            return this.cont.getContext();
        }

        public void initCancellability() {
            this.cont.initCancellability();
        }

        public void invokeOnCancellation(Function1<? super Throwable, Unit> function1) {
            this.cont.invokeOnCancellation(function1);
        }

        public void invokeOnCancellation(Segment<?> segment, int i) {
            this.cont.invokeOnCancellation(segment, i);
        }

        public boolean isActive() {
            return this.cont.isActive();
        }

        public boolean isCancelled() {
            return this.cont.isCancelled();
        }

        public boolean isCompleted() {
            return this.cont.isCompleted();
        }

        @Deprecated(level = DeprecationLevel.WARNING, message = "Use the overload that also accepts the `value` and the coroutine context in lambda", replaceWith = @ReplaceWith(expression = "resume(value) { cause, _, _ -> onCancellation(cause) }", imports = {}))
        public void resume(Unit unit, Function1<? super Throwable, Unit> function1) {
            this.cont.resume(unit, function1);
        }

        public void resumeUndispatched(CoroutineDispatcher coroutineDispatcher, Unit unit) {
            this.cont.resumeUndispatched(coroutineDispatcher, unit);
        }

        public void resumeUndispatchedWithException(CoroutineDispatcher coroutineDispatcher, Throwable th) {
            this.cont.resumeUndispatchedWithException(coroutineDispatcher, th);
        }

        public void resumeWith(Object obj) {
            this.cont.resumeWith(obj);
        }

        public Object tryResume(Unit unit, Object obj) {
            return this.cont.tryResume(unit, obj);
        }

        public Object tryResumeWithException(Throwable th) {
            return this.cont.tryResumeWithException(th);
        }

        public CancellableContinuationWithOwner(CancellableContinuationImpl<? super Unit> cont2, Object owner2) {
            this.cont = cont2;
            this.owner = owner2;
        }

        public <R extends Unit> Object tryResume(R value, Object idempotent, Function3<? super Throwable, ? super R, ? super CoroutineContext, Unit> onCancellation) {
            MutexImpl mutexImpl = MutexImpl.this;
            boolean z = true;
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if (!(MutexImpl.owner$volatile$FU.get(mutexImpl) == MutexKt.NO_OWNER)) {
                    throw new AssertionError();
                }
            }
            Object token = this.cont.tryResume(value, idempotent, new MutexImpl$CancellableContinuationWithOwner$$ExternalSyntheticLambda0(MutexImpl.this, this));
            if (token != null) {
                MutexImpl mutexImpl2 = MutexImpl.this;
                if (DebugKt.getASSERTIONS_ENABLED()) {
                    if (MutexImpl.owner$volatile$FU.get(mutexImpl2) != MutexKt.NO_OWNER) {
                        z = false;
                    }
                    if (!z) {
                        throw new AssertionError();
                    }
                }
                MutexImpl.owner$volatile$FU.set(MutexImpl.this, this.owner);
            }
            return token;
        }

        /* access modifiers changed from: private */
        public static final Unit tryResume$lambda$3(MutexImpl this$02, CancellableContinuationWithOwner this$1, Throwable th, Unit unit, CoroutineContext coroutineContext) {
            if (DebugKt.getASSERTIONS_ENABLED()) {
                Object it = MutexImpl.owner$volatile$FU.get(this$02);
                if (!(it == MutexKt.NO_OWNER || it == this$1.owner)) {
                    throw new AssertionError();
                }
            }
            MutexImpl.owner$volatile$FU.set(this$02, this$1.owner);
            this$02.unlock(this$1.owner);
            return Unit.INSTANCE;
        }

        public <R extends Unit> void resume(R value, Function3<? super Throwable, ? super R, ? super CoroutineContext, Unit> onCancellation) {
            MutexImpl mutexImpl = MutexImpl.this;
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if (!(MutexImpl.owner$volatile$FU.get(mutexImpl) == MutexKt.NO_OWNER)) {
                    throw new AssertionError();
                }
            }
            MutexImpl.owner$volatile$FU.set(MutexImpl.this, this.owner);
            this.cont.resume(value, (Function1<? super Throwable, Unit>) new MutexImpl$CancellableContinuationWithOwner$$ExternalSyntheticLambda1(MutexImpl.this, this));
        }

        /* access modifiers changed from: private */
        public static final Unit resume$lambda$6(MutexImpl this$02, CancellableContinuationWithOwner this$1, Throwable it) {
            this$02.unlock(this$1.owner);
            return Unit.INSTANCE;
        }
    }

    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0004\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001f\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00052\b\u0010\u000b\u001a\u0004\u0018\u00010\u0005H\u0016J\u0012\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0005H\u0016J\u0011\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0001J\u001d\u0010\u0012\u001a\u00020\r2\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0001R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u00028\u0006X\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0004¢\u0006\u0002\n\u0000R\t\u0010\u0017\u001a\u00020\u0018X\u0005¨\u0006\u0019"}, d2 = {"Lkotlinx/coroutines/sync/MutexImpl$SelectInstanceWithOwner;", "Q", "Lkotlinx/coroutines/selects/SelectInstanceInternal;", "select", "owner", "", "<init>", "(Lkotlinx/coroutines/sync/MutexImpl;Lkotlinx/coroutines/selects/SelectInstanceInternal;Ljava/lang/Object;)V", "trySelect", "", "clauseObject", "result", "selectInRegistrationPhase", "", "internalResult", "disposeOnCompletion", "disposableHandle", "Lkotlinx/coroutines/DisposableHandle;", "invokeOnCancellation", "segment", "Lkotlinx/coroutines/internal/Segment;", "index", "", "context", "Lkotlin/coroutines/CoroutineContext;", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: Mutex.kt */
    private final class SelectInstanceWithOwner<Q> implements SelectInstanceInternal<Q> {
        public final Object owner;
        public final SelectInstanceInternal<Q> select;

        public void disposeOnCompletion(DisposableHandle disposableHandle) {
            this.select.disposeOnCompletion(disposableHandle);
        }

        public CoroutineContext getContext() {
            return this.select.getContext();
        }

        public void invokeOnCancellation(Segment<?> segment, int i) {
            this.select.invokeOnCancellation(segment, i);
        }

        public SelectInstanceWithOwner(SelectInstanceInternal<Q> select2, Object owner2) {
            this.select = select2;
            this.owner = owner2;
        }

        public boolean trySelect(Object clauseObject, Object result) {
            MutexImpl mutexImpl = MutexImpl.this;
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if (!(MutexImpl.owner$volatile$FU.get(mutexImpl) == MutexKt.NO_OWNER)) {
                    throw new AssertionError();
                }
            }
            boolean success = this.select.trySelect(clauseObject, result);
            MutexImpl mutexImpl2 = MutexImpl.this;
            if (success) {
                MutexImpl.owner$volatile$FU.set(mutexImpl2, this.owner);
            }
            return success;
        }

        public void selectInRegistrationPhase(Object internalResult) {
            MutexImpl mutexImpl = MutexImpl.this;
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if (!(MutexImpl.owner$volatile$FU.get(mutexImpl) == MutexKt.NO_OWNER)) {
                    throw new AssertionError();
                }
            }
            MutexImpl.owner$volatile$FU.set(MutexImpl.this, this.owner);
            this.select.selectInRegistrationPhase(internalResult);
        }
    }

    public String toString() {
        return "Mutex@" + DebugStringsKt.getHexAddress(this) + "[isLocked=" + isLocked() + ",owner=" + owner$volatile$FU.get(this) + ']';
    }
}
