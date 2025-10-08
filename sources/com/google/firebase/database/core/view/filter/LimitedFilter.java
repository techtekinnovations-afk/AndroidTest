package com.google.firebase.database.core.view.filter;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.view.Change;
import com.google.firebase.database.core.view.QueryParams;
import com.google.firebase.database.core.view.filter.NodeFilter;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.PriorityUtilities;
import java.util.Iterator;

public class LimitedFilter implements NodeFilter {
    private final Index index;
    private final int limit;
    private final RangedFilter rangedFilter;
    private final boolean reverse;

    public LimitedFilter(QueryParams params) {
        this.rangedFilter = new RangedFilter(params);
        this.index = params.getIndex();
        this.limit = params.getLimit();
        this.reverse = !params.isViewFromLeft();
    }

    public IndexedNode updateChild(IndexedNode snap, ChildKey key, Node newChild, Path affectedPath, NodeFilter.CompleteChildSource source, ChildChangeAccumulator optChangeAccumulator) {
        Node newChild2;
        if (!this.rangedFilter.matches(new NamedNode(key, newChild))) {
            newChild2 = EmptyNode.Empty();
        } else {
            newChild2 = newChild;
        }
        if (snap.getNode().getImmediateChild(key).equals(newChild2)) {
            return snap;
        }
        if (snap.getNode().getChildCount() < this.limit) {
            Path affectedPath2 = affectedPath;
            NodeFilter.CompleteChildSource source2 = source;
            ChildChangeAccumulator optChangeAccumulator2 = optChangeAccumulator;
            IndexedNode snap2 = this.rangedFilter.getIndexedFilter().updateChild(snap, key, newChild2, affectedPath2, source2, optChangeAccumulator2);
            Path path = affectedPath2;
            NodeFilter.CompleteChildSource completeChildSource = source2;
            ChildChangeAccumulator childChangeAccumulator = optChangeAccumulator2;
            return snap2;
        }
        return fullLimitUpdateChild(snap, key, newChild2, source, optChangeAccumulator);
    }

    private IndexedNode fullLimitUpdateChild(IndexedNode oldIndexed, ChildKey childKey, Node childSnap, NodeFilter.CompleteChildSource source, ChildChangeAccumulator optChangeAccumulator) {
        IndexedNode indexedNode = oldIndexed;
        ChildKey childKey2 = childKey;
        Node node = childSnap;
        NodeFilter.CompleteChildSource completeChildSource = source;
        ChildChangeAccumulator childChangeAccumulator = optChangeAccumulator;
        Utilities.hardAssert(indexedNode.getNode().getChildCount() == this.limit);
        NamedNode newChildNamedNode = new NamedNode(childKey2, node);
        NamedNode windowBoundary = this.reverse ? indexedNode.getFirstChild() : indexedNode.getLastChild();
        boolean inRange = this.rangedFilter.matches(newChildNamedNode);
        if (indexedNode.getNode().hasChild(childKey2)) {
            Node oldChildSnap = indexedNode.getNode().getImmediateChild(childKey2);
            NamedNode nextChild = completeChildSource.getChildAfterChild(this.index, windowBoundary, this.reverse);
            while (nextChild != null && (nextChild.getName().equals(childKey2) || indexedNode.getNode().hasChild(nextChild.getName()))) {
                nextChild = completeChildSource.getChildAfterChild(this.index, nextChild, this.reverse);
            }
            if (inRange && !node.isEmpty() && (nextChild == null ? 1 : this.index.compare(nextChild, newChildNamedNode, this.reverse)) >= 0) {
                if (childChangeAccumulator != null) {
                    childChangeAccumulator.trackChildChange(Change.childChangedChange(childKey2, node, oldChildSnap));
                }
                return oldIndexed.updateChild(childKey, childSnap);
            }
            if (childChangeAccumulator != null) {
                childChangeAccumulator.trackChildChange(Change.childRemovedChange(childKey2, oldChildSnap));
            }
            IndexedNode newIndexed = indexedNode.updateChild(childKey2, EmptyNode.Empty());
            if (!(nextChild != null && this.rangedFilter.matches(nextChild))) {
                return newIndexed;
            }
            if (childChangeAccumulator != null) {
                childChangeAccumulator.trackChildChange(Change.childAddedChange(nextChild.getName(), nextChild.getNode()));
            }
            return newIndexed.updateChild(nextChild.getName(), nextChild.getNode());
        } else if (node.isEmpty() || !inRange || this.index.compare(windowBoundary, newChildNamedNode, this.reverse) < 0) {
            return oldIndexed;
        } else {
            if (childChangeAccumulator != null) {
                childChangeAccumulator.trackChildChange(Change.childRemovedChange(windowBoundary.getName(), windowBoundary.getNode()));
                childChangeAccumulator.trackChildChange(Change.childAddedChange(childKey, childSnap));
            }
            return oldIndexed.updateChild(childKey, childSnap).updateChild(windowBoundary.getName(), EmptyNode.Empty());
        }
    }

    public IndexedNode updateFullNode(IndexedNode oldSnap, IndexedNode newSnap, ChildChangeAccumulator optChangeAccumulator) {
        IndexedNode filtered;
        int sign;
        NamedNode endPost;
        NamedNode startPost;
        Iterator<NamedNode> iterator;
        if (newSnap.getNode().isLeafNode() || newSnap.getNode().isEmpty()) {
            filtered = IndexedNode.from(EmptyNode.Empty(), this.index);
        } else {
            filtered = newSnap.updatePriority(PriorityUtilities.NullPriority());
            if (this.reverse) {
                iterator = newSnap.reverseIterator();
                startPost = this.rangedFilter.getEndPost();
                endPost = this.rangedFilter.getStartPost();
                sign = -1;
            } else {
                iterator = newSnap.iterator();
                startPost = this.rangedFilter.getStartPost();
                endPost = this.rangedFilter.getEndPost();
                sign = 1;
            }
            int count = 0;
            boolean foundStartPost = false;
            while (iterator.hasNext()) {
                NamedNode next = iterator.next();
                if (!foundStartPost && this.index.compare(startPost, next) * sign <= 0) {
                    foundStartPost = true;
                }
                if (foundStartPost && count < this.limit && this.index.compare(next, endPost) * sign <= 0) {
                    count++;
                } else {
                    filtered = filtered.updateChild(next.getName(), EmptyNode.Empty());
                }
            }
        }
        return this.rangedFilter.getIndexedFilter().updateFullNode(oldSnap, filtered, optChangeAccumulator);
    }

    public IndexedNode updatePriority(IndexedNode oldSnap, Node newPriority) {
        return oldSnap;
    }

    public NodeFilter getIndexedFilter() {
        return this.rangedFilter.getIndexedFilter();
    }

    public Index getIndex() {
        return this.index;
    }

    public boolean filtersNodes() {
        return true;
    }
}
