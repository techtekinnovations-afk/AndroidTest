package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.Optimizer;
import androidx.constraintlayout.core.widgets.VirtualLayout;
import java.util.ArrayList;

public class BasicMeasure {
    public static final int AT_MOST = Integer.MIN_VALUE;
    private static final boolean DEBUG = false;
    private static final boolean DO_NOT_USE = false;
    public static final int EXACTLY = 1073741824;
    public static final int FIXED = -3;
    public static final int MATCH_PARENT = -1;
    private static final int MODE_SHIFT = 30;
    public static final int UNSPECIFIED = 0;
    public static final int WRAP_CONTENT = -2;
    private ConstraintWidgetContainer mConstraintWidgetContainer;
    private Measure mMeasure = new Measure();
    private final ArrayList<ConstraintWidget> mVariableDimensionsWidgets = new ArrayList<>();

    public static class Measure {
        public static int SELF_DIMENSIONS = 0;
        public static int TRY_GIVEN_DIMENSIONS = 1;
        public static int USE_GIVEN_DIMENSIONS = 2;
        public ConstraintWidget.DimensionBehaviour horizontalBehavior;
        public int horizontalDimension;
        public int measureStrategy;
        public int measuredBaseline;
        public boolean measuredHasBaseline;
        public int measuredHeight;
        public boolean measuredNeedsSolverPass;
        public int measuredWidth;
        public ConstraintWidget.DimensionBehaviour verticalBehavior;
        public int verticalDimension;
    }

    public interface Measurer {
        void didMeasures();

        void measure(ConstraintWidget constraintWidget, Measure measure);
    }

    public void updateHierarchy(ConstraintWidgetContainer layout) {
        this.mVariableDimensionsWidgets.clear();
        int childCount = layout.mChildren.size();
        for (int i = 0; i < childCount; i++) {
            ConstraintWidget widget = (ConstraintWidget) layout.mChildren.get(i);
            if (widget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || widget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                this.mVariableDimensionsWidgets.add(widget);
            }
        }
        layout.invalidateGraph();
    }

    public BasicMeasure(ConstraintWidgetContainer constraintWidgetContainer) {
        this.mConstraintWidgetContainer = constraintWidgetContainer;
    }

    private void measureChildren(ConstraintWidgetContainer layout) {
        int childCount = layout.mChildren.size();
        boolean optimize = layout.optimizeFor(64);
        Measurer measurer = layout.getMeasurer();
        for (int i = 0; i < childCount; i++) {
            ConstraintWidget child = (ConstraintWidget) layout.mChildren.get(i);
            if (!(child instanceof Guideline) && !(child instanceof Barrier) && !child.isInVirtualLayout() && (!optimize || child.mHorizontalRun == null || child.mVerticalRun == null || !child.mHorizontalRun.mDimension.resolved || !child.mVerticalRun.mDimension.resolved)) {
                boolean skip = false;
                ConstraintWidget.DimensionBehaviour widthBehavior = child.getDimensionBehaviour(0);
                ConstraintWidget.DimensionBehaviour heightBehavior = child.getDimensionBehaviour(1);
                if (widthBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && child.mMatchConstraintDefaultWidth != 1 && heightBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && child.mMatchConstraintDefaultHeight != 1) {
                    skip = true;
                }
                if (!skip && layout.optimizeFor(1) && !(child instanceof VirtualLayout)) {
                    if (widthBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && child.mMatchConstraintDefaultWidth == 0 && heightBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && !child.isInHorizontalChain()) {
                        skip = true;
                    }
                    if (heightBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && child.mMatchConstraintDefaultHeight == 0 && widthBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && !child.isInHorizontalChain()) {
                        skip = true;
                    }
                    if ((widthBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || heightBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && child.mDimensionRatio > 0.0f) {
                        skip = true;
                    }
                }
                if (!skip) {
                    measure(measurer, child, Measure.SELF_DIMENSIONS);
                    if (layout.mMetrics != null) {
                        layout.mMetrics.measuredWidgets++;
                    }
                }
            }
        }
        measurer.didMeasures();
    }

    private void solveLinearSystem(ConstraintWidgetContainer layout, String reason, int pass, int w, int h) {
        ConstraintWidgetContainer constraintWidgetContainer = layout;
        long startLayout = 0;
        if (constraintWidgetContainer.mMetrics != null) {
            startLayout = System.nanoTime();
        }
        int minWidth = constraintWidgetContainer.getMinWidth();
        int minHeight = constraintWidgetContainer.getMinHeight();
        constraintWidgetContainer.setMinWidth(0);
        constraintWidgetContainer.setMinHeight(0);
        constraintWidgetContainer.setWidth(w);
        constraintWidgetContainer.setHeight(h);
        constraintWidgetContainer.setMinWidth(minWidth);
        constraintWidgetContainer.setMinHeight(minHeight);
        this.mConstraintWidgetContainer.setPass(pass);
        this.mConstraintWidgetContainer.layout();
        if (constraintWidgetContainer.mMetrics != null) {
            long endLayout = System.nanoTime();
            constraintWidgetContainer.mMetrics.mSolverPasses++;
            constraintWidgetContainer.mMetrics.measuresLayoutDuration += endLayout - startLayout;
        }
    }

    public long solverMeasure(ConstraintWidgetContainer layout, int optimizationLevel, int paddingX, int paddingY, int widthMode, int widthSize, int heightMode, int heightSize, int lastMeasureWidth, int lastMeasureHeight) {
        long layoutTime;
        long j;
        boolean containerWrapHeight;
        boolean allSolved;
        int computations;
        long layoutTime2;
        boolean z;
        int sizeDependentWidgetsCount;
        int optimizations;
        int sizeDependentWidgetsCount2;
        Measurer measurer;
        int maxIterations;
        int i;
        int measureStrategy;
        boolean optimize;
        boolean optimizeWrap;
        int childCount;
        boolean needSolverPass;
        boolean preWidth;
        int minWidth;
        int heightSize2;
        boolean z2;
        boolean allSolved2;
        BasicMeasure basicMeasure = this;
        ConstraintWidgetContainer constraintWidgetContainer = layout;
        int i2 = optimizationLevel;
        int i3 = widthMode;
        int i4 = heightMode;
        Measurer measurer2 = constraintWidgetContainer.getMeasurer();
        long layoutTime3 = 0;
        int childCount2 = constraintWidgetContainer.mChildren.size();
        int startingWidth = constraintWidgetContainer.getWidth();
        int startingHeight = constraintWidgetContainer.getHeight();
        boolean optimizeWrap2 = Optimizer.enabled(i2, 128);
        boolean optimize2 = optimizeWrap2 || Optimizer.enabled(i2, 64);
        if (optimize2) {
            int i5 = 0;
            while (true) {
                if (i5 >= childCount2) {
                    layoutTime = layoutTime3;
                    break;
                }
                ConstraintWidget child = (ConstraintWidget) constraintWidgetContainer.mChildren.get(i5);
                layoutTime = layoutTime3;
                boolean matchWidth = child.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                boolean ratio = matchWidth && (child.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && child.getDimensionRatio() > 0.0f;
                if (!child.isInHorizontalChain() || !ratio) {
                    if (child.isInVerticalChain() && ratio) {
                        optimize2 = false;
                        break;
                    }
                    boolean z3 = matchWidth;
                    if (child instanceof VirtualLayout) {
                        optimize2 = false;
                        break;
                    } else if (child.isInHorizontalChain() || child.isInVerticalChain()) {
                        optimize2 = false;
                    } else {
                        i5++;
                        layoutTime3 = layoutTime;
                    }
                } else {
                    optimize2 = false;
                    break;
                }
            }
        } else {
            layoutTime = 0;
        }
        if (!optimize2 || LinearSystem.sMetrics == null) {
            j = 1;
        } else {
            j = 1;
            LinearSystem.sMetrics.measures++;
        }
        boolean optimize3 = ((i3 == 1073741824 && i4 == 1073741824) || optimizeWrap2) & optimize2;
        int computations2 = 0;
        if (optimize3) {
            int widthSize2 = Math.min(constraintWidgetContainer.getMaxWidth(), widthSize);
            int heightSize3 = Math.min(constraintWidgetContainer.getMaxHeight(), heightSize);
            if (i3 == 1073741824 && constraintWidgetContainer.getWidth() != widthSize2) {
                constraintWidgetContainer.setWidth(widthSize2);
                constraintWidgetContainer.invalidateGraph();
            }
            if (i4 == 1073741824 && constraintWidgetContainer.getHeight() != heightSize3) {
                constraintWidgetContainer.setHeight(heightSize3);
                constraintWidgetContainer.invalidateGraph();
            }
            if (i3 == 1073741824 && i4 == 1073741824) {
                allSolved2 = constraintWidgetContainer.directMeasure(optimizeWrap2);
                computations2 = 2;
                heightSize2 = heightSize3;
                z2 = true;
            } else {
                allSolved2 = constraintWidgetContainer.directMeasureSetup(optimizeWrap2);
                if (i3 == 1073741824) {
                    heightSize2 = heightSize3;
                    allSolved2 &= constraintWidgetContainer.directMeasureWithOrientation(optimizeWrap2, 0);
                    computations2 = 0 + 1;
                } else {
                    heightSize2 = heightSize3;
                }
                if (i4 == 1073741824) {
                    z2 = true;
                    allSolved2 &= constraintWidgetContainer.directMeasureWithOrientation(optimizeWrap2, 1);
                    computations2++;
                } else {
                    z2 = true;
                }
            }
            if (allSolved2) {
                if (i3 != 1073741824) {
                    z2 = false;
                }
                constraintWidgetContainer.updateFromRuns(z2, i4 == 1073741824);
            }
            allSolved = allSolved2;
            computations = computations2;
            containerWrapHeight = true;
            int computations3 = heightSize2;
        } else {
            containerWrapHeight = true;
            int i6 = widthSize;
            allSolved = false;
            computations = 0;
            int computations4 = heightSize;
        }
        if (!allSolved || computations != 2) {
            int optimizations2 = constraintWidgetContainer.getOptimizationLevel();
            if (childCount2 > 0) {
                measureChildren(layout);
            }
            int i7 = computations;
            if (constraintWidgetContainer.mMetrics != null) {
                layoutTime = System.nanoTime();
            }
            updateHierarchy(layout);
            int sizeDependentWidgetsCount3 = basicMeasure.mVariableDimensionsWidgets.size();
            if (childCount2 > 0) {
                sizeDependentWidgetsCount = sizeDependentWidgetsCount3;
                optimizations = optimizations2;
                z = false;
                basicMeasure.solveLinearSystem(constraintWidgetContainer, "First pass", 0, startingWidth, startingHeight);
            } else {
                sizeDependentWidgetsCount = sizeDependentWidgetsCount3;
                optimizations = optimizations2;
                z = false;
            }
            if (sizeDependentWidgetsCount > 0) {
                boolean needSolverPass2 = false;
                boolean containerWrapWidth = constraintWidgetContainer.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT ? containerWrapHeight : z;
                if (constraintWidgetContainer.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    containerWrapHeight = z;
                }
                int minWidth2 = Math.max(constraintWidgetContainer.getWidth(), basicMeasure.mConstraintWidgetContainer.getMinWidth());
                int minHeight = Math.max(constraintWidgetContainer.getHeight(), basicMeasure.mConstraintWidgetContainer.getMinHeight());
                int minWidth3 = minWidth2;
                int startingWidth2 = startingWidth;
                int i8 = 0;
                while (i8 < sizeDependentWidgetsCount) {
                    int startingHeight2 = startingHeight;
                    ConstraintWidget widget = basicMeasure.mVariableDimensionsWidgets.get(i8);
                    int i9 = i8;
                    if ((widget instanceof VirtualLayout) == 0) {
                        needSolverPass = needSolverPass2;
                        childCount = childCount2;
                        optimizeWrap = optimizeWrap2;
                        optimize = optimize3;
                    } else {
                        int preWidth2 = widget.getWidth();
                        int preHeight = widget.getHeight();
                        childCount = childCount2;
                        boolean needSolverPass3 = needSolverPass2 | basicMeasure.measure(measurer2, widget, Measure.TRY_GIVEN_DIMENSIONS);
                        if (constraintWidgetContainer.mMetrics != null) {
                            optimizeWrap = optimizeWrap2;
                            optimize = optimize3;
                            constraintWidgetContainer.mMetrics.measuredMatchWidgets += j;
                        } else {
                            optimizeWrap = optimizeWrap2;
                            optimize = optimize3;
                        }
                        int measuredWidth = widget.getWidth();
                        int measuredHeight = widget.getHeight();
                        if (measuredWidth != preWidth2) {
                            widget.setWidth(measuredWidth);
                            if (!containerWrapWidth || widget.getRight() <= minWidth3) {
                            } else {
                                int i10 = preWidth2;
                                minWidth3 = Math.max(minWidth3, widget.getRight() + widget.getAnchor(ConstraintAnchor.Type.RIGHT).getMargin());
                            }
                            preWidth = true;
                        } else {
                            preWidth = needSolverPass3;
                        }
                        if (measuredHeight != preHeight) {
                            widget.setHeight(measuredHeight);
                            if (!containerWrapHeight || widget.getBottom() <= minHeight) {
                                minWidth = minWidth3;
                            } else {
                                minWidth = minWidth3;
                                minHeight = Math.max(minHeight, widget.getBottom() + widget.getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin());
                            }
                            preWidth = true;
                        } else {
                            minWidth = minWidth3;
                        }
                        needSolverPass = preWidth | ((VirtualLayout) widget).needSolverPass();
                        minWidth3 = minWidth;
                    }
                    int i11 = heightMode;
                    needSolverPass2 = needSolverPass;
                    i8 = i9 + 1;
                    startingHeight = startingHeight2;
                    childCount2 = childCount;
                    optimizeWrap2 = optimizeWrap;
                    optimize3 = optimize;
                }
                int i12 = i8;
                int startingHeight3 = startingHeight;
                int i13 = childCount2;
                boolean z4 = optimizeWrap2;
                boolean optimize4 = optimize3;
                int maxIterations2 = 2;
                int j2 = 0;
                int minWidth4 = minWidth3;
                int minWidth5 = minHeight;
                boolean minHeight2 = needSolverPass2;
                while (true) {
                    if (j2 >= maxIterations2) {
                        int i14 = sizeDependentWidgetsCount;
                        int i15 = maxIterations2;
                        Measurer measurer3 = measurer2;
                        int i16 = startingHeight3;
                        break;
                    }
                    int preHeight2 = 0;
                    boolean needSolverPass4 = minHeight2;
                    int minHeight3 = minWidth5;
                    int minWidth6 = minWidth4;
                    while (preHeight2 < sizeDependentWidgetsCount) {
                        ConstraintWidget widget2 = basicMeasure.mVariableDimensionsWidgets.get(preHeight2);
                        if ((widget2 instanceof Helper) && !(widget2 instanceof VirtualLayout)) {
                            i = preHeight2;
                        } else if (widget2 instanceof Guideline) {
                            i = preHeight2;
                        } else {
                            i = preHeight2;
                            if (widget2.getVisibility() != 8 && ((!optimize4 || !widget2.mHorizontalRun.mDimension.resolved || !widget2.mVerticalRun.mDimension.resolved) && !(widget2 instanceof VirtualLayout))) {
                                int preWidth3 = widget2.getWidth();
                                int preHeight3 = widget2.getHeight();
                                sizeDependentWidgetsCount2 = sizeDependentWidgetsCount;
                                int sizeDependentWidgetsCount4 = widget2.getBaselineDistance();
                                int measureStrategy2 = Measure.TRY_GIVEN_DIMENSIONS;
                                maxIterations = maxIterations2;
                                if (j2 == maxIterations - 1) {
                                    measureStrategy = Measure.USE_GIVEN_DIMENSIONS;
                                } else {
                                    measureStrategy = measureStrategy2;
                                }
                                needSolverPass4 |= basicMeasure.measure(measurer2, widget2, measureStrategy);
                                if (constraintWidgetContainer.mMetrics != null) {
                                    int i17 = measureStrategy;
                                    measurer = measurer2;
                                    constraintWidgetContainer.mMetrics.measuredMatchWidgets += j;
                                } else {
                                    measurer = measurer2;
                                }
                                int measuredWidth2 = widget2.getWidth();
                                int measuredHeight2 = widget2.getHeight();
                                if (measuredWidth2 != preWidth3) {
                                    widget2.setWidth(measuredWidth2);
                                    if (!containerWrapWidth || widget2.getRight() <= minWidth6) {
                                    } else {
                                        int i18 = measuredWidth2;
                                        minWidth6 = Math.max(minWidth6, widget2.getRight() + widget2.getAnchor(ConstraintAnchor.Type.RIGHT).getMargin());
                                    }
                                    needSolverPass4 = true;
                                }
                                if (measuredHeight2 != preHeight3) {
                                    widget2.setHeight(measuredHeight2);
                                    if (containerWrapHeight && widget2.getBottom() > minHeight3) {
                                        minHeight3 = Math.max(minHeight3, widget2.getBottom() + widget2.getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin());
                                    }
                                    needSolverPass4 = true;
                                }
                                if (widget2.hasBaseline() && sizeDependentWidgetsCount4 != widget2.getBaselineDistance()) {
                                    needSolverPass4 = true;
                                }
                                preHeight2 = i + 1;
                                basicMeasure = this;
                                sizeDependentWidgetsCount = sizeDependentWidgetsCount2;
                                maxIterations2 = maxIterations;
                                measurer2 = measurer;
                            }
                        }
                        sizeDependentWidgetsCount2 = sizeDependentWidgetsCount;
                        maxIterations = maxIterations2;
                        measurer = measurer2;
                        preHeight2 = i + 1;
                        basicMeasure = this;
                        sizeDependentWidgetsCount = sizeDependentWidgetsCount2;
                        maxIterations2 = maxIterations;
                        measurer2 = measurer;
                    }
                    int i19 = preHeight2;
                    int sizeDependentWidgetsCount5 = sizeDependentWidgetsCount;
                    int maxIterations3 = maxIterations2;
                    Measurer measurer4 = measurer2;
                    if (!needSolverPass4) {
                        int i20 = minWidth6;
                        int i21 = startingHeight3;
                        break;
                    }
                    int minWidth7 = minWidth6;
                    int startingWidth3 = startingWidth2;
                    solveLinearSystem(constraintWidgetContainer, "intermediate pass", j2 + 1, startingWidth3, startingHeight3);
                    minHeight2 = false;
                    j2++;
                    startingWidth2 = startingWidth3;
                    minWidth4 = minWidth7;
                    minWidth5 = minHeight3;
                    maxIterations2 = maxIterations3;
                    measurer2 = measurer4;
                    basicMeasure = this;
                    sizeDependentWidgetsCount = sizeDependentWidgetsCount5;
                }
            } else {
                int i22 = sizeDependentWidgetsCount;
                Measurer measurer5 = measurer2;
                int i23 = childCount2;
                boolean z5 = optimizeWrap2;
                boolean z6 = optimize3;
            }
            constraintWidgetContainer.setOptimizationLevel(optimizations);
            layoutTime2 = layoutTime;
        } else {
            int i24 = computations;
            int i25 = startingWidth;
            Measurer measurer6 = measurer2;
            int i26 = childCount2;
            boolean z7 = optimizeWrap2;
            boolean z8 = optimize3;
            layoutTime2 = layoutTime;
        }
        if (constraintWidgetContainer.mMetrics != null) {
            return System.nanoTime() - layoutTime2;
        }
        return layoutTime2;
    }

    private boolean measure(Measurer measurer, ConstraintWidget widget, int measureStrategy) {
        this.mMeasure.horizontalBehavior = widget.getHorizontalDimensionBehaviour();
        this.mMeasure.verticalBehavior = widget.getVerticalDimensionBehaviour();
        this.mMeasure.horizontalDimension = widget.getWidth();
        this.mMeasure.verticalDimension = widget.getHeight();
        this.mMeasure.measuredNeedsSolverPass = false;
        this.mMeasure.measureStrategy = measureStrategy;
        boolean horizontalMatchConstraints = this.mMeasure.horizontalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        boolean verticalMatchConstraints = this.mMeasure.verticalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        boolean horizontalUseRatio = horizontalMatchConstraints && widget.mDimensionRatio > 0.0f;
        boolean verticalUseRatio = verticalMatchConstraints && widget.mDimensionRatio > 0.0f;
        if (horizontalUseRatio && widget.mResolvedMatchConstraintDefault[0] == 4) {
            this.mMeasure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        if (verticalUseRatio && widget.mResolvedMatchConstraintDefault[1] == 4) {
            this.mMeasure.verticalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        measurer.measure(widget, this.mMeasure);
        widget.setWidth(this.mMeasure.measuredWidth);
        widget.setHeight(this.mMeasure.measuredHeight);
        widget.setHasBaseline(this.mMeasure.measuredHasBaseline);
        widget.setBaselineDistance(this.mMeasure.measuredBaseline);
        this.mMeasure.measureStrategy = Measure.SELF_DIMENSIONS;
        return this.mMeasure.measuredNeedsSolverPass;
    }
}
