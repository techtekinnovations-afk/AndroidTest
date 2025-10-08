package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.R;
import androidx.core.widget.NestedScrollView;
import org.xmlpull.v1.XmlPullParser;

class TouchResponse {
    public static final int COMPLETE_MODE_CONTINUOUS_VELOCITY = 0;
    public static final int COMPLETE_MODE_SPRING = 1;
    private static final boolean DEBUG = false;
    private static final float EPSILON = 1.0E-7f;
    static final int FLAG_DISABLE_POST_SCROLL = 1;
    static final int FLAG_DISABLE_SCROLL = 2;
    static final int FLAG_SUPPORT_SCROLL_UP = 4;
    private static final int SEC_TO_MILLISECONDS = 1000;
    private static final int SIDE_BOTTOM = 3;
    private static final int SIDE_END = 6;
    private static final int SIDE_LEFT = 1;
    private static final int SIDE_MIDDLE = 4;
    private static final int SIDE_RIGHT = 2;
    private static final int SIDE_START = 5;
    private static final int SIDE_TOP = 0;
    private static final String TAG = "TouchResponse";
    private static final float[][] TOUCH_DIRECTION = {new float[]{0.0f, -1.0f}, new float[]{0.0f, 1.0f}, new float[]{-1.0f, 0.0f}, new float[]{1.0f, 0.0f}, new float[]{-1.0f, 0.0f}, new float[]{1.0f, 0.0f}};
    private static final int TOUCH_DOWN = 1;
    private static final int TOUCH_END = 5;
    private static final int TOUCH_LEFT = 2;
    private static final int TOUCH_RIGHT = 3;
    private static final float[][] TOUCH_SIDES = {new float[]{0.5f, 0.0f}, new float[]{0.0f, 0.5f}, new float[]{1.0f, 0.5f}, new float[]{0.5f, 1.0f}, new float[]{0.5f, 0.5f}, new float[]{0.0f, 0.5f}, new float[]{1.0f, 0.5f}};
    private static final int TOUCH_START = 4;
    private static final int TOUCH_UP = 0;
    private float[] mAnchorDpDt = new float[2];
    private int mAutoCompleteMode = 0;
    private float mDragScale = 1.0f;
    private boolean mDragStarted = false;
    private float mDragThreshold = 10.0f;
    private int mFlags = 0;
    boolean mIsRotateMode = false;
    private float mLastTouchX;
    private float mLastTouchY;
    private int mLimitBoundsTo = -1;
    private float mMaxAcceleration = 1.2f;
    private float mMaxVelocity = 4.0f;
    private final MotionLayout mMotionLayout;
    private boolean mMoveWhenScrollAtTop = true;
    private int mOnTouchUp = 0;
    float mRotateCenterX = 0.5f;
    float mRotateCenterY = 0.5f;
    private int mRotationCenterId = -1;
    private int mSpringBoundary = 0;
    private float mSpringDamping = 10.0f;
    private float mSpringMass = 1.0f;
    private float mSpringStiffness = Float.NaN;
    private float mSpringStopThreshold = Float.NaN;
    private int[] mTempLoc = new int[2];
    private int mTouchAnchorId = -1;
    private int mTouchAnchorSide = 0;
    private float mTouchAnchorX = 0.5f;
    private float mTouchAnchorY = 0.5f;
    private float mTouchDirectionX = 0.0f;
    private float mTouchDirectionY = 1.0f;
    private int mTouchRegionId = -1;
    private int mTouchSide = 0;

    TouchResponse(Context context, MotionLayout layout, XmlPullParser parser) {
        this.mMotionLayout = layout;
        fillFromAttributeList(context, Xml.asAttributeSet(parser));
    }

    TouchResponse(MotionLayout layout, OnSwipe onSwipe) {
        this.mMotionLayout = layout;
        this.mTouchAnchorId = onSwipe.getTouchAnchorId();
        this.mTouchAnchorSide = onSwipe.getTouchAnchorSide();
        if (this.mTouchAnchorSide != -1) {
            this.mTouchAnchorX = TOUCH_SIDES[this.mTouchAnchorSide][0];
            this.mTouchAnchorY = TOUCH_SIDES[this.mTouchAnchorSide][1];
        }
        this.mTouchSide = onSwipe.getDragDirection();
        if (this.mTouchSide < TOUCH_DIRECTION.length) {
            this.mTouchDirectionX = TOUCH_DIRECTION[this.mTouchSide][0];
            this.mTouchDirectionY = TOUCH_DIRECTION[this.mTouchSide][1];
        } else {
            this.mTouchDirectionY = Float.NaN;
            this.mTouchDirectionX = Float.NaN;
            this.mIsRotateMode = true;
        }
        this.mMaxVelocity = onSwipe.getMaxVelocity();
        this.mMaxAcceleration = onSwipe.getMaxAcceleration();
        this.mMoveWhenScrollAtTop = onSwipe.getMoveWhenScrollAtTop();
        this.mDragScale = onSwipe.getDragScale();
        this.mDragThreshold = onSwipe.getDragThreshold();
        this.mTouchRegionId = onSwipe.getTouchRegionId();
        this.mOnTouchUp = onSwipe.getOnTouchUp();
        this.mFlags = onSwipe.getNestedScrollFlags();
        this.mLimitBoundsTo = onSwipe.getLimitBoundsTo();
        this.mRotationCenterId = onSwipe.getRotationCenterId();
        this.mSpringBoundary = onSwipe.getSpringBoundary();
        this.mSpringDamping = onSwipe.getSpringDamping();
        this.mSpringMass = onSwipe.getSpringMass();
        this.mSpringStiffness = onSwipe.getSpringStiffness();
        this.mSpringStopThreshold = onSwipe.getSpringStopThreshold();
        this.mAutoCompleteMode = onSwipe.getAutoCompleteMode();
    }

    public void setRTL(boolean rtl) {
        if (rtl) {
            TOUCH_DIRECTION[4] = TOUCH_DIRECTION[3];
            TOUCH_DIRECTION[5] = TOUCH_DIRECTION[2];
            TOUCH_SIDES[5] = TOUCH_SIDES[2];
            TOUCH_SIDES[6] = TOUCH_SIDES[1];
        } else {
            TOUCH_DIRECTION[4] = TOUCH_DIRECTION[2];
            TOUCH_DIRECTION[5] = TOUCH_DIRECTION[3];
            TOUCH_SIDES[5] = TOUCH_SIDES[1];
            TOUCH_SIDES[6] = TOUCH_SIDES[2];
        }
        this.mTouchAnchorX = TOUCH_SIDES[this.mTouchAnchorSide][0];
        this.mTouchAnchorY = TOUCH_SIDES[this.mTouchAnchorSide][1];
        if (this.mTouchSide < TOUCH_DIRECTION.length) {
            this.mTouchDirectionX = TOUCH_DIRECTION[this.mTouchSide][0];
            this.mTouchDirectionY = TOUCH_DIRECTION[this.mTouchSide][1];
        }
    }

    private void fillFromAttributeList(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.OnSwipe);
        fill(a);
        a.recycle();
    }

    private void fill(TypedArray a) {
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.OnSwipe_touchAnchorId) {
                this.mTouchAnchorId = a.getResourceId(attr, this.mTouchAnchorId);
            } else if (attr == R.styleable.OnSwipe_touchAnchorSide) {
                this.mTouchAnchorSide = a.getInt(attr, this.mTouchAnchorSide);
                this.mTouchAnchorX = TOUCH_SIDES[this.mTouchAnchorSide][0];
                this.mTouchAnchorY = TOUCH_SIDES[this.mTouchAnchorSide][1];
            } else if (attr == R.styleable.OnSwipe_dragDirection) {
                this.mTouchSide = a.getInt(attr, this.mTouchSide);
                if (this.mTouchSide < TOUCH_DIRECTION.length) {
                    this.mTouchDirectionX = TOUCH_DIRECTION[this.mTouchSide][0];
                    this.mTouchDirectionY = TOUCH_DIRECTION[this.mTouchSide][1];
                } else {
                    this.mTouchDirectionY = Float.NaN;
                    this.mTouchDirectionX = Float.NaN;
                    this.mIsRotateMode = true;
                }
            } else if (attr == R.styleable.OnSwipe_maxVelocity) {
                this.mMaxVelocity = a.getFloat(attr, this.mMaxVelocity);
            } else if (attr == R.styleable.OnSwipe_maxAcceleration) {
                this.mMaxAcceleration = a.getFloat(attr, this.mMaxAcceleration);
            } else if (attr == R.styleable.OnSwipe_moveWhenScrollAtTop) {
                this.mMoveWhenScrollAtTop = a.getBoolean(attr, this.mMoveWhenScrollAtTop);
            } else if (attr == R.styleable.OnSwipe_dragScale) {
                this.mDragScale = a.getFloat(attr, this.mDragScale);
            } else if (attr == R.styleable.OnSwipe_dragThreshold) {
                this.mDragThreshold = a.getFloat(attr, this.mDragThreshold);
            } else if (attr == R.styleable.OnSwipe_touchRegionId) {
                this.mTouchRegionId = a.getResourceId(attr, this.mTouchRegionId);
            } else if (attr == R.styleable.OnSwipe_onTouchUp) {
                this.mOnTouchUp = a.getInt(attr, this.mOnTouchUp);
            } else if (attr == R.styleable.OnSwipe_nestedScrollFlags) {
                this.mFlags = a.getInteger(attr, 0);
            } else if (attr == R.styleable.OnSwipe_limitBoundsTo) {
                this.mLimitBoundsTo = a.getResourceId(attr, 0);
            } else if (attr == R.styleable.OnSwipe_rotationCenterId) {
                this.mRotationCenterId = a.getResourceId(attr, this.mRotationCenterId);
            } else if (attr == R.styleable.OnSwipe_springDamping) {
                this.mSpringDamping = a.getFloat(attr, this.mSpringDamping);
            } else if (attr == R.styleable.OnSwipe_springMass) {
                this.mSpringMass = a.getFloat(attr, this.mSpringMass);
            } else if (attr == R.styleable.OnSwipe_springStiffness) {
                this.mSpringStiffness = a.getFloat(attr, this.mSpringStiffness);
            } else if (attr == R.styleable.OnSwipe_springStopThreshold) {
                this.mSpringStopThreshold = a.getFloat(attr, this.mSpringStopThreshold);
            } else if (attr == R.styleable.OnSwipe_springBoundary) {
                this.mSpringBoundary = a.getInt(attr, this.mSpringBoundary);
            } else if (attr == R.styleable.OnSwipe_autoCompleteMode) {
                this.mAutoCompleteMode = a.getInt(attr, this.mAutoCompleteMode);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setUpTouchEvent(float lastTouchX, float lastTouchY) {
        this.mLastTouchX = lastTouchX;
        this.mLastTouchY = lastTouchY;
        this.mDragStarted = false;
    }

    /* JADX WARNING: type inference failed for: r29v1 */
    /* JADX WARNING: type inference failed for: r29v2 */
    /* JADX WARNING: type inference failed for: r29v3 */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x02d2  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x02ee  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x030f  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x032b  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0395  */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void processTouchRotateEvent(android.view.MotionEvent r31, androidx.constraintlayout.motion.widget.MotionLayout.MotionTracker r32, int r33, androidx.constraintlayout.motion.widget.MotionScene r34) {
        /*
            r30 = this;
            r0 = r30
            r1 = r32
            r2 = r31
            r1.addMovement(r2)
            int r3 = r2.getAction()
            r5 = -1
            r7 = 0
            r9 = 1073741824(0x40000000, float:2.0)
            r10 = 1
            switch(r3) {
                case 0: goto L_0x03ab;
                case 1: goto L_0x0203;
                case 2: goto L_0x0017;
                default: goto L_0x0015;
            }
        L_0x0015:
            goto L_0x03bb
        L_0x0017:
            float r3 = r2.getRawY()
            float r11 = r0.mLastTouchY
            float r3 = r3 - r11
            float r11 = r2.getRawX()
            float r12 = r0.mLastTouchX
            float r11 = r11 - r12
            androidx.constraintlayout.motion.widget.MotionLayout r12 = r0.mMotionLayout
            int r12 = r12.getWidth()
            float r12 = (float) r12
            float r12 = r12 / r9
            androidx.constraintlayout.motion.widget.MotionLayout r13 = r0.mMotionLayout
            int r13 = r13.getHeight()
            float r13 = (float) r13
            float r13 = r13 / r9
            int r14 = r0.mRotationCenterId
            if (r14 == r5) goto L_0x0071
            androidx.constraintlayout.motion.widget.MotionLayout r14 = r0.mMotionLayout
            int r15 = r0.mRotationCenterId
            android.view.View r14 = r14.findViewById(r15)
            androidx.constraintlayout.motion.widget.MotionLayout r15 = r0.mMotionLayout
            r16 = 1135869952(0x43b40000, float:360.0)
            int[] r4 = r0.mTempLoc
            r15.getLocationOnScreen(r4)
            int[] r4 = r0.mTempLoc
            r4 = r4[r7]
            float r4 = (float) r4
            int r15 = r14.getLeft()
            int r17 = r14.getRight()
            int r15 = r15 + r17
            float r15 = (float) r15
            float r15 = r15 / r9
            float r12 = r4 + r15
            int[] r4 = r0.mTempLoc
            r4 = r4[r10]
            float r4 = (float) r4
            int r15 = r14.getTop()
            int r17 = r14.getBottom()
            int r15 = r15 + r17
            float r15 = (float) r15
            float r15 = r15 / r9
            float r13 = r4 + r15
        L_0x0070:
            goto L_0x00c4
        L_0x0071:
            r16 = 1135869952(0x43b40000, float:360.0)
            int r4 = r0.mTouchAnchorId
            if (r4 == r5) goto L_0x0070
            androidx.constraintlayout.motion.widget.MotionLayout r4 = r0.mMotionLayout
            int r14 = r0.mTouchAnchorId
            androidx.constraintlayout.motion.widget.MotionController r4 = r4.getMotionController(r14)
            androidx.constraintlayout.motion.widget.MotionLayout r14 = r0.mMotionLayout
            int r15 = r4.getAnimateRelativeTo()
            android.view.View r14 = r14.findViewById(r15)
            if (r14 != 0) goto L_0x0093
            java.lang.String r9 = "TouchResponse"
            java.lang.String r15 = "could not find view to animate to"
            android.util.Log.e(r9, r15)
            goto L_0x00c4
        L_0x0093:
            androidx.constraintlayout.motion.widget.MotionLayout r15 = r0.mMotionLayout
            r17 = r9
            int[] r9 = r0.mTempLoc
            r15.getLocationOnScreen(r9)
            int[] r9 = r0.mTempLoc
            r9 = r9[r7]
            float r9 = (float) r9
            int r15 = r14.getLeft()
            int r18 = r14.getRight()
            int r15 = r15 + r18
            float r15 = (float) r15
            float r15 = r15 / r17
            float r12 = r9 + r15
            int[] r9 = r0.mTempLoc
            r9 = r9[r10]
            float r9 = (float) r9
            int r15 = r14.getTop()
            int r18 = r14.getBottom()
            int r15 = r15 + r18
            float r15 = (float) r15
            float r15 = r15 / r17
            float r13 = r9 + r15
        L_0x00c4:
            float r4 = r2.getRawX()
            float r4 = r4 - r12
            float r9 = r2.getRawY()
            float r9 = r9 - r13
            float r14 = r2.getRawY()
            float r14 = r14 - r13
            double r14 = (double) r14
            float r17 = r2.getRawX()
            float r7 = r17 - r12
            r17 = r9
            double r8 = (double) r7
            double r7 = java.lang.Math.atan2(r14, r8)
            float r9 = r0.mLastTouchY
            float r9 = r9 - r13
            double r14 = (double) r9
            float r9 = r0.mLastTouchX
            float r9 = r9 - r12
            r21 = r7
            double r6 = (double) r9
            double r6 = java.lang.Math.atan2(r14, r6)
            double r8 = r21 - r6
            r14 = 4640537203540230144(0x4066800000000000, double:180.0)
            double r8 = r8 * r14
            r14 = 4614256656552045848(0x400921fb54442d18, double:3.141592653589793)
            double r8 = r8 / r14
            float r8 = (float) r8
            r9 = 1134886912(0x43a50000, float:330.0)
            int r9 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r9 <= 0) goto L_0x0107
            float r8 = r8 - r16
            goto L_0x010f
        L_0x0107:
            r9 = -1012596736(0xffffffffc3a50000, float:-330.0)
            int r9 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r9 >= 0) goto L_0x010f
            float r8 = r8 + r16
        L_0x010f:
            float r9 = java.lang.Math.abs(r8)
            double r14 = (double) r9
            r23 = 4576918229304087675(0x3f847ae147ae147b, double:0.01)
            int r9 = (r14 > r23 ? 1 : (r14 == r23 ? 0 : -1))
            if (r9 > 0) goto L_0x0121
            boolean r9 = r0.mDragStarted
            if (r9 == 0) goto L_0x03bb
        L_0x0121:
            androidx.constraintlayout.motion.widget.MotionLayout r9 = r0.mMotionLayout
            float r9 = r9.getProgress()
            boolean r14 = r0.mDragStarted
            if (r14 != 0) goto L_0x0132
            r0.mDragStarted = r10
            androidx.constraintlayout.motion.widget.MotionLayout r14 = r0.mMotionLayout
            r14.setProgress(r9)
        L_0x0132:
            int r14 = r0.mTouchAnchorId
            if (r14 == r5) goto L_0x0160
            androidx.constraintlayout.motion.widget.MotionLayout r5 = r0.mMotionLayout
            int r14 = r0.mTouchAnchorId
            float r15 = r0.mTouchAnchorX
            r29 = r10
            float r10 = r0.mTouchAnchorY
            float[] r2 = r0.mAnchorDpDt
            r28 = r2
            r23 = r5
            r25 = r9
            r27 = r10
            r24 = r14
            r26 = r15
            r23.getAnchorDpDt(r24, r25, r26, r27, r28)
            float[] r2 = r0.mAnchorDpDt
            float[] r5 = r0.mAnchorDpDt
            r5 = r5[r29]
            double r9 = (double) r5
            double r9 = java.lang.Math.toDegrees(r9)
            float r5 = (float) r9
            r2[r29] = r5
            goto L_0x0168
        L_0x0160:
            r25 = r9
            r29 = r10
            float[] r2 = r0.mAnchorDpDt
            r2[r29] = r16
        L_0x0168:
            float r2 = r0.mDragScale
            float r2 = r2 * r8
            float[] r5 = r0.mAnchorDpDt
            r5 = r5[r29]
            float r2 = r2 / r5
            float r9 = r25 + r2
            r5 = 1065353216(0x3f800000, float:1.0)
            float r9 = java.lang.Math.min(r9, r5)
            r5 = 0
            float r9 = java.lang.Math.max(r9, r5)
            androidx.constraintlayout.motion.widget.MotionLayout r10 = r0.mMotionLayout
            float r10 = r10.getProgress()
            int r14 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r14 == 0) goto L_0x01e7
            int r14 = (r10 > r5 ? 1 : (r10 == r5 ? 0 : -1))
            if (r14 == 0) goto L_0x0191
            r20 = 1065353216(0x3f800000, float:1.0)
            int r5 = (r10 > r20 ? 1 : (r10 == r20 ? 0 : -1))
            if (r5 != 0) goto L_0x01a0
        L_0x0191:
            androidx.constraintlayout.motion.widget.MotionLayout r5 = r0.mMotionLayout
            r19 = 0
            int r14 = (r10 > r19 ? 1 : (r10 == r19 ? 0 : -1))
            if (r14 != 0) goto L_0x019c
            r14 = r29
            goto L_0x019d
        L_0x019c:
            r14 = 0
        L_0x019d:
            r5.endTrigger(r14)
        L_0x01a0:
            androidx.constraintlayout.motion.widget.MotionLayout r5 = r0.mMotionLayout
            r5.setProgress(r9)
            r5 = 1000(0x3e8, float:1.401E-42)
            r1.computeCurrentVelocity(r5)
            float r5 = r1.getXVelocity()
            float r14 = r1.getYVelocity()
            r16 = r2
            r15 = r3
            double r2 = (double) r14
            r23 = r6
            double r6 = (double) r5
            double r2 = java.lang.Math.hypot(r2, r6)
            double r6 = (double) r14
            r18 = r2
            double r2 = (double) r5
            double r2 = java.lang.Math.atan2(r6, r2)
            double r2 = r2 - r21
            double r2 = java.lang.Math.sin(r2)
            double r2 = r2 * r18
            double r6 = (double) r4
            r18 = r2
            r2 = r17
            r17 = r4
            double r3 = (double) r2
            double r3 = java.lang.Math.hypot(r6, r3)
            double r3 = r18 / r3
            float r3 = (float) r3
            androidx.constraintlayout.motion.widget.MotionLayout r4 = r0.mMotionLayout
            double r6 = (double) r3
            double r6 = java.lang.Math.toDegrees(r6)
            float r6 = (float) r6
            r4.mLastVelocity = r6
            goto L_0x01f5
        L_0x01e7:
            r16 = r2
            r15 = r3
            r23 = r6
            r2 = r17
            r17 = r4
            androidx.constraintlayout.motion.widget.MotionLayout r3 = r0.mMotionLayout
            r5 = 0
            r3.mLastVelocity = r5
        L_0x01f5:
            float r3 = r31.getRawX()
            r0.mLastTouchX = r3
            float r3 = r31.getRawY()
            r0.mLastTouchY = r3
            goto L_0x03bb
        L_0x0203:
            r17 = r9
            r29 = r10
            r16 = 1135869952(0x43b40000, float:360.0)
            r2 = 0
            r0.mDragStarted = r2
            r2 = 16
            r1.computeCurrentVelocity(r2)
            float r2 = r1.getXVelocity()
            float r3 = r1.getYVelocity()
            androidx.constraintlayout.motion.widget.MotionLayout r4 = r0.mMotionLayout
            float r4 = r4.getProgress()
            r8 = r4
            androidx.constraintlayout.motion.widget.MotionLayout r6 = r0.mMotionLayout
            int r6 = r6.getWidth()
            float r6 = (float) r6
            float r6 = r6 / r17
            androidx.constraintlayout.motion.widget.MotionLayout r7 = r0.mMotionLayout
            int r7 = r7.getHeight()
            float r7 = (float) r7
            float r7 = r7 / r17
            int r9 = r0.mRotationCenterId
            if (r9 == r5) goto L_0x026e
            androidx.constraintlayout.motion.widget.MotionLayout r9 = r0.mMotionLayout
            int r10 = r0.mRotationCenterId
            android.view.View r9 = r9.findViewById(r10)
            androidx.constraintlayout.motion.widget.MotionLayout r10 = r0.mMotionLayout
            int[] r11 = r0.mTempLoc
            r10.getLocationOnScreen(r11)
            int[] r10 = r0.mTempLoc
            r18 = 0
            r10 = r10[r18]
            float r10 = (float) r10
            int r11 = r9.getLeft()
            int r12 = r9.getRight()
            int r11 = r11 + r12
            float r11 = (float) r11
            float r11 = r11 / r17
            float r6 = r10 + r11
            int[] r10 = r0.mTempLoc
            r10 = r10[r29]
            float r10 = (float) r10
            int r11 = r9.getTop()
            int r12 = r9.getBottom()
            int r11 = r11 + r12
            float r11 = (float) r11
            float r11 = r11 / r17
            float r7 = r10 + r11
            goto L_0x02b6
        L_0x026e:
            int r9 = r0.mTouchAnchorId
            if (r9 == r5) goto L_0x02b6
            androidx.constraintlayout.motion.widget.MotionLayout r9 = r0.mMotionLayout
            int r10 = r0.mTouchAnchorId
            androidx.constraintlayout.motion.widget.MotionController r9 = r9.getMotionController(r10)
            androidx.constraintlayout.motion.widget.MotionLayout r10 = r0.mMotionLayout
            int r11 = r9.getAnimateRelativeTo()
            android.view.View r10 = r10.findViewById(r11)
            androidx.constraintlayout.motion.widget.MotionLayout r11 = r0.mMotionLayout
            int[] r12 = r0.mTempLoc
            r11.getLocationOnScreen(r12)
            int[] r11 = r0.mTempLoc
            r18 = 0
            r11 = r11[r18]
            float r11 = (float) r11
            int r12 = r10.getLeft()
            int r13 = r10.getRight()
            int r12 = r12 + r13
            float r12 = (float) r12
            float r12 = r12 / r17
            float r6 = r11 + r12
            int[] r11 = r0.mTempLoc
            r11 = r11[r29]
            float r11 = (float) r11
            int r12 = r10.getTop()
            int r13 = r10.getBottom()
            int r12 = r12 + r13
            float r12 = (float) r12
            float r12 = r12 / r17
            float r7 = r11 + r12
            r12 = r6
            r13 = r7
            goto L_0x02b8
        L_0x02b6:
            r12 = r6
            r13 = r7
        L_0x02b8:
            float r6 = r31.getRawX()
            float r14 = r6 - r12
            float r6 = r31.getRawY()
            float r15 = r6 - r13
            double r6 = (double) r15
            double r9 = (double) r14
            double r6 = java.lang.Math.atan2(r6, r9)
            double r17 = java.lang.Math.toDegrees(r6)
            int r6 = r0.mTouchAnchorId
            if (r6 == r5) goto L_0x02ee
            androidx.constraintlayout.motion.widget.MotionLayout r6 = r0.mMotionLayout
            int r7 = r0.mTouchAnchorId
            float r9 = r0.mTouchAnchorX
            float r10 = r0.mTouchAnchorY
            float[] r11 = r0.mAnchorDpDt
            r6.getAnchorDpDt(r7, r8, r9, r10, r11)
            float[] r5 = r0.mAnchorDpDt
            float[] r6 = r0.mAnchorDpDt
            r6 = r6[r29]
            double r6 = (double) r6
            double r6 = java.lang.Math.toDegrees(r6)
            float r6 = (float) r6
            r5[r29] = r6
            goto L_0x02f2
        L_0x02ee:
            float[] r5 = r0.mAnchorDpDt
            r5[r29] = r16
        L_0x02f2:
            float r5 = r3 + r15
            double r5 = (double) r5
            float r7 = r2 + r14
            double r9 = (double) r7
            double r5 = java.lang.Math.atan2(r5, r9)
            double r5 = java.lang.Math.toDegrees(r5)
            double r9 = r5 - r17
            float r7 = (float) r9
            r9 = 1115291648(0x427a0000, float:62.5)
            float r10 = r7 * r9
            boolean r11 = java.lang.Float.isNaN(r10)
            r16 = 1077936128(0x40400000, float:3.0)
            if (r11 != 0) goto L_0x031a
            float r11 = r10 * r16
            float r1 = r0.mDragScale
            float r11 = r11 * r1
            float[] r1 = r0.mAnchorDpDt
            r1 = r1[r29]
            float r11 = r11 / r1
            float r8 = r8 + r11
        L_0x031a:
            r19 = 0
            int r1 = (r8 > r19 ? 1 : (r8 == r19 ? 0 : -1))
            if (r1 == 0) goto L_0x0395
            r20 = 1065353216(0x3f800000, float:1.0)
            int r1 = (r8 > r20 ? 1 : (r8 == r20 ? 0 : -1))
            if (r1 == 0) goto L_0x0395
            int r1 = r0.mOnTouchUp
            r11 = 3
            if (r1 == r11) goto L_0x0395
            float r1 = r0.mDragScale
            float r1 = r1 * r10
            float[] r11 = r0.mAnchorDpDt
            r11 = r11[r29]
            float r1 = r1 / r11
            double r10 = (double) r8
            r21 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            int r10 = (r10 > r21 ? 1 : (r10 == r21 ? 0 : -1))
            if (r10 >= 0) goto L_0x033c
            r10 = 0
            goto L_0x033e
        L_0x033c:
            r10 = 1065353216(0x3f800000, float:1.0)
        L_0x033e:
            int r11 = r0.mOnTouchUp
            r21 = r1
            r1 = 6
            if (r11 != r1) goto L_0x0357
            float r1 = r4 + r21
            r19 = 0
            int r1 = (r1 > r19 ? 1 : (r1 == r19 ? 0 : -1))
            if (r1 >= 0) goto L_0x0352
            float r1 = java.lang.Math.abs(r21)
            goto L_0x0354
        L_0x0352:
            r1 = r21
        L_0x0354:
            r10 = 1065353216(0x3f800000, float:1.0)
            goto L_0x0359
        L_0x0357:
            r1 = r21
        L_0x0359:
            int r11 = r0.mOnTouchUp
            r21 = r1
            r1 = 7
            if (r11 != r1) goto L_0x0372
            float r1 = r4 + r21
            r20 = 1065353216(0x3f800000, float:1.0)
            int r1 = (r1 > r20 ? 1 : (r1 == r20 ? 0 : -1))
            if (r1 <= 0) goto L_0x036e
            float r1 = java.lang.Math.abs(r21)
            float r1 = -r1
            goto L_0x0370
        L_0x036e:
            r1 = r21
        L_0x0370:
            r10 = 0
            goto L_0x0374
        L_0x0372:
            r1 = r21
        L_0x0374:
            androidx.constraintlayout.motion.widget.MotionLayout r11 = r0.mMotionLayout
            r21 = r1
            int r1 = r0.mOnTouchUp
            r22 = r2
            float r2 = r21 * r16
            r11.touchAnimateTo(r1, r10, r2)
            r19 = 0
            int r1 = (r19 > r4 ? 1 : (r19 == r4 ? 0 : -1))
            if (r1 >= 0) goto L_0x038d
            r20 = 1065353216(0x3f800000, float:1.0)
            int r1 = (r20 > r4 ? 1 : (r20 == r4 ? 0 : -1))
            if (r1 > 0) goto L_0x0394
        L_0x038d:
            androidx.constraintlayout.motion.widget.MotionLayout r1 = r0.mMotionLayout
            androidx.constraintlayout.motion.widget.MotionLayout$TransitionState r2 = androidx.constraintlayout.motion.widget.MotionLayout.TransitionState.FINISHED
            r1.setState(r2)
        L_0x0394:
            goto L_0x03bb
        L_0x0395:
            r22 = r2
            r19 = 0
            int r1 = (r19 > r8 ? 1 : (r19 == r8 ? 0 : -1))
            if (r1 >= 0) goto L_0x03a3
            r20 = 1065353216(0x3f800000, float:1.0)
            int r1 = (r20 > r8 ? 1 : (r20 == r8 ? 0 : -1))
            if (r1 > 0) goto L_0x03bb
        L_0x03a3:
            androidx.constraintlayout.motion.widget.MotionLayout r1 = r0.mMotionLayout
            androidx.constraintlayout.motion.widget.MotionLayout$TransitionState r2 = androidx.constraintlayout.motion.widget.MotionLayout.TransitionState.FINISHED
            r1.setState(r2)
            goto L_0x03bb
        L_0x03ab:
            float r1 = r31.getRawX()
            r0.mLastTouchX = r1
            float r1 = r31.getRawY()
            r0.mLastTouchY = r1
            r2 = 0
            r0.mDragStarted = r2
        L_0x03bb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.TouchResponse.processTouchRotateEvent(android.view.MotionEvent, androidx.constraintlayout.motion.widget.MotionLayout$MotionTracker, int, androidx.constraintlayout.motion.widget.MotionScene):void");
    }

    /* JADX WARNING: type inference failed for: r22v5 */
    /* JADX WARNING: type inference failed for: r21v1 */
    /* JADX WARNING: type inference failed for: r21v2 */
    /* JADX WARNING: type inference failed for: r22v6 */
    /* JADX WARNING: type inference failed for: r21v3 */
    /* JADX WARNING: type inference failed for: r22v7 */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void processTouchEvent(android.view.MotionEvent r24, androidx.constraintlayout.motion.widget.MotionLayout.MotionTracker r25, int r26, androidx.constraintlayout.motion.widget.MotionScene r27) {
        /*
            r23 = this;
            r0 = r23
            r1 = r25
            boolean r2 = r0.mIsRotateMode
            if (r2 == 0) goto L_0x000c
            r23.processTouchRotateEvent(r24, r25, r26, r27)
            return
        L_0x000c:
            r2 = r24
            r1.addMovement(r2)
            int r3 = r2.getAction()
            r4 = 7
            r5 = 6
            r6 = -1
            r8 = 1065353216(0x3f800000, float:1.0)
            r9 = 1
            r11 = 0
            switch(r3) {
                case 0: goto L_0x0238;
                case 1: goto L_0x0152;
                case 2: goto L_0x0021;
                default: goto L_0x001f;
            }
        L_0x001f:
            goto L_0x0248
        L_0x0021:
            float r3 = r2.getRawY()
            float r12 = r0.mLastTouchY
            float r3 = r3 - r12
            float r12 = r2.getRawX()
            float r13 = r0.mLastTouchX
            float r12 = r12 - r13
            float r13 = r0.mTouchDirectionX
            float r13 = r13 * r12
            float r14 = r0.mTouchDirectionY
            float r14 = r14 * r3
            float r13 = r13 + r14
            float r14 = java.lang.Math.abs(r13)
            float r15 = r0.mDragThreshold
            int r14 = (r14 > r15 ? 1 : (r14 == r15 ? 0 : -1))
            if (r14 > 0) goto L_0x0044
            boolean r14 = r0.mDragStarted
            if (r14 == 0) goto L_0x0248
        L_0x0044:
            androidx.constraintlayout.motion.widget.MotionLayout r14 = r0.mMotionLayout
            float r14 = r14.getProgress()
            boolean r15 = r0.mDragStarted
            if (r15 != 0) goto L_0x0055
            r0.mDragStarted = r9
            androidx.constraintlayout.motion.widget.MotionLayout r15 = r0.mMotionLayout
            r15.setProgress(r14)
        L_0x0055:
            int r15 = r0.mTouchAnchorId
            if (r15 == r6) goto L_0x0075
            androidx.constraintlayout.motion.widget.MotionLayout r15 = r0.mMotionLayout
            int r6 = r0.mTouchAnchorId
            r21 = r9
            float r9 = r0.mTouchAnchorX
            r22 = 0
            float r10 = r0.mTouchAnchorY
            float[] r7 = r0.mAnchorDpDt
            r16 = r6
            r20 = r7
            r18 = r9
            r19 = r10
            r17 = r14
            r15.getAnchorDpDt(r16, r17, r18, r19, r20)
            goto L_0x009a
        L_0x0075:
            r21 = r9
            r17 = r14
            r22 = 0
            androidx.constraintlayout.motion.widget.MotionLayout r6 = r0.mMotionLayout
            int r6 = r6.getWidth()
            androidx.constraintlayout.motion.widget.MotionLayout r7 = r0.mMotionLayout
            int r7 = r7.getHeight()
            int r6 = java.lang.Math.min(r6, r7)
            float r6 = (float) r6
            float[] r7 = r0.mAnchorDpDt
            float r9 = r0.mTouchDirectionY
            float r9 = r9 * r6
            r7[r21] = r9
            float[] r7 = r0.mAnchorDpDt
            float r9 = r0.mTouchDirectionX
            float r9 = r9 * r6
            r7[r22] = r9
        L_0x009a:
            float r6 = r0.mTouchDirectionX
            float[] r7 = r0.mAnchorDpDt
            r7 = r7[r22]
            float r6 = r6 * r7
            float r7 = r0.mTouchDirectionY
            float[] r9 = r0.mAnchorDpDt
            r9 = r9[r21]
            float r7 = r7 * r9
            float r6 = r6 + r7
            float r7 = r0.mDragScale
            float r6 = r6 * r7
            float r7 = java.lang.Math.abs(r6)
            double r9 = (double) r7
            r14 = 4576918229304087675(0x3f847ae147ae147b, double:0.01)
            int r7 = (r9 > r14 ? 1 : (r9 == r14 ? 0 : -1))
            r9 = 1008981770(0x3c23d70a, float:0.01)
            if (r7 >= 0) goto L_0x00c5
            float[] r7 = r0.mAnchorDpDt
            r7[r22] = r9
            float[] r7 = r0.mAnchorDpDt
            r7[r21] = r9
        L_0x00c5:
            float r7 = r0.mTouchDirectionX
            int r7 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r7 == 0) goto L_0x00d2
            float[] r7 = r0.mAnchorDpDt
            r7 = r7[r22]
            float r7 = r12 / r7
            goto L_0x00d8
        L_0x00d2:
            float[] r7 = r0.mAnchorDpDt
            r7 = r7[r21]
            float r7 = r3 / r7
        L_0x00d8:
            float r14 = r17 + r7
            float r10 = java.lang.Math.min(r14, r8)
            float r10 = java.lang.Math.max(r10, r11)
            int r14 = r0.mOnTouchUp
            if (r14 != r5) goto L_0x00ea
            float r10 = java.lang.Math.max(r10, r9)
        L_0x00ea:
            int r5 = r0.mOnTouchUp
            if (r5 != r4) goto L_0x00f5
            r4 = 1065185444(0x3f7d70a4, float:0.99)
            float r10 = java.lang.Math.min(r10, r4)
        L_0x00f5:
            androidx.constraintlayout.motion.widget.MotionLayout r4 = r0.mMotionLayout
            float r4 = r4.getProgress()
            int r5 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r5 == 0) goto L_0x0140
            int r5 = (r4 > r11 ? 1 : (r4 == r11 ? 0 : -1))
            if (r5 == 0) goto L_0x0107
            int r5 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r5 != 0) goto L_0x0115
        L_0x0107:
            androidx.constraintlayout.motion.widget.MotionLayout r5 = r0.mMotionLayout
            int r8 = (r4 > r11 ? 1 : (r4 == r11 ? 0 : -1))
            if (r8 != 0) goto L_0x0110
            r8 = r21
            goto L_0x0112
        L_0x0110:
            r8 = r22
        L_0x0112:
            r5.endTrigger(r8)
        L_0x0115:
            androidx.constraintlayout.motion.widget.MotionLayout r5 = r0.mMotionLayout
            r5.setProgress(r10)
            r5 = 1000(0x3e8, float:1.401E-42)
            r1.computeCurrentVelocity(r5)
            float r5 = r1.getXVelocity()
            float r8 = r1.getYVelocity()
            float r9 = r0.mTouchDirectionX
            int r9 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r9 == 0) goto L_0x0134
            float[] r9 = r0.mAnchorDpDt
            r9 = r9[r22]
            float r9 = r5 / r9
            goto L_0x013a
        L_0x0134:
            float[] r9 = r0.mAnchorDpDt
            r9 = r9[r21]
            float r9 = r8 / r9
        L_0x013a:
            androidx.constraintlayout.motion.widget.MotionLayout r11 = r0.mMotionLayout
            r11.mLastVelocity = r9
            goto L_0x0144
        L_0x0140:
            androidx.constraintlayout.motion.widget.MotionLayout r5 = r0.mMotionLayout
            r5.mLastVelocity = r11
        L_0x0144:
            float r5 = r2.getRawX()
            r0.mLastTouchX = r5
            float r5 = r2.getRawY()
            r0.mLastTouchY = r5
            goto L_0x0248
        L_0x0152:
            r21 = r9
            r22 = 0
            r3 = r22
            r0.mDragStarted = r3
            r3 = 1000(0x3e8, float:1.401E-42)
            r1.computeCurrentVelocity(r3)
            float r3 = r1.getXVelocity()
            float r7 = r1.getYVelocity()
            androidx.constraintlayout.motion.widget.MotionLayout r9 = r0.mMotionLayout
            float r9 = r9.getProgress()
            r14 = r9
            int r10 = r0.mTouchAnchorId
            if (r10 == r6) goto L_0x0186
            androidx.constraintlayout.motion.widget.MotionLayout r12 = r0.mMotionLayout
            int r13 = r0.mTouchAnchorId
            float r15 = r0.mTouchAnchorX
            float r6 = r0.mTouchAnchorY
            float[] r10 = r0.mAnchorDpDt
            r16 = r6
            r17 = r10
            r12.getAnchorDpDt(r13, r14, r15, r16, r17)
            r22 = 0
            goto L_0x01a7
        L_0x0186:
            androidx.constraintlayout.motion.widget.MotionLayout r6 = r0.mMotionLayout
            int r6 = r6.getWidth()
            androidx.constraintlayout.motion.widget.MotionLayout r10 = r0.mMotionLayout
            int r10 = r10.getHeight()
            int r6 = java.lang.Math.min(r6, r10)
            float r6 = (float) r6
            float[] r10 = r0.mAnchorDpDt
            float r12 = r0.mTouchDirectionY
            float r12 = r12 * r6
            r10[r21] = r12
            float[] r10 = r0.mAnchorDpDt
            float r12 = r0.mTouchDirectionX
            float r12 = r12 * r6
            r22 = 0
            r10[r22] = r12
        L_0x01a7:
            float r6 = r0.mTouchDirectionX
            float[] r10 = r0.mAnchorDpDt
            r10 = r10[r22]
            float r6 = r6 * r10
            float r10 = r0.mTouchDirectionY
            float[] r12 = r0.mAnchorDpDt
            r12 = r12[r21]
            float r10 = r10 * r12
            float r6 = r6 + r10
            float r10 = r0.mTouchDirectionX
            int r10 = (r10 > r11 ? 1 : (r10 == r11 ? 0 : -1))
            if (r10 == 0) goto L_0x01c5
            float[] r10 = r0.mAnchorDpDt
            r22 = 0
            r10 = r10[r22]
            float r10 = r3 / r10
            goto L_0x01cb
        L_0x01c5:
            float[] r10 = r0.mAnchorDpDt
            r10 = r10[r21]
            float r10 = r7 / r10
        L_0x01cb:
            boolean r12 = java.lang.Float.isNaN(r10)
            if (r12 != 0) goto L_0x01d6
            r12 = 1077936128(0x40400000, float:3.0)
            float r12 = r10 / r12
            float r14 = r14 + r12
        L_0x01d6:
            int r12 = (r14 > r11 ? 1 : (r14 == r11 ? 0 : -1))
            if (r12 == 0) goto L_0x0226
            int r12 = (r14 > r8 ? 1 : (r14 == r8 ? 0 : -1))
            if (r12 == 0) goto L_0x0226
            int r12 = r0.mOnTouchUp
            r13 = 3
            if (r12 == r13) goto L_0x0226
            double r12 = (double) r14
            r15 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            int r12 = (r12 > r15 ? 1 : (r12 == r15 ? 0 : -1))
            if (r12 >= 0) goto L_0x01ec
            r12 = r11
            goto L_0x01ed
        L_0x01ec:
            r12 = r8
        L_0x01ed:
            int r13 = r0.mOnTouchUp
            if (r13 != r5) goto L_0x01fe
            float r5 = r9 + r10
            int r5 = (r5 > r11 ? 1 : (r5 == r11 ? 0 : -1))
            if (r5 >= 0) goto L_0x01fc
            float r5 = java.lang.Math.abs(r10)
            r10 = r5
        L_0x01fc:
            r12 = 1065353216(0x3f800000, float:1.0)
        L_0x01fe:
            int r5 = r0.mOnTouchUp
            if (r5 != r4) goto L_0x020f
            float r4 = r9 + r10
            int r4 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r4 <= 0) goto L_0x020e
            float r4 = java.lang.Math.abs(r10)
            float r4 = -r4
            r10 = r4
        L_0x020e:
            r12 = 0
        L_0x020f:
            androidx.constraintlayout.motion.widget.MotionLayout r4 = r0.mMotionLayout
            int r5 = r0.mOnTouchUp
            r4.touchAnimateTo(r5, r12, r10)
            int r4 = (r11 > r9 ? 1 : (r11 == r9 ? 0 : -1))
            if (r4 >= 0) goto L_0x021e
            int r4 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r4 > 0) goto L_0x022f
        L_0x021e:
            androidx.constraintlayout.motion.widget.MotionLayout r4 = r0.mMotionLayout
            androidx.constraintlayout.motion.widget.MotionLayout$TransitionState r5 = androidx.constraintlayout.motion.widget.MotionLayout.TransitionState.FINISHED
            r4.setState(r5)
            goto L_0x022f
        L_0x0226:
            int r4 = (r11 > r14 ? 1 : (r11 == r14 ? 0 : -1))
            if (r4 >= 0) goto L_0x0230
            int r4 = (r8 > r14 ? 1 : (r8 == r14 ? 0 : -1))
            if (r4 > 0) goto L_0x022f
            goto L_0x0230
        L_0x022f:
            goto L_0x0248
        L_0x0230:
            androidx.constraintlayout.motion.widget.MotionLayout r4 = r0.mMotionLayout
            androidx.constraintlayout.motion.widget.MotionLayout$TransitionState r5 = androidx.constraintlayout.motion.widget.MotionLayout.TransitionState.FINISHED
            r4.setState(r5)
            goto L_0x0248
        L_0x0238:
            float r3 = r2.getRawX()
            r0.mLastTouchX = r3
            float r3 = r2.getRawY()
            r0.mLastTouchY = r3
            r3 = 0
            r0.mDragStarted = r3
        L_0x0248:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.TouchResponse.processTouchEvent(android.view.MotionEvent, androidx.constraintlayout.motion.widget.MotionLayout$MotionTracker, int, androidx.constraintlayout.motion.widget.MotionScene):void");
    }

    /* access modifiers changed from: package-private */
    public void setDown(float lastTouchX, float lastTouchY) {
        this.mLastTouchX = lastTouchX;
        this.mLastTouchY = lastTouchY;
    }

    /* access modifiers changed from: package-private */
    public float getProgressDirection(float dx, float dy) {
        this.mMotionLayout.getAnchorDpDt(this.mTouchAnchorId, this.mMotionLayout.getProgress(), this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
        if (this.mTouchDirectionX != 0.0f) {
            if (this.mAnchorDpDt[0] == 0.0f) {
                this.mAnchorDpDt[0] = 1.0E-7f;
            }
            return (this.mTouchDirectionX * dx) / this.mAnchorDpDt[0];
        }
        if (this.mAnchorDpDt[1] == 0.0f) {
            this.mAnchorDpDt[1] = 1.0E-7f;
        }
        return (this.mTouchDirectionY * dy) / this.mAnchorDpDt[1];
    }

    /* access modifiers changed from: package-private */
    public void scrollUp(float dx, float dy) {
        float velocity;
        this.mDragStarted = false;
        float pos = this.mMotionLayout.getProgress();
        this.mMotionLayout.getAnchorDpDt(this.mTouchAnchorId, pos, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
        float f = (this.mTouchDirectionX * this.mAnchorDpDt[0]) + (this.mTouchDirectionY * this.mAnchorDpDt[1]);
        float f2 = 0.0f;
        if (this.mTouchDirectionX != 0.0f) {
            velocity = (this.mTouchDirectionX * dx) / this.mAnchorDpDt[0];
        } else {
            velocity = (this.mTouchDirectionY * dy) / this.mAnchorDpDt[1];
        }
        if (!Float.isNaN(velocity)) {
            pos += velocity / 3.0f;
        }
        if (pos != 0.0f && pos != 1.0f && this.mOnTouchUp != 3) {
            MotionLayout motionLayout = this.mMotionLayout;
            int i = this.mOnTouchUp;
            if (((double) pos) >= 0.5d) {
                f2 = 1.0f;
            }
            motionLayout.touchAnimateTo(i, f2, velocity);
        }
    }

    /* access modifiers changed from: package-private */
    public void scrollMove(float dx, float dy) {
        float change;
        float f = (this.mTouchDirectionX * dx) + (this.mTouchDirectionY * dy);
        float pos = this.mMotionLayout.getProgress();
        if (!this.mDragStarted) {
            this.mDragStarted = true;
            this.mMotionLayout.setProgress(pos);
        }
        this.mMotionLayout.getAnchorDpDt(this.mTouchAnchorId, pos, this.mTouchAnchorX, this.mTouchAnchorY, this.mAnchorDpDt);
        if (((double) Math.abs((this.mTouchDirectionX * this.mAnchorDpDt[0]) + (this.mTouchDirectionY * this.mAnchorDpDt[1]))) < 0.01d) {
            this.mAnchorDpDt[0] = 0.01f;
            this.mAnchorDpDt[1] = 0.01f;
        }
        if (this.mTouchDirectionX != 0.0f) {
            change = (this.mTouchDirectionX * dx) / this.mAnchorDpDt[0];
        } else {
            change = (this.mTouchDirectionY * dy) / this.mAnchorDpDt[1];
        }
        float pos2 = Math.max(Math.min(pos + change, 1.0f), 0.0f);
        if (pos2 != this.mMotionLayout.getProgress()) {
            this.mMotionLayout.setProgress(pos2);
        }
    }

    /* access modifiers changed from: package-private */
    public void setupTouch() {
        View view = null;
        if (this.mTouchAnchorId != -1 && (view = this.mMotionLayout.findViewById(this.mTouchAnchorId)) == null) {
            Log.e(TAG, "cannot find TouchAnchorId @id/" + Debug.getName(this.mMotionLayout.getContext(), this.mTouchAnchorId));
        }
        if (view instanceof NestedScrollView) {
            NestedScrollView sv = (NestedScrollView) view;
            sv.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;
                }
            });
            sv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                }
            });
        }
    }

    public void setAnchorId(int id) {
        this.mTouchAnchorId = id;
    }

    public int getAnchorId() {
        return this.mTouchAnchorId;
    }

    public void setTouchAnchorLocation(float x, float y) {
        this.mTouchAnchorX = x;
        this.mTouchAnchorY = y;
    }

    public void setMaxVelocity(float velocity) {
        this.mMaxVelocity = velocity;
    }

    public void setMaxAcceleration(float acceleration) {
        this.mMaxAcceleration = acceleration;
    }

    /* access modifiers changed from: package-private */
    public float getMaxAcceleration() {
        return this.mMaxAcceleration;
    }

    public float getMaxVelocity() {
        return this.mMaxVelocity;
    }

    /* access modifiers changed from: package-private */
    public boolean getMoveWhenScrollAtTop() {
        return this.mMoveWhenScrollAtTop;
    }

    public int getAutoCompleteMode() {
        return this.mAutoCompleteMode;
    }

    /* access modifiers changed from: package-private */
    public void setAutoCompleteMode(int autoCompleteMode) {
        this.mAutoCompleteMode = autoCompleteMode;
    }

    /* access modifiers changed from: package-private */
    public RectF getTouchRegion(ViewGroup layout, RectF rect) {
        View view;
        if (this.mTouchRegionId == -1 || (view = layout.findViewById(this.mTouchRegionId)) == null) {
            return null;
        }
        rect.set((float) view.getLeft(), (float) view.getTop(), (float) view.getRight(), (float) view.getBottom());
        return rect;
    }

    /* access modifiers changed from: package-private */
    public int getTouchRegionId() {
        return this.mTouchRegionId;
    }

    /* access modifiers changed from: package-private */
    public RectF getLimitBoundsTo(ViewGroup layout, RectF rect) {
        View view;
        if (this.mLimitBoundsTo == -1 || (view = layout.findViewById(this.mLimitBoundsTo)) == null) {
            return null;
        }
        rect.set((float) view.getLeft(), (float) view.getTop(), (float) view.getRight(), (float) view.getBottom());
        return rect;
    }

    /* access modifiers changed from: package-private */
    public int getLimitBoundsToId() {
        return this.mLimitBoundsTo;
    }

    /* access modifiers changed from: package-private */
    public float dot(float dx, float dy) {
        return (this.mTouchDirectionX * dx) + (this.mTouchDirectionY * dy);
    }

    public String toString() {
        if (Float.isNaN(this.mTouchDirectionX)) {
            return Key.ROTATION;
        }
        return this.mTouchDirectionX + " , " + this.mTouchDirectionY;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public void setTouchUpMode(int touchUpMode) {
        this.mOnTouchUp = touchUpMode;
    }

    public float getSpringStiffness() {
        return this.mSpringStiffness;
    }

    public float getSpringMass() {
        return this.mSpringMass;
    }

    public float getSpringDamping() {
        return this.mSpringDamping;
    }

    public float getSpringStopThreshold() {
        return this.mSpringStopThreshold;
    }

    public int getSpringBoundary() {
        return this.mSpringBoundary;
    }

    /* access modifiers changed from: package-private */
    public boolean isDragStarted() {
        return this.mDragStarted;
    }
}
