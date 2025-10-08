package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.key.MotionKeyPosition;
import androidx.constraintlayout.core.motion.utils.Easing;
import java.util.Arrays;
import java.util.HashMap;

public class MotionPaths implements Comparable<MotionPaths> {
    public static final int CARTESIAN = 0;
    public static final boolean DEBUG = false;
    static final int OFF_HEIGHT = 4;
    static final int OFF_PATH_ROTATE = 5;
    static final int OFF_POSITION = 0;
    static final int OFF_WIDTH = 3;
    static final int OFF_X = 1;
    static final int OFF_Y = 2;
    public static final boolean OLD_WAY = false;
    public static final int PERPENDICULAR = 1;
    public static final int SCREEN = 2;
    public static final String TAG = "MotionPaths";
    static String[] sNames = {"position", "x", "y", "width", "height", "pathRotate"};
    int mAnimateCircleAngleTo;
    String mAnimateRelativeTo = null;
    HashMap<String, CustomVariable> mCustomAttributes = new HashMap<>();
    int mDrawPath = 0;
    float mHeight;
    public String mId;
    Easing mKeyFrameEasing;
    int mMode = 0;
    int mPathMotionArc = -1;
    float mPathRotate = Float.NaN;
    float mPosition;
    float mProgress = Float.NaN;
    float mRelativeAngle = Float.NaN;
    Motion mRelativeToController = null;
    double[] mTempDelta = new double[18];
    double[] mTempValue = new double[18];
    float mTime;
    float mWidth;
    float mX;
    float mY;

    public MotionPaths() {
    }

    /* access modifiers changed from: package-private */
    public void initCartesian(MotionKeyPosition c, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        MotionKeyPosition motionKeyPosition = c;
        MotionPaths motionPaths = startTimePoint;
        MotionPaths motionPaths2 = endTimePoint;
        float position = ((float) motionKeyPosition.mFramePosition) / 100.0f;
        this.mTime = position;
        this.mDrawPath = motionKeyPosition.mDrawPath;
        float scaleWidth = Float.isNaN(motionKeyPosition.mPercentWidth) ? position : motionKeyPosition.mPercentWidth;
        float scaleHeight = Float.isNaN(motionKeyPosition.mPercentHeight) ? position : motionKeyPosition.mPercentHeight;
        float scaleX = motionPaths2.mWidth - motionPaths.mWidth;
        float scaleY = motionPaths2.mHeight - motionPaths.mHeight;
        this.mPosition = this.mTime;
        float path = position;
        float startCenterX = motionPaths.mX + (motionPaths.mWidth / 2.0f);
        float startCenterY = motionPaths.mY + (motionPaths.mHeight / 2.0f);
        float pathVectorX = (motionPaths2.mX + (motionPaths2.mWidth / 2.0f)) - startCenterX;
        float pathVectorY = (motionPaths2.mY + (motionPaths2.mHeight / 2.0f)) - startCenterY;
        this.mX = (float) ((int) ((motionPaths.mX + (pathVectorX * path)) - ((scaleX * scaleWidth) / 2.0f)));
        this.mY = (float) ((int) ((motionPaths.mY + (pathVectorY * path)) - ((scaleY * scaleHeight) / 2.0f)));
        this.mWidth = (float) ((int) (motionPaths.mWidth + (scaleX * scaleWidth)));
        this.mHeight = (float) ((int) (motionPaths.mHeight + (scaleY * scaleHeight)));
        float dxdx = Float.isNaN(motionKeyPosition.mPercentX) ? position : motionKeyPosition.mPercentX;
        float dxdy = 0.0f;
        float dydx = Float.isNaN(motionKeyPosition.mAltPercentY) ? 0.0f : motionKeyPosition.mAltPercentY;
        float dydy = Float.isNaN(motionKeyPosition.mPercentY) ? position : motionKeyPosition.mPercentY;
        if (!Float.isNaN(motionKeyPosition.mAltPercentX)) {
            dxdy = motionKeyPosition.mAltPercentX;
        }
        this.mMode = 0;
        this.mX = (float) ((int) (((motionPaths.mX + (pathVectorX * dxdx)) + (pathVectorY * dxdy)) - ((scaleX * scaleWidth) / 2.0f)));
        this.mY = (float) ((int) (((motionPaths.mY + (pathVectorX * dydx)) + (pathVectorY * dydy)) - ((scaleY * scaleHeight) / 2.0f)));
        this.mKeyFrameEasing = Easing.getInterpolator(motionKeyPosition.mTransitionEasing);
        this.mPathMotionArc = motionKeyPosition.mPathMotionArc;
    }

    public MotionPaths(int parentWidth, int parentHeight, MotionKeyPosition c, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        if (startTimePoint.mAnimateRelativeTo != null) {
            initPolar(parentWidth, parentHeight, c, startTimePoint, endTimePoint);
            return;
        }
        switch (c.mPositionType) {
            case 1:
                MotionPaths endTimePoint2 = endTimePoint;
                MotionPaths startTimePoint2 = startTimePoint;
                MotionKeyPosition c2 = c;
                int i = parentHeight;
                int parentHeight2 = parentWidth;
                initPath(c2, startTimePoint2, endTimePoint2);
                return;
            case 2:
                initScreen(parentWidth, parentHeight, c, startTimePoint, endTimePoint);
                MotionPaths motionPaths = endTimePoint;
                MotionPaths endTimePoint3 = startTimePoint;
                MotionKeyPosition motionKeyPosition = c;
                int i2 = parentHeight;
                int parentHeight3 = parentWidth;
                return;
            default:
                MotionPaths endTimePoint4 = endTimePoint;
                MotionPaths startTimePoint3 = startTimePoint;
                MotionKeyPosition c3 = c;
                int i3 = parentHeight;
                int parentHeight4 = parentWidth;
                initCartesian(c3, startTimePoint3, endTimePoint4);
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public void initPolar(int parentWidth, int parentHeight, MotionKeyPosition c, MotionPaths s, MotionPaths e) {
        float f;
        float position = ((float) c.mFramePosition) / 100.0f;
        this.mTime = position;
        this.mDrawPath = c.mDrawPath;
        this.mMode = c.mPositionType;
        float scaleWidth = Float.isNaN(c.mPercentWidth) ? position : c.mPercentWidth;
        float scaleHeight = Float.isNaN(c.mPercentHeight) ? position : c.mPercentHeight;
        float scaleX = e.mWidth - s.mWidth;
        float scaleY = e.mHeight - s.mHeight;
        this.mPosition = this.mTime;
        this.mWidth = (float) ((int) (s.mWidth + (scaleX * scaleWidth)));
        this.mHeight = (float) ((int) (s.mHeight + (scaleY * scaleHeight)));
        float f2 = 1.0f - position;
        float f3 = position;
        switch (c.mPositionType) {
            case 1:
                this.mX = ((Float.isNaN(c.mPercentX) ? position : c.mPercentX) * (e.mX - s.mX)) + s.mX;
                this.mY = ((Float.isNaN(c.mPercentY) ? position : c.mPercentY) * (e.mY - s.mY)) + s.mY;
                break;
            case 2:
                if (Float.isNaN(c.mPercentX)) {
                    f = ((e.mX - s.mX) * position) + s.mX;
                } else {
                    f = c.mPercentX * Math.min(scaleHeight, scaleWidth);
                }
                this.mX = f;
                this.mY = Float.isNaN(c.mPercentY) ? ((e.mY - s.mY) * position) + s.mY : c.mPercentY;
                break;
            default:
                this.mX = ((Float.isNaN(c.mPercentX) ? position : c.mPercentX) * (e.mX - s.mX)) + s.mX;
                this.mY = ((Float.isNaN(c.mPercentY) ? position : c.mPercentY) * (e.mY - s.mY)) + s.mY;
                break;
        }
        this.mAnimateRelativeTo = s.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(c.mTransitionEasing);
        this.mPathMotionArc = c.mPathMotionArc;
    }

    public void setupRelative(Motion mc, MotionPaths relative) {
        double dx = (double) (((this.mX + (this.mWidth / 2.0f)) - relative.mX) - (relative.mWidth / 2.0f));
        double dy = (double) (((this.mY + (this.mHeight / 2.0f)) - relative.mY) - (relative.mHeight / 2.0f));
        this.mRelativeToController = mc;
        this.mX = (float) Math.hypot(dy, dx);
        if (Float.isNaN(this.mRelativeAngle)) {
            this.mY = (float) (Math.atan2(dy, dx) + 1.5707963267948966d);
        } else {
            this.mY = (float) Math.toRadians((double) this.mRelativeAngle);
        }
    }

    /* access modifiers changed from: package-private */
    public void initScreen(int parentWidth, int parentHeight, MotionKeyPosition c, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        int parentWidth2;
        MotionKeyPosition motionKeyPosition = c;
        MotionPaths motionPaths = startTimePoint;
        MotionPaths motionPaths2 = endTimePoint;
        float position = ((float) motionKeyPosition.mFramePosition) / 100.0f;
        this.mTime = position;
        this.mDrawPath = motionKeyPosition.mDrawPath;
        float scaleWidth = Float.isNaN(motionKeyPosition.mPercentWidth) ? position : motionKeyPosition.mPercentWidth;
        float scaleHeight = Float.isNaN(motionKeyPosition.mPercentHeight) ? position : motionKeyPosition.mPercentHeight;
        float scaleX = motionPaths2.mWidth - motionPaths.mWidth;
        float scaleY = motionPaths2.mHeight - motionPaths.mHeight;
        this.mPosition = this.mTime;
        float path = position;
        float startCenterX = motionPaths.mX + (motionPaths.mWidth / 2.0f);
        float startCenterY = motionPaths.mY + (motionPaths.mHeight / 2.0f);
        float endCenterX = motionPaths2.mX + (motionPaths2.mWidth / 2.0f);
        float endCenterY = motionPaths2.mY + (motionPaths2.mHeight / 2.0f);
        this.mX = (float) ((int) ((motionPaths.mX + ((endCenterX - startCenterX) * path)) - ((scaleX * scaleWidth) / 2.0f)));
        this.mY = (float) ((int) ((motionPaths.mY + ((endCenterY - startCenterY) * path)) - ((scaleY * scaleHeight) / 2.0f)));
        this.mWidth = (float) ((int) (motionPaths.mWidth + (scaleX * scaleWidth)));
        this.mHeight = (float) ((int) (motionPaths.mHeight + (scaleY * scaleHeight)));
        this.mMode = 2;
        if (!Float.isNaN(motionKeyPosition.mPercentX)) {
            parentWidth2 = parentWidth - ((int) this.mWidth);
            this.mX = (float) ((int) (((float) parentWidth2) * motionKeyPosition.mPercentX));
        } else {
            parentWidth2 = parentWidth;
        }
        if (!Float.isNaN(motionKeyPosition.mPercentY)) {
            int i = parentWidth2;
            this.mY = (float) ((int) (((float) (parentHeight - ((int) this.mHeight))) * motionKeyPosition.mPercentY));
        } else {
            int i2 = parentHeight;
        }
        this.mAnimateRelativeTo = this.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(motionKeyPosition.mTransitionEasing);
        this.mPathMotionArc = motionKeyPosition.mPathMotionArc;
    }

    /* access modifiers changed from: package-private */
    public void initPath(MotionKeyPosition c, MotionPaths startTimePoint, MotionPaths endTimePoint) {
        MotionKeyPosition motionKeyPosition = c;
        MotionPaths motionPaths = startTimePoint;
        MotionPaths motionPaths2 = endTimePoint;
        float position = ((float) motionKeyPosition.mFramePosition) / 100.0f;
        this.mTime = position;
        this.mDrawPath = motionKeyPosition.mDrawPath;
        float scaleWidth = Float.isNaN(motionKeyPosition.mPercentWidth) ? position : motionKeyPosition.mPercentWidth;
        float scaleHeight = Float.isNaN(motionKeyPosition.mPercentHeight) ? position : motionKeyPosition.mPercentHeight;
        float scaleX = motionPaths2.mWidth - motionPaths.mWidth;
        float scaleY = motionPaths2.mHeight - motionPaths.mHeight;
        this.mPosition = this.mTime;
        float path = Float.isNaN(motionKeyPosition.mPercentX) ? position : motionKeyPosition.mPercentX;
        float startCenterX = motionPaths.mX + (motionPaths.mWidth / 2.0f);
        float startCenterY = motionPaths.mY + (motionPaths.mHeight / 2.0f);
        float pathVectorX = (motionPaths2.mX + (motionPaths2.mWidth / 2.0f)) - startCenterX;
        float pathVectorY = (motionPaths2.mY + (motionPaths2.mHeight / 2.0f)) - startCenterY;
        float f = position;
        this.mX = (float) ((int) ((motionPaths.mX + (pathVectorX * path)) - ((scaleX * scaleWidth) / 2.0f)));
        this.mY = (float) ((int) ((motionPaths.mY + (pathVectorY * path)) - ((scaleY * scaleHeight) / 2.0f)));
        this.mWidth = (float) ((int) (motionPaths.mWidth + (scaleX * scaleWidth)));
        this.mHeight = (float) ((int) (motionPaths.mHeight + (scaleY * scaleHeight)));
        float perpendicular = Float.isNaN(motionKeyPosition.mPercentY) ? 0.0f : motionKeyPosition.mPercentY;
        this.mMode = 1;
        this.mX = (float) ((int) ((motionPaths.mX + (pathVectorX * path)) - ((scaleX * scaleWidth) / 2.0f)));
        this.mY = (float) ((int) ((motionPaths.mY + (pathVectorY * path)) - ((scaleY * scaleHeight) / 2.0f)));
        this.mX += (-pathVectorY) * perpendicular;
        this.mY += pathVectorX * perpendicular;
        this.mAnimateRelativeTo = this.mAnimateRelativeTo;
        this.mKeyFrameEasing = Easing.getInterpolator(motionKeyPosition.mTransitionEasing);
        this.mPathMotionArc = motionKeyPosition.mPathMotionArc;
    }

    private static float xRotate(float sin, float cos, float cx, float cy, float x, float y) {
        return (((x - cx) * cos) - ((y - cy) * sin)) + cx;
    }

    private static float yRotate(float sin, float cos, float cx, float cy, float x, float y) {
        return ((x - cx) * sin) + ((y - cy) * cos) + cy;
    }

    private boolean diff(float a, float b) {
        if (Float.isNaN(a) || Float.isNaN(b)) {
            if (Float.isNaN(a) != Float.isNaN(b)) {
                return true;
            }
            return false;
        } else if (Math.abs(a - b) > 1.0E-6f) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public void different(MotionPaths points, boolean[] mask, String[] custom, boolean arcMode) {
        boolean diffx = diff(this.mX, points.mX);
        boolean diffy = diff(this.mY, points.mY);
        int c = 0 + 1;
        mask[0] = mask[0] | diff(this.mPosition, points.mPosition);
        int c2 = c + 1;
        boolean z = false;
        mask[c] = mask[c] | (diffx || diffy || arcMode);
        int c3 = c2 + 1;
        boolean z2 = mask[c2];
        if (diffx || diffy || arcMode) {
            z = true;
        }
        mask[c2] = z2 | z;
        int c4 = c3 + 1;
        mask[c3] = mask[c3] | diff(this.mWidth, points.mWidth);
        int i = c4 + 1;
        mask[c4] = mask[c4] | diff(this.mHeight, points.mHeight);
    }

    /* access modifiers changed from: package-private */
    public void getCenter(double p, int[] toUse, double[] data, float[] point, int offset) {
        float f;
        int[] iArr = toUse;
        float v_x = this.mX;
        float v_y = this.mY;
        float v_width = this.mWidth;
        float v_height = this.mHeight;
        for (int i = 0; i < iArr.length; i++) {
            float value = (float) data[i];
            switch (iArr[i]) {
                case 1:
                    v_x = value;
                    break;
                case 2:
                    v_y = value;
                    break;
                case 3:
                    v_width = value;
                    break;
                case 4:
                    v_height = value;
                    break;
            }
        }
        if (this.mRelativeToController != null) {
            float[] pos = new float[2];
            this.mRelativeToController.getCenter(p, pos, new float[2]);
            float rx = pos[0];
            float ry = pos[1];
            float radius = v_x;
            float[] fArr = pos;
            f = 2.0f;
            float angle = v_y;
            float angle2 = v_x;
            float v_x2 = (float) ((((double) rx) + (Math.sin((double) angle) * ((double) radius))) - ((double) (v_width / 2.0f)));
            v_y = (float) ((((double) ry) - (Math.cos((double) angle) * ((double) radius))) - ((double) (v_height / 2.0f)));
            v_x = v_x2;
        } else {
            double d = p;
            float f2 = v_x;
            f = 2.0f;
        }
        point[offset] = (v_width / f) + v_x + 0.0f;
        point[offset + 1] = (v_height / f) + v_y + 0.0f;
    }

    /* access modifiers changed from: package-private */
    public void getCenter(double p, int[] toUse, double[] data, float[] point, double[] vdata, float[] velocity) {
        float v_width;
        float v_height;
        float f;
        int[] iArr = toUse;
        float v_x = this.mX;
        float v_y = this.mY;
        float v_width2 = this.mWidth;
        float v_height2 = this.mHeight;
        float dv_x = 0.0f;
        float dv_y = 0.0f;
        float dv_width = 0.0f;
        float dv_height = 0.0f;
        for (int i = 0; i < iArr.length; i++) {
            float value = (float) data[i];
            float dvalue = (float) vdata[i];
            switch (iArr[i]) {
                case 1:
                    v_x = value;
                    dv_x = dvalue;
                    break;
                case 2:
                    v_y = value;
                    dv_y = dvalue;
                    break;
                case 3:
                    v_width2 = value;
                    dv_width = dvalue;
                    break;
                case 4:
                    v_height2 = value;
                    dv_height = dvalue;
                    break;
            }
        }
        float dpos_x = (dv_width / 2.0f) + dv_x;
        float dpos_y = (dv_height / 2.0f) + dv_y;
        if (this.mRelativeToController != null) {
            f = 2.0f;
            float[] pos = new float[2];
            float[] vel = new float[2];
            float v_x2 = v_x;
            this.mRelativeToController.getCenter(p, pos, vel);
            float rx = pos[0];
            float ry = pos[1];
            float drx = vel[0];
            float dry = vel[1];
            v_width = v_width2;
            v_height = v_height2;
            float f2 = rx;
            double d = (double) rx;
            float rx2 = v_x2;
            float f3 = dv_x;
            float angle = v_y;
            float v_x3 = (float) ((d + (Math.sin((double) angle) * ((double) rx2))) - ((double) (v_width / 2.0f)));
            float f4 = ry;
            float f5 = rx2;
            float v_y2 = (float) ((((double) ry) - (((double) rx2) * Math.cos((double) angle))) - ((double) (v_height / 2.0f)));
            float dradius = dv_x;
            float v_y3 = v_y2;
            float v_y4 = dv_y;
            dpos_x = (float) (((double) drx) + (Math.sin((double) angle) * ((double) dradius)) + (((double) v_y4) * Math.cos((double) angle)));
            float f6 = drx;
            float f7 = dry;
            float f8 = dradius;
            dpos_y = (float) ((((double) dry) - (((double) dradius) * Math.cos((double) angle))) + (((double) v_y4) * Math.sin((double) angle)));
            v_x = v_x3;
            v_y = v_y3;
        } else {
            float f9 = v_y;
            v_width = v_width2;
            v_height = v_height2;
            float f10 = dv_x;
            f = 2.0f;
        }
        point[0] = (v_width / f) + v_x + 0.0f;
        point[1] = (v_height / f) + v_y + 0.0f;
        velocity[0] = dpos_x;
        velocity[1] = dpos_y;
    }

    /* access modifiers changed from: package-private */
    public void getCenterVelocity(double p, int[] toUse, double[] data, float[] point, int offset) {
        float f;
        int[] iArr = toUse;
        float v_x = this.mX;
        float v_y = this.mY;
        float v_width = this.mWidth;
        float v_height = this.mHeight;
        for (int i = 0; i < iArr.length; i++) {
            float value = (float) data[i];
            switch (iArr[i]) {
                case 1:
                    v_x = value;
                    break;
                case 2:
                    v_y = value;
                    break;
                case 3:
                    v_width = value;
                    break;
                case 4:
                    v_height = value;
                    break;
            }
        }
        if (this.mRelativeToController != null) {
            float[] pos = new float[2];
            this.mRelativeToController.getCenter(p, pos, new float[2]);
            float rx = pos[0];
            float ry = pos[1];
            float radius = v_x;
            float[] fArr = pos;
            f = 2.0f;
            float angle = v_y;
            float angle2 = v_x;
            float v_x2 = (float) ((((double) rx) + (Math.sin((double) angle) * ((double) radius))) - ((double) (v_width / 2.0f)));
            v_y = (float) ((((double) ry) - (Math.cos((double) angle) * ((double) radius))) - ((double) (v_height / 2.0f)));
            v_x = v_x2;
        } else {
            double d = p;
            float f2 = v_x;
            f = 2.0f;
        }
        point[offset] = (v_width / f) + v_x + 0.0f;
        point[offset + 1] = (v_height / f) + v_y + 0.0f;
    }

    /* access modifiers changed from: package-private */
    public void getBounds(int[] toUse, double[] data, float[] point, int offset) {
        float f = this.mX;
        float f2 = this.mY;
        float v_width = this.mWidth;
        float v_height = this.mHeight;
        for (int i = 0; i < toUse.length; i++) {
            float value = (float) data[i];
            switch (toUse[i]) {
                case 1:
                    float v_x = value;
                    break;
                case 2:
                    float v_y = value;
                    break;
                case 3:
                    v_width = value;
                    break;
                case 4:
                    v_height = value;
                    break;
            }
        }
        point[offset] = v_width;
        point[offset + 1] = v_height;
    }

    /* access modifiers changed from: package-private */
    public void setView(float position, MotionWidget view, int[] toUse, double[] data, double[] slope, double[] cycle) {
        char c;
        float v_width;
        float v_height;
        float dv_x;
        int i;
        float v_y;
        double d;
        MotionWidget motionWidget = view;
        int[] iArr = toUse;
        double[] dArr = slope;
        float v_x = this.mX;
        float v_y2 = this.mY;
        float dv_width = this.mWidth;
        float dv_height = this.mHeight;
        float dv_x2 = 0.0f;
        float dv_y = 0.0f;
        float dv_width2 = 0.0f;
        float dv_height2 = 0.0f;
        float path_rotate = Float.NaN;
        if (iArr.length != 0) {
            c = 1;
            if (this.mTempValue.length <= iArr[iArr.length - 1]) {
                int scratch_data_length = iArr[iArr.length - 1] + 1;
                this.mTempValue = new double[scratch_data_length];
                this.mTempDelta = new double[scratch_data_length];
            }
        } else {
            c = 1;
        }
        float v_x2 = v_x;
        float v_y3 = v_y2;
        Arrays.fill(this.mTempValue, Double.NaN);
        for (int i2 = 0; i2 < iArr.length; i2++) {
            this.mTempValue[iArr[i2]] = data[i2];
            this.mTempDelta[iArr[i2]] = dArr[i2];
        }
        int i3 = 0;
        float v_y4 = v_y3;
        while (i3 < this.mTempValue.length) {
            double deltaCycle = 0.0d;
            if (Double.isNaN(this.mTempValue[i3])) {
                if (cycle == null) {
                    i = i3;
                    v_y = v_y4;
                } else if (cycle[i3] == 0.0d) {
                    i = i3;
                    v_y = v_y4;
                }
                v_y4 = v_y;
                i3 = i + 1;
            }
            if (cycle != null) {
                deltaCycle = cycle[i3];
            }
            if (Double.isNaN(this.mTempValue[i3])) {
                i = i3;
                v_y = v_y4;
                d = deltaCycle;
            } else {
                i = i3;
                v_y = v_y4;
                d = this.mTempValue[i3] + deltaCycle;
            }
            float value = (float) d;
            float dvalue = (float) this.mTempDelta[i];
            switch (i) {
                case 0:
                    float delta_path = value;
                    v_y4 = v_y;
                    continue;
                case 1:
                    dv_x2 = dvalue;
                    v_x2 = value;
                    v_y4 = v_y;
                    continue;
                case 2:
                    v_y4 = value;
                    dv_y = dvalue;
                    continue;
                case 3:
                    dv_width2 = dvalue;
                    dv_width = value;
                    v_y4 = v_y;
                    continue;
                case 4:
                    dv_height2 = dvalue;
                    dv_height = value;
                    v_y4 = v_y;
                    continue;
                case 5:
                    path_rotate = value;
                    v_y4 = v_y;
                    continue;
            }
            v_y4 = v_y;
            i3 = i + 1;
        }
        int i4 = i3;
        float v_y5 = v_y4;
        if (this.mRelativeToController != null) {
            float[] pos = new float[2];
            float[] vel = new float[2];
            v_width = dv_width;
            v_height = dv_height;
            this.mRelativeToController.getCenter((double) position, pos, vel);
            float rx = pos[0];
            float ry = pos[c];
            float dangle = dv_y;
            float drx = vel[0];
            float dry = vel[c];
            float f = dv_x2;
            float f2 = dv_y;
            float f3 = rx;
            float radius = v_x2;
            float[] fArr = vel;
            float angle = v_y5;
            float pos_x = (float) ((((double) rx) + (Math.sin((double) angle) * ((double) radius))) - ((double) (v_width / 2.0f)));
            float f4 = ry;
            float pos_y = (float) ((((double) ry) - (Math.cos((double) angle) * ((double) radius))) - ((double) (v_height / 2.0f)));
            float dradius = dv_x2;
            float dangle2 = dangle;
            float dpos_x = (float) (((double) drx) + (Math.sin((double) angle) * ((double) dradius)) + (((double) dangle2) * Math.cos((double) angle) * ((double) radius)));
            float f5 = dv_width2;
            float dv_height3 = dv_height2;
            float f6 = dradius;
            double sin = Math.sin((double) angle) * ((double) radius);
            float f7 = drx;
            float f8 = angle;
            float dpos_y = (float) ((((double) dry) - (((double) dradius) * Math.cos((double) angle))) + (sin * ((double) dangle2)));
            float dv_x3 = dpos_x;
            float dv_y2 = dpos_y;
            v_x2 = pos_x;
            float v_y6 = pos_y;
            float f9 = dry;
            float f10 = radius;
            if (dArr.length >= 2) {
                slope[0] = (double) dpos_x;
                slope[c] = (double) dpos_y;
            }
            if (!Float.isNaN(path_rotate)) {
                float f11 = dpos_x;
                float f12 = dv_height3;
                motionWidget.setRotationZ((float) (((double) path_rotate) + Math.toDegrees(Math.atan2((double) dv_y2, (double) dv_x3))));
            } else {
                float f13 = dv_height3;
            }
            float dangle3 = dv_x3;
            dv_x = v_y6;
        } else {
            v_width = dv_width;
            v_height = dv_height;
            float dv_x4 = dv_x2;
            float dv_y3 = dv_y;
            float dv_width3 = dv_width2;
            float dv_height4 = dv_height2;
            if (!Float.isNaN(path_rotate)) {
                motionWidget.setRotationZ(0.0f + ((float) (((double) path_rotate) + Math.toDegrees(Math.atan2((double) (dv_y3 + (dv_height4 / 2.0f)), (double) (dv_x4 + (dv_width3 / 2.0f)))))));
            }
            dv_x = v_y5;
            float f14 = dv_y3;
            float f15 = dv_x4;
        }
        int l = (int) (v_x2 + 0.5f);
        int t = (int) (dv_x + 0.5f);
        int r = (int) (v_x2 + 0.5f + v_width);
        int b = (int) (0.5f + dv_x + v_height);
        int i5 = r - l;
        int i6 = b - t;
        motionWidget.layout(l, t, r, b);
    }

    /* access modifiers changed from: package-private */
    public void getRect(int[] toUse, double[] data, float[] path, int offset) {
        float ry;
        float f;
        float v_height;
        float angle;
        float v_x;
        float cx;
        float cy;
        float x1;
        float y1;
        float y4;
        float x4;
        int[] iArr = toUse;
        float v_x2 = this.mX;
        float v_y = this.mY;
        float v_width = this.mWidth;
        float v_height2 = this.mHeight;
        float v_x3 = v_x2;
        int i = 0;
        while (true) {
            float v_y2 = v_y;
            if (i < iArr.length) {
                int i2 = i;
                float value = (float) data[i2];
                switch (toUse[i2]) {
                    case 0:
                        float delta_path = value;
                        break;
                    case 1:
                        v_x3 = value;
                        break;
                    case 2:
                        v_y2 = value;
                        break;
                    case 3:
                        v_width = value;
                        break;
                    case 4:
                        v_height2 = value;
                        break;
                }
                i = i2 + 1;
                iArr = toUse;
                v_y = v_y2;
            } else {
                int i3 = i;
                if (this.mRelativeToController != null) {
                    float rx = this.mRelativeToController.getCenterX();
                    float ry2 = this.mRelativeToController.getCenterY();
                    f = 2.0f;
                    float radius = v_x3;
                    float radius2 = rx;
                    float angle2 = v_y2;
                    float v_x4 = (float) ((((double) rx) + (Math.sin((double) angle2) * ((double) radius))) - ((double) (v_width / 2.0f)));
                    v_height = v_height2;
                    float ry3 = ry2;
                    ry = v_width;
                    float f2 = ry3;
                    float f3 = radius;
                    angle = (float) ((((double) ry3) - (((double) radius) * Math.cos((double) angle2))) - ((double) (v_height / 2.0f)));
                    v_x = v_x4;
                } else {
                    ry = v_width;
                    v_height = v_height2;
                    f = 2.0f;
                    angle = v_y2;
                    v_x = v_x3;
                }
                float x12 = v_x;
                float y12 = angle;
                float x2 = v_x + ry;
                float y2 = y12;
                float x3 = x2;
                float y3 = angle + v_height;
                float x42 = x12;
                float y42 = y3;
                float cx2 = x12 + (ry / f);
                float cy2 = y12 + (v_height / f);
                if (!Float.isNaN(Float.NaN)) {
                    cx = x12 + ((x2 - x12) * Float.NaN);
                } else {
                    cx = cx2;
                }
                if (!Float.isNaN(Float.NaN)) {
                    cy = y12 + ((y3 - y12) * Float.NaN);
                } else {
                    cy = cy2;
                }
                if (1.0f != 1.0f) {
                    float midx = (x12 + x2) / f;
                    x2 = ((x2 - midx) * 1.0f) + midx;
                    x3 = ((x3 - midx) * 1.0f) + midx;
                    x42 = ((x42 - midx) * 1.0f) + midx;
                    x1 = ((x12 - midx) * 1.0f) + midx;
                } else {
                    x1 = x12;
                }
                if (1.0f != 1.0f) {
                    float midy = (y12 + y3) / f;
                    y2 = ((y2 - midy) * 1.0f) + midy;
                    y3 = ((y3 - midy) * 1.0f) + midy;
                    y42 = ((y42 - midy) * 1.0f) + midy;
                    y1 = ((y12 - midy) * 1.0f) + midy;
                } else {
                    y1 = y12;
                }
                if (0.0f != 0.0f) {
                    float sin = (float) Math.sin(Math.toRadians((double) 0.0f));
                    float cos = (float) Math.cos(Math.toRadians((double) 0.0f));
                    float tx1 = xRotate(sin, cos, cx, cy, x1, y1);
                    float ty1 = yRotate(sin, cos, cx, cy, x1, y1);
                    float f4 = x1;
                    float f5 = y1;
                    float x22 = x2;
                    float y22 = y2;
                    x2 = xRotate(sin, cos, cx, cy, x22, y22);
                    y2 = yRotate(sin, cos, cx, cy, x22, y22);
                    float f6 = x22;
                    float f7 = y22;
                    float x32 = x3;
                    float y32 = y3;
                    x3 = xRotate(sin, cos, cx, cy, x32, y32);
                    y3 = yRotate(sin, cos, cx, cy, x32, y32);
                    float f8 = x32;
                    float f9 = y32;
                    float x33 = x42;
                    float y43 = y42;
                    x42 = xRotate(sin, cos, cx, cy, x33, y43);
                    y42 = yRotate(sin, cos, cx, cy, x33, y43);
                    x4 = tx1;
                    y4 = ty1;
                } else {
                    float f10 = y2;
                    float f11 = x3;
                    float f12 = y3;
                    float x13 = x1;
                    float y13 = y1;
                    float x14 = x42;
                    float y14 = y42;
                    x4 = x13;
                    y4 = y13;
                }
                int offset2 = offset + 1;
                path[offset] = x4 + 0.0f;
                int offset3 = offset2 + 1;
                path[offset2] = y4 + 0.0f;
                int offset4 = offset3 + 1;
                path[offset3] = x2 + 0.0f;
                int offset5 = offset4 + 1;
                path[offset4] = y2 + 0.0f;
                int offset6 = offset5 + 1;
                path[offset5] = x3 + 0.0f;
                int offset7 = offset6 + 1;
                path[offset6] = y3 + 0.0f;
                int offset8 = offset7 + 1;
                path[offset7] = x42 + 0.0f;
                int i4 = offset8 + 1;
                path[offset8] = y42 + 0.0f;
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setDpDt(float locationX, float locationY, float[] mAnchorDpDt, int[] toUse, double[] deltaData, double[] data) {
        int[] iArr = toUse;
        float d_x = 0.0f;
        float d_y = 0.0f;
        float d_width = 0.0f;
        float d_height = 0.0f;
        for (int i = 0; i < iArr.length; i++) {
            float deltaV = (float) deltaData[i];
            switch (iArr[i]) {
                case 1:
                    d_x = deltaV;
                    break;
                case 2:
                    d_y = deltaV;
                    break;
                case 3:
                    d_width = deltaV;
                    break;
                case 4:
                    d_height = deltaV;
                    break;
            }
        }
        float deltaX = d_x - ((0.0f * d_width) / 2.0f);
        float deltaY = d_y - ((0.0f * d_height) / 2.0f);
        mAnchorDpDt[0] = ((1.0f - locationX) * deltaX) + ((deltaX + ((0.0f + 1.0f) * d_width)) * locationX) + 0.0f;
        mAnchorDpDt[1] = ((1.0f - locationY) * deltaY) + ((deltaY + ((0.0f + 1.0f) * d_height)) * locationY) + 0.0f;
    }

    /* access modifiers changed from: package-private */
    public void fillStandard(double[] data, int[] toUse) {
        float[] set = {this.mPosition, this.mX, this.mY, this.mWidth, this.mHeight, this.mPathRotate};
        int c = 0;
        for (int i = 0; i < toUse.length; i++) {
            if (toUse[i] < set.length) {
                data[c] = (double) set[toUse[i]];
                c++;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean hasCustomData(String name) {
        return this.mCustomAttributes.containsKey(name);
    }

    /* access modifiers changed from: package-private */
    public int getCustomDataCount(String name) {
        CustomVariable a = this.mCustomAttributes.get(name);
        if (a == null) {
            return 0;
        }
        return a.numberOfInterpolatedValues();
    }

    /* access modifiers changed from: package-private */
    public int getCustomData(String name, double[] value, int offset) {
        CustomVariable a = this.mCustomAttributes.get(name);
        if (a == null) {
            return 0;
        }
        if (a.numberOfInterpolatedValues() == 1) {
            value[offset] = (double) a.getValueToInterpolate();
            return 1;
        }
        int n = a.numberOfInterpolatedValues();
        float[] f = new float[n];
        a.getValuesToInterpolate(f);
        int i = 0;
        while (i < n) {
            value[offset] = (double) f[i];
            i++;
            offset++;
        }
        return n;
    }

    /* access modifiers changed from: package-private */
    public void setBounds(float x, float y, float w, float h) {
        this.mX = x;
        this.mY = y;
        this.mWidth = w;
        this.mHeight = h;
    }

    public int compareTo(MotionPaths o) {
        return Float.compare(this.mPosition, o.mPosition);
    }

    public void applyParameters(MotionWidget c) {
        this.mKeyFrameEasing = Easing.getInterpolator(c.mMotion.mTransitionEasing);
        this.mPathMotionArc = c.mMotion.mPathMotionArc;
        this.mAnimateRelativeTo = c.mMotion.mAnimateRelativeTo;
        this.mPathRotate = c.mMotion.mPathRotate;
        this.mDrawPath = c.mMotion.mDrawPath;
        this.mAnimateCircleAngleTo = c.mMotion.mAnimateCircleAngleTo;
        this.mProgress = c.mPropertySet.mProgress;
        if (!(c.mWidgetFrame == null || c.mWidgetFrame.widget == null)) {
            this.mRelativeAngle = c.mWidgetFrame.widget.mCircleConstraintAngle;
        }
        for (String s : c.getCustomAttributeNames()) {
            CustomVariable attr = c.getCustomAttribute(s);
            if (attr != null && attr.isContinuous()) {
                this.mCustomAttributes.put(s, attr);
            }
        }
    }

    public void configureRelativeTo(Motion toOrbit) {
        double[] pos = toOrbit.getPos((double) this.mProgress);
    }
}
