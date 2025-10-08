package androidx.collection;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.ULong;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.scheduling.WorkQueueKt;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0015\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\t\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u000f\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u0007\u001a\u00020\bH\u0002J\u0006\u0010\t\u001a\u00020\bJ\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0002J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u0004H\u0002J'\u0010\u000e\u001a\u00028\u00002\u0006\u0010\u000b\u001a\u00020\u00042\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0010H\bø\u0001\u0000¢\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\bH\u0002J\u0010\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u0004H\u0002J\u0010\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0004H\u0002J\u0011\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u0018H\nJ\u0011\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u0019H\nJ\u0011\u0010\u0016\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u0004H\nJ\u0011\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u001aH\nJ\u0017\u0010\u001b\u001a\u00020\b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002H\nJ\u001d\u0010\u001d\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00028\u0000¢\u0006\u0002\u0010\u001fJ\u0014\u0010 \u001a\u00020\b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002J\u0015\u0010!\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u000b\u001a\u00020\u0004¢\u0006\u0002\u0010\"J\u001b\u0010!\u001a\u00020#2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00028\u0000¢\u0006\u0002\u0010$J\b\u0010%\u001a\u00020\bH\u0002J&\u0010&\u001a\u00020\b2\u0018\u0010'\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020#0(H\bø\u0001\u0000J\u0017\u0010)\u001a\u0004\u0018\u00018\u00002\u0006\u0010*\u001a\u00020\u0004H\u0001¢\u0006\u0002\u0010\"J\u0010\u0010+\u001a\u00020\b2\u0006\u0010,\u001a\u00020\u0004H\u0002J\u001e\u0010-\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010.J\u0006\u0010/\u001a\u00020\u0004J\u0019\u00100\u001a\u00020\b2\u0006\u0010*\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u000201H\bR\u000e\u0010\u0006\u001a\u00020\u0004X\u000e¢\u0006\u0002\n\u0000\u0002\u0007\n\u0005\b20\u0001¨\u00062"}, d2 = {"Landroidx/collection/MutableIntObjectMap;", "V", "Landroidx/collection/IntObjectMap;", "initialCapacity", "", "(I)V", "growthLimit", "adjustStorage", "", "clear", "findAbsoluteInsertIndex", "key", "findFirstAvailableSlot", "hash1", "getOrPut", "defaultValue", "Lkotlin/Function0;", "(ILkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "initializeGrowth", "initializeMetadata", "capacity", "initializeStorage", "minusAssign", "keys", "Landroidx/collection/IntList;", "Landroidx/collection/IntSet;", "", "plusAssign", "from", "put", "value", "(ILjava/lang/Object;)Ljava/lang/Object;", "putAll", "remove", "(I)Ljava/lang/Object;", "", "(ILjava/lang/Object;)Z", "removeDeletedMarkers", "removeIf", "predicate", "Lkotlin/Function2;", "removeValueAt", "index", "resizeStorage", "newCapacity", "set", "(ILjava/lang/Object;)V", "trim", "writeMetadata", "", "collection"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: IntObjectMap.kt */
public final class MutableIntObjectMap<V> extends IntObjectMap<V> {
    private int growthLimit;

    public MutableIntObjectMap() {
        this(0, 1, (DefaultConstructorMarker) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ MutableIntObjectMap(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 6 : i);
    }

    public MutableIntObjectMap(int initialCapacity) {
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
        this.keys = new int[newCapacity];
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

    public final V getOrPut(int key, Function0<? extends V> defaultValue) {
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        V v = get(key);
        if (v != null) {
            return v;
        }
        Object it = defaultValue.invoke();
        set(key, it);
        return it;
    }

    public final void set(int key, V value) {
        int index = findAbsoluteInsertIndex(key);
        this.keys[index] = key;
        this.values[index] = value;
    }

    public final V put(int key, V value) {
        int index = findAbsoluteInsertIndex(key);
        Object oldValue = this.values[index];
        this.keys[index] = key;
        this.values[index] = value;
        return oldValue;
    }

    public final void putAll(IntObjectMap<V> from) {
        int[] k$iv;
        int $i$f$forEach;
        int[] k$iv2;
        int $i$f$forEach2;
        int i;
        Intrinsics.checkNotNullParameter(from, TypedValues.TransitionType.S_FROM);
        IntObjectMap this_$iv = from;
        int $i$f$forEach3 = 0;
        int[] k$iv3 = this_$iv.keys;
        Object[] v$iv = this_$iv.values;
        long[] m$iv$iv = this_$iv.metadata;
        int lastIndex$iv$iv = m$iv$iv.length - 2;
        int i$iv$iv = 0;
        if (0 <= lastIndex$iv$iv) {
            while (true) {
                long slot$iv$iv = m$iv$iv[i$iv$iv];
                long $this$maskEmptyOrDeleted$iv$iv$iv = slot$iv$iv;
                IntObjectMap this_$iv2 = this_$iv;
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
                    IntObjectMap<V> intObjectMap = from;
                    this_$iv = this_$iv2;
                    $i$f$forEach3 = $i$f$forEach;
                    k$iv3 = k$iv;
                } else {
                    return;
                }
            }
        } else {
            int[] iArr = k$iv3;
        }
    }

    public final void plusAssign(IntObjectMap<V> from) {
        Intrinsics.checkNotNullParameter(from, TypedValues.TransitionType.S_FROM);
        putAll(from);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0086, code lost:
        r10 = r24;
        r8 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0097, code lost:
        if (((((~r8) << 6) & r8) & -9187201950435737472L) == 0) goto L_0x00a5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0099, code lost:
        r8 = -1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final V remove(int r24) {
        /*
            r23 = this;
            r0 = r23
            r1 = r0
            androidx.collection.IntObjectMap r1 = (androidx.collection.IntObjectMap) r1
            r2 = 0
            r3 = 0
            int r4 = java.lang.Integer.hashCode(r24)
            r5 = -862048943(0xffffffffcc9e2d51, float:-8.2930312E7)
            int r4 = r4 * r5
            int r5 = r4 << 16
            r3 = r4 ^ r5
            r4 = 0
            r4 = r3 & 127(0x7f, float:1.78E-43)
            int r5 = r1._capacity
            r6 = 0
            int r6 = r3 >>> 7
            r6 = r6 & r5
            r7 = 0
        L_0x001f:
            long[] r8 = r1.metadata
            r9 = 0
            int r10 = r6 >> 3
            r11 = r6 & 7
            int r11 = r11 << 3
            r12 = r8[r10]
            long r12 = r12 >>> r11
            int r14 = r10 + 1
            r14 = r8[r14]
            int r16 = 64 - r11
            long r14 = r14 << r16
            r16 = r2
            r17 = r3
            long r2 = (long) r11
            long r2 = -r2
            r18 = 63
            long r2 = r2 >> r18
            long r2 = r2 & r14
            long r2 = r2 | r12
            r8 = r2
            r10 = 0
            long r11 = (long) r4
            r13 = 72340172838076673(0x101010101010101, double:7.748604185489348E-304)
            long r11 = r11 * r13
            long r11 = r11 ^ r8
            long r13 = r11 - r13
            r18 = r2
            long r2 = ~r11
            long r2 = r2 & r13
            r13 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r2 = r2 & r13
        L_0x0058:
            r8 = r2
            r10 = 0
            r11 = 0
            int r15 = (r8 > r11 ? 1 : (r8 == r11 ? 0 : -1))
            if (r15 == 0) goto L_0x0062
            r15 = 1
            goto L_0x0063
        L_0x0062:
            r15 = 0
        L_0x0063:
            if (r15 == 0) goto L_0x0086
            r8 = r2
            r10 = 0
            r11 = r8
            r15 = 0
            int r20 = java.lang.Long.numberOfTrailingZeros(r11)
            int r11 = r20 >> 3
            int r11 = r11 + r6
            r8 = r11 & r5
            int[] r9 = r1.keys
            r9 = r9[r8]
            r10 = r24
            if (r9 != r10) goto L_0x007c
            goto L_0x009b
        L_0x007c:
            r11 = r2
            r9 = 0
            r20 = 1
            long r20 = r11 - r20
            long r11 = r11 & r20
            r2 = r11
            goto L_0x0058
        L_0x0086:
            r10 = r24
            r8 = r18
            r15 = 0
            r20 = r11
            long r11 = ~r8
            r22 = 6
            long r11 = r11 << r22
            long r11 = r11 & r8
            long r8 = r11 & r13
            int r8 = (r8 > r20 ? 1 : (r8 == r20 ? 0 : -1))
            if (r8 == 0) goto L_0x00a5
            r8 = -1
        L_0x009b:
            if (r8 < 0) goto L_0x00a3
            java.lang.Object r1 = r0.removeValueAt(r8)
            return r1
        L_0x00a3:
            r1 = 0
            return r1
        L_0x00a5:
            int r7 = r7 + 8
            int r8 = r6 + r7
            r6 = r8 & r5
            r2 = r16
            r3 = r17
            goto L_0x001f
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.MutableIntObjectMap.remove(int):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x008c, code lost:
        r10 = r26;
        r8 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x009d, code lost:
        if (((((~r8) << 6) & r8) & -9187201950435737472L) == 0) goto L_0x00b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x009f, code lost:
        r8 = -1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean remove(int r26, V r27) {
        /*
            r25 = this;
            r0 = r25
            r1 = r0
            androidx.collection.IntObjectMap r1 = (androidx.collection.IntObjectMap) r1
            r2 = 0
            r3 = 0
            int r4 = java.lang.Integer.hashCode(r26)
            r5 = -862048943(0xffffffffcc9e2d51, float:-8.2930312E7)
            int r4 = r4 * r5
            int r5 = r4 << 16
            r3 = r4 ^ r5
            r4 = 0
            r4 = r3 & 127(0x7f, float:1.78E-43)
            int r5 = r1._capacity
            r6 = 0
            int r6 = r3 >>> 7
            r6 = r6 & r5
            r7 = 0
        L_0x001f:
            long[] r8 = r1.metadata
            r9 = 0
            int r10 = r6 >> 3
            r11 = r6 & 7
            int r11 = r11 << 3
            r12 = r8[r10]
            long r12 = r12 >>> r11
            int r14 = r10 + 1
            r14 = r8[r14]
            int r16 = 64 - r11
            long r14 = r14 << r16
            r16 = r2
            r17 = r3
            long r2 = (long) r11
            long r2 = -r2
            r18 = 63
            long r2 = r2 >> r18
            long r2 = r2 & r14
            long r2 = r2 | r12
            r8 = r2
            r10 = 0
            long r11 = (long) r4
            r13 = 72340172838076673(0x101010101010101, double:7.748604185489348E-304)
            long r11 = r11 * r13
            long r11 = r11 ^ r8
            long r13 = r11 - r13
            r18 = r2
            long r2 = ~r11
            long r2 = r2 & r13
            r13 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r2 = r2 & r13
        L_0x0058:
            r8 = r2
            r10 = 0
            r11 = 0
            int r15 = (r8 > r11 ? 1 : (r8 == r11 ? 0 : -1))
            r20 = 0
            r21 = 1
            if (r15 == 0) goto L_0x0067
            r8 = r21
            goto L_0x0069
        L_0x0067:
            r8 = r20
        L_0x0069:
            if (r8 == 0) goto L_0x008c
            r8 = r2
            r10 = 0
            r11 = r8
            r15 = 0
            int r22 = java.lang.Long.numberOfTrailingZeros(r11)
            int r11 = r22 >> 3
            int r11 = r11 + r6
            r8 = r11 & r5
            int[] r9 = r1.keys
            r9 = r9[r8]
            r10 = r26
            if (r9 != r10) goto L_0x0082
            goto L_0x00a1
        L_0x0082:
            r11 = r2
            r9 = 0
            r20 = 1
            long r20 = r11 - r20
            long r11 = r11 & r20
            r2 = r11
            goto L_0x0058
        L_0x008c:
            r10 = r26
            r8 = r18
            r15 = 0
            r22 = r11
            long r11 = ~r8
            r24 = 6
            long r11 = r11 << r24
            long r11 = r11 & r8
            long r8 = r11 & r13
            int r8 = (r8 > r22 ? 1 : (r8 == r22 ? 0 : -1))
            if (r8 == 0) goto L_0x00b7
            r8 = -1
        L_0x00a1:
            if (r8 < 0) goto L_0x00b4
            java.lang.Object[] r1 = r0.values
            r1 = r1[r8]
            r9 = r27
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r1, (java.lang.Object) r9)
            if (r1 == 0) goto L_0x00b6
            r0.removeValueAt(r8)
            return r21
        L_0x00b4:
            r9 = r27
        L_0x00b6:
            return r20
        L_0x00b7:
            r9 = r27
            int r7 = r7 + 8
            int r8 = r6 + r7
            r6 = r8 & r5
            r2 = r16
            r3 = r17
            goto L_0x001f
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.MutableIntObjectMap.remove(int, java.lang.Object):boolean");
    }

    public final void removeIf(Function2<? super Integer, ? super V, Boolean> predicate) {
        int $i$f$removeIf;
        int $i$f$removeIf2;
        int i;
        Function2<? super Integer, ? super V, Boolean> function2 = predicate;
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
                            if (function2.invoke(Integer.valueOf(this.keys[index]), this.values[index]).booleanValue()) {
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

    public final void minusAssign(int key) {
        remove(key);
    }

    public final void minusAssign(int[] keys) {
        Intrinsics.checkNotNullParameter(keys, "keys");
        for (int key : keys) {
            remove(key);
        }
    }

    public final void minusAssign(IntSet keys) {
        IntSet this_$iv;
        IntSet this_$iv2;
        int i;
        Intrinsics.checkNotNullParameter(keys, "keys");
        int $i$f$minusAssign = 0;
        IntSet this_$iv3 = keys;
        int[] k$iv = this_$iv3.elements;
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
                    IntSet intSet = keys;
                    $i$f$minusAssign = $i$f$minusAssign2;
                    this_$iv3 = this_$iv;
                } else {
                    return;
                }
            }
        } else {
            IntSet intSet2 = this_$iv3;
        }
    }

    public final void minusAssign(IntList keys) {
        Intrinsics.checkNotNullParameter(keys, "keys");
        IntList this_$iv = keys;
        int[] content$iv = this_$iv.content;
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
        initializeGrowth();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0088, code lost:
        r9 = r25;
        r7 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0099, code lost:
        if (((((~r7) << 6) & r7) & -9187201950435737472L) == 0) goto L_0x012b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x009b, code lost:
        r4 = r0.findFirstAvailableSlot(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x00a4, code lost:
        if (r0.growthLimit != 0) goto L_0x00c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x00b9, code lost:
        if (((r0.metadata[r4 >> 3] >> ((r4 & 7) << 3)) & 255) != 254) goto L_0x00be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x00bb, code lost:
        r5 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00be, code lost:
        r5 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00c0, code lost:
        if (r5 != null) goto L_0x00c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00c2, code lost:
        r0.adjustStorage();
        r4 = r0.findFirstAvailableSlot(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00c9, code lost:
        r0._size++;
        r5 = r0.growthLimit;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00e5, code lost:
        if (((r0.metadata[r4 >> 3] >> ((r4 & 7) << 3)) & 255) != 128) goto L_0x00e9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00e7, code lost:
        r19 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00e9, code lost:
        r0.growthLimit = r5 - r19;
        r10 = (long) r3;
        r5 = r24;
        r13 = r5.metadata;
        r17 = r4 >> 3;
        r18 = (r4 & 7) << 3;
        r13[r17] = (r13[r17] & (~(255 << r18))) | (r10 << r18);
        r7 = r5._capacity;
        r8 = ((r4 - 7) & r7) + (r7 & 7);
        r17 = r8 >> 3;
        r18 = (r8 & 7) << 3;
        r23 = r1;
        r13[r17] = (r13[r17] & (~(255 << r18))) | (r10 << r18);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x012a, code lost:
        return r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int findAbsoluteInsertIndex(int r25) {
        /*
            r24 = this;
            r0 = r24
            r1 = 0
            int r2 = java.lang.Integer.hashCode(r25)
            r3 = -862048943(0xffffffffcc9e2d51, float:-8.2930312E7)
            int r2 = r2 * r3
            int r3 = r2 << 16
            r1 = r2 ^ r3
            r2 = 0
            int r2 = r1 >>> 7
            r3 = 0
            r3 = r1 & 127(0x7f, float:1.78E-43)
            int r4 = r0._capacity
            r5 = r2 & r4
            r6 = 0
        L_0x001d:
            long[] r7 = r0.metadata
            r8 = 0
            int r9 = r5 >> 3
            r10 = r5 & 7
            int r10 = r10 << 3
            r11 = r7[r9]
            long r11 = r11 >>> r10
            int r13 = r9 + 1
            r13 = r7[r13]
            int r15 = 64 - r10
            long r13 = r13 << r15
            r15 = r4
            r16 = r5
            long r4 = (long) r10
            long r4 = -r4
            r17 = 63
            long r4 = r4 >> r17
            long r4 = r4 & r13
            long r4 = r4 | r11
            r7 = r4
            r9 = 0
            long r10 = (long) r3
            r12 = 72340172838076673(0x101010101010101, double:7.748604185489348E-304)
            long r10 = r10 * r12
            long r10 = r10 ^ r7
            long r12 = r10 - r12
            r17 = r4
            long r4 = ~r10
            long r4 = r4 & r12
            r12 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r4 = r4 & r12
        L_0x0054:
            r7 = r4
            r9 = 0
            r10 = 0
            int r14 = (r7 > r10 ? 1 : (r7 == r10 ? 0 : -1))
            r19 = 0
            r20 = 1
            if (r14 == 0) goto L_0x0063
            r7 = r20
            goto L_0x0065
        L_0x0063:
            r7 = r19
        L_0x0065:
            if (r7 == 0) goto L_0x0088
            r7 = r4
            r9 = 0
            r10 = r7
            r14 = 0
            int r19 = java.lang.Long.numberOfTrailingZeros(r10)
            int r10 = r19 >> 3
            int r7 = r16 + r10
            r7 = r7 & r15
            int[] r8 = r0.keys
            r8 = r8[r7]
            r9 = r25
            if (r8 != r9) goto L_0x007e
            return r7
        L_0x007e:
            r10 = r4
            r8 = 0
            r19 = 1
            long r19 = r10 - r19
            long r10 = r10 & r19
            r4 = r10
            goto L_0x0054
        L_0x0088:
            r9 = r25
            r7 = r17
            r14 = 0
            r21 = r10
            long r10 = ~r7
            r23 = 6
            long r10 = r10 << r23
            long r10 = r10 & r7
            long r7 = r10 & r12
            int r7 = (r7 > r21 ? 1 : (r7 == r21 ? 0 : -1))
            if (r7 == 0) goto L_0x012b
            int r4 = r0.findFirstAvailableSlot(r2)
            int r5 = r0.growthLimit
            r7 = 255(0xff, double:1.26E-321)
            if (r5 != 0) goto L_0x00c9
            long[] r5 = r0.metadata
            r10 = 0
            r11 = 0
            int r12 = r4 >> 3
            r12 = r5[r12]
            r14 = r4 & 7
            int r14 = r14 << 3
            long r12 = r12 >> r14
            long r11 = r12 & r7
            r13 = 254(0xfe, double:1.255E-321)
            int r11 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r11 != 0) goto L_0x00be
            r5 = r20
            goto L_0x00c0
        L_0x00be:
            r5 = r19
        L_0x00c0:
            if (r5 != 0) goto L_0x00c9
            r0.adjustStorage()
            int r4 = r0.findFirstAvailableSlot(r2)
        L_0x00c9:
            int r5 = r0._size
            int r5 = r5 + 1
            r0._size = r5
            int r5 = r0.growthLimit
            long[] r10 = r0.metadata
            r11 = 0
            r12 = 0
            int r13 = r4 >> 3
            r13 = r10[r13]
            r17 = r4 & 7
            int r17 = r17 << 3
            long r13 = r13 >> r17
            long r12 = r13 & r7
            r17 = 128(0x80, double:6.32E-322)
            int r12 = (r12 > r17 ? 1 : (r12 == r17 ? 0 : -1))
            if (r12 != 0) goto L_0x00e9
            r19 = r20
        L_0x00e9:
            int r5 = r5 - r19
            r0.growthLimit = r5
            long r10 = (long) r3
            r5 = r24
            r12 = 0
            long[] r13 = r5.metadata
            r14 = 0
            int r17 = r4 >> 3
            r18 = r4 & 7
            int r18 = r18 << 3
            r19 = r13[r17]
            r21 = r7
            long r7 = r21 << r18
            long r7 = ~r7
            long r7 = r19 & r7
            long r19 = r10 << r18
            long r7 = r7 | r19
            r13[r17] = r7
            int r7 = r5._capacity
            int r8 = r4 + -7
            r8 = r8 & r7
            r14 = r7 & 7
            int r8 = r8 + r14
            r14 = 0
            int r17 = r8 >> 3
            r18 = r8 & 7
            int r18 = r18 << 3
            r19 = r13[r17]
            r23 = r1
            long r0 = r21 << r18
            long r0 = ~r0
            long r0 = r19 & r0
            long r19 = r10 << r18
            long r0 = r0 | r19
            r13[r17] = r0
            return r4
        L_0x012b:
            r23 = r1
            int r6 = r6 + 8
            int r0 = r16 + r6
            r5 = r0 & r15
            r0 = r24
            r4 = r15
            goto L_0x001d
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.collection.MutableIntObjectMap.findAbsoluteInsertIndex(int):int");
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
            removeDeletedMarkers();
        }
    }

    private final void resizeStorage(int newCapacity) {
        long[] previousMetadata;
        MutableIntObjectMap mutableIntObjectMap = this;
        long[] previousMetadata2 = mutableIntObjectMap.metadata;
        int[] previousKeys = mutableIntObjectMap.keys;
        Object[] previousValues = mutableIntObjectMap.values;
        int previousCapacity = mutableIntObjectMap._capacity;
        initializeStorage(newCapacity);
        int[] newKeys = mutableIntObjectMap.keys;
        Object[] newValues = mutableIntObjectMap.values;
        int i = 0;
        while (i < previousCapacity) {
            if (((previousMetadata2[i >> 3] >> ((i & 7) << 3)) & 255) < 128) {
                int previousKey = previousKeys[i];
                int hash$iv = Integer.hashCode(previousKey) * ScatterMapKt.MurmurHashC1;
                int hash = hash$iv ^ (hash$iv << 16);
                int index = mutableIntObjectMap.findFirstAvailableSlot(hash >>> 7);
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
                int i2 = c$iv;
                int i3 = cloneIndex$iv;
                m$iv[i$iv$iv2] = (m$iv[i$iv$iv2] & (~(255 << b$iv$iv2))) | (value$iv << b$iv$iv2);
                newKeys[index] = previousKey;
                newValues[index] = previousValues[i];
            } else {
                previousMetadata = previousMetadata2;
            }
            i++;
            mutableIntObjectMap = this;
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
}
