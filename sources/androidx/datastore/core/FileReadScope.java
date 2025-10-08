package androidx.datastore.core;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001b\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\u000e\u001a\u00020\u000fH\u0004J\b\u0010\u0010\u001a\u00020\u000fH\u0016J\u000e\u0010\u0011\u001a\u00028\u0000H@¢\u0006\u0002\u0010\u0012R\u000e\u0010\b\u001a\u00020\tX\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0013"}, d2 = {"Landroidx/datastore/core/FileReadScope;", "T", "Landroidx/datastore/core/ReadScope;", "file", "Ljava/io/File;", "serializer", "Landroidx/datastore/core/Serializer;", "(Ljava/io/File;Landroidx/datastore/core/Serializer;)V", "closed", "Ljava/util/concurrent/atomic/AtomicBoolean;", "getFile", "()Ljava/io/File;", "getSerializer", "()Landroidx/datastore/core/Serializer;", "checkNotClosed", "", "close", "readData", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "datastore-core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: FileStorage.kt */
public class FileReadScope<T> implements ReadScope<T> {
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final File file;
    private final Serializer<T> serializer;

    public Object readData(Continuation<? super T> continuation) {
        return readData$suspendImpl(this, continuation);
    }

    public FileReadScope(File file2, Serializer<T> serializer2) {
        Intrinsics.checkNotNullParameter(file2, "file");
        Intrinsics.checkNotNullParameter(serializer2, "serializer");
        this.file = file2;
        this.serializer = serializer2;
    }

    /* access modifiers changed from: protected */
    public final File getFile() {
        return this.file;
    }

    /* access modifiers changed from: protected */
    public final Serializer<T> getSerializer() {
        return this.serializer;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        kotlin.io.CloseableKt.closeFinally(r2, (java.lang.Throwable) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x007b, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        kotlin.io.CloseableKt.closeFinally(r2, r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x007f, code lost:
        throw r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
        return r6;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ <T> java.lang.Object readData$suspendImpl(androidx.datastore.core.FileReadScope<T> r10, kotlin.coroutines.Continuation<? super T> r11) {
        /*
            boolean r0 = r11 instanceof androidx.datastore.core.FileReadScope$readData$1
            if (r0 == 0) goto L_0x0014
            r0 = r11
            androidx.datastore.core.FileReadScope$readData$1 r0 = (androidx.datastore.core.FileReadScope$readData$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r11 = r0.label
            int r11 = r11 - r2
            r0.label = r11
            goto L_0x0019
        L_0x0014:
            androidx.datastore.core.FileReadScope$readData$1 r0 = new androidx.datastore.core.FileReadScope$readData$1
            r0.<init>(r10, r11)
        L_0x0019:
            java.lang.Object r11 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x004b;
                case 1: goto L_0x003b;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x002d:
            r10 = 0
            java.lang.Object r1 = r0.L$0
            java.io.Closeable r1 = (java.io.Closeable) r1
            kotlin.ResultKt.throwOnFailure(r11)     // Catch:{ all -> 0x0038 }
            r6 = r11
            goto L_0x00ab
        L_0x0038:
            r10 = move-exception
            goto L_0x00b4
        L_0x003b:
            r10 = 0
            java.lang.Object r2 = r0.L$1
            java.io.Closeable r2 = (java.io.Closeable) r2
            java.lang.Object r4 = r0.L$0
            androidx.datastore.core.FileReadScope r4 = (androidx.datastore.core.FileReadScope) r4
            kotlin.ResultKt.throwOnFailure(r11)     // Catch:{ all -> 0x0049 }
            r6 = r11
            goto L_0x0075
        L_0x0049:
            r10 = move-exception
            goto L_0x007a
        L_0x004b:
            kotlin.ResultKt.throwOnFailure(r11)
            r4 = r10
            r4.checkNotClosed()
            java.io.FileInputStream r10 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x0080 }
            java.io.File r2 = r4.file     // Catch:{ FileNotFoundException -> 0x0080 }
            r10.<init>(r2)     // Catch:{ FileNotFoundException -> 0x0080 }
            r2 = r10
            java.io.Closeable r2 = (java.io.Closeable) r2     // Catch:{ FileNotFoundException -> 0x0080 }
            r10 = r2
            java.io.FileInputStream r10 = (java.io.FileInputStream) r10     // Catch:{ all -> 0x0049 }
            r5 = 0
            androidx.datastore.core.Serializer<T> r6 = r4.serializer     // Catch:{ all -> 0x0049 }
            r7 = r10
            java.io.InputStream r7 = (java.io.InputStream) r7     // Catch:{ all -> 0x0049 }
            r0.L$0 = r4     // Catch:{ all -> 0x0049 }
            r0.L$1 = r2     // Catch:{ all -> 0x0049 }
            r8 = 1
            r0.label = r8     // Catch:{ all -> 0x0049 }
            java.lang.Object r6 = r6.readFrom(r7, r0)     // Catch:{ all -> 0x0049 }
            if (r6 != r1) goto L_0x0074
            return r1
        L_0x0074:
            r10 = r5
        L_0x0075:
            kotlin.io.CloseableKt.closeFinally(r2, r3)     // Catch:{ FileNotFoundException -> 0x0080 }
            goto L_0x00c0
        L_0x007a:
            throw r10     // Catch:{ all -> 0x007b }
        L_0x007b:
            r5 = move-exception
            kotlin.io.CloseableKt.closeFinally(r2, r10)     // Catch:{ FileNotFoundException -> 0x0080 }
            throw r5     // Catch:{ FileNotFoundException -> 0x0080 }
        L_0x0080:
            r10 = move-exception
            java.io.File r10 = r4.file
            boolean r10 = r10.exists()
            if (r10 == 0) goto L_0x00ba
            java.io.FileInputStream r10 = new java.io.FileInputStream
            java.io.File r2 = r4.file
            r10.<init>(r2)
            java.io.Closeable r10 = (java.io.Closeable) r10
            r2 = r10
            java.io.FileInputStream r2 = (java.io.FileInputStream) r2     // Catch:{ all -> 0x00b0 }
            r5 = 0
            androidx.datastore.core.Serializer<T> r6 = r4.serializer     // Catch:{ all -> 0x00b0 }
            r7 = r2
            java.io.InputStream r7 = (java.io.InputStream) r7     // Catch:{ all -> 0x00b0 }
            r0.L$0 = r10     // Catch:{ all -> 0x00b0 }
            r0.L$1 = r3     // Catch:{ all -> 0x00b0 }
            r8 = 2
            r0.label = r8     // Catch:{ all -> 0x00b0 }
            java.lang.Object r6 = r6.readFrom(r7, r0)     // Catch:{ all -> 0x00b0 }
            if (r6 != r1) goto L_0x00a9
            return r1
        L_0x00a9:
            r1 = r10
            r10 = r5
        L_0x00ab:
            kotlin.io.CloseableKt.closeFinally(r1, r3)
            return r6
        L_0x00b0:
            r1 = move-exception
            r9 = r1
            r1 = r10
            r10 = r9
        L_0x00b4:
            throw r10     // Catch:{ all -> 0x00b5 }
        L_0x00b5:
            r2 = move-exception
            kotlin.io.CloseableKt.closeFinally(r1, r10)
            throw r2
        L_0x00ba:
            androidx.datastore.core.Serializer<T> r10 = r4.serializer
            java.lang.Object r6 = r10.getDefaultValue()
        L_0x00c0:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.FileReadScope.readData$suspendImpl(androidx.datastore.core.FileReadScope, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public void close() {
        this.closed.set(true);
    }

    /* access modifiers changed from: protected */
    public final void checkNotClosed() {
        if (this.closed.get()) {
            throw new IllegalStateException("This scope has already been closed.".toString());
        }
    }
}
