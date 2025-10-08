package com.google.android.material.behavior;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;

final class HideBottomViewOnScrollDelegate extends HideViewOnScrollDelegate {
    HideBottomViewOnScrollDelegate() {
    }

    /* access modifiers changed from: package-private */
    public int getViewEdge() {
        return 1;
    }

    /* access modifiers changed from: package-private */
    public <V extends View> int getSize(V child, ViewGroup.MarginLayoutParams paramsCompat) {
        return child.getMeasuredHeight() + paramsCompat.bottomMargin;
    }

    /* access modifiers changed from: package-private */
    public <V extends View> void setAdditionalHiddenOffset(V child, int size, int additionalHiddenOffset) {
        child.setTranslationY((float) (size + additionalHiddenOffset));
    }

    /* access modifiers changed from: package-private */
    public int getTargetTranslation() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public <V extends View> void setViewTranslation(V child, int targetTranslation) {
        child.setTranslationY((float) targetTranslation);
    }

    /* access modifiers changed from: package-private */
    public <V extends View> ViewPropertyAnimator getViewTranslationAnimator(V child, int targetTranslation) {
        return child.animate().translationY((float) targetTranslation);
    }
}
