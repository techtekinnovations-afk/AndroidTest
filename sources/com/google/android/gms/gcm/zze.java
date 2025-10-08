package com.google.android.gms.gcm;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

final class zze implements ThreadFactory {
    private final AtomicInteger zzy = new AtomicInteger(1);

    zze(GcmTaskService gcmTaskService) {
    }

    public final Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, new StringBuilder(20).append("gcm-task#").append(this.zzy.getAndIncrement()).toString());
        thread.setPriority(4);
        return thread;
    }
}
