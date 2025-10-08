package com.google.firebase.firestore;

import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firebase.firestore.util.Function;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FirebaseFirestore$$ExternalSyntheticLambda9 implements Function {
    public final /* synthetic */ String f$0;

    public /* synthetic */ FirebaseFirestore$$ExternalSyntheticLambda9(String str) {
        this.f$0 = str;
    }

    public final Object apply(Object obj) {
        return ((FirestoreClient) obj).getNamedQuery(this.f$0);
    }
}
