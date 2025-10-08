package androidx.datastore.core;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;

@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B1\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\u0002\u0010\fJ\b\u0010\u0013\u001a\u00020\u000bH\u0002J\b\u0010\u0014\u001a\u00020\u000bH\u0016JX\u0010\u0015\u001a\u0002H\u0016\"\u0004\b\u0001\u0010\u00162B\u0010\u0017\u001a>\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0019\u0012\u0013\u0012\u00110\u001a¢\u0006\f\b\u001b\u0012\b\b\u001c\u0012\u0004\b\b(\u001d\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00160\u001e\u0012\u0006\u0012\u0004\u0018\u00010\u001f0\u0018¢\u0006\u0002\b H@¢\u0006\u0002\u0010!J=\u0010\"\u001a\u00020\u000b2-\u0010\u0017\u001a)\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000$\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u001e\u0012\u0006\u0012\u0004\u0018\u00010\u001f0#¢\u0006\u0002\b H@¢\u0006\u0002\u0010%J\f\u0010&\u001a\u00020\u000b*\u00020\u0004H\u0002R\u000e\u0010\r\u001a\u00020\u000eX\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\bX\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Landroidx/datastore/core/FileStorageConnection;", "T", "Landroidx/datastore/core/StorageConnection;", "file", "Ljava/io/File;", "serializer", "Landroidx/datastore/core/Serializer;", "coordinator", "Landroidx/datastore/core/InterProcessCoordinator;", "onClose", "Lkotlin/Function0;", "", "(Ljava/io/File;Landroidx/datastore/core/Serializer;Landroidx/datastore/core/InterProcessCoordinator;Lkotlin/jvm/functions/Function0;)V", "closed", "Ljava/util/concurrent/atomic/AtomicBoolean;", "getCoordinator", "()Landroidx/datastore/core/InterProcessCoordinator;", "transactionMutex", "Lkotlinx/coroutines/sync/Mutex;", "checkNotClosed", "close", "readScope", "R", "block", "Lkotlin/Function3;", "Landroidx/datastore/core/ReadScope;", "", "Lkotlin/ParameterName;", "name", "locked", "Lkotlin/coroutines/Continuation;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function3;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "writeScope", "Lkotlin/Function2;", "Landroidx/datastore/core/WriteScope;", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createParentDirectories", "datastore-core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: FileStorage.kt */
public final class FileStorageConnection<T> implements StorageConnection<T> {
    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final InterProcessCoordinator coordinator;
    private final File file;
    private final Function0<Unit> onClose;
    private final Serializer<T> serializer;
    private final Mutex transactionMutex = MutexKt.Mutex$default(false, 1, (Object) null);

    public FileStorageConnection(File file2, Serializer<T> serializer2, InterProcessCoordinator coordinator2, Function0<Unit> onClose2) {
        Intrinsics.checkNotNullParameter(file2, "file");
        Intrinsics.checkNotNullParameter(serializer2, "serializer");
        Intrinsics.checkNotNullParameter(coordinator2, "coordinator");
        Intrinsics.checkNotNullParameter(onClose2, "onClose");
        this.file = file2;
        this.serializer = serializer2;
        this.coordinator = coordinator2;
        this.onClose = onClose2;
    }

    public InterProcessCoordinator getCoordinator() {
        return this.coordinator;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0084, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0085, code lost:
        r8 = r1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0044  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0026  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <R> java.lang.Object readScope(kotlin.jvm.functions.Function3<? super androidx.datastore.core.ReadScope<T>, ? super java.lang.Boolean, ? super kotlin.coroutines.Continuation<? super R>, ? extends java.lang.Object> r13, kotlin.coroutines.Continuation<? super R> r14) {
        /*
            r12 = this;
            boolean r0 = r14 instanceof androidx.datastore.core.FileStorageConnection$readScope$1
            if (r0 == 0) goto L_0x0014
            r0 = r14
            androidx.datastore.core.FileStorageConnection$readScope$1 r0 = (androidx.datastore.core.FileStorageConnection$readScope$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r14 = r0.label
            int r14 = r14 - r2
            r0.label = r14
            goto L_0x0019
        L_0x0014:
            androidx.datastore.core.FileStorageConnection$readScope$1 r0 = new androidx.datastore.core.FileStorageConnection$readScope$1
            r0.<init>(r12, r14)
        L_0x0019:
            java.lang.Object r14 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            r4 = 0
            switch(r2) {
                case 0: goto L_0x0044;
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
            androidx.datastore.core.FileStorageConnection r6 = (androidx.datastore.core.FileStorageConnection) r6
            kotlin.ResultKt.throwOnFailure(r14)     // Catch:{ all -> 0x0041 }
            r7 = r13
            r13 = r14
            r8 = r4
            goto L_0x007d
        L_0x0041:
            r1 = move-exception
            r7 = r4
            goto L_0x0097
        L_0x0044:
            kotlin.ResultKt.throwOnFailure(r14)
            r6 = r12
            r6.checkNotClosed()
            kotlinx.coroutines.sync.Mutex r2 = r6.transactionMutex
            boolean r2 = kotlinx.coroutines.sync.Mutex.DefaultImpls.tryLock$default(r2, r4, r3, r4)
            androidx.datastore.core.FileReadScope r5 = new androidx.datastore.core.FileReadScope     // Catch:{ all -> 0x00a5 }
            java.io.File r7 = r6.file     // Catch:{ all -> 0x00a5 }
            androidx.datastore.core.Serializer<T> r8 = r6.serializer     // Catch:{ all -> 0x00a5 }
            r5.<init>(r7, r8)     // Catch:{ all -> 0x00a5 }
            androidx.datastore.core.Closeable r5 = (androidx.datastore.core.Closeable) r5     // Catch:{ all -> 0x00a5 }
            r7 = 0
            r8 = 0
            r9 = r5
            androidx.datastore.core.FileReadScope r9 = (androidx.datastore.core.FileReadScope) r9     // Catch:{ all -> 0x0094 }
            r10 = 0
            if (r2 == 0) goto L_0x0068
            r11 = r3
            goto L_0x0069
        L_0x0068:
            r11 = 0
        L_0x0069:
            java.lang.Boolean r11 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r11)     // Catch:{ all -> 0x0094 }
            r0.L$0 = r6     // Catch:{ all -> 0x0094 }
            r0.L$1 = r5     // Catch:{ all -> 0x0094 }
            r0.Z$0 = r2     // Catch:{ all -> 0x0094 }
            r0.label = r3     // Catch:{ all -> 0x0094 }
            java.lang.Object r13 = r13.invoke(r9, r11, r0)     // Catch:{ all -> 0x0094 }
            if (r13 != r1) goto L_0x007c
            return r1
        L_0x007c:
            r1 = r10
        L_0x007d:
            r5.close()     // Catch:{ all -> 0x0084 }
            goto L_0x0087
        L_0x0084:
            r1 = move-exception
            r8 = r1
        L_0x0087:
            if (r8 != 0) goto L_0x0092
            if (r2 == 0) goto L_0x0091
            kotlinx.coroutines.sync.Mutex r1 = r6.transactionMutex
            kotlinx.coroutines.sync.Mutex.DefaultImpls.unlock$default(r1, r4, r3, r4)
        L_0x0091:
            return r13
        L_0x0092:
            throw r8     // Catch:{ all -> 0x00a5 }
        L_0x0094:
            r1 = move-exception
            r13 = r7
            r7 = r8
        L_0x0097:
            r7 = r1
            r5.close()     // Catch:{ all -> 0x009d }
            goto L_0x00a2
        L_0x009d:
            r1 = move-exception
            kotlin.ExceptionsKt.addSuppressed(r7, r1)     // Catch:{ all -> 0x00a5 }
        L_0x00a2:
            throw r7     // Catch:{ all -> 0x00a5 }
        L_0x00a5:
            r13 = move-exception
            if (r2 == 0) goto L_0x00ad
            kotlinx.coroutines.sync.Mutex r1 = r6.transactionMutex
            kotlinx.coroutines.sync.Mutex.DefaultImpls.unlock$default(r1, r4, r3, r4)
        L_0x00ad:
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.FileStorageConnection.readScope(kotlin.jvm.functions.Function3, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00d1 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00d2  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object writeScope(kotlin.jvm.functions.Function2<? super androidx.datastore.core.WriteScope<T>, ? super kotlin.coroutines.Continuation<? super kotlin.Unit>, ? extends java.lang.Object> r17, kotlin.coroutines.Continuation<? super kotlin.Unit> r18) {
        /*
            r16 = this;
            r0 = r18
            boolean r1 = r0 instanceof androidx.datastore.core.FileStorageConnection$writeScope$1
            if (r1 == 0) goto L_0x0018
            r1 = r0
            androidx.datastore.core.FileStorageConnection$writeScope$1 r1 = (androidx.datastore.core.FileStorageConnection$writeScope$1) r1
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
            androidx.datastore.core.FileStorageConnection$writeScope$1 r1 = new androidx.datastore.core.FileStorageConnection$writeScope$1
            r2 = r16
            r1.<init>(r2, r0)
        L_0x001f:
            java.lang.Object r3 = r1.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r1.label
            switch(r4) {
                case 0: goto L_0x0065;
                case 1: goto L_0x0050;
                case 2: goto L_0x0032;
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
            java.lang.Object r8 = r1.L$3
            androidx.datastore.core.Closeable r8 = (androidx.datastore.core.Closeable) r8
            java.lang.Object r9 = r1.L$2
            java.io.File r9 = (java.io.File) r9
            r10 = 0
            java.lang.Object r11 = r1.L$1
            kotlinx.coroutines.sync.Mutex r11 = (kotlinx.coroutines.sync.Mutex) r11
            java.lang.Object r12 = r1.L$0
            androidx.datastore.core.FileStorageConnection r12 = (androidx.datastore.core.FileStorageConnection) r12
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x004d }
            goto L_0x00d4
        L_0x004d:
            r0 = move-exception
            goto L_0x012d
        L_0x0050:
            r4 = 0
            r5 = 0
            java.lang.Object r6 = r1.L$2
            kotlinx.coroutines.sync.Mutex r6 = (kotlinx.coroutines.sync.Mutex) r6
            java.lang.Object r7 = r1.L$1
            kotlin.jvm.functions.Function2 r7 = (kotlin.jvm.functions.Function2) r7
            java.lang.Object r8 = r1.L$0
            androidx.datastore.core.FileStorageConnection r8 = (androidx.datastore.core.FileStorageConnection) r8
            kotlin.ResultKt.throwOnFailure(r3)
            r10 = r5
            r11 = r6
            r12 = r8
            goto L_0x008d
        L_0x0065:
            kotlin.ResultKt.throwOnFailure(r3)
            r4 = r16
            r7 = r17
            r4.checkNotClosed()
            java.io.File r5 = r4.file
            r4.createParentDirectories(r5)
            kotlinx.coroutines.sync.Mutex r5 = r4.transactionMutex
            r6 = 0
            r8 = 0
            r1.L$0 = r4
            r1.L$1 = r7
            r1.L$2 = r5
            r9 = 1
            r1.label = r9
            java.lang.Object r9 = r5.lock(r6, r1)
            if (r9 != r0) goto L_0x0089
            return r0
        L_0x0089:
            r12 = r4
            r11 = r5
            r10 = r6
            r4 = r8
        L_0x008d:
            r5 = 0
            java.io.File r6 = new java.io.File     // Catch:{ all -> 0x0147 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x0147 }
            r8.<init>()     // Catch:{ all -> 0x0147 }
            java.io.File r9 = r12.file     // Catch:{ all -> 0x0147 }
            java.lang.String r9 = r9.getAbsolutePath()     // Catch:{ all -> 0x0147 }
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x0147 }
            java.lang.String r9 = ".tmp"
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ all -> 0x0147 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x0147 }
            r6.<init>(r8)     // Catch:{ all -> 0x0147 }
            r9 = r6
            androidx.datastore.core.FileWriteScope r6 = new androidx.datastore.core.FileWriteScope     // Catch:{ IOException -> 0x013b }
            androidx.datastore.core.Serializer<T> r8 = r12.serializer     // Catch:{ IOException -> 0x013b }
            r6.<init>(r9, r8)     // Catch:{ IOException -> 0x013b }
            androidx.datastore.core.Closeable r6 = (androidx.datastore.core.Closeable) r6     // Catch:{ IOException -> 0x013b }
            r8 = r6
            r6 = 0
            r13 = 0
            r14 = r8
            androidx.datastore.core.FileWriteScope r14 = (androidx.datastore.core.FileWriteScope) r14     // Catch:{ all -> 0x012b }
            r15 = 0
            r1.L$0 = r12     // Catch:{ all -> 0x012b }
            r1.L$1 = r11     // Catch:{ all -> 0x012b }
            r1.L$2 = r9     // Catch:{ all -> 0x012b }
            r1.L$3 = r8     // Catch:{ all -> 0x012b }
            r2 = 2
            r1.label = r2     // Catch:{ all -> 0x012b }
            java.lang.Object r2 = r7.invoke(r14, r1)     // Catch:{ all -> 0x012b }
            if (r2 != r0) goto L_0x00d2
            return r0
        L_0x00d2:
            r7 = r13
            r0 = r15
        L_0x00d4:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x004d }
            r8.close()     // Catch:{ all -> 0x00de }
            goto L_0x00e1
        L_0x00de:
            r0 = move-exception
            r7 = r0
        L_0x00e1:
            if (r7 != 0) goto L_0x0129
            boolean r0 = r9.exists()     // Catch:{ IOException -> 0x013b }
            if (r0 == 0) goto L_0x011e
            java.io.File r0 = r12.file     // Catch:{ IOException -> 0x013b }
            boolean r0 = androidx.datastore.core.FileMoves_androidKt.atomicMoveTo(r9, r0)     // Catch:{ IOException -> 0x013b }
            if (r0 == 0) goto L_0x00f3
            goto L_0x011e
        L_0x00f3:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ IOException -> 0x013b }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x013b }
            r2.<init>()     // Catch:{ IOException -> 0x013b }
            java.lang.String r6 = "Unable to rename "
            java.lang.StringBuilder r2 = r2.append(r6)     // Catch:{ IOException -> 0x013b }
            java.lang.StringBuilder r2 = r2.append(r9)     // Catch:{ IOException -> 0x013b }
            java.lang.String r6 = " to "
            java.lang.StringBuilder r2 = r2.append(r6)     // Catch:{ IOException -> 0x013b }
            java.io.File r6 = r12.file     // Catch:{ IOException -> 0x013b }
            java.lang.StringBuilder r2 = r2.append(r6)     // Catch:{ IOException -> 0x013b }
            java.lang.String r6 = ". This likely means that there are multiple instances of DataStore for this file. Ensure that you are only creating a single instance of datastore for this file."
            java.lang.StringBuilder r2 = r2.append(r6)     // Catch:{ IOException -> 0x013b }
            java.lang.String r2 = r2.toString()     // Catch:{ IOException -> 0x013b }
            r0.<init>(r2)     // Catch:{ IOException -> 0x013b }
            throw r0     // Catch:{ IOException -> 0x013b }
        L_0x011e:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0147 }
            r11.unlock(r10)
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x0129:
            throw r7     // Catch:{ IOException -> 0x013b }
        L_0x012b:
            r0 = move-exception
            r7 = r13
        L_0x012d:
            r2 = r0
            r8.close()     // Catch:{ all -> 0x0133 }
            goto L_0x0138
        L_0x0133:
            r0 = move-exception
            kotlin.ExceptionsKt.addSuppressed(r2, r0)     // Catch:{ IOException -> 0x013b }
        L_0x0138:
            throw r2     // Catch:{ IOException -> 0x013b }
        L_0x013b:
            r0 = move-exception
            boolean r2 = r9.exists()     // Catch:{ all -> 0x0147 }
            if (r2 == 0) goto L_0x0145
            r9.delete()     // Catch:{ all -> 0x0147 }
        L_0x0145:
            throw r0     // Catch:{ all -> 0x0147 }
        L_0x0147:
            r0 = move-exception
            r11.unlock(r10)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.FileStorageConnection.writeScope(kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public void close() {
        this.closed.set(true);
        this.onClose.invoke();
    }

    private final void checkNotClosed() {
        if (this.closed.get()) {
            throw new IllegalStateException("StorageConnection has already been disposed.".toString());
        }
    }

    private final void createParentDirectories(File $this$createParentDirectories) {
        File parent = $this$createParentDirectories.getCanonicalFile().getParentFile();
        if (parent != null) {
            File it = parent;
            it.mkdirs();
            if (!it.isDirectory()) {
                throw new IOException("Unable to create parent directories of " + $this$createParentDirectories);
            }
        }
    }
}
