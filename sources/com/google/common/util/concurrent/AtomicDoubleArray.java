package com.google.common.util.concurrent;

import com.google.common.primitives.ImmutableLongArray;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLongArray;

@ElementTypesAreNonnullByDefault
public class AtomicDoubleArray implements Serializable {
    private static final long serialVersionUID = 0;
    private transient AtomicLongArray longs;

    public AtomicDoubleArray(int length) {
        this.longs = new AtomicLongArray(length);
    }

    public AtomicDoubleArray(double[] array) {
        int len = array.length;
        long[] longArray = new long[len];
        for (int i = 0; i < len; i++) {
            longArray[i] = Double.doubleToRawLongBits(array[i]);
        }
        this.longs = new AtomicLongArray(longArray);
    }

    public final int length() {
        return this.longs.length();
    }

    public final double get(int i) {
        return Double.longBitsToDouble(this.longs.get(i));
    }

    public final void set(int i, double newValue) {
        this.longs.set(i, Double.doubleToRawLongBits(newValue));
    }

    public final void lazySet(int i, double newValue) {
        this.longs.lazySet(i, Double.doubleToRawLongBits(newValue));
    }

    public final double getAndSet(int i, double newValue) {
        return Double.longBitsToDouble(this.longs.getAndSet(i, Double.doubleToRawLongBits(newValue)));
    }

    public final boolean compareAndSet(int i, double expect, double update) {
        return this.longs.compareAndSet(i, Double.doubleToRawLongBits(expect), Double.doubleToRawLongBits(update));
    }

    public final boolean weakCompareAndSet(int i, double expect, double update) {
        return this.longs.weakCompareAndSet(i, Double.doubleToRawLongBits(expect), Double.doubleToRawLongBits(update));
    }

    public final double getAndAdd(int i, double delta) {
        while (true) {
            long current = this.longs.get(i);
            double currentVal = Double.longBitsToDouble(current);
            int i2 = i;
            if (this.longs.compareAndSet(i2, current, Double.doubleToRawLongBits(currentVal + delta)) != 0) {
                return currentVal;
            }
            i = i2;
        }
    }

    public double addAndGet(int i, double delta) {
        while (true) {
            long current = this.longs.get(i);
            double nextVal = Double.longBitsToDouble(current) + delta;
            int i2 = i;
            if (this.longs.compareAndSet(i2, current, Double.doubleToRawLongBits(nextVal)) != 0) {
                return nextVal;
            }
            i = i2;
        }
    }

    public String toString() {
        int iMax = length() - 1;
        if (iMax == -1) {
            return "[]";
        }
        StringBuilder b = new StringBuilder((iMax + 1) * 19);
        b.append('[');
        int i = 0;
        while (true) {
            b.append(Double.longBitsToDouble(this.longs.get(i)));
            if (i == iMax) {
                return b.append(']').toString();
            }
            b.append(',').append(' ');
            i++;
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        int length = length();
        s.writeInt(length);
        for (int i = 0; i < length; i++) {
            s.writeDouble(get(i));
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        int length = s.readInt();
        ImmutableLongArray.Builder builder = ImmutableLongArray.builder();
        for (int i = 0; i < length; i++) {
            builder.add(Double.doubleToRawLongBits(s.readDouble()));
        }
        this.longs = new AtomicLongArray(builder.build().toArray());
    }
}
