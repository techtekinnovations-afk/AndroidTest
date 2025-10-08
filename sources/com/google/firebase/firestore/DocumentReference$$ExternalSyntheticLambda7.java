package com.google.firebase.firestore;

import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class DocumentReference$$ExternalSyntheticLambda7 implements EventListener {
    public final /* synthetic */ TaskCompletionSource f$0;
    public final /* synthetic */ TaskCompletionSource f$1;
    public final /* synthetic */ Source f$2;

    public /* synthetic */ DocumentReference$$ExternalSyntheticLambda7(TaskCompletionSource taskCompletionSource, TaskCompletionSource taskCompletionSource2, Source source) {
        this.f$0 = taskCompletionSource;
        this.f$1 = taskCompletionSource2;
        this.f$2 = source;
    }

    public final void onEvent(Object obj, FirebaseFirestoreException firebaseFirestoreException) {
        DocumentReference.lambda$getViaSnapshotListener$5(this.f$0, this.f$1, this.f$2, (DocumentSnapshot) obj, firebaseFirestoreException);
    }
}
