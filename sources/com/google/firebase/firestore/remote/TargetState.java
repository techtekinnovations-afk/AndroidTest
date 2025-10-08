package com.google.firebase.firestore.remote;

import com.google.firebase.database.collection.ImmutableSortedSet;
import com.google.firebase.firestore.core.DocumentViewChange;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.util.Assert;
import com.google.protobuf.ByteString;
import java.util.HashMap;
import java.util.Map;

final class TargetState {
    private boolean current = false;
    private final Map<DocumentKey, DocumentViewChange.Type> documentChanges = new HashMap();
    private boolean hasChanges = true;
    private int outstandingResponses = 0;
    private ByteString resumeToken = ByteString.EMPTY;

    TargetState() {
    }

    /* access modifiers changed from: package-private */
    public boolean isCurrent() {
        return this.current;
    }

    /* access modifiers changed from: package-private */
    public boolean isPending() {
        return this.outstandingResponses != 0;
    }

    /* access modifiers changed from: package-private */
    public boolean hasChanges() {
        return this.hasChanges;
    }

    /* access modifiers changed from: package-private */
    public void updateResumeToken(ByteString resumeToken2) {
        if (!resumeToken2.isEmpty()) {
            this.hasChanges = true;
            this.resumeToken = resumeToken2;
        }
    }

    /* access modifiers changed from: package-private */
    public TargetChange toTargetChange() {
        ImmutableSortedSet<DocumentKey> addedDocuments = DocumentKey.emptyKeySet();
        ImmutableSortedSet<DocumentKey> modifiedDocuments = DocumentKey.emptyKeySet();
        ImmutableSortedSet<DocumentKey> removedDocuments = DocumentKey.emptyKeySet();
        ImmutableSortedSet<DocumentKey> addedDocuments2 = addedDocuments;
        ImmutableSortedSet<DocumentKey> modifiedDocuments2 = modifiedDocuments;
        ImmutableSortedSet<DocumentKey> removedDocuments2 = removedDocuments;
        for (Map.Entry<DocumentKey, DocumentViewChange.Type> entry : this.documentChanges.entrySet()) {
            DocumentKey key = entry.getKey();
            DocumentViewChange.Type changeType = entry.getValue();
            switch (changeType) {
                case ADDED:
                    addedDocuments2 = addedDocuments2.insert(key);
                    break;
                case MODIFIED:
                    modifiedDocuments2 = modifiedDocuments2.insert(key);
                    break;
                case REMOVED:
                    removedDocuments2 = removedDocuments2.insert(key);
                    break;
                default:
                    throw Assert.fail("Encountered invalid change type: %s", changeType);
            }
        }
        return new TargetChange(this.resumeToken, this.current, addedDocuments2, modifiedDocuments2, removedDocuments2);
    }

    /* access modifiers changed from: package-private */
    public void clearChanges() {
        this.hasChanges = false;
        this.documentChanges.clear();
    }

    /* access modifiers changed from: package-private */
    public void addDocumentChange(DocumentKey key, DocumentViewChange.Type changeType) {
        this.hasChanges = true;
        this.documentChanges.put(key, changeType);
    }

    /* access modifiers changed from: package-private */
    public void removeDocumentChange(DocumentKey key) {
        this.hasChanges = true;
        this.documentChanges.remove(key);
    }

    /* access modifiers changed from: package-private */
    public void recordPendingTargetRequest() {
        this.outstandingResponses++;
    }

    /* access modifiers changed from: package-private */
    public void recordTargetResponse() {
        this.outstandingResponses--;
    }

    /* access modifiers changed from: package-private */
    public void markCurrent() {
        this.hasChanges = true;
        this.current = true;
    }
}
