package com.google.android.material.carousel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import androidx.core.math.MathUtils;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.ClampedCornerSize;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.shape.ShapeableDelegate;

public class MaskableFrameLayout extends FrameLayout implements Maskable, Shapeable {
    private static final int NOT_SET = -1;
    private View.OnHoverListener hoverListener;
    private boolean isHovered;
    private final RectF maskRect;
    private float maskXPercentage;
    private OnMaskChangedListener onMaskChangedListener;
    private Boolean savedForceCompatClippingEnabled;
    private final Rect screenBoundsRect;
    private ShapeAppearanceModel shapeAppearanceModel;
    private final ShapeableDelegate shapeableDelegate;

    public MaskableFrameLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public MaskableFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaskableFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.maskXPercentage = -1.0f;
        this.maskRect = new RectF();
        this.screenBoundsRect = new Rect();
        this.shapeableDelegate = ShapeableDelegate.create(this);
        this.savedForceCompatClippingEnabled = null;
        this.isHovered = false;
        setShapeAppearanceModel(ShapeAppearanceModel.builder(context, attrs, defStyleAttr, 0, 0).build());
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.maskXPercentage != -1.0f) {
            updateMaskRectForMaskXPercentage();
        }
    }

    public void getFocusedRect(Rect r) {
        r.set((int) this.maskRect.left, (int) this.maskRect.top, (int) this.maskRect.right, (int) this.maskRect.bottom);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.savedForceCompatClippingEnabled != null) {
            this.shapeableDelegate.setForceCompatClippingEnabled(this, this.savedForceCompatClippingEnabled.booleanValue());
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        this.savedForceCompatClippingEnabled = Boolean.valueOf(this.shapeableDelegate.isForceCompatClippingEnabled());
        this.shapeableDelegate.setForceCompatClippingEnabled(this, true);
        super.onDetachedFromWindow();
    }

    public void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel2) {
        this.shapeAppearanceModel = shapeAppearanceModel2.withTransformedCornerSizes(new MaskableFrameLayout$$ExternalSyntheticLambda0());
        this.shapeableDelegate.onShapeAppearanceChanged(this, this.shapeAppearanceModel);
    }

    static /* synthetic */ CornerSize lambda$setShapeAppearanceModel$0(CornerSize cornerSize) {
        if (cornerSize instanceof AbsoluteCornerSize) {
            return ClampedCornerSize.createFromCornerSize((AbsoluteCornerSize) cornerSize);
        }
        return cornerSize;
    }

    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.shapeAppearanceModel;
    }

    @Deprecated
    public void setMaskXPercentage(float percentage) {
        float percentage2 = MathUtils.clamp(percentage, 0.0f, 1.0f);
        if (this.maskXPercentage != percentage2) {
            this.maskXPercentage = percentage2;
            updateMaskRectForMaskXPercentage();
        }
    }

    private void updateMaskRectForMaskXPercentage() {
        if (this.maskXPercentage != -1.0f) {
            float maskWidth = AnimationUtils.lerp(0.0f, ((float) getWidth()) / 2.0f, 0.0f, 1.0f, this.maskXPercentage);
            setMaskRectF(new RectF(maskWidth, 0.0f, ((float) getWidth()) - maskWidth, (float) getHeight()));
        }
    }

    public void setMaskRectF(RectF maskRect2) {
        this.maskRect.set(maskRect2);
        onMaskChanged();
    }

    @Deprecated
    public float getMaskXPercentage() {
        return this.maskXPercentage;
    }

    public RectF getMaskRectF() {
        return this.maskRect;
    }

    public void setOnMaskChangedListener(OnMaskChangedListener onMaskChangedListener2) {
        this.onMaskChangedListener = onMaskChangedListener2;
    }

    private void onMaskChanged() {
        this.shapeableDelegate.onMaskChanged(this, this.maskRect);
        if (this.onMaskChangedListener != null) {
            this.onMaskChangedListener.onMaskChanged(this.maskRect);
        }
    }

    public void setForceCompatClipping(boolean forceCompatClipping) {
        this.shapeableDelegate.setForceCompatClippingEnabled(this, forceCompatClipping);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.maskRect.isEmpty() && event.getAction() == 0) {
            if (!this.maskRect.contains(event.getX(), event.getY())) {
                return false;
            }
        }
        return super.onTouchEvent(event);
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!this.maskRect.isEmpty()) {
            if (!this.maskRect.contains(event.getX(), event.getY())) {
                return true;
            }
        }
        return super.onInterceptTouchEvent(event);
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        this.shapeableDelegate.maybeClip(canvas, new MaskableFrameLayout$$ExternalSyntheticLambda1(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$dispatchDraw$1$com-google-android-material-carousel-MaskableFrameLayout  reason: not valid java name */
    public /* synthetic */ void m1642lambda$dispatchDraw$1$comgoogleandroidmaterialcarouselMaskableFrameLayout(Canvas x$0) {
        super.dispatchDraw(x$0);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.getBoundsInScreen(this.screenBoundsRect);
        if (getX() > 0.0f) {
            this.screenBoundsRect.left = (int) (((float) this.screenBoundsRect.left) + this.maskRect.left);
        }
        if (getY() > 0.0f) {
            this.screenBoundsRect.top = (int) (((float) this.screenBoundsRect.top) + this.maskRect.top);
        }
        this.screenBoundsRect.right = this.screenBoundsRect.left + Math.round(this.maskRect.width());
        this.screenBoundsRect.bottom = this.screenBoundsRect.top + Math.round(this.maskRect.height());
        info.setBoundsInScreen(this.screenBoundsRect);
    }

    public void setOnHoverListener(View.OnHoverListener l) {
        this.hoverListener = l;
    }

    public boolean onHoverEvent(MotionEvent event) {
        int action = event.getAction();
        if (!this.maskRect.isEmpty() && (action == 9 || action == 10 || action == 7)) {
            if (!this.maskRect.contains(event.getX(), event.getY())) {
                if (this.isHovered && this.hoverListener != null) {
                    event.setAction(10);
                    this.hoverListener.onHover(this, event);
                }
                this.isHovered = false;
                return false;
            }
        }
        if (this.hoverListener != null) {
            if (!this.isHovered && action == 7) {
                event.setAction(9);
                this.isHovered = true;
            }
            if (action == 7 || action == 9) {
                this.isHovered = true;
            }
            this.hoverListener.onHover(this, event);
        }
        return super.onHoverEvent(event);
    }
}
