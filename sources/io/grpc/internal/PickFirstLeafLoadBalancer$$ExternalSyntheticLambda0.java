package io.grpc.internal;

import io.grpc.ConnectivityStateInfo;
import io.grpc.LoadBalancer;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class PickFirstLeafLoadBalancer$$ExternalSyntheticLambda0 implements LoadBalancer.SubchannelStateListener {
    public final /* synthetic */ PickFirstLeafLoadBalancer f$0;
    public final /* synthetic */ LoadBalancer.Subchannel f$1;

    public /* synthetic */ PickFirstLeafLoadBalancer$$ExternalSyntheticLambda0(PickFirstLeafLoadBalancer pickFirstLeafLoadBalancer, LoadBalancer.Subchannel subchannel) {
        this.f$0 = pickFirstLeafLoadBalancer;
        this.f$1 = subchannel;
    }

    public final void onSubchannelState(ConnectivityStateInfo connectivityStateInfo) {
        this.f$0.m1936lambda$createNewSubchannel$0$iogrpcinternalPickFirstLeafLoadBalancer(this.f$1, connectivityStateInfo);
    }
}
