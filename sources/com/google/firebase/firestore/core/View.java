package com.google.firebase.firestore.core;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.collection.ImmutableSortedSet;
import com.google.firebase.firestore.core.DocumentViewChange;
import com.google.firebase.firestore.core.LimboDocumentChange;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.core.ViewSnapshot;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.DocumentSet;
import com.google.firebase.firestore.remote.TargetChange;
import com.google.firebase.firestore.util.Assert;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class View {
    private boolean current;
    private DocumentSet documentSet;
    private ImmutableSortedSet<DocumentKey> limboDocuments;
    private ImmutableSortedSet<DocumentKey> mutatedKeys;
    private final Query query;
    private ViewSnapshot.SyncState syncState = ViewSnapshot.SyncState.NONE;
    private ImmutableSortedSet<DocumentKey> syncedDocuments;

    public static class DocumentChanges {
        final DocumentViewChangeSet changeSet;
        final DocumentSet documentSet;
        final ImmutableSortedSet<DocumentKey> mutatedKeys;
        /* access modifiers changed from: private */
        public final boolean needsRefill;

        private DocumentChanges(DocumentSet newDocuments, DocumentViewChangeSet changes, ImmutableSortedSet<DocumentKey> mutatedKeys2, boolean needsRefill2) {
            this.documentSet = newDocuments;
            this.changeSet = changes;
            this.mutatedKeys = mutatedKeys2;
            this.needsRefill = needsRefill2;
        }

        public boolean needsRefill() {
            return this.needsRefill;
        }
    }

    public View(Query query2, ImmutableSortedSet<DocumentKey> remoteDocuments) {
        this.query = query2;
        this.documentSet = DocumentSet.emptySet(query2.comparator());
        this.syncedDocuments = remoteDocuments;
        this.limboDocuments = DocumentKey.emptyKeySet();
        this.mutatedKeys = DocumentKey.emptyKeySet();
    }

    public ViewSnapshot.SyncState getSyncState() {
        return this.syncState;
    }

    public DocumentChanges computeDocChanges(ImmutableSortedMap<DocumentKey, Document> docChanges) {
        return computeDocChanges(docChanges, (DocumentChanges) null);
    }

    public DocumentChanges computeDocChanges(ImmutableSortedMap<DocumentKey, Document> docChanges, DocumentChanges previousChanges) {
        Document document;
        Document document2;
        ImmutableSortedSet<DocumentKey> newMutatedKeys;
        Document oldDoc;
        DocumentSet oldDocumentSet;
        DocumentChanges documentChanges = previousChanges;
        DocumentViewChangeSet changeSet = documentChanges != null ? documentChanges.changeSet : new DocumentViewChangeSet();
        DocumentSet oldDocumentSet2 = documentChanges != null ? documentChanges.documentSet : this.documentSet;
        ImmutableSortedSet<DocumentKey> newMutatedKeys2 = documentChanges != null ? documentChanges.mutatedKeys : this.mutatedKeys;
        DocumentSet newDocumentSet = oldDocumentSet2;
        boolean changeApplied = false;
        if (!this.query.getLimitType().equals(Query.LimitType.LIMIT_TO_FIRST) || ((long) oldDocumentSet2.size()) != this.query.getLimit()) {
            document = null;
        } else {
            document = oldDocumentSet2.getLastDocument();
        }
        Document lastDocInLimit = document;
        if (!this.query.getLimitType().equals(Query.LimitType.LIMIT_TO_LAST) || ((long) oldDocumentSet2.size()) != this.query.getLimit()) {
            document2 = null;
        } else {
            document2 = oldDocumentSet2.getFirstDocument();
        }
        Document firstDocInLimit = document2;
        Iterator<Map.Entry<DocumentKey, Document>> it = docChanges.iterator();
        while (it.hasNext()) {
            Map.Entry<DocumentKey, Document> entry = it.next();
            DocumentKey key = entry.getKey();
            Document oldDoc2 = oldDocumentSet2.getDocument(key);
            Document newDoc = this.query.matches(entry.getValue()) ? entry.getValue() : null;
            boolean oldDocHadPendingMutations = oldDoc2 != null && this.mutatedKeys.contains(oldDoc2.getKey());
            boolean newDocHasPendingMutations = newDoc != null && (newDoc.hasLocalMutations() || (this.mutatedKeys.contains(newDoc.getKey()) && newDoc.hasCommittedMutations()));
            boolean needsRefill = false;
            if (oldDoc2 == null || newDoc == null) {
                oldDocumentSet = oldDocumentSet2;
                boolean needsRefill2 = changeApplied;
                if (oldDoc2 == null && newDoc != null) {
                    changeSet.addChange(DocumentViewChange.create(DocumentViewChange.Type.ADDED, newDoc));
                    needsRefill = true;
                    changeApplied = needsRefill2;
                } else if (oldDoc2 == null || newDoc != null) {
                    changeApplied = needsRefill2;
                } else {
                    changeSet.addChange(DocumentViewChange.create(DocumentViewChange.Type.REMOVED, oldDoc2));
                    needsRefill = true;
                    changeApplied = (lastDocInLimit == null && firstDocInLimit == null) ? needsRefill2 : true;
                }
            } else {
                oldDocumentSet = oldDocumentSet2;
                boolean needsRefill3 = changeApplied;
                if (!oldDoc2.getData().equals(newDoc.getData())) {
                    if (!shouldWaitForSyncedDocument(oldDoc2, newDoc)) {
                        changeSet.addChange(DocumentViewChange.create(DocumentViewChange.Type.MODIFIED, newDoc));
                        if ((lastDocInLimit == null || this.query.comparator().compare(newDoc, lastDocInLimit) <= 0) && (firstDocInLimit == null || this.query.comparator().compare(newDoc, firstDocInLimit) >= 0)) {
                            needsRefill = true;
                            changeApplied = needsRefill3;
                        } else {
                            needsRefill = true;
                            changeApplied = true;
                        }
                    }
                } else if (oldDocHadPendingMutations != newDocHasPendingMutations) {
                    changeSet.addChange(DocumentViewChange.create(DocumentViewChange.Type.METADATA, newDoc));
                    needsRefill = true;
                    changeApplied = needsRefill3;
                }
                changeApplied = needsRefill3;
            }
            if (needsRefill) {
                if (newDoc != null) {
                    DocumentSet newDocumentSet2 = newDocumentSet.add(newDoc);
                    if (newDoc.hasLocalMutations()) {
                        newMutatedKeys2 = newMutatedKeys2.insert(newDoc.getKey());
                        newDocumentSet = newDocumentSet2;
                    } else {
                        newMutatedKeys2 = newMutatedKeys2.remove(newDoc.getKey());
                        newDocumentSet = newDocumentSet2;
                    }
                } else {
                    DocumentSet newDocumentSet3 = newDocumentSet.remove(key);
                    newMutatedKeys2 = newMutatedKeys2.remove(key);
                    newDocumentSet = newDocumentSet3;
                }
            }
            DocumentChanges documentChanges2 = previousChanges;
            oldDocumentSet2 = oldDocumentSet;
        }
        boolean needsRefill4 = changeApplied;
        if (this.query.hasLimit()) {
            long i = (long) newDocumentSet.size();
            long limit = this.query.getLimit();
            while (true) {
                i -= limit;
                if (i <= 0) {
                    break;
                }
                if (this.query.getLimitType().equals(Query.LimitType.LIMIT_TO_FIRST)) {
                    oldDoc = newDocumentSet.getLastDocument();
                } else {
                    oldDoc = newDocumentSet.getFirstDocument();
                }
                newDocumentSet = newDocumentSet.remove(oldDoc.getKey());
                newMutatedKeys2 = newMutatedKeys2.remove(oldDoc.getKey());
                changeSet.addChange(DocumentViewChange.create(DocumentViewChange.Type.REMOVED, oldDoc));
                limit = 1;
            }
            newMutatedKeys = newMutatedKeys2;
        } else {
            newMutatedKeys = newMutatedKeys2;
        }
        Assert.hardAssert(!needsRefill4 || previousChanges == null, "View was refilled using docs that themselves needed refilling.", new Object[0]);
        return new DocumentChanges(newDocumentSet, changeSet, newMutatedKeys, needsRefill4);
    }

    private boolean shouldWaitForSyncedDocument(Document oldDoc, Document newDoc) {
        return oldDoc.hasLocalMutations() && newDoc.hasCommittedMutations() && !newDoc.hasLocalMutations();
    }

    public ViewChange applyChanges(DocumentChanges docChanges) {
        return applyChanges(docChanges, (TargetChange) null);
    }

    public ViewChange applyChanges(DocumentChanges docChanges, TargetChange targetChange) {
        return applyChanges(docChanges, targetChange, false);
    }

    public ViewChange applyChanges(DocumentChanges docChanges, TargetChange targetChange, boolean targetIsPendingReset) {
        ViewSnapshot snapshot;
        DocumentChanges documentChanges = docChanges;
        TargetChange targetChange2 = targetChange;
        Assert.hardAssert(!documentChanges.needsRefill, "Cannot apply changes that need a refill", new Object[0]);
        DocumentSet oldDocumentSet = this.documentSet;
        this.documentSet = documentChanges.documentSet;
        this.mutatedKeys = documentChanges.mutatedKeys;
        List<DocumentViewChange> viewChanges = documentChanges.changeSet.getChanges();
        Collections.sort(viewChanges, new View$$ExternalSyntheticLambda0(this));
        applyTargetChange(targetChange2);
        List<LimboDocumentChange> limboDocumentChanges = targetIsPendingReset ? Collections.emptyList() : updateLimboDocuments();
        ViewSnapshot.SyncState newSyncState = this.limboDocuments.size() == 0 && this.current && !targetIsPendingReset ? ViewSnapshot.SyncState.SYNCED : ViewSnapshot.SyncState.LOCAL;
        boolean syncStatedChanged = newSyncState != this.syncState;
        this.syncState = newSyncState;
        if (viewChanges.size() != 0 || syncStatedChanged) {
            snapshot = new ViewSnapshot(this.query, documentChanges.documentSet, oldDocumentSet, viewChanges, newSyncState == ViewSnapshot.SyncState.LOCAL, documentChanges.mutatedKeys, syncStatedChanged, false, targetChange2 != null && !targetChange2.getResumeToken().isEmpty());
        } else {
            snapshot = null;
        }
        return new ViewChange(snapshot, limboDocumentChanges);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$applyChanges$0$com-google-firebase-firestore-core-View  reason: not valid java name */
    public /* synthetic */ int m1838lambda$applyChanges$0$comgooglefirebasefirestorecoreView(DocumentViewChange o1, DocumentViewChange o2) {
        int typeComp = Integer.compare(changeTypeOrder(o1), changeTypeOrder(o2));
        if (typeComp != 0) {
            return typeComp;
        }
        return this.query.comparator().compare(o1.getDocument(), o2.getDocument());
    }

    public ViewChange applyOnlineStateChange(OnlineState onlineState) {
        if (!this.current || onlineState != OnlineState.OFFLINE) {
            return new ViewChange((ViewSnapshot) null, Collections.emptyList());
        }
        this.current = false;
        return applyChanges(new DocumentChanges(this.documentSet, new DocumentViewChangeSet(), this.mutatedKeys, false));
    }

    private void applyTargetChange(TargetChange targetChange) {
        if (targetChange != null) {
            Iterator<DocumentKey> it = targetChange.getAddedDocuments().iterator();
            while (it.hasNext()) {
                this.syncedDocuments = this.syncedDocuments.insert(it.next());
            }
            Iterator<DocumentKey> it2 = targetChange.getModifiedDocuments().iterator();
            while (it2.hasNext()) {
                DocumentKey documentKey = it2.next();
                Assert.hardAssert(this.syncedDocuments.contains(documentKey), "Modified document %s not found in view.", documentKey);
            }
            Iterator<DocumentKey> it3 = targetChange.getRemovedDocuments().iterator();
            while (it3.hasNext()) {
                this.syncedDocuments = this.syncedDocuments.remove(it3.next());
            }
            this.current = targetChange.isCurrent();
        }
    }

    private List<LimboDocumentChange> updateLimboDocuments() {
        if (!this.current) {
            return Collections.emptyList();
        }
        ImmutableSortedSet<DocumentKey> oldLimboDocs = this.limboDocuments;
        this.limboDocuments = DocumentKey.emptyKeySet();
        Iterator<Document> it = this.documentSet.iterator();
        while (it.hasNext()) {
            Document doc = it.next();
            if (shouldBeLimboDoc(doc.getKey())) {
                this.limboDocuments = this.limboDocuments.insert(doc.getKey());
            }
        }
        List<LimboDocumentChange> changes = new ArrayList<>(oldLimboDocs.size() + this.limboDocuments.size());
        Iterator<DocumentKey> it2 = oldLimboDocs.iterator();
        while (it2.hasNext()) {
            DocumentKey key = it2.next();
            if (!this.limboDocuments.contains(key)) {
                changes.add(new LimboDocumentChange(LimboDocumentChange.Type.REMOVED, key));
            }
        }
        Iterator<DocumentKey> it3 = this.limboDocuments.iterator();
        while (it3.hasNext()) {
            DocumentKey key2 = it3.next();
            if (!oldLimboDocs.contains(key2)) {
                changes.add(new LimboDocumentChange(LimboDocumentChange.Type.ADDED, key2));
            }
        }
        return changes;
    }

    private boolean shouldBeLimboDoc(DocumentKey key) {
        Document doc;
        if (!this.syncedDocuments.contains(key) && (doc = this.documentSet.getDocument(key)) != null && !doc.hasLocalMutations()) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public ImmutableSortedSet<DocumentKey> getLimboDocuments() {
        return this.limboDocuments;
    }

    /* access modifiers changed from: package-private */
    public ImmutableSortedSet<DocumentKey> getSyncedDocuments() {
        return this.syncedDocuments;
    }

    private static int changeTypeOrder(DocumentViewChange change) {
        switch (change.getType()) {
            case ADDED:
                return 1;
            case MODIFIED:
                return 2;
            case METADATA:
                return 2;
            case REMOVED:
                return 0;
            default:
                throw new IllegalArgumentException("Unknown change type: " + change.getType());
        }
    }
}
