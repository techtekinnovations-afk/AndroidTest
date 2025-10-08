package com.google.protobuf;

import com.google.protobuf.Internal;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class IntArrayList extends AbstractProtobufList<Integer> implements Internal.IntList, RandomAccess, PrimitiveNonBoxingCollection {
    private static final IntArrayList EMPTY_LIST = new IntArrayList(new int[0], 0, false);
    private int[] array;
    private int size;

    public static IntArrayList emptyList() {
        return EMPTY_LIST;
    }

    IntArrayList() {
        this(new int[10], 0, true);
    }

    private IntArrayList(int[] other, int size2, boolean isMutable) {
        super(isMutable);
        this.array = other;
        this.size = size2;
    }

    /* access modifiers changed from: protected */
    public void removeRange(int fromIndex, int toIndex) {
        ensureIsMutable();
        if (toIndex >= fromIndex) {
            System.arraycopy(this.array, toIndex, this.array, fromIndex, this.size - toIndex);
            this.size -= toIndex - fromIndex;
            this.modCount++;
            return;
        }
        throw new IndexOutOfBoundsException("toIndex < fromIndex");
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntArrayList)) {
            return super.equals(o);
        }
        IntArrayList other = (IntArrayList) o;
        if (this.size != other.size) {
            return false;
        }
        int[] arr = other.array;
        for (int i = 0; i < this.size; i++) {
            if (this.array[i] != arr[i]) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int result = 1;
        for (int i = 0; i < this.size; i++) {
            result = (result * 31) + this.array[i];
        }
        return result;
    }

    public Internal.IntList mutableCopyWithCapacity(int capacity) {
        if (capacity >= this.size) {
            return new IntArrayList(Arrays.copyOf(this.array, capacity), this.size, true);
        }
        throw new IllegalArgumentException();
    }

    public Integer get(int index) {
        return Integer.valueOf(getInt(index));
    }

    public int getInt(int index) {
        ensureIndexInRange(index);
        return this.array[index];
    }

    public int indexOf(Object element) {
        if (!(element instanceof Integer)) {
            return -1;
        }
        int unboxedElement = ((Integer) element).intValue();
        int numElems = size();
        for (int i = 0; i < numElems; i++) {
            if (this.array[i] == unboxedElement) {
                return i;
            }
        }
        return -1;
    }

    public boolean contains(Object element) {
        return indexOf(element) != -1;
    }

    public int size() {
        return this.size;
    }

    public Integer set(int index, Integer element) {
        return Integer.valueOf(setInt(index, element.intValue()));
    }

    public int setInt(int index, int element) {
        ensureIsMutable();
        ensureIndexInRange(index);
        int previousValue = this.array[index];
        this.array[index] = element;
        return previousValue;
    }

    public boolean add(Integer element) {
        addInt(element.intValue());
        return true;
    }

    public void add(int index, Integer element) {
        addInt(index, element.intValue());
    }

    public void addInt(int element) {
        ensureIsMutable();
        if (this.size == this.array.length) {
            int[] newArray = new int[(((this.size * 3) / 2) + 1)];
            System.arraycopy(this.array, 0, newArray, 0, this.size);
            this.array = newArray;
        }
        int[] iArr = this.array;
        int i = this.size;
        this.size = i + 1;
        iArr[i] = element;
    }

    private void addInt(int index, int element) {
        ensureIsMutable();
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(index));
        }
        if (this.size < this.array.length) {
            System.arraycopy(this.array, index, this.array, index + 1, this.size - index);
        } else {
            int[] newArray = new int[(((this.size * 3) / 2) + 1)];
            System.arraycopy(this.array, 0, newArray, 0, index);
            System.arraycopy(this.array, index, newArray, index + 1, this.size - index);
            this.array = newArray;
        }
        this.array[index] = element;
        this.size++;
        this.modCount++;
    }

    public boolean addAll(Collection<? extends Integer> collection) {
        ensureIsMutable();
        Internal.checkNotNull(collection);
        if (!(collection instanceof IntArrayList)) {
            return super.addAll(collection);
        }
        IntArrayList list = (IntArrayList) collection;
        if (list.size == 0) {
            return false;
        }
        if (Integer.MAX_VALUE - this.size >= list.size) {
            int newSize = this.size + list.size;
            if (newSize > this.array.length) {
                this.array = Arrays.copyOf(this.array, newSize);
            }
            System.arraycopy(list.array, 0, this.array, this.size, list.size);
            this.size = newSize;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public Integer remove(int index) {
        ensureIsMutable();
        ensureIndexInRange(index);
        int value = this.array[index];
        if (index < this.size - 1) {
            System.arraycopy(this.array, index + 1, this.array, index, (this.size - index) - 1);
        }
        this.size--;
        this.modCount++;
        return Integer.valueOf(value);
    }

    private void ensureIndexInRange(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(index));
        }
    }

    private String makeOutOfBoundsExceptionMessage(int index) {
        return "Index:" + index + ", Size:" + this.size;
    }
}
