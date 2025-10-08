package io.grpc.internal;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class PickFirstLeafLoadBalancer$RequestConnectionPicker$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ PickFirstLeafLoadBalancer f$0;

    public /* synthetic */ PickFirstLeafLoadBalancer$RequestConnectionPicker$$ExternalSyntheticLambda0(PickFirstLeafLoadBalancer pickFirstLeafLoadBalancer) {
        this.f$0 = pickFirstLeafLoadBalancer;
    }

    public final void run() {
        this.f$0.requestConnection();
    }
}
