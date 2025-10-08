package kotlinx.coroutines.internal;

import kotlin.Metadata;
import kotlinx.coroutines.CoroutineDispatcher;

@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0000\u001a\u0016\u0010\u0003\u001a\u00020\u0004*\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0000¨\u0006\u0007"}, d2 = {"checkParallelism", "", "", "namedOrThis", "Lkotlinx/coroutines/CoroutineDispatcher;", "name", "", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: LimitedDispatcher.kt */
public final class LimitedDispatcherKt {
    public static final void checkParallelism(int $this$checkParallelism) {
        boolean z = true;
        if ($this$checkParallelism < 1) {
            z = false;
        }
        if (!z) {
            throw new IllegalArgumentException(("Expected positive parallelism level, but got " + $this$checkParallelism).toString());
        }
    }

    public static final CoroutineDispatcher namedOrThis(CoroutineDispatcher $this$namedOrThis, String name) {
        if (name != null) {
            return new NamedDispatcher($this$namedOrThis, name);
        }
        return $this$namedOrThis;
    }
}
