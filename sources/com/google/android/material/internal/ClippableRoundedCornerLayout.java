package com.google.android.material.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class ClippableRoundedCornerLayout extends FrameLayout {
    private float[] cornerRadii = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    private Path path;

    public ClippableRoundedCornerLayout(Context context) {
        super(context);
    }

    public ClippableRoundedCornerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClippableRoundedCornerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        if (this.path == null) {
            super.dispatchDraw(canvas);
            return;
        }
        int save = canvas.save();
        canvas.clipPath(this.path);
        super.dispatchDraw(canvas);
        canvas.restoreToCount(save);
    }

    public void resetClipBoundsAndCornerRadii() {
        this.path = null;
        this.cornerRadii = new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        invalidate();
    }

    public float[] getCornerRadii() {
        return this.cornerRadii;
    }

    public void updateCornerRadii(float[] cornerRadii2) {
        updateClipBoundsAndCornerRadii((float) getLeft(), (float) getTop(), (float) getRight(), (float) getBottom(), cornerRadii2);
    }

    public void updateClipBoundsAndCornerRadii(Rect rect, float[] cornerRadii2) {
        updateClipBoundsAndCornerRadii((float) rect.left, (float) rect.top, (float) rect.right, (float) rect.bottom, cornerRadii2);
    }

    public void updateClipBoundsAndCornerRadii(float left, float top, float right, float bottom, float[] cornerRadii2) {
        updateClipBoundsAndCornerRadii(new RectF(left, top, right, bottom), cornerRadii2);
    }

    public void updateClipBoundsAndCornerRadii(RectF rectF, float[] cornerRadii2) {
        if (this.path == null) {
            this.path = new Path();
        }
        this.cornerRadii = cornerRadii2;
        this.path.reset();
        this.path.addRoundRect(rectF, cornerRadii2, Path.Direction.CW);
        this.path.close();
        invalidate();
    }
}
