package com.google.firebase.firestore.local;

import com.google.firebase.firestore.util.Supplier;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class LocalStore$$ExternalSyntheticLambda13 implements Supplier {
    public final /* synthetic */ LocalStore f$0;
    public final /* synthetic */ LruGarbageCollector f$1;

    public /* synthetic */ LocalStore$$ExternalSyntheticLambda13(LocalStore localStore, LruGarbageCollector lruGarbageCollector) {
        this.f$0 = localStore;
        this.f$1 = lruGarbageCollector;
    }

    public final Object get() {
        return this.f$0.m1845lambda$collectGarbage$18$comgooglefirebasefirestorelocalLocalStore(this.f$1);
    }
}
