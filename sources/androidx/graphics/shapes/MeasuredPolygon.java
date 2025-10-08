package androidx.graphics.shapes;

import androidx.collection.FloatList;
import androidx.collection.MutableFloatList;
import androidx.graphics.shapes.Feature;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.AbstractList;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0005\b\u0000\u0018\u0000 \u00182\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0002\u0018\u0019B3\b\u0012\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u000e\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u0015J\u0015\u0010\u0016\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0017\u001a\u00020\u0010H\u0002R\u0018\u0010\b\u001a\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0006X\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\u00108VX\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001a"}, d2 = {"Landroidx/graphics/shapes/MeasuredPolygon;", "Lkotlin/collections/AbstractList;", "Landroidx/graphics/shapes/MeasuredPolygon$MeasuredCubic;", "measurer", "Landroidx/graphics/shapes/Measurer;", "features", "", "Landroidx/graphics/shapes/ProgressableFeature;", "cubics", "Landroidx/graphics/shapes/Cubic;", "outlineProgress", "Landroidx/collection/FloatList;", "(Landroidx/graphics/shapes/Measurer;Ljava/util/List;Ljava/util/List;Landroidx/collection/FloatList;)V", "getFeatures", "()Ljava/util/List;", "size", "", "getSize", "()I", "cutAndShift", "cuttingPoint", "", "get", "index", "Companion", "MeasuredCubic", "graphics-shapes_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: PolygonMeasure.kt */
public final class MeasuredPolygon extends AbstractList<MeasuredCubic> {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private final List<MeasuredCubic> cubics;
    private final List<ProgressableFeature> features;
    /* access modifiers changed from: private */
    public final Measurer measurer;

    public /* synthetic */ MeasuredPolygon(Measurer measurer2, List list, List list2, FloatList floatList, DefaultConstructorMarker defaultConstructorMarker) {
        this(measurer2, list, list2, floatList);
    }

    public /* bridge */ boolean contains(MeasuredCubic element) {
        return super.contains(element);
    }

    public final /* bridge */ boolean contains(Object element) {
        if (!(element instanceof MeasuredCubic)) {
            return false;
        }
        return contains((MeasuredCubic) element);
    }

    public /* bridge */ int indexOf(MeasuredCubic element) {
        return super.indexOf(element);
    }

    public final /* bridge */ int indexOf(Object element) {
        if (!(element instanceof MeasuredCubic)) {
            return -1;
        }
        return indexOf((MeasuredCubic) element);
    }

    public /* bridge */ int lastIndexOf(MeasuredCubic element) {
        return super.lastIndexOf(element);
    }

    public final /* bridge */ int lastIndexOf(Object element) {
        if (!(element instanceof MeasuredCubic)) {
            return -1;
        }
        return lastIndexOf((MeasuredCubic) element);
    }

    public final List<ProgressableFeature> getFeatures() {
        return this.features;
    }

    private MeasuredPolygon(Measurer measurer2, List<ProgressableFeature> features2, List<? extends Cubic> cubics2, FloatList outlineProgress) {
        boolean z = false;
        if (outlineProgress.getSize() == cubics2.size() + 1) {
            if (outlineProgress.first() == 0.0f) {
                if (outlineProgress.last() == 1.0f ? true : z) {
                    this.measurer = measurer2;
                    this.features = features2;
                    List measuredCubics = new ArrayList();
                    float startOutlineProgress = 0.0f;
                    int size = cubics2.size();
                    for (int index = 0; index < size; index++) {
                        if (outlineProgress.get(index + 1) - outlineProgress.get(index) > 1.0E-4f) {
                            measuredCubics.add(new MeasuredCubic(this, (Cubic) cubics2.get(index), startOutlineProgress, outlineProgress.get(index + 1)));
                            startOutlineProgress = outlineProgress.get(index + 1);
                        }
                    }
                    MeasuredCubic.updateProgressRange$graphics_shapes_release$default(measuredCubics.get(CollectionsKt.getLastIndex(measuredCubics)), 0.0f, 1.0f, 1, (Object) null);
                    this.cubics = measuredCubics;
                    return;
                }
                throw new IllegalArgumentException("Last outline progress value is expected to be one".toString());
            }
            throw new IllegalArgumentException("First outline progress value is expected to be zero".toString());
        }
        throw new IllegalArgumentException("Outline progress size is expected to be the cubics size + 1".toString());
    }

    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0004\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\"\u0010\u0010\u001a\u0016\u0012\b\u0012\u00060\u0000R\u00020\u0012\u0012\b\u0012\u00060\u0000R\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u0005J\b\u0010\u0014\u001a\u00020\u0015H\u0016J!\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005H\u0000¢\u0006\u0002\b\u0018R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001e\u0010\u0006\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u0005@BX\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u001e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u0005@BX\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\f¨\u0006\u0019"}, d2 = {"Landroidx/graphics/shapes/MeasuredPolygon$MeasuredCubic;", "", "cubic", "Landroidx/graphics/shapes/Cubic;", "startOutlineProgress", "", "endOutlineProgress", "(Landroidx/graphics/shapes/MeasuredPolygon;Landroidx/graphics/shapes/Cubic;FF)V", "getCubic", "()Landroidx/graphics/shapes/Cubic;", "<set-?>", "getEndOutlineProgress", "()F", "measuredSize", "getMeasuredSize", "getStartOutlineProgress", "cutAtProgress", "Lkotlin/Pair;", "Landroidx/graphics/shapes/MeasuredPolygon;", "cutOutlineProgress", "toString", "", "updateProgressRange", "", "updateProgressRange$graphics_shapes_release", "graphics-shapes_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* compiled from: PolygonMeasure.kt */
    public final class MeasuredCubic {
        private final Cubic cubic;
        private float endOutlineProgress;
        private final float measuredSize;
        private float startOutlineProgress;
        final /* synthetic */ MeasuredPolygon this$0;

        public MeasuredCubic(MeasuredPolygon this$02, Cubic cubic2, float startOutlineProgress2, float endOutlineProgress2) {
            Intrinsics.checkNotNullParameter(cubic2, "cubic");
            this.this$0 = this$02;
            this.cubic = cubic2;
            if (endOutlineProgress2 >= startOutlineProgress2) {
                this.measuredSize = this.this$0.measurer.measureCubic(this.cubic);
                this.startOutlineProgress = startOutlineProgress2;
                this.endOutlineProgress = endOutlineProgress2;
                return;
            }
            throw new IllegalArgumentException("endOutlineProgress is expected to be equal or greater than startOutlineProgress".toString());
        }

        public final Cubic getCubic() {
            return this.cubic;
        }

        public final float getMeasuredSize() {
            return this.measuredSize;
        }

        public final float getStartOutlineProgress() {
            return this.startOutlineProgress;
        }

        public final float getEndOutlineProgress() {
            return this.endOutlineProgress;
        }

        public static /* synthetic */ void updateProgressRange$graphics_shapes_release$default(MeasuredCubic measuredCubic, float f, float f2, int i, Object obj) {
            if ((i & 1) != 0) {
                f = measuredCubic.startOutlineProgress;
            }
            if ((i & 2) != 0) {
                f2 = measuredCubic.endOutlineProgress;
            }
            measuredCubic.updateProgressRange$graphics_shapes_release(f, f2);
        }

        public final void updateProgressRange$graphics_shapes_release(float startOutlineProgress2, float endOutlineProgress2) {
            if (endOutlineProgress2 >= startOutlineProgress2) {
                this.startOutlineProgress = startOutlineProgress2;
                this.endOutlineProgress = endOutlineProgress2;
                return;
            }
            throw new IllegalArgumentException("endOutlineProgress is expected to be equal or greater than startOutlineProgress".toString());
        }

        public final Pair<MeasuredCubic, MeasuredCubic> cutAtProgress(float cutOutlineProgress) {
            float boundedCutOutlineProgress = RangesKt.coerceIn(cutOutlineProgress, this.startOutlineProgress, this.endOutlineProgress);
            float outlineProgressSize = this.endOutlineProgress - this.startOutlineProgress;
            float t = this.this$0.measurer.findCubicCutPoint(this.cubic, this.measuredSize * ((boundedCutOutlineProgress - this.startOutlineProgress) / outlineProgressSize));
            boolean z = false;
            if (0.0f <= t && t <= 1.0f) {
                z = true;
            }
            if (z) {
                String access$getLOG_TAG$p = PolygonMeasureKt.LOG_TAG;
                Pair<Cubic, Cubic> split = this.cubic.split(t);
                return TuplesKt.to(new MeasuredCubic(this.this$0, split.component1(), this.startOutlineProgress, boundedCutOutlineProgress), new MeasuredCubic(this.this$0, split.component2(), boundedCutOutlineProgress, this.endOutlineProgress));
            }
            throw new IllegalArgumentException("Cubic cut point is expected to be between 0 and 1".toString());
        }

        public String toString() {
            return "MeasuredCubic(outlineProgress=[" + this.startOutlineProgress + " .. " + this.endOutlineProgress + "], size=" + this.measuredSize + ", cubic=" + this.cubic + ')';
        }
    }

    public final MeasuredPolygon cutAndShift(float cuttingPoint) {
        float f;
        float f2 = cuttingPoint;
        float f3 = 1.0f;
        if (!(0.0f <= f2 && f2 <= 1.0f)) {
            throw new IllegalArgumentException("Cutting point is expected to be between 0 and 1".toString());
        } else if (f2 < 1.0E-4f) {
            return this;
        } else {
            int index$iv = 0;
            Iterator<MeasuredCubic> it = this.cubics.iterator();
            while (true) {
                if (!it.hasNext()) {
                    index$iv = -1;
                    break;
                }
                MeasuredCubic it2 = it.next();
                if (((f2 > it2.getEndOutlineProgress() || it2.getStartOutlineProgress() > f2) ? null : 1) != null) {
                    break;
                }
                index$iv++;
            }
            Pair<MeasuredCubic, MeasuredCubic> cutAtProgress = this.cubics.get(index$iv).cutAtProgress(f2);
            MeasuredCubic b1 = cutAtProgress.component1();
            String access$getLOG_TAG$p = PolygonMeasureKt.LOG_TAG;
            List retCubics = CollectionsKt.mutableListOf(cutAtProgress.component2().getCubic());
            int size = this.cubics.size();
            for (int i = 1; i < size; i++) {
                retCubics.add(this.cubics.get((i + index$iv) % this.cubics.size()).getCubic());
            }
            retCubics.add(b1.getCubic());
            MutableFloatList retOutlineProgress = new MutableFloatList(this.cubics.size() + 2);
            int size2 = this.cubics.size() + 2;
            for (int index = 0; index < size2; index++) {
                if (index == 0) {
                    f = 0.0f;
                } else if (index == this.cubics.size() + 1) {
                    f = 1.0f;
                } else {
                    f = Utils.positiveModulo(this.cubics.get(((index$iv + index) - 1) % this.cubics.size()).getEndOutlineProgress() - f2, 1.0f);
                }
                retOutlineProgress.add(f);
            }
            List createListBuilder = CollectionsKt.createListBuilder();
            List $this$cutAndShift_u24lambda_u2410 = createListBuilder;
            int i2 = 0;
            int size3 = this.features.size();
            while (i2 < size3) {
                $this$cutAndShift_u24lambda_u2410.add(new ProgressableFeature(Utils.positiveModulo(this.features.get(i2).getProgress() - f2, f3), this.features.get(i2).getFeature()));
                i2++;
                f3 = 1.0f;
            }
            return new MeasuredPolygon(this.measurer, CollectionsKt.build(createListBuilder), retCubics, retOutlineProgress);
        }
    }

    public int getSize() {
        return this.cubics.size();
    }

    public MeasuredCubic get(int index) {
        return this.cubics.get(index);
    }

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001d\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0000¢\u0006\u0002\b\t¨\u0006\n"}, d2 = {"Landroidx/graphics/shapes/MeasuredPolygon$Companion;", "", "()V", "measurePolygon", "Landroidx/graphics/shapes/MeasuredPolygon;", "measurer", "Landroidx/graphics/shapes/Measurer;", "polygon", "Landroidx/graphics/shapes/RoundedPolygon;", "measurePolygon$graphics_shapes_release", "graphics-shapes_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* compiled from: PolygonMeasure.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final MeasuredPolygon measurePolygon$graphics_shapes_release(Measurer measurer, RoundedPolygon polygon) {
            List list;
            Measurer measurer2 = measurer;
            Intrinsics.checkNotNullParameter(measurer2, "measurer");
            RoundedPolygon roundedPolygon = polygon;
            Intrinsics.checkNotNullParameter(roundedPolygon, "polygon");
            List cubics = new ArrayList();
            List featureToCubic = new ArrayList();
            int size = roundedPolygon.getFeatures$graphics_shapes_release().size();
            for (int featureIndex = 0; featureIndex < size; featureIndex++) {
                Feature feature = roundedPolygon.getFeatures$graphics_shapes_release().get(featureIndex);
                int size2 = feature.getCubics().size();
                for (int cubicIndex = 0; cubicIndex < size2; cubicIndex++) {
                    if ((feature instanceof Feature.Corner) && cubicIndex == feature.getCubics().size() / 2) {
                        featureToCubic.add(TuplesKt.to(feature, Integer.valueOf(cubics.size())));
                    }
                    cubics.add(feature.getCubics().get(cubicIndex));
                }
            }
            float f = 0.0f;
            Object initial$iv = Float.valueOf(0.0f);
            Iterable<Cubic> $this$runningFold$iv$iv = cubics;
            int estimatedSize$iv$iv = CollectionsKt.collectionSizeOrDefault($this$runningFold$iv$iv, 9);
            if (estimatedSize$iv$iv == 0) {
                list = CollectionsKt.listOf(initial$iv);
            } else {
                ArrayList result$iv$iv = new ArrayList(estimatedSize$iv$iv + 1);
                result$iv$iv.add(initial$iv);
                Object accumulator$iv$iv = initial$iv;
                for (Cubic cubic : $this$runningFold$iv$iv) {
                    float f2 = f;
                    float measure = ((Number) accumulator$iv$iv).floatValue();
                    float it = measurer2.measureCubic(cubic);
                    if (it >= f2) {
                        Unit unit = Unit.INSTANCE;
                        accumulator$iv$iv = Float.valueOf(measure + it);
                        result$iv$iv.add(accumulator$iv$iv);
                        f = f2;
                    } else {
                        throw new IllegalArgumentException("Measured cubic is expected to be greater or equal to zero".toString());
                    }
                }
                list = result$iv$iv;
            }
            List measures = list;
            float totalMeasure = ((Number) CollectionsKt.last(measures)).floatValue();
            MutableFloatList outlineProgress = new MutableFloatList(measures.size());
            int size3 = measures.size();
            for (int i = 0; i < size3; i++) {
                outlineProgress.add(((Number) measures.get(i)).floatValue() / totalMeasure);
            }
            String access$getLOG_TAG$p = PolygonMeasureKt.LOG_TAG;
            List createListBuilder = CollectionsKt.createListBuilder();
            List $this$measurePolygon_u24lambda_u244 = createListBuilder;
            int i2 = 0;
            int size4 = featureToCubic.size();
            while (i2 < size4) {
                int ix = ((Number) ((Pair) featureToCubic.get(i2)).getSecond()).intValue();
                $this$measurePolygon_u24lambda_u244.add(new ProgressableFeature((outlineProgress.get(ix) + outlineProgress.get(ix + 1)) / ((float) 2), (Feature) ((Pair) featureToCubic.get(i2)).getFirst()));
                i2++;
                createListBuilder = createListBuilder;
            }
            return new MeasuredPolygon(measurer2, CollectionsKt.build(createListBuilder), cubics, outlineProgress, (DefaultConstructorMarker) null);
        }
    }
}
