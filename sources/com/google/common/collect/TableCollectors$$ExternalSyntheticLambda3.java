package com.google.common.collect;

import java.util.function.BinaryOperator;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class TableCollectors$$ExternalSyntheticLambda3 implements BinaryOperator {
    public final /* synthetic */ BinaryOperator f$0;

    public /* synthetic */ TableCollectors$$ExternalSyntheticLambda3(BinaryOperator binaryOperator) {
        this.f$0 = binaryOperator;
    }

    public final Object apply(Object obj, Object obj2) {
        return TableCollectors.lambda$toTable$7(this.f$0, (Table) obj, (Table) obj2);
    }
}
