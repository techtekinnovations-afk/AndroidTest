package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.LinearLayoutCompat;

public class ActionMenuView extends LinearLayoutCompat implements MenuBuilder.ItemInvoker, MenuView {
    static final int GENERATED_ITEM_PADDING = 4;
    static final int MIN_CELL_SIZE = 56;
    private static final String TAG = "ActionMenuView";
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    private boolean mFormatItems;
    private int mFormatItemsWidth;
    private int mGeneratedItemPadding;
    private MenuBuilder mMenu;
    MenuBuilder.Callback mMenuBuilderCallback;
    private int mMinCellSize;
    OnMenuItemClickListener mOnMenuItemClickListener;
    private Context mPopupContext;
    private int mPopupTheme;
    private ActionMenuPresenter mPresenter;
    private boolean mReserveOverflow;

    public interface ActionMenuChildView {
        boolean needsDividerAfter();

        boolean needsDividerBefore();
    }

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public ActionMenuView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ActionMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBaselineAligned(false);
        float density = context.getResources().getDisplayMetrics().density;
        this.mMinCellSize = (int) (56.0f * density);
        this.mGeneratedItemPadding = (int) (4.0f * density);
        this.mPopupContext = context;
        this.mPopupTheme = 0;
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

    public void setPresenter(ActionMenuPresenter presenter) {
        this.mPresenter = presenter;
        this.mPresenter.setMenuView(this);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mPresenter != null) {
            this.mPresenter.updateMenuView(false);
            if (this.mPresenter.isOverflowMenuShowing()) {
                this.mPresenter.hideOverflowMenu();
                this.mPresenter.showOverflowMenu();
            }
        }
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        this.mOnMenuItemClickListener = listener;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        boolean wasFormatted = this.mFormatItems;
        this.mFormatItems = View.MeasureSpec.getMode(widthMeasureSpec) == 1073741824;
        if (wasFormatted != this.mFormatItems) {
            this.mFormatItemsWidth = 0;
        }
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        if (!(!this.mFormatItems || this.mMenu == null || widthSize == this.mFormatItemsWidth)) {
            this.mFormatItemsWidth = widthSize;
            this.mMenu.onItemsChanged(true);
        }
        int childCount = getChildCount();
        if (!this.mFormatItems || childCount <= 0) {
            for (int i = 0; i < childCount; i++) {
                LayoutParams lp = (LayoutParams) getChildAt(i).getLayoutParams();
                lp.rightMargin = 0;
                lp.leftMargin = 0;
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        onMeasureExactFormat(widthMeasureSpec, heightMeasureSpec);
    }

    /* JADX WARNING: Removed duplicated region for block: B:129:0x0296  */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x02c6  */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x02c8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onMeasureExactFormat(int r40, int r41) {
        /*
            r39 = this;
            r0 = r39
            int r1 = android.view.View.MeasureSpec.getMode(r41)
            int r2 = android.view.View.MeasureSpec.getSize(r40)
            int r3 = android.view.View.MeasureSpec.getSize(r41)
            int r4 = r0.getPaddingLeft()
            int r5 = r0.getPaddingRight()
            int r4 = r4 + r5
            int r5 = r0.getPaddingTop()
            int r6 = r0.getPaddingBottom()
            int r5 = r5 + r6
            r6 = -2
            r7 = r41
            int r6 = getChildMeasureSpec(r7, r5, r6)
            int r2 = r2 - r4
            int r8 = r0.mMinCellSize
            int r8 = r2 / r8
            int r9 = r0.mMinCellSize
            int r9 = r2 % r9
            r10 = 0
            if (r8 != 0) goto L_0x0037
            r0.setMeasuredDimension(r2, r10)
            return
        L_0x0037:
            int r11 = r0.mMinCellSize
            int r12 = r9 / r8
            int r11 = r11 + r12
            r12 = r8
            r13 = 0
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            int r10 = r0.getChildCount()
            r21 = 0
            r38 = r16
            r16 = r3
            r3 = r38
            r38 = r21
            r21 = r4
            r4 = r38
        L_0x0058:
            if (r4 >= r10) goto L_0x00ef
            android.view.View r7 = r0.getChildAt(r4)
            r23 = r4
            int r4 = r7.getVisibility()
            r24 = r8
            r8 = 8
            if (r4 != r8) goto L_0x006c
            goto L_0x00e7
        L_0x006c:
            boolean r4 = r7 instanceof androidx.appcompat.view.menu.ActionMenuItemView
            int r3 = r3 + 1
            if (r4 == 0) goto L_0x007f
            int r8 = r0.mGeneratedItemPadding
            r25 = r3
            int r3 = r0.mGeneratedItemPadding
            r26 = r4
            r4 = 0
            r7.setPadding(r8, r4, r3, r4)
            goto L_0x0084
        L_0x007f:
            r25 = r3
            r26 = r4
            r4 = 0
        L_0x0084:
            android.view.ViewGroup$LayoutParams r3 = r7.getLayoutParams()
            androidx.appcompat.widget.ActionMenuView$LayoutParams r3 = (androidx.appcompat.widget.ActionMenuView.LayoutParams) r3
            r3.expanded = r4
            r3.extraPixels = r4
            r3.cellsUsed = r4
            r3.expandable = r4
            r3.leftMargin = r4
            r3.rightMargin = r4
            if (r26 == 0) goto L_0x00a3
            r4 = r7
            androidx.appcompat.view.menu.ActionMenuItemView r4 = (androidx.appcompat.view.menu.ActionMenuItemView) r4
            boolean r4 = r4.hasText()
            if (r4 == 0) goto L_0x00a3
            r4 = 1
            goto L_0x00a4
        L_0x00a3:
            r4 = 0
        L_0x00a4:
            r3.preventEdgeOffset = r4
            boolean r4 = r3.isOverflowButton
            if (r4 == 0) goto L_0x00ac
            r4 = 1
            goto L_0x00ad
        L_0x00ac:
            r4 = r12
        L_0x00ad:
            int r8 = measureChildForCells(r7, r11, r4, r6, r5)
            int r14 = java.lang.Math.max(r14, r8)
            r27 = r4
            boolean r4 = r3.expandable
            if (r4 == 0) goto L_0x00bd
            int r15 = r15 + 1
        L_0x00bd:
            boolean r4 = r3.isOverflowButton
            if (r4 == 0) goto L_0x00c3
            r17 = 1
        L_0x00c3:
            int r12 = r12 - r8
            int r4 = r7.getMeasuredHeight()
            int r4 = java.lang.Math.max(r13, r4)
            r13 = 1
            if (r8 != r13) goto L_0x00df
            int r13 = r13 << r23
            r22 = r3
            r28 = r4
            long r3 = (long) r13
            long r3 = r18 | r3
            r18 = r3
            r3 = r25
            r13 = r28
            goto L_0x00e7
        L_0x00df:
            r22 = r3
            r28 = r4
            r3 = r25
            r13 = r28
        L_0x00e7:
            int r4 = r23 + 1
            r7 = r41
            r8 = r24
            goto L_0x0058
        L_0x00ef:
            r23 = r4
            r24 = r8
            r4 = 2
            if (r17 == 0) goto L_0x00fa
            if (r3 != r4) goto L_0x00fa
            r7 = 1
            goto L_0x00fb
        L_0x00fa:
            r7 = 0
        L_0x00fb:
            r8 = 0
        L_0x00fc:
            r25 = 1
            r27 = 0
            if (r15 <= 0) goto L_0x01bb
            if (r12 <= 0) goto L_0x01bb
            r23 = 2147483647(0x7fffffff, float:NaN)
            r29 = 0
            r31 = 0
            r32 = 0
            r33 = r4
            r4 = r23
            r23 = r5
            r5 = r31
            r31 = r7
            r7 = r32
        L_0x0119:
            if (r7 >= r10) goto L_0x0149
            android.view.View r32 = r0.getChildAt(r7)
            android.view.ViewGroup$LayoutParams r34 = r32.getLayoutParams()
            r35 = r7
            r7 = r34
            androidx.appcompat.widget.ActionMenuView$LayoutParams r7 = (androidx.appcompat.widget.ActionMenuView.LayoutParams) r7
            r34 = r8
            boolean r8 = r7.expandable
            if (r8 != 0) goto L_0x0130
            goto L_0x0144
        L_0x0130:
            int r8 = r7.cellsUsed
            if (r8 >= r4) goto L_0x013a
            int r4 = r7.cellsUsed
            long r29 = r25 << r35
            r5 = 1
            goto L_0x0144
        L_0x013a:
            int r8 = r7.cellsUsed
            if (r8 != r4) goto L_0x0144
            long r36 = r25 << r35
            long r29 = r29 | r36
            int r5 = r5 + 1
        L_0x0144:
            int r7 = r35 + 1
            r8 = r34
            goto L_0x0119
        L_0x0149:
            r35 = r7
            r34 = r8
            long r18 = r18 | r29
            if (r5 <= r12) goto L_0x0157
            r35 = r1
            r36 = r2
            goto L_0x01c7
        L_0x0157:
            int r4 = r4 + 1
            r7 = 0
        L_0x015a:
            if (r7 >= r10) goto L_0x01aa
            android.view.View r8 = r0.getChildAt(r7)
            android.view.ViewGroup$LayoutParams r25 = r8.getLayoutParams()
            r32 = r5
            r5 = r25
            androidx.appcompat.widget.ActionMenuView$LayoutParams r5 = (androidx.appcompat.widget.ActionMenuView.LayoutParams) r5
            r25 = r7
            r22 = 1
            int r7 = r22 << r25
            r35 = r1
            r36 = r2
            long r1 = (long) r7
            long r1 = r29 & r1
            int r1 = (r1 > r27 ? 1 : (r1 == r27 ? 0 : -1))
            if (r1 != 0) goto L_0x0185
            int r1 = r5.cellsUsed
            if (r1 != r4) goto L_0x01a1
            int r1 = r22 << r25
            long r1 = (long) r1
            long r18 = r18 | r1
            goto L_0x01a1
        L_0x0185:
            if (r31 == 0) goto L_0x0197
            boolean r1 = r5.preventEdgeOffset
            if (r1 == 0) goto L_0x0197
            r1 = 1
            if (r12 != r1) goto L_0x0197
            int r1 = r0.mGeneratedItemPadding
            int r1 = r1 + r11
            int r2 = r0.mGeneratedItemPadding
            r7 = 0
            r8.setPadding(r1, r7, r2, r7)
        L_0x0197:
            int r1 = r5.cellsUsed
            r2 = 1
            int r1 = r1 + r2
            r5.cellsUsed = r1
            r5.expanded = r2
            int r12 = r12 + -1
        L_0x01a1:
            int r7 = r25 + 1
            r5 = r32
            r1 = r35
            r2 = r36
            goto L_0x015a
        L_0x01aa:
            r35 = r1
            r36 = r2
            r32 = r5
            r25 = r7
            r8 = 1
            r5 = r23
            r7 = r31
            r4 = r33
            goto L_0x00fc
        L_0x01bb:
            r35 = r1
            r36 = r2
            r33 = r4
            r23 = r5
            r31 = r7
            r34 = r8
        L_0x01c7:
            if (r17 != 0) goto L_0x01ce
            r1 = 1
            if (r3 != r1) goto L_0x01ce
            r1 = 1
            goto L_0x01cf
        L_0x01ce:
            r1 = 0
        L_0x01cf:
            if (r12 <= 0) goto L_0x0290
            int r2 = (r18 > r27 ? 1 : (r18 == r27 ? 0 : -1))
            if (r2 == 0) goto L_0x0290
            int r2 = r3 + -1
            if (r12 < r2) goto L_0x01e3
            if (r1 != 0) goto L_0x01e3
            r2 = 1
            if (r14 <= r2) goto L_0x01df
            goto L_0x01e3
        L_0x01df:
            r20 = r1
            goto L_0x0292
        L_0x01e3:
            int r2 = java.lang.Long.bitCount(r18)
            float r2 = (float) r2
            if (r1 != 0) goto L_0x0223
            long r4 = r18 & r25
            int r4 = (r4 > r27 ? 1 : (r4 == r27 ? 0 : -1))
            r5 = 1056964608(0x3f000000, float:0.5)
            if (r4 == 0) goto L_0x0203
            r4 = 0
            android.view.View r7 = r0.getChildAt(r4)
            android.view.ViewGroup$LayoutParams r7 = r7.getLayoutParams()
            androidx.appcompat.widget.ActionMenuView$LayoutParams r7 = (androidx.appcompat.widget.ActionMenuView.LayoutParams) r7
            boolean r8 = r7.preventEdgeOffset
            if (r8 != 0) goto L_0x0204
            float r2 = r2 - r5
            goto L_0x0204
        L_0x0203:
            r4 = 0
        L_0x0204:
            int r7 = r10 + -1
            r22 = 1
            int r7 = r22 << r7
            long r7 = (long) r7
            long r7 = r18 & r7
            int r7 = (r7 > r27 ? 1 : (r7 == r27 ? 0 : -1))
            if (r7 == 0) goto L_0x0224
            int r7 = r10 + -1
            android.view.View r7 = r0.getChildAt(r7)
            android.view.ViewGroup$LayoutParams r7 = r7.getLayoutParams()
            androidx.appcompat.widget.ActionMenuView$LayoutParams r7 = (androidx.appcompat.widget.ActionMenuView.LayoutParams) r7
            boolean r8 = r7.preventEdgeOffset
            if (r8 != 0) goto L_0x0224
            float r2 = r2 - r5
            goto L_0x0224
        L_0x0223:
            r4 = 0
        L_0x0224:
            r5 = 0
            int r5 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r5 <= 0) goto L_0x022f
            int r4 = r12 * r11
            float r4 = (float) r4
            float r4 = r4 / r2
            int r4 = (int) r4
            goto L_0x0230
        L_0x022f:
        L_0x0230:
            r5 = 0
            r8 = r34
        L_0x0233:
            if (r5 >= r10) goto L_0x028a
            r22 = 1
            int r7 = r22 << r5
            r20 = r1
            r25 = r2
            long r1 = (long) r7
            long r1 = r18 & r1
            int r1 = (r1 > r27 ? 1 : (r1 == r27 ? 0 : -1))
            if (r1 != 0) goto L_0x0245
            goto L_0x0283
        L_0x0245:
            android.view.View r1 = r0.getChildAt(r5)
            android.view.ViewGroup$LayoutParams r2 = r1.getLayoutParams()
            androidx.appcompat.widget.ActionMenuView$LayoutParams r2 = (androidx.appcompat.widget.ActionMenuView.LayoutParams) r2
            boolean r7 = r1 instanceof androidx.appcompat.view.menu.ActionMenuItemView
            if (r7 == 0) goto L_0x0265
            r2.extraPixels = r4
            r7 = 1
            r2.expanded = r7
            if (r5 != 0) goto L_0x0263
            boolean r7 = r2.preventEdgeOffset
            if (r7 != 0) goto L_0x0263
            int r7 = -r4
            int r7 = r7 / 2
            r2.leftMargin = r7
        L_0x0263:
            r8 = 1
            goto L_0x0283
        L_0x0265:
            boolean r7 = r2.isOverflowButton
            if (r7 == 0) goto L_0x0275
            r2.extraPixels = r4
            r7 = 1
            r2.expanded = r7
            int r7 = -r4
            int r7 = r7 / 2
            r2.rightMargin = r7
            r8 = 1
            goto L_0x0283
        L_0x0275:
            if (r5 == 0) goto L_0x027b
            int r7 = r4 / 2
            r2.leftMargin = r7
        L_0x027b:
            int r7 = r10 + -1
            if (r5 == r7) goto L_0x0283
            int r7 = r4 / 2
            r2.rightMargin = r7
        L_0x0283:
            int r5 = r5 + 1
            r1 = r20
            r2 = r25
            goto L_0x0233
        L_0x028a:
            r20 = r1
            r25 = r2
            r12 = 0
            goto L_0x0294
        L_0x0290:
            r20 = r1
        L_0x0292:
            r8 = r34
        L_0x0294:
            if (r8 == 0) goto L_0x02c0
            r2 = 0
        L_0x0297:
            if (r2 >= r10) goto L_0x02be
            android.view.View r4 = r0.getChildAt(r2)
            android.view.ViewGroup$LayoutParams r5 = r4.getLayoutParams()
            androidx.appcompat.widget.ActionMenuView$LayoutParams r5 = (androidx.appcompat.widget.ActionMenuView.LayoutParams) r5
            boolean r7 = r5.expanded
            if (r7 != 0) goto L_0x02aa
            r25 = r2
            goto L_0x02bb
        L_0x02aa:
            int r7 = r5.cellsUsed
            int r7 = r7 * r11
            int r1 = r5.extraPixels
            int r7 = r7 + r1
            r25 = r2
            r1 = 1073741824(0x40000000, float:2.0)
            int r2 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r1)
            r4.measure(r2, r6)
        L_0x02bb:
            int r2 = r25 + 1
            goto L_0x0297
        L_0x02be:
            r25 = r2
        L_0x02c0:
            r1 = r35
            r2 = 1073741824(0x40000000, float:2.0)
            if (r1 == r2) goto L_0x02c8
            r2 = r13
            goto L_0x02ca
        L_0x02c8:
            r2 = r16
        L_0x02ca:
            r4 = r36
            r0.setMeasuredDimension(r4, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActionMenuView.onMeasureExactFormat(int, int):void");
    }

    static int measureChildForCells(View child, int cellSize, int cellsRemaining, int parentHeightMeasureSpec, int parentHeightPadding) {
        int i = cellsRemaining;
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int childHeightSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(parentHeightMeasureSpec) - parentHeightPadding, View.MeasureSpec.getMode(parentHeightMeasureSpec));
        ActionMenuItemView itemView = child instanceof ActionMenuItemView ? (ActionMenuItemView) child : null;
        boolean expandable = false;
        boolean hasText = itemView != null && itemView.hasText();
        int cellsUsed = 0;
        if (i > 0 && (!hasText || i >= 2)) {
            child.measure(View.MeasureSpec.makeMeasureSpec(cellSize * i, Integer.MIN_VALUE), childHeightSpec);
            int measuredWidth = child.getMeasuredWidth();
            cellsUsed = measuredWidth / cellSize;
            if (measuredWidth % cellSize != 0) {
                cellsUsed++;
            }
            if (hasText && cellsUsed < 2) {
                cellsUsed = 2;
            }
        }
        if (!lp.isOverflowButton && hasText) {
            expandable = true;
        }
        lp.expandable = expandable;
        lp.cellsUsed = cellsUsed;
        child.measure(View.MeasureSpec.makeMeasureSpec(cellsUsed * cellSize, 1073741824), childHeightSpec);
        return cellsUsed;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int i;
        int dividerWidth;
        int spacerSize;
        int midVertical;
        int r;
        int l;
        ActionMenuView actionMenuView = this;
        if (!actionMenuView.mFormatItems) {
            super.onLayout(changed, left, top, right, bottom);
            return;
        }
        int childCount = actionMenuView.getChildCount();
        int midVertical2 = (bottom - top) / 2;
        int dividerWidth2 = actionMenuView.getDividerWidth();
        int nonOverflowCount = 0;
        int widthRemaining = ((right - left) - actionMenuView.getPaddingRight()) - actionMenuView.getPaddingLeft();
        boolean hasOverflow = false;
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(actionMenuView);
        int i2 = 0;
        while (true) {
            i = 8;
            if (i2 >= childCount) {
                break;
            }
            View v = actionMenuView.getChildAt(i2);
            if (v.getVisibility() == 8) {
                midVertical = midVertical2;
            } else {
                LayoutParams p = (LayoutParams) v.getLayoutParams();
                if (p.isOverflowButton) {
                    int overflowWidth = v.getMeasuredWidth();
                    if (actionMenuView.hasSupportDividerBeforeChildAt(i2)) {
                        overflowWidth += dividerWidth2;
                    }
                    int height = v.getMeasuredHeight();
                    if (isLayoutRtl) {
                        l = actionMenuView.getPaddingLeft() + p.leftMargin;
                        r = l + overflowWidth;
                    } else {
                        r = (actionMenuView.getWidth() - actionMenuView.getPaddingRight()) - p.rightMargin;
                        l = r - overflowWidth;
                    }
                    int t = midVertical2 - (height / 2);
                    midVertical = midVertical2;
                    v.layout(l, t, r, t + height);
                    widthRemaining -= overflowWidth;
                    hasOverflow = true;
                } else {
                    midVertical = midVertical2;
                    widthRemaining -= (v.getMeasuredWidth() + p.leftMargin) + p.rightMargin;
                    actionMenuView.hasSupportDividerBeforeChildAt(i2);
                    nonOverflowCount++;
                }
            }
            i2++;
            midVertical2 = midVertical;
        }
        int midVertical3 = midVertical2;
        if (childCount != 1 || hasOverflow) {
            int spacerCount = nonOverflowCount - (!hasOverflow);
            int spacerSize2 = Math.max(0, spacerCount > 0 ? widthRemaining / spacerCount : 0);
            if (isLayoutRtl) {
                int startRight = actionMenuView.getWidth() - actionMenuView.getPaddingRight();
                int i3 = 0;
                while (i3 < childCount) {
                    View v2 = actionMenuView.getChildAt(i3);
                    LayoutParams lp = (LayoutParams) v2.getLayoutParams();
                    if (v2.getVisibility() == i) {
                        spacerSize = spacerSize2;
                        dividerWidth = dividerWidth2;
                    } else if (lp.isOverflowButton) {
                        spacerSize = spacerSize2;
                        dividerWidth = dividerWidth2;
                    } else {
                        int startRight2 = startRight - lp.rightMargin;
                        int width = v2.getMeasuredWidth();
                        int height2 = v2.getMeasuredHeight();
                        int t2 = midVertical3 - (height2 / 2);
                        spacerSize = spacerSize2;
                        dividerWidth = dividerWidth2;
                        v2.layout(startRight2 - width, t2, startRight2, t2 + height2);
                        startRight = startRight2 - ((lp.leftMargin + width) + spacerSize);
                    }
                    i3++;
                    spacerSize2 = spacerSize;
                    dividerWidth2 = dividerWidth;
                    i = 8;
                }
                int i4 = dividerWidth2;
                return;
            }
            int spacerSize3 = spacerSize2;
            int i5 = dividerWidth2;
            int startLeft = actionMenuView.getPaddingLeft();
            int i6 = 0;
            while (i6 < childCount) {
                View v3 = actionMenuView.getChildAt(i6);
                LayoutParams lp2 = (LayoutParams) v3.getLayoutParams();
                if (v3.getVisibility() != 8 && !lp2.isOverflowButton) {
                    int startLeft2 = startLeft + lp2.leftMargin;
                    int width2 = v3.getMeasuredWidth();
                    int height3 = v3.getMeasuredHeight();
                    int t3 = midVertical3 - (height3 / 2);
                    v3.layout(startLeft2, t3, startLeft2 + width2, t3 + height3);
                    startLeft = startLeft2 + lp2.rightMargin + width2 + spacerSize3;
                }
                i6++;
                actionMenuView = this;
            }
            return;
        }
        View v4 = actionMenuView.getChildAt(0);
        int width3 = v4.getMeasuredWidth();
        int height4 = v4.getMeasuredHeight();
        int l2 = ((right - left) / 2) - (width3 / 2);
        int t4 = midVertical3 - (height4 / 2);
        v4.layout(l2, t4, l2 + width3, t4 + height4);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dismissPopupMenus();
    }

    public void setOverflowIcon(Drawable icon) {
        getMenu();
        this.mPresenter.setOverflowIcon(icon);
    }

    public Drawable getOverflowIcon() {
        getMenu();
        return this.mPresenter.getOverflowIcon();
    }

    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    public void setOverflowReserved(boolean reserveOverflow) {
        this.mReserveOverflow = reserveOverflow;
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        LayoutParams params = new LayoutParams(-2, -2);
        params.gravity = 16;
        return params;
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        LayoutParams result;
        if (p == null) {
            return generateDefaultLayoutParams();
        }
        if (p instanceof LayoutParams) {
            result = new LayoutParams((LayoutParams) p);
        } else {
            result = new LayoutParams(p);
        }
        if (result.gravity <= 0) {
            result.gravity = 16;
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public LayoutParams generateOverflowButtonLayoutParams() {
        LayoutParams result = generateDefaultLayoutParams();
        result.isOverflowButton = true;
        return result;
    }

    public boolean invokeItem(MenuItemImpl item) {
        return this.mMenu.performItemAction(item, 0);
    }

    public int getWindowAnimations() {
        return 0;
    }

    public void initialize(MenuBuilder menu) {
        this.mMenu = menu;
    }

    public Menu getMenu() {
        if (this.mMenu == null) {
            Context context = getContext();
            this.mMenu = new MenuBuilder(context);
            this.mMenu.setCallback(new MenuBuilderCallback());
            this.mPresenter = new ActionMenuPresenter(context);
            this.mPresenter.setReserveOverflow(true);
            this.mPresenter.setCallback(this.mActionMenuPresenterCallback != null ? this.mActionMenuPresenterCallback : new ActionMenuPresenterCallback());
            this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
            this.mPresenter.setMenuView(this);
        }
        return this.mMenu;
    }

    public void setMenuCallbacks(MenuPresenter.Callback pcb, MenuBuilder.Callback mcb) {
        this.mActionMenuPresenterCallback = pcb;
        this.mMenuBuilderCallback = mcb;
    }

    public MenuBuilder peekMenu() {
        return this.mMenu;
    }

    public boolean showOverflowMenu() {
        return this.mPresenter != null && this.mPresenter.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        return this.mPresenter != null && this.mPresenter.hideOverflowMenu();
    }

    public boolean isOverflowMenuShowing() {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowing();
    }

    public boolean isOverflowMenuShowPending() {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowPending();
    }

    public void dismissPopupMenus() {
        if (this.mPresenter != null) {
            this.mPresenter.dismissPopupMenus();
        }
    }

    /* access modifiers changed from: protected */
    public boolean hasSupportDividerBeforeChildAt(int childIndex) {
        if (childIndex == 0) {
            return false;
        }
        View childBefore = getChildAt(childIndex - 1);
        View child = getChildAt(childIndex);
        boolean result = false;
        if (childIndex < getChildCount() && (childBefore instanceof ActionMenuChildView)) {
            result = false | ((ActionMenuChildView) childBefore).needsDividerAfter();
        }
        if (childIndex <= 0 || !(child instanceof ActionMenuChildView)) {
            return result;
        }
        return result | ((ActionMenuChildView) child).needsDividerBefore();
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        return false;
    }

    public void setExpandedActionViewsExclusive(boolean exclusive) {
        this.mPresenter.setExpandedActionViewsExclusive(exclusive);
    }

    private class MenuBuilderCallback implements MenuBuilder.Callback {
        MenuBuilderCallback() {
        }

        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
            return ActionMenuView.this.mOnMenuItemClickListener != null && ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick(item);
        }

        public void onMenuModeChange(MenuBuilder menu) {
            if (ActionMenuView.this.mMenuBuilderCallback != null) {
                ActionMenuView.this.mMenuBuilderCallback.onMenuModeChange(menu);
            }
        }
    }

    private static class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        ActionMenuPresenterCallback() {
        }

        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        }

        public boolean onOpenSubMenu(MenuBuilder subMenu) {
            return false;
        }
    }

    public static class LayoutParams extends LinearLayoutCompat.LayoutParams {
        @ViewDebug.ExportedProperty
        public int cellsUsed;
        @ViewDebug.ExportedProperty
        public boolean expandable;
        boolean expanded;
        @ViewDebug.ExportedProperty
        public int extraPixels;
        @ViewDebug.ExportedProperty
        public boolean isOverflowButton;
        @ViewDebug.ExportedProperty
        public boolean preventEdgeOffset;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(ViewGroup.LayoutParams other) {
            super(other);
        }

        public LayoutParams(LayoutParams other) {
            super((ViewGroup.LayoutParams) other);
            this.isOverflowButton = other.isOverflowButton;
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.isOverflowButton = false;
        }

        LayoutParams(int width, int height, boolean isOverflowButton2) {
            super(width, height);
            this.isOverflowButton = isOverflowButton2;
        }
    }
}
