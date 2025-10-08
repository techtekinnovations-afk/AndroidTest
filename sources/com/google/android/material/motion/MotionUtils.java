package com.google.android.material.motion;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.animation.AnimationUtils;
import android.view.animation.PathInterpolator;
import androidx.core.graphics.PathParser;
import androidx.dynamicanimation.animation.SpringForce;
import com.google.android.material.R;
import com.google.android.material.resources.MaterialAttributes;

public class MotionUtils {
    private static final String EASING_TYPE_CUBIC_BEZIER = "cubic-bezier";
    private static final String EASING_TYPE_FORMAT_END = ")";
    private static final String EASING_TYPE_FORMAT_START = "(";
    private static final String EASING_TYPE_PATH = "path";

    private MotionUtils() {
    }

    public static SpringForce resolveThemeSpringForce(Context context, int attrResId, int defStyleRes) {
        TypedArray a;
        TypedValue tv = MaterialAttributes.resolve(context, attrResId);
        if (tv == null) {
            a = context.obtainStyledAttributes((AttributeSet) null, R.styleable.MaterialSpring, 0, defStyleRes);
        } else {
            a = context.obtainStyledAttributes(tv.resourceId, R.styleable.MaterialSpring);
        }
        SpringForce springForce = new SpringForce();
        try {
            float stiffness = a.getFloat(R.styleable.MaterialSpring_stiffness, Float.MIN_VALUE);
            if (stiffness != Float.MIN_VALUE) {
                float damping = a.getFloat(R.styleable.MaterialSpring_damping, Float.MIN_VALUE);
                if (damping != Float.MIN_VALUE) {
                    springForce.setStiffness(stiffness);
                    springForce.setDampingRatio(damping);
                    return springForce;
                }
                throw new IllegalArgumentException("A MaterialSpring style must have a damping value.");
            }
            throw new IllegalArgumentException("A MaterialSpring style must have stiffness value.");
        } finally {
            a.recycle();
        }
    }

    public static int resolveThemeDuration(Context context, int attrResId, int defaultDuration) {
        return MaterialAttributes.resolveInteger(context, attrResId, defaultDuration);
    }

    public static TimeInterpolator resolveThemeInterpolator(Context context, int attrResId, TimeInterpolator defaultInterpolator) {
        TypedValue easingValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(attrResId, easingValue, true)) {
            return defaultInterpolator;
        }
        if (easingValue.type == 3) {
            String easingString = String.valueOf(easingValue.string);
            if (isLegacyEasingAttribute(easingString)) {
                return getLegacyThemeInterpolator(easingString);
            }
            return AnimationUtils.loadInterpolator(context, easingValue.resourceId);
        }
        throw new IllegalArgumentException("Motion easing theme attribute must be an @interpolator resource for ?attr/motionEasing*Interpolator attributes or a string for ?attr/motionEasing* attributes.");
    }

    private static TimeInterpolator getLegacyThemeInterpolator(String easingString) {
        if (isLegacyEasingType(easingString, EASING_TYPE_CUBIC_BEZIER)) {
            String[] controlPoints = getLegacyEasingContent(easingString, EASING_TYPE_CUBIC_BEZIER).split(",");
            if (controlPoints.length == 4) {
                return new PathInterpolator(getLegacyControlPoint(controlPoints, 0), getLegacyControlPoint(controlPoints, 1), getLegacyControlPoint(controlPoints, 2), getLegacyControlPoint(controlPoints, 3));
            }
            throw new IllegalArgumentException("Motion easing theme attribute must have 4 control points if using bezier curve format; instead got: " + controlPoints.length);
        } else if (isLegacyEasingType(easingString, EASING_TYPE_PATH)) {
            return new PathInterpolator(PathParser.createPathFromPathData(getLegacyEasingContent(easingString, EASING_TYPE_PATH)));
        } else {
            throw new IllegalArgumentException("Invalid motion easing type: " + easingString);
        }
    }

    private static boolean isLegacyEasingAttribute(String easingString) {
        return isLegacyEasingType(easingString, EASING_TYPE_CUBIC_BEZIER) || isLegacyEasingType(easingString, EASING_TYPE_PATH);
    }

    private static boolean isLegacyEasingType(String easingString, String easingType) {
        return easingString.startsWith(new StringBuilder().append(easingType).append(EASING_TYPE_FORMAT_START).toString()) && easingString.endsWith(EASING_TYPE_FORMAT_END);
    }

    private static String getLegacyEasingContent(String easingString, String easingType) {
        return easingString.substring(easingType.length() + EASING_TYPE_FORMAT_START.length(), easingString.length() - EASING_TYPE_FORMAT_END.length());
    }

    private static float getLegacyControlPoint(String[] controlPoints, int index) {
        float controlPoint = Float.parseFloat(controlPoints[index]);
        if (controlPoint >= 0.0f && controlPoint <= 1.0f) {
            return controlPoint;
        }
        throw new IllegalArgumentException("Motion easing control point value must be between 0 and 1; instead got: " + controlPoint);
    }
}
