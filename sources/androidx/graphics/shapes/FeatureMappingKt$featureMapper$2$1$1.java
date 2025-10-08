package androidx.graphics.shapes;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "i", "", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* compiled from: FeatureMapping.kt */
final class FeatureMappingKt$featureMapper$2$1$1 extends Lambda implements Function1<Integer, CharSequence> {
    final /* synthetic */ int $N;
    final /* synthetic */ DoubleMapper $dm;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FeatureMappingKt$featureMapper$2$1$1(DoubleMapper doubleMapper, int i) {
        super(1);
        this.$dm = doubleMapper;
        this.$N = i;
    }

    public final CharSequence invoke(int i) {
        return Format_jvmKt.toStringWithLessPrecision(this.$dm.map(((float) i) / ((float) this.$N)));
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
        return invoke(((Number) p1).intValue());
    }
}
