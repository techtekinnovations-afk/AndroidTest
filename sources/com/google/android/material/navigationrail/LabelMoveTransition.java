package com.google.android.material.navigationrail;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import androidx.transition.Transition;
import androidx.transition.TransitionValues;

class LabelMoveTransition extends Transition {
    private static final float HORIZONTAL_DISTANCE = -30.0f;
    private static final String LABEL_VISIBILITY = "NavigationRailLabelVisibility";

    LabelMoveTransition() {
    }

    public void captureStartValues(TransitionValues transitionValues) {
        transitionValues.values.put(LABEL_VISIBILITY, Integer.valueOf(transitionValues.view.getVisibility()));
    }

    public void captureEndValues(TransitionValues transitionValues) {
        transitionValues.values.put(LABEL_VISIBILITY, Integer.valueOf(transitionValues.view.getVisibility()));
    }

    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null || startValues.values.get(LABEL_VISIBILITY) == null || endValues.values.get(LABEL_VISIBILITY) == null) {
            return super.createAnimator(sceneRoot, startValues, endValues);
        }
        if (((Integer) startValues.values.get(LABEL_VISIBILITY)).intValue() != 8 || ((Integer) endValues.values.get(LABEL_VISIBILITY)).intValue() != 0) {
            return super.createAnimator(sceneRoot, startValues, endValues);
        }
        View view = endValues.view;
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.addUpdateListener(new LabelMoveTransition$$ExternalSyntheticLambda0(view));
        return animator;
    }
}
