package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b#\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a6\u0010\b\u001a\u00060\u0002j\u0002`\u00032\n\u0010\t\u001a\u00060\u0002j\u0002`\u00032\n\u0010\n\u001a\u00060\u0002j\u0002`\u00032\u0006\u0010\u000b\u001a\u00020\u0001H\u0000ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a&\u0010\u000e\u001a\u00020\u000f*\u00060\u0002j\u0002`\u00032\n\u0010\u0010\u001a\u00060\u0002j\u0002`\u0003H\u0000ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0012\u001a2\u0010\u0013\u001a\u00060\u0002j\u0002`\u0003*\u00060\u0002j\u0002`\u00032\b\b\u0002\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0006\u001a\u00020\u0001H\u0000ø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015\u001a'\u0010\u0016\u001a\u00060\u0002j\u0002`\u0003*\u00060\u0002j\u0002`\u00032\u0006\u0010\u0017\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0004\b\u0018\u0010\u0019\u001a&\u0010\u001a\u001a\u00020\u0001*\u00060\u0002j\u0002`\u00032\n\u0010\u0010\u001a\u00060\u0002j\u0002`\u0003H\u0000ø\u0001\u0000¢\u0006\u0004\b\u001b\u0010\u001c\u001a*\u0010\u001a\u001a\u00020\u0001*\u00060\u0002j\u0002`\u00032\u0006\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u0001H\u0000ø\u0001\u0000¢\u0006\u0004\b\u001f\u0010 \u001a\u001e\u0010!\u001a\u00060\u0002j\u0002`\u0003*\u00060\u0002j\u0002`\u0003H\u0000ø\u0001\u0000¢\u0006\u0004\b\"\u0010#\u001a\u001a\u0010$\u001a\u00020\u0001*\u00060\u0002j\u0002`\u0003H\u0000ø\u0001\u0000¢\u0006\u0004\b%\u0010\u0005\u001a\u001a\u0010&\u001a\u00020\u0001*\u00060\u0002j\u0002`\u0003H\u0000ø\u0001\u0000¢\u0006\u0004\b'\u0010\u0005\u001a+\u0010(\u001a\u00060\u0002j\u0002`\u0003*\u00060\u0002j\u0002`\u00032\n\u0010\u0010\u001a\u00060\u0002j\u0002`\u0003H\u0002ø\u0001\u0000¢\u0006\u0004\b)\u0010*\u001a+\u0010+\u001a\u00060\u0002j\u0002`\u0003*\u00060\u0002j\u0002`\u00032\n\u0010\u0010\u001a\u00060\u0002j\u0002`\u0003H\u0002ø\u0001\u0000¢\u0006\u0004\b,\u0010*\u001a'\u0010-\u001a\u00060\u0002j\u0002`\u0003*\u00060\u0002j\u0002`\u00032\u0006\u0010\u0017\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0004\b.\u0010\u0019\u001a'\u0010/\u001a\u00060\u0002j\u0002`\u0003*\u00060\u0002j\u0002`\u00032\u0006\u0010\u0017\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0004\b0\u0010\u0019\u001a&\u00101\u001a\u00060\u0002j\u0002`\u0003*\u00060\u0002j\u0002`\u00032\u0006\u00102\u001a\u000203H\u0000ø\u0001\u0000¢\u0006\u0004\b4\u00105\u001a\u001f\u00106\u001a\u00060\u0002j\u0002`\u0003*\u00060\u0002j\u0002`\u0003H\u0002ø\u0001\u0000¢\u0006\u0004\b7\u0010#\"\u001c\u0010\u0000\u001a\u00020\u0001*\u00060\u0002j\u0002`\u00038@X\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u001c\u0010\u0006\u001a\u00020\u0001*\u00060\u0002j\u0002`\u00038@X\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005*\f\b\u0000\u00108\"\u00020\u00022\u00020\u0002\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u00069"}, d2 = {"x", "", "Landroidx/collection/FloatFloatPair;", "Landroidx/graphics/shapes/Point;", "getX-DnnuFBc", "(J)F", "y", "getY-DnnuFBc", "interpolate", "start", "stop", "fraction", "interpolate-dLqxh1s", "(JJF)J", "clockwise", "", "other", "clockwise-ybeJwSQ", "(JJ)Z", "copy", "copy-5P9i7ZU", "(JFF)J", "div", "operand", "div-so9K2fw", "(JF)J", "dotProduct", "dotProduct-ybeJwSQ", "(JJ)F", "otherX", "otherY", "dotProduct-5P9i7ZU", "(JFF)F", "getDirection", "getDirection-DnnuFBc", "(J)J", "getDistance", "getDistance-DnnuFBc", "getDistanceSquared", "getDistanceSquared-DnnuFBc", "minus", "minus-ybeJwSQ", "(JJ)J", "plus", "plus-ybeJwSQ", "rem", "rem-so9K2fw", "times", "times-so9K2fw", "transformed", "f", "Landroidx/graphics/shapes/PointTransformer;", "transformed-so9K2fw", "(JLandroidx/graphics/shapes/PointTransformer;)J", "unaryMinus", "unaryMinus-DnnuFBc", "Point", "graphics-shapes_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* compiled from: Point.kt */
public final class PointKt {
    /* renamed from: getX-DnnuFBc  reason: not valid java name */
    public static final float m2043getXDnnuFBc(long $this$x) {
        return Float.intBitsToFloat((int) ($this$x >> 32));
    }

    /* renamed from: getY-DnnuFBc  reason: not valid java name */
    public static final float m2044getYDnnuFBc(long $this$y) {
        return Float.intBitsToFloat((int) (4294967295L & $this$y));
    }

    /* renamed from: copy-5P9i7ZU  reason: not valid java name */
    public static final long m2035copy5P9i7ZU(long $this$copy_u2d5P9i7ZU, float x, float y) {
        return FloatFloatPair.m1964constructorimpl(x, y);
    }

    /* renamed from: copy-5P9i7ZU$default  reason: not valid java name */
    public static /* synthetic */ long m2036copy5P9i7ZU$default(long j, float f, float f2, int i, Object obj) {
        if ((i & 1) != 0) {
            f = Float.intBitsToFloat((int) (j >> 32));
        }
        if ((i & 2) != 0) {
            f2 = Float.intBitsToFloat((int) (4294967295L & j));
        }
        return m2035copy5P9i7ZU(j, f, f2);
    }

    /* renamed from: getDistance-DnnuFBc  reason: not valid java name */
    public static final float m2041getDistanceDnnuFBc(long $this$getDistance_u2dDnnuFBc) {
        return (float) Math.sqrt((double) ((m2043getXDnnuFBc($this$getDistance_u2dDnnuFBc) * m2043getXDnnuFBc($this$getDistance_u2dDnnuFBc)) + (m2044getYDnnuFBc($this$getDistance_u2dDnnuFBc) * m2044getYDnnuFBc($this$getDistance_u2dDnnuFBc))));
    }

    /* renamed from: getDistanceSquared-DnnuFBc  reason: not valid java name */
    public static final float m2042getDistanceSquaredDnnuFBc(long $this$getDistanceSquared_u2dDnnuFBc) {
        return (m2043getXDnnuFBc($this$getDistanceSquared_u2dDnnuFBc) * m2043getXDnnuFBc($this$getDistanceSquared_u2dDnnuFBc)) + (m2044getYDnnuFBc($this$getDistanceSquared_u2dDnnuFBc) * m2044getYDnnuFBc($this$getDistanceSquared_u2dDnnuFBc));
    }

    /* renamed from: dotProduct-ybeJwSQ  reason: not valid java name */
    public static final float m2039dotProductybeJwSQ(long $this$dotProduct_u2dybeJwSQ, long other) {
        return (m2043getXDnnuFBc($this$dotProduct_u2dybeJwSQ) * m2043getXDnnuFBc(other)) + (m2044getYDnnuFBc($this$dotProduct_u2dybeJwSQ) * m2044getYDnnuFBc(other));
    }

    /* renamed from: dotProduct-5P9i7ZU  reason: not valid java name */
    public static final float m2038dotProduct5P9i7ZU(long $this$dotProduct_u2d5P9i7ZU, float otherX, float otherY) {
        return (m2043getXDnnuFBc($this$dotProduct_u2d5P9i7ZU) * otherX) + (m2044getYDnnuFBc($this$dotProduct_u2d5P9i7ZU) * otherY);
    }

    /* renamed from: clockwise-ybeJwSQ  reason: not valid java name */
    public static final boolean m2034clockwiseybeJwSQ(long $this$clockwise_u2dybeJwSQ, long other) {
        return (m2043getXDnnuFBc($this$clockwise_u2dybeJwSQ) * m2044getYDnnuFBc(other)) - (m2044getYDnnuFBc($this$clockwise_u2dybeJwSQ) * m2043getXDnnuFBc(other)) > 0.0f;
    }

    /* renamed from: getDirection-DnnuFBc  reason: not valid java name */
    public static final long m2040getDirectionDnnuFBc(long $this$getDirection_u2dDnnuFBc) {
        long $this$getDirection_DnnuFBc_u24lambda_u241 = $this$getDirection_u2dDnnuFBc;
        float d = m2041getDistanceDnnuFBc($this$getDirection_DnnuFBc_u24lambda_u241);
        if (d > 0.0f) {
            return m2037divso9K2fw($this$getDirection_DnnuFBc_u24lambda_u241, d);
        }
        throw new IllegalArgumentException("Can't get the direction of a 0-length vector".toString());
    }

    /* renamed from: unaryMinus-DnnuFBc  reason: not valid java name */
    public static final long m2051unaryMinusDnnuFBc(long $this$unaryMinus_u2dDnnuFBc) {
        return FloatFloatPair.m1964constructorimpl(-m2043getXDnnuFBc($this$unaryMinus_u2dDnnuFBc), -m2044getYDnnuFBc($this$unaryMinus_u2dDnnuFBc));
    }

    /* renamed from: minus-ybeJwSQ  reason: not valid java name */
    public static final long m2046minusybeJwSQ(long $this$minus_u2dybeJwSQ, long other) {
        return FloatFloatPair.m1964constructorimpl(m2043getXDnnuFBc($this$minus_u2dybeJwSQ) - m2043getXDnnuFBc(other), m2044getYDnnuFBc($this$minus_u2dybeJwSQ) - m2044getYDnnuFBc(other));
    }

    /* renamed from: plus-ybeJwSQ  reason: not valid java name */
    public static final long m2047plusybeJwSQ(long $this$plus_u2dybeJwSQ, long other) {
        return FloatFloatPair.m1964constructorimpl(m2043getXDnnuFBc($this$plus_u2dybeJwSQ) + m2043getXDnnuFBc(other), m2044getYDnnuFBc($this$plus_u2dybeJwSQ) + m2044getYDnnuFBc(other));
    }

    /* renamed from: times-so9K2fw  reason: not valid java name */
    public static final long m2049timesso9K2fw(long $this$times_u2dso9K2fw, float operand) {
        return FloatFloatPair.m1964constructorimpl(m2043getXDnnuFBc($this$times_u2dso9K2fw) * operand, m2044getYDnnuFBc($this$times_u2dso9K2fw) * operand);
    }

    /* renamed from: div-so9K2fw  reason: not valid java name */
    public static final long m2037divso9K2fw(long $this$div_u2dso9K2fw, float operand) {
        return FloatFloatPair.m1964constructorimpl(m2043getXDnnuFBc($this$div_u2dso9K2fw) / operand, m2044getYDnnuFBc($this$div_u2dso9K2fw) / operand);
    }

    /* renamed from: rem-so9K2fw  reason: not valid java name */
    public static final long m2048remso9K2fw(long $this$rem_u2dso9K2fw, float operand) {
        return FloatFloatPair.m1964constructorimpl(m2043getXDnnuFBc($this$rem_u2dso9K2fw) % operand, m2044getYDnnuFBc($this$rem_u2dso9K2fw) % operand);
    }

    /* renamed from: interpolate-dLqxh1s  reason: not valid java name */
    public static final long m2045interpolatedLqxh1s(long start, long stop, float fraction) {
        return FloatFloatPair.m1964constructorimpl(Utils.interpolate(m2043getXDnnuFBc(start), m2043getXDnnuFBc(stop), fraction), Utils.interpolate(m2044getYDnnuFBc(start), m2044getYDnnuFBc(stop), fraction));
    }

    /* renamed from: transformed-so9K2fw  reason: not valid java name */
    public static final long m2050transformedso9K2fw(long $this$transformed_u2dso9K2fw, PointTransformer f) {
        Intrinsics.checkNotNullParameter(f, "f");
        long result = f.m2052transformXgqJiTY(m2043getXDnnuFBc($this$transformed_u2dso9K2fw), m2044getYDnnuFBc($this$transformed_u2dso9K2fw));
        return FloatFloatPair.m1964constructorimpl(Float.intBitsToFloat((int) (result >> 32)), Float.intBitsToFloat((int) (4294967295L & result)));
    }
}
