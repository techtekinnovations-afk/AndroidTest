package kotlinx.coroutines.debug.internal;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.debug.internal.DebugProbesImpl;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: DebugProbesImpl.kt */
public final class DebugProbesImpl$dumpCoroutinesInfoImpl$3 implements Function1<DebugProbesImpl.CoroutineOwner<?>, R> {
    final /* synthetic */ Function2<DebugProbesImpl.CoroutineOwner<?>, CoroutineContext, R> $create;

    public DebugProbesImpl$dumpCoroutinesInfoImpl$3(Function2<? super DebugProbesImpl.CoroutineOwner<?>, ? super CoroutineContext, ? extends R> function2) {
        this.$create = function2;
    }

    public final R invoke(DebugProbesImpl.CoroutineOwner<?> owner) {
        CoroutineContext context;
        if (!DebugProbesImpl.INSTANCE.isFinished(owner) && (context = owner.info.getContext()) != null) {
            return this.$create.invoke(owner, context);
        }
        return null;
    }
}
