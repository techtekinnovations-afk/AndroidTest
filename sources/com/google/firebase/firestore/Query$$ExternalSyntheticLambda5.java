package com.google.firebase.firestore;

import com.google.firebase.firestore.core.AsyncEventListener;
import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firebase.firestore.core.QueryListener;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class Query$$ExternalSyntheticLambda5 implements ListenerRegistration {
    public final /* synthetic */ AsyncEventListener f$0;
    public final /* synthetic */ FirestoreClient f$1;
    public final /* synthetic */ QueryListener f$2;

    public /* synthetic */ Query$$ExternalSyntheticLambda5(AsyncEventListener asyncEventListener, FirestoreClient firestoreClient, QueryListener queryListener) {
        this.f$0 = asyncEventListener;
        this.f$1 = firestoreClient;
        this.f$2 = queryListener;
    }

    public final void remove() {
        Query.lambda$addSnapshotListenerInternal$4(this.f$0, this.f$1, this.f$2);
    }
}
