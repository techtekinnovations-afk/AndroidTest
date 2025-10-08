package com.google.common.base.internal;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;

public class Finalizer implements Runnable {
    private static final String FINALIZABLE_REFERENCE = "com.google.common.base.FinalizableReference";
    @CheckForNull
    private static final Constructor<Thread> bigThreadConstructor = getBigThreadConstructor();
    @CheckForNull
    private static final Field inheritableThreadLocals = (bigThreadConstructor == null ? getInheritableThreadLocalsField() : null);
    private static final Logger logger = Logger.getLogger(Finalizer.class.getName());
    private final WeakReference<Class<?>> finalizableReferenceClassReference;
    private final PhantomReference<Object> frqReference;
    private final ReferenceQueue<Object> queue;

    public static void startFinalizer(Class<?> finalizableReferenceClass, ReferenceQueue<Object> queue2, PhantomReference<Object> frqReference2) {
        if (finalizableReferenceClass.getName().equals(FINALIZABLE_REFERENCE)) {
            Finalizer finalizer = new Finalizer(finalizableReferenceClass, queue2, frqReference2);
            String threadName = Finalizer.class.getName();
            Thread thread = null;
            if (bigThreadConstructor != null) {
                try {
                    ThreadGroup threadGroup = null;
                    thread = bigThreadConstructor.newInstance(new Object[]{null, finalizer, threadName, 0L, false});
                } catch (Throwable t) {
                    logger.log(Level.INFO, "Failed to create a thread without inherited thread-local values", t);
                }
            }
            if (thread == null) {
                ThreadGroup threadGroup2 = null;
                thread = new Thread((ThreadGroup) null, finalizer, threadName);
            }
            thread.setDaemon(true);
            try {
                if (inheritableThreadLocals != null) {
                    inheritableThreadLocals.set(thread, (Object) null);
                }
            } catch (Throwable t2) {
                logger.log(Level.INFO, "Failed to clear thread local values inherited by reference finalizer thread.", t2);
            }
            thread.start();
            return;
        }
        throw new IllegalArgumentException("Expected com.google.common.base.FinalizableReference.");
    }

    private Finalizer(Class<?> finalizableReferenceClass, ReferenceQueue<Object> queue2, PhantomReference<Object> frqReference2) {
        this.queue = queue2;
        this.finalizableReferenceClassReference = new WeakReference<>(finalizableReferenceClass);
        this.frqReference = frqReference2;
    }

    public void run() {
        while (cleanUp(this.queue.remove())) {
        }
    }

    private boolean cleanUp(Reference<?> firstReference) {
        Reference<? extends Object> poll;
        Method finalizeReferentMethod = getFinalizeReferentMethod();
        if (finalizeReferentMethod == null || !finalizeReference(firstReference, finalizeReferentMethod)) {
            return false;
        }
        do {
            poll = this.queue.poll();
            if (poll == null) {
                return true;
            }
        } while (finalizeReference(poll, finalizeReferentMethod));
        return false;
    }

    private boolean finalizeReference(Reference<?> reference, Method finalizeReferentMethod) {
        reference.clear();
        if (reference == this.frqReference) {
            return false;
        }
        try {
            finalizeReferentMethod.invoke(reference, new Object[0]);
            return true;
        } catch (Throwable t) {
            logger.log(Level.SEVERE, "Error cleaning up after reference.", t);
            return true;
        }
    }

    @CheckForNull
    private Method getFinalizeReferentMethod() {
        Class<?> finalizableReferenceClass = (Class) this.finalizableReferenceClassReference.get();
        if (finalizableReferenceClass == null) {
            return null;
        }
        try {
            return finalizableReferenceClass.getMethod("finalizeReferent", new Class[0]);
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }

    @CheckForNull
    private static Field getInheritableThreadLocalsField() {
        try {
            Field inheritableThreadLocals2 = Thread.class.getDeclaredField("inheritableThreadLocals");
            inheritableThreadLocals2.setAccessible(true);
            return inheritableThreadLocals2;
        } catch (Throwable th) {
            logger.log(Level.INFO, "Couldn't access Thread.inheritableThreadLocals. Reference finalizer threads will inherit thread local values.");
            return null;
        }
    }

    @CheckForNull
    private static Constructor<Thread> getBigThreadConstructor() {
        try {
            return Thread.class.getConstructor(new Class[]{ThreadGroup.class, Runnable.class, String.class, Long.TYPE, Boolean.TYPE});
        } catch (Throwable th) {
            return null;
        }
    }
}
