package com.google.android.material.navigationrail;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarMenuView;

public class NavigationRailMenuView extends NavigationBarMenuView {
    private int itemMinimumHeight = -1;
    private int itemSpacing = 0;
    private final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);

    public NavigationRailMenuView(Context context) {
        super(context);
        this.layoutParams.gravity = 49;
        setLayoutParams(this.layoutParams);
        setItemActiveIndicatorResizeable(true);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredHeight;
        int maxHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        int visibleContentItemCount = getCurrentVisibleContentItemCount();
        if (visibleContentItemCount <= 1 || !isShifting(getLabelVisibilityMode(), visibleContentItemCount)) {
            measuredHeight = measureSharedChildHeights(widthMeasureSpec, maxHeight, visibleContentItemCount, (View) null);
        } else {
            measuredHeight = measureShiftingChildHeights(widthMeasureSpec, maxHeight, visibleContentItemCount);
        }
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), View.resolveSizeAndState(measuredHeight, heightMeasureSpec, 0));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int spacing;
        int count = getChildCount();
        int width = right - left;
        int visibleCount = 0;
        int childrenHeight = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                childrenHeight += child.getMeasuredHeight();
                visibleCount++;
            }
        }
        if (visibleCount <= 1) {
            spacing = 0;
        } else {
            spacing = Math.max(0, Math.min((getMeasuredHeight() - childrenHeight) / (visibleCount - 1), this.itemSpacing));
        }
        int used = 0;
        for (int i2 = 0; i2 < count; i2++) {
            View child2 = getChildAt(i2);
            if (child2.getVisibility() != 8) {
                int childHeight = child2.getMeasuredHeight();
                child2.layout(0, used, width, childHeight + used);
                used += childHeight + spacing;
            }
        }
    }

    /* access modifiers changed from: protected */
    public NavigationBarItemView createNavigationBarItemView(Context context) {
        return new NavigationRailItemView(context);
    }

    private int makeSharedHeightSpec(int parentWidthSpec, int maxHeight, int shareCount) {
        int minHeight;
        int maxAvailable = maxHeight / Math.max(1, shareCount);
        if (this.itemMinimumHeight != -1) {
            minHeight = this.itemMinimumHeight;
        } else {
            minHeight = View.MeasureSpec.getSize(parentWidthSpec);
        }
        return View.MeasureSpec.makeMeasureSpec(Math.min(minHeight, maxAvailable), 0);
    }

    private int measureShiftingChildHeights(int widthMeasureSpec, int maxHeight, int shareCount) {
        int selectedViewHeight = 0;
        View selectedView = getChildAt(getSelectedItemPosition());
        if (selectedView != null) {
            selectedViewHeight = measureChildHeight(selectedView, widthMeasureSpec, makeSharedHeightSpec(widthMeasureSpec, maxHeight, shareCount));
            maxHeight -= selectedViewHeight;
            shareCount--;
        }
        return measureSharedChildHeights(widthMeasureSpec, maxHeight, shareCount, selectedView) + selectedViewHeight;
    }

    private int measureSharedChildHeights(int widthMeasureSpec, int maxHeight, int shareCount, View selectedView) {
        int childHeightSpec;
        int subheaderHeightSpec = View.MeasureSpec.makeMeasureSpec(maxHeight, 0);
        int childCount = getChildCount();
        int totalHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (!(child instanceof NavigationBarItemView)) {
                int subheaderHeight = measureChildHeight(child, widthMeasureSpec, subheaderHeightSpec);
                maxHeight -= subheaderHeight;
                totalHeight += subheaderHeight;
            }
        }
        int maxHeight2 = Math.max(maxHeight, 0);
        if (selectedView == null) {
            childHeightSpec = makeSharedHeightSpec(widthMeasureSpec, maxHeight2, shareCount);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(selectedView.getMeasuredHeight(), 0);
        }
        int visibleChildCount = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            View child2 = getChildAt(i2);
            if (child2.getVisibility() == 0) {
                visibleChildCount++;
            }
            if ((child2 instanceof NavigationBarItemView) && child2 != selectedView) {
                totalHeight += measureChildHeight(child2, widthMeasureSpec, childHeightSpec);
            }
        }
        return (Math.max(0, visibleChildCount - 1) * this.itemSpacing) + totalHeight;
    }

    private int measureChildHeight(View child, int widthMeasureSpec, int heightMeasureSpec) {
        child.measure(widthMeasureSpec, heightMeasureSpec);
        if (child.getVisibility() != 8) {
            return child.getMeasuredHeight();
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public void setMenuGravity(int gravity) {
        if (this.layoutParams.gravity != gravity) {
            this.layoutParams.gravity = gravity;
            setLayoutParams(this.layoutParams);
        }
    }

    /* access modifiers changed from: package-private */
    public int getMenuGravity() {
        return this.layoutParams.gravity;
    }

    public void setItemMinimumHeight(int minHeight) {
        if (this.itemMinimumHeight != minHeight) {
            this.itemMinimumHeight = minHeight;
            requestLayout();
        }
    }

    public int getItemMinimumHeight() {
        return this.itemMinimumHeight;
    }

    public void setItemSpacing(int spacing) {
        if (this.itemSpacing != spacing) {
            this.itemSpacing = spacing;
            requestLayout();
        }
    }

    public int getItemSpacing() {
        return this.itemSpacing;
    }
}
