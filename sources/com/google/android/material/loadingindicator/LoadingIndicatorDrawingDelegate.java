package com.google.android.material.loadingindicator;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.graphics.shapes.Morph;
import androidx.graphics.shapes.RoundedPolygon;
import androidx.graphics.shapes.Shapes_androidKt;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.math.MathUtils;
import com.google.android.material.shape.MaterialShapes;

class LoadingIndicatorDrawingDelegate {
    private static final Morph[] INDETERMINATE_MORPH_SEQUENCE = new Morph[INDETERMINATE_SHAPES.length];
    private static final RoundedPolygon[] INDETERMINATE_SHAPES = {MaterialShapes.normalize(MaterialShapes.SOFT_BURST, true, new RectF(-1.0f, -1.0f, 1.0f, 1.0f)), MaterialShapes.normalize(MaterialShapes.COOKIE_9, true, new RectF(-1.0f, -1.0f, 1.0f, 1.0f)), MaterialShapes.normalize(MaterialShapes.PENTAGON, true, new RectF(-1.0f, -1.0f, 1.0f, 1.0f)), MaterialShapes.normalize(MaterialShapes.PILL, true, new RectF(-1.0f, -1.0f, 1.0f, 1.0f)), MaterialShapes.normalize(MaterialShapes.SUNNY, true, new RectF(-1.0f, -1.0f, 1.0f, 1.0f)), MaterialShapes.normalize(MaterialShapes.COOKIE_4, true, new RectF(-1.0f, -1.0f, 1.0f, 1.0f)), MaterialShapes.normalize(MaterialShapes.OVAL, true, new RectF(-1.0f, -1.0f, 1.0f, 1.0f))};
    final Path indicatorPath = new Path();
    final Matrix indicatorPathTransform = new Matrix();
    LoadingIndicatorSpec specs;

    public LoadingIndicatorDrawingDelegate(LoadingIndicatorSpec specs2) {
        this.specs = specs2;
    }

    /* access modifiers changed from: package-private */
    public int getPreferredWidth() {
        return Math.max(this.specs.containerHeight, this.specs.indicatorSize);
    }

    /* access modifiers changed from: package-private */
    public int getPreferredHeight() {
        return Math.max(this.specs.containerWidth, this.specs.indicatorSize);
    }

    /* access modifiers changed from: package-private */
    public void adjustCanvas(Canvas canvas, Rect bounds) {
        canvas.translate((float) bounds.centerX(), (float) bounds.centerY());
        if (this.specs.scaleToFit) {
            float scale = Math.min(((float) bounds.width()) / ((float) getPreferredWidth()), ((float) bounds.height()) / ((float) getPreferredHeight()));
            canvas.scale(scale, scale);
        }
        canvas.clipRect(((float) (-getPreferredWidth())) / 2.0f, ((float) (-getPreferredHeight())) / 2.0f, ((float) getPreferredWidth()) / 2.0f, ((float) getPreferredHeight()) / 2.0f);
        canvas.rotate(-90.0f);
    }

    /* access modifiers changed from: package-private */
    public void drawContainer(Canvas canvas, Paint paint, int color, int drawableAlpha) {
        float radius = ((float) Math.min(this.specs.containerWidth, this.specs.containerHeight)) / 2.0f;
        paint.setColor(MaterialColors.compositeARGBWithAlpha(color, drawableAlpha));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(((float) (-this.specs.containerWidth)) / 2.0f, ((float) (-this.specs.containerHeight)) / 2.0f, ((float) this.specs.containerWidth) / 2.0f, ((float) this.specs.containerHeight) / 2.0f), radius, radius, paint);
    }

    /* access modifiers changed from: package-private */
    public void drawIndicator(Canvas canvas, Paint paint, IndicatorState indicatorState, int drawableAlpha) {
        paint.setColor(MaterialColors.compositeARGBWithAlpha(indicatorState.color, drawableAlpha));
        paint.setStyle(Paint.Style.FILL);
        canvas.save();
        canvas.rotate(indicatorState.rotationDegree);
        this.indicatorPath.rewind();
        int shapeMorphFraction = (int) Math.floor((double) indicatorState.morphFraction);
        int fractionAmongAllShapes = MathUtils.floorMod(shapeMorphFraction, INDETERMINATE_MORPH_SEQUENCE.length);
        Shapes_androidKt.toPath(INDETERMINATE_MORPH_SEQUENCE[fractionAmongAllShapes], indicatorState.morphFraction - ((float) shapeMorphFraction), this.indicatorPath);
        this.indicatorPathTransform.setScale(((float) this.specs.indicatorSize) / 2.0f, ((float) this.specs.indicatorSize) / 2.0f);
        this.indicatorPath.transform(this.indicatorPathTransform);
        canvas.drawPath(this.indicatorPath, paint);
        canvas.restore();
    }

    static {
        for (int i = 0; i < INDETERMINATE_SHAPES.length; i++) {
            INDETERMINATE_MORPH_SEQUENCE[i] = new Morph(INDETERMINATE_SHAPES[i], INDETERMINATE_SHAPES[(i + 1) % INDETERMINATE_SHAPES.length]);
        }
    }

    protected static class IndicatorState {
        int color;
        float morphFraction;
        float rotationDegree;

        protected IndicatorState() {
        }
    }
}
