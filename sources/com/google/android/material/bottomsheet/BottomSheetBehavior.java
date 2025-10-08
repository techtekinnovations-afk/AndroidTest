package com.google.android.material.bottomsheet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.RoundedCorner;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowInsets;
import androidx.activity.BackEventCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import com.google.android.material.R;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.motion.MaterialBackHandler;
import com.google.android.material.motion.MaterialBottomContainerBackHelper;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.logging.type.LogSeverity;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> implements MaterialBackHandler {
    private static final int CORNER_ANIMATION_DURATION = 500;
    static final int DEFAULT_SIGNIFICANT_VEL_THRESHOLD = 500;
    private static final int DEF_STYLE_RES = R.style.Widget_Design_BottomSheet_Modal;
    private static final float HIDE_FRICTION = 0.1f;
    private static final float HIDE_THRESHOLD = 0.5f;
    private static final int INVALID_POSITION = -1;
    private static final int NO_MAX_SIZE = -1;
    public static final int PEEK_HEIGHT_AUTO = -1;
    public static final int SAVE_ALL = -1;
    public static final int SAVE_FIT_TO_CONTENTS = 2;
    public static final int SAVE_HIDEABLE = 4;
    public static final int SAVE_NONE = 0;
    public static final int SAVE_PEEK_HEIGHT = 1;
    public static final int SAVE_SKIP_COLLAPSED = 8;
    public static final int STATE_COLLAPSED = 4;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_EXPANDED = 3;
    public static final int STATE_HALF_EXPANDED = 6;
    public static final int STATE_HIDDEN = 5;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "BottomSheetBehavior";
    static final int VIEW_INDEX_ACCESSIBILITY_DELEGATE_VIEW = 1;
    private static final int VIEW_INDEX_BOTTOM_SHEET = 0;
    WeakReference<View> accessibilityDelegateViewRef;
    int activePointerId;
    private ColorStateList backgroundTint;
    MaterialBottomContainerBackHelper bottomContainerBackHelper;
    private final ArrayList<BottomSheetCallback> callbacks = new ArrayList<>();
    private int childHeight;
    int collapsedOffset;
    private final ViewDragHelper.Callback dragCallback = new ViewDragHelper.Callback() {
        private long viewCapturedMillis;

        public boolean tryCaptureView(View child, int pointerId) {
            if (BottomSheetBehavior.this.state == 1 || BottomSheetBehavior.this.touchingScrollingChild) {
                return false;
            }
            if (BottomSheetBehavior.this.state == 3 && BottomSheetBehavior.this.activePointerId == pointerId) {
                View scroll = BottomSheetBehavior.this.nestedScrollingChildRef != null ? (View) BottomSheetBehavior.this.nestedScrollingChildRef.get() : null;
                if (scroll != null && scroll.canScrollVertically(-1)) {
                    return false;
                }
            }
            this.viewCapturedMillis = SystemClock.uptimeMillis();
            if (BottomSheetBehavior.this.viewRef == null || BottomSheetBehavior.this.viewRef.get() != child) {
                return false;
            }
            return true;
        }

        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            BottomSheetBehavior.this.dispatchOnSlide(top);
        }

        public void onViewDragStateChanged(int state) {
            if (state == 1 && BottomSheetBehavior.this.draggable) {
                BottomSheetBehavior.this.setStateInternal(1);
            }
        }

        private boolean releasedLow(View child) {
            return child.getTop() > (BottomSheetBehavior.this.parentHeight + BottomSheetBehavior.this.getExpandedOffset()) / 2;
        }

        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int targetState;
            int targetState2;
            if (yvel < 0.0f) {
                if (BottomSheetBehavior.this.fitToContents) {
                    targetState = 3;
                } else {
                    int targetState3 = releasedChild.getTop();
                    long dragDurationMillis = SystemClock.uptimeMillis() - this.viewCapturedMillis;
                    if (BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                        if (BottomSheetBehavior.this.shouldExpandOnUpwardDrag(dragDurationMillis, (((float) targetState3) * 100.0f) / ((float) BottomSheetBehavior.this.parentHeight))) {
                            targetState2 = 3;
                        } else {
                            targetState2 = 4;
                        }
                        targetState = targetState2;
                    } else if (targetState3 > BottomSheetBehavior.this.halfExpandedOffset) {
                        targetState = 6;
                    } else {
                        targetState = 3;
                    }
                }
            } else if (!BottomSheetBehavior.this.hideable || !BottomSheetBehavior.this.shouldHide(releasedChild, yvel)) {
                if (yvel == 0.0f || Math.abs(xvel) > Math.abs(yvel)) {
                    int currentTop = releasedChild.getTop();
                    if (BottomSheetBehavior.this.fitToContents) {
                        if (Math.abs(currentTop - BottomSheetBehavior.this.fitToContentsOffset) < Math.abs(currentTop - BottomSheetBehavior.this.collapsedOffset)) {
                            targetState = 3;
                        } else {
                            targetState = 4;
                        }
                    } else if (currentTop < BottomSheetBehavior.this.halfExpandedOffset) {
                        if (currentTop < Math.abs(currentTop - BottomSheetBehavior.this.collapsedOffset)) {
                            targetState = 3;
                        } else if (BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                            targetState = 4;
                        } else {
                            targetState = 6;
                        }
                    } else if (Math.abs(currentTop - BottomSheetBehavior.this.halfExpandedOffset) >= Math.abs(currentTop - BottomSheetBehavior.this.collapsedOffset)) {
                        targetState = 4;
                    } else if (BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                        targetState = 4;
                    } else {
                        targetState = 6;
                    }
                } else if (BottomSheetBehavior.this.fitToContents) {
                    targetState = 4;
                } else {
                    int targetState4 = releasedChild.getTop();
                    if (Math.abs(targetState4 - BottomSheetBehavior.this.halfExpandedOffset) >= Math.abs(targetState4 - BottomSheetBehavior.this.collapsedOffset)) {
                        targetState = 4;
                    } else if (BottomSheetBehavior.this.shouldSkipHalfExpandedStateWhenDragging()) {
                        targetState = 4;
                    } else {
                        targetState = 6;
                    }
                }
            } else if ((Math.abs(xvel) < Math.abs(yvel) && yvel > ((float) BottomSheetBehavior.this.significantVelocityThreshold)) || releasedLow(releasedChild)) {
                targetState = 5;
            } else if (BottomSheetBehavior.this.fitToContents) {
                targetState = 3;
            } else if (Math.abs(releasedChild.getTop() - BottomSheetBehavior.this.getExpandedOffset()) < Math.abs(releasedChild.getTop() - BottomSheetBehavior.this.halfExpandedOffset)) {
                targetState = 3;
            } else {
                targetState = 6;
            }
            BottomSheetBehavior.this.startSettling(releasedChild, targetState, BottomSheetBehavior.this.shouldSkipSmoothAnimation());
        }

        public int clampViewPositionVertical(View child, int top, int dy) {
            return MathUtils.clamp(top, BottomSheetBehavior.this.getExpandedOffset(), getViewVerticalDragRange(child));
        }

        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return child.getLeft();
        }

        public int getViewVerticalDragRange(View child) {
            if (BottomSheetBehavior.this.canBeHiddenByDragging()) {
                return BottomSheetBehavior.this.parentHeight;
            }
            return BottomSheetBehavior.this.collapsedOffset;
        }
    };
    WeakReference<View> dragHandleViewRef;
    /* access modifiers changed from: private */
    public boolean draggable = true;
    private boolean draggableOnNestedScroll = true;
    private boolean draggableOnNestedScrollLastDragIgnored;
    float elevation = -1.0f;
    final SparseIntArray expandHalfwayActionIds = new SparseIntArray();
    private boolean expandedCornersRemoved;
    int expandedOffset;
    /* access modifiers changed from: private */
    public boolean fitToContents = true;
    int fitToContentsOffset;
    /* access modifiers changed from: private */
    public int gestureInsetBottom;
    private boolean gestureInsetBottomIgnored;
    int halfExpandedOffset;
    float halfExpandedRatio = 0.5f;
    private float hideFriction = 0.1f;
    boolean hideable;
    private boolean ignoreEvents;
    private Map<View, Integer> importantForAccessibilityMap;
    private int initialY = -1;
    /* access modifiers changed from: private */
    public int insetBottom;
    /* access modifiers changed from: private */
    public int insetTop;
    private ValueAnimator interpolatorAnimator;
    private int lastNestedScrollDy;
    int lastStableState = 4;
    /* access modifiers changed from: private */
    public boolean marginLeftSystemWindowInsets;
    /* access modifiers changed from: private */
    public boolean marginRightSystemWindowInsets;
    /* access modifiers changed from: private */
    public boolean marginTopSystemWindowInsets;
    /* access modifiers changed from: private */
    public MaterialShapeDrawable materialShapeDrawable;
    private int maxHeight = -1;
    private int maxWidth = -1;
    private float maximumVelocity;
    private boolean nestedScrolled;
    WeakReference<View> nestedScrollingChildRef;
    /* access modifiers changed from: private */
    public boolean paddingBottomSystemWindowInsets;
    /* access modifiers changed from: private */
    public boolean paddingLeftSystemWindowInsets;
    /* access modifiers changed from: private */
    public boolean paddingRightSystemWindowInsets;
    private boolean paddingTopSystemWindowInsets;
    int parentHeight;
    int parentWidth;
    /* access modifiers changed from: private */
    public int peekHeight;
    private boolean peekHeightAuto;
    private int peekHeightGestureInsetBuffer;
    private int peekHeightMin;
    private int saveFlags = 0;
    private ShapeAppearanceModel shapeAppearanceModelDefault;
    private boolean shouldRemoveExpandedCorners;
    /* access modifiers changed from: private */
    public int significantVelocityThreshold;
    /* access modifiers changed from: private */
    public boolean skipCollapsed;
    int state = 4;
    private final BottomSheetBehavior<V>.StateSettlingTracker stateSettlingTracker = new StateSettlingTracker();
    boolean touchingScrollingChild;
    private boolean updateImportantForAccessibilityOnSiblings = false;
    private VelocityTracker velocityTracker;
    ViewDragHelper viewDragHelper;
    WeakReference<V> viewRef;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SaveFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StableState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public static abstract class BottomSheetCallback {
        public abstract void onSlide(View view, float f);

        public abstract void onStateChanged(View view, int i);

        /* access modifiers changed from: package-private */
        public void onLayout(View bottomSheet) {
        }
    }

    public BottomSheetBehavior() {
    }

    public BottomSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.peekHeightGestureInsetBuffer = context.getResources().getDimensionPixelSize(R.dimen.mtrl_min_touch_target_size);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BottomSheetBehavior_Layout);
        if (a.hasValue(R.styleable.BottomSheetBehavior_Layout_backgroundTint)) {
            this.backgroundTint = MaterialResources.getColorStateList(context, a, R.styleable.BottomSheetBehavior_Layout_backgroundTint);
        }
        if (a.hasValue(R.styleable.BottomSheetBehavior_Layout_shapeAppearance)) {
            this.shapeAppearanceModelDefault = ShapeAppearanceModel.builder(context, attrs, R.attr.bottomSheetStyle, DEF_STYLE_RES).build();
        }
        createMaterialShapeDrawableIfNeeded(context);
        createShapeValueAnimator();
        this.elevation = a.getDimension(R.styleable.BottomSheetBehavior_Layout_android_elevation, -1.0f);
        if (a.hasValue(R.styleable.BottomSheetBehavior_Layout_android_maxWidth)) {
            setMaxWidth(a.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_android_maxWidth, -1));
        }
        if (a.hasValue(R.styleable.BottomSheetBehavior_Layout_android_maxHeight)) {
            setMaxHeight(a.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_android_maxHeight, -1));
        }
        TypedValue value = a.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight);
        if (value == null || value.data != -1) {
            setPeekHeight(a.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight, -1));
        } else {
            setPeekHeight(value.data);
        }
        setHideable(a.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
        setGestureInsetBottomIgnored(a.getBoolean(R.styleable.BottomSheetBehavior_Layout_gestureInsetBottomIgnored, false));
        setFitToContents(a.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_fitToContents, true));
        setSkipCollapsed(a.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
        setDraggable(a.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_draggable, true));
        setDraggableOnNestedScroll(a.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_draggableOnNestedScroll, true));
        setSaveFlags(a.getInt(R.styleable.BottomSheetBehavior_Layout_behavior_saveFlags, 0));
        setHalfExpandedRatio(a.getFloat(R.styleable.BottomSheetBehavior_Layout_behavior_halfExpandedRatio, 0.5f));
        TypedValue value2 = a.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_expandedOffset);
        if (value2 == null || value2.type != 16) {
            setExpandedOffset(a.getDimensionPixelOffset(R.styleable.BottomSheetBehavior_Layout_behavior_expandedOffset, 0));
        } else {
            setExpandedOffset(value2.data);
        }
        setSignificantVelocityThreshold(a.getInt(R.styleable.BottomSheetBehavior_Layout_behavior_significantVelocityThreshold, LogSeverity.ERROR_VALUE));
        this.paddingBottomSystemWindowInsets = a.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingBottomSystemWindowInsets, false);
        this.paddingLeftSystemWindowInsets = a.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingLeftSystemWindowInsets, false);
        this.paddingRightSystemWindowInsets = a.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingRightSystemWindowInsets, false);
        this.paddingTopSystemWindowInsets = a.getBoolean(R.styleable.BottomSheetBehavior_Layout_paddingTopSystemWindowInsets, true);
        this.marginLeftSystemWindowInsets = a.getBoolean(R.styleable.BottomSheetBehavior_Layout_marginLeftSystemWindowInsets, false);
        this.marginRightSystemWindowInsets = a.getBoolean(R.styleable.BottomSheetBehavior_Layout_marginRightSystemWindowInsets, false);
        this.marginTopSystemWindowInsets = a.getBoolean(R.styleable.BottomSheetBehavior_Layout_marginTopSystemWindowInsets, false);
        this.shouldRemoveExpandedCorners = a.getBoolean(R.styleable.BottomSheetBehavior_Layout_shouldRemoveExpandedCorners, true);
        a.recycle();
        this.maximumVelocity = (float) ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }

    public Parcelable onSaveInstanceState(CoordinatorLayout parent, V child) {
        return new SavedState(super.onSaveInstanceState(parent, child), (BottomSheetBehavior<?>) this);
    }

    public void onRestoreInstanceState(CoordinatorLayout parent, V child, Parcelable state2) {
        SavedState ss = (SavedState) state2;
        super.onRestoreInstanceState(parent, child, ss.getSuperState());
        restoreOptionalState(ss);
        if (ss.state == 1 || ss.state == 2) {
            this.state = 4;
            this.lastStableState = this.state;
            return;
        }
        this.state = ss.state;
        this.lastStableState = this.state;
    }

    public void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
        super.onAttachedToLayoutParams(layoutParams);
        this.viewRef = null;
        this.viewDragHelper = null;
        this.bottomContainerBackHelper = null;
    }

    public void onDetachedFromLayoutParams() {
        super.onDetachedFromLayoutParams();
        this.viewRef = null;
        this.viewDragHelper = null;
        this.bottomContainerBackHelper = null;
    }

    public boolean onMeasureChild(CoordinatorLayout parent, V child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
        child.measure(getChildMeasureSpec(parentWidthMeasureSpec, parent.getPaddingLeft() + parent.getPaddingRight() + lp.leftMargin + lp.rightMargin + widthUsed, this.maxWidth, lp.width), getChildMeasureSpec(parentHeightMeasureSpec, parent.getPaddingTop() + parent.getPaddingBottom() + lp.topMargin + lp.bottomMargin + heightUsed, this.maxHeight, lp.height));
        return true;
    }

    private int getChildMeasureSpec(int parentMeasureSpec, int padding, int maxSize, int childDimension) {
        int i;
        int result = ViewGroup.getChildMeasureSpec(parentMeasureSpec, padding, childDimension);
        if (maxSize == -1) {
            return result;
        }
        int mode = View.MeasureSpec.getMode(result);
        int size = View.MeasureSpec.getSize(result);
        switch (mode) {
            case 1073741824:
                return View.MeasureSpec.makeMeasureSpec(Math.min(size, maxSize), 1073741824);
            default:
                if (size == 0) {
                    i = maxSize;
                } else {
                    i = Math.min(size, maxSize);
                }
                return View.MeasureSpec.makeMeasureSpec(i, Integer.MIN_VALUE);
        }
    }

    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        if (parent.getFitsSystemWindows() && !child.getFitsSystemWindows()) {
            child.setFitsSystemWindows(true);
        }
        if (this.viewRef == null) {
            this.peekHeightMin = parent.getResources().getDimensionPixelSize(R.dimen.design_bottom_sheet_peek_height_min);
            setWindowInsetsListener(child);
            ViewCompat.setWindowInsetsAnimationCallback(child, new InsetsAnimationCallback(child));
            this.viewRef = new WeakReference<>(child);
            this.bottomContainerBackHelper = new MaterialBottomContainerBackHelper(child);
            if (this.materialShapeDrawable != null) {
                child.setBackground(this.materialShapeDrawable);
                this.materialShapeDrawable.setElevation(this.elevation == -1.0f ? child.getElevation() : this.elevation);
            } else if (this.backgroundTint != null) {
                ViewCompat.setBackgroundTintList(child, this.backgroundTint);
            }
            updateAccessibilityActions();
            if (child.getImportantForAccessibility() == 0) {
                child.setImportantForAccessibility(1);
            }
        }
        if (this.viewDragHelper == null) {
            this.viewDragHelper = ViewDragHelper.create(parent, this.dragCallback);
        }
        int savedTop = child.getTop();
        parent.onLayoutChild(child, layoutDirection);
        this.parentWidth = parent.getWidth();
        this.parentHeight = parent.getHeight();
        this.childHeight = child.getHeight();
        if (this.parentHeight - this.childHeight < this.insetTop) {
            if (this.paddingTopSystemWindowInsets) {
                this.childHeight = this.maxHeight == -1 ? this.parentHeight : Math.min(this.parentHeight, this.maxHeight);
            } else {
                int insetHeight = this.parentHeight - this.insetTop;
                this.childHeight = this.maxHeight == -1 ? insetHeight : Math.min(insetHeight, this.maxHeight);
            }
        }
        this.fitToContentsOffset = Math.max(0, this.parentHeight - this.childHeight);
        calculateHalfExpandedOffset();
        calculateCollapsedOffset();
        if (this.state == 3) {
            ViewCompat.offsetTopAndBottom(child, getExpandedOffset());
        } else if (this.state == 6) {
            ViewCompat.offsetTopAndBottom(child, this.halfExpandedOffset);
        } else if (this.hideable && this.state == 5) {
            ViewCompat.offsetTopAndBottom(child, this.parentHeight);
        } else if (this.state == 4) {
            ViewCompat.offsetTopAndBottom(child, this.collapsedOffset);
        } else if (this.state == 1 || this.state == 2) {
            ViewCompat.offsetTopAndBottom(child, savedTop - child.getTop());
        }
        updateDrawableForTargetState(this.state, false);
        this.nestedScrollingChildRef = new WeakReference<>(findScrollingChild(child));
        for (int i = 0; i < this.callbacks.size(); i++) {
            this.callbacks.get(i).onLayout(child);
        }
        return true;
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        if (!child.isShown() || !this.draggable) {
            this.ignoreEvents = true;
            return false;
        }
        int action = event.getActionMasked();
        if (action == 0) {
            reset();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(event);
        switch (action) {
            case 0:
                int initialX = (int) event.getX();
                this.initialY = (int) event.getY();
                if (this.state != 2 && isTouchingScrollingChild(parent, initialX, this.initialY)) {
                    this.activePointerId = event.getPointerId(event.getActionIndex());
                    if (!isTouchingDragHandle(parent, initialX, this.initialY)) {
                        this.touchingScrollingChild = true;
                    }
                }
                this.ignoreEvents = this.activePointerId == -1 && !parent.isPointInChildBounds(child, initialX, this.initialY);
                break;
            case 1:
            case 3:
                this.touchingScrollingChild = false;
                this.activePointerId = -1;
                if (this.ignoreEvents) {
                    this.ignoreEvents = false;
                    return false;
                }
                break;
        }
        if (this.ignoreEvents == 0 && this.viewDragHelper != null && this.viewDragHelper.shouldInterceptTouchEvent(event)) {
            return true;
        }
        View scroll = this.nestedScrollingChildRef != null ? (View) this.nestedScrollingChildRef.get() : null;
        if (action != 2 || scroll == null || this.ignoreEvents || this.state == 1 || parent.isPointInChildBounds(scroll, (int) event.getX(), (int) event.getY()) || this.viewDragHelper == null || this.initialY == -1 || Math.abs(((float) this.initialY) - event.getY()) <= ((float) this.viewDragHelper.getTouchSlop())) {
            return false;
        }
        return true;
    }

    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        if (!child.isShown()) {
            return false;
        }
        int action = event.getActionMasked();
        if (this.state == 1 && action == 0) {
            return true;
        }
        if (shouldHandleDraggingWithHelper()) {
            this.viewDragHelper.processTouchEvent(event);
        }
        if (action == 0) {
            reset();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(event);
        if (shouldHandleDraggingWithHelper() && action == 2 && !this.ignoreEvents && Math.abs(((float) this.initialY) - event.getY()) > ((float) this.viewDragHelper.getTouchSlop())) {
            this.viewDragHelper.captureChildView(child, event.getPointerId(event.getActionIndex()));
        }
        return !this.ignoreEvents;
    }

    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v, View directTargetChild, View target, int axes, int type) {
        this.lastNestedScrollDy = 0;
        this.nestedScrolled = false;
        if ((axes & 2) != 0) {
            return true;
        }
        return false;
    }

    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx, int dy, int[] consumed, int type) {
        if (type != 1) {
            View scrollingChild = this.nestedScrollingChildRef != null ? (View) this.nestedScrollingChildRef.get() : null;
            if (!isNestedScrollingCheckEnabled() || target == scrollingChild) {
                int currentTop = child.getTop();
                int newTop = currentTop - dy;
                if (dy > 0) {
                    if (!this.nestedScrolled && !this.draggableOnNestedScroll && target == scrollingChild && target.canScrollVertically(1)) {
                        this.draggableOnNestedScrollLastDragIgnored = true;
                        return;
                    } else if (newTop < getExpandedOffset()) {
                        consumed[1] = currentTop - getExpandedOffset();
                        ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                        setStateInternal(3);
                    } else if (this.draggable) {
                        consumed[1] = dy;
                        ViewCompat.offsetTopAndBottom(child, -dy);
                        setStateInternal(1);
                    } else {
                        return;
                    }
                } else if (dy < 0) {
                    boolean canScrollUp = target.canScrollVertically(-1);
                    if (!this.nestedScrolled && !this.draggableOnNestedScroll && target == scrollingChild && canScrollUp) {
                        this.draggableOnNestedScrollLastDragIgnored = true;
                        return;
                    } else if (!canScrollUp) {
                        if (newTop > this.collapsedOffset && !canBeHiddenByDragging()) {
                            consumed[1] = currentTop - this.collapsedOffset;
                            ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                            setStateInternal(4);
                        } else if (this.draggable) {
                            consumed[1] = dy;
                            ViewCompat.offsetTopAndBottom(child, -dy);
                            setStateInternal(1);
                        } else {
                            return;
                        }
                    }
                }
                dispatchOnSlide(child.getTop());
                this.lastNestedScrollDy = dy;
                this.nestedScrolled = true;
                this.draggableOnNestedScrollLastDragIgnored = false;
            }
        }
    }

    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V child, View target, int type) {
        int currentTop;
        if (child.getTop() == getExpandedOffset()) {
            setStateInternal(3);
        } else if (!isNestedScrollingCheckEnabled() || (this.nestedScrollingChildRef != null && target == this.nestedScrollingChildRef.get() && this.nestedScrolled)) {
            if (this.lastNestedScrollDy > 0) {
                if (this.fitToContents) {
                    currentTop = 3;
                } else if (child.getTop() > this.halfExpandedOffset) {
                    currentTop = 6;
                } else {
                    currentTop = 3;
                }
            } else if (this.hideable != 0 && shouldHide(child, getYVelocity())) {
                currentTop = 5;
            } else if (this.lastNestedScrollDy == 0) {
                int currentTop2 = child.getTop();
                if (this.fitToContents) {
                    if (Math.abs(currentTop2 - this.fitToContentsOffset) < Math.abs(currentTop2 - this.collapsedOffset)) {
                        currentTop = 3;
                    } else {
                        currentTop = 4;
                    }
                } else if (currentTop2 < this.halfExpandedOffset) {
                    if (currentTop2 < Math.abs(currentTop2 - this.collapsedOffset)) {
                        currentTop = 3;
                    } else if (shouldSkipHalfExpandedStateWhenDragging() != 0) {
                        currentTop = 4;
                    } else {
                        currentTop = 6;
                    }
                } else if (Math.abs(currentTop2 - this.halfExpandedOffset) < Math.abs(currentTop2 - this.collapsedOffset)) {
                    currentTop = 6;
                } else {
                    currentTop = 4;
                }
            } else if (this.fitToContents != 0) {
                currentTop = 4;
            } else {
                int targetState = child.getTop();
                if (Math.abs(targetState - this.halfExpandedOffset) < Math.abs(targetState - this.collapsedOffset)) {
                    currentTop = 6;
                } else {
                    currentTop = 4;
                }
            }
            startSettling(child, currentTop, false);
            this.nestedScrolled = false;
        }
    }

    public void onNestedScroll(CoordinatorLayout coordinatorLayout, V v, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, int[] consumed) {
    }

    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V child, View target, float velocityX, float velocityY) {
        if (!isNestedScrollingCheckEnabled() || this.nestedScrollingChildRef == null || target != this.nestedScrollingChildRef.get()) {
            return false;
        }
        if ((this.state == 3 || this.draggableOnNestedScrollLastDragIgnored) && !super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)) {
            return false;
        }
        return true;
    }

    public boolean isFitToContents() {
        return this.fitToContents;
    }

    public void setFitToContents(boolean fitToContents2) {
        if (this.fitToContents != fitToContents2) {
            this.fitToContents = fitToContents2;
            if (this.viewRef != null) {
                calculateCollapsedOffset();
            }
            setStateInternal((!this.fitToContents || this.state != 6) ? this.state : 3);
            updateDrawableForTargetState(this.state, true);
            updateAccessibilityActions();
        }
    }

    public void setMaxWidth(int maxWidth2) {
        this.maxWidth = maxWidth2;
    }

    public int getMaxWidth() {
        return this.maxWidth;
    }

    public void setMaxHeight(int maxHeight2) {
        this.maxHeight = maxHeight2;
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }

    public void setPeekHeight(int peekHeight2) {
        setPeekHeight(peekHeight2, false);
    }

    public final void setPeekHeight(int peekHeight2, boolean animate) {
        boolean layout = false;
        if (peekHeight2 == -1) {
            if (!this.peekHeightAuto) {
                this.peekHeightAuto = true;
                layout = true;
            }
        } else if (this.peekHeightAuto || this.peekHeight != peekHeight2) {
            this.peekHeightAuto = false;
            this.peekHeight = Math.max(0, peekHeight2);
            layout = true;
        }
        if (layout) {
            updatePeekHeight(animate);
        }
    }

    /* access modifiers changed from: private */
    public void updatePeekHeight(boolean animate) {
        V view;
        if (this.viewRef != null) {
            calculateCollapsedOffset();
            if (this.state == 4 && (view = (View) this.viewRef.get()) != null) {
                if (animate) {
                    setState(4);
                } else {
                    view.requestLayout();
                }
            }
        }
    }

    public int getPeekHeight() {
        if (this.peekHeightAuto) {
            return -1;
        }
        return this.peekHeight;
    }

    public void setHalfExpandedRatio(float ratio) {
        if (ratio <= 0.0f || ratio >= 1.0f) {
            throw new IllegalArgumentException("ratio must be a float value between 0 and 1");
        }
        this.halfExpandedRatio = ratio;
        if (this.viewRef != null) {
            calculateHalfExpandedOffset();
        }
    }

    public float getHalfExpandedRatio() {
        return this.halfExpandedRatio;
    }

    public void setExpandedOffset(int offset) {
        if (offset >= 0) {
            this.expandedOffset = offset;
            updateDrawableForTargetState(this.state, true);
            return;
        }
        throw new IllegalArgumentException("offset must be greater than or equal to 0");
    }

    public int getExpandedOffset() {
        if (this.fitToContents) {
            return this.fitToContentsOffset;
        }
        return Math.max(this.expandedOffset, this.paddingTopSystemWindowInsets ? 0 : this.insetTop);
    }

    public float calculateSlideOffset() {
        if (this.viewRef == null || this.viewRef.get() == null) {
            return -1.0f;
        }
        return calculateSlideOffsetWithTop(((View) this.viewRef.get()).getTop());
    }

    public void setHideable(boolean hideable2) {
        if (this.hideable != hideable2) {
            this.hideable = hideable2;
            if (!hideable2 && this.state == 5) {
                setState(4);
            }
            updateAccessibilityActions();
        }
    }

    public boolean isHideable() {
        return this.hideable;
    }

    public void setSkipCollapsed(boolean skipCollapsed2) {
        this.skipCollapsed = skipCollapsed2;
    }

    public boolean getSkipCollapsed() {
        return this.skipCollapsed;
    }

    public void setDraggable(boolean draggable2) {
        this.draggable = draggable2;
    }

    public boolean isDraggable() {
        return this.draggable;
    }

    public void setDraggableOnNestedScroll(boolean draggableOnNestedScroll2) {
        this.draggableOnNestedScroll = draggableOnNestedScroll2;
    }

    public boolean isDraggableOnNestedScroll() {
        return this.draggableOnNestedScroll;
    }

    public void setSignificantVelocityThreshold(int significantVelocityThreshold2) {
        this.significantVelocityThreshold = significantVelocityThreshold2;
    }

    public int getSignificantVelocityThreshold() {
        return this.significantVelocityThreshold;
    }

    public void setSaveFlags(int flags) {
        this.saveFlags = flags;
    }

    public int getSaveFlags() {
        return this.saveFlags;
    }

    public void setHideFriction(float hideFriction2) {
        this.hideFriction = hideFriction2;
    }

    public float getHideFriction() {
        return this.hideFriction;
    }

    @Deprecated
    public void setBottomSheetCallback(BottomSheetCallback callback) {
        Log.w(TAG, "BottomSheetBehavior now supports multiple callbacks. `setBottomSheetCallback()` removes all existing callbacks, including ones set internally by library authors, which may result in unintended behavior. This may change in the future. Please use `addBottomSheetCallback()` and `removeBottomSheetCallback()` instead to set your own callbacks.");
        this.callbacks.clear();
        if (callback != null) {
            this.callbacks.add(callback);
        }
    }

    public void addBottomSheetCallback(BottomSheetCallback callback) {
        if (!this.callbacks.contains(callback)) {
            this.callbacks.add(callback);
        }
    }

    public void removeBottomSheetCallback(BottomSheetCallback callback) {
        this.callbacks.remove(callback);
    }

    public void setState(int state2) {
        final int finalState;
        if (state2 == 1 || state2 == 2) {
            throw new IllegalArgumentException("STATE_" + (state2 == 1 ? "DRAGGING" : "SETTLING") + " should not be set externally.");
        } else if (this.hideable || state2 != 5) {
            if (state2 != 6 || !this.fitToContents || getTopOffsetForState(state2) > this.fitToContentsOffset) {
                finalState = state2;
            } else {
                finalState = 3;
            }
            if (this.viewRef == null || this.viewRef.get() == null) {
                setStateInternal(state2);
                return;
            }
            final V child = (View) this.viewRef.get();
            runAfterLayout(child, new Runnable() {
                public void run() {
                    BottomSheetBehavior.this.startSettling(child, finalState, false);
                }
            });
        } else {
            Log.w(TAG, "Cannot set state: " + state2);
        }
    }

    private void runAfterLayout(V child, Runnable runnable) {
        if (isLayouting(child)) {
            child.post(runnable);
        } else {
            runnable.run();
        }
    }

    private boolean isLayouting(V child) {
        ViewParent parent = child.getParent();
        return parent != null && parent.isLayoutRequested() && child.isAttachedToWindow();
    }

    public void setGestureInsetBottomIgnored(boolean gestureInsetBottomIgnored2) {
        this.gestureInsetBottomIgnored = gestureInsetBottomIgnored2;
    }

    public boolean isGestureInsetBottomIgnored() {
        return this.gestureInsetBottomIgnored;
    }

    public void setShouldRemoveExpandedCorners(boolean shouldRemoveExpandedCorners2) {
        if (this.shouldRemoveExpandedCorners != shouldRemoveExpandedCorners2) {
            this.shouldRemoveExpandedCorners = shouldRemoveExpandedCorners2;
            updateDrawableForTargetState(getState(), true);
        }
    }

    public boolean isShouldRemoveExpandedCorners() {
        return this.shouldRemoveExpandedCorners;
    }

    public int getState() {
        return this.state;
    }

    /* access modifiers changed from: package-private */
    public void setStateInternal(int state2) {
        View bottomSheet;
        if (this.state != state2) {
            this.state = state2;
            if (state2 == 4 || state2 == 3 || state2 == 6 || (this.hideable && state2 == 5)) {
                this.lastStableState = state2;
            }
            if (this.viewRef != null && (bottomSheet = (View) this.viewRef.get()) != null) {
                if (state2 == 3) {
                    updateImportantForAccessibility(true);
                } else if (state2 == 6 || state2 == 5 || state2 == 4) {
                    updateImportantForAccessibility(false);
                }
                updateDrawableForTargetState(state2, true);
                for (int i = 0; i < this.callbacks.size(); i++) {
                    this.callbacks.get(i).onStateChanged(bottomSheet, state2);
                }
                updateAccessibilityActions();
            }
        }
    }

    private void updateDrawableForTargetState(int state2, boolean animate) {
        boolean removeCorners;
        if (state2 != 2 && this.expandedCornersRemoved != (removeCorners = isExpandedAndShouldRemoveCorners()) && this.materialShapeDrawable != null) {
            this.expandedCornersRemoved = removeCorners;
            float to = 1.0f;
            if (!animate || this.interpolatorAnimator == null) {
                if (this.interpolatorAnimator != null && this.interpolatorAnimator.isRunning()) {
                    this.interpolatorAnimator.cancel();
                }
                MaterialShapeDrawable materialShapeDrawable2 = this.materialShapeDrawable;
                if (this.expandedCornersRemoved) {
                    to = calculateInterpolationWithCornersRemoved();
                }
                materialShapeDrawable2.setInterpolation(to);
            } else if (this.interpolatorAnimator.isRunning()) {
                this.interpolatorAnimator.reverse();
            } else {
                float from = this.materialShapeDrawable.getInterpolation();
                if (removeCorners) {
                    to = calculateInterpolationWithCornersRemoved();
                }
                this.interpolatorAnimator.setFloatValues(new float[]{from, to});
                this.interpolatorAnimator.start();
            }
        }
    }

    private float calculateInterpolationWithCornersRemoved() {
        WindowInsets insets;
        if (this.materialShapeDrawable == null || this.viewRef == null || this.viewRef.get() == null || Build.VERSION.SDK_INT < 31) {
            return 0.0f;
        }
        V view = (View) this.viewRef.get();
        if (!isAtTopOfScreen() || (insets = view.getRootWindowInsets()) == null) {
            return 0.0f;
        }
        return Math.max(calculateCornerInterpolation(this.materialShapeDrawable.getTopLeftCornerResolvedSize(), insets.getRoundedCorner(0)), calculateCornerInterpolation(this.materialShapeDrawable.getTopRightCornerResolvedSize(), insets.getRoundedCorner(1)));
    }

    private float calculateCornerInterpolation(float materialShapeDrawableCornerSize, RoundedCorner deviceRoundedCorner) {
        if (deviceRoundedCorner != null) {
            float deviceCornerRadius = (float) deviceRoundedCorner.getRadius();
            if (deviceCornerRadius > 0.0f && materialShapeDrawableCornerSize > 0.0f) {
                return deviceCornerRadius / materialShapeDrawableCornerSize;
            }
        }
        return 0.0f;
    }

    private boolean isTouchingScrollingChild(CoordinatorLayout parent, int xCoordinate, int yCoordinate) {
        View scrollingChild = this.nestedScrollingChildRef != null ? (View) this.nestedScrollingChildRef.get() : null;
        return scrollingChild != null && parent.isPointInChildBounds(scrollingChild, xCoordinate, yCoordinate);
    }

    private boolean isTouchingDragHandle(CoordinatorLayout parent, int xCoordinate, int yCoordinate) {
        View dragHandleView = this.dragHandleViewRef != null ? (View) this.dragHandleViewRef.get() : null;
        return dragHandleView != null && parent.isPointInChildBounds(dragHandleView, xCoordinate, yCoordinate);
    }

    private boolean isAtTopOfScreen() {
        if (this.viewRef == null || this.viewRef.get() == null) {
            return false;
        }
        int[] location = new int[2];
        ((View) this.viewRef.get()).getLocationOnScreen(location);
        if (location[1] == 0) {
            return true;
        }
        return false;
    }

    private boolean isExpandedAndShouldRemoveCorners() {
        return this.state == 3 && (this.shouldRemoveExpandedCorners || isAtTopOfScreen());
    }

    private int calculatePeekHeight() {
        if (this.peekHeightAuto) {
            return Math.min(Math.max(this.peekHeightMin, this.parentHeight - ((this.parentWidth * 9) / 16)), this.childHeight) + this.insetBottom;
        }
        if (this.gestureInsetBottomIgnored != 0 || this.paddingBottomSystemWindowInsets || this.gestureInsetBottom <= 0) {
            return this.peekHeight + this.insetBottom;
        }
        return Math.max(this.peekHeight, this.gestureInsetBottom + this.peekHeightGestureInsetBuffer);
    }

    private void calculateCollapsedOffset() {
        int peek = calculatePeekHeight();
        if (this.fitToContents) {
            this.collapsedOffset = Math.max(this.parentHeight - peek, this.fitToContentsOffset);
        } else {
            this.collapsedOffset = this.parentHeight - peek;
        }
    }

    private void calculateHalfExpandedOffset() {
        this.halfExpandedOffset = (int) (((float) this.parentHeight) * (1.0f - this.halfExpandedRatio));
    }

    private float calculateSlideOffsetWithTop(int top) {
        if (top > this.collapsedOffset || this.collapsedOffset == getExpandedOffset()) {
            return ((float) (this.collapsedOffset - top)) / ((float) (this.parentHeight - this.collapsedOffset));
        }
        return ((float) (this.collapsedOffset - top)) / ((float) (this.collapsedOffset - getExpandedOffset()));
    }

    private void reset() {
        this.activePointerId = -1;
        this.initialY = -1;
        if (this.velocityTracker != null) {
            this.velocityTracker.recycle();
            this.velocityTracker = null;
        }
    }

    private void restoreOptionalState(SavedState ss) {
        if (this.saveFlags != 0) {
            if (this.saveFlags == -1 || (this.saveFlags & 1) == 1) {
                this.peekHeight = ss.peekHeight;
            }
            if (this.saveFlags == -1 || (this.saveFlags & 2) == 2) {
                this.fitToContents = ss.fitToContents;
            }
            if (this.saveFlags == -1 || (this.saveFlags & 4) == 4) {
                this.hideable = ss.hideable;
            }
            if (this.saveFlags == -1 || (this.saveFlags & 8) == 8) {
                this.skipCollapsed = ss.skipCollapsed;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean shouldHide(View child, float yvel) {
        if (this.skipCollapsed) {
            return true;
        }
        if (!isHideableWhenDragging() || child.getTop() < this.collapsedOffset) {
            return false;
        }
        if (Math.abs((((float) child.getTop()) + (this.hideFriction * yvel)) - ((float) this.collapsedOffset)) / ((float) calculatePeekHeight()) > 0.5f) {
            return true;
        }
        return false;
    }

    public void startBackProgress(BackEventCompat backEvent) {
        if (this.bottomContainerBackHelper != null) {
            this.bottomContainerBackHelper.startBackProgress(backEvent);
        }
    }

    public void updateBackProgress(BackEventCompat backEvent) {
        if (this.bottomContainerBackHelper != null) {
            this.bottomContainerBackHelper.updateBackProgress(backEvent);
        }
    }

    public void handleBackInvoked() {
        if (this.bottomContainerBackHelper != null) {
            BackEventCompat backEvent = this.bottomContainerBackHelper.onHandleBackInvoked();
            int i = 4;
            if (backEvent == null || Build.VERSION.SDK_INT < 34) {
                if (this.hideable) {
                    i = 5;
                }
                setState(i);
            } else if (this.hideable) {
                this.bottomContainerBackHelper.finishBackProgressNotPersistent(backEvent, new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        BottomSheetBehavior.this.setStateInternal(5);
                        if (BottomSheetBehavior.this.viewRef != null && BottomSheetBehavior.this.viewRef.get() != null) {
                            ((View) BottomSheetBehavior.this.viewRef.get()).requestLayout();
                        }
                    }
                });
            } else {
                this.bottomContainerBackHelper.finishBackProgressPersistent(backEvent, (Animator.AnimatorListener) null);
                setState(4);
            }
        }
    }

    public void cancelBackProgress() {
        if (this.bottomContainerBackHelper != null) {
            this.bottomContainerBackHelper.cancelBackProgress();
        }
    }

    /* access modifiers changed from: package-private */
    public MaterialBottomContainerBackHelper getBackHelper() {
        return this.bottomContainerBackHelper;
    }

    /* access modifiers changed from: package-private */
    public View findScrollingChild(View view) {
        if (view.getVisibility() != 0) {
            return null;
        }
        if (view.isNestedScrollingEnabled()) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                View scrollingChild = findScrollingChild(group.getChildAt(i));
                if (scrollingChild != null) {
                    return scrollingChild;
                }
            }
        }
        return null;
    }

    private boolean shouldHandleDraggingWithHelper() {
        return this.viewDragHelper != null && (this.draggable || this.state == 1);
    }

    private void createMaterialShapeDrawableIfNeeded(Context context) {
        if (this.shapeAppearanceModelDefault != null) {
            this.materialShapeDrawable = new MaterialShapeDrawable(this.shapeAppearanceModelDefault);
            this.materialShapeDrawable.initializeElevationOverlay(context);
            if (this.backgroundTint != null) {
                this.materialShapeDrawable.setFillColor(this.backgroundTint);
                return;
            }
            TypedValue defaultColor = new TypedValue();
            context.getTheme().resolveAttribute(16842801, defaultColor, true);
            this.materialShapeDrawable.setTint(defaultColor.data);
        }
    }

    /* access modifiers changed from: package-private */
    public MaterialShapeDrawable getMaterialShapeDrawable() {
        return this.materialShapeDrawable;
    }

    private void createShapeValueAnimator() {
        this.interpolatorAnimator = ValueAnimator.ofFloat(new float[]{calculateInterpolationWithCornersRemoved(), 1.0f});
        this.interpolatorAnimator.setDuration(500);
        this.interpolatorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = ((Float) animation.getAnimatedValue()).floatValue();
                if (BottomSheetBehavior.this.materialShapeDrawable != null) {
                    BottomSheetBehavior.this.materialShapeDrawable.setInterpolation(value);
                }
            }
        });
    }

    private void setWindowInsetsListener(View child) {
        final boolean shouldHandleGestureInsets = Build.VERSION.SDK_INT >= 29 && !isGestureInsetBottomIgnored() && !this.peekHeightAuto;
        if (this.paddingBottomSystemWindowInsets || this.paddingLeftSystemWindowInsets || this.paddingRightSystemWindowInsets || this.marginLeftSystemWindowInsets || this.marginRightSystemWindowInsets || this.marginTopSystemWindowInsets || shouldHandleGestureInsets) {
            ViewUtils.doOnApplyWindowInsets(child, new ViewUtils.OnApplyWindowInsetsListener() {
                public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets, ViewUtils.RelativePadding initialPadding) {
                    Insets systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    Insets mandatoryGestureInsets = insets.getInsets(WindowInsetsCompat.Type.mandatorySystemGestures());
                    int unused = BottomSheetBehavior.this.insetTop = systemBarInsets.top;
                    boolean isRtl = ViewUtils.isLayoutRtl(view);
                    int bottomPadding = view.getPaddingBottom();
                    int leftPadding = view.getPaddingLeft();
                    int rightPadding = view.getPaddingRight();
                    if (BottomSheetBehavior.this.paddingBottomSystemWindowInsets) {
                        int unused2 = BottomSheetBehavior.this.insetBottom = insets.getSystemWindowInsetBottom();
                        bottomPadding = initialPadding.bottom + BottomSheetBehavior.this.insetBottom;
                    }
                    if (BottomSheetBehavior.this.paddingLeftSystemWindowInsets) {
                        leftPadding = systemBarInsets.left + (isRtl ? initialPadding.end : initialPadding.start);
                    }
                    if (BottomSheetBehavior.this.paddingRightSystemWindowInsets) {
                        rightPadding = systemBarInsets.right + (isRtl ? initialPadding.start : initialPadding.end);
                    }
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                    boolean marginUpdated = false;
                    if (BottomSheetBehavior.this.marginLeftSystemWindowInsets && mlp.leftMargin != systemBarInsets.left) {
                        mlp.leftMargin = systemBarInsets.left;
                        marginUpdated = true;
                    }
                    if (BottomSheetBehavior.this.marginRightSystemWindowInsets && mlp.rightMargin != systemBarInsets.right) {
                        mlp.rightMargin = systemBarInsets.right;
                        marginUpdated = true;
                    }
                    if (BottomSheetBehavior.this.marginTopSystemWindowInsets && mlp.topMargin != systemBarInsets.top) {
                        mlp.topMargin = systemBarInsets.top;
                        marginUpdated = true;
                    }
                    if (marginUpdated) {
                        view.setLayoutParams(mlp);
                    }
                    view.setPadding(leftPadding, view.getPaddingTop(), rightPadding, bottomPadding);
                    if (shouldHandleGestureInsets) {
                        int unused3 = BottomSheetBehavior.this.gestureInsetBottom = mandatoryGestureInsets.bottom;
                    }
                    if (BottomSheetBehavior.this.paddingBottomSystemWindowInsets || shouldHandleGestureInsets) {
                        BottomSheetBehavior.this.updatePeekHeight(false);
                    }
                    return insets;
                }
            });
        }
    }

    private float getYVelocity() {
        if (this.velocityTracker == null) {
            return 0.0f;
        }
        this.velocityTracker.computeCurrentVelocity(1000, this.maximumVelocity);
        return this.velocityTracker.getYVelocity(this.activePointerId);
    }

    /* access modifiers changed from: private */
    public void startSettling(View child, int state2, boolean isReleasingView) {
        int top = getTopOffsetForState(state2);
        if (this.viewDragHelper != null && (!isReleasingView ? this.viewDragHelper.smoothSlideViewTo(child, child.getLeft(), top) : this.viewDragHelper.settleCapturedViewAt(child.getLeft(), top))) {
            setStateInternal(2);
            updateDrawableForTargetState(state2, true);
            this.stateSettlingTracker.continueSettlingToState(state2);
            return;
        }
        setStateInternal(state2);
    }

    private int getTopOffsetForState(int state2) {
        switch (state2) {
            case 3:
                return getExpandedOffset();
            case 4:
                return this.collapsedOffset;
            case 5:
                return this.parentHeight;
            case 6:
                return this.halfExpandedOffset;
            default:
                throw new IllegalArgumentException("Invalid state to get top offset: " + state2);
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchOnSlide(int top) {
        View bottomSheet = (View) this.viewRef.get();
        if (bottomSheet != null && !this.callbacks.isEmpty()) {
            float slideOffset = calculateSlideOffsetWithTop(top);
            for (int i = 0; i < this.callbacks.size(); i++) {
                this.callbacks.get(i).onSlide(bottomSheet, slideOffset);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int getPeekHeightMin() {
        return this.peekHeightMin;
    }

    public void disableShapeAnimations() {
        this.interpolatorAnimator = null;
    }

    public boolean isNestedScrollingCheckEnabled() {
        return true;
    }

    public boolean shouldSkipHalfExpandedStateWhenDragging() {
        return false;
    }

    public boolean shouldSkipSmoothAnimation() {
        return true;
    }

    public boolean isHideableWhenDragging() {
        return true;
    }

    /* access modifiers changed from: private */
    public boolean canBeHiddenByDragging() {
        return isHideable() && isHideableWhenDragging();
    }

    public boolean shouldExpandOnUpwardDrag(long dragDurationMillis, float yPositionPercentage) {
        return false;
    }

    public void setHideableInternal(boolean hideable2) {
        this.hideable = hideable2;
    }

    public int getLastStableState() {
        return this.lastStableState;
    }

    private class StateSettlingTracker {
        private final Runnable continueSettlingRunnable;
        /* access modifiers changed from: private */
        public boolean isContinueSettlingRunnablePosted;
        /* access modifiers changed from: private */
        public int targetState;

        private StateSettlingTracker() {
            this.continueSettlingRunnable = new Runnable() {
                public void run() {
                    boolean unused = StateSettlingTracker.this.isContinueSettlingRunnablePosted = false;
                    if (BottomSheetBehavior.this.viewDragHelper != null && BottomSheetBehavior.this.viewDragHelper.continueSettling(true)) {
                        StateSettlingTracker.this.continueSettlingToState(StateSettlingTracker.this.targetState);
                    } else if (BottomSheetBehavior.this.state == 2) {
                        BottomSheetBehavior.this.setStateInternal(StateSettlingTracker.this.targetState);
                    }
                }
            };
        }

        /* access modifiers changed from: package-private */
        public void continueSettlingToState(int targetState2) {
            if (BottomSheetBehavior.this.viewRef != null && BottomSheetBehavior.this.viewRef.get() != null) {
                this.targetState = targetState2;
                if (!this.isContinueSettlingRunnablePosted) {
                    ((View) BottomSheetBehavior.this.viewRef.get()).postOnAnimation(this.continueSettlingRunnable);
                    this.isContinueSettlingRunnablePosted = true;
                }
            }
        }
    }

    protected static class SavedState extends AbsSavedState {
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
        boolean fitToContents;
        boolean hideable;
        int peekHeight;
        boolean skipCollapsed;
        final int state;

        public SavedState(Parcel source) {
            this(source, (ClassLoader) null);
        }

        public SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            this.state = source.readInt();
            this.peekHeight = source.readInt();
            boolean z = false;
            this.fitToContents = source.readInt() == 1;
            this.hideable = source.readInt() == 1;
            this.skipCollapsed = source.readInt() == 1 ? true : z;
        }

        public SavedState(Parcelable superState, BottomSheetBehavior<?> behavior) {
            super(superState);
            this.state = behavior.state;
            this.peekHeight = behavior.peekHeight;
            this.fitToContents = behavior.fitToContents;
            this.hideable = behavior.hideable;
            this.skipCollapsed = behavior.skipCollapsed;
        }

        @Deprecated
        public SavedState(Parcelable superstate, int state2) {
            super(superstate);
            this.state = state2;
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.state);
            out.writeInt(this.peekHeight);
            out.writeInt(this.fitToContents ? 1 : 0);
            out.writeInt(this.hideable ? 1 : 0);
            out.writeInt(this.skipCollapsed ? 1 : 0);
        }
    }

    public static <V extends View> BottomSheetBehavior<V> from(V view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof CoordinatorLayout.LayoutParams) {
            CoordinatorLayout.Behavior<?> behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
            if (behavior instanceof BottomSheetBehavior) {
                return (BottomSheetBehavior) behavior;
            }
            throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
        }
        throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
    }

    public void setUpdateImportantForAccessibilityOnSiblings(boolean updateImportantForAccessibilityOnSiblings2) {
        this.updateImportantForAccessibilityOnSiblings = updateImportantForAccessibilityOnSiblings2;
    }

    private void updateImportantForAccessibility(boolean expanded) {
        if (this.viewRef != null) {
            ViewParent viewParent = ((View) this.viewRef.get()).getParent();
            if (viewParent instanceof CoordinatorLayout) {
                CoordinatorLayout parent = (CoordinatorLayout) viewParent;
                int childCount = parent.getChildCount();
                if (expanded) {
                    if (this.importantForAccessibilityMap == null) {
                        this.importantForAccessibilityMap = new HashMap(childCount);
                    } else {
                        return;
                    }
                }
                for (int i = 0; i < childCount; i++) {
                    View child = parent.getChildAt(i);
                    if (child != this.viewRef.get()) {
                        if (expanded) {
                            this.importantForAccessibilityMap.put(child, Integer.valueOf(child.getImportantForAccessibility()));
                            if (this.updateImportantForAccessibilityOnSiblings) {
                                child.setImportantForAccessibility(4);
                            }
                        } else if (this.updateImportantForAccessibilityOnSiblings && this.importantForAccessibilityMap != null && this.importantForAccessibilityMap.containsKey(child)) {
                            child.setImportantForAccessibility(this.importantForAccessibilityMap.get(child).intValue());
                        }
                    }
                }
                if (!expanded) {
                    this.importantForAccessibilityMap = null;
                } else if (this.updateImportantForAccessibilityOnSiblings) {
                    ((View) this.viewRef.get()).sendAccessibilityEvent(8);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setDragHandleView(BottomSheetDragHandleView dragHandleView) {
        this.dragHandleViewRef = dragHandleView != null ? new WeakReference<>(dragHandleView) : null;
    }

    /* access modifiers changed from: package-private */
    public void setAccessibilityDelegateView(View accessibilityDelegateView) {
        if (accessibilityDelegateView != null || this.accessibilityDelegateViewRef == null) {
            this.accessibilityDelegateViewRef = new WeakReference<>(accessibilityDelegateView);
            updateAccessibilityActions(accessibilityDelegateView, 1);
            return;
        }
        clearAccessibilityAction((View) this.accessibilityDelegateViewRef.get(), 1);
        this.accessibilityDelegateViewRef = null;
    }

    private void updateAccessibilityActions() {
        if (this.viewRef != null) {
            updateAccessibilityActions((View) this.viewRef.get(), 0);
        }
        if (this.accessibilityDelegateViewRef != null) {
            updateAccessibilityActions((View) this.accessibilityDelegateViewRef.get(), 1);
        }
    }

    private void updateAccessibilityActions(View view, int viewIndex) {
        if (view != null) {
            clearAccessibilityAction(view, viewIndex);
            int nextState = 6;
            if (!this.fitToContents && this.state != 6) {
                this.expandHalfwayActionIds.put(viewIndex, addAccessibilityActionForState(view, R.string.bottomsheet_action_expand_halfway, 6));
            }
            if (this.hideable && isHideableWhenDragging() && this.state != 5) {
                replaceAccessibilityActionForState(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, 5);
            }
            switch (this.state) {
                case 3:
                    if (this.fitToContents) {
                        nextState = 4;
                    }
                    replaceAccessibilityActionForState(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, nextState);
                    return;
                case 4:
                    if (this.fitToContents) {
                        nextState = 3;
                    }
                    replaceAccessibilityActionForState(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, nextState);
                    return;
                case 6:
                    replaceAccessibilityActionForState(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE, 4);
                    replaceAccessibilityActionForState(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, 3);
                    return;
                default:
                    return;
            }
        }
    }

    private void clearAccessibilityAction(View view, int viewIndex) {
        if (view != null) {
            ViewCompat.removeAccessibilityAction(view, 524288);
            ViewCompat.removeAccessibilityAction(view, 262144);
            ViewCompat.removeAccessibilityAction(view, 1048576);
            int expandHalfwayActionId = this.expandHalfwayActionIds.get(viewIndex, -1);
            if (expandHalfwayActionId != -1) {
                ViewCompat.removeAccessibilityAction(view, expandHalfwayActionId);
                this.expandHalfwayActionIds.delete(viewIndex);
            }
        }
    }

    private void replaceAccessibilityActionForState(View child, AccessibilityNodeInfoCompat.AccessibilityActionCompat action, int state2) {
        ViewCompat.replaceAccessibilityAction(child, action, (CharSequence) null, createAccessibilityViewCommandForState(state2));
    }

    private int addAccessibilityActionForState(View child, int stringResId, int state2) {
        return ViewCompat.addAccessibilityAction(child, child.getResources().getString(stringResId), createAccessibilityViewCommandForState(state2));
    }

    private AccessibilityViewCommand createAccessibilityViewCommandForState(final int state2) {
        return new AccessibilityViewCommand() {
            public boolean perform(View view, AccessibilityViewCommand.CommandArguments arguments) {
                BottomSheetBehavior.this.setState(state2);
                return true;
            }
        };
    }
}
