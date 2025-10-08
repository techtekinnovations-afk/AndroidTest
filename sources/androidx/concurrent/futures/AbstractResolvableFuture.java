package androidx.concurrent.futures;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.firestore.model.Values;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractResolvableFuture<V> implements ListenableFuture<V> {
    static final AtomicHelper ATOMIC_HELPER;
    static final boolean GENERATE_CANCELLATION_CAUSES = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));
    private static final Object NULL = new Object();
    private static final long SPIN_THRESHOLD_NANOS = 1000;
    private static final Logger log;
    volatile Listener listeners;
    volatile Object value;
    volatile Waiter waiters;

    static {
        AtomicHelper helper;
        Class<AbstractResolvableFuture> cls = AbstractResolvableFuture.class;
        log = Logger.getLogger(cls.getName());
        Throwable thrownAtomicReferenceFieldUpdaterFailure = null;
        try {
            helper = new SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(Waiter.class, Thread.class, "thread"), AtomicReferenceFieldUpdater.newUpdater(Waiter.class, Waiter.class, "next"), AtomicReferenceFieldUpdater.newUpdater(cls, Waiter.class, "waiters"), AtomicReferenceFieldUpdater.newUpdater(cls, Listener.class, "listeners"), AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, Values.VECTOR_MAP_VECTORS_KEY));
        } catch (Throwable atomicReferenceFieldUpdaterFailure) {
            thrownAtomicReferenceFieldUpdaterFailure = atomicReferenceFieldUpdaterFailure;
            helper = new SynchronizedHelper();
        }
        ATOMIC_HELPER = helper;
        Class<LockSupport> cls2 = LockSupport.class;
        if (thrownAtomicReferenceFieldUpdaterFailure != null) {
            log.log(Level.SEVERE, "SafeAtomicHelper is broken!", thrownAtomicReferenceFieldUpdaterFailure);
        }
    }

    private static final class Waiter {
        static final Waiter TOMBSTONE = new Waiter(false);
        volatile Waiter next;
        volatile Thread thread;

        Waiter(boolean unused) {
        }

        Waiter() {
            AbstractResolvableFuture.ATOMIC_HELPER.putThread(this, Thread.currentThread());
        }

        /* access modifiers changed from: package-private */
        public void setNext(Waiter next2) {
            AbstractResolvableFuture.ATOMIC_HELPER.putNext(this, next2);
        }

        /* access modifiers changed from: package-private */
        public void unpark() {
            Thread w = this.thread;
            if (w != null) {
                this.thread = null;
                LockSupport.unpark(w);
            }
        }
    }

    private void removeWaiter(Waiter node) {
        node.thread = null;
        while (true) {
            Waiter pred = null;
            Waiter curr = this.waiters;
            if (curr != Waiter.TOMBSTONE) {
                while (curr != null) {
                    Waiter succ = curr.next;
                    if (curr.thread != null) {
                        pred = curr;
                    } else if (pred != null) {
                        pred.next = succ;
                        if (pred.thread == null) {
                        }
                    } else if (!ATOMIC_HELPER.casWaiters(this, curr, succ)) {
                    }
                    curr = succ;
                }
                return;
            }
            return;
        }
    }

    private static final class Listener {
        static final Listener TOMBSTONE = new Listener((Runnable) null, (Executor) null);
        final Executor executor;
        Listener next;
        final Runnable task;

        Listener(Runnable task2, Executor executor2) {
            this.task = task2;
            this.executor = executor2;
        }
    }

    private static final class Failure {
        static final Failure FALLBACK_INSTANCE = new Failure(new Throwable("Failure occurred while trying to finish a future.") {
            public synchronized Throwable fillInStackTrace() {
                return this;
            }
        });
        final Throwable exception;

        Failure(Throwable exception2) {
            this.exception = (Throwable) AbstractResolvableFuture.checkNotNull(exception2);
        }
    }

    private static final class Cancellation {
        static final Cancellation CAUSELESS_CANCELLED;
        static final Cancellation CAUSELESS_INTERRUPTED;
        final Throwable cause;
        final boolean wasInterrupted;

        static {
            if (AbstractResolvableFuture.GENERATE_CANCELLATION_CAUSES) {
                CAUSELESS_CANCELLED = null;
                CAUSELESS_INTERRUPTED = null;
                return;
            }
            CAUSELESS_CANCELLED = new Cancellation(false, (Throwable) null);
            CAUSELESS_INTERRUPTED = new Cancellation(true, (Throwable) null);
        }

        Cancellation(boolean wasInterrupted2, Throwable cause2) {
            this.wasInterrupted = wasInterrupted2;
            this.cause = cause2;
        }
    }

    private static final class SetFuture<V> implements Runnable {
        final ListenableFuture<? extends V> future;
        final AbstractResolvableFuture<V> owner;

        SetFuture(AbstractResolvableFuture<V> owner2, ListenableFuture<? extends V> future2) {
            this.owner = owner2;
            this.future = future2;
        }

        public void run() {
            if (this.owner.value == this) {
                if (AbstractResolvableFuture.ATOMIC_HELPER.casValue(this.owner, this, AbstractResolvableFuture.getFutureValue(this.future))) {
                    AbstractResolvableFuture.complete(this.owner);
                }
            }
        }
    }

    protected AbstractResolvableFuture() {
    }

    public final V get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException, ExecutionException {
        long j;
        boolean z;
        long j2 = timeout;
        TimeUnit timeUnit = unit;
        long timeoutNanos = timeUnit.toNanos(j2);
        long remainingNanos = timeoutNanos;
        if (!Thread.interrupted()) {
            Object localValue = this.value;
            if ((localValue != null) && (!(localValue instanceof SetFuture))) {
                return getDoneValue(localValue);
            }
            long j3 = 0;
            long endNanos = remainingNanos > 0 ? System.nanoTime() + remainingNanos : 0;
            if (remainingNanos >= 1000) {
                Waiter oldHead = this.waiters;
                z = true;
                if (oldHead != Waiter.TOMBSTONE) {
                    Waiter node = new Waiter();
                    while (true) {
                        node.setNext(oldHead);
                        j = j3;
                        if (ATOMIC_HELPER.casWaiters(this, oldHead, node)) {
                            do {
                                LockSupport.parkNanos(this, remainingNanos);
                                if (!Thread.interrupted()) {
                                    Object localValue2 = this.value;
                                    if ((localValue2 != null) && (!(localValue2 instanceof SetFuture))) {
                                        return getDoneValue(localValue2);
                                    }
                                    remainingNanos = endNanos - System.nanoTime();
                                } else {
                                    removeWaiter(node);
                                    throw new InterruptedException();
                                }
                            } while (remainingNanos >= 1000);
                            removeWaiter(node);
                        } else {
                            oldHead = this.waiters;
                            if (oldHead == Waiter.TOMBSTONE) {
                                break;
                            }
                            j3 = j;
                        }
                    }
                }
                return getDoneValue(this.value);
            }
            z = true;
            j = 0;
            while (remainingNanos > j) {
                Object localValue3 = this.value;
                if ((localValue3 != null ? z : false) && (!(localValue3 instanceof SetFuture))) {
                    return getDoneValue(localValue3);
                }
                if (!Thread.interrupted()) {
                    remainingNanos = endNanos - System.nanoTime();
                } else {
                    throw new InterruptedException();
                }
            }
            String futureToString = toString();
            String unitString = timeUnit.toString().toLowerCase(Locale.ROOT);
            String message = "Waited " + j2 + " " + timeUnit.toString().toLowerCase(Locale.ROOT);
            if (remainingNanos + 1000 < j) {
                String message2 = message + " (plus ";
                long overWaitNanos = -remainingNanos;
                long j4 = timeoutNanos;
                long overWaitUnits = timeUnit.convert(overWaitNanos, TimeUnit.NANOSECONDS);
                long overWaitLeftoverNanos = overWaitNanos - timeUnit.toNanos(overWaitUnits);
                boolean shouldShowExtraNanos = (overWaitUnits == j || overWaitLeftoverNanos > 1000) ? z : false;
                if (overWaitUnits > j) {
                    String message3 = message2 + overWaitUnits + " " + unitString;
                    if (shouldShowExtraNanos) {
                        String str = message3;
                        message3 = message3 + ",";
                    } else {
                        String str2 = message3;
                    }
                    message2 = message3 + " ";
                }
                if (shouldShowExtraNanos) {
                    message2 = message2 + overWaitLeftoverNanos + " nanoseconds ";
                }
                message = message2 + "delay)";
            }
            if (isDone()) {
                throw new TimeoutException(message + " but future completed as timeout expired");
            }
            throw new TimeoutException(message + " for " + futureToString);
        }
        throw new InterruptedException();
    }

    public final V get() throws InterruptedException, ExecutionException {
        Object localValue;
        if (!Thread.interrupted()) {
            Object localValue2 = this.value;
            if ((localValue2 != null) && (!(localValue2 instanceof SetFuture))) {
                return getDoneValue(localValue2);
            }
            Waiter oldHead = this.waiters;
            if (oldHead != Waiter.TOMBSTONE) {
                Waiter node = new Waiter();
                do {
                    node.setNext(oldHead);
                    if (ATOMIC_HELPER.casWaiters(this, oldHead, node)) {
                        do {
                            LockSupport.park(this);
                            if (!Thread.interrupted()) {
                                localValue = this.value;
                            } else {
                                removeWaiter(node);
                                throw new InterruptedException();
                            }
                        } while (!((localValue != null) & (!(localValue instanceof SetFuture))));
                        return getDoneValue(localValue);
                    }
                    oldHead = this.waiters;
                } while (oldHead != Waiter.TOMBSTONE);
            }
            return getDoneValue(this.value);
        }
        throw new InterruptedException();
    }

    private V getDoneValue(Object obj) throws ExecutionException {
        if (obj instanceof Cancellation) {
            throw cancellationExceptionWithCause("Task was cancelled.", ((Cancellation) obj).cause);
        } else if (obj instanceof Failure) {
            throw new ExecutionException(((Failure) obj).exception);
        } else if (obj == NULL) {
            return null;
        } else {
            return obj;
        }
    }

    public final boolean isDone() {
        Object localValue = this.value;
        return (true ^ (localValue instanceof SetFuture)) & (localValue != null);
    }

    public final boolean isCancelled() {
        return this.value instanceof Cancellation;
    }

    /* JADX WARNING: type inference failed for: r6v6, types: [com.google.common.util.concurrent.ListenableFuture, com.google.common.util.concurrent.ListenableFuture<? extends V>] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean cancel(boolean r11) {
        /*
            r10 = this;
            java.lang.Object r0 = r10.value
            r1 = 0
            r2 = 1
            r3 = 0
            if (r0 != 0) goto L_0x0009
            r4 = r2
            goto L_0x000a
        L_0x0009:
            r4 = r3
        L_0x000a:
            boolean r5 = r0 instanceof androidx.concurrent.futures.AbstractResolvableFuture.SetFuture
            r4 = r4 | r5
            if (r4 == 0) goto L_0x0064
            boolean r4 = GENERATE_CANCELLATION_CAUSES
            if (r4 == 0) goto L_0x0020
            androidx.concurrent.futures.AbstractResolvableFuture$Cancellation r4 = new androidx.concurrent.futures.AbstractResolvableFuture$Cancellation
            java.util.concurrent.CancellationException r5 = new java.util.concurrent.CancellationException
            java.lang.String r6 = "Future.cancel() was called."
            r5.<init>(r6)
            r4.<init>(r11, r5)
            goto L_0x0027
        L_0x0020:
            if (r11 == 0) goto L_0x0025
            androidx.concurrent.futures.AbstractResolvableFuture$Cancellation r4 = androidx.concurrent.futures.AbstractResolvableFuture.Cancellation.CAUSELESS_INTERRUPTED
            goto L_0x0027
        L_0x0025:
            androidx.concurrent.futures.AbstractResolvableFuture$Cancellation r4 = androidx.concurrent.futures.AbstractResolvableFuture.Cancellation.CAUSELESS_CANCELLED
        L_0x0027:
            r5 = r10
        L_0x0029:
            androidx.concurrent.futures.AbstractResolvableFuture$AtomicHelper r6 = ATOMIC_HELPER
            boolean r6 = r6.casValue(r5, r0, r4)
            if (r6 == 0) goto L_0x005d
            r1 = 1
            if (r11 == 0) goto L_0x0037
            r5.interruptTask()
        L_0x0037:
            complete(r5)
            boolean r6 = r0 instanceof androidx.concurrent.futures.AbstractResolvableFuture.SetFuture
            if (r6 == 0) goto L_0x0064
            r6 = r0
            androidx.concurrent.futures.AbstractResolvableFuture$SetFuture r6 = (androidx.concurrent.futures.AbstractResolvableFuture.SetFuture) r6
            com.google.common.util.concurrent.ListenableFuture<? extends V> r6 = r6.future
            boolean r7 = r6 instanceof androidx.concurrent.futures.AbstractResolvableFuture
            if (r7 == 0) goto L_0x0059
            r7 = r6
            androidx.concurrent.futures.AbstractResolvableFuture r7 = (androidx.concurrent.futures.AbstractResolvableFuture) r7
            java.lang.Object r0 = r7.value
            if (r0 != 0) goto L_0x0050
            r8 = r2
            goto L_0x0051
        L_0x0050:
            r8 = r3
        L_0x0051:
            boolean r9 = r0 instanceof androidx.concurrent.futures.AbstractResolvableFuture.SetFuture
            r8 = r8 | r9
            if (r8 == 0) goto L_0x0058
            r5 = r7
            goto L_0x0029
        L_0x0058:
            goto L_0x005c
        L_0x0059:
            r6.cancel(r11)
        L_0x005c:
            goto L_0x0064
        L_0x005d:
            java.lang.Object r0 = r5.value
            boolean r6 = r0 instanceof androidx.concurrent.futures.AbstractResolvableFuture.SetFuture
            if (r6 != 0) goto L_0x0029
        L_0x0064:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.concurrent.futures.AbstractResolvableFuture.cancel(boolean):boolean");
    }

    /* access modifiers changed from: protected */
    public void interruptTask() {
    }

    /* access modifiers changed from: protected */
    public final boolean wasInterrupted() {
        Object localValue = this.value;
        return (localValue instanceof Cancellation) && ((Cancellation) localValue).wasInterrupted;
    }

    public final void addListener(Runnable listener, Executor executor) {
        checkNotNull(listener);
        checkNotNull(executor);
        Listener oldHead = this.listeners;
        if (oldHead != Listener.TOMBSTONE) {
            Listener newNode = new Listener(listener, executor);
            do {
                newNode.next = oldHead;
                if (!ATOMIC_HELPER.casListeners(this, oldHead, newNode)) {
                    oldHead = this.listeners;
                } else {
                    return;
                }
            } while (oldHead != Listener.TOMBSTONE);
        }
        executeListener(listener, executor);
    }

    /* access modifiers changed from: protected */
    public boolean set(V value2) {
        if (!ATOMIC_HELPER.casValue(this, (Object) null, value2 == null ? NULL : value2)) {
            return false;
        }
        complete(this);
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean setException(Throwable throwable) {
        if (!ATOMIC_HELPER.casValue(this, (Object) null, new Failure((Throwable) checkNotNull(throwable)))) {
            return false;
        }
        complete(this);
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean setFuture(ListenableFuture<? extends V> future) {
        SetFuture valueToSet;
        Failure failure;
        checkNotNull(future);
        Object localValue = this.value;
        if (localValue == null) {
            if (future.isDone()) {
                if (!ATOMIC_HELPER.casValue(this, (Object) null, getFutureValue(future))) {
                    return false;
                }
                complete(this);
                return true;
            }
            valueToSet = new SetFuture(this, future);
            if (ATOMIC_HELPER.casValue(this, (Object) null, valueToSet)) {
                try {
                    future.addListener(valueToSet, DirectExecutor.INSTANCE);
                } catch (Throwable th) {
                    failure = Failure.FALLBACK_INSTANCE;
                }
                return true;
            }
            localValue = this.value;
        }
        if (localValue instanceof Cancellation) {
            future.cancel(((Cancellation) localValue).wasInterrupted);
        }
        return false;
        ATOMIC_HELPER.casValue(this, valueToSet, failure);
        return true;
    }

    static Object getFutureValue(ListenableFuture<?> future) {
        Object v;
        if (future instanceof AbstractResolvableFuture) {
            Object v2 = ((AbstractResolvableFuture) future).value;
            if (!(v2 instanceof Cancellation)) {
                return v2;
            }
            Cancellation c = (Cancellation) v2;
            if (!c.wasInterrupted) {
                return v2;
            }
            if (c.cause != null) {
                v = new Cancellation(false, c.cause);
            } else {
                v = Cancellation.CAUSELESS_CANCELLED;
            }
            return v;
        }
        boolean wasCancelled = future.isCancelled();
        if ((!GENERATE_CANCELLATION_CAUSES) && wasCancelled) {
            return Cancellation.CAUSELESS_CANCELLED;
        }
        try {
            Object v3 = getUninterruptibly(future);
            return v3 == null ? NULL : v3;
        } catch (ExecutionException exception) {
            return new Failure(exception.getCause());
        } catch (CancellationException cancellation) {
            if (!wasCancelled) {
                return new Failure(new IllegalArgumentException("get() threw CancellationException, despite reporting isCancelled() == false: " + future, cancellation));
            }
            return new Cancellation(false, cancellation);
        } catch (Throwable t) {
            return new Failure(t);
        }
    }

    static <V> V getUninterruptibly(Future<V> future) throws ExecutionException {
        V v;
        boolean interrupted = false;
        while (true) {
            try {
                v = future.get();
                break;
            } catch (InterruptedException e) {
                interrupted = true;
            } catch (Throwable th) {
                if (interrupted) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (interrupted) {
            Thread.currentThread().interrupt();
        }
        return v;
    }

    static void complete(AbstractResolvableFuture<?> abstractResolvableFuture) {
        Listener next = null;
        AbstractResolvableFuture<V> future = abstractResolvableFuture;
        while (true) {
            future.releaseWaiters();
            future.afterDone();
            next = future.clearListeners(next);
            while (true) {
                if (next != null) {
                    Listener curr = next;
                    next = next.next;
                    Runnable task = curr.task;
                    if (task instanceof SetFuture) {
                        SetFuture<?> setFuture = (SetFuture) task;
                        AbstractResolvableFuture<V> abstractResolvableFuture2 = setFuture.owner;
                        if (abstractResolvableFuture2.value == setFuture) {
                            if (ATOMIC_HELPER.casValue(abstractResolvableFuture2, setFuture, getFutureValue(setFuture.future))) {
                                future = abstractResolvableFuture2;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        executeListener(task, curr.executor);
                    }
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void afterDone() {
    }

    /* access modifiers changed from: package-private */
    public final void maybePropagateCancellationTo(Future<?> related) {
        if ((related != null) && isCancelled()) {
            related.cancel(wasInterrupted());
        }
    }

    private void releaseWaiters() {
        Waiter head;
        do {
            head = this.waiters;
        } while (!ATOMIC_HELPER.casWaiters(this, head, Waiter.TOMBSTONE));
        for (Waiter currentWaiter = head; currentWaiter != null; currentWaiter = currentWaiter.next) {
            currentWaiter.unpark();
        }
    }

    private Listener clearListeners(Listener onto) {
        Listener head;
        do {
            head = this.listeners;
        } while (!ATOMIC_HELPER.casListeners(this, head, Listener.TOMBSTONE));
        Listener reversedList = onto;
        while (head != null) {
            Listener tmp = head;
            head = head.next;
            tmp.next = reversedList;
            reversedList = tmp;
        }
        return reversedList;
    }

    public String toString() {
        String pendingDescription;
        StringBuilder builder = new StringBuilder().append(super.toString()).append("[status=");
        if (isCancelled()) {
            builder.append("CANCELLED");
        } else if (isDone()) {
            addDoneString(builder);
        } else {
            try {
                pendingDescription = pendingToString();
            } catch (RuntimeException e) {
                pendingDescription = "Exception thrown from implementation: " + e.getClass();
            }
            if (pendingDescription != null && !pendingDescription.isEmpty()) {
                builder.append("PENDING, info=[").append(pendingDescription).append("]");
            } else if (isDone()) {
                addDoneString(builder);
            } else {
                builder.append("PENDING");
            }
        }
        return builder.append("]").toString();
    }

    /* access modifiers changed from: protected */
    public String pendingToString() {
        Object localValue = this.value;
        if (localValue instanceof SetFuture) {
            return "setFuture=[" + userObjectToString(((SetFuture) localValue).future) + "]";
        }
        if (this instanceof ScheduledFuture) {
            return "remaining delay=[" + ((ScheduledFuture) this).getDelay(TimeUnit.MILLISECONDS) + " ms]";
        }
        return null;
    }

    private void addDoneString(StringBuilder builder) {
        try {
            builder.append("SUCCESS, result=[").append(userObjectToString(getUninterruptibly(this))).append("]");
        } catch (ExecutionException e) {
            builder.append("FAILURE, cause=[").append(e.getCause()).append("]");
        } catch (CancellationException e2) {
            builder.append("CANCELLED");
        } catch (RuntimeException e3) {
            builder.append("UNKNOWN, cause=[").append(e3.getClass()).append(" thrown from get()]");
        }
    }

    private String userObjectToString(Object o) {
        if (o == this) {
            return "this future";
        }
        return String.valueOf(o);
    }

    private static void executeListener(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (RuntimeException e) {
            log.log(Level.SEVERE, "RuntimeException while executing runnable " + runnable + " with executor " + executor, e);
        }
    }

    private static abstract class AtomicHelper {
        /* access modifiers changed from: package-private */
        public abstract boolean casListeners(AbstractResolvableFuture<?> abstractResolvableFuture, Listener listener, Listener listener2);

        /* access modifiers changed from: package-private */
        public abstract boolean casValue(AbstractResolvableFuture<?> abstractResolvableFuture, Object obj, Object obj2);

        /* access modifiers changed from: package-private */
        public abstract boolean casWaiters(AbstractResolvableFuture<?> abstractResolvableFuture, Waiter waiter, Waiter waiter2);

        /* access modifiers changed from: package-private */
        public abstract void putNext(Waiter waiter, Waiter waiter2);

        /* access modifiers changed from: package-private */
        public abstract void putThread(Waiter waiter, Thread thread);

        private AtomicHelper() {
        }
    }

    private static final class SafeAtomicHelper extends AtomicHelper {
        final AtomicReferenceFieldUpdater<AbstractResolvableFuture, Listener> listenersUpdater;
        final AtomicReferenceFieldUpdater<AbstractResolvableFuture, Object> valueUpdater;
        final AtomicReferenceFieldUpdater<Waiter, Waiter> waiterNextUpdater;
        final AtomicReferenceFieldUpdater<Waiter, Thread> waiterThreadUpdater;
        final AtomicReferenceFieldUpdater<AbstractResolvableFuture, Waiter> waitersUpdater;

        SafeAtomicHelper(AtomicReferenceFieldUpdater<Waiter, Thread> waiterThreadUpdater2, AtomicReferenceFieldUpdater<Waiter, Waiter> waiterNextUpdater2, AtomicReferenceFieldUpdater<AbstractResolvableFuture, Waiter> waitersUpdater2, AtomicReferenceFieldUpdater<AbstractResolvableFuture, Listener> listenersUpdater2, AtomicReferenceFieldUpdater<AbstractResolvableFuture, Object> valueUpdater2) {
            super();
            this.waiterThreadUpdater = waiterThreadUpdater2;
            this.waiterNextUpdater = waiterNextUpdater2;
            this.waitersUpdater = waitersUpdater2;
            this.listenersUpdater = listenersUpdater2;
            this.valueUpdater = valueUpdater2;
        }

        /* access modifiers changed from: package-private */
        public void putThread(Waiter waiter, Thread newValue) {
            this.waiterThreadUpdater.lazySet(waiter, newValue);
        }

        /* access modifiers changed from: package-private */
        public void putNext(Waiter waiter, Waiter newValue) {
            this.waiterNextUpdater.lazySet(waiter, newValue);
        }

        /* access modifiers changed from: package-private */
        public boolean casWaiters(AbstractResolvableFuture<?> future, Waiter expect, Waiter update) {
            return AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(this.waitersUpdater, future, expect, update);
        }

        /* access modifiers changed from: package-private */
        public boolean casListeners(AbstractResolvableFuture<?> future, Listener expect, Listener update) {
            return AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(this.listenersUpdater, future, expect, update);
        }

        /* access modifiers changed from: package-private */
        public boolean casValue(AbstractResolvableFuture<?> future, Object expect, Object update) {
            return AbstractResolvableFuture$SafeAtomicHelper$$ExternalSyntheticBackportWithForwarding0.m(this.valueUpdater, future, expect, update);
        }
    }

    private static final class SynchronizedHelper extends AtomicHelper {
        SynchronizedHelper() {
            super();
        }

        /* access modifiers changed from: package-private */
        public void putThread(Waiter waiter, Thread newValue) {
            waiter.thread = newValue;
        }

        /* access modifiers changed from: package-private */
        public void putNext(Waiter waiter, Waiter newValue) {
            waiter.next = newValue;
        }

        /* access modifiers changed from: package-private */
        public boolean casWaiters(AbstractResolvableFuture<?> future, Waiter expect, Waiter update) {
            synchronized (future) {
                if (future.waiters != expect) {
                    return false;
                }
                future.waiters = update;
                return true;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean casListeners(AbstractResolvableFuture<?> future, Listener expect, Listener update) {
            synchronized (future) {
                if (future.listeners != expect) {
                    return false;
                }
                future.listeners = update;
                return true;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean casValue(AbstractResolvableFuture<?> future, Object expect, Object update) {
            synchronized (future) {
                if (future.value != expect) {
                    return false;
                }
                future.value = update;
                return true;
            }
        }
    }

    private static CancellationException cancellationExceptionWithCause(String message, Throwable cause) {
        CancellationException exception = new CancellationException(message);
        exception.initCause(cause);
        return exception;
    }

    static <T> T checkNotNull(T reference) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException();
    }
}
