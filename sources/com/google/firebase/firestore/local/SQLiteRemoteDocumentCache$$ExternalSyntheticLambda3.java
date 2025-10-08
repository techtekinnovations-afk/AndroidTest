package com.google.firebase.firestore.local;

import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.model.MutableDocument;
import com.google.firebase.firestore.util.Function;
import java.util.Set;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SQLiteRemoteDocumentCache$$ExternalSyntheticLambda3 implements Function {
    public final /* synthetic */ Query f$0;
    public final /* synthetic */ Set f$1;

    public /* synthetic */ SQLiteRemoteDocumentCache$$ExternalSyntheticLambda3(Query query, Set set) {
        this.f$0 = query;
        this.f$1 = set;
    }

    public final Object apply(Object obj) {
        return SQLiteRemoteDocumentCache.lambda$getDocumentsMatchingQuery$3(this.f$0, this.f$1, (MutableDocument) obj);
    }
}
