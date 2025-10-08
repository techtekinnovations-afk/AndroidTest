package androidx.transition;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import io.grpc.internal.GrpcUtil;

public class SidePropagation extends VisibilityPropagation {
    private float mPropagationSpeed = 3.0f;
    private int mSide = 80;

    public void setSide(int side) {
        this.mSide = side;
    }

    public void setPropagationSpeed(float propagationSpeed) {
        if (propagationSpeed != 0.0f) {
            this.mPropagationSpeed = propagationSpeed;
            return;
        }
        throw new IllegalArgumentException("propagationSpeed may not be 0");
    }

    public long getStartDelay(ViewGroup sceneRoot, Transition transition, TransitionValues startValues, TransitionValues endValues) {
        TransitionValues positionValues;
        int directionMultiplier;
        int epicenterX;
        int epicenterX2;
        TransitionValues transitionValues = startValues;
        if (transitionValues == null && endValues == null) {
            return 0;
        }
        Rect epicenter = transition.getEpicenter();
        if (endValues == null || getViewVisibility(transitionValues) == 0) {
            directionMultiplier = -1;
            positionValues = startValues;
        } else {
            directionMultiplier = 1;
            positionValues = endValues;
        }
        int viewCenterX = getViewX(positionValues);
        int viewCenterY = getViewY(positionValues);
        int[] loc = new int[2];
        ViewGroup viewGroup = sceneRoot;
        viewGroup.getLocationOnScreen(loc);
        int left = loc[0] + Math.round(viewGroup.getTranslationX());
        int top = loc[1] + Math.round(viewGroup.getTranslationY());
        int right = viewGroup.getWidth() + left;
        int bottom = viewGroup.getHeight() + top;
        if (epicenter != null) {
            epicenterX2 = epicenter.centerX();
            epicenterX = epicenter.centerY();
        } else {
            epicenterX = (top + bottom) / 2;
            epicenterX2 = (left + right) / 2;
        }
        int[] iArr = loc;
        int epicenterX3 = epicenterX2;
        ViewGroup viewGroup2 = viewGroup;
        int epicenterY = epicenterX;
        int[] iArr2 = iArr;
        float distance = (float) distance(viewGroup2, viewCenterX, viewCenterY, epicenterX3, epicenterY, left, top, right, bottom);
        float distanceFraction = distance / ((float) getMaxDistance(sceneRoot));
        long duration = transition.getDuration();
        if (duration < 0) {
            duration = 300;
        }
        float f = distance;
        int i = viewCenterX;
        return (long) Math.round((((float) (((long) directionMultiplier) * duration)) / this.mPropagationSpeed) * distanceFraction);
    }

    private int distance(View sceneRoot, int viewX, int viewY, int epicenterX, int epicenterY, int left, int top, int right, int bottom) {
        int side = 5;
        boolean isRtl = false;
        if (this.mSide == 8388611) {
            if (sceneRoot.getLayoutDirection() == 1) {
                isRtl = true;
            }
            if (!isRtl) {
                side = 3;
            }
        } else if (this.mSide == 8388613) {
            if (sceneRoot.getLayoutDirection() == 1) {
                isRtl = true;
            }
            if (isRtl) {
                side = 3;
            }
        } else {
            side = this.mSide;
        }
        switch (side) {
            case 3:
                return (right - viewX) + Math.abs(epicenterY - viewY);
            case 5:
                return (viewX - left) + Math.abs(epicenterY - viewY);
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE:
                return (bottom - viewY) + Math.abs(epicenterX - viewX);
            case GrpcUtil.DEFAULT_PORT_PLAINTEXT:
                return (viewY - top) + Math.abs(epicenterX - viewX);
            default:
                return 0;
        }
    }

    private int getMaxDistance(ViewGroup sceneRoot) {
        switch (this.mSide) {
            case 3:
            case 5:
            case GravityCompat.START:
            case GravityCompat.END:
                return sceneRoot.getWidth();
            default:
                return sceneRoot.getHeight();
        }
    }
}
