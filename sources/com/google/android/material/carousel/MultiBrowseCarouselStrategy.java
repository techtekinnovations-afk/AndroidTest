package com.google.android.material.carousel;

import android.view.View;
import androidx.core.math.MathUtils;
import androidx.recyclerview.widget.RecyclerView;

public final class MultiBrowseCarouselStrategy extends CarouselStrategy {
    private static final int[] MEDIUM_COUNTS = {1, 0};
    private static final int[] SMALL_COUNTS = {1};
    private int keylineCount = 0;

    public KeylineState onFirstChildMeasuredWithMargins(Carousel carousel, View child) {
        int[] smallCounts;
        boolean refreshArrangement;
        int carouselSize = carousel.getContainerHeight();
        if (carousel.isHorizontal()) {
            carouselSize = carousel.getContainerWidth();
        }
        RecyclerView.LayoutParams childLayoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        float childMargins = (float) (childLayoutParams.topMargin + childLayoutParams.bottomMargin);
        float measuredChildSize = (float) child.getMeasuredHeight();
        if (carousel.isHorizontal()) {
            childMargins = (float) (childLayoutParams.leftMargin + childLayoutParams.rightMargin);
            measuredChildSize = (float) child.getMeasuredWidth();
        }
        float smallChildSizeMin = getSmallItemSizeMin() + childMargins;
        float smallChildSizeMax = Math.max(getSmallItemSizeMax() + childMargins, smallChildSizeMin);
        float targetLargeChildSize = Math.min(measuredChildSize + childMargins, (float) carouselSize);
        float targetSmallChildSize = MathUtils.clamp((measuredChildSize / 3.0f) + childMargins, smallChildSizeMin + childMargins, smallChildSizeMax + childMargins);
        float targetMediumChildSize = (targetLargeChildSize + targetSmallChildSize) / 2.0f;
        int[] smallCounts2 = SMALL_COUNTS;
        if (((float) carouselSize) <= smallChildSizeMin * 2.0f) {
            smallCounts2 = new int[]{0};
        }
        int[] mediumCounts = MEDIUM_COUNTS;
        if (carousel.getCarouselAlignment() == 1) {
            int[] smallCounts3 = doubleCounts(smallCounts2);
            mediumCounts = doubleCounts(mediumCounts);
            smallCounts = smallCounts3;
        } else {
            smallCounts = smallCounts2;
        }
        float minAvailableLargeSpace = (((float) carouselSize) - (((float) CarouselStrategyHelper.maxValue(mediumCounts)) * targetMediumChildSize)) - (((float) CarouselStrategyHelper.maxValue(smallCounts)) * smallChildSizeMax);
        float targetLargeChildSize2 = targetLargeChildSize;
        float f = measuredChildSize;
        float f2 = minAvailableLargeSpace;
        int largeCountMax = (int) Math.ceil((double) (((float) carouselSize) / targetLargeChildSize2));
        int[] largeCounts = new int[((largeCountMax - ((int) Math.max(1.0d, Math.floor((double) (minAvailableLargeSpace / targetLargeChildSize))))) + 1)];
        for (int i = 0; i < largeCounts.length; i++) {
            largeCounts[i] = largeCountMax - i;
        }
        int[] mediumCounts2 = mediumCounts;
        float targetLargeChildSize3 = targetLargeChildSize2;
        Arrangement arrangement = Arrangement.findLowestCostArrangement((float) carouselSize, targetSmallChildSize, smallChildSizeMin, smallChildSizeMax, smallCounts, targetMediumChildSize, mediumCounts2, targetLargeChildSize3, largeCounts);
        int[] iArr = smallCounts;
        int[] iArr2 = mediumCounts2;
        int[] iArr3 = largeCounts;
        this.keylineCount = arrangement.getItemCount();
        boolean refreshArrangement2 = ensureArrangementFitsItemCount(arrangement, carousel.getItemCount());
        if (arrangement.mediumCount == 0 && arrangement.smallCount == 0 && ((float) carouselSize) > smallChildSizeMin * 2.0f) {
            arrangement.smallCount = 1;
            refreshArrangement = true;
        } else {
            refreshArrangement = refreshArrangement2;
        }
        if (refreshArrangement) {
            Arrangement arrangement2 = arrangement;
            arrangement = Arrangement.findLowestCostArrangement((float) carouselSize, targetSmallChildSize, smallChildSizeMin, smallChildSizeMax, new int[]{arrangement.smallCount}, targetMediumChildSize, new int[]{arrangement.mediumCount}, targetLargeChildSize3, new int[]{arrangement.largeCount});
        } else {
            Arrangement arrangement3 = arrangement;
        }
        return CarouselStrategyHelper.createKeylineState(child.getContext(), childMargins, carouselSize, arrangement, carousel.getCarouselAlignment());
    }

    /* access modifiers changed from: package-private */
    public boolean ensureArrangementFitsItemCount(Arrangement arrangement, int carouselItemCount) {
        int keylineSurplus = arrangement.getItemCount() - carouselItemCount;
        boolean changed = keylineSurplus > 0 && (arrangement.smallCount > 0 || arrangement.mediumCount > 1);
        while (keylineSurplus > 0) {
            if (arrangement.smallCount > 0) {
                arrangement.smallCount--;
            } else if (arrangement.mediumCount > 1) {
                arrangement.mediumCount--;
            }
            keylineSurplus--;
        }
        return changed;
    }

    public boolean shouldRefreshKeylineState(Carousel carousel, int oldItemCount) {
        return (oldItemCount < this.keylineCount && carousel.getItemCount() >= this.keylineCount) || (oldItemCount >= this.keylineCount && carousel.getItemCount() < this.keylineCount);
    }
}
