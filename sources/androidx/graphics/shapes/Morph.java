package androidx.graphics.shapes;

import androidx.graphics.shapes.MeasuredPolygon;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\t0\u00072\u0006\u0010\u0010\u001a\u00020\u0011J\u001c\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00132\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u0007J\u0010\u0010\u0017\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0013J2\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u001a\u001a\u00020\u001b2\u0012\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u00190\u001dH\bø\u0001\u0000R \u0010\u0006\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\b0\u0007X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R,\u0010\n\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\b0\u00078@X\u0004¢\u0006\f\u0012\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000\u0002\u0007\n\u0005\b20\u0001¨\u0006\u001f"}, d2 = {"Landroidx/graphics/shapes/Morph;", "", "start", "Landroidx/graphics/shapes/RoundedPolygon;", "end", "(Landroidx/graphics/shapes/RoundedPolygon;Landroidx/graphics/shapes/RoundedPolygon;)V", "_morphMatch", "", "Lkotlin/Pair;", "Landroidx/graphics/shapes/Cubic;", "morphMatch", "getMorphMatch$annotations", "()V", "getMorphMatch", "()Ljava/util/List;", "asCubics", "progress", "", "calculateBounds", "", "bounds", "approximate", "", "calculateMaxBounds", "forEachCubic", "", "mutableCubic", "Landroidx/graphics/shapes/MutableCubic;", "callback", "Lkotlin/Function1;", "Companion", "graphics-shapes_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: Morph.kt */
public final class Morph {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private final List<Pair<Cubic, Cubic>> _morphMatch = Companion.match$graphics_shapes_release(this.start, this.end);
    private final RoundedPolygon end;
    private final RoundedPolygon start;

    public static /* synthetic */ void getMorphMatch$annotations() {
    }

    @JvmStatic
    public static final List<Pair<Cubic, Cubic>> match$graphics_shapes_release(RoundedPolygon roundedPolygon, RoundedPolygon roundedPolygon2) {
        return Companion.match$graphics_shapes_release(roundedPolygon, roundedPolygon2);
    }

    public final float[] calculateBounds() {
        return calculateBounds$default(this, (float[]) null, false, 3, (Object) null);
    }

    public final float[] calculateBounds(float[] fArr) {
        Intrinsics.checkNotNullParameter(fArr, "bounds");
        return calculateBounds$default(this, fArr, false, 2, (Object) null);
    }

    public Morph(RoundedPolygon start2, RoundedPolygon end2) {
        Intrinsics.checkNotNullParameter(start2, "start");
        Intrinsics.checkNotNullParameter(end2, "end");
        this.start = start2;
        this.end = end2;
    }

    public final List<Pair<Cubic, Cubic>> getMorphMatch() {
        return this._morphMatch;
    }

    public static /* synthetic */ float[] calculateBounds$default(Morph morph, float[] fArr, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            fArr = new float[4];
        }
        if ((i & 2) != 0) {
            z = true;
        }
        return morph.calculateBounds(fArr, z);
    }

    public final float[] calculateBounds(float[] bounds, boolean approximate) {
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        this.start.calculateBounds(bounds, approximate);
        float minX = bounds[0];
        float minY = bounds[1];
        float maxX = bounds[2];
        float maxY = bounds[3];
        this.end.calculateBounds(bounds, approximate);
        bounds[0] = Math.min(minX, bounds[0]);
        bounds[1] = Math.min(minY, bounds[1]);
        bounds[2] = Math.max(maxX, bounds[2]);
        bounds[3] = Math.max(maxY, bounds[3]);
        return bounds;
    }

    public static /* synthetic */ float[] calculateMaxBounds$default(Morph morph, float[] fArr, int i, Object obj) {
        if ((i & 1) != 0) {
            fArr = new float[4];
        }
        return morph.calculateMaxBounds(fArr);
    }

    public final float[] calculateMaxBounds(float[] bounds) {
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        this.start.calculateMaxBounds(bounds);
        float minX = bounds[0];
        float minY = bounds[1];
        float maxX = bounds[2];
        float maxY = bounds[3];
        this.end.calculateMaxBounds(bounds);
        bounds[0] = Math.min(minX, bounds[0]);
        bounds[1] = Math.min(minY, bounds[1]);
        bounds[2] = Math.max(maxX, bounds[2]);
        bounds[3] = Math.max(maxY, bounds[3]);
        return bounds;
    }

    public final List<Cubic> asCubics(float progress) {
        List createListBuilder = CollectionsKt.createListBuilder();
        List $this$asCubics_u24lambda_u240 = createListBuilder;
        Cubic firstCubic = null;
        Cubic lastCubic = null;
        int size = this._morphMatch.size();
        for (int i = 0; i < size; i++) {
            float[] fArr = new float[8];
            for (int i2 = 0; i2 < 8; i2++) {
                fArr[i2] = Utils.interpolate(((Cubic) this._morphMatch.get(i).getFirst()).getPoints$graphics_shapes_release()[i2], ((Cubic) this._morphMatch.get(i).getSecond()).getPoints$graphics_shapes_release()[i2], progress);
            }
            float f = progress;
            Cubic cubic = new Cubic(fArr);
            if (firstCubic == null) {
                firstCubic = cubic;
            }
            if (lastCubic != null) {
                $this$asCubics_u24lambda_u240.add(lastCubic);
            }
            lastCubic = cubic;
        }
        float f2 = progress;
        if (!(lastCubic == null || firstCubic == null)) {
            $this$asCubics_u24lambda_u240.add(CubicKt.Cubic(lastCubic.getAnchor0X(), lastCubic.getAnchor0Y(), lastCubic.getControl0X(), lastCubic.getControl0Y(), lastCubic.getControl1X(), lastCubic.getControl1Y(), firstCubic.getAnchor0X(), firstCubic.getAnchor0Y()));
        }
        return CollectionsKt.build(createListBuilder);
    }

    public static /* synthetic */ void forEachCubic$default(Morph $this, float progress, MutableCubic mutableCubic, Function1 callback, int i, Object obj) {
        if ((i & 2) != 0) {
            mutableCubic = new MutableCubic();
        }
        Intrinsics.checkNotNullParameter(mutableCubic, "mutableCubic");
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = $this.getMorphMatch().size();
        for (int i2 = 0; i2 < size; i2++) {
            mutableCubic.interpolate((Cubic) $this.getMorphMatch().get(i2).getFirst(), (Cubic) $this.getMorphMatch().get(i2).getSecond(), progress);
            callback.invoke(mutableCubic);
        }
    }

    public final void forEachCubic(float progress, MutableCubic mutableCubic, Function1<? super MutableCubic, Unit> callback) {
        Intrinsics.checkNotNullParameter(mutableCubic, "mutableCubic");
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = getMorphMatch().size();
        for (int i = 0; i < size; i++) {
            mutableCubic.interpolate((Cubic) getMorphMatch().get(i).getFirst(), (Cubic) getMorphMatch().get(i).getSecond(), progress);
            callback.invoke(mutableCubic);
        }
    }

    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J/\u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00050\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0001¢\u0006\u0002\b\n¨\u0006\u000b"}, d2 = {"Landroidx/graphics/shapes/Morph$Companion;", "", "()V", "match", "", "Lkotlin/Pair;", "Landroidx/graphics/shapes/Cubic;", "p1", "Landroidx/graphics/shapes/RoundedPolygon;", "p2", "match$graphics_shapes_release", "graphics-shapes_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* compiled from: Morph.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final List<Pair<Cubic, Cubic>> match$graphics_shapes_release(RoundedPolygon p1, RoundedPolygon p2) {
            float b2a;
            float b2a2;
            Pair<MeasuredPolygon.MeasuredCubic, MeasuredPolygon.MeasuredCubic> pair;
            MeasuredPolygon.MeasuredCubic newb1;
            Pair<MeasuredPolygon.MeasuredCubic, MeasuredPolygon.MeasuredCubic> pair2;
            RoundedPolygon roundedPolygon = p1;
            RoundedPolygon roundedPolygon2 = p2;
            Intrinsics.checkNotNullParameter(roundedPolygon, "p1");
            Intrinsics.checkNotNullParameter(roundedPolygon2, "p2");
            MeasuredPolygon measuredPolygon1 = MeasuredPolygon.Companion.measurePolygon$graphics_shapes_release(new AngleMeasurer(roundedPolygon.getCenterX(), roundedPolygon.getCenterY()), roundedPolygon);
            MeasuredPolygon measuredPolygon2 = MeasuredPolygon.Companion.measurePolygon$graphics_shapes_release(new AngleMeasurer(roundedPolygon2.getCenterX(), roundedPolygon2.getCenterY()), roundedPolygon2);
            DoubleMapper doubleMapper = FeatureMappingKt.featureMapper(measuredPolygon1.getFeatures(), measuredPolygon2.getFeatures());
            float polygon2CutPoint = doubleMapper.map(0.0f);
            String access$getLOG_TAG$p = MorphKt.LOG_TAG;
            MeasuredPolygon bs1 = measuredPolygon1;
            MeasuredPolygon bs2 = measuredPolygon2.cutAndShift(polygon2CutPoint);
            List ret = new ArrayList();
            int i1 = 0 + 1;
            MeasuredPolygon.MeasuredCubic b1 = (MeasuredPolygon.MeasuredCubic) CollectionsKt.getOrNull(bs1, 0);
            int i2 = 0 + 1;
            MeasuredPolygon.MeasuredCubic b2 = (MeasuredPolygon.MeasuredCubic) CollectionsKt.getOrNull(bs2, 0);
            while (b1 != null && b2 != null) {
                float b1a = i1 == bs1.size() ? 1.0f : b1.getEndOutlineProgress();
                if (i2 == bs2.size()) {
                    b2a = 1.0f;
                } else {
                    b2a = doubleMapper.mapBack(Utils.positiveModulo(b2.getEndOutlineProgress() + polygon2CutPoint, 1.0f));
                }
                float minb = Math.min(b1a, b2a);
                String access$getLOG_TAG$p2 = MorphKt.LOG_TAG;
                if (b1a > minb + 1.0E-6f) {
                    String access$getLOG_TAG$p3 = MorphKt.LOG_TAG;
                    b2a2 = b2a;
                    pair = b1.cutAtProgress(minb);
                } else {
                    b2a2 = b2a;
                    pair = TuplesKt.to(b1, CollectionsKt.getOrNull(bs1, i1));
                    i1++;
                }
                MeasuredPolygon.MeasuredCubic seg1 = pair.component1();
                MeasuredPolygon.MeasuredCubic newb12 = pair.component2();
                if (b2a2 > minb + 1.0E-6f) {
                    String access$getLOG_TAG$p4 = MorphKt.LOG_TAG;
                    float f = minb;
                    newb1 = newb12;
                    pair2 = b2.cutAtProgress(Utils.positiveModulo(doubleMapper.map(minb) - polygon2CutPoint, 1.0f));
                } else {
                    newb1 = newb12;
                    pair2 = TuplesKt.to(b2, CollectionsKt.getOrNull(bs2, i2));
                    i2++;
                }
                String access$getLOG_TAG$p5 = MorphKt.LOG_TAG;
                MeasuredPolygon.MeasuredCubic newb2 = pair2.component2();
                ret.add(TuplesKt.to(seg1.getCubic(), pair2.component1().getCubic()));
                b1 = newb1;
                b2 = newb2;
                RoundedPolygon roundedPolygon3 = p1;
                RoundedPolygon roundedPolygon4 = p2;
            }
            if (b1 == null && b2 == null) {
                return ret;
            }
            throw new IllegalArgumentException("Expected both Polygon's Cubic to be fully matched".toString());
        }
    }

    public final void forEachCubic(float progress, Function1<? super MutableCubic, Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        MutableCubic mutableCubic$iv = new MutableCubic();
        int size = getMorphMatch().size();
        for (int i$iv = 0; i$iv < size; i$iv++) {
            mutableCubic$iv.interpolate((Cubic) getMorphMatch().get(i$iv).getFirst(), (Cubic) getMorphMatch().get(i$iv).getSecond(), progress);
            callback.invoke(mutableCubic$iv);
        }
    }
}
