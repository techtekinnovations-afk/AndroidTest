package com.google.firebase.database.connection;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class PersistentConnectionImpl$$ExternalSyntheticLambda2 implements OnSuccessListener {
    public final /* synthetic */ PersistentConnectionImpl f$0;
    public final /* synthetic */ long f$1;
    public final /* synthetic */ Task f$2;
    public final /* synthetic */ Task f$3;

    public /* synthetic */ PersistentConnectionImpl$$ExternalSyntheticLambda2(PersistentConnectionImpl persistentConnectionImpl, long j, Task task, Task task2) {
        this.f$0 = persistentConnectionImpl;
        this.f$1 = j;
        this.f$2 = task;
        this.f$3 = task2;
    }

    public final void onSuccess(Object obj) {
        this.f$0.m1782lambda$tryScheduleReconnect$1$comgooglefirebasedatabaseconnectionPersistentConnectionImpl(this.f$1, this.f$2, this.f$3, (Void) obj);
    }
}
