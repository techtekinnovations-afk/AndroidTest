package okio.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.UShort;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import kotlinx.coroutines.scheduling.WorkQueueKt;
import okio.BufferedSource;
import okio.FileMetadata;
import okio.FileSystem;
import okio.Path;
import okio.ZipFileSystem;

@Metadata(d1 = {"\u0000j\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\"\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u00132\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\u0017H\u0002\u001a\u001f\u0010\u0018\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u0001H\u0002¢\u0006\u0002\u0010\u001b\u001a.\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020 2\u0014\b\u0002\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020#0\"H\u0000\u001a\f\u0010$\u001a\u00020\u0015*\u00020%H\u0000\u001a\f\u0010&\u001a\u00020'*\u00020%H\u0002\u001a.\u0010(\u001a\u00020)*\u00020%2\u0006\u0010*\u001a\u00020\u00012\u0018\u0010+\u001a\u0014\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020)0,H\u0002\u001a\u0014\u0010-\u001a\u00020.*\u00020%2\u0006\u0010/\u001a\u00020.H\u0000\u001a\u0018\u00100\u001a\u0004\u0018\u00010.*\u00020%2\b\u0010/\u001a\u0004\u0018\u00010.H\u0002\u001a\u0014\u00101\u001a\u00020'*\u00020%2\u0006\u00102\u001a\u00020'H\u0002\u001a\f\u00103\u001a\u00020)*\u00020%H\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u000bXT¢\u0006\u0002\n\u0000\"\u000e\u0010\f\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\r\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u0018\u0010\u000e\u001a\u00020\u000f*\u00020\u00018BX\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u00064"}, d2 = {"BIT_FLAG_ENCRYPTED", "", "BIT_FLAG_UNSUPPORTED_MASK", "CENTRAL_FILE_HEADER_SIGNATURE", "COMPRESSION_METHOD_DEFLATED", "COMPRESSION_METHOD_STORED", "END_OF_CENTRAL_DIRECTORY_SIGNATURE", "HEADER_ID_EXTENDED_TIMESTAMP", "HEADER_ID_ZIP64_EXTENDED_INFO", "LOCAL_FILE_HEADER_SIGNATURE", "MAX_ZIP_ENTRY_AND_ARCHIVE_SIZE", "", "ZIP64_EOCD_RECORD_SIGNATURE", "ZIP64_LOCATOR_SIGNATURE", "hex", "", "getHex", "(I)Ljava/lang/String;", "buildIndex", "", "Lokio/Path;", "Lokio/internal/ZipEntry;", "entries", "", "dosDateTimeToEpochMillis", "date", "time", "(II)Ljava/lang/Long;", "openZip", "Lokio/ZipFileSystem;", "zipPath", "fileSystem", "Lokio/FileSystem;", "predicate", "Lkotlin/Function1;", "", "readEntry", "Lokio/BufferedSource;", "readEocdRecord", "Lokio/internal/EocdRecord;", "readExtra", "", "extraSize", "block", "Lkotlin/Function2;", "readLocalHeader", "Lokio/FileMetadata;", "basicMetadata", "readOrSkipLocalHeader", "readZip64EocdRecord", "regularRecord", "skipLocalHeader", "okio"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* compiled from: ZipFiles.kt */
public final class ZipFilesKt {
    private static final int BIT_FLAG_ENCRYPTED = 1;
    private static final int BIT_FLAG_UNSUPPORTED_MASK = 1;
    private static final int CENTRAL_FILE_HEADER_SIGNATURE = 33639248;
    public static final int COMPRESSION_METHOD_DEFLATED = 8;
    public static final int COMPRESSION_METHOD_STORED = 0;
    private static final int END_OF_CENTRAL_DIRECTORY_SIGNATURE = 101010256;
    private static final int HEADER_ID_EXTENDED_TIMESTAMP = 21589;
    private static final int HEADER_ID_ZIP64_EXTENDED_INFO = 1;
    private static final int LOCAL_FILE_HEADER_SIGNATURE = 67324752;
    private static final long MAX_ZIP_ENTRY_AND_ARCHIVE_SIZE = 4294967295L;
    private static final int ZIP64_EOCD_RECORD_SIGNATURE = 101075792;
    private static final int ZIP64_LOCATOR_SIGNATURE = 117853008;

    public static /* synthetic */ ZipFileSystem openZip$default(Path path, FileSystem fileSystem, Function1 function1, int i, Object obj) throws IOException {
        if ((i & 4) != 0) {
            function1 = ZipFilesKt$openZip$1.INSTANCE;
        }
        return openZip(path, fileSystem, function1);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:100:0x01be, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x01bf, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x01c0, code lost:
        r21 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0055, code lost:
        r13 = r7;
        r15 = readEocdRecord(r17);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x005f, code lost:
        r20 = r6;
        r6 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0068, code lost:
        r9 = r6.readUtf8((long) r15.getCommentByteCount());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        r6.close();
        r21 = r7;
        r6 = r13 - ((long) 20);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0076, code lost:
        if (r6 <= r18) goto L_0x0141;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0078, code lost:
        r10 = okio.Okio.buffer(r5.source(r6));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        r16 = (okio.BufferedSource) r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0091, code lost:
        if (r16.readIntLe() != ZIP64_LOCATOR_SIGNATURE) goto L_0x012a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0093, code lost:
        r8 = r16.readIntLe();
        r25 = r16.readLongLe();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00a2, code lost:
        r23 = r6;
        r6 = r16.readIntLe();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00a9, code lost:
        if (r6 != 1) goto L_0x011e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00ab, code lost:
        if (r8 != 0) goto L_0x011e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00ad, code lost:
        r19 = r6;
        r6 = r25;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        r25 = r6;
        r6 = okio.Okio.buffer(r5.source(r6));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        r0 = (okio.BufferedSource) r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00c6, code lost:
        r29 = r8;
        r8 = r0.readIntLe();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00d1, code lost:
        if (r8 != ZIP64_EOCD_RECORD_SIGNATURE) goto L_0x00e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        r15 = readZip64EocdRecord(r0, r15);
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        kotlin.io.CloseableKt.closeFinally(r6, (java.lang.Throwable) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00e0, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00e1, code lost:
        r7 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        r28 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0113, code lost:
        throw new java.io.IOException("bad zip: expected " + getHex(ZIP64_EOCD_RECORD_SIGNATURE) + " but was " + getHex(r8));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0114, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0115, code lost:
        r29 = r8;
        r7 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        throw r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0119, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        kotlin.io.CloseableKt.closeFinally(r6, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x011d, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x011e, code lost:
        r19 = r6;
        r29 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0129, code lost:
        throw new java.io.IOException("unsupported zip: spanned");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x012a, code lost:
        r23 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x012c, code lost:
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:?, code lost:
        kotlin.io.CloseableKt.closeFinally(r10, (java.lang.Throwable) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0134, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0135, code lost:
        r6 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0137, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0138, code lost:
        r23 = r6;
        r6 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:?, code lost:
        throw r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0141, code lost:
        r23 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0143, code lost:
        r6 = new java.util.ArrayList();
        r7 = okio.Okio.buffer(r5.source(r15.getCentralDirectoryOffset()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:?, code lost:
        r0 = (okio.BufferedSource) r7;
        r16 = 0;
        r25 = r15.getEntryCount();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0166, code lost:
        if (r16 >= r25) goto L_0x01a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0168, code lost:
        r10 = readEntry(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0176, code lost:
        if (r10.getOffset() >= r15.getCentralDirectoryOffset()) goto L_0x0196;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0182, code lost:
        if (r3.invoke(r10).booleanValue() == false) goto L_0x018d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0184, code lost:
        r19 = r0;
        r6.add(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x018d, code lost:
        r19 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x018f, code lost:
        r16 = r16 + 1;
        r0 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x0196, code lost:
        r19 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x019f, code lost:
        throw new java.io.IOException("bad zip: local file header offset >= central directory offset");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01a0, code lost:
        r19 = r0;
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:?, code lost:
        kotlin.io.CloseableKt.closeFinally(r7, (java.lang.Throwable) null);
        r3 = new okio.ZipFileSystem(r1, r2, buildIndex(r6), r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x01b2, code lost:
        kotlin.io.CloseableKt.closeFinally(r4, (java.lang.Throwable) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x01b6, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x01b7, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x01b8, code lost:
        r3 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:?, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x01ba, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:?, code lost:
        kotlin.io.CloseableKt.closeFinally(r7, r3);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final okio.ZipFileSystem openZip(okio.Path r31, okio.FileSystem r32, kotlin.jvm.functions.Function1<? super okio.internal.ZipEntry, java.lang.Boolean> r33) throws java.io.IOException {
        /*
            r1 = r31
            r2 = r32
            r3 = r33
            java.lang.String r0 = "zipPath"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r1, r0)
            java.lang.String r0 = "fileSystem"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r2, r0)
            java.lang.String r0 = "predicate"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r3, r0)
            okio.FileHandle r0 = r2.openReadOnly(r1)
            r4 = r0
            java.io.Closeable r4 = (java.io.Closeable) r4
            r0 = r4
            okio.FileHandle r0 = (okio.FileHandle) r0     // Catch:{ all -> 0x020f }
            r5 = r0
            r6 = 0
            long r7 = r5.size()     // Catch:{ all -> 0x020f }
            r0 = 22
            long r9 = (long) r0     // Catch:{ all -> 0x020f }
            long r7 = r7 - r9
            r9 = 0
            int r0 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r0 < 0) goto L_0x01f0
            r11 = 65536(0x10000, double:3.2379E-319)
            long r11 = r7 - r11
            long r11 = java.lang.Math.max(r11, r9)     // Catch:{ all -> 0x020f }
            r13 = 0
            r15 = 0
            r0 = 0
            r16 = r0
        L_0x003e:
            okio.Source r0 = r5.source(r7)     // Catch:{ all -> 0x020f }
            okio.BufferedSource r0 = okio.Okio.buffer((okio.Source) r0)     // Catch:{ all -> 0x020f }
            r17 = r0
            int r0 = r17.readIntLe()     // Catch:{ all -> 0x01e5 }
            r18 = r9
            r9 = 101010256(0x6054b50, float:2.506985E-35)
            if (r0 != r9) goto L_0x01c3
            r13 = r7
            okio.internal.EocdRecord r0 = readEocdRecord(r17)     // Catch:{ all -> 0x01e5 }
            r15 = r0
            int r0 = r15.getCommentByteCount()     // Catch:{ all -> 0x01e5 }
            long r9 = (long) r0
            r20 = r6
            r6 = r17
            java.lang.String r0 = r6.readUtf8(r9)     // Catch:{ all -> 0x01bf }
            r9 = r0
            r6.close()     // Catch:{ all -> 0x020f }
            r0 = 20
            r21 = r7
            long r6 = (long) r0     // Catch:{ all -> 0x020f }
            long r6 = r13 - r6
            int r0 = (r6 > r18 ? 1 : (r6 == r18 ? 0 : -1))
            if (r0 <= 0) goto L_0x0141
            okio.Source r0 = r5.source(r6)     // Catch:{ all -> 0x020f }
            okio.BufferedSource r0 = okio.Okio.buffer((okio.Source) r0)     // Catch:{ all -> 0x020f }
            r10 = r0
            java.io.Closeable r10 = (java.io.Closeable) r10     // Catch:{ all -> 0x020f }
            r0 = r10
            okio.BufferedSource r0 = (okio.BufferedSource) r0     // Catch:{ all -> 0x0137 }
            r16 = r0
            r17 = 0
            int r0 = r16.readIntLe()     // Catch:{ all -> 0x0137 }
            r8 = 117853008(0x7064b50, float:1.0103172E-34)
            if (r0 != r8) goto L_0x012a
            int r0 = r16.readIntLe()     // Catch:{ all -> 0x0137 }
            r8 = r0
            long r23 = r16.readLongLe()     // Catch:{ all -> 0x0137 }
            r25 = r23
            int r0 = r16.readIntLe()     // Catch:{ all -> 0x0137 }
            r19 = r0
            r0 = 1
            r23 = r6
            r6 = r19
            if (r6 != r0) goto L_0x011e
            if (r8 != 0) goto L_0x011e
            r19 = r6
            r6 = r25
            okio.Source r0 = r5.source(r6)     // Catch:{ all -> 0x0134 }
            okio.BufferedSource r0 = okio.Okio.buffer((okio.Source) r0)     // Catch:{ all -> 0x0134 }
            r25 = r6
            r6 = r0
            java.io.Closeable r6 = (java.io.Closeable) r6     // Catch:{ all -> 0x0134 }
            r0 = r6
            okio.BufferedSource r0 = (okio.BufferedSource) r0     // Catch:{ all -> 0x0114 }
            r7 = 0
            int r27 = r0.readIntLe()     // Catch:{ all -> 0x0114 }
            r28 = r27
            r27 = r7
            r7 = 101075792(0x6064b50, float:2.525793E-35)
            r29 = r8
            r8 = r28
            if (r8 != r7) goto L_0x00e3
            okio.internal.EocdRecord r7 = readZip64EocdRecord(r0, r15)     // Catch:{ all -> 0x00e0 }
            r15 = r7
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00e0 }
            r0 = 0
            kotlin.io.CloseableKt.closeFinally(r6, r0)     // Catch:{ all -> 0x0134 }
            goto L_0x012c
        L_0x00e0:
            r0 = move-exception
            r7 = r0
            goto L_0x0118
        L_0x00e3:
            r18 = r7
            java.io.IOException r7 = new java.io.IOException     // Catch:{ all -> 0x00e0 }
            r28 = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00e0 }
            r0.<init>()     // Catch:{ all -> 0x00e0 }
            r30 = r8
            java.lang.String r8 = "bad zip: expected "
            java.lang.StringBuilder r0 = r0.append(r8)     // Catch:{ all -> 0x00e0 }
            java.lang.String r8 = getHex(r18)     // Catch:{ all -> 0x00e0 }
            java.lang.StringBuilder r0 = r0.append(r8)     // Catch:{ all -> 0x00e0 }
            java.lang.String r8 = " but was "
            java.lang.StringBuilder r0 = r0.append(r8)     // Catch:{ all -> 0x00e0 }
            java.lang.String r8 = getHex(r30)     // Catch:{ all -> 0x00e0 }
            java.lang.StringBuilder r0 = r0.append(r8)     // Catch:{ all -> 0x00e0 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00e0 }
            r7.<init>(r0)     // Catch:{ all -> 0x00e0 }
            throw r7     // Catch:{ all -> 0x00e0 }
        L_0x0114:
            r0 = move-exception
            r29 = r8
            r7 = r0
        L_0x0118:
            throw r7     // Catch:{ all -> 0x0119 }
        L_0x0119:
            r0 = move-exception
            kotlin.io.CloseableKt.closeFinally(r6, r7)     // Catch:{ all -> 0x0134 }
            throw r0     // Catch:{ all -> 0x0134 }
        L_0x011e:
            r19 = r6
            r29 = r8
            java.io.IOException r0 = new java.io.IOException     // Catch:{ all -> 0x0134 }
            java.lang.String r6 = "unsupported zip: spanned"
            r0.<init>(r6)     // Catch:{ all -> 0x0134 }
            throw r0     // Catch:{ all -> 0x0134 }
        L_0x012a:
            r23 = r6
        L_0x012c:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0134 }
            r0 = 0
            kotlin.io.CloseableKt.closeFinally(r10, r0)     // Catch:{ all -> 0x020f }
            goto L_0x0143
        L_0x0134:
            r0 = move-exception
            r6 = r0
            goto L_0x013b
        L_0x0137:
            r0 = move-exception
            r23 = r6
            r6 = r0
        L_0x013b:
            throw r6     // Catch:{ all -> 0x013c }
        L_0x013c:
            r0 = move-exception
            kotlin.io.CloseableKt.closeFinally(r10, r6)     // Catch:{ all -> 0x020f }
            throw r0     // Catch:{ all -> 0x020f }
        L_0x0141:
            r23 = r6
        L_0x0143:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x020f }
            r0.<init>()     // Catch:{ all -> 0x020f }
            java.util.List r0 = (java.util.List) r0     // Catch:{ all -> 0x020f }
            r6 = r0
            long r7 = r15.getCentralDirectoryOffset()     // Catch:{ all -> 0x020f }
            okio.Source r0 = r5.source(r7)     // Catch:{ all -> 0x020f }
            okio.BufferedSource r0 = okio.Okio.buffer((okio.Source) r0)     // Catch:{ all -> 0x020f }
            r7 = r0
            java.io.Closeable r7 = (java.io.Closeable) r7     // Catch:{ all -> 0x020f }
            r0 = r7
            okio.BufferedSource r0 = (okio.BufferedSource) r0     // Catch:{ all -> 0x01b7 }
            r8 = 0
            r16 = 0
            long r25 = r15.getEntryCount()     // Catch:{ all -> 0x01b7 }
        L_0x0164:
            int r10 = (r16 > r25 ? 1 : (r16 == r25 ? 0 : -1))
            if (r10 >= 0) goto L_0x01a0
            okio.internal.ZipEntry r10 = readEntry(r0)     // Catch:{ all -> 0x01b7 }
            long r27 = r10.getOffset()     // Catch:{ all -> 0x01b7 }
            long r29 = r15.getCentralDirectoryOffset()     // Catch:{ all -> 0x01b7 }
            int r19 = (r27 > r29 ? 1 : (r27 == r29 ? 0 : -1))
            if (r19 >= 0) goto L_0x0196
            java.lang.Object r19 = r3.invoke(r10)     // Catch:{ all -> 0x01b7 }
            java.lang.Boolean r19 = (java.lang.Boolean) r19     // Catch:{ all -> 0x01b7 }
            boolean r19 = r19.booleanValue()     // Catch:{ all -> 0x01b7 }
            if (r19 == 0) goto L_0x018d
            r19 = r0
            r0 = r6
            java.util.Collection r0 = (java.util.Collection) r0     // Catch:{ all -> 0x01b7 }
            r0.add(r10)     // Catch:{ all -> 0x01b7 }
            goto L_0x018f
        L_0x018d:
            r19 = r0
        L_0x018f:
            r27 = 1
            long r16 = r16 + r27
            r0 = r19
            goto L_0x0164
        L_0x0196:
            r19 = r0
            java.io.IOException r0 = new java.io.IOException     // Catch:{ all -> 0x01b7 }
            java.lang.String r3 = "bad zip: local file header offset >= central directory offset"
            r0.<init>(r3)     // Catch:{ all -> 0x01b7 }
            throw r0     // Catch:{ all -> 0x01b7 }
        L_0x01a0:
            r19 = r0
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x01b7 }
            r0 = 0
            kotlin.io.CloseableKt.closeFinally(r7, r0)     // Catch:{ all -> 0x020f }
            java.util.Map r0 = buildIndex(r6)     // Catch:{ all -> 0x020f }
            okio.ZipFileSystem r3 = new okio.ZipFileSystem     // Catch:{ all -> 0x020f }
            r3.<init>(r1, r2, r0, r9)     // Catch:{ all -> 0x020f }
            r0 = 0
            kotlin.io.CloseableKt.closeFinally(r4, r0)
            return r3
        L_0x01b7:
            r0 = move-exception
            r3 = r0
            throw r3     // Catch:{ all -> 0x01ba }
        L_0x01ba:
            r0 = move-exception
            kotlin.io.CloseableKt.closeFinally(r7, r3)     // Catch:{ all -> 0x020f }
            throw r0     // Catch:{ all -> 0x020f }
        L_0x01bf:
            r0 = move-exception
            r21 = r7
            goto L_0x01ec
        L_0x01c3:
            r20 = r6
            r21 = r7
            r6 = r17
            r6.close()     // Catch:{ all -> 0x020f }
            r7 = -1
            long r7 = r21 + r7
            int r0 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r0 < 0) goto L_0x01dd
            r3 = r33
            r9 = r18
            r6 = r20
            goto L_0x003e
        L_0x01dd:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ all -> 0x020f }
            java.lang.String r3 = "not a zip: end of central directory signature not found"
            r0.<init>(r3)     // Catch:{ all -> 0x020f }
            throw r0     // Catch:{ all -> 0x020f }
        L_0x01e5:
            r0 = move-exception
            r20 = r6
            r21 = r7
            r6 = r17
        L_0x01ec:
            r6.close()     // Catch:{ all -> 0x020f }
            throw r0     // Catch:{ all -> 0x020f }
        L_0x01f0:
            r20 = r6
            java.io.IOException r0 = new java.io.IOException     // Catch:{ all -> 0x020f }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x020f }
            r3.<init>()     // Catch:{ all -> 0x020f }
            java.lang.String r6 = "not a zip: size="
            java.lang.StringBuilder r3 = r3.append(r6)     // Catch:{ all -> 0x020f }
            long r9 = r5.size()     // Catch:{ all -> 0x020f }
            java.lang.StringBuilder r3 = r3.append(r9)     // Catch:{ all -> 0x020f }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x020f }
            r0.<init>(r3)     // Catch:{ all -> 0x020f }
            throw r0     // Catch:{ all -> 0x020f }
        L_0x020f:
            r0 = move-exception
            r3 = r0
            throw r3     // Catch:{ all -> 0x0212 }
        L_0x0212:
            r0 = move-exception
            kotlin.io.CloseableKt.closeFinally(r4, r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal.ZipFilesKt.openZip(okio.Path, okio.FileSystem, kotlin.jvm.functions.Function1):okio.ZipFileSystem");
    }

    private static final Map<Path, ZipEntry> buildIndex(List<ZipEntry> entries) {
        Path root = Path.Companion.get$default(Path.Companion, "/", false, 1, (Object) null);
        Map result = MapsKt.mutableMapOf(TuplesKt.to(root, new ZipEntry(root, true, (String) null, 0, 0, 0, 0, (Long) null, 0, TypedValues.PositionType.TYPE_CURVE_FIT, (DefaultConstructorMarker) null)));
        for (ZipEntry entry : CollectionsKt.sortedWith(entries, new ZipFilesKt$buildIndex$$inlined$sortedBy$1())) {
            if (result.put(entry.getCanonicalPath(), entry) == null) {
                ZipEntry child = entry;
                while (true) {
                    Path parent = child.getCanonicalPath().parent();
                    if (parent == null) {
                        break;
                    }
                    Path parentPath = parent;
                    ZipEntry parentEntry = result.get(parentPath);
                    if (parentEntry != null) {
                        parentEntry.getChildren().add(child.getCanonicalPath());
                        break;
                    }
                    ZipEntry parentEntry2 = new ZipEntry(parentPath, true, (String) null, 0, 0, 0, 0, (Long) null, 0, TypedValues.PositionType.TYPE_CURVE_FIT, (DefaultConstructorMarker) null);
                    result.put(parentPath, parentEntry2);
                    parentEntry2.getChildren().add(child.getCanonicalPath());
                    child = parentEntry2;
                }
            }
        }
        return result;
    }

    public static final ZipEntry readEntry(BufferedSource $this$readEntry) throws IOException {
        BufferedSource bufferedSource = $this$readEntry;
        Intrinsics.checkNotNullParameter(bufferedSource, "<this>");
        int signature = bufferedSource.readIntLe();
        if (signature == CENTRAL_FILE_HEADER_SIGNATURE) {
            bufferedSource.skip(4);
            int bitFlag = bufferedSource.readShortLe() & 65535;
            if ((bitFlag & 1) == 0) {
                int compressionMethod = bufferedSource.readShortLe() & 65535;
                int readShortLe = bufferedSource.readShortLe() & 65535;
                short readShortLe2 = bufferedSource.readShortLe() & UShort.MAX_VALUE;
                Long lastModifiedAtMillis = dosDateTimeToEpochMillis(readShortLe2, readShortLe);
                long crc = ((long) bufferedSource.readIntLe()) & MAX_ZIP_ENTRY_AND_ARCHIVE_SIZE;
                Ref.LongRef compressedSize = new Ref.LongRef();
                compressedSize.element = ((long) bufferedSource.readIntLe()) & MAX_ZIP_ENTRY_AND_ARCHIVE_SIZE;
                Ref.LongRef size = new Ref.LongRef();
                size.element = ((long) bufferedSource.readIntLe()) & MAX_ZIP_ENTRY_AND_ARCHIVE_SIZE;
                short readShortLe3 = bufferedSource.readShortLe() & UShort.MAX_VALUE;
                short readShortLe4 = bufferedSource.readShortLe() & UShort.MAX_VALUE;
                int commentByteCount = 65535 & bufferedSource.readShortLe();
                bufferedSource.skip(8);
                Ref.LongRef offset = new Ref.LongRef();
                offset.element = ((long) bufferedSource.readIntLe()) & MAX_ZIP_ENTRY_AND_ARCHIVE_SIZE;
                String name = bufferedSource.readUtf8((long) readShortLe3);
                int i = signature;
                int i2 = bitFlag;
                if (!StringsKt.contains$default((CharSequence) name, 0, false, 2, (Object) null)) {
                    BufferedSource bufferedSource2 = $this$readEntry;
                    long result = 0;
                    if (size.element == MAX_ZIP_ENTRY_AND_ARCHIVE_SIZE) {
                        short s = readShortLe;
                        result = 0 + ((long) 8);
                    } else {
                        int time = readShortLe;
                    }
                    if (compressedSize.element == MAX_ZIP_ENTRY_AND_ARCHIVE_SIZE) {
                        result += (long) 8;
                    }
                    if (offset.element == MAX_ZIP_ENTRY_AND_ARCHIVE_SIZE) {
                        result += (long) 8;
                    }
                    int commentByteCount2 = commentByteCount;
                    Ref.BooleanRef hasZip64Extra = new Ref.BooleanRef();
                    Ref.LongRef compressedSize2 = compressedSize;
                    String name2 = name;
                    int commentByteCount3 = commentByteCount2;
                    Ref.LongRef offset2 = offset;
                    long requiredZip64ExtraSize = result;
                    readExtra(bufferedSource, readShortLe4, new ZipFilesKt$readEntry$1(hasZip64Extra, requiredZip64ExtraSize, size, bufferedSource, compressedSize2, offset2));
                    if (requiredZip64ExtraSize <= 0 || hasZip64Extra.element) {
                        String comment = bufferedSource.readUtf8((long) commentByteCount3);
                        short s2 = readShortLe2;
                        Ref.BooleanRef booleanRef = hasZip64Extra;
                        long j = requiredZip64ExtraSize;
                        Path canonicalPath = Path.Companion.get$default(Path.Companion, "/", false, 1, (Object) null).resolve(name2);
                        boolean isDirectory = StringsKt.endsWith$default(name2, "/", false, 2, (Object) null);
                        long j2 = compressedSize2.element;
                        boolean isDirectory2 = isDirectory;
                        short s3 = readShortLe4;
                        short s4 = readShortLe3;
                        short s5 = s2;
                        boolean isDirectory3 = isDirectory2;
                        long j3 = j2;
                        short s6 = s3;
                        boolean z = isDirectory3;
                        return new ZipEntry(canonicalPath, isDirectory3, comment, crc, j3, size.element, compressionMethod, lastModifiedAtMillis, offset2.element);
                    }
                    throw new IOException("bad zip: zip64 extra required but absent");
                }
                int i3 = readShortLe;
                throw new IOException("bad zip: filename contains 0x00");
            }
            throw new IOException("unsupported zip: general purpose bit flag=" + getHex(bitFlag));
        }
        throw new IOException("bad zip: expected " + getHex(CENTRAL_FILE_HEADER_SIGNATURE) + " but was " + getHex(signature));
    }

    private static final EocdRecord readEocdRecord(BufferedSource $this$readEocdRecord) throws IOException {
        int diskNumber = $this$readEocdRecord.readShortLe() & 65535;
        int diskWithCentralDir = $this$readEocdRecord.readShortLe() & 65535;
        long entryCount = (long) ($this$readEocdRecord.readShortLe() & UShort.MAX_VALUE);
        if (entryCount == ((long) ($this$readEocdRecord.readShortLe() & UShort.MAX_VALUE)) && diskNumber == 0 && diskWithCentralDir == 0) {
            $this$readEocdRecord.skip(4);
            return new EocdRecord(entryCount, MAX_ZIP_ENTRY_AND_ARCHIVE_SIZE & ((long) $this$readEocdRecord.readIntLe()), $this$readEocdRecord.readShortLe() & 65535);
        }
        throw new IOException("unsupported zip: spanned");
    }

    private static final EocdRecord readZip64EocdRecord(BufferedSource $this$readZip64EocdRecord, EocdRecord regularRecord) throws IOException {
        $this$readZip64EocdRecord.skip(12);
        int diskNumber = $this$readZip64EocdRecord.readIntLe();
        int diskWithCentralDirStart = $this$readZip64EocdRecord.readIntLe();
        long entryCount = $this$readZip64EocdRecord.readLongLe();
        if (entryCount == $this$readZip64EocdRecord.readLongLe() && diskNumber == 0 && diskWithCentralDirStart == 0) {
            $this$readZip64EocdRecord.skip(8);
            return new EocdRecord(entryCount, $this$readZip64EocdRecord.readLongLe(), regularRecord.getCommentByteCount());
        }
        throw new IOException("unsupported zip: spanned");
    }

    private static final void readExtra(BufferedSource $this$readExtra, int extraSize, Function2<? super Integer, ? super Long, Unit> block) {
        long remaining = (long) extraSize;
        while (remaining != 0) {
            if (remaining >= 4) {
                int headerId = $this$readExtra.readShortLe() & 65535;
                long dataSize = ((long) $this$readExtra.readShortLe()) & 65535;
                long remaining2 = remaining - ((long) 4);
                if (remaining2 >= dataSize) {
                    $this$readExtra.require(dataSize);
                    long sizeBefore = $this$readExtra.getBuffer().size();
                    block.invoke(Integer.valueOf(headerId), Long.valueOf(dataSize));
                    long fieldRemaining = ($this$readExtra.getBuffer().size() + dataSize) - sizeBefore;
                    if (fieldRemaining >= 0) {
                        if (fieldRemaining > 0) {
                            $this$readExtra.getBuffer().skip(fieldRemaining);
                        }
                        remaining = remaining2 - dataSize;
                    } else {
                        throw new IOException("unsupported zip: too many bytes processed for " + headerId);
                    }
                } else {
                    throw new IOException("bad zip: truncated value in extra field");
                }
            } else {
                throw new IOException("bad zip: truncated header in extra field");
            }
        }
    }

    public static final void skipLocalHeader(BufferedSource $this$skipLocalHeader) {
        Intrinsics.checkNotNullParameter($this$skipLocalHeader, "<this>");
        readOrSkipLocalHeader($this$skipLocalHeader, (FileMetadata) null);
    }

    public static final FileMetadata readLocalHeader(BufferedSource $this$readLocalHeader, FileMetadata basicMetadata) {
        Intrinsics.checkNotNullParameter($this$readLocalHeader, "<this>");
        Intrinsics.checkNotNullParameter(basicMetadata, "basicMetadata");
        FileMetadata readOrSkipLocalHeader = readOrSkipLocalHeader($this$readLocalHeader, basicMetadata);
        Intrinsics.checkNotNull(readOrSkipLocalHeader);
        return readOrSkipLocalHeader;
    }

    private static final FileMetadata readOrSkipLocalHeader(BufferedSource $this$readOrSkipLocalHeader, FileMetadata basicMetadata) {
        BufferedSource bufferedSource = $this$readOrSkipLocalHeader;
        Ref.ObjectRef lastModifiedAtMillis = new Ref.ObjectRef();
        lastModifiedAtMillis.element = basicMetadata != null ? basicMetadata.getLastModifiedAtMillis() : null;
        Ref.ObjectRef lastAccessedAtMillis = new Ref.ObjectRef();
        Ref.ObjectRef createdAtMillis = new Ref.ObjectRef();
        int signature = bufferedSource.readIntLe();
        if (signature == LOCAL_FILE_HEADER_SIGNATURE) {
            bufferedSource.skip(2);
            int bitFlag = bufferedSource.readShortLe() & 65535;
            if ((bitFlag & 1) == 0) {
                bufferedSource.skip(18);
                int extraSize = 65535 & bufferedSource.readShortLe();
                bufferedSource.skip(((long) bufferedSource.readShortLe()) & 65535);
                if (basicMetadata == null) {
                    bufferedSource.skip((long) extraSize);
                    return null;
                }
                readExtra(bufferedSource, extraSize, new ZipFilesKt$readOrSkipLocalHeader$1(bufferedSource, lastModifiedAtMillis, lastAccessedAtMillis, createdAtMillis));
                return new FileMetadata(basicMetadata.isRegularFile(), basicMetadata.isDirectory(), (Path) null, basicMetadata.getSize(), (Long) createdAtMillis.element, (Long) lastModifiedAtMillis.element, (Long) lastAccessedAtMillis.element, (Map) null, 128, (DefaultConstructorMarker) null);
            }
            throw new IOException("unsupported zip: general purpose bit flag=" + getHex(bitFlag));
        }
        throw new IOException("bad zip: expected " + getHex(LOCAL_FILE_HEADER_SIGNATURE) + " but was " + getHex(signature));
    }

    private static final Long dosDateTimeToEpochMillis(int date, int time) {
        if (time == -1) {
            return null;
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(14, 0);
        cal.set(((date >> 9) & WorkQueueKt.MASK) + 1980, ((date >> 5) & 15) - 1, date & 31, (time >> 11) & 31, (time >> 5) & 63, (time & 31) << 1);
        return Long.valueOf(cal.getTime().getTime());
    }

    private static final String getHex(int $this$hex) {
        StringBuilder append = new StringBuilder().append("0x");
        String num = Integer.toString($this$hex, CharsKt.checkRadix(16));
        Intrinsics.checkNotNullExpressionValue(num, "toString(this, checkRadix(radix))");
        return append.append(num).toString();
    }
}
