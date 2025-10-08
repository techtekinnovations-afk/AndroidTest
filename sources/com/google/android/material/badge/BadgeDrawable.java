package com.google.android.material.badge;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.badge.BadgeState;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.Locale;

public class BadgeDrawable extends Drawable implements TextDrawableHelper.TextDrawableDelegate {
    public static final int BADGE_CONTENT_NOT_TRUNCATED = -2;
    public static final int BADGE_FIXED_EDGE_END = 1;
    public static final int BADGE_FIXED_EDGE_START = 0;
    static final int BADGE_RADIUS_NOT_SPECIFIED = -1;
    @Deprecated
    public static final int BOTTOM_END = 8388693;
    @Deprecated
    public static final int BOTTOM_START = 8388691;
    static final String DEFAULT_EXCEED_MAX_BADGE_NUMBER_SUFFIX = "+";
    static final String DEFAULT_EXCEED_MAX_BADGE_TEXT_SUFFIX = "…";
    private static final int DEFAULT_STYLE = R.style.Widget_MaterialComponents_Badge;
    private static final int DEFAULT_THEME_ATTR = R.attr.badgeStyle;
    private static final float FONT_SCALE_THRESHOLD = 0.3f;
    static final int OFFSET_ALIGNMENT_MODE_EDGE = 0;
    static final int OFFSET_ALIGNMENT_MODE_LEGACY = 1;
    private static final String TAG = "Badge";
    public static final int TOP_END = 8388661;
    public static final int TOP_START = 8388659;
    private WeakReference<View> anchorViewRef;
    private final Rect badgeBounds = new Rect();
    private float badgeCenterX;
    private float badgeCenterY;
    private final WeakReference<Context> contextRef;
    private float cornerRadius;
    private WeakReference<FrameLayout> customBadgeParentRef;
    private float halfBadgeHeight;
    private float halfBadgeWidth;
    private int maxBadgeNumber;
    private final MaterialShapeDrawable shapeDrawable;
    private final BadgeState state;
    private final TextDrawableHelper textDrawableHelper = new TextDrawableHelper(this);

    @Retention(RetentionPolicy.SOURCE)
    public @interface BadgeGravity {
    }

    /* access modifiers changed from: package-private */
    public BadgeState.State getSavedState() {
        return this.state.getOverridingState();
    }

    static BadgeDrawable createFromSavedState(Context context, BadgeState.State savedState) {
        return new BadgeDrawable(context, 0, DEFAULT_THEME_ATTR, DEFAULT_STYLE, savedState);
    }

    public static BadgeDrawable create(Context context) {
        return new BadgeDrawable(context, 0, DEFAULT_THEME_ATTR, DEFAULT_STYLE, (BadgeState.State) null);
    }

    public static BadgeDrawable createFromResource(Context context, int id) {
        return new BadgeDrawable(context, id, DEFAULT_THEME_ATTR, DEFAULT_STYLE, (BadgeState.State) null);
    }

    public void setVisible(boolean visible) {
        this.state.setVisible(visible);
        onVisibilityUpdated();
    }

    private void onVisibilityUpdated() {
        setVisible(this.state.isVisible(), false);
    }

    public void setBadgeFixedEdge(int fixedEdge) {
        if (this.state.badgeFixedEdge != fixedEdge) {
            this.state.badgeFixedEdge = fixedEdge;
            updateCenterAndBounds();
        }
    }

    private void restoreState() {
        onBadgeShapeAppearanceUpdated();
        onBadgeTextAppearanceUpdated();
        onMaxBadgeLengthUpdated();
        onBadgeContentUpdated();
        onAlphaUpdated();
        onBackgroundColorUpdated();
        onBadgeTextColorUpdated();
        onBadgeGravityUpdated();
        updateCenterAndBounds();
        onVisibilityUpdated();
    }

    private BadgeDrawable(Context context, int badgeResId, int defStyleAttr, int defStyleRes, BadgeState.State savedState) {
        int i;
        int i2;
        this.contextRef = new WeakReference<>(context);
        ThemeEnforcement.checkMaterialTheme(context);
        this.textDrawableHelper.getTextPaint().setTextAlign(Paint.Align.CENTER);
        Context context2 = context;
        this.state = new BadgeState(context2, badgeResId, defStyleAttr, defStyleRes, savedState);
        if (hasBadgeContent()) {
            i = this.state.getBadgeWithTextShapeAppearanceResId();
        } else {
            i = this.state.getBadgeShapeAppearanceResId();
        }
        if (hasBadgeContent()) {
            i2 = this.state.getBadgeWithTextShapeAppearanceOverlayResId();
        } else {
            i2 = this.state.getBadgeShapeAppearanceOverlayResId();
        }
        this.shapeDrawable = new MaterialShapeDrawable(ShapeAppearanceModel.builder(context2, i, i2).build());
        restoreState();
    }

    @Deprecated
    public void updateBadgeCoordinates(View anchorView, ViewGroup customBadgeParent) {
        if (customBadgeParent instanceof FrameLayout) {
            updateBadgeCoordinates(anchorView, (FrameLayout) customBadgeParent);
            return;
        }
        throw new IllegalArgumentException("customBadgeParent must be a FrameLayout");
    }

    public void updateBadgeCoordinates(View anchorView) {
        updateBadgeCoordinates(anchorView, (FrameLayout) null);
    }

    public void updateBadgeCoordinates(View anchorView, FrameLayout customBadgeParent) {
        this.anchorViewRef = new WeakReference<>(anchorView);
        this.customBadgeParentRef = new WeakReference<>(customBadgeParent);
        updateAnchorParentToNotClip(anchorView);
        updateCenterAndBounds();
        invalidateSelf();
    }

    public FrameLayout getCustomBadgeParent() {
        if (this.customBadgeParentRef != null) {
            return (FrameLayout) this.customBadgeParentRef.get();
        }
        return null;
    }

    private static void updateAnchorParentToNotClip(View anchorView) {
        ViewGroup anchorViewParent = (ViewGroup) anchorView.getParent();
        anchorViewParent.setClipChildren(false);
        anchorViewParent.setClipToPadding(false);
    }

    public int getBackgroundColor() {
        return this.shapeDrawable.getFillColor().getDefaultColor();
    }

    public void setBackgroundColor(int backgroundColor) {
        this.state.setBackgroundColor(backgroundColor);
        onBackgroundColorUpdated();
    }

    private void onBackgroundColorUpdated() {
        ColorStateList backgroundColorStateList = ColorStateList.valueOf(this.state.getBackgroundColor());
        if (this.shapeDrawable.getFillColor() != backgroundColorStateList) {
            this.shapeDrawable.setFillColor(backgroundColorStateList);
            invalidateSelf();
        }
    }

    public int getBadgeTextColor() {
        return this.textDrawableHelper.getTextPaint().getColor();
    }

    public void setBadgeTextColor(int badgeTextColor) {
        if (this.textDrawableHelper.getTextPaint().getColor() != badgeTextColor) {
            this.state.setBadgeTextColor(badgeTextColor);
            onBadgeTextColorUpdated();
        }
    }

    private void onBadgeTextColorUpdated() {
        this.textDrawableHelper.getTextPaint().setColor(this.state.getBadgeTextColor());
        invalidateSelf();
    }

    public Locale getBadgeNumberLocale() {
        return this.state.getNumberLocale();
    }

    public void setBadgeNumberLocale(Locale locale) {
        if (!locale.equals(this.state.getNumberLocale())) {
            this.state.setNumberLocale(locale);
            invalidateSelf();
        }
    }

    public boolean hasNumber() {
        return !this.state.hasText() && this.state.hasNumber();
    }

    public int getNumber() {
        if (this.state.hasNumber()) {
            return this.state.getNumber();
        }
        return 0;
    }

    public void setNumber(int number) {
        int number2 = Math.max(0, number);
        if (this.state.getNumber() != number2) {
            this.state.setNumber(number2);
            onNumberUpdated();
        }
    }

    public void clearNumber() {
        if (this.state.hasNumber()) {
            this.state.clearNumber();
            onNumberUpdated();
        }
    }

    private void onNumberUpdated() {
        if (!hasText()) {
            onBadgeContentUpdated();
        }
    }

    public boolean hasText() {
        return this.state.hasText();
    }

    public String getText() {
        return this.state.getText();
    }

    public void setText(String text) {
        if (!TextUtils.equals(this.state.getText(), text)) {
            this.state.setText(text);
            onTextUpdated();
        }
    }

    public void clearText() {
        if (this.state.hasText()) {
            this.state.clearText();
            onTextUpdated();
        }
    }

    private void onTextUpdated() {
        onBadgeContentUpdated();
    }

    public int getMaxCharacterCount() {
        return this.state.getMaxCharacterCount();
    }

    public void setMaxCharacterCount(int maxCharacterCount) {
        if (this.state.getMaxCharacterCount() != maxCharacterCount) {
            this.state.setMaxCharacterCount(maxCharacterCount);
            onMaxBadgeLengthUpdated();
        }
    }

    public int getMaxNumber() {
        return this.state.getMaxNumber();
    }

    public void setMaxNumber(int maxNumber) {
        if (this.state.getMaxNumber() != maxNumber) {
            this.state.setMaxNumber(maxNumber);
            onMaxBadgeLengthUpdated();
        }
    }

    private void onMaxBadgeLengthUpdated() {
        updateMaxBadgeNumber();
        this.textDrawableHelper.setTextSizeDirty(true);
        updateCenterAndBounds();
        invalidateSelf();
    }

    public int getBadgeGravity() {
        return this.state.getBadgeGravity();
    }

    public void setBadgeGravity(int gravity) {
        if (gravity == 8388691 || gravity == 8388693) {
            Log.w(TAG, "Bottom badge gravities are deprecated; please use a top gravity instead.");
        }
        if (this.state.getBadgeGravity() != gravity) {
            this.state.setBadgeGravity(gravity);
            onBadgeGravityUpdated();
        }
    }

    private void onBadgeGravityUpdated() {
        if (this.anchorViewRef != null && this.anchorViewRef.get() != null) {
            updateBadgeCoordinates((View) this.anchorViewRef.get(), this.customBadgeParentRef != null ? (FrameLayout) this.customBadgeParentRef.get() : null);
        }
    }

    public boolean isStateful() {
        return false;
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    public int getAlpha() {
        return this.state.getAlpha();
    }

    public void setAlpha(int alpha) {
        this.state.setAlpha(alpha);
        onAlphaUpdated();
    }

    private void onAlphaUpdated() {
        this.textDrawableHelper.getTextPaint().setAlpha(getAlpha());
        invalidateSelf();
    }

    public int getOpacity() {
        return -3;
    }

    public int getIntrinsicHeight() {
        return this.badgeBounds.height();
    }

    public int getIntrinsicWidth() {
        return this.badgeBounds.width();
    }

    public void draw(Canvas canvas) {
        if (!getBounds().isEmpty() && getAlpha() != 0 && isVisible()) {
            this.shapeDrawable.draw(canvas);
            if (hasBadgeContent()) {
                drawBadgeContent(canvas);
            }
        }
    }

    public void onTextSizeChange() {
        invalidateSelf();
    }

    public boolean onStateChange(int[] state2) {
        return super.onStateChange(state2);
    }

    public void setContentDescriptionForText(CharSequence charSequence) {
        this.state.setContentDescriptionForText(charSequence);
    }

    public void setContentDescriptionNumberless(CharSequence charSequence) {
        this.state.setContentDescriptionNumberless(charSequence);
    }

    public void setContentDescriptionQuantityStringsResource(int stringsResource) {
        this.state.setContentDescriptionQuantityStringsResource(stringsResource);
    }

    public void setContentDescriptionExceedsMaxBadgeNumberStringResource(int stringsResource) {
        this.state.setContentDescriptionExceedsMaxBadgeNumberStringResource(stringsResource);
    }

    public CharSequence getContentDescription() {
        if (!isVisible()) {
            return null;
        }
        if (hasText()) {
            return getTextContentDescription();
        }
        if (hasNumber()) {
            return getNumberContentDescription();
        }
        return getEmptyContentDescription();
    }

    private String getNumberContentDescription() {
        Context context;
        if (this.state.getContentDescriptionQuantityStrings() == 0 || (context = (Context) this.contextRef.get()) == null) {
            return null;
        }
        if (this.maxBadgeNumber == -2 || getNumber() <= this.maxBadgeNumber) {
            return context.getResources().getQuantityString(this.state.getContentDescriptionQuantityStrings(), getNumber(), new Object[]{Integer.valueOf(getNumber())});
        }
        return context.getString(this.state.getContentDescriptionExceedsMaxBadgeNumberStringResource(), new Object[]{Integer.valueOf(this.maxBadgeNumber)});
    }

    private CharSequence getTextContentDescription() {
        CharSequence contentDescription = this.state.getContentDescriptionForText();
        if (contentDescription != null) {
            return contentDescription;
        }
        return getText();
    }

    private CharSequence getEmptyContentDescription() {
        return this.state.getContentDescriptionNumberless();
    }

    public void setHorizontalPadding(int horizontalPadding) {
        if (horizontalPadding != this.state.getBadgeHorizontalPadding()) {
            this.state.setBadgeHorizontalPadding(horizontalPadding);
            updateCenterAndBounds();
        }
    }

    public int getHorizontalPadding() {
        return this.state.getBadgeHorizontalPadding();
    }

    public void setVerticalPadding(int verticalPadding) {
        if (verticalPadding != this.state.getBadgeVerticalPadding()) {
            this.state.setBadgeVerticalPadding(verticalPadding);
            updateCenterAndBounds();
        }
    }

    public int getVerticalPadding() {
        return this.state.getBadgeVerticalPadding();
    }

    public void setHorizontalOffset(int px) {
        setHorizontalOffsetWithoutText(px);
        setHorizontalOffsetWithText(px);
    }

    public int getHorizontalOffset() {
        return this.state.getHorizontalOffsetWithoutText();
    }

    public void setHorizontalOffsetWithoutText(int px) {
        this.state.setHorizontalOffsetWithoutText(px);
        updateCenterAndBounds();
    }

    public int getHorizontalOffsetWithoutText() {
        return this.state.getHorizontalOffsetWithoutText();
    }

    public void setHorizontalOffsetWithText(int px) {
        this.state.setHorizontalOffsetWithText(px);
        updateCenterAndBounds();
    }

    public int getHorizontalOffsetWithText() {
        return this.state.getHorizontalOffsetWithText();
    }

    /* access modifiers changed from: package-private */
    public void setAdditionalHorizontalOffset(int px) {
        this.state.setAdditionalHorizontalOffset(px);
        updateCenterAndBounds();
    }

    /* access modifiers changed from: package-private */
    public int getAdditionalHorizontalOffset() {
        return this.state.getAdditionalHorizontalOffset();
    }

    public void setVerticalOffset(int px) {
        setVerticalOffsetWithoutText(px);
        setVerticalOffsetWithText(px);
    }

    public int getVerticalOffset() {
        return this.state.getVerticalOffsetWithoutText();
    }

    public void setVerticalOffsetWithoutText(int px) {
        this.state.setVerticalOffsetWithoutText(px);
        updateCenterAndBounds();
    }

    public int getVerticalOffsetWithoutText() {
        return this.state.getVerticalOffsetWithoutText();
    }

    public void setVerticalOffsetWithText(int px) {
        this.state.setVerticalOffsetWithText(px);
        updateCenterAndBounds();
    }

    public int getVerticalOffsetWithText() {
        return this.state.getVerticalOffsetWithText();
    }

    public void setLargeFontVerticalOffsetAdjustment(int px) {
        this.state.setLargeFontVerticalOffsetAdjustment(px);
        updateCenterAndBounds();
    }

    public int getLargeFontVerticalOffsetAdjustment() {
        return this.state.getLargeFontVerticalOffsetAdjustment();
    }

    /* access modifiers changed from: package-private */
    public void setAdditionalVerticalOffset(int px) {
        this.state.setAdditionalVerticalOffset(px);
        updateCenterAndBounds();
    }

    /* access modifiers changed from: package-private */
    public int getAdditionalVerticalOffset() {
        return this.state.getAdditionalVerticalOffset();
    }

    @Deprecated
    public void setAutoAdjustToWithinGrandparentBounds(boolean autoAdjustToWithinGrandparentBounds) {
        if (this.state.isAutoAdjustedToGrandparentBounds() != autoAdjustToWithinGrandparentBounds) {
            this.state.setAutoAdjustToGrandparentBounds(autoAdjustToWithinGrandparentBounds);
            if (this.anchorViewRef != null && this.anchorViewRef.get() != null) {
                autoAdjustWithinGrandparentBounds((View) this.anchorViewRef.get());
            }
        }
    }

    public void setTextAppearance(int id) {
        this.state.setTextAppearanceResId(id);
        onBadgeTextAppearanceUpdated();
    }

    private void onBadgeTextAppearanceUpdated() {
        TextAppearance textAppearance;
        Context context = (Context) this.contextRef.get();
        if (context != null && this.textDrawableHelper.getTextAppearance() != (textAppearance = new TextAppearance(context, this.state.getTextAppearanceResId()))) {
            this.textDrawableHelper.setTextAppearance(textAppearance, context);
            onBadgeTextColorUpdated();
            updateCenterAndBounds();
            invalidateSelf();
        }
    }

    public void setBadgeWithoutTextShapeAppearance(int id) {
        this.state.setBadgeShapeAppearanceResId(id);
        onBadgeShapeAppearanceUpdated();
    }

    public void setBadgeWithoutTextShapeAppearanceOverlay(int id) {
        this.state.setBadgeShapeAppearanceOverlayResId(id);
        onBadgeShapeAppearanceUpdated();
    }

    public void setBadgeWithTextShapeAppearance(int id) {
        this.state.setBadgeWithTextShapeAppearanceResId(id);
        onBadgeShapeAppearanceUpdated();
    }

    public void setBadgeWithTextShapeAppearanceOverlay(int id) {
        this.state.setBadgeWithTextShapeAppearanceOverlayResId(id);
        onBadgeShapeAppearanceUpdated();
    }

    private void onBadgeShapeAppearanceUpdated() {
        int i;
        int i2;
        Context context = (Context) this.contextRef.get();
        if (context != null) {
            MaterialShapeDrawable materialShapeDrawable = this.shapeDrawable;
            if (hasBadgeContent()) {
                i = this.state.getBadgeWithTextShapeAppearanceResId();
            } else {
                i = this.state.getBadgeShapeAppearanceResId();
            }
            if (hasBadgeContent()) {
                i2 = this.state.getBadgeWithTextShapeAppearanceOverlayResId();
            } else {
                i2 = this.state.getBadgeShapeAppearanceOverlayResId();
            }
            materialShapeDrawable.setShapeAppearanceModel(ShapeAppearanceModel.builder(context, i, i2).build());
            invalidateSelf();
        }
    }

    private void updateCenterAndBounds() {
        Context context = (Context) this.contextRef.get();
        ViewGroup customBadgeParent = null;
        View anchorView = this.anchorViewRef != null ? (View) this.anchorViewRef.get() : null;
        if (context != null && anchorView != null) {
            Rect tmpRect = new Rect();
            tmpRect.set(this.badgeBounds);
            Rect anchorRect = new Rect();
            anchorView.getDrawingRect(anchorRect);
            if (this.customBadgeParentRef != null) {
                customBadgeParent = (ViewGroup) this.customBadgeParentRef.get();
            }
            if (customBadgeParent != null) {
                customBadgeParent.offsetDescendantRectToMyCoords(anchorView, anchorRect);
            }
            calculateCenterAndBounds(anchorRect, anchorView);
            BadgeUtils.updateBadgeBounds(this.badgeBounds, this.badgeCenterX, this.badgeCenterY, this.halfBadgeWidth, this.halfBadgeHeight);
            if (this.cornerRadius != -1.0f) {
                this.shapeDrawable.setCornerSize(this.cornerRadius);
            }
            if (!tmpRect.equals(this.badgeBounds)) {
                this.shapeDrawable.setBounds(this.badgeBounds);
            }
        }
    }

    private int getTotalVerticalOffsetForState() {
        int vOffset = this.state.getVerticalOffsetWithoutText();
        if (hasBadgeContent()) {
            vOffset = this.state.getVerticalOffsetWithText();
            Context context = (Context) this.contextRef.get();
            if (context != null) {
                vOffset = AnimationUtils.lerp(vOffset, vOffset - this.state.getLargeFontVerticalOffsetAdjustment(), AnimationUtils.lerp(0.0f, 1.0f, FONT_SCALE_THRESHOLD, 1.0f, MaterialResources.getFontScale(context) - 1.0f));
            }
        }
        if (this.state.offsetAlignmentMode == 0) {
            vOffset -= Math.round(this.halfBadgeHeight);
        }
        return this.state.getAdditionalVerticalOffset() + vOffset;
    }

    private int getTotalHorizontalOffsetForState() {
        int hOffset;
        if (hasBadgeContent()) {
            hOffset = this.state.getHorizontalOffsetWithText();
        } else {
            hOffset = this.state.getHorizontalOffsetWithoutText();
        }
        if (this.state.offsetAlignmentMode == 1) {
            hOffset += hasBadgeContent() ? this.state.horizontalInsetWithText : this.state.horizontalInset;
        }
        return this.state.getAdditionalHorizontalOffset() + hOffset;
    }

    private void calculateCenterAndBounds(Rect anchorRect, View anchorView) {
        float f;
        float f2;
        this.cornerRadius = hasBadgeContent() ? this.state.badgeWithTextRadius : this.state.badgeRadius;
        if (this.cornerRadius != -1.0f) {
            this.halfBadgeWidth = this.cornerRadius;
            this.halfBadgeHeight = this.cornerRadius;
        } else {
            this.halfBadgeWidth = (float) Math.round((hasBadgeContent() ? this.state.badgeWithTextWidth : this.state.badgeWidth) / 2.0f);
            this.halfBadgeHeight = (float) Math.round((hasBadgeContent() ? this.state.badgeWithTextHeight : this.state.badgeHeight) / 2.0f);
        }
        if (hasBadgeContent()) {
            String badgeContent = getBadgeContent();
            this.halfBadgeWidth = Math.max(this.halfBadgeWidth, (this.textDrawableHelper.getTextWidth(badgeContent) / 2.0f) + ((float) this.state.getBadgeHorizontalPadding()));
            this.halfBadgeHeight = Math.max(this.halfBadgeHeight, (this.textDrawableHelper.getTextHeight(badgeContent) / 2.0f) + ((float) this.state.getBadgeVerticalPadding()));
            this.halfBadgeWidth = Math.max(this.halfBadgeWidth, this.halfBadgeHeight);
        }
        int totalVerticalOffset = getTotalVerticalOffsetForState();
        switch (this.state.getBadgeGravity()) {
            case 8388691:
            case 8388693:
                this.badgeCenterY = (float) (anchorRect.bottom - totalVerticalOffset);
                break;
            default:
                this.badgeCenterY = (float) (anchorRect.top + totalVerticalOffset);
                break;
        }
        int totalHorizontalOffset = getTotalHorizontalOffsetForState();
        switch (this.state.getBadgeGravity()) {
            case 8388659:
            case 8388691:
                if (this.state.badgeFixedEdge == 0) {
                    if (anchorView.getLayoutDirection() == 0) {
                        f = (((float) anchorRect.left) + this.halfBadgeWidth) - ((this.halfBadgeHeight * 2.0f) - ((float) totalHorizontalOffset));
                    } else {
                        f = (((float) anchorRect.right) - this.halfBadgeWidth) + ((this.halfBadgeHeight * 2.0f) - ((float) totalHorizontalOffset));
                    }
                } else if (anchorView.getLayoutDirection() == 0) {
                    f = ((float) totalHorizontalOffset) + (((float) anchorRect.left) - this.halfBadgeWidth);
                } else {
                    f = (((float) anchorRect.right) + this.halfBadgeWidth) - ((float) totalHorizontalOffset);
                }
                this.badgeCenterX = f;
                break;
            default:
                if (this.state.badgeFixedEdge == 0) {
                    if (anchorView.getLayoutDirection() == 0) {
                        f2 = (((float) anchorRect.right) + this.halfBadgeWidth) - ((float) totalHorizontalOffset);
                    } else {
                        f2 = (((float) anchorRect.left) - this.halfBadgeWidth) + ((float) totalHorizontalOffset);
                    }
                } else if (anchorView.getLayoutDirection() == 0) {
                    f2 = (((float) anchorRect.right) - this.halfBadgeWidth) + ((this.halfBadgeHeight * 2.0f) - ((float) totalHorizontalOffset));
                } else {
                    f2 = (((float) anchorRect.left) + this.halfBadgeWidth) - ((this.halfBadgeHeight * 2.0f) - ((float) totalHorizontalOffset));
                }
                this.badgeCenterX = f2;
                break;
        }
        if (this.state.isAutoAdjustedToGrandparentBounds()) {
            autoAdjustWithinGrandparentBounds(anchorView);
        } else {
            autoAdjustWithinViewBounds(anchorView, (View) null);
        }
    }

    private void autoAdjustWithinViewBounds(View anchorView, View ancestorView) {
        ViewParent anchorParent;
        float totalAnchorXOffset;
        float totalAnchorYOffset;
        ViewParent customAnchorParent = getCustomBadgeParent();
        if (customAnchorParent == null) {
            totalAnchorYOffset = anchorView.getY();
            totalAnchorXOffset = anchorView.getX();
            anchorParent = anchorView.getParent();
        } else {
            totalAnchorYOffset = 0.0f;
            totalAnchorXOffset = 0.0f;
            anchorParent = customAnchorParent;
        }
        ViewParent currentViewParent = anchorParent;
        while ((currentViewParent instanceof View) && currentViewParent != ancestorView) {
            ViewParent viewGrandparent = currentViewParent.getParent();
            if (!(viewGrandparent instanceof ViewGroup) || ((ViewGroup) viewGrandparent).getClipChildren()) {
                break;
            }
            View currentViewGroup = (View) currentViewParent;
            totalAnchorYOffset += currentViewGroup.getY();
            totalAnchorXOffset += currentViewGroup.getX();
            currentViewParent = currentViewParent.getParent();
        }
        if (currentViewParent instanceof View) {
            float topCutOff = getTopCutOff(totalAnchorYOffset);
            float leftCutOff = getLeftCutOff(totalAnchorXOffset);
            float bottomCutOff = getBottomCutOff((float) ((View) currentViewParent).getHeight(), totalAnchorYOffset);
            float rightCutOff = getRightCutoff((float) ((View) currentViewParent).getWidth(), totalAnchorXOffset);
            if (topCutOff < 0.0f) {
                this.badgeCenterY += Math.abs(topCutOff);
            }
            if (leftCutOff < 0.0f) {
                this.badgeCenterX += Math.abs(leftCutOff);
            }
            if (bottomCutOff > 0.0f) {
                this.badgeCenterY -= Math.abs(bottomCutOff);
            }
            if (rightCutOff > 0.0f) {
                this.badgeCenterX -= Math.abs(rightCutOff);
            }
        }
    }

    private void autoAdjustWithinGrandparentBounds(View anchorView) {
        ViewParent anchorParent;
        ViewParent customAnchor = getCustomBadgeParent();
        if (customAnchor == null) {
            anchorParent = anchorView.getParent();
        } else {
            anchorParent = customAnchor;
        }
        if ((anchorParent instanceof View) && (anchorParent.getParent() instanceof View)) {
            autoAdjustWithinViewBounds(anchorView, (View) anchorParent.getParent());
        }
    }

    private float getTopCutOff(float totalAnchorYOffset) {
        return (this.badgeCenterY - this.halfBadgeHeight) + totalAnchorYOffset;
    }

    private float getLeftCutOff(float totalAnchorXOffset) {
        return (this.badgeCenterX - this.halfBadgeWidth) + totalAnchorXOffset;
    }

    private float getBottomCutOff(float ancestorHeight, float totalAnchorYOffset) {
        return ((this.badgeCenterY + this.halfBadgeHeight) - ancestorHeight) + totalAnchorYOffset;
    }

    private float getRightCutoff(float ancestorWidth, float totalAnchorXOffset) {
        return ((this.badgeCenterX + this.halfBadgeWidth) - ancestorWidth) + totalAnchorXOffset;
    }

    private void drawBadgeContent(Canvas canvas) {
        String badgeContent = getBadgeContent();
        if (badgeContent != null) {
            Rect textBounds = new Rect();
            this.textDrawableHelper.getTextPaint().getTextBounds(badgeContent, 0, badgeContent.length(), textBounds);
            float exactCenterY = this.badgeCenterY - textBounds.exactCenterY();
            canvas.drawText(badgeContent, this.badgeCenterX, (float) (textBounds.bottom <= 0 ? (int) exactCenterY : Math.round(exactCenterY)), this.textDrawableHelper.getTextPaint());
        }
    }

    private boolean hasBadgeContent() {
        return hasText() || hasNumber();
    }

    private String getBadgeContent() {
        if (hasText()) {
            return getTextBadgeText();
        }
        if (hasNumber()) {
            return getNumberBadgeText();
        }
        return null;
    }

    private String getTextBadgeText() {
        String text = getText();
        int maxCharacterCount = getMaxCharacterCount();
        if (maxCharacterCount == -2 || text == null || text.length() <= maxCharacterCount) {
            return text;
        }
        Context context = (Context) this.contextRef.get();
        if (context == null) {
            return "";
        }
        return String.format(context.getString(R.string.m3_exceed_max_badge_text_suffix), new Object[]{text.substring(0, maxCharacterCount - 1), DEFAULT_EXCEED_MAX_BADGE_TEXT_SUFFIX});
    }

    private String getNumberBadgeText() {
        if (this.maxBadgeNumber == -2 || getNumber() <= this.maxBadgeNumber) {
            return NumberFormat.getInstance(this.state.getNumberLocale()).format((long) getNumber());
        }
        Context context = (Context) this.contextRef.get();
        if (context == null) {
            return "";
        }
        return String.format(this.state.getNumberLocale(), context.getString(R.string.mtrl_exceed_max_badge_number_suffix), new Object[]{Integer.valueOf(this.maxBadgeNumber), DEFAULT_EXCEED_MAX_BADGE_NUMBER_SUFFIX});
    }

    private void onBadgeContentUpdated() {
        this.textDrawableHelper.setTextSizeDirty(true);
        onBadgeShapeAppearanceUpdated();
        updateCenterAndBounds();
        invalidateSelf();
    }

    private void updateMaxBadgeNumber() {
        if (getMaxCharacterCount() != -2) {
            this.maxBadgeNumber = ((int) Math.pow(10.0d, ((double) getMaxCharacterCount()) - 1.0d)) - 1;
        } else {
            this.maxBadgeNumber = getMaxNumber();
        }
    }
}
