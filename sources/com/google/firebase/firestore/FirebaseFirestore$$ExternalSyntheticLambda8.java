package com.google.firebase.firestore;

import com.google.firebase.firestore.core.AsyncEventListener;
import com.google.firebase.firestore.core.FirestoreClient;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FirebaseFirestore$$ExternalSyntheticLambda8 implements ListenerRegistration {
    public final /* synthetic */ AsyncEventListener f$0;
    public final /* synthetic */ FirestoreClient f$1;

    public /* synthetic */ FirebaseFirestore$$ExternalSyntheticLambda8(AsyncEventListener asyncEventListener, FirestoreClient firestoreClient) {
        this.f$0 = asyncEventListener;
        this.f$1 = firestoreClient;
    }

    public final void remove() {
        FirebaseFirestore.lambda$addSnapshotsInSyncListener$10(this.f$0, this.f$1);
    }
}
