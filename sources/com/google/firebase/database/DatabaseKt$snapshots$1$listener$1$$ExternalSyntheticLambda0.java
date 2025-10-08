package com.google.firebase.database;

import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class DatabaseKt$snapshots$1$listener$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ ProducerScope f$0;
    public final /* synthetic */ DataSnapshot f$1;

    public /* synthetic */ DatabaseKt$snapshots$1$listener$1$$ExternalSyntheticLambda0(ProducerScope producerScope, DataSnapshot dataSnapshot) {
        this.f$0 = producerScope;
        this.f$1 = dataSnapshot;
    }

    public final void run() {
        DatabaseKt$snapshots$1$listener$1.onDataChange$lambda$0(this.f$0, this.f$1);
    }
}
