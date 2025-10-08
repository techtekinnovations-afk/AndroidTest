package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import java.util.ArrayList;

public class Chain {
    private static final boolean DEBUG = false;
    public static final boolean USE_CHAIN_OPTIMIZATION = false;

    public static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem system, ArrayList<ConstraintWidget> widgets, int orientation) {
        ChainHead[] chainsArray;
        int chainsSize;
        int offset;
        if (orientation == 0) {
            offset = 0;
            chainsSize = constraintWidgetContainer.mHorizontalChainsSize;
            chainsArray = constraintWidgetContainer.mHorizontalChainsArray;
        } else {
            offset = 2;
            chainsSize = constraintWidgetContainer.mVerticalChainsSize;
            chainsArray = constraintWidgetContainer.mVerticalChainsArray;
        }
        for (int i = 0; i < chainsSize; i++) {
            ChainHead first = chainsArray[i];
            first.define();
            if (widgets == null || widgets.contains(first.mFirst)) {
                applyChainConstraints(constraintWidgetContainer, system, orientation, offset, first);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:300:0x0609 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:305:0x0615  */
    /* JADX WARNING: Removed duplicated region for block: B:308:0x0620  */
    /* JADX WARNING: Removed duplicated region for block: B:309:0x0625  */
    /* JADX WARNING: Removed duplicated region for block: B:312:0x062a  */
    /* JADX WARNING: Removed duplicated region for block: B:313:0x062f  */
    /* JADX WARNING: Removed duplicated region for block: B:315:0x0632  */
    /* JADX WARNING: Removed duplicated region for block: B:320:0x0649  */
    /* JADX WARNING: Removed duplicated region for block: B:322:0x064c  */
    /* JADX WARNING: Removed duplicated region for block: B:323:0x0658  */
    /* JADX WARNING: Removed duplicated region for block: B:325:0x065b A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:349:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void applyChainConstraints(androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r41, androidx.constraintlayout.core.LinearSystem r42, int r43, int r44, androidx.constraintlayout.core.widgets.ChainHead r45) {
        /*
            r0 = r41
            r1 = r42
            r10 = r43
            r11 = r45
            androidx.constraintlayout.core.widgets.ConstraintWidget r12 = r11.mFirst
            androidx.constraintlayout.core.widgets.ConstraintWidget r13 = r11.mLast
            androidx.constraintlayout.core.widgets.ConstraintWidget r14 = r11.mFirstVisibleWidget
            androidx.constraintlayout.core.widgets.ConstraintWidget r15 = r11.mLastVisibleWidget
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r11.mHead
            r3 = r12
            r4 = 0
            r5 = 0
            float r6 = r11.mTotalWeight
            androidx.constraintlayout.core.widgets.ConstraintWidget r7 = r11.mFirstMatchConstraintWidget
            androidx.constraintlayout.core.widgets.ConstraintWidget r8 = r11.mLastMatchConstraintWidget
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r9 = r0.mListDimensionBehaviors
            r9 = r9[r10]
            r16 = r3
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            r17 = r4
            if (r9 != r3) goto L_0x0029
            r3 = 1
            goto L_0x002a
        L_0x0029:
            r3 = 0
        L_0x002a:
            r19 = r3
            r3 = 0
            r9 = 0
            r20 = 0
            if (r10 != 0) goto L_0x0058
            int r4 = r2.mHorizontalChainStyle
            if (r4 != 0) goto L_0x0038
            r4 = 1
            goto L_0x0039
        L_0x0038:
            r4 = 0
        L_0x0039:
            int r3 = r2.mHorizontalChainStyle
            r23 = r4
            r4 = 1
            if (r3 != r4) goto L_0x0042
            r3 = 1
            goto L_0x0043
        L_0x0042:
            r3 = 0
        L_0x0043:
            int r4 = r2.mHorizontalChainStyle
            r9 = 2
            if (r4 != r9) goto L_0x004a
            r4 = 1
            goto L_0x004b
        L_0x004a:
            r4 = 0
        L_0x004b:
            r20 = r16
            r16 = r3
            r3 = r20
            r22 = r5
            r20 = r17
            r17 = r4
            goto L_0x007d
        L_0x0058:
            int r4 = r2.mVerticalChainStyle
            if (r4 != 0) goto L_0x005e
            r4 = 1
            goto L_0x005f
        L_0x005e:
            r4 = 0
        L_0x005f:
            int r3 = r2.mVerticalChainStyle
            r23 = r4
            r4 = 1
            if (r3 != r4) goto L_0x0068
            r3 = 1
            goto L_0x0069
        L_0x0068:
            r3 = 0
        L_0x0069:
            int r4 = r2.mVerticalChainStyle
            r9 = 2
            if (r4 != r9) goto L_0x0070
            r4 = 1
            goto L_0x0071
        L_0x0070:
            r4 = 0
        L_0x0071:
            r20 = r16
            r16 = r3
            r3 = r20
            r22 = r5
            r20 = r17
            r17 = r4
        L_0x007d:
            if (r22 != 0) goto L_0x0179
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r4 = r3.mListAnchors
            r4 = r4[r44]
            r25 = 4
            if (r17 == 0) goto L_0x0089
            r25 = 1
        L_0x0089:
            int r26 = r4.getMargin()
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r9 = r3.mListDimensionBehaviors
            r9 = r9[r10]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r5 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r9 != r5) goto L_0x009d
            int[] r5 = r3.mResolvedMatchConstraintDefault
            r5 = r5[r10]
            if (r5 != 0) goto L_0x009d
            r5 = 1
            goto L_0x009e
        L_0x009d:
            r5 = 0
        L_0x009e:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r4.mTarget
            if (r9 == 0) goto L_0x00af
            if (r3 == r12) goto L_0x00af
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r4.mTarget
            int r9 = r9.getMargin()
            int r26 = r26 + r9
            r9 = r26
            goto L_0x00b1
        L_0x00af:
            r9 = r26
        L_0x00b1:
            if (r17 == 0) goto L_0x00b9
            if (r3 == r12) goto L_0x00b9
            if (r3 == r14) goto L_0x00b9
            r25 = 8
        L_0x00b9:
            r26 = r5
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r4.mTarget
            if (r5 == 0) goto L_0x00ff
            if (r3 != r14) goto L_0x00d0
            androidx.constraintlayout.core.SolverVariable r5 = r4.mSolverVariable
            r29 = r6
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r4.mTarget
            androidx.constraintlayout.core.SolverVariable r6 = r6.mSolverVariable
            r30 = r7
            r7 = 6
            r1.addGreaterThan(r5, r6, r9, r7)
            goto L_0x00df
        L_0x00d0:
            r29 = r6
            r30 = r7
            androidx.constraintlayout.core.SolverVariable r5 = r4.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r4.mTarget
            androidx.constraintlayout.core.SolverVariable r6 = r6.mSolverVariable
            r7 = 8
            r1.addGreaterThan(r5, r6, r9, r7)
        L_0x00df:
            if (r26 == 0) goto L_0x00e5
            if (r17 != 0) goto L_0x00e5
            r25 = 5
        L_0x00e5:
            if (r3 != r14) goto L_0x00f1
            if (r17 == 0) goto L_0x00f1
            boolean r5 = r3.isInBarrier(r10)
            if (r5 == 0) goto L_0x00f1
            r5 = 5
            goto L_0x00f3
        L_0x00f1:
            r5 = r25
        L_0x00f3:
            androidx.constraintlayout.core.SolverVariable r6 = r4.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor r7 = r4.mTarget
            androidx.constraintlayout.core.SolverVariable r7 = r7.mSolverVariable
            r1.addEquality(r6, r7, r9, r5)
            r25 = r5
            goto L_0x0103
        L_0x00ff:
            r29 = r6
            r30 = r7
        L_0x0103:
            if (r19 == 0) goto L_0x0140
            int r5 = r3.getVisibility()
            r7 = 8
            if (r5 == r7) goto L_0x012b
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r5 = r3.mListDimensionBehaviors
            r5 = r5[r10]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r6 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r5 != r6) goto L_0x012b
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r5 = r3.mListAnchors
            int r6 = r44 + 1
            r5 = r5[r6]
            androidx.constraintlayout.core.SolverVariable r5 = r5.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r6 = r3.mListAnchors
            r6 = r6[r44]
            androidx.constraintlayout.core.SolverVariable r6 = r6.mSolverVariable
            r24 = r4
            r4 = 0
            r7 = 5
            r1.addGreaterThan(r5, r6, r4, r7)
            goto L_0x012d
        L_0x012b:
            r24 = r4
        L_0x012d:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r4 = r3.mListAnchors
            r4 = r4[r44]
            androidx.constraintlayout.core.SolverVariable r4 = r4.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r5 = r0.mListAnchors
            r5 = r5[r44]
            androidx.constraintlayout.core.SolverVariable r5 = r5.mSolverVariable
            r6 = 0
            r7 = 8
            r1.addGreaterThan(r4, r5, r6, r7)
            goto L_0x0142
        L_0x0140:
            r24 = r4
        L_0x0142:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r4 = r3.mListAnchors
            int r5 = r44 + 1
            r4 = r4[r5]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x0168
            androidx.constraintlayout.core.widgets.ConstraintWidget r5 = r4.mOwner
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r6 = r5.mListAnchors
            r6 = r6[r44]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r6.mTarget
            if (r6 == 0) goto L_0x0164
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r6 = r5.mListAnchors
            r6 = r6[r44]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r6.mTarget
            androidx.constraintlayout.core.widgets.ConstraintWidget r6 = r6.mOwner
            if (r6 == r3) goto L_0x0161
            goto L_0x0164
        L_0x0161:
            r20 = r5
            goto L_0x016b
        L_0x0164:
            r5 = 0
            r20 = r5
            goto L_0x016b
        L_0x0168:
            r5 = 0
            r20 = r5
        L_0x016b:
            if (r20 == 0) goto L_0x0170
            r3 = r20
            goto L_0x0173
        L_0x0170:
            r5 = 1
            r22 = r5
        L_0x0173:
            r6 = r29
            r7 = r30
            goto L_0x007d
        L_0x0179:
            r29 = r6
            r30 = r7
            if (r15 == 0) goto L_0x01eb
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r5 = r13.mListAnchors
            int r6 = r44 + 1
            r5 = r5[r6]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r5.mTarget
            if (r5 == 0) goto L_0x01eb
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r5 = r15.mListAnchors
            int r6 = r44 + 1
            r5 = r5[r6]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r6 = r15.mListDimensionBehaviors
            r6 = r6[r10]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r7 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r6 != r7) goto L_0x019f
            int[] r6 = r15.mResolvedMatchConstraintDefault
            r6 = r6[r10]
            if (r6 != 0) goto L_0x019f
            r6 = 1
            goto L_0x01a0
        L_0x019f:
            r6 = 0
        L_0x01a0:
            if (r6 == 0) goto L_0x01bc
            if (r17 != 0) goto L_0x01bc
            androidx.constraintlayout.core.widgets.ConstraintAnchor r7 = r5.mTarget
            androidx.constraintlayout.core.widgets.ConstraintWidget r7 = r7.mOwner
            if (r7 != r0) goto L_0x01bc
            androidx.constraintlayout.core.SolverVariable r7 = r5.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r5.mTarget
            androidx.constraintlayout.core.SolverVariable r9 = r9.mSolverVariable
            int r4 = r5.getMargin()
            int r4 = -r4
            r26 = r3
            r3 = 5
            r1.addEquality(r7, r9, r4, r3)
            goto L_0x01d5
        L_0x01bc:
            r26 = r3
            if (r17 == 0) goto L_0x01d5
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r5.mTarget
            androidx.constraintlayout.core.widgets.ConstraintWidget r3 = r3.mOwner
            if (r3 != r0) goto L_0x01d5
            androidx.constraintlayout.core.SolverVariable r3 = r5.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r5.mTarget
            androidx.constraintlayout.core.SolverVariable r4 = r4.mSolverVariable
            int r7 = r5.getMargin()
            int r7 = -r7
            r9 = 4
            r1.addEquality(r3, r4, r7, r9)
        L_0x01d5:
            androidx.constraintlayout.core.SolverVariable r3 = r5.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r4 = r13.mListAnchors
            int r7 = r44 + 1
            r4 = r4[r7]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r4.mTarget
            androidx.constraintlayout.core.SolverVariable r4 = r4.mSolverVariable
            int r7 = r5.getMargin()
            int r7 = -r7
            r9 = 6
            r1.addLowerThan(r3, r4, r7, r9)
            goto L_0x01ed
        L_0x01eb:
            r26 = r3
        L_0x01ed:
            if (r19 == 0) goto L_0x020e
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r3 = r0.mListAnchors
            int r4 = r44 + 1
            r3 = r3[r4]
            androidx.constraintlayout.core.SolverVariable r3 = r3.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r4 = r13.mListAnchors
            int r5 = r44 + 1
            r4 = r4[r5]
            androidx.constraintlayout.core.SolverVariable r4 = r4.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r5 = r13.mListAnchors
            int r6 = r44 + 1
            r5 = r5[r6]
            int r5 = r5.getMargin()
            r7 = 8
            r1.addGreaterThan(r3, r4, r5, r7)
        L_0x020e:
            java.util.ArrayList<androidx.constraintlayout.core.widgets.ConstraintWidget> r3 = r11.mWeightedMatchConstraintsWidgets
            if (r3 == 0) goto L_0x02de
            int r4 = r3.size()
            r5 = 1
            if (r4 <= r5) goto L_0x02d9
            r6 = 0
            r7 = 0
            boolean r9 = r11.mHasUndefinedWeights
            if (r9 == 0) goto L_0x0229
            boolean r9 = r11.mHasComplexMatchWeights
            if (r9 != 0) goto L_0x0229
            int r9 = r11.mWidgetsMatchCount
            float r9 = (float) r9
            r33 = r9
            goto L_0x022b
        L_0x0229:
            r33 = r29
        L_0x022b:
            r9 = 0
            r32 = r7
        L_0x022e:
            if (r9 >= r4) goto L_0x02d2
            java.lang.Object r7 = r3.get(r9)
            androidx.constraintlayout.core.widgets.ConstraintWidget r7 = (androidx.constraintlayout.core.widgets.ConstraintWidget) r7
            float[] r5 = r7.mWeight
            r5 = r5[r10]
            r24 = 0
            int r28 = (r5 > r24 ? 1 : (r5 == r24 ? 0 : -1))
            if (r28 >= 0) goto L_0x026c
            boolean r0 = r11.mHasComplexMatchWeights
            if (r0 == 0) goto L_0x0260
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r0 = r7.mListAnchors
            int r24 = r44 + 1
            r0 = r0[r24]
            androidx.constraintlayout.core.SolverVariable r0 = r0.mSolverVariable
            r28 = r3
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r3 = r7.mListAnchors
            r3 = r3[r44]
            androidx.constraintlayout.core.SolverVariable r3 = r3.mSolverVariable
            r39 = r4
            r25 = r5
            r4 = 4
            r5 = 0
            r1.addEquality(r0, r3, r5, r4)
            r4 = 0
            goto L_0x02c7
        L_0x0260:
            r28 = r3
            r39 = r4
            r25 = r5
            r4 = 4
            r5 = 1065353216(0x3f800000, float:1.0)
            r34 = r5
            goto L_0x0275
        L_0x026c:
            r28 = r3
            r39 = r4
            r25 = r5
            r4 = 4
            r34 = r25
        L_0x0275:
            int r0 = (r34 > r24 ? 1 : (r34 == r24 ? 0 : -1))
            if (r0 != 0) goto L_0x028e
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r0 = r7.mListAnchors
            int r3 = r44 + 1
            r0 = r0[r3]
            androidx.constraintlayout.core.SolverVariable r0 = r0.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r3 = r7.mListAnchors
            r3 = r3[r44]
            androidx.constraintlayout.core.SolverVariable r3 = r3.mSolverVariable
            r4 = 0
            r5 = 8
            r1.addEquality(r0, r3, r4, r5)
            goto L_0x02c7
        L_0x028e:
            r4 = 0
            if (r6 == 0) goto L_0x02c1
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r0 = r6.mListAnchors
            r0 = r0[r44]
            androidx.constraintlayout.core.SolverVariable r0 = r0.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r3 = r6.mListAnchors
            int r5 = r44 + 1
            r3 = r3[r5]
            androidx.constraintlayout.core.SolverVariable r3 = r3.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r5 = r7.mListAnchors
            r5 = r5[r44]
            androidx.constraintlayout.core.SolverVariable r5 = r5.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r4 = r7.mListAnchors
            int r24 = r44 + 1
            r4 = r4[r24]
            androidx.constraintlayout.core.SolverVariable r4 = r4.mSolverVariable
            androidx.constraintlayout.core.ArrayRow r31 = r1.createRow()
            r35 = r0
            r36 = r3
            r38 = r4
            r37 = r5
            r31.createRowEqualMatchDimensions(r32, r33, r34, r35, r36, r37, r38)
            r0 = r31
            r1.addConstraint(r0)
        L_0x02c1:
            r0 = r7
            r3 = r34
            r6 = r0
            r32 = r3
        L_0x02c7:
            int r9 = r9 + 1
            r5 = 1
            r0 = r41
            r3 = r28
            r4 = r39
            goto L_0x022e
        L_0x02d2:
            r28 = r3
            r39 = r4
            r29 = r33
            goto L_0x02e0
        L_0x02d9:
            r28 = r3
            r39 = r4
            goto L_0x02e0
        L_0x02de:
            r28 = r3
        L_0x02e0:
            if (r14 == 0) goto L_0x0357
            if (r14 == r15) goto L_0x02ed
            if (r17 == 0) goto L_0x02e7
            goto L_0x02ed
        L_0x02e7:
            r24 = r2
            r25 = r8
            goto L_0x035b
        L_0x02ed:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r3 = r12.mListAnchors
            r3 = r3[r44]
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r4 = r13.mListAnchors
            int r5 = r44 + 1
            r4 = r4[r5]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r3.mTarget
            if (r5 == 0) goto L_0x0300
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r3.mTarget
            androidx.constraintlayout.core.SolverVariable r5 = r5.mSolverVariable
            goto L_0x0301
        L_0x0300:
            r5 = 0
        L_0x0301:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r4.mTarget
            if (r6 == 0) goto L_0x030a
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r4.mTarget
            androidx.constraintlayout.core.SolverVariable r6 = r6.mSolverVariable
            goto L_0x030b
        L_0x030a:
            r6 = 0
        L_0x030b:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r7 = r14.mListAnchors
            r3 = r7[r44]
            if (r15 == 0) goto L_0x0317
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r7 = r15.mListAnchors
            int r9 = r44 + 1
            r4 = r7[r9]
        L_0x0317:
            if (r5 == 0) goto L_0x034a
            if (r6 == 0) goto L_0x034a
            r7 = 1056964608(0x3f000000, float:0.5)
            if (r10 != 0) goto L_0x0322
            float r7 = r2.mHorizontalBiasPercent
            goto L_0x0324
        L_0x0322:
            float r7 = r2.mVerticalBiasPercent
        L_0x0324:
            int r9 = r3.getMargin()
            r18 = r8
            int r8 = r4.getMargin()
            r21 = r2
            androidx.constraintlayout.core.SolverVariable r2 = r3.mSolverVariable
            r24 = r3
            r3 = r5
            r5 = r7
            androidx.constraintlayout.core.SolverVariable r7 = r4.mSolverVariable
            r25 = r4
            r4 = r9
            r9 = 7
            r40 = r25
            r25 = r18
            r18 = r24
            r24 = r21
            r21 = r40
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x0353
        L_0x034a:
            r24 = r2
            r18 = r3
            r21 = r4
            r3 = r5
            r25 = r8
        L_0x0353:
            r1 = r42
            goto L_0x0605
        L_0x0357:
            r24 = r2
            r25 = r8
        L_0x035b:
            if (r23 == 0) goto L_0x0488
            if (r14 == 0) goto L_0x0488
            r1 = r14
            r2 = r14
            int r3 = r11.mWidgetsMatchCount
            if (r3 <= 0) goto L_0x036d
            int r3 = r11.mWidgetsCount
            int r4 = r11.mWidgetsMatchCount
            if (r3 != r4) goto L_0x036d
            r4 = 1
            goto L_0x036e
        L_0x036d:
            r4 = 0
        L_0x036e:
            r18 = r4
            r40 = r2
            r2 = r1
            r1 = r40
        L_0x0375:
            if (r2 == 0) goto L_0x047e
            androidx.constraintlayout.core.widgets.ConstraintWidget[] r3 = r2.mNextChainWidget
            r3 = r3[r10]
        L_0x037b:
            if (r3 == 0) goto L_0x038a
            int r4 = r3.getVisibility()
            r7 = 8
            if (r4 != r7) goto L_0x038c
            androidx.constraintlayout.core.widgets.ConstraintWidget[] r4 = r3.mNextChainWidget
            r3 = r4[r10]
            goto L_0x037b
        L_0x038a:
            r7 = 8
        L_0x038c:
            if (r3 != 0) goto L_0x039a
            if (r2 != r15) goto L_0x0391
            goto L_0x039a
        L_0x0391:
            r34 = r1
            r33 = r2
            r26 = r3
            r0 = r7
            goto L_0x046d
        L_0x039a:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r4 = r2.mListAnchors
            r4 = r4[r44]
            androidx.constraintlayout.core.SolverVariable r5 = r4.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r4.mTarget
            if (r6 == 0) goto L_0x03a9
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r4.mTarget
            androidx.constraintlayout.core.SolverVariable r6 = r6.mSolverVariable
            goto L_0x03aa
        L_0x03a9:
            r6 = 0
        L_0x03aa:
            if (r1 == r2) goto L_0x03b5
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r8 = r1.mListAnchors
            int r9 = r44 + 1
            r8 = r8[r9]
            androidx.constraintlayout.core.SolverVariable r6 = r8.mSolverVariable
            goto L_0x03ca
        L_0x03b5:
            if (r2 != r14) goto L_0x03ca
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r8 = r12.mListAnchors
            r8 = r8[r44]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r8 = r8.mTarget
            if (r8 == 0) goto L_0x03c8
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r8 = r12.mListAnchors
            r8 = r8[r44]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r8 = r8.mTarget
            androidx.constraintlayout.core.SolverVariable r8 = r8.mSolverVariable
            goto L_0x03c9
        L_0x03c8:
            r8 = 0
        L_0x03c9:
            r6 = r8
        L_0x03ca:
            r8 = 0
            r9 = 0
            r20 = 0
            int r21 = r4.getMargin()
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r0 = r2.mListAnchors
            int r26 = r44 + 1
            r0 = r0[r26]
            int r0 = r0.getMargin()
            if (r3 == 0) goto L_0x03e7
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r7 = r3.mListAnchors
            r7 = r7[r44]
            androidx.constraintlayout.core.SolverVariable r9 = r7.mSolverVariable
            r31 = r7
            goto L_0x03f8
        L_0x03e7:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r7 = r13.mListAnchors
            int r31 = r44 + 1
            r7 = r7[r31]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r7 = r7.mTarget
            if (r7 == 0) goto L_0x03f6
            androidx.constraintlayout.core.SolverVariable r9 = r7.mSolverVariable
            r31 = r7
            goto L_0x03f8
        L_0x03f6:
            r31 = r7
        L_0x03f8:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r7 = r2.mListAnchors
            int r8 = r44 + 1
            r7 = r7[r8]
            androidx.constraintlayout.core.SolverVariable r7 = r7.mSolverVariable
            if (r31 == 0) goto L_0x0407
            int r8 = r31.getMargin()
            int r0 = r0 + r8
        L_0x0407:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r8 = r1.mListAnchors
            int r20 = r44 + 1
            r8 = r8[r20]
            int r8 = r8.getMargin()
            int r21 = r21 + r8
            if (r5 == 0) goto L_0x045e
            if (r6 == 0) goto L_0x045e
            if (r9 == 0) goto L_0x045e
            if (r7 == 0) goto L_0x045e
            r8 = r21
            if (r2 != r14) goto L_0x042a
            r20 = r0
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r0 = r14.mListAnchors
            r0 = r0[r44]
            int r8 = r0.getMargin()
            goto L_0x042c
        L_0x042a:
            r20 = r0
        L_0x042c:
            r0 = r20
            if (r2 != r15) goto L_0x043d
            r32 = r0
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r0 = r15.mListAnchors
            int r33 = r44 + 1
            r0 = r0[r33]
            int r0 = r0.getMargin()
            goto L_0x043f
        L_0x043d:
            r32 = r0
        L_0x043f:
            r32 = 5
            if (r18 == 0) goto L_0x0445
            r32 = 8
        L_0x0445:
            r33 = r2
            r2 = r5
            r5 = 1056964608(0x3f000000, float:0.5)
            r34 = r1
            r26 = r3
            r3 = r6
            r6 = r9
            r9 = r32
            r1 = r42
            r32 = r4
            r4 = r8
            r8 = r0
            r0 = 8
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x046d
        L_0x045e:
            r20 = r0
            r34 = r1
            r33 = r2
            r26 = r3
            r32 = r4
            r2 = r5
            r3 = r6
            r6 = r9
            r0 = 8
        L_0x046d:
            int r1 = r33.getVisibility()
            if (r1 == r0) goto L_0x0476
            r1 = r33
            goto L_0x0478
        L_0x0476:
            r1 = r34
        L_0x0478:
            r2 = r26
            r20 = r26
            goto L_0x0375
        L_0x047e:
            r34 = r1
            r33 = r2
            r1 = r42
            r31 = r33
            goto L_0x0607
        L_0x0488:
            r0 = 8
            if (r16 == 0) goto L_0x0603
            if (r14 == 0) goto L_0x0603
            r1 = r14
            r2 = r14
            int r3 = r11.mWidgetsMatchCount
            if (r3 <= 0) goto L_0x049c
            int r3 = r11.mWidgetsCount
            int r4 = r11.mWidgetsMatchCount
            if (r3 != r4) goto L_0x049c
            r4 = 1
            goto L_0x049d
        L_0x049c:
            r4 = 0
        L_0x049d:
            r18 = r4
            r40 = r2
            r2 = r1
            r1 = r40
        L_0x04a4:
            if (r2 == 0) goto L_0x0581
            androidx.constraintlayout.core.widgets.ConstraintWidget[] r3 = r2.mNextChainWidget
            r3 = r3[r10]
        L_0x04aa:
            if (r3 == 0) goto L_0x04b7
            int r4 = r3.getVisibility()
            if (r4 != r0) goto L_0x04b7
            androidx.constraintlayout.core.widgets.ConstraintWidget[] r4 = r3.mNextChainWidget
            r3 = r4[r10]
            goto L_0x04aa
        L_0x04b7:
            if (r2 == r14) goto L_0x056a
            if (r2 == r15) goto L_0x056a
            if (r3 == 0) goto L_0x056a
            if (r3 != r15) goto L_0x04c0
            r3 = 0
        L_0x04c0:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r4 = r2.mListAnchors
            r4 = r4[r44]
            androidx.constraintlayout.core.SolverVariable r5 = r4.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r4.mTarget
            if (r6 == 0) goto L_0x04cf
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r4.mTarget
            androidx.constraintlayout.core.SolverVariable r6 = r6.mSolverVariable
            goto L_0x04d0
        L_0x04cf:
            r6 = 0
        L_0x04d0:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r7 = r1.mListAnchors
            int r8 = r44 + 1
            r7 = r7[r8]
            androidx.constraintlayout.core.SolverVariable r6 = r7.mSolverVariable
            r7 = 0
            r8 = 0
            r9 = 0
            int r20 = r4.getMargin()
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r0 = r2.mListAnchors
            int r26 = r44 + 1
            r0 = r0[r26]
            int r0 = r0.getMargin()
            if (r3 == 0) goto L_0x04fe
            r26 = r0
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r0 = r3.mListAnchors
            r0 = r0[r44]
            androidx.constraintlayout.core.SolverVariable r7 = r0.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor r8 = r0.mTarget
            if (r8 == 0) goto L_0x04fc
            androidx.constraintlayout.core.widgets.ConstraintAnchor r8 = r0.mTarget
            androidx.constraintlayout.core.SolverVariable r8 = r8.mSolverVariable
            goto L_0x04fd
        L_0x04fc:
            r8 = 0
        L_0x04fd:
            goto L_0x0515
        L_0x04fe:
            r26 = r0
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r0 = r15.mListAnchors
            r0 = r0[r44]
            if (r0 == 0) goto L_0x0508
            androidx.constraintlayout.core.SolverVariable r8 = r0.mSolverVariable
        L_0x0508:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r7 = r2.mListAnchors
            int r31 = r44 + 1
            r7 = r7[r31]
            androidx.constraintlayout.core.SolverVariable r7 = r7.mSolverVariable
            r40 = r8
            r8 = r7
            r7 = r40
        L_0x0515:
            if (r0 == 0) goto L_0x051e
            int r9 = r0.getMargin()
            int r9 = r26 + r9
            goto L_0x0520
        L_0x051e:
            r9 = r26
        L_0x0520:
            r26 = r0
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r0 = r1.mListAnchors
            int r31 = r44 + 1
            r0 = r0[r31]
            int r0 = r0.getMargin()
            int r20 = r20 + r0
            r0 = 4
            if (r18 == 0) goto L_0x0533
            r0 = 8
        L_0x0533:
            if (r5 == 0) goto L_0x0554
            if (r6 == 0) goto L_0x0554
            if (r7 == 0) goto L_0x0554
            if (r8 == 0) goto L_0x0554
            r31 = r2
            r2 = r5
            r5 = 1056964608(0x3f000000, float:0.5)
            r32 = r20
            r20 = r4
            r4 = r32
            r32 = r3
            r3 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r0
            r0 = r1
            r1 = r42
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x0567
        L_0x0554:
            r31 = r20
            r20 = r4
            r4 = r31
            r31 = r2
            r32 = r3
            r2 = r5
            r3 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r0
            r0 = r1
            r1 = r42
        L_0x0567:
            r20 = r32
            goto L_0x0571
        L_0x056a:
            r0 = r1
            r31 = r2
            r1 = r42
            r20 = r3
        L_0x0571:
            int r2 = r31.getVisibility()
            r7 = 8
            if (r2 == r7) goto L_0x057b
            r0 = r31
        L_0x057b:
            r2 = r20
            r1 = r0
            r0 = r7
            goto L_0x04a4
        L_0x0581:
            r0 = r1
            r31 = r2
            r1 = r42
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r14.mListAnchors
            r2 = r2[r44]
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r3 = r12.mListAnchors
            r3 = r3[r44]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r3.mTarget
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r4 = r15.mListAnchors
            int r5 = r44 + 1
            r4 = r4[r5]
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r5 = r13.mListAnchors
            int r6 = r44 + 1
            r5 = r5[r6]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r5.mTarget
            r9 = 5
            if (r3 == 0) goto L_0x05ea
            if (r14 == r15) goto L_0x05b7
            androidx.constraintlayout.core.SolverVariable r6 = r2.mSolverVariable
            androidx.constraintlayout.core.SolverVariable r7 = r3.mSolverVariable
            int r8 = r2.getMargin()
            r1.addEquality(r6, r7, r8, r9)
            r32 = r0
            r21 = r2
            r26 = r3
            r0 = r4
            r10 = r5
            goto L_0x05f2
        L_0x05b7:
            if (r5 == 0) goto L_0x05e1
            r6 = r2
            androidx.constraintlayout.core.SolverVariable r2 = r6.mSolverVariable
            r7 = r3
            androidx.constraintlayout.core.SolverVariable r3 = r7.mSolverVariable
            int r8 = r6.getMargin()
            r21 = r6
            androidx.constraintlayout.core.SolverVariable r6 = r4.mSolverVariable
            r26 = r7
            androidx.constraintlayout.core.SolverVariable r7 = r5.mSolverVariable
            r32 = r4
            r4 = r8
            int r8 = r32.getMargin()
            r33 = r5
            r5 = 1056964608(0x3f000000, float:0.5)
            r10 = r32
            r32 = r0
            r0 = r10
            r10 = r33
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x05f2
        L_0x05e1:
            r32 = r0
            r21 = r2
            r26 = r3
            r0 = r4
            r10 = r5
            goto L_0x05f2
        L_0x05ea:
            r32 = r0
            r21 = r2
            r26 = r3
            r0 = r4
            r10 = r5
        L_0x05f2:
            if (r10 == 0) goto L_0x0607
            if (r14 == r15) goto L_0x0607
            androidx.constraintlayout.core.SolverVariable r2 = r0.mSolverVariable
            androidx.constraintlayout.core.SolverVariable r3 = r10.mSolverVariable
            int r4 = r0.getMargin()
            int r4 = -r4
            r1.addEquality(r2, r3, r4, r9)
            goto L_0x0607
        L_0x0603:
            r1 = r42
        L_0x0605:
            r31 = r26
        L_0x0607:
            if (r23 != 0) goto L_0x060b
            if (r16 == 0) goto L_0x0675
        L_0x060b:
            if (r14 == 0) goto L_0x0675
            if (r14 == r15) goto L_0x0675
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r0 = r14.mListAnchors
            r0 = r0[r44]
            if (r15 != 0) goto L_0x0616
            r15 = r14
        L_0x0616:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r15.mListAnchors
            int r3 = r44 + 1
            r2 = r2[r3]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r0.mTarget
            if (r3 == 0) goto L_0x0625
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r0.mTarget
            androidx.constraintlayout.core.SolverVariable r3 = r3.mSolverVariable
            goto L_0x0626
        L_0x0625:
            r3 = 0
        L_0x0626:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r2.mTarget
            if (r4 == 0) goto L_0x062f
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r2.mTarget
            androidx.constraintlayout.core.SolverVariable r4 = r4.mSolverVariable
            goto L_0x0630
        L_0x062f:
            r4 = 0
        L_0x0630:
            if (r13 == r15) goto L_0x0649
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r5 = r13.mListAnchors
            int r6 = r44 + 1
            r5 = r5[r6]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r5.mTarget
            if (r6 == 0) goto L_0x0643
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r5.mTarget
            androidx.constraintlayout.core.SolverVariable r6 = r6.mSolverVariable
            r27 = r6
            goto L_0x0645
        L_0x0643:
            r27 = 0
        L_0x0645:
            r4 = r27
            r6 = r4
            goto L_0x064a
        L_0x0649:
            r6 = r4
        L_0x064a:
            if (r14 != r15) goto L_0x0658
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r4 = r14.mListAnchors
            r0 = r4[r44]
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r4 = r14.mListAnchors
            int r5 = r44 + 1
            r2 = r4[r5]
            r10 = r2
            goto L_0x0659
        L_0x0658:
            r10 = r2
        L_0x0659:
            if (r3 == 0) goto L_0x0675
            if (r6 == 0) goto L_0x0675
            r5 = 1056964608(0x3f000000, float:0.5)
            int r4 = r0.getMargin()
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r15.mListAnchors
            int r7 = r44 + 1
            r2 = r2[r7]
            int r8 = r2.getMargin()
            androidx.constraintlayout.core.SolverVariable r2 = r0.mSolverVariable
            androidx.constraintlayout.core.SolverVariable r7 = r10.mSolverVariable
            r9 = 5
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
        L_0x0675:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.Chain.applyChainConstraints(androidx.constraintlayout.core.widgets.ConstraintWidgetContainer, androidx.constraintlayout.core.LinearSystem, int, int, androidx.constraintlayout.core.widgets.ChainHead):void");
    }
}
