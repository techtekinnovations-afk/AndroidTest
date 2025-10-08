package com.google.firebase.datastorage;

import android.content.Context;
import android.os.Process;
import android.util.Log;
import androidx.datastore.core.CorruptionException;
import androidx.datastore.core.DataStore;
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler;
import androidx.datastore.preferences.PreferenceDataStoreDelegateKt;
import androidx.datastore.preferences.SharedPreferencesMigrationKt;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesFactory;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference2Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadOnlyProperty;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.CoroutineScope;

@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J'\u0010\u0016\u001a\u0002H\u0017\"\u0004\b\u0000\u0010\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00170\u00192\u0006\u0010\u001a\u001a\u0002H\u0017¢\u0006\u0002\u0010\u001bJ\u001a\u0010\u001c\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00170\u0019J'\u0010\u001d\u001a\u00020\u0011\"\u0004\b\u0000\u0010\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00170\u00192\u0006\u0010\u001e\u001a\u0002H\u0017¢\u0006\u0002\u0010\u001fJ\u0016\u0010 \u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0019\u0012\u0004\u0012\u00020\u00010!J\u001a\u0010\"\u001a\u00020\u00112\u0012\u0010#\u001a\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020&0$R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0004¢\u0006\u0002\n\u0000R%\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010*\u00020\u00038BX\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lcom/google/firebase/datastorage/JavaDataStorage;", "", "context", "Landroid/content/Context;", "name", "", "<init>", "(Landroid/content/Context;Ljava/lang/String;)V", "getContext", "()Landroid/content/Context;", "getName", "()Ljava/lang/String;", "editLock", "Ljava/lang/ThreadLocal;", "", "dataStore", "Landroidx/datastore/core/DataStore;", "Landroidx/datastore/preferences/core/Preferences;", "getDataStore", "(Landroid/content/Context;)Landroidx/datastore/core/DataStore;", "dataStore$delegate", "Lkotlin/properties/ReadOnlyProperty;", "getSync", "T", "key", "Landroidx/datastore/preferences/core/Preferences$Key;", "defaultValue", "(Landroidx/datastore/preferences/core/Preferences$Key;Ljava/lang/Object;)Ljava/lang/Object;", "contains", "putSync", "value", "(Landroidx/datastore/preferences/core/Preferences$Key;Ljava/lang/Object;)Landroidx/datastore/preferences/core/Preferences;", "getAllSync", "", "editSync", "transform", "Lkotlin/Function1;", "Landroidx/datastore/preferences/core/MutablePreferences;", "", "com.google.firebase-firebase-common"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: JavaDataStorage.kt */
public final class JavaDataStorage {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property2(new PropertyReference2Impl(JavaDataStorage.class, "dataStore", "getDataStore(Landroid/content/Context;)Landroidx/datastore/core/DataStore;", 0))};
    private final Context context;
    /* access modifiers changed from: private */
    public final DataStore<Preferences> dataStore = getDataStore(this.context);
    private final ReadOnlyProperty dataStore$delegate = PreferenceDataStoreDelegateKt.preferencesDataStore$default(this.name, new ReplaceFileCorruptionHandler(new JavaDataStorage$$ExternalSyntheticLambda0(this)), new JavaDataStorage$$ExternalSyntheticLambda1(this), (CoroutineScope) null, 8, (Object) null);
    /* access modifiers changed from: private */
    public final ThreadLocal<Boolean> editLock = new ThreadLocal<>();
    private final String name;

    public JavaDataStorage(Context context2, String name2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(name2, "name");
        this.context = context2;
        this.name = name2;
    }

    public final Context getContext() {
        return this.context;
    }

    public final String getName() {
        return this.name;
    }

    private final DataStore<Preferences> getDataStore(Context $this$dataStore) {
        return (DataStore) this.dataStore$delegate.getValue($this$dataStore, $$delegatedProperties[0]);
    }

    /* access modifiers changed from: private */
    public static final List dataStore_delegate$lambda$1(JavaDataStorage this$0, Context it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return CollectionsKt.listOf(SharedPreferencesMigrationKt.SharedPreferencesMigration$default(it, this$0.name, (Set) null, 4, (Object) null));
    }

    /* access modifiers changed from: private */
    public static final Preferences dataStore_delegate$lambda$0(JavaDataStorage this$0, CorruptionException ex) {
        Intrinsics.checkNotNullParameter(ex, "ex");
        Log.w(Reflection.getOrCreateKotlinClass(JavaDataStorage.class).getSimpleName(), "CorruptionException in " + this$0.name + " DataStore running in process " + Process.myPid(), ex);
        return PreferencesFactory.createEmpty();
    }

    public final <T> T getSync(Preferences.Key<T> key, T defaultValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        return BuildersKt__BuildersKt.runBlocking$default((CoroutineContext) null, new JavaDataStorage$getSync$1(this, key, defaultValue, (Continuation<? super JavaDataStorage$getSync$1>) null), 1, (Object) null);
    }

    public final <T> boolean contains(Preferences.Key<T> key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return ((Boolean) BuildersKt__BuildersKt.runBlocking$default((CoroutineContext) null, new JavaDataStorage$contains$1(this, key, (Continuation<? super JavaDataStorage$contains$1>) null), 1, (Object) null)).booleanValue();
    }

    public final <T> Preferences putSync(Preferences.Key<T> key, T value) {
        Intrinsics.checkNotNullParameter(key, "key");
        return (Preferences) BuildersKt__BuildersKt.runBlocking$default((CoroutineContext) null, new JavaDataStorage$putSync$1(this, key, value, (Continuation<? super JavaDataStorage$putSync$1>) null), 1, (Object) null);
    }

    public final Map<Preferences.Key<?>, Object> getAllSync() {
        return (Map) BuildersKt__BuildersKt.runBlocking$default((CoroutineContext) null, new JavaDataStorage$getAllSync$1(this, (Continuation<? super JavaDataStorage$getAllSync$1>) null), 1, (Object) null);
    }

    public final Preferences editSync(Function1<? super MutablePreferences, Unit> transform) {
        Intrinsics.checkNotNullParameter(transform, "transform");
        return (Preferences) BuildersKt__BuildersKt.runBlocking$default((CoroutineContext) null, new JavaDataStorage$editSync$1(this, transform, (Continuation<? super JavaDataStorage$editSync$1>) null), 1, (Object) null);
    }
}
