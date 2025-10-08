package androidx.datastore.preferences.core;

import androidx.datastore.core.DataMigration;
import androidx.datastore.core.DataStore;
import androidx.datastore.core.DataStoreFactory;
import androidx.datastore.core.FileStorage;
import androidx.datastore.core.Storage;
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler;
import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;
import okio.Path;

@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JN\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00072\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\t2\u0014\b\u0002\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\f0\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0007JN\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\t2\u0014\b\u0002\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\f0\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0007JN\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\t2\u0014\b\u0002\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\f0\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00130\u0010H\u0007¨\u0006\u0014"}, d2 = {"Landroidx/datastore/preferences/core/PreferenceDataStoreFactory;", "", "()V", "create", "Landroidx/datastore/core/DataStore;", "Landroidx/datastore/preferences/core/Preferences;", "storage", "Landroidx/datastore/core/Storage;", "corruptionHandler", "Landroidx/datastore/core/handlers/ReplaceFileCorruptionHandler;", "migrations", "", "Landroidx/datastore/core/DataMigration;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "produceFile", "Lkotlin/Function0;", "Ljava/io/File;", "createWithPath", "Lokio/Path;", "datastore-preferences-core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: PreferenceDataStoreFactory.jvmAndroid.kt */
public final class PreferenceDataStoreFactory {
    public static final PreferenceDataStoreFactory INSTANCE = new PreferenceDataStoreFactory();

    public final DataStore<Preferences> create(Storage<Preferences> storage) {
        Intrinsics.checkNotNullParameter(storage, "storage");
        return create$default(this, (Storage) storage, (ReplaceFileCorruptionHandler) null, (List) null, (CoroutineScope) null, 14, (Object) null);
    }

    public final DataStore<Preferences> create(Storage<Preferences> storage, ReplaceFileCorruptionHandler<Preferences> replaceFileCorruptionHandler) {
        Intrinsics.checkNotNullParameter(storage, "storage");
        return create$default(this, (Storage) storage, (ReplaceFileCorruptionHandler) replaceFileCorruptionHandler, (List) null, (CoroutineScope) null, 12, (Object) null);
    }

    public final DataStore<Preferences> create(Storage<Preferences> storage, ReplaceFileCorruptionHandler<Preferences> replaceFileCorruptionHandler, List<? extends DataMigration<Preferences>> list) {
        Intrinsics.checkNotNullParameter(storage, "storage");
        Intrinsics.checkNotNullParameter(list, "migrations");
        return create$default(this, (Storage) storage, (ReplaceFileCorruptionHandler) replaceFileCorruptionHandler, (List) list, (CoroutineScope) null, 8, (Object) null);
    }

    public final DataStore<Preferences> create(ReplaceFileCorruptionHandler<Preferences> replaceFileCorruptionHandler, List<? extends DataMigration<Preferences>> list, Function0<? extends File> function0) {
        Intrinsics.checkNotNullParameter(list, "migrations");
        Intrinsics.checkNotNullParameter(function0, "produceFile");
        return create$default(this, (ReplaceFileCorruptionHandler) replaceFileCorruptionHandler, (List) list, (CoroutineScope) null, (Function0) function0, 4, (Object) null);
    }

    public final DataStore<Preferences> create(ReplaceFileCorruptionHandler<Preferences> replaceFileCorruptionHandler, Function0<? extends File> function0) {
        Intrinsics.checkNotNullParameter(function0, "produceFile");
        return create$default(this, (ReplaceFileCorruptionHandler) replaceFileCorruptionHandler, (List) null, (CoroutineScope) null, (Function0) function0, 6, (Object) null);
    }

    public final DataStore<Preferences> create(Function0<? extends File> function0) {
        Intrinsics.checkNotNullParameter(function0, "produceFile");
        return create$default(this, (ReplaceFileCorruptionHandler) null, (List) null, (CoroutineScope) null, (Function0) function0, 7, (Object) null);
    }

    public final DataStore<Preferences> createWithPath(ReplaceFileCorruptionHandler<Preferences> replaceFileCorruptionHandler, List<? extends DataMigration<Preferences>> list, Function0<Path> function0) {
        Intrinsics.checkNotNullParameter(list, "migrations");
        Intrinsics.checkNotNullParameter(function0, "produceFile");
        return createWithPath$default(this, replaceFileCorruptionHandler, list, (CoroutineScope) null, function0, 4, (Object) null);
    }

    public final DataStore<Preferences> createWithPath(ReplaceFileCorruptionHandler<Preferences> replaceFileCorruptionHandler, Function0<Path> function0) {
        Intrinsics.checkNotNullParameter(function0, "produceFile");
        return createWithPath$default(this, replaceFileCorruptionHandler, (List) null, (CoroutineScope) null, function0, 6, (Object) null);
    }

    public final DataStore<Preferences> createWithPath(Function0<Path> function0) {
        Intrinsics.checkNotNullParameter(function0, "produceFile");
        return createWithPath$default(this, (ReplaceFileCorruptionHandler) null, (List) null, (CoroutineScope) null, function0, 7, (Object) null);
    }

    private PreferenceDataStoreFactory() {
    }

    public static /* synthetic */ DataStore create$default(PreferenceDataStoreFactory preferenceDataStoreFactory, ReplaceFileCorruptionHandler replaceFileCorruptionHandler, List list, CoroutineScope coroutineScope, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            replaceFileCorruptionHandler = null;
        }
        if ((i & 2) != 0) {
            list = CollectionsKt.emptyList();
        }
        if ((i & 4) != 0) {
            coroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO().plus(SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null)));
        }
        return preferenceDataStoreFactory.create((ReplaceFileCorruptionHandler<Preferences>) replaceFileCorruptionHandler, (List<? extends DataMigration<Preferences>>) list, coroutineScope, (Function0<? extends File>) function0);
    }

    public final DataStore<Preferences> create(ReplaceFileCorruptionHandler<Preferences> corruptionHandler, List<? extends DataMigration<Preferences>> migrations, CoroutineScope scope, Function0<? extends File> produceFile) {
        Intrinsics.checkNotNullParameter(migrations, "migrations");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(produceFile, "produceFile");
        return new PreferenceDataStore(create((Storage<Preferences>) new FileStorage(PreferencesFileSerializer.INSTANCE, (Function1) null, new PreferenceDataStoreFactory$create$delegate$1(produceFile), 2, (DefaultConstructorMarker) null), corruptionHandler, migrations, scope));
    }

    public static /* synthetic */ DataStore create$default(PreferenceDataStoreFactory preferenceDataStoreFactory, Storage storage, ReplaceFileCorruptionHandler replaceFileCorruptionHandler, List list, CoroutineScope coroutineScope, int i, Object obj) {
        if ((i & 2) != 0) {
            replaceFileCorruptionHandler = null;
        }
        if ((i & 4) != 0) {
            list = CollectionsKt.emptyList();
        }
        if ((i & 8) != 0) {
            coroutineScope = CoroutineScopeKt.CoroutineScope(Actual_jvmAndroidKt.ioDispatcher().plus(SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null)));
        }
        return preferenceDataStoreFactory.create((Storage<Preferences>) storage, (ReplaceFileCorruptionHandler<Preferences>) replaceFileCorruptionHandler, (List<? extends DataMigration<Preferences>>) list, coroutineScope);
    }

    public final DataStore<Preferences> create(Storage<Preferences> storage, ReplaceFileCorruptionHandler<Preferences> corruptionHandler, List<? extends DataMigration<Preferences>> migrations, CoroutineScope scope) {
        Intrinsics.checkNotNullParameter(storage, "storage");
        Intrinsics.checkNotNullParameter(migrations, "migrations");
        Intrinsics.checkNotNullParameter(scope, "scope");
        return new PreferenceDataStore(DataStoreFactory.INSTANCE.create(storage, corruptionHandler, migrations, scope));
    }

    public static /* synthetic */ DataStore createWithPath$default(PreferenceDataStoreFactory preferenceDataStoreFactory, ReplaceFileCorruptionHandler replaceFileCorruptionHandler, List list, CoroutineScope coroutineScope, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            replaceFileCorruptionHandler = null;
        }
        if ((i & 2) != 0) {
            list = CollectionsKt.emptyList();
        }
        if ((i & 4) != 0) {
            coroutineScope = CoroutineScopeKt.CoroutineScope(Actual_jvmAndroidKt.ioDispatcher().plus(SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null)));
        }
        return preferenceDataStoreFactory.createWithPath(replaceFileCorruptionHandler, list, coroutineScope, function0);
    }

    public final DataStore<Preferences> createWithPath(ReplaceFileCorruptionHandler<Preferences> corruptionHandler, List<? extends DataMigration<Preferences>> migrations, CoroutineScope scope, Function0<Path> produceFile) {
        Intrinsics.checkNotNullParameter(migrations, "migrations");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(produceFile, "produceFile");
        return create(corruptionHandler, migrations, scope, (Function0<? extends File>) new PreferenceDataStoreFactory$createWithPath$1(produceFile));
    }
}
