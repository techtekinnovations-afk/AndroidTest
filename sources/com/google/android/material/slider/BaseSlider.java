package com.google.android.material.slider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewOverlay;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.SeekBar;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.slider.BaseOnChangeListener;
import com.google.android.material.slider.BaseOnSliderTouchListener;
import com.google.android.material.slider.BaseSlider;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.google.android.material.tooltip.TooltipDrawable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;

abstract class BaseSlider<S extends BaseSlider<S, L, T>, L extends BaseOnChangeListener<S>, T extends BaseOnSliderTouchListener<S>> extends View {
    private static final int DEFAULT_LABEL_ANIMATION_ENTER_DURATION = 83;
    private static final int DEFAULT_LABEL_ANIMATION_EXIT_DURATION = 117;
    static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_Slider;
    private static final String EXCEPTION_ILLEGAL_DISCRETE_VALUE = "Value(%s) must be equal to valueFrom(%s) plus a multiple of stepSize(%s) when using stepSize(%s)";
    private static final String EXCEPTION_ILLEGAL_MIN_SEPARATION = "minSeparation(%s) must be greater or equal to 0";
    private static final String EXCEPTION_ILLEGAL_MIN_SEPARATION_STEP_SIZE = "minSeparation(%s) must be greater or equal and a multiple of stepSize(%s) when using stepSize(%s)";
    private static final String EXCEPTION_ILLEGAL_MIN_SEPARATION_STEP_SIZE_UNIT = "minSeparation(%s) cannot be set as a dimension when using stepSize(%s)";
    private static final String EXCEPTION_ILLEGAL_STEP_SIZE = "The stepSize(%s) must be 0, or a factor of the valueFrom(%s)-valueTo(%s) range";
    private static final String EXCEPTION_ILLEGAL_VALUE = "Slider value(%s) must be greater or equal to valueFrom(%s), and lower or equal to valueTo(%s)";
    private static final String EXCEPTION_ILLEGAL_VALUE_FROM = "valueFrom(%s) must be smaller than valueTo(%s)";
    private static final int HALO_ALPHA = 63;
    private static final int LABEL_ANIMATION_ENTER_DURATION_ATTR = R.attr.motionDurationMedium4;
    private static final int LABEL_ANIMATION_ENTER_EASING_ATTR = R.attr.motionEasingEmphasizedInterpolator;
    private static final int LABEL_ANIMATION_EXIT_DURATION_ATTR = R.attr.motionDurationShort3;
    private static final int LABEL_ANIMATION_EXIT_EASING_ATTR = R.attr.motionEasingEmphasizedAccelerateInterpolator;
    private static final float LEFT_LABEL_PIVOT_X = 1.2f;
    private static final float LEFT_LABEL_PIVOT_Y = 0.5f;
    private static final int MAX_TIMEOUT_TOOLTIP_WITH_ACCESSIBILITY = 120000;
    private static final int MIN_TIMEOUT_TOOLTIP_WITH_ACCESSIBILITY = 10000;
    private static final float RIGHT_LABEL_PIVOT_X = -0.2f;
    private static final float RIGHT_LABEL_PIVOT_Y = 0.5f;
    /* access modifiers changed from: private */
    public static final String TAG = BaseSlider.class.getSimpleName();
    private static final double THRESHOLD = 1.0E-4d;
    private static final float THUMB_WIDTH_PRESSED_RATIO = 0.5f;
    private static final int TIMEOUT_SEND_ACCESSIBILITY_EVENT = 200;
    private static final float TOP_LABEL_PIVOT_X = 0.5f;
    private static final float TOP_LABEL_PIVOT_Y = 1.2f;
    private static final float TOUCH_SLOP_RATIO = 0.8f;
    private static final int TRACK_CORNER_SIZE_UNSET = -1;
    static final int UNIT_PX = 0;
    static final int UNIT_VALUE = 1;
    private static final String WARNING_FLOATING_POINT_ERROR = "Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.";
    private static final String WARNING_PARSE_ERROR = "Error parsing value(%s), valueFrom(%s), and valueTo(%s) into a float.";
    private BaseSlider<S, L, T>.AccessibilityEventSender accessibilityEventSender;
    /* access modifiers changed from: private */
    public final AccessibilityHelper accessibilityHelper;
    private final AccessibilityManager accessibilityManager;
    private int activeThumbIdx;
    private final Paint activeTicksPaint;
    private final Paint activeTrackPaint;
    private final RectF activeTrackRect;
    private boolean centered;
    private final List<L> changeListeners;
    private final RectF cornerRect;
    private Drawable customThumbDrawable;
    private List<Drawable> customThumbDrawablesForValues;
    private final MaterialShapeDrawable defaultThumbDrawable;
    private int defaultThumbRadius;
    private int defaultThumbTrackGapSize;
    private int defaultThumbWidth;
    private int defaultTickActiveRadius;
    private int defaultTickInactiveRadius;
    private int defaultTrackThickness;
    private boolean dirtyConfig;
    private int focusedThumbIdx;
    private boolean forceDrawCompatHalo;
    private LabelFormatter formatter;
    private ColorStateList haloColor;
    private final Paint haloPaint;
    private int haloRadius;
    private final Rect iconRect;
    private final RectF iconRectF;
    private final Paint inactiveTicksPaint;
    private final RectF inactiveTrackLeftRect;
    private final Paint inactiveTrackPaint;
    private final RectF inactiveTrackRightRect;
    private boolean isLongPress;
    private int labelBehavior;
    private int labelPadding;
    private final Rect labelRect;
    private int labelStyle;
    /* access modifiers changed from: private */
    public final List<TooltipDrawable> labels;
    private boolean labelsAreAnimatedIn;
    private ValueAnimator labelsInAnimator;
    private ValueAnimator labelsOutAnimator;
    private MotionEvent lastEvent;
    private int minTickSpacing;
    private int minTouchTargetSize;
    private int minTrackSidePadding;
    private int minWidgetThickness;
    private final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
    private final ViewTreeObserver.OnScrollChangedListener onScrollChangedListener;
    private final Runnable resetActiveThumbIndex;
    private final Matrix rotationMatrix;
    private final int scaledTouchSlop;
    private int separationUnit;
    private float stepSize;
    private final Paint stopIndicatorPaint;
    private boolean thisAndAncestorsVisible;
    private int thumbHeight;
    private boolean thumbIsPressed;
    private final Paint thumbPaint;
    private int thumbTrackGapSize;
    private int thumbWidth;
    private int tickActiveRadius;
    private ColorStateList tickColorActive;
    private ColorStateList tickColorInactive;
    private int tickInactiveRadius;
    private int tickVisibilityMode;
    private float[] ticksCoordinates;
    private final int tooltipTimeoutMillis;
    private float touchDownAxis1;
    private float touchDownAxis2;
    private final List<T> touchListeners;
    private float touchPosition;
    private ColorStateList trackColorActive;
    private ColorStateList trackColorInactive;
    private int trackCornerSize;
    private ColorStateList trackIconActiveColor;
    private Drawable trackIconActiveEnd;
    private boolean trackIconActiveEndMutated;
    private Drawable trackIconActiveStart;
    private boolean trackIconActiveStartMutated;
    private ColorStateList trackIconInactiveColor;
    private Drawable trackIconInactiveEnd;
    private boolean trackIconInactiveEndMutated;
    private Drawable trackIconInactiveStart;
    private boolean trackIconInactiveStartMutated;
    private int trackIconPadding;
    private int trackIconSize;
    private int trackInsideCornerSize;
    private final Path trackPath;
    private int trackSidePadding;
    private int trackStopIndicatorSize;
    private int trackThickness;
    private int trackWidth;
    private float valueFrom;
    private float valueTo;
    private ArrayList<Float> values;
    private int widgetOrientation;
    private int widgetThickness;

    private enum FullCornerDirection {
        BOTH,
        LEFT,
        RIGHT,
        NONE
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-google-android-material-slider-BaseSlider  reason: not valid java name */
    public /* synthetic */ void m1720lambda$new$0$comgoogleandroidmaterialsliderBaseSlider() {
        setActiveThumbIndex(-1);
        invalidate();
    }

    public BaseSlider(Context context) {
        this(context, (AttributeSet) null);
    }

    public BaseSlider(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.sliderStyle);
    }

    public BaseSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        this.labels = new ArrayList();
        this.changeListeners = new ArrayList();
        this.touchListeners = new ArrayList();
        this.labelsAreAnimatedIn = false;
        this.defaultThumbWidth = -1;
        this.defaultThumbTrackGapSize = -1;
        this.centered = false;
        this.trackIconActiveStartMutated = false;
        this.trackIconActiveEndMutated = false;
        this.trackIconInactiveStartMutated = false;
        this.trackIconInactiveEndMutated = false;
        this.thumbIsPressed = false;
        this.values = new ArrayList<>();
        this.activeThumbIdx = -1;
        this.focusedThumbIdx = -1;
        this.stepSize = 0.0f;
        this.isLongPress = false;
        this.trackPath = new Path();
        this.activeTrackRect = new RectF();
        this.inactiveTrackLeftRect = new RectF();
        this.inactiveTrackRightRect = new RectF();
        this.cornerRect = new RectF();
        this.labelRect = new Rect();
        this.iconRectF = new RectF();
        this.iconRect = new Rect();
        this.rotationMatrix = new Matrix();
        this.defaultThumbDrawable = new MaterialShapeDrawable();
        this.customThumbDrawablesForValues = Collections.emptyList();
        this.separationUnit = 0;
        this.onScrollChangedListener = new BaseSlider$$ExternalSyntheticLambda1(this);
        this.onGlobalLayoutListener = new BaseSlider$$ExternalSyntheticLambda2(this);
        this.resetActiveThumbIndex = new BaseSlider$$ExternalSyntheticLambda3(this);
        Context context2 = getContext();
        this.thisAndAncestorsVisible = isShown();
        this.inactiveTrackPaint = new Paint();
        this.activeTrackPaint = new Paint();
        this.thumbPaint = new Paint(1);
        this.thumbPaint.setStyle(Paint.Style.FILL);
        this.thumbPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.haloPaint = new Paint(1);
        this.haloPaint.setStyle(Paint.Style.FILL);
        this.inactiveTicksPaint = new Paint();
        this.inactiveTicksPaint.setStyle(Paint.Style.STROKE);
        this.inactiveTicksPaint.setStrokeCap(Paint.Cap.ROUND);
        this.activeTicksPaint = new Paint();
        this.activeTicksPaint.setStyle(Paint.Style.STROKE);
        this.activeTicksPaint.setStrokeCap(Paint.Cap.ROUND);
        this.stopIndicatorPaint = new Paint();
        this.stopIndicatorPaint.setStyle(Paint.Style.FILL);
        this.stopIndicatorPaint.setStrokeCap(Paint.Cap.ROUND);
        loadResources(context2.getResources());
        processAttributes(context2, attrs, defStyleAttr);
        setFocusable(true);
        setClickable(true);
        this.defaultThumbDrawable.setShadowCompatibilityMode(2);
        this.scaledTouchSlop = ViewConfiguration.get(context2).getScaledTouchSlop();
        this.accessibilityHelper = new AccessibilityHelper(this);
        ViewCompat.setAccessibilityDelegate(this, this.accessibilityHelper);
        this.accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        if (Build.VERSION.SDK_INT >= 29) {
            this.tooltipTimeoutMillis = this.accessibilityManager.getRecommendedTimeoutMillis(MIN_TIMEOUT_TOOLTIP_WITH_ACCESSIBILITY, 6);
        } else {
            this.tooltipTimeoutMillis = MAX_TIMEOUT_TOOLTIP_WITH_ACCESSIBILITY;
        }
    }

    private void loadResources(Resources resources) {
        this.minWidgetThickness = resources.getDimensionPixelSize(R.dimen.mtrl_slider_widget_height);
        this.minTrackSidePadding = resources.getDimensionPixelOffset(R.dimen.mtrl_slider_track_side_padding);
        this.trackSidePadding = this.minTrackSidePadding;
        this.defaultThumbRadius = resources.getDimensionPixelSize(R.dimen.mtrl_slider_thumb_radius);
        this.defaultTrackThickness = resources.getDimensionPixelSize(R.dimen.mtrl_slider_track_height);
        this.defaultTickActiveRadius = resources.getDimensionPixelSize(R.dimen.mtrl_slider_tick_radius);
        this.defaultTickInactiveRadius = resources.getDimensionPixelSize(R.dimen.mtrl_slider_tick_radius);
        this.minTickSpacing = resources.getDimensionPixelSize(R.dimen.mtrl_slider_tick_min_spacing);
        this.labelPadding = resources.getDimensionPixelSize(R.dimen.mtrl_slider_label_padding);
        this.trackIconPadding = resources.getDimensionPixelOffset(R.dimen.m3_slider_track_icon_padding);
    }

    private void processAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        ColorStateList colorStateList3;
        int i;
        ColorStateList colorStateList4;
        ColorStateList colorStateList5;
        Context context2 = context;
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context2, attrs, R.styleable.Slider, defStyleAttr, DEF_STYLE_RES, new int[0]);
        setOrientation(a.getInt(R.styleable.Slider_android_orientation, 0));
        this.labelStyle = a.getResourceId(R.styleable.Slider_labelStyle, R.style.Widget_MaterialComponents_Tooltip);
        this.valueFrom = a.getFloat(R.styleable.Slider_android_valueFrom, 0.0f);
        this.valueTo = a.getFloat(R.styleable.Slider_android_valueTo, 1.0f);
        setValues(Float.valueOf(this.valueFrom));
        setCentered(a.getBoolean(R.styleable.Slider_centered, false));
        this.stepSize = a.getFloat(R.styleable.Slider_android_stepSize, 0.0f);
        this.minTouchTargetSize = (int) Math.ceil((double) a.getDimension(R.styleable.Slider_minTouchTargetSize, (float) MaterialAttributes.resolveMinimumAccessibleTouchTarget(context2)));
        boolean hasTrackColor = a.hasValue(R.styleable.Slider_trackColor);
        int trackColorInactiveRes = hasTrackColor ? R.styleable.Slider_trackColor : R.styleable.Slider_trackColorInactive;
        int trackColorActiveRes = hasTrackColor ? R.styleable.Slider_trackColor : R.styleable.Slider_trackColorActive;
        ColorStateList trackColorInactive2 = MaterialResources.getColorStateList(context2, a, trackColorInactiveRes);
        if (trackColorInactive2 != null) {
            colorStateList = trackColorInactive2;
        } else {
            colorStateList = AppCompatResources.getColorStateList(context2, R.color.material_slider_inactive_track_color);
        }
        setTrackInactiveTintList(colorStateList);
        ColorStateList trackColorActive2 = MaterialResources.getColorStateList(context2, a, trackColorActiveRes);
        if (trackColorActive2 != null) {
            colorStateList2 = trackColorActive2;
        } else {
            colorStateList2 = AppCompatResources.getColorStateList(context2, R.color.material_slider_active_track_color);
        }
        setTrackActiveTintList(colorStateList2);
        this.defaultThumbDrawable.setFillColor(MaterialResources.getColorStateList(context2, a, R.styleable.Slider_thumbColor));
        if (a.hasValue(R.styleable.Slider_thumbStrokeColor)) {
            setThumbStrokeColor(MaterialResources.getColorStateList(context2, a, R.styleable.Slider_thumbStrokeColor));
        }
        setThumbStrokeWidth(a.getDimension(R.styleable.Slider_thumbStrokeWidth, 0.0f));
        ColorStateList haloColor2 = MaterialResources.getColorStateList(context2, a, R.styleable.Slider_haloColor);
        if (haloColor2 != null) {
            colorStateList3 = haloColor2;
        } else {
            colorStateList3 = AppCompatResources.getColorStateList(context2, R.color.material_slider_halo_color);
        }
        setHaloTintList(colorStateList3);
        if (a.hasValue(R.styleable.Slider_tickVisibilityMode)) {
            i = a.getInt(R.styleable.Slider_tickVisibilityMode, -1);
        } else {
            i = convertToTickVisibilityMode(a.getBoolean(R.styleable.Slider_tickVisible, true));
        }
        this.tickVisibilityMode = i;
        boolean hasTickColor = a.hasValue(R.styleable.Slider_tickColor);
        int tickColorInactiveRes = hasTickColor ? R.styleable.Slider_tickColor : R.styleable.Slider_tickColorInactive;
        int tickColorActiveRes = hasTickColor ? R.styleable.Slider_tickColor : R.styleable.Slider_tickColorActive;
        int tickColorInactiveRes2 = tickColorInactiveRes;
        ColorStateList tickColorInactive2 = MaterialResources.getColorStateList(context2, a, tickColorInactiveRes2);
        if (tickColorInactive2 != null) {
            colorStateList4 = tickColorInactive2;
        } else {
            colorStateList4 = AppCompatResources.getColorStateList(context2, R.color.material_slider_inactive_tick_marks_color);
        }
        setTickInactiveTintList(colorStateList4);
        ColorStateList tickColorActive2 = MaterialResources.getColorStateList(context2, a, tickColorActiveRes);
        if (tickColorActive2 != null) {
            colorStateList5 = tickColorActive2;
        } else {
            colorStateList5 = AppCompatResources.getColorStateList(context2, R.color.material_slider_active_tick_marks_color);
        }
        setTickActiveTintList(colorStateList5);
        setThumbTrackGapSize(a.getDimensionPixelSize(R.styleable.Slider_thumbTrackGapSize, 0));
        setTrackStopIndicatorSize(a.getDimensionPixelSize(R.styleable.Slider_trackStopIndicatorSize, 0));
        setTrackCornerSize(a.getDimensionPixelSize(R.styleable.Slider_trackCornerSize, -1));
        setTrackInsideCornerSize(a.getDimensionPixelSize(R.styleable.Slider_trackInsideCornerSize, 0));
        setTrackIconActiveStart(MaterialResources.getDrawable(context2, a, R.styleable.Slider_trackIconActiveStart));
        setTrackIconActiveEnd(MaterialResources.getDrawable(context2, a, R.styleable.Slider_trackIconActiveEnd));
        setTrackIconActiveColor(MaterialResources.getColorStateList(context2, a, R.styleable.Slider_trackIconActiveColor));
        setTrackIconInactiveStart(MaterialResources.getDrawable(context2, a, R.styleable.Slider_trackIconInactiveStart));
        setTrackIconInactiveEnd(MaterialResources.getDrawable(context2, a, R.styleable.Slider_trackIconInactiveEnd));
        setTrackIconInactiveColor(MaterialResources.getColorStateList(context2, a, R.styleable.Slider_trackIconInactiveColor));
        setTrackIconSize(a.getDimensionPixelSize(R.styleable.Slider_trackIconSize, 0));
        int radius = a.getDimensionPixelSize(R.styleable.Slider_thumbRadius, 0);
        int thumbWidth2 = a.getDimensionPixelSize(R.styleable.Slider_thumbWidth, radius * 2);
        int i2 = tickColorInactiveRes2;
        int thumbHeight2 = a.getDimensionPixelSize(R.styleable.Slider_thumbHeight, radius * 2);
        setThumbWidth(thumbWidth2);
        setThumbHeight(thumbHeight2);
        int i3 = thumbWidth2;
        setHaloRadius(a.getDimensionPixelSize(R.styleable.Slider_haloRadius, 0));
        setThumbElevation(a.getDimension(R.styleable.Slider_thumbElevation, 0.0f));
        setTrackHeight(a.getDimensionPixelSize(R.styleable.Slider_trackHeight, 0));
        setTickActiveRadius(a.getDimensionPixelSize(R.styleable.Slider_tickRadiusActive, this.trackStopIndicatorSize / 2));
        setTickInactiveRadius(a.getDimensionPixelSize(R.styleable.Slider_tickRadiusInactive, this.trackStopIndicatorSize / 2));
        setLabelBehavior(a.getInt(R.styleable.Slider_labelBehavior, 0));
        if (!a.getBoolean(R.styleable.Slider_android_enabled, true)) {
            setEnabled(false);
        }
        a.recycle();
    }

    private boolean maybeIncreaseTrackSidePadding() {
        int newTrackSidePadding = this.minTrackSidePadding + Math.max(Math.max(Math.max((this.thumbWidth / 2) - this.defaultThumbRadius, 0), Math.max((this.trackThickness - this.defaultTrackThickness) / 2, 0)), Math.max(Math.max(this.tickActiveRadius - this.defaultTickActiveRadius, 0), Math.max(this.tickInactiveRadius - this.defaultTickInactiveRadius, 0)));
        if (this.trackSidePadding == newTrackSidePadding) {
            return false;
        }
        this.trackSidePadding = newTrackSidePadding;
        if (!isLaidOut()) {
            return true;
        }
        updateTrackWidth(isVertical() ? getHeight() : getWidth());
        return true;
    }

    private boolean valueLandsOnTick(float value) {
        return isMultipleOfStepSize(new BigDecimal(Float.toString(value)).subtract(new BigDecimal(Float.toString(this.valueFrom)), MathContext.DECIMAL64).doubleValue());
    }

    private boolean isMultipleOfStepSize(double value) {
        double result = new BigDecimal(Double.toString(value)).divide(new BigDecimal(Float.toString(this.stepSize)), MathContext.DECIMAL64).doubleValue();
        return Math.abs(((double) Math.round(result)) - result) < THRESHOLD;
    }

    private void validateStepSize() {
        if (this.stepSize > 0.0f && !valueLandsOnTick(this.valueTo)) {
            throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_STEP_SIZE, new Object[]{Float.valueOf(this.stepSize), Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)}));
        }
    }

    private void validateValues() {
        if (this.valueFrom < this.valueTo) {
            Iterator<Float> it = this.values.iterator();
            while (it.hasNext()) {
                Float value = it.next();
                if (value.floatValue() < this.valueFrom || value.floatValue() > this.valueTo) {
                    throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_VALUE, new Object[]{value, Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)}));
                } else if (this.stepSize > 0.0f && !valueLandsOnTick(value.floatValue())) {
                    throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_DISCRETE_VALUE, new Object[]{value, Float.valueOf(this.valueFrom), Float.valueOf(this.stepSize), Float.valueOf(this.stepSize)}));
                }
            }
            return;
        }
        throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_VALUE_FROM, new Object[]{Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)}));
    }

    private void validateMinSeparation() {
        float minSeparation = getMinSeparation();
        if (minSeparation < 0.0f) {
            throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_MIN_SEPARATION, new Object[]{Float.valueOf(minSeparation)}));
        } else if (this.stepSize > 0.0f && minSeparation > 0.0f) {
            if (this.separationUnit != 1) {
                throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_MIN_SEPARATION_STEP_SIZE_UNIT, new Object[]{Float.valueOf(minSeparation), Float.valueOf(this.stepSize)}));
            } else if (minSeparation < this.stepSize || !isMultipleOfStepSize((double) minSeparation)) {
                throw new IllegalStateException(String.format(EXCEPTION_ILLEGAL_MIN_SEPARATION_STEP_SIZE, new Object[]{Float.valueOf(minSeparation), Float.valueOf(this.stepSize), Float.valueOf(this.stepSize)}));
            }
        }
    }

    private void warnAboutFloatingPointError() {
        if (this.stepSize != 0.0f) {
            if (((float) ((int) this.stepSize)) != this.stepSize) {
                Log.w(TAG, String.format(WARNING_FLOATING_POINT_ERROR, new Object[]{"stepSize", Float.valueOf(this.stepSize)}));
            }
            if (((float) ((int) this.valueFrom)) != this.valueFrom) {
                Log.w(TAG, String.format(WARNING_FLOATING_POINT_ERROR, new Object[]{"valueFrom", Float.valueOf(this.valueFrom)}));
            }
            if (((float) ((int) this.valueTo)) != this.valueTo) {
                Log.w(TAG, String.format(WARNING_FLOATING_POINT_ERROR, new Object[]{"valueTo", Float.valueOf(this.valueTo)}));
            }
        }
    }

    private void validateConfigurationIfDirty() {
        if (this.dirtyConfig) {
            validateValues();
            validateStepSize();
            validateMinSeparation();
            warnAboutFloatingPointError();
            this.dirtyConfig = false;
        }
    }

    public void scheduleTooltipTimeout() {
        removeCallbacks(this.resetActiveThumbIndex);
        postDelayed(this.resetActiveThumbIndex, (long) this.tooltipTimeoutMillis);
    }

    public float getValueFrom() {
        return this.valueFrom;
    }

    public void setValueFrom(float valueFrom2) {
        this.valueFrom = valueFrom2;
        this.dirtyConfig = true;
        postInvalidate();
    }

    public float getValueTo() {
        return this.valueTo;
    }

    public void setValueTo(float valueTo2) {
        this.valueTo = valueTo2;
        this.dirtyConfig = true;
        postInvalidate();
    }

    /* access modifiers changed from: package-private */
    public List<Float> getValues() {
        return new ArrayList(this.values);
    }

    /* access modifiers changed from: package-private */
    public void setValues(Float... values2) {
        ArrayList<Float> list = new ArrayList<>();
        Collections.addAll(list, values2);
        setValuesInternal(list);
    }

    /* access modifiers changed from: package-private */
    public void setValues(List<Float> values2) {
        setValuesInternal(new ArrayList(values2));
    }

    private void setValuesInternal(ArrayList<Float> values2) {
        if (!values2.isEmpty()) {
            Collections.sort(values2);
            if (this.values.size() != values2.size() || !this.values.equals(values2)) {
                this.values = values2;
                this.dirtyConfig = true;
                this.focusedThumbIdx = 0;
                updateHaloHotspot();
                createLabelPool();
                dispatchOnChangedProgrammatically();
                postInvalidate();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("At least one value must be set");
    }

    private void createLabelPool() {
        int strokeWidth;
        if (this.labels.size() > this.values.size()) {
            List<TooltipDrawable> tooltipDrawables = this.labels.subList(this.values.size(), this.labels.size());
            for (TooltipDrawable label : tooltipDrawables) {
                if (isAttachedToWindow()) {
                    detachLabelFromContentView(label);
                }
            }
            tooltipDrawables.clear();
        }
        while (true) {
            strokeWidth = 0;
            if (this.labels.size() >= this.values.size()) {
                break;
            }
            TooltipDrawable tooltipDrawable = TooltipDrawable.createFromAttributes(getContext(), (AttributeSet) null, 0, this.labelStyle);
            this.labels.add(tooltipDrawable);
            if (isAttachedToWindow()) {
                attachLabelToContentView(tooltipDrawable);
            }
        }
        if (this.labels.size() != 1) {
            strokeWidth = 1;
        }
        for (TooltipDrawable label2 : this.labels) {
            label2.setStrokeWidth((float) strokeWidth);
        }
    }

    public float getStepSize() {
        return this.stepSize;
    }

    public void setStepSize(float stepSize2) {
        if (stepSize2 < 0.0f) {
            throw new IllegalArgumentException(String.format(EXCEPTION_ILLEGAL_STEP_SIZE, new Object[]{Float.valueOf(stepSize2), Float.valueOf(this.valueFrom), Float.valueOf(this.valueTo)}));
        } else if (this.stepSize != stepSize2) {
            this.stepSize = stepSize2;
            this.dirtyConfig = true;
            postInvalidate();
        }
    }

    /* access modifiers changed from: package-private */
    public void setCustomThumbDrawable(int drawableResId) {
        setCustomThumbDrawable(getResources().getDrawable(drawableResId));
    }

    /* access modifiers changed from: package-private */
    public void setCustomThumbDrawable(Drawable drawable) {
        this.customThumbDrawable = initializeCustomThumbDrawable(drawable);
        this.customThumbDrawablesForValues.clear();
        postInvalidate();
    }

    /* access modifiers changed from: package-private */
    public void setCustomThumbDrawablesForValues(int... customThumbDrawableResIds) {
        Drawable[] customThumbDrawables = new Drawable[customThumbDrawableResIds.length];
        for (int i = 0; i < customThumbDrawableResIds.length; i++) {
            customThumbDrawables[i] = getResources().getDrawable(customThumbDrawableResIds[i]);
        }
        setCustomThumbDrawablesForValues(customThumbDrawables);
    }

    /* access modifiers changed from: package-private */
    public void setCustomThumbDrawablesForValues(Drawable... customThumbDrawables) {
        this.customThumbDrawable = null;
        this.customThumbDrawablesForValues = new ArrayList();
        for (Drawable originalDrawable : customThumbDrawables) {
            this.customThumbDrawablesForValues.add(initializeCustomThumbDrawable(originalDrawable));
        }
        postInvalidate();
    }

    private Drawable initializeCustomThumbDrawable(Drawable originalDrawable) {
        Drawable drawable = originalDrawable.mutate().getConstantState().newDrawable();
        adjustCustomThumbDrawableBounds(drawable);
        return drawable;
    }

    private void adjustCustomThumbDrawableBounds(Drawable drawable) {
        int originalWidth = drawable.getIntrinsicWidth();
        int originalHeight = drawable.getIntrinsicHeight();
        if (originalWidth == -1 && originalHeight == -1) {
            drawable.setBounds(0, 0, this.thumbWidth, this.thumbHeight);
            return;
        }
        float scaleRatio = ((float) Math.max(this.thumbWidth, this.thumbHeight)) / ((float) Math.max(originalWidth, originalHeight));
        drawable.setBounds(0, 0, (int) (((float) originalWidth) * scaleRatio), (int) (((float) originalHeight) * scaleRatio));
    }

    public int getFocusedThumbIndex() {
        return this.focusedThumbIdx;
    }

    public void setFocusedThumbIndex(int index) {
        if (index < 0 || index >= this.values.size()) {
            throw new IllegalArgumentException("index out of range");
        }
        this.focusedThumbIdx = index;
        this.accessibilityHelper.requestKeyboardFocusForVirtualView(this.focusedThumbIdx);
        postInvalidate();
    }

    /* access modifiers changed from: protected */
    public void setActiveThumbIndex(int index) {
        this.activeThumbIdx = index;
    }

    public int getActiveThumbIndex() {
        return this.activeThumbIdx;
    }

    public void addOnChangeListener(L listener) {
        this.changeListeners.add(listener);
    }

    public void removeOnChangeListener(L listener) {
        this.changeListeners.remove(listener);
    }

    public void clearOnChangeListeners() {
        this.changeListeners.clear();
    }

    public void addOnSliderTouchListener(T listener) {
        this.touchListeners.add(listener);
    }

    public void removeOnSliderTouchListener(T listener) {
        this.touchListeners.remove(listener);
    }

    public void clearOnSliderTouchListeners() {
        this.touchListeners.clear();
    }

    public boolean hasLabelFormatter() {
        return this.formatter != null;
    }

    public void setLabelFormatter(LabelFormatter formatter2) {
        this.formatter = formatter2;
    }

    public float getThumbElevation() {
        return this.defaultThumbDrawable.getElevation();
    }

    public void setThumbElevation(float elevation) {
        this.defaultThumbDrawable.setElevation(elevation);
    }

    public void setThumbElevationResource(int elevation) {
        setThumbElevation(getResources().getDimension(elevation));
    }

    public int getThumbRadius() {
        return this.thumbWidth / 2;
    }

    public void setThumbRadius(int radius) {
        setThumbWidth(radius * 2);
        setThumbHeight(radius * 2);
    }

    public void setThumbRadiusResource(int radius) {
        setThumbRadius(getResources().getDimensionPixelSize(radius));
    }

    public int getThumbWidth() {
        return this.thumbWidth;
    }

    public void setThumbWidth(int width) {
        if (width != this.thumbWidth) {
            this.thumbWidth = width;
            this.defaultThumbDrawable.setShapeAppearanceModel(ShapeAppearanceModel.builder().setAllCorners(0, ((float) this.thumbWidth) / 2.0f).build());
            this.defaultThumbDrawable.setBounds(0, 0, this.thumbWidth, this.thumbHeight);
            if (this.customThumbDrawable != null) {
                adjustCustomThumbDrawableBounds(this.customThumbDrawable);
            }
            for (Drawable customDrawable : this.customThumbDrawablesForValues) {
                adjustCustomThumbDrawableBounds(customDrawable);
            }
            updateWidgetLayout(false);
        }
    }

    public void setThumbWidthResource(int width) {
        setThumbWidth(getResources().getDimensionPixelSize(width));
    }

    public int getThumbHeight() {
        return this.thumbHeight;
    }

    public void setThumbHeight(int height) {
        if (height != this.thumbHeight) {
            this.thumbHeight = height;
            this.defaultThumbDrawable.setBounds(0, 0, this.thumbWidth, this.thumbHeight);
            if (this.customThumbDrawable != null) {
                adjustCustomThumbDrawableBounds(this.customThumbDrawable);
            }
            for (Drawable customDrawable : this.customThumbDrawablesForValues) {
                adjustCustomThumbDrawableBounds(customDrawable);
            }
            updateWidgetLayout(false);
        }
    }

    public void setThumbHeightResource(int height) {
        setThumbHeight(getResources().getDimensionPixelSize(height));
    }

    public void setThumbStrokeColor(ColorStateList thumbStrokeColor) {
        this.defaultThumbDrawable.setStrokeColor(thumbStrokeColor);
        postInvalidate();
    }

    public void setThumbStrokeColorResource(int thumbStrokeColorResourceId) {
        if (thumbStrokeColorResourceId != 0) {
            setThumbStrokeColor(AppCompatResources.getColorStateList(getContext(), thumbStrokeColorResourceId));
        }
    }

    public ColorStateList getThumbStrokeColor() {
        return this.defaultThumbDrawable.getStrokeColor();
    }

    public void setThumbStrokeWidth(float thumbStrokeWidth) {
        this.defaultThumbDrawable.setStrokeWidth(thumbStrokeWidth);
        postInvalidate();
    }

    public void setThumbStrokeWidthResource(int thumbStrokeWidthResourceId) {
        if (thumbStrokeWidthResourceId != 0) {
            setThumbStrokeWidth(getResources().getDimension(thumbStrokeWidthResourceId));
        }
    }

    public float getThumbStrokeWidth() {
        return this.defaultThumbDrawable.getStrokeWidth();
    }

    public int getHaloRadius() {
        return this.haloRadius;
    }

    public void setHaloRadius(int radius) {
        if (radius != this.haloRadius) {
            this.haloRadius = radius;
            Drawable background = getBackground();
            if (shouldDrawCompatHalo() || !(background instanceof RippleDrawable)) {
                postInvalidate();
            } else {
                DrawableUtils.setRippleDrawableRadius((RippleDrawable) background, this.haloRadius);
            }
        }
    }

    public void setHaloRadiusResource(int radius) {
        setHaloRadius(getResources().getDimensionPixelSize(radius));
    }

    public int getLabelBehavior() {
        return this.labelBehavior;
    }

    public void setLabelBehavior(int labelBehavior2) {
        if (this.labelBehavior != labelBehavior2) {
            this.labelBehavior = labelBehavior2;
            updateWidgetLayout(true);
        }
    }

    private boolean shouldAlwaysShowLabel() {
        return this.labelBehavior == 3;
    }

    public int getTrackSidePadding() {
        return this.trackSidePadding;
    }

    public int getTrackWidth() {
        return this.trackWidth;
    }

    public int getTrackHeight() {
        return this.trackThickness;
    }

    public void setTrackHeight(int trackHeight) {
        if (this.trackThickness != trackHeight) {
            this.trackThickness = trackHeight;
            invalidateTrack();
            updateWidgetLayout(false);
        }
    }

    public int getTickActiveRadius() {
        return this.tickActiveRadius;
    }

    public void setTickActiveRadius(int tickActiveRadius2) {
        if (this.tickActiveRadius != tickActiveRadius2) {
            this.tickActiveRadius = tickActiveRadius2;
            this.activeTicksPaint.setStrokeWidth((float) (tickActiveRadius2 * 2));
            updateWidgetLayout(false);
        }
    }

    public int getTickInactiveRadius() {
        return this.tickInactiveRadius;
    }

    public void setTickInactiveRadius(int tickInactiveRadius2) {
        if (this.tickInactiveRadius != tickInactiveRadius2) {
            this.tickInactiveRadius = tickInactiveRadius2;
            this.inactiveTicksPaint.setStrokeWidth((float) (tickInactiveRadius2 * 2));
            updateWidgetLayout(false);
        }
    }

    private void updateWidgetLayout(boolean forceRefresh) {
        boolean sizeChanged = maybeIncreaseWidgetThickness();
        boolean sidePaddingChanged = maybeIncreaseTrackSidePadding();
        if (isVertical()) {
            updateRotationMatrix();
        }
        if (sizeChanged || forceRefresh) {
            requestLayout();
        } else if (sidePaddingChanged) {
            postInvalidate();
        }
    }

    private boolean maybeIncreaseWidgetThickness() {
        int paddings;
        if (isVertical()) {
            paddings = getPaddingLeft() + getPaddingRight();
        } else {
            paddings = getPaddingTop() + getPaddingBottom();
        }
        int newWidgetHeight = Math.max(this.minWidgetThickness, Math.max(this.trackThickness + paddings, this.thumbHeight + paddings));
        if (newWidgetHeight == this.widgetThickness) {
            return false;
        }
        this.widgetThickness = newWidgetHeight;
        return true;
    }

    private void updateRotationMatrix() {
        float pivot = (float) calculateTrackCenter();
        this.rotationMatrix.reset();
        this.rotationMatrix.setRotate(90.0f, pivot, pivot);
    }

    public ColorStateList getHaloTintList() {
        return this.haloColor;
    }

    public void setHaloTintList(ColorStateList haloColor2) {
        if (!haloColor2.equals(this.haloColor)) {
            this.haloColor = haloColor2;
            Drawable background = getBackground();
            if (shouldDrawCompatHalo() || !(background instanceof RippleDrawable)) {
                this.haloPaint.setColor(getColorForState(haloColor2));
                this.haloPaint.setAlpha(63);
                invalidate();
                return;
            }
            ((RippleDrawable) background).setColor(haloColor2);
        }
    }

    public ColorStateList getThumbTintList() {
        return this.defaultThumbDrawable.getFillColor();
    }

    public void setThumbTintList(ColorStateList thumbColor) {
        if (!thumbColor.equals(this.defaultThumbDrawable.getFillColor())) {
            this.defaultThumbDrawable.setFillColor(thumbColor);
            invalidate();
        }
    }

    public ColorStateList getTickTintList() {
        if (this.tickColorInactive.equals(this.tickColorActive)) {
            return this.tickColorActive;
        }
        throw new IllegalStateException("The inactive and active ticks are different colors. Use the getTickColorInactive() and getTickColorActive() methods instead.");
    }

    public void setTickTintList(ColorStateList tickColor) {
        setTickInactiveTintList(tickColor);
        setTickActiveTintList(tickColor);
    }

    public ColorStateList getTickActiveTintList() {
        return this.tickColorActive;
    }

    public void setTickActiveTintList(ColorStateList tickColor) {
        if (!tickColor.equals(this.tickColorActive)) {
            this.tickColorActive = tickColor;
            this.activeTicksPaint.setColor(getColorForState(this.tickColorActive));
            invalidate();
        }
    }

    public ColorStateList getTickInactiveTintList() {
        return this.tickColorInactive;
    }

    public void setTickInactiveTintList(ColorStateList tickColor) {
        if (!tickColor.equals(this.tickColorInactive)) {
            this.tickColorInactive = tickColor;
            this.inactiveTicksPaint.setColor(getColorForState(this.tickColorInactive));
            invalidate();
        }
    }

    public boolean isTickVisible() {
        switch (this.tickVisibilityMode) {
            case 0:
                return true;
            case 1:
                if (getDesiredTickCount() <= getMaxTickCount()) {
                    return true;
                }
                return false;
            case 2:
                return false;
            default:
                throw new IllegalStateException("Unexpected tickVisibilityMode: " + this.tickVisibilityMode);
        }
    }

    @Deprecated
    public void setTickVisible(boolean tickVisible) {
        setTickVisibilityMode(convertToTickVisibilityMode(tickVisible));
    }

    private int convertToTickVisibilityMode(boolean tickVisible) {
        return tickVisible ? 0 : 2;
    }

    public int getTickVisibilityMode() {
        return this.tickVisibilityMode;
    }

    public void setTickVisibilityMode(int tickVisibilityMode2) {
        if (this.tickVisibilityMode != tickVisibilityMode2) {
            this.tickVisibilityMode = tickVisibilityMode2;
            postInvalidate();
        }
    }

    public ColorStateList getTrackTintList() {
        if (this.trackColorInactive.equals(this.trackColorActive)) {
            return this.trackColorActive;
        }
        throw new IllegalStateException("The inactive and active parts of the track are different colors. Use the getInactiveTrackColor() and getActiveTrackColor() methods instead.");
    }

    public void setTrackTintList(ColorStateList trackColor) {
        setTrackInactiveTintList(trackColor);
        setTrackActiveTintList(trackColor);
    }

    public ColorStateList getTrackActiveTintList() {
        return this.trackColorActive;
    }

    public void setTrackActiveTintList(ColorStateList trackColor) {
        if (!trackColor.equals(this.trackColorActive)) {
            this.trackColorActive = trackColor;
            this.activeTrackPaint.setColor(getColorForState(this.trackColorActive));
            invalidate();
        }
    }

    public ColorStateList getTrackInactiveTintList() {
        return this.trackColorInactive;
    }

    public void setTrackInactiveTintList(ColorStateList trackColor) {
        if (!trackColor.equals(this.trackColorInactive)) {
            this.trackColorInactive = trackColor;
            this.inactiveTrackPaint.setColor(getColorForState(this.trackColorInactive));
            invalidate();
        }
    }

    public int getThumbTrackGapSize() {
        return this.thumbTrackGapSize;
    }

    public void setThumbTrackGapSize(int thumbTrackGapSize2) {
        if (this.thumbTrackGapSize != thumbTrackGapSize2) {
            this.thumbTrackGapSize = thumbTrackGapSize2;
            invalidate();
        }
    }

    public int getTrackStopIndicatorSize() {
        return this.trackStopIndicatorSize;
    }

    public void setTrackStopIndicatorSize(int trackStopIndicatorSize2) {
        if (this.trackStopIndicatorSize != trackStopIndicatorSize2) {
            this.trackStopIndicatorSize = trackStopIndicatorSize2;
            this.stopIndicatorPaint.setStrokeWidth((float) trackStopIndicatorSize2);
            invalidate();
        }
    }

    public int getTrackCornerSize() {
        if (this.trackCornerSize == -1) {
            return this.trackThickness / 2;
        }
        return this.trackCornerSize;
    }

    public void setTrackCornerSize(int cornerSize) {
        if (this.trackCornerSize != cornerSize) {
            this.trackCornerSize = cornerSize;
            invalidate();
        }
    }

    public int getTrackInsideCornerSize() {
        return this.trackInsideCornerSize;
    }

    public void setTrackInsideCornerSize(int cornerSize) {
        if (this.trackInsideCornerSize != cornerSize) {
            this.trackInsideCornerSize = cornerSize;
            invalidate();
        }
    }

    public void setTrackIconActiveStart(Drawable icon) {
        if (icon != this.trackIconActiveStart) {
            this.trackIconActiveStart = icon;
            this.trackIconActiveStartMutated = false;
            updateTrackIconActiveStart();
            invalidate();
        }
    }

    private void updateTrackIconActiveStart() {
        if (this.trackIconActiveStart != null) {
            if (!this.trackIconActiveStartMutated && this.trackIconActiveColor != null) {
                this.trackIconActiveStart = DrawableCompat.wrap(this.trackIconActiveStart).mutate();
                this.trackIconActiveStartMutated = true;
            }
            if (this.trackIconActiveStartMutated) {
                this.trackIconActiveStart.setTintList(this.trackIconActiveColor);
            }
        }
    }

    public void setTrackIconActiveStart(int iconResourceId) {
        Drawable icon = null;
        if (iconResourceId != 0) {
            icon = AppCompatResources.getDrawable(getContext(), iconResourceId);
        }
        setTrackIconActiveStart(icon);
    }

    public Drawable getTrackIconActiveStart() {
        return this.trackIconActiveStart;
    }

    public void setTrackIconActiveEnd(Drawable icon) {
        if (icon != this.trackIconActiveEnd) {
            this.trackIconActiveEnd = icon;
            this.trackIconActiveEndMutated = false;
            updateTrackIconActiveEnd();
            invalidate();
        }
    }

    private void updateTrackIconActiveEnd() {
        if (this.trackIconActiveEnd != null) {
            if (!this.trackIconActiveEndMutated && this.trackIconActiveColor != null) {
                this.trackIconActiveEnd = DrawableCompat.wrap(this.trackIconActiveEnd).mutate();
                this.trackIconActiveEndMutated = true;
            }
            if (this.trackIconActiveEndMutated) {
                this.trackIconActiveEnd.setTintList(this.trackIconActiveColor);
            }
        }
    }

    public void setTrackIconActiveEnd(int iconResourceId) {
        Drawable icon = null;
        if (iconResourceId != 0) {
            icon = AppCompatResources.getDrawable(getContext(), iconResourceId);
        }
        setTrackIconActiveEnd(icon);
    }

    public Drawable getTrackIconActiveEnd() {
        return this.trackIconActiveEnd;
    }

    public void setTrackIconSize(int size) {
        if (this.trackIconSize != size) {
            this.trackIconSize = size;
            invalidate();
        }
    }

    public int getTrackIconSize() {
        return this.trackIconSize;
    }

    public void setTrackIconActiveColor(ColorStateList color) {
        if (color != this.trackIconActiveColor) {
            this.trackIconActiveColor = color;
            updateTrackIconActiveStart();
            updateTrackIconActiveEnd();
            invalidate();
        }
    }

    public ColorStateList getTrackIconActiveColor() {
        return this.trackIconActiveColor;
    }

    public void setTrackIconInactiveStart(Drawable icon) {
        if (icon != this.trackIconInactiveStart) {
            this.trackIconInactiveStart = icon;
            this.trackIconInactiveStartMutated = false;
            updateTrackIconInactiveStart();
            invalidate();
        }
    }

    private void updateTrackIconInactiveStart() {
        if (this.trackIconInactiveStart != null) {
            if (!this.trackIconInactiveStartMutated && this.trackIconInactiveColor != null) {
                this.trackIconInactiveStart = DrawableCompat.wrap(this.trackIconInactiveStart).mutate();
                this.trackIconInactiveStartMutated = true;
            }
            if (this.trackIconInactiveStartMutated) {
                this.trackIconInactiveStart.setTintList(this.trackIconInactiveColor);
            }
        }
    }

    public void setTrackIconInactiveStart(int iconResourceId) {
        Drawable icon = null;
        if (iconResourceId != 0) {
            icon = AppCompatResources.getDrawable(getContext(), iconResourceId);
        }
        setTrackIconInactiveStart(icon);
    }

    public Drawable getTrackIconInactiveStart() {
        return this.trackIconInactiveStart;
    }

    public void setTrackIconInactiveEnd(Drawable icon) {
        if (icon != this.trackIconInactiveEnd) {
            this.trackIconInactiveEnd = icon;
            this.trackIconInactiveEndMutated = false;
            updateTrackIconInactiveEnd();
            invalidate();
        }
    }

    private void updateTrackIconInactiveEnd() {
        if (this.trackIconInactiveEnd != null) {
            if (!this.trackIconInactiveEndMutated && this.trackIconInactiveColor != null) {
                this.trackIconInactiveEnd = DrawableCompat.wrap(this.trackIconInactiveEnd).mutate();
                this.trackIconInactiveEndMutated = true;
            }
            if (this.trackIconInactiveEndMutated) {
                this.trackIconInactiveEnd.setTintList(this.trackIconInactiveColor);
            }
        }
    }

    public void setTrackIconInactiveEnd(int iconResourceId) {
        Drawable icon = null;
        if (iconResourceId != 0) {
            icon = AppCompatResources.getDrawable(getContext(), iconResourceId);
        }
        setTrackIconInactiveEnd(icon);
    }

    public Drawable getTrackIconInactiveEnd() {
        return this.trackIconInactiveEnd;
    }

    public void setTrackIconInactiveColor(ColorStateList color) {
        if (color != this.trackIconInactiveColor) {
            this.trackIconInactiveColor = color;
            updateTrackIconInactiveStart();
            updateTrackIconInactiveEnd();
            invalidate();
        }
    }

    public ColorStateList getTrackIconInactiveColor() {
        return this.trackIconInactiveColor;
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View changedView, int visibility) {
        ViewOverlay contentViewOverlay;
        super.onVisibilityChanged(changedView, visibility);
        if (visibility != 0 && (contentViewOverlay = getContentViewOverlay()) != null) {
            for (TooltipDrawable label : this.labels) {
                contentViewOverlay.remove(label);
            }
        }
    }

    /* access modifiers changed from: private */
    public ViewOverlay getContentViewOverlay() {
        View contentView = ViewUtils.getContentView(this);
        if (contentView == null) {
            return null;
        }
        return contentView.getOverlay();
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setLayerType(enabled ? 0 : 2, (Paint) null);
    }

    public void setOrientation(int orientation) {
        if (this.widgetOrientation != orientation) {
            this.widgetOrientation = orientation;
            updateWidgetLayout(true);
        }
    }

    public void setCentered(boolean isCentered) {
        if (this.centered != isCentered) {
            this.centered = isCentered;
            if (isCentered) {
                setValues(Float.valueOf((this.valueFrom + this.valueTo) / 2.0f));
            } else {
                setValues(Float.valueOf(this.valueFrom));
            }
            updateWidgetLayout(true);
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.thisAndAncestorsVisible = isShown();
        getViewTreeObserver().addOnScrollChangedListener(this.onScrollChangedListener);
        getViewTreeObserver().addOnGlobalLayoutListener(this.onGlobalLayoutListener);
        for (TooltipDrawable label : this.labels) {
            attachLabelToContentView(label);
        }
    }

    private void attachLabelToContentView(TooltipDrawable label) {
        label.setRelativeToView(ViewUtils.getContentView(this));
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (this.accessibilityEventSender != null) {
            removeCallbacks(this.accessibilityEventSender);
        }
        this.labelsAreAnimatedIn = false;
        for (TooltipDrawable label : this.labels) {
            detachLabelFromContentView(label);
        }
        getViewTreeObserver().removeOnScrollChangedListener(this.onScrollChangedListener);
        getViewTreeObserver().removeOnGlobalLayoutListener(this.onGlobalLayoutListener);
        super.onDetachedFromWindow();
    }

    private void detachLabelFromContentView(TooltipDrawable label) {
        View contentView = ViewUtils.getContentView(this);
        if (contentView != null) {
            contentView.getOverlay().remove(label);
            label.detachView(contentView);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int labelSize = 0;
        if (this.labelBehavior == 1 || shouldAlwaysShowLabel()) {
            labelSize = this.labels.get(0).getIntrinsicHeight();
        }
        int spec = View.MeasureSpec.makeMeasureSpec(this.widgetThickness + labelSize, 1073741824);
        if (isVertical()) {
            super.onMeasure(spec, heightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, spec);
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        updateTrackWidth(isVertical() ? h : w);
        updateHaloHotspot();
    }

    private void updateTicksCoordinates() {
        int tickCount;
        validateConfigurationIfDirty();
        int tickCount2 = 0;
        if (this.stepSize <= 0.0f) {
            updateTicksCoordinates(0);
            return;
        }
        switch (this.tickVisibilityMode) {
            case 0:
                tickCount = Math.min(getDesiredTickCount(), getMaxTickCount());
                break;
            case 1:
                int desiredTickCount = getDesiredTickCount();
                if (desiredTickCount <= getMaxTickCount()) {
                    tickCount2 = desiredTickCount;
                }
                tickCount = tickCount2;
                break;
            case 2:
                tickCount = 0;
                break;
            default:
                throw new IllegalStateException("Unexpected tickVisibilityMode: " + this.tickVisibilityMode);
        }
        updateTicksCoordinates(tickCount);
    }

    private void updateTicksCoordinates(int tickCount) {
        if (tickCount == 0) {
            this.ticksCoordinates = null;
            return;
        }
        if (this.ticksCoordinates == null || this.ticksCoordinates.length != tickCount * 2) {
            this.ticksCoordinates = new float[(tickCount * 2)];
        }
        float interval = ((float) this.trackWidth) / ((float) (tickCount - 1));
        float trackCenterY = (float) calculateTrackCenter();
        for (int i = 0; i < tickCount * 2; i += 2) {
            this.ticksCoordinates[i] = ((float) this.trackSidePadding) + ((((float) i) / 2.0f) * interval);
            this.ticksCoordinates[i + 1] = trackCenterY;
        }
        if (isVertical() != 0) {
            this.rotationMatrix.mapPoints(this.ticksCoordinates);
        }
    }

    private int getDesiredTickCount() {
        return (int) (((this.valueTo - this.valueFrom) / this.stepSize) + 1.0f);
    }

    private int getMaxTickCount() {
        return (this.trackWidth / this.minTickSpacing) + 1;
    }

    private void updateTrackWidth(int width) {
        this.trackWidth = Math.max(width - (this.trackSidePadding * 2), 0);
        updateTicksCoordinates();
    }

    /* access modifiers changed from: private */
    public void updateHaloHotspot() {
        if (!shouldDrawCompatHalo() && getMeasuredWidth() > 0) {
            Drawable background = getBackground();
            if (background instanceof RippleDrawable) {
                float x = (normalizeValue(this.values.get(this.focusedThumbIdx).floatValue()) * ((float) this.trackWidth)) + ((float) this.trackSidePadding);
                int y = calculateTrackCenter();
                float[] haloBounds = {x - ((float) this.haloRadius), (float) (y - this.haloRadius), ((float) this.haloRadius) + x, (float) (this.haloRadius + y)};
                if (isVertical()) {
                    this.rotationMatrix.mapPoints(haloBounds);
                }
                background.setHotspotBounds((int) haloBounds[0], (int) haloBounds[1], (int) haloBounds[2], (int) haloBounds[3]);
            }
        }
    }

    private int calculateTrackCenter() {
        int i = this.widgetThickness / 2;
        int i2 = 0;
        if (this.labelBehavior == 1 || shouldAlwaysShowLabel()) {
            i2 = this.labels.get(0).getIntrinsicHeight();
        }
        return i + i2;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.dirtyConfig) {
            validateConfigurationIfDirty();
            updateTicksCoordinates();
        }
        super.onDraw(canvas);
        int yCenter = calculateTrackCenter();
        drawInactiveTracks(canvas, this.trackWidth, yCenter);
        drawActiveTracks(canvas, this.trackWidth, yCenter);
        if (isRtl() || isVertical()) {
            drawTrackIcons(canvas, this.activeTrackRect, this.inactiveTrackLeftRect);
        } else {
            drawTrackIcons(canvas, this.activeTrackRect, this.inactiveTrackRightRect);
        }
        maybeDrawTicks(canvas);
        maybeDrawStopIndicator(canvas, yCenter);
        if ((this.thumbIsPressed || isFocused()) && isEnabled()) {
            maybeDrawCompatHalo(canvas, this.trackWidth, yCenter);
        }
        updateLabels();
        drawThumbs(canvas, this.trackWidth, yCenter);
    }

    private float[] getActiveRange() {
        float min = this.values.get(0).floatValue();
        float max = this.values.get(this.values.size() - 1).floatValue();
        float left = normalizeValue(this.values.size() == 1 ? this.valueFrom : min);
        float right = normalizeValue(max);
        if (isCentered()) {
            left = Math.min(0.5f, right);
            right = Math.max(0.5f, right);
        }
        if (isCentered() || (!isRtl() && !isVertical())) {
            return new float[]{left, right};
        }
        return new float[]{right, left};
    }

    private void drawInactiveTracks(Canvas canvas, int width, int yCenter) {
        float[] activeRange = getActiveRange();
        float top = ((float) yCenter) - (((float) this.trackThickness) / 2.0f);
        float bottom = ((float) yCenter) + (((float) this.trackThickness) / 2.0f);
        Canvas canvas2 = canvas;
        drawInactiveTrackSection((float) (this.trackSidePadding - getTrackCornerSize()), (((float) this.trackSidePadding) + (activeRange[0] * ((float) width))) - ((float) this.thumbTrackGapSize), top, bottom, canvas2, this.inactiveTrackLeftRect, FullCornerDirection.LEFT);
        drawInactiveTrackSection(((float) this.trackSidePadding) + (activeRange[1] * ((float) width)) + ((float) this.thumbTrackGapSize), (float) (this.trackSidePadding + width + getTrackCornerSize()), top, bottom, canvas2, this.inactiveTrackRightRect, FullCornerDirection.RIGHT);
    }

    private void drawInactiveTrackSection(float from, float to, float top, float bottom, Canvas canvas, RectF rect, FullCornerDirection direction) {
        if (to - from > ((float) (getTrackCornerSize() - this.thumbTrackGapSize))) {
            rect.set(from, top, to, bottom);
        } else {
            rect.setEmpty();
        }
        updateTrack(canvas, this.inactiveTrackPaint, rect, (float) getTrackCornerSize(), direction);
    }

    private float normalizeValue(float value) {
        float normalized = (value - this.valueFrom) / (this.valueTo - this.valueFrom);
        if (isRtl() || isVertical()) {
            return 1.0f - normalized;
        }
        return normalized;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawActiveTracks(android.graphics.Canvas r17, int r18, int r19) {
        /*
            r16 = this;
            r0 = r16
            r6 = r18
            r7 = r19
            float[] r8 = r0.getActiveRange()
            int r1 = r0.trackSidePadding
            float r1 = (float) r1
            r9 = 1
            r2 = r8[r9]
            float r3 = (float) r6
            float r2 = r2 * r3
            float r1 = r1 + r2
            int r2 = r0.trackSidePadding
            float r2 = (float) r2
            r10 = 0
            r3 = r8[r10]
            float r4 = (float) r6
            float r3 = r3 * r4
            float r2 = r2 + r3
            int r3 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1))
            if (r3 < 0) goto L_0x0026
            android.graphics.RectF r3 = r0.activeTrackRect
            r3.setEmpty()
            return
        L_0x0026:
            com.google.android.material.slider.BaseSlider$FullCornerDirection r3 = com.google.android.material.slider.BaseSlider.FullCornerDirection.NONE
            java.util.ArrayList<java.lang.Float> r4 = r0.values
            int r4 = r4.size()
            if (r4 != r9) goto L_0x004b
            boolean r4 = r0.isCentered()
            if (r4 != 0) goto L_0x004b
            boolean r4 = r0.isRtl()
            if (r4 != 0) goto L_0x0046
            boolean r4 = r0.isVertical()
            if (r4 == 0) goto L_0x0043
            goto L_0x0046
        L_0x0043:
            com.google.android.material.slider.BaseSlider$FullCornerDirection r4 = com.google.android.material.slider.BaseSlider.FullCornerDirection.LEFT
            goto L_0x0048
        L_0x0046:
            com.google.android.material.slider.BaseSlider$FullCornerDirection r4 = com.google.android.material.slider.BaseSlider.FullCornerDirection.RIGHT
        L_0x0048:
            r3 = r4
            r5 = r3
            goto L_0x004c
        L_0x004b:
            r5 = r3
        L_0x004c:
            r3 = 0
            r11 = r3
        L_0x004e:
            java.util.ArrayList<java.lang.Float> r3 = r0.values
            int r3 = r3.size()
            if (r11 >= r3) goto L_0x010d
            java.util.ArrayList<java.lang.Float> r3 = r0.values
            int r3 = r3.size()
            if (r3 <= r9) goto L_0x0091
            if (r11 <= 0) goto L_0x0072
            java.util.ArrayList<java.lang.Float> r3 = r0.values
            int r4 = r11 + -1
            java.lang.Object r3 = r3.get(r4)
            java.lang.Float r3 = (java.lang.Float) r3
            float r3 = r3.floatValue()
            float r2 = r0.valueToX(r3)
        L_0x0072:
            java.util.ArrayList<java.lang.Float> r3 = r0.values
            java.lang.Object r3 = r3.get(r11)
            java.lang.Float r3 = (java.lang.Float) r3
            float r3 = r3.floatValue()
            float r1 = r0.valueToX(r3)
            boolean r3 = r0.isRtl()
            if (r3 != 0) goto L_0x008e
            boolean r3 = r0.isVertical()
            if (r3 == 0) goto L_0x0091
        L_0x008e:
            r3 = r2
            r2 = r1
            r1 = r3
        L_0x0091:
            int r12 = r0.getTrackCornerSize()
            int r3 = r5.ordinal()
            switch(r3) {
                case 1: goto L_0x00d3;
                case 2: goto L_0x00ca;
                case 3: goto L_0x009d;
                default: goto L_0x009c;
            }
        L_0x009c:
            goto L_0x00dc
        L_0x009d:
            boolean r3 = r0.isCentered()
            if (r3 != 0) goto L_0x00ae
            int r3 = r0.thumbTrackGapSize
            float r3 = (float) r3
            float r2 = r2 + r3
            int r3 = r0.thumbTrackGapSize
            float r3 = (float) r3
            float r1 = r1 - r3
            r13 = r1
            r14 = r2
            goto L_0x00de
        L_0x00ae:
            r3 = r8[r9]
            r4 = 1056964608(0x3f000000, float:0.5)
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 != 0) goto L_0x00bd
            int r3 = r0.thumbTrackGapSize
            float r3 = (float) r3
            float r2 = r2 + r3
            r13 = r1
            r14 = r2
            goto L_0x00de
        L_0x00bd:
            r3 = r8[r10]
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 != 0) goto L_0x00dc
            int r3 = r0.thumbTrackGapSize
            float r3 = (float) r3
            float r1 = r1 - r3
            r13 = r1
            r14 = r2
            goto L_0x00de
        L_0x00ca:
            int r3 = r0.thumbTrackGapSize
            float r3 = (float) r3
            float r2 = r2 + r3
            float r3 = (float) r12
            float r1 = r1 + r3
            r13 = r1
            r14 = r2
            goto L_0x00de
        L_0x00d3:
            float r3 = (float) r12
            float r2 = r2 - r3
            int r3 = r0.thumbTrackGapSize
            float r3 = (float) r3
            float r1 = r1 - r3
            r13 = r1
            r14 = r2
            goto L_0x00de
        L_0x00dc:
            r13 = r1
            r14 = r2
        L_0x00de:
            int r1 = (r14 > r13 ? 1 : (r14 == r13 ? 0 : -1))
            if (r1 < 0) goto L_0x00e8
            android.graphics.RectF r1 = r0.activeTrackRect
            r1.setEmpty()
            goto L_0x0105
        L_0x00e8:
            android.graphics.RectF r1 = r0.activeTrackRect
            float r2 = (float) r7
            int r3 = r0.trackThickness
            float r3 = (float) r3
            r4 = 1073741824(0x40000000, float:2.0)
            float r3 = r3 / r4
            float r2 = r2 - r3
            float r3 = (float) r7
            int r15 = r0.trackThickness
            float r15 = (float) r15
            float r15 = r15 / r4
            float r3 = r3 + r15
            r1.set(r14, r2, r13, r3)
            android.graphics.Paint r2 = r0.activeTrackPaint
            android.graphics.RectF r3 = r0.activeTrackRect
            float r4 = (float) r12
            r1 = r17
            r0.updateTrack(r1, r2, r3, r4, r5)
        L_0x0105:
            int r11 = r11 + 1
            r0 = r16
            r1 = r13
            r2 = r14
            goto L_0x004e
        L_0x010d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.slider.BaseSlider.drawActiveTracks(android.graphics.Canvas, int, int):void");
    }

    private float calculateStartTrackCornerSize(float trackCornerSize2) {
        if (this.values.isEmpty() || !hasGapBetweenThumbAndTrack()) {
            return trackCornerSize2;
        }
        float currentX = valueToX(this.values.get((isRtl() || isVertical()) ? this.values.size() - 1 : 0).floatValue()) - ((float) this.trackSidePadding);
        if (currentX < trackCornerSize2) {
            return Math.max(currentX, (float) this.trackInsideCornerSize);
        }
        return trackCornerSize2;
    }

    private float calculateEndTrackCornerSize(float trackCornerSize2) {
        if (this.values.isEmpty() || !hasGapBetweenThumbAndTrack()) {
            return trackCornerSize2;
        }
        float currentX = valueToX(this.values.get((isRtl() || isVertical()) ? 0 : this.values.size() - 1).floatValue()) - ((float) this.trackSidePadding);
        if (currentX > ((float) this.trackWidth) - trackCornerSize2) {
            return Math.max(((float) this.trackWidth) - currentX, (float) this.trackInsideCornerSize);
        }
        return trackCornerSize2;
    }

    private void drawTrackIcons(Canvas canvas, RectF activeTrackBounds, RectF inactiveTrackBounds) {
        if (hasTrackIcons()) {
            if (this.values.size() > 1) {
                Log.w(TAG, "Track icons can only be used when only 1 thumb is present.");
            }
            calculateBoundsAndDrawTrackIcon(canvas, activeTrackBounds, this.trackIconActiveStart, true);
            calculateBoundsAndDrawTrackIcon(canvas, inactiveTrackBounds, this.trackIconInactiveStart, true);
            calculateBoundsAndDrawTrackIcon(canvas, activeTrackBounds, this.trackIconActiveEnd, false);
            calculateBoundsAndDrawTrackIcon(canvas, inactiveTrackBounds, this.trackIconInactiveEnd, false);
        }
    }

    private boolean hasTrackIcons() {
        return (this.trackIconActiveStart == null && this.trackIconActiveEnd == null && this.trackIconInactiveStart == null && this.trackIconInactiveEnd == null) ? false : true;
    }

    private void calculateBoundsAndDrawTrackIcon(Canvas canvas, RectF trackBounds, Drawable icon, boolean isStart) {
        if (icon != null) {
            calculateTrackIconBounds(trackBounds, this.iconRectF, this.trackIconSize, this.trackIconPadding, isStart);
            if (!this.iconRectF.isEmpty()) {
                drawTrackIcon(canvas, this.iconRectF, icon);
                return;
            }
            return;
        }
        RectF rectF = trackBounds;
        boolean z = isStart;
    }

    private void drawTrackIcon(Canvas canvas, RectF iconBounds, Drawable icon) {
        if (isVertical()) {
            this.rotationMatrix.mapRect(iconBounds);
        }
        iconBounds.round(this.iconRect);
        icon.setBounds(this.iconRect);
        icon.draw(canvas);
    }

    private void calculateTrackIconBounds(RectF trackBounds, RectF iconBounds, int iconSize, int iconPadding, boolean isStart) {
        float iconLeft;
        if (trackBounds.right - trackBounds.left >= ((float) ((iconPadding * 2) + iconSize))) {
            if ((isRtl() || isVertical()) ^ isStart) {
                iconLeft = trackBounds.left + ((float) iconPadding);
            } else {
                iconLeft = (trackBounds.right - ((float) iconPadding)) - ((float) iconSize);
            }
            float iconTop = ((float) calculateTrackCenter()) - (((float) iconSize) / 2.0f);
            iconBounds.set(iconLeft, iconTop, ((float) iconSize) + iconLeft, ((float) iconSize) + iconTop);
            return;
        }
        iconBounds.setEmpty();
    }

    private boolean hasGapBetweenThumbAndTrack() {
        return this.thumbTrackGapSize > 0;
    }

    private void updateTrack(Canvas canvas, Paint paint, RectF bounds, float cornerSize, FullCornerDirection direction) {
        if (!bounds.isEmpty()) {
            float leftCornerSize = calculateStartTrackCornerSize(cornerSize);
            float rightCornerSize = calculateEndTrackCornerSize(cornerSize);
            switch (direction.ordinal()) {
                case 1:
                    rightCornerSize = (float) this.trackInsideCornerSize;
                    break;
                case 2:
                    leftCornerSize = (float) this.trackInsideCornerSize;
                    break;
                case 3:
                    leftCornerSize = (float) this.trackInsideCornerSize;
                    rightCornerSize = (float) this.trackInsideCornerSize;
                    break;
            }
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeCap(Paint.Cap.BUTT);
            if (hasGapBetweenThumbAndTrack()) {
                paint.setAntiAlias(true);
            }
            RectF rotated = new RectF(bounds);
            if (isVertical()) {
                this.rotationMatrix.mapRect(rotated);
            }
            this.trackPath.reset();
            if (bounds.width() >= leftCornerSize + rightCornerSize) {
                this.trackPath.addRoundRect(rotated, getCornerRadii(leftCornerSize, rightCornerSize), Path.Direction.CW);
                canvas.drawPath(this.trackPath, paint);
                return;
            }
            float minCornerSize = Math.min(leftCornerSize, rightCornerSize);
            float maxCornerSize = Math.max(leftCornerSize, rightCornerSize);
            canvas.save();
            this.trackPath.addRoundRect(rotated, minCornerSize, minCornerSize, Path.Direction.CW);
            canvas.clipPath(this.trackPath);
            switch (direction.ordinal()) {
                case 1:
                    this.cornerRect.set(bounds.left, bounds.top, bounds.left + (2.0f * maxCornerSize), bounds.bottom);
                    break;
                case 2:
                    this.cornerRect.set(bounds.right - (2.0f * maxCornerSize), bounds.top, bounds.right, bounds.bottom);
                    break;
                default:
                    this.cornerRect.set(bounds.centerX() - maxCornerSize, bounds.top, bounds.centerX() + maxCornerSize, bounds.bottom);
                    break;
            }
            if (isVertical()) {
                this.rotationMatrix.mapRect(this.cornerRect);
            }
            canvas.drawRoundRect(this.cornerRect, maxCornerSize, maxCornerSize, paint);
            canvas.restore();
        }
    }

    private float[] getCornerRadii(float leftSide, float rightSide) {
        if (isVertical()) {
            return new float[]{leftSide, leftSide, leftSide, leftSide, rightSide, rightSide, rightSide, rightSide};
        }
        return new float[]{leftSide, leftSide, rightSide, rightSide, rightSide, rightSide, leftSide, leftSide};
    }

    private void maybeDrawTicks(Canvas canvas) {
        if (this.ticksCoordinates != null && this.ticksCoordinates.length != 0) {
            float[] activeRange = getActiveRange();
            int leftActiveTickIndex = (int) Math.ceil((double) (activeRange[0] * ((((float) this.ticksCoordinates.length) / 2.0f) - 1.0f)));
            int rightActiveTickIndex = (int) Math.floor((double) (activeRange[1] * ((((float) this.ticksCoordinates.length) / 2.0f) - 1.0f)));
            if (leftActiveTickIndex > 0) {
                drawTicks(0, leftActiveTickIndex * 2, canvas, this.inactiveTicksPaint);
            }
            if (leftActiveTickIndex <= rightActiveTickIndex) {
                drawTicks(leftActiveTickIndex * 2, (rightActiveTickIndex + 1) * 2, canvas, this.activeTicksPaint);
            }
            if ((rightActiveTickIndex + 1) * 2 < this.ticksCoordinates.length) {
                drawTicks((rightActiveTickIndex + 1) * 2, this.ticksCoordinates.length, canvas, this.inactiveTicksPaint);
            }
        }
    }

    private void drawTicks(int from, int to, Canvas canvas, Paint paint) {
        for (int i = from; i < to; i += 2) {
            float coordinateToCheck = isVertical() ? this.ticksCoordinates[i + 1] : this.ticksCoordinates[i];
            if (!isOverlappingThumb(coordinateToCheck) && (!isCentered() || !isOverlappingCenterGap(coordinateToCheck))) {
                canvas.drawPoint(this.ticksCoordinates[i], this.ticksCoordinates[i + 1], paint);
            }
        }
    }

    private boolean isOverlappingThumb(float tickCoordinate) {
        float threshold = ((float) this.thumbTrackGapSize) + (((float) this.thumbWidth) / 2.0f);
        Iterator<Float> it = this.values.iterator();
        if (!it.hasNext()) {
            return false;
        }
        float valueToX = valueToX(it.next().floatValue());
        if (tickCoordinate < valueToX - threshold || tickCoordinate > valueToX + threshold) {
            return false;
        }
        return true;
    }

    private boolean isOverlappingCenterGap(float tickCoordinate) {
        float threshold = ((float) this.thumbTrackGapSize) + (((float) this.thumbWidth) / 2.0f);
        float trackCenter = ((float) (this.trackWidth + (this.trackSidePadding * 2))) / 2.0f;
        return tickCoordinate >= trackCenter - threshold && tickCoordinate <= trackCenter + threshold;
    }

    private void maybeDrawStopIndicator(Canvas canvas, int yCenter) {
        if (this.trackStopIndicatorSize > 0 && !this.values.isEmpty()) {
            if (this.values.get(this.values.size() - 1).floatValue() < this.valueTo) {
                drawStopIndicator(canvas, valueToX(this.valueTo), (float) yCenter);
            }
            if (isCentered() || (this.values.size() > 1 && this.values.get(0).floatValue() > this.valueFrom)) {
                drawStopIndicator(canvas, valueToX(this.valueFrom), (float) yCenter);
            }
        }
    }

    private void drawStopIndicator(Canvas canvas, float x, float y) {
        Iterator<Float> it = this.values.iterator();
        while (it.hasNext()) {
            float valueToX = valueToX(it.next().floatValue());
            float threshold = ((float) this.thumbTrackGapSize) + (((float) this.thumbWidth) / 2.0f);
            if (x >= valueToX - threshold && x <= valueToX + threshold) {
                return;
            }
        }
        if (isVertical()) {
            canvas.drawPoint(y, x, this.stopIndicatorPaint);
        } else {
            canvas.drawPoint(x, y, this.stopIndicatorPaint);
        }
    }

    private void drawThumbs(Canvas canvas, int width, int yCenter) {
        int yCenter2;
        int width2;
        Canvas canvas2;
        int i = 0;
        while (i < this.values.size()) {
            float value = this.values.get(i).floatValue();
            if (this.customThumbDrawable != null) {
                canvas2 = canvas;
                width2 = width;
                yCenter2 = yCenter;
                drawThumbDrawable(canvas2, width2, yCenter2, value, this.customThumbDrawable);
            } else {
                canvas2 = canvas;
                width2 = width;
                yCenter2 = yCenter;
                if (i < this.customThumbDrawablesForValues.size()) {
                    drawThumbDrawable(canvas2, width2, yCenter2, value, this.customThumbDrawablesForValues.get(i));
                } else {
                    if (!isEnabled()) {
                        canvas2.drawCircle(((float) this.trackSidePadding) + (normalizeValue(value) * ((float) width2)), (float) yCenter2, (float) getThumbRadius(), this.thumbPaint);
                    }
                    drawThumbDrawable(canvas2, width2, yCenter2, value, this.defaultThumbDrawable);
                }
            }
            i++;
            canvas = canvas2;
            width = width2;
            yCenter = yCenter2;
        }
    }

    private void drawThumbDrawable(Canvas canvas, int width, int top, float value, Drawable thumbDrawable) {
        canvas.save();
        if (isVertical()) {
            canvas.concat(this.rotationMatrix);
        }
        canvas.translate(((float) (this.trackSidePadding + ((int) (normalizeValue(value) * ((float) width))))) - (((float) thumbDrawable.getBounds().width()) / 2.0f), ((float) top) - (((float) thumbDrawable.getBounds().height()) / 2.0f));
        thumbDrawable.draw(canvas);
        canvas.restore();
    }

    private void maybeDrawCompatHalo(Canvas canvas, int width, int top) {
        Canvas canvas2;
        if (shouldDrawCompatHalo()) {
            float[] bounds = {((float) this.trackSidePadding) + (normalizeValue(this.values.get(this.focusedThumbIdx).floatValue()) * ((float) width)), (float) top};
            if (isVertical()) {
                this.rotationMatrix.mapPoints(bounds);
            }
            if (Build.VERSION.SDK_INT < 28) {
                canvas2 = canvas;
                canvas2.clipRect(bounds[0] - ((float) this.haloRadius), bounds[1] - ((float) this.haloRadius), bounds[0] + ((float) this.haloRadius), bounds[1] + ((float) this.haloRadius), Region.Op.UNION);
            } else {
                canvas2 = canvas;
            }
            canvas2.drawCircle(bounds[0], bounds[1], (float) this.haloRadius, this.haloPaint);
            return;
        }
    }

    private boolean shouldDrawCompatHalo() {
        return this.forceDrawCompatHalo || !(getBackground() instanceof RippleDrawable);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        float eventCoordinateAxis1 = isVertical() ? event.getY() : event.getX();
        float eventCoordinateAxis2 = isVertical() ? event.getX() : event.getY();
        this.touchPosition = (eventCoordinateAxis1 - ((float) this.trackSidePadding)) / ((float) this.trackWidth);
        this.touchPosition = Math.max(0.0f, this.touchPosition);
        this.touchPosition = Math.min(1.0f, this.touchPosition);
        switch (event.getActionMasked()) {
            case 0:
                this.touchDownAxis1 = eventCoordinateAxis1;
                this.touchDownAxis2 = eventCoordinateAxis2;
                if ((isVertical() || !isPotentialVerticalScroll(event)) && (!isVertical() || !isPotentialHorizontalScroll(event))) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    if (pickActiveThumb()) {
                        requestFocus();
                        this.thumbIsPressed = true;
                        updateThumbWidthWhenPressed();
                        onStartTrackingTouch();
                        snapTouchPosition();
                        updateHaloHotspot();
                        invalidate();
                        break;
                    }
                }
                break;
            case 1:
            case 3:
                this.thumbIsPressed = false;
                if (this.lastEvent != null && this.lastEvent.getActionMasked() == 0 && Math.abs(this.lastEvent.getX() - event.getX()) <= ((float) this.scaledTouchSlop) && Math.abs(this.lastEvent.getY() - event.getY()) <= ((float) this.scaledTouchSlop) && pickActiveThumb()) {
                    onStartTrackingTouch();
                }
                if (this.activeThumbIdx != -1) {
                    snapTouchPosition();
                    updateHaloHotspot();
                    if (!(!hasGapBetweenThumbAndTrack() || this.defaultThumbWidth == -1 || this.defaultThumbTrackGapSize == -1)) {
                        setThumbWidth(this.defaultThumbWidth);
                        setThumbTrackGapSize(this.defaultThumbTrackGapSize);
                    }
                    this.activeThumbIdx = -1;
                    onStopTrackingTouch();
                }
                invalidate();
                break;
            case 2:
                if (!this.thumbIsPressed) {
                    if (!isVertical() && isPotentialVerticalScroll(event) && Math.abs(eventCoordinateAxis1 - this.touchDownAxis1) < ((float) this.scaledTouchSlop)) {
                        return false;
                    }
                    if (isVertical() && isPotentialHorizontalScroll(event) && Math.abs(eventCoordinateAxis2 - this.touchDownAxis2) < ((float) this.scaledTouchSlop) * TOUCH_SLOP_RATIO) {
                        return false;
                    }
                    getParent().requestDisallowInterceptTouchEvent(true);
                    if (pickActiveThumb()) {
                        this.thumbIsPressed = true;
                        updateThumbWidthWhenPressed();
                        onStartTrackingTouch();
                    }
                }
                snapTouchPosition();
                updateHaloHotspot();
                invalidate();
                break;
        }
        setPressed(this.thumbIsPressed);
        this.lastEvent = MotionEvent.obtain(event);
        return true;
    }

    private void updateThumbWidthWhenPressed() {
        if (hasGapBetweenThumbAndTrack()) {
            this.defaultThumbWidth = this.thumbWidth;
            this.defaultThumbTrackGapSize = this.thumbTrackGapSize;
            int pressedThumbWidth = Math.round(((float) this.thumbWidth) * 0.5f);
            setThumbWidth(pressedThumbWidth);
            setThumbTrackGapSize(this.thumbTrackGapSize - ((this.thumbWidth - pressedThumbWidth) / 2));
        }
    }

    private double snapPosition(float position) {
        if (this.stepSize <= 0.0f) {
            return (double) position;
        }
        int stepCount = (int) ((this.valueTo - this.valueFrom) / this.stepSize);
        return ((double) Math.round(((float) stepCount) * position)) / ((double) stepCount);
    }

    /* access modifiers changed from: protected */
    public boolean pickActiveThumb() {
        if (this.activeThumbIdx != -1) {
            return true;
        }
        float touchValue = getValueOfTouchPositionAbsolute();
        float touchX = valueToX(touchValue);
        this.activeThumbIdx = 0;
        float activeThumbDiff = Math.abs(this.values.get(this.activeThumbIdx).floatValue() - touchValue);
        for (int i = 1; i < this.values.size(); i++) {
            float valueDiff = Math.abs(this.values.get(i).floatValue() - touchValue);
            float valueX = valueToX(this.values.get(i).floatValue());
            if (Float.compare(valueDiff, activeThumbDiff) > 0) {
                break;
            }
            boolean movingForward = isRtl() || isVertical() ? valueX - touchX > 0.0f : valueX - touchX < 0.0f;
            if (Float.compare(valueDiff, activeThumbDiff) < 0) {
                activeThumbDiff = valueDiff;
                this.activeThumbIdx = i;
            } else if (Float.compare(valueDiff, activeThumbDiff) != 0) {
                continue;
            } else if (Math.abs(valueX - touchX) < ((float) this.scaledTouchSlop)) {
                this.activeThumbIdx = -1;
                return false;
            } else if (movingForward) {
                activeThumbDiff = valueDiff;
                this.activeThumbIdx = i;
            }
        }
        if (this.activeThumbIdx != -1) {
            return true;
        }
        return false;
    }

    private float getValueOfTouchPositionAbsolute() {
        float position = this.touchPosition;
        if (isRtl() || isVertical()) {
            position = 1.0f - position;
        }
        return ((this.valueTo - this.valueFrom) * position) + this.valueFrom;
    }

    private boolean snapTouchPosition() {
        return snapActiveThumbToValue(getValueOfTouchPosition());
    }

    private boolean snapActiveThumbToValue(float value) {
        return snapThumbToValue(this.activeThumbIdx, value);
    }

    /* access modifiers changed from: private */
    public boolean snapThumbToValue(int idx, float value) {
        this.focusedThumbIdx = idx;
        if (((double) Math.abs(value - this.values.get(idx).floatValue())) < THRESHOLD) {
            return false;
        }
        this.values.set(idx, Float.valueOf(getClampedValue(idx, value)));
        dispatchOnChangedFromUser(idx);
        return true;
    }

    private float getClampedValue(int idx, float value) {
        float minSeparation = getMinSeparation();
        float minSeparation2 = this.separationUnit == 0 ? dimenToValue(minSeparation) : minSeparation;
        if (isRtl() || isVertical()) {
            minSeparation2 = -minSeparation2;
        }
        return MathUtils.clamp(value, idx + -1 < 0 ? this.valueFrom : this.values.get(idx - 1).floatValue() + minSeparation2, idx + 1 >= this.values.size() ? this.valueTo : this.values.get(idx + 1).floatValue() - minSeparation2);
    }

    private float dimenToValue(float dimen) {
        if (dimen == 0.0f) {
            return 0.0f;
        }
        return (((dimen - ((float) this.trackSidePadding)) / ((float) this.trackWidth)) * (this.valueFrom - this.valueTo)) + this.valueFrom;
    }

    /* access modifiers changed from: protected */
    public void setSeparationUnit(int separationUnit2) {
        this.separationUnit = separationUnit2;
        this.dirtyConfig = true;
        postInvalidate();
    }

    /* access modifiers changed from: protected */
    public float getMinSeparation() {
        return 0.0f;
    }

    private float getValueOfTouchPosition() {
        double position = snapPosition(this.touchPosition);
        if (isRtl() || isVertical()) {
            position = 1.0d - position;
        }
        return (float) ((((double) (this.valueTo - this.valueFrom)) * position) + ((double) this.valueFrom));
    }

    private float valueToX(float value) {
        return (normalizeValue(value) * ((float) this.trackWidth)) + ((float) this.trackSidePadding);
    }

    private static float getAnimatorCurrentValueOrDefault(ValueAnimator animator, float defaultValue) {
        if (animator == null || !animator.isRunning()) {
            return defaultValue;
        }
        float value = ((Float) animator.getAnimatedValue()).floatValue();
        animator.cancel();
        return value;
    }

    private ValueAnimator createLabelAnimator(boolean enter) {
        TimeInterpolator interpolator;
        int duration;
        float endFraction = 0.0f;
        float startFraction = getAnimatorCurrentValueOrDefault(enter ? this.labelsOutAnimator : this.labelsInAnimator, enter ? 0.0f : 1.0f);
        if (enter) {
            endFraction = 1.0f;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{startFraction, endFraction});
        if (enter) {
            duration = MotionUtils.resolveThemeDuration(getContext(), LABEL_ANIMATION_ENTER_DURATION_ATTR, DEFAULT_LABEL_ANIMATION_ENTER_DURATION);
            interpolator = MotionUtils.resolveThemeInterpolator(getContext(), LABEL_ANIMATION_ENTER_EASING_ATTR, AnimationUtils.DECELERATE_INTERPOLATOR);
        } else {
            duration = MotionUtils.resolveThemeDuration(getContext(), LABEL_ANIMATION_EXIT_DURATION_ATTR, DEFAULT_LABEL_ANIMATION_EXIT_DURATION);
            interpolator = MotionUtils.resolveThemeInterpolator(getContext(), LABEL_ANIMATION_EXIT_EASING_ATTR, AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
        }
        animator.setDuration((long) duration);
        animator.setInterpolator(interpolator);
        animator.addUpdateListener(new BaseSlider$$ExternalSyntheticLambda0(this));
        return animator;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$createLabelAnimator$1$com-google-android-material-slider-BaseSlider  reason: not valid java name */
    public /* synthetic */ void m1719lambda$createLabelAnimator$1$comgoogleandroidmaterialsliderBaseSlider(ValueAnimator animation) {
        float fraction = ((Float) animation.getAnimatedValue()).floatValue();
        for (TooltipDrawable label : this.labels) {
            label.setRevealFraction(fraction);
        }
        postInvalidateOnAnimation();
    }

    /* access modifiers changed from: private */
    public void updateLabels() {
        updateLabelPivots();
        switch (this.labelBehavior) {
            case 0:
            case 1:
                if (this.activeThumbIdx == -1 || !isEnabled()) {
                    ensureLabelsRemoved();
                    return;
                } else {
                    ensureLabelsAdded();
                    return;
                }
            case 2:
                ensureLabelsRemoved();
                return;
            case 3:
                if (!isEnabled() || !isSliderVisibleOnScreen()) {
                    ensureLabelsRemoved();
                    return;
                } else {
                    ensureLabelsAdded();
                    return;
                }
            default:
                throw new IllegalArgumentException("Unexpected labelBehavior: " + this.labelBehavior);
        }
    }

    private void updateLabelPivots() {
        float labelPivotY;
        float labelPivotX;
        boolean isVertical = isVertical();
        boolean isRtl = isRtl();
        if (isVertical && isRtl) {
            labelPivotX = RIGHT_LABEL_PIVOT_X;
            labelPivotY = 0.5f;
        } else if (isVertical) {
            labelPivotX = 1.2f;
            labelPivotY = 0.5f;
        } else {
            labelPivotX = 0.5f;
            labelPivotY = 1.2f;
        }
        for (TooltipDrawable label : this.labels) {
            label.setPivots(labelPivotX, labelPivotY);
        }
    }

    private boolean isSliderVisibleOnScreen() {
        Rect contentViewBounds = new Rect();
        ViewUtils.getContentView(this).getHitRect(contentViewBounds);
        return getLocalVisibleRect(contentViewBounds) && isThisAndAncestorsVisible();
    }

    private boolean isThisAndAncestorsVisible() {
        return this.thisAndAncestorsVisible;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setVisibleToUser(false);
    }

    public void onVisibilityAggregated(boolean isVisible) {
        super.onVisibilityAggregated(isVisible);
        this.thisAndAncestorsVisible = isVisible;
    }

    private void ensureLabelsRemoved() {
        if (this.labelsAreAnimatedIn) {
            this.labelsAreAnimatedIn = false;
            this.labelsOutAnimator = createLabelAnimator(false);
            this.labelsInAnimator = null;
            this.labelsOutAnimator.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ViewOverlay contentViewOverlay = BaseSlider.this.getContentViewOverlay();
                    if (contentViewOverlay != null) {
                        for (TooltipDrawable label : BaseSlider.this.labels) {
                            contentViewOverlay.remove(label);
                        }
                    }
                }
            });
            this.labelsOutAnimator.start();
        }
    }

    private void ensureLabelsAdded() {
        if (!this.labelsAreAnimatedIn) {
            this.labelsAreAnimatedIn = true;
            this.labelsInAnimator = createLabelAnimator(true);
            this.labelsOutAnimator = null;
            this.labelsInAnimator.start();
        }
        Iterator<TooltipDrawable> labelItr = this.labels.iterator();
        for (int i = 0; i < this.values.size() && labelItr.hasNext(); i++) {
            if (i != this.focusedThumbIdx) {
                setValueForLabel(labelItr.next(), this.values.get(i).floatValue());
            }
        }
        if (labelItr.hasNext() != 0) {
            setValueForLabel(labelItr.next(), this.values.get(this.focusedThumbIdx).floatValue());
            return;
        }
        throw new IllegalStateException(String.format("Not enough labels(%d) to display all the values(%d)", new Object[]{Integer.valueOf(this.labels.size()), Integer.valueOf(this.values.size())}));
    }

    /* access modifiers changed from: private */
    public String formatValue(float value) {
        if (hasLabelFormatter()) {
            return this.formatter.getFormattedValue(value);
        }
        return String.format(((float) ((int) value)) == value ? "%.0f" : "%.2f", new Object[]{Float.valueOf(value)});
    }

    private void setValueForLabel(TooltipDrawable label, float value) {
        label.setText(formatValue(value));
        positionLabel(label, value);
        ViewOverlay contentViewOverlay = getContentViewOverlay();
        if (contentViewOverlay != null) {
            contentViewOverlay.add(label);
        }
    }

    private void positionLabel(TooltipDrawable label, float value) {
        calculateLabelBounds(label, value);
        if (isVertical()) {
            RectF labelBounds = new RectF(this.labelRect);
            this.rotationMatrix.mapRect(labelBounds);
            labelBounds.round(this.labelRect);
        }
        DescendantOffsetUtils.offsetDescendantRect(ViewUtils.getContentView(this), this, this.labelRect);
        label.setBounds(this.labelRect);
    }

    private void calculateLabelBounds(TooltipDrawable label, float value) {
        int top;
        int bottom;
        int right;
        int left;
        if (isVertical()) {
            left = (this.trackSidePadding + ((int) (normalizeValue(value) * ((float) this.trackWidth)))) - (label.getIntrinsicHeight() / 2);
            right = label.getIntrinsicHeight() + left;
            if (isRtl()) {
                bottom = calculateTrackCenter() - (this.labelPadding + (this.thumbHeight / 2));
                top = bottom - label.getIntrinsicWidth();
            } else {
                top = this.labelPadding + (this.thumbHeight / 2) + calculateTrackCenter();
                bottom = label.getIntrinsicWidth() + top;
            }
        } else {
            left = (this.trackSidePadding + ((int) (normalizeValue(value) * ((float) this.trackWidth)))) - (label.getIntrinsicWidth() / 2);
            right = label.getIntrinsicWidth() + left;
            bottom = calculateTrackCenter() - (this.labelPadding + (this.thumbHeight / 2));
            top = bottom - label.getIntrinsicHeight();
        }
        this.labelRect.set(left, top, right, bottom);
    }

    private void invalidateTrack() {
        this.inactiveTrackPaint.setStrokeWidth((float) this.trackThickness);
        this.activeTrackPaint.setStrokeWidth((float) this.trackThickness);
    }

    private boolean isInVerticalScrollingContainer() {
        ViewParent p = getParent();
        while (true) {
            boolean canScrollVertically = false;
            if (!(p instanceof ViewGroup)) {
                return false;
            }
            ViewGroup parent = (ViewGroup) p;
            if (parent.canScrollVertically(1) || parent.canScrollVertically(-1)) {
                canScrollVertically = true;
            }
            if (canScrollVertically && parent.shouldDelayChildPressedState()) {
                return true;
            }
            p = p.getParent();
        }
    }

    private boolean isInHorizontalScrollingContainer() {
        ViewParent p = getParent();
        while (true) {
            boolean canScrollHorizontally = false;
            if (!(p instanceof ViewGroup)) {
                return false;
            }
            ViewGroup parent = (ViewGroup) p;
            if (parent.canScrollHorizontally(1) || parent.canScrollHorizontally(-1)) {
                canScrollHorizontally = true;
            }
            if (canScrollHorizontally && parent.shouldDelayChildPressedState()) {
                return true;
            }
            p = p.getParent();
        }
    }

    private static boolean isMouseEvent(MotionEvent event) {
        return event.getToolType(0) == 3;
    }

    private boolean isPotentialVerticalScroll(MotionEvent event) {
        return !isMouseEvent(event) && isInVerticalScrollingContainer();
    }

    private boolean isPotentialHorizontalScroll(MotionEvent event) {
        return !isMouseEvent(event) && isInHorizontalScrollingContainer();
    }

    private void dispatchOnChangedProgrammatically() {
        for (L listener : this.changeListeners) {
            Iterator<Float> it = this.values.iterator();
            while (it.hasNext()) {
                listener.onValueChange(this, it.next().floatValue(), false);
            }
        }
    }

    private void dispatchOnChangedFromUser(int idx) {
        for (L listener : this.changeListeners) {
            listener.onValueChange(this, this.values.get(idx).floatValue(), true);
        }
        if (this.accessibilityManager != null && this.accessibilityManager.isEnabled()) {
            scheduleAccessibilityEventSender(idx);
        }
    }

    private void onStartTrackingTouch() {
        for (T listener : this.touchListeners) {
            listener.onStartTrackingTouch(this);
        }
    }

    private void onStopTrackingTouch() {
        for (T listener : this.touchListeners) {
            listener.onStopTrackingTouch(this);
        }
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        this.inactiveTrackPaint.setColor(getColorForState(this.trackColorInactive));
        this.activeTrackPaint.setColor(getColorForState(this.trackColorActive));
        this.inactiveTicksPaint.setColor(getColorForState(this.tickColorInactive));
        this.activeTicksPaint.setColor(getColorForState(this.tickColorActive));
        this.stopIndicatorPaint.setColor(getColorForState(this.tickColorInactive));
        for (TooltipDrawable label : this.labels) {
            if (label.isStateful()) {
                label.setState(getDrawableState());
            }
        }
        if (this.defaultThumbDrawable.isStateful()) {
            this.defaultThumbDrawable.setState(getDrawableState());
        }
        this.haloPaint.setColor(getColorForState(this.haloColor));
        this.haloPaint.setAlpha(63);
    }

    private int getColorForState(ColorStateList colorStateList) {
        return colorStateList.getColorForState(getDrawableState(), colorStateList.getDefaultColor());
    }

    /* access modifiers changed from: package-private */
    public void forceDrawCompatHalo(boolean force) {
        this.forceDrawCompatHalo = force;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!isEnabled()) {
            return super.onKeyDown(keyCode, event);
        }
        if (this.values.size() == 1) {
            this.activeThumbIdx = 0;
        }
        if (this.activeThumbIdx == -1) {
            Boolean handled = onKeyDownNoActiveThumb(keyCode, event);
            return handled != null ? handled.booleanValue() : super.onKeyDown(keyCode, event);
        }
        this.isLongPress |= event.isLongPress();
        Float increment = calculateIncrementForKey(keyCode);
        if (increment != null) {
            if (snapActiveThumbToValue(this.values.get(this.activeThumbIdx).floatValue() + increment.floatValue())) {
                updateHaloHotspot();
                postInvalidate();
            }
            return true;
        }
        switch (keyCode) {
            case 23:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_WRAP_BEHAVIOR_IN_PARENT /*66*/:
                this.activeThumbIdx = -1;
                postInvalidate();
                return true;
            case LockFreeTaskQueueCore.CLOSED_SHIFT:
                if (event.hasNoModifiers()) {
                    return moveFocus(1);
                }
                if (event.isShiftPressed()) {
                    return moveFocus(-1);
                }
                return false;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    private Boolean onKeyDownNoActiveThumb(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 21:
                moveFocusInAbsoluteDirection(-1);
                return true;
            case 22:
                moveFocusInAbsoluteDirection(1);
                return true;
            case 23:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_WRAP_BEHAVIOR_IN_PARENT /*66*/:
                this.activeThumbIdx = this.focusedThumbIdx;
                postInvalidate();
                return true;
            case LockFreeTaskQueueCore.CLOSED_SHIFT:
                if (event.hasNoModifiers()) {
                    return Boolean.valueOf(moveFocus(1));
                }
                if (event.isShiftPressed()) {
                    return Boolean.valueOf(moveFocus(-1));
                }
                return false;
            case 69:
                moveFocus(-1);
                return true;
            case 70:
            case 81:
                moveFocus(1);
                return true;
            default:
                return null;
        }
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        this.isLongPress = false;
        return super.onKeyUp(keyCode, event);
    }

    /* access modifiers changed from: package-private */
    public final boolean isRtl() {
        return getLayoutDirection() == 1;
    }

    public boolean isVertical() {
        return this.widgetOrientation == 1;
    }

    public boolean isCentered() {
        return this.centered;
    }

    private boolean moveFocus(int direction) {
        int oldFocusedThumbIdx = this.focusedThumbIdx;
        this.focusedThumbIdx = (int) MathUtils.clamp(((long) oldFocusedThumbIdx) + ((long) direction), 0, (long) (this.values.size() - 1));
        if (this.focusedThumbIdx == oldFocusedThumbIdx) {
            return false;
        }
        if (this.activeThumbIdx != -1) {
            this.activeThumbIdx = this.focusedThumbIdx;
        }
        updateHaloHotspot();
        postInvalidate();
        return true;
    }

    private boolean moveFocusInAbsoluteDirection(int direction) {
        if (isRtl() || isVertical()) {
            direction = direction == Integer.MIN_VALUE ? Integer.MAX_VALUE : -direction;
        }
        return moveFocus(direction);
    }

    private Float calculateIncrementForKey(int keyCode) {
        float increment = this.isLongPress ? calculateStepIncrement(20) : calculateStepIncrement();
        switch (keyCode) {
            case 19:
                if (isVertical()) {
                    return Float.valueOf(increment);
                }
                return null;
            case 20:
                if (isVertical()) {
                    return Float.valueOf(-increment);
                }
                return null;
            case 21:
                return Float.valueOf(isRtl() ? increment : -increment);
            case 22:
                return Float.valueOf(isRtl() ? -increment : increment);
            case 69:
                return Float.valueOf(-increment);
            case 70:
            case 81:
                return Float.valueOf(increment);
            default:
                return null;
        }
    }

    private float calculateStepIncrement() {
        if (this.stepSize == 0.0f) {
            return 1.0f;
        }
        return this.stepSize;
    }

    /* access modifiers changed from: private */
    public float calculateStepIncrement(int stepFactor) {
        float increment = calculateStepIncrement();
        float numSteps = (this.valueTo - this.valueFrom) / increment;
        if (numSteps <= ((float) stepFactor)) {
            return increment;
        }
        return ((float) Math.round(numSteps / ((float) stepFactor))) * increment;
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (!gainFocus) {
            this.activeThumbIdx = -1;
            this.accessibilityHelper.clearKeyboardFocusForVirtualView(this.focusedThumbIdx);
            return;
        }
        focusThumbOnFocusGained(direction);
        this.accessibilityHelper.requestKeyboardFocusForVirtualView(this.focusedThumbIdx);
    }

    private void focusThumbOnFocusGained(int direction) {
        switch (direction) {
            case 1:
                moveFocus(Integer.MAX_VALUE);
                return;
            case 2:
                moveFocus(Integer.MIN_VALUE);
                return;
            case 17:
                moveFocusInAbsoluteDirection(Integer.MAX_VALUE);
                return;
            case ConstraintLayout.LayoutParams.Table.LAYOUT_WRAP_BEHAVIOR_IN_PARENT /*66*/:
                moveFocusInAbsoluteDirection(Integer.MIN_VALUE);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public final int getAccessibilityFocusedVirtualViewId() {
        return this.accessibilityHelper.getAccessibilityFocusedVirtualViewId();
    }

    public CharSequence getAccessibilityClassName() {
        return SeekBar.class.getName();
    }

    public boolean dispatchHoverEvent(MotionEvent event) {
        return this.accessibilityHelper.dispatchHoverEvent(event) || super.dispatchHoverEvent(event);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    private void scheduleAccessibilityEventSender(int idx) {
        if (this.accessibilityEventSender == null) {
            this.accessibilityEventSender = new AccessibilityEventSender();
        } else {
            removeCallbacks(this.accessibilityEventSender);
        }
        this.accessibilityEventSender.setVirtualViewId(idx);
        postDelayed(this.accessibilityEventSender, 200);
    }

    private class AccessibilityEventSender implements Runnable {
        int virtualViewId;

        private AccessibilityEventSender() {
            this.virtualViewId = -1;
        }

        /* access modifiers changed from: package-private */
        public void setVirtualViewId(int virtualViewId2) {
            this.virtualViewId = virtualViewId2;
        }

        public void run() {
            BaseSlider.this.accessibilityHelper.sendEventForVirtualView(this.virtualViewId, 4);
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SliderState sliderState = new SliderState(super.onSaveInstanceState());
        sliderState.valueFrom = this.valueFrom;
        sliderState.valueTo = this.valueTo;
        sliderState.values = new ArrayList<>(this.values);
        sliderState.stepSize = this.stepSize;
        sliderState.hasFocus = hasFocus();
        return sliderState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        SliderState sliderState = (SliderState) state;
        super.onRestoreInstanceState(sliderState.getSuperState());
        this.valueFrom = sliderState.valueFrom;
        this.valueTo = sliderState.valueTo;
        setValuesInternal(sliderState.values);
        this.stepSize = sliderState.stepSize;
        if (sliderState.hasFocus) {
            requestFocus();
        }
    }

    static class SliderState extends View.BaseSavedState {
        public static final Parcelable.Creator<SliderState> CREATOR = new Parcelable.Creator<SliderState>() {
            public SliderState createFromParcel(Parcel source) {
                return new SliderState(source);
            }

            public SliderState[] newArray(int size) {
                return new SliderState[size];
            }
        };
        boolean hasFocus;
        float stepSize;
        float valueFrom;
        float valueTo;
        ArrayList<Float> values;

        SliderState(Parcelable superState) {
            super(superState);
        }

        private SliderState(Parcel source) {
            super(source);
            this.valueFrom = source.readFloat();
            this.valueTo = source.readFloat();
            this.values = new ArrayList<>();
            source.readList(this.values, Float.class.getClassLoader());
            this.stepSize = source.readFloat();
            this.hasFocus = source.createBooleanArray()[0];
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeFloat(this.valueFrom);
            dest.writeFloat(this.valueTo);
            dest.writeList(this.values);
            dest.writeFloat(this.stepSize);
            dest.writeBooleanArray(new boolean[]{this.hasFocus});
        }
    }

    /* access modifiers changed from: package-private */
    public void updateBoundsForVirtualViewId(int virtualViewId, Rect virtualViewBounds) {
        int x = this.trackSidePadding + ((int) (normalizeValue(getValues().get(virtualViewId).floatValue()) * ((float) this.trackWidth)));
        int y = calculateTrackCenter();
        int touchTargetOffsetX = Math.max(this.thumbWidth / 2, this.minTouchTargetSize / 2);
        int touchTargetOffsetY = Math.max(this.thumbHeight / 2, this.minTouchTargetSize / 2);
        RectF rect = new RectF((float) (x - touchTargetOffsetX), (float) (y - touchTargetOffsetY), (float) (x + touchTargetOffsetX), (float) (y + touchTargetOffsetY));
        if (isVertical()) {
            this.rotationMatrix.mapRect(rect);
        }
        virtualViewBounds.set((int) rect.left, (int) rect.top, (int) rect.right, (int) rect.bottom);
    }

    public static class AccessibilityHelper extends ExploreByTouchHelper {
        private final BaseSlider<?, ?, ?> slider;
        final Rect virtualViewBounds = new Rect();

        AccessibilityHelper(BaseSlider<?, ?, ?> slider2) {
            super(slider2);
            this.slider = slider2;
        }

        /* access modifiers changed from: protected */
        public int getVirtualViewAt(float x, float y) {
            for (int i = 0; i < this.slider.getValues().size(); i++) {
                this.slider.updateBoundsForVirtualViewId(i, this.virtualViewBounds);
                if (this.virtualViewBounds.contains((int) x, (int) y)) {
                    return i;
                }
            }
            return -1;
        }

        /* access modifiers changed from: protected */
        public void getVisibleVirtualViews(List<Integer> virtualViewIds) {
            for (int i = 0; i < this.slider.getValues().size(); i++) {
                virtualViewIds.add(Integer.valueOf(i));
            }
        }

        /* access modifiers changed from: protected */
        public void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfoCompat info) {
            info.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS);
            List<Float> values = this.slider.getValues();
            float value = values.get(virtualViewId).floatValue();
            float valueFrom = this.slider.getValueFrom();
            float valueTo = this.slider.getValueTo();
            if (this.slider.isEnabled()) {
                if (value > valueFrom) {
                    info.addAction(8192);
                }
                if (value < valueTo) {
                    info.addAction(4096);
                }
            }
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(2);
            try {
                valueFrom = nf.parse(nf.format((double) valueFrom)).floatValue();
                valueTo = nf.parse(nf.format((double) valueTo)).floatValue();
                value = nf.parse(nf.format((double) value)).floatValue();
            } catch (ParseException e) {
                Log.w(BaseSlider.TAG, String.format(BaseSlider.WARNING_PARSE_ERROR, new Object[]{Float.valueOf(value), Float.valueOf(valueFrom), Float.valueOf(valueTo)}));
            }
            info.setRangeInfo(AccessibilityNodeInfoCompat.RangeInfoCompat.obtain(1, valueFrom, valueTo, value));
            info.setClassName(SeekBar.class.getName());
            StringBuilder contentDescription = new StringBuilder();
            if (this.slider.getContentDescription() != null) {
                contentDescription.append(this.slider.getContentDescription()).append(",");
            }
            String verbalValue = this.slider.formatValue(value);
            String verbalValueType = this.slider.getContext().getString(R.string.material_slider_value);
            if (values.size() > 1) {
                verbalValueType = startOrEndDescription(virtualViewId);
            }
            CharSequence stateDescription = ViewCompat.getStateDescription(this.slider);
            if (!TextUtils.isEmpty(stateDescription)) {
                info.setStateDescription(stateDescription);
            } else {
                contentDescription.append(String.format(Locale.getDefault(), "%s, %s", new Object[]{verbalValueType, verbalValue}));
            }
            info.setContentDescription(contentDescription.toString());
            this.slider.updateBoundsForVirtualViewId(virtualViewId, this.virtualViewBounds);
            info.setBoundsInParent(this.virtualViewBounds);
        }

        private String startOrEndDescription(int virtualViewId) {
            if (virtualViewId == this.slider.getValues().size() - 1) {
                return this.slider.getContext().getString(R.string.material_slider_range_end);
            }
            if (virtualViewId == 0) {
                return this.slider.getContext().getString(R.string.material_slider_range_start);
            }
            return "";
        }

        /* access modifiers changed from: protected */
        public boolean onPerformActionForVirtualView(int virtualViewId, int action, Bundle arguments) {
            if (!this.slider.isEnabled()) {
                return false;
            }
            switch (action) {
                case 4096:
                case 8192:
                    float increment = this.slider.calculateStepIncrement(20);
                    if (action == 8192) {
                        increment = -increment;
                    }
                    if (this.slider.isRtl()) {
                        increment = -increment;
                    }
                    if (!this.slider.snapThumbToValue(virtualViewId, MathUtils.clamp(this.slider.getValues().get(virtualViewId).floatValue() + increment, this.slider.getValueFrom(), this.slider.getValueTo()))) {
                        return false;
                    }
                    this.slider.setActiveThumbIndex(virtualViewId);
                    this.slider.scheduleTooltipTimeout();
                    this.slider.updateHaloHotspot();
                    this.slider.postInvalidate();
                    invalidateVirtualView(virtualViewId);
                    return true;
                case 16908349:
                    if (arguments == null || !arguments.containsKey(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_PROGRESS_VALUE)) {
                        return false;
                    }
                    if (!this.slider.snapThumbToValue(virtualViewId, arguments.getFloat(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_PROGRESS_VALUE))) {
                        return false;
                    }
                    this.slider.updateHaloHotspot();
                    this.slider.postInvalidate();
                    invalidateVirtualView(virtualViewId);
                    return true;
                default:
                    return false;
            }
        }
    }
}
