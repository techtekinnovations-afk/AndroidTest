package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.internal.ThreadContextKt;

@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0000\u001a&\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0002\u001aX\u0010\b\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\t2\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u0002H\t2\b\b\u0002\u0010\f\u001a\u00020\r2\"\u0010\u000e\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\t\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\r0\u000fH@¢\u0006\u0002\u0010\u0011¨\u0006\u0012"}, d2 = {"asChannelFlow", "Lkotlinx/coroutines/flow/internal/ChannelFlow;", "T", "Lkotlinx/coroutines/flow/Flow;", "withUndispatchedContextCollector", "Lkotlinx/coroutines/flow/FlowCollector;", "emitContext", "Lkotlin/coroutines/CoroutineContext;", "withContextUndispatched", "V", "newContext", "value", "countOrElement", "", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Object;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: ChannelFlow.kt */
public final class ChannelFlowKt {
    public static final <T> ChannelFlow<T> asChannelFlow(Flow<? extends T> $this$asChannelFlow) {
        ChannelFlow<T> channelFlow = $this$asChannelFlow instanceof ChannelFlow ? (ChannelFlow) $this$asChannelFlow : null;
        if (channelFlow == null) {
            return new ChannelFlowOperatorImpl<>($this$asChannelFlow, (CoroutineContext) null, 0, (BufferOverflow) null, 14, (DefaultConstructorMarker) null);
        }
        return channelFlow;
    }

    /* access modifiers changed from: private */
    public static final <T> FlowCollector<T> withUndispatchedContextCollector(FlowCollector<? super T> $this$withUndispatchedContextCollector, CoroutineContext emitContext) {
        if (($this$withUndispatchedContextCollector instanceof SendingCollector) || ($this$withUndispatchedContextCollector instanceof NopCollector)) {
            return $this$withUndispatchedContextCollector;
        }
        return new UndispatchedContextCollector<>($this$withUndispatchedContextCollector, emitContext);
    }

    public static /* synthetic */ Object withContextUndispatched$default(CoroutineContext coroutineContext, Object obj, Object obj2, Function2 function2, Continuation continuation, int i, Object obj3) {
        if ((i & 4) != 0) {
            obj2 = ThreadContextKt.threadContextElements(coroutineContext);
        }
        return withContextUndispatched(coroutineContext, obj, obj2, function2, continuation);
    }

    /* JADX INFO: finally extract failed */
    public static final <T, V> Object withContextUndispatched(CoroutineContext newContext, V value, Object countOrElement, Function2<? super V, ? super Continuation<? super T>, ? extends Object> block, Continuation<? super T> $completion) {
        Continuation uCont = $completion;
        Object oldValue$iv = ThreadContextKt.updateThreadContext(newContext, countOrElement);
        try {
            Continuation stackFrameContinuation = new StackFrameContinuation(uCont, newContext);
            Object wrapWithContinuationImpl = !(block instanceof BaseContinuationImpl) ? IntrinsicsKt.wrapWithContinuationImpl(block, value, stackFrameContinuation) : ((Function2) TypeIntrinsics.beforeCheckcastToFunctionOfArity(block, 2)).invoke(value, stackFrameContinuation);
            ThreadContextKt.restoreThreadContext(newContext, oldValue$iv);
            if (wrapWithContinuationImpl == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended($completion);
            }
            return wrapWithContinuationImpl;
        } catch (Throwable th) {
            ThreadContextKt.restoreThreadContext(newContext, oldValue$iv);
            throw th;
        }
    }
}
