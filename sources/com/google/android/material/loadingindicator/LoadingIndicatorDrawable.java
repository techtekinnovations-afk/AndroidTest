package com.google.android.material.loadingindicator;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import com.google.android.material.R;
import com.google.android.material.progressindicator.AnimatorDurationScaleProvider;

public final class LoadingIndicatorDrawable extends Drawable implements Drawable.Callback {
    int alpha;
    private LoadingIndicatorAnimatorDelegate animatorDelegate;
    AnimatorDurationScaleProvider animatorDurationScaleProvider = new AnimatorDurationScaleProvider();
    private final Context context;
    private LoadingIndicatorDrawingDelegate drawingDelegate;
    Paint paint = new Paint();
    private final LoadingIndicatorSpec specs;
    private Drawable staticDummyDrawable;

    public static LoadingIndicatorDrawable create(Context context2, LoadingIndicatorSpec specs2) {
        LoadingIndicatorDrawable loadingIndicatorDrawable = new LoadingIndicatorDrawable(context2, specs2, new LoadingIndicatorDrawingDelegate(specs2), new LoadingIndicatorAnimatorDelegate(specs2));
        loadingIndicatorDrawable.setStaticDummyDrawable(VectorDrawableCompat.create(context2.getResources(), R.drawable.ic_mtrl_arrow_circle, (Resources.Theme) null));
        return loadingIndicatorDrawable;
    }

    LoadingIndicatorDrawable(Context context2, LoadingIndicatorSpec specs2, LoadingIndicatorDrawingDelegate drawingDelegate2, LoadingIndicatorAnimatorDelegate animatorDelegate2) {
        this.context = context2;
        this.specs = specs2;
        this.drawingDelegate = drawingDelegate2;
        this.animatorDelegate = animatorDelegate2;
        animatorDelegate2.registerDrawable(this);
        setAlpha(255);
    }

    public int getIntrinsicWidth() {
        return this.drawingDelegate.getPreferredWidth();
    }

    public int getIntrinsicHeight() {
        return this.drawingDelegate.getPreferredHeight();
    }

    public void draw(Canvas canvas) {
        Rect clipBounds = new Rect();
        Rect bounds = getBounds();
        if (!bounds.isEmpty() && isVisible() && canvas.getClipBounds(clipBounds)) {
            if (!isSystemAnimatorDisabled() || this.staticDummyDrawable == null) {
                canvas.save();
                this.drawingDelegate.adjustCanvas(canvas, bounds);
                this.drawingDelegate.drawContainer(canvas, this.paint, this.specs.containerColor, getAlpha());
                this.drawingDelegate.drawIndicator(canvas, this.paint, this.animatorDelegate.indicatorState, getAlpha());
                canvas.restore();
                return;
            }
            this.staticDummyDrawable.setBounds(bounds);
            this.staticDummyDrawable.setTint(this.specs.indicatorColors[0]);
            this.staticDummyDrawable.draw(canvas);
        }
    }

    public boolean setVisible(boolean visible, boolean restart) {
        return setVisible(visible, restart, visible);
    }

    public boolean setVisible(boolean visible, boolean restart, boolean animate) {
        boolean changed = super.setVisible(visible, restart);
        this.animatorDelegate.cancelAnimatorImmediately();
        if (visible && animate && !isSystemAnimatorDisabled()) {
            this.animatorDelegate.startAnimator();
        }
        return changed;
    }

    public void setAlpha(int alpha2) {
        if (this.alpha != alpha2) {
            this.alpha = alpha2;
            invalidateSelf();
        }
    }

    public int getAlpha() {
        return this.alpha;
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public int getOpacity() {
        return -3;
    }

    public void invalidateDrawable(Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, what, when);
        }
    }

    public void unscheduleDrawable(Drawable who, Runnable what) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, what);
        }
    }

    private boolean isSystemAnimatorDisabled() {
        if (this.animatorDurationScaleProvider == null || this.animatorDurationScaleProvider.getSystemAnimatorDurationScale(this.context.getContentResolver()) != 0.0f) {
            return false;
        }
        return true;
    }

    public Drawable getStaticDummyDrawable() {
        return this.staticDummyDrawable;
    }

    public void setStaticDummyDrawable(Drawable staticDummyDrawable2) {
        this.staticDummyDrawable = staticDummyDrawable2;
    }

    /* access modifiers changed from: package-private */
    public LoadingIndicatorAnimatorDelegate getAnimatorDelegate() {
        return this.animatorDelegate;
    }

    /* access modifiers changed from: package-private */
    public void setAnimatorDelegate(LoadingIndicatorAnimatorDelegate animatorDelegate2) {
        this.animatorDelegate = animatorDelegate2;
        animatorDelegate2.registerDrawable(this);
    }

    /* access modifiers changed from: package-private */
    public LoadingIndicatorDrawingDelegate getDrawingDelegate() {
        return this.drawingDelegate;
    }

    /* access modifiers changed from: package-private */
    public void setDrawingDelegate(LoadingIndicatorDrawingDelegate drawingDelegate2) {
        this.drawingDelegate = drawingDelegate2;
    }
}
