package com.google.firebase.heartbeatinfo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class HeartBeatInfoStorage$$ExternalSyntheticBackport1 {
    public static /* synthetic */ Set m(Object[] objArr) {
        HashSet hashSet = new HashSet(objArr.length);
        int length = objArr.length;
        int i = 0;
        while (i < length) {
            Object obj = objArr[i];
            if (hashSet.add(Objects.requireNonNull(obj))) {
                i++;
            } else {
                throw new IllegalArgumentException("duplicate element: " + obj);
            }
        }
        return Collections.unmodifiableSet(hashSet);
    }
}
