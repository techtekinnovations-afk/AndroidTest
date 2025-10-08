package com.google.firebase.firestore.core;

import com.google.firebase.firestore.core.FieldFilter;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firestore.v1.Value;
import java.util.ArrayList;
import java.util.List;

public class KeyFieldNotInFilter extends FieldFilter {
    private final List<DocumentKey> keys = new ArrayList();

    KeyFieldNotInFilter(FieldPath field, Value value) {
        super(field, FieldFilter.Operator.NOT_IN, value);
        this.keys.addAll(KeyFieldInFilter.extractDocumentKeysFromArrayValue(FieldFilter.Operator.NOT_IN, value));
    }

    public boolean matches(Document doc) {
        return !this.keys.contains(doc.getKey());
    }
}
