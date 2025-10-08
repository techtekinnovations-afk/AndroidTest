package com.google.android.material.timepicker;

import android.content.res.Resources;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.timepicker.TimePickerView;
import java.util.Locale;

class TimePickerTextInputPresenter implements TimePickerView.OnSelectionChange, TimePickerPresenter {
    private final TimePickerTextInputKeyController controller;
    private final EditText hourEditText;
    private final ChipTextInputComboView hourTextInput;
    private final TextWatcher hourTextWatcher = new TextWatcherAdapter() {
        public void afterTextChanged(Editable s) {
            try {
                if (TextUtils.isEmpty(s)) {
                    TimePickerTextInputPresenter.this.time.setHour(0);
                    return;
                }
                TimePickerTextInputPresenter.this.time.setHour(Integer.parseInt(s.toString()));
            } catch (NumberFormatException e) {
            }
        }
    };
    private final EditText minuteEditText;
    private final ChipTextInputComboView minuteTextInput;
    private final TextWatcher minuteTextWatcher = new TextWatcherAdapter() {
        public void afterTextChanged(Editable s) {
            try {
                if (TextUtils.isEmpty(s)) {
                    TimePickerTextInputPresenter.this.time.setMinute(0);
                    return;
                }
                TimePickerTextInputPresenter.this.time.setMinute(Integer.parseInt(s.toString()));
            } catch (NumberFormatException e) {
            }
        }
    };
    /* access modifiers changed from: private */
    public final TimeModel time;
    private final LinearLayout timePickerView;
    private MaterialButtonToggleGroup toggle;

    public TimePickerTextInputPresenter(LinearLayout timePickerView2, TimeModel time2) {
        this.timePickerView = timePickerView2;
        this.time = time2;
        final Resources res = timePickerView2.getResources();
        this.minuteTextInput = (ChipTextInputComboView) timePickerView2.findViewById(R.id.material_minute_text_input);
        this.hourTextInput = (ChipTextInputComboView) timePickerView2.findViewById(R.id.material_hour_text_input);
        TextView minuteLabel = (TextView) this.minuteTextInput.findViewById(R.id.material_label);
        TextView hourLabel = (TextView) this.hourTextInput.findViewById(R.id.material_label);
        minuteLabel.setText(res.getString(R.string.material_timepicker_minute));
        minuteLabel.setImportantForAccessibility(2);
        hourLabel.setText(res.getString(R.string.material_timepicker_hour));
        hourLabel.setImportantForAccessibility(2);
        this.minuteTextInput.setTag(R.id.selection_type, 12);
        this.hourTextInput.setTag(R.id.selection_type, 10);
        if (time2.format == 0) {
            setupPeriodToggle();
        }
        View.OnClickListener onClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                TimePickerTextInputPresenter.this.onSelectionChanged(((Integer) v.getTag(R.id.selection_type)).intValue());
            }
        };
        this.hourTextInput.setOnClickListener(onClickListener);
        this.minuteTextInput.setOnClickListener(onClickListener);
        this.hourTextInput.addInputFilter(time2.getHourInputValidator());
        this.minuteTextInput.addInputFilter(time2.getMinuteInputValidator());
        this.hourEditText = this.hourTextInput.getTextInput().getEditText();
        this.hourEditText.setAccessibilityDelegate(setTimeUnitAccessiblityLabel(timePickerView2.getResources(), R.string.material_timepicker_hour));
        this.minuteEditText = this.minuteTextInput.getTextInput().getEditText();
        this.minuteEditText.setAccessibilityDelegate(setTimeUnitAccessiblityLabel(timePickerView2.getResources(), R.string.material_timepicker_minute));
        this.controller = new TimePickerTextInputKeyController(this.hourTextInput, this.minuteTextInput, time2);
        final TimeModel time3 = time2;
        this.hourTextInput.setChipDelegate(new ClickActionDelegate(timePickerView2.getContext(), R.string.material_hour_selection) {
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                info.setContentDescription(res.getString(R.string.material_timepicker_hour) + " " + host.getResources().getString(time3.getHourContentDescriptionResId(), new Object[]{String.valueOf(time3.getHourForDisplay())}));
            }
        });
        this.minuteTextInput.setChipDelegate(new ClickActionDelegate(timePickerView2.getContext(), R.string.material_minute_selection) {
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                info.setContentDescription(res.getString(R.string.material_timepicker_minute) + " " + host.getResources().getString(R.string.material_minute_suffix, new Object[]{String.valueOf(time3.minute)}));
            }
        });
        initialize();
    }

    private View.AccessibilityDelegate setTimeUnitAccessiblityLabel(final Resources res, final int contentDescriptionResId) {
        return new View.AccessibilityDelegate() {
            public void onInitializeAccessibilityNodeInfo(View v, AccessibilityNodeInfo info) {
                super.onInitializeAccessibilityNodeInfo(v, info);
                info.setText(res.getString(contentDescriptionResId));
            }
        };
    }

    public void initialize() {
        addTextWatchers();
        setTime(this.time);
        this.controller.bind();
    }

    private void addTextWatchers() {
        this.hourEditText.addTextChangedListener(this.hourTextWatcher);
        this.minuteEditText.addTextChangedListener(this.minuteTextWatcher);
    }

    private void removeTextWatchers() {
        this.hourEditText.removeTextChangedListener(this.hourTextWatcher);
        this.minuteEditText.removeTextChangedListener(this.minuteTextWatcher);
    }

    private void setTime(TimeModel time2) {
        removeTextWatchers();
        Locale current = this.timePickerView.getResources().getConfiguration().locale;
        String minuteFormatted = String.format(current, TimeModel.ZERO_LEADING_NUMBER_FORMAT, new Object[]{Integer.valueOf(time2.minute)});
        String hourFormatted = String.format(current, TimeModel.ZERO_LEADING_NUMBER_FORMAT, new Object[]{Integer.valueOf(time2.getHourForDisplay())});
        this.minuteTextInput.setText(minuteFormatted);
        this.hourTextInput.setText(hourFormatted);
        addTextWatchers();
        updateSelection();
    }

    private void setupPeriodToggle() {
        this.toggle = (MaterialButtonToggleGroup) this.timePickerView.findViewById(R.id.material_clock_period_toggle);
        this.toggle.addOnButtonCheckedListener(new TimePickerTextInputPresenter$$ExternalSyntheticLambda0(this));
        this.toggle.setVisibility(0);
        updateSelection();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$setupPeriodToggle$0$com-google-android-material-timepicker-TimePickerTextInputPresenter  reason: not valid java name */
    public /* synthetic */ void m1739lambda$setupPeriodToggle$0$comgoogleandroidmaterialtimepickerTimePickerTextInputPresenter(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
        if (isChecked) {
            this.time.setPeriod(checkedId == R.id.material_clock_period_pm_button ? 1 : 0);
        }
    }

    private void updateSelection() {
        int i;
        if (this.toggle != null) {
            MaterialButtonToggleGroup materialButtonToggleGroup = this.toggle;
            if (this.time.period == 0) {
                i = R.id.material_clock_period_am_button;
            } else {
                i = R.id.material_clock_period_pm_button;
            }
            materialButtonToggleGroup.check(i);
        }
    }

    public void onSelectionChanged(int selection) {
        this.time.selection = selection;
        boolean z = true;
        this.minuteTextInput.setChecked(selection == 12);
        ChipTextInputComboView chipTextInputComboView = this.hourTextInput;
        if (selection != 10) {
            z = false;
        }
        chipTextInputComboView.setChecked(z);
        updateSelection();
    }

    public void show() {
        this.timePickerView.setVisibility(0);
        onSelectionChanged(this.time.selection);
    }

    public void hide() {
        View currentFocus = this.timePickerView.getFocusedChild();
        if (currentFocus != null) {
            ViewUtils.hideKeyboard(currentFocus, false);
        }
        this.timePickerView.setVisibility(8);
    }

    public void invalidate() {
        setTime(this.time);
    }

    public void resetChecked() {
        boolean z = true;
        this.minuteTextInput.setChecked(this.time.selection == 12);
        ChipTextInputComboView chipTextInputComboView = this.hourTextInput;
        if (this.time.selection != 10) {
            z = false;
        }
        chipTextInputComboView.setChecked(z);
    }

    public void clearCheck() {
        this.minuteTextInput.setChecked(false);
        this.hourTextInput.setChecked(false);
    }
}
