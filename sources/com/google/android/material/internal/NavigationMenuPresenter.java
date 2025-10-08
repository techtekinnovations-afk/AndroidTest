package com.google.android.material.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;
import com.google.android.material.R;
import java.util.ArrayList;

public class NavigationMenuPresenter implements MenuPresenter {
    public static final int NO_TEXT_APPEARANCE_SET = 0;
    private static final String STATE_ADAPTER = "android:menu:adapter";
    private static final String STATE_HEADER = "android:menu:header";
    private static final String STATE_HIERARCHY = "android:menu:list";
    NavigationMenuAdapter adapter;
    private MenuPresenter.Callback callback;
    int dividerInsetEnd;
    int dividerInsetStart;
    boolean hasCustomItemIconSize;
    LinearLayout headerLayout;
    ColorStateList iconTintList;
    private int id;
    boolean isBehindStatusBar = true;
    Drawable itemBackground;
    RippleDrawable itemForeground;
    int itemHorizontalPadding;
    int itemIconPadding;
    int itemIconSize;
    /* access modifiers changed from: private */
    public int itemMaxLines;
    int itemVerticalPadding;
    LayoutInflater layoutInflater;
    MenuBuilder menu;
    private NavigationMenuView menuView;
    final View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View view) {
            NavigationMenuPresenter.this.setUpdateSuspended(true);
            MenuItemImpl item = ((NavigationMenuItemView) view).getItemData();
            boolean result = NavigationMenuPresenter.this.menu.performItemAction(item, NavigationMenuPresenter.this, 0);
            boolean checkStateChanged = false;
            if (item != null && item.isCheckable() && result) {
                NavigationMenuPresenter.this.adapter.setCheckedItem(item);
                checkStateChanged = true;
            }
            NavigationMenuPresenter.this.setUpdateSuspended(false);
            if (checkStateChanged) {
                NavigationMenuPresenter.this.updateMenuView(false);
            }
        }
    };
    private int overScrollMode = -1;
    int paddingSeparator;
    private int paddingTopDefault;
    ColorStateList subheaderColor;
    int subheaderInsetEnd;
    int subheaderInsetStart;
    int subheaderTextAppearance = 0;
    int textAppearance = 0;
    boolean textAppearanceActiveBoldEnabled = true;
    ColorStateList textColor;

    private interface NavigationMenuItem {
    }

    public void initForMenu(Context context, MenuBuilder menu2) {
        this.layoutInflater = LayoutInflater.from(context);
        this.menu = menu2;
        this.paddingSeparator = context.getResources().getDimensionPixelOffset(R.dimen.design_navigation_separator_vertical_padding);
    }

    public MenuView getMenuView(ViewGroup root) {
        if (this.menuView == null) {
            this.menuView = (NavigationMenuView) this.layoutInflater.inflate(R.layout.design_navigation_menu, root, false);
            this.menuView.setAccessibilityDelegateCompat(new NavigationMenuViewAccessibilityDelegate(this.menuView));
            if (this.adapter == null) {
                this.adapter = new NavigationMenuAdapter();
                this.adapter.setHasStableIds(true);
            }
            if (this.overScrollMode != -1) {
                this.menuView.setOverScrollMode(this.overScrollMode);
            }
            this.headerLayout = (LinearLayout) this.layoutInflater.inflate(R.layout.design_navigation_item_header, this.menuView, false);
            this.headerLayout.setImportantForAccessibility(2);
            this.menuView.setAdapter(this.adapter);
        }
        return this.menuView;
    }

    private void updateAllTextMenuItems() {
        if (this.adapter != null) {
            this.adapter.updateAllTextMenuItems();
        }
    }

    private void updateAllSubHeaderMenuItems() {
        if (this.adapter != null) {
            this.adapter.updateAllSubHeaderMenuItems();
        }
    }

    private void updateAllDividerMenuItems() {
        if (this.adapter != null) {
            this.adapter.updateAllDividerMenuItems();
        }
    }

    public void updateMenuView(boolean cleared) {
        if (this.adapter != null) {
            this.adapter.update();
        }
    }

    public void setCallback(MenuPresenter.Callback cb) {
        this.callback = cb;
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
        return false;
    }

    public void onCloseMenu(MenuBuilder menu2, boolean allMenusAreClosing) {
        if (this.callback != null) {
            this.callback.onCloseMenu(menu2, allMenusAreClosing);
        }
    }

    public boolean flagActionItems() {
        return false;
    }

    public boolean expandItemActionView(MenuBuilder menu2, MenuItemImpl item) {
        return false;
    }

    public boolean collapseItemActionView(MenuBuilder menu2, MenuItemImpl item) {
        return false;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public Parcelable onSaveInstanceState() {
        Bundle state = new Bundle();
        if (this.menuView != null) {
            SparseArray<Parcelable> hierarchy = new SparseArray<>();
            this.menuView.saveHierarchyState(hierarchy);
            state.putSparseParcelableArray("android:menu:list", hierarchy);
        }
        if (this.adapter != null) {
            state.putBundle(STATE_ADAPTER, this.adapter.createInstanceState());
        }
        if (this.headerLayout != null) {
            SparseArray<Parcelable> header = new SparseArray<>();
            this.headerLayout.saveHierarchyState(header);
            state.putSparseParcelableArray(STATE_HEADER, header);
        }
        return state;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle state = (Bundle) parcelable;
            SparseArray<Parcelable> hierarchy = state.getSparseParcelableArray("android:menu:list");
            if (hierarchy != null) {
                this.menuView.restoreHierarchyState(hierarchy);
            }
            Bundle adapterState = state.getBundle(STATE_ADAPTER);
            if (adapterState != null) {
                this.adapter.restoreInstanceState(adapterState);
            }
            SparseArray<Parcelable> header = state.getSparseParcelableArray(STATE_HEADER);
            if (header != null) {
                this.headerLayout.restoreHierarchyState(header);
            }
        }
    }

    public void setCheckedItem(MenuItemImpl item) {
        this.adapter.setCheckedItem(item);
    }

    public MenuItemImpl getCheckedItem() {
        return this.adapter.getCheckedItem();
    }

    public View inflateHeaderView(int res) {
        View view = this.layoutInflater.inflate(res, this.headerLayout, false);
        addHeaderView(view);
        return view;
    }

    public void addHeaderView(View view) {
        this.headerLayout.addView(view);
        this.menuView.setPadding(0, 0, 0, this.menuView.getPaddingBottom());
    }

    public void removeHeaderView(View view) {
        this.headerLayout.removeView(view);
        if (!hasHeader()) {
            this.menuView.setPadding(0, this.paddingTopDefault, 0, this.menuView.getPaddingBottom());
        }
    }

    public int getHeaderCount() {
        return this.headerLayout.getChildCount();
    }

    private boolean hasHeader() {
        return getHeaderCount() > 0;
    }

    public View getHeaderView(int index) {
        return this.headerLayout.getChildAt(index);
    }

    public void setSubheaderColor(ColorStateList subheaderColor2) {
        this.subheaderColor = subheaderColor2;
        updateAllSubHeaderMenuItems();
    }

    public void setSubheaderTextAppearance(int resId) {
        this.subheaderTextAppearance = resId;
        updateAllSubHeaderMenuItems();
    }

    public ColorStateList getItemTintList() {
        return this.iconTintList;
    }

    public void setItemIconTintList(ColorStateList tint) {
        this.iconTintList = tint;
        updateAllTextMenuItems();
    }

    public ColorStateList getItemTextColor() {
        return this.textColor;
    }

    public void setItemTextColor(ColorStateList textColor2) {
        this.textColor = textColor2;
        updateAllTextMenuItems();
    }

    public void setItemTextAppearance(int resId) {
        this.textAppearance = resId;
        updateAllTextMenuItems();
    }

    public void setItemTextAppearanceActiveBoldEnabled(boolean isBold) {
        this.textAppearanceActiveBoldEnabled = isBold;
        updateAllTextMenuItems();
    }

    public Drawable getItemBackground() {
        return this.itemBackground;
    }

    public void setItemBackground(Drawable itemBackground2) {
        this.itemBackground = itemBackground2;
        updateAllTextMenuItems();
    }

    public void setItemForeground(RippleDrawable itemForeground2) {
        this.itemForeground = itemForeground2;
        updateAllTextMenuItems();
    }

    public int getItemHorizontalPadding() {
        return this.itemHorizontalPadding;
    }

    public void setItemHorizontalPadding(int itemHorizontalPadding2) {
        this.itemHorizontalPadding = itemHorizontalPadding2;
        updateAllTextMenuItems();
    }

    public int getItemVerticalPadding() {
        return this.itemVerticalPadding;
    }

    public void setItemVerticalPadding(int itemVerticalPadding2) {
        this.itemVerticalPadding = itemVerticalPadding2;
        updateAllTextMenuItems();
    }

    public int getDividerInsetStart() {
        return this.dividerInsetStart;
    }

    public void setDividerInsetStart(int dividerInsetStart2) {
        this.dividerInsetStart = dividerInsetStart2;
        updateAllDividerMenuItems();
    }

    public int getDividerInsetEnd() {
        return this.dividerInsetEnd;
    }

    public void setDividerInsetEnd(int dividerInsetEnd2) {
        this.dividerInsetEnd = dividerInsetEnd2;
        updateAllDividerMenuItems();
    }

    public int getSubheaderInsetStart() {
        return this.subheaderInsetStart;
    }

    public void setSubheaderInsetStart(int subheaderInsetStart2) {
        this.subheaderInsetStart = subheaderInsetStart2;
        updateAllSubHeaderMenuItems();
    }

    public int getSubheaderInsetEnd() {
        return this.subheaderInsetEnd;
    }

    public void setSubheaderInsetEnd(int subheaderInsetEnd2) {
        this.subheaderInsetEnd = subheaderInsetEnd2;
        updateAllSubHeaderMenuItems();
    }

    public int getItemIconPadding() {
        return this.itemIconPadding;
    }

    public void setItemIconPadding(int itemIconPadding2) {
        this.itemIconPadding = itemIconPadding2;
        updateAllTextMenuItems();
    }

    public void setItemMaxLines(int itemMaxLines2) {
        this.itemMaxLines = itemMaxLines2;
        updateAllTextMenuItems();
    }

    public int getItemMaxLines() {
        return this.itemMaxLines;
    }

    public void setItemIconSize(int itemIconSize2) {
        if (this.itemIconSize != itemIconSize2) {
            this.itemIconSize = itemIconSize2;
            this.hasCustomItemIconSize = true;
            updateAllTextMenuItems();
        }
    }

    public void setUpdateSuspended(boolean updateSuspended) {
        if (this.adapter != null) {
            this.adapter.setUpdateSuspended(updateSuspended);
        }
    }

    public void setBehindStatusBar(boolean behindStatusBar) {
        if (this.isBehindStatusBar != behindStatusBar) {
            this.isBehindStatusBar = behindStatusBar;
            updateTopPadding();
        }
    }

    public boolean isBehindStatusBar() {
        return this.isBehindStatusBar;
    }

    private void updateTopPadding() {
        int topPadding = 0;
        if (!hasHeader() && this.isBehindStatusBar) {
            topPadding = this.paddingTopDefault;
        }
        this.menuView.setPadding(0, topPadding, 0, this.menuView.getPaddingBottom());
    }

    public void dispatchApplyWindowInsets(WindowInsetsCompat insets) {
        int top = insets.getSystemWindowInsetTop();
        if (this.paddingTopDefault != top) {
            this.paddingTopDefault = top;
            updateTopPadding();
        }
        this.menuView.setPadding(0, this.menuView.getPaddingTop(), 0, insets.getSystemWindowInsetBottom());
        ViewCompat.dispatchApplyWindowInsets(this.headerLayout, insets);
    }

    public void setOverScrollMode(int overScrollMode2) {
        this.overScrollMode = overScrollMode2;
        if (this.menuView != null) {
            this.menuView.setOverScrollMode(overScrollMode2);
        }
    }

    private static abstract class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class NormalViewHolder extends ViewHolder {
        public NormalViewHolder(LayoutInflater inflater, ViewGroup parent, View.OnClickListener listener) {
            super(inflater.inflate(R.layout.design_navigation_item, parent, false));
            this.itemView.setOnClickListener(listener);
        }
    }

    private static class SubheaderViewHolder extends ViewHolder {
        public SubheaderViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.design_navigation_item_subheader, parent, false));
        }
    }

    private static class SeparatorViewHolder extends ViewHolder {
        public SeparatorViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.design_navigation_item_separator, parent, false));
        }
    }

    private static class HeaderViewHolder extends ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class NavigationMenuAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final String STATE_ACTION_VIEWS = "android:menu:action_views";
        private static final String STATE_CHECKED_ITEM = "android:menu:checked";
        private static final int VIEW_TYPE_HEADER = 3;
        private static final int VIEW_TYPE_NORMAL = 0;
        private static final int VIEW_TYPE_SEPARATOR = 2;
        private static final int VIEW_TYPE_SUBHEADER = 1;
        private MenuItemImpl checkedItem;
        private final ArrayList<NavigationMenuItem> items = new ArrayList<>();
        private boolean updateSuspended;

        NavigationMenuAdapter() {
            prepareMenuItems();
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public int getItemCount() {
            return this.items.size();
        }

        public int getItemViewType(int position) {
            NavigationMenuItem item = this.items.get(position);
            if (item instanceof NavigationMenuSeparatorItem) {
                return 2;
            }
            if (item instanceof NavigationMenuHeaderItem) {
                return 3;
            }
            if (!(item instanceof NavigationMenuTextItem)) {
                throw new RuntimeException("Unknown item type.");
            } else if (((NavigationMenuTextItem) item).getMenuItem().hasSubMenu()) {
                return 1;
            } else {
                return 0;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case 0:
                    return new NormalViewHolder(NavigationMenuPresenter.this.layoutInflater, parent, NavigationMenuPresenter.this.onClickListener);
                case 1:
                    return new SubheaderViewHolder(NavigationMenuPresenter.this.layoutInflater, parent);
                case 2:
                    return new SeparatorViewHolder(NavigationMenuPresenter.this.layoutInflater, parent);
                case 3:
                    return new HeaderViewHolder(NavigationMenuPresenter.this.headerLayout);
                default:
                    return null;
            }
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            switch (getItemViewType(position)) {
                case 0:
                    NavigationMenuItemView itemView = (NavigationMenuItemView) holder.itemView;
                    itemView.setIconTintList(NavigationMenuPresenter.this.iconTintList);
                    itemView.setTextAppearance(NavigationMenuPresenter.this.textAppearance);
                    if (NavigationMenuPresenter.this.textColor != null) {
                        itemView.setTextColor(NavigationMenuPresenter.this.textColor);
                    }
                    itemView.setBackground(NavigationMenuPresenter.this.itemBackground != null ? NavigationMenuPresenter.this.itemBackground.getConstantState().newDrawable() : null);
                    if (NavigationMenuPresenter.this.itemForeground != null) {
                        itemView.setForeground(NavigationMenuPresenter.this.itemForeground.getConstantState().newDrawable());
                    }
                    NavigationMenuTextItem item = (NavigationMenuTextItem) this.items.get(position);
                    itemView.setNeedsEmptyIcon(item.needsEmptyIcon);
                    itemView.setPadding(NavigationMenuPresenter.this.itemHorizontalPadding, NavigationMenuPresenter.this.itemVerticalPadding, NavigationMenuPresenter.this.itemHorizontalPadding, NavigationMenuPresenter.this.itemVerticalPadding);
                    itemView.setIconPadding(NavigationMenuPresenter.this.itemIconPadding);
                    if (NavigationMenuPresenter.this.hasCustomItemIconSize) {
                        itemView.setIconSize(NavigationMenuPresenter.this.itemIconSize);
                    }
                    itemView.setMaxLines(NavigationMenuPresenter.this.itemMaxLines);
                    itemView.initialize(item.getMenuItem(), NavigationMenuPresenter.this.textAppearanceActiveBoldEnabled);
                    setAccessibilityDelegate(itemView, position, false);
                    return;
                case 1:
                    TextView subHeader = (TextView) holder.itemView;
                    subHeader.setText(((NavigationMenuTextItem) this.items.get(position)).getMenuItem().getTitle());
                    TextViewCompat.setTextAppearance(subHeader, NavigationMenuPresenter.this.subheaderTextAppearance);
                    subHeader.setPaddingRelative(NavigationMenuPresenter.this.subheaderInsetStart, subHeader.getPaddingTop(), NavigationMenuPresenter.this.subheaderInsetEnd, subHeader.getPaddingBottom());
                    if (NavigationMenuPresenter.this.subheaderColor != null) {
                        subHeader.setTextColor(NavigationMenuPresenter.this.subheaderColor);
                    }
                    setAccessibilityDelegate(subHeader, position, true);
                    return;
                case 2:
                    NavigationMenuSeparatorItem item2 = (NavigationMenuSeparatorItem) this.items.get(position);
                    holder.itemView.setPaddingRelative(NavigationMenuPresenter.this.dividerInsetStart, item2.getPaddingTop(), NavigationMenuPresenter.this.dividerInsetEnd, item2.getPaddingBottom());
                    return;
                default:
                    return;
            }
        }

        private void setAccessibilityDelegate(View view, final int position, final boolean isHeader) {
            ViewCompat.setAccessibilityDelegate(view, new AccessibilityDelegateCompat() {
                public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
                    super.onInitializeAccessibilityNodeInfo(host, info);
                    info.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(NavigationMenuAdapter.this.adjustItemPositionForA11yDelegate(position), 1, 1, 1, isHeader, host.isSelected()));
                }
            });
        }

        /* access modifiers changed from: private */
        public int adjustItemPositionForA11yDelegate(int position) {
            int adjustedPosition = position;
            for (int i = 0; i < position; i++) {
                if (NavigationMenuPresenter.this.adapter.getItemViewType(i) == 2 || NavigationMenuPresenter.this.adapter.getItemViewType(i) == 3) {
                    adjustedPosition--;
                }
            }
            return adjustedPosition;
        }

        public void onViewRecycled(ViewHolder holder) {
            if (holder instanceof NormalViewHolder) {
                ((NavigationMenuItemView) holder.itemView).recycle();
            }
        }

        public void update() {
            int prevItemSize = this.items.size();
            prepareMenuItems();
            notifyDataSetChanged();
            if (prevItemSize == this.items.size()) {
                notifyItemRangeChanged(0, this.items.size());
            }
        }

        private void prepareMenuItems() {
            if (!this.updateSuspended) {
                this.updateSuspended = true;
                this.items.clear();
                this.items.add(new NavigationMenuHeaderItem());
                int currentGroupId = -1;
                int currentGroupStart = 0;
                boolean currentGroupHasIcon = false;
                int i = 0;
                int totalSize = NavigationMenuPresenter.this.menu.getVisibleItems().size();
                while (true) {
                    boolean z = false;
                    if (i < totalSize) {
                        MenuItemImpl item = NavigationMenuPresenter.this.menu.getVisibleItems().get(i);
                        if (item.isChecked()) {
                            setCheckedItem(item);
                        }
                        if (item.isCheckable()) {
                            item.setExclusiveCheckable(false);
                        }
                        if (item.hasSubMenu()) {
                            SubMenu subMenu = item.getSubMenu();
                            if (subMenu.hasVisibleItems()) {
                                if (i != 0) {
                                    this.items.add(new NavigationMenuSeparatorItem(NavigationMenuPresenter.this.paddingSeparator, 0));
                                }
                                this.items.add(new NavigationMenuTextItem(item));
                                boolean subMenuHasIcon = false;
                                int subMenuStart = this.items.size();
                                int size = subMenu.size();
                                for (int j = 0; j < size; j++) {
                                    MenuItemImpl subMenuItem = (MenuItemImpl) subMenu.getItem(j);
                                    if (subMenuItem.isVisible()) {
                                        if (!subMenuHasIcon && subMenuItem.getIcon() != null) {
                                            subMenuHasIcon = true;
                                        }
                                        if (subMenuItem.isCheckable()) {
                                            subMenuItem.setExclusiveCheckable(false);
                                        }
                                        if (subMenuItem.isChecked()) {
                                            setCheckedItem(subMenuItem);
                                        }
                                        this.items.add(new NavigationMenuTextItem(subMenuItem));
                                    }
                                }
                                if (subMenuHasIcon) {
                                    appendTransparentIconIfMissing(subMenuStart, this.items.size());
                                }
                            }
                        } else {
                            int groupId = item.getGroupId();
                            if (groupId != currentGroupId) {
                                currentGroupStart = this.items.size();
                                if (item.getIcon() != null) {
                                    z = true;
                                }
                                currentGroupHasIcon = z;
                                if (i != 0) {
                                    currentGroupStart++;
                                    this.items.add(new NavigationMenuSeparatorItem(NavigationMenuPresenter.this.paddingSeparator, NavigationMenuPresenter.this.paddingSeparator));
                                }
                            } else if (!currentGroupHasIcon && item.getIcon() != null) {
                                currentGroupHasIcon = true;
                                appendTransparentIconIfMissing(currentGroupStart, this.items.size());
                            }
                            NavigationMenuTextItem textItem = new NavigationMenuTextItem(item);
                            textItem.needsEmptyIcon = currentGroupHasIcon;
                            this.items.add(textItem);
                            currentGroupId = groupId;
                        }
                        i++;
                    } else {
                        this.updateSuspended = false;
                        return;
                    }
                }
            }
        }

        private void appendTransparentIconIfMissing(int startIndex, int endIndex) {
            for (int i = startIndex; i < endIndex; i++) {
                ((NavigationMenuTextItem) this.items.get(i)).needsEmptyIcon = true;
            }
        }

        public void setCheckedItem(MenuItemImpl checkedItem2) {
            if (this.checkedItem != checkedItem2 && checkedItem2.isCheckable()) {
                if (this.checkedItem != null) {
                    this.checkedItem.setChecked(false);
                }
                this.checkedItem = checkedItem2;
                checkedItem2.setChecked(true);
            }
        }

        public MenuItemImpl getCheckedItem() {
            return this.checkedItem;
        }

        public Bundle createInstanceState() {
            Bundle state = new Bundle();
            if (this.checkedItem != null) {
                state.putInt(STATE_CHECKED_ITEM, this.checkedItem.getItemId());
            }
            SparseArray<ParcelableSparseArray> actionViewStates = new SparseArray<>();
            int size = this.items.size();
            for (int i = 0; i < size; i++) {
                NavigationMenuItem navigationMenuItem = this.items.get(i);
                if (navigationMenuItem instanceof NavigationMenuTextItem) {
                    MenuItemImpl item = ((NavigationMenuTextItem) navigationMenuItem).getMenuItem();
                    View actionView = item != null ? item.getActionView() : null;
                    if (actionView != null) {
                        ParcelableSparseArray container = new ParcelableSparseArray();
                        actionView.saveHierarchyState(container);
                        actionViewStates.put(item.getItemId(), container);
                    }
                }
            }
            state.putSparseParcelableArray(STATE_ACTION_VIEWS, actionViewStates);
            return state;
        }

        public void restoreInstanceState(Bundle state) {
            MenuItemImpl item;
            View actionView;
            ParcelableSparseArray container;
            MenuItemImpl menuItem;
            int checkedItem2 = state.getInt(STATE_CHECKED_ITEM, 0);
            if (checkedItem2 != 0) {
                this.updateSuspended = true;
                int i = 0;
                int size = this.items.size();
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    NavigationMenuItem item2 = this.items.get(i);
                    if ((item2 instanceof NavigationMenuTextItem) && (menuItem = ((NavigationMenuTextItem) item2).getMenuItem()) != null && menuItem.getItemId() == checkedItem2) {
                        setCheckedItem(menuItem);
                        break;
                    }
                    i++;
                }
                this.updateSuspended = false;
                prepareMenuItems();
            }
            SparseArray<ParcelableSparseArray> actionViewStates = state.getSparseParcelableArray(STATE_ACTION_VIEWS);
            if (actionViewStates != null) {
                int size2 = this.items.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    NavigationMenuItem navigationMenuItem = this.items.get(i2);
                    if (!(!(navigationMenuItem instanceof NavigationMenuTextItem) || (item = ((NavigationMenuTextItem) navigationMenuItem).getMenuItem()) == null || (actionView = item.getActionView()) == null || (container = actionViewStates.get(item.getItemId())) == null)) {
                        actionView.restoreHierarchyState(container);
                    }
                }
            }
        }

        public void setUpdateSuspended(boolean updateSuspended2) {
            this.updateSuspended = updateSuspended2;
        }

        /* access modifiers changed from: package-private */
        public int getRowCount() {
            int itemCount = 0;
            for (int i = 0; i < NavigationMenuPresenter.this.adapter.getItemCount(); i++) {
                int type = NavigationMenuPresenter.this.adapter.getItemViewType(i);
                if (type == 0 || type == 1) {
                    itemCount++;
                }
            }
            return itemCount;
        }

        /* access modifiers changed from: private */
        public void updateAllTextMenuItems() {
            for (int i = 0; i < this.items.size(); i++) {
                if ((this.items.get(i) instanceof NavigationMenuTextItem) && getItemViewType(i) == 0) {
                    notifyItemChanged(i);
                }
            }
        }

        /* access modifiers changed from: private */
        public void updateAllSubHeaderMenuItems() {
            for (int i = 0; i < this.items.size(); i++) {
                if ((this.items.get(i) instanceof NavigationMenuTextItem) && getItemViewType(i) == 1) {
                    notifyItemChanged(i);
                }
            }
        }

        /* access modifiers changed from: private */
        public void updateAllDividerMenuItems() {
            for (int i = 0; i < this.items.size(); i++) {
                if (this.items.get(i) instanceof NavigationMenuSeparatorItem) {
                    notifyItemChanged(i);
                }
            }
        }
    }

    private static class NavigationMenuTextItem implements NavigationMenuItem {
        private final MenuItemImpl menuItem;
        boolean needsEmptyIcon;

        NavigationMenuTextItem(MenuItemImpl item) {
            this.menuItem = item;
        }

        public MenuItemImpl getMenuItem() {
            return this.menuItem;
        }
    }

    private static class NavigationMenuSeparatorItem implements NavigationMenuItem {
        private final int paddingBottom;
        private final int paddingTop;

        public NavigationMenuSeparatorItem(int paddingTop2, int paddingBottom2) {
            this.paddingTop = paddingTop2;
            this.paddingBottom = paddingBottom2;
        }

        public int getPaddingTop() {
            return this.paddingTop;
        }

        public int getPaddingBottom() {
            return this.paddingBottom;
        }
    }

    private static class NavigationMenuHeaderItem implements NavigationMenuItem {
        NavigationMenuHeaderItem() {
        }
    }

    private class NavigationMenuViewAccessibilityDelegate extends RecyclerViewAccessibilityDelegate {
        NavigationMenuViewAccessibilityDelegate(RecyclerView recyclerView) {
            super(recyclerView);
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(NavigationMenuPresenter.this.adapter.getRowCount(), 1, false));
        }
    }
}
