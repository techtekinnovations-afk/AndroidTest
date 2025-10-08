package com.google.common.collect;

import com.google.common.collect.TableCollectors;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class TableCollectors$$ExternalSyntheticLambda5 implements BiConsumer {
    public final /* synthetic */ Function f$0;
    public final /* synthetic */ Function f$1;
    public final /* synthetic */ Function f$2;
    public final /* synthetic */ BinaryOperator f$3;

    public /* synthetic */ TableCollectors$$ExternalSyntheticLambda5(Function function, Function function2, Function function3, BinaryOperator binaryOperator) {
        this.f$0 = function;
        this.f$1 = function2;
        this.f$2 = function3;
        this.f$3 = binaryOperator;
    }

    public final void accept(Object obj, Object obj2) {
        ((TableCollectors.ImmutableTableCollectorState) obj).put(this.f$0.apply(obj2), this.f$1.apply(obj2), this.f$2.apply(obj2), this.f$3);
    }
}
