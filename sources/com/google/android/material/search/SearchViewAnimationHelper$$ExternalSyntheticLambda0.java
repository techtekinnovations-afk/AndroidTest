package com.google.android.material.search;

import android.animation.ValueAnimator;
import android.graphics.Rect;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SearchViewAnimationHelper$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ SearchViewAnimationHelper f$0;
    public final /* synthetic */ Rect f$1;

    public /* synthetic */ SearchViewAnimationHelper$$ExternalSyntheticLambda0(SearchViewAnimationHelper searchViewAnimationHelper, Rect rect) {
        this.f$0 = searchViewAnimationHelper;
        this.f$1 = rect;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f$0.m1708lambda$addEditTextClipAnimator$6$comgoogleandroidmaterialsearchSearchViewAnimationHelper(this.f$1, valueAnimator);
    }
}
