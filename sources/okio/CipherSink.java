package okio;

import java.io.IOException;
import javax.crypto.Cipher;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\r\u001a\u00020\u000eH\u0016J\n\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u000eH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0018\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0018\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u0018H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lokio/CipherSink;", "Lokio/Sink;", "sink", "Lokio/BufferedSink;", "cipher", "Ljavax/crypto/Cipher;", "(Lokio/BufferedSink;Ljavax/crypto/Cipher;)V", "blockSize", "", "getCipher", "()Ljavax/crypto/Cipher;", "closed", "", "close", "", "doFinal", "", "flush", "timeout", "Lokio/Timeout;", "update", "source", "Lokio/Buffer;", "remaining", "", "write", "byteCount", "okio"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: CipherSink.kt */
public final class CipherSink implements Sink {
    private final int blockSize = this.cipher.getBlockSize();
    private final Cipher cipher;
    private boolean closed;
    private final BufferedSink sink;

    public CipherSink(BufferedSink sink2, Cipher cipher2) {
        Intrinsics.checkNotNullParameter(sink2, "sink");
        Intrinsics.checkNotNullParameter(cipher2, "cipher");
        this.sink = sink2;
        this.cipher = cipher2;
        if (!(this.blockSize > 0)) {
            throw new IllegalArgumentException(("Block cipher required " + this.cipher).toString());
        }
    }

    public final Cipher getCipher() {
        return this.cipher;
    }

    public void write(Buffer source, long byteCount) throws IOException {
        Intrinsics.checkNotNullParameter(source, "source");
        long byteCount2 = byteCount;
        SegmentedByteString.checkOffsetAndCount(source.size(), 0, byteCount2);
        if (!this.closed) {
            long remaining = byteCount2;
            while (remaining > 0) {
                remaining -= (long) update(source, remaining);
            }
            return;
        }
        throw new IllegalStateException("closed".toString());
    }

    private final int update(Buffer source, long remaining) {
        Segment head = source.head;
        Intrinsics.checkNotNull(head);
        int size = (int) Math.min(remaining, (long) (head.limit - head.pos));
        Buffer buffer = this.sink.getBuffer();
        int outputSize = this.cipher.getOutputSize(size);
        int size2 = size;
        while (outputSize > 8192) {
            if (size2 <= this.blockSize) {
                BufferedSink bufferedSink = this.sink;
                byte[] update = this.cipher.update(source.readByteArray(remaining));
                Intrinsics.checkNotNullExpressionValue(update, "cipher.update(source.readByteArray(remaining))");
                bufferedSink.write(update);
                return (int) remaining;
            }
            size2 -= this.blockSize;
            outputSize = this.cipher.getOutputSize(size2);
        }
        Segment s = buffer.writableSegment$okio(outputSize);
        int ciphered = this.cipher.update(head.data, head.pos, size2, s.data, s.limit);
        s.limit += ciphered;
        buffer.setSize$okio(buffer.size() + ((long) ciphered));
        if (s.pos == s.limit) {
            buffer.head = s.pop();
            SegmentPool.recycle(s);
        }
        this.sink.emitCompleteSegments();
        source.setSize$okio(source.size() - ((long) size2));
        head.pos += size2;
        if (head.pos == head.limit) {
            source.head = head.pop();
            SegmentPool.recycle(head);
        }
        return size2;
    }

    public void flush() {
        this.sink.flush();
    }

    public Timeout timeout() {
        return this.sink.timeout();
    }

    public void close() throws IOException {
        if (!this.closed) {
            this.closed = true;
            Throwable thrown = doFinal();
            try {
                this.sink.close();
            } catch (Throwable e) {
                if (thrown == null) {
                    thrown = e;
                }
            }
            if (thrown != null) {
                throw thrown;
            }
        }
    }

    private final Throwable doFinal() {
        int outputSize = this.cipher.getOutputSize(0);
        if (outputSize == 0) {
            return null;
        }
        if (outputSize > 8192) {
            try {
                BufferedSink bufferedSink = this.sink;
                byte[] doFinal = this.cipher.doFinal();
                Intrinsics.checkNotNullExpressionValue(doFinal, "cipher.doFinal()");
                bufferedSink.write(doFinal);
                return null;
            } catch (Throwable t) {
                return t;
            }
        } else {
            Throwable thrown = null;
            Buffer buffer = this.sink.getBuffer();
            Segment s = buffer.writableSegment$okio(outputSize);
            try {
                int ciphered = this.cipher.doFinal(s.data, s.limit);
                s.limit += ciphered;
                buffer.setSize$okio(buffer.size() + ((long) ciphered));
            } catch (Throwable e) {
                thrown = e;
            }
            if (s.pos == s.limit) {
                buffer.head = s.pop();
                SegmentPool.recycle(s);
            }
            return thrown;
        }
    }
}
