package kotlinx.coroutines.flow;

import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref;

@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/flow/FlowCollector;", "Lkotlinx/coroutines/flow/SharingCommand;"}, k = 3, mv = {2, 0, 0}, xi = 48)
@DebugMetadata(c = "kotlinx.coroutines.flow.StartedLazily$command$1", f = "SharingStarted.kt", i = {}, l = {151}, m = "invokeSuspend", n = {}, s = {})
/* compiled from: SharingStarted.kt */
final class StartedLazily$command$1 extends SuspendLambda implements Function2<FlowCollector<? super SharingCommand>, Continuation<? super Unit>, Object> {
    final /* synthetic */ StateFlow<Integer> $subscriptionCount;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    StartedLazily$command$1(StateFlow<Integer> stateFlow, Continuation<? super StartedLazily$command$1> continuation) {
        super(2, continuation);
        this.$subscriptionCount = stateFlow;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        StartedLazily$command$1 startedLazily$command$1 = new StartedLazily$command$1(this.$subscriptionCount, continuation);
        startedLazily$command$1.L$0 = obj;
        return startedLazily$command$1;
    }

    public final Object invoke(FlowCollector<? super SharingCommand> flowCollector, Continuation<? super Unit> continuation) {
        return ((StartedLazily$command$1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                final FlowCollector $this$flow = (FlowCollector) this.L$0;
                final Ref.BooleanRef started = new Ref.BooleanRef();
                this.label = 1;
                if (this.$subscriptionCount.collect(new FlowCollector() {
                    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
                    /* JADX WARNING: Removed duplicated region for block: B:11:0x0030  */
                    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public final java.lang.Object emit(int r7, kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
                        /*
                            r6 = this;
                            boolean r0 = r8 instanceof kotlinx.coroutines.flow.StartedLazily$command$1$1$emit$1
                            if (r0 == 0) goto L_0x0014
                            r0 = r8
                            kotlinx.coroutines.flow.StartedLazily$command$1$1$emit$1 r0 = (kotlinx.coroutines.flow.StartedLazily$command$1$1$emit$1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r1 = r1 & r2
                            if (r1 == 0) goto L_0x0014
                            int r1 = r0.label
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L_0x0019
                        L_0x0014:
                            kotlinx.coroutines.flow.StartedLazily$command$1$1$emit$1 r0 = new kotlinx.coroutines.flow.StartedLazily$command$1$1$emit$1
                            r0.<init>(r6, r8)
                        L_0x0019:
                            java.lang.Object r1 = r0.result
                            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                            int r3 = r0.label
                            switch(r3) {
                                case 0: goto L_0x0030;
                                case 1: goto L_0x002c;
                                default: goto L_0x0024;
                            }
                        L_0x0024:
                            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                            r7.<init>(r0)
                            throw r7
                        L_0x002c:
                            kotlin.ResultKt.throwOnFailure(r1)
                            goto L_0x004e
                        L_0x0030:
                            kotlin.ResultKt.throwOnFailure(r1)
                            r3 = r6
                            if (r7 <= 0) goto L_0x004e
                            kotlin.jvm.internal.Ref$BooleanRef r4 = r3
                            boolean r4 = r4.element
                            if (r4 != 0) goto L_0x004e
                            kotlin.jvm.internal.Ref$BooleanRef r7 = r3
                            r4 = 1
                            r7.element = r4
                            kotlinx.coroutines.flow.FlowCollector<kotlinx.coroutines.flow.SharingCommand> r7 = r2
                            kotlinx.coroutines.flow.SharingCommand r5 = kotlinx.coroutines.flow.SharingCommand.START
                            r0.label = r4
                            java.lang.Object r7 = r7.emit(r5, r0)
                            if (r7 != r2) goto L_0x004e
                            return r2
                        L_0x004e:
                            kotlin.Unit r7 = kotlin.Unit.INSTANCE
                            return r7
                        */
                        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.StartedLazily$command$1.AnonymousClass1.emit(int, kotlin.coroutines.Continuation):java.lang.Object");
                    }

                    public /* bridge */ /* synthetic */ Object emit(Object value, Continuation $completion) {
                        return emit(((Number) value).intValue(), (Continuation<? super Unit>) $completion);
                    }
                }, this) != coroutine_suspended) {
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        throw new KotlinNothingValueException();
    }
}
