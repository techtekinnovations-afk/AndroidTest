package com.google.firebase.firestore;

import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firebase.firestore.util.Function;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FirebaseFirestore$$ExternalSyntheticLambda14 implements Function {
    public final /* synthetic */ TransactionOptions f$0;
    public final /* synthetic */ Function f$1;

    public /* synthetic */ FirebaseFirestore$$ExternalSyntheticLambda14(TransactionOptions transactionOptions, Function function) {
        this.f$0 = transactionOptions;
        this.f$1 = function;
    }

    public final Object apply(Object obj) {
        return ((FirestoreClient) obj).transaction(this.f$0, this.f$1);
    }
}
