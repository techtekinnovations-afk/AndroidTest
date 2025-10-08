package com.google.firebase.firestore.model;

import com.google.firebase.firestore.model.FieldIndex;
import java.util.Comparator;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FieldIndex$IndexOffset$$ExternalSyntheticLambda0 implements Comparator {
    public final int compare(Object obj, Object obj2) {
        return FieldIndex.IndexOffset.fromDocument((MutableDocument) obj).compareTo(FieldIndex.IndexOffset.fromDocument((MutableDocument) obj2));
    }
}
