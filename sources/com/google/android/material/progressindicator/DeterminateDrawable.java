package com.google.android.material.progressindicator;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import androidx.core.math.MathUtils;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.progressindicator.BaseProgressIndicatorSpec;
import com.google.android.material.progressindicator.DrawingDelegate;

public final class DeterminateDrawable<S extends BaseProgressIndicatorSpec> extends DrawableWithAnimatedVisibilityChange {
    private static final int AMPLITUDE_ANIMATION_DURATION_MS = 500;
    private static final float FULL_AMPLITUDE_FRACTION_MAX = 0.9f;
    private static final float FULL_AMPLITUDE_FRACTION_MIN = 0.1f;
    static final float GAP_RAMP_DOWN_THRESHOLD = 0.01f;
    private static final FloatPropertyCompat<DeterminateDrawable<?>> INDICATOR_LENGTH_IN_LEVEL = new FloatPropertyCompat<DeterminateDrawable<?>>("indicatorLevel") {
        public float getValue(DeterminateDrawable<?> drawable) {
            return drawable.getIndicatorFraction() * 10000.0f;
        }

        public void setValue(DeterminateDrawable<?> drawable, float value) {
            drawable.setIndicatorFraction(value / 10000.0f);
            drawable.maybeStartAmplitudeAnimator((int) value);
        }
    };
    private static final int MAX_DRAWABLE_LEVEL = 10000;
    private static final int PHASE_ANIMATION_DURATION_MS = 1000;
    private static final float SPRING_FORCE_STIFFNESS = 50.0f;
    private final DrawingDelegate.ActiveIndicator activeIndicator;
    private ValueAnimator amplitudeAnimator;
    private TimeInterpolator amplitudeInterpolator;
    private TimeInterpolator amplitudeOffInterpolator;
    private TimeInterpolator amplitudeOnInterpolator;
    private DrawingDelegate<S> drawingDelegate;
    private final ValueAnimator phaseAnimator;
    private boolean skipAnimationOnLevelChange = false;
    private final SpringAnimation springAnimation;
    private final SpringForce springForce;
    private float targetAmplitudeFraction;

    public /* bridge */ /* synthetic */ void clearAnimationCallbacks() {
        super.clearAnimationCallbacks();
    }

    public /* bridge */ /* synthetic */ int getAlpha() {
        return super.getAlpha();
    }

    public /* bridge */ /* synthetic */ int getOpacity() {
        return super.getOpacity();
    }

    public /* bridge */ /* synthetic */ boolean hideNow() {
        return super.hideNow();
    }

    public /* bridge */ /* synthetic */ boolean isHiding() {
        return super.isHiding();
    }

    public /* bridge */ /* synthetic */ boolean isRunning() {
        return super.isRunning();
    }

    public /* bridge */ /* synthetic */ boolean isShowing() {
        return super.isShowing();
    }

    public /* bridge */ /* synthetic */ void registerAnimationCallback(Animatable2Compat.AnimationCallback animationCallback) {
        super.registerAnimationCallback(animationCallback);
    }

    public /* bridge */ /* synthetic */ void setAlpha(int i) {
        super.setAlpha(i);
    }

    public /* bridge */ /* synthetic */ void setColorFilter(ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
    }

    public /* bridge */ /* synthetic */ boolean setVisible(boolean z, boolean z2) {
        return super.setVisible(z, z2);
    }

    public /* bridge */ /* synthetic */ boolean setVisible(boolean z, boolean z2, boolean z3) {
        return super.setVisible(z, z2, z3);
    }

    public /* bridge */ /* synthetic */ void start() {
        super.start();
    }

    public /* bridge */ /* synthetic */ void stop() {
        super.stop();
    }

    public /* bridge */ /* synthetic */ boolean unregisterAnimationCallback(Animatable2Compat.AnimationCallback animationCallback) {
        return super.unregisterAnimationCallback(animationCallback);
    }

    DeterminateDrawable(Context context, BaseProgressIndicatorSpec baseSpec, DrawingDelegate<S> drawingDelegate2) {
        super(context, baseSpec);
        setDrawingDelegate(drawingDelegate2);
        this.activeIndicator = new DrawingDelegate.ActiveIndicator();
        this.activeIndicator.isDeterminate = true;
        this.springForce = new SpringForce();
        this.springForce.setDampingRatio(1.0f);
        this.springForce.setStiffness(50.0f);
        this.springAnimation = new SpringAnimation(this, INDICATOR_LENGTH_IN_LEVEL);
        this.springAnimation.setSpring(this.springForce);
        this.phaseAnimator = new ValueAnimator();
        this.phaseAnimator.setDuration(1000);
        this.phaseAnimator.setFloatValues(new float[]{0.0f, 1.0f});
        this.phaseAnimator.setRepeatCount(-1);
        this.phaseAnimator.addUpdateListener(new DeterminateDrawable$$ExternalSyntheticLambda1(this, baseSpec));
        if (baseSpec.hasWavyEffect(true) && baseSpec.waveSpeed != 0) {
            this.phaseAnimator.start();
        }
        setGrowFraction(1.0f);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-google-android-material-progressindicator-DeterminateDrawable  reason: not valid java name */
    public /* synthetic */ void m1697lambda$new$0$comgoogleandroidmaterialprogressindicatorDeterminateDrawable(BaseProgressIndicatorSpec baseSpec, ValueAnimator animation) {
        if (baseSpec.hasWavyEffect(true) && baseSpec.waveSpeed != 0 && isVisible()) {
            invalidateSelf();
        }
    }

    private void maybeInitializeAmplitudeAnimator() {
        if (this.amplitudeAnimator == null) {
            this.amplitudeOnInterpolator = MotionUtils.resolveThemeInterpolator(this.context, R.attr.motionEasingStandardInterpolator, AnimationUtils.LINEAR_INTERPOLATOR);
            this.amplitudeOffInterpolator = MotionUtils.resolveThemeInterpolator(this.context, R.attr.motionEasingEmphasizedAccelerateInterpolator, AnimationUtils.LINEAR_INTERPOLATOR);
            this.amplitudeAnimator = new ValueAnimator();
            this.amplitudeAnimator.setDuration(500);
            this.amplitudeAnimator.setFloatValues(new float[]{0.0f, 1.0f});
            this.amplitudeAnimator.setInterpolator((TimeInterpolator) null);
            this.amplitudeAnimator.addUpdateListener(new DeterminateDrawable$$ExternalSyntheticLambda0(this));
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$maybeInitializeAmplitudeAnimator$1$com-google-android-material-progressindicator-DeterminateDrawable  reason: not valid java name */
    public /* synthetic */ void m1696lambda$maybeInitializeAmplitudeAnimator$1$comgoogleandroidmaterialprogressindicatorDeterminateDrawable(ValueAnimator animation) {
        this.activeIndicator.amplitudeFraction = this.amplitudeInterpolator.getInterpolation(this.amplitudeAnimator.getAnimatedFraction());
    }

    public static DeterminateDrawable<LinearProgressIndicatorSpec> createLinearDrawable(Context context, LinearProgressIndicatorSpec spec) {
        return createLinearDrawable(context, spec, new LinearDrawingDelegate(spec));
    }

    static DeterminateDrawable<LinearProgressIndicatorSpec> createLinearDrawable(Context context, LinearProgressIndicatorSpec spec, LinearDrawingDelegate drawingDelegate2) {
        return new DeterminateDrawable<>(context, spec, drawingDelegate2);
    }

    public static DeterminateDrawable<CircularProgressIndicatorSpec> createCircularDrawable(Context context, CircularProgressIndicatorSpec spec) {
        return createCircularDrawable(context, spec, new CircularDrawingDelegate(spec));
    }

    static DeterminateDrawable<CircularProgressIndicatorSpec> createCircularDrawable(Context context, CircularProgressIndicatorSpec spec, CircularDrawingDelegate drawingDelegate2) {
        return new DeterminateDrawable<>(context, spec, drawingDelegate2);
    }

    public void addSpringAnimationEndListener(DynamicAnimation.OnAnimationEndListener listener) {
        this.springAnimation.addEndListener(listener);
    }

    public void removeSpringAnimationEndListener(DynamicAnimation.OnAnimationEndListener listener) {
        this.springAnimation.removeEndListener(listener);
    }

    /* access modifiers changed from: package-private */
    public boolean setVisibleInternal(boolean visible, boolean restart, boolean animate) {
        boolean changed = super.setVisibleInternal(visible, restart, animate);
        float systemAnimatorDurationScale = this.animatorDurationScaleProvider.getSystemAnimatorDurationScale(this.context.getContentResolver());
        if (systemAnimatorDurationScale == 0.0f) {
            this.skipAnimationOnLevelChange = true;
        } else {
            this.skipAnimationOnLevelChange = false;
            this.springForce.setStiffness(50.0f / systemAnimatorDurationScale);
        }
        return changed;
    }

    public void jumpToCurrentState() {
        this.springAnimation.skipToEnd();
        setIndicatorFraction(((float) getLevel()) / 10000.0f);
    }

    /* access modifiers changed from: protected */
    public boolean onLevelChange(int level) {
        float nextAmplitudeFraction = getAmplitudeFractionFromLevel(level);
        if (this.skipAnimationOnLevelChange) {
            this.springAnimation.skipToEnd();
            setIndicatorFraction(((float) level) / 10000.0f);
            setAmplitudeFraction(nextAmplitudeFraction);
            return true;
        }
        this.springAnimation.setStartValue(getIndicatorFraction() * 10000.0f);
        this.springAnimation.animateToFinalPosition((float) level);
        return true;
    }

    public int getIntrinsicWidth() {
        return this.drawingDelegate.getPreferredWidth();
    }

    public int getIntrinsicHeight() {
        return this.drawingDelegate.getPreferredHeight();
    }

    /* access modifiers changed from: package-private */
    public void setLevelByFraction(float fraction) {
        setLevel((int) (10000.0f * fraction));
    }

    private float getAmplitudeFractionFromLevel(int level) {
        if (((float) level) < 1000.0f || ((float) level) > 9000.0f) {
            return 0.0f;
        }
        return 1.0f;
    }

    /* access modifiers changed from: private */
    public void maybeStartAmplitudeAnimator(int level) {
        if (this.baseSpec.hasWavyEffect(true)) {
            maybeInitializeAmplitudeAnimator();
            float newAmplitudeFraction = getAmplitudeFractionFromLevel(level);
            if (newAmplitudeFraction != this.targetAmplitudeFraction) {
                if (this.amplitudeAnimator.isRunning()) {
                    this.amplitudeAnimator.cancel();
                }
                this.targetAmplitudeFraction = newAmplitudeFraction;
                if (this.targetAmplitudeFraction == 1.0f) {
                    this.amplitudeInterpolator = this.amplitudeOnInterpolator;
                    this.amplitudeAnimator.start();
                    return;
                }
                this.amplitudeInterpolator = this.amplitudeOffInterpolator;
                this.amplitudeAnimator.reverse();
            } else if (!this.amplitudeAnimator.isRunning()) {
                setAmplitudeFraction(newAmplitudeFraction);
            }
        }
    }

    public void draw(Canvas canvas) {
        int gapSize;
        if (!getBounds().isEmpty() && isVisible() && canvas.getClipBounds(this.clipBounds)) {
            canvas.save();
            this.drawingDelegate.validateSpecAndAdjustCanvas(canvas, getBounds(), getGrowFraction(), isShowing(), isHiding());
            this.activeIndicator.phaseFraction = getPhaseFraction();
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setAntiAlias(true);
            this.activeIndicator.color = this.baseSpec.indicatorColors[0];
            if (this.baseSpec.indicatorTrackGapSize > 0) {
                if (this.drawingDelegate instanceof LinearDrawingDelegate) {
                    gapSize = this.baseSpec.indicatorTrackGapSize;
                } else {
                    gapSize = (int) ((((float) this.baseSpec.indicatorTrackGapSize) * MathUtils.clamp(getIndicatorFraction(), 0.0f, (float) GAP_RAMP_DOWN_THRESHOLD)) / GAP_RAMP_DOWN_THRESHOLD);
                }
                this.drawingDelegate.fillTrack(canvas, this.paint, getIndicatorFraction(), 1.0f, this.baseSpec.trackColor, getAlpha(), gapSize);
            } else {
                this.drawingDelegate.fillTrack(canvas, this.paint, 0.0f, 1.0f, this.baseSpec.trackColor, getAlpha(), 0);
            }
            this.drawingDelegate.fillIndicator(canvas, this.paint, this.activeIndicator, getAlpha());
            this.drawingDelegate.drawStopIndicator(canvas, this.paint, this.baseSpec.indicatorColors[0], getAlpha());
            canvas.restore();
        }
    }

    /* access modifiers changed from: private */
    public float getIndicatorFraction() {
        return this.activeIndicator.endFraction;
    }

    /* access modifiers changed from: private */
    public void setIndicatorFraction(float indicatorFraction) {
        this.activeIndicator.endFraction = indicatorFraction;
        invalidateSelf();
    }

    private void setAmplitudeFraction(float amplitudeFraction) {
        this.activeIndicator.amplitudeFraction = amplitudeFraction;
        invalidateSelf();
    }

    /* access modifiers changed from: package-private */
    public DrawingDelegate<S> getDrawingDelegate() {
        return this.drawingDelegate;
    }

    /* access modifiers changed from: package-private */
    public void setDrawingDelegate(DrawingDelegate<S> drawingDelegate2) {
        this.drawingDelegate = drawingDelegate2;
    }

    /* access modifiers changed from: package-private */
    public void setEnforcedDrawing(boolean enforced) {
        if (enforced && !this.phaseAnimator.isRunning()) {
            this.phaseAnimator.start();
        } else if (!enforced && this.phaseAnimator.isRunning()) {
            this.phaseAnimator.cancel();
        }
    }
}
