package kotlinx.coroutines.tasks;

import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.TaskCompletionSource;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.Deferred;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class TasksKt$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ CancellationTokenSource f$0;
    public final /* synthetic */ Deferred f$1;
    public final /* synthetic */ TaskCompletionSource f$2;

    public /* synthetic */ TasksKt$$ExternalSyntheticLambda2(CancellationTokenSource cancellationTokenSource, Deferred deferred, TaskCompletionSource taskCompletionSource) {
        this.f$0 = cancellationTokenSource;
        this.f$1 = deferred;
        this.f$2 = taskCompletionSource;
    }

    public final Object invoke(Object obj) {
        return TasksKt.asTask$lambda$0(this.f$0, this.f$1, this.f$2, (Throwable) obj);
    }
}
