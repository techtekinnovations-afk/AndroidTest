package com.google.firebase.firestore.remote;

import com.google.firebase.firestore.remote.AndroidConnectivityMonitor;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class AndroidConnectivityMonitor$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ AndroidConnectivityMonitor f$0;
    public final /* synthetic */ AndroidConnectivityMonitor.NetworkReceiver f$1;

    public /* synthetic */ AndroidConnectivityMonitor$$ExternalSyntheticLambda1(AndroidConnectivityMonitor androidConnectivityMonitor, AndroidConnectivityMonitor.NetworkReceiver networkReceiver) {
        this.f$0 = androidConnectivityMonitor;
        this.f$1 = networkReceiver;
    }

    public final void run() {
        this.f$0.m1908lambda$configureNetworkMonitoring$1$comgooglefirebasefirestoreremoteAndroidConnectivityMonitor(this.f$1);
    }
}
