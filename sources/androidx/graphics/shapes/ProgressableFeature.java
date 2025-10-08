package androidx.graphics.shapes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Landroidx/graphics/shapes/ProgressableFeature;", "", "progress", "", "feature", "Landroidx/graphics/shapes/Feature;", "(FLandroidx/graphics/shapes/Feature;)V", "getFeature", "()Landroidx/graphics/shapes/Feature;", "getProgress", "()F", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "graphics-shapes_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: FeatureMapping.kt */
public final class ProgressableFeature {
    private final Feature feature;
    private final float progress;

    public static /* synthetic */ ProgressableFeature copy$default(ProgressableFeature progressableFeature, float f, Feature feature2, int i, Object obj) {
        if ((i & 1) != 0) {
            f = progressableFeature.progress;
        }
        if ((i & 2) != 0) {
            feature2 = progressableFeature.feature;
        }
        return progressableFeature.copy(f, feature2);
    }

    public final float component1() {
        return this.progress;
    }

    public final Feature component2() {
        return this.feature;
    }

    public final ProgressableFeature copy(float f, Feature feature2) {
        Intrinsics.checkNotNullParameter(feature2, "feature");
        return new ProgressableFeature(f, feature2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ProgressableFeature)) {
            return false;
        }
        ProgressableFeature progressableFeature = (ProgressableFeature) obj;
        return Float.compare(this.progress, progressableFeature.progress) == 0 && Intrinsics.areEqual((Object) this.feature, (Object) progressableFeature.feature);
    }

    public int hashCode() {
        return (Float.hashCode(this.progress) * 31) + this.feature.hashCode();
    }

    public String toString() {
        return "ProgressableFeature(progress=" + this.progress + ", feature=" + this.feature + ')';
    }

    public ProgressableFeature(float progress2, Feature feature2) {
        Intrinsics.checkNotNullParameter(feature2, "feature");
        this.progress = progress2;
        this.feature = feature2;
    }

    public final Feature getFeature() {
        return this.feature;
    }

    public final float getProgress() {
        return this.progress;
    }
}
