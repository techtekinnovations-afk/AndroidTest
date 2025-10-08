package com.google.firebase.database.android;

import com.google.firebase.database.core.TokenProvider;
import com.google.firebase.internal.InternalTokenResult;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class AndroidAuthTokenProvider$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ TokenProvider.TokenChangeListener f$0;
    public final /* synthetic */ InternalTokenResult f$1;

    public /* synthetic */ AndroidAuthTokenProvider$$ExternalSyntheticLambda2(TokenProvider.TokenChangeListener tokenChangeListener, InternalTokenResult internalTokenResult) {
        this.f$0 = tokenChangeListener;
        this.f$1 = internalTokenResult;
    }

    public final void run() {
        this.f$0.onTokenChange(this.f$1.getToken());
    }
}
