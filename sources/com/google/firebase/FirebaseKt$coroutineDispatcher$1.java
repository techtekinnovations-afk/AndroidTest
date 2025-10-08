package com.google.firebase;

import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentFactory;
import com.google.firebase.components.Qualified;
import java.lang.annotation.Annotation;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.ExecutorsKt;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: Firebase.kt */
public final class FirebaseKt$coroutineDispatcher$1<T> implements ComponentFactory {
    public static final FirebaseKt$coroutineDispatcher$1<T> INSTANCE = new FirebaseKt$coroutineDispatcher$1<>();

    public final CoroutineDispatcher create(ComponentContainer c) {
        Intrinsics.reifiedOperationMarker(4, "T");
        Class cls = Annotation.class;
        Class cls2 = cls;
        Class cls3 = Executor.class;
        Class cls4 = cls3;
        Object obj = c.get(Qualified.qualified(cls, cls3));
        Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
        return ExecutorsKt.from((Executor) obj);
    }
}
