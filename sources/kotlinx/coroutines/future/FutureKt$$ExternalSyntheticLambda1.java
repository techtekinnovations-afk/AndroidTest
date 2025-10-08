package kotlinx.coroutines.future;

import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CompletableDeferred;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FutureKt$$ExternalSyntheticLambda1 implements Function2 {
    public final /* synthetic */ CompletableDeferred f$0;

    public /* synthetic */ FutureKt$$ExternalSyntheticLambda1(CompletableDeferred completableDeferred) {
        this.f$0 = completableDeferred;
    }

    public final Object invoke(Object obj, Object obj2) {
        return FutureKt.asDeferred$lambda$5(this.f$0, obj, (Throwable) obj2);
    }
}
