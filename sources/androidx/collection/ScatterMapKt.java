package androidx.collection;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.scheduling.WorkQueueKt;

@Metadata(d1 = {"\u0000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0001\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u001a\u001e\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\u00190\u0017\"\u0004\b\u0000\u0010\u0018\"\u0004\b\u0001\u0010\u0019\u001a\u001d\u0010\u001a\u001a\u00060\u0001j\u0002`\u001b2\u0006\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\bH\b\u001a\u0011\u0010\u001e\u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\bH\b\u001a\u0011\u0010 \u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\bH\b\u001a\u0013\u0010\u001f\u001a\u00020\b2\b\u0010!\u001a\u0004\u0018\u00010\u0010H\b\u001a\u0019\u0010\"\u001a\u00020#2\u0006\u0010\u001c\u001a\u00020\r2\u0006\u0010$\u001a\u00020\bH\b\u001a\u0019\u0010%\u001a\u00020#2\u0006\u0010\u001c\u001a\u00020\r2\u0006\u0010$\u001a\u00020\bH\b\u001a\u0011\u0010&\u001a\u00020#2\u0006\u0010'\u001a\u00020\u0001H\b\u001a\u0019\u0010&\u001a\u00020#2\u0006\u0010\u001c\u001a\u00020\r2\u0006\u0010$\u001a\u00020\bH\b\u001a\u0010\u0010(\u001a\u00020\b2\u0006\u0010)\u001a\u00020\bH\u0000\u001a\u001e\u0010*\u001a\u000e\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\u00190\u000f\"\u0004\b\u0000\u0010\u0018\"\u0004\b\u0001\u0010\u0019\u001aO\u0010*\u001a\u000e\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\u00190\u000f\"\u0004\b\u0000\u0010\u0018\"\u0004\b\u0001\u0010\u00192*\u0010+\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\u00190-0,\"\u000e\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\u00190-¢\u0006\u0002\u0010.\u001a\u0010\u0010/\u001a\u00020\b2\u0006\u0010)\u001a\u00020\bH\u0000\u001a\u0010\u00100\u001a\u00020\b2\u0006\u00101\u001a\u00020\bH\u0000\u001a\u0019\u00102\u001a\u00020\u00012\u0006\u00103\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\bH\b\u001a\u0010\u00104\u001a\u00020\b2\u0006\u0010)\u001a\u00020\bH\u0000\u001a!\u00105\u001a\u0002062\u0006\u00103\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\b2\u0006\u0010'\u001a\u00020\u0001H\b\u001a\u0011\u00107\u001a\u00020\b*\u00060\u0001j\u0002`8H\b\u001a\u0011\u00109\u001a\u00020#*\u00060\u0001j\u0002`8H\b\u001a\u0011\u0010:\u001a\u00020\b*\u00060\u0001j\u0002`;H\b\u001a\u0015\u0010<\u001a\u00060\u0001j\u0002`8*\u00060\u0001j\u0002`\u001bH\b\u001a\u0015\u0010=\u001a\u00060\u0001j\u0002`8*\u00060\u0001j\u0002`\u001bH\b\u001a\u001d\u0010>\u001a\u00060\u0001j\u0002`8*\u00060\u0001j\u0002`\u001b2\u0006\u0010?\u001a\u00020\bH\b\u001a\u0011\u0010@\u001a\u00020\u0001*\u00060\u0001j\u0002`8H\b\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u0016\u0010\u0002\u001a\u00020\u00018\u0000XT¢\u0006\b\n\u0000\u0012\u0004\b\u0003\u0010\u0004\"\u0016\u0010\u0005\u001a\u00020\u00018\u0000XT¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0004\"\u000e\u0010\u0007\u001a\u00020\bXT¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\bXT¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u000b\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u0010\u0010\f\u001a\u00020\r8\u0000X\u0004¢\u0006\u0002\n\u0000\"\u001c\u0010\u000e\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0004\u0012\u00020\u00110\u000fX\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0012\u001a\u00020\bXT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0013\u001a\u00020\bXT¢\u0006\u0002\n\u0000\"\u0016\u0010\u0014\u001a\u00020\u00018\u0000XT¢\u0006\b\n\u0000\u0012\u0004\b\u0015\u0010\u0004*\f\b\u0000\u0010A\"\u00020\u00012\u00020\u0001*\f\b\u0000\u0010B\"\u00020\u00012\u00020\u0001*\f\b\u0000\u0010C\"\u00020\u00012\u00020\u0001¨\u0006D"}, d2 = {"AllEmpty", "", "BitmaskLsb", "getBitmaskLsb$annotations", "()V", "BitmaskMsb", "getBitmaskMsb$annotations", "ClonedMetadataCount", "", "DefaultScatterCapacity", "Deleted", "Empty", "EmptyGroup", "", "EmptyScatterMap", "Landroidx/collection/MutableScatterMap;", "", "", "GroupWidth", "MurmurHashC1", "Sentinel", "getSentinel$annotations", "emptyScatterMap", "Landroidx/collection/ScatterMap;", "K", "V", "group", "Landroidx/collection/Group;", "metadata", "offset", "h1", "hash", "h2", "k", "isDeleted", "", "index", "isEmpty", "isFull", "value", "loadedCapacity", "capacity", "mutableScatterMapOf", "pairs", "", "Lkotlin/Pair;", "([Lkotlin/Pair;)Landroidx/collection/MutableScatterMap;", "nextCapacity", "normalizeCapacity", "n", "readRawMetadata", "data", "unloadedCapacity", "writeRawMetadata", "", "get", "Landroidx/collection/Bitmask;", "hasNext", "lowestBitSet", "Landroidx/collection/StaticBitmask;", "maskEmpty", "maskEmptyOrDeleted", "match", "m", "next", "Bitmask", "Group", "StaticBitmask", "collection"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* compiled from: ScatterMap.kt */
public final class ScatterMapKt {
    public static final long AllEmpty = -9187201950435737472L;
    public static final long BitmaskLsb = 72340172838076673L;
    public static final long BitmaskMsb = -9187201950435737472L;
    public static final int ClonedMetadataCount = 7;
    public static final int DefaultScatterCapacity = 6;
    public static final long Deleted = 254;
    public static final long Empty = 128;
    public static final long[] EmptyGroup = {-9187201950435737345L, -1};
    private static final MutableScatterMap EmptyScatterMap = new MutableScatterMap(0);
    public static final int GroupWidth = 8;
    public static final int MurmurHashC1 = -862048943;
    public static final long Sentinel = 255;

    public static /* synthetic */ void getBitmaskLsb$annotations() {
    }

    public static /* synthetic */ void getBitmaskMsb$annotations() {
    }

    public static /* synthetic */ void getSentinel$annotations() {
    }

    public static final <K, V> ScatterMap<K, V> emptyScatterMap() {
        MutableScatterMap mutableScatterMap = EmptyScatterMap;
        Intrinsics.checkNotNull(mutableScatterMap, "null cannot be cast to non-null type androidx.collection.ScatterMap<K of androidx.collection.ScatterMapKt.emptyScatterMap, V of androidx.collection.ScatterMapKt.emptyScatterMap>");
        return mutableScatterMap;
    }

    public static final <K, V> MutableScatterMap<K, V> mutableScatterMapOf() {
        return new MutableScatterMap<>(0, 1, (DefaultConstructorMarker) null);
    }

    public static final <K, V> MutableScatterMap<K, V> mutableScatterMapOf(Pair<? extends K, ? extends V>... pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        MutableScatterMap $this$mutableScatterMapOf_u24lambda_u240 = new MutableScatterMap(pairs.length);
        $this$mutableScatterMapOf_u24lambda_u240.putAll(pairs);
        return $this$mutableScatterMapOf_u24lambda_u240;
    }

    public static final int hash(Object k) {
        int hash = (k != null ? k.hashCode() : 0) * MurmurHashC1;
        return (hash << 16) ^ hash;
    }

    public static final int h1(int hash) {
        return hash >>> 7;
    }

    public static final int h2(int hash) {
        return hash & WorkQueueKt.MASK;
    }

    public static final int nextCapacity(int capacity) {
        if (capacity == 0) {
            return 6;
        }
        return (capacity * 2) + 1;
    }

    public static final int normalizeCapacity(int n) {
        if (n > 0) {
            return -1 >>> Integer.numberOfLeadingZeros(n);
        }
        return 0;
    }

    public static final int loadedCapacity(int capacity) {
        if (capacity == 7) {
            return 6;
        }
        return capacity - (capacity / 8);
    }

    public static final int unloadedCapacity(int capacity) {
        if (capacity == 7) {
            return 8;
        }
        return ((capacity - 1) / 7) + capacity;
    }

    public static final long readRawMetadata(long[] data, int offset) {
        Intrinsics.checkNotNullParameter(data, "data");
        return (data[offset >> 3] >> ((offset & 7) << 3)) & 255;
    }

    public static final void writeRawMetadata(long[] data, int offset, long value) {
        Intrinsics.checkNotNullParameter(data, "data");
        int i = offset >> 3;
        int b = (offset & 7) << 3;
        data[i] = (data[i] & (~(255 << b))) | (value << b);
    }

    public static final boolean isEmpty(long[] metadata, int index) {
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        return ((metadata[index >> 3] >> ((index & 7) << 3)) & 255) == 128;
    }

    public static final boolean isDeleted(long[] metadata, int index) {
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        return ((metadata[index >> 3] >> ((index & 7) << 3)) & 255) == 254;
    }

    public static final boolean isFull(long[] metadata, int index) {
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        return ((metadata[index >> 3] >> ((index & 7) << 3)) & 255) < 128;
    }

    public static final boolean isFull(long value) {
        return value < 128;
    }

    public static final int lowestBitSet(long $this$lowestBitSet) {
        return Long.numberOfTrailingZeros($this$lowestBitSet) >> 3;
    }

    public static final int get(long $this$get) {
        return Long.numberOfTrailingZeros($this$get) >> 3;
    }

    public static final long next(long $this$next) {
        return ($this$next - 1) & $this$next;
    }

    public static final boolean hasNext(long $this$hasNext) {
        return $this$hasNext != 0;
    }

    public static final long group(long[] metadata, int offset) {
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        int i = offset >> 3;
        int b = (offset & 7) << 3;
        return (metadata[i] >>> b) | ((metadata[i + 1] << (64 - b)) & ((-((long) b)) >> 63));
    }

    public static final long match(long $this$match, int m) {
        long x = (((long) m) * BitmaskLsb) ^ $this$match;
        return (x - BitmaskLsb) & (~x) & -9187201950435737472L;
    }

    public static final long maskEmpty(long $this$maskEmpty) {
        return ((~$this$maskEmpty) << 6) & $this$maskEmpty & -9187201950435737472L;
    }

    public static final long maskEmptyOrDeleted(long $this$maskEmptyOrDeleted) {
        return ((~$this$maskEmptyOrDeleted) << 7) & $this$maskEmptyOrDeleted & -9187201950435737472L;
    }
}
