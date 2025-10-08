package com.google.firebase.firestore;

import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.Function;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FirebaseFirestore$$ExternalSyntheticLambda5 implements Function {
    public final /* synthetic */ FirebaseFirestore f$0;

    public /* synthetic */ FirebaseFirestore$$ExternalSyntheticLambda5(FirebaseFirestore firebaseFirestore) {
        this.f$0 = firebaseFirestore;
    }

    public final Object apply(Object obj) {
        return this.f$0.newClient((AsyncQueue) obj);
    }
}
