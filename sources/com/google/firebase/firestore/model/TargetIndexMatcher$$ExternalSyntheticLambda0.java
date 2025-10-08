package com.google.firebase.firestore.model;

import com.google.firebase.firestore.core.FieldFilter;
import java.util.Comparator;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class TargetIndexMatcher$$ExternalSyntheticLambda0 implements Comparator {
    public final int compare(Object obj, Object obj2) {
        return ((FieldFilter) obj).getField().compareTo(((FieldFilter) obj2).getField());
    }
}
