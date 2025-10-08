package androidx.constraintlayout.core.motion.key;

import androidx.constraintlayout.core.motion.MotionWidget;
import androidx.constraintlayout.core.motion.utils.FloatRect;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.HashMap;
import java.util.HashSet;

public class MotionKeyPosition extends MotionKey {
    static final int KEY_TYPE = 2;
    static final String NAME = "KeyPosition";
    protected static final float SELECTION_SLOPE = 20.0f;
    public static final int TYPE_CARTESIAN = 0;
    public static final int TYPE_PATH = 1;
    public static final int TYPE_SCREEN = 2;
    public float mAltPercentX = Float.NaN;
    public float mAltPercentY = Float.NaN;
    private float mCalculatedPositionX = Float.NaN;
    private float mCalculatedPositionY = Float.NaN;
    public int mCurveFit = UNSET;
    public int mDrawPath = 0;
    public int mPathMotionArc = UNSET;
    public float mPercentHeight = Float.NaN;
    public float mPercentWidth = Float.NaN;
    public float mPercentX = Float.NaN;
    public float mPercentY = Float.NaN;
    public int mPositionType = 0;
    public String mTransitionEasing = null;

    public MotionKeyPosition() {
        this.mType = 2;
    }

    private void calcScreenPosition(int layoutWidth, int layoutHeight) {
        this.mCalculatedPositionX = (((float) (layoutWidth - 0)) * this.mPercentX) + ((float) (0 / 2));
        this.mCalculatedPositionY = (((float) (layoutHeight - 0)) * this.mPercentX) + ((float) (0 / 2));
    }

    private void calcPathPosition(float startX, float startY, float endX, float endY) {
        float pathVectorX = endX - startX;
        float pathVectorY = endY - startY;
        this.mCalculatedPositionX = (this.mPercentX * pathVectorX) + startX + (this.mPercentY * (-pathVectorY));
        this.mCalculatedPositionY = (this.mPercentX * pathVectorY) + startY + (this.mPercentY * pathVectorX);
    }

    private void calcCartesianPosition(float startX, float startY, float endX, float endY) {
        float pathVectorX = endX - startX;
        float pathVectorY = endY - startY;
        float dxdy = 0.0f;
        float dxdx = Float.isNaN(this.mPercentX) ? 0.0f : this.mPercentX;
        float dydx = Float.isNaN(this.mAltPercentY) ? 0.0f : this.mAltPercentY;
        float dydy = Float.isNaN(this.mPercentY) ? 0.0f : this.mPercentY;
        if (!Float.isNaN(this.mAltPercentX)) {
            dxdy = this.mAltPercentX;
        }
        this.mCalculatedPositionX = (float) ((int) ((pathVectorX * dxdx) + startX + (pathVectorY * dxdy)));
        this.mCalculatedPositionY = (float) ((int) ((pathVectorX * dydx) + startY + (pathVectorY * dydy)));
    }

    /* access modifiers changed from: package-private */
    public float getPositionX() {
        return this.mCalculatedPositionX;
    }

    /* access modifiers changed from: package-private */
    public float getPositionY() {
        return this.mCalculatedPositionY;
    }

    public void positionAttributes(MotionWidget view, FloatRect start, FloatRect end, float x, float y, String[] attribute, float[] value) {
        switch (this.mPositionType) {
            case 1:
                positionPathAttributes(start, end, x, y, attribute, value);
                return;
            case 2:
                positionScreenAttributes(view, start, end, x, y, attribute, value);
                MotionWidget motionWidget = view;
                return;
            default:
                MotionWidget motionWidget2 = view;
                positionCartAttributes(start, end, x, y, attribute, value);
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public void positionPathAttributes(FloatRect start, FloatRect end, float x, float y, String[] attribute, float[] value) {
        float startCenterX = start.centerX();
        float startCenterY = start.centerY();
        float pathVectorX = end.centerX() - startCenterX;
        float pathVectorY = end.centerY() - startCenterY;
        float distance = (float) Math.hypot((double) pathVectorX, (double) pathVectorY);
        if (((double) distance) < 1.0E-4d) {
            System.out.println("distance ~ 0");
            value[0] = 0.0f;
            value[1] = 0.0f;
            return;
        }
        float dx = pathVectorX / distance;
        float dy = pathVectorY / distance;
        float perpendicular = (((y - startCenterY) * dx) - ((x - startCenterX) * dy)) / distance;
        float dist = (((x - startCenterX) * dx) + ((y - startCenterY) * dy)) / distance;
        if (attribute[0] == null) {
            attribute[0] = "percentX";
            attribute[1] = "percentY";
            value[0] = dist;
            value[1] = perpendicular;
        } else if ("percentX".equals(attribute[0])) {
            value[0] = dist;
            value[1] = perpendicular;
        }
    }

    /* access modifiers changed from: package-private */
    public void positionScreenAttributes(MotionWidget view, FloatRect start, FloatRect end, float x, float y, String[] attribute, float[] value) {
        float startCenterX = start.centerX();
        float startCenterY = start.centerY();
        float centerX = end.centerX() - startCenterX;
        float centerY = end.centerY() - startCenterY;
        MotionWidget viewGroup = view.getParent();
        int width = viewGroup.getWidth();
        int height = viewGroup.getHeight();
        if (attribute[0] == null) {
            attribute[0] = "percentX";
            value[0] = x / ((float) width);
            attribute[1] = "percentY";
            value[1] = y / ((float) height);
        } else if ("percentX".equals(attribute[0])) {
            value[0] = x / ((float) width);
            value[1] = y / ((float) height);
        } else {
            value[1] = x / ((float) width);
            value[0] = y / ((float) height);
        }
    }

    /* access modifiers changed from: package-private */
    public void positionCartAttributes(FloatRect start, FloatRect end, float x, float y, String[] attribute, float[] value) {
        float startCenterX = start.centerX();
        float startCenterY = start.centerY();
        float pathVectorX = end.centerX() - startCenterX;
        float pathVectorY = end.centerY() - startCenterY;
        if (attribute[0] == null) {
            attribute[0] = "percentX";
            value[0] = (x - startCenterX) / pathVectorX;
            attribute[1] = "percentY";
            value[1] = (y - startCenterY) / pathVectorY;
        } else if ("percentX".equals(attribute[0])) {
            value[0] = (x - startCenterX) / pathVectorX;
            value[1] = (y - startCenterY) / pathVectorY;
        } else {
            value[1] = (x - startCenterX) / pathVectorX;
            value[0] = (y - startCenterY) / pathVectorY;
        }
    }

    public boolean intersects(int layoutWidth, int layoutHeight, FloatRect start, FloatRect end, float x, float y) {
        calcPosition(layoutWidth, layoutHeight, start.centerX(), start.centerY(), end.centerX(), end.centerY());
        if (Math.abs(x - this.mCalculatedPositionX) >= SELECTION_SLOPE || Math.abs(y - this.mCalculatedPositionY) >= SELECTION_SLOPE) {
            return false;
        }
        return true;
    }

    public MotionKey copy(MotionKey src) {
        super.copy(src);
        MotionKeyPosition k = (MotionKeyPosition) src;
        this.mTransitionEasing = k.mTransitionEasing;
        this.mPathMotionArc = k.mPathMotionArc;
        this.mDrawPath = k.mDrawPath;
        this.mPercentWidth = k.mPercentWidth;
        this.mPercentHeight = Float.NaN;
        this.mPercentX = k.mPercentX;
        this.mPercentY = k.mPercentY;
        this.mAltPercentX = k.mAltPercentX;
        this.mAltPercentY = k.mAltPercentY;
        this.mCalculatedPositionX = k.mCalculatedPositionX;
        this.mCalculatedPositionY = k.mCalculatedPositionY;
        return this;
    }

    public MotionKey clone() {
        return new MotionKeyPosition().copy(this);
    }

    /* access modifiers changed from: package-private */
    public void calcPosition(int layoutWidth, int layoutHeight, float startX, float startY, float endX, float endY) {
        switch (this.mPositionType) {
            case 1:
                calcPathPosition(startX, startY, endX, endY);
                return;
            case 2:
                calcScreenPosition(layoutWidth, layoutHeight);
                return;
            default:
                calcCartesianPosition(startX, startY, endX, endY);
                return;
        }
    }

    public void getAttributeNames(HashSet<String> hashSet) {
    }

    public void addValues(HashMap<String, SplineSet> hashMap) {
    }

    public boolean setValue(int type, int value) {
        switch (type) {
            case 100:
                this.mFramePosition = value;
                return true;
            case TypedValues.PositionType.TYPE_CURVE_FIT /*508*/:
                this.mCurveFit = value;
                return true;
            case TypedValues.PositionType.TYPE_POSITION_TYPE /*510*/:
                this.mPositionType = value;
                return true;
            default:
                return super.setValue(type, value);
        }
    }

    public boolean setValue(int type, float value) {
        switch (type) {
            case TypedValues.PositionType.TYPE_PERCENT_WIDTH /*503*/:
                this.mPercentWidth = value;
                return true;
            case TypedValues.PositionType.TYPE_PERCENT_HEIGHT /*504*/:
                this.mPercentHeight = value;
                return true;
            case TypedValues.PositionType.TYPE_SIZE_PERCENT /*505*/:
                this.mPercentWidth = value;
                this.mPercentHeight = value;
                return true;
            case TypedValues.PositionType.TYPE_PERCENT_X /*506*/:
                this.mPercentX = value;
                return true;
            case TypedValues.PositionType.TYPE_PERCENT_Y /*507*/:
                this.mPercentY = value;
                return true;
            default:
                return super.setValue(type, value);
        }
    }

    public boolean setValue(int type, String value) {
        switch (type) {
            case TypedValues.PositionType.TYPE_TRANSITION_EASING /*501*/:
                this.mTransitionEasing = value.toString();
                return true;
            default:
                return super.setValue(type, value);
        }
    }

    public int getId(String name) {
        return TypedValues.PositionType.getId(name);
    }
}
