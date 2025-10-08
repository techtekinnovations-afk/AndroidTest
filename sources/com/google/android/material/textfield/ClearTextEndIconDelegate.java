package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.motion.MotionUtils;

class ClearTextEndIconDelegate extends EndIconDelegate {
    private static final float ANIMATION_SCALE_FROM_VALUE = 0.8f;
    private static final int DEFAULT_ANIMATION_FADE_DURATION = 100;
    private static final int DEFAULT_ANIMATION_SCALE_DURATION = 150;
    private final int animationFadeDuration;
    private final TimeInterpolator animationFadeInterpolator;
    private final int animationScaleDuration;
    private final TimeInterpolator animationScaleInterpolator;
    private EditText editText;
    private AnimatorSet iconInAnim;
    private ValueAnimator iconOutAnim;
    private final View.OnFocusChangeListener onFocusChangeListener = new ClearTextEndIconDelegate$$ExternalSyntheticLambda1(this);
    private final View.OnClickListener onIconClickListener = new ClearTextEndIconDelegate$$ExternalSyntheticLambda0(this);

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-google-android-material-textfield-ClearTextEndIconDelegate  reason: not valid java name */
    public /* synthetic */ void m1724lambda$new$0$comgoogleandroidmaterialtextfieldClearTextEndIconDelegate(View view) {
        if (this.editText != null) {
            Editable text = this.editText.getText();
            if (text != null) {
                text.clear();
            }
            refreshIconState();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$new$1$com-google-android-material-textfield-ClearTextEndIconDelegate  reason: not valid java name */
    public /* synthetic */ void m1725lambda$new$1$comgoogleandroidmaterialtextfieldClearTextEndIconDelegate(View view, boolean hasFocus) {
        animateIcon(shouldBeVisible());
    }

    ClearTextEndIconDelegate(EndCompoundLayout endLayout) {
        super(endLayout);
        this.animationFadeDuration = MotionUtils.resolveThemeDuration(endLayout.getContext(), R.attr.motionDurationShort3, 100);
        this.animationScaleDuration = MotionUtils.resolveThemeDuration(endLayout.getContext(), R.attr.motionDurationShort3, DEFAULT_ANIMATION_SCALE_DURATION);
        this.animationFadeInterpolator = MotionUtils.resolveThemeInterpolator(endLayout.getContext(), R.attr.motionEasingLinearInterpolator, AnimationUtils.LINEAR_INTERPOLATOR);
        this.animationScaleInterpolator = MotionUtils.resolveThemeInterpolator(endLayout.getContext(), R.attr.motionEasingEmphasizedInterpolator, AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
    }

    /* access modifiers changed from: package-private */
    public void setUp() {
        initAnimators();
    }

    /* access modifiers changed from: package-private */
    public void tearDown() {
        if (this.editText != null) {
            this.editText.post(new ClearTextEndIconDelegate$$ExternalSyntheticLambda3(this));
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$tearDown$2$com-google-android-material-textfield-ClearTextEndIconDelegate  reason: not valid java name */
    public /* synthetic */ void m1726lambda$tearDown$2$comgoogleandroidmaterialtextfieldClearTextEndIconDelegate() {
        animateIcon(true);
    }

    /* access modifiers changed from: package-private */
    public int getIconDrawableResId() {
        return R.drawable.mtrl_ic_cancel;
    }

    /* access modifiers changed from: package-private */
    public int getIconContentDescriptionResId() {
        return R.string.clear_text_end_icon_content_description;
    }

    /* access modifiers changed from: package-private */
    public void onSuffixVisibilityChanged(boolean visible) {
        if (this.endLayout.getSuffixText() != null) {
            animateIcon(visible);
        }
    }

    /* access modifiers changed from: package-private */
    public View.OnClickListener getOnIconClickListener() {
        return this.onIconClickListener;
    }

    public void onEditTextAttached(EditText editText2) {
        this.editText = editText2;
        this.textInputLayout.setEndIconVisible(shouldBeVisible());
    }

    /* access modifiers changed from: package-private */
    public void afterEditTextChanged(Editable s) {
        if (this.endLayout.getSuffixText() == null) {
            animateIcon(shouldBeVisible());
        }
    }

    /* access modifiers changed from: package-private */
    public View.OnFocusChangeListener getOnEditTextFocusChangeListener() {
        return this.onFocusChangeListener;
    }

    /* access modifiers changed from: package-private */
    public View.OnFocusChangeListener getOnIconViewFocusChangeListener() {
        return this.onFocusChangeListener;
    }

    private void animateIcon(boolean show) {
        boolean shouldSkipAnimation = this.endLayout.isEndIconVisible() == show;
        if (show && !this.iconInAnim.isRunning()) {
            this.iconOutAnim.cancel();
            this.iconInAnim.start();
            if (shouldSkipAnimation) {
                this.iconInAnim.end();
            }
        } else if (!show) {
            this.iconInAnim.cancel();
            this.iconOutAnim.start();
            if (shouldSkipAnimation) {
                this.iconOutAnim.end();
            }
        }
    }

    private void initAnimators() {
        ValueAnimator scaleAnimator = getScaleAnimator();
        ValueAnimator fadeAnimator = getAlphaAnimator(0.0f, 1.0f);
        this.iconInAnim = new AnimatorSet();
        this.iconInAnim.playTogether(new Animator[]{scaleAnimator, fadeAnimator});
        this.iconInAnim.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                ClearTextEndIconDelegate.this.endLayout.setEndIconVisible(true);
            }
        });
        this.iconOutAnim = getAlphaAnimator(1.0f, 0.0f);
        this.iconOutAnim.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                ClearTextEndIconDelegate.this.endLayout.setEndIconVisible(false);
            }
        });
    }

    private ValueAnimator getAlphaAnimator(float... values) {
        ValueAnimator animator = ValueAnimator.ofFloat(values);
        animator.setInterpolator(this.animationFadeInterpolator);
        animator.setDuration((long) this.animationFadeDuration);
        animator.addUpdateListener(new ClearTextEndIconDelegate$$ExternalSyntheticLambda2(this));
        return animator;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$getAlphaAnimator$3$com-google-android-material-textfield-ClearTextEndIconDelegate  reason: not valid java name */
    public /* synthetic */ void m1722lambda$getAlphaAnimator$3$comgoogleandroidmaterialtextfieldClearTextEndIconDelegate(ValueAnimator animation) {
        this.endIconView.setAlpha(((Float) animation.getAnimatedValue()).floatValue());
    }

    private ValueAnimator getScaleAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{ANIMATION_SCALE_FROM_VALUE, 1.0f});
        animator.setInterpolator(this.animationScaleInterpolator);
        animator.setDuration((long) this.animationScaleDuration);
        animator.addUpdateListener(new ClearTextEndIconDelegate$$ExternalSyntheticLambda4(this));
        return animator;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$getScaleAnimator$4$com-google-android-material-textfield-ClearTextEndIconDelegate  reason: not valid java name */
    public /* synthetic */ void m1723lambda$getScaleAnimator$4$comgoogleandroidmaterialtextfieldClearTextEndIconDelegate(ValueAnimator animation) {
        float scale = ((Float) animation.getAnimatedValue()).floatValue();
        this.endIconView.setScaleX(scale);
        this.endIconView.setScaleY(scale);
    }

    private boolean shouldBeVisible() {
        return this.editText != null && (this.editText.hasFocus() || this.endIconView.hasFocus()) && this.editText.getText().length() > 0;
    }
}
