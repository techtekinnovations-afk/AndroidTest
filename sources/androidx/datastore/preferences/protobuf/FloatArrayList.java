package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.Internal;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class FloatArrayList extends AbstractProtobufList<Float> implements Internal.FloatList, RandomAccess, PrimitiveNonBoxingCollection {
    private static final FloatArrayList EMPTY_LIST = new FloatArrayList(new float[0], 0, false);
    private float[] array;
    private int size;

    public static FloatArrayList emptyList() {
        return EMPTY_LIST;
    }

    FloatArrayList() {
        this(new float[10], 0, true);
    }

    private FloatArrayList(float[] other, int size2, boolean isMutable) {
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
        if (!(o instanceof FloatArrayList)) {
            return super.equals(o);
        }
        FloatArrayList other = (FloatArrayList) o;
        if (this.size != other.size) {
            return false;
        }
        float[] arr = other.array;
        for (int i = 0; i < this.size; i++) {
            if (Float.floatToIntBits(this.array[i]) != Float.floatToIntBits(arr[i])) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int result = 1;
        for (int i = 0; i < this.size; i++) {
            result = (result * 31) + Float.floatToIntBits(this.array[i]);
        }
        return result;
    }

    public Internal.FloatList mutableCopyWithCapacity(int capacity) {
        if (capacity >= this.size) {
            return new FloatArrayList(Arrays.copyOf(this.array, capacity), this.size, true);
        }
        throw new IllegalArgumentException();
    }

    public Float get(int index) {
        return Float.valueOf(getFloat(index));
    }

    public float getFloat(int index) {
        ensureIndexInRange(index);
        return this.array[index];
    }

    public int indexOf(Object element) {
        if (!(element instanceof Float)) {
            return -1;
        }
        float unboxedElement = ((Float) element).floatValue();
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

    public Float set(int index, Float element) {
        return Float.valueOf(setFloat(index, element.floatValue()));
    }

    public float setFloat(int index, float element) {
        ensureIsMutable();
        ensureIndexInRange(index);
        float previousValue = this.array[index];
        this.array[index] = element;
        return previousValue;
    }

    public boolean add(Float element) {
        addFloat(element.floatValue());
        return true;
    }

    public void add(int index, Float element) {
        addFloat(index, element.floatValue());
    }

    public void addFloat(float element) {
        ensureIsMutable();
        if (this.size == this.array.length) {
            float[] newArray = new float[(((this.size * 3) / 2) + 1)];
            System.arraycopy(this.array, 0, newArray, 0, this.size);
            this.array = newArray;
        }
        float[] fArr = this.array;
        int i = this.size;
        this.size = i + 1;
        fArr[i] = element;
    }

    private void addFloat(int index, float element) {
        ensureIsMutable();
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(index));
        }
        if (this.size < this.array.length) {
            System.arraycopy(this.array, index, this.array, index + 1, this.size - index);
        } else {
            float[] newArray = new float[(((this.size * 3) / 2) + 1)];
            System.arraycopy(this.array, 0, newArray, 0, index);
            System.arraycopy(this.array, index, newArray, index + 1, this.size - index);
            this.array = newArray;
        }
        this.array[index] = element;
        this.size++;
        this.modCount++;
    }

    public boolean addAll(Collection<? extends Float> collection) {
        ensureIsMutable();
        Internal.checkNotNull(collection);
        if (!(collection instanceof FloatArrayList)) {
            return super.addAll(collection);
        }
        FloatArrayList list = (FloatArrayList) collection;
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

    public Float remove(int index) {
        ensureIsMutable();
        ensureIndexInRange(index);
        float value = this.array[index];
        if (index < this.size - 1) {
            System.arraycopy(this.array, index + 1, this.array, index, (this.size - index) - 1);
        }
        this.size--;
        this.modCount++;
        return Float.valueOf(value);
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
