package com.google.firebase.firestore.auth;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.appcheck.AppCheckTokenResult;
import com.google.firebase.appcheck.interop.AppCheckTokenListener;
import com.google.firebase.appcheck.interop.InteropAppCheckTokenProvider;
import com.google.firebase.firestore.util.Executors;
import com.google.firebase.firestore.util.Listener;
import com.google.firebase.firestore.util.Logger;
import com.google.firebase.inject.Deferred;
import com.google.firebase.inject.Provider;

public final class FirebaseAppCheckTokenProvider extends CredentialsProvider<String> {
    private static final String LOG_TAG = "FirebaseAppCheckTokenProvider";
    private Listener<String> changeListener;
    private boolean forceRefresh;
    private InteropAppCheckTokenProvider interopAppCheckTokenProvider;
    private final AppCheckTokenListener tokenListener = new FirebaseAppCheckTokenProvider$$ExternalSyntheticLambda0(this);

    public FirebaseAppCheckTokenProvider(Deferred<InteropAppCheckTokenProvider> deferredAppCheckTokenProvider) {
        deferredAppCheckTokenProvider.whenAvailable(new FirebaseAppCheckTokenProvider$$ExternalSyntheticLambda1(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$new$1$com-google-firebase-firestore-auth-FirebaseAppCheckTokenProvider  reason: not valid java name */
    public /* synthetic */ void m1808lambda$new$1$comgooglefirebasefirestoreauthFirebaseAppCheckTokenProvider(Provider provider) {
        synchronized (this) {
            this.interopAppCheckTokenProvider = (InteropAppCheckTokenProvider) provider.get();
            if (this.interopAppCheckTokenProvider != null) {
                this.interopAppCheckTokenProvider.addAppCheckTokenListener(this.tokenListener);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: onTokenChanged */
    public synchronized void m1807lambda$new$0$comgooglefirebasefirestoreauthFirebaseAppCheckTokenProvider(AppCheckTokenResult result) {
        if (result.getError() != null) {
            Logger.warn(LOG_TAG, "Error getting App Check token; using placeholder token instead. Error: " + result.getError(), new Object[0]);
        }
        if (this.changeListener != null) {
            this.changeListener.onValue(result.getToken());
        }
    }

    public synchronized Task<String> getToken() {
        if (this.interopAppCheckTokenProvider == null) {
            return Tasks.forException(new FirebaseApiNotAvailableException("AppCheck is not available"));
        }
        Task<AppCheckTokenResult> res = this.interopAppCheckTokenProvider.getToken(this.forceRefresh);
        this.forceRefresh = false;
        return res.continueWithTask(Executors.DIRECT_EXECUTOR, new FirebaseAppCheckTokenProvider$$ExternalSyntheticLambda2());
    }

    static /* synthetic */ Task lambda$getToken$2(Task task) throws Exception {
        if (task.isSuccessful()) {
            return Tasks.forResult(((AppCheckTokenResult) task.getResult()).getToken());
        }
        return Tasks.forException(task.getException());
    }

    public synchronized void invalidateToken() {
        this.forceRefresh = true;
    }

    public synchronized void removeChangeListener() {
        this.changeListener = null;
        if (this.interopAppCheckTokenProvider != null) {
            this.interopAppCheckTokenProvider.removeAppCheckTokenListener(this.tokenListener);
        }
    }

    public synchronized void setChangeListener(Listener<String> changeListener2) {
        this.changeListener = changeListener2;
    }
}
