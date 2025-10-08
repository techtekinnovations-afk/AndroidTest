package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: Collection.kt */
final class FlowKt__CollectionKt$toCollection$2<T> implements FlowCollector {
    final /* synthetic */ C $destination;

    FlowKt__CollectionKt$toCollection$2(C c) {
        this.$destination = c;
    }

    public final Object emit(T value, Continuation<? super Unit> $completion) {
        this.$destination.add(value);
        return Unit.INSTANCE;
    }
}
