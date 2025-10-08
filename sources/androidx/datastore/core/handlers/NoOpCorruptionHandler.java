package androidx.datastore.core.handlers;

import androidx.datastore.core.CorruptionException;
import androidx.datastore.core.CorruptionHandler;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;

@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u0006H@¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Landroidx/datastore/core/handlers/NoOpCorruptionHandler;", "T", "Landroidx/datastore/core/CorruptionHandler;", "()V", "handleCorruption", "ex", "Landroidx/datastore/core/CorruptionException;", "(Landroidx/datastore/core/CorruptionException;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "datastore-core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: NoOpCorruptionHandler.jvm.kt */
public final class NoOpCorruptionHandler<T> implements CorruptionHandler<T> {
    public Object handleCorruption(CorruptionException ex, Continuation<? super T> $completion) throws CorruptionException {
        throw ex;
    }
}
