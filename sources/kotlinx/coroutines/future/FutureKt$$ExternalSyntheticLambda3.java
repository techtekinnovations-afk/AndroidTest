package kotlinx.coroutines.future;

import java.util.concurrent.CompletableFuture;
import kotlin.jvm.functions.Function1;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FutureKt$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ CompletableFuture f$0;

    public /* synthetic */ FutureKt$$ExternalSyntheticLambda3(CompletableFuture completableFuture) {
        this.f$0 = completableFuture;
    }

    public final Object invoke(Object obj) {
        return FutureKt.asCompletableFuture$lambda$2(this.f$0, (Throwable) obj);
    }
}
