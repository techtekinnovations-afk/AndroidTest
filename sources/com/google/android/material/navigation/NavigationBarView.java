package com.google.android.material.navigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuView;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class NavigationBarView extends FrameLayout {
    public static final int ACTIVE_INDICATOR_WIDTH_MATCH_PARENT = -1;
    public static final int ACTIVE_INDICATOR_WIDTH_WRAP_CONTENT = -2;
    public static final int ITEM_GRAVITY_CENTER = 17;
    public static final int ITEM_GRAVITY_START_CENTER = 8388627;
    public static final int ITEM_GRAVITY_TOP_CENTER = 49;
    public static final int ITEM_ICON_GRAVITY_START = 1;
    public static final int ITEM_ICON_GRAVITY_TOP = 0;
    public static final int LABEL_VISIBILITY_AUTO = -1;
    public static final int LABEL_VISIBILITY_LABELED = 1;
    public static final int LABEL_VISIBILITY_SELECTED = 0;
    public static final int LABEL_VISIBILITY_UNLABELED = 2;
    private static final int MENU_PRESENTER_ID = 1;
    private final NavigationBarMenu menu;
    private MenuInflater menuInflater;
    private final NavigationBarMenuView menuView;
    private final NavigationBarPresenter presenter = new NavigationBarPresenter();
    /* access modifiers changed from: private */
    public OnItemReselectedListener reselectedListener;
    /* access modifiers changed from: private */
    public OnItemSelectedListener selectedListener;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ItemGravity {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ItemIconGravity {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LabelVisibility {
    }

    public interface OnItemReselectedListener {
        void onNavigationItemReselected(MenuItem menuItem);
    }

    public interface OnItemSelectedListener {
        boolean onNavigationItemSelected(MenuItem menuItem);
    }

    /* access modifiers changed from: protected */
    public abstract NavigationBarMenuView createNavigationBarMenuView(Context context);

    public abstract int getMaxItemCount();

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public NavigationBarView(android.content.Context r26, android.util.AttributeSet r27, int r28, int r29) {
        /*
            r25 = this;
            r0 = r25
            r2 = r27
            r4 = r28
            android.content.Context r1 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r26, r27, r28, r29)
            r0.<init>(r1, r2, r4)
            com.google.android.material.navigation.NavigationBarPresenter r1 = new com.google.android.material.navigation.NavigationBarPresenter
            r1.<init>()
            r0.presenter = r1
            android.content.Context r1 = r0.getContext()
            int[] r3 = com.google.android.material.R.styleable.NavigationBarView
            int r5 = com.google.android.material.R.styleable.NavigationBarView_itemTextAppearanceInactive
            int r6 = com.google.android.material.R.styleable.NavigationBarView_itemTextAppearanceActive
            int[] r6 = new int[]{r5, r6}
            r5 = r29
            androidx.appcompat.widget.TintTypedArray r3 = com.google.android.material.internal.ThemeEnforcement.obtainTintedStyledAttributes(r1, r2, r3, r4, r5, r6)
            com.google.android.material.navigation.NavigationBarMenu r5 = new com.google.android.material.navigation.NavigationBarMenu
            java.lang.Class r6 = r0.getClass()
            int r7 = r0.getMaxItemCount()
            boolean r8 = r0.isSubMenuSupported()
            r5.<init>(r1, r6, r7, r8)
            r0.menu = r5
            com.google.android.material.navigation.NavigationBarMenuView r5 = r0.createNavigationBarMenuView(r1)
            r0.menuView = r5
            com.google.android.material.navigation.NavigationBarMenuView r5 = r0.menuView
            int r6 = r0.getSuggestedMinimumHeight()
            r5.setMinimumHeight(r6)
            com.google.android.material.navigation.NavigationBarMenuView r5 = r0.menuView
            int r6 = r0.getCollapsedMaxItemCount()
            r5.setCollapsedMaxItemCount(r6)
            com.google.android.material.navigation.NavigationBarPresenter r5 = r0.presenter
            com.google.android.material.navigation.NavigationBarMenuView r6 = r0.menuView
            r5.setMenuView(r6)
            com.google.android.material.navigation.NavigationBarPresenter r5 = r0.presenter
            r6 = 1
            r5.setId(r6)
            com.google.android.material.navigation.NavigationBarMenuView r5 = r0.menuView
            com.google.android.material.navigation.NavigationBarPresenter r7 = r0.presenter
            r5.setPresenter(r7)
            com.google.android.material.navigation.NavigationBarMenu r5 = r0.menu
            com.google.android.material.navigation.NavigationBarPresenter r7 = r0.presenter
            r5.addMenuPresenter(r7)
            com.google.android.material.navigation.NavigationBarPresenter r5 = r0.presenter
            android.content.Context r7 = r0.getContext()
            com.google.android.material.navigation.NavigationBarMenu r8 = r0.menu
            r5.initForMenu(r7, r8)
            int r5 = com.google.android.material.R.styleable.NavigationBarView_itemIconTint
            boolean r5 = r3.hasValue(r5)
            if (r5 == 0) goto L_0x008d
            com.google.android.material.navigation.NavigationBarMenuView r5 = r0.menuView
            int r7 = com.google.android.material.R.styleable.NavigationBarView_itemIconTint
            android.content.res.ColorStateList r7 = r3.getColorStateList(r7)
            r5.setIconTintList(r7)
            goto L_0x009b
        L_0x008d:
            com.google.android.material.navigation.NavigationBarMenuView r5 = r0.menuView
            com.google.android.material.navigation.NavigationBarMenuView r7 = r0.menuView
            r8 = 16842808(0x1010038, float:2.3693715E-38)
            android.content.res.ColorStateList r7 = r7.createDefaultColorStateList(r8)
            r5.setIconTintList(r7)
        L_0x009b:
            int r5 = com.google.android.material.R.styleable.NavigationBarView_itemIconSize
            android.content.res.Resources r7 = r0.getResources()
            int r8 = com.google.android.material.R.dimen.mtrl_navigation_bar_item_default_icon_size
            int r7 = r7.getDimensionPixelSize(r8)
            int r5 = r3.getDimensionPixelSize(r5, r7)
            r0.setItemIconSize(r5)
            int r5 = com.google.android.material.R.styleable.NavigationBarView_itemTextAppearanceInactive
            boolean r5 = r3.hasValue(r5)
            r7 = 0
            if (r5 == 0) goto L_0x00c0
            int r5 = com.google.android.material.R.styleable.NavigationBarView_itemTextAppearanceInactive
            int r5 = r3.getResourceId(r5, r7)
            r0.setItemTextAppearanceInactive(r5)
        L_0x00c0:
            int r5 = com.google.android.material.R.styleable.NavigationBarView_itemTextAppearanceActive
            boolean r5 = r3.hasValue(r5)
            if (r5 == 0) goto L_0x00d1
            int r5 = com.google.android.material.R.styleable.NavigationBarView_itemTextAppearanceActive
            int r5 = r3.getResourceId(r5, r7)
            r0.setItemTextAppearanceActive(r5)
        L_0x00d1:
            int r5 = com.google.android.material.R.styleable.NavigationBarView_horizontalItemTextAppearanceInactive
            boolean r5 = r3.hasValue(r5)
            if (r5 == 0) goto L_0x00e2
            int r5 = com.google.android.material.R.styleable.NavigationBarView_horizontalItemTextAppearanceInactive
            int r5 = r3.getResourceId(r5, r7)
            r0.setHorizontalItemTextAppearanceInactive(r5)
        L_0x00e2:
            int r5 = com.google.android.material.R.styleable.NavigationBarView_horizontalItemTextAppearanceActive
            boolean r5 = r3.hasValue(r5)
            if (r5 == 0) goto L_0x00f3
            int r5 = com.google.android.material.R.styleable.NavigationBarView_horizontalItemTextAppearanceActive
            int r5 = r3.getResourceId(r5, r7)
            r0.setHorizontalItemTextAppearanceActive(r5)
        L_0x00f3:
            int r5 = com.google.android.material.R.styleable.NavigationBarView_itemTextAppearanceActiveBoldEnabled
            boolean r5 = r3.getBoolean(r5, r6)
            r0.setItemTextAppearanceActiveBoldEnabled(r5)
            int r8 = com.google.android.material.R.styleable.NavigationBarView_itemTextColor
            boolean r8 = r3.hasValue(r8)
            if (r8 == 0) goto L_0x010d
            int r8 = com.google.android.material.R.styleable.NavigationBarView_itemTextColor
            android.content.res.ColorStateList r8 = r3.getColorStateList(r8)
            r0.setItemTextColor(r8)
        L_0x010d:
            android.graphics.drawable.Drawable r8 = r0.getBackground()
            android.content.res.ColorStateList r9 = com.google.android.material.drawable.DrawableUtils.getColorStateListOrNull(r8)
            if (r8 == 0) goto L_0x011d
            if (r9 == 0) goto L_0x011a
            goto L_0x011d
        L_0x011a:
            r10 = r29
            goto L_0x0138
        L_0x011d:
            r10 = r29
            com.google.android.material.shape.ShapeAppearanceModel$Builder r11 = com.google.android.material.shape.ShapeAppearanceModel.builder((android.content.Context) r1, (android.util.AttributeSet) r2, (int) r4, (int) r10)
            com.google.android.material.shape.ShapeAppearanceModel r11 = r11.build()
            com.google.android.material.shape.MaterialShapeDrawable r12 = new com.google.android.material.shape.MaterialShapeDrawable
            r12.<init>((com.google.android.material.shape.ShapeAppearanceModel) r11)
            if (r9 == 0) goto L_0x0132
            r12.setFillColor(r9)
        L_0x0132:
            r12.initializeElevationOverlay(r1)
            r0.setBackground(r12)
        L_0x0138:
            int r11 = com.google.android.material.R.styleable.NavigationBarView_itemPaddingTop
            boolean r11 = r3.hasValue(r11)
            if (r11 == 0) goto L_0x0149
            int r11 = com.google.android.material.R.styleable.NavigationBarView_itemPaddingTop
            int r11 = r3.getDimensionPixelSize(r11, r7)
            r0.setItemPaddingTop(r11)
        L_0x0149:
            int r11 = com.google.android.material.R.styleable.NavigationBarView_itemPaddingBottom
            boolean r11 = r3.hasValue(r11)
            if (r11 == 0) goto L_0x015a
            int r11 = com.google.android.material.R.styleable.NavigationBarView_itemPaddingBottom
            int r11 = r3.getDimensionPixelSize(r11, r7)
            r0.setItemPaddingBottom(r11)
        L_0x015a:
            int r11 = com.google.android.material.R.styleable.NavigationBarView_activeIndicatorLabelPadding
            boolean r11 = r3.hasValue(r11)
            if (r11 == 0) goto L_0x016b
            int r11 = com.google.android.material.R.styleable.NavigationBarView_activeIndicatorLabelPadding
            int r11 = r3.getDimensionPixelSize(r11, r7)
            r0.setActiveIndicatorLabelPadding(r11)
        L_0x016b:
            int r11 = com.google.android.material.R.styleable.NavigationBarView_iconLabelHorizontalSpacing
            boolean r11 = r3.hasValue(r11)
            if (r11 == 0) goto L_0x017c
            int r11 = com.google.android.material.R.styleable.NavigationBarView_iconLabelHorizontalSpacing
            int r11 = r3.getDimensionPixelSize(r11, r7)
            r0.setIconLabelHorizontalSpacing(r11)
        L_0x017c:
            int r11 = com.google.android.material.R.styleable.NavigationBarView_elevation
            boolean r11 = r3.hasValue(r11)
            if (r11 == 0) goto L_0x018e
            int r11 = com.google.android.material.R.styleable.NavigationBarView_elevation
            int r11 = r3.getDimensionPixelSize(r11, r7)
            float r11 = (float) r11
            r0.setElevation(r11)
        L_0x018e:
            int r11 = com.google.android.material.R.styleable.NavigationBarView_backgroundTint
            android.content.res.ColorStateList r11 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r1, (androidx.appcompat.widget.TintTypedArray) r3, (int) r11)
            android.graphics.drawable.Drawable r12 = r0.getBackground()
            android.graphics.drawable.Drawable r12 = r12.mutate()
            r12.setTintList(r11)
            int r12 = com.google.android.material.R.styleable.NavigationBarView_labelVisibilityMode
            r13 = -1
            int r12 = r3.getInteger(r12, r13)
            r0.setLabelVisibilityMode(r12)
            int r12 = com.google.android.material.R.styleable.NavigationBarView_itemIconGravity
            int r12 = r3.getInteger(r12, r7)
            r0.setItemIconGravity(r12)
            int r12 = com.google.android.material.R.styleable.NavigationBarView_itemGravity
            r14 = 49
            int r12 = r3.getInteger(r12, r14)
            r0.setItemGravity(r12)
            int r12 = com.google.android.material.R.styleable.NavigationBarView_itemBackground
            int r12 = r3.getResourceId(r12, r7)
            if (r12 == 0) goto L_0x01cb
            com.google.android.material.navigation.NavigationBarMenuView r14 = r0.menuView
            r14.setItemBackgroundRes(r12)
            goto L_0x01d4
        L_0x01cb:
            int r14 = com.google.android.material.R.styleable.NavigationBarView_itemRippleColor
            android.content.res.ColorStateList r14 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r1, (androidx.appcompat.widget.TintTypedArray) r3, (int) r14)
            r0.setItemRippleColor(r14)
        L_0x01d4:
            int r14 = com.google.android.material.R.styleable.NavigationBarView_measureBottomPaddingFromLabelBaseline
            boolean r14 = r3.getBoolean(r14, r6)
            r0.setMeasureBottomPaddingFromLabelBaseline(r14)
            int r14 = com.google.android.material.R.styleable.NavigationBarView_labelFontScalingEnabled
            boolean r14 = r3.getBoolean(r14, r7)
            r0.setLabelFontScalingEnabled(r14)
            int r14 = com.google.android.material.R.styleable.NavigationBarView_labelMaxLines
            int r14 = r3.getInteger(r14, r6)
            r0.setLabelMaxLines(r14)
            int r14 = com.google.android.material.R.styleable.NavigationBarView_itemActiveIndicatorStyle
            int r14 = r3.getResourceId(r14, r7)
            if (r14 == 0) goto L_0x02e3
            r0.setItemActiveIndicatorEnabled(r6)
            int[] r15 = com.google.android.material.R.styleable.NavigationBarActiveIndicator
            android.content.res.TypedArray r15 = r1.obtainStyledAttributes(r14, r15)
            r26 = r13
            int r13 = com.google.android.material.R.styleable.NavigationBarActiveIndicator_android_width
            int r13 = r15.getDimensionPixelSize(r13, r7)
            r0.setItemActiveIndicatorWidth(r13)
            int r6 = com.google.android.material.R.styleable.NavigationBarActiveIndicator_android_height
            int r6 = r15.getDimensionPixelSize(r6, r7)
            r0.setItemActiveIndicatorHeight(r6)
            int r2 = com.google.android.material.R.styleable.NavigationBarActiveIndicator_marginHorizontal
            int r2 = r15.getDimensionPixelOffset(r2, r7)
            r0.setItemActiveIndicatorMarginHorizontal(r2)
            r17 = -2
            int r7 = com.google.android.material.R.styleable.NavigationBarActiveIndicator_expandedWidth
            java.lang.String r7 = r15.getString(r7)
            if (r7 == 0) goto L_0x0258
            java.lang.String r4 = java.lang.String.valueOf(r26)
            boolean r4 = r4.equals(r7)
            if (r4 == 0) goto L_0x0238
            r17 = -1
            r18 = r5
            r4 = r17
            goto L_0x025c
        L_0x0238:
            r26 = -2
            java.lang.String r4 = java.lang.String.valueOf(r26)
            boolean r4 = r4.equals(r7)
            if (r4 == 0) goto L_0x024b
            r17 = -2
            r18 = r5
            r4 = r17
            goto L_0x025c
        L_0x024b:
            int r4 = com.google.android.material.R.styleable.NavigationBarActiveIndicator_expandedWidth
            r18 = r5
            r5 = r26
            int r17 = r15.getDimensionPixelSize(r4, r5)
            r4 = r17
            goto L_0x025c
        L_0x0258:
            r18 = r5
            r4 = r17
        L_0x025c:
            r0.setItemActiveIndicatorExpandedWidth(r4)
            int r5 = com.google.android.material.R.styleable.NavigationBarActiveIndicator_expandedHeight
            int r5 = r15.getDimensionPixelSize(r5, r13)
            r0.setItemActiveIndicatorExpandedHeight(r5)
            r17 = r4
            int r4 = com.google.android.material.R.styleable.NavigationBarActiveIndicator_expandedMarginHorizontal
            int r4 = r15.getDimensionPixelOffset(r4, r2)
            r0.setItemActiveIndicatorExpandedMarginHorizontal(r4)
            r26 = r2
            android.content.res.Resources r2 = r0.getResources()
            r19 = r4
            int r4 = com.google.android.material.R.dimen.m3_navigation_item_leading_trailing_space
            int r2 = r2.getDimensionPixelSize(r4)
            int r4 = com.google.android.material.R.styleable.NavigationBarActiveIndicator_expandedActiveIndicatorPaddingStart
            int r4 = r15.getDimensionPixelOffset(r4, r2)
            r20 = r4
            int r4 = com.google.android.material.R.styleable.NavigationBarActiveIndicator_expandedActiveIndicatorPaddingEnd
            int r4 = r15.getDimensionPixelOffset(r4, r2)
            r21 = r2
            int r2 = r0.getLayoutDirection()
            r22 = r4
            r4 = 1
            if (r2 != r4) goto L_0x029e
            r2 = r22
            goto L_0x02a0
        L_0x029e:
            r2 = r20
        L_0x02a0:
            int r4 = com.google.android.material.R.styleable.NavigationBarActiveIndicator_expandedActiveIndicatorPaddingTop
            r23 = r5
            r5 = 0
            int r4 = r15.getDimensionPixelOffset(r4, r5)
            int r5 = r0.getLayoutDirection()
            r24 = r6
            r6 = 1
            if (r5 != r6) goto L_0x02b5
            r5 = r20
            goto L_0x02b7
        L_0x02b5:
            r5 = r22
        L_0x02b7:
            int r6 = com.google.android.material.R.styleable.NavigationBarActiveIndicator_expandedActiveIndicatorPaddingBottom
            r16 = r7
            r7 = 0
            int r6 = r15.getDimensionPixelOffset(r6, r7)
            r0.setItemActiveIndicatorExpandedPadding(r2, r4, r5, r6)
            int r2 = com.google.android.material.R.styleable.NavigationBarActiveIndicator_android_color
            android.content.res.ColorStateList r2 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r1, (android.content.res.TypedArray) r15, (int) r2)
            r0.setItemActiveIndicatorColor(r2)
            int r4 = com.google.android.material.R.styleable.NavigationBarActiveIndicator_shapeAppearance
            r5 = 0
            int r4 = r15.getResourceId(r4, r5)
            com.google.android.material.shape.ShapeAppearanceModel$Builder r6 = com.google.android.material.shape.ShapeAppearanceModel.builder(r1, r4, r5)
            com.google.android.material.shape.ShapeAppearanceModel r5 = r6.build()
            r0.setItemActiveIndicatorShapeAppearance(r5)
            r15.recycle()
            goto L_0x02e5
        L_0x02e3:
            r18 = r5
        L_0x02e5:
            int r2 = com.google.android.material.R.styleable.NavigationBarView_menu
            boolean r2 = r3.hasValue(r2)
            if (r2 == 0) goto L_0x02f7
            int r2 = com.google.android.material.R.styleable.NavigationBarView_menu
            r5 = 0
            int r2 = r3.getResourceId(r2, r5)
            r0.inflateMenu(r2)
        L_0x02f7:
            r3.recycle()
            boolean r2 = r0.shouldAddMenuView()
            if (r2 != 0) goto L_0x0305
            com.google.android.material.navigation.NavigationBarMenuView r2 = r0.menuView
            r0.addView(r2)
        L_0x0305:
            com.google.android.material.navigation.NavigationBarMenu r2 = r0.menu
            com.google.android.material.navigation.NavigationBarView$1 r4 = new com.google.android.material.navigation.NavigationBarView$1
            r4.<init>()
            r2.setCallback(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.navigation.NavigationBarView.<init>(android.content.Context, android.util.AttributeSet, int, int):void");
    }

    public boolean shouldAddMenuView() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
    }

    public void setElevation(float elevation) {
        super.setElevation(elevation);
        MaterialShapeUtils.setElevation(this, elevation);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.selectedListener = listener;
    }

    public void setOnItemReselectedListener(OnItemReselectedListener listener) {
        this.reselectedListener = listener;
    }

    public Menu getMenu() {
        return this.menu;
    }

    public MenuView getMenuView() {
        return this.menuView;
    }

    public ViewGroup getMenuViewGroup() {
        return this.menuView;
    }

    public void inflateMenu(int resId) {
        this.presenter.setUpdateSuspended(true);
        getMenuInflater().inflate(resId, this.menu);
        this.presenter.setUpdateSuspended(false);
        this.presenter.updateMenuView(true);
    }

    public ColorStateList getItemIconTintList() {
        return this.menuView.getIconTintList();
    }

    public void setItemIconTintList(ColorStateList tint) {
        this.menuView.setIconTintList(tint);
    }

    public void setItemIconSize(int iconSize) {
        this.menuView.setItemIconSize(iconSize);
    }

    public void setItemIconSizeRes(int iconSizeRes) {
        setItemIconSize(getResources().getDimensionPixelSize(iconSizeRes));
    }

    public int getItemIconSize() {
        return this.menuView.getItemIconSize();
    }

    public ColorStateList getItemTextColor() {
        return this.menuView.getItemTextColor();
    }

    public void setItemTextColor(ColorStateList textColor) {
        this.menuView.setItemTextColor(textColor);
    }

    @Deprecated
    public int getItemBackgroundResource() {
        return this.menuView.getItemBackgroundRes();
    }

    public void setItemBackgroundResource(int resId) {
        this.menuView.setItemBackgroundRes(resId);
    }

    public Drawable getItemBackground() {
        return this.menuView.getItemBackground();
    }

    public void setItemBackground(Drawable background) {
        this.menuView.setItemBackground(background);
    }

    public ColorStateList getItemRippleColor() {
        return this.menuView.getItemRippleColor();
    }

    public void setItemRippleColor(ColorStateList itemRippleColor) {
        this.menuView.setItemRippleColor(itemRippleColor);
    }

    public int getItemPaddingTop() {
        return this.menuView.getItemPaddingTop();
    }

    public void setItemPaddingTop(int paddingTop) {
        this.menuView.setItemPaddingTop(paddingTop);
    }

    public int getItemPaddingBottom() {
        return this.menuView.getItemPaddingBottom();
    }

    public void setItemPaddingBottom(int paddingBottom) {
        this.menuView.setItemPaddingBottom(paddingBottom);
    }

    private void setMeasureBottomPaddingFromLabelBaseline(boolean measurePaddingFromBaseline) {
        this.menuView.setMeasurePaddingFromLabelBaseline(measurePaddingFromBaseline);
    }

    public void setLabelFontScalingEnabled(boolean labelFontScalingEnabled) {
        this.menuView.setLabelFontScalingEnabled(labelFontScalingEnabled);
    }

    public boolean getScaleLabelTextWithFont() {
        return this.menuView.getScaleLabelTextWithFont();
    }

    public void setLabelMaxLines(int labelMaxLines) {
        this.menuView.setLabelMaxLines(labelMaxLines);
    }

    public int getLabelMaxLines(int labelMaxLines) {
        return this.menuView.getLabelMaxLines();
    }

    public void setActiveIndicatorLabelPadding(int activeIndicatorLabelPadding) {
        this.menuView.setActiveIndicatorLabelPadding(activeIndicatorLabelPadding);
    }

    public int getActiveIndicatorLabelPadding() {
        return this.menuView.getActiveIndicatorLabelPadding();
    }

    public void setIconLabelHorizontalSpacing(int iconLabelSpacing) {
        this.menuView.setIconLabelHorizontalSpacing(iconLabelSpacing);
    }

    public int getIconLabelHorizontalSpacing() {
        return this.menuView.getIconLabelHorizontalSpacing();
    }

    public boolean isItemActiveIndicatorEnabled() {
        return this.menuView.getItemActiveIndicatorEnabled();
    }

    public void setItemActiveIndicatorEnabled(boolean enabled) {
        this.menuView.setItemActiveIndicatorEnabled(enabled);
    }

    public int getItemActiveIndicatorWidth() {
        return this.menuView.getItemActiveIndicatorWidth();
    }

    public void setItemActiveIndicatorWidth(int width) {
        this.menuView.setItemActiveIndicatorWidth(width);
    }

    public int getItemActiveIndicatorHeight() {
        return this.menuView.getItemActiveIndicatorHeight();
    }

    public void setItemActiveIndicatorHeight(int height) {
        this.menuView.setItemActiveIndicatorHeight(height);
    }

    public int getItemActiveIndicatorMarginHorizontal() {
        return this.menuView.getItemActiveIndicatorMarginHorizontal();
    }

    public void setItemActiveIndicatorMarginHorizontal(int horizontalMargin) {
        this.menuView.setItemActiveIndicatorMarginHorizontal(horizontalMargin);
    }

    public void setItemGravity(int itemGravity) {
        if (this.menuView.getItemGravity() != itemGravity) {
            this.menuView.setItemGravity(itemGravity);
            this.presenter.updateMenuView(false);
        }
    }

    public int getItemGravity() {
        return this.menuView.getItemGravity();
    }

    public int getItemActiveIndicatorExpandedWidth() {
        return this.menuView.getItemActiveIndicatorExpandedWidth();
    }

    public void setItemActiveIndicatorExpandedWidth(int width) {
        this.menuView.setItemActiveIndicatorExpandedWidth(width);
    }

    public int getItemActiveIndicatorExpandedHeight() {
        return this.menuView.getItemActiveIndicatorExpandedHeight();
    }

    public void setItemActiveIndicatorExpandedHeight(int height) {
        this.menuView.setItemActiveIndicatorExpandedHeight(height);
    }

    public int getItemActiveIndicatorExpandedMarginHorizontal() {
        return this.menuView.getItemActiveIndicatorExpandedMarginHorizontal();
    }

    public void setItemActiveIndicatorExpandedMarginHorizontal(int horizontalMargin) {
        this.menuView.setItemActiveIndicatorExpandedMarginHorizontal(horizontalMargin);
    }

    public void setItemActiveIndicatorExpandedPadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        this.menuView.setItemActiveIndicatorExpandedPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public ShapeAppearanceModel getItemActiveIndicatorShapeAppearance() {
        return this.menuView.getItemActiveIndicatorShapeAppearance();
    }

    public void setItemActiveIndicatorShapeAppearance(ShapeAppearanceModel shapeAppearance) {
        this.menuView.setItemActiveIndicatorShapeAppearance(shapeAppearance);
    }

    public ColorStateList getItemActiveIndicatorColor() {
        return this.menuView.getItemActiveIndicatorColor();
    }

    public void setItemActiveIndicatorColor(ColorStateList csl) {
        this.menuView.setItemActiveIndicatorColor(csl);
    }

    public int getSelectedItemId() {
        return this.menuView.getSelectedItemId();
    }

    public void setSelectedItemId(int itemId) {
        MenuItem item = this.menu.findItem(itemId);
        if (item != null) {
            boolean result = this.menu.performItemAction(item, this.presenter, 0);
            if (!item.isCheckable()) {
                return;
            }
            if (!result || item.isChecked()) {
                this.menuView.setCheckedItem(item);
            }
        }
    }

    public void setLabelVisibilityMode(int labelVisibilityMode) {
        if (this.menuView.getLabelVisibilityMode() != labelVisibilityMode) {
            this.menuView.setLabelVisibilityMode(labelVisibilityMode);
            this.presenter.updateMenuView(false);
        }
    }

    public int getLabelVisibilityMode() {
        return this.menuView.getLabelVisibilityMode();
    }

    public void setItemIconGravity(int itemIconGravity) {
        if (this.menuView.getItemIconGravity() != itemIconGravity) {
            this.menuView.setItemIconGravity(itemIconGravity);
            this.presenter.updateMenuView(false);
        }
    }

    public int getItemIconGravity() {
        return this.menuView.getItemIconGravity();
    }

    public void setItemTextAppearanceInactive(int textAppearanceRes) {
        this.menuView.setItemTextAppearanceInactive(textAppearanceRes);
    }

    public int getItemTextAppearanceInactive() {
        return this.menuView.getItemTextAppearanceInactive();
    }

    public void setItemTextAppearanceActive(int textAppearanceRes) {
        this.menuView.setItemTextAppearanceActive(textAppearanceRes);
    }

    public int getItemTextAppearanceActive() {
        return this.menuView.getItemTextAppearanceActive();
    }

    public void setHorizontalItemTextAppearanceInactive(int textAppearanceRes) {
        this.menuView.setHorizontalItemTextAppearanceInactive(textAppearanceRes);
    }

    public int getHorizontalItemTextAppearanceInactive() {
        return this.menuView.getHorizontalItemTextAppearanceInactive();
    }

    public void setHorizontalItemTextAppearanceActive(int textAppearanceRes) {
        this.menuView.setHorizontalItemTextAppearanceActive(textAppearanceRes);
    }

    public int getHorizontalItemTextAppearanceActive() {
        return this.menuView.getHorizontalItemTextAppearanceActive();
    }

    public void setItemTextAppearanceActiveBoldEnabled(boolean isBold) {
        this.menuView.setItemTextAppearanceActiveBoldEnabled(isBold);
    }

    public void setItemOnTouchListener(int menuItemId, View.OnTouchListener onTouchListener) {
        this.menuView.setItemOnTouchListener(menuItemId, onTouchListener);
    }

    public BadgeDrawable getBadge(int menuItemId) {
        return this.menuView.getBadge(menuItemId);
    }

    public BadgeDrawable getOrCreateBadge(int menuItemId) {
        return this.menuView.getOrCreateBadge(menuItemId);
    }

    public void removeBadge(int menuItemId) {
        this.menuView.removeBadge(menuItemId);
    }

    /* access modifiers changed from: protected */
    public boolean isSubMenuSupported() {
        return false;
    }

    public int getCollapsedMaxItemCount() {
        return getMaxItemCount();
    }

    private MenuInflater getMenuInflater() {
        if (this.menuInflater == null) {
            this.menuInflater = new SupportMenuInflater(getContext());
        }
        return this.menuInflater;
    }

    public NavigationBarPresenter getPresenter() {
        return this.presenter;
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.menuPresenterState = new Bundle();
        this.menu.savePresenterStates(savedState.menuPresenterState);
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.menu.restorePresenterStates(savedState.menuPresenterState);
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
        Bundle menuPresenterState;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            readFromParcel(source, loader == null ? getClass().getClassLoader() : loader);
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeBundle(this.menuPresenterState);
        }

        private void readFromParcel(Parcel in, ClassLoader loader) {
            this.menuPresenterState = in.readBundle(loader);
        }
    }
}
