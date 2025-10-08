package com.google.firebase.database.android;

import com.google.firebase.auth.internal.IdTokenListener;
import com.google.firebase.database.core.TokenProvider;
import com.google.firebase.internal.InternalTokenResult;
import java.util.concurrent.ExecutorService;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class AndroidAuthTokenProvider$$ExternalSyntheticLambda5 implements IdTokenListener {
    public final /* synthetic */ ExecutorService f$0;
    public final /* synthetic */ TokenProvider.TokenChangeListener f$1;

    public /* synthetic */ AndroidAuthTokenProvider$$ExternalSyntheticLambda5(ExecutorService executorService, TokenProvider.TokenChangeListener tokenChangeListener) {
        this.f$0 = executorService;
        this.f$1 = tokenChangeListener;
    }

    public final void onIdTokenChanged(InternalTokenResult internalTokenResult) {
        this.f$0.execute(new AndroidAuthTokenProvider$$ExternalSyntheticLambda2(this.f$1, internalTokenResult));
    }
}
