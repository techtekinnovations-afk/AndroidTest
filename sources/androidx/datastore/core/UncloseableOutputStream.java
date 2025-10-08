package androidx.datastore.core;

import java.io.FileOutputStream;
import java.io.OutputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016J\u0010\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH\u0016J \u0010\n\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\u0010\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u000fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Landroidx/datastore/core/UncloseableOutputStream;", "Ljava/io/OutputStream;", "fileOutputStream", "Ljava/io/FileOutputStream;", "(Ljava/io/FileOutputStream;)V", "getFileOutputStream", "()Ljava/io/FileOutputStream;", "close", "", "flush", "write", "b", "", "bytes", "off", "", "len", "datastore-core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: UncloseableOutputStream.kt */
public final class UncloseableOutputStream extends OutputStream {
    private final FileOutputStream fileOutputStream;

    public UncloseableOutputStream(FileOutputStream fileOutputStream2) {
        Intrinsics.checkNotNullParameter(fileOutputStream2, "fileOutputStream");
        this.fileOutputStream = fileOutputStream2;
    }

    public final FileOutputStream getFileOutputStream() {
        return this.fileOutputStream;
    }

    public void write(int b) {
        this.fileOutputStream.write(b);
    }

    public void write(byte[] b) {
        Intrinsics.checkNotNullParameter(b, "b");
        this.fileOutputStream.write(b);
    }

    public void write(byte[] bytes, int off, int len) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        this.fileOutputStream.write(bytes, off, len);
    }

    public void close() {
    }

    public void flush() {
        this.fileOutputStream.flush();
    }
}
