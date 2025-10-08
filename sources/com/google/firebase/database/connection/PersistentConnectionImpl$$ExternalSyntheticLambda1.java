package com.google.firebase.database.connection;

import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.connection.PersistentConnectionImpl;
import java.util.Map;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class PersistentConnectionImpl$$ExternalSyntheticLambda1 implements PersistentConnectionImpl.ConnectionRequestCallback {
    public final /* synthetic */ TaskCompletionSource f$0;

    public /* synthetic */ PersistentConnectionImpl$$ExternalSyntheticLambda1(TaskCompletionSource taskCompletionSource) {
        this.f$0 = taskCompletionSource;
    }

    public final void onResponse(Map map) {
        PersistentConnectionImpl.lambda$get$0(this.f$0, map);
    }
}
