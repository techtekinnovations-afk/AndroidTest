package com.google.android.material.loadingindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.ProgressBar;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.progressindicator.AnimatorDurationScaleProvider;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.Arrays;

public final class LoadingIndicator extends View implements Drawable.Callback {
    static final int DEF_STYLE_RES = R.style.Widget_Material3_LoadingIndicator;
    private final LoadingIndicatorDrawable drawable;
    private final LoadingIndicatorSpec specs;

    public LoadingIndicator(Context context) {
        this(context, (AttributeSet) null);
    }

    public LoadingIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.loadingIndicatorStyle);
    }

    public LoadingIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        Context context2 = getContext();
        this.drawable = LoadingIndicatorDrawable.create(context2, new LoadingIndicatorSpec(context2, attrs, defStyleAttr));
        this.drawable.setCallback(this);
        this.specs = this.drawable.getDrawingDelegate().specs;
        setAnimatorDurationScaleProvider(new AnimatorDurationScaleProvider());
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        LoadingIndicatorDrawingDelegate drawingDelegate = this.drawable.getDrawingDelegate();
        int preferredWidth = drawingDelegate.getPreferredWidth() + getPaddingLeft() + getPaddingRight();
        int preferredHeight = drawingDelegate.getPreferredHeight() + getPaddingTop() + getPaddingBottom();
        if (widthMode == Integer.MIN_VALUE) {
            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(Math.min(widthSize, preferredWidth), 1073741824);
        } else if (widthMode == 0) {
            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(preferredWidth, 1073741824);
        }
        if (heightMode == Integer.MIN_VALUE) {
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(Math.min(heightSize, preferredHeight), 1073741824);
        } else if (heightMode == 0) {
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(preferredHeight, 1073741824);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int saveCount = canvas.save();
        if (!(getPaddingLeft() == 0 && getPaddingTop() == 0)) {
            canvas.translate((float) getPaddingLeft(), (float) getPaddingTop());
        }
        if (!(getPaddingRight() == 0 && getPaddingBottom() == 0)) {
            canvas.clipRect(0, 0, getWidth() - (getPaddingLeft() + getPaddingRight()), getHeight() - (getPaddingTop() + getPaddingBottom()));
        }
        this.drawable.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.drawable.setBounds(0, 0, w, h);
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        this.drawable.setVisible(visibleToUser(), false, visibility == 0);
    }

    /* access modifiers changed from: protected */
    public void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        this.drawable.setVisible(visibleToUser(), false, visibility == 0);
    }

    public void invalidateDrawable(Drawable drawable2) {
        invalidate();
    }

    public CharSequence getAccessibilityClassName() {
        return ProgressBar.class.getName();
    }

    /* access modifiers changed from: package-private */
    public boolean visibleToUser() {
        return isAttachedToWindow() && getWindowVisibility() == 0 && isEffectivelyVisible();
    }

    /* access modifiers changed from: package-private */
    public boolean isEffectivelyVisible() {
        View current = this;
        while (current.getVisibility() == 0) {
            ViewParent parent = current.getParent();
            if (parent == null) {
                if (getWindowVisibility() == 0) {
                    return true;
                }
                return false;
            } else if (!(parent instanceof View)) {
                return true;
            } else {
                current = (View) parent;
            }
        }
        return false;
    }

    public LoadingIndicatorDrawable getDrawable() {
        return this.drawable;
    }

    public void setIndicatorSize(int indicatorSize) {
        if (this.specs.indicatorSize != indicatorSize) {
            this.specs.indicatorSize = indicatorSize;
            requestLayout();
            invalidate();
        }
    }

    public int getIndicatorSize() {
        return this.specs.indicatorSize;
    }

    public void setContainerWidth(int containerWidth) {
        if (this.specs.containerWidth != containerWidth) {
            this.specs.containerWidth = containerWidth;
            requestLayout();
            invalidate();
        }
    }

    public int getContainerWidth() {
        return this.specs.containerWidth;
    }

    public void setContainerHeight(int containerHeight) {
        if (this.specs.containerHeight != containerHeight) {
            this.specs.containerHeight = containerHeight;
            requestLayout();
            invalidate();
        }
    }

    public int getContainerHeight() {
        return this.specs.containerHeight;
    }

    public void setIndicatorColor(int... indicatorColors) {
        if (indicatorColors.length == 0) {
            indicatorColors = new int[]{MaterialColors.getColor(getContext(), androidx.appcompat.R.attr.colorPrimary, -1)};
        }
        if (!Arrays.equals(getIndicatorColor(), indicatorColors)) {
            this.specs.indicatorColors = indicatorColors;
            this.drawable.getAnimatorDelegate().invalidateSpecValues();
            invalidate();
        }
    }

    public int[] getIndicatorColor() {
        return this.specs.indicatorColors;
    }

    public void setContainerColor(int containerColor) {
        if (this.specs.containerColor != containerColor) {
            this.specs.containerColor = containerColor;
            invalidate();
        }
    }

    public int getContainerColor() {
        return this.specs.containerColor;
    }

    public void setAnimatorDurationScaleProvider(AnimatorDurationScaleProvider animatorDurationScaleProvider) {
        this.drawable.animatorDurationScaleProvider = animatorDurationScaleProvider;
    }
}
