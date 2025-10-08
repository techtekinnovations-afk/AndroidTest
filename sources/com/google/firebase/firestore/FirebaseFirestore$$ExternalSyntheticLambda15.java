package com.google.firebase.firestore;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FirebaseFirestore$$ExternalSyntheticLambda15 implements EventListener {
    public final /* synthetic */ Runnable f$0;

    public /* synthetic */ FirebaseFirestore$$ExternalSyntheticLambda15(Runnable runnable) {
        this.f$0 = runnable;
    }

    public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
        FirebaseFirestore.lambda$addSnapshotsInSyncListener$9(this.f$0, (Void) obj, firebaseFirestoreException);
    }
}
