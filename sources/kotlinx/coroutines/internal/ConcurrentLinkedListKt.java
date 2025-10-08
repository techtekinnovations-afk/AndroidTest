package kotlinx.coroutines.internal;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import com.google.common.util.concurrent.Striped$SmallLazyStriped$$ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

@Metadata(d1 = {"\u0000N\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001ag\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003*\u0002H\u00022\u0006\u0010\u0004\u001a\u00020\u000526\u0010\u0006\u001a2\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0004\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u0002H\u00020\u0007H\u0000¢\u0006\u0002\u0010\u000b\u001a+\u0010\f\u001a\u00020\r\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u000e2\u0006\u0010\u000f\u001a\u0002H\u0002H\b\u001as\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u0002H\u000228\b\b\u0010\u0006\u001a2\u0012\u0013\u0012\u00110\u0005¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u0004\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0004\u0012\u0002H\u00020\u0007H\b\u001a!\u0010\u0012\u001a\u0002H\u0013\"\u000e\b\u0000\u0010\u0013*\b\u0012\u0004\u0012\u0002H\u00130\u0014*\u0002H\u0013H\u0000¢\u0006\u0002\u0010\u0015\u001a8\u0010\u0016\u001a\u00020\r*\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192!\u0010\u001a\u001a\u001d\u0012\u0013\u0012\u00110\u0019¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u001c\u0012\u0004\u0012\u00020\r0\u001bH\b\"\u000e\u0010\u001d\u001a\u00020\u0019XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u001e\u001a\u00020\u001fX\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"findSegmentInternal", "Lkotlinx/coroutines/internal/SegmentOrClosed;", "S", "Lkotlinx/coroutines/internal/Segment;", "id", "", "createNewSegment", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "prev", "(Lkotlinx/coroutines/internal/Segment;JLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "moveForward", "", "Lkotlinx/atomicfu/AtomicRef;", "to", "findSegmentAndMoveForward", "startFrom", "close", "N", "Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;", "(Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;)Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;", "addConditionally", "Lkotlinx/atomicfu/AtomicInt;", "delta", "", "condition", "Lkotlin/Function1;", "cur", "POINTERS_SHIFT", "CLOSED", "Lkotlinx/coroutines/internal/Symbol;", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: ConcurrentLinkedList.kt */
public final class ConcurrentLinkedListKt {
    /* access modifiers changed from: private */
    public static final Symbol CLOSED = new Symbol("CLOSED");
    private static final int POINTERS_SHIFT = 16;

    public static final <S extends Segment<S>> Object findSegmentInternal(S $this$findSegmentInternal, long id, Function2<? super Long, ? super S, ? extends S> createNewSegment) {
        Segment cur = $this$findSegmentInternal;
        while (true) {
            if (cur.id >= id && !cur.isRemoved()) {
                return SegmentOrClosed.m1597constructorimpl(cur);
            }
            Object it$iv = cur.getNextOrClosed();
            if (it$iv == CLOSED) {
                return SegmentOrClosed.m1597constructorimpl(CLOSED);
            }
            Segment next = (Segment) ((ConcurrentLinkedListNode) it$iv);
            if (next != null) {
                cur = next;
            } else {
                Segment newTail = (Segment) createNewSegment.invoke(Long.valueOf(cur.id + 1), cur);
                if (cur.trySetNext(newTail)) {
                    if (cur.isRemoved()) {
                        cur.remove();
                    }
                    cur = newTail;
                }
            }
        }
    }

    public static final /* synthetic */ <S extends Segment<S>> boolean moveForward$atomicfu(Object dispatchReceiver$atomicfu, AtomicReferenceFieldUpdater handler$atomicfu, S to) {
        while (true) {
            Segment cur = (Segment) handler$atomicfu.get(dispatchReceiver$atomicfu);
            if (cur.id >= to.id) {
                return true;
            }
            if (!to.tryIncPointers$kotlinx_coroutines_core()) {
                return false;
            }
            if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(handler$atomicfu, dispatchReceiver$atomicfu, cur, to)) {
                if (cur.decPointers$kotlinx_coroutines_core()) {
                    cur.remove();
                }
                return true;
            } else if (to.decPointers$kotlinx_coroutines_core()) {
                to.remove();
            }
        }
    }

    public static final /* synthetic */ <S extends Segment<S>> boolean moveForward$atomicfu$array(AtomicReferenceArray handler$atomicfu, int index$atomicfu, S to) {
        while (true) {
            Segment cur = (Segment) handler$atomicfu.get(index$atomicfu);
            if (cur.id >= to.id) {
                return true;
            }
            if (!to.tryIncPointers$kotlinx_coroutines_core()) {
                return false;
            }
            if (Striped$SmallLazyStriped$$ExternalSyntheticBackportWithForwarding0.m(handler$atomicfu, index$atomicfu, cur, to)) {
                if (cur.decPointers$kotlinx_coroutines_core()) {
                    cur.remove();
                }
                return true;
            } else if (to.decPointers$kotlinx_coroutines_core()) {
                to.remove();
            }
        }
    }

    public static final /* synthetic */ <S extends Segment<S>> Object findSegmentAndMoveForward$atomicfu(Object dispatchReceiver$atomicfu, AtomicReferenceFieldUpdater handler$atomicfu, long id, S startFrom, Function2<? super Long, ? super S, ? extends S> createNewSegment) {
        Object s;
        boolean z;
        do {
            s = findSegmentInternal(startFrom, id, createNewSegment);
            if (SegmentOrClosed.m1602isClosedimpl(s)) {
                break;
            }
            Segment to$iv = SegmentOrClosed.m1600getSegmentimpl(s);
            while (true) {
                Segment cur$iv = (Segment) handler$atomicfu.get(dispatchReceiver$atomicfu);
                z = true;
                if (cur$iv.id >= to$iv.id) {
                    break;
                } else if (!to$iv.tryIncPointers$kotlinx_coroutines_core()) {
                    z = false;
                    continue;
                    break;
                } else if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(handler$atomicfu, dispatchReceiver$atomicfu, cur$iv, to$iv)) {
                    if (cur$iv.decPointers$kotlinx_coroutines_core()) {
                        cur$iv.remove();
                        continue;
                    } else {
                        continue;
                    }
                } else if (to$iv.decPointers$kotlinx_coroutines_core()) {
                    to$iv.remove();
                }
            }
        } while (!z);
        return s;
    }

    public static final /* synthetic */ <S extends Segment<S>> Object findSegmentAndMoveForward$atomicfu$array(AtomicReferenceArray handler$atomicfu, int index$atomicfu, long id, S startFrom, Function2<? super Long, ? super S, ? extends S> createNewSegment) {
        Object s;
        boolean z;
        do {
            s = findSegmentInternal(startFrom, id, createNewSegment);
            if (SegmentOrClosed.m1602isClosedimpl(s)) {
                break;
            }
            Segment to$iv = SegmentOrClosed.m1600getSegmentimpl(s);
            while (true) {
                Segment cur$iv = (Segment) handler$atomicfu.get(index$atomicfu);
                z = true;
                if (cur$iv.id >= to$iv.id) {
                    break;
                } else if (!to$iv.tryIncPointers$kotlinx_coroutines_core()) {
                    z = false;
                    continue;
                    break;
                } else if (Striped$SmallLazyStriped$$ExternalSyntheticBackportWithForwarding0.m(handler$atomicfu, index$atomicfu, cur$iv, to$iv)) {
                    if (cur$iv.decPointers$kotlinx_coroutines_core()) {
                        cur$iv.remove();
                        continue;
                    } else {
                        continue;
                    }
                } else if (to$iv.decPointers$kotlinx_coroutines_core()) {
                    to$iv.remove();
                }
            }
        } while (!z);
        return s;
    }

    public static final <N extends ConcurrentLinkedListNode<N>> N close(N $this$close) {
        ConcurrentLinkedListNode this_$iv = $this$close;
        while (true) {
            Object it$iv = this_$iv.getNextOrClosed();
            if (it$iv == CLOSED) {
                return this_$iv;
            }
            ConcurrentLinkedListNode next = (ConcurrentLinkedListNode) it$iv;
            if (next != null) {
                this_$iv = next;
            } else if (this_$iv.markAsClosed()) {
                return this_$iv;
            }
        }
    }

    private static final /* synthetic */ boolean addConditionally$atomicfu(Object dispatchReceiver$atomicfu, AtomicIntegerFieldUpdater handler$atomicfu, int delta, Function1<? super Integer, Boolean> condition) {
        int cur;
        do {
            cur = handler$atomicfu.get(dispatchReceiver$atomicfu);
            if (!condition.invoke(Integer.valueOf(cur)).booleanValue()) {
                return false;
            }
        } while (!handler$atomicfu.compareAndSet(dispatchReceiver$atomicfu, cur, cur + delta));
        return true;
    }

    private static final /* synthetic */ boolean addConditionally$atomicfu$array(AtomicIntegerArray handler$atomicfu, int index$atomicfu, int delta, Function1<? super Integer, Boolean> condition) {
        int cur;
        do {
            cur = handler$atomicfu.get(index$atomicfu);
            if (!condition.invoke(Integer.valueOf(cur)).booleanValue()) {
                return false;
            }
        } while (!handler$atomicfu.compareAndSet(index$atomicfu, cur, cur + delta));
        return true;
    }
}
