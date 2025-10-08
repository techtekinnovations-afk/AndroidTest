package com.google.firebase.firestore.remote;

import com.google.firebase.firestore.local.QueryPurpose;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MutableDocument;
import com.google.firebase.firestore.model.SnapshotVersion;
import java.util.Map;
import java.util.Set;

public final class RemoteEvent {
    private final Map<DocumentKey, MutableDocument> documentUpdates;
    private final Set<DocumentKey> resolvedLimboDocuments;
    private final SnapshotVersion snapshotVersion;
    private final Map<Integer, TargetChange> targetChanges;
    private final Map<Integer, QueryPurpose> targetMismatches;

    public RemoteEvent(SnapshotVersion snapshotVersion2, Map<Integer, TargetChange> targetChanges2, Map<Integer, QueryPurpose> targetMismatches2, Map<DocumentKey, MutableDocument> documentUpdates2, Set<DocumentKey> resolvedLimboDocuments2) {
        this.snapshotVersion = snapshotVersion2;
        this.targetChanges = targetChanges2;
        this.targetMismatches = targetMismatches2;
        this.documentUpdates = documentUpdates2;
        this.resolvedLimboDocuments = resolvedLimboDocuments2;
    }

    public SnapshotVersion getSnapshotVersion() {
        return this.snapshotVersion;
    }

    public Map<Integer, TargetChange> getTargetChanges() {
        return this.targetChanges;
    }

    public Map<Integer, QueryPurpose> getTargetMismatches() {
        return this.targetMismatches;
    }

    public Map<DocumentKey, MutableDocument> getDocumentUpdates() {
        return this.documentUpdates;
    }

    public Set<DocumentKey> getResolvedLimboDocuments() {
        return this.resolvedLimboDocuments;
    }

    public String toString() {
        return "RemoteEvent{snapshotVersion=" + this.snapshotVersion + ", targetChanges=" + this.targetChanges + ", targetMismatches=" + this.targetMismatches + ", documentUpdates=" + this.documentUpdates + ", resolvedLimboDocuments=" + this.resolvedLimboDocuments + '}';
    }
}
