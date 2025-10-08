package com.google.firebase.firestore;

import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firebase.firestore.util.Function;
import java.util.List;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class DocumentReference$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ List f$0;

    public /* synthetic */ DocumentReference$$ExternalSyntheticLambda1(List list) {
        this.f$0 = list;
    }

    public final Object apply(Object obj) {
        return ((FirestoreClient) obj).write(this.f$0);
    }
}
