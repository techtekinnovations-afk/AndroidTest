package com.google.android.material.navigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.core.widget.TextViewCompat;
import com.google.android.material.R;

public class NavigationBarSubheaderView extends FrameLayout implements NavigationBarMenuItemView {
    private boolean expanded;
    private MenuItemImpl itemData;
    boolean onlyShowWhenExpanded;
    private final TextView subheaderLabel = ((TextView) findViewById(R.id.navigation_menu_subheader_label));
    private ColorStateList textColor;

    NavigationBarSubheaderView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.m3_navigation_menu_subheader, this, true);
    }

    public void initialize(MenuItemImpl menuItem, int i) {
        this.itemData = menuItem;
        menuItem.setCheckable(false);
        this.subheaderLabel.setText(menuItem.getTitle());
        updateVisibility();
    }

    public void setTextAppearance(int textAppearance) {
        TextViewCompat.setTextAppearance(this.subheaderLabel, textAppearance);
        if (this.textColor != null) {
            this.subheaderLabel.setTextColor(this.textColor);
        }
    }

    public void setTextColor(ColorStateList color) {
        this.textColor = color;
        if (color != null) {
            this.subheaderLabel.setTextColor(color);
        }
    }

    public MenuItemImpl getItemData() {
        return this.itemData;
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

    private void updateVisibility() {
        if (this.itemData != null) {
            setVisibility((!this.itemData.isVisible() || (!this.expanded && this.onlyShowWhenExpanded)) ? 8 : 0);
        }
    }
}
