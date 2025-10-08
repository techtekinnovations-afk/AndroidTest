package com.google.android.material.loadingindicator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.util.Property;
import androidx.core.math.MathUtils;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.google.android.material.animation.ArgbEvaluatorCompat;
import com.google.android.material.loadingindicator.LoadingIndicatorDrawingDelegate;

class LoadingIndicatorAnimatorDelegate {
    private static final Property<LoadingIndicatorAnimatorDelegate, Float> ANIMATION_FRACTION = new Property<LoadingIndicatorAnimatorDelegate, Float>(Float.class, "animationFraction") {
        public Float get(LoadingIndicatorAnimatorDelegate delegate) {
            return Float.valueOf(delegate.getAnimationFraction());
        }

        public void set(LoadingIndicatorAnimatorDelegate delegate, Float value) {
            delegate.setAnimationFraction(value.floatValue());
        }
    };
    private static final int CONSTANT_ROTATION_PER_SHAPE_DEGREES = 50;
    private static final int DURATION_PER_SHAPE_IN_MS = 650;
    private static final int EXTRA_ROTATION_PER_SHAPE_DEGREES = 90;
    private static final FloatPropertyCompat<LoadingIndicatorAnimatorDelegate> MORPH_FACTOR = new FloatPropertyCompat<LoadingIndicatorAnimatorDelegate>("morphFactor") {
        public float getValue(LoadingIndicatorAnimatorDelegate delegate) {
            return delegate.getMorphFactor();
        }

        public void setValue(LoadingIndicatorAnimatorDelegate delegate, float value) {
            delegate.setMorphFactor(value);
        }
    };
    private static final float SPRING_DAMPING_RATIO = 0.6f;
    private static final float SPRING_STIFFNESS = 200.0f;
    private float animationFraction;
    private ObjectAnimator animator;
    LoadingIndicatorDrawable drawable;
    LoadingIndicatorDrawingDelegate.IndicatorState indicatorState = new LoadingIndicatorDrawingDelegate.IndicatorState();
    private float morphFactor;
    private int morphFactorTarget;
    LoadingIndicatorSpec specs;
    /* access modifiers changed from: private */
    public SpringAnimation springAnimation;

    static /* synthetic */ int access$004(LoadingIndicatorAnimatorDelegate x0) {
        int i = x0.morphFactorTarget + 1;
        x0.morphFactorTarget = i;
        return i;
    }

    public LoadingIndicatorAnimatorDelegate(LoadingIndicatorSpec specs2) {
        this.specs = specs2;
    }

    /* access modifiers changed from: protected */
    public void registerDrawable(LoadingIndicatorDrawable drawable2) {
        this.drawable = drawable2;
    }

    /* access modifiers changed from: package-private */
    public void startAnimator() {
        maybeInitializeAnimators();
        resetPropertiesForNewStart();
        this.springAnimation.animateToFinalPosition((float) this.morphFactorTarget);
        this.animator.start();
    }

    /* access modifiers changed from: package-private */
    public void cancelAnimatorImmediately() {
        if (this.animator != null) {
            this.animator.cancel();
        }
        if (this.springAnimation != null) {
            this.springAnimation.skipToEnd();
        }
    }

    /* access modifiers changed from: package-private */
    public void invalidateSpecValues() {
        resetPropertiesForNewStart();
    }

    /* access modifiers changed from: package-private */
    public void resetPropertiesForNewStart() {
        this.morphFactorTarget = 1;
        setMorphFactor(0.0f);
        this.indicatorState.color = this.specs.indicatorColors[0];
    }

    private void maybeInitializeAnimators() {
        if (this.springAnimation == null) {
            this.springAnimation = (SpringAnimation) new SpringAnimation(this, MORPH_FACTOR).setSpring(new SpringForce().setStiffness(200.0f).setDampingRatio(SPRING_DAMPING_RATIO)).setMinimumVisibleChange(0.01f);
        }
        if (this.animator == null) {
            this.animator = ObjectAnimator.ofFloat(this, ANIMATION_FRACTION, new float[]{0.0f, 1.0f});
            this.animator.setDuration(650);
            this.animator.setInterpolator((TimeInterpolator) null);
            this.animator.setRepeatCount(-1);
            this.animator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationRepeat(Animator animation) {
                    super.onAnimationRepeat(animation);
                    LoadingIndicatorAnimatorDelegate.this.springAnimation.animateToFinalPosition((float) LoadingIndicatorAnimatorDelegate.access$004(LoadingIndicatorAnimatorDelegate.this));
                }
            });
        }
    }

    private void updateIndicatorRotation(int playtime) {
        float morphFactorBase = (float) (this.morphFactorTarget - 1);
        float morphFactorPerShape = this.morphFactor - morphFactorBase;
        float timeFactorPerShape = ((float) playtime) / 650.0f;
        if (timeFactorPerShape == 1.0f) {
            timeFactorPerShape = 0.0f;
        }
        this.indicatorState.rotationDegree = 140.0f * morphFactorBase;
        this.indicatorState.rotationDegree += 50.0f * timeFactorPerShape;
        this.indicatorState.rotationDegree += 90.0f * morphFactorPerShape;
        this.indicatorState.rotationDegree %= 360.0f;
    }

    private void updateIndicatorShapeAndColor() {
        this.indicatorState.morphFraction = this.morphFactor;
        int startColorIndex = (this.morphFactorTarget - 1) % this.specs.indicatorColors.length;
        int endColorIndex = (startColorIndex + 1) % this.specs.indicatorColors.length;
        int startColor = this.specs.indicatorColors[startColorIndex];
        int endColor = this.specs.indicatorColors[endColorIndex];
        this.indicatorState.color = ArgbEvaluatorCompat.getInstance().evaluate(MathUtils.clamp(this.morphFactor - ((float) (this.morphFactorTarget - 1)), 0.0f, 1.0f), Integer.valueOf(startColor), Integer.valueOf(endColor)).intValue();
    }

    /* access modifiers changed from: private */
    public float getAnimationFraction() {
        return this.animationFraction;
    }

    /* access modifiers changed from: package-private */
    public void setAnimationFraction(float fraction) {
        this.animationFraction = fraction;
        updateIndicatorRotation((int) (this.animationFraction * 650.0f));
        if (this.drawable != null) {
            this.drawable.invalidateSelf();
        }
    }

    /* access modifiers changed from: private */
    public float getMorphFactor() {
        return this.morphFactor;
    }

    /* access modifiers changed from: package-private */
    public void setMorphFactor(float factor) {
        this.morphFactor = factor;
        updateIndicatorShapeAndColor();
        if (this.drawable != null) {
            this.drawable.invalidateSelf();
        }
    }

    /* access modifiers changed from: package-private */
    public void setMorphFactorTarget(int factorTarget) {
        this.morphFactorTarget = factorTarget;
    }
}
