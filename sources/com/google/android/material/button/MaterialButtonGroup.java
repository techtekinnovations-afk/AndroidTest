package com.google.android.material.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.google.android.material.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.StateListCornerSize;
import com.google.android.material.shape.StateListShapeAppearanceModel;
import com.google.android.material.shape.StateListSizeChange;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class MaterialButtonGroup extends LinearLayout {
    private static final int DEF_STYLE_RES = R.style.Widget_Material3_MaterialButtonGroup;
    private static final String LOG_TAG = "MButtonGroup";
    private StateListSizeChange buttonSizeChange;
    private Integer[] childOrder;
    private final Comparator<MaterialButton> childOrderComparator;
    private boolean childShapesDirty;
    private StateListShapeAppearanceModel groupStateListShapeAppearance;
    StateListCornerSize innerCornerSize;
    private final List<ShapeAppearanceModel> originalChildShapeAppearanceModels;
    private final List<StateListShapeAppearanceModel> originalChildStateListShapeAppearanceModels;
    private final PressedStateTracker pressedStateTracker;
    private int spacing;

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-google-android-material-button-MaterialButtonGroup  reason: not valid java name */
    public /* synthetic */ int m1638lambda$new$0$comgoogleandroidmaterialbuttonMaterialButtonGroup(MaterialButton v1, MaterialButton v2) {
        int checked = Boolean.valueOf(v1.isChecked()).compareTo(Boolean.valueOf(v2.isChecked()));
        if (checked != 0) {
            return checked;
        }
        int stateful = Boolean.valueOf(v1.isPressed()).compareTo(Boolean.valueOf(v2.isPressed()));
        if (stateful != 0) {
            return stateful;
        }
        return Integer.compare(indexOfChild(v1), indexOfChild(v2));
    }

    public MaterialButtonGroup(Context context) {
        this(context, (AttributeSet) null);
    }

    public MaterialButtonGroup(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.materialButtonGroupStyle);
    }

    public MaterialButtonGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        this.originalChildShapeAppearanceModels = new ArrayList();
        this.originalChildStateListShapeAppearanceModels = new ArrayList();
        this.pressedStateTracker = new PressedStateTracker();
        this.childOrderComparator = new MaterialButtonGroup$$ExternalSyntheticLambda0(this);
        this.childShapesDirty = true;
        Context context2 = getContext();
        TypedArray attributes = ThemeEnforcement.obtainStyledAttributes(context2, attrs, R.styleable.MaterialButtonGroup, defStyleAttr, DEF_STYLE_RES, new int[0]);
        if (attributes.hasValue(R.styleable.MaterialButtonGroup_buttonSizeChange)) {
            this.buttonSizeChange = StateListSizeChange.create(context2, attributes, R.styleable.MaterialButtonGroup_buttonSizeChange);
        }
        if (attributes.hasValue(R.styleable.MaterialButtonGroup_shapeAppearance)) {
            this.groupStateListShapeAppearance = StateListShapeAppearanceModel.create(context2, attributes, R.styleable.MaterialButtonGroup_shapeAppearance);
            if (this.groupStateListShapeAppearance == null) {
                this.groupStateListShapeAppearance = new StateListShapeAppearanceModel.Builder(ShapeAppearanceModel.builder(context2, attributes.getResourceId(R.styleable.MaterialButtonGroup_shapeAppearance, 0), attributes.getResourceId(R.styleable.MaterialButtonGroup_shapeAppearanceOverlay, 0)).build()).build();
            }
        }
        if (attributes.hasValue(R.styleable.MaterialButtonGroup_innerCornerSize)) {
            this.innerCornerSize = StateListCornerSize.create(context2, attributes, R.styleable.MaterialButtonGroup_innerCornerSize, new AbsoluteCornerSize(0.0f));
        }
        this.spacing = attributes.getDimensionPixelSize(R.styleable.MaterialButtonGroup_android_spacing, 0);
        setChildrenDrawingOrderEnabled(true);
        setEnabled(attributes.getBoolean(R.styleable.MaterialButtonGroup_android_enabled, true));
        attributes.recycle();
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        updateChildOrder();
        super.dispatchDraw(canvas);
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (!(child instanceof MaterialButton)) {
            Log.e(LOG_TAG, "Child views must be of type MaterialButton.");
            return;
        }
        recoverAllChildrenLayoutParams();
        this.childShapesDirty = true;
        super.addView(child, index, params);
        MaterialButton buttonChild = (MaterialButton) child;
        setGeneratedIdIfNeeded(buttonChild);
        buttonChild.setOnPressedChangeListenerInternal(this.pressedStateTracker);
        this.originalChildShapeAppearanceModels.add(buttonChild.getShapeAppearanceModel());
        this.originalChildStateListShapeAppearanceModels.add(buttonChild.getStateListShapeAppearanceModel());
        buttonChild.setEnabled(isEnabled());
    }

    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        if (child instanceof MaterialButton) {
            ((MaterialButton) child).setOnPressedChangeListenerInternal((MaterialButton.OnPressedChangeListener) null);
        }
        int indexOfChild = indexOfChild(child);
        if (indexOfChild >= 0) {
            this.originalChildShapeAppearanceModels.remove(indexOfChild);
            this.originalChildStateListShapeAppearanceModels.remove(indexOfChild);
        }
        this.childShapesDirty = true;
        updateChildShapes();
        recoverAllChildrenLayoutParams();
        adjustChildMarginsAndUpdateLayout();
    }

    private void recoverAllChildrenLayoutParams() {
        for (int i = 0; i < getChildCount(); i++) {
            getChildButton(i).recoverOriginalLayoutParams();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        updateChildShapes();
        adjustChildMarginsAndUpdateLayout();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            recoverAllChildrenLayoutParams();
            adjustChildSizeChange();
        }
    }

    /* access modifiers changed from: package-private */
    public void updateChildShapes() {
        if (!(this.innerCornerSize == null && this.groupStateListShapeAppearance == null) && this.childShapesDirty) {
            this.childShapesDirty = false;
            int childCount = getChildCount();
            int firstVisibleChildIndex = getFirstVisibleChildIndex();
            int lastVisibleChildIndex = getLastVisibleChildIndex();
            int i = 0;
            while (i < childCount) {
                MaterialButton button = getChildButton(i);
                if (button.getVisibility() != 8) {
                    boolean isFirstVisible = i == firstVisibleChildIndex;
                    boolean isLastVisible = i == lastVisibleChildIndex;
                    StateListShapeAppearanceModel.Builder originalStateListShapeBuilder = getOriginalStateListShapeBuilder(isFirstVisible, isLastVisible, i);
                    boolean isHorizontal = getOrientation() == 0;
                    boolean isRtl = ViewUtils.isLayoutRtl(this);
                    int cornerPositionBitsToKeep = 0;
                    if (isHorizontal) {
                        if (isFirstVisible) {
                            cornerPositionBitsToKeep = 0 | 5;
                        }
                        if (isLastVisible) {
                            cornerPositionBitsToKeep |= 10;
                        }
                        if (isRtl) {
                            cornerPositionBitsToKeep = StateListShapeAppearanceModel.swapCornerPositionRtl(cornerPositionBitsToKeep);
                        }
                    } else {
                        if (isFirstVisible) {
                            cornerPositionBitsToKeep = 0 | 3;
                        }
                        if (isLastVisible) {
                            cornerPositionBitsToKeep |= 12;
                        }
                    }
                    StateListShapeAppearanceModel newStateListShape = originalStateListShapeBuilder.setCornerSizeOverride(this.innerCornerSize, ~cornerPositionBitsToKeep).build();
                    if (newStateListShape.isStateful()) {
                        button.setStateListShapeAppearanceModel(newStateListShape);
                    } else {
                        button.setShapeAppearanceModel(newStateListShape.getDefaultShape(true));
                    }
                }
                i++;
            }
        }
    }

    private StateListShapeAppearanceModel.Builder getOriginalStateListShapeBuilder(boolean isFirstVisible, boolean isLastVisible, int index) {
        StateListShapeAppearanceModel originalStateList;
        if (this.groupStateListShapeAppearance == null || (!isFirstVisible && !isLastVisible)) {
            originalStateList = this.originalChildStateListShapeAppearanceModels.get(index);
        } else {
            originalStateList = this.groupStateListShapeAppearance;
        }
        if (originalStateList == null) {
            return new StateListShapeAppearanceModel.Builder(this.originalChildShapeAppearanceModels.get(index));
        }
        return originalStateList.toBuilder();
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int childCount, int i) {
        if (this.childOrder != null && i < this.childOrder.length) {
            return this.childOrder[i].intValue();
        }
        Log.w(LOG_TAG, "Child order wasn't updated");
        return i;
    }

    private void adjustChildMarginsAndUpdateLayout() {
        int firstVisibleChildIndex = getFirstVisibleChildIndex();
        if (firstVisibleChildIndex != -1) {
            for (int i = firstVisibleChildIndex + 1; i < getChildCount(); i++) {
                MaterialButton currentButton = getChildButton(i);
                MaterialButton previousButton = getChildButton(i - 1);
                int smallestStrokeWidth = 0;
                if (this.spacing <= 0) {
                    smallestStrokeWidth = Math.min(currentButton.getStrokeWidth(), previousButton.getStrokeWidth());
                    currentButton.setShouldDrawSurfaceColorStroke(true);
                    previousButton.setShouldDrawSurfaceColorStroke(true);
                } else {
                    currentButton.setShouldDrawSurfaceColorStroke(false);
                    previousButton.setShouldDrawSurfaceColorStroke(false);
                }
                LinearLayout.LayoutParams params = buildLayoutParams(currentButton);
                if (getOrientation() == 0) {
                    params.setMarginEnd(0);
                    params.setMarginStart(this.spacing - smallestStrokeWidth);
                    params.topMargin = 0;
                } else {
                    params.bottomMargin = 0;
                    params.topMargin = this.spacing - smallestStrokeWidth;
                    params.setMarginStart(0);
                }
                currentButton.setLayoutParams(params);
            }
            resetChildMargins(firstVisibleChildIndex);
        }
    }

    private void resetChildMargins(int childIndex) {
        if (getChildCount() != 0 && childIndex != -1) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getChildButton(childIndex).getLayoutParams();
            if (getOrientation() == 1) {
                params.topMargin = 0;
                params.bottomMargin = 0;
                return;
            }
            params.setMarginEnd(0);
            params.setMarginStart(0);
            params.leftMargin = 0;
            params.rightMargin = 0;
        }
    }

    /* access modifiers changed from: package-private */
    public void onButtonWidthChanged(MaterialButton button, int increaseSize) {
        int buttonIndex = indexOfChild(button);
        if (buttonIndex >= 0) {
            MaterialButton prevVisibleButton = getPrevVisibleChildButton(buttonIndex);
            MaterialButton nextVisibleButton = getNextVisibleChildButton(buttonIndex);
            if (prevVisibleButton != null || nextVisibleButton != null) {
                if (prevVisibleButton == null) {
                    nextVisibleButton.setDisplayedWidthDecrease(increaseSize);
                }
                if (nextVisibleButton == null) {
                    prevVisibleButton.setDisplayedWidthDecrease(increaseSize);
                }
                if (prevVisibleButton != null && nextVisibleButton != null) {
                    prevVisibleButton.setDisplayedWidthDecrease(increaseSize / 2);
                    nextVisibleButton.setDisplayedWidthDecrease((increaseSize + 1) / 2);
                }
            }
        }
    }

    private void adjustChildSizeChange() {
        int i;
        int i2;
        if (this.buttonSizeChange != null && getChildCount() != 0) {
            int firstVisibleChildIndex = getFirstVisibleChildIndex();
            int lastVisibleChildIndex = getLastVisibleChildIndex();
            int widthIncreaseOnSingleEdge = Integer.MAX_VALUE;
            for (int i3 = firstVisibleChildIndex; i3 <= lastVisibleChildIndex; i3++) {
                if (isChildVisible(i3)) {
                    int widthIncrease = getButtonAllowedWidthIncrease(i3);
                    if (i3 == firstVisibleChildIndex || i3 == lastVisibleChildIndex) {
                        i2 = widthIncrease;
                    } else {
                        i2 = widthIncrease / 2;
                    }
                    widthIncreaseOnSingleEdge = Math.min(widthIncreaseOnSingleEdge, i2);
                }
            }
            for (int i4 = firstVisibleChildIndex; i4 <= lastVisibleChildIndex; i4++) {
                if (isChildVisible(i4)) {
                    getChildButton(i4).setSizeChange(this.buttonSizeChange);
                    MaterialButton childButton = getChildButton(i4);
                    if (i4 == firstVisibleChildIndex || i4 == lastVisibleChildIndex) {
                        i = widthIncreaseOnSingleEdge;
                    } else {
                        i = widthIncreaseOnSingleEdge * 2;
                    }
                    childButton.setWidthChangeMax(i);
                }
            }
        }
    }

    private int getButtonAllowedWidthIncrease(int index) {
        int nextButtonAllowedWidthDecrease = 0;
        if (!isChildVisible(index) || this.buttonSizeChange == null) {
            return 0;
        }
        int widthIncrease = Math.max(0, this.buttonSizeChange.getMaxWidthChange(getChildButton(index).getWidth()));
        MaterialButton prevVisibleButton = getPrevVisibleChildButton(index);
        int prevButtonAllowedWidthDecrease = prevVisibleButton == null ? 0 : prevVisibleButton.getAllowedWidthDecrease();
        MaterialButton nextVisibleButton = getNextVisibleChildButton(index);
        if (nextVisibleButton != null) {
            nextButtonAllowedWidthDecrease = nextVisibleButton.getAllowedWidthDecrease();
        }
        return Math.min(widthIncrease, prevButtonAllowedWidthDecrease + nextButtonAllowedWidthDecrease);
    }

    public void setOrientation(int orientation) {
        if (getOrientation() != orientation) {
            this.childShapesDirty = true;
        }
        super.setOrientation(orientation);
    }

    public StateListSizeChange getButtonSizeChange() {
        return this.buttonSizeChange;
    }

    public void setButtonSizeChange(StateListSizeChange buttonSizeChange2) {
        if (this.buttonSizeChange != buttonSizeChange2) {
            this.buttonSizeChange = buttonSizeChange2;
            adjustChildSizeChange();
            requestLayout();
            invalidate();
        }
    }

    public int getSpacing() {
        return this.spacing;
    }

    public void setSpacing(int spacing2) {
        this.spacing = spacing2;
        invalidate();
        requestLayout();
    }

    public CornerSize getInnerCornerSize() {
        return this.innerCornerSize.getDefaultCornerSize();
    }

    public void setInnerCornerSize(CornerSize cornerSize) {
        this.innerCornerSize = StateListCornerSize.create(cornerSize);
        this.childShapesDirty = true;
        updateChildShapes();
        invalidate();
    }

    public StateListCornerSize getInnerCornerSizeStateList() {
        return this.innerCornerSize;
    }

    public void setInnerCornerSizeStateList(StateListCornerSize cornerSizeStateList) {
        this.innerCornerSize = cornerSizeStateList;
        this.childShapesDirty = true;
        updateChildShapes();
        invalidate();
    }

    public ShapeAppearanceModel getShapeAppearance() {
        if (this.groupStateListShapeAppearance == null) {
            return null;
        }
        return this.groupStateListShapeAppearance.getDefaultShape(true);
    }

    public void setShapeAppearance(ShapeAppearanceModel shapeAppearance) {
        this.groupStateListShapeAppearance = new StateListShapeAppearanceModel.Builder(shapeAppearance).build();
        this.childShapesDirty = true;
        updateChildShapes();
        invalidate();
    }

    public StateListShapeAppearanceModel getStateListShapeAppearance() {
        return this.groupStateListShapeAppearance;
    }

    public void setStateListShapeAppearance(StateListShapeAppearanceModel stateListShapeAppearance) {
        this.groupStateListShapeAppearance = stateListShapeAppearance;
        this.childShapesDirty = true;
        updateChildShapes();
        invalidate();
    }

    /* access modifiers changed from: package-private */
    public MaterialButton getChildButton(int index) {
        return (MaterialButton) getChildAt(index);
    }

    /* access modifiers changed from: package-private */
    public LinearLayout.LayoutParams buildLayoutParams(View child) {
        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            return (LinearLayout.LayoutParams) layoutParams;
        }
        return new LinearLayout.LayoutParams(layoutParams.width, layoutParams.height);
    }

    private int getFirstVisibleChildIndex() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (isChildVisible(i)) {
                return i;
            }
        }
        return -1;
    }

    private int getLastVisibleChildIndex() {
        for (int i = getChildCount() - 1; i >= 0; i--) {
            if (isChildVisible(i)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isChildVisible(int i) {
        return getChildAt(i).getVisibility() != 8;
    }

    private void setGeneratedIdIfNeeded(MaterialButton materialButton) {
        if (materialButton.getId() == -1) {
            materialButton.setId(View.generateViewId());
        }
    }

    private MaterialButton getNextVisibleChildButton(int index) {
        int childCount = getChildCount();
        for (int i = index + 1; i < childCount; i++) {
            if (isChildVisible(i)) {
                return getChildButton(i);
            }
        }
        return null;
    }

    private MaterialButton getPrevVisibleChildButton(int index) {
        for (int i = index - 1; i >= 0; i--) {
            if (isChildVisible(i)) {
                return getChildButton(i);
            }
        }
        return null;
    }

    private void updateChildOrder() {
        SortedMap<MaterialButton, Integer> viewToIndexMap = new TreeMap<>(this.childOrderComparator);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            viewToIndexMap.put(getChildButton(i), Integer.valueOf(i));
        }
        this.childOrder = (Integer[]) viewToIndexMap.values().toArray(new Integer[0]);
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (int i = 0; i < getChildCount(); i++) {
            getChildButton(i).setEnabled(enabled);
        }
    }

    private class PressedStateTracker implements MaterialButton.OnPressedChangeListener {
        private PressedStateTracker() {
        }

        public void onPressedChanged(MaterialButton button, boolean isPressed) {
            MaterialButtonGroup.this.invalidate();
        }
    }
}
