package com.google.firebase.database.core;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;
import com.google.firebase.database.core.Repo;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class Repo$9$$ExternalSyntheticLambda1 implements OnCompleteListener {
    public final /* synthetic */ Repo.AnonymousClass9 f$0;
    public final /* synthetic */ TaskCompletionSource f$1;
    public final /* synthetic */ DataSnapshot f$2;
    public final /* synthetic */ Query f$3;
    public final /* synthetic */ Repo f$4;

    public /* synthetic */ Repo$9$$ExternalSyntheticLambda1(Repo.AnonymousClass9 r1, TaskCompletionSource taskCompletionSource, DataSnapshot dataSnapshot, Query query, Repo repo) {
        this.f$0 = r1;
        this.f$1 = taskCompletionSource;
        this.f$2 = dataSnapshot;
        this.f$3 = query;
        this.f$4 = repo;
    }

    public final void onComplete(Task task) {
        this.f$0.m1785lambda$run$1$comgooglefirebasedatabasecoreRepo$9(this.f$1, this.f$2, this.f$3, this.f$4, task);
    }
}
