package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002BD\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012-\u0010\u0004\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0005¢\u0006\u0002\b\t¢\u0006\u0004\b\n\u0010\u000bJ\u000e\u0010\r\u001a\u00020\u0007H@¢\u0006\u0002\u0010\u000eJ\u0011\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00028\u0000HAR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0004¢\u0006\u0002\n\u0000R7\u0010\u0004\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0005¢\u0006\u0002\b\tX\u0004¢\u0006\u0004\n\u0002\u0010\f¨\u0006\u0011"}, d2 = {"Lkotlinx/coroutines/flow/SubscribedFlowCollector;", "T", "Lkotlinx/coroutines/flow/FlowCollector;", "collector", "action", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "", "Lkotlin/ExtensionFunctionType;", "<init>", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "onSubscription", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "emit", "value", "kotlinx-coroutines-core"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: Share.kt */
public final class SubscribedFlowCollector<T> implements FlowCollector<T> {
    private final Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object> action;
    private final FlowCollector<T> collector;

    public Object emit(T t, Continuation<? super Unit> continuation) {
        return this.collector.emit(t, continuation);
    }

    public SubscribedFlowCollector(FlowCollector<? super T> collector2, Function2<? super FlowCollector<? super T>, ? super Continuation<? super Unit>, ? extends Object> action2) {
        this.collector = collector2;
        this.action = action2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object onSubscription(kotlin.coroutines.Continuation<? super kotlin.Unit> r9) {
        /*
            r8 = this;
            boolean r0 = r9 instanceof kotlinx.coroutines.flow.SubscribedFlowCollector$onSubscription$1
            if (r0 == 0) goto L_0x0014
            r0 = r9
            kotlinx.coroutines.flow.SubscribedFlowCollector$onSubscription$1 r0 = (kotlinx.coroutines.flow.SubscribedFlowCollector$onSubscription$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.flow.SubscribedFlowCollector$onSubscription$1 r0 = new kotlinx.coroutines.flow.SubscribedFlowCollector$onSubscription$1
            r0.<init>(r8, r9)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            switch(r3) {
                case 0: goto L_0x003c;
                case 1: goto L_0x0030;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002c:
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x007a
        L_0x0030:
            java.lang.Object r3 = r0.L$1
            kotlinx.coroutines.flow.internal.SafeCollector r3 = (kotlinx.coroutines.flow.internal.SafeCollector) r3
            java.lang.Object r4 = r0.L$0
            kotlinx.coroutines.flow.SubscribedFlowCollector r4 = (kotlinx.coroutines.flow.SubscribedFlowCollector) r4
            kotlin.ResultKt.throwOnFailure(r1)     // Catch:{ all -> 0x007e }
            goto L_0x005d
        L_0x003c:
            kotlin.ResultKt.throwOnFailure(r1)
            r4 = r8
            kotlinx.coroutines.flow.internal.SafeCollector r3 = new kotlinx.coroutines.flow.internal.SafeCollector
            kotlinx.coroutines.flow.FlowCollector<T> r5 = r4.collector
            r6 = 0
            kotlin.coroutines.CoroutineContext r7 = r0.getContext()
            r3.<init>(r5, r7)
            kotlin.jvm.functions.Function2<kotlinx.coroutines.flow.FlowCollector<? super T>, kotlin.coroutines.Continuation<? super kotlin.Unit>, java.lang.Object> r5 = r4.action     // Catch:{ all -> 0x007e }
            r0.L$0 = r4     // Catch:{ all -> 0x007e }
            r0.L$1 = r3     // Catch:{ all -> 0x007e }
            r6 = 1
            r0.label = r6     // Catch:{ all -> 0x007e }
            java.lang.Object r5 = r5.invoke(r3, r0)     // Catch:{ all -> 0x007e }
            if (r5 != r2) goto L_0x005d
            return r2
        L_0x005d:
            r3.releaseIntercepted()
            kotlinx.coroutines.flow.FlowCollector<T> r3 = r4.collector
            boolean r3 = r3 instanceof kotlinx.coroutines.flow.SubscribedFlowCollector
            if (r3 == 0) goto L_0x007d
            kotlinx.coroutines.flow.FlowCollector<T> r3 = r4.collector
            kotlinx.coroutines.flow.SubscribedFlowCollector r3 = (kotlinx.coroutines.flow.SubscribedFlowCollector) r3
            r5 = 0
            r0.L$0 = r5
            r0.L$1 = r5
            r5 = 2
            r0.label = r5
            java.lang.Object r3 = r3.onSubscription(r0)
            if (r3 != r2) goto L_0x007a
            return r2
        L_0x007a:
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            return r2
        L_0x007d:
            goto L_0x007a
        L_0x007e:
            r2 = move-exception
            r3.releaseIntercepted()
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.SubscribedFlowCollector.onSubscription(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
