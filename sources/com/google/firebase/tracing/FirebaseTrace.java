package com.google.firebase.tracing;

import android.os.Trace;

public final class FirebaseTrace {
    private FirebaseTrace() {
    }

    public static void pushTrace(String name) {
        Trace.beginSection(name);
    }

    public static void popTrace() {
        Trace.endSection();
    }
}
