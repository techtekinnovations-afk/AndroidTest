package com.google.android.material.behavior;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

abstract class HideViewOnScrollDelegate {
    /* access modifiers changed from: package-private */
    public abstract <V extends View> int getSize(V v, ViewGroup.MarginLayoutParams marginLayoutParams);

    /* access modifiers changed from: package-private */
    public abstract int getTargetTranslation();

    /* access modifiers changed from: package-private */
    public abstract int getViewEdge();

    /* access modifiers changed from: package-private */
    public abstract <V extends View> ViewPropertyAnimator getViewTranslationAnimator(V v, int i);

    /* access modifiers changed from: package-private */
    public abstract <V extends View> void setAdditionalHiddenOffset(V v, int i, int i2);

    /* access modifiers changed from: package-private */
    public abstract <V extends View> void setViewTranslation(V v, int i);

    HideViewOnScrollDelegate() {
    }
}
