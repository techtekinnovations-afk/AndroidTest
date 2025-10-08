package com.google.android.material.navigationrail;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;
import androidx.transition.ChangeBounds;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.navigation.NavigationBarDividerView;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.resources.MaterialResources;

public class NavigationRailView extends NavigationBarView {
    static final int COLLAPSED_MAX_ITEM_COUNT = 7;
    private static final TimeInterpolator CUBIC_BEZIER_INTERPOLATOR = new PathInterpolator(0.38f, 1.21f, 0.22f, 1.0f);
    private static final int DEFAULT_HEADER_GRAVITY = 49;
    static final int DEFAULT_MENU_GRAVITY = 49;
    private static final int EXPAND_DURATION = 500;
    private static final int FADE_DURATION = 100;
    static final int NO_ITEM_MINIMUM_HEIGHT = -1;
    private int collapsedIconGravity;
    private int collapsedItemGravity;
    private int collapsedItemMinHeight;
    private int collapsedItemSpacing;
    private NavigationRailFrameLayout contentContainer;
    private final int contentMarginTop;
    private boolean expanded;
    private int expandedIconGravity;
    private int expandedItemGravity;
    private int expandedItemMinHeight;
    private int expandedItemSpacing;
    private final int headerMarginBottom;
    private View headerView;
    private final int maxExpandedWidth;
    private final int minExpandedWidth;
    /* access modifiers changed from: private */
    public Boolean paddingBottomSystemWindowInsets;
    /* access modifiers changed from: private */
    public Boolean paddingStartSystemWindowInsets;
    /* access modifiers changed from: private */
    public Boolean paddingTopSystemWindowInsets;
    private final boolean scrollingEnabled;
    private boolean submenuDividersEnabled;

    public NavigationRailView(Context context) {
        this(context, (AttributeSet) null);
    }

    public NavigationRailView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.navigationRailStyle);
    }

    public NavigationRailView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.Widget_MaterialComponents_NavigationRailView);
    }

    public NavigationRailView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.paddingTopSystemWindowInsets = null;
        this.paddingBottomSystemWindowInsets = null;
        this.paddingStartSystemWindowInsets = null;
        this.expanded = false;
        this.collapsedItemMinHeight = -1;
        this.collapsedIconGravity = 0;
        this.collapsedItemGravity = 49;
        Context context2 = getContext();
        this.expandedItemSpacing = getContext().getResources().getDimensionPixelSize(R.dimen.m3_navigation_rail_expanded_item_spacing);
        this.expandedItemGravity = NavigationBarView.ITEM_GRAVITY_START_CENTER;
        this.expandedIconGravity = 1;
        TintTypedArray attributes = ThemeEnforcement.obtainTintedStyledAttributes(context2, attrs, R.styleable.NavigationRailView, defStyleAttr, defStyleRes, new int[0]);
        this.contentMarginTop = attributes.getDimensionPixelSize(R.styleable.NavigationRailView_contentMarginTop, getResources().getDimensionPixelSize(R.dimen.mtrl_navigation_rail_margin));
        this.headerMarginBottom = attributes.getDimensionPixelSize(R.styleable.NavigationRailView_headerMarginBottom, getResources().getDimensionPixelSize(R.dimen.mtrl_navigation_rail_margin));
        this.scrollingEnabled = attributes.getBoolean(R.styleable.NavigationRailView_scrollingEnabled, false);
        setSubmenuDividersEnabled(attributes.getBoolean(R.styleable.NavigationRailView_submenuDividersEnabled, false));
        addContentContainer();
        int headerLayoutRes = attributes.getResourceId(R.styleable.NavigationRailView_headerLayout, 0);
        if (headerLayoutRes != 0) {
            addHeaderView(headerLayoutRes);
        }
        setMenuGravity(attributes.getInt(R.styleable.NavigationRailView_menuGravity, 49));
        int collapsedItemMinHeight2 = attributes.getDimensionPixelSize(R.styleable.NavigationRailView_itemMinHeight, -1);
        int expandedItemMinHeight2 = attributes.getDimensionPixelSize(R.styleable.NavigationRailView_itemMinHeight, -1);
        collapsedItemMinHeight2 = attributes.hasValue(R.styleable.NavigationRailView_collapsedItemMinHeight) ? attributes.getDimensionPixelSize(R.styleable.NavigationRailView_collapsedItemMinHeight, -1) : collapsedItemMinHeight2;
        expandedItemMinHeight2 = attributes.hasValue(R.styleable.NavigationRailView_expandedItemMinHeight) ? attributes.getDimensionPixelSize(R.styleable.NavigationRailView_expandedItemMinHeight, -1) : expandedItemMinHeight2;
        setCollapsedItemMinimumHeight(collapsedItemMinHeight2);
        setExpandedItemMinimumHeight(expandedItemMinHeight2);
        this.minExpandedWidth = attributes.getDimensionPixelSize(R.styleable.NavigationRailView_expandedMinWidth, context2.getResources().getDimensionPixelSize(R.dimen.m3_navigation_rail_min_expanded_width));
        this.maxExpandedWidth = attributes.getDimensionPixelSize(R.styleable.NavigationRailView_expandedMaxWidth, context2.getResources().getDimensionPixelSize(R.dimen.m3_navigation_rail_max_expanded_width));
        if (attributes.hasValue(R.styleable.NavigationRailView_paddingTopSystemWindowInsets)) {
            this.paddingTopSystemWindowInsets = Boolean.valueOf(attributes.getBoolean(R.styleable.NavigationRailView_paddingTopSystemWindowInsets, false));
        }
        if (attributes.hasValue(R.styleable.NavigationRailView_paddingBottomSystemWindowInsets)) {
            this.paddingBottomSystemWindowInsets = Boolean.valueOf(attributes.getBoolean(R.styleable.NavigationRailView_paddingBottomSystemWindowInsets, false));
        }
        if (attributes.hasValue(R.styleable.NavigationRailView_paddingStartSystemWindowInsets)) {
            this.paddingStartSystemWindowInsets = Boolean.valueOf(attributes.getBoolean(R.styleable.NavigationRailView_paddingStartSystemWindowInsets, false));
        }
        int largeFontTopPadding = getResources().getDimensionPixelOffset(R.dimen.m3_navigation_rail_item_padding_top_with_large_font);
        int largeFontBottomPadding = getResources().getDimensionPixelOffset(R.dimen.m3_navigation_rail_item_padding_bottom_with_large_font);
        float progress = AnimationUtils.lerp(0.0f, 1.0f, 0.3f, 1.0f, MaterialResources.getFontScale(context2) - 1.0f);
        setItemPaddingTop(Math.round((float) AnimationUtils.lerp(getItemPaddingTop(), largeFontTopPadding, progress)));
        setItemPaddingBottom(Math.round((float) AnimationUtils.lerp(getItemPaddingBottom(), largeFontBottomPadding, progress)));
        setCollapsedItemSpacing(attributes.getDimensionPixelSize(R.styleable.NavigationRailView_itemSpacing, 0));
        setExpanded(attributes.getBoolean(R.styleable.NavigationRailView_expanded, false));
        attributes.recycle();
        applyWindowInsets();
    }

    private void startTransitionAnimation() {
        if (isLaidOut()) {
            Transition changeBoundsTransition = new ChangeBounds().setDuration(500).setInterpolator(CUBIC_BEZIER_INTERPOLATOR);
            Transition labelFadeInTransition = new Fade().setDuration(100);
            Transition labelFadeOutTransition = new Fade().setDuration(100);
            Transition labelHorizontalMoveTransition = new LabelMoveTransition();
            Transition fadingItemsTransition = new Fade().setDuration(100);
            int childCount = getNavigationRailMenuView().getChildCount();
            for (int i = 0; i < childCount; i++) {
                View item = getNavigationRailMenuView().getChildAt(i);
                if (item instanceof NavigationBarItemView) {
                    changeBoundsTransition.excludeTarget((View) ((NavigationBarItemView) item).getLabelGroup(), true);
                    changeBoundsTransition.excludeTarget((View) ((NavigationBarItemView) item).getExpandedLabelGroup(), true);
                    if (this.expanded) {
                        labelFadeOutTransition.addTarget((View) ((NavigationBarItemView) item).getExpandedLabelGroup());
                        labelFadeInTransition.addTarget((View) ((NavigationBarItemView) item).getLabelGroup());
                    } else {
                        labelFadeOutTransition.addTarget((View) ((NavigationBarItemView) item).getLabelGroup());
                        labelFadeInTransition.addTarget((View) ((NavigationBarItemView) item).getExpandedLabelGroup());
                    }
                    labelHorizontalMoveTransition.addTarget((View) ((NavigationBarItemView) item).getExpandedLabelGroup());
                }
                fadingItemsTransition.addTarget(item);
            }
            TransitionSet changeBoundsFadeLabelInTransition = new TransitionSet();
            changeBoundsFadeLabelInTransition.setOrdering(0);
            changeBoundsFadeLabelInTransition.addTransition(changeBoundsTransition).addTransition(labelFadeInTransition).addTransition(labelHorizontalMoveTransition);
            if (!this.expanded) {
                changeBoundsFadeLabelInTransition.addTransition(fadingItemsTransition);
            }
            TransitionSet fadeOutTransitions = new TransitionSet();
            fadeOutTransitions.setOrdering(0);
            fadeOutTransitions.addTransition(labelFadeOutTransition);
            if (this.expanded) {
                fadeOutTransitions.addTransition(fadingItemsTransition);
            }
            TransitionSet transitionSet = new TransitionSet();
            transitionSet.setOrdering(1);
            transitionSet.addTransition(fadeOutTransitions).addTransition(changeBoundsFadeLabelInTransition);
            TransitionManager.beginDelayedTransition((ViewGroup) getParent(), transitionSet);
        }
    }

    public void setItemIconGravity(int itemIconGravity) {
        this.collapsedIconGravity = itemIconGravity;
        this.expandedIconGravity = itemIconGravity;
        super.setItemIconGravity(itemIconGravity);
    }

    public int getItemIconGravity() {
        return getNavigationRailMenuView().getItemIconGravity();
    }

    public void setItemGravity(int itemGravity) {
        this.collapsedItemGravity = itemGravity;
        this.expandedItemGravity = itemGravity;
        super.setItemGravity(itemGravity);
    }

    public int getItemGravity() {
        return getNavigationRailMenuView().getItemGravity();
    }

    private void setExpanded(boolean expanded2) {
        if (this.expanded != expanded2) {
            startTransitionAnimation();
            this.expanded = expanded2;
            int iconGravity = this.collapsedIconGravity;
            int itemSpacing = this.collapsedItemSpacing;
            int itemMinHeight = this.collapsedItemMinHeight;
            int itemGravity = this.collapsedItemGravity;
            if (expanded2) {
                iconGravity = this.expandedIconGravity;
                itemSpacing = this.expandedItemSpacing;
                itemMinHeight = this.expandedItemMinHeight;
                itemGravity = this.expandedItemGravity;
            }
            getNavigationRailMenuView().setItemGravity(itemGravity);
            super.setItemIconGravity(iconGravity);
            getNavigationRailMenuView().setItemSpacing(itemSpacing);
            getNavigationRailMenuView().setItemMinimumHeight(itemMinHeight);
            getNavigationRailMenuView().setExpanded(expanded2);
        }
    }

    public void expand() {
        if (!this.expanded) {
            setExpanded(true);
            announceForAccessibility(getResources().getString(R.string.nav_rail_expanded_a11y_label));
        }
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    public void collapse() {
        if (this.expanded) {
            setExpanded(false);
            announceForAccessibility(getResources().getString(R.string.nav_rail_collapsed_a11y_label));
        }
    }

    private void applyWindowInsets() {
        ViewUtils.doOnApplyWindowInsets(this, new ViewUtils.OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets, ViewUtils.RelativePadding initialPadding) {
                Insets systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                Insets displayCutoutInsets = insets.getInsets(WindowInsetsCompat.Type.displayCutout());
                if (NavigationRailView.this.shouldApplyWindowInsetPadding(NavigationRailView.this.paddingTopSystemWindowInsets)) {
                    initialPadding.top += systemBarInsets.top;
                }
                if (NavigationRailView.this.shouldApplyWindowInsetPadding(NavigationRailView.this.paddingBottomSystemWindowInsets)) {
                    initialPadding.bottom += systemBarInsets.bottom;
                }
                if (NavigationRailView.this.shouldApplyWindowInsetPadding(NavigationRailView.this.paddingStartSystemWindowInsets)) {
                    if (ViewUtils.isLayoutRtl(view)) {
                        initialPadding.start += Math.max(systemBarInsets.right, displayCutoutInsets.right);
                    } else {
                        initialPadding.start += Math.max(systemBarInsets.left, displayCutoutInsets.left);
                    }
                }
                initialPadding.applyToView(view);
                return insets;
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean shouldApplyWindowInsetPadding(Boolean paddingInsetFlag) {
        return paddingInsetFlag != null ? paddingInsetFlag.booleanValue() : getFitsSystemWindows();
    }

    private int getMaxChildWidth() {
        int childCount = getNavigationRailMenuView().getChildCount();
        int maxChildWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getNavigationRailMenuView().getChildAt(i);
            if (child.getVisibility() != 8 && !(child instanceof NavigationBarDividerView)) {
                maxChildWidth = Math.max(maxChildWidth, child.getMeasuredWidth());
            }
        }
        return maxChildWidth;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minWidthSpec = makeMinWidthSpec(widthMeasureSpec);
        if (this.expanded) {
            measureChild(getNavigationRailMenuView(), widthMeasureSpec, heightMeasureSpec);
            if (this.headerView != null) {
                measureChild(this.headerView, widthMeasureSpec, heightMeasureSpec);
            }
            minWidthSpec = makeExpandedWidthMeasureSpec(widthMeasureSpec, getMaxChildWidth());
            if (getItemActiveIndicatorExpandedWidth() == -1) {
                getNavigationRailMenuView().updateActiveIndicator(View.MeasureSpec.getSize(minWidthSpec));
            }
        }
        super.onMeasure(minWidthSpec, heightMeasureSpec);
        if (this.contentContainer.getMeasuredHeight() < getMeasuredHeight()) {
            measureChild(this.contentContainer, minWidthSpec, View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
        }
    }

    public void addHeaderView(int layoutRes) {
        addHeaderView(LayoutInflater.from(getContext()).inflate(layoutRes, this, false));
    }

    public void addHeaderView(View headerView2) {
        removeHeaderView();
        this.headerView = headerView2;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        params.gravity = 49;
        params.bottomMargin = this.headerMarginBottom;
        this.contentContainer.addView(headerView2, 0, params);
    }

    public View getHeaderView() {
        return this.headerView;
    }

    public void removeHeaderView() {
        if (this.headerView != null) {
            this.contentContainer.removeView(this.headerView);
            this.headerView = null;
        }
    }

    public void setMenuGravity(int gravity) {
        getNavigationRailMenuView().setMenuGravity(gravity);
    }

    public int getMenuGravity() {
        return getNavigationRailMenuView().getMenuGravity();
    }

    public int getItemMinimumHeight() {
        return getNavigationRailMenuView().getItemMinimumHeight();
    }

    public void setItemMinimumHeight(int minHeight) {
        this.collapsedItemMinHeight = minHeight;
        this.expandedItemMinHeight = minHeight;
        ((NavigationRailMenuView) getMenuView()).setItemMinimumHeight(minHeight);
    }

    public void setCollapsedItemMinimumHeight(int minHeight) {
        this.collapsedItemMinHeight = minHeight;
        if (!this.expanded) {
            ((NavigationRailMenuView) getMenuView()).setItemMinimumHeight(minHeight);
        }
    }

    public int getCollapsedItemMinimumHeight() {
        return this.collapsedItemMinHeight;
    }

    public void setExpandedItemMinimumHeight(int minHeight) {
        this.expandedItemMinHeight = minHeight;
        if (this.expanded) {
            ((NavigationRailMenuView) getMenuView()).setItemMinimumHeight(minHeight);
        }
    }

    public int getExpandedItemMinimumHeight() {
        return this.expandedItemMinHeight;
    }

    public void setSubmenuDividersEnabled(boolean submenuDividersEnabled2) {
        if (this.submenuDividersEnabled != submenuDividersEnabled2) {
            this.submenuDividersEnabled = submenuDividersEnabled2;
            getNavigationRailMenuView().setSubmenuDividersEnabled(submenuDividersEnabled2);
        }
    }

    public boolean getSubmenuDividersEnabled() {
        return this.submenuDividersEnabled;
    }

    public void setItemSpacing(int itemSpacing) {
        this.collapsedItemSpacing = itemSpacing;
        this.expandedItemSpacing = itemSpacing;
        getNavigationRailMenuView().setItemSpacing(itemSpacing);
    }

    public void setCollapsedItemSpacing(int itemSpacing) {
        this.collapsedItemSpacing = itemSpacing;
        if (!this.expanded) {
            getNavigationRailMenuView().setItemSpacing(itemSpacing);
        }
    }

    public int getItemSpacing() {
        return getNavigationRailMenuView().getItemSpacing();
    }

    public int getMaxItemCount() {
        return Integer.MAX_VALUE;
    }

    public int getCollapsedMaxItemCount() {
        return 7;
    }

    private NavigationRailMenuView getNavigationRailMenuView() {
        return (NavigationRailMenuView) getMenuView();
    }

    /* access modifiers changed from: protected */
    public NavigationRailMenuView createNavigationBarMenuView(Context context) {
        return new NavigationRailMenuView(context);
    }

    private int makeMinWidthSpec(int measureSpec) {
        int minWidth = getSuggestedMinimumWidth();
        if (View.MeasureSpec.getMode(measureSpec) == 1073741824 || minWidth <= 0) {
            return measureSpec;
        }
        return View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(measureSpec), minWidth + getPaddingLeft() + getPaddingRight()), 1073741824);
    }

    private int makeExpandedWidthMeasureSpec(int measureSpec, int measuredWidth) {
        int minWidth = Math.min(this.minExpandedWidth, View.MeasureSpec.getSize(measureSpec));
        if (View.MeasureSpec.getMode(measureSpec) == 1073741824) {
            return measureSpec;
        }
        int newWidth = Math.max(measuredWidth, minWidth);
        if (this.headerView != null) {
            newWidth = Math.max(newWidth, this.headerView.getMeasuredWidth());
        }
        return View.MeasureSpec.makeMeasureSpec(Math.max(getSuggestedMinimumWidth(), Math.min(newWidth, this.maxExpandedWidth)), 1073741824);
    }

    /* access modifiers changed from: protected */
    public boolean isSubMenuSupported() {
        return true;
    }

    private void addContentContainer() {
        View menuView = (View) getMenuView();
        this.contentContainer = new NavigationRailFrameLayout(getContext());
        this.contentContainer.setPaddingTop(this.contentMarginTop);
        this.contentContainer.setScrollingEnabled(this.scrollingEnabled);
        this.contentContainer.setClipChildren(false);
        this.contentContainer.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        menuView.setLayoutParams(new FrameLayout.LayoutParams(-2, -2));
        this.contentContainer.addView(menuView);
        if (!this.scrollingEnabled) {
            addView(this.contentContainer);
            return;
        }
        ScrollView scrollView = new ScrollView(getContext());
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.addView(this.contentContainer);
        scrollView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        addView(scrollView);
    }

    public boolean shouldAddMenuView() {
        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }
}
