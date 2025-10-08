package com.google.firebase.firestore.remote;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import java.util.HashMap;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class Datastore$$ExternalSyntheticLambda0 implements Continuation {
    public final /* synthetic */ Datastore f$0;
    public final /* synthetic */ HashMap f$1;

    public /* synthetic */ Datastore$$ExternalSyntheticLambda0(Datastore datastore, HashMap hashMap) {
        this.f$0 = datastore;
        this.f$1 = hashMap;
    }

    public final Object then(Task task) {
        return this.f$0.m1910lambda$runAggregateQuery$1$comgooglefirebasefirestoreremoteDatastore(this.f$1, task);
    }
}
