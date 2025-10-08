package com.google.firebase.datastorage;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "Landroidx/datastore/preferences/core/Preferences;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 0, 0}, xi = 48)
@DebugMetadata(c = "com.google.firebase.datastorage.JavaDataStorage$editSync$1", f = "JavaDataStorage.kt", i = {}, l = {220}, m = "invokeSuspend", n = {}, s = {})
/* compiled from: JavaDataStorage.kt */
final class JavaDataStorage$editSync$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Preferences>, Object> {
    final /* synthetic */ Function1<MutablePreferences, Unit> $transform;
    int label;
    final /* synthetic */ JavaDataStorage this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    JavaDataStorage$editSync$1(JavaDataStorage javaDataStorage, Function1<? super MutablePreferences, Unit> function1, Continuation<? super JavaDataStorage$editSync$1> continuation) {
        super(2, continuation);
        this.this$0 = javaDataStorage;
        this.$transform = function1;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new JavaDataStorage$editSync$1(this.this$0, this.$transform, continuation);
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Preferences> continuation) {
        return ((JavaDataStorage$editSync$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Throwable th;
        Object $result2;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                if (!Intrinsics.areEqual(this.this$0.editLock.get(), (Object) Boxing.boxBoolean(true))) {
                    this.this$0.editLock.set(Boxing.boxBoolean(true));
                    DataStore access$getDataStore$p = this.this$0.dataStore;
                    final Function1<MutablePreferences, Unit> function1 = this.$transform;
                    this.label = 1;
                    Object edit = PreferencesKt.edit(access$getDataStore$p, new AnonymousClass1((Continuation<? super AnonymousClass1>) null), this);
                    if (edit != coroutine_suspended) {
                        $result2 = $result;
                        $result = edit;
                        break;
                    } else {
                        return coroutine_suspended;
                    }
                } else {
                    throw new IllegalStateException("Don't call JavaDataStorage.edit() from within an existing edit() callback.\nThis causes deadlocks, and is generally indicative of a code smell.\nInstead, either pass around the initial `MutablePreferences` instance, or don't do everything in a single callback. ");
                }
            case 1:
                try {
                    ResultKt.throwOnFailure($result);
                    $result2 = $result;
                    break;
                } catch (Throwable th2) {
                    th = th2;
                    this.this$0.editLock.set(Boxing.boxBoolean(false));
                    throw th;
                }
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        try {
            Object $result3 = (Preferences) $result;
            this.this$0.editLock.set(Boxing.boxBoolean(false));
            return $result3;
        } catch (Throwable th3) {
            Object obj = $result2;
            th = th3;
            Object obj2 = obj;
            this.this$0.editLock.set(Boxing.boxBoolean(false));
            throw th;
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n"}, d2 = {"<anonymous>", "", "it", "Landroidx/datastore/preferences/core/MutablePreferences;"}, k = 3, mv = {2, 0, 0}, xi = 48)
    @DebugMetadata(c = "com.google.firebase.datastorage.JavaDataStorage$editSync$1$1", f = "JavaDataStorage.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.google.firebase.datastorage.JavaDataStorage$editSync$1$1  reason: invalid class name */
    /* compiled from: JavaDataStorage.kt */
    static final class AnonymousClass1 extends SuspendLambda implements Function2<MutablePreferences, Continuation<? super Unit>, Object> {
        /* synthetic */ Object L$0;
        int label;

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 r0 = new AnonymousClass1(function1, continuation);
            r0.L$0 = obj;
            return r0;
        }

        public final Object invoke(MutablePreferences mutablePreferences, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(mutablePreferences, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    function1.invoke((MutablePreferences) this.L$0);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }
}
