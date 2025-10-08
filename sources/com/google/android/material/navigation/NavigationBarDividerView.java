package com.google.android.material.navigation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import androidx.appcompat.view.menu.MenuItemImpl;
import com.google.android.material.R;

public class NavigationBarDividerView extends FrameLayout implements NavigationBarMenuItemView {
    private boolean dividersEnabled;
    private boolean expanded;
    boolean onlyShowWhenExpanded;

    NavigationBarDividerView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.m3_navigation_menu_divider, this, true);
        updateVisibility();
    }

    public void initialize(MenuItemImpl menuItem, int i) {
        updateVisibility();
    }

    public MenuItemImpl getItemData() {
        return null;
    }

    public void setTitle(CharSequence charSequence) {
    }

    public void setEnabled(boolean enabled) {
    }

    public void setCheckable(boolean checkable) {
    }

    public void setChecked(boolean checked) {
    }

    public void setShortcut(boolean showShortcut, char shortcutKey) {
    }

    public void setIcon(Drawable drawable) {
    }

    public boolean prefersCondensedTitle() {
        return false;
    }

    public boolean showsIcon() {
        return false;
    }

    public void setExpanded(boolean expanded2) {
        this.expanded = expanded2;
        updateVisibility();
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    public void setOnlyShowWhenExpanded(boolean onlyShowWhenExpanded2) {
        this.onlyShowWhenExpanded = onlyShowWhenExpanded2;
        updateVisibility();
    }

    public boolean isOnlyVisibleWhenExpanded() {
        return this.onlyShowWhenExpanded;
    }

    public void updateVisibility() {
        setVisibility((!this.dividersEnabled || (!this.expanded && this.onlyShowWhenExpanded)) ? 8 : 0);
    }

    public void setDividersEnabled(boolean dividersEnabled2) {
        this.dividersEnabled = dividersEnabled2;
        updateVisibility();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
