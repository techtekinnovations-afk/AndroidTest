package com.google.android.material.carousel;

import android.content.Context;
import com.google.android.material.R;
import com.google.android.material.carousel.KeylineState;

final class CarouselStrategyHelper {
    private CarouselStrategyHelper() {
    }

    static float getExtraSmallSize(Context context) {
        return context.getResources().getDimension(R.dimen.m3_carousel_gone_size);
    }

    static float getSmallSizeMin(Context context) {
        return context.getResources().getDimension(R.dimen.m3_carousel_small_item_size_min);
    }

    static float getSmallSizeMax(Context context) {
        return context.getResources().getDimension(R.dimen.m3_carousel_small_item_size_max);
    }

    static KeylineState createKeylineState(Context context, float childMargins, int availableSpace, Arrangement arrangement, int alignment) {
        if (alignment == 1) {
            return createCenterAlignedKeylineState(context, childMargins, availableSpace, arrangement);
        }
        return createLeftAlignedKeylineState(context, childMargins, availableSpace, arrangement);
    }

    static KeylineState createLeftAlignedKeylineState(Context context, float childHorizontalMargins, int availableSpace, Arrangement arrangement) {
        float f = childHorizontalMargins;
        int i = availableSpace;
        Arrangement arrangement2 = arrangement;
        float extraSmallChildWidth = Math.min(getExtraSmallSize(context) + f, arrangement2.largeSize);
        float largeStartCenterX = addStart(0.0f, arrangement2.largeSize, arrangement2.largeCount);
        float start = updateCurPosition(0.0f, addEnd(largeStartCenterX, arrangement2.largeSize, arrangement2.largeCount), arrangement2.largeSize, arrangement2.largeCount);
        float mediumCenterX = addStart(start, arrangement2.mediumSize, arrangement2.mediumCount);
        float start2 = updateCurPosition(start, mediumCenterX, arrangement2.mediumSize, arrangement2.mediumCount);
        float smallStartCenterX = addStart(start2, arrangement2.smallSize, arrangement2.smallCount);
        float extraSmallTailCenterX = (extraSmallChildWidth / 2.0f) + ((float) i);
        float extraSmallMask = CarouselStrategy.getChildMaskPercentage(extraSmallChildWidth, arrangement2.largeSize, f);
        float smallMask = CarouselStrategy.getChildMaskPercentage(arrangement2.smallSize, arrangement2.largeSize, f);
        float mediumMask = CarouselStrategy.getChildMaskPercentage(arrangement2.mediumSize, arrangement2.largeSize, f);
        KeylineState.Builder addAnchorKeyline = new KeylineState.Builder(arrangement2.largeSize, i).addAnchorKeyline(0.0f - (extraSmallChildWidth / 2.0f), extraSmallMask, extraSmallChildWidth);
        float f2 = extraSmallMask;
        KeylineState.Builder builder = addAnchorKeyline;
        float extraSmallMask2 = f2;
        float smallMask2 = smallMask;
        float smallMask3 = start2;
        float mediumMask2 = mediumMask;
        KeylineState.Builder builder2 = builder.addKeylineRange(largeStartCenterX, 0.0f, arrangement2.largeSize, arrangement2.largeCount, true);
        if (arrangement2.mediumCount > 0) {
            builder2.addKeyline(mediumCenterX, mediumMask2, arrangement2.mediumSize);
        }
        if (arrangement2.smallCount > 0) {
            builder2.addKeylineRange(smallStartCenterX, smallMask2, arrangement2.smallSize, arrangement2.smallCount);
        }
        builder2.addAnchorKeyline(extraSmallTailCenterX, extraSmallMask2, extraSmallChildWidth);
        return builder2.build();
    }

    static KeylineState createCenterAlignedKeylineState(Context context, float childHorizontalMargins, int availableSpace, Arrangement arrangement) {
        float f = childHorizontalMargins;
        int i = availableSpace;
        Arrangement arrangement2 = arrangement;
        float extraSmallChildWidth = Math.min(getExtraSmallSize(context) + f, arrangement2.largeSize);
        float halfSmallStartCenterX = addStart(0.0f, arrangement2.smallSize, arrangement2.smallCount);
        float halfSmallEndCenterX = addEnd(halfSmallStartCenterX, arrangement2.smallSize, (int) Math.floor((double) (((float) arrangement2.smallCount) / 2.0f)));
        float start = updateCurPosition(0.0f, halfSmallEndCenterX, arrangement2.smallSize, arrangement2.smallCount);
        float halfMediumStartCenterX = addStart(start, arrangement2.mediumSize, arrangement2.mediumCount);
        float halfMediumEndCenterX = addEnd(halfMediumStartCenterX, arrangement2.mediumSize, (int) Math.floor((double) (((float) arrangement2.mediumCount) / 2.0f)));
        float start2 = updateCurPosition(start, halfMediumEndCenterX, arrangement2.mediumSize, arrangement2.mediumCount);
        float largeStartCenterX = addStart(start2, arrangement2.largeSize, arrangement2.largeCount);
        float largeEndCenterX = addEnd(largeStartCenterX, arrangement2.largeSize, arrangement2.largeCount);
        float start3 = updateCurPosition(start2, largeEndCenterX, arrangement2.largeSize, arrangement2.largeCount);
        float secondHalfMediumStartCenterX = addStart(start3, arrangement2.mediumSize, arrangement2.mediumCount);
        float secondHalfMediumEndCenterX = addEnd(secondHalfMediumStartCenterX, arrangement2.mediumSize, (int) Math.ceil((double) (((float) arrangement2.mediumCount) / 2.0f)));
        float start4 = updateCurPosition(start3, secondHalfMediumEndCenterX, arrangement2.mediumSize, arrangement2.mediumCount);
        float secondHalfSmallStartCenterX = addStart(start4, arrangement2.smallSize, arrangement2.smallCount);
        float extraSmallTailCenterX = ((float) i) + (extraSmallChildWidth / 2.0f);
        float extraSmallMask = CarouselStrategy.getChildMaskPercentage(extraSmallChildWidth, arrangement2.largeSize, f);
        float f2 = start4;
        float f3 = secondHalfMediumEndCenterX;
        float smallMask = CarouselStrategy.getChildMaskPercentage(arrangement2.smallSize, arrangement2.largeSize, f);
        float f4 = halfSmallEndCenterX;
        float mediumMask = CarouselStrategy.getChildMaskPercentage(arrangement2.mediumSize, arrangement2.largeSize, f);
        float extraSmallHeadCenterX = 0.0f - (extraSmallChildWidth / 2.0f);
        KeylineState.Builder builder = new KeylineState.Builder(arrangement2.largeSize, i).addAnchorKeyline(extraSmallHeadCenterX, extraSmallMask, extraSmallChildWidth);
        if (arrangement2.smallCount > 0) {
            float f5 = extraSmallHeadCenterX;
            float f6 = halfMediumEndCenterX;
            float f7 = largeEndCenterX;
            builder.addKeylineRange(halfSmallStartCenterX, smallMask, arrangement2.smallSize, (int) Math.floor((double) (((float) arrangement2.smallCount) / 2.0f)));
        } else {
            float f8 = halfMediumEndCenterX;
            float f9 = largeEndCenterX;
        }
        if (arrangement2.mediumCount > 0) {
            builder.addKeylineRange(halfMediumStartCenterX, mediumMask, arrangement2.mediumSize, (int) Math.floor((double) (((float) arrangement2.mediumCount) / 2.0f)));
        }
        float f10 = extraSmallTailCenterX;
        KeylineState.Builder builder2 = builder;
        float extraSmallTailCenterX2 = f10;
        float largeMask = arrangement2.largeSize;
        float extraSmallMask2 = extraSmallMask;
        builder2.addKeylineRange(largeStartCenterX, 0.0f, largeMask, arrangement2.largeCount, true);
        if (arrangement2.mediumCount > 0) {
            builder2.addKeylineRange(secondHalfMediumStartCenterX, mediumMask, arrangement2.mediumSize, (int) Math.ceil((double) (((float) arrangement2.mediumCount) / 2.0f)));
        }
        if (arrangement2.smallCount > 0) {
            builder2.addKeylineRange(secondHalfSmallStartCenterX, smallMask, arrangement2.smallSize, (int) Math.ceil((double) (((float) arrangement2.smallCount) / 2.0f)));
        }
        builder2.addAnchorKeyline(extraSmallTailCenterX2, extraSmallMask2, extraSmallChildWidth);
        return builder2.build();
    }

    static int maxValue(int[] array) {
        int largest = Integer.MIN_VALUE;
        for (int j : array) {
            if (j > largest) {
                largest = j;
            }
        }
        return largest;
    }

    static float addStart(float start, float itemSize, int count) {
        if (count > 0) {
            return (itemSize / 2.0f) + start;
        }
        return start;
    }

    static float addEnd(float startKeylinePos, float itemSize, int count) {
        return (((float) Math.max(0, count - 1)) * itemSize) + startKeylinePos;
    }

    static float updateCurPosition(float curPosition, float lastEndKeyline, float itemSize, int count) {
        if (count > 0) {
            return (itemSize / 2.0f) + lastEndKeyline;
        }
        return curPosition;
    }
}
