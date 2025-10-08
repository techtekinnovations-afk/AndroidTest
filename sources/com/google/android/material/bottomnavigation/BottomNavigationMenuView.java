package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.material.R;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarMenuView;
import java.util.ArrayList;
import java.util.List;

public class BottomNavigationMenuView extends NavigationBarMenuView {
    private final int activeItemMaxWidth;
    private final int activeItemMinWidth;
    private final int inactiveItemMaxWidth;
    private final int inactiveItemMinWidth;
    private boolean itemHorizontalTranslationEnabled;
    private final List<Integer> tempChildWidths = new ArrayList();

    public BottomNavigationMenuView(Context context) {
        super(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        params.gravity = 17;
        setLayoutParams(params);
        Resources res = getResources();
        this.inactiveItemMaxWidth = res.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_max_width);
        this.inactiveItemMinWidth = res.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_min_width);
        this.activeItemMaxWidth = res.getDimensionPixelSize(R.dimen.design_bottom_navigation_active_item_max_width);
        this.activeItemMinWidth = res.getDimensionPixelSize(R.dimen.design_bottom_navigation_active_item_min_width);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0107  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onMeasure(int r23, int r24) {
        /*
            r22 = this;
            r0 = r22
            int r1 = android.view.View.MeasureSpec.getSize(r23)
            int r2 = r0.getCurrentVisibleContentItemCount()
            int r3 = r0.getChildCount()
            java.util.List<java.lang.Integer> r4 = r0.tempChildWidths
            r4.clear()
            r4 = 0
            r5 = 0
            int r6 = android.view.View.MeasureSpec.getSize(r24)
            r7 = -2147483648(0xffffffff80000000, float:-0.0)
            int r8 = android.view.View.MeasureSpec.makeMeasureSpec(r6, r7)
            int r9 = r0.getItemIconGravity()
            r11 = 8
            r12 = 1
            if (r9 != 0) goto L_0x0144
            int r9 = r0.getLabelVisibilityMode()
            boolean r9 = r0.isShifting(r9, r2)
            if (r9 == 0) goto L_0x00ca
            boolean r9 = r0.isItemHorizontalTranslationEnabled()
            if (r9 == 0) goto L_0x00c5
            int r9 = r0.getSelectedItemPosition()
            android.view.View r9 = r0.getChildAt(r9)
            int r13 = r0.activeItemMinWidth
            int r14 = r9.getVisibility()
            if (r14 == r11) goto L_0x0059
            int r14 = r0.activeItemMaxWidth
            int r7 = android.view.View.MeasureSpec.makeMeasureSpec(r14, r7)
            r9.measure(r7, r8)
            int r7 = r9.getMeasuredWidth()
            int r13 = java.lang.Math.max(r13, r7)
        L_0x0059:
            int r7 = r9.getVisibility()
            if (r7 == r11) goto L_0x0061
            r7 = r12
            goto L_0x0062
        L_0x0061:
            r7 = 0
        L_0x0062:
            int r7 = r2 - r7
            int r14 = r0.inactiveItemMinWidth
            int r14 = r14 * r7
            int r14 = r1 - r14
            int r15 = r0.activeItemMaxWidth
            int r15 = java.lang.Math.min(r13, r15)
            int r15 = java.lang.Math.min(r14, r15)
            int r16 = r1 - r15
            if (r7 != 0) goto L_0x0078
            goto L_0x0079
        L_0x0078:
            r12 = r7
        L_0x0079:
            int r12 = r16 / r12
            int r10 = r0.inactiveItemMaxWidth
            int r10 = java.lang.Math.min(r12, r10)
            int r17 = r1 - r15
            int r18 = r10 * r7
            int r17 = r17 - r18
            r18 = 0
            r11 = r18
        L_0x008b:
            if (r11 >= r3) goto L_0x00c0
            r19 = 0
            android.view.View r20 = r0.getChildAt(r11)
            r21 = r2
            int r2 = r20.getVisibility()
            r20 = r4
            r4 = 8
            if (r2 == r4) goto L_0x00b0
            int r2 = r0.getSelectedItemPosition()
            if (r11 != r2) goto L_0x00a7
            r2 = r15
            goto L_0x00a8
        L_0x00a7:
            r2 = r10
        L_0x00a8:
            r19 = r2
            if (r17 <= 0) goto L_0x00b0
            int r19 = r19 + 1
            int r17 = r17 + -1
        L_0x00b0:
            java.util.List<java.lang.Integer> r2 = r0.tempChildWidths
            java.lang.Integer r4 = java.lang.Integer.valueOf(r19)
            r2.add(r4)
            int r11 = r11 + 1
            r4 = r20
            r2 = r21
            goto L_0x008b
        L_0x00c0:
            r21 = r2
            r20 = r4
            goto L_0x0102
        L_0x00c5:
            r21 = r2
            r20 = r4
            goto L_0x00ce
        L_0x00ca:
            r21 = r2
            r20 = r4
        L_0x00ce:
            if (r21 != 0) goto L_0x00d1
            goto L_0x00d3
        L_0x00d1:
            r12 = r21
        L_0x00d3:
            int r2 = r1 / r12
            int r4 = r0.activeItemMaxWidth
            int r4 = java.lang.Math.min(r2, r4)
            int r7 = r4 * r21
            int r7 = r1 - r7
            r9 = 0
        L_0x00e0:
            if (r9 >= r3) goto L_0x0102
            r10 = 0
            android.view.View r11 = r0.getChildAt(r9)
            int r11 = r11.getVisibility()
            r12 = 8
            if (r11 == r12) goto L_0x00f6
            r10 = r4
            if (r7 <= 0) goto L_0x00f6
            int r10 = r10 + 1
            int r7 = r7 + -1
        L_0x00f6:
            java.util.List<java.lang.Integer> r11 = r0.tempChildWidths
            java.lang.Integer r12 = java.lang.Integer.valueOf(r10)
            r11.add(r12)
            int r9 = r9 + 1
            goto L_0x00e0
        L_0x0102:
            r2 = 0
            r4 = r20
        L_0x0105:
            if (r2 >= r3) goto L_0x0143
            android.view.View r7 = r0.getChildAt(r2)
            int r9 = r7.getVisibility()
            r12 = 8
            if (r9 != r12) goto L_0x0114
            goto L_0x0140
        L_0x0114:
            java.util.List<java.lang.Integer> r9 = r0.tempChildWidths
            java.lang.Object r9 = r9.get(r2)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r9 = r9.intValue()
            r10 = 1073741824(0x40000000, float:2.0)
            int r9 = android.view.View.MeasureSpec.makeMeasureSpec(r9, r10)
            r7.measure(r9, r8)
            android.view.ViewGroup$LayoutParams r9 = r7.getLayoutParams()
            int r10 = r7.getMeasuredWidth()
            r9.width = r10
            int r10 = r7.getMeasuredWidth()
            int r4 = r4 + r10
            int r10 = r7.getMeasuredHeight()
            int r5 = java.lang.Math.max(r5, r10)
        L_0x0140:
            int r2 = r2 + 1
            goto L_0x0105
        L_0x0143:
            goto L_0x01a9
        L_0x0144:
            r21 = r2
            r20 = r4
            if (r21 != 0) goto L_0x014b
            goto L_0x014d
        L_0x014b:
            r12 = r21
        L_0x014d:
            int r2 = r12 + 3
            float r2 = (float) r2
            r4 = 1092616192(0x41200000, float:10.0)
            float r2 = r2 / r4
            r4 = 1063675494(0x3f666666, float:0.9)
            float r2 = java.lang.Math.min(r2, r4)
            float r4 = (float) r1
            float r2 = r2 * r4
            float r4 = (float) r12
            float r2 = r2 / r4
            int r2 = java.lang.Math.round(r2)
            float r4 = (float) r1
            float r9 = (float) r12
            float r4 = r4 / r9
            int r4 = java.lang.Math.round(r4)
            r9 = 0
        L_0x016a:
            if (r9 >= r3) goto L_0x01a7
            android.view.View r10 = r0.getChildAt(r9)
            int r11 = r10.getVisibility()
            r13 = 8
            if (r11 == r13) goto L_0x01a2
            int r11 = android.view.View.MeasureSpec.makeMeasureSpec(r4, r7)
            r10.measure(r11, r8)
            int r11 = r10.getMeasuredWidth()
            if (r11 >= r2) goto L_0x0191
            r11 = 1073741824(0x40000000, float:2.0)
            int r14 = android.view.View.MeasureSpec.makeMeasureSpec(r2, r11)
            r10.measure(r14, r8)
            goto L_0x0193
        L_0x0191:
            r11 = 1073741824(0x40000000, float:2.0)
        L_0x0193:
            int r14 = r10.getMeasuredWidth()
            int r20 = r20 + r14
            int r14 = r10.getMeasuredHeight()
            int r5 = java.lang.Math.max(r5, r14)
            goto L_0x01a4
        L_0x01a2:
            r11 = 1073741824(0x40000000, float:2.0)
        L_0x01a4:
            int r9 = r9 + 1
            goto L_0x016a
        L_0x01a7:
            r4 = r20
        L_0x01a9:
            int r2 = r0.getSuggestedMinimumHeight()
            int r2 = java.lang.Math.max(r5, r2)
            r0.setMeasuredDimension(r4, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.bottomnavigation.BottomNavigationMenuView.onMeasure(int, int):void");
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int count = getChildCount();
        int width = right - left;
        int height = bottom - top;
        int used = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                if (getLayoutDirection() == 1) {
                    child.layout((width - used) - child.getMeasuredWidth(), 0, width - used, height);
                } else {
                    child.layout(used, 0, child.getMeasuredWidth() + used, height);
                }
                used += child.getMeasuredWidth();
            }
        }
    }

    public void setItemHorizontalTranslationEnabled(boolean itemHorizontalTranslationEnabled2) {
        this.itemHorizontalTranslationEnabled = itemHorizontalTranslationEnabled2;
    }

    public boolean isItemHorizontalTranslationEnabled() {
        return this.itemHorizontalTranslationEnabled;
    }

    /* access modifiers changed from: protected */
    public NavigationBarItemView createNavigationBarItemView(Context context) {
        return new BottomNavigationItemView(context);
    }
}
