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
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u0003H@"}, d2 = {"<anonymous>", "", "T", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 8, 0}, xi = 48)
@DebugMetadata(c = "androidx.datastore.core.DataStoreImpl$incrementCollector$2$1", f = "DataStoreImpl.kt", i = {}, l = {134, 135}, m = "invokeSuspend", n = {}, s = {})
/* compiled from: DataStoreImpl.kt */
final class DataStoreImpl$incrementCollector$2$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    int label;
    final /* synthetic */ DataStoreImpl<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DataStoreImpl$incrementCollector$2$1(DataStoreImpl<T> dataStoreImpl, Continuation<? super DataStoreImpl$incrementCollector$2$1> continuation) {
        super(2, continuation);
        this.this$0 = dataStoreImpl;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new DataStoreImpl$incrementCollector$2$1(this.this$0, continuation);
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((DataStoreImpl$incrementCollector$2$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        DataStoreImpl$incrementCollector$2$1 dataStoreImpl$incrementCollector$2$1;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                dataStoreImpl$incrementCollector$2$1 = this;
                dataStoreImpl$incrementCollector$2$1.label = 1;
                if (dataStoreImpl$incrementCollector$2$1.this$0.readAndInit.awaitComplete(dataStoreImpl$incrementCollector$2$1) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                break;
            case 1:
                dataStoreImpl$incrementCollector$2$1 = this;
                ResultKt.throwOnFailure($result);
                break;
            case 2:
                ResultKt.throwOnFailure($result);
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Flow<Unit> conflate = FlowKt.conflate(dataStoreImpl$incrementCollector$2$1.this$0.getCoordinator().getUpdateNotifications());
        final DataStoreImpl<T> dataStoreImpl = dataStoreImpl$incrementCollector$2$1.this$0;
        dataStoreImpl$incrementCollector$2$1.label = 2;
        if (conflate.collect(new FlowCollector() {
            public final Object emit(Unit it, Continuation<? super Unit> $completion) {
                if (dataStoreImpl.inMemoryCache.getCurrentState() instanceof Final) {
                    return Unit.INSTANCE;
                }
                Object access$readDataAndUpdateCache = dataStoreImpl.readDataAndUpdateCache(true, $completion);
                return access$readDataAndUpdateCache == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? access$readDataAndUpdateCache : Unit.INSTANCE;
            }
        }, dataStoreImpl$incrementCollector$2$1) == coroutine_suspended) {
            return coroutine_suspended;
        }
        DataStoreImpl$incrementCollector$2$1 dataStoreImpl$incrementCollector$2$12 = dataStoreImpl$incrementCollector$2$1;
        return Unit.INSTANCE;
    }
}
