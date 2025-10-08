package com.google.firebase;

import kotlin.Metadata;
import kotlin.jvm.internal.PropertyReference1Impl;

@Metadata(k = 3, mv = {2, 0, 0}, xi = 48)
/* compiled from: Timestamp.kt */
/* synthetic */ class Timestamp$compareTo$1 extends PropertyReference1Impl {
    public static final Timestamp$compareTo$1 INSTANCE = new Timestamp$compareTo$1();

    Timestamp$compareTo$1() {
        super(Timestamp.class, "seconds", "getSeconds()J", 0);
    }

    public Object get(Object receiver0) {
        return Long.valueOf(((Timestamp) receiver0).getSeconds());
    }
}
