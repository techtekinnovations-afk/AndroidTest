package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u001a\u0001\u0010\u0003\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0005\"\u0004\b\u0001\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00050\u00072\u0014\u0010\b\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u0002H\u00060\n0\t2\u0016\u0010\u000b\u001a\u0012\u0012\u000e\u0012\f\u0012\u0006\u0012\u0004\u0018\u0001H\u0006\u0018\u00010\t0\f29\u0010\r\u001a5\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00050\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00060\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u000e¢\u0006\u0002\b\u0010H@¢\u0006\u0002\u0010\u0011\u001ak\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00050\n\"\u0004\b\u0000\u0010\u0013\"\u0004\b\u0001\u0010\u0014\"\u0004\b\u0002\u0010\u00052\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00130\n2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00140\n2(\u0010\r\u001a$\b\u0001\u0012\u0004\u0012\u0002H\u0013\u0012\u0004\u0012\u0002H\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00050\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u000eH\u0000¢\u0006\u0002\u0010\u0017*\u001c\b\u0002\u0010\u0000\"\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001¨\u0006\u0018"}, d2 = {"Update", "Lkotlin/collections/IndexedValue;", "", "combineInternal", "", "R", "T", "Lkotlinx/coroutines/flow/FlowCollector;", "flows", "", "Lkotlinx/coroutines/flow/Flow;", "arrayFactory", "Lkotlin/Function0;", "transform", "Lkotlin/Function3;", "Lkotlin/coroutines/Continuation;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/flow/FlowCollector;[Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "zipImpl", "T1", "T2", "flow", "flow2", "(Lkotlinx/coroutines/flow/Flow;Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "kotlinx-coroutines-core"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: Combine.kt */
public final class CombineKt {
    public static final <R, T> Object combineInternal(FlowCollector<? super R> $this$combineInternal, Flow<? extends T>[] flows, Function0<T[]> arrayFactory, Function3<? super FlowCollector<? super R>, ? super T[], ? super Continuation<? super Unit>, ? extends Object> transform, Continuation<? super Unit> $completion) {
        Object flowScope = FlowCoroutineKt.flowScope(new CombineKt$combineInternal$2(flows, arrayFactory, transform, $this$combineInternal, (Continuation<? super CombineKt$combineInternal$2>) null), $completion);
        return flowScope == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? flowScope : Unit.INSTANCE;
    }

    public static final <T1, T2, R> Flow<R> zipImpl(Flow<? extends T1> flow, Flow<? extends T2> flow2, Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> transform) {
        return new CombineKt$zipImpl$$inlined$unsafeFlow$1(flow2, flow, transform);
    }
}
