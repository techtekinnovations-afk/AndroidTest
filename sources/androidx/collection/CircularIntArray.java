package androidx.collection;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0003J\u000e\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0003J\u0006\u0010\u0013\u001a\u00020\u0010J\b\u0010\u0014\u001a\u00020\u0010H\u0002J\u0011\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u0003H\u0002J\u0006\u0010\u0017\u001a\u00020\u0018J\u0006\u0010\u0019\u001a\u00020\u0003J\u0006\u0010\u001a\u001a\u00020\u0003J\u000e\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u0003J\u000e\u0010\u001d\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u0003J\u0006\u0010\u001e\u001a\u00020\u0003R\u000e\u0010\u0005\u001a\u00020\u0003X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0003X\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\r\u0010\nR\u000e\u0010\u000e\u001a\u00020\u0003X\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Landroidx/collection/CircularIntArray;", "", "minCapacity", "", "(I)V", "capacityBitmask", "elements", "", "first", "getFirst", "()I", "head", "last", "getLast", "tail", "addFirst", "", "element", "addLast", "clear", "doubleCapacity", "get", "index", "isEmpty", "", "popFirst", "popLast", "removeFromEnd", "count", "removeFromStart", "size", "collection"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: CircularIntArray.kt */
public final class CircularIntArray {
    private int capacityBitmask;
    private int[] elements;
    private int head;
    private int tail;

    public CircularIntArray() {
        this(0, 1, (DefaultConstructorMarker) null);
    }

    public CircularIntArray(int minCapacity) {
        int arrayCapacity;
        boolean z = false;
        if (minCapacity >= 1) {
            if (minCapacity <= 1073741824 ? true : z) {
                if (Integer.bitCount(minCapacity) != 1) {
                    arrayCapacity = Integer.highestOneBit(minCapacity - 1) << 1;
                } else {
                    arrayCapacity = minCapacity;
                }
                this.capacityBitmask = arrayCapacity - 1;
                this.elements = new int[arrayCapacity];
                return;
            }
            throw new IllegalArgumentException("capacity must be <= 2^30".toString());
        }
        throw new IllegalArgumentException("capacity must be >= 1".toString());
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ CircularIntArray(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 8 : i);
    }

    private final void doubleCapacity() {
        int n = this.elements.length;
        int r = n - this.head;
        int newCapacity = n << 1;
        if (newCapacity >= 0) {
            int[] a = new int[newCapacity];
            ArraysKt.copyInto(this.elements, a, 0, this.head, n);
            ArraysKt.copyInto(this.elements, a, r, 0, this.head);
            this.elements = a;
            this.head = 0;
            this.tail = n;
            this.capacityBitmask = newCapacity - 1;
            return;
        }
        throw new RuntimeException("Max array capacity exceeded");
    }

    public final void addFirst(int element) {
        this.head = (this.head - 1) & this.capacityBitmask;
        this.elements[this.head] = element;
        if (this.head == this.tail) {
            doubleCapacity();
        }
    }

    public final void addLast(int element) {
        this.elements[this.tail] = element;
        this.tail = (this.tail + 1) & this.capacityBitmask;
        if (this.tail == this.head) {
            doubleCapacity();
        }
    }

    public final int popFirst() {
        if (this.head != this.tail) {
            int result = this.elements[this.head];
            this.head = (this.head + 1) & this.capacityBitmask;
            return result;
        }
        CollectionPlatformUtils collectionPlatformUtils = CollectionPlatformUtils.INSTANCE;
        throw new ArrayIndexOutOfBoundsException();
    }

    public final int popLast() {
        if (this.head != this.tail) {
            int t = (this.tail - 1) & this.capacityBitmask;
            int result = this.elements[t];
            this.tail = t;
            return result;
        }
        CollectionPlatformUtils collectionPlatformUtils = CollectionPlatformUtils.INSTANCE;
        throw new ArrayIndexOutOfBoundsException();
    }

    public final void clear() {
        this.tail = this.head;
    }

    public final void removeFromStart(int count) {
        if (count > 0) {
            if (count <= size()) {
                this.head = (this.head + count) & this.capacityBitmask;
            } else {
                CollectionPlatformUtils collectionPlatformUtils = CollectionPlatformUtils.INSTANCE;
                throw new ArrayIndexOutOfBoundsException();
            }
        }
    }

    public final void removeFromEnd(int count) {
        if (count > 0) {
            if (count <= size()) {
                this.tail = (this.tail - count) & this.capacityBitmask;
            } else {
                CollectionPlatformUtils collectionPlatformUtils = CollectionPlatformUtils.INSTANCE;
                throw new ArrayIndexOutOfBoundsException();
            }
        }
    }

    public final int getFirst() {
        if (this.head != this.tail) {
            return this.elements[this.head];
        }
        CollectionPlatformUtils collectionPlatformUtils = CollectionPlatformUtils.INSTANCE;
        throw new ArrayIndexOutOfBoundsException();
    }

    public final int getLast() {
        if (this.head != this.tail) {
            return this.elements[(this.tail - 1) & this.capacityBitmask];
        }
        CollectionPlatformUtils collectionPlatformUtils = CollectionPlatformUtils.INSTANCE;
        throw new ArrayIndexOutOfBoundsException();
    }

    public final int get(int index) {
        if (index >= 0 && index < size()) {
            return this.elements[(this.head + index) & this.capacityBitmask];
        }
        CollectionPlatformUtils collectionPlatformUtils = CollectionPlatformUtils.INSTANCE;
        throw new ArrayIndexOutOfBoundsException();
    }

    public final int size() {
        return (this.tail - this.head) & this.capacityBitmask;
    }

    public final boolean isEmpty() {
        return this.head == this.tail;
    }
}
