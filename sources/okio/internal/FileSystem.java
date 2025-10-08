package okio.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.collections.ArrayDeque;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import okio.BufferedSink;
import okio.FileMetadata;
import okio.Okio;
import okio.Path;
import okio.Source;

@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aI\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u00072\u0006\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH@ø\u0001\u0000¢\u0006\u0002\u0010\f\u001a\u001c\u0010\r\u001a\u00020\u0001*\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u0003H\u0000\u001a\u001c\u0010\u0010\u001a\u00020\u0001*\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\nH\u0000\u001a\u001c\u0010\u0013\u001a\u00020\u0001*\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\nH\u0000\u001a\u0014\u0010\u0016\u001a\u00020\n*\u00020\u00052\u0006\u0010\b\u001a\u00020\u0003H\u0000\u001a\"\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00030\u0018*\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH\u0000\u001a\u0014\u0010\u0019\u001a\u00020\u001a*\u00020\u00052\u0006\u0010\b\u001a\u00020\u0003H\u0000\u001a\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u0003*\u00020\u00052\u0006\u0010\b\u001a\u00020\u0003H\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\u001c"}, d2 = {"collectRecursively", "", "Lkotlin/sequences/SequenceScope;", "Lokio/Path;", "fileSystem", "Lokio/FileSystem;", "stack", "Lkotlin/collections/ArrayDeque;", "path", "followSymlinks", "", "postorder", "(Lkotlin/sequences/SequenceScope;Lokio/FileSystem;Lkotlin/collections/ArrayDeque;Lokio/Path;ZZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "commonCopy", "source", "target", "commonCreateDirectories", "dir", "mustCreate", "commonDeleteRecursively", "fileOrDirectory", "mustExist", "commonExists", "commonListRecursively", "Lkotlin/sequences/Sequence;", "commonMetadata", "Lokio/FileMetadata;", "symlinkTarget", "okio"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* renamed from: okio.internal.-FileSystem  reason: invalid class name */
/* compiled from: FileSystem.kt */
public final class FileSystem {
    public static final FileMetadata commonMetadata(okio.FileSystem $this$commonMetadata, Path path) throws IOException {
        Intrinsics.checkNotNullParameter($this$commonMetadata, "<this>");
        Intrinsics.checkNotNullParameter(path, "path");
        FileMetadata metadataOrNull = $this$commonMetadata.metadataOrNull(path);
        if (metadataOrNull != null) {
            return metadataOrNull;
        }
        throw new FileNotFoundException("no such file: " + path);
    }

    public static final boolean commonExists(okio.FileSystem $this$commonExists, Path path) throws IOException {
        Intrinsics.checkNotNullParameter($this$commonExists, "<this>");
        Intrinsics.checkNotNullParameter(path, "path");
        return $this$commonExists.metadataOrNull(path) != null;
    }

    public static final void commonCreateDirectories(okio.FileSystem $this$commonCreateDirectories, Path dir, boolean mustCreate) throws IOException {
        Intrinsics.checkNotNullParameter($this$commonCreateDirectories, "<this>");
        Intrinsics.checkNotNullParameter(dir, "dir");
        ArrayDeque directories = new ArrayDeque();
        Path path = dir;
        while (path != null && !$this$commonCreateDirectories.exists(path)) {
            directories.addFirst(path);
            path = path.parent();
        }
        if (!mustCreate || !directories.isEmpty()) {
            Iterator it = directories.iterator();
            while (it.hasNext()) {
                $this$commonCreateDirectories.createDirectory((Path) it.next());
            }
            return;
        }
        throw new IOException(dir + " already exist.");
    }

    public static final void commonDeleteRecursively(okio.FileSystem $this$commonDeleteRecursively, Path fileOrDirectory, boolean mustExist) throws IOException {
        Intrinsics.checkNotNullParameter($this$commonDeleteRecursively, "<this>");
        Intrinsics.checkNotNullParameter(fileOrDirectory, "fileOrDirectory");
        Iterator iterator = SequencesKt.sequence(new FileSystem$commonDeleteRecursively$sequence$1($this$commonDeleteRecursively, fileOrDirectory, (Continuation<? super FileSystem$commonDeleteRecursively$sequence$1>) null)).iterator();
        while (iterator.hasNext()) {
            $this$commonDeleteRecursively.delete((Path) iterator.next(), mustExist && !iterator.hasNext());
        }
    }

    public static final Sequence<Path> commonListRecursively(okio.FileSystem $this$commonListRecursively, Path dir, boolean followSymlinks) throws IOException {
        Intrinsics.checkNotNullParameter($this$commonListRecursively, "<this>");
        Intrinsics.checkNotNullParameter(dir, "dir");
        return SequencesKt.sequence(new FileSystem$commonListRecursively$1(dir, $this$commonListRecursively, followSymlinks, (Continuation<? super FileSystem$commonListRecursively$1>) null));
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0096, code lost:
        r10 = r8.listOrNull(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x009a, code lost:
        if (r10 != null) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x009c, code lost:
        r10 = kotlin.collections.CollectionsKt.emptyList();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00a7, code lost:
        if (r10.isEmpty() != false) goto L_0x014e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00a9, code lost:
        r11 = r6;
        r12 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00ac, code lost:
        if (r5 == false) goto L_0x00ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00b2, code lost:
        if (r7.contains(r11) != false) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00cd, code lost:
        throw new java.io.IOException("symlink cycle at " + r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00ce, code lost:
        r13 = symlinkTarget(r8, r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00d2, code lost:
        if (r13 != null) goto L_0x0149;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00d4, code lost:
        if (r5 != false) goto L_0x00d8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00d6, code lost:
        if (r12 != 0) goto L_0x014e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00d8, code lost:
        r7.addLast(r11);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r11 = r10.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00e0, code lost:
        r10 = r9;
        r9 = r8;
        r8 = r6;
        r6 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00e8, code lost:
        if (r6.hasNext() == false) goto L_0x0134;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00ea, code lost:
        r11 = r6.next();
        r12 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00f1, code lost:
        if (r5 == false) goto L_0x00f5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00f3, code lost:
        r13 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00f5, code lost:
        r13 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00f6, code lost:
        if (r3 == false) goto L_0x00f9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00f8, code lost:
        r12 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00f9, code lost:
        r1.L$0 = r10;
        r1.L$1 = r9;
        r1.L$2 = r7;
        r1.L$3 = r8;
        r1.L$4 = r6;
        r1.Z$0 = r5;
        r1.Z$1 = r3;
        r1.label = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x010a, code lost:
        r22 = r1;
        r18 = r7;
        r17 = r9;
        r16 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
        r1 = collectRecursively(r16, r17, r18, r11, r13, r12, r22);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x011c, code lost:
        r11 = r16;
        r10 = r17;
        r9 = r18;
        r7 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0124, code lost:
        if (r1 != r0) goto L_0x0127;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x0126, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0127, code lost:
        r1 = r7;
        r7 = r9;
        r9 = r10;
        r10 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x012c, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x012d, code lost:
        r1 = r22;
        r8 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0134, code lost:
        r11 = r10;
        r10 = r9;
        r9 = r7;
        r7 = r1;
        r9.removeLast();
        r6 = r8;
        r9 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x013e, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x013f, code lost:
        r9 = r7;
        r7 = r1;
        r8 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0143, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0144, code lost:
        r8 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0149, code lost:
        r11 = r13;
        r12 = r12 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x014e, code lost:
        if (r3 == false) goto L_0x0165;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0150, code lost:
        r1.L$0 = null;
        r1.L$1 = null;
        r1.L$2 = null;
        r1.L$3 = null;
        r1.L$4 = null;
        r1.label = 3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0162, code lost:
        if (r9.yield(r6, r1) != r0) goto L_0x0165;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0164, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0167, code lost:
        return kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0034  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0057  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x006f  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0027  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object collectRecursively(kotlin.sequences.SequenceScope<? super okio.Path> r16, okio.FileSystem r17, kotlin.collections.ArrayDeque<okio.Path> r18, okio.Path r19, boolean r20, boolean r21, kotlin.coroutines.Continuation<? super kotlin.Unit> r22) {
        /*
            r0 = r22
            boolean r1 = r0 instanceof okio.internal.FileSystem$collectRecursively$1
            if (r1 == 0) goto L_0x0016
            r1 = r0
            okio.internal.-FileSystem$collectRecursively$1 r1 = (okio.internal.FileSystem$collectRecursively$1) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r2 & r3
            if (r2 == 0) goto L_0x0016
            int r0 = r1.label
            int r0 = r0 - r3
            r1.label = r0
            goto L_0x001b
        L_0x0016:
            okio.internal.-FileSystem$collectRecursively$1 r1 = new okio.internal.-FileSystem$collectRecursively$1
            r1.<init>(r0)
        L_0x001b:
            java.lang.Object r2 = r1.result
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r1.label
            r4 = 1
            switch(r3) {
                case 0: goto L_0x006f;
                case 1: goto L_0x0057;
                case 2: goto L_0x0034;
                case 3: goto L_0x002f;
                default: goto L_0x0027;
            }
        L_0x0027:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002f:
            kotlin.ResultKt.throwOnFailure(r2)
            goto L_0x0165
        L_0x0034:
            boolean r3 = r1.Z$1
            boolean r5 = r1.Z$0
            java.lang.Object r6 = r1.L$4
            java.util.Iterator r6 = (java.util.Iterator) r6
            java.lang.Object r7 = r1.L$3
            okio.Path r7 = (okio.Path) r7
            java.lang.Object r8 = r1.L$2
            kotlin.collections.ArrayDeque r8 = (kotlin.collections.ArrayDeque) r8
            java.lang.Object r9 = r1.L$1
            okio.FileSystem r9 = (okio.FileSystem) r9
            java.lang.Object r10 = r1.L$0
            kotlin.sequences.SequenceScope r10 = (kotlin.sequences.SequenceScope) r10
            kotlin.ResultKt.throwOnFailure(r2)     // Catch:{ all -> 0x0054 }
            r15 = r8
            r8 = r7
            r7 = r15
            goto L_0x012b
        L_0x0054:
            r0 = move-exception
            goto L_0x0145
        L_0x0057:
            boolean r3 = r1.Z$1
            boolean r5 = r1.Z$0
            java.lang.Object r6 = r1.L$3
            okio.Path r6 = (okio.Path) r6
            java.lang.Object r7 = r1.L$2
            kotlin.collections.ArrayDeque r7 = (kotlin.collections.ArrayDeque) r7
            java.lang.Object r8 = r1.L$1
            okio.FileSystem r8 = (okio.FileSystem) r8
            java.lang.Object r9 = r1.L$0
            kotlin.sequences.SequenceScope r9 = (kotlin.sequences.SequenceScope) r9
            kotlin.ResultKt.throwOnFailure(r2)
            goto L_0x0095
        L_0x006f:
            kotlin.ResultKt.throwOnFailure(r2)
            r9 = r16
            r7 = r18
            r5 = r20
            r8 = r17
            r6 = r19
            r3 = r21
            if (r3 != 0) goto L_0x0096
            r1.L$0 = r9
            r1.L$1 = r8
            r1.L$2 = r7
            r1.L$3 = r6
            r1.Z$0 = r5
            r1.Z$1 = r3
            r1.label = r4
            java.lang.Object r10 = r9.yield(r6, r1)
            if (r10 != r0) goto L_0x0095
            return r0
        L_0x0095:
        L_0x0096:
            java.util.List r10 = r8.listOrNull(r6)
            if (r10 != 0) goto L_0x00a0
            java.util.List r10 = kotlin.collections.CollectionsKt.emptyList()
        L_0x00a0:
            r11 = r10
            java.util.Collection r11 = (java.util.Collection) r11
            boolean r11 = r11.isEmpty()
            if (r11 != 0) goto L_0x014e
            r11 = r6
            r12 = 0
        L_0x00ab:
            if (r5 == 0) goto L_0x00ce
            boolean r13 = r7.contains(r11)
            if (r13 != 0) goto L_0x00b5
            goto L_0x00ce
        L_0x00b5:
            java.io.IOException r0 = new java.io.IOException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r13 = "symlink cycle at "
            java.lang.StringBuilder r4 = r4.append(r13)
            java.lang.StringBuilder r4 = r4.append(r6)
            java.lang.String r4 = r4.toString()
            r0.<init>(r4)
            throw r0
        L_0x00ce:
            okio.Path r13 = symlinkTarget(r8, r11)
            if (r13 != 0) goto L_0x0149
            if (r5 != 0) goto L_0x00d8
            if (r12 != 0) goto L_0x014e
        L_0x00d8:
            r7.addLast(r11)
            java.util.Iterator r11 = r10.iterator()     // Catch:{ all -> 0x0143 }
            r10 = r9
            r9 = r8
            r8 = r6
            r6 = r11
        L_0x00e4:
            boolean r11 = r6.hasNext()     // Catch:{ all -> 0x013e }
            if (r11 == 0) goto L_0x0134
            java.lang.Object r11 = r6.next()     // Catch:{ all -> 0x013e }
            okio.Path r11 = (okio.Path) r11     // Catch:{ all -> 0x013e }
            r12 = 0
            if (r5 == 0) goto L_0x00f5
            r13 = r4
            goto L_0x00f6
        L_0x00f5:
            r13 = r12
        L_0x00f6:
            if (r3 == 0) goto L_0x00f9
            r12 = r4
        L_0x00f9:
            r1.L$0 = r10     // Catch:{ all -> 0x013e }
            r1.L$1 = r9     // Catch:{ all -> 0x013e }
            r1.L$2 = r7     // Catch:{ all -> 0x013e }
            r1.L$3 = r8     // Catch:{ all -> 0x013e }
            r1.L$4 = r6     // Catch:{ all -> 0x013e }
            r1.Z$0 = r5     // Catch:{ all -> 0x013e }
            r1.Z$1 = r3     // Catch:{ all -> 0x013e }
            r14 = 2
            r1.label = r14     // Catch:{ all -> 0x013e }
            r22 = r1
            r18 = r7
            r17 = r9
            r16 = r10
            r19 = r11
            r21 = r12
            r20 = r13
            java.lang.Object r1 = collectRecursively(r16, r17, r18, r19, r20, r21, r22)     // Catch:{ all -> 0x012c }
            r11 = r16
            r10 = r17
            r9 = r18
            r7 = r22
            if (r1 != r0) goto L_0x0127
            return r0
        L_0x0127:
            r1 = r7
            r7 = r9
            r9 = r10
            r10 = r11
        L_0x012b:
            goto L_0x00e4
        L_0x012c:
            r0 = move-exception
            r9 = r18
            r7 = r22
            r1 = r7
            r8 = r9
            goto L_0x0145
        L_0x0134:
            r11 = r10
            r10 = r9
            r9 = r7
            r7 = r1
            r9.removeLast()
            r6 = r8
            r9 = r11
            goto L_0x014e
        L_0x013e:
            r0 = move-exception
            r9 = r7
            r7 = r1
            r8 = r9
            goto L_0x0145
        L_0x0143:
            r0 = move-exception
            r8 = r7
        L_0x0145:
            r8.removeLast()
            throw r0
        L_0x0149:
            r11 = r13
            int r12 = r12 + 1
            goto L_0x00ab
        L_0x014e:
            if (r3 == 0) goto L_0x0168
            r3 = 0
            r1.L$0 = r3
            r1.L$1 = r3
            r1.L$2 = r3
            r1.L$3 = r3
            r1.L$4 = r3
            r3 = 3
            r1.label = r3
            java.lang.Object r3 = r9.yield(r6, r1)
            if (r3 != r0) goto L_0x0165
            return r0
        L_0x0165:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        L_0x0168:
            goto L_0x0165
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal.FileSystem.collectRecursively(kotlin.sequences.SequenceScope, okio.FileSystem, kotlin.collections.ArrayDeque, okio.Path, boolean, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final Path symlinkTarget(okio.FileSystem $this$symlinkTarget, Path path) throws IOException {
        Intrinsics.checkNotNullParameter($this$symlinkTarget, "<this>");
        Intrinsics.checkNotNullParameter(path, "path");
        Path target = $this$symlinkTarget.metadata(path).getSymlinkTarget();
        if (target == null) {
            return null;
        }
        Path parent = path.parent();
        Intrinsics.checkNotNull(parent);
        return parent.resolve(target);
    }

    public static final void commonCopy(okio.FileSystem $this$commonCopy, Path source, Path target) throws IOException {
        okio.FileSystem fileSystem = $this$commonCopy;
        Path path = target;
        Intrinsics.checkNotNullParameter(fileSystem, "<this>");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(path, TypedValues.AttributesType.S_TARGET);
        Closeable $this$use$iv = $this$commonCopy.source(source);
        Object result$iv = null;
        Throwable thrown$iv = null;
        try {
            Source bytesIn = (Source) $this$use$iv;
            Closeable $this$use$iv2 = Okio.buffer(fileSystem.sink(path));
            Object result$iv2 = null;
            Throwable thrown$iv2 = null;
            try {
                result$iv2 = Long.valueOf(((BufferedSink) $this$use$iv2).writeAll(bytesIn));
                if ($this$use$iv2 != null) {
                    try {
                        $this$use$iv2.close();
                    } catch (Throwable t$iv) {
                        thrown$iv2 = t$iv;
                    }
                }
            } catch (Throwable t$iv2) {
                ExceptionsKt.addSuppressed(thrown$iv2, t$iv2);
            }
            if (thrown$iv2 == null) {
                Intrinsics.checkNotNull(result$iv2);
                result$iv = Long.valueOf(((Number) result$iv2).longValue());
                if ($this$use$iv != null) {
                    try {
                        $this$use$iv.close();
                    } catch (Throwable t$iv3) {
                        thrown$iv = t$iv3;
                    }
                }
                if (thrown$iv == null) {
                    Intrinsics.checkNotNull(result$iv);
                    return;
                }
                throw thrown$iv;
            }
            throw thrown$iv2;
        } catch (Throwable t$iv4) {
            thrown$iv = t$iv4;
            if ($this$use$iv != null) {
                try {
                    $this$use$iv.close();
                } catch (Throwable t$iv5) {
                    ExceptionsKt.addSuppressed(thrown$iv, t$iv5);
                }
            }
        }
    }
}
