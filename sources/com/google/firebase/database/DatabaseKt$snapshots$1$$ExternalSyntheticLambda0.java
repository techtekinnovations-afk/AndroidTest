package com.google.firebase.database;

import kotlin.jvm.functions.Function0;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class DatabaseKt$snapshots$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ Query f$0;
    public final /* synthetic */ ValueEventListener f$1;

    public /* synthetic */ DatabaseKt$snapshots$1$$ExternalSyntheticLambda0(Query query, ValueEventListener valueEventListener) {
        this.f$0 = query;
        this.f$1 = valueEventListener;
    }

    public final Object invoke() {
        return DatabaseKt$snapshots$1.invokeSuspend$lambda$0(this.f$0, this.f$1);
    }
}
