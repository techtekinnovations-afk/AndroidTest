package com.google.firebase.concurrent;

import com.google.firebase.concurrent.DelegatingScheduledFuture;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class DelegatingScheduledExecutorService implements ScheduledExecutorService {
    private final ExecutorService delegate;
    private final ScheduledExecutorService scheduler;

    DelegatingScheduledExecutorService(ExecutorService delegate2, ScheduledExecutorService scheduler2) {
        this.delegate = delegate2;
        this.scheduler = scheduler2;
    }

    public void shutdown() {
        throw new UnsupportedOperationException("Shutting down is not allowed.");
    }

    public List<Runnable> shutdownNow() {
        throw new UnsupportedOperationException("Shutting down is not allowed.");
    }

    public boolean isShutdown() {
        return this.delegate.isShutdown();
    }

    public boolean isTerminated() {
        return this.delegate.isTerminated();
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return this.delegate.awaitTermination(timeout, unit);
    }

    public <T> Future<T> submit(Callable<T> task) {
        return this.delegate.submit(task);
    }

    public <T> Future<T> submit(Runnable task, T result) {
        return this.delegate.submit(task, result);
    }

    public Future<?> submit(Runnable task) {
        return this.delegate.submit(task);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return this.delegate.invokeAll(tasks);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return this.delegate.invokeAll(tasks, timeout, unit);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws ExecutionException, InterruptedException {
        return this.delegate.invokeAny(tasks);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
        return this.delegate.invokeAny(tasks, timeout, unit);
    }

    public void execute(Runnable command) {
        this.delegate.execute(command);
    }

    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return new DelegatingScheduledFuture(new DelegatingScheduledExecutorService$$ExternalSyntheticLambda0(this, command, delay, unit));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$schedule$2$com-google-firebase-concurrent-DelegatingScheduledExecutorService  reason: not valid java name */
    public /* synthetic */ ScheduledFuture m1770lambda$schedule$2$comgooglefirebaseconcurrentDelegatingScheduledExecutorService(Runnable command, long delay, TimeUnit unit, DelegatingScheduledFuture.Completer completer) {
        return this.scheduler.schedule(new DelegatingScheduledExecutorService$$ExternalSyntheticLambda4(this, command, completer), delay, unit);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$schedule$1$com-google-firebase-concurrent-DelegatingScheduledExecutorService  reason: not valid java name */
    public /* synthetic */ void m1769lambda$schedule$1$comgooglefirebaseconcurrentDelegatingScheduledExecutorService(Runnable command, DelegatingScheduledFuture.Completer completer) {
        this.delegate.execute(new DelegatingScheduledExecutorService$$ExternalSyntheticLambda6(command, completer));
    }

    static /* synthetic */ void lambda$schedule$0(Runnable command, DelegatingScheduledFuture.Completer completer) {
        try {
            command.run();
            completer.set(null);
        } catch (Exception ex) {
            completer.setException(ex);
        }
    }

    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return new DelegatingScheduledFuture(new DelegatingScheduledExecutorService$$ExternalSyntheticLambda8(this, callable, delay, unit));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$schedule$5$com-google-firebase-concurrent-DelegatingScheduledExecutorService  reason: not valid java name */
    public /* synthetic */ ScheduledFuture m1772lambda$schedule$5$comgooglefirebaseconcurrentDelegatingScheduledExecutorService(Callable callable, long delay, TimeUnit unit, DelegatingScheduledFuture.Completer completer) {
        return this.scheduler.schedule(new DelegatingScheduledExecutorService$$ExternalSyntheticLambda10(this, callable, completer), delay, unit);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$schedule$4$com-google-firebase-concurrent-DelegatingScheduledExecutorService  reason: not valid java name */
    public /* synthetic */ Future m1771lambda$schedule$4$comgooglefirebaseconcurrentDelegatingScheduledExecutorService(Callable callable, DelegatingScheduledFuture.Completer completer) throws Exception {
        return this.delegate.submit(new DelegatingScheduledExecutorService$$ExternalSyntheticLambda9(callable, completer));
    }

    static /* synthetic */ void lambda$schedule$3(Callable callable, DelegatingScheduledFuture.Completer completer) {
        try {
            completer.set(callable.call());
        } catch (Exception ex) {
            completer.setException(ex);
        }
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return new DelegatingScheduledFuture(new DelegatingScheduledExecutorService$$ExternalSyntheticLambda5(this, command, initialDelay, period, unit));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$scheduleAtFixedRate$8$com-google-firebase-concurrent-DelegatingScheduledExecutorService  reason: not valid java name */
    public /* synthetic */ ScheduledFuture m1774lambda$scheduleAtFixedRate$8$comgooglefirebaseconcurrentDelegatingScheduledExecutorService(Runnable command, long initialDelay, long period, TimeUnit unit, DelegatingScheduledFuture.Completer completer) {
        return this.scheduler.scheduleAtFixedRate(new DelegatingScheduledExecutorService$$ExternalSyntheticLambda3(this, command, completer), initialDelay, period, unit);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$scheduleAtFixedRate$7$com-google-firebase-concurrent-DelegatingScheduledExecutorService  reason: not valid java name */
    public /* synthetic */ void m1773lambda$scheduleAtFixedRate$7$comgooglefirebaseconcurrentDelegatingScheduledExecutorService(Runnable command, DelegatingScheduledFuture.Completer completer) {
        this.delegate.execute(new DelegatingScheduledExecutorService$$ExternalSyntheticLambda11(command, completer));
    }

    static /* synthetic */ void lambda$scheduleAtFixedRate$6(Runnable command, DelegatingScheduledFuture.Completer completer) {
        try {
            command.run();
        } catch (Exception ex) {
            completer.setException(ex);
            throw ex;
        }
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return new DelegatingScheduledFuture(new DelegatingScheduledExecutorService$$ExternalSyntheticLambda7(this, command, initialDelay, delay, unit));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$scheduleWithFixedDelay$11$com-google-firebase-concurrent-DelegatingScheduledExecutorService  reason: not valid java name */
    public /* synthetic */ ScheduledFuture m1776lambda$scheduleWithFixedDelay$11$comgooglefirebaseconcurrentDelegatingScheduledExecutorService(Runnable command, long initialDelay, long delay, TimeUnit unit, DelegatingScheduledFuture.Completer completer) {
        return this.scheduler.scheduleWithFixedDelay(new DelegatingScheduledExecutorService$$ExternalSyntheticLambda2(this, command, completer), initialDelay, delay, unit);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$scheduleWithFixedDelay$10$com-google-firebase-concurrent-DelegatingScheduledExecutorService  reason: not valid java name */
    public /* synthetic */ void m1775lambda$scheduleWithFixedDelay$10$comgooglefirebaseconcurrentDelegatingScheduledExecutorService(Runnable command, DelegatingScheduledFuture.Completer completer) {
        this.delegate.execute(new DelegatingScheduledExecutorService$$ExternalSyntheticLambda1(command, completer));
    }

    static /* synthetic */ void lambda$scheduleWithFixedDelay$9(Runnable command, DelegatingScheduledFuture.Completer completer) {
        try {
            command.run();
        } catch (Exception ex) {
            completer.setException(ex);
        }
    }
}
