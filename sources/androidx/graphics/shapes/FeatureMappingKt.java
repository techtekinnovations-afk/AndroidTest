package androidx.graphics.shapes;

import androidx.graphics.shapes.Feature;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;

@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a6\u0010\u0002\u001a\f\u0012\u0004\u0012\u00020\u00040\u0003j\u0002`\u00052\u0010\u0010\u0006\u001a\f\u0012\u0004\u0012\u00020\u00040\u0003j\u0002`\u00052\u0010\u0010\u0007\u001a\f\u0012\u0004\u0012\u00020\u00040\u0003j\u0002`\u0005H\u0000\u001a\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\nH\u0000\u001a,\u0010\u000b\u001a\u00020\f2\u0010\u0010\r\u001a\f\u0012\u0004\u0012\u00020\u00040\u0003j\u0002`\u00052\u0010\u0010\u000e\u001a\f\u0012\u0004\u0012\u00020\u00040\u0003j\u0002`\u0005H\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001XD¢\u0006\u0002\n\u0000*\u0018\b\u0000\u0010\u000f\"\b\u0012\u0004\u0012\u00020\u00040\u00032\b\u0012\u0004\u0012\u00020\u00040\u0003¨\u0006\u0010"}, d2 = {"LOG_TAG", "", "doMapping", "", "Landroidx/graphics/shapes/ProgressableFeature;", "Landroidx/graphics/shapes/MeasuredFeatures;", "f1", "f2", "featureDistSquared", "", "Landroidx/graphics/shapes/Feature;", "featureMapper", "Landroidx/graphics/shapes/DoubleMapper;", "features1", "features2", "MeasuredFeatures", "graphics-shapes_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* compiled from: FeatureMapping.kt */
public final class FeatureMappingKt {
    private static final String LOG_TAG = "FeatureMapping";

    public static final DoubleMapper featureMapper(List<ProgressableFeature> features1, List<ProgressableFeature> features2) {
        Pair<A, B> pair;
        Intrinsics.checkNotNullParameter(features1, "features1");
        Intrinsics.checkNotNullParameter(features2, "features2");
        List createListBuilder = CollectionsKt.createListBuilder();
        List $this$featureMapper_u24lambda_u240 = createListBuilder;
        int size = features1.size();
        for (int i = 0; i < size; i++) {
            if (features1.get(i).getFeature() instanceof Feature.Corner) {
                $this$featureMapper_u24lambda_u240.add(features1.get(i));
            }
        }
        List filteredFeatures1 = CollectionsKt.build(createListBuilder);
        List createListBuilder2 = CollectionsKt.createListBuilder();
        List $this$featureMapper_u24lambda_u241 = createListBuilder2;
        int size2 = features2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            if (features2.get(i2).getFeature() instanceof Feature.Corner) {
                $this$featureMapper_u24lambda_u241.add(features2.get(i2));
            }
        }
        List filteredFeatures2 = CollectionsKt.build(createListBuilder2);
        if (filteredFeatures1.size() > filteredFeatures2.size()) {
            pair = TuplesKt.to(doMapping(filteredFeatures2, filteredFeatures1), filteredFeatures2);
        } else {
            pair = TuplesKt.to(filteredFeatures1, doMapping(filteredFeatures1, filteredFeatures2));
        }
        List m1 = (List) pair.component1();
        List m2 = (List) pair.component2();
        List createListBuilder3 = CollectionsKt.createListBuilder();
        List $this$featureMapper_u24lambda_u242 = createListBuilder3;
        int i3 = 0;
        int size3 = m1.size();
        while (i3 < size3 && i3 != m2.size()) {
            $this$featureMapper_u24lambda_u242.add(TuplesKt.to(Float.valueOf(((ProgressableFeature) m1.get(i3)).getProgress()), Float.valueOf(((ProgressableFeature) m2.get(i3)).getProgress())));
            i3++;
        }
        List mm = CollectionsKt.build(createListBuilder3);
        String str = LOG_TAG;
        Pair[] pairArr = (Pair[]) mm.toArray(new Pair[0]);
        DoubleMapper doubleMapper = new DoubleMapper((Pair[]) Arrays.copyOf(pairArr, pairArr.length));
        DoubleMapper doubleMapper2 = doubleMapper;
        String str2 = LOG_TAG;
        return doubleMapper;
    }

    public static final float featureDistSquared(Feature f1, Feature f2) {
        Intrinsics.checkNotNullParameter(f1, "f1");
        Intrinsics.checkNotNullParameter(f2, "f2");
        if (!(f1 instanceof Feature.Corner) || !(f2 instanceof Feature.Corner) || ((Feature.Corner) f1).getConvex() == ((Feature.Corner) f2).getConvex()) {
            float dx = ((((Cubic) CollectionsKt.first(f1.getCubics())).getAnchor0X() + ((Cubic) CollectionsKt.last(f1.getCubics())).getAnchor1X()) / 2.0f) - ((((Cubic) CollectionsKt.first(f2.getCubics())).getAnchor0X() + ((Cubic) CollectionsKt.last(f2.getCubics())).getAnchor1X()) / 2.0f);
            float dy = ((((Cubic) CollectionsKt.first(f1.getCubics())).getAnchor0Y() + ((Cubic) CollectionsKt.last(f1.getCubics())).getAnchor1Y()) / 2.0f) - ((((Cubic) CollectionsKt.first(f2.getCubics())).getAnchor0Y() + ((Cubic) CollectionsKt.last(f2.getCubics())).getAnchor1Y()) / 2.0f);
            return (dx * dx) + (dy * dy);
        }
        String str = LOG_TAG;
        return Float.MAX_VALUE;
    }

    public static final List<ProgressableFeature> doMapping(List<ProgressableFeature> f1, List<ProgressableFeature> f2) {
        int n;
        int m;
        List<ProgressableFeature> list = f1;
        List<ProgressableFeature> list2 = f2;
        Intrinsics.checkNotNullParameter(list, "f1");
        Intrinsics.checkNotNullParameter(list2, "f2");
        Iterator iterator$iv = CollectionsKt.getIndices(list2).iterator();
        if (iterator$iv.hasNext()) {
            int minElem$iv = ((IntIterator) iterator$iv).nextInt();
            if (iterator$iv.hasNext()) {
                float minValue$iv = featureDistSquared(list.get(0).getFeature(), list2.get(minElem$iv).getFeature());
                while (true) {
                    int e$iv = ((IntIterator) iterator$iv).nextInt();
                    float v$iv = featureDistSquared(list.get(0).getFeature(), list2.get(e$iv).getFeature());
                    if (Float.compare(minValue$iv, v$iv) > 0) {
                        minElem$iv = e$iv;
                        minValue$iv = v$iv;
                    }
                    if (iterator$iv.hasNext() == 0) {
                        break;
                    }
                    list = f1;
                }
            }
            int m2 = list.size();
            int n2 = list2.size();
            List ret = CollectionsKt.mutableListOf(list2.get(minElem$iv));
            int lastPicked = minElem$iv;
            int i = 1;
            while (i < m2) {
                int it = minElem$iv - (m2 - i);
                if (it <= lastPicked) {
                    it += n2;
                }
                Iterator iterator$iv2 = new IntRange(lastPicked + 1, it).iterator();
                if (iterator$iv2.hasNext()) {
                    int minElem$iv2 = ((IntIterator) iterator$iv2).nextInt();
                    if (!iterator$iv2.hasNext()) {
                        m = m2;
                        n = n2;
                    } else {
                        m = m2;
                        float minValue$iv2 = featureDistSquared(list.get(i).getFeature(), list2.get(minElem$iv2 % n2).getFeature());
                        while (true) {
                            int e$iv2 = ((IntIterator) iterator$iv2).nextInt();
                            n = n2;
                            float v$iv2 = featureDistSquared(list.get(i).getFeature(), list2.get(e$iv2 % n).getFeature());
                            if (Float.compare(minValue$iv2, v$iv2) > 0) {
                                minValue$iv2 = v$iv2;
                                minElem$iv2 = e$iv2;
                            }
                            if (!iterator$iv2.hasNext()) {
                                break;
                            }
                            list = f1;
                            n2 = n;
                        }
                    }
                    ret.add(list2.get(minElem$iv2 % n));
                    lastPicked = minElem$iv2;
                    i++;
                    list = f1;
                    m2 = m;
                    n2 = n;
                } else {
                    throw new NoSuchElementException();
                }
            }
            return ret;
        }
        throw new NoSuchElementException();
    }
}
