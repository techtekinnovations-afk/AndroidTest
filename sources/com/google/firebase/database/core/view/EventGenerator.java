package com.google.firebase.database.core.view;

import com.google.firebase.database.core.EventRegistration;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.view.Event;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.NamedNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EventGenerator {
    /* access modifiers changed from: private */
    public final Index index;
    private final QuerySpec query;

    public EventGenerator(QuerySpec query2) {
        this.query = query2;
        this.index = query2.getIndex();
    }

    private void generateEventsForType(List<DataEvent> events, Event.EventType type, List<Change> changes, List<EventRegistration> eventRegistrations, IndexedNode eventCache) {
        List<Change> filteredChanges = new ArrayList<>();
        for (Change change : changes) {
            if (change.getEventType().equals(type)) {
                filteredChanges.add(change);
            }
        }
        Collections.sort(filteredChanges, changeComparator());
        for (Change change2 : filteredChanges) {
            for (EventRegistration registration : eventRegistrations) {
                if (registration.respondsTo(type)) {
                    events.add(generateEvent(change2, registration, eventCache));
                }
            }
        }
    }

    private DataEvent generateEvent(Change change, EventRegistration registration, IndexedNode eventCache) {
        Change newChange;
        if (change.getEventType().equals(Event.EventType.VALUE) || change.getEventType().equals(Event.EventType.CHILD_REMOVED)) {
            newChange = change;
        } else {
            newChange = change.changeWithPrevName(eventCache.getPredecessorChildName(change.getChildKey(), change.getIndexedNode().getNode(), this.index));
        }
        return registration.createEvent(newChange, this.query);
    }

    public List<DataEvent> generateEventsForChanges(List<Change> changes, IndexedNode eventCache, List<EventRegistration> eventRegistrations) {
        List<DataEvent> events = new ArrayList<>();
        ArrayList arrayList = new ArrayList();
        for (Change change : changes) {
            if (change.getEventType().equals(Event.EventType.CHILD_CHANGED) && this.index.indexedValueChanged(change.getOldIndexedNode().getNode(), change.getIndexedNode().getNode())) {
                arrayList.add(Change.childMovedChange(change.getChildKey(), change.getIndexedNode()));
            }
        }
        List<Change> changes2 = changes;
        IndexedNode eventCache2 = eventCache;
        List<EventRegistration> eventRegistrations2 = eventRegistrations;
        generateEventsForType(events, Event.EventType.CHILD_REMOVED, changes2, eventRegistrations2, eventCache2);
        generateEventsForType(events, Event.EventType.CHILD_ADDED, changes2, eventRegistrations2, eventCache2);
        ArrayList arrayList2 = arrayList;
        generateEventsForType(events, Event.EventType.CHILD_MOVED, arrayList2, eventRegistrations2, eventCache2);
        ArrayList arrayList3 = arrayList2;
        List<Change> changes3 = changes;
        generateEventsForType(events, Event.EventType.CHILD_CHANGED, changes3, eventRegistrations2, eventCache2);
        generateEventsForType(events, Event.EventType.VALUE, changes3, eventRegistrations2, eventCache2);
        return events;
    }

    private Comparator<Change> changeComparator() {
        return new Comparator<Change>() {
            public int compare(Change a, Change b) {
                Utilities.hardAssert((a.getChildKey() == null || b.getChildKey() == null) ? false : true);
                return EventGenerator.this.index.compare(new NamedNode(a.getChildKey(), a.getIndexedNode().getNode()), new NamedNode(b.getChildKey(), b.getIndexedNode().getNode()));
            }
        };
    }
}
