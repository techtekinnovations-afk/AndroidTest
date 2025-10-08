package com.google.firebase.firestore;

import androidx.core.util.Consumer;
import com.google.firebase.firestore.core.FirestoreClient;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class PersistentCacheIndexManager$$ExternalSyntheticLambda2 implements Consumer {
    public final void accept(Object obj) {
        ((FirestoreClient) obj).deleteAllFieldIndexes();
    }
}
