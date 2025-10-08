package kotlinx.coroutines.internal;

import java.lang.reflect.Constructor;
import kotlin.jvm.functions.Function1;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class ExceptionsConstructorKt$$ExternalSyntheticLambda4 implements Function1 {
    public final /* synthetic */ Constructor f$0;

    public /* synthetic */ ExceptionsConstructorKt$$ExternalSyntheticLambda4(Constructor constructor) {
        this.f$0 = constructor;
    }

    public final Object invoke(Object obj) {
        return ExceptionsConstructorKt.createConstructor$lambda$7$lambda$6(this.f$0, (Throwable) obj);
    }
}
