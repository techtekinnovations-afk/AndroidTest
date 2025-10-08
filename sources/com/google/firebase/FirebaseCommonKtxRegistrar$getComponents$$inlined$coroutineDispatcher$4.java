package com.google.firebase;

import com.google.firebase.annotations.concurrent.UiThread;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;
import com.google.firebase.components.Qualified;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.ExecutorsKt;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: Firebase.kt */
public final class FirebaseCommonKtxRegistrar$getComponents$$inlined$coroutineDispatcher$4<T> implements ComponentFactory {
    public static final FirebaseCommonKtxRegistrar$getComponents$$inlined$coroutineDispatcher$4<T> INSTANCE = new FirebaseCommonKtxRegistrar$getComponents$$inlined$coroutineDispatcher$4<>();

    public final CoroutineDispatcher create(ComponentContainer c) {
        Object obj = c.get(Qualified.qualified(UiThread.class, Executor.class));
        Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
        return ExecutorsKt.from((Executor) obj);
    }
}
