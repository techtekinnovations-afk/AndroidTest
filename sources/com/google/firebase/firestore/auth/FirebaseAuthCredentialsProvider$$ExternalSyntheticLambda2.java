package com.google.firebase.firestore.auth;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FirebaseAuthCredentialsProvider$$ExternalSyntheticLambda2 implements Continuation {
    public final /* synthetic */ FirebaseAuthCredentialsProvider f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ FirebaseAuthCredentialsProvider$$ExternalSyntheticLambda2(FirebaseAuthCredentialsProvider firebaseAuthCredentialsProvider, int i) {
        this.f$0 = firebaseAuthCredentialsProvider;
        this.f$1 = i;
    }

    public final Object then(Task task) {
        return this.f$0.m1809lambda$getToken$2$comgooglefirebasefirestoreauthFirebaseAuthCredentialsProvider(this.f$1, task);
    }
}
