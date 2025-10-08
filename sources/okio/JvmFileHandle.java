package okio;

import java.io.RandomAccessFile;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0007\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0014J\b\u0010\t\u001a\u00020\bH\u0014J(\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000bH\u0014J\u0010\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\rH\u0014J\b\u0010\u0014\u001a\u00020\rH\u0014J(\u0010\u0015\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000bH\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lokio/JvmFileHandle;", "Lokio/FileHandle;", "readWrite", "", "randomAccessFile", "Ljava/io/RandomAccessFile;", "(ZLjava/io/RandomAccessFile;)V", "protectedClose", "", "protectedFlush", "protectedRead", "", "fileOffset", "", "array", "", "arrayOffset", "byteCount", "protectedResize", "size", "protectedSize", "protectedWrite", "okio"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: JvmFileHandle.kt */
public final class JvmFileHandle extends FileHandle {
    private final RandomAccessFile randomAccessFile;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public JvmFileHandle(boolean readWrite, RandomAccessFile randomAccessFile2) {
        super(readWrite);
        Intrinsics.checkNotNullParameter(randomAccessFile2, "randomAccessFile");
        this.randomAccessFile = randomAccessFile2;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void protectedResize(long r9) {
        /*
            r8 = this;
            monitor-enter(r8)
            long r0 = r8.size()     // Catch:{ all -> 0x0020 }
            r3 = r0
            long r0 = r9 - r3
            r5 = 0
            int r2 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r2 <= 0) goto L_0x0018
            int r2 = (int) r0     // Catch:{ all -> 0x0020 }
            byte[] r5 = new byte[r2]     // Catch:{ all -> 0x0020 }
            r6 = 0
            int r7 = (int) r0
            r2 = r8
            r2.protectedWrite(r3, r5, r6, r7)     // Catch:{ all -> 0x0025 }
            goto L_0x001e
        L_0x0018:
            r2 = r8
            java.io.RandomAccessFile r5 = r2.randomAccessFile     // Catch:{ all -> 0x0025 }
            r5.setLength(r9)     // Catch:{ all -> 0x0025 }
        L_0x001e:
            monitor-exit(r8)
            return
        L_0x0020:
            r0 = move-exception
            r2 = r8
        L_0x0022:
            r9 = r0
            monitor-exit(r8)     // Catch:{ all -> 0x0025 }
            throw r9
        L_0x0025:
            r0 = move-exception
            goto L_0x0022
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.JvmFileHandle.protectedResize(long):void");
    }

    /* access modifiers changed from: protected */
    public synchronized long protectedSize() {
        return this.randomAccessFile.length();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0021, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized int protectedRead(long r4, byte[] r6, int r7, int r8) {
        /*
            r3 = this;
            monitor-enter(r3)
            java.lang.String r0 = "array"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)     // Catch:{ all -> 0x0022 }
            java.io.RandomAccessFile r0 = r3.randomAccessFile     // Catch:{ all -> 0x0022 }
            r0.seek(r4)     // Catch:{ all -> 0x0022 }
            r0 = 0
        L_0x000c:
            if (r0 >= r8) goto L_0x0020
            java.io.RandomAccessFile r1 = r3.randomAccessFile     // Catch:{ all -> 0x0022 }
            int r2 = r8 - r0
            int r1 = r1.read(r6, r7, r2)     // Catch:{ all -> 0x0022 }
            r2 = -1
            if (r1 != r2) goto L_0x001e
            if (r0 != 0) goto L_0x001d
            monitor-exit(r3)
            return r2
        L_0x001d:
            goto L_0x0020
        L_0x001e:
            int r0 = r0 + r1
            goto L_0x000c
        L_0x0020:
            monitor-exit(r3)
            return r0
        L_0x0022:
            r4 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0022 }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.JvmFileHandle.protectedRead(long, byte[], int, int):int");
    }

    /* access modifiers changed from: protected */
    public synchronized void protectedWrite(long fileOffset, byte[] array, int arrayOffset, int byteCount) {
        Intrinsics.checkNotNullParameter(array, "array");
        this.randomAccessFile.seek(fileOffset);
        this.randomAccessFile.write(array, arrayOffset, byteCount);
    }

    /* access modifiers changed from: protected */
    public synchronized void protectedFlush() {
        this.randomAccessFile.getFD().sync();
    }

    /* access modifiers changed from: protected */
    public synchronized void protectedClose() {
        this.randomAccessFile.close();
    }
}
