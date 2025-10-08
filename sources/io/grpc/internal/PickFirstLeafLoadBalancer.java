package io.grpc.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import io.grpc.Attributes;
import io.grpc.ConnectivityState;
import io.grpc.ConnectivityStateInfo;
import io.grpc.EquivalentAddressGroup;
import io.grpc.LoadBalancer;
import io.grpc.Status;
import io.grpc.SynchronizationContext;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

final class PickFirstLeafLoadBalancer extends LoadBalancer {
    static final int CONNECTION_DELAY_INTERVAL_MS = 250;
    public static final String GRPC_EXPERIMENTAL_XDS_DUALSTACK_ENDPOINTS = "GRPC_EXPERIMENTAL_XDS_DUALSTACK_ENDPOINTS";
    /* access modifiers changed from: private */
    public static final Logger log = Logger.getLogger(PickFirstLeafLoadBalancer.class.getName());
    /* access modifiers changed from: private */
    public Index addressIndex;
    private ConnectivityState concludedState = ConnectivityState.IDLE;
    private final boolean enableHappyEyeballs = GrpcUtil.getFlag(GRPC_EXPERIMENTAL_XDS_DUALSTACK_ENDPOINTS, false);
    private boolean firstPass = true;
    /* access modifiers changed from: private */
    public final LoadBalancer.Helper helper;
    private int numTf = 0;
    private ConnectivityState rawConnectivityState = ConnectivityState.IDLE;
    /* access modifiers changed from: private */
    @Nullable
    public SynchronizationContext.ScheduledHandle scheduleConnectionTask;
    /* access modifiers changed from: private */
    public final Map<SocketAddress, SubchannelData> subchannels = new HashMap();

    PickFirstLeafLoadBalancer(LoadBalancer.Helper helper2) {
        this.helper = (LoadBalancer.Helper) Preconditions.checkNotNull(helper2, "helper");
    }

    public Status acceptResolvedAddresses(LoadBalancer.ResolvedAddresses resolvedAddresses) {
        if (this.rawConnectivityState == ConnectivityState.SHUTDOWN) {
            return Status.FAILED_PRECONDITION.withDescription("Already shut down");
        }
        List<EquivalentAddressGroup> servers = resolvedAddresses.getAddresses();
        if (servers.isEmpty()) {
            Status unavailableStatus = Status.UNAVAILABLE.withDescription("NameResolver returned no usable address. addrs=" + resolvedAddresses.getAddresses() + ", attrs=" + resolvedAddresses.getAttributes());
            handleNameResolutionError(unavailableStatus);
            return unavailableStatus;
        }
        for (EquivalentAddressGroup eag : servers) {
            if (eag == null) {
                Status unavailableStatus2 = Status.UNAVAILABLE.withDescription("NameResolver returned address list with null endpoint. addrs=" + resolvedAddresses.getAddresses() + ", attrs=" + resolvedAddresses.getAttributes());
                handleNameResolutionError(unavailableStatus2);
                return unavailableStatus2;
            }
        }
        this.firstPass = true;
        if (resolvedAddresses.getLoadBalancingPolicyConfig() instanceof PickFirstLeafLoadBalancerConfig) {
            PickFirstLeafLoadBalancerConfig config = (PickFirstLeafLoadBalancerConfig) resolvedAddresses.getLoadBalancingPolicyConfig();
            if (config.shuffleAddressList != null && config.shuffleAddressList.booleanValue()) {
                servers = new ArrayList<>(servers);
                Collections.shuffle(servers, config.randomSeed != null ? new Random(config.randomSeed.longValue()) : new Random());
            }
        }
        ImmutableList<EquivalentAddressGroup> newImmutableAddressGroups = ImmutableList.builder().addAll((Iterable) servers).build();
        if (this.addressIndex == null) {
            this.addressIndex = new Index(newImmutableAddressGroups);
        } else if (this.rawConnectivityState == ConnectivityState.READY) {
            SocketAddress previousAddress = this.addressIndex.getCurrentAddress();
            this.addressIndex.updateGroups(newImmutableAddressGroups);
            if (this.addressIndex.seekTo(previousAddress)) {
                return Status.OK;
            }
            this.addressIndex.reset();
        } else {
            this.addressIndex.updateGroups(newImmutableAddressGroups);
        }
        Set<SocketAddress> oldAddrs = new HashSet<>(this.subchannels.keySet());
        Set<SocketAddress> newAddrs = new HashSet<>();
        UnmodifiableIterator<EquivalentAddressGroup> it = newImmutableAddressGroups.iterator();
        while (it.hasNext()) {
            newAddrs.addAll(it.next().getAddresses());
        }
        for (SocketAddress oldAddr : oldAddrs) {
            if (!newAddrs.contains(oldAddr)) {
                this.subchannels.remove(oldAddr).getSubchannel().shutdown();
            }
        }
        if (oldAddrs.size() == 0 || this.rawConnectivityState == ConnectivityState.CONNECTING || this.rawConnectivityState == ConnectivityState.READY) {
            this.rawConnectivityState = ConnectivityState.CONNECTING;
            updateBalancingState(ConnectivityState.CONNECTING, new Picker(LoadBalancer.PickResult.withNoResult()));
            cancelScheduleTask();
            requestConnection();
        } else if (this.rawConnectivityState == ConnectivityState.IDLE) {
            updateBalancingState(ConnectivityState.IDLE, new RequestConnectionPicker(this));
        } else if (this.rawConnectivityState == ConnectivityState.TRANSIENT_FAILURE) {
            cancelScheduleTask();
            requestConnection();
        }
        return Status.OK;
    }

    public void handleNameResolutionError(Status error) {
        for (SubchannelData subchannelData : this.subchannels.values()) {
            subchannelData.getSubchannel().shutdown();
        }
        this.subchannels.clear();
        updateBalancingState(ConnectivityState.TRANSIENT_FAILURE, new Picker(LoadBalancer.PickResult.withError(error)));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: processSubchannelState */
    public void m1936lambda$createNewSubchannel$0$iogrpcinternalPickFirstLeafLoadBalancer(LoadBalancer.Subchannel subchannel, ConnectivityStateInfo stateInfo) {
        ConnectivityState newState = stateInfo.getState();
        SubchannelData subchannelData = this.subchannels.get(getAddress(subchannel));
        if (subchannelData != null && subchannelData.getSubchannel() == subchannel && newState != ConnectivityState.SHUTDOWN) {
            if (newState == ConnectivityState.IDLE) {
                this.helper.refreshNameResolution();
            }
            subchannelData.updateState(newState);
            if (this.rawConnectivityState == ConnectivityState.TRANSIENT_FAILURE || this.concludedState == ConnectivityState.TRANSIENT_FAILURE) {
                if (newState != ConnectivityState.CONNECTING) {
                    if (newState == ConnectivityState.IDLE) {
                        requestConnection();
                        return;
                    }
                } else {
                    return;
                }
            }
            switch (newState) {
                case IDLE:
                    this.addressIndex.reset();
                    this.rawConnectivityState = ConnectivityState.IDLE;
                    updateBalancingState(ConnectivityState.IDLE, new RequestConnectionPicker(this));
                    return;
                case CONNECTING:
                    this.rawConnectivityState = ConnectivityState.CONNECTING;
                    updateBalancingState(ConnectivityState.CONNECTING, new Picker(LoadBalancer.PickResult.withNoResult()));
                    return;
                case READY:
                    shutdownRemaining(subchannelData);
                    this.addressIndex.seekTo(getAddress(subchannel));
                    this.rawConnectivityState = ConnectivityState.READY;
                    updateHealthCheckedState(subchannelData);
                    return;
                case TRANSIENT_FAILURE:
                    if (this.addressIndex.isValid() && this.subchannels.get(this.addressIndex.getCurrentAddress()).getSubchannel() == subchannel && this.addressIndex.increment()) {
                        cancelScheduleTask();
                        requestConnection();
                    }
                    if (isPassComplete()) {
                        this.rawConnectivityState = ConnectivityState.TRANSIENT_FAILURE;
                        updateBalancingState(ConnectivityState.TRANSIENT_FAILURE, new Picker(LoadBalancer.PickResult.withError(stateInfo.getStatus())));
                        int i = this.numTf + 1;
                        this.numTf = i;
                        if (i >= this.addressIndex.size() || this.firstPass) {
                            this.firstPass = false;
                            this.numTf = 0;
                            this.helper.refreshNameResolution();
                            return;
                        }
                        return;
                    }
                    return;
                default:
                    throw new IllegalArgumentException("Unsupported state:" + newState);
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateHealthCheckedState(SubchannelData subchannelData) {
        if (subchannelData.state == ConnectivityState.READY) {
            if (subchannelData.getHealthState() == ConnectivityState.READY) {
                updateBalancingState(ConnectivityState.READY, new LoadBalancer.FixedResultPicker(LoadBalancer.PickResult.withSubchannel(subchannelData.subchannel)));
            } else if (subchannelData.getHealthState() == ConnectivityState.TRANSIENT_FAILURE) {
                updateBalancingState(ConnectivityState.TRANSIENT_FAILURE, new Picker(LoadBalancer.PickResult.withError(subchannelData.healthListener.healthStateInfo.getStatus())));
            } else if (this.concludedState != ConnectivityState.TRANSIENT_FAILURE) {
                updateBalancingState(subchannelData.getHealthState(), new Picker(LoadBalancer.PickResult.withNoResult()));
            }
        }
    }

    private void updateBalancingState(ConnectivityState state, LoadBalancer.SubchannelPicker picker) {
        if (state != this.concludedState || (state != ConnectivityState.IDLE && state != ConnectivityState.CONNECTING)) {
            this.concludedState = state;
            this.helper.updateBalancingState(state, picker);
        }
    }

    public void shutdown() {
        log.log(Level.FINE, "Shutting down, currently have {} subchannels created", Integer.valueOf(this.subchannels.size()));
        this.rawConnectivityState = ConnectivityState.SHUTDOWN;
        this.concludedState = ConnectivityState.SHUTDOWN;
        cancelScheduleTask();
        for (SubchannelData subchannelData : this.subchannels.values()) {
            subchannelData.getSubchannel().shutdown();
        }
        this.subchannels.clear();
    }

    private void shutdownRemaining(SubchannelData activeSubchannelData) {
        cancelScheduleTask();
        for (SubchannelData subchannelData : this.subchannels.values()) {
            if (!subchannelData.getSubchannel().equals(activeSubchannelData.subchannel)) {
                subchannelData.getSubchannel().shutdown();
            }
        }
        this.subchannels.clear();
        activeSubchannelData.updateState(ConnectivityState.READY);
        this.subchannels.put(getAddress(activeSubchannelData.subchannel), activeSubchannelData);
    }

    public void requestConnection() {
        LoadBalancer.Subchannel subchannel;
        if (this.addressIndex != null && this.addressIndex.isValid() && this.rawConnectivityState != ConnectivityState.SHUTDOWN) {
            SocketAddress currentAddress = this.addressIndex.getCurrentAddress();
            if (this.subchannels.containsKey(currentAddress)) {
                subchannel = this.subchannels.get(currentAddress).getSubchannel();
            } else {
                subchannel = createNewSubchannel(currentAddress);
            }
            switch (this.subchannels.get(currentAddress).getState()) {
                case IDLE:
                    subchannel.requestConnection();
                    this.subchannels.get(currentAddress).updateState(ConnectivityState.CONNECTING);
                    scheduleNextConnection();
                    return;
                case CONNECTING:
                    if (this.enableHappyEyeballs) {
                        scheduleNextConnection();
                        return;
                    } else {
                        subchannel.requestConnection();
                        return;
                    }
                case READY:
                    log.warning("Requesting a connection even though we have a READY subchannel");
                    return;
                case TRANSIENT_FAILURE:
                    this.addressIndex.increment();
                    requestConnection();
                    return;
                default:
                    return;
            }
        }
    }

    private void scheduleNextConnection() {
        if (!this.enableHappyEyeballs) {
            return;
        }
        if (this.scheduleConnectionTask == null || !this.scheduleConnectionTask.isPending()) {
            this.scheduleConnectionTask = this.helper.getSynchronizationContext().schedule(new Runnable() {
                public void run() {
                    SynchronizationContext.ScheduledHandle unused = PickFirstLeafLoadBalancer.this.scheduleConnectionTask = null;
                    if (PickFirstLeafLoadBalancer.this.addressIndex.increment()) {
                        PickFirstLeafLoadBalancer.this.requestConnection();
                    }
                }
            }, 250, TimeUnit.MILLISECONDS, this.helper.getScheduledExecutorService());
        }
    }

    private void cancelScheduleTask() {
        if (this.scheduleConnectionTask != null) {
            this.scheduleConnectionTask.cancel();
            this.scheduleConnectionTask = null;
        }
    }

    private LoadBalancer.Subchannel createNewSubchannel(SocketAddress addr) {
        HealthListener hcListener = new HealthListener();
        LoadBalancer.Subchannel subchannel = this.helper.createSubchannel(LoadBalancer.CreateSubchannelArgs.newBuilder().setAddresses((List<EquivalentAddressGroup>) Lists.newArrayList((E[]) new EquivalentAddressGroup[]{new EquivalentAddressGroup(addr)})).addOption(HEALTH_CONSUMER_LISTENER_ARG_KEY, hcListener).build());
        if (subchannel != null) {
            SubchannelData subchannelData = new SubchannelData(subchannel, ConnectivityState.IDLE, hcListener);
            SubchannelData unused = hcListener.subchannelData = subchannelData;
            this.subchannels.put(addr, subchannelData);
            if (subchannel.getAttributes().get(LoadBalancer.HAS_HEALTH_PRODUCER_LISTENER_KEY) == null) {
                ConnectivityStateInfo unused2 = hcListener.healthStateInfo = ConnectivityStateInfo.forNonError(ConnectivityState.READY);
            }
            subchannel.start(new PickFirstLeafLoadBalancer$$ExternalSyntheticLambda0(this, subchannel));
            return subchannel;
        }
        log.warning("Was not able to create subchannel for " + addr);
        throw new IllegalStateException("Can't create subchannel");
    }

    private boolean isPassComplete() {
        if (this.addressIndex == null || this.addressIndex.isValid() || this.subchannels.size() < this.addressIndex.size()) {
            return false;
        }
        for (SubchannelData sc : this.subchannels.values()) {
            if (!sc.isCompletedConnectivityAttempt()) {
                return false;
            }
        }
        return true;
    }

    private final class HealthListener implements LoadBalancer.SubchannelStateListener {
        /* access modifiers changed from: private */
        public ConnectivityStateInfo healthStateInfo;
        /* access modifiers changed from: private */
        public SubchannelData subchannelData;

        private HealthListener() {
            this.healthStateInfo = ConnectivityStateInfo.forNonError(ConnectivityState.IDLE);
        }

        public void onSubchannelState(ConnectivityStateInfo newState) {
            PickFirstLeafLoadBalancer.log.log(Level.FINE, "Received health status {0} for subchannel {1}", new Object[]{newState, this.subchannelData.subchannel});
            this.healthStateInfo = newState;
            if (PickFirstLeafLoadBalancer.this.addressIndex.isValid() && ((SubchannelData) PickFirstLeafLoadBalancer.this.subchannels.get(PickFirstLeafLoadBalancer.this.addressIndex.getCurrentAddress())).healthListener == this) {
                PickFirstLeafLoadBalancer.this.updateHealthCheckedState(this.subchannelData);
            }
        }
    }

    private SocketAddress getAddress(LoadBalancer.Subchannel subchannel) {
        return subchannel.getAddresses().getAddresses().get(0);
    }

    /* access modifiers changed from: package-private */
    public ConnectivityState getConcludedConnectivityState() {
        return this.concludedState;
    }

    private static final class Picker extends LoadBalancer.SubchannelPicker {
        private final LoadBalancer.PickResult result;

        Picker(LoadBalancer.PickResult result2) {
            this.result = (LoadBalancer.PickResult) Preconditions.checkNotNull(result2, "result");
        }

        public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args) {
            return this.result;
        }

        public String toString() {
            return MoreObjects.toStringHelper((Class<?>) Picker.class).add("result", (Object) this.result).toString();
        }
    }

    private final class RequestConnectionPicker extends LoadBalancer.SubchannelPicker {
        private final AtomicBoolean connectionRequested = new AtomicBoolean(false);
        private final PickFirstLeafLoadBalancer pickFirstLeafLoadBalancer;

        RequestConnectionPicker(PickFirstLeafLoadBalancer pickFirstLeafLoadBalancer2) {
            this.pickFirstLeafLoadBalancer = (PickFirstLeafLoadBalancer) Preconditions.checkNotNull(pickFirstLeafLoadBalancer2, "pickFirstLeafLoadBalancer");
        }

        public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args) {
            if (this.connectionRequested.compareAndSet(false, true)) {
                SynchronizationContext synchronizationContext = PickFirstLeafLoadBalancer.this.helper.getSynchronizationContext();
                PickFirstLeafLoadBalancer pickFirstLeafLoadBalancer2 = this.pickFirstLeafLoadBalancer;
                Objects.requireNonNull(pickFirstLeafLoadBalancer2);
                synchronizationContext.execute(new PickFirstLeafLoadBalancer$RequestConnectionPicker$$ExternalSyntheticLambda0(pickFirstLeafLoadBalancer2));
            }
            return LoadBalancer.PickResult.withNoResult();
        }
    }

    static final class Index {
        private List<EquivalentAddressGroup> addressGroups;
        private int addressIndex;
        private int groupIndex;

        public Index(List<EquivalentAddressGroup> groups) {
            this.addressGroups = groups != null ? groups : Collections.emptyList();
        }

        public boolean isValid() {
            return this.groupIndex < this.addressGroups.size();
        }

        public boolean isAtBeginning() {
            return this.groupIndex == 0 && this.addressIndex == 0;
        }

        public boolean increment() {
            if (!isValid()) {
                return false;
            }
            this.addressIndex++;
            if (this.addressIndex < this.addressGroups.get(this.groupIndex).getAddresses().size()) {
                return true;
            }
            this.groupIndex++;
            this.addressIndex = 0;
            if (this.groupIndex < this.addressGroups.size()) {
                return true;
            }
            return false;
        }

        public void reset() {
            this.groupIndex = 0;
            this.addressIndex = 0;
        }

        public SocketAddress getCurrentAddress() {
            if (isValid()) {
                return this.addressGroups.get(this.groupIndex).getAddresses().get(this.addressIndex);
            }
            throw new IllegalStateException("Index is past the end of the address group list");
        }

        public Attributes getCurrentEagAttributes() {
            if (isValid()) {
                return this.addressGroups.get(this.groupIndex).getAttributes();
            }
            throw new IllegalStateException("Index is off the end of the address group list");
        }

        public void updateGroups(ImmutableList<EquivalentAddressGroup> newGroups) {
            this.addressGroups = newGroups != null ? newGroups : Collections.emptyList();
            reset();
        }

        public boolean seekTo(SocketAddress needle) {
            int i = 0;
            while (i < this.addressGroups.size()) {
                int j = this.addressGroups.get(i).getAddresses().indexOf(needle);
                if (j == -1) {
                    i++;
                } else {
                    this.groupIndex = i;
                    this.addressIndex = j;
                    return true;
                }
            }
            return false;
        }

        public int size() {
            if (this.addressGroups != null) {
                return this.addressGroups.size();
            }
            return 0;
        }
    }

    private static final class SubchannelData {
        private boolean completedConnectivityAttempt = false;
        /* access modifiers changed from: private */
        public final HealthListener healthListener;
        /* access modifiers changed from: private */
        public ConnectivityState state;
        /* access modifiers changed from: private */
        public final LoadBalancer.Subchannel subchannel;

        public SubchannelData(LoadBalancer.Subchannel subchannel2, ConnectivityState state2, HealthListener subchannelHealthListener) {
            this.subchannel = subchannel2;
            this.state = state2;
            this.healthListener = subchannelHealthListener;
        }

        public LoadBalancer.Subchannel getSubchannel() {
            return this.subchannel;
        }

        public ConnectivityState getState() {
            return this.state;
        }

        public boolean isCompletedConnectivityAttempt() {
            return this.completedConnectivityAttempt;
        }

        /* access modifiers changed from: private */
        public void updateState(ConnectivityState newState) {
            this.state = newState;
            if (newState == ConnectivityState.READY || newState == ConnectivityState.TRANSIENT_FAILURE) {
                this.completedConnectivityAttempt = true;
            } else if (newState == ConnectivityState.IDLE) {
                this.completedConnectivityAttempt = false;
            }
        }

        /* access modifiers changed from: private */
        public ConnectivityState getHealthState() {
            return this.healthListener.healthStateInfo.getState();
        }
    }

    public static final class PickFirstLeafLoadBalancerConfig {
        @Nullable
        final Long randomSeed;
        @Nullable
        public final Boolean shuffleAddressList;

        public PickFirstLeafLoadBalancerConfig(@Nullable Boolean shuffleAddressList2) {
            this(shuffleAddressList2, (Long) null);
        }

        PickFirstLeafLoadBalancerConfig(@Nullable Boolean shuffleAddressList2, @Nullable Long randomSeed2) {
            this.shuffleAddressList = shuffleAddressList2;
            this.randomSeed = randomSeed2;
        }
    }
}
