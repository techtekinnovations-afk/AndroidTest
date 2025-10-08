package com.google.firebase;

import kotlin.Metadata;
import kotlin.jvm.internal.PropertyReference1Impl;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: Timestamp.kt */
/* synthetic */ class Timestamp$compareTo$2 extends PropertyReference1Impl {
    public static final Timestamp$compareTo$2 INSTANCE = new Timestamp$compareTo$2();

    Timestamp$compareTo$2() {
        super(Timestamp.class, "nanoseconds", "getNanoseconds()I", 0);
    }

    public Object get(Object receiver0) {
        return Integer.valueOf(((Timestamp) receiver0).getNanoseconds());
    }
}
