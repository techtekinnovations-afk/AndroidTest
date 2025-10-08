package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.core.content.ContextCompat;
import androidx.core.util.Preconditions;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.animation.AnimatorSetCompat;
import com.google.android.material.animation.ImageMatrixProperty;
import com.google.android.material.animation.MatrixEvaluator;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shadow.ShadowViewDelegate;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class FloatingActionButtonImpl {
    static final int ANIM_STATE_HIDING = 1;
    static final int ANIM_STATE_NONE = 0;
    static final int ANIM_STATE_SHOWING = 2;
    static final long ELEVATION_ANIM_DELAY = 100;
    static final long ELEVATION_ANIM_DURATION = 100;
    static final TimeInterpolator ELEVATION_ANIM_INTERPOLATOR = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
    static final int[] EMPTY_STATE_SET = new int[0];
    static final int[] ENABLED_STATE_SET = {16842910};
    static final int[] FOCUSED_ENABLED_STATE_SET = {16842908, 16842910};
    private static final int HIDE_ANIM_DURATION_ATTR = R.attr.motionDurationMedium1;
    private static final int HIDE_ANIM_EASING_ATTR = R.attr.motionEasingEmphasizedAccelerateInterpolator;
    private static final float HIDE_ICON_SCALE = 0.4f;
    private static final float HIDE_OPACITY = 0.0f;
    private static final float HIDE_SCALE = 0.4f;
    static final int[] HOVERED_ENABLED_STATE_SET = {16843623, 16842910};
    static final int[] HOVERED_FOCUSED_ENABLED_STATE_SET = {16843623, 16842908, 16842910};
    static final int[] PRESSED_ENABLED_STATE_SET = {16842919, 16842910};
    static final float SHADOW_MULTIPLIER = 1.5f;
    private static final int SHOW_ANIM_DURATION_ATTR = R.attr.motionDurationLong2;
    private static final int SHOW_ANIM_EASING_ATTR = R.attr.motionEasingEmphasizedInterpolator;
    private static final float SHOW_ICON_SCALE = 1.0f;
    private static final float SHOW_OPACITY = 1.0f;
    private static final float SHOW_SCALE = 1.0f;
    private static final float SPEC_HIDE_ICON_SCALE = 0.0f;
    private static final float SPEC_HIDE_SCALE = 0.0f;
    /* access modifiers changed from: private */
    public int animState = 0;
    BorderDrawable borderDrawable;
    Drawable contentBackground;
    /* access modifiers changed from: private */
    public Animator currentAnimator;
    float elevation;
    boolean ensureMinTouchTargetSize;
    private ArrayList<Animator.AnimatorListener> hideListeners;
    private MotionSpec hideMotionSpec;
    float hoveredFocusedTranslationZ;
    /* access modifiers changed from: private */
    public float imageMatrixScale = 1.0f;
    private int maxImageSize;
    int minTouchTargetSize;
    private ViewTreeObserver.OnPreDrawListener preDrawListener;
    float pressedTranslationZ;
    Drawable rippleDrawable;
    boolean shadowPaddingEnabled = true;
    final ShadowViewDelegate shadowViewDelegate;
    ShapeAppearanceModel shapeAppearance;
    MaterialShapeDrawable shapeDrawable;
    private ArrayList<Animator.AnimatorListener> showListeners;
    private MotionSpec showMotionSpec;
    private StateListAnimator stateListAnimator;
    private final Matrix tmpMatrix = new Matrix();
    private final Rect tmpRect = new Rect();
    private final RectF tmpRectF1 = new RectF();
    private final RectF tmpRectF2 = new RectF();
    private ArrayList<InternalTransformationCallback> transformationCallbacks;
    final FloatingActionButton view;

    interface InternalTransformationCallback {
        void onScaleChanged();

        void onTranslationChanged();
    }

    interface InternalVisibilityChangedListener {
        void onHidden();

        void onShown();
    }

    FloatingActionButtonImpl(FloatingActionButton view2, ShadowViewDelegate shadowViewDelegate2) {
        this.view = view2;
        this.shadowViewDelegate = shadowViewDelegate2;
    }

    /* access modifiers changed from: package-private */
    public void initializeBackgroundDrawable(ColorStateList backgroundTint, PorterDuff.Mode backgroundTintMode, ColorStateList rippleColor, int borderWidth) {
        Drawable rippleContent;
        this.shapeDrawable = createShapeDrawable();
        this.shapeDrawable.setTintList(backgroundTint);
        if (backgroundTintMode != null) {
            this.shapeDrawable.setTintMode(backgroundTintMode);
        }
        this.shapeDrawable.initializeElevationOverlay(this.view.getContext());
        if (borderWidth > 0) {
            this.borderDrawable = createBorderDrawable(borderWidth, backgroundTint);
            rippleContent = new LayerDrawable(new Drawable[]{(Drawable) Preconditions.checkNotNull(this.borderDrawable), (Drawable) Preconditions.checkNotNull(this.shapeDrawable)});
        } else {
            this.borderDrawable = null;
            rippleContent = this.shapeDrawable;
        }
        this.rippleDrawable = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(rippleColor), rippleContent, (Drawable) null);
        this.contentBackground = this.rippleDrawable;
    }

    /* access modifiers changed from: package-private */
    public void setBackgroundTintList(ColorStateList tint) {
        if (this.shapeDrawable != null) {
            this.shapeDrawable.setTintList(tint);
        }
        if (this.borderDrawable != null) {
            this.borderDrawable.setBorderTint(tint);
        }
    }

    /* access modifiers changed from: package-private */
    public void setBackgroundTintMode(PorterDuff.Mode tintMode) {
        if (this.shapeDrawable != null) {
            this.shapeDrawable.setTintMode(tintMode);
        }
    }

    /* access modifiers changed from: package-private */
    public void setMinTouchTargetSize(int minTouchTargetSize2) {
        this.minTouchTargetSize = minTouchTargetSize2;
    }

    /* access modifiers changed from: package-private */
    public void setRippleColor(ColorStateList rippleColor) {
        if (this.rippleDrawable instanceof RippleDrawable) {
            ((RippleDrawable) this.rippleDrawable).setColor(RippleUtils.sanitizeRippleDrawableColor(rippleColor));
        } else if (this.rippleDrawable != null) {
            this.rippleDrawable.setTintList(RippleUtils.sanitizeRippleDrawableColor(rippleColor));
        }
    }

    /* access modifiers changed from: package-private */
    public final void setElevation(float elevation2) {
        if (this.elevation != elevation2) {
            this.elevation = elevation2;
            onElevationsChanged(this.elevation, this.hoveredFocusedTranslationZ, this.pressedTranslationZ);
        }
    }

    /* access modifiers changed from: package-private */
    public float getElevation() {
        return this.view.getElevation();
    }

    /* access modifiers changed from: package-private */
    public float getHoveredFocusedTranslationZ() {
        return this.hoveredFocusedTranslationZ;
    }

    /* access modifiers changed from: package-private */
    public float getPressedTranslationZ() {
        return this.pressedTranslationZ;
    }

    /* access modifiers changed from: package-private */
    public final void setHoveredFocusedTranslationZ(float translationZ) {
        if (this.hoveredFocusedTranslationZ != translationZ) {
            this.hoveredFocusedTranslationZ = translationZ;
            onElevationsChanged(this.elevation, this.hoveredFocusedTranslationZ, this.pressedTranslationZ);
        }
    }

    /* access modifiers changed from: package-private */
    public final void setPressedTranslationZ(float translationZ) {
        if (this.pressedTranslationZ != translationZ) {
            this.pressedTranslationZ = translationZ;
            onElevationsChanged(this.elevation, this.hoveredFocusedTranslationZ, this.pressedTranslationZ);
        }
    }

    /* access modifiers changed from: package-private */
    public final void setMaxImageSize(int maxImageSize2) {
        if (this.maxImageSize != maxImageSize2) {
            this.maxImageSize = maxImageSize2;
            updateImageMatrixScale();
        }
    }

    /* access modifiers changed from: package-private */
    public final void updateImageMatrixScale() {
        setImageMatrixScale(this.imageMatrixScale);
    }

    /* access modifiers changed from: package-private */
    public final void setImageMatrixScale(float scale) {
        this.imageMatrixScale = scale;
        Matrix matrix = this.tmpMatrix;
        calculateImageMatrixFromScale(scale, matrix);
        this.view.setImageMatrix(matrix);
    }

    private void calculateImageMatrixFromScale(float scale, Matrix matrix) {
        matrix.reset();
        Drawable drawable = this.view.getDrawable();
        if (drawable != null && this.maxImageSize != 0) {
            RectF drawableBounds = this.tmpRectF1;
            RectF imageBounds = this.tmpRectF2;
            drawableBounds.set(0.0f, 0.0f, (float) drawable.getIntrinsicWidth(), (float) drawable.getIntrinsicHeight());
            imageBounds.set(0.0f, 0.0f, (float) this.maxImageSize, (float) this.maxImageSize);
            matrix.setRectToRect(drawableBounds, imageBounds, Matrix.ScaleToFit.CENTER);
            matrix.postScale(scale, scale, ((float) this.maxImageSize) / 2.0f, ((float) this.maxImageSize) / 2.0f);
        }
    }

    /* access modifiers changed from: package-private */
    public final void setShapeAppearance(ShapeAppearanceModel shapeAppearance2) {
        this.shapeAppearance = shapeAppearance2;
        if (this.shapeDrawable != null) {
            this.shapeDrawable.setShapeAppearanceModel(shapeAppearance2);
        }
        if (this.rippleDrawable instanceof Shapeable) {
            ((Shapeable) this.rippleDrawable).setShapeAppearanceModel(shapeAppearance2);
        }
        if (this.borderDrawable != null) {
            this.borderDrawable.setShapeAppearanceModel(shapeAppearance2);
        }
    }

    /* access modifiers changed from: package-private */
    public final ShapeAppearanceModel getShapeAppearance() {
        return this.shapeAppearance;
    }

    /* access modifiers changed from: package-private */
    public final MotionSpec getShowMotionSpec() {
        return this.showMotionSpec;
    }

    /* access modifiers changed from: package-private */
    public final void setShowMotionSpec(MotionSpec spec) {
        this.showMotionSpec = spec;
    }

    /* access modifiers changed from: package-private */
    public final MotionSpec getHideMotionSpec() {
        return this.hideMotionSpec;
    }

    /* access modifiers changed from: package-private */
    public final void setHideMotionSpec(MotionSpec spec) {
        this.hideMotionSpec = spec;
    }

    /* access modifiers changed from: package-private */
    public final boolean ignoreExpandBoundsForA11y() {
        return this.ensureMinTouchTargetSize && this.view.getSizeDimension() < this.minTouchTargetSize;
    }

    /* access modifiers changed from: package-private */
    public boolean getEnsureMinTouchTargetSize() {
        return this.ensureMinTouchTargetSize;
    }

    /* access modifiers changed from: package-private */
    public void setEnsureMinTouchTargetSize(boolean flag) {
        this.ensureMinTouchTargetSize = flag;
    }

    /* access modifiers changed from: package-private */
    public void setShadowPaddingEnabled(boolean shadowPaddingEnabled2) {
        this.shadowPaddingEnabled = shadowPaddingEnabled2;
        updatePadding();
    }

    /* access modifiers changed from: package-private */
    public void onElevationsChanged(float elevation2, float hoveredFocusedTranslationZ2, float pressedTranslationZ2) {
        if (this.view.getStateListAnimator() == this.stateListAnimator) {
            this.stateListAnimator = createDefaultStateListAnimator(elevation2, hoveredFocusedTranslationZ2, pressedTranslationZ2);
            this.view.setStateListAnimator(this.stateListAnimator);
        }
        if (shouldAddPadding()) {
            updatePadding();
        }
    }

    private StateListAnimator createDefaultStateListAnimator(float elevation2, float hoveredFocusedTranslationZ2, float pressedTranslationZ2) {
        StateListAnimator stateListAnimator2 = new StateListAnimator();
        stateListAnimator2.addState(PRESSED_ENABLED_STATE_SET, createElevationAnimator(elevation2, pressedTranslationZ2));
        stateListAnimator2.addState(HOVERED_FOCUSED_ENABLED_STATE_SET, createElevationAnimator(elevation2, hoveredFocusedTranslationZ2));
        stateListAnimator2.addState(FOCUSED_ENABLED_STATE_SET, createElevationAnimator(elevation2, hoveredFocusedTranslationZ2));
        stateListAnimator2.addState(HOVERED_ENABLED_STATE_SET, createElevationAnimator(elevation2, hoveredFocusedTranslationZ2));
        AnimatorSet set = new AnimatorSet();
        List<Animator> animators = new ArrayList<>();
        animators.add(ObjectAnimator.ofFloat(this.view, "elevation", new float[]{elevation2}).setDuration(0));
        if (Build.VERSION.SDK_INT <= 24) {
            animators.add(ObjectAnimator.ofFloat(this.view, View.TRANSLATION_Z, new float[]{this.view.getTranslationZ()}).setDuration(100));
        }
        animators.add(ObjectAnimator.ofFloat(this.view, View.TRANSLATION_Z, new float[]{0.0f}).setDuration(100));
        set.playSequentially((Animator[]) animators.toArray(new Animator[0]));
        set.setInterpolator(ELEVATION_ANIM_INTERPOLATOR);
        stateListAnimator2.addState(ENABLED_STATE_SET, set);
        stateListAnimator2.addState(EMPTY_STATE_SET, createElevationAnimator(0.0f, 0.0f));
        return stateListAnimator2;
    }

    /* access modifiers changed from: package-private */
    public void updateShapeElevation(float elevation2) {
        if (this.shapeDrawable != null) {
            this.shapeDrawable.setElevation(elevation2);
        }
    }

    /* access modifiers changed from: package-private */
    public void onDrawableStateChangedForLollipop() {
        if (this.view.isEnabled()) {
            this.view.setElevation(this.elevation);
            if (this.view.isPressed()) {
                this.view.setTranslationZ(this.pressedTranslationZ);
            } else if (this.view.isFocused() || this.view.isHovered()) {
                this.view.setTranslationZ(this.hoveredFocusedTranslationZ);
            } else {
                this.view.setTranslationZ(0.0f);
            }
        } else {
            this.view.setElevation(0.0f);
            this.view.setTranslationZ(0.0f);
        }
    }

    /* access modifiers changed from: package-private */
    public void addOnShowAnimationListener(Animator.AnimatorListener listener) {
        if (this.showListeners == null) {
            this.showListeners = new ArrayList<>();
        }
        this.showListeners.add(listener);
    }

    /* access modifiers changed from: package-private */
    public void removeOnShowAnimationListener(Animator.AnimatorListener listener) {
        if (this.showListeners != null) {
            this.showListeners.remove(listener);
        }
    }

    public void addOnHideAnimationListener(Animator.AnimatorListener listener) {
        if (this.hideListeners == null) {
            this.hideListeners = new ArrayList<>();
        }
        this.hideListeners.add(listener);
    }

    public void removeOnHideAnimationListener(Animator.AnimatorListener listener) {
        if (this.hideListeners != null) {
            this.hideListeners.remove(listener);
        }
    }

    /* access modifiers changed from: package-private */
    public void hide(final InternalVisibilityChangedListener listener, final boolean fromUser) {
        FloatingActionButtonImpl floatingActionButtonImpl;
        AnimatorSet set;
        if (!isOrWillBeHidden()) {
            if (this.currentAnimator != null) {
                this.currentAnimator.cancel();
            }
            if (shouldAnimateVisibilityChange()) {
                if (this.hideMotionSpec != null) {
                    set = createAnimator(this.hideMotionSpec, 0.0f, 0.0f, 0.0f);
                    floatingActionButtonImpl = this;
                } else {
                    floatingActionButtonImpl = this;
                    set = floatingActionButtonImpl.createDefaultAnimator(0.0f, 0.4f, 0.4f, HIDE_ANIM_DURATION_ATTR, HIDE_ANIM_EASING_ATTR);
                }
                set.addListener(new AnimatorListenerAdapter() {
                    private boolean cancelled;

                    public void onAnimationStart(Animator animation) {
                        FloatingActionButtonImpl.this.view.internalSetVisibility(0, fromUser);
                        int unused = FloatingActionButtonImpl.this.animState = 1;
                        Animator unused2 = FloatingActionButtonImpl.this.currentAnimator = animation;
                        this.cancelled = false;
                    }

                    public void onAnimationCancel(Animator animation) {
                        this.cancelled = true;
                    }

                    public void onAnimationEnd(Animator animation) {
                        int unused = FloatingActionButtonImpl.this.animState = 0;
                        Animator unused2 = FloatingActionButtonImpl.this.currentAnimator = null;
                        if (!this.cancelled) {
                            FloatingActionButtonImpl.this.view.internalSetVisibility(fromUser ? 8 : 4, fromUser);
                            if (listener != null) {
                                listener.onHidden();
                            }
                        }
                    }
                });
                if (floatingActionButtonImpl.hideListeners != null) {
                    Iterator<Animator.AnimatorListener> it = floatingActionButtonImpl.hideListeners.iterator();
                    while (it.hasNext()) {
                        set.addListener(it.next());
                    }
                }
                set.start();
                return;
            }
            this.view.internalSetVisibility(fromUser ? 8 : 4, fromUser);
            if (listener != null) {
                listener.onHidden();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void show(final InternalVisibilityChangedListener listener, final boolean fromUser) {
        FloatingActionButtonImpl floatingActionButtonImpl;
        AnimatorSet set;
        if (!isOrWillBeShown()) {
            if (this.currentAnimator != null) {
                this.currentAnimator.cancel();
            }
            boolean useDefaultAnimation = this.showMotionSpec == null;
            if (shouldAnimateVisibilityChange()) {
                if (this.view.getVisibility() != 0) {
                    float f = 0.0f;
                    this.view.setAlpha(0.0f);
                    this.view.setScaleY(useDefaultAnimation ? 0.4f : 0.0f);
                    this.view.setScaleX(useDefaultAnimation ? 0.4f : 0.0f);
                    if (useDefaultAnimation) {
                        f = 0.4f;
                    }
                    setImageMatrixScale(f);
                }
                if (this.showMotionSpec != null) {
                    set = createAnimator(this.showMotionSpec, 1.0f, 1.0f, 1.0f);
                    floatingActionButtonImpl = this;
                } else {
                    floatingActionButtonImpl = this;
                    set = floatingActionButtonImpl.createDefaultAnimator(1.0f, 1.0f, 1.0f, SHOW_ANIM_DURATION_ATTR, SHOW_ANIM_EASING_ATTR);
                }
                set.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationStart(Animator animation) {
                        FloatingActionButtonImpl.this.view.internalSetVisibility(0, fromUser);
                        int unused = FloatingActionButtonImpl.this.animState = 2;
                        Animator unused2 = FloatingActionButtonImpl.this.currentAnimator = animation;
                    }

                    public void onAnimationEnd(Animator animation) {
                        int unused = FloatingActionButtonImpl.this.animState = 0;
                        Animator unused2 = FloatingActionButtonImpl.this.currentAnimator = null;
                        if (listener != null) {
                            listener.onShown();
                        }
                    }
                });
                if (floatingActionButtonImpl.showListeners != null) {
                    Iterator<Animator.AnimatorListener> it = floatingActionButtonImpl.showListeners.iterator();
                    while (it.hasNext()) {
                        set.addListener(it.next());
                    }
                }
                set.start();
                return;
            }
            this.view.internalSetVisibility(0, fromUser);
            this.view.setAlpha(1.0f);
            this.view.setScaleY(1.0f);
            this.view.setScaleX(1.0f);
            setImageMatrixScale(1.0f);
            if (listener != null) {
                listener.onShown();
            }
        }
    }

    private AnimatorSet createAnimator(MotionSpec spec, float opacity, float scale, float iconScale) {
        List<Animator> animators = new ArrayList<>();
        ObjectAnimator animatorOpacity = ObjectAnimator.ofFloat(this.view, View.ALPHA, new float[]{opacity});
        spec.getTiming("opacity").apply(animatorOpacity);
        animators.add(animatorOpacity);
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(this.view, View.SCALE_X, new float[]{scale});
        spec.getTiming("scale").apply(animatorScaleX);
        workAroundOreoBug(animatorScaleX);
        animators.add(animatorScaleX);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(this.view, View.SCALE_Y, new float[]{scale});
        spec.getTiming("scale").apply(animatorScaleY);
        workAroundOreoBug(animatorScaleY);
        animators.add(animatorScaleY);
        calculateImageMatrixFromScale(iconScale, this.tmpMatrix);
        ObjectAnimator animatorIconScale = ObjectAnimator.ofObject(this.view, new ImageMatrixProperty(), new MatrixEvaluator() {
            public Matrix evaluate(float fraction, Matrix startValue, Matrix endValue) {
                float unused = FloatingActionButtonImpl.this.imageMatrixScale = fraction;
                return super.evaluate(fraction, startValue, endValue);
            }
        }, new Matrix[]{new Matrix(this.tmpMatrix)});
        spec.getTiming("iconScale").apply(animatorIconScale);
        animators.add(animatorIconScale);
        AnimatorSet set = new AnimatorSet();
        AnimatorSetCompat.playTogether(set, animators);
        return set;
    }

    private AnimatorSet createDefaultAnimator(float targetOpacity, float targetScale, float targetIconScale, int duration, int interpolator) {
        AnimatorSet set = new AnimatorSet();
        List<Animator> animators = new ArrayList<>();
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.addUpdateListener(new FloatingActionButtonImpl$$ExternalSyntheticLambda0(this, this.view.getAlpha(), targetOpacity, this.view.getScaleX(), targetScale, this.view.getScaleY(), this.imageMatrixScale, targetIconScale, new Matrix(this.tmpMatrix)));
        animators.add(animator);
        AnimatorSetCompat.playTogether(set, animators);
        set.setDuration((long) MotionUtils.resolveThemeDuration(this.view.getContext(), duration, this.view.getContext().getResources().getInteger(R.integer.material_motion_duration_long_1)));
        set.setInterpolator(MotionUtils.resolveThemeInterpolator(this.view.getContext(), interpolator, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        return set;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$createDefaultAnimator$0$com-google-android-material-floatingactionbutton-FloatingActionButtonImpl  reason: not valid java name */
    public /* synthetic */ void m1692lambda$createDefaultAnimator$0$comgoogleandroidmaterialfloatingactionbuttonFloatingActionButtonImpl(float startAlpha, float targetOpacity, float startScaleX, float targetScale, float startScaleY, float startImageMatrixScale, float targetIconScale, Matrix matrix, ValueAnimator animation) {
        float progress = ((Float) animation.getAnimatedValue()).floatValue();
        this.view.setAlpha(AnimationUtils.lerp(startAlpha, targetOpacity, 0.0f, 0.2f, progress));
        this.view.setScaleX(AnimationUtils.lerp(startScaleX, targetScale, progress));
        this.view.setScaleY(AnimationUtils.lerp(startScaleY, targetScale, progress));
        this.imageMatrixScale = AnimationUtils.lerp(startImageMatrixScale, targetIconScale, progress);
        calculateImageMatrixFromScale(AnimationUtils.lerp(startImageMatrixScale, targetIconScale, progress), matrix);
        this.view.setImageMatrix(matrix);
    }

    private void workAroundOreoBug(ObjectAnimator animator) {
        if (Build.VERSION.SDK_INT == 26) {
            animator.setEvaluator(new TypeEvaluator<Float>() {
                final FloatEvaluator floatEvaluator = new FloatEvaluator();

                public Float evaluate(float fraction, Float startValue, Float endValue) {
                    float evaluated = this.floatEvaluator.evaluate(fraction, startValue, endValue).floatValue();
                    return Float.valueOf(evaluated < 0.1f ? 0.0f : evaluated);
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public void addTransformationCallback(InternalTransformationCallback listener) {
        if (this.transformationCallbacks == null) {
            this.transformationCallbacks = new ArrayList<>();
        }
        this.transformationCallbacks.add(listener);
    }

    /* access modifiers changed from: package-private */
    public void removeTransformationCallback(InternalTransformationCallback listener) {
        if (this.transformationCallbacks != null) {
            this.transformationCallbacks.remove(listener);
        }
    }

    /* access modifiers changed from: package-private */
    public void onTranslationChanged() {
        if (this.transformationCallbacks != null) {
            Iterator<InternalTransformationCallback> it = this.transformationCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onTranslationChanged();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onScaleChanged() {
        if (this.transformationCallbacks != null) {
            Iterator<InternalTransformationCallback> it = this.transformationCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onScaleChanged();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final Drawable getContentBackground() {
        return this.contentBackground;
    }

    /* access modifiers changed from: package-private */
    public void onCompatShadowChanged() {
        updatePadding();
    }

    /* access modifiers changed from: package-private */
    public final void updatePadding() {
        Rect rect = this.tmpRect;
        getPadding(rect);
        onPaddingUpdated(rect);
        this.shadowViewDelegate.setShadowPadding(rect.left, rect.top, rect.right, rect.bottom);
    }

    /* access modifiers changed from: package-private */
    public void getPadding(Rect rect) {
        if (this.shadowViewDelegate.isCompatPaddingEnabled()) {
            int touchTargetPadding = getTouchTargetPadding();
            float maxShadowSize = this.shadowPaddingEnabled ? getElevation() + this.pressedTranslationZ : 0.0f;
            int hPadding = Math.max(touchTargetPadding, (int) Math.ceil((double) maxShadowSize));
            int vPadding = Math.max(touchTargetPadding, (int) Math.ceil((double) (SHADOW_MULTIPLIER * maxShadowSize)));
            rect.set(hPadding, vPadding, hPadding, vPadding);
        } else if (ignoreExpandBoundsForA11y()) {
            int minPadding = (this.minTouchTargetSize - this.view.getSizeDimension()) / 2;
            rect.set(minPadding, minPadding, minPadding, minPadding);
        } else {
            rect.set(0, 0, 0, 0);
        }
    }

    /* access modifiers changed from: package-private */
    public int getTouchTargetPadding() {
        if (this.ensureMinTouchTargetSize) {
            return Math.max((this.minTouchTargetSize - this.view.getSizeDimension()) / 2, 0);
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public void onPaddingUpdated(Rect padding) {
        Preconditions.checkNotNull(this.contentBackground, "Didn't initialize content background");
        if (shouldAddPadding()) {
            this.shadowViewDelegate.setBackgroundDrawable(new InsetDrawable(this.contentBackground, padding.left, padding.top, padding.right, padding.bottom));
            return;
        }
        this.shadowViewDelegate.setBackgroundDrawable(this.contentBackground);
    }

    /* access modifiers changed from: package-private */
    public boolean shouldAddPadding() {
        return this.shadowViewDelegate.isCompatPaddingEnabled() || ignoreExpandBoundsForA11y();
    }

    /* access modifiers changed from: package-private */
    public void onAttachedToWindow() {
        if (this.shapeDrawable != null) {
            MaterialShapeUtils.setParentAbsoluteElevation(this.view, this.shapeDrawable);
        }
    }

    /* access modifiers changed from: package-private */
    public void onDetachedFromWindow() {
        ViewTreeObserver viewTreeObserver = this.view.getViewTreeObserver();
        if (this.preDrawListener != null) {
            viewTreeObserver.removeOnPreDrawListener(this.preDrawListener);
            this.preDrawListener = null;
        }
    }

    /* access modifiers changed from: package-private */
    public BorderDrawable createBorderDrawable(int borderWidth, ColorStateList backgroundTint) {
        Context context = this.view.getContext();
        BorderDrawable borderDrawable2 = new BorderDrawable((ShapeAppearanceModel) Preconditions.checkNotNull(this.shapeAppearance));
        borderDrawable2.setGradientColors(ContextCompat.getColor(context, R.color.design_fab_stroke_top_outer_color), ContextCompat.getColor(context, R.color.design_fab_stroke_top_inner_color), ContextCompat.getColor(context, R.color.design_fab_stroke_end_inner_color), ContextCompat.getColor(context, R.color.design_fab_stroke_end_outer_color));
        borderDrawable2.setBorderWidth((float) borderWidth);
        borderDrawable2.setBorderTint(backgroundTint);
        return borderDrawable2;
    }

    /* access modifiers changed from: package-private */
    public MaterialShapeDrawable createShapeDrawable() {
        return new AlwaysStatefulMaterialShapeDrawable((ShapeAppearanceModel) Preconditions.checkNotNull(this.shapeAppearance));
    }

    /* access modifiers changed from: package-private */
    public boolean isOrWillBeShown() {
        if (this.view.getVisibility() != 0) {
            if (this.animState == 2) {
                return true;
            }
            return false;
        } else if (this.animState != 1) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isOrWillBeHidden() {
        if (this.view.getVisibility() == 0) {
            if (this.animState == 1) {
                return true;
            }
            return false;
        } else if (this.animState != 2) {
            return true;
        } else {
            return false;
        }
    }

    private Animator createElevationAnimator(float elevation2, float translationZ) {
        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(this.view, "elevation", new float[]{elevation2}).setDuration(0)).with(ObjectAnimator.ofFloat(this.view, View.TRANSLATION_Z, new float[]{translationZ}).setDuration(100));
        set.setInterpolator(ELEVATION_ANIM_INTERPOLATOR);
        return set;
    }

    private boolean shouldAnimateVisibilityChange() {
        return this.view.isLaidOut() && !this.view.isInEditMode();
    }

    static class AlwaysStatefulMaterialShapeDrawable extends MaterialShapeDrawable {
        AlwaysStatefulMaterialShapeDrawable(ShapeAppearanceModel shapeAppearanceModel) {
            super(shapeAppearanceModel);
        }

        public boolean isStateful() {
            return true;
        }
    }
}
