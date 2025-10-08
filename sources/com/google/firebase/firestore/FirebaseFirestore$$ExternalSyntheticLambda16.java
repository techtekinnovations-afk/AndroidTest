package com.google.firebase.firestore;

import android.app.Activity;
import com.google.firebase.firestore.core.AsyncEventListener;
import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firebase.firestore.util.Function;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FirebaseFirestore$$ExternalSyntheticLambda16 implements Function {
    public final /* synthetic */ AsyncEventListener f$0;
    public final /* synthetic */ Activity f$1;

    public /* synthetic */ FirebaseFirestore$$ExternalSyntheticLambda16(AsyncEventListener asyncEventListener, Activity activity) {
        this.f$0 = asyncEventListener;
        this.f$1 = activity;
    }

    public final Object apply(Object obj) {
        return ((FirestoreClient) obj).addSnapshotsInSyncListener(this.f$0);
    }
}
