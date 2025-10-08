package kotlin;

import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.UIntRange;
import kotlin.ranges.URangesKt;

@JvmInline
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b!\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b@\u0018\u0000 {2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001{B\u0011\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\f¢\u0006\u0004\b\n\u0010\u000bJ\u0018\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\rH\n¢\u0006\u0004\b\u000e\u0010\u000fJ\u0018\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0010\u0010\u000bJ\u0018\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0011H\n¢\u0006\u0004\b\u0012\u0010\u0013J\u0018\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0014H\n¢\u0006\u0004\b\u0015\u0010\u0016J\u0013\u0010\u0017\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b\u0018\u0010\u0005J\u0018\u0010\u0019\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\n¢\u0006\u0004\b\u001a\u0010\u000fJ\u0018\u0010\u0019\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\n¢\u0006\u0004\b\u001b\u0010\u000bJ\u0018\u0010\u0019\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\n¢\u0006\u0004\b\u001c\u0010\u001dJ\u0018\u0010\u0019\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\n¢\u0006\u0004\b\u001e\u0010\u0016J\u001a\u0010\u001f\u001a\u00020 2\b\u0010\t\u001a\u0004\u0018\u00010!HÖ\u0003¢\u0006\u0004\b\"\u0010#J\u0018\u0010$\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\b¢\u0006\u0004\b%\u0010\u000fJ\u0018\u0010$\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\b¢\u0006\u0004\b&\u0010\u000bJ\u0018\u0010$\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\b¢\u0006\u0004\b'\u0010\u001dJ\u0018\u0010$\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\b¢\u0006\u0004\b(\u0010\u0016J\u0010\u0010)\u001a\u00020\u0003HÖ\u0001¢\u0006\u0004\b*\u0010\u0005J\u0013\u0010+\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b,\u0010\u0005J\u0013\u0010-\u001a\u00020\u0000H\bø\u0001\u0000¢\u0006\u0004\b.\u0010\u0005J\u0018\u0010/\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\n¢\u0006\u0004\b0\u0010\u000fJ\u0018\u0010/\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\n¢\u0006\u0004\b1\u0010\u000bJ\u0018\u0010/\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\n¢\u0006\u0004\b2\u0010\u001dJ\u0018\u0010/\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\n¢\u0006\u0004\b3\u0010\u0016J\u0018\u00104\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\rH\b¢\u0006\u0004\b5\u00106J\u0018\u00104\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\b¢\u0006\u0004\b7\u0010\u000bJ\u0018\u00104\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\b¢\u0006\u0004\b8\u0010\u001dJ\u0018\u00104\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\b¢\u0006\u0004\b9\u0010:J\u0018\u0010;\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\f¢\u0006\u0004\b<\u0010\u000bJ\u0018\u0010=\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\n¢\u0006\u0004\b>\u0010\u000fJ\u0018\u0010=\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\n¢\u0006\u0004\b?\u0010\u000bJ\u0018\u0010=\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\n¢\u0006\u0004\b@\u0010\u001dJ\u0018\u0010=\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\n¢\u0006\u0004\bA\u0010\u0016J\u0018\u0010B\u001a\u00020C2\u0006\u0010\t\u001a\u00020\u0000H\n¢\u0006\u0004\bD\u0010EJ\u0018\u0010F\u001a\u00020C2\u0006\u0010\t\u001a\u00020\u0000H\n¢\u0006\u0004\bG\u0010EJ\u0018\u0010H\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\n¢\u0006\u0004\bI\u0010\u000fJ\u0018\u0010H\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\n¢\u0006\u0004\bJ\u0010\u000bJ\u0018\u0010H\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\n¢\u0006\u0004\bK\u0010\u001dJ\u0018\u0010H\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\n¢\u0006\u0004\bL\u0010\u0016J\u001b\u0010M\u001a\u00020\u00002\u0006\u0010N\u001a\u00020\u0003H\fø\u0001\u0000¢\u0006\u0004\bO\u0010\u000bJ\u001b\u0010P\u001a\u00020\u00002\u0006\u0010N\u001a\u00020\u0003H\fø\u0001\u0000¢\u0006\u0004\bQ\u0010\u000bJ\u0018\u0010R\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\n¢\u0006\u0004\bS\u0010\u000fJ\u0018\u0010R\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\n¢\u0006\u0004\bT\u0010\u000bJ\u0018\u0010R\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\n¢\u0006\u0004\bU\u0010\u001dJ\u0018\u0010R\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\n¢\u0006\u0004\bV\u0010\u0016J\u0010\u0010W\u001a\u00020XH\b¢\u0006\u0004\bY\u0010ZJ\u0010\u0010[\u001a\u00020\\H\b¢\u0006\u0004\b]\u0010^J\u0010\u0010_\u001a\u00020`H\b¢\u0006\u0004\ba\u0010bJ\u0010\u0010c\u001a\u00020\u0003H\b¢\u0006\u0004\bd\u0010\u0005J\u0010\u0010e\u001a\u00020fH\b¢\u0006\u0004\bg\u0010hJ\u0010\u0010i\u001a\u00020jH\b¢\u0006\u0004\bk\u0010lJ\u000f\u0010m\u001a\u00020nH\u0016¢\u0006\u0004\bo\u0010pJ\u0013\u0010q\u001a\u00020\rH\bø\u0001\u0000¢\u0006\u0004\br\u0010ZJ\u0013\u0010s\u001a\u00020\u0000H\bø\u0001\u0000¢\u0006\u0004\bt\u0010\u0005J\u0013\u0010u\u001a\u00020\u0011H\bø\u0001\u0000¢\u0006\u0004\bv\u0010hJ\u0013\u0010w\u001a\u00020\u0014H\bø\u0001\u0000¢\u0006\u0004\bx\u0010lJ\u0018\u0010y\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\f¢\u0006\u0004\bz\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u0001\u0002\u0001\u00020\u0003\u0002\u0004\n\u0002\b!¨\u0006|"}, d2 = {"Lkotlin/UInt;", "", "data", "", "constructor-impl", "(I)I", "getData$annotations", "()V", "and", "other", "and-WZ4Q5Ns", "(II)I", "compareTo", "Lkotlin/UByte;", "compareTo-7apg3OU", "(IB)I", "compareTo-WZ4Q5Ns", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(IJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(IS)I", "dec", "dec-pVg5ArA", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(IJ)J", "div-xj2QHRw", "equals", "", "", "equals-impl", "(ILjava/lang/Object;)Z", "floorDiv", "floorDiv-7apg3OU", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "floorDiv-xj2QHRw", "hashCode", "hashCode-impl", "inc", "inc-pVg5ArA", "inv", "inv-pVg5ArA", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "mod", "mod-7apg3OU", "(IB)B", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "mod-xj2QHRw", "(IS)S", "or", "or-WZ4Q5Ns", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-WZ4Q5Ns", "(II)Lkotlin/ranges/UIntRange;", "rangeUntil", "rangeUntil-WZ4Q5Ns", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "shl", "bitCount", "shl-pVg5ArA", "shr", "shr-pVg5ArA", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(I)B", "toDouble", "", "toDouble-impl", "(I)D", "toFloat", "", "toFloat-impl", "(I)F", "toInt", "toInt-impl", "toLong", "", "toLong-impl", "(I)J", "toShort", "", "toShort-impl", "(I)S", "toString", "", "toString-impl", "(I)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-WZ4Q5Ns", "Companion", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* compiled from: UInt.kt */
public final class UInt implements Comparable<UInt> {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    public static final int MAX_VALUE = -1;
    public static final int MIN_VALUE = 0;
    public static final int SIZE_BITS = 32;
    public static final int SIZE_BYTES = 4;
    private final int data;

    /* renamed from: box-impl  reason: not valid java name */
    public static final /* synthetic */ UInt m95boximpl(int i) {
        return new UInt(i);
    }

    /* renamed from: constructor-impl  reason: not valid java name */
    public static int m101constructorimpl(int i) {
        return i;
    }

    /* renamed from: equals-impl  reason: not valid java name */
    public static boolean m107equalsimpl(int i, Object obj) {
        return (obj instanceof UInt) && i == ((UInt) obj).m153unboximpl();
    }

    /* renamed from: equals-impl0  reason: not valid java name */
    public static final boolean m108equalsimpl0(int i, int i2) {
        return i == i2;
    }

    public static /* synthetic */ void getData$annotations() {
    }

    /* renamed from: hashCode-impl  reason: not valid java name */
    public static int m113hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    public boolean equals(Object obj) {
        return m107equalsimpl(this.data, obj);
    }

    public int hashCode() {
        return m113hashCodeimpl(this.data);
    }

    /* renamed from: unbox-impl  reason: not valid java name */
    public final /* synthetic */ int m153unboximpl() {
        return this.data;
    }

    public /* bridge */ /* synthetic */ int compareTo(Object other) {
        return UnsignedKt.uintCompare(m153unboximpl(), ((UInt) other).m153unboximpl());
    }

    private /* synthetic */ UInt(int data2) {
        this.data = data2;
    }

    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0013\u0010\u0003\u001a\u00020\u0004XTø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0005R\u0013\u0010\u0006\u001a\u00020\u0004XTø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bXT¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bXT¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b!¨\u0006\n"}, d2 = {"Lkotlin/UInt$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UInt;", "I", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* compiled from: UInt.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: compareTo-7apg3OU  reason: not valid java name */
    private static final int m96compareTo7apg3OU(int arg0, byte other) {
        return Integer.compare(arg0 ^ Integer.MIN_VALUE, m101constructorimpl(other & 255) ^ Integer.MIN_VALUE);
    }

    /* renamed from: compareTo-xj2QHRw  reason: not valid java name */
    private static final int m100compareToxj2QHRw(int arg0, short other) {
        return Integer.compare(arg0 ^ Integer.MIN_VALUE, m101constructorimpl(65535 & other) ^ Integer.MIN_VALUE);
    }

    /* renamed from: compareTo-WZ4Q5Ns  reason: not valid java name */
    private int m98compareToWZ4Q5Ns(int other) {
        return UnsignedKt.uintCompare(m153unboximpl(), other);
    }

    /* renamed from: compareTo-WZ4Q5Ns  reason: not valid java name */
    private static int m99compareToWZ4Q5Ns(int arg0, int other) {
        return UnsignedKt.uintCompare(arg0, other);
    }

    /* renamed from: compareTo-VKZWuLQ  reason: not valid java name */
    private static final int m97compareToVKZWuLQ(int arg0, long other) {
        return Long.compare(ULong.m180constructorimpl(((long) arg0) & 4294967295L) ^ Long.MIN_VALUE, other ^ Long.MIN_VALUE);
    }

    /* renamed from: plus-7apg3OU  reason: not valid java name */
    private static final int m125plus7apg3OU(int arg0, byte other) {
        return m101constructorimpl(m101constructorimpl(other & 255) + arg0);
    }

    /* renamed from: plus-xj2QHRw  reason: not valid java name */
    private static final int m128plusxj2QHRw(int arg0, short other) {
        return m101constructorimpl(m101constructorimpl(65535 & other) + arg0);
    }

    /* renamed from: plus-WZ4Q5Ns  reason: not valid java name */
    private static final int m127plusWZ4Q5Ns(int arg0, int other) {
        return m101constructorimpl(arg0 + other);
    }

    /* renamed from: plus-VKZWuLQ  reason: not valid java name */
    private static final long m126plusVKZWuLQ(int arg0, long other) {
        return ULong.m180constructorimpl(ULong.m180constructorimpl(((long) arg0) & 4294967295L) + other);
    }

    /* renamed from: minus-7apg3OU  reason: not valid java name */
    private static final int m116minus7apg3OU(int arg0, byte other) {
        return m101constructorimpl(arg0 - m101constructorimpl(other & 255));
    }

    /* renamed from: minus-xj2QHRw  reason: not valid java name */
    private static final int m119minusxj2QHRw(int arg0, short other) {
        return m101constructorimpl(arg0 - m101constructorimpl(65535 & other));
    }

    /* renamed from: minus-WZ4Q5Ns  reason: not valid java name */
    private static final int m118minusWZ4Q5Ns(int arg0, int other) {
        return m101constructorimpl(arg0 - other);
    }

    /* renamed from: minus-VKZWuLQ  reason: not valid java name */
    private static final long m117minusVKZWuLQ(int arg0, long other) {
        return ULong.m180constructorimpl(ULong.m180constructorimpl(((long) arg0) & 4294967295L) - other);
    }

    /* renamed from: times-7apg3OU  reason: not valid java name */
    private static final int m137times7apg3OU(int arg0, byte other) {
        return m101constructorimpl(m101constructorimpl(other & 255) * arg0);
    }

    /* renamed from: times-xj2QHRw  reason: not valid java name */
    private static final int m140timesxj2QHRw(int arg0, short other) {
        return m101constructorimpl(m101constructorimpl(65535 & other) * arg0);
    }

    /* renamed from: times-WZ4Q5Ns  reason: not valid java name */
    private static final int m139timesWZ4Q5Ns(int arg0, int other) {
        return m101constructorimpl(arg0 * other);
    }

    /* renamed from: times-VKZWuLQ  reason: not valid java name */
    private static final long m138timesVKZWuLQ(int arg0, long other) {
        return ULong.m180constructorimpl(ULong.m180constructorimpl(((long) arg0) & 4294967295L) * other);
    }

    /* renamed from: div-7apg3OU  reason: not valid java name */
    private static final int m103div7apg3OU(int arg0, byte other) {
        return UByte$$ExternalSyntheticBackport0.m(arg0, m101constructorimpl(other & 255));
    }

    /* renamed from: div-xj2QHRw  reason: not valid java name */
    private static final int m106divxj2QHRw(int arg0, short other) {
        return UByte$$ExternalSyntheticBackport0.m(arg0, m101constructorimpl(65535 & other));
    }

    /* renamed from: div-WZ4Q5Ns  reason: not valid java name */
    private static final int m105divWZ4Q5Ns(int arg0, int other) {
        return UnsignedKt.m357uintDivideJ1ME1BU(arg0, other);
    }

    /* renamed from: div-VKZWuLQ  reason: not valid java name */
    private static final long m104divVKZWuLQ(int arg0, long other) {
        return UByte$$ExternalSyntheticBackport3.m(ULong.m180constructorimpl(((long) arg0) & 4294967295L), other);
    }

    /* renamed from: rem-7apg3OU  reason: not valid java name */
    private static final int m131rem7apg3OU(int arg0, byte other) {
        return UByte$$ExternalSyntheticBackport1.m(arg0, m101constructorimpl(other & 255));
    }

    /* renamed from: rem-xj2QHRw  reason: not valid java name */
    private static final int m134remxj2QHRw(int arg0, short other) {
        return UByte$$ExternalSyntheticBackport1.m(arg0, m101constructorimpl(65535 & other));
    }

    /* renamed from: rem-WZ4Q5Ns  reason: not valid java name */
    private static final int m133remWZ4Q5Ns(int arg0, int other) {
        return UnsignedKt.m358uintRemainderJ1ME1BU(arg0, other);
    }

    /* renamed from: rem-VKZWuLQ  reason: not valid java name */
    private static final long m132remVKZWuLQ(int arg0, long other) {
        return UByte$$ExternalSyntheticBackport2.m(ULong.m180constructorimpl(((long) arg0) & 4294967295L), other);
    }

    /* renamed from: floorDiv-7apg3OU  reason: not valid java name */
    private static final int m109floorDiv7apg3OU(int arg0, byte other) {
        return UByte$$ExternalSyntheticBackport0.m(arg0, m101constructorimpl(other & 255));
    }

    /* renamed from: floorDiv-xj2QHRw  reason: not valid java name */
    private static final int m112floorDivxj2QHRw(int arg0, short other) {
        return UByte$$ExternalSyntheticBackport0.m(arg0, m101constructorimpl(65535 & other));
    }

    /* renamed from: floorDiv-WZ4Q5Ns  reason: not valid java name */
    private static final int m111floorDivWZ4Q5Ns(int arg0, int other) {
        return UByte$$ExternalSyntheticBackport0.m(arg0, other);
    }

    /* renamed from: floorDiv-VKZWuLQ  reason: not valid java name */
    private static final long m110floorDivVKZWuLQ(int arg0, long other) {
        return UByte$$ExternalSyntheticBackport3.m(ULong.m180constructorimpl(((long) arg0) & 4294967295L), other);
    }

    /* renamed from: mod-7apg3OU  reason: not valid java name */
    private static final byte m120mod7apg3OU(int arg0, byte other) {
        return UByte.m24constructorimpl((byte) UByte$$ExternalSyntheticBackport1.m(arg0, m101constructorimpl(other & 255)));
    }

    /* renamed from: mod-xj2QHRw  reason: not valid java name */
    private static final short m123modxj2QHRw(int arg0, short other) {
        return UShort.m287constructorimpl((short) UByte$$ExternalSyntheticBackport1.m(arg0, m101constructorimpl(65535 & other)));
    }

    /* renamed from: mod-WZ4Q5Ns  reason: not valid java name */
    private static final int m122modWZ4Q5Ns(int arg0, int other) {
        return UByte$$ExternalSyntheticBackport1.m(arg0, other);
    }

    /* renamed from: mod-VKZWuLQ  reason: not valid java name */
    private static final long m121modVKZWuLQ(int arg0, long other) {
        return UByte$$ExternalSyntheticBackport2.m(ULong.m180constructorimpl(((long) arg0) & 4294967295L), other);
    }

    /* renamed from: inc-pVg5ArA  reason: not valid java name */
    private static final int m114incpVg5ArA(int arg0) {
        return m101constructorimpl(arg0 + 1);
    }

    /* renamed from: dec-pVg5ArA  reason: not valid java name */
    private static final int m102decpVg5ArA(int arg0) {
        return m101constructorimpl(arg0 - 1);
    }

    /* renamed from: rangeTo-WZ4Q5Ns  reason: not valid java name */
    private static final UIntRange m129rangeToWZ4Q5Ns(int arg0, int other) {
        return new UIntRange(arg0, other, (DefaultConstructorMarker) null);
    }

    /* renamed from: rangeUntil-WZ4Q5Ns  reason: not valid java name */
    private static final UIntRange m130rangeUntilWZ4Q5Ns(int arg0, int other) {
        return URangesKt.m1270untilJ1ME1BU(arg0, other);
    }

    /* renamed from: shl-pVg5ArA  reason: not valid java name */
    private static final int m135shlpVg5ArA(int arg0, int bitCount) {
        return m101constructorimpl(arg0 << bitCount);
    }

    /* renamed from: shr-pVg5ArA  reason: not valid java name */
    private static final int m136shrpVg5ArA(int arg0, int bitCount) {
        return m101constructorimpl(arg0 >>> bitCount);
    }

    /* renamed from: and-WZ4Q5Ns  reason: not valid java name */
    private static final int m94andWZ4Q5Ns(int arg0, int other) {
        return m101constructorimpl(arg0 & other);
    }

    /* renamed from: or-WZ4Q5Ns  reason: not valid java name */
    private static final int m124orWZ4Q5Ns(int arg0, int other) {
        return m101constructorimpl(arg0 | other);
    }

    /* renamed from: xor-WZ4Q5Ns  reason: not valid java name */
    private static final int m152xorWZ4Q5Ns(int arg0, int other) {
        return m101constructorimpl(arg0 ^ other);
    }

    /* renamed from: inv-pVg5ArA  reason: not valid java name */
    private static final int m115invpVg5ArA(int arg0) {
        return m101constructorimpl(~arg0);
    }

    /* renamed from: toByte-impl  reason: not valid java name */
    private static final byte m141toByteimpl(int arg0) {
        return (byte) arg0;
    }

    /* renamed from: toShort-impl  reason: not valid java name */
    private static final short m146toShortimpl(int arg0) {
        return (short) arg0;
    }

    /* renamed from: toInt-impl  reason: not valid java name */
    private static final int m144toIntimpl(int arg0) {
        return arg0;
    }

    /* renamed from: toLong-impl  reason: not valid java name */
    private static final long m145toLongimpl(int arg0) {
        return ((long) arg0) & 4294967295L;
    }

    /* renamed from: toUByte-w2LRezQ  reason: not valid java name */
    private static final byte m148toUBytew2LRezQ(int arg0) {
        return UByte.m24constructorimpl((byte) arg0);
    }

    /* renamed from: toUShort-Mh2AYeg  reason: not valid java name */
    private static final short m151toUShortMh2AYeg(int arg0) {
        return UShort.m287constructorimpl((short) arg0);
    }

    /* renamed from: toUInt-pVg5ArA  reason: not valid java name */
    private static final int m149toUIntpVg5ArA(int arg0) {
        return arg0;
    }

    /* renamed from: toULong-s-VKNKU  reason: not valid java name */
    private static final long m150toULongsVKNKU(int arg0) {
        return ULong.m180constructorimpl(((long) arg0) & 4294967295L);
    }

    /* renamed from: toFloat-impl  reason: not valid java name */
    private static final float m143toFloatimpl(int arg0) {
        return (float) UnsignedKt.uintToDouble(arg0);
    }

    /* renamed from: toDouble-impl  reason: not valid java name */
    private static final double m142toDoubleimpl(int arg0) {
        return UnsignedKt.uintToDouble(arg0);
    }

    /* renamed from: toString-impl  reason: not valid java name */
    public static String m147toStringimpl(int arg0) {
        return String.valueOf(((long) arg0) & 4294967295L);
    }

    public String toString() {
        return m147toStringimpl(this.data);
    }
}
