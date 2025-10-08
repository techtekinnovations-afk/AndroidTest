package com.google.android.material.datepicker;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.core.util.Pair;
import java.util.Collection;

class MonthAdapter extends BaseAdapter {
    private static final int MAXIMUM_GRID_CELLS = ((UtcDates.getUtcCalendar().getMaximum(5) + UtcDates.getUtcCalendar().getMaximum(7)) - 1);
    static final int MAXIMUM_WEEKS = UtcDates.getUtcCalendar().getMaximum(4);
    private static final int NO_DAY_NUMBER = -1;
    final CalendarConstraints calendarConstraints;
    CalendarStyle calendarStyle;
    final DateSelector<?> dateSelector;
    final DayViewDecorator dayViewDecorator;
    final Month month;
    private Collection<Long> previouslySelectedDates;

    MonthAdapter(Month month2, DateSelector<?> dateSelector2, CalendarConstraints calendarConstraints2, DayViewDecorator dayViewDecorator2) {
        this.month = month2;
        this.dateSelector = dateSelector2;
        this.calendarConstraints = calendarConstraints2;
        this.dayViewDecorator = dayViewDecorator2;
        this.previouslySelectedDates = dateSelector2.getSelectedDays();
    }

    public boolean hasStableIds() {
        return true;
    }

    public Long getItem(int position) {
        if (position < firstPositionInMonth() || position > lastPositionInMonth()) {
            return null;
        }
        return Long.valueOf(this.month.getDay(positionToDay(position)));
    }

    public long getItemId(int position) {
        return (long) (position / this.month.daysInWeek);
    }

    public int getCount() {
        return MAXIMUM_GRID_CELLS;
    }

    /* JADX WARNING: type inference failed for: r3v4, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.widget.TextView getView(int r8, android.view.View r9, android.view.ViewGroup r10) {
        /*
            r7 = this;
            android.content.Context r0 = r10.getContext()
            r7.initializeStyles(r0)
            r0 = r9
            android.widget.TextView r0 = (android.widget.TextView) r0
            r1 = 0
            if (r9 != 0) goto L_0x001e
            android.content.Context r2 = r10.getContext()
            android.view.LayoutInflater r2 = android.view.LayoutInflater.from(r2)
            int r3 = com.google.android.material.R.layout.mtrl_calendar_day
            android.view.View r3 = r2.inflate(r3, r10, r1)
            r0 = r3
            android.widget.TextView r0 = (android.widget.TextView) r0
        L_0x001e:
            int r2 = r7.firstPositionInMonth()
            int r2 = r8 - r2
            r3 = -1
            if (r2 < 0) goto L_0x0058
            com.google.android.material.datepicker.Month r4 = r7.month
            int r4 = r4.daysInMonth
            if (r2 < r4) goto L_0x002e
            goto L_0x0058
        L_0x002e:
            int r3 = r2 + 1
            com.google.android.material.datepicker.Month r4 = r7.month
            r0.setTag(r4)
            android.content.res.Resources r4 = r0.getResources()
            android.content.res.Configuration r4 = r4.getConfiguration()
            java.util.Locale r4 = r4.locale
            java.lang.Integer r5 = java.lang.Integer.valueOf(r3)
            java.lang.Object[] r5 = new java.lang.Object[]{r5}
            java.lang.String r6 = "%d"
            java.lang.String r5 = java.lang.String.format(r4, r6, r5)
            r0.setText(r5)
            r0.setVisibility(r1)
            r1 = 1
            r0.setEnabled(r1)
            goto L_0x0060
        L_0x0058:
            r4 = 8
            r0.setVisibility(r4)
            r0.setEnabled(r1)
        L_0x0060:
            java.lang.Long r1 = r7.getItem((int) r8)
            if (r1 != 0) goto L_0x0067
            return r0
        L_0x0067:
            long r4 = r1.longValue()
            r7.updateSelectedState(r0, r4, r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.datepicker.MonthAdapter.getView(int, android.view.View, android.view.ViewGroup):android.widget.TextView");
    }

    public void updateSelectedStates(MaterialCalendarGridView monthGrid) {
        for (Long date : this.previouslySelectedDates) {
            updateSelectedStateForDate(monthGrid, date.longValue());
        }
        if (this.dateSelector != null) {
            for (Long date2 : this.dateSelector.getSelectedDays()) {
                updateSelectedStateForDate(monthGrid, date2.longValue());
            }
            this.previouslySelectedDates = this.dateSelector.getSelectedDays();
        }
    }

    private void updateSelectedStateForDate(MaterialCalendarGridView monthGrid, long date) {
        if (Month.create(date).equals(this.month)) {
            int day = this.month.getDayOfMonth(date);
            updateSelectedState((TextView) monthGrid.getChildAt(monthGrid.getAdapter().dayToPosition(day) - monthGrid.getFirstVisiblePosition()), date, day);
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: CodeShrinkVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Don't wrap MOVE or CONST insns: 0x0058: MOVE  (r8v0 int) = (r21v0 'dayNumber' int)
        	at jadx.core.dex.instructions.args.InsnArg.wrapArg(InsnArg.java:164)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.assignInline(CodeShrinkVisitor.java:133)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.checkInline(CodeShrinkVisitor.java:118)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkBlock(CodeShrinkVisitor.java:65)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkMethod(CodeShrinkVisitor.java:43)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.visit(CodeShrinkVisitor.java:35)
        */
    private void updateSelectedState(android.widget.TextView r18, long r19, int r21) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            if (r1 != 0) goto L_0x0009
            return
        L_0x0009:
            android.content.Context r5 = r1.getContext()
            java.lang.String r11 = r0.getDayContentDescription(r5, r2)
            r1.setContentDescription(r11)
            com.google.android.material.datepicker.CalendarConstraints r4 = r0.calendarConstraints
            com.google.android.material.datepicker.CalendarConstraints$DateValidator r4 = r4.getDateValidator()
            boolean r9 = r4.isValid(r2)
            r4 = 0
            if (r9 == 0) goto L_0x0049
            r6 = 1
            r1.setEnabled(r6)
            boolean r4 = r0.isSelected(r2)
            r1.setSelected(r4)
            if (r4 == 0) goto L_0x0035
            com.google.android.material.datepicker.CalendarStyle r6 = r0.calendarStyle
            com.google.android.material.datepicker.CalendarItemStyle r6 = r6.selectedDay
            r10 = r4
            r12 = r6
            goto L_0x0053
        L_0x0035:
            boolean r6 = r0.isToday(r2)
            if (r6 == 0) goto L_0x0042
            com.google.android.material.datepicker.CalendarStyle r6 = r0.calendarStyle
            com.google.android.material.datepicker.CalendarItemStyle r6 = r6.todayDay
            r10 = r4
            r12 = r6
            goto L_0x0053
        L_0x0042:
            com.google.android.material.datepicker.CalendarStyle r6 = r0.calendarStyle
            com.google.android.material.datepicker.CalendarItemStyle r6 = r6.day
            r10 = r4
            r12 = r6
            goto L_0x0053
        L_0x0049:
            r6 = 0
            r1.setEnabled(r6)
            com.google.android.material.datepicker.CalendarStyle r6 = r0.calendarStyle
            com.google.android.material.datepicker.CalendarItemStyle r6 = r6.invalidDay
            r10 = r4
            r12 = r6
        L_0x0053:
            com.google.android.material.datepicker.DayViewDecorator r4 = r0.dayViewDecorator
            if (r4 == 0) goto L_0x00a3
            r4 = -1
            r8 = r21
            if (r8 == r4) goto L_0x00a3
            com.google.android.material.datepicker.Month r4 = r0.month
            int r6 = r4.year
            com.google.android.material.datepicker.Month r4 = r0.month
            int r7 = r4.month
            com.google.android.material.datepicker.DayViewDecorator r4 = r0.dayViewDecorator
            android.content.res.ColorStateList r13 = r4.getBackgroundColor(r5, r6, r7, r8, r9, r10)
            com.google.android.material.datepicker.DayViewDecorator r4 = r0.dayViewDecorator
            r8 = r21
            android.content.res.ColorStateList r14 = r4.getTextColor(r5, r6, r7, r8, r9, r10)
            r12.styleItem(r1, r13, r14)
            com.google.android.material.datepicker.DayViewDecorator r4 = r0.dayViewDecorator
            android.graphics.drawable.Drawable r15 = r4.getCompoundDrawableLeft(r5, r6, r7, r8, r9, r10)
            com.google.android.material.datepicker.DayViewDecorator r4 = r0.dayViewDecorator
            android.graphics.drawable.Drawable r4 = r4.getCompoundDrawableTop(r5, r6, r7, r8, r9, r10)
            r8 = r4
            com.google.android.material.datepicker.DayViewDecorator r4 = r0.dayViewDecorator
            r2 = r8
            r8 = r21
            android.graphics.drawable.Drawable r3 = r4.getCompoundDrawableRight(r5, r6, r7, r8, r9, r10)
            com.google.android.material.datepicker.DayViewDecorator r4 = r0.dayViewDecorator
            android.graphics.drawable.Drawable r4 = r4.getCompoundDrawableBottom(r5, r6, r7, r8, r9, r10)
            r1.setCompoundDrawables(r15, r2, r3, r4)
            r8 = r4
            com.google.android.material.datepicker.DayViewDecorator r4 = r0.dayViewDecorator
            r16 = r8
            r8 = r21
            java.lang.CharSequence r4 = r4.getContentDescription(r5, r6, r7, r8, r9, r10, r11)
            r1.setContentDescription(r4)
            goto L_0x00a6
        L_0x00a3:
            r12.styleItem(r1)
        L_0x00a6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.datepicker.MonthAdapter.updateSelectedState(android.widget.TextView, long, int):void");
    }

    private String getDayContentDescription(Context context, long date) {
        return DateStrings.getDayContentDescription(context, date, isToday(date), isStartOfRange(date), isEndOfRange(date));
    }

    private boolean isToday(long date) {
        return UtcDates.getTodayCalendar().getTimeInMillis() == date;
    }

    /* access modifiers changed from: package-private */
    public boolean isStartOfRange(long date) {
        for (Pair<Long, Long> range : this.dateSelector.getSelectedRanges()) {
            if (range.first != null && ((Long) range.first).longValue() == date) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean isEndOfRange(long date) {
        for (Pair<Long, Long> range : this.dateSelector.getSelectedRanges()) {
            if (range.second != null && ((Long) range.second).longValue() == date) {
                return true;
            }
        }
        return false;
    }

    private boolean isSelected(long date) {
        for (Long longValue : this.dateSelector.getSelectedDays()) {
            if (UtcDates.canonicalYearMonthDay(date) == UtcDates.canonicalYearMonthDay(longValue.longValue())) {
                return true;
            }
        }
        return false;
    }

    private void initializeStyles(Context context) {
        if (this.calendarStyle == null) {
            this.calendarStyle = new CalendarStyle(context);
        }
    }

    /* access modifiers changed from: package-private */
    public int firstPositionInMonth() {
        return this.month.daysFromStartOfWeekToFirstOfMonth(this.calendarConstraints.getFirstDayOfWeek());
    }

    /* access modifiers changed from: package-private */
    public int lastPositionInMonth() {
        return (firstPositionInMonth() + this.month.daysInMonth) - 1;
    }

    /* access modifiers changed from: package-private */
    public int positionToDay(int position) {
        return (position - firstPositionInMonth()) + 1;
    }

    /* access modifiers changed from: package-private */
    public int dayToPosition(int day) {
        return firstPositionInMonth() + (day - 1);
    }

    /* access modifiers changed from: package-private */
    public boolean withinMonth(int position) {
        return position >= firstPositionInMonth() && position <= lastPositionInMonth();
    }

    /* access modifiers changed from: package-private */
    public boolean isFirstInRow(int position) {
        return position % this.month.daysInWeek == 0;
    }

    /* access modifiers changed from: package-private */
    public boolean isLastInRow(int position) {
        return (position + 1) % this.month.daysInWeek == 0;
    }
}
