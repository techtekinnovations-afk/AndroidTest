package com.google.firebase.firestore.remote;

import com.google.firebase.firestore.remote.AndroidConnectivityMonitor;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class AndroidConnectivityMonitor$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ AndroidConnectivityMonitor f$0;
    public final /* synthetic */ AndroidConnectivityMonitor.DefaultNetworkCallback f$1;

    public /* synthetic */ AndroidConnectivityMonitor$$ExternalSyntheticLambda0(AndroidConnectivityMonitor androidConnectivityMonitor, AndroidConnectivityMonitor.DefaultNetworkCallback defaultNetworkCallback) {
        this.f$0 = androidConnectivityMonitor;
        this.f$1 = defaultNetworkCallback;
    }

    public final void run() {
        this.f$0.m1907lambda$configureNetworkMonitoring$0$comgooglefirebasefirestoreremoteAndroidConnectivityMonitor(this.f$1);
    }
}
