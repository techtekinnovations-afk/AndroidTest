package com.google.firebase.firestore.remote;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import io.grpc.ClientCall;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FirestoreChannel$$ExternalSyntheticLambda0 implements OnCompleteListener {
    public final /* synthetic */ FirestoreChannel f$0;
    public final /* synthetic */ ClientCall[] f$1;
    public final /* synthetic */ IncomingStreamObserver f$2;

    public /* synthetic */ FirestoreChannel$$ExternalSyntheticLambda0(FirestoreChannel firestoreChannel, ClientCall[] clientCallArr, IncomingStreamObserver incomingStreamObserver) {
        this.f$0 = firestoreChannel;
        this.f$1 = clientCallArr;
        this.f$2 = incomingStreamObserver;
    }

    public final void onComplete(Task task) {
        this.f$0.m1911lambda$runBidiStreamingRpc$0$comgooglefirebasefirestoreremoteFirestoreChannel(this.f$1, this.f$2, task);
    }
}
