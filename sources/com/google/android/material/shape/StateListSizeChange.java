package com.google.android.material.shape;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.TypedValue;
import com.google.android.material.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class StateListSizeChange {
    private static final int INITIAL_CAPACITY = 10;
    private SizeChange defaultSizeChange;
    SizeChange[] sizeChanges = new SizeChange[10];
    int stateCount;
    int[][] stateSpecs = new int[10][];

    public enum SizeChangeType {
        PERCENT,
        PIXELS
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0039 A[Catch:{ all -> 0x005b, all -> 0x0062, NotFoundException | IOException | XmlPullParserException -> 0x0067 }] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0053 A[SYNTHETIC, Splitter:B:23:0x0053] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.material.shape.StateListSizeChange create(android.content.Context r9, android.content.res.TypedArray r10, int r11) {
        /*
            r0 = 0
            int r0 = r10.getResourceId(r11, r0)
            r1 = 0
            if (r0 != 0) goto L_0x0009
            return r1
        L_0x0009:
            android.content.res.Resources r2 = r9.getResources()
            java.lang.String r2 = r2.getResourceTypeName(r0)
            java.lang.String r3 = "xml"
            boolean r3 = r2.equals(r3)
            if (r3 != 0) goto L_0x001a
            return r1
        L_0x001a:
            android.content.res.Resources r3 = r9.getResources()     // Catch:{ XmlPullParserException -> 0x006b, IOException -> 0x0069, NotFoundException -> 0x0067 }
            android.content.res.XmlResourceParser r3 = r3.getXml(r0)     // Catch:{ XmlPullParserException -> 0x006b, IOException -> 0x0069, NotFoundException -> 0x0067 }
            com.google.android.material.shape.StateListSizeChange r4 = new com.google.android.material.shape.StateListSizeChange     // Catch:{ all -> 0x005b }
            r4.<init>()     // Catch:{ all -> 0x005b }
            android.util.AttributeSet r5 = android.util.Xml.asAttributeSet(r3)     // Catch:{ all -> 0x005b }
        L_0x002b:
            int r6 = r3.next()     // Catch:{ all -> 0x005b }
            r7 = r6
            r8 = 2
            if (r6 == r8) goto L_0x0037
            r6 = 1
            if (r7 == r6) goto L_0x0037
            goto L_0x002b
        L_0x0037:
            if (r7 != r8) goto L_0x0053
            java.lang.String r6 = r3.getName()     // Catch:{ all -> 0x005b }
            java.lang.String r8 = "selector"
            boolean r8 = r6.equals(r8)     // Catch:{ all -> 0x005b }
            if (r8 == 0) goto L_0x004c
            android.content.res.Resources$Theme r8 = r9.getTheme()     // Catch:{ all -> 0x005b }
            r4.loadSizeChangeFromItems(r9, r3, r5, r8)     // Catch:{ all -> 0x005b }
        L_0x004c:
            if (r3 == 0) goto L_0x0052
            r3.close()     // Catch:{ XmlPullParserException -> 0x006b, IOException -> 0x0069, NotFoundException -> 0x0067 }
        L_0x0052:
            return r4
        L_0x0053:
            org.xmlpull.v1.XmlPullParserException r6 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ all -> 0x005b }
            java.lang.String r8 = "No start tag found"
            r6.<init>(r8)     // Catch:{ all -> 0x005b }
            throw r6     // Catch:{ all -> 0x005b }
        L_0x005b:
            r4 = move-exception
            if (r3 == 0) goto L_0x0066
            r3.close()     // Catch:{ all -> 0x0062 }
            goto L_0x0066
        L_0x0062:
            r5 = move-exception
            r4.addSuppressed(r5)     // Catch:{ XmlPullParserException -> 0x006b, IOException -> 0x0069, NotFoundException -> 0x0067 }
        L_0x0066:
            throw r4     // Catch:{ XmlPullParserException -> 0x006b, IOException -> 0x0069, NotFoundException -> 0x0067 }
        L_0x0067:
            r3 = move-exception
            goto L_0x006c
        L_0x0069:
            r3 = move-exception
            goto L_0x006c
        L_0x006b:
            r3 = move-exception
        L_0x006c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.shape.StateListSizeChange.create(android.content.Context, android.content.res.TypedArray, int):com.google.android.material.shape.StateListSizeChange");
    }

    public boolean isStateful() {
        return this.stateCount > 1;
    }

    public SizeChange getDefaultSizeChange() {
        return this.defaultSizeChange;
    }

    public SizeChange getSizeChangeForState(int[] stateSet) {
        int idx = indexOfStateSet(stateSet);
        if (idx < 0) {
            idx = indexOfStateSet(StateSet.WILD_CARD);
        }
        return idx < 0 ? this.defaultSizeChange : this.sizeChanges[idx];
    }

    public int getMaxWidthChange(int baseWidth) {
        int maxWidthChange = -baseWidth;
        for (int i = 0; i < this.stateCount; i++) {
            SizeChange sizeChange = this.sizeChanges[i];
            if (sizeChange.widthChange.type == SizeChangeType.PIXELS) {
                maxWidthChange = (int) Math.max((float) maxWidthChange, sizeChange.widthChange.amount);
            } else if (sizeChange.widthChange.type == SizeChangeType.PERCENT) {
                maxWidthChange = (int) Math.max((float) maxWidthChange, ((float) baseWidth) * sizeChange.widthChange.amount);
            }
        }
        return maxWidthChange;
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

    private void loadSizeChangeFromItems(Context context, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme) throws XmlPullParserException, IOException {
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
                }
                if (type != 2 || depth2 > innerDepth) {
                    i = 1;
                } else if (parser.getName().equals("item")) {
                    Resources res = context.getResources();
                    boolean z = false;
                    if (theme2 == null) {
                        a = res.obtainAttributes(attributeSet, R.styleable.StateListSizeChange);
                    } else {
                        a = theme2.obtainStyledAttributes(attributeSet, R.styleable.StateListSizeChange, 0, 0);
                    }
                    SizeChangeAmount widthChangeAmount = getSizeChangeAmount(a, R.styleable.StateListSizeChange_widthChange, (SizeChangeAmount) null);
                    a.recycle();
                    int j = 0;
                    int numAttrs = attributeSet.getAttributeCount();
                    int[] stateSpec = new int[numAttrs];
                    int i2 = 0;
                    while (i2 < numAttrs) {
                        int stateResId = attributeSet.getAttributeNameResource(i2);
                        if (stateResId != R.attr.widthChange) {
                            int j2 = j + 1;
                            stateSpec[j] = attributeSet.getAttributeBooleanValue(i2, z) ? stateResId : -stateResId;
                            j = j2;
                        }
                        i2++;
                        z = false;
                    }
                    addStateSizeChange(StateSet.trimStateSet(stateSpec, j), new SizeChange(widthChangeAmount));
                    i = 1;
                }
            } else {
                return;
            }
        }
    }

    private SizeChangeAmount getSizeChangeAmount(TypedArray a, int index, SizeChangeAmount defaultValue) {
        TypedValue value = a.peekValue(index);
        if (value == null) {
            return defaultValue;
        }
        if (value.type == 5) {
            return new SizeChangeAmount(SizeChangeType.PIXELS, (float) TypedValue.complexToDimensionPixelSize(value.data, a.getResources().getDisplayMetrics()));
        }
        if (value.type == 6) {
            return new SizeChangeAmount(SizeChangeType.PERCENT, value.getFraction(1.0f, 1.0f));
        }
        return defaultValue;
    }

    private void addStateSizeChange(int[] stateSpec, SizeChange sizeChange) {
        if (this.stateCount == 0 || stateSpec.length == 0) {
            this.defaultSizeChange = sizeChange;
        }
        if (this.stateCount >= this.stateSpecs.length) {
            growArray(this.stateCount, this.stateCount + 10);
        }
        this.stateSpecs[this.stateCount] = stateSpec;
        this.sizeChanges[this.stateCount] = sizeChange;
        this.stateCount++;
    }

    private void growArray(int oldSize, int newSize) {
        int[][] newStateSets = new int[newSize][];
        System.arraycopy(this.stateSpecs, 0, newStateSets, 0, oldSize);
        this.stateSpecs = newStateSets;
        SizeChange[] newSizeChanges = new SizeChange[newSize];
        System.arraycopy(this.sizeChanges, 0, newSizeChanges, 0, oldSize);
        this.sizeChanges = newSizeChanges;
    }

    public static class SizeChange {
        public SizeChangeAmount widthChange;

        SizeChange(SizeChangeAmount widthChange2) {
            this.widthChange = widthChange2;
        }

        SizeChange(SizeChange other) {
            this.widthChange = new SizeChangeAmount(other.widthChange.type, other.widthChange.amount);
        }
    }

    public static class SizeChangeAmount {
        float amount;
        SizeChangeType type;

        SizeChangeAmount(SizeChangeType type2, float amount2) {
            this.type = type2;
            this.amount = amount2;
        }

        public int getChange(int baseSize) {
            if (this.type == SizeChangeType.PERCENT) {
                return (int) (this.amount * ((float) baseSize));
            }
            if (this.type == SizeChangeType.PIXELS) {
                return (int) this.amount;
            }
            return 0;
        }
    }
}
