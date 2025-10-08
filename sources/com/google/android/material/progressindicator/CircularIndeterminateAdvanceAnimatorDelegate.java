package com.google.android.material.progressindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.util.Property;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.animation.ArgbEvaluatorCompat;
import com.google.android.material.progressindicator.DrawingDelegate;

final class CircularIndeterminateAdvanceAnimatorDelegate extends IndeterminateAnimatorDelegate<ObjectAnimator> {
    private static final Property<CircularIndeterminateAdvanceAnimatorDelegate, Float> ANIMATION_FRACTION = new Property<CircularIndeterminateAdvanceAnimatorDelegate, Float>(Float.class, "animationFraction") {
        public Float get(CircularIndeterminateAdvanceAnimatorDelegate delegate) {
            return Float.valueOf(delegate.getAnimationFraction());
        }

        public void set(CircularIndeterminateAdvanceAnimatorDelegate delegate, Float value) {
            delegate.setAnimationFraction(value.floatValue());
        }
    };
    private static final Property<CircularIndeterminateAdvanceAnimatorDelegate, Float> COMPLETE_END_FRACTION = new Property<CircularIndeterminateAdvanceAnimatorDelegate, Float>(Float.class, "completeEndFraction") {
        public Float get(CircularIndeterminateAdvanceAnimatorDelegate delegate) {
            return Float.valueOf(delegate.getCompleteEndFraction());
        }

        public void set(CircularIndeterminateAdvanceAnimatorDelegate delegate, Float value) {
            delegate.setCompleteEndFraction(value.floatValue());
        }
    };
    private static final int CONSTANT_ROTATION_DEGREES = 1520;
    private static final int[] DELAY_TO_COLLAPSE_IN_MS = {667, 2017, 3367, 4717};
    private static final int[] DELAY_TO_EXPAND_IN_MS = {0, 1350, 2700, 4050};
    private static final int[] DELAY_TO_FADE_IN_MS = {1000, 2350, 3700, 5050};
    private static final int DURATION_TO_COLLAPSE_IN_MS = 667;
    private static final int DURATION_TO_COMPLETE_END_IN_MS = 333;
    private static final int DURATION_TO_EXPAND_IN_MS = 667;
    private static final int DURATION_TO_FADE_IN_MS = 333;
    private static final int EXTRA_DEGREES_PER_CYCLE = 250;
    private static final int TAIL_DEGREES_OFFSET = -20;
    private static final int TOTAL_CYCLES = 4;
    private static final int TOTAL_DURATION_IN_MS = 5400;
    private float animationFraction;
    private ObjectAnimator animator;
    Animatable2Compat.AnimationCallback animatorCompleteCallback = null;
    /* access modifiers changed from: private */
    public final BaseProgressIndicatorSpec baseSpec;
    private ObjectAnimator completeEndAnimator;
    private float completeEndFraction;
    /* access modifiers changed from: private */
    public int indicatorColorIndexOffset = 0;
    private final FastOutSlowInInterpolator interpolator;

    public CircularIndeterminateAdvanceAnimatorDelegate(CircularProgressIndicatorSpec spec) {
        super(1);
        this.baseSpec = spec;
        this.interpolator = new FastOutSlowInInterpolator();
    }

    /* access modifiers changed from: package-private */
    public void startAnimator() {
        maybeInitializeAnimators();
        resetPropertiesForNewStart();
        this.animator.start();
    }

    private void maybeInitializeAnimators() {
        if (this.animator == null) {
            this.animator = ObjectAnimator.ofFloat(this, ANIMATION_FRACTION, new float[]{0.0f, 1.0f});
            this.animator.setDuration((long) (this.baseSpec.indeterminateAnimatorDurationScale * 5400.0f));
            this.animator.setInterpolator((TimeInterpolator) null);
            this.animator.setRepeatCount(-1);
            this.animator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationRepeat(Animator animation) {
                    super.onAnimationRepeat(animation);
                    int unused = CircularIndeterminateAdvanceAnimatorDelegate.this.indicatorColorIndexOffset = (CircularIndeterminateAdvanceAnimatorDelegate.this.indicatorColorIndexOffset + 4) % CircularIndeterminateAdvanceAnimatorDelegate.this.baseSpec.indicatorColors.length;
                }
            });
        }
        if (this.completeEndAnimator == null) {
            this.completeEndAnimator = ObjectAnimator.ofFloat(this, COMPLETE_END_FRACTION, new float[]{0.0f, 1.0f});
            this.completeEndAnimator.setDuration((long) (this.baseSpec.indeterminateAnimatorDurationScale * 333.0f));
            this.completeEndAnimator.setInterpolator(this.interpolator);
            this.completeEndAnimator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    CircularIndeterminateAdvanceAnimatorDelegate.this.cancelAnimatorImmediately();
                    if (CircularIndeterminateAdvanceAnimatorDelegate.this.animatorCompleteCallback != null) {
                        CircularIndeterminateAdvanceAnimatorDelegate.this.animatorCompleteCallback.onAnimationEnd(CircularIndeterminateAdvanceAnimatorDelegate.this.drawable);
                    }
                }
            });
        }
    }

    private void updateAnimatorsDuration() {
        maybeInitializeAnimators();
        this.animator.setDuration((long) (this.baseSpec.indeterminateAnimatorDurationScale * 5400.0f));
        this.completeEndAnimator.setDuration((long) (this.baseSpec.indeterminateAnimatorDurationScale * 333.0f));
    }

    /* access modifiers changed from: package-private */
    public void cancelAnimatorImmediately() {
        if (this.animator != null) {
            this.animator.cancel();
        }
    }

    /* access modifiers changed from: package-private */
    public void requestCancelAnimatorAfterCurrentCycle() {
        if (this.completeEndAnimator != null && !this.completeEndAnimator.isRunning()) {
            if (this.drawable.isVisible()) {
                this.completeEndAnimator.start();
            } else {
                cancelAnimatorImmediately();
            }
        }
    }

    public void invalidateSpecValues() {
        updateAnimatorsDuration();
        resetPropertiesForNewStart();
    }

    public void registerAnimatorsCompleteCallback(Animatable2Compat.AnimationCallback callback) {
        this.animatorCompleteCallback = callback;
    }

    public void unregisterAnimatorsCompleteCallback() {
        this.animatorCompleteCallback = null;
    }

    private void updateSegmentPositions(int playtime) {
        DrawingDelegate.ActiveIndicator activeIndicator = (DrawingDelegate.ActiveIndicator) this.activeIndicators.get(0);
        activeIndicator.startFraction = (this.animationFraction * 1520.0f) - 0.21875f;
        activeIndicator.endFraction = this.animationFraction * 1520.0f;
        for (int cycleIndex = 0; cycleIndex < 4; cycleIndex++) {
            activeIndicator.endFraction += this.interpolator.getInterpolation(getFractionInRange(playtime, DELAY_TO_EXPAND_IN_MS[cycleIndex], 667)) * 250.0f;
            activeIndicator.startFraction += this.interpolator.getInterpolation(getFractionInRange(playtime, DELAY_TO_COLLAPSE_IN_MS[cycleIndex], 667)) * 250.0f;
        }
        activeIndicator.startFraction += (activeIndicator.endFraction - activeIndicator.startFraction) * this.completeEndFraction;
        activeIndicator.startFraction /= 360.0f;
        activeIndicator.endFraction /= 360.0f;
    }

    private void maybeUpdateSegmentColors(int playtime) {
        int cycleIndex = 0;
        while (cycleIndex < 4) {
            float timeFraction = getFractionInRange(playtime, DELAY_TO_FADE_IN_MS[cycleIndex], 333);
            if (timeFraction <= 0.0f || timeFraction >= 1.0f) {
                cycleIndex++;
            } else {
                int startColorIndex = (this.indicatorColorIndexOffset + cycleIndex) % this.baseSpec.indicatorColors.length;
                int endColorIndex = (startColorIndex + 1) % this.baseSpec.indicatorColors.length;
                int startColor = this.baseSpec.indicatorColors[startColorIndex];
                int endColor = this.baseSpec.indicatorColors[endColorIndex];
                ((DrawingDelegate.ActiveIndicator) this.activeIndicators.get(0)).color = ArgbEvaluatorCompat.getInstance().evaluate(this.interpolator.getInterpolation(timeFraction), Integer.valueOf(startColor), Integer.valueOf(endColor)).intValue();
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void resetPropertiesForNewStart() {
        this.indicatorColorIndexOffset = 0;
        ((DrawingDelegate.ActiveIndicator) this.activeIndicators.get(0)).color = this.baseSpec.indicatorColors[0];
        this.completeEndFraction = 0.0f;
    }

    /* access modifiers changed from: private */
    public float getAnimationFraction() {
        return this.animationFraction;
    }

    /* access modifiers changed from: package-private */
    public void setAnimationFraction(float fraction) {
        this.animationFraction = fraction;
        int playtime = (int) (this.animationFraction * 5400.0f);
        updateSegmentPositions(playtime);
        maybeUpdateSegmentColors(playtime);
        this.drawable.invalidateSelf();
    }

    /* access modifiers changed from: private */
    public float getCompleteEndFraction() {
        return this.completeEndFraction;
    }

    /* access modifiers changed from: private */
    public void setCompleteEndFraction(float fraction) {
        this.completeEndFraction = fraction;
    }
}
