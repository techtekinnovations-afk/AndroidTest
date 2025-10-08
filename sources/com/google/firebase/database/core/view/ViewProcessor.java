package com.google.firebase.database.core.view;

import com.google.firebase.database.core.CompoundWrite;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.WriteTreeRef;
import com.google.firebase.database.core.operation.AckUserWrite;
import com.google.firebase.database.core.operation.Merge;
import com.google.firebase.database.core.operation.Operation;
import com.google.firebase.database.core.operation.Overwrite;
import com.google.firebase.database.core.utilities.ImmutableTree;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.view.filter.ChildChangeAccumulator;
import com.google.firebase.database.core.view.filter.NodeFilter;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.ChildrenNode;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.KeyIndex;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ViewProcessor {
    private static NodeFilter.CompleteChildSource NO_COMPLETE_SOURCE = new NodeFilter.CompleteChildSource() {
        public Node getCompleteChild(ChildKey childKey) {
            return null;
        }

        public NamedNode getChildAfterChild(Index index, NamedNode child, boolean reverse) {
            return null;
        }
    };
    private final NodeFilter filter;

    public ViewProcessor(NodeFilter filter2) {
        this.filter = filter2;
    }

    public static class ProcessorResult {
        public final List<Change> changes;
        public final ViewCache viewCache;

        public ProcessorResult(ViewCache viewCache2, List<Change> changes2) {
            this.viewCache = viewCache2;
            this.changes = changes2;
        }
    }

    public ProcessorResult applyOperation(ViewCache oldViewCache, Operation operation, WriteTreeRef writesCache, Node optCompleteCache) {
        ViewCache oldViewCache2;
        ViewCache oldViewCache3;
        ChildChangeAccumulator accumulator = new ChildChangeAccumulator();
        boolean filterServerNode = false;
        switch (operation.getType()) {
            case Overwrite:
                WriteTreeRef writesCache2 = writesCache;
                Node optCompleteCache2 = optCompleteCache;
                ViewCache oldViewCache4 = oldViewCache;
                Overwrite overwrite = (Overwrite) operation;
                if (!overwrite.getSource().isFromUser()) {
                    Utilities.hardAssert(overwrite.getSource().isFromServer());
                    if (overwrite.getSource().isTagged() || (oldViewCache4.getServerCache().isFiltered() && !overwrite.getPath().isEmpty())) {
                        filterServerNode = true;
                    }
                    oldViewCache3 = oldViewCache4;
                    ChildChangeAccumulator accumulator2 = accumulator;
                    ViewCache newViewCache = applyServerOverwrite(oldViewCache3, overwrite.getPath(), overwrite.getSnapshot(), writesCache2, optCompleteCache2, filterServerNode, accumulator2);
                    accumulator = accumulator2;
                    oldViewCache2 = newViewCache;
                    break;
                } else {
                    oldViewCache3 = oldViewCache4;
                    oldViewCache2 = applyUserOverwrite(oldViewCache3, overwrite.getPath(), overwrite.getSnapshot(), writesCache2, optCompleteCache2, accumulator);
                    break;
                }
            case Merge:
                WriteTreeRef writesCache3 = writesCache;
                Node optCompleteCache3 = optCompleteCache;
                ViewCache oldViewCache5 = oldViewCache;
                Merge merge = (Merge) operation;
                if (!merge.getSource().isFromUser()) {
                    Utilities.hardAssert(merge.getSource().isFromServer());
                    if (merge.getSource().isTagged() || oldViewCache5.getServerCache().isFiltered()) {
                        filterServerNode = true;
                    }
                    oldViewCache3 = oldViewCache5;
                    ChildChangeAccumulator accumulator3 = accumulator;
                    ViewCache newViewCache2 = applyServerMerge(oldViewCache3, merge.getPath(), merge.getChildren(), writesCache3, optCompleteCache3, filterServerNode, accumulator3);
                    accumulator = accumulator3;
                    oldViewCache2 = newViewCache2;
                    break;
                } else {
                    oldViewCache3 = oldViewCache5;
                    oldViewCache2 = applyUserMerge(oldViewCache3, merge.getPath(), merge.getChildren(), writesCache3, optCompleteCache3, accumulator);
                    break;
                }
            case AckUserWrite:
                oldViewCache3 = oldViewCache;
                WriteTreeRef writesCache4 = writesCache;
                Node optCompleteCache4 = optCompleteCache;
                AckUserWrite ackUserWrite = (AckUserWrite) operation;
                if (ackUserWrite.isRevert()) {
                    WriteTreeRef writesCache5 = writesCache4;
                    Node optCompleteCache5 = optCompleteCache4;
                    ViewCache newViewCache3 = revertUserWrite(oldViewCache3, ackUserWrite.getPath(), writesCache5, optCompleteCache5, accumulator);
                    ViewCache viewCache = oldViewCache3;
                    Node node = optCompleteCache5;
                    WriteTreeRef writeTreeRef = writesCache5;
                    oldViewCache2 = newViewCache3;
                    break;
                } else {
                    oldViewCache2 = ackUserWrite(oldViewCache3, ackUserWrite.getPath(), ackUserWrite.getAffectedTree(), writesCache4, optCompleteCache4, accumulator);
                    break;
                }
            case ListenComplete:
                oldViewCache3 = oldViewCache;
                WriteTreeRef writesCache6 = writesCache;
                Node optCompleteCache6 = optCompleteCache;
                oldViewCache2 = listenComplete(oldViewCache3, operation.getPath(), writesCache6, optCompleteCache6, accumulator);
                Node node2 = optCompleteCache6;
                WriteTreeRef writeTreeRef2 = writesCache6;
                break;
            default:
                ViewCache viewCache2 = oldViewCache;
                WriteTreeRef writeTreeRef3 = writesCache;
                Node node3 = optCompleteCache;
                throw new AssertionError("Unknown operation: " + operation.getType());
        }
        List<Change> changes = new ArrayList<>(accumulator.getChanges());
        maybeAddValueEvent(oldViewCache3, oldViewCache2, changes);
        return new ProcessorResult(oldViewCache2, changes);
    }

    private void maybeAddValueEvent(ViewCache oldViewCache, ViewCache newViewCache, List<Change> accumulator) {
        CacheNode eventSnap = newViewCache.getEventCache();
        if (eventSnap.isFullyInitialized()) {
            boolean isLeafOrEmpty = eventSnap.getNode().isLeafNode() || eventSnap.getNode().isEmpty();
            if (!accumulator.isEmpty() || !oldViewCache.getEventCache().isFullyInitialized() || ((isLeafOrEmpty && !eventSnap.getNode().equals(oldViewCache.getCompleteEventSnap())) || !eventSnap.getNode().getPriority().equals(oldViewCache.getCompleteEventSnap().getPriority()))) {
                accumulator.add(Change.valueChange(eventSnap.getIndexedNode()));
            }
        }
    }

    private ViewCache generateEventCacheAfterServerEvent(ViewCache viewCache, Path changePath, WriteTreeRef writesCache, NodeFilter.CompleteChildSource source, ChildChangeAccumulator accumulator) {
        IndexedNode newEventCache;
        Node newEventChild;
        Node newEventChild2;
        Node serverCache;
        ViewCache viewCache2 = viewCache;
        Path path = changePath;
        WriteTreeRef writeTreeRef = writesCache;
        CacheNode oldEventSnap = viewCache2.getEventCache();
        if (writeTreeRef.shadowingWrite(path) != null) {
            return viewCache2;
        }
        boolean z = false;
        if (path.isEmpty()) {
            Utilities.hardAssert(viewCache2.getServerCache().isFullyInitialized(), "If change path is empty, we must have complete server data");
            if (viewCache2.getServerCache().isFiltered()) {
                Node serverCache2 = viewCache2.getCompleteServerSnap();
                serverCache = writeTreeRef.calcCompleteEventChildren(serverCache2 instanceof ChildrenNode ? serverCache2 : EmptyNode.Empty());
            } else {
                serverCache = writeTreeRef.calcCompleteEventCache(viewCache2.getCompleteServerSnap());
            }
            newEventCache = this.filter.updateFullNode(viewCache2.getEventCache().getIndexedNode(), IndexedNode.from(serverCache, this.filter.getIndex()), accumulator);
        } else {
            ChildChangeAccumulator childChangeAccumulator = accumulator;
            ChildKey childKey = path.getFront();
            if (childKey.isPriorityChildName()) {
                Utilities.hardAssert(path.size() == 1, "Can't have a priority with additional path components");
                Node updatedPriority = writeTreeRef.calcEventCacheAfterServerOverwrite(path, oldEventSnap.getNode(), viewCache2.getServerCache().getNode());
                if (updatedPriority != null) {
                    newEventCache = this.filter.updatePriority(oldEventSnap.getIndexedNode(), updatedPriority);
                } else {
                    newEventCache = oldEventSnap.getIndexedNode();
                }
            } else {
                Path childChangePath = path.popFront();
                if (oldEventSnap.isCompleteForChild(childKey)) {
                    Node eventChildUpdate = writeTreeRef.calcEventCacheAfterServerOverwrite(path, oldEventSnap.getNode(), viewCache2.getServerCache().getNode());
                    if (eventChildUpdate != null) {
                        newEventChild2 = oldEventSnap.getNode().getImmediateChild(childKey).updateChild(childChangePath, eventChildUpdate);
                    } else {
                        newEventChild2 = oldEventSnap.getNode().getImmediateChild(childKey);
                    }
                    newEventChild = newEventChild2;
                } else {
                    newEventChild = writeTreeRef.calcCompleteChild(childKey, viewCache2.getServerCache());
                }
                if (newEventChild != null) {
                    newEventCache = this.filter.updateChild(oldEventSnap.getIndexedNode(), childKey, newEventChild, childChangePath, source, accumulator);
                } else {
                    newEventCache = oldEventSnap.getIndexedNode();
                }
            }
        }
        if (oldEventSnap.isFullyInitialized() || path.isEmpty()) {
            z = true;
        }
        return viewCache2.updateEventSnap(newEventCache, z, this.filter.filtersNodes());
    }

    private ViewCache applyServerOverwrite(ViewCache oldViewCache, Path changePath, Node changedSnap, WriteTreeRef writesCache, Node optCompleteCache, boolean filterServerNode, ChildChangeAccumulator accumulator) {
        IndexedNode newServerCache;
        Path path;
        ViewCache viewCache = oldViewCache;
        Node node = changedSnap;
        CacheNode oldServerSnap = viewCache.getServerCache();
        NodeFilter nodeFilter = this.filter;
        if (!filterServerNode) {
            nodeFilter = nodeFilter.getIndexedFilter();
        }
        NodeFilter serverFilter = nodeFilter;
        boolean z = true;
        if (changePath.isEmpty()) {
            path = changePath;
            newServerCache = serverFilter.updateFullNode(oldServerSnap.getIndexedNode(), IndexedNode.from(node, serverFilter.getIndex()), (ChildChangeAccumulator) null);
        } else if (!serverFilter.filtersNodes() || oldServerSnap.isFiltered()) {
            ChildKey childKey = changePath.getFront();
            path = changePath;
            if (!oldServerSnap.isCompleteForPath(path) && path.size() > 1) {
                return viewCache;
            }
            Path childChangePath = path.popFront();
            Node newChildNode = oldServerSnap.getNode().getImmediateChild(childKey).updateChild(childChangePath, node);
            if (childKey.isPriorityChildName()) {
                newServerCache = serverFilter.updatePriority(oldServerSnap.getIndexedNode(), newChildNode);
            } else {
                newServerCache = serverFilter.updateChild(oldServerSnap.getIndexedNode(), childKey, newChildNode, childChangePath, NO_COMPLETE_SOURCE, (ChildChangeAccumulator) null);
            }
        } else {
            Utilities.hardAssert(!changePath.isEmpty(), "An empty path should have been caught in the other branch");
            ChildKey childKey2 = changePath.getFront();
            path = changePath;
            newServerCache = serverFilter.updateFullNode(oldServerSnap.getIndexedNode(), oldServerSnap.getIndexedNode().updateChild(childKey2, oldServerSnap.getNode().getImmediateChild(childKey2).updateChild(changePath.popFront(), node)), (ChildChangeAccumulator) null);
        }
        if (!oldServerSnap.isFullyInitialized() && !path.isEmpty()) {
            z = false;
        }
        ViewCache newViewCache = viewCache.updateServerSnap(newServerCache, z, serverFilter.filtersNodes());
        WriteTreeRef writeTreeRef = writesCache;
        return generateEventCacheAfterServerEvent(newViewCache, path, writeTreeRef, new WriteTreeCompleteChildSource(writeTreeRef, newViewCache, optCompleteCache), accumulator);
    }

    private ViewCache applyUserOverwrite(ViewCache oldViewCache, Path changePath, Node changedSnap, WriteTreeRef writesCache, Node optCompleteCache, ChildChangeAccumulator accumulator) {
        Node newChild;
        CacheNode oldEventSnap = oldViewCache.getEventCache();
        NodeFilter.CompleteChildSource source = new WriteTreeCompleteChildSource(writesCache, oldViewCache, optCompleteCache);
        if (changePath.isEmpty()) {
            ChildChangeAccumulator childChangeAccumulator = accumulator;
            return oldViewCache.updateEventSnap(this.filter.updateFullNode(oldViewCache.getEventCache().getIndexedNode(), IndexedNode.from(changedSnap, this.filter.getIndex()), accumulator), true, this.filter.filtersNodes());
        }
        ChildKey childKey = changePath.getFront();
        if (childKey.isPriorityChildName()) {
            ChildChangeAccumulator childChangeAccumulator2 = accumulator;
            return oldViewCache.updateEventSnap(this.filter.updatePriority(oldViewCache.getEventCache().getIndexedNode(), changedSnap), oldEventSnap.isFullyInitialized(), oldEventSnap.isFiltered());
        }
        Path childChangePath = changePath.popFront();
        Node oldChild = oldEventSnap.getNode().getImmediateChild(childKey);
        if (childChangePath.isEmpty()) {
            newChild = changedSnap;
        } else {
            Node newChild2 = source.getCompleteChild(childKey);
            if (newChild2 == null) {
                newChild = EmptyNode.Empty();
            } else if (!childChangePath.getBack().isPriorityChildName() || !newChild2.getChild(childChangePath.getParent()).isEmpty()) {
                newChild = newChild2.updateChild(childChangePath, changedSnap);
            } else {
                newChild = newChild2;
            }
        }
        if (!oldChild.equals(newChild)) {
            return oldViewCache.updateEventSnap(this.filter.updateChild(oldEventSnap.getIndexedNode(), childKey, newChild, childChangePath, source, accumulator), oldEventSnap.isFullyInitialized(), this.filter.filtersNodes());
        }
        return oldViewCache;
    }

    private static boolean cacheHasChild(ViewCache viewCache, ChildKey childKey) {
        return viewCache.getEventCache().isCompleteForChild(childKey);
    }

    private ViewCache applyUserMerge(ViewCache viewCache, Path path, CompoundWrite changedChildren, WriteTreeRef writesCache, Node serverCache, ChildChangeAccumulator accumulator) {
        Utilities.hardAssert(changedChildren.rootWrite() == null, "Can't have a merge that is an overwrite");
        Iterator<Map.Entry<Path, Node>> it = changedChildren.iterator();
        ViewCache currentViewCache = viewCache;
        while (it.hasNext()) {
            Map.Entry<Path, Node> entry = it.next();
            Path writePath = path.child(entry.getKey());
            if (cacheHasChild(viewCache, writePath.getFront())) {
                currentViewCache = applyUserOverwrite(currentViewCache, writePath, entry.getValue(), writesCache, serverCache, accumulator);
            }
        }
        Iterator<Map.Entry<Path, Node>> it2 = changedChildren.iterator();
        ViewCache currentViewCache2 = currentViewCache;
        while (it2.hasNext()) {
            Map.Entry<Path, Node> entry2 = it2.next();
            Path writePath2 = path.child(entry2.getKey());
            if (!cacheHasChild(viewCache, writePath2.getFront())) {
                currentViewCache2 = applyUserOverwrite(currentViewCache2, writePath2, entry2.getValue(), writesCache, serverCache, accumulator);
            }
        }
        return currentViewCache2;
    }

    private ViewCache applyServerMerge(ViewCache viewCache, Path path, CompoundWrite changedChildren, WriteTreeRef writesCache, Node serverCache, boolean filterServerNode, ChildChangeAccumulator accumulator) {
        CompoundWrite actualMerge;
        if (viewCache.getServerCache().getNode().isEmpty() && !viewCache.getServerCache().isFullyInitialized()) {
            return viewCache;
        }
        ViewCache curViewCache = viewCache;
        Utilities.hardAssert(changedChildren.rootWrite() == null, "Can't have a merge that is an overwrite");
        if (path.isEmpty()) {
            actualMerge = changedChildren;
            Path path2 = path;
            CompoundWrite compoundWrite = changedChildren;
        } else {
            actualMerge = CompoundWrite.emptyWrite().addWrites(path, changedChildren);
        }
        Node serverNode = viewCache.getServerCache().getNode();
        Map<ChildKey, CompoundWrite> childCompoundWrites = actualMerge.childCompoundWrites();
        ViewCache curViewCache2 = curViewCache;
        for (Map.Entry<ChildKey, CompoundWrite> childMerge : childCompoundWrites.entrySet()) {
            ChildKey childKey = childMerge.getKey();
            if (serverNode.hasChild(childKey)) {
                Node serverChild = serverNode.getImmediateChild(childKey);
                ChildKey childKey2 = childKey;
                Node node = serverChild;
                curViewCache2 = applyServerOverwrite(curViewCache2, new Path(childKey), childMerge.getValue().apply(serverChild), writesCache, serverCache, filterServerNode, accumulator);
            }
        }
        ViewCache curViewCache3 = curViewCache2;
        for (Map.Entry<ChildKey, CompoundWrite> childMerge2 : childCompoundWrites.entrySet()) {
            ChildKey childKey3 = childMerge2.getKey();
            boolean isUnknownDeepMerge = !viewCache.getServerCache().isCompleteForChild(childKey3) && childMerge2.getValue().rootWrite() == null;
            if (!serverNode.hasChild(childKey3) && !isUnknownDeepMerge) {
                curViewCache3 = applyServerOverwrite(curViewCache3, new Path(childKey3), childMerge2.getValue().apply(serverNode.getImmediateChild(childKey3)), writesCache, serverCache, filterServerNode, accumulator);
            }
        }
        return curViewCache3;
    }

    private ViewCache ackUserWrite(ViewCache viewCache, Path ackPath, ImmutableTree<Boolean> affectedTree, WriteTreeRef writesCache, Node optCompleteCache, ChildChangeAccumulator accumulator) {
        if (writesCache.shadowingWrite(ackPath) != null) {
            return viewCache;
        }
        boolean filterServerNode = viewCache.getServerCache().isFiltered();
        CacheNode serverCache = viewCache.getServerCache();
        if (affectedTree.getValue() == null) {
            ViewCache viewCache2 = viewCache;
            Path ackPath2 = ackPath;
            WriteTreeRef writesCache2 = writesCache;
            Node optCompleteCache2 = optCompleteCache;
            ChildChangeAccumulator accumulator2 = accumulator;
            CompoundWrite changedChildren = CompoundWrite.emptyWrite();
            Iterator<Map.Entry<Path, Boolean>> it = affectedTree.iterator();
            CompoundWrite changedChildren2 = changedChildren;
            while (it.hasNext()) {
                Path mergePath = it.next().getKey();
                Path serverCachePath = ackPath2.child(mergePath);
                if (serverCache.isCompleteForPath(serverCachePath)) {
                    changedChildren2 = changedChildren2.addWrite(mergePath, serverCache.getNode().getChild(serverCachePath));
                }
            }
            return applyServerMerge(viewCache2, ackPath2, changedChildren2, writesCache2, optCompleteCache2, filterServerNode, accumulator2);
        } else if ((!ackPath.isEmpty() || !serverCache.isFullyInitialized()) && !serverCache.isCompleteForPath(ackPath)) {
            ViewCache viewCache3 = viewCache;
            Path ackPath3 = ackPath;
            WriteTreeRef writesCache3 = writesCache;
            Node optCompleteCache3 = optCompleteCache;
            ChildChangeAccumulator accumulator3 = accumulator;
            if (!ackPath3.isEmpty()) {
                return viewCache3;
            }
            CompoundWrite changedChildren3 = CompoundWrite.emptyWrite();
            CompoundWrite changedChildren4 = changedChildren3;
            for (NamedNode child : serverCache.getNode()) {
                changedChildren4 = changedChildren4.addWrite(child.getName(), child.getNode());
            }
            return applyServerMerge(viewCache3, ackPath3, changedChildren4, writesCache3, optCompleteCache3, filterServerNode, accumulator3);
        } else {
            return applyServerOverwrite(viewCache, ackPath, serverCache.getNode().getChild(ackPath), writesCache, optCompleteCache, filterServerNode, accumulator);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00f8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.firebase.database.core.view.ViewCache revertUserWrite(com.google.firebase.database.core.view.ViewCache r9, com.google.firebase.database.core.Path r10, com.google.firebase.database.core.WriteTreeRef r11, com.google.firebase.database.snapshot.Node r12, com.google.firebase.database.core.view.filter.ChildChangeAccumulator r13) {
        /*
            r8 = this;
            com.google.firebase.database.snapshot.Node r0 = r11.shadowingWrite(r10)
            if (r0 == 0) goto L_0x0007
            return r9
        L_0x0007:
            com.google.firebase.database.core.view.ViewProcessor$WriteTreeCompleteChildSource r0 = new com.google.firebase.database.core.view.ViewProcessor$WriteTreeCompleteChildSource
            r0.<init>(r11, r9, r12)
            r6 = r0
            com.google.firebase.database.core.view.CacheNode r0 = r9.getEventCache()
            com.google.firebase.database.snapshot.IndexedNode r2 = r0.getIndexedNode()
            boolean r0 = r10.isEmpty()
            if (r0 != 0) goto L_0x00af
            com.google.firebase.database.snapshot.ChildKey r0 = r10.getFront()
            boolean r0 = r0.isPriorityChildName()
            if (r0 == 0) goto L_0x0028
            r7 = r13
            goto L_0x00b0
        L_0x0028:
            com.google.firebase.database.snapshot.ChildKey r3 = r10.getFront()
            com.google.firebase.database.core.view.CacheNode r0 = r9.getServerCache()
            com.google.firebase.database.snapshot.Node r0 = r11.calcCompleteChild(r3, r0)
            if (r0 != 0) goto L_0x004a
            com.google.firebase.database.core.view.CacheNode r1 = r9.getServerCache()
            boolean r1 = r1.isCompleteForChild(r3)
            if (r1 == 0) goto L_0x004a
            com.google.firebase.database.snapshot.Node r1 = r2.getNode()
            com.google.firebase.database.snapshot.Node r0 = r1.getImmediateChild(r3)
            r4 = r0
            goto L_0x004b
        L_0x004a:
            r4 = r0
        L_0x004b:
            if (r4 == 0) goto L_0x005a
            com.google.firebase.database.core.view.filter.NodeFilter r1 = r8.filter
            com.google.firebase.database.core.Path r5 = r10.popFront()
            r7 = r13
            com.google.firebase.database.snapshot.IndexedNode r13 = r1.updateChild(r2, r3, r4, r5, r6, r7)
            r0 = r4
            goto L_0x007c
        L_0x005a:
            r7 = r13
            r0 = r4
            if (r0 != 0) goto L_0x007b
            com.google.firebase.database.core.view.CacheNode r13 = r9.getEventCache()
            com.google.firebase.database.snapshot.Node r13 = r13.getNode()
            boolean r13 = r13.hasChild(r3)
            if (r13 == 0) goto L_0x007b
            com.google.firebase.database.core.view.filter.NodeFilter r1 = r8.filter
            com.google.firebase.database.snapshot.EmptyNode r4 = com.google.firebase.database.snapshot.EmptyNode.Empty()
            com.google.firebase.database.core.Path r5 = r10.popFront()
            com.google.firebase.database.snapshot.IndexedNode r13 = r1.updateChild(r2, r3, r4, r5, r6, r7)
            goto L_0x007c
        L_0x007b:
            r13 = r2
        L_0x007c:
            com.google.firebase.database.snapshot.Node r1 = r13.getNode()
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L_0x00e0
            com.google.firebase.database.core.view.CacheNode r1 = r9.getServerCache()
            boolean r1 = r1.isFullyInitialized()
            if (r1 == 0) goto L_0x00e0
            com.google.firebase.database.snapshot.Node r1 = r9.getCompleteServerSnap()
            com.google.firebase.database.snapshot.Node r1 = r11.calcCompleteEventCache(r1)
            boolean r4 = r1.isLeafNode()
            if (r4 == 0) goto L_0x00e0
            com.google.firebase.database.core.view.filter.NodeFilter r4 = r8.filter
            com.google.firebase.database.snapshot.Index r4 = r4.getIndex()
            com.google.firebase.database.snapshot.IndexedNode r4 = com.google.firebase.database.snapshot.IndexedNode.from(r1, r4)
            com.google.firebase.database.core.view.filter.NodeFilter r5 = r8.filter
            com.google.firebase.database.snapshot.IndexedNode r13 = r5.updateFullNode(r13, r4, r7)
            goto L_0x00e0
        L_0x00af:
            r7 = r13
        L_0x00b0:
            com.google.firebase.database.core.view.CacheNode r13 = r9.getServerCache()
            boolean r13 = r13.isFullyInitialized()
            if (r13 == 0) goto L_0x00c3
            com.google.firebase.database.snapshot.Node r13 = r9.getCompleteServerSnap()
            com.google.firebase.database.snapshot.Node r13 = r11.calcCompleteEventCache(r13)
            goto L_0x00cf
        L_0x00c3:
            com.google.firebase.database.core.view.CacheNode r13 = r9.getServerCache()
            com.google.firebase.database.snapshot.Node r13 = r13.getNode()
            com.google.firebase.database.snapshot.Node r13 = r11.calcCompleteEventChildren(r13)
        L_0x00cf:
            com.google.firebase.database.core.view.filter.NodeFilter r0 = r8.filter
            com.google.firebase.database.snapshot.Index r0 = r0.getIndex()
            com.google.firebase.database.snapshot.IndexedNode r0 = com.google.firebase.database.snapshot.IndexedNode.from(r13, r0)
            com.google.firebase.database.core.view.filter.NodeFilter r1 = r8.filter
            com.google.firebase.database.snapshot.IndexedNode r13 = r1.updateFullNode(r2, r0, r7)
        L_0x00e0:
            com.google.firebase.database.core.view.CacheNode r0 = r9.getServerCache()
            boolean r0 = r0.isFullyInitialized()
            if (r0 != 0) goto L_0x00f8
            com.google.firebase.database.core.Path r0 = com.google.firebase.database.core.Path.getEmptyPath()
            com.google.firebase.database.snapshot.Node r0 = r11.shadowingWrite(r0)
            if (r0 == 0) goto L_0x00f6
            goto L_0x00f8
        L_0x00f6:
            r0 = 0
            goto L_0x00f9
        L_0x00f8:
            r0 = 1
        L_0x00f9:
            com.google.firebase.database.core.view.filter.NodeFilter r1 = r8.filter
            boolean r1 = r1.filtersNodes()
            com.google.firebase.database.core.view.ViewCache r1 = r9.updateEventSnap(r13, r0, r1)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.core.view.ViewProcessor.revertUserWrite(com.google.firebase.database.core.view.ViewCache, com.google.firebase.database.core.Path, com.google.firebase.database.core.WriteTreeRef, com.google.firebase.database.snapshot.Node, com.google.firebase.database.core.view.filter.ChildChangeAccumulator):com.google.firebase.database.core.view.ViewCache");
    }

    private ViewCache listenComplete(ViewCache viewCache, Path path, WriteTreeRef writesCache, Node serverCache, ChildChangeAccumulator accumulator) {
        CacheNode oldServerNode = viewCache.getServerCache();
        return generateEventCacheAfterServerEvent(viewCache.updateServerSnap(oldServerNode.getIndexedNode(), oldServerNode.isFullyInitialized() || path.isEmpty(), oldServerNode.isFiltered()), path, writesCache, NO_COMPLETE_SOURCE, accumulator);
    }

    private static class WriteTreeCompleteChildSource implements NodeFilter.CompleteChildSource {
        private final Node optCompleteServerCache;
        private final ViewCache viewCache;
        private final WriteTreeRef writes;

        public WriteTreeCompleteChildSource(WriteTreeRef writes2, ViewCache viewCache2, Node optCompleteServerCache2) {
            this.writes = writes2;
            this.viewCache = viewCache2;
            this.optCompleteServerCache = optCompleteServerCache2;
        }

        public Node getCompleteChild(ChildKey childKey) {
            CacheNode serverNode;
            CacheNode node = this.viewCache.getEventCache();
            if (node.isCompleteForChild(childKey)) {
                return node.getNode().getImmediateChild(childKey);
            }
            if (this.optCompleteServerCache != null) {
                serverNode = new CacheNode(IndexedNode.from(this.optCompleteServerCache, KeyIndex.getInstance()), true, false);
            } else {
                serverNode = this.viewCache.getServerCache();
            }
            return this.writes.calcCompleteChild(childKey, serverNode);
        }

        public NamedNode getChildAfterChild(Index index, NamedNode child, boolean reverse) {
            Node completeServerData;
            if (this.optCompleteServerCache != null) {
                completeServerData = this.optCompleteServerCache;
            } else {
                completeServerData = this.viewCache.getCompleteServerSnap();
            }
            return this.writes.calcNextNodeAfterPost(completeServerData, child, reverse, index);
        }
    }
}
