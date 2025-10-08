package com.google.android.material.bottomsheet;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import com.google.android.material.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class BottomSheetDragHandleView extends AppCompatImageView implements AccessibilityManager.AccessibilityStateChangeListener {
    private static final int DEF_STYLE_RES = R.style.Widget_Material3_BottomSheet_DragHandle;
    private final AccessibilityManager accessibilityManager;
    /* access modifiers changed from: private */
    public BottomSheetBehavior<?> bottomSheetBehavior;
    private final BottomSheetBehavior.BottomSheetCallback bottomSheetCallback;
    private final String clickToCollapseActionLabel;
    private boolean clickToExpand;
    private final String clickToExpandActionLabel;
    private final GestureDetector gestureDetector;
    private final GestureDetector.OnGestureListener gestureListener;
    private boolean hasClickListener;
    private boolean hasTouchListener;

    public BottomSheetDragHandleView(Context context) {
        this(context, (AttributeSet) null);
    }

    public BottomSheetDragHandleView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.bottomSheetDragHandleStyle);
    }

    public BottomSheetDragHandleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        this.hasTouchListener = false;
        this.hasClickListener = false;
        this.clickToExpandActionLabel = getResources().getString(R.string.bottomsheet_action_expand);
        this.clickToCollapseActionLabel = getResources().getString(R.string.bottomsheet_action_collapse);
        this.bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
            public void onStateChanged(View bottomSheet, int newState) {
                BottomSheetDragHandleView.this.onBottomSheetStateChanged(newState);
            }

            public void onSlide(View bottomSheet, float slideOffset) {
            }
        };
        this.gestureListener = new GestureDetector.SimpleOnGestureListener() {
            public boolean onDown(MotionEvent e) {
                return BottomSheetDragHandleView.this.isClickable();
            }

            public boolean onSingleTapConfirmed(MotionEvent e) {
                return BottomSheetDragHandleView.this.expandOrCollapseBottomSheetIfPossible();
            }

            public boolean onDoubleTap(MotionEvent e) {
                if (BottomSheetDragHandleView.this.bottomSheetBehavior == null || !BottomSheetDragHandleView.this.bottomSheetBehavior.isHideable()) {
                    return super.onDoubleTap(e);
                }
                BottomSheetDragHandleView.this.bottomSheetBehavior.setState(5);
                return true;
            }
        };
        Context context2 = getContext();
        this.gestureDetector = new GestureDetector(context2, this.gestureListener, new Handler(Looper.getMainLooper()));
        this.accessibilityManager = (AccessibilityManager) context2.getSystemService("accessibility");
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat() {
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);
                if (event.getEventType() == 1) {
                    boolean unused = BottomSheetDragHandleView.this.expandOrCollapseBottomSheetIfPossible();
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setBottomSheetBehavior(findParentBottomSheetBehavior());
        if (this.accessibilityManager != null) {
            this.accessibilityManager.addAccessibilityStateChangeListener(this);
            onAccessibilityStateChanged(this.accessibilityManager.isEnabled());
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (this.accessibilityManager != null) {
            this.accessibilityManager.removeAccessibilityStateChangeListener(this);
        }
        setBottomSheetBehavior((BottomSheetBehavior<?>) null);
        super.onDetachedFromWindow();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.hasClickListener || this.hasTouchListener) {
            return super.onTouchEvent(event);
        }
        return this.gestureDetector.onTouchEvent(event);
    }

    public void setOnTouchListener(View.OnTouchListener l) {
        this.hasTouchListener = l != null;
        super.setOnTouchListener(l);
    }

    public void setOnClickListener(View.OnClickListener l) {
        this.hasClickListener = l != null;
        super.setOnClickListener(l);
    }

    public void onAccessibilityStateChanged(boolean enabled) {
    }

    private void setBottomSheetBehavior(BottomSheetBehavior<?> behavior) {
        if (this.bottomSheetBehavior != null) {
            this.bottomSheetBehavior.removeBottomSheetCallback(this.bottomSheetCallback);
            this.bottomSheetBehavior.setAccessibilityDelegateView((View) null);
            this.bottomSheetBehavior.setDragHandleView((BottomSheetDragHandleView) null);
        }
        this.bottomSheetBehavior = behavior;
        if (this.bottomSheetBehavior != null) {
            this.bottomSheetBehavior.setAccessibilityDelegateView(this);
            this.bottomSheetBehavior.setDragHandleView(this);
            onBottomSheetStateChanged(this.bottomSheetBehavior.getState());
            this.bottomSheetBehavior.addBottomSheetCallback(this.bottomSheetCallback);
        }
        setClickable(hasAttachedBehavior());
    }

    /* access modifiers changed from: private */
    public void onBottomSheetStateChanged(int state) {
        if (state == 4) {
            this.clickToExpand = true;
        } else if (state == 3) {
            this.clickToExpand = false;
        }
        ViewCompat.replaceAccessibilityAction(this, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, this.clickToExpand ? this.clickToExpandActionLabel : this.clickToCollapseActionLabel, new BottomSheetDragHandleView$$ExternalSyntheticLambda0(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$onBottomSheetStateChanged$0$com-google-android-material-bottomsheet-BottomSheetDragHandleView  reason: not valid java name */
    public /* synthetic */ boolean m1635lambda$onBottomSheetStateChanged$0$comgoogleandroidmaterialbottomsheetBottomSheetDragHandleView(View v, AccessibilityViewCommand.CommandArguments args) {
        return expandOrCollapseBottomSheetIfPossible();
    }

    private boolean hasAttachedBehavior() {
        return this.bottomSheetBehavior != null;
    }

    /* access modifiers changed from: private */
    public boolean expandOrCollapseBottomSheetIfPossible() {
        boolean canHalfExpand = false;
        if (!hasAttachedBehavior()) {
            return false;
        }
        if (!this.bottomSheetBehavior.isFitToContents() && !this.bottomSheetBehavior.shouldSkipHalfExpandedStateWhenDragging()) {
            canHalfExpand = true;
        }
        int currentState = this.bottomSheetBehavior.getState();
        int nextState = 6;
        int i = 3;
        if (currentState == 4) {
            if (!canHalfExpand) {
                nextState = 3;
            }
        } else if (currentState != 3) {
            if (this.clickToExpand == 0) {
                i = 4;
            }
            nextState = i;
        } else if (!canHalfExpand) {
            nextState = 4;
        }
        this.bottomSheetBehavior.setState(nextState);
        return true;
    }

    private BottomSheetBehavior<?> findParentBottomSheetBehavior() {
        View parent = this;
        while (true) {
            View parentView = getParentView(parent);
            parent = parentView;
            if (parentView == null) {
                return null;
            }
            ViewGroup.LayoutParams layoutParams = parent.getLayoutParams();
            if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
                CoordinatorLayout.Behavior<?> behavior = ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
                if (behavior instanceof BottomSheetBehavior) {
                    return (BottomSheetBehavior) behavior;
                }
            }
        }
    }

    private static View getParentView(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof View) {
            return (View) parent;
        }
        return null;
    }
}
