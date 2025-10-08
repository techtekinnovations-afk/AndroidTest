package com.google.firebase.datastorage;

import androidx.datastore.preferences.core.Preferences;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@Metadata(d1 = {"\u0000\b\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u0002H\n"}, d2 = {"<anonymous>", "T", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {2, 0, 0}, xi = 48)
@DebugMetadata(c = "com.google.firebase.datastorage.JavaDataStorage$getSync$1", f = "JavaDataStorage.kt", i = {}, l = {104}, m = "invokeSuspend", n = {}, s = {})
/* compiled from: JavaDataStorage.kt */
final class JavaDataStorage$getSync$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super T>, Object> {
    final /* synthetic */ T $defaultValue;
    final /* synthetic */ Preferences.Key<T> $key;
    int label;
    final /* synthetic */ JavaDataStorage this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    JavaDataStorage$getSync$1(JavaDataStorage javaDataStorage, Preferences.Key<T> key, T t, Continuation<? super JavaDataStorage$getSync$1> continuation) {
        super(2, continuation);
        this.this$0 = javaDataStorage;
        this.$key = key;
        this.$defaultValue = t;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new JavaDataStorage$getSync$1(this.this$0, this.$key, this.$defaultValue, continuation);
    }

    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super T> continuation) {
        return ((JavaDataStorage$getSync$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0036, code lost:
        r5 = r5.get(r4.$key);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r5) {
        /*
            r4 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r4.label
            switch(r1) {
                case 0: goto L_0x0016;
                case 1: goto L_0x0011;
                default: goto L_0x0009;
            }
        L_0x0009:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r0)
            throw r5
        L_0x0011:
            kotlin.ResultKt.throwOnFailure(r5)
            r0 = r5
            goto L_0x0032
        L_0x0016:
            kotlin.ResultKt.throwOnFailure(r5)
            com.google.firebase.datastorage.JavaDataStorage r1 = r4.this$0
            androidx.datastore.core.DataStore r1 = r1.dataStore
            kotlinx.coroutines.flow.Flow r1 = r1.getData()
            r2 = r4
            kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
            r3 = 1
            r4.label = r3
            java.lang.Object r1 = kotlinx.coroutines.flow.FlowKt.firstOrNull(r1, r2)
            if (r1 != r0) goto L_0x0030
            return r0
        L_0x0030:
            r0 = r5
            r5 = r1
        L_0x0032:
            androidx.datastore.preferences.core.Preferences r5 = (androidx.datastore.preferences.core.Preferences) r5
            if (r5 == 0) goto L_0x003e
            androidx.datastore.preferences.core.Preferences$Key<T> r1 = r4.$key
            java.lang.Object r5 = r5.get(r1)
            if (r5 != 0) goto L_0x0040
        L_0x003e:
            T r5 = r4.$defaultValue
        L_0x0040:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.datastorage.JavaDataStorage$getSync$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
