package com.google.android.material.carousel;

import androidx.core.math.MathUtils;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.carousel.CarouselStrategy;
import com.google.android.material.carousel.KeylineState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeylineStateList {
    private static final int NO_INDEX = -1;
    private final KeylineState defaultState;
    private final float endShiftRange;
    private final List<KeylineState> endStateSteps;
    private final float[] endStateStepsInterpolationPoints;
    private final float startShiftRange;
    private final List<KeylineState> startStateSteps;
    private final float[] startStateStepsInterpolationPoints;

    private KeylineStateList(KeylineState defaultState2, List<KeylineState> startStateSteps2, List<KeylineState> endStateSteps2) {
        this.defaultState = defaultState2;
        this.startStateSteps = Collections.unmodifiableList(startStateSteps2);
        this.endStateSteps = Collections.unmodifiableList(endStateSteps2);
        this.startShiftRange = startStateSteps2.get(startStateSteps2.size() - 1).getFirstKeyline().loc - defaultState2.getFirstKeyline().loc;
        this.endShiftRange = defaultState2.getLastKeyline().loc - endStateSteps2.get(endStateSteps2.size() - 1).getLastKeyline().loc;
        this.startStateStepsInterpolationPoints = getStateStepInterpolationPoints(this.startShiftRange, startStateSteps2, true);
        this.endStateStepsInterpolationPoints = getStateStepInterpolationPoints(this.endShiftRange, endStateSteps2, false);
    }

    static KeylineStateList from(Carousel carousel, KeylineState state, float itemMargins, float leftOrTopPadding, float rightOrBottomPadding, CarouselStrategy.StrategyType strategyType) {
        return new KeylineStateList(state, getStateStepsStart(carousel, state, itemMargins, leftOrTopPadding, strategyType), getStateStepsEnd(carousel, state, itemMargins, rightOrBottomPadding, strategyType));
    }

    /* access modifiers changed from: package-private */
    public KeylineState getDefaultState() {
        return this.defaultState;
    }

    /* access modifiers changed from: package-private */
    public KeylineState getStartState() {
        return this.startStateSteps.get(this.startStateSteps.size() - 1);
    }

    /* access modifiers changed from: package-private */
    public KeylineState getEndState() {
        return this.endStateSteps.get(this.endStateSteps.size() - 1);
    }

    public KeylineState getShiftedState(float scrollOffset, float minScrollOffset, float maxScrollOffset) {
        return getShiftedState(scrollOffset, minScrollOffset, maxScrollOffset, false);
    }

    /* access modifiers changed from: package-private */
    public KeylineState getShiftedState(float scrollOffset, float minScrollOffset, float maxScrollOffset, boolean roundToNearestStep) {
        float[] interpolationPoints;
        List<KeylineState> steps;
        float interpolation;
        float startShiftOffset = this.startShiftRange + minScrollOffset;
        float endShiftOffset = maxScrollOffset - this.endShiftRange;
        float startPaddingShift = getStartState().getFirstFocalKeyline().leftOrTopPaddingShift;
        float endPaddingShift = getEndState().getFirstFocalKeyline().rightOrBottomPaddingShift;
        if (this.startShiftRange == startPaddingShift) {
            startShiftOffset += startPaddingShift;
        }
        if (this.endShiftRange == endPaddingShift) {
            endShiftOffset -= endPaddingShift;
        }
        if (scrollOffset < startShiftOffset) {
            interpolation = AnimationUtils.lerp(1.0f, 0.0f, minScrollOffset, startShiftOffset, scrollOffset);
            steps = this.startStateSteps;
            interpolationPoints = this.startStateStepsInterpolationPoints;
        } else if (scrollOffset <= endShiftOffset) {
            return this.defaultState;
        } else {
            interpolation = AnimationUtils.lerp(0.0f, 1.0f, endShiftOffset, maxScrollOffset, scrollOffset);
            steps = this.endStateSteps;
            interpolationPoints = this.endStateStepsInterpolationPoints;
        }
        if (roundToNearestStep) {
            return closestStateStepFromInterpolation(steps, interpolation, interpolationPoints);
        }
        return lerp(steps, interpolation, interpolationPoints);
    }

    private static KeylineState lerp(List<KeylineState> stateSteps, float interpolation, float[] stateStepsInterpolationPoints) {
        float[] stateStepsRange = getStateStepsRange(stateSteps, interpolation, stateStepsInterpolationPoints);
        return KeylineState.lerp(stateSteps.get((int) stateStepsRange[1]), stateSteps.get((int) stateStepsRange[2]), stateStepsRange[0]);
    }

    private static float[] getStateStepsRange(List<KeylineState> stateSteps, float interpolation, float[] stateStepsInterpolationPoints) {
        int numberOfSteps = stateSteps.size();
        float lowerBounds = stateStepsInterpolationPoints[0];
        for (int i = 1; i < numberOfSteps; i++) {
            float upperBounds = stateStepsInterpolationPoints[i];
            if (interpolation <= upperBounds) {
                return new float[]{AnimationUtils.lerp(0.0f, 1.0f, lowerBounds, upperBounds, interpolation), (float) (i - 1), (float) i};
            }
            lowerBounds = upperBounds;
        }
        return new float[]{0.0f, 0.0f, 0.0f};
    }

    private KeylineState closestStateStepFromInterpolation(List<KeylineState> stateSteps, float interpolation, float[] stateStepsInterpolationPoints) {
        float[] stateStepsRange = getStateStepsRange(stateSteps, interpolation, stateStepsInterpolationPoints);
        if (stateStepsRange[0] >= 0.5f) {
            return stateSteps.get((int) stateStepsRange[2]);
        }
        return stateSteps.get((int) stateStepsRange[1]);
    }

    private static float[] getStateStepInterpolationPoints(float shiftRange, List<KeylineState> stateSteps, boolean isShiftingLeft) {
        float distanceShifted;
        int numberOfSteps = stateSteps.size();
        float[] stateStepsInterpolationPoints = new float[numberOfSteps];
        int i = 1;
        while (i < numberOfSteps) {
            KeylineState prevState = stateSteps.get(i - 1);
            KeylineState currState = stateSteps.get(i);
            if (isShiftingLeft) {
                distanceShifted = currState.getFirstKeyline().loc - prevState.getFirstKeyline().loc;
            } else {
                distanceShifted = prevState.getLastKeyline().loc - currState.getLastKeyline().loc;
            }
            stateStepsInterpolationPoints[i] = i == numberOfSteps + -1 ? 1.0f : stateStepsInterpolationPoints[i - 1] + (distanceShifted / shiftRange);
            i++;
        }
        return stateStepsInterpolationPoints;
    }

    private static boolean isFirstFocalItemAtLeftOfContainer(KeylineState state) {
        return state.getFirstFocalKeyline().locOffset - (state.getFirstFocalKeyline().maskedItemSize / 2.0f) >= 0.0f && state.getFirstFocalKeyline() == state.getFirstNonAnchorKeyline();
    }

    private static boolean isLastFocalItemVisibleAtRightOfContainer(Carousel carousel, KeylineState state) {
        int containerSize = carousel.getContainerHeight();
        if (carousel.isHorizontal()) {
            containerSize = carousel.getContainerWidth();
        }
        return state.getLastFocalKeyline().locOffset + (state.getLastFocalKeyline().maskedItemSize / 2.0f) <= ((float) containerSize) && state.getLastFocalKeyline() == state.getLastNonAnchorKeyline();
    }

    private static KeylineState shiftKeylineStateForPadding(KeylineState keylineState, float padding, int carouselSize, boolean leftShift, float childMargins, CarouselStrategy.StrategyType strategyType) {
        switch (strategyType) {
            case CONTAINED:
                return shiftKeylineStateForPaddingContained(keylineState, padding, carouselSize, leftShift, childMargins);
            default:
                return shiftKeylineStateForPaddingUncontained(keylineState, padding, carouselSize, leftShift);
        }
    }

    private static KeylineState shiftKeylineStateForPaddingUncontained(KeylineState keylineState, float padding, int carouselSize, boolean leftShift) {
        float f;
        int i = carouselSize;
        List<KeylineState.Keyline> tmpKeylines = new ArrayList<>(keylineState.getKeylines());
        KeylineState.Builder builder = new KeylineState.Builder(keylineState.getItemSize(), i);
        boolean z = true;
        int unchangingAnchorPosition = leftShift ? 0 : tmpKeylines.size() - 1;
        int j = 0;
        while (j < tmpKeylines.size()) {
            KeylineState.Keyline k = tmpKeylines.get(j);
            if (!k.isAnchor || j != unchangingAnchorPosition) {
                float f2 = k.locOffset;
                float newOffset = leftShift ? f2 + padding : f2 - padding;
                float leftOrTopPadding = leftShift ? padding : 0.0f;
                float rightOrBottomPadding = leftShift ? 0.0f : padding;
                boolean isFocal = (j < keylineState.getFirstFocalKeylineIndex() || j > keylineState.getLastFocalKeylineIndex()) ? false : z;
                float f3 = k.mask;
                float f4 = k.maskedItemSize;
                float f5 = f3;
                boolean z2 = k.isAnchor;
                if (leftShift) {
                    f = Math.max(0.0f, ((k.maskedItemSize / 2.0f) + newOffset) - ((float) i));
                } else {
                    f = Math.min(0.0f, newOffset - (k.maskedItemSize / 2.0f));
                }
                builder.addKeyline(newOffset, f5, f4, isFocal, z2, Math.abs(f), leftOrTopPadding, rightOrBottomPadding);
            } else {
                builder.addKeyline(k.locOffset, k.mask, k.maskedItemSize, false, true, k.cutoff);
            }
            j++;
            z = true;
        }
        return builder.build();
    }

    private static KeylineState shiftKeylineStateForPaddingContained(KeylineState keylineState, float padding, int carouselSize, boolean leftShift, float childMargins) {
        List<KeylineState.Keyline> tmpKeylines = new ArrayList<>(keylineState.getKeylines());
        KeylineState.Builder builder = new KeylineState.Builder(keylineState.getItemSize(), carouselSize);
        float toDecreaseBy = padding / ((float) keylineState.getNumberOfNonAnchorKeylines());
        float nextOffset = 0.0f;
        if (leftShift) {
            nextOffset = padding;
        }
        float nextOffset2 = nextOffset;
        int j = 0;
        while (j < tmpKeylines.size()) {
            KeylineState.Keyline k = tmpKeylines.get(j);
            if (k.isAnchor) {
                builder.addKeyline(k.locOffset, k.mask, k.maskedItemSize, false, true, k.cutoff);
            } else {
                boolean isFocal = j >= keylineState.getFirstFocalKeylineIndex() && j <= keylineState.getLastFocalKeylineIndex();
                float maskedItemSize = k.maskedItemSize - toDecreaseBy;
                float mask = CarouselStrategy.getChildMaskPercentage(maskedItemSize, keylineState.getItemSize(), childMargins);
                float locOffset = (maskedItemSize / 2.0f) + nextOffset2;
                float actualPaddingShift = Math.abs(locOffset - k.locOffset);
                float f = k.cutoff;
                float f2 = 0.0f;
                float f3 = 0.0f;
                if (leftShift) {
                    f2 = actualPaddingShift;
                }
                if (!leftShift) {
                    f3 = actualPaddingShift;
                }
                builder.addKeyline(locOffset, mask, maskedItemSize, isFocal, false, f, f2, f3);
                nextOffset2 += maskedItemSize;
            }
            j++;
        }
        return builder.build();
    }

    private static List<KeylineState> getStateStepsStart(Carousel carousel, KeylineState defaultState2, float itemMargins, float leftOrTopPaddingForKeylineShift, CarouselStrategy.StrategyType strategyType) {
        float f;
        int carouselSize;
        int carouselSize2;
        KeylineState shifted;
        int i;
        KeylineState keylineState = defaultState2;
        List<KeylineState> steps = new ArrayList<>();
        steps.add(keylineState);
        int firstNonAnchorKeylineIndex = findFirstNonAnchorKeylineIndex(keylineState);
        int carouselSize3 = carousel.isHorizontal() ? carousel.getContainerWidth() : carousel.getContainerHeight();
        int i2 = 0;
        if (isFirstFocalItemAtLeftOfContainer(keylineState)) {
            f = 0.0f;
            carouselSize = carouselSize3;
        } else if (firstNonAnchorKeylineIndex == -1) {
            f = 0.0f;
            carouselSize = carouselSize3;
        } else {
            int start = firstNonAnchorKeylineIndex;
            int numberOfSteps = keylineState.getFirstFocalKeylineIndex() - start;
            float originalStart = keylineState.getFirstKeyline().locOffset - (keylineState.getFirstKeyline().maskedItemSize / 2.0f);
            if (numberOfSteps > 0 || keylineState.getFirstFocalKeyline().cutoff <= 0.0f) {
                float cutoffs = 0.0f;
                int i3 = 0;
                while (i3 < numberOfSteps) {
                    KeylineState prevStepState = steps.get(steps.size() - 1);
                    int itemOrigIndex = start + i3;
                    int dstIndex = keylineState.getKeylines().size() - 1;
                    float cutoffs2 = cutoffs + keylineState.getKeylines().get(itemOrigIndex).cutoff;
                    if (itemOrigIndex - 1 >= 0) {
                        dstIndex = findFirstIndexAfterLastFocalKeylineWithMask(prevStepState, keylineState.getKeylines().get(itemOrigIndex - 1).mask) - 1;
                    }
                    KeylineState shifted2 = moveKeylineAndCreateKeylineState(prevStepState, firstNonAnchorKeylineIndex, dstIndex, originalStart + cutoffs2, (keylineState.getFirstFocalKeylineIndex() - i3) - 1, (keylineState.getLastFocalKeylineIndex() - i3) - 1, carouselSize3);
                    int i4 = i2;
                    if (i3 != numberOfSteps - 1 || leftOrTopPaddingForKeylineShift <= i4) {
                        carouselSize2 = carouselSize3;
                        i = i3;
                        KeylineState shifted3 = shifted2;
                        int i5 = itemOrigIndex;
                        shifted = shifted3;
                    } else {
                        carouselSize2 = carouselSize3;
                        i = i3;
                        KeylineState shifted4 = shifted2;
                        int i6 = itemOrigIndex;
                        shifted = shiftKeylineStateForPadding(shifted4, leftOrTopPaddingForKeylineShift, carouselSize2, true, itemMargins, strategyType);
                    }
                    steps.add(shifted);
                    i3 = i + 1;
                    carouselSize3 = carouselSize2;
                    cutoffs = cutoffs2;
                    i2 = i4;
                }
                return steps;
            }
            steps.add(shiftKeylinesAndCreateKeylineState(keylineState, originalStart + keylineState.getFirstFocalKeyline().cutoff + leftOrTopPaddingForKeylineShift, carouselSize3));
            return steps;
        }
        if (leftOrTopPaddingForKeylineShift > f) {
            int carouselSize4 = carouselSize;
            int i7 = carouselSize4;
            steps.add(shiftKeylineStateForPadding(keylineState, leftOrTopPaddingForKeylineShift, carouselSize4, true, itemMargins, strategyType));
        }
        return steps;
    }

    private static List<KeylineState> getStateStepsEnd(Carousel carousel, KeylineState defaultState2, float itemMargins, float rightOrBottomPaddingForKeylineShift, CarouselStrategy.StrategyType strategyType) {
        float f;
        int carouselSize;
        int carouselSize2;
        KeylineState shifted;
        int i;
        KeylineState keylineState = defaultState2;
        List<KeylineState> steps = new ArrayList<>();
        steps.add(keylineState);
        int lastNonAnchorKeylineIndex = findLastNonAnchorKeylineIndex(keylineState);
        int carouselSize3 = carousel.isHorizontal() ? carousel.getContainerWidth() : carousel.getContainerHeight();
        int i2 = 0;
        if (isLastFocalItemVisibleAtRightOfContainer(carousel, defaultState2)) {
            f = 0.0f;
            carouselSize = carouselSize3;
        } else if (lastNonAnchorKeylineIndex == -1) {
            f = 0.0f;
            carouselSize = carouselSize3;
        } else {
            int end = lastNonAnchorKeylineIndex;
            int numberOfSteps = end - keylineState.getLastFocalKeylineIndex();
            float originalStart = keylineState.getFirstKeyline().locOffset - (keylineState.getFirstKeyline().maskedItemSize / 2.0f);
            if (numberOfSteps > 0 || keylineState.getLastFocalKeyline().cutoff <= 0.0f) {
                float cutoffs = 0.0f;
                int i3 = 0;
                while (i3 < numberOfSteps) {
                    KeylineState prevStepState = steps.get(steps.size() - 1);
                    int itemOrigIndex = end - i3;
                    float cutoffs2 = cutoffs + keylineState.getKeylines().get(itemOrigIndex).cutoff;
                    int dstIndex = 0;
                    if (itemOrigIndex + 1 < keylineState.getKeylines().size()) {
                        dstIndex = findLastIndexBeforeFirstFocalKeylineWithMask(prevStepState, keylineState.getKeylines().get(itemOrigIndex + 1).mask) + 1;
                    }
                    KeylineState shifted2 = moveKeylineAndCreateKeylineState(prevStepState, lastNonAnchorKeylineIndex, dstIndex, originalStart - cutoffs2, keylineState.getFirstFocalKeylineIndex() + i3 + 1, keylineState.getLastFocalKeylineIndex() + i3 + 1, carouselSize3);
                    int i4 = i2;
                    if (i3 != numberOfSteps - 1 || rightOrBottomPaddingForKeylineShift <= i4) {
                        carouselSize2 = carouselSize3;
                        i = i3;
                        KeylineState shifted3 = shifted2;
                        int i5 = itemOrigIndex;
                        shifted = shifted3;
                    } else {
                        carouselSize2 = carouselSize3;
                        i = i3;
                        KeylineState shifted4 = shifted2;
                        int i6 = itemOrigIndex;
                        shifted = shiftKeylineStateForPadding(shifted4, rightOrBottomPaddingForKeylineShift, carouselSize2, false, itemMargins, strategyType);
                    }
                    steps.add(shifted);
                    i3 = i + 1;
                    carouselSize3 = carouselSize2;
                    cutoffs = cutoffs2;
                    i2 = i4;
                }
                return steps;
            }
            steps.add(shiftKeylinesAndCreateKeylineState(keylineState, (originalStart - keylineState.getLastFocalKeyline().cutoff) - rightOrBottomPaddingForKeylineShift, carouselSize3));
            return steps;
        }
        if (rightOrBottomPaddingForKeylineShift > f) {
            int carouselSize4 = carouselSize;
            int i7 = carouselSize4;
            steps.add(shiftKeylineStateForPadding(keylineState, rightOrBottomPaddingForKeylineShift, carouselSize4, false, itemMargins, strategyType));
        }
        return steps;
    }

    private static KeylineState shiftKeylinesAndCreateKeylineState(KeylineState state, float startOffset, int carouselSize) {
        return moveKeylineAndCreateKeylineState(state, 0, 0, startOffset, state.getFirstFocalKeylineIndex(), state.getLastFocalKeylineIndex(), carouselSize);
    }

    private static KeylineState moveKeylineAndCreateKeylineState(KeylineState state, int keylineSrcIndex, int keylineDstIndex, float startOffset, int newFirstFocalIndex, int newLastFocalIndex, int carouselSize) {
        boolean isFocal;
        List<KeylineState.Keyline> tmpKeylines = new ArrayList<>(state.getKeylines());
        tmpKeylines.add(keylineDstIndex, tmpKeylines.remove(keylineSrcIndex));
        KeylineState.Builder builder = new KeylineState.Builder(state.getItemSize(), carouselSize);
        float startOffset2 = startOffset;
        for (int j = 0; j < tmpKeylines.size(); j++) {
            KeylineState.Keyline k = tmpKeylines.get(j);
            float offset = (k.maskedItemSize / 2.0f) + startOffset2;
            if (j < newFirstFocalIndex) {
                int i = newLastFocalIndex;
            } else if (j <= newLastFocalIndex) {
                isFocal = true;
                builder.addKeyline(offset, k.mask, k.maskedItemSize, isFocal, k.isAnchor, k.cutoff);
                startOffset2 += k.maskedItemSize;
            }
            isFocal = false;
            builder.addKeyline(offset, k.mask, k.maskedItemSize, isFocal, k.isAnchor, k.cutoff);
            startOffset2 += k.maskedItemSize;
        }
        int i2 = newFirstFocalIndex;
        return builder.build();
    }

    private static int findFirstIndexAfterLastFocalKeylineWithMask(KeylineState state, float mask) {
        for (int i = state.getLastFocalKeylineIndex(); i < state.getKeylines().size(); i++) {
            if (mask == state.getKeylines().get(i).mask) {
                return i;
            }
        }
        return state.getKeylines().size() - 1;
    }

    private static int findLastIndexBeforeFirstFocalKeylineWithMask(KeylineState state, float mask) {
        for (int i = state.getFirstFocalKeylineIndex() - 1; i >= 0; i--) {
            if (mask == state.getKeylines().get(i).mask) {
                return i;
            }
        }
        return 0;
    }

    private static int findFirstNonAnchorKeylineIndex(KeylineState state) {
        for (int i = 0; i < state.getKeylines().size(); i++) {
            if (!state.getKeylines().get(i).isAnchor) {
                return i;
            }
        }
        return -1;
    }

    private static int findLastNonAnchorKeylineIndex(KeylineState state) {
        for (int i = state.getKeylines().size() - 1; i >= 0; i--) {
            if (!state.getKeylines().get(i).isAnchor) {
                return i;
            }
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public Map<Integer, KeylineState> getKeylineStateForPositionMap(int itemCount, int minHorizontalScroll, int maxHorizontalScroll, boolean isRTL) {
        int i = itemCount;
        float itemSize = this.defaultState.getItemSize();
        Map<Integer, KeylineState> keylineStates = new HashMap<>();
        int endStepsIndex = 0;
        int startStepsIndex = 0;
        for (int i2 = 0; i2 < i; i2++) {
            int position = isRTL ? (i - i2) - 1 : i2;
            if (((float) position) * itemSize * ((float) (isRTL ? -1 : 1)) > ((float) maxHorizontalScroll) - this.endShiftRange || i2 >= i - this.endStateSteps.size()) {
                keylineStates.put(Integer.valueOf(position), this.endStateSteps.get(MathUtils.clamp(endStepsIndex, 0, this.endStateSteps.size() - 1)));
                endStepsIndex++;
            }
        }
        int i3 = maxHorizontalScroll;
        for (int i4 = i - 1; i4 >= 0; i4--) {
            int position2 = isRTL ? (i - i4) - 1 : i4;
            if (((float) position2) * itemSize * ((float) (isRTL ? -1 : 1)) < ((float) minHorizontalScroll) + this.startShiftRange || i4 < this.startStateSteps.size()) {
                keylineStates.put(Integer.valueOf(position2), this.startStateSteps.get(MathUtils.clamp(startStepsIndex, 0, this.startStateSteps.size() - 1)));
                startStepsIndex++;
            }
        }
        int i5 = minHorizontalScroll;
        return keylineStates;
    }
}
