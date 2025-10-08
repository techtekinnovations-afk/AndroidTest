package com.google.android.material.shadow;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;

public class ShadowRenderer {
    private static final int COLOR_ALPHA_END = 0;
    private static final int COLOR_ALPHA_MIDDLE = 20;
    private static final int COLOR_ALPHA_START = 68;
    private static final int[] cornerColors = new int[4];
    private static final float[] cornerPositions = {0.0f, 0.0f, 0.5f, 1.0f};
    private static final int[] edgeColors = new int[3];
    private static final float[] edgePositions = {0.0f, 0.5f, 1.0f};
    private final Paint cornerShadowPaint;
    private final Paint edgeShadowPaint;
    private final Path scratch;
    private int shadowEndColor;
    private int shadowMiddleColor;
    private final Paint shadowPaint;
    private int shadowStartColor;
    private final Paint transparentPaint;

    public ShadowRenderer() {
        this(ViewCompat.MEASURED_STATE_MASK);
    }

    public ShadowRenderer(int color) {
        this.scratch = new Path();
        this.transparentPaint = new Paint();
        this.shadowPaint = new Paint();
        setShadowColor(color);
        this.transparentPaint.setColor(0);
        this.cornerShadowPaint = new Paint(4);
        this.cornerShadowPaint.setStyle(Paint.Style.FILL);
        this.edgeShadowPaint = new Paint(this.cornerShadowPaint);
    }

    public void setShadowColor(int color) {
        this.shadowStartColor = ColorUtils.setAlphaComponent(color, COLOR_ALPHA_START);
        this.shadowMiddleColor = ColorUtils.setAlphaComponent(color, 20);
        this.shadowEndColor = ColorUtils.setAlphaComponent(color, 0);
        this.shadowPaint.setColor(this.shadowStartColor);
    }

    public void drawEdgeShadow(Canvas canvas, Matrix transform, RectF bounds, int elevation) {
        bounds.bottom += (float) elevation;
        bounds.offset(0.0f, (float) (-elevation));
        edgeColors[0] = this.shadowEndColor;
        edgeColors[1] = this.shadowMiddleColor;
        edgeColors[2] = this.shadowStartColor;
        this.edgeShadowPaint.setShader(new LinearGradient(bounds.left, bounds.top, bounds.left, bounds.bottom, edgeColors, edgePositions, Shader.TileMode.CLAMP));
        canvas.save();
        canvas.concat(transform);
        canvas.drawRect(bounds, this.edgeShadowPaint);
        canvas.restore();
    }

    public void drawCornerShadow(Canvas canvas, Matrix matrix, RectF bounds, int elevation, float startAngle, float sweepAngle) {
        float f;
        Canvas canvas2 = canvas;
        RectF rectF = bounds;
        int i = elevation;
        float f2 = sweepAngle;
        boolean drawShadowInsideBounds = f2 < 0.0f;
        Path arcBounds = this.scratch;
        if (drawShadowInsideBounds) {
            cornerColors[0] = 0;
            cornerColors[1] = this.shadowEndColor;
            cornerColors[2] = this.shadowMiddleColor;
            cornerColors[3] = this.shadowStartColor;
            f = startAngle;
        } else {
            arcBounds.rewind();
            arcBounds.moveTo(rectF.centerX(), rectF.centerY());
            f = startAngle;
            arcBounds.arcTo(rectF, f, f2);
            arcBounds.close();
            rectF.inset((float) (-i), (float) (-i));
            cornerColors[0] = 0;
            cornerColors[1] = this.shadowStartColor;
            cornerColors[2] = this.shadowMiddleColor;
            cornerColors[3] = this.shadowEndColor;
        }
        float radius = rectF.width() / 2.0f;
        if (radius > 0.0f) {
            float startRatio = 1.0f - (((float) i) / radius);
            cornerPositions[1] = startRatio;
            cornerPositions[2] = startRatio + ((1.0f - startRatio) / 2.0f);
            this.cornerShadowPaint.setShader(new RadialGradient(rectF.centerX(), rectF.centerY(), radius, cornerColors, cornerPositions, Shader.TileMode.CLAMP));
            canvas2.save();
            canvas.concat(matrix);
            canvas2.scale(1.0f, rectF.height() / rectF.width());
            if (!drawShadowInsideBounds) {
                canvas2.clipPath(arcBounds, Region.Op.DIFFERENCE);
                canvas2.drawPath(arcBounds, this.transparentPaint);
            }
            canvas2.drawArc(rectF, f, f2, true, this.cornerShadowPaint);
            canvas.restore();
        }
    }

    public void drawInnerCornerShadow(Canvas canvas, Matrix matrix, RectF bounds, int elevation, float startAngle, float sweepAngle, float[] cornerPosition) {
        float sweepAngle2;
        float startAngle2;
        if (sweepAngle > 0.0f) {
            startAngle2 = startAngle + sweepAngle;
            sweepAngle2 = -sweepAngle;
        } else {
            startAngle2 = startAngle;
            sweepAngle2 = sweepAngle;
        }
        Canvas canvas2 = canvas;
        Matrix matrix2 = matrix;
        RectF bounds2 = bounds;
        drawCornerShadow(canvas2, matrix2, bounds2, elevation, startAngle2, sweepAngle2);
        Path shapeBounds = this.scratch;
        shapeBounds.rewind();
        shapeBounds.moveTo(cornerPosition[0], cornerPosition[1]);
        shapeBounds.arcTo(bounds2, startAngle2, sweepAngle2);
        shapeBounds.close();
        canvas2.save();
        canvas2.concat(matrix2);
        canvas2.scale(1.0f, bounds2.height() / bounds2.width());
        canvas2.drawPath(shapeBounds, this.transparentPaint);
        canvas2.drawPath(shapeBounds, this.shadowPaint);
        canvas2.restore();
    }

    public Paint getShadowPaint() {
        return this.shadowPaint;
    }
}
