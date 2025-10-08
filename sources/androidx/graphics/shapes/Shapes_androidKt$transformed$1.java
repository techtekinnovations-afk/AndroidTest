package androidx.graphics.shapes;

import android.graphics.Matrix;
import androidx.collection.FloatFloatPair;
import kotlin.Metadata;

@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\u0010\u0000\u001a\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, d2 = {"<anonymous>", "Landroidx/collection/FloatFloatPair;", "Landroidx/graphics/shapes/TransformResult;", "x", "", "y", "transform-XgqJiTY", "(FF)J"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* compiled from: Shapes.android.kt */
final class Shapes_androidKt$transformed$1 implements PointTransformer {
    final /* synthetic */ Matrix $matrix;
    final /* synthetic */ float[] $tempArray;

    Shapes_androidKt$transformed$1(float[] fArr, Matrix matrix) {
        this.$tempArray = fArr;
        this.$matrix = matrix;
    }

    /* renamed from: transform-XgqJiTY  reason: not valid java name */
    public final long m2063transformXgqJiTY(float x, float y) {
        this.$tempArray[0] = x;
        this.$tempArray[1] = y;
        this.$matrix.mapPoints(this.$tempArray);
        return FloatFloatPair.m1964constructorimpl(this.$tempArray[0], this.$tempArray[1]);
    }
}
