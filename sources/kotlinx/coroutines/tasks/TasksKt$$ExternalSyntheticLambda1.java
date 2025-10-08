package kotlinx.coroutines.tasks;

import com.google.android.gms.tasks.CancellationTokenSource;
import kotlin.jvm.functions.Function1;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class TasksKt$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ CancellationTokenSource f$0;

    public /* synthetic */ TasksKt$$ExternalSyntheticLambda1(CancellationTokenSource cancellationTokenSource) {
        this.f$0 = cancellationTokenSource;
    }

    public final Object invoke(Object obj) {
        return TasksKt.asDeferredImpl$lambda$2(this.f$0, (Throwable) obj);
    }
}
