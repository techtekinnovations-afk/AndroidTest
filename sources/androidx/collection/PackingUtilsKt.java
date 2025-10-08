package androidx.collection;

import kotlin.Metadata;

@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u001a\u0019\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\b\u001a\u0019\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00062\u0006\u0010\u0004\u001a\u00020\u0006H\b¨\u0006\u0007"}, d2 = {"packFloats", "", "val1", "", "val2", "packInts", "", "collection"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* compiled from: PackingUtils.kt */
public final class PackingUtilsKt {
    public static final long packFloats(float val1, float val2) {
        return (((long) Float.floatToRawIntBits(val1)) << 32) | (4294967295L & ((long) Float.floatToRawIntBits(val2)));
    }

    public static final long packInts(int val1, int val2) {
        return (((long) val1) << 32) | (((long) val2) & 4294967295L);
    }
}
