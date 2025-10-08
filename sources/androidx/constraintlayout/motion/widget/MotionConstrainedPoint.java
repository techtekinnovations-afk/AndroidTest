package androidx.constraintlayout.motion.widget;

import android.graphics.Rect;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.HashSet;
import java.util.LinkedHashMap;

class MotionConstrainedPoint implements Comparable<MotionConstrainedPoint> {
    static final int CARTESIAN = 2;
    public static final boolean DEBUG = false;
    static final int PERPENDICULAR = 1;
    public static final String TAG = "MotionPaths";
    static String[] sNames = {"position", "x", "y", "width", "height", "pathRotate"};
    private float mAlpha = 1.0f;
    private int mAnimateRelativeTo = -1;
    private boolean mApplyElevation = false;
    LinkedHashMap<String, ConstraintAttribute> mAttributes = new LinkedHashMap<>();
    private int mDrawPath = 0;
    private float mElevation = 0.0f;
    private float mHeight;
    private Easing mKeyFrameEasing;
    int mMode = 0;
    private float mPathRotate = Float.NaN;
    private float mPivotX = Float.NaN;
    private float mPivotY = Float.NaN;
    private float mPosition;
    private float mProgress = Float.NaN;
    private float mRotation = 0.0f;
    private float mRotationX = 0.0f;
    private float mScaleX = 1.0f;
    private float mScaleY = 1.0f;
    double[] mTempDelta = new double[18];
    double[] mTempValue = new double[18];
    private float mTranslationX = 0.0f;
    private float mTranslationY = 0.0f;
    private float mTranslationZ = 0.0f;
    int mVisibility;
    int mVisibilityMode = 0;
    private float mWidth;
    private float mX;
    private float mY;
    public float rotationY = 0.0f;

    MotionConstrainedPoint() {
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
    public void different(MotionConstrainedPoint points, HashSet<String> keySet) {
        if (diff(this.mAlpha, points.mAlpha)) {
            keySet.add("alpha");
        }
        if (diff(this.mElevation, points.mElevation)) {
            keySet.add("elevation");
        }
        if (this.mVisibility != points.mVisibility && this.mVisibilityMode == 0 && (this.mVisibility == 0 || points.mVisibility == 0)) {
            keySet.add("alpha");
        }
        if (diff(this.mRotation, points.mRotation)) {
            keySet.add(Key.ROTATION);
        }
        if (!Float.isNaN(this.mPathRotate) || !Float.isNaN(points.mPathRotate)) {
            keySet.add("transitionPathRotate");
        }
        if (!Float.isNaN(this.mProgress) || !Float.isNaN(points.mProgress)) {
            keySet.add("progress");
        }
        if (diff(this.mRotationX, points.mRotationX)) {
            keySet.add("rotationX");
        }
        if (diff(this.rotationY, points.rotationY)) {
            keySet.add("rotationY");
        }
        if (diff(this.mPivotX, points.mPivotX)) {
            keySet.add(Key.PIVOT_X);
        }
        if (diff(this.mPivotY, points.mPivotY)) {
            keySet.add(Key.PIVOT_Y);
        }
        if (diff(this.mScaleX, points.mScaleX)) {
            keySet.add("scaleX");
        }
        if (diff(this.mScaleY, points.mScaleY)) {
            keySet.add("scaleY");
        }
        if (diff(this.mTranslationX, points.mTranslationX)) {
            keySet.add("translationX");
        }
        if (diff(this.mTranslationY, points.mTranslationY)) {
            keySet.add("translationY");
        }
        if (diff(this.mTranslationZ, points.mTranslationZ)) {
            keySet.add("translationZ");
        }
    }

    /* access modifiers changed from: package-private */
    public void different(MotionConstrainedPoint points, boolean[] mask, String[] custom) {
        int c = 0 + 1;
        mask[0] = mask[0] | diff(this.mPosition, points.mPosition);
        int c2 = c + 1;
        mask[c] = mask[c] | diff(this.mX, points.mX);
        int c3 = c2 + 1;
        mask[c2] = mask[c2] | diff(this.mY, points.mY);
        int c4 = c3 + 1;
        mask[c3] = mask[c3] | diff(this.mWidth, points.mWidth);
        int i = c4 + 1;
        mask[c4] = mask[c4] | diff(this.mHeight, points.mHeight);
    }

    /* access modifiers changed from: package-private */
    public void fillStandard(double[] data, int[] toUse) {
        int[] iArr = toUse;
        float f = this.mPosition;
        float f2 = f;
        float[] set = {f2, this.mX, this.mY, this.mWidth, this.mHeight, this.mAlpha, this.mElevation, this.mRotation, this.mRotationX, this.rotationY, this.mScaleX, this.mScaleY, this.mPivotX, this.mPivotY, this.mTranslationX, this.mTranslationY, this.mTranslationZ, this.mPathRotate};
        int c = 0;
        for (int i = 0; i < iArr.length; i++) {
            if (iArr[i] < set.length) {
                data[c] = (double) set[iArr[i]];
                c++;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean hasCustomData(String name) {
        return this.mAttributes.containsKey(name);
    }

    /* access modifiers changed from: package-private */
    public int getCustomDataCount(String name) {
        return this.mAttributes.get(name).numberOfInterpolatedValues();
    }

    /* access modifiers changed from: package-private */
    public int getCustomData(String name, double[] value, int offset) {
        ConstraintAttribute a = this.mAttributes.get(name);
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

    public int compareTo(MotionConstrainedPoint o) {
        return Float.compare(this.mPosition, o.mPosition);
    }

    public void applyParameters(View view) {
        this.mVisibility = view.getVisibility();
        this.mAlpha = view.getVisibility() != 0 ? 0.0f : view.getAlpha();
        this.mApplyElevation = false;
        this.mElevation = view.getElevation();
        this.mRotation = view.getRotation();
        this.mRotationX = view.getRotationX();
        this.rotationY = view.getRotationY();
        this.mScaleX = view.getScaleX();
        this.mScaleY = view.getScaleY();
        this.mPivotX = view.getPivotX();
        this.mPivotY = view.getPivotY();
        this.mTranslationX = view.getTranslationX();
        this.mTranslationY = view.getTranslationY();
        this.mTranslationZ = view.getTranslationZ();
    }

    public void applyParameters(ConstraintSet.Constraint c) {
        this.mVisibilityMode = c.propertySet.mVisibilityMode;
        this.mVisibility = c.propertySet.visibility;
        this.mAlpha = (c.propertySet.visibility == 0 || this.mVisibilityMode != 0) ? c.propertySet.alpha : 0.0f;
        this.mApplyElevation = c.transform.applyElevation;
        this.mElevation = c.transform.elevation;
        this.mRotation = c.transform.rotation;
        this.mRotationX = c.transform.rotationX;
        this.rotationY = c.transform.rotationY;
        this.mScaleX = c.transform.scaleX;
        this.mScaleY = c.transform.scaleY;
        this.mPivotX = c.transform.transformPivotX;
        this.mPivotY = c.transform.transformPivotY;
        this.mTranslationX = c.transform.translationX;
        this.mTranslationY = c.transform.translationY;
        this.mTranslationZ = c.transform.translationZ;
        this.mKeyFrameEasing = Easing.getInterpolator(c.motion.mTransitionEasing);
        this.mPathRotate = c.motion.mPathRotate;
        this.mDrawPath = c.motion.mDrawPath;
        this.mAnimateRelativeTo = c.motion.mAnimateRelativeTo;
        this.mProgress = c.propertySet.mProgress;
        for (String s : c.mCustomConstraints.keySet()) {
            ConstraintAttribute attr = c.mCustomConstraints.get(s);
            if (attr.isContinuous()) {
                this.mAttributes.put(s, attr);
            }
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addValues(java.util.HashMap<java.lang.String, androidx.constraintlayout.motion.utils.ViewSpline> r9, int r10) {
        /*
            r8 = this;
            java.util.Set r0 = r9.keySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x0008:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0214
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            java.lang.Object r2 = r9.get(r1)
            androidx.constraintlayout.motion.utils.ViewSpline r2 = (androidx.constraintlayout.motion.utils.ViewSpline) r2
            if (r2 != 0) goto L_0x001d
            goto L_0x0008
        L_0x001d:
            int r3 = r1.hashCode()
            r4 = 1
            switch(r3) {
                case -1249320806: goto L_0x00b3;
                case -1249320805: goto L_0x00a9;
                case -1225497657: goto L_0x009e;
                case -1225497656: goto L_0x0093;
                case -1225497655: goto L_0x0088;
                case -1001078227: goto L_0x007d;
                case -908189618: goto L_0x0072;
                case -908189617: goto L_0x0067;
                case -760884510: goto L_0x005d;
                case -760884509: goto L_0x0053;
                case -40300674: goto L_0x0048;
                case -4379043: goto L_0x003d;
                case 37232917: goto L_0x0032;
                case 92909918: goto L_0x0027;
                default: goto L_0x0025;
            }
        L_0x0025:
            goto L_0x00bd
        L_0x0027:
            java.lang.String r3 = "alpha"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0025
            r3 = 0
            goto L_0x00be
        L_0x0032:
            java.lang.String r3 = "transitionPathRotate"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0025
            r3 = 7
            goto L_0x00be
        L_0x003d:
            java.lang.String r3 = "elevation"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0025
            r3 = r4
            goto L_0x00be
        L_0x0048:
            java.lang.String r3 = "rotation"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0025
            r3 = 2
            goto L_0x00be
        L_0x0053:
            java.lang.String r3 = "transformPivotY"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0025
            r3 = 6
            goto L_0x00be
        L_0x005d:
            java.lang.String r3 = "transformPivotX"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0025
            r3 = 5
            goto L_0x00be
        L_0x0067:
            java.lang.String r3 = "scaleY"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0025
            r3 = 10
            goto L_0x00be
        L_0x0072:
            java.lang.String r3 = "scaleX"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0025
            r3 = 9
            goto L_0x00be
        L_0x007d:
            java.lang.String r3 = "progress"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0025
            r3 = 8
            goto L_0x00be
        L_0x0088:
            java.lang.String r3 = "translationZ"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0025
            r3 = 13
            goto L_0x00be
        L_0x0093:
            java.lang.String r3 = "translationY"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0025
            r3 = 12
            goto L_0x00be
        L_0x009e:
            java.lang.String r3 = "translationX"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0025
            r3 = 11
            goto L_0x00be
        L_0x00a9:
            java.lang.String r3 = "rotationY"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0025
            r3 = 4
            goto L_0x00be
        L_0x00b3:
            java.lang.String r3 = "rotationX"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0025
            r3 = 3
            goto L_0x00be
        L_0x00bd:
            r3 = -1
        L_0x00be:
            r5 = 1065353216(0x3f800000, float:1.0)
            r6 = 0
            switch(r3) {
                case 0: goto L_0x01c0;
                case 1: goto L_0x01b1;
                case 2: goto L_0x01a2;
                case 3: goto L_0x0192;
                case 4: goto L_0x0182;
                case 5: goto L_0x0172;
                case 6: goto L_0x0162;
                case 7: goto L_0x0152;
                case 8: goto L_0x0142;
                case 9: goto L_0x0132;
                case 10: goto L_0x0122;
                case 11: goto L_0x0112;
                case 12: goto L_0x0102;
                case 13: goto L_0x00f2;
                default: goto L_0x00c4;
            }
        L_0x00c4:
            java.lang.String r3 = "CUSTOM"
            boolean r3 = r1.startsWith(r3)
            java.lang.String r5 = "MotionPaths"
            if (r3 == 0) goto L_0x01fc
            java.lang.String r3 = ","
            java.lang.String[] r3 = r1.split(r3)
            r3 = r3[r4]
            java.util.LinkedHashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r4 = r8.mAttributes
            boolean r4 = r4.containsKey(r3)
            if (r4 == 0) goto L_0x01fb
            java.util.LinkedHashMap<java.lang.String, androidx.constraintlayout.widget.ConstraintAttribute> r4 = r8.mAttributes
            java.lang.Object r4 = r4.get(r3)
            androidx.constraintlayout.widget.ConstraintAttribute r4 = (androidx.constraintlayout.widget.ConstraintAttribute) r4
            boolean r6 = r2 instanceof androidx.constraintlayout.motion.utils.ViewSpline.CustomSet
            if (r6 == 0) goto L_0x01cf
            r5 = r2
            androidx.constraintlayout.motion.utils.ViewSpline$CustomSet r5 = (androidx.constraintlayout.motion.utils.ViewSpline.CustomSet) r5
            r5.setPoint((int) r10, (androidx.constraintlayout.widget.ConstraintAttribute) r4)
            goto L_0x01fb
        L_0x00f2:
            float r3 = r8.mTranslationZ
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x00fb
            goto L_0x00fd
        L_0x00fb:
            float r6 = r8.mTranslationZ
        L_0x00fd:
            r2.setPoint(r10, r6)
            goto L_0x0212
        L_0x0102:
            float r3 = r8.mTranslationY
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x010b
            goto L_0x010d
        L_0x010b:
            float r6 = r8.mTranslationY
        L_0x010d:
            r2.setPoint(r10, r6)
            goto L_0x0212
        L_0x0112:
            float r3 = r8.mTranslationX
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x011b
            goto L_0x011d
        L_0x011b:
            float r6 = r8.mTranslationX
        L_0x011d:
            r2.setPoint(r10, r6)
            goto L_0x0212
        L_0x0122:
            float r3 = r8.mScaleY
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x012b
            goto L_0x012d
        L_0x012b:
            float r5 = r8.mScaleY
        L_0x012d:
            r2.setPoint(r10, r5)
            goto L_0x0212
        L_0x0132:
            float r3 = r8.mScaleX
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x013b
            goto L_0x013d
        L_0x013b:
            float r5 = r8.mScaleX
        L_0x013d:
            r2.setPoint(r10, r5)
            goto L_0x0212
        L_0x0142:
            float r3 = r8.mProgress
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x014b
            goto L_0x014d
        L_0x014b:
            float r6 = r8.mProgress
        L_0x014d:
            r2.setPoint(r10, r6)
            goto L_0x0212
        L_0x0152:
            float r3 = r8.mPathRotate
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x015b
            goto L_0x015d
        L_0x015b:
            float r6 = r8.mPathRotate
        L_0x015d:
            r2.setPoint(r10, r6)
            goto L_0x0212
        L_0x0162:
            float r3 = r8.mPivotY
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x016b
            goto L_0x016d
        L_0x016b:
            float r6 = r8.mPivotY
        L_0x016d:
            r2.setPoint(r10, r6)
            goto L_0x0212
        L_0x0172:
            float r3 = r8.mPivotX
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x017b
            goto L_0x017d
        L_0x017b:
            float r6 = r8.mPivotX
        L_0x017d:
            r2.setPoint(r10, r6)
            goto L_0x0212
        L_0x0182:
            float r3 = r8.rotationY
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x018b
            goto L_0x018d
        L_0x018b:
            float r6 = r8.rotationY
        L_0x018d:
            r2.setPoint(r10, r6)
            goto L_0x0212
        L_0x0192:
            float r3 = r8.mRotationX
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x019b
            goto L_0x019d
        L_0x019b:
            float r6 = r8.mRotationX
        L_0x019d:
            r2.setPoint(r10, r6)
            goto L_0x0212
        L_0x01a2:
            float r3 = r8.mRotation
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x01ab
            goto L_0x01ad
        L_0x01ab:
            float r6 = r8.mRotation
        L_0x01ad:
            r2.setPoint(r10, r6)
            goto L_0x0212
        L_0x01b1:
            float r3 = r8.mElevation
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x01ba
            goto L_0x01bc
        L_0x01ba:
            float r6 = r8.mElevation
        L_0x01bc:
            r2.setPoint(r10, r6)
            goto L_0x0212
        L_0x01c0:
            float r3 = r8.mAlpha
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 == 0) goto L_0x01c9
            goto L_0x01cb
        L_0x01c9:
            float r5 = r8.mAlpha
        L_0x01cb:
            r2.setPoint(r10, r5)
            goto L_0x0212
        L_0x01cf:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.StringBuilder r6 = r6.append(r1)
            java.lang.String r7 = " ViewSpline not a CustomSet frame = "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r10)
            java.lang.String r7 = ", value"
            java.lang.StringBuilder r6 = r6.append(r7)
            float r7 = r4.getValueToInterpolate()
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r2)
            java.lang.String r6 = r6.toString()
            android.util.Log.e(r5, r6)
        L_0x01fb:
            goto L_0x0212
        L_0x01fc:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "UNKNOWN spline "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r1)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r5, r3)
        L_0x0212:
            goto L_0x0008
        L_0x0214:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.MotionConstrainedPoint.addValues(java.util.HashMap, int):void");
    }

    public void setState(View view) {
        setBounds(view.getX(), view.getY(), (float) view.getWidth(), (float) view.getHeight());
        applyParameters(view);
    }

    public void setState(Rect rect, View view, int rotation, float prevous) {
        setBounds((float) rect.left, (float) rect.top, (float) rect.width(), (float) rect.height());
        applyParameters(view);
        this.mPivotX = Float.NaN;
        this.mPivotY = Float.NaN;
        switch (rotation) {
            case 1:
                this.mRotation = prevous - 90.0f;
                return;
            case 2:
                this.mRotation = 90.0f + prevous;
                return;
            default:
                return;
        }
    }

    public void setState(Rect cw, ConstraintSet constraintSet, int rotation, int viewId) {
        setBounds((float) cw.left, (float) cw.top, (float) cw.width(), (float) cw.height());
        applyParameters(constraintSet.getParameters(viewId));
        switch (rotation) {
            case 1:
            case 3:
                this.mRotation -= 90.0f;
                return;
            case 2:
            case 4:
                this.mRotation += 90.0f;
                if (this.mRotation > 180.0f) {
                    this.mRotation -= 360.0f;
                    return;
                }
                return;
            default:
                return;
        }
    }
}
