package androidx.datastore.core;

import com.google.firebase.firestore.util.ExponentialBackoff;
import java.io.File;
import java.io.IOException;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.sync.Mutex;
import kotlinx.coroutines.sync.MutexKt;

@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0000\u0018\u0000 42\u00020\u0001:\u00014B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010 \u001a\u00020\u00052\u0006\u0010!\u001a\u00020\bH\u0002J\u000e\u0010\"\u001a\u00020#H@¢\u0006\u0002\u0010$J\u000e\u0010%\u001a\u00020#H@¢\u0006\u0002\u0010$J2\u0010&\u001a\u0002H'\"\u0004\b\u0000\u0010'2\u001c\u0010(\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0*\u0012\u0006\u0012\u0004\u0018\u00010+0)H@¢\u0006\u0002\u0010,J8\u0010-\u001a\u0002H'\"\u0004\b\u0000\u0010'2\"\u0010(\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020/\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0*\u0012\u0006\u0012\u0004\u0018\u00010+0.H@¢\u0006\u0002\u00100J:\u00101\u001a\u0002H'\"\u0004\b\u0000\u0010'2$\b\u0004\u0010(\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u0002H'0*\u0012\u0006\u0012\u0004\u0018\u00010+0.HH¢\u0006\u0002\u00100J\f\u00102\u001a\u00020\u001d*\u00020\u0005H\u0002J\f\u00103\u001a\u00020\u001d*\u00020\u0005H\u0002R\u000e\u0010\u0007\u001a\u00020\bXD¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bXD¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bXD¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0012\u001a\u00020\u00058BX\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0013\u0010\fR\u001b\u0010\u0016\u001a\u00020\u00118BX\u0002¢\u0006\f\u001a\u0004\b\u0019\u0010\u001a*\u0004\b\u0017\u0010\u0018R\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cX\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f¨\u00065"}, d2 = {"Landroidx/datastore/core/MultiProcessCoordinator;", "Landroidx/datastore/core/InterProcessCoordinator;", "context", "Lkotlin/coroutines/CoroutineContext;", "file", "Ljava/io/File;", "(Lkotlin/coroutines/CoroutineContext;Ljava/io/File;)V", "LOCK_ERROR_MESSAGE", "", "LOCK_SUFFIX", "VERSION_SUFFIX", "getFile", "()Ljava/io/File;", "inMemoryMutex", "Lkotlinx/coroutines/sync/Mutex;", "lazySharedCounter", "Lkotlin/Lazy;", "Landroidx/datastore/core/SharedCounter;", "lockFile", "getLockFile", "lockFile$delegate", "Lkotlin/Lazy;", "sharedCounter", "getSharedCounter$delegate", "(Landroidx/datastore/core/MultiProcessCoordinator;)Ljava/lang/Object;", "getSharedCounter", "()Landroidx/datastore/core/SharedCounter;", "updateNotifications", "Lkotlinx/coroutines/flow/Flow;", "", "getUpdateNotifications", "()Lkotlinx/coroutines/flow/Flow;", "fileWithSuffix", "suffix", "getVersion", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "incrementAndGetVersion", "lock", "T", "block", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "tryLock", "Lkotlin/Function2;", "", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "withLazyCounter", "createIfNotExists", "createParentDirectories", "Companion", "datastore-core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: MultiProcessCoordinator.android.kt */
public final class MultiProcessCoordinator implements InterProcessCoordinator {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static final String DEADLOCK_ERROR_MESSAGE = "Resource deadlock would occur";
    /* access modifiers changed from: private */
    public static final long INITIAL_WAIT_MILLIS = 10;
    /* access modifiers changed from: private */
    public static final long MAX_WAIT_MILLIS = ExponentialBackoff.DEFAULT_BACKOFF_MAX_DELAY_MS;
    private final String LOCK_ERROR_MESSAGE = "fcntl failed: EAGAIN";
    /* access modifiers changed from: private */
    public final String LOCK_SUFFIX = ".lock";
    /* access modifiers changed from: private */
    public final String VERSION_SUFFIX = ".version";
    private final CoroutineContext context;
    private final File file;
    private final Mutex inMemoryMutex = MutexKt.Mutex$default(false, 1, (Object) null);
    private final Lazy<SharedCounter> lazySharedCounter = LazyKt.lazy(new MultiProcessCoordinator$lazySharedCounter$1(this));
    private final Lazy lockFile$delegate = LazyKt.lazy(new MultiProcessCoordinator$lockFile$2(this));
    private final Flow<Unit> updateNotifications = MulticastFileObserver.Companion.observe(this.file);

    public MultiProcessCoordinator(CoroutineContext context2, File file2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        Intrinsics.checkNotNullParameter(file2, "file");
        this.context = context2;
        this.file = file2;
    }

    /* access modifiers changed from: protected */
    public final File getFile() {
        return this.file;
    }

    public Flow<Unit> getUpdateNotifications() {
        return this.updateNotifications;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v13, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v11, resolved type: java.io.Closeable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v15, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v16, resolved type: java.nio.channels.FileLock} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v16, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v13, resolved type: java.io.Closeable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v17, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v12, resolved type: kotlinx.coroutines.sync.Mutex} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00a7, code lost:
        r7 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r8 = new java.io.FileOutputStream(r9.getLockFile());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        r9 = (java.io.FileOutputStream) r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00b7, code lost:
        r12 = 0;
        r13 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        r14 = Companion;
        r1.L$0 = r11;
        r1.L$1 = r10;
        r1.L$2 = r8;
        r1.label = 2;
        r14 = androidx.datastore.core.MultiProcessCoordinator.Companion.access$getExclusiveFileLockWithRetryIfDeadlock(r14, r9, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00c9, code lost:
        if (r14 != r0) goto L_0x00cc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00cb, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00cc, code lost:
        r9 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00cf, code lost:
        r8 = (java.nio.channels.FileLock) r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        r1.L$0 = r10;
        r1.L$1 = r9;
        r1.L$2 = r8;
        r1.label = 3;
        r13 = r11.invoke(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00dd, code lost:
        if (r13 != r0) goto L_0x00e2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00df, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00e2, code lost:
        if (r8 == null) goto L_0x00ed;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        r8.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00e8, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00e9, code lost:
        r5 = r4;
        r8 = r9;
        r4 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
        kotlin.io.CloseableKt.closeFinally(r9, (java.lang.Throwable) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00f0, code lost:
        r10.unlock(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00f3, code lost:
        return r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00f4, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00f6, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00f7, code lost:
        r5 = r6;
        r6 = r7;
        r7 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00fb, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00fc, code lost:
        r8 = r13;
        r5 = r6;
        r6 = r7;
        r7 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0101, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0102, code lost:
        r5 = r6;
        r6 = false;
        r9 = r8;
        r8 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:?, code lost:
        r8.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x010d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x010e, code lost:
        r7 = r6;
        r8 = r9;
        r6 = r5;
        r5 = r4;
        r4 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0115, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0116, code lost:
        r5 = r4;
        r4 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:?, code lost:
        throw r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0119, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:?, code lost:
        kotlin.io.CloseableKt.closeFinally(r8, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x011d, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x011e, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x011f, code lost:
        r4 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0121, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0122, code lost:
        r10.unlock(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0125, code lost:
        throw r0;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0051  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0073  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0087  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0109 A[SYNTHETIC, Splitter:B:62:0x0109] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> java.lang.Object lock(kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> r18, kotlin.coroutines.Continuation<? super T> r19) {
        /*
            r17 = this;
            r0 = r19
            boolean r1 = r0 instanceof androidx.datastore.core.MultiProcessCoordinator$lock$1
            if (r1 == 0) goto L_0x0018
            r1 = r0
            androidx.datastore.core.MultiProcessCoordinator$lock$1 r1 = (androidx.datastore.core.MultiProcessCoordinator$lock$1) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0018
            int r0 = r1.label
            int r0 = r0 - r3
            r1.label = r0
            r2 = r17
            goto L_0x001f
        L_0x0018:
            androidx.datastore.core.MultiProcessCoordinator$lock$1 r1 = new androidx.datastore.core.MultiProcessCoordinator$lock$1
            r2 = r17
            r1.<init>(r2, r0)
        L_0x001f:
            java.lang.Object r3 = r1.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r4 = r1.label
            r5 = 0
            switch(r4) {
                case 0: goto L_0x0087;
                case 1: goto L_0x0073;
                case 2: goto L_0x0051;
                case 3: goto L_0x0033;
                default: goto L_0x002b;
            }
        L_0x002b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0033:
            r4 = 0
            r6 = 0
            r7 = 0
            java.lang.Object r0 = r1.L$2
            r8 = r0
            java.nio.channels.FileLock r8 = (java.nio.channels.FileLock) r8
            java.lang.Object r0 = r1.L$1
            r9 = r0
            java.io.Closeable r9 = (java.io.Closeable) r9
            java.lang.Object r0 = r1.L$0
            r10 = r0
            kotlinx.coroutines.sync.Mutex r10 = (kotlinx.coroutines.sync.Mutex) r10
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x004e }
            r13 = r3
            r12 = r7
            r7 = r6
            r6 = r5
            goto L_0x00e0
        L_0x004e:
            r0 = move-exception
            goto L_0x0107
        L_0x0051:
            r4 = 0
            r6 = 0
            r7 = 0
            java.lang.Object r8 = r1.L$2
            r9 = r8
            java.io.Closeable r9 = (java.io.Closeable) r9
            r8 = 0
            java.lang.Object r10 = r1.L$1
            kotlinx.coroutines.sync.Mutex r10 = (kotlinx.coroutines.sync.Mutex) r10
            java.lang.Object r11 = r1.L$0
            kotlin.jvm.functions.Function1 r11 = (kotlin.jvm.functions.Function1) r11
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x006b }
            r14 = r3
            r13 = r5
            r12 = r7
            r7 = r6
            r6 = r8
            goto L_0x00cd
        L_0x006b:
            r0 = move-exception
            r16 = r8
            r8 = r5
            r5 = r16
            goto L_0x0107
        L_0x0073:
            r4 = 0
            r6 = 0
            java.lang.Object r7 = r1.L$2
            kotlinx.coroutines.sync.Mutex r7 = (kotlinx.coroutines.sync.Mutex) r7
            java.lang.Object r8 = r1.L$1
            kotlin.jvm.functions.Function1 r8 = (kotlin.jvm.functions.Function1) r8
            java.lang.Object r9 = r1.L$0
            androidx.datastore.core.MultiProcessCoordinator r9 = (androidx.datastore.core.MultiProcessCoordinator) r9
            kotlin.ResultKt.throwOnFailure(r3)
            r10 = r7
            r11 = r8
            goto L_0x00a7
        L_0x0087:
            kotlin.ResultKt.throwOnFailure(r3)
            r9 = r17
            r4 = r18
            kotlinx.coroutines.sync.Mutex r6 = r9.inMemoryMutex
            r7 = 0
            r8 = 0
            r1.L$0 = r9
            r1.L$1 = r4
            r1.L$2 = r6
            r10 = 1
            r1.label = r10
            java.lang.Object r10 = r6.lock(r7, r1)
            if (r10 != r0) goto L_0x00a3
            return r0
        L_0x00a3:
            r11 = r4
            r10 = r6
            r6 = r7
            r4 = r8
        L_0x00a7:
            r7 = 0
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ all -> 0x0121 }
            java.io.File r12 = r9.getLockFile()     // Catch:{ all -> 0x0121 }
            r8.<init>(r12)     // Catch:{ all -> 0x0121 }
            java.io.Closeable r8 = (java.io.Closeable) r8     // Catch:{ all -> 0x0121 }
            r9 = r8
            java.io.FileOutputStream r9 = (java.io.FileOutputStream) r9     // Catch:{ all -> 0x0115 }
            r12 = 0
            r13 = 0
            androidx.datastore.core.MultiProcessCoordinator$Companion r14 = Companion     // Catch:{ all -> 0x0101 }
            r1.L$0 = r11     // Catch:{ all -> 0x0101 }
            r1.L$1 = r10     // Catch:{ all -> 0x0101 }
            r1.L$2 = r8     // Catch:{ all -> 0x0101 }
            r15 = 2
            r1.label = r15     // Catch:{ all -> 0x0101 }
            java.lang.Object r14 = r14.getExclusiveFileLockWithRetryIfDeadlock(r9, r1)     // Catch:{ all -> 0x0101 }
            if (r14 != r0) goto L_0x00cc
            return r0
        L_0x00cc:
            r9 = r8
        L_0x00cd:
            java.nio.channels.FileLock r14 = (java.nio.channels.FileLock) r14     // Catch:{ all -> 0x00fb }
            r8 = r14
            r1.L$0 = r10     // Catch:{ all -> 0x00f6 }
            r1.L$1 = r9     // Catch:{ all -> 0x00f6 }
            r1.L$2 = r8     // Catch:{ all -> 0x00f6 }
            r13 = 3
            r1.label = r13     // Catch:{ all -> 0x00f6 }
            java.lang.Object r13 = r11.invoke(r1)     // Catch:{ all -> 0x00f6 }
            if (r13 != r0) goto L_0x00e0
            return r0
        L_0x00e0:
            if (r8 == 0) goto L_0x00ed
            r8.release()     // Catch:{ all -> 0x00e8 }
            goto L_0x00ed
        L_0x00e8:
            r0 = move-exception
            r5 = r4
            r8 = r9
            r4 = r0
            goto L_0x0118
        L_0x00ed:
            kotlin.io.CloseableKt.closeFinally(r9, r5)     // Catch:{ all -> 0x00f4 }
            r10.unlock(r6)
            return r13
        L_0x00f4:
            r0 = move-exception
            goto L_0x0122
        L_0x00f6:
            r0 = move-exception
            r5 = r6
            r6 = r7
            r7 = r12
            goto L_0x0107
        L_0x00fb:
            r0 = move-exception
            r8 = r13
            r5 = r6
            r6 = r7
            r7 = r12
            goto L_0x0107
        L_0x0101:
            r0 = move-exception
            r5 = r6
            r6 = r7
            r9 = r8
            r7 = r12
            r8 = r13
        L_0x0107:
            if (r8 == 0) goto L_0x0114
            r8.release()     // Catch:{ all -> 0x010d }
            goto L_0x0114
        L_0x010d:
            r0 = move-exception
            r7 = r6
            r8 = r9
            r6 = r5
            r5 = r4
            r4 = r0
            goto L_0x0118
        L_0x0114:
            throw r0     // Catch:{ all -> 0x010d }
        L_0x0115:
            r0 = move-exception
            r5 = r4
            r4 = r0
        L_0x0118:
            throw r4     // Catch:{ all -> 0x0119 }
        L_0x0119:
            r0 = move-exception
            kotlin.io.CloseableKt.closeFinally(r8, r4)     // Catch:{ all -> 0x011e }
            throw r0     // Catch:{ all -> 0x011e }
        L_0x011e:
            r0 = move-exception
            r4 = r5
            goto L_0x0122
        L_0x0121:
            r0 = move-exception
        L_0x0122:
            r10.unlock(r6)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.MultiProcessCoordinator.lock(kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v31, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v8, resolved type: java.nio.channels.FileLock} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v32, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v8, resolved type: java.io.Closeable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v33, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v3, resolved type: kotlinx.coroutines.sync.Mutex} */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x01a4, code lost:
        r7.unlock(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0092, code lost:
        if (r6 == false) goto L_0x0097;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0094, code lost:
        r7.unlock(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0097, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0147, code lost:
        if (r9 == null) goto L_0x0156;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:?, code lost:
        r9.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x014d, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x014e, code lost:
        r2 = r0;
        r12 = r4;
        r4 = r6;
        r6 = r8;
        r15 = r10;
        r7 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:?, code lost:
        kotlin.io.CloseableKt.closeFinally(r10, (java.lang.Throwable) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x015a, code lost:
        if (r8 == false) goto L_0x015f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x015c, code lost:
        r11.unlock(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x015f, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0160, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0161, code lost:
        r7 = r11;
        r4 = r6;
        r6 = r8;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x01a4  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0105 A[Catch:{ IOException -> 0x00de, all -> 0x00d0, all -> 0x0170, all -> 0x00f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x011c A[Catch:{ IOException -> 0x00de, all -> 0x00d0, all -> 0x0170, all -> 0x00f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x011e A[Catch:{ IOException -> 0x00de, all -> 0x00d0, all -> 0x0170, all -> 0x00f4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x011f  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x017e A[SYNTHETIC, Splitter:B:98:0x017e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> java.lang.Object tryLock(kotlin.jvm.functions.Function2<? super java.lang.Boolean, ? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> r25, kotlin.coroutines.Continuation<? super T> r26) {
        /*
            r24 = this;
            r0 = r26
            boolean r1 = r0 instanceof androidx.datastore.core.MultiProcessCoordinator$tryLock$1
            if (r1 == 0) goto L_0x0018
            r1 = r0
            androidx.datastore.core.MultiProcessCoordinator$tryLock$1 r1 = (androidx.datastore.core.MultiProcessCoordinator$tryLock$1) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0018
            int r0 = r1.label
            int r0 = r0 - r3
            r1.label = r0
            r2 = r24
            goto L_0x001f
        L_0x0018:
            androidx.datastore.core.MultiProcessCoordinator$tryLock$1 r1 = new androidx.datastore.core.MultiProcessCoordinator$tryLock$1
            r2 = r24
            r1.<init>(r2, r0)
        L_0x001f:
            java.lang.Object r3 = r1.result
            java.lang.Object r4 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r0 = r1.label
            r5 = 0
            switch(r0) {
                case 0: goto L_0x0066;
                case 1: goto L_0x0054;
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
            r6 = 0
            r7 = 0
            boolean r8 = r1.Z$0
            java.lang.Object r0 = r1.L$2
            r9 = r0
            java.nio.channels.FileLock r9 = (java.nio.channels.FileLock) r9
            java.lang.Object r0 = r1.L$1
            r10 = r0
            java.io.Closeable r10 = (java.io.Closeable) r10
            java.lang.Object r0 = r1.L$0
            r11 = r0
            kotlinx.coroutines.sync.Mutex r11 = (kotlinx.coroutines.sync.Mutex) r11
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x004f }
            r0 = r3
            r19 = r0
            goto L_0x0145
        L_0x004f:
            r0 = move-exception
            r19 = r3
            goto L_0x017c
        L_0x0054:
            r4 = 0
            r0 = 0
            boolean r6 = r1.Z$0
            java.lang.Object r7 = r1.L$0
            kotlinx.coroutines.sync.Mutex r7 = (kotlinx.coroutines.sync.Mutex) r7
            kotlin.ResultKt.throwOnFailure(r3)     // Catch:{ all -> 0x0061 }
            r0 = r3
            goto L_0x0091
        L_0x0061:
            r0 = move-exception
            r19 = r3
            goto L_0x01a2
        L_0x0066:
            kotlin.ResultKt.throwOnFailure(r3)
            r6 = r24
            r7 = r25
            kotlinx.coroutines.sync.Mutex r11 = r6.inMemoryMutex
            r8 = 0
            r9 = 0
            boolean r10 = r11.tryLock(r8)
            r0 = r10
            r12 = 0
            r13 = 1
            r14 = 0
            if (r0 != 0) goto L_0x00a1
            java.lang.Boolean r0 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r14)     // Catch:{ all -> 0x0098 }
            r1.L$0 = r11     // Catch:{ all -> 0x0098 }
            r1.Z$0 = r10     // Catch:{ all -> 0x0098 }
            r1.label = r13     // Catch:{ all -> 0x0098 }
            java.lang.Object r0 = r7.invoke(r0, r1)     // Catch:{ all -> 0x0098 }
            if (r0 != r4) goto L_0x008e
            return r4
        L_0x008e:
            r5 = r8
            r6 = r10
            r7 = r11
        L_0x0091:
            if (r6 == 0) goto L_0x0097
            r7.unlock(r5)
        L_0x0097:
            return r0
        L_0x0098:
            r0 = move-exception
            r19 = r3
            r5 = r8
            r4 = r9
            r6 = r10
            r7 = r11
            goto L_0x01a2
        L_0x00a1:
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ all -> 0x019b }
            java.io.File r15 = r6.getLockFile()     // Catch:{ all -> 0x019b }
            r0.<init>(r15)     // Catch:{ all -> 0x019b }
            r15 = r0
            java.io.Closeable r15 = (java.io.Closeable) r15     // Catch:{ all -> 0x019b }
            r0 = r15
            java.io.FileInputStream r0 = (java.io.FileInputStream) r0     // Catch:{ all -> 0x018b }
            r16 = 0
            r17 = 0
            java.nio.channels.FileChannel r18 = r0.getChannel()     // Catch:{ IOException -> 0x00de, all -> 0x00d0 }
            r19 = 0
            r21 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r23 = 1
            java.nio.channels.FileLock r0 = r18.tryLock(r19, r21, r23)     // Catch:{ IOException -> 0x00de, all -> 0x00d0 }
            r2 = r0
            r19 = r3
            r3 = 1
            goto L_0x0122
        L_0x00d0:
            r0 = move-exception
            r19 = r3
            r5 = r8
            r6 = r9
            r8 = r10
            r4 = r12
            r10 = r15
            r7 = r16
            r9 = r17
            goto L_0x017c
        L_0x00de:
            r0 = move-exception
            java.lang.String r5 = r0.getMessage()     // Catch:{ all -> 0x0170 }
            if (r5 == 0) goto L_0x0100
            java.lang.String r13 = r6.LOCK_ERROR_MESSAGE     // Catch:{ all -> 0x0170 }
            r19 = r3
            r2 = 2
            r3 = 0
            boolean r5 = kotlin.text.StringsKt.startsWith$default(r5, r13, r14, r2, r3)     // Catch:{ all -> 0x00f4 }
            r2 = 1
            if (r5 != r2) goto L_0x0102
            r2 = 1
            goto L_0x0103
        L_0x00f4:
            r0 = move-exception
            r5 = r8
            r6 = r9
            r8 = r10
            r4 = r12
            r10 = r15
            r7 = r16
            r9 = r17
            goto L_0x017c
        L_0x0100:
            r19 = r3
        L_0x0102:
            r2 = r14
        L_0x0103:
            if (r2 != 0) goto L_0x011f
            java.lang.String r2 = r0.getMessage()     // Catch:{ all -> 0x00f4 }
            if (r2 == 0) goto L_0x0118
            java.lang.String r3 = DEADLOCK_ERROR_MESSAGE     // Catch:{ all -> 0x00f4 }
            r5 = 2
            r6 = 0
            boolean r2 = kotlin.text.StringsKt.startsWith$default(r2, r3, r14, r5, r6)     // Catch:{ all -> 0x00f4 }
            r3 = 1
            if (r2 != r3) goto L_0x0119
            r2 = r3
            goto L_0x011a
        L_0x0118:
            r3 = 1
        L_0x0119:
            r2 = r14
        L_0x011a:
            if (r2 == 0) goto L_0x011d
            goto L_0x0120
        L_0x011d:
            throw r0     // Catch:{ all -> 0x00f4 }
        L_0x011f:
            r3 = 1
        L_0x0120:
            r2 = r17
        L_0x0122:
            if (r2 == 0) goto L_0x0126
            r13 = r3
            goto L_0x0127
        L_0x0126:
            r13 = r14
        L_0x0127:
            java.lang.Boolean r0 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r13)     // Catch:{ all -> 0x0166 }
            r1.L$0 = r11     // Catch:{ all -> 0x0166 }
            r1.L$1 = r15     // Catch:{ all -> 0x0166 }
            r1.L$2 = r2     // Catch:{ all -> 0x0166 }
            r1.Z$0 = r10     // Catch:{ all -> 0x0166 }
            r5 = 2
            r1.label = r5     // Catch:{ all -> 0x0166 }
            java.lang.Object r0 = r7.invoke(r0, r1)     // Catch:{ all -> 0x0166 }
            if (r0 != r4) goto L_0x013d
            return r4
        L_0x013d:
            r5 = r8
            r6 = r9
            r8 = r10
            r4 = r12
            r10 = r15
            r7 = r16
            r9 = r2
        L_0x0145:
            if (r9 == 0) goto L_0x0156
            r9.release()     // Catch:{ all -> 0x014d }
            goto L_0x0156
        L_0x014d:
            r0 = move-exception
            r2 = r0
            r12 = r4
            r4 = r6
            r6 = r8
            r15 = r10
            r7 = r11
            goto L_0x0193
        L_0x0156:
            r3 = 0
            kotlin.io.CloseableKt.closeFinally(r10, r3)     // Catch:{ all -> 0x0160 }
            if (r8 == 0) goto L_0x015f
            r11.unlock(r5)
        L_0x015f:
            return r0
        L_0x0160:
            r0 = move-exception
            r7 = r11
            r4 = r6
            r6 = r8
            goto L_0x01a2
        L_0x0166:
            r0 = move-exception
            r5 = r8
            r6 = r9
            r8 = r10
            r4 = r12
            r10 = r15
            r7 = r16
            r9 = r2
            goto L_0x017c
        L_0x0170:
            r0 = move-exception
            r19 = r3
            r5 = r8
            r6 = r9
            r8 = r10
            r4 = r12
            r10 = r15
            r7 = r16
            r9 = r17
        L_0x017c:
            if (r9 == 0) goto L_0x018a
            r9.release()     // Catch:{ all -> 0x0182 }
            goto L_0x018a
        L_0x0182:
            r0 = move-exception
            r2 = r0
            r12 = r4
            r4 = r6
            r6 = r8
            r15 = r10
            r7 = r11
            goto L_0x0193
        L_0x018a:
            throw r0     // Catch:{ all -> 0x0182 }
        L_0x018b:
            r0 = move-exception
            r19 = r3
            r2 = r0
            r5 = r8
            r4 = r9
            r6 = r10
            r7 = r11
        L_0x0193:
            throw r2     // Catch:{ all -> 0x0194 }
        L_0x0194:
            r0 = move-exception
            kotlin.io.CloseableKt.closeFinally(r15, r2)     // Catch:{ all -> 0x0199 }
            throw r0     // Catch:{ all -> 0x0199 }
        L_0x0199:
            r0 = move-exception
            goto L_0x01a2
        L_0x019b:
            r0 = move-exception
            r19 = r3
            r5 = r8
            r4 = r9
            r6 = r10
            r7 = r11
        L_0x01a2:
            if (r6 == 0) goto L_0x01a7
            r7.unlock(r5)
        L_0x01a7:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.MultiProcessCoordinator.tryLock(kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public Object getVersion(Continuation<? super Integer> $completion) {
        if (!this.lazySharedCounter.isInitialized()) {
            return BuildersKt.withContext(this.context, new MultiProcessCoordinator$getVersion$$inlined$withLazyCounter$1(this, (Continuation) null), $completion);
        }
        Continuation<? super Integer> continuation = $completion;
        return Boxing.boxInt(getSharedCounter().getValue());
    }

    public Object incrementAndGetVersion(Continuation<? super Integer> $completion) {
        if (!this.lazySharedCounter.isInitialized()) {
            return BuildersKt.withContext(this.context, new MultiProcessCoordinator$incrementAndGetVersion$$inlined$withLazyCounter$1(this, (Continuation) null), $completion);
        }
        Continuation<? super Integer> continuation = $completion;
        return Boxing.boxInt(getSharedCounter().incrementAndGetValue());
    }

    private final File getLockFile() {
        return (File) this.lockFile$delegate.getValue();
    }

    /* access modifiers changed from: private */
    public final SharedCounter getSharedCounter() {
        return this.lazySharedCounter.getValue();
    }

    private static Object getSharedCounter$delegate(MultiProcessCoordinator multiProcessCoordinator) {
        return multiProcessCoordinator.lazySharedCounter;
    }

    /* access modifiers changed from: private */
    public final File fileWithSuffix(String suffix) {
        return new File(this.file.getAbsolutePath() + suffix);
    }

    /* access modifiers changed from: private */
    public final void createIfNotExists(File $this$createIfNotExists) {
        createParentDirectories($this$createIfNotExists);
        if (!$this$createIfNotExists.exists()) {
            $this$createIfNotExists.createNewFile();
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

    private final <T> Object withLazyCounter(Function2<? super SharedCounter, ? super Continuation<? super T>, ? extends Object> block, Continuation<? super T> $completion) {
        if (this.lazySharedCounter.isInitialized()) {
            return block.invoke(getSharedCounter(), $completion);
        }
        return BuildersKt.withContext(this.context, new MultiProcessCoordinator$withLazyCounter$2(block, this, (Continuation<? super MultiProcessCoordinator$withLazyCounter$2>) null), $completion);
    }

    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH@¢\u0006\u0002\u0010\fR\u000e\u0010\u0003\u001a\u00020\u0004XD¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006XD¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006XD¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Landroidx/datastore/core/MultiProcessCoordinator$Companion;", "", "()V", "DEADLOCK_ERROR_MESSAGE", "", "INITIAL_WAIT_MILLIS", "", "MAX_WAIT_MILLIS", "getExclusiveFileLockWithRetryIfDeadlock", "Ljava/nio/channels/FileLock;", "lockFileStream", "Ljava/io/FileOutputStream;", "(Ljava/io/FileOutputStream;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "datastore-core_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* compiled from: MultiProcessCoordinator.android.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* access modifiers changed from: private */
        /* JADX WARNING: Removed duplicated region for block: B:10:0x002d  */
        /* JADX WARNING: Removed duplicated region for block: B:11:0x0039  */
        /* JADX WARNING: Removed duplicated region for block: B:14:0x004d A[SYNTHETIC, Splitter:B:14:0x004d] */
        /* JADX WARNING: Removed duplicated region for block: B:29:0x008d  */
        /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object getExclusiveFileLockWithRetryIfDeadlock(java.io.FileOutputStream r14, kotlin.coroutines.Continuation<? super java.nio.channels.FileLock> r15) {
            /*
                r13 = this;
                boolean r0 = r15 instanceof androidx.datastore.core.MultiProcessCoordinator$Companion$getExclusiveFileLockWithRetryIfDeadlock$1
                if (r0 == 0) goto L_0x0014
                r0 = r15
                androidx.datastore.core.MultiProcessCoordinator$Companion$getExclusiveFileLockWithRetryIfDeadlock$1 r0 = (androidx.datastore.core.MultiProcessCoordinator$Companion$getExclusiveFileLockWithRetryIfDeadlock$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r1 = r1 & r2
                if (r1 == 0) goto L_0x0014
                int r15 = r0.label
                int r15 = r15 - r2
                r0.label = r15
                goto L_0x0019
            L_0x0014:
                androidx.datastore.core.MultiProcessCoordinator$Companion$getExclusiveFileLockWithRetryIfDeadlock$1 r0 = new androidx.datastore.core.MultiProcessCoordinator$Companion$getExclusiveFileLockWithRetryIfDeadlock$1
                r0.<init>(r13, r15)
            L_0x0019:
                java.lang.Object r15 = r0.result
                java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r2 = r0.label
                r3 = 2
                switch(r2) {
                    case 0: goto L_0x0039;
                    case 1: goto L_0x002d;
                    default: goto L_0x0025;
                }
            L_0x0025:
                java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
                java.lang.String r15 = "call to 'resume' before 'invoke' with coroutine"
                r14.<init>(r15)
                throw r14
            L_0x002d:
                long r4 = r0.J$0
                java.lang.Object r14 = r0.L$0
                java.io.FileOutputStream r14 = (java.io.FileOutputStream) r14
                kotlin.ResultKt.throwOnFailure(r15)
                r2 = r1
                r1 = r0
                goto L_0x0089
            L_0x0039:
                kotlin.ResultKt.throwOnFailure(r15)
                long r4 = androidx.datastore.core.MultiProcessCoordinator.INITIAL_WAIT_MILLIS
                r2 = r1
                r1 = r0
            L_0x0042:
                long r6 = androidx.datastore.core.MultiProcessCoordinator.MAX_WAIT_MILLIS
                int r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                java.lang.String r6 = "lockFileStream.getChanne…LUE, /* shared= */ false)"
                if (r0 > 0) goto L_0x008d
                java.nio.channels.FileChannel r7 = r14.getChannel()     // Catch:{ IOException -> 0x0061 }
                r10 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                r12 = 0
                r8 = 0
                java.nio.channels.FileLock r0 = r7.lock(r8, r10, r12)     // Catch:{ IOException -> 0x0061 }
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r6)     // Catch:{ IOException -> 0x0061 }
                return r0
            L_0x0061:
                r0 = move-exception
                java.lang.String r6 = r0.getMessage()
                r7 = 1
                r8 = 0
                if (r6 == 0) goto L_0x007a
                java.lang.CharSequence r6 = (java.lang.CharSequence) r6
                java.lang.String r9 = androidx.datastore.core.MultiProcessCoordinator.DEADLOCK_ERROR_MESSAGE
                java.lang.CharSequence r9 = (java.lang.CharSequence) r9
                r10 = 0
                boolean r6 = kotlin.text.StringsKt.contains$default((java.lang.CharSequence) r6, (java.lang.CharSequence) r9, (boolean) r8, (int) r3, (java.lang.Object) r10)
                if (r6 != r7) goto L_0x007a
                r8 = r7
            L_0x007a:
                if (r8 == 0) goto L_0x008c
                r1.L$0 = r14
                r1.J$0 = r4
                r1.label = r7
                java.lang.Object r0 = kotlinx.coroutines.DelayKt.delay(r4, r1)
                if (r0 != r2) goto L_0x0089
                return r2
            L_0x0089:
                long r6 = (long) r3
                long r4 = r4 * r6
                goto L_0x0042
            L_0x008c:
                throw r0
            L_0x008d:
                java.nio.channels.FileChannel r7 = r14.getChannel()
                r10 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                r12 = 0
                r8 = 0
                java.nio.channels.FileLock r0 = r7.lock(r8, r10, r12)
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r6)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.MultiProcessCoordinator.Companion.getExclusiveFileLockWithRetryIfDeadlock(java.io.FileOutputStream, kotlin.coroutines.Continuation):java.lang.Object");
        }
    }
}
