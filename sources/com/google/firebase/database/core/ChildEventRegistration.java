package com.google.firebase.database.core;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.InternalHelpers;
import com.google.firebase.database.core.view.Change;
import com.google.firebase.database.core.view.DataEvent;
import com.google.firebase.database.core.view.Event;
import com.google.firebase.database.core.view.QuerySpec;

public class ChildEventRegistration extends EventRegistration {
    private final ChildEventListener eventListener;
    private final Repo repo;
    private final QuerySpec spec;

    public ChildEventRegistration(Repo repo2, ChildEventListener eventListener2, QuerySpec spec2) {
        this.repo = repo2;
        this.eventListener = eventListener2;
        this.spec = spec2;
    }

    public boolean respondsTo(Event.EventType eventType) {
        return eventType != Event.EventType.VALUE;
    }

    public boolean equals(Object other) {
        return (other instanceof ChildEventRegistration) && ((ChildEventRegistration) other).eventListener.equals(this.eventListener) && ((ChildEventRegistration) other).repo.equals(this.repo) && ((ChildEventRegistration) other).spec.equals(this.spec);
    }

    public int hashCode() {
        return (((this.eventListener.hashCode() * 31) + this.repo.hashCode()) * 31) + this.spec.hashCode();
    }

    public DataEvent createEvent(Change change, QuerySpec query) {
        return new DataEvent(change.getEventType(), this, InternalHelpers.createDataSnapshot(InternalHelpers.createReference(this.repo, query.getPath().child(change.getChildKey())), change.getIndexedNode()), change.getPrevName() != null ? change.getPrevName().asString() : null);
    }

    public void fireEvent(DataEvent eventData) {
        if (!isZombied()) {
            switch (eventData.getEventType()) {
                case CHILD_ADDED:
                    this.eventListener.onChildAdded(eventData.getSnapshot(), eventData.getPreviousName());
                    return;
                case CHILD_CHANGED:
                    this.eventListener.onChildChanged(eventData.getSnapshot(), eventData.getPreviousName());
                    return;
                case CHILD_MOVED:
                    this.eventListener.onChildMoved(eventData.getSnapshot(), eventData.getPreviousName());
                    return;
                case CHILD_REMOVED:
                    this.eventListener.onChildRemoved(eventData.getSnapshot());
                    return;
                default:
                    return;
            }
        }
    }

    public void fireCancelEvent(DatabaseError error) {
        this.eventListener.onCancelled(error);
    }

    public EventRegistration clone(QuerySpec newQuery) {
        return new ChildEventRegistration(this.repo, this.eventListener, newQuery);
    }

    public boolean isSameListener(EventRegistration other) {
        return (other instanceof ChildEventRegistration) && ((ChildEventRegistration) other).eventListener.equals(this.eventListener);
    }

    public QuerySpec getQuerySpec() {
        return this.spec;
    }

    public String toString() {
        return "ChildEventRegistration";
    }

    /* access modifiers changed from: package-private */
    public Repo getRepo() {
        return this.repo;
    }
}
