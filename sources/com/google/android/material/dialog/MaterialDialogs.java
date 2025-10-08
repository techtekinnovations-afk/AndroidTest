package com.google.android.material.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.util.AttributeSet;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;

public class MaterialDialogs {
    private MaterialDialogs() {
    }

    public static InsetDrawable insetDrawable(Drawable drawable, Rect backgroundInsets) {
        return new InsetDrawable(drawable, backgroundInsets.left, backgroundInsets.top, backgroundInsets.right, backgroundInsets.bottom);
    }

    public static Rect getDialogBackgroundInsets(Context context, int defaultStyleAttribute, int defaultStyleResource) {
        Context context2 = context;
        TypedArray attributes = ThemeEnforcement.obtainStyledAttributes(context2, (AttributeSet) null, R.styleable.MaterialAlertDialog, defaultStyleAttribute, defaultStyleResource, new int[0]);
        int backgroundInsetStart = attributes.getDimensionPixelSize(R.styleable.MaterialAlertDialog_backgroundInsetStart, context2.getResources().getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_start));
        int backgroundInsetTop = attributes.getDimensionPixelSize(R.styleable.MaterialAlertDialog_backgroundInsetTop, context2.getResources().getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_top));
        int backgroundInsetEnd = attributes.getDimensionPixelSize(R.styleable.MaterialAlertDialog_backgroundInsetEnd, context2.getResources().getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_end));
        int backgroundInsetBottom = attributes.getDimensionPixelSize(R.styleable.MaterialAlertDialog_backgroundInsetBottom, context2.getResources().getDimensionPixelSize(R.dimen.mtrl_alert_dialog_background_inset_bottom));
        attributes.recycle();
        int layoutDirection = context2.getResources().getConfiguration().getLayoutDirection();
        return new Rect(layoutDirection == 1 ? backgroundInsetEnd : backgroundInsetStart, backgroundInsetTop, layoutDirection == 1 ? backgroundInsetStart : backgroundInsetEnd, backgroundInsetBottom);
    }
}
