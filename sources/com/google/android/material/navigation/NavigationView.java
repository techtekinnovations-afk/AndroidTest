package com.google.android.material.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import androidx.activity.BackEventCompat;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ContextUtils;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.internal.NavigationMenuPresenter;
import com.google.android.material.internal.ScrimInsetsFrameLayout;
import com.google.android.material.internal.WindowUtils;
import com.google.android.material.motion.MaterialBackHandler;
import com.google.android.material.motion.MaterialBackOrchestrator;
import com.google.android.material.motion.MaterialSideContainerBackHelper;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeableDelegate;

public class NavigationView extends ScrimInsetsFrameLayout implements MaterialBackHandler {
    private static final int[] CHECKED_STATE_SET = {16842912};
    private static final int DEF_STYLE_RES = R.style.Widget_Design_NavigationView;
    private static final int[] DISABLED_STATE_SET = {-16842910};
    private static final int PRESENTER_NAVIGATION_VIEW_ID = 1;
    private final DrawerLayout.DrawerListener backDrawerListener;
    /* access modifiers changed from: private */
    public final MaterialBackOrchestrator backOrchestrator;
    private boolean bottomInsetScrimEnabled;
    private int drawerLayoutCornerSize;
    private final boolean drawerLayoutCornerSizeBackAnimationEnabled;
    private final int drawerLayoutCornerSizeBackAnimationMax;
    private boolean endInsetScrimEnabled;
    OnNavigationItemSelectedListener listener;
    private final int maxWidth;
    private final NavigationMenu menu;
    private MenuInflater menuInflater;
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
    /* access modifiers changed from: private */
    public final NavigationMenuPresenter presenter;
    private final ShapeableDelegate shapeableDelegate;
    private final MaterialSideContainerBackHelper sideContainerBackHelper;
    private boolean startInsetScrimEnabled;
    /* access modifiers changed from: private */
    public final int[] tmpLocation;
    private boolean topInsetScrimEnabled;

    public interface OnNavigationItemSelectedListener {
        boolean onNavigationItemSelected(MenuItem menuItem);
    }

    public NavigationView(Context context) {
        this(context, (AttributeSet) null);
    }

    public NavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.navigationViewStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public NavigationView(android.content.Context r20, android.util.AttributeSet r21, int r22) {
        /*
            r19 = this;
            r0 = r19
            r2 = r21
            r4 = r22
            int r1 = DEF_STYLE_RES
            r3 = r20
            android.content.Context r1 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r3, r2, r4, r1)
            r0.<init>(r1, r2, r4)
            com.google.android.material.internal.NavigationMenuPresenter r1 = new com.google.android.material.internal.NavigationMenuPresenter
            r1.<init>()
            r0.presenter = r1
            r1 = 2
            int[] r1 = new int[r1]
            r0.tmpLocation = r1
            r7 = 1
            r0.topInsetScrimEnabled = r7
            r0.bottomInsetScrimEnabled = r7
            r0.startInsetScrimEnabled = r7
            r0.endInsetScrimEnabled = r7
            r8 = 0
            r0.drawerLayoutCornerSize = r8
            com.google.android.material.shape.ShapeableDelegate r1 = com.google.android.material.shape.ShapeableDelegate.create(r0)
            r0.shapeableDelegate = r1
            com.google.android.material.motion.MaterialSideContainerBackHelper r1 = new com.google.android.material.motion.MaterialSideContainerBackHelper
            r1.<init>(r0)
            r0.sideContainerBackHelper = r1
            com.google.android.material.motion.MaterialBackOrchestrator r1 = new com.google.android.material.motion.MaterialBackOrchestrator
            r1.<init>(r0)
            r0.backOrchestrator = r1
            com.google.android.material.navigation.NavigationView$1 r1 = new com.google.android.material.navigation.NavigationView$1
            r1.<init>()
            r0.backDrawerListener = r1
            android.content.Context r1 = r0.getContext()
            com.google.android.material.internal.NavigationMenu r3 = new com.google.android.material.internal.NavigationMenu
            r3.<init>(r1)
            r0.menu = r3
            int[] r3 = com.google.android.material.R.styleable.NavigationView
            int r5 = DEF_STYLE_RES
            int[] r6 = new int[r8]
            androidx.appcompat.widget.TintTypedArray r3 = com.google.android.material.internal.ThemeEnforcement.obtainTintedStyledAttributes(r1, r2, r3, r4, r5, r6)
            int r5 = com.google.android.material.R.styleable.NavigationView_android_background
            boolean r5 = r3.hasValue(r5)
            if (r5 == 0) goto L_0x006a
            int r5 = com.google.android.material.R.styleable.NavigationView_android_background
            android.graphics.drawable.Drawable r5 = r3.getDrawable(r5)
            r0.setBackground(r5)
        L_0x006a:
            int r5 = com.google.android.material.R.styleable.NavigationView_drawerLayoutCornerSize
            int r5 = r3.getDimensionPixelSize(r5, r8)
            r0.drawerLayoutCornerSize = r5
            int r5 = r0.drawerLayoutCornerSize
            if (r5 != 0) goto L_0x0078
            r5 = r7
            goto L_0x0079
        L_0x0078:
            r5 = r8
        L_0x0079:
            r0.drawerLayoutCornerSizeBackAnimationEnabled = r5
            android.content.res.Resources r5 = r0.getResources()
            int r6 = com.google.android.material.R.dimen.m3_navigation_drawer_layout_corner_size
            int r5 = r5.getDimensionPixelSize(r6)
            r0.drawerLayoutCornerSizeBackAnimationMax = r5
            android.graphics.drawable.Drawable r5 = r0.getBackground()
            android.content.res.ColorStateList r6 = com.google.android.material.drawable.DrawableUtils.getColorStateListOrNull(r5)
            if (r5 == 0) goto L_0x0094
            if (r6 == 0) goto L_0x00ae
        L_0x0094:
            int r9 = DEF_STYLE_RES
            com.google.android.material.shape.ShapeAppearanceModel$Builder r9 = com.google.android.material.shape.ShapeAppearanceModel.builder((android.content.Context) r1, (android.util.AttributeSet) r2, (int) r4, (int) r9)
            com.google.android.material.shape.ShapeAppearanceModel r9 = r9.build()
            com.google.android.material.shape.MaterialShapeDrawable r10 = new com.google.android.material.shape.MaterialShapeDrawable
            r10.<init>((com.google.android.material.shape.ShapeAppearanceModel) r9)
            if (r6 == 0) goto L_0x00a8
            r10.setFillColor(r6)
        L_0x00a8:
            r10.initializeElevationOverlay(r1)
            r0.setBackground(r10)
        L_0x00ae:
            int r9 = com.google.android.material.R.styleable.NavigationView_elevation
            boolean r9 = r3.hasValue(r9)
            if (r9 == 0) goto L_0x00c0
            int r9 = com.google.android.material.R.styleable.NavigationView_elevation
            int r9 = r3.getDimensionPixelSize(r9, r8)
            float r9 = (float) r9
            r0.setElevation(r9)
        L_0x00c0:
            int r9 = com.google.android.material.R.styleable.NavigationView_android_fitsSystemWindows
            boolean r9 = r3.getBoolean(r9, r8)
            r0.setFitsSystemWindows(r9)
            int r9 = com.google.android.material.R.styleable.NavigationView_android_maxWidth
            int r9 = r3.getDimensionPixelSize(r9, r8)
            r0.maxWidth = r9
            r9 = 0
            int r10 = com.google.android.material.R.styleable.NavigationView_subheaderColor
            boolean r10 = r3.hasValue(r10)
            if (r10 == 0) goto L_0x00e0
            int r10 = com.google.android.material.R.styleable.NavigationView_subheaderColor
            android.content.res.ColorStateList r9 = r3.getColorStateList(r10)
        L_0x00e0:
            r10 = 0
            int r11 = com.google.android.material.R.styleable.NavigationView_subheaderTextAppearance
            boolean r11 = r3.hasValue(r11)
            if (r11 == 0) goto L_0x00ef
            int r11 = com.google.android.material.R.styleable.NavigationView_subheaderTextAppearance
            int r10 = r3.getResourceId(r11, r8)
        L_0x00ef:
            r11 = 16842808(0x1010038, float:2.3693715E-38)
            if (r10 != 0) goto L_0x00fa
            if (r9 != 0) goto L_0x00fa
            android.content.res.ColorStateList r9 = r0.createDefaultColorStateList(r11)
        L_0x00fa:
            int r12 = com.google.android.material.R.styleable.NavigationView_itemIconTint
            boolean r12 = r3.hasValue(r12)
            if (r12 == 0) goto L_0x0109
            int r11 = com.google.android.material.R.styleable.NavigationView_itemIconTint
            android.content.res.ColorStateList r11 = r3.getColorStateList(r11)
            goto L_0x010d
        L_0x0109:
            android.content.res.ColorStateList r11 = r0.createDefaultColorStateList(r11)
        L_0x010d:
            r12 = 0
            int r13 = com.google.android.material.R.styleable.NavigationView_itemTextAppearance
            boolean r13 = r3.hasValue(r13)
            if (r13 == 0) goto L_0x011c
            int r13 = com.google.android.material.R.styleable.NavigationView_itemTextAppearance
            int r12 = r3.getResourceId(r13, r8)
        L_0x011c:
            int r13 = com.google.android.material.R.styleable.NavigationView_itemTextAppearanceActiveBoldEnabled
            boolean r13 = r3.getBoolean(r13, r7)
            int r14 = com.google.android.material.R.styleable.NavigationView_itemIconSize
            boolean r14 = r3.hasValue(r14)
            if (r14 == 0) goto L_0x0133
            int r14 = com.google.android.material.R.styleable.NavigationView_itemIconSize
            int r14 = r3.getDimensionPixelSize(r14, r8)
            r0.setItemIconSize(r14)
        L_0x0133:
            r14 = 0
            int r15 = com.google.android.material.R.styleable.NavigationView_itemTextColor
            boolean r15 = r3.hasValue(r15)
            if (r15 == 0) goto L_0x0142
            int r15 = com.google.android.material.R.styleable.NavigationView_itemTextColor
            android.content.res.ColorStateList r14 = r3.getColorStateList(r15)
        L_0x0142:
            if (r12 != 0) goto L_0x014d
            if (r14 != 0) goto L_0x014d
            r15 = 16842806(0x1010036, float:2.369371E-38)
            android.content.res.ColorStateList r14 = r0.createDefaultColorStateList(r15)
        L_0x014d:
            int r15 = com.google.android.material.R.styleable.NavigationView_itemBackground
            android.graphics.drawable.Drawable r15 = r3.getDrawable(r15)
            if (r15 != 0) goto L_0x0181
            boolean r16 = r0.hasShapeAppearance(r3)
            if (r16 == 0) goto L_0x0181
            android.graphics.drawable.Drawable r15 = r0.createDefaultItemBackground(r3)
            int r7 = com.google.android.material.R.styleable.NavigationView_itemRippleColor
            android.content.res.ColorStateList r7 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r1, (androidx.appcompat.widget.TintTypedArray) r3, (int) r7)
            if (r7 == 0) goto L_0x017e
            r8 = 0
            android.graphics.drawable.Drawable r2 = r0.createDefaultItemDrawable(r3, r8)
            android.graphics.drawable.RippleDrawable r8 = new android.graphics.drawable.RippleDrawable
            android.content.res.ColorStateList r4 = com.google.android.material.ripple.RippleUtils.sanitizeRippleDrawableColor(r7)
            r17 = r5
            r5 = 0
            r8.<init>(r4, r5, r2)
            com.google.android.material.internal.NavigationMenuPresenter r4 = r0.presenter
            r4.setItemForeground(r8)
            goto L_0x0183
        L_0x017e:
            r17 = r5
            goto L_0x0183
        L_0x0181:
            r17 = r5
        L_0x0183:
            int r2 = com.google.android.material.R.styleable.NavigationView_itemHorizontalPadding
            boolean r2 = r3.hasValue(r2)
            if (r2 == 0) goto L_0x0195
            int r2 = com.google.android.material.R.styleable.NavigationView_itemHorizontalPadding
            r4 = 0
            int r2 = r3.getDimensionPixelSize(r2, r4)
            r0.setItemHorizontalPadding(r2)
        L_0x0195:
            int r2 = com.google.android.material.R.styleable.NavigationView_itemVerticalPadding
            boolean r2 = r3.hasValue(r2)
            if (r2 == 0) goto L_0x01a8
            int r2 = com.google.android.material.R.styleable.NavigationView_itemVerticalPadding
            r4 = 0
            int r2 = r3.getDimensionPixelSize(r2, r4)
            r0.setItemVerticalPadding(r2)
            goto L_0x01a9
        L_0x01a8:
            r4 = 0
        L_0x01a9:
            int r2 = com.google.android.material.R.styleable.NavigationView_dividerInsetStart
            int r2 = r3.getDimensionPixelSize(r2, r4)
            r0.setDividerInsetStart(r2)
            int r5 = com.google.android.material.R.styleable.NavigationView_dividerInsetEnd
            int r5 = r3.getDimensionPixelSize(r5, r4)
            r0.setDividerInsetEnd(r5)
            int r7 = com.google.android.material.R.styleable.NavigationView_subheaderInsetStart
            int r7 = r3.getDimensionPixelSize(r7, r4)
            r0.setSubheaderInsetStart(r7)
            int r8 = com.google.android.material.R.styleable.NavigationView_subheaderInsetEnd
            int r8 = r3.getDimensionPixelSize(r8, r4)
            r0.setSubheaderInsetEnd(r8)
            int r4 = com.google.android.material.R.styleable.NavigationView_topInsetScrimEnabled
            r20 = r2
            boolean r2 = r0.topInsetScrimEnabled
            boolean r2 = r3.getBoolean(r4, r2)
            r0.setTopInsetScrimEnabled(r2)
            int r2 = com.google.android.material.R.styleable.NavigationView_bottomInsetScrimEnabled
            boolean r4 = r0.bottomInsetScrimEnabled
            boolean r2 = r3.getBoolean(r2, r4)
            r0.setBottomInsetScrimEnabled(r2)
            int r2 = com.google.android.material.R.styleable.NavigationView_startInsetScrimEnabled
            boolean r4 = r0.startInsetScrimEnabled
            boolean r2 = r3.getBoolean(r2, r4)
            r0.setStartInsetScrimEnabled(r2)
            int r2 = com.google.android.material.R.styleable.NavigationView_endInsetScrimEnabled
            boolean r4 = r0.endInsetScrimEnabled
            boolean r2 = r3.getBoolean(r2, r4)
            r0.setEndInsetScrimEnabled(r2)
            int r2 = com.google.android.material.R.styleable.NavigationView_itemIconPadding
            r4 = 0
            int r2 = r3.getDimensionPixelSize(r2, r4)
            int r4 = com.google.android.material.R.styleable.NavigationView_itemMaxLines
            r18 = r5
            r5 = 1
            int r4 = r3.getInt(r4, r5)
            r0.setItemMaxLines(r4)
            com.google.android.material.internal.NavigationMenu r4 = r0.menu
            com.google.android.material.navigation.NavigationView$2 r5 = new com.google.android.material.navigation.NavigationView$2
            r5.<init>()
            r4.setCallback(r5)
            com.google.android.material.internal.NavigationMenuPresenter r4 = r0.presenter
            r5 = 1
            r4.setId(r5)
            com.google.android.material.internal.NavigationMenuPresenter r4 = r0.presenter
            com.google.android.material.internal.NavigationMenu r5 = r0.menu
            r4.initForMenu(r1, r5)
            if (r10 == 0) goto L_0x022c
            com.google.android.material.internal.NavigationMenuPresenter r4 = r0.presenter
            r4.setSubheaderTextAppearance(r10)
        L_0x022c:
            com.google.android.material.internal.NavigationMenuPresenter r4 = r0.presenter
            r4.setSubheaderColor(r9)
            com.google.android.material.internal.NavigationMenuPresenter r4 = r0.presenter
            r4.setItemIconTintList(r11)
            com.google.android.material.internal.NavigationMenuPresenter r4 = r0.presenter
            int r5 = r0.getOverScrollMode()
            r4.setOverScrollMode(r5)
            if (r12 == 0) goto L_0x0246
            com.google.android.material.internal.NavigationMenuPresenter r4 = r0.presenter
            r4.setItemTextAppearance(r12)
        L_0x0246:
            com.google.android.material.internal.NavigationMenuPresenter r4 = r0.presenter
            r4.setItemTextAppearanceActiveBoldEnabled(r13)
            com.google.android.material.internal.NavigationMenuPresenter r4 = r0.presenter
            r4.setItemTextColor(r14)
            com.google.android.material.internal.NavigationMenuPresenter r4 = r0.presenter
            r4.setItemBackground(r15)
            com.google.android.material.internal.NavigationMenuPresenter r4 = r0.presenter
            r4.setItemIconPadding(r2)
            com.google.android.material.internal.NavigationMenu r4 = r0.menu
            com.google.android.material.internal.NavigationMenuPresenter r5 = r0.presenter
            r4.addMenuPresenter(r5)
            com.google.android.material.internal.NavigationMenuPresenter r4 = r0.presenter
            androidx.appcompat.view.menu.MenuView r4 = r4.getMenuView(r0)
            android.view.View r4 = (android.view.View) r4
            r0.addView(r4)
            int r4 = com.google.android.material.R.styleable.NavigationView_menu
            boolean r4 = r3.hasValue(r4)
            if (r4 == 0) goto L_0x027f
            int r4 = com.google.android.material.R.styleable.NavigationView_menu
            r5 = 0
            int r4 = r3.getResourceId(r4, r5)
            r0.inflateMenu(r4)
            goto L_0x0280
        L_0x027f:
            r5 = 0
        L_0x0280:
            int r4 = com.google.android.material.R.styleable.NavigationView_headerLayout
            boolean r4 = r3.hasValue(r4)
            if (r4 == 0) goto L_0x0291
            int r4 = com.google.android.material.R.styleable.NavigationView_headerLayout
            int r4 = r3.getResourceId(r4, r5)
            r0.inflateHeaderView(r4)
        L_0x0291:
            r3.recycle()
            r0.setupInsetScrimsListener()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.navigation.NavigationView.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    public void setOverScrollMode(int overScrollMode) {
        super.setOverScrollMode(overScrollMode);
        if (this.presenter != null) {
            this.presenter.setOverScrollMode(overScrollMode);
        }
    }

    public void setForceCompatClippingEnabled(boolean enabled) {
        this.shapeableDelegate.setForceCompatClippingEnabled(this, enabled);
    }

    private void maybeUpdateCornerSizeForDrawerLayout(int width, int height) {
        if ((getParent() instanceof DrawerLayout) && (getLayoutParams() instanceof DrawerLayout.LayoutParams)) {
            if ((this.drawerLayoutCornerSize > 0 || this.drawerLayoutCornerSizeBackAnimationEnabled) && (getBackground() instanceof MaterialShapeDrawable)) {
                boolean isAbsGravityLeft = Gravity.getAbsoluteGravity(((DrawerLayout.LayoutParams) getLayoutParams()).gravity, getLayoutDirection()) == 3;
                MaterialShapeDrawable background = (MaterialShapeDrawable) getBackground();
                ShapeAppearanceModel.Builder builder = background.getShapeAppearanceModel().toBuilder().setAllCornerSizes((float) this.drawerLayoutCornerSize);
                if (isAbsGravityLeft) {
                    builder.setTopLeftCornerSize(0.0f);
                    builder.setBottomLeftCornerSize(0.0f);
                } else {
                    builder.setTopRightCornerSize(0.0f);
                    builder.setBottomRightCornerSize(0.0f);
                }
                ShapeAppearanceModel model = builder.build();
                background.setShapeAppearanceModel(model);
                this.shapeableDelegate.onShapeAppearanceChanged(this, model);
                this.shapeableDelegate.onMaskChanged(this, new RectF(0.0f, 0.0f, (float) width, (float) height));
                this.shapeableDelegate.setOffsetZeroCornerEdgeBoundsEnabled(this, true);
            }
        }
    }

    /* access modifiers changed from: private */
    public void maybeClearCornerSizeAnimationForDrawerLayout() {
        if (this.drawerLayoutCornerSizeBackAnimationEnabled && this.drawerLayoutCornerSize != 0) {
            this.drawerLayoutCornerSize = 0;
            maybeUpdateCornerSizeForDrawerLayout(getWidth(), getHeight());
        }
    }

    private boolean hasShapeAppearance(TintTypedArray a) {
        return a.hasValue(R.styleable.NavigationView_itemShapeAppearance) || a.hasValue(R.styleable.NavigationView_itemShapeAppearanceOverlay);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
        ViewParent parent = getParent();
        if ((parent instanceof DrawerLayout) && this.backOrchestrator.shouldListenForBackCallbacks()) {
            DrawerLayout drawerLayout = (DrawerLayout) parent;
            drawerLayout.removeDrawerListener(this.backDrawerListener);
            drawerLayout.addDrawerListener(this.backDrawerListener);
            if (drawerLayout.isDrawerOpen((View) this)) {
                this.backOrchestrator.startListeningForBackCallbacksWithPriorityOverlay();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this.onGlobalLayoutListener);
        ViewParent parent = getParent();
        if (parent instanceof DrawerLayout) {
            ((DrawerLayout) parent).removeDrawerListener(this.backDrawerListener);
        }
        this.backOrchestrator.stopListeningForBackCallbacks();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        maybeUpdateCornerSizeForDrawerLayout(w, h);
    }

    public void setElevation(float elevation) {
        super.setElevation(elevation);
        MaterialShapeUtils.setElevation(this, elevation);
    }

    private Drawable createDefaultItemBackground(TintTypedArray a) {
        return createDefaultItemDrawable(a, MaterialResources.getColorStateList(getContext(), a, R.styleable.NavigationView_itemShapeFillColor));
    }

    private Drawable createDefaultItemDrawable(TintTypedArray a, ColorStateList fillColor) {
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(ShapeAppearanceModel.builder(getContext(), a.getResourceId(R.styleable.NavigationView_itemShapeAppearance, 0), a.getResourceId(R.styleable.NavigationView_itemShapeAppearanceOverlay, 0)).build());
        materialShapeDrawable.setFillColor(fillColor);
        return new InsetDrawable(materialShapeDrawable, a.getDimensionPixelSize(R.styleable.NavigationView_itemShapeInsetStart, 0), a.getDimensionPixelSize(R.styleable.NavigationView_itemShapeInsetTop, 0), a.getDimensionPixelSize(R.styleable.NavigationView_itemShapeInsetEnd, 0), a.getDimensionPixelSize(R.styleable.NavigationView_itemShapeInsetBottom, 0));
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState state = new SavedState(super.onSaveInstanceState());
        state.menuState = new Bundle();
        this.menu.savePresenterStates(state.menuState);
        return state;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable savedState) {
        if (!(savedState instanceof SavedState)) {
            super.onRestoreInstanceState(savedState);
            return;
        }
        SavedState state = (SavedState) savedState;
        super.onRestoreInstanceState(state.getSuperState());
        this.menu.restorePresenterStates(state.menuState);
    }

    public void setNavigationItemSelectedListener(OnNavigationItemSelectedListener listener2) {
        this.listener = listener2;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthSpec, int heightSpec) {
        switch (View.MeasureSpec.getMode(widthSpec)) {
            case Integer.MIN_VALUE:
                widthSpec = View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(widthSpec), this.maxWidth), 1073741824);
                break;
            case 0:
                widthSpec = View.MeasureSpec.makeMeasureSpec(this.maxWidth, 1073741824);
                break;
        }
        super.onMeasure(widthSpec, heightSpec);
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        this.shapeableDelegate.maybeClip(canvas, new NavigationView$$ExternalSyntheticLambda0(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$dispatchDraw$0$com-google-android-material-navigation-NavigationView  reason: not valid java name */
    public /* synthetic */ void m1695lambda$dispatchDraw$0$comgoogleandroidmaterialnavigationNavigationView(Canvas x$0) {
        super.dispatchDraw(x$0);
    }

    /* access modifiers changed from: protected */
    public void onInsetsChanged(WindowInsetsCompat insets) {
        this.presenter.dispatchApplyWindowInsets(insets);
    }

    public void inflateMenu(int resId) {
        this.presenter.setUpdateSuspended(true);
        getMenuInflater().inflate(resId, this.menu);
        this.presenter.setUpdateSuspended(false);
        this.presenter.updateMenuView(false);
    }

    public Menu getMenu() {
        return this.menu;
    }

    public View inflateHeaderView(int res) {
        return this.presenter.inflateHeaderView(res);
    }

    public void addHeaderView(View view) {
        this.presenter.addHeaderView(view);
    }

    public void removeHeaderView(View view) {
        this.presenter.removeHeaderView(view);
    }

    public int getHeaderCount() {
        return this.presenter.getHeaderCount();
    }

    public View getHeaderView(int index) {
        return this.presenter.getHeaderView(index);
    }

    public ColorStateList getItemIconTintList() {
        return this.presenter.getItemTintList();
    }

    public void setItemIconTintList(ColorStateList tint) {
        this.presenter.setItemIconTintList(tint);
    }

    public ColorStateList getItemTextColor() {
        return this.presenter.getItemTextColor();
    }

    public void setItemTextColor(ColorStateList textColor) {
        this.presenter.setItemTextColor(textColor);
    }

    public Drawable getItemBackground() {
        return this.presenter.getItemBackground();
    }

    public void setItemBackgroundResource(int resId) {
        setItemBackground(getContext().getDrawable(resId));
    }

    public void setItemBackground(Drawable itemBackground) {
        this.presenter.setItemBackground(itemBackground);
    }

    public int getItemHorizontalPadding() {
        return this.presenter.getItemHorizontalPadding();
    }

    public void setItemHorizontalPadding(int padding) {
        this.presenter.setItemHorizontalPadding(padding);
    }

    public void setItemHorizontalPaddingResource(int paddingResource) {
        this.presenter.setItemHorizontalPadding(getResources().getDimensionPixelSize(paddingResource));
    }

    public int getItemVerticalPadding() {
        return this.presenter.getItemVerticalPadding();
    }

    public void setItemVerticalPadding(int padding) {
        this.presenter.setItemVerticalPadding(padding);
    }

    public void setItemVerticalPaddingResource(int paddingResource) {
        this.presenter.setItemVerticalPadding(getResources().getDimensionPixelSize(paddingResource));
    }

    public int getItemIconPadding() {
        return this.presenter.getItemIconPadding();
    }

    public void setItemIconPadding(int padding) {
        this.presenter.setItemIconPadding(padding);
    }

    public void setItemIconPaddingResource(int paddingResource) {
        this.presenter.setItemIconPadding(getResources().getDimensionPixelSize(paddingResource));
    }

    public void setCheckedItem(int id) {
        MenuItem item = this.menu.findItem(id);
        if (item != null) {
            this.presenter.setCheckedItem((MenuItemImpl) item);
        }
    }

    public void setCheckedItem(MenuItem checkedItem) {
        MenuItem item = this.menu.findItem(checkedItem.getItemId());
        if (item != null) {
            this.presenter.setCheckedItem((MenuItemImpl) item);
            return;
        }
        throw new IllegalArgumentException("Called setCheckedItem(MenuItem) with an item that is not in the current menu.");
    }

    public MenuItem getCheckedItem() {
        return this.presenter.getCheckedItem();
    }

    public void setItemTextAppearance(int resId) {
        this.presenter.setItemTextAppearance(resId);
    }

    public void setItemTextAppearanceActiveBoldEnabled(boolean isBold) {
        this.presenter.setItemTextAppearanceActiveBoldEnabled(isBold);
    }

    public void setItemIconSize(int iconSize) {
        this.presenter.setItemIconSize(iconSize);
    }

    public void setItemMaxLines(int itemMaxLines) {
        this.presenter.setItemMaxLines(itemMaxLines);
    }

    public int getItemMaxLines() {
        return this.presenter.getItemMaxLines();
    }

    public boolean isTopInsetScrimEnabled() {
        return this.topInsetScrimEnabled;
    }

    public void setTopInsetScrimEnabled(boolean enabled) {
        this.topInsetScrimEnabled = enabled;
    }

    public boolean isBottomInsetScrimEnabled() {
        return this.bottomInsetScrimEnabled;
    }

    public void setBottomInsetScrimEnabled(boolean enabled) {
        this.bottomInsetScrimEnabled = enabled;
    }

    public boolean isStartInsetScrimEnabled() {
        return this.startInsetScrimEnabled;
    }

    public void setStartInsetScrimEnabled(boolean enabled) {
        this.startInsetScrimEnabled = enabled;
    }

    public boolean isEndInsetScrimEnabled() {
        return this.endInsetScrimEnabled;
    }

    public void setEndInsetScrimEnabled(boolean enabled) {
        this.endInsetScrimEnabled = enabled;
    }

    public int getDividerInsetStart() {
        return this.presenter.getDividerInsetStart();
    }

    public void setDividerInsetStart(int dividerInsetStart) {
        this.presenter.setDividerInsetStart(dividerInsetStart);
    }

    public int getDividerInsetEnd() {
        return this.presenter.getDividerInsetEnd();
    }

    public void setDividerInsetEnd(int dividerInsetEnd) {
        this.presenter.setDividerInsetEnd(dividerInsetEnd);
    }

    public int getSubheaderInsetStart() {
        return this.presenter.getSubheaderInsetStart();
    }

    public void setSubheaderInsetStart(int subheaderInsetStart) {
        this.presenter.setSubheaderInsetStart(subheaderInsetStart);
    }

    public int getSubheaderInsetEnd() {
        return this.presenter.getSubheaderInsetEnd();
    }

    public void setSubheaderInsetEnd(int subheaderInsetEnd) {
        this.presenter.setSubheaderInsetEnd(subheaderInsetEnd);
    }

    public void startBackProgress(BackEventCompat backEvent) {
        requireDrawerLayoutParent();
        this.sideContainerBackHelper.startBackProgress(backEvent);
    }

    public void updateBackProgress(BackEventCompat backEvent) {
        this.sideContainerBackHelper.updateBackProgress(backEvent, ((DrawerLayout.LayoutParams) requireDrawerLayoutParent().second).gravity);
        if (this.drawerLayoutCornerSizeBackAnimationEnabled) {
            this.drawerLayoutCornerSize = AnimationUtils.lerp(0, this.drawerLayoutCornerSizeBackAnimationMax, this.sideContainerBackHelper.interpolateProgress(backEvent.getProgress()));
            maybeUpdateCornerSizeForDrawerLayout(getWidth(), getHeight());
        }
    }

    public void handleBackInvoked() {
        Pair<DrawerLayout, DrawerLayout.LayoutParams> drawerLayoutPair = requireDrawerLayoutParent();
        DrawerLayout drawerLayout = (DrawerLayout) drawerLayoutPair.first;
        BackEventCompat backEvent = this.sideContainerBackHelper.onHandleBackInvoked();
        if (backEvent == null || Build.VERSION.SDK_INT < 34) {
            drawerLayout.closeDrawer((View) this);
            return;
        }
        this.sideContainerBackHelper.finishBackProgress(backEvent, ((DrawerLayout.LayoutParams) drawerLayoutPair.second).gravity, DrawerLayoutUtils.getScrimCloseAnimatorListener(drawerLayout, this), DrawerLayoutUtils.getScrimCloseAnimatorUpdateListener(drawerLayout));
    }

    public void cancelBackProgress() {
        requireDrawerLayoutParent();
        this.sideContainerBackHelper.cancelBackProgress();
        maybeClearCornerSizeAnimationForDrawerLayout();
    }

    private Pair<DrawerLayout, DrawerLayout.LayoutParams> requireDrawerLayoutParent() {
        ViewParent parent = getParent();
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if ((parent instanceof DrawerLayout) && (layoutParams instanceof DrawerLayout.LayoutParams)) {
            return new Pair<>((DrawerLayout) parent, (DrawerLayout.LayoutParams) layoutParams);
        }
        throw new IllegalStateException("NavigationView back progress requires the direct parent view to be a DrawerLayout.");
    }

    /* access modifiers changed from: package-private */
    public MaterialSideContainerBackHelper getBackHelper() {
        return this.sideContainerBackHelper;
    }

    private MenuInflater getMenuInflater() {
        if (this.menuInflater == null) {
            this.menuInflater = new SupportMenuInflater(getContext());
        }
        return this.menuInflater;
    }

    private ColorStateList createDefaultColorStateList(int baseColorThemeAttr) {
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

    private void setupInsetScrimsListener() {
        this.onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                NavigationView.this.getLocationOnScreen(NavigationView.this.tmpLocation);
                boolean z = true;
                boolean isBehindStatusBar = NavigationView.this.tmpLocation[1] == 0;
                NavigationView.this.presenter.setBehindStatusBar(isBehindStatusBar);
                NavigationView.this.setDrawTopInsetForeground(isBehindStatusBar && NavigationView.this.isTopInsetScrimEnabled());
                boolean isRtl = NavigationView.this.getLayoutDirection() == 1;
                NavigationView.this.setDrawLeftInsetForeground((NavigationView.this.tmpLocation[0] == 0 || NavigationView.this.tmpLocation[0] + NavigationView.this.getWidth() == 0) && (!isRtl ? NavigationView.this.isStartInsetScrimEnabled() : NavigationView.this.isEndInsetScrimEnabled()));
                Activity activity = ContextUtils.getActivity(NavigationView.this.getContext());
                if (activity != null) {
                    Rect displayBounds = WindowUtils.getCurrentWindowBounds(activity);
                    NavigationView.this.setDrawBottomInsetForeground((displayBounds.height() - NavigationView.this.getHeight() == NavigationView.this.tmpLocation[1]) && (Color.alpha(activity.getWindow().getNavigationBarColor()) != 0) && NavigationView.this.isBottomInsetScrimEnabled());
                    boolean isOnRightSide = displayBounds.width() == NavigationView.this.tmpLocation[0] || displayBounds.width() - NavigationView.this.getWidth() == NavigationView.this.tmpLocation[0];
                    NavigationView navigationView = NavigationView.this;
                    if (!isOnRightSide || (!isRtl ? !NavigationView.this.isEndInsetScrimEnabled() : !NavigationView.this.isStartInsetScrimEnabled())) {
                        z = false;
                    }
                    navigationView.setDrawRightInsetForeground(z);
                }
            }
        };
        getViewTreeObserver().addOnGlobalLayoutListener(this.onGlobalLayoutListener);
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
        public Bundle menuState;

        public SavedState(Parcel in, ClassLoader loader) {
            super(in, loader);
            this.menuState = in.readBundle(loader);
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeBundle(this.menuState);
        }
    }
}
