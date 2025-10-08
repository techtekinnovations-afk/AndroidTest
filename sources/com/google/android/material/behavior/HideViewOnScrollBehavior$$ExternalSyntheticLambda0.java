package com.google.android.material.behavior;

import android.view.View;
import android.view.accessibility.AccessibilityManager;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class HideViewOnScrollBehavior$$ExternalSyntheticLambda0 implements AccessibilityManager.TouchExplorationStateChangeListener {
    public final /* synthetic */ HideViewOnScrollBehavior f$0;
    public final /* synthetic */ View f$1;

    public /* synthetic */ HideViewOnScrollBehavior$$ExternalSyntheticLambda0(HideViewOnScrollBehavior hideViewOnScrollBehavior, View view) {
        this.f$0 = hideViewOnScrollBehavior;
        this.f$1 = view;
    }

    public final void onTouchExplorationStateChanged(boolean z) {
        this.f$0.m1634lambda$disableIfTouchExplorationEnabled$0$comgoogleandroidmaterialbehaviorHideViewOnScrollBehavior(this.f$1, z);
    }
}
