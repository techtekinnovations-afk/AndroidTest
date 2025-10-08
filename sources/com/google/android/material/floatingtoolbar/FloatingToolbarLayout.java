package com.google.android.material.floatingtoolbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class FloatingToolbarLayout extends FrameLayout {
    private static final int DEF_STYLE_RES = R.style.Widget_Material3_FloatingToolbar;
    private static final String TAG = FloatingToolbarLayout.class.getSimpleName();
    /* access modifiers changed from: private */
    public int bottomMarginWindowInset;
    /* access modifiers changed from: private */
    public int leftMarginWindowInset;
    /* access modifiers changed from: private */
    public boolean marginBottomSystemWindowInsets;
    /* access modifiers changed from: private */
    public boolean marginLeftSystemWindowInsets;
    /* access modifiers changed from: private */
    public boolean marginRightSystemWindowInsets;
    /* access modifiers changed from: private */
    public boolean marginTopSystemWindowInsets;
    private Rect originalMargins;
    /* access modifiers changed from: private */
    public int rightMarginWindowInset;
    /* access modifiers changed from: private */
    public int topMarginWindowInset;

    public FloatingToolbarLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public FloatingToolbarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.floatingToolbarStyle);
    }

    public FloatingToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, DEF_STYLE_RES);
    }

    public FloatingToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, defStyleRes), attrs, defStyleAttr);
        Context context2 = getContext();
        AttributeSet attrs2 = attrs;
        int defStyleAttr2 = defStyleAttr;
        int defStyleRes2 = defStyleRes;
        TintTypedArray attributes = ThemeEnforcement.obtainTintedStyledAttributes(context2, attrs2, R.styleable.FloatingToolbar, defStyleAttr2, defStyleRes2, new int[0]);
        if (attributes.hasValue(R.styleable.FloatingToolbar_backgroundTint)) {
            int backgroundColor = attributes.getColor(R.styleable.FloatingToolbar_backgroundTint, 0);
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(ShapeAppearanceModel.builder(context2, attrs2, defStyleAttr2, defStyleRes2).build());
            materialShapeDrawable.setFillColor(ColorStateList.valueOf(backgroundColor));
            setBackground(materialShapeDrawable);
        }
        this.marginLeftSystemWindowInsets = attributes.getBoolean(R.styleable.FloatingToolbar_marginLeftSystemWindowInsets, true);
        this.marginTopSystemWindowInsets = attributes.getBoolean(R.styleable.FloatingToolbar_marginTopSystemWindowInsets, false);
        this.marginRightSystemWindowInsets = attributes.getBoolean(R.styleable.FloatingToolbar_marginRightSystemWindowInsets, true);
        this.marginBottomSystemWindowInsets = attributes.getBoolean(R.styleable.FloatingToolbar_marginBottomSystemWindowInsets, true);
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                if (!FloatingToolbarLayout.this.marginLeftSystemWindowInsets && !FloatingToolbarLayout.this.marginRightSystemWindowInsets && !FloatingToolbarLayout.this.marginTopSystemWindowInsets && !FloatingToolbarLayout.this.marginBottomSystemWindowInsets) {
                    return insets;
                }
                Insets systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout() | WindowInsetsCompat.Type.ime());
                int unused = FloatingToolbarLayout.this.bottomMarginWindowInset = systemBarInsets.bottom;
                int unused2 = FloatingToolbarLayout.this.topMarginWindowInset = systemBarInsets.top;
                int unused3 = FloatingToolbarLayout.this.rightMarginWindowInset = systemBarInsets.right;
                int unused4 = FloatingToolbarLayout.this.leftMarginWindowInset = systemBarInsets.left;
                FloatingToolbarLayout.this.updateMargins();
                return insets;
            }
        });
        attributes.recycle();
    }

    /* access modifiers changed from: private */
    public void updateMargins() {
        ViewGroup.LayoutParams lp = getLayoutParams();
        if (this.originalMargins == null) {
            Log.w(TAG, "Unable to update margins because original view margins are not set");
            return;
        }
        boolean marginChanged = false;
        int newLeftMargin = this.originalMargins.left + (this.marginLeftSystemWindowInsets ? this.leftMarginWindowInset : 0);
        int newRightMargin = this.originalMargins.right + (this.marginRightSystemWindowInsets ? this.rightMarginWindowInset : 0);
        int newTopMargin = this.originalMargins.top + (this.marginTopSystemWindowInsets ? this.topMarginWindowInset : 0);
        int newBottomMargin = this.originalMargins.bottom + (this.marginBottomSystemWindowInsets ? this.bottomMarginWindowInset : 0);
        ViewGroup.MarginLayoutParams marginLp = (ViewGroup.MarginLayoutParams) lp;
        if (!(marginLp.bottomMargin == newBottomMargin && marginLp.leftMargin == newLeftMargin && marginLp.rightMargin == newRightMargin && marginLp.topMargin == newTopMargin)) {
            marginChanged = true;
        }
        if (marginChanged) {
            marginLp.bottomMargin = newBottomMargin;
            marginLp.leftMargin = newLeftMargin;
            marginLp.rightMargin = newRightMargin;
            marginLp.topMargin = newTopMargin;
            requestLayout();
        }
    }

    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
            this.originalMargins = new Rect(marginParams.leftMargin, marginParams.topMargin, marginParams.rightMargin, marginParams.bottomMargin);
            updateMargins();
            return;
        }
        this.originalMargins = null;
    }
}
