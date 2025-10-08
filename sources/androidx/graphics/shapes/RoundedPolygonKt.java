package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;
import androidx.collection.MutableFloatList;
import androidx.graphics.shapes.Feature;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001\u001a@\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\nH\u0007\u001aL\u0010\u0000\u001a\u00020\u00012\b\b\u0001\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\n2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\bH\u0007\u001a\u0019\u0010\u000f\u001a\u00060\u0010j\u0002`\u00112\u0006\u0010\u0003\u001a\u00020\u0004H\u0002¢\u0006\u0002\u0010\u0012\u001a(\u0010\u0013\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0002¨\u0006\u0014"}, d2 = {"RoundedPolygon", "Landroidx/graphics/shapes/RoundedPolygon;", "source", "vertices", "", "rounding", "Landroidx/graphics/shapes/CornerRounding;", "perVertexRounding", "", "centerX", "", "centerY", "numVertices", "", "radius", "calculateCenter", "Landroidx/collection/FloatFloatPair;", "Landroidx/graphics/shapes/Point;", "([F)J", "verticesFromNumVerts", "graphics-shapes_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* compiled from: RoundedPolygon.kt */
public final class RoundedPolygonKt {
    public static final RoundedPolygon RoundedPolygon(int i) {
        return RoundedPolygon$default(i, 0.0f, 0.0f, 0.0f, (CornerRounding) null, (List) null, 62, (Object) null);
    }

    public static final RoundedPolygon RoundedPolygon(int i, float f) {
        return RoundedPolygon$default(i, f, 0.0f, 0.0f, (CornerRounding) null, (List) null, 60, (Object) null);
    }

    public static final RoundedPolygon RoundedPolygon(int i, float f, float f2) {
        return RoundedPolygon$default(i, f, f2, 0.0f, (CornerRounding) null, (List) null, 56, (Object) null);
    }

    public static final RoundedPolygon RoundedPolygon(int i, float f, float f2, float f3) {
        return RoundedPolygon$default(i, f, f2, f3, (CornerRounding) null, (List) null, 48, (Object) null);
    }

    public static final RoundedPolygon RoundedPolygon(int i, float f, float f2, float f3, CornerRounding cornerRounding) {
        Intrinsics.checkNotNullParameter(cornerRounding, "rounding");
        return RoundedPolygon$default(i, f, f2, f3, cornerRounding, (List) null, 32, (Object) null);
    }

    public static final RoundedPolygon RoundedPolygon(float[] fArr) {
        Intrinsics.checkNotNullParameter(fArr, "vertices");
        return RoundedPolygon$default(fArr, (CornerRounding) null, (List) null, 0.0f, 0.0f, 30, (Object) null);
    }

    public static final RoundedPolygon RoundedPolygon(float[] fArr, CornerRounding cornerRounding) {
        Intrinsics.checkNotNullParameter(fArr, "vertices");
        Intrinsics.checkNotNullParameter(cornerRounding, "rounding");
        return RoundedPolygon$default(fArr, cornerRounding, (List) null, 0.0f, 0.0f, 28, (Object) null);
    }

    public static final RoundedPolygon RoundedPolygon(float[] fArr, CornerRounding cornerRounding, List<CornerRounding> list) {
        Intrinsics.checkNotNullParameter(fArr, "vertices");
        Intrinsics.checkNotNullParameter(cornerRounding, "rounding");
        return RoundedPolygon$default(fArr, cornerRounding, list, 0.0f, 0.0f, 24, (Object) null);
    }

    public static final RoundedPolygon RoundedPolygon(float[] fArr, CornerRounding cornerRounding, List<CornerRounding> list, float f) {
        Intrinsics.checkNotNullParameter(fArr, "vertices");
        Intrinsics.checkNotNullParameter(cornerRounding, "rounding");
        return RoundedPolygon$default(fArr, cornerRounding, list, f, 0.0f, 16, (Object) null);
    }

    public static /* synthetic */ RoundedPolygon RoundedPolygon$default(int i, float f, float f2, float f3, CornerRounding cornerRounding, List list, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            f = 1.0f;
        }
        if ((i2 & 4) != 0) {
            f2 = 0.0f;
        }
        if ((i2 & 8) != 0) {
            f3 = 0.0f;
        }
        if ((i2 & 16) != 0) {
            cornerRounding = CornerRounding.Unrounded;
        }
        List list2 = (i2 & 32) != 0 ? null : list;
        return RoundedPolygon(i, f, f2, f3, cornerRounding, list2);
    }

    public static final RoundedPolygon RoundedPolygon(int numVertices, float radius, float centerX, float centerY, CornerRounding rounding, List<CornerRounding> perVertexRounding) {
        Intrinsics.checkNotNullParameter(rounding, "rounding");
        return RoundedPolygon(verticesFromNumVerts(numVertices, radius, centerX, centerY), rounding, perVertexRounding, centerX, centerY);
    }

    public static final RoundedPolygon RoundedPolygon(RoundedPolygon source) {
        Intrinsics.checkNotNullParameter(source, "source");
        return new RoundedPolygon(source.getFeatures$graphics_shapes_release(), source.getCenterX(), source.getCenterY());
    }

    public static /* synthetic */ RoundedPolygon RoundedPolygon$default(float[] fArr, CornerRounding cornerRounding, List list, float f, float f2, int i, Object obj) {
        if ((i & 2) != 0) {
            cornerRounding = CornerRounding.Unrounded;
        }
        if ((i & 4) != 0) {
            list = null;
        }
        if ((i & 8) != 0) {
            f = Float.MIN_VALUE;
        }
        if ((i & 16) != 0) {
            f2 = Float.MIN_VALUE;
        }
        return RoundedPolygon(fArr, cornerRounding, (List<CornerRounding>) list, f, f2);
    }

    public static final RoundedPolygon RoundedPolygon(float[] vertices, CornerRounding rounding, List<CornerRounding> perVertexRounding, float centerX, float centerY) {
        long j;
        Pair pair;
        CornerRounding cornerRounding;
        float[] fArr = vertices;
        List<CornerRounding> list = perVertexRounding;
        Intrinsics.checkNotNullParameter(fArr, "vertices");
        CornerRounding cornerRounding2 = rounding;
        Intrinsics.checkNotNullParameter(cornerRounding2, "rounding");
        if (fArr.length >= 6) {
            int i = 2;
            int i2 = 1;
            if (fArr.length % 2 == 1) {
                throw new IllegalArgumentException("The vertices array should have even size");
            } else if (list == null || list.size() * 2 == fArr.length) {
                List corners = new ArrayList();
                int n = fArr.length / 2;
                List roundedCorners = new ArrayList();
                int i3 = 0;
                while (i3 < n) {
                    if (list == null || (cornerRounding = list.get(i3)) == null) {
                        cornerRounding = cornerRounding2;
                    }
                    CornerRounding vtxRounding = cornerRounding;
                    int prevIndex = (((i3 + n) - i2) % n) * 2;
                    int nextIndex = ((i3 + 1) % n) * 2;
                    roundedCorners.add(new RoundedCorner(FloatFloatPair.m1964constructorimpl(fArr[prevIndex], fArr[prevIndex + 1]), FloatFloatPair.m1964constructorimpl(fArr[i3 * 2], fArr[(i3 * 2) + i2]), FloatFloatPair.m1964constructorimpl(fArr[nextIndex], fArr[nextIndex + 1]), vtxRounding, (DefaultConstructorMarker) null));
                    i3++;
                    i2 = i2;
                }
                int i4 = i2;
                Iterable $this$map$iv = RangesKt.until(0, n);
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                Iterator it = $this$map$iv.iterator();
                while (it.hasNext()) {
                    int ix = ((IntIterator) it).nextInt();
                    float expectedRoundCut = ((RoundedCorner) roundedCorners.get(ix)).getExpectedRoundCut() + ((RoundedCorner) roundedCorners.get((ix + 1) % n)).getExpectedRoundCut();
                    int i5 = i;
                    float expectedCut = ((RoundedCorner) roundedCorners.get(ix)).getExpectedCut() + ((RoundedCorner) roundedCorners.get((ix + 1) % n)).getExpectedCut();
                    float sideSize = Utils.distance(fArr[ix * 2] - fArr[((ix + 1) % n) * 2], fArr[(ix * 2) + 1] - fArr[(((ix + 1) % n) * 2) + 1]);
                    if (expectedRoundCut > sideSize) {
                        float f = sideSize;
                        pair = TuplesKt.to(Float.valueOf(sideSize / expectedRoundCut), Float.valueOf(0.0f));
                    } else {
                        float sideSize2 = sideSize;
                        if (expectedCut > sideSize2) {
                            pair = TuplesKt.to(Float.valueOf(1.0f), Float.valueOf((sideSize2 - expectedRoundCut) / (expectedCut - expectedRoundCut)));
                        } else {
                            pair = TuplesKt.to(Float.valueOf(1.0f), Float.valueOf(1.0f));
                        }
                    }
                    destination$iv$iv.add(pair);
                    fArr = vertices;
                    List<CornerRounding> list2 = perVertexRounding;
                    i = i5;
                }
                int i6 = i;
                List cutAdjusts = (List) destination$iv$iv;
                int i7 = 0;
                while (i7 < n) {
                    int i8 = i6;
                    MutableFloatList allowedCuts = new MutableFloatList(i8);
                    for (int delta = 0; delta < i8; delta++) {
                        Pair pair2 = (Pair) cutAdjusts.get((((i7 + n) - 1) + delta) % n);
                        allowedCuts.add((((RoundedCorner) roundedCorners.get(i7)).getExpectedRoundCut() * ((Number) pair2.component1()).floatValue()) + ((((RoundedCorner) roundedCorners.get(i7)).getExpectedCut() - ((RoundedCorner) roundedCorners.get(i7)).getExpectedRoundCut()) * ((Number) pair2.component2()).floatValue()));
                    }
                    corners.add(((RoundedCorner) roundedCorners.get(i7)).getCubics(allowedCuts.get(0), allowedCuts.get(i4)));
                    i7++;
                    i6 = i8;
                    i4 = 1;
                }
                List tempFeatures = new ArrayList();
                int i9 = 0;
                while (i9 < n) {
                    int prevVtxIndex = ((i9 + n) - 1) % n;
                    int nextVtxIndex = (i9 + 1) % n;
                    long currVertex = FloatFloatPair.m1964constructorimpl(vertices[i9 * 2], vertices[(i9 * 2) + 1]);
                    long prevVertex = FloatFloatPair.m1964constructorimpl(vertices[prevVtxIndex * 2], vertices[(prevVtxIndex * 2) + 1]);
                    long nextVertex = FloatFloatPair.m1964constructorimpl(vertices[nextVtxIndex * 2], vertices[(nextVtxIndex * 2) + 1]);
                    int i10 = prevVtxIndex;
                    int n2 = n;
                    long j2 = prevVertex;
                    long j3 = nextVertex;
                    tempFeatures.add(new Feature.Corner((List) corners.get(i9), currVertex, ((RoundedCorner) roundedCorners.get(i9)).m2055getCenter1ufDz9w(), PointKt.m2034clockwiseybeJwSQ(PointKt.m2046minusybeJwSQ(currVertex, prevVertex), PointKt.m2046minusybeJwSQ(nextVertex, currVertex)), (DefaultConstructorMarker) null));
                    tempFeatures.add(new Feature.Edge(CollectionsKt.listOf(Cubic.Companion.straightLine(((Cubic) CollectionsKt.last((List) corners.get(i9))).getAnchor1X(), ((Cubic) CollectionsKt.last((List) corners.get(i9))).getAnchor1Y(), ((Cubic) CollectionsKt.first((List) corners.get((i9 + 1) % n2))).getAnchor0X(), ((Cubic) CollectionsKt.first((List) corners.get((i9 + 1) % n2))).getAnchor0Y()))));
                    i9++;
                    cutAdjusts = cutAdjusts;
                    n = n2;
                }
                int i11 = n;
                if (!(centerX == Float.MIN_VALUE)) {
                    if (!(centerY == Float.MIN_VALUE)) {
                        j = FloatFloatPair.m1964constructorimpl(centerX, centerY);
                        return new RoundedPolygon(tempFeatures, Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L)));
                    }
                }
                j = calculateCenter(vertices);
                return new RoundedPolygon(tempFeatures, Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L)));
            } else {
                throw new IllegalArgumentException("perVertexRounding list should be either null or the same size as the number of vertices (vertices.size / 2)");
            }
        } else {
            throw new IllegalArgumentException("Polygons must have at least 3 vertices");
        }
    }

    private static final long calculateCenter(float[] vertices) {
        float cumulativeX = 0.0f;
        float cumulativeY = 0.0f;
        int index = 0;
        while (index < vertices.length) {
            int index2 = index + 1;
            cumulativeX += vertices[index];
            index = index2 + 1;
            cumulativeY += vertices[index2];
        }
        float f = (float) 2;
        return FloatFloatPair.m1964constructorimpl((cumulativeX / ((float) vertices.length)) / f, (cumulativeY / ((float) vertices.length)) / f);
    }

    private static final float[] verticesFromNumVerts(int numVertices, float radius, float centerX, float centerY) {
        float[] result = new float[(numVertices * 2)];
        int arrayIndex = 0;
        int i = 0;
        while (i < numVertices) {
            float radius2 = radius;
            long vertex = PointKt.m2047plusybeJwSQ(Utils.m2065radialToCartesianL6JJ3z0$default(radius2, (Utils.getFloatPi() / ((float) numVertices)) * ((float) 2) * ((float) i), 0, 4, (Object) null), FloatFloatPair.m1964constructorimpl(centerX, centerY));
            int arrayIndex2 = arrayIndex + 1;
            result[arrayIndex] = PointKt.m2043getXDnnuFBc(vertex);
            arrayIndex = arrayIndex2 + 1;
            result[arrayIndex2] = PointKt.m2044getYDnnuFBc(vertex);
            i++;
            radius = radius2;
        }
        return result;
    }
}
