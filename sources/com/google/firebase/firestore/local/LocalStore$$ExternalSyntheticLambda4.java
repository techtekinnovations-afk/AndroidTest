package com.google.firebase.firestore.local;

import com.google.firebase.database.collection.ImmutableSortedSet;
import com.google.firebase.firestore.bundle.NamedQuery;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class LocalStore$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ LocalStore f$0;
    public final /* synthetic */ NamedQuery f$1;
    public final /* synthetic */ TargetData f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ ImmutableSortedSet f$4;

    public /* synthetic */ LocalStore$$ExternalSyntheticLambda4(LocalStore localStore, NamedQuery namedQuery, TargetData targetData, int i, ImmutableSortedSet immutableSortedSet) {
        this.f$0 = localStore;
        this.f$1 = namedQuery;
        this.f$2 = targetData;
        this.f$3 = i;
        this.f$4 = immutableSortedSet;
    }

    public final void run() {
        this.f$0.m1855lambda$saveNamedQuery$12$comgooglefirebasefirestorelocalLocalStore(this.f$1, this.f$2, this.f$3, this.f$4);
    }
}
