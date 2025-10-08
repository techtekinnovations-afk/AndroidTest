package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;

@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\u001a\u001c\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001\u001aT\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000126\u0010\u0003\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\t0\u0004\u001a6\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u000b0\r\u001au\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0014\u0010\f\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\r2:\u0010\u0003\u001a6\u0012\u0015\u0012\u0013\u0018\u00010\u000f¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\u0007\u0012\u0015\u0012\u0013\u0018\u00010\u000f¢\u0006\f\b\u0005\u0012\b\b\u0006\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\t0\u0004H\u0002¢\u0006\u0002\b\u0011\"\u001e\u0010\u000e\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\rX\u0004¢\u0006\u0002\n\u0000\"$\u0010\u0010\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0012\u0004\u0012\u00020\t0\u0004X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"distinctUntilChanged", "Lkotlinx/coroutines/flow/Flow;", "T", "areEquivalent", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "old", "new", "", "distinctUntilChangedBy", "K", "keySelector", "Lkotlin/Function1;", "defaultKeySelector", "", "defaultAreEquivalent", "distinctUntilChangedBy$FlowKt__DistinctKt", "kotlinx-coroutines-core"}, k = 5, mv = {2, 0, 0}, xi = 48, xs = "kotlinx/coroutines/flow/FlowKt")
/* compiled from: Distinct.kt */
final /* synthetic */ class FlowKt__DistinctKt {
    private static final Function2<Object, Object, Boolean> defaultAreEquivalent = new FlowKt__DistinctKt$$ExternalSyntheticLambda1();
    private static final Function1<Object, Object> defaultKeySelector = new FlowKt__DistinctKt$$ExternalSyntheticLambda0();

    public static final <T> Flow<T> distinctUntilChanged(Flow<? extends T> $this$distinctUntilChanged) {
        if ($this$distinctUntilChanged instanceof StateFlow) {
            return $this$distinctUntilChanged;
        }
        return distinctUntilChangedBy$FlowKt__DistinctKt($this$distinctUntilChanged, defaultKeySelector, defaultAreEquivalent);
    }

    public static final <T> Flow<T> distinctUntilChanged(Flow<? extends T> $this$distinctUntilChanged, Function2<? super T, ? super T, Boolean> areEquivalent) {
        Function1<Object, Object> function1 = defaultKeySelector;
        Intrinsics.checkNotNull(areEquivalent, "null cannot be cast to non-null type kotlin.Function2<kotlin.Any?, kotlin.Any?, kotlin.Boolean>");
        return distinctUntilChangedBy$FlowKt__DistinctKt($this$distinctUntilChanged, function1, (Function2) TypeIntrinsics.beforeCheckcastToFunctionOfArity(areEquivalent, 2));
    }

    public static final <T, K> Flow<T> distinctUntilChangedBy(Flow<? extends T> $this$distinctUntilChangedBy, Function1<? super T, ? extends K> keySelector) {
        return distinctUntilChangedBy$FlowKt__DistinctKt($this$distinctUntilChangedBy, keySelector, defaultAreEquivalent);
    }

    /* access modifiers changed from: private */
    public static final Object defaultKeySelector$lambda$0$FlowKt__DistinctKt(Object it) {
        return it;
    }

    /* access modifiers changed from: private */
    public static final boolean defaultAreEquivalent$lambda$1$FlowKt__DistinctKt(Object old, Object obj) {
        return Intrinsics.areEqual(old, obj);
    }

    private static final <T> Flow<T> distinctUntilChangedBy$FlowKt__DistinctKt(Flow<? extends T> $this$distinctUntilChangedBy, Function1<? super T, ? extends Object> keySelector, Function2<Object, Object, Boolean> areEquivalent) {
        if (($this$distinctUntilChangedBy instanceof DistinctFlowImpl) && ((DistinctFlowImpl) $this$distinctUntilChangedBy).keySelector == keySelector && ((DistinctFlowImpl) $this$distinctUntilChangedBy).areEquivalent == areEquivalent) {
            return $this$distinctUntilChangedBy;
        }
        return new DistinctFlowImpl<>($this$distinctUntilChangedBy, keySelector, areEquivalent);
    }
}
