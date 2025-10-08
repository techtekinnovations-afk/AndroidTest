package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import com.google.android.material.progressindicator.BaseProgressIndicatorSpec;
import java.util.Arrays;

abstract class DrawingDelegate<S extends BaseProgressIndicatorSpec> {
    static final float WAVE_SMOOTHNESS = 0.48f;
    final PathMeasure activePathMeasure = new PathMeasure(this.cachedActivePath, false);
    final Path cachedActivePath = new Path();
    final Path displayedActivePath = new Path();
    S spec;
    final Matrix transform;

    /* access modifiers changed from: package-private */
    public abstract void adjustCanvas(Canvas canvas, Rect rect, float f, boolean z, boolean z2);

    /* access modifiers changed from: package-private */
    public abstract void drawStopIndicator(Canvas canvas, Paint paint, int i, int i2);

    /* access modifiers changed from: package-private */
    public abstract void fillIndicator(Canvas canvas, Paint paint, ActiveIndicator activeIndicator, int i);

    /* access modifiers changed from: package-private */
    public abstract void fillTrack(Canvas canvas, Paint paint, float f, float f2, int i, int i2, int i3);

    /* access modifiers changed from: package-private */
    public abstract int getPreferredHeight();

    /* access modifiers changed from: package-private */
    public abstract int getPreferredWidth();

    /* access modifiers changed from: package-private */
    public abstract void invalidateCachedPaths();

    public DrawingDelegate(S spec2) {
        this.spec = spec2;
        this.transform = new Matrix();
    }

    /* access modifiers changed from: package-private */
    public void validateSpecAndAdjustCanvas(Canvas canvas, Rect bounds, float trackThicknessFraction, boolean isShowing, boolean isHiding) {
        this.spec.validateSpec();
        adjustCanvas(canvas, bounds, trackThicknessFraction, isShowing, isHiding);
    }

    /* access modifiers changed from: package-private */
    public float vectorToCanvasRotation(float[] vector) {
        return (float) Math.toDegrees(Math.atan2((double) vector[1], (double) vector[0]));
    }

    protected static class ActiveIndicator {
        float amplitudeFraction = 1.0f;
        int color;
        float endFraction;
        int gapSize;
        boolean isDeterminate;
        float phaseFraction;
        float rotationDegree;
        float startFraction;

        protected ActiveIndicator() {
        }
    }

    protected class PathPoint {
        float[] posVec;
        float[] tanVec;
        final Matrix transform;

        public PathPoint() {
            this.posVec = new float[2];
            this.tanVec = new float[2];
            this.tanVec[0] = 1.0f;
            this.transform = new Matrix();
        }

        public PathPoint(DrawingDelegate this$02, DrawingDelegate<S>.PathPoint other) {
            this(other.posVec, other.tanVec);
        }

        public PathPoint(float[] pos, float[] tan) {
            this.posVec = new float[2];
            this.tanVec = new float[2];
            System.arraycopy(pos, 0, this.posVec, 0, 2);
            System.arraycopy(tan, 0, this.tanVec, 0, 2);
            this.transform = new Matrix();
        }

        /* access modifiers changed from: package-private */
        public void translate(float x, float y) {
            float[] fArr = this.posVec;
            fArr[0] = fArr[0] + x;
            float[] fArr2 = this.posVec;
            fArr2[1] = fArr2[1] + y;
        }

        /* access modifiers changed from: package-private */
        public void scale(float x, float y) {
            float[] fArr = this.posVec;
            fArr[0] = fArr[0] * x;
            float[] fArr2 = this.posVec;
            fArr2[1] = fArr2[1] * y;
            float[] fArr3 = this.tanVec;
            fArr3[0] = fArr3[0] * x;
            float[] fArr4 = this.tanVec;
            fArr4[1] = fArr4[1] * y;
        }

        /* access modifiers changed from: package-private */
        public float distance(DrawingDelegate<S>.PathPoint other) {
            return (float) Math.hypot((double) (other.posVec[0] - this.posVec[0]), (double) (other.posVec[1] - this.posVec[1]));
        }

        /* access modifiers changed from: package-private */
        public void moveAlong(float distance) {
            float angle = (float) Math.atan2((double) this.tanVec[1], (double) this.tanVec[0]);
            this.posVec[0] = (float) (((double) this.posVec[0]) + (((double) distance) * Math.cos((double) angle)));
            this.posVec[1] = (float) (((double) this.posVec[1]) + (((double) distance) * Math.sin((double) angle)));
        }

        /* access modifiers changed from: package-private */
        public void moveAcross(float distance) {
            float angle = (float) (Math.atan2((double) this.tanVec[1], (double) this.tanVec[0]) + 1.5707963267948966d);
            this.posVec[0] = (float) (((double) this.posVec[0]) + (((double) distance) * Math.cos((double) angle)));
            this.posVec[1] = (float) (((double) this.posVec[1]) + (((double) distance) * Math.sin((double) angle)));
        }

        public void rotate(float rotationDegrees) {
            this.transform.reset();
            this.transform.setRotate(rotationDegrees);
            this.transform.mapPoints(this.posVec);
            this.transform.mapPoints(this.tanVec);
        }

        public void reset() {
            Arrays.fill(this.posVec, 0.0f);
            Arrays.fill(this.tanVec, 0.0f);
            this.tanVec[0] = 1.0f;
            this.transform.reset();
        }
    }
}
