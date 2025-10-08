package com.google.android.material.timepicker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.math.MathUtils;
import com.google.android.material.motion.MotionUtils;
import java.util.ArrayList;
import java.util.List;

class ClockHandView extends View {
    private static final int DEFAULT_ANIMATION_DURATION = 200;
    private boolean animatingOnTouchUp;
    private final int animationDuration;
    private final TimeInterpolator animationInterpolator;
    private final float centerDotRadius;
    private boolean changedDuringTouch;
    private int circleRadius;
    private int currentLevel;
    private double degRad;
    private float downX;
    private float downY;
    private boolean isInTapRegion;
    private boolean isMultiLevel;
    private final List<OnRotateListener> listeners;
    private OnActionUpListener onActionUpListener;
    private float originalDeg;
    private final Paint paint;
    private final ValueAnimator rotationAnimator;
    private final int scaledTouchSlop;
    private final RectF selectorBox;
    private final int selectorRadius;
    private final int selectorStrokeWidth;

    public interface OnActionUpListener {
        void onActionUp(float f, boolean z);
    }

    public interface OnRotateListener {
        void onRotate(float f, boolean z);
    }

    public ClockHandView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ClockHandView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.materialClockStyle);
    }

    public ClockHandView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.rotationAnimator = new ValueAnimator();
        this.listeners = new ArrayList();
        this.paint = new Paint();
        this.selectorBox = new RectF();
        this.currentLevel = 1;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ClockHandView, defStyleAttr, R.style.Widget_MaterialComponents_TimePicker_Clock);
        this.animationDuration = MotionUtils.resolveThemeDuration(context, R.attr.motionDurationLong2, 200);
        this.animationInterpolator = MotionUtils.resolveThemeInterpolator(context, R.attr.motionEasingEmphasizedInterpolator, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        this.circleRadius = a.getDimensionPixelSize(R.styleable.ClockHandView_materialCircleRadius, 0);
        this.selectorRadius = a.getDimensionPixelSize(R.styleable.ClockHandView_selectorSize, 0);
        Resources res = getResources();
        this.selectorStrokeWidth = res.getDimensionPixelSize(R.dimen.material_clock_hand_stroke_width);
        this.centerDotRadius = (float) res.getDimensionPixelSize(R.dimen.material_clock_hand_center_dot_radius);
        int selectorColor = a.getColor(R.styleable.ClockHandView_clockHandColor, 0);
        this.paint.setAntiAlias(true);
        this.paint.setColor(selectorColor);
        setHandRotation(0.0f);
        this.scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setImportantForAccessibility(2);
        a.recycle();
        initRotationAnimator();
    }

    private void initRotationAnimator() {
        this.rotationAnimator.addUpdateListener(new ClockHandView$$ExternalSyntheticLambda0(this));
        this.rotationAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationCancel(Animator animation) {
                animation.end();
            }
        });
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$initRotationAnimator$0$com-google-android-material-timepicker-ClockHandView  reason: not valid java name */
    public /* synthetic */ void m1737lambda$initRotationAnimator$0$comgoogleandroidmaterialtimepickerClockHandView(ValueAnimator animation) {
        setHandRotationInternal(((Float) animation.getAnimatedValue()).floatValue(), true);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int i = bottom;
        int bottom2 = right;
        int right2 = top;
        int top2 = left;
        int left2 = changed;
        if (!this.rotationAnimator.isRunning()) {
            setHandRotation(getHandRotation());
        }
    }

    public void setHandRotation(float degrees) {
        setHandRotation(degrees, false);
    }

    public void setHandRotation(float degrees, boolean animate) {
        this.rotationAnimator.cancel();
        if (!animate) {
            setHandRotationInternal(degrees, false);
            return;
        }
        Pair<Float, Float> animationValues = getValuesForAnimation(degrees);
        this.rotationAnimator.setFloatValues(new float[]{((Float) animationValues.first).floatValue(), ((Float) animationValues.second).floatValue()});
        this.rotationAnimator.setDuration((long) this.animationDuration);
        this.rotationAnimator.setInterpolator(this.animationInterpolator);
        this.rotationAnimator.start();
    }

    private Pair<Float, Float> getValuesForAnimation(float degrees) {
        float currentDegrees = getHandRotation();
        if (Math.abs(currentDegrees - degrees) > 180.0f) {
            if (currentDegrees > 180.0f && degrees < 180.0f) {
                degrees += 360.0f;
            }
            if (currentDegrees < 180.0f && degrees > 180.0f) {
                currentDegrees += 360.0f;
            }
        }
        return new Pair<>(Float.valueOf(currentDegrees), Float.valueOf(degrees));
    }

    private void setHandRotationInternal(float degrees, boolean animate) {
        float degrees2 = degrees % 360.0f;
        this.originalDeg = degrees2;
        this.degRad = Math.toRadians((double) (this.originalDeg - 90.0f));
        int leveledCircleRadius = getLeveledCircleRadius(this.currentLevel);
        float selCenterX = ((float) (getWidth() / 2)) + (((float) leveledCircleRadius) * ((float) Math.cos(this.degRad)));
        float selCenterY = ((float) (getHeight() / 2)) + (((float) leveledCircleRadius) * ((float) Math.sin(this.degRad)));
        this.selectorBox.set(selCenterX - ((float) this.selectorRadius), selCenterY - ((float) this.selectorRadius), ((float) this.selectorRadius) + selCenterX, ((float) this.selectorRadius) + selCenterY);
        for (OnRotateListener listener : this.listeners) {
            listener.onRotate(degrees2, animate);
        }
        invalidate();
    }

    public void setAnimateOnTouchUp(boolean animating) {
        this.animatingOnTouchUp = animating;
    }

    public void addOnRotateListener(OnRotateListener listener) {
        this.listeners.add(listener);
    }

    public void setOnActionUpListener(OnActionUpListener listener) {
        this.onActionUpListener = listener;
    }

    public float getHandRotation() {
        return this.originalDeg;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSelector(canvas);
    }

    private void drawSelector(Canvas canvas) {
        Canvas canvas2 = canvas;
        int yCenter = getHeight() / 2;
        int xCenter = getWidth() / 2;
        int leveledCircleRadius = getLeveledCircleRadius(this.currentLevel);
        float selCenterX = ((float) xCenter) + (((float) leveledCircleRadius) * ((float) Math.cos(this.degRad)));
        float selCenterY = ((float) yCenter) + (((float) leveledCircleRadius) * ((float) Math.sin(this.degRad)));
        this.paint.setStrokeWidth(0.0f);
        canvas2.drawCircle(selCenterX, selCenterY, (float) this.selectorRadius, this.paint);
        double sin = Math.sin(this.degRad);
        double cos = Math.cos(this.degRad);
        float lineLength = (float) (leveledCircleRadius - this.selectorRadius);
        float linePointX = (float) (((int) (((double) lineLength) * cos)) + xCenter);
        this.paint.setStrokeWidth((float) this.selectorStrokeWidth);
        float f = lineLength;
        canvas2.drawLine((float) xCenter, (float) yCenter, linePointX, (float) (((int) (((double) lineLength) * sin)) + yCenter), this.paint);
        float f2 = linePointX;
        canvas2.drawCircle((float) xCenter, (float) yCenter, this.centerDotRadius, this.paint);
    }

    public RectF getCurrentSelectorBox() {
        return this.selectorBox;
    }

    public int getSelectorRadius() {
        return this.selectorRadius;
    }

    public void setCircleRadius(int circleRadius2) {
        this.circleRadius = circleRadius2;
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean actionUp;
        boolean actionDown;
        boolean forceSelection;
        int action = event.getActionMasked();
        boolean forceSelection2 = false;
        float x = event.getX();
        float y = event.getY();
        boolean z = false;
        switch (action) {
            case 0:
                this.downX = x;
                this.downY = y;
                this.isInTapRegion = true;
                this.changedDuringTouch = false;
                forceSelection = false;
                actionDown = true;
                actionUp = false;
                break;
            case 1:
            case 2:
                int deltaX = (int) (x - this.downX);
                int deltaY = (int) (y - this.downY);
                this.isInTapRegion = (deltaX * deltaX) + (deltaY * deltaY) > this.scaledTouchSlop;
                if (this.changedDuringTouch) {
                    forceSelection2 = true;
                }
                if (action == 1) {
                    z = true;
                }
                boolean actionUp2 = z;
                if (this.isMultiLevel) {
                    adjustLevel(x, y);
                }
                forceSelection = forceSelection2;
                actionDown = false;
                actionUp = actionUp2;
                break;
            default:
                forceSelection = false;
                actionDown = false;
                actionUp = false;
                break;
        }
        this.changedDuringTouch = this.changedDuringTouch | handleTouchInput(x, y, forceSelection, actionDown, actionUp);
        if (this.changedDuringTouch && actionUp && this.onActionUpListener != null) {
            this.onActionUpListener.onActionUp((float) getDegreesFromXY(x, y), this.isInTapRegion);
        }
        return true;
    }

    private void adjustLevel(float x, float y) {
        int i = 2;
        if (MathUtils.dist((float) (getWidth() / 2), (float) (getHeight() / 2), x, y) > ((float) getLeveledCircleRadius(2)) + ViewUtils.dpToPx(getContext(), 12)) {
            i = 1;
        }
        this.currentLevel = i;
    }

    private boolean handleTouchInput(float x, float y, boolean forceSelection, boolean touchDown, boolean actionUp) {
        int degrees = getDegreesFromXY(x, y);
        boolean z = false;
        boolean valueChanged = getHandRotation() != ((float) degrees);
        if (touchDown && valueChanged) {
            return true;
        }
        if (!valueChanged && !forceSelection) {
            return false;
        }
        float f = (float) degrees;
        if (actionUp && this.animatingOnTouchUp) {
            z = true;
        }
        setHandRotation(f, z);
        return true;
    }

    private int getDegreesFromXY(float x, float y) {
        int degrees = ((int) Math.toDegrees(Math.atan2((double) (y - ((float) (getHeight() / 2))), (double) (x - ((float) (getWidth() / 2)))))) + 90;
        if (degrees < 0) {
            return degrees + 360;
        }
        return degrees;
    }

    /* access modifiers changed from: package-private */
    public int getCurrentLevel() {
        return this.currentLevel;
    }

    /* access modifiers changed from: package-private */
    public void setCurrentLevel(int level) {
        this.currentLevel = level;
        invalidate();
    }

    /* access modifiers changed from: package-private */
    public void setMultiLevel(boolean isMultiLevel2) {
        if (this.isMultiLevel && !isMultiLevel2) {
            this.currentLevel = 1;
        }
        this.isMultiLevel = isMultiLevel2;
        invalidate();
    }

    private int getLeveledCircleRadius(int level) {
        return level == 2 ? Math.round(((float) this.circleRadius) * 0.66f) : this.circleRadius;
    }
}
