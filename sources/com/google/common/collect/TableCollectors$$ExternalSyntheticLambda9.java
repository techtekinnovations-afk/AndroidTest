package com.google.common.collect;

import com.google.common.collect.ImmutableTable;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class TableCollectors$$ExternalSyntheticLambda9 implements BiConsumer {
    public final /* synthetic */ Function f$0;
    public final /* synthetic */ Function f$1;
    public final /* synthetic */ Function f$2;

    public /* synthetic */ TableCollectors$$ExternalSyntheticLambda9(Function function, Function function2, Function function3) {
        this.f$0 = function;
        this.f$1 = function2;
        this.f$2 = function3;
    }

    public final void accept(Object obj, Object obj2) {
        ((ImmutableTable.Builder) obj).put(this.f$0.apply(obj2), this.f$1.apply(obj2), this.f$2.apply(obj2));
    }
}
