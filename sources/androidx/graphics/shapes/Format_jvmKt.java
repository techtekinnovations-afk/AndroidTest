package androidx.graphics.shapes;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\u0007\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0000¨\u0006\u0003"}, d2 = {"toStringWithLessPrecision", "", "", "graphics-shapes_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* compiled from: Format.jvm.kt */
public final class Format_jvmKt {
    public static final String toStringWithLessPrecision(float $this$toStringWithLessPrecision) {
        String format = String.format("%.3f", Arrays.copyOf(new Object[]{Float.valueOf($this$toStringWithLessPrecision)}, 1));
        Intrinsics.checkNotNullExpressionValue(format, "format(this, *args)");
        return format;
    }
}
