package kotlinx.coroutines.tasks;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlinx.coroutines.CancellableContinuation;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: Tasks.kt */
final class TasksKt$awaitImpl$2$1<TResult> implements OnCompleteListener {
    final /* synthetic */ CancellableContinuation<T> $cont;

    TasksKt$awaitImpl$2$1(CancellableContinuation<? super T> cancellableContinuation) {
        this.$cont = cancellableContinuation;
    }

    public final void onComplete(Task<T> it) {
        Exception e = it.getException();
        if (e != null) {
            Result.Companion companion = Result.Companion;
            this.$cont.resumeWith(Result.m6constructorimpl(ResultKt.createFailure(e)));
        } else if (it.isCanceled()) {
            CancellableContinuation.DefaultImpls.cancel$default(this.$cont, (Throwable) null, 1, (Object) null);
        } else {
            Result.Companion companion2 = Result.Companion;
            this.$cont.resumeWith(Result.m6constructorimpl(it.getResult()));
        }
    }
}
