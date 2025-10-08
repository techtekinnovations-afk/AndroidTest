package com.google.firebase.firestore.remote;

import com.google.android.gms.tasks.OnSuccessListener;
import io.grpc.ClientCall;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FirestoreChannel$2$$ExternalSyntheticLambda0 implements OnSuccessListener {
    public final void onSuccess(Object obj) {
        ((ClientCall) obj).halfClose();
    }
}
