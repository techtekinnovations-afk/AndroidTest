package com.google.firebase.firestore.auth;

import com.google.firebase.appcheck.AppCheckTokenResult;
import com.google.firebase.appcheck.interop.AppCheckTokenListener;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FirebaseAppCheckTokenProvider$$ExternalSyntheticLambda0 implements AppCheckTokenListener {
    public final /* synthetic */ FirebaseAppCheckTokenProvider f$0;

    public /* synthetic */ FirebaseAppCheckTokenProvider$$ExternalSyntheticLambda0(FirebaseAppCheckTokenProvider firebaseAppCheckTokenProvider) {
        this.f$0 = firebaseAppCheckTokenProvider;
    }

    public final void onAppCheckTokenChanged(AppCheckTokenResult appCheckTokenResult) {
        this.f$0.m1807lambda$new$0$comgooglefirebasefirestoreauthFirebaseAppCheckTokenProvider(appCheckTokenResult);
    }
}
