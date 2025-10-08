package kotlin.collections.builders;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.AbstractList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.jvm.internal.markers.KMutableMap;
import kotlin.ranges.RangesKt;

@Metadata(d1 = {"\u0000¨\u0001\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0002\b\b\n\u0002\u0010#\n\u0002\u0010'\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010&\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0000\u0018\u0000 \u0001*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\u00060\u0004j\u0002`\u0005:\f\u0001\u0001\u0001\u0001\u0001\u0001B\u0007\b\u0016¢\u0006\u0002\u0010\u0006B\u000f\b\u0016\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tBE\b\u0002\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000b\u0012\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u000b\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010\u0010\u001a\u00020\b\u0012\u0006\u0010\u0011\u001a\u00020\b¢\u0006\u0002\u0010\u0012J\u0017\u00103\u001a\u00020\b2\u0006\u00104\u001a\u00028\u0000H\u0000¢\u0006\u0004\b5\u00106J\u0013\u00107\u001a\b\u0012\u0004\u0012\u00028\u00010\u000bH\u0002¢\u0006\u0002\u00108J\u0012\u00109\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010:J\r\u0010;\u001a\u00020<H\u0000¢\u0006\u0002\b=J\b\u0010>\u001a\u00020<H\u0016J\u0010\u0010?\u001a\u00020<2\u0006\u0010@\u001a\u00020!H\u0002J\u0019\u0010A\u001a\u00020!2\n\u0010B\u001a\u0006\u0012\u0002\b\u00030CH\u0000¢\u0006\u0002\bDJ!\u0010E\u001a\u00020!2\u0012\u0010F\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010GH\u0000¢\u0006\u0002\bHJ\u0015\u0010I\u001a\u00020!2\u0006\u00104\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010JJ\u0015\u0010K\u001a\u00020!2\u0006\u0010L\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010JJ\u0018\u0010M\u001a\u00020!2\u000e\u0010N\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030:H\u0002J\u0010\u0010O\u001a\u00020<2\u0006\u0010P\u001a\u00020\bH\u0002J\u0010\u0010Q\u001a\u00020<2\u0006\u0010R\u001a\u00020\bH\u0002J\u0019\u0010S\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010TH\u0000¢\u0006\u0002\bUJ\u0013\u0010V\u001a\u00020!2\b\u0010N\u001a\u0004\u0018\u00010WH\u0002J\u0015\u0010X\u001a\u00020\b2\u0006\u00104\u001a\u00028\u0000H\u0002¢\u0006\u0002\u00106J\u0015\u0010Y\u001a\u00020\b2\u0006\u0010L\u001a\u00028\u0001H\u0002¢\u0006\u0002\u00106J\u0018\u0010Z\u001a\u0004\u0018\u00018\u00012\u0006\u00104\u001a\u00028\u0000H\u0002¢\u0006\u0002\u0010[J\u0015\u0010\\\u001a\u00020\b2\u0006\u00104\u001a\u00028\u0000H\u0002¢\u0006\u0002\u00106J\b\u0010]\u001a\u00020\bH\u0016J\b\u0010^\u001a\u00020!H\u0016J\u0019\u0010_\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010`H\u0000¢\u0006\u0002\baJ\u001f\u0010b\u001a\u0004\u0018\u00018\u00012\u0006\u00104\u001a\u00028\u00002\u0006\u0010L\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010cJ\u001e\u0010d\u001a\u00020<2\u0014\u0010e\u001a\u0010\u0012\u0006\b\u0001\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010:H\u0016J\"\u0010f\u001a\u00020!2\u0018\u0010e\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010G0CH\u0002J\u001c\u0010g\u001a\u00020!2\u0012\u0010F\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010GH\u0002J\u0010\u0010h\u001a\u00020!2\u0006\u0010i\u001a\u00020\bH\u0002J\b\u0010j\u001a\u00020<H\u0002J\u0010\u0010k\u001a\u00020<2\u0006\u0010l\u001a\u00020\bH\u0002J\u0017\u0010m\u001a\u0004\u0018\u00018\u00012\u0006\u00104\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010[J!\u0010n\u001a\u00020!2\u0012\u0010F\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010GH\u0000¢\u0006\u0002\boJ\u0010\u0010p\u001a\u00020<2\u0006\u0010q\u001a\u00020\bH\u0002J\u0010\u0010r\u001a\u00020<2\u0006\u0010s\u001a\u00020\bH\u0002J\u0017\u0010t\u001a\u00020!2\u0006\u00104\u001a\u00028\u0000H\u0000¢\u0006\u0004\bu\u0010JJ\u0017\u0010v\u001a\u00020!2\u0006\u0010w\u001a\u00028\u0001H\u0000¢\u0006\u0004\bx\u0010JJ\u0010\u0010y\u001a\u00020!2\u0006\u0010z\u001a\u00020\bH\u0002J\b\u0010{\u001a\u00020|H\u0016J\u0019\u0010}\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010~H\u0000¢\u0006\u0002\bJ\t\u0010\u0001\u001a\u00020WH\u0002R\u0014\u0010\u0013\u001a\u00020\b8@X\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R&\u0010\u0016\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00180\u00178VX\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u001c\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u001cX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\bX\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\u00020\b8BX\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0015R\u001e\u0010\"\u001a\u00020!2\u0006\u0010 \u001a\u00020!@BX\u000e¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u001a\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00000\u00178VX\u0004¢\u0006\u0006\u001a\u0004\b&\u0010\u001aR\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bX\u000e¢\u0006\u0004\n\u0002\u0010'R\u0016\u0010(\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010)X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\bX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\bX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\bX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u000e¢\u0006\u0002\n\u0000R\u001e\u0010+\u001a\u00020\b2\u0006\u0010 \u001a\u00020\b@RX\u000e¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\u0015R\u001a\u0010-\u001a\b\u0012\u0004\u0012\u00028\u00010.8VX\u0004¢\u0006\u0006\u001a\u0004\b/\u00100R\u0018\u0010\f\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u000bX\u000e¢\u0006\u0004\n\u0002\u0010'R\u0016\u00101\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u000102X\u000e¢\u0006\u0002\n\u0000¨\u0006\u0001"}, d2 = {"Lkotlin/collections/builders/MapBuilder;", "K", "V", "", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "()V", "initialCapacity", "", "(I)V", "keysArray", "", "valuesArray", "presenceArray", "", "hashArray", "maxProbeDistance", "length", "([Ljava/lang/Object;[Ljava/lang/Object;[I[III)V", "capacity", "getCapacity$kotlin_stdlib", "()I", "entries", "", "", "getEntries", "()Ljava/util/Set;", "entriesView", "Lkotlin/collections/builders/MapBuilderEntries;", "hashShift", "hashSize", "getHashSize", "<set-?>", "", "isReadOnly", "isReadOnly$kotlin_stdlib", "()Z", "keys", "getKeys", "[Ljava/lang/Object;", "keysView", "Lkotlin/collections/builders/MapBuilderKeys;", "modCount", "size", "getSize", "values", "", "getValues", "()Ljava/util/Collection;", "valuesView", "Lkotlin/collections/builders/MapBuilderValues;", "addKey", "key", "addKey$kotlin_stdlib", "(Ljava/lang/Object;)I", "allocateValuesArray", "()[Ljava/lang/Object;", "build", "", "checkIsMutable", "", "checkIsMutable$kotlin_stdlib", "clear", "compact", "updateHashArray", "containsAllEntries", "m", "", "containsAllEntries$kotlin_stdlib", "containsEntry", "entry", "", "containsEntry$kotlin_stdlib", "containsKey", "(Ljava/lang/Object;)Z", "containsValue", "value", "contentEquals", "other", "ensureCapacity", "minCapacity", "ensureExtraCapacity", "n", "entriesIterator", "Lkotlin/collections/builders/MapBuilder$EntriesItr;", "entriesIterator$kotlin_stdlib", "equals", "", "findKey", "findValue", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", "hash", "hashCode", "isEmpty", "keysIterator", "Lkotlin/collections/builders/MapBuilder$KeysItr;", "keysIterator$kotlin_stdlib", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "putAll", "from", "putAllEntries", "putEntry", "putRehash", "i", "registerModification", "rehash", "newHashSize", "remove", "removeEntry", "removeEntry$kotlin_stdlib", "removeEntryAt", "index", "removeHashAt", "removedHash", "removeKey", "removeKey$kotlin_stdlib", "removeValue", "element", "removeValue$kotlin_stdlib", "shouldCompact", "extraCapacity", "toString", "", "valuesIterator", "Lkotlin/collections/builders/MapBuilder$ValuesItr;", "valuesIterator$kotlin_stdlib", "writeReplace", "Companion", "EntriesItr", "EntryRef", "Itr", "KeysItr", "ValuesItr", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* compiled from: MapBuilder.kt */
public final class MapBuilder<K, V> implements Map<K, V>, Serializable, KMutableMap {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static final MapBuilder Empty;
    private static final int INITIAL_CAPACITY = 8;
    private static final int INITIAL_MAX_PROBE_DISTANCE = 2;
    private static final int MAGIC = -1640531527;
    private static final int TOMBSTONE = -1;
    private MapBuilderEntries<K, V> entriesView;
    private int[] hashArray;
    private int hashShift;
    private boolean isReadOnly;
    /* access modifiers changed from: private */
    public K[] keysArray;
    private MapBuilderKeys<K> keysView;
    /* access modifiers changed from: private */
    public int length;
    private int maxProbeDistance;
    /* access modifiers changed from: private */
    public int modCount;
    /* access modifiers changed from: private */
    public int[] presenceArray;
    private int size;
    /* access modifiers changed from: private */
    public V[] valuesArray;
    private MapBuilderValues<V> valuesView;

    private MapBuilder(K[] keysArray2, V[] valuesArray2, int[] presenceArray2, int[] hashArray2, int maxProbeDistance2, int length2) {
        this.keysArray = keysArray2;
        this.valuesArray = valuesArray2;
        this.presenceArray = presenceArray2;
        this.hashArray = hashArray2;
        this.maxProbeDistance = maxProbeDistance2;
        this.length = length2;
        this.hashShift = Companion.computeShift(getHashSize());
    }

    public final /* bridge */ Set<Map.Entry<K, V>> entrySet() {
        return getEntries();
    }

    public final /* bridge */ Set<K> keySet() {
        return getKeys();
    }

    public final /* bridge */ int size() {
        return getSize();
    }

    public final /* bridge */ Collection<V> values() {
        return getValues();
    }

    public int getSize() {
        return this.size;
    }

    public final boolean isReadOnly$kotlin_stdlib() {
        return this.isReadOnly;
    }

    public MapBuilder() {
        this(8);
    }

    public MapBuilder(int initialCapacity) {
        this(ListBuilderKt.arrayOfUninitializedElements(initialCapacity), (V[]) null, new int[initialCapacity], new int[Companion.computeHashSize(initialCapacity)], 2, 0);
    }

    public final Map<K, V> build() {
        checkIsMutable$kotlin_stdlib();
        this.isReadOnly = true;
        if (size() > 0) {
            return this;
        }
        MapBuilder mapBuilder = Empty;
        Intrinsics.checkNotNull(mapBuilder, "null cannot be cast to non-null type kotlin.collections.Map<K of kotlin.collections.builders.MapBuilder, V of kotlin.collections.builders.MapBuilder>");
        return mapBuilder;
    }

    private final Object writeReplace() {
        if (this.isReadOnly) {
            return new SerializedMap(this);
        }
        throw new NotSerializableException("The map cannot be serialized while it is being built.");
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(Object key) {
        return findKey(key) >= 0;
    }

    public boolean containsValue(Object value) {
        return findValue(value) >= 0;
    }

    public V get(Object key) {
        int index = findKey(key);
        if (index < 0) {
            return null;
        }
        V[] vArr = this.valuesArray;
        Intrinsics.checkNotNull(vArr);
        return vArr[index];
    }

    public V put(K key, V value) {
        checkIsMutable$kotlin_stdlib();
        int index = addKey$kotlin_stdlib(key);
        Object[] valuesArray2 = allocateValuesArray();
        if (index < 0) {
            Object oldValue = valuesArray2[(-index) - 1];
            valuesArray2[(-index) - 1] = value;
            return oldValue;
        }
        valuesArray2[index] = value;
        return null;
    }

    public void putAll(Map<? extends K, ? extends V> from) {
        Intrinsics.checkNotNullParameter(from, TypedValues.TransitionType.S_FROM);
        checkIsMutable$kotlin_stdlib();
        putAllEntries(from.entrySet());
    }

    public V remove(Object key) {
        checkIsMutable$kotlin_stdlib();
        int index = findKey(key);
        if (index < 0) {
            return null;
        }
        Object[] objArr = this.valuesArray;
        Intrinsics.checkNotNull(objArr);
        Object oldValue = objArr[index];
        removeEntryAt(index);
        return oldValue;
    }

    public void clear() {
        checkIsMutable$kotlin_stdlib();
        int i = 0;
        int i2 = this.length - 1;
        if (0 <= i2) {
            while (true) {
                int hash = this.presenceArray[i];
                if (hash >= 0) {
                    this.hashArray[hash] = 0;
                    this.presenceArray[i] = -1;
                }
                if (i == i2) {
                    break;
                }
                i++;
            }
        }
        ListBuilderKt.resetRange(this.keysArray, 0, this.length);
        V[] vArr = this.valuesArray;
        if (vArr != null) {
            ListBuilderKt.resetRange(vArr, 0, this.length);
        }
        this.size = 0;
        this.length = 0;
        registerModification();
    }

    public Set<K> getKeys() {
        MapBuilderKeys cur = this.keysView;
        if (cur != null) {
            return cur;
        }
        MapBuilderKeys mapBuilderKeys = new MapBuilderKeys(this);
        this.keysView = mapBuilderKeys;
        return mapBuilderKeys;
    }

    public Collection<V> getValues() {
        MapBuilderValues cur = this.valuesView;
        if (cur != null) {
            return cur;
        }
        MapBuilderValues mapBuilderValues = new MapBuilderValues(this);
        this.valuesView = mapBuilderValues;
        return mapBuilderValues;
    }

    public Set<Map.Entry<K, V>> getEntries() {
        MapBuilderEntries cur = this.entriesView;
        if (cur != null) {
            return cur;
        }
        MapBuilderEntries<K, V> mapBuilderEntries = new MapBuilderEntries<>(this);
        this.entriesView = mapBuilderEntries;
        return mapBuilderEntries;
    }

    public boolean equals(Object other) {
        return other == this || ((other instanceof Map) && contentEquals((Map) other));
    }

    public int hashCode() {
        int result = 0;
        EntriesItr it = entriesIterator$kotlin_stdlib();
        while (it.hasNext()) {
            result += it.nextHashCode$kotlin_stdlib();
        }
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder((size() * 3) + 2);
        sb.append("{");
        int i = 0;
        EntriesItr it = entriesIterator$kotlin_stdlib();
        while (it.hasNext()) {
            if (i > 0) {
                sb.append(", ");
            }
            it.nextAppendString(sb);
            i++;
        }
        sb.append("}");
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
        return sb2;
    }

    public final int getCapacity$kotlin_stdlib() {
        return this.keysArray.length;
    }

    private final int getHashSize() {
        return this.hashArray.length;
    }

    private final void registerModification() {
        this.modCount++;
    }

    public final void checkIsMutable$kotlin_stdlib() {
        if (this.isReadOnly) {
            throw new UnsupportedOperationException();
        }
    }

    private final void ensureExtraCapacity(int n) {
        if (shouldCompact(n)) {
            compact(true);
        } else {
            ensureCapacity(this.length + n);
        }
    }

    private final boolean shouldCompact(int extraCapacity) {
        int spareCapacity = getCapacity$kotlin_stdlib() - this.length;
        int gaps = this.length - size();
        return spareCapacity < extraCapacity && gaps + spareCapacity >= extraCapacity && gaps >= getCapacity$kotlin_stdlib() / 4;
    }

    private final void ensureCapacity(int minCapacity) {
        if (minCapacity < 0) {
            throw new OutOfMemoryError();
        } else if (minCapacity > getCapacity$kotlin_stdlib()) {
            int newSize = AbstractList.Companion.newCapacity$kotlin_stdlib(getCapacity$kotlin_stdlib(), minCapacity);
            this.keysArray = ListBuilderKt.copyOfUninitializedElements(this.keysArray, newSize);
            V[] vArr = this.valuesArray;
            this.valuesArray = vArr != null ? ListBuilderKt.copyOfUninitializedElements(vArr, newSize) : null;
            int[] copyOf = Arrays.copyOf(this.presenceArray, newSize);
            Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(...)");
            this.presenceArray = copyOf;
            int newHashSize = Companion.computeHashSize(newSize);
            if (newHashSize > getHashSize()) {
                rehash(newHashSize);
            }
        }
    }

    /* access modifiers changed from: private */
    public final V[] allocateValuesArray() {
        Object[] curValuesArray = this.valuesArray;
        if (curValuesArray != null) {
            return curValuesArray;
        }
        Object[] newValuesArray = ListBuilderKt.arrayOfUninitializedElements(getCapacity$kotlin_stdlib());
        this.valuesArray = newValuesArray;
        return newValuesArray;
    }

    private final int hash(K key) {
        return ((key != null ? key.hashCode() : 0) * MAGIC) >>> this.hashShift;
    }

    private final void compact(boolean updateHashArray) {
        int j = 0;
        Object[] valuesArray2 = this.valuesArray;
        for (int i = 0; i < this.length; i++) {
            int hash = this.presenceArray[i];
            if (hash >= 0) {
                this.keysArray[j] = this.keysArray[i];
                if (valuesArray2 != null) {
                    valuesArray2[j] = valuesArray2[i];
                }
                if (updateHashArray) {
                    this.presenceArray[j] = hash;
                    this.hashArray[hash] = j + 1;
                }
                j++;
            }
        }
        ListBuilderKt.resetRange(this.keysArray, j, this.length);
        if (valuesArray2 != null) {
            ListBuilderKt.resetRange(valuesArray2, j, this.length);
        }
        this.length = j;
    }

    private final void rehash(int newHashSize) {
        registerModification();
        if (this.length > size()) {
            compact(false);
        }
        this.hashArray = new int[newHashSize];
        this.hashShift = Companion.computeShift(newHashSize);
        int i = 0;
        while (i < this.length) {
            int i2 = i + 1;
            if (putRehash(i) != 0) {
                i = i2;
            } else {
                throw new IllegalStateException("This cannot happen with fixed magic multiplier and grow-only hash array. Have object hashCodes changed?");
            }
        }
    }

    private final boolean putRehash(int i) {
        int hash = hash(this.keysArray[i]);
        int probesLeft = this.maxProbeDistance;
        while (this.hashArray[hash] != 0) {
            probesLeft--;
            if (probesLeft < 0) {
                return false;
            }
            hash = hash == 0 ? getHashSize() - 1 : hash - 1;
        }
        this.hashArray[hash] = i + 1;
        this.presenceArray[i] = hash;
        return true;
    }

    private final int findKey(K key) {
        int hash = hash(key);
        int probesLeft = this.maxProbeDistance;
        while (true) {
            int index = this.hashArray[hash];
            if (index == 0) {
                return -1;
            }
            if (index > 0 && Intrinsics.areEqual((Object) this.keysArray[index - 1], (Object) key)) {
                return index - 1;
            }
            probesLeft--;
            if (probesLeft < 0) {
                return -1;
            }
            hash = hash == 0 ? getHashSize() - 1 : hash - 1;
        }
    }

    private final int findValue(V value) {
        int i = this.length;
        while (true) {
            i--;
            if (i < 0) {
                return -1;
            }
            if (this.presenceArray[i] >= 0) {
                V[] vArr = this.valuesArray;
                Intrinsics.checkNotNull(vArr);
                if (Intrinsics.areEqual((Object) vArr[i], (Object) value)) {
                    return i;
                }
            }
        }
    }

    public final int addKey$kotlin_stdlib(K key) {
        checkIsMutable$kotlin_stdlib();
        while (true) {
            int hash = hash(key);
            int tentativeMaxProbeDistance = RangesKt.coerceAtMost(this.maxProbeDistance * 2, getHashSize() / 2);
            int probeDistance = 0;
            while (true) {
                int index = this.hashArray[hash];
                if (index <= 0) {
                    if (this.length >= getCapacity$kotlin_stdlib()) {
                        ensureExtraCapacity(1);
                    } else {
                        int putIndex = this.length;
                        this.length = putIndex + 1;
                        this.keysArray[putIndex] = key;
                        this.presenceArray[putIndex] = hash;
                        this.hashArray[hash] = putIndex + 1;
                        this.size = size() + 1;
                        registerModification();
                        if (probeDistance > this.maxProbeDistance) {
                            this.maxProbeDistance = probeDistance;
                        }
                        return putIndex;
                    }
                } else if (Intrinsics.areEqual((Object) this.keysArray[index - 1], (Object) key)) {
                    return -index;
                } else {
                    probeDistance++;
                    if (probeDistance > tentativeMaxProbeDistance) {
                        rehash(getHashSize() * 2);
                        break;
                    }
                    hash = hash == 0 ? getHashSize() - 1 : hash - 1;
                }
            }
        }
    }

    public final boolean removeKey$kotlin_stdlib(K key) {
        checkIsMutable$kotlin_stdlib();
        int index = findKey(key);
        if (index < 0) {
            return false;
        }
        removeEntryAt(index);
        return true;
    }

    /* access modifiers changed from: private */
    public final void removeEntryAt(int index) {
        ListBuilderKt.resetAt(this.keysArray, index);
        V[] vArr = this.valuesArray;
        if (vArr != null) {
            ListBuilderKt.resetAt(vArr, index);
        }
        removeHashAt(this.presenceArray[index]);
        this.presenceArray[index] = -1;
        this.size = size() - 1;
        registerModification();
    }

    private final void removeHashAt(int removedHash) {
        int hash = removedHash;
        int hole = removedHash;
        int probeDistance = 0;
        int patchAttemptsLeft = RangesKt.coerceAtMost(this.maxProbeDistance * 2, getHashSize() / 2);
        do {
            hash = hash == 0 ? getHashSize() - 1 : hash - 1;
            probeDistance++;
            if (probeDistance > this.maxProbeDistance) {
                this.hashArray[hole] = 0;
                return;
            }
            int index = this.hashArray[hash];
            if (index == 0) {
                this.hashArray[hole] = 0;
                return;
            }
            if (index < 0) {
                this.hashArray[hole] = -1;
                hole = hash;
                probeDistance = 0;
            } else if (((hash(this.keysArray[index - 1]) - hash) & (getHashSize() - 1)) >= probeDistance) {
                this.hashArray[hole] = index;
                this.presenceArray[index - 1] = hole;
                hole = hash;
                probeDistance = 0;
            }
            patchAttemptsLeft--;
        } while (patchAttemptsLeft >= 0);
        this.hashArray[hole] = -1;
    }

    public final boolean containsEntry$kotlin_stdlib(Map.Entry<? extends K, ? extends V> entry) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        int index = findKey(entry.getKey());
        if (index < 0) {
            return false;
        }
        V[] vArr = this.valuesArray;
        Intrinsics.checkNotNull(vArr);
        return Intrinsics.areEqual((Object) vArr[index], (Object) entry.getValue());
    }

    private final boolean contentEquals(Map<?, ?> other) {
        return size() == other.size() && containsAllEntries$kotlin_stdlib(other.entrySet());
    }

    public final boolean containsAllEntries$kotlin_stdlib(Collection<?> m) {
        Intrinsics.checkNotNullParameter(m, "m");
        for (Object entry : m) {
            if (entry != null) {
                try {
                    if (!containsEntry$kotlin_stdlib((Map.Entry) entry)) {
                    }
                } catch (ClassCastException e) {
                    return false;
                }
            }
            return false;
        }
        return true;
    }

    private final boolean putEntry(Map.Entry<? extends K, ? extends V> entry) {
        int index = addKey$kotlin_stdlib(entry.getKey());
        Object[] valuesArray2 = allocateValuesArray();
        if (index >= 0) {
            valuesArray2[index] = entry.getValue();
            return true;
        }
        if (Intrinsics.areEqual((Object) entry.getValue(), valuesArray2[(-index) - 1])) {
            return false;
        }
        valuesArray2[(-index) - 1] = entry.getValue();
        return true;
    }

    private final boolean putAllEntries(Collection<? extends Map.Entry<? extends K, ? extends V>> from) {
        if (from.isEmpty()) {
            return false;
        }
        ensureExtraCapacity(from.size());
        boolean updated = false;
        for (Map.Entry putEntry : from) {
            if (putEntry(putEntry)) {
                updated = true;
            }
        }
        return updated;
    }

    public final boolean removeEntry$kotlin_stdlib(Map.Entry<? extends K, ? extends V> entry) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        checkIsMutable$kotlin_stdlib();
        int index = findKey(entry.getKey());
        if (index < 0) {
            return false;
        }
        V[] vArr = this.valuesArray;
        Intrinsics.checkNotNull(vArr);
        if (!Intrinsics.areEqual((Object) vArr[index], (Object) entry.getValue())) {
            return false;
        }
        removeEntryAt(index);
        return true;
    }

    public final boolean removeValue$kotlin_stdlib(V element) {
        checkIsMutable$kotlin_stdlib();
        int index = findValue(element);
        if (index < 0) {
            return false;
        }
        removeEntryAt(index);
        return true;
    }

    public final KeysItr<K, V> keysIterator$kotlin_stdlib() {
        return new KeysItr<>(this);
    }

    public final ValuesItr<K, V> valuesIterator$kotlin_stdlib() {
        return new ValuesItr<>(this);
    }

    public final EntriesItr<K, V> entriesIterator$kotlin_stdlib() {
        return new EntriesItr<>(this);
    }

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\b\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\tH\u0002J\u0010\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\tH\u0002R \u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tXT¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tXT¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tXT¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tXT¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lkotlin/collections/builders/MapBuilder$Companion;", "", "()V", "Empty", "Lkotlin/collections/builders/MapBuilder;", "", "getEmpty$kotlin_stdlib", "()Lkotlin/collections/builders/MapBuilder;", "INITIAL_CAPACITY", "", "INITIAL_MAX_PROBE_DISTANCE", "MAGIC", "TOMBSTONE", "computeHashSize", "capacity", "computeShift", "hashSize", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* compiled from: MapBuilder.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final MapBuilder getEmpty$kotlin_stdlib() {
            return MapBuilder.Empty;
        }

        /* access modifiers changed from: private */
        public final int computeHashSize(int capacity) {
            return Integer.highestOneBit(RangesKt.coerceAtLeast(capacity, 1) * 3);
        }

        /* access modifiers changed from: private */
        public final int computeShift(int hashSize) {
            return Integer.numberOfLeadingZeros(hashSize) + 1;
        }
    }

    static {
        MapBuilder it = new MapBuilder(0);
        it.isReadOnly = true;
        Empty = it;
    }

    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0010\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u00020\u0003B\u0019\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0005¢\u0006\u0002\u0010\u0006J\r\u0010\u0013\u001a\u00020\u0014H\u0000¢\u0006\u0002\b\u0015J\u0006\u0010\u0016\u001a\u00020\u0017J\r\u0010\u0018\u001a\u00020\u0014H\u0000¢\u0006\u0002\b\u0019J\u0006\u0010\u001a\u001a\u00020\u0014R\u000e\u0010\u0007\u001a\u00020\bX\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u00020\bX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\bX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u000b\"\u0004\b\u0010\u0010\rR \u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0005X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001b"}, d2 = {"Lkotlin/collections/builders/MapBuilder$Itr;", "K", "V", "", "map", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "expectedModCount", "", "index", "getIndex$kotlin_stdlib", "()I", "setIndex$kotlin_stdlib", "(I)V", "lastIndex", "getLastIndex$kotlin_stdlib", "setLastIndex$kotlin_stdlib", "getMap$kotlin_stdlib", "()Lkotlin/collections/builders/MapBuilder;", "checkForComodification", "", "checkForComodification$kotlin_stdlib", "hasNext", "", "initNext", "initNext$kotlin_stdlib", "remove", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* compiled from: MapBuilder.kt */
    public static class Itr<K, V> {
        private int expectedModCount = this.map.modCount;
        private int index;
        private int lastIndex = -1;
        private final MapBuilder<K, V> map;

        public Itr(MapBuilder<K, V> map2) {
            Intrinsics.checkNotNullParameter(map2, "map");
            this.map = map2;
            initNext$kotlin_stdlib();
        }

        public final MapBuilder<K, V> getMap$kotlin_stdlib() {
            return this.map;
        }

        public final int getIndex$kotlin_stdlib() {
            return this.index;
        }

        public final void setIndex$kotlin_stdlib(int i) {
            this.index = i;
        }

        public final int getLastIndex$kotlin_stdlib() {
            return this.lastIndex;
        }

        public final void setLastIndex$kotlin_stdlib(int i) {
            this.lastIndex = i;
        }

        public final void initNext$kotlin_stdlib() {
            while (this.index < this.map.length && this.map.presenceArray[this.index] < 0) {
                this.index++;
            }
        }

        public final boolean hasNext() {
            return this.index < this.map.length;
        }

        public final void remove() {
            checkForComodification$kotlin_stdlib();
            if (this.lastIndex != -1) {
                this.map.checkIsMutable$kotlin_stdlib();
                this.map.removeEntryAt(this.lastIndex);
                this.lastIndex = -1;
                this.expectedModCount = this.map.modCount;
                return;
            }
            throw new IllegalStateException("Call next() before removing element from the iterator.".toString());
        }

        public final void checkForComodification$kotlin_stdlib() {
            if (this.map.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010)\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\b\u0012\u0004\u0012\u0002H\u00010\u0004B\u0019\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\b\u001a\u00028\u0002H\u0002¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Lkotlin/collections/builders/MapBuilder$KeysItr;", "K", "V", "Lkotlin/collections/builders/MapBuilder$Itr;", "", "map", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "next", "()Ljava/lang/Object;", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* compiled from: MapBuilder.kt */
    public static final class KeysItr<K, V> extends Itr<K, V> implements Iterator<K>, KMutableIterator {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public KeysItr(MapBuilder<K, V> map) {
            super(map);
            Intrinsics.checkNotNullParameter(map, "map");
        }

        public K next() {
            checkForComodification$kotlin_stdlib();
            if (getIndex$kotlin_stdlib() < getMap$kotlin_stdlib().length) {
                int index$kotlin_stdlib = getIndex$kotlin_stdlib();
                setIndex$kotlin_stdlib(index$kotlin_stdlib + 1);
                setLastIndex$kotlin_stdlib(index$kotlin_stdlib);
                Object result = getMap$kotlin_stdlib().keysArray[getLastIndex$kotlin_stdlib()];
                initNext$kotlin_stdlib();
                return result;
            }
            throw new NoSuchElementException();
        }
    }

    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010)\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\b\u0012\u0004\u0012\u0002H\u00020\u0004B\u0019\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\b\u001a\u00028\u0003H\u0002¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Lkotlin/collections/builders/MapBuilder$ValuesItr;", "K", "V", "Lkotlin/collections/builders/MapBuilder$Itr;", "", "map", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "next", "()Ljava/lang/Object;", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* compiled from: MapBuilder.kt */
    public static final class ValuesItr<K, V> extends Itr<K, V> implements Iterator<V>, KMutableIterator {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public ValuesItr(MapBuilder<K, V> map) {
            super(map);
            Intrinsics.checkNotNullParameter(map, "map");
        }

        public V next() {
            checkForComodification$kotlin_stdlib();
            if (getIndex$kotlin_stdlib() < getMap$kotlin_stdlib().length) {
                int index$kotlin_stdlib = getIndex$kotlin_stdlib();
                setIndex$kotlin_stdlib(index$kotlin_stdlib + 1);
                setLastIndex$kotlin_stdlib(index$kotlin_stdlib);
                Object[] access$getValuesArray$p = getMap$kotlin_stdlib().valuesArray;
                Intrinsics.checkNotNull(access$getValuesArray$p);
                Object result = access$getValuesArray$p[getLastIndex$kotlin_stdlib()];
                initNext$kotlin_stdlib();
                return result;
            }
            throw new NoSuchElementException();
        }
    }

    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010)\n\u0002\u0010'\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00050\u0004B\u0019\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0007¢\u0006\u0002\u0010\bJ\u0015\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\nH\u0002J\u0012\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u00060\u000ej\u0002`\u000fJ\r\u0010\u0010\u001a\u00020\u0011H\u0000¢\u0006\u0002\b\u0012¨\u0006\u0013"}, d2 = {"Lkotlin/collections/builders/MapBuilder$EntriesItr;", "K", "V", "Lkotlin/collections/builders/MapBuilder$Itr;", "", "", "map", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "next", "Lkotlin/collections/builders/MapBuilder$EntryRef;", "nextAppendString", "", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "nextHashCode", "", "nextHashCode$kotlin_stdlib", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* compiled from: MapBuilder.kt */
    public static final class EntriesItr<K, V> extends Itr<K, V> implements Iterator<Map.Entry<K, V>>, KMutableIterator {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public EntriesItr(MapBuilder<K, V> map) {
            super(map);
            Intrinsics.checkNotNullParameter(map, "map");
        }

        public EntryRef<K, V> next() {
            checkForComodification$kotlin_stdlib();
            if (getIndex$kotlin_stdlib() < getMap$kotlin_stdlib().length) {
                int index$kotlin_stdlib = getIndex$kotlin_stdlib();
                setIndex$kotlin_stdlib(index$kotlin_stdlib + 1);
                setLastIndex$kotlin_stdlib(index$kotlin_stdlib);
                EntryRef result = new EntryRef(getMap$kotlin_stdlib(), getLastIndex$kotlin_stdlib());
                initNext$kotlin_stdlib();
                return result;
            }
            throw new NoSuchElementException();
        }

        public final int nextHashCode$kotlin_stdlib() {
            if (getIndex$kotlin_stdlib() < getMap$kotlin_stdlib().length) {
                int index$kotlin_stdlib = getIndex$kotlin_stdlib();
                setIndex$kotlin_stdlib(index$kotlin_stdlib + 1);
                setLastIndex$kotlin_stdlib(index$kotlin_stdlib);
                Object obj = getMap$kotlin_stdlib().keysArray[getLastIndex$kotlin_stdlib()];
                int i = 0;
                int hashCode = obj != null ? obj.hashCode() : 0;
                Object[] access$getValuesArray$p = getMap$kotlin_stdlib().valuesArray;
                Intrinsics.checkNotNull(access$getValuesArray$p);
                Object obj2 = access$getValuesArray$p[getLastIndex$kotlin_stdlib()];
                if (obj2 != null) {
                    i = obj2.hashCode();
                }
                int result = hashCode ^ i;
                initNext$kotlin_stdlib();
                return result;
            }
            throw new NoSuchElementException();
        }

        public final void nextAppendString(StringBuilder sb) {
            Intrinsics.checkNotNullParameter(sb, "sb");
            if (getIndex$kotlin_stdlib() < getMap$kotlin_stdlib().length) {
                int index$kotlin_stdlib = getIndex$kotlin_stdlib();
                setIndex$kotlin_stdlib(index$kotlin_stdlib + 1);
                setLastIndex$kotlin_stdlib(index$kotlin_stdlib);
                Object key = getMap$kotlin_stdlib().keysArray[getLastIndex$kotlin_stdlib()];
                if (key == getMap$kotlin_stdlib()) {
                    sb.append("(this Map)");
                } else {
                    sb.append(key);
                }
                sb.append('=');
                Object[] access$getValuesArray$p = getMap$kotlin_stdlib().valuesArray;
                Intrinsics.checkNotNull(access$getValuesArray$p);
                Object value = access$getValuesArray$p[getLastIndex$kotlin_stdlib()];
                if (value == getMap$kotlin_stdlib()) {
                    sb.append("(this Map)");
                } else {
                    sb.append(value);
                }
                initNext$kotlin_stdlib();
                return;
            }
            throw new NoSuchElementException();
        }
    }

    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010'\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B!\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0002J\b\u0010\u0012\u001a\u00020\u0007H\u0016J\u0015\u0010\u0013\u001a\u00028\u00032\u0006\u0010\u0014\u001a\u00028\u0003H\u0016¢\u0006\u0002\u0010\u0015J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00028\u00028VX\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0005X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00028\u00038VX\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000b¨\u0006\u0018"}, d2 = {"Lkotlin/collections/builders/MapBuilder$EntryRef;", "K", "V", "", "map", "Lkotlin/collections/builders/MapBuilder;", "index", "", "(Lkotlin/collections/builders/MapBuilder;I)V", "key", "getKey", "()Ljava/lang/Object;", "value", "getValue", "equals", "", "other", "", "hashCode", "setValue", "newValue", "(Ljava/lang/Object;)Ljava/lang/Object;", "toString", "", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* compiled from: MapBuilder.kt */
    public static final class EntryRef<K, V> implements Map.Entry<K, V>, KMutableMap.Entry {
        private final int index;
        private final MapBuilder<K, V> map;

        public EntryRef(MapBuilder<K, V> map2, int index2) {
            Intrinsics.checkNotNullParameter(map2, "map");
            this.map = map2;
            this.index = index2;
        }

        public K getKey() {
            return this.map.keysArray[this.index];
        }

        public V getValue() {
            V[] access$getValuesArray$p = this.map.valuesArray;
            Intrinsics.checkNotNull(access$getValuesArray$p);
            return access$getValuesArray$p[this.index];
        }

        public V setValue(V newValue) {
            this.map.checkIsMutable$kotlin_stdlib();
            Object[] valuesArray = this.map.allocateValuesArray();
            Object oldValue = valuesArray[this.index];
            valuesArray[this.index] = newValue;
            return oldValue;
        }

        public boolean equals(Object other) {
            return (other instanceof Map.Entry) && Intrinsics.areEqual(((Map.Entry) other).getKey(), getKey()) && Intrinsics.areEqual(((Map.Entry) other).getValue(), getValue());
        }

        public int hashCode() {
            Object key = getKey();
            int i = 0;
            int hashCode = key != null ? key.hashCode() : 0;
            Object value = getValue();
            if (value != null) {
                i = value.hashCode();
            }
            return hashCode ^ i;
        }

        public String toString() {
            return new StringBuilder().append(getKey()).append('=').append(getValue()).toString();
        }
    }
}
