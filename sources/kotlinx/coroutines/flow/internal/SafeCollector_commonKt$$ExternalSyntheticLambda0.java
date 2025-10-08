package kotlinx.coroutines.flow.internal;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SafeCollector_commonKt$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ SafeCollector f$0;

    public /* synthetic */ SafeCollector_commonKt$$ExternalSyntheticLambda0(SafeCollector safeCollector) {
        this.f$0 = safeCollector;
    }

    public final Object invoke(Object obj, Object obj2) {
        return Integer.valueOf(SafeCollector_commonKt.checkContext$lambda$0(this.f$0, ((Integer) obj).intValue(), (CoroutineContext.Element) obj2));
    }
}
