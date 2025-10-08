package com.google.firebase.firestore;

import com.google.firebase.firestore.core.ViewSnapshot;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class DocumentReference$$ExternalSyntheticLambda5 implements EventListener {
    public final /* synthetic */ DocumentReference f$0;
    public final /* synthetic */ EventListener f$1;

    public /* synthetic */ DocumentReference$$ExternalSyntheticLambda5(DocumentReference documentReference, EventListener eventListener) {
        this.f$0 = documentReference;
        this.f$1 = eventListener;
    }

    public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
        this.f$0.m1790lambda$addSnapshotListenerInternal$6$comgooglefirebasefirestoreDocumentReference(this.f$1, (ViewSnapshot) obj, firebaseFirestoreException);
    }
}
