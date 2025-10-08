package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;
import androidx.graphics.shapes.RoundedPolygon;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\u001aH\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005H\u0002\u001a0\u0010\f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005H\u0002\u001a4\u0010\u000e\u001a\u00020\u000f*\u00020\u00102\b\b\u0003\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u0005H\u0007\u001a>\u0010\u0012\u001a\u00020\u000f*\u00020\u00102\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0013\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u0005H\u0007\u001a\u0001\u0010\u0014\u001a\u00020\u000f*\u00020\u00102\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0003\u0010\u0015\u001a\u00020\u00052\b\b\u0002\u0010\u0016\u001a\u00020\u00172\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00172\u0010\b\u0002\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u001a2\b\b\u0003\u0010\b\u001a\u00020\u00052\b\b\u0003\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u0005H\u0007\u001aN\u0010\u001b\u001a\u00020\u000f*\u00020\u00102\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0016\u001a\u00020\u00172\u0010\b\u0002\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u001a2\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u001ad\u0010\u001c\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\u0016\u001a\u00020\u00172\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00172\u0010\b\u0002\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u001a2\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u0005H\u0007¨\u0006\u001d"}, d2 = {"pillStarVerticesFromNumVerts", "", "numVerticesPerRadius", "", "width", "", "height", "innerRadius", "vertexSpacing", "startLocation", "centerX", "centerY", "starVerticesFromNumVerts", "radius", "circle", "Landroidx/graphics/shapes/RoundedPolygon;", "Landroidx/graphics/shapes/RoundedPolygon$Companion;", "numVertices", "pill", "smoothing", "pillStar", "innerRadiusRatio", "rounding", "Landroidx/graphics/shapes/CornerRounding;", "innerRounding", "perVertexRounding", "", "rectangle", "star", "graphics-shapes_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* compiled from: Shapes.kt */
public final class ShapesKt {
    public static final RoundedPolygon circle(RoundedPolygon.Companion companion) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return circle$default(companion, 0, 0.0f, 0.0f, 0.0f, 15, (Object) null);
    }

    public static final RoundedPolygon circle(RoundedPolygon.Companion companion, int i) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return circle$default(companion, i, 0.0f, 0.0f, 0.0f, 14, (Object) null);
    }

    public static final RoundedPolygon circle(RoundedPolygon.Companion companion, int i, float f) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return circle$default(companion, i, f, 0.0f, 0.0f, 12, (Object) null);
    }

    public static final RoundedPolygon circle(RoundedPolygon.Companion companion, int i, float f, float f2) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return circle$default(companion, i, f, f2, 0.0f, 8, (Object) null);
    }

    public static final RoundedPolygon pill(RoundedPolygon.Companion companion) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return pill$default(companion, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 31, (Object) null);
    }

    public static final RoundedPolygon pill(RoundedPolygon.Companion companion, float f) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return pill$default(companion, f, 0.0f, 0.0f, 0.0f, 0.0f, 30, (Object) null);
    }

    public static final RoundedPolygon pill(RoundedPolygon.Companion companion, float f, float f2) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return pill$default(companion, f, f2, 0.0f, 0.0f, 0.0f, 28, (Object) null);
    }

    public static final RoundedPolygon pill(RoundedPolygon.Companion companion, float f, float f2, float f3) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return pill$default(companion, f, f2, f3, 0.0f, 0.0f, 24, (Object) null);
    }

    public static final RoundedPolygon pill(RoundedPolygon.Companion companion, float f, float f2, float f3, float f4) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return pill$default(companion, f, f2, f3, f4, 0.0f, 16, (Object) null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return pillStar$default(companion, 0.0f, 0.0f, 0, 0.0f, (CornerRounding) null, (CornerRounding) null, (List) null, 0.0f, 0.0f, 0.0f, 0.0f, 2047, (Object) null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion, float f) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return pillStar$default(companion, f, 0.0f, 0, 0.0f, (CornerRounding) null, (CornerRounding) null, (List) null, 0.0f, 0.0f, 0.0f, 0.0f, 2046, (Object) null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion, float f, float f2) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return pillStar$default(companion, f, f2, 0, 0.0f, (CornerRounding) null, (CornerRounding) null, (List) null, 0.0f, 0.0f, 0.0f, 0.0f, 2044, (Object) null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion, float f, float f2, int i) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return pillStar$default(companion, f, f2, i, 0.0f, (CornerRounding) null, (CornerRounding) null, (List) null, 0.0f, 0.0f, 0.0f, 0.0f, 2040, (Object) null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion, float f, float f2, int i, float f3) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return pillStar$default(companion, f, f2, i, f3, (CornerRounding) null, (CornerRounding) null, (List) null, 0.0f, 0.0f, 0.0f, 0.0f, 2032, (Object) null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion, float f, float f2, int i, float f3, CornerRounding cornerRounding) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        CornerRounding cornerRounding2 = cornerRounding;
        Intrinsics.checkNotNullParameter(cornerRounding2, "rounding");
        return pillStar$default(companion, f, f2, i, f3, cornerRounding2, (CornerRounding) null, (List) null, 0.0f, 0.0f, 0.0f, 0.0f, 2016, (Object) null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion, float f, float f2, int i, float f3, CornerRounding cornerRounding, CornerRounding cornerRounding2) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        CornerRounding cornerRounding3 = cornerRounding;
        Intrinsics.checkNotNullParameter(cornerRounding3, "rounding");
        return pillStar$default(companion, f, f2, i, f3, cornerRounding3, cornerRounding2, (List) null, 0.0f, 0.0f, 0.0f, 0.0f, 1984, (Object) null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion, float f, float f2, int i, float f3, CornerRounding cornerRounding, CornerRounding cornerRounding2, List<CornerRounding> list) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        CornerRounding cornerRounding3 = cornerRounding;
        Intrinsics.checkNotNullParameter(cornerRounding3, "rounding");
        return pillStar$default(companion, f, f2, i, f3, cornerRounding3, cornerRounding2, list, 0.0f, 0.0f, 0.0f, 0.0f, 1920, (Object) null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion, float f, float f2, int i, float f3, CornerRounding cornerRounding, CornerRounding cornerRounding2, List<CornerRounding> list, float f4) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        CornerRounding cornerRounding3 = cornerRounding;
        Intrinsics.checkNotNullParameter(cornerRounding3, "rounding");
        return pillStar$default(companion, f, f2, i, f3, cornerRounding3, cornerRounding2, list, f4, 0.0f, 0.0f, 0.0f, 1792, (Object) null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion, float f, float f2, int i, float f3, CornerRounding cornerRounding, CornerRounding cornerRounding2, List<CornerRounding> list, float f4, float f5) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        CornerRounding cornerRounding3 = cornerRounding;
        Intrinsics.checkNotNullParameter(cornerRounding3, "rounding");
        return pillStar$default(companion, f, f2, i, f3, cornerRounding3, cornerRounding2, list, f4, f5, 0.0f, 0.0f, 1536, (Object) null);
    }

    public static final RoundedPolygon pillStar(RoundedPolygon.Companion companion, float f, float f2, int i, float f3, CornerRounding cornerRounding, CornerRounding cornerRounding2, List<CornerRounding> list, float f4, float f5, float f6) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        CornerRounding cornerRounding3 = cornerRounding;
        Intrinsics.checkNotNullParameter(cornerRounding3, "rounding");
        return pillStar$default(companion, f, f2, i, f3, cornerRounding3, cornerRounding2, list, f4, f5, f6, 0.0f, 1024, (Object) null);
    }

    public static final RoundedPolygon star(RoundedPolygon.Companion companion, int i) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return star$default(companion, i, 0.0f, 0.0f, (CornerRounding) null, (CornerRounding) null, (List) null, 0.0f, 0.0f, 254, (Object) null);
    }

    public static final RoundedPolygon star(RoundedPolygon.Companion companion, int i, float f) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return star$default(companion, i, f, 0.0f, (CornerRounding) null, (CornerRounding) null, (List) null, 0.0f, 0.0f, 252, (Object) null);
    }

    public static final RoundedPolygon star(RoundedPolygon.Companion companion, int i, float f, float f2) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        return star$default(companion, i, f, f2, (CornerRounding) null, (CornerRounding) null, (List) null, 0.0f, 0.0f, 248, (Object) null);
    }

    public static final RoundedPolygon star(RoundedPolygon.Companion companion, int i, float f, float f2, CornerRounding cornerRounding) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        CornerRounding cornerRounding2 = cornerRounding;
        Intrinsics.checkNotNullParameter(cornerRounding2, "rounding");
        return star$default(companion, i, f, f2, cornerRounding2, (CornerRounding) null, (List) null, 0.0f, 0.0f, 240, (Object) null);
    }

    public static final RoundedPolygon star(RoundedPolygon.Companion companion, int i, float f, float f2, CornerRounding cornerRounding, CornerRounding cornerRounding2) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        CornerRounding cornerRounding3 = cornerRounding;
        Intrinsics.checkNotNullParameter(cornerRounding3, "rounding");
        return star$default(companion, i, f, f2, cornerRounding3, cornerRounding2, (List) null, 0.0f, 0.0f, 224, (Object) null);
    }

    public static final RoundedPolygon star(RoundedPolygon.Companion companion, int i, float f, float f2, CornerRounding cornerRounding, CornerRounding cornerRounding2, List<CornerRounding> list) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        CornerRounding cornerRounding3 = cornerRounding;
        Intrinsics.checkNotNullParameter(cornerRounding3, "rounding");
        return star$default(companion, i, f, f2, cornerRounding3, cornerRounding2, list, 0.0f, 0.0f, 192, (Object) null);
    }

    public static final RoundedPolygon star(RoundedPolygon.Companion companion, int i, float f, float f2, CornerRounding cornerRounding, CornerRounding cornerRounding2, List<CornerRounding> list, float f3) {
        Intrinsics.checkNotNullParameter(companion, "<this>");
        CornerRounding cornerRounding3 = cornerRounding;
        Intrinsics.checkNotNullParameter(cornerRounding3, "rounding");
        return star$default(companion, i, f, f2, cornerRounding3, cornerRounding2, list, f3, 0.0f, 128, (Object) null);
    }

    public static /* synthetic */ RoundedPolygon circle$default(RoundedPolygon.Companion companion, int i, float f, float f2, float f3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 8;
        }
        if ((i2 & 2) != 0) {
            f = 1.0f;
        }
        if ((i2 & 4) != 0) {
            f2 = 0.0f;
        }
        if ((i2 & 8) != 0) {
            f3 = 0.0f;
        }
        return circle(companion, i, f, f2, f3);
    }

    public static final RoundedPolygon circle(RoundedPolygon.Companion $this$circle, int numVertices, float radius, float centerX, float centerY) {
        Intrinsics.checkNotNullParameter($this$circle, "<this>");
        if (numVertices >= 3) {
            return RoundedPolygonKt.RoundedPolygon$default(numVertices, radius / ((float) Math.cos((double) (Utils.getFloatPi() / ((float) numVertices)))), centerX, centerY, new CornerRounding(radius, 0.0f, 2, (DefaultConstructorMarker) null), (List) null, 32, (Object) null);
        }
        float f = centerX;
        throw new IllegalArgumentException("Circle must have at least three vertices");
    }

    public static /* synthetic */ RoundedPolygon rectangle$default(RoundedPolygon.Companion companion, float f, float f2, CornerRounding cornerRounding, List list, float f3, float f4, int i, Object obj) {
        if ((i & 1) != 0) {
            f = 2.0f;
        }
        if ((i & 2) != 0) {
            f2 = 2.0f;
        }
        if ((i & 4) != 0) {
            cornerRounding = CornerRounding.Unrounded;
        }
        if ((i & 8) != 0) {
            list = null;
        }
        if ((i & 16) != 0) {
            f3 = 0.0f;
        }
        if ((i & 32) != 0) {
            f4 = 0.0f;
        }
        return rectangle(companion, f, f2, cornerRounding, list, f3, f4);
    }

    public static final RoundedPolygon rectangle(RoundedPolygon.Companion $this$rectangle, float width, float height, CornerRounding rounding, List<CornerRounding> perVertexRounding, float centerX, float centerY) {
        Intrinsics.checkNotNullParameter($this$rectangle, "<this>");
        Intrinsics.checkNotNullParameter(rounding, "rounding");
        float f = (float) 2;
        float left = centerX - (width / f);
        float top = centerY - (height / f);
        float right = (width / f) + centerX;
        float bottom = (height / f) + centerY;
        return RoundedPolygonKt.RoundedPolygon(new float[]{right, bottom, left, bottom, left, top, right, top}, rounding, perVertexRounding, centerX, centerY);
    }

    public static /* synthetic */ RoundedPolygon star$default(RoundedPolygon.Companion companion, int i, float f, float f2, CornerRounding cornerRounding, CornerRounding cornerRounding2, List list, float f3, float f4, int i2, Object obj) {
        float f5;
        float f6;
        CornerRounding cornerRounding3;
        CornerRounding cornerRounding4;
        float f7;
        int i3 = i2;
        if ((i3 & 2) != 0) {
            f5 = 1.0f;
        } else {
            f5 = f;
        }
        if ((i3 & 4) != 0) {
            f6 = 0.5f;
        } else {
            f6 = f2;
        }
        if ((i3 & 8) != 0) {
            cornerRounding3 = CornerRounding.Unrounded;
        } else {
            cornerRounding3 = cornerRounding;
        }
        List list2 = null;
        if ((i3 & 16) != 0) {
            cornerRounding4 = null;
        } else {
            cornerRounding4 = cornerRounding2;
        }
        if ((i3 & 32) == 0) {
            list2 = list;
        }
        float f8 = 0.0f;
        if ((i3 & 64) != 0) {
            f7 = 0.0f;
        } else {
            f7 = f3;
        }
        if ((i3 & 128) == 0) {
            f8 = f4;
        }
        return star(companion, i, f5, f6, cornerRounding3, cornerRounding4, list2, f7, f8);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v1, resolved type: java.util.Collection} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v7, resolved type: java.util.List<androidx.graphics.shapes.CornerRounding>} */
    /* JADX WARNING: type inference failed for: r18v0 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final androidx.graphics.shapes.RoundedPolygon star(androidx.graphics.shapes.RoundedPolygon.Companion r20, int r21, float r22, float r23, androidx.graphics.shapes.CornerRounding r24, androidx.graphics.shapes.CornerRounding r25, java.util.List<androidx.graphics.shapes.CornerRounding> r26, float r27, float r28) {
        /*
            r0 = r21
            r1 = r22
            r2 = r23
            r3 = r24
            r4 = r27
            r5 = r28
            java.lang.String r6 = "<this>"
            r7 = r20
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r6)
            java.lang.String r6 = "rounding"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r3, r6)
            r6 = 0
            int r8 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
            if (r8 <= 0) goto L_0x0083
            int r6 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x0083
            int r6 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1))
            if (r6 >= 0) goto L_0x007b
            r6 = r26
            if (r6 != 0) goto L_0x006e
            if (r25 == 0) goto L_0x006e
            r8 = 0
            kotlin.ranges.IntRange r9 = kotlin.ranges.RangesKt.until((int) r8, (int) r0)
            java.lang.Iterable r9 = (java.lang.Iterable) r9
            r10 = 0
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            java.util.Collection r11 = (java.util.Collection) r11
            r12 = r9
            r13 = 0
            java.util.Iterator r14 = r12.iterator()
        L_0x0040:
            boolean r15 = r14.hasNext()
            if (r15 == 0) goto L_0x0068
            r15 = r14
            kotlin.collections.IntIterator r15 = (kotlin.collections.IntIterator) r15
            int r15 = r15.nextInt()
            r16 = r15
            r17 = 0
            r18 = r8
            r8 = 2
            androidx.graphics.shapes.CornerRounding[] r8 = new androidx.graphics.shapes.CornerRounding[r8]
            r8[r18] = r3
            r19 = 1
            r8[r19] = r25
            java.util.List r8 = kotlin.collections.CollectionsKt.listOf(r8)
            java.lang.Iterable r8 = (java.lang.Iterable) r8
            kotlin.collections.CollectionsKt.addAll(r11, r8)
            r8 = r18
            goto L_0x0040
        L_0x0068:
            r8 = r11
            java.util.List r8 = (java.util.List) r8
            r6 = r8
        L_0x006e:
            float[] r8 = starVerticesFromNumVerts(r0, r1, r2, r4, r5)
            androidx.graphics.shapes.RoundedPolygon r8 = androidx.graphics.shapes.RoundedPolygonKt.RoundedPolygon((float[]) r8, (androidx.graphics.shapes.CornerRounding) r3, (java.util.List<androidx.graphics.shapes.CornerRounding>) r6, (float) r4, (float) r5)
            return r8
        L_0x007b:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.String r8 = "innerRadius must be less than radius"
            r6.<init>(r8)
            throw r6
        L_0x0083:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException
            java.lang.String r8 = "Star radii must both be greater than 0"
            r6.<init>(r8)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.graphics.shapes.ShapesKt.star(androidx.graphics.shapes.RoundedPolygon$Companion, int, float, float, androidx.graphics.shapes.CornerRounding, androidx.graphics.shapes.CornerRounding, java.util.List, float, float):androidx.graphics.shapes.RoundedPolygon");
    }

    public static /* synthetic */ RoundedPolygon pill$default(RoundedPolygon.Companion companion, float f, float f2, float f3, float f4, float f5, int i, Object obj) {
        float f6;
        if ((i & 1) != 0) {
            f = 2.0f;
        }
        if ((i & 2) != 0) {
            f2 = 1.0f;
        }
        if ((i & 4) != 0) {
            f3 = 0.0f;
        }
        if ((i & 8) != 0) {
            f4 = 0.0f;
        }
        if ((i & 16) != 0) {
            f6 = 0.0f;
        } else {
            f6 = f5;
        }
        float f7 = f4;
        return pill(companion, f, f2, f3, f7, f6);
    }

    public static final RoundedPolygon pill(RoundedPolygon.Companion $this$pill, float width, float height, float smoothing, float centerX, float centerY) {
        Intrinsics.checkNotNullParameter($this$pill, "<this>");
        if (width > 0.0f && height > 0.0f) {
            float f = (float) 2;
            float wHalf = width / f;
            float hHalf = height / f;
            return RoundedPolygonKt.RoundedPolygon$default(new float[]{wHalf + centerX, hHalf + centerY, (-wHalf) + centerX, hHalf + centerY, (-wHalf) + centerX, (-hHalf) + centerY, wHalf + centerX, (-hHalf) + centerY}, new CornerRounding(Math.min(wHalf, hHalf), smoothing), (List) null, centerX, centerY, 4, (Object) null);
        }
        float f2 = smoothing;
        throw new IllegalArgumentException("Pill shapes must have positive width and height");
    }

    public static /* synthetic */ RoundedPolygon pillStar$default(RoundedPolygon.Companion companion, float f, float f2, int i, float f3, CornerRounding cornerRounding, CornerRounding cornerRounding2, List list, float f4, float f5, float f6, float f7, int i2, Object obj) {
        float f8;
        if ((i2 & 1) != 0) {
            f = 2.0f;
        }
        if ((i2 & 2) != 0) {
            f2 = 1.0f;
        }
        if ((i2 & 4) != 0) {
            i = 8;
        }
        if ((i2 & 8) != 0) {
            f3 = 0.5f;
        }
        if ((i2 & 16) != 0) {
            cornerRounding = CornerRounding.Unrounded;
        }
        if ((i2 & 32) != 0) {
            cornerRounding2 = null;
        }
        if ((i2 & 64) != 0) {
            list = null;
        }
        if ((i2 & 128) != 0) {
            f4 = 0.5f;
        }
        if ((i2 & 256) != 0) {
            f5 = 0.0f;
        }
        if ((i2 & 512) != 0) {
            f6 = 0.0f;
        }
        if ((i2 & 1024) != 0) {
            f8 = 0.0f;
        } else {
            f8 = f7;
        }
        float f9 = f5;
        float f10 = f6;
        List list2 = list;
        float f11 = f4;
        CornerRounding cornerRounding3 = cornerRounding2;
        float f12 = f3;
        return pillStar(companion, f, f2, i, f12, cornerRounding, cornerRounding3, list2, f11, f9, f10, f8);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v2, resolved type: java.util.Collection} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: java.util.List<androidx.graphics.shapes.CornerRounding>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final androidx.graphics.shapes.RoundedPolygon pillStar(androidx.graphics.shapes.RoundedPolygon.Companion r16, float r17, float r18, int r19, float r20, androidx.graphics.shapes.CornerRounding r21, androidx.graphics.shapes.CornerRounding r22, java.util.List<androidx.graphics.shapes.CornerRounding> r23, float r24, float r25, float r26, float r27) {
        /*
            r0 = r21
            java.lang.String r1 = "<this>"
            r2 = r16
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r2, r1)
            java.lang.String r1 = "rounding"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r0, r1)
            r1 = 0
            int r3 = (r17 > r1 ? 1 : (r17 == r1 ? 0 : -1))
            r4 = 1
            r5 = 0
            if (r3 <= 0) goto L_0x001b
            int r3 = (r18 > r1 ? 1 : (r18 == r1 ? 0 : -1))
            if (r3 <= 0) goto L_0x001b
            r3 = r4
            goto L_0x001c
        L_0x001b:
            r3 = r5
        L_0x001c:
            if (r3 == 0) goto L_0x00a2
            int r1 = (r20 > r1 ? 1 : (r20 == r1 ? 0 : -1))
            if (r1 <= 0) goto L_0x002a
            r1 = 1065353216(0x3f800000, float:1.0)
            int r1 = (r20 > r1 ? 1 : (r20 == r1 ? 0 : -1))
            if (r1 > 0) goto L_0x002a
            r1 = r4
            goto L_0x002b
        L_0x002a:
            r1 = r5
        L_0x002b:
            if (r1 == 0) goto L_0x0095
            r1 = r23
            if (r1 != 0) goto L_0x0070
            if (r22 == 0) goto L_0x0070
            r6 = r19
            kotlin.ranges.IntRange r3 = kotlin.ranges.RangesKt.until((int) r5, (int) r6)
            java.lang.Iterable r3 = (java.lang.Iterable) r3
            r7 = 0
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            java.util.Collection r8 = (java.util.Collection) r8
            r9 = r3
            r10 = 0
            java.util.Iterator r11 = r9.iterator()
        L_0x0049:
            boolean r12 = r11.hasNext()
            if (r12 == 0) goto L_0x0069
            r12 = r11
            kotlin.collections.IntIterator r12 = (kotlin.collections.IntIterator) r12
            int r12 = r12.nextInt()
            r13 = r12
            r14 = 0
            r15 = 2
            androidx.graphics.shapes.CornerRounding[] r15 = new androidx.graphics.shapes.CornerRounding[r15]
            r15[r5] = r0
            r15[r4] = r22
            java.util.List r13 = kotlin.collections.CollectionsKt.listOf(r15)
            java.lang.Iterable r13 = (java.lang.Iterable) r13
            kotlin.collections.CollectionsKt.addAll(r8, r13)
            goto L_0x0049
        L_0x0069:
            r4 = r8
            java.util.List r4 = (java.util.List) r4
            r1 = r4
            goto L_0x0072
        L_0x0070:
            r6 = r19
        L_0x0072:
            r7 = r17
            r8 = r18
            r9 = r20
            r10 = r24
            r11 = r25
            r12 = r26
            r13 = r27
            float[] r3 = pillStarVerticesFromNumVerts(r6, r7, r8, r9, r10, r11, r12, r13)
            androidx.graphics.shapes.RoundedPolygon r3 = androidx.graphics.shapes.RoundedPolygonKt.RoundedPolygon((float[]) r3, (androidx.graphics.shapes.CornerRounding) r0, (java.util.List<androidx.graphics.shapes.CornerRounding>) r1, (float) r12, (float) r13)
            return r3
        L_0x0095:
            r12 = r26
            r13 = r27
            r1 = 0
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            java.lang.String r4 = "innerRadius must be between 0 and 1"
            r3.<init>(r4)
            throw r3
        L_0x00a2:
            r12 = r26
            r13 = r27
            r1 = 0
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            java.lang.String r4 = "Pill shapes must have positive width and height"
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.graphics.shapes.ShapesKt.pillStar(androidx.graphics.shapes.RoundedPolygon$Companion, float, float, int, float, androidx.graphics.shapes.CornerRounding, androidx.graphics.shapes.CornerRounding, java.util.List, float, float, float, float):androidx.graphics.shapes.RoundedPolygon");
    }

    private static final float[] pillStarVerticesFromNumVerts(int numVerticesPerRadius, float width, float height, float innerRadius, float vertexSpacing, float startLocation, float centerX, float centerY) {
        int i;
        float perimeter;
        long vertex;
        float endcapRadius = Math.min(width, height);
        float vSegLen = RangesKt.coerceAtLeast(height - width, 0.0f);
        float hSegLen = RangesKt.coerceAtLeast(width - height, 0.0f);
        float f = (float) 2;
        float vSegHalf = vSegLen / f;
        float hSegHalf = hSegLen / f;
        float circlePerimeter = Utils.getTwoPi() * endcapRadius * Utils.interpolate(innerRadius, 1.0f, vertexSpacing);
        float perimeter2 = (f * hSegLen) + (f * vSegLen) + circlePerimeter;
        float[] sections = new float[11];
        sections[0] = 0.0f;
        sections[1] = vSegLen / f;
        float f2 = (float) 4;
        sections[2] = sections[1] + (circlePerimeter / f2);
        sections[3] = sections[2] + hSegLen;
        sections[4] = sections[3] + (circlePerimeter / f2);
        sections[5] = sections[4] + vSegLen;
        sections[6] = sections[5] + (circlePerimeter / f2);
        sections[7] = sections[6] + hSegLen;
        sections[8] = sections[7] + (circlePerimeter / f2);
        sections[9] = sections[8] + (vSegLen / f);
        sections[10] = perimeter2;
        float secStart = 0.0f;
        float secEnd = sections[1];
        float t = startLocation * perimeter2;
        float[] result = new float[(numVerticesPerRadius * 4)];
        int arrayIndex = 0;
        int currSecIndex = 0;
        long rectBR = FloatFloatPair.m1964constructorimpl(hSegHalf, vSegHalf);
        float endcapRadius2 = endcapRadius;
        long rectBL = FloatFloatPair.m1964constructorimpl(-hSegHalf, vSegHalf);
        float vSegLen2 = vSegLen;
        float tPerVertex = perimeter2 / ((float) (numVerticesPerRadius * 2));
        long rectTL = FloatFloatPair.m1964constructorimpl(-hSegHalf, -vSegHalf);
        float hSegLen2 = hSegLen;
        boolean inner = false;
        long rectTR = FloatFloatPair.m1964constructorimpl(hSegHalf, -vSegHalf);
        float f3 = f;
        int i2 = numVerticesPerRadius * 2;
        float f4 = circlePerimeter;
        int i3 = 0;
        while (i3 < i2) {
            float boundedT = t % perimeter2;
            if (boundedT < secStart) {
                currSecIndex = 0;
            }
            while (true) {
                int i4 = i2;
                if (boundedT >= sections[(currSecIndex + 1) % sections.length]) {
                    currSecIndex = (currSecIndex + 1) % sections.length;
                    secStart = sections[currSecIndex];
                    secEnd = sections[(currSecIndex + 1) % sections.length];
                    i2 = i4;
                } else {
                    float tInSection = boundedT - secStart;
                    float tProportion = tInSection / (secEnd - secStart);
                    float currRadius = inner ? endcapRadius2 * innerRadius : endcapRadius2;
                    switch (currSecIndex) {
                        case 0:
                            i = i3;
                            perimeter = perimeter2;
                            vertex = FloatFloatPair.m1964constructorimpl(currRadius, tProportion * vSegHalf);
                            break;
                        case 1:
                            i = i3;
                            perimeter = perimeter2;
                            float tInSection2 = currRadius;
                            vertex = PointKt.m2047plusybeJwSQ(Utils.m2065radialToCartesianL6JJ3z0$default(currRadius, (Utils.getFloatPi() * tProportion) / f3, 0, 4, (Object) null), rectBR);
                            break;
                        case 2:
                            i = i3;
                            perimeter = perimeter2;
                            vertex = FloatFloatPair.m1964constructorimpl(hSegHalf - (tProportion * hSegLen2), currRadius);
                            break;
                        case 3:
                            i = i3;
                            perimeter = perimeter2;
                            float tInSection3 = currRadius;
                            vertex = PointKt.m2047plusybeJwSQ(Utils.m2065radialToCartesianL6JJ3z0$default(currRadius, (Utils.getFloatPi() / f3) + ((Utils.getFloatPi() * tProportion) / f3), 0, 4, (Object) null), rectBL);
                            break;
                        case 4:
                            i = i3;
                            perimeter = perimeter2;
                            vertex = FloatFloatPair.m1964constructorimpl(-currRadius, vSegHalf - (tProportion * vSegLen2));
                            break;
                        case 5:
                            i = i3;
                            perimeter = perimeter2;
                            float tInSection4 = currRadius;
                            vertex = PointKt.m2047plusybeJwSQ(Utils.m2065radialToCartesianL6JJ3z0$default(currRadius, Utils.getFloatPi() + ((Utils.getFloatPi() * tProportion) / f3), 0, 4, (Object) null), rectTL);
                            break;
                        case 6:
                            i = i3;
                            perimeter = perimeter2;
                            vertex = FloatFloatPair.m1964constructorimpl((-hSegHalf) + (tProportion * hSegLen2), -currRadius);
                            break;
                        case 7:
                            i = i3;
                            perimeter = perimeter2;
                            float f5 = tInSection;
                            float tInSection5 = currRadius;
                            vertex = PointKt.m2047plusybeJwSQ(Utils.m2065radialToCartesianL6JJ3z0$default(currRadius, (Utils.getFloatPi() * 1.5f) + ((Utils.getFloatPi() * tProportion) / f3), 0, 4, (Object) null), rectTR);
                            break;
                        default:
                            float f6 = tInSection;
                            i = i3;
                            perimeter = perimeter2;
                            vertex = FloatFloatPair.m1964constructorimpl(currRadius, (-vSegHalf) + (tProportion * vSegHalf));
                            break;
                    }
                    int arrayIndex2 = arrayIndex + 1;
                    result[arrayIndex] = PointKt.m2043getXDnnuFBc(vertex) + centerX;
                    arrayIndex = arrayIndex2 + 1;
                    result[arrayIndex2] = PointKt.m2044getYDnnuFBc(vertex) + centerY;
                    t += tPerVertex;
                    inner = !inner;
                    i3 = i + 1;
                    i2 = i4;
                    perimeter2 = perimeter;
                }
            }
        }
        return result;
    }

    private static final float[] starVerticesFromNumVerts(int numVerticesPerRadius, float radius, float innerRadius, float centerX, float centerY) {
        float[] result = new float[(numVerticesPerRadius * 4)];
        int arrayIndex = 0;
        for (int i = 0; i < numVerticesPerRadius; i++) {
            long vertex = Utils.m2065radialToCartesianL6JJ3z0$default(radius, (Utils.getFloatPi() / ((float) numVerticesPerRadius)) * ((float) 2) * ((float) i), 0, 4, (Object) null);
            int arrayIndex2 = arrayIndex + 1;
            result[arrayIndex] = PointKt.m2043getXDnnuFBc(vertex) + centerX;
            int arrayIndex3 = arrayIndex2 + 1;
            result[arrayIndex2] = PointKt.m2044getYDnnuFBc(vertex) + centerY;
            long vertex2 = Utils.m2065radialToCartesianL6JJ3z0$default(innerRadius, (Utils.getFloatPi() / ((float) numVerticesPerRadius)) * ((float) ((i * 2) + 1)), 0, 4, (Object) null);
            int arrayIndex4 = arrayIndex3 + 1;
            result[arrayIndex3] = PointKt.m2043getXDnnuFBc(vertex2) + centerX;
            arrayIndex = arrayIndex4 + 1;
            result[arrayIndex4] = PointKt.m2044getYDnnuFBc(vertex2) + centerY;
        }
        return result;
    }
}
