package com.google.firebase.firestore;

import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firebase.firestore.util.Function;
import java.util.List;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FirebaseFirestore$$ExternalSyntheticLambda17 implements Function {
    public final /* synthetic */ List f$0;

    public /* synthetic */ FirebaseFirestore$$ExternalSyntheticLambda17(List list) {
        this.f$0 = list;
    }

    public final Object apply(Object obj) {
        return ((FirestoreClient) obj).configureFieldIndexes(this.f$0);
    }
}
