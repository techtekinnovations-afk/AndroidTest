package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.ChainHead;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import java.util.ArrayList;
import java.util.Iterator;

public class Direct {
    private static final boolean APPLY_MATCH_PARENT = false;
    private static final boolean DEBUG = false;
    private static final boolean EARLY_TERMINATION = true;
    private static int sHcount = 0;
    private static BasicMeasure.Measure sMeasure = new BasicMeasure.Measure();
    private static int sVcount = 0;

    public static void solvingPass(ConstraintWidgetContainer layout, BasicMeasure.Measurer measurer) {
        ConstraintWidgetContainer constraintWidgetContainer = layout;
        BasicMeasure.Measurer measurer2 = measurer;
        ConstraintWidget.DimensionBehaviour horizontal = constraintWidgetContainer.getHorizontalDimensionBehaviour();
        ConstraintWidget.DimensionBehaviour vertical = constraintWidgetContainer.getVerticalDimensionBehaviour();
        sHcount = 0;
        sVcount = 0;
        constraintWidgetContainer.resetFinalResolution();
        ArrayList<ConstraintWidget> children = constraintWidgetContainer.getChildren();
        int count = children.size();
        for (int i = 0; i < count; i++) {
            children.get(i).resetFinalResolution();
        }
        boolean isRtl = constraintWidgetContainer.isRtl();
        if (horizontal == ConstraintWidget.DimensionBehaviour.FIXED) {
            constraintWidgetContainer.setFinalHorizontal(0, constraintWidgetContainer.getWidth());
        } else {
            constraintWidgetContainer.setFinalLeft(0);
        }
        boolean hasGuideline = false;
        boolean hasBarrier = false;
        for (int i2 = 0; i2 < count; i2++) {
            ConstraintWidget child = children.get(i2);
            if (child instanceof Guideline) {
                Guideline guideline = (Guideline) child;
                if (guideline.getOrientation() == 1) {
                    if (guideline.getRelativeBegin() != -1) {
                        guideline.setFinalValue(guideline.getRelativeBegin());
                    } else if (guideline.getRelativeEnd() != -1 && constraintWidgetContainer.isResolvedHorizontally()) {
                        guideline.setFinalValue(constraintWidgetContainer.getWidth() - guideline.getRelativeEnd());
                    } else if (constraintWidgetContainer.isResolvedHorizontally()) {
                        guideline.setFinalValue((int) ((guideline.getRelativePercent() * ((float) constraintWidgetContainer.getWidth())) + 0.5f));
                    }
                    hasGuideline = true;
                }
            } else if ((child instanceof Barrier) && ((Barrier) child).getOrientation() == 0) {
                hasBarrier = true;
            }
        }
        if (hasGuideline) {
            for (int i3 = 0; i3 < count; i3++) {
                ConstraintWidget child2 = children.get(i3);
                if (child2 instanceof Guideline) {
                    Guideline guideline2 = (Guideline) child2;
                    if (guideline2.getOrientation() == 1) {
                        horizontalSolvingPass(0, guideline2, measurer2, isRtl);
                    }
                }
            }
        }
        horizontalSolvingPass(0, constraintWidgetContainer, measurer2, isRtl);
        if (hasBarrier) {
            for (int i4 = 0; i4 < count; i4++) {
                ConstraintWidget child3 = children.get(i4);
                if (child3 instanceof Barrier) {
                    Barrier barrier = (Barrier) child3;
                    if (barrier.getOrientation() == 0) {
                        solveBarrier(0, barrier, measurer2, 0, isRtl);
                    }
                }
            }
        }
        if (vertical == ConstraintWidget.DimensionBehaviour.FIXED) {
            constraintWidgetContainer.setFinalVertical(0, constraintWidgetContainer.getHeight());
        } else {
            constraintWidgetContainer.setFinalTop(0);
        }
        boolean hasGuideline2 = false;
        boolean hasBarrier2 = false;
        for (int i5 = 0; i5 < count; i5++) {
            ConstraintWidget child4 = children.get(i5);
            if (child4 instanceof Guideline) {
                Guideline guideline3 = (Guideline) child4;
                if (guideline3.getOrientation() == 0) {
                    if (guideline3.getRelativeBegin() != -1) {
                        guideline3.setFinalValue(guideline3.getRelativeBegin());
                    } else if (guideline3.getRelativeEnd() != -1 && constraintWidgetContainer.isResolvedVertically()) {
                        guideline3.setFinalValue(constraintWidgetContainer.getHeight() - guideline3.getRelativeEnd());
                    } else if (constraintWidgetContainer.isResolvedVertically()) {
                        guideline3.setFinalValue((int) ((guideline3.getRelativePercent() * ((float) constraintWidgetContainer.getHeight())) + 0.5f));
                    }
                    hasGuideline2 = true;
                }
            } else if ((child4 instanceof Barrier) && ((Barrier) child4).getOrientation() == 1) {
                hasBarrier2 = true;
            }
        }
        if (hasGuideline2) {
            for (int i6 = 0; i6 < count; i6++) {
                ConstraintWidget child5 = children.get(i6);
                if (child5 instanceof Guideline) {
                    Guideline guideline4 = (Guideline) child5;
                    if (guideline4.getOrientation() == 0) {
                        verticalSolvingPass(1, guideline4, measurer2);
                    }
                }
            }
        }
        verticalSolvingPass(0, constraintWidgetContainer, measurer2);
        if (hasBarrier2) {
            for (int i7 = 0; i7 < count; i7++) {
                ConstraintWidget child6 = children.get(i7);
                if (child6 instanceof Barrier) {
                    Barrier barrier2 = (Barrier) child6;
                    if (barrier2.getOrientation() == 1) {
                        solveBarrier(0, barrier2, measurer2, 1, isRtl);
                    }
                }
            }
        }
        for (int i8 = 0; i8 < count; i8++) {
            ConstraintWidget child7 = children.get(i8);
            if (child7.isMeasureRequested()) {
                if (canMeasure(0, child7)) {
                    ConstraintWidgetContainer.measure(0, child7, measurer2, sMeasure, BasicMeasure.Measure.SELF_DIMENSIONS);
                    if (!(child7 instanceof Guideline)) {
                        horizontalSolvingPass(0, child7, measurer2, isRtl);
                        verticalSolvingPass(0, child7, measurer2);
                    } else if (((Guideline) child7).getOrientation() == 0) {
                        verticalSolvingPass(0, child7, measurer2);
                    } else {
                        horizontalSolvingPass(0, child7, measurer2, isRtl);
                    }
                }
            }
        }
    }

    private static void solveBarrier(int level, Barrier barrier, BasicMeasure.Measurer measurer, int orientation, boolean isRtl) {
        if (!barrier.allSolved()) {
            return;
        }
        if (orientation == 0) {
            horizontalSolvingPass(level + 1, barrier, measurer, isRtl);
        } else {
            verticalSolvingPass(level + 1, barrier, measurer);
        }
    }

    public static String ls(int level) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < level; i++) {
            builder.append("  ");
        }
        builder.append("+-(" + level + ") ");
        return builder.toString();
    }

    private static void horizontalSolvingPass(int level, ConstraintWidget layout, BasicMeasure.Measurer measurer, boolean isRtl) {
        float f;
        float f2;
        ConstraintWidget constraintWidget = layout;
        BasicMeasure.Measurer measurer2 = measurer;
        boolean z = isRtl;
        if (!constraintWidget.isHorizontalSolvingPassDone()) {
            sHcount++;
            if (!(constraintWidget instanceof ConstraintWidgetContainer) && constraintWidget.isMeasureRequested() && canMeasure(level + 1, constraintWidget)) {
                ConstraintWidgetContainer.measure(level + 1, constraintWidget, measurer2, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
            }
            ConstraintAnchor left = constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT);
            ConstraintAnchor right = constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT);
            int l = left.getFinalValue();
            int r = right.getFinalValue();
            if (left.getDependents() == null || !left.hasFinalValue()) {
                f = 0.0f;
            } else {
                Iterator<ConstraintAnchor> it = left.getDependents().iterator();
                while (it.hasNext()) {
                    ConstraintAnchor first = it.next();
                    ConstraintWidget widget = first.mOwner;
                    boolean canMeasure = canMeasure(level + 1, widget);
                    if (!widget.isMeasureRequested() || !canMeasure) {
                        f2 = 0.0f;
                    } else {
                        f2 = 0.0f;
                        ConstraintWidgetContainer.measure(level + 1, widget, measurer2, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
                    }
                    boolean bothConnected = (first == widget.mLeft && widget.mRight.mTarget != null && widget.mRight.mTarget.hasFinalValue()) || (first == widget.mRight && widget.mLeft.mTarget != null && widget.mLeft.mTarget.hasFinalValue());
                    if (widget.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || canMeasure) {
                        if (!widget.isMeasureRequested()) {
                            if (first == widget.mLeft && widget.mRight.mTarget == null) {
                                int x1 = widget.mLeft.getMargin() + l;
                                widget.setFinalHorizontal(x1, widget.getWidth() + x1);
                                horizontalSolvingPass(level + 1, widget, measurer2, z);
                            } else if (first == widget.mRight && widget.mLeft.mTarget == null) {
                                int x2 = l - widget.mRight.getMargin();
                                widget.setFinalHorizontal(x2 - widget.getWidth(), x2);
                                horizontalSolvingPass(level + 1, widget, measurer2, z);
                            } else if (bothConnected && !widget.isInHorizontalChain()) {
                                solveHorizontalCenterConstraints(level + 1, measurer2, widget, z);
                            }
                        }
                    } else if (widget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.mMatchConstraintMaxWidth >= 0 && widget.mMatchConstraintMinWidth >= 0 && ((widget.getVisibility() == 8 || (widget.mMatchConstraintDefaultWidth == 0 && widget.getDimensionRatio() == f2)) && !widget.isInHorizontalChain() && !widget.isInVirtualLayout() && bothConnected && !widget.isInHorizontalChain())) {
                        solveHorizontalMatchConstraint(level + 1, constraintWidget, measurer2, widget, z);
                    }
                }
                f = 0.0f;
            }
            if (!(constraintWidget instanceof Guideline)) {
                if (right.getDependents() != null && right.hasFinalValue()) {
                    Iterator<ConstraintAnchor> it2 = right.getDependents().iterator();
                    while (it2.hasNext()) {
                        ConstraintAnchor first2 = it2.next();
                        ConstraintWidget widget2 = first2.mOwner;
                        boolean canMeasure2 = canMeasure(level + 1, widget2);
                        if (widget2.isMeasureRequested() && canMeasure2) {
                            ConstraintWidgetContainer.measure(level + 1, widget2, measurer2, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
                        }
                        boolean bothConnected2 = (first2 == widget2.mLeft && widget2.mRight.mTarget != null && widget2.mRight.mTarget.hasFinalValue()) || (first2 == widget2.mRight && widget2.mLeft.mTarget != null && widget2.mLeft.mTarget.hasFinalValue());
                        if (widget2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            if (!canMeasure2) {
                                if (widget2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget2.mMatchConstraintMaxWidth >= 0 && widget2.mMatchConstraintMinWidth >= 0) {
                                    if ((widget2.getVisibility() == 8 || (widget2.mMatchConstraintDefaultWidth == 0 && widget2.getDimensionRatio() == f)) && !widget2.isInHorizontalChain() && !widget2.isInVirtualLayout() && bothConnected2 && !widget2.isInHorizontalChain()) {
                                        solveHorizontalMatchConstraint(level + 1, constraintWidget, measurer2, widget2, z);
                                    }
                                }
                            }
                        }
                        if (!widget2.isMeasureRequested()) {
                            if (first2 == widget2.mLeft && widget2.mRight.mTarget == null) {
                                int x12 = widget2.mLeft.getMargin() + r;
                                widget2.setFinalHorizontal(x12, widget2.getWidth() + x12);
                                horizontalSolvingPass(level + 1, widget2, measurer2, z);
                            } else if (first2 == widget2.mRight && widget2.mLeft.mTarget == null) {
                                int x22 = r - widget2.mRight.getMargin();
                                widget2.setFinalHorizontal(x22 - widget2.getWidth(), x22);
                                horizontalSolvingPass(level + 1, widget2, measurer2, z);
                            } else if (bothConnected2 && !widget2.isInHorizontalChain()) {
                                solveHorizontalCenterConstraints(level + 1, measurer2, widget2, z);
                            }
                        }
                    }
                }
                constraintWidget.markHorizontalSolvingPassDone();
            }
        }
    }

    private static void verticalSolvingPass(int level, ConstraintWidget layout, BasicMeasure.Measurer measurer) {
        float f;
        float f2;
        ConstraintWidget constraintWidget = layout;
        BasicMeasure.Measurer measurer2 = measurer;
        if (!constraintWidget.isVerticalSolvingPassDone()) {
            sVcount++;
            if (!(constraintWidget instanceof ConstraintWidgetContainer) && constraintWidget.isMeasureRequested() && canMeasure(level + 1, constraintWidget)) {
                ConstraintWidgetContainer.measure(level + 1, constraintWidget, measurer2, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
            }
            ConstraintAnchor top = constraintWidget.getAnchor(ConstraintAnchor.Type.TOP);
            ConstraintAnchor bottom = constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM);
            int t = top.getFinalValue();
            int b = bottom.getFinalValue();
            if (top.getDependents() == null || !top.hasFinalValue()) {
                f = 0.0f;
            } else {
                Iterator<ConstraintAnchor> it = top.getDependents().iterator();
                while (it.hasNext()) {
                    ConstraintAnchor first = it.next();
                    ConstraintWidget widget = first.mOwner;
                    boolean canMeasure = canMeasure(level + 1, widget);
                    if (!widget.isMeasureRequested() || !canMeasure) {
                        f2 = 0.0f;
                    } else {
                        f2 = 0.0f;
                        ConstraintWidgetContainer.measure(level + 1, widget, measurer2, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
                    }
                    boolean bothConnected = (first == widget.mTop && widget.mBottom.mTarget != null && widget.mBottom.mTarget.hasFinalValue()) || (first == widget.mBottom && widget.mTop.mTarget != null && widget.mTop.mTarget.hasFinalValue());
                    if (widget.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || canMeasure) {
                        if (!widget.isMeasureRequested()) {
                            if (first == widget.mTop && widget.mBottom.mTarget == null) {
                                int y1 = widget.mTop.getMargin() + t;
                                widget.setFinalVertical(y1, widget.getHeight() + y1);
                                verticalSolvingPass(level + 1, widget, measurer2);
                            } else if (first == widget.mBottom && widget.mTop.mTarget == null) {
                                int y2 = t - widget.mBottom.getMargin();
                                widget.setFinalVertical(y2 - widget.getHeight(), y2);
                                verticalSolvingPass(level + 1, widget, measurer2);
                            } else if (bothConnected && !widget.isInVerticalChain()) {
                                solveVerticalCenterConstraints(level + 1, measurer2, widget);
                            }
                        }
                    } else if (widget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget.mMatchConstraintMaxHeight >= 0 && widget.mMatchConstraintMinHeight >= 0 && ((widget.getVisibility() == 8 || (widget.mMatchConstraintDefaultHeight == 0 && widget.getDimensionRatio() == f2)) && !widget.isInVerticalChain() && !widget.isInVirtualLayout() && bothConnected && !widget.isInVerticalChain())) {
                        solveVerticalMatchConstraint(level + 1, constraintWidget, measurer2, widget);
                    }
                }
                f = 0.0f;
            }
            if (!(constraintWidget instanceof Guideline)) {
                if (bottom.getDependents() != null && bottom.hasFinalValue()) {
                    Iterator<ConstraintAnchor> it2 = bottom.getDependents().iterator();
                    while (it2.hasNext()) {
                        ConstraintAnchor first2 = it2.next();
                        ConstraintWidget widget2 = first2.mOwner;
                        boolean canMeasure2 = canMeasure(level + 1, widget2);
                        if (widget2.isMeasureRequested() && canMeasure2) {
                            ConstraintWidgetContainer.measure(level + 1, widget2, measurer2, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
                        }
                        boolean bothConnected2 = (first2 == widget2.mTop && widget2.mBottom.mTarget != null && widget2.mBottom.mTarget.hasFinalValue()) || (first2 == widget2.mBottom && widget2.mTop.mTarget != null && widget2.mTop.mTarget.hasFinalValue());
                        if (widget2.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || canMeasure2) {
                            if (!widget2.isMeasureRequested()) {
                                if (first2 == widget2.mTop && widget2.mBottom.mTarget == null) {
                                    int y12 = widget2.mTop.getMargin() + b;
                                    widget2.setFinalVertical(y12, widget2.getHeight() + y12);
                                    verticalSolvingPass(level + 1, widget2, measurer2);
                                } else if (first2 == widget2.mBottom && widget2.mTop.mTarget == null) {
                                    int y22 = b - widget2.mBottom.getMargin();
                                    widget2.setFinalVertical(y22 - widget2.getHeight(), y22);
                                    verticalSolvingPass(level + 1, widget2, measurer2);
                                } else if (bothConnected2 && !widget2.isInVerticalChain()) {
                                    solveVerticalCenterConstraints(level + 1, measurer2, widget2);
                                }
                            }
                        } else if (widget2.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && widget2.mMatchConstraintMaxHeight >= 0 && widget2.mMatchConstraintMinHeight >= 0) {
                            if ((widget2.getVisibility() == 8 || (widget2.mMatchConstraintDefaultHeight == 0 && widget2.getDimensionRatio() == f)) && !widget2.isInVerticalChain() && !widget2.isInVirtualLayout() && bothConnected2 && !widget2.isInVerticalChain()) {
                                solveVerticalMatchConstraint(level + 1, constraintWidget, measurer2, widget2);
                            }
                        }
                    }
                }
                ConstraintAnchor baseline = constraintWidget.getAnchor(ConstraintAnchor.Type.BASELINE);
                if (baseline.getDependents() != null && baseline.hasFinalValue()) {
                    int baselineValue = baseline.getFinalValue();
                    Iterator<ConstraintAnchor> it3 = baseline.getDependents().iterator();
                    while (it3.hasNext()) {
                        ConstraintAnchor first3 = it3.next();
                        ConstraintWidget widget3 = first3.mOwner;
                        boolean canMeasure3 = canMeasure(level + 1, widget3);
                        if (widget3.isMeasureRequested() && canMeasure3) {
                            ConstraintWidgetContainer.measure(level + 1, widget3, measurer2, new BasicMeasure.Measure(), BasicMeasure.Measure.SELF_DIMENSIONS);
                        }
                        if ((widget3.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || canMeasure3) && !widget3.isMeasureRequested() && first3 == widget3.mBaseline) {
                            widget3.setFinalBaseline(first3.getMargin() + baselineValue);
                            verticalSolvingPass(level + 1, widget3, measurer2);
                        }
                    }
                }
                constraintWidget.markVerticalSolvingPassDone();
            }
        }
    }

    private static void solveHorizontalCenterConstraints(int level, BasicMeasure.Measurer measurer, ConstraintWidget widget, boolean isRtl) {
        int d1;
        float bias = widget.getHorizontalBiasPercent();
        int start = widget.mLeft.mTarget.getFinalValue();
        int end = widget.mRight.mTarget.getFinalValue();
        int s1 = widget.mLeft.getMargin() + start;
        int s2 = end - widget.mRight.getMargin();
        if (start == end) {
            bias = 0.5f;
            s1 = start;
            s2 = end;
        }
        int width = widget.getWidth();
        int distance = (s2 - s1) - width;
        if (s1 > s2) {
            distance = (s1 - s2) - width;
        }
        if (distance > 0) {
            d1 = (int) ((((float) distance) * bias) + 0.5f);
        } else {
            d1 = (int) (((float) distance) * bias);
        }
        int x1 = s1 + d1;
        int x2 = x1 + width;
        if (s1 > s2) {
            x1 = s1 + d1;
            x2 = x1 - width;
        }
        widget.setFinalHorizontal(x1, x2);
        horizontalSolvingPass(level + 1, widget, measurer, isRtl);
    }

    private static void solveVerticalCenterConstraints(int level, BasicMeasure.Measurer measurer, ConstraintWidget widget) {
        int d1;
        float bias = widget.getVerticalBiasPercent();
        int start = widget.mTop.mTarget.getFinalValue();
        int end = widget.mBottom.mTarget.getFinalValue();
        int s1 = widget.mTop.getMargin() + start;
        int s2 = end - widget.mBottom.getMargin();
        if (start == end) {
            bias = 0.5f;
            s1 = start;
            s2 = end;
        }
        int height = widget.getHeight();
        int distance = (s2 - s1) - height;
        if (s1 > s2) {
            distance = (s1 - s2) - height;
        }
        if (distance > 0) {
            d1 = (int) ((((float) distance) * bias) + 0.5f);
        } else {
            d1 = (int) (((float) distance) * bias);
        }
        int y1 = s1 + d1;
        int y2 = y1 + height;
        if (s1 > s2) {
            y1 = s1 - d1;
            y2 = y1 - height;
        }
        widget.setFinalVertical(y1, y2);
        verticalSolvingPass(level + 1, widget, measurer);
    }

    private static void solveHorizontalMatchConstraint(int level, ConstraintWidget layout, BasicMeasure.Measurer measurer, ConstraintWidget widget, boolean isRtl) {
        int parentWidth;
        float bias = widget.getHorizontalBiasPercent();
        int s1 = widget.mLeft.mTarget.getFinalValue() + widget.mLeft.getMargin();
        int s2 = widget.mRight.mTarget.getFinalValue() - widget.mRight.getMargin();
        if (s2 >= s1) {
            int width = widget.getWidth();
            if (widget.getVisibility() != 8) {
                if (widget.mMatchConstraintDefaultWidth == 2) {
                    if (layout instanceof ConstraintWidgetContainer) {
                        parentWidth = layout.getWidth();
                    } else {
                        parentWidth = layout.getParent().getWidth();
                    }
                    width = (int) (widget.getHorizontalBiasPercent() * 0.5f * ((float) parentWidth));
                } else if (widget.mMatchConstraintDefaultWidth == 0) {
                    width = s2 - s1;
                }
                width = Math.max(widget.mMatchConstraintMinWidth, width);
                if (widget.mMatchConstraintMaxWidth > 0) {
                    width = Math.min(widget.mMatchConstraintMaxWidth, width);
                }
            }
            int x1 = s1 + ((int) ((((float) ((s2 - s1) - width)) * bias) + 0.5f));
            widget.setFinalHorizontal(x1, x1 + width);
            horizontalSolvingPass(level + 1, widget, measurer, isRtl);
        }
    }

    private static void solveVerticalMatchConstraint(int level, ConstraintWidget layout, BasicMeasure.Measurer measurer, ConstraintWidget widget) {
        int parentHeight;
        float bias = widget.getVerticalBiasPercent();
        int s1 = widget.mTop.mTarget.getFinalValue() + widget.mTop.getMargin();
        int s2 = widget.mBottom.mTarget.getFinalValue() - widget.mBottom.getMargin();
        if (s2 >= s1) {
            int height = widget.getHeight();
            if (widget.getVisibility() != 8) {
                if (widget.mMatchConstraintDefaultHeight == 2) {
                    if (layout instanceof ConstraintWidgetContainer) {
                        parentHeight = layout.getHeight();
                    } else {
                        parentHeight = layout.getParent().getHeight();
                    }
                    height = (int) (bias * 0.5f * ((float) parentHeight));
                } else if (widget.mMatchConstraintDefaultHeight == 0) {
                    height = s2 - s1;
                }
                height = Math.max(widget.mMatchConstraintMinHeight, height);
                if (widget.mMatchConstraintMaxHeight > 0) {
                    height = Math.min(widget.mMatchConstraintMaxHeight, height);
                }
            }
            int y1 = s1 + ((int) ((((float) ((s2 - s1) - height)) * bias) + 0.5f));
            widget.setFinalVertical(y1, y1 + height);
            verticalSolvingPass(level + 1, widget, measurer);
        }
    }

    private static boolean canMeasure(int level, ConstraintWidget layout) {
        ConstraintWidget.DimensionBehaviour horizontalBehaviour = layout.getHorizontalDimensionBehaviour();
        ConstraintWidget.DimensionBehaviour verticalBehaviour = layout.getVerticalDimensionBehaviour();
        ConstraintWidgetContainer parent = layout.getParent() != null ? (ConstraintWidgetContainer) layout.getParent() : null;
        if (parent == null || parent.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.FIXED) {
        }
        if (parent == null || parent.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.FIXED) {
        }
        boolean isHorizontalFixed = horizontalBehaviour == ConstraintWidget.DimensionBehaviour.FIXED || layout.isResolvedHorizontally() || horizontalBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (horizontalBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && layout.mMatchConstraintDefaultWidth == 0 && layout.mDimensionRatio == 0.0f && layout.hasDanglingDimension(0)) || (horizontalBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && layout.mMatchConstraintDefaultWidth == 1 && layout.hasResolvedTargets(0, layout.getWidth()));
        boolean isVerticalFixed = verticalBehaviour == ConstraintWidget.DimensionBehaviour.FIXED || layout.isResolvedVertically() || verticalBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (verticalBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && layout.mMatchConstraintDefaultHeight == 0 && layout.mDimensionRatio == 0.0f && layout.hasDanglingDimension(1)) || (verticalBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && layout.mMatchConstraintDefaultHeight == 1 && layout.hasResolvedTargets(1, layout.getHeight()));
        if (layout.mDimensionRatio > 0.0f && (isHorizontalFixed || isVerticalFixed)) {
            return true;
        }
        if (!isHorizontalFixed || !isVerticalFixed) {
            return false;
        }
        return true;
    }

    public static boolean solveChain(ConstraintWidgetContainer container, LinearSystem system, int orientation, int offset, ChainHead chainHead, boolean isChainSpread, boolean isChainSpreadInside, boolean isChainPacked) {
        int gap;
        ConstraintWidget next;
        int current;
        float bias;
        boolean done;
        ConstraintWidget head;
        BasicMeasure.Measure measure;
        int totalSize;
        ConstraintWidget next2;
        if (isChainPacked) {
            return false;
        }
        if (orientation == 0) {
            if (!container.isResolvedHorizontally()) {
                return false;
            }
        } else if (!container.isResolvedVertically()) {
            return false;
        }
        boolean isRtl = container.isRtl();
        ConstraintWidget first = chainHead.getFirst();
        ConstraintWidget next3 = chainHead.getLast();
        ConstraintWidget firstVisibleWidget = chainHead.getFirstVisibleWidget();
        ConstraintWidget lastVisibleWidget = chainHead.getLastVisibleWidget();
        ConstraintWidget head2 = chainHead.getHead();
        ConstraintWidget widget = first;
        boolean done2 = false;
        ConstraintAnchor begin = first.mListAnchors[offset];
        ConstraintAnchor end = next3.mListAnchors[offset + 1];
        if (begin.mTarget == null) {
            ConstraintWidget constraintWidget = first;
            ConstraintWidget constraintWidget2 = next3;
            ConstraintWidget constraintWidget3 = head2;
            return false;
        } else if (end.mTarget == null) {
            ConstraintWidget constraintWidget4 = first;
            ConstraintWidget constraintWidget5 = next3;
            ConstraintWidget constraintWidget6 = head2;
            return false;
        } else if (!begin.mTarget.hasFinalValue()) {
            ConstraintWidget constraintWidget7 = first;
            ConstraintWidget constraintWidget8 = next3;
            ConstraintWidget constraintWidget9 = head2;
            return false;
        } else if (!end.mTarget.hasFinalValue()) {
            ConstraintWidget constraintWidget10 = first;
            ConstraintWidget constraintWidget11 = next3;
            ConstraintWidget constraintWidget12 = head2;
            return false;
        } else if (firstVisibleWidget == null) {
            ConstraintWidget constraintWidget13 = first;
            ConstraintWidget constraintWidget14 = next3;
            ConstraintWidget constraintWidget15 = head2;
            return false;
        } else if (lastVisibleWidget == null) {
            ConstraintWidget constraintWidget16 = first;
            ConstraintWidget constraintWidget17 = next3;
            ConstraintWidget constraintWidget18 = head2;
            return false;
        } else {
            int startPoint = begin.mTarget.getFinalValue() + firstVisibleWidget.mListAnchors[offset].getMargin();
            int endPoint = end.mTarget.getFinalValue() - lastVisibleWidget.mListAnchors[offset + 1].getMargin();
            int distance = endPoint - startPoint;
            if (distance <= 0) {
                return false;
            }
            int totalSize2 = 0;
            BasicMeasure.Measure measure2 = new BasicMeasure.Measure();
            boolean z = false;
            int numWidgets = 0;
            int numVisibleWidgets = 0;
            while (true) {
                ConstraintWidget first2 = first;
                if (!done2) {
                    boolean canMeasure = canMeasure(0 + 1, widget);
                    if (!canMeasure) {
                        return false;
                    }
                    boolean z2 = canMeasure;
                    ConstraintWidget last = next3;
                    if (widget.mListDimensionBehaviors[orientation] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        return false;
                    }
                    if (widget.isMeasureRequested()) {
                        head = head2;
                        done = done2;
                        measure = measure2;
                        ConstraintWidgetContainer.measure(0 + 1, widget, container.getMeasurer(), measure, BasicMeasure.Measure.SELF_DIMENSIONS);
                    } else {
                        head = head2;
                        done = done2;
                        measure = measure2;
                    }
                    int totalSize3 = totalSize2 + widget.mListAnchors[offset].getMargin();
                    if (orientation == 0) {
                        totalSize = totalSize3 + widget.getWidth();
                    } else {
                        totalSize = totalSize3 + widget.getHeight();
                    }
                    totalSize2 = totalSize + widget.mListAnchors[offset + 1].getMargin();
                    numWidgets++;
                    if (widget.getVisibility() != 8) {
                        numVisibleWidgets++;
                    }
                    ConstraintAnchor nextAnchor = widget.mListAnchors[offset + 1].mTarget;
                    if (nextAnchor != null) {
                        next2 = nextAnchor.mOwner;
                        if (next2.mListAnchors[offset].mTarget == null || next2.mListAnchors[offset].mTarget.mOwner != widget) {
                            next2 = null;
                        }
                    } else {
                        next2 = null;
                    }
                    if (next2 != null) {
                        widget = next2;
                    } else {
                        done = true;
                    }
                    measure2 = measure;
                    first = first2;
                    next3 = last;
                    head2 = head;
                    done2 = done;
                } else {
                    ConstraintWidget last2 = next3;
                    ConstraintWidget head3 = head2;
                    boolean z3 = done2;
                    BasicMeasure.Measure measure3 = measure2;
                    if (numVisibleWidgets == 0 || numVisibleWidgets != numWidgets || distance < totalSize2) {
                        return false;
                    }
                    int gap2 = distance - totalSize2;
                    if (isChainSpread) {
                        gap2 /= numVisibleWidgets + 1;
                    } else if (isChainSpreadInside && numVisibleWidgets > 2) {
                        gap2 = (gap2 / numVisibleWidgets) - 1;
                    }
                    if (numVisibleWidgets == 1) {
                        if (orientation == 0) {
                            bias = head3.getHorizontalBiasPercent();
                        } else {
                            bias = head3.getVerticalBiasPercent();
                        }
                        int i = numWidgets;
                        int p1 = (int) (((float) startPoint) + 0.5f + (((float) gap2) * bias));
                        if (orientation == 0) {
                            firstVisibleWidget.setFinalHorizontal(p1, firstVisibleWidget.getWidth() + p1);
                        } else {
                            firstVisibleWidget.setFinalVertical(p1, firstVisibleWidget.getHeight() + p1);
                        }
                        int i2 = p1;
                        horizontalSolvingPass(0 + 1, firstVisibleWidget, container.getMeasurer(), isRtl);
                        return true;
                    }
                    if (isChainSpread) {
                        boolean done3 = false;
                        int current2 = startPoint + gap2;
                        ConstraintWidget widget2 = first2;
                        while (!done3) {
                            boolean done4 = done3;
                            if (widget2.getVisibility() != 8) {
                                int current3 = current2 + widget2.mListAnchors[offset].getMargin();
                                if (orientation == 0) {
                                    widget2.setFinalHorizontal(current3, widget2.getWidth() + current3);
                                    horizontalSolvingPass(0 + 1, widget2, container.getMeasurer(), isRtl);
                                    current = current3 + widget2.getWidth();
                                } else {
                                    widget2.setFinalVertical(current3, widget2.getHeight() + current3);
                                    verticalSolvingPass(0 + 1, widget2, container.getMeasurer());
                                    current = current3 + widget2.getHeight();
                                }
                                current2 = current + widget2.mListAnchors[offset + 1].getMargin() + gap2;
                            } else if (orientation == 0) {
                                widget2.setFinalHorizontal(current2, current2);
                                horizontalSolvingPass(0 + 1, widget2, container.getMeasurer(), isRtl);
                            } else {
                                widget2.setFinalVertical(current2, current2);
                                verticalSolvingPass(0 + 1, widget2, container.getMeasurer());
                            }
                            widget2.addToSolver(system, z);
                            ConstraintAnchor nextAnchor2 = widget2.mListAnchors[offset + 1].mTarget;
                            if (nextAnchor2 != null) {
                                next = nextAnchor2.mOwner;
                                gap = gap2;
                                if (next.mListAnchors[offset].mTarget == null || next.mListAnchors[offset].mTarget.mOwner != widget2) {
                                    next = null;
                                }
                            } else {
                                gap = gap2;
                                next = null;
                            }
                            if (next != null) {
                                widget2 = next;
                            } else {
                                done4 = true;
                            }
                            done3 = done4;
                            gap2 = gap;
                            z = false;
                        }
                        int i3 = gap2;
                    } else {
                        if (isChainSpreadInside) {
                            if (numVisibleWidgets != 2) {
                                return false;
                            }
                            if (orientation == 0) {
                                firstVisibleWidget.setFinalHorizontal(startPoint, firstVisibleWidget.getWidth() + startPoint);
                                lastVisibleWidget.setFinalHorizontal(endPoint - lastVisibleWidget.getWidth(), endPoint);
                                horizontalSolvingPass(0 + 1, firstVisibleWidget, container.getMeasurer(), isRtl);
                                horizontalSolvingPass(0 + 1, lastVisibleWidget, container.getMeasurer(), isRtl);
                            } else {
                                firstVisibleWidget.setFinalVertical(startPoint, firstVisibleWidget.getHeight() + startPoint);
                                lastVisibleWidget.setFinalVertical(endPoint - lastVisibleWidget.getHeight(), endPoint);
                                verticalSolvingPass(0 + 1, firstVisibleWidget, container.getMeasurer());
                                verticalSolvingPass(0 + 1, lastVisibleWidget, container.getMeasurer());
                            }
                            return true;
                        }
                    }
                    return true;
                }
            }
        }
    }
}
