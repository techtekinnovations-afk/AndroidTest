package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Ref;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: Count.kt */
final class FlowKt__CountKt$count$2<T> implements FlowCollector {
    final /* synthetic */ Ref.IntRef $i;

    FlowKt__CountKt$count$2(Ref.IntRef intRef) {
        this.$i = intRef;
    }

    public final Object emit(T it, Continuation<? super Unit> $completion) {
        this.$i.element++;
        int i = this.$i.element;
        return Unit.INSTANCE;
    }
}
