package androidx.datastore.core.okio;

import androidx.datastore.core.ReadScope;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import okio.FileSystem;
import okio.Path;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b¢\u0006\u0002\u0010\tJ\b\u0010\u0012\u001a\u00020\u0013H\u0004J\b\u0010\u0014\u001a\u00020\u0013H\u0016J\u000e\u0010\u0015\u001a\u00028\u0000H@¢\u0006\u0002\u0010\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bX\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0017"}, d2 = {"Landroidx/datastore/core/okio/OkioReadScope;", "T", "Landroidx/datastore/core/ReadScope;", "fileSystem", "Lokio/FileSystem;", "path", "Lokio/Path;", "serializer", "Landroidx/datastore/core/okio/OkioSerializer;", "(Lokio/FileSystem;Lokio/Path;Landroidx/datastore/core/okio/OkioSerializer;)V", "closed", "Landroidx/datastore/core/okio/AtomicBoolean;", "getFileSystem", "()Lokio/FileSystem;", "getPath", "()Lokio/Path;", "getSerializer", "()Landroidx/datastore/core/okio/OkioSerializer;", "checkClose", "", "close", "readData", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "datastore-core-okio"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: OkioStorage.kt */
public class OkioReadScope<T> implements ReadScope<T> {
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final FileSystem fileSystem;
    private final Path path;
    private final OkioSerializer<T> serializer;

    public Object readData(Continuation<? super T> continuation) {
        return readData$suspendImpl(this, continuation);
    }

    public OkioReadScope(FileSystem fileSystem2, Path path2, OkioSerializer<T> serializer2) {
        Intrinsics.checkNotNullParameter(fileSystem2, "fileSystem");
        Intrinsics.checkNotNullParameter(path2, "path");
        Intrinsics.checkNotNullParameter(serializer2, "serializer");
        this.fileSystem = fileSystem2;
        this.path = path2;
        this.serializer = serializer2;
    }

    /* access modifiers changed from: protected */
    public final FileSystem getFileSystem() {
        return this.fileSystem;
    }

    /* access modifiers changed from: protected */
    public final Path getPath() {
        return this.path;
    }

    /* access modifiers changed from: protected */
    public final OkioSerializer<T> getSerializer() {
        return this.serializer;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0095, code lost:
        if (r7 == null) goto L_0x009f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        r7.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x009b, code lost:
        r14 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x009c, code lost:
        r5 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00fc, code lost:
        if (r5 == null) goto L_0x011b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0102, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0103, code lost:
        r6 = r2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0040  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x005a  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00a9 A[SYNTHETIC, Splitter:B:40:0x00a9] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00b8 A[Catch:{ FileNotFoundException -> 0x00bf, all -> 0x0107 }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00be A[Catch:{ FileNotFoundException -> 0x00bf, all -> 0x0107 }] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0110 A[SYNTHETIC, Splitter:B:67:0x0110] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x011d  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0122  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ <T> java.lang.Object readData$suspendImpl(androidx.datastore.core.okio.OkioReadScope<T> r14, kotlin.coroutines.Continuation<? super T> r15) {
        /*
            boolean r0 = r15 instanceof androidx.datastore.core.okio.OkioReadScope$readData$1
            if (r0 == 0) goto L_0x0014
            r0 = r15
            androidx.datastore.core.okio.OkioReadScope$readData$1 r0 = (androidx.datastore.core.okio.OkioReadScope$readData$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r15 = r0.label
            int r15 = r15 - r2
            r0.label = r15
            goto L_0x0019
        L_0x0014:
            androidx.datastore.core.okio.OkioReadScope$readData$1 r0 = new androidx.datastore.core.okio.OkioReadScope$readData$1
            r0.<init>(r14, r15)
        L_0x0019:
            java.lang.Object r15 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x005a;
                case 1: goto L_0x0040;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r15 = "call to 'resume' before 'invoke' with coroutine"
            r14.<init>(r15)
            throw r14
        L_0x002d:
            r14 = 0
            r1 = 0
            r2 = 0
            r4 = 0
            java.lang.Object r5 = r0.L$0
            java.io.Closeable r5 = (java.io.Closeable) r5
            kotlin.ResultKt.throwOnFailure(r15)     // Catch:{ all -> 0x003d }
            r6 = r3
            r10 = r4
            r4 = r15
            goto L_0x00f8
        L_0x003d:
            r2 = move-exception
            goto L_0x010c
        L_0x0040:
            r14 = 0
            r2 = 0
            r4 = 0
            r5 = 0
            java.lang.Object r6 = r0.L$1
            java.io.Closeable r6 = (java.io.Closeable) r6
            java.lang.Object r7 = r0.L$0
            androidx.datastore.core.okio.OkioReadScope r7 = (androidx.datastore.core.okio.OkioReadScope) r7
            kotlin.ResultKt.throwOnFailure(r15)     // Catch:{ all -> 0x0057 }
            r11 = r15
            r9 = r4
            r8 = r7
            r4 = r14
            r14 = r3
            r7 = r6
            r6 = r14
            goto L_0x0091
        L_0x0057:
            r4 = move-exception
            r5 = r3
            goto L_0x00a5
        L_0x005a:
            kotlin.ResultKt.throwOnFailure(r15)
            r7 = r14
            r7.checkClose()
            okio.FileSystem r14 = r7.fileSystem     // Catch:{ FileNotFoundException -> 0x00bf }
            okio.Path r2 = r7.path     // Catch:{ FileNotFoundException -> 0x00bf }
            r4 = 0
            okio.Source r5 = r14.source(r2)     // Catch:{ FileNotFoundException -> 0x00bf }
            okio.BufferedSource r5 = okio.Okio.buffer((okio.Source) r5)     // Catch:{ FileNotFoundException -> 0x00bf }
            java.io.Closeable r5 = (java.io.Closeable) r5     // Catch:{ FileNotFoundException -> 0x00bf }
            r6 = r5
            r2 = 0
            r5 = 0
            r14 = 0
            r8 = r6
            okio.BufferedSource r8 = (okio.BufferedSource) r8     // Catch:{ all -> 0x00a1 }
            r9 = 0
            r10 = 0
            androidx.datastore.core.okio.OkioSerializer<T> r11 = r7.serializer     // Catch:{ all -> 0x00a1 }
            r0.L$0 = r7     // Catch:{ all -> 0x00a1 }
            r0.L$1 = r6     // Catch:{ all -> 0x00a1 }
            r12 = 1
            r0.label = r12     // Catch:{ all -> 0x00a1 }
            java.lang.Object r11 = r11.readFrom(r8, r0)     // Catch:{ all -> 0x00a1 }
            if (r11 != r1) goto L_0x008d
            return r1
        L_0x008d:
            r8 = r7
            r7 = r6
            r6 = r5
            r5 = r10
        L_0x0091:
            if (r7 == 0) goto L_0x009f
            r7.close()     // Catch:{ all -> 0x009b }
            goto L_0x009f
        L_0x009b:
            r14 = move-exception
            r5 = r14
        L_0x009f:
            r7 = r8
            goto L_0x00b6
        L_0x00a1:
            r14 = move-exception
            r13 = r4
            r4 = r14
            r14 = r13
        L_0x00a5:
            r8 = r4
            if (r6 == 0) goto L_0x00b3
            r6.close()     // Catch:{ all -> 0x00ad }
            goto L_0x00b3
        L_0x00ad:
            r4 = move-exception
            kotlin.ExceptionsKt.addSuppressed(r8, r4)     // Catch:{ FileNotFoundException -> 0x00bf }
        L_0x00b3:
            r4 = r14
            r11 = r5
            r14 = r8
        L_0x00b6:
            if (r14 != 0) goto L_0x00be
            kotlin.jvm.internal.Intrinsics.checkNotNull(r11)     // Catch:{ FileNotFoundException -> 0x00bf }
            goto L_0x012b
        L_0x00be:
            throw r14     // Catch:{ FileNotFoundException -> 0x00bf }
        L_0x00bf:
            r14 = move-exception
            okio.FileSystem r14 = r7.fileSystem
            okio.Path r2 = r7.path
            boolean r14 = r14.exists(r2)
            if (r14 == 0) goto L_0x0123
            okio.FileSystem r14 = r7.fileSystem
            okio.Path r2 = r7.path
            r4 = 0
            okio.Source r5 = r14.source(r2)
            okio.BufferedSource r5 = okio.Okio.buffer((okio.Source) r5)
            java.io.Closeable r5 = (java.io.Closeable) r5
            r14 = 0
            r2 = 0
            r6 = 0
            r8 = r5
            okio.BufferedSource r8 = (okio.BufferedSource) r8     // Catch:{ all -> 0x0107 }
            r9 = 0
            r10 = 0
            androidx.datastore.core.okio.OkioSerializer<T> r11 = r7.serializer     // Catch:{ all -> 0x0107 }
            r0.L$0 = r5     // Catch:{ all -> 0x0107 }
            r0.L$1 = r3     // Catch:{ all -> 0x0107 }
            r3 = 2
            r0.label = r3     // Catch:{ all -> 0x0107 }
            java.lang.Object r3 = r11.readFrom(r8, r0)     // Catch:{ all -> 0x0107 }
            if (r3 != r1) goto L_0x00f3
            return r1
        L_0x00f3:
            r1 = r14
            r14 = r4
            r4 = r3
            r3 = r2
            r2 = r9
        L_0x00f8:
            if (r5 == 0) goto L_0x0106
            r5.close()     // Catch:{ all -> 0x0102 }
            goto L_0x0106
        L_0x0102:
            r2 = move-exception
            r6 = r2
        L_0x0106:
            goto L_0x011b
        L_0x0107:
            r1 = move-exception
            r3 = r2
            r2 = r1
            r1 = r14
            r14 = r4
        L_0x010c:
            r6 = r2
            if (r5 == 0) goto L_0x011a
            r5.close()     // Catch:{ all -> 0x0114 }
            goto L_0x011a
        L_0x0114:
            r2 = move-exception
            kotlin.ExceptionsKt.addSuppressed(r6, r2)
        L_0x011a:
            r4 = r3
        L_0x011b:
            if (r6 != 0) goto L_0x0122
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)
            r11 = r4
            goto L_0x012a
        L_0x0122:
            throw r6
        L_0x0123:
            androidx.datastore.core.okio.OkioSerializer<T> r14 = r7.serializer
            java.lang.Object r14 = r14.getDefaultValue()
            r11 = r14
        L_0x012a:
        L_0x012b:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.okio.OkioReadScope.readData$suspendImpl(androidx.datastore.core.okio.OkioReadScope, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public void close() {
        this.closed.set(true);
    }

    /* access modifiers changed from: protected */
    public final void checkClose() {
        if (this.closed.get()) {
            throw new IllegalStateException("This scope has already been closed.".toString());
        }
    }
}
