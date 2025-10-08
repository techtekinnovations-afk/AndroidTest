package kotlinx.coroutines.future;

import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: Future.kt */
final class FutureKt$await$2$1 implements Function1<Throwable, Unit> {
    final /* synthetic */ ContinuationHandler<T> $consumer;
    final /* synthetic */ CompletableFuture<T> $future;

    FutureKt$await$2$1(CompletableFuture<T> completableFuture, ContinuationHandler<T> continuationHandler) {
        this.$future = completableFuture;
        this.$consumer = continuationHandler;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
        invoke((Throwable) p1);
        return Unit.INSTANCE;
    }

    public final void invoke(Throwable it) {
        this.$future.cancel(false);
        this.$consumer.cont = null;
    }
}
