package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\n"}, d2 = {"<anonymous>", "", "R", "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {2, 0, 0}, xi = 48)
@DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$map$1", f = "Deprecated.kt", i = {0, 0, 1, 1, 2, 2}, l = {514, 363, 363}, m = "invokeSuspend", n = {"$this$produce", "$this$consume$iv$iv", "$this$produce", "$this$consume$iv$iv", "$this$produce", "$this$consume$iv$iv"}, s = {"L$0", "L$2", "L$0", "L$2", "L$0", "L$2"})
/* compiled from: Deprecated.kt */
final class ChannelsKt__DeprecatedKt$map$1 extends SuspendLambda implements Function2<ProducerScope<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ ReceiveChannel<E> $this_map;
    final /* synthetic */ Function2<E, Continuation<? super R>, Object> $transform;
    private /* synthetic */ Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ChannelsKt__DeprecatedKt$map$1(ReceiveChannel<? extends E> receiveChannel, Function2<? super E, ? super Continuation<? super R>, ? extends Object> function2, Continuation<? super ChannelsKt__DeprecatedKt$map$1> continuation) {
        super(2, continuation);
        this.$this_map = receiveChannel;
        this.$transform = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ChannelsKt__DeprecatedKt$map$1 channelsKt__DeprecatedKt$map$1 = new ChannelsKt__DeprecatedKt$map$1(this.$this_map, this.$transform, continuation);
        channelsKt__DeprecatedKt$map$1.L$0 = obj;
        return channelsKt__DeprecatedKt$map$1;
    }

    public final Object invoke(ProducerScope<? super R> producerScope, Continuation<? super Unit> continuation) {
        return ((ChannelsKt__DeprecatedKt$map$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v10, resolved type: kotlinx.coroutines.channels.ReceiveChannel<E>} */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00a6, code lost:
        r2.L$0 = r4;
        r2.L$1 = r8;
        r2.L$2 = r10;
        r2.L$3 = r12;
        r2.label = 1;
        r11 = r12.hasNext(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00b5, code lost:
        if (r11 != r0) goto L_0x00b8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00b7, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00b8, code lost:
        r16 = r4;
        r4 = r3;
        r3 = r11;
        r11 = r10;
        r10 = r9;
        r9 = r8;
        r8 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00c7, code lost:
        if (((java.lang.Boolean) r3).booleanValue() == false) goto L_0x0109;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00c9, code lost:
        r3 = r12.next();
        r13 = 0;
        r2.L$0 = r8;
        r2.L$1 = r9;
        r2.L$2 = r11;
        r2.L$3 = r12;
        r2.L$4 = r8;
        r2.label = 2;
        r14 = r9.invoke(r3, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00df, code lost:
        if (r14 != r0) goto L_0x00e2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00e1, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00e2, code lost:
        r3 = r12;
        r12 = r9;
        r9 = r3;
        r3 = r14;
        r14 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00e7, code lost:
        r2.L$0 = r14;
        r2.L$1 = r12;
        r2.L$2 = r11;
        r2.L$3 = r9;
        r2.L$4 = null;
        r2.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00f9, code lost:
        if (r8.send(r3, r2) != r0) goto L_0x00fc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00fb, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00fc, code lost:
        r3 = r12;
        r12 = r9;
        r9 = r10;
        r10 = r11;
        r11 = r3;
        r3 = r4;
        r8 = r7;
        r7 = r13;
        r4 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0105, code lost:
        r7 = r8;
        r8 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0109, code lost:
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x010c, code lost:
        kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r11, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0114, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0115, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0116, code lost:
        r3 = r4;
        r4 = r7;
        r9 = r10;
        r10 = r11;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r18) {
        /*
            r17 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            r1 = r17
            int r2 = r1.label
            switch(r2) {
                case 0: goto L_0x008a;
                case 1: goto L_0x0062;
                case 2: goto L_0x0037;
                case 3: goto L_0x0013;
                default: goto L_0x000b;
            }
        L_0x000b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r2)
            throw r0
        L_0x0013:
            r2 = r17
            r3 = r18
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            java.lang.Object r8 = r2.L$3
            kotlinx.coroutines.channels.ChannelIterator r8 = (kotlinx.coroutines.channels.ChannelIterator) r8
            r9 = 0
            java.lang.Object r10 = r2.L$2
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r11 = r2.L$1
            kotlin.jvm.functions.Function2 r11 = (kotlin.jvm.functions.Function2) r11
            java.lang.Object r12 = r2.L$0
            kotlinx.coroutines.channels.ProducerScope r12 = (kotlinx.coroutines.channels.ProducerScope) r12
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0087 }
            r16 = r8
            r8 = r4
            r4 = r12
            r12 = r16
            goto L_0x0105
        L_0x0037:
            r2 = r17
            r3 = r18
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            java.lang.Object r8 = r2.L$4
            kotlinx.coroutines.channels.ProducerScope r8 = (kotlinx.coroutines.channels.ProducerScope) r8
            java.lang.Object r9 = r2.L$3
            kotlinx.coroutines.channels.ChannelIterator r9 = (kotlinx.coroutines.channels.ChannelIterator) r9
            r10 = 0
            java.lang.Object r11 = r2.L$2
            kotlinx.coroutines.channels.ReceiveChannel r11 = (kotlinx.coroutines.channels.ReceiveChannel) r11
            java.lang.Object r12 = r2.L$1
            kotlin.jvm.functions.Function2 r12 = (kotlin.jvm.functions.Function2) r12
            java.lang.Object r13 = r2.L$0
            kotlinx.coroutines.channels.ProducerScope r13 = (kotlinx.coroutines.channels.ProducerScope) r13
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x005d }
            r14 = r13
            r13 = r7
            r7 = r4
            r4 = r3
            goto L_0x00e7
        L_0x005d:
            r0 = move-exception
            r9 = r10
            r10 = r11
            goto L_0x011d
        L_0x0062:
            r2 = r17
            r3 = r18
            r4 = 0
            r5 = 0
            r6 = 0
            java.lang.Object r7 = r2.L$3
            kotlinx.coroutines.channels.ChannelIterator r7 = (kotlinx.coroutines.channels.ChannelIterator) r7
            r9 = 0
            java.lang.Object r8 = r2.L$2
            r10 = r8
            kotlinx.coroutines.channels.ReceiveChannel r10 = (kotlinx.coroutines.channels.ReceiveChannel) r10
            java.lang.Object r8 = r2.L$1
            kotlin.jvm.functions.Function2 r8 = (kotlin.jvm.functions.Function2) r8
            java.lang.Object r11 = r2.L$0
            kotlinx.coroutines.channels.ProducerScope r11 = (kotlinx.coroutines.channels.ProducerScope) r11
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0087 }
            r12 = r9
            r9 = r8
            r8 = r11
            r11 = r10
            r10 = r12
            r12 = r7
            r7 = r4
            r4 = r3
            goto L_0x00c1
        L_0x0087:
            r0 = move-exception
            goto L_0x011d
        L_0x008a:
            kotlin.ResultKt.throwOnFailure(r18)
            r2 = r17
            r3 = r18
            java.lang.Object r4 = r2.L$0
            kotlinx.coroutines.channels.ProducerScope r4 = (kotlinx.coroutines.channels.ProducerScope) r4
            kotlinx.coroutines.channels.ReceiveChannel<E> r5 = r2.$this_map
            kotlin.jvm.functions.Function2<E, kotlin.coroutines.Continuation<? super R>, java.lang.Object> r6 = r2.$transform
            r7 = 0
            r10 = r5
            r5 = 0
            r9 = 0
            r8 = r10
            r11 = 0
            kotlinx.coroutines.channels.ChannelIterator r12 = r8.iterator()     // Catch:{ all -> 0x011b }
            r8 = r6
            r6 = r11
        L_0x00a6:
            r2.L$0 = r4     // Catch:{ all -> 0x011b }
            r2.L$1 = r8     // Catch:{ all -> 0x011b }
            r2.L$2 = r10     // Catch:{ all -> 0x011b }
            r2.L$3 = r12     // Catch:{ all -> 0x011b }
            r11 = 1
            r2.label = r11     // Catch:{ all -> 0x011b }
            java.lang.Object r11 = r12.hasNext(r2)     // Catch:{ all -> 0x011b }
            if (r11 != r0) goto L_0x00b8
            return r0
        L_0x00b8:
            r16 = r4
            r4 = r3
            r3 = r11
            r11 = r10
            r10 = r9
            r9 = r8
            r8 = r16
        L_0x00c1:
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x0115 }
            boolean r3 = r3.booleanValue()     // Catch:{ all -> 0x0115 }
            if (r3 == 0) goto L_0x0109
            java.lang.Object r3 = r12.next()     // Catch:{ all -> 0x0115 }
            r13 = 0
            r2.L$0 = r8     // Catch:{ all -> 0x0115 }
            r2.L$1 = r9     // Catch:{ all -> 0x0115 }
            r2.L$2 = r11     // Catch:{ all -> 0x0115 }
            r2.L$3 = r12     // Catch:{ all -> 0x0115 }
            r2.L$4 = r8     // Catch:{ all -> 0x0115 }
            r14 = 2
            r2.label = r14     // Catch:{ all -> 0x0115 }
            java.lang.Object r14 = r9.invoke(r3, r2)     // Catch:{ all -> 0x0115 }
            if (r14 != r0) goto L_0x00e2
            return r0
        L_0x00e2:
            r3 = r12
            r12 = r9
            r9 = r3
            r3 = r14
            r14 = r8
        L_0x00e7:
            r2.L$0 = r14     // Catch:{ all -> 0x0115 }
            r2.L$1 = r12     // Catch:{ all -> 0x0115 }
            r2.L$2 = r11     // Catch:{ all -> 0x0115 }
            r2.L$3 = r9     // Catch:{ all -> 0x0115 }
            r15 = 0
            r2.L$4 = r15     // Catch:{ all -> 0x0115 }
            r15 = 3
            r2.label = r15     // Catch:{ all -> 0x0115 }
            java.lang.Object r3 = r8.send(r3, r2)     // Catch:{ all -> 0x0115 }
            if (r3 != r0) goto L_0x00fc
            return r0
        L_0x00fc:
            r3 = r12
            r12 = r9
            r9 = r10
            r10 = r11
            r11 = r3
            r3 = r4
            r8 = r7
            r7 = r13
            r4 = r14
        L_0x0105:
            r7 = r8
            r8 = r11
            goto L_0x00a6
        L_0x0109:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0115 }
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r11, r10)
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x0115:
            r0 = move-exception
            r3 = r4
            r4 = r7
            r9 = r10
            r10 = r11
            goto L_0x011d
        L_0x011b:
            r0 = move-exception
            r4 = r7
        L_0x011d:
            r6 = r0
            throw r0     // Catch:{ all -> 0x0120 }
        L_0x0120:
            r0 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r10, r6)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$map$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
