package com.google.android.material.progressindicator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.google.android.material.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

public class LinearProgressIndicator extends BaseProgressIndicator<LinearProgressIndicatorSpec> {
    public static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_LinearProgressIndicator;
    public static final int INDETERMINATE_ANIMATION_TYPE_CONTIGUOUS = 0;
    public static final int INDETERMINATE_ANIMATION_TYPE_DISJOINT = 1;
    public static final int INDICATOR_DIRECTION_END_TO_START = 3;
    public static final int INDICATOR_DIRECTION_LEFT_TO_RIGHT = 0;
    public static final int INDICATOR_DIRECTION_RIGHT_TO_LEFT = 1;
    public static final int INDICATOR_DIRECTION_START_TO_END = 2;

    @Retention(RetentionPolicy.SOURCE)
    public @interface IndeterminateAnimationType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface IndicatorDirection {
    }

    public LinearProgressIndicator(Context context) {
        this(context, (AttributeSet) null);
    }

    public LinearProgressIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.linearProgressIndicatorStyle);
    }

    public LinearProgressIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, DEF_STYLE_RES);
        initializeDrawables();
        this.initialized = true;
    }

    /* access modifiers changed from: package-private */
    public LinearProgressIndicatorSpec createSpec(Context context, AttributeSet attrs) {
        return new LinearProgressIndicatorSpec(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int i = bottom;
        int bottom2 = right;
        int right2 = top;
        int top2 = left;
        int left2 = changed;
        LinearProgressIndicatorSpec linearProgressIndicatorSpec = (LinearProgressIndicatorSpec) this.spec;
        boolean z = true;
        if (!(((LinearProgressIndicatorSpec) this.spec).indicatorDirection == 1 || ((getLayoutDirection() == 1 && ((LinearProgressIndicatorSpec) this.spec).indicatorDirection == 2) || (getLayoutDirection() == 0 && ((LinearProgressIndicatorSpec) this.spec).indicatorDirection == 3)))) {
            z = false;
        }
        linearProgressIndicatorSpec.drawHorizontallyInverse = z;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        int contentWidth = w - (getPaddingLeft() + getPaddingRight());
        int contentHeight = h - (getPaddingTop() + getPaddingBottom());
        Drawable drawable = getIndeterminateDrawable();
        if (drawable != null) {
            drawable.setBounds(0, 0, contentWidth, contentHeight);
        }
        Drawable drawable2 = getProgressDrawable();
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, contentWidth, contentHeight);
        }
    }

    private void initializeDrawables() {
        LinearDrawingDelegate drawingDelegate = new LinearDrawingDelegate((LinearProgressIndicatorSpec) this.spec);
        setIndeterminateDrawable(IndeterminateDrawable.createLinearDrawable(getContext(), (LinearProgressIndicatorSpec) this.spec, drawingDelegate));
        setProgressDrawable(DeterminateDrawable.createLinearDrawable(getContext(), (LinearProgressIndicatorSpec) this.spec, drawingDelegate));
    }

    public void setIndicatorColor(int... indicatorColors) {
        super.setIndicatorColor(indicatorColors);
        ((LinearProgressIndicatorSpec) this.spec).validateSpec();
    }

    public void setTrackCornerRadius(int trackCornerRadius) {
        super.setTrackCornerRadius(trackCornerRadius);
        ((LinearProgressIndicatorSpec) this.spec).validateSpec();
        invalidate();
    }

    public int getTrackInnerCornerRadius() {
        return ((LinearProgressIndicatorSpec) this.spec).trackInnerCornerRadius;
    }

    public void setTrackInnerCornerRadius(int trackInnerCornerRadius) {
        if (((LinearProgressIndicatorSpec) this.spec).trackInnerCornerRadius != trackInnerCornerRadius) {
            ((LinearProgressIndicatorSpec) this.spec).trackInnerCornerRadius = Math.round(Math.min((float) trackInnerCornerRadius, ((float) ((LinearProgressIndicatorSpec) this.spec).trackThickness) / 2.0f));
            ((LinearProgressIndicatorSpec) this.spec).useRelativeTrackInnerCornerRadius = false;
            ((LinearProgressIndicatorSpec) this.spec).hasInnerCornerRadius = true;
            ((LinearProgressIndicatorSpec) this.spec).validateSpec();
            invalidate();
        }
    }

    public void setTrackInnerCornerRadiusFraction(float trackInnerCornerRadiusFraction) {
        if (((LinearProgressIndicatorSpec) this.spec).trackInnerCornerRadiusFraction != trackInnerCornerRadiusFraction) {
            ((LinearProgressIndicatorSpec) this.spec).trackInnerCornerRadiusFraction = Math.min(trackInnerCornerRadiusFraction, 0.5f);
            ((LinearProgressIndicatorSpec) this.spec).useRelativeTrackInnerCornerRadius = true;
            ((LinearProgressIndicatorSpec) this.spec).hasInnerCornerRadius = true;
            ((LinearProgressIndicatorSpec) this.spec).validateSpec();
            invalidate();
        }
    }

    public int getTrackStopIndicatorSize() {
        return ((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorSize;
    }

    public void setTrackStopIndicatorSize(int trackStopIndicatorSize) {
        if (((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorSize != trackStopIndicatorSize) {
            ((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorSize = Math.min(trackStopIndicatorSize, ((LinearProgressIndicatorSpec) this.spec).trackThickness);
            ((LinearProgressIndicatorSpec) this.spec).validateSpec();
            invalidate();
        }
    }

    public Integer getTrackStopIndicatorPadding() {
        return ((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorPadding;
    }

    public void setTrackStopIndicatorPadding(Integer trackStopIndicatorPadding) {
        if (!Objects.equals(((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorPadding, trackStopIndicatorPadding)) {
            ((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorPadding = trackStopIndicatorPadding;
            invalidate();
        }
    }

    public int getIndeterminateAnimationType() {
        return ((LinearProgressIndicatorSpec) this.spec).indeterminateAnimationType;
    }

    public void setIndeterminateAnimationType(int indeterminateAnimationType) {
        if (((LinearProgressIndicatorSpec) this.spec).indeterminateAnimationType != indeterminateAnimationType) {
            if (!visibleToUser() || !isIndeterminate()) {
                ((LinearProgressIndicatorSpec) this.spec).indeterminateAnimationType = indeterminateAnimationType;
                ((LinearProgressIndicatorSpec) this.spec).validateSpec();
                if (indeterminateAnimationType == 0) {
                    getIndeterminateDrawable().setAnimatorDelegate(new LinearIndeterminateContiguousAnimatorDelegate((LinearProgressIndicatorSpec) this.spec));
                } else {
                    getIndeterminateDrawable().setAnimatorDelegate(new LinearIndeterminateDisjointAnimatorDelegate(getContext(), (LinearProgressIndicatorSpec) this.spec));
                }
                registerSwitchIndeterminateModeCallback();
                invalidate();
                return;
            }
            throw new IllegalStateException("Cannot change indeterminate animation type while the progress indicator is show in indeterminate mode.");
        }
    }

    public int getIndicatorDirection() {
        return ((LinearProgressIndicatorSpec) this.spec).indicatorDirection;
    }

    public void setIndicatorDirection(int indicatorDirection) {
        ((LinearProgressIndicatorSpec) this.spec).indicatorDirection = indicatorDirection;
        LinearProgressIndicatorSpec linearProgressIndicatorSpec = (LinearProgressIndicatorSpec) this.spec;
        boolean z = true;
        if (!(indicatorDirection == 1 || ((getLayoutDirection() == 1 && ((LinearProgressIndicatorSpec) this.spec).indicatorDirection == 2) || (getLayoutDirection() == 0 && indicatorDirection == 3)))) {
            z = false;
        }
        linearProgressIndicatorSpec.drawHorizontallyInverse = z;
        invalidate();
    }

    public void setProgressCompat(int progress, boolean animated) {
        if (this.spec == null || ((LinearProgressIndicatorSpec) this.spec).indeterminateAnimationType != 0 || !isIndeterminate()) {
            super.setProgressCompat(progress, animated);
        }
    }
}
