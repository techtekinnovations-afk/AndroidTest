package com.google.android.material.navigation;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

class DividerMenuItem implements MenuItem {
    DividerMenuItem() {
    }

    public boolean collapseActionView() {
        return false;
    }

    public boolean expandActionView() {
        return false;
    }

    public ActionProvider getActionProvider() {
        return null;
    }

    public View getActionView() {
        return null;
    }

    public char getAlphabeticShortcut() {
        return 0;
    }

    public int getGroupId() {
        return 0;
    }

    public Drawable getIcon() {
        return null;
    }

    public Intent getIntent() {
        return null;
    }

    public int getItemId() {
        return 0;
    }

    public ContextMenu.ContextMenuInfo getMenuInfo() {
        return null;
    }

    public char getNumericShortcut() {
        return 0;
    }

    public int getOrder() {
        return 0;
    }

    public SubMenu getSubMenu() {
        return null;
    }

    public CharSequence getTitle() {
        return null;
    }

    public CharSequence getTitleCondensed() {
        return null;
    }

    public boolean hasSubMenu() {
        return false;
    }

    public boolean isActionViewExpanded() {
        return false;
    }

    public boolean isCheckable() {
        return false;
    }

    public boolean isChecked() {
        return false;
    }

    public boolean isEnabled() {
        return false;
    }

    public boolean isVisible() {
        return false;
    }

    public MenuItem setActionProvider(ActionProvider actionProvider) {
        return null;
    }

    public MenuItem setActionView(View view) {
        return null;
    }

    public MenuItem setActionView(int resId) {
        return null;
    }

    public MenuItem setAlphabeticShortcut(char alphaChar) {
        return null;
    }

    public MenuItem setCheckable(boolean checkable) {
        return null;
    }

    public MenuItem setChecked(boolean checked) {
        return null;
    }

    public MenuItem setEnabled(boolean enabled) {
        return null;
    }

    public MenuItem setIcon(Drawable icon) {
        return null;
    }

    public MenuItem setIcon(int iconRes) {
        return null;
    }

    public MenuItem setIntent(Intent intent) {
        return null;
    }

    public MenuItem setNumericShortcut(char numericChar) {
        return null;
    }

    public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener listener) {
        return null;
    }

    public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener menuItemClickListener) {
        return null;
    }

    public MenuItem setShortcut(char numericChar, char alphaChar) {
        return null;
    }

    public void setShowAsAction(int actionEnum) {
    }

    public MenuItem setShowAsActionFlags(int actionEnum) {
        return null;
    }

    public MenuItem setTitle(int title) {
        return null;
    }

    public MenuItem setTitle(CharSequence title) {
        return null;
    }

    public MenuItem setTitleCondensed(CharSequence title) {
        return null;
    }

    public MenuItem setVisible(boolean visible) {
        return null;
    }
}
