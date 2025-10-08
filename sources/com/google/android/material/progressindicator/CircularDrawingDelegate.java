package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.Pair;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.math.MathUtils;
import com.google.android.material.progressindicator.DrawingDelegate;
import java.util.ArrayList;
import java.util.List;

final class CircularDrawingDelegate extends DrawingDelegate<CircularProgressIndicatorSpec> {
    private static final float QUARTER_CIRCLE_CONTROL_HANDLE_LENGTH = 0.5522848f;
    private static final float ROUND_CAP_RAMP_DOWN_THRESHHOLD = 0.01f;
    private float adjustedRadius;
    private float adjustedWavelength;
    private final RectF arcBounds = new RectF();
    private float cachedAmplitude;
    private float cachedRadius;
    private int cachedWavelength;
    private float displayedAmplitude;
    private float displayedCornerRadius;
    private float displayedTrackThickness;
    private boolean drawingDeterminateIndicator;
    private final Pair<DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint, DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint> endPoints = new Pair<>(new DrawingDelegate.PathPoint(), new DrawingDelegate.PathPoint());
    private float totalTrackLengthFraction;

    CircularDrawingDelegate(CircularProgressIndicatorSpec spec) {
        super(spec);
    }

    /* access modifiers changed from: package-private */
    public int getPreferredWidth() {
        return getSize();
    }

    /* access modifiers changed from: package-private */
    public int getPreferredHeight() {
        return getSize();
    }

    /* access modifiers changed from: package-private */
    public void adjustCanvas(Canvas canvas, Rect bounds, float trackThicknessFraction, boolean isShowing, boolean isHiding) {
        float scaleX = ((float) bounds.width()) / ((float) getPreferredWidth());
        float scaleY = ((float) bounds.height()) / ((float) getPreferredHeight());
        float outerRadiusWithInset = (((float) ((CircularProgressIndicatorSpec) this.spec).indicatorSize) / 2.0f) + ((float) ((CircularProgressIndicatorSpec) this.spec).indicatorInset);
        canvas.translate(((float) bounds.left) + (outerRadiusWithInset * scaleX), ((float) bounds.top) + (outerRadiusWithInset * scaleY));
        canvas.rotate(-90.0f);
        canvas.scale(scaleX, scaleY);
        if (((CircularProgressIndicatorSpec) this.spec).indicatorDirection != 0) {
            canvas.scale(1.0f, -1.0f);
            if (Build.VERSION.SDK_INT == 29) {
                canvas.rotate(0.1f);
            }
        }
        canvas.clipRect(-outerRadiusWithInset, -outerRadiusWithInset, outerRadiusWithInset, outerRadiusWithInset);
        this.displayedTrackThickness = ((float) ((CircularProgressIndicatorSpec) this.spec).trackThickness) * trackThicknessFraction;
        this.displayedCornerRadius = ((float) Math.min(((CircularProgressIndicatorSpec) this.spec).trackThickness / 2, ((CircularProgressIndicatorSpec) this.spec).getTrackCornerRadiusInPx())) * trackThicknessFraction;
        this.displayedAmplitude = ((float) ((CircularProgressIndicatorSpec) this.spec).waveAmplitude) * trackThicknessFraction;
        this.adjustedRadius = ((float) (((CircularProgressIndicatorSpec) this.spec).indicatorSize - ((CircularProgressIndicatorSpec) this.spec).trackThickness)) / 2.0f;
        if (isShowing || isHiding) {
            float deltaRadius = ((1.0f - trackThicknessFraction) * ((float) ((CircularProgressIndicatorSpec) this.spec).trackThickness)) / 2.0f;
            if ((isShowing && ((CircularProgressIndicatorSpec) this.spec).showAnimationBehavior == 2) || (isHiding && ((CircularProgressIndicatorSpec) this.spec).hideAnimationBehavior == 1)) {
                this.adjustedRadius += deltaRadius;
            } else if ((isShowing && ((CircularProgressIndicatorSpec) this.spec).showAnimationBehavior == 1) || (isHiding && ((CircularProgressIndicatorSpec) this.spec).hideAnimationBehavior == 2)) {
                this.adjustedRadius -= deltaRadius;
            }
        }
        if (!isHiding || ((CircularProgressIndicatorSpec) this.spec).hideAnimationBehavior != 3) {
            this.totalTrackLengthFraction = 1.0f;
        } else {
            this.totalTrackLengthFraction = trackThicknessFraction;
        }
    }

    /* access modifiers changed from: package-private */
    public void fillIndicator(Canvas canvas, Paint paint, DrawingDelegate.ActiveIndicator activeIndicator, int drawableAlpha) {
        DrawingDelegate.ActiveIndicator activeIndicator2 = activeIndicator;
        int color = MaterialColors.compositeARGBWithAlpha(activeIndicator2.color, drawableAlpha);
        canvas.save();
        canvas.rotate(activeIndicator2.rotationDegree);
        this.drawingDeterminateIndicator = activeIndicator2.isDeterminate;
        drawArc(canvas, paint, activeIndicator2.startFraction, activeIndicator2.endFraction, color, activeIndicator2.gapSize, activeIndicator2.gapSize, activeIndicator2.amplitudeFraction, activeIndicator2.phaseFraction, true);
        canvas.restore();
    }

    /* access modifiers changed from: package-private */
    public void fillTrack(Canvas canvas, Paint paint, float startFraction, float endFraction, int color, int drawableAlpha, int gapSize) {
        int color2 = MaterialColors.compositeARGBWithAlpha(color, drawableAlpha);
        this.drawingDeterminateIndicator = false;
        drawArc(canvas, paint, startFraction, endFraction, color2, gapSize, gapSize, 0.0f, 0.0f, false);
    }

    private void drawArc(Canvas canvas, Paint paint, float startFraction, float endFraction, int paintColor, int startGapSize, int endGapSize, float amplitudeFraction, float phaseFraction, boolean shouldDrawActiveIndicator) {
        float f;
        float startFraction2;
        float f2;
        Paint paint2;
        Canvas canvas2;
        CircularDrawingDelegate circularDrawingDelegate;
        float shrinkRatio;
        float blockWidth;
        if (endFraction >= startFraction) {
            f = endFraction - startFraction;
        } else {
            f = (endFraction + 1.0f) - startFraction;
        }
        float arcFraction = f;
        float startFraction3 = startFraction % 1.0f;
        if (startFraction3 < 0.0f) {
            startFraction2 = startFraction3 + 1.0f;
        } else {
            startFraction2 = startFraction3;
        }
        if (this.totalTrackLengthFraction >= 1.0f || startFraction2 + arcFraction <= 1.0f) {
            Paint paint3 = paint;
            float startFraction4 = startFraction2;
            float displayedCornerRadiusInDegree = (float) Math.toDegrees((double) (this.displayedCornerRadius / this.adjustedRadius));
            float arcFractionOverRoundCapThreshold = arcFraction - 0.99f;
            if (arcFractionOverRoundCapThreshold >= 0.0f) {
                float increasedArcFraction = ((arcFractionOverRoundCapThreshold * displayedCornerRadiusInDegree) / 180.0f) / ROUND_CAP_RAMP_DOWN_THRESHHOLD;
                arcFraction += increasedArcFraction;
                if (!shouldDrawActiveIndicator) {
                    startFraction4 -= increasedArcFraction / 2.0f;
                }
            }
            float startFraction5 = MathUtils.lerp(1.0f - this.totalTrackLengthFraction, 1.0f, startFraction4);
            float arcFraction2 = MathUtils.lerp(0.0f, this.totalTrackLengthFraction, arcFraction);
            float startGapSizeInDegrees = (float) Math.toDegrees((double) (((float) startGapSize) / this.adjustedRadius));
            float endGapSizeInDegrees = (float) Math.toDegrees((double) (((float) endGapSize) / this.adjustedRadius));
            float arcDegree = ((arcFraction2 * 360.0f) - startGapSizeInDegrees) - endGapSizeInDegrees;
            float startDegree = (startFraction5 * 360.0f) + startGapSizeInDegrees;
            if (arcDegree > 0.0f) {
                boolean shouldDrawWavyPath = ((CircularProgressIndicatorSpec) this.spec).hasWavyEffect(this.drawingDeterminateIndicator) && shouldDrawActiveIndicator && amplitudeFraction > 0.0f;
                paint3.setAntiAlias(true);
                paint3.setColor(paintColor);
                paint3.setStrokeWidth(this.displayedTrackThickness);
                float blockWidth2 = this.displayedCornerRadius * 2.0f;
                if (arcDegree < displayedCornerRadiusInDegree * 2.0f) {
                    float shrinkRatio2 = arcDegree / (displayedCornerRadiusInDegree * 2.0f);
                    float centerDegree = startDegree + (displayedCornerRadiusInDegree * shrinkRatio2);
                    DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint center = new DrawingDelegate.PathPoint();
                    if (!shouldDrawWavyPath) {
                        center.rotate(centerDegree + 90.0f);
                        center.moveAcross(-this.adjustedRadius);
                        blockWidth = blockWidth2;
                        shrinkRatio = shrinkRatio2;
                    } else {
                        float centerDistance = ((centerDegree / 360.0f) * this.activePathMeasure.getLength()) / 2.0f;
                        float amplitude = this.displayedAmplitude * amplitudeFraction;
                        blockWidth = blockWidth2;
                        if (!(this.adjustedRadius == this.cachedRadius && amplitude == this.cachedAmplitude)) {
                            this.cachedAmplitude = amplitude;
                            this.cachedRadius = this.adjustedRadius;
                            invalidateCachedPaths();
                        }
                        float f3 = amplitude;
                        shrinkRatio = shrinkRatio2;
                        this.activePathMeasure.getPosTan(centerDistance, center.posVec, center.tanVec);
                    }
                    paint3.setStyle(Paint.Style.FILL);
                    drawRoundedBlock(canvas, paint, center, blockWidth, this.displayedTrackThickness, shrinkRatio);
                    float f4 = endGapSizeInDegrees;
                    return;
                }
                float blockWidth3 = blockWidth2;
                Paint paint4 = paint3;
                paint4.setStyle(Paint.Style.STROKE);
                paint4.setStrokeCap(((CircularProgressIndicatorSpec) this.spec).useStrokeCap() ? Paint.Cap.ROUND : Paint.Cap.BUTT);
                float startDegreeWithoutCorners = startDegree + displayedCornerRadiusInDegree;
                float arcDegreeWithoutCorners = arcDegree - (displayedCornerRadiusInDegree * 2.0f);
                ((DrawingDelegate.PathPoint) this.endPoints.first).reset();
                ((DrawingDelegate.PathPoint) this.endPoints.second).reset();
                if (!shouldDrawWavyPath) {
                    ((DrawingDelegate.PathPoint) this.endPoints.first).rotate(startDegreeWithoutCorners + 90.0f);
                    ((DrawingDelegate.PathPoint) this.endPoints.first).moveAcross(-this.adjustedRadius);
                    ((DrawingDelegate.PathPoint) this.endPoints.second).rotate(startDegreeWithoutCorners + arcDegreeWithoutCorners + 90.0f);
                    ((DrawingDelegate.PathPoint) this.endPoints.second).moveAcross(-this.adjustedRadius);
                    f2 = 0.0f;
                    this.arcBounds.set(-this.adjustedRadius, -this.adjustedRadius, this.adjustedRadius, this.adjustedRadius);
                    Paint paint5 = paint;
                    canvas.drawArc(this.arcBounds, startDegreeWithoutCorners, arcDegreeWithoutCorners, false, paint5);
                    float f5 = startDegreeWithoutCorners;
                    float f6 = arcDegreeWithoutCorners;
                    paint2 = paint5;
                    canvas2 = canvas;
                    circularDrawingDelegate = this;
                    float f7 = endGapSizeInDegrees;
                } else {
                    f2 = 0.0f;
                    paint2 = paint;
                    circularDrawingDelegate = this;
                    float f8 = endGapSizeInDegrees;
                    circularDrawingDelegate.calculateDisplayedPath(this.activePathMeasure, this.displayedActivePath, this.endPoints, startDegreeWithoutCorners / 360.0f, arcDegreeWithoutCorners / 360.0f, amplitudeFraction, phaseFraction);
                    canvas2 = canvas;
                    canvas2.drawPath(circularDrawingDelegate.displayedActivePath, paint2);
                }
                if (((CircularProgressIndicatorSpec) circularDrawingDelegate.spec).useStrokeCap() || circularDrawingDelegate.displayedCornerRadius <= f2) {
                    return;
                }
                paint2.setStyle(Paint.Style.FILL);
                float blockWidth4 = blockWidth3;
                circularDrawingDelegate.drawRoundedBlock(canvas2, paint2, (DrawingDelegate.PathPoint) circularDrawingDelegate.endPoints.first, blockWidth4, circularDrawingDelegate.displayedTrackThickness);
                circularDrawingDelegate.drawRoundedBlock(canvas, paint, (DrawingDelegate.PathPoint) circularDrawingDelegate.endPoints.second, blockWidth4, circularDrawingDelegate.displayedTrackThickness);
                return;
            }
            return;
        }
        Canvas canvas3 = canvas;
        Paint paint6 = paint;
        int i = paintColor;
        float f9 = amplitudeFraction;
        float f10 = phaseFraction;
        boolean z = shouldDrawActiveIndicator;
        drawArc(canvas3, paint6, startFraction2, 1.0f, i, startGapSize, 0, f9, f10, z);
        drawArc(canvas3, paint6, 1.0f, startFraction2 + arcFraction, i, 0, endGapSize, f9, f10, z);
        Paint paint7 = paint6;
    }

    /* access modifiers changed from: package-private */
    public void drawStopIndicator(Canvas canvas, Paint paint, int color, int drawableAlpha) {
    }

    private int getSize() {
        return ((CircularProgressIndicatorSpec) this.spec).indicatorSize + (((CircularProgressIndicatorSpec) this.spec).indicatorInset * 2);
    }

    private void drawRoundedBlock(Canvas canvas, Paint paint, DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint center, float markWidth, float markHeight) {
        drawRoundedBlock(canvas, paint, center, markWidth, markHeight, 1.0f);
    }

    private void drawRoundedBlock(Canvas canvas, Paint paint, DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint center, float markWidth, float markHeight, float scale) {
        float markHeight2 = Math.min(markHeight, this.displayedTrackThickness);
        float markCornerSize = Math.min(markWidth / 2.0f, (this.displayedCornerRadius * markHeight2) / this.displayedTrackThickness);
        RectF roundedBlock = new RectF((-markWidth) / 2.0f, (-markHeight2) / 2.0f, markWidth / 2.0f, markHeight2 / 2.0f);
        canvas.save();
        canvas.translate(center.posVec[0], center.posVec[1]);
        canvas.rotate(vectorToCanvasRotation(center.tanVec));
        canvas.scale(scale, scale);
        canvas.drawRoundRect(roundedBlock, markCornerSize, markCornerSize, paint);
        canvas.restore();
    }

    /* access modifiers changed from: package-private */
    public void invalidateCachedPaths() {
        this.cachedActivePath.rewind();
        this.cachedActivePath.moveTo(1.0f, 0.0f);
        for (int i = 0; i < 2; i++) {
            this.cachedActivePath.cubicTo(1.0f, QUARTER_CIRCLE_CONTROL_HANDLE_LENGTH, QUARTER_CIRCLE_CONTROL_HANDLE_LENGTH, 1.0f, 0.0f, 1.0f);
            this.cachedActivePath.cubicTo(-0.5522848f, 1.0f, -1.0f, QUARTER_CIRCLE_CONTROL_HANDLE_LENGTH, -1.0f, 0.0f);
            this.cachedActivePath.cubicTo(-1.0f, -0.5522848f, -0.5522848f, -1.0f, 0.0f, -1.0f);
            this.cachedActivePath.cubicTo(QUARTER_CIRCLE_CONTROL_HANDLE_LENGTH, -1.0f, 1.0f, -0.5522848f, 1.0f, 0.0f);
        }
        this.transform.reset();
        this.transform.setScale(this.adjustedRadius, this.adjustedRadius);
        this.cachedActivePath.transform(this.transform);
        if (((CircularProgressIndicatorSpec) this.spec).hasWavyEffect(this.drawingDeterminateIndicator)) {
            this.activePathMeasure.setPath(this.cachedActivePath, false);
            createWavyPath(this.activePathMeasure, this.cachedActivePath, this.cachedAmplitude);
        }
        this.activePathMeasure.setPath(this.cachedActivePath, false);
    }

    private void createWavyPath(PathMeasure basePathMeasure, Path outPath, float amplitude) {
        outPath.rewind();
        float basePathLength = basePathMeasure.getLength();
        int cycleCountInPath = Math.max(3, (int) ((basePathLength / ((float) (this.drawingDeterminateIndicator ? ((CircularProgressIndicatorSpec) this.spec).wavelengthDeterminate : ((CircularProgressIndicatorSpec) this.spec).wavelengthIndeterminate))) / 2.0f)) * 2;
        this.adjustedWavelength = basePathLength / ((float) cycleCountInPath);
        List<DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint> anchors = new ArrayList<>();
        for (int i = 0; i < cycleCountInPath; i++) {
            DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint startAnchor = new DrawingDelegate.PathPoint();
            basePathMeasure.getPosTan(this.adjustedWavelength * ((float) i), startAnchor.posVec, startAnchor.tanVec);
            DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint midAnchor = new DrawingDelegate.PathPoint();
            basePathMeasure.getPosTan((this.adjustedWavelength * ((float) i)) + (this.adjustedWavelength / 2.0f), midAnchor.posVec, midAnchor.tanVec);
            anchors.add(startAnchor);
            midAnchor.moveAcross(amplitude * 2.0f);
            anchors.add(midAnchor);
        }
        anchors.add(anchors.get(0));
        DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint startAnchor2 = anchors.get(0);
        outPath.moveTo(startAnchor2.posVec[0], startAnchor2.posVec[1]);
        for (int i2 = 1; i2 < anchors.size(); i2++) {
            DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint endAnchor = anchors.get(i2);
            appendCubicPerHalfCycle(outPath, startAnchor2, endAnchor);
            startAnchor2 = endAnchor;
        }
    }

    private void appendCubicPerHalfCycle(Path outPath, DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint anchor1, DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint anchor2) {
        float controlLength = (this.adjustedWavelength / 2.0f) * 0.48f;
        DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint control1 = new DrawingDelegate.PathPoint(this, anchor1);
        DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint control2 = new DrawingDelegate.PathPoint(this, anchor2);
        control1.moveAlong(controlLength);
        control2.moveAlong(-controlLength);
        outPath.cubicTo(control1.posVec[0], control1.posVec[1], control2.posVec[0], control2.posVec[1], anchor2.posVec[0], anchor2.posVec[1]);
    }

    private void calculateDisplayedPath(PathMeasure pathMeasure, Path displayedPath, Pair<DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint, DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint> endPoints2, float start, float span, float amplitudeFraction, float phaseFraction) {
        float start2;
        Pair<DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint, DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint> pair = endPoints2;
        float amplitude = this.displayedAmplitude * amplitudeFraction;
        int wavelength = this.drawingDeterminateIndicator ? ((CircularProgressIndicatorSpec) this.spec).wavelengthDeterminate : ((CircularProgressIndicatorSpec) this.spec).wavelengthIndeterminate;
        if (this.adjustedRadius != this.cachedRadius || (pathMeasure == this.activePathMeasure && !(amplitude == this.cachedAmplitude && wavelength == this.cachedWavelength))) {
            this.cachedAmplitude = amplitude;
            this.cachedWavelength = wavelength;
            this.cachedRadius = this.adjustedRadius;
            invalidateCachedPaths();
        }
        displayedPath.rewind();
        float span2 = androidx.core.math.MathUtils.clamp(span, 0.0f, 1.0f);
        float resultRotation = 0.0f;
        if (((CircularProgressIndicatorSpec) this.spec).hasWavyEffect(this.drawingDeterminateIndicator)) {
            float phaseFractionInOneCycle = phaseFraction / ((float) ((((double) this.adjustedRadius) * 6.283185307179586d) / ((double) this.adjustedWavelength)));
            start2 = start + phaseFractionInOneCycle;
            resultRotation = 0.0f - (360.0f * phaseFractionInOneCycle);
        } else {
            start2 = start;
        }
        float start3 = start2 % 1.0f;
        float startDistance = (pathMeasure.getLength() * start3) / 2.0f;
        float endDistance = ((start3 + span2) * pathMeasure.getLength()) / 2.0f;
        pathMeasure.getSegment(startDistance, endDistance, displayedPath, true);
        DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint startPoint = (DrawingDelegate.PathPoint) pair.first;
        startPoint.reset();
        pathMeasure.getPosTan(startDistance, startPoint.posVec, startPoint.tanVec);
        DrawingDelegate<CircularProgressIndicatorSpec>.PathPoint endPoint = (DrawingDelegate.PathPoint) pair.second;
        endPoint.reset();
        pathMeasure.getPosTan(endDistance, endPoint.posVec, endPoint.tanVec);
        this.transform.reset();
        this.transform.setRotate(resultRotation);
        startPoint.rotate(resultRotation);
        endPoint.rotate(resultRotation);
        displayedPath.transform(this.transform);
    }
}
