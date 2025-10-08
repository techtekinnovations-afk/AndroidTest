package com.google.firebase.firestore.auth;

import com.google.firebase.auth.internal.IdTokenListener;
import com.google.firebase.internal.InternalTokenResult;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FirebaseAuthCredentialsProvider$$ExternalSyntheticLambda0 implements IdTokenListener {
    public final /* synthetic */ FirebaseAuthCredentialsProvider f$0;

    public /* synthetic */ FirebaseAuthCredentialsProvider$$ExternalSyntheticLambda0(FirebaseAuthCredentialsProvider firebaseAuthCredentialsProvider) {
        this.f$0 = firebaseAuthCredentialsProvider;
    }

    public final void onIdTokenChanged(InternalTokenResult internalTokenResult) {
        this.f$0.m1810lambda$new$0$comgooglefirebasefirestoreauthFirebaseAuthCredentialsProvider(internalTokenResult);
    }
}
