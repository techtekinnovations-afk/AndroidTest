package androidx.graphics.shapes;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0000\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "it", "Lkotlin/Pair;", "", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* compiled from: FeatureMapping.kt */
final class FeatureMappingKt$featureMapper$1$1 extends Lambda implements Function1<Pair<? extends Float, ? extends Float>, CharSequence> {
    public static final FeatureMappingKt$featureMapper$1$1 INSTANCE = new FeatureMappingKt$featureMapper$1$1();

    FeatureMappingKt$featureMapper$1$1() {
        super(1);
    }

    public final CharSequence invoke(Pair<Float, Float> it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it.getFirst().floatValue() + " -> " + it.getSecond().floatValue();
    }
}
