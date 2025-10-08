package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Pair;
import androidx.core.math.MathUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.progressindicator.DrawingDelegate;

final class LinearDrawingDelegate extends DrawingDelegate<LinearProgressIndicatorSpec> {
    private float adjustedWavelength;
    private int cachedWavelength;
    private float displayedAmplitude;
    private float displayedCornerRadius;
    private float displayedInnerCornerRadius;
    private float displayedTrackThickness;
    private boolean drawingDeterminateIndicator;
    Pair<DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint, DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint> endPoints = new Pair<>(new DrawingDelegate.PathPoint(), new DrawingDelegate.PathPoint());
    private float totalTrackLengthFraction;
    private float trackLength = 300.0f;

    LinearDrawingDelegate(LinearProgressIndicatorSpec spec) {
        super(spec);
    }

    /* access modifiers changed from: package-private */
    public int getPreferredWidth() {
        return -1;
    }

    /* access modifiers changed from: package-private */
    public int getPreferredHeight() {
        return ((LinearProgressIndicatorSpec) this.spec).trackThickness + (((LinearProgressIndicatorSpec) this.spec).waveAmplitude * 2);
    }

    /* access modifiers changed from: package-private */
    public void adjustCanvas(Canvas canvas, Rect bounds, float trackThicknessFraction, boolean isShowing, boolean isHiding) {
        if (this.trackLength != ((float) bounds.width())) {
            this.trackLength = (float) bounds.width();
            invalidateCachedPaths();
        }
        float trackSize = (float) getPreferredHeight();
        canvas.translate(((float) bounds.left) + (((float) bounds.width()) / 2.0f), ((float) bounds.top) + (((float) bounds.height()) / 2.0f) + Math.max(0.0f, (((float) bounds.height()) - trackSize) / 2.0f));
        if (((LinearProgressIndicatorSpec) this.spec).drawHorizontallyInverse) {
            canvas.scale(-1.0f, 1.0f);
        }
        float halfTrackLength = this.trackLength / 2.0f;
        float halfTrackSize = trackSize / 2.0f;
        canvas.clipRect(-halfTrackLength, -halfTrackSize, halfTrackLength, halfTrackSize);
        this.displayedTrackThickness = ((float) ((LinearProgressIndicatorSpec) this.spec).trackThickness) * trackThicknessFraction;
        this.displayedCornerRadius = ((float) Math.min(((LinearProgressIndicatorSpec) this.spec).trackThickness / 2, ((LinearProgressIndicatorSpec) this.spec).getTrackCornerRadiusInPx())) * trackThicknessFraction;
        this.displayedAmplitude = ((float) ((LinearProgressIndicatorSpec) this.spec).waveAmplitude) * trackThicknessFraction;
        this.displayedInnerCornerRadius = Math.min(((float) ((LinearProgressIndicatorSpec) this.spec).trackThickness) / 2.0f, (float) ((LinearProgressIndicatorSpec) this.spec).getTrackInnerCornerRadiusInPx()) * trackThicknessFraction;
        if (isShowing || isHiding) {
            if ((isShowing && ((LinearProgressIndicatorSpec) this.spec).showAnimationBehavior == 2) || (isHiding && ((LinearProgressIndicatorSpec) this.spec).hideAnimationBehavior == 1)) {
                canvas.scale(1.0f, -1.0f);
            }
            if (isShowing || (isHiding && ((LinearProgressIndicatorSpec) this.spec).hideAnimationBehavior != 3)) {
                canvas.translate(0.0f, (((float) ((LinearProgressIndicatorSpec) this.spec).trackThickness) * (1.0f - trackThicknessFraction)) / 2.0f);
            }
        }
        if (!isHiding || ((LinearProgressIndicatorSpec) this.spec).hideAnimationBehavior != 3) {
            this.totalTrackLengthFraction = 1.0f;
        } else {
            this.totalTrackLengthFraction = trackThicknessFraction;
        }
    }

    /* access modifiers changed from: package-private */
    public void fillIndicator(Canvas canvas, Paint paint, DrawingDelegate.ActiveIndicator activeIndicator, int drawableAlpha) {
        DrawingDelegate.ActiveIndicator activeIndicator2 = activeIndicator;
        int color = MaterialColors.compositeARGBWithAlpha(activeIndicator2.color, drawableAlpha);
        this.drawingDeterminateIndicator = activeIndicator2.isDeterminate;
        drawLine(canvas, paint, activeIndicator2.startFraction, activeIndicator2.endFraction, color, activeIndicator2.gapSize, activeIndicator2.gapSize, activeIndicator2.amplitudeFraction, activeIndicator2.phaseFraction, true);
    }

    /* access modifiers changed from: package-private */
    public void fillTrack(Canvas canvas, Paint paint, float startFraction, float endFraction, int color, int drawableAlpha, int gapSize) {
        int color2 = MaterialColors.compositeARGBWithAlpha(color, drawableAlpha);
        this.drawingDeterminateIndicator = false;
        drawLine(canvas, paint, startFraction, endFraction, color2, gapSize, gapSize, 0.0f, 0.0f, false);
    }

    private void drawLine(Canvas canvas, Paint paint, float startFraction, float endFraction, int paintColor, int startGapSize, int endGapSize, float amplitudeFraction, float phaseFraction, boolean drawingActiveIndicator) {
        float endCornerRadius;
        float cornerRampDownThreshold;
        float f;
        Paint paint2;
        Canvas canvas2;
        LinearDrawingDelegate linearDrawingDelegate;
        Paint paint3 = paint;
        float startFraction2 = MathUtils.clamp(startFraction, 0.0f, 1.0f);
        float endFraction2 = MathUtils.clamp(endFraction, 0.0f, 1.0f);
        float startFraction3 = com.google.android.material.math.MathUtils.lerp(1.0f - this.totalTrackLengthFraction, 1.0f, startFraction2);
        float endFraction3 = com.google.android.material.math.MathUtils.lerp(1.0f - this.totalTrackLengthFraction, 1.0f, endFraction2);
        int startPx = (int) ((this.trackLength * startFraction3) + ((float) ((int) ((((float) startGapSize) * MathUtils.clamp(startFraction3, 0.0f, 0.01f)) / 0.01f))));
        int endPx = (int) ((this.trackLength * endFraction3) - ((float) ((int) ((((float) endGapSize) * (1.0f - MathUtils.clamp(endFraction3, 0.99f, 1.0f))) / 0.01f))));
        float startCornerRadius = this.displayedCornerRadius;
        float endCornerRadius2 = this.displayedCornerRadius;
        if (this.displayedCornerRadius != this.displayedInnerCornerRadius) {
            float cornerRampDownThreshold2 = Math.max(this.displayedCornerRadius, this.displayedInnerCornerRadius) / this.trackLength;
            cornerRampDownThreshold = com.google.android.material.math.MathUtils.lerp(this.displayedCornerRadius, this.displayedInnerCornerRadius, MathUtils.clamp(((float) startPx) / this.trackLength, 0.0f, cornerRampDownThreshold2) / cornerRampDownThreshold2);
            endCornerRadius = com.google.android.material.math.MathUtils.lerp(this.displayedCornerRadius, this.displayedInnerCornerRadius, MathUtils.clamp((this.trackLength - ((float) endPx)) / this.trackLength, 0.0f, cornerRampDownThreshold2) / cornerRampDownThreshold2);
        } else {
            cornerRampDownThreshold = startCornerRadius;
            endCornerRadius = endCornerRadius2;
        }
        float originX = (-this.trackLength) / 2.0f;
        boolean drawWavyPath = ((LinearProgressIndicatorSpec) this.spec).hasWavyEffect(this.drawingDeterminateIndicator) && drawingActiveIndicator && amplitudeFraction > 0.0f;
        if (startPx <= endPx) {
            float startBlockCenterX = ((float) startPx) + cornerRampDownThreshold;
            float endBlockCenterX = ((float) endPx) - endCornerRadius;
            float startBlockWidth = cornerRampDownThreshold * 2.0f;
            float endBlockWidth = 2.0f * endCornerRadius;
            paint3.setColor(paintColor);
            paint3.setAntiAlias(true);
            paint3.setStrokeWidth(this.displayedTrackThickness);
            ((DrawingDelegate.PathPoint) this.endPoints.first).reset();
            ((DrawingDelegate.PathPoint) this.endPoints.second).reset();
            ((DrawingDelegate.PathPoint) this.endPoints.first).translate(startBlockCenterX + originX, 0.0f);
            ((DrawingDelegate.PathPoint) this.endPoints.second).translate(endBlockCenterX + originX, 0.0f);
            if (startPx != 0 || endBlockCenterX + endCornerRadius >= startBlockCenterX + cornerRampDownThreshold) {
                float f2 = endCornerRadius;
                float endCornerRadius3 = endBlockWidth;
                float endCornerRadius4 = f2;
                int i = startPx;
                int i2 = endPx;
                if (startBlockCenterX - cornerRampDownThreshold > endBlockCenterX - endCornerRadius4) {
                    float endBlockWidth2 = endCornerRadius3;
                    float startCornerRadius2 = cornerRampDownThreshold;
                    float endCornerRadius5 = endCornerRadius4;
                    float f3 = endBlockWidth2;
                    float startBlockWidth2 = startBlockWidth;
                    float startBlockWidth3 = f3;
                    drawRoundedBlock(canvas, paint, (DrawingDelegate.PathPoint) this.endPoints.second, startBlockWidth3, this.displayedTrackThickness, endCornerRadius5, (DrawingDelegate.PathPoint) this.endPoints.first, startBlockWidth2, this.displayedTrackThickness, startCornerRadius2, false);
                    float f4 = endCornerRadius5;
                    float f5 = startBlockWidth2;
                    float f6 = startCornerRadius2;
                    float startCornerRadius3 = startBlockWidth3;
                    float endCornerRadius6 = f4;
                    return;
                }
                Paint paint4 = paint;
                float startBlockWidth4 = startBlockWidth;
                float endCornerRadius7 = endCornerRadius4;
                float startCornerRadius4 = cornerRampDownThreshold;
                paint4.setStyle(Paint.Style.STROKE);
                paint4.setStrokeCap(((LinearProgressIndicatorSpec) this.spec).useStrokeCap() ? Paint.Cap.ROUND : Paint.Cap.BUTT);
                if (!drawWavyPath) {
                    canvas.drawLine(((DrawingDelegate.PathPoint) this.endPoints.first).posVec[0], ((DrawingDelegate.PathPoint) this.endPoints.first).posVec[1], ((DrawingDelegate.PathPoint) this.endPoints.second).posVec[0], ((DrawingDelegate.PathPoint) this.endPoints.second).posVec[1], paint4);
                    canvas2 = canvas;
                    linearDrawingDelegate = this;
                    f = 0.0f;
                    paint2 = paint;
                } else {
                    linearDrawingDelegate = this;
                    f = 0.0f;
                    paint2 = paint;
                    linearDrawingDelegate.calculateDisplayedPath(this.activePathMeasure, this.displayedActivePath, this.endPoints, startBlockCenterX / this.trackLength, endBlockCenterX / this.trackLength, amplitudeFraction, phaseFraction);
                    canvas2 = canvas;
                    canvas2.drawPath(linearDrawingDelegate.displayedActivePath, paint2);
                }
                if (!((LinearProgressIndicatorSpec) linearDrawingDelegate.spec).useStrokeCap()) {
                    if (startBlockCenterX <= f || startCornerRadius4 <= f) {
                    } else {
                        float startBlockWidth5 = startBlockWidth4;
                        linearDrawingDelegate.drawRoundedBlock(canvas2, paint2, (DrawingDelegate.PathPoint) linearDrawingDelegate.endPoints.first, startBlockWidth5, linearDrawingDelegate.displayedTrackThickness, startCornerRadius4);
                        float f7 = startBlockWidth5;
                    }
                    if (endBlockCenterX >= linearDrawingDelegate.trackLength || endCornerRadius7 <= f) {
                    } else {
                        linearDrawingDelegate.drawRoundedBlock(canvas, paint, (DrawingDelegate.PathPoint) linearDrawingDelegate.endPoints.second, endCornerRadius3, linearDrawingDelegate.displayedTrackThickness, endCornerRadius7);
                    }
                } else {
                    float f8 = endCornerRadius7;
                }
            } else {
                float endBlockWidth3 = endBlockWidth;
                int i3 = endPx;
                int endPx2 = startPx;
                drawRoundedBlock(canvas, paint3, (DrawingDelegate.PathPoint) this.endPoints.first, startBlockWidth, this.displayedTrackThickness, cornerRampDownThreshold, (DrawingDelegate.PathPoint) this.endPoints.second, endBlockWidth3, this.displayedTrackThickness, endCornerRadius, true);
                float f9 = endCornerRadius;
                float endCornerRadius8 = endBlockWidth3;
                float f10 = cornerRampDownThreshold;
                float startCornerRadius5 = f9;
            }
        } else {
            int i4 = endPx;
            float f11 = cornerRampDownThreshold;
            float startCornerRadius6 = endCornerRadius;
        }
    }

    /* access modifiers changed from: package-private */
    public void drawStopIndicator(Canvas canvas, Paint paint, int color, int drawableAlpha) {
        float stopIndicatorCenterX;
        int paintColor = MaterialColors.compositeARGBWithAlpha(color, drawableAlpha);
        this.drawingDeterminateIndicator = false;
        if (((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorSize <= 0 || paintColor == 0) {
            Paint paint2 = paint;
            return;
        }
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(paintColor);
        if (((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorPadding != null) {
            stopIndicatorCenterX = ((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorPadding.floatValue() + (((float) ((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorSize) / 2.0f);
        } else {
            stopIndicatorCenterX = this.displayedTrackThickness / 2.0f;
        }
        Canvas canvas2 = canvas;
        Paint paint3 = paint;
        drawRoundedBlock(canvas2, paint3, new DrawingDelegate.PathPoint(new float[]{(this.trackLength / 2.0f) - stopIndicatorCenterX, 0.0f}, new float[]{1.0f, 0.0f}), (float) ((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorSize, (float) ((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorSize, (this.displayedCornerRadius * ((float) ((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorSize)) / this.displayedTrackThickness);
    }

    private void drawRoundedBlock(Canvas canvas, Paint paint, DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint drawCenter, float drawWidth, float drawHeight, float drawCornerSize) {
        drawRoundedBlock(canvas, paint, drawCenter, drawWidth, drawHeight, drawCornerSize, (DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint) null, 0.0f, 0.0f, 0.0f, false);
    }

    private void drawRoundedBlock(Canvas canvas, Paint paint, DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint drawCenter, float drawWidth, float drawHeight, float drawCornerSize, DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint clipCenter, float clipWidth, float clipHeight, float clipCornerSize, boolean clipRight) {
        char c;
        char c2;
        float f;
        float clipWidth2;
        float clipWidth3;
        Canvas canvas2 = canvas;
        Paint paint2 = paint;
        DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint pathPoint = drawCenter;
        float f2 = drawWidth;
        float f3 = drawCornerSize;
        DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint pathPoint2 = clipCenter;
        float drawHeight2 = Math.min(drawHeight, this.displayedTrackThickness);
        RectF drawRect = new RectF((-f2) / 2.0f, (-drawHeight2) / 2.0f, f2 / 2.0f, drawHeight2 / 2.0f);
        paint2.setStyle(Paint.Style.FILL);
        canvas2.save();
        if (pathPoint2 != null) {
            float clipHeight2 = Math.min(clipHeight, this.displayedTrackThickness);
            float clipCornerSize2 = Math.min(clipWidth / 2.0f, (clipCornerSize * clipHeight2) / this.displayedTrackThickness);
            RectF patchRect = new RectF();
            if (clipRight) {
                c = 1;
                f = 2.0f;
                float leftEdgeDiff = (pathPoint2.posVec[0] - clipCornerSize2) - (pathPoint.posVec[0] - f3);
                if (leftEdgeDiff > 0.0f) {
                    pathPoint2.translate((-leftEdgeDiff) / 2.0f, 0.0f);
                    clipWidth3 = clipWidth + leftEdgeDiff;
                } else {
                    clipWidth3 = clipWidth;
                }
                c2 = 0;
                float f4 = leftEdgeDiff;
                patchRect.set(0.0f, (-drawHeight2) / 2.0f, f2 / 2.0f, drawHeight2 / 2.0f);
                clipWidth2 = clipWidth3;
                float f5 = drawHeight2;
            } else {
                f = 2.0f;
                c2 = 0;
                c = 1;
                float rightEdgeDiff = (pathPoint2.posVec[0] + clipCornerSize2) - (pathPoint.posVec[0] + f3);
                if (rightEdgeDiff < 0.0f) {
                    pathPoint2.translate((-rightEdgeDiff) / 2.0f, 0.0f);
                    clipWidth2 = clipWidth - rightEdgeDiff;
                } else {
                    clipWidth2 = clipWidth;
                }
                float f6 = drawHeight2;
                patchRect.set((-f2) / 2.0f, (-drawHeight2) / 2.0f, 0.0f, drawHeight2 / 2.0f);
            }
            RectF clipRect = new RectF((-clipWidth2) / f, (-clipHeight2) / f, clipWidth2 / f, clipHeight2 / f);
            canvas2.translate(pathPoint2.posVec[c2], pathPoint2.posVec[c]);
            canvas2.rotate(vectorToCanvasRotation(pathPoint2.tanVec));
            Path clipPath = new Path();
            clipPath.addRoundRect(clipRect, clipCornerSize2, clipCornerSize2, Path.Direction.CCW);
            canvas2.clipPath(clipPath);
            canvas2.rotate(-vectorToCanvasRotation(pathPoint2.tanVec));
            canvas2.translate(-pathPoint2.posVec[c2], -pathPoint2.posVec[c]);
            canvas2.translate(pathPoint.posVec[c2], pathPoint.posVec[c]);
            canvas2.rotate(vectorToCanvasRotation(pathPoint.tanVec));
            canvas2.drawRect(patchRect, paint2);
            canvas2.drawRoundRect(drawRect, f3, f3, paint2);
        } else {
            float f7 = drawHeight2;
            canvas2.translate(pathPoint.posVec[0], pathPoint.posVec[1]);
            canvas2.rotate(vectorToCanvasRotation(pathPoint.tanVec));
            canvas2.drawRoundRect(drawRect, f3, f3, paint2);
            float f8 = clipWidth;
            float f9 = clipHeight;
            float f10 = clipCornerSize;
        }
        canvas2.restore();
    }

    /* access modifiers changed from: package-private */
    public void invalidateCachedPaths() {
        this.cachedActivePath.rewind();
        if (((LinearProgressIndicatorSpec) this.spec).hasWavyEffect(this.drawingDeterminateIndicator)) {
            int cycleCount = (int) (this.trackLength / ((float) (this.drawingDeterminateIndicator ? ((LinearProgressIndicatorSpec) this.spec).wavelengthDeterminate : ((LinearProgressIndicatorSpec) this.spec).wavelengthIndeterminate)));
            this.adjustedWavelength = this.trackLength / ((float) cycleCount);
            for (int i = 0; i <= cycleCount; i++) {
                this.cachedActivePath.cubicTo(((float) (i * 2)) + 0.48f, 0.0f, ((float) ((i * 2) + 1)) - 0.48f, 1.0f, (float) ((i * 2) + 1), 1.0f);
                this.cachedActivePath.cubicTo(((float) ((i * 2) + 1)) + 0.48f, 1.0f, ((float) ((i * 2) + 2)) - 0.48f, 0.0f, (float) ((i * 2) + 2), 0.0f);
            }
            this.transform.reset();
            this.transform.setScale(this.adjustedWavelength / 2.0f, -2.0f);
            this.transform.postTranslate(0.0f, 1.0f);
            this.cachedActivePath.transform(this.transform);
        } else {
            this.cachedActivePath.lineTo(this.trackLength, 0.0f);
        }
        this.activePathMeasure.setPath(this.cachedActivePath, false);
    }

    private void calculateDisplayedPath(PathMeasure pathMeasure, Path displayedPath, Pair<DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint, DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint> endPoints2, float start, float end, float amplitudeFraction, float phaseFraction) {
        float end2;
        float start2;
        PathMeasure pathMeasure2 = pathMeasure;
        Path path = displayedPath;
        Pair<DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint, DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint> pair = endPoints2;
        int wavelength = this.drawingDeterminateIndicator ? ((LinearProgressIndicatorSpec) this.spec).wavelengthDeterminate : ((LinearProgressIndicatorSpec) this.spec).wavelengthIndeterminate;
        if (pathMeasure2 == this.activePathMeasure && wavelength != this.cachedWavelength) {
            this.cachedWavelength = wavelength;
            invalidateCachedPaths();
        }
        path.rewind();
        float resultTranslationX = (-this.trackLength) / 2.0f;
        boolean hasWavyEffect = ((LinearProgressIndicatorSpec) this.spec).hasWavyEffect(this.drawingDeterminateIndicator);
        if (hasWavyEffect) {
            float cycleCount = this.trackLength / this.adjustedWavelength;
            float phaseFractionInPath = phaseFraction / cycleCount;
            float ratio = cycleCount / (cycleCount + 1.0f);
            start2 = (start + phaseFractionInPath) * ratio;
            end2 = (end + phaseFractionInPath) * ratio;
            resultTranslationX -= this.adjustedWavelength * phaseFraction;
        } else {
            start2 = start;
            end2 = end;
        }
        float startDistance = pathMeasure2.getLength() * start2;
        float endDistance = pathMeasure2.getLength() * end2;
        pathMeasure2.getSegment(startDistance, endDistance, path, true);
        DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint startPoint = (DrawingDelegate.PathPoint) pair.first;
        startPoint.reset();
        pathMeasure2.getPosTan(startDistance, startPoint.posVec, startPoint.tanVec);
        DrawingDelegate<LinearProgressIndicatorSpec>.PathPoint endPoint = (DrawingDelegate.PathPoint) pair.second;
        endPoint.reset();
        pathMeasure2.getPosTan(endDistance, endPoint.posVec, endPoint.tanVec);
        this.transform.reset();
        this.transform.setTranslate(resultTranslationX, 0.0f);
        startPoint.translate(resultTranslationX, 0.0f);
        endPoint.translate(resultTranslationX, 0.0f);
        if (hasWavyEffect) {
            float scaleY = this.displayedAmplitude * amplitudeFraction;
            this.transform.postScale(1.0f, scaleY);
            startPoint.scale(1.0f, scaleY);
            endPoint.scale(1.0f, scaleY);
        }
        path.transform(this.transform);
    }
}
