package com.google.android.material.navigation;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.SubMenuBuilder;

public final class NavigationBarMenu extends MenuBuilder {
    public static final int NO_MAX_ITEM_LIMIT = Integer.MAX_VALUE;
    private final int maxItemCount;
    private final boolean subMenuSupported;
    private final Class<?> viewClass;

    public NavigationBarMenu(Context context, Class<?> viewClass2, int maxItemCount2, boolean subMenuSupported2) {
        super(context);
        this.viewClass = viewClass2;
        this.maxItemCount = maxItemCount2;
        this.subMenuSupported = subMenuSupported2;
    }

    public int getMaxItemCount() {
        return this.maxItemCount;
    }

    public SubMenu addSubMenu(int group, int id, int categoryOrder, CharSequence title) {
        if (this.subMenuSupported) {
            MenuItemImpl item = (MenuItemImpl) addInternal(group, id, categoryOrder, title);
            SubMenuBuilder subMenu = new NavigationBarSubMenu(getContext(), this, item);
            item.setSubMenu(subMenu);
            return subMenu;
        }
        throw new UnsupportedOperationException(this.viewClass.getSimpleName() + " does not support submenus");
    }

    /* access modifiers changed from: protected */
    public MenuItem addInternal(int group, int id, int categoryOrder, CharSequence title) {
        if (size() + 1 <= this.maxItemCount) {
            stopDispatchingItemsChanged();
            MenuItem item = super.addInternal(group, id, categoryOrder, title);
            startDispatchingItemsChanged();
            return item;
        }
        String viewClassName = this.viewClass.getSimpleName();
        throw new IllegalArgumentException("Maximum number of items supported by " + viewClassName + " is " + this.maxItemCount + ". Limit can be checked with " + viewClassName + "#getMaxItemCount()");
    }
}
