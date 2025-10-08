package com.google.firebase.firestore.core;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenSource;
import com.google.firebase.firestore.core.SyncEngine;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Util;
import io.grpc.Status;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class EventManager implements SyncEngine.SyncEngineCallback {
    private OnlineState onlineState = OnlineState.UNKNOWN;
    private final Map<Query, QueryListenersInfo> queries;
    private final Set<EventListener<Void>> snapshotsInSyncListeners = new HashSet();
    private final SyncEngine syncEngine;

    public static class ListenOptions {
        public boolean includeDocumentMetadataChanges;
        public boolean includeQueryMetadataChanges;
        public ListenSource source = ListenSource.DEFAULT;
        public boolean waitForSyncWhenOnline;
    }

    private enum ListenerRemovalAction {
        TERMINATE_LOCAL_LISTEN_AND_REQUIRE_WATCH_DISCONNECTION,
        TERMINATE_LOCAL_LISTEN_ONLY,
        REQUIRE_WATCH_DISCONNECTION_ONLY,
        NO_ACTION_REQUIRED
    }

    private enum ListenerSetupAction {
        INITIALIZE_LOCAL_LISTEN_AND_REQUIRE_WATCH_CONNECTION,
        INITIALIZE_LOCAL_LISTEN_ONLY,
        REQUIRE_WATCH_CONNECTION_ONLY,
        NO_ACTION_REQUIRED
    }

    private static class QueryListenersInfo {
        /* access modifiers changed from: private */
        public final List<QueryListener> listeners = new ArrayList();
        /* access modifiers changed from: private */
        public int targetId;
        /* access modifiers changed from: private */
        public ViewSnapshot viewSnapshot;

        QueryListenersInfo() {
        }

        /* access modifiers changed from: package-private */
        public boolean hasRemoteListeners() {
            for (QueryListener listener : this.listeners) {
                if (listener.listensToRemoteStore()) {
                    return true;
                }
            }
            return false;
        }
    }

    public EventManager(SyncEngine syncEngine2) {
        this.syncEngine = syncEngine2;
        this.queries = new HashMap();
        syncEngine2.setCallback(this);
    }

    public int addQueryListener(QueryListener queryListener) {
        ListenerSetupAction listenerSetupAction;
        Query query = queryListener.getQuery();
        ListenerSetupAction listenerAction = ListenerSetupAction.NO_ACTION_REQUIRED;
        QueryListenersInfo queryInfo = this.queries.get(query);
        if (queryInfo == null) {
            queryInfo = new QueryListenersInfo();
            this.queries.put(query, queryInfo);
            if (queryListener.listensToRemoteStore()) {
                listenerSetupAction = ListenerSetupAction.INITIALIZE_LOCAL_LISTEN_AND_REQUIRE_WATCH_CONNECTION;
            } else {
                listenerSetupAction = ListenerSetupAction.INITIALIZE_LOCAL_LISTEN_ONLY;
            }
            listenerAction = listenerSetupAction;
        } else if (!queryInfo.hasRemoteListeners() && queryListener.listensToRemoteStore()) {
            listenerAction = ListenerSetupAction.REQUIRE_WATCH_CONNECTION_ONLY;
        }
        queryInfo.listeners.add(queryListener);
        Assert.hardAssert(!queryListener.onOnlineStateChanged(this.onlineState), "onOnlineStateChanged() shouldn't raise an event for brand-new listeners.", new Object[0]);
        if (queryInfo.viewSnapshot != null && queryListener.onViewSnapshot(queryInfo.viewSnapshot)) {
            raiseSnapshotsInSyncEvent();
        }
        switch (listenerAction) {
            case INITIALIZE_LOCAL_LISTEN_AND_REQUIRE_WATCH_CONNECTION:
                int unused = queryInfo.targetId = this.syncEngine.listen(query, true);
                break;
            case INITIALIZE_LOCAL_LISTEN_ONLY:
                int unused2 = queryInfo.targetId = this.syncEngine.listen(query, false);
                break;
            case REQUIRE_WATCH_CONNECTION_ONLY:
                this.syncEngine.listenToRemoteStore(query);
                break;
        }
        return queryInfo.targetId;
    }

    public void removeQueryListener(QueryListener listener) {
        ListenerRemovalAction listenerRemovalAction;
        Query query = listener.getQuery();
        QueryListenersInfo queryInfo = this.queries.get(query);
        ListenerRemovalAction listenerAction = ListenerRemovalAction.NO_ACTION_REQUIRED;
        if (queryInfo != null) {
            queryInfo.listeners.remove(listener);
            if (queryInfo.listeners.isEmpty()) {
                if (listener.listensToRemoteStore()) {
                    listenerRemovalAction = ListenerRemovalAction.TERMINATE_LOCAL_LISTEN_AND_REQUIRE_WATCH_DISCONNECTION;
                } else {
                    listenerRemovalAction = ListenerRemovalAction.TERMINATE_LOCAL_LISTEN_ONLY;
                }
                listenerAction = listenerRemovalAction;
            } else if (!queryInfo.hasRemoteListeners() && listener.listensToRemoteStore()) {
                listenerAction = ListenerRemovalAction.REQUIRE_WATCH_DISCONNECTION_ONLY;
            }
            switch (listenerAction) {
                case TERMINATE_LOCAL_LISTEN_AND_REQUIRE_WATCH_DISCONNECTION:
                    this.queries.remove(query);
                    this.syncEngine.stopListening(query, true);
                    return;
                case TERMINATE_LOCAL_LISTEN_ONLY:
                    this.queries.remove(query);
                    this.syncEngine.stopListening(query, false);
                    return;
                case REQUIRE_WATCH_DISCONNECTION_ONLY:
                    this.syncEngine.stopListeningToRemoteStore(query);
                    return;
                default:
                    return;
            }
        }
    }

    public void addSnapshotsInSyncListener(EventListener<Void> listener) {
        this.snapshotsInSyncListeners.add(listener);
        listener.onEvent(null, (FirebaseFirestoreException) null);
    }

    public void removeSnapshotsInSyncListener(EventListener<Void> listener) {
        this.snapshotsInSyncListeners.remove(listener);
    }

    private void raiseSnapshotsInSyncEvent() {
        for (EventListener<Void> listener : this.snapshotsInSyncListeners) {
            listener.onEvent(null, (FirebaseFirestoreException) null);
        }
    }

    public void onViewSnapshots(List<ViewSnapshot> snapshotList) {
        boolean raisedEvent = false;
        for (ViewSnapshot viewSnapshot : snapshotList) {
            QueryListenersInfo info = this.queries.get(viewSnapshot.getQuery());
            if (info != null) {
                for (QueryListener listener : info.listeners) {
                    if (listener.onViewSnapshot(viewSnapshot)) {
                        raisedEvent = true;
                    }
                }
                ViewSnapshot unused = info.viewSnapshot = viewSnapshot;
            }
        }
        if (raisedEvent) {
            raiseSnapshotsInSyncEvent();
        }
    }

    public void onError(Query query, Status error) {
        QueryListenersInfo info = this.queries.get(query);
        if (info != null) {
            for (QueryListener listener : info.listeners) {
                listener.onError(Util.exceptionFromStatus(error));
            }
        }
        this.queries.remove(query);
    }

    public void handleOnlineStateChange(OnlineState onlineState2) {
        boolean raisedEvent = false;
        this.onlineState = onlineState2;
        for (QueryListenersInfo info : this.queries.values()) {
            for (QueryListener listener : info.listeners) {
                if (listener.onOnlineStateChanged(onlineState2)) {
                    raisedEvent = true;
                }
            }
        }
        if (raisedEvent) {
            raiseSnapshotsInSyncEvent();
        }
    }
}
