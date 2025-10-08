package kotlin.time;

import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.ComparableTimeMark;
import kotlin.time.TimeSource;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\b'\u0018\u00002\u00020\u0001:\u0001\u000bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\nH$R\u0014\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\f"}, d2 = {"Lkotlin/time/AbstractDoubleTimeSource;", "Lkotlin/time/TimeSource$WithComparableMarks;", "unit", "Lkotlin/time/DurationUnit;", "(Lkotlin/time/DurationUnit;)V", "getUnit", "()Lkotlin/time/DurationUnit;", "markNow", "Lkotlin/time/ComparableTimeMark;", "read", "", "DoubleTimeMark", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
@Deprecated(message = "Using AbstractDoubleTimeSource is no longer recommended, use AbstractLongTimeSource instead.")
/* compiled from: TimeSources.kt */
public abstract class AbstractDoubleTimeSource implements TimeSource.WithComparableMarks {
    private final DurationUnit unit;

    /* access modifiers changed from: protected */
    public abstract double read();

    public AbstractDoubleTimeSource(DurationUnit unit2) {
        Intrinsics.checkNotNullParameter(unit2, "unit");
        this.unit = unit2;
    }

    /* access modifiers changed from: protected */
    public final DurationUnit getUnit() {
        return this.unit;
    }

    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\n\u001a\u00020\u0007H\u0016ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\fJ\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u001b\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015J\u0018\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0007H\u0002¢\u0006\u0004\b\u0018\u0010\u0019J\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u0013\u0010\u0006\u001a\u00020\u0007X\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b!¨\u0006\u001c"}, d2 = {"Lkotlin/time/AbstractDoubleTimeSource$DoubleTimeMark;", "Lkotlin/time/ComparableTimeMark;", "startedAt", "", "timeSource", "Lkotlin/time/AbstractDoubleTimeSource;", "offset", "Lkotlin/time/Duration;", "(DLkotlin/time/AbstractDoubleTimeSource;JLkotlin/jvm/internal/DefaultConstructorMarker;)V", "J", "elapsedNow", "elapsedNow-UwyO8pc", "()J", "equals", "", "other", "", "hashCode", "", "minus", "minus-UwyO8pc", "(Lkotlin/time/ComparableTimeMark;)J", "plus", "duration", "plus-LRDsOJo", "(J)Lkotlin/time/ComparableTimeMark;", "toString", "", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* compiled from: TimeSources.kt */
    private static final class DoubleTimeMark implements ComparableTimeMark {
        private final long offset;
        private final double startedAt;
        private final AbstractDoubleTimeSource timeSource;

        public /* synthetic */ DoubleTimeMark(double d, AbstractDoubleTimeSource abstractDoubleTimeSource, long j, DefaultConstructorMarker defaultConstructorMarker) {
            this(d, abstractDoubleTimeSource, j);
        }

        private DoubleTimeMark(double startedAt2, AbstractDoubleTimeSource timeSource2, long offset2) {
            Intrinsics.checkNotNullParameter(timeSource2, "timeSource");
            this.startedAt = startedAt2;
            this.timeSource = timeSource2;
            this.offset = offset2;
        }

        public int compareTo(ComparableTimeMark other) {
            return ComparableTimeMark.DefaultImpls.compareTo(this, other);
        }

        public boolean hasNotPassedNow() {
            return ComparableTimeMark.DefaultImpls.hasNotPassedNow(this);
        }

        public boolean hasPassedNow() {
            return ComparableTimeMark.DefaultImpls.hasPassedNow(this);
        }

        /* renamed from: minus-LRDsOJo  reason: not valid java name */
        public ComparableTimeMark m1316minusLRDsOJo(long duration) {
            return ComparableTimeMark.DefaultImpls.m1333minusLRDsOJo(this, duration);
        }

        /* renamed from: elapsedNow-UwyO8pc  reason: not valid java name */
        public long m1314elapsedNowUwyO8pc() {
            return Duration.m1373minusLRDsOJo(DurationKt.toDuration(this.timeSource.read() - this.startedAt, this.timeSource.getUnit()), this.offset);
        }

        /* renamed from: plus-LRDsOJo  reason: not valid java name */
        public ComparableTimeMark m1319plusLRDsOJo(long duration) {
            return new DoubleTimeMark(this.startedAt, this.timeSource, Duration.m1374plusLRDsOJo(this.offset, duration), (DefaultConstructorMarker) null);
        }

        /* renamed from: minus-UwyO8pc  reason: not valid java name */
        public long m1317minusUwyO8pc(ComparableTimeMark other) {
            Intrinsics.checkNotNullParameter(other, "other");
            if (!(other instanceof DoubleTimeMark) || !Intrinsics.areEqual((Object) this.timeSource, (Object) ((DoubleTimeMark) other).timeSource)) {
                throw new IllegalArgumentException("Subtracting or comparing time marks from different time sources is not possible: " + this + " and " + other);
            } else if (Duration.m1343equalsimpl0(this.offset, ((DoubleTimeMark) other).offset) && Duration.m1370isInfiniteimpl(this.offset)) {
                return Duration.Companion.m1441getZEROUwyO8pc();
            } else {
                long offsetDiff = Duration.m1373minusLRDsOJo(this.offset, ((DoubleTimeMark) other).offset);
                long startedAtDiff = DurationKt.toDuration(this.startedAt - ((DoubleTimeMark) other).startedAt, this.timeSource.getUnit());
                return Duration.m1343equalsimpl0(startedAtDiff, Duration.m1391unaryMinusUwyO8pc(offsetDiff)) ? Duration.Companion.m1441getZEROUwyO8pc() : Duration.m1374plusLRDsOJo(startedAtDiff, offsetDiff);
            }
        }

        public boolean equals(Object other) {
            return (other instanceof DoubleTimeMark) && Intrinsics.areEqual((Object) this.timeSource, (Object) ((DoubleTimeMark) other).timeSource) && Duration.m1343equalsimpl0(m1317minusUwyO8pc((ComparableTimeMark) other), Duration.Companion.m1441getZEROUwyO8pc());
        }

        public int hashCode() {
            return Duration.m1366hashCodeimpl(Duration.m1374plusLRDsOJo(DurationKt.toDuration(this.startedAt, this.timeSource.getUnit()), this.offset));
        }

        public String toString() {
            return "DoubleTimeMark(" + this.startedAt + DurationUnitKt.shortName(this.timeSource.getUnit()) + " + " + Duration.m1387toStringimpl(this.offset) + ", " + this.timeSource + ')';
        }
    }

    public ComparableTimeMark markNow() {
        return new DoubleTimeMark(read(), this, Duration.Companion.m1441getZEROUwyO8pc(), (DefaultConstructorMarker) null);
    }
}
