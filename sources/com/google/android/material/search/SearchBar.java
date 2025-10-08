package com.google.android.material.search;

import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.TextViewCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ToolbarUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;

public class SearchBar extends Toolbar {
    private static final int DEFAULT_SCROLL_FLAGS = 53;
    private static final int DEF_STYLE_RES = R.style.Widget_Material3_SearchBar;
    private static final String NAMESPACE_APP = "http://schemas.android.com/apk/res-auto";
    /* access modifiers changed from: private */
    public final int backgroundColor;
    /* access modifiers changed from: private */
    public MaterialShapeDrawable backgroundShape;
    private View centerView;
    private final boolean defaultMarginsEnabled;
    private final Drawable defaultNavigationIcon;
    private boolean defaultScrollFlagsEnabled;
    private final boolean forceDefaultNavigationOnClickListener;
    private final boolean layoutInflated;
    private final AppBarLayout.LiftOnScrollProgressListener liftColorListener;
    private boolean liftOnScroll;
    /* access modifiers changed from: private */
    public final ColorStateList liftOnScrollColor;
    private int maxWidth;
    private int menuResId;
    private ActionMenuView menuView;
    private ImageButton navIconButton;
    private Integer navigationIconTint;
    private Drawable originalNavigationIconBackground;
    private final TextView placeholderTextView;
    private final SearchBarAnimationHelper searchBarAnimationHelper;
    private boolean textCentered;
    private final TextView textView;
    private final FrameLayout textViewContainer;
    private final boolean tintNavigationIcon;

    public SearchBar(Context context) {
        this(context, (AttributeSet) null);
    }

    public SearchBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.materialSearchBarStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public SearchBar(android.content.Context r17, android.util.AttributeSet r18, int r19) {
        /*
            r16 = this;
            r0 = r16
            r2 = r18
            r4 = r19
            int r1 = DEF_STYLE_RES
            r3 = r17
            android.content.Context r1 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r3, r2, r4, r1)
            r0.<init>(r1, r2, r4)
            r7 = -1
            r0.menuResId = r7
            com.google.android.material.search.SearchBar$1 r1 = new com.google.android.material.search.SearchBar$1
            r1.<init>()
            r0.liftColorListener = r1
            android.content.Context r1 = r0.getContext()
            r0.validateAttributes(r2)
            int r3 = r0.getDefaultNavigationIconResource()
            android.graphics.drawable.Drawable r3 = androidx.appcompat.content.res.AppCompatResources.getDrawable(r1, r3)
            r0.defaultNavigationIcon = r3
            com.google.android.material.search.SearchBarAnimationHelper r3 = new com.google.android.material.search.SearchBarAnimationHelper
            r3.<init>()
            r0.searchBarAnimationHelper = r3
            int[] r3 = com.google.android.material.R.styleable.SearchBar
            int r5 = DEF_STYLE_RES
            r8 = 0
            int[] r6 = new int[r8]
            android.content.res.TypedArray r6 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r1, r2, r3, r4, r5, r6)
            r11 = r1
            r9 = r2
            r10 = r4
            int r1 = DEF_STYLE_RES
            com.google.android.material.shape.ShapeAppearanceModel$Builder r1 = com.google.android.material.shape.ShapeAppearanceModel.builder((android.content.Context) r11, (android.util.AttributeSet) r9, (int) r10, (int) r1)
            com.google.android.material.shape.ShapeAppearanceModel r1 = r1.build()
            int r2 = com.google.android.material.R.styleable.SearchBar_backgroundTint
            int r2 = r6.getColor(r2, r8)
            r0.backgroundColor = r2
            int r2 = com.google.android.material.R.styleable.SearchBar_liftOnScrollColor
            android.content.res.ColorStateList r2 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r11, (android.content.res.TypedArray) r6, (int) r2)
            r0.liftOnScrollColor = r2
            int r2 = com.google.android.material.R.styleable.SearchBar_elevation
            r3 = 0
            float r3 = r6.getDimension(r2, r3)
            int r2 = com.google.android.material.R.styleable.SearchBar_defaultMarginsEnabled
            r4 = 1
            boolean r2 = r6.getBoolean(r2, r4)
            r0.defaultMarginsEnabled = r2
            int r2 = com.google.android.material.R.styleable.SearchBar_defaultScrollFlagsEnabled
            boolean r2 = r6.getBoolean(r2, r4)
            r0.defaultScrollFlagsEnabled = r2
            int r2 = com.google.android.material.R.styleable.SearchBar_hideNavigationIcon
            boolean r12 = r6.getBoolean(r2, r8)
            int r2 = com.google.android.material.R.styleable.SearchBar_forceDefaultNavigationOnClickListener
            boolean r2 = r6.getBoolean(r2, r8)
            r0.forceDefaultNavigationOnClickListener = r2
            int r2 = com.google.android.material.R.styleable.SearchBar_tintNavigationIcon
            boolean r2 = r6.getBoolean(r2, r4)
            r0.tintNavigationIcon = r2
            int r2 = com.google.android.material.R.styleable.SearchBar_navigationIconTint
            boolean r2 = r6.hasValue(r2)
            if (r2 == 0) goto L_0x009e
            int r2 = com.google.android.material.R.styleable.SearchBar_navigationIconTint
            int r2 = r6.getColor(r2, r7)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r0.navigationIconTint = r2
        L_0x009e:
            int r2 = com.google.android.material.R.styleable.SearchBar_android_textAppearance
            int r13 = r6.getResourceId(r2, r7)
            int r2 = com.google.android.material.R.styleable.SearchBar_android_text
            java.lang.String r14 = r6.getString(r2)
            int r2 = com.google.android.material.R.styleable.SearchBar_android_hint
            java.lang.String r15 = r6.getString(r2)
            int r2 = com.google.android.material.R.styleable.SearchBar_strokeWidth
            r5 = -1082130432(0xffffffffbf800000, float:-1.0)
            float r2 = r6.getDimension(r2, r5)
            int r5 = com.google.android.material.R.styleable.SearchBar_strokeColor
            int r5 = r6.getColor(r5, r8)
            int r4 = com.google.android.material.R.styleable.SearchBar_textCentered
            boolean r4 = r6.getBoolean(r4, r8)
            r0.textCentered = r4
            int r4 = com.google.android.material.R.styleable.SearchBar_liftOnScroll
            boolean r4 = r6.getBoolean(r4, r8)
            r0.liftOnScroll = r4
            int r4 = com.google.android.material.R.styleable.SearchBar_android_maxWidth
            int r4 = r6.getDimensionPixelSize(r4, r7)
            r0.maxWidth = r4
            r6.recycle()
            if (r12 != 0) goto L_0x00de
            r0.initNavigationIcon()
        L_0x00de:
            r4 = 1
            r0.setClickable(r4)
            r0.setFocusable(r4)
            android.view.LayoutInflater r7 = android.view.LayoutInflater.from(r11)
            int r8 = com.google.android.material.R.layout.mtrl_search_bar
            r7.inflate(r8, r0)
            r0.layoutInflated = r4
            int r4 = com.google.android.material.R.id.open_search_bar_text_view
            android.view.View r4 = r0.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r0.textView = r4
            int r4 = com.google.android.material.R.id.open_search_bar_placeholder_text_view
            android.view.View r4 = r0.findViewById(r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            r0.placeholderTextView = r4
            int r4 = com.google.android.material.R.id.open_search_bar_text_view_container
            android.view.View r4 = r0.findViewById(r4)
            android.widget.FrameLayout r4 = (android.widget.FrameLayout) r4
            r0.textViewContainer = r4
            r0.setElevation(r3)
            r0.initTextView(r13, r14, r15)
            r4 = r2
            int r2 = r0.backgroundColor
            r0.initBackground(r1, r2, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.search.SearchBar.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    /* access modifiers changed from: package-private */
    public void setPlaceholderText(String string) {
        this.placeholderTextView.setText(string);
    }

    private void validateAttributes(AttributeSet attributeSet) {
        if (attributeSet != null) {
            if (attributeSet.getAttributeValue(NAMESPACE_APP, "title") != null) {
                throw new UnsupportedOperationException("SearchBar does not support title. Use hint or text instead.");
            } else if (attributeSet.getAttributeValue(NAMESPACE_APP, "subtitle") != null) {
                throw new UnsupportedOperationException("SearchBar does not support subtitle. Use hint or text instead.");
            }
        }
    }

    private AppBarLayout getAppBarLayoutParentIfExists() {
        for (ViewParent v = getParent(); v != null; v = v.getParent()) {
            if (v instanceof AppBarLayout) {
                return (AppBarLayout) v;
            }
        }
        return null;
    }

    private void initNavigationIcon() {
        setNavigationIcon(getNavigationIcon() == null ? this.defaultNavigationIcon : getNavigationIcon());
        setNavigationIconDecorative(true);
    }

    private void initTextView(int textAppearanceResId, String text, String hint) {
        if (textAppearanceResId != -1) {
            TextViewCompat.setTextAppearance(this.textView, textAppearanceResId);
            TextViewCompat.setTextAppearance(this.placeholderTextView, textAppearanceResId);
        }
        setText((CharSequence) text);
        setHint((CharSequence) hint);
        setTextCentered(this.textCentered);
    }

    private void initBackground(ShapeAppearanceModel shapeAppearance, int backgroundColor2, float elevation, float strokeWidth, int strokeColor) {
        this.backgroundShape = new MaterialShapeDrawable(shapeAppearance);
        this.backgroundShape.initializeElevationOverlay(getContext());
        this.backgroundShape.setElevation(elevation);
        if (strokeWidth >= 0.0f) {
            this.backgroundShape.setStroke(strokeWidth, strokeColor);
        }
        int rippleColor = MaterialColors.getColor(this, androidx.appcompat.R.attr.colorControlHighlight);
        this.backgroundShape.setFillColor(ColorStateList.valueOf(backgroundColor2));
        setBackground(new RippleDrawable(ColorStateList.valueOf(rippleColor), this.backgroundShape, this.backgroundShape));
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (this.layoutInflated && this.centerView == null && !(child instanceof ActionMenuView)) {
            this.centerView = child;
            this.centerView.setAlpha(0.0f);
        }
        super.addView(child, index, params);
    }

    public void setElevation(float elevation) {
        super.setElevation(elevation);
        if (this.backgroundShape != null) {
            this.backgroundShape.setElevation(elevation);
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(EditText.class.getCanonicalName());
        info.setEditable(isEnabled());
        CharSequence text = getText();
        boolean isTextEmpty = TextUtils.isEmpty(text);
        if (Build.VERSION.SDK_INT >= 26) {
            info.setHintText(getHint());
            info.setShowingHintText(isTextEmpty);
        }
        if (isTextEmpty) {
            text = getHint();
        }
        info.setText(text);
    }

    public void setNavigationOnClickListener(View.OnClickListener listener) {
        if (!this.forceDefaultNavigationOnClickListener) {
            super.setNavigationOnClickListener(listener);
            setNavigationIconDecorative(listener == null);
        }
    }

    public void setNavigationIcon(Drawable navigationIcon) {
        super.setNavigationIcon(maybeTintNavigationIcon(navigationIcon));
    }

    private Drawable maybeTintNavigationIcon(Drawable navigationIcon) {
        int navigationIconColorAttr;
        int navigationIconColorAttr2;
        if (!this.tintNavigationIcon || navigationIcon == null) {
            return navigationIcon;
        }
        if (this.navigationIconTint != null) {
            navigationIconColorAttr = this.navigationIconTint.intValue();
        } else {
            if (navigationIcon == this.defaultNavigationIcon) {
                navigationIconColorAttr2 = R.attr.colorOnSurfaceVariant;
            } else {
                navigationIconColorAttr2 = R.attr.colorOnSurface;
            }
            navigationIconColorAttr = MaterialColors.getColor(this, navigationIconColorAttr2);
        }
        Drawable wrappedNavigationIcon = DrawableCompat.wrap(navigationIcon.mutate());
        wrappedNavigationIcon.setTint(navigationIconColorAttr);
        return wrappedNavigationIcon;
    }

    private void setNavigationIconDecorative(boolean decorative) {
        ImageButton navigationIconButton = ToolbarUtils.getNavigationIconButton(this);
        if (navigationIconButton != null) {
            navigationIconButton.setClickable(!decorative);
            navigationIconButton.setFocusable(!decorative);
            Drawable navigationIconBackground = navigationIconButton.getBackground();
            if (navigationIconBackground != null) {
                this.originalNavigationIconBackground = navigationIconBackground;
            }
            navigationIconButton.setBackgroundDrawable(decorative ? null : this.originalNavigationIconBackground);
            setHandwritingBoundsInsets();
        }
    }

    public void inflateMenu(int resId) {
        super.inflateMenu(resId);
        this.menuResId = resId;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.maxWidth >= 0 && this.maxWidth < View.MeasureSpec.getSize(widthMeasureSpec)) {
            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.maxWidth, View.MeasureSpec.getMode(widthMeasureSpec));
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureCenterView(widthMeasureSpec, heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int i = bottom;
        int bottom2 = right;
        int right2 = top;
        int top2 = left;
        int left2 = changed;
        if (this.centerView != null) {
            layoutViewInCenter(this.centerView);
        }
        setHandwritingBoundsInsets();
        if (this.textView != null && this.textCentered) {
            layoutTextViewCenterAvoidToolbarViewsAndPadding();
        }
    }

    public void setLiftOnScroll(boolean liftOnScroll2) {
        this.liftOnScroll = liftOnScroll2;
        if (liftOnScroll2) {
            addLiftOnScrollProgressListener();
        } else {
            removeLiftOnScrollProgressListener();
        }
    }

    public boolean isLiftOnScroll() {
        return this.liftOnScroll;
    }

    private void addLiftOnScrollProgressListener() {
        AppBarLayout appBarLayout = getAppBarLayoutParentIfExists();
        if (appBarLayout != null && this.liftOnScrollColor != null) {
            appBarLayout.addLiftOnScrollProgressListener(this.liftColorListener);
        }
    }

    private void removeLiftOnScrollProgressListener() {
        AppBarLayout appBarLayout = getAppBarLayoutParentIfExists();
        if (appBarLayout != null) {
            appBarLayout.removeLiftOnScrollProgressListener(this.liftColorListener);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this, this.backgroundShape);
        setDefaultMargins();
        setOrClearDefaultScrollFlags();
        if (this.liftOnScroll) {
            addLiftOnScrollProgressListener();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeLiftOnScrollProgressListener();
    }

    public void setTitle(CharSequence title) {
    }

    public void setSubtitle(CharSequence subtitle) {
    }

    private void setDefaultMargins() {
        if (this.defaultMarginsEnabled && (getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            Resources resources = getResources();
            int marginHorizontal = resources.getDimensionPixelSize(R.dimen.m3_searchbar_margin_horizontal);
            int marginVertical = resources.getDimensionPixelSize(getDefaultMarginVerticalResource());
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) getLayoutParams();
            lp.leftMargin = defaultIfZero(lp.leftMargin, marginHorizontal);
            lp.topMargin = defaultIfZero(lp.topMargin, marginVertical);
            lp.rightMargin = defaultIfZero(lp.rightMargin, marginHorizontal);
            lp.bottomMargin = defaultIfZero(lp.bottomMargin, marginVertical);
        }
    }

    /* access modifiers changed from: protected */
    public int getDefaultMarginVerticalResource() {
        return R.dimen.m3_searchbar_margin_vertical;
    }

    /* access modifiers changed from: protected */
    public int getDefaultNavigationIconResource() {
        return R.drawable.ic_search_black_24;
    }

    private int defaultIfZero(int value, int defValue) {
        return value == 0 ? defValue : value;
    }

    private void setOrClearDefaultScrollFlags() {
        if (getLayoutParams() instanceof AppBarLayout.LayoutParams) {
            AppBarLayout.LayoutParams lp = (AppBarLayout.LayoutParams) getLayoutParams();
            if (this.defaultScrollFlagsEnabled) {
                if (lp.getScrollFlags() == 0) {
                    lp.setScrollFlags(53);
                }
            } else if (lp.getScrollFlags() == 53) {
                lp.setScrollFlags(0);
            }
        }
    }

    private void measureCenterView(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.centerView != null) {
            this.centerView.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private ActionMenuView findOrGetMenuView() {
        if (this.menuView == null) {
            this.menuView = ToolbarUtils.getActionMenuView(this);
        }
        return this.menuView;
    }

    private ImageButton findOrGetNavView() {
        if (this.navIconButton == null) {
            this.navIconButton = ToolbarUtils.getNavigationIconButton(this);
        }
        return this.navIconButton;
    }

    private void layoutTextViewCenterAvoidToolbarViewsAndPadding() {
        int textViewContainerLeft;
        int textViewContainerLeft2 = (getMeasuredWidth() / 2) - (this.textViewContainer.getMeasuredWidth() / 2);
        int textViewContainerRight = this.textViewContainer.getMeasuredWidth() + textViewContainerLeft2;
        int textViewContainerTop = (getMeasuredHeight() / 2) - (this.textViewContainer.getMeasuredHeight() / 2);
        int textViewContainerBottom = this.textViewContainer.getMeasuredHeight() + textViewContainerTop;
        boolean isRtl = true;
        if (getLayoutDirection() != 1) {
            isRtl = false;
        }
        View menuView2 = findOrGetMenuView();
        View navIconButton2 = findOrGetNavView();
        int textViewLeft = (this.textViewContainer.getMeasuredWidth() / 2) - (this.textView.getMeasuredWidth() / 2);
        int left = textViewLeft + textViewContainerLeft2;
        int right = textViewContainerLeft2 + this.textView.getMeasuredWidth() + textViewLeft;
        View leftView = isRtl ? menuView2 : navIconButton2;
        View rightView = isRtl ? navIconButton2 : menuView2;
        int leftShift = 0;
        int rightShift = 0;
        if (leftView != null) {
            textViewContainerLeft = textViewContainerLeft2;
            leftShift = Math.max(leftView.getRight() - left, 0);
        } else {
            textViewContainerLeft = textViewContainerLeft2;
        }
        int left2 = left + leftShift;
        int right2 = right + leftShift;
        if (rightView != null) {
            rightShift = Math.max(right2 - rightView.getLeft(), 0);
        }
        int left3 = left2 - rightShift;
        int right3 = right2 - rightShift;
        int paddingLeftShift = Math.max(getPaddingLeft() - left3, getContentInsetLeft() - left3);
        int textViewContainerRight2 = textViewContainerRight;
        int paddingRightShift = Math.max(right3 - (getMeasuredWidth() - getPaddingRight()), right3 - (getMeasuredWidth() - getContentInsetRight()));
        int paddingLeftShift2 = Math.max(paddingLeftShift, 0);
        int paddingRightShift2 = Math.max(paddingRightShift, 0);
        int totalShift = ((leftShift - rightShift) + paddingLeftShift2) - paddingRightShift2;
        int i = paddingLeftShift2;
        int i2 = paddingRightShift2;
        this.textViewContainer.layout(textViewContainerLeft + totalShift, textViewContainerTop, textViewContainerRight2 + totalShift, textViewContainerBottom);
    }

    private void layoutViewInCenter(View view) {
        if (view != null) {
            int viewWidth = view.getMeasuredWidth();
            int left = (getMeasuredWidth() / 2) - (viewWidth / 2);
            int viewHeight = view.getMeasuredHeight();
            int top = (getMeasuredHeight() / 2) - (viewHeight / 2);
            layoutChild(view, left, top, left + viewWidth, top + viewHeight);
        }
    }

    private void layoutChild(View child, int left, int top, int right, int bottom) {
        if (getLayoutDirection() == 1) {
            child.layout(getMeasuredWidth() - right, top, getMeasuredWidth() - left, bottom);
        } else {
            child.layout(left, top, right, bottom);
        }
    }

    private void setHandwritingBoundsInsets() {
        if (Build.VERSION.SDK_INT >= 34) {
            boolean isRtl = true;
            if (getLayoutDirection() != 1) {
                isRtl = false;
            }
            int startInset = 0;
            View navigationIconButton = ToolbarUtils.getNavigationIconButton(this);
            if (navigationIconButton != null && navigationIconButton.isClickable()) {
                startInset = isRtl ? getWidth() - navigationIconButton.getLeft() : navigationIconButton.getRight();
            }
            int endInset = 0;
            View actionMenuView = ToolbarUtils.getActionMenuView(this);
            if (actionMenuView != null) {
                endInset = isRtl ? actionMenuView.getRight() : getWidth() - actionMenuView.getLeft();
            }
            setHandwritingBoundsOffsets((float) (-(isRtl ? endInset : startInset)), 0.0f, (float) (-(isRtl ? startInset : endInset)), 0.0f);
        }
    }

    public View getCenterView() {
        return this.centerView;
    }

    public void setCenterView(View view) {
        if (this.centerView != null) {
            removeView(this.centerView);
            this.centerView = null;
        }
        if (view != null) {
            addView(view);
        }
    }

    /* access modifiers changed from: package-private */
    public TextView getPlaceholderTextView() {
        return this.placeholderTextView;
    }

    public TextView getTextView() {
        return this.textView;
    }

    public CharSequence getText() {
        return this.textView.getText();
    }

    public void setText(CharSequence text) {
        this.textView.setText(text);
        this.placeholderTextView.setText(text);
    }

    public void setText(int textResId) {
        this.textView.setText(textResId);
        this.placeholderTextView.setText(textResId);
    }

    public void setTextCentered(boolean textCentered2) {
        this.textCentered = textCentered2;
        if (this.textView != null) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) this.textView.getLayoutParams();
            if (textCentered2) {
                lp.gravity = 1;
                this.textView.setGravity(1);
            } else {
                lp.gravity = 0;
                this.textView.setGravity(0);
            }
            this.textView.setLayoutParams(lp);
            this.placeholderTextView.setLayoutParams(lp);
        }
    }

    public boolean getTextCentered() {
        return this.textCentered;
    }

    public void clearText() {
        this.textView.setText("");
        this.placeholderTextView.setText("");
    }

    public CharSequence getHint() {
        return this.textView.getHint();
    }

    public void setHint(CharSequence hint) {
        this.textView.setHint(hint);
    }

    public void setHint(int hintResId) {
        this.textView.setHint(hintResId);
    }

    public int getStrokeColor() {
        return this.backgroundShape.getStrokeColor().getDefaultColor();
    }

    public void setStrokeColor(int strokeColor) {
        if (getStrokeColor() != strokeColor) {
            this.backgroundShape.setStrokeColor(ColorStateList.valueOf(strokeColor));
        }
    }

    public float getStrokeWidth() {
        return this.backgroundShape.getStrokeWidth();
    }

    public void setStrokeWidth(float strokeWidth) {
        if (getStrokeWidth() != strokeWidth) {
            this.backgroundShape.setStrokeWidth(strokeWidth);
        }
    }

    public float getCornerSize() {
        return this.backgroundShape.getTopLeftCornerResolvedSize();
    }

    public boolean isDefaultScrollFlagsEnabled() {
        return this.defaultScrollFlagsEnabled;
    }

    public void setDefaultScrollFlagsEnabled(boolean defaultScrollFlagsEnabled2) {
        this.defaultScrollFlagsEnabled = defaultScrollFlagsEnabled2;
        setOrClearDefaultScrollFlags();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$startOnLoadAnimation$0$com-google-android-material-search-SearchBar  reason: not valid java name */
    public /* synthetic */ void m1698lambda$startOnLoadAnimation$0$comgoogleandroidmaterialsearchSearchBar() {
        this.searchBarAnimationHelper.startOnLoadAnimation(this);
    }

    public void startOnLoadAnimation() {
        post(new SearchBar$$ExternalSyntheticLambda0(this));
    }

    public void stopOnLoadAnimation() {
        this.searchBarAnimationHelper.stopOnLoadAnimation(this);
    }

    public boolean isOnLoadAnimationFadeInEnabled() {
        return this.searchBarAnimationHelper.isOnLoadAnimationFadeInEnabled();
    }

    public void setOnLoadAnimationFadeInEnabled(boolean onLoadAnimationFadeInEnabled) {
        this.searchBarAnimationHelper.setOnLoadAnimationFadeInEnabled(onLoadAnimationFadeInEnabled);
    }

    public void addOnLoadAnimationCallback(OnLoadAnimationCallback onLoadAnimationCallback) {
        this.searchBarAnimationHelper.addOnLoadAnimationCallback(onLoadAnimationCallback);
    }

    public boolean removeOnLoadAnimationCallback(OnLoadAnimationCallback onLoadAnimationCallback) {
        return this.searchBarAnimationHelper.removeOnLoadAnimationCallback(onLoadAnimationCallback);
    }

    public boolean isExpanding() {
        return this.searchBarAnimationHelper.isExpanding();
    }

    public boolean expand(View expandedView) {
        return expand(expandedView, (AppBarLayout) null);
    }

    public boolean expand(View expandedView, AppBarLayout appBarLayout) {
        return expand(expandedView, appBarLayout, false);
    }

    public boolean expand(View expandedView, AppBarLayout appBarLayout, boolean skipAnimation) {
        if ((expandedView.getVisibility() == 0 || isExpanding()) && !isCollapsing()) {
            return false;
        }
        this.searchBarAnimationHelper.startExpandAnimation(this, expandedView, appBarLayout, skipAnimation);
        return true;
    }

    public void addExpandAnimationListener(AnimatorListenerAdapter listener) {
        this.searchBarAnimationHelper.addExpandAnimationListener(listener);
    }

    public boolean removeExpandAnimationListener(AnimatorListenerAdapter listener) {
        return this.searchBarAnimationHelper.removeExpandAnimationListener(listener);
    }

    public boolean isCollapsing() {
        return this.searchBarAnimationHelper.isCollapsing();
    }

    public boolean collapse(View expandedView) {
        return collapse(expandedView, (AppBarLayout) null);
    }

    public boolean collapse(View expandedView, AppBarLayout appBarLayout) {
        return collapse(expandedView, appBarLayout, false);
    }

    public boolean collapse(View expandedView, AppBarLayout appBarLayout, boolean skipAnimation) {
        if ((expandedView.getVisibility() != 0 || isCollapsing()) && !isExpanding()) {
            return false;
        }
        this.searchBarAnimationHelper.startCollapseAnimation(this, expandedView, appBarLayout, skipAnimation);
        return true;
    }

    public void addCollapseAnimationListener(AnimatorListenerAdapter listener) {
        this.searchBarAnimationHelper.addCollapseAnimationListener(listener);
    }

    public boolean removeCollapseAnimationListener(AnimatorListenerAdapter listener) {
        return this.searchBarAnimationHelper.removeCollapseAnimationListener(listener);
    }

    /* access modifiers changed from: package-private */
    public int getMenuResId() {
        return this.menuResId;
    }

    /* access modifiers changed from: package-private */
    public float getCompatElevation() {
        return this.backgroundShape != null ? this.backgroundShape.getElevation() : getElevation();
    }

    public void setMaxWidth(int maxWidth2) {
        if (this.maxWidth != maxWidth2) {
            this.maxWidth = maxWidth2;
            requestLayout();
        }
    }

    public int getMaxWidth() {
        return this.maxWidth;
    }

    public static class ScrollingViewBehavior extends AppBarLayout.ScrollingViewBehavior {
        private boolean initialized = false;

        public ScrollingViewBehavior() {
        }

        public ScrollingViewBehavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
            boolean changed = super.onDependentViewChanged(parent, child, dependency);
            if (!this.initialized && (dependency instanceof AppBarLayout)) {
                this.initialized = true;
                setAppBarLayoutTransparent((AppBarLayout) dependency);
            }
            return changed;
        }

        private void setAppBarLayoutTransparent(AppBarLayout appBarLayout) {
            appBarLayout.setBackgroundColor(0);
            appBarLayout.setTargetElevation(0.0f);
        }

        /* access modifiers changed from: protected */
        public boolean shouldHeaderOverlapScrollingChild() {
            return true;
        }
    }

    public static abstract class OnLoadAnimationCallback {
        public void onAnimationStart() {
        }

        public void onAnimationEnd() {
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        CharSequence text = getText();
        savedState.text = text == null ? null : text.toString();
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
        setText((CharSequence) savedState.text);
    }

    static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
            public SavedState createFromParcel(Parcel source, ClassLoader loader) {
                return new SavedState(source, loader);
            }

            public SavedState createFromParcel(Parcel source) {
                return new SavedState(source);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        String text;

        public SavedState(Parcel source) {
            this(source, (ClassLoader) null);
        }

        public SavedState(Parcel source, ClassLoader classLoader) {
            super(source, classLoader);
            this.text = source.readString();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeString(this.text);
        }
    }
}
