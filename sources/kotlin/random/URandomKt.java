package kotlin.random;

import kotlin.Metadata;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;
import kotlin.ranges.ULongRange;

@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001f\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0000¢\u0006\u0004\b\u0005\u0010\u0006\u001a\u001f\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\bH\u0000¢\u0006\u0004\b\t\u0010\n\u001a\u0019\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007¢\u0006\u0002\u0010\u0010\u001a\u001b\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u0011\u001a\u00020\fH\u0007¢\u0006\u0004\b\u0012\u0010\u0013\u001a/\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u0011\u001a\u00020\f2\b\b\u0002\u0010\u0014\u001a\u00020\u000f2\b\b\u0002\u0010\u0015\u001a\u00020\u000fH\u0007¢\u0006\u0004\b\u0016\u0010\u0017\u001a\u0011\u0010\u0018\u001a\u00020\u0003*\u00020\rH\u0007¢\u0006\u0002\u0010\u0019\u001a\u001b\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0003H\u0007¢\u0006\u0004\b\u001a\u0010\u001b\u001a#\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0007¢\u0006\u0004\b\u001c\u0010\u001d\u001a\u0019\u0010\u0018\u001a\u00020\u0003*\u00020\r2\u0006\u0010\u001e\u001a\u00020\u001fH\u0007¢\u0006\u0002\u0010 \u001a\u0011\u0010!\u001a\u00020\b*\u00020\rH\u0007¢\u0006\u0002\u0010\"\u001a\u001b\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u0004\u001a\u00020\bH\u0007¢\u0006\u0004\b#\u0010$\u001a#\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\bH\u0007¢\u0006\u0004\b%\u0010&\u001a\u0019\u0010!\u001a\u00020\b*\u00020\r2\u0006\u0010\u001e\u001a\u00020'H\u0007¢\u0006\u0002\u0010(¨\u0006)"}, d2 = {"checkUIntRangeBounds", "", "from", "Lkotlin/UInt;", "until", "checkUIntRangeBounds-J1ME1BU", "(II)V", "checkULongRangeBounds", "Lkotlin/ULong;", "checkULongRangeBounds-eb3DHEI", "(JJ)V", "nextUBytes", "Lkotlin/UByteArray;", "Lkotlin/random/Random;", "size", "", "(Lkotlin/random/Random;I)[B", "array", "nextUBytes-EVgfTAA", "(Lkotlin/random/Random;[B)[B", "fromIndex", "toIndex", "nextUBytes-Wvrt4B4", "(Lkotlin/random/Random;[BII)[B", "nextUInt", "(Lkotlin/random/Random;)I", "nextUInt-qCasIEU", "(Lkotlin/random/Random;I)I", "nextUInt-a8DCA5k", "(Lkotlin/random/Random;II)I", "range", "Lkotlin/ranges/UIntRange;", "(Lkotlin/random/Random;Lkotlin/ranges/UIntRange;)I", "nextULong", "(Lkotlin/random/Random;)J", "nextULong-V1Xi4fY", "(Lkotlin/random/Random;J)J", "nextULong-jmpaW-c", "(Lkotlin/random/Random;JJ)J", "Lkotlin/ranges/ULongRange;", "(Lkotlin/random/Random;Lkotlin/ranges/ULongRange;)J", "kotlin-stdlib"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* compiled from: URandom.kt */
public final class URandomKt {
    public static final int nextUInt(Random $this$nextUInt) {
        Intrinsics.checkNotNullParameter($this$nextUInt, "<this>");
        return UInt.m101constructorimpl($this$nextUInt.nextInt());
    }

    /* renamed from: nextUInt-qCasIEU  reason: not valid java name */
    public static final int m1222nextUIntqCasIEU(Random $this$nextUInt_u2dqCasIEU, int until) {
        Intrinsics.checkNotNullParameter($this$nextUInt_u2dqCasIEU, "$this$nextUInt");
        return m1221nextUInta8DCA5k($this$nextUInt_u2dqCasIEU, 0, until);
    }

    /* renamed from: nextUInt-a8DCA5k  reason: not valid java name */
    public static final int m1221nextUInta8DCA5k(Random $this$nextUInt_u2da8DCA5k, int from, int until) {
        Intrinsics.checkNotNullParameter($this$nextUInt_u2da8DCA5k, "$this$nextUInt");
        m1216checkUIntRangeBoundsJ1ME1BU(from, until);
        return UInt.m101constructorimpl(Integer.MIN_VALUE ^ $this$nextUInt_u2da8DCA5k.nextInt(from ^ Integer.MIN_VALUE, until ^ Integer.MIN_VALUE));
    }

    public static final int nextUInt(Random $this$nextUInt, UIntRange range) {
        Intrinsics.checkNotNullParameter($this$nextUInt, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot get random in empty range: " + range);
        } else if (Integer.compare(range.m1226getLastpVg5ArA() ^ Integer.MIN_VALUE, -1 ^ Integer.MIN_VALUE) < 0) {
            return m1221nextUInta8DCA5k($this$nextUInt, range.m1225getFirstpVg5ArA(), UInt.m101constructorimpl(range.m1226getLastpVg5ArA() + 1));
        } else {
            if (Integer.compare(range.m1225getFirstpVg5ArA() ^ Integer.MIN_VALUE, 0 ^ Integer.MIN_VALUE) > 0) {
                return UInt.m101constructorimpl(m1221nextUInta8DCA5k($this$nextUInt, UInt.m101constructorimpl(range.m1225getFirstpVg5ArA() - 1), range.m1226getLastpVg5ArA()) + 1);
            }
            return nextUInt($this$nextUInt);
        }
    }

    public static final long nextULong(Random $this$nextULong) {
        Intrinsics.checkNotNullParameter($this$nextULong, "<this>");
        return ULong.m180constructorimpl($this$nextULong.nextLong());
    }

    /* renamed from: nextULong-V1Xi4fY  reason: not valid java name */
    public static final long m1223nextULongV1Xi4fY(Random $this$nextULong_u2dV1Xi4fY, long until) {
        Intrinsics.checkNotNullParameter($this$nextULong_u2dV1Xi4fY, "$this$nextULong");
        return m1224nextULongjmpaWc($this$nextULong_u2dV1Xi4fY, 0, until);
    }

    /* renamed from: nextULong-jmpaW-c  reason: not valid java name */
    public static final long m1224nextULongjmpaWc(Random $this$nextULong_u2djmpaW_u2dc, long from, long until) {
        Intrinsics.checkNotNullParameter($this$nextULong_u2djmpaW_u2dc, "$this$nextULong");
        m1217checkULongRangeBoundseb3DHEI(from, until);
        return ULong.m180constructorimpl(Long.MIN_VALUE ^ $this$nextULong_u2djmpaW_u2dc.nextLong(from ^ Long.MIN_VALUE, until ^ Long.MIN_VALUE));
    }

    public static final long nextULong(Random $this$nextULong, ULongRange range) {
        Intrinsics.checkNotNullParameter($this$nextULong, "<this>");
        Intrinsics.checkNotNullParameter(range, "range");
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot get random in empty range: " + range);
        } else if (Long.compare(range.m1235getLastsVKNKU() ^ Long.MIN_VALUE, -1 ^ Long.MIN_VALUE) < 0) {
            return m1224nextULongjmpaWc($this$nextULong, range.m1234getFirstsVKNKU(), ULong.m180constructorimpl(range.m1235getLastsVKNKU() + ULong.m180constructorimpl(((long) 1) & 4294967295L)));
        } else {
            if (Long.compare(range.m1234getFirstsVKNKU() ^ Long.MIN_VALUE, 0 ^ Long.MIN_VALUE) <= 0) {
                return nextULong($this$nextULong);
            }
            long j = ((long) 1) & 4294967295L;
            return ULong.m180constructorimpl(m1224nextULongjmpaWc($this$nextULong, ULong.m180constructorimpl(range.m1234getFirstsVKNKU() - ULong.m180constructorimpl(j)), range.m1235getLastsVKNKU()) + ULong.m180constructorimpl(j));
        }
    }

    /* renamed from: nextUBytes-EVgfTAA  reason: not valid java name */
    public static final byte[] m1218nextUBytesEVgfTAA(Random $this$nextUBytes_u2dEVgfTAA, byte[] array) {
        Intrinsics.checkNotNullParameter($this$nextUBytes_u2dEVgfTAA, "$this$nextUBytes");
        Intrinsics.checkNotNullParameter(array, "array");
        $this$nextUBytes_u2dEVgfTAA.nextBytes(array);
        return array;
    }

    public static final byte[] nextUBytes(Random $this$nextUBytes, int size) {
        Intrinsics.checkNotNullParameter($this$nextUBytes, "<this>");
        return UByteArray.m77constructorimpl($this$nextUBytes.nextBytes(size));
    }

    /* renamed from: nextUBytes-Wvrt4B4$default  reason: not valid java name */
    public static /* synthetic */ byte[] m1220nextUBytesWvrt4B4$default(Random random, byte[] bArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = UByteArray.m83getSizeimpl(bArr);
        }
        return m1219nextUBytesWvrt4B4(random, bArr, i, i2);
    }

    /* renamed from: nextUBytes-Wvrt4B4  reason: not valid java name */
    public static final byte[] m1219nextUBytesWvrt4B4(Random $this$nextUBytes_u2dWvrt4B4, byte[] array, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter($this$nextUBytes_u2dWvrt4B4, "$this$nextUBytes");
        Intrinsics.checkNotNullParameter(array, "array");
        $this$nextUBytes_u2dWvrt4B4.nextBytes(array, fromIndex, toIndex);
        return array;
    }

    /* renamed from: checkUIntRangeBounds-J1ME1BU  reason: not valid java name */
    public static final void m1216checkUIntRangeBoundsJ1ME1BU(int from, int until) {
        if (!(Integer.compare(until ^ Integer.MIN_VALUE, from ^ Integer.MIN_VALUE) > 0)) {
            throw new IllegalArgumentException(RandomKt.boundsErrorMessage(UInt.m95boximpl(from), UInt.m95boximpl(until)).toString());
        }
    }

    /* renamed from: checkULongRangeBounds-eb3DHEI  reason: not valid java name */
    public static final void m1217checkULongRangeBoundseb3DHEI(long from, long until) {
        if (!(Long.compare(until ^ Long.MIN_VALUE, from ^ Long.MIN_VALUE) > 0)) {
            throw new IllegalArgumentException(RandomKt.boundsErrorMessage(ULong.m174boximpl(from), ULong.m174boximpl(until)).toString());
        }
    }
}
