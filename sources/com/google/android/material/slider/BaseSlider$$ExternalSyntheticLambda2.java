package com.google.android.material.slider;

import android.view.ViewTreeObserver;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class BaseSlider$$ExternalSyntheticLambda2 implements ViewTreeObserver.OnGlobalLayoutListener {
    public final /* synthetic */ BaseSlider f$0;

    public /* synthetic */ BaseSlider$$ExternalSyntheticLambda2(BaseSlider baseSlider) {
        this.f$0 = baseSlider;
    }

    public final void onGlobalLayout() {
        this.f$0.updateLabels();
    }
}
