package androidx.constraintlayout.core.motion;

import androidx.constraintlayout.core.motion.key.MotionKey;
import androidx.constraintlayout.core.motion.key.MotionKeyPosition;
import androidx.constraintlayout.core.motion.key.MotionKeyTrigger;
import androidx.constraintlayout.core.motion.utils.CurveFit;
import androidx.constraintlayout.core.motion.utils.DifferentialInterpolator;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.core.motion.utils.FloatRect;
import androidx.constraintlayout.core.motion.utils.KeyCache;
import androidx.constraintlayout.core.motion.utils.KeyCycleOscillator;
import androidx.constraintlayout.core.motion.utils.Rect;
import androidx.constraintlayout.core.motion.utils.SplineSet;
import androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet;
import androidx.constraintlayout.core.motion.utils.TypedBundle;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.core.motion.utils.Utils;
import androidx.constraintlayout.core.motion.utils.VelocityMatrix;
import androidx.constraintlayout.core.motion.utils.ViewState;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class Motion implements TypedValues {
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
    private HashMap<String, SplineSet> mAttributesMap;
    String mConstraintTag;
    float mCurrentCenterX;
    float mCurrentCenterY;
    private int mCurveFitType = 0;
    private HashMap<String, KeyCycleOscillator> mCycleMap;
    private MotionPaths mEndMotionPath = new MotionPaths();
    private MotionConstrainedPoint mEndPoint = new MotionConstrainedPoint();
    public String mId;
    private double[] mInterpolateData;
    private int[] mInterpolateVariables;
    private double[] mInterpolateVelocity;
    private ArrayList<MotionKey> mKeyList = new ArrayList<>();
    private MotionKeyTrigger[] mKeyTriggers;
    private int mMaxDimension = 4;
    private ArrayList<MotionPaths> mMotionPaths = new ArrayList<>();
    float mMotionStagger = Float.NaN;
    private boolean mNoMovement = false;
    private int mPathMotionArc = -1;
    private DifferentialInterpolator mQuantizeMotionInterpolator = null;
    private float mQuantizeMotionPhase = Float.NaN;
    private int mQuantizeMotionSteps = -1;
    Motion mRelativeMotion;
    private CurveFit[] mSpline;
    float mStaggerOffset = 0.0f;
    float mStaggerScale = 1.0f;
    private MotionPaths mStartMotionPath = new MotionPaths();
    private MotionConstrainedPoint mStartPoint = new MotionConstrainedPoint();
    Rect mTempRect = new Rect();
    private HashMap<String, TimeCycleSplineSet> mTimeCycleAttributesMap;
    private int mTransformPivotTarget = -1;
    private MotionWidget mTransformPivotView = null;
    private float[] mValuesBuff = new float[this.mMaxDimension];
    private float[] mVelocity = new float[1];
    MotionWidget mView;

    public int getTransformPivotTarget() {
        return this.mTransformPivotTarget;
    }

    public void setTransformPivotTarget(int transformPivotTarget) {
        this.mTransformPivotTarget = transformPivotTarget;
        this.mTransformPivotView = null;
    }

    public MotionPaths getKeyFrame(int i) {
        return this.mMotionPaths.get(i);
    }

    public Motion(MotionWidget view) {
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

    public String getAnimateRelativeTo() {
        return this.mStartMotionPath.mAnimateRelativeTo;
    }

    public void setupRelative(Motion motionController) {
        this.mRelativeMotion = motionController;
    }

    private void setupRelative() {
        if (this.mRelativeMotion != null) {
            this.mStartMotionPath.setupRelative(this.mRelativeMotion, this.mRelativeMotion.mStartMotionPath);
            this.mEndMotionPath.setupRelative(this.mRelativeMotion, this.mRelativeMotion.mEndMotionPath);
        }
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
        this.mSpline[0].getPos(p, position);
        this.mSpline[0].getSlope(p, velocity);
        Arrays.fill(vel, 0.0f);
        this.mStartMotionPath.getCenter(p, this.mInterpolateVariables, position, pos, velocity, vel);
    }

    public void buildPath(float[] points, int pointCount) {
        float mils;
        int i = pointCount;
        float f = 1.0f;
        float mils2 = 1.0f / ((float) (i - 1));
        KeyCycleOscillator osc_y = null;
        SplineSet trans_x = this.mAttributesMap == null ? null : this.mAttributesMap.get("translationX");
        SplineSet trans_y = this.mAttributesMap == null ? null : this.mAttributesMap.get("translationY");
        KeyCycleOscillator osc_x = this.mCycleMap == null ? null : this.mCycleMap.get("translationX");
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
        Motion motion = this;
        int i = pointCount;
        float f = 1.0f;
        float mils2 = 1.0f / ((float) (i - 1));
        SplineSet trans_x = motion.mAttributesMap == null ? null : motion.mAttributesMap.get("translationX");
        if (motion.mAttributesMap != null) {
            SplineSet splineSet = motion.mAttributesMap.get("translationY");
        }
        if (motion.mCycleMap != null) {
            KeyCycleOscillator keyCycleOscillator = motion.mCycleMap.get("translationX");
        }
        if (motion.mCycleMap != null) {
            KeyCycleOscillator keyCycleOscillator2 = motion.mCycleMap.get("translationY");
        }
        int i2 = 0;
        while (i2 < i) {
            float position = ((float) i2) * mils2;
            if (motion.mStaggerScale != f) {
                if (position < motion.mStaggerOffset) {
                    position = 0.0f;
                }
                if (position > motion.mStaggerOffset && ((double) position) < 1.0d) {
                    position = Math.min((position - motion.mStaggerOffset) * motion.mStaggerScale, f);
                }
            }
            double p = (double) position;
            Easing easing = motion.mStartMotionPath.mKeyFrameEasing;
            float start = 0.0f;
            float end = Float.NaN;
            Iterator<MotionPaths> it = motion.mMotionPaths.iterator();
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
            motion.mSpline[0].getPos(p, motion.mInterpolateData);
            if (motion.mArcSpline != null && motion.mInterpolateData.length > 0) {
                motion.mArcSpline.getPos(p, motion.mInterpolateData);
            }
            motion.mStartMotionPath.getBounds(motion.mInterpolateVariables, motion.mInterpolateData, bounds, i2 * 2);
            i2++;
            motion = this;
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
    public MotionKeyPosition getPositionKeyframe(int layoutWidth, int layoutHeight, float x, float y) {
        float y2;
        float x2;
        int layoutHeight2;
        int layoutWidth2;
        FloatRect start = new FloatRect();
        start.left = this.mStartMotionPath.mX;
        start.top = this.mStartMotionPath.mY;
        start.right = start.left + this.mStartMotionPath.mWidth;
        start.bottom = start.top + this.mStartMotionPath.mHeight;
        FloatRect end = new FloatRect();
        end.left = this.mEndMotionPath.mX;
        end.top = this.mEndMotionPath.mY;
        end.right = end.left + this.mEndMotionPath.mWidth;
        end.bottom = end.top + this.mEndMotionPath.mHeight;
        Iterator<MotionKey> it = this.mKeyList.iterator();
        while (it.hasNext()) {
            MotionKey key = it.next();
            if (key instanceof MotionKeyPosition) {
                layoutWidth2 = layoutWidth;
                layoutHeight2 = layoutHeight;
                x2 = x;
                y2 = y;
                if (((MotionKeyPosition) key).intersects(layoutWidth2, layoutHeight2, start, end, x2, y2) != 0) {
                    return (MotionKeyPosition) key;
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

    public int buildKeyFrames(float[] keyFrames, int[] mode, int[] pos) {
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
        if (pos != null) {
            Iterator<MotionPaths> it2 = this.mMotionPaths.iterator();
            while (it2.hasNext()) {
                pos[count] = (int) (it2.next().mPosition * 100.0f);
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
        MotionPaths redundant = null;
        Iterator<MotionPaths> it = this.mMotionPaths.iterator();
        while (it.hasNext()) {
            MotionPaths p = it.next();
            if (point.mPosition == p.mPosition) {
                redundant = p;
            }
        }
        if (redundant != null) {
            this.mMotionPaths.remove(redundant);
        }
        int pos = Collections.binarySearch(this.mMotionPaths, point);
        if (pos == 0) {
            Utils.loge(TAG, " KeyPath position \"" + point.mPosition + "\" outside of range");
        }
        this.mMotionPaths.add((-pos) - 1, point);
    }

    /* access modifiers changed from: package-private */
    public void addKeys(ArrayList<MotionKey> list) {
        this.mKeyList.addAll(list);
    }

    public void addKey(MotionKey key) {
        this.mKeyList.add(key);
    }

    public void setPathMotionArc(int arc) {
        this.mPathMotionArc = arc;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v40, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v30, resolved type: double[][]} */
    /* JADX WARNING: type inference failed for: r18v0 */
    /* JADX WARNING: type inference failed for: r18v1 */
    /* JADX WARNING: type inference failed for: r18v2 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setup(int r32, int r33, float r34, long r35) {
        /*
            r31 = this;
            r0 = r31
            r1 = r35
            java.util.HashSet r3 = new java.util.HashSet
            r3.<init>()
            java.util.HashSet r4 = new java.util.HashSet
            r4.<init>()
            java.util.HashSet r5 = new java.util.HashSet
            r5.<init>()
            java.util.HashSet r6 = new java.util.HashSet
            r6.<init>()
            java.util.HashMap r7 = new java.util.HashMap
            r7.<init>()
            r8 = 0
            r0.setupRelative()
            int r9 = r0.mPathMotionArc
            r10 = -1
            if (r9 == r10) goto L_0x0032
            androidx.constraintlayout.core.motion.MotionPaths r9 = r0.mStartMotionPath
            int r9 = r9.mPathMotionArc
            if (r9 != r10) goto L_0x0032
            androidx.constraintlayout.core.motion.MotionPaths r9 = r0.mStartMotionPath
            int r11 = r0.mPathMotionArc
            r9.mPathMotionArc = r11
        L_0x0032:
            androidx.constraintlayout.core.motion.MotionConstrainedPoint r9 = r0.mStartPoint
            androidx.constraintlayout.core.motion.MotionConstrainedPoint r11 = r0.mEndPoint
            r9.different(r11, r5)
            java.util.ArrayList<androidx.constraintlayout.core.motion.key.MotionKey> r9 = r0.mKeyList
            if (r9 == 0) goto L_0x00a0
            java.util.ArrayList<androidx.constraintlayout.core.motion.key.MotionKey> r9 = r0.mKeyList
            java.util.Iterator r9 = r9.iterator()
        L_0x0043:
            boolean r11 = r9.hasNext()
            if (r11 == 0) goto L_0x00a0
            java.lang.Object r11 = r9.next()
            androidx.constraintlayout.core.motion.key.MotionKey r11 = (androidx.constraintlayout.core.motion.key.MotionKey) r11
            boolean r12 = r11 instanceof androidx.constraintlayout.core.motion.key.MotionKeyPosition
            if (r12 == 0) goto L_0x0076
            r16 = r11
            androidx.constraintlayout.core.motion.key.MotionKeyPosition r16 = (androidx.constraintlayout.core.motion.key.MotionKeyPosition) r16
            androidx.constraintlayout.core.motion.MotionPaths r13 = new androidx.constraintlayout.core.motion.MotionPaths
            androidx.constraintlayout.core.motion.MotionPaths r12 = r0.mStartMotionPath
            androidx.constraintlayout.core.motion.MotionPaths r14 = r0.mEndMotionPath
            r15 = r33
            r17 = r12
            r18 = r14
            r14 = r32
            r13.<init>(r14, r15, r16, r17, r18)
            r12 = r16
            r0.insertKey(r13)
            int r13 = r12.mCurveFit
            if (r13 == r10) goto L_0x0075
            int r13 = r12.mCurveFit
            r0.mCurveFitType = r13
        L_0x0075:
            goto L_0x009f
        L_0x0076:
            boolean r12 = r11 instanceof androidx.constraintlayout.core.motion.key.MotionKeyCycle
            if (r12 == 0) goto L_0x007e
            r11.getAttributeNames(r6)
            goto L_0x009f
        L_0x007e:
            boolean r12 = r11 instanceof androidx.constraintlayout.core.motion.key.MotionKeyTimeCycle
            if (r12 == 0) goto L_0x0086
            r11.getAttributeNames(r4)
            goto L_0x009f
        L_0x0086:
            boolean r12 = r11 instanceof androidx.constraintlayout.core.motion.key.MotionKeyTrigger
            if (r12 == 0) goto L_0x0099
            if (r8 != 0) goto L_0x0092
            java.util.ArrayList r12 = new java.util.ArrayList
            r12.<init>()
            r8 = r12
        L_0x0092:
            r12 = r11
            androidx.constraintlayout.core.motion.key.MotionKeyTrigger r12 = (androidx.constraintlayout.core.motion.key.MotionKeyTrigger) r12
            r8.add(r12)
            goto L_0x009f
        L_0x0099:
            r11.setInterpolation(r7)
            r11.getAttributeNames(r5)
        L_0x009f:
            goto L_0x0043
        L_0x00a0:
            r9 = 0
            if (r8 == 0) goto L_0x00ad
            androidx.constraintlayout.core.motion.key.MotionKeyTrigger[] r11 = new androidx.constraintlayout.core.motion.key.MotionKeyTrigger[r9]
            java.lang.Object[] r11 = r8.toArray(r11)
            androidx.constraintlayout.core.motion.key.MotionKeyTrigger[] r11 = (androidx.constraintlayout.core.motion.key.MotionKeyTrigger[]) r11
            r0.mKeyTriggers = r11
        L_0x00ad:
            boolean r11 = r5.isEmpty()
            java.lang.String r12 = ","
            java.lang.String r13 = "CUSTOM,"
            if (r11 != 0) goto L_0x01ce
            java.util.HashMap r11 = new java.util.HashMap
            r11.<init>()
            r0.mAttributesMap = r11
            java.util.Iterator r11 = r5.iterator()
        L_0x00c2:
            boolean r15 = r11.hasNext()
            if (r15 == 0) goto L_0x015e
            java.lang.Object r15 = r11.next()
            java.lang.String r15 = (java.lang.String) r15
            boolean r16 = r15.startsWith(r13)
            if (r16 == 0) goto L_0x0134
            androidx.constraintlayout.core.motion.utils.KeyFrameArray$CustomVar r16 = new androidx.constraintlayout.core.motion.utils.KeyFrameArray$CustomVar
            r16.<init>()
            r17 = r16
            java.lang.String[] r16 = r15.split(r12)
            r18 = 1
            r14 = r16[r18]
            java.util.ArrayList<androidx.constraintlayout.core.motion.key.MotionKey> r10 = r0.mKeyList
            java.util.Iterator r10 = r10.iterator()
        L_0x00e9:
            boolean r19 = r10.hasNext()
            if (r19 == 0) goto L_0x0127
            java.lang.Object r19 = r10.next()
            r9 = r19
            androidx.constraintlayout.core.motion.key.MotionKey r9 = (androidx.constraintlayout.core.motion.key.MotionKey) r9
            r19 = r3
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.CustomVariable> r3 = r9.mCustom
            if (r3 != 0) goto L_0x0101
            r3 = r19
            r9 = 0
            goto L_0x00e9
        L_0x0101:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.CustomVariable> r3 = r9.mCustom
            java.lang.Object r3 = r3.get(r14)
            androidx.constraintlayout.core.motion.CustomVariable r3 = (androidx.constraintlayout.core.motion.CustomVariable) r3
            if (r3 == 0) goto L_0x0117
            r21 = r4
            int r4 = r9.mFramePosition
            r22 = r6
            r6 = r17
            r6.append(r4, r3)
            goto L_0x011d
        L_0x0117:
            r21 = r4
            r22 = r6
            r6 = r17
        L_0x011d:
            r17 = r6
            r3 = r19
            r4 = r21
            r6 = r22
            r9 = 0
            goto L_0x00e9
        L_0x0127:
            r19 = r3
            r21 = r4
            r22 = r6
            r6 = r17
            androidx.constraintlayout.core.motion.utils.SplineSet r3 = androidx.constraintlayout.core.motion.utils.SplineSet.makeCustomSplineSet(r15, r6)
            goto L_0x0140
        L_0x0134:
            r19 = r3
            r21 = r4
            r22 = r6
            r18 = 1
            androidx.constraintlayout.core.motion.utils.SplineSet r3 = androidx.constraintlayout.core.motion.utils.SplineSet.makeSpline(r15, r1)
        L_0x0140:
            if (r3 != 0) goto L_0x014c
            r3 = r19
            r4 = r21
            r6 = r22
            r9 = 0
            r10 = -1
            goto L_0x00c2
        L_0x014c:
            r3.setType(r15)
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.SplineSet> r4 = r0.mAttributesMap
            r4.put(r15, r3)
            r3 = r19
            r4 = r21
            r6 = r22
            r9 = 0
            r10 = -1
            goto L_0x00c2
        L_0x015e:
            r19 = r3
            r21 = r4
            r22 = r6
            r18 = 1
            java.util.ArrayList<androidx.constraintlayout.core.motion.key.MotionKey> r3 = r0.mKeyList
            if (r3 == 0) goto L_0x0186
            java.util.ArrayList<androidx.constraintlayout.core.motion.key.MotionKey> r3 = r0.mKeyList
            java.util.Iterator r3 = r3.iterator()
        L_0x0170:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0186
            java.lang.Object r4 = r3.next()
            androidx.constraintlayout.core.motion.key.MotionKey r4 = (androidx.constraintlayout.core.motion.key.MotionKey) r4
            boolean r6 = r4 instanceof androidx.constraintlayout.core.motion.key.MotionKeyAttributes
            if (r6 == 0) goto L_0x0185
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.SplineSet> r6 = r0.mAttributesMap
            r4.addValues(r6)
        L_0x0185:
            goto L_0x0170
        L_0x0186:
            androidx.constraintlayout.core.motion.MotionConstrainedPoint r3 = r0.mStartPoint
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.SplineSet> r4 = r0.mAttributesMap
            r6 = 0
            r3.addValues(r4, r6)
            androidx.constraintlayout.core.motion.MotionConstrainedPoint r3 = r0.mEndPoint
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.SplineSet> r4 = r0.mAttributesMap
            r6 = 100
            r3.addValues(r4, r6)
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.SplineSet> r3 = r0.mAttributesMap
            java.util.Set r3 = r3.keySet()
            java.util.Iterator r3 = r3.iterator()
        L_0x01a1:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x01d6
            java.lang.Object r4 = r3.next()
            java.lang.String r4 = (java.lang.String) r4
            r6 = 0
            boolean r9 = r7.containsKey(r4)
            if (r9 == 0) goto L_0x01c0
            java.lang.Object r9 = r7.get(r4)
            java.lang.Integer r9 = (java.lang.Integer) r9
            if (r9 == 0) goto L_0x01c0
            int r6 = r9.intValue()
        L_0x01c0:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.SplineSet> r9 = r0.mAttributesMap
            java.lang.Object r9 = r9.get(r4)
            androidx.constraintlayout.core.motion.utils.SplineSet r9 = (androidx.constraintlayout.core.motion.utils.SplineSet) r9
            if (r9 == 0) goto L_0x01cd
            r9.setup(r6)
        L_0x01cd:
            goto L_0x01a1
        L_0x01ce:
            r19 = r3
            r21 = r4
            r22 = r6
            r18 = 1
        L_0x01d6:
            boolean r3 = r21.isEmpty()
            if (r3 != 0) goto L_0x02ae
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet> r3 = r0.mTimeCycleAttributesMap
            if (r3 != 0) goto L_0x01e7
            java.util.HashMap r3 = new java.util.HashMap
            r3.<init>()
            r0.mTimeCycleAttributesMap = r3
        L_0x01e7:
            java.util.Iterator r3 = r21.iterator()
        L_0x01eb:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0258
            java.lang.Object r4 = r3.next()
            java.lang.String r4 = (java.lang.String) r4
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet> r6 = r0.mTimeCycleAttributesMap
            boolean r6 = r6.containsKey(r4)
            if (r6 == 0) goto L_0x0200
            goto L_0x01eb
        L_0x0200:
            r6 = 0
            boolean r9 = r4.startsWith(r13)
            if (r9 == 0) goto L_0x0247
            androidx.constraintlayout.core.motion.utils.KeyFrameArray$CustomVar r9 = new androidx.constraintlayout.core.motion.utils.KeyFrameArray$CustomVar
            r9.<init>()
            java.lang.String[] r10 = r4.split(r12)
            r10 = r10[r18]
            java.util.ArrayList<androidx.constraintlayout.core.motion.key.MotionKey> r11 = r0.mKeyList
            java.util.Iterator r11 = r11.iterator()
        L_0x0218:
            boolean r14 = r11.hasNext()
            if (r14 == 0) goto L_0x0240
            java.lang.Object r14 = r11.next()
            androidx.constraintlayout.core.motion.key.MotionKey r14 = (androidx.constraintlayout.core.motion.key.MotionKey) r14
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.CustomVariable> r15 = r14.mCustom
            if (r15 != 0) goto L_0x0229
            goto L_0x0218
        L_0x0229:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.CustomVariable> r15 = r14.mCustom
            java.lang.Object r15 = r15.get(r10)
            androidx.constraintlayout.core.motion.CustomVariable r15 = (androidx.constraintlayout.core.motion.CustomVariable) r15
            if (r15 == 0) goto L_0x023b
            r17 = r3
            int r3 = r14.mFramePosition
            r9.append(r3, r15)
            goto L_0x023d
        L_0x023b:
            r17 = r3
        L_0x023d:
            r3 = r17
            goto L_0x0218
        L_0x0240:
            r17 = r3
            androidx.constraintlayout.core.motion.utils.SplineSet r3 = androidx.constraintlayout.core.motion.utils.SplineSet.makeCustomSplineSet(r4, r9)
            goto L_0x024d
        L_0x0247:
            r17 = r3
            androidx.constraintlayout.core.motion.utils.SplineSet r3 = androidx.constraintlayout.core.motion.utils.SplineSet.makeSpline(r4, r1)
        L_0x024d:
            if (r3 != 0) goto L_0x0252
            r3 = r17
            goto L_0x01eb
        L_0x0252:
            r3.setType(r4)
            r3 = r17
            goto L_0x01eb
        L_0x0258:
            java.util.ArrayList<androidx.constraintlayout.core.motion.key.MotionKey> r3 = r0.mKeyList
            if (r3 == 0) goto L_0x027b
            java.util.ArrayList<androidx.constraintlayout.core.motion.key.MotionKey> r3 = r0.mKeyList
            java.util.Iterator r3 = r3.iterator()
        L_0x0262:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x027b
            java.lang.Object r4 = r3.next()
            androidx.constraintlayout.core.motion.key.MotionKey r4 = (androidx.constraintlayout.core.motion.key.MotionKey) r4
            boolean r6 = r4 instanceof androidx.constraintlayout.core.motion.key.MotionKeyTimeCycle
            if (r6 == 0) goto L_0x027a
            r6 = r4
            androidx.constraintlayout.core.motion.key.MotionKeyTimeCycle r6 = (androidx.constraintlayout.core.motion.key.MotionKeyTimeCycle) r6
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet> r9 = r0.mTimeCycleAttributesMap
            r6.addTimeValues(r9)
        L_0x027a:
            goto L_0x0262
        L_0x027b:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet> r3 = r0.mTimeCycleAttributesMap
            java.util.Set r3 = r3.keySet()
            java.util.Iterator r3 = r3.iterator()
        L_0x0285:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x02ae
            java.lang.Object r4 = r3.next()
            java.lang.String r4 = (java.lang.String) r4
            r6 = 0
            boolean r9 = r7.containsKey(r4)
            if (r9 == 0) goto L_0x02a2
            java.lang.Object r9 = r7.get(r4)
            java.lang.Integer r9 = (java.lang.Integer) r9
            int r6 = r9.intValue()
        L_0x02a2:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet> r9 = r0.mTimeCycleAttributesMap
            java.lang.Object r9 = r9.get(r4)
            androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet r9 = (androidx.constraintlayout.core.motion.utils.TimeCycleSplineSet) r9
            r9.setup(r6)
            goto L_0x0285
        L_0x02ae:
            java.util.ArrayList<androidx.constraintlayout.core.motion.MotionPaths> r3 = r0.mMotionPaths
            int r3 = r3.size()
            r4 = 2
            int r3 = r3 + r4
            androidx.constraintlayout.core.motion.MotionPaths[] r3 = new androidx.constraintlayout.core.motion.MotionPaths[r3]
            r6 = 1
            androidx.constraintlayout.core.motion.MotionPaths r9 = r0.mStartMotionPath
            r20 = 0
            r3[r20] = r9
            int r9 = r3.length
            int r9 = r9 + -1
            androidx.constraintlayout.core.motion.MotionPaths r10 = r0.mEndMotionPath
            r3[r9] = r10
            java.util.ArrayList<androidx.constraintlayout.core.motion.MotionPaths> r9 = r0.mMotionPaths
            int r9 = r9.size()
            if (r9 <= 0) goto L_0x02d7
            int r9 = r0.mCurveFitType
            int r10 = androidx.constraintlayout.core.motion.key.MotionKey.UNSET
            if (r9 != r10) goto L_0x02d7
            r9 = 0
            r0.mCurveFitType = r9
        L_0x02d7:
            java.util.ArrayList<androidx.constraintlayout.core.motion.MotionPaths> r9 = r0.mMotionPaths
            java.util.Iterator r9 = r9.iterator()
        L_0x02dd:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x02ef
            java.lang.Object r10 = r9.next()
            androidx.constraintlayout.core.motion.MotionPaths r10 = (androidx.constraintlayout.core.motion.MotionPaths) r10
            int r11 = r6 + 1
            r3[r6] = r10
            r6 = r11
            goto L_0x02dd
        L_0x02ef:
            r9 = 18
            java.util.HashSet r10 = new java.util.HashSet
            r10.<init>()
            androidx.constraintlayout.core.motion.MotionPaths r11 = r0.mEndMotionPath
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.CustomVariable> r11 = r11.mCustomAttributes
            java.util.Set r11 = r11.keySet()
            java.util.Iterator r11 = r11.iterator()
        L_0x0302:
            boolean r12 = r11.hasNext()
            if (r12 == 0) goto L_0x0333
            java.lang.Object r12 = r11.next()
            java.lang.String r12 = (java.lang.String) r12
            androidx.constraintlayout.core.motion.MotionPaths r14 = r0.mStartMotionPath
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.CustomVariable> r14 = r14.mCustomAttributes
            boolean r14 = r14.containsKey(r12)
            if (r14 == 0) goto L_0x0332
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.StringBuilder r14 = r14.append(r13)
            java.lang.StringBuilder r14 = r14.append(r12)
            java.lang.String r14 = r14.toString()
            boolean r14 = r5.contains(r14)
            if (r14 != 0) goto L_0x0332
            r10.add(r12)
        L_0x0332:
            goto L_0x0302
        L_0x0333:
            r11 = 0
            java.lang.String[] r12 = new java.lang.String[r11]
            java.lang.Object[] r11 = r10.toArray(r12)
            java.lang.String[] r11 = (java.lang.String[]) r11
            r0.mAttributeNames = r11
            java.lang.String[] r11 = r0.mAttributeNames
            int r11 = r11.length
            int[] r11 = new int[r11]
            r0.mAttributeInterpolatorCount = r11
            r11 = 0
        L_0x0346:
            java.lang.String[] r12 = r0.mAttributeNames
            int r12 = r12.length
            if (r11 >= r12) goto L_0x0382
            java.lang.String[] r12 = r0.mAttributeNames
            r12 = r12[r11]
            int[] r13 = r0.mAttributeInterpolatorCount
            r20 = 0
            r13[r11] = r20
            r13 = 0
        L_0x0356:
            int r14 = r3.length
            if (r13 >= r14) goto L_0x037f
            r14 = r3[r13]
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.CustomVariable> r14 = r14.mCustomAttributes
            boolean r14 = r14.containsKey(r12)
            if (r14 == 0) goto L_0x037c
            r14 = r3[r13]
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.CustomVariable> r14 = r14.mCustomAttributes
            java.lang.Object r14 = r14.get(r12)
            androidx.constraintlayout.core.motion.CustomVariable r14 = (androidx.constraintlayout.core.motion.CustomVariable) r14
            if (r14 == 0) goto L_0x037c
            int[] r15 = r0.mAttributeInterpolatorCount
            r17 = r15[r11]
            int r23 = r14.numberOfInterpolatedValues()
            int r17 = r17 + r23
            r15[r11] = r17
            goto L_0x037f
        L_0x037c:
            int r13 = r13 + 1
            goto L_0x0356
        L_0x037f:
            int r11 = r11 + 1
            goto L_0x0346
        L_0x0382:
            r20 = 0
            r11 = r3[r20]
            int r11 = r11.mPathMotionArc
            r12 = -1
            if (r11 == r12) goto L_0x038e
            r11 = r18
            goto L_0x038f
        L_0x038e:
            r11 = 0
        L_0x038f:
            java.lang.String[] r12 = r0.mAttributeNames
            int r12 = r12.length
            int r12 = r12 + r9
            boolean[] r12 = new boolean[r12]
            r13 = 1
        L_0x0396:
            int r14 = r3.length
            if (r13 >= r14) goto L_0x03a8
            r14 = r3[r13]
            int r15 = r13 + -1
            r15 = r3[r15]
            java.lang.String[] r4 = r0.mAttributeNames
            r14.different(r15, r12, r4, r11)
            int r13 = r13 + 1
            r4 = 2
            goto L_0x0396
        L_0x03a8:
            r4 = 0
            r6 = 1
        L_0x03aa:
            int r13 = r12.length
            if (r6 >= r13) goto L_0x03b6
            boolean r13 = r12[r6]
            if (r13 == 0) goto L_0x03b3
            int r4 = r4 + 1
        L_0x03b3:
            int r6 = r6 + 1
            goto L_0x03aa
        L_0x03b6:
            int[] r6 = new int[r4]
            r0.mInterpolateVariables = r6
            r6 = 2
            int r13 = java.lang.Math.max(r6, r4)
            double[] r6 = new double[r13]
            r0.mInterpolateData = r6
            double[] r6 = new double[r13]
            r0.mInterpolateVelocity = r6
            r4 = 0
            r6 = 1
        L_0x03c9:
            int r14 = r12.length
            if (r6 >= r14) goto L_0x03da
            boolean r14 = r12[r6]
            if (r14 == 0) goto L_0x03d7
            int[] r14 = r0.mInterpolateVariables
            int r15 = r4 + 1
            r14[r4] = r6
            r4 = r15
        L_0x03d7:
            int r6 = r6 + 1
            goto L_0x03c9
        L_0x03da:
            int r6 = r3.length
            int[] r14 = r0.mInterpolateVariables
            int r14 = r14.length
            r15 = 2
            int[] r1 = new int[r15]
            r1[r18] = r14
            r20 = 0
            r1[r20] = r6
            java.lang.Class r2 = java.lang.Double.TYPE
            java.lang.Object r1 = java.lang.reflect.Array.newInstance(r2, r1)
            double[][] r1 = (double[][]) r1
            int r2 = r3.length
            double[] r2 = new double[r2]
            r6 = 0
        L_0x03f3:
            int r14 = r3.length
            if (r6 >= r14) goto L_0x040d
            r14 = r3[r6]
            r15 = r1[r6]
            r23 = r4
            int[] r4 = r0.mInterpolateVariables
            r14.fillStandard(r15, r4)
            r4 = r3[r6]
            float r4 = r4.mTime
            double r14 = (double) r4
            r2[r6] = r14
            int r6 = r6 + 1
            r4 = r23
            goto L_0x03f3
        L_0x040d:
            r23 = r4
            r4 = 0
        L_0x0410:
            int[] r6 = r0.mInterpolateVariables
            int r6 = r6.length
            if (r4 >= r6) goto L_0x046b
            int[] r6 = r0.mInterpolateVariables
            r6 = r6[r4]
            java.lang.String[] r14 = androidx.constraintlayout.core.motion.MotionPaths.sNames
            int r14 = r14.length
            if (r6 >= r14) goto L_0x0462
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String[] r15 = androidx.constraintlayout.core.motion.MotionPaths.sNames
            r24 = r4
            int[] r4 = r0.mInterpolateVariables
            r4 = r4[r24]
            r4 = r15[r4]
            java.lang.StringBuilder r4 = r14.append(r4)
            java.lang.String r14 = " ["
            java.lang.StringBuilder r4 = r4.append(r14)
            java.lang.String r4 = r4.toString()
            r14 = 0
        L_0x043c:
            int r15 = r3.length
            if (r14 >= r15) goto L_0x045d
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            java.lang.StringBuilder r15 = r15.append(r4)
            r25 = r1[r14]
            r27 = r4
            r26 = r5
            r4 = r25[r24]
            java.lang.StringBuilder r4 = r15.append(r4)
            java.lang.String r4 = r4.toString()
            int r14 = r14 + 1
            r5 = r26
            goto L_0x043c
        L_0x045d:
            r27 = r4
            r26 = r5
            goto L_0x0466
        L_0x0462:
            r24 = r4
            r26 = r5
        L_0x0466:
            int r4 = r24 + 1
            r5 = r26
            goto L_0x0410
        L_0x046b:
            r24 = r4
            r26 = r5
            java.lang.String[] r4 = r0.mAttributeNames
            int r4 = r4.length
            int r4 = r4 + 1
            androidx.constraintlayout.core.motion.utils.CurveFit[] r4 = new androidx.constraintlayout.core.motion.utils.CurveFit[r4]
            r0.mSpline = r4
            r4 = 0
        L_0x0479:
            java.lang.String[] r5 = r0.mAttributeNames
            int r5 = r5.length
            if (r4 >= r5) goto L_0x04ff
            r5 = 0
            r6 = 0
            r14 = 0
            java.lang.String[] r15 = r0.mAttributeNames
            r15 = r15[r4]
            r24 = 0
            r30 = r24
            r24 = r4
            r4 = r30
        L_0x048d:
            r25 = r7
            int r7 = r3.length
            if (r4 >= r7) goto L_0x04de
            r7 = r3[r4]
            boolean r7 = r7.hasCustomData(r15)
            if (r7 == 0) goto L_0x04d7
            if (r6 != 0) goto L_0x04bf
            int r7 = r3.length
            double[] r14 = new double[r7]
            int r7 = r3.length
            r27 = r4
            r4 = r3[r27]
            int r4 = r4.getCustomDataCount(r15)
            r28 = r4
            r29 = r7
            r4 = 2
            int[] r7 = new int[r4]
            r7[r18] = r28
            r20 = 0
            r7[r20] = r29
            java.lang.Class r4 = java.lang.Double.TYPE
            java.lang.Object r4 = java.lang.reflect.Array.newInstance(r4, r7)
            r6 = r4
            double[][] r6 = (double[][]) r6
            goto L_0x04c1
        L_0x04bf:
            r27 = r4
        L_0x04c1:
            r4 = r3[r27]
            float r4 = r4.mTime
            r28 = r6
            double r6 = (double) r4
            r14[r5] = r6
            r4 = r3[r27]
            r6 = r28[r5]
            r7 = 0
            r4.getCustomData(r15, r6, r7)
            int r5 = r5 + 1
            r6 = r28
            goto L_0x04d9
        L_0x04d7:
            r27 = r4
        L_0x04d9:
            int r4 = r27 + 1
            r7 = r25
            goto L_0x048d
        L_0x04de:
            r27 = r4
            double[] r4 = java.util.Arrays.copyOf(r14, r5)
            java.lang.Object[] r7 = java.util.Arrays.copyOf(r6, r5)
            r6 = r7
            double[][] r6 = (double[][]) r6
            androidx.constraintlayout.core.motion.utils.CurveFit[] r7 = r0.mSpline
            int r14 = r24 + 1
            r27 = r5
            int r5 = r0.mCurveFitType
            androidx.constraintlayout.core.motion.utils.CurveFit r5 = androidx.constraintlayout.core.motion.utils.CurveFit.get(r5, r4, r6)
            r7[r14] = r5
            int r4 = r24 + 1
            r7 = r25
            goto L_0x0479
        L_0x04ff:
            r24 = r4
            r25 = r7
            androidx.constraintlayout.core.motion.utils.CurveFit[] r4 = r0.mSpline
            int r5 = r0.mCurveFitType
            androidx.constraintlayout.core.motion.utils.CurveFit r5 = androidx.constraintlayout.core.motion.utils.CurveFit.get(r5, r2, r1)
            r20 = 0
            r4[r20] = r5
            r4 = r3[r20]
            int r4 = r4.mPathMotionArc
            r5 = -1
            if (r4 == r5) goto L_0x0568
            int r4 = r3.length
            int[] r5 = new int[r4]
            double[] r6 = new double[r4]
            r15 = 2
            int[] r7 = new int[r15]
            r7[r18] = r15
            r20 = 0
            r7[r20] = r4
            java.lang.Class r14 = java.lang.Double.TYPE
            java.lang.Object r7 = java.lang.reflect.Array.newInstance(r14, r7)
            double[][] r7 = (double[][]) r7
            r14 = 0
        L_0x052d:
            if (r14 >= r4) goto L_0x055d
            r15 = r3[r14]
            int r15 = r15.mPathMotionArc
            r5[r14] = r15
            r15 = r3[r14]
            float r15 = r15.mTime
            r16 = r1
            r17 = r2
            double r1 = (double) r15
            r6[r14] = r1
            r1 = r7[r14]
            r2 = r3[r14]
            float r2 = r2.mX
            r15 = r1
            double r1 = (double) r2
            r20 = 0
            r15[r20] = r1
            r1 = r7[r14]
            r2 = r3[r14]
            float r2 = r2.mY
            r15 = r1
            double r1 = (double) r2
            r15[r18] = r1
            int r14 = r14 + 1
            r1 = r16
            r2 = r17
            goto L_0x052d
        L_0x055d:
            r16 = r1
            r17 = r2
            androidx.constraintlayout.core.motion.utils.CurveFit r1 = androidx.constraintlayout.core.motion.utils.CurveFit.getArc(r5, r6, r7)
            r0.mArcSpline = r1
            goto L_0x056c
        L_0x0568:
            r16 = r1
            r17 = r2
        L_0x056c:
            r1 = 2143289344(0x7fc00000, float:NaN)
            java.util.HashMap r2 = new java.util.HashMap
            r2.<init>()
            r0.mCycleMap = r2
            java.util.ArrayList<androidx.constraintlayout.core.motion.key.MotionKey> r2 = r0.mKeyList
            if (r2 == 0) goto L_0x05e2
            java.util.Iterator r2 = r22.iterator()
        L_0x057d:
            boolean r4 = r2.hasNext()
            if (r4 == 0) goto L_0x05a9
            java.lang.Object r4 = r2.next()
            java.lang.String r4 = (java.lang.String) r4
            androidx.constraintlayout.core.motion.utils.KeyCycleOscillator r5 = androidx.constraintlayout.core.motion.utils.KeyCycleOscillator.makeWidgetCycle(r4)
            if (r5 != 0) goto L_0x0590
            goto L_0x057d
        L_0x0590:
            boolean r6 = r5.variesByPath()
            if (r6 == 0) goto L_0x05a0
            boolean r6 = java.lang.Float.isNaN(r1)
            if (r6 == 0) goto L_0x05a0
            float r1 = r0.getPreCycleDistance()
        L_0x05a0:
            r5.setType(r4)
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.KeyCycleOscillator> r6 = r0.mCycleMap
            r6.put(r4, r5)
            goto L_0x057d
        L_0x05a9:
            java.util.ArrayList<androidx.constraintlayout.core.motion.key.MotionKey> r2 = r0.mKeyList
            java.util.Iterator r2 = r2.iterator()
        L_0x05af:
            boolean r4 = r2.hasNext()
            if (r4 == 0) goto L_0x05c8
            java.lang.Object r4 = r2.next()
            androidx.constraintlayout.core.motion.key.MotionKey r4 = (androidx.constraintlayout.core.motion.key.MotionKey) r4
            boolean r5 = r4 instanceof androidx.constraintlayout.core.motion.key.MotionKeyCycle
            if (r5 == 0) goto L_0x05c7
            r5 = r4
            androidx.constraintlayout.core.motion.key.MotionKeyCycle r5 = (androidx.constraintlayout.core.motion.key.MotionKeyCycle) r5
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.KeyCycleOscillator> r6 = r0.mCycleMap
            r5.addCycleValues(r6)
        L_0x05c7:
            goto L_0x05af
        L_0x05c8:
            java.util.HashMap<java.lang.String, androidx.constraintlayout.core.motion.utils.KeyCycleOscillator> r2 = r0.mCycleMap
            java.util.Collection r2 = r2.values()
            java.util.Iterator r2 = r2.iterator()
        L_0x05d2:
            boolean r4 = r2.hasNext()
            if (r4 == 0) goto L_0x05e2
            java.lang.Object r4 = r2.next()
            androidx.constraintlayout.core.motion.utils.KeyCycleOscillator r4 = (androidx.constraintlayout.core.motion.utils.KeyCycleOscillator) r4
            r4.setup(r1)
            goto L_0x05d2
        L_0x05e2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.motion.Motion.setup(int, int, float, long):void");
    }

    public String toString() {
        return " start: x: " + this.mStartMotionPath.mX + " y: " + this.mStartMotionPath.mY + " end: x: " + this.mEndMotionPath.mX + " y: " + this.mEndMotionPath.mY;
    }

    private void readView(MotionPaths motionPaths) {
        motionPaths.setBounds((float) this.mView.getX(), (float) this.mView.getY(), (float) this.mView.getWidth(), (float) this.mView.getHeight());
    }

    public void setView(MotionWidget view) {
        this.mView = view;
    }

    public MotionWidget getView() {
        return this.mView;
    }

    public void setStart(MotionWidget mw) {
        this.mStartMotionPath.mTime = 0.0f;
        this.mStartMotionPath.mPosition = 0.0f;
        this.mStartMotionPath.setBounds((float) mw.getX(), (float) mw.getY(), (float) mw.getWidth(), (float) mw.getHeight());
        this.mStartMotionPath.applyParameters(mw);
        this.mStartPoint.setState(mw);
        TypedBundle p = mw.getWidgetFrame().getMotionProperties();
        if (p != null) {
            p.applyDelta((TypedValues) this);
        }
    }

    public void setEnd(MotionWidget mw) {
        this.mEndMotionPath.mTime = 1.0f;
        this.mEndMotionPath.mPosition = 1.0f;
        readView(this.mEndMotionPath);
        this.mEndMotionPath.setBounds((float) mw.getLeft(), (float) mw.getTop(), (float) mw.getWidth(), (float) mw.getHeight());
        this.mEndMotionPath.applyParameters(mw);
        this.mEndPoint.setState(mw);
    }

    public void setStartState(ViewState rect, MotionWidget v, int rotation, int preWidth, int preHeight) {
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

    private static DifferentialInterpolator getInterpolator(int type, String interpolatorString, int id) {
        switch (type) {
            case -1:
                final Easing easing = Easing.getInterpolator(interpolatorString);
                return new DifferentialInterpolator() {
                    float mX;

                    public float getInterpolation(float x) {
                        this.mX = x;
                        return (float) Easing.this.get((double) x);
                    }

                    public float getVelocity() {
                        return (float) Easing.this.getDiff((double) this.mX);
                    }
                };
            default:
                return null;
        }
    }

    /* access modifiers changed from: package-private */
    public void setBothStates(MotionWidget v) {
        this.mStartMotionPath.mTime = 0.0f;
        this.mStartMotionPath.mPosition = 0.0f;
        this.mNoMovement = true;
        this.mStartMotionPath.setBounds((float) v.getX(), (float) v.getY(), (float) v.getWidth(), (float) v.getHeight());
        this.mEndMotionPath.setBounds((float) v.getX(), (float) v.getY(), (float) v.getWidth(), (float) v.getHeight());
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
    }

    public boolean interpolate(MotionWidget child, float globalPosition, long time, KeyCache keyCache) {
        float pin;
        float section;
        MotionWidget motionWidget = child;
        float position = getAdjustedPosition(globalPosition, (float[]) null);
        if (this.mQuantizeMotionSteps != -1) {
            float f = position;
            float steps = 1.0f / ((float) this.mQuantizeMotionSteps);
            float jump = ((float) Math.floor((double) (position / steps))) * steps;
            float section2 = (position % steps) / steps;
            if (!Float.isNaN(this.mQuantizeMotionPhase)) {
                section2 = (this.mQuantizeMotionPhase + section2) % 1.0f;
            }
            if (this.mQuantizeMotionInterpolator != null) {
                section = this.mQuantizeMotionInterpolator.getInterpolation(section2);
            } else {
                section = ((double) section2) > 0.5d ? 1.0f : 0.0f;
            }
            pin = (section * steps) + jump;
        } else {
            pin = position;
        }
        if (this.mAttributesMap != null) {
            for (SplineSet aSpline : this.mAttributesMap.values()) {
                aSpline.setProperty(motionWidget, pin);
            }
        }
        if (this.mSpline != null) {
            this.mSpline[0].getPos((double) pin, this.mInterpolateData);
            this.mSpline[0].getSlope((double) pin, this.mInterpolateVelocity);
            if (this.mArcSpline != null && this.mInterpolateData.length > 0) {
                this.mArcSpline.getPos((double) pin, this.mInterpolateData);
                this.mArcSpline.getSlope((double) pin, this.mInterpolateVelocity);
            }
            if (!this.mNoMovement) {
                float f2 = pin;
                MotionWidget motionWidget2 = motionWidget;
                float position2 = f2;
                this.mStartMotionPath.setView(position2, motionWidget2, this.mInterpolateVariables, this.mInterpolateData, this.mInterpolateVelocity, (double[]) null);
                MotionWidget motionWidget3 = motionWidget2;
                pin = position2;
                motionWidget = motionWidget3;
            }
            if (this.mTransformPivotTarget != -1) {
                if (this.mTransformPivotView == null) {
                    this.mTransformPivotView = motionWidget.getParent().findViewById(this.mTransformPivotTarget);
                }
                if (this.mTransformPivotView != null) {
                    float cy = ((float) (this.mTransformPivotView.getTop() + this.mTransformPivotView.getBottom())) / 2.0f;
                    float cx = ((float) (this.mTransformPivotView.getLeft() + this.mTransformPivotView.getRight())) / 2.0f;
                    if (motionWidget.getRight() - motionWidget.getLeft() > 0 && motionWidget.getBottom() - motionWidget.getTop() > 0) {
                        motionWidget.setPivotX(cx - ((float) motionWidget.getLeft()));
                        motionWidget.setPivotY(cy - ((float) motionWidget.getTop()));
                    }
                }
            }
            for (int i = 1; i < this.mSpline.length; i++) {
                this.mSpline[i].getPos((double) pin, this.mValuesBuff);
                this.mStartMotionPath.mCustomAttributes.get(this.mAttributeNames[i - 1]).setInterpolatedValue(motionWidget, this.mValuesBuff);
            }
            if (this.mStartPoint.mVisibilityMode == 0) {
                if (pin <= 0.0f) {
                    motionWidget.setVisibility(this.mStartPoint.mVisibility);
                } else if (pin >= 1.0f) {
                    motionWidget.setVisibility(this.mEndPoint.mVisibility);
                } else if (this.mEndPoint.mVisibility != this.mStartPoint.mVisibility) {
                    motionWidget.setVisibility(4);
                }
            }
            if (this.mKeyTriggers != null) {
                for (MotionKeyTrigger conditionallyFire : this.mKeyTriggers) {
                    conditionallyFire.conditionallyFire(pin, motionWidget);
                }
            }
        } else {
            float float_l = this.mStartMotionPath.mX + ((this.mEndMotionPath.mX - this.mStartMotionPath.mX) * pin);
            float float_t = this.mStartMotionPath.mY + ((this.mEndMotionPath.mY - this.mStartMotionPath.mY) * pin);
            int l = (int) (float_l + 0.5f);
            int t = (int) (float_t + 0.5f);
            int r = (int) (float_l + 0.5f + this.mStartMotionPath.mWidth + ((this.mEndMotionPath.mWidth - this.mStartMotionPath.mWidth) * pin));
            int b = (int) (0.5f + float_t + this.mStartMotionPath.mHeight + ((this.mEndMotionPath.mHeight - this.mStartMotionPath.mHeight) * pin));
            int i2 = r - l;
            int i3 = b - t;
            motionWidget.layout(l, t, r, b);
        }
        if (this.mCycleMap != null) {
            for (KeyCycleOscillator osc : this.mCycleMap.values()) {
                if (osc instanceof KeyCycleOscillator.PathRotateSet) {
                    ((KeyCycleOscillator.PathRotateSet) osc).setPathRotate(motionWidget, pin, this.mInterpolateVelocity[0], this.mInterpolateVelocity[1]);
                } else {
                    osc.setProperty(motionWidget, pin);
                }
            }
        }
        return false;
    }

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
        KeyCycleOscillator osc_sy = null;
        SplineSet trans_x = this.mAttributesMap == null ? null : this.mAttributesMap.get("translationX");
        SplineSet trans_y = this.mAttributesMap == null ? null : this.mAttributesMap.get("translationY");
        SplineSet rotation = this.mAttributesMap == null ? null : this.mAttributesMap.get("rotationZ");
        SplineSet scale_x = this.mAttributesMap == null ? null : this.mAttributesMap.get("scaleX");
        SplineSet scale_y = this.mAttributesMap == null ? null : this.mAttributesMap.get("scaleY");
        KeyCycleOscillator osc_x = this.mCycleMap == null ? null : this.mCycleMap.get("translationX");
        KeyCycleOscillator osc_y = this.mCycleMap == null ? null : this.mCycleMap.get("translationY");
        KeyCycleOscillator osc_r = this.mCycleMap == null ? null : this.mCycleMap.get("rotationZ");
        KeyCycleOscillator osc_sx = this.mCycleMap == null ? null : this.mCycleMap.get("scaleX");
        if (this.mCycleMap != null) {
            osc_sy = this.mCycleMap.get("scaleY");
        }
        VelocityMatrix vmat2 = new VelocityMatrix();
        vmat2.clear();
        vmat2.setRotationVelocity(rotation, position2);
        vmat2.setTranslationVelocity(trans_x, trans_y, position2);
        vmat2.setScaleVelocity(scale_x, scale_y, position2);
        vmat2.setRotationVelocity(osc_r, position2);
        vmat2.setTranslationVelocity(osc_x, osc_y, position2);
        vmat2.setScaleVelocity(osc_sx, osc_sy, position2);
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
            vmat2.setRotationVelocity(osc_r, position2);
            vmat2.setTranslationVelocity(osc_x, osc_y, position2);
            vmat2.setScaleVelocity(osc_sx, osc_sy, position2);
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
        return this.mView.getName();
    }

    /* access modifiers changed from: package-private */
    public void positionKeyframe(MotionWidget view, MotionKeyPosition key, float x, float y, String[] attribute, float[] value) {
        FloatRect start = new FloatRect();
        start.left = this.mStartMotionPath.mX;
        start.top = this.mStartMotionPath.mY;
        start.right = start.left + this.mStartMotionPath.mWidth;
        start.bottom = start.top + this.mStartMotionPath.mHeight;
        FloatRect end = new FloatRect();
        end.left = this.mEndMotionPath.mX;
        end.top = this.mEndMotionPath.mY;
        end.right = end.left + this.mEndMotionPath.mWidth;
        end.bottom = end.top + this.mEndMotionPath.mHeight;
        key.positionAttributes(view, start, end, x, y, attribute, value);
    }

    public int getKeyFramePositions(int[] type, float[] pos) {
        int i = 0;
        Iterator<MotionKey> it = this.mKeyList.iterator();
        int count = 0;
        while (it.hasNext() != 0) {
            MotionKey key = it.next();
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
        Iterator<MotionKey> it = this.mKeyList.iterator();
        while (it.hasNext()) {
            MotionKey key = it.next();
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
                if (key instanceof MotionKeyPosition) {
                    MotionKeyPosition kp = (MotionKeyPosition) key;
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

    public boolean setValue(int id, int value) {
        switch (id) {
            case 509:
                setPathMotionArc(value);
                return true;
            case TypedValues.MotionType.TYPE_QUANTIZE_MOTIONSTEPS:
                this.mQuantizeMotionSteps = value;
                return true;
            case TypedValues.TransitionType.TYPE_AUTO_TRANSITION:
                return true;
            default:
                return false;
        }
    }

    public boolean setValue(int id, float value) {
        if (602 == id) {
            this.mQuantizeMotionPhase = value;
            return true;
        } else if (600 != id) {
            return false;
        } else {
            this.mMotionStagger = value;
            return true;
        }
    }

    public boolean setValue(int id, String value) {
        if (705 == id || 611 == id) {
            this.mQuantizeMotionInterpolator = getInterpolator(-1, value, 0);
            return true;
        } else if (605 != id) {
            return false;
        } else {
            this.mStartMotionPath.mAnimateRelativeTo = value;
            return true;
        }
    }

    public boolean setValue(int id, boolean value) {
        return false;
    }

    public int getId(String name) {
        return 0;
    }

    public void setStaggerScale(float staggerScale) {
        this.mStaggerScale = staggerScale;
    }

    public void setStaggerOffset(float staggerOffset) {
        this.mStaggerOffset = staggerOffset;
    }

    public float getMotionStagger() {
        return this.mMotionStagger;
    }

    public void setIdString(String stringId) {
        this.mId = stringId;
        this.mStartMotionPath.mId = this.mId;
    }
}
