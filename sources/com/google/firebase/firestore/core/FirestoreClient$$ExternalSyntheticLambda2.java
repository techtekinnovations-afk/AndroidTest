package com.google.firebase.firestore.core;

import com.google.firebase.firestore.model.DocumentKey;
import java.util.concurrent.Callable;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FirestoreClient$$ExternalSyntheticLambda2 implements Callable {
    public final /* synthetic */ FirestoreClient f$0;
    public final /* synthetic */ DocumentKey f$1;

    public /* synthetic */ FirestoreClient$$ExternalSyntheticLambda2(FirestoreClient firestoreClient, DocumentKey documentKey) {
        this.f$0 = firestoreClient;
        this.f$1 = documentKey;
    }

    public final Object call() {
        return this.f$0.m1818lambda$getDocumentFromLocalCache$9$comgooglefirebasefirestorecoreFirestoreClient(this.f$1);
    }
}
