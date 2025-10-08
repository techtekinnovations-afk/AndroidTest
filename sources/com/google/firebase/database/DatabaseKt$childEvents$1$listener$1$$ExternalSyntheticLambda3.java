package com.google.firebase.database;

import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class DatabaseKt$childEvents$1$listener$1$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ ProducerScope f$0;
    public final /* synthetic */ DataSnapshot f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ DatabaseKt$childEvents$1$listener$1$$ExternalSyntheticLambda3(ProducerScope producerScope, DataSnapshot dataSnapshot, String str) {
        this.f$0 = producerScope;
        this.f$1 = dataSnapshot;
        this.f$2 = str;
    }

    public final void run() {
        DatabaseKt$childEvents$1$listener$1.onChildChanged$lambda$1(this.f$0, this.f$1, this.f$2);
    }
}
