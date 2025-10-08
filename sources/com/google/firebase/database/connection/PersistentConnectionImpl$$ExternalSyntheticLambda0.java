package com.google.firebase.database.connection;

import com.google.firebase.database.connection.PersistentConnectionImpl;
import java.util.Map;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class PersistentConnectionImpl$$ExternalSyntheticLambda0 implements PersistentConnectionImpl.ConnectionRequestCallback {
    public final /* synthetic */ PersistentConnectionImpl f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ PersistentConnectionImpl$$ExternalSyntheticLambda0(PersistentConnectionImpl persistentConnectionImpl, boolean z) {
        this.f$0 = persistentConnectionImpl;
        this.f$1 = z;
    }

    public final void onResponse(Map map) {
        this.f$0.m1781lambda$sendAppCheckTokenHelper$4$comgooglefirebasedatabaseconnectionPersistentConnectionImpl(this.f$1, map);
    }
}
