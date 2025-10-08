package com.google.firebase.concurrent;

import android.os.Build;
import android.os.StrictMode;
import com.google.firebase.annotations.concurrent.Background;
import com.google.firebase.annotations.concurrent.Blocking;
import com.google.firebase.annotations.concurrent.Lightweight;
import com.google.firebase.annotations.concurrent.UiThread;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.Lazy;
import com.google.firebase.components.Qualified;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

public class ExecutorsRegistrar implements ComponentRegistrar {
    static final Lazy<ScheduledExecutorService> BG_EXECUTOR = new Lazy<>(new ExecutorsRegistrar$$ExternalSyntheticLambda0());
    static final Lazy<ScheduledExecutorService> BLOCKING_EXECUTOR = new Lazy<>(new ExecutorsRegistrar$$ExternalSyntheticLambda2());
    static final Lazy<ScheduledExecutorService> LITE_EXECUTOR = new Lazy<>(new ExecutorsRegistrar$$ExternalSyntheticLambda1());
    static final Lazy<ScheduledExecutorService> SCHEDULER = new Lazy<>(new ExecutorsRegistrar$$ExternalSyntheticLambda3());

    public List<Component<?>> getComponents() {
        return Arrays.asList(new Component[]{Component.builder(Qualified.qualified(Background.class, ScheduledExecutorService.class), (Qualified<? super T>[]) new Qualified[]{Qualified.qualified(Background.class, ExecutorService.class), Qualified.qualified(Background.class, Executor.class)}).factory(new ExecutorsRegistrar$$ExternalSyntheticLambda4()).build(), Component.builder(Qualified.qualified(Blocking.class, ScheduledExecutorService.class), (Qualified<? super T>[]) new Qualified[]{Qualified.qualified(Blocking.class, ExecutorService.class), Qualified.qualified(Blocking.class, Executor.class)}).factory(new ExecutorsRegistrar$$ExternalSyntheticLambda5()).build(), Component.builder(Qualified.qualified(Lightweight.class, ScheduledExecutorService.class), (Qualified<? super T>[]) new Qualified[]{Qualified.qualified(Lightweight.class, ExecutorService.class), Qualified.qualified(Lightweight.class, Executor.class)}).factory(new ExecutorsRegistrar$$ExternalSyntheticLambda6()).build(), Component.builder(Qualified.qualified(UiThread.class, Executor.class)).factory(new ExecutorsRegistrar$$ExternalSyntheticLambda7()).build()});
    }

    /* access modifiers changed from: private */
    public static ScheduledExecutorService scheduled(ExecutorService delegate) {
        return new DelegatingScheduledExecutorService(delegate, SCHEDULER.get());
    }

    private static ThreadFactory factory(String threadPrefix, int priority) {
        return new CustomThreadFactory(threadPrefix, priority, (StrictMode.ThreadPolicy) null);
    }

    private static ThreadFactory factory(String threadPrefix, int priority, StrictMode.ThreadPolicy policy) {
        return new CustomThreadFactory(threadPrefix, priority, policy);
    }

    private static StrictMode.ThreadPolicy bgPolicy() {
        StrictMode.ThreadPolicy.Builder builder = new StrictMode.ThreadPolicy.Builder().detectNetwork();
        builder.detectResourceMismatches();
        if (Build.VERSION.SDK_INT >= 26) {
            builder.detectUnbufferedIo();
        }
        return builder.penaltyLog().build();
    }

    private static StrictMode.ThreadPolicy litePolicy() {
        return new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build();
    }
}
