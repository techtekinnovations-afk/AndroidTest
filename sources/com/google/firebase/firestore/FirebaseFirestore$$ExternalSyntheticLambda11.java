package com.google.firebase.firestore;

import com.google.firebase.firestore.util.Function;
import java.util.concurrent.Executor;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FirebaseFirestore$$ExternalSyntheticLambda11 implements Function {
    public final /* synthetic */ FirebaseFirestore f$0;

    public /* synthetic */ FirebaseFirestore$$ExternalSyntheticLambda11(FirebaseFirestore firebaseFirestore) {
        this.f$0 = firebaseFirestore;
    }

    public final Object apply(Object obj) {
        return this.f$0.clearPersistence((Executor) obj);
    }
}
