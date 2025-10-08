package androidx.graphics.shapes;

import androidx.collection.FloatList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0001H\u0000\u001a \u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u0001H\u0000\u001a\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0003H\u0000¨\u0006\u000e"}, d2 = {"linearMap", "", "xValues", "Landroidx/collection/FloatList;", "yValues", "x", "progressInRange", "", "progress", "progressFrom", "progressTo", "validateProgress", "", "p", "graphics-shapes_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* compiled from: FloatMapping.kt */
public final class FloatMappingKt {
    public static final boolean progressInRange(float progress, float progressFrom, float progressTo) {
        if (progressTo >= progressFrom) {
            if (progressFrom > progress || progress > progressTo) {
                return false;
            }
            return true;
        } else if (progress >= progressFrom || progress <= progressTo) {
            return true;
        } else {
            return false;
        }
    }

    public static final float linearMap(FloatList xValues, FloatList yValues, float x) {
        Intrinsics.checkNotNullParameter(xValues, "xValues");
        Intrinsics.checkNotNullParameter(yValues, "yValues");
        if (0.0f <= x && x <= 1.0f) {
            Iterator it = RangesKt.until(0, xValues._size).iterator();
            while (it.hasNext()) {
                int segmentStartIndex = ((IntIterator) it).nextInt();
                int it2 = segmentStartIndex;
                if (progressInRange(x, xValues.get(it2), xValues.get((it2 + 1) % xValues.getSize())) != 0) {
                    int segmentEndIndex = (segmentStartIndex + 1) % xValues.getSize();
                    float it3 = Utils.positiveModulo(xValues.get(segmentEndIndex) - xValues.get(segmentStartIndex), 1.0f);
                    return Utils.positiveModulo(yValues.get(segmentStartIndex) + (Utils.positiveModulo(yValues.get(segmentEndIndex) - yValues.get(segmentStartIndex), 1.0f) * (it3 < 0.001f ? 0.5f : Utils.positiveModulo(x - xValues.get(segmentStartIndex), 1.0f) / it3)), 1.0f);
                }
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        throw new IllegalArgumentException(("Invalid progress: " + x).toString());
    }

    public static final void validateProgress(FloatList p) {
        int count$iv;
        FloatList floatList = p;
        Intrinsics.checkNotNullParameter(floatList, "p");
        boolean z = true;
        Boolean bool = true;
        FloatList this_$iv$iv = p;
        float[] content$iv$iv = this_$iv$iv.content;
        int i$iv$iv = 0;
        int i = this_$iv$iv._size;
        while (true) {
            boolean z2 = false;
            if (i$iv$iv >= i) {
                break;
            }
            float curr = content$iv$iv[i$iv$iv];
            if (bool.booleanValue()) {
                if (0.0f <= curr && curr <= 1.0f) {
                    z2 = true;
                }
            }
            bool = Boolean.valueOf(z2);
            i$iv$iv++;
        }
        if (bool.booleanValue()) {
            Iterable $this$count$iv = RangesKt.until(1, floatList.getSize());
            if (!($this$count$iv instanceof Collection) || !((Collection) $this$count$iv).isEmpty()) {
                count$iv = 0;
                Iterator it = $this$count$iv.iterator();
                while (it.hasNext()) {
                    int it2 = ((IntIterator) it).nextInt();
                    if ((floatList.get(it2) < floatList.get(it2 + -1) ? 1 : 0) != 0 && (count$iv = count$iv + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            } else {
                count$iv = 0;
            }
            if (count$iv > 1) {
                z = false;
            }
            if (!z) {
                throw new IllegalArgumentException(("FloatMapping - Progress wraps more than once: " + FloatList.joinToString$default(floatList, (CharSequence) null, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, 31, (Object) null)).toString());
            }
            return;
        }
        throw new IllegalArgumentException(("FloatMapping - Progress outside of range: " + FloatList.joinToString$default(p, (CharSequence) null, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, 31, (Object) null)).toString());
    }
}
