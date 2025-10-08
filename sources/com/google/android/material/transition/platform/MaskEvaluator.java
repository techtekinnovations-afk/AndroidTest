package com.google.android.material.transition.platform;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.google.android.material.transition.platform.MaterialContainerTransform;

class MaskEvaluator {
    private ShapeAppearanceModel currentShapeAppearanceModel;
    private final Path endPath = new Path();
    private final Path path = new Path();
    private final ShapeAppearancePathProvider pathProvider = ShapeAppearancePathProvider.getInstance();
    private final Path startPath = new Path();

    MaskEvaluator() {
    }

    /* access modifiers changed from: package-private */
    public void evaluate(float progress, ShapeAppearanceModel startShapeAppearanceModel, ShapeAppearanceModel endShapeAppearanceModel, RectF currentStartBounds, RectF currentStartBoundsMasked, RectF currentEndBoundsMasked, MaterialContainerTransform.ProgressThresholds shapeMaskThresholds) {
        RectF currentEndBoundsMasked2 = currentEndBoundsMasked;
        this.currentShapeAppearanceModel = TransitionUtils.lerp(startShapeAppearanceModel, endShapeAppearanceModel, currentStartBounds, currentEndBoundsMasked2, shapeMaskThresholds.getStart(), shapeMaskThresholds.getEnd(), progress);
        this.pathProvider.calculatePath(this.currentShapeAppearanceModel, 1.0f, currentStartBoundsMasked, this.startPath);
        this.pathProvider.calculatePath(this.currentShapeAppearanceModel, 1.0f, currentEndBoundsMasked2, this.endPath);
        this.path.op(this.startPath, this.endPath, Path.Op.UNION);
    }

    /* access modifiers changed from: package-private */
    public void clip(Canvas canvas) {
        canvas.clipPath(this.path);
    }

    /* access modifiers changed from: package-private */
    public Path getPath() {
        return this.path;
    }

    /* access modifiers changed from: package-private */
    public ShapeAppearanceModel getCurrentShapeAppearanceModel() {
        return this.currentShapeAppearanceModel;
    }
}
