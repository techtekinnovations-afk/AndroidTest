package androidx.datastore.core;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H@"}, d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/flow/FlowCollector;"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "androidx.datastore.core.DataStoreImpl$data$1", f = "DataStoreImpl.kt", i = {0, 1, 1}, l = {72, 74, 100}, m = "invokeSuspend", n = {"$this$flow", "$this$flow", "startState"}, s = {"L$0", "L$0", "L$1"})
/* compiled from: DataStoreImpl.kt */
final class DataStoreImpl$data$1 extends SuspendLambda implements Function2<FlowCollector<? super T>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ DataStoreImpl<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DataStoreImpl$data$1(DataStoreImpl<T> dataStoreImpl, Continuation<? super DataStoreImpl$data$1> continuation) {
        super(2, continuation);
        this.this$0 = dataStoreImpl;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        DataStoreImpl$data$1 dataStoreImpl$data$1 = new DataStoreImpl$data$1(this.this$0, continuation);
        dataStoreImpl$data$1.L$0 = obj;
        return dataStoreImpl$data$1;
    }

    public final Object invoke(FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) {
        return ((DataStoreImpl$data$1) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x004f, code lost:
        r12 = (androidx.datastore.core.State) r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0054, code lost:
        if ((r12 instanceof androidx.datastore.core.Data) == false) goto L_0x0077;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0056, code lost:
        r2.L$0 = r3;
        r2.L$1 = r12;
        r2.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x006b, code lost:
        if (r3.emit(((androidx.datastore.core.Data) r12).getValue(), r2) != r0) goto L_0x006e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x006d, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x006e, code lost:
        r10 = r2;
        r2 = r12;
        r12 = r1;
        r1 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0072, code lost:
        r10 = r1;
        r1 = r12;
        r12 = r2;
        r2 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0079, code lost:
        if ((r12 instanceof androidx.datastore.core.UnInitialized) != false) goto L_0x00ed;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x007d, code lost:
        if ((r12 instanceof androidx.datastore.core.ReadException) != false) goto L_0x00e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0081, code lost:
        if ((r12 instanceof androidx.datastore.core.Final) == false) goto L_0x0086;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0085, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0086, code lost:
        r4 = androidx.datastore.core.DataStoreImpl.access$getInMemoryCache$p(r2.this$0).getFlow();
        r6 = r2.this$0;
        r4 = r2.this$0;
        r2.L$0 = null;
        r2.L$1 = null;
        r2.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00dd, code lost:
        if (kotlinx.coroutines.flow.FlowKt.emitAll(r3, kotlinx.coroutines.flow.FlowKt.onCompletion(new androidx.datastore.core.DataStoreImpl$data$1$invokeSuspend$$inlined$map$1(kotlinx.coroutines.flow.FlowKt.dropWhile(kotlinx.coroutines.flow.FlowKt.takeWhile(kotlinx.coroutines.flow.FlowKt.onStart(r4, new androidx.datastore.core.DataStoreImpl$data$1.AnonymousClass1((kotlin.coroutines.Continuation<? super androidx.datastore.core.DataStoreImpl$data$1.AnonymousClass1>) null)), new androidx.datastore.core.DataStoreImpl$data$1.AnonymousClass2((kotlin.coroutines.Continuation<? super androidx.datastore.core.DataStoreImpl$data$1.AnonymousClass2>) null)), new androidx.datastore.core.DataStoreImpl$data$1.AnonymousClass3((kotlin.coroutines.Continuation<? super androidx.datastore.core.DataStoreImpl$data$1.AnonymousClass3>) null))), new androidx.datastore.core.DataStoreImpl$data$1.AnonymousClass5((kotlin.coroutines.Continuation<? super androidx.datastore.core.DataStoreImpl$data$1.AnonymousClass5>) null)), (kotlin.coroutines.Continuation<? super kotlin.Unit>) r2) != r0) goto L_0x00e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00df, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00e0, code lost:
        r12 = r1;
        r0 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00e4, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00ec, code lost:
        throw ((androidx.datastore.core.ReadException) r12).getReadException();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00f8, code lost:
        throw new java.lang.IllegalStateException("This is a bug in DataStore. Please file a bug at: https://issuetracker.google.com/issues/new?component=907884&template=1466542".toString());
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r11.label
            switch(r1) {
                case 0: goto L_0x0030;
                case 1: goto L_0x0024;
                case 2: goto L_0x0017;
                case 3: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r0)
            throw r12
        L_0x0011:
            r0 = r11
            kotlin.ResultKt.throwOnFailure(r12)
            goto L_0x00e2
        L_0x0017:
            r1 = r11
            java.lang.Object r2 = r1.L$1
            androidx.datastore.core.State r2 = (androidx.datastore.core.State) r2
            java.lang.Object r3 = r1.L$0
            kotlinx.coroutines.flow.FlowCollector r3 = (kotlinx.coroutines.flow.FlowCollector) r3
            kotlin.ResultKt.throwOnFailure(r12)
            goto L_0x0072
        L_0x0024:
            r1 = r11
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
            kotlin.ResultKt.throwOnFailure(r12)
            r3 = r2
            r2 = r1
            r1 = r12
            goto L_0x004f
        L_0x0030:
            kotlin.ResultKt.throwOnFailure(r12)
            r1 = r11
            java.lang.Object r2 = r1.L$0
            kotlinx.coroutines.flow.FlowCollector r2 = (kotlinx.coroutines.flow.FlowCollector) r2
            androidx.datastore.core.DataStoreImpl<T> r3 = r1.this$0
            r4 = r1
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r1.L$0 = r2
            r5 = 1
            r1.label = r5
            r5 = 0
            java.lang.Object r3 = r3.readState(r5, r4)
            if (r3 != r0) goto L_0x004a
            return r0
        L_0x004a:
            r10 = r1
            r1 = r12
            r12 = r3
            r3 = r2
            r2 = r10
        L_0x004f:
            androidx.datastore.core.State r12 = (androidx.datastore.core.State) r12
            boolean r4 = r12 instanceof androidx.datastore.core.Data
            if (r4 == 0) goto L_0x0077
            r4 = r12
            androidx.datastore.core.Data r4 = (androidx.datastore.core.Data) r4
            java.lang.Object r4 = r4.getValue()
            r5 = r2
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            r2.L$0 = r3
            r2.L$1 = r12
            r6 = 2
            r2.label = r6
            java.lang.Object r4 = r3.emit(r4, r5)
            if (r4 != r0) goto L_0x006e
            return r0
        L_0x006e:
            r10 = r2
            r2 = r12
            r12 = r1
            r1 = r10
        L_0x0072:
            r10 = r1
            r1 = r12
            r12 = r2
            r2 = r10
            goto L_0x0086
        L_0x0077:
            boolean r4 = r12 instanceof androidx.datastore.core.UnInitialized
            if (r4 != 0) goto L_0x00ed
            boolean r4 = r12 instanceof androidx.datastore.core.ReadException
            if (r4 != 0) goto L_0x00e5
            boolean r4 = r12 instanceof androidx.datastore.core.Final
            if (r4 == 0) goto L_0x0086
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x0086:
            androidx.datastore.core.DataStoreImpl<T> r4 = r2.this$0
            androidx.datastore.core.DataStoreInMemoryCache r4 = r4.inMemoryCache
            kotlinx.coroutines.flow.Flow r4 = r4.getFlow()
            androidx.datastore.core.DataStoreImpl$data$1$1 r5 = new androidx.datastore.core.DataStoreImpl$data$1$1
            androidx.datastore.core.DataStoreImpl<T> r6 = r2.this$0
            r7 = 0
            r5.<init>(r6, r7)
            kotlin.jvm.functions.Function2 r5 = (kotlin.jvm.functions.Function2) r5
            kotlinx.coroutines.flow.Flow r4 = kotlinx.coroutines.flow.FlowKt.onStart(r4, r5)
            androidx.datastore.core.DataStoreImpl$data$1$2 r5 = new androidx.datastore.core.DataStoreImpl$data$1$2
            r5.<init>(r7)
            kotlin.jvm.functions.Function2 r5 = (kotlin.jvm.functions.Function2) r5
            kotlinx.coroutines.flow.Flow r4 = kotlinx.coroutines.flow.FlowKt.takeWhile(r4, r5)
            androidx.datastore.core.DataStoreImpl$data$1$3 r5 = new androidx.datastore.core.DataStoreImpl$data$1$3
            r5.<init>(r12, r7)
            kotlin.jvm.functions.Function2 r5 = (kotlin.jvm.functions.Function2) r5
            kotlinx.coroutines.flow.Flow r12 = kotlinx.coroutines.flow.FlowKt.dropWhile(r4, r5)
            r4 = 0
            r5 = r12
            r6 = 0
            r8 = 0
            androidx.datastore.core.DataStoreImpl$data$1$invokeSuspend$$inlined$map$1 r9 = new androidx.datastore.core.DataStoreImpl$data$1$invokeSuspend$$inlined$map$1
            r9.<init>(r5)
            kotlinx.coroutines.flow.Flow r9 = (kotlinx.coroutines.flow.Flow) r9
            androidx.datastore.core.DataStoreImpl$data$1$5 r12 = new androidx.datastore.core.DataStoreImpl$data$1$5
            androidx.datastore.core.DataStoreImpl<T> r4 = r2.this$0
            r12.<init>(r4, r7)
            kotlin.jvm.functions.Function3 r12 = (kotlin.jvm.functions.Function3) r12
            kotlinx.coroutines.flow.Flow r12 = kotlinx.coroutines.flow.FlowKt.onCompletion(r9, r12)
            r4 = r2
            kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
            r2.L$0 = r7
            r2.L$1 = r7
            r5 = 3
            r2.label = r5
            java.lang.Object r12 = kotlinx.coroutines.flow.FlowKt.emitAll(r3, r12, (kotlin.coroutines.Continuation<? super kotlin.Unit>) r4)
            if (r12 != r0) goto L_0x00e0
            return r0
        L_0x00e0:
            r12 = r1
            r0 = r2
        L_0x00e2:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        L_0x00e5:
            r0 = r12
            androidx.datastore.core.ReadException r0 = (androidx.datastore.core.ReadException) r0
            java.lang.Throwable r0 = r0.getReadException()
            throw r0
        L_0x00ed:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r0 = "This is a bug in DataStore. Please file a bug at: https://issuetracker.google.com/issues/new?component=907884&template=1466542"
            java.lang.String r0 = r0.toString()
            r12.<init>(r0)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl$data$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
