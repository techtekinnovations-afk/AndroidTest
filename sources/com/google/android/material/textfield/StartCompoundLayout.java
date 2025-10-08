package com.google.android.material.textfield;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.GravityCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.R;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;

class StartCompoundLayout extends LinearLayout {
    private boolean hintExpanded;
    private CharSequence prefixText;
    private final TextView prefixTextView;
    private int startIconMinSize;
    private View.OnLongClickListener startIconOnLongClickListener;
    private ImageView.ScaleType startIconScaleType;
    private ColorStateList startIconTintList;
    private PorterDuff.Mode startIconTintMode;
    private final CheckableImageButton startIconView = ((CheckableImageButton) LayoutInflater.from(getContext()).inflate(R.layout.design_text_input_start_icon, this, false));
    private final TextInputLayout textInputLayout;

    StartCompoundLayout(TextInputLayout textInputLayout2, TintTypedArray a) {
        super(textInputLayout2.getContext());
        this.textInputLayout = textInputLayout2;
        setVisibility(8);
        setOrientation(0);
        setLayoutParams(new FrameLayout.LayoutParams(-2, -1, GravityCompat.START));
        IconHelper.setCompatRippleBackgroundIfNeeded(this.startIconView);
        this.prefixTextView = new AppCompatTextView(getContext());
        initStartIconView(a);
        initPrefixTextView(a);
        addView(this.startIconView);
        addView(this.prefixTextView);
    }

    private void initStartIconView(TintTypedArray a) {
        if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
            ((ViewGroup.MarginLayoutParams) this.startIconView.getLayoutParams()).setMarginEnd(0);
        }
        setStartIconOnClickListener((View.OnClickListener) null);
        setStartIconOnLongClickListener((View.OnLongClickListener) null);
        if (a.hasValue(R.styleable.TextInputLayout_startIconTint)) {
            this.startIconTintList = MaterialResources.getColorStateList(getContext(), a, R.styleable.TextInputLayout_startIconTint);
        }
        if (a.hasValue(R.styleable.TextInputLayout_startIconTintMode)) {
            this.startIconTintMode = ViewUtils.parseTintMode(a.getInt(R.styleable.TextInputLayout_startIconTintMode, -1), (PorterDuff.Mode) null);
        }
        if (a.hasValue(R.styleable.TextInputLayout_startIconDrawable)) {
            setStartIconDrawable(a.getDrawable(R.styleable.TextInputLayout_startIconDrawable));
            if (a.hasValue(R.styleable.TextInputLayout_startIconContentDescription)) {
                setStartIconContentDescription(a.getText(R.styleable.TextInputLayout_startIconContentDescription));
            }
            setStartIconCheckable(a.getBoolean(R.styleable.TextInputLayout_startIconCheckable, true));
        }
        setStartIconMinSize(a.getDimensionPixelSize(R.styleable.TextInputLayout_startIconMinSize, getResources().getDimensionPixelSize(R.dimen.mtrl_min_touch_target_size)));
        if (a.hasValue(R.styleable.TextInputLayout_startIconScaleType)) {
            setStartIconScaleType(IconHelper.convertScaleType(a.getInt(R.styleable.TextInputLayout_startIconScaleType, -1)));
        }
    }

    private void initPrefixTextView(TintTypedArray a) {
        this.prefixTextView.setVisibility(8);
        this.prefixTextView.setId(R.id.textinput_prefix_text);
        this.prefixTextView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        this.prefixTextView.setAccessibilityLiveRegion(1);
        setPrefixTextAppearance(a.getResourceId(R.styleable.TextInputLayout_prefixTextAppearance, 0));
        if (a.hasValue(R.styleable.TextInputLayout_prefixTextColor)) {
            setPrefixTextColor(a.getColorStateList(R.styleable.TextInputLayout_prefixTextColor));
        }
        setPrefixText(a.getText(R.styleable.TextInputLayout_prefixText));
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        updatePrefixTextViewPadding();
    }

    /* access modifiers changed from: package-private */
    public TextView getPrefixTextView() {
        return this.prefixTextView;
    }

    /* access modifiers changed from: package-private */
    public void setPrefixText(CharSequence prefixText2) {
        this.prefixText = TextUtils.isEmpty(prefixText2) ? null : prefixText2;
        this.prefixTextView.setText(prefixText2);
        updateVisibility();
    }

    /* access modifiers changed from: package-private */
    public CharSequence getPrefixText() {
        return this.prefixText;
    }

    /* access modifiers changed from: package-private */
    public void setPrefixTextColor(ColorStateList prefixTextColor) {
        this.prefixTextView.setTextColor(prefixTextColor);
    }

    /* access modifiers changed from: package-private */
    public ColorStateList getPrefixTextColor() {
        return this.prefixTextView.getTextColors();
    }

    /* access modifiers changed from: package-private */
    public void setPrefixTextAppearance(int prefixTextAppearance) {
        TextViewCompat.setTextAppearance(this.prefixTextView, prefixTextAppearance);
    }

    /* access modifiers changed from: package-private */
    public void setStartIconDrawable(Drawable startIconDrawable) {
        this.startIconView.setImageDrawable(startIconDrawable);
        if (startIconDrawable != null) {
            IconHelper.applyIconTint(this.textInputLayout, this.startIconView, this.startIconTintList, this.startIconTintMode);
            setStartIconVisible(true);
            refreshStartIconDrawableState();
            return;
        }
        setStartIconVisible(false);
        setStartIconOnClickListener((View.OnClickListener) null);
        setStartIconOnLongClickListener((View.OnLongClickListener) null);
        setStartIconContentDescription((CharSequence) null);
    }

    /* access modifiers changed from: package-private */
    public Drawable getStartIconDrawable() {
        return this.startIconView.getDrawable();
    }

    /* access modifiers changed from: package-private */
    public void setStartIconOnClickListener(View.OnClickListener startIconOnClickListener) {
        IconHelper.setIconOnClickListener(this.startIconView, startIconOnClickListener, this.startIconOnLongClickListener);
    }

    /* access modifiers changed from: package-private */
    public void setStartIconOnLongClickListener(View.OnLongClickListener startIconOnLongClickListener2) {
        this.startIconOnLongClickListener = startIconOnLongClickListener2;
        IconHelper.setIconOnLongClickListener(this.startIconView, startIconOnLongClickListener2);
    }

    /* access modifiers changed from: package-private */
    public void setStartIconVisible(boolean visible) {
        if (isStartIconVisible() != visible) {
            this.startIconView.setVisibility(visible ? 0 : 8);
            updatePrefixTextViewPadding();
            updateVisibility();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isStartIconVisible() {
        return this.startIconView.getVisibility() == 0;
    }

    /* access modifiers changed from: package-private */
    public void refreshStartIconDrawableState() {
        IconHelper.refreshIconDrawableState(this.textInputLayout, this.startIconView, this.startIconTintList);
    }

    /* access modifiers changed from: package-private */
    public void setStartIconCheckable(boolean startIconCheckable) {
        this.startIconView.setCheckable(startIconCheckable);
    }

    /* access modifiers changed from: package-private */
    public boolean isStartIconCheckable() {
        return this.startIconView.isCheckable();
    }

    /* access modifiers changed from: package-private */
    public void setStartIconContentDescription(CharSequence startIconContentDescription) {
        if (getStartIconContentDescription() != startIconContentDescription) {
            this.startIconView.setContentDescription(startIconContentDescription);
        }
    }

    /* access modifiers changed from: package-private */
    public CharSequence getStartIconContentDescription() {
        return this.startIconView.getContentDescription();
    }

    /* access modifiers changed from: package-private */
    public void setStartIconTintList(ColorStateList startIconTintList2) {
        if (this.startIconTintList != startIconTintList2) {
            this.startIconTintList = startIconTintList2;
            IconHelper.applyIconTint(this.textInputLayout, this.startIconView, startIconTintList2, this.startIconTintMode);
        }
    }

    /* access modifiers changed from: package-private */
    public void setStartIconTintMode(PorterDuff.Mode startIconTintMode2) {
        if (this.startIconTintMode != startIconTintMode2) {
            this.startIconTintMode = startIconTintMode2;
            IconHelper.applyIconTint(this.textInputLayout, this.startIconView, this.startIconTintList, this.startIconTintMode);
        }
    }

    /* access modifiers changed from: package-private */
    public void setStartIconMinSize(int iconSize) {
        if (iconSize < 0) {
            throw new IllegalArgumentException("startIconSize cannot be less than 0");
        } else if (iconSize != this.startIconMinSize) {
            this.startIconMinSize = iconSize;
            IconHelper.setIconMinSize(this.startIconView, iconSize);
        }
    }

    /* access modifiers changed from: package-private */
    public int getStartIconMinSize() {
        return this.startIconMinSize;
    }

    /* access modifiers changed from: package-private */
    public void setStartIconScaleType(ImageView.ScaleType startIconScaleType2) {
        this.startIconScaleType = startIconScaleType2;
        IconHelper.setIconScaleType(this.startIconView, startIconScaleType2);
    }

    /* access modifiers changed from: package-private */
    public ImageView.ScaleType getStartIconScaleType() {
        return this.startIconScaleType;
    }

    /* access modifiers changed from: package-private */
    public void setupAccessibilityNodeInfo(AccessibilityNodeInfoCompat info) {
        if (this.prefixTextView.getVisibility() == 0) {
            info.setLabelFor(this.prefixTextView);
            info.setTraversalAfter(this.prefixTextView);
            return;
        }
        info.setTraversalAfter(this.startIconView);
    }

    /* access modifiers changed from: package-private */
    public void updatePrefixTextViewPadding() {
        EditText editText = this.textInputLayout.editText;
        if (editText != null) {
            this.prefixTextView.setPaddingRelative(isStartIconVisible() ? 0 : editText.getPaddingStart(), editText.getCompoundPaddingTop(), getContext().getResources().getDimensionPixelSize(R.dimen.material_input_text_to_prefix_suffix_padding), editText.getCompoundPaddingBottom());
        }
    }

    /* access modifiers changed from: package-private */
    public int getPrefixTextStartOffset() {
        int startIconOffset;
        if (isStartIconVisible()) {
            startIconOffset = this.startIconView.getMeasuredWidth() + ((ViewGroup.MarginLayoutParams) this.startIconView.getLayoutParams()).getMarginEnd();
        } else {
            startIconOffset = 0;
        }
        return getPaddingStart() + this.prefixTextView.getPaddingStart() + startIconOffset;
    }

    /* access modifiers changed from: package-private */
    public void onHintStateChanged(boolean hintExpanded2) {
        this.hintExpanded = hintExpanded2;
        updateVisibility();
    }

    private void updateVisibility() {
        int i = 8;
        int prefixTextVisibility = (this.prefixText == null || this.hintExpanded) ? 8 : 0;
        if (this.startIconView.getVisibility() == 0 || prefixTextVisibility == 0) {
            i = 0;
        }
        setVisibility(i);
        this.prefixTextView.setVisibility(prefixTextVisibility);
        this.textInputLayout.updateDummyDrawables();
    }
}
