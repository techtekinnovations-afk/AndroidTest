package kotlinx.coroutines;

import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class CoroutineContextKt$$ExternalSyntheticLambda1 implements Function2 {
    public final /* synthetic */ Ref.ObjectRef f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ CoroutineContextKt$$ExternalSyntheticLambda1(Ref.ObjectRef objectRef, boolean z) {
        this.f$0 = objectRef;
        this.f$1 = z;
    }

    public final Object invoke(Object obj, Object obj2) {
        return CoroutineContextKt.foldCopies$lambda$1(this.f$0, this.f$1, (CoroutineContext) obj, (CoroutineContext.Element) obj2);
    }
}
