package okio;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\b\u0010\u0006\u001a\u00020\u0005H\u0016J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\u0018\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"okio/Pipe$sink$1", "Lokio/Sink;", "timeout", "Lokio/Timeout;", "close", "", "flush", "write", "source", "Lokio/Buffer;", "byteCount", "", "okio"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: Pipe.kt */
public final class Pipe$sink$1 implements Sink {
    final /* synthetic */ Pipe this$0;
    private final Timeout timeout = new Timeout();

    Pipe$sink$1(Pipe $receiver) {
        this.this$0 = $receiver;
    }

    public void write(Buffer source, long byteCount) {
        Buffer buffer = source;
        Intrinsics.checkNotNullParameter(buffer, "source");
        Object delegate = null;
        Lock lock = this.this$0.getLock();
        Pipe pipe = this.this$0;
        lock.lock();
        try {
            if (pipe.getSinkClosed$okio()) {
                throw new IllegalStateException("closed".toString());
            } else if (!pipe.getCanceled$okio()) {
                long byteCount2 = byteCount;
                while (true) {
                    if (byteCount2 > 0) {
                        try {
                            Object it = pipe.getFoldedSink$okio();
                            if (it != null) {
                                delegate = it;
                                break;
                            } else if (!pipe.getSourceClosed$okio()) {
                                long bufferSpaceAvailable = pipe.getMaxBufferSize$okio() - pipe.getBuffer$okio().size();
                                if (bufferSpaceAvailable == 0) {
                                    this.timeout.awaitSignal(pipe.getCondition());
                                    if (pipe.getCanceled$okio()) {
                                        throw new IOException("canceled");
                                    }
                                } else {
                                    long bytesToWrite = Math.min(bufferSpaceAvailable, byteCount2);
                                    pipe.getBuffer$okio().write(buffer, bytesToWrite);
                                    byteCount2 -= bytesToWrite;
                                    pipe.getCondition().signalAll();
                                }
                            } else {
                                throw new IOException("source is closed");
                            }
                        } catch (Throwable th) {
                            th = th;
                            lock.unlock();
                            throw th;
                        }
                    }
                }
                try {
                    Unit unit = Unit.INSTANCE;
                    lock.unlock();
                    if (delegate != null) {
                        Pipe this_$iv = this.this$0;
                        Sink $this$forward$iv = delegate;
                        Timeout this_$iv$iv = $this$forward$iv.timeout();
                        Timeout other$iv$iv = this_$iv.sink().timeout();
                        long originalTimeout$iv$iv = this_$iv$iv.timeoutNanos();
                        Sink sink = delegate;
                        Pipe pipe2 = this_$iv;
                        this_$iv$iv.timeout(Timeout.Companion.minTimeout(other$iv$iv.timeoutNanos(), this_$iv$iv.timeoutNanos()), TimeUnit.NANOSECONDS);
                        if (this_$iv$iv.hasDeadline()) {
                            long originalDeadline$iv$iv = this_$iv$iv.deadlineNanoTime();
                            if (other$iv$iv.hasDeadline()) {
                                this_$iv$iv.deadlineNanoTime(Math.min(this_$iv$iv.deadlineNanoTime(), other$iv$iv.deadlineNanoTime()));
                            }
                            try {
                                $this$forward$iv.write(buffer, byteCount2);
                                Unit unit2 = Unit.INSTANCE;
                            } finally {
                                this_$iv$iv.timeout(originalTimeout$iv$iv, TimeUnit.NANOSECONDS);
                                if (other$iv$iv.hasDeadline()) {
                                    this_$iv$iv.deadlineNanoTime(originalDeadline$iv$iv);
                                }
                            }
                        } else {
                            if (other$iv$iv.hasDeadline()) {
                                this_$iv$iv.deadlineNanoTime(other$iv$iv.deadlineNanoTime());
                            }
                            try {
                                $this$forward$iv.write(buffer, byteCount2);
                                Unit unit3 = Unit.INSTANCE;
                            } finally {
                                this_$iv$iv.timeout(originalTimeout$iv$iv, TimeUnit.NANOSECONDS);
                                if (other$iv$iv.hasDeadline()) {
                                    this_$iv$iv.clearDeadline();
                                }
                            }
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    Object obj = delegate;
                    lock.unlock();
                    throw th;
                }
            } else {
                throw new IOException("canceled");
            }
        } catch (Throwable th3) {
            th = th3;
            long j = byteCount;
            lock.unlock();
            throw th;
        }
    }

    public void flush() {
        Sink sink = null;
        Lock lock = this.this$0.getLock();
        Pipe pipe = this.this$0;
        lock.lock();
        try {
            if (pipe.getSinkClosed$okio()) {
                throw new IllegalStateException("closed".toString());
            } else if (!pipe.getCanceled$okio()) {
                Sink it = pipe.getFoldedSink$okio();
                if (it != null) {
                    sink = it;
                } else if (pipe.getSourceClosed$okio()) {
                    if (pipe.getBuffer$okio().size() > 0) {
                        throw new IOException("source is closed");
                    }
                }
                Unit unit = Unit.INSTANCE;
                if (sink != null) {
                    Pipe this_$iv = this.this$0;
                    Sink $this$forward$iv = sink;
                    Timeout this_$iv$iv = $this$forward$iv.timeout();
                    Timeout other$iv$iv = this_$iv.sink().timeout();
                    long originalTimeout$iv$iv = this_$iv$iv.timeoutNanos();
                    this_$iv$iv.timeout(Timeout.Companion.minTimeout(other$iv$iv.timeoutNanos(), this_$iv$iv.timeoutNanos()), TimeUnit.NANOSECONDS);
                    if (this_$iv$iv.hasDeadline()) {
                        long originalDeadline$iv$iv = this_$iv$iv.deadlineNanoTime();
                        if (other$iv$iv.hasDeadline()) {
                            this_$iv$iv.deadlineNanoTime(Math.min(this_$iv$iv.deadlineNanoTime(), other$iv$iv.deadlineNanoTime()));
                        }
                        try {
                            $this$forward$iv.flush();
                            Unit unit2 = Unit.INSTANCE;
                        } finally {
                            this_$iv$iv.timeout(originalTimeout$iv$iv, TimeUnit.NANOSECONDS);
                            if (other$iv$iv.hasDeadline()) {
                                this_$iv$iv.deadlineNanoTime(originalDeadline$iv$iv);
                            }
                        }
                    } else {
                        if (other$iv$iv.hasDeadline()) {
                            this_$iv$iv.deadlineNanoTime(other$iv$iv.deadlineNanoTime());
                        }
                        try {
                            $this$forward$iv.flush();
                            Unit unit3 = Unit.INSTANCE;
                        } finally {
                            this_$iv$iv.timeout(originalTimeout$iv$iv, TimeUnit.NANOSECONDS);
                            if (other$iv$iv.hasDeadline()) {
                                this_$iv$iv.clearDeadline();
                            }
                        }
                    }
                }
            } else {
                throw new IOException("canceled");
            }
        } finally {
            lock.unlock();
        }
    }

    public void close() {
        Sink sink = null;
        Lock lock = this.this$0.getLock();
        Pipe pipe = this.this$0;
        lock.lock();
        try {
            if (!pipe.getSinkClosed$okio()) {
                Sink it = pipe.getFoldedSink$okio();
                if (it != null) {
                    sink = it;
                } else {
                    if (pipe.getSourceClosed$okio()) {
                        if (pipe.getBuffer$okio().size() > 0) {
                            throw new IOException("source is closed");
                        }
                    }
                    pipe.setSinkClosed$okio(true);
                    pipe.getCondition().signalAll();
                }
                Unit unit = Unit.INSTANCE;
                lock.unlock();
                if (sink != null) {
                    Pipe this_$iv = this.this$0;
                    Sink $this$forward$iv = sink;
                    Timeout this_$iv$iv = $this$forward$iv.timeout();
                    Timeout other$iv$iv = this_$iv.sink().timeout();
                    long originalTimeout$iv$iv = this_$iv$iv.timeoutNanos();
                    this_$iv$iv.timeout(Timeout.Companion.minTimeout(other$iv$iv.timeoutNanos(), this_$iv$iv.timeoutNanos()), TimeUnit.NANOSECONDS);
                    if (this_$iv$iv.hasDeadline()) {
                        long originalDeadline$iv$iv = this_$iv$iv.deadlineNanoTime();
                        if (other$iv$iv.hasDeadline()) {
                            this_$iv$iv.deadlineNanoTime(Math.min(this_$iv$iv.deadlineNanoTime(), other$iv$iv.deadlineNanoTime()));
                        }
                        try {
                            $this$forward$iv.close();
                            Unit unit2 = Unit.INSTANCE;
                        } finally {
                            this_$iv$iv.timeout(originalTimeout$iv$iv, TimeUnit.NANOSECONDS);
                            if (other$iv$iv.hasDeadline()) {
                                this_$iv$iv.deadlineNanoTime(originalDeadline$iv$iv);
                            }
                        }
                    } else {
                        if (other$iv$iv.hasDeadline()) {
                            this_$iv$iv.deadlineNanoTime(other$iv$iv.deadlineNanoTime());
                        }
                        try {
                            $this$forward$iv.close();
                            Unit unit3 = Unit.INSTANCE;
                        } finally {
                            this_$iv$iv.timeout(originalTimeout$iv$iv, TimeUnit.NANOSECONDS);
                            if (other$iv$iv.hasDeadline()) {
                                this_$iv$iv.clearDeadline();
                            }
                        }
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public Timeout timeout() {
        return this.timeout;
    }
}
