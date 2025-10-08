package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.internal.ScopeCoroutine;

@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0001\u001a\u001b\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0010\u001aK\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\n0\t\"\u0004\b\u0000\u0010\n2/\b\u0005\u0010\u000b\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\n0\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\f¢\u0006\u0002\b\u0010H\b¢\u0006\u0002\u0010\u0011¨\u0006\u0012"}, d2 = {"checkContext", "", "Lkotlinx/coroutines/flow/internal/SafeCollector;", "currentContext", "Lkotlin/coroutines/CoroutineContext;", "transitiveCoroutineParent", "Lkotlinx/coroutines/Job;", "collectJob", "unsafeFlow", "Lkotlinx/coroutines/flow/Flow;", "T", "block", "Lkotlin/Function2;", "Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/flow/Flow;", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: SafeCollector.common.kt */
public final class SafeCollector_commonKt {
    public static final void checkContext(SafeCollector<?> $this$checkContext, CoroutineContext currentContext) {
        if (((Number) currentContext.fold(0, new SafeCollector_commonKt$$ExternalSyntheticLambda0($this$checkContext))).intValue() != $this$checkContext.collectContextSize) {
            throw new IllegalStateException(("Flow invariant is violated:\n\t\tFlow was collected in " + $this$checkContext.collectContext + ",\n\t\tbut emission happened in " + currentContext + ".\n\t\tPlease refer to 'flow' documentation or use 'flowOn' instead").toString());
        }
    }

    /* access modifiers changed from: private */
    public static final int checkContext$lambda$0(SafeCollector $this_checkContext, int count, CoroutineContext.Element element) {
        CoroutineContext.Key key = element.getKey();
        CoroutineContext.Element collectElement = $this_checkContext.collectContext.get(key);
        if (key == Job.Key) {
            Job collectJob = (Job) collectElement;
            Intrinsics.checkNotNull(element, "null cannot be cast to non-null type kotlinx.coroutines.Job");
            Job emissionParentJob = transitiveCoroutineParent((Job) element, collectJob);
            if (emissionParentJob == collectJob) {
                return collectJob == null ? count : count + 1;
            }
            throw new IllegalStateException(("Flow invariant is violated:\n\t\tEmission from another coroutine is detected.\n\t\tChild of " + emissionParentJob + ", expected child of " + collectJob + ".\n\t\tFlowCollector is not thread-safe and concurrent emissions are prohibited.\n\t\tTo mitigate this restriction please use 'channelFlow' builder instead of 'flow'").toString());
        } else if (element != collectElement) {
            return Integer.MIN_VALUE;
        } else {
            return count + 1;
        }
    }

    public static final Job transitiveCoroutineParent(Job $this$transitiveCoroutineParent, Job collectJob) {
        while ($this$transitiveCoroutineParent != null) {
            if ($this$transitiveCoroutineParent == collectJob || !($this$transitiveCoroutineParent instanceof ScopeCoroutine)) {
                return $this$transitiveCoroutineParent;
            }
            $this$transitiveCoroutineParent = ((ScopeCoroutine) $this$transitiveCoroutineParent).getParent();
        }
        return null;
    }

    public static final <T> Flow<T> unsafeFlow(Function2<? super FlowCollector<? super T>, ? super Continuation<? super Unit>, ? extends Object> block) {
        return new SafeCollector_commonKt$unsafeFlow$1(block);
    }
}
