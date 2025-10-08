package com.google.firebase.firestore;

import android.app.Activity;
import com.google.firebase.firestore.core.AsyncEventListener;
import com.google.firebase.firestore.core.EventManager;
import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firebase.firestore.util.Function;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class Query$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ Query f$0;
    public final /* synthetic */ EventManager.ListenOptions f$1;
    public final /* synthetic */ AsyncEventListener f$2;
    public final /* synthetic */ Activity f$3;

    public /* synthetic */ Query$$ExternalSyntheticLambda1(Query query, EventManager.ListenOptions listenOptions, AsyncEventListener asyncEventListener, Activity activity) {
        this.f$0 = query;
        this.f$1 = listenOptions;
        this.f$2 = asyncEventListener;
        this.f$3 = activity;
    }

    public final Object apply(Object obj) {
        return this.f$0.m1802lambda$addSnapshotListenerInternal$5$comgooglefirebasefirestoreQuery(this.f$1, this.f$2, this.f$3, (FirestoreClient) obj);
    }
}
