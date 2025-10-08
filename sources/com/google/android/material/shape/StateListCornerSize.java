package com.google.android.material.shape;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.StateSet;
import com.google.android.material.R;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class StateListCornerSize {
    private static final int INITIAL_CAPACITY = 10;
    CornerSize[] cornerSizes = new CornerSize[10];
    private CornerSize defaultCornerSize;
    int stateCount;
    int[][] stateSpecs = new int[10][];

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0048 A[Catch:{ all -> 0x006a, all -> 0x0071, NotFoundException | IOException | XmlPullParserException -> 0x0076 }] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0062 A[SYNTHETIC, Splitter:B:25:0x0062] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.material.shape.StateListCornerSize create(android.content.Context r8, android.content.res.TypedArray r9, int r10, com.google.android.material.shape.CornerSize r11) {
        /*
            r0 = 0
            int r0 = r9.getResourceId(r10, r0)
            if (r0 != 0) goto L_0x0010
            com.google.android.material.shape.CornerSize r1 = com.google.android.material.shape.ShapeAppearanceModel.getCornerSize(r9, r10, r11)
            com.google.android.material.shape.StateListCornerSize r1 = create(r1)
            return r1
        L_0x0010:
            android.content.res.Resources r1 = r8.getResources()
            java.lang.String r1 = r1.getResourceTypeName(r0)
            java.lang.String r2 = "xml"
            boolean r2 = r1.equals(r2)
            if (r2 != 0) goto L_0x0029
            com.google.android.material.shape.CornerSize r2 = com.google.android.material.shape.ShapeAppearanceModel.getCornerSize(r9, r10, r11)
            com.google.android.material.shape.StateListCornerSize r2 = create(r2)
            return r2
        L_0x0029:
            android.content.res.Resources r2 = r8.getResources()     // Catch:{ XmlPullParserException -> 0x007a, IOException -> 0x0078, NotFoundException -> 0x0076 }
            android.content.res.XmlResourceParser r2 = r2.getXml(r0)     // Catch:{ XmlPullParserException -> 0x007a, IOException -> 0x0078, NotFoundException -> 0x0076 }
            com.google.android.material.shape.StateListCornerSize r3 = new com.google.android.material.shape.StateListCornerSize     // Catch:{ all -> 0x006a }
            r3.<init>()     // Catch:{ all -> 0x006a }
            android.util.AttributeSet r4 = android.util.Xml.asAttributeSet(r2)     // Catch:{ all -> 0x006a }
        L_0x003a:
            int r5 = r2.next()     // Catch:{ all -> 0x006a }
            r6 = r5
            r7 = 2
            if (r5 == r7) goto L_0x0046
            r5 = 1
            if (r6 == r5) goto L_0x0046
            goto L_0x003a
        L_0x0046:
            if (r6 != r7) goto L_0x0062
            java.lang.String r5 = r2.getName()     // Catch:{ all -> 0x006a }
            java.lang.String r7 = "selector"
            boolean r7 = r5.equals(r7)     // Catch:{ all -> 0x006a }
            if (r7 == 0) goto L_0x005b
            android.content.res.Resources$Theme r7 = r8.getTheme()     // Catch:{ all -> 0x006a }
            r3.loadCornerSizesFromItems(r8, r2, r4, r7)     // Catch:{ all -> 0x006a }
        L_0x005b:
            if (r2 == 0) goto L_0x0061
            r2.close()     // Catch:{ XmlPullParserException -> 0x007a, IOException -> 0x0078, NotFoundException -> 0x0076 }
        L_0x0061:
            return r3
        L_0x0062:
            org.xmlpull.v1.XmlPullParserException r5 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ all -> 0x006a }
            java.lang.String r7 = "No start tag found"
            r5.<init>(r7)     // Catch:{ all -> 0x006a }
            throw r5     // Catch:{ all -> 0x006a }
        L_0x006a:
            r3 = move-exception
            if (r2 == 0) goto L_0x0075
            r2.close()     // Catch:{ all -> 0x0071 }
            goto L_0x0075
        L_0x0071:
            r4 = move-exception
            r3.addSuppressed(r4)     // Catch:{ XmlPullParserException -> 0x007a, IOException -> 0x0078, NotFoundException -> 0x0076 }
        L_0x0075:
            throw r3     // Catch:{ XmlPullParserException -> 0x007a, IOException -> 0x0078, NotFoundException -> 0x0076 }
        L_0x0076:
            r2 = move-exception
            goto L_0x007b
        L_0x0078:
            r2 = move-exception
            goto L_0x007b
        L_0x007a:
            r2 = move-exception
        L_0x007b:
            com.google.android.material.shape.StateListCornerSize r3 = create(r11)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.shape.StateListCornerSize.create(android.content.Context, android.content.res.TypedArray, int, com.google.android.material.shape.CornerSize):com.google.android.material.shape.StateListCornerSize");
    }

    public static StateListCornerSize create(CornerSize cornerSize) {
        StateListCornerSize stateListCornerSize = new StateListCornerSize();
        stateListCornerSize.addStateCornerSize(StateSet.WILD_CARD, cornerSize);
        return stateListCornerSize;
    }

    public StateListCornerSize withTransformedCornerSizes(ShapeAppearanceModel.CornerSizeUnaryOperator op) {
        StateListCornerSize newStateListCornerSize = new StateListCornerSize();
        newStateListCornerSize.stateCount = this.stateCount;
        newStateListCornerSize.stateSpecs = new int[this.stateSpecs.length][];
        System.arraycopy(this.stateSpecs, 0, newStateListCornerSize.stateSpecs, 0, this.stateSpecs.length);
        newStateListCornerSize.cornerSizes = new CornerSize[this.cornerSizes.length];
        for (int i = 0; i < this.stateCount; i++) {
            newStateListCornerSize.cornerSizes[i] = op.apply(this.cornerSizes[i]);
        }
        return newStateListCornerSize;
    }

    public boolean isStateful() {
        return this.stateCount > 1;
    }

    public CornerSize getDefaultCornerSize() {
        return this.defaultCornerSize;
    }

    public CornerSize getCornerSizeForState(int[] stateSet) {
        int idx = indexOfStateSet(stateSet);
        if (idx < 0) {
            idx = indexOfStateSet(StateSet.WILD_CARD);
        }
        return idx < 0 ? this.defaultCornerSize : this.cornerSizes[idx];
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

    private void loadCornerSizesFromItems(Context context, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
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
                    return;
                } else if (type != 2 || depth2 > innerDepth) {
                    i = 1;
                } else if (parser.getName().equals("item")) {
                    Resources res = context.getResources();
                    if (theme2 == null) {
                        a = res.obtainAttributes(attributeSet, R.styleable.ShapeAppearance);
                    } else {
                        a = theme2.obtainStyledAttributes(attributeSet, R.styleable.ShapeAppearance, 0, 0);
                    }
                    CornerSize cornerSize = ShapeAppearanceModel.getCornerSize(a, R.styleable.ShapeAppearance_cornerSize, new AbsoluteCornerSize(0.0f));
                    a.recycle();
                    int j = 0;
                    int numAttrs = attributeSet.getAttributeCount();
                    int[] stateSpec = new int[numAttrs];
                    for (int i2 = 0; i2 < numAttrs; i2++) {
                        int stateResId = attributeSet.getAttributeNameResource(i2);
                        if (stateResId != R.attr.cornerSize) {
                            int j2 = j + 1;
                            stateSpec[j] = attributeSet.getAttributeBooleanValue(i2, false) ? stateResId : -stateResId;
                            j = j2;
                        }
                    }
                    addStateCornerSize(StateSet.trimStateSet(stateSpec, j), cornerSize);
                    i = 1;
                }
            } else {
                return;
            }
        }
    }

    private void addStateCornerSize(int[] stateSpec, CornerSize cornerSize) {
        if (this.stateCount == 0 || stateSpec.length == 0) {
            this.defaultCornerSize = cornerSize;
        }
        if (this.stateCount >= this.stateSpecs.length) {
            growArray(this.stateCount, this.stateCount + 10);
        }
        this.stateSpecs[this.stateCount] = stateSpec;
        this.cornerSizes[this.stateCount] = cornerSize;
        this.stateCount++;
    }

    private void growArray(int oldSize, int newSize) {
        int[][] newStateSets = new int[newSize][];
        System.arraycopy(this.stateSpecs, 0, newStateSets, 0, oldSize);
        this.stateSpecs = newStateSets;
        CornerSize[] newCornerSizes = new CornerSize[newSize];
        System.arraycopy(this.cornerSizes, 0, newCornerSizes, 0, oldSize);
        this.cornerSizes = newCornerSizes;
    }
}
