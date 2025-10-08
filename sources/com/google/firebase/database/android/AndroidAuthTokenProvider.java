package com.google.firebase.database.android;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.database.core.TokenProvider;
import com.google.firebase.inject.Deferred;
import com.google.firebase.inject.Provider;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

public class AndroidAuthTokenProvider implements TokenProvider {
    private final Deferred<InternalAuthProvider> deferredAuthProvider;
    private final AtomicReference<InternalAuthProvider> internalAuth = new AtomicReference<>();

    public AndroidAuthTokenProvider(Deferred<InternalAuthProvider> deferredAuthProvider2) {
        this.deferredAuthProvider = deferredAuthProvider2;
        deferredAuthProvider2.whenAvailable(new AndroidAuthTokenProvider$$ExternalSyntheticLambda1(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-google-firebase-database-android-AndroidAuthTokenProvider  reason: not valid java name */
    public /* synthetic */ void m1780lambda$new$0$comgooglefirebasedatabaseandroidAndroidAuthTokenProvider(Provider authProvider) {
        this.internalAuth.set((InternalAuthProvider) authProvider.get());
    }

    public void getToken(boolean forceRefresh, TokenProvider.GetTokenCompletionListener listener) {
        InternalAuthProvider authProvider = this.internalAuth.get();
        if (authProvider != null) {
            authProvider.getAccessToken(forceRefresh).addOnSuccessListener(new AndroidAuthTokenProvider$$ExternalSyntheticLambda3(listener)).addOnFailureListener(new AndroidAuthTokenProvider$$ExternalSyntheticLambda4(listener));
        } else {
            listener.onSuccess((String) null);
        }
    }

    static /* synthetic */ void lambda$getToken$2(TokenProvider.GetTokenCompletionListener listener, Exception e) {
        if (isUnauthenticatedUsage(e)) {
            listener.onSuccess((String) null);
        } else {
            listener.onError(e.getMessage());
        }
    }

    public void addTokenChangeListener(ExecutorService executorService, TokenProvider.TokenChangeListener tokenListener) {
        this.deferredAuthProvider.whenAvailable(new AndroidAuthTokenProvider$$ExternalSyntheticLambda0(executorService, tokenListener));
    }

    public void removeTokenChangeListener(TokenProvider.TokenChangeListener tokenListener) {
    }

    private static boolean isUnauthenticatedUsage(Exception e) {
        return (e instanceof FirebaseApiNotAvailableException) || (e instanceof FirebaseNoSignedInUserException);
    }
}
