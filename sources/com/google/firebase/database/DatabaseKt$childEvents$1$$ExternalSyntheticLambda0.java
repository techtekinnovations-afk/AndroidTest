package com.google.firebase.database;

import kotlin.jvm.functions.Function0;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class DatabaseKt$childEvents$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ Query f$0;
    public final /* synthetic */ ChildEventListener f$1;

    public /* synthetic */ DatabaseKt$childEvents$1$$ExternalSyntheticLambda0(Query query, ChildEventListener childEventListener) {
        this.f$0 = query;
        this.f$1 = childEventListener;
    }

    public final Object invoke() {
        return DatabaseKt$childEvents$1.invokeSuspend$lambda$0(this.f$0, this.f$1);
    }
}
