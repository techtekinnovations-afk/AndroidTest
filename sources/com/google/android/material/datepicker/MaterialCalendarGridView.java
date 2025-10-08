package com.google.android.material.datepicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;
import androidx.core.util.Pair;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.internal.ViewUtils;
import java.util.Calendar;

final class MaterialCalendarGridView extends GridView {
    private final Calendar dayCompute;
    private final boolean nestedScrollable;

    public MaterialCalendarGridView(Context context) {
        this(context, (AttributeSet) null);
    }

    public MaterialCalendarGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialCalendarGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.dayCompute = UtcDates.getUtcCalendar();
        if (MaterialDatePicker.isFullscreen(getContext())) {
            setNextFocusLeftId(R.id.cancel_button);
            setNextFocusRightId(R.id.confirm_button);
        }
        this.nestedScrollable = MaterialDatePicker.isNestedScrollable(getContext());
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat() {
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setCollectionInfo((Object) null);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getAdapter().notifyDataSetChanged();
    }

    public void setSelection(int position) {
        if (position < getAdapter().firstPositionInMonth()) {
            super.setSelection(getAdapter().firstPositionInMonth());
        } else {
            super.setSelection(position);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!super.onKeyDown(keyCode, event)) {
            return false;
        }
        int selectedPosition = getSelectedItemPosition();
        if (selectedPosition == -1 || (selectedPosition >= getAdapter().firstPositionInMonth() && selectedPosition <= getAdapter().lastPositionInMonth())) {
            return true;
        }
        if (19 != keyCode) {
            return false;
        }
        setSelection(getAdapter().firstPositionInMonth());
        return true;
    }

    public MonthAdapter getAdapter() {
        return (MonthAdapter) super.getAdapter();
    }

    public final void setAdapter(ListAdapter adapter) {
        if (adapter instanceof MonthAdapter) {
            super.setAdapter(adapter);
            return;
        }
        throw new IllegalArgumentException(String.format("%1$s must have its Adapter set to a %2$s", new Object[]{MaterialCalendarGridView.class.getCanonicalName(), MonthAdapter.class.getCanonicalName()}));
    }

    /* access modifiers changed from: protected */
    public final void onDraw(Canvas canvas) {
        int firstHighlightPosition;
        int rangeHighlightStart;
        int firstVisiblePositionInMonth;
        int rangeHighlightStart2;
        int rangeHighlightEnd;
        int rangeHighlightStart3;
        int i;
        int left;
        MaterialCalendarGridView materialCalendarGridView = this;
        super.onDraw(canvas);
        MonthAdapter monthAdapter = materialCalendarGridView.getAdapter();
        DateSelector<?> dateSelector = monthAdapter.dateSelector;
        CalendarStyle calendarStyle = monthAdapter.calendarStyle;
        int firstVisiblePositionInMonth2 = Math.max(monthAdapter.firstPositionInMonth(), materialCalendarGridView.getFirstVisiblePosition());
        int lastVisiblePositionInMonth = Math.min(monthAdapter.lastPositionInMonth(), materialCalendarGridView.getLastVisiblePosition());
        Long firstOfMonth = monthAdapter.getItem(firstVisiblePositionInMonth2);
        Long lastOfMonth = monthAdapter.getItem(lastVisiblePositionInMonth);
        for (Pair<Long, Long> range : dateSelector.getSelectedRanges()) {
            if (range.first == null) {
                DateSelector<?> dateSelector2 = dateSelector;
                int i2 = firstVisiblePositionInMonth2;
                int i3 = lastVisiblePositionInMonth;
                materialCalendarGridView = this;
            } else if (range.second != null) {
                long startItem = ((Long) range.first).longValue();
                long endItem = ((Long) range.second).longValue();
                if (!skipMonth(firstOfMonth, lastOfMonth, Long.valueOf(startItem), Long.valueOf(endItem))) {
                    boolean isRtl = ViewUtils.isLayoutRtl(materialCalendarGridView);
                    DateSelector<?> dateSelector3 = dateSelector;
                    if (startItem < firstOfMonth.longValue()) {
                        firstHighlightPosition = firstVisiblePositionInMonth2;
                        if (monthAdapter.isFirstInRow(firstHighlightPosition)) {
                            rangeHighlightStart = 0;
                        } else if (!isRtl) {
                            rangeHighlightStart = materialCalendarGridView.getChildAtPosition(firstHighlightPosition - 1).getRight();
                        } else {
                            rangeHighlightStart = materialCalendarGridView.getChildAtPosition(firstHighlightPosition - 1).getLeft();
                        }
                    } else {
                        materialCalendarGridView.dayCompute.setTimeInMillis(startItem);
                        firstHighlightPosition = monthAdapter.dayToPosition(materialCalendarGridView.dayCompute.get(5));
                        rangeHighlightStart = horizontalMidPoint(materialCalendarGridView.getChildAtPosition(firstHighlightPosition));
                    }
                    if (endItem > lastOfMonth.longValue()) {
                        rangeHighlightStart2 = rangeHighlightStart;
                        rangeHighlightStart3 = lastVisiblePositionInMonth;
                        if (monthAdapter.isLastInRow(rangeHighlightStart3) != 0) {
                            rangeHighlightEnd = materialCalendarGridView.getWidth();
                        } else if (!isRtl) {
                            rangeHighlightEnd = materialCalendarGridView.getChildAtPosition(rangeHighlightStart3).getRight();
                        } else {
                            rangeHighlightEnd = materialCalendarGridView.getChildAtPosition(rangeHighlightStart3).getLeft();
                        }
                        firstVisiblePositionInMonth = firstVisiblePositionInMonth2;
                    } else {
                        rangeHighlightStart2 = rangeHighlightStart;
                        materialCalendarGridView.dayCompute.setTimeInMillis(endItem);
                        firstVisiblePositionInMonth = firstVisiblePositionInMonth2;
                        rangeHighlightStart3 = monthAdapter.dayToPosition(materialCalendarGridView.dayCompute.get(5));
                        rangeHighlightEnd = horizontalMidPoint(materialCalendarGridView.getChildAtPosition(rangeHighlightStart3));
                    }
                    int lastVisiblePositionInMonth2 = lastVisiblePositionInMonth;
                    int firstRow = (int) monthAdapter.getItemId(firstHighlightPosition);
                    int lastRow = (int) monthAdapter.getItemId(rangeHighlightStart3);
                    int bottom = firstRow;
                    while (bottom <= lastRow) {
                        MonthAdapter monthAdapter2 = monthAdapter;
                        int firstPositionInRow = bottom * materialCalendarGridView.getNumColumns();
                        int lastRow2 = lastRow;
                        int lastRow3 = (firstPositionInRow + materialCalendarGridView.getNumColumns()) - 1;
                        View firstView = materialCalendarGridView.getChildAtPosition(firstPositionInRow);
                        int top = firstView.getTop() + calendarStyle.day.getTopInset();
                        int row = bottom;
                        int bottom2 = firstView.getBottom() - calendarStyle.day.getBottomInset();
                        if (!isRtl) {
                            left = firstPositionInRow > firstHighlightPosition ? 0 : rangeHighlightStart2;
                            i = rangeHighlightStart3 > lastRow3 ? getWidth() : rangeHighlightEnd;
                        } else {
                            left = rangeHighlightStart3 > lastRow3 ? 0 : rangeHighlightEnd;
                            i = firstPositionInRow > firstHighlightPosition ? getWidth() : rangeHighlightStart2;
                        }
                        int i4 = firstPositionInRow;
                        int left2 = left;
                        int lastHighlightPosition = rangeHighlightStart3;
                        int right = i;
                        int i5 = lastRow3;
                        int i6 = left2;
                        int i7 = top;
                        canvas.drawRect((float) left2, (float) top, (float) right, (float) bottom2, calendarStyle.rangeFill);
                        bottom = row + 1;
                        materialCalendarGridView = this;
                        monthAdapter = monthAdapter2;
                        lastRow = lastRow2;
                        rangeHighlightStart3 = lastHighlightPosition;
                    }
                    int i8 = rangeHighlightStart3;
                    int i9 = lastRow;
                    int i10 = bottom;
                    materialCalendarGridView = this;
                    dateSelector = dateSelector3;
                    firstVisiblePositionInMonth2 = firstVisiblePositionInMonth;
                    lastVisiblePositionInMonth = lastVisiblePositionInMonth2;
                }
            }
        }
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.nestedScrollable) {
            super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(ViewCompat.MEASURED_SIZE_MASK, Integer.MIN_VALUE));
            getLayoutParams().height = getMeasuredHeight();
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        if (gainFocus) {
            gainFocus(direction, previouslyFocusedRect);
        } else {
            super.onFocusChanged(false, direction, previouslyFocusedRect);
        }
    }

    private void gainFocus(int direction, Rect previouslyFocusedRect) {
        if (direction == 33) {
            setSelection(getAdapter().lastPositionInMonth());
        } else if (direction == 130) {
            setSelection(getAdapter().firstPositionInMonth());
        } else {
            super.onFocusChanged(true, direction, previouslyFocusedRect);
        }
    }

    private View getChildAtPosition(int position) {
        return getChildAt(position - getFirstVisiblePosition());
    }

    private static boolean skipMonth(Long firstOfMonth, Long lastOfMonth, Long startDay, Long endDay) {
        if (firstOfMonth == null || lastOfMonth == null || startDay == null || endDay == null || startDay.longValue() > lastOfMonth.longValue() || endDay.longValue() < firstOfMonth.longValue()) {
            return true;
        }
        return false;
    }

    private static int horizontalMidPoint(View view) {
        return view.getLeft() + (view.getWidth() / 2);
    }
}
