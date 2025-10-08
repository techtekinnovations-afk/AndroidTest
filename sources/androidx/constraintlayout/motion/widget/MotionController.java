package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.KeyCycleOscillator;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.VelocityMatrix;
import androidx.constraintlayout.motion.utils.ViewOscillator;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.motion.utils.ViewState;
import androidx.constraintlayout.motion.utils.ViewTimeCycle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class MotionController {
    static final int BOUNCE = 4;
    private static final boolean DEBUG = false;
    public static final int DRAW_PATH_AS_CONFIGURED = 4;
    public static final int DRAW_PATH_BASIC = 1;
    public static final int DRAW_PATH_CARTESIAN = 3;
    public static final int DRAW_PATH_NONE = 0;
    public static final int DRAW_PATH_RECTANGLE = 5;
    public static final int DRAW_PATH_RELATIVE = 2;
    public static final int DRAW_PATH_SCREEN = 6;
    static final int EASE_IN = 1;
    static final int EASE_IN_OUT = 0;
    static final int EASE_OUT = 2;
    private static final boolean FAVOR_FIXED_SIZE_VIEWS = false;
    public static final int HORIZONTAL_PATH_X = 2;
    public static final int HORIZONTAL_PATH_Y = 3;
    private static final int INTERPOLATOR_REFERENCE_ID = -2;
    private static final int INTERPOLATOR_UNDEFINED = -3;
    static final int LINEAR = 3;
    static final int OVERSHOOT = 5;
    public static final int PATH_PERCENT = 0;
    public static final int PATH_PERPENDICULAR = 1;
    public static final int ROTATION_LEFT = 2;
    public static final int ROTATION_RIGHT = 1;
    private static final int SPLINE_STRING = -1;
    private static final String TAG = "MotionController";
    public static final int VERTICAL_PATH_X = 4;
    public static final int VERTICAL_PATH_Y = 5;
    private CurveFit mArcSpline;
    private int[] mAttributeInterpolatorCount;
    private String[] mAttributeNames;
    String[] mAttributeTable;
    private HashMap<String, ViewSpline> mAttributesMap;
    String mConstraintTag;
    float mCurrentCenterX;
    float mCurrentCenterY;
    private int mCurveFitType = -1;
    private HashMap<String, ViewOscillator> mCycleMap;
    private MotionPaths mEndMotionPath = new MotionPaths();
    private MotionConstrainedPoint mEndPoint = new MotionConstrainedPoint();
    boolean mForceMeasure = false;
    int mId;
    private double[] mInterpolateData;
    private int[] mInterpolateVariables;
    private double[] mInterpolateVelocity;
    private ArrayList<Key> mKeyList = new ArrayList<>();
    private KeyTrigger[] mKeyTriggers;
    private int mMaxDimension = 4;
    private ArrayList<MotionPaths> mMotionPaths = new ArrayList<>();
    float mMotionStagger = Float.NaN;
    private boolean mNoMovement = false;
    private int mPathMotionArc = Key.UNSET;
    private Interpolator mQuantizeMotionInterpolator = null;
    private float mQuantizeMotionPhase = Float.NaN;
    private int mQuantizeMotionSteps = Key.UNSET;
    private CurveFit[] mSpline;
    float mStaggerOffset = 0.0f;
    float mStaggerScale = 1.0f;
    private MotionPaths mStartMotionPath = new MotionPaths();
    private MotionConstrainedPoint mStartPoint = new MotionConstrainedPoint();
    Rect mTempRect = new Rect();
    private HashMap<String, ViewTimeCycle> mTimeCycleAttributesMap;
    private int mTransformPivotTarget = Key.UNSET;
    private View mTransformPivotView = null;
    private float[] mValuesBuff = new float[this.mMaxDimension];
    private float[] mVelocity = new float[1];
    View mView;

    public int getTransformPivotTarget() {
        return this.mTransformPivotTarget;
    }

    public void setTransformPivotTarget(int transformPivotTarget) {
        this.mTransformPivotTarget = transformPivotTarget;
        this.mTransformPivotView = null;
    }

    /* access modifiers changed from: package-private */
    public MotionPaths getKeyFrame(int i) {
        return this.mMotionPaths.get(i);
    }

    MotionController(View view) {
        setView(view);
    }

    public float getStartX() {
        return this.mStartMotionPath.mX;
    }

    public float getStartY() {
        return this.mStartMotionPath.mY;
    }

    public float getFinalX() {
        return this.mEndMotionPath.mX;
    }

    public float getFinalY() {
        return this.mEndMotionPath.mY;
    }

    public float getStartWidth() {
        return this.mStartMotionPath.mWidth;
    }

    public float getStartHeight() {
        return this.mStartMotionPath.mHeight;
    }

    public float getFinalWidth() {
        return this.mEndMotionPath.mWidth;
    }

    public float getFinalHeight() {
        return this.mEndMotionPath.mHeight;
    }

    public int getAnimateRelativeTo() {
        return this.mStartMotionPath.mAnimateRelativeTo;
    }

    public void setupRelative(MotionController motionController) {
        this.mStartMotionPath.setupRelative(motionController, motionController.mStartMotionPath);
        this.mEndMotionPath.setupRelative(motionController, motionController.mEndMotionPath);
    }

    public float getCenterX() {
        return this.mCurrentCenterX;
    }

    public float getCenterY() {
        return this.mCurrentCenterY;
    }

    public void getCenter(double p, float[] pos, float[] vel) {
        double[] position = new double[4];
        double[] velocity = new double[4];
        int[] iArr = new int[4];
        this.mSpline[0].getPos(p, position);
        this.mSpline[0].getSlope(p, velocity);
        Arrays.fill(vel, 0.0f);
        this.mStartMotionPath.getCenter(p, this.mInterpolateVariables, position, pos, velocity, vel);
    }

    public void remeasure() {
        this.mForceMeasure = true;
    }

    /* access modifiers changed from: package-private */
    public void buildPath(float[] points, int pointCount) {
        float mils;
        int i = pointCount;
        float f = 1.0f;
        float mils2 = 1.0f / ((float) (i - 1));
        ViewOscillator osc_y = null;
        SplineSet trans_x = this.mAttributesMap == null ? null : this.mAttributesMap.get("translationX");
        SplineSet trans_y = this.mAttributesMap == null ? null : this.mAttributesMap.get("translationY");
        ViewOscillator osc_x = this.mCycleMap == null ? null : this.mCycleMap.get("translationX");
        if (this.mCycleMap != null) {
            osc_y = this.mCycleMap.get("translationY");
        }
        int i2 = 0;
        while (i2 < i) {
            float position = ((float) i2) * mils2;
            if (this.mStaggerScale != f) {
                if (position < this.mStaggerOffset) {
                    position = 0.0f;
                }
                if (position > this.mStaggerOffset && ((double) position) < 1.0d) {
                    position = Math.min((position - this.mStaggerOffset) * this.mStaggerScale, f);
                }
            }
            double p = (double) position;
            Easing easing = this.mStartMotionPath.mKeyFrameEasing;
            float start = 0.0f;
            float end = Float.NaN;
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            while (it.hasNext()) {
                MotionPaths frame = it.next();
                if (frame.mKeyFrameEasing != null) {
                    if (frame.mTime < position) {
                        Easing easing2 = frame.mKeyFrameEasing;
                        start = frame.mTime;
                        easing = easing2;
                    } else if (Float.isNaN(end)) {
                        end = frame.mTime;
                    }
                }
                int i3 = pointCount;
            }
            if (easing != null) {
                if (Float.isNaN(end)) {
                    end = 1.0f;
                }
                mils = mils2;
                p = (double) (((end - start) * ((float) easing.get((double) ((position - start) / (end - start))))) + start);
            } else {
                mils = mils2;
            }
            this.mSpline[0].getPos(p, this.mInterpolateData);
            if (this.mArcSpline != null && this.mInterpolateData.length > 0) {
                this.mArcSpline.getPos(p, this.mInterpolateData);
            }
            this.mStartMotionPath.getCenter(p, this.mInterpolateVariables, this.mInterpolateData, points, i2 * 2);
            if (osc_x != null) {
                int i4 = i2 * 2;
                points[i4] = points[i4] + osc_x.get(position);
            } else if (trans_x != null) {
                int i5 = i2 * 2;
                points[i5] = points[i5] + trans_x.get(position);
            }
            if (osc_y != null) {
                int i6 = (i2 * 2) + 1;
                points[i6] = points[i6] + osc_y.get(position);
            } else if (trans_y != null) {
                int i7 = (i2 * 2) + 1;
                points[i7] = points[i7] + trans_y.get(position);
            }
            i2++;
            i = pointCount;
            mils2 = mils;
            f = 1.0f;
        }
    }

    /* access modifiers changed from: package-private */
    public double[] getPos(double position) {
        this.mSpline[0].getPos(position, this.mInterpolateData);
        if (this.mArcSpline != null && this.mInterpolateData.length > 0) {
            this.mArcSpline.getPos(position, this.mInterpolateData);
        }
        return this.mInterpolateData;
    }

    /* access modifiers changed from: package-private */
    public void buildBounds(float[] bounds, int pointCount) {
        float mils;
        MotionController motionController = this;
        int i = pointCount;
        float f = 1.0f;
        float mils2 = 1.0f / ((float) (i - 1));
        SplineSet trans_x = motionController.mAttributesMap == null ? null : motionController.mAttributesMap.get("translationX");
        if (motionController.mAttributesMap != null) {
            SplineSet splineSet = motionController.mAttributesMap.get("translationY");
        }
        if (motionController.mCycleMap != null) {
            ViewOscillator viewOscillator = motionController.mCycleMap.get("translationX");
        }
        if (motionController.mCycleMap != null) {
            ViewOscillator viewOscillator2 = motionController.mCycleMap.get("translationY");
        }
        int i2 = 0;
        while (i2 < i) {
            float position = ((float) i2) * mils2;
            if (motionController.mStaggerScale != f) {
                if (position < motionController.mStaggerOffset) {
                    position = 0.0f;
                }
                if (position > motionController.mStaggerOffset && ((double) position) < 1.0d) {
                    position = Math.min((position - motionController.mStaggerOffset) * motionController.mStaggerScale, f);
                }
            }
            double p = (double) position;
            Easing easing = motionController.mStartMotionPath.mKeyFrameEasing;
            float start = 0.0f;
            float end = Float.NaN;
            Iterator<MotionPaths> it = motionController.mMotionPaths.iterator();
            while (it.hasNext()) {
                MotionPaths frame = it.next();
                if (frame.mKeyFrameEasing != null) {
                    if (frame.mTime < position) {
                        Easing easing2 = frame.mKeyFrameEasing;
                        start = frame.mTime;
                        easing = easing2;
                    } else if (Float.isNaN(end)) {
                        end = frame.mTime;
                    }
                }
                int i3 = pointCount;
            }
            if (easing != null) {
                if (Float.isNaN(end)) {
                    end = 1.0f;
                }
                mils = mils2;
                p = (double) (((end - start) * ((float) easing.get((double) ((position - start) / (end - start))))) + start);
            } else {
                mils = mils2;
            }
            motionController.mSpline[0].getPos(p, motionController.mInterpolateData);
            if (motionController.mArcSpline != null && motionController.mInterpolateData.length > 0) {
                motionController.mArcSpline.getPos(p, motionController.mInterpolateData);
            }
            motionController.mStartMotionPath.getBounds(motionController.mInterpolateVariables, motionController.mInterpolateData, bounds, i2 * 2);
            i2++;
            motionController = this;
            i = pointCount;
            mils2 = mils;
            trans_x = trans_x;
            f = 1.0f;
        }
    }

    private float getPreCycleDistance() {
        float sum;
        float position;
        char c;
        int pointCount = 100;
        float[] points = new float[2];
        float sum2 = 0.0f;
        float mils = 1.0f / ((float) (100 - 1));
        double x = 0.0d;
        double y = 0.0d;
        int i = 0;
        while (i < pointCount) {
            float position2 = ((float) i) * mils;
            double p = (double) position2;
            Easing easing = this.mStartMotionPath.mKeyFrameEasing;
            float end = Float.NaN;
            int pointCount2 = pointCount;
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            float start = 0.0f;
            while (it.hasNext()) {
                MotionPaths frame = it.next();
                Iterator<MotionPaths> it2 = it;
                if (frame.mKeyFrameEasing != null) {
                    if (frame.mTime < position2) {
                        Easing easing2 = frame.mKeyFrameEasing;
                        start = frame.mTime;
                        easing = easing2;
                    } else if (Float.isNaN(end)) {
                        end = frame.mTime;
                    }
                }
                it = it2;
            }
            if (easing != null) {
                if (Float.isNaN(end)) {
                    end = 1.0f;
                }
                sum = sum2;
                position = position2;
                p = (double) (((end - start) * ((float) easing.get((double) ((position2 - start) / (end - start))))) + start);
                float offset = end;
            } else {
                sum = sum2;
                position = position2;
                float f = end;
            }
            this.mSpline[0].getPos(p, this.mInterpolateData);
            Easing easing3 = easing;
            float f2 = position;
            this.mStartMotionPath.getCenter(p, this.mInterpolateVariables, this.mInterpolateData, points, 0);
            if (i > 0) {
                double d = p;
                c = 1;
                sum += (float) Math.hypot(y - ((double) points[1]), x - ((double) points[0]));
            } else {
                c = 1;
            }
            x = (double) points[0];
            y = (double) points[c];
            i++;
            pointCount = pointCount2;
            sum2 = sum;
        }
        return sum2;
    }

    /* access modifiers changed from: package-private */
    public KeyPositionBase getPositionKeyframe(int layoutWidth, int layoutHeight, float x, float y) {
        float y2;
        float x2;
        int layoutHeight2;
        int layoutWidth2;
        RectF start = new RectF();
        start.left = this.mStartMotionPath.mX;
        start.top = this.mStartMotionPath.mY;
        start.right = start.left + this.mStartMotionPath.mWidth;
        start.bottom = start.top + this.mStartMotionPath.mHeight;
        RectF end = new RectF();
        end.left = this.mEndMotionPath.mX;
        end.top = this.mEndMotionPath.mY;
        end.right = end.left + this.mEndMotionPath.mWidth;
        end.bottom = end.top + this.mEndMotionPath.mHeight;
        Iterator<Key> it = this.mKeyList.iterator();
        while (it.hasNext()) {
            Key key = it.next();
            if (key instanceof KeyPositionBase) {
                layoutWidth2 = layoutWidth;
                layoutHeight2 = layoutHeight;
                x2 = x;
                y2 = y;
                if (((KeyPositionBase) key).intersects(layoutWidth2, layoutHeight2, start, end, x2, y2) != 0) {
                    return (KeyPositionBase) key;
                }
            } else {
                layoutWidth2 = layoutWidth;
                layoutHeight2 = layoutHeight;
                x2 = x;
                y2 = y;
            }
            layoutWidth = layoutWidth2;
            layoutHeight = layoutHeight2;
            x = x2;
            y = y2;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public int buildKeyFrames(float[] keyFrames, int[] mode) {
        if (keyFrames == null) {
            return 0;
        }
        int count = 0;
        double[] time = this.mSpline[0].getTimePoints();
        if (mode != null) {
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            while (it.hasNext()) {
                mode[count] = it.next().mMode;
                count++;
            }
            count = 0;
        }
        int count2 = count;
        for (int i = 0; i < time.length; i++) {
            this.mSpline[0].getPos(time[i], this.mInterpolateData);
            this.mStartMotionPath.getCenter(time[i], this.mInterpolateVariables, this.mInterpolateData, keyFrames, count2);
            count2 += 2;
        }
        return count2 / 2;
    }

    /* access modifiers changed from: package-private */
    public int buildKeyBounds(float[] keyBounds, int[] mode) {
        if (keyBounds == null) {
            return 0;
        }
        int count = 0;
        double[] time = this.mSpline[0].getTimePoints();
        if (mode != null) {
            Iterator<MotionPaths> it = this.mMotionPaths.iterator();
            while (it.hasNext()) {
                mode[count] = it.next().mMode;
                count++;
            }
            count = 0;
        }
        for (double pos : time) {
            this.mSpline[0].getPos(pos, this.mInterpolateData);
            this.mStartMotionPath.getBounds(this.mInterpolateVariables, this.mInterpolateData, keyBounds, count);
            count += 2;
        }
        return count / 2;
    }

    /* access modifiers changed from: package-private */
    public int getAttributeValues(String attributeType, float[] points, int pointCount) {
        float f = 1.0f / ((float) (pointCount - 1));
        SplineSet spline = this.mAttributesMap.get(attributeType);
        if (spline == null) {
            return -1;
        }
        for (int j = 0; j < points.length; j++) {
            points[j] = spline.get((float) (j / (points.length - 1)));
        }
        return points.length;
    }

    /* access modifiers changed from: package-private */
    public void buildRect(float p, float[] path, int offset) {
        this.mSpline[0].getPos((double) getAdjustedPosition(p, (float[]) null), this.mInterpolateData);
        this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, path, offset);
    }

    /* access modifiers changed from: package-private */
    public void buildRectangles(float[] path, int pointCount) {
        float mils = 1.0f / ((float) (pointCount - 1));
        for (int i = 0; i < pointCount; i++) {
            this.mSpline[0].getPos((double) getAdjustedPosition(((float) i) * mils, (float[]) null), this.mInterpolateData);
            this.mStartMotionPath.getRect(this.mInterpolateVariables, this.mInterpolateData, path, i * 8);
        }
    }

    /* access modifiers changed from: package-private */
    public float getKeyFrameParameter(int type, float x, float y) {
        float dx = this.mEndMotionPath.mX - this.mStartMotionPath.mX;
        float dy = this.mEndMotionPath.mY - this.mStartMotionPath.mY;
        float startCenterX = this.mStartMotionPath.mX + (this.mStartMotionPath.mWidth / 2.0f);
        float startCenterY = this.mStartMotionPath.mY + (this.mStartMotionPath.mHeight / 2.0f);
        float hypotenuse = (float) Math.hypot((double) dx, (double) dy);
        if (((double) hypotenuse) < 1.0E-7d) {
            return Float.NaN;
        }
        float vx = x - startCenterX;
        float vy = y - startCenterY;
        if (((float) Math.hypot((double) vx, (double) vy)) == 0.0f) {
            return 0.0f;
        }
        float pathDistance = (vx * dx) + (vy * dy);
        switch (type) {
            case 0:
                return pathDistance / hypotenuse;
            case 1:
                return (float) Math.sqrt((double) ((hypotenuse * hypotenuse) - (pathDistance * pathDistance)));
            case 2:
                return vx / dx;
            case 3:
                return vy / dx;
            case 4:
                return vx / dy;
            case 5:
                return vy / dy;
            default:
                return 0.0f;
        }
    }

    private void insertKey(MotionPaths point) {
        int pos = Collections.binarySearch(this.mMotionPaths, point);
        if (pos == 0) {
            Log.e(TAG, " KeyPath position \"" + point.mPosition + "\" outside of range");
        }
        this.mMotionPaths.add((-pos) - 1, point);
    }

    /* access modifiers changed from: package-private */
    public void addKeys(ArrayList<Key> list) {
        this.mKeyList.addAll(list);
    }

    public void addKey(Key key) {
        this.mKeyList.add(key);
    }

    public void setPathMotionArc(int arc) {
        this.mPathMotionArc = arc;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v0, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v20, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v22, resolved type: double[][]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v20, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v21, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v1, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v2, resolved type: char} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setup(int r33, int r34, float r35, long r36) {
        /*
            r32 = this;
            r0 = r32
            java.util.HashSet r1 = new java.util.HashSet
            r1.<init>()
            java.util.HashSet r2 = new java.util.HashSet
            r2.<init>()
            java.util.HashSet r3 = new java.util.HashSet
            r3.<init>()
            java.util.HashSet r4 = new java.util.HashSet
            r4.<init>()
            java.util.HashMap r5 = new java.util.HashMap
            r5.<init>()
            r6 = 0
            int r7 = r0.mPathMotionArc
            int r8 = androidx.constraintlayout.motion.widget.Key.UNSET
            if (r7 == r8) goto L_0x0028
            androidx.constraintlayout.motion.widget.MotionPaths r7 = r0.mStartMotionPath
            int r8 = r0.mPathMotionArc
            r7.mPathMotionArc = r8
        L_0x0028:
            androidx.constraintlayout.motion.widget.MotionConstrainedPoint r7 = r0.mStartPoint
            androidx.constraintlayout.motion.widget.MotionConstrainedPoint r8 = r0.mEndPoint
            r7.different(r8, r3)
            java.util.ArrayList<androidx.constraintlayout.motion.widget.Key> r7 = r0.mKeyList
            if (r7 == 0) goto L_0x0091
            java.util.ArrayList<androidx.constraintlayout.motion.widget.Key> r7 = r0.mKeyList
            java.util.Iterator r7 = r7.iterator()
        L_0x0039:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x0091
            java.lang.Object r8 = r7.next()
            androidx.constraintlayout.motion.widget.Key r8 = (androidx.constraintlayout.motion.widget.Key) r8
            boolean r9 = r8 instanceof androidx.constraintlayout.motion.widget.KeyPosition
            if (r9 == 0) goto L_0x0067
            r13 = r8
            androidx.constraintlayout.motion.widget.KeyPosition r13 = (androidx.constraintlayout.motion.widget.KeyPosition) r13
            androidx.constraintlayout.motion.widget.MotionPaths r10 = new androidx.constraintlayout.motion.widget.MotionPaths
            androidx.constraintlayout.motion.widget.MotionPaths r14 = r0.mStartMotionPath
            androidx.constraintlayout.motion.widget.MotionPaths r15 = r0.mEndMotionPath
            r11 = r33
            r12 = r34
            r10.<init>(r11, r12, r13, r14, r15)
            r0.insertKey(r10)
            int r9 = r13.mCurveFit
            int r10 = androidx.constraintlayout.motion.widget.Key.UNSET
            if (r9 == r10) goto L_0x0066
            int r9 = r13.mCurveFit
            r0.mCurveFitType = r9
        L_0x0066:
            goto L_0x0090
        L_0x0067:
            boolean r9 = r8 instanceof androidx.constraintlayout.motion.widget.KeyCycle
            if (r9 == 0) goto L_0x006f
            r8.getAttributeNames(r4)
            goto L_0x0090
        L_0x006f:
            boolean r9 = r8 instanceof androidx.constraintlayout.motion.widget.KeyTimeCycle
            if (r9 == 0) goto L_0x0077
            r8.getAttributeNames(r2)
            goto L_0x0090
        L_0x0077:
            boolean r9 = r8 instanceof androidx.constraintlayout.motion.widget.KeyTrigger
            if (r9 == 0) goto L_0x008a
            if (r6 != 0) goto L_0x0083
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            r6 = r9
        L_0x0083:
            r9 = r8
            androidx.constraintlayout.motion.widget.KeyTrigger r9 = (androidx.constraintlayout.motion.widget.KeyTrigger) r9
            r6.add(r9)
            goto L_0x0090
        L_0x008a:
            r8.setInterpolation(r5)
            r8.getAttributeNames(r3)
        L_0x0090:
            goto L_0x0039
        L_0x0091:
            r7 = 0
            if (r6 == 0) goto L_0x009e
            androidx.constraintlayout.motion.widget.KeyTrigger[] r8 = new androidx.constraintlayout.motion.widget.KeyTrigger[r7]
            java.lang.Object[] r8 = r6.toArray(r8)
            androidx.constraintlayout.motion.widget.KeyTrigger[] r8 = (androidx.constraintlayout.motion.widget.KeyTrigger[]) r8
            r0.mKeyTriggers = r8
        L_0x009e:
            boolean r8 = r3.isEmpty()
            java.lang.String r9 = ","
            java.lang.String r10 = "CUSTOM,"
            r11 = 1
            if (r8 != 0) goto L_0x019c
            java.util.HashMap r8 = new java.util.HashMap
            r8.<init>()
            r0.mAttributesMap = r8
            java.util.Iterator r8 = r3.iterator()
        L_0x00b4:
            boolean r12 = r8.hasNext()
            if (r12 == 0) goto L_0x0130
            java.lang.Object r12 = r8.next()
            java.lang.String r12 = (java.lang.String) r12
            boolean r13 = r12.startsWith(r10)
            if (r13 == 0) goto L_0x0112
            android.util.SparseArray r13 = new android.util.SparseArray
            r13.<init>()
            java.lang.String[] r14 = r12.split(r9)
            r14 = r14[r11]
            java.util.ArrayList<androidx.constraintlayout.motion.widget.Key> r15 = r0.mKeyList
            java.util.Iterator r15 = r15.iterator()
        L_0x00d7:
            boolean r16 = r15.hasNext()
            if (r16 == 0) goto L_0x0109
            java.lang.Object r16 = r15.next()
            r17 = r11
            r11 = r16
            androidx.constraintlayout.motion.widget.Key r11 = (androidx.constraintlayout.motion.widget.Key) r11
            java.util.HashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r7 = r11.mCustomConstraints
            if (r7 != 0) goto L_0x00ef
            r11 = r17
            r7 = 0
            goto L_0x00d7
        L_0x00ef:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r7 = r11.mCustomConstraints
            java.lang.Object r7 = r7.get(r14)
            androidx.constraintlayout.widget.ConstraintAttribute r7 = (androidx.constraintlayout.widget.ConstraintAttribute) r7
            if (r7 == 0) goto L_0x0101
            r18 = r1
            int r1 = r11.mFramePosition
            r13.append(r1, r7)
            goto L_0x0103
        L_0x0101:
            r18 = r1
        L_0x0103:
            r11 = r17
            r1 = r18
            r7 = 0
            goto L_0x00d7
        L_0x0109:
            r18 = r1
            r17 = r11
            androidx.constraintlayout.motion.utils.ViewSpline r1 = androidx.constraintlayout.motion.utils.ViewSpline.makeCustomSpline(r12, r13)
            goto L_0x011a
        L_0x0112:
            r18 = r1
            r17 = r11
            androidx.constraintlayout.motion.utils.ViewSpline r1 = androidx.constraintlayout.motion.utils.ViewSpline.makeSpline(r12)
        L_0x011a:
            if (r1 != 0) goto L_0x0122
            r11 = r17
            r1 = r18
            r7 = 0
            goto L_0x00b4
        L_0x0122:
            r1.setType(r12)
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewSpline> r7 = r0.mAttributesMap
            r7.put(r12, r1)
            r11 = r17
            r1 = r18
            r7 = 0
            goto L_0x00b4
        L_0x0130:
            r18 = r1
            r17 = r11
            java.util.ArrayList<androidx.constraintlayout.motion.widget.Key> r1 = r0.mKeyList
            if (r1 == 0) goto L_0x0154
            java.util.ArrayList<androidx.constraintlayout.motion.widget.Key> r1 = r0.mKeyList
            java.util.Iterator r1 = r1.iterator()
        L_0x013e:
            boolean r7 = r1.hasNext()
            if (r7 == 0) goto L_0x0154
            java.lang.Object r7 = r1.next()
            androidx.constraintlayout.motion.widget.Key r7 = (androidx.constraintlayout.motion.widget.Key) r7
            boolean r8 = r7 instanceof androidx.constraintlayout.motion.widget.KeyAttributes
            if (r8 == 0) goto L_0x0153
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewSpline> r8 = r0.mAttributesMap
            r7.addValues(r8)
        L_0x0153:
            goto L_0x013e
        L_0x0154:
            androidx.constraintlayout.motion.widget.MotionConstrainedPoint r1 = r0.mStartPoint
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewSpline> r7 = r0.mAttributesMap
            r8 = 0
            r1.addValues(r7, r8)
            androidx.constraintlayout.motion.widget.MotionConstrainedPoint r1 = r0.mEndPoint
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewSpline> r7 = r0.mAttributesMap
            r8 = 100
            r1.addValues(r7, r8)
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewSpline> r1 = r0.mAttributesMap
            java.util.Set r1 = r1.keySet()
            java.util.Iterator r1 = r1.iterator()
        L_0x016f:
            boolean r7 = r1.hasNext()
            if (r7 == 0) goto L_0x01a0
            java.lang.Object r7 = r1.next()
            java.lang.String r7 = (java.lang.String) r7
            r8 = 0
            boolean r11 = r5.containsKey(r7)
            if (r11 == 0) goto L_0x018e
            java.lang.Object r11 = r5.get(r7)
            java.lang.Integer r11 = (java.lang.Integer) r11
            if (r11 == 0) goto L_0x018e
            int r8 = r11.intValue()
        L_0x018e:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewSpline> r11 = r0.mAttributesMap
            java.lang.Object r11 = r11.get(r7)
            androidx.constraintlayout.core.motion.utils.SplineSet r11 = (androidx.constraintlayout.core.motion.utils.SplineSet) r11
            if (r11 == 0) goto L_0x019b
            r11.setup(r8)
        L_0x019b:
            goto L_0x016f
        L_0x019c:
            r18 = r1
            r17 = r11
        L_0x01a0:
            boolean r1 = r2.isEmpty()
            if (r1 != 0) goto L_0x0283
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewTimeCycle> r1 = r0.mTimeCycleAttributesMap
            if (r1 != 0) goto L_0x01b1
            java.util.HashMap r1 = new java.util.HashMap
            r1.<init>()
            r0.mTimeCycleAttributesMap = r1
        L_0x01b1:
            java.util.Iterator r1 = r2.iterator()
        L_0x01b5:
            boolean r7 = r1.hasNext()
            if (r7 == 0) goto L_0x022b
            java.lang.Object r7 = r1.next()
            java.lang.String r7 = (java.lang.String) r7
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewTimeCycle> r8 = r0.mTimeCycleAttributesMap
            boolean r8 = r8.containsKey(r7)
            if (r8 == 0) goto L_0x01ca
            goto L_0x01b5
        L_0x01ca:
            r8 = 0
            boolean r11 = r7.startsWith(r10)
            if (r11 == 0) goto L_0x0213
            android.util.SparseArray r11 = new android.util.SparseArray
            r11.<init>()
            java.lang.String[] r12 = r7.split(r9)
            r12 = r12[r17]
            java.util.ArrayList<androidx.constraintlayout.motion.widget.Key> r13 = r0.mKeyList
            java.util.Iterator r13 = r13.iterator()
        L_0x01e2:
            boolean r14 = r13.hasNext()
            if (r14 == 0) goto L_0x020a
            java.lang.Object r14 = r13.next()
            androidx.constraintlayout.motion.widget.Key r14 = (androidx.constraintlayout.motion.widget.Key) r14
            java.util.HashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r15 = r14.mCustomConstraints
            if (r15 != 0) goto L_0x01f3
            goto L_0x01e2
        L_0x01f3:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r15 = r14.mCustomConstraints
            java.lang.Object r15 = r15.get(r12)
            androidx.constraintlayout.widget.ConstraintAttribute r15 = (androidx.constraintlayout.widget.ConstraintAttribute) r15
            if (r15 == 0) goto L_0x0205
            r19 = r1
            int r1 = r14.mFramePosition
            r11.append(r1, r15)
            goto L_0x0207
        L_0x0205:
            r19 = r1
        L_0x0207:
            r1 = r19
            goto L_0x01e2
        L_0x020a:
            r19 = r1
            androidx.constraintlayout.motion.utils.ViewTimeCycle r1 = androidx.constraintlayout.motion.utils.ViewTimeCycle.makeCustomSpline(r7, r11)
            r11 = r36
            goto L_0x021b
        L_0x0213:
            r19 = r1
            r11 = r36
            androidx.constraintlayout.motion.utils.ViewTimeCycle r1 = androidx.constraintlayout.motion.utils.ViewTimeCycle.makeSpline(r7, r11)
        L_0x021b:
            if (r1 != 0) goto L_0x0220
            r1 = r19
            goto L_0x01b5
        L_0x0220:
            r1.setType(r7)
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewTimeCycle> r8 = r0.mTimeCycleAttributesMap
            r8.put(r7, r1)
            r1 = r19
            goto L_0x01b5
        L_0x022b:
            r11 = r36
            java.util.ArrayList<androidx.constraintlayout.motion.widget.Key> r1 = r0.mKeyList
            if (r1 == 0) goto L_0x0250
            java.util.ArrayList<androidx.constraintlayout.motion.widget.Key> r1 = r0.mKeyList
            java.util.Iterator r1 = r1.iterator()
        L_0x0237:
            boolean r7 = r1.hasNext()
            if (r7 == 0) goto L_0x0250
            java.lang.Object r7 = r1.next()
            androidx.constraintlayout.motion.widget.Key r7 = (androidx.constraintlayout.motion.widget.Key) r7
            boolean r8 = r7 instanceof androidx.constraintlayout.motion.widget.KeyTimeCycle
            if (r8 == 0) goto L_0x024f
            r8 = r7
            androidx.constraintlayout.motion.widget.KeyTimeCycle r8 = (androidx.constraintlayout.motion.widget.KeyTimeCycle) r8
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewTimeCycle> r9 = r0.mTimeCycleAttributesMap
            r8.addTimeValues(r9)
        L_0x024f:
            goto L_0x0237
        L_0x0250:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewTimeCycle> r1 = r0.mTimeCycleAttributesMap
            java.util.Set r1 = r1.keySet()
            java.util.Iterator r1 = r1.iterator()
        L_0x025a:
            boolean r7 = r1.hasNext()
            if (r7 == 0) goto L_0x0285
            java.lang.Object r7 = r1.next()
            java.lang.String r7 = (java.lang.String) r7
            r8 = 0
            boolean r9 = r5.containsKey(r7)
            if (r9 == 0) goto L_0x0277
            java.lang.Object r9 = r5.get(r7)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r8 = r9.intValue()
        L_0x0277:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewTimeCycle> r9 = r0.mTimeCycleAttributesMap
            java.lang.Object r9 = r9.get(r7)
            androidx.constraintlayout.motion.utils.ViewTimeCycle r9 = (androidx.constraintlayout.motion.utils.ViewTimeCycle) r9
            r9.setup(r8)
            goto L_0x025a
        L_0x0283:
            r11 = r36
        L_0x0285:
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionPaths> r1 = r0.mMotionPaths
            int r1 = r1.size()
            r7 = 2
            int r1 = r1 + r7
            androidx.constraintlayout.motion.widget.MotionPaths[] r1 = new androidx.constraintlayout.motion.widget.MotionPaths[r1]
            r8 = 1
            androidx.constraintlayout.motion.widget.MotionPaths r9 = r0.mStartMotionPath
            r16 = 0
            r1[r16] = r9
            int r9 = r1.length
            int r9 = r9 + -1
            androidx.constraintlayout.motion.widget.MotionPaths r13 = r0.mEndMotionPath
            r1[r9] = r13
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionPaths> r9 = r0.mMotionPaths
            int r9 = r9.size()
            if (r9 <= 0) goto L_0x02ad
            int r9 = r0.mCurveFitType
            r13 = -1
            if (r9 != r13) goto L_0x02ad
            r9 = 0
            r0.mCurveFitType = r9
        L_0x02ad:
            java.util.ArrayList<androidx.constraintlayout.motion.widget.MotionPaths> r9 = r0.mMotionPaths
            java.util.Iterator r9 = r9.iterator()
        L_0x02b3:
            boolean r13 = r9.hasNext()
            if (r13 == 0) goto L_0x02c5
            java.lang.Object r13 = r9.next()
            androidx.constraintlayout.motion.widget.MotionPaths r13 = (androidx.constraintlayout.motion.widget.MotionPaths) r13
            int r14 = r8 + 1
            r1[r8] = r13
            r8 = r14
            goto L_0x02b3
        L_0x02c5:
            r9 = 18
            java.util.HashSet r13 = new java.util.HashSet
            r13.<init>()
            androidx.constraintlayout.motion.widget.MotionPaths r14 = r0.mEndMotionPath
            java.util.LinkedHashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r14 = r14.mAttributes
            java.util.Set r14 = r14.keySet()
            java.util.Iterator r14 = r14.iterator()
        L_0x02d8:
            boolean r15 = r14.hasNext()
            if (r15 == 0) goto L_0x030a
            java.lang.Object r15 = r14.next()
            java.lang.String r15 = (java.lang.String) r15
            androidx.constraintlayout.motion.widget.MotionPaths r7 = r0.mStartMotionPath
            java.util.LinkedHashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r7 = r7.mAttributes
            boolean r7 = r7.containsKey(r15)
            if (r7 == 0) goto L_0x0308
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.StringBuilder r7 = r7.append(r10)
            java.lang.StringBuilder r7 = r7.append(r15)
            java.lang.String r7 = r7.toString()
            boolean r7 = r3.contains(r7)
            if (r7 != 0) goto L_0x0308
            r13.add(r15)
        L_0x0308:
            r7 = 2
            goto L_0x02d8
        L_0x030a:
            r7 = 0
            java.lang.String[] r10 = new java.lang.String[r7]
            java.lang.Object[] r7 = r13.toArray(r10)
            java.lang.String[] r7 = (java.lang.String[]) r7
            r0.mAttributeNames = r7
            java.lang.String[] r7 = r0.mAttributeNames
            int r7 = r7.length
            int[] r7 = new int[r7]
            r0.mAttributeInterpolatorCount = r7
            r7 = 0
        L_0x031d:
            java.lang.String[] r10 = r0.mAttributeNames
            int r10 = r10.length
            if (r7 >= r10) goto L_0x0366
            java.lang.String[] r10 = r0.mAttributeNames
            r10 = r10[r7]
            int[] r14 = r0.mAttributeInterpolatorCount
            r16 = 0
            r14[r7] = r16
            r14 = 0
        L_0x032d:
            int r15 = r1.length
            if (r14 >= r15) goto L_0x035f
            r15 = r1[r14]
            java.util.LinkedHashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r15 = r15.mAttributes
            boolean r15 = r15.containsKey(r10)
            if (r15 == 0) goto L_0x0358
            r15 = r1[r14]
            java.util.LinkedHashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r15 = r15.mAttributes
            java.lang.Object r15 = r15.get(r10)
            androidx.constraintlayout.widget.ConstraintAttribute r15 = (androidx.constraintlayout.widget.ConstraintAttribute) r15
            if (r15 == 0) goto L_0x0355
            r20 = r2
            int[] r2 = r0.mAttributeInterpolatorCount
            r21 = r2[r7]
            int r22 = r15.numberOfInterpolatedValues()
            int r21 = r21 + r22
            r2[r7] = r21
            goto L_0x0361
        L_0x0355:
            r20 = r2
            goto L_0x035a
        L_0x0358:
            r20 = r2
        L_0x035a:
            int r14 = r14 + 1
            r2 = r20
            goto L_0x032d
        L_0x035f:
            r20 = r2
        L_0x0361:
            int r7 = r7 + 1
            r2 = r20
            goto L_0x031d
        L_0x0366:
            r20 = r2
            r16 = 0
            r2 = r1[r16]
            int r2 = r2.mPathMotionArc
            int r7 = androidx.constraintlayout.motion.widget.Key.UNSET
            if (r2 == r7) goto L_0x0375
            r2 = r17
            goto L_0x0376
        L_0x0375:
            r2 = 0
        L_0x0376:
            java.lang.String[] r7 = r0.mAttributeNames
            int r7 = r7.length
            int r7 = r7 + r9
            boolean[] r7 = new boolean[r7]
            r10 = 1
        L_0x037d:
            int r14 = r1.length
            if (r10 >= r14) goto L_0x0392
            r14 = r1[r10]
            int r15 = r10 + -1
            r15 = r1[r15]
            r21 = r3
            java.lang.String[] r3 = r0.mAttributeNames
            r14.different(r15, r7, r3, r2)
            int r10 = r10 + 1
            r3 = r21
            goto L_0x037d
        L_0x0392:
            r21 = r3
            r3 = 0
            r8 = 1
        L_0x0396:
            int r10 = r7.length
            if (r8 >= r10) goto L_0x03a2
            boolean r10 = r7[r8]
            if (r10 == 0) goto L_0x039f
            int r3 = r3 + 1
        L_0x039f:
            int r8 = r8 + 1
            goto L_0x0396
        L_0x03a2:
            int[] r8 = new int[r3]
            r0.mInterpolateVariables = r8
            r8 = 2
            int r10 = java.lang.Math.max(r8, r3)
            double[] r8 = new double[r10]
            r0.mInterpolateData = r8
            double[] r8 = new double[r10]
            r0.mInterpolateVelocity = r8
            r3 = 0
            r8 = 1
        L_0x03b5:
            int r14 = r7.length
            if (r8 >= r14) goto L_0x03c6
            boolean r14 = r7[r8]
            if (r14 == 0) goto L_0x03c3
            int[] r14 = r0.mInterpolateVariables
            int r15 = r3 + 1
            r14[r3] = r8
            r3 = r15
        L_0x03c3:
            int r8 = r8 + 1
            goto L_0x03b5
        L_0x03c6:
            int r8 = r1.length
            int[] r14 = r0.mInterpolateVariables
            int r14 = r14.length
            r22 = r2
            r15 = 2
            int[] r2 = new int[r15]
            r2[r17] = r14
            r16 = 0
            r2[r16] = r8
            java.lang.Class r8 = java.lang.Double.TYPE
            java.lang.Object r2 = java.lang.reflect.Array.newInstance(r8, r2)
            double[][] r2 = (double[][]) r2
            int r8 = r1.length
            double[] r8 = new double[r8]
            r14 = 0
        L_0x03e1:
            int r15 = r1.length
            if (r14 >= r15) goto L_0x03ff
            r15 = r1[r14]
            r23 = r3
            r3 = r2[r14]
            r24 = r4
            int[] r4 = r0.mInterpolateVariables
            r15.fillStandard(r3, r4)
            r3 = r1[r14]
            float r3 = r3.mTime
            double r3 = (double) r3
            r8[r14] = r3
            int r14 = r14 + 1
            r3 = r23
            r4 = r24
            goto L_0x03e1
        L_0x03ff:
            r23 = r3
            r24 = r4
            r3 = 0
        L_0x0404:
            int[] r4 = r0.mInterpolateVariables
            int r4 = r4.length
            if (r3 >= r4) goto L_0x045d
            int[] r4 = r0.mInterpolateVariables
            r4 = r4[r3]
            java.lang.String[] r14 = androidx.constraintlayout.motion.widget.MotionPaths.sNames
            int r14 = r14.length
            if (r4 >= r14) goto L_0x0456
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String[] r15 = androidx.constraintlayout.motion.widget.MotionPaths.sNames
            r25 = r3
            int[] r3 = r0.mInterpolateVariables
            r3 = r3[r25]
            r3 = r15[r3]
            java.lang.StringBuilder r3 = r14.append(r3)
            java.lang.String r14 = " ["
            java.lang.StringBuilder r3 = r3.append(r14)
            java.lang.String r3 = r3.toString()
            r14 = 0
        L_0x0430:
            int r15 = r1.length
            if (r14 >= r15) goto L_0x0451
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.StringBuilder r15 = r15.append(r3)
            r26 = r2[r14]
            r28 = r3
            r27 = r4
            r3 = r26[r25]
            java.lang.StringBuilder r3 = r15.append(r3)
            java.lang.String r3 = r3.toString()
            int r14 = r14 + 1
            r4 = r27
            goto L_0x0430
        L_0x0451:
            r28 = r3
            r27 = r4
            goto L_0x045a
        L_0x0456:
            r25 = r3
            r27 = r4
        L_0x045a:
            int r3 = r25 + 1
            goto L_0x0404
        L_0x045d:
            r25 = r3
            java.lang.String[] r3 = r0.mAttributeNames
            int r3 = r3.length
            int r3 = r3 + 1
            androidx.constraintlayout.core.motion.utils.CurveFit[] r3 = new androidx.constraintlayout.core.motion.utils.CurveFit[r3]
            r0.mSpline = r3
            r3 = 0
        L_0x0469:
            java.lang.String[] r4 = r0.mAttributeNames
            int r4 = r4.length
            if (r3 >= r4) goto L_0x04f6
            r4 = 0
            r14 = 0
            r15 = 0
            r25 = r3
            java.lang.String[] r3 = r0.mAttributeNames
            r3 = r3[r25]
            r26 = 0
            r31 = r26
            r26 = r5
            r5 = r31
        L_0x047f:
            r27 = r6
            int r6 = r1.length
            if (r5 >= r6) goto L_0x04d2
            r6 = r1[r5]
            boolean r6 = r6.hasCustomData(r3)
            if (r6 == 0) goto L_0x04c7
            if (r14 != 0) goto L_0x04b1
            int r6 = r1.length
            double[] r15 = new double[r6]
            int r6 = r1.length
            r28 = r5
            r5 = r1[r28]
            int r5 = r5.getCustomDataCount(r3)
            r29 = r5
            r30 = r6
            r5 = 2
            int[] r6 = new int[r5]
            r6[r17] = r29
            r16 = 0
            r6[r16] = r30
            java.lang.Class r5 = java.lang.Double.TYPE
            java.lang.Object r5 = java.lang.reflect.Array.newInstance(r5, r6)
            r14 = r5
            double[][] r14 = (double[][]) r14
            goto L_0x04b3
        L_0x04b1:
            r28 = r5
        L_0x04b3:
            r5 = r1[r28]
            float r5 = r5.mTime
            double r5 = (double) r5
            r15[r4] = r5
            r5 = r1[r28]
            r6 = r14[r4]
            r29 = r7
            r7 = 0
            r5.getCustomData(r3, r6, r7)
            int r4 = r4 + 1
            goto L_0x04cb
        L_0x04c7:
            r28 = r5
            r29 = r7
        L_0x04cb:
            int r5 = r28 + 1
            r6 = r27
            r7 = r29
            goto L_0x047f
        L_0x04d2:
            r28 = r5
            r29 = r7
            double[] r5 = java.util.Arrays.copyOf(r15, r4)
            java.lang.Object[] r6 = java.util.Arrays.copyOf(r14, r4)
            double[][] r6 = (double[][]) r6
            androidx.constraintlayout.core.motion.utils.CurveFit[] r7 = r0.mSpline
            int r14 = r25 + 1
            int r15 = r0.mCurveFitType
            androidx.constraintlayout.core.motion.utils.CurveFit r15 = androidx.constraintlayout.core.motion.utils.CurveFit.get(r15, r5, r6)
            r7[r14] = r15
            int r3 = r25 + 1
            r5 = r26
            r6 = r27
            r7 = r29
            goto L_0x0469
        L_0x04f6:
            r25 = r3
            r26 = r5
            r27 = r6
            r29 = r7
            androidx.constraintlayout.core.motion.utils.CurveFit[] r3 = r0.mSpline
            int r4 = r0.mCurveFitType
            androidx.constraintlayout.core.motion.utils.CurveFit r4 = androidx.constraintlayout.core.motion.utils.CurveFit.get(r4, r8, r2)
            r16 = 0
            r3[r16] = r4
            r3 = r1[r16]
            int r3 = r3.mPathMotionArc
            int r4 = androidx.constraintlayout.motion.widget.Key.UNSET
            if (r3 == r4) goto L_0x0562
            int r3 = r1.length
            int[] r4 = new int[r3]
            double[] r5 = new double[r3]
            r15 = 2
            int[] r6 = new int[r15]
            r6[r17] = r15
            r16 = 0
            r6[r16] = r3
            java.lang.Class r7 = java.lang.Double.TYPE
            java.lang.Object r6 = java.lang.reflect.Array.newInstance(r7, r6)
            double[][] r6 = (double[][]) r6
            r7 = 0
        L_0x0529:
            if (r7 >= r3) goto L_0x0557
            r14 = r1[r7]
            int r14 = r14.mPathMotionArc
            r4[r7] = r14
            r14 = r1[r7]
            float r14 = r14.mTime
            double r14 = (double) r14
            r5[r7] = r14
            r14 = r6[r7]
            r15 = r1[r7]
            float r15 = r15.mX
            r19 = r1
            r25 = r2
            double r1 = (double) r15
            r16 = 0
            r14[r16] = r1
            r1 = r6[r7]
            r2 = r19[r7]
            float r2 = r2.mY
            double r14 = (double) r2
            r1[r17] = r14
            int r7 = r7 + 1
            r1 = r19
            r2 = r25
            goto L_0x0529
        L_0x0557:
            r19 = r1
            r25 = r2
            androidx.constraintlayout.core.motion.utils.CurveFit r1 = androidx.constraintlayout.core.motion.utils.CurveFit.getArc(r4, r5, r6)
            r0.mArcSpline = r1
            goto L_0x0566
        L_0x0562:
            r19 = r1
            r25 = r2
        L_0x0566:
            r1 = 2143289344(0x7fc00000, float:NaN)
            java.util.HashMap r2 = new java.util.HashMap
            r2.<init>()
            r0.mCycleMap = r2
            java.util.ArrayList<androidx.constraintlayout.motion.widget.Key> r2 = r0.mKeyList
            if (r2 == 0) goto L_0x05dc
            java.util.Iterator r2 = r24.iterator()
        L_0x0577:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x05a3
            java.lang.Object r3 = r2.next()
            java.lang.String r3 = (java.lang.String) r3
            androidx.constraintlayout.motion.utils.ViewOscillator r4 = androidx.constraintlayout.motion.utils.ViewOscillator.makeSpline(r3)
            if (r4 != 0) goto L_0x058a
            goto L_0x0577
        L_0x058a:
            boolean r5 = r4.variesByPath()
            if (r5 == 0) goto L_0x059a
            boolean r5 = java.lang.Float.isNaN(r1)
            if (r5 == 0) goto L_0x059a
            float r1 = r0.getPreCycleDistance()
        L_0x059a:
            r4.setType(r3)
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewOscillator> r5 = r0.mCycleMap
            r5.put(r3, r4)
            goto L_0x0577
        L_0x05a3:
            java.util.ArrayList<androidx.constraintlayout.motion.widget.Key> r2 = r0.mKeyList
            java.util.Iterator r2 = r2.iterator()
        L_0x05a9:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x05c2
            java.lang.Object r3 = r2.next()
            androidx.constraintlayout.motion.widget.Key r3 = (androidx.constraintlayout.motion.widget.Key) r3
            boolean r4 = r3 instanceof androidx.constraintlayout.motion.widget.KeyCycle
            if (r4 == 0) goto L_0x05c1
            r4 = r3
            androidx.constraintlayout.motion.widget.KeyCycle r4 = (androidx.constraintlayout.motion.widget.KeyCycle) r4
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewOscillator> r5 = r0.mCycleMap
            r4.addCycleValues(r5)
        L_0x05c1:
            goto L_0x05a9
        L_0x05c2:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewOscillator> r2 = r0.mCycleMap
            java.util.Collection r2 = r2.values()
            java.util.Iterator r2 = r2.iterator()
        L_0x05cc:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x05dc
            java.lang.Object r3 = r2.next()
            androidx.constraintlayout.motion.utils.ViewOscillator r3 = (androidx.constraintlayout.motion.utils.ViewOscillator) r3
            r3.setup(r1)
            goto L_0x05cc
        L_0x05dc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionController.setup(int, int, float, long):void");
    }

    public String toString() {
        return " start: x: " + this.mStartMotionPath.mX + " y: " + this.mStartMotionPath.mY + " end: x: " + this.mEndMotionPath.mX + " y: " + this.mEndMotionPath.mY;
    }

    private void readView(MotionPaths motionPaths) {
        motionPaths.setBounds((float) ((int) this.mView.getX()), (float) ((int) this.mView.getY()), (float) this.mView.getWidth(), (float) this.mView.getHeight());
    }

    public void setView(View view) {
        this.mView = view;
        this.mId = view.getId();
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp instanceof ConstraintLayout.LayoutParams) {
            this.mConstraintTag = ((ConstraintLayout.LayoutParams) lp).getConstraintTag();
        }
    }

    public View getView() {
        return this.mView;
    }

    /* access modifiers changed from: package-private */
    public void setStartCurrentState(View v) {
        this.mStartMotionPath.mTime = 0.0f;
        this.mStartMotionPath.mPosition = 0.0f;
        this.mStartMotionPath.setBounds(v.getX(), v.getY(), (float) v.getWidth(), (float) v.getHeight());
        this.mStartPoint.setState(v);
    }

    public void setStartState(ViewState rect, View v, int rotation, int preWidth, int preHeight) {
        this.mStartMotionPath.mTime = 0.0f;
        this.mStartMotionPath.mPosition = 0.0f;
        Rect r = new Rect();
        switch (rotation) {
            case 1:
                int cx = rect.left + rect.right;
                r.left = ((rect.top + rect.bottom) - rect.width()) / 2;
                r.top = preWidth - ((rect.height() + cx) / 2);
                r.right = r.left + rect.width();
                r.bottom = r.top + rect.height();
                break;
            case 2:
                int cx2 = rect.left + rect.right;
                r.left = preHeight - ((rect.width() + (rect.top + rect.bottom)) / 2);
                r.top = (cx2 - rect.height()) / 2;
                r.right = r.left + rect.width();
                r.bottom = r.top + rect.height();
                break;
        }
        this.mStartMotionPath.setBounds((float) r.left, (float) r.top, (float) r.width(), (float) r.height());
        this.mStartPoint.setState(r, v, rotation, rect.rotation);
    }

    /* access modifiers changed from: package-private */
    public void rotate(Rect rect, Rect out, int rotation, int preHeight, int preWidth) {
        switch (rotation) {
            case 1:
                int cx = rect.left + rect.right;
                out.left = ((rect.top + rect.bottom) - rect.width()) / 2;
                out.top = preWidth - ((rect.height() + cx) / 2);
                out.right = out.left + rect.width();
                out.bottom = out.top + rect.height();
                return;
            case 2:
                int cx2 = rect.left + rect.right;
                out.left = preHeight - ((rect.width() + (rect.top + rect.bottom)) / 2);
                out.top = (cx2 - rect.height()) / 2;
                out.right = out.left + rect.width();
                out.bottom = out.top + rect.height();
                return;
            case 3:
                int cx3 = rect.left + rect.right;
                int i = rect.top + rect.bottom;
                out.left = ((rect.height() / 2) + rect.top) - (cx3 / 2);
                out.top = preWidth - ((rect.height() + cx3) / 2);
                out.right = out.left + rect.width();
                out.bottom = out.top + rect.height();
                return;
            case 4:
                int cx4 = rect.left + rect.right;
                out.left = preHeight - ((rect.width() + (rect.bottom + rect.top)) / 2);
                out.top = (cx4 - rect.height()) / 2;
                out.right = out.left + rect.width();
                out.bottom = out.top + rect.height();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public void setStartState(Rect cw, ConstraintSet constraintSet, int parentWidth, int parentHeight) {
        Rect cw2;
        MotionController motionController;
        int rotate = constraintSet.mRotate;
        if (rotate != 0) {
            motionController = this;
            cw2 = cw;
            motionController.rotate(cw2, this.mTempRect, rotate, parentWidth, parentHeight);
        } else {
            motionController = this;
            cw2 = cw;
            int i = parentWidth;
            int i2 = parentHeight;
        }
        motionController.mStartMotionPath.mTime = 0.0f;
        motionController.mStartMotionPath.mPosition = 0.0f;
        readView(motionController.mStartMotionPath);
        motionController.mStartMotionPath.setBounds((float) cw2.left, (float) cw2.top, (float) cw2.width(), (float) cw2.height());
        ConstraintSet.Constraint constraint = constraintSet.getParameters(motionController.mId);
        motionController.mStartMotionPath.applyParameters(constraint);
        motionController.mMotionStagger = constraint.motion.mMotionStagger;
        motionController.mStartPoint.setState(cw2, constraintSet, rotate, motionController.mId);
        motionController.mTransformPivotTarget = constraint.transform.transformPivotTarget;
        motionController.mQuantizeMotionSteps = constraint.motion.mQuantizeMotionSteps;
        motionController.mQuantizeMotionPhase = constraint.motion.mQuantizeMotionPhase;
        motionController.mQuantizeMotionInterpolator = getInterpolator(motionController.mView.getContext(), constraint.motion.mQuantizeInterpolatorType, constraint.motion.mQuantizeInterpolatorString, constraint.motion.mQuantizeInterpolatorID);
    }

    private static Interpolator getInterpolator(Context context, int type, String interpolatorString, int id) {
        switch (type) {
            case -3:
                return null;
            case -2:
                return AnimationUtils.loadInterpolator(context, id);
            case -1:
                final Easing easing = Easing.getInterpolator(interpolatorString);
                return new Interpolator() {
                    public float getInterpolation(float v) {
                        return (float) Easing.this.get((double) v);
                    }
                };
            case 0:
                return new AccelerateDecelerateInterpolator();
            case 1:
                return new AccelerateInterpolator();
            case 2:
                return new DecelerateInterpolator();
            case 3:
                return null;
            case 4:
                return new BounceInterpolator();
            case 5:
                return new OvershootInterpolator();
            default:
                return null;
        }
    }

    /* access modifiers changed from: package-private */
    public void setEndState(Rect cw, ConstraintSet constraintSet, int parentWidth, int parentHeight) {
        MotionController motionController;
        int rotate = constraintSet.mRotate;
        if (rotate != 0) {
            motionController = this;
            motionController.rotate(cw, this.mTempRect, rotate, parentWidth, parentHeight);
            cw = motionController.mTempRect;
        } else {
            motionController = this;
            Rect rect = cw;
            int i = parentWidth;
            int i2 = parentHeight;
        }
        motionController.mEndMotionPath.mTime = 1.0f;
        motionController.mEndMotionPath.mPosition = 1.0f;
        readView(motionController.mEndMotionPath);
        motionController.mEndMotionPath.setBounds((float) cw.left, (float) cw.top, (float) cw.width(), (float) cw.height());
        motionController.mEndMotionPath.applyParameters(constraintSet.getParameters(motionController.mId));
        motionController.mEndPoint.setState(cw, constraintSet, rotate, motionController.mId);
    }

    /* access modifiers changed from: package-private */
    public void setBothStates(View v) {
        this.mStartMotionPath.mTime = 0.0f;
        this.mStartMotionPath.mPosition = 0.0f;
        this.mNoMovement = true;
        this.mStartMotionPath.setBounds(v.getX(), v.getY(), (float) v.getWidth(), (float) v.getHeight());
        this.mEndMotionPath.setBounds(v.getX(), v.getY(), (float) v.getWidth(), (float) v.getHeight());
        this.mStartPoint.setState(v);
        this.mEndPoint.setState(v);
    }

    private float getAdjustedPosition(float position, float[] velocity) {
        if (velocity != null) {
            velocity[0] = 1.0f;
        } else if (((double) this.mStaggerScale) != 1.0d) {
            if (position < this.mStaggerOffset) {
                position = 0.0f;
            }
            if (position > this.mStaggerOffset && ((double) position) < 1.0d) {
                position = Math.min((position - this.mStaggerOffset) * this.mStaggerScale, 1.0f);
            }
        }
        float adjusted = position;
        Easing easing = this.mStartMotionPath.mKeyFrameEasing;
        float start = 0.0f;
        float end = Float.NaN;
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        while (it.hasNext()) {
            MotionPaths frame = it.next();
            if (frame.mKeyFrameEasing != null) {
                if (frame.mTime < position) {
                    easing = frame.mKeyFrameEasing;
                    start = frame.mTime;
                } else if (Float.isNaN(end)) {
                    end = frame.mTime;
                }
            }
        }
        if (easing != null) {
            if (Float.isNaN(end)) {
                end = 1.0f;
            }
            float offset = (position - start) / (end - start);
            adjusted = ((end - start) * ((float) easing.get((double) offset))) + start;
            if (velocity != null) {
                velocity[0] = (float) easing.getDiff((double) offset);
            }
        }
        return adjusted;
    }

    /* access modifiers changed from: package-private */
    public void endTrigger(boolean start) {
        if ("button".equals(Debug.getName(this.mView)) && this.mKeyTriggers != null) {
            for (KeyTrigger conditionallyFire : this.mKeyTriggers) {
                conditionallyFire.conditionallyFire(start ? -100.0f : 100.0f, this.mView);
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v79, resolved type: androidx.constraintlayout.motion.utils.ViewTimeCycle} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v7, resolved type: androidx.constraintlayout.motion.utils.ViewTimeCycle$PathRotate} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean interpolate(android.view.View r24, float r25, long r26, androidx.constraintlayout.core.motion.utils.KeyCache r28) {
        /*
            r23 = this;
            r0 = r23
            r2 = r24
            r1 = 0
            r3 = 0
            r11 = r25
            float r3 = r0.getAdjustedPosition(r11, r3)
            int r4 = r0.mQuantizeMotionSteps
            int r5 = androidx.constraintlayout.motion.widget.Key.UNSET
            r13 = 1065353216(0x3f800000, float:1.0)
            if (r4 == r5) goto L_0x004c
            int r4 = r0.mQuantizeMotionSteps
            float r4 = (float) r4
            float r4 = r13 / r4
            float r5 = r3 / r4
            double r5 = (double) r5
            double r5 = java.lang.Math.floor(r5)
            float r5 = (float) r5
            float r5 = r5 * r4
            float r6 = r3 % r4
            float r6 = r6 / r4
            float r7 = r0.mQuantizeMotionPhase
            boolean r7 = java.lang.Float.isNaN(r7)
            if (r7 != 0) goto L_0x0032
            float r7 = r0.mQuantizeMotionPhase
            float r7 = r7 + r6
            float r6 = r7 % r13
        L_0x0032:
            android.view.animation.Interpolator r7 = r0.mQuantizeMotionInterpolator
            if (r7 == 0) goto L_0x003d
            android.view.animation.Interpolator r7 = r0.mQuantizeMotionInterpolator
            float r6 = r7.getInterpolation(r6)
            goto L_0x0048
        L_0x003d:
            double r7 = (double) r6
            r9 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r7 <= 0) goto L_0x0046
            r7 = r13
            goto L_0x0047
        L_0x0046:
            r7 = 0
        L_0x0047:
            r6 = r7
        L_0x0048:
            float r7 = r6 * r4
            float r3 = r7 + r5
        L_0x004c:
            r4 = 0
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewSpline> r5 = r0.mAttributesMap
            if (r5 == 0) goto L_0x006b
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewSpline> r5 = r0.mAttributesMap
            java.util.Collection r5 = r5.values()
            java.util.Iterator r5 = r5.iterator()
        L_0x005b:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x006b
            java.lang.Object r6 = r5.next()
            androidx.constraintlayout.motion.utils.ViewSpline r6 = (androidx.constraintlayout.motion.utils.ViewSpline) r6
            r6.setProperty(r2, r3)
            goto L_0x005b
        L_0x006b:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewTimeCycle> r5 = r0.mTimeCycleAttributesMap
            if (r5 == 0) goto L_0x009d
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewTimeCycle> r5 = r0.mTimeCycleAttributesMap
            java.util.Collection r5 = r5.values()
            java.util.Iterator r7 = r5.iterator()
            r8 = r1
            r9 = r4
        L_0x007b:
            boolean r1 = r7.hasNext()
            if (r1 == 0) goto L_0x009b
            java.lang.Object r1 = r7.next()
            androidx.constraintlayout.motion.utils.ViewTimeCycle r1 = (androidx.constraintlayout.motion.utils.ViewTimeCycle) r1
            boolean r4 = r1 instanceof androidx.constraintlayout.motion.utils.ViewTimeCycle.PathRotate
            if (r4 == 0) goto L_0x008f
            r9 = r1
            androidx.constraintlayout.motion.utils.ViewTimeCycle$PathRotate r9 = (androidx.constraintlayout.motion.utils.ViewTimeCycle.PathRotate) r9
            goto L_0x007b
        L_0x008f:
            r4 = r26
            r6 = r28
            boolean r10 = r1.setProperty(r2, r3, r4, r6)
            r8 = r8 | r10
            r2 = r24
            goto L_0x007b
        L_0x009b:
            r14 = r8
            goto L_0x009f
        L_0x009d:
            r14 = r1
            r9 = r4
        L_0x009f:
            androidx.constraintlayout.core.motion.utils.CurveFit[] r1 = r0.mSpline
            r15 = 1
            r10 = 0
            if (r1 == 0) goto L_0x0228
            androidx.constraintlayout.core.motion.utils.CurveFit[] r1 = r0.mSpline
            r1 = r1[r10]
            double r4 = (double) r3
            double[] r2 = r0.mInterpolateData
            r1.getPos((double) r4, (double[]) r2)
            androidx.constraintlayout.core.motion.utils.CurveFit[] r1 = r0.mSpline
            r1 = r1[r10]
            double r4 = (double) r3
            double[] r2 = r0.mInterpolateVelocity
            r1.getSlope((double) r4, (double[]) r2)
            androidx.constraintlayout.core.motion.utils.CurveFit r1 = r0.mArcSpline
            if (r1 == 0) goto L_0x00d2
            double[] r1 = r0.mInterpolateData
            int r1 = r1.length
            if (r1 <= 0) goto L_0x00d2
            androidx.constraintlayout.core.motion.utils.CurveFit r1 = r0.mArcSpline
            double r4 = (double) r3
            double[] r2 = r0.mInterpolateData
            r1.getPos((double) r4, (double[]) r2)
            androidx.constraintlayout.core.motion.utils.CurveFit r1 = r0.mArcSpline
            double r4 = (double) r3
            double[] r2 = r0.mInterpolateVelocity
            r1.getSlope((double) r4, (double[]) r2)
        L_0x00d2:
            boolean r1 = r0.mNoMovement
            if (r1 != 0) goto L_0x00ef
            androidx.constraintlayout.motion.widget.MotionPaths r1 = r0.mStartMotionPath
            int[] r4 = r0.mInterpolateVariables
            double[] r5 = r0.mInterpolateData
            double[] r6 = r0.mInterpolateVelocity
            r7 = 0
            boolean r8 = r0.mForceMeasure
            r2 = r3
            r3 = r24
            r1.setView(r2, r3, r4, r5, r6, r7, r8)
            r21 = r3
            r3 = r2
            r2 = r21
            r0.mForceMeasure = r10
            goto L_0x00f1
        L_0x00ef:
            r2 = r24
        L_0x00f1:
            int r1 = r0.mTransformPivotTarget
            int r4 = androidx.constraintlayout.motion.widget.Key.UNSET
            if (r1 == r4) goto L_0x0157
            android.view.View r1 = r0.mTransformPivotView
            if (r1 != 0) goto L_0x0109
            android.view.ViewParent r1 = r2.getParent()
            android.view.View r1 = (android.view.View) r1
            int r4 = r0.mTransformPivotTarget
            android.view.View r4 = r1.findViewById(r4)
            r0.mTransformPivotView = r4
        L_0x0109:
            android.view.View r1 = r0.mTransformPivotView
            if (r1 == 0) goto L_0x0157
            android.view.View r1 = r0.mTransformPivotView
            int r1 = r1.getTop()
            android.view.View r4 = r0.mTransformPivotView
            int r4 = r4.getBottom()
            int r1 = r1 + r4
            float r1 = (float) r1
            r4 = 1073741824(0x40000000, float:2.0)
            float r1 = r1 / r4
            android.view.View r5 = r0.mTransformPivotView
            int r5 = r5.getLeft()
            android.view.View r6 = r0.mTransformPivotView
            int r6 = r6.getRight()
            int r5 = r5 + r6
            float r5 = (float) r5
            float r5 = r5 / r4
            int r4 = r2.getRight()
            int r6 = r2.getLeft()
            int r4 = r4 - r6
            if (r4 <= 0) goto L_0x0157
            int r4 = r2.getBottom()
            int r6 = r2.getTop()
            int r4 = r4 - r6
            if (r4 <= 0) goto L_0x0157
            int r4 = r2.getLeft()
            float r4 = (float) r4
            float r4 = r5 - r4
            int r6 = r2.getTop()
            float r6 = (float) r6
            float r6 = r1 - r6
            r2.setPivotX(r4)
            r2.setPivotY(r6)
        L_0x0157:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewSpline> r1 = r0.mAttributesMap
            if (r1 == 0) goto L_0x0196
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewSpline> r1 = r0.mAttributesMap
            java.util.Collection r1 = r1.values()
            java.util.Iterator r8 = r1.iterator()
        L_0x0165:
            boolean r1 = r8.hasNext()
            if (r1 == 0) goto L_0x0196
            java.lang.Object r1 = r8.next()
            androidx.constraintlayout.core.motion.utils.SplineSet r1 = (androidx.constraintlayout.core.motion.utils.SplineSet) r1
            boolean r4 = r1 instanceof androidx.constraintlayout.motion.utils.ViewSpline.PathRotate
            if (r4 == 0) goto L_0x0191
            double[] r4 = r0.mInterpolateVelocity
            int r4 = r4.length
            if (r4 <= r15) goto L_0x0191
            r4 = r1
            r1 = r4
            androidx.constraintlayout.motion.utils.ViewSpline$PathRotate r1 = (androidx.constraintlayout.motion.utils.ViewSpline.PathRotate) r1
            double[] r5 = r0.mInterpolateVelocity
            r6 = r5[r10]
            double[] r5 = r0.mInterpolateVelocity
            r16 = r5[r15]
            r21 = r16
            r16 = r4
            r4 = r6
            r6 = r21
            r1.setPathRotate(r2, r3, r4, r6)
            goto L_0x0193
        L_0x0191:
            r16 = r1
        L_0x0193:
            r2 = r24
            goto L_0x0165
        L_0x0196:
            if (r9 == 0) goto L_0x01b6
            double[] r1 = r0.mInterpolateVelocity
            r7 = r1[r10]
            double[] r1 = r0.mInterpolateVelocity
            r4 = r1[r15]
            r2 = r24
            r1 = r9
            r12 = r10
            r16 = 0
            r9 = r4
            r5 = r26
            r4 = r3
            r3 = r28
            boolean r7 = r1.setPathRotate(r2, r3, r4, r5, r7, r9)
            r9 = r1
            r3 = r4
            r1 = r14 | r7
            r14 = r1
            goto L_0x01bb
        L_0x01b6:
            r2 = r24
            r12 = r10
            r16 = 0
        L_0x01bb:
            r1 = 1
        L_0x01bc:
            androidx.constraintlayout.core.motion.utils.CurveFit[] r4 = r0.mSpline
            int r4 = r4.length
            if (r1 >= r4) goto L_0x01e3
            androidx.constraintlayout.core.motion.utils.CurveFit[] r4 = r0.mSpline
            r4 = r4[r1]
            double r5 = (double) r3
            float[] r7 = r0.mValuesBuff
            r4.getPos((double) r5, (float[]) r7)
            androidx.constraintlayout.motion.widget.MotionPaths r5 = r0.mStartMotionPath
            java.util.LinkedHashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r5 = r5.mAttributes
            java.lang.String[] r6 = r0.mAttributeNames
            int r7 = r1 + -1
            r6 = r6[r7]
            java.lang.Object r5 = r5.get(r6)
            androidx.constraintlayout.widget.ConstraintAttribute r5 = (androidx.constraintlayout.widget.ConstraintAttribute) r5
            float[] r6 = r0.mValuesBuff
            androidx.constraintlayout.motion.utils.CustomSupport.setInterpolatedValue(r5, r2, r6)
            int r1 = r1 + 1
            goto L_0x01bc
        L_0x01e3:
            androidx.constraintlayout.motion.widget.MotionConstrainedPoint r1 = r0.mStartPoint
            int r1 = r1.mVisibilityMode
            if (r1 != 0) goto L_0x020e
            int r1 = (r3 > r16 ? 1 : (r3 == r16 ? 0 : -1))
            if (r1 > 0) goto L_0x01f5
            androidx.constraintlayout.motion.widget.MotionConstrainedPoint r1 = r0.mStartPoint
            int r1 = r1.mVisibility
            r2.setVisibility(r1)
            goto L_0x020e
        L_0x01f5:
            int r1 = (r3 > r13 ? 1 : (r3 == r13 ? 0 : -1))
            if (r1 < 0) goto L_0x0201
            androidx.constraintlayout.motion.widget.MotionConstrainedPoint r1 = r0.mEndPoint
            int r1 = r1.mVisibility
            r2.setVisibility(r1)
            goto L_0x020e
        L_0x0201:
            androidx.constraintlayout.motion.widget.MotionConstrainedPoint r1 = r0.mEndPoint
            int r1 = r1.mVisibility
            androidx.constraintlayout.motion.widget.MotionConstrainedPoint r4 = r0.mStartPoint
            int r4 = r4.mVisibility
            if (r1 == r4) goto L_0x020e
            r2.setVisibility(r12)
        L_0x020e:
            androidx.constraintlayout.motion.widget.KeyTrigger[] r1 = r0.mKeyTriggers
            if (r1 == 0) goto L_0x0222
            r1 = 0
        L_0x0213:
            androidx.constraintlayout.motion.widget.KeyTrigger[] r4 = r0.mKeyTriggers
            int r4 = r4.length
            if (r1 >= r4) goto L_0x0222
            androidx.constraintlayout.motion.widget.KeyTrigger[] r4 = r0.mKeyTriggers
            r4 = r4[r1]
            r4.conditionallyFire(r3, r2)
            int r1 = r1 + 1
            goto L_0x0213
        L_0x0222:
            r19 = r3
            r16 = r15
            goto L_0x02bb
        L_0x0228:
            r2 = r24
            r12 = r10
            androidx.constraintlayout.motion.widget.MotionPaths r1 = r0.mStartMotionPath
            float r1 = r1.mX
            androidx.constraintlayout.motion.widget.MotionPaths r4 = r0.mEndMotionPath
            float r4 = r4.mX
            androidx.constraintlayout.motion.widget.MotionPaths r5 = r0.mStartMotionPath
            float r5 = r5.mX
            float r4 = r4 - r5
            float r4 = r4 * r3
            float r1 = r1 + r4
            androidx.constraintlayout.motion.widget.MotionPaths r4 = r0.mStartMotionPath
            float r4 = r4.mY
            androidx.constraintlayout.motion.widget.MotionPaths r5 = r0.mEndMotionPath
            float r5 = r5.mY
            androidx.constraintlayout.motion.widget.MotionPaths r6 = r0.mStartMotionPath
            float r6 = r6.mY
            float r5 = r5 - r6
            float r5 = r5 * r3
            float r4 = r4 + r5
            androidx.constraintlayout.motion.widget.MotionPaths r5 = r0.mStartMotionPath
            float r5 = r5.mWidth
            androidx.constraintlayout.motion.widget.MotionPaths r6 = r0.mEndMotionPath
            float r6 = r6.mWidth
            androidx.constraintlayout.motion.widget.MotionPaths r7 = r0.mStartMotionPath
            float r7 = r7.mWidth
            float r6 = r6 - r7
            float r6 = r6 * r3
            float r5 = r5 + r6
            androidx.constraintlayout.motion.widget.MotionPaths r6 = r0.mStartMotionPath
            float r6 = r6.mHeight
            androidx.constraintlayout.motion.widget.MotionPaths r7 = r0.mEndMotionPath
            float r7 = r7.mHeight
            androidx.constraintlayout.motion.widget.MotionPaths r8 = r0.mStartMotionPath
            float r8 = r8.mHeight
            float r7 = r7 - r8
            float r7 = r7 * r3
            float r6 = r6 + r7
            r7 = 1056964608(0x3f000000, float:0.5)
            float r8 = r1 + r7
            int r8 = (int) r8
            float r10 = r4 + r7
            int r10 = (int) r10
            float r13 = r1 + r7
            float r13 = r13 + r5
            int r13 = (int) r13
            float r7 = r7 + r4
            float r7 = r7 + r6
            int r7 = (int) r7
            r16 = r15
            int r15 = r13 - r8
            int r12 = r7 - r10
            r18 = r1
            androidx.constraintlayout.motion.widget.MotionPaths r1 = r0.mEndMotionPath
            float r1 = r1.mWidth
            r19 = r1
            androidx.constraintlayout.motion.widget.MotionPaths r1 = r0.mStartMotionPath
            float r1 = r1.mWidth
            int r1 = (r19 > r1 ? 1 : (r19 == r1 ? 0 : -1))
            if (r1 != 0) goto L_0x02a2
            androidx.constraintlayout.motion.widget.MotionPaths r1 = r0.mEndMotionPath
            float r1 = r1.mHeight
            r19 = r1
            androidx.constraintlayout.motion.widget.MotionPaths r1 = r0.mStartMotionPath
            float r1 = r1.mHeight
            int r1 = (r19 > r1 ? 1 : (r19 == r1 ? 0 : -1))
            if (r1 != 0) goto L_0x02a2
            boolean r1 = r0.mForceMeasure
            if (r1 == 0) goto L_0x029f
            goto L_0x02a2
        L_0x029f:
            r19 = r3
            goto L_0x02b8
        L_0x02a2:
            r1 = 1073741824(0x40000000, float:2.0)
            r19 = r3
            int r3 = android.view.View.MeasureSpec.makeMeasureSpec(r15, r1)
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r12, r1)
            r2.measure(r3, r1)
            r20 = r1
            r1 = 0
            r0.mForceMeasure = r1
        L_0x02b8:
            r2.layout(r8, r10, r13, r7)
        L_0x02bb:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewOscillator> r1 = r0.mCycleMap
            if (r1 == 0) goto L_0x02fa
            java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewOscillator> r1 = r0.mCycleMap
            java.util.Collection r1 = r1.values()
            java.util.Iterator r8 = r1.iterator()
        L_0x02c9:
            boolean r1 = r8.hasNext()
            if (r1 == 0) goto L_0x02f7
            java.lang.Object r1 = r8.next()
            r10 = r1
            androidx.constraintlayout.motion.utils.ViewOscillator r10 = (androidx.constraintlayout.motion.utils.ViewOscillator) r10
            boolean r1 = r10 instanceof androidx.constraintlayout.motion.utils.ViewOscillator.PathRotateSet
            if (r1 == 0) goto L_0x02ed
            r1 = r10
            androidx.constraintlayout.motion.utils.ViewOscillator$PathRotateSet r1 = (androidx.constraintlayout.motion.utils.ViewOscillator.PathRotateSet) r1
            double[] r3 = r0.mInterpolateVelocity
            r17 = 0
            r4 = r3[r17]
            double[] r3 = r0.mInterpolateVelocity
            r6 = r3[r16]
            r3 = r19
            r1.setPathRotate(r2, r3, r4, r6)
            goto L_0x02f4
        L_0x02ed:
            r3 = r19
            r17 = 0
            r10.setProperty(r2, r3)
        L_0x02f4:
            r19 = r3
            goto L_0x02c9
        L_0x02f7:
            r3 = r19
            goto L_0x02fc
        L_0x02fa:
            r3 = r19
        L_0x02fc:
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionController.interpolate(android.view.View, float, long, androidx.constraintlayout.core.motion.utils.KeyCache):boolean");
    }

    /* access modifiers changed from: package-private */
    public void getDpDt(float position, float locationX, float locationY, float[] mAnchorDpDt) {
        float position2 = getAdjustedPosition(position, this.mVelocity);
        if (this.mSpline != null) {
            this.mSpline[0].getSlope((double) position2, this.mInterpolateVelocity);
            this.mSpline[0].getPos((double) position2, this.mInterpolateData);
            float v = this.mVelocity[0];
            for (int i = 0; i < this.mInterpolateVelocity.length; i++) {
                double[] dArr = this.mInterpolateVelocity;
                dArr[i] = dArr[i] * ((double) v);
            }
            if (this.mArcSpline == null) {
                this.mStartMotionPath.setDpDt(locationX, locationY, mAnchorDpDt, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
            } else if (this.mInterpolateData.length > 0) {
                this.mArcSpline.getPos((double) position2, this.mInterpolateData);
                this.mArcSpline.getSlope((double) position2, this.mInterpolateVelocity);
                this.mStartMotionPath.setDpDt(locationX, locationY, mAnchorDpDt, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
            }
        } else {
            float dleft = this.mEndMotionPath.mX - this.mStartMotionPath.mX;
            float dTop = this.mEndMotionPath.mY - this.mStartMotionPath.mY;
            float dWidth = this.mEndMotionPath.mWidth - this.mStartMotionPath.mWidth;
            float dHeight = this.mEndMotionPath.mHeight - this.mStartMotionPath.mHeight;
            mAnchorDpDt[0] = ((1.0f - locationX) * dleft) + ((dleft + dWidth) * locationX);
            mAnchorDpDt[1] = ((1.0f - locationY) * dTop) + ((dTop + dHeight) * locationY);
        }
    }

    /* access modifiers changed from: package-private */
    public void getPostLayoutDvDp(float position, int width, int height, float locationX, float locationY, float[] mAnchorDpDt) {
        VelocityMatrix vmat;
        float position2 = getAdjustedPosition(position, this.mVelocity);
        ViewOscillator osc_sy = null;
        SplineSet trans_x = this.mAttributesMap == null ? null : this.mAttributesMap.get("translationX");
        SplineSet trans_y = this.mAttributesMap == null ? null : this.mAttributesMap.get("translationY");
        SplineSet rotation = this.mAttributesMap == null ? null : this.mAttributesMap.get(Key.ROTATION);
        SplineSet scale_x = this.mAttributesMap == null ? null : this.mAttributesMap.get("scaleX");
        SplineSet scale_y = this.mAttributesMap == null ? null : this.mAttributesMap.get("scaleY");
        ViewOscillator osc_x = this.mCycleMap == null ? null : this.mCycleMap.get("translationX");
        ViewOscillator osc_y = this.mCycleMap == null ? null : this.mCycleMap.get("translationY");
        ViewOscillator osc_r = this.mCycleMap == null ? null : this.mCycleMap.get(Key.ROTATION);
        ViewOscillator osc_sx = this.mCycleMap == null ? null : this.mCycleMap.get("scaleX");
        if (this.mCycleMap != null) {
            osc_sy = this.mCycleMap.get("scaleY");
        }
        VelocityMatrix vmat2 = new VelocityMatrix();
        vmat2.clear();
        vmat2.setRotationVelocity(rotation, position2);
        vmat2.setTranslationVelocity(trans_x, trans_y, position2);
        vmat2.setScaleVelocity(scale_x, scale_y, position2);
        vmat2.setRotationVelocity((KeyCycleOscillator) osc_r, position2);
        vmat2.setTranslationVelocity((KeyCycleOscillator) osc_x, (KeyCycleOscillator) osc_y, position2);
        vmat2.setScaleVelocity((KeyCycleOscillator) osc_sx, (KeyCycleOscillator) osc_sy, position2);
        if (this.mArcSpline != null) {
            if (this.mInterpolateData.length > 0) {
                vmat = vmat2;
                this.mArcSpline.getPos((double) position2, this.mInterpolateData);
                this.mArcSpline.getSlope((double) position2, this.mInterpolateVelocity);
                this.mStartMotionPath.setDpDt(locationX, locationY, mAnchorDpDt, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
            } else {
                vmat = vmat2;
            }
            vmat.applyTransform(locationX, locationY, width, height, mAnchorDpDt);
        } else if (this.mSpline != null) {
            float position3 = getAdjustedPosition(position2, this.mVelocity);
            VelocityMatrix vmat3 = vmat2;
            this.mSpline[0].getSlope((double) position3, this.mInterpolateVelocity);
            this.mSpline[0].getPos((double) position3, this.mInterpolateData);
            float v = this.mVelocity[0];
            int i = 0;
            while (i < this.mInterpolateVelocity.length) {
                double[] dArr = this.mInterpolateVelocity;
                int i2 = i;
                dArr[i2] = dArr[i] * ((double) v);
                i = i2 + 1;
            }
            int i3 = i;
            float f = position3;
            this.mStartMotionPath.setDpDt(locationX, locationY, mAnchorDpDt, this.mInterpolateVariables, this.mInterpolateVelocity, this.mInterpolateData);
            vmat3.applyTransform(locationX, locationY, width, height, mAnchorDpDt);
        } else {
            float dleft = this.mEndMotionPath.mX - this.mStartMotionPath.mX;
            float dTop = this.mEndMotionPath.mY - this.mStartMotionPath.mY;
            mAnchorDpDt[0] = ((1.0f - locationX) * dleft) + ((dleft + (this.mEndMotionPath.mWidth - this.mStartMotionPath.mWidth)) * locationX);
            mAnchorDpDt[1] = ((1.0f - locationY) * dTop) + ((dTop + (this.mEndMotionPath.mHeight - this.mStartMotionPath.mHeight)) * locationY);
            vmat2.clear();
            vmat2.setRotationVelocity(rotation, position2);
            vmat2.setTranslationVelocity(trans_x, trans_y, position2);
            vmat2.setScaleVelocity(scale_x, scale_y, position2);
            vmat2.setRotationVelocity((KeyCycleOscillator) osc_r, position2);
            vmat2.setTranslationVelocity((KeyCycleOscillator) osc_x, (KeyCycleOscillator) osc_y, position2);
            vmat2.setScaleVelocity((KeyCycleOscillator) osc_sx, (KeyCycleOscillator) osc_sy, position2);
            vmat2.applyTransform(locationX, locationY, width, height, mAnchorDpDt);
        }
    }

    public int getDrawPath() {
        int mode = this.mStartMotionPath.mDrawPath;
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        while (it.hasNext()) {
            mode = Math.max(mode, it.next().mDrawPath);
        }
        return Math.max(mode, this.mEndMotionPath.mDrawPath);
    }

    public void setDrawPath(int debugMode) {
        this.mStartMotionPath.mDrawPath = debugMode;
    }

    /* access modifiers changed from: package-private */
    public String name() {
        return this.mView.getContext().getResources().getResourceEntryName(this.mView.getId());
    }

    /* access modifiers changed from: package-private */
    public void positionKeyframe(View view, KeyPositionBase key, float x, float y, String[] attribute, float[] value) {
        RectF start = new RectF();
        start.left = this.mStartMotionPath.mX;
        start.top = this.mStartMotionPath.mY;
        start.right = start.left + this.mStartMotionPath.mWidth;
        start.bottom = start.top + this.mStartMotionPath.mHeight;
        RectF end = new RectF();
        end.left = this.mEndMotionPath.mX;
        end.top = this.mEndMotionPath.mY;
        end.right = end.left + this.mEndMotionPath.mWidth;
        end.bottom = end.top + this.mEndMotionPath.mHeight;
        key.positionAttributes(view, start, end, x, y, attribute, value);
    }

    public int getKeyFramePositions(int[] type, float[] pos) {
        int i = 0;
        Iterator<Key> it = this.mKeyList.iterator();
        int count = 0;
        while (it.hasNext() != 0) {
            Key key = it.next();
            int i2 = i + 1;
            type[i] = key.mFramePosition + (key.mType * 1000);
            float time = ((float) key.mFramePosition) / 100.0f;
            this.mSpline[0].getPos((double) time, this.mInterpolateData);
            this.mStartMotionPath.getCenter((double) time, this.mInterpolateVariables, this.mInterpolateData, pos, count);
            count += 2;
            i = i2;
        }
        return i;
    }

    public int getKeyFrameInfo(int type, int[] info) {
        int i = type;
        int count = 0;
        int cursor = 0;
        float[] pos = new float[2];
        Iterator<Key> it = this.mKeyList.iterator();
        while (it.hasNext()) {
            Key key = it.next();
            if (key.mType == i || i != -1) {
                int len = cursor;
                info[cursor] = 0;
                int cursor2 = cursor + 1;
                info[cursor2] = key.mType;
                int cursor3 = cursor2 + 1;
                info[cursor3] = key.mFramePosition;
                float time = ((float) key.mFramePosition) / 100.0f;
                this.mSpline[0].getPos((double) time, this.mInterpolateData);
                float f = time;
                this.mStartMotionPath.getCenter((double) time, this.mInterpolateVariables, this.mInterpolateData, pos, 0);
                int cursor4 = cursor3 + 1;
                info[cursor4] = Float.floatToIntBits(pos[0]);
                int cursor5 = cursor4 + 1;
                info[cursor5] = Float.floatToIntBits(pos[1]);
                if (key instanceof KeyPosition) {
                    KeyPosition kp = (KeyPosition) key;
                    int cursor6 = cursor5 + 1;
                    info[cursor6] = kp.mPositionType;
                    int cursor7 = cursor6 + 1;
                    info[cursor7] = Float.floatToIntBits(kp.mPercentX);
                    cursor5 = cursor7 + 1;
                    info[cursor5] = Float.floatToIntBits(kp.mPercentY);
                }
                cursor = cursor5 + 1;
                info[len] = cursor - len;
                count++;
            }
        }
        return count;
    }
}
