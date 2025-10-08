package com.google.firebase;

import android.content.Context;
import com.google.firebase.components.Component;
import com.google.firebase.components.Dependency;
import com.google.firebase.components.Qualified;
import java.lang.annotation.Annotation;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;

@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001b\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u0014\u0010\u0007\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\b\u001a\u00020\t\u001a\u001a\u0010\u0007\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b\u001a\"\u0010\u0007\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u001b\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f\"\n\b\u0000\u0010\u0011\u0018\u0001*\u00020\u0012H\b\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\n\u001a\u00020\u000b*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0013"}, d2 = {"app", "Lcom/google/firebase/FirebaseApp;", "Lcom/google/firebase/Firebase;", "getApp", "(Lcom/google/firebase/Firebase;)Lcom/google/firebase/FirebaseApp;", "name", "", "initialize", "context", "Landroid/content/Context;", "options", "Lcom/google/firebase/FirebaseOptions;", "getOptions", "(Lcom/google/firebase/Firebase;)Lcom/google/firebase/FirebaseOptions;", "coroutineDispatcher", "Lcom/google/firebase/components/Component;", "Lkotlinx/coroutines/CoroutineDispatcher;", "T", "", "com.google.firebase-firebase-common"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: Firebase.kt */
public final class FirebaseKt {
    public static final FirebaseApp getApp(Firebase $this$app) {
        Intrinsics.checkNotNullParameter($this$app, "<this>");
        FirebaseApp instance = FirebaseApp.getInstance();
        Intrinsics.checkNotNullExpressionValue(instance, "getInstance(...)");
        return instance;
    }

    public static final FirebaseApp app(Firebase $this$app, String name) {
        Intrinsics.checkNotNullParameter($this$app, "<this>");
        Intrinsics.checkNotNullParameter(name, "name");
        FirebaseApp instance = FirebaseApp.getInstance(name);
        Intrinsics.checkNotNullExpressionValue(instance, "getInstance(...)");
        return instance;
    }

    public static final FirebaseApp initialize(Firebase $this$initialize, Context context) {
        Intrinsics.checkNotNullParameter($this$initialize, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return FirebaseApp.initializeApp(context);
    }

    public static final FirebaseApp initialize(Firebase $this$initialize, Context context, FirebaseOptions options) {
        Intrinsics.checkNotNullParameter($this$initialize, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(options, "options");
        FirebaseApp initializeApp = FirebaseApp.initializeApp(context, options);
        Intrinsics.checkNotNullExpressionValue(initializeApp, "initializeApp(...)");
        return initializeApp;
    }

    public static final FirebaseApp initialize(Firebase $this$initialize, Context context, FirebaseOptions options, String name) {
        Intrinsics.checkNotNullParameter($this$initialize, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.checkNotNullParameter(name, "name");
        FirebaseApp initializeApp = FirebaseApp.initializeApp(context, options, name);
        Intrinsics.checkNotNullExpressionValue(initializeApp, "initializeApp(...)");
        return initializeApp;
    }

    public static final FirebaseOptions getOptions(Firebase $this$options) {
        Intrinsics.checkNotNullParameter($this$options, "<this>");
        FirebaseOptions options = getApp(Firebase.INSTANCE).getOptions();
        Intrinsics.checkNotNullExpressionValue(options, "getOptions(...)");
        return options;
    }

    private static final /* synthetic */ <T extends Annotation> Component<CoroutineDispatcher> coroutineDispatcher() {
        Intrinsics.reifiedOperationMarker(4, "T");
        Class<Annotation> cls = Annotation.class;
        Class cls2 = cls;
        Class<CoroutineDispatcher> cls3 = CoroutineDispatcher.class;
        Class cls4 = cls3;
        Component.Builder<CoroutineDispatcher> builder = Component.builder(Qualified.qualified(cls, cls3));
        Intrinsics.reifiedOperationMarker(4, "T");
        Class<Annotation> cls5 = Annotation.class;
        Class cls6 = cls5;
        Class<Executor> cls7 = Executor.class;
        Class cls8 = cls7;
        Component.Builder<CoroutineDispatcher> add = builder.add(Dependency.required((Qualified<?>) Qualified.qualified(cls5, cls7)));
        Intrinsics.needClassReification();
        Component<CoroutineDispatcher> build = add.factory(FirebaseKt$coroutineDispatcher$1.INSTANCE).build();
        Intrinsics.checkNotNullExpressionValue(build, "build(...)");
        Component component = build;
        return build;
    }
}
