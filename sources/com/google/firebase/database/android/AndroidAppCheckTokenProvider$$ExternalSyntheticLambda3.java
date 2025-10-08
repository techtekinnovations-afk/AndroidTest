package com.google.firebase.database.android;

import com.google.firebase.appcheck.interop.InteropAppCheckTokenProvider;
import com.google.firebase.database.core.TokenProvider;
import com.google.firebase.inject.Deferred;
import com.google.firebase.inject.Provider;
import java.util.concurrent.ExecutorService;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class AndroidAppCheckTokenProvider$$ExternalSyntheticLambda3 implements Deferred.DeferredHandler {
    public final /* synthetic */ ExecutorService f$0;
    public final /* synthetic */ TokenProvider.TokenChangeListener f$1;

    public /* synthetic */ AndroidAppCheckTokenProvider$$ExternalSyntheticLambda3(ExecutorService executorService, TokenProvider.TokenChangeListener tokenChangeListener) {
        this.f$0 = executorService;
        this.f$1 = tokenChangeListener;
    }

    public final void handle(Provider provider) {
        ((InteropAppCheckTokenProvider) provider.get()).addAppCheckTokenListener(new AndroidAppCheckTokenProvider$$ExternalSyntheticLambda2(this.f$0, this.f$1));
    }
}
