package com.google.android.material.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import com.google.android.material.R;
import com.google.android.material.drawable.DrawableUtils;
import java.util.ArrayList;
import java.util.List;

public class ViewUtils {
    public static final int EDGE_TO_EDGE_FLAGS = 768;

    public interface OnApplyWindowInsetsListener {
        WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat, RelativePadding relativePadding);
    }

    private ViewUtils() {
    }

    public static void showKeyboard(View view) {
        showKeyboard(view, true);
    }

    public static void showKeyboard(View view, boolean useWindowInsetsController) {
        WindowInsetsControllerCompat windowController;
        if (!useWindowInsetsController || (windowController = ViewCompat.getWindowInsetsController(view)) == null) {
            getInputMethodManager(view).showSoftInput(view, 1);
        } else {
            windowController.show(WindowInsetsCompat.Type.ime());
        }
    }

    public static void requestFocusAndShowKeyboard(View view) {
        requestFocusAndShowKeyboard(view, true);
    }

    public static void requestFocusAndShowKeyboard(View view, boolean useWindowInsetsController) {
        view.requestFocus();
        view.post(new ViewUtils$$ExternalSyntheticLambda0(view, useWindowInsetsController));
    }

    public static void hideKeyboard(View view) {
        hideKeyboard(view, true);
    }

    public static void hideKeyboard(View view, boolean useWindowInsetsController) {
        WindowInsetsControllerCompat windowController;
        if (!useWindowInsetsController || (windowController = ViewCompat.getWindowInsetsController(view)) == null) {
            InputMethodManager imm = getInputMethodManager(view);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                return;
            }
            return;
        }
        windowController.hide(WindowInsetsCompat.Type.ime());
    }

    private static InputMethodManager getInputMethodManager(View view) {
        return (InputMethodManager) ContextCompat.getSystemService(view.getContext(), InputMethodManager.class);
    }

    public static void setBoundsFromRect(View view, Rect rect) {
        view.setLeft(rect.left);
        view.setTop(rect.top);
        view.setRight(rect.right);
        view.setBottom(rect.bottom);
    }

    public static Rect calculateRectFromBounds(View view) {
        return calculateRectFromBounds(view, 0);
    }

    public static Rect calculateRectFromBounds(View view, int offsetY) {
        return new Rect(view.getLeft(), view.getTop() + offsetY, view.getRight(), view.getBottom() + offsetY);
    }

    public static Rect calculateOffsetRectFromBounds(View view, View offsetView) {
        int[] offsetViewAbsolutePosition = new int[2];
        offsetView.getLocationOnScreen(offsetViewAbsolutePosition);
        int offsetViewAbsoluteLeft = offsetViewAbsolutePosition[0];
        int offsetViewAbsoluteTop = offsetViewAbsolutePosition[1];
        int[] viewAbsolutePosition = new int[2];
        view.getLocationOnScreen(viewAbsolutePosition);
        int fromLeft = offsetViewAbsoluteLeft - viewAbsolutePosition[0];
        int fromTop = offsetViewAbsoluteTop - viewAbsolutePosition[1];
        return new Rect(fromLeft, fromTop, offsetView.getWidth() + fromLeft, offsetView.getHeight() + fromTop);
    }

    public static List<View> getChildren(View view) {
        List<View> children = new ArrayList<>();
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                children.add(viewGroup.getChildAt(i));
            }
        }
        return children;
    }

    public static PorterDuff.Mode parseTintMode(int value, PorterDuff.Mode defaultMode) {
        switch (value) {
            case 3:
                return PorterDuff.Mode.SRC_OVER;
            case 5:
                return PorterDuff.Mode.SRC_IN;
            case 9:
                return PorterDuff.Mode.SRC_ATOP;
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return defaultMode;
        }
    }

    public static boolean isLayoutRtl(View view) {
        return view.getLayoutDirection() == 1;
    }

    public static float dpToPx(Context context, int dp) {
        return TypedValue.applyDimension(1, (float) dp, context.getResources().getDisplayMetrics());
    }

    public static class RelativePadding {
        public int bottom;
        public int end;
        public int start;
        public int top;

        public RelativePadding(int start2, int top2, int end2, int bottom2) {
            this.start = start2;
            this.top = top2;
            this.end = end2;
            this.bottom = bottom2;
        }

        public RelativePadding(RelativePadding other) {
            this.start = other.start;
            this.top = other.top;
            this.end = other.end;
            this.bottom = other.bottom;
        }

        public void applyToView(View view) {
            view.setPaddingRelative(this.start, this.top, this.end, this.bottom);
        }
    }

    public static void doOnApplyWindowInsets(View view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        doOnApplyWindowInsets(view, attrs, defStyleAttr, defStyleRes, (OnApplyWindowInsetsListener) null);
    }

    public static void doOnApplyWindowInsets(View view, AttributeSet attrs, int defStyleAttr, int defStyleRes, final OnApplyWindowInsetsListener listener) {
        TypedArray a = view.getContext().obtainStyledAttributes(attrs, R.styleable.Insets, defStyleAttr, defStyleRes);
        final boolean paddingBottomSystemWindowInsets = a.getBoolean(R.styleable.Insets_paddingBottomSystemWindowInsets, false);
        final boolean paddingLeftSystemWindowInsets = a.getBoolean(R.styleable.Insets_paddingLeftSystemWindowInsets, false);
        final boolean paddingRightSystemWindowInsets = a.getBoolean(R.styleable.Insets_paddingRightSystemWindowInsets, false);
        a.recycle();
        doOnApplyWindowInsets(view, new OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets, RelativePadding initialPadding) {
                if (paddingBottomSystemWindowInsets) {
                    initialPadding.bottom += insets.getSystemWindowInsetBottom();
                }
                boolean isRtl = ViewUtils.isLayoutRtl(view);
                if (paddingLeftSystemWindowInsets) {
                    if (isRtl) {
                        initialPadding.end += insets.getSystemWindowInsetLeft();
                    } else {
                        initialPadding.start += insets.getSystemWindowInsetLeft();
                    }
                }
                if (paddingRightSystemWindowInsets) {
                    if (isRtl) {
                        initialPadding.start += insets.getSystemWindowInsetRight();
                    } else {
                        initialPadding.end += insets.getSystemWindowInsetRight();
                    }
                }
                initialPadding.applyToView(view);
                if (listener != null) {
                    return listener.onApplyWindowInsets(view, insets, initialPadding);
                }
                return insets;
            }
        });
    }

    public static void doOnApplyWindowInsets(View view, final OnApplyWindowInsetsListener listener) {
        final RelativePadding initialPadding = new RelativePadding(view.getPaddingStart(), view.getPaddingTop(), view.getPaddingEnd(), view.getPaddingBottom());
        ViewCompat.setOnApplyWindowInsetsListener(view, new androidx.core.view.OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets) {
                return OnApplyWindowInsetsListener.this.onApplyWindowInsets(view, insets, new RelativePadding(initialPadding));
            }
        });
        requestApplyInsetsWhenAttached(view);
    }

    public static void requestApplyInsetsWhenAttached(View view) {
        if (view.isAttachedToWindow()) {
            view.requestApplyInsets();
        } else {
            view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                public void onViewAttachedToWindow(View v) {
                    v.removeOnAttachStateChangeListener(this);
                    v.requestApplyInsets();
                }

                public void onViewDetachedFromWindow(View v) {
                }
            });
        }
    }

    public static float getParentAbsoluteElevation(View view) {
        float absoluteElevation = 0.0f;
        for (ViewParent viewParent = view.getParent(); viewParent instanceof View; viewParent = viewParent.getParent()) {
            absoluteElevation += ((View) viewParent).getElevation();
        }
        return absoluteElevation;
    }

    @Deprecated
    public static ViewOverlayImpl getOverlay(final View view) {
        if (view == null) {
            return null;
        }
        return new ViewOverlayImpl() {
            public void add(Drawable drawable) {
                view.getOverlay().add(drawable);
            }

            public void remove(Drawable drawable) {
                view.getOverlay().remove(drawable);
            }
        };
    }

    public static ViewGroup getContentView(View view) {
        if (view == null) {
            return null;
        }
        View rootView = view.getRootView();
        ViewGroup contentView = (ViewGroup) rootView.findViewById(16908290);
        if (contentView != null) {
            return contentView;
        }
        if (rootView == view || !(rootView instanceof ViewGroup)) {
            return null;
        }
        return (ViewGroup) rootView;
    }

    @Deprecated
    public static ViewOverlayImpl getContentViewOverlay(View view) {
        return getOverlay(getContentView(view));
    }

    public static void addOnGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener victim) {
        if (view != null) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(victim);
        }
    }

    public static void removeOnGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener victim) {
        if (view != null) {
            removeOnGlobalLayoutListener(view.getViewTreeObserver(), victim);
        }
    }

    public static void removeOnGlobalLayoutListener(ViewTreeObserver viewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener victim) {
        viewTreeObserver.removeOnGlobalLayoutListener(victim);
    }

    public static Integer getBackgroundColor(View view) {
        ColorStateList backgroundColorStateList = DrawableUtils.getColorStateListOrNull(view.getBackground());
        if (backgroundColorStateList != null) {
            return Integer.valueOf(backgroundColorStateList.getDefaultColor());
        }
        return null;
    }
}
