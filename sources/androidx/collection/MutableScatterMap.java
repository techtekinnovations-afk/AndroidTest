package androidx.collection;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ULong;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableMap;
import kotlin.sequences.Sequence;
import kotlinx.coroutines.scheduling.WorkQueueKt;

@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\t\n\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003:\u0001EB\u000f\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\b\u001a\u00020\tH\u0002J\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u000bJ\u0006\u0010\f\u001a\u00020\tJS\u0010\r\u001a\u00028\u00012\u0006\u0010\u000e\u001a\u00028\u000028\u0010\u000f\u001a4\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u000e\u0012\u0015\u0012\u0013\u0018\u00018\u0001¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00028\u00010\u0010H\bø\u0001\u0000¢\u0006\u0002\u0010\u0014J\u0010\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0005H\u0002J\u0015\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00028\u0000H\u0001¢\u0006\u0002\u0010\u0018J'\u0010\u0019\u001a\u00028\u00012\u0006\u0010\u000e\u001a\u00028\u00002\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00010\u001bH\bø\u0001\u0000¢\u0006\u0002\u0010\u001cJ\b\u0010\u001d\u001a\u00020\tH\u0002J\u0010\u0010\u001e\u001a\u00020\t2\u0006\u0010\u001f\u001a\u00020\u0005H\u0002J\u0010\u0010 \u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u0016\u0010!\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00028\u0000H\n¢\u0006\u0002\u0010\"J\u0017\u0010!\u001a\u00020\t2\f\u0010#\u001a\b\u0012\u0004\u0012\u00028\u00000$H\nJ\u0017\u0010!\u001a\u00020\t2\f\u0010#\u001a\b\u0012\u0004\u0012\u00028\u00000%H\nJ\u001e\u0010!\u001a\u00020\t2\u000e\u0010#\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000&H\n¢\u0006\u0002\u0010'J\u0017\u0010!\u001a\u00020\t2\f\u0010#\u001a\b\u0012\u0004\u0012\u00028\u00000(H\nJ\u0017\u0010!\u001a\u00020\t2\f\u0010#\u001a\b\u0012\u0004\u0012\u00028\u00000)H\nJ\u001d\u0010*\u001a\u00020\t2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0003H\nJ*\u0010*\u001a\u00020\t2\u001a\u0010,\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010-0&H\n¢\u0006\u0002\u0010.J\u001d\u0010*\u001a\u00020\t2\u0012\u0010/\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010-H\nJ#\u0010*\u001a\u00020\t2\u0018\u0010,\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010-0(H\nJ\u001d\u0010*\u001a\u00020\t2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u000100H\nJ#\u0010*\u001a\u00020\t2\u0018\u0010,\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010-0)H\nJ\u001d\u00101\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u000e\u001a\u00028\u00002\u0006\u0010\u0013\u001a\u00028\u0001¢\u0006\u0002\u00102J\u001a\u00103\u001a\u00020\t2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0003J'\u00103\u001a\u00020\t2\u001a\u0010,\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010-0&¢\u0006\u0002\u0010.J \u00103\u001a\u00020\t2\u0018\u0010,\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010-0(J\u001a\u00103\u001a\u00020\t2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u000100J \u00103\u001a\u00020\t2\u0018\u0010,\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010-0)J\u0015\u00104\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u000e\u001a\u00028\u0000¢\u0006\u0002\u00105J\u001b\u00104\u001a\u0002062\u0006\u0010\u000e\u001a\u00028\u00002\u0006\u0010\u0013\u001a\u00028\u0001¢\u0006\u0002\u00107J\b\u00108\u001a\u00020\tH\u0002J&\u00109\u001a\u00020\t2\u0018\u0010:\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u0002060\u0010H\bø\u0001\u0000J\u0017\u0010;\u001a\u0004\u0018\u00018\u00012\u0006\u0010<\u001a\u00020\u0005H\u0001¢\u0006\u0002\u0010=J\u0010\u0010>\u001a\u00020\t2\u0006\u0010?\u001a\u00020\u0005H\u0002J\u001e\u0010@\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00028\u00002\u0006\u0010\u0013\u001a\u00028\u0001H\u0002¢\u0006\u0002\u0010AJ\u0006\u0010B\u001a\u00020\u0005J\u0019\u0010C\u001a\u00020\t2\u0006\u0010<\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020DH\bR\u000e\u0010\u0007\u001a\u00020\u0005X\u000e¢\u0006\u0002\n\u0000\u0002\u0007\n\u0005\b20\u0001¨\u0006F"}, d2 = {"Landroidx/collection/MutableScatterMap;", "K", "V", "Landroidx/collection/ScatterMap;", "initialCapacity", "", "(I)V", "growthLimit", "adjustStorage", "", "asMutableMap", "", "clear", "compute", "key", "computeBlock", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "value", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "findFirstAvailableSlot", "hash1", "findInsertIndex", "(Ljava/lang/Object;)I", "getOrPut", "defaultValue", "Lkotlin/Function0;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "initializeGrowth", "initializeMetadata", "capacity", "initializeStorage", "minusAssign", "(Ljava/lang/Object;)V", "keys", "Landroidx/collection/ObjectList;", "Landroidx/collection/ScatterSet;", "", "([Ljava/lang/Object;)V", "", "Lkotlin/sequences/Sequence;", "plusAssign", "from", "pairs", "Lkotlin/Pair;", "([Lkotlin/Pair;)V", "pair", "", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "putAll", "remove", "(Ljava/lang/Object;)Ljava/lang/Object;", "", "(Ljava/lang/Object;Ljava/lang/Object;)Z", "removeDeletedMarkers", "removeIf", "predicate", "removeValueAt", "index", "(I)Ljava/lang/Object;", "resizeStorage", "newCapacity", "set", "(Ljava/lang/Object;Ljava/lang/Object;)V", "trim", "writeMetadata", "", "MutableMapWrapper", "collection"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: ScatterMap.kt */
public final class MutableScatterMap<K, V> extends ScatterMap<K, V> {
    private int growthLimit;

    public MutableScatterMap() {
        this(0, 1, (DefaultConstructorMarker) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ MutableScatterMap(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 6 : i);
    }

    public MutableScatterMap(int initialCapacity) {
        super((DefaultConstructorMarker) null);
        if (initialCapacity >= 0) {
            initializeStorage(ScatterMapKt.unloadedCapacity(initialCapacity));
            return;
        }
        throw new IllegalArgumentException("Capacity must be a positive value.".toString());
    }

    private final void initializeStorage(int initialCapacity) {
        int newCapacity;
        if (initialCapacity > 0) {
            newCapacity = Math.max(7, ScatterMapKt.normalizeCapacity(initialCapacity));
        } else {
            newCapacity = 0;
        }
        this._capacity = newCapacity;
        initializeMetadata(newCapacity);
        this.keys = new Object[newCapacity];
        this.values = new Object[newCapacity];
    }

    private final void initializeMetadata(int capacity) {
        long[] jArr;
        if (capacity == 0) {
            jArr = ScatterMapKt.EmptyGroup;
        } else {
            long[] $this$initializeMetadata_u24lambda_u241 = new long[(((((capacity + 1) + 7) + 7) & -8) >> 3)];
            ArraysKt.fill$default($this$initializeMetadata_u24lambda_u241, -9187201950435737472L, 0, 0, 6, (Object) null);
            jArr = $this$initializeMetadata_u24lambda_u241;
        }
        this.metadata = jArr;
        long[] data$iv = this.metadata;
        int i$iv = capacity >> 3;
        int b$iv = (capacity & 7) << 3;
        data$iv[i$iv] = (data$iv[i$iv] & (~(255 << b$iv))) | (255 << b$iv);
        initializeGrowth();
    }

    private final void initializeGrowth() {
        this.growthLimit = ScatterMapKt.loadedCapacity(getCapacity()) - this._size;
    }

    public final V getOrPut(K key, Function0<? extends V> defaultValue) {
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        V v = get(key);
        if (v != null) {
            return v;
        }
        Object it = defaultValue.invoke();
        set(key, it);
        return it;
    }

    public final V compute(K key, Function2<? super K, ? super V, ? extends V> computeBlock) {
        Intrinsics.checkNotNullParameter(computeBlock, "computeBlock");
        int index = findInsertIndex(key);
        boolean inserting = index < 0;
        Object computedValue = computeBlock.invoke(key, inserting ? null : this.values[index]);
        if (inserting) {
            int insertionIndex = ~index;
            this.keys[insertionIndex] = key;
            this.values[insertionIndex] = computedValue;
        } else {
            this.values[index] = computedValue;
        }
        return computedValue;
    }

    public final void set(K key, V value) {
        int index = findInsertIndex(key);
        if (index < 0) {
            index = ~index;
        }
        this.keys[index] = key;
        this.values[index] = value;
    }

    public final V put(K key, V value) {
        int index = findInsertIndex(key);
        if (index < 0) {
            index = ~index;
        }
        Object oldValue = this.values[index];
        this.keys[index] = key;
        this.values[index] = value;
        return oldValue;
    }

    public final void putAll(Pair<? extends K, ? extends V>[] pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        for (Pair<? extends K, ? extends V> pair : pairs) {
            set(pair.component1(), pair.component2());
        }
    }

    public final void putAll(Iterable<? extends Pair<? extends K, ? extends V>> pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        for (Pair pair : pairs) {
            set(pair.component1(), pair.component2());
        }
    }

    public final void putAll(Sequence<? extends Pair<? extends K, ? extends V>> pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        for (Pair pair : pairs) {
            set(pair.component1(), pair.component2());
        }
    }

    public final void putAll(Map<K, ? extends V> from) {
        Intrinsics.checkNotNullParameter(from, TypedValues.TransitionType.S_FROM);
        for (Map.Entry element$iv : from.entrySet()) {
            set(element$iv.getKey(), element$iv.getValue());
        }
    }

    public final void putAll(ScatterMap<K, V> from) {
        Object[] k$iv;
        int $i$f$forEach;
        Object[] k$iv2;
        int $i$f$forEach2;
        int i;
        Intrinsics.checkNotNullParameter(from, TypedValues.TransitionType.S_FROM);
        ScatterMap this_$iv = from;
        int $i$f$forEach3 = 0;
        Object[] k$iv3 = this_$iv.keys;
        Object[] v$iv = this_$iv.values;
        long[] m$iv$iv = this_$iv.metadata;
        int lastIndex$iv$iv = m$iv$iv.length - 2;
        int i$iv$iv = 0;
        if (0 <= lastIndex$iv$iv) {
            while (true) {
                long slot$iv$iv = m$iv$iv[i$iv$iv];
                long $this$maskEmptyOrDeleted$iv$iv$iv = slot$iv$iv;
                ScatterMap this_$iv2 = this_$iv;
                if ((((~$this$maskEmptyOrDeleted$iv$iv$iv) << 7) & $this$maskEmptyOrDeleted$iv$iv$iv & -9187201950435737472L) != -9187201950435737472L) {
                    int i2 = 8;
                    int bitCount$iv$iv = 8 - ((~(i$iv$iv - lastIndex$iv$iv)) >>> 31);
                    int j$iv$iv = 0;
                    while (j$iv$iv < bitCount$iv$iv) {
                        if ((255 & slot$iv$iv) < 128) {
                            int index$iv = (i$iv$iv << 3) + j$iv$iv;
                            i = i2;
                            $i$f$forEach2 = $i$f$forEach3;
                            k$iv2 = k$iv3;
                            set(k$iv3[index$iv], v$iv[index$iv]);
                        } else {
                            i = i2;
                            $i$f$forEach2 = $i$f$forEach3;
                            k$iv2 = k$iv3;
                        }
                        slot$iv$iv >>= i;
                        j$iv$iv++;
                        i2 = i;
                        $i$f$forEach3 = $i$f$forEach2;
                        k$iv3 = k$iv2;
                    }
                    int i3 = i2;
                    $i$f$forEach = $i$f$forEach3;
                    k$iv = k$iv3;
                    if (bitCount$iv$iv != i2) {
                        return;
                    }
                } else {
                    $i$f$forEach = $i$f$forEach3;
                    k$iv = k$iv3;
                }
                if (i$iv$iv != lastIndex$iv$iv) {
                    i$iv$iv++;
                    ScatterMap<K, V> scatterMap = from;
                    this_$iv = this_$iv2;
                    $i$f$forEach3 = $i$f$forEach;
                    k$iv3 = k$iv;
                } else {
                    return;
                }
            }
        } else {
            Object[] objArr = k$iv3;
        }
    }

    public final void plusAssign(Pair<? extends K, ? extends V> pair) {
        Intrinsics.checkNotNullParameter(pair, "pair");
        set(pair.getFirst(), pair.getSecond());
    }

    public final void plusAssign(Pair<? extends K, ? extends V>[] pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        putAll(pairs);
    }

    public final void plusAssign(Iterable<? extends Pair<? extends K, ? extends V>> pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        putAll(pairs);
    }

    public final void plusAssign(Sequence<? extends Pair<? extends K, ? extends V>> pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        putAll(pairs);
    }

    public final void plusAssign(Map<K, ? extends V> from) {
        Intrinsics.checkNotNullParameter(from, TypedValues.TransitionType.S_FROM);
        putAll(from);
    }

    public final void plusAssign(ScatterMap<K, V> from) {
        Intrinsics.checkNotNullParameter(from, TypedValues.TransitionType.S_FROM);
        putAll(from);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0097, code lost:
        r10 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x00a5, code lost:
        if (((((~r10) << 6) & r10) & -9187201950435737472L) == 0) goto L_0x00b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x00a7, code lost:
        r10 = -1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final V remove(K r25) {
        /*
            r24 = this;
            r0 = r24
            r1 = r25
            r2 = r0
            androidx.collection.ScatterMap r2 = (androidx.collection.ScatterMap) r2
            r3 = 0
            r4 = 0
            if (r1 == 0) goto L_0x0010
            int r6 = r1.hashCode()
            goto L_0x0011
        L_0x0010:
            r6 = 0
        L_0x0011:
            r7 = -862048943(0xffffffffcc9e2d51, float:-8.2930312E7)
            int r6 = r6 * r7
            int r7 = r6 << 16
            r4 = r6 ^ r7
            r6 = 0
            r6 = r4 & 127(0x7f, float:1.78E-43)
            int r7 = r2._capacity
            r8 = 0
            int r8 = r4 >>> 7
            r8 = r8 & r7
            r9 = 0
        L_0x0025:
            long[] r10 = r2.metadata
            r11 = 0
            int r12 = r8 >> 3
            r13 = r8 & 7
            int r13 = r13 << 3
            r14 = r10[r12]
            long r14 = r14 >>> r13
            int r16 = r12 + 1
            r16 = r10[r16]
            int r18 = 64 - r13
            long r16 = r16 << r18
            r19 = r6
            long r5 = (long) r13
            long r5 = -r5
            r20 = 63
            long r5 = r5 >> r20
            long r5 = r16 & r5
            long r5 = r5 | r14
            r10 = r5
            r12 = 0
            r13 = r19
            long r14 = (long) r13
            r16 = 72340172838076673(0x101010101010101, double:7.748604185489348E-304)
            long r14 = r14 * r16
            long r14 = r14 ^ r10
            long r16 = r14 - r16
            r19 = r3
            r20 = r4
            long r3 = ~r14
            long r3 = r16 & r3
            r16 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r3 = r3 & r16
        L_0x0064:
            r10 = r3
            r12 = 0
            r14 = 0
            int r21 = (r10 > r14 ? 1 : (r10 == r14 ? 0 : -1))
            if (r21 == 0) goto L_0x006f
            r21 = 1
            goto L_0x0071
        L_0x006f:
            r21 = 0
        L_0x0071:
            if (r21 == 0) goto L_0x0097
            r10 = r3
            r12 = 0
            r14 = r10
            r21 = 0
            int r22 = java.lang.Long.numberOfTrailingZeros(r14)
            int r14 = r22 >> 3
            int r14 = r14 + r8
            r10 = r14 & r7
            java.lang.Object[] r11 = r2.keys
            r11 = r11[r10]
            boolean r11 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r11, (java.lang.Object) r1)
            if (r11 == 0) goto L_0x008d
            goto L_0x00a9
        L_0x008d:
            r11 = r3
            r14 = 0
            r21 = 1
            long r21 = r11 - r21
            long r11 = r11 & r21
            r3 = r11
            goto L_0x0064
        L_0x0097:
            r10 = r5
            r12 = 0
            r21 = r14
            long r14 = ~r10
            r23 = 6
            long r14 = r14 << r23
            long r14 = r14 & r10
            long r10 = r14 & r16
            int r10 = (r10 > r21 ? 1 : (r10 == r21 ? 0 : -1))
            if (r10 == 0) goto L_0x00b3
            r10 = -1
        L_0x00a9:
            if (r10 < 0) goto L_0x00b1
            java.lang.Object r2 = r0.removeValueAt(r10)
            return r2
        L_0x00b1:
            r2 = 0
            return r2
        L_0x00b3:
            int r9 = r9 + 8
            int r10 = r8 + r9
            r8 = r10 & r7
            r6 = r13
            r3 = r19
            r4 = r20
            goto L_0x0025
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.MutableScatterMap.remove(java.lang.Object):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x009b, code lost:
        r10 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x00a9, code lost:
        if (((((~r10) << 6) & r10) & -9187201950435737472L) == 0) goto L_0x00c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x00ab, code lost:
        r10 = -1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean remove(K r26, V r27) {
        /*
            r25 = this;
            r0 = r25
            r1 = r26
            r2 = r0
            androidx.collection.ScatterMap r2 = (androidx.collection.ScatterMap) r2
            r3 = 0
            r4 = 0
            if (r1 == 0) goto L_0x0010
            int r6 = r1.hashCode()
            goto L_0x0011
        L_0x0010:
            r6 = 0
        L_0x0011:
            r7 = -862048943(0xffffffffcc9e2d51, float:-8.2930312E7)
            int r6 = r6 * r7
            int r7 = r6 << 16
            r4 = r6 ^ r7
            r6 = 0
            r6 = r4 & 127(0x7f, float:1.78E-43)
            int r7 = r2._capacity
            r8 = 0
            int r8 = r4 >>> 7
            r8 = r8 & r7
            r9 = 0
        L_0x0025:
            long[] r10 = r2.metadata
            r11 = 0
            int r12 = r8 >> 3
            r13 = r8 & 7
            int r13 = r13 << 3
            r14 = r10[r12]
            long r14 = r14 >>> r13
            int r16 = r12 + 1
            r16 = r10[r16]
            int r18 = 64 - r13
            long r16 = r16 << r18
            r19 = r6
            r18 = 0
            long r5 = (long) r13
            long r5 = -r5
            r20 = 63
            long r5 = r5 >> r20
            long r5 = r16 & r5
            long r5 = r5 | r14
            r10 = r5
            r12 = 0
            r13 = r19
            long r14 = (long) r13
            r16 = 72340172838076673(0x101010101010101, double:7.748604185489348E-304)
            long r14 = r14 * r16
            long r14 = r14 ^ r10
            long r16 = r14 - r16
            r19 = r3
            r20 = r4
            long r3 = ~r14
            long r3 = r16 & r3
            r16 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r3 = r3 & r16
        L_0x0066:
            r10 = r3
            r12 = 0
            r14 = 0
            int r21 = (r10 > r14 ? 1 : (r10 == r14 ? 0 : -1))
            r22 = 1
            if (r21 == 0) goto L_0x0073
            r10 = r22
            goto L_0x0075
        L_0x0073:
            r10 = r18
        L_0x0075:
            if (r10 == 0) goto L_0x009b
            r10 = r3
            r12 = 0
            r14 = r10
            r21 = 0
            int r23 = java.lang.Long.numberOfTrailingZeros(r14)
            int r14 = r23 >> 3
            int r14 = r14 + r8
            r10 = r14 & r7
            java.lang.Object[] r11 = r2.keys
            r11 = r11[r10]
            boolean r11 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r11, (java.lang.Object) r1)
            if (r11 == 0) goto L_0x0091
            goto L_0x00ad
        L_0x0091:
            r11 = r3
            r14 = 0
            r21 = 1
            long r21 = r11 - r21
            long r11 = r11 & r21
            r3 = r11
            goto L_0x0066
        L_0x009b:
            r10 = r5
            r12 = 0
            r23 = r14
            long r14 = ~r10
            r21 = 6
            long r14 = r14 << r21
            long r14 = r14 & r10
            long r10 = r14 & r16
            int r10 = (r10 > r23 ? 1 : (r10 == r23 ? 0 : -1))
            if (r10 == 0) goto L_0x00c3
            r10 = -1
        L_0x00ad:
            if (r10 < 0) goto L_0x00c0
            java.lang.Object[] r2 = r0.values
            r2 = r2[r10]
            r11 = r27
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r2, (java.lang.Object) r11)
            if (r2 == 0) goto L_0x00c2
            r0.removeValueAt(r10)
            return r22
        L_0x00c0:
            r11 = r27
        L_0x00c2:
            return r18
        L_0x00c3:
            r11 = r27
            int r9 = r9 + 8
            int r10 = r8 + r9
            r8 = r10 & r7
            r6 = r13
            r3 = r19
            r4 = r20
            goto L_0x0025
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.MutableScatterMap.remove(java.lang.Object, java.lang.Object):boolean");
    }

    public final void removeIf(Function2<? super K, ? super V, Boolean> predicate) {
        int $i$f$removeIf;
        int $i$f$removeIf2;
        int i;
        Function2<? super K, ? super V, Boolean> function2 = predicate;
        Intrinsics.checkNotNullParameter(function2, "predicate");
        int $i$f$removeIf3 = 0;
        long[] m$iv = this.metadata;
        int lastIndex$iv = m$iv.length - 2;
        int i$iv = 0;
        if (0 <= lastIndex$iv) {
            while (true) {
                long slot$iv = m$iv[i$iv];
                long $this$maskEmptyOrDeleted$iv$iv = slot$iv;
                if ((((~$this$maskEmptyOrDeleted$iv$iv) << 7) & $this$maskEmptyOrDeleted$iv$iv & -9187201950435737472L) != -9187201950435737472L) {
                    int $i$f$removeIf4 = 8;
                    int bitCount$iv = 8 - ((~(i$iv - lastIndex$iv)) >>> 31);
                    int j$iv = 0;
                    while (j$iv < bitCount$iv) {
                        if ((255 & slot$iv) < 128) {
                            int index = (i$iv << 3) + j$iv;
                            i = $i$f$removeIf4;
                            $i$f$removeIf2 = $i$f$removeIf3;
                            if (function2.invoke(this.keys[index], this.values[index]).booleanValue()) {
                                removeValueAt(index);
                            }
                        } else {
                            $i$f$removeIf2 = $i$f$removeIf3;
                            i = $i$f$removeIf4;
                        }
                        slot$iv >>= i;
                        j$iv++;
                        $i$f$removeIf4 = i;
                        $i$f$removeIf3 = $i$f$removeIf2;
                    }
                    $i$f$removeIf = $i$f$removeIf3;
                    if (bitCount$iv != $i$f$removeIf4) {
                        return;
                    }
                } else {
                    $i$f$removeIf = $i$f$removeIf3;
                }
                if (i$iv != lastIndex$iv) {
                    i$iv++;
                    $i$f$removeIf3 = $i$f$removeIf;
                } else {
                    return;
                }
            }
        }
    }

    public final void minusAssign(K key) {
        remove(key);
    }

    public final void minusAssign(K[] keys) {
        Intrinsics.checkNotNullParameter(keys, "keys");
        for (Object key : keys) {
            remove(key);
        }
    }

    public final void minusAssign(Iterable<? extends K> keys) {
        Intrinsics.checkNotNullParameter(keys, "keys");
        for (Object key : keys) {
            remove(key);
        }
    }

    public final void minusAssign(Sequence<? extends K> keys) {
        Intrinsics.checkNotNullParameter(keys, "keys");
        for (Object key : keys) {
            remove(key);
        }
    }

    public final void minusAssign(ScatterSet<K> keys) {
        ScatterSet this_$iv;
        ScatterSet this_$iv2;
        int i;
        Intrinsics.checkNotNullParameter(keys, "keys");
        int $i$f$minusAssign = 0;
        ScatterSet this_$iv3 = keys;
        Object[] k$iv = this_$iv3.elements;
        long[] m$iv$iv = this_$iv3.metadata;
        int lastIndex$iv$iv = m$iv$iv.length - 2;
        int i$iv$iv = 0;
        if (0 <= lastIndex$iv$iv) {
            while (true) {
                long slot$iv$iv = m$iv$iv[i$iv$iv];
                long $this$maskEmptyOrDeleted$iv$iv$iv = slot$iv$iv;
                int $i$f$minusAssign2 = $i$f$minusAssign;
                if ((((~$this$maskEmptyOrDeleted$iv$iv$iv) << 7) & $this$maskEmptyOrDeleted$iv$iv$iv & -9187201950435737472L) != -9187201950435737472L) {
                    int i2 = 8;
                    int bitCount$iv$iv = 8 - ((~(i$iv$iv - lastIndex$iv$iv)) >>> 31);
                    int j$iv$iv = 0;
                    while (j$iv$iv < bitCount$iv$iv) {
                        if ((255 & slot$iv$iv) < 128) {
                            i = i2;
                            this_$iv2 = this_$iv3;
                            remove(k$iv[(i$iv$iv << 3) + j$iv$iv]);
                        } else {
                            i = i2;
                            this_$iv2 = this_$iv3;
                        }
                        slot$iv$iv >>= i;
                        j$iv$iv++;
                        i2 = i;
                        this_$iv3 = this_$iv2;
                    }
                    int i3 = i2;
                    this_$iv = this_$iv3;
                    if (bitCount$iv$iv != i2) {
                        return;
                    }
                } else {
                    this_$iv = this_$iv3;
                }
                if (i$iv$iv != lastIndex$iv$iv) {
                    i$iv$iv++;
                    ScatterSet<K> scatterSet = keys;
                    $i$f$minusAssign = $i$f$minusAssign2;
                    this_$iv3 = this_$iv;
                } else {
                    return;
                }
            }
        } else {
            ScatterSet scatterSet2 = this_$iv3;
        }
    }

    public final void minusAssign(ObjectList<K> keys) {
        Intrinsics.checkNotNullParameter(keys, "keys");
        ObjectList this_$iv = keys;
        Object[] content$iv = this_$iv.content;
        int i = this_$iv._size;
        for (int i$iv = 0; i$iv < i; i$iv++) {
            remove(content$iv[i$iv]);
        }
    }

    public final V removeValueAt(int index) {
        this._size--;
        long[] m$iv = this.metadata;
        int i$iv$iv = index >> 3;
        int b$iv$iv = (index & 7) << 3;
        m$iv[i$iv$iv] = (m$iv[i$iv$iv] & (~(255 << b$iv$iv))) | (254 << b$iv$iv);
        int $i$f$writeRawMetadata = this._capacity;
        int cloneIndex$iv = ((index - 7) & $i$f$writeRawMetadata) + ($i$f$writeRawMetadata & 7);
        int i$iv$iv2 = cloneIndex$iv >> 3;
        int b$iv$iv2 = (cloneIndex$iv & 7) << 3;
        m$iv[i$iv$iv2] = ((~(255 << b$iv$iv2)) & m$iv[i$iv$iv2]) | (254 << b$iv$iv2);
        this.keys[index] = null;
        Object oldValue = this.values[index];
        this.values[index] = null;
        return oldValue;
    }

    public final void clear() {
        this._size = 0;
        if (this.metadata != ScatterMapKt.EmptyGroup) {
            ArraysKt.fill$default(this.metadata, -9187201950435737472L, 0, 0, 6, (Object) null);
            long[] data$iv = this.metadata;
            int offset$iv = this._capacity;
            int i$iv = offset$iv >> 3;
            int b$iv = (offset$iv & 7) << 3;
            data$iv[i$iv] = (data$iv[i$iv] & (~(255 << b$iv))) | (255 << b$iv);
        }
        ArraysKt.fill((T[]) this.values, null, 0, this._capacity);
        ArraysKt.fill((T[]) this.keys, null, 0, this._capacity);
        initializeGrowth();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x008e, code lost:
        r9 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x009b, code lost:
        if (((((~r9) << 6) & r9) & -9187201950435737472L) == 0) goto L_0x0130;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x009d, code lost:
        r4 = r18;
        r2 = r0.findFirstAvailableSlot(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00a8, code lost:
        if (r0.growthLimit != 0) goto L_0x00cc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00bd, code lost:
        if (((r0.metadata[r2 >> 3] >> ((r2 & 7) << 3)) & 255) != 254) goto L_0x00c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00bf, code lost:
        r3 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00c2, code lost:
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00c3, code lost:
        if (r3 != null) goto L_0x00cc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00c5, code lost:
        r0.adjustStorage();
        r2 = r0.findFirstAvailableSlot(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00cc, code lost:
        r0._size++;
        r3 = r0.growthLimit;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00e8, code lost:
        if (((r0.metadata[r2 >> 3] >> ((r2 & 7) << 3)) & 255) != 128) goto L_0x00ed;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00ea, code lost:
        r17 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00ed, code lost:
        r17 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00ef, code lost:
        r0.growthLimit = r3 - r17;
        r11 = (long) r5;
        r3 = r24;
        r14 = r3.metadata;
        r17 = r2 >> 3;
        r18 = (r2 & 7) << 3;
        r14[r17] = (r14[r17] & (~(255 << r18))) | (r11 << r18);
        r9 = r3._capacity;
        r10 = ((r2 - 7) & r9) + (r9 & 7);
        r17 = r10 >> 3;
        r18 = (r10 & 7) << 3;
        r14[r17] = (r14[r17] & (~(255 << r18))) | (r11 << r18);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x012f, code lost:
        return ~r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int findInsertIndex(K r25) {
        /*
            r24 = this;
            r0 = r24
            r1 = r25
            r2 = 0
            if (r1 == 0) goto L_0x000c
            int r4 = r1.hashCode()
            goto L_0x000d
        L_0x000c:
            r4 = 0
        L_0x000d:
            r5 = -862048943(0xffffffffcc9e2d51, float:-8.2930312E7)
            int r4 = r4 * r5
            int r5 = r4 << 16
            r2 = r4 ^ r5
            r4 = 0
            int r4 = r2 >>> 7
            r5 = 0
            r5 = r2 & 127(0x7f, float:1.78E-43)
            int r6 = r0._capacity
            r7 = r4 & r6
            r8 = 0
        L_0x0023:
            long[] r9 = r0.metadata
            r10 = 0
            int r11 = r7 >> 3
            r12 = r7 & 7
            int r12 = r12 << 3
            r13 = r9[r11]
            long r13 = r13 >>> r12
            int r15 = r11 + 1
            r15 = r9[r15]
            int r17 = 64 - r12
            long r15 = r15 << r17
            r18 = r4
            long r3 = (long) r12
            long r3 = -r3
            r19 = 63
            long r3 = r3 >> r19
            long r3 = r3 & r15
            long r3 = r3 | r13
            r9 = r3
            r11 = 0
            long r12 = (long) r5
            r14 = 72340172838076673(0x101010101010101, double:7.748604185489348E-304)
            long r12 = r12 * r14
            long r12 = r12 ^ r9
            long r14 = r12 - r14
            r16 = r2
            r19 = r3
            long r2 = ~r12
            long r2 = r2 & r14
            r14 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r2 = r2 & r14
        L_0x005c:
            r9 = r2
            r4 = 0
            r11 = 0
            int r13 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            r21 = 1
            if (r13 == 0) goto L_0x0069
            r4 = r21
            goto L_0x006a
        L_0x0069:
            r4 = 0
        L_0x006a:
            if (r4 == 0) goto L_0x008e
            r9 = r2
            r4 = 0
            r11 = r9
            r13 = 0
            int r21 = java.lang.Long.numberOfTrailingZeros(r11)
            int r11 = r21 >> 3
            int r11 = r11 + r7
            r4 = r11 & r6
            java.lang.Object[] r9 = r0.keys
            r9 = r9[r4]
            boolean r9 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r9, (java.lang.Object) r1)
            if (r9 == 0) goto L_0x0085
            return r4
        L_0x0085:
            r9 = r2
            r11 = 0
            r12 = 1
            long r12 = r9 - r12
            long r9 = r9 & r12
            r2 = r9
            goto L_0x005c
        L_0x008e:
            r9 = r19
            r4 = 0
            r22 = r11
            long r11 = ~r9
            r13 = 6
            long r11 = r11 << r13
            long r11 = r11 & r9
            long r9 = r11 & r14
            int r4 = (r9 > r22 ? 1 : (r9 == r22 ? 0 : -1))
            if (r4 == 0) goto L_0x0130
            r4 = r18
            int r2 = r0.findFirstAvailableSlot(r4)
            int r3 = r0.growthLimit
            r9 = 255(0xff, double:1.26E-321)
            if (r3 != 0) goto L_0x00cc
            long[] r3 = r0.metadata
            r11 = 0
            r12 = 0
            int r13 = r2 >> 3
            r13 = r3[r13]
            r15 = r2 & 7
            int r15 = r15 << 3
            long r13 = r13 >> r15
            long r12 = r13 & r9
            r14 = 254(0xfe, double:1.255E-321)
            int r12 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r12 != 0) goto L_0x00c2
            r3 = r21
            goto L_0x00c3
        L_0x00c2:
            r3 = 0
        L_0x00c3:
            if (r3 != 0) goto L_0x00cc
            r0.adjustStorage()
            int r2 = r0.findFirstAvailableSlot(r4)
        L_0x00cc:
            int r3 = r0._size
            int r3 = r3 + 1
            r0._size = r3
            int r3 = r0.growthLimit
            long[] r11 = r0.metadata
            r12 = 0
            r13 = 0
            int r14 = r2 >> 3
            r14 = r11[r14]
            r18 = r2 & 7
            int r18 = r18 << 3
            long r14 = r14 >> r18
            long r13 = r14 & r9
            r18 = 128(0x80, double:6.32E-322)
            int r13 = (r13 > r18 ? 1 : (r13 == r18 ? 0 : -1))
            if (r13 != 0) goto L_0x00ed
            r17 = r21
            goto L_0x00ef
        L_0x00ed:
            r17 = 0
        L_0x00ef:
            int r3 = r3 - r17
            r0.growthLimit = r3
            long r11 = (long) r5
            r3 = r24
            r13 = 0
            long[] r14 = r3.metadata
            r15 = 0
            int r17 = r2 >> 3
            r18 = r2 & 7
            int r18 = r18 << 3
            r19 = r14[r17]
            r21 = r9
            long r9 = r21 << r18
            long r9 = ~r9
            long r9 = r19 & r9
            long r19 = r11 << r18
            long r9 = r9 | r19
            r14[r17] = r9
            int r9 = r3._capacity
            int r10 = r2 + -7
            r10 = r10 & r9
            r15 = r9 & 7
            int r10 = r10 + r15
            r15 = 0
            int r17 = r10 >> 3
            r18 = r10 & 7
            int r18 = r18 << 3
            r19 = r14[r17]
            long r0 = r21 << r18
            long r0 = ~r0
            long r0 = r19 & r0
            long r19 = r11 << r18
            long r0 = r0 | r19
            r14[r17] = r0
            int r0 = ~r2
            return r0
        L_0x0130:
            r4 = r18
            int r8 = r8 + 8
            int r0 = r7 + r8
            r7 = r0 & r6
            r0 = r24
            r1 = r25
            r2 = r16
            goto L_0x0023
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.MutableScatterMap.findInsertIndex(java.lang.Object):int");
    }

    private final int findFirstAvailableSlot(int hash1) {
        int probeMask = this._capacity;
        int probeOffset = hash1 & probeMask;
        int probeIndex = 0;
        while (true) {
            long[] metadata$iv = this.metadata;
            int i$iv = probeOffset >> 3;
            int b$iv = (probeOffset & 7) << 3;
            long $this$maskEmptyOrDeleted$iv = (metadata$iv[i$iv] >>> b$iv) | ((metadata$iv[i$iv + 1] << (64 - b$iv)) & ((-((long) b$iv)) >> 63));
            long m = ((~$this$maskEmptyOrDeleted$iv) << 7) & $this$maskEmptyOrDeleted$iv & -9187201950435737472L;
            if (m != 0) {
                return ((Long.numberOfTrailingZeros(m) >> 3) + probeOffset) & probeMask;
            }
            probeIndex += 8;
            probeOffset = (probeOffset + probeIndex) & probeMask;
        }
    }

    public final int trim() {
        int previousCapacity = this._capacity;
        int newCapacity = ScatterMapKt.normalizeCapacity(ScatterMapKt.unloadedCapacity(this._size));
        if (newCapacity >= previousCapacity) {
            return 0;
        }
        resizeStorage(newCapacity);
        return previousCapacity - this._capacity;
    }

    private final void adjustStorage() {
        if (this._capacity <= 8 || Long.compare(ULong.m180constructorimpl(ULong.m180constructorimpl((long) this._size) * 32) ^ Long.MIN_VALUE, ULong.m180constructorimpl(ULong.m180constructorimpl((long) this._capacity) * 25) ^ Long.MIN_VALUE) > 0) {
            resizeStorage(ScatterMapKt.nextCapacity(this._capacity));
        } else {
            resizeStorage(this._capacity);
        }
    }

    private final void resizeStorage(int newCapacity) {
        long[] previousMetadata;
        MutableScatterMap mutableScatterMap = this;
        long[] previousMetadata2 = mutableScatterMap.metadata;
        Object[] previousKeys = mutableScatterMap.keys;
        Object[] previousValues = mutableScatterMap.values;
        int previousCapacity = mutableScatterMap._capacity;
        initializeStorage(newCapacity);
        Object[] newKeys = mutableScatterMap.keys;
        Object[] newValues = mutableScatterMap.values;
        int i = 0;
        while (i < previousCapacity) {
            int i2 = 0;
            if (((previousMetadata2[i >> 3] >> ((i & 7) << 3)) & 255) < 128) {
                Object previousKey = previousKeys[i];
                if (previousKey != null) {
                    i2 = previousKey.hashCode();
                }
                int hash$iv = i2 * ScatterMapKt.MurmurHashC1;
                int hash = hash$iv ^ (hash$iv << 16);
                int index = mutableScatterMap.findFirstAvailableSlot(hash >>> 7);
                long value$iv = (long) (hash & WorkQueueKt.MASK);
                long[] m$iv = this.metadata;
                int i$iv$iv = index >> 3;
                int b$iv$iv = (index & 7) << 3;
                previousMetadata = previousMetadata2;
                m$iv[i$iv$iv] = (m$iv[i$iv$iv] & (~(255 << b$iv$iv))) | (value$iv << b$iv$iv);
                int c$iv = this._capacity;
                int cloneIndex$iv = ((index - 7) & c$iv) + (c$iv & 7);
                int i$iv$iv2 = cloneIndex$iv >> 3;
                int b$iv$iv2 = (cloneIndex$iv & 7) << 3;
                int i3 = c$iv;
                int i4 = cloneIndex$iv;
                m$iv[i$iv$iv2] = (m$iv[i$iv$iv2] & (~(255 << b$iv$iv2))) | (value$iv << b$iv$iv2);
                newKeys[index] = previousKey;
                newValues[index] = previousValues[i];
            } else {
                previousMetadata = previousMetadata2;
            }
            i++;
            mutableScatterMap = this;
            previousMetadata2 = previousMetadata;
        }
    }

    private final void removeDeletedMarkers() {
        int capacity;
        long[] m;
        long[] m2 = this.metadata;
        int capacity2 = this._capacity;
        int removedDeletes = 0;
        int i = 0;
        while (i < capacity2) {
            if (((m2[i >> 3] >> ((i & 7) << 3)) & 255) == 254) {
                long[] m$iv = this.metadata;
                int i$iv$iv = i >> 3;
                int b$iv$iv = (i & 7) << 3;
                m$iv[i$iv$iv] = (m$iv[i$iv$iv] & (~(255 << b$iv$iv))) | (128 << b$iv$iv);
                int c$iv = this._capacity;
                int cloneIndex$iv = ((i - 7) & c$iv) + (c$iv & 7);
                int i$iv$iv2 = cloneIndex$iv >> 3;
                int b$iv$iv2 = (cloneIndex$iv & 7) << 3;
                m = m2;
                capacity = capacity2;
                m$iv[i$iv$iv2] = (m$iv[i$iv$iv2] & (~(255 << b$iv$iv2))) | (128 << b$iv$iv2);
                removedDeletes++;
            } else {
                m = m2;
                capacity = capacity2;
            }
            i++;
            m2 = m;
            capacity2 = capacity;
        }
        this.growthLimit += removedDeletes;
    }

    private final void writeMetadata(int index, long value) {
        long[] m = this.metadata;
        int i$iv = index >> 3;
        int b$iv = (index & 7) << 3;
        m[i$iv] = (m[i$iv] & (~(255 << b$iv))) | (value << b$iv);
        int c = this._capacity;
        int cloneIndex = ((index - 7) & c) + (c & 7);
        int i$iv2 = cloneIndex >> 3;
        int b$iv2 = (cloneIndex & 7) << 3;
        m[i$iv2] = ((~(255 << b$iv2)) & m[i$iv2]) | (value << b$iv2);
    }

    public final Map<K, V> asMutableMap() {
        return new MutableMapWrapper();
    }

    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010'\n\u0002\b\u0005\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\b\u0003\b\u0004\u0018\u00002\u00120\u0001R\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00022\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0003B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u001f\u0010\u0012\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0013\u001a\u00028\u00002\u0006\u0010\u0014\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u0015J\u001e\u0010\u0016\u001a\u00020\u00112\u0014\u0010\u0017\u001a\u0010\u0012\u0006\b\u0001\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0018H\u0016J\u0017\u0010\u0019\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0013\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001aR&\u0010\u0005\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00070\u00068VX\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u00068VX\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\tR\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00010\r8VX\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001b"}, d2 = {"Landroidx/collection/MutableScatterMap$MutableMapWrapper;", "Landroidx/collection/ScatterMap$MapWrapper;", "Landroidx/collection/ScatterMap;", "", "(Landroidx/collection/MutableScatterMap;)V", "entries", "", "", "getEntries", "()Ljava/util/Set;", "keys", "getKeys", "values", "", "getValues", "()Ljava/util/Collection;", "clear", "", "put", "key", "value", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "putAll", "from", "", "remove", "(Ljava/lang/Object;)Ljava/lang/Object;", "collection"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* compiled from: ScatterMap.kt */
    private final class MutableMapWrapper extends ScatterMap<K, V>.MapWrapper implements Map<K, V>, KMutableMap {
        public MutableMapWrapper() {
            super();
        }

        public Set<Map.Entry<K, V>> getEntries() {
            return new MutableScatterMap$MutableMapWrapper$entries$1(MutableScatterMap.this);
        }

        public Set<K> getKeys() {
            return new MutableScatterMap$MutableMapWrapper$keys$1(MutableScatterMap.this);
        }

        public Collection<V> getValues() {
            return new MutableScatterMap$MutableMapWrapper$values$1(MutableScatterMap.this);
        }

        public void clear() {
            MutableScatterMap.this.clear();
        }

        public V remove(Object key) {
            return MutableScatterMap.this.remove(key);
        }

        public void putAll(Map<? extends K, ? extends V> from) {
            Intrinsics.checkNotNullParameter(from, TypedValues.TransitionType.S_FROM);
            for (Map.Entry element$iv : from.entrySet()) {
                put(element$iv.getKey(), element$iv.getValue());
            }
        }

        public V put(K key, V value) {
            return MutableScatterMap.this.put(key, value);
        }
    }
}
