package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.timepicker.ClockHandView;
import java.util.Arrays;

class ClockFaceView extends RadialViewGroup implements ClockHandView.OnRotateListener {
    private static final float EPSILON = 0.001f;
    private static final int INITIAL_CAPACITY = 12;
    private static final String VALUE_PLACEHOLDER = "";
    /* access modifiers changed from: private */
    public final int clockHandPadding;
    /* access modifiers changed from: private */
    public final ClockHandView clockHandView;
    private final int clockSize;
    private float currentHandRotation;
    private final int[] gradientColors;
    private final float[] gradientPositions;
    private final int minimumHeight;
    private final int minimumWidth;
    private final RectF scratch;
    private final Rect scratchLineBounds;
    private final ColorStateList textColor;
    /* access modifiers changed from: private */
    public final SparseArray<TextView> textViewPool;
    /* access modifiers changed from: private */
    public final Rect textViewRect;
    private final AccessibilityDelegateCompat valueAccessibilityDelegate;
    private String[] values;

    public ClockFaceView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ClockFaceView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.materialClockStyle);
    }

    public ClockFaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.textViewRect = new Rect();
        this.scratch = new RectF();
        this.scratchLineBounds = new Rect();
        this.textViewPool = new SparseArray<>();
        this.gradientPositions = new float[]{0.0f, 0.9f, 1.0f};
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ClockFaceView, defStyleAttr, R.style.Widget_MaterialComponents_TimePicker_Clock);
        Resources res = getResources();
        this.textColor = MaterialResources.getColorStateList(context, a, R.styleable.ClockFaceView_clockNumberTextColor);
        LayoutInflater.from(context).inflate(R.layout.material_clockface_view, this, true);
        this.clockHandView = (ClockHandView) findViewById(R.id.material_clock_hand);
        this.clockHandPadding = res.getDimensionPixelSize(R.dimen.material_clock_hand_padding);
        int clockHandTextColor = this.textColor.getColorForState(new int[]{16842913}, this.textColor.getDefaultColor());
        this.gradientColors = new int[]{clockHandTextColor, clockHandTextColor, this.textColor.getDefaultColor()};
        this.clockHandView.addOnRotateListener(this);
        int defaultBackgroundColor = AppCompatResources.getColorStateList(context, R.color.material_timepicker_clockface).getDefaultColor();
        ColorStateList backgroundColor = MaterialResources.getColorStateList(context, a, R.styleable.ClockFaceView_clockFaceBackgroundColor);
        setBackgroundColor(backgroundColor == null ? defaultBackgroundColor : backgroundColor.getDefaultColor());
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (!ClockFaceView.this.isShown()) {
                    return true;
                }
                ClockFaceView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                ClockFaceView.this.setRadius(((ClockFaceView.this.getHeight() / 2) - ClockFaceView.this.clockHandView.getSelectorRadius()) - ClockFaceView.this.clockHandPadding);
                return true;
            }
        });
        setFocusable(false);
        a.recycle();
        this.valueAccessibilityDelegate = new AccessibilityDelegateCompat() {
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                int index = ((Integer) host.getTag(R.id.material_value_index)).intValue();
                if (index > 0) {
                    info.setTraversalAfter((View) ClockFaceView.this.textViewPool.get(index - 1));
                }
                info.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, index, 1, false, host.isSelected()));
                info.setClickable(true);
                info.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
            }

            public boolean performAccessibilityAction(View host, int action, Bundle args) {
                if (action != 16) {
                    return super.performAccessibilityAction(host, action, args);
                }
                long time = SystemClock.uptimeMillis();
                host.getHitRect(ClockFaceView.this.textViewRect);
                float x = (float) ClockFaceView.this.textViewRect.centerX();
                float y = (float) ClockFaceView.this.textViewRect.centerY();
                ClockFaceView.this.clockHandView.onTouchEvent(MotionEvent.obtain(time, time, 0, x, y, 0));
                ClockFaceView.this.clockHandView.onTouchEvent(MotionEvent.obtain(time, time, 1, x, y, 0));
                return true;
            }
        };
        String[] initialValues = new String[12];
        Arrays.fill(initialValues, "");
        setValues(initialValues, 0);
        this.minimumHeight = res.getDimensionPixelSize(R.dimen.material_time_picker_minimum_screen_height);
        this.minimumWidth = res.getDimensionPixelSize(R.dimen.material_time_picker_minimum_screen_width);
        this.clockSize = res.getDimensionPixelSize(R.dimen.material_clock_size);
    }

    public void setValues(String[] values2, int contentDescription) {
        this.values = values2;
        updateTextViews(contentDescription);
    }

    /* JADX WARNING: type inference failed for: r5v8, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateTextViews(int r10) {
        /*
            r9 = this;
            r0 = 0
            android.content.Context r1 = r9.getContext()
            android.view.LayoutInflater r1 = android.view.LayoutInflater.from(r1)
            android.util.SparseArray<android.widget.TextView> r2 = r9.textViewPool
            int r2 = r2.size()
            r3 = 0
        L_0x0010:
            java.lang.String[] r4 = r9.values
            int r4 = r4.length
            int r4 = java.lang.Math.max(r4, r2)
            if (r3 >= r4) goto L_0x0085
            android.util.SparseArray<android.widget.TextView> r4 = r9.textViewPool
            java.lang.Object r4 = r4.get(r3)
            android.widget.TextView r4 = (android.widget.TextView) r4
            java.lang.String[] r5 = r9.values
            int r5 = r5.length
            if (r3 < r5) goto L_0x002f
            r9.removeView(r4)
            android.util.SparseArray<android.widget.TextView> r5 = r9.textViewPool
            r5.remove(r3)
            goto L_0x0082
        L_0x002f:
            if (r4 != 0) goto L_0x0043
            int r5 = com.google.android.material.R.layout.material_clockface_textview
            r6 = 0
            android.view.View r5 = r1.inflate(r5, r9, r6)
            r4 = r5
            android.widget.TextView r4 = (android.widget.TextView) r4
            android.util.SparseArray<android.widget.TextView> r5 = r9.textViewPool
            r5.put(r3, r4)
            r9.addView(r4)
        L_0x0043:
            java.lang.String[] r5 = r9.values
            r5 = r5[r3]
            r4.setText(r5)
            int r5 = com.google.android.material.R.id.material_value_index
            java.lang.Integer r6 = java.lang.Integer.valueOf(r3)
            r4.setTag(r5, r6)
            int r5 = r3 / 12
            r6 = 1
            int r5 = r5 + r6
            int r7 = com.google.android.material.R.id.material_clock_level
            java.lang.Integer r8 = java.lang.Integer.valueOf(r5)
            r4.setTag(r7, r8)
            if (r5 <= r6) goto L_0x0063
            r0 = 1
        L_0x0063:
            androidx.core.view.AccessibilityDelegateCompat r6 = r9.valueAccessibilityDelegate
            androidx.core.view.ViewCompat.setAccessibilityDelegate(r4, r6)
            android.content.res.ColorStateList r6 = r9.textColor
            r4.setTextColor(r6)
            if (r10 == 0) goto L_0x0082
            android.content.res.Resources r6 = r9.getResources()
            java.lang.String[] r7 = r9.values
            r7 = r7[r3]
            java.lang.Object[] r7 = new java.lang.Object[]{r7}
            java.lang.String r7 = r6.getString(r10, r7)
            r4.setContentDescription(r7)
        L_0x0082:
            int r3 = r3 + 1
            goto L_0x0010
        L_0x0085:
            com.google.android.material.timepicker.ClockHandView r3 = r9.clockHandView
            r3.setMultiLevel(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.timepicker.ClockFaceView.updateTextViews(int):void");
    }

    /* access modifiers changed from: protected */
    public void updateLayoutParams() {
        super.updateLayoutParams();
        for (int i = 0; i < this.textViewPool.size(); i++) {
            this.textViewPool.get(i).setVisibility(0);
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        AccessibilityNodeInfoCompat.wrap(info).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, this.values.length, false, 1));
    }

    public void setRadius(int radius) {
        if (radius != getRadius()) {
            super.setRadius(radius);
            this.clockHandView.setCircleRadius(getRadius());
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        findIntersectingTextView();
    }

    public void setHandRotation(float rotation) {
        this.clockHandView.setHandRotation(rotation);
        findIntersectingTextView();
    }

    private void findIntersectingTextView() {
        RectF selectorBox = this.clockHandView.getCurrentSelectorBox();
        TextView selected = getSelectedTextView(selectorBox);
        for (int i = 0; i < this.textViewPool.size(); i++) {
            TextView tv = this.textViewPool.get(i);
            if (tv != null) {
                tv.setSelected(tv == selected);
                tv.getPaint().setShader(getGradientForTextView(selectorBox, tv));
                tv.invalidate();
            }
        }
    }

    private TextView getSelectedTextView(RectF selectorBox) {
        float minArea = Float.MAX_VALUE;
        TextView selected = null;
        for (int i = 0; i < this.textViewPool.size(); i++) {
            TextView tv = this.textViewPool.get(i);
            if (tv != null) {
                tv.getHitRect(this.textViewRect);
                this.scratch.set(this.textViewRect);
                this.scratch.union(selectorBox);
                float area = this.scratch.width() * this.scratch.height();
                if (area < minArea) {
                    minArea = area;
                    selected = tv;
                }
            }
        }
        return selected;
    }

    private RadialGradient getGradientForTextView(RectF selectorBox, TextView tv) {
        tv.getHitRect(this.textViewRect);
        this.scratch.set(this.textViewRect);
        tv.getLineBounds(0, this.scratchLineBounds);
        this.scratch.inset((float) this.scratchLineBounds.left, (float) this.scratchLineBounds.top);
        if (!RectF.intersects(selectorBox, this.scratch)) {
            return null;
        }
        return new RadialGradient(selectorBox.centerX() - this.scratch.left, selectorBox.centerY() - this.scratch.top, 0.5f * selectorBox.width(), this.gradientColors, this.gradientPositions, Shader.TileMode.CLAMP);
    }

    public void onRotate(float rotation, boolean animating) {
        if (Math.abs(this.currentHandRotation - rotation) > EPSILON) {
            this.currentHandRotation = rotation;
            findIntersectingTextView();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int size = (int) (((float) this.clockSize) / max3(((float) this.minimumHeight) / ((float) displayMetrics.heightPixels), ((float) this.minimumWidth) / ((float) displayMetrics.widthPixels), 1.0f));
        int spec = View.MeasureSpec.makeMeasureSpec(size, 1073741824);
        setMeasuredDimension(size, size);
        super.onMeasure(spec, spec);
    }

    private static float max3(float a, float b, float c) {
        return Math.max(Math.max(a, b), c);
    }

    /* access modifiers changed from: package-private */
    public int getCurrentLevel() {
        return this.clockHandView.getCurrentLevel();
    }

    /* access modifiers changed from: package-private */
    public void setCurrentLevel(int level) {
        this.clockHandView.setCurrentLevel(level);
    }
}
