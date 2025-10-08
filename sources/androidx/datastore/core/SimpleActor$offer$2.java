package androidx.datastore.core;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H@"}, d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "androidx.datastore.core.SimpleActor$offer$2", f = "SimpleActor.kt", i = {}, l = {121, 121}, m = "invokeSuspend", n = {}, s = {})
/* compiled from: SimpleActor.kt */
final class SimpleActor$offer$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    Object L$0;
    int label;
    final /* synthetic */ SimpleActor<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SimpleActor$offer$2(SimpleActor<T> simpleActor, Continuation<? super SimpleActor$offer$2> continuation) {
        super(2, continuation);
        this.this$0 = simpleActor;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new SimpleActor$offer$2(this.this$0, continuation);
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((SimpleActor$offer$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x007f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r7.label
            r2 = 1
            switch(r1) {
                case 0: goto L_0x0025;
                case 1: goto L_0x0018;
                case 2: goto L_0x0012;
                default: goto L_0x000a;
            }
        L_0x000a:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r0)
            throw r8
        L_0x0012:
            r1 = r7
            kotlin.ResultKt.throwOnFailure(r8)
            goto L_0x0073
        L_0x0018:
            r1 = r7
            java.lang.Object r3 = r1.L$0
            kotlin.jvm.functions.Function2 r3 = (kotlin.jvm.functions.Function2) r3
            kotlin.ResultKt.throwOnFailure(r8)
            r4 = r3
            r3 = r1
            r1 = r0
            r0 = r8
            goto L_0x0063
        L_0x0025:
            kotlin.ResultKt.throwOnFailure(r8)
            r1 = r7
            androidx.datastore.core.SimpleActor<T> r3 = r1.this$0
            androidx.datastore.core.AtomicInt r3 = r3.remainingMessages
            int r3 = r3.get()
            if (r3 <= 0) goto L_0x0037
            r3 = r2
            goto L_0x0038
        L_0x0037:
            r3 = 0
        L_0x0038:
            if (r3 == 0) goto L_0x0082
        L_0x003a:
            androidx.datastore.core.SimpleActor<T> r3 = r1.this$0
            kotlinx.coroutines.CoroutineScope r3 = r3.scope
            kotlinx.coroutines.CoroutineScopeKt.ensureActive(r3)
            androidx.datastore.core.SimpleActor<T> r3 = r1.this$0
            kotlin.jvm.functions.Function2 r3 = r3.consumeMessage
            androidx.datastore.core.SimpleActor<T> r4 = r1.this$0
            kotlinx.coroutines.channels.Channel r4 = r4.messageQueue
            r5 = r1
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r1.L$0 = r3
            r1.label = r2
            java.lang.Object r4 = r4.receive(r5)
            if (r4 != r0) goto L_0x005d
            return r0
        L_0x005d:
            r6 = r0
            r0 = r8
            r8 = r4
            r4 = r3
            r3 = r1
            r1 = r6
        L_0x0063:
            r5 = 0
            r3.L$0 = r5
            r5 = 2
            r3.label = r5
            java.lang.Object r8 = r4.invoke(r8, r3)
            if (r8 != r1) goto L_0x0070
            return r1
        L_0x0070:
            r8 = r0
            r0 = r1
            r1 = r3
        L_0x0073:
            androidx.datastore.core.SimpleActor<T> r3 = r1.this$0
            androidx.datastore.core.AtomicInt r3 = r3.remainingMessages
            int r3 = r3.decrementAndGet()
            if (r3 != 0) goto L_0x003a
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x0082:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "Check failed."
            java.lang.String r2 = r2.toString()
            r0.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.SimpleActor$offer$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
