package com.google.firebase.firestore.remote;

import com.google.firebase.firestore.core.OnlineState;
import com.google.firebase.firestore.remote.OnlineStateTracker;
import com.google.firebase.firestore.remote.RemoteStore;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class RemoteStore$$ExternalSyntheticLambda1 implements OnlineStateTracker.OnlineStateCallback {
    public final /* synthetic */ RemoteStore.RemoteStoreCallback f$0;

    public /* synthetic */ RemoteStore$$ExternalSyntheticLambda1(RemoteStore.RemoteStoreCallback remoteStoreCallback) {
        this.f$0 = remoteStoreCallback;
    }

    public final void handleOnlineStateChange(OnlineState onlineState) {
        this.f$0.handleOnlineStateChange(onlineState);
    }
}
