package com.google.android.material.carousel;

import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.carousel.CarouselStrategy;
import com.google.android.material.carousel.KeylineState;

public final class UncontainedCarouselStrategy extends CarouselStrategy {
    private static final float MEDIUM_LARGE_ITEM_PERCENTAGE_THRESHOLD = 0.85f;

    public KeylineState onFirstChildMeasuredWithMargins(Carousel carousel, View child) {
        float measuredChildSize;
        float measuredChildSize2;
        int availableSpace = carousel.isHorizontal() ? carousel.getContainerWidth() : carousel.getContainerHeight();
        RecyclerView.LayoutParams childLayoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        float childMargins = (float) (childLayoutParams.topMargin + childLayoutParams.bottomMargin);
        float measuredChildSize3 = (float) child.getMeasuredHeight();
        if (carousel.isHorizontal()) {
            float childMargins2 = (float) (childLayoutParams.leftMargin + childLayoutParams.rightMargin);
            measuredChildSize = (float) child.getMeasuredWidth();
            measuredChildSize2 = childMargins2;
        } else {
            measuredChildSize = measuredChildSize3;
            measuredChildSize2 = childMargins;
        }
        float largeChildSize = measuredChildSize + measuredChildSize2;
        float mediumChildSize = CarouselStrategyHelper.getExtraSmallSize(child.getContext()) + measuredChildSize2;
        float xSmallChildSize = CarouselStrategyHelper.getExtraSmallSize(child.getContext()) + measuredChildSize2;
        boolean isCenter = true;
        int largeCount = Math.max(1, (int) Math.floor((double) (((float) availableSpace) / largeChildSize)));
        float remainingSpace = ((float) availableSpace) - (((float) largeCount) * largeChildSize);
        if (carousel.getCarouselAlignment() != 1) {
            isCenter = false;
        }
        if (isCenter) {
            float remainingSpace2 = remainingSpace / 2.0f;
            float mediumChildSize2 = Math.max(Math.min(3.0f * remainingSpace2, largeChildSize), getSmallItemSizeMin() + measuredChildSize2);
            KeylineState createCenterAlignedKeylineState = createCenterAlignedKeylineState(availableSpace, measuredChildSize2, largeChildSize, largeCount, mediumChildSize2, xSmallChildSize, remainingSpace2);
            float f = mediumChildSize2;
            float mediumChildSize3 = largeCount;
            float f2 = largeChildSize;
            float largeChildSize2 = availableSpace;
            return createCenterAlignedKeylineState;
        }
        int largeCount2 = largeCount;
        float largeChildSize3 = largeChildSize;
        int availableSpace2 = availableSpace;
        int mediumCount = 0;
        if (remainingSpace > 0.0f) {
            mediumCount = 1;
        }
        float xSmallChildSize2 = xSmallChildSize;
        float xSmallChildSize3 = calculateMediumChildSize(mediumChildSize, largeChildSize3, remainingSpace);
        KeylineState createLeftAlignedKeylineState = createLeftAlignedKeylineState(child.getContext(), measuredChildSize2, availableSpace2, largeChildSize3, largeCount2, xSmallChildSize3, mediumCount, xSmallChildSize2);
        float f3 = xSmallChildSize3;
        float mediumChildSize4 = xSmallChildSize2;
        return createLeftAlignedKeylineState;
    }

    private float calculateMediumChildSize(float mediumChildSize, float largeChildSize, float remainingSpace) {
        float mediumChildSize2 = Math.max(1.5f * remainingSpace, mediumChildSize);
        float largeItemThreshold = MEDIUM_LARGE_ITEM_PERCENTAGE_THRESHOLD * largeChildSize;
        if (mediumChildSize2 > largeItemThreshold) {
            mediumChildSize2 = Math.max(largeItemThreshold, 1.2f * remainingSpace);
        }
        return Math.min(largeChildSize, mediumChildSize2);
    }

    private KeylineState createCenterAlignedKeylineState(int availableSpace, float childMargins, float largeSize, int largeCount, float mediumSize, float xSmallSize, float remainingSpace) {
        float f = childMargins;
        float f2 = largeSize;
        float f3 = mediumSize;
        float xSmallSize2 = Math.min(xSmallSize, f2);
        float extraSmallMask = getChildMaskPercentage(xSmallSize2, f2, f);
        float mediumMask = getChildMaskPercentage(f3, f2, f);
        float firstMediumCenterX = (0.0f + remainingSpace) - (f3 / 2.0f);
        float start = (f3 / 2.0f) + firstMediumCenterX;
        int i = largeCount;
        float start2 = start + (((float) i) * f2);
        KeylineState.Builder builder = new KeylineState.Builder(f2, availableSpace).addAnchorKeyline((firstMediumCenterX - (f3 / 2.0f)) - (xSmallSize2 / 2.0f), extraSmallMask, xSmallSize2).addKeyline(firstMediumCenterX, mediumMask, f3, false).addKeylineRange((f2 / 2.0f) + start, 0.0f, f2, i, true);
        builder.addKeyline((f3 / 2.0f) + start2, mediumMask, f3, false);
        builder.addAnchorKeyline((xSmallSize2 / 2.0f) + start2 + f3, extraSmallMask, xSmallSize2);
        return builder.build();
    }

    private KeylineState createLeftAlignedKeylineState(Context context, float childMargins, int availableSpace, float largeSize, int largeCount, float mediumSize, int mediumCount, float xSmallSize) {
        float f = childMargins;
        float f2 = largeSize;
        float f3 = mediumSize;
        float xSmallSize2 = Math.min(xSmallSize, f2);
        float leftAnchorSize = Math.max(xSmallSize2, 0.5f * f3);
        float leftAnchorMask = getChildMaskPercentage(leftAnchorSize, f2, f);
        float extraSmallMask = getChildMaskPercentage(xSmallSize2, f2, f);
        float mediumMask = getChildMaskPercentage(f3, f2, f);
        int i = largeCount;
        float start = 0.0f + (((float) i) * f2);
        KeylineState.Builder builder = new KeylineState.Builder(f2, availableSpace).addAnchorKeyline(0.0f - (leftAnchorSize / 2.0f), leftAnchorMask, leftAnchorSize).addKeylineRange(f2 / 2.0f, 0.0f, f2, i, true);
        if (mediumCount > 0) {
            float mediumCenterX = (f3 / 2.0f) + start;
            start += f3;
            builder.addKeyline(mediumCenterX, mediumMask, f3, false);
        }
        builder.addAnchorKeyline((CarouselStrategyHelper.getExtraSmallSize(context) / 2.0f) + start, extraSmallMask, xSmallSize2);
        return builder.build();
    }

    /* access modifiers changed from: package-private */
    public CarouselStrategy.StrategyType getStrategyType() {
        return CarouselStrategy.StrategyType.UNCONTAINED;
    }
}
