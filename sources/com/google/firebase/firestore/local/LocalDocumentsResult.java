package com.google.firebase.firestore.local;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentCollections;
import com.google.firebase.firestore.model.DocumentKey;
import java.util.Map;

public final class LocalDocumentsResult {
    private final int batchId;
    private final ImmutableSortedMap<DocumentKey, Document> documents;

    LocalDocumentsResult(int batchId2, ImmutableSortedMap<DocumentKey, Document> documents2) {
        this.batchId = batchId2;
        this.documents = documents2;
    }

    public static LocalDocumentsResult fromOverlayedDocuments(int batchId2, Map<DocumentKey, OverlayedDocument> overlays) {
        ImmutableSortedMap<DocumentKey, Document> documents2 = DocumentCollections.emptyDocumentMap();
        for (Map.Entry<DocumentKey, OverlayedDocument> entry : overlays.entrySet()) {
            documents2 = documents2.insert(entry.getKey(), entry.getValue().getDocument());
        }
        return new LocalDocumentsResult(batchId2, documents2);
    }

    public int getBatchId() {
        return this.batchId;
    }

    public ImmutableSortedMap<DocumentKey, Document> getDocuments() {
        return this.documents;
    }
}
