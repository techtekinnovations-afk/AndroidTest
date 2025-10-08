package com.google.android.material.textfield;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.ListPopupWindow;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ManufacturerUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.List;

public class MaterialAutoCompleteTextView extends AppCompatAutoCompleteTextView {
    private static final int MAX_ITEMS_MEASURED = 15;
    private static final String SWITCH_ACCESS_ACTIVITY_NAME = "SwitchAccess";
    private final AccessibilityManager accessibilityManager;
    private ColorStateList dropDownBackgroundTint;
    /* access modifiers changed from: private */
    public final ListPopupWindow modalListPopup;
    private final float popupElevation;
    private final int simpleItemLayout;
    /* access modifiers changed from: private */
    public int simpleItemSelectedColor;
    /* access modifiers changed from: private */
    public ColorStateList simpleItemSelectedRippleColor;
    private final Rect tempRect;

    public MaterialAutoCompleteTextView(Context context) {
        this(context, (AttributeSet) null);
    }

    public MaterialAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.autoCompleteTextViewStyle);
    }

    public MaterialAutoCompleteTextView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, defStyleAttr, 0), attributeSet, defStyleAttr);
        this.tempRect = new Rect();
        Context context2 = getContext();
        TypedArray attributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, com.google.android.material.R.styleable.MaterialAutoCompleteTextView, defStyleAttr, R.style.Widget_AppCompat_AutoCompleteTextView, new int[0]);
        if (attributes.hasValue(com.google.android.material.R.styleable.MaterialAutoCompleteTextView_android_inputType) && attributes.getInt(com.google.android.material.R.styleable.MaterialAutoCompleteTextView_android_inputType, 0) == 0) {
            setKeyListener((KeyListener) null);
        }
        this.simpleItemLayout = attributes.getResourceId(com.google.android.material.R.styleable.MaterialAutoCompleteTextView_simpleItemLayout, com.google.android.material.R.layout.mtrl_auto_complete_simple_item);
        this.popupElevation = (float) attributes.getDimensionPixelOffset(com.google.android.material.R.styleable.MaterialAutoCompleteTextView_android_popupElevation, com.google.android.material.R.dimen.mtrl_exposed_dropdown_menu_popup_elevation);
        if (attributes.hasValue(com.google.android.material.R.styleable.MaterialAutoCompleteTextView_dropDownBackgroundTint)) {
            this.dropDownBackgroundTint = ColorStateList.valueOf(attributes.getColor(com.google.android.material.R.styleable.MaterialAutoCompleteTextView_dropDownBackgroundTint, 0));
        }
        this.simpleItemSelectedColor = attributes.getColor(com.google.android.material.R.styleable.MaterialAutoCompleteTextView_simpleItemSelectedColor, 0);
        this.simpleItemSelectedRippleColor = MaterialResources.getColorStateList(context2, attributes, com.google.android.material.R.styleable.MaterialAutoCompleteTextView_simpleItemSelectedRippleColor);
        this.accessibilityManager = (AccessibilityManager) context2.getSystemService("accessibility");
        this.modalListPopup = new ListPopupWindow(context2);
        this.modalListPopup.setModal(true);
        this.modalListPopup.setAnchorView(this);
        this.modalListPopup.setInputMethodMode(2);
        this.modalListPopup.setAdapter(getAdapter());
        this.modalListPopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int position, long id) {
                long id2;
                int position2;
                View selectedView2;
                MaterialAutoCompleteTextView materialAutoCompleteTextView = MaterialAutoCompleteTextView.this;
                MaterialAutoCompleteTextView.this.setText(MaterialAutoCompleteTextView.this.convertSelectionToString(position < 0 ? materialAutoCompleteTextView.modalListPopup.getSelectedItem() : materialAutoCompleteTextView.getAdapter().getItem(position)), false);
                AdapterView.OnItemClickListener userOnItemClickListener = MaterialAutoCompleteTextView.this.getOnItemClickListener();
                if (userOnItemClickListener != null) {
                    if (selectedView == null || position < 0) {
                        selectedView2 = MaterialAutoCompleteTextView.this.modalListPopup.getSelectedView();
                        position2 = MaterialAutoCompleteTextView.this.modalListPopup.getSelectedItemPosition();
                        id2 = MaterialAutoCompleteTextView.this.modalListPopup.getSelectedItemId();
                    } else {
                        selectedView2 = selectedView;
                        position2 = position;
                        id2 = id;
                    }
                    userOnItemClickListener.onItemClick(MaterialAutoCompleteTextView.this.modalListPopup.getListView(), selectedView2, position2, id2);
                    View view = selectedView2;
                    int i = position2;
                    long j = id2;
                }
                MaterialAutoCompleteTextView.this.modalListPopup.dismiss();
            }
        });
        if (attributes.hasValue(com.google.android.material.R.styleable.MaterialAutoCompleteTextView_simpleItems)) {
            setSimpleItems(attributes.getResourceId(com.google.android.material.R.styleable.MaterialAutoCompleteTextView_simpleItems, 0));
        }
        attributes.recycle();
    }

    public void showDropDown() {
        if (isPopupRequired()) {
            this.modalListPopup.show();
        } else {
            super.showDropDown();
        }
    }

    public void dismissDropDown() {
        if (isPopupRequired()) {
            this.modalListPopup.dismiss();
        } else {
            super.dismissDropDown();
        }
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!isPopupRequired()) {
            super.onWindowFocusChanged(hasWindowFocus);
        }
    }

    private boolean isPopupRequired() {
        return isTouchExplorationEnabled() || isSwitchAccessEnabled();
    }

    private boolean isTouchExplorationEnabled() {
        return this.accessibilityManager != null && this.accessibilityManager.isTouchExplorationEnabled();
    }

    private boolean isSwitchAccessEnabled() {
        List<AccessibilityServiceInfo> accessibilityServiceInfos;
        if (!(this.accessibilityManager == null || !this.accessibilityManager.isEnabled() || (accessibilityServiceInfos = this.accessibilityManager.getEnabledAccessibilityServiceList(16)) == null)) {
            for (AccessibilityServiceInfo info : accessibilityServiceInfos) {
                if (info.getSettingsActivityName() != null && info.getSettingsActivityName().contains(SWITCH_ACCESS_ACTIVITY_NAME)) {
                    return true;
                }
            }
        }
        return false;
    }

    public <T extends ListAdapter & Filterable> void setAdapter(T adapter) {
        super.setAdapter(adapter);
        this.modalListPopup.setAdapter(getAdapter());
    }

    public void setRawInputType(int type) {
        super.setRawInputType(type);
        onInputTypeChanged();
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
        super.setOnItemSelectedListener(listener);
        this.modalListPopup.setOnItemSelectedListener(getOnItemSelectedListener());
    }

    public void setSimpleItems(int stringArrayResId) {
        setSimpleItems(getResources().getStringArray(stringArrayResId));
    }

    public void setSimpleItems(String[] stringArray) {
        setAdapter(new MaterialArrayAdapter(getContext(), this.simpleItemLayout, stringArray));
    }

    public void setDropDownBackgroundTint(int dropDownBackgroundColor) {
        setDropDownBackgroundTintList(ColorStateList.valueOf(dropDownBackgroundColor));
    }

    public void setDropDownBackgroundTintList(ColorStateList dropDownBackgroundTint2) {
        this.dropDownBackgroundTint = dropDownBackgroundTint2;
        Drawable dropDownBackground = getDropDownBackground();
        if (dropDownBackground instanceof MaterialShapeDrawable) {
            ((MaterialShapeDrawable) dropDownBackground).setFillColor(this.dropDownBackgroundTint);
        }
    }

    public ColorStateList getDropDownBackgroundTintList() {
        return this.dropDownBackgroundTint;
    }

    public void setSimpleItemSelectedColor(int simpleItemSelectedColor2) {
        this.simpleItemSelectedColor = simpleItemSelectedColor2;
        if (getAdapter() instanceof MaterialArrayAdapter) {
            ((MaterialArrayAdapter) getAdapter()).updateSelectedItemColorStateList();
        }
    }

    public int getSimpleItemSelectedColor() {
        return this.simpleItemSelectedColor;
    }

    public void setSimpleItemSelectedRippleColor(ColorStateList simpleItemSelectedRippleColor2) {
        this.simpleItemSelectedRippleColor = simpleItemSelectedRippleColor2;
        if (getAdapter() instanceof MaterialArrayAdapter) {
            ((MaterialArrayAdapter) getAdapter()).updateSelectedItemColorStateList();
        }
    }

    public ColorStateList getSimpleItemSelectedRippleColor() {
        return this.simpleItemSelectedRippleColor;
    }

    public void setDropDownBackgroundDrawable(Drawable d) {
        super.setDropDownBackgroundDrawable(d);
        if (this.modalListPopup != null) {
            this.modalListPopup.setBackgroundDrawable(d);
        }
    }

    public float getPopupElevation() {
        return this.popupElevation;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        TextInputLayout layout = findTextInputLayoutAncestor();
        if (layout != null && layout.isProvidingHint() && super.getHint() == null && ManufacturerUtils.isMeizuDevice()) {
            setHint("");
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.modalListPopup.dismiss();
    }

    public CharSequence getHint() {
        TextInputLayout textInputLayout = findTextInputLayoutAncestor();
        if (textInputLayout == null || !textInputLayout.isProvidingHint()) {
            return super.getHint();
        }
        return textInputLayout.getHint();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (View.MeasureSpec.getMode(widthMeasureSpec) == Integer.MIN_VALUE) {
            setMeasuredDimension(Math.min(Math.max(getMeasuredWidth(), measureContentWidth()), View.MeasureSpec.getSize(widthMeasureSpec)), getMeasuredHeight());
        }
    }

    private int measureContentWidth() {
        ListAdapter adapter = getAdapter();
        TextInputLayout textInputLayout = findTextInputLayoutAncestor();
        if (adapter == null || textInputLayout == null) {
            return 0;
        }
        int width = 0;
        View itemView = null;
        int itemType = 0;
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
        int end = Math.min(adapter.getCount(), Math.max(0, this.modalListPopup.getSelectedItemPosition()) + 15);
        for (int i = Math.max(0, end - 15); i < end; i++) {
            int positionType = adapter.getItemViewType(i);
            if (positionType != itemType) {
                itemType = positionType;
                itemView = null;
            }
            itemView = adapter.getView(i, itemView, textInputLayout);
            if (itemView.getLayoutParams() == null) {
                itemView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            }
            itemView.measure(widthMeasureSpec, heightMeasureSpec);
            width = Math.max(width, itemView.getMeasuredWidth());
        }
        Drawable background = this.modalListPopup.getBackground();
        if (background != null) {
            background.getPadding(this.tempRect);
            width += this.tempRect.left + this.tempRect.right;
        }
        return width + textInputLayout.getEndIconView().getMeasuredWidth();
    }

    private void onInputTypeChanged() {
        TextInputLayout textInputLayout = findTextInputLayoutAncestor();
        if (textInputLayout != null) {
            textInputLayout.updateEditTextBoxBackgroundIfNeeded();
        }
    }

    private TextInputLayout findTextInputLayoutAncestor() {
        for (ViewParent parent = getParent(); parent != null; parent = parent.getParent()) {
            if (parent instanceof TextInputLayout) {
                return (TextInputLayout) parent;
            }
        }
        return null;
    }

    private class MaterialArrayAdapter<T> extends ArrayAdapter<String> {
        private ColorStateList pressedRippleColor;
        private ColorStateList selectedItemRippleOverlaidColor;

        MaterialArrayAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
            updateSelectedItemColorStateList();
        }

        /* access modifiers changed from: package-private */
        public void updateSelectedItemColorStateList() {
            this.pressedRippleColor = sanitizeDropdownItemSelectedRippleColor();
            this.selectedItemRippleOverlaidColor = createItemSelectedColorStateList();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setBackground(MaterialAutoCompleteTextView.this.getText().toString().contentEquals(textView.getText()) ? getSelectedItemDrawable() : null);
            }
            return view;
        }

        private Drawable getSelectedItemDrawable() {
            if (!hasSelectedColor()) {
                return null;
            }
            Drawable colorDrawable = new ColorDrawable(MaterialAutoCompleteTextView.this.simpleItemSelectedColor);
            if (this.pressedRippleColor == null) {
                return colorDrawable;
            }
            colorDrawable.setTintList(this.selectedItemRippleOverlaidColor);
            return new RippleDrawable(this.pressedRippleColor, colorDrawable, (Drawable) null);
        }

        private ColorStateList createItemSelectedColorStateList() {
            if (!hasSelectedColor() || !hasSelectedRippleColor()) {
                return null;
            }
            int[] stateHovered = {16843623, -16842919};
            int[] stateSelected = {16842913, -16842919};
            return new ColorStateList(new int[][]{stateSelected, stateHovered, new int[0]}, new int[]{MaterialColors.layer(MaterialAutoCompleteTextView.this.simpleItemSelectedColor, MaterialAutoCompleteTextView.this.simpleItemSelectedRippleColor.getColorForState(stateSelected, 0)), MaterialColors.layer(MaterialAutoCompleteTextView.this.simpleItemSelectedColor, MaterialAutoCompleteTextView.this.simpleItemSelectedRippleColor.getColorForState(stateHovered, 0)), MaterialAutoCompleteTextView.this.simpleItemSelectedColor});
        }

        private ColorStateList sanitizeDropdownItemSelectedRippleColor() {
            if (!hasSelectedRippleColor()) {
                return null;
            }
            int[] statePressed = {16842919};
            return new ColorStateList(new int[][]{statePressed, new int[0]}, new int[]{MaterialAutoCompleteTextView.this.simpleItemSelectedRippleColor.getColorForState(statePressed, 0), 0});
        }

        private boolean hasSelectedColor() {
            return MaterialAutoCompleteTextView.this.simpleItemSelectedColor != 0;
        }

        private boolean hasSelectedRippleColor() {
            return MaterialAutoCompleteTextView.this.simpleItemSelectedRippleColor != null;
        }
    }
}
