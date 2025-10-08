package com.google.android.material.button;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.customview.view.AbsSavedState;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.shape.StateListShapeAppearanceModel;
import com.google.android.material.shape.StateListSizeChange;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class MaterialButton extends AppCompatButton implements Checkable, Shapeable {
    private static final int[] CHECKABLE_STATE_SET = {16842911};
    private static final int[] CHECKED_STATE_SET = {16842912};
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_Button;
    public static final int ICON_GRAVITY_END = 3;
    public static final int ICON_GRAVITY_START = 1;
    public static final int ICON_GRAVITY_TEXT_END = 4;
    public static final int ICON_GRAVITY_TEXT_START = 2;
    public static final int ICON_GRAVITY_TEXT_TOP = 32;
    public static final int ICON_GRAVITY_TOP = 16;
    private static final String LOG_TAG = "MaterialButton";
    private static final int MATERIAL_SIZE_OVERLAY_ATTR = R.attr.materialSizeOverlay;
    private static final float OPTICAL_CENTER_RATIO = 0.11f;
    private static final int UNSET = -1;
    private static final FloatPropertyCompat<MaterialButton> WIDTH_INCREASE = new FloatPropertyCompat<MaterialButton>("widthIncrease") {
        public float getValue(MaterialButton button) {
            return button.getDisplayedWidthIncrease();
        }

        public void setValue(MaterialButton button, float value) {
            button.setDisplayedWidthIncrease(value);
        }
    };
    private String accessibilityClassName;
    int allowedWidthDecrease;
    private boolean broadcasting;
    private boolean checked;
    private float displayedWidthDecrease;
    private float displayedWidthIncrease;
    private Drawable icon;
    private int iconGravity;
    private int iconLeft;
    private int iconPadding;
    private int iconSize;
    private ColorStateList iconTint;
    private PorterDuff.Mode iconTintMode;
    private int iconTop;
    private boolean isInHorizontalButtonGroup;
    private final MaterialButtonHelper materialButtonHelper;
    private final LinkedHashSet<OnCheckedChangeListener> onCheckedChangeListeners;
    private OnPressedChangeListener onPressedChangeListenerInternal;
    private boolean opticalCenterEnabled;
    private int opticalCenterShift;
    private int orientation;
    private LinearLayout.LayoutParams originalLayoutParams;
    private int originalPaddingEnd;
    private int originalPaddingStart;
    private float originalWidth;
    StateListSizeChange sizeChange;
    int widthChangeMax;
    private SpringAnimation widthIncreaseSpringAnimation;

    @Retention(RetentionPolicy.SOURCE)
    public @interface IconGravity {
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(MaterialButton materialButton, boolean z);
    }

    interface OnPressedChangeListener {
        void onPressedChanged(MaterialButton materialButton, boolean z);
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<MaterialButton> {
        private int mIconPaddingId;
        private boolean mPropertiesMapped = false;

        public void mapProperties(PropertyMapper propertyMapper) {
            this.mIconPaddingId = propertyMapper.mapInt("iconPadding", R.attr.iconPadding);
            this.mPropertiesMapped = true;
        }

        public void readProperties(MaterialButton materialButton, PropertyReader propertyReader) {
            if (this.mPropertiesMapped) {
                propertyReader.readInt(this.mIconPaddingId, materialButton.getIconPadding());
                return;
            }
            throw new InspectionCompanion.UninitializedPropertyMapException();
        }
    }

    public MaterialButton(Context context) {
        this(context, (AttributeSet) null);
    }

    public MaterialButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.materialButtonStyle);
    }

    public MaterialButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES, new int[]{MATERIAL_SIZE_OVERLAY_ATTR}), attrs, defStyleAttr);
        ShapeAppearanceModel shapeAppearanceModel;
        this.onCheckedChangeListeners = new LinkedHashSet<>();
        boolean z = false;
        this.checked = false;
        this.broadcasting = false;
        this.orientation = -1;
        this.originalWidth = -1.0f;
        this.originalPaddingStart = -1;
        this.originalPaddingEnd = -1;
        this.allowedWidthDecrease = -1;
        Context context2 = getContext();
        AttributeSet attrs2 = attrs;
        int defStyleAttr2 = defStyleAttr;
        TypedArray attributes = ThemeEnforcement.obtainStyledAttributes(context2, attrs2, R.styleable.MaterialButton, defStyleAttr2, DEF_STYLE_RES, new int[0]);
        this.iconPadding = attributes.getDimensionPixelSize(R.styleable.MaterialButton_iconPadding, 0);
        this.iconTintMode = ViewUtils.parseTintMode(attributes.getInt(R.styleable.MaterialButton_iconTintMode, -1), PorterDuff.Mode.SRC_IN);
        this.iconTint = MaterialResources.getColorStateList(getContext(), attributes, R.styleable.MaterialButton_iconTint);
        this.icon = MaterialResources.getDrawable(getContext(), attributes, R.styleable.MaterialButton_icon);
        this.iconGravity = attributes.getInteger(R.styleable.MaterialButton_iconGravity, 1);
        this.iconSize = attributes.getDimensionPixelSize(R.styleable.MaterialButton_iconSize, 0);
        StateListShapeAppearanceModel stateListShapeAppearanceModel = StateListShapeAppearanceModel.create(context2, attributes, R.styleable.MaterialButton_shapeAppearance);
        if (stateListShapeAppearanceModel != null) {
            shapeAppearanceModel = stateListShapeAppearanceModel.getDefaultShape(true);
        } else {
            shapeAppearanceModel = ShapeAppearanceModel.builder(context2, attrs2, defStyleAttr2, DEF_STYLE_RES).build();
        }
        boolean opticalCenterEnabled2 = attributes.getBoolean(R.styleable.MaterialButton_opticalCenterEnabled, false);
        this.materialButtonHelper = new MaterialButtonHelper(this, shapeAppearanceModel);
        this.materialButtonHelper.loadFromAttributes(attributes);
        setCheckedInternal(attributes.getBoolean(R.styleable.MaterialButton_android_checked, false));
        if (stateListShapeAppearanceModel != null) {
            this.materialButtonHelper.setCornerSpringForce(createSpringForce());
            this.materialButtonHelper.setStateListShapeAppearanceModel(stateListShapeAppearanceModel);
        }
        setOpticalCenterEnabled(opticalCenterEnabled2);
        attributes.recycle();
        setCompoundDrawablePadding(this.iconPadding);
        updateIcon(this.icon != null ? true : z);
    }

    private void initializeSizeAnimation() {
        this.widthIncreaseSpringAnimation = new SpringAnimation(this, WIDTH_INCREASE);
        this.widthIncreaseSpringAnimation.setSpring(createSpringForce());
    }

    private SpringForce createSpringForce() {
        return MotionUtils.resolveThemeSpringForce(getContext(), R.attr.motionSpringFastSpatial, R.style.Motion_Material3_Spring_Standard_Fast_Spatial);
    }

    /* access modifiers changed from: package-private */
    public String getA11yClassName() {
        if (!TextUtils.isEmpty(this.accessibilityClassName)) {
            return this.accessibilityClassName;
        }
        return (isCheckable() ? CompoundButton.class : Button.class).getName();
    }

    public void setA11yClassName(String className) {
        this.accessibilityClassName = className;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(getA11yClassName());
        info.setCheckable(isCheckable());
        info.setChecked(isChecked());
        info.setClickable(isClickable());
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(getA11yClassName());
        accessibilityEvent.setChecked(isChecked());
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.checked = this.checked;
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        setChecked(savedState.checked);
    }

    public void setSupportBackgroundTintList(ColorStateList tint) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setSupportBackgroundTintList(tint);
        } else {
            super.setSupportBackgroundTintList(tint);
        }
    }

    public ColorStateList getSupportBackgroundTintList() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.getSupportBackgroundTintList();
        }
        return super.getSupportBackgroundTintList();
    }

    public void setSupportBackgroundTintMode(PorterDuff.Mode tintMode) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setSupportBackgroundTintMode(tintMode);
        } else {
            super.setSupportBackgroundTintMode(tintMode);
        }
    }

    public PorterDuff.Mode getSupportBackgroundTintMode() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.getSupportBackgroundTintMode();
        }
        return super.getSupportBackgroundTintMode();
    }

    public void setBackgroundTintList(ColorStateList tintList) {
        setSupportBackgroundTintList(tintList);
    }

    public ColorStateList getBackgroundTintList() {
        return getSupportBackgroundTintList();
    }

    public void setBackgroundTintMode(PorterDuff.Mode tintMode) {
        setSupportBackgroundTintMode(tintMode);
    }

    public PorterDuff.Mode getBackgroundTintMode() {
        return getSupportBackgroundTintMode();
    }

    public void setBackgroundColor(int color) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setBackgroundColor(color);
        } else {
            super.setBackgroundColor(color);
        }
    }

    public void setBackground(Drawable background) {
        setBackgroundDrawable(background);
    }

    public void setBackgroundResource(int backgroundResourceId) {
        Drawable background = null;
        if (backgroundResourceId != 0) {
            background = AppCompatResources.getDrawable(getContext(), backgroundResourceId);
        }
        setBackgroundDrawable(background);
    }

    public void setBackgroundDrawable(Drawable background) {
        if (!isUsingOriginalBackground()) {
            super.setBackgroundDrawable(background);
        } else if (background != getBackground()) {
            Log.w(LOG_TAG, "MaterialButton manages its own background to control elevation, shape, color and states. Consider using backgroundTint, shapeAppearance and other attributes where available. A custom background will ignore these attributes and you should consider handling interaction states such as pressed, focused and disabled");
            this.materialButtonHelper.setBackgroundOverwritten();
            super.setBackgroundDrawable(background);
        } else {
            getBackground().setState(background.getState());
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int localIconSizeAndPadding;
        super.onLayout(changed, left, top, right, bottom);
        int i = bottom;
        int bottom2 = right;
        int right2 = top;
        int top2 = left;
        int left2 = changed;
        updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
        int curOrientation = getResources().getConfiguration().orientation;
        if (this.orientation != curOrientation) {
            this.orientation = curOrientation;
            this.originalWidth = -1.0f;
        }
        if (this.originalWidth == -1.0f) {
            this.originalWidth = (float) getMeasuredWidth();
            if (this.originalLayoutParams == null && (getParent() instanceof MaterialButtonGroup) && ((MaterialButtonGroup) getParent()).getButtonSizeChange() != null) {
                this.originalLayoutParams = (LinearLayout.LayoutParams) getLayoutParams();
                LinearLayout.LayoutParams newLayoutParams = new LinearLayout.LayoutParams(this.originalLayoutParams);
                newLayoutParams.width = (int) this.originalWidth;
                setLayoutParams(newLayoutParams);
            }
        }
        if (this.allowedWidthDecrease == -1) {
            if (this.icon == null) {
                localIconSizeAndPadding = 0;
            } else {
                localIconSizeAndPadding = getIconPadding() + (this.iconSize == 0 ? this.icon.getIntrinsicWidth() : this.iconSize);
            }
            this.allowedWidthDecrease = (getMeasuredWidth() - getTextLayoutWidth()) - localIconSizeAndPadding;
        }
        if (this.originalPaddingStart == -1) {
            this.originalPaddingStart = getPaddingStart();
        }
        if (this.originalPaddingEnd == -1) {
            this.originalPaddingEnd = getPaddingEnd();
        }
        this.isInHorizontalButtonGroup = isInHorizontalButtonGroup();
    }

    /* access modifiers changed from: package-private */
    public void recoverOriginalLayoutParams() {
        if (this.originalLayoutParams != null) {
            setLayoutParams(this.originalLayoutParams);
            this.originalLayoutParams = null;
            this.originalWidth = -1.0f;
        }
    }

    public void setWidth(int pixels) {
        this.originalWidth = -1.0f;
        super.setWidth(pixels);
    }

    /* access modifiers changed from: protected */
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        super.onTextChanged(charSequence, i, i1, i2);
        updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isUsingOriginalBackground()) {
            MaterialShapeUtils.setParentAbsoluteElevation(this, this.materialButtonHelper.getMaterialShapeDrawable());
        }
    }

    public void setElevation(float elevation) {
        super.setElevation(elevation);
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.getMaterialShapeDrawable().setElevation(elevation);
        }
    }

    public void refreshDrawableState() {
        super.refreshDrawableState();
        if (this.icon != null) {
            if (this.icon.setState(getDrawableState())) {
                invalidate();
            }
        }
    }

    public void setTextAlignment(int textAlignment) {
        super.setTextAlignment(textAlignment);
        updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
    }

    private Layout.Alignment getGravityTextAlignment() {
        switch (getGravity() & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) {
            case 1:
                return Layout.Alignment.ALIGN_CENTER;
            case 5:
            case GravityCompat.END /*8388613*/:
                return Layout.Alignment.ALIGN_OPPOSITE;
            default:
                return Layout.Alignment.ALIGN_NORMAL;
        }
    }

    private Layout.Alignment getActualTextAlignment() {
        switch (getTextAlignment()) {
            case 1:
                return getGravityTextAlignment();
            case 3:
            case 6:
                return Layout.Alignment.ALIGN_OPPOSITE;
            case 4:
                return Layout.Alignment.ALIGN_CENTER;
            default:
                return Layout.Alignment.ALIGN_NORMAL;
        }
    }

    private void updateIconPosition(int buttonWidth, int buttonHeight) {
        if (this.icon != null && getLayout() != null) {
            if (isIconStart() || isIconEnd()) {
                this.iconTop = 0;
                Layout.Alignment textAlignment = getActualTextAlignment();
                boolean z = true;
                if (this.iconGravity == 1 || this.iconGravity == 3 || ((this.iconGravity == 2 && textAlignment == Layout.Alignment.ALIGN_NORMAL) || (this.iconGravity == 4 && textAlignment == Layout.Alignment.ALIGN_OPPOSITE))) {
                    this.iconLeft = 0;
                    updateIcon(false);
                    return;
                }
                int availableWidth = ((((buttonWidth - getTextLayoutWidth()) - getPaddingEnd()) - (this.iconSize == 0 ? this.icon.getIntrinsicWidth() : this.iconSize)) - this.iconPadding) - getPaddingStart();
                int newIconLeft = textAlignment == Layout.Alignment.ALIGN_CENTER ? availableWidth / 2 : availableWidth;
                boolean isLayoutRTL = isLayoutRTL();
                if (this.iconGravity != 4) {
                    z = false;
                }
                if (isLayoutRTL != z) {
                    newIconLeft = -newIconLeft;
                }
                if (this.iconLeft != newIconLeft) {
                    this.iconLeft = newIconLeft;
                    updateIcon(false);
                }
            } else if (isIconTop()) {
                this.iconLeft = 0;
                if (this.iconGravity == 16) {
                    this.iconTop = 0;
                    updateIcon(false);
                    return;
                }
                int newIconTop = Math.max(0, (((((buttonHeight - getTextHeight()) - getPaddingTop()) - (this.iconSize == 0 ? this.icon.getIntrinsicHeight() : this.iconSize)) - this.iconPadding) - getPaddingBottom()) / 2);
                if (this.iconTop != newIconTop) {
                    this.iconTop = newIconTop;
                    updateIcon(false);
                }
            }
        }
    }

    private int getTextLayoutWidth() {
        float maxWidth = 0.0f;
        int lineCount = getLineCount();
        for (int line = 0; line < lineCount; line++) {
            maxWidth = Math.max(maxWidth, getLayout().getLineWidth(line));
        }
        return (int) Math.ceil((double) maxWidth);
    }

    private int getTextHeight() {
        if (getLineCount() > 1) {
            return getLayout().getHeight();
        }
        Paint textPaint = getPaint();
        String buttonText = getText().toString();
        if (getTransformationMethod() != null) {
            buttonText = getTransformationMethod().getTransformation(buttonText, this).toString();
        }
        Rect bounds = new Rect();
        textPaint.getTextBounds(buttonText, 0, buttonText.length(), bounds);
        return Math.min(bounds.height(), getLayout().getHeight());
    }

    private boolean isLayoutRTL() {
        return getLayoutDirection() == 1;
    }

    /* access modifiers changed from: package-private */
    public void setInternalBackground(Drawable background) {
        super.setBackgroundDrawable(background);
    }

    public void setIconPadding(int iconPadding2) {
        if (this.iconPadding != iconPadding2) {
            this.iconPadding = iconPadding2;
            setCompoundDrawablePadding(iconPadding2);
        }
    }

    public int getIconPadding() {
        return this.iconPadding;
    }

    public void setIconSize(int iconSize2) {
        if (iconSize2 < 0) {
            throw new IllegalArgumentException("iconSize cannot be less than 0");
        } else if (this.iconSize != iconSize2) {
            this.iconSize = iconSize2;
            updateIcon(true);
        }
    }

    public int getIconSize() {
        return this.iconSize;
    }

    public void setIcon(Drawable icon2) {
        if (this.icon != icon2) {
            this.icon = icon2;
            updateIcon(true);
            updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    public void setIconResource(int iconResourceId) {
        Drawable icon2 = null;
        if (iconResourceId != 0) {
            icon2 = AppCompatResources.getDrawable(getContext(), iconResourceId);
        }
        setIcon(icon2);
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public void setIconTint(ColorStateList iconTint2) {
        if (this.iconTint != iconTint2) {
            this.iconTint = iconTint2;
            updateIcon(false);
        }
    }

    public void setIconTintResource(int iconTintResourceId) {
        setIconTint(AppCompatResources.getColorStateList(getContext(), iconTintResourceId));
    }

    public ColorStateList getIconTint() {
        return this.iconTint;
    }

    public void setIconTintMode(PorterDuff.Mode iconTintMode2) {
        if (this.iconTintMode != iconTintMode2) {
            this.iconTintMode = iconTintMode2;
            updateIcon(false);
        }
    }

    public PorterDuff.Mode getIconTintMode() {
        return this.iconTintMode;
    }

    private void updateIcon(boolean needsIconReset) {
        boolean hasIconChanged = true;
        if (this.icon != null) {
            this.icon = DrawableCompat.wrap(this.icon).mutate();
            this.icon.setTintList(this.iconTint);
            if (this.iconTintMode != null) {
                this.icon.setTintMode(this.iconTintMode);
            }
            this.icon.setBounds(this.iconLeft, this.iconTop, this.iconLeft + (this.iconSize != 0 ? this.iconSize : this.icon.getIntrinsicWidth()), this.iconTop + (this.iconSize != 0 ? this.iconSize : this.icon.getIntrinsicHeight()));
            this.icon.setVisible(true, needsIconReset);
        }
        if (needsIconReset) {
            resetIconDrawable();
            return;
        }
        Drawable[] existingDrawables = getCompoundDrawablesRelative();
        Drawable drawableStart = existingDrawables[0];
        Drawable drawableTop = existingDrawables[1];
        Drawable drawableEnd = existingDrawables[2];
        if ((!isIconStart() || drawableStart == this.icon) && ((!isIconEnd() || drawableEnd == this.icon) && (!isIconTop() || drawableTop == this.icon))) {
            hasIconChanged = false;
        }
        if (hasIconChanged) {
            resetIconDrawable();
        }
    }

    private void resetIconDrawable() {
        if (isIconStart()) {
            setCompoundDrawablesRelative(this.icon, (Drawable) null, (Drawable) null, (Drawable) null);
        } else if (isIconEnd()) {
            setCompoundDrawablesRelative((Drawable) null, (Drawable) null, this.icon, (Drawable) null);
        } else if (isIconTop()) {
            setCompoundDrawablesRelative((Drawable) null, this.icon, (Drawable) null, (Drawable) null);
        }
    }

    private boolean isIconStart() {
        return this.iconGravity == 1 || this.iconGravity == 2;
    }

    private boolean isIconEnd() {
        return this.iconGravity == 3 || this.iconGravity == 4;
    }

    private boolean isIconTop() {
        return this.iconGravity == 16 || this.iconGravity == 32;
    }

    public void setRippleColor(ColorStateList rippleColor) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setRippleColor(rippleColor);
        }
    }

    public void setRippleColorResource(int rippleColorResourceId) {
        if (isUsingOriginalBackground()) {
            setRippleColor(AppCompatResources.getColorStateList(getContext(), rippleColorResourceId));
        }
    }

    public ColorStateList getRippleColor() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.getRippleColor();
        }
        return null;
    }

    public void setStrokeColor(ColorStateList strokeColor) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setStrokeColor(strokeColor);
        }
    }

    public void setStrokeColorResource(int strokeColorResourceId) {
        if (isUsingOriginalBackground()) {
            setStrokeColor(AppCompatResources.getColorStateList(getContext(), strokeColorResourceId));
        }
    }

    public ColorStateList getStrokeColor() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.getStrokeColor();
        }
        return null;
    }

    public void setStrokeWidth(int strokeWidth) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setStrokeWidth(strokeWidth);
        }
    }

    public void setStrokeWidthResource(int strokeWidthResourceId) {
        if (isUsingOriginalBackground()) {
            setStrokeWidth(getResources().getDimensionPixelSize(strokeWidthResourceId));
        }
    }

    public int getStrokeWidth() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.getStrokeWidth();
        }
        return 0;
    }

    public void setCornerRadius(int cornerRadius) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setCornerRadius(cornerRadius);
        }
    }

    public void setCornerRadiusResource(int cornerRadiusResourceId) {
        if (isUsingOriginalBackground()) {
            setCornerRadius(getResources().getDimensionPixelSize(cornerRadiusResourceId));
        }
    }

    public int getCornerRadius() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.getCornerRadius();
        }
        return 0;
    }

    public int getIconGravity() {
        return this.iconGravity;
    }

    public void setIconGravity(int iconGravity2) {
        if (this.iconGravity != iconGravity2) {
            this.iconGravity = iconGravity2;
            updateIconPosition(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    public void setInsetBottom(int insetBottom) {
        this.materialButtonHelper.setInsetBottom(insetBottom);
    }

    public int getInsetBottom() {
        return this.materialButtonHelper.getInsetBottom();
    }

    public void setInsetTop(int insetTop) {
        this.materialButtonHelper.setInsetTop(insetTop);
    }

    public int getInsetTop() {
        return this.materialButtonHelper.getInsetTop();
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 2);
        if (isCheckable()) {
            mergeDrawableStates(drawableState, CHECKABLE_STATE_SET);
        }
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    public void addOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.onCheckedChangeListeners.add(listener);
    }

    public void removeOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.onCheckedChangeListeners.remove(listener);
    }

    public void clearOnCheckedChangeListeners() {
        this.onCheckedChangeListeners.clear();
    }

    public void setChecked(boolean checked2) {
        setCheckedInternal(checked2);
    }

    private void setCheckedInternal(boolean checked2) {
        if (isCheckable() && this.checked != checked2) {
            this.checked = checked2;
            refreshDrawableState();
            if (getParent() instanceof MaterialButtonToggleGroup) {
                ((MaterialButtonToggleGroup) getParent()).onButtonCheckedStateChanged(this, this.checked);
            }
            if (!this.broadcasting) {
                this.broadcasting = true;
                Iterator it = this.onCheckedChangeListeners.iterator();
                while (it.hasNext()) {
                    ((OnCheckedChangeListener) it.next()).onCheckedChanged(this, this.checked);
                }
                this.broadcasting = false;
            }
        }
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void toggle() {
        setChecked(!this.checked);
    }

    public boolean performClick() {
        if (isEnabled() && this.materialButtonHelper.isToggleCheckedStateOnClick()) {
            toggle();
        }
        return super.performClick();
    }

    public boolean isToggleCheckedStateOnClick() {
        return this.materialButtonHelper.isToggleCheckedStateOnClick();
    }

    public void setToggleCheckedStateOnClick(boolean toggleCheckedStateOnClick) {
        this.materialButtonHelper.setToggleCheckedStateOnClick(toggleCheckedStateOnClick);
    }

    public boolean isCheckable() {
        return this.materialButtonHelper != null && this.materialButtonHelper.isCheckable();
    }

    public void setCheckable(boolean checkable) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setCheckable(checkable);
        }
    }

    public void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setShapeAppearanceModel(shapeAppearanceModel);
            return;
        }
        throw new IllegalStateException("Attempted to set ShapeAppearanceModel on a MaterialButton which has an overwritten background.");
    }

    public ShapeAppearanceModel getShapeAppearanceModel() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.getShapeAppearanceModel();
        }
        throw new IllegalStateException("Attempted to get ShapeAppearanceModel from a MaterialButton which has an overwritten background.");
    }

    public void setStateListShapeAppearanceModel(StateListShapeAppearanceModel stateListShapeAppearanceModel) {
        if (isUsingOriginalBackground()) {
            if (this.materialButtonHelper.getCornerSpringForce() == null && stateListShapeAppearanceModel.isStateful()) {
                this.materialButtonHelper.setCornerSpringForce(createSpringForce());
            }
            this.materialButtonHelper.setStateListShapeAppearanceModel(stateListShapeAppearanceModel);
            return;
        }
        throw new IllegalStateException("Attempted to set StateListShapeAppearanceModel on a MaterialButton which has an overwritten background.");
    }

    public StateListShapeAppearanceModel getStateListShapeAppearanceModel() {
        if (isUsingOriginalBackground()) {
            return this.materialButtonHelper.getStateListShapeAppearanceModel();
        }
        throw new IllegalStateException("Attempted to get StateListShapeAppearanceModel from a MaterialButton which has an overwritten background.");
    }

    public void setCornerSpringForce(SpringForce springForce) {
        this.materialButtonHelper.setCornerSpringForce(springForce);
    }

    public SpringForce getCornerSpringForce() {
        return this.materialButtonHelper.getCornerSpringForce();
    }

    /* access modifiers changed from: package-private */
    public void setOnPressedChangeListenerInternal(OnPressedChangeListener listener) {
        this.onPressedChangeListenerInternal = listener;
    }

    public void setPressed(boolean pressed) {
        if (this.onPressedChangeListenerInternal != null) {
            this.onPressedChangeListenerInternal.onPressedChanged(this, pressed);
        }
        super.setPressed(pressed);
        maybeAnimateSize(false);
    }

    private boolean isUsingOriginalBackground() {
        return this.materialButtonHelper != null && !this.materialButtonHelper.isBackgroundOverwritten();
    }

    /* access modifiers changed from: package-private */
    public void setShouldDrawSurfaceColorStroke(boolean shouldDrawSurfaceColorStroke) {
        if (isUsingOriginalBackground()) {
            this.materialButtonHelper.setShouldDrawSurfaceColorStroke(shouldDrawSurfaceColorStroke);
        }
    }

    private void maybeAnimateSize(boolean skipAnimation) {
        if (this.sizeChange != null) {
            if (this.widthIncreaseSpringAnimation == null) {
                initializeSizeAnimation();
            }
            if (this.isInHorizontalButtonGroup) {
                this.widthIncreaseSpringAnimation.animateToFinalPosition((float) Math.min(this.widthChangeMax, this.sizeChange.getSizeChangeForState(getDrawableState()).widthChange.getChange(getWidth())));
                if (skipAnimation) {
                    this.widthIncreaseSpringAnimation.skipToEnd();
                }
            }
        }
    }

    private boolean isInHorizontalButtonGroup() {
        return (getParent() instanceof MaterialButtonGroup) && ((MaterialButtonGroup) getParent()).getOrientation() == 0;
    }

    /* access modifiers changed from: package-private */
    public void setSizeChange(StateListSizeChange sizeChange2) {
        if (this.sizeChange != sizeChange2) {
            this.sizeChange = sizeChange2;
            maybeAnimateSize(true);
        }
    }

    /* access modifiers changed from: package-private */
    public void setWidthChangeMax(int widthChangeMax2) {
        if (this.widthChangeMax != widthChangeMax2) {
            this.widthChangeMax = widthChangeMax2;
            maybeAnimateSize(true);
        }
    }

    /* access modifiers changed from: package-private */
    public int getAllowedWidthDecrease() {
        return this.allowedWidthDecrease;
    }

    /* access modifiers changed from: private */
    public float getDisplayedWidthIncrease() {
        return this.displayedWidthIncrease;
    }

    /* access modifiers changed from: private */
    public void setDisplayedWidthIncrease(float widthIncrease) {
        if (this.displayedWidthIncrease != widthIncrease) {
            this.displayedWidthIncrease = widthIncrease;
            updatePaddingsAndSizeForWidthAnimation();
            invalidate();
            if (getParent() instanceof MaterialButtonGroup) {
                ((MaterialButtonGroup) getParent()).onButtonWidthChanged(this, (int) this.displayedWidthIncrease);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setDisplayedWidthDecrease(int widthDecrease) {
        this.displayedWidthDecrease = (float) Math.min(widthDecrease, this.allowedWidthDecrease);
        updatePaddingsAndSizeForWidthAnimation();
        invalidate();
    }

    public void setOpticalCenterEnabled(boolean opticalCenterEnabled2) {
        if (this.opticalCenterEnabled != opticalCenterEnabled2) {
            this.opticalCenterEnabled = opticalCenterEnabled2;
            if (opticalCenterEnabled2) {
                this.materialButtonHelper.setCornerSizeChangeListener(new MaterialButton$$ExternalSyntheticLambda0(this));
            } else {
                this.materialButtonHelper.setCornerSizeChangeListener((MaterialShapeDrawable.OnCornerSizeChangeListener) null);
            }
            post(new MaterialButton$$ExternalSyntheticLambda1(this));
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$setOpticalCenterEnabled$0$com-google-android-material-button-MaterialButton  reason: not valid java name */
    public /* synthetic */ void m1636lambda$setOpticalCenterEnabled$0$comgoogleandroidmaterialbuttonMaterialButton(float diffX) {
        int opticalCenterShift2 = (int) (OPTICAL_CENTER_RATIO * diffX);
        if (this.opticalCenterShift != opticalCenterShift2) {
            this.opticalCenterShift = opticalCenterShift2;
            updatePaddingsAndSizeForWidthAnimation();
            invalidate();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$setOpticalCenterEnabled$1$com-google-android-material-button-MaterialButton  reason: not valid java name */
    public /* synthetic */ void m1637lambda$setOpticalCenterEnabled$1$comgoogleandroidmaterialbuttonMaterialButton() {
        this.opticalCenterShift = getOpticalCenterShift();
        updatePaddingsAndSizeForWidthAnimation();
        invalidate();
    }

    public boolean isOpticalCenterEnabled() {
        return this.opticalCenterEnabled;
    }

    private void updatePaddingsAndSizeForWidthAnimation() {
        int widthChange = (int) (this.displayedWidthIncrease - this.displayedWidthDecrease);
        int paddingStartChange = (widthChange / 2) + this.opticalCenterShift;
        getLayoutParams().width = (int) (this.originalWidth + ((float) widthChange));
        setPaddingRelative(this.originalPaddingStart + paddingStartChange, getPaddingTop(), (this.originalPaddingEnd + widthChange) - paddingStartChange, getPaddingBottom());
    }

    private int getOpticalCenterShift() {
        MaterialShapeDrawable materialShapeDrawable;
        if (!this.opticalCenterEnabled || !this.isInHorizontalButtonGroup || (materialShapeDrawable = this.materialButtonHelper.getMaterialShapeDrawable()) == null) {
            return 0;
        }
        return (int) (materialShapeDrawable.getCornerSizeDiffX() * OPTICAL_CENTER_RATIO);
    }

    static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
            public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in, (ClassLoader) null);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        boolean checked;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            if (loader == null) {
                ClassLoader loader2 = getClass().getClassLoader();
            }
            readFromParcel(source);
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.checked ? 1 : 0);
        }

        private void readFromParcel(Parcel in) {
            boolean z = true;
            if (in.readInt() != 1) {
                z = false;
            }
            this.checked = z;
        }
    }
}
