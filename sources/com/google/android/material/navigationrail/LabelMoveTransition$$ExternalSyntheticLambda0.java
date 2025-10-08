package com.google.android.material.navigationrail;

import android.animation.ValueAnimator;
import android.view.View;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class LabelMoveTransition$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ View f$0;

    public /* synthetic */ LabelMoveTransition$$ExternalSyntheticLambda0(View view) {
        this.f$0 = view;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f$0.setTranslationX((1.0f - valueAnimator.getAnimatedFraction()) * LabelMoveTransition.HORIZONTAL_DISTANCE);
    }
}
