package com.google.firebase.firestore.remote;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.remote.FirestoreChannel;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FirestoreChannel$$ExternalSyntheticLambda2 implements OnCompleteListener {
    public final /* synthetic */ FirestoreChannel f$0;
    public final /* synthetic */ FirestoreChannel.StreamingListener f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ FirestoreChannel$$ExternalSyntheticLambda2(FirestoreChannel firestoreChannel, FirestoreChannel.StreamingListener streamingListener, Object obj) {
        this.f$0 = firestoreChannel;
        this.f$1 = streamingListener;
        this.f$2 = obj;
    }

    public final void onComplete(Task task) {
        this.f$0.m1913lambda$runStreamingResponseRpc$1$comgooglefirebasefirestoreremoteFirestoreChannel(this.f$1, this.f$2, task);
    }
}
