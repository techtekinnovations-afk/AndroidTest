package com.google.firebase.firestore.util;

import com.google.firebase.firestore.util.AsyncQueue;
import java.util.Comparator;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class AsyncQueue$$ExternalSyntheticLambda0 implements Comparator {
    public final int compare(Object obj, Object obj2) {
        return Long.compare(((AsyncQueue.DelayedTask) obj).targetTimeMs, ((AsyncQueue.DelayedTask) obj2).targetTimeMs);
    }
}
