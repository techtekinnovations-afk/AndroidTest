package kotlin;

import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;
import kotlin.ranges.URangesKt;

@JvmInline
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0005\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b!\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b@\u0018\u0000 v2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001vB\u0011\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\f¢\u0006\u0004\b\n\u0010\u000bJ\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0000H\n¢\u0006\u0004\b\u000e\u0010\u000fJ\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0010H\n¢\u0006\u0004\b\u0011\u0010\u0012J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0013H\n¢\u0006\u0004\b\u0014\u0010\u0015J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0016H\n¢\u0006\u0004\b\u0017\u0010\u0018J\u0013\u0010\u0019\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u0005J\u0018\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\n¢\u0006\u0004\b\u001c\u0010\u000fJ\u0018\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\n¢\u0006\u0004\b\u001d\u0010\u0012J\u0018\u0010\u001b\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\n¢\u0006\u0004\b\u001e\u0010\u001fJ\u0018\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\n¢\u0006\u0004\b \u0010\u0018J\u001a\u0010!\u001a\u00020\"2\b\u0010\t\u001a\u0004\u0018\u00010#HÖ\u0003¢\u0006\u0004\b$\u0010%J\u0018\u0010&\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\b¢\u0006\u0004\b'\u0010\u000fJ\u0018\u0010&\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\b¢\u0006\u0004\b(\u0010\u0012J\u0018\u0010&\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\b¢\u0006\u0004\b)\u0010\u001fJ\u0018\u0010&\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\b¢\u0006\u0004\b*\u0010\u0018J\u0010\u0010+\u001a\u00020\rHÖ\u0001¢\u0006\u0004\b,\u0010-J\u0013\u0010.\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b/\u0010\u0005J\u0013\u00100\u001a\u00020\u0000H\bø\u0001\u0000¢\u0006\u0004\b1\u0010\u0005J\u0018\u00102\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\n¢\u0006\u0004\b3\u0010\u000fJ\u0018\u00102\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\n¢\u0006\u0004\b4\u0010\u0012J\u0018\u00102\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\n¢\u0006\u0004\b5\u0010\u001fJ\u0018\u00102\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\n¢\u0006\u0004\b6\u0010\u0018J\u0018\u00107\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\b¢\u0006\u0004\b8\u0010\u000bJ\u0018\u00107\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\b¢\u0006\u0004\b9\u0010\u0012J\u0018\u00107\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\b¢\u0006\u0004\b:\u0010\u001fJ\u0018\u00107\u001a\u00020\u00162\u0006\u0010\t\u001a\u00020\u0016H\b¢\u0006\u0004\b;\u0010<J\u0018\u0010=\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\f¢\u0006\u0004\b>\u0010\u000bJ\u0018\u0010?\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\n¢\u0006\u0004\b@\u0010\u000fJ\u0018\u0010?\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\n¢\u0006\u0004\bA\u0010\u0012J\u0018\u0010?\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\n¢\u0006\u0004\bB\u0010\u001fJ\u0018\u0010?\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\n¢\u0006\u0004\bC\u0010\u0018J\u0018\u0010D\u001a\u00020E2\u0006\u0010\t\u001a\u00020\u0000H\n¢\u0006\u0004\bF\u0010GJ\u0018\u0010H\u001a\u00020E2\u0006\u0010\t\u001a\u00020\u0000H\n¢\u0006\u0004\bI\u0010GJ\u0018\u0010J\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\n¢\u0006\u0004\bK\u0010\u000fJ\u0018\u0010J\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\n¢\u0006\u0004\bL\u0010\u0012J\u0018\u0010J\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\n¢\u0006\u0004\bM\u0010\u001fJ\u0018\u0010J\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\n¢\u0006\u0004\bN\u0010\u0018J\u0018\u0010O\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\n¢\u0006\u0004\bP\u0010\u000fJ\u0018\u0010O\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\n¢\u0006\u0004\bQ\u0010\u0012J\u0018\u0010O\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\n¢\u0006\u0004\bR\u0010\u001fJ\u0018\u0010O\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\n¢\u0006\u0004\bS\u0010\u0018J\u0010\u0010T\u001a\u00020\u0003H\b¢\u0006\u0004\bU\u0010\u0005J\u0010\u0010V\u001a\u00020WH\b¢\u0006\u0004\bX\u0010YJ\u0010\u0010Z\u001a\u00020[H\b¢\u0006\u0004\b\\\u0010]J\u0010\u0010^\u001a\u00020\rH\b¢\u0006\u0004\b_\u0010-J\u0010\u0010`\u001a\u00020aH\b¢\u0006\u0004\bb\u0010cJ\u0010\u0010d\u001a\u00020eH\b¢\u0006\u0004\bf\u0010gJ\u000f\u0010h\u001a\u00020iH\u0016¢\u0006\u0004\bj\u0010kJ\u0013\u0010l\u001a\u00020\u0000H\bø\u0001\u0000¢\u0006\u0004\bm\u0010\u0005J\u0013\u0010n\u001a\u00020\u0010H\bø\u0001\u0000¢\u0006\u0004\bo\u0010-J\u0013\u0010p\u001a\u00020\u0013H\bø\u0001\u0000¢\u0006\u0004\bq\u0010cJ\u0013\u0010r\u001a\u00020\u0016H\bø\u0001\u0000¢\u0006\u0004\bs\u0010gJ\u0018\u0010t\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\f¢\u0006\u0004\bu\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u0001\u0002\u0001\u00020\u0003\u0002\u0004\n\u0002\b!¨\u0006w"}, d2 = {"Lkotlin/UByte;", "", "data", "", "constructor-impl", "(B)B", "getData$annotations", "()V", "and", "other", "and-7apg3OU", "(BB)B", "compareTo", "", "compareTo-7apg3OU", "(BB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(BI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(BJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(BS)I", "dec", "dec-w2LRezQ", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(BJ)J", "div-xj2QHRw", "equals", "", "", "equals-impl", "(BLjava/lang/Object;)Z", "floorDiv", "floorDiv-7apg3OU", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "floorDiv-xj2QHRw", "hashCode", "hashCode-impl", "(B)I", "inc", "inc-w2LRezQ", "inv", "inv-w2LRezQ", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "mod", "mod-7apg3OU", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "mod-xj2QHRw", "(BS)S", "or", "or-7apg3OU", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-7apg3OU", "(BB)Lkotlin/ranges/UIntRange;", "rangeUntil", "rangeUntil-7apg3OU", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "toByte-impl", "toDouble", "", "toDouble-impl", "(B)D", "toFloat", "", "toFloat-impl", "(B)F", "toInt", "toInt-impl", "toLong", "", "toLong-impl", "(B)J", "toShort", "", "toShort-impl", "(B)S", "toString", "", "toString-impl", "(B)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-7apg3OU", "Companion", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* compiled from: UByte.kt */
public final class UByte implements Comparable<UByte> {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    public static final byte MAX_VALUE = -1;
    public static final byte MIN_VALUE = 0;
    public static final int SIZE_BITS = 8;
    public static final int SIZE_BYTES = 1;
    private final byte data;

    /* renamed from: box-impl  reason: not valid java name */
    public static final /* synthetic */ UByte m18boximpl(byte b) {
        return new UByte(b);
    }

    /* renamed from: constructor-impl  reason: not valid java name */
    public static byte m24constructorimpl(byte b) {
        return b;
    }

    /* renamed from: equals-impl  reason: not valid java name */
    public static boolean m30equalsimpl(byte b, Object obj) {
        return (obj instanceof UByte) && b == ((UByte) obj).m74unboximpl();
    }

    /* renamed from: equals-impl0  reason: not valid java name */
    public static final boolean m31equalsimpl0(byte b, byte b2) {
        return b == b2;
    }

    public static /* synthetic */ void getData$annotations() {
    }

    /* renamed from: hashCode-impl  reason: not valid java name */
    public static int m36hashCodeimpl(byte b) {
        return Byte.hashCode(b);
    }

    public boolean equals(Object obj) {
        return m30equalsimpl(this.data, obj);
    }

    public int hashCode() {
        return m36hashCodeimpl(this.data);
    }

    /* renamed from: unbox-impl  reason: not valid java name */
    public final /* synthetic */ byte m74unboximpl() {
        return this.data;
    }

    public /* bridge */ /* synthetic */ int compareTo(Object other) {
        return Intrinsics.compare((int) m74unboximpl() & 255, (int) ((UByte) other).m74unboximpl() & 255);
    }

    private /* synthetic */ UByte(byte data2) {
        this.data = data2;
    }

    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0013\u0010\u0003\u001a\u00020\u0004XTø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0005R\u0013\u0010\u0006\u001a\u00020\u0004XTø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bXT¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bXT¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b!¨\u0006\n"}, d2 = {"Lkotlin/UByte$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UByte;", "B", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* compiled from: UByte.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: compareTo-7apg3OU  reason: not valid java name */
    private int m19compareTo7apg3OU(byte other) {
        return Intrinsics.compare((int) m74unboximpl() & 255, (int) other & 255);
    }

    /* renamed from: compareTo-7apg3OU  reason: not valid java name */
    private static int m20compareTo7apg3OU(byte arg0, byte other) {
        return Intrinsics.compare((int) arg0 & 255, (int) other & 255);
    }

    /* renamed from: compareTo-xj2QHRw  reason: not valid java name */
    private static final int m23compareToxj2QHRw(byte arg0, short other) {
        return Intrinsics.compare((int) arg0 & 255, (int) 65535 & other);
    }

    /* renamed from: compareTo-WZ4Q5Ns  reason: not valid java name */
    private static final int m22compareToWZ4Q5Ns(byte arg0, int other) {
        return Integer.compare(UInt.m101constructorimpl(arg0 & 255) ^ Integer.MIN_VALUE, other ^ Integer.MIN_VALUE);
    }

    /* renamed from: compareTo-VKZWuLQ  reason: not valid java name */
    private static final int m21compareToVKZWuLQ(byte arg0, long other) {
        return Long.compare(ULong.m180constructorimpl(((long) arg0) & 255) ^ Long.MIN_VALUE, other ^ Long.MIN_VALUE);
    }

    /* renamed from: plus-7apg3OU  reason: not valid java name */
    private static final int m48plus7apg3OU(byte arg0, byte other) {
        return UInt.m101constructorimpl(UInt.m101constructorimpl(arg0 & 255) + UInt.m101constructorimpl(other & 255));
    }

    /* renamed from: plus-xj2QHRw  reason: not valid java name */
    private static final int m51plusxj2QHRw(byte arg0, short other) {
        return UInt.m101constructorimpl(UInt.m101constructorimpl(arg0 & 255) + UInt.m101constructorimpl(65535 & other));
    }

    /* renamed from: plus-WZ4Q5Ns  reason: not valid java name */
    private static final int m50plusWZ4Q5Ns(byte arg0, int other) {
        return UInt.m101constructorimpl(UInt.m101constructorimpl(arg0 & 255) + other);
    }

    /* renamed from: plus-VKZWuLQ  reason: not valid java name */
    private static final long m49plusVKZWuLQ(byte arg0, long other) {
        return ULong.m180constructorimpl(ULong.m180constructorimpl(((long) arg0) & 255) + other);
    }

    /* renamed from: minus-7apg3OU  reason: not valid java name */
    private static final int m39minus7apg3OU(byte arg0, byte other) {
        return UInt.m101constructorimpl(UInt.m101constructorimpl(arg0 & 255) - UInt.m101constructorimpl(other & 255));
    }

    /* renamed from: minus-xj2QHRw  reason: not valid java name */
    private static final int m42minusxj2QHRw(byte arg0, short other) {
        return UInt.m101constructorimpl(UInt.m101constructorimpl(arg0 & 255) - UInt.m101constructorimpl(65535 & other));
    }

    /* renamed from: minus-WZ4Q5Ns  reason: not valid java name */
    private static final int m41minusWZ4Q5Ns(byte arg0, int other) {
        return UInt.m101constructorimpl(UInt.m101constructorimpl(arg0 & 255) - other);
    }

    /* renamed from: minus-VKZWuLQ  reason: not valid java name */
    private static final long m40minusVKZWuLQ(byte arg0, long other) {
        return ULong.m180constructorimpl(ULong.m180constructorimpl(((long) arg0) & 255) - other);
    }

    /* renamed from: times-7apg3OU  reason: not valid java name */
    private static final int m58times7apg3OU(byte arg0, byte other) {
        return UInt.m101constructorimpl(UInt.m101constructorimpl(arg0 & 255) * UInt.m101constructorimpl(other & 255));
    }

    /* renamed from: times-xj2QHRw  reason: not valid java name */
    private static final int m61timesxj2QHRw(byte arg0, short other) {
        return UInt.m101constructorimpl(UInt.m101constructorimpl(arg0 & 255) * UInt.m101constructorimpl(65535 & other));
    }

    /* renamed from: times-WZ4Q5Ns  reason: not valid java name */
    private static final int m60timesWZ4Q5Ns(byte arg0, int other) {
        return UInt.m101constructorimpl(UInt.m101constructorimpl(arg0 & 255) * other);
    }

    /* renamed from: times-VKZWuLQ  reason: not valid java name */
    private static final long m59timesVKZWuLQ(byte arg0, long other) {
        return ULong.m180constructorimpl(ULong.m180constructorimpl(((long) arg0) & 255) * other);
    }

    /* renamed from: div-7apg3OU  reason: not valid java name */
    private static final int m26div7apg3OU(byte arg0, byte other) {
        return UByte$$ExternalSyntheticBackport0.m(UInt.m101constructorimpl(arg0 & 255), UInt.m101constructorimpl(other & 255));
    }

    /* renamed from: div-xj2QHRw  reason: not valid java name */
    private static final int m29divxj2QHRw(byte arg0, short other) {
        return UByte$$ExternalSyntheticBackport0.m(UInt.m101constructorimpl(arg0 & 255), UInt.m101constructorimpl(65535 & other));
    }

    /* renamed from: div-WZ4Q5Ns  reason: not valid java name */
    private static final int m28divWZ4Q5Ns(byte arg0, int other) {
        return UByte$$ExternalSyntheticBackport0.m(UInt.m101constructorimpl(arg0 & 255), other);
    }

    /* renamed from: div-VKZWuLQ  reason: not valid java name */
    private static final long m27divVKZWuLQ(byte arg0, long other) {
        return UByte$$ExternalSyntheticBackport3.m(ULong.m180constructorimpl(((long) arg0) & 255), other);
    }

    /* renamed from: rem-7apg3OU  reason: not valid java name */
    private static final int m54rem7apg3OU(byte arg0, byte other) {
        return UByte$$ExternalSyntheticBackport1.m(UInt.m101constructorimpl(arg0 & 255), UInt.m101constructorimpl(other & 255));
    }

    /* renamed from: rem-xj2QHRw  reason: not valid java name */
    private static final int m57remxj2QHRw(byte arg0, short other) {
        return UByte$$ExternalSyntheticBackport1.m(UInt.m101constructorimpl(arg0 & 255), UInt.m101constructorimpl(65535 & other));
    }

    /* renamed from: rem-WZ4Q5Ns  reason: not valid java name */
    private static final int m56remWZ4Q5Ns(byte arg0, int other) {
        return UByte$$ExternalSyntheticBackport1.m(UInt.m101constructorimpl(arg0 & 255), other);
    }

    /* renamed from: rem-VKZWuLQ  reason: not valid java name */
    private static final long m55remVKZWuLQ(byte arg0, long other) {
        return UByte$$ExternalSyntheticBackport2.m(ULong.m180constructorimpl(((long) arg0) & 255), other);
    }

    /* renamed from: floorDiv-7apg3OU  reason: not valid java name */
    private static final int m32floorDiv7apg3OU(byte arg0, byte other) {
        return UByte$$ExternalSyntheticBackport0.m(UInt.m101constructorimpl(arg0 & 255), UInt.m101constructorimpl(other & 255));
    }

    /* renamed from: floorDiv-xj2QHRw  reason: not valid java name */
    private static final int m35floorDivxj2QHRw(byte arg0, short other) {
        return UByte$$ExternalSyntheticBackport0.m(UInt.m101constructorimpl(arg0 & 255), UInt.m101constructorimpl(65535 & other));
    }

    /* renamed from: floorDiv-WZ4Q5Ns  reason: not valid java name */
    private static final int m34floorDivWZ4Q5Ns(byte arg0, int other) {
        return UByte$$ExternalSyntheticBackport0.m(UInt.m101constructorimpl(arg0 & 255), other);
    }

    /* renamed from: floorDiv-VKZWuLQ  reason: not valid java name */
    private static final long m33floorDivVKZWuLQ(byte arg0, long other) {
        return UByte$$ExternalSyntheticBackport3.m(ULong.m180constructorimpl(((long) arg0) & 255), other);
    }

    /* renamed from: mod-7apg3OU  reason: not valid java name */
    private static final byte m43mod7apg3OU(byte arg0, byte other) {
        return m24constructorimpl((byte) UByte$$ExternalSyntheticBackport1.m(UInt.m101constructorimpl(arg0 & 255), UInt.m101constructorimpl(other & 255)));
    }

    /* renamed from: mod-xj2QHRw  reason: not valid java name */
    private static final short m46modxj2QHRw(byte arg0, short other) {
        return UShort.m287constructorimpl((short) UByte$$ExternalSyntheticBackport1.m(UInt.m101constructorimpl(arg0 & 255), UInt.m101constructorimpl(65535 & other)));
    }

    /* renamed from: mod-WZ4Q5Ns  reason: not valid java name */
    private static final int m45modWZ4Q5Ns(byte arg0, int other) {
        return UByte$$ExternalSyntheticBackport1.m(UInt.m101constructorimpl(arg0 & 255), other);
    }

    /* renamed from: mod-VKZWuLQ  reason: not valid java name */
    private static final long m44modVKZWuLQ(byte arg0, long other) {
        return UByte$$ExternalSyntheticBackport2.m(ULong.m180constructorimpl(((long) arg0) & 255), other);
    }

    /* renamed from: inc-w2LRezQ  reason: not valid java name */
    private static final byte m37incw2LRezQ(byte arg0) {
        return m24constructorimpl((byte) (arg0 + 1));
    }

    /* renamed from: dec-w2LRezQ  reason: not valid java name */
    private static final byte m25decw2LRezQ(byte arg0) {
        return m24constructorimpl((byte) (arg0 - 1));
    }

    /* renamed from: rangeTo-7apg3OU  reason: not valid java name */
    private static final UIntRange m52rangeTo7apg3OU(byte arg0, byte other) {
        return new UIntRange(UInt.m101constructorimpl(arg0 & 255), UInt.m101constructorimpl(other & 255), (DefaultConstructorMarker) null);
    }

    /* renamed from: rangeUntil-7apg3OU  reason: not valid java name */
    private static final UIntRange m53rangeUntil7apg3OU(byte arg0, byte other) {
        return URangesKt.m1270untilJ1ME1BU(UInt.m101constructorimpl(arg0 & 255), UInt.m101constructorimpl(other & 255));
    }

    /* renamed from: and-7apg3OU  reason: not valid java name */
    private static final byte m17and7apg3OU(byte arg0, byte other) {
        return m24constructorimpl((byte) (arg0 & other));
    }

    /* renamed from: or-7apg3OU  reason: not valid java name */
    private static final byte m47or7apg3OU(byte arg0, byte other) {
        return m24constructorimpl((byte) (arg0 | other));
    }

    /* renamed from: xor-7apg3OU  reason: not valid java name */
    private static final byte m73xor7apg3OU(byte arg0, byte other) {
        return m24constructorimpl((byte) (arg0 ^ other));
    }

    /* renamed from: inv-w2LRezQ  reason: not valid java name */
    private static final byte m38invw2LRezQ(byte arg0) {
        return m24constructorimpl((byte) (~arg0));
    }

    /* renamed from: toByte-impl  reason: not valid java name */
    private static final byte m62toByteimpl(byte arg0) {
        return arg0;
    }

    /* renamed from: toShort-impl  reason: not valid java name */
    private static final short m67toShortimpl(byte arg0) {
        return (short) (((short) arg0) & 255);
    }

    /* renamed from: toInt-impl  reason: not valid java name */
    private static final int m65toIntimpl(byte arg0) {
        return arg0 & 255;
    }

    /* renamed from: toLong-impl  reason: not valid java name */
    private static final long m66toLongimpl(byte arg0) {
        return ((long) arg0) & 255;
    }

    /* renamed from: toUByte-w2LRezQ  reason: not valid java name */
    private static final byte m69toUBytew2LRezQ(byte arg0) {
        return arg0;
    }

    /* renamed from: toUShort-Mh2AYeg  reason: not valid java name */
    private static final short m72toUShortMh2AYeg(byte arg0) {
        return UShort.m287constructorimpl((short) (((short) arg0) & 255));
    }

    /* renamed from: toUInt-pVg5ArA  reason: not valid java name */
    private static final int m70toUIntpVg5ArA(byte arg0) {
        return UInt.m101constructorimpl(arg0 & 255);
    }

    /* renamed from: toULong-s-VKNKU  reason: not valid java name */
    private static final long m71toULongsVKNKU(byte arg0) {
        return ULong.m180constructorimpl(((long) arg0) & 255);
    }

    /* renamed from: toFloat-impl  reason: not valid java name */
    private static final float m64toFloatimpl(byte arg0) {
        return (float) UnsignedKt.uintToDouble(arg0 & 255);
    }

    /* renamed from: toDouble-impl  reason: not valid java name */
    private static final double m63toDoubleimpl(byte arg0) {
        return UnsignedKt.uintToDouble(arg0 & 255);
    }

    /* renamed from: toString-impl  reason: not valid java name */
    public static String m68toStringimpl(byte arg0) {
        return String.valueOf(arg0 & 255);
    }

    public String toString() {
        return m68toStringimpl(this.data);
    }
}
