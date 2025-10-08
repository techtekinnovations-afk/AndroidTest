package com.google.android.material.navigationrail;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

public class NavigationRailFrameLayout extends FrameLayout {
    int paddingTop = 0;
    boolean scrollingEnabled = false;

    public NavigationRailFrameLayout(Context context) {
        super(context);
    }

    public void setPaddingTop(int paddingTop2) {
        this.paddingTop = paddingTop2;
    }

    public void setScrollingEnabled(boolean scrollingEnabled2) {
        this.scrollingEnabled = scrollingEnabled2;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        int totalHeaderHeight = 0;
        View menuView = getChildAt(0);
        int menuHeightSpec = heightMeasureSpec;
        int height = View.MeasureSpec.getSize(heightMeasureSpec);
        if (childCount > 1) {
            View headerView = getChildAt(0);
            measureChild(headerView, widthMeasureSpec, heightMeasureSpec);
            FrameLayout.LayoutParams headerLp = (FrameLayout.LayoutParams) headerView.getLayoutParams();
            totalHeaderHeight = headerView.getMeasuredHeight() + headerLp.bottomMargin + headerLp.topMargin;
            int maxMenuHeight = (height - totalHeaderHeight) - this.paddingTop;
            menuView = getChildAt(1);
            if (!this.scrollingEnabled) {
                menuHeightSpec = View.MeasureSpec.makeMeasureSpec(maxMenuHeight, Integer.MIN_VALUE);
            }
        }
        FrameLayout.LayoutParams menuLp = (FrameLayout.LayoutParams) menuView.getLayoutParams();
        measureChild(menuView, widthMeasureSpec, menuHeightSpec);
        setMeasuredDimension(getMeasuredWidth(), Math.max(height, this.paddingTop + totalHeaderHeight + menuView.getMeasuredHeight() + menuLp.bottomMargin + menuLp.topMargin));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int i = bottom;
        int bottom2 = right;
        int right2 = top;
        int top2 = left;
        int left2 = changed;
        int childCount = getChildCount();
        int y = this.paddingTop;
        for (int i2 = 0; i2 < childCount; i2++) {
            View child = getChildAt(i2);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) child.getLayoutParams();
            int y2 = Math.max(y, child.getTop()) + lp.topMargin;
            child.layout(child.getLeft(), y2, child.getRight(), child.getMeasuredHeight() + y2);
            y = y2 + child.getMeasuredHeight() + lp.bottomMargin;
        }
    }
}
