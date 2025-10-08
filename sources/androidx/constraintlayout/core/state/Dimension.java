package androidx.constraintlayout.core.state;

import androidx.constraintlayout.core.widgets.ConstraintWidget;

public class Dimension {
    public static final Object FIXED_DIMENSION = new String("FIXED_DIMENSION");
    public static final Object PARENT_DIMENSION = new String("PARENT_DIMENSION");
    public static final Object PERCENT_DIMENSION = new String("PERCENT_DIMENSION");
    public static final Object RATIO_DIMENSION = new String("RATIO_DIMENSION");
    public static final Object SPREAD_DIMENSION = new String("SPREAD_DIMENSION");
    public static final Object WRAP_DIMENSION = new String("WRAP_DIMENSION");
    Object mInitialValue = WRAP_DIMENSION;
    boolean mIsSuggested = false;
    int mMax = Integer.MAX_VALUE;
    int mMin = 0;
    float mPercent = 1.0f;
    String mRatioString = null;
    int mValue = 0;
    private final int mWrapContent = -2;

    public enum Type {
        FIXED,
        WRAP,
        MATCH_PARENT,
        MATCH_CONSTRAINT
    }

    public boolean equalsFixedValue(int value) {
        if (this.mInitialValue == null && this.mValue == value) {
            return true;
        }
        return false;
    }

    private Dimension() {
    }

    private Dimension(Object type) {
        this.mInitialValue = type;
    }

    @Deprecated
    public static Dimension Suggested(int value) {
        return createSuggested(value);
    }

    public static Dimension createSuggested(int value) {
        Dimension dimension = new Dimension();
        dimension.suggested(value);
        return dimension;
    }

    @Deprecated
    public static Dimension Suggested(Object startValue) {
        return createSuggested(startValue);
    }

    public static Dimension createSuggested(Object startValue) {
        Dimension dimension = new Dimension();
        dimension.suggested(startValue);
        return dimension;
    }

    @Deprecated
    public static Dimension Fixed(int value) {
        return createFixed(value);
    }

    public static Dimension createFixed(int value) {
        Dimension dimension = new Dimension(FIXED_DIMENSION);
        dimension.fixed(value);
        return dimension;
    }

    @Deprecated
    public static Dimension Fixed(Object value) {
        Dimension dimension = new Dimension(FIXED_DIMENSION);
        dimension.fixed(value);
        return dimension;
    }

    public static Dimension createFixed(Object value) {
        Dimension dimension = new Dimension(FIXED_DIMENSION);
        dimension.fixed(value);
        return dimension;
    }

    @Deprecated
    public static Dimension Percent(Object key, float value) {
        return createPercent(key, value);
    }

    public static Dimension createPercent(Object key, float value) {
        Dimension dimension = new Dimension(PERCENT_DIMENSION);
        dimension.percent(key, value);
        return dimension;
    }

    @Deprecated
    public static Dimension Parent() {
        return createParent();
    }

    public static Dimension createParent() {
        return new Dimension(PARENT_DIMENSION);
    }

    @Deprecated
    public static Dimension Wrap() {
        return createWrap();
    }

    public static Dimension createWrap() {
        return new Dimension(WRAP_DIMENSION);
    }

    @Deprecated
    public static Dimension Spread() {
        return createSpread();
    }

    public static Dimension createSpread() {
        return new Dimension(SPREAD_DIMENSION);
    }

    @Deprecated
    public static Dimension Ratio(String ratio) {
        return createRatio(ratio);
    }

    public static Dimension createRatio(String ratio) {
        Dimension dimension = new Dimension(RATIO_DIMENSION);
        dimension.ratio(ratio);
        return dimension;
    }

    public Dimension percent(Object key, float value) {
        this.mPercent = value;
        return this;
    }

    public Dimension min(int value) {
        if (value >= 0) {
            this.mMin = value;
        }
        return this;
    }

    public Dimension min(Object value) {
        if (value == WRAP_DIMENSION) {
            this.mMin = -2;
        }
        return this;
    }

    public Dimension max(int value) {
        if (this.mMax >= 0) {
            this.mMax = value;
        }
        return this;
    }

    public Dimension max(Object value) {
        if (value == WRAP_DIMENSION && this.mIsSuggested) {
            this.mInitialValue = WRAP_DIMENSION;
            this.mMax = Integer.MAX_VALUE;
        }
        return this;
    }

    public Dimension suggested(int value) {
        this.mIsSuggested = true;
        if (value >= 0) {
            this.mMax = value;
        }
        return this;
    }

    public Dimension suggested(Object value) {
        this.mInitialValue = value;
        this.mIsSuggested = true;
        return this;
    }

    public Dimension fixed(Object value) {
        this.mInitialValue = value;
        if (value instanceof Integer) {
            this.mValue = ((Integer) value).intValue();
            this.mInitialValue = null;
        }
        return this;
    }

    public Dimension fixed(int value) {
        this.mInitialValue = null;
        this.mValue = value;
        return this;
    }

    public Dimension ratio(String ratio) {
        this.mRatioString = ratio;
        return this;
    }

    /* access modifiers changed from: package-private */
    public void setValue(int value) {
        this.mIsSuggested = false;
        this.mInitialValue = null;
        this.mValue = value;
    }

    /* access modifiers changed from: package-private */
    public int getValue() {
        return this.mValue;
    }

    public void apply(State state, ConstraintWidget constraintWidget, int orientation) {
        if (this.mRatioString != null) {
            constraintWidget.setDimensionRatio(this.mRatioString);
        }
        if (orientation == 0) {
            if (this.mIsSuggested) {
                constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
                int type = 0;
                if (this.mInitialValue == WRAP_DIMENSION) {
                    type = 1;
                } else if (this.mInitialValue == PERCENT_DIMENSION) {
                    type = 2;
                }
                constraintWidget.setHorizontalMatchStyle(type, this.mMin, this.mMax, this.mPercent);
                return;
            }
            if (this.mMin > 0) {
                constraintWidget.setMinWidth(this.mMin);
            }
            if (this.mMax < Integer.MAX_VALUE) {
                constraintWidget.setMaxWidth(this.mMax);
            }
            if (this.mInitialValue == WRAP_DIMENSION) {
                constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
            } else if (this.mInitialValue == PARENT_DIMENSION) {
                constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
            } else if (this.mInitialValue == null) {
                constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                constraintWidget.setWidth(this.mValue);
            }
        } else if (this.mIsSuggested) {
            constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
            int type2 = 0;
            if (this.mInitialValue == WRAP_DIMENSION) {
                type2 = 1;
            } else if (this.mInitialValue == PERCENT_DIMENSION) {
                type2 = 2;
            }
            constraintWidget.setVerticalMatchStyle(type2, this.mMin, this.mMax, this.mPercent);
        } else {
            if (this.mMin > 0) {
                constraintWidget.setMinHeight(this.mMin);
            }
            if (this.mMax < Integer.MAX_VALUE) {
                constraintWidget.setMaxHeight(this.mMax);
            }
            if (this.mInitialValue == WRAP_DIMENSION) {
                constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
            } else if (this.mInitialValue == PARENT_DIMENSION) {
                constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
            } else if (this.mInitialValue == null) {
                constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                constraintWidget.setHeight(this.mValue);
            }
        }
    }
}
