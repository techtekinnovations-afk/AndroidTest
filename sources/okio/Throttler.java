package okio;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007\b\u0016¢\u0006\u0002\u0010\u0002B\u000f\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u001d\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0004H\u0000¢\u0006\u0002\b\u0014J$\u0010\u0006\u001a\u00020\u00152\u0006\u0010\u0006\u001a\u00020\u00042\b\b\u0002\u0010\u0010\u001a\u00020\u00042\b\b\u0002\u0010\u000f\u001a\u00020\u0004H\u0007J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0018\u001a\u00020\u0019J\u0015\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0004H\u0000¢\u0006\u0002\b\u001bJ\f\u0010\u001c\u001a\u00020\u0004*\u00020\u0004H\u0002J\f\u0010\u001d\u001a\u00020\u0004*\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0004X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lokio/Throttler;", "", "()V", "allocatedUntil", "", "(J)V", "bytesPerSecond", "condition", "Ljava/util/concurrent/locks/Condition;", "getCondition", "()Ljava/util/concurrent/locks/Condition;", "lock", "Ljava/util/concurrent/locks/ReentrantLock;", "getLock", "()Ljava/util/concurrent/locks/ReentrantLock;", "maxByteCount", "waitByteCount", "byteCountOrWaitNanos", "now", "byteCount", "byteCountOrWaitNanos$okio", "", "sink", "Lokio/Sink;", "source", "Lokio/Source;", "take", "take$okio", "bytesToNanos", "nanosToBytes", "okio"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: Throttler.kt */
public final class Throttler {
    private long allocatedUntil;
    private long bytesPerSecond;
    private final Condition condition;
    private final ReentrantLock lock;
    private long maxByteCount;
    private long waitByteCount;

    public final void bytesPerSecond(long j) {
        bytesPerSecond$default(this, j, 0, 0, 6, (Object) null);
    }

    public final void bytesPerSecond(long j, long j2) {
        bytesPerSecond$default(this, j, j2, 0, 4, (Object) null);
    }

    public Throttler(long allocatedUntil2) {
        this.allocatedUntil = allocatedUntil2;
        this.waitByteCount = 8192;
        this.maxByteCount = 262144;
        this.lock = new ReentrantLock();
        Condition newCondition = this.lock.newCondition();
        Intrinsics.checkNotNullExpressionValue(newCondition, "lock.newCondition()");
        this.condition = newCondition;
    }

    public final ReentrantLock getLock() {
        return this.lock;
    }

    public final Condition getCondition() {
        return this.condition;
    }

    public Throttler() {
        this(System.nanoTime());
    }

    public static /* synthetic */ void bytesPerSecond$default(Throttler throttler, long j, long j2, long j3, int i, Object obj) {
        long j4;
        long j5;
        if ((i & 2) != 0) {
            j4 = throttler.waitByteCount;
        } else {
            j4 = j2;
        }
        if ((i & 4) != 0) {
            j5 = throttler.maxByteCount;
        } else {
            j5 = j3;
        }
        throttler.bytesPerSecond(j, j4, j5);
    }

    public final void bytesPerSecond(long bytesPerSecond2, long waitByteCount2, long maxByteCount2) {
        Lock lock2 = this.lock;
        lock2.lock();
        boolean z = true;
        if (bytesPerSecond2 >= 0) {
            if (waitByteCount2 > 0) {
                if (maxByteCount2 < waitByteCount2) {
                    z = false;
                }
                if (z) {
                    try {
                        this.bytesPerSecond = bytesPerSecond2;
                        this.waitByteCount = waitByteCount2;
                        this.maxByteCount = maxByteCount2;
                        this.condition.signalAll();
                        Unit unit = Unit.INSTANCE;
                    } finally {
                        lock2.unlock();
                    }
                } else {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
            } else {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
        } else {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    public final long take$okio(long byteCount) {
        if (byteCount > 0) {
            Lock lock2 = this.lock;
            lock2.lock();
            while (true) {
                try {
                    long byteCountOrWaitNanos = byteCountOrWaitNanos$okio(System.nanoTime(), byteCount);
                    if (byteCountOrWaitNanos >= 0) {
                        return byteCountOrWaitNanos;
                    }
                    this.condition.awaitNanos(-byteCountOrWaitNanos);
                } finally {
                    lock2.unlock();
                }
            }
        } else {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    public final long byteCountOrWaitNanos$okio(long now, long byteCount) {
        if (this.bytesPerSecond == 0) {
            return byteCount;
        }
        long idleInNanos = Math.max(this.allocatedUntil - now, 0);
        long immediateBytes = this.maxByteCount - nanosToBytes(idleInNanos);
        if (immediateBytes >= byteCount) {
            this.allocatedUntil = now + idleInNanos + bytesToNanos(byteCount);
            return byteCount;
        } else if (immediateBytes >= this.waitByteCount) {
            this.allocatedUntil = bytesToNanos(this.maxByteCount) + now;
            return immediateBytes;
        } else {
            long minByteCount = Math.min(this.waitByteCount, byteCount);
            long minWaitNanos = bytesToNanos(minByteCount - this.maxByteCount) + idleInNanos;
            if (minWaitNanos != 0) {
                return -minWaitNanos;
            }
            this.allocatedUntil = bytesToNanos(this.maxByteCount) + now;
            return minByteCount;
        }
    }

    private final long nanosToBytes(long $this$nanosToBytes) {
        return (this.bytesPerSecond * $this$nanosToBytes) / 1000000000;
    }

    private final long bytesToNanos(long $this$bytesToNanos) {
        return (1000000000 * $this$bytesToNanos) / this.bytesPerSecond;
    }

    public final Source source(Source source) {
        Intrinsics.checkNotNullParameter(source, "source");
        return new Throttler$source$1(source, this);
    }

    public final Sink sink(Sink sink) {
        Intrinsics.checkNotNullParameter(sink, "sink");
        return new Throttler$sink$1(sink, this);
    }
}
