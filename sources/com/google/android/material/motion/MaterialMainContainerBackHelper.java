package com.google.android.material.motion;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.RoundedCorner;
import android.view.View;
import android.view.WindowInsets;
import androidx.activity.BackEventCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ClippableRoundedCornerLayout;
import com.google.android.material.internal.ViewUtils;

public class MaterialMainContainerBackHelper extends MaterialBackAnimationHelper<View> {
    private static final float MIN_SCALE = 0.9f;
    private float[] expandedCornerRadii;
    private Rect initialHideFromClipBounds;
    private Rect initialHideToClipBounds;
    private float initialTouchY;
    private final float maxTranslationY;
    private final float minEdgeGap;

    public MaterialMainContainerBackHelper(View view) {
        super(view);
        Resources resources = view.getResources();
        this.minEdgeGap = resources.getDimension(R.dimen.m3_back_progress_main_container_min_edge_gap);
        this.maxTranslationY = resources.getDimension(R.dimen.m3_back_progress_main_container_max_translation_y);
    }

    public Rect getInitialHideToClipBounds() {
        return this.initialHideToClipBounds;
    }

    public Rect getInitialHideFromClipBounds() {
        return this.initialHideFromClipBounds;
    }

    public void startBackProgress(BackEventCompat backEvent, View collapsedView) {
        super.onStartBackProgress(backEvent);
        startBackProgress(backEvent.getTouchY(), collapsedView);
    }

    public void startBackProgress(float touchY, View collapsedView) {
        this.initialHideToClipBounds = ViewUtils.calculateRectFromBounds(this.view);
        if (collapsedView != null) {
            this.initialHideFromClipBounds = ViewUtils.calculateOffsetRectFromBounds(this.view, collapsedView);
        }
        this.initialTouchY = touchY;
    }

    public void updateBackProgress(BackEventCompat backEvent, View collapsedView, float collapsedCornerSize) {
        if (super.onUpdateBackProgress(backEvent) != null) {
            if (!(collapsedView == null || collapsedView.getVisibility() == 4)) {
                collapsedView.setVisibility(4);
            }
            updateBackProgress(backEvent.getProgress(), backEvent.getSwipeEdge() == 0, backEvent.getTouchY(), collapsedCornerSize);
        }
    }

    public void updateBackProgress(float progress, boolean leftSwipeEdge, float touchY, float collapsedCornerSize) {
        float progress2 = interpolateProgress(progress);
        float width = (float) this.view.getWidth();
        float height = (float) this.view.getHeight();
        if (width <= 0.0f) {
            float f = collapsedCornerSize;
        } else if (height <= 0.0f) {
            float f2 = collapsedCornerSize;
        } else {
            float scale = AnimationUtils.lerp(1.0f, (float) MIN_SCALE, progress2);
            float translationX = AnimationUtils.lerp(0.0f, Math.max(0.0f, ((width - (MIN_SCALE * width)) / 2.0f) - this.minEdgeGap), progress2) * ((float) (leftSwipeEdge ? 1 : -1));
            float maxTranslationY2 = Math.min(Math.max(0.0f, ((height - (scale * height)) / 2.0f) - this.minEdgeGap), this.maxTranslationY);
            float yDelta = touchY - this.initialTouchY;
            float translationY = AnimationUtils.lerp(0.0f, maxTranslationY2, Math.abs(yDelta) / height) * Math.signum(yDelta);
            if (Float.isNaN(scale) || Float.isNaN(translationX)) {
                float f3 = collapsedCornerSize;
            } else if (Float.isNaN(translationY)) {
                float f4 = collapsedCornerSize;
            } else {
                this.view.setScaleX(scale);
                this.view.setScaleY(scale);
                this.view.setTranslationX(translationX);
                this.view.setTranslationY(translationY);
                if (this.view instanceof ClippableRoundedCornerLayout) {
                    ((ClippableRoundedCornerLayout) this.view).updateCornerRadii(lerpCornerRadii(getExpandedCornerRadii(), collapsedCornerSize, progress2));
                } else {
                    float f5 = collapsedCornerSize;
                }
            }
        }
    }

    public void finishBackProgress(long duration, View collapsedView) {
        AnimatorSet resetAnimator = createResetScaleAndTranslationAnimator(collapsedView);
        resetAnimator.setDuration(duration);
        resetAnimator.start();
        resetInitialValues();
    }

    public void cancelBackProgress(View collapsedView) {
        if (super.onCancelBackProgress() != null) {
            AnimatorSet cancelAnimatorSet = createResetScaleAndTranslationAnimator(collapsedView);
            if (this.view instanceof ClippableRoundedCornerLayout) {
                cancelAnimatorSet.playTogether(new Animator[]{createCornerAnimator((ClippableRoundedCornerLayout) this.view)});
            }
            cancelAnimatorSet.setDuration((long) this.cancelDuration);
            cancelAnimatorSet.start();
            resetInitialValues();
        }
    }

    private void resetInitialValues() {
        this.initialTouchY = 0.0f;
        this.initialHideToClipBounds = null;
        this.initialHideFromClipBounds = null;
    }

    private AnimatorSet createResetScaleAndTranslationAnimator(final View collapsedView) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.view, View.SCALE_X, new float[]{1.0f}), ObjectAnimator.ofFloat(this.view, View.SCALE_Y, new float[]{1.0f}), ObjectAnimator.ofFloat(this.view, View.TRANSLATION_X, new float[]{0.0f}), ObjectAnimator.ofFloat(this.view, View.TRANSLATION_Y, new float[]{0.0f})});
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                if (collapsedView != null) {
                    collapsedView.setVisibility(0);
                }
            }
        });
        return animatorSet;
    }

    private ValueAnimator createCornerAnimator(ClippableRoundedCornerLayout clippableRoundedCornerLayout) {
        ValueAnimator cornerAnimator = ValueAnimator.ofObject(new MaterialMainContainerBackHelper$$ExternalSyntheticLambda0(), new Object[]{clippableRoundedCornerLayout.getCornerRadii(), getExpandedCornerRadii()});
        cornerAnimator.addUpdateListener(new MaterialMainContainerBackHelper$$ExternalSyntheticLambda1(clippableRoundedCornerLayout));
        return cornerAnimator;
    }

    /* access modifiers changed from: private */
    public static float[] lerpCornerRadii(float[] startValue, float[] endValue, float fraction) {
        float f = fraction;
        return new float[]{AnimationUtils.lerp(startValue[0], endValue[0], f), AnimationUtils.lerp(startValue[1], endValue[1], f), AnimationUtils.lerp(startValue[2], endValue[2], f), AnimationUtils.lerp(startValue[3], endValue[3], f), AnimationUtils.lerp(startValue[4], endValue[4], f), AnimationUtils.lerp(startValue[5], endValue[5], f), AnimationUtils.lerp(startValue[6], endValue[6], f), AnimationUtils.lerp(startValue[7], endValue[7], f)};
    }

    private static float[] lerpCornerRadii(float[] startValue, float endValue, float fraction) {
        float f = endValue;
        float f2 = fraction;
        return new float[]{AnimationUtils.lerp(startValue[0], f, f2), AnimationUtils.lerp(startValue[1], f, f2), AnimationUtils.lerp(startValue[2], f, f2), AnimationUtils.lerp(startValue[3], f, f2), AnimationUtils.lerp(startValue[4], f, f2), AnimationUtils.lerp(startValue[5], f, f2), AnimationUtils.lerp(startValue[6], f, f2), AnimationUtils.lerp(startValue[7], f, f2)};
    }

    public float[] getExpandedCornerRadii() {
        if (this.expandedCornerRadii == null) {
            this.expandedCornerRadii = calculateExpandedCornerRadii();
        }
        return this.expandedCornerRadii;
    }

    public void clearExpandedCornerRadii() {
        this.expandedCornerRadii = null;
    }

    private float[] calculateExpandedCornerRadii() {
        WindowInsets insets;
        int topRight;
        int bottomRight;
        char c;
        int bottomLeft;
        if (Build.VERSION.SDK_INT < 31 || (insets = this.view.getRootWindowInsets()) == null) {
            return new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
        }
        DisplayMetrics displayMetrics = this.view.getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        int[] location = new int[2];
        this.view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        int width = this.view.getWidth();
        int height = this.view.getHeight();
        int topLeft = (x == 0 && y == 0) ? getRoundedCornerRadius(insets, 0) : 0;
        if (x + width < screenWidth || y != 0) {
            topRight = 0;
        } else {
            topRight = getRoundedCornerRadius(insets, 1);
        }
        if (x + width < screenWidth || y + height < screenHeight) {
            bottomRight = 0;
        } else {
            bottomRight = getRoundedCornerRadius(insets, 2);
        }
        if (x == 0) {
            c = 1;
            if (y + height >= screenHeight) {
                bottomLeft = getRoundedCornerRadius(insets, 3);
                float[] fArr = new float[8];
                fArr[0] = (float) topLeft;
                fArr[c] = (float) topLeft;
                fArr[2] = (float) topRight;
                fArr[3] = (float) topRight;
                fArr[4] = (float) bottomRight;
                fArr[5] = (float) bottomRight;
                fArr[6] = (float) bottomLeft;
                fArr[7] = (float) bottomLeft;
                return fArr;
            }
        } else {
            c = 1;
        }
        bottomLeft = 0;
        float[] fArr2 = new float[8];
        fArr2[0] = (float) topLeft;
        fArr2[c] = (float) topLeft;
        fArr2[2] = (float) topRight;
        fArr2[3] = (float) topRight;
        fArr2[4] = (float) bottomRight;
        fArr2[5] = (float) bottomRight;
        fArr2[6] = (float) bottomLeft;
        fArr2[7] = (float) bottomLeft;
        return fArr2;
    }

    private int getRoundedCornerRadius(WindowInsets insets, int position) {
        RoundedCorner roundedCorner = insets.getRoundedCorner(position);
        if (roundedCorner != null) {
            return roundedCorner.getRadius();
        }
        return 0;
    }
}
