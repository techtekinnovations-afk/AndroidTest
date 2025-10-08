package androidx.constraintlayout.core.motion.utils;

import java.util.Arrays;

public class ArcCurveFit extends CurveFit {
    public static final int ARC_ABOVE = 5;
    public static final int ARC_BELOW = 4;
    public static final int ARC_START_FLIP = 3;
    public static final int ARC_START_HORIZONTAL = 2;
    public static final int ARC_START_LINEAR = 0;
    public static final int ARC_START_VERTICAL = 1;
    private static final int DOWN_ARC = 4;
    private static final int START_HORIZONTAL = 2;
    private static final int START_LINEAR = 3;
    private static final int START_VERTICAL = 1;
    private static final int UP_ARC = 5;
    Arc[] mArcs;
    private boolean mExtrapolate = true;
    private final double[] mTime;

    public void getPos(double t, double[] v) {
        if (!this.mExtrapolate) {
            if (t < this.mArcs[0].mTime1) {
                t = this.mArcs[0].mTime1;
            }
            if (t > this.mArcs[this.mArcs.length - 1].mTime2) {
                t = this.mArcs[this.mArcs.length - 1].mTime2;
            }
        } else if (t < this.mArcs[0].mTime1) {
            double t0 = this.mArcs[0].mTime1;
            double dt = t - this.mArcs[0].mTime1;
            if (this.mArcs[0].mLinear) {
                v[0] = this.mArcs[0].getLinearX(t0) + (this.mArcs[0].getLinearDX(t0) * dt);
                v[1] = this.mArcs[0].getLinearY(t0) + (this.mArcs[0].getLinearDY(t0) * dt);
                return;
            }
            this.mArcs[0].setPoint(t0);
            v[0] = this.mArcs[0].getX() + (this.mArcs[0].getDX() * dt);
            v[1] = this.mArcs[0].getY() + (this.mArcs[0].getDY() * dt);
            return;
        } else if (t > this.mArcs[this.mArcs.length - 1].mTime2) {
            double t02 = this.mArcs[this.mArcs.length - 1].mTime2;
            double dt2 = t - t02;
            int p = this.mArcs.length - 1;
            if (this.mArcs[p].mLinear) {
                v[0] = this.mArcs[p].getLinearX(t02) + (this.mArcs[p].getLinearDX(t02) * dt2);
                v[1] = this.mArcs[p].getLinearY(t02) + (this.mArcs[p].getLinearDY(t02) * dt2);
                return;
            }
            this.mArcs[p].setPoint(t);
            v[0] = this.mArcs[p].getX() + (this.mArcs[p].getDX() * dt2);
            v[1] = this.mArcs[p].getY() + (this.mArcs[p].getDY() * dt2);
            return;
        }
        int i = 0;
        while (i < this.mArcs.length) {
            if (t > this.mArcs[i].mTime2) {
                i++;
            } else if (this.mArcs[i].mLinear) {
                v[0] = this.mArcs[i].getLinearX(t);
                v[1] = this.mArcs[i].getLinearY(t);
                return;
            } else {
                this.mArcs[i].setPoint(t);
                v[0] = this.mArcs[i].getX();
                v[1] = this.mArcs[i].getY();
                return;
            }
        }
    }

    public void getPos(double t, float[] v) {
        if (this.mExtrapolate) {
            if (t < this.mArcs[0].mTime1) {
                double t0 = this.mArcs[0].mTime1;
                double dt = t - this.mArcs[0].mTime1;
                if (this.mArcs[0].mLinear) {
                    v[0] = (float) (this.mArcs[0].getLinearX(t0) + (this.mArcs[0].getLinearDX(t0) * dt));
                    v[1] = (float) (this.mArcs[0].getLinearY(t0) + (this.mArcs[0].getLinearDY(t0) * dt));
                    return;
                }
                this.mArcs[0].setPoint(t0);
                v[0] = (float) (this.mArcs[0].getX() + (this.mArcs[0].getDX() * dt));
                v[1] = (float) (this.mArcs[0].getY() + (this.mArcs[0].getDY() * dt));
                return;
            } else if (t > this.mArcs[this.mArcs.length - 1].mTime2) {
                double t02 = this.mArcs[this.mArcs.length - 1].mTime2;
                double dt2 = t - t02;
                int p = this.mArcs.length - 1;
                if (this.mArcs[p].mLinear) {
                    v[0] = (float) (this.mArcs[p].getLinearX(t02) + (this.mArcs[p].getLinearDX(t02) * dt2));
                    v[1] = (float) (this.mArcs[p].getLinearY(t02) + (this.mArcs[p].getLinearDY(t02) * dt2));
                    return;
                }
                this.mArcs[p].setPoint(t);
                v[0] = (float) this.mArcs[p].getX();
                v[1] = (float) this.mArcs[p].getY();
                return;
            }
        } else if (t < this.mArcs[0].mTime1) {
            t = this.mArcs[0].mTime1;
        } else if (t > this.mArcs[this.mArcs.length - 1].mTime2) {
            t = this.mArcs[this.mArcs.length - 1].mTime2;
        }
        int i = 0;
        while (i < this.mArcs.length) {
            if (t > this.mArcs[i].mTime2) {
                i++;
            } else if (this.mArcs[i].mLinear) {
                v[0] = (float) this.mArcs[i].getLinearX(t);
                v[1] = (float) this.mArcs[i].getLinearY(t);
                return;
            } else {
                this.mArcs[i].setPoint(t);
                v[0] = (float) this.mArcs[i].getX();
                v[1] = (float) this.mArcs[i].getY();
                return;
            }
        }
    }

    public void getSlope(double t, double[] v) {
        if (t < this.mArcs[0].mTime1) {
            t = this.mArcs[0].mTime1;
        } else if (t > this.mArcs[this.mArcs.length - 1].mTime2) {
            t = this.mArcs[this.mArcs.length - 1].mTime2;
        }
        int i = 0;
        while (i < this.mArcs.length) {
            if (t > this.mArcs[i].mTime2) {
                i++;
            } else if (this.mArcs[i].mLinear) {
                v[0] = this.mArcs[i].getLinearDX(t);
                v[1] = this.mArcs[i].getLinearDY(t);
                return;
            } else {
                this.mArcs[i].setPoint(t);
                v[0] = this.mArcs[i].getDX();
                v[1] = this.mArcs[i].getDY();
                return;
            }
        }
    }

    public double getPos(double t, int j) {
        if (this.mExtrapolate) {
            if (t < this.mArcs[0].mTime1) {
                double t0 = this.mArcs[0].mTime1;
                double dt = t - this.mArcs[0].mTime1;
                if (!this.mArcs[0].mLinear) {
                    this.mArcs[0].setPoint(t0);
                    if (j == 0) {
                        return this.mArcs[0].getX() + (this.mArcs[0].getDX() * dt);
                    }
                    return this.mArcs[0].getY() + (this.mArcs[0].getDY() * dt);
                } else if (j == 0) {
                    return this.mArcs[0].getLinearX(t0) + (this.mArcs[0].getLinearDX(t0) * dt);
                } else {
                    return this.mArcs[0].getLinearY(t0) + (this.mArcs[0].getLinearDY(t0) * dt);
                }
            } else if (t > this.mArcs[this.mArcs.length - 1].mTime2) {
                double t02 = this.mArcs[this.mArcs.length - 1].mTime2;
                double dt2 = t - t02;
                int p = this.mArcs.length - 1;
                if (j == 0) {
                    return this.mArcs[p].getLinearX(t02) + (this.mArcs[p].getLinearDX(t02) * dt2);
                }
                return this.mArcs[p].getLinearY(t02) + (this.mArcs[p].getLinearDY(t02) * dt2);
            }
        } else if (t < this.mArcs[0].mTime1) {
            t = this.mArcs[0].mTime1;
        } else if (t > this.mArcs[this.mArcs.length - 1].mTime2) {
            t = this.mArcs[this.mArcs.length - 1].mTime2;
        }
        int i = 0;
        while (i < this.mArcs.length) {
            if (t > this.mArcs[i].mTime2) {
                i++;
            } else if (!this.mArcs[i].mLinear) {
                this.mArcs[i].setPoint(t);
                if (j == 0) {
                    return this.mArcs[i].getX();
                }
                return this.mArcs[i].getY();
            } else if (j == 0) {
                return this.mArcs[i].getLinearX(t);
            } else {
                return this.mArcs[i].getLinearY(t);
            }
        }
        return Double.NaN;
    }

    public double getSlope(double t, int j) {
        if (t < this.mArcs[0].mTime1) {
            t = this.mArcs[0].mTime1;
        }
        if (t > this.mArcs[this.mArcs.length - 1].mTime2) {
            t = this.mArcs[this.mArcs.length - 1].mTime2;
        }
        int i = 0;
        while (i < this.mArcs.length) {
            if (t > this.mArcs[i].mTime2) {
                i++;
            } else if (!this.mArcs[i].mLinear) {
                this.mArcs[i].setPoint(t);
                if (j == 0) {
                    return this.mArcs[i].getDX();
                }
                return this.mArcs[i].getDY();
            } else if (j == 0) {
                return this.mArcs[i].getLinearDX(t);
            } else {
                return this.mArcs[i].getLinearDY(t);
            }
        }
        return Double.NaN;
    }

    public double[] getTimePoints() {
        return this.mTime;
    }

    public ArcCurveFit(int[] arcModes, double[] time, double[][] y) {
        double[] dArr = time;
        this.mTime = dArr;
        this.mArcs = new Arc[(dArr.length - 1)];
        int mode = 1;
        int last = 1;
        int i = 0;
        while (i < this.mArcs.length) {
            int mode2 = 2;
            switch (arcModes[i]) {
                case 0:
                    mode2 = 3;
                    break;
                case 1:
                    last = 1;
                    mode2 = 1;
                    break;
                case 2:
                    last = 2;
                    break;
                case 3:
                    mode2 = last != 1 ? 1 : mode2;
                    last = mode2;
                    break;
                case 4:
                    mode2 = 4;
                    break;
                case 5:
                    mode2 = 5;
                    break;
                default:
                    mode2 = mode;
                    break;
            }
            this.mArcs[i] = new Arc(mode2, dArr[i], dArr[i + 1], y[i][0], y[i][1], y[i + 1][0], y[i + 1][1]);
            i++;
            mode = mode2;
        }
    }

    private static class Arc {
        private static final double EPSILON = 0.001d;
        private static final String TAG = "Arc";
        private static double[] sOurPercent = new double[91];
        double mArcDistance;
        double mArcVelocity;
        double mEllipseA;
        double mEllipseB;
        double mEllipseCenterX;
        double mEllipseCenterY;
        boolean mLinear = false;
        double[] mLut;
        double mOneOverDeltaTime;
        double mTime1;
        double mTime2;
        double mTmpCosAngle;
        double mTmpSinAngle;
        boolean mVertical;
        double mX1;
        double mX2;
        double mY1;
        double mY2;

        Arc(int mode, double t1, double t2, double x1, double y1, double x2, double y2) {
            double d;
            double d2;
            double d3;
            int i = mode;
            boolean z = false;
            double dx = x2 - x1;
            double dy = y2 - y1;
            int i2 = 1;
            switch (i) {
                case 1:
                    this.mVertical = true;
                    break;
                case 4:
                    this.mVertical = dy > 0.0d ? true : z;
                    break;
                case 5:
                    this.mVertical = dy < 0.0d ? true : z;
                    break;
                default:
                    this.mVertical = false;
                    break;
            }
            this.mTime1 = t1;
            this.mTime2 = t2;
            this.mOneOverDeltaTime = 1.0d / (this.mTime2 - this.mTime1);
            if (3 == i) {
                this.mLinear = true;
            }
            if (this.mLinear || Math.abs(dx) < EPSILON) {
                d3 = x1;
                d2 = y1;
                d = x2;
            } else if (Math.abs(dy) < EPSILON) {
                d3 = x1;
                d2 = y1;
                d = x2;
            } else {
                this.mLut = new double[101];
                this.mEllipseA = ((double) (this.mVertical ? -1 : 1)) * dx;
                this.mEllipseB = ((double) (!this.mVertical ? -1 : i2)) * dy;
                this.mEllipseCenterX = this.mVertical ? x2 : x1;
                this.mEllipseCenterY = this.mVertical ? y1 : y2;
                double d4 = y1;
                double d5 = x2;
                buildTable(x1, d4, d5, y2);
                double d6 = d5;
                double d7 = d4;
                this.mArcVelocity = this.mOneOverDeltaTime * this.mArcDistance;
                return;
            }
            this.mLinear = true;
            this.mX1 = d3;
            this.mX2 = d;
            this.mY1 = d2;
            this.mY2 = y2;
            this.mArcDistance = Math.hypot(dy, dx);
            this.mArcVelocity = this.mOneOverDeltaTime * this.mArcDistance;
            this.mEllipseCenterX = dx / (this.mTime2 - this.mTime1);
            this.mEllipseCenterY = dy / (this.mTime2 - this.mTime1);
        }

        /* access modifiers changed from: package-private */
        public void setPoint(double time) {
            double angle = lookup((this.mVertical ? this.mTime2 - time : time - this.mTime1) * this.mOneOverDeltaTime) * 1.5707963267948966d;
            this.mTmpSinAngle = Math.sin(angle);
            this.mTmpCosAngle = Math.cos(angle);
        }

        /* access modifiers changed from: package-private */
        public double getX() {
            return this.mEllipseCenterX + (this.mEllipseA * this.mTmpSinAngle);
        }

        /* access modifiers changed from: package-private */
        public double getY() {
            return this.mEllipseCenterY + (this.mEllipseB * this.mTmpCosAngle);
        }

        /* access modifiers changed from: package-private */
        public double getDX() {
            double vx = this.mEllipseA * this.mTmpCosAngle;
            double norm = this.mArcVelocity / Math.hypot(vx, (-this.mEllipseB) * this.mTmpSinAngle);
            return this.mVertical ? (-vx) * norm : vx * norm;
        }

        /* access modifiers changed from: package-private */
        public double getDY() {
            double vx = this.mEllipseA * this.mTmpCosAngle;
            double vy = (-this.mEllipseB) * this.mTmpSinAngle;
            double norm = this.mArcVelocity / Math.hypot(vx, vy);
            return this.mVertical ? (-vy) * norm : vy * norm;
        }

        public double getLinearX(double t) {
            return this.mX1 + ((this.mX2 - this.mX1) * (t - this.mTime1) * this.mOneOverDeltaTime);
        }

        public double getLinearY(double t) {
            return this.mY1 + ((this.mY2 - this.mY1) * (t - this.mTime1) * this.mOneOverDeltaTime);
        }

        public double getLinearDX(double t) {
            return this.mEllipseCenterX;
        }

        public double getLinearDY(double t) {
            return this.mEllipseCenterY;
        }

        /* access modifiers changed from: package-private */
        public double lookup(double v) {
            if (v <= 0.0d) {
                return 0.0d;
            }
            if (v >= 1.0d) {
                return 1.0d;
            }
            double pos = ((double) (this.mLut.length - 1)) * v;
            int iv = (int) pos;
            return this.mLut[iv] + ((this.mLut[iv + 1] - this.mLut[iv]) * (pos - ((double) ((int) pos))));
        }

        private void buildTable(double x1, double y1, double x2, double y2) {
            int i;
            double b;
            double a;
            double a2 = x2 - x1;
            double b2 = y1 - y2;
            double lx = 0.0d;
            double ly = 0.0d;
            double dist = 0.0d;
            int i2 = 0;
            while (i2 < sOurPercent.length) {
                double angle = Math.toRadians((((double) i2) * 90.0d) / ((double) (sOurPercent.length - 1)));
                double px = a2 * Math.sin(angle);
                double py = b2 * Math.cos(angle);
                if (i2 > 0) {
                    a = a2;
                    b = b2;
                    dist += Math.hypot(px - lx, py - ly);
                    sOurPercent[i2] = dist;
                } else {
                    a = a2;
                    b = b2;
                }
                lx = px;
                ly = py;
                i2++;
                a2 = a;
                b2 = b;
            }
            double d = b2;
            this.mArcDistance = dist;
            for (int i3 = 0; i3 < sOurPercent.length; i3++) {
                double[] dArr = sOurPercent;
                dArr[i3] = dArr[i3] / dist;
            }
            int i4 = 0;
            while (i4 < this.mLut.length) {
                double pos = ((double) i4) / ((double) (this.mLut.length - 1));
                int index = Arrays.binarySearch(sOurPercent, pos);
                if (index >= 0) {
                    this.mLut[i4] = ((double) index) / ((double) (sOurPercent.length - 1));
                    i = i4;
                } else if (index == -1) {
                    this.mLut[i4] = 0.0d;
                    i = i4;
                } else {
                    int p1 = (-index) - 2;
                    i = i4;
                    double d2 = pos;
                    this.mLut[i] = (((double) p1) + ((pos - sOurPercent[p1]) / (sOurPercent[(-index) - 1] - sOurPercent[p1]))) / ((double) (sOurPercent.length - 1));
                }
                i4 = i + 1;
            }
        }
    }
}
