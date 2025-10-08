package com.google.firebase.firestore.local;

import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.mutation.FieldMask;
import javax.annotation.Nullable;

public class OverlayedDocument {
    @Nullable
    private FieldMask mutatedFields;
    private Document overlayedDocument;

    OverlayedDocument(Document overlayedDocument2, FieldMask mutatedFields2) {
        this.overlayedDocument = overlayedDocument2;
        this.mutatedFields = mutatedFields2;
    }

    public Document getDocument() {
        return this.overlayedDocument;
    }

    @Nullable
    public FieldMask getMutatedFields() {
        return this.mutatedFields;
    }
}
