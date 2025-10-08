package com.google.android.material.behavior;

import android.view.View;
import android.view.accessibility.AccessibilityManager;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class HideBottomViewOnScrollBehavior$$ExternalSyntheticLambda0 implements AccessibilityManager.TouchExplorationStateChangeListener {
    public final /* synthetic */ HideBottomViewOnScrollBehavior f$0;
    public final /* synthetic */ View f$1;

    public /* synthetic */ HideBottomViewOnScrollBehavior$$ExternalSyntheticLambda0(HideBottomViewOnScrollBehavior hideBottomViewOnScrollBehavior, View view) {
        this.f$0 = hideBottomViewOnScrollBehavior;
        this.f$1 = view;
    }

    public final void onTouchExplorationStateChanged(boolean z) {
        this.f$0.m1633lambda$disableIfTouchExplorationEnabled$0$comgoogleandroidmaterialbehaviorHideBottomViewOnScrollBehavior(this.f$1, z);
    }
}
