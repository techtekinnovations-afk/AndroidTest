package kotlinx.coroutines.debug.internal;

import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.debug.internal.DebugProbesImpl;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: DebugProbesImpl.kt */
public final class DebugProbesImpl$dumpCoroutinesInfo$$inlined$dumpCoroutinesInfoImpl$1 implements Function1<DebugProbesImpl.CoroutineOwner<?>, DebugCoroutineInfo> {
    public final DebugCoroutineInfo invoke(DebugProbesImpl.CoroutineOwner<?> owner) {
        CoroutineContext context;
        if (DebugProbesImpl.INSTANCE.isFinished(owner) || (context = owner.info.getContext()) == null) {
            return null;
        }
        return new DebugCoroutineInfo(owner.info, context);
    }
}
