package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010 \n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B5\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u0012\n\u0010\u0005\u001a\u00060\u0003j\u0002`\u0004\u0012\n\u0010\u0006\u001a\u00060\u0003j\u0002`\u0004\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u0010\u0010'\u001a\u00020\u00112\u0006\u0010(\u001a\u00020\u0011H\u0002Jf\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u00112\u0006\u0010,\u001a\u00020\u00112\n\u0010-\u001a\u00060\u0003j\u0002`\u00042\n\u0010.\u001a\u00060\u0003j\u0002`\u00042\n\u0010/\u001a\u00060\u0003j\u0002`\u00042\n\u00100\u001a\u00060\u0003j\u0002`\u00042\n\u00101\u001a\u00060\u0003j\u0002`\u00042\u0006\u00102\u001a\u00020\u0011H\u0002ø\u0001\u0000¢\u0006\u0004\b3\u00104J \u00105\u001a\b\u0012\u0004\u0012\u00020*062\u0006\u00107\u001a\u00020\u00112\b\b\u0002\u00108\u001a\u00020\u0011H\u0007JJ\u00109\u001a\n\u0018\u00010\u0003j\u0004\u0018\u0001`\u00042\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u00042\n\u0010:\u001a\u00060\u0003j\u0002`\u00042\n\u0010\u0005\u001a\u00060\u0003j\u0002`\u00042\n\u0010\u0016\u001a\u00060\u0003j\u0002`\u0004H\u0002ø\u0001\u0000¢\u0006\u0004\b;\u0010<R&\u0010\n\u001a\u00060\u0003j\u0002`\u0004X\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0010\n\u0002\u0010\u000f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u001d\u0010\u0016\u001a\u00060\u0003j\u0002`\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\u0017\u0010\fR\u001d\u0010\u0018\u001a\u00060\u0003j\u0002`\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\u0019\u0010\fR\u0011\u0010\u001a\u001a\u00020\u00118F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0013R\u0011\u0010\u001c\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0013R\u001d\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\u001e\u0010\fR\u001d\u0010\u0005\u001a\u00060\u0003j\u0002`\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\u001f\u0010\fR\u001d\u0010\u0006\u001a\u00060\u0003j\u0002`\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b \u0010\fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010#\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0013R\u0011\u0010%\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0013\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006="}, d2 = {"Landroidx/graphics/shapes/RoundedCorner;", "", "p0", "Landroidx/collection/FloatFloatPair;", "Landroidx/graphics/shapes/Point;", "p1", "p2", "rounding", "Landroidx/graphics/shapes/CornerRounding;", "(JJJLandroidx/graphics/shapes/CornerRounding;Lkotlin/jvm/internal/DefaultConstructorMarker;)V", "center", "getCenter-1ufDz9w", "()J", "setCenter-DnnuFBc", "(J)V", "J", "cornerRadius", "", "getCornerRadius", "()F", "cosAngle", "getCosAngle", "d1", "getD1-1ufDz9w", "d2", "getD2-1ufDz9w", "expectedCut", "getExpectedCut", "expectedRoundCut", "getExpectedRoundCut", "getP0-1ufDz9w", "getP1-1ufDz9w", "getP2-1ufDz9w", "getRounding", "()Landroidx/graphics/shapes/CornerRounding;", "sinAngle", "getSinAngle", "smoothing", "getSmoothing", "calculateActualSmoothingValue", "allowedCut", "computeFlankingCurve", "Landroidx/graphics/shapes/Cubic;", "actualRoundCut", "actualSmoothingValues", "corner", "sideStart", "circleSegmentIntersection", "otherCircleSegmentIntersection", "circleCenter", "actualR", "computeFlankingCurve-oAJzIJU", "(FFJJJJJF)Landroidx/graphics/shapes/Cubic;", "getCubics", "", "allowedCut0", "allowedCut1", "lineIntersection", "d0", "lineIntersection-CBFvKDc", "(JJJJ)Landroidx/collection/FloatFloatPair;", "graphics-shapes_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: RoundedPolygon.kt */
final class RoundedCorner {
    private long center;
    private final float cornerRadius;
    private final float cosAngle;
    private final long d1;
    private final long d2;
    private final float expectedRoundCut;
    private final long p0;
    private final long p1;
    private final long p2;
    private final CornerRounding rounding;
    private final float sinAngle;
    private final float smoothing;

    public /* synthetic */ RoundedCorner(long j, long j2, long j3, CornerRounding cornerRounding, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3, cornerRounding);
    }

    public final List<Cubic> getCubics(float f) {
        return getCubics$default(this, f, 0.0f, 2, (Object) null);
    }

    private RoundedCorner(long p02, long p12, long p22, CornerRounding rounding2) {
        float f;
        this.p0 = p02;
        this.p1 = p12;
        this.p2 = p22;
        this.rounding = rounding2;
        this.d1 = PointKt.m2040getDirectionDnnuFBc(PointKt.m2046minusybeJwSQ(this.p0, this.p1));
        this.d2 = PointKt.m2040getDirectionDnnuFBc(PointKt.m2046minusybeJwSQ(this.p2, this.p1));
        CornerRounding cornerRounding = this.rounding;
        this.cornerRadius = cornerRounding != null ? cornerRounding.getRadius() : 0.0f;
        CornerRounding cornerRounding2 = this.rounding;
        this.smoothing = cornerRounding2 != null ? cornerRounding2.getSmoothing() : 0.0f;
        this.cosAngle = PointKt.m2039dotProductybeJwSQ(this.d1, this.d2);
        float f2 = (float) 1;
        this.sinAngle = (float) Math.sqrt((double) (f2 - Utils.square(this.cosAngle)));
        if (((double) this.sinAngle) > 0.001d) {
            f = (this.cornerRadius * (this.cosAngle + f2)) / this.sinAngle;
        } else {
            f = 0.0f;
        }
        this.expectedRoundCut = f;
        this.center = FloatFloatPair.m1964constructorimpl(0.0f, 0.0f);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ RoundedCorner(long r11, long r13, long r15, androidx.graphics.shapes.CornerRounding r17, int r18, kotlin.jvm.internal.DefaultConstructorMarker r19) {
        /*
            r10 = this;
            r0 = r18 & 8
            if (r0 == 0) goto L_0x0007
            r0 = 0
            r8 = r0
            goto L_0x0009
        L_0x0007:
            r8 = r17
        L_0x0009:
            r9 = 0
            r1 = r10
            r2 = r11
            r4 = r13
            r6 = r15
            r1.<init>(r2, r4, r6, r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.graphics.shapes.RoundedCorner.<init>(long, long, long, androidx.graphics.shapes.CornerRounding, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* renamed from: getP0-1ufDz9w  reason: not valid java name */
    public final long m2058getP01ufDz9w() {
        return this.p0;
    }

    /* renamed from: getP1-1ufDz9w  reason: not valid java name */
    public final long m2059getP11ufDz9w() {
        return this.p1;
    }

    /* renamed from: getP2-1ufDz9w  reason: not valid java name */
    public final long m2060getP21ufDz9w() {
        return this.p2;
    }

    public final CornerRounding getRounding() {
        return this.rounding;
    }

    /* renamed from: getD1-1ufDz9w  reason: not valid java name */
    public final long m2056getD11ufDz9w() {
        return this.d1;
    }

    /* renamed from: getD2-1ufDz9w  reason: not valid java name */
    public final long m2057getD21ufDz9w() {
        return this.d2;
    }

    public final float getCornerRadius() {
        return this.cornerRadius;
    }

    public final float getSmoothing() {
        return this.smoothing;
    }

    public final float getCosAngle() {
        return this.cosAngle;
    }

    public final float getSinAngle() {
        return this.sinAngle;
    }

    public final float getExpectedRoundCut() {
        return this.expectedRoundCut;
    }

    public final float getExpectedCut() {
        return (((float) 1) + this.smoothing) * this.expectedRoundCut;
    }

    /* renamed from: getCenter-1ufDz9w  reason: not valid java name */
    public final long m2055getCenter1ufDz9w() {
        return this.center;
    }

    /* renamed from: setCenter-DnnuFBc  reason: not valid java name */
    public final void m2061setCenterDnnuFBc(long j) {
        this.center = j;
    }

    public static /* synthetic */ List getCubics$default(RoundedCorner roundedCorner, float f, float f2, int i, Object obj) {
        if ((i & 2) != 0) {
            f2 = f;
        }
        return roundedCorner.getCubics(f, f2);
    }

    public final List<Cubic> getCubics(float allowedCut0, float allowedCut1) {
        float allowedCut = Math.min(allowedCut0, allowedCut1);
        if (this.expectedRoundCut < 1.0E-4f) {
            float f = allowedCut1;
        } else if (allowedCut < 1.0E-4f) {
            float f2 = allowedCut1;
        } else if (this.cornerRadius < 1.0E-4f) {
            float f3 = allowedCut1;
        } else {
            float actualRoundCut = Math.min(allowedCut, this.expectedRoundCut);
            float actualSmoothing0 = calculateActualSmoothingValue(allowedCut0);
            float actualSmoothing1 = calculateActualSmoothingValue(allowedCut1);
            float actualR = (this.cornerRadius * actualRoundCut) / this.expectedRoundCut;
            float centerDistance = (float) Math.sqrt((double) (Utils.square(actualR) + Utils.square(actualRoundCut)));
            this.center = PointKt.m2047plusybeJwSQ(this.p1, PointKt.m2049timesso9K2fw(PointKt.m2040getDirectionDnnuFBc(PointKt.m2037divso9K2fw(PointKt.m2047plusybeJwSQ(this.d1, this.d2), 2.0f)), centerDistance));
            long circleIntersection0 = PointKt.m2047plusybeJwSQ(this.p1, PointKt.m2049timesso9K2fw(this.d1, actualRoundCut));
            long circleIntersection2 = PointKt.m2047plusybeJwSQ(this.p1, PointKt.m2049timesso9K2fw(this.d2, actualRoundCut));
            float f4 = centerDistance;
            float f5 = actualSmoothing0;
            Cubic flanking0 = m2053computeFlankingCurveoAJzIJU(actualRoundCut, actualSmoothing0, this.p1, this.p0, circleIntersection0, circleIntersection2, this.center, actualR);
            long j = circleIntersection2;
            long circleIntersection22 = circleIntersection0;
            long circleIntersection23 = j;
            Cubic r3 = m2053computeFlankingCurveoAJzIJU(actualRoundCut, actualSmoothing1, this.p1, this.p2, circleIntersection23, circleIntersection22, this.center, actualR);
            long j2 = circleIntersection22;
            long circleIntersection02 = circleIntersection23;
            long circleIntersection24 = j2;
            Cubic flanking2 = r3.reverse();
            return CollectionsKt.listOf(flanking0, Cubic.Companion.circularArc(PointKt.m2043getXDnnuFBc(this.center), PointKt.m2044getYDnnuFBc(this.center), flanking0.getAnchor1X(), flanking0.getAnchor1Y(), flanking2.getAnchor0X(), flanking2.getAnchor0Y()), flanking2);
        }
        this.center = this.p1;
        return CollectionsKt.listOf(Cubic.Companion.straightLine(PointKt.m2043getXDnnuFBc(this.p1), PointKt.m2044getYDnnuFBc(this.p1), PointKt.m2043getXDnnuFBc(this.p1), PointKt.m2044getYDnnuFBc(this.p1)));
    }

    private final float calculateActualSmoothingValue(float allowedCut) {
        if (allowedCut > getExpectedCut()) {
            return this.smoothing;
        }
        if (allowedCut > this.expectedRoundCut) {
            return (this.smoothing * (allowedCut - this.expectedRoundCut)) / (getExpectedCut() - this.expectedRoundCut);
        }
        return 0.0f;
    }

    /* renamed from: computeFlankingCurve-oAJzIJU  reason: not valid java name */
    private final Cubic m2053computeFlankingCurveoAJzIJU(float actualRoundCut, float actualSmoothingValues, long corner, long sideStart, long circleSegmentIntersection, long otherCircleSegmentIntersection, long circleCenter, float actualR) {
        long anchorEnd;
        float f = actualSmoothingValues;
        long j = corner;
        long j2 = circleCenter;
        long j3 = sideStart;
        long sideDirection = PointKt.m2040getDirectionDnnuFBc(PointKt.m2046minusybeJwSQ(j3, j));
        long curveStart = PointKt.m2047plusybeJwSQ(j, PointKt.m2049timesso9K2fw(PointKt.m2049timesso9K2fw(sideDirection, actualRoundCut), ((float) 1) + f));
        long p = PointKt.m2045interpolatedLqxh1s(circleSegmentIntersection, PointKt.m2037divso9K2fw(PointKt.m2047plusybeJwSQ(circleSegmentIntersection, otherCircleSegmentIntersection), 2.0f), f);
        long curveEnd = PointKt.m2047plusybeJwSQ(j2, PointKt.m2049timesso9K2fw(Utils.directionVector(PointKt.m2043getXDnnuFBc(p) - PointKt.m2043getXDnnuFBc(j2), PointKt.m2044getYDnnuFBc(p) - PointKt.m2044getYDnnuFBc(j2)), actualR));
        long curveEnd2 = curveEnd;
        long curveStart2 = curveStart;
        FloatFloatPair r2 = m2054lineIntersectionCBFvKDc(j3, sideDirection, curveEnd2, Utils.m2066rotate90DnnuFBc(PointKt.m2046minusybeJwSQ(curveEnd2, j2)));
        if (r2 != null) {
            anchorEnd = r2.m1972unboximpl();
        } else {
            anchorEnd = circleSegmentIntersection;
        }
        return new Cubic(curveStart2, PointKt.m2037divso9K2fw(PointKt.m2047plusybeJwSQ(curveStart2, PointKt.m2049timesso9K2fw(anchorEnd, 2.0f)), 3.0f), anchorEnd, curveEnd, (DefaultConstructorMarker) null);
    }

    /* renamed from: lineIntersection-CBFvKDc  reason: not valid java name */
    private final FloatFloatPair m2054lineIntersectionCBFvKDc(long p02, long d0, long p12, long d12) {
        long j = p02;
        long j2 = d0;
        long rotatedD1 = Utils.m2066rotate90DnnuFBc(d12);
        float den = PointKt.m2039dotProductybeJwSQ(j2, rotatedD1);
        if (Math.abs(den) < 1.0E-4f) {
            return null;
        }
        float num = PointKt.m2039dotProductybeJwSQ(PointKt.m2046minusybeJwSQ(p12, j), rotatedD1);
        if (Math.abs(den) < Math.abs(num) * 1.0E-4f) {
            return null;
        }
        return FloatFloatPair.m1961boximpl(PointKt.m2047plusybeJwSQ(j, PointKt.m2049timesso9K2fw(j2, num / den)));
    }
}
