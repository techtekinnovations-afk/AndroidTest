package androidx.datastore.preferences.core;

import androidx.datastore.preferences.core.Preferences;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0010'\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\u0010\u0000\u001a\u00020\u00012\u0016\u0010\u0002\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012\u0004\u0012\u00020\u00050\u0003H\n¢\u0006\u0002\b\u0006"}, d2 = {"<anonymous>", "", "entry", "", "Landroidx/datastore/preferences/core/Preferences$Key;", "", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
/* compiled from: Preferences.kt */
final class MutablePreferences$toString$1 extends Lambda implements Function1<Map.Entry<Preferences.Key<?>, Object>, CharSequence> {
    public static final MutablePreferences$toString$1 INSTANCE = new MutablePreferences$toString$1();

    MutablePreferences$toString$1() {
        super(1);
    }

    public final CharSequence invoke(Map.Entry<Preferences.Key<?>, Object> entry) {
        String value;
        Intrinsics.checkNotNullParameter(entry, "entry");
        Object value2 = entry.getValue();
        if (value2 instanceof byte[]) {
            value = ArraysKt.joinToString$default((byte[]) value2, (CharSequence) ", ", (CharSequence) "[", (CharSequence) "]", 0, (CharSequence) null, (Function1) null, 56, (Object) null);
        } else {
            value = String.valueOf(entry.getValue());
        }
        return "  " + entry.getKey().getName() + " = " + value;
    }
}
