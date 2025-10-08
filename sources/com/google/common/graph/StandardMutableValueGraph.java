package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.google.firebase.firestore.model.Values;
import java.util.Objects;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
final class StandardMutableValueGraph<N, V> extends StandardValueGraph<N, V> implements MutableValueGraph<N, V> {
    private final ElementOrder<N> incidentEdgeOrder;

    StandardMutableValueGraph(AbstractGraphBuilder<? super N> builder) {
        super(builder);
        this.incidentEdgeOrder = builder.incidentEdgeOrder.cast();
    }

    public ElementOrder<N> incidentEdgeOrder() {
        return this.incidentEdgeOrder;
    }

    public boolean addNode(N node) {
        Preconditions.checkNotNull(node, "node");
        if (containsNode(node)) {
            return false;
        }
        addNodeInternal(node);
        return true;
    }

    private GraphConnections<N, V> addNodeInternal(N node) {
        GraphConnections<N, V> connections = newConnections();
        Preconditions.checkState(this.nodeConnections.put(node, connections) == null);
        return connections;
    }

    @CheckForNull
    public V putEdgeValue(N nodeU, N nodeV, V value) {
        Preconditions.checkNotNull(nodeU, "nodeU");
        Preconditions.checkNotNull(nodeV, "nodeV");
        Preconditions.checkNotNull(value, Values.VECTOR_MAP_VECTORS_KEY);
        if (!allowsSelfLoops()) {
            Preconditions.checkArgument(!nodeU.equals(nodeV), "Cannot add self-loop edge on node %s, as self-loops are not allowed. To construct a graph that allows self-loops, call allowsSelfLoops(true) on the Builder.", (Object) nodeU);
        }
        GraphConnections<N, V> connectionsU = (GraphConnections) this.nodeConnections.get(nodeU);
        if (connectionsU == null) {
            connectionsU = addNodeInternal(nodeU);
        }
        V previousValue = connectionsU.addSuccessor(nodeV, value);
        GraphConnections<N, V> connectionsV = (GraphConnections) this.nodeConnections.get(nodeV);
        if (connectionsV == null) {
            connectionsV = addNodeInternal(nodeV);
        }
        connectionsV.addPredecessor(nodeU, value);
        if (previousValue == null) {
            long j = this.edgeCount + 1;
            this.edgeCount = j;
            Graphs.checkPositive(j);
        }
        return previousValue;
    }

    @CheckForNull
    public V putEdgeValue(EndpointPair<N> endpoints, V value) {
        validateEndpoints(endpoints);
        return putEdgeValue(endpoints.nodeU(), endpoints.nodeV(), value);
    }

    public boolean removeNode(N node) {
        Preconditions.checkNotNull(node, "node");
        GraphConnections<N, V> connections = (GraphConnections) this.nodeConnections.get(node);
        if (connections == null) {
            return false;
        }
        if (allowsSelfLoops() && connections.removeSuccessor(node) != null) {
            connections.removePredecessor(node);
            this.edgeCount--;
        }
        UnmodifiableIterator<N> it = ImmutableList.copyOf(connections.successors()).iterator();
        while (it.hasNext()) {
            N successor = it.next();
            ((GraphConnections) Objects.requireNonNull((GraphConnections) this.nodeConnections.getWithoutCaching(successor))).removePredecessor(node);
            Objects.requireNonNull(connections.removeSuccessor(successor));
            this.edgeCount--;
        }
        if (isDirected()) {
            UnmodifiableIterator<N> it2 = ImmutableList.copyOf(connections.predecessors()).iterator();
            while (it2.hasNext()) {
                N predecessor = it2.next();
                Preconditions.checkState(((GraphConnections) Objects.requireNonNull((GraphConnections) this.nodeConnections.getWithoutCaching(predecessor))).removeSuccessor(node) != null);
                connections.removePredecessor(predecessor);
                this.edgeCount--;
            }
        }
        this.nodeConnections.remove(node);
        Graphs.checkNonNegative(this.edgeCount);
        return true;
    }

    @CheckForNull
    public V removeEdge(N nodeU, N nodeV) {
        Preconditions.checkNotNull(nodeU, "nodeU");
        Preconditions.checkNotNull(nodeV, "nodeV");
        GraphConnections<N, V> connectionsU = (GraphConnections) this.nodeConnections.get(nodeU);
        GraphConnections<N, V> connectionsV = (GraphConnections) this.nodeConnections.get(nodeV);
        if (connectionsU == null || connectionsV == null) {
            return null;
        }
        V previousValue = connectionsU.removeSuccessor(nodeV);
        if (previousValue != null) {
            connectionsV.removePredecessor(nodeU);
            long j = this.edgeCount - 1;
            this.edgeCount = j;
            Graphs.checkNonNegative(j);
        }
        return previousValue;
    }

    @CheckForNull
    public V removeEdge(EndpointPair<N> endpoints) {
        validateEndpoints(endpoints);
        return removeEdge(endpoints.nodeU(), endpoints.nodeV());
    }

    private GraphConnections<N, V> newConnections() {
        if (isDirected()) {
            return DirectedGraphConnections.of(this.incidentEdgeOrder);
        }
        return UndirectedGraphConnections.of(this.incidentEdgeOrder);
    }
}
