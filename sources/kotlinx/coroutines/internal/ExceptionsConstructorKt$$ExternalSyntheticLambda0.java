package kotlinx.coroutines.internal;

import kotlin.jvm.functions.Function1;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class ExceptionsConstructorKt$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ Function1 f$0;

    public /* synthetic */ ExceptionsConstructorKt$$ExternalSyntheticLambda0(Function1 function1) {
        this.f$0 = function1;
    }

    public final Object invoke(Object obj) {
        return ExceptionsConstructorKt.safeCtor$lambda$9(this.f$0, (Throwable) obj);
    }
}
