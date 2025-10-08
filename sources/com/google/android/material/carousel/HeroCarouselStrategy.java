package com.google.android.material.carousel;

import android.content.Context;
import android.view.View;
import androidx.core.math.MathUtils;
import androidx.recyclerview.widget.RecyclerView;

public class HeroCarouselStrategy extends CarouselStrategy {
    private static final int[] MEDIUM_COUNTS = {0, 1};
    private static final int[] SMALL_COUNTS = {1};
    private int keylineCount = 0;

    public KeylineState onFirstChildMeasuredWithMargins(Carousel carousel, View child) {
        int[] iArr;
        int[] iArr2;
        Arrangement arrangement;
        int availableSpace = carousel.getContainerHeight();
        if (carousel.isHorizontal()) {
            availableSpace = carousel.getContainerWidth();
        }
        RecyclerView.LayoutParams childLayoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        float childMargins = (float) (childLayoutParams.topMargin + childLayoutParams.bottomMargin);
        float measuredChildSize = (float) (child.getMeasuredWidth() * 2);
        if (carousel.isHorizontal()) {
            childMargins = (float) (childLayoutParams.leftMargin + childLayoutParams.rightMargin);
            measuredChildSize = (float) (child.getMeasuredHeight() * 2);
        }
        float smallChildSizeMin = getSmallItemSizeMin() + childMargins;
        float smallChildSizeMax = Math.max(getSmallItemSizeMax() + childMargins, smallChildSizeMin);
        float targetLargeChildSize = Math.min(measuredChildSize + childMargins, (float) availableSpace);
        float targetSmallChildSize = MathUtils.clamp((measuredChildSize / 3.0f) + childMargins, smallChildSizeMin + childMargins, smallChildSizeMax + childMargins);
        float targetMediumChildSize = (targetLargeChildSize + targetSmallChildSize) / 2.0f;
        int[] smallCounts = SMALL_COUNTS;
        if (((float) availableSpace) < 2.0f * smallChildSizeMin) {
            smallCounts = new int[]{0};
        }
        float minAvailableLargeSpace = ((float) availableSpace) - (((float) CarouselStrategyHelper.maxValue(SMALL_COUNTS)) * smallChildSizeMax);
        float f = minAvailableLargeSpace;
        float f2 = measuredChildSize;
        int[] smallCounts2 = smallCounts;
        int largeCountMin = (int) Math.max(1.0d, Math.floor((double) (minAvailableLargeSpace / targetLargeChildSize)));
        int i = 1;
        int[] largeCounts = new int[((((int) Math.ceil((double) (((float) availableSpace) / targetLargeChildSize))) - largeCountMin) + 1)];
        for (int i2 = 0; i2 < largeCounts.length; i2++) {
            largeCounts[i2] = largeCountMin + i2;
        }
        boolean isCenterAligned = carousel.getCarouselAlignment() == 1;
        float f3 = (float) availableSpace;
        if (isCenterAligned) {
            iArr = doubleCounts(smallCounts2);
        } else {
            iArr = smallCounts2;
        }
        if (isCenterAligned) {
            iArr2 = doubleCounts(MEDIUM_COUNTS);
        } else {
            iArr2 = MEDIUM_COUNTS;
        }
        Arrangement arrangement2 = Arrangement.findLowestCostArrangement(f3, targetSmallChildSize, smallChildSizeMin, smallChildSizeMax, iArr, targetMediumChildSize, iArr2, targetLargeChildSize, largeCounts);
        this.keylineCount = arrangement2.getItemCount();
        if (arrangement2.getItemCount() > carousel.getItemCount()) {
            isCenterAligned = false;
            arrangement = Arrangement.findLowestCostArrangement((float) availableSpace, targetSmallChildSize, smallChildSizeMin, smallChildSizeMax, smallCounts2, targetMediumChildSize, MEDIUM_COUNTS, targetLargeChildSize, largeCounts);
        } else {
            arrangement = arrangement2;
        }
        Context context = child.getContext();
        if (!isCenterAligned) {
            i = 0;
        }
        return CarouselStrategyHelper.createKeylineState(context, childMargins, availableSpace, arrangement, i);
    }

    public boolean shouldRefreshKeylineState(Carousel carousel, int oldItemCount) {
        if (carousel.getCarouselAlignment() == 1) {
            if (oldItemCount < this.keylineCount && carousel.getItemCount() >= this.keylineCount) {
                return true;
            }
            if (oldItemCount < this.keylineCount || carousel.getItemCount() >= this.keylineCount) {
                return false;
            }
            return true;
        }
        return false;
    }
}
