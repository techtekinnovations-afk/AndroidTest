package com.google.android.material.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityManager;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.motion.MotionUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.LinkedHashSet;

@Deprecated
public class HideBottomViewOnScrollBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    private static final int DEFAULT_ENTER_ANIMATION_DURATION_MS = 225;
    private static final int DEFAULT_EXIT_ANIMATION_DURATION_MS = 175;
    private static final int ENTER_ANIM_DURATION_ATTR = R.attr.motionDurationLong2;
    private static final int ENTER_EXIT_ANIM_EASING_ATTR = R.attr.motionEasingEmphasizedInterpolator;
    private static final int EXIT_ANIM_DURATION_ATTR = R.attr.motionDurationMedium4;
    public static final int STATE_SCROLLED_DOWN = 1;
    public static final int STATE_SCROLLED_UP = 2;
    /* access modifiers changed from: private */
    public AccessibilityManager accessibilityManager;
    private int additionalHiddenOffsetY = 0;
    /* access modifiers changed from: private */
    public ViewPropertyAnimator currentAnimator;
    private int currentState = 2;
    private boolean disableOnTouchExploration = true;
    private int enterAnimDuration;
    private TimeInterpolator enterAnimInterpolator;
    private int exitAnimDuration;
    private TimeInterpolator exitAnimInterpolator;
    private int height = 0;
    private final LinkedHashSet<OnScrollStateChangedListener> onScrollStateChangedListeners = new LinkedHashSet<>();
    /* access modifiers changed from: private */
    public AccessibilityManager.TouchExplorationStateChangeListener touchExplorationListener;

    public interface OnScrollStateChangedListener {
        void onStateChanged(View view, int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScrollState {
    }

    public HideBottomViewOnScrollBehavior() {
    }

    public HideBottomViewOnScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        this.height = child.getMeasuredHeight() + ((ViewGroup.MarginLayoutParams) child.getLayoutParams()).bottomMargin;
        this.enterAnimDuration = MotionUtils.resolveThemeDuration(child.getContext(), ENTER_ANIM_DURATION_ATTR, DEFAULT_ENTER_ANIMATION_DURATION_MS);
        this.exitAnimDuration = MotionUtils.resolveThemeDuration(child.getContext(), EXIT_ANIM_DURATION_ATTR, DEFAULT_EXIT_ANIMATION_DURATION_MS);
        this.enterAnimInterpolator = MotionUtils.resolveThemeInterpolator(child.getContext(), ENTER_EXIT_ANIM_EASING_ATTR, AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        this.exitAnimInterpolator = MotionUtils.resolveThemeInterpolator(child.getContext(), ENTER_EXIT_ANIM_EASING_ATTR, AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
        disableIfTouchExplorationEnabled(child);
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    private void disableIfTouchExplorationEnabled(V child) {
        if (this.accessibilityManager == null) {
            this.accessibilityManager = (AccessibilityManager) ContextCompat.getSystemService(child.getContext(), AccessibilityManager.class);
        }
        if (this.accessibilityManager != null && this.touchExplorationListener == null) {
            this.touchExplorationListener = new HideBottomViewOnScrollBehavior$$ExternalSyntheticLambda0(this, child);
            this.accessibilityManager.addTouchExplorationStateChangeListener(this.touchExplorationListener);
            child.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                public void onViewAttachedToWindow(View v) {
                }

                public void onViewDetachedFromWindow(View v) {
                    if (HideBottomViewOnScrollBehavior.this.touchExplorationListener != null && HideBottomViewOnScrollBehavior.this.accessibilityManager != null) {
                        HideBottomViewOnScrollBehavior.this.accessibilityManager.removeTouchExplorationStateChangeListener(HideBottomViewOnScrollBehavior.this.touchExplorationListener);
                        AccessibilityManager.TouchExplorationStateChangeListener unused = HideBottomViewOnScrollBehavior.this.touchExplorationListener = null;
                    }
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$disableIfTouchExplorationEnabled$0$com-google-android-material-behavior-HideBottomViewOnScrollBehavior  reason: not valid java name */
    public /* synthetic */ void m1633lambda$disableIfTouchExplorationEnabled$0$comgoogleandroidmaterialbehaviorHideBottomViewOnScrollBehavior(View child, boolean enabled) {
        if (enabled && isScrolledDown()) {
            slideUp(child);
        }
    }

    public void setAdditionalHiddenOffsetY(V child, int offset) {
        this.additionalHiddenOffsetY = offset;
        if (this.currentState == 1) {
            child.setTranslationY((float) (this.height + this.additionalHiddenOffsetY));
        }
    }

    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v, View directTargetChild, View target, int nestedScrollAxes, int type) {
        return nestedScrollAxes == 2;
    }

    public void onNestedScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, int[] consumed) {
        if (dyConsumed > 0) {
            slideDown(child);
        } else if (dyConsumed < 0) {
            slideUp(child);
        }
    }

    public boolean isScrolledUp() {
        return this.currentState == 2;
    }

    public void slideUp(V child) {
        slideUp(child, true);
    }

    public void slideUp(V child, boolean animate) {
        if (!isScrolledUp()) {
            if (this.currentAnimator != null) {
                this.currentAnimator.cancel();
                child.clearAnimation();
            }
            updateCurrentState(child, 2);
            if (animate) {
                animateChildTo(child, 0, (long) this.enterAnimDuration, this.enterAnimInterpolator);
                return;
            }
            child.setTranslationY((float) 0);
        }
    }

    public boolean isScrolledDown() {
        return this.currentState == 1;
    }

    public void slideDown(V child) {
        slideDown(child, true);
    }

    public void slideDown(V child, boolean animate) {
        if (!isScrolledDown()) {
            if (!this.disableOnTouchExploration || this.accessibilityManager == null || !this.accessibilityManager.isTouchExplorationEnabled()) {
                if (this.currentAnimator != null) {
                    this.currentAnimator.cancel();
                    child.clearAnimation();
                }
                updateCurrentState(child, 1);
                int targetTranslationY = this.height + this.additionalHiddenOffsetY;
                if (animate) {
                    animateChildTo(child, targetTranslationY, (long) this.exitAnimDuration, this.exitAnimInterpolator);
                    return;
                }
                child.setTranslationY((float) targetTranslationY);
            }
        }
    }

    private void updateCurrentState(V child, int state) {
        this.currentState = state;
        Iterator it = this.onScrollStateChangedListeners.iterator();
        while (it.hasNext()) {
            ((OnScrollStateChangedListener) it.next()).onStateChanged(child, this.currentState);
        }
    }

    private void animateChildTo(V child, int targetY, long duration, TimeInterpolator interpolator) {
        this.currentAnimator = child.animate().translationY((float) targetY).setInterpolator(interpolator).setDuration(duration).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                ViewPropertyAnimator unused = HideBottomViewOnScrollBehavior.this.currentAnimator = null;
            }
        });
    }

    public void addOnScrollStateChangedListener(OnScrollStateChangedListener listener) {
        this.onScrollStateChangedListeners.add(listener);
    }

    public void removeOnScrollStateChangedListener(OnScrollStateChangedListener listener) {
        this.onScrollStateChangedListeners.remove(listener);
    }

    public void clearOnScrollStateChangedListeners() {
        this.onScrollStateChangedListeners.clear();
    }

    public void disableOnTouchExploration(boolean disableOnTouchExploration2) {
        this.disableOnTouchExploration = disableOnTouchExploration2;
    }

    public boolean isDisabledOnTouchExploration() {
        return this.disableOnTouchExploration;
    }
}
