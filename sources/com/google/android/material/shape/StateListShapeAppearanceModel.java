package com.google.android.material.shape;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.StateSet;
import com.google.android.material.R;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.io.IOException;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class StateListShapeAppearanceModel {
    public static final int CORNER_BOTTOM_LEFT = 4;
    public static final int CORNER_BOTTOM_RIGHT = 8;
    public static final int CORNER_TOP_LEFT = 1;
    public static final int CORNER_TOP_RIGHT = 2;
    private static final int INITIAL_CAPACITY = 10;
    final StateListCornerSize bottomLeftCornerSizeOverride;
    final StateListCornerSize bottomRightCornerSizeOverride;
    final ShapeAppearanceModel defaultShape;
    final ShapeAppearanceModel[] shapeAppearanceModels;
    final int stateCount;
    final int[][] stateSpecs;
    final StateListCornerSize topLeftCornerSizeOverride;
    final StateListCornerSize topRightCornerSizeOverride;

    public static final class Builder {
        /* access modifiers changed from: private */
        public StateListCornerSize bottomLeftCornerSizeOverride;
        /* access modifiers changed from: private */
        public StateListCornerSize bottomRightCornerSizeOverride;
        /* access modifiers changed from: private */
        public ShapeAppearanceModel defaultShape;
        /* access modifiers changed from: private */
        public ShapeAppearanceModel[] shapeAppearanceModels;
        /* access modifiers changed from: private */
        public int stateCount;
        /* access modifiers changed from: private */
        public int[][] stateSpecs;
        /* access modifiers changed from: private */
        public StateListCornerSize topLeftCornerSizeOverride;
        /* access modifiers changed from: private */
        public StateListCornerSize topRightCornerSizeOverride;

        public Builder(StateListShapeAppearanceModel other) {
            this.stateCount = other.stateCount;
            this.defaultShape = other.defaultShape;
            this.stateSpecs = new int[other.stateSpecs.length][];
            this.shapeAppearanceModels = new ShapeAppearanceModel[other.shapeAppearanceModels.length];
            System.arraycopy(other.stateSpecs, 0, this.stateSpecs, 0, this.stateCount);
            System.arraycopy(other.shapeAppearanceModels, 0, this.shapeAppearanceModels, 0, this.stateCount);
            this.topLeftCornerSizeOverride = other.topLeftCornerSizeOverride;
            this.topRightCornerSizeOverride = other.topRightCornerSizeOverride;
            this.bottomLeftCornerSizeOverride = other.bottomLeftCornerSizeOverride;
            this.bottomRightCornerSizeOverride = other.bottomRightCornerSizeOverride;
        }

        public Builder(ShapeAppearanceModel shapeAppearanceModel) {
            initialize();
            addStateShapeAppearanceModel(StateSet.WILD_CARD, shapeAppearanceModel);
        }

        /* JADX WARNING: Removed duplicated region for block: B:11:0x0020 A[Catch:{ all -> 0x0041, all -> 0x0048, NotFoundException | IOException | XmlPullParserException -> 0x004d }] */
        /* JADX WARNING: Removed duplicated region for block: B:17:0x0039 A[SYNTHETIC, Splitter:B:17:0x0039] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private Builder(android.content.Context r6, int r7) {
            /*
                r5 = this;
                r5.<init>()
                r5.initialize()
                android.content.res.Resources r0 = r6.getResources()     // Catch:{ XmlPullParserException -> 0x0051, IOException -> 0x004f, NotFoundException -> 0x004d }
                android.content.res.XmlResourceParser r0 = r0.getXml(r7)     // Catch:{ XmlPullParserException -> 0x0051, IOException -> 0x004f, NotFoundException -> 0x004d }
                android.util.AttributeSet r1 = android.util.Xml.asAttributeSet(r0)     // Catch:{ all -> 0x0041 }
            L_0x0012:
                int r2 = r0.next()     // Catch:{ all -> 0x0041 }
                r3 = r2
                r4 = 2
                if (r2 == r4) goto L_0x001e
                r2 = 1
                if (r3 == r2) goto L_0x001e
                goto L_0x0012
            L_0x001e:
                if (r3 != r4) goto L_0x0039
                java.lang.String r2 = r0.getName()     // Catch:{ all -> 0x0041 }
                java.lang.String r4 = "selector"
                boolean r4 = r2.equals(r4)     // Catch:{ all -> 0x0041 }
                if (r4 == 0) goto L_0x0033
                android.content.res.Resources$Theme r4 = r6.getTheme()     // Catch:{ all -> 0x0041 }
                com.google.android.material.shape.StateListShapeAppearanceModel.loadShapeAppearanceModelsFromItems(r5, r6, r0, r1, r4)     // Catch:{ all -> 0x0041 }
            L_0x0033:
                if (r0 == 0) goto L_0x0038
                r0.close()     // Catch:{ XmlPullParserException -> 0x0051, IOException -> 0x004f, NotFoundException -> 0x004d }
            L_0x0038:
                goto L_0x0055
            L_0x0039:
                org.xmlpull.v1.XmlPullParserException r2 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ all -> 0x0041 }
                java.lang.String r4 = "No start tag found"
                r2.<init>(r4)     // Catch:{ all -> 0x0041 }
                throw r2     // Catch:{ all -> 0x0041 }
            L_0x0041:
                r1 = move-exception
                if (r0 == 0) goto L_0x004c
                r0.close()     // Catch:{ all -> 0x0048 }
                goto L_0x004c
            L_0x0048:
                r2 = move-exception
                r1.addSuppressed(r2)     // Catch:{ XmlPullParserException -> 0x0051, IOException -> 0x004f, NotFoundException -> 0x004d }
            L_0x004c:
                throw r1     // Catch:{ XmlPullParserException -> 0x0051, IOException -> 0x004f, NotFoundException -> 0x004d }
            L_0x004d:
                r0 = move-exception
                goto L_0x0052
            L_0x004f:
                r0 = move-exception
                goto L_0x0052
            L_0x0051:
                r0 = move-exception
            L_0x0052:
                r5.initialize()
            L_0x0055:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.shape.StateListShapeAppearanceModel.Builder.<init>(android.content.Context, int):void");
        }

        private void initialize() {
            this.defaultShape = new ShapeAppearanceModel();
            this.stateSpecs = new int[10][];
            this.shapeAppearanceModels = new ShapeAppearanceModel[10];
        }

        public Builder setCornerSizeOverride(StateListCornerSize cornerSizeOverride, int cornerPositionSet) {
            if (containsFlag(cornerPositionSet, 1)) {
                this.topLeftCornerSizeOverride = cornerSizeOverride;
            }
            if (containsFlag(cornerPositionSet, 2)) {
                this.topRightCornerSizeOverride = cornerSizeOverride;
            }
            if (containsFlag(cornerPositionSet, 4)) {
                this.bottomLeftCornerSizeOverride = cornerSizeOverride;
            }
            if (containsFlag(cornerPositionSet, 8)) {
                this.bottomRightCornerSizeOverride = cornerSizeOverride;
            }
            return this;
        }

        private boolean containsFlag(int flagSet, int flag) {
            return (flagSet | flag) == flagSet;
        }

        public Builder addStateShapeAppearanceModel(int[] stateSpec, ShapeAppearanceModel shapeAppearanceModel) {
            if (this.stateCount == 0 || stateSpec.length == 0) {
                this.defaultShape = shapeAppearanceModel;
            }
            if (this.stateCount >= this.stateSpecs.length) {
                growArray(this.stateCount, this.stateCount + 10);
            }
            this.stateSpecs[this.stateCount] = stateSpec;
            this.shapeAppearanceModels[this.stateCount] = shapeAppearanceModel;
            this.stateCount++;
            return this;
        }

        public Builder withTransformedCornerSizes(ShapeAppearanceModel.CornerSizeUnaryOperator op) {
            ShapeAppearanceModel[] newShapeAppearanceModels = new ShapeAppearanceModel[this.shapeAppearanceModels.length];
            for (int i = 0; i < this.stateCount; i++) {
                newShapeAppearanceModels[i] = this.shapeAppearanceModels[i].withTransformedCornerSizes(op);
            }
            this.shapeAppearanceModels = newShapeAppearanceModels;
            if (this.topLeftCornerSizeOverride != null) {
                this.topLeftCornerSizeOverride = this.topLeftCornerSizeOverride.withTransformedCornerSizes(op);
            }
            if (this.topRightCornerSizeOverride != null) {
                this.topRightCornerSizeOverride = this.topRightCornerSizeOverride.withTransformedCornerSizes(op);
            }
            if (this.bottomLeftCornerSizeOverride != null) {
                this.bottomLeftCornerSizeOverride = this.bottomLeftCornerSizeOverride.withTransformedCornerSizes(op);
            }
            if (this.bottomRightCornerSizeOverride != null) {
                this.bottomRightCornerSizeOverride = this.bottomRightCornerSizeOverride.withTransformedCornerSizes(op);
            }
            return this;
        }

        private void growArray(int oldSize, int newSize) {
            int[][] newStateSpecs = new int[newSize][];
            System.arraycopy(this.stateSpecs, 0, newStateSpecs, 0, oldSize);
            this.stateSpecs = newStateSpecs;
            ShapeAppearanceModel[] newShapeAppearanceModels = new ShapeAppearanceModel[newSize];
            System.arraycopy(this.shapeAppearanceModels, 0, newShapeAppearanceModels, 0, oldSize);
            this.shapeAppearanceModels = newShapeAppearanceModels;
        }

        public StateListShapeAppearanceModel build() {
            if (this.stateCount == 0) {
                return null;
            }
            return new StateListShapeAppearanceModel(this);
        }
    }

    public static StateListShapeAppearanceModel create(Context context, TypedArray attributes, int index) {
        int resourceId = attributes.getResourceId(index, 0);
        if (resourceId != 0 && Objects.equals(context.getResources().getResourceTypeName(resourceId), "xml")) {
            return new Builder(context, resourceId).build();
        }
        return null;
    }

    /* access modifiers changed from: private */
    public static void loadShapeAppearanceModelsFromItems(Builder builder, Context context, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray a;
        AttributeSet attributeSet = attrs;
        Resources.Theme theme2 = theme;
        int i = 1;
        int innerDepth = parser.getDepth() + 1;
        while (true) {
            int next = parser.next();
            int type = next;
            if (next != i) {
                int depth = parser.getDepth();
                int depth2 = depth;
                if (depth < innerDepth && type == 3) {
                    Builder builder2 = builder;
                    Context context2 = context;
                    return;
                } else if (type != 2 || depth2 > innerDepth) {
                    Builder builder3 = builder;
                    Context context3 = context;
                    theme2 = theme;
                    i = 1;
                } else if (parser.getName().equals("item")) {
                    Resources res = context.getResources();
                    if (theme2 == null) {
                        a = res.obtainAttributes(attributeSet, R.styleable.MaterialShape);
                    } else {
                        a = theme2.obtainStyledAttributes(attributeSet, R.styleable.MaterialShape, 0, 0);
                    }
                    ShapeAppearanceModel shapeAppearanceModel = ShapeAppearanceModel.builder(context, a.getResourceId(R.styleable.MaterialShape_shapeAppearance, 0), a.getResourceId(R.styleable.MaterialShape_shapeAppearanceOverlay, 0)).build();
                    a.recycle();
                    int j = 0;
                    int numAttrs = attributeSet.getAttributeCount();
                    int[] stateSpec = new int[numAttrs];
                    int i2 = 0;
                    while (i2 < numAttrs) {
                        int stateResId = attributeSet.getAttributeNameResource(i2);
                        if (!(stateResId == R.attr.shapeAppearance || stateResId == R.attr.shapeAppearanceOverlay)) {
                            int j2 = j + 1;
                            stateSpec[j] = attributeSet.getAttributeBooleanValue(i2, false) ? stateResId : -stateResId;
                            j = j2;
                        }
                        i2++;
                        Resources.Theme theme3 = theme;
                    }
                    builder.addStateShapeAppearanceModel(StateSet.trimStateSet(stateSpec, j), shapeAppearanceModel);
                    theme2 = theme;
                    i = 1;
                }
            } else {
                Builder builder4 = builder;
                Context context4 = context;
                return;
            }
        }
    }

    private StateListShapeAppearanceModel(Builder builder) {
        this.stateCount = builder.stateCount;
        this.defaultShape = builder.defaultShape;
        this.stateSpecs = builder.stateSpecs;
        this.shapeAppearanceModels = builder.shapeAppearanceModels;
        this.topLeftCornerSizeOverride = builder.topLeftCornerSizeOverride;
        this.topRightCornerSizeOverride = builder.topRightCornerSizeOverride;
        this.bottomLeftCornerSizeOverride = builder.bottomLeftCornerSizeOverride;
        this.bottomRightCornerSizeOverride = builder.bottomRightCornerSizeOverride;
    }

    public int getStateCount() {
        return this.stateCount;
    }

    public ShapeAppearanceModel getDefaultShape(boolean withCornerSizeOverrides) {
        if (!withCornerSizeOverrides || (this.topLeftCornerSizeOverride == null && this.topRightCornerSizeOverride == null && this.bottomLeftCornerSizeOverride == null && this.bottomRightCornerSizeOverride == null)) {
            return this.defaultShape;
        }
        ShapeAppearanceModel.Builder builder = this.defaultShape.toBuilder();
        if (this.topLeftCornerSizeOverride != null) {
            builder.setTopLeftCornerSize(this.topLeftCornerSizeOverride.getDefaultCornerSize());
        }
        if (this.topRightCornerSizeOverride != null) {
            builder.setTopRightCornerSize(this.topRightCornerSizeOverride.getDefaultCornerSize());
        }
        if (this.bottomLeftCornerSizeOverride != null) {
            builder.setBottomLeftCornerSize(this.bottomLeftCornerSizeOverride.getDefaultCornerSize());
        }
        if (this.bottomRightCornerSizeOverride != null) {
            builder.setBottomRightCornerSize(this.bottomRightCornerSizeOverride.getDefaultCornerSize());
        }
        return builder.build();
    }

    /* access modifiers changed from: protected */
    public ShapeAppearanceModel getShapeForState(int[] stateSet) {
        int idx = indexOfStateSet(stateSet);
        if (idx < 0) {
            idx = indexOfStateSet(StateSet.WILD_CARD);
        }
        if (this.topLeftCornerSizeOverride == null && this.topRightCornerSizeOverride == null && this.bottomLeftCornerSizeOverride == null && this.bottomRightCornerSizeOverride == null) {
            return this.shapeAppearanceModels[idx];
        }
        ShapeAppearanceModel.Builder builder = this.shapeAppearanceModels[idx].toBuilder();
        if (this.topLeftCornerSizeOverride != null) {
            builder.setTopLeftCornerSize(this.topLeftCornerSizeOverride.getCornerSizeForState(stateSet));
        }
        if (this.topRightCornerSizeOverride != null) {
            builder.setTopRightCornerSize(this.topRightCornerSizeOverride.getCornerSizeForState(stateSet));
        }
        if (this.bottomLeftCornerSizeOverride != null) {
            builder.setBottomLeftCornerSize(this.bottomLeftCornerSizeOverride.getCornerSizeForState(stateSet));
        }
        if (this.bottomRightCornerSizeOverride != null) {
            builder.setBottomRightCornerSize(this.bottomRightCornerSizeOverride.getCornerSizeForState(stateSet));
        }
        return builder.build();
    }

    private int indexOfStateSet(int[] stateSet) {
        int[][] stateSpecs2 = this.stateSpecs;
        for (int i = 0; i < this.stateCount; i++) {
            if (StateSet.stateSetMatches(stateSpecs2[i], stateSet)) {
                return i;
            }
        }
        return -1;
    }

    public StateListShapeAppearanceModel withTransformedCornerSizes(ShapeAppearanceModel.CornerSizeUnaryOperator op) {
        return toBuilder().withTransformedCornerSizes(op).build();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public boolean isStateful() {
        if (this.stateCount > 1) {
            return true;
        }
        if (this.topLeftCornerSizeOverride != null && this.topLeftCornerSizeOverride.isStateful()) {
            return true;
        }
        if (this.topRightCornerSizeOverride != null && this.topRightCornerSizeOverride.isStateful()) {
            return true;
        }
        if (this.bottomLeftCornerSizeOverride != null && this.bottomLeftCornerSizeOverride.isStateful()) {
            return true;
        }
        if (this.bottomRightCornerSizeOverride == null || !this.bottomRightCornerSizeOverride.isStateful()) {
            return false;
        }
        return true;
    }

    public static int swapCornerPositionRtl(int flagSet) {
        return ((flagSet & 5) << 1) | ((flagSet & 10) >> 1);
    }
}
