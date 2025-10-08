package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.appcompat.R;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.CollapsibleActionView;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.appcompat.widget.ActionMenuView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuHostHelper;
import androidx.core.view.MenuProvider;
import androidx.core.view.ViewCompat;
import androidx.customview.view.AbsSavedState;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.google.android.material.navigation.NavigationBarView;
import io.grpc.internal.GrpcUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlin.time.DurationKt;

public class Toolbar extends ViewGroup implements MenuHost {
    private static final String TAG = "Toolbar";
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    private OnBackInvokedCallback mBackInvokedCallback;
    private boolean mBackInvokedCallbackEnabled;
    private OnBackInvokedDispatcher mBackInvokedDispatcher;
    int mButtonGravity;
    ImageButton mCollapseButtonView;
    private CharSequence mCollapseDescription;
    private Drawable mCollapseIcon;
    private boolean mCollapsible;
    private int mContentInsetEndWithActions;
    private int mContentInsetStartWithNavigation;
    private RtlSpacingHelper mContentInsets;
    private boolean mEatingHover;
    private boolean mEatingTouch;
    View mExpandedActionView;
    private ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    private int mGravity;
    private final ArrayList<View> mHiddenViews;
    private ImageView mLogoView;
    private int mMaxButtonHeight;
    MenuBuilder.Callback mMenuBuilderCallback;
    final MenuHostHelper mMenuHostHelper;
    ActionMenuView mMenuView;
    private final ActionMenuView.OnMenuItemClickListener mMenuViewItemClickListener;
    private ImageButton mNavButtonView;
    OnMenuItemClickListener mOnMenuItemClickListener;
    private ActionMenuPresenter mOuterActionMenuPresenter;
    private Context mPopupContext;
    private int mPopupTheme;
    private ArrayList<MenuItem> mProvidedMenuItems;
    private final Runnable mShowOverflowMenuRunnable;
    private CharSequence mSubtitleText;
    private int mSubtitleTextAppearance;
    private ColorStateList mSubtitleTextColor;
    private TextView mSubtitleTextView;
    private final int[] mTempMargins;
    private final ArrayList<View> mTempViews;
    private int mTitleMarginBottom;
    private int mTitleMarginEnd;
    private int mTitleMarginStart;
    private int mTitleMarginTop;
    private CharSequence mTitleText;
    private int mTitleTextAppearance;
    private ColorStateList mTitleTextColor;
    private TextView mTitleTextView;
    private ToolbarWidgetWrapper mWrapper;

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<Toolbar> {
        private int mCollapseContentDescriptionId;
        private int mCollapseIconId;
        private int mContentInsetEndId;
        private int mContentInsetEndWithActionsId;
        private int mContentInsetLeftId;
        private int mContentInsetRightId;
        private int mContentInsetStartId;
        private int mContentInsetStartWithNavigationId;
        private int mLogoDescriptionId;
        private int mLogoId;
        private int mMenuId;
        private int mNavigationContentDescriptionId;
        private int mNavigationIconId;
        private int mPopupThemeId;
        private boolean mPropertiesMapped = false;
        private int mSubtitleId;
        private int mTitleId;
        private int mTitleMarginBottomId;
        private int mTitleMarginEndId;
        private int mTitleMarginStartId;
        private int mTitleMarginTopId;

        public void mapProperties(PropertyMapper propertyMapper) {
            this.mCollapseContentDescriptionId = propertyMapper.mapObject("collapseContentDescription", R.attr.collapseContentDescription);
            this.mCollapseIconId = propertyMapper.mapObject("collapseIcon", R.attr.collapseIcon);
            this.mContentInsetEndId = propertyMapper.mapInt("contentInsetEnd", R.attr.contentInsetEnd);
            this.mContentInsetEndWithActionsId = propertyMapper.mapInt("contentInsetEndWithActions", R.attr.contentInsetEndWithActions);
            this.mContentInsetLeftId = propertyMapper.mapInt("contentInsetLeft", R.attr.contentInsetLeft);
            this.mContentInsetRightId = propertyMapper.mapInt("contentInsetRight", R.attr.contentInsetRight);
            this.mContentInsetStartId = propertyMapper.mapInt("contentInsetStart", R.attr.contentInsetStart);
            this.mContentInsetStartWithNavigationId = propertyMapper.mapInt("contentInsetStartWithNavigation", R.attr.contentInsetStartWithNavigation);
            this.mLogoId = propertyMapper.mapObject("logo", R.attr.logo);
            this.mLogoDescriptionId = propertyMapper.mapObject("logoDescription", R.attr.logoDescription);
            this.mMenuId = propertyMapper.mapObject("menu", R.attr.menu);
            this.mNavigationContentDescriptionId = propertyMapper.mapObject("navigationContentDescription", R.attr.navigationContentDescription);
            this.mNavigationIconId = propertyMapper.mapObject("navigationIcon", R.attr.navigationIcon);
            this.mPopupThemeId = propertyMapper.mapResourceId("popupTheme", R.attr.popupTheme);
            this.mSubtitleId = propertyMapper.mapObject("subtitle", R.attr.subtitle);
            this.mTitleId = propertyMapper.mapObject("title", R.attr.title);
            this.mTitleMarginBottomId = propertyMapper.mapInt("titleMarginBottom", R.attr.titleMarginBottom);
            this.mTitleMarginEndId = propertyMapper.mapInt("titleMarginEnd", R.attr.titleMarginEnd);
            this.mTitleMarginStartId = propertyMapper.mapInt("titleMarginStart", R.attr.titleMarginStart);
            this.mTitleMarginTopId = propertyMapper.mapInt("titleMarginTop", R.attr.titleMarginTop);
            this.mPropertiesMapped = true;
        }

        public void readProperties(Toolbar toolbar, PropertyReader propertyReader) {
            if (this.mPropertiesMapped) {
                propertyReader.readObject(this.mCollapseContentDescriptionId, toolbar.getCollapseContentDescription());
                propertyReader.readObject(this.mCollapseIconId, toolbar.getCollapseIcon());
                propertyReader.readInt(this.mContentInsetEndId, toolbar.getContentInsetEnd());
                propertyReader.readInt(this.mContentInsetEndWithActionsId, toolbar.getContentInsetEndWithActions());
                propertyReader.readInt(this.mContentInsetLeftId, toolbar.getContentInsetLeft());
                propertyReader.readInt(this.mContentInsetRightId, toolbar.getContentInsetRight());
                propertyReader.readInt(this.mContentInsetStartId, toolbar.getContentInsetStart());
                propertyReader.readInt(this.mContentInsetStartWithNavigationId, toolbar.getContentInsetStartWithNavigation());
                propertyReader.readObject(this.mLogoId, toolbar.getLogo());
                propertyReader.readObject(this.mLogoDescriptionId, toolbar.getLogoDescription());
                propertyReader.readObject(this.mMenuId, toolbar.getMenu());
                propertyReader.readObject(this.mNavigationContentDescriptionId, toolbar.getNavigationContentDescription());
                propertyReader.readObject(this.mNavigationIconId, toolbar.getNavigationIcon());
                propertyReader.readResourceId(this.mPopupThemeId, toolbar.getPopupTheme());
                propertyReader.readObject(this.mSubtitleId, toolbar.getSubtitle());
                propertyReader.readObject(this.mTitleId, toolbar.getTitle());
                propertyReader.readInt(this.mTitleMarginBottomId, toolbar.getTitleMarginBottom());
                propertyReader.readInt(this.mTitleMarginEndId, toolbar.getTitleMarginEnd());
                propertyReader.readInt(this.mTitleMarginStartId, toolbar.getTitleMarginStart());
                propertyReader.readInt(this.mTitleMarginTopId, toolbar.getTitleMarginTop());
                return;
            }
            throw new InspectionCompanion.UninitializedPropertyMapException();
        }
    }

    public Toolbar(Context context) {
        this(context, (AttributeSet) null);
    }

    public Toolbar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.toolbarStyle);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public Toolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mGravity = NavigationBarView.ITEM_GRAVITY_START_CENTER;
        this.mTempViews = new ArrayList<>();
        this.mHiddenViews = new ArrayList<>();
        this.mTempMargins = new int[2];
        this.mMenuHostHelper = new MenuHostHelper(new Toolbar$$ExternalSyntheticLambda1(this));
        this.mProvidedMenuItems = new ArrayList<>();
        this.mMenuViewItemClickListener = new ActionMenuView.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (Toolbar.this.mMenuHostHelper.onMenuItemSelected(item)) {
                    return true;
                }
                if (Toolbar.this.mOnMenuItemClickListener != null) {
                    return Toolbar.this.mOnMenuItemClickListener.onMenuItemClick(item);
                }
                return false;
            }
        };
        this.mShowOverflowMenuRunnable = new Runnable() {
            public void run() {
                Toolbar.this.showOverflowMenu();
            }
        };
        AttributeSet attributeSet = attrs;
        int i = defStyleAttr;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attributeSet, R.styleable.Toolbar, i, 0);
        ViewCompat.saveAttributeDataForStyleable(this, context, R.styleable.Toolbar, attributeSet, a.getWrappedTypeArray(), i, 0);
        this.mTitleTextAppearance = a.getResourceId(R.styleable.Toolbar_titleTextAppearance, 0);
        this.mSubtitleTextAppearance = a.getResourceId(R.styleable.Toolbar_subtitleTextAppearance, 0);
        this.mGravity = a.getInteger(R.styleable.Toolbar_android_gravity, this.mGravity);
        this.mButtonGravity = a.getInteger(R.styleable.Toolbar_buttonGravity, 48);
        int titleMargin = a.getDimensionPixelOffset(R.styleable.Toolbar_titleMargin, 0);
        titleMargin = a.hasValue(R.styleable.Toolbar_titleMargins) ? a.getDimensionPixelOffset(R.styleable.Toolbar_titleMargins, titleMargin) : titleMargin;
        this.mTitleMarginBottom = titleMargin;
        this.mTitleMarginTop = titleMargin;
        this.mTitleMarginEnd = titleMargin;
        this.mTitleMarginStart = titleMargin;
        int marginStart = a.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginStart, -1);
        if (marginStart >= 0) {
            this.mTitleMarginStart = marginStart;
        }
        int marginEnd = a.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginEnd, -1);
        if (marginEnd >= 0) {
            this.mTitleMarginEnd = marginEnd;
        }
        int marginTop = a.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginTop, -1);
        if (marginTop >= 0) {
            this.mTitleMarginTop = marginTop;
        }
        int marginBottom = a.getDimensionPixelOffset(R.styleable.Toolbar_titleMarginBottom, -1);
        if (marginBottom >= 0) {
            this.mTitleMarginBottom = marginBottom;
        }
        this.mMaxButtonHeight = a.getDimensionPixelSize(R.styleable.Toolbar_maxButtonHeight, -1);
        int contentInsetStart = a.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetStart, Integer.MIN_VALUE);
        int contentInsetEnd = a.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetEnd, Integer.MIN_VALUE);
        int contentInsetLeft = a.getDimensionPixelSize(R.styleable.Toolbar_contentInsetLeft, 0);
        int contentInsetRight = a.getDimensionPixelSize(R.styleable.Toolbar_contentInsetRight, 0);
        ensureContentInsets();
        this.mContentInsets.setAbsolute(contentInsetLeft, contentInsetRight);
        if (!(contentInsetStart == Integer.MIN_VALUE && contentInsetEnd == Integer.MIN_VALUE)) {
            this.mContentInsets.setRelative(contentInsetStart, contentInsetEnd);
        }
        this.mContentInsetStartWithNavigation = a.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetStartWithNavigation, Integer.MIN_VALUE);
        this.mContentInsetEndWithActions = a.getDimensionPixelOffset(R.styleable.Toolbar_contentInsetEndWithActions, Integer.MIN_VALUE);
        this.mCollapseIcon = a.getDrawable(R.styleable.Toolbar_collapseIcon);
        this.mCollapseDescription = a.getText(R.styleable.Toolbar_collapseContentDescription);
        CharSequence title = a.getText(R.styleable.Toolbar_title);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
        CharSequence subtitle = a.getText(R.styleable.Toolbar_subtitle);
        if (!TextUtils.isEmpty(subtitle)) {
            setSubtitle(subtitle);
        }
        this.mPopupContext = getContext();
        setPopupTheme(a.getResourceId(R.styleable.Toolbar_popupTheme, 0));
        Drawable navIcon = a.getDrawable(R.styleable.Toolbar_navigationIcon);
        if (navIcon != null) {
            setNavigationIcon(navIcon);
        }
        CharSequence navDesc = a.getText(R.styleable.Toolbar_navigationContentDescription);
        if (!TextUtils.isEmpty(navDesc)) {
            setNavigationContentDescription(navDesc);
        }
        Drawable logo = a.getDrawable(R.styleable.Toolbar_logo);
        if (logo != null) {
            setLogo(logo);
        }
        int i2 = titleMargin;
        CharSequence logoDesc = a.getText(R.styleable.Toolbar_logoDescription);
        if (!TextUtils.isEmpty(logoDesc)) {
            setLogoDescription(logoDesc);
        }
        CharSequence charSequence = logoDesc;
        if (a.hasValue(R.styleable.Toolbar_titleTextColor)) {
            setTitleTextColor(a.getColorStateList(R.styleable.Toolbar_titleTextColor));
        }
        if (a.hasValue(R.styleable.Toolbar_subtitleTextColor)) {
            setSubtitleTextColor(a.getColorStateList(R.styleable.Toolbar_subtitleTextColor));
        }
        if (a.hasValue(R.styleable.Toolbar_menu)) {
            int i3 = marginStart;
            inflateMenu(a.getResourceId(R.styleable.Toolbar_menu, 0));
        }
        a.recycle();
    }

    public void setBackInvokedCallbackEnabled(boolean enabled) {
        if (this.mBackInvokedCallbackEnabled != enabled) {
            this.mBackInvokedCallbackEnabled = enabled;
            updateBackInvokedCallbackState();
        }
    }

    public boolean isBackInvokedCallbackEnabled() {
        return this.mBackInvokedCallbackEnabled;
    }

    public void setPopupTheme(int resId) {
        if (this.mPopupTheme != resId) {
            this.mPopupTheme = resId;
            if (resId == 0) {
                this.mPopupContext = getContext();
            } else {
                this.mPopupContext = new ContextThemeWrapper(getContext(), resId);
            }
        }
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    public void setTitleMargin(int start, int top, int end, int bottom) {
        this.mTitleMarginStart = start;
        this.mTitleMarginTop = top;
        this.mTitleMarginEnd = end;
        this.mTitleMarginBottom = bottom;
        requestLayout();
    }

    public int getTitleMarginStart() {
        return this.mTitleMarginStart;
    }

    public void setTitleMarginStart(int margin) {
        this.mTitleMarginStart = margin;
        requestLayout();
    }

    public int getTitleMarginTop() {
        return this.mTitleMarginTop;
    }

    public void setTitleMarginTop(int margin) {
        this.mTitleMarginTop = margin;
        requestLayout();
    }

    public int getTitleMarginEnd() {
        return this.mTitleMarginEnd;
    }

    public void setTitleMarginEnd(int margin) {
        this.mTitleMarginEnd = margin;
        requestLayout();
    }

    public int getTitleMarginBottom() {
        return this.mTitleMarginBottom;
    }

    public void setTitleMarginBottom(int margin) {
        this.mTitleMarginBottom = margin;
        requestLayout();
    }

    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        ensureContentInsets();
        RtlSpacingHelper rtlSpacingHelper = this.mContentInsets;
        boolean z = true;
        if (layoutDirection != 1) {
            z = false;
        }
        rtlSpacingHelper.setDirection(z);
    }

    public void setLogo(int resId) {
        setLogo(AppCompatResources.getDrawable(getContext(), resId));
    }

    public boolean canShowOverflowMenu() {
        return getVisibility() == 0 && this.mMenuView != null && this.mMenuView.isOverflowReserved();
    }

    public boolean isOverflowMenuShowing() {
        return this.mMenuView != null && this.mMenuView.isOverflowMenuShowing();
    }

    public boolean isOverflowMenuShowPending() {
        return this.mMenuView != null && this.mMenuView.isOverflowMenuShowPending();
    }

    public boolean showOverflowMenu() {
        return this.mMenuView != null && this.mMenuView.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        return this.mMenuView != null && this.mMenuView.hideOverflowMenu();
    }

    public void setMenu(MenuBuilder menu, ActionMenuPresenter outerPresenter) {
        if (menu != null || this.mMenuView != null) {
            ensureMenuView();
            MenuBuilder oldMenu = this.mMenuView.peekMenu();
            if (oldMenu != menu) {
                if (oldMenu != null) {
                    oldMenu.removeMenuPresenter(this.mOuterActionMenuPresenter);
                    oldMenu.removeMenuPresenter(this.mExpandedMenuPresenter);
                }
                if (this.mExpandedMenuPresenter == null) {
                    this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
                }
                outerPresenter.setExpandedActionViewsExclusive(true);
                if (menu != null) {
                    menu.addMenuPresenter(outerPresenter, this.mPopupContext);
                    menu.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
                } else {
                    outerPresenter.initForMenu(this.mPopupContext, (MenuBuilder) null);
                    this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, (MenuBuilder) null);
                    outerPresenter.updateMenuView(true);
                    this.mExpandedMenuPresenter.updateMenuView(true);
                }
                this.mMenuView.setPopupTheme(this.mPopupTheme);
                this.mMenuView.setPresenter(outerPresenter);
                this.mOuterActionMenuPresenter = outerPresenter;
                updateBackInvokedCallbackState();
            }
        }
    }

    public void dismissPopupMenus() {
        if (this.mMenuView != null) {
            this.mMenuView.dismissPopupMenus();
        }
    }

    public boolean isTitleTruncated() {
        Layout titleLayout;
        if (this.mTitleTextView == null || (titleLayout = this.mTitleTextView.getLayout()) == null) {
            return false;
        }
        int lineCount = titleLayout.getLineCount();
        for (int i = 0; i < lineCount; i++) {
            if (titleLayout.getEllipsisCount(i) > 0) {
                return true;
            }
        }
        return false;
    }

    public void setLogo(Drawable drawable) {
        if (drawable != null) {
            ensureLogoView();
            if (!isChildOrHidden(this.mLogoView)) {
                addSystemView(this.mLogoView, true);
            }
        } else if (this.mLogoView != null && isChildOrHidden(this.mLogoView)) {
            removeView(this.mLogoView);
            this.mHiddenViews.remove(this.mLogoView);
        }
        if (this.mLogoView != null) {
            this.mLogoView.setImageDrawable(drawable);
        }
    }

    public Drawable getLogo() {
        if (this.mLogoView != null) {
            return this.mLogoView.getDrawable();
        }
        return null;
    }

    public void setLogoDescription(int resId) {
        setLogoDescription(getContext().getText(resId));
    }

    public void setLogoDescription(CharSequence description) {
        if (!TextUtils.isEmpty(description)) {
            ensureLogoView();
        }
        if (this.mLogoView != null) {
            this.mLogoView.setContentDescription(description);
        }
    }

    public CharSequence getLogoDescription() {
        if (this.mLogoView != null) {
            return this.mLogoView.getContentDescription();
        }
        return null;
    }

    private void ensureLogoView() {
        if (this.mLogoView == null) {
            this.mLogoView = new AppCompatImageView(getContext());
        }
    }

    public boolean hasExpandedActionView() {
        return (this.mExpandedMenuPresenter == null || this.mExpandedMenuPresenter.mCurrentExpandedItem == null) ? false : true;
    }

    public void collapseActionView() {
        MenuItemImpl item;
        if (this.mExpandedMenuPresenter == null) {
            item = null;
        } else {
            item = this.mExpandedMenuPresenter.mCurrentExpandedItem;
        }
        if (item != null) {
            item.collapseActionView();
        }
    }

    public CharSequence getTitle() {
        return this.mTitleText;
    }

    public void setTitle(int resId) {
        setTitle(getContext().getText(resId));
    }

    public void setTitle(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            if (this.mTitleTextView == null) {
                Context context = getContext();
                this.mTitleTextView = new AppCompatTextView(context);
                this.mTitleTextView.setSingleLine();
                this.mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                if (this.mTitleTextAppearance != 0) {
                    this.mTitleTextView.setTextAppearance(context, this.mTitleTextAppearance);
                }
                if (this.mTitleTextColor != null) {
                    this.mTitleTextView.setTextColor(this.mTitleTextColor);
                }
            }
            if (!isChildOrHidden(this.mTitleTextView)) {
                addSystemView(this.mTitleTextView, true);
            }
        } else if (this.mTitleTextView != null && isChildOrHidden(this.mTitleTextView)) {
            removeView(this.mTitleTextView);
            this.mHiddenViews.remove(this.mTitleTextView);
        }
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setText(title);
        }
        this.mTitleText = title;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitleText;
    }

    public void setSubtitle(int resId) {
        setSubtitle(getContext().getText(resId));
    }

    public void setSubtitle(CharSequence subtitle) {
        if (!TextUtils.isEmpty(subtitle)) {
            if (this.mSubtitleTextView == null) {
                Context context = getContext();
                this.mSubtitleTextView = new AppCompatTextView(context);
                this.mSubtitleTextView.setSingleLine();
                this.mSubtitleTextView.setEllipsize(TextUtils.TruncateAt.END);
                if (this.mSubtitleTextAppearance != 0) {
                    this.mSubtitleTextView.setTextAppearance(context, this.mSubtitleTextAppearance);
                }
                if (this.mSubtitleTextColor != null) {
                    this.mSubtitleTextView.setTextColor(this.mSubtitleTextColor);
                }
            }
            if (!isChildOrHidden(this.mSubtitleTextView)) {
                addSystemView(this.mSubtitleTextView, true);
            }
        } else if (this.mSubtitleTextView != null && isChildOrHidden(this.mSubtitleTextView)) {
            removeView(this.mSubtitleTextView);
            this.mHiddenViews.remove(this.mSubtitleTextView);
        }
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setText(subtitle);
        }
        this.mSubtitleText = subtitle;
    }

    public void setTitleTextAppearance(Context context, int resId) {
        this.mTitleTextAppearance = resId;
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextAppearance(context, resId);
        }
    }

    public void setSubtitleTextAppearance(Context context, int resId) {
        this.mSubtitleTextAppearance = resId;
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextAppearance(context, resId);
        }
    }

    public void setTitleTextColor(int color) {
        setTitleTextColor(ColorStateList.valueOf(color));
    }

    public void setTitleTextColor(ColorStateList color) {
        this.mTitleTextColor = color;
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextColor(color);
        }
    }

    public void setSubtitleTextColor(int color) {
        setSubtitleTextColor(ColorStateList.valueOf(color));
    }

    public void setSubtitleTextColor(ColorStateList color) {
        this.mSubtitleTextColor = color;
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextColor(color);
        }
    }

    public CharSequence getNavigationContentDescription() {
        if (this.mNavButtonView != null) {
            return this.mNavButtonView.getContentDescription();
        }
        return null;
    }

    public void setNavigationContentDescription(int resId) {
        setNavigationContentDescription(resId != 0 ? getContext().getText(resId) : null);
    }

    public void setNavigationContentDescription(CharSequence description) {
        if (!TextUtils.isEmpty(description)) {
            ensureNavButtonView();
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setContentDescription(description);
            TooltipCompat.setTooltipText(this.mNavButtonView, description);
        }
    }

    public void setNavigationIcon(int resId) {
        setNavigationIcon(AppCompatResources.getDrawable(getContext(), resId));
    }

    public void setNavigationIcon(Drawable icon) {
        if (icon != null) {
            ensureNavButtonView();
            if (!isChildOrHidden(this.mNavButtonView)) {
                addSystemView(this.mNavButtonView, true);
            }
        } else if (this.mNavButtonView != null && isChildOrHidden(this.mNavButtonView)) {
            removeView(this.mNavButtonView);
            this.mHiddenViews.remove(this.mNavButtonView);
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setImageDrawable(icon);
        }
    }

    public Drawable getNavigationIcon() {
        if (this.mNavButtonView != null) {
            return this.mNavButtonView.getDrawable();
        }
        return null;
    }

    public void setNavigationOnClickListener(View.OnClickListener listener) {
        ensureNavButtonView();
        this.mNavButtonView.setOnClickListener(listener);
    }

    public CharSequence getCollapseContentDescription() {
        if (this.mCollapseButtonView != null) {
            return this.mCollapseButtonView.getContentDescription();
        }
        return null;
    }

    public void setCollapseContentDescription(int resId) {
        setCollapseContentDescription(resId != 0 ? getContext().getText(resId) : null);
    }

    public void setCollapseContentDescription(CharSequence description) {
        if (!TextUtils.isEmpty(description)) {
            ensureCollapseButtonView();
        }
        if (this.mCollapseButtonView != null) {
            this.mCollapseButtonView.setContentDescription(description);
        }
    }

    public Drawable getCollapseIcon() {
        if (this.mCollapseButtonView != null) {
            return this.mCollapseButtonView.getDrawable();
        }
        return null;
    }

    public void setCollapseIcon(int resId) {
        setCollapseIcon(AppCompatResources.getDrawable(getContext(), resId));
    }

    public void setCollapseIcon(Drawable icon) {
        if (icon != null) {
            ensureCollapseButtonView();
            this.mCollapseButtonView.setImageDrawable(icon);
        } else if (this.mCollapseButtonView != null) {
            this.mCollapseButtonView.setImageDrawable(this.mCollapseIcon);
        }
    }

    public Menu getMenu() {
        ensureMenu();
        return this.mMenuView.getMenu();
    }

    public void setOverflowIcon(Drawable icon) {
        ensureMenu();
        this.mMenuView.setOverflowIcon(icon);
    }

    public Drawable getOverflowIcon() {
        ensureMenu();
        return this.mMenuView.getOverflowIcon();
    }

    private void ensureMenu() {
        ensureMenuView();
        if (this.mMenuView.peekMenu() == null) {
            MenuBuilder menu = (MenuBuilder) this.mMenuView.getMenu();
            if (this.mExpandedMenuPresenter == null) {
                this.mExpandedMenuPresenter = new ExpandedActionViewMenuPresenter();
            }
            this.mMenuView.setExpandedActionViewsExclusive(true);
            menu.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
            updateBackInvokedCallbackState();
        }
    }

    private void ensureMenuView() {
        if (this.mMenuView == null) {
            this.mMenuView = new ActionMenuView(getContext());
            this.mMenuView.setPopupTheme(this.mPopupTheme);
            this.mMenuView.setOnMenuItemClickListener(this.mMenuViewItemClickListener);
            this.mMenuView.setMenuCallbacks(this.mActionMenuPresenterCallback, new MenuBuilder.Callback() {
                public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                    return Toolbar.this.mMenuBuilderCallback != null && Toolbar.this.mMenuBuilderCallback.onMenuItemSelected(menu, item);
                }

                public void onMenuModeChange(MenuBuilder menu) {
                    if (!Toolbar.this.mMenuView.isOverflowMenuShowing()) {
                        Toolbar.this.mMenuHostHelper.onPrepareMenu(menu);
                    }
                    if (Toolbar.this.mMenuBuilderCallback != null) {
                        Toolbar.this.mMenuBuilderCallback.onMenuModeChange(menu);
                    }
                }
            });
            LayoutParams lp = generateDefaultLayoutParams();
            lp.gravity = (this.mButtonGravity & 112) | GravityCompat.END;
            this.mMenuView.setLayoutParams(lp);
            addSystemView(this.mMenuView, false);
        }
    }

    private MenuInflater getMenuInflater() {
        return new SupportMenuInflater(getContext());
    }

    public void inflateMenu(int resId) {
        getMenuInflater().inflate(resId, getMenu());
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        this.mOnMenuItemClickListener = listener;
    }

    public void setContentInsetsRelative(int contentInsetStart, int contentInsetEnd) {
        ensureContentInsets();
        this.mContentInsets.setRelative(contentInsetStart, contentInsetEnd);
    }

    public int getContentInsetStart() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getStart();
        }
        return 0;
    }

    public int getContentInsetEnd() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getEnd();
        }
        return 0;
    }

    public void setContentInsetsAbsolute(int contentInsetLeft, int contentInsetRight) {
        ensureContentInsets();
        this.mContentInsets.setAbsolute(contentInsetLeft, contentInsetRight);
    }

    public int getContentInsetLeft() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getLeft();
        }
        return 0;
    }

    public int getContentInsetRight() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getRight();
        }
        return 0;
    }

    public int getContentInsetStartWithNavigation() {
        if (this.mContentInsetStartWithNavigation != Integer.MIN_VALUE) {
            return this.mContentInsetStartWithNavigation;
        }
        return getContentInsetStart();
    }

    public void setContentInsetStartWithNavigation(int insetStartWithNavigation) {
        if (insetStartWithNavigation < 0) {
            insetStartWithNavigation = Integer.MIN_VALUE;
        }
        if (insetStartWithNavigation != this.mContentInsetStartWithNavigation) {
            this.mContentInsetStartWithNavigation = insetStartWithNavigation;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public int getContentInsetEndWithActions() {
        if (this.mContentInsetEndWithActions != Integer.MIN_VALUE) {
            return this.mContentInsetEndWithActions;
        }
        return getContentInsetEnd();
    }

    public void setContentInsetEndWithActions(int insetEndWithActions) {
        if (insetEndWithActions < 0) {
            insetEndWithActions = Integer.MIN_VALUE;
        }
        if (insetEndWithActions != this.mContentInsetEndWithActions) {
            this.mContentInsetEndWithActions = insetEndWithActions;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public int getCurrentContentInsetStart() {
        if (getNavigationIcon() != null) {
            return Math.max(getContentInsetStart(), Math.max(this.mContentInsetStartWithNavigation, 0));
        }
        return getContentInsetStart();
    }

    public int getCurrentContentInsetEnd() {
        boolean hasActions = false;
        if (this.mMenuView != null) {
            MenuBuilder mb = this.mMenuView.peekMenu();
            hasActions = mb != null && mb.hasVisibleItems();
        }
        if (hasActions) {
            return Math.max(getContentInsetEnd(), Math.max(this.mContentInsetEndWithActions, 0));
        }
        return getContentInsetEnd();
    }

    public int getCurrentContentInsetLeft() {
        if (getLayoutDirection() == 1) {
            return getCurrentContentInsetEnd();
        }
        return getCurrentContentInsetStart();
    }

    public int getCurrentContentInsetRight() {
        if (getLayoutDirection() == 1) {
            return getCurrentContentInsetStart();
        }
        return getCurrentContentInsetEnd();
    }

    private void ensureNavButtonView() {
        if (this.mNavButtonView == null) {
            this.mNavButtonView = new AppCompatImageButton(getContext(), (AttributeSet) null, R.attr.toolbarNavigationButtonStyle);
            LayoutParams lp = generateDefaultLayoutParams();
            lp.gravity = (this.mButtonGravity & 112) | GravityCompat.START;
            this.mNavButtonView.setLayoutParams(lp);
        }
    }

    /* access modifiers changed from: package-private */
    public View getNavButtonView() {
        return this.mNavButtonView;
    }

    /* access modifiers changed from: package-private */
    public void ensureCollapseButtonView() {
        if (this.mCollapseButtonView == null) {
            this.mCollapseButtonView = new AppCompatImageButton(getContext(), (AttributeSet) null, R.attr.toolbarNavigationButtonStyle);
            this.mCollapseButtonView.setImageDrawable(this.mCollapseIcon);
            this.mCollapseButtonView.setContentDescription(this.mCollapseDescription);
            LayoutParams lp = generateDefaultLayoutParams();
            lp.gravity = (this.mButtonGravity & 112) | GravityCompat.START;
            lp.mViewType = 2;
            this.mCollapseButtonView.setLayoutParams(lp);
            this.mCollapseButtonView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toolbar.this.collapseActionView();
                }
            });
        }
    }

    private void addSystemView(View v, boolean allowHide) {
        LayoutParams lp;
        ViewGroup.LayoutParams vlp = v.getLayoutParams();
        if (vlp == null) {
            lp = generateDefaultLayoutParams();
        } else if (!checkLayoutParams(vlp)) {
            lp = generateLayoutParams(vlp);
        } else {
            lp = (LayoutParams) vlp;
        }
        lp.mViewType = 1;
        if (!allowHide || this.mExpandedActionView == null) {
            addView(v, lp);
            return;
        }
        v.setLayoutParams(lp);
        this.mHiddenViews.add(v);
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState state = new SavedState(super.onSaveInstanceState());
        if (!(this.mExpandedMenuPresenter == null || this.mExpandedMenuPresenter.mCurrentExpandedItem == null)) {
            state.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
        }
        state.isOverflowOpen = isOverflowMenuShowing();
        return state;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        MenuItem item;
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        Menu menu = this.mMenuView != null ? this.mMenuView.peekMenu() : null;
        if (!(ss.expandedMenuItemId == 0 || this.mExpandedMenuPresenter == null || menu == null || (item = menu.findItem(ss.expandedMenuItemId)) == null)) {
            item.expandActionView();
        }
        if (ss.isOverflowOpen) {
            postShowOverflowMenu();
        }
    }

    private void postShowOverflowMenu() {
        removeCallbacks(this.mShowOverflowMenuRunnable);
        post(this.mShowOverflowMenuRunnable);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.mShowOverflowMenuRunnable);
        updateBackInvokedCallbackState();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateBackInvokedCallbackState();
    }

    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        if (action == 0) {
            this.mEatingTouch = false;
        }
        if (!this.mEatingTouch) {
            boolean handled = super.onTouchEvent(ev);
            if (action == 0 && !handled) {
                this.mEatingTouch = true;
            }
        }
        if (action == 1 || action == 3) {
            this.mEatingTouch = false;
        }
        return true;
    }

    public boolean onHoverEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        if (action == 9) {
            this.mEatingHover = false;
        }
        if (!this.mEatingHover) {
            boolean handled = super.onHoverEvent(ev);
            if (action == 9 && !handled) {
                this.mEatingHover = true;
            }
        }
        if (action == 10 || action == 3) {
            this.mEatingHover = false;
        }
        return true;
    }

    private void measureChildConstrained(View child, int parentWidthSpec, int widthUsed, int parentHeightSpec, int heightUsed, int heightConstraint) {
        int size;
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
        int childWidthSpec = getChildMeasureSpec(parentWidthSpec, getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin + widthUsed, lp.width);
        int childHeightSpec = getChildMeasureSpec(parentHeightSpec, getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin + heightUsed, lp.height);
        int childHeightMode = View.MeasureSpec.getMode(childHeightSpec);
        if (childHeightMode != 1073741824 && heightConstraint >= 0) {
            if (childHeightMode != 0) {
                size = Math.min(View.MeasureSpec.getSize(childHeightSpec), heightConstraint);
            } else {
                size = heightConstraint;
            }
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(size, 1073741824);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    private int measureChildCollapseMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed, int[] collapsingMargins) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
        int leftDiff = lp.leftMargin - collapsingMargins[0];
        int rightDiff = lp.rightMargin - collapsingMargins[1];
        int hMargins = Math.max(0, leftDiff) + Math.max(0, rightDiff);
        collapsingMargins[0] = Math.max(0, -leftDiff);
        collapsingMargins[1] = Math.max(0, -rightDiff);
        child.measure(getChildMeasureSpec(parentWidthMeasureSpec, getPaddingLeft() + getPaddingRight() + hMargins + widthUsed, lp.width), getChildMeasureSpec(parentHeightMeasureSpec, getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin + heightUsed, lp.height));
        return child.getMeasuredWidth() + hMargins;
    }

    private boolean shouldCollapse() {
        if (!this.mCollapsible) {
            return false;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (shouldLayout(child) && child.getMeasuredWidth() > 0 && child.getMeasuredHeight() > 0) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int marginEndIndex;
        int marginStartIndex;
        int[] collapsingMargins;
        int width;
        int titleVertMargins;
        int titleHeight;
        int childCount;
        int width2;
        int childState;
        int height = 0;
        int childState2 = 0;
        int[] collapsingMargins2 = this.mTempMargins;
        if (ViewUtils.isLayoutRtl(this)) {
            marginStartIndex = 1;
            marginEndIndex = 0;
        } else {
            marginStartIndex = 0;
            marginEndIndex = 1;
        }
        int navWidth = 0;
        if (shouldLayout(this.mNavButtonView)) {
            measureChildConstrained(this.mNavButtonView, widthMeasureSpec, 0, heightMeasureSpec, 0, this.mMaxButtonHeight);
            navWidth = this.mNavButtonView.getMeasuredWidth() + getHorizontalMargins(this.mNavButtonView);
            height = Math.max(0, this.mNavButtonView.getMeasuredHeight() + getVerticalMargins(this.mNavButtonView));
            childState2 = View.combineMeasuredStates(0, this.mNavButtonView.getMeasuredState());
        }
        if (shouldLayout(this.mCollapseButtonView)) {
            measureChildConstrained(this.mCollapseButtonView, widthMeasureSpec, 0, heightMeasureSpec, 0, this.mMaxButtonHeight);
            navWidth = this.mCollapseButtonView.getMeasuredWidth() + getHorizontalMargins(this.mCollapseButtonView);
            height = Math.max(height, this.mCollapseButtonView.getMeasuredHeight() + getVerticalMargins(this.mCollapseButtonView));
            childState2 = View.combineMeasuredStates(childState2, this.mCollapseButtonView.getMeasuredState());
        }
        int contentInsetStart = getCurrentContentInsetStart();
        int width3 = 0 + Math.max(contentInsetStart, navWidth);
        collapsingMargins2[marginStartIndex] = Math.max(0, contentInsetStart - navWidth);
        int menuWidth = 0;
        if (shouldLayout(this.mMenuView)) {
            measureChildConstrained(this.mMenuView, widthMeasureSpec, width3, heightMeasureSpec, 0, this.mMaxButtonHeight);
            menuWidth = this.mMenuView.getMeasuredWidth() + getHorizontalMargins(this.mMenuView);
            height = Math.max(height, this.mMenuView.getMeasuredHeight() + getVerticalMargins(this.mMenuView));
            childState2 = View.combineMeasuredStates(childState2, this.mMenuView.getMeasuredState());
        }
        int contentInsetEnd = getCurrentContentInsetEnd();
        int width4 = width3 + Math.max(contentInsetEnd, menuWidth);
        collapsingMargins2[marginEndIndex] = Math.max(0, contentInsetEnd - menuWidth);
        if (shouldLayout(this.mExpandedActionView)) {
            collapsingMargins = collapsingMargins2;
            int i = contentInsetEnd;
            width4 += measureChildCollapseMargins(this.mExpandedActionView, widthMeasureSpec, width4, heightMeasureSpec, 0, collapsingMargins);
            height = Math.max(height, this.mExpandedActionView.getMeasuredHeight() + getVerticalMargins(this.mExpandedActionView));
            childState2 = View.combineMeasuredStates(childState2, this.mExpandedActionView.getMeasuredState());
        } else {
            collapsingMargins = collapsingMargins2;
            int i2 = contentInsetEnd;
        }
        if (shouldLayout(this.mLogoView)) {
            width4 += measureChildCollapseMargins(this.mLogoView, widthMeasureSpec, width4, heightMeasureSpec, 0, collapsingMargins);
            height = Math.max(height, this.mLogoView.getMeasuredHeight() + getVerticalMargins(this.mLogoView));
            childState2 = View.combineMeasuredStates(childState2, this.mLogoView.getMeasuredState());
        }
        int childCount2 = getChildCount();
        int i3 = height;
        int i4 = 0;
        int height2 = childState2;
        int height3 = i3;
        while (i4 < childCount2) {
            int childCount3 = childCount2;
            View child = getChildAt(i4);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (lp.mViewType != 0) {
                childState = height2;
                width2 = width;
                childCount = childCount3;
                LayoutParams layoutParams = lp;
            } else if (!shouldLayout(child)) {
                childState = height2;
                width2 = width;
                childCount = childCount3;
            } else {
                childCount = childCount3;
                LayoutParams layoutParams2 = lp;
                width += measureChildCollapseMargins(child, widthMeasureSpec, width, heightMeasureSpec, 0, collapsingMargins);
                height3 = Math.max(height3, child.getMeasuredHeight() + getVerticalMargins(child));
                height2 = View.combineMeasuredStates(height2, child.getMeasuredState());
                i4++;
                childCount2 = childCount;
            }
            height2 = childState;
            width = width2;
            i4++;
            childCount2 = childCount;
        }
        int childState3 = height2;
        int width5 = width;
        int titleWidth = 0;
        int titleHeight2 = 0;
        int titleVertMargins2 = this.mTitleMarginTop + this.mTitleMarginBottom;
        int titleHorizMargins = this.mTitleMarginStart + this.mTitleMarginEnd;
        if (shouldLayout(this.mTitleTextView)) {
            int measureChildCollapseMargins = measureChildCollapseMargins(this.mTitleTextView, widthMeasureSpec, width5 + titleHorizMargins, heightMeasureSpec, titleVertMargins2, collapsingMargins);
            titleVertMargins = titleVertMargins2;
            titleWidth = this.mTitleTextView.getMeasuredWidth() + getHorizontalMargins(this.mTitleTextView);
            titleHeight2 = this.mTitleTextView.getMeasuredHeight() + getVerticalMargins(this.mTitleTextView);
            childState3 = View.combineMeasuredStates(childState3, this.mTitleTextView.getMeasuredState());
        } else {
            titleVertMargins = titleVertMargins2;
        }
        if (shouldLayout(this.mSubtitleTextView)) {
            titleWidth = Math.max(titleWidth, measureChildCollapseMargins(this.mSubtitleTextView, widthMeasureSpec, width5 + titleHorizMargins, heightMeasureSpec, titleHeight2 + titleVertMargins, collapsingMargins));
            int titleHeight3 = titleHeight2 + this.mSubtitleTextView.getMeasuredHeight() + getVerticalMargins(this.mSubtitleTextView);
            childState3 = View.combineMeasuredStates(childState3, this.mSubtitleTextView.getMeasuredState());
            titleHeight = titleHeight3;
        } else {
            titleHeight = titleHeight2;
        }
        int height4 = Math.max(height3, titleHeight);
        int width6 = width5 + titleWidth + getPaddingLeft() + getPaddingRight();
        int height5 = height4 + getPaddingTop() + getPaddingBottom();
        int i5 = titleHeight;
        int i6 = height5;
        setMeasuredDimension(View.resolveSizeAndState(Math.max(width6, getSuggestedMinimumWidth()), widthMeasureSpec, -16777216 & childState3), shouldCollapse() ? 0 : View.resolveSizeAndState(Math.max(height5, getSuggestedMinimumHeight()), heightMeasureSpec, childState3 << 16));
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x0166, code lost:
        if (r0.mTitleTextView.getMeasuredWidth() <= 0) goto L_0x016b;
     */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0182  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x01a1  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01b6  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x01f3  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x0278  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onLayout(boolean r34, int r35, int r36, int r37, int r38) {
        /*
            r33 = this;
            r0 = r33
            int r1 = r0.getLayoutDirection()
            r2 = 0
            r3 = 1
            if (r1 != r3) goto L_0x000c
            r1 = r3
            goto L_0x000d
        L_0x000c:
            r1 = r2
        L_0x000d:
            int r4 = r0.getWidth()
            int r5 = r0.getHeight()
            int r6 = r0.getPaddingLeft()
            int r7 = r0.getPaddingRight()
            int r8 = r0.getPaddingTop()
            int r9 = r0.getPaddingBottom()
            r10 = r6
            int r11 = r4 - r7
            int[] r12 = r0.mTempMargins
            r12[r3] = r2
            r12[r2] = r2
            int r13 = androidx.core.view.ViewCompat.getMinimumHeight(r0)
            if (r13 < 0) goto L_0x003b
            int r14 = r38 - r36
            int r14 = java.lang.Math.min(r13, r14)
            goto L_0x003c
        L_0x003b:
            r14 = r2
        L_0x003c:
            android.widget.ImageButton r15 = r0.mNavButtonView
            boolean r15 = r0.shouldLayout(r15)
            if (r15 == 0) goto L_0x0053
            if (r1 == 0) goto L_0x004d
            android.widget.ImageButton r15 = r0.mNavButtonView
            int r11 = r0.layoutChildRight(r15, r11, r12, r14)
            goto L_0x0053
        L_0x004d:
            android.widget.ImageButton r15 = r0.mNavButtonView
            int r10 = r0.layoutChildLeft(r15, r10, r12, r14)
        L_0x0053:
            android.widget.ImageButton r15 = r0.mCollapseButtonView
            boolean r15 = r0.shouldLayout(r15)
            if (r15 == 0) goto L_0x006a
            if (r1 == 0) goto L_0x0064
            android.widget.ImageButton r15 = r0.mCollapseButtonView
            int r11 = r0.layoutChildRight(r15, r11, r12, r14)
            goto L_0x006a
        L_0x0064:
            android.widget.ImageButton r15 = r0.mCollapseButtonView
            int r10 = r0.layoutChildLeft(r15, r10, r12, r14)
        L_0x006a:
            androidx.appcompat.widget.ActionMenuView r15 = r0.mMenuView
            boolean r15 = r0.shouldLayout(r15)
            if (r15 == 0) goto L_0x0081
            if (r1 == 0) goto L_0x007b
            androidx.appcompat.widget.ActionMenuView r15 = r0.mMenuView
            int r10 = r0.layoutChildLeft(r15, r10, r12, r14)
            goto L_0x0081
        L_0x007b:
            androidx.appcompat.widget.ActionMenuView r15 = r0.mMenuView
            int r11 = r0.layoutChildRight(r15, r11, r12, r14)
        L_0x0081:
            int r15 = r0.getCurrentContentInsetLeft()
            int r16 = r0.getCurrentContentInsetRight()
            r17 = r3
            int r3 = r15 - r10
            int r3 = java.lang.Math.max(r2, r3)
            r12[r2] = r3
            int r3 = r4 - r7
            int r3 = r3 - r11
            int r3 = r16 - r3
            int r3 = java.lang.Math.max(r2, r3)
            r12[r17] = r3
            int r3 = java.lang.Math.max(r10, r15)
            int r10 = r4 - r7
            int r10 = r10 - r16
            int r10 = java.lang.Math.min(r11, r10)
            android.view.View r11 = r0.mExpandedActionView
            boolean r11 = r0.shouldLayout(r11)
            if (r11 == 0) goto L_0x00c1
            if (r1 == 0) goto L_0x00bb
            android.view.View r11 = r0.mExpandedActionView
            int r10 = r0.layoutChildRight(r11, r10, r12, r14)
            goto L_0x00c1
        L_0x00bb:
            android.view.View r11 = r0.mExpandedActionView
            int r3 = r0.layoutChildLeft(r11, r3, r12, r14)
        L_0x00c1:
            android.widget.ImageView r11 = r0.mLogoView
            boolean r11 = r0.shouldLayout(r11)
            if (r11 == 0) goto L_0x00d8
            if (r1 == 0) goto L_0x00d2
            android.widget.ImageView r11 = r0.mLogoView
            int r10 = r0.layoutChildRight(r11, r10, r12, r14)
            goto L_0x00d8
        L_0x00d2:
            android.widget.ImageView r11 = r0.mLogoView
            int r3 = r0.layoutChildLeft(r11, r3, r12, r14)
        L_0x00d8:
            android.widget.TextView r11 = r0.mTitleTextView
            boolean r11 = r0.shouldLayout(r11)
            android.widget.TextView r2 = r0.mSubtitleTextView
            boolean r2 = r0.shouldLayout(r2)
            r19 = 0
            if (r11 == 0) goto L_0x0109
            r20 = r1
            android.widget.TextView r1 = r0.mTitleTextView
            android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
            androidx.appcompat.widget.Toolbar$LayoutParams r1 = (androidx.appcompat.widget.Toolbar.LayoutParams) r1
            r21 = r2
            int r2 = r1.topMargin
            r22 = r2
            android.widget.TextView r2 = r0.mTitleTextView
            int r2 = r2.getMeasuredHeight()
            int r2 = r22 + r2
            r22 = r2
            int r2 = r1.bottomMargin
            int r2 = r22 + r2
            int r19 = r19 + r2
            goto L_0x010d
        L_0x0109:
            r20 = r1
            r21 = r2
        L_0x010d:
            if (r21 == 0) goto L_0x012b
            android.widget.TextView r1 = r0.mSubtitleTextView
            android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
            androidx.appcompat.widget.Toolbar$LayoutParams r1 = (androidx.appcompat.widget.Toolbar.LayoutParams) r1
            int r2 = r1.topMargin
            r22 = r2
            android.widget.TextView r2 = r0.mSubtitleTextView
            int r2 = r2.getMeasuredHeight()
            int r2 = r22 + r2
            r22 = r2
            int r2 = r1.bottomMargin
            int r2 = r22 + r2
            int r19 = r19 + r2
        L_0x012b:
            if (r11 != 0) goto L_0x013a
            if (r21 == 0) goto L_0x0130
            goto L_0x013a
        L_0x0130:
            r26 = r4
            r28 = r5
            r30 = r6
            r31 = r7
            goto L_0x02f9
        L_0x013a:
            if (r11 == 0) goto L_0x013f
            android.widget.TextView r1 = r0.mTitleTextView
            goto L_0x0141
        L_0x013f:
            android.widget.TextView r1 = r0.mSubtitleTextView
        L_0x0141:
            if (r21 == 0) goto L_0x0146
            android.widget.TextView r2 = r0.mSubtitleTextView
            goto L_0x0148
        L_0x0146:
            android.widget.TextView r2 = r0.mTitleTextView
        L_0x0148:
            android.view.ViewGroup$LayoutParams r22 = r1.getLayoutParams()
            r23 = r1
            r1 = r22
            androidx.appcompat.widget.Toolbar$LayoutParams r1 = (androidx.appcompat.widget.Toolbar.LayoutParams) r1
            android.view.ViewGroup$LayoutParams r22 = r2.getLayoutParams()
            r24 = r2
            r2 = r22
            androidx.appcompat.widget.Toolbar$LayoutParams r2 = (androidx.appcompat.widget.Toolbar.LayoutParams) r2
            if (r11 == 0) goto L_0x0169
            r22 = r3
            android.widget.TextView r3 = r0.mTitleTextView
            int r3 = r3.getMeasuredWidth()
            if (r3 > 0) goto L_0x0175
            goto L_0x016b
        L_0x0169:
            r22 = r3
        L_0x016b:
            if (r21 == 0) goto L_0x0178
            android.widget.TextView r3 = r0.mSubtitleTextView
            int r3 = r3.getMeasuredWidth()
            if (r3 <= 0) goto L_0x0178
        L_0x0175:
            r3 = r17
            goto L_0x0179
        L_0x0178:
            r3 = 0
        L_0x0179:
            r25 = r3
            int r3 = r0.mGravity
            r3 = r3 & 112(0x70, float:1.57E-43)
            switch(r3) {
                case 48: goto L_0x01b6;
                case 80: goto L_0x01a1;
                default: goto L_0x0182;
            }
        L_0x0182:
            int r3 = r5 - r8
            int r3 = r3 - r9
            int r26 = r3 - r19
            r27 = r3
            int r3 = r26 / 2
            r26 = r4
            int r4 = r1.topMargin
            r28 = r4
            int r4 = r0.mTitleMarginTop
            int r4 = r28 + r4
            if (r3 >= r4) goto L_0x01cb
            int r4 = r1.topMargin
            r28 = r3
            int r3 = r0.mTitleMarginTop
            int r3 = r3 + r4
            r29 = r1
            goto L_0x01ee
        L_0x01a1:
            int r3 = r5 - r9
            r26 = r3
            int r3 = r2.bottomMargin
            int r3 = r26 - r3
            r26 = r3
            int r3 = r0.mTitleMarginBottom
            int r3 = r26 - r3
            int r3 = r3 - r19
            r29 = r1
            r26 = r4
            goto L_0x01f1
        L_0x01b6:
            int r3 = r0.getPaddingTop()
            r26 = r3
            int r3 = r1.topMargin
            int r3 = r26 + r3
            r26 = r3
            int r3 = r0.mTitleMarginTop
            int r3 = r26 + r3
            r29 = r1
            r26 = r4
            goto L_0x01f1
        L_0x01cb:
            r28 = r3
            int r3 = r5 - r9
            int r3 = r3 - r19
            int r3 = r3 - r28
            int r3 = r3 - r8
            int r4 = r1.bottomMargin
            r29 = r1
            int r1 = r0.mTitleMarginBottom
            int r4 = r4 + r1
            if (r3 >= r4) goto L_0x01ec
            int r1 = r2.bottomMargin
            int r4 = r0.mTitleMarginBottom
            int r1 = r1 + r4
            int r1 = r1 - r3
            int r1 = r28 - r1
            r4 = 0
            int r1 = java.lang.Math.max(r4, r1)
            r3 = r1
            goto L_0x01ee
        L_0x01ec:
            r3 = r28
        L_0x01ee:
            int r1 = r8 + r3
            r3 = r1
        L_0x01f1:
            if (r20 == 0) goto L_0x0278
            if (r25 == 0) goto L_0x01f8
            int r1 = r0.mTitleMarginStart
            goto L_0x01f9
        L_0x01f8:
            r1 = 0
        L_0x01f9:
            r4 = r12[r17]
            int r1 = r1 - r4
            r4 = 0
            int r18 = java.lang.Math.max(r4, r1)
            int r10 = r10 - r18
            r27 = r2
            int r2 = -r1
            int r2 = java.lang.Math.max(r4, r2)
            r12[r17] = r2
            r2 = r10
            r4 = r10
            if (r11 == 0) goto L_0x023d
            r18 = r1
            android.widget.TextView r1 = r0.mTitleTextView
            android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
            androidx.appcompat.widget.Toolbar$LayoutParams r1 = (androidx.appcompat.widget.Toolbar.LayoutParams) r1
            r28 = r5
            android.widget.TextView r5 = r0.mTitleTextView
            int r5 = r5.getMeasuredWidth()
            int r5 = r2 - r5
            r30 = r6
            android.widget.TextView r6 = r0.mTitleTextView
            int r6 = r6.getMeasuredHeight()
            int r6 = r6 + r3
            r31 = r7
            android.widget.TextView r7 = r0.mTitleTextView
            r7.layout(r5, r3, r2, r6)
            int r7 = r0.mTitleMarginEnd
            int r2 = r5 - r7
            int r7 = r1.bottomMargin
            int r3 = r6 + r7
            goto L_0x0245
        L_0x023d:
            r18 = r1
            r28 = r5
            r30 = r6
            r31 = r7
        L_0x0245:
            if (r21 == 0) goto L_0x026d
            android.widget.TextView r1 = r0.mSubtitleTextView
            android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
            androidx.appcompat.widget.Toolbar$LayoutParams r1 = (androidx.appcompat.widget.Toolbar.LayoutParams) r1
            int r5 = r1.topMargin
            int r3 = r3 + r5
            android.widget.TextView r5 = r0.mSubtitleTextView
            int r5 = r5.getMeasuredWidth()
            int r5 = r4 - r5
            android.widget.TextView r6 = r0.mSubtitleTextView
            int r6 = r6.getMeasuredHeight()
            int r6 = r6 + r3
            android.widget.TextView r7 = r0.mSubtitleTextView
            r7.layout(r5, r3, r4, r6)
            int r7 = r0.mTitleMarginEnd
            int r4 = r4 - r7
            int r7 = r1.bottomMargin
            int r3 = r6 + r7
        L_0x026d:
            if (r25 == 0) goto L_0x0274
            int r1 = java.lang.Math.min(r2, r4)
            r10 = r1
        L_0x0274:
            r3 = r22
            goto L_0x02f9
        L_0x0278:
            r27 = r2
            r28 = r5
            r30 = r6
            r31 = r7
            if (r25 == 0) goto L_0x0285
            int r4 = r0.mTitleMarginStart
            goto L_0x0286
        L_0x0285:
            r4 = 0
        L_0x0286:
            r1 = 0
            r2 = r12[r1]
            int r4 = r4 - r2
            int r2 = java.lang.Math.max(r1, r4)
            int r2 = r22 + r2
            int r5 = -r4
            int r5 = java.lang.Math.max(r1, r5)
            r12[r1] = r5
            r1 = r2
            r5 = r2
            if (r11 == 0) goto L_0x02c3
            android.widget.TextView r6 = r0.mTitleTextView
            android.view.ViewGroup$LayoutParams r6 = r6.getLayoutParams()
            androidx.appcompat.widget.Toolbar$LayoutParams r6 = (androidx.appcompat.widget.Toolbar.LayoutParams) r6
            android.widget.TextView r7 = r0.mTitleTextView
            int r7 = r7.getMeasuredWidth()
            int r7 = r7 + r1
            r18 = r2
            android.widget.TextView r2 = r0.mTitleTextView
            int r2 = r2.getMeasuredHeight()
            int r2 = r2 + r3
            r22 = r4
            android.widget.TextView r4 = r0.mTitleTextView
            r4.layout(r1, r3, r7, r2)
            int r4 = r0.mTitleMarginEnd
            int r1 = r7 + r4
            int r4 = r6.bottomMargin
            int r3 = r2 + r4
            goto L_0x02c7
        L_0x02c3:
            r18 = r2
            r22 = r4
        L_0x02c7:
            if (r21 == 0) goto L_0x02ef
            android.widget.TextView r2 = r0.mSubtitleTextView
            android.view.ViewGroup$LayoutParams r2 = r2.getLayoutParams()
            androidx.appcompat.widget.Toolbar$LayoutParams r2 = (androidx.appcompat.widget.Toolbar.LayoutParams) r2
            int r4 = r2.topMargin
            int r3 = r3 + r4
            android.widget.TextView r4 = r0.mSubtitleTextView
            int r4 = r4.getMeasuredWidth()
            int r4 = r4 + r5
            android.widget.TextView r6 = r0.mSubtitleTextView
            int r6 = r6.getMeasuredHeight()
            int r6 = r6 + r3
            android.widget.TextView r7 = r0.mSubtitleTextView
            r7.layout(r5, r3, r4, r6)
            int r7 = r0.mTitleMarginEnd
            int r5 = r4 + r7
            int r7 = r2.bottomMargin
            int r3 = r6 + r7
        L_0x02ef:
            if (r25 == 0) goto L_0x02f7
            int r2 = java.lang.Math.max(r1, r5)
            r3 = r2
            goto L_0x02f9
        L_0x02f7:
            r3 = r18
        L_0x02f9:
            java.util.ArrayList<android.view.View> r1 = r0.mTempViews
            r2 = 3
            r0.addCustomViewsWithGravity(r1, r2)
            java.util.ArrayList<android.view.View> r1 = r0.mTempViews
            int r1 = r1.size()
            r2 = 0
        L_0x0306:
            if (r2 >= r1) goto L_0x0317
            java.util.ArrayList<android.view.View> r4 = r0.mTempViews
            java.lang.Object r4 = r4.get(r2)
            android.view.View r4 = (android.view.View) r4
            int r3 = r0.layoutChildLeft(r4, r3, r12, r14)
            int r2 = r2 + 1
            goto L_0x0306
        L_0x0317:
            java.util.ArrayList<android.view.View> r2 = r0.mTempViews
            r4 = 5
            r0.addCustomViewsWithGravity(r2, r4)
            java.util.ArrayList<android.view.View> r2 = r0.mTempViews
            int r2 = r2.size()
            r4 = 0
        L_0x0324:
            if (r4 >= r2) goto L_0x0335
            java.util.ArrayList<android.view.View> r5 = r0.mTempViews
            java.lang.Object r5 = r5.get(r4)
            android.view.View r5 = (android.view.View) r5
            int r10 = r0.layoutChildRight(r5, r10, r12, r14)
            int r4 = r4 + 1
            goto L_0x0324
        L_0x0335:
            java.util.ArrayList<android.view.View> r4 = r0.mTempViews
            r5 = r17
            r0.addCustomViewsWithGravity(r4, r5)
            java.util.ArrayList<android.view.View> r4 = r0.mTempViews
            int r4 = r0.getViewListMeasuredWidth(r4, r12)
            int r5 = r26 - r30
            int r5 = r5 - r31
            int r5 = r5 / 2
            int r6 = r30 + r5
            int r5 = r4 / 2
            int r7 = r6 - r5
            r17 = r1
            int r1 = r7 + r4
            if (r7 >= r3) goto L_0x0356
            r7 = r3
            goto L_0x035c
        L_0x0356:
            if (r1 <= r10) goto L_0x035c
            int r18 = r1 - r10
            int r7 = r7 - r18
        L_0x035c:
            r18 = r1
            java.util.ArrayList<android.view.View> r1 = r0.mTempViews
            int r1 = r1.size()
            r22 = 0
            r32 = r22
            r22 = r2
            r2 = r32
        L_0x036c:
            if (r2 >= r1) goto L_0x0381
            r23 = r1
            java.util.ArrayList<android.view.View> r1 = r0.mTempViews
            java.lang.Object r1 = r1.get(r2)
            android.view.View r1 = (android.view.View) r1
            int r7 = r0.layoutChildLeft(r1, r7, r12, r14)
            int r2 = r2 + 1
            r1 = r23
            goto L_0x036c
        L_0x0381:
            r23 = r1
            java.util.ArrayList<android.view.View> r1 = r0.mTempViews
            r1.clear()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.Toolbar.onLayout(boolean, int, int, int, int):void");
    }

    private int getViewListMeasuredWidth(List<View> views, int[] collapsingMargins) {
        int collapseLeft = collapsingMargins[0];
        int collapseRight = collapsingMargins[1];
        int width = 0;
        int count = views.size();
        for (int i = 0; i < count; i++) {
            View v = views.get(i);
            LayoutParams lp = (LayoutParams) v.getLayoutParams();
            int l = lp.leftMargin - collapseLeft;
            int r = lp.rightMargin - collapseRight;
            int leftMargin = Math.max(0, l);
            int rightMargin = Math.max(0, r);
            collapseLeft = Math.max(0, -l);
            collapseRight = Math.max(0, -r);
            width += v.getMeasuredWidth() + leftMargin + rightMargin;
        }
        return width;
    }

    private int layoutChildLeft(View child, int left, int[] collapsingMargins, int alignmentHeight) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int l = lp.leftMargin - collapsingMargins[0];
        int left2 = left + Math.max(0, l);
        collapsingMargins[0] = Math.max(0, -l);
        int top = getChildTop(child, alignmentHeight);
        int childWidth = child.getMeasuredWidth();
        child.layout(left2, top, left2 + childWidth, child.getMeasuredHeight() + top);
        return left2 + lp.rightMargin + childWidth;
    }

    private int layoutChildRight(View child, int right, int[] collapsingMargins, int alignmentHeight) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int r = lp.rightMargin - collapsingMargins[1];
        int right2 = right - Math.max(0, r);
        collapsingMargins[1] = Math.max(0, -r);
        int top = getChildTop(child, alignmentHeight);
        int childWidth = child.getMeasuredWidth();
        child.layout(right2 - childWidth, top, right2, child.getMeasuredHeight() + top);
        return right2 - (lp.leftMargin + childWidth);
    }

    private int getChildTop(View child, int alignmentHeight) {
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int childHeight = child.getMeasuredHeight();
        int alignmentOffset = alignmentHeight > 0 ? (childHeight - alignmentHeight) / 2 : 0;
        switch (getChildVerticalGravity(lp.gravity)) {
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE /*48*/:
                return getPaddingTop() - alignmentOffset;
            case GrpcUtil.DEFAULT_PORT_PLAINTEXT:
                return (((getHeight() - getPaddingBottom()) - childHeight) - lp.bottomMargin) - alignmentOffset;
            default:
                int paddingTop = getPaddingTop();
                int paddingBottom = getPaddingBottom();
                int height = getHeight();
                int spaceAbove = (((height - paddingTop) - paddingBottom) - childHeight) / 2;
                if (spaceAbove < lp.topMargin) {
                    spaceAbove = lp.topMargin;
                } else {
                    int spaceBelow = (((height - paddingBottom) - childHeight) - spaceAbove) - paddingTop;
                    if (spaceBelow < lp.bottomMargin) {
                        spaceAbove = Math.max(0, spaceAbove - (lp.bottomMargin - spaceBelow));
                    }
                }
                return paddingTop + spaceAbove;
        }
    }

    private int getChildVerticalGravity(int gravity) {
        int vgrav = gravity & 112;
        switch (vgrav) {
            case 16:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE /*48*/:
            case GrpcUtil.DEFAULT_PORT_PLAINTEXT:
                return vgrav;
            default:
                return this.mGravity & 112;
        }
    }

    private void addCustomViewsWithGravity(List<View> views, int gravity) {
        boolean isRtl = true;
        if (getLayoutDirection() != 1) {
            isRtl = false;
        }
        int childCount = getChildCount();
        int absGrav = GravityCompat.getAbsoluteGravity(gravity, getLayoutDirection());
        views.clear();
        if (isRtl) {
            for (int i = childCount - 1; i >= 0; i--) {
                View child = getChildAt(i);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.mViewType == 0 && shouldLayout(child) && getChildHorizontalGravity(lp.gravity) == absGrav) {
                    views.add(child);
                }
            }
            return;
        }
        for (int i2 = 0; i2 < childCount; i2++) {
            View child2 = getChildAt(i2);
            LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
            if (lp2.mViewType == 0 && shouldLayout(child2) && getChildHorizontalGravity(lp2.gravity) == absGrav) {
                views.add(child2);
            }
        }
    }

    private int getChildHorizontalGravity(int gravity) {
        int ld = getLayoutDirection();
        int hGrav = GravityCompat.getAbsoluteGravity(gravity, ld) & 7;
        switch (hGrav) {
            case 1:
            case 3:
            case 5:
                return hGrav;
            default:
                return ld == 1 ? 5 : 3;
        }
    }

    private boolean shouldLayout(View view) {
        return (view == null || view.getParent() != this || view.getVisibility() == 8) ? false : true;
    }

    private int getHorizontalMargins(View v) {
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        return mlp.getMarginStart() + mlp.getMarginEnd();
    }

    private int getVerticalMargins(View v) {
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        return mlp.topMargin + mlp.bottomMargin;
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        if (p instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) p);
        }
        if (p instanceof ActionBar.LayoutParams) {
            return new LayoutParams((ActionBar.LayoutParams) p);
        }
        if (p instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) p);
        }
        return new LayoutParams(p);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return super.checkLayoutParams(p) && (p instanceof LayoutParams);
    }

    public DecorToolbar getWrapper() {
        if (this.mWrapper == null) {
            this.mWrapper = new ToolbarWidgetWrapper(this, true);
        }
        return this.mWrapper;
    }

    /* access modifiers changed from: package-private */
    public void removeChildrenForExpandedActionView() {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (!(((LayoutParams) child.getLayoutParams()).mViewType == 2 || child == this.mMenuView)) {
                removeViewAt(i);
                this.mHiddenViews.add(child);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void addChildrenForExpandedActionView() {
        for (int i = this.mHiddenViews.size() - 1; i >= 0; i--) {
            addView(this.mHiddenViews.get(i));
        }
        this.mHiddenViews.clear();
    }

    private boolean isChildOrHidden(View child) {
        return child.getParent() == this || this.mHiddenViews.contains(child);
    }

    public void setCollapsible(boolean collapsible) {
        this.mCollapsible = collapsible;
        requestLayout();
    }

    public void setMenuCallbacks(MenuPresenter.Callback pcb, MenuBuilder.Callback mcb) {
        this.mActionMenuPresenterCallback = pcb;
        this.mMenuBuilderCallback = mcb;
        if (this.mMenuView != null) {
            this.mMenuView.setMenuCallbacks(pcb, mcb);
        }
    }

    private void ensureContentInsets() {
        if (this.mContentInsets == null) {
            this.mContentInsets = new RtlSpacingHelper();
        }
    }

    /* access modifiers changed from: package-private */
    public final TextView getTitleTextView() {
        return this.mTitleTextView;
    }

    /* access modifiers changed from: package-private */
    public final TextView getSubtitleTextView() {
        return this.mSubtitleTextView;
    }

    /* access modifiers changed from: package-private */
    public ActionMenuPresenter getOuterActionMenuPresenter() {
        return this.mOuterActionMenuPresenter;
    }

    /* access modifiers changed from: package-private */
    public Context getPopupContext() {
        return this.mPopupContext;
    }

    private ArrayList<MenuItem> getCurrentMenuItems() {
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        Menu menu = getMenu();
        for (int i = 0; i < menu.size(); i++) {
            menuItems.add(menu.getItem(i));
        }
        return menuItems;
    }

    private void onCreateMenu() {
        Menu menu = getMenu();
        ArrayList<MenuItem> oldMenuItemList = getCurrentMenuItems();
        this.mMenuHostHelper.onCreateMenu(menu, getMenuInflater());
        ArrayList<MenuItem> newMenuItemList = getCurrentMenuItems();
        newMenuItemList.removeAll(oldMenuItemList);
        this.mProvidedMenuItems = newMenuItemList;
    }

    public void addMenuProvider(MenuProvider provider) {
        this.mMenuHostHelper.addMenuProvider(provider);
    }

    public void addMenuProvider(MenuProvider provider, LifecycleOwner owner) {
        this.mMenuHostHelper.addMenuProvider(provider, owner);
    }

    public void addMenuProvider(MenuProvider provider, LifecycleOwner owner, Lifecycle.State state) {
        this.mMenuHostHelper.addMenuProvider(provider, owner, state);
    }

    public void removeMenuProvider(MenuProvider provider) {
        this.mMenuHostHelper.removeMenuProvider(provider);
    }

    public void invalidateMenu() {
        Iterator<MenuItem> it = this.mProvidedMenuItems.iterator();
        while (it.hasNext()) {
            getMenu().removeItem(it.next().getItemId());
        }
        onCreateMenu();
    }

    /* access modifiers changed from: package-private */
    public void updateBackInvokedCallbackState() {
        if (Build.VERSION.SDK_INT >= 33) {
            OnBackInvokedDispatcher currentDispatcher = Api33Impl.findOnBackInvokedDispatcher(this);
            boolean shouldBeRegistered = hasExpandedActionView() && currentDispatcher != null && isAttachedToWindow() && this.mBackInvokedCallbackEnabled;
            if (shouldBeRegistered && this.mBackInvokedDispatcher == null) {
                if (this.mBackInvokedCallback == null) {
                    this.mBackInvokedCallback = Api33Impl.newOnBackInvokedCallback(new Toolbar$$ExternalSyntheticLambda0(this));
                }
                Api33Impl.tryRegisterOnBackInvokedCallback(currentDispatcher, this.mBackInvokedCallback);
                this.mBackInvokedDispatcher = currentDispatcher;
            } else if (!shouldBeRegistered && this.mBackInvokedDispatcher != null) {
                Api33Impl.tryUnregisterOnBackInvokedCallback(this.mBackInvokedDispatcher, this.mBackInvokedCallback);
                this.mBackInvokedDispatcher = null;
            }
        }
    }

    public static class LayoutParams extends ActionBar.LayoutParams {
        static final int CUSTOM = 0;
        static final int EXPANDED = 2;
        static final int SYSTEM = 1;
        int mViewType;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.mViewType = 0;
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.mViewType = 0;
            this.gravity = NavigationBarView.ITEM_GRAVITY_START_CENTER;
        }

        public LayoutParams(int width, int height, int gravity) {
            super(width, height);
            this.mViewType = 0;
            this.gravity = gravity;
        }

        public LayoutParams(int gravity) {
            this(-2, -1, gravity);
        }

        public LayoutParams(LayoutParams source) {
            super((ActionBar.LayoutParams) source);
            this.mViewType = 0;
            this.mViewType = source.mViewType;
        }

        public LayoutParams(ActionBar.LayoutParams source) {
            super(source);
            this.mViewType = 0;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super((ViewGroup.LayoutParams) source);
            this.mViewType = 0;
            copyMarginsFromCompat(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
            this.mViewType = 0;
        }

        /* access modifiers changed from: package-private */
        public void copyMarginsFromCompat(ViewGroup.MarginLayoutParams source) {
            this.leftMargin = source.leftMargin;
            this.topMargin = source.topMargin;
            this.rightMargin = source.rightMargin;
            this.bottomMargin = source.bottomMargin;
        }
    }

    public static class SavedState extends AbsSavedState {
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
        int expandedMenuItemId;
        boolean isOverflowOpen;

        public SavedState(Parcel source) {
            this(source, (ClassLoader) null);
        }

        public SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            this.expandedMenuItemId = source.readInt();
            this.isOverflowOpen = source.readInt() != 0;
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.expandedMenuItemId);
            out.writeInt(this.isOverflowOpen ? 1 : 0);
        }
    }

    private class ExpandedActionViewMenuPresenter implements MenuPresenter {
        MenuItemImpl mCurrentExpandedItem;
        MenuBuilder mMenu;

        ExpandedActionViewMenuPresenter() {
        }

        public void initForMenu(Context context, MenuBuilder menu) {
            if (!(this.mMenu == null || this.mCurrentExpandedItem == null)) {
                this.mMenu.collapseItemActionView(this.mCurrentExpandedItem);
            }
            this.mMenu = menu;
        }

        public MenuView getMenuView(ViewGroup root) {
            return null;
        }

        public void updateMenuView(boolean cleared) {
            if (this.mCurrentExpandedItem != null) {
                boolean found = false;
                if (this.mMenu != null) {
                    int count = this.mMenu.size();
                    int i = 0;
                    while (true) {
                        if (i >= count) {
                            break;
                        } else if (this.mMenu.getItem(i) == this.mCurrentExpandedItem) {
                            found = true;
                            break;
                        } else {
                            i++;
                        }
                    }
                }
                if (!found) {
                    collapseItemActionView(this.mMenu, this.mCurrentExpandedItem);
                }
            }
        }

        public void setCallback(MenuPresenter.Callback cb) {
        }

        public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
            return false;
        }

        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        }

        public boolean flagActionItems() {
            return false;
        }

        public boolean expandItemActionView(MenuBuilder menu, MenuItemImpl item) {
            Toolbar.this.ensureCollapseButtonView();
            ViewParent collapseButtonParent = Toolbar.this.mCollapseButtonView.getParent();
            if (collapseButtonParent != Toolbar.this) {
                if (collapseButtonParent instanceof ViewGroup) {
                    ((ViewGroup) collapseButtonParent).removeView(Toolbar.this.mCollapseButtonView);
                }
                Toolbar.this.addView(Toolbar.this.mCollapseButtonView);
            }
            Toolbar.this.mExpandedActionView = item.getActionView();
            this.mCurrentExpandedItem = item;
            ViewParent expandedActionParent = Toolbar.this.mExpandedActionView.getParent();
            if (expandedActionParent != Toolbar.this) {
                if (expandedActionParent instanceof ViewGroup) {
                    ((ViewGroup) expandedActionParent).removeView(Toolbar.this.mExpandedActionView);
                }
                LayoutParams lp = Toolbar.this.generateDefaultLayoutParams();
                lp.gravity = (Toolbar.this.mButtonGravity & 112) | GravityCompat.START;
                lp.mViewType = 2;
                Toolbar.this.mExpandedActionView.setLayoutParams(lp);
                Toolbar.this.addView(Toolbar.this.mExpandedActionView);
            }
            Toolbar.this.removeChildrenForExpandedActionView();
            Toolbar.this.requestLayout();
            item.setActionViewExpanded(true);
            if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView) Toolbar.this.mExpandedActionView).onActionViewExpanded();
            }
            Toolbar.this.updateBackInvokedCallbackState();
            return true;
        }

        public boolean collapseItemActionView(MenuBuilder menu, MenuItemImpl item) {
            if (Toolbar.this.mExpandedActionView instanceof CollapsibleActionView) {
                ((CollapsibleActionView) Toolbar.this.mExpandedActionView).onActionViewCollapsed();
            }
            Toolbar.this.removeView(Toolbar.this.mExpandedActionView);
            Toolbar.this.removeView(Toolbar.this.mCollapseButtonView);
            Toolbar.this.mExpandedActionView = null;
            Toolbar.this.addChildrenForExpandedActionView();
            this.mCurrentExpandedItem = null;
            Toolbar.this.requestLayout();
            item.setActionViewExpanded(false);
            Toolbar.this.updateBackInvokedCallbackState();
            return true;
        }

        public int getId() {
            return 0;
        }

        public Parcelable onSaveInstanceState() {
            return null;
        }

        public void onRestoreInstanceState(Parcelable state) {
        }
    }

    static class Api33Impl {
        private Api33Impl() {
        }

        static void tryRegisterOnBackInvokedCallback(Object dispatcherObj, Object callback) {
            ((OnBackInvokedDispatcher) dispatcherObj).registerOnBackInvokedCallback(DurationKt.NANOS_IN_MILLIS, (OnBackInvokedCallback) callback);
        }

        static void tryUnregisterOnBackInvokedCallback(Object dispatcherObj, Object callbackObj) {
            ((OnBackInvokedDispatcher) dispatcherObj).unregisterOnBackInvokedCallback((OnBackInvokedCallback) callbackObj);
        }

        static OnBackInvokedDispatcher findOnBackInvokedDispatcher(View view) {
            return view.findOnBackInvokedDispatcher();
        }

        static OnBackInvokedCallback newOnBackInvokedCallback(Runnable action) {
            Objects.requireNonNull(action);
            return new Toolbar$Api33Impl$$ExternalSyntheticLambda0(action);
        }
    }
}
