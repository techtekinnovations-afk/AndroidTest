package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.Cache;
import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.state.WidgetFrame;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.analyzer.ChainRun;
import androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun;
import androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun;
import androidx.constraintlayout.core.widgets.analyzer.WidgetRun;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ConstraintWidget {
    public static final int ANCHOR_BASELINE = 4;
    public static final int ANCHOR_BOTTOM = 3;
    public static final int ANCHOR_LEFT = 0;
    public static final int ANCHOR_RIGHT = 1;
    public static final int ANCHOR_TOP = 2;
    private static final boolean AUTOTAG_CENTER = false;
    public static final int BOTH = 2;
    public static final int CHAIN_PACKED = 2;
    public static final int CHAIN_SPREAD = 0;
    public static final int CHAIN_SPREAD_INSIDE = 1;
    public static float DEFAULT_BIAS = 0.5f;
    static final int DIMENSION_HORIZONTAL = 0;
    static final int DIMENSION_VERTICAL = 1;
    protected static final int DIRECT = 2;
    private static final boolean DO_NOT_USE = false;
    public static final int GONE = 8;
    public static final int HORIZONTAL = 0;
    public static final int INVISIBLE = 4;
    public static final int MATCH_CONSTRAINT_PERCENT = 2;
    public static final int MATCH_CONSTRAINT_RATIO = 3;
    public static final int MATCH_CONSTRAINT_RATIO_RESOLVED = 4;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    protected static final int SOLVER = 1;
    public static final int UNKNOWN = -1;
    private static final boolean USE_WRAP_DIMENSION_FOR_SPREAD = false;
    public static final int VERTICAL = 1;
    public static final int VISIBLE = 0;
    private static final int WRAP = -2;
    public static final int WRAP_BEHAVIOR_HORIZONTAL_ONLY = 1;
    public static final int WRAP_BEHAVIOR_INCLUDED = 0;
    public static final int WRAP_BEHAVIOR_SKIPPED = 3;
    public static final int WRAP_BEHAVIOR_VERTICAL_ONLY = 2;
    public WidgetFrame frame;
    public ChainRun horizontalChainRun;
    public int horizontalGroup;
    public boolean[] isTerminalWidget;
    protected ArrayList<ConstraintAnchor> mAnchors;
    private boolean mAnimated;
    public ConstraintAnchor mBaseline;
    int mBaselineDistance;
    public ConstraintAnchor mBottom;
    boolean mBottomHasCentered;
    public ConstraintAnchor mCenter;
    ConstraintAnchor mCenterX;
    ConstraintAnchor mCenterY;
    public float mCircleConstraintAngle;
    private Object mCompanionWidget;
    private int mContainerItemSkip;
    private String mDebugName;
    public float mDimensionRatio;
    protected int mDimensionRatioSide;
    int mDistToBottom;
    int mDistToLeft;
    int mDistToRight;
    int mDistToTop;
    boolean mGroupsToSolver;
    private boolean mHasBaseline;
    int mHeight;
    private int mHeightOverride;
    float mHorizontalBiasPercent;
    boolean mHorizontalChainFixedPosition;
    int mHorizontalChainStyle;
    ConstraintWidget mHorizontalNextWidget;
    public int mHorizontalResolution;
    public HorizontalWidgetRun mHorizontalRun;
    private boolean mHorizontalSolvingPass;
    boolean mHorizontalWrapVisited;
    private boolean mInPlaceholder;
    private boolean mInVirtualLayout;
    public boolean mIsHeightWrapContent;
    private boolean[] mIsInBarrier;
    public boolean mIsWidthWrapContent;
    private int mLastHorizontalMeasureSpec;
    private int mLastVerticalMeasureSpec;
    public ConstraintAnchor mLeft;
    boolean mLeftHasCentered;
    public ConstraintAnchor[] mListAnchors;
    public DimensionBehaviour[] mListDimensionBehaviors;
    protected ConstraintWidget[] mListNextMatchConstraintsWidget;
    public int mMatchConstraintDefaultHeight;
    public int mMatchConstraintDefaultWidth;
    public int mMatchConstraintMaxHeight;
    public int mMatchConstraintMaxWidth;
    public int mMatchConstraintMinHeight;
    public int mMatchConstraintMinWidth;
    public float mMatchConstraintPercentHeight;
    public float mMatchConstraintPercentWidth;
    private int[] mMaxDimension;
    private boolean mMeasureRequested;
    protected int mMinHeight;
    protected int mMinWidth;
    protected ConstraintWidget[] mNextChainWidget;
    protected int mOffsetX;
    protected int mOffsetY;
    private boolean mOptimizeWrapO;
    private boolean mOptimizeWrapOnResolved;
    public ConstraintWidget mParent;
    int mRelX;
    int mRelY;
    float mResolvedDimensionRatio;
    int mResolvedDimensionRatioSide;
    boolean mResolvedHasRatio;
    private boolean mResolvedHorizontal;
    public int[] mResolvedMatchConstraintDefault;
    private boolean mResolvedVertical;
    public ConstraintAnchor mRight;
    boolean mRightHasCentered;
    public ConstraintAnchor mTop;
    boolean mTopHasCentered;
    private String mType;
    float mVerticalBiasPercent;
    boolean mVerticalChainFixedPosition;
    int mVerticalChainStyle;
    ConstraintWidget mVerticalNextWidget;
    public int mVerticalResolution;
    public VerticalWidgetRun mVerticalRun;
    private boolean mVerticalSolvingPass;
    boolean mVerticalWrapVisited;
    private int mVisibility;
    public float[] mWeight;
    int mWidth;
    private int mWidthOverride;
    private int mWrapBehaviorInParent;
    protected int mX;
    protected int mY;
    public boolean measured;
    public WidgetRun[] run;
    public String stringId;
    public ChainRun verticalChainRun;
    public int verticalGroup;

    public enum DimensionBehaviour {
        FIXED,
        WRAP_CONTENT,
        MATCH_CONSTRAINT,
        MATCH_PARENT
    }

    public WidgetRun getRun(int orientation) {
        if (orientation == 0) {
            return this.mHorizontalRun;
        }
        if (orientation == 1) {
            return this.mVerticalRun;
        }
        return null;
    }

    public void setFinalFrame(int left, int top, int right, int bottom, int baseline, int orientation) {
        setFrame(left, top, right, bottom);
        setBaselineDistance(baseline);
        if (orientation == 0) {
            this.mResolvedHorizontal = true;
            this.mResolvedVertical = false;
        } else if (orientation == 1) {
            this.mResolvedHorizontal = false;
            this.mResolvedVertical = true;
        } else if (orientation == 2) {
            this.mResolvedHorizontal = true;
            this.mResolvedVertical = true;
        } else {
            this.mResolvedHorizontal = false;
            this.mResolvedVertical = false;
        }
    }

    public void setFinalLeft(int x1) {
        this.mLeft.setFinalValue(x1);
        this.mX = x1;
    }

    public void setFinalTop(int y1) {
        this.mTop.setFinalValue(y1);
        this.mY = y1;
    }

    public void resetSolvingPassFlag() {
        this.mHorizontalSolvingPass = false;
        this.mVerticalSolvingPass = false;
    }

    public boolean isHorizontalSolvingPassDone() {
        return this.mHorizontalSolvingPass;
    }

    public boolean isVerticalSolvingPassDone() {
        return this.mVerticalSolvingPass;
    }

    public void markHorizontalSolvingPassDone() {
        this.mHorizontalSolvingPass = true;
    }

    public void markVerticalSolvingPassDone() {
        this.mVerticalSolvingPass = true;
    }

    public void setFinalHorizontal(int x1, int x2) {
        if (!this.mResolvedHorizontal) {
            this.mLeft.setFinalValue(x1);
            this.mRight.setFinalValue(x2);
            this.mX = x1;
            this.mWidth = x2 - x1;
            this.mResolvedHorizontal = true;
        }
    }

    public void setFinalVertical(int y1, int y2) {
        if (!this.mResolvedVertical) {
            this.mTop.setFinalValue(y1);
            this.mBottom.setFinalValue(y2);
            this.mY = y1;
            this.mHeight = y2 - y1;
            if (this.mHasBaseline) {
                this.mBaseline.setFinalValue(this.mBaselineDistance + y1);
            }
            this.mResolvedVertical = true;
        }
    }

    public void setFinalBaseline(int baselineValue) {
        if (this.mHasBaseline) {
            int y1 = baselineValue - this.mBaselineDistance;
            this.mY = y1;
            this.mTop.setFinalValue(y1);
            this.mBottom.setFinalValue(this.mHeight + y1);
            this.mBaseline.setFinalValue(baselineValue);
            this.mResolvedVertical = true;
        }
    }

    public boolean isResolvedHorizontally() {
        return this.mResolvedHorizontal || (this.mLeft.hasFinalValue() && this.mRight.hasFinalValue());
    }

    public boolean isResolvedVertically() {
        return this.mResolvedVertical || (this.mTop.hasFinalValue() && this.mBottom.hasFinalValue());
    }

    public void resetFinalResolution() {
        this.mResolvedHorizontal = false;
        this.mResolvedVertical = false;
        this.mHorizontalSolvingPass = false;
        this.mVerticalSolvingPass = false;
        int mAnchorsSize = this.mAnchors.size();
        for (int i = 0; i < mAnchorsSize; i++) {
            this.mAnchors.get(i).resetFinalResolution();
        }
    }

    public void ensureMeasureRequested() {
        this.mMeasureRequested = true;
    }

    public boolean hasDependencies() {
        int mAnchorsSize = this.mAnchors.size();
        for (int i = 0; i < mAnchorsSize; i++) {
            if (this.mAnchors.get(i).hasDependents()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasDanglingDimension(int orientation) {
        if (orientation == 0) {
            if ((this.mLeft.mTarget != null ? 1 : 0) + (this.mRight.mTarget != null ? 1 : 0) < 2) {
                return true;
            }
            return false;
        }
        if ((this.mTop.mTarget != null ? 1 : 0) + (this.mBottom.mTarget != null ? 1 : 0) + (this.mBaseline.mTarget != null ? 1 : 0) < 2) {
            return true;
        }
        return false;
    }

    public boolean hasResolvedTargets(int orientation, int size) {
        if (orientation == 0) {
            if (this.mLeft.mTarget != null && this.mLeft.mTarget.hasFinalValue() && this.mRight.mTarget != null && this.mRight.mTarget.hasFinalValue()) {
                if ((this.mRight.mTarget.getFinalValue() - this.mRight.getMargin()) - (this.mLeft.mTarget.getFinalValue() + this.mLeft.getMargin()) >= size) {
                    return true;
                }
                return false;
            }
        } else if (this.mTop.mTarget != null && this.mTop.mTarget.hasFinalValue() && this.mBottom.mTarget != null && this.mBottom.mTarget.hasFinalValue()) {
            if ((this.mBottom.mTarget.getFinalValue() - this.mBottom.getMargin()) - (this.mTop.mTarget.getFinalValue() + this.mTop.getMargin()) >= size) {
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean isInVirtualLayout() {
        return this.mInVirtualLayout;
    }

    public void setInVirtualLayout(boolean inVirtualLayout) {
        this.mInVirtualLayout = inVirtualLayout;
    }

    public int getMaxHeight() {
        return this.mMaxDimension[1];
    }

    public int getMaxWidth() {
        return this.mMaxDimension[0];
    }

    public void setMaxWidth(int maxWidth) {
        this.mMaxDimension[0] = maxWidth;
    }

    public void setMaxHeight(int maxHeight) {
        this.mMaxDimension[1] = maxHeight;
    }

    public boolean isSpreadWidth() {
        return this.mMatchConstraintDefaultWidth == 0 && this.mDimensionRatio == 0.0f && this.mMatchConstraintMinWidth == 0 && this.mMatchConstraintMaxWidth == 0 && this.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT;
    }

    public boolean isSpreadHeight() {
        return this.mMatchConstraintDefaultHeight == 0 && this.mDimensionRatio == 0.0f && this.mMatchConstraintMinHeight == 0 && this.mMatchConstraintMaxHeight == 0 && this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT;
    }

    public void setHasBaseline(boolean hasBaseline) {
        this.mHasBaseline = hasBaseline;
    }

    public boolean getHasBaseline() {
        return this.mHasBaseline;
    }

    public boolean isInPlaceholder() {
        return this.mInPlaceholder;
    }

    public void setInPlaceholder(boolean inPlaceholder) {
        this.mInPlaceholder = inPlaceholder;
    }

    /* access modifiers changed from: protected */
    public void setInBarrier(int orientation, boolean value) {
        this.mIsInBarrier[orientation] = value;
    }

    public boolean isInBarrier(int orientation) {
        return this.mIsInBarrier[orientation];
    }

    public void setMeasureRequested(boolean measureRequested) {
        this.mMeasureRequested = measureRequested;
    }

    public boolean isMeasureRequested() {
        return this.mMeasureRequested && this.mVisibility != 8;
    }

    public void setWrapBehaviorInParent(int behavior) {
        if (behavior >= 0 && behavior <= 3) {
            this.mWrapBehaviorInParent = behavior;
        }
    }

    public int getWrapBehaviorInParent() {
        return this.mWrapBehaviorInParent;
    }

    public int getLastHorizontalMeasureSpec() {
        return this.mLastHorizontalMeasureSpec;
    }

    public int getLastVerticalMeasureSpec() {
        return this.mLastVerticalMeasureSpec;
    }

    public void setLastMeasureSpec(int horizontal, int vertical) {
        this.mLastHorizontalMeasureSpec = horizontal;
        this.mLastVerticalMeasureSpec = vertical;
        setMeasureRequested(false);
    }

    public void reset() {
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mParent = null;
        this.mCircleConstraintAngle = Float.NaN;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.mHorizontalBiasPercent = DEFAULT_BIAS;
        this.mVerticalBiasPercent = DEFAULT_BIAS;
        this.mListDimensionBehaviors[0] = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors[1] = DimensionBehaviour.FIXED;
        this.mCompanionWidget = null;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mType = null;
        this.mHorizontalWrapVisited = false;
        this.mVerticalWrapVisited = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mHorizontalChainFixedPosition = false;
        this.mVerticalChainFixedPosition = false;
        this.mWeight[0] = -1.0f;
        this.mWeight[1] = -1.0f;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mMaxDimension[0] = Integer.MAX_VALUE;
        this.mMaxDimension[1] = Integer.MAX_VALUE;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mMatchConstraintMaxWidth = Integer.MAX_VALUE;
        this.mMatchConstraintMaxHeight = Integer.MAX_VALUE;
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMinHeight = 0;
        this.mResolvedHasRatio = false;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mGroupsToSolver = false;
        this.isTerminalWidget[0] = true;
        this.isTerminalWidget[1] = true;
        this.mInVirtualLayout = false;
        this.mIsInBarrier[0] = false;
        this.mIsInBarrier[1] = false;
        this.mMeasureRequested = true;
        this.mResolvedMatchConstraintDefault[0] = 0;
        this.mResolvedMatchConstraintDefault[1] = 0;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
    }

    private void serializeAnchor(StringBuilder ret, String side, ConstraintAnchor a) {
        if (a.mTarget != null) {
            ret.append(side);
            ret.append(" : [ '");
            ret.append(a.mTarget);
            ret.append("',");
            ret.append(a.mMargin);
            ret.append(",");
            ret.append(a.mGoneMargin);
            ret.append(",");
            ret.append(" ] ,\n");
        }
    }

    private void serializeCircle(StringBuilder ret, ConstraintAnchor a, float angle) {
        if (a.mTarget != null && !Float.isNaN(angle)) {
            ret.append("circle : [ '");
            ret.append(a.mTarget);
            ret.append("',");
            ret.append(a.mMargin);
            ret.append(",");
            ret.append(angle);
            ret.append(",");
            ret.append(" ] ,\n");
        }
    }

    private void serializeAttribute(StringBuilder ret, String type, float value, float def) {
        if (value != def) {
            ret.append(type);
            ret.append(" :   ");
            ret.append(value);
            ret.append(",\n");
        }
    }

    private void serializeAttribute(StringBuilder ret, String type, int value, int def) {
        if (value != def) {
            ret.append(type);
            ret.append(" :   ");
            ret.append(value);
            ret.append(",\n");
        }
    }

    private void serializeAttribute(StringBuilder ret, String type, String value, String def) {
        if (!def.equals(value)) {
            ret.append(type);
            ret.append(" :   ");
            ret.append(value);
            ret.append(",\n");
        }
    }

    private void serializeDimensionRatio(StringBuilder ret, String type, float value, int whichSide) {
        if (value != 0.0f) {
            ret.append(type);
            ret.append(" :  [");
            ret.append(value);
            ret.append(",");
            ret.append(whichSide);
            ret.append("");
            ret.append("],\n");
        }
    }

    private void serializeSize(StringBuilder ret, String type, int size, int min, int max, int override, int matchConstraintMin, int matchConstraintDefault, float matchConstraintPercent, float weight) {
        ret.append(type);
        ret.append(" :  {\n");
        serializeAttribute(ret, "size", size, Integer.MIN_VALUE);
        serializeAttribute(ret, "min", min, 0);
        serializeAttribute(ret, "max", max, Integer.MAX_VALUE);
        serializeAttribute(ret, "matchMin", matchConstraintMin, 0);
        serializeAttribute(ret, "matchDef", matchConstraintDefault, 0);
        serializeAttribute(ret, "matchPercent", matchConstraintDefault, 1);
        serializeAttribute(ret, "matchConstraintPercent", matchConstraintPercent, 1.0f);
        serializeAttribute(ret, "weight", weight, 1.0f);
        serializeAttribute(ret, "override", override, 1);
        ret.append("},\n");
    }

    public StringBuilder serialize(StringBuilder ret) {
        ret.append("{\n");
        serializeAnchor(ret, "left", this.mLeft);
        serializeAnchor(ret, "top", this.mTop);
        serializeAnchor(ret, "right", this.mRight);
        serializeAnchor(ret, "bottom", this.mBottom);
        serializeAnchor(ret, "baseline", this.mBaseline);
        serializeAnchor(ret, "centerX", this.mCenterX);
        serializeAnchor(ret, "centerY", this.mCenterY);
        serializeCircle(ret, this.mCenter, this.mCircleConstraintAngle);
        StringBuilder sb = ret;
        serializeSize(sb, "width", this.mWidth, this.mMinWidth, this.mMaxDimension[0], this.mWidthOverride, this.mMatchConstraintMinWidth, this.mMatchConstraintDefaultWidth, this.mMatchConstraintPercentWidth, this.mWeight[0]);
        serializeSize(ret, "height", this.mHeight, this.mMinHeight, this.mMaxDimension[1], this.mHeightOverride, this.mMatchConstraintMinHeight, this.mMatchConstraintDefaultHeight, this.mMatchConstraintPercentHeight, this.mWeight[1]);
        serializeDimensionRatio(ret, "dimensionRatio", this.mDimensionRatio, this.mDimensionRatioSide);
        serializeAttribute(ret, "horizontalBias", this.mHorizontalBiasPercent, DEFAULT_BIAS);
        serializeAttribute(ret, "verticalBias", this.mVerticalBiasPercent, DEFAULT_BIAS);
        ret.append("}\n");
        return ret;
    }

    public boolean oppositeDimensionDependsOn(int orientation) {
        int oppositeOrientation = orientation == 0 ? 1 : 0;
        DimensionBehaviour dimensionBehaviour = this.mListDimensionBehaviors[orientation];
        DimensionBehaviour oppositeDimensionBehaviour = this.mListDimensionBehaviors[oppositeOrientation];
        if (dimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT && oppositeDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
            return true;
        }
        return false;
    }

    public boolean oppositeDimensionsTied() {
        return this.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT && this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT;
    }

    public boolean hasDimensionOverride() {
        return (this.mWidthOverride == -1 && this.mHeightOverride == -1) ? false : true;
    }

    public ConstraintWidget() {
        this.measured = false;
        this.run = new WidgetRun[2];
        this.mHorizontalRun = null;
        this.mVerticalRun = null;
        this.isTerminalWidget = new boolean[]{true, true};
        this.mResolvedHasRatio = false;
        this.mMeasureRequested = true;
        this.mOptimizeWrapO = false;
        this.mOptimizeWrapOnResolved = true;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
        this.frame = new WidgetFrame(this);
        this.mResolvedHorizontal = false;
        this.mResolvedVertical = false;
        this.mHorizontalSolvingPass = false;
        this.mVerticalSolvingPass = false;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mWrapBehaviorInParent = 0;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mResolvedMatchConstraintDefault = new int[2];
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mMaxDimension = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.mCircleConstraintAngle = Float.NaN;
        this.mHasBaseline = false;
        this.mInVirtualLayout = false;
        this.mLastHorizontalMeasureSpec = 0;
        this.mLastVerticalMeasureSpec = 0;
        this.mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        this.mCenter = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mListAnchors = new ConstraintAnchor[]{this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, this.mCenter};
        this.mAnchors = new ArrayList<>();
        this.mIsInBarrier = new boolean[2];
        this.mListDimensionBehaviors = new DimensionBehaviour[]{DimensionBehaviour.FIXED, DimensionBehaviour.FIXED};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mRelX = 0;
        this.mRelY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        this.mHorizontalBiasPercent = DEFAULT_BIAS;
        this.mVerticalBiasPercent = DEFAULT_BIAS;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mAnimated = false;
        this.mDebugName = null;
        this.mType = null;
        this.mGroupsToSolver = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.horizontalGroup = -1;
        this.verticalGroup = -1;
        addAnchors();
    }

    public ConstraintWidget(String debugName) {
        this.measured = false;
        this.run = new WidgetRun[2];
        this.mHorizontalRun = null;
        this.mVerticalRun = null;
        this.isTerminalWidget = new boolean[]{true, true};
        this.mResolvedHasRatio = false;
        this.mMeasureRequested = true;
        this.mOptimizeWrapO = false;
        this.mOptimizeWrapOnResolved = true;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
        this.frame = new WidgetFrame(this);
        this.mResolvedHorizontal = false;
        this.mResolvedVertical = false;
        this.mHorizontalSolvingPass = false;
        this.mVerticalSolvingPass = false;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mWrapBehaviorInParent = 0;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mResolvedMatchConstraintDefault = new int[2];
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mMaxDimension = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.mCircleConstraintAngle = Float.NaN;
        this.mHasBaseline = false;
        this.mInVirtualLayout = false;
        this.mLastHorizontalMeasureSpec = 0;
        this.mLastVerticalMeasureSpec = 0;
        this.mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        this.mCenter = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mListAnchors = new ConstraintAnchor[]{this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, this.mCenter};
        this.mAnchors = new ArrayList<>();
        this.mIsInBarrier = new boolean[2];
        this.mListDimensionBehaviors = new DimensionBehaviour[]{DimensionBehaviour.FIXED, DimensionBehaviour.FIXED};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mRelX = 0;
        this.mRelY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        this.mHorizontalBiasPercent = DEFAULT_BIAS;
        this.mVerticalBiasPercent = DEFAULT_BIAS;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mAnimated = false;
        this.mDebugName = null;
        this.mType = null;
        this.mGroupsToSolver = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.horizontalGroup = -1;
        this.verticalGroup = -1;
        addAnchors();
        setDebugName(debugName);
    }

    public ConstraintWidget(int x, int y, int width, int height) {
        this.measured = false;
        this.run = new WidgetRun[2];
        this.mHorizontalRun = null;
        this.mVerticalRun = null;
        this.isTerminalWidget = new boolean[]{true, true};
        this.mResolvedHasRatio = false;
        this.mMeasureRequested = true;
        this.mOptimizeWrapO = false;
        this.mOptimizeWrapOnResolved = true;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
        this.frame = new WidgetFrame(this);
        this.mResolvedHorizontal = false;
        this.mResolvedVertical = false;
        this.mHorizontalSolvingPass = false;
        this.mVerticalSolvingPass = false;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mWrapBehaviorInParent = 0;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mResolvedMatchConstraintDefault = new int[2];
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mMaxDimension = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.mCircleConstraintAngle = Float.NaN;
        this.mHasBaseline = false;
        this.mInVirtualLayout = false;
        this.mLastHorizontalMeasureSpec = 0;
        this.mLastVerticalMeasureSpec = 0;
        this.mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        this.mCenter = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mListAnchors = new ConstraintAnchor[]{this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, this.mCenter};
        this.mAnchors = new ArrayList<>();
        this.mIsInBarrier = new boolean[2];
        this.mListDimensionBehaviors = new DimensionBehaviour[]{DimensionBehaviour.FIXED, DimensionBehaviour.FIXED};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mRelX = 0;
        this.mRelY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        this.mHorizontalBiasPercent = DEFAULT_BIAS;
        this.mVerticalBiasPercent = DEFAULT_BIAS;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mAnimated = false;
        this.mDebugName = null;
        this.mType = null;
        this.mGroupsToSolver = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.horizontalGroup = -1;
        this.verticalGroup = -1;
        this.mX = x;
        this.mY = y;
        this.mWidth = width;
        this.mHeight = height;
        addAnchors();
    }

    public ConstraintWidget(String debugName, int x, int y, int width, int height) {
        this(x, y, width, height);
        setDebugName(debugName);
    }

    public ConstraintWidget(int width, int height) {
        this(0, 0, width, height);
    }

    public void ensureWidgetRuns() {
        if (this.mHorizontalRun == null) {
            this.mHorizontalRun = new HorizontalWidgetRun(this);
        }
        if (this.mVerticalRun == null) {
            this.mVerticalRun = new VerticalWidgetRun(this);
        }
    }

    public ConstraintWidget(String debugName, int width, int height) {
        this(width, height);
        setDebugName(debugName);
    }

    public void resetSolverVariables(Cache cache) {
        this.mLeft.resetSolverVariable(cache);
        this.mTop.resetSolverVariable(cache);
        this.mRight.resetSolverVariable(cache);
        this.mBottom.resetSolverVariable(cache);
        this.mBaseline.resetSolverVariable(cache);
        this.mCenter.resetSolverVariable(cache);
        this.mCenterX.resetSolverVariable(cache);
        this.mCenterY.resetSolverVariable(cache);
    }

    private void addAnchors() {
        this.mAnchors.add(this.mLeft);
        this.mAnchors.add(this.mTop);
        this.mAnchors.add(this.mRight);
        this.mAnchors.add(this.mBottom);
        this.mAnchors.add(this.mCenterX);
        this.mAnchors.add(this.mCenterY);
        this.mAnchors.add(this.mCenter);
        this.mAnchors.add(this.mBaseline);
    }

    public boolean isRoot() {
        return this.mParent == null;
    }

    public ConstraintWidget getParent() {
        return this.mParent;
    }

    public void setParent(ConstraintWidget widget) {
        this.mParent = widget;
    }

    public void setWidthWrapContent(boolean widthWrapContent) {
        this.mIsWidthWrapContent = widthWrapContent;
    }

    public boolean isWidthWrapContent() {
        return this.mIsWidthWrapContent;
    }

    public void setHeightWrapContent(boolean heightWrapContent) {
        this.mIsHeightWrapContent = heightWrapContent;
    }

    public boolean isHeightWrapContent() {
        return this.mIsHeightWrapContent;
    }

    public void connectCircularConstraint(ConstraintWidget target, float angle, int radius) {
        immediateConnect(ConstraintAnchor.Type.CENTER, target, ConstraintAnchor.Type.CENTER, radius, 0);
        this.mCircleConstraintAngle = angle;
    }

    public String getType() {
        return this.mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public void setVisibility(int visibility) {
        this.mVisibility = visibility;
    }

    public int getVisibility() {
        return this.mVisibility;
    }

    public void setAnimated(boolean animated) {
        this.mAnimated = animated;
    }

    public boolean isAnimated() {
        return this.mAnimated;
    }

    public String getDebugName() {
        return this.mDebugName;
    }

    public void setDebugName(String name) {
        this.mDebugName = name;
    }

    public void setDebugSolverName(LinearSystem system, String name) {
        this.mDebugName = name;
        SolverVariable left = system.createObjectVariable(this.mLeft);
        SolverVariable top = system.createObjectVariable(this.mTop);
        SolverVariable right = system.createObjectVariable(this.mRight);
        SolverVariable bottom = system.createObjectVariable(this.mBottom);
        left.setName(name + ".left");
        top.setName(name + ".top");
        right.setName(name + ".right");
        bottom.setName(name + ".bottom");
        system.createObjectVariable(this.mBaseline).setName(name + ".baseline");
    }

    public void createObjectVariables(LinearSystem system) {
        system.createObjectVariable(this.mLeft);
        system.createObjectVariable(this.mTop);
        system.createObjectVariable(this.mRight);
        system.createObjectVariable(this.mBottom);
        if (this.mBaselineDistance > 0) {
            system.createObjectVariable(this.mBaseline);
        }
    }

    public String toString() {
        String str = "";
        StringBuilder append = new StringBuilder().append(this.mType != null ? "type: " + this.mType + " " : str);
        if (this.mDebugName != null) {
            str = "id: " + this.mDebugName + " ";
        }
        return append.append(str).append("(").append(this.mX).append(", ").append(this.mY).append(") - (").append(this.mWidth).append(" x ").append(this.mHeight).append(")").toString();
    }

    public int getX() {
        if (this.mParent == null || !(this.mParent instanceof ConstraintWidgetContainer)) {
            return this.mX;
        }
        return ((ConstraintWidgetContainer) this.mParent).mPaddingLeft + this.mX;
    }

    public int getY() {
        if (this.mParent == null || !(this.mParent instanceof ConstraintWidgetContainer)) {
            return this.mY;
        }
        return ((ConstraintWidgetContainer) this.mParent).mPaddingTop + this.mY;
    }

    public int getWidth() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mWidth;
    }

    public int getOptimizerWrapWidth() {
        int w;
        int w2 = this.mWidth;
        if (this.mListDimensionBehaviors[0] != DimensionBehaviour.MATCH_CONSTRAINT) {
            return w2;
        }
        if (this.mMatchConstraintDefaultWidth == 1) {
            w = Math.max(this.mMatchConstraintMinWidth, w2);
        } else if (this.mMatchConstraintMinWidth > 0) {
            w = this.mMatchConstraintMinWidth;
            this.mWidth = w;
        } else {
            w = 0;
        }
        if (this.mMatchConstraintMaxWidth <= 0 || this.mMatchConstraintMaxWidth >= w) {
            return w;
        }
        return this.mMatchConstraintMaxWidth;
    }

    public int getOptimizerWrapHeight() {
        int h;
        int h2 = this.mHeight;
        if (this.mListDimensionBehaviors[1] != DimensionBehaviour.MATCH_CONSTRAINT) {
            return h2;
        }
        if (this.mMatchConstraintDefaultHeight == 1) {
            h = Math.max(this.mMatchConstraintMinHeight, h2);
        } else if (this.mMatchConstraintMinHeight > 0) {
            h = this.mMatchConstraintMinHeight;
            this.mHeight = h;
        } else {
            h = 0;
        }
        if (this.mMatchConstraintMaxHeight <= 0 || this.mMatchConstraintMaxHeight >= h) {
            return h;
        }
        return this.mMatchConstraintMaxHeight;
    }

    public int getHeight() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mHeight;
    }

    public int getLength(int orientation) {
        if (orientation == 0) {
            return getWidth();
        }
        if (orientation == 1) {
            return getHeight();
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int getRootX() {
        return this.mX + this.mOffsetX;
    }

    /* access modifiers changed from: protected */
    public int getRootY() {
        return this.mY + this.mOffsetY;
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public int getLeft() {
        return getX();
    }

    public int getTop() {
        return getY();
    }

    public int getRight() {
        return getX() + this.mWidth;
    }

    public int getBottom() {
        return getY() + this.mHeight;
    }

    public int getHorizontalMargin() {
        int margin = 0;
        if (this.mLeft != null) {
            margin = 0 + this.mLeft.mMargin;
        }
        if (this.mRight != null) {
            return margin + this.mRight.mMargin;
        }
        return margin;
    }

    public int getVerticalMargin() {
        int margin = 0;
        if (this.mLeft != null) {
            margin = 0 + this.mTop.mMargin;
        }
        if (this.mRight != null) {
            return margin + this.mBottom.mMargin;
        }
        return margin;
    }

    public float getHorizontalBiasPercent() {
        return this.mHorizontalBiasPercent;
    }

    public float getVerticalBiasPercent() {
        return this.mVerticalBiasPercent;
    }

    public float getBiasPercent(int orientation) {
        if (orientation == 0) {
            return this.mHorizontalBiasPercent;
        }
        if (orientation == 1) {
            return this.mVerticalBiasPercent;
        }
        return -1.0f;
    }

    public boolean hasBaseline() {
        return this.mHasBaseline;
    }

    public int getBaselineDistance() {
        return this.mBaselineDistance;
    }

    public Object getCompanionWidget() {
        return this.mCompanionWidget;
    }

    public ArrayList<ConstraintAnchor> getAnchors() {
        return this.mAnchors;
    }

    public void setX(int x) {
        this.mX = x;
    }

    public void setY(int y) {
        this.mY = y;
    }

    public void setOrigin(int x, int y) {
        this.mX = x;
        this.mY = y;
    }

    public void setOffset(int x, int y) {
        this.mOffsetX = x;
        this.mOffsetY = y;
    }

    public void setGoneMargin(ConstraintAnchor.Type type, int goneMargin) {
        switch (type) {
            case LEFT:
                this.mLeft.mGoneMargin = goneMargin;
                return;
            case TOP:
                this.mTop.mGoneMargin = goneMargin;
                return;
            case RIGHT:
                this.mRight.mGoneMargin = goneMargin;
                return;
            case BOTTOM:
                this.mBottom.mGoneMargin = goneMargin;
                return;
            case BASELINE:
                this.mBaseline.mGoneMargin = goneMargin;
                return;
            default:
                return;
        }
    }

    public void setWidth(int w) {
        this.mWidth = w;
        if (this.mWidth < this.mMinWidth) {
            this.mWidth = this.mMinWidth;
        }
    }

    public void setHeight(int h) {
        this.mHeight = h;
        if (this.mHeight < this.mMinHeight) {
            this.mHeight = this.mMinHeight;
        }
    }

    public void setLength(int length, int orientation) {
        if (orientation == 0) {
            setWidth(length);
        } else if (orientation == 1) {
            setHeight(length);
        }
    }

    public void setHorizontalMatchStyle(int horizontalMatchStyle, int min, int max, float percent) {
        this.mMatchConstraintDefaultWidth = horizontalMatchStyle;
        this.mMatchConstraintMinWidth = min;
        this.mMatchConstraintMaxWidth = max == Integer.MAX_VALUE ? 0 : max;
        this.mMatchConstraintPercentWidth = percent;
        if (percent > 0.0f && percent < 1.0f && this.mMatchConstraintDefaultWidth == 0) {
            this.mMatchConstraintDefaultWidth = 2;
        }
    }

    public void setVerticalMatchStyle(int verticalMatchStyle, int min, int max, float percent) {
        this.mMatchConstraintDefaultHeight = verticalMatchStyle;
        this.mMatchConstraintMinHeight = min;
        this.mMatchConstraintMaxHeight = max == Integer.MAX_VALUE ? 0 : max;
        this.mMatchConstraintPercentHeight = percent;
        if (percent > 0.0f && percent < 1.0f && this.mMatchConstraintDefaultHeight == 0) {
            this.mMatchConstraintDefaultHeight = 2;
        }
    }

    public void setDimensionRatio(String ratio) {
        int commaIndex;
        if (ratio == null || ratio.length() == 0) {
            this.mDimensionRatio = 0.0f;
            return;
        }
        int dimensionRatioSide = -1;
        float dimensionRatio = 0.0f;
        int len = ratio.length();
        int commaIndex2 = ratio.indexOf(44);
        if (commaIndex2 <= 0 || commaIndex2 >= len - 1) {
            commaIndex = 0;
        } else {
            String dimension = ratio.substring(0, commaIndex2);
            if (dimension.equalsIgnoreCase("W")) {
                dimensionRatioSide = 0;
            } else if (dimension.equalsIgnoreCase("H")) {
                dimensionRatioSide = 1;
            }
            commaIndex = commaIndex2 + 1;
        }
        int colonIndex = ratio.indexOf(58);
        if (colonIndex < 0 || colonIndex >= len - 1) {
            String r = ratio.substring(commaIndex);
            if (r.length() > 0) {
                try {
                    dimensionRatio = Float.parseFloat(r);
                } catch (NumberFormatException e) {
                }
            }
        } else {
            String nominator = ratio.substring(commaIndex, colonIndex);
            String denominator = ratio.substring(colonIndex + 1);
            if (nominator.length() > 0 && denominator.length() > 0) {
                try {
                    float nominatorValue = Float.parseFloat(nominator);
                    float denominatorValue = Float.parseFloat(denominator);
                    if (nominatorValue > 0.0f && denominatorValue > 0.0f) {
                        dimensionRatio = dimensionRatioSide == 1 ? Math.abs(denominatorValue / nominatorValue) : Math.abs(nominatorValue / denominatorValue);
                    }
                } catch (NumberFormatException e2) {
                }
            }
        }
        if (dimensionRatio > 0.0f) {
            this.mDimensionRatio = dimensionRatio;
            this.mDimensionRatioSide = dimensionRatioSide;
        }
    }

    public void setDimensionRatio(float ratio, int dimensionRatioSide) {
        this.mDimensionRatio = ratio;
        this.mDimensionRatioSide = dimensionRatioSide;
    }

    public float getDimensionRatio() {
        return this.mDimensionRatio;
    }

    public int getDimensionRatioSide() {
        return this.mDimensionRatioSide;
    }

    public void setHorizontalBiasPercent(float horizontalBiasPercent) {
        this.mHorizontalBiasPercent = horizontalBiasPercent;
    }

    public void setVerticalBiasPercent(float verticalBiasPercent) {
        this.mVerticalBiasPercent = verticalBiasPercent;
    }

    public void setMinWidth(int w) {
        if (w < 0) {
            this.mMinWidth = 0;
        } else {
            this.mMinWidth = w;
        }
    }

    public void setMinHeight(int h) {
        if (h < 0) {
            this.mMinHeight = 0;
        } else {
            this.mMinHeight = h;
        }
    }

    public void setDimension(int w, int h) {
        this.mWidth = w;
        if (this.mWidth < this.mMinWidth) {
            this.mWidth = this.mMinWidth;
        }
        this.mHeight = h;
        if (this.mHeight < this.mMinHeight) {
            this.mHeight = this.mMinHeight;
        }
    }

    public void setFrame(int left, int top, int right, int bottom) {
        int w = right - left;
        int h = bottom - top;
        this.mX = left;
        this.mY = top;
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        if (this.mListDimensionBehaviors[0] == DimensionBehaviour.FIXED && w < this.mWidth) {
            w = this.mWidth;
        }
        if (this.mListDimensionBehaviors[1] == DimensionBehaviour.FIXED && h < this.mHeight) {
            h = this.mHeight;
        }
        this.mWidth = w;
        this.mHeight = h;
        if (this.mHeight < this.mMinHeight) {
            this.mHeight = this.mMinHeight;
        }
        if (this.mWidth < this.mMinWidth) {
            this.mWidth = this.mMinWidth;
        }
        if (this.mMatchConstraintMaxWidth > 0 && this.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT) {
            this.mWidth = Math.min(this.mWidth, this.mMatchConstraintMaxWidth);
        }
        if (this.mMatchConstraintMaxHeight > 0 && this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT) {
            this.mHeight = Math.min(this.mHeight, this.mMatchConstraintMaxHeight);
        }
        if (w != this.mWidth) {
            this.mWidthOverride = this.mWidth;
        }
        if (h != this.mHeight) {
            this.mHeightOverride = this.mHeight;
        }
    }

    public void setFrame(int start, int end, int orientation) {
        if (orientation == 0) {
            setHorizontalDimension(start, end);
        } else if (orientation == 1) {
            setVerticalDimension(start, end);
        }
    }

    public void setHorizontalDimension(int left, int right) {
        this.mX = left;
        this.mWidth = right - left;
        if (this.mWidth < this.mMinWidth) {
            this.mWidth = this.mMinWidth;
        }
    }

    public void setVerticalDimension(int top, int bottom) {
        this.mY = top;
        this.mHeight = bottom - top;
        if (this.mHeight < this.mMinHeight) {
            this.mHeight = this.mMinHeight;
        }
    }

    /* access modifiers changed from: package-private */
    public int getRelativePositioning(int orientation) {
        if (orientation == 0) {
            return this.mRelX;
        }
        if (orientation == 1) {
            return this.mRelY;
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public void setRelativePositioning(int offset, int orientation) {
        if (orientation == 0) {
            this.mRelX = offset;
        } else if (orientation == 1) {
            this.mRelY = offset;
        }
    }

    public void setBaselineDistance(int baseline) {
        this.mBaselineDistance = baseline;
        this.mHasBaseline = baseline > 0;
    }

    public void setCompanionWidget(Object companion) {
        this.mCompanionWidget = companion;
    }

    public void setContainerItemSkip(int skip) {
        if (skip >= 0) {
            this.mContainerItemSkip = skip;
        } else {
            this.mContainerItemSkip = 0;
        }
    }

    public int getContainerItemSkip() {
        return this.mContainerItemSkip;
    }

    public void setHorizontalWeight(float horizontalWeight) {
        this.mWeight[0] = horizontalWeight;
    }

    public void setVerticalWeight(float verticalWeight) {
        this.mWeight[1] = verticalWeight;
    }

    public void setHorizontalChainStyle(int horizontalChainStyle) {
        this.mHorizontalChainStyle = horizontalChainStyle;
    }

    public int getHorizontalChainStyle() {
        return this.mHorizontalChainStyle;
    }

    public void setVerticalChainStyle(int verticalChainStyle) {
        this.mVerticalChainStyle = verticalChainStyle;
    }

    public int getVerticalChainStyle() {
        return this.mVerticalChainStyle;
    }

    public boolean allowedInBarrier() {
        return this.mVisibility != 8;
    }

    public void immediateConnect(ConstraintAnchor.Type startType, ConstraintWidget target, ConstraintAnchor.Type endType, int margin, int goneMargin) {
        getAnchor(startType).connect(target.getAnchor(endType), margin, goneMargin, true);
    }

    public void connect(ConstraintAnchor from, ConstraintAnchor to, int margin) {
        if (from.getOwner() == this) {
            connect(from.getType(), to.getOwner(), to.getType(), margin);
        }
    }

    public void connect(ConstraintAnchor.Type constraintFrom, ConstraintWidget target, ConstraintAnchor.Type constraintTo) {
        connect(constraintFrom, target, constraintTo, 0);
    }

    public void connect(ConstraintAnchor.Type constraintFrom, ConstraintWidget target, ConstraintAnchor.Type constraintTo, int margin) {
        if (constraintFrom == ConstraintAnchor.Type.CENTER) {
            if (constraintTo == ConstraintAnchor.Type.CENTER) {
                ConstraintAnchor left = getAnchor(ConstraintAnchor.Type.LEFT);
                ConstraintAnchor right = getAnchor(ConstraintAnchor.Type.RIGHT);
                ConstraintAnchor top = getAnchor(ConstraintAnchor.Type.TOP);
                ConstraintAnchor bottom = getAnchor(ConstraintAnchor.Type.BOTTOM);
                boolean centerX = false;
                boolean centerY = false;
                if ((left == null || !left.isConnected()) && (right == null || !right.isConnected())) {
                    connect(ConstraintAnchor.Type.LEFT, target, ConstraintAnchor.Type.LEFT, 0);
                    connect(ConstraintAnchor.Type.RIGHT, target, ConstraintAnchor.Type.RIGHT, 0);
                    centerX = true;
                }
                if ((top == null || !top.isConnected()) && (bottom == null || !bottom.isConnected())) {
                    connect(ConstraintAnchor.Type.TOP, target, ConstraintAnchor.Type.TOP, 0);
                    connect(ConstraintAnchor.Type.BOTTOM, target, ConstraintAnchor.Type.BOTTOM, 0);
                    centerY = true;
                }
                if (centerX && centerY) {
                    getAnchor(ConstraintAnchor.Type.CENTER).connect(target.getAnchor(ConstraintAnchor.Type.CENTER), 0);
                } else if (centerX) {
                    getAnchor(ConstraintAnchor.Type.CENTER_X).connect(target.getAnchor(ConstraintAnchor.Type.CENTER_X), 0);
                } else if (centerY) {
                    getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(target.getAnchor(ConstraintAnchor.Type.CENTER_Y), 0);
                }
            } else if (constraintTo == ConstraintAnchor.Type.LEFT || constraintTo == ConstraintAnchor.Type.RIGHT) {
                connect(ConstraintAnchor.Type.LEFT, target, constraintTo, 0);
                connect(ConstraintAnchor.Type.RIGHT, target, constraintTo, 0);
                getAnchor(ConstraintAnchor.Type.CENTER).connect(target.getAnchor(constraintTo), 0);
            } else if (constraintTo == ConstraintAnchor.Type.TOP || constraintTo == ConstraintAnchor.Type.BOTTOM) {
                connect(ConstraintAnchor.Type.TOP, target, constraintTo, 0);
                connect(ConstraintAnchor.Type.BOTTOM, target, constraintTo, 0);
                getAnchor(ConstraintAnchor.Type.CENTER).connect(target.getAnchor(constraintTo), 0);
            }
        } else if (constraintFrom == ConstraintAnchor.Type.CENTER_X && (constraintTo == ConstraintAnchor.Type.LEFT || constraintTo == ConstraintAnchor.Type.RIGHT)) {
            ConstraintAnchor left2 = getAnchor(ConstraintAnchor.Type.LEFT);
            ConstraintAnchor targetAnchor = target.getAnchor(constraintTo);
            ConstraintAnchor right2 = getAnchor(ConstraintAnchor.Type.RIGHT);
            left2.connect(targetAnchor, 0);
            right2.connect(targetAnchor, 0);
            getAnchor(ConstraintAnchor.Type.CENTER_X).connect(targetAnchor, 0);
        } else if (constraintFrom == ConstraintAnchor.Type.CENTER_Y && (constraintTo == ConstraintAnchor.Type.TOP || constraintTo == ConstraintAnchor.Type.BOTTOM)) {
            ConstraintAnchor targetAnchor2 = target.getAnchor(constraintTo);
            getAnchor(ConstraintAnchor.Type.TOP).connect(targetAnchor2, 0);
            getAnchor(ConstraintAnchor.Type.BOTTOM).connect(targetAnchor2, 0);
            getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(targetAnchor2, 0);
        } else if (constraintFrom == ConstraintAnchor.Type.CENTER_X && constraintTo == ConstraintAnchor.Type.CENTER_X) {
            getAnchor(ConstraintAnchor.Type.LEFT).connect(target.getAnchor(ConstraintAnchor.Type.LEFT), 0);
            getAnchor(ConstraintAnchor.Type.RIGHT).connect(target.getAnchor(ConstraintAnchor.Type.RIGHT), 0);
            getAnchor(ConstraintAnchor.Type.CENTER_X).connect(target.getAnchor(constraintTo), 0);
        } else if (constraintFrom == ConstraintAnchor.Type.CENTER_Y && constraintTo == ConstraintAnchor.Type.CENTER_Y) {
            getAnchor(ConstraintAnchor.Type.TOP).connect(target.getAnchor(ConstraintAnchor.Type.TOP), 0);
            getAnchor(ConstraintAnchor.Type.BOTTOM).connect(target.getAnchor(ConstraintAnchor.Type.BOTTOM), 0);
            getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(target.getAnchor(constraintTo), 0);
        } else {
            ConstraintAnchor fromAnchor = getAnchor(constraintFrom);
            ConstraintAnchor toAnchor = target.getAnchor(constraintTo);
            if (fromAnchor.isValidConnection(toAnchor)) {
                if (constraintFrom == ConstraintAnchor.Type.BASELINE) {
                    ConstraintAnchor top2 = getAnchor(ConstraintAnchor.Type.TOP);
                    ConstraintAnchor bottom2 = getAnchor(ConstraintAnchor.Type.BOTTOM);
                    if (top2 != null) {
                        top2.reset();
                    }
                    if (bottom2 != null) {
                        bottom2.reset();
                    }
                } else if (constraintFrom == ConstraintAnchor.Type.TOP || constraintFrom == ConstraintAnchor.Type.BOTTOM) {
                    ConstraintAnchor baseline = getAnchor(ConstraintAnchor.Type.BASELINE);
                    if (baseline != null) {
                        baseline.reset();
                    }
                    ConstraintAnchor center = getAnchor(ConstraintAnchor.Type.CENTER);
                    if (center.getTarget() != toAnchor) {
                        center.reset();
                    }
                    ConstraintAnchor opposite = getAnchor(constraintFrom).getOpposite();
                    ConstraintAnchor centerY2 = getAnchor(ConstraintAnchor.Type.CENTER_Y);
                    if (centerY2.isConnected()) {
                        opposite.reset();
                        centerY2.reset();
                    }
                } else if (constraintFrom == ConstraintAnchor.Type.LEFT || constraintFrom == ConstraintAnchor.Type.RIGHT) {
                    ConstraintAnchor center2 = getAnchor(ConstraintAnchor.Type.CENTER);
                    if (center2.getTarget() != toAnchor) {
                        center2.reset();
                    }
                    ConstraintAnchor opposite2 = getAnchor(constraintFrom).getOpposite();
                    ConstraintAnchor centerX2 = getAnchor(ConstraintAnchor.Type.CENTER_X);
                    if (centerX2.isConnected()) {
                        opposite2.reset();
                        centerX2.reset();
                    }
                }
                fromAnchor.connect(toAnchor, margin);
            }
        }
    }

    public void resetAllConstraints() {
        resetAnchors();
        setVerticalBiasPercent(DEFAULT_BIAS);
        setHorizontalBiasPercent(DEFAULT_BIAS);
    }

    public void resetAnchor(ConstraintAnchor anchor) {
        if (getParent() == null || !(getParent() instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            ConstraintAnchor left = getAnchor(ConstraintAnchor.Type.LEFT);
            ConstraintAnchor right = getAnchor(ConstraintAnchor.Type.RIGHT);
            ConstraintAnchor top = getAnchor(ConstraintAnchor.Type.TOP);
            ConstraintAnchor bottom = getAnchor(ConstraintAnchor.Type.BOTTOM);
            ConstraintAnchor center = getAnchor(ConstraintAnchor.Type.CENTER);
            ConstraintAnchor centerX = getAnchor(ConstraintAnchor.Type.CENTER_X);
            ConstraintAnchor centerY = getAnchor(ConstraintAnchor.Type.CENTER_Y);
            if (anchor == center) {
                if (left.isConnected() && right.isConnected() && left.getTarget() == right.getTarget()) {
                    left.reset();
                    right.reset();
                }
                if (top.isConnected() && bottom.isConnected() && top.getTarget() == bottom.getTarget()) {
                    top.reset();
                    bottom.reset();
                }
                this.mHorizontalBiasPercent = 0.5f;
                this.mVerticalBiasPercent = 0.5f;
            } else if (anchor == centerX) {
                if (left.isConnected() && right.isConnected() && left.getTarget().getOwner() == right.getTarget().getOwner()) {
                    left.reset();
                    right.reset();
                }
                this.mHorizontalBiasPercent = 0.5f;
            } else if (anchor == centerY) {
                if (top.isConnected() && bottom.isConnected() && top.getTarget().getOwner() == bottom.getTarget().getOwner()) {
                    top.reset();
                    bottom.reset();
                }
                this.mVerticalBiasPercent = 0.5f;
            } else if (anchor == left || anchor == right) {
                if (left.isConnected() && left.getTarget() == right.getTarget()) {
                    center.reset();
                }
            } else if ((anchor == top || anchor == bottom) && top.isConnected() && top.getTarget() == bottom.getTarget()) {
                center.reset();
            }
            anchor.reset();
        }
    }

    public void resetAnchors() {
        ConstraintWidget parent = getParent();
        if (parent == null || !(parent instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            int mAnchorsSize = this.mAnchors.size();
            for (int i = 0; i < mAnchorsSize; i++) {
                this.mAnchors.get(i).reset();
            }
        }
    }

    public ConstraintAnchor getAnchor(ConstraintAnchor.Type anchorType) {
        switch (anchorType) {
            case LEFT:
                return this.mLeft;
            case TOP:
                return this.mTop;
            case RIGHT:
                return this.mRight;
            case BOTTOM:
                return this.mBottom;
            case BASELINE:
                return this.mBaseline;
            case CENTER:
                return this.mCenter;
            case CENTER_X:
                return this.mCenterX;
            case CENTER_Y:
                return this.mCenterY;
            case NONE:
                return null;
            default:
                throw new AssertionError(anchorType.name());
        }
    }

    public DimensionBehaviour getHorizontalDimensionBehaviour() {
        return this.mListDimensionBehaviors[0];
    }

    public DimensionBehaviour getVerticalDimensionBehaviour() {
        return this.mListDimensionBehaviors[1];
    }

    public DimensionBehaviour getDimensionBehaviour(int orientation) {
        if (orientation == 0) {
            return getHorizontalDimensionBehaviour();
        }
        if (orientation == 1) {
            return getVerticalDimensionBehaviour();
        }
        return null;
    }

    public void setHorizontalDimensionBehaviour(DimensionBehaviour behaviour) {
        this.mListDimensionBehaviors[0] = behaviour;
    }

    public void setVerticalDimensionBehaviour(DimensionBehaviour behaviour) {
        this.mListDimensionBehaviors[1] = behaviour;
    }

    public boolean isInHorizontalChain() {
        if (this.mLeft.mTarget != null && this.mLeft.mTarget.mTarget == this.mLeft) {
            return true;
        }
        if (this.mRight.mTarget == null || this.mRight.mTarget.mTarget != this.mRight) {
            return false;
        }
        return true;
    }

    public ConstraintWidget getPreviousChainMember(int orientation) {
        if (orientation == 0) {
            if (this.mLeft.mTarget == null || this.mLeft.mTarget.mTarget != this.mLeft) {
                return null;
            }
            return this.mLeft.mTarget.mOwner;
        } else if (orientation == 1 && this.mTop.mTarget != null && this.mTop.mTarget.mTarget == this.mTop) {
            return this.mTop.mTarget.mOwner;
        } else {
            return null;
        }
    }

    public ConstraintWidget getNextChainMember(int orientation) {
        if (orientation == 0) {
            if (this.mRight.mTarget == null || this.mRight.mTarget.mTarget != this.mRight) {
                return null;
            }
            return this.mRight.mTarget.mOwner;
        } else if (orientation == 1 && this.mBottom.mTarget != null && this.mBottom.mTarget.mTarget == this.mBottom) {
            return this.mBottom.mTarget.mOwner;
        } else {
            return null;
        }
    }

    public ConstraintWidget getHorizontalChainControlWidget() {
        ConstraintWidget found = null;
        if (!isInHorizontalChain()) {
            return null;
        }
        ConstraintWidget tmp = this;
        while (found == null && tmp != null) {
            ConstraintAnchor anchor = tmp.getAnchor(ConstraintAnchor.Type.LEFT);
            ConstraintAnchor targetAnchor = null;
            ConstraintAnchor targetOwner = anchor == null ? null : anchor.getTarget();
            ConstraintWidget target = targetOwner == null ? null : targetOwner.getOwner();
            if (target == getParent()) {
                return tmp;
            }
            if (target != null) {
                targetAnchor = target.getAnchor(ConstraintAnchor.Type.RIGHT).getTarget();
            }
            if (targetAnchor == null || targetAnchor.getOwner() == tmp) {
                tmp = target;
            } else {
                found = tmp;
            }
        }
        return found;
    }

    public boolean isInVerticalChain() {
        if (this.mTop.mTarget != null && this.mTop.mTarget.mTarget == this.mTop) {
            return true;
        }
        if (this.mBottom.mTarget == null || this.mBottom.mTarget.mTarget != this.mBottom) {
            return false;
        }
        return true;
    }

    public ConstraintWidget getVerticalChainControlWidget() {
        ConstraintWidget found = null;
        if (!isInVerticalChain()) {
            return null;
        }
        ConstraintWidget tmp = this;
        while (found == null && tmp != null) {
            ConstraintAnchor anchor = tmp.getAnchor(ConstraintAnchor.Type.TOP);
            ConstraintAnchor targetAnchor = null;
            ConstraintAnchor targetOwner = anchor == null ? null : anchor.getTarget();
            ConstraintWidget target = targetOwner == null ? null : targetOwner.getOwner();
            if (target == getParent()) {
                return tmp;
            }
            if (target != null) {
                targetAnchor = target.getAnchor(ConstraintAnchor.Type.BOTTOM).getTarget();
            }
            if (targetAnchor == null || targetAnchor.getOwner() == tmp) {
                tmp = target;
            } else {
                found = tmp;
            }
        }
        return found;
    }

    private boolean isChainHead(int orientation) {
        int offset = orientation * 2;
        return (this.mListAnchors[offset].mTarget == null || this.mListAnchors[offset].mTarget.mTarget == this.mListAnchors[offset] || this.mListAnchors[offset + 1].mTarget == null || this.mListAnchors[offset + 1].mTarget.mTarget != this.mListAnchors[offset + 1]) ? false : true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v18, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v12, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v5, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r43v0, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v1, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v2, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v3, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v31, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v33, resolved type: int} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:192:0x0336  */
    /* JADX WARNING: Removed duplicated region for block: B:193:0x0338  */
    /* JADX WARNING: Removed duplicated region for block: B:199:0x0345  */
    /* JADX WARNING: Removed duplicated region for block: B:200:0x0347  */
    /* JADX WARNING: Removed duplicated region for block: B:205:0x0358  */
    /* JADX WARNING: Removed duplicated region for block: B:206:0x035a  */
    /* JADX WARNING: Removed duplicated region for block: B:208:0x035d  */
    /* JADX WARNING: Removed duplicated region for block: B:209:0x0360  */
    /* JADX WARNING: Removed duplicated region for block: B:212:0x036a  */
    /* JADX WARNING: Removed duplicated region for block: B:213:0x036e  */
    /* JADX WARNING: Removed duplicated region for block: B:218:0x0389  */
    /* JADX WARNING: Removed duplicated region for block: B:253:0x0523  */
    /* JADX WARNING: Removed duplicated region for block: B:262:0x0557  */
    /* JADX WARNING: Removed duplicated region for block: B:273:0x05a8  */
    /* JADX WARNING: Removed duplicated region for block: B:276:0x05b8  */
    /* JADX WARNING: Removed duplicated region for block: B:277:0x05bc  */
    /* JADX WARNING: Removed duplicated region for block: B:281:0x05c4  */
    /* JADX WARNING: Removed duplicated region for block: B:316:0x06b7  */
    /* JADX WARNING: Removed duplicated region for block: B:318:0x06c6  */
    /* JADX WARNING: Removed duplicated region for block: B:322:0x06f0  */
    /* JADX WARNING: Removed duplicated region for block: B:325:0x06fa  */
    /* JADX WARNING: Removed duplicated region for block: B:328:0x0721  */
    /* JADX WARNING: Removed duplicated region for block: B:330:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addToSolver(androidx.constraintlayout.core.LinearSystem r48, boolean r49) {
        /*
            r47 = this;
            r0 = r47
            r1 = r48
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r0.mLeft
            androidx.constraintlayout.core.SolverVariable r2 = r1.createObjectVariable(r2)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r0.mRight
            androidx.constraintlayout.core.SolverVariable r3 = r1.createObjectVariable(r3)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r0.mTop
            androidx.constraintlayout.core.SolverVariable r4 = r1.createObjectVariable(r4)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r0.mBottom
            androidx.constraintlayout.core.SolverVariable r5 = r1.createObjectVariable(r5)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r0.mBaseline
            androidx.constraintlayout.core.SolverVariable r6 = r1.createObjectVariable(r6)
            r7 = 0
            r8 = 0
            androidx.constraintlayout.core.widgets.ConstraintWidget r9 = r0.mParent
            r10 = 1
            r11 = 0
            if (r9 == 0) goto L_0x005b
            androidx.constraintlayout.core.widgets.ConstraintWidget r9 = r0.mParent
            if (r9 == 0) goto L_0x003a
            androidx.constraintlayout.core.widgets.ConstraintWidget r9 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r9 = r9.mListDimensionBehaviors
            r9 = r9[r11]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r12 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r9 != r12) goto L_0x003a
            r9 = r10
            goto L_0x003b
        L_0x003a:
            r9 = r11
        L_0x003b:
            r7 = r9
            androidx.constraintlayout.core.widgets.ConstraintWidget r9 = r0.mParent
            if (r9 == 0) goto L_0x004c
            androidx.constraintlayout.core.widgets.ConstraintWidget r9 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r9 = r9.mListDimensionBehaviors
            r9 = r9[r10]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r12 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r9 != r12) goto L_0x004c
            r9 = r10
            goto L_0x004d
        L_0x004c:
            r9 = r11
        L_0x004d:
            r8 = r9
            int r9 = r0.mWrapBehaviorInParent
            switch(r9) {
                case 1: goto L_0x0059;
                case 2: goto L_0x0057;
                case 3: goto L_0x0054;
                default: goto L_0x0053;
            }
        L_0x0053:
            goto L_0x005b
        L_0x0054:
            r7 = 0
            r8 = 0
            goto L_0x005b
        L_0x0057:
            r7 = 0
            goto L_0x005b
        L_0x0059:
            r8 = 0
        L_0x005b:
            int r9 = r0.mVisibility
            r12 = 8
            if (r9 != r12) goto L_0x0078
            boolean r9 = r0.mAnimated
            if (r9 != 0) goto L_0x0078
            boolean r9 = r0.hasDependencies()
            if (r9 != 0) goto L_0x0078
            boolean[] r9 = r0.mIsInBarrier
            boolean r9 = r9[r11]
            if (r9 != 0) goto L_0x0078
            boolean[] r9 = r0.mIsInBarrier
            boolean r9 = r9[r10]
            if (r9 != 0) goto L_0x0078
            return
        L_0x0078:
            boolean r9 = r0.mResolvedHorizontal
            if (r9 != 0) goto L_0x0080
            boolean r9 = r0.mResolvedVertical
            if (r9 == 0) goto L_0x0109
        L_0x0080:
            boolean r9 = r0.mResolvedHorizontal
            if (r9 == 0) goto L_0x00b6
            int r9 = r0.mX
            r1.addEquality(r2, r9)
            int r9 = r0.mX
            int r13 = r0.mWidth
            int r9 = r9 + r13
            r1.addEquality(r3, r9)
            if (r7 == 0) goto L_0x00b6
            androidx.constraintlayout.core.widgets.ConstraintWidget r9 = r0.mParent
            if (r9 == 0) goto L_0x00b6
            boolean r9 = r0.mOptimizeWrapOnResolved
            if (r9 == 0) goto L_0x00aa
            androidx.constraintlayout.core.widgets.ConstraintWidget r9 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r9 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r9
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r0.mLeft
            r9.addHorizontalWrapMinVariable(r13)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r0.mRight
            r9.addHorizontalWrapMaxVariable(r13)
            goto L_0x00b6
        L_0x00aa:
            r9 = 5
            androidx.constraintlayout.core.widgets.ConstraintWidget r13 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r13.mRight
            androidx.constraintlayout.core.SolverVariable r13 = r1.createObjectVariable(r13)
            r1.addGreaterThan(r13, r3, r11, r9)
        L_0x00b6:
            boolean r9 = r0.mResolvedVertical
            if (r9 == 0) goto L_0x00fc
            int r9 = r0.mY
            r1.addEquality(r4, r9)
            int r9 = r0.mY
            int r13 = r0.mHeight
            int r9 = r9 + r13
            r1.addEquality(r5, r9)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r0.mBaseline
            boolean r9 = r9.hasDependents()
            if (r9 == 0) goto L_0x00d7
            int r9 = r0.mY
            int r13 = r0.mBaselineDistance
            int r9 = r9 + r13
            r1.addEquality(r6, r9)
        L_0x00d7:
            if (r8 == 0) goto L_0x00fc
            androidx.constraintlayout.core.widgets.ConstraintWidget r9 = r0.mParent
            if (r9 == 0) goto L_0x00fc
            boolean r9 = r0.mOptimizeWrapOnResolved
            if (r9 == 0) goto L_0x00f0
            androidx.constraintlayout.core.widgets.ConstraintWidget r9 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r9 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r9
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r0.mTop
            r9.addVerticalWrapMinVariable(r13)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r0.mBottom
            r9.addVerticalWrapMaxVariable(r13)
            goto L_0x00fc
        L_0x00f0:
            r9 = 5
            androidx.constraintlayout.core.widgets.ConstraintWidget r13 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r13.mBottom
            androidx.constraintlayout.core.SolverVariable r13 = r1.createObjectVariable(r13)
            r1.addGreaterThan(r13, r5, r11, r9)
        L_0x00fc:
            boolean r9 = r0.mResolvedHorizontal
            if (r9 == 0) goto L_0x0109
            boolean r9 = r0.mResolvedVertical
            if (r9 == 0) goto L_0x0109
            r0.mResolvedHorizontal = r11
            r0.mResolvedVertical = r11
            return
        L_0x0109:
            androidx.constraintlayout.core.Metrics r9 = androidx.constraintlayout.core.LinearSystem.sMetrics
            if (r9 == 0) goto L_0x0117
            androidx.constraintlayout.core.Metrics r9 = androidx.constraintlayout.core.LinearSystem.sMetrics
            r15 = 1
            long r13 = r9.widgets
            long r13 = r13 + r15
            r9.widgets = r13
            goto L_0x0119
        L_0x0117:
            r15 = 1
        L_0x0119:
            if (r49 == 0) goto L_0x01b6
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r9 = r0.mHorizontalRun
            if (r9 == 0) goto L_0x01b6
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r9 = r0.mVerticalRun
            if (r9 == 0) goto L_0x01b6
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r9 = r0.mHorizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r9 = r9.start
            boolean r9 = r9.resolved
            if (r9 == 0) goto L_0x01b6
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r9 = r0.mHorizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r9 = r9.end
            boolean r9 = r9.resolved
            if (r9 == 0) goto L_0x01b6
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r9 = r0.mVerticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r9 = r9.start
            boolean r9 = r9.resolved
            if (r9 == 0) goto L_0x01b6
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r9 = r0.mVerticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r9 = r9.end
            boolean r9 = r9.resolved
            if (r9 == 0) goto L_0x01b6
            androidx.constraintlayout.core.Metrics r9 = androidx.constraintlayout.core.LinearSystem.sMetrics
            if (r9 == 0) goto L_0x014e
            androidx.constraintlayout.core.Metrics r9 = androidx.constraintlayout.core.LinearSystem.sMetrics
            long r13 = r9.graphSolved
            long r13 = r13 + r15
            r9.graphSolved = r13
        L_0x014e:
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r9 = r0.mHorizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r9 = r9.start
            int r9 = r9.value
            r1.addEquality(r2, r9)
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r9 = r0.mHorizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r9 = r9.end
            int r9 = r9.value
            r1.addEquality(r3, r9)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r9 = r0.mVerticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r9 = r9.start
            int r9 = r9.value
            r1.addEquality(r4, r9)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r9 = r0.mVerticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r9 = r9.end
            int r9 = r9.value
            r1.addEquality(r5, r9)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r9 = r0.mVerticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r9 = r9.baseline
            int r9 = r9.value
            r1.addEquality(r6, r9)
            androidx.constraintlayout.core.widgets.ConstraintWidget r9 = r0.mParent
            if (r9 == 0) goto L_0x01b1
            if (r7 == 0) goto L_0x0198
            boolean[] r9 = r0.isTerminalWidget
            boolean r9 = r9[r11]
            if (r9 == 0) goto L_0x0198
            boolean r9 = r0.isInHorizontalChain()
            if (r9 != 0) goto L_0x0198
            androidx.constraintlayout.core.widgets.ConstraintWidget r9 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r9.mRight
            androidx.constraintlayout.core.SolverVariable r9 = r1.createObjectVariable(r9)
            r1.addGreaterThan(r9, r3, r11, r12)
        L_0x0198:
            if (r8 == 0) goto L_0x01b1
            boolean[] r9 = r0.isTerminalWidget
            boolean r9 = r9[r10]
            if (r9 == 0) goto L_0x01b1
            boolean r9 = r0.isInVerticalChain()
            if (r9 != 0) goto L_0x01b1
            androidx.constraintlayout.core.widgets.ConstraintWidget r9 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r9.mBottom
            androidx.constraintlayout.core.SolverVariable r9 = r1.createObjectVariable(r9)
            r1.addGreaterThan(r9, r5, r11, r12)
        L_0x01b1:
            r0.mResolvedHorizontal = r11
            r0.mResolvedVertical = r11
            return
        L_0x01b6:
            androidx.constraintlayout.core.Metrics r9 = androidx.constraintlayout.core.LinearSystem.sMetrics
            if (r9 == 0) goto L_0x01c1
            androidx.constraintlayout.core.Metrics r9 = androidx.constraintlayout.core.LinearSystem.sMetrics
            long r13 = r9.linearSolved
            long r13 = r13 + r15
            r9.linearSolved = r13
        L_0x01c1:
            r9 = 0
            r13 = 0
            androidx.constraintlayout.core.widgets.ConstraintWidget r14 = r0.mParent
            if (r14 == 0) goto L_0x0234
            boolean r14 = r0.isChainHead(r11)
            if (r14 == 0) goto L_0x01d6
            androidx.constraintlayout.core.widgets.ConstraintWidget r14 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r14 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r14
            r14.addChain(r0, r11)
            r9 = 1
            goto L_0x01da
        L_0x01d6:
            boolean r9 = r0.isInHorizontalChain()
        L_0x01da:
            boolean r14 = r0.isChainHead(r10)
            if (r14 == 0) goto L_0x01e9
            androidx.constraintlayout.core.widgets.ConstraintWidget r14 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r14 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r14
            r14.addChain(r0, r10)
            r13 = 1
            goto L_0x01ed
        L_0x01e9:
            boolean r13 = r0.isInVerticalChain()
        L_0x01ed:
            if (r9 != 0) goto L_0x020c
            if (r7 == 0) goto L_0x020c
            int r14 = r0.mVisibility
            if (r14 == r12) goto L_0x020c
            androidx.constraintlayout.core.widgets.ConstraintAnchor r14 = r0.mLeft
            androidx.constraintlayout.core.widgets.ConstraintAnchor r14 = r14.mTarget
            if (r14 != 0) goto L_0x020c
            androidx.constraintlayout.core.widgets.ConstraintAnchor r14 = r0.mRight
            androidx.constraintlayout.core.widgets.ConstraintAnchor r14 = r14.mTarget
            if (r14 != 0) goto L_0x020c
            androidx.constraintlayout.core.widgets.ConstraintWidget r14 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r14 = r14.mRight
            androidx.constraintlayout.core.SolverVariable r14 = r1.createObjectVariable(r14)
            r1.addGreaterThan(r14, r3, r11, r10)
        L_0x020c:
            if (r13 != 0) goto L_0x022f
            if (r8 == 0) goto L_0x022f
            int r14 = r0.mVisibility
            if (r14 == r12) goto L_0x022f
            androidx.constraintlayout.core.widgets.ConstraintAnchor r14 = r0.mTop
            androidx.constraintlayout.core.widgets.ConstraintAnchor r14 = r14.mTarget
            if (r14 != 0) goto L_0x022f
            androidx.constraintlayout.core.widgets.ConstraintAnchor r14 = r0.mBottom
            androidx.constraintlayout.core.widgets.ConstraintAnchor r14 = r14.mTarget
            if (r14 != 0) goto L_0x022f
            androidx.constraintlayout.core.widgets.ConstraintAnchor r14 = r0.mBaseline
            if (r14 != 0) goto L_0x022f
            androidx.constraintlayout.core.widgets.ConstraintWidget r14 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r14 = r14.mBottom
            androidx.constraintlayout.core.SolverVariable r14 = r1.createObjectVariable(r14)
            r1.addGreaterThan(r14, r5, r11, r10)
        L_0x022f:
            r19 = r9
            r20 = r13
            goto L_0x0238
        L_0x0234:
            r19 = r9
            r20 = r13
        L_0x0238:
            int r9 = r0.mWidth
            int r13 = r0.mMinWidth
            if (r9 >= r13) goto L_0x0240
            int r9 = r0.mMinWidth
        L_0x0240:
            int r13 = r0.mHeight
            int r14 = r0.mMinHeight
            if (r13 >= r14) goto L_0x0248
            int r13 = r0.mMinHeight
        L_0x0248:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r14 = r0.mListDimensionBehaviors
            r14 = r14[r11]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r15 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r14 == r15) goto L_0x0252
            r14 = r10
            goto L_0x0253
        L_0x0252:
            r14 = r11
        L_0x0253:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r15 = r0.mListDimensionBehaviors
            r15 = r15[r10]
            r16 = r10
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r10 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r15 == r10) goto L_0x0260
            r10 = r16
            goto L_0x0261
        L_0x0260:
            r10 = r11
        L_0x0261:
            r15 = 0
            r17 = r11
            int r11 = r0.mDimensionRatioSide
            r0.mResolvedDimensionRatioSide = r11
            float r11 = r0.mDimensionRatio
            r0.mResolvedDimensionRatio = r11
            int r11 = r0.mMatchConstraintDefaultWidth
            int r12 = r0.mMatchConstraintDefaultHeight
            r21 = r4
            float r4 = r0.mDimensionRatio
            r22 = 0
            int r4 = (r4 > r22 ? 1 : (r4 == r22 ? 0 : -1))
            r22 = r4
            if (r22 <= 0) goto L_0x0316
            int r4 = r0.mVisibility
            r23 = r5
            r5 = 8
            if (r4 == r5) goto L_0x0313
            r15 = 1
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r4 = r0.mListDimensionBehaviors
            r4 = r4[r17]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r5 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r4 != r5) goto L_0x0290
            if (r11 != 0) goto L_0x0290
            r11 = 3
        L_0x0290:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r4 = r0.mListDimensionBehaviors
            r4 = r4[r16]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r5 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r4 != r5) goto L_0x029b
            if (r12 != 0) goto L_0x029b
            r12 = 3
        L_0x029b:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r4 = r0.mListDimensionBehaviors
            r4 = r4[r17]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r5 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r24 = r6
            r6 = 3
            if (r4 != r5) goto L_0x02b7
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r4 = r0.mListDimensionBehaviors
            r4 = r4[r16]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r5 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r4 != r5) goto L_0x02b7
            if (r11 != r6) goto L_0x02b7
            if (r12 != r6) goto L_0x02b7
            r0.setupDimensionRatio(r7, r8, r14, r10)
            goto L_0x031a
        L_0x02b7:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r4 = r0.mListDimensionBehaviors
            r4 = r4[r17]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r5 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r4 != r5) goto L_0x02de
            if (r11 != r6) goto L_0x02de
            r4 = r17
            r0.mResolvedDimensionRatioSide = r4
            float r4 = r0.mResolvedDimensionRatio
            int r5 = r0.mHeight
            float r5 = (float) r5
            float r4 = r4 * r5
            int r9 = (int) r4
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r4 = r0.mListDimensionBehaviors
            r4 = r4[r16]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r5 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r4 == r5) goto L_0x02da
            r11 = 4
            r15 = 0
            r28 = r13
            r4 = r15
            goto L_0x031d
        L_0x02da:
            r28 = r13
            r4 = r15
            goto L_0x031d
        L_0x02de:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r4 = r0.mListDimensionBehaviors
            r4 = r4[r16]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r5 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r4 != r5) goto L_0x031a
            if (r12 != r6) goto L_0x031a
            r4 = r16
            r0.mResolvedDimensionRatioSide = r4
            int r4 = r0.mDimensionRatioSide
            r5 = -1
            if (r4 != r5) goto L_0x02f8
            r4 = 1065353216(0x3f800000, float:1.0)
            float r5 = r0.mResolvedDimensionRatio
            float r4 = r4 / r5
            r0.mResolvedDimensionRatio = r4
        L_0x02f8:
            float r4 = r0.mResolvedDimensionRatio
            int r5 = r0.mWidth
            float r5 = (float) r5
            float r4 = r4 * r5
            int r13 = (int) r4
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r4 = r0.mListDimensionBehaviors
            r17 = 0
            r4 = r4[r17]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r5 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r4 == r5) goto L_0x030f
            r12 = 4
            r15 = 0
            r28 = r13
            r4 = r15
            goto L_0x031d
        L_0x030f:
            r28 = r13
            r4 = r15
            goto L_0x031d
        L_0x0313:
            r24 = r6
            goto L_0x031a
        L_0x0316:
            r23 = r5
            r24 = r6
        L_0x031a:
            r28 = r13
            r4 = r15
        L_0x031d:
            int[] r5 = r0.mResolvedMatchConstraintDefault
            r17 = 0
            r5[r17] = r11
            int[] r5 = r0.mResolvedMatchConstraintDefault
            r16 = 1
            r5[r16] = r12
            r0.mResolvedHasRatio = r4
            if (r4 == 0) goto L_0x0338
            int r5 = r0.mResolvedDimensionRatioSide
            if (r5 == 0) goto L_0x0336
            int r5 = r0.mResolvedDimensionRatioSide
            r6 = -1
            if (r5 != r6) goto L_0x0338
        L_0x0336:
            r5 = 1
            goto L_0x0339
        L_0x0338:
            r5 = 0
        L_0x0339:
            if (r4 == 0) goto L_0x0347
            int r6 = r0.mResolvedDimensionRatioSide
            r13 = 1
            if (r6 == r13) goto L_0x0345
            int r6 = r0.mResolvedDimensionRatioSide
            r13 = -1
            if (r6 != r13) goto L_0x0347
        L_0x0345:
            r6 = 1
            goto L_0x0348
        L_0x0347:
            r6 = 0
        L_0x0348:
            r29 = r6
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r6 = r0.mListDimensionBehaviors
            r17 = 0
            r6 = r6[r17]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r13 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r6 != r13) goto L_0x035a
            boolean r6 = r0 instanceof androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
            if (r6 == 0) goto L_0x035a
            r6 = 1
            goto L_0x035b
        L_0x035a:
            r6 = 0
        L_0x035b:
            if (r6 == 0) goto L_0x0360
            r9 = 0
            r13 = r9
            goto L_0x0361
        L_0x0360:
            r13 = r9
        L_0x0361:
            r9 = 1
            androidx.constraintlayout.core.widgets.ConstraintAnchor r15 = r0.mCenter
            boolean r15 = r15.isConnected()
            if (r15 == 0) goto L_0x036e
            r9 = 0
            r27 = r9
            goto L_0x0370
        L_0x036e:
            r27 = r9
        L_0x0370:
            boolean[] r9 = r0.mIsInBarrier
            r17 = 0
            boolean r9 = r9[r17]
            boolean[] r15 = r0.mIsInBarrier
            r16 = 1
            boolean r30 = r15[r16]
            int r15 = r0.mHorizontalResolution
            r22 = r4
            r4 = 2
            r31 = 0
            if (r15 == r4) goto L_0x0523
            boolean r15 = r0.mResolvedHorizontal
            if (r15 != 0) goto L_0x0523
            if (r49 == 0) goto L_0x0486
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r15 = r0.mHorizontalRun
            if (r15 == 0) goto L_0x0486
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r15 = r0.mHorizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r15 = r15.start
            boolean r15 = r15.resolved
            if (r15 == 0) goto L_0x0486
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r15 = r0.mHorizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r15 = r15.end
            boolean r15 = r15.resolved
            if (r15 != 0) goto L_0x03a5
            r26 = r2
            r2 = 8
            goto L_0x048a
        L_0x03a5:
            if (r49 == 0) goto L_0x0463
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r15 = r0.mHorizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r15 = r15.start
            int r15 = r15.value
            r1.addEquality(r2, r15)
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r15 = r0.mHorizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r15 = r15.end
            int r15 = r15.value
            r1.addEquality(r3, r15)
            androidx.constraintlayout.core.widgets.ConstraintWidget r15 = r0.mParent
            if (r15 == 0) goto L_0x0440
            if (r7 == 0) goto L_0x041d
            boolean[] r15 = r0.isTerminalWidget
            r4 = 0
            boolean r15 = r15[r4]
            if (r15 == 0) goto L_0x041d
            boolean r15 = r0.isInHorizontalChain()
            if (r15 != 0) goto L_0x03fa
            androidx.constraintlayout.core.widgets.ConstraintWidget r15 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r15 = r15.mRight
            androidx.constraintlayout.core.SolverVariable r15 = r1.createObjectVariable(r15)
            r26 = r2
            r2 = 8
            r1.addGreaterThan(r15, r3, r4, r2)
            r33 = r3
            r37 = r5
            r3 = r7
            r4 = r8
            r38 = r9
            r34 = r10
            r39 = r13
            r35 = r14
            r44 = r21
            r36 = r22
            r45 = r23
            r46 = r24
            r32 = r26
            r9 = r6
            r22 = r11
            r23 = r12
            goto L_0x0540
        L_0x03fa:
            r26 = r2
            r2 = 8
            r33 = r3
            r37 = r5
            r3 = r7
            r4 = r8
            r38 = r9
            r34 = r10
            r39 = r13
            r35 = r14
            r44 = r21
            r36 = r22
            r45 = r23
            r46 = r24
            r32 = r26
            r9 = r6
            r22 = r11
            r23 = r12
            goto L_0x0540
        L_0x041d:
            r26 = r2
            r2 = 8
            r33 = r3
            r37 = r5
            r3 = r7
            r4 = r8
            r38 = r9
            r34 = r10
            r39 = r13
            r35 = r14
            r44 = r21
            r36 = r22
            r45 = r23
            r46 = r24
            r32 = r26
            r9 = r6
            r22 = r11
            r23 = r12
            goto L_0x0540
        L_0x0440:
            r26 = r2
            r2 = 8
            r33 = r3
            r37 = r5
            r3 = r7
            r4 = r8
            r38 = r9
            r34 = r10
            r39 = r13
            r35 = r14
            r44 = r21
            r36 = r22
            r45 = r23
            r46 = r24
            r32 = r26
            r9 = r6
            r22 = r11
            r23 = r12
            goto L_0x0540
        L_0x0463:
            r26 = r2
            r2 = 8
            r33 = r3
            r37 = r5
            r3 = r7
            r4 = r8
            r38 = r9
            r34 = r10
            r39 = r13
            r35 = r14
            r44 = r21
            r36 = r22
            r45 = r23
            r46 = r24
            r32 = r26
            r9 = r6
            r22 = r11
            r23 = r12
            goto L_0x0540
        L_0x0486:
            r26 = r2
            r2 = 8
        L_0x048a:
            androidx.constraintlayout.core.widgets.ConstraintWidget r4 = r0.mParent
            if (r4 == 0) goto L_0x0497
            androidx.constraintlayout.core.widgets.ConstraintWidget r4 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r4.mRight
            androidx.constraintlayout.core.SolverVariable r4 = r1.createObjectVariable(r4)
            goto L_0x0499
        L_0x0497:
            r4 = r31
        L_0x0499:
            androidx.constraintlayout.core.widgets.ConstraintWidget r15 = r0.mParent
            if (r15 == 0) goto L_0x04a6
            androidx.constraintlayout.core.widgets.ConstraintWidget r15 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r15 = r15.mLeft
            androidx.constraintlayout.core.SolverVariable r15 = r1.createObjectVariable(r15)
            goto L_0x04a8
        L_0x04a6:
            r15 = r31
        L_0x04a8:
            boolean[] r2 = r0.isTerminalWidget
            r17 = 0
            boolean r2 = r2[r17]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r0.mListDimensionBehaviors
            r1 = r1[r17]
            r32 = r10
            androidx.constraintlayout.core.widgets.ConstraintAnchor r10 = r0.mLeft
            r33 = r22
            r22 = r11
            androidx.constraintlayout.core.widgets.ConstraintAnchor r11 = r0.mRight
            r34 = r23
            r23 = r12
            int r12 = r0.mX
            r35 = r14
            int r14 = r0.mMinWidth
            r36 = r1
            int[] r1 = r0.mMaxDimension
            r1 = r1[r17]
            r37 = r1
            float r1 = r0.mHorizontalBiasPercent
            r38 = r1
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r0.mListDimensionBehaviors
            r16 = 1
            r1 = r1[r16]
            r39 = r2
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r2 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 != r2) goto L_0x04e1
            r18 = r16
            goto L_0x04e3
        L_0x04e1:
            r18 = r17
        L_0x04e3:
            r2 = 8
            int r1 = r0.mMatchConstraintMinWidth
            int r2 = r0.mMatchConstraintMaxWidth
            r41 = r1
            float r1 = r0.mMatchConstraintPercentWidth
            r25 = r2
            r42 = 2
            r2 = 1
            r16 = r33
            r33 = r3
            r3 = r7
            r7 = r4
            r4 = r8
            r8 = r36
            r36 = r16
            r17 = r5
            r44 = r21
            r46 = r24
            r45 = r34
            r16 = r38
            r5 = r39
            r24 = r41
            r21 = r9
            r34 = r32
            r9 = r6
            r6 = r15
            r32 = r26
            r15 = r37
            r26 = r1
            r1 = r48
            r0.applyConstraints(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27)
            r39 = r13
            r37 = r17
            r38 = r21
            goto L_0x0540
        L_0x0523:
            r32 = r2
            r33 = r3
            r37 = r5
            r3 = r7
            r4 = r8
            r38 = r9
            r34 = r10
            r39 = r13
            r35 = r14
            r44 = r21
            r36 = r22
            r45 = r23
            r46 = r24
            r9 = r6
            r22 = r11
            r23 = r12
        L_0x0540:
            r2 = 1
            if (r49 == 0) goto L_0x05a8
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r5 = r0.mVerticalRun
            if (r5 == 0) goto L_0x05a8
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r5 = r0.mVerticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r5 = r5.start
            boolean r5 = r5.resolved
            if (r5 == 0) goto L_0x05a8
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r5 = r0.mVerticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r5 = r5.end
            boolean r5 = r5.resolved
            if (r5 == 0) goto L_0x05a8
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r5 = r0.mVerticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r5 = r5.start
            int r5 = r5.value
            r6 = r44
            r1.addEquality(r6, r5)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r5 = r0.mVerticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r5 = r5.end
            int r5 = r5.value
            r7 = r45
            r1.addEquality(r7, r5)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r5 = r0.mVerticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r5 = r5.baseline
            int r5 = r5.value
            r8 = r46
            r1.addEquality(r8, r5)
            androidx.constraintlayout.core.widgets.ConstraintWidget r5 = r0.mParent
            if (r5 == 0) goto L_0x05a1
            if (r20 != 0) goto L_0x059b
            if (r4 == 0) goto L_0x059b
            boolean[] r5 = r0.isTerminalWidget
            r16 = 1
            boolean r5 = r5[r16]
            if (r5 == 0) goto L_0x0597
            androidx.constraintlayout.core.widgets.ConstraintWidget r5 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r5.mBottom
            androidx.constraintlayout.core.SolverVariable r5 = r1.createObjectVariable(r5)
            r10 = 8
            r11 = 0
            r1.addGreaterThan(r5, r7, r11, r10)
            goto L_0x05a6
        L_0x0597:
            r10 = 8
            r11 = 0
            goto L_0x05a6
        L_0x059b:
            r10 = 8
            r11 = 0
            r16 = 1
            goto L_0x05a6
        L_0x05a1:
            r10 = 8
            r11 = 0
            r16 = 1
        L_0x05a6:
            r2 = 0
            goto L_0x05b3
        L_0x05a8:
            r6 = r44
            r7 = r45
            r8 = r46
            r10 = 8
            r11 = 0
            r16 = 1
        L_0x05b3:
            int r5 = r0.mVerticalResolution
            r12 = 2
            if (r5 != r12) goto L_0x05bc
            r2 = 0
            r40 = r2
            goto L_0x05be
        L_0x05bc:
            r40 = r2
        L_0x05be:
            if (r40 == 0) goto L_0x06b7
            boolean r2 = r0.mResolvedVertical
            if (r2 != 0) goto L_0x06b7
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r0.mListDimensionBehaviors
            r2 = r2[r16]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r5 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r2 != r5) goto L_0x05d3
            boolean r2 = r0 instanceof androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
            if (r2 == 0) goto L_0x05d3
            r2 = r16
            goto L_0x05d4
        L_0x05d3:
            r2 = r11
        L_0x05d4:
            r9 = r2
            if (r9 == 0) goto L_0x05dc
            r28 = 0
            r13 = r28
            goto L_0x05de
        L_0x05dc:
            r13 = r28
        L_0x05de:
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r0.mParent
            if (r2 == 0) goto L_0x05eb
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.mBottom
            androidx.constraintlayout.core.SolverVariable r2 = r1.createObjectVariable(r2)
            goto L_0x05ed
        L_0x05eb:
            r2 = r31
        L_0x05ed:
            androidx.constraintlayout.core.widgets.ConstraintWidget r5 = r0.mParent
            if (r5 == 0) goto L_0x05fa
            androidx.constraintlayout.core.widgets.ConstraintWidget r5 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r5.mTop
            androidx.constraintlayout.core.SolverVariable r31 = r1.createObjectVariable(r5)
            goto L_0x05fb
        L_0x05fa:
        L_0x05fb:
            int r5 = r0.mBaselineDistance
            if (r5 > 0) goto L_0x0603
            int r5 = r0.mVisibility
            if (r5 != r10) goto L_0x0645
        L_0x0603:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r0.mBaseline
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r5.mTarget
            if (r5 == 0) goto L_0x0630
            int r5 = r0.getBaselineDistance()
            r1.addEquality(r8, r6, r5, r10)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r0.mBaseline
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r5.mTarget
            androidx.constraintlayout.core.SolverVariable r5 = r1.createObjectVariable(r5)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r12 = r0.mBaseline
            int r12 = r12.getMargin()
            r1.addEquality(r8, r5, r12, r10)
            r27 = 0
            if (r4 == 0) goto L_0x062f
            androidx.constraintlayout.core.widgets.ConstraintAnchor r10 = r0.mBottom
            androidx.constraintlayout.core.SolverVariable r10 = r1.createObjectVariable(r10)
            r14 = 5
            r1.addGreaterThan(r2, r10, r11, r14)
        L_0x062f:
            goto L_0x0645
        L_0x0630:
            int r5 = r0.mVisibility
            if (r5 != r10) goto L_0x063e
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r0.mBaseline
            int r5 = r5.getMargin()
            r1.addEquality(r8, r6, r5, r10)
            goto L_0x0645
        L_0x063e:
            int r5 = r0.getBaselineDistance()
            r1.addEquality(r8, r6, r5, r10)
        L_0x0645:
            boolean[] r5 = r0.isTerminalWidget
            boolean r5 = r5[r16]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r10 = r0.mListDimensionBehaviors
            r10 = r10[r16]
            r46 = r8
            r8 = r10
            androidx.constraintlayout.core.widgets.ConstraintAnchor r10 = r0.mTop
            r17 = r11
            androidx.constraintlayout.core.widgets.ConstraintAnchor r11 = r0.mBottom
            int r12 = r0.mY
            int r14 = r0.mMinHeight
            int[] r15 = r0.mMaxDimension
            r15 = r15[r16]
            float r1 = r0.mVerticalBiasPercent
            r18 = r1
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r0.mListDimensionBehaviors
            r1 = r1[r17]
            r21 = r2
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r2 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r43 = r16
            r16 = r18
            if (r1 != r2) goto L_0x0673
            r18 = r43
            goto L_0x0675
        L_0x0673:
            r18 = r17
        L_0x0675:
            int r1 = r0.mMatchConstraintMinHeight
            int r2 = r0.mMatchConstraintMaxHeight
            r24 = r1
            float r1 = r0.mMatchConstraintPercentHeight
            r25 = r2
            r2 = 0
            r17 = r4
            r4 = r3
            r3 = r17
            r17 = r20
            r20 = r19
            r19 = r17
            r17 = r23
            r23 = r22
            r22 = r17
            r26 = r1
            r44 = r6
            r45 = r7
            r7 = r21
            r17 = r29
            r21 = r30
            r6 = r31
            r1 = r48
            r0.applyConstraints(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27)
            r2 = r20
            r20 = r19
            r19 = r2
            r2 = r23
            r23 = r22
            r22 = r2
            r10 = r3
            r8 = r4
            r2 = r7
            r7 = r0
            r28 = r13
            goto L_0x06c4
        L_0x06b7:
            r10 = r4
            r44 = r6
            r45 = r7
            r46 = r8
            r17 = r29
            r21 = r30
            r7 = r0
            r8 = r3
        L_0x06c4:
            if (r36 == 0) goto L_0x06f0
            r6 = 8
            int r0 = r7.mResolvedDimensionRatioSide
            r13 = 1
            if (r0 != r13) goto L_0x06df
            float r5 = r7.mResolvedDimensionRatio
            r0 = r48
            r4 = r32
            r3 = r33
            r2 = r44
            r1 = r45
            r0.addRatio(r1, r2, r3, r4, r5, r6)
            r1 = r48
            goto L_0x06f2
        L_0x06df:
            float r5 = r7.mResolvedDimensionRatio
            r0 = r48
            r2 = r32
            r1 = r33
            r4 = r44
            r3 = r45
            r0.addRatio(r1, r2, r3, r4, r5, r6)
            r1 = r0
            goto L_0x06f2
        L_0x06f0:
            r1 = r48
        L_0x06f2:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r7.mCenter
            boolean r0 = r0.isConnected()
            if (r0 == 0) goto L_0x0718
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r7.mCenter
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r0.getTarget()
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r0.getOwner()
            float r2 = r7.mCircleConstraintAngle
            r3 = 1119092736(0x42b40000, float:90.0)
            float r2 = r2 + r3
            double r2 = (double) r2
            double r2 = java.lang.Math.toRadians(r2)
            float r2 = (float) r2
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r7.mCenter
            int r3 = r3.getMargin()
            r1.addCenterPoint(r7, r0, r2, r3)
        L_0x0718:
            r4 = 0
            r7.mResolvedHorizontal = r4
            r7.mResolvedVertical = r4
            androidx.constraintlayout.core.Metrics r0 = androidx.constraintlayout.core.LinearSystem.sMetrics
            if (r0 == 0) goto L_0x0733
            androidx.constraintlayout.core.Metrics r0 = androidx.constraintlayout.core.LinearSystem.sMetrics
            int r2 = r1.getNumEquations()
            long r2 = (long) r2
            r0.mEquations = r2
            androidx.constraintlayout.core.Metrics r0 = androidx.constraintlayout.core.LinearSystem.sMetrics
            int r2 = r1.getNumVariables()
            long r2 = (long) r2
            r0.mVariables = r2
        L_0x0733:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.ConstraintWidget.addToSolver(androidx.constraintlayout.core.LinearSystem, boolean):void");
    }

    /* access modifiers changed from: package-private */
    public boolean addFirst() {
        return (this instanceof VirtualLayout) || (this instanceof Guideline);
    }

    public void setupDimensionRatio(boolean hParentWrapContent, boolean vParentWrapContent, boolean horizontalDimensionFixed, boolean verticalDimensionFixed) {
        if (this.mResolvedDimensionRatioSide == -1) {
            if (horizontalDimensionFixed && !verticalDimensionFixed) {
                this.mResolvedDimensionRatioSide = 0;
            } else if (!horizontalDimensionFixed && verticalDimensionFixed) {
                this.mResolvedDimensionRatioSide = 1;
                if (this.mDimensionRatioSide == -1) {
                    this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                }
            }
        }
        if (this.mResolvedDimensionRatioSide == 0 && (!this.mTop.isConnected() || !this.mBottom.isConnected())) {
            this.mResolvedDimensionRatioSide = 1;
        } else if (this.mResolvedDimensionRatioSide == 1 && (!this.mLeft.isConnected() || !this.mRight.isConnected())) {
            this.mResolvedDimensionRatioSide = 0;
        }
        if (this.mResolvedDimensionRatioSide == -1 && (!this.mTop.isConnected() || !this.mBottom.isConnected() || !this.mLeft.isConnected() || !this.mRight.isConnected())) {
            if (this.mTop.isConnected() && this.mBottom.isConnected()) {
                this.mResolvedDimensionRatioSide = 0;
            } else if (this.mLeft.isConnected() && this.mRight.isConnected()) {
                this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                this.mResolvedDimensionRatioSide = 1;
            }
        }
        if (this.mResolvedDimensionRatioSide != -1) {
            return;
        }
        if (this.mMatchConstraintMinWidth > 0 && this.mMatchConstraintMinHeight == 0) {
            this.mResolvedDimensionRatioSide = 0;
        } else if (this.mMatchConstraintMinWidth == 0 && this.mMatchConstraintMinHeight > 0) {
            this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
            this.mResolvedDimensionRatioSide = 1;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:323:0x0645 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void applyConstraints(androidx.constraintlayout.core.LinearSystem r35, boolean r36, boolean r37, boolean r38, boolean r39, androidx.constraintlayout.core.SolverVariable r40, androidx.constraintlayout.core.SolverVariable r41, androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour r42, boolean r43, androidx.constraintlayout.core.widgets.ConstraintAnchor r44, androidx.constraintlayout.core.widgets.ConstraintAnchor r45, int r46, int r47, int r48, int r49, float r50, boolean r51, boolean r52, boolean r53, boolean r54, boolean r55, int r56, int r57, int r58, int r59, float r60, boolean r61) {
        /*
            r34 = this;
            r0 = r34
            r1 = r35
            r12 = r44
            r13 = r45
            r14 = r48
            r15 = r49
            r3 = r58
            r4 = r59
            androidx.constraintlayout.core.SolverVariable r5 = r1.createObjectVariable(r12)
            androidx.constraintlayout.core.SolverVariable r7 = r1.createObjectVariable(r13)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r12.getTarget()
            androidx.constraintlayout.core.SolverVariable r9 = r1.createObjectVariable(r6)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r13.getTarget()
            androidx.constraintlayout.core.SolverVariable r6 = r1.createObjectVariable(r6)
            androidx.constraintlayout.core.Metrics r8 = androidx.constraintlayout.core.LinearSystem.getMetrics()
            if (r8 == 0) goto L_0x003a
            androidx.constraintlayout.core.Metrics r8 = androidx.constraintlayout.core.LinearSystem.getMetrics()
            long r10 = r8.nonresolvedWidgets
            r16 = 1
            long r10 = r10 + r16
            r8.nonresolvedWidgets = r10
        L_0x003a:
            boolean r10 = r12.isConnected()
            boolean r11 = r13.isConnected()
            androidx.constraintlayout.core.widgets.ConstraintAnchor r8 = r0.mCenter
            boolean r16 = r8.isConnected()
            r8 = 0
            r17 = 0
            if (r10 == 0) goto L_0x004f
            int r17 = r17 + 1
        L_0x004f:
            if (r11 == 0) goto L_0x0053
            int r17 = r17 + 1
        L_0x0053:
            if (r16 == 0) goto L_0x005c
            int r17 = r17 + 1
            r18 = r10
            r10 = r17
            goto L_0x0060
        L_0x005c:
            r18 = r10
            r10 = r17
        L_0x0060:
            if (r51 == 0) goto L_0x006b
            r17 = 3
            r33 = r17
            r17 = r11
            r11 = r33
            goto L_0x006f
        L_0x006b:
            r17 = r11
            r11 = r56
        L_0x006f:
            int r19 = r42.ordinal()
            switch(r19) {
                case 0: goto L_0x0083;
                case 1: goto L_0x0081;
                case 2: goto L_0x0079;
                case 3: goto L_0x0077;
                default: goto L_0x0076;
            }
        L_0x0076:
            goto L_0x0085
        L_0x0077:
            r8 = 0
            goto L_0x0085
        L_0x0079:
            r2 = 4
            if (r11 == r2) goto L_0x007e
            r2 = 1
            goto L_0x007f
        L_0x007e:
            r2 = 0
        L_0x007f:
            r8 = r2
            goto L_0x0085
        L_0x0081:
            r8 = 0
            goto L_0x0085
        L_0x0083:
            r8 = 0
        L_0x0085:
            int r2 = r0.mWidthOverride
            r13 = -1
            if (r2 == r13) goto L_0x0092
            if (r36 == 0) goto L_0x0092
            r8 = 0
            int r2 = r0.mWidthOverride
            r0.mWidthOverride = r13
            goto L_0x0094
        L_0x0092:
            r2 = r47
        L_0x0094:
            r47 = r2
            int r2 = r0.mHeightOverride
            if (r2 == r13) goto L_0x00a2
            if (r36 != 0) goto L_0x00a2
            r8 = 0
            int r2 = r0.mHeightOverride
            r0.mHeightOverride = r13
            goto L_0x00a4
        L_0x00a2:
            r2 = r47
        L_0x00a4:
            int r13 = r0.mVisibility
            r47 = r2
            r2 = 8
            if (r13 != r2) goto L_0x00b1
            r13 = 0
            r8 = 0
            r21 = r8
            goto L_0x00b5
        L_0x00b1:
            r13 = r47
            r21 = r8
        L_0x00b5:
            if (r61 == 0) goto L_0x00d9
            if (r18 != 0) goto L_0x00c5
            if (r17 != 0) goto L_0x00c5
            if (r16 != 0) goto L_0x00c5
            r8 = r46
            r1.addEquality(r5, r8)
            r22 = r6
            goto L_0x00dd
        L_0x00c5:
            r8 = r46
            if (r18 == 0) goto L_0x00d6
            if (r17 != 0) goto L_0x00d6
            r22 = r6
            int r6 = r12.getMargin()
            r1.addEquality(r5, r9, r6, r2)
            goto L_0x00dd
        L_0x00d6:
            r22 = r6
            goto L_0x00dd
        L_0x00d9:
            r8 = r46
            r22 = r6
        L_0x00dd:
            r6 = 3
            if (r21 != 0) goto L_0x010d
            if (r43 == 0) goto L_0x00f9
            r2 = 0
            r1.addEquality(r7, r5, r2, r6)
            if (r14 <= 0) goto L_0x00ee
            r2 = 8
            r1.addGreaterThan(r7, r5, r14, r2)
            goto L_0x00f0
        L_0x00ee:
            r2 = 8
        L_0x00f0:
            r6 = 2147483647(0x7fffffff, float:NaN)
            if (r15 >= r6) goto L_0x00fe
            r1.addLowerThan(r7, r5, r15, r2)
            goto L_0x00fe
        L_0x00f9:
            r2 = 8
            r1.addEquality(r7, r5, r13, r2)
        L_0x00fe:
            r25 = r4
            r26 = r13
            r27 = r21
            r2 = r22
            r13 = 5
            r21 = r39
            r22 = r3
            goto L_0x021e
        L_0x010d:
            r2 = 2
            if (r10 == r2) goto L_0x0137
            if (r51 != 0) goto L_0x0137
            r2 = 1
            if (r11 == r2) goto L_0x0117
            if (r11 != 0) goto L_0x0137
        L_0x0117:
            r21 = 0
            int r2 = java.lang.Math.max(r3, r13)
            if (r4 <= 0) goto L_0x0123
            int r2 = java.lang.Math.min(r4, r2)
        L_0x0123:
            r6 = 8
            r1.addEquality(r7, r5, r2, r6)
            r25 = r4
            r26 = r13
            r27 = r21
            r2 = r22
            r13 = 5
            r21 = r39
            r22 = r3
            goto L_0x021e
        L_0x0137:
            r2 = -2
            if (r3 != r2) goto L_0x013b
            r3 = r13
        L_0x013b:
            if (r4 != r2) goto L_0x013f
            r2 = r13
            goto L_0x0140
        L_0x013f:
            r2 = r4
        L_0x0140:
            if (r13 <= 0) goto L_0x0146
            r4 = 1
            if (r11 == r4) goto L_0x0146
            r13 = 0
        L_0x0146:
            if (r3 <= 0) goto L_0x0151
            r6 = 8
            r1.addGreaterThan(r7, r5, r3, r6)
            int r13 = java.lang.Math.max(r13, r3)
        L_0x0151:
            if (r2 <= 0) goto L_0x0166
            r4 = 1
            if (r37 == 0) goto L_0x015a
            r6 = 1
            if (r11 != r6) goto L_0x015a
            r4 = 0
        L_0x015a:
            if (r4 == 0) goto L_0x0161
            r6 = 8
            r1.addLowerThan(r7, r5, r2, r6)
        L_0x0161:
            int r6 = java.lang.Math.min(r13, r2)
            r13 = r6
        L_0x0166:
            r4 = 1
            if (r11 != r4) goto L_0x0194
            if (r37 == 0) goto L_0x0172
            r6 = 8
            r1.addEquality(r7, r5, r13, r6)
            r4 = 5
            goto L_0x0185
        L_0x0172:
            r6 = 8
            if (r53 == 0) goto L_0x017e
            r4 = 5
            r1.addEquality(r7, r5, r13, r4)
            r1.addLowerThan(r7, r5, r13, r6)
            goto L_0x0185
        L_0x017e:
            r4 = 5
            r1.addEquality(r7, r5, r13, r4)
            r1.addLowerThan(r7, r5, r13, r6)
        L_0x0185:
            r25 = r2
            r26 = r13
            r27 = r21
            r2 = r22
            r21 = r39
            r22 = r3
            r13 = r4
            goto L_0x021e
        L_0x0194:
            r4 = 5
            r6 = 2
            if (r11 != r6) goto L_0x020c
            r6 = 0
            r26 = 0
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r4 = r12.getType()
            r58 = r2
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r2 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.TOP
            if (r4 == r2) goto L_0x01c8
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r2 = r12.getType()
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r4 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.BOTTOM
            if (r2 != r4) goto L_0x01ae
            goto L_0x01c8
        L_0x01ae:
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r4 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.LEFT
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.getAnchor(r4)
            androidx.constraintlayout.core.SolverVariable r2 = r1.createObjectVariable(r2)
            androidx.constraintlayout.core.widgets.ConstraintWidget r4 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r6 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.RIGHT
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r4.getAnchor(r6)
            androidx.constraintlayout.core.SolverVariable r4 = r1.createObjectVariable(r4)
            r6 = r4
            goto L_0x01e1
        L_0x01c8:
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r4 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.TOP
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.getAnchor(r4)
            androidx.constraintlayout.core.SolverVariable r2 = r1.createObjectVariable(r2)
            androidx.constraintlayout.core.widgets.ConstraintWidget r4 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r6 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.BOTTOM
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r4.getAnchor(r6)
            androidx.constraintlayout.core.SolverVariable r4 = r1.createObjectVariable(r4)
            r6 = r4
        L_0x01e1:
            r4 = r3
            androidx.constraintlayout.core.ArrayRow r3 = r1.createRow()
            r8 = r7
            r7 = r2
            r2 = r22
            r22 = r4
            r4 = r8
            r8 = r60
            r25 = r13
            r13 = 5
            androidx.constraintlayout.core.ArrayRow r3 = r3.createRowDimensionRatio(r4, r5, r6, r7, r8)
            r33 = r7
            r7 = r4
            r4 = r33
            r1.addConstraint(r3)
            if (r37 == 0) goto L_0x0203
            r3 = 0
            r21 = r3
        L_0x0203:
            r27 = r21
            r26 = r25
            r21 = r39
            r25 = r58
            goto L_0x021e
        L_0x020c:
            r58 = r2
            r25 = r13
            r2 = r22
            r22 = r3
            r13 = r4
            r3 = 1
            r27 = r21
            r26 = r25
            r25 = r58
            r21 = r3
        L_0x021e:
            if (r61 == 0) goto L_0x0679
            if (r53 == 0) goto L_0x022f
            r15 = r40
            r4 = r41
            r12 = r45
            r6 = r2
            r3 = r9
            r20 = r10
            r9 = r11
            goto L_0x0684
        L_0x022f:
            r3 = 5
            if (r18 != 0) goto L_0x0240
            if (r17 != 0) goto L_0x0240
            if (r16 != 0) goto L_0x0240
            r15 = r40
            r6 = r2
            r14 = r3
            r3 = r9
            r20 = r10
            r9 = r11
            goto L_0x0641
        L_0x0240:
            if (r18 == 0) goto L_0x025c
            if (r17 != 0) goto L_0x025c
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r12.mTarget
            androidx.constraintlayout.core.widgets.ConstraintWidget r4 = r4.mOwner
            if (r37 == 0) goto L_0x0250
            boolean r6 = r4 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r6 == 0) goto L_0x0250
            r3 = 8
        L_0x0250:
            r24 = r37
            r15 = r40
            r6 = r2
            r14 = r3
            r3 = r9
            r20 = r10
            r9 = r11
            goto L_0x0643
        L_0x025c:
            if (r18 != 0) goto L_0x02aa
            if (r17 == 0) goto L_0x02aa
            int r4 = r45.getMargin()
            int r4 = -r4
            r6 = 8
            r1.addEquality(r7, r2, r4, r6)
            if (r37 == 0) goto L_0x029f
            boolean r4 = r0.mOptimizeWrapO
            if (r4 == 0) goto L_0x0290
            boolean r4 = r5.isFinalValue
            if (r4 == 0) goto L_0x0290
            androidx.constraintlayout.core.widgets.ConstraintWidget r4 = r0.mParent
            if (r4 == 0) goto L_0x0290
            androidx.constraintlayout.core.widgets.ConstraintWidget r4 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r4 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r4
            if (r36 == 0) goto L_0x0283
            r4.addHorizontalWrapMinVariable(r12)
            goto L_0x0286
        L_0x0283:
            r4.addVerticalWrapMinVariable(r12)
        L_0x0286:
            r15 = r40
            r6 = r2
            r14 = r3
            r3 = r9
            r20 = r10
            r9 = r11
            goto L_0x0641
        L_0x0290:
            r4 = r40
            r6 = 0
            r1.addGreaterThan(r5, r4, r6, r13)
            r6 = r2
            r14 = r3
            r15 = r4
            r3 = r9
            r20 = r10
            r9 = r11
            goto L_0x0641
        L_0x029f:
            r4 = r40
            r6 = r2
            r14 = r3
            r15 = r4
            r3 = r9
            r20 = r10
            r9 = r11
            goto L_0x0641
        L_0x02aa:
            r4 = r40
            r6 = 0
            if (r18 == 0) goto L_0x063a
            if (r17 == 0) goto L_0x063a
            r13 = 1
            r8 = 0
            r28 = 0
            r29 = 0
            r30 = 5
            r31 = 4
            r32 = 6
            if (r37 == 0) goto L_0x02c1
            r30 = 5
        L_0x02c1:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r12.mTarget
            androidx.constraintlayout.core.widgets.ConstraintWidget r6 = r6.mOwner
            r12 = r45
            r39 = r3
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r12.mTarget
            androidx.constraintlayout.core.widgets.ConstraintWidget r3 = r3.mOwner
            r47 = r13
            androidx.constraintlayout.core.widgets.ConstraintWidget r13 = r0.getParent()
            if (r27 == 0) goto L_0x04cd
            if (r11 != 0) goto L_0x0350
            if (r25 != 0) goto L_0x030c
            if (r22 != 0) goto L_0x030c
            r28 = 1
            r19 = 8
            r20 = 8
            boolean r4 = r9.isFinalValue
            if (r4 == 0) goto L_0x02ff
            boolean r4 = r2.isFinalValue
            if (r4 == 0) goto L_0x02ff
            int r4 = r44.getMargin()
            r58 = r8
            r8 = 8
            r1.addEquality(r5, r9, r4, r8)
            int r4 = r12.getMargin()
            int r4 = -r4
            r1.addEquality(r7, r2, r4, r8)
            return
        L_0x02ff:
            r58 = r8
            r8 = 8
            r23 = r47
            r4 = r58
            r30 = r19
            r31 = r20
            goto L_0x031d
        L_0x030c:
            r58 = r8
            r8 = 8
            r4 = 1
            r19 = 5
            r20 = 5
            r23 = 1
            r29 = 1
            r30 = r19
            r31 = r20
        L_0x031d:
            boolean r8 = r6 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r8 != 0) goto L_0x033a
            boolean r8 = r3 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r8 == 0) goto L_0x0326
            goto L_0x033a
        L_0x0326:
            r14 = r39
            r15 = r40
            r19 = r4
            r20 = r10
            r4 = r41
            r10 = r6
            r6 = r2
            r2 = r23
            r23 = r11
            r11 = r3
            r3 = r9
            goto L_0x0527
        L_0x033a:
            r31 = 4
            r14 = r39
            r15 = r40
            r19 = r4
            r20 = r10
            r4 = r41
            r10 = r6
            r6 = r2
            r2 = r23
            r23 = r11
            r11 = r3
            r3 = r9
            goto L_0x0527
        L_0x0350:
            r58 = r8
            r4 = 2
            if (r11 != r4) goto L_0x0392
            r8 = 1
            r30 = 5
            r31 = 5
            r4 = 1
            r29 = 1
            boolean r1 = r6 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r1 != 0) goto L_0x037b
            boolean r1 = r3 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r1 == 0) goto L_0x0366
            goto L_0x037b
        L_0x0366:
            r1 = r35
            r14 = r39
            r15 = r40
            r19 = r8
            r20 = r10
            r23 = r11
            r11 = r3
            r10 = r6
            r3 = r9
            r6 = r2
            r2 = r4
            r4 = r41
            goto L_0x0527
        L_0x037b:
            r31 = 4
            r1 = r35
            r14 = r39
            r15 = r40
            r19 = r8
            r20 = r10
            r23 = r11
            r11 = r3
            r10 = r6
            r3 = r9
            r6 = r2
            r2 = r4
            r4 = r41
            goto L_0x0527
        L_0x0392:
            r4 = 1
            if (r11 != r4) goto L_0x03b0
            r8 = 1
            r29 = 1
            r30 = 8
            r1 = r35
            r14 = r39
            r15 = r40
            r4 = r41
            r19 = r8
            r20 = r10
            r23 = r11
            r11 = r3
            r10 = r6
            r3 = r9
            r6 = r2
            r2 = r47
            goto L_0x0527
        L_0x03b0:
            r1 = 3
            if (r11 != r1) goto L_0x04b5
            int r4 = r0.mResolvedDimensionRatioSide
            r8 = -1
            if (r4 != r8) goto L_0x040f
            r8 = 1
            r29 = 1
            r28 = 1
            r30 = 8
            r31 = 5
            if (r54 == 0) goto L_0x03f7
            r31 = 5
            r32 = 4
            if (r37 == 0) goto L_0x03e1
            r32 = 5
            r1 = r35
            r14 = r39
            r15 = r40
            r4 = r41
            r19 = r8
            r20 = r10
            r23 = r11
            r11 = r3
            r10 = r6
            r3 = r9
            r6 = r2
            r2 = r47
            goto L_0x0527
        L_0x03e1:
            r1 = r35
            r14 = r39
            r15 = r40
            r4 = r41
            r19 = r8
            r20 = r10
            r23 = r11
            r11 = r3
            r10 = r6
            r3 = r9
            r6 = r2
            r2 = r47
            goto L_0x0527
        L_0x03f7:
            r32 = 8
            r1 = r35
            r14 = r39
            r15 = r40
            r4 = r41
            r19 = r8
            r20 = r10
            r23 = r11
            r11 = r3
            r10 = r6
            r3 = r9
            r6 = r2
            r2 = r47
            goto L_0x0527
        L_0x040f:
            r8 = 1
            r29 = 1
            r28 = 1
            if (r51 == 0) goto L_0x0440
            r4 = r57
            r1 = 2
            if (r4 == r1) goto L_0x0422
            r1 = 1
            if (r4 != r1) goto L_0x041f
            goto L_0x0422
        L_0x041f:
            r19 = 0
            goto L_0x0424
        L_0x0422:
            r19 = 1
        L_0x0424:
            if (r19 != 0) goto L_0x042a
            r30 = 8
            r31 = 5
        L_0x042a:
            r1 = r35
            r14 = r39
            r15 = r40
            r4 = r41
            r19 = r8
            r20 = r10
            r23 = r11
            r11 = r3
            r10 = r6
            r3 = r9
            r6 = r2
            r2 = r47
            goto L_0x0527
        L_0x0440:
            r4 = r57
            r30 = 5
            if (r25 <= 0) goto L_0x045e
            r31 = 5
            r1 = r35
            r14 = r39
            r15 = r40
            r4 = r41
            r19 = r8
            r20 = r10
            r23 = r11
            r11 = r3
            r10 = r6
            r3 = r9
            r6 = r2
            r2 = r47
            goto L_0x0527
        L_0x045e:
            if (r25 != 0) goto L_0x049f
            if (r22 != 0) goto L_0x049f
            if (r54 != 0) goto L_0x047c
            r31 = 8
            r1 = r35
            r14 = r39
            r15 = r40
            r4 = r41
            r19 = r8
            r20 = r10
            r23 = r11
            r11 = r3
            r10 = r6
            r3 = r9
            r6 = r2
            r2 = r47
            goto L_0x0527
        L_0x047c:
            if (r6 == r13) goto L_0x0484
            if (r3 == r13) goto L_0x0484
            r1 = 4
            r30 = r1
            goto L_0x0487
        L_0x0484:
            r1 = 5
            r30 = r1
        L_0x0487:
            r31 = 4
            r1 = r35
            r14 = r39
            r15 = r40
            r4 = r41
            r19 = r8
            r20 = r10
            r23 = r11
            r11 = r3
            r10 = r6
            r3 = r9
            r6 = r2
            r2 = r47
            goto L_0x0527
        L_0x049f:
            r1 = r35
            r14 = r39
            r15 = r40
            r4 = r41
            r19 = r8
            r20 = r10
            r23 = r11
            r11 = r3
            r10 = r6
            r3 = r9
            r6 = r2
            r2 = r47
            goto L_0x0527
        L_0x04b5:
            r4 = r57
            r1 = r35
            r14 = r39
            r15 = r40
            r4 = r41
            r19 = r58
            r20 = r10
            r23 = r11
            r11 = r3
            r10 = r6
            r3 = r9
            r6 = r2
            r2 = r47
            goto L_0x0527
        L_0x04cd:
            r4 = r57
            r58 = r8
            r19 = 1
            r29 = 1
            boolean r1 = r9.isFinalValue
            if (r1 == 0) goto L_0x0515
            boolean r1 = r2.isFinalValue
            if (r1 == 0) goto L_0x0515
            int r4 = r44.getMargin()
            int r8 = r12.getMargin()
            r1 = r3
            r3 = r9
            r9 = 8
            r14 = r39
            r15 = r40
            r20 = r10
            r23 = r11
            r11 = r1
            r10 = r6
            r1 = r35
            r6 = r2
            r2 = r5
            r5 = r50
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            r5 = r2
            if (r37 == 0) goto L_0x0512
            if (r21 == 0) goto L_0x0512
            r2 = 0
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r12.mTarget
            if (r4 == 0) goto L_0x050a
            int r2 = r12.getMargin()
        L_0x050a:
            r4 = r41
            if (r6 == r4) goto L_0x0514
            r1.addGreaterThan(r4, r7, r2, r14)
            goto L_0x0514
        L_0x0512:
            r4 = r41
        L_0x0514:
            return
        L_0x0515:
            r1 = r35
            r14 = r39
            r15 = r40
            r4 = r41
            r20 = r10
            r23 = r11
            r11 = r3
            r10 = r6
            r3 = r9
            r6 = r2
            r2 = r47
        L_0x0527:
            if (r29 == 0) goto L_0x0530
            if (r3 != r6) goto L_0x0530
            if (r10 == r13) goto L_0x0530
            r29 = 0
            r2 = 0
        L_0x0530:
            if (r19 == 0) goto L_0x056b
            if (r27 != 0) goto L_0x054b
            if (r52 != 0) goto L_0x054b
            if (r54 != 0) goto L_0x054b
            if (r3 != r15) goto L_0x054b
            if (r6 != r4) goto L_0x054b
            r32 = 8
            r8 = 8
            r2 = 0
            r9 = 0
            r24 = r9
            r9 = r32
            r32 = r8
            r30 = r2
            goto L_0x0553
        L_0x054b:
            r24 = r37
            r9 = r32
            r32 = r30
            r30 = r2
        L_0x0553:
            int r4 = r44.getMargin()
            int r8 = r12.getMargin()
            r2 = r5
            r12 = 8
            r5 = r50
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            r5 = r2
            r2 = r30
            r30 = r32
            r32 = r9
            goto L_0x056f
        L_0x056b:
            r12 = 8
            r24 = r37
        L_0x056f:
            int r4 = r0.mVisibility
            if (r4 != r12) goto L_0x057a
            boolean r4 = r45.hasDependents()
            if (r4 != 0) goto L_0x057a
            return
        L_0x057a:
            if (r29 == 0) goto L_0x05a0
            if (r24 == 0) goto L_0x058c
            if (r3 == r6) goto L_0x058c
            if (r27 != 0) goto L_0x058c
            boolean r4 = r10 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r4 != 0) goto L_0x058a
            boolean r4 = r11 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r4 == 0) goto L_0x058c
        L_0x058a:
            r4 = 6
            goto L_0x058e
        L_0x058c:
            r4 = r30
        L_0x058e:
            int r8 = r44.getMargin()
            r1.addGreaterThan(r5, r3, r8, r4)
            int r8 = r45.getMargin()
            int r8 = -r8
            r1.addLowerThan(r7, r6, r8, r4)
            r30 = r4
        L_0x05a0:
            if (r24 == 0) goto L_0x05b8
            if (r55 == 0) goto L_0x05b8
            boolean r4 = r10 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r4 != 0) goto L_0x05b8
            boolean r4 = r11 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r4 != 0) goto L_0x05b8
            if (r11 == r13) goto L_0x05b8
            r31 = 6
            r30 = 6
            r2 = 1
            r4 = r30
            r8 = r31
            goto L_0x05bc
        L_0x05b8:
            r4 = r30
            r8 = r31
        L_0x05bc:
            if (r2 == 0) goto L_0x0602
            if (r28 == 0) goto L_0x05e3
            if (r54 == 0) goto L_0x05c4
            if (r38 == 0) goto L_0x05e3
        L_0x05c4:
            r9 = r8
            if (r10 == r13) goto L_0x05c9
            if (r11 != r13) goto L_0x05ca
        L_0x05c9:
            r9 = 6
        L_0x05ca:
            boolean r12 = r10 instanceof androidx.constraintlayout.core.widgets.Guideline
            if (r12 != 0) goto L_0x05d2
            boolean r12 = r11 instanceof androidx.constraintlayout.core.widgets.Guideline
            if (r12 == 0) goto L_0x05d3
        L_0x05d2:
            r9 = 5
        L_0x05d3:
            boolean r12 = r10 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r12 != 0) goto L_0x05db
            boolean r12 = r11 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r12 == 0) goto L_0x05dc
        L_0x05db:
            r9 = 5
        L_0x05dc:
            if (r54 == 0) goto L_0x05df
            r9 = 5
        L_0x05df:
            int r8 = java.lang.Math.max(r9, r8)
        L_0x05e3:
            if (r24 == 0) goto L_0x05f2
            int r8 = java.lang.Math.min(r4, r8)
            if (r51 == 0) goto L_0x05f2
            if (r54 != 0) goto L_0x05f2
            if (r10 == r13) goto L_0x05f1
            if (r11 != r13) goto L_0x05f2
        L_0x05f1:
            r8 = 4
        L_0x05f2:
            int r9 = r44.getMargin()
            r1.addEquality(r5, r3, r9, r8)
            int r9 = r45.getMargin()
            int r9 = -r9
            r1.addEquality(r7, r6, r9, r8)
        L_0x0602:
            if (r24 == 0) goto L_0x0610
            r9 = 0
            if (r15 != r3) goto L_0x060b
            int r9 = r44.getMargin()
        L_0x060b:
            if (r3 == r15) goto L_0x0610
            r1.addGreaterThan(r5, r15, r9, r14)
        L_0x0610:
            if (r24 == 0) goto L_0x0635
            if (r27 == 0) goto L_0x0635
            if (r48 != 0) goto L_0x0635
            if (r22 != 0) goto L_0x0635
            if (r27 == 0) goto L_0x062c
            r9 = r23
            r12 = 3
            if (r9 != r12) goto L_0x0628
            r37 = r2
            r2 = 8
            r12 = 0
            r1.addGreaterThan(r7, r5, r12, r2)
            goto L_0x0643
        L_0x0628:
            r37 = r2
            r12 = 0
            goto L_0x0631
        L_0x062c:
            r37 = r2
            r9 = r23
            r12 = 0
        L_0x0631:
            r1.addGreaterThan(r7, r5, r12, r14)
            goto L_0x0643
        L_0x0635:
            r37 = r2
            r9 = r23
            goto L_0x0643
        L_0x063a:
            r6 = r2
            r14 = r3
            r15 = r4
            r3 = r9
            r20 = r10
            r9 = r11
        L_0x0641:
            r24 = r37
        L_0x0643:
            if (r24 == 0) goto L_0x0674
            if (r21 == 0) goto L_0x0674
            r2 = 0
            r12 = r45
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r12.mTarget
            if (r4 == 0) goto L_0x0652
            int r2 = r12.getMargin()
        L_0x0652:
            r4 = r41
            if (r6 == r4) goto L_0x0678
            boolean r8 = r0.mOptimizeWrapO
            if (r8 == 0) goto L_0x0670
            boolean r8 = r7.isFinalValue
            if (r8 == 0) goto L_0x0670
            androidx.constraintlayout.core.widgets.ConstraintWidget r8 = r0.mParent
            if (r8 == 0) goto L_0x0670
            androidx.constraintlayout.core.widgets.ConstraintWidget r8 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r8 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r8
            if (r36 == 0) goto L_0x066c
            r8.addHorizontalWrapMaxVariable(r12)
            goto L_0x066f
        L_0x066c:
            r8.addVerticalWrapMaxVariable(r12)
        L_0x066f:
            return
        L_0x0670:
            r1.addGreaterThan(r4, r7, r2, r14)
            goto L_0x0678
        L_0x0674:
            r4 = r41
            r12 = r45
        L_0x0678:
            return
        L_0x0679:
            r15 = r40
            r4 = r41
            r12 = r45
            r6 = r2
            r3 = r9
            r20 = r10
            r9 = r11
        L_0x0684:
            r2 = r20
            r8 = 2
            if (r2 >= r8) goto L_0x06d2
            if (r37 == 0) goto L_0x06d2
            if (r21 == 0) goto L_0x06d2
            r8 = 0
            r10 = 8
            r1.addGreaterThan(r5, r15, r8, r10)
            if (r36 != 0) goto L_0x069e
            androidx.constraintlayout.core.widgets.ConstraintAnchor r8 = r0.mBaseline
            androidx.constraintlayout.core.widgets.ConstraintAnchor r8 = r8.mTarget
            if (r8 != 0) goto L_0x069c
            goto L_0x069e
        L_0x069c:
            r8 = 0
            goto L_0x069f
        L_0x069e:
            r8 = 1
        L_0x069f:
            if (r36 != 0) goto L_0x06ca
            androidx.constraintlayout.core.widgets.ConstraintAnchor r10 = r0.mBaseline
            androidx.constraintlayout.core.widgets.ConstraintAnchor r10 = r10.mTarget
            if (r10 == 0) goto L_0x06ca
            androidx.constraintlayout.core.widgets.ConstraintAnchor r10 = r0.mBaseline
            androidx.constraintlayout.core.widgets.ConstraintAnchor r10 = r10.mTarget
            androidx.constraintlayout.core.widgets.ConstraintWidget r10 = r10.mOwner
            float r11 = r10.mDimensionRatio
            r13 = 0
            int r11 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r11 == 0) goto L_0x06c9
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r11 = r10.mListDimensionBehaviors
            r13 = 0
            r11 = r11[r13]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r13 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r11 != r13) goto L_0x06c9
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r11 = r10.mListDimensionBehaviors
            r19 = 1
            r11 = r11[r19]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r13 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r11 != r13) goto L_0x06c9
            r8 = 1
            goto L_0x06ca
        L_0x06c9:
            r8 = 0
        L_0x06ca:
            if (r8 == 0) goto L_0x06d2
            r10 = 8
            r13 = 0
            r1.addGreaterThan(r4, r7, r13, r10)
        L_0x06d2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.ConstraintWidget.applyConstraints(androidx.constraintlayout.core.LinearSystem, boolean, boolean, boolean, boolean, androidx.constraintlayout.core.SolverVariable, androidx.constraintlayout.core.SolverVariable, androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour, boolean, androidx.constraintlayout.core.widgets.ConstraintAnchor, androidx.constraintlayout.core.widgets.ConstraintAnchor, int, int, int, int, float, boolean, boolean, boolean, boolean, boolean, int, int, int, int, float, boolean):void");
    }

    public void updateFromSolver(LinearSystem system, boolean optimize) {
        int left = system.getObjectVariableValue(this.mLeft);
        int top = system.getObjectVariableValue(this.mTop);
        int right = system.getObjectVariableValue(this.mRight);
        int bottom = system.getObjectVariableValue(this.mBottom);
        if (optimize && this.mHorizontalRun != null && this.mHorizontalRun.start.resolved && this.mHorizontalRun.end.resolved) {
            left = this.mHorizontalRun.start.value;
            right = this.mHorizontalRun.end.value;
        }
        if (optimize && this.mVerticalRun != null && this.mVerticalRun.start.resolved && this.mVerticalRun.end.resolved) {
            top = this.mVerticalRun.start.value;
            bottom = this.mVerticalRun.end.value;
        }
        int h = bottom - top;
        if (right - left < 0 || h < 0 || left == Integer.MIN_VALUE || left == Integer.MAX_VALUE || top == Integer.MIN_VALUE || top == Integer.MAX_VALUE || right == Integer.MIN_VALUE || right == Integer.MAX_VALUE || bottom == Integer.MIN_VALUE || bottom == Integer.MAX_VALUE) {
            left = 0;
            top = 0;
            right = 0;
            bottom = 0;
        }
        setFrame(left, top, right, bottom);
    }

    public void copy(ConstraintWidget src, HashMap<ConstraintWidget, ConstraintWidget> map) {
        this.mHorizontalResolution = src.mHorizontalResolution;
        this.mVerticalResolution = src.mVerticalResolution;
        this.mMatchConstraintDefaultWidth = src.mMatchConstraintDefaultWidth;
        this.mMatchConstraintDefaultHeight = src.mMatchConstraintDefaultHeight;
        this.mResolvedMatchConstraintDefault[0] = src.mResolvedMatchConstraintDefault[0];
        this.mResolvedMatchConstraintDefault[1] = src.mResolvedMatchConstraintDefault[1];
        this.mMatchConstraintMinWidth = src.mMatchConstraintMinWidth;
        this.mMatchConstraintMaxWidth = src.mMatchConstraintMaxWidth;
        this.mMatchConstraintMinHeight = src.mMatchConstraintMinHeight;
        this.mMatchConstraintMaxHeight = src.mMatchConstraintMaxHeight;
        this.mMatchConstraintPercentHeight = src.mMatchConstraintPercentHeight;
        this.mIsWidthWrapContent = src.mIsWidthWrapContent;
        this.mIsHeightWrapContent = src.mIsHeightWrapContent;
        this.mResolvedDimensionRatioSide = src.mResolvedDimensionRatioSide;
        this.mResolvedDimensionRatio = src.mResolvedDimensionRatio;
        this.mMaxDimension = Arrays.copyOf(src.mMaxDimension, src.mMaxDimension.length);
        this.mCircleConstraintAngle = src.mCircleConstraintAngle;
        this.mHasBaseline = src.mHasBaseline;
        this.mInPlaceholder = src.mInPlaceholder;
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mListDimensionBehaviors = (DimensionBehaviour[]) Arrays.copyOf(this.mListDimensionBehaviors, 2);
        ConstraintWidget constraintWidget = null;
        this.mParent = this.mParent == null ? null : map.get(src.mParent);
        this.mWidth = src.mWidth;
        this.mHeight = src.mHeight;
        this.mDimensionRatio = src.mDimensionRatio;
        this.mDimensionRatioSide = src.mDimensionRatioSide;
        this.mX = src.mX;
        this.mY = src.mY;
        this.mRelX = src.mRelX;
        this.mRelY = src.mRelY;
        this.mOffsetX = src.mOffsetX;
        this.mOffsetY = src.mOffsetY;
        this.mBaselineDistance = src.mBaselineDistance;
        this.mMinWidth = src.mMinWidth;
        this.mMinHeight = src.mMinHeight;
        this.mHorizontalBiasPercent = src.mHorizontalBiasPercent;
        this.mVerticalBiasPercent = src.mVerticalBiasPercent;
        this.mCompanionWidget = src.mCompanionWidget;
        this.mContainerItemSkip = src.mContainerItemSkip;
        this.mVisibility = src.mVisibility;
        this.mAnimated = src.mAnimated;
        this.mDebugName = src.mDebugName;
        this.mType = src.mType;
        this.mDistToTop = src.mDistToTop;
        this.mDistToLeft = src.mDistToLeft;
        this.mDistToRight = src.mDistToRight;
        this.mDistToBottom = src.mDistToBottom;
        this.mLeftHasCentered = src.mLeftHasCentered;
        this.mRightHasCentered = src.mRightHasCentered;
        this.mTopHasCentered = src.mTopHasCentered;
        this.mBottomHasCentered = src.mBottomHasCentered;
        this.mHorizontalWrapVisited = src.mHorizontalWrapVisited;
        this.mVerticalWrapVisited = src.mVerticalWrapVisited;
        this.mHorizontalChainStyle = src.mHorizontalChainStyle;
        this.mVerticalChainStyle = src.mVerticalChainStyle;
        this.mHorizontalChainFixedPosition = src.mHorizontalChainFixedPosition;
        this.mVerticalChainFixedPosition = src.mVerticalChainFixedPosition;
        this.mWeight[0] = src.mWeight[0];
        this.mWeight[1] = src.mWeight[1];
        this.mListNextMatchConstraintsWidget[0] = src.mListNextMatchConstraintsWidget[0];
        this.mListNextMatchConstraintsWidget[1] = src.mListNextMatchConstraintsWidget[1];
        this.mNextChainWidget[0] = src.mNextChainWidget[0];
        this.mNextChainWidget[1] = src.mNextChainWidget[1];
        this.mHorizontalNextWidget = src.mHorizontalNextWidget == null ? null : map.get(src.mHorizontalNextWidget);
        if (src.mVerticalNextWidget != null) {
            constraintWidget = map.get(src.mVerticalNextWidget);
        }
        this.mVerticalNextWidget = constraintWidget;
    }

    public void updateFromRuns(boolean updateHorizontal, boolean updateVertical) {
        boolean updateHorizontal2 = updateHorizontal & this.mHorizontalRun.isResolved();
        boolean updateVertical2 = updateVertical & this.mVerticalRun.isResolved();
        int left = this.mHorizontalRun.start.value;
        int top = this.mVerticalRun.start.value;
        int right = this.mHorizontalRun.end.value;
        int bottom = this.mVerticalRun.end.value;
        int h = bottom - top;
        if (right - left < 0 || h < 0 || left == Integer.MIN_VALUE || left == Integer.MAX_VALUE || top == Integer.MIN_VALUE || top == Integer.MAX_VALUE || right == Integer.MIN_VALUE || right == Integer.MAX_VALUE || bottom == Integer.MIN_VALUE || bottom == Integer.MAX_VALUE) {
            left = 0;
            top = 0;
            right = 0;
            bottom = 0;
        }
        int w = right - left;
        int h2 = bottom - top;
        if (updateHorizontal2) {
            this.mX = left;
        }
        if (updateVertical2) {
            this.mY = top;
        }
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        if (updateHorizontal2) {
            if (this.mListDimensionBehaviors[0] == DimensionBehaviour.FIXED && w < this.mWidth) {
                w = this.mWidth;
            }
            this.mWidth = w;
            if (this.mWidth < this.mMinWidth) {
                this.mWidth = this.mMinWidth;
            }
        }
        if (updateVertical2) {
            if (this.mListDimensionBehaviors[1] == DimensionBehaviour.FIXED && h2 < this.mHeight) {
                h2 = this.mHeight;
            }
            this.mHeight = h2;
            if (this.mHeight < this.mMinHeight) {
                this.mHeight = this.mMinHeight;
            }
        }
    }

    public void addChildrenToSolverByDependency(ConstraintWidgetContainer container, LinearSystem system, HashSet<ConstraintWidget> widgets, int orientation, boolean addSelf) {
        if (addSelf) {
            if (widgets.contains(this)) {
                Optimizer.checkMatchParent(container, system, this);
                widgets.remove(this);
                addToSolver(system, container.optimizeFor(64));
            } else {
                return;
            }
        }
        if (orientation == 0) {
            HashSet<ConstraintAnchor> dependents = this.mLeft.getDependents();
            if (dependents != null) {
                Iterator<ConstraintAnchor> it = dependents.iterator();
                while (it.hasNext()) {
                    it.next().mOwner.addChildrenToSolverByDependency(container, system, widgets, orientation, true);
                }
            }
            HashSet<ConstraintAnchor> dependents2 = this.mRight.getDependents();
            if (dependents2 != null) {
                Iterator<ConstraintAnchor> it2 = dependents2.iterator();
                while (it2.hasNext()) {
                    it2.next().mOwner.addChildrenToSolverByDependency(container, system, widgets, orientation, true);
                }
                return;
            }
            return;
        }
        HashSet<ConstraintAnchor> dependents3 = this.mTop.getDependents();
        if (dependents3 != null) {
            Iterator<ConstraintAnchor> it3 = dependents3.iterator();
            while (it3.hasNext()) {
                it3.next().mOwner.addChildrenToSolverByDependency(container, system, widgets, orientation, true);
            }
        }
        HashSet<ConstraintAnchor> dependents4 = this.mBottom.getDependents();
        if (dependents4 != null) {
            Iterator<ConstraintAnchor> it4 = dependents4.iterator();
            while (it4.hasNext()) {
                it4.next().mOwner.addChildrenToSolverByDependency(container, system, widgets, orientation, true);
            }
        }
        HashSet<ConstraintAnchor> dependents5 = this.mBaseline.getDependents();
        if (dependents5 != null) {
            Iterator<ConstraintAnchor> it5 = dependents5.iterator();
            while (it5.hasNext()) {
                it5.next().mOwner.addChildrenToSolverByDependency(container, system, widgets, orientation, true);
            }
        }
    }

    public void getSceneString(StringBuilder ret) {
        ret.append("  " + this.stringId + ":{\n");
        ret.append("    actualWidth:" + this.mWidth);
        ret.append("\n");
        ret.append("    actualHeight:" + this.mHeight);
        ret.append("\n");
        ret.append("    actualLeft:" + this.mX);
        ret.append("\n");
        ret.append("    actualTop:" + this.mY);
        ret.append("\n");
        getSceneString(ret, "left", this.mLeft);
        getSceneString(ret, "top", this.mTop);
        getSceneString(ret, "right", this.mRight);
        getSceneString(ret, "bottom", this.mBottom);
        getSceneString(ret, "baseline", this.mBaseline);
        getSceneString(ret, "centerX", this.mCenterX);
        getSceneString(ret, "centerY", this.mCenterY);
        getSceneString(ret, "    width", this.mWidth, this.mMinWidth, this.mMaxDimension[0], this.mWidthOverride, this.mMatchConstraintMinWidth, this.mMatchConstraintDefaultWidth, this.mMatchConstraintPercentWidth, this.mListDimensionBehaviors[0], this.mWeight[0]);
        getSceneString(ret, "    height", this.mHeight, this.mMinHeight, this.mMaxDimension[1], this.mHeightOverride, this.mMatchConstraintMinHeight, this.mMatchConstraintDefaultHeight, this.mMatchConstraintPercentHeight, this.mListDimensionBehaviors[1], this.mWeight[1]);
        serializeDimensionRatio(ret, "    dimensionRatio", this.mDimensionRatio, this.mDimensionRatioSide);
        serializeAttribute(ret, "    horizontalBias", this.mHorizontalBiasPercent, DEFAULT_BIAS);
        serializeAttribute(ret, "    verticalBias", this.mVerticalBiasPercent, DEFAULT_BIAS);
        serializeAttribute(ret, "    horizontalChainStyle", this.mHorizontalChainStyle, 0);
        serializeAttribute(ret, "    verticalChainStyle", this.mVerticalChainStyle, 0);
        ret.append("  }");
    }

    private void getSceneString(StringBuilder ret, String type, int size, int min, int max, int override, int matchConstraintMin, int matchConstraintDefault, float matchConstraintPercent, DimensionBehaviour behavior, float weight) {
        ret.append(type);
        ret.append(" :  {\n");
        serializeAttribute(ret, "      behavior", behavior.toString(), DimensionBehaviour.FIXED.toString());
        serializeAttribute(ret, "      size", size, 0);
        serializeAttribute(ret, "      min", min, 0);
        serializeAttribute(ret, "      max", max, Integer.MAX_VALUE);
        serializeAttribute(ret, "      matchMin", matchConstraintMin, 0);
        serializeAttribute(ret, "      matchDef", matchConstraintDefault, 0);
        serializeAttribute(ret, "      matchPercent", matchConstraintPercent, 1.0f);
        ret.append("    },\n");
    }

    private void getSceneString(StringBuilder ret, String side, ConstraintAnchor a) {
        if (a.mTarget != null) {
            ret.append("    ");
            ret.append(side);
            ret.append(" : [ '");
            ret.append(a.mTarget);
            ret.append("'");
            if (!(a.mGoneMargin == Integer.MIN_VALUE && a.mMargin == 0)) {
                ret.append(",");
                ret.append(a.mMargin);
                if (a.mGoneMargin != Integer.MIN_VALUE) {
                    ret.append(",");
                    ret.append(a.mGoneMargin);
                    ret.append(",");
                }
            }
            ret.append(" ] ,\n");
        }
    }
}
