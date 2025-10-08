package com.google.android.material.behavior;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

final class HideLeftViewOnScrollDelegate extends HideViewOnScrollDelegate {
    HideLeftViewOnScrollDelegate() {
    }

    /* access modifiers changed from: package-private */
    public int getViewEdge() {
        return 2;
    }

    /* access modifiers changed from: package-private */
    public <V extends View> int getSize(V child, ViewGroup.MarginLayoutParams paramsCompat) {
        return child.getMeasuredWidth() + paramsCompat.leftMargin;
    }

    /* access modifiers changed from: package-private */
    public <V extends View> void setAdditionalHiddenOffset(V child, int size, int additionalHiddenOffset) {
        child.setTranslationX((float) (size - additionalHiddenOffset));
    }

    /* access modifiers changed from: package-private */
    public int getTargetTranslation() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public <V extends View> void setViewTranslation(V child, int targetTranslation) {
        child.setTranslationX((float) (-targetTranslation));
    }

    /* access modifiers changed from: package-private */
    public <V extends View> ViewPropertyAnimator getViewTranslationAnimator(V child, int targetTranslation) {
        return child.animate().translationX((float) (-targetTranslation));
    }
}
