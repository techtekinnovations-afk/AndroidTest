package com.google.android.material.search;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.BackEventCompat;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ClippableRoundedCornerLayout;
import com.google.android.material.internal.FadeThroughDrawable;
import com.google.android.material.internal.FadeThroughUpdateListener;
import com.google.android.material.internal.MultiViewUpdateListener;
import com.google.android.material.internal.RectEvaluator;
import com.google.android.material.internal.ReversableAnimatedValueInterpolator;
import com.google.android.material.internal.ToolbarUtils;
import com.google.android.material.internal.TouchObserverFrameLayout;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.motion.MaterialMainContainerBackHelper;
import com.google.android.material.search.SearchView;
import java.util.Objects;

class SearchViewAnimationHelper {
    private static final float CONTENT_FROM_SCALE = 0.95f;
    private static final long HIDE_CLEAR_BUTTON_ALPHA_DURATION_MS = 42;
    private static final long HIDE_CLEAR_BUTTON_ALPHA_START_DELAY_MS = 0;
    private static final long HIDE_CONTENT_ALPHA_DURATION_MS = 83;
    private static final long HIDE_CONTENT_ALPHA_START_DELAY_MS = 0;
    private static final long HIDE_CONTENT_SCALE_DURATION_MS = 250;
    private static final long HIDE_DURATION_MS = 250;
    private static final long HIDE_TRANSLATE_DURATION_MS = 300;
    private static final long SHOW_CLEAR_BUTTON_ALPHA_DURATION_MS = 50;
    private static final long SHOW_CLEAR_BUTTON_ALPHA_START_DELAY_MS = 250;
    private static final long SHOW_CONTENT_ALPHA_DURATION_MS = 150;
    private static final long SHOW_CONTENT_ALPHA_START_DELAY_MS = 75;
    private static final long SHOW_CONTENT_SCALE_DURATION_MS = 300;
    private static final long SHOW_DURATION_MS = 300;
    private static final long SHOW_SCRIM_ALPHA_DURATION_MS = 100;
    private static final long SHOW_TRANSLATE_DURATION_MS = 350;
    private static final long SHOW_TRANSLATE_KEYBOARD_START_DELAY_MS = 150;
    /* access modifiers changed from: private */
    public final MaterialMainContainerBackHelper backHelper = new MaterialMainContainerBackHelper(this.rootView);
    private AnimatorSet backProgressAnimatorSet;
    private final ImageButton clearButton;
    private final TouchObserverFrameLayout contentContainer;
    private final View divider;
    private final Toolbar dummyToolbar;
    /* access modifiers changed from: private */
    public final EditText editText;
    private final FrameLayout headerContainer;
    /* access modifiers changed from: private */
    public final ClippableRoundedCornerLayout rootView;
    private final View scrim;
    /* access modifiers changed from: private */
    public SearchBar searchBar;
    private final TextView searchPrefix;
    /* access modifiers changed from: private */
    public final SearchView searchView;
    private final LinearLayout textContainer;
    private final Toolbar toolbar;
    private final FrameLayout toolbarContainer;

    SearchViewAnimationHelper(SearchView searchView2) {
        this.searchView = searchView2;
        this.scrim = searchView2.scrim;
        this.rootView = searchView2.rootView;
        this.headerContainer = searchView2.headerContainer;
        this.toolbarContainer = searchView2.toolbarContainer;
        this.toolbar = searchView2.toolbar;
        this.dummyToolbar = searchView2.dummyToolbar;
        this.searchPrefix = searchView2.searchPrefix;
        this.editText = searchView2.editText;
        this.clearButton = searchView2.clearButton;
        this.divider = searchView2.divider;
        this.contentContainer = searchView2.contentContainer;
        this.textContainer = searchView2.textContainer;
    }

    /* access modifiers changed from: package-private */
    public void setSearchBar(SearchBar searchBar2) {
        this.searchBar = searchBar2;
    }

    /* access modifiers changed from: package-private */
    public void show() {
        if (this.searchBar != null) {
            startShowAnimationExpand();
        } else {
            startShowAnimationTranslate();
        }
    }

    /* access modifiers changed from: package-private */
    public AnimatorSet hide() {
        if (this.searchBar != null) {
            return startHideAnimationCollapse();
        }
        return startHideAnimationTranslate();
    }

    private void startShowAnimationExpand() {
        if (this.searchView.isAdjustNothingSoftInputMode()) {
            this.searchView.requestFocusAndShowKeyboardIfNeeded();
        }
        this.searchView.setTransitionState(SearchView.TransitionState.SHOWING);
        setUpDummyToolbarIfNeeded();
        this.editText.setText(this.searchBar.getText());
        this.editText.setSelection(this.editText.getText().length());
        this.rootView.setVisibility(4);
        this.rootView.post(new SearchViewAnimationHelper$$ExternalSyntheticLambda3(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$startShowAnimationExpand$0$com-google-android-material-search-SearchViewAnimationHelper  reason: not valid java name */
    public /* synthetic */ void m1711lambda$startShowAnimationExpand$0$comgoogleandroidmaterialsearchSearchViewAnimationHelper() {
        AnimatorSet animatorSet = getExpandCollapseAnimatorSet(true);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                SearchViewAnimationHelper.this.rootView.setVisibility(0);
                SearchViewAnimationHelper.this.searchBar.stopOnLoadAnimation();
            }

            public void onAnimationEnd(Animator animation) {
                if (!SearchViewAnimationHelper.this.searchView.isAdjustNothingSoftInputMode()) {
                    SearchViewAnimationHelper.this.searchView.requestFocusAndShowKeyboardIfNeeded();
                }
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.SHOWN);
            }
        });
        animatorSet.start();
    }

    private AnimatorSet startHideAnimationCollapse() {
        if (this.searchView.isAdjustNothingSoftInputMode()) {
            this.searchView.clearFocusAndHideKeyboard();
        }
        AnimatorSet animatorSet = getExpandCollapseAnimatorSet(false);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.HIDING);
            }

            public void onAnimationEnd(Animator animation) {
                SearchViewAnimationHelper.this.rootView.setVisibility(8);
                if (!SearchViewAnimationHelper.this.searchView.isAdjustNothingSoftInputMode()) {
                    SearchViewAnimationHelper.this.searchView.clearFocusAndHideKeyboard();
                }
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.HIDDEN);
            }
        });
        animatorSet.start();
        return animatorSet;
    }

    private void startShowAnimationTranslate() {
        if (this.searchView.isAdjustNothingSoftInputMode()) {
            SearchView searchView2 = this.searchView;
            SearchView searchView3 = this.searchView;
            Objects.requireNonNull(searchView3);
            searchView2.postDelayed(new SearchViewAnimationHelper$$ExternalSyntheticLambda6(searchView3), 150);
        }
        this.rootView.setVisibility(4);
        this.rootView.post(new SearchViewAnimationHelper$$ExternalSyntheticLambda7(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$startShowAnimationTranslate$1$com-google-android-material-search-SearchViewAnimationHelper  reason: not valid java name */
    public /* synthetic */ void m1712lambda$startShowAnimationTranslate$1$comgoogleandroidmaterialsearchSearchViewAnimationHelper() {
        this.rootView.setTranslationY((float) this.rootView.getHeight());
        AnimatorSet animatorSet = getTranslateAnimatorSet(true);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                SearchViewAnimationHelper.this.rootView.setVisibility(0);
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.SHOWING);
            }

            public void onAnimationEnd(Animator animation) {
                if (!SearchViewAnimationHelper.this.searchView.isAdjustNothingSoftInputMode()) {
                    SearchViewAnimationHelper.this.searchView.requestFocusAndShowKeyboardIfNeeded();
                }
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.SHOWN);
            }
        });
        animatorSet.start();
    }

    private AnimatorSet startHideAnimationTranslate() {
        if (this.searchView.isAdjustNothingSoftInputMode()) {
            this.searchView.clearFocusAndHideKeyboard();
        }
        AnimatorSet animatorSet = getTranslateAnimatorSet(false);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.HIDING);
            }

            public void onAnimationEnd(Animator animation) {
                SearchViewAnimationHelper.this.rootView.setVisibility(8);
                if (!SearchViewAnimationHelper.this.searchView.isAdjustNothingSoftInputMode()) {
                    SearchViewAnimationHelper.this.searchView.clearFocusAndHideKeyboard();
                }
                SearchViewAnimationHelper.this.searchView.setTransitionState(SearchView.TransitionState.HIDDEN);
            }
        });
        animatorSet.start();
        return animatorSet;
    }

    private AnimatorSet getTranslateAnimatorSet(boolean show) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{getTranslationYAnimator()});
        addBackButtonProgressAnimatorIfNeeded(animatorSet);
        animatorSet.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        animatorSet.setDuration(show ? SHOW_TRANSLATE_DURATION_MS : 300);
        return animatorSet;
    }

    private Animator getTranslationYAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{(float) this.rootView.getHeight(), 0.0f});
        animator.addUpdateListener(MultiViewUpdateListener.translationYListener(this.rootView));
        return animator;
    }

    private AnimatorSet getExpandCollapseAnimatorSet(final boolean show) {
        AnimatorSet animatorSet = new AnimatorSet();
        if (!(this.backProgressAnimatorSet != null)) {
            animatorSet.playTogether(new Animator[]{getButtonsProgressAnimator(show), getButtonsTranslationAnimator(show)});
        }
        animatorSet.playTogether(new Animator[]{getScrimAlphaAnimator(show), getRootViewAnimator(show), getClearButtonAnimator(show), getContentAnimator(show), getHeaderContainerAnimator(show), getDummyToolbarAnimator(show), getActionMenuViewsAlphaAnimator(show), getEditTextAnimator(show), getSearchPrefixAnimator(show), getTextAnimator(show)});
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animation) {
                SearchViewAnimationHelper.this.setContentViewsAlpha(show ? 0.0f : 1.0f);
            }

            public void onAnimationEnd(Animator animation) {
                SearchViewAnimationHelper.this.setContentViewsAlpha(show ? 1.0f : 0.0f);
                SearchViewAnimationHelper.this.editText.setAlpha(1.0f);
                if (SearchViewAnimationHelper.this.searchBar != null) {
                    SearchViewAnimationHelper.this.searchBar.getTextView().setAlpha(1.0f);
                }
                SearchViewAnimationHelper.this.editText.setClipBounds((Rect) null);
                SearchViewAnimationHelper.this.rootView.resetClipBoundsAndCornerRadii();
                if (!show) {
                    SearchViewAnimationHelper.this.backHelper.clearExpandedCornerRadii();
                }
            }
        });
        return animatorSet;
    }

    /* access modifiers changed from: private */
    public void setContentViewsAlpha(float alpha) {
        this.clearButton.setAlpha(alpha);
        this.divider.setAlpha(alpha);
        this.contentContainer.setAlpha(alpha);
        setActionMenuViewAlphaIfNeeded(alpha);
    }

    private void setActionMenuViewAlphaIfNeeded(float alpha) {
        ActionMenuView actionMenuView;
        if (this.searchView.isMenuItemsAnimated() && (actionMenuView = ToolbarUtils.getActionMenuView(this.toolbar)) != null) {
            actionMenuView.setAlpha(alpha);
        }
    }

    private Animator getScrimAlphaAnimator(boolean show) {
        TimeInterpolator interpolator = show ? AnimationUtils.LINEAR_INTERPOLATOR : AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.setDuration(show ? 300 : 250);
        animator.setStartDelay(show ? SHOW_SCRIM_ALPHA_DURATION_MS : 0);
        animator.setInterpolator(ReversableAnimatedValueInterpolator.of(show, interpolator));
        animator.addUpdateListener(MultiViewUpdateListener.alphaListener(this.scrim));
        return animator;
    }

    private Animator getRootViewAnimator(boolean show) {
        Rect toClipBounds;
        Rect fromClipBounds;
        Rect initialHideToClipBounds = this.backHelper.getInitialHideToClipBounds();
        Rect initialHideFromClipBounds = this.backHelper.getInitialHideFromClipBounds();
        if (initialHideToClipBounds != null) {
            toClipBounds = initialHideToClipBounds;
        } else {
            toClipBounds = ViewUtils.calculateRectFromBounds(this.searchView);
        }
        if (initialHideFromClipBounds != null) {
            fromClipBounds = initialHideFromClipBounds;
        } else {
            fromClipBounds = ViewUtils.calculateOffsetRectFromBounds(this.rootView, this.searchBar);
        }
        Rect clipBounds = new Rect(fromClipBounds);
        float fromCornerRadius = this.searchBar.getCornerSize();
        float[] toCornerRadius = maxCornerRadii(this.rootView.getCornerRadii(), this.backHelper.getExpandedCornerRadii());
        ValueAnimator animator = ValueAnimator.ofObject(new RectEvaluator(clipBounds), new Object[]{fromClipBounds, toClipBounds});
        animator.addUpdateListener(new SearchViewAnimationHelper$$ExternalSyntheticLambda1(this, fromCornerRadius, toCornerRadius, clipBounds));
        animator.setDuration(show ? 300 : 250);
        animator.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        return animator;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$getRootViewAnimator$2$com-google-android-material-search-SearchViewAnimationHelper  reason: not valid java name */
    public /* synthetic */ void m1710lambda$getRootViewAnimator$2$comgoogleandroidmaterialsearchSearchViewAnimationHelper(float fromCornerRadius, float[] toCornerRadius, Rect clipBounds, ValueAnimator valueAnimator) {
        this.rootView.updateClipBoundsAndCornerRadii(clipBounds, lerpCornerRadii(fromCornerRadius, toCornerRadius, valueAnimator.getAnimatedFraction()));
    }

    private static float[] maxCornerRadii(float[] startValue, float[] endValue) {
        return new float[]{Math.max(startValue[0], endValue[0]), Math.max(startValue[1], endValue[1]), Math.max(startValue[2], endValue[2]), Math.max(startValue[3], endValue[3]), Math.max(startValue[4], endValue[4]), Math.max(startValue[5], endValue[5]), Math.max(startValue[6], endValue[6]), Math.max(startValue[7], endValue[7])};
    }

    private static float[] lerpCornerRadii(float startValue, float[] endValue, float fraction) {
        float f = startValue;
        float f2 = fraction;
        return new float[]{AnimationUtils.lerp(f, endValue[0], f2), AnimationUtils.lerp(f, endValue[1], f2), AnimationUtils.lerp(f, endValue[2], f2), AnimationUtils.lerp(f, endValue[3], f2), AnimationUtils.lerp(f, endValue[4], f2), AnimationUtils.lerp(f, endValue[5], f2), AnimationUtils.lerp(f, endValue[6], f2), AnimationUtils.lerp(f, endValue[7], f2)};
    }

    private Animator getClearButtonAnimator(boolean show) {
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.setDuration(show ? SHOW_CLEAR_BUTTON_ALPHA_DURATION_MS : HIDE_CLEAR_BUTTON_ALPHA_DURATION_MS);
        animator.setStartDelay(show ? 250 : 0);
        animator.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.LINEAR_INTERPOLATOR));
        animator.addUpdateListener(MultiViewUpdateListener.alphaListener(this.clearButton));
        return animator;
    }

    private AnimatorSet getButtonsProgressAnimator(boolean show) {
        AnimatorSet animatorSet = new AnimatorSet();
        addBackButtonProgressAnimatorIfNeeded(animatorSet);
        animatorSet.setDuration(show ? 300 : 250);
        animatorSet.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        return animatorSet;
    }

    private AnimatorSet getButtonsTranslationAnimator(boolean show) {
        AnimatorSet animatorSet = new AnimatorSet();
        addBackButtonTranslationAnimatorIfNeeded(animatorSet);
        addActionMenuViewAnimatorIfNeeded(animatorSet);
        animatorSet.setDuration(show ? 300 : 250);
        animatorSet.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        return animatorSet;
    }

    private void addBackButtonTranslationAnimatorIfNeeded(AnimatorSet animatorSet) {
        ImageButton searchViewBackButton = ToolbarUtils.getNavigationIconButton(this.toolbar);
        if (searchViewBackButton != null) {
            ValueAnimator backButtonAnimatorX = ValueAnimator.ofFloat(new float[]{(float) getTranslationXBetweenViews(ToolbarUtils.getNavigationIconButton(this.searchBar), searchViewBackButton), 0.0f});
            backButtonAnimatorX.addUpdateListener(MultiViewUpdateListener.translationXListener(searchViewBackButton));
            ValueAnimator backButtonAnimatorY = ValueAnimator.ofFloat(new float[]{(float) getFromTranslationY(), 0.0f});
            backButtonAnimatorY.addUpdateListener(MultiViewUpdateListener.translationYListener(searchViewBackButton));
            animatorSet.playTogether(new Animator[]{backButtonAnimatorX, backButtonAnimatorY});
        }
    }

    private void addBackButtonProgressAnimatorIfNeeded(AnimatorSet animatorSet) {
        ImageButton backButton = ToolbarUtils.getNavigationIconButton(this.toolbar);
        if (backButton != null) {
            Drawable drawable = DrawableCompat.unwrap(backButton.getDrawable());
            if (this.searchView.isAnimatedNavigationIcon()) {
                addDrawerArrowDrawableAnimatorIfNeeded(animatorSet, drawable);
                addFadeThroughDrawableAnimatorIfNeeded(animatorSet, drawable);
                addBackButtonAnimatorIfNeeded(animatorSet, backButton);
                return;
            }
            setFullDrawableProgressIfNeeded(drawable);
        }
    }

    private void addBackButtonAnimatorIfNeeded(AnimatorSet animatorSet, ImageButton backButton) {
        if (this.searchBar != null && this.searchBar.getNavigationIcon() == null) {
            ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            animator.addUpdateListener(new SearchViewAnimationHelper$$ExternalSyntheticLambda8(backButton));
            animatorSet.playTogether(new Animator[]{animator});
        }
    }

    private void addDrawerArrowDrawableAnimatorIfNeeded(AnimatorSet animatorSet, Drawable drawable) {
        if (drawable instanceof DrawerArrowDrawable) {
            ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            animator.addUpdateListener(new SearchViewAnimationHelper$$ExternalSyntheticLambda2((DrawerArrowDrawable) drawable));
            animatorSet.playTogether(new Animator[]{animator});
        }
    }

    private void addFadeThroughDrawableAnimatorIfNeeded(AnimatorSet animatorSet, Drawable drawable) {
        if (drawable instanceof FadeThroughDrawable) {
            ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            animator.addUpdateListener(new SearchViewAnimationHelper$$ExternalSyntheticLambda4((FadeThroughDrawable) drawable));
            animatorSet.playTogether(new Animator[]{animator});
        }
    }

    private void setFullDrawableProgressIfNeeded(Drawable drawable) {
        if (drawable instanceof DrawerArrowDrawable) {
            ((DrawerArrowDrawable) drawable).setProgress(1.0f);
        }
        if (drawable instanceof FadeThroughDrawable) {
            ((FadeThroughDrawable) drawable).setProgress(1.0f);
        }
    }

    private void addActionMenuViewAnimatorIfNeeded(AnimatorSet animatorSet) {
        ActionMenuView searchViewActionMenuView = ToolbarUtils.getActionMenuView(this.toolbar);
        if (searchViewActionMenuView != null) {
            ValueAnimator actionMenuViewAnimatorX = ValueAnimator.ofFloat(new float[]{(float) getTranslationXBetweenViews(ToolbarUtils.getActionMenuView(this.searchBar), searchViewActionMenuView), 0.0f});
            actionMenuViewAnimatorX.addUpdateListener(MultiViewUpdateListener.translationXListener(searchViewActionMenuView));
            ValueAnimator actionMenuViewAnimatorY = ValueAnimator.ofFloat(new float[]{(float) getFromTranslationY(), 0.0f});
            actionMenuViewAnimatorY.addUpdateListener(MultiViewUpdateListener.translationYListener(searchViewActionMenuView));
            animatorSet.playTogether(new Animator[]{actionMenuViewAnimatorX, actionMenuViewAnimatorY});
        }
    }

    private Animator getDummyToolbarAnimator(boolean show) {
        return getTranslationAnimator(show, this.dummyToolbar, getFromTranslationXEnd(this.dummyToolbar), getFromTranslationY());
    }

    private Animator getHeaderContainerAnimator(boolean show) {
        return getTranslationAnimator(show, this.headerContainer, getFromTranslationXEnd(this.headerContainer), getFromTranslationY());
    }

    private Animator getActionMenuViewsAlphaAnimator(boolean show) {
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.setDuration(show ? 300 : 250);
        animator.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        if (this.searchView.isMenuItemsAnimated()) {
            animator.addUpdateListener(new FadeThroughUpdateListener(ToolbarUtils.getActionMenuView(this.dummyToolbar), ToolbarUtils.getActionMenuView(this.toolbar)));
        }
        return animator;
    }

    private Animator getSearchPrefixAnimator(boolean show) {
        return getTranslationAnimatorForText(show, this.searchPrefix);
    }

    private Animator getEditTextAnimator(boolean show) {
        return getTranslationAnimatorForText(show, this.editText);
    }

    private AnimatorSet getTextAnimator(boolean show) {
        AnimatorSet animatorSet = new AnimatorSet();
        addTextFadeAnimatorIfNeeded(animatorSet);
        addEditTextClipAnimator(animatorSet);
        animatorSet.setDuration(show ? 300 : 250);
        animatorSet.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.LINEAR_INTERPOLATOR));
        return animatorSet;
    }

    private void addEditTextClipAnimator(AnimatorSet animatorSet) {
        if (this.searchBar != null && TextUtils.equals(this.editText.getText(), this.searchBar.getText())) {
            Rect editTextClipBounds = new Rect(0, 0, this.editText.getWidth(), this.editText.getHeight());
            ValueAnimator animator = ValueAnimator.ofInt(new int[]{this.searchBar.getTextView().getWidth(), this.editText.getWidth()});
            animator.addUpdateListener(new SearchViewAnimationHelper$$ExternalSyntheticLambda0(this, editTextClipBounds));
            animatorSet.playTogether(new Animator[]{animator});
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$addEditTextClipAnimator$6$com-google-android-material-search-SearchViewAnimationHelper  reason: not valid java name */
    public /* synthetic */ void m1708lambda$addEditTextClipAnimator$6$comgoogleandroidmaterialsearchSearchViewAnimationHelper(Rect editTextClipBounds, ValueAnimator animation) {
        editTextClipBounds.right = ((Integer) animation.getAnimatedValue()).intValue();
        this.editText.setClipBounds(editTextClipBounds);
    }

    private void addTextFadeAnimatorIfNeeded(AnimatorSet animatorSet) {
        if (this.searchBar != null && !TextUtils.equals(this.editText.getText(), this.searchBar.getText())) {
            ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            animator.addUpdateListener(new SearchViewAnimationHelper$$ExternalSyntheticLambda5(this));
            animatorSet.playTogether(new Animator[]{animator});
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$addTextFadeAnimatorIfNeeded$7$com-google-android-material-search-SearchViewAnimationHelper  reason: not valid java name */
    public /* synthetic */ void m1709lambda$addTextFadeAnimatorIfNeeded$7$comgoogleandroidmaterialsearchSearchViewAnimationHelper(ValueAnimator animation) {
        this.editText.setAlpha(((Float) animation.getAnimatedValue()).floatValue());
        this.searchBar.getTextView().setAlpha(1.0f - ((Float) animation.getAnimatedValue()).floatValue());
    }

    private Animator getTranslationAnimatorForText(boolean show, View v) {
        TextView textView = this.searchBar.getPlaceholderTextView();
        if (TextUtils.isEmpty(textView.getText()) || show) {
            textView = this.searchBar.getTextView();
        }
        return getTranslationAnimator(show, v, getViewLeftFromSearchViewParent(textView) - (v.getLeft() + this.textContainer.getLeft()), getFromTranslationY());
    }

    private int getViewLeftFromSearchViewParent(View v) {
        int left = v.getLeft();
        ViewParent viewParent = v.getParent();
        while ((viewParent instanceof View) && viewParent != this.searchView.getParent()) {
            left += ((View) viewParent).getLeft();
            viewParent = viewParent.getParent();
        }
        return left;
    }

    private int getViewTopFromSearchViewParent(View v) {
        int top = v.getTop();
        ViewParent viewParent = v.getParent();
        while ((viewParent instanceof View) && viewParent != this.searchView.getParent()) {
            top += ((View) viewParent).getTop();
            viewParent = viewParent.getParent();
        }
        return top;
    }

    private Animator getContentAnimator(boolean show) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{getContentAlphaAnimator(show), getDividerAnimator(show), getContentScaleAnimator(show)});
        return animatorSet;
    }

    private Animator getContentAlphaAnimator(boolean show) {
        ValueAnimator animatorAlpha = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animatorAlpha.setDuration(show ? 150 : HIDE_CONTENT_ALPHA_DURATION_MS);
        animatorAlpha.setStartDelay(show ? 75 : 0);
        animatorAlpha.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.LINEAR_INTERPOLATOR));
        animatorAlpha.addUpdateListener(MultiViewUpdateListener.alphaListener(this.divider, this.contentContainer));
        return animatorAlpha;
    }

    private Animator getDividerAnimator(boolean show) {
        ValueAnimator animatorDivider = ValueAnimator.ofFloat(new float[]{(((float) this.contentContainer.getHeight()) * 0.050000012f) / 2.0f, 0.0f});
        animatorDivider.setDuration(show ? 300 : 250);
        animatorDivider.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        animatorDivider.addUpdateListener(MultiViewUpdateListener.translationYListener(this.divider));
        return animatorDivider;
    }

    private Animator getContentScaleAnimator(boolean show) {
        ValueAnimator animatorScale = ValueAnimator.ofFloat(new float[]{CONTENT_FROM_SCALE, 1.0f});
        animatorScale.setDuration(show ? 300 : 250);
        animatorScale.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        animatorScale.addUpdateListener(MultiViewUpdateListener.scaleListener(this.contentContainer));
        return animatorScale;
    }

    private Animator getTranslationAnimator(boolean show, View view, int startX, int startY) {
        ValueAnimator animatorX = ValueAnimator.ofFloat(new float[]{(float) startX, 0.0f});
        animatorX.addUpdateListener(MultiViewUpdateListener.translationXListener(view));
        ValueAnimator animatorY = ValueAnimator.ofFloat(new float[]{(float) startY, 0.0f});
        animatorY.addUpdateListener(MultiViewUpdateListener.translationYListener(view));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{animatorX, animatorY});
        animatorSet.setDuration(show ? 300 : 250);
        animatorSet.setInterpolator(ReversableAnimatedValueInterpolator.of(show, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        return animatorSet;
    }

    private int getTranslationXBetweenViews(View searchBarSubView, View searchViewSubView) {
        if (searchBarSubView != null) {
            return getViewLeftFromSearchViewParent(searchBarSubView) - getViewLeftFromSearchViewParent(searchViewSubView);
        }
        int marginStart = ((ViewGroup.MarginLayoutParams) searchViewSubView.getLayoutParams()).getMarginStart();
        int paddingStart = this.searchBar.getPaddingStart();
        int searchBarLeft = getViewLeftFromSearchViewParent(this.searchBar);
        if (ViewUtils.isLayoutRtl(this.searchBar)) {
            return (((this.searchBar.getWidth() + searchBarLeft) + marginStart) - paddingStart) - this.searchView.getRight();
        }
        return (searchBarLeft - marginStart) + paddingStart;
    }

    private int getFromTranslationXEnd(View view) {
        int marginEnd = ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).getMarginEnd();
        int viewLeft = getViewLeftFromSearchViewParent(this.searchBar);
        if (ViewUtils.isLayoutRtl(this.searchBar)) {
            return viewLeft - marginEnd;
        }
        return ((this.searchBar.getWidth() + viewLeft) + marginEnd) - this.searchView.getWidth();
    }

    private int getFromTranslationY() {
        return (getViewTopFromSearchViewParent(this.searchBar) + (this.searchBar.getHeight() / 2)) - (this.toolbarContainer.getTop() + (this.toolbarContainer.getHeight() / 2));
    }

    private void setUpDummyToolbarIfNeeded() {
        Menu menu = this.dummyToolbar.getMenu();
        if (menu != null) {
            menu.clear();
        }
        if (this.searchBar.getMenuResId() == -1 || !this.searchView.isMenuItemsAnimated()) {
            this.dummyToolbar.setVisibility(8);
            return;
        }
        this.dummyToolbar.inflateMenu(this.searchBar.getMenuResId());
        setMenuItemsNotClickable(this.dummyToolbar);
        this.dummyToolbar.setVisibility(0);
    }

    private void setMenuItemsNotClickable(Toolbar toolbar2) {
        ActionMenuView actionMenuView = ToolbarUtils.getActionMenuView(toolbar2);
        if (actionMenuView != null) {
            for (int i = 0; i < actionMenuView.getChildCount(); i++) {
                View menuItem = actionMenuView.getChildAt(i);
                menuItem.setClickable(false);
                menuItem.setFocusable(false);
                menuItem.setFocusableInTouchMode(false);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void startBackProgress(BackEventCompat backEvent) {
        this.backHelper.startBackProgress(backEvent, (View) this.searchBar);
    }

    public void updateBackProgress(BackEventCompat backEvent) {
        if (backEvent.getProgress() > 0.0f) {
            this.backHelper.updateBackProgress(backEvent, this.searchBar, this.searchBar.getCornerSize());
            if (this.backProgressAnimatorSet == null) {
                if (this.searchView.isAdjustNothingSoftInputMode()) {
                    this.searchView.clearFocusAndHideKeyboard();
                }
                if (this.searchView.isAnimatedNavigationIcon()) {
                    this.backProgressAnimatorSet = getButtonsProgressAnimator(false);
                    this.backProgressAnimatorSet.start();
                    this.backProgressAnimatorSet.pause();
                    return;
                }
                return;
            }
            this.backProgressAnimatorSet.setCurrentPlayTime((long) (backEvent.getProgress() * ((float) this.backProgressAnimatorSet.getDuration())));
        }
    }

    public BackEventCompat onHandleBackInvoked() {
        return this.backHelper.onHandleBackInvoked();
    }

    public void finishBackProgress() {
        this.backHelper.finishBackProgress(hide().getTotalDuration(), this.searchBar);
        if (this.backProgressAnimatorSet != null) {
            getButtonsTranslationAnimator(false).start();
            this.backProgressAnimatorSet.resume();
        }
        this.backProgressAnimatorSet = null;
    }

    public void cancelBackProgress() {
        this.backHelper.cancelBackProgress(this.searchBar);
        if (this.backProgressAnimatorSet != null) {
            this.backProgressAnimatorSet.reverse();
        }
        this.backProgressAnimatorSet = null;
    }

    /* access modifiers changed from: package-private */
    public MaterialMainContainerBackHelper getBackHelper() {
        return this.backHelper;
    }
}
