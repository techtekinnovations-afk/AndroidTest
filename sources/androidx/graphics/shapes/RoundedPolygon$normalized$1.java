package androidx.graphics.shapes;

import androidx.collection.FloatFloatPair;
import kotlin.Metadata;

@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0010\u0000\u001a\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\n"}, d2 = {"<anonymous>", "Landroidx/collection/FloatFloatPair;", "Landroidx/graphics/shapes/TransformResult;", "x", "", "y"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* compiled from: RoundedPolygon.kt */
final class RoundedPolygon$normalized$1 implements PointTransformer {
    final /* synthetic */ float $offsetX;
    final /* synthetic */ float $offsetY;
    final /* synthetic */ float $side;

    RoundedPolygon$normalized$1(float f, float f2, float f3) {
        this.$offsetX = f;
        this.$side = f2;
        this.$offsetY = f3;
    }

    /* renamed from: transform-XgqJiTY  reason: not valid java name */
    public final long m2062transformXgqJiTY(float x, float y) {
        return FloatFloatPair.m1964constructorimpl((this.$offsetX + x) / this.$side, (this.$offsetY + y) / this.$side);
    }
}
