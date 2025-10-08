package com.google.common.math;

import com.google.common.base.Preconditions;
import java.lang.Comparable;
import java.lang.Number;
import java.math.RoundingMode;

@ElementTypesAreNonnullByDefault
abstract class ToDoubleRounder<X extends Number & Comparable<X>> {
    /* access modifiers changed from: package-private */
    public abstract X minus(X x, X x2);

    /* access modifiers changed from: package-private */
    public abstract double roundToDoubleArbitrarily(X x);

    /* access modifiers changed from: package-private */
    public abstract int sign(X x);

    /* access modifiers changed from: package-private */
    public abstract X toX(double d, RoundingMode roundingMode);

    ToDoubleRounder() {
    }

    /* access modifiers changed from: package-private */
    public final double roundToDouble(X x, RoundingMode mode) {
        double roundCeilingAsDouble;
        X roundFloor;
        X roundCeiling;
        double roundFloorAsDouble;
        X x2 = x;
        Preconditions.checkNotNull(x2, "x");
        RoundingMode roundingMode = mode;
        Preconditions.checkNotNull(roundingMode, "mode");
        double roundArbitrarily = roundToDoubleArbitrarily(x);
        if (Double.isInfinite(roundArbitrarily)) {
            switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                    return ((double) sign(x)) * Double.MAX_VALUE;
                case 5:
                    if (roundArbitrarily == Double.POSITIVE_INFINITY) {
                        return Double.MAX_VALUE;
                    }
                    return Double.NEGATIVE_INFINITY;
                case 6:
                    if (roundArbitrarily == Double.POSITIVE_INFINITY) {
                        return Double.POSITIVE_INFINITY;
                    }
                    return -1.7976931348623157E308d;
                case 7:
                    return roundArbitrarily;
                case 8:
                    throw new ArithmeticException(x2 + " cannot be represented precisely as a double");
            }
        }
        X roundArbitrarilyAsX = toX(roundArbitrarily, RoundingMode.UNNECESSARY);
        int cmpXToRoundArbitrarily = ((Comparable) x2).compareTo(roundArbitrarilyAsX);
        switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
            case 1:
                if (sign(x) < 0) {
                    return cmpXToRoundArbitrarily <= 0 ? roundArbitrarily : Math.nextUp(roundArbitrarily);
                }
                if (cmpXToRoundArbitrarily >= 0) {
                    return roundArbitrarily;
                }
                return DoubleUtils.nextDown(roundArbitrarily);
            case 2:
            case 3:
            case 4:
                if (cmpXToRoundArbitrarily >= 0) {
                    roundFloorAsDouble = roundArbitrarily;
                    roundFloor = roundArbitrarilyAsX;
                    roundCeilingAsDouble = Math.nextUp(roundArbitrarily);
                    if (roundCeilingAsDouble == Double.POSITIVE_INFINITY) {
                        return roundFloorAsDouble;
                    }
                    roundCeiling = toX(roundCeilingAsDouble, RoundingMode.CEILING);
                } else {
                    roundCeilingAsDouble = roundArbitrarily;
                    roundCeiling = roundArbitrarilyAsX;
                    roundFloorAsDouble = DoubleUtils.nextDown(roundArbitrarily);
                    if (roundFloorAsDouble == Double.NEGATIVE_INFINITY) {
                        return roundCeilingAsDouble;
                    }
                    roundFloor = toX(roundFloorAsDouble, RoundingMode.FLOOR);
                }
                int diff = ((Comparable) minus(x2, roundFloor)).compareTo(minus(roundCeiling, x2));
                if (diff < 0) {
                    return roundFloorAsDouble;
                }
                if (diff > 0) {
                    return roundCeilingAsDouble;
                }
                switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[roundingMode.ordinal()]) {
                    case 2:
                        if ((Double.doubleToRawLongBits(roundFloorAsDouble) & 1) == 0) {
                            return roundFloorAsDouble;
                        }
                        return roundCeilingAsDouble;
                    case 3:
                        return sign(x) >= 0 ? roundFloorAsDouble : roundCeilingAsDouble;
                    case 4:
                        return sign(x) >= 0 ? roundCeilingAsDouble : roundFloorAsDouble;
                    default:
                        int i = diff;
                        throw new AssertionError("impossible");
                }
            case 5:
                if (cmpXToRoundArbitrarily >= 0) {
                    return roundArbitrarily;
                }
                return DoubleUtils.nextDown(roundArbitrarily);
            case 6:
                return cmpXToRoundArbitrarily <= 0 ? roundArbitrarily : Math.nextUp(roundArbitrarily);
            case 7:
                if (sign(x) >= 0) {
                    return cmpXToRoundArbitrarily <= 0 ? roundArbitrarily : Math.nextUp(roundArbitrarily);
                }
                if (cmpXToRoundArbitrarily >= 0) {
                    return roundArbitrarily;
                }
                return DoubleUtils.nextDown(roundArbitrarily);
            case 8:
                MathPreconditions.checkRoundingUnnecessary(cmpXToRoundArbitrarily == 0);
                return roundArbitrarily;
            default:
                throw new AssertionError("impossible");
        }
    }

    /* renamed from: com.google.common.math.ToDoubleRounder$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$java$math$RoundingMode = new int[RoundingMode.values().length];

        static {
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.DOWN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_EVEN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_DOWN.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_UP.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.FLOOR.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.CEILING.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.UP.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.UNNECESSARY.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }
}
