package androidx.datastore.core;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00062\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006R \u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00060\u0005X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0007\u0010\u0003R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00068F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u001d\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00060\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u0011"}, d2 = {"Landroidx/datastore/core/DataStoreInMemoryCache;", "T", "", "()V", "cachedValue", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Landroidx/datastore/core/State;", "getCachedValue$annotations", "currentState", "getCurrentState", "()Landroidx/datastore/core/State;", "flow", "Lkotlinx/coroutines/flow/Flow;", "getFlow", "()Lkotlinx/coroutines/flow/Flow;", "tryUpdate", "newState", "datastore-core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: DataStoreInMemoryCache.kt */
public final class DataStoreInMemoryCache<T> {
    private final MutableStateFlow<State<T>> cachedValue;

    private static /* synthetic */ void getCachedValue$annotations() {
    }

    public DataStoreInMemoryCache() {
        UnInitialized unInitialized = UnInitialized.INSTANCE;
        Intrinsics.checkNotNull(unInitialized, "null cannot be cast to non-null type androidx.datastore.core.State<T of androidx.datastore.core.DataStoreInMemoryCache>");
        this.cachedValue = StateFlowKt.MutableStateFlow(unInitialized);
    }

    public final State<T> getCurrentState() {
        return this.cachedValue.getValue();
    }

    public final Flow<State<T>> getFlow() {
        return this.cachedValue;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x002d, code lost:
        if (r8.getVersion() > r3.getVersion()) goto L_0x0030;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final androidx.datastore.core.State<T> tryUpdate(androidx.datastore.core.State<T> r8) {
        /*
            r7 = this;
            java.lang.String r0 = "newState"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r0)
            kotlinx.coroutines.flow.MutableStateFlow<androidx.datastore.core.State<T>> r0 = r7.cachedValue
            r1 = 0
        L_0x0008:
            java.lang.Object r2 = r0.getValue()
            r3 = r2
            androidx.datastore.core.State r3 = (androidx.datastore.core.State) r3
            r4 = 0
            boolean r5 = r3 instanceof androidx.datastore.core.ReadException
            if (r5 == 0) goto L_0x0018
            r5 = 1
            goto L_0x001e
        L_0x0018:
            androidx.datastore.core.UnInitialized r5 = androidx.datastore.core.UnInitialized.INSTANCE
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r3, (java.lang.Object) r5)
        L_0x001e:
            if (r5 == 0) goto L_0x0021
            goto L_0x0030
        L_0x0021:
            boolean r5 = r3 instanceof androidx.datastore.core.Data
            if (r5 == 0) goto L_0x0033
            int r5 = r8.getVersion()
            int r6 = r3.getVersion()
            if (r5 <= r6) goto L_0x0032
        L_0x0030:
            r5 = r8
            goto L_0x0039
        L_0x0032:
            goto L_0x0038
        L_0x0033:
            boolean r5 = r3 instanceof androidx.datastore.core.Final
            if (r5 == 0) goto L_0x0043
        L_0x0038:
            r5 = r3
        L_0x0039:
            boolean r3 = r0.compareAndSet(r2, r5)
            if (r3 == 0) goto L_0x0008
            return r5
        L_0x0043:
            kotlin.NoWhenBranchMatchedException r5 = new kotlin.NoWhenBranchMatchedException
            r5.<init>()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreInMemoryCache.tryUpdate(androidx.datastore.core.State):androidx.datastore.core.State");
    }
}
