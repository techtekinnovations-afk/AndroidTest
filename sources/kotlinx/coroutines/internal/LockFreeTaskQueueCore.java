package kotlinx.coroutines.internal;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.DebugKt;

@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000 0*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0002:\u0002/0B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u0006\u0010\u0015\u001a\u00020\u0006J\u0013\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00028\u0000¢\u0006\u0002\u0010\u0018J1\u0010\u0019\u001a\u0016\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u001a2\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010\u001cJ\b\u0010\u001d\u001a\u0004\u0018\u00010\u0002J1\u0010\u001e\u001a\u0016\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000j\n\u0012\u0004\u0012\u00028\u0000\u0018\u0001`\u001a2\u0006\u0010\u001f\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u0004H\u0002¢\u0006\u0002\u0010!J\f\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000J\b\u0010#\u001a\u00020$H\u0002J%\u0010%\u001a\u0012\u0012\u0004\u0012\u00028\u00000\u0000j\b\u0012\u0004\u0012\u00028\u0000`\u001a2\u0006\u0010&\u001a\u00020$H\u0002¢\u0006\u0002\u0010'J%\u0010(\u001a\u0012\u0012\u0004\u0012\u00028\u00000\u0000j\b\u0012\u0004\u0012\u00028\u0000`\u001a2\u0006\u0010&\u001a\u00020$H\u0002¢\u0006\u0002\u0010'J&\u0010)\u001a\b\u0012\u0004\u0012\u0002H+0*\"\u0004\b\u0001\u0010+2\u0012\u0010,\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u0002H+0-J\u0006\u0010.\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00000\u000bX\u0004R\t\u0010\f\u001a\u00020\rX\u0004R\u0011\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u000fX\u0004R\u0011\u0010\u0010\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u00061"}, d2 = {"Lkotlinx/coroutines/internal/LockFreeTaskQueueCore;", "E", "", "capacity", "", "singleConsumer", "", "<init>", "(IZ)V", "mask", "_next", "Lkotlinx/atomicfu/AtomicRef;", "_state", "Lkotlinx/atomicfu/AtomicLong;", "array", "Lkotlinx/atomicfu/AtomicArray;", "isEmpty", "()Z", "size", "getSize", "()I", "close", "addLast", "element", "(Ljava/lang/Object;)I", "fillPlaceholder", "Lkotlinx/coroutines/internal/Core;", "index", "(ILjava/lang/Object;)Lkotlinx/coroutines/internal/LockFreeTaskQueueCore;", "removeFirstOrNull", "removeSlowPath", "oldHead", "newHead", "(II)Lkotlinx/coroutines/internal/LockFreeTaskQueueCore;", "next", "markFrozen", "", "allocateOrGetNextCopy", "state", "(J)Lkotlinx/coroutines/internal/LockFreeTaskQueueCore;", "allocateNextCopy", "map", "", "R", "transform", "Lkotlin/Function1;", "isClosed", "Placeholder", "Companion", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: LockFreeTaskQueue.kt */
public final class LockFreeTaskQueueCore<E> {
    public static final int ADD_CLOSED = 2;
    public static final int ADD_FROZEN = 1;
    public static final int ADD_SUCCESS = 0;
    public static final int CAPACITY_BITS = 30;
    public static final long CLOSED_MASK = 2305843009213693952L;
    public static final int CLOSED_SHIFT = 61;
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    public static final long FROZEN_MASK = 1152921504606846976L;
    public static final int FROZEN_SHIFT = 60;
    public static final long HEAD_MASK = 1073741823;
    public static final int HEAD_SHIFT = 0;
    public static final int INITIAL_CAPACITY = 8;
    public static final int MAX_CAPACITY_MASK = 1073741823;
    public static final int MIN_ADD_SPIN_CAPACITY = 1024;
    public static final Symbol REMOVE_FROZEN = new Symbol("REMOVE_FROZEN");
    public static final long TAIL_MASK = 1152921503533105152L;
    public static final int TAIL_SHIFT = 30;
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicReferenceFieldUpdater _next$volatile$FU;
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicLongFieldUpdater _state$volatile$FU;
    private volatile /* synthetic */ Object _next$volatile;
    private volatile /* synthetic */ long _state$volatile;
    private final /* synthetic */ AtomicReferenceArray array = new AtomicReferenceArray(this.capacity);
    private final int capacity;
    private final int mask = (this.capacity - 1);
    private final boolean singleConsumer;

    private final /* synthetic */ AtomicReferenceArray getArray() {
        return this.array;
    }

    private final /* synthetic */ Object get_next$volatile() {
        return this._next$volatile;
    }

    private final /* synthetic */ long get_state$volatile() {
        return this._state$volatile;
    }

    private final /* synthetic */ void loop$atomicfu(Object obj, AtomicLongFieldUpdater atomicLongFieldUpdater, Function1<? super Long, Unit> function1) {
        while (true) {
            function1.invoke(Long.valueOf(atomicLongFieldUpdater.get(obj)));
        }
    }

    private final /* synthetic */ void loop$atomicfu(Object obj, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, Function1<Object, Unit> function1) {
        while (true) {
            function1.invoke(atomicReferenceFieldUpdater.get(obj));
        }
    }

    private final /* synthetic */ void set_next$volatile(Object obj) {
        this._next$volatile = obj;
    }

    private final /* synthetic */ void set_state$volatile(long j) {
        this._state$volatile = j;
    }

    private final /* synthetic */ void update$atomicfu(Object obj, AtomicLongFieldUpdater atomicLongFieldUpdater, Function1<? super Long, Long> function1) {
        while (true) {
            long j = atomicLongFieldUpdater.get(obj);
            Object obj2 = obj;
            AtomicLongFieldUpdater atomicLongFieldUpdater2 = atomicLongFieldUpdater;
            if (!atomicLongFieldUpdater2.compareAndSet(obj2, j, function1.invoke(Long.valueOf(j)).longValue())) {
                atomicLongFieldUpdater = atomicLongFieldUpdater2;
                obj = obj2;
            } else {
                return;
            }
        }
    }

    private final /* synthetic */ long updateAndGet$atomicfu(Object obj, AtomicLongFieldUpdater atomicLongFieldUpdater, Function1<? super Long, Long> function1) {
        while (true) {
            long j = atomicLongFieldUpdater.get(obj);
            Number invoke = function1.invoke(Long.valueOf(j));
            Object obj2 = obj;
            AtomicLongFieldUpdater atomicLongFieldUpdater2 = atomicLongFieldUpdater;
            if (atomicLongFieldUpdater2.compareAndSet(obj2, j, invoke.longValue())) {
                return invoke.longValue();
            }
            atomicLongFieldUpdater = atomicLongFieldUpdater2;
            obj = obj2;
        }
    }

    public LockFreeTaskQueueCore(int capacity2, boolean singleConsumer2) {
        this.capacity = capacity2;
        this.singleConsumer = singleConsumer2;
        boolean z = true;
        if (this.mask <= 1073741823) {
            if (!((this.capacity & this.mask) != 0 ? false : z)) {
                throw new IllegalStateException("Check failed.".toString());
            }
            return;
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    public final boolean isEmpty() {
        Companion companion = Companion;
        long $this$withState$iv = _state$volatile$FU.get(this);
        if (((int) ((HEAD_MASK & $this$withState$iv) >> 0)) == ((int) ((TAIL_MASK & $this$withState$iv) >> 30))) {
            return true;
        }
        return false;
    }

    public final int getSize() {
        Companion companion = Companion;
        long $this$withState$iv = _state$volatile$FU.get(this);
        return (((int) ((TAIL_MASK & $this$withState$iv) >> 30)) - ((int) ((HEAD_MASK & $this$withState$iv) >> 0))) & MAX_CAPACITY_MASK;
    }

    public final boolean close() {
        long j;
        long state;
        AtomicLongFieldUpdater handler$atomicfu$iv = _state$volatile$FU;
        do {
            j = handler$atomicfu$iv.get(this);
            state = j;
            if ((state & CLOSED_MASK) != 0) {
                return true;
            }
            if ((FROZEN_MASK & state) != 0) {
                return false;
            }
        } while (!handler$atomicfu$iv.compareAndSet(this, j, state | CLOSED_MASK));
        return true;
    }

    public final int addLast(E element) {
        int i;
        LockFreeTaskQueueCore fillPlaceholder;
        LockFreeTaskQueueCore lockFreeTaskQueueCore = this;
        E e = element;
        LockFreeTaskQueueCore this_$iv = this;
        AtomicLongFieldUpdater handler$atomicfu$iv = _state$volatile$FU;
        while (true) {
            long state = handler$atomicfu$iv.get(lockFreeTaskQueueCore);
            if ((3458764513820540928L & state) != 0) {
                return Companion.addFailReason(state);
            }
            Companion companion = Companion;
            long $this$withState$iv = state;
            int head$iv = (int) ((HEAD_MASK & $this$withState$iv) >> 0);
            int tail$iv = (int) ((TAIL_MASK & $this$withState$iv) >> 30);
            int head = head$iv;
            int tail = tail$iv;
            int mask2 = lockFreeTaskQueueCore.mask;
            int i2 = head$iv;
            if (((tail + 2) & mask2) == (head & mask2)) {
                return 1;
            }
            if (!lockFreeTaskQueueCore.singleConsumer) {
                AtomicReferenceArray array2 = lockFreeTaskQueueCore.getArray();
                i = MAX_CAPACITY_MASK;
                if (array2.get(tail & mask2) != null) {
                    if (lockFreeTaskQueueCore.capacity < 1024 || ((tail - head) & MAX_CAPACITY_MASK) > (lockFreeTaskQueueCore.capacity >> 1)) {
                        return 1;
                    }
                }
            } else {
                i = MAX_CAPACITY_MASK;
            }
            AtomicLongFieldUpdater atomicLongFieldUpdater = _state$volatile$FU;
            AtomicLongFieldUpdater atomicLongFieldUpdater2 = atomicLongFieldUpdater;
            int i3 = tail$iv;
            LockFreeTaskQueueCore lockFreeTaskQueueCore2 = this_$iv;
            int tail2 = tail;
            long updateTail = Companion.updateTail(state, (tail + 1) & i);
            LockFreeTaskQueueCore this_$iv2 = lockFreeTaskQueueCore2;
            if (atomicLongFieldUpdater2.compareAndSet(lockFreeTaskQueueCore, state, updateTail)) {
                getArray().set(tail2 & mask2, e);
                LockFreeTaskQueueCore cur = this;
                while ((_state$volatile$FU.get(cur) & FROZEN_MASK) != 0 && (fillPlaceholder = cur.next().fillPlaceholder(tail2, e)) != null) {
                    cur = fillPlaceholder;
                }
                return 0;
            }
            lockFreeTaskQueueCore = this;
            this_$iv = this_$iv2;
        }
        return 1;
    }

    private final LockFreeTaskQueueCore<E> fillPlaceholder(int index, E element) {
        Object old = getArray().get(this.mask & index);
        if (!(old instanceof Placeholder) || ((Placeholder) old).index != index) {
            return null;
        }
        getArray().set(this.mask & index, element);
        return this;
    }

    public final Object removeFirstOrNull() {
        AtomicLongFieldUpdater handler$atomicfu$iv = _state$volatile$FU;
        LockFreeTaskQueueCore this_$iv = this;
        while (true) {
            long state = handler$atomicfu$iv.get(this);
            if ((FROZEN_MASK & state) != 0) {
                return REMOVE_FROZEN;
            }
            Companion companion = Companion;
            long $this$withState$iv = state;
            int head = (int) ((HEAD_MASK & $this$withState$iv) >> 0);
            AtomicLongFieldUpdater handler$atomicfu$iv2 = handler$atomicfu$iv;
            if ((this.mask & ((int) ((TAIL_MASK & $this$withState$iv) >> 30))) == (this.mask & head)) {
                return null;
            }
            Object element = getArray().get(this.mask & head);
            if (element == null) {
                if (this.singleConsumer) {
                    return null;
                }
                handler$atomicfu$iv = handler$atomicfu$iv2;
            } else if (element instanceof Placeholder) {
                return null;
            } else {
                int newHead = (head + 1) & MAX_CAPACITY_MASK;
                int head2 = head;
                int head3 = head2;
                Object element2 = element;
                LockFreeTaskQueueCore lockFreeTaskQueueCore = this_$iv;
                int newHead2 = newHead;
                LockFreeTaskQueueCore this_$iv2 = lockFreeTaskQueueCore;
                if (_state$volatile$FU.compareAndSet(this, state, Companion.updateHead(state, newHead))) {
                    getArray().set(this.mask & head3, (Object) null);
                    return element2;
                } else if (this.singleConsumer) {
                    LockFreeTaskQueueCore cur = this;
                    while (true) {
                        LockFreeTaskQueueCore removeSlowPath = cur.removeSlowPath(head3, newHead2);
                        if (removeSlowPath == null) {
                            return element2;
                        }
                        cur = removeSlowPath;
                    }
                } else {
                    handler$atomicfu$iv = handler$atomicfu$iv2;
                    this_$iv = this_$iv2;
                }
            }
        }
    }

    private final LockFreeTaskQueueCore<E> removeSlowPath(int oldHead, int newHead) {
        AtomicLongFieldUpdater handler$atomicfu$iv = _state$volatile$FU;
        while (true) {
            long state = handler$atomicfu$iv.get(this);
            Companion companion = Companion;
            long $this$withState$iv = state;
            boolean z = false;
            int head$iv = (int) ((HEAD_MASK & $this$withState$iv) >> 0);
            int i = (int) ((TAIL_MASK & $this$withState$iv) >> 30);
            int head = head$iv;
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if (head == oldHead) {
                    z = true;
                }
                if (!z) {
                    throw new AssertionError();
                }
            } else {
                int i2 = oldHead;
            }
            if ((state & FROZEN_MASK) != 0) {
                return next();
            }
            AtomicLongFieldUpdater handler$atomicfu$iv2 = handler$atomicfu$iv;
            if (_state$volatile$FU.compareAndSet(this, state, Companion.updateHead(state, newHead))) {
                getArray().set(this.mask & head, (Object) null);
                return null;
            }
            handler$atomicfu$iv = handler$atomicfu$iv2;
        }
    }

    public final LockFreeTaskQueueCore<E> next() {
        return allocateOrGetNextCopy(markFrozen());
    }

    private final long markFrozen() {
        long j;
        long state;
        AtomicLongFieldUpdater handler$atomicfu$iv = _state$volatile$FU;
        do {
            j = handler$atomicfu$iv.get(this);
            long state2 = j;
            if ((state2 & FROZEN_MASK) != 0) {
                return state2;
            }
            state = state2 | FROZEN_MASK;
        } while (!handler$atomicfu$iv.compareAndSet(this, j, state));
        return state;
    }

    private final LockFreeTaskQueueCore<E> allocateOrGetNextCopy(long state) {
        AtomicReferenceFieldUpdater handler$atomicfu$iv = _next$volatile$FU;
        while (true) {
            LockFreeTaskQueueCore next = (LockFreeTaskQueueCore) handler$atomicfu$iv.get(this);
            if (next != null) {
                return next;
            }
            AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_next$volatile$FU, this, (Object) null, allocateNextCopy(state));
        }
    }

    private final LockFreeTaskQueueCore<E> allocateNextCopy(long state) {
        LockFreeTaskQueueCore next = new LockFreeTaskQueueCore(this.capacity * 2, this.singleConsumer);
        Companion this_$iv = Companion;
        long $this$withState$iv = state;
        int tail = (int) ((TAIL_MASK & $this$withState$iv) >> 30);
        for (int index = (int) ((HEAD_MASK & $this$withState$iv) >> 0); (this.mask & index) != (this.mask & tail); index++) {
            Object value = getArray().get(this.mask & index);
            if (value == null) {
                value = new Placeholder(index);
            }
            next.getArray().set(next.mask & index, value);
        }
        Companion companion = this_$iv;
        long j = $this$withState$iv;
        _state$volatile$FU.set(next, Companion.wo(state, FROZEN_MASK));
        return next;
    }

    public final <R> List<R> map(Function1<? super E, ? extends R> transform) {
        ArrayList res = new ArrayList(this.capacity);
        Companion companion = Companion;
        long $this$withState$iv = _state$volatile$FU.get(this);
        int tail = (int) ((TAIL_MASK & $this$withState$iv) >> 30);
        for (int index = (int) ((HEAD_MASK & $this$withState$iv) >> 0); (this.mask & index) != (this.mask & tail); index++) {
            Object element = getArray().get(this.mask & index);
            if (element != null && !(element instanceof Placeholder)) {
                res.add(transform.invoke(element));
            }
        }
        return res;
    }

    public final boolean isClosed() {
        return (_state$volatile$FU.get(this) & CLOSED_MASK) != 0;
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"Lkotlinx/coroutines/internal/LockFreeTaskQueueCore$Placeholder;", "", "index", "", "<init>", "(I)V", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: LockFreeTaskQueue.kt */
    public static final class Placeholder {
        public final int index;

        public Placeholder(int index2) {
            this.index = index2;
        }
    }

    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0015\u0010\u0017\u001a\u00020\n*\u00020\n2\u0006\u0010\u0018\u001a\u00020\nH\u0004J\u0012\u0010\u0019\u001a\u00020\n*\u00020\n2\u0006\u0010\u001a\u001a\u00020\u0005J\u0012\u0010\u001b\u001a\u00020\n*\u00020\n2\u0006\u0010\u001c\u001a\u00020\u0005JP\u0010\u001d\u001a\u0002H\u001e\"\u0004\b\u0001\u0010\u001e*\u00020\n26\u0010\u001f\u001a2\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b(#\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b!\u0012\b\b\"\u0012\u0004\b\b($\u0012\u0004\u0012\u0002H\u001e0 H\b¢\u0006\u0002\u0010%J\n\u0010&\u001a\u00020\u0005*\u00020\nR\u000e\u0010\u0004\u001a\u00020\u0005XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005XT¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005XT¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nXT¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005XT¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nXT¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005XT¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\nXT¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nXT¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005XT¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u00020\u00138\u0006X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0005XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0005XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0005XT¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lkotlinx/coroutines/internal/LockFreeTaskQueueCore$Companion;", "", "<init>", "()V", "INITIAL_CAPACITY", "", "CAPACITY_BITS", "MAX_CAPACITY_MASK", "HEAD_SHIFT", "HEAD_MASK", "", "TAIL_SHIFT", "TAIL_MASK", "FROZEN_SHIFT", "FROZEN_MASK", "CLOSED_SHIFT", "CLOSED_MASK", "MIN_ADD_SPIN_CAPACITY", "REMOVE_FROZEN", "Lkotlinx/coroutines/internal/Symbol;", "ADD_SUCCESS", "ADD_FROZEN", "ADD_CLOSED", "wo", "other", "updateHead", "newHead", "updateTail", "newTail", "withState", "T", "block", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "head", "tail", "(JLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "addFailReason", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: LockFreeTaskQueue.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final long wo(long $this$wo, long other) {
            return (~other) & $this$wo;
        }

        public final long updateHead(long $this$updateHead, int newHead) {
            return wo($this$updateHead, LockFreeTaskQueueCore.HEAD_MASK) | (((long) newHead) << 0);
        }

        public final long updateTail(long $this$updateTail, int newTail) {
            return wo($this$updateTail, LockFreeTaskQueueCore.TAIL_MASK) | (((long) newTail) << 30);
        }

        public final <T> T withState(long $this$withState, Function2<? super Integer, ? super Integer, ? extends T> block) {
            return block.invoke(Integer.valueOf((int) ((LockFreeTaskQueueCore.HEAD_MASK & $this$withState) >> 0)), Integer.valueOf((int) ((LockFreeTaskQueueCore.TAIL_MASK & $this$withState) >> 30)));
        }

        public final int addFailReason(long $this$addFailReason) {
            return (LockFreeTaskQueueCore.CLOSED_MASK & $this$addFailReason) != 0 ? 2 : 1;
        }
    }

    static {
        Class<LockFreeTaskQueueCore> cls = LockFreeTaskQueueCore.class;
        _next$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_next$volatile");
        _state$volatile$FU = AtomicLongFieldUpdater.newUpdater(cls, "_state$volatile");
    }
}
