package kotlinx.coroutines.sync;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import com.google.common.util.concurrent.Striped$SmallLazyStriped$$ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.Waiter;
import kotlinx.coroutines.internal.ConcurrentLinkedListKt;
import kotlinx.coroutines.internal.Segment;
import kotlinx.coroutines.internal.SegmentOrClosed;
import kotlinx.coroutines.selects.SelectInstance;

@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0010\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u0016H@¢\u0006\u0002\u0010\u001bJ\u000e\u0010\u001c\u001a\u00020\u0016H@¢\u0006\u0002\u0010\u001bJ\u0016\u0010\u001a\u001a\u00020\u00162\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00160\u001eH\u0005Jb\u0010\u001a\u001a\u00020\u0016\"\u0004\b\u0000\u0010\u001f2\u0006\u0010\u001d\u001a\u0002H\u001f2!\u0010 \u001a\u001d\u0012\u0013\u0012\u0011H\u001f¢\u0006\f\b\"\u0012\b\b#\u0012\u0004\b\b(\u001d\u0012\u0004\u0012\u00020\u00190!2!\u0010$\u001a\u001d\u0012\u0013\u0012\u0011H\u001f¢\u0006\f\b\"\u0012\b\b#\u0012\u0004\b\b(\u001d\u0012\u0004\u0012\u00020\u00160!H\b¢\u0006\u0002\u0010%J\u001e\u0010&\u001a\u00020\u00162\n\u0010'\u001a\u0006\u0012\u0002\b\u00030(2\b\u0010)\u001a\u0004\u0018\u00010\u0001H\u0004J\b\u0010*\u001a\u00020\u0003H\u0002J\u0006\u0010+\u001a\u00020\u0016J\b\u0010,\u001a\u00020\u0016H\u0002J\u0010\u0010-\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020.H\u0002J\b\u0010/\u001a\u00020\u0019H\u0002J\f\u00100\u001a\u00020\u0019*\u00020\u0001H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0004R\t\u0010\n\u001a\u00020\u000bX\u0004R\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0004R\t\u0010\r\u001a\u00020\u000bX\u0004R\t\u0010\u000e\u001a\u00020\u000fX\u0004R\u0011\u0010\u0010\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R&\u0010\u0013\u001a\u001a\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00160\u0014X\u0004¢\u0006\u0002\n\u0000¨\u00061"}, d2 = {"Lkotlinx/coroutines/sync/SemaphoreAndMutexImpl;", "", "permits", "", "acquiredPermits", "<init>", "(II)V", "head", "Lkotlinx/atomicfu/AtomicRef;", "Lkotlinx/coroutines/sync/SemaphoreSegment;", "deqIdx", "Lkotlinx/atomicfu/AtomicLong;", "tail", "enqIdx", "_availablePermits", "Lkotlinx/atomicfu/AtomicInt;", "availablePermits", "getAvailablePermits", "()I", "onCancellationRelease", "Lkotlin/Function3;", "", "", "Lkotlin/coroutines/CoroutineContext;", "tryAcquire", "", "acquire", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "acquireSlowPath", "waiter", "Lkotlinx/coroutines/CancellableContinuation;", "W", "suspend", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "onAcquired", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "onAcquireRegFunction", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "ignoredParam", "decPermits", "release", "coerceAvailablePermitsAtMaximum", "addAcquireToQueue", "Lkotlinx/coroutines/Waiter;", "tryResumeNextFromQueue", "tryResumeAcquire", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: Semaphore.kt */
public class SemaphoreAndMutexImpl {
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicIntegerFieldUpdater _availablePermits$volatile$FU;
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicLongFieldUpdater deqIdx$volatile$FU;
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicLongFieldUpdater enqIdx$volatile$FU;
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicReferenceFieldUpdater head$volatile$FU;
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicReferenceFieldUpdater tail$volatile$FU;
    private volatile /* synthetic */ int _availablePermits$volatile;
    private volatile /* synthetic */ long deqIdx$volatile;
    private volatile /* synthetic */ long enqIdx$volatile;
    private volatile /* synthetic */ Object head$volatile;
    private final Function3<Throwable, Unit, CoroutineContext, Unit> onCancellationRelease;
    private final int permits;
    private volatile /* synthetic */ Object tail$volatile;

    static {
        Class<SemaphoreAndMutexImpl> cls = SemaphoreAndMutexImpl.class;
        head$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "head$volatile");
        deqIdx$volatile$FU = AtomicLongFieldUpdater.newUpdater(cls, "deqIdx$volatile");
        tail$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "tail$volatile");
        enqIdx$volatile$FU = AtomicLongFieldUpdater.newUpdater(cls, "enqIdx$volatile");
        _availablePermits$volatile$FU = AtomicIntegerFieldUpdater.newUpdater(cls, "_availablePermits$volatile");
    }

    private final /* synthetic */ long getDeqIdx$volatile() {
        return this.deqIdx$volatile;
    }

    private final /* synthetic */ long getEnqIdx$volatile() {
        return this.enqIdx$volatile;
    }

    private final /* synthetic */ Object getHead$volatile() {
        return this.head$volatile;
    }

    private final /* synthetic */ Object getTail$volatile() {
        return this.tail$volatile;
    }

    private final /* synthetic */ int get_availablePermits$volatile() {
        return this._availablePermits$volatile;
    }

    private final /* synthetic */ void setDeqIdx$volatile(long j) {
        this.deqIdx$volatile = j;
    }

    private final /* synthetic */ void setEnqIdx$volatile(long j) {
        this.enqIdx$volatile = j;
    }

    private final /* synthetic */ void setHead$volatile(Object obj) {
        this.head$volatile = obj;
    }

    private final /* synthetic */ void setTail$volatile(Object obj) {
        this.tail$volatile = obj;
    }

    private final /* synthetic */ void set_availablePermits$volatile(int i) {
        this._availablePermits$volatile = i;
    }

    public SemaphoreAndMutexImpl(int permits2, int acquiredPermits) {
        this.permits = permits2;
        boolean z = true;
        if (this.permits > 0) {
            if ((acquiredPermits < 0 || acquiredPermits > this.permits) ? false : z) {
                SemaphoreSegment s = new SemaphoreSegment(0, (SemaphoreSegment) null, 2);
                this.head$volatile = s;
                this.tail$volatile = s;
                this._availablePermits$volatile = this.permits - acquiredPermits;
                this.onCancellationRelease = new SemaphoreAndMutexImpl$$ExternalSyntheticLambda0(this);
                return;
            }
            throw new IllegalArgumentException(("The number of acquired permits should be in 0.." + this.permits).toString());
        }
        throw new IllegalArgumentException(("Semaphore should have at least 1 permit, but had " + this.permits).toString());
    }

    public final int getAvailablePermits() {
        return Math.max(_availablePermits$volatile$FU.get(this), 0);
    }

    /* access modifiers changed from: private */
    public static final Unit onCancellationRelease$lambda$2(SemaphoreAndMutexImpl this$0, Throwable th, Unit unit, CoroutineContext coroutineContext) {
        this$0.release();
        return Unit.INSTANCE;
    }

    public final boolean tryAcquire() {
        while (true) {
            int p = _availablePermits$volatile$FU.get(this);
            if (p > this.permits) {
                coerceAvailablePermitsAtMaximum();
            } else if (p <= 0) {
                return false;
            } else {
                if (_availablePermits$volatile$FU.compareAndSet(this, p, p - 1)) {
                    return true;
                }
            }
        }
    }

    public final Object acquire(Continuation<? super Unit> $completion) {
        if (decPermits() > 0) {
            return Unit.INSTANCE;
        }
        Object acquireSlowPath = acquireSlowPath($completion);
        return acquireSlowPath == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? acquireSlowPath : Unit.INSTANCE;
    }

    /* access modifiers changed from: private */
    public final Object acquireSlowPath(Continuation<? super Unit> $completion) {
        CancellableContinuationImpl orCreateCancellableContinuation = CancellableContinuationKt.getOrCreateCancellableContinuation(IntrinsicsKt.intercepted($completion));
        CancellableContinuationImpl cont = orCreateCancellableContinuation;
        try {
            if (!addAcquireToQueue(cont)) {
                acquire((CancellableContinuation<? super Unit>) cont);
            }
            Object result = orCreateCancellableContinuation.getResult();
            if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended($completion);
            }
            return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
        } catch (Throwable e$iv) {
            orCreateCancellableContinuation.releaseClaimedReusableContinuation$kotlinx_coroutines_core();
            throw e$iv;
        }
    }

    /* access modifiers changed from: protected */
    public final void acquire(CancellableContinuation<? super Unit> waiter) {
        while (decPermits() <= 0) {
            CancellableContinuation<? super Unit> cancellableContinuation = waiter;
            Intrinsics.checkNotNull(cancellableContinuation, "null cannot be cast to non-null type kotlinx.coroutines.Waiter");
            if (addAcquireToQueue((Waiter) cancellableContinuation)) {
                return;
            }
        }
        waiter.resume(Unit.INSTANCE, (Function3<? super Throwable, ? super R, ? super CoroutineContext, Unit>) this.onCancellationRelease);
    }

    private final <W> void acquire(W waiter, Function1<? super W, Boolean> suspend, Function1<? super W, Unit> onAcquired) {
        while (decPermits() <= 0) {
            if (suspend.invoke(waiter).booleanValue()) {
                return;
            }
        }
        onAcquired.invoke(waiter);
    }

    /* access modifiers changed from: protected */
    public final void onAcquireRegFunction(SelectInstance<?> select, Object ignoredParam) {
        while (decPermits() <= 0) {
            SelectInstance<?> selectInstance = select;
            Intrinsics.checkNotNull(selectInstance, "null cannot be cast to non-null type kotlinx.coroutines.Waiter");
            if (addAcquireToQueue((Waiter) selectInstance)) {
                return;
            }
        }
        select.selectInRegistrationPhase(Unit.INSTANCE);
    }

    private final int decPermits() {
        int p;
        do {
            p = _availablePermits$volatile$FU.getAndDecrement(this);
        } while (p > this.permits);
        return p;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0017 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x000d A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void release() {
        /*
            r4 = this;
        L_0x0001:
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater r0 = _availablePermits$volatile$FU
            int r0 = r0.getAndIncrement(r4)
            int r1 = r4.permits
            if (r0 >= r1) goto L_0x0017
            if (r0 < 0) goto L_0x0010
            return
        L_0x0010:
            boolean r1 = r4.tryResumeNextFromQueue()
            if (r1 == 0) goto L_0x0001
            return
        L_0x0017:
            r4.coerceAvailablePermitsAtMaximum()
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "The number of released permits cannot be greater than "
            java.lang.StringBuilder r2 = r2.append(r3)
            int r3 = r4.permits
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.sync.SemaphoreAndMutexImpl.release():void");
    }

    private final void coerceAvailablePermitsAtMaximum() {
        int cur;
        do {
            cur = _availablePermits$volatile$FU.get(this);
            if (cur <= this.permits || _availablePermits$volatile$FU.compareAndSet(this, cur, this.permits)) {
            }
            cur = _availablePermits$volatile$FU.get(this);
            return;
        } while (_availablePermits$volatile$FU.compareAndSet(this, cur, this.permits));
    }

    /* access modifiers changed from: private */
    public final boolean addAcquireToQueue(Waiter waiter) {
        Object s$iv;
        long enqIdx;
        boolean z;
        boolean z2;
        SemaphoreSegment curTail;
        boolean z3;
        Waiter waiter2 = waiter;
        SemaphoreSegment curTail2 = (SemaphoreSegment) tail$volatile$FU.get(this);
        long enqIdx2 = enqIdx$volatile$FU.getAndIncrement(this);
        KFunction createNewSegment = SemaphoreAndMutexImpl$addAcquireToQueue$createNewSegment$1.INSTANCE;
        AtomicReferenceFieldUpdater handler$atomicfu$iv = tail$volatile$FU;
        long id$iv = enqIdx2 / ((long) SemaphoreKt.SEGMENT_SIZE);
        while (true) {
            s$iv = ConcurrentLinkedListKt.findSegmentInternal(curTail2, id$iv, (Function2) createNewSegment);
            if (SegmentOrClosed.m1602isClosedimpl(s$iv)) {
                enqIdx = enqIdx2;
                z = false;
                z2 = true;
                break;
            }
            Segment to$iv$iv = SegmentOrClosed.m1600getSegmentimpl(s$iv);
            while (true) {
                Segment cur$iv$iv = (Segment) handler$atomicfu$iv.get(this);
                z = false;
                z2 = true;
                curTail = curTail2;
                enqIdx = enqIdx2;
                if (cur$iv$iv.id >= to$iv$iv.id) {
                    z3 = true;
                    break;
                } else if (!to$iv$iv.tryIncPointers$kotlinx_coroutines_core()) {
                    z3 = false;
                    break;
                } else if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(handler$atomicfu$iv, this, cur$iv$iv, to$iv$iv)) {
                    if (cur$iv$iv.decPointers$kotlinx_coroutines_core()) {
                        cur$iv$iv.remove();
                    }
                    z3 = true;
                } else {
                    if (to$iv$iv.decPointers$kotlinx_coroutines_core()) {
                        to$iv$iv.remove();
                    }
                    curTail2 = curTail;
                    enqIdx2 = enqIdx;
                }
            }
            if (z3) {
                break;
            }
            curTail2 = curTail;
            enqIdx2 = enqIdx;
        }
        SemaphoreSegment segment = (SemaphoreSegment) SegmentOrClosed.m1600getSegmentimpl(s$iv);
        int i = (int) (enqIdx % ((long) SemaphoreKt.SEGMENT_SIZE));
        if (Striped$SmallLazyStriped$$ExternalSyntheticBackportWithForwarding0.m(segment.getAcquirers(), i, (Object) null, waiter2)) {
            waiter2.invokeOnCancellation(segment, i);
            return z2;
        }
        if (Striped$SmallLazyStriped$$ExternalSyntheticBackportWithForwarding0.m(segment.getAcquirers(), i, SemaphoreKt.PERMIT, SemaphoreKt.TAKEN)) {
            if (waiter2 instanceof CancellableContinuation) {
                Intrinsics.checkNotNull(waiter2, "null cannot be cast to non-null type kotlinx.coroutines.CancellableContinuation<kotlin.Unit>");
                CancellableContinuation cancellableContinuation = (CancellableContinuation) waiter2;
                ((CancellableContinuation) waiter2).resume(Unit.INSTANCE, this.onCancellationRelease);
            } else if (waiter2 instanceof SelectInstance) {
                ((SelectInstance) waiter2).selectInRegistrationPhase(Unit.INSTANCE);
            } else {
                throw new IllegalStateException(("unexpected: " + waiter2).toString());
            }
            return z2;
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(segment.getAcquirers().get(i) == SemaphoreKt.BROKEN ? z2 : z)) {
                throw new AssertionError();
            }
        }
        return z;
    }

    private final boolean tryResumeNextFromQueue() {
        Object s$iv;
        boolean z;
        boolean z2;
        Segment to$iv$iv;
        SemaphoreSegment curHead = (SemaphoreSegment) head$volatile$FU.get(this);
        long deqIdx = deqIdx$volatile$FU.getAndIncrement(this);
        long id = deqIdx / ((long) SemaphoreKt.SEGMENT_SIZE);
        KFunction createNewSegment = SemaphoreAndMutexImpl$tryResumeNextFromQueue$createNewSegment$1.INSTANCE;
        AtomicReferenceFieldUpdater handler$atomicfu$iv = head$volatile$FU;
        while (true) {
            s$iv = ConcurrentLinkedListKt.findSegmentInternal(curHead, id, (Function2) createNewSegment);
            if (SegmentOrClosed.m1602isClosedimpl(s$iv)) {
                z = false;
                z2 = true;
                break;
            }
            Segment to$iv$iv2 = SegmentOrClosed.m1600getSegmentimpl(s$iv);
            while (true) {
                Segment cur$iv$iv = (Segment) handler$atomicfu$iv.get(this);
                z = false;
                z2 = true;
                if (cur$iv$iv.id >= to$iv$iv2.id) {
                    to$iv$iv = 1;
                    continue;
                    break;
                } else if (!to$iv$iv2.tryIncPointers$kotlinx_coroutines_core()) {
                    to$iv$iv = null;
                    continue;
                    break;
                } else if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(handler$atomicfu$iv, this, cur$iv$iv, to$iv$iv2)) {
                    if (cur$iv$iv.decPointers$kotlinx_coroutines_core()) {
                        cur$iv$iv.remove();
                    }
                    to$iv$iv = 1;
                    continue;
                } else if (to$iv$iv2.decPointers$kotlinx_coroutines_core()) {
                    to$iv$iv2.remove();
                }
            }
            if (to$iv$iv != null) {
                break;
            }
        }
        SemaphoreSegment segment = (SemaphoreSegment) SegmentOrClosed.m1600getSegmentimpl(s$iv);
        segment.cleanPrev();
        if (segment.id > id) {
            return z;
        }
        int i = (int) (deqIdx % ((long) SemaphoreKt.SEGMENT_SIZE));
        Object cellState = segment.getAcquirers().getAndSet(i, SemaphoreKt.PERMIT);
        if (cellState == null) {
            int access$getMAX_SPIN_CYCLES$p = SemaphoreKt.MAX_SPIN_CYCLES;
            int i2 = z;
            while (i2 < access$getMAX_SPIN_CYCLES$p) {
                int i3 = i2;
                SemaphoreSegment curHead2 = curHead;
                if (segment.getAcquirers().get(i) == SemaphoreKt.TAKEN) {
                    return z2;
                }
                i2++;
                curHead = curHead2;
            }
            return !Striped$SmallLazyStriped$$ExternalSyntheticBackportWithForwarding0.m(segment.getAcquirers(), i, SemaphoreKt.PERMIT, SemaphoreKt.BROKEN);
        }
        if (cellState == SemaphoreKt.CANCELLED) {
            return z;
        }
        return tryResumeAcquire(cellState);
    }

    private final boolean tryResumeAcquire(Object $this$tryResumeAcquire) {
        if ($this$tryResumeAcquire instanceof CancellableContinuation) {
            Intrinsics.checkNotNull($this$tryResumeAcquire, "null cannot be cast to non-null type kotlinx.coroutines.CancellableContinuation<kotlin.Unit>");
            CancellableContinuation cancellableContinuation = (CancellableContinuation) $this$tryResumeAcquire;
            Object token = ((CancellableContinuation) $this$tryResumeAcquire).tryResume(Unit.INSTANCE, (Object) null, this.onCancellationRelease);
            if (token == null) {
                return false;
            }
            ((CancellableContinuation) $this$tryResumeAcquire).completeResume(token);
            return true;
        } else if ($this$tryResumeAcquire instanceof SelectInstance) {
            return ((SelectInstance) $this$tryResumeAcquire).trySelect(this, Unit.INSTANCE);
        } else {
            throw new IllegalStateException(("unexpected: " + $this$tryResumeAcquire).toString());
        }
    }
}
