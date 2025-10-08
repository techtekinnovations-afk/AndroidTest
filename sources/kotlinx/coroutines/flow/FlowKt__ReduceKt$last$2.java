package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Ref;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: Reduce.kt */
final class FlowKt__ReduceKt$last$2<T> implements FlowCollector {
    final /* synthetic */ Ref.ObjectRef<Object> $result;

    FlowKt__ReduceKt$last$2(Ref.ObjectRef<Object> objectRef) {
        this.$result = objectRef;
    }

    public final Object emit(T it, Continuation<? super Unit> $completion) {
        this.$result.element = it;
        return Unit.INSTANCE;
    }
}
