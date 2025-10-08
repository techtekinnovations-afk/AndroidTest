package androidx.datastore.migrations;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.datastore.core.DataMigration;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010#\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0001,Bu\b\u0017\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012$\b\u0002\u0010\t\u001a\u001e\b\u0001\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0006\u0012\u0004\u0018\u00010\r0\n\u0012(\u0010\u000e\u001a$\b\u0001\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000b\u0012\u0006\u0012\u0004\u0018\u00010\r0\u000f¢\u0006\u0002\u0010\u0011Bw\b\u0017\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\b\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012$\b\u0002\u0010\t\u001a\u001e\b\u0001\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0006\u0012\u0004\u0018\u00010\r0\n\u0012(\u0010\u000e\u001a$\b\u0001\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000b\u0012\u0006\u0012\u0004\u0018\u00010\r0\u000f¢\u0006\u0002\u0010\u0015B\u0001\b\u0002\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012$\b\u0002\u0010\t\u001a\u001e\b\u0001\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0006\u0012\u0004\u0018\u00010\r0\n\u0012(\u0010\u000e\u001a$\b\u0001\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000b\u0012\u0006\u0012\u0004\u0018\u00010\r0\u000f\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\u0017J\u000e\u0010!\u001a\u00020\"H@¢\u0006\u0002\u0010#J\u0018\u0010$\u001a\u00020\"2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\bH\u0002J\u0010\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020&H\u0002J\u0018\u0010(\u001a\u00020&2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\bH\u0002J\u0016\u0010\u000e\u001a\u00028\u00002\u0006\u0010)\u001a\u00028\u0000H@¢\u0006\u0002\u0010*J\u0016\u0010+\u001a\u00020\f2\u0006\u0010)\u001a\u00028\u0000H@¢\u0006\u0002\u0010*R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0018\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0019X\u0004¢\u0006\u0002\n\u0000R2\u0010\u000e\u001a$\b\u0001\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u000b\u0012\u0006\u0012\u0004\u0018\u00010\r0\u000fX\u0004¢\u0006\u0004\n\u0002\u0010\u001aR\u0010\u0010\u0016\u001a\u0004\u0018\u00010\bX\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u001b\u001a\u00020\u00058BX\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b\u001c\u0010\u001dR,\u0010\t\u001a\u001e\b\u0001\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0006\u0012\u0004\u0018\u00010\r0\nX\u0004¢\u0006\u0004\n\u0002\u0010 ¨\u0006-"}, d2 = {"Landroidx/datastore/migrations/SharedPreferencesMigration;", "T", "Landroidx/datastore/core/DataMigration;", "produceSharedPreferences", "Lkotlin/Function0;", "Landroid/content/SharedPreferences;", "keysToMigrate", "", "", "shouldRunMigration", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "", "migrate", "Lkotlin/Function3;", "Landroidx/datastore/migrations/SharedPreferencesView;", "(Lkotlin/jvm/functions/Function0;Ljava/util/Set;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;)V", "context", "Landroid/content/Context;", "sharedPreferencesName", "(Landroid/content/Context;Ljava/lang/String;Ljava/util/Set;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;)V", "name", "(Lkotlin/jvm/functions/Function0;Ljava/util/Set;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;Landroid/content/Context;Ljava/lang/String;)V", "keySet", "", "Lkotlin/jvm/functions/Function3;", "sharedPrefs", "getSharedPrefs", "()Landroid/content/SharedPreferences;", "sharedPrefs$delegate", "Lkotlin/Lazy;", "Lkotlin/jvm/functions/Function2;", "cleanUp", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteSharedPreferences", "getSharedPrefsBackup", "Ljava/io/File;", "prefsFile", "getSharedPrefsFile", "currentData", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "shouldMigrate", "Api24Impl", "datastore_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: SharedPreferencesMigration.android.kt */
public final class SharedPreferencesMigration<T> implements DataMigration<T> {
    private final Context context;
    private final Set<String> keySet;
    private final Function3<SharedPreferencesView, T, Continuation<? super T>, Object> migrate;
    private final String name;
    private final Lazy sharedPrefs$delegate;
    private final Function2<T, Continuation<? super Boolean>, Object> shouldRunMigration;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public SharedPreferencesMigration(Context context2, String str, Set<String> set, Function3<? super SharedPreferencesView, ? super T, ? super Continuation<? super T>, ? extends Object> function3) {
        this(context2, str, set, (Function2) null, function3, 8, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(str, "sharedPreferencesName");
        Intrinsics.checkNotNullParameter(set, "keysToMigrate");
        Intrinsics.checkNotNullParameter(function3, "migrate");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public SharedPreferencesMigration(Context context2, String str, Function3<? super SharedPreferencesView, ? super T, ? super Continuation<? super T>, ? extends Object> function3) {
        this(context2, str, (Set) null, (Function2) null, function3, 12, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(str, "sharedPreferencesName");
        Intrinsics.checkNotNullParameter(function3, "migrate");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public SharedPreferencesMigration(Function0<? extends SharedPreferences> function0, Set<String> set, Function3<? super SharedPreferencesView, ? super T, ? super Continuation<? super T>, ? extends Object> function3) {
        this((Function0) function0, (Set) set, (Function2) null, (Function3) function3, 4, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(function0, "produceSharedPreferences");
        Intrinsics.checkNotNullParameter(set, "keysToMigrate");
        Intrinsics.checkNotNullParameter(function3, "migrate");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public SharedPreferencesMigration(Function0<? extends SharedPreferences> function0, Function3<? super SharedPreferencesView, ? super T, ? super Continuation<? super T>, ? extends Object> function3) {
        this((Function0) function0, (Set) null, (Function2) null, (Function3) function3, 6, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(function0, "produceSharedPreferences");
        Intrinsics.checkNotNullParameter(function3, "migrate");
    }

    private SharedPreferencesMigration(Function0<? extends SharedPreferences> produceSharedPreferences, Set<String> keysToMigrate, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> shouldRunMigration2, Function3<? super SharedPreferencesView, ? super T, ? super Continuation<? super T>, ? extends Object> migrate2, Context context2, String name2) {
        Set<String> set;
        this.shouldRunMigration = shouldRunMigration2;
        this.migrate = migrate2;
        this.context = context2;
        this.name = name2;
        this.sharedPrefs$delegate = LazyKt.lazy(produceSharedPreferences);
        if (keysToMigrate == SharedPreferencesMigration_androidKt.getMIGRATE_ALL_KEYS()) {
            set = null;
        } else {
            set = CollectionsKt.toMutableSet(keysToMigrate);
        }
        this.keySet = set;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    /* synthetic */ SharedPreferencesMigration(kotlin.jvm.functions.Function0 r8, java.util.Set r9, kotlin.jvm.functions.Function2 r10, kotlin.jvm.functions.Function3 r11, android.content.Context r12, java.lang.String r13, int r14, kotlin.jvm.internal.DefaultConstructorMarker r15) {
        /*
            r7 = this;
            r14 = r14 & 4
            if (r14 == 0) goto L_0x000e
            androidx.datastore.migrations.SharedPreferencesMigration$1 r10 = new androidx.datastore.migrations.SharedPreferencesMigration$1
            r14 = 0
            r10.<init>(r14)
            kotlin.jvm.functions.Function2 r10 = (kotlin.jvm.functions.Function2) r10
            r3 = r10
            goto L_0x000f
        L_0x000e:
            r3 = r10
        L_0x000f:
            r0 = r7
            r1 = r8
            r2 = r9
            r4 = r11
            r5 = r12
            r6 = r13
            r0.<init>((kotlin.jvm.functions.Function0<? extends android.content.SharedPreferences>) r1, (java.util.Set<java.lang.String>) r2, r3, r4, (android.content.Context) r5, (java.lang.String) r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.migrations.SharedPreferencesMigration.<init>(kotlin.jvm.functions.Function0, java.util.Set, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function3, android.content.Context, java.lang.String, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ SharedPreferencesMigration(Function0 function0, Set<String> set, Function2 function2, Function3 function3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((Function0<? extends SharedPreferences>) function0, (i & 2) != 0 ? SharedPreferencesMigration_androidKt.getMIGRATE_ALL_KEYS() : set, (i & 4) != 0 ? new AnonymousClass2((Continuation<? super AnonymousClass2>) null) : function2, function3);
    }

    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H@"}, d2 = {"<anonymous>", "", "T", "it"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @DebugMetadata(c = "androidx.datastore.migrations.SharedPreferencesMigration$2", f = "SharedPreferencesMigration.android.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: androidx.datastore.migrations.SharedPreferencesMigration$2  reason: invalid class name */
    /* compiled from: SharedPreferencesMigration.android.kt */
    static final class AnonymousClass2 extends SuspendLambda implements Function2<T, Continuation<? super Boolean>, Object> {
        int label;

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass2(continuation);
        }

        public final Object invoke(T t, Continuation<? super Boolean> continuation) {
            return ((AnonymousClass2) create(t, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    return Boxing.boxBoolean(true);
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public SharedPreferencesMigration(Function0<? extends SharedPreferences> produceSharedPreferences, Set<String> keysToMigrate, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> shouldRunMigration2, Function3<? super SharedPreferencesView, ? super T, ? super Continuation<? super T>, ? extends Object> migrate2) {
        this(produceSharedPreferences, keysToMigrate, shouldRunMigration2, migrate2, (Context) null, (String) null);
        Intrinsics.checkNotNullParameter(produceSharedPreferences, "produceSharedPreferences");
        Intrinsics.checkNotNullParameter(keysToMigrate, "keysToMigrate");
        Intrinsics.checkNotNullParameter(shouldRunMigration2, "shouldRunMigration");
        Intrinsics.checkNotNullParameter(migrate2, "migrate");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ SharedPreferencesMigration(android.content.Context r7, java.lang.String r8, java.util.Set r9, kotlin.jvm.functions.Function2 r10, kotlin.jvm.functions.Function3 r11, int r12, kotlin.jvm.internal.DefaultConstructorMarker r13) {
        /*
            r6 = this;
            r13 = r12 & 4
            if (r13 == 0) goto L_0x000a
            java.util.Set r9 = androidx.datastore.migrations.SharedPreferencesMigration_androidKt.getMIGRATE_ALL_KEYS()
            r3 = r9
            goto L_0x000b
        L_0x000a:
            r3 = r9
        L_0x000b:
            r9 = r12 & 8
            if (r9 == 0) goto L_0x001a
            androidx.datastore.migrations.SharedPreferencesMigration$3 r9 = new androidx.datastore.migrations.SharedPreferencesMigration$3
            r10 = 0
            r9.<init>(r10)
            r10 = r9
            kotlin.jvm.functions.Function2 r10 = (kotlin.jvm.functions.Function2) r10
            r4 = r10
            goto L_0x001b
        L_0x001a:
            r4 = r10
        L_0x001b:
            r0 = r6
            r1 = r7
            r2 = r8
            r5 = r11
            r0.<init>(r1, r2, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.migrations.SharedPreferencesMigration.<init>(android.content.Context, java.lang.String, java.util.Set, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function3, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public SharedPreferencesMigration(final Context context2, final String sharedPreferencesName, Set<String> keysToMigrate, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> shouldRunMigration2, Function3<? super SharedPreferencesView, ? super T, ? super Continuation<? super T>, ? extends Object> migrate2) {
        this((Function0<? extends SharedPreferences>) new Function0<SharedPreferences>() {
            public final SharedPreferences invoke() {
                SharedPreferences sharedPreferences = context2.getSharedPreferences(sharedPreferencesName, 0);
                Intrinsics.checkNotNullExpressionValue(sharedPreferences, "context.getSharedPrefere…me, Context.MODE_PRIVATE)");
                return sharedPreferences;
            }
        }, keysToMigrate, shouldRunMigration2, migrate2, context2, sharedPreferencesName);
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(sharedPreferencesName, "sharedPreferencesName");
        Intrinsics.checkNotNullParameter(keysToMigrate, "keysToMigrate");
        Intrinsics.checkNotNullParameter(shouldRunMigration2, "shouldRunMigration");
        Intrinsics.checkNotNullParameter(migrate2, "migrate");
    }

    private final SharedPreferences getSharedPrefs() {
        return (SharedPreferences) this.sharedPrefs$delegate.getValue();
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0037  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0051  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0056  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object shouldMigrate(T r9, kotlin.coroutines.Continuation<? super java.lang.Boolean> r10) {
        /*
            r8 = this;
            boolean r0 = r10 instanceof androidx.datastore.migrations.SharedPreferencesMigration$shouldMigrate$1
            if (r0 == 0) goto L_0x0014
            r0 = r10
            androidx.datastore.migrations.SharedPreferencesMigration$shouldMigrate$1 r0 = (androidx.datastore.migrations.SharedPreferencesMigration$shouldMigrate$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r10 = r0.label
            int r10 = r10 - r2
            r0.label = r10
            goto L_0x0019
        L_0x0014:
            androidx.datastore.migrations.SharedPreferencesMigration$shouldMigrate$1 r0 = new androidx.datastore.migrations.SharedPreferencesMigration$shouldMigrate$1
            r0.<init>(r8, r10)
        L_0x0019:
            java.lang.Object r10 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            switch(r2) {
                case 0: goto L_0x0037;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L_0x002d:
            java.lang.Object r9 = r0.L$0
            androidx.datastore.migrations.SharedPreferencesMigration r9 = (androidx.datastore.migrations.SharedPreferencesMigration) r9
            kotlin.ResultKt.throwOnFailure(r10)
            r2 = r9
            r9 = r10
            goto L_0x0048
        L_0x0037:
            kotlin.ResultKt.throwOnFailure(r10)
            r2 = r8
            kotlin.jvm.functions.Function2<T, kotlin.coroutines.Continuation<? super java.lang.Boolean>, java.lang.Object> r4 = r2.shouldRunMigration
            r0.L$0 = r2
            r0.label = r3
            java.lang.Object r9 = r4.invoke(r9, r0)
            if (r9 != r1) goto L_0x0048
            return r1
        L_0x0048:
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            r1 = 0
            if (r9 != 0) goto L_0x0056
            java.lang.Boolean r9 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r1)
            return r9
        L_0x0056:
            java.util.Set<java.lang.String> r9 = r2.keySet
            if (r9 != 0) goto L_0x0070
            android.content.SharedPreferences r9 = r2.getSharedPrefs()
            java.util.Map r9 = r9.getAll()
            java.lang.String r2 = "sharedPrefs.all"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r9, r2)
            boolean r9 = r9.isEmpty()
            if (r9 != 0) goto L_0x006e
            goto L_0x00a2
        L_0x006e:
            r3 = r1
            goto L_0x00a2
        L_0x0070:
            java.util.Set<java.lang.String> r9 = r2.keySet
            java.lang.Iterable r9 = (java.lang.Iterable) r9
            android.content.SharedPreferences r4 = r2.getSharedPrefs()
            r2 = 0
            boolean r5 = r9 instanceof java.util.Collection
            if (r5 == 0) goto L_0x0088
            r5 = r9
            java.util.Collection r5 = (java.util.Collection) r5
            boolean r5 = r5.isEmpty()
            if (r5 == 0) goto L_0x0088
            r3 = r1
            goto L_0x00a2
        L_0x0088:
            java.util.Iterator r5 = r9.iterator()
        L_0x008c:
            boolean r9 = r5.hasNext()
            if (r9 == 0) goto L_0x00a1
            java.lang.Object r9 = r5.next()
            r6 = r9
            java.lang.String r6 = (java.lang.String) r6
            r7 = 0
            boolean r6 = r4.contains(r6)
            if (r6 == 0) goto L_0x008c
            goto L_0x00a2
        L_0x00a1:
            r3 = r1
        L_0x00a2:
            java.lang.Boolean r9 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.migrations.SharedPreferencesMigration.shouldMigrate(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public Object migrate(T currentData, Continuation<? super T> $completion) {
        return this.migrate.invoke(new SharedPreferencesView(getSharedPrefs(), this.keySet), currentData, $completion);
    }

    public Object cleanUp(Continuation<? super Unit> $completion) throws IOException {
        SharedPreferences.Editor sharedPrefsEditor = getSharedPrefs().edit();
        if (this.keySet == null) {
            sharedPrefsEditor.clear();
        } else {
            for (String key : this.keySet) {
                sharedPrefsEditor.remove(key);
            }
        }
        if (sharedPrefsEditor.commit()) {
            if (!(!getSharedPrefs().getAll().isEmpty() || this.context == null || this.name == null)) {
                deleteSharedPreferences(this.context, this.name);
            }
            Set<String> set = this.keySet;
            if (set != null) {
                set.clear();
            }
            return Unit.INSTANCE;
        }
        throw new IOException("Unable to delete migrated keys from SharedPreferences.");
    }

    private final void deleteSharedPreferences(Context context2, String name2) {
        Api24Impl.deleteSharedPreferences(context2, name2);
    }

    private final File getSharedPrefsFile(Context context2, String name2) {
        return new File(new File(context2.getApplicationInfo().dataDir, "shared_prefs"), name2 + ".xml");
    }

    private final File getSharedPrefsBackup(File prefsFile) {
        return new File(prefsFile.getPath() + ".bak");
    }

    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bÃ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007¨\u0006\t"}, d2 = {"Landroidx/datastore/migrations/SharedPreferencesMigration$Api24Impl;", "", "()V", "deleteSharedPreferences", "", "context", "Landroid/content/Context;", "name", "", "datastore_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* compiled from: SharedPreferencesMigration.android.kt */
    private static final class Api24Impl {
        public static final Api24Impl INSTANCE = new Api24Impl();

        private Api24Impl() {
        }

        @JvmStatic
        public static final boolean deleteSharedPreferences(Context context, String name) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(name, "name");
            return context.deleteSharedPreferences(name);
        }
    }
}
