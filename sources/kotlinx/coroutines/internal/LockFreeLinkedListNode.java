package kotlinx.coroutines.internal;

import androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;

@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0010\u000e\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\t\u001a\u00020\bH\u0002J\u001e\u0010\u0016\u001a\u00060\u0000j\u0002`\u00112\n\u0010\u0017\u001a\u00060\u0000j\u0002`\u0011H\u0010¢\u0006\u0002\u0010\u0018J\u0017\u0010\u0019\u001a\u00020\u000b2\n\u0010\u001a\u001a\u00060\u0000j\u0002`\u0011¢\u0006\u0002\u0010\u001bJ\u001f\u0010\u001c\u001a\u00020\u000b2\n\u0010\u001a\u001a\u00060\u0000j\u0002`\u00112\u0006\u0010\u001d\u001a\u00020\u001e¢\u0006\u0002\u0010\u001fJ\u000e\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u001eJ%\u0010#\u001a\u00020\u000b2\n\u0010\u001a\u001a\u00060\u0000j\u0002`\u00112\n\u0010\r\u001a\u00060\u0000j\u0002`\u0011H\u0001¢\u0006\u0002\u0010$J\b\u0010%\u001a\u00020\u000bH\u0016J\u0015\u0010&\u001a\n\u0018\u00010\u0000j\u0004\u0018\u0001`\u0011H\u0001¢\u0006\u0002\u0010\u0013J\u0019\u0010'\u001a\u00020!2\n\u0010\r\u001a\u00060\u0000j\u0002`\u0011H\u0002¢\u0006\u0002\u0010(J\u0016\u0010)\u001a\n\u0018\u00010\u0000j\u0004\u0018\u0001`\u0011H\u0010¢\u0006\u0002\u0010\u0013J'\u0010*\u001a\u00020!2\n\u0010+\u001a\u00060\u0000j\u0002`\u00112\n\u0010\r\u001a\u00060\u0000j\u0002`\u0011H\u0000¢\u0006\u0004\b,\u0010-J\b\u0010.\u001a\u00020/H\u0016R\u000f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005X\u0004R\u000f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00000\u0005X\u0004R\u0011\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0005X\u0004R\u0014\u0010\n\u001a\u00020\u000b8VX\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\fR\u0011\u0010\r\u001a\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0015\u0010\u0010\u001a\u00060\u0000j\u0002`\u00118F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0015\u0010\u0014\u001a\u00060\u0000j\u0002`\u00118F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0013¨\u00060"}, d2 = {"Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "", "<init>", "()V", "_next", "Lkotlinx/atomicfu/AtomicRef;", "_prev", "_removedRef", "Lkotlinx/coroutines/internal/Removed;", "removed", "isRemoved", "", "()Z", "next", "getNext", "()Ljava/lang/Object;", "nextNode", "Lkotlinx/coroutines/internal/Node;", "getNextNode", "()Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "prevNode", "getPrevNode", "findPrevNonRemoved", "current", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "addOneIfEmpty", "node", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Z", "addLast", "permissionsBitmask", "", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;I)Z", "close", "", "forbiddenElementsBit", "addNext", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Z", "remove", "removeOrNext", "finishAdd", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "correctPrev", "validateNode", "prev", "validateNode$kotlinx_coroutines_core", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "toString", "", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: LockFreeLinkedList.kt */
public class LockFreeLinkedListNode {
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicReferenceFieldUpdater _next$volatile$FU;
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicReferenceFieldUpdater _prev$volatile$FU;
    /* access modifiers changed from: private */
    public static final /* synthetic */ AtomicReferenceFieldUpdater _removedRef$volatile$FU;
    private volatile /* synthetic */ Object _next$volatile = this;
    private volatile /* synthetic */ Object _prev$volatile = this;
    private volatile /* synthetic */ Object _removedRef$volatile;

    static {
        Class<LockFreeLinkedListNode> cls = LockFreeLinkedListNode.class;
        _next$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_next$volatile");
        _prev$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_prev$volatile");
        _removedRef$volatile$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_removedRef$volatile");
    }

    private final /* synthetic */ Object get_next$volatile() {
        return this._next$volatile;
    }

    private final /* synthetic */ Object get_prev$volatile() {
        return this._prev$volatile;
    }

    private final /* synthetic */ Object get_removedRef$volatile() {
        return this._removedRef$volatile;
    }

    private final /* synthetic */ void loop$atomicfu(Object obj, AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, Function1<Object, Unit> function1) {
        while (true) {
            function1.invoke(atomicReferenceFieldUpdater.get(obj));
        }
    }

    private final /* synthetic */ void set_next$volatile(Object obj) {
        this._next$volatile = obj;
    }

    private final /* synthetic */ void set_prev$volatile(Object obj) {
        this._prev$volatile = obj;
    }

    private final /* synthetic */ void set_removedRef$volatile(Object obj) {
        this._removedRef$volatile = obj;
    }

    private final Removed removed() {
        Removed removed = (Removed) _removedRef$volatile$FU.get(this);
        if (removed != null) {
            return removed;
        }
        Removed it = new Removed(this);
        _removedRef$volatile$FU.set(this, it);
        return it;
    }

    public boolean isRemoved() {
        return getNext() instanceof Removed;
    }

    public final Object getNext() {
        return _next$volatile$FU.get(this);
    }

    public final LockFreeLinkedListNode getNextNode() {
        LockFreeLinkedListNode lockFreeLinkedListNode;
        Object it = getNext();
        Removed removed = it instanceof Removed ? (Removed) it : null;
        if (removed != null && (lockFreeLinkedListNode = removed.ref) != null) {
            return lockFreeLinkedListNode;
        }
        Intrinsics.checkNotNull(it, "null cannot be cast to non-null type kotlinx.coroutines.internal.LockFreeLinkedListNode");
        return (LockFreeLinkedListNode) it;
    }

    public final LockFreeLinkedListNode getPrevNode() {
        LockFreeLinkedListNode correctPrev = correctPrev();
        return correctPrev == null ? findPrevNonRemoved((LockFreeLinkedListNode) _prev$volatile$FU.get(this)) : correctPrev;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: kotlinx.coroutines.internal.LockFreeLinkedListNode} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final kotlinx.coroutines.internal.LockFreeLinkedListNode findPrevNonRemoved(kotlinx.coroutines.internal.LockFreeLinkedListNode r2) {
        /*
            r1 = this;
        L_0x0001:
            boolean r0 = r2.isRemoved()
            if (r0 != 0) goto L_0x0008
            return r2
        L_0x0008:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = _prev$volatile$FU
            java.lang.Object r0 = r0.get(r2)
            r2 = r0
            kotlinx.coroutines.internal.LockFreeLinkedListNode r2 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r2
            goto L_0x0001
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.LockFreeLinkedListNode.findPrevNonRemoved(kotlinx.coroutines.internal.LockFreeLinkedListNode):kotlinx.coroutines.internal.LockFreeLinkedListNode");
    }

    public final boolean addOneIfEmpty(LockFreeLinkedListNode node) {
        _prev$volatile$FU.set(node, this);
        _next$volatile$FU.set(node, this);
        while (getNext() == this) {
            if (AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_next$volatile$FU, this, this, node)) {
                node.finishAdd(this);
                return true;
            }
        }
        return false;
    }

    public final boolean addLast(LockFreeLinkedListNode node, int permissionsBitmask) {
        LockFreeLinkedListNode currentPrev;
        do {
            currentPrev = getPrevNode();
            if (currentPrev instanceof ListClosed) {
                if ((((ListClosed) currentPrev).forbiddenElementsBitmask & permissionsBitmask) != 0 || !currentPrev.addLast(node, permissionsBitmask)) {
                    return false;
                }
                return true;
            }
        } while (!currentPrev.addNext(node, this));
        return true;
    }

    public final void close(int forbiddenElementsBit) {
        addLast(new ListClosed(forbiddenElementsBit), forbiddenElementsBit);
    }

    public final boolean addNext(LockFreeLinkedListNode node, LockFreeLinkedListNode next) {
        _prev$volatile$FU.set(node, this);
        _next$volatile$FU.set(node, next);
        if (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_next$volatile$FU, this, next, node)) {
            return false;
        }
        node.finishAdd(next);
        return true;
    }

    public boolean remove() {
        return removeOrNext() == null;
    }

    public final LockFreeLinkedListNode removeOrNext() {
        Object next;
        do {
            next = getNext();
            if (next instanceof Removed) {
                return ((Removed) next).ref;
            }
            if (next == this) {
                return (LockFreeLinkedListNode) next;
            }
            Intrinsics.checkNotNull(next, "null cannot be cast to non-null type kotlinx.coroutines.internal.LockFreeLinkedListNode");
        } while (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_next$volatile$FU, this, next, ((LockFreeLinkedListNode) next).removed()));
        ((LockFreeLinkedListNode) next).correctPrev();
        return null;
    }

    private final void finishAdd(LockFreeLinkedListNode next) {
        LockFreeLinkedListNode nextPrev;
        AtomicReferenceFieldUpdater handler$atomicfu$iv = _prev$volatile$FU;
        do {
            nextPrev = (LockFreeLinkedListNode) handler$atomicfu$iv.get(next);
            if (getNext() != next) {
                return;
            }
        } while (!AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(_prev$volatile$FU, next, nextPrev, this));
        if (isRemoved()) {
            next.correctPrev();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: kotlinx.coroutines.internal.LockFreeLinkedListNode} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: kotlinx.coroutines.internal.LockFreeLinkedListNode} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: kotlinx.coroutines.internal.Removed} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final kotlinx.coroutines.internal.LockFreeLinkedListNode correctPrev() {
        /*
            r6 = this;
        L_0x0001:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r0 = _prev$volatile$FU
            java.lang.Object r0 = r0.get(r6)
            kotlinx.coroutines.internal.LockFreeLinkedListNode r0 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r0
            r1 = r0
            r2 = 0
        L_0x000d:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r3 = _next$volatile$FU
            java.lang.Object r3 = r3.get(r1)
            if (r3 != r6) goto L_0x0027
            if (r0 != r1) goto L_0x001b
            return r1
        L_0x001b:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r4 = _prev$volatile$FU
            boolean r4 = androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(r4, r6, r0, r1)
            if (r4 != 0) goto L_0x0026
            goto L_0x0001
        L_0x0026:
            return r1
        L_0x0027:
            boolean r4 = r6.isRemoved()
            if (r4 == 0) goto L_0x002f
            r4 = 0
            return r4
        L_0x002f:
            boolean r4 = r3 instanceof kotlinx.coroutines.internal.Removed
            if (r4 == 0) goto L_0x0054
            if (r2 == 0) goto L_0x0048
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r4 = _next$volatile$FU
            r5 = r3
            kotlinx.coroutines.internal.Removed r5 = (kotlinx.coroutines.internal.Removed) r5
            kotlinx.coroutines.internal.LockFreeLinkedListNode r5 = r5.ref
            boolean r4 = androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(r4, r2, r1, r5)
            if (r4 != 0) goto L_0x0045
            goto L_0x0001
        L_0x0045:
            r1 = r2
            r2 = 0
            goto L_0x000d
        L_0x0048:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r4 = _prev$volatile$FU
            java.lang.Object r4 = r4.get(r1)
            r1 = r4
            kotlinx.coroutines.internal.LockFreeLinkedListNode r1 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r1
            goto L_0x000d
        L_0x0054:
            r2 = r1
            java.lang.String r4 = "null cannot be cast to non-null type kotlinx.coroutines.internal.LockFreeLinkedListNode"
            kotlin.jvm.internal.Intrinsics.checkNotNull(r3, r4)
            r1 = r3
            kotlinx.coroutines.internal.LockFreeLinkedListNode r1 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r1
            goto L_0x000d
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.LockFreeLinkedListNode.correctPrev():kotlinx.coroutines.internal.LockFreeLinkedListNode");
    }

    public final void validateNode$kotlinx_coroutines_core(LockFreeLinkedListNode prev, LockFreeLinkedListNode next) {
        boolean z = true;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if ((prev == _prev$volatile$FU.get(this) ? 1 : 0) == 0) {
                throw new AssertionError();
            }
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (next != _next$volatile$FU.get(this)) {
                z = false;
            }
            if (!z) {
                throw new AssertionError();
            }
        }
    }

    public String toString() {
        return new LockFreeLinkedListNode$toString$1(this) + '@' + DebugStringsKt.getHexAddress(this);
    }
}
