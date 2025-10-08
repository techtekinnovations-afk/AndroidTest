package com.google.firebase.database.core;

import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class Repo$9$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ TaskCompletionSource f$0;
    public final /* synthetic */ DataSnapshot f$1;

    public /* synthetic */ Repo$9$$ExternalSyntheticLambda0(TaskCompletionSource taskCompletionSource, DataSnapshot dataSnapshot) {
        this.f$0 = taskCompletionSource;
        this.f$1 = dataSnapshot;
    }

    public final void run() {
        this.f$0.trySetResult(this.f$1);
    }
}
