package com.google.android.material.navigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.util.Pools;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.internal.TextScale;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.util.HashSet;

public abstract class NavigationBarMenuView extends ViewGroup implements MenuView {
    private static final int[] CHECKED_STATE_SET = {16842912};
    private static final int DEFAULT_COLLAPSED_MAX_COUNT = 7;
    private static final int[] DISABLED_STATE_SET = {-16842910};
    private static final int NO_PADDING = -1;
    private static final int NO_SELECTED_ITEM = -1;
    private final SparseArray<BadgeDrawable> badgeDrawables = new SparseArray<>();
    private NavigationBarMenuItemView[] buttons;
    private MenuItem checkedItem = null;
    private int collapsedMaxItemCount = 7;
    private boolean dividersEnabled = false;
    private boolean expanded;
    private int horizontalItemTextAppearanceActive;
    private int horizontalItemTextAppearanceInactive;
    private int iconLabelHorizontalSpacing = -1;
    private ColorStateList itemActiveIndicatorColor;
    private boolean itemActiveIndicatorEnabled;
    private int itemActiveIndicatorExpandedHeight;
    private int itemActiveIndicatorExpandedMarginHorizontal;
    private final Rect itemActiveIndicatorExpandedPadding = new Rect();
    private int itemActiveIndicatorExpandedWidth;
    private int itemActiveIndicatorHeight;
    private int itemActiveIndicatorLabelPadding = -1;
    private int itemActiveIndicatorMarginHorizontal;
    private boolean itemActiveIndicatorResizeable = false;
    private ShapeAppearanceModel itemActiveIndicatorShapeAppearance;
    private int itemActiveIndicatorWidth;
    private Drawable itemBackground;
    private int itemBackgroundRes;
    private int itemGravity = 49;
    private int itemIconGravity;
    private int itemIconSize;
    private ColorStateList itemIconTint;
    private int itemPaddingBottom = -1;
    private int itemPaddingTop = -1;
    private Pools.Pool<NavigationBarItemView> itemPool;
    private int itemPoolSize = 0;
    private ColorStateList itemRippleColor;
    private int itemTextAppearanceActive;
    private boolean itemTextAppearanceActiveBoldEnabled;
    private int itemTextAppearanceInactive;
    private final ColorStateList itemTextColorDefault = createDefaultColorStateList(16842808);
    private ColorStateList itemTextColorFromUser;
    private int labelMaxLines = 1;
    private int labelVisibilityMode;
    private boolean measurePaddingFromLabelBaseline;
    /* access modifiers changed from: private */
    public NavigationBarMenuBuilder menu;
    private final View.OnClickListener onClickListener;
    private final SparseArray<View.OnTouchListener> onTouchListeners = new SparseArray<>();
    /* access modifiers changed from: private */
    public NavigationBarPresenter presenter;
    private boolean scaleLabelWithFont;
    private int selectedItemId = -1;
    private int selectedItemPosition = -1;
    private final TransitionSet set;

    /* access modifiers changed from: protected */
    public abstract NavigationBarItemView createNavigationBarItemView(Context context);

    public NavigationBarMenuView(Context context) {
        super(context);
        if (isInEditMode()) {
            this.set = null;
        } else {
            this.set = new AutoTransition();
            this.set.setOrdering(0);
            this.set.excludeTarget((Class<?>) TextView.class, true);
            this.set.setDuration((long) MotionUtils.resolveThemeDuration(getContext(), R.attr.motionDurationMedium4, getResources().getInteger(R.integer.material_motion_duration_long_1)));
            this.set.setInterpolator(MotionUtils.resolveThemeInterpolator(getContext(), R.attr.motionEasingStandard, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
            this.set.addTransition(new TextScale());
        }
        this.onClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                MenuItem item = ((NavigationBarItemView) v).getItemData();
                boolean result = NavigationBarMenuView.this.menu.performItemAction(item, NavigationBarMenuView.this.presenter, 0);
                if (item != null && item.isCheckable()) {
                    if (!result || item.isChecked()) {
                        NavigationBarMenuView.this.setCheckedItem(item);
                    }
                }
            }
        };
        setImportantForAccessibility(1);
    }

    public void setCheckedItem(MenuItem checkedItem2) {
        if (this.checkedItem != checkedItem2 && checkedItem2.isCheckable()) {
            if (this.checkedItem != null && this.checkedItem.isChecked()) {
                this.checkedItem.setChecked(false);
            }
            checkedItem2.setChecked(true);
            this.checkedItem = checkedItem2;
        }
    }

    public void setExpanded(boolean expanded2) {
        this.expanded = expanded2;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                item.setExpanded(expanded2);
            }
        }
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    public void initialize(MenuBuilder menu2) {
        this.menu = new NavigationBarMenuBuilder(menu2);
    }

    public int getWindowAnimations() {
        return 0;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        AccessibilityNodeInfoCompat.wrap(info).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, getCurrentVisibleContentItemCount(), false, 1));
    }

    public void setIconTintList(ColorStateList tint) {
        this.itemIconTint = tint;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setIconTintList(tint);
                }
            }
        }
    }

    public ColorStateList getIconTintList() {
        return this.itemIconTint;
    }

    public void setItemIconSize(int iconSize) {
        this.itemIconSize = iconSize;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setIconSize(iconSize);
                }
            }
        }
    }

    public int getItemIconSize() {
        return this.itemIconSize;
    }

    public void setItemTextColor(ColorStateList color) {
        this.itemTextColorFromUser = color;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setTextColor(color);
                }
            }
        }
    }

    public ColorStateList getItemTextColor() {
        return this.itemTextColorFromUser;
    }

    public void setItemTextAppearanceInactive(int textAppearanceRes) {
        this.itemTextAppearanceInactive = textAppearanceRes;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setTextAppearanceInactive(textAppearanceRes);
                }
            }
        }
    }

    public int getItemTextAppearanceInactive() {
        return this.itemTextAppearanceInactive;
    }

    public void setItemTextAppearanceActive(int textAppearanceRes) {
        this.itemTextAppearanceActive = textAppearanceRes;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setTextAppearanceActive(textAppearanceRes);
                }
            }
        }
    }

    public void setItemTextAppearanceActiveBoldEnabled(boolean isBold) {
        this.itemTextAppearanceActiveBoldEnabled = isBold;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setTextAppearanceActiveBoldEnabled(isBold);
                }
            }
        }
    }

    public int getItemTextAppearanceActive() {
        return this.itemTextAppearanceActive;
    }

    public void setHorizontalItemTextAppearanceInactive(int textAppearanceRes) {
        this.horizontalItemTextAppearanceInactive = textAppearanceRes;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setHorizontalTextAppearanceInactive(textAppearanceRes);
                }
            }
        }
    }

    public int getHorizontalItemTextAppearanceInactive() {
        return this.horizontalItemTextAppearanceInactive;
    }

    public void setHorizontalItemTextAppearanceActive(int textAppearanceRes) {
        this.horizontalItemTextAppearanceActive = textAppearanceRes;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setHorizontalTextAppearanceActive(textAppearanceRes);
                }
            }
        }
    }

    public int getHorizontalItemTextAppearanceActive() {
        return this.horizontalItemTextAppearanceActive;
    }

    public void setItemBackgroundRes(int background) {
        this.itemBackgroundRes = background;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setItemBackground(background);
                }
            }
        }
    }

    public int getItemPaddingTop() {
        return this.itemPaddingTop;
    }

    public void setItemPaddingTop(int paddingTop) {
        this.itemPaddingTop = paddingTop;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setItemPaddingTop(paddingTop);
                }
            }
        }
    }

    public int getItemPaddingBottom() {
        return this.itemPaddingBottom;
    }

    public void setItemPaddingBottom(int paddingBottom) {
        this.itemPaddingBottom = paddingBottom;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setItemPaddingBottom(this.itemPaddingBottom);
                }
            }
        }
    }

    public void setMeasurePaddingFromLabelBaseline(boolean measurePaddingFromLabelBaseline2) {
        this.measurePaddingFromLabelBaseline = measurePaddingFromLabelBaseline2;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setMeasureBottomPaddingFromLabelBaseline(measurePaddingFromLabelBaseline2);
                }
            }
        }
    }

    public void setLabelFontScalingEnabled(boolean scaleLabelWithFont2) {
        this.scaleLabelWithFont = scaleLabelWithFont2;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setLabelFontScalingEnabled(scaleLabelWithFont2);
                }
            }
        }
    }

    public boolean getScaleLabelTextWithFont() {
        return this.scaleLabelWithFont;
    }

    public void setLabelMaxLines(int labelMaxLines2) {
        this.labelMaxLines = labelMaxLines2;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setLabelMaxLines(labelMaxLines2);
                }
            }
        }
    }

    public int getLabelMaxLines() {
        return this.labelMaxLines;
    }

    public int getActiveIndicatorLabelPadding() {
        return this.itemActiveIndicatorLabelPadding;
    }

    public void setActiveIndicatorLabelPadding(int activeIndicatorLabelPadding) {
        this.itemActiveIndicatorLabelPadding = activeIndicatorLabelPadding;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorLabelPadding(activeIndicatorLabelPadding);
                }
            }
        }
    }

    public int getIconLabelHorizontalSpacing() {
        return this.iconLabelHorizontalSpacing;
    }

    public void setIconLabelHorizontalSpacing(int iconLabelHorizontalSpacing2) {
        this.iconLabelHorizontalSpacing = iconLabelHorizontalSpacing2;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setIconLabelHorizontalSpacing(iconLabelHorizontalSpacing2);
                }
            }
        }
    }

    public boolean getItemActiveIndicatorEnabled() {
        return this.itemActiveIndicatorEnabled;
    }

    public void setItemActiveIndicatorEnabled(boolean enabled) {
        this.itemActiveIndicatorEnabled = enabled;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorEnabled(enabled);
                }
            }
        }
    }

    public int getItemActiveIndicatorWidth() {
        return this.itemActiveIndicatorWidth;
    }

    public void setItemActiveIndicatorWidth(int width) {
        this.itemActiveIndicatorWidth = width;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorWidth(width);
                }
            }
        }
    }

    public int getItemActiveIndicatorHeight() {
        return this.itemActiveIndicatorHeight;
    }

    public void setItemActiveIndicatorHeight(int height) {
        this.itemActiveIndicatorHeight = height;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorHeight(height);
                }
            }
        }
    }

    public void setItemGravity(int itemGravity2) {
        this.itemGravity = itemGravity2;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setItemGravity(itemGravity2);
                }
            }
        }
    }

    public int getItemGravity() {
        return this.itemGravity;
    }

    public int getItemActiveIndicatorExpandedWidth() {
        return this.itemActiveIndicatorExpandedWidth;
    }

    public void setItemActiveIndicatorExpandedWidth(int width) {
        this.itemActiveIndicatorExpandedWidth = width;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorExpandedWidth(width);
                }
            }
        }
    }

    public int getItemActiveIndicatorExpandedHeight() {
        return this.itemActiveIndicatorExpandedHeight;
    }

    public void setItemActiveIndicatorExpandedHeight(int height) {
        this.itemActiveIndicatorExpandedHeight = height;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorExpandedHeight(height);
                }
            }
        }
    }

    public int getItemActiveIndicatorMarginHorizontal() {
        return this.itemActiveIndicatorMarginHorizontal;
    }

    public void setItemActiveIndicatorMarginHorizontal(int marginHorizontal) {
        this.itemActiveIndicatorMarginHorizontal = marginHorizontal;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorMarginHorizontal(marginHorizontal);
                }
            }
        }
    }

    public int getItemActiveIndicatorExpandedMarginHorizontal() {
        return this.itemActiveIndicatorExpandedMarginHorizontal;
    }

    public void setItemActiveIndicatorExpandedMarginHorizontal(int marginHorizontal) {
        this.itemActiveIndicatorExpandedMarginHorizontal = marginHorizontal;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorExpandedMarginHorizontal(marginHorizontal);
                }
            }
        }
    }

    public void setItemActiveIndicatorExpandedPadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        this.itemActiveIndicatorExpandedPadding.left = paddingLeft;
        this.itemActiveIndicatorExpandedPadding.top = paddingTop;
        this.itemActiveIndicatorExpandedPadding.right = paddingRight;
        this.itemActiveIndicatorExpandedPadding.bottom = paddingBottom;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorExpandedPadding(this.itemActiveIndicatorExpandedPadding);
                }
            }
        }
    }

    public ShapeAppearanceModel getItemActiveIndicatorShapeAppearance() {
        return this.itemActiveIndicatorShapeAppearance;
    }

    public void setItemActiveIndicatorShapeAppearance(ShapeAppearanceModel shapeAppearance) {
        this.itemActiveIndicatorShapeAppearance = shapeAppearance;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorDrawable(createItemActiveIndicatorDrawable());
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean isItemActiveIndicatorResizeable() {
        return this.itemActiveIndicatorResizeable;
    }

    /* access modifiers changed from: protected */
    public void setItemActiveIndicatorResizeable(boolean resizeable) {
        this.itemActiveIndicatorResizeable = resizeable;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorResizeable(resizeable);
                }
            }
        }
    }

    public ColorStateList getItemActiveIndicatorColor() {
        return this.itemActiveIndicatorColor;
    }

    public void setItemActiveIndicatorColor(ColorStateList csl) {
        this.itemActiveIndicatorColor = csl;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setActiveIndicatorDrawable(createItemActiveIndicatorDrawable());
                }
            }
        }
    }

    private Drawable createItemActiveIndicatorDrawable() {
        if (this.itemActiveIndicatorShapeAppearance == null || this.itemActiveIndicatorColor == null) {
            return null;
        }
        MaterialShapeDrawable drawable = new MaterialShapeDrawable(this.itemActiveIndicatorShapeAppearance);
        drawable.setFillColor(this.itemActiveIndicatorColor);
        return drawable;
    }

    @Deprecated
    public int getItemBackgroundRes() {
        return this.itemBackgroundRes;
    }

    public void setItemBackground(Drawable background) {
        this.itemBackground = background;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setItemBackground(background);
                }
            }
        }
    }

    public void setItemRippleColor(ColorStateList itemRippleColor2) {
        this.itemRippleColor = itemRippleColor2;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setItemRippleColor(itemRippleColor2);
                }
            }
        }
    }

    public ColorStateList getItemRippleColor() {
        return this.itemRippleColor;
    }

    public Drawable getItemBackground() {
        if (this.buttons != null && this.buttons.length > 0) {
            for (NavigationBarMenuItemView button : this.buttons) {
                if (button instanceof NavigationBarItemView) {
                    return ((NavigationBarItemView) button).getBackground();
                }
            }
        }
        return this.itemBackground;
    }

    public void setLabelVisibilityMode(int labelVisibilityMode2) {
        this.labelVisibilityMode = labelVisibilityMode2;
    }

    public int getLabelVisibilityMode() {
        return this.labelVisibilityMode;
    }

    public void setItemIconGravity(int itemIconGravity2) {
        this.itemIconGravity = itemIconGravity2;
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).setItemIconGravity(itemIconGravity2);
                }
            }
        }
    }

    public int getItemIconGravity() {
        return this.itemIconGravity;
    }

    public void setItemOnTouchListener(int menuItemId, View.OnTouchListener onTouchListener) {
        if (onTouchListener == null) {
            this.onTouchListeners.remove(menuItemId);
        } else {
            this.onTouchListeners.put(menuItemId, onTouchListener);
        }
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if ((item instanceof NavigationBarItemView) && item.getItemData() != null && item.getItemData().getItemId() == menuItemId) {
                    ((NavigationBarItemView) item).setOnTouchListener(onTouchListener);
                }
            }
        }
    }

    public ColorStateList createDefaultColorStateList(int baseColorThemeAttr) {
        TypedValue value = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(baseColorThemeAttr, value, true)) {
            return null;
        }
        ColorStateList baseColor = AppCompatResources.getColorStateList(getContext(), value.resourceId);
        if (!getContext().getTheme().resolveAttribute(androidx.appcompat.R.attr.colorPrimary, value, true)) {
            return null;
        }
        int colorPrimary = value.data;
        int defaultColor = baseColor.getDefaultColor();
        return new ColorStateList(new int[][]{DISABLED_STATE_SET, CHECKED_STATE_SET, EMPTY_STATE_SET}, new int[]{baseColor.getColorForState(DISABLED_STATE_SET, defaultColor), colorPrimary, defaultColor});
    }

    public void setPresenter(NavigationBarPresenter presenter2) {
        this.presenter = presenter2;
    }

    private void releaseItemPool() {
        if (this.buttons != null && this.itemPool != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    this.itemPool.release((NavigationBarItemView) item);
                    ((NavigationBarItemView) item).clear();
                }
            }
        }
    }

    private NavigationBarItemView createMenuItem(int index, MenuItemImpl item, boolean shifting, boolean hideWhenCollapsed) {
        this.presenter.setUpdateSuspended(true);
        item.setCheckable(true);
        this.presenter.setUpdateSuspended(false);
        NavigationBarItemView child = getNewItem();
        child.setShifting(shifting);
        child.setLabelMaxLines(this.labelMaxLines);
        child.setIconTintList(this.itemIconTint);
        child.setIconSize(this.itemIconSize);
        child.setTextColor(this.itemTextColorDefault);
        child.setTextAppearanceInactive(this.itemTextAppearanceInactive);
        child.setTextAppearanceActive(this.itemTextAppearanceActive);
        child.setHorizontalTextAppearanceInactive(this.horizontalItemTextAppearanceInactive);
        child.setHorizontalTextAppearanceActive(this.horizontalItemTextAppearanceActive);
        child.setTextAppearanceActiveBoldEnabled(this.itemTextAppearanceActiveBoldEnabled);
        child.setTextColor(this.itemTextColorFromUser);
        if (this.itemPaddingTop != -1) {
            child.setItemPaddingTop(this.itemPaddingTop);
        }
        if (this.itemPaddingBottom != -1) {
            child.setItemPaddingBottom(this.itemPaddingBottom);
        }
        child.setMeasureBottomPaddingFromLabelBaseline(this.measurePaddingFromLabelBaseline);
        child.setLabelFontScalingEnabled(this.scaleLabelWithFont);
        if (this.itemActiveIndicatorLabelPadding != -1) {
            child.setActiveIndicatorLabelPadding(this.itemActiveIndicatorLabelPadding);
        }
        if (this.iconLabelHorizontalSpacing != -1) {
            child.setIconLabelHorizontalSpacing(this.iconLabelHorizontalSpacing);
        }
        child.setActiveIndicatorWidth(this.itemActiveIndicatorWidth);
        child.setActiveIndicatorHeight(this.itemActiveIndicatorHeight);
        child.setActiveIndicatorExpandedWidth(this.itemActiveIndicatorExpandedWidth);
        child.setActiveIndicatorExpandedHeight(this.itemActiveIndicatorExpandedHeight);
        child.setActiveIndicatorMarginHorizontal(this.itemActiveIndicatorMarginHorizontal);
        child.setItemGravity(this.itemGravity);
        child.setActiveIndicatorExpandedPadding(this.itemActiveIndicatorExpandedPadding);
        child.setActiveIndicatorExpandedMarginHorizontal(this.itemActiveIndicatorExpandedMarginHorizontal);
        child.setActiveIndicatorDrawable(createItemActiveIndicatorDrawable());
        child.setActiveIndicatorResizeable(this.itemActiveIndicatorResizeable);
        child.setActiveIndicatorEnabled(this.itemActiveIndicatorEnabled);
        if (this.itemBackground != null) {
            child.setItemBackground(this.itemBackground);
        } else {
            child.setItemBackground(this.itemBackgroundRes);
        }
        child.setItemRippleColor(this.itemRippleColor);
        child.setLabelVisibilityMode(this.labelVisibilityMode);
        child.setItemIconGravity(this.itemIconGravity);
        child.setOnlyShowWhenExpanded(hideWhenCollapsed);
        child.setExpanded(this.expanded);
        child.initialize(item, 0);
        child.setItemPosition(index);
        int itemId = item.getItemId();
        child.setOnTouchListener(this.onTouchListeners.get(itemId));
        child.setOnClickListener(this.onClickListener);
        if (this.selectedItemId != 0 && itemId == this.selectedItemId) {
            this.selectedItemPosition = index;
        }
        setBadgeIfNeeded(child);
        return child;
    }

    public void buildMenuView() {
        NavigationBarMenuItemView child;
        removeAllViews();
        releaseItemPool();
        this.presenter.setUpdateSuspended(true);
        this.menu.refreshItems();
        this.presenter.setUpdateSuspended(false);
        int contentItemCount = this.menu.getContentItemCount();
        if (contentItemCount == 0) {
            this.selectedItemId = 0;
            this.selectedItemPosition = 0;
            this.buttons = null;
            this.itemPool = null;
            return;
        }
        if (this.itemPool == null || this.itemPoolSize != contentItemCount) {
            this.itemPoolSize = contentItemCount;
            this.itemPool = new Pools.SynchronizedPool(contentItemCount);
        }
        removeUnusedBadges();
        int menuSize = this.menu.size();
        this.buttons = new NavigationBarMenuItemView[menuSize];
        int collapsedItemsSoFar = 0;
        int nextSubheaderItemCount = 0;
        boolean shifting = isShifting(this.labelVisibilityMode, getCurrentVisibleContentItemCount());
        for (int i = 0; i < menuSize; i++) {
            MenuItem menuItem = this.menu.getItemAt(i);
            if (menuItem instanceof DividerMenuItem) {
                child = new NavigationBarDividerView(getContext());
                child.setOnlyShowWhenExpanded(true);
                ((NavigationBarDividerView) child).setDividersEnabled(this.dividersEnabled);
            } else if (menuItem.hasSubMenu()) {
                if (nextSubheaderItemCount <= 0) {
                    child = new NavigationBarSubheaderView(getContext());
                    ((NavigationBarSubheaderView) child).setTextAppearance(this.horizontalItemTextAppearanceActive != 0 ? this.horizontalItemTextAppearanceActive : this.itemTextAppearanceActive);
                    ((NavigationBarSubheaderView) child).setTextColor(this.itemTextColorFromUser);
                    child.setOnlyShowWhenExpanded(true);
                    child.initialize((MenuItemImpl) menuItem, 0);
                    nextSubheaderItemCount = menuItem.getSubMenu().size();
                } else {
                    throw new IllegalArgumentException("Only one layer of submenu is supported; a submenu inside a submenu is not supported by the Navigation Bar.");
                }
            } else if (nextSubheaderItemCount > 0) {
                child = createMenuItem(i, (MenuItemImpl) menuItem, shifting, true);
                nextSubheaderItemCount--;
            } else {
                child = createMenuItem(i, (MenuItemImpl) menuItem, shifting, collapsedItemsSoFar >= this.collapsedMaxItemCount);
                collapsedItemsSoFar++;
            }
            if (!(menuItem instanceof DividerMenuItem) && menuItem.isCheckable() && this.selectedItemPosition == -1) {
                this.selectedItemPosition = i;
            }
            this.buttons[i] = child;
            addView((View) child);
        }
        this.selectedItemPosition = Math.min(menuSize - 1, this.selectedItemPosition);
        setCheckedItem(this.buttons[this.selectedItemPosition].getItemData());
    }

    private boolean isMenuStructureSame() {
        if (this.buttons == null || this.menu == null || this.menu.size() != this.buttons.length) {
            return false;
        }
        int i = 0;
        while (true) {
            boolean incorrectItemType = true;
            if (i >= this.buttons.length) {
                return true;
            }
            if ((this.menu.getItemAt(i) instanceof DividerMenuItem) && !(this.buttons[i] instanceof NavigationBarDividerView)) {
                return false;
            }
            boolean incorrectSubheaderType = this.menu.getItemAt(i).hasSubMenu() && !(this.buttons[i] instanceof NavigationBarSubheaderView);
            if (this.menu.getItemAt(i).hasSubMenu() || (this.buttons[i] instanceof NavigationBarItemView)) {
                incorrectItemType = false;
            }
            if ((this.menu.getItemAt(i) instanceof DividerMenuItem) || (!incorrectSubheaderType && !incorrectItemType)) {
                i++;
            }
        }
        return false;
    }

    public void updateMenuView() {
        if (this.menu != null && this.buttons != null) {
            this.presenter.setUpdateSuspended(true);
            this.menu.refreshItems();
            this.presenter.setUpdateSuspended(false);
            if (!isMenuStructureSame()) {
                buildMenuView();
                return;
            }
            int previousSelectedId = this.selectedItemId;
            int menuSize = this.menu.size();
            for (int i = 0; i < menuSize; i++) {
                MenuItem item = this.menu.getItemAt(i);
                if (item.isChecked()) {
                    setCheckedItem(item);
                    this.selectedItemId = item.getItemId();
                    this.selectedItemPosition = i;
                }
            }
            if (!(previousSelectedId == this.selectedItemId || this.set == null)) {
                TransitionManager.beginDelayedTransition(this, this.set);
            }
            boolean shifting = isShifting(this.labelVisibilityMode, getCurrentVisibleContentItemCount());
            for (int i2 = 0; i2 < menuSize; i2++) {
                this.presenter.setUpdateSuspended(true);
                this.buttons[i2].setExpanded(this.expanded);
                if (this.buttons[i2] instanceof NavigationBarItemView) {
                    NavigationBarItemView itemView = (NavigationBarItemView) this.buttons[i2];
                    itemView.setLabelVisibilityMode(this.labelVisibilityMode);
                    itemView.setItemIconGravity(this.itemIconGravity);
                    itemView.setItemGravity(this.itemGravity);
                    itemView.setShifting(shifting);
                }
                if (this.menu.getItemAt(i2) instanceof MenuItemImpl) {
                    this.buttons[i2].initialize((MenuItemImpl) this.menu.getItemAt(i2), 0);
                }
                this.presenter.setUpdateSuspended(false);
            }
        }
    }

    private NavigationBarItemView getNewItem() {
        NavigationBarItemView item = this.itemPool != null ? this.itemPool.acquire() : null;
        if (item == null) {
            return createNavigationBarItemView(getContext());
        }
        return item;
    }

    public void setSubmenuDividersEnabled(boolean dividersEnabled2) {
        if (this.dividersEnabled != dividersEnabled2) {
            this.dividersEnabled = dividersEnabled2;
            if (this.buttons != null) {
                for (NavigationBarMenuItemView itemView : this.buttons) {
                    if (itemView instanceof NavigationBarDividerView) {
                        ((NavigationBarDividerView) itemView).setDividersEnabled(dividersEnabled2);
                    }
                }
            }
        }
    }

    public void setCollapsedMaxItemCount(int collapsedMaxCount) {
        this.collapsedMaxItemCount = collapsedMaxCount;
    }

    private int getCollapsedVisibleItemCount() {
        return Math.min(this.collapsedMaxItemCount, this.menu.getVisibleMainContentItemCount());
    }

    public int getCurrentVisibleContentItemCount() {
        return this.expanded ? this.menu.getVisibleContentItemCount() : getCollapsedVisibleItemCount();
    }

    public int getSelectedItemId() {
        return this.selectedItemId;
    }

    /* access modifiers changed from: protected */
    public boolean isShifting(int labelVisibilityMode2, int childCount) {
        if (labelVisibilityMode2 == -1) {
            if (childCount > 3) {
                return true;
            }
            return false;
        } else if (labelVisibilityMode2 == 0) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public void tryRestoreSelectedItemId(int itemId) {
        int size = this.menu.size();
        for (int i = 0; i < size; i++) {
            MenuItem item = this.menu.getItemAt(i);
            if (itemId == item.getItemId()) {
                this.selectedItemId = itemId;
                this.selectedItemPosition = i;
                setCheckedItem(item);
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public SparseArray<BadgeDrawable> getBadgeDrawables() {
        return this.badgeDrawables;
    }

    /* access modifiers changed from: package-private */
    public void restoreBadgeDrawables(SparseArray<BadgeDrawable> badgeDrawables2) {
        BadgeDrawable badge;
        for (int i = 0; i < badgeDrawables2.size(); i++) {
            int key = badgeDrawables2.keyAt(i);
            if (this.badgeDrawables.indexOfKey(key) < 0) {
                this.badgeDrawables.append(key, badgeDrawables2.get(key));
            }
        }
        if (this.buttons != null) {
            for (NavigationBarMenuItemView itemView : this.buttons) {
                if ((itemView instanceof NavigationBarItemView) && (badge = this.badgeDrawables.get(((NavigationBarItemView) itemView).getId())) != null) {
                    ((NavigationBarItemView) itemView).setBadge(badge);
                }
            }
        }
    }

    public BadgeDrawable getBadge(int menuItemId) {
        return this.badgeDrawables.get(menuItemId);
    }

    /* access modifiers changed from: package-private */
    public BadgeDrawable getOrCreateBadge(int menuItemId) {
        validateMenuItemId(menuItemId);
        BadgeDrawable badgeDrawable = this.badgeDrawables.get(menuItemId);
        if (badgeDrawable == null) {
            badgeDrawable = BadgeDrawable.create(getContext());
            this.badgeDrawables.put(menuItemId, badgeDrawable);
        }
        NavigationBarItemView itemView = findItemView(menuItemId);
        if (itemView != null) {
            itemView.setBadge(badgeDrawable);
        }
        return badgeDrawable;
    }

    /* access modifiers changed from: package-private */
    public void removeBadge(int menuItemId) {
        validateMenuItemId(menuItemId);
        NavigationBarItemView itemView = findItemView(menuItemId);
        if (itemView != null) {
            itemView.removeBadge();
        }
        this.badgeDrawables.put(menuItemId, (Object) null);
    }

    private void setBadgeIfNeeded(NavigationBarItemView child) {
        BadgeDrawable badgeDrawable;
        int childId = child.getId();
        if (isValidId(childId) && (badgeDrawable = this.badgeDrawables.get(childId)) != null) {
            child.setBadge(badgeDrawable);
        }
    }

    private void removeUnusedBadges() {
        HashSet<Integer> activeKeys = new HashSet<>();
        for (int i = 0; i < this.menu.size(); i++) {
            activeKeys.add(Integer.valueOf(this.menu.getItemAt(i).getItemId()));
        }
        for (int i2 = 0; i2 < this.badgeDrawables.size(); i2++) {
            int key = this.badgeDrawables.keyAt(i2);
            if (!activeKeys.contains(Integer.valueOf(key))) {
                this.badgeDrawables.delete(key);
            }
        }
    }

    public NavigationBarItemView findItemView(int menuItemId) {
        validateMenuItemId(menuItemId);
        if (this.buttons == null) {
            return null;
        }
        for (NavigationBarMenuItemView itemView : this.buttons) {
            if ((itemView instanceof NavigationBarItemView) && ((NavigationBarItemView) itemView).getId() == menuItemId) {
                return (NavigationBarItemView) itemView;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public int getSelectedItemPosition() {
        return this.selectedItemPosition;
    }

    /* access modifiers changed from: protected */
    public NavigationBarMenuBuilder getMenu() {
        return this.menu;
    }

    private boolean isValidId(int viewId) {
        return viewId != -1;
    }

    private void validateMenuItemId(int viewId) {
        if (!isValidId(viewId)) {
            throw new IllegalArgumentException(viewId + " is not a valid view id");
        }
    }

    public void updateActiveIndicator(int availableWidth) {
        if (this.buttons != null) {
            for (NavigationBarMenuItemView item : this.buttons) {
                if (item instanceof NavigationBarItemView) {
                    ((NavigationBarItemView) item).updateActiveIndicatorLayoutParams(availableWidth);
                }
            }
        }
    }
}
