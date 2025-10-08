package com.google.firebase.database.android;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.appcheck.AppCheckTokenResult;
import com.google.firebase.database.core.TokenProvider;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class AndroidAppCheckTokenProvider$$ExternalSyntheticLambda4 implements OnSuccessListener {
    public final /* synthetic */ TokenProvider.GetTokenCompletionListener f$0;

    public /* synthetic */ AndroidAppCheckTokenProvider$$ExternalSyntheticLambda4(TokenProvider.GetTokenCompletionListener getTokenCompletionListener) {
        this.f$0 = getTokenCompletionListener;
    }

    public final void onSuccess(Object obj) {
        this.f$0.onSuccess(((AppCheckTokenResult) obj).getToken());
    }
}
