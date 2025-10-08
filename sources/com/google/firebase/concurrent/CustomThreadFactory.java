package com.google.firebase.concurrent;

import android.os.Process;
import android.os.StrictMode;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.Nullable;

class CustomThreadFactory implements ThreadFactory {
    private static final ThreadFactory DEFAULT = Executors.defaultThreadFactory();
    private final String namePrefix;
    private final StrictMode.ThreadPolicy policy;
    private final int priority;
    private final AtomicLong threadCount = new AtomicLong();

    CustomThreadFactory(String namePrefix2, int priority2, @Nullable StrictMode.ThreadPolicy policy2) {
        this.namePrefix = namePrefix2;
        this.priority = priority2;
        this.policy = policy2;
    }

    public Thread newThread(Runnable r) {
        Thread thread = DEFAULT.newThread(new CustomThreadFactory$$ExternalSyntheticLambda0(this, r));
        thread.setName(String.format(Locale.ROOT, "%s Thread #%d", new Object[]{this.namePrefix, Long.valueOf(this.threadCount.getAndIncrement())}));
        return thread;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$newThread$0$com-google-firebase-concurrent-CustomThreadFactory  reason: not valid java name */
    public /* synthetic */ void m1768lambda$newThread$0$comgooglefirebaseconcurrentCustomThreadFactory(Runnable r) {
        Process.setThreadPriority(this.priority);
        if (this.policy != null) {
            StrictMode.setThreadPolicy(this.policy);
        }
        r.run();
    }
}
