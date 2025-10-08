package com.google.android.material.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.RadioButton;
import android.widget.ToggleButton;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.StateListCornerSize;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MaterialButtonToggleGroup extends MaterialButtonGroup {
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_MaterialButtonToggleGroup;
    private static final String LOG_TAG = "MButtonToggleGroup";
    private Set<Integer> checkedIds;
    private final int defaultCheckId;
    private final LinkedHashSet<OnButtonCheckedListener> onButtonCheckedListeners;
    private boolean selectionRequired;
    private boolean singleSelection;
    private boolean skipCheckedStateTracker;

    public interface OnButtonCheckedListener {
        void onButtonChecked(MaterialButtonToggleGroup materialButtonToggleGroup, int i, boolean z);
    }

    public MaterialButtonToggleGroup(Context context) {
        this(context, (AttributeSet) null);
    }

    public MaterialButtonToggleGroup(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.materialButtonToggleGroupStyle);
    }

    public MaterialButtonToggleGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        this.onButtonCheckedListeners = new LinkedHashSet<>();
        this.skipCheckedStateTracker = false;
        this.checkedIds = new HashSet();
        TypedArray attributes = ThemeEnforcement.obtainStyledAttributes(getContext(), attrs, R.styleable.MaterialButtonToggleGroup, defStyleAttr, DEF_STYLE_RES, new int[0]);
        setSingleSelection(attributes.getBoolean(R.styleable.MaterialButtonToggleGroup_singleSelection, false));
        this.defaultCheckId = attributes.getResourceId(R.styleable.MaterialButtonToggleGroup_checkedButton, -1);
        this.selectionRequired = attributes.getBoolean(R.styleable.MaterialButtonToggleGroup_selectionRequired, false);
        if (this.innerCornerSize == null) {
            this.innerCornerSize = StateListCornerSize.create(new AbsoluteCornerSize(0.0f));
        }
        setEnabled(attributes.getBoolean(R.styleable.MaterialButtonToggleGroup_android_enabled, true));
        attributes.recycle();
        setImportantForAccessibility(1);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        if (this.defaultCheckId != -1) {
            updateCheckedIds(Collections.singleton(Integer.valueOf(this.defaultCheckId)));
        }
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (!(child instanceof MaterialButton)) {
            Log.e(LOG_TAG, "Child views must be of type MaterialButton.");
            return;
        }
        super.addView(child, index, params);
        MaterialButton buttonChild = (MaterialButton) child;
        setupButtonChild(buttonChild);
        checkInternal(buttonChild.getId(), buttonChild.isChecked());
        ViewCompat.setAccessibilityDelegate(buttonChild, new AccessibilityDelegateCompat() {
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                info.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, MaterialButtonToggleGroup.this.getIndexWithinVisibleButtons(host), 1, false, ((MaterialButton) host).isChecked()));
            }
        });
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        int i;
        super.onInitializeAccessibilityNodeInfo(info);
        AccessibilityNodeInfoCompat infoCompat = AccessibilityNodeInfoCompat.wrap(info);
        int visibleButtonCount = getVisibleButtonCount();
        if (isSingleSelection()) {
            i = 1;
        } else {
            i = 2;
        }
        infoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, visibleButtonCount, false, i));
    }

    public void check(int id) {
        checkInternal(id, true);
    }

    public void uncheck(int id) {
        checkInternal(id, false);
    }

    public void clearChecked() {
        updateCheckedIds(new HashSet());
    }

    public int getCheckedButtonId() {
        if (!this.singleSelection || this.checkedIds.isEmpty()) {
            return -1;
        }
        return this.checkedIds.iterator().next().intValue();
    }

    public List<Integer> getCheckedButtonIds() {
        List<Integer> orderedCheckedIds = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            int childId = getChildButton(i).getId();
            if (this.checkedIds.contains(Integer.valueOf(childId))) {
                orderedCheckedIds.add(Integer.valueOf(childId));
            }
        }
        return orderedCheckedIds;
    }

    public void addOnButtonCheckedListener(OnButtonCheckedListener listener) {
        this.onButtonCheckedListeners.add(listener);
    }

    public void removeOnButtonCheckedListener(OnButtonCheckedListener listener) {
        this.onButtonCheckedListeners.remove(listener);
    }

    public void clearOnButtonCheckedListeners() {
        this.onButtonCheckedListeners.clear();
    }

    public boolean isSingleSelection() {
        return this.singleSelection;
    }

    public void setSingleSelection(boolean singleSelection2) {
        if (this.singleSelection != singleSelection2) {
            this.singleSelection = singleSelection2;
            clearChecked();
        }
        updateChildrenA11yClassName();
    }

    private void updateChildrenA11yClassName() {
        String className = getChildrenA11yClassName();
        for (int i = 0; i < getChildCount(); i++) {
            getChildButton(i).setA11yClassName(className);
        }
    }

    private String getChildrenA11yClassName() {
        return (this.singleSelection ? RadioButton.class : ToggleButton.class).getName();
    }

    public void setSelectionRequired(boolean selectionRequired2) {
        this.selectionRequired = selectionRequired2;
    }

    public boolean isSelectionRequired() {
        return this.selectionRequired;
    }

    public void setSingleSelection(int id) {
        setSingleSelection(getResources().getBoolean(id));
    }

    private void setCheckedStateForView(int viewId, boolean checked) {
        View checkedView = findViewById(viewId);
        if (checkedView instanceof MaterialButton) {
            this.skipCheckedStateTracker = true;
            ((MaterialButton) checkedView).setChecked(checked);
            this.skipCheckedStateTracker = false;
        }
    }

    private boolean isChildVisible(int i) {
        return getChildAt(i).getVisibility() != 8;
    }

    private int getVisibleButtonCount() {
        int count = 0;
        for (int i = 0; i < getChildCount(); i++) {
            if ((getChildAt(i) instanceof MaterialButton) && isChildVisible(i)) {
                count++;
            }
        }
        return count;
    }

    /* access modifiers changed from: private */
    public int getIndexWithinVisibleButtons(View child) {
        if (!(child instanceof MaterialButton)) {
            return -1;
        }
        int index = 0;
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) == child) {
                return index;
            }
            if ((getChildAt(i) instanceof MaterialButton) && isChildVisible(i)) {
                index++;
            }
        }
        return -1;
    }

    private void checkInternal(int buttonId, boolean checked) {
        if (buttonId == -1) {
            Log.e(LOG_TAG, "Button ID is not valid: " + buttonId);
            return;
        }
        Set<Integer> checkedIds2 = new HashSet<>(this.checkedIds);
        if (checked && !checkedIds2.contains(Integer.valueOf(buttonId))) {
            if (this.singleSelection && !checkedIds2.isEmpty()) {
                checkedIds2.clear();
            }
            checkedIds2.add(Integer.valueOf(buttonId));
        } else if (!checked && checkedIds2.contains(Integer.valueOf(buttonId))) {
            if (!this.selectionRequired || checkedIds2.size() > 1) {
                checkedIds2.remove(Integer.valueOf(buttonId));
            }
        } else {
            return;
        }
        updateCheckedIds(checkedIds2);
    }

    private void updateCheckedIds(Set<Integer> checkedIds2) {
        Set<Integer> previousCheckedIds = this.checkedIds;
        this.checkedIds = new HashSet(checkedIds2);
        for (int i = 0; i < getChildCount(); i++) {
            int buttonId = getChildButton(i).getId();
            setCheckedStateForView(buttonId, checkedIds2.contains(Integer.valueOf(buttonId)));
            if (previousCheckedIds.contains(Integer.valueOf(buttonId)) != checkedIds2.contains(Integer.valueOf(buttonId))) {
                dispatchOnButtonChecked(buttonId, checkedIds2.contains(Integer.valueOf(buttonId)));
            }
        }
        invalidate();
    }

    private void dispatchOnButtonChecked(int buttonId, boolean checked) {
        Iterator it = this.onButtonCheckedListeners.iterator();
        while (it.hasNext()) {
            ((OnButtonCheckedListener) it.next()).onButtonChecked(this, buttonId, checked);
        }
    }

    private void setupButtonChild(MaterialButton buttonChild) {
        buttonChild.setMaxLines(1);
        buttonChild.setEllipsize(TextUtils.TruncateAt.END);
        buttonChild.setCheckable(true);
        buttonChild.setA11yClassName(getChildrenA11yClassName());
    }

    /* access modifiers changed from: package-private */
    public void onButtonCheckedStateChanged(MaterialButton button, boolean isChecked) {
        if (!this.skipCheckedStateTracker) {
            checkInternal(button.getId(), isChecked);
        }
    }
}
