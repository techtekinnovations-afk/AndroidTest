package androidx.datastore.core;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u001b\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007¢\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00028\u0000H@¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Landroidx/datastore/core/FileWriteScope;", "T", "Landroidx/datastore/core/FileReadScope;", "Landroidx/datastore/core/WriteScope;", "file", "Ljava/io/File;", "serializer", "Landroidx/datastore/core/Serializer;", "(Ljava/io/File;Landroidx/datastore/core/Serializer;)V", "writeData", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "datastore-core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: FileStorage.kt */
public final class FileWriteScope<T> extends FileReadScope<T> implements WriteScope<T> {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FileWriteScope(File file, Serializer<T> serializer) {
        super(file, serializer);
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(serializer, "serializer");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r1.getFD().sync();
        r10 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0078, code lost:
        kotlin.io.CloseableKt.closeFinally(r2, (java.lang.Throwable) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x007e, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object writeData(T r10, kotlin.coroutines.Continuation<? super kotlin.Unit> r11) {
        /*
            r9 = this;
            boolean r0 = r11 instanceof androidx.datastore.core.FileWriteScope$writeData$1
            if (r0 == 0) goto L_0x0014
            r0 = r11
            androidx.datastore.core.FileWriteScope$writeData$1 r0 = (androidx.datastore.core.FileWriteScope$writeData$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r11 = r0.label
            int r11 = r11 - r2
            r0.label = r11
            goto L_0x0019
        L_0x0014:
            androidx.datastore.core.FileWriteScope$writeData$1 r0 = new androidx.datastore.core.FileWriteScope$writeData$1
            r0.<init>(r9, r11)
        L_0x0019:
            java.lang.Object r11 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            switch(r2) {
                case 0: goto L_0x003b;
                case 1: goto L_0x002c;
                default: goto L_0x0024;
            }
        L_0x0024:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L_0x002c:
            r10 = 0
            java.lang.Object r1 = r0.L$1
            java.io.FileOutputStream r1 = (java.io.FileOutputStream) r1
            java.lang.Object r2 = r0.L$0
            java.io.Closeable r2 = (java.io.Closeable) r2
            kotlin.ResultKt.throwOnFailure(r11)     // Catch:{ all -> 0x0039 }
            goto L_0x006e
        L_0x0039:
            r10 = move-exception
            goto L_0x0081
        L_0x003b:
            kotlin.ResultKt.throwOnFailure(r11)
            r2 = r9
            r2.checkNotClosed()
            java.io.FileOutputStream r3 = new java.io.FileOutputStream
            java.io.File r4 = r2.getFile()
            r3.<init>(r4)
            r4 = r3
            java.io.Closeable r4 = (java.io.Closeable) r4
            r3 = r4
            java.io.FileOutputStream r3 = (java.io.FileOutputStream) r3     // Catch:{ all -> 0x007f }
            r5 = 0
            androidx.datastore.core.Serializer r6 = r2.getSerializer()     // Catch:{ all -> 0x007f }
            androidx.datastore.core.UncloseableOutputStream r7 = new androidx.datastore.core.UncloseableOutputStream     // Catch:{ all -> 0x007f }
            r7.<init>(r3)     // Catch:{ all -> 0x007f }
            java.io.OutputStream r7 = (java.io.OutputStream) r7     // Catch:{ all -> 0x007f }
            r0.L$0 = r4     // Catch:{ all -> 0x007f }
            r0.L$1 = r3     // Catch:{ all -> 0x007f }
            r8 = 1
            r0.label = r8     // Catch:{ all -> 0x007f }
            java.lang.Object r6 = r6.writeTo(r10, r7, r0)     // Catch:{ all -> 0x007f }
            if (r6 != r1) goto L_0x006b
            return r1
        L_0x006b:
            r1 = r3
            r2 = r4
            r10 = r5
        L_0x006e:
            java.io.FileDescriptor r3 = r1.getFD()     // Catch:{ all -> 0x0039 }
            r3.sync()     // Catch:{ all -> 0x0039 }
            kotlin.Unit r10 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0039 }
            r10 = 0
            kotlin.io.CloseableKt.closeFinally(r2, r10)
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        L_0x007f:
            r10 = move-exception
            r2 = r4
        L_0x0081:
            throw r10     // Catch:{ all -> 0x0082 }
        L_0x0082:
            r1 = move-exception
            kotlin.io.CloseableKt.closeFinally(r2, r10)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.FileWriteScope.writeData(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
