package com.google.android.material.slider;

import android.view.ViewTreeObserver;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class BaseSlider$$ExternalSyntheticLambda1 implements ViewTreeObserver.OnScrollChangedListener {
    public final /* synthetic */ BaseSlider f$0;

    public /* synthetic */ BaseSlider$$ExternalSyntheticLambda1(BaseSlider baseSlider) {
        this.f$0 = baseSlider;
    }

    public final void onScrollChanged() {
        this.f$0.updateLabels();
    }
}
