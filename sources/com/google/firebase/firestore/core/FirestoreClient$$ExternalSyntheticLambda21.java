package com.google.firebase.firestore.core;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FirestoreClient$$ExternalSyntheticLambda21 implements OnFailureListener {
    public final /* synthetic */ TaskCompletionSource f$0;

    public /* synthetic */ FirestoreClient$$ExternalSyntheticLambda21(TaskCompletionSource taskCompletionSource) {
        this.f$0 = taskCompletionSource;
    }

    public final void onFailure(Exception exc) {
        this.f$0.setException(exc);
    }
}
