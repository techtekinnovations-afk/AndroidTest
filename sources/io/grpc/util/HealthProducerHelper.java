package io.grpc.util;

import com.google.common.base.Preconditions;
import io.grpc.Attributes;
import io.grpc.ConnectivityStateInfo;
import io.grpc.LoadBalancer;

public final class HealthProducerHelper extends ForwardingLoadBalancerHelper {
    private final LoadBalancer.Helper delegate;

    public HealthProducerHelper(LoadBalancer.Helper helper) {
        this.delegate = (LoadBalancer.Helper) Preconditions.checkNotNull(helper, "helper");
    }

    public LoadBalancer.Subchannel createSubchannel(LoadBalancer.CreateSubchannelArgs args) {
        LoadBalancer.SubchannelStateListener healthConsumerListener = (LoadBalancer.SubchannelStateListener) args.getOption(LoadBalancer.HEALTH_CONSUMER_LISTENER_ARG_KEY);
        LoadBalancer.Subchannel delegateSubchannel = super.createSubchannel(args);
        if (!(healthConsumerListener != null && delegateSubchannel.getAttributes().get(LoadBalancer.HAS_HEALTH_PRODUCER_LISTENER_KEY) == null)) {
            return delegateSubchannel;
        }
        return new HealthProducerSubchannel(delegateSubchannel, healthConsumerListener);
    }

    /* access modifiers changed from: protected */
    public LoadBalancer.Helper delegate() {
        return this.delegate;
    }

    static final class HealthProducerSubchannel extends ForwardingSubchannel {
        private final LoadBalancer.Subchannel delegate;
        /* access modifiers changed from: private */
        public final LoadBalancer.SubchannelStateListener healthListener;

        HealthProducerSubchannel(LoadBalancer.Subchannel delegate2, LoadBalancer.SubchannelStateListener healthListener2) {
            this.delegate = (LoadBalancer.Subchannel) Preconditions.checkNotNull(delegate2, "delegate");
            this.healthListener = (LoadBalancer.SubchannelStateListener) Preconditions.checkNotNull(healthListener2, "healthListener");
        }

        public LoadBalancer.Subchannel delegate() {
            return this.delegate;
        }

        public void start(final LoadBalancer.SubchannelStateListener listener) {
            this.delegate.start(new LoadBalancer.SubchannelStateListener() {
                public void onSubchannelState(ConnectivityStateInfo newState) {
                    listener.onSubchannelState(newState);
                    HealthProducerSubchannel.this.healthListener.onSubchannelState(newState);
                }
            });
        }

        public Attributes getAttributes() {
            return super.getAttributes().toBuilder().set(LoadBalancer.HAS_HEALTH_PRODUCER_LISTENER_KEY, Boolean.TRUE).build();
        }
    }
}
