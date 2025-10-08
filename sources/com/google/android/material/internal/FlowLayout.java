package com.google.android.material.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.R;

public class FlowLayout extends ViewGroup {
    private int itemSpacing;
    private int lineSpacing;
    private int rowCount;
    private boolean singleLine;

    public FlowLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.singleLine = false;
        loadFromAttributes(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.singleLine = false;
        loadFromAttributes(context, attrs);
    }

    private void loadFromAttributes(Context context, AttributeSet attrs) {
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FlowLayout, 0, 0);
        this.lineSpacing = array.getDimensionPixelSize(R.styleable.FlowLayout_lineSpacing, 0);
        this.itemSpacing = array.getDimensionPixelSize(R.styleable.FlowLayout_horizontalItemSpacing, 0);
        array.recycle();
    }

    /* access modifiers changed from: protected */
    public int getLineSpacing() {
        return this.lineSpacing;
    }

    /* access modifiers changed from: protected */
    public void setLineSpacing(int lineSpacing2) {
        this.lineSpacing = lineSpacing2;
    }

    /* access modifiers changed from: protected */
    public int getItemSpacing() {
        return this.itemSpacing;
    }

    /* access modifiers changed from: protected */
    public void setItemSpacing(int itemSpacing2) {
        this.itemSpacing = itemSpacing2;
    }

    public boolean isSingleLine() {
        return this.singleLine;
    }

    public void setSingleLine(boolean singleLine2) {
        this.singleLine = singleLine2;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxWidth;
        int maxWidth2;
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int height = View.MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == Integer.MIN_VALUE || widthMode == 1073741824) {
            maxWidth = width;
        } else {
            maxWidth = Integer.MAX_VALUE;
        }
        int childBottom = getPaddingLeft();
        int childTop = getPaddingTop();
        int childBottom2 = childTop;
        int i = childBottom;
        int maxChildRight = 0;
        int maxRight = maxWidth - getPaddingRight();
        int i2 = 0;
        while (i2 < getChildCount()) {
            View child = getChildAt(i2);
            if (child.getVisibility() == 8) {
                int i3 = widthMeasureSpec;
                int i4 = heightMeasureSpec;
                maxWidth2 = maxWidth;
            } else {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                maxWidth2 = maxWidth;
                ViewGroup.LayoutParams lp = child.getLayoutParams();
                int leftMargin = 0;
                int rightMargin = 0;
                int childLeft = childBottom;
                if ((lp instanceof ViewGroup.MarginLayoutParams) != 0) {
                    ViewGroup.MarginLayoutParams marginLp = (ViewGroup.MarginLayoutParams) lp;
                    ViewGroup.LayoutParams layoutParams = lp;
                    leftMargin = 0 + marginLp.leftMargin;
                    rightMargin = 0 + marginLp.rightMargin;
                }
                if (childLeft + leftMargin + child.getMeasuredWidth() > maxRight && !isSingleLine()) {
                    int childLeft2 = getPaddingLeft();
                    childTop = childBottom2 + this.lineSpacing;
                    childLeft = childLeft2;
                }
                int childRight = childLeft + leftMargin + child.getMeasuredWidth();
                int childBottom3 = child.getMeasuredHeight() + childTop;
                if (childRight > maxChildRight) {
                    maxChildRight = childRight;
                }
                int childLeft3 = childLeft + leftMargin + rightMargin + child.getMeasuredWidth() + this.itemSpacing;
                if (i2 == getChildCount() - 1) {
                    maxChildRight += rightMargin;
                    int i5 = childRight;
                    childBottom2 = childBottom3;
                    childBottom = childLeft3;
                } else {
                    int i6 = childRight;
                    childBottom2 = childBottom3;
                    childBottom = childLeft3;
                }
            }
            i2++;
            maxWidth = maxWidth2;
        }
        int i7 = childBottom;
        setMeasuredDimension(getMeasuredDimension(width, widthMode, maxChildRight + getPaddingRight()), getMeasuredDimension(height, heightMode, childBottom2 + getPaddingBottom()));
    }

    private static int getMeasuredDimension(int size, int mode, int childrenEdge) {
        switch (mode) {
            case Integer.MIN_VALUE:
                return Math.min(childrenEdge, size);
            case 1073741824:
                return size;
            default:
                return childrenEdge;
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean sizeChanged, int left, int top, int right, int bottom) {
        int i;
        boolean isRtl = false;
        if (getChildCount() == 0) {
            this.rowCount = 0;
            return;
        }
        int i2 = 1;
        this.rowCount = 1;
        if (getLayoutDirection() == 1) {
            isRtl = true;
        }
        int paddingStart = isRtl ? getPaddingRight() : getPaddingLeft();
        int paddingEnd = isRtl ? getPaddingLeft() : getPaddingRight();
        int childStart = paddingStart;
        int childTop = getPaddingTop();
        int childBottom = childTop;
        int i3 = 0;
        while (i3 < getChildCount()) {
            View child = getChildAt(i3);
            if (child.getVisibility() == 8) {
                child.setTag(R.id.row_index_key, -1);
                i = i2;
            } else {
                ViewGroup.LayoutParams lp = child.getLayoutParams();
                int startMargin = 0;
                int endMargin = 0;
                if (lp instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLp = (ViewGroup.MarginLayoutParams) lp;
                    startMargin = marginLp.getMarginStart();
                    endMargin = marginLp.getMarginEnd();
                }
                int childEnd = childStart + startMargin + child.getMeasuredWidth();
                int maxChildEnd = (right - left) - paddingEnd;
                if (!this.singleLine && childEnd > maxChildEnd) {
                    childStart = paddingStart;
                    childEnd = childStart + startMargin + child.getMeasuredWidth();
                    childTop = childBottom + this.lineSpacing;
                    this.rowCount += i2;
                }
                i = i2;
                child.setTag(R.id.row_index_key, Integer.valueOf(this.rowCount - 1));
                int childBottom2 = child.getMeasuredHeight() + childTop;
                if (isRtl) {
                    child.layout((right - left) - childEnd, childTop, ((right - left) - childStart) - startMargin, childBottom2);
                } else {
                    child.layout(childStart + startMargin, childTop, childEnd, childBottom2);
                }
                childStart += startMargin + endMargin + child.getMeasuredWidth() + this.itemSpacing;
                childBottom = childBottom2;
            }
            i3++;
            i2 = i;
        }
    }

    /* access modifiers changed from: protected */
    public int getRowCount() {
        return this.rowCount;
    }

    public int getRowIndex(View child) {
        Object index = child.getTag(R.id.row_index_key);
        if (!(index instanceof Integer)) {
            return -1;
        }
        return ((Integer) index).intValue();
    }
}
