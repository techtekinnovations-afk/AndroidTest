package io.grpc.util;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import io.grpc.ConnectivityState;
import io.grpc.LoadBalancer;
import io.grpc.util.MultiChildLoadBalancer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinLoadBalancer extends MultiChildLoadBalancer {
    protected LoadBalancer.SubchannelPicker currentPicker = new EmptyPicker();
    private final AtomicInteger sequence = new AtomicInteger(new Random().nextInt());

    public RoundRobinLoadBalancer(LoadBalancer.Helper helper) {
        super(helper);
    }

    /* access modifiers changed from: protected */
    public LoadBalancer.SubchannelPicker getSubchannelPicker(Map<Object, LoadBalancer.SubchannelPicker> map) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x002f A[EDGE_INSN: B:15:0x002f->B:10:0x002f ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:5:0x0019  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateOverallBalancingState() {
        /*
            r6 = this;
            java.util.List r0 = r6.getReadyChildren()
            boolean r1 = r0.isEmpty()
            if (r1 == 0) goto L_0x004a
            r1 = 0
            java.util.Collection r2 = r6.getChildLbStates()
            java.util.Iterator r2 = r2.iterator()
        L_0x0013:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x002f
            java.lang.Object r3 = r2.next()
            io.grpc.util.MultiChildLoadBalancer$ChildLbState r3 = (io.grpc.util.MultiChildLoadBalancer.ChildLbState) r3
            io.grpc.ConnectivityState r4 = r3.getCurrentState()
            io.grpc.ConnectivityState r5 = io.grpc.ConnectivityState.CONNECTING
            if (r4 == r5) goto L_0x002d
            io.grpc.ConnectivityState r5 = io.grpc.ConnectivityState.IDLE
            if (r4 != r5) goto L_0x002c
            goto L_0x002d
        L_0x002c:
            goto L_0x0013
        L_0x002d:
            r1 = 1
        L_0x002f:
            if (r1 == 0) goto L_0x003c
            io.grpc.ConnectivityState r2 = io.grpc.ConnectivityState.CONNECTING
            io.grpc.util.RoundRobinLoadBalancer$EmptyPicker r3 = new io.grpc.util.RoundRobinLoadBalancer$EmptyPicker
            r3.<init>()
            r6.updateBalancingState(r2, r3)
            goto L_0x0049
        L_0x003c:
            io.grpc.ConnectivityState r2 = io.grpc.ConnectivityState.TRANSIENT_FAILURE
            java.util.Collection r3 = r6.getChildLbStates()
            io.grpc.LoadBalancer$SubchannelPicker r3 = r6.createReadyPicker(r3)
            r6.updateBalancingState(r2, r3)
        L_0x0049:
            goto L_0x0053
        L_0x004a:
            io.grpc.ConnectivityState r1 = io.grpc.ConnectivityState.READY
            io.grpc.LoadBalancer$SubchannelPicker r2 = r6.createReadyPicker(r0)
            r6.updateBalancingState(r1, r2)
        L_0x0053:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.grpc.util.RoundRobinLoadBalancer.updateOverallBalancingState():void");
    }

    private void updateBalancingState(ConnectivityState state, LoadBalancer.SubchannelPicker picker) {
        if (state != this.currentConnectivityState || !picker.equals(this.currentPicker)) {
            getHelper().updateBalancingState(state, picker);
            this.currentConnectivityState = state;
            this.currentPicker = picker;
        }
    }

    /* access modifiers changed from: protected */
    public LoadBalancer.SubchannelPicker createReadyPicker(Collection<MultiChildLoadBalancer.ChildLbState> children) {
        List<LoadBalancer.SubchannelPicker> pickerList = new ArrayList<>();
        for (MultiChildLoadBalancer.ChildLbState child : children) {
            pickerList.add(child.getCurrentPicker());
        }
        return new ReadyPicker(pickerList, this.sequence);
    }

    static class ReadyPicker extends LoadBalancer.SubchannelPicker {
        private final int hashCode;
        private final AtomicInteger index;
        private final List<LoadBalancer.SubchannelPicker> subchannelPickers;

        public ReadyPicker(List<LoadBalancer.SubchannelPicker> list, AtomicInteger index2) {
            Preconditions.checkArgument(!list.isEmpty(), "empty list");
            this.subchannelPickers = list;
            this.index = (AtomicInteger) Preconditions.checkNotNull(index2, "index");
            int sum = 0;
            for (LoadBalancer.SubchannelPicker picker : this.subchannelPickers) {
                sum += picker.hashCode();
            }
            this.hashCode = sum;
        }

        public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args) {
            return this.subchannelPickers.get(nextIndex()).pickSubchannel(args);
        }

        public String toString() {
            return MoreObjects.toStringHelper((Class<?>) ReadyPicker.class).add("subchannelPickers", (Object) this.subchannelPickers).toString();
        }

        private int nextIndex() {
            return (this.index.getAndIncrement() & Integer.MAX_VALUE) % this.subchannelPickers.size();
        }

        /* access modifiers changed from: package-private */
        public List<LoadBalancer.SubchannelPicker> getSubchannelPickers() {
            return this.subchannelPickers;
        }

        public int hashCode() {
            return this.hashCode;
        }

        /* JADX WARNING: type inference failed for: r6v0, types: [java.lang.Object] */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean equals(java.lang.Object r6) {
            /*
                r5 = this;
                boolean r0 = r6 instanceof io.grpc.util.RoundRobinLoadBalancer.ReadyPicker
                r1 = 0
                if (r0 != 0) goto L_0x0006
                return r1
            L_0x0006:
                r0 = r6
                io.grpc.util.RoundRobinLoadBalancer$ReadyPicker r0 = (io.grpc.util.RoundRobinLoadBalancer.ReadyPicker) r0
                r2 = 1
                if (r0 != r5) goto L_0x000d
                return r2
            L_0x000d:
                int r3 = r5.hashCode
                int r4 = r0.hashCode
                if (r3 != r4) goto L_0x0038
                java.util.concurrent.atomic.AtomicInteger r3 = r5.index
                java.util.concurrent.atomic.AtomicInteger r4 = r0.index
                if (r3 != r4) goto L_0x0038
                java.util.List<io.grpc.LoadBalancer$SubchannelPicker> r3 = r5.subchannelPickers
                int r3 = r3.size()
                java.util.List<io.grpc.LoadBalancer$SubchannelPicker> r4 = r0.subchannelPickers
                int r4 = r4.size()
                if (r3 != r4) goto L_0x0038
                java.util.HashSet r3 = new java.util.HashSet
                java.util.List<io.grpc.LoadBalancer$SubchannelPicker> r4 = r5.subchannelPickers
                r3.<init>(r4)
                java.util.List<io.grpc.LoadBalancer$SubchannelPicker> r4 = r0.subchannelPickers
                boolean r3 = r3.containsAll(r4)
                if (r3 == 0) goto L_0x0038
                r1 = r2
                goto L_0x0039
            L_0x0038:
            L_0x0039:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: io.grpc.util.RoundRobinLoadBalancer.ReadyPicker.equals(java.lang.Object):boolean");
        }
    }

    static final class EmptyPicker extends LoadBalancer.SubchannelPicker {
        EmptyPicker() {
        }

        public LoadBalancer.PickResult pickSubchannel(LoadBalancer.PickSubchannelArgs args) {
            return LoadBalancer.PickResult.withNoResult();
        }

        public int hashCode() {
            return getClass().hashCode();
        }

        public boolean equals(Object o) {
            return o instanceof EmptyPicker;
        }
    }
}
