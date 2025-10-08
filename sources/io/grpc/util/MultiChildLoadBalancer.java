package io.grpc.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import io.grpc.Attributes;
import io.grpc.ConnectivityState;
import io.grpc.EquivalentAddressGroup;
import io.grpc.LoadBalancer;
import io.grpc.LoadBalancerProvider;
import io.grpc.Status;
import io.grpc.internal.PickFirstLoadBalancerProvider;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public abstract class MultiChildLoadBalancer extends LoadBalancer {
    /* access modifiers changed from: private */
    public static final Logger logger = Logger.getLogger(MultiChildLoadBalancer.class.getName());
    /* access modifiers changed from: private */
    public final Map<Object, ChildLbState> childLbStates = new LinkedHashMap();
    protected ConnectivityState currentConnectivityState;
    /* access modifiers changed from: private */
    public final LoadBalancer.Helper helper;
    protected final LoadBalancerProvider pickFirstLbProvider = new PickFirstLoadBalancerProvider();
    protected boolean resolvingAddresses;

    /* access modifiers changed from: protected */
    public abstract LoadBalancer.SubchannelPicker getSubchannelPicker(Map<Object, LoadBalancer.SubchannelPicker> map);

    protected MultiChildLoadBalancer(LoadBalancer.Helper helper2) {
        this.helper = (LoadBalancer.Helper) Preconditions.checkNotNull(helper2, "helper");
        logger.log(Level.FINE, "Created");
    }

    /* access modifiers changed from: protected */
    public LoadBalancer.SubchannelPicker getInitialPicker() {
        return new LoadBalancer.FixedResultPicker(LoadBalancer.PickResult.withNoResult());
    }

    /* access modifiers changed from: protected */
    public LoadBalancer.SubchannelPicker getErrorPicker(Status error) {
        return new LoadBalancer.FixedResultPicker(LoadBalancer.PickResult.withError(error));
    }

    /* access modifiers changed from: protected */
    public ImmutableMap<Object, ChildLbState> getImmutableChildMap() {
        return ImmutableMap.copyOf(this.childLbStates);
    }

    /* access modifiers changed from: protected */
    public Collection<ChildLbState> getChildLbStates() {
        return this.childLbStates.values();
    }

    /* access modifiers changed from: protected */
    public ChildLbState getChildLbState(Object key) {
        if (key == null) {
            return null;
        }
        if (key instanceof EquivalentAddressGroup) {
            key = new Endpoint((EquivalentAddressGroup) key);
        }
        return this.childLbStates.get(key);
    }

    /* access modifiers changed from: protected */
    public ChildLbState getChildLbStateEag(EquivalentAddressGroup eag) {
        return getChildLbState(new Endpoint(eag));
    }

    /* access modifiers changed from: protected */
    public Map<Object, ChildLbState> createChildLbMap(LoadBalancer.ResolvedAddresses resolvedAddresses) {
        Map<Object, ChildLbState> childLbMap = new HashMap<>();
        for (EquivalentAddressGroup eag : resolvedAddresses.getAddresses()) {
            Endpoint endpoint = new Endpoint(eag);
            ChildLbState existingChildLbState = this.childLbStates.get(endpoint);
            if (existingChildLbState != null) {
                childLbMap.put(endpoint, existingChildLbState);
            } else {
                childLbMap.put(endpoint, createChildLbState(endpoint, (Object) null, getInitialPicker(), resolvedAddresses));
            }
        }
        return childLbMap;
    }

    /* access modifiers changed from: protected */
    public ChildLbState createChildLbState(Object key, Object policyConfig, LoadBalancer.SubchannelPicker initialPicker, LoadBalancer.ResolvedAddresses resolvedAddresses) {
        return new ChildLbState(this, key, this.pickFirstLbProvider, policyConfig, initialPicker);
    }

    /* JADX INFO: finally extract failed */
    public Status acceptResolvedAddresses(LoadBalancer.ResolvedAddresses resolvedAddresses) {
        try {
            this.resolvingAddresses = true;
            AcceptResolvedAddressRetVal acceptRetVal = acceptResolvedAddressesInternal(resolvedAddresses);
            if (!acceptRetVal.status.isOk()) {
                Status status = acceptRetVal.status;
                this.resolvingAddresses = false;
                return status;
            }
            updateOverallBalancingState();
            shutdownRemoved(acceptRetVal.removedChildren);
            Status status2 = acceptRetVal.status;
            this.resolvingAddresses = false;
            return status2;
        } catch (Throwable th) {
            this.resolvingAddresses = false;
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public LoadBalancer.ResolvedAddresses getChildAddresses(Object key, LoadBalancer.ResolvedAddresses resolvedAddresses, Object childConfig) {
        Endpoint endpointKey;
        if (key instanceof EquivalentAddressGroup) {
            endpointKey = new Endpoint((EquivalentAddressGroup) key);
        } else {
            Preconditions.checkArgument(key instanceof Endpoint, "key is wrong type");
            endpointKey = (Endpoint) key;
        }
        EquivalentAddressGroup eagToUse = null;
        Iterator<EquivalentAddressGroup> it = resolvedAddresses.getAddresses().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            EquivalentAddressGroup currEag = it.next();
            if (endpointKey.equals(new Endpoint(currEag))) {
                eagToUse = currEag;
                break;
            }
        }
        Preconditions.checkNotNull(eagToUse, key + " no longer present in load balancer children");
        return resolvedAddresses.toBuilder().setAddresses(Collections.singletonList(eagToUse)).setAttributes(Attributes.newBuilder().set(IS_PETIOLE_POLICY, true).build()).setLoadBalancingPolicyConfig(childConfig).build();
    }

    /* access modifiers changed from: protected */
    public AcceptResolvedAddressRetVal acceptResolvedAddressesInternal(LoadBalancer.ResolvedAddresses resolvedAddresses) {
        logger.log(Level.FINE, "Received resolution result: {0}", resolvedAddresses);
        Map<Object, ChildLbState> newChildren = createChildLbMap(resolvedAddresses);
        if (newChildren.isEmpty()) {
            Status unavailableStatus = Status.UNAVAILABLE.withDescription("NameResolver returned no usable address. " + resolvedAddresses);
            handleNameResolutionError(unavailableStatus);
            return new AcceptResolvedAddressRetVal(unavailableStatus, (List<ChildLbState>) null);
        }
        for (Map.Entry<Object, ChildLbState> entry : newChildren.entrySet()) {
            Object key = entry.getKey();
            LoadBalancerProvider childPolicyProvider = entry.getValue().getPolicyProvider();
            Object childConfig = entry.getValue().getConfig();
            if (!this.childLbStates.containsKey(key)) {
                this.childLbStates.put(key, entry.getValue());
            } else {
                ChildLbState existingChildLbState = this.childLbStates.get(key);
                if (existingChildLbState.isDeactivated() && reactivateChildOnReuse()) {
                    existingChildLbState.reactivate(childPolicyProvider);
                }
            }
            ChildLbState childLbState = this.childLbStates.get(key);
            LoadBalancer.ResolvedAddresses childAddresses = getChildAddresses(key, resolvedAddresses, childConfig);
            this.childLbStates.get(key).setResolvedAddresses(childAddresses);
            if (!childLbState.deactivated) {
                childLbState.lb.handleResolvedAddresses(childAddresses);
            }
        }
        List<ChildLbState> removedChildren = new ArrayList<>();
        UnmodifiableIterator<Object> it = ImmutableList.copyOf(this.childLbStates.keySet()).iterator();
        while (it.hasNext()) {
            Object key2 = it.next();
            if (!newChildren.containsKey(key2)) {
                ChildLbState childLbState2 = this.childLbStates.get(key2);
                childLbState2.deactivate();
                removedChildren.add(childLbState2);
            }
        }
        return new AcceptResolvedAddressRetVal(Status.OK, removedChildren);
    }

    /* access modifiers changed from: protected */
    public void shutdownRemoved(List<ChildLbState> removedChildren) {
        for (ChildLbState childLbState : removedChildren) {
            childLbState.shutdown();
        }
    }

    public void handleNameResolutionError(Status error) {
        if (this.currentConnectivityState != ConnectivityState.READY) {
            this.helper.updateBalancingState(ConnectivityState.TRANSIENT_FAILURE, getErrorPicker(error));
        }
    }

    /* access modifiers changed from: protected */
    public void handleNameResolutionError(ChildLbState child, Status error) {
        child.lb.handleNameResolutionError(error);
    }

    /* access modifiers changed from: protected */
    public boolean reconnectOnIdle() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean reactivateChildOnReuse() {
        return true;
    }

    public void shutdown() {
        logger.log(Level.FINE, "Shutdown");
        for (ChildLbState state : this.childLbStates.values()) {
            state.shutdown();
        }
        this.childLbStates.clear();
    }

    /* access modifiers changed from: protected */
    public void updateOverallBalancingState() {
        ConnectivityState overallState = null;
        Map<Object, LoadBalancer.SubchannelPicker> childPickers = new HashMap<>();
        for (ChildLbState childLbState : getChildLbStates()) {
            if (!childLbState.deactivated) {
                childPickers.put(childLbState.key, childLbState.currentPicker);
                overallState = aggregateState(overallState, childLbState.currentState);
            }
        }
        if (overallState != null) {
            this.helper.updateBalancingState(overallState, getSubchannelPicker(childPickers));
            this.currentConnectivityState = overallState;
        }
    }

    @Nullable
    protected static ConnectivityState aggregateState(@Nullable ConnectivityState overallState, ConnectivityState childState) {
        if (overallState == null) {
            return childState;
        }
        if (overallState == ConnectivityState.READY || childState == ConnectivityState.READY) {
            return ConnectivityState.READY;
        }
        if (overallState == ConnectivityState.CONNECTING || childState == ConnectivityState.CONNECTING) {
            return ConnectivityState.CONNECTING;
        }
        if (overallState == ConnectivityState.IDLE || childState == ConnectivityState.IDLE) {
            return ConnectivityState.IDLE;
        }
        return overallState;
    }

    /* access modifiers changed from: protected */
    public LoadBalancer.Helper getHelper() {
        return this.helper;
    }

    /* access modifiers changed from: protected */
    public void removeChild(Object key) {
        this.childLbStates.remove(key);
    }

    /* access modifiers changed from: protected */
    public List<ChildLbState> getReadyChildren() {
        List<ChildLbState> activeChildren = new ArrayList<>();
        for (ChildLbState child : getChildLbStates()) {
            if (!child.isDeactivated() && child.getCurrentState() == ConnectivityState.READY) {
                activeChildren.add(child);
            }
        }
        return activeChildren;
    }

    public class ChildLbState {
        private final Object config;
        /* access modifiers changed from: private */
        public LoadBalancer.SubchannelPicker currentPicker;
        /* access modifiers changed from: private */
        public ConnectivityState currentState;
        /* access modifiers changed from: private */
        public boolean deactivated;
        /* access modifiers changed from: private */
        public final Object key;
        /* access modifiers changed from: private */
        public final GracefulSwitchLoadBalancer lb;
        private final LoadBalancerProvider policyProvider;
        private LoadBalancer.ResolvedAddresses resolvedAddresses;

        public ChildLbState(MultiChildLoadBalancer this$02, Object key2, LoadBalancerProvider policyProvider2, Object childConfig, LoadBalancer.SubchannelPicker initialPicker) {
            this(key2, policyProvider2, childConfig, initialPicker, (LoadBalancer.ResolvedAddresses) null, false);
        }

        public ChildLbState(Object key2, LoadBalancerProvider policyProvider2, Object childConfig, LoadBalancer.SubchannelPicker initialPicker, LoadBalancer.ResolvedAddresses resolvedAddrs, boolean deactivated2) {
            this.key = key2;
            this.policyProvider = policyProvider2;
            this.deactivated = deactivated2;
            this.currentPicker = initialPicker;
            this.config = childConfig;
            this.lb = new GracefulSwitchLoadBalancer(new ChildLbStateHelper());
            this.currentState = deactivated2 ? ConnectivityState.IDLE : ConnectivityState.CONNECTING;
            this.resolvedAddresses = resolvedAddrs;
            if (!deactivated2) {
                this.lb.switchTo(policyProvider2);
            }
        }

        public String toString() {
            return "Address = " + this.key + ", state = " + this.currentState + ", picker type: " + this.currentPicker.getClass() + ", lb: " + this.lb.delegate().getClass() + (this.deactivated ? ", deactivated" : "");
        }

        public Object getKey() {
            return this.key;
        }

        /* access modifiers changed from: package-private */
        public Object getConfig() {
            return this.config;
        }

        /* access modifiers changed from: protected */
        public GracefulSwitchLoadBalancer getLb() {
            return this.lb;
        }

        public LoadBalancerProvider getPolicyProvider() {
            return this.policyProvider;
        }

        /* access modifiers changed from: protected */
        public LoadBalancer.Subchannel getSubchannels(LoadBalancer.PickSubchannelArgs args) {
            if (getCurrentPicker() == null) {
                return null;
            }
            return getCurrentPicker().pickSubchannel(args).getSubchannel();
        }

        public ConnectivityState getCurrentState() {
            return this.currentState;
        }

        public LoadBalancer.SubchannelPicker getCurrentPicker() {
            return this.currentPicker;
        }

        public EquivalentAddressGroup getEag() {
            if (this.resolvedAddresses == null || this.resolvedAddresses.getAddresses().isEmpty()) {
                return null;
            }
            return this.resolvedAddresses.getAddresses().get(0);
        }

        public boolean isDeactivated() {
            return this.deactivated;
        }

        /* access modifiers changed from: protected */
        public void setDeactivated() {
            this.deactivated = true;
        }

        /* access modifiers changed from: protected */
        public void markReactivated() {
            this.deactivated = false;
        }

        /* access modifiers changed from: protected */
        public void setResolvedAddresses(LoadBalancer.ResolvedAddresses newAddresses) {
            Preconditions.checkNotNull(newAddresses, "Missing address list for child");
            this.resolvedAddresses = newAddresses;
        }

        /* access modifiers changed from: protected */
        public void deactivate() {
            if (!this.deactivated) {
                MultiChildLoadBalancer.this.childLbStates.remove(this.key);
                this.deactivated = true;
                MultiChildLoadBalancer.logger.log(Level.FINE, "Child balancer {0} deactivated", this.key);
            }
        }

        /* access modifiers changed from: protected */
        public void reactivate(LoadBalancerProvider policyProvider2) {
            this.deactivated = false;
        }

        /* access modifiers changed from: protected */
        public void shutdown() {
            this.lb.shutdown();
            this.currentState = ConnectivityState.SHUTDOWN;
            MultiChildLoadBalancer.logger.log(Level.FINE, "Child balancer {0} deleted", this.key);
        }

        public LoadBalancer.ResolvedAddresses getResolvedAddresses() {
            return this.resolvedAddresses;
        }

        private final class ChildLbStateHelper extends ForwardingLoadBalancerHelper {
            private ChildLbStateHelper() {
            }

            public void updateBalancingState(ConnectivityState newState, LoadBalancer.SubchannelPicker newPicker) {
                if (MultiChildLoadBalancer.this.childLbStates.containsKey(ChildLbState.this.key)) {
                    ConnectivityState unused = ChildLbState.this.currentState = newState;
                    LoadBalancer.SubchannelPicker unused2 = ChildLbState.this.currentPicker = newPicker;
                    if (!ChildLbState.this.deactivated && !MultiChildLoadBalancer.this.resolvingAddresses) {
                        if (newState == ConnectivityState.IDLE && MultiChildLoadBalancer.this.reconnectOnIdle()) {
                            ChildLbState.this.lb.requestConnection();
                        }
                        MultiChildLoadBalancer.this.updateOverallBalancingState();
                    }
                }
            }

            /* access modifiers changed from: protected */
            public LoadBalancer.Helper delegate() {
                return MultiChildLoadBalancer.this.helper;
            }
        }
    }

    protected static class Endpoint {
        final String[] addrs;
        final int hashCode;

        public Endpoint(EquivalentAddressGroup eag) {
            Preconditions.checkNotNull(eag, "eag");
            this.addrs = new String[eag.getAddresses().size()];
            int i = 0;
            for (SocketAddress address : eag.getAddresses()) {
                this.addrs[i] = address.toString();
                i++;
            }
            Arrays.sort(this.addrs);
            this.hashCode = Arrays.hashCode(this.addrs);
        }

        public int hashCode() {
            return this.hashCode;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || !(other instanceof Endpoint)) {
                return false;
            }
            Endpoint o = (Endpoint) other;
            if (o.hashCode == this.hashCode && o.addrs.length == this.addrs.length) {
                return Arrays.equals(o.addrs, this.addrs);
            }
            return false;
        }

        public String toString() {
            return Arrays.toString(this.addrs);
        }
    }

    protected static class AcceptResolvedAddressRetVal {
        public final List<ChildLbState> removedChildren;
        public final Status status;

        public AcceptResolvedAddressRetVal(Status status2, List<ChildLbState> removedChildren2) {
            this.status = status2;
            this.removedChildren = removedChildren2;
        }
    }
}
