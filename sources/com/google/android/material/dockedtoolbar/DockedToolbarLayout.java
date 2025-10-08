package com.google.android.material.dockedtoolbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.TintTypedArray;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class DockedToolbarLayout extends FrameLayout {
    private static final int DEF_STYLE_RES = R.style.Widget_Material3_DockedToolbar;
    private static final String TAG = DockedToolbarLayout.class.getSimpleName();
    /* access modifiers changed from: private */
    public Boolean paddingBottomSystemWindowInsets;
    /* access modifiers changed from: private */
    public Boolean paddingTopSystemWindowInsets;

    public DockedToolbarLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public DockedToolbarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.dockedToolbarStyle);
    }

    public DockedToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, DEF_STYLE_RES);
    }

    public DockedToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, defStyleRes), attrs, defStyleAttr);
        Context context2 = getContext();
        AttributeSet attrs2 = attrs;
        int defStyleAttr2 = defStyleAttr;
        int defStyleRes2 = defStyleRes;
        TintTypedArray attributes = ThemeEnforcement.obtainTintedStyledAttributes(context2, attrs2, R.styleable.DockedToolbar, defStyleAttr2, defStyleRes2, new int[0]);
        if (attributes.hasValue(R.styleable.DockedToolbar_backgroundTint)) {
            int backgroundColor = attributes.getColor(R.styleable.DockedToolbar_backgroundTint, 0);
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(ShapeAppearanceModel.builder(context2, attrs2, defStyleAttr2, defStyleRes2).build());
            materialShapeDrawable.setFillColor(ColorStateList.valueOf(backgroundColor));
            setBackground(materialShapeDrawable);
        }
        if (attributes.hasValue(R.styleable.DockedToolbar_paddingTopSystemWindowInsets)) {
            this.paddingTopSystemWindowInsets = Boolean.valueOf(attributes.getBoolean(R.styleable.DockedToolbar_paddingTopSystemWindowInsets, true));
        }
        if (attributes.hasValue(R.styleable.DockedToolbar_paddingBottomSystemWindowInsets)) {
            this.paddingBottomSystemWindowInsets = Boolean.valueOf(attributes.getBoolean(R.styleable.DockedToolbar_paddingBottomSystemWindowInsets, true));
        }
        ViewUtils.doOnApplyWindowInsets(this, new ViewUtils.OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets, ViewUtils.RelativePadding initialPadding) {
                if (DockedToolbarLayout.this.paddingTopSystemWindowInsets != null && DockedToolbarLayout.this.paddingBottomSystemWindowInsets != null && !DockedToolbarLayout.this.paddingTopSystemWindowInsets.booleanValue() && !DockedToolbarLayout.this.paddingBottomSystemWindowInsets.booleanValue()) {
                    return insets;
                }
                Insets systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout() | WindowInsetsCompat.Type.ime());
                int bottomInset = systemBarInsets.bottom;
                int topInset = systemBarInsets.top;
                int bottomPadding = 0;
                int topPadding = 0;
                ViewGroup.LayoutParams lp = view.getLayoutParams();
                if (DockedToolbarLayout.this.hasGravity(lp, 48) && DockedToolbarLayout.this.paddingTopSystemWindowInsets == null && DockedToolbarLayout.this.getFitsSystemWindows()) {
                    topPadding = topInset;
                }
                if (DockedToolbarLayout.this.hasGravity(lp, 80) && DockedToolbarLayout.this.paddingBottomSystemWindowInsets == null && DockedToolbarLayout.this.getFitsSystemWindows()) {
                    bottomPadding = bottomInset;
                }
                int i = 0;
                if (DockedToolbarLayout.this.paddingBottomSystemWindowInsets != null) {
                    bottomPadding = DockedToolbarLayout.this.paddingBottomSystemWindowInsets.booleanValue() ? bottomInset : 0;
                }
                if (DockedToolbarLayout.this.paddingTopSystemWindowInsets != null) {
                    if (DockedToolbarLayout.this.paddingTopSystemWindowInsets.booleanValue()) {
                        i = topInset;
                    }
                    topPadding = i;
                }
                initialPadding.top += topPadding;
                initialPadding.bottom += bottomPadding;
                initialPadding.applyToView(view);
                return insets;
            }
        });
        setImportantForAccessibility(1);
        attributes.recycle();
    }

    /* access modifiers changed from: private */
    public boolean hasGravity(ViewGroup.LayoutParams lp, int gravity) {
        if (lp instanceof CoordinatorLayout.LayoutParams) {
            if ((((CoordinatorLayout.LayoutParams) lp).gravity & gravity) == gravity) {
                return true;
            }
            return false;
        } else if (!(lp instanceof FrameLayout.LayoutParams)) {
            return false;
        } else {
            if ((((FrameLayout.LayoutParams) lp).gravity & gravity) == gravity) {
                return true;
            }
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (View.MeasureSpec.getMode(heightMeasureSpec) != 1073741824) {
            int childCount = getChildCount();
            int newHeight = Math.max(getMeasuredHeight(), getSuggestedMinimumHeight() + getPaddingTop() + getPaddingBottom());
            for (int i = 0; i < childCount; i++) {
                measureChild(getChildAt(i), widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(newHeight, 1073741824));
            }
            setMeasuredDimension(getMeasuredWidth(), newHeight);
        }
    }
}
