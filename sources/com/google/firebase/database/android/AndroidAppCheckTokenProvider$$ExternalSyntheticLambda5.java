package com.google.firebase.database.android;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.core.TokenProvider;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class AndroidAppCheckTokenProvider$$ExternalSyntheticLambda5 implements OnFailureListener {
    public final /* synthetic */ TokenProvider.GetTokenCompletionListener f$0;

    public /* synthetic */ AndroidAppCheckTokenProvider$$ExternalSyntheticLambda5(TokenProvider.GetTokenCompletionListener getTokenCompletionListener) {
        this.f$0 = getTokenCompletionListener;
    }

    public final void onFailure(Exception exc) {
        this.f$0.onError(exc.getMessage());
    }
}
