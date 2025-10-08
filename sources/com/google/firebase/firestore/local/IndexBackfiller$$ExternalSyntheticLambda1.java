package com.google.firebase.firestore.local;

import com.google.common.base.Supplier;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class IndexBackfiller$$ExternalSyntheticLambda1 implements Supplier {
    public final /* synthetic */ LocalStore f$0;

    public /* synthetic */ IndexBackfiller$$ExternalSyntheticLambda1(LocalStore localStore) {
        this.f$0 = localStore;
    }

    public final Object get() {
        return this.f$0.getLocalDocumentsForCurrentUser();
    }
}
