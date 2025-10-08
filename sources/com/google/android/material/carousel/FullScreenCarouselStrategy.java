package com.google.android.material.carousel;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class FullScreenCarouselStrategy extends CarouselStrategy {
    public KeylineState onFirstChildMeasuredWithMargins(Carousel carousel, View child) {
        float childMargins;
        int availableSpace;
        RecyclerView.LayoutParams childLayoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
        if (carousel.isHorizontal()) {
            availableSpace = carousel.getContainerWidth();
            childMargins = (float) (childLayoutParams.leftMargin + childLayoutParams.rightMargin);
        } else {
            availableSpace = carousel.getContainerHeight();
            childMargins = (float) (childLayoutParams.topMargin + childLayoutParams.bottomMargin);
        }
        return CarouselStrategyHelper.createLeftAlignedKeylineState(child.getContext(), childMargins, availableSpace, new Arrangement(0, 0.0f, 0.0f, 0.0f, 0, 0.0f, 0, Math.min(((float) availableSpace) + childMargins, (float) availableSpace), 1, (float) availableSpace));
    }
}
