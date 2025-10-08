package com.google.android.material.bottomsheet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.EdgeToEdgeUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.motion.MaterialBackOrchestrator;
import com.google.android.material.shape.MaterialShapeDrawable;

public class BottomSheetDialog extends AppCompatDialog {
    private MaterialBackOrchestrator backOrchestrator;
    /* access modifiers changed from: private */
    public BottomSheetBehavior<FrameLayout> behavior;
    /* access modifiers changed from: private */
    public FrameLayout bottomSheet;
    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback;
    boolean cancelable;
    private boolean canceledOnTouchOutside;
    private boolean canceledOnTouchOutsideSet;
    private FrameLayout container;
    private CoordinatorLayout coordinator;
    boolean dismissWithAnimation;
    /* access modifiers changed from: private */
    public EdgeToEdgeCallback edgeToEdgeCallback;
    private boolean edgeToEdgeEnabled;

    public BottomSheetDialog(Context context) {
        this(context, 0);
        initialize();
    }

    public BottomSheetDialog(Context context, int theme) {
        super(context, getThemeResId(context, theme));
        this.cancelable = true;
        this.canceledOnTouchOutside = true;
        this.bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == 5) {
                    BottomSheetDialog.this.cancel();
                }
            }

            public void onSlide(View bottomSheet, float slideOffset) {
            }
        };
        supportRequestWindowFeature(1);
        initialize();
    }

    protected BottomSheetDialog(Context context, boolean cancelable2, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable2, cancelListener);
        this.cancelable = true;
        this.canceledOnTouchOutside = true;
        this.bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == 5) {
                    BottomSheetDialog.this.cancel();
                }
            }

            public void onSlide(View bottomSheet, float slideOffset) {
            }
        };
        supportRequestWindowFeature(1);
        this.cancelable = cancelable2;
        initialize();
    }

    private void initialize() {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.enableEdgeToEdge});
        this.edgeToEdgeEnabled = a.getBoolean(0, false);
        a.recycle();
    }

    public void setContentView(int layoutResId) {
        super.setContentView(wrapInBottomSheet(layoutResId, (View) null, (ViewGroup.LayoutParams) null));
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setStatusBarColor(0);
            window.addFlags(Integer.MIN_VALUE);
            window.setLayout(-1, -1);
        }
    }

    public void setContentView(View view) {
        super.setContentView(wrapInBottomSheet(0, view, (ViewGroup.LayoutParams) null));
    }

    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(wrapInBottomSheet(0, view, params));
    }

    public void setCancelable(boolean cancelable2) {
        super.setCancelable(cancelable2);
        if (this.cancelable != cancelable2) {
            this.cancelable = cancelable2;
            if (this.behavior != null) {
                this.behavior.setHideable(cancelable2);
            }
            if (getWindow() != null) {
                updateListeningForBackCallbacks();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        if (this.behavior != null && this.behavior.getState() == 5) {
            this.behavior.setState(4);
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        if (window != null) {
            boolean z = true;
            boolean drawEdgeToEdge = this.edgeToEdgeEnabled && Color.alpha(window.getNavigationBarColor()) < 255;
            if (this.container != null) {
                this.container.setFitsSystemWindows(!drawEdgeToEdge);
            }
            if (this.coordinator != null) {
                this.coordinator.setFitsSystemWindows(!drawEdgeToEdge);
            }
            if (drawEdgeToEdge) {
                z = false;
            }
            WindowCompat.setDecorFitsSystemWindows(window, z);
            if (this.edgeToEdgeCallback != null) {
                this.edgeToEdgeCallback.setWindow(window);
            }
        }
        updateListeningForBackCallbacks();
    }

    public void onDetachedFromWindow() {
        if (this.edgeToEdgeCallback != null) {
            this.edgeToEdgeCallback.setWindow((Window) null);
        }
        if (this.backOrchestrator != null) {
            this.backOrchestrator.stopListeningForBackCallbacks();
        }
    }

    public void cancel() {
        BottomSheetBehavior<FrameLayout> behavior2 = getBehavior();
        if (!this.dismissWithAnimation || behavior2.getState() == 5) {
            super.cancel();
        } else {
            behavior2.setState(5);
        }
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        if (cancel && !this.cancelable) {
            this.cancelable = true;
        }
        this.canceledOnTouchOutside = cancel;
        this.canceledOnTouchOutsideSet = true;
    }

    public BottomSheetBehavior<FrameLayout> getBehavior() {
        if (this.behavior == null) {
            ensureContainerAndBehavior();
        }
        return this.behavior;
    }

    public void setDismissWithAnimation(boolean dismissWithAnimation2) {
        this.dismissWithAnimation = dismissWithAnimation2;
    }

    public boolean getDismissWithAnimation() {
        return this.dismissWithAnimation;
    }

    public boolean getEdgeToEdgeEnabled() {
        return this.edgeToEdgeEnabled;
    }

    private FrameLayout ensureContainerAndBehavior() {
        if (this.container == null) {
            this.container = (FrameLayout) View.inflate(getContext(), R.layout.design_bottom_sheet_dialog, (ViewGroup) null);
            this.coordinator = (CoordinatorLayout) this.container.findViewById(R.id.coordinator);
            this.bottomSheet = (FrameLayout) this.container.findViewById(R.id.design_bottom_sheet);
            this.behavior = BottomSheetBehavior.from(this.bottomSheet);
            this.behavior.addBottomSheetCallback(this.bottomSheetCallback);
            this.behavior.setHideable(this.cancelable);
            this.backOrchestrator = new MaterialBackOrchestrator(this.behavior, this.bottomSheet);
        }
        return this.container;
    }

    private View wrapInBottomSheet(int layoutResId, View view, ViewGroup.LayoutParams params) {
        ensureContainerAndBehavior();
        CoordinatorLayout coordinator2 = (CoordinatorLayout) this.container.findViewById(R.id.coordinator);
        if (layoutResId != 0 && view == null) {
            view = getLayoutInflater().inflate(layoutResId, coordinator2, false);
        }
        if (this.edgeToEdgeEnabled) {
            ViewCompat.setOnApplyWindowInsetsListener(this.container, new OnApplyWindowInsetsListener() {
                public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets) {
                    if (BottomSheetDialog.this.edgeToEdgeCallback != null) {
                        BottomSheetDialog.this.behavior.removeBottomSheetCallback(BottomSheetDialog.this.edgeToEdgeCallback);
                    }
                    if (insets != null) {
                        EdgeToEdgeCallback unused = BottomSheetDialog.this.edgeToEdgeCallback = new EdgeToEdgeCallback(BottomSheetDialog.this.bottomSheet, insets);
                        BottomSheetDialog.this.edgeToEdgeCallback.setWindow(BottomSheetDialog.this.getWindow());
                        BottomSheetDialog.this.behavior.addBottomSheetCallback(BottomSheetDialog.this.edgeToEdgeCallback);
                    }
                    return insets;
                }
            });
        }
        this.bottomSheet.removeAllViews();
        if (params == null) {
            this.bottomSheet.addView(view);
        } else {
            this.bottomSheet.addView(view, params);
        }
        coordinator2.findViewById(R.id.touch_outside).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (BottomSheetDialog.this.cancelable && BottomSheetDialog.this.isShowing() && BottomSheetDialog.this.shouldWindowCloseOnTouchOutside()) {
                    BottomSheetDialog.this.cancel();
                }
            }
        });
        ViewCompat.setAccessibilityDelegate(this.bottomSheet, new AccessibilityDelegateCompat() {
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                if (BottomSheetDialog.this.cancelable) {
                    info.addAction(1048576);
                    info.setDismissable(true);
                    return;
                }
                info.setDismissable(false);
            }

            public boolean performAccessibilityAction(View host, int action, Bundle args) {
                if (action != 1048576 || !BottomSheetDialog.this.cancelable) {
                    return super.performAccessibilityAction(host, action, args);
                }
                BottomSheetDialog.this.cancel();
                return true;
            }
        });
        this.bottomSheet.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                return true;
            }
        });
        return this.container;
    }

    private void updateListeningForBackCallbacks() {
        if (this.backOrchestrator != null) {
            if (this.cancelable) {
                this.backOrchestrator.startListeningForBackCallbacks();
            } else {
                this.backOrchestrator.stopListeningForBackCallbacks();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean shouldWindowCloseOnTouchOutside() {
        if (!this.canceledOnTouchOutsideSet) {
            TypedArray a = getContext().obtainStyledAttributes(new int[]{16843611});
            this.canceledOnTouchOutside = a.getBoolean(0, true);
            a.recycle();
            this.canceledOnTouchOutsideSet = true;
        }
        return this.canceledOnTouchOutside;
    }

    private static int getThemeResId(Context context, int themeId) {
        if (themeId != 0) {
            return themeId;
        }
        TypedValue outValue = new TypedValue();
        if (context.getTheme().resolveAttribute(R.attr.bottomSheetDialogTheme, outValue, true)) {
            return outValue.resourceId;
        }
        return R.style.Theme_Design_Light_BottomSheetDialog;
    }

    /* access modifiers changed from: package-private */
    public void removeDefaultCallback() {
        this.behavior.removeBottomSheetCallback(this.bottomSheetCallback);
    }

    private static class EdgeToEdgeCallback extends BottomSheetBehavior.BottomSheetCallback {
        private final WindowInsetsCompat insetsCompat;
        private final Boolean lightBottomSheet;
        private boolean lightStatusBar;
        private Window window;

        private EdgeToEdgeCallback(View bottomSheet, WindowInsetsCompat insetsCompat2) {
            ColorStateList backgroundTint;
            this.insetsCompat = insetsCompat2;
            MaterialShapeDrawable msd = BottomSheetBehavior.from(bottomSheet).getMaterialShapeDrawable();
            if (msd != null) {
                backgroundTint = msd.getFillColor();
            } else {
                backgroundTint = bottomSheet.getBackgroundTintList();
            }
            if (backgroundTint != null) {
                this.lightBottomSheet = Boolean.valueOf(MaterialColors.isColorLight(backgroundTint.getDefaultColor()));
                return;
            }
            Integer backgroundColor = ViewUtils.getBackgroundColor(bottomSheet);
            if (backgroundColor != null) {
                this.lightBottomSheet = Boolean.valueOf(MaterialColors.isColorLight(backgroundColor.intValue()));
            } else {
                this.lightBottomSheet = null;
            }
        }

        public void onStateChanged(View bottomSheet, int newState) {
            setPaddingForPosition(bottomSheet);
        }

        public void onSlide(View bottomSheet, float slideOffset) {
            setPaddingForPosition(bottomSheet);
        }

        /* access modifiers changed from: package-private */
        public void onLayout(View bottomSheet) {
            setPaddingForPosition(bottomSheet);
        }

        /* access modifiers changed from: package-private */
        public void setWindow(Window window2) {
            if (this.window != window2) {
                this.window = window2;
                if (window2 != null) {
                    this.lightStatusBar = WindowCompat.getInsetsController(window2, window2.getDecorView()).isAppearanceLightStatusBars();
                }
            }
        }

        private void setPaddingForPosition(View bottomSheet) {
            if (bottomSheet.getTop() < this.insetsCompat.getSystemWindowInsetTop()) {
                if (this.window != null) {
                    EdgeToEdgeUtils.setLightStatusBar(this.window, this.lightBottomSheet == null ? this.lightStatusBar : this.lightBottomSheet.booleanValue());
                }
                bottomSheet.setPadding(bottomSheet.getPaddingLeft(), this.insetsCompat.getSystemWindowInsetTop() - bottomSheet.getTop(), bottomSheet.getPaddingRight(), bottomSheet.getPaddingBottom());
            } else if (bottomSheet.getTop() != 0) {
                if (this.window != null) {
                    EdgeToEdgeUtils.setLightStatusBar(this.window, this.lightStatusBar);
                }
                bottomSheet.setPadding(bottomSheet.getPaddingLeft(), 0, bottomSheet.getPaddingRight(), bottomSheet.getPaddingBottom());
            }
        }
    }

    @Deprecated
    public static void setLightStatusBar(View view, boolean isLight) {
        int flags;
        int flags2 = view.getSystemUiVisibility();
        if (isLight) {
            flags = flags2 | 8192;
        } else {
            flags = flags2 & -8193;
        }
        view.setSystemUiVisibility(flags);
    }
}
