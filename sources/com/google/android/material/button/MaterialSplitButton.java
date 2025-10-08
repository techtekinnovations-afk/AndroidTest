package com.google.android.material.button;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.material.R;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class MaterialSplitButton extends MaterialButtonGroup {
    private static final int DEF_STYLE_RES = R.style.Widget_Material3_MaterialSplitButton;
    private static final int REQUIRED_BUTTON_COUNT = 2;

    public MaterialSplitButton(Context context) {
        this(context, (AttributeSet) null);
    }

    public MaterialSplitButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.materialSplitButtonStyle);
    }

    public MaterialSplitButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        int i;
        if (!(child instanceof MaterialButton)) {
            throw new IllegalArgumentException("MaterialSplitButton can only hold MaterialButtons.");
        } else if (getChildCount() <= 2) {
            MaterialButton buttonChild = (MaterialButton) child;
            super.addView(child, index, params);
            if (indexOfChild(child) == 1) {
                buttonChild.setCheckable(true);
                buttonChild.setA11yClassName(Button.class.getName());
                if (Build.VERSION.SDK_INT >= 30) {
                    Resources resources = getResources();
                    if (buttonChild.isChecked()) {
                        i = R.string.mtrl_button_expanded_content_description;
                    } else {
                        i = R.string.mtrl_button_collapsed_content_description;
                    }
                    buttonChild.setStateDescription(resources.getString(i));
                    buttonChild.addOnCheckedChangeListener(new MaterialSplitButton$$ExternalSyntheticLambda0(this));
                }
            }
        } else {
            throw new IllegalArgumentException("MaterialSplitButton can only hold two MaterialButtons.");
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$addView$0$com-google-android-material-button-MaterialSplitButton  reason: not valid java name */
    public /* synthetic */ void m1639lambda$addView$0$comgoogleandroidmaterialbuttonMaterialSplitButton(MaterialButton button, boolean isChecked) {
        int i;
        Resources resources = getResources();
        if (isChecked) {
            i = R.string.mtrl_button_expanded_content_description;
        } else {
            i = R.string.mtrl_button_collapsed_content_description;
        }
        button.setStateDescription(resources.getString(i));
    }
}
