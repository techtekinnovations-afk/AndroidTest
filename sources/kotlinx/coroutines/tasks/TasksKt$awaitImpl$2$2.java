package kotlinx.coroutines.tasks;

import com.google.android.gms.tasks.CancellationTokenSource;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: Tasks.kt */
final class TasksKt$awaitImpl$2$2 implements Function1<Throwable, Unit> {
    final /* synthetic */ CancellationTokenSource $cancellationTokenSource;

    TasksKt$awaitImpl$2$2(CancellationTokenSource cancellationTokenSource) {
        this.$cancellationTokenSource = cancellationTokenSource;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object p1) {
        invoke((Throwable) p1);
        return Unit.INSTANCE;
    }

    public final void invoke(Throwable it) {
        this.$cancellationTokenSource.cancel();
    }
}
