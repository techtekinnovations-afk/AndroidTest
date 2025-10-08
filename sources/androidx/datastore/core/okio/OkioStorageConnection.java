package androidx.datastore.core.okio;

import androidx.datastore.core.InterProcessCoordinator;
import androidx.datastore.core.StorageConnection;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;
import okio.FileSystem;
import okio.Path;

@Metadata(d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B9\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\u0002\u0010\u000eJ\b\u0010\u0015\u001a\u00020\rH\u0002J\b\u0010\u0016\u001a\u00020\rH\u0016JX\u0010\u0017\u001a\u0002H\u0018\"\u0004\b\u0001\u0010\u00182B\u0010\u0019\u001a>\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u001b\u0012\u0013\u0012\u00110\u001c¢\u0006\f\b\u001d\u0012\b\b\u001e\u0012\u0004\b\b(\u001f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180 \u0012\u0006\u0012\u0004\u0018\u00010!0\u001a¢\u0006\u0002\b\"H@¢\u0006\u0002\u0010#J=\u0010$\u001a\u00020\r2-\u0010\u0019\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000&\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0 \u0012\u0006\u0012\u0004\u0018\u00010!0%¢\u0006\u0002\b\"H@¢\u0006\u0002\u0010'R\u000e\u0010\u000f\u001a\u00020\u0010X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\nX\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Landroidx/datastore/core/okio/OkioStorageConnection;", "T", "Landroidx/datastore/core/StorageConnection;", "fileSystem", "Lokio/FileSystem;", "path", "Lokio/Path;", "serializer", "Landroidx/datastore/core/okio/OkioSerializer;", "coordinator", "Landroidx/datastore/core/InterProcessCoordinator;", "onClose", "Lkotlin/Function0;", "", "(Lokio/FileSystem;Lokio/Path;Landroidx/datastore/core/okio/OkioSerializer;Landroidx/datastore/core/InterProcessCoordinator;Lkotlin/jvm/functions/Function0;)V", "closed", "Landroidx/datastore/core/okio/AtomicBoolean;", "getCoordinator", "()Landroidx/datastore/core/InterProcessCoordinator;", "transactionMutex", "Lkotlinx/coroutines/sync/Mutex;", "checkNotClosed", "close", "readScope", "R", "block", "Lkotlin/Function3;", "Landroidx/datastore/core/ReadScope;", "", "Lkotlin/ParameterName;", "name", "locked", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "writeScope", "Lkotlin/Function2;", "Landroidx/datastore/core/WriteScope;", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "datastore-core-okio"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: OkioStorage.kt */
public final class OkioStorageConnection<T> implements StorageConnection<T> {
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final InterProcessCoordinator coordinator;
    private final FileSystem fileSystem;
    private final Function0<Unit> onClose;
    private final Path path;
    private final OkioSerializer<T> serializer;
    private final Mutex transactionMutex = MutexKt.Mutex$default(false, 1, (Object) null);

    public OkioStorageConnection(FileSystem fileSystem2, Path path2, OkioSerializer<T> serializer2, InterProcessCoordinator coordinator2, Function0<Unit> onClose2) {
        Intrinsics.checkNotNullParameter(fileSystem2, "fileSystem");
        Intrinsics.checkNotNullParameter(path2, "path");
        Intrinsics.checkNotNullParameter(serializer2, "serializer");
        Intrinsics.checkNotNullParameter(coordinator2, "coordinator");
        Intrinsics.checkNotNullParameter(onClose2, "onClose");
        this.fileSystem = fileSystem2;
        this.path = path2;
        this.serializer = serializer2;
        this.coordinator = coordinator2;
        this.onClose = onClose2;
    }

    public InterProcessCoordinator getCoordinator() {
        return this.coordinator;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0083, code lost:
        r8 = th;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <R> java.lang.Object readScope(kotlin.jvm.functions.Function3<? super androidx.datastore.core.ReadScope<T>, ? super java.lang.Boolean, ? super kotlin.coroutines.Continuation<? super R>, ? extends java.lang.Object> r13, kotlin.coroutines.Continuation<? super R> r14) {
        /*
            r12 = this;
            boolean r0 = r14 instanceof androidx.datastore.core.okio.OkioStorageConnection$readScope$1
            if (r0 == 0) goto L_0x0014
            r0 = r14
            androidx.datastore.core.okio.OkioStorageConnection$readScope$1 r0 = (androidx.datastore.core.okio.OkioStorageConnection$readScope$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r14 = r0.label
            int r14 = r14 - r2
            r0.label = r14
            goto L_0x0019
        L_0x0014:
            androidx.datastore.core.okio.OkioStorageConnection$readScope$1 r0 = new androidx.datastore.core.okio.OkioStorageConnection$readScope$1
            r0.<init>(r12, r14)
        L_0x0019:
            java.lang.Object r14 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            r4 = 0
            switch(r2) {
                case 0: goto L_0x0043;
                case 1: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException
            java.lang.String r14 = "call to 'resume' before 'invoke' with coroutine"
            r13.<init>(r14)
            throw r13
        L_0x002e:
            r13 = 0
            r1 = 0
            boolean r2 = r0.Z$0
            java.lang.Object r5 = r0.L$1
            androidx.datastore.core.Closeable r5 = (androidx.datastore.core.Closeable) r5
            java.lang.Object r6 = r0.L$0
            androidx.datastore.core.okio.OkioStorageConnection r6 = (androidx.datastore.core.okio.OkioStorageConnection) r6
            kotlin.ResultKt.throwOnFailure(r14)     // Catch:{ all -> 0x0040 }
            r13 = r14
            r8 = r4
            goto L_0x007d
        L_0x0040:
            r1 = move-exception
            r7 = r4
            goto L_0x0095
        L_0x0043:
            kotlin.ResultKt.throwOnFailure(r14)
            r6 = r12
            r6.checkNotClosed()
            kotlinx.coroutines.sync.Mutex r2 = r6.transactionMutex
            boolean r2 = kotlinx.coroutines.sync.Mutex.DefaultImpls.tryLock$default(r2, r4, r3, r4)
            androidx.datastore.core.okio.OkioReadScope r5 = new androidx.datastore.core.okio.OkioReadScope     // Catch:{ all -> 0x00a3 }
            okio.FileSystem r7 = r6.fileSystem     // Catch:{ all -> 0x00a3 }
            okio.Path r8 = r6.path     // Catch:{ all -> 0x00a3 }
            androidx.datastore.core.okio.OkioSerializer<T> r9 = r6.serializer     // Catch:{ all -> 0x00a3 }
            r5.<init>(r7, r8, r9)     // Catch:{ all -> 0x00a3 }
            androidx.datastore.core.Closeable r5 = (androidx.datastore.core.Closeable) r5     // Catch:{ all -> 0x00a3 }
            r7 = 0
            r8 = 0
            r9 = r5
            androidx.datastore.core.okio.OkioReadScope r9 = (androidx.datastore.core.okio.OkioReadScope) r9     // Catch:{ all -> 0x0092 }
            r10 = 0
            if (r2 == 0) goto L_0x0069
            r11 = r3
            goto L_0x006a
        L_0x0069:
            r11 = 0
        L_0x006a:
            java.lang.Boolean r11 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r11)     // Catch:{ all -> 0x0092 }
            r0.L$0 = r6     // Catch:{ all -> 0x0092 }
            r0.L$1 = r5     // Catch:{ all -> 0x0092 }
            r0.Z$0 = r2     // Catch:{ all -> 0x0092 }
            r0.label = r3     // Catch:{ all -> 0x0092 }
            java.lang.Object r13 = r13.invoke(r9, r11, r0)     // Catch:{ all -> 0x0092 }
            if (r13 != r1) goto L_0x007d
            return r1
        L_0x007d:
            r5.close()     // Catch:{ all -> 0x0083 }
            goto L_0x0086
        L_0x0083:
            r8 = move-exception
        L_0x0086:
            if (r8 != 0) goto L_0x0090
            if (r2 == 0) goto L_0x008f
            kotlinx.coroutines.sync.Mutex r1 = r6.transactionMutex
            kotlinx.coroutines.sync.Mutex.DefaultImpls.unlock$default(r1, r4, r3, r4)
        L_0x008f:
            return r13
        L_0x0090:
            throw r8     // Catch:{ all -> 0x00a3 }
        L_0x0092:
            r1 = move-exception
            r13 = r7
            r7 = r8
        L_0x0095:
            r7 = r1
            r5.close()     // Catch:{ all -> 0x009b }
            goto L_0x00a0
        L_0x009b:
            r1 = move-exception
            kotlin.ExceptionsKt.addSuppressed(r7, r1)     // Catch:{ all -> 0x00a3 }
        L_0x00a0:
            throw r7     // Catch:{ all -> 0x00a3 }
        L_0x00a3:
            r13 = move-exception
            if (r2 == 0) goto L_0x00ab
            kotlinx.coroutines.sync.Mutex r1 = r6.transactionMutex
            kotlinx.coroutines.sync.Mutex.DefaultImpls.unlock$default(r1, r4, r3, r4)
        L_0x00ab:
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.okio.OkioStorageConnection.readScope(kotlin.jvm.functions.Function3, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0051  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00ea A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00eb  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002b  */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:43:0x0111=Splitter:B:43:0x0111, B:68:0x0148=Splitter:B:68:0x0148} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object writeScope(kotlin.jvm.functions.Function2<? super androidx.datastore.core.WriteScope<T>, ? super kotlin.coroutines.Continuation<? super kotlin.Unit>, ? extends java.lang.Object> r17, kotlin.coroutines.Continuation<? super kotlin.Unit> r18) {
        /*
            r16 = this;
            r0 = r18
            boolean r1 = r0 instanceof androidx.datastore.core.okio.OkioStorageConnection$writeScope$1
            if (r1 == 0) goto L_0x0018
            r1 = r0
            androidx.datastore.core.okio.OkioStorageConnection$writeScope$1 r1 = (androidx.datastore.core.okio.OkioStorageConnection$writeScope$1) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0018
            int r0 = r1.label
            int r0 = r0 - r3
            r1.label = r0
            r2 = r16
            goto L_0x001f
        L_0x0018:
            androidx.datastore.core.okio.OkioStorageConnection$writeScope$1 r1 = new androidx.datastore.core.okio.OkioStorageConnection$writeScope$1
            r2 = r16
            r1.<init>(r2, r0)
        L_0x001f:
            java.lang.Object r3 = r1.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r1.label
            r5 = 0
            switch(r4) {
                case 0: goto L_0x006a;
                case 1: goto L_0x0051;
                case 2: goto L_0x0033;
                default: goto L_0x002b;
            }
        L_0x002b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0033:
            r4 = 0
            r5 = 0
            r6 = 0
            r0 = 0
            r7 = 0
            java.lang.Object r8 = r1.L$3
            androidx.datastore.core.Closeable r8 = (androidx.datastore.core.Closeable) r8
            java.lang.Object r9 = r1.L$2
            okio.Path r9 = (okio.Path) r9
            r10 = 0
            java.lang.Object r11 = r1.L$1
            kotlinx.coroutines.sync.Mutex r11 = (kotlinx.coroutines.sync.Mutex) r11
            java.lang.Object r12 = r1.L$0
            androidx.datastore.core.okio.OkioStorageConnection r12 = (androidx.datastore.core.okio.OkioStorageConnection) r12
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x004e }
            goto L_0x00f1
        L_0x004e:
            r0 = move-exception
            goto L_0x0123
        L_0x0051:
            r4 = 0
            r6 = 0
            java.lang.Object r7 = r1.L$3
            kotlinx.coroutines.sync.Mutex r7 = (kotlinx.coroutines.sync.Mutex) r7
            java.lang.Object r8 = r1.L$2
            okio.Path r8 = (okio.Path) r8
            java.lang.Object r9 = r1.L$1
            kotlin.jvm.functions.Function2 r9 = (kotlin.jvm.functions.Function2) r9
            java.lang.Object r10 = r1.L$0
            androidx.datastore.core.okio.OkioStorageConnection r10 = (androidx.datastore.core.okio.OkioStorageConnection) r10
            kotlin.ResultKt.throwOnFailure(r3)
            r11 = r7
            r12 = r10
            r10 = r6
            goto L_0x009f
        L_0x006a:
            kotlin.ResultKt.throwOnFailure(r3)
            r4 = r16
            r9 = r17
            r4.checkNotClosed()
            okio.Path r6 = r4.path
            okio.Path r6 = r6.parent()
            if (r6 == 0) goto L_0x014e
            r8 = r6
            okio.FileSystem r6 = r4.fileSystem
            r6.createDirectories(r8, r5)
            kotlinx.coroutines.sync.Mutex r6 = r4.transactionMutex
            r7 = 0
            r10 = 0
            r1.L$0 = r4
            r1.L$1 = r9
            r1.L$2 = r8
            r1.L$3 = r6
            r11 = 1
            r1.label = r11
            java.lang.Object r11 = r6.lock(r7, r1)
            if (r11 != r0) goto L_0x009b
            return r0
        L_0x009b:
            r12 = r4
            r11 = r6
            r4 = r10
            r10 = r7
        L_0x009f:
            r6 = 0
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0149 }
            r7.<init>()     // Catch:{ all -> 0x0149 }
            okio.Path r13 = r12.path     // Catch:{ all -> 0x0149 }
            java.lang.String r13 = r13.name()     // Catch:{ all -> 0x0149 }
            java.lang.StringBuilder r7 = r7.append(r13)     // Catch:{ all -> 0x0149 }
            java.lang.String r13 = ".tmp"
            java.lang.StringBuilder r7 = r7.append(r13)     // Catch:{ all -> 0x0149 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0149 }
            okio.Path r7 = r8.resolve((java.lang.String) r7)     // Catch:{ all -> 0x0149 }
            okio.FileSystem r8 = r12.fileSystem     // Catch:{ IOException -> 0x0135 }
            r8.delete(r7, r5)     // Catch:{ IOException -> 0x0135 }
            androidx.datastore.core.okio.OkioWriteScope r5 = new androidx.datastore.core.okio.OkioWriteScope     // Catch:{ IOException -> 0x0135 }
            okio.FileSystem r8 = r12.fileSystem     // Catch:{ IOException -> 0x0135 }
            androidx.datastore.core.okio.OkioSerializer<T> r13 = r12.serializer     // Catch:{ IOException -> 0x0135 }
            r5.<init>(r8, r7, r13)     // Catch:{ IOException -> 0x0135 }
            androidx.datastore.core.Closeable r5 = (androidx.datastore.core.Closeable) r5     // Catch:{ IOException -> 0x0135 }
            r8 = r5
            r5 = 0
            r13 = 0
            r14 = r8
            androidx.datastore.core.okio.OkioWriteScope r14 = (androidx.datastore.core.okio.OkioWriteScope) r14     // Catch:{ all -> 0x011d }
            r15 = 0
            r1.L$0 = r12     // Catch:{ all -> 0x011d }
            r1.L$1 = r11     // Catch:{ all -> 0x011d }
            r1.L$2 = r7     // Catch:{ all -> 0x011d }
            r1.L$3 = r8     // Catch:{ all -> 0x011d }
            r2 = 2
            r1.label = r2     // Catch:{ all -> 0x011d }
            java.lang.Object r2 = r9.invoke(r14, r1)     // Catch:{ all -> 0x011d }
            if (r2 != r0) goto L_0x00eb
            return r0
        L_0x00eb:
            r0 = r6
            r6 = r5
            r5 = r0
            r9 = r7
            r7 = r13
            r0 = r15
        L_0x00f1:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x004e }
            r8.close()     // Catch:{ all -> 0x00fb }
            goto L_0x00fe
        L_0x00fb:
            r0 = move-exception
            r7 = r0
        L_0x00fe:
            if (r7 != 0) goto L_0x011b
            okio.FileSystem r0 = r12.fileSystem     // Catch:{ IOException -> 0x0131 }
            boolean r0 = r0.exists(r9)     // Catch:{ IOException -> 0x0131 }
            if (r0 == 0) goto L_0x0110
            okio.FileSystem r0 = r12.fileSystem     // Catch:{ IOException -> 0x0131 }
            okio.Path r2 = r12.path     // Catch:{ IOException -> 0x0131 }
            r0.atomicMove(r9, r2)     // Catch:{ IOException -> 0x0131 }
        L_0x0110:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0149 }
            r11.unlock(r10)
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x011b:
            throw r7     // Catch:{ IOException -> 0x0131 }
        L_0x011d:
            r0 = move-exception
            r9 = r6
            r6 = r5
            r5 = r9
            r9 = r7
            r7 = r13
        L_0x0123:
            r2 = r0
            r8.close()     // Catch:{ all -> 0x0129 }
            goto L_0x012e
        L_0x0129:
            r0 = move-exception
            kotlin.ExceptionsKt.addSuppressed(r2, r0)     // Catch:{ IOException -> 0x0131 }
        L_0x012e:
            throw r2     // Catch:{ IOException -> 0x0131 }
        L_0x0131:
            r0 = move-exception
            r6 = r5
            r7 = r9
            goto L_0x0136
        L_0x0135:
            r0 = move-exception
        L_0x0136:
            r2 = r0
            okio.FileSystem r0 = r12.fileSystem     // Catch:{ all -> 0x0149 }
            boolean r0 = r0.exists(r7)     // Catch:{ all -> 0x0149 }
            if (r0 == 0) goto L_0x0147
            okio.FileSystem r0 = r12.fileSystem     // Catch:{ IOException -> 0x0146 }
            r0.delete(r7)     // Catch:{ IOException -> 0x0146 }
            goto L_0x0147
        L_0x0146:
            r0 = move-exception
        L_0x0147:
            throw r2     // Catch:{ all -> 0x0149 }
        L_0x0149:
            r0 = move-exception
            r11.unlock(r10)
            throw r0
        L_0x014e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r2 = "must have a parent path"
            java.lang.String r2 = r2.toString()
            r0.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.okio.OkioStorageConnection.writeScope(kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private final void checkNotClosed() {
        if (this.closed.get()) {
            throw new IllegalStateException("StorageConnection has already been disposed.".toString());
        }
    }

    public void close() {
        this.closed.set(true);
        this.onClose.invoke();
    }
}
