package androidx.collection;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0006\u0010\u0002\u001a\u00020\u0001\u001a\u0006\u0010\u0003\u001a\u00020\u0001\u001a\u000e\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0016\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005\u001a\u001e\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005\u001a\u0012\u0010\u0003\u001a\u00020\u00012\n\u0010\b\u001a\u00020\t\"\u00020\u0005\u001a\t\u0010\n\u001a\u00020\u000bH\b\u001a\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005\u001a\u001e\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005\u001a\u0015\u0010\n\u001a\u00020\u000b2\n\u0010\b\u001a\u00020\t\"\u00020\u0005H\b\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"EmptyFloatList", "Landroidx/collection/FloatList;", "emptyFloatList", "floatListOf", "element1", "", "element2", "element3", "elements", "", "mutableFloatListOf", "Landroidx/collection/MutableFloatList;", "collection"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* compiled from: FloatList.kt */
public final class FloatListKt {
    private static final FloatList EmptyFloatList = new MutableFloatList(0);

    public static final FloatList emptyFloatList() {
        return EmptyFloatList;
    }

    public static final FloatList floatListOf() {
        return EmptyFloatList;
    }

    public static final FloatList floatListOf(float element1) {
        return mutableFloatListOf(element1);
    }

    public static final FloatList floatListOf(float element1, float element2) {
        return mutableFloatListOf(element1, element2);
    }

    public static final FloatList floatListOf(float element1, float element2, float element3) {
        return mutableFloatListOf(element1, element2, element3);
    }

    public static final FloatList floatListOf(float... elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        MutableFloatList $this$floatListOf_u24lambda_u240 = new MutableFloatList(elements.length);
        $this$floatListOf_u24lambda_u240.plusAssign(elements);
        return $this$floatListOf_u24lambda_u240;
    }

    public static final MutableFloatList mutableFloatListOf() {
        return new MutableFloatList(0, 1, (DefaultConstructorMarker) null);
    }

    public static final MutableFloatList mutableFloatListOf(float element1) {
        MutableFloatList list = new MutableFloatList(1);
        list.add(element1);
        return list;
    }

    public static final MutableFloatList mutableFloatListOf(float element1, float element2) {
        MutableFloatList list = new MutableFloatList(2);
        MutableFloatList this_$iv = list;
        this_$iv.add(element1);
        this_$iv.add(element2);
        return list;
    }

    public static final MutableFloatList mutableFloatListOf(float element1, float element2, float element3) {
        MutableFloatList list = new MutableFloatList(3);
        MutableFloatList this_$iv = list;
        this_$iv.add(element1);
        this_$iv.add(element2);
        this_$iv.add(element3);
        return list;
    }

    public static final MutableFloatList mutableFloatListOf(float... elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        MutableFloatList $this$mutableFloatListOf_u24lambda_u241 = new MutableFloatList(elements.length);
        $this$mutableFloatListOf_u24lambda_u241.plusAssign(elements);
        return $this$mutableFloatListOf_u24lambda_u241;
    }
}
