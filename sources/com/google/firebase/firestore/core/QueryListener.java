package com.google.firebase.firestore.core;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenSource;
import com.google.firebase.firestore.core.DocumentViewChange;
import com.google.firebase.firestore.core.EventManager;
import com.google.firebase.firestore.util.Assert;
import java.util.ArrayList;
import java.util.List;

public class QueryListener {
    private final EventListener<ViewSnapshot> listener;
    private OnlineState onlineState = OnlineState.UNKNOWN;
    private final EventManager.ListenOptions options;
    private final Query query;
    private boolean raisedInitialEvent = false;
    private ViewSnapshot snapshot;

    public QueryListener(Query query2, EventManager.ListenOptions options2, EventListener<ViewSnapshot> listener2) {
        this.query = query2;
        this.listener = listener2;
        this.options = options2;
    }

    public Query getQuery() {
        return this.query;
    }

    public boolean listensToRemoteStore() {
        if (this.options != null) {
            return !this.options.source.equals(ListenSource.CACHE);
        }
        return true;
    }

    public boolean onViewSnapshot(ViewSnapshot newSnapshot) {
        Assert.hardAssert(!newSnapshot.getChanges().isEmpty() || newSnapshot.didSyncStateChange(), "We got a new snapshot with no changes?", new Object[0]);
        boolean raisedEvent = false;
        if (!this.options.includeDocumentMetadataChanges) {
            List<DocumentViewChange> documentChanges = new ArrayList<>();
            for (DocumentViewChange change : newSnapshot.getChanges()) {
                if (change.getType() != DocumentViewChange.Type.METADATA) {
                    documentChanges.add(change);
                }
            }
            newSnapshot = new ViewSnapshot(newSnapshot.getQuery(), newSnapshot.getDocuments(), newSnapshot.getOldDocuments(), documentChanges, newSnapshot.isFromCache(), newSnapshot.getMutatedKeys(), newSnapshot.didSyncStateChange(), true, newSnapshot.hasCachedResults());
        }
        if (!this.raisedInitialEvent) {
            if (shouldRaiseInitialEvent(newSnapshot, this.onlineState)) {
                raiseInitialEvent(newSnapshot);
                raisedEvent = true;
            }
        } else if (shouldRaiseEvent(newSnapshot)) {
            this.listener.onEvent(newSnapshot, (FirebaseFirestoreException) null);
            raisedEvent = true;
        }
        this.snapshot = newSnapshot;
        return raisedEvent;
    }

    public void onError(FirebaseFirestoreException error) {
        this.listener.onEvent(null, error);
    }

    public boolean onOnlineStateChanged(OnlineState onlineState2) {
        this.onlineState = onlineState2;
        if (this.snapshot == null || this.raisedInitialEvent || !shouldRaiseInitialEvent(this.snapshot, onlineState2)) {
            return false;
        }
        raiseInitialEvent(this.snapshot);
        return true;
    }

    private boolean shouldRaiseInitialEvent(ViewSnapshot snapshot2, OnlineState onlineState2) {
        Assert.hardAssert(!this.raisedInitialEvent, "Determining whether to raise first event but already had first event.", new Object[0]);
        if (!snapshot2.isFromCache() || !listensToRemoteStore()) {
            return true;
        }
        boolean maybeOnline = !onlineState2.equals(OnlineState.OFFLINE);
        if (this.options.waitForSyncWhenOnline && maybeOnline) {
            Assert.hardAssert(snapshot2.isFromCache(), "Waiting for sync, but snapshot is not from cache", new Object[0]);
            return false;
        } else if (!snapshot2.getDocuments().isEmpty() || snapshot2.hasCachedResults() || onlineState2.equals(OnlineState.OFFLINE)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean shouldRaiseEvent(ViewSnapshot snapshot2) {
        boolean hasPendingWritesChanged = true;
        if (!snapshot2.getChanges().isEmpty()) {
            return true;
        }
        if (this.snapshot == null || this.snapshot.hasPendingWrites() == snapshot2.hasPendingWrites()) {
            hasPendingWritesChanged = false;
        }
        if (snapshot2.didSyncStateChange() || hasPendingWritesChanged) {
            return this.options.includeQueryMetadataChanges;
        }
        return false;
    }

    private void raiseInitialEvent(ViewSnapshot snapshot2) {
        Assert.hardAssert(!this.raisedInitialEvent, "Trying to raise initial event for second time", new Object[0]);
        ViewSnapshot snapshot3 = ViewSnapshot.fromInitialDocuments(snapshot2.getQuery(), snapshot2.getDocuments(), snapshot2.getMutatedKeys(), snapshot2.isFromCache(), snapshot2.excludesMetadataChanges(), snapshot2.hasCachedResults());
        this.raisedInitialEvent = true;
        this.listener.onEvent(snapshot3, (FirebaseFirestoreException) null);
    }
}
