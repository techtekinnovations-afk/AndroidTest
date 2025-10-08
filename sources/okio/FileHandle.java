package okio;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u00002\u00060\u0001j\u0002`\u0002:\u0002-.B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0014\u001a\u00020\u0013J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0011J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019J\b\u0010\u001a\u001a\u00020\u0013H$J\b\u0010\u001b\u001a\u00020\u0013H$J(\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\r2\u0006\u0010!\u001a\u00020\rH$J\u0010\u0010\"\u001a\u00020\u00132\u0006\u0010#\u001a\u00020\u0016H$J\b\u0010$\u001a\u00020\u0016H$J(\u0010%\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\r2\u0006\u0010!\u001a\u00020\rH$J&\u0010&\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\r2\u0006\u0010!\u001a\u00020\rJ\u001e\u0010&\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020'2\u0006\u0010!\u001a\u00020\u0016J \u0010(\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020'2\u0006\u0010!\u001a\u00020\u0016H\u0002J\u0016\u0010)\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0016J\u0016\u0010)\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010*\u001a\u00020\u00132\u0006\u0010#\u001a\u00020\u0016J\u0010\u0010\u0017\u001a\u00020\u00112\b\b\u0002\u0010\u001d\u001a\u00020\u0016J\u0006\u0010#\u001a\u00020\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001d\u001a\u00020\u0016J&\u0010+\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\r2\u0006\u0010!\u001a\u00020\rJ\u001e\u0010+\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020'2\u0006\u0010!\u001a\u00020\u0016J \u0010,\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020'2\u0006\u0010!\u001a\u00020\u0016H\u0002R\u000e\u0010\u0006\u001a\u00020\u0004X\u000e¢\u0006\u0002\n\u0000R\u0015\u0010\u0007\u001a\u00060\bj\u0002`\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006/"}, d2 = {"Lokio/FileHandle;", "Ljava/io/Closeable;", "Lokio/Closeable;", "readWrite", "", "(Z)V", "closed", "lock", "Ljava/util/concurrent/locks/ReentrantLock;", "Lokio/Lock;", "getLock", "()Ljava/util/concurrent/locks/ReentrantLock;", "openStreamCount", "", "getReadWrite", "()Z", "appendingSink", "Lokio/Sink;", "close", "", "flush", "position", "", "sink", "source", "Lokio/Source;", "protectedClose", "protectedFlush", "protectedRead", "fileOffset", "array", "", "arrayOffset", "byteCount", "protectedResize", "size", "protectedSize", "protectedWrite", "read", "Lokio/Buffer;", "readNoCloseCheck", "reposition", "resize", "write", "writeNoCloseCheck", "FileHandleSink", "FileHandleSource", "okio"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: FileHandle.kt */
public abstract class FileHandle implements Closeable {
    /* access modifiers changed from: private */
    public boolean closed;
    private final ReentrantLock lock = _JvmPlatformKt.newLock();
    /* access modifiers changed from: private */
    public int openStreamCount;
    private final boolean readWrite;

    /* access modifiers changed from: protected */
    public abstract void protectedClose() throws IOException;

    /* access modifiers changed from: protected */
    public abstract void protectedFlush() throws IOException;

    /* access modifiers changed from: protected */
    public abstract int protectedRead(long j, byte[] bArr, int i, int i2) throws IOException;

    /* access modifiers changed from: protected */
    public abstract void protectedResize(long j) throws IOException;

    /* access modifiers changed from: protected */
    public abstract long protectedSize() throws IOException;

    /* access modifiers changed from: protected */
    public abstract void protectedWrite(long j, byte[] bArr, int i, int i2) throws IOException;

    public FileHandle(boolean readWrite2) {
        this.readWrite = readWrite2;
    }

    public final boolean getReadWrite() {
        return this.readWrite;
    }

    public final ReentrantLock getLock() {
        return this.lock;
    }

    /* JADX INFO: finally extract failed */
    public final int read(long fileOffset, byte[] array, int arrayOffset, int byteCount) throws IOException {
        Intrinsics.checkNotNullParameter(array, "array");
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            if (!this.closed) {
                Unit unit = Unit.INSTANCE;
                lock2.unlock();
                return protectedRead(fileOffset, array, arrayOffset, byteCount);
            }
            throw new IllegalStateException("closed".toString());
        } catch (Throwable th) {
            lock2.unlock();
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    public final long read(long fileOffset, Buffer sink, long byteCount) throws IOException {
        Intrinsics.checkNotNullParameter(sink, "sink");
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            if (!this.closed) {
                Unit unit = Unit.INSTANCE;
                lock2.unlock();
                return readNoCloseCheck(fileOffset, sink, byteCount);
            }
            throw new IllegalStateException("closed".toString());
        } catch (Throwable th) {
            lock2.unlock();
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    public final long size() throws IOException {
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            if (!this.closed) {
                Unit unit = Unit.INSTANCE;
                lock2.unlock();
                return protectedSize();
            }
            throw new IllegalStateException("closed".toString());
        } catch (Throwable th) {
            lock2.unlock();
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    public final void resize(long size) throws IOException {
        if (this.readWrite) {
            Lock lock2 = this.lock;
            lock2.lock();
            try {
                if (!this.closed) {
                    Unit unit = Unit.INSTANCE;
                    lock2.unlock();
                    protectedResize(size);
                    return;
                }
                throw new IllegalStateException("closed".toString());
            } catch (Throwable th) {
                lock2.unlock();
                throw th;
            }
        } else {
            throw new IllegalStateException("file handle is read-only".toString());
        }
    }

    /* JADX INFO: finally extract failed */
    public final void write(long fileOffset, byte[] array, int arrayOffset, int byteCount) {
        Intrinsics.checkNotNullParameter(array, "array");
        if (this.readWrite) {
            Lock lock2 = this.lock;
            lock2.lock();
            try {
                if (!this.closed) {
                    Unit unit = Unit.INSTANCE;
                    lock2.unlock();
                    protectedWrite(fileOffset, array, arrayOffset, byteCount);
                    return;
                }
                throw new IllegalStateException("closed".toString());
            } catch (Throwable th) {
                lock2.unlock();
                throw th;
            }
        } else {
            throw new IllegalStateException("file handle is read-only".toString());
        }
    }

    /* JADX INFO: finally extract failed */
    public final void write(long fileOffset, Buffer source, long byteCount) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        if (this.readWrite) {
            Lock lock2 = this.lock;
            lock2.lock();
            try {
                if (!this.closed) {
                    Unit unit = Unit.INSTANCE;
                    lock2.unlock();
                    writeNoCloseCheck(fileOffset, source, byteCount);
                    return;
                }
                throw new IllegalStateException("closed".toString());
            } catch (Throwable th) {
                lock2.unlock();
                throw th;
            }
        } else {
            throw new IllegalStateException("file handle is read-only".toString());
        }
    }

    /* JADX INFO: finally extract failed */
    public final void flush() throws IOException {
        if (this.readWrite) {
            Lock lock2 = this.lock;
            lock2.lock();
            try {
                if (!this.closed) {
                    Unit unit = Unit.INSTANCE;
                    lock2.unlock();
                    protectedFlush();
                    return;
                }
                throw new IllegalStateException("closed".toString());
            } catch (Throwable th) {
                lock2.unlock();
                throw th;
            }
        } else {
            throw new IllegalStateException("file handle is read-only".toString());
        }
    }

    public static /* synthetic */ Source source$default(FileHandle fileHandle, long j, int i, Object obj) throws IOException {
        if (obj == null) {
            if ((i & 1) != 0) {
                j = 0;
            }
            return fileHandle.source(j);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: source");
    }

    /* JADX INFO: finally extract failed */
    public final Source source(long fileOffset) throws IOException {
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            if (!this.closed) {
                this.openStreamCount++;
                lock2.unlock();
                return new FileHandleSource(this, fileOffset);
            }
            throw new IllegalStateException("closed".toString());
        } catch (Throwable th) {
            lock2.unlock();
            throw th;
        }
    }

    public final long position(Source source) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        Source source2 = source;
        long bufferSize = 0;
        if (source2 instanceof RealBufferedSource) {
            bufferSize = ((RealBufferedSource) source2).bufferField.size();
            source2 = ((RealBufferedSource) source2).source;
        }
        if (!((source2 instanceof FileHandleSource) && ((FileHandleSource) source2).getFileHandle() == this)) {
            throw new IllegalArgumentException("source was not created by this FileHandle".toString());
        } else if (!((FileHandleSource) source2).getClosed()) {
            return ((FileHandleSource) source2).getPosition() - bufferSize;
        } else {
            throw new IllegalStateException("closed".toString());
        }
    }

    public final void reposition(Source source, long position) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        boolean z = true;
        if (source instanceof RealBufferedSource) {
            Source fileHandleSource = ((RealBufferedSource) source).source;
            if (!((fileHandleSource instanceof FileHandleSource) && ((FileHandleSource) fileHandleSource).getFileHandle() == this)) {
                throw new IllegalArgumentException("source was not created by this FileHandle".toString());
            } else if (!((FileHandleSource) fileHandleSource).getClosed()) {
                long bufferSize = ((RealBufferedSource) source).bufferField.size();
                long toSkip = position - (((FileHandleSource) fileHandleSource).getPosition() - bufferSize);
                if (0 > toSkip || toSkip >= bufferSize) {
                    z = false;
                }
                if (z) {
                    ((RealBufferedSource) source).skip(toSkip);
                    return;
                }
                ((RealBufferedSource) source).bufferField.clear();
                ((FileHandleSource) fileHandleSource).setPosition(position);
            } else {
                throw new IllegalStateException("closed".toString());
            }
        } else {
            if (!(source instanceof FileHandleSource) || ((FileHandleSource) source).getFileHandle() != this) {
                z = false;
            }
            if (!z) {
                throw new IllegalArgumentException("source was not created by this FileHandle".toString());
            } else if (!((FileHandleSource) source).getClosed()) {
                ((FileHandleSource) source).setPosition(position);
            } else {
                throw new IllegalStateException("closed".toString());
            }
        }
    }

    public static /* synthetic */ Sink sink$default(FileHandle fileHandle, long j, int i, Object obj) throws IOException {
        if (obj == null) {
            if ((i & 1) != 0) {
                j = 0;
            }
            return fileHandle.sink(j);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: sink");
    }

    /* JADX INFO: finally extract failed */
    public final Sink sink(long fileOffset) throws IOException {
        if (this.readWrite) {
            Lock lock2 = this.lock;
            lock2.lock();
            try {
                if (!this.closed) {
                    this.openStreamCount++;
                    lock2.unlock();
                    return new FileHandleSink(this, fileOffset);
                }
                throw new IllegalStateException("closed".toString());
            } catch (Throwable th) {
                lock2.unlock();
                throw th;
            }
        } else {
            throw new IllegalStateException("file handle is read-only".toString());
        }
    }

    public final Sink appendingSink() throws IOException {
        return sink(size());
    }

    public final long position(Sink sink) throws IOException {
        Intrinsics.checkNotNullParameter(sink, "sink");
        Sink sink2 = sink;
        long bufferSize = 0;
        if (sink2 instanceof RealBufferedSink) {
            bufferSize = ((RealBufferedSink) sink2).bufferField.size();
            sink2 = ((RealBufferedSink) sink2).sink;
        }
        if (!((sink2 instanceof FileHandleSink) && ((FileHandleSink) sink2).getFileHandle() == this)) {
            throw new IllegalArgumentException("sink was not created by this FileHandle".toString());
        } else if (!((FileHandleSink) sink2).getClosed()) {
            return ((FileHandleSink) sink2).getPosition() + bufferSize;
        } else {
            throw new IllegalStateException("closed".toString());
        }
    }

    public final void reposition(Sink sink, long position) throws IOException {
        Intrinsics.checkNotNullParameter(sink, "sink");
        boolean z = true;
        if (sink instanceof RealBufferedSink) {
            Sink fileHandleSink = ((RealBufferedSink) sink).sink;
            if (!(fileHandleSink instanceof FileHandleSink) || ((FileHandleSink) fileHandleSink).getFileHandle() != this) {
                z = false;
            }
            if (!z) {
                throw new IllegalArgumentException("sink was not created by this FileHandle".toString());
            } else if (!((FileHandleSink) fileHandleSink).getClosed()) {
                ((RealBufferedSink) sink).emit();
                ((FileHandleSink) fileHandleSink).setPosition(position);
            } else {
                throw new IllegalStateException("closed".toString());
            }
        } else {
            if (!(sink instanceof FileHandleSink) || ((FileHandleSink) sink).getFileHandle() != this) {
                z = false;
            }
            if (!z) {
                throw new IllegalArgumentException("sink was not created by this FileHandle".toString());
            } else if (!((FileHandleSink) sink).getClosed()) {
                ((FileHandleSink) sink).setPosition(position);
            } else {
                throw new IllegalStateException("closed".toString());
            }
        }
    }

    public final void close() throws IOException {
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            if (!this.closed) {
                this.closed = true;
                if (this.openStreamCount != 0) {
                    lock2.unlock();
                    return;
                }
                Unit unit = Unit.INSTANCE;
                lock2.unlock();
                protectedClose();
            }
        } finally {
            lock2.unlock();
        }
    }

    /* access modifiers changed from: private */
    public final long readNoCloseCheck(long fileOffset, Buffer sink, long byteCount) {
        Buffer buffer = sink;
        long j = byteCount;
        if (j >= 0) {
            long targetOffset = fileOffset + j;
            long currentOffset = fileOffset;
            while (true) {
                if (currentOffset >= targetOffset) {
                    break;
                }
                Segment tail = buffer.writableSegment$okio(1);
                long targetOffset2 = targetOffset;
                int readByteCount = protectedRead(currentOffset, tail.data, tail.limit, (int) Math.min(targetOffset - currentOffset, (long) (8192 - tail.limit)));
                if (readByteCount == -1) {
                    if (tail.pos == tail.limit) {
                        buffer.head = tail.pop();
                        SegmentPool.recycle(tail);
                    }
                    if (fileOffset == currentOffset) {
                        return -1;
                    }
                } else {
                    tail.limit += readByteCount;
                    currentOffset += (long) readByteCount;
                    buffer.setSize$okio(buffer.size() + ((long) readByteCount));
                    targetOffset = targetOffset2;
                }
            }
            return currentOffset - fileOffset;
        }
        throw new IllegalArgumentException(("byteCount < 0: " + j).toString());
    }

    /* access modifiers changed from: private */
    public final void writeNoCloseCheck(long fileOffset, Buffer source, long byteCount) {
        Buffer buffer = source;
        SegmentedByteString.checkOffsetAndCount(buffer.size(), 0, byteCount);
        long targetOffset = fileOffset + byteCount;
        long currentOffset = fileOffset;
        while (currentOffset < targetOffset) {
            Segment head = buffer.head;
            Intrinsics.checkNotNull(head);
            int toCopy = (int) Math.min(targetOffset - currentOffset, (long) (head.limit - head.pos));
            protectedWrite(currentOffset, head.data, head.pos, toCopy);
            head.pos += toCopy;
            currentOffset += (long) toCopy;
            buffer.setSize$okio(buffer.size() - ((long) toCopy));
            if (head.pos == head.limit) {
                buffer.head = head.pop();
                SegmentPool.recycle(head);
            }
        }
    }

    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0014H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u0018\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0005H\u0016R\u001a\u0010\u0007\u001a\u00020\bX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0004\u001a\u00020\u0005X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u001c"}, d2 = {"Lokio/FileHandle$FileHandleSink;", "Lokio/Sink;", "fileHandle", "Lokio/FileHandle;", "position", "", "(Lokio/FileHandle;J)V", "closed", "", "getClosed", "()Z", "setClosed", "(Z)V", "getFileHandle", "()Lokio/FileHandle;", "getPosition", "()J", "setPosition", "(J)V", "close", "", "flush", "timeout", "Lokio/Timeout;", "write", "source", "Lokio/Buffer;", "byteCount", "okio"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* compiled from: FileHandle.kt */
    private static final class FileHandleSink implements Sink {
        private boolean closed;
        private final FileHandle fileHandle;
        private long position;

        public FileHandleSink(FileHandle fileHandle2, long position2) {
            Intrinsics.checkNotNullParameter(fileHandle2, "fileHandle");
            this.fileHandle = fileHandle2;
            this.position = position2;
        }

        public final FileHandle getFileHandle() {
            return this.fileHandle;
        }

        public final long getPosition() {
            return this.position;
        }

        public final void setPosition(long j) {
            this.position = j;
        }

        public final boolean getClosed() {
            return this.closed;
        }

        public final void setClosed(boolean z) {
            this.closed = z;
        }

        public void write(Buffer source, long byteCount) {
            Intrinsics.checkNotNullParameter(source, "source");
            if (!this.closed) {
                long byteCount2 = byteCount;
                this.fileHandle.writeNoCloseCheck(this.position, source, byteCount2);
                this.position += byteCount2;
                return;
            }
            long j = byteCount;
            throw new IllegalStateException("closed".toString());
        }

        public void flush() {
            if (!this.closed) {
                this.fileHandle.protectedFlush();
                return;
            }
            throw new IllegalStateException("closed".toString());
        }

        public Timeout timeout() {
            return Timeout.NONE;
        }

        public void close() {
            if (!this.closed) {
                this.closed = true;
                Lock lock = this.fileHandle.getLock();
                lock.lock();
                try {
                    FileHandle fileHandle2 = this.fileHandle;
                    fileHandle2.openStreamCount = fileHandle2.openStreamCount - 1;
                    if (this.fileHandle.openStreamCount == 0) {
                        if (this.fileHandle.closed) {
                            Unit unit = Unit.INSTANCE;
                            lock.unlock();
                            this.fileHandle.protectedClose();
                        }
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0005H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016R\u001a\u0010\u0007\u001a\u00020\bX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0004\u001a\u00020\u0005X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u001b"}, d2 = {"Lokio/FileHandle$FileHandleSource;", "Lokio/Source;", "fileHandle", "Lokio/FileHandle;", "position", "", "(Lokio/FileHandle;J)V", "closed", "", "getClosed", "()Z", "setClosed", "(Z)V", "getFileHandle", "()Lokio/FileHandle;", "getPosition", "()J", "setPosition", "(J)V", "close", "", "read", "sink", "Lokio/Buffer;", "byteCount", "timeout", "Lokio/Timeout;", "okio"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* compiled from: FileHandle.kt */
    private static final class FileHandleSource implements Source {
        private boolean closed;
        private final FileHandle fileHandle;
        private long position;

        public FileHandleSource(FileHandle fileHandle2, long position2) {
            Intrinsics.checkNotNullParameter(fileHandle2, "fileHandle");
            this.fileHandle = fileHandle2;
            this.position = position2;
        }

        public final FileHandle getFileHandle() {
            return this.fileHandle;
        }

        public final long getPosition() {
            return this.position;
        }

        public final void setPosition(long j) {
            this.position = j;
        }

        public final boolean getClosed() {
            return this.closed;
        }

        public final void setClosed(boolean z) {
            this.closed = z;
        }

        public long read(Buffer sink, long byteCount) {
            Intrinsics.checkNotNullParameter(sink, "sink");
            if (!this.closed) {
                long result = this.fileHandle.readNoCloseCheck(this.position, sink, byteCount);
                if (result != -1) {
                    this.position += result;
                }
                return result;
            }
            long j = byteCount;
            throw new IllegalStateException("closed".toString());
        }

        public Timeout timeout() {
            return Timeout.NONE;
        }

        public void close() {
            if (!this.closed) {
                this.closed = true;
                Lock lock = this.fileHandle.getLock();
                lock.lock();
                try {
                    FileHandle fileHandle2 = this.fileHandle;
                    fileHandle2.openStreamCount = fileHandle2.openStreamCount - 1;
                    if (this.fileHandle.openStreamCount == 0) {
                        if (this.fileHandle.closed) {
                            Unit unit = Unit.INSTANCE;
                            lock.unlock();
                            this.fileHandle.protectedClose();
                        }
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
