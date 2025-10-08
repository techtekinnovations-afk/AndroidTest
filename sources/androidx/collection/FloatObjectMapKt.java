package androidx.collection;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0015\u001a\u0012\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0000\u0010\u0005\u001a\u0012\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0000\u0010\u0005\u001a'\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u0002H\u0005¢\u0006\u0002\u0010\n\u001a7\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u0002H\u00052\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u0002H\u0005¢\u0006\u0002\u0010\r\u001aG\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u0002H\u00052\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u0002H\u00052\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u0002H\u0005¢\u0006\u0002\u0010\u0010\u001aW\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u0002H\u00052\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u0002H\u00052\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u0002H\u00052\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u0002H\u0005¢\u0006\u0002\u0010\u0013\u001ag\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u0002H\u00052\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u0002H\u00052\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u0002H\u00052\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u0002H\u00052\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u0002H\u0005¢\u0006\u0002\u0010\u0016\u001a\u0012\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0001\"\u0004\b\u0000\u0010\u0005\u001a'\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0001\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u0002H\u0005¢\u0006\u0002\u0010\u0018\u001a7\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0001\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u0002H\u00052\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u0002H\u0005¢\u0006\u0002\u0010\u0019\u001aG\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0001\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u0002H\u00052\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u0002H\u00052\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u0002H\u0005¢\u0006\u0002\u0010\u001a\u001aW\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0001\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u0002H\u00052\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u0002H\u00052\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u0002H\u00052\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u0002H\u0005¢\u0006\u0002\u0010\u001b\u001ag\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0001\"\u0004\b\u0000\u0010\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u0002H\u00052\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u0002H\u00052\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u0002H\u00052\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u0002H\u00052\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u0002H\u0005¢\u0006\u0002\u0010\u001c\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"EmptyFloatObjectMap", "Landroidx/collection/MutableFloatObjectMap;", "", "emptyFloatObjectMap", "Landroidx/collection/FloatObjectMap;", "V", "floatObjectMapOf", "key1", "", "value1", "(FLjava/lang/Object;)Landroidx/collection/FloatObjectMap;", "key2", "value2", "(FLjava/lang/Object;FLjava/lang/Object;)Landroidx/collection/FloatObjectMap;", "key3", "value3", "(FLjava/lang/Object;FLjava/lang/Object;FLjava/lang/Object;)Landroidx/collection/FloatObjectMap;", "key4", "value4", "(FLjava/lang/Object;FLjava/lang/Object;FLjava/lang/Object;FLjava/lang/Object;)Landroidx/collection/FloatObjectMap;", "key5", "value5", "(FLjava/lang/Object;FLjava/lang/Object;FLjava/lang/Object;FLjava/lang/Object;FLjava/lang/Object;)Landroidx/collection/FloatObjectMap;", "mutableFloatObjectMapOf", "(FLjava/lang/Object;)Landroidx/collection/MutableFloatObjectMap;", "(FLjava/lang/Object;FLjava/lang/Object;)Landroidx/collection/MutableFloatObjectMap;", "(FLjava/lang/Object;FLjava/lang/Object;FLjava/lang/Object;)Landroidx/collection/MutableFloatObjectMap;", "(FLjava/lang/Object;FLjava/lang/Object;FLjava/lang/Object;FLjava/lang/Object;)Landroidx/collection/MutableFloatObjectMap;", "(FLjava/lang/Object;FLjava/lang/Object;FLjava/lang/Object;FLjava/lang/Object;FLjava/lang/Object;)Landroidx/collection/MutableFloatObjectMap;", "collection"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* compiled from: FloatObjectMap.kt */
public final class FloatObjectMapKt {
    private static final MutableFloatObjectMap EmptyFloatObjectMap = new MutableFloatObjectMap(0);

    public static final <V> FloatObjectMap<V> emptyFloatObjectMap() {
        MutableFloatObjectMap mutableFloatObjectMap = EmptyFloatObjectMap;
        Intrinsics.checkNotNull(mutableFloatObjectMap, "null cannot be cast to non-null type androidx.collection.FloatObjectMap<V of androidx.collection.FloatObjectMapKt.emptyFloatObjectMap>");
        return mutableFloatObjectMap;
    }

    public static final <V> FloatObjectMap<V> floatObjectMapOf() {
        MutableFloatObjectMap mutableFloatObjectMap = EmptyFloatObjectMap;
        Intrinsics.checkNotNull(mutableFloatObjectMap, "null cannot be cast to non-null type androidx.collection.FloatObjectMap<V of androidx.collection.FloatObjectMapKt.floatObjectMapOf>");
        return mutableFloatObjectMap;
    }

    public static final <V> FloatObjectMap<V> floatObjectMapOf(float key1, V value1) {
        MutableFloatObjectMap map = new MutableFloatObjectMap(0, 1, (DefaultConstructorMarker) null);
        map.set(key1, value1);
        return map;
    }

    public static final <V> FloatObjectMap<V> floatObjectMapOf(float key1, V value1, float key2, V value2) {
        MutableFloatObjectMap mutableFloatObjectMap = new MutableFloatObjectMap(0, 1, (DefaultConstructorMarker) null);
        MutableFloatObjectMap map = mutableFloatObjectMap;
        map.set(key1, value1);
        map.set(key2, value2);
        return mutableFloatObjectMap;
    }

    public static final <V> FloatObjectMap<V> floatObjectMapOf(float key1, V value1, float key2, V value2, float key3, V value3) {
        MutableFloatObjectMap mutableFloatObjectMap = new MutableFloatObjectMap(0, 1, (DefaultConstructorMarker) null);
        MutableFloatObjectMap map = mutableFloatObjectMap;
        map.set(key1, value1);
        map.set(key2, value2);
        map.set(key3, value3);
        return mutableFloatObjectMap;
    }

    public static final <V> FloatObjectMap<V> floatObjectMapOf(float key1, V value1, float key2, V value2, float key3, V value3, float key4, V value4) {
        MutableFloatObjectMap mutableFloatObjectMap = new MutableFloatObjectMap(0, 1, (DefaultConstructorMarker) null);
        MutableFloatObjectMap map = mutableFloatObjectMap;
        map.set(key1, value1);
        map.set(key2, value2);
        map.set(key3, value3);
        map.set(key4, value4);
        return mutableFloatObjectMap;
    }

    public static final <V> FloatObjectMap<V> floatObjectMapOf(float key1, V value1, float key2, V value2, float key3, V value3, float key4, V value4, float key5, V value5) {
        MutableFloatObjectMap mutableFloatObjectMap = new MutableFloatObjectMap(0, 1, (DefaultConstructorMarker) null);
        MutableFloatObjectMap map = mutableFloatObjectMap;
        map.set(key1, value1);
        map.set(key2, value2);
        map.set(key3, value3);
        map.set(key4, value4);
        map.set(key5, value5);
        return mutableFloatObjectMap;
    }

    public static final <V> MutableFloatObjectMap<V> mutableFloatObjectMapOf() {
        return new MutableFloatObjectMap<>(0, 1, (DefaultConstructorMarker) null);
    }

    public static final <V> MutableFloatObjectMap<V> mutableFloatObjectMapOf(float key1, V value1) {
        MutableFloatObjectMap map = new MutableFloatObjectMap(0, 1, (DefaultConstructorMarker) null);
        map.set(key1, value1);
        return map;
    }

    public static final <V> MutableFloatObjectMap<V> mutableFloatObjectMapOf(float key1, V value1, float key2, V value2) {
        MutableFloatObjectMap mutableFloatObjectMap = new MutableFloatObjectMap(0, 1, (DefaultConstructorMarker) null);
        MutableFloatObjectMap map = mutableFloatObjectMap;
        map.set(key1, value1);
        map.set(key2, value2);
        return mutableFloatObjectMap;
    }

    public static final <V> MutableFloatObjectMap<V> mutableFloatObjectMapOf(float key1, V value1, float key2, V value2, float key3, V value3) {
        MutableFloatObjectMap mutableFloatObjectMap = new MutableFloatObjectMap(0, 1, (DefaultConstructorMarker) null);
        MutableFloatObjectMap map = mutableFloatObjectMap;
        map.set(key1, value1);
        map.set(key2, value2);
        map.set(key3, value3);
        return mutableFloatObjectMap;
    }

    public static final <V> MutableFloatObjectMap<V> mutableFloatObjectMapOf(float key1, V value1, float key2, V value2, float key3, V value3, float key4, V value4) {
        MutableFloatObjectMap mutableFloatObjectMap = new MutableFloatObjectMap(0, 1, (DefaultConstructorMarker) null);
        MutableFloatObjectMap map = mutableFloatObjectMap;
        map.set(key1, value1);
        map.set(key2, value2);
        map.set(key3, value3);
        map.set(key4, value4);
        return mutableFloatObjectMap;
    }

    public static final <V> MutableFloatObjectMap<V> mutableFloatObjectMapOf(float key1, V value1, float key2, V value2, float key3, V value3, float key4, V value4, float key5, V value5) {
        MutableFloatObjectMap mutableFloatObjectMap = new MutableFloatObjectMap(0, 1, (DefaultConstructorMarker) null);
        MutableFloatObjectMap map = mutableFloatObjectMap;
        map.set(key1, value1);
        map.set(key2, value2);
        map.set(key3, value3);
        map.set(key4, value4);
        map.set(key5, value5);
        return mutableFloatObjectMap;
    }
}
