package kotlinx.coroutines.future;

import java.util.concurrent.CompletableFuture;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.Deferred;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FutureKt$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ CompletableFuture f$0;
    public final /* synthetic */ Deferred f$1;

    public /* synthetic */ FutureKt$$ExternalSyntheticLambda0(CompletableFuture completableFuture, Deferred deferred) {
        this.f$0 = completableFuture;
        this.f$1 = deferred;
    }

    public final Object invoke(Object obj) {
        return FutureKt.asCompletableFuture$lambda$1(this.f$0, this.f$1, (Throwable) obj);
    }
}
