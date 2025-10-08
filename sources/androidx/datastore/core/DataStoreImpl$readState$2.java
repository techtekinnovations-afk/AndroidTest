package androidx.datastore.core;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H@"}, d2 = {"<anonymous>", "Landroidx/datastore/core/State;", "T", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "androidx.datastore.core.DataStoreImpl$readState$2", f = "DataStoreImpl.kt", i = {}, l = {218, 226}, m = "invokeSuspend", n = {}, s = {})
/* compiled from: DataStoreImpl.kt */
final class DataStoreImpl$readState$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super State<T>>, Object> {
    final /* synthetic */ boolean $requireLock;
    int label;
    final /* synthetic */ DataStoreImpl<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DataStoreImpl$readState$2(DataStoreImpl<T> dataStoreImpl, boolean z, Continuation<? super DataStoreImpl$readState$2> continuation) {
        super(2, continuation);
        this.this$0 = dataStoreImpl;
        this.$requireLock = z;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new DataStoreImpl$readState$2(this.this$0, this.$requireLock, continuation);
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super State<T>> continuation) {
        return ((DataStoreImpl$readState$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object $result2;
        DataStoreImpl$readState$2 dataStoreImpl$readState$2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                dataStoreImpl$readState$2 = this;
                if (dataStoreImpl$readState$2.this$0.inMemoryCache.getCurrentState() instanceof Final) {
                    return dataStoreImpl$readState$2.this$0.inMemoryCache.getCurrentState();
                }
                dataStoreImpl$readState$2.label = 1;
                if (dataStoreImpl$readState$2.this$0.readAndInitOrPropagateAndThrowFailure(dataStoreImpl$readState$2) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                break;
            case 1:
                dataStoreImpl$readState$2 = this;
                try {
                    ResultKt.throwOnFailure($result);
                    break;
                } catch (Throwable throwable) {
                    return new ReadException(throwable, -1);
                }
            case 2:
                ResultKt.throwOnFailure($result);
                $result2 = $result;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        dataStoreImpl$readState$2.label = 2;
        Object access$readDataAndUpdateCache = dataStoreImpl$readState$2.this$0.readDataAndUpdateCache(dataStoreImpl$readState$2.$requireLock, dataStoreImpl$readState$2);
        if (access$readDataAndUpdateCache == coroutine_suspended) {
            return coroutine_suspended;
        }
        $result2 = $result;
        $result = access$readDataAndUpdateCache;
        Object obj = $result2;
        Object $result3 = (State) $result;
        Object obj2 = obj;
        return $result3;
    }
}
