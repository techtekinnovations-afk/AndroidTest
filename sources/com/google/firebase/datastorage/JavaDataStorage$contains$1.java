package com.google.firebase.datastorage;

import androidx.datastore.preferences.core.Preferences;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;

@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 0, 0}, xi = 48)
@DebugMetadata(c = "com.google.firebase.datastorage.JavaDataStorage$contains$1", f = "JavaDataStorage.kt", i = {}, l = {124}, m = "invokeSuspend", n = {}, s = {})
/* compiled from: JavaDataStorage.kt */
final class JavaDataStorage$contains$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
    final /* synthetic */ Preferences.Key<T> $key;
    int label;
    final /* synthetic */ JavaDataStorage this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    JavaDataStorage$contains$1(JavaDataStorage javaDataStorage, Preferences.Key<T> key, Continuation<? super JavaDataStorage$contains$1> continuation) {
        super(2, continuation);
        this.this$0 = javaDataStorage;
        this.$key = key;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new JavaDataStorage$contains$1(this.this$0, this.$key, continuation);
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Boolean> continuation) {
        return ((JavaDataStorage$contains$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                this.label = 1;
                Object firstOrNull = FlowKt.firstOrNull(this.this$0.dataStore.getData(), this);
                if (firstOrNull != coroutine_suspended) {
                    Object obj = $result;
                    $result = firstOrNull;
                    break;
                } else {
                    return coroutine_suspended;
                }
            case 1:
                ResultKt.throwOnFailure($result);
                Object obj2 = $result;
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        Preferences preferences = (Preferences) $result;
        return Boxing.boxBoolean(preferences != null ? preferences.contains(this.$key) : false);
    }
}
