package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.flow.internal.NullSurrogateKt;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: Reduce.kt */
final class FlowKt__ReduceKt$single$2<T> implements FlowCollector {
    final /* synthetic */ Ref.ObjectRef<Object> $result;

    FlowKt__ReduceKt$single$2(Ref.ObjectRef<Object> objectRef) {
        this.$result = objectRef;
    }

    public final Object emit(T value, Continuation<? super Unit> $completion) {
        if (this.$result.element == NullSurrogateKt.NULL) {
            this.$result.element = value;
            return Unit.INSTANCE;
        }
        throw new IllegalArgumentException("Flow has more than one element".toString());
    }
}
