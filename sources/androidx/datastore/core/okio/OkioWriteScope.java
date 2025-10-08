package androidx.datastore.core.okio;

import androidx.datastore.core.WriteScope;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okio.FileSystem;
import okio.Path;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B#\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t¢\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00028\u0000H@¢\u0006\u0002\u0010\u000e¨\u0006\u000f"}, d2 = {"Landroidx/datastore/core/okio/OkioWriteScope;", "T", "Landroidx/datastore/core/okio/OkioReadScope;", "Landroidx/datastore/core/WriteScope;", "fileSystem", "Lokio/FileSystem;", "path", "Lokio/Path;", "serializer", "Landroidx/datastore/core/okio/OkioSerializer;", "(Lokio/FileSystem;Lokio/Path;Landroidx/datastore/core/okio/OkioSerializer;)V", "writeData", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "datastore-core-okio"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: OkioStorage.kt */
public final class OkioWriteScope<T> extends OkioReadScope<T> implements WriteScope<T> {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public OkioWriteScope(FileSystem fileSystem, Path path, OkioSerializer<T> serializer) {
        super(fileSystem, path, serializer);
        Intrinsics.checkNotNullParameter(fileSystem, "fileSystem");
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(serializer, "serializer");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r10.flush();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00af, code lost:
        r2 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00b1, code lost:
        if (r9 == null) goto L_0x00d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r9.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b7, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00b8, code lost:
        r7 = r0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00fc  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0102  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object writeData(T r19, kotlin.coroutines.Continuation<? super kotlin.Unit> r20) {
        /*
            r18 = this;
            r0 = r20
            boolean r1 = r0 instanceof androidx.datastore.core.okio.OkioWriteScope$writeData$1
            if (r1 == 0) goto L_0x0018
            r1 = r0
            androidx.datastore.core.okio.OkioWriteScope$writeData$1 r1 = (androidx.datastore.core.okio.OkioWriteScope$writeData$1) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0018
            int r0 = r1.label
            int r0 = r0 - r3
            r1.label = r0
            r2 = r18
            goto L_0x001f
        L_0x0018:
            androidx.datastore.core.okio.OkioWriteScope$writeData$1 r1 = new androidx.datastore.core.okio.OkioWriteScope$writeData$1
            r2 = r18
            r1.<init>(r2, r0)
        L_0x001f:
            java.lang.Object r3 = r1.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r1.label
            switch(r4) {
                case 0: goto L_0x004d;
                case 1: goto L_0x0032;
                default: goto L_0x002a;
            }
        L_0x002a:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0032:
            r4 = 0
            r5 = 0
            r6 = 0
            r0 = 0
            r7 = 0
            r8 = 0
            java.lang.Object r9 = r1.L$2
            java.io.Closeable r9 = (java.io.Closeable) r9
            java.lang.Object r10 = r1.L$1
            okio.FileHandle r10 = (okio.FileHandle) r10
            r11 = 0
            r12 = 0
            java.lang.Object r13 = r1.L$0
            java.io.Closeable r13 = (java.io.Closeable) r13
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x004a }
            goto L_0x00a9
        L_0x004a:
            r0 = move-exception
            goto L_0x00c1
        L_0x004d:
            kotlin.ResultKt.throwOnFailure(r3)
            r4 = r18
            r5 = r19
            r4.checkClose()
            okio.FileSystem r6 = r4.getFileSystem()
            okio.Path r7 = r4.getPath()
            okio.FileHandle r6 = r6.openReadWrite(r7)
            r13 = r6
            java.io.Closeable r13 = (java.io.Closeable) r13
            r6 = 0
            r12 = 0
            r11 = 0
            r7 = r13
            okio.FileHandle r7 = (okio.FileHandle) r7     // Catch:{ all -> 0x00e9 }
            r10 = r7
            r7 = 0
            r8 = 0
            r14 = 0
            r15 = 1
            okio.Sink r8 = okio.FileHandle.sink$default(r10, r8, r15, r14)     // Catch:{ all -> 0x00e9 }
            okio.BufferedSink r8 = okio.Okio.buffer((okio.Sink) r8)     // Catch:{ all -> 0x00e9 }
            java.io.Closeable r8 = (java.io.Closeable) r8     // Catch:{ all -> 0x00e9 }
            r9 = r8
            r8 = 0
            r14 = 0
            r16 = 0
            r17 = r9
            okio.BufferedSink r17 = (okio.BufferedSink) r17     // Catch:{ all -> 0x00bc }
            r19 = r17
            r17 = 0
            androidx.datastore.core.okio.OkioSerializer r15 = r4.getSerializer()     // Catch:{ all -> 0x00bc }
            r1.L$0 = r13     // Catch:{ all -> 0x00bc }
            r1.L$1 = r10     // Catch:{ all -> 0x00bc }
            r1.L$2 = r9     // Catch:{ all -> 0x00bc }
            r2 = 1
            r1.label = r2     // Catch:{ all -> 0x00bc }
            r2 = r19
            java.lang.Object r15 = r15.writeTo(r5, r2, r1)     // Catch:{ all -> 0x00bc }
            if (r15 != r0) goto L_0x00a1
            return r0
        L_0x00a1:
            r4 = r6
            r5 = r7
            r6 = r8
            r8 = r14
            r7 = r16
            r0 = r17
        L_0x00a9:
            r10.flush()     // Catch:{ all -> 0x004a }
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x004a }
            r2 = r0
            if (r9 == 0) goto L_0x00bb
            r9.close()     // Catch:{ all -> 0x00b7 }
            goto L_0x00bb
        L_0x00b7:
            r0 = move-exception
            r7 = r0
        L_0x00bb:
            goto L_0x00d3
        L_0x00bc:
            r0 = move-exception
            r4 = r6
            r5 = r7
            r6 = r8
            r8 = r14
        L_0x00c1:
            r7 = r0
            if (r9 == 0) goto L_0x00d2
            r9.close()     // Catch:{ all -> 0x00c9 }
            goto L_0x00d2
        L_0x00c9:
            r0 = move-exception
            kotlin.ExceptionsKt.addSuppressed(r7, r0)     // Catch:{ all -> 0x00cf }
            goto L_0x00d2
        L_0x00cf:
            r0 = move-exception
            r6 = r4
            goto L_0x00ea
        L_0x00d2:
            r2 = r8
        L_0x00d3:
            if (r7 != 0) goto L_0x00e8
            kotlin.jvm.internal.Intrinsics.checkNotNull(r2)     // Catch:{ all -> 0x00cf }
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00cf }
            r2 = r0
            if (r13 == 0) goto L_0x00e7
            r13.close()     // Catch:{ all -> 0x00e3 }
            goto L_0x00e7
        L_0x00e3:
            r0 = move-exception
            r11 = r0
        L_0x00e7:
            goto L_0x00fa
        L_0x00e8:
            throw r7     // Catch:{ all -> 0x00cf }
        L_0x00e9:
            r0 = move-exception
        L_0x00ea:
            r11 = r0
            if (r13 == 0) goto L_0x00f8
            r13.close()     // Catch:{ all -> 0x00f2 }
            goto L_0x00f8
        L_0x00f2:
            r0 = move-exception
            kotlin.ExceptionsKt.addSuppressed(r11, r0)
        L_0x00f8:
            r4 = r6
            r2 = r12
        L_0x00fa:
            if (r11 != 0) goto L_0x0102
            kotlin.jvm.internal.Intrinsics.checkNotNull(r2)
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x0102:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.okio.OkioWriteScope.writeData(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
