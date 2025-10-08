package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.navigation.NavigationBarMenuView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNavigationView extends NavigationBarView {
    private static final int MAX_ITEM_COUNT = 6;

    @Deprecated
    public interface OnNavigationItemReselectedListener extends NavigationBarView.OnItemReselectedListener {
    }

    @Deprecated
    public interface OnNavigationItemSelectedListener extends NavigationBarView.OnItemSelectedListener {
    }

    public BottomNavigationView(Context context) {
        this(context, (AttributeSet) null);
    }

    public BottomNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.bottomNavigationStyle);
    }

    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.Widget_Design_BottomNavigationView);
    }

    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TintTypedArray attributes = ThemeEnforcement.obtainTintedStyledAttributes(getContext(), attrs, R.styleable.BottomNavigationView, defStyleAttr, defStyleRes, new int[0]);
        setItemHorizontalTranslationEnabled(attributes.getBoolean(R.styleable.BottomNavigationView_itemHorizontalTranslationEnabled, true));
        if (attributes.hasValue(R.styleable.BottomNavigationView_android_minHeight)) {
            setMinimumHeight(attributes.getDimensionPixelSize(R.styleable.BottomNavigationView_android_minHeight, 0));
        }
        attributes.recycle();
        applyWindowInsets();
    }

    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }

    private void applyWindowInsets() {
        ViewUtils.doOnApplyWindowInsets(this, new ViewUtils.OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets, ViewUtils.RelativePadding initialPadding) {
                initialPadding.bottom += insets.getSystemWindowInsetBottom();
                boolean isRtl = true;
                if (view.getLayoutDirection() != 1) {
                    isRtl = false;
                }
                int systemWindowInsetLeft = insets.getSystemWindowInsetLeft();
                int systemWindowInsetRight = insets.getSystemWindowInsetRight();
                initialPadding.start += isRtl ? systemWindowInsetRight : systemWindowInsetLeft;
                initialPadding.end += isRtl ? systemWindowInsetLeft : systemWindowInsetRight;
                initialPadding.applyToView(view);
                return insets;
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, makeMinHeightSpec(heightMeasureSpec));
        if (View.MeasureSpec.getMode(heightMeasureSpec) != 1073741824) {
            setMeasuredDimension(getMeasuredWidth(), Math.max(getMeasuredHeight(), getSuggestedMinimumHeight() + getPaddingTop() + getPaddingBottom()));
        }
    }

    private int makeMinHeightSpec(int measureSpec) {
        int minHeight = getSuggestedMinimumHeight();
        if (View.MeasureSpec.getMode(measureSpec) == 1073741824 || minHeight <= 0) {
            return measureSpec;
        }
        return View.MeasureSpec.makeMeasureSpec(Math.max(View.MeasureSpec.getSize(measureSpec), minHeight + getPaddingTop() + getPaddingBottom()), Integer.MIN_VALUE);
    }

    public void setItemHorizontalTranslationEnabled(boolean itemHorizontalTranslationEnabled) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) getMenuView();
        if (menuView.isItemHorizontalTranslationEnabled() != itemHorizontalTranslationEnabled) {
            menuView.setItemHorizontalTranslationEnabled(itemHorizontalTranslationEnabled);
            getPresenter().updateMenuView(false);
        }
    }

    public boolean isItemHorizontalTranslationEnabled() {
        return ((BottomNavigationMenuView) getMenuView()).isItemHorizontalTranslationEnabled();
    }

    public int getMaxItemCount() {
        return 6;
    }

    /* access modifiers changed from: protected */
    public NavigationBarMenuView createNavigationBarMenuView(Context context) {
        return new BottomNavigationMenuView(context);
    }

    @Deprecated
    public void setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener listener) {
        setOnItemSelectedListener(listener);
    }

    @Deprecated
    public void setOnNavigationItemReselectedListener(OnNavigationItemReselectedListener listener) {
        setOnItemReselectedListener(listener);
    }
}
