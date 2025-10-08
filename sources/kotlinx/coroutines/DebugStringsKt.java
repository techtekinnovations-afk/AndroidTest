package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.internal.DispatchedContinuation;

@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0005\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u0006H\u0000\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0018\u0010\u0007\u001a\u00020\u0001*\u00020\u00028@X\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0004¨\u0006\t"}, d2 = {"hexAddress", "", "", "getHexAddress", "(Ljava/lang/Object;)Ljava/lang/String;", "toDebugString", "Lkotlin/coroutines/Continuation;", "classSimpleName", "getClassSimpleName", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: DebugStrings.kt */
public final class DebugStringsKt {
    public static final String getHexAddress(Object $this$hexAddress) {
        return Integer.toHexString(System.identityHashCode($this$hexAddress));
    }

    public static final String toDebugString(Continuation<?> $this$toDebugString) {
        String str;
        if ($this$toDebugString instanceof DispatchedContinuation) {
            return ((DispatchedContinuation) $this$toDebugString).toString();
        }
        try {
            Result.Companion companion = Result.Companion;
            Continuation $this$toDebugString_u24lambda_u240 = $this$toDebugString;
            str = Result.m6constructorimpl($this$toDebugString_u24lambda_u240 + '@' + getHexAddress($this$toDebugString_u24lambda_u240));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            str = Result.m6constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m9exceptionOrNullimpl(str) != null) {
            str = $this$toDebugString.getClass().getName() + '@' + getHexAddress($this$toDebugString);
        }
        return (String) str;
    }

    public static final String getClassSimpleName(Object $this$classSimpleName) {
        return $this$classSimpleName.getClass().getSimpleName();
    }
}
