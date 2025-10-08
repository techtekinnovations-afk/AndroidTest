package androidx.datastore.core;

import androidx.datastore.core.Message;
import androidx.datastore.core.UpdatingDataContextElement;
import androidx.datastore.core.handlers.NoOpCorruptionHandler;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;

@Metadata(d1 = {"\u0000°\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0000\u0018\u0000 V*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0002VWBn\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012?\b\u0002\u0010\u0005\u001a9\u00125\u00123\b\u0001\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\b¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u00070\u0006\u0012\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012¢\u0006\u0002\u0010\u0013J\u000e\u00103\u001a\u00020\rH@¢\u0006\u0002\u00104JG\u00105\u001a\u0002H6\"\u0004\b\u0001\u001062\u0006\u00107\u001a\u0002082\u001c\u00109\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H60\f\u0012\u0006\u0012\u0004\u0018\u00010\u000e0:H@\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001¢\u0006\u0002\u0010;J\u001c\u0010<\u001a\u00020\r2\f\u0010=\u001a\b\u0012\u0004\u0012\u00028\u000002H@¢\u0006\u0002\u0010>J\u000e\u0010?\u001a\u00020\rH@¢\u0006\u0002\u00104J\u000e\u0010@\u001a\u00020\rH@¢\u0006\u0002\u00104J\u001c\u0010A\u001a\b\u0012\u0004\u0012\u00028\u00000B2\u0006\u0010C\u001a\u000208H@¢\u0006\u0002\u0010DJ\u000e\u0010E\u001a\u00028\u0000H@¢\u0006\u0002\u00104J\u001c\u0010F\u001a\b\u0012\u0004\u0012\u00028\u00000G2\u0006\u00107\u001a\u000208H@¢\u0006\u0002\u0010DJ\u001c\u0010H\u001a\b\u0012\u0004\u0012\u00028\u00000B2\u0006\u0010C\u001a\u000208H@¢\u0006\u0002\u0010DJI\u0010I\u001a\u00028\u000021\u0010J\u001a-\b\u0001\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(K\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\f\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u00072\u0006\u0010L\u001a\u00020MH@¢\u0006\u0002\u0010NJA\u0010O\u001a\u00028\u000021\u0010J\u001a-\b\u0001\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(K\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\f\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\u0007H@¢\u0006\u0002\u0010PJ \u0010Q\u001a\u00020\u00152\u0006\u0010R\u001a\u00028\u00002\u0006\u0010S\u001a\u000208H@¢\u0006\u0004\bT\u0010UR\u000e\u0010\u0014\u001a\u00020\u0015X\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u001a\u001a\u00020\u001b8BX\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0010X\u0004¢\u0006\u0002\n\u0000R\u001a\u0010 \u001a\b\u0012\u0004\u0012\u00028\u00000!X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00028\u00000%X\u0004¢\u0006\u0002\n\u0000R\u0018\u0010&\u001a\f0'R\b\u0012\u0004\u0012\u00028\u00000\u0000X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0004¢\u0006\u0002\n\u0000R!\u0010(\u001a\b\u0012\u0004\u0012\u00028\u00000)8@X\u0002¢\u0006\f\u001a\u0004\b,\u0010-*\u0004\b*\u0010+R\u001a\u0010.\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000)0/X\u0004¢\u0006\u0002\n\u0000R\u001a\u00100\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000201X\u0004¢\u0006\u0002\n\u0000¨\u0006X"}, d2 = {"Landroidx/datastore/core/DataStoreImpl;", "T", "Landroidx/datastore/core/DataStore;", "storage", "Landroidx/datastore/core/Storage;", "initTasksList", "", "Lkotlin/Function2;", "Landroidx/datastore/core/InitializerApi;", "Lkotlin/ParameterName;", "name", "api", "Lkotlin/coroutines/Continuation;", "", "", "corruptionHandler", "Landroidx/datastore/core/CorruptionHandler;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "(Landroidx/datastore/core/Storage;Ljava/util/List;Landroidx/datastore/core/CorruptionHandler;Lkotlinx/coroutines/CoroutineScope;)V", "collectorCounter", "", "collectorJob", "Lkotlinx/coroutines/Job;", "collectorMutex", "Lkotlinx/coroutines/sync/Mutex;", "coordinator", "Landroidx/datastore/core/InterProcessCoordinator;", "getCoordinator", "()Landroidx/datastore/core/InterProcessCoordinator;", "coordinator$delegate", "Lkotlin/Lazy;", "data", "Lkotlinx/coroutines/flow/Flow;", "getData", "()Lkotlinx/coroutines/flow/Flow;", "inMemoryCache", "Landroidx/datastore/core/DataStoreInMemoryCache;", "readAndInit", "Landroidx/datastore/core/DataStoreImpl$InitDataStore;", "storageConnection", "Landroidx/datastore/core/StorageConnection;", "getStorageConnection$datastore_core_release$delegate", "(Landroidx/datastore/core/DataStoreImpl;)Ljava/lang/Object;", "getStorageConnection$datastore_core_release", "()Landroidx/datastore/core/StorageConnection;", "storageConnectionDelegate", "Lkotlin/Lazy;", "writeActor", "Landroidx/datastore/core/SimpleActor;", "Landroidx/datastore/core/Message$Update;", "decrementCollector", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "doWithWriteFileLock", "R", "hasWriteFileLock", "", "block", "Lkotlin/Function1;", "(ZLkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "handleUpdate", "update", "(Landroidx/datastore/core/Message$Update;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "incrementCollector", "readAndInitOrPropagateAndThrowFailure", "readDataAndUpdateCache", "Landroidx/datastore/core/State;", "requireLock", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "readDataFromFileOrDefault", "readDataOrHandleCorruption", "Landroidx/datastore/core/Data;", "readState", "transformAndWrite", "transform", "t", "callerContext", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/CoroutineContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateData", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "writeData", "newData", "updateCache", "writeData$datastore_core_release", "(Ljava/lang/Object;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "InitDataStore", "datastore-core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: DataStoreImpl.kt */
public final class DataStoreImpl<T> implements DataStore<T> {
    private static final String BUG_MESSAGE = "This is a bug in DataStore. Please file a bug at: https://issuetracker.google.com/issues/new?component=907884&template=1466542";
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private int collectorCounter;
    private Job collectorJob;
    private final Mutex collectorMutex;
    private final Lazy coordinator$delegate;
    private final CorruptionHandler<T> corruptionHandler;
    private final Flow<T> data;
    /* access modifiers changed from: private */
    public final DataStoreInMemoryCache<T> inMemoryCache;
    /* access modifiers changed from: private */
    public final DataStoreImpl<T>.InitDataStore readAndInit;
    private final CoroutineScope scope;
    /* access modifiers changed from: private */
    public final Storage<T> storage;
    /* access modifiers changed from: private */
    public final Lazy<StorageConnection<T>> storageConnectionDelegate;
    /* access modifiers changed from: private */
    public final SimpleActor<Message.Update<T>> writeActor;

    public DataStoreImpl(Storage<T> storage2, List<? extends Function2<? super InitializerApi<T>, ? super Continuation<? super Unit>, ? extends Object>> initTasksList, CorruptionHandler<T> corruptionHandler2, CoroutineScope scope2) {
        Intrinsics.checkNotNullParameter(storage2, "storage");
        Intrinsics.checkNotNullParameter(initTasksList, "initTasksList");
        Intrinsics.checkNotNullParameter(corruptionHandler2, "corruptionHandler");
        Intrinsics.checkNotNullParameter(scope2, "scope");
        this.storage = storage2;
        this.corruptionHandler = corruptionHandler2;
        this.scope = scope2;
        this.data = FlowKt.flow(new DataStoreImpl$data$1(this, (Continuation<? super DataStoreImpl$data$1>) null));
        this.collectorMutex = MutexKt.Mutex$default(false, 1, (Object) null);
        this.inMemoryCache = new DataStoreInMemoryCache<>();
        this.readAndInit = new InitDataStore(this, initTasksList);
        this.storageConnectionDelegate = LazyKt.lazy(new DataStoreImpl$storageConnectionDelegate$1(this));
        this.coordinator$delegate = LazyKt.lazy(new DataStoreImpl$coordinator$2(this));
        this.writeActor = new SimpleActor<>(this.scope, new DataStoreImpl$writeActor$1(this), DataStoreImpl$writeActor$2.INSTANCE, new DataStoreImpl$writeActor$3(this, (Continuation<? super DataStoreImpl$writeActor$3>) null));
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ DataStoreImpl(Storage storage2, List list, CorruptionHandler corruptionHandler2, CoroutineScope coroutineScope, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(storage2, (i & 2) != 0 ? CollectionsKt.emptyList() : list, (i & 4) != 0 ? new NoOpCorruptionHandler() : corruptionHandler2, (i & 8) != 0 ? CoroutineScopeKt.CoroutineScope(Actual_jvmKt.ioDispatcher().plus(SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null))) : coroutineScope);
    }

    public Flow<T> getData() {
        return this.data;
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x005e A[Catch:{ all -> 0x007f }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object incrementCollector(kotlin.coroutines.Continuation<? super kotlin.Unit> r15) {
        /*
            r14 = this;
            boolean r0 = r15 instanceof androidx.datastore.core.DataStoreImpl$incrementCollector$1
            if (r0 == 0) goto L_0x0014
            r0 = r15
            androidx.datastore.core.DataStoreImpl$incrementCollector$1 r0 = (androidx.datastore.core.DataStoreImpl$incrementCollector$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r15 = r0.label
            int r15 = r15 - r2
            r0.label = r15
            goto L_0x0019
        L_0x0014:
            androidx.datastore.core.DataStoreImpl$incrementCollector$1 r0 = new androidx.datastore.core.DataStoreImpl$incrementCollector$1
            r0.<init>(r14, r15)
        L_0x0019:
            r15 = r0
            java.lang.Object r1 = r15.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r15.label
            r3 = 1
            switch(r2) {
                case 0: goto L_0x003d;
                case 1: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r15 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r15.<init>(r0)
            throw r15
        L_0x002e:
            r0 = 0
            r2 = 0
            java.lang.Object r4 = r15.L$1
            kotlinx.coroutines.sync.Mutex r4 = (kotlinx.coroutines.sync.Mutex) r4
            java.lang.Object r5 = r15.L$0
            androidx.datastore.core.DataStoreImpl r5 = (androidx.datastore.core.DataStoreImpl) r5
            kotlin.ResultKt.throwOnFailure(r1)
            r6 = r0
            goto L_0x0053
        L_0x003d:
            kotlin.ResultKt.throwOnFailure(r1)
            r5 = r14
            kotlinx.coroutines.sync.Mutex r4 = r5.collectorMutex
            r2 = 0
            r6 = 0
            r15.L$0 = r5
            r15.L$1 = r4
            r15.label = r3
            java.lang.Object r7 = r4.lock(r2, r15)
            if (r7 != r0) goto L_0x0053
            return r0
        L_0x0053:
            r0 = 0
            int r7 = r5.collectorCounter     // Catch:{ all -> 0x007f }
            int r7 = r7 + r3
            r5.collectorCounter = r7     // Catch:{ all -> 0x007f }
            int r7 = r5.collectorCounter     // Catch:{ all -> 0x007f }
            if (r7 != r3) goto L_0x0074
            kotlinx.coroutines.CoroutineScope r8 = r5.scope     // Catch:{ all -> 0x007f }
            androidx.datastore.core.DataStoreImpl$incrementCollector$2$1 r3 = new androidx.datastore.core.DataStoreImpl$incrementCollector$2$1     // Catch:{ all -> 0x007f }
            r7 = 0
            r3.<init>(r5, r7)     // Catch:{ all -> 0x007f }
            r11 = r3
            kotlin.jvm.functions.Function2 r11 = (kotlin.jvm.functions.Function2) r11     // Catch:{ all -> 0x007f }
            r12 = 3
            r13 = 0
            r9 = 0
            r10 = 0
            kotlinx.coroutines.Job r3 = kotlinx.coroutines.BuildersKt__Builders_commonKt.launch$default(r8, r9, r10, r11, r12, r13)     // Catch:{ all -> 0x007f }
            r5.collectorJob = r3     // Catch:{ all -> 0x007f }
        L_0x0074:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x007f }
            r4.unlock(r2)
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x007f:
            r0 = move-exception
            r4.unlock(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl.incrementCollector(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x005e A[Catch:{ all -> 0x0073 }] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object decrementCollector(kotlin.coroutines.Continuation<? super kotlin.Unit> r10) {
        /*
            r9 = this;
            boolean r0 = r10 instanceof androidx.datastore.core.DataStoreImpl$decrementCollector$1
            if (r0 == 0) goto L_0x0014
            r0 = r10
            androidx.datastore.core.DataStoreImpl$decrementCollector$1 r0 = (androidx.datastore.core.DataStoreImpl$decrementCollector$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r10 = r0.label
            int r10 = r10 - r2
            r0.label = r10
            goto L_0x0019
        L_0x0014:
            androidx.datastore.core.DataStoreImpl$decrementCollector$1 r0 = new androidx.datastore.core.DataStoreImpl$decrementCollector$1
            r0.<init>(r9, r10)
        L_0x0019:
            java.lang.Object r10 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            switch(r2) {
                case 0: goto L_0x003b;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r0)
            throw r10
        L_0x002d:
            r1 = 0
            r2 = 0
            java.lang.Object r4 = r0.L$1
            kotlinx.coroutines.sync.Mutex r4 = (kotlinx.coroutines.sync.Mutex) r4
            java.lang.Object r5 = r0.L$0
            androidx.datastore.core.DataStoreImpl r5 = (androidx.datastore.core.DataStoreImpl) r5
            kotlin.ResultKt.throwOnFailure(r10)
            goto L_0x0052
        L_0x003b:
            kotlin.ResultKt.throwOnFailure(r10)
            r5 = r9
            kotlinx.coroutines.sync.Mutex r4 = r5.collectorMutex
            r2 = 0
            r6 = 0
            r0.L$0 = r5
            r0.L$1 = r4
            r0.label = r3
            java.lang.Object r7 = r4.lock(r2, r0)
            if (r7 != r1) goto L_0x0051
            return r1
        L_0x0051:
            r1 = r6
        L_0x0052:
            r6 = 0
            int r7 = r5.collectorCounter     // Catch:{ all -> 0x0073 }
            int r7 = r7 + -1
            r5.collectorCounter = r7     // Catch:{ all -> 0x0073 }
            int r7 = r5.collectorCounter     // Catch:{ all -> 0x0073 }
            if (r7 != 0) goto L_0x0068
            kotlinx.coroutines.Job r7 = r5.collectorJob     // Catch:{ all -> 0x0073 }
            r8 = 0
            if (r7 == 0) goto L_0x0066
            kotlinx.coroutines.Job.DefaultImpls.cancel$default((kotlinx.coroutines.Job) r7, (java.util.concurrent.CancellationException) r8, (int) r3, (java.lang.Object) r8)     // Catch:{ all -> 0x0073 }
        L_0x0066:
            r5.collectorJob = r8     // Catch:{ all -> 0x0073 }
        L_0x0068:
            kotlin.Unit r3 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0073 }
            r4.unlock(r2)
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            return r1
        L_0x0073:
            r3 = move-exception
            r4.unlock(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl.decrementCollector(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public Object updateData(Function2<? super T, ? super Continuation<? super T>, ? extends Object> transform, Continuation<? super T> $completion) {
        UpdatingDataContextElement parentContextElement = (UpdatingDataContextElement) $completion.getContext().get(UpdatingDataContextElement.Companion.Key.INSTANCE);
        if (parentContextElement != null) {
            parentContextElement.checkNotUpdating(this);
        }
        return BuildersKt.withContext(new UpdatingDataContextElement(parentContextElement, this), new DataStoreImpl$updateData$2(this, transform, (Continuation<? super DataStoreImpl$updateData$2>) null), $completion);
    }

    private static Object getStorageConnection$datastore_core_release$delegate(DataStoreImpl<Object> dataStoreImpl) {
        return dataStoreImpl.storageConnectionDelegate;
    }

    public final StorageConnection<T> getStorageConnection$datastore_core_release() {
        return this.storageConnectionDelegate.getValue();
    }

    /* access modifiers changed from: private */
    public final InterProcessCoordinator getCoordinator() {
        return (InterProcessCoordinator) this.coordinator$delegate.getValue();
    }

    /* access modifiers changed from: private */
    public final Object readState(boolean requireLock, Continuation<? super State<T>> $completion) {
        return BuildersKt.withContext(this.scope.getCoroutineContext(), new DataStoreImpl$readState$2(this, requireLock, (Continuation<? super DataStoreImpl$readState$2>) null), $completion);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        r5 = r4.getTransform();
        r6 = r4.getCallerContext();
        r0.L$0 = r2;
        r0.L$1 = null;
        r0.L$2 = null;
        r0.label = 3;
        r5 = r3.transformAndWrite(r5, r6, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00c5, code lost:
        if (r5 != r1) goto L_0x00c8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00c7, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00c8, code lost:
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
        r10 = kotlin.Result.m6constructorimpl(r5);
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object handleUpdate(androidx.datastore.core.Message.Update<T> r10, kotlin.coroutines.Continuation<? super kotlin.Unit> r11) {
        /*
            r9 = this;
            boolean r0 = r11 instanceof androidx.datastore.core.DataStoreImpl$handleUpdate$1
            if (r0 == 0) goto L_0x0014
            r0 = r11
            androidx.datastore.core.DataStoreImpl$handleUpdate$1 r0 = (androidx.datastore.core.DataStoreImpl$handleUpdate$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r11 = r0.label
            int r11 = r11 - r2
            r0.label = r11
            goto L_0x0019
        L_0x0014:
            androidx.datastore.core.DataStoreImpl$handleUpdate$1 r0 = new androidx.datastore.core.DataStoreImpl$handleUpdate$1
            r0.<init>(r9, r11)
        L_0x0019:
            java.lang.Object r11 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            switch(r2) {
                case 0: goto L_0x005a;
                case 1: goto L_0x004d;
                case 2: goto L_0x0037;
                case 3: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x002c:
            r10 = 0
            java.lang.Object r1 = r0.L$0
            kotlinx.coroutines.CompletableDeferred r1 = (kotlinx.coroutines.CompletableDeferred) r1
            kotlin.ResultKt.throwOnFailure(r11)     // Catch:{ all -> 0x0057 }
            r5 = r11
            goto L_0x00c9
        L_0x0037:
            r10 = 0
            java.lang.Object r2 = r0.L$2
            kotlinx.coroutines.CompletableDeferred r2 = (kotlinx.coroutines.CompletableDeferred) r2
            java.lang.Object r3 = r0.L$1
            androidx.datastore.core.DataStoreImpl r3 = (androidx.datastore.core.DataStoreImpl) r3
            java.lang.Object r4 = r0.L$0
            androidx.datastore.core.Message$Update r4 = (androidx.datastore.core.Message.Update) r4
            kotlin.ResultKt.throwOnFailure(r11)     // Catch:{ all -> 0x0049 }
            goto L_0x00af
        L_0x0049:
            r10 = move-exception
            r1 = r2
            goto L_0x00f3
        L_0x004d:
            r10 = 0
            java.lang.Object r1 = r0.L$0
            kotlinx.coroutines.CompletableDeferred r1 = (kotlinx.coroutines.CompletableDeferred) r1
            kotlin.ResultKt.throwOnFailure(r11)     // Catch:{ all -> 0x0057 }
            r5 = r11
            goto L_0x008b
        L_0x0057:
            r10 = move-exception
            goto L_0x00f3
        L_0x005a:
            kotlin.ResultKt.throwOnFailure(r11)
            r2 = r9
            r4 = r10
            kotlinx.coroutines.CompletableDeferred r10 = r4.getAck()
            kotlin.Result$Companion r3 = kotlin.Result.Companion     // Catch:{ all -> 0x00ef }
            androidx.datastore.core.DataStoreImpl r2 = (androidx.datastore.core.DataStoreImpl) r2     // Catch:{ all -> 0x00ef }
            r3 = r2
            r2 = 0
            androidx.datastore.core.DataStoreInMemoryCache<T> r5 = r3.inMemoryCache     // Catch:{ all -> 0x00ef }
            androidx.datastore.core.State r5 = r5.getCurrentState()     // Catch:{ all -> 0x00ef }
            boolean r6 = r5 instanceof androidx.datastore.core.Data     // Catch:{ all -> 0x00ef }
            r7 = 1
            if (r6 == 0) goto L_0x008d
            kotlin.jvm.functions.Function2 r5 = r4.getTransform()     // Catch:{ all -> 0x00ef }
            kotlin.coroutines.CoroutineContext r6 = r4.getCallerContext()     // Catch:{ all -> 0x00ef }
            r0.L$0 = r10     // Catch:{ all -> 0x00ef }
            r0.label = r7     // Catch:{ all -> 0x00ef }
            java.lang.Object r5 = r3.transformAndWrite(r5, r6, r0)     // Catch:{ all -> 0x00ef }
            if (r5 != r1) goto L_0x0089
            return r1
        L_0x0089:
            r1 = r10
            r10 = r2
        L_0x008b:
            goto L_0x00ca
        L_0x008d:
            boolean r6 = r5 instanceof androidx.datastore.core.ReadException     // Catch:{ all -> 0x00ef }
            if (r6 == 0) goto L_0x0092
            goto L_0x0094
        L_0x0092:
            boolean r7 = r5 instanceof androidx.datastore.core.UnInitialized     // Catch:{ all -> 0x00ef }
        L_0x0094:
            if (r7 == 0) goto L_0x00dd
            androidx.datastore.core.State r6 = r4.getLastState()     // Catch:{ all -> 0x00ef }
            if (r5 != r6) goto L_0x00d0
            r0.L$0 = r4     // Catch:{ all -> 0x00ef }
            r0.L$1 = r3     // Catch:{ all -> 0x00ef }
            r0.L$2 = r10     // Catch:{ all -> 0x00ef }
            r5 = 2
            r0.label = r5     // Catch:{ all -> 0x00ef }
            java.lang.Object r5 = r3.readAndInitOrPropagateAndThrowFailure(r0)     // Catch:{ all -> 0x00ef }
            if (r5 != r1) goto L_0x00ac
            return r1
        L_0x00ac:
            r8 = r2
            r2 = r10
            r10 = r8
        L_0x00af:
            kotlin.jvm.functions.Function2 r5 = r4.getTransform()     // Catch:{ all -> 0x0049 }
            kotlin.coroutines.CoroutineContext r6 = r4.getCallerContext()     // Catch:{ all -> 0x0049 }
            r0.L$0 = r2     // Catch:{ all -> 0x0049 }
            r7 = 0
            r0.L$1 = r7     // Catch:{ all -> 0x0049 }
            r0.L$2 = r7     // Catch:{ all -> 0x0049 }
            r7 = 3
            r0.label = r7     // Catch:{ all -> 0x0049 }
            java.lang.Object r5 = r3.transformAndWrite(r5, r6, r0)     // Catch:{ all -> 0x0049 }
            if (r5 != r1) goto L_0x00c8
            return r1
        L_0x00c8:
            r1 = r2
        L_0x00c9:
        L_0x00ca:
            java.lang.Object r10 = kotlin.Result.m6constructorimpl(r5)     // Catch:{ all -> 0x0057 }
            goto L_0x00fd
        L_0x00d0:
            java.lang.String r1 = "null cannot be cast to non-null type androidx.datastore.core.ReadException<T of androidx.datastore.core.DataStoreImpl.handleUpdate$lambda$2>"
            kotlin.jvm.internal.Intrinsics.checkNotNull(r5, r1)     // Catch:{ all -> 0x00ef }
            r1 = r5
            androidx.datastore.core.ReadException r1 = (androidx.datastore.core.ReadException) r1     // Catch:{ all -> 0x00ef }
            java.lang.Throwable r1 = r1.getReadException()     // Catch:{ all -> 0x00ef }
            throw r1     // Catch:{ all -> 0x00ef }
        L_0x00dd:
            boolean r1 = r5 instanceof androidx.datastore.core.Final     // Catch:{ all -> 0x00ef }
            if (r1 == 0) goto L_0x00e9
            r1 = r5
            androidx.datastore.core.Final r1 = (androidx.datastore.core.Final) r1     // Catch:{ all -> 0x00ef }
            java.lang.Throwable r1 = r1.getFinalException()     // Catch:{ all -> 0x00ef }
            goto L_0x00ee
        L_0x00e9:
            kotlin.NoWhenBranchMatchedException r1 = new kotlin.NoWhenBranchMatchedException     // Catch:{ all -> 0x00ef }
            r1.<init>()     // Catch:{ all -> 0x00ef }
        L_0x00ee:
            throw r1     // Catch:{ all -> 0x00ef }
        L_0x00ef:
            r1 = move-exception
            r8 = r1
            r1 = r10
            r10 = r8
        L_0x00f3:
            kotlin.Result$Companion r2 = kotlin.Result.Companion
            java.lang.Object r10 = kotlin.ResultKt.createFailure(r10)
            java.lang.Object r10 = kotlin.Result.m6constructorimpl(r10)
        L_0x00fd:
            kotlinx.coroutines.CompletableDeferredKt.completeWith(r1, r10)
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl.handleUpdate(androidx.datastore.core.Message$Update, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x006b A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object readAndInitOrPropagateAndThrowFailure(kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof androidx.datastore.core.DataStoreImpl$readAndInitOrPropagateAndThrowFailure$1
            if (r0 == 0) goto L_0x0014
            r0 = r8
            androidx.datastore.core.DataStoreImpl$readAndInitOrPropagateAndThrowFailure$1 r0 = (androidx.datastore.core.DataStoreImpl$readAndInitOrPropagateAndThrowFailure$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L_0x0019
        L_0x0014:
            androidx.datastore.core.DataStoreImpl$readAndInitOrPropagateAndThrowFailure$1 r0 = new androidx.datastore.core.DataStoreImpl$readAndInitOrPropagateAndThrowFailure$1
            r0.<init>(r7, r8)
        L_0x0019:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            switch(r2) {
                case 0: goto L_0x0041;
                case 1: goto L_0x0038;
                case 2: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r0)
            throw r8
        L_0x002c:
            int r1 = r0.I$0
            java.lang.Object r2 = r0.L$0
            androidx.datastore.core.DataStoreImpl r2 = (androidx.datastore.core.DataStoreImpl) r2
            kotlin.ResultKt.throwOnFailure(r8)     // Catch:{ all -> 0x0036 }
            goto L_0x006d
        L_0x0036:
            r3 = move-exception
            goto L_0x0075
        L_0x0038:
            java.lang.Object r2 = r0.L$0
            androidx.datastore.core.DataStoreImpl r2 = (androidx.datastore.core.DataStoreImpl) r2
            kotlin.ResultKt.throwOnFailure(r8)
            r3 = r8
            goto L_0x0055
        L_0x0041:
            kotlin.ResultKt.throwOnFailure(r8)
            r2 = r7
            androidx.datastore.core.InterProcessCoordinator r3 = r2.getCoordinator()
            r0.L$0 = r2
            r4 = 1
            r0.label = r4
            java.lang.Object r3 = r3.getVersion(r0)
            if (r3 != r1) goto L_0x0055
            return r1
        L_0x0055:
            java.lang.Number r3 = (java.lang.Number) r3
            int r3 = r3.intValue()
            androidx.datastore.core.DataStoreImpl<T>$InitDataStore r4 = r2.readAndInit     // Catch:{ all -> 0x0071 }
            r0.L$0 = r2     // Catch:{ all -> 0x0071 }
            r0.I$0 = r3     // Catch:{ all -> 0x0071 }
            r5 = 2
            r0.label = r5     // Catch:{ all -> 0x0071 }
            java.lang.Object r4 = r4.runIfNeeded(r0)     // Catch:{ all -> 0x0071 }
            if (r4 != r1) goto L_0x006c
            return r1
        L_0x006c:
            r1 = r3
        L_0x006d:
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            return r3
        L_0x0071:
            r1 = move-exception
            r6 = r3
            r3 = r1
            r1 = r6
        L_0x0075:
            androidx.datastore.core.DataStoreInMemoryCache<T> r4 = r2.inMemoryCache
            androidx.datastore.core.ReadException r5 = new androidx.datastore.core.ReadException
            r5.<init>(r3, r1)
            androidx.datastore.core.State r5 = (androidx.datastore.core.State) r5
            r4.tryUpdate(r5)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl.readAndInitOrPropagateAndThrowFailure(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0082  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0087  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00ae  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00dc  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object readDataAndUpdateCache(boolean r9, kotlin.coroutines.Continuation<? super androidx.datastore.core.State<T>> r10) {
        /*
            r8 = this;
            boolean r0 = r10 instanceof androidx.datastore.core.DataStoreImpl$readDataAndUpdateCache$1
            if (r0 == 0) goto L_0x0014
            r0 = r10
            androidx.datastore.core.DataStoreImpl$readDataAndUpdateCache$1 r0 = (androidx.datastore.core.DataStoreImpl$readDataAndUpdateCache$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r10 = r0.label
            int r10 = r10 - r2
            r0.label = r10
            goto L_0x0019
        L_0x0014:
            androidx.datastore.core.DataStoreImpl$readDataAndUpdateCache$1 r0 = new androidx.datastore.core.DataStoreImpl$readDataAndUpdateCache$1
            r0.<init>(r8, r10)
        L_0x0019:
            java.lang.Object r10 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            switch(r2) {
                case 0: goto L_0x0053;
                case 1: goto L_0x0040;
                case 2: goto L_0x0036;
                case 3: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L_0x002c:
            java.lang.Object r9 = r0.L$0
            androidx.datastore.core.DataStoreImpl r9 = (androidx.datastore.core.DataStoreImpl) r9
            kotlin.ResultKt.throwOnFailure(r10)
            r2 = r10
            goto L_0x00c7
        L_0x0036:
            java.lang.Object r9 = r0.L$0
            androidx.datastore.core.DataStoreImpl r9 = (androidx.datastore.core.DataStoreImpl) r9
            kotlin.ResultKt.throwOnFailure(r10)
            r2 = r10
            goto L_0x00ab
        L_0x0040:
            boolean r9 = r0.Z$0
            java.lang.Object r2 = r0.L$1
            androidx.datastore.core.State r2 = (androidx.datastore.core.State) r2
            java.lang.Object r3 = r0.L$0
            androidx.datastore.core.DataStoreImpl r3 = (androidx.datastore.core.DataStoreImpl) r3
            kotlin.ResultKt.throwOnFailure(r10)
            r4 = r2
            r2 = r9
            r9 = r3
            r3 = r4
            r4 = r10
            goto L_0x0078
        L_0x0053:
            kotlin.ResultKt.throwOnFailure(r10)
            r2 = r8
            androidx.datastore.core.DataStoreInMemoryCache<T> r3 = r2.inMemoryCache
            androidx.datastore.core.State r3 = r3.getCurrentState()
            boolean r4 = r3 instanceof androidx.datastore.core.UnInitialized
            if (r4 != 0) goto L_0x00e2
            androidx.datastore.core.InterProcessCoordinator r4 = r2.getCoordinator()
            r0.L$0 = r2
            r0.L$1 = r3
            r0.Z$0 = r9
            r5 = 1
            r0.label = r5
            java.lang.Object r4 = r4.getVersion(r0)
            if (r4 != r1) goto L_0x0075
            return r1
        L_0x0075:
            r7 = r2
            r2 = r9
            r9 = r7
        L_0x0078:
            java.lang.Number r4 = (java.lang.Number) r4
            int r4 = r4.intValue()
            boolean r5 = r3 instanceof androidx.datastore.core.Data
            if (r5 == 0) goto L_0x0087
            int r5 = r3.getVersion()
            goto L_0x0088
        L_0x0087:
            r5 = -1
        L_0x0088:
            boolean r6 = r3 instanceof androidx.datastore.core.Data
            if (r6 == 0) goto L_0x008f
            if (r4 != r5) goto L_0x008f
            return r3
        L_0x008f:
            r3 = 0
            if (r2 == 0) goto L_0x00ae
            androidx.datastore.core.InterProcessCoordinator r2 = r9.getCoordinator()
            androidx.datastore.core.DataStoreImpl$readDataAndUpdateCache$3 r4 = new androidx.datastore.core.DataStoreImpl$readDataAndUpdateCache$3
            r4.<init>(r9, r3)
            kotlin.jvm.functions.Function1 r4 = (kotlin.jvm.functions.Function1) r4
            r0.L$0 = r9
            r0.L$1 = r3
            r3 = 2
            r0.label = r3
            java.lang.Object r2 = r2.lock(r4, r0)
            if (r2 != r1) goto L_0x00ab
            return r1
        L_0x00ab:
            kotlin.Pair r2 = (kotlin.Pair) r2
            goto L_0x00c9
        L_0x00ae:
            androidx.datastore.core.InterProcessCoordinator r2 = r9.getCoordinator()
            androidx.datastore.core.DataStoreImpl$readDataAndUpdateCache$4 r4 = new androidx.datastore.core.DataStoreImpl$readDataAndUpdateCache$4
            r4.<init>(r9, r5, r3)
            kotlin.jvm.functions.Function2 r4 = (kotlin.jvm.functions.Function2) r4
            r0.L$0 = r9
            r0.L$1 = r3
            r3 = 3
            r0.label = r3
            java.lang.Object r2 = r2.tryLock(r4, r0)
            if (r2 != r1) goto L_0x00c7
            return r1
        L_0x00c7:
            kotlin.Pair r2 = (kotlin.Pair) r2
        L_0x00c9:
            java.lang.Object r1 = r2.component1()
            androidx.datastore.core.State r1 = (androidx.datastore.core.State) r1
            java.lang.Object r2 = r2.component2()
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 == 0) goto L_0x00e1
            androidx.datastore.core.DataStoreInMemoryCache<T> r2 = r9.inMemoryCache
            r2.tryUpdate(r1)
        L_0x00e1:
            return r1
        L_0x00e2:
            r1 = 0
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r4 = "This is a bug in DataStore. Please file a bug at: https://issuetracker.google.com/issues/new?component=907884&template=1466542"
            java.lang.String r4 = r4.toString()
            r1.<init>(r4)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl.readDataAndUpdateCache(boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* access modifiers changed from: private */
    public final Object readDataFromFileOrDefault(Continuation<? super T> $completion) {
        return StorageConnectionKt.readData(getStorageConnection$datastore_core_release(), $completion);
    }

    /* access modifiers changed from: private */
    public final Object transformAndWrite(Function2<? super T, ? super Continuation<? super T>, ? extends Object> transform, CoroutineContext callerContext, Continuation<? super T> $completion) {
        return getCoordinator().lock(new DataStoreImpl$transformAndWrite$2(this, callerContext, transform, (Continuation<? super DataStoreImpl$transformAndWrite$2>) null), $completion);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object writeData$datastore_core_release(T r10, boolean r11, kotlin.coroutines.Continuation<? super java.lang.Integer> r12) {
        /*
            r9 = this;
            boolean r0 = r12 instanceof androidx.datastore.core.DataStoreImpl$writeData$1
            if (r0 == 0) goto L_0x0014
            r0 = r12
            androidx.datastore.core.DataStoreImpl$writeData$1 r0 = (androidx.datastore.core.DataStoreImpl$writeData$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r12 = r0.label
            int r12 = r12 - r2
            r0.label = r12
            goto L_0x0019
        L_0x0014:
            androidx.datastore.core.DataStoreImpl$writeData$1 r0 = new androidx.datastore.core.DataStoreImpl$writeData$1
            r0.<init>(r9, r12)
        L_0x0019:
            java.lang.Object r12 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            switch(r2) {
                case 0: goto L_0x0034;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x002c:
            java.lang.Object r10 = r0.L$0
            kotlin.jvm.internal.Ref$IntRef r10 = (kotlin.jvm.internal.Ref.IntRef) r10
            kotlin.ResultKt.throwOnFailure(r12)
            goto L_0x005d
        L_0x0034:
            kotlin.ResultKt.throwOnFailure(r12)
            r4 = r9
            r5 = r10
            kotlin.jvm.internal.Ref$IntRef r3 = new kotlin.jvm.internal.Ref$IntRef
            r3.<init>()
            androidx.datastore.core.StorageConnection r10 = r4.getStorageConnection$datastore_core_release()
            androidx.datastore.core.DataStoreImpl$writeData$2 r2 = new androidx.datastore.core.DataStoreImpl$writeData$2
            r8 = 1
            if (r11 == 0) goto L_0x0049
            r6 = r8
            goto L_0x004b
        L_0x0049:
            r11 = 0
            r6 = r11
        L_0x004b:
            r7 = 0
            r2.<init>(r3, r4, r5, r6, r7)
            kotlin.jvm.functions.Function2 r2 = (kotlin.jvm.functions.Function2) r2
            r0.L$0 = r3
            r0.label = r8
            java.lang.Object r10 = r10.writeScope(r2, r0)
            if (r10 != r1) goto L_0x005c
            return r1
        L_0x005c:
            r10 = r3
        L_0x005d:
            int r11 = r10.element
            java.lang.Integer r11 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r11)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl.writeData$datastore_core_release(java.lang.Object, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00b3, code lost:
        if (r6 == null) goto L_0x00ba;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        r11 = r6.hashCode();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00ba, code lost:
        r11 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00bb, code lost:
        r8 = r7.getCoordinator();
        r0.L$0 = r7;
        r0.L$1 = r6;
        r0.Z$0 = r2;
        r0.I$0 = r11;
        r0.label = 2;
        r8 = r8.getVersion(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00ce, code lost:
        if (r8 != r1) goto L_0x00d1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00d0, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00ef, code lost:
        r6 = ((java.lang.Number) r6).intValue();
        r0.L$0 = r2;
        r0.Z$0 = r11;
        r0.label = 4;
        r7 = r2.getCoordinator().tryLock(new androidx.datastore.core.DataStoreImpl$readDataOrHandleCorruption$2(r2, r6, (kotlin.coroutines.Continuation<? super androidx.datastore.core.DataStoreImpl$readDataOrHandleCorruption$2>) null), r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x010b, code lost:
        if (r7 != r1) goto L_0x010e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x010d, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x010e, code lost:
        r9 = (androidx.datastore.core.Data) r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0111, code lost:
        r7 = r2;
        r2 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:?, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:?, code lost:
        return new androidx.datastore.core.Data(r6, r11, ((java.lang.Number) r8).intValue());
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0077  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x012e A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x012f  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0027  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object readDataOrHandleCorruption(boolean r11, kotlin.coroutines.Continuation<? super androidx.datastore.core.Data<T>> r12) {
        /*
            r10 = this;
            boolean r0 = r12 instanceof androidx.datastore.core.DataStoreImpl$readDataOrHandleCorruption$1
            if (r0 == 0) goto L_0x0014
            r0 = r12
            androidx.datastore.core.DataStoreImpl$readDataOrHandleCorruption$1 r0 = (androidx.datastore.core.DataStoreImpl$readDataOrHandleCorruption$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r12 = r0.label
            int r12 = r12 - r2
            r0.label = r12
            goto L_0x0019
        L_0x0014:
            androidx.datastore.core.DataStoreImpl$readDataOrHandleCorruption$1 r0 = new androidx.datastore.core.DataStoreImpl$readDataOrHandleCorruption$1
            r0.<init>(r10, r12)
        L_0x0019:
            java.lang.Object r12 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            r4 = 0
            r5 = 0
            switch(r2) {
                case 0: goto L_0x009c;
                case 1: goto L_0x008b;
                case 2: goto L_0x0077;
                case 3: goto L_0x006b;
                case 4: goto L_0x005f;
                case 5: goto L_0x0043;
                case 6: goto L_0x002f;
                default: goto L_0x0027;
            }
        L_0x0027:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r12)
            throw r11
        L_0x002f:
            java.lang.Object r11 = r0.L$2
            kotlin.jvm.internal.Ref$IntRef r11 = (kotlin.jvm.internal.Ref.IntRef) r11
            java.lang.Object r1 = r0.L$1
            kotlin.jvm.internal.Ref$ObjectRef r1 = (kotlin.jvm.internal.Ref.ObjectRef) r1
            java.lang.Object r2 = r0.L$0
            androidx.datastore.core.CorruptionException r2 = (androidx.datastore.core.CorruptionException) r2
            kotlin.ResultKt.throwOnFailure(r12)     // Catch:{ all -> 0x0040 }
            goto L_0x015b
        L_0x0040:
            r11 = move-exception
            goto L_0x0171
        L_0x0043:
            boolean r11 = r0.Z$0
            java.lang.Object r2 = r0.L$3
            kotlin.jvm.internal.Ref$ObjectRef r2 = (kotlin.jvm.internal.Ref.ObjectRef) r2
            java.lang.Object r6 = r0.L$2
            kotlin.jvm.internal.Ref$ObjectRef r6 = (kotlin.jvm.internal.Ref.ObjectRef) r6
            java.lang.Object r7 = r0.L$1
            androidx.datastore.core.CorruptionException r7 = (androidx.datastore.core.CorruptionException) r7
            java.lang.Object r8 = r0.L$0
            androidx.datastore.core.DataStoreImpl r8 = (androidx.datastore.core.DataStoreImpl) r8
            kotlin.ResultKt.throwOnFailure(r12)
            r9 = r6
            r6 = r2
            r2 = r9
            r9 = r8
            r8 = r12
            goto L_0x0133
        L_0x005f:
            boolean r11 = r0.Z$0
            java.lang.Object r2 = r0.L$0
            androidx.datastore.core.DataStoreImpl r2 = (androidx.datastore.core.DataStoreImpl) r2
            kotlin.ResultKt.throwOnFailure(r12)     // Catch:{ CorruptionException -> 0x0098 }
            r7 = r12
            goto L_0x010e
        L_0x006b:
            boolean r11 = r0.Z$0
            java.lang.Object r2 = r0.L$0
            androidx.datastore.core.DataStoreImpl r2 = (androidx.datastore.core.DataStoreImpl) r2
            kotlin.ResultKt.throwOnFailure(r12)     // Catch:{ CorruptionException -> 0x0098 }
            r6 = r12
            goto L_0x00ef
        L_0x0077:
            int r11 = r0.I$0
            boolean r2 = r0.Z$0
            java.lang.Object r6 = r0.L$1
            java.lang.Object r7 = r0.L$0
            androidx.datastore.core.DataStoreImpl r7 = (androidx.datastore.core.DataStoreImpl) r7
            kotlin.ResultKt.throwOnFailure(r12)     // Catch:{ CorruptionException -> 0x0086 }
            r8 = r12
            goto L_0x00d1
        L_0x0086:
            r6 = move-exception
            r11 = r2
            r8 = r7
            goto L_0x0114
        L_0x008b:
            boolean r11 = r0.Z$0
            java.lang.Object r2 = r0.L$0
            androidx.datastore.core.DataStoreImpl r2 = (androidx.datastore.core.DataStoreImpl) r2
            kotlin.ResultKt.throwOnFailure(r12)     // Catch:{ CorruptionException -> 0x0098 }
            r6 = r12
            r7 = r2
            r2 = r11
            goto L_0x00b2
        L_0x0098:
            r6 = move-exception
            r8 = r2
            goto L_0x0114
        L_0x009c:
            kotlin.ResultKt.throwOnFailure(r12)
            r2 = r10
            if (r11 == 0) goto L_0x00dd
            r0.L$0 = r2     // Catch:{ CorruptionException -> 0x0098 }
            r0.Z$0 = r11     // Catch:{ CorruptionException -> 0x0098 }
            r0.label = r3     // Catch:{ CorruptionException -> 0x0098 }
            java.lang.Object r6 = r2.readDataFromFileOrDefault(r0)     // Catch:{ CorruptionException -> 0x0098 }
            if (r6 != r1) goto L_0x00b0
            return r1
        L_0x00b0:
            r7 = r2
            r2 = r11
        L_0x00b2:
            if (r6 == 0) goto L_0x00ba
            int r11 = r6.hashCode()     // Catch:{ CorruptionException -> 0x0086 }
            goto L_0x00bb
        L_0x00ba:
            r11 = r5
        L_0x00bb:
            androidx.datastore.core.InterProcessCoordinator r8 = r7.getCoordinator()     // Catch:{ CorruptionException -> 0x0086 }
            r0.L$0 = r7     // Catch:{ CorruptionException -> 0x0086 }
            r0.L$1 = r6     // Catch:{ CorruptionException -> 0x0086 }
            r0.Z$0 = r2     // Catch:{ CorruptionException -> 0x0086 }
            r0.I$0 = r11     // Catch:{ CorruptionException -> 0x0086 }
            r9 = 2
            r0.label = r9     // Catch:{ CorruptionException -> 0x0086 }
            java.lang.Object r8 = r8.getVersion(r0)     // Catch:{ CorruptionException -> 0x0086 }
            if (r8 != r1) goto L_0x00d1
            return r1
        L_0x00d1:
            java.lang.Number r8 = (java.lang.Number) r8     // Catch:{ CorruptionException -> 0x0086 }
            int r8 = r8.intValue()     // Catch:{ CorruptionException -> 0x0086 }
            androidx.datastore.core.Data r9 = new androidx.datastore.core.Data     // Catch:{ CorruptionException -> 0x0086 }
            r9.<init>(r6, r11, r8)     // Catch:{ CorruptionException -> 0x0086 }
            goto L_0x0113
        L_0x00dd:
            androidx.datastore.core.InterProcessCoordinator r6 = r2.getCoordinator()     // Catch:{ CorruptionException -> 0x0098 }
            r0.L$0 = r2     // Catch:{ CorruptionException -> 0x0098 }
            r0.Z$0 = r11     // Catch:{ CorruptionException -> 0x0098 }
            r7 = 3
            r0.label = r7     // Catch:{ CorruptionException -> 0x0098 }
            java.lang.Object r6 = r6.getVersion(r0)     // Catch:{ CorruptionException -> 0x0098 }
            if (r6 != r1) goto L_0x00ef
            return r1
        L_0x00ef:
            java.lang.Number r6 = (java.lang.Number) r6     // Catch:{ CorruptionException -> 0x0098 }
            int r6 = r6.intValue()     // Catch:{ CorruptionException -> 0x0098 }
            androidx.datastore.core.InterProcessCoordinator r7 = r2.getCoordinator()     // Catch:{ CorruptionException -> 0x0098 }
            androidx.datastore.core.DataStoreImpl$readDataOrHandleCorruption$2 r8 = new androidx.datastore.core.DataStoreImpl$readDataOrHandleCorruption$2     // Catch:{ CorruptionException -> 0x0098 }
            r8.<init>(r2, r6, r4)     // Catch:{ CorruptionException -> 0x0098 }
            kotlin.jvm.functions.Function2 r8 = (kotlin.jvm.functions.Function2) r8     // Catch:{ CorruptionException -> 0x0098 }
            r0.L$0 = r2     // Catch:{ CorruptionException -> 0x0098 }
            r0.Z$0 = r11     // Catch:{ CorruptionException -> 0x0098 }
            r9 = 4
            r0.label = r9     // Catch:{ CorruptionException -> 0x0098 }
            java.lang.Object r7 = r7.tryLock(r8, r0)     // Catch:{ CorruptionException -> 0x0098 }
            if (r7 != r1) goto L_0x010e
            return r1
        L_0x010e:
            r9 = r7
            androidx.datastore.core.Data r9 = (androidx.datastore.core.Data) r9     // Catch:{ CorruptionException -> 0x0098 }
            r7 = r2
            r2 = r11
        L_0x0113:
            return r9
        L_0x0114:
            kotlin.jvm.internal.Ref$ObjectRef r2 = new kotlin.jvm.internal.Ref$ObjectRef
            r2.<init>()
            androidx.datastore.core.CorruptionHandler<T> r7 = r8.corruptionHandler
            r0.L$0 = r8
            r0.L$1 = r6
            r0.L$2 = r2
            r0.L$3 = r2
            r0.Z$0 = r11
            r9 = 5
            r0.label = r9
            java.lang.Object r7 = r7.handleCorruption(r6, r0)
            if (r7 != r1) goto L_0x012f
            return r1
        L_0x012f:
            r9 = r8
            r8 = r7
            r7 = r6
            r6 = r2
        L_0x0133:
            r6.element = r8
            kotlin.jvm.internal.Ref$IntRef r6 = new kotlin.jvm.internal.Ref$IntRef
            r6.<init>()
            if (r11 == 0) goto L_0x013e
            goto L_0x013f
        L_0x013e:
            r3 = r5
        L_0x013f:
            androidx.datastore.core.DataStoreImpl$readDataOrHandleCorruption$3 r11 = new androidx.datastore.core.DataStoreImpl$readDataOrHandleCorruption$3     // Catch:{ all -> 0x016f }
            r11.<init>(r2, r9, r6, r4)     // Catch:{ all -> 0x016f }
            kotlin.jvm.functions.Function1 r11 = (kotlin.jvm.functions.Function1) r11     // Catch:{ all -> 0x016f }
            r0.L$0 = r7     // Catch:{ all -> 0x016f }
            r0.L$1 = r2     // Catch:{ all -> 0x016f }
            r0.L$2 = r6     // Catch:{ all -> 0x016f }
            r0.L$3 = r4     // Catch:{ all -> 0x016f }
            r4 = 6
            r0.label = r4     // Catch:{ all -> 0x016f }
            java.lang.Object r11 = r9.doWithWriteFileLock(r3, r11, r0)     // Catch:{ all -> 0x016f }
            if (r11 != r1) goto L_0x0158
            return r1
        L_0x0158:
            r1 = r2
            r11 = r6
            r2 = r7
        L_0x015b:
            androidx.datastore.core.Data r2 = new androidx.datastore.core.Data
            T r3 = r1.element
            T r4 = r1.element
            if (r4 == 0) goto L_0x0169
            int r5 = r4.hashCode()
        L_0x0169:
            int r1 = r11.element
            r2.<init>(r3, r5, r1)
            return r2
        L_0x016f:
            r11 = move-exception
            r2 = r7
        L_0x0171:
            r1 = r2
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            kotlin.ExceptionsKt.addSuppressed(r1, r11)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl.readDataOrHandleCorruption(boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* access modifiers changed from: private */
    public final <R> Object doWithWriteFileLock(boolean hasWriteFileLock, Function1<? super Continuation<? super R>, ? extends Object> block, Continuation<? super R> $completion) {
        if (hasWriteFileLock) {
            return block.invoke($completion);
        }
        return getCoordinator().lock(new DataStoreImpl$doWithWriteFileLock$3(block, (Continuation<? super DataStoreImpl$doWithWriteFileLock$3>) null), $completion);
    }

    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\b\u0004\u0018\u00002\u00020\u0001BD\u0012=\u0010\u0002\u001a9\u00125\u00123\b\u0001\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u00040\u0003¢\u0006\u0002\u0010\fJ\u000e\u0010\u000e\u001a\u00020\nH@¢\u0006\u0002\u0010\u000fRG\u0010\r\u001a;\u00125\u00123\b\u0001\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0004\u0018\u00010\u0003X\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Landroidx/datastore/core/DataStoreImpl$InitDataStore;", "Landroidx/datastore/core/RunOnce;", "initTasksList", "", "Lkotlin/Function2;", "Landroidx/datastore/core/InitializerApi;", "Lkotlin/ParameterName;", "name", "api", "Lkotlin/coroutines/Continuation;", "", "", "(Landroidx/datastore/core/DataStoreImpl;Ljava/util/List;)V", "initTasks", "doRun", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "datastore-core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* compiled from: DataStoreImpl.kt */
    private final class InitDataStore extends RunOnce {
        /* access modifiers changed from: private */
        public List<? extends Function2<? super InitializerApi<T>, ? super Continuation<? super Unit>, ? extends Object>> initTasks;
        final /* synthetic */ DataStoreImpl<T> this$0;

        public InitDataStore(DataStoreImpl this$02, List<? extends Function2<? super InitializerApi<T>, ? super Continuation<? super Unit>, ? extends Object>> initTasksList) {
            Intrinsics.checkNotNullParameter(initTasksList, "initTasksList");
            this.this$0 = this$02;
            this.initTasks = CollectionsKt.toList(initTasksList);
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x006f, code lost:
            r3 = (androidx.datastore.core.Data) r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0082, code lost:
            r3 = (androidx.datastore.core.Data) r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0084, code lost:
            androidx.datastore.core.DataStoreImpl.access$getInMemoryCache$p(r1.this$0).tryUpdate(r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0093, code lost:
            return kotlin.Unit.INSTANCE;
         */
        /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
        /* JADX WARNING: Removed duplicated region for block: B:11:0x0035  */
        /* JADX WARNING: Removed duplicated region for block: B:12:0x003e  */
        /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object doRun(kotlin.coroutines.Continuation<? super kotlin.Unit> r8) {
            /*
                r7 = this;
                boolean r0 = r8 instanceof androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$1
                if (r0 == 0) goto L_0x0014
                r0 = r8
                androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$1 r0 = (androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r1 = r1 & r2
                if (r1 == 0) goto L_0x0014
                int r8 = r0.label
                int r8 = r8 - r2
                r0.label = r8
                goto L_0x0019
            L_0x0014:
                androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$1 r0 = new androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$1
                r0.<init>(r7, r8)
            L_0x0019:
                java.lang.Object r8 = r0.result
                java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r2 = r0.label
                switch(r2) {
                    case 0: goto L_0x003e;
                    case 1: goto L_0x0035;
                    case 2: goto L_0x002c;
                    default: goto L_0x0024;
                }
            L_0x0024:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r0)
                throw r8
            L_0x002c:
                java.lang.Object r1 = r0.L$0
                androidx.datastore.core.DataStoreImpl$InitDataStore r1 = (androidx.datastore.core.DataStoreImpl.InitDataStore) r1
                kotlin.ResultKt.throwOnFailure(r8)
                r3 = r8
                goto L_0x006f
            L_0x0035:
                java.lang.Object r1 = r0.L$0
                androidx.datastore.core.DataStoreImpl$InitDataStore r1 = (androidx.datastore.core.DataStoreImpl.InitDataStore) r1
                kotlin.ResultKt.throwOnFailure(r8)
                r3 = r8
                goto L_0x0082
            L_0x003e:
                kotlin.ResultKt.throwOnFailure(r8)
                r2 = r7
                java.util.List<? extends kotlin.jvm.functions.Function2<? super androidx.datastore.core.InitializerApi<T>, ? super kotlin.coroutines.Continuation<? super kotlin.Unit>, ? extends java.lang.Object>> r3 = r2.initTasks
                if (r3 == 0) goto L_0x0072
                java.util.List<? extends kotlin.jvm.functions.Function2<? super androidx.datastore.core.InitializerApi<T>, ? super kotlin.coroutines.Continuation<? super kotlin.Unit>, ? extends java.lang.Object>> r3 = r2.initTasks
                kotlin.jvm.internal.Intrinsics.checkNotNull(r3)
                boolean r3 = r3.isEmpty()
                if (r3 == 0) goto L_0x0052
                goto L_0x0072
            L_0x0052:
                androidx.datastore.core.DataStoreImpl<T> r3 = r2.this$0
                androidx.datastore.core.InterProcessCoordinator r3 = r3.getCoordinator()
                androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$initData$1 r4 = new androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$initData$1
                androidx.datastore.core.DataStoreImpl<T> r5 = r2.this$0
                r6 = 0
                r4.<init>(r5, r2, r6)
                kotlin.jvm.functions.Function1 r4 = (kotlin.jvm.functions.Function1) r4
                r0.L$0 = r2
                r5 = 2
                r0.label = r5
                java.lang.Object r3 = r3.lock(r4, r0)
                if (r3 != r1) goto L_0x006e
                return r1
            L_0x006e:
                r1 = r2
            L_0x006f:
                androidx.datastore.core.Data r3 = (androidx.datastore.core.Data) r3
                goto L_0x0084
            L_0x0072:
                androidx.datastore.core.DataStoreImpl<T> r3 = r2.this$0
                r0.L$0 = r2
                r4 = 1
                r0.label = r4
                r4 = 0
                java.lang.Object r3 = r3.readDataOrHandleCorruption(r4, r0)
                if (r3 != r1) goto L_0x0081
                return r1
            L_0x0081:
                r1 = r2
            L_0x0082:
                androidx.datastore.core.Data r3 = (androidx.datastore.core.Data) r3
            L_0x0084:
                androidx.datastore.core.DataStoreImpl<T> r2 = r1.this$0
                androidx.datastore.core.DataStoreInMemoryCache r2 = r2.inMemoryCache
                r4 = r3
                androidx.datastore.core.State r4 = (androidx.datastore.core.State) r4
                r2.tryUpdate(r4)
                kotlin.Unit r2 = kotlin.Unit.INSTANCE
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl.InitDataStore.doRun(kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Landroidx/datastore/core/DataStoreImpl$Companion;", "", "()V", "BUG_MESSAGE", "", "datastore-core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* compiled from: DataStoreImpl.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
