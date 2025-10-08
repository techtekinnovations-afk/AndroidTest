package androidx.datastore.core;

import androidx.datastore.core.handlers.NoOpCorruptionHandler;
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler;
import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;

@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002Jb\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0000\u0010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\u00072\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u0002H\u0005\u0018\u00010\t2\u0014\b\u0002\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00050\f0\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0007JT\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0000\u0010\u00052\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00050\u00132\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u0002H\u0005\u0018\u00010\t2\u0014\b\u0002\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00050\f0\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0007¨\u0006\u0014"}, d2 = {"Landroidx/datastore/core/MultiProcessDataStoreFactory;", "", "()V", "create", "Landroidx/datastore/core/DataStore;", "T", "serializer", "Landroidx/datastore/core/Serializer;", "corruptionHandler", "Landroidx/datastore/core/handlers/ReplaceFileCorruptionHandler;", "migrations", "", "Landroidx/datastore/core/DataMigration;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "produceFile", "Lkotlin/Function0;", "Ljava/io/File;", "storage", "Landroidx/datastore/core/Storage;", "datastore-core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: MultiProcessDataStoreFactory.android.kt */
public final class MultiProcessDataStoreFactory {
    public static final MultiProcessDataStoreFactory INSTANCE = new MultiProcessDataStoreFactory();

    public final <T> DataStore<T> create(Serializer<T> serializer, ReplaceFileCorruptionHandler<T> replaceFileCorruptionHandler, List<? extends DataMigration<T>> list, Function0<? extends File> function0) {
        Intrinsics.checkNotNullParameter(serializer, "serializer");
        Intrinsics.checkNotNullParameter(list, "migrations");
        Intrinsics.checkNotNullParameter(function0, "produceFile");
        return create$default(this, serializer, replaceFileCorruptionHandler, list, (CoroutineScope) null, function0, 8, (Object) null);
    }

    public final <T> DataStore<T> create(Serializer<T> serializer, ReplaceFileCorruptionHandler<T> replaceFileCorruptionHandler, Function0<? extends File> function0) {
        Intrinsics.checkNotNullParameter(serializer, "serializer");
        Intrinsics.checkNotNullParameter(function0, "produceFile");
        return create$default(this, serializer, replaceFileCorruptionHandler, (List) null, (CoroutineScope) null, function0, 12, (Object) null);
    }

    public final <T> DataStore<T> create(Serializer<T> serializer, Function0<? extends File> function0) {
        Intrinsics.checkNotNullParameter(serializer, "serializer");
        Intrinsics.checkNotNullParameter(function0, "produceFile");
        return create$default(this, serializer, (ReplaceFileCorruptionHandler) null, (List) null, (CoroutineScope) null, function0, 14, (Object) null);
    }

    public final <T> DataStore<T> create(Storage<T> storage) {
        Intrinsics.checkNotNullParameter(storage, "storage");
        return create$default(this, storage, (ReplaceFileCorruptionHandler) null, (List) null, (CoroutineScope) null, 14, (Object) null);
    }

    public final <T> DataStore<T> create(Storage<T> storage, ReplaceFileCorruptionHandler<T> replaceFileCorruptionHandler) {
        Intrinsics.checkNotNullParameter(storage, "storage");
        return create$default(this, storage, replaceFileCorruptionHandler, (List) null, (CoroutineScope) null, 12, (Object) null);
    }

    public final <T> DataStore<T> create(Storage<T> storage, ReplaceFileCorruptionHandler<T> replaceFileCorruptionHandler, List<? extends DataMigration<T>> list) {
        Intrinsics.checkNotNullParameter(storage, "storage");
        Intrinsics.checkNotNullParameter(list, "migrations");
        return create$default(this, storage, replaceFileCorruptionHandler, list, (CoroutineScope) null, 8, (Object) null);
    }

    private MultiProcessDataStoreFactory() {
    }

    public static /* synthetic */ DataStore create$default(MultiProcessDataStoreFactory multiProcessDataStoreFactory, Storage storage, ReplaceFileCorruptionHandler replaceFileCorruptionHandler, List list, CoroutineScope coroutineScope, int i, Object obj) {
        if ((i & 2) != 0) {
            replaceFileCorruptionHandler = null;
        }
        if ((i & 4) != 0) {
            list = CollectionsKt.emptyList();
        }
        if ((i & 8) != 0) {
            coroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO().plus(SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null)));
        }
        return multiProcessDataStoreFactory.create(storage, replaceFileCorruptionHandler, list, coroutineScope);
    }

    public final <T> DataStore<T> create(Storage<T> storage, ReplaceFileCorruptionHandler<T> corruptionHandler, List<? extends DataMigration<T>> migrations, CoroutineScope scope) {
        Intrinsics.checkNotNullParameter(storage, "storage");
        Intrinsics.checkNotNullParameter(migrations, "migrations");
        Intrinsics.checkNotNullParameter(scope, "scope");
        return new DataStoreImpl<>(storage, CollectionsKt.listOf(DataMigrationInitializer.Companion.getInitializer(migrations)), corruptionHandler != null ? corruptionHandler : new NoOpCorruptionHandler(), scope);
    }

    public static /* synthetic */ DataStore create$default(MultiProcessDataStoreFactory multiProcessDataStoreFactory, Serializer serializer, ReplaceFileCorruptionHandler replaceFileCorruptionHandler, List list, CoroutineScope coroutineScope, Function0 function0, int i, Object obj) {
        if ((i & 2) != 0) {
            replaceFileCorruptionHandler = null;
        }
        if ((i & 4) != 0) {
            list = CollectionsKt.emptyList();
        }
        if ((i & 8) != 0) {
            coroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO().plus(SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null)));
        }
        return multiProcessDataStoreFactory.create(serializer, replaceFileCorruptionHandler, list, coroutineScope, function0);
    }

    public final <T> DataStore<T> create(Serializer<T> serializer, ReplaceFileCorruptionHandler<T> corruptionHandler, List<? extends DataMigration<T>> migrations, CoroutineScope scope, Function0<? extends File> produceFile) {
        Intrinsics.checkNotNullParameter(serializer, "serializer");
        Intrinsics.checkNotNullParameter(migrations, "migrations");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(produceFile, "produceFile");
        return new DataStoreImpl<>(new FileStorage(serializer, new MultiProcessDataStoreFactory$create$1(scope), produceFile), CollectionsKt.listOf(DataMigrationInitializer.Companion.getInitializer(migrations)), corruptionHandler != null ? corruptionHandler : new NoOpCorruptionHandler(), scope);
    }
}
