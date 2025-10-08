package com.google.android.material.search;

import android.animation.ValueAnimator;
import android.widget.ImageButton;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SearchViewAnimationHelper$$ExternalSyntheticLambda8 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ ImageButton f$0;

    public /* synthetic */ SearchViewAnimationHelper$$ExternalSyntheticLambda8(ImageButton imageButton) {
        this.f$0 = imageButton;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f$0.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }
}
