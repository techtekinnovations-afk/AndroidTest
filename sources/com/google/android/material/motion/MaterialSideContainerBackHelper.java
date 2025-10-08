package com.google.android.material.motion;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.BackEventCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;

public class MaterialSideContainerBackHelper extends MaterialBackAnimationHelper<View> {
    private final float maxScaleXDistanceGrow;
    private final float maxScaleXDistanceShrink;
    private final float maxScaleYDistance;

    public MaterialSideContainerBackHelper(View view) {
        super(view);
        Resources resources = view.getResources();
        this.maxScaleXDistanceShrink = resources.getDimension(R.dimen.m3_back_progress_side_container_max_scale_x_distance_shrink);
        this.maxScaleXDistanceGrow = resources.getDimension(R.dimen.m3_back_progress_side_container_max_scale_x_distance_grow);
        this.maxScaleYDistance = resources.getDimension(R.dimen.m3_back_progress_side_container_max_scale_y_distance);
    }

    public void startBackProgress(BackEventCompat backEvent) {
        super.onStartBackProgress(backEvent);
    }

    public void updateBackProgress(BackEventCompat backEvent, int gravity) {
        if (super.onUpdateBackProgress(backEvent) != null) {
            updateBackProgress(backEvent.getProgress(), backEvent.getSwipeEdge() == 0, gravity);
        }
    }

    public void updateBackProgress(float progress, boolean leftSwipeEdge, int gravity) {
        int i;
        float f;
        float progress2 = interpolateProgress(progress);
        boolean leftGravity = checkAbsoluteGravity(gravity, 3);
        boolean swipeEdgeMatchesGravity = leftSwipeEdge == leftGravity;
        int width = this.view.getWidth();
        int height = this.view.getHeight();
        if (((float) width) <= 0.0f) {
        } else if (((float) height) <= 0.0f) {
            float f2 = progress2;
        } else {
            float maxScaleXDeltaShrink = this.maxScaleXDistanceShrink / ((float) width);
            float maxScaleXDeltaGrow = this.maxScaleXDistanceGrow / ((float) width);
            float maxScaleYDelta = this.maxScaleYDistance / ((float) height);
            this.view.setPivotX(leftGravity ? 0.0f : (float) width);
            float scaleXDelta = AnimationUtils.lerp(0.0f, swipeEdgeMatchesGravity ? maxScaleXDeltaGrow : -maxScaleXDeltaShrink, progress2);
            float scaleX = scaleXDelta + 1.0f;
            float scaleY = 1.0f - AnimationUtils.lerp(0.0f, maxScaleYDelta, progress2);
            if (Float.isNaN(scaleX)) {
            } else if (Float.isNaN(scaleY)) {
                float f3 = progress2;
            } else {
                this.view.setScaleX(scaleX);
                this.view.setScaleY(scaleY);
                if (this.view instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) this.view;
                    int i2 = 0;
                    while (true) {
                        float progress3 = progress2;
                        if (i2 < viewGroup.getChildCount()) {
                            View childView = viewGroup.getChildAt(i2);
                            if (leftGravity) {
                                i = i2;
                                f = (float) ((width - childView.getRight()) + childView.getWidth());
                            } else {
                                i = i2;
                                f = (float) (-childView.getLeft());
                            }
                            childView.setPivotX(f);
                            childView.setPivotY((float) (-childView.getTop()));
                            float childScaleX = swipeEdgeMatchesGravity ? 1.0f - scaleXDelta : 1.0f;
                            float childScaleY = scaleY != 0.0f ? (scaleX / scaleY) * childScaleX : 1.0f;
                            if (Float.isNaN(childScaleX)) {
                                float childScaleX2 = childScaleY;
                            } else if (!Float.isNaN(childScaleY)) {
                                childView.setScaleX(childScaleX);
                                float f4 = childScaleX;
                                childView.setScaleY(childScaleY);
                            }
                            i2 = i + 1;
                            progress2 = progress3;
                        } else {
                            int i3 = i2;
                            return;
                        }
                    }
                }
            }
        }
    }

    public void finishBackProgress(BackEventCompat backEvent, final int gravity, Animator.AnimatorListener animatorListener, ValueAnimator.AnimatorUpdateListener finishAnimatorUpdateListener) {
        final boolean leftSwipeEdge = backEvent.getSwipeEdge() == 0;
        boolean leftGravity = checkAbsoluteGravity(gravity, 3);
        float scaledWidth = (((float) this.view.getWidth()) * this.view.getScaleX()) + ((float) getEdgeMargin(leftGravity));
        ObjectAnimator finishAnimator = ObjectAnimator.ofFloat(this.view, View.TRANSLATION_X, new float[]{leftGravity ? -scaledWidth : scaledWidth});
        if (finishAnimatorUpdateListener != null) {
            finishAnimator.addUpdateListener(finishAnimatorUpdateListener);
        }
        finishAnimator.setInterpolator(new FastOutSlowInInterpolator());
        finishAnimator.setDuration((long) AnimationUtils.lerp(this.hideDurationMax, this.hideDurationMin, backEvent.getProgress()));
        finishAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                MaterialSideContainerBackHelper.this.view.setTranslationX(0.0f);
                MaterialSideContainerBackHelper.this.updateBackProgress(0.0f, leftSwipeEdge, gravity);
            }
        });
        if (animatorListener != null) {
            finishAnimator.addListener(animatorListener);
        }
        finishAnimator.start();
    }

    public void cancelBackProgress() {
        if (super.onCancelBackProgress() != null) {
            AnimatorSet cancelAnimatorSet = new AnimatorSet();
            cancelAnimatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.view, View.SCALE_X, new float[]{1.0f}), ObjectAnimator.ofFloat(this.view, View.SCALE_Y, new float[]{1.0f})});
            if (this.view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) this.view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    cancelAnimatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(viewGroup.getChildAt(i), View.SCALE_Y, new float[]{1.0f})});
                }
            }
            cancelAnimatorSet.setDuration((long) this.cancelDuration);
            cancelAnimatorSet.start();
        }
    }

    private boolean checkAbsoluteGravity(int gravity, int checkFor) {
        return (Gravity.getAbsoluteGravity(gravity, this.view.getLayoutDirection()) & checkFor) == checkFor;
    }

    private int getEdgeMargin(boolean leftGravity) {
        ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            return 0;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        return leftGravity ? marginLayoutParams.leftMargin : marginLayoutParams.rightMargin;
    }
}
