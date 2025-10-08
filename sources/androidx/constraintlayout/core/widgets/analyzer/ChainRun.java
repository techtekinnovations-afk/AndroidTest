package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

public class ChainRun extends WidgetRun {
    private int mChainStyle;
    ArrayList<WidgetRun> mWidgets = new ArrayList<>();

    public ChainRun(ConstraintWidget widget, int orientation) {
        super(widget);
        this.orientation = orientation;
        build();
    }

    public String toString() {
        StringBuilder log = new StringBuilder("ChainRun ");
        log.append(this.orientation == 0 ? "horizontal : " : "vertical : ");
        Iterator<WidgetRun> it = this.mWidgets.iterator();
        while (it.hasNext()) {
            log.append("<");
            log.append(it.next());
            log.append("> ");
        }
        return log.toString();
    }

    /* access modifiers changed from: package-private */
    public boolean supportsWrapComputation() {
        int count = this.mWidgets.size();
        for (int i = 0; i < count; i++) {
            if (!this.mWidgets.get(i).supportsWrapComputation()) {
                return false;
            }
        }
        return true;
    }

    public long getWrapDimension() {
        int count = this.mWidgets.size();
        long wrapDimension = 0;
        for (int i = 0; i < count; i++) {
            WidgetRun run = this.mWidgets.get(i);
            wrapDimension = wrapDimension + ((long) run.start.mMargin) + run.getWrapDimension() + ((long) run.end.mMargin);
        }
        return wrapDimension;
    }

    private void build() {
        ConstraintWidget current = this.mWidget;
        ConstraintWidget previous = current.getPreviousChainMember(this.orientation);
        while (previous != null) {
            current = previous;
            previous = current.getPreviousChainMember(this.orientation);
        }
        this.mWidget = current;
        this.mWidgets.add(current.getRun(this.orientation));
        ConstraintWidget next = current.getNextChainMember(this.orientation);
        while (next != null) {
            ConstraintWidget current2 = next;
            this.mWidgets.add(current2.getRun(this.orientation));
            next = current2.getNextChainMember(this.orientation);
        }
        Iterator<WidgetRun> it = this.mWidgets.iterator();
        while (it.hasNext()) {
            WidgetRun run = it.next();
            if (this.orientation == 0) {
                run.mWidget.horizontalChainRun = this;
            } else if (this.orientation == 1) {
                run.mWidget.verticalChainRun = this;
            }
        }
        if ((this.orientation == 0 && ((ConstraintWidgetContainer) this.mWidget.getParent()).isRtl()) && this.mWidgets.size() > 1) {
            this.mWidget = this.mWidgets.get(this.mWidgets.size() - 1).mWidget;
        }
        this.mChainStyle = this.orientation == 0 ? this.mWidget.getHorizontalChainStyle() : this.mWidget.getVerticalChainStyle();
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.mRunGroup = null;
        Iterator<WidgetRun> it = this.mWidgets.iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
    }

    /* access modifiers changed from: package-private */
    public void reset() {
        this.start.resolved = false;
        this.end.resolved = false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:56:0x00e6  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00f8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void update(androidx.constraintlayout.core.widgets.analyzer.Dependency r28) {
        /*
            r27 = this;
            r0 = r27
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r0.start
            boolean r1 = r1.resolved
            if (r1 == 0) goto L_0x0494
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r0.end
            boolean r1 = r1.resolved
            if (r1 != 0) goto L_0x0010
            goto L_0x0494
        L_0x0010:
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r0.mWidget
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r1.getParent()
            r2 = 0
            boolean r3 = r1 instanceof androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
            if (r3 == 0) goto L_0x0022
            r3 = r1
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r3 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r3
            boolean r2 = r3.isRtl()
        L_0x0022:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r3 = r0.end
            int r3 = r3.value
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r4 = r0.start
            int r4 = r4.value
            int r3 = r3 - r4
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r8 = r0.mWidgets
            int r8 = r8.size()
            r9 = -1
            r10 = 0
        L_0x0037:
            r11 = 8
            if (r10 >= r8) goto L_0x0051
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r12 = r0.mWidgets
            java.lang.Object r12 = r12.get(r10)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r12 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r12
            androidx.constraintlayout.core.widgets.ConstraintWidget r13 = r12.mWidget
            int r13 = r13.getVisibility()
            if (r13 != r11) goto L_0x004f
            int r10 = r10 + 1
            goto L_0x0037
        L_0x004f:
            r9 = r10
        L_0x0051:
            r10 = -1
            int r12 = r8 + -1
        L_0x0054:
            if (r12 < 0) goto L_0x006c
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r13 = r0.mWidgets
            java.lang.Object r13 = r13.get(r12)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r13 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r13
            androidx.constraintlayout.core.widgets.ConstraintWidget r14 = r13.mWidget
            int r14 = r14.getVisibility()
            if (r14 != r11) goto L_0x006a
            int r12 = r12 + -1
            goto L_0x0054
        L_0x006a:
            r10 = r12
        L_0x006c:
            r12 = 0
        L_0x006d:
            r15 = 2
            r16 = 0
            if (r12 >= r15) goto L_0x0123
            r17 = 0
            r14 = r17
        L_0x0076:
            if (r14 >= r8) goto L_0x0110
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r15 = r0.mWidgets
            java.lang.Object r15 = r15.get(r14)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r15 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r15
            androidx.constraintlayout.core.widgets.ConstraintWidget r13 = r15.mWidget
            int r13 = r13.getVisibility()
            if (r13 != r11) goto L_0x008c
            r19 = r1
            goto L_0x0107
        L_0x008c:
            int r7 = r7 + 1
            if (r14 <= 0) goto L_0x0097
            if (r14 < r9) goto L_0x0097
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r13 = r15.start
            int r13 = r13.mMargin
            int r4 = r4 + r13
        L_0x0097:
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r13 = r15.mDimension
            int r13 = r13.value
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r11 = r15.mDimensionBehavior
            r19 = r1
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r11 == r1) goto L_0x00a5
            r1 = 1
            goto L_0x00a6
        L_0x00a5:
            r1 = 0
        L_0x00a6:
            if (r1 == 0) goto L_0x00c9
            int r11 = r0.orientation
            if (r11 != 0) goto L_0x00b7
            androidx.constraintlayout.core.widgets.ConstraintWidget r11 = r15.mWidget
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r11 = r11.mHorizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r11 = r11.mDimension
            boolean r11 = r11.resolved
            if (r11 != 0) goto L_0x00b7
            return
        L_0x00b7:
            int r11 = r0.orientation
            r20 = r1
            r1 = 1
            if (r11 != r1) goto L_0x00e2
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r15.mWidget
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r1 = r1.mVerticalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r1 = r1.mDimension
            boolean r1 = r1.resolved
            if (r1 != 0) goto L_0x00e2
            return
        L_0x00c9:
            r20 = r1
            int r1 = r15.matchConstraintsType
            r11 = 1
            if (r1 != r11) goto L_0x00da
            if (r12 != 0) goto L_0x00da
            r1 = 1
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r11 = r15.mDimension
            int r13 = r11.wrapValue
            int r5 = r5 + 1
            goto L_0x00e4
        L_0x00da:
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r1 = r15.mDimension
            boolean r1 = r1.resolved
            if (r1 == 0) goto L_0x00e2
            r1 = 1
            goto L_0x00e4
        L_0x00e2:
            r1 = r20
        L_0x00e4:
            if (r1 != 0) goto L_0x00f8
            int r5 = r5 + 1
            androidx.constraintlayout.core.widgets.ConstraintWidget r11 = r15.mWidget
            float[] r11 = r11.mWeight
            r20 = r1
            int r1 = r0.orientation
            r1 = r11[r1]
            int r11 = (r1 > r16 ? 1 : (r1 == r16 ? 0 : -1))
            if (r11 < 0) goto L_0x00f7
            float r6 = r6 + r1
        L_0x00f7:
            goto L_0x00fb
        L_0x00f8:
            r20 = r1
            int r4 = r4 + r13
        L_0x00fb:
            int r1 = r8 + -1
            if (r14 >= r1) goto L_0x0107
            if (r14 >= r10) goto L_0x0107
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r15.end
            int r1 = r1.mMargin
            int r1 = -r1
            int r4 = r4 + r1
        L_0x0107:
            int r14 = r14 + 1
            r1 = r19
            r11 = 8
            r15 = 2
            goto L_0x0076
        L_0x0110:
            r19 = r1
            if (r4 < r3) goto L_0x0125
            if (r5 != 0) goto L_0x0117
            goto L_0x0125
        L_0x0117:
            r7 = 0
            r5 = 0
            r4 = 0
            r6 = 0
            int r12 = r12 + 1
            r1 = r19
            r11 = 8
            goto L_0x006d
        L_0x0123:
            r19 = r1
        L_0x0125:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r0.start
            int r1 = r1.value
            if (r2 == 0) goto L_0x012f
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r11 = r0.end
            int r1 = r11.value
        L_0x012f:
            r11 = 1056964608(0x3f000000, float:0.5)
            if (r4 <= r3) goto L_0x0146
            r12 = 1073741824(0x40000000, float:2.0)
            if (r2 == 0) goto L_0x013f
            int r13 = r4 - r3
            float r13 = (float) r13
            float r13 = r13 / r12
            float r13 = r13 + r11
            int r12 = (int) r13
            int r1 = r1 + r12
            goto L_0x0146
        L_0x013f:
            int r13 = r4 - r3
            float r13 = (float) r13
            float r13 = r13 / r12
            float r13 = r13 + r11
            int r12 = (int) r13
            int r1 = r1 - r12
        L_0x0146:
            r12 = 0
            if (r5 <= 0) goto L_0x0258
            int r13 = r3 - r4
            float r13 = (float) r13
            float r14 = (float) r5
            float r13 = r13 / r14
            float r13 = r13 + r11
            int r12 = (int) r13
            r13 = 0
            r14 = 0
        L_0x0152:
            if (r14 >= r8) goto L_0x0203
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r15 = r0.mWidgets
            java.lang.Object r15 = r15.get(r14)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r15 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r15
            r20 = r11
            androidx.constraintlayout.core.widgets.ConstraintWidget r11 = r15.mWidget
            int r11 = r11.getVisibility()
            r21 = r1
            r1 = 8
            if (r11 != r1) goto L_0x0174
            r22 = r2
            r23 = r4
            r24 = r5
            r25 = r6
            goto L_0x01f3
        L_0x0174:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r1 = r15.mDimensionBehavior
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r11 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 != r11) goto L_0x01eb
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r1 = r15.mDimension
            boolean r1 = r1.resolved
            if (r1 != 0) goto L_0x01eb
            r1 = r12
            int r11 = (r6 > r16 ? 1 : (r6 == r16 ? 0 : -1))
            if (r11 <= 0) goto L_0x0199
            androidx.constraintlayout.core.widgets.ConstraintWidget r11 = r15.mWidget
            float[] r11 = r11.mWeight
            r22 = r1
            int r1 = r0.orientation
            r1 = r11[r1]
            int r11 = r3 - r4
            float r11 = (float) r11
            float r11 = r11 * r1
            float r11 = r11 / r6
            float r11 = r11 + r20
            int r11 = (int) r11
            r1 = r11
            goto L_0x019b
        L_0x0199:
            r22 = r1
        L_0x019b:
            r11 = r1
            r22 = r2
            int r2 = r0.orientation
            if (r2 != 0) goto L_0x01b4
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r15.mWidget
            int r2 = r2.mMatchConstraintMaxWidth
            r23 = r2
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r15.mWidget
            int r2 = r2.mMatchConstraintMinWidth
            r26 = r4
            r4 = r2
            r2 = r23
            r23 = r26
            goto L_0x01c5
        L_0x01b4:
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r15.mWidget
            int r2 = r2.mMatchConstraintMaxHeight
            r23 = r2
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r15.mWidget
            int r2 = r2.mMatchConstraintMinHeight
            r26 = r4
            r4 = r2
            r2 = r23
            r23 = r26
        L_0x01c5:
            r24 = r5
            int r5 = r15.matchConstraintsType
            r25 = r6
            r6 = 1
            if (r5 != r6) goto L_0x01d6
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r5 = r15.mDimension
            int r5 = r5.wrapValue
            int r11 = java.lang.Math.min(r11, r5)
        L_0x01d6:
            int r5 = java.lang.Math.max(r4, r11)
            if (r2 <= 0) goto L_0x01e0
            int r5 = java.lang.Math.min(r2, r5)
        L_0x01e0:
            if (r5 == r1) goto L_0x01e5
            int r13 = r13 + 1
            r1 = r5
        L_0x01e5:
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r6 = r15.mDimension
            r6.resolve(r1)
            goto L_0x01f3
        L_0x01eb:
            r22 = r2
            r23 = r4
            r24 = r5
            r25 = r6
        L_0x01f3:
            int r14 = r14 + 1
            r11 = r20
            r1 = r21
            r2 = r22
            r4 = r23
            r5 = r24
            r6 = r25
            goto L_0x0152
        L_0x0203:
            r21 = r1
            r22 = r2
            r23 = r4
            r24 = r5
            r25 = r6
            r20 = r11
            if (r13 <= 0) goto L_0x0249
            int r5 = r24 - r13
            r1 = 0
            r2 = 0
        L_0x0215:
            if (r2 >= r8) goto L_0x0247
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r4 = r0.mWidgets
            java.lang.Object r4 = r4.get(r2)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r4 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r4
            androidx.constraintlayout.core.widgets.ConstraintWidget r6 = r4.mWidget
            int r6 = r6.getVisibility()
            r11 = 8
            if (r6 != r11) goto L_0x022a
            goto L_0x0244
        L_0x022a:
            if (r2 <= 0) goto L_0x0233
            if (r2 < r9) goto L_0x0233
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r6 = r4.start
            int r6 = r6.mMargin
            int r1 = r1 + r6
        L_0x0233:
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r6 = r4.mDimension
            int r6 = r6.value
            int r1 = r1 + r6
            int r6 = r8 + -1
            if (r2 >= r6) goto L_0x0244
            if (r2 >= r10) goto L_0x0244
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r6 = r4.end
            int r6 = r6.mMargin
            int r6 = -r6
            int r1 = r1 + r6
        L_0x0244:
            int r2 = r2 + 1
            goto L_0x0215
        L_0x0247:
            r4 = r1
            goto L_0x024d
        L_0x0249:
            r4 = r23
            r5 = r24
        L_0x024d:
            int r1 = r0.mChainStyle
            r2 = 2
            if (r1 != r2) goto L_0x0264
            if (r13 != 0) goto L_0x0264
            r1 = 0
            r0.mChainStyle = r1
            goto L_0x0264
        L_0x0258:
            r21 = r1
            r22 = r2
            r23 = r4
            r24 = r5
            r25 = r6
            r20 = r11
        L_0x0264:
            if (r4 <= r3) goto L_0x026a
            r2 = 2
            r0.mChainStyle = r2
            goto L_0x026b
        L_0x026a:
            r2 = 2
        L_0x026b:
            if (r7 <= 0) goto L_0x0273
            if (r5 != 0) goto L_0x0273
            if (r9 != r10) goto L_0x0273
            r0.mChainStyle = r2
        L_0x0273:
            int r1 = r0.mChainStyle
            r6 = 1
            if (r1 != r6) goto L_0x0329
            r1 = 0
            if (r7 <= r6) goto L_0x0282
            int r2 = r3 - r4
            int r11 = r7 + -1
            int r1 = r2 / r11
            goto L_0x028a
        L_0x0282:
            if (r7 != r6) goto L_0x028a
            int r2 = r3 - r4
            r18 = 2
            int r1 = r2 / 2
        L_0x028a:
            if (r5 <= 0) goto L_0x028d
            r1 = 0
        L_0x028d:
            r2 = 0
            r6 = r2
            r2 = r21
        L_0x0291:
            if (r6 >= r8) goto L_0x0324
            r11 = r6
            if (r22 == 0) goto L_0x029a
            int r13 = r6 + 1
            int r11 = r8 - r13
        L_0x029a:
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r13 = r0.mWidgets
            java.lang.Object r13 = r13.get(r11)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r13 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r13
            androidx.constraintlayout.core.widgets.ConstraintWidget r14 = r13.mWidget
            int r14 = r14.getVisibility()
            r15 = 8
            if (r14 != r15) goto L_0x02ba
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            r14.resolve(r2)
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.end
            r14.resolve(r2)
            r16 = r1
            goto L_0x031e
        L_0x02ba:
            if (r6 <= 0) goto L_0x02c1
            if (r22 == 0) goto L_0x02c0
            int r2 = r2 - r1
            goto L_0x02c1
        L_0x02c0:
            int r2 = r2 + r1
        L_0x02c1:
            if (r6 <= 0) goto L_0x02d2
            if (r6 < r9) goto L_0x02d2
            if (r22 == 0) goto L_0x02cd
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            int r14 = r14.mMargin
            int r2 = r2 - r14
            goto L_0x02d2
        L_0x02cd:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            int r14 = r14.mMargin
            int r2 = r2 + r14
        L_0x02d2:
            if (r22 == 0) goto L_0x02da
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.end
            r14.resolve(r2)
            goto L_0x02df
        L_0x02da:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            r14.resolve(r2)
        L_0x02df:
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r14 = r13.mDimension
            int r14 = r14.value
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r15 = r13.mDimensionBehavior
            r16 = r1
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r15 != r1) goto L_0x02f4
            int r1 = r13.matchConstraintsType
            r15 = 1
            if (r1 != r15) goto L_0x02f4
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r1 = r13.mDimension
            int r14 = r1.wrapValue
        L_0x02f4:
            if (r22 == 0) goto L_0x02f8
            int r2 = r2 - r14
            goto L_0x02f9
        L_0x02f8:
            int r2 = r2 + r14
        L_0x02f9:
            if (r22 == 0) goto L_0x0301
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.start
            r1.resolve(r2)
            goto L_0x0306
        L_0x0301:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.end
            r1.resolve(r2)
        L_0x0306:
            r15 = 1
            r13.mResolved = r15
            int r1 = r8 + -1
            if (r6 >= r1) goto L_0x031e
            if (r6 >= r10) goto L_0x031e
            if (r22 == 0) goto L_0x0318
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.end
            int r1 = r1.mMargin
            int r1 = -r1
            int r2 = r2 - r1
            goto L_0x031e
        L_0x0318:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.end
            int r1 = r1.mMargin
            int r1 = -r1
            int r2 = r2 + r1
        L_0x031e:
            int r6 = r6 + 1
            r1 = r16
            goto L_0x0291
        L_0x0324:
            r16 = r1
            r1 = r2
            goto L_0x0493
        L_0x0329:
            int r1 = r0.mChainStyle
            if (r1 != 0) goto L_0x03cf
            int r1 = r3 - r4
            int r2 = r7 + 1
            int r1 = r1 / r2
            if (r5 <= 0) goto L_0x0335
            r1 = 0
        L_0x0335:
            r2 = 0
            r6 = r2
            r2 = r21
        L_0x0339:
            if (r6 >= r8) goto L_0x03ca
            r11 = r6
            if (r22 == 0) goto L_0x0342
            int r13 = r6 + 1
            int r11 = r8 - r13
        L_0x0342:
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r13 = r0.mWidgets
            java.lang.Object r13 = r13.get(r11)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r13 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r13
            androidx.constraintlayout.core.widgets.ConstraintWidget r14 = r13.mWidget
            int r14 = r14.getVisibility()
            r15 = 8
            if (r14 != r15) goto L_0x0361
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            r14.resolve(r2)
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.end
            r14.resolve(r2)
            r16 = r1
            goto L_0x03c4
        L_0x0361:
            if (r22 == 0) goto L_0x0365
            int r2 = r2 - r1
            goto L_0x0366
        L_0x0365:
            int r2 = r2 + r1
        L_0x0366:
            if (r6 <= 0) goto L_0x0377
            if (r6 < r9) goto L_0x0377
            if (r22 == 0) goto L_0x0372
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            int r14 = r14.mMargin
            int r2 = r2 - r14
            goto L_0x0377
        L_0x0372:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            int r14 = r14.mMargin
            int r2 = r2 + r14
        L_0x0377:
            if (r22 == 0) goto L_0x037f
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.end
            r14.resolve(r2)
            goto L_0x0384
        L_0x037f:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            r14.resolve(r2)
        L_0x0384:
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r14 = r13.mDimension
            int r14 = r14.value
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r15 = r13.mDimensionBehavior
            r16 = r1
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r15 != r1) goto L_0x039d
            int r1 = r13.matchConstraintsType
            r15 = 1
            if (r1 != r15) goto L_0x039d
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r1 = r13.mDimension
            int r1 = r1.wrapValue
            int r14 = java.lang.Math.min(r14, r1)
        L_0x039d:
            if (r22 == 0) goto L_0x03a1
            int r2 = r2 - r14
            goto L_0x03a2
        L_0x03a1:
            int r2 = r2 + r14
        L_0x03a2:
            if (r22 == 0) goto L_0x03aa
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.start
            r1.resolve(r2)
            goto L_0x03af
        L_0x03aa:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.end
            r1.resolve(r2)
        L_0x03af:
            int r1 = r8 + -1
            if (r6 >= r1) goto L_0x03c4
            if (r6 >= r10) goto L_0x03c4
            if (r22 == 0) goto L_0x03be
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.end
            int r1 = r1.mMargin
            int r1 = -r1
            int r2 = r2 - r1
            goto L_0x03c4
        L_0x03be:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.end
            int r1 = r1.mMargin
            int r1 = -r1
            int r2 = r2 + r1
        L_0x03c4:
            int r6 = r6 + 1
            r1 = r16
            goto L_0x0339
        L_0x03ca:
            r16 = r1
            r1 = r2
            goto L_0x0493
        L_0x03cf:
            int r1 = r0.mChainStyle
            r2 = 2
            if (r1 != r2) goto L_0x0491
            int r1 = r0.orientation
            if (r1 != 0) goto L_0x03df
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r0.mWidget
            float r1 = r1.getHorizontalBiasPercent()
            goto L_0x03e5
        L_0x03df:
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r0.mWidget
            float r1 = r1.getVerticalBiasPercent()
        L_0x03e5:
            if (r22 == 0) goto L_0x03ec
            r2 = 1065353216(0x3f800000, float:1.0)
            float r1 = r2 - r1
        L_0x03ec:
            int r2 = r3 - r4
            float r2 = (float) r2
            float r2 = r2 * r1
            float r2 = r2 + r20
            int r2 = (int) r2
            if (r2 < 0) goto L_0x03f7
            if (r5 <= 0) goto L_0x03f8
        L_0x03f7:
            r2 = 0
        L_0x03f8:
            if (r22 == 0) goto L_0x03fd
            int r6 = r21 - r2
            goto L_0x03ff
        L_0x03fd:
            int r6 = r21 + r2
        L_0x03ff:
            r11 = 0
        L_0x0400:
            if (r11 >= r8) goto L_0x048d
            r13 = r11
            if (r22 == 0) goto L_0x0409
            int r14 = r11 + 1
            int r13 = r8 - r14
        L_0x0409:
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r14 = r0.mWidgets
            java.lang.Object r14 = r14.get(r13)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r14 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r14
            androidx.constraintlayout.core.widgets.ConstraintWidget r15 = r14.mWidget
            int r15 = r15.getVisibility()
            r0 = 8
            if (r15 != r0) goto L_0x0429
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r15 = r14.start
            r15.resolve(r6)
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r15 = r14.end
            r15.resolve(r6)
            r16 = r1
            r1 = 1
            goto L_0x0485
        L_0x0429:
            if (r11 <= 0) goto L_0x043a
            if (r11 < r9) goto L_0x043a
            if (r22 == 0) goto L_0x0435
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r15 = r14.start
            int r15 = r15.mMargin
            int r6 = r6 - r15
            goto L_0x043a
        L_0x0435:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r15 = r14.start
            int r15 = r15.mMargin
            int r6 = r6 + r15
        L_0x043a:
            if (r22 == 0) goto L_0x0442
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r15 = r14.end
            r15.resolve(r6)
            goto L_0x0447
        L_0x0442:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r15 = r14.start
            r15.resolve(r6)
        L_0x0447:
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r15 = r14.mDimension
            int r15 = r15.value
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r0 = r14.mDimensionBehavior
            r16 = r1
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r0 != r1) goto L_0x045d
            int r0 = r14.matchConstraintsType
            r1 = 1
            if (r0 != r1) goto L_0x045e
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r0 = r14.mDimension
            int r15 = r0.wrapValue
            goto L_0x045e
        L_0x045d:
            r1 = 1
        L_0x045e:
            if (r22 == 0) goto L_0x0462
            int r6 = r6 - r15
            goto L_0x0463
        L_0x0462:
            int r6 = r6 + r15
        L_0x0463:
            if (r22 == 0) goto L_0x046b
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r14.start
            r0.resolve(r6)
            goto L_0x0470
        L_0x046b:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r14.end
            r0.resolve(r6)
        L_0x0470:
            int r0 = r8 + -1
            if (r11 >= r0) goto L_0x0485
            if (r11 >= r10) goto L_0x0485
            if (r22 == 0) goto L_0x047f
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r14.end
            int r0 = r0.mMargin
            int r0 = -r0
            int r6 = r6 - r0
            goto L_0x0485
        L_0x047f:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r14.end
            int r0 = r0.mMargin
            int r0 = -r0
            int r6 = r6 + r0
        L_0x0485:
            int r11 = r11 + 1
            r0 = r27
            r1 = r16
            goto L_0x0400
        L_0x048d:
            r16 = r1
            r1 = r6
            goto L_0x0493
        L_0x0491:
            r1 = r21
        L_0x0493:
            return
        L_0x0494:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.analyzer.ChainRun.update(androidx.constraintlayout.core.widgets.analyzer.Dependency):void");
    }

    public void applyToWidget() {
        for (int i = 0; i < this.mWidgets.size(); i++) {
            this.mWidgets.get(i).applyToWidget();
        }
    }

    private ConstraintWidget getFirstVisibleWidget() {
        for (int i = 0; i < this.mWidgets.size(); i++) {
            WidgetRun run = this.mWidgets.get(i);
            if (run.mWidget.getVisibility() != 8) {
                return run.mWidget;
            }
        }
        return null;
    }

    private ConstraintWidget getLastVisibleWidget() {
        for (int i = this.mWidgets.size() - 1; i >= 0; i--) {
            WidgetRun run = this.mWidgets.get(i);
            if (run.mWidget.getVisibility() != 8) {
                return run.mWidget;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void apply() {
        Iterator<WidgetRun> it = this.mWidgets.iterator();
        while (it.hasNext()) {
            it.next().apply();
        }
        int count = this.mWidgets.size();
        if (count >= 1) {
            ConstraintWidget firstWidget = this.mWidgets.get(0).mWidget;
            ConstraintWidget lastWidget = this.mWidgets.get(count - 1).mWidget;
            if (this.orientation == 0) {
                ConstraintAnchor startAnchor = firstWidget.mLeft;
                ConstraintAnchor endAnchor = lastWidget.mRight;
                DependencyNode startTarget = getTarget(startAnchor, 0);
                int startMargin = startAnchor.getMargin();
                ConstraintWidget firstVisibleWidget = getFirstVisibleWidget();
                if (firstVisibleWidget != null) {
                    startMargin = firstVisibleWidget.mLeft.getMargin();
                }
                if (startTarget != null) {
                    addTarget(this.start, startTarget, startMargin);
                }
                DependencyNode endTarget = getTarget(endAnchor, 0);
                int endMargin = endAnchor.getMargin();
                ConstraintWidget lastVisibleWidget = getLastVisibleWidget();
                if (lastVisibleWidget != null) {
                    endMargin = lastVisibleWidget.mRight.getMargin();
                }
                if (endTarget != null) {
                    addTarget(this.end, endTarget, -endMargin);
                }
            } else {
                ConstraintAnchor startAnchor2 = firstWidget.mTop;
                ConstraintAnchor endAnchor2 = lastWidget.mBottom;
                DependencyNode startTarget2 = getTarget(startAnchor2, 1);
                int startMargin2 = startAnchor2.getMargin();
                ConstraintWidget firstVisibleWidget2 = getFirstVisibleWidget();
                if (firstVisibleWidget2 != null) {
                    startMargin2 = firstVisibleWidget2.mTop.getMargin();
                }
                if (startTarget2 != null) {
                    addTarget(this.start, startTarget2, startMargin2);
                }
                DependencyNode endTarget2 = getTarget(endAnchor2, 1);
                int endMargin2 = endAnchor2.getMargin();
                ConstraintWidget lastVisibleWidget2 = getLastVisibleWidget();
                if (lastVisibleWidget2 != null) {
                    endMargin2 = lastVisibleWidget2.mBottom.getMargin();
                }
                if (endTarget2 != null) {
                    addTarget(this.end, endTarget2, -endMargin2);
                }
            }
            this.start.updateDelegate = this;
            this.end.updateDelegate = this;
        }
    }
}
