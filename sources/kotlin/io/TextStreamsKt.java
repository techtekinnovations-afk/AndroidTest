package kotlin.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;

@Metadata(d1 = {"\u0000X\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\b\u001a\u0017\u0010\u0000\u001a\u00020\u0005*\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\b\u001a\u001c\u0010\u0007\u001a\u00020\b*\u00020\u00022\u0006\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\u001e\u0010\n\u001a\u00020\u000b*\u00020\u00022\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000b0\r\u001a\u0010\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010*\u00020\u0001\u001a\n\u0010\u0011\u001a\u00020\u0012*\u00020\u0013\u001a\u0010\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0015*\u00020\u0002\u001a\n\u0010\u0016\u001a\u00020\u000e*\u00020\u0002\u001a\u0017\u0010\u0016\u001a\u00020\u000e*\u00020\u00132\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\b\u001a\r\u0010\u0019\u001a\u00020\u001a*\u00020\u000eH\b\u001aB\u0010\u001b\u001a\u0002H\u001c\"\u0004\b\u0000\u0010\u001c*\u00020\u00022\u0018\u0010\u001d\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u0010\u0012\u0004\u0012\u0002H\u001c0\rH\bø\u0001\u0000\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u001e\u0002\u0007\n\u0005\b20\u0001¨\u0006\u001f"}, d2 = {"buffered", "Ljava/io/BufferedReader;", "Ljava/io/Reader;", "bufferSize", "", "Ljava/io/BufferedWriter;", "Ljava/io/Writer;", "copyTo", "", "out", "forEachLine", "", "action", "Lkotlin/Function1;", "", "lineSequence", "Lkotlin/sequences/Sequence;", "readBytes", "", "Ljava/net/URL;", "readLines", "", "readText", "charset", "Ljava/nio/charset/Charset;", "reader", "Ljava/io/StringReader;", "useLines", "T", "block", "(Ljava/io/Reader;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* compiled from: ReadWrite.kt */
public final class TextStreamsKt {
    static /* synthetic */ BufferedReader buffered$default(Reader $this$buffered_u24default, int bufferSize, int i, Object obj) {
        if ((i & 1) != 0) {
            bufferSize = 8192;
        }
        Intrinsics.checkNotNullParameter($this$buffered_u24default, "<this>");
        return $this$buffered_u24default instanceof BufferedReader ? (BufferedReader) $this$buffered_u24default : new BufferedReader($this$buffered_u24default, bufferSize);
    }

    private static final BufferedReader buffered(Reader $this$buffered, int bufferSize) {
        Intrinsics.checkNotNullParameter($this$buffered, "<this>");
        return $this$buffered instanceof BufferedReader ? (BufferedReader) $this$buffered : new BufferedReader($this$buffered, bufferSize);
    }

    static /* synthetic */ BufferedWriter buffered$default(Writer $this$buffered_u24default, int bufferSize, int i, Object obj) {
        if ((i & 1) != 0) {
            bufferSize = 8192;
        }
        Intrinsics.checkNotNullParameter($this$buffered_u24default, "<this>");
        return $this$buffered_u24default instanceof BufferedWriter ? (BufferedWriter) $this$buffered_u24default : new BufferedWriter($this$buffered_u24default, bufferSize);
    }

    private static final BufferedWriter buffered(Writer $this$buffered, int bufferSize) {
        Intrinsics.checkNotNullParameter($this$buffered, "<this>");
        return $this$buffered instanceof BufferedWriter ? (BufferedWriter) $this$buffered : new BufferedWriter($this$buffered, bufferSize);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0047, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0048, code lost:
        kotlin.io.CloseableKt.closeFinally(r2, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004b, code lost:
        throw r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final void forEachLine(java.io.Reader r11, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> r12) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r11, r0)
            java.lang.String r0 = "action"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r12, r0)
            r0 = r11
            r1 = 0
            boolean r2 = r0 instanceof java.io.BufferedReader
            if (r2 == 0) goto L_0x0015
            r2 = r0
            java.io.BufferedReader r2 = (java.io.BufferedReader) r2
            goto L_0x001c
        L_0x0015:
            java.io.BufferedReader r2 = new java.io.BufferedReader
            r3 = 8192(0x2000, float:1.14794E-41)
            r2.<init>(r0, r3)
        L_0x001c:
            java.io.Closeable r2 = (java.io.Closeable) r2
            r3 = r2
            java.io.BufferedReader r3 = (java.io.BufferedReader) r3     // Catch:{ all -> 0x0045 }
            r4 = 0
            kotlin.sequences.Sequence r5 = lineSequence(r3)     // Catch:{ all -> 0x0045 }
            r6 = 0
            r7 = r5
            r8 = 0
            java.util.Iterator r9 = r7.iterator()     // Catch:{ all -> 0x0045 }
        L_0x002d:
            boolean r10 = r9.hasNext()     // Catch:{ all -> 0x0045 }
            if (r10 == 0) goto L_0x003b
            java.lang.Object r10 = r9.next()     // Catch:{ all -> 0x0045 }
            r12.invoke(r10)     // Catch:{ all -> 0x0045 }
            goto L_0x002d
        L_0x003b:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x0045 }
            r3 = 0
            kotlin.io.CloseableKt.closeFinally(r2, r3)
            return
        L_0x0045:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x0047 }
        L_0x0047:
            r4 = move-exception
            kotlin.io.CloseableKt.closeFinally(r2, r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.io.TextStreamsKt.forEachLine(java.io.Reader, kotlin.jvm.functions.Function1):void");
    }

    public static final List<String> readLines(Reader $this$readLines) {
        Intrinsics.checkNotNullParameter($this$readLines, "<this>");
        ArrayList result = new ArrayList();
        forEachLine($this$readLines, new TextStreamsKt$readLines$1(result));
        return result;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003c, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0041, code lost:
        if (kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0) == false) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0049, code lost:
        kotlin.io.CloseableKt.closeFinally(r1, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004c, code lost:
        throw r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> T useLines(java.io.Reader r7, kotlin.jvm.functions.Function1<? super kotlin.sequences.Sequence<java.lang.String>, ? extends T> r8) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r0)
            java.lang.String r0 = "block"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r0)
            r0 = 0
            boolean r1 = r7 instanceof java.io.BufferedReader
            if (r1 == 0) goto L_0x0014
            r1 = r7
            java.io.BufferedReader r1 = (java.io.BufferedReader) r1
            goto L_0x001b
        L_0x0014:
            java.io.BufferedReader r1 = new java.io.BufferedReader
            r2 = 8192(0x2000, float:1.14794E-41)
            r1.<init>(r7, r2)
        L_0x001b:
            java.io.Closeable r1 = (java.io.Closeable) r1
            r2 = 0
            r3 = 1
            r4 = r1
            java.io.BufferedReader r4 = (java.io.BufferedReader) r4     // Catch:{ all -> 0x003a }
            r5 = 0
            kotlin.sequences.Sequence r6 = lineSequence(r4)     // Catch:{ all -> 0x003a }
            java.lang.Object r6 = r8.invoke(r6)     // Catch:{ all -> 0x003a }
            boolean r2 = kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(r3, r3, r2)
            if (r2 == 0) goto L_0x0036
            r2 = 0
            kotlin.io.CloseableKt.closeFinally(r1, r2)
            goto L_0x0039
        L_0x0036:
            r1.close()
        L_0x0039:
            return r6
        L_0x003a:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x003c }
        L_0x003c:
            r5 = move-exception
            boolean r2 = kotlin.internal.PlatformImplementationsKt.apiVersionIsAtLeast(r3, r3, r2)
            if (r2 != 0) goto L_0x0049
            r1.close()     // Catch:{ all -> 0x0047 }
            goto L_0x004c
        L_0x0047:
            r1 = move-exception
            goto L_0x004c
        L_0x0049:
            kotlin.io.CloseableKt.closeFinally(r1, r4)
        L_0x004c:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.io.TextStreamsKt.useLines(java.io.Reader, kotlin.jvm.functions.Function1):java.lang.Object");
    }

    private static final StringReader reader(String $this$reader) {
        Intrinsics.checkNotNullParameter($this$reader, "<this>");
        return new StringReader($this$reader);
    }

    public static final Sequence<String> lineSequence(BufferedReader $this$lineSequence) {
        Intrinsics.checkNotNullParameter($this$lineSequence, "<this>");
        return SequencesKt.constrainOnce(new LinesSequence($this$lineSequence));
    }

    public static final String readText(Reader $this$readText) {
        Intrinsics.checkNotNullParameter($this$readText, "<this>");
        StringWriter buffer = new StringWriter();
        copyTo$default($this$readText, buffer, 0, 2, (Object) null);
        String stringWriter = buffer.toString();
        Intrinsics.checkNotNullExpressionValue(stringWriter, "toString(...)");
        return stringWriter;
    }

    public static /* synthetic */ long copyTo$default(Reader reader, Writer writer, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 8192;
        }
        return copyTo(reader, writer, i);
    }

    public static final long copyTo(Reader $this$copyTo, Writer out, int bufferSize) {
        Intrinsics.checkNotNullParameter($this$copyTo, "<this>");
        Intrinsics.checkNotNullParameter(out, "out");
        long charsCopied = 0;
        char[] buffer = new char[bufferSize];
        int chars = $this$copyTo.read(buffer);
        while (chars >= 0) {
            out.write(buffer, 0, chars);
            charsCopied += (long) chars;
            chars = $this$copyTo.read(buffer);
        }
        return charsCopied;
    }

    private static final String readText(URL $this$readText, Charset charset) {
        Intrinsics.checkNotNullParameter($this$readText, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        return new String(readBytes($this$readText), charset);
    }

    static /* synthetic */ String readText$default(URL $this$readText_u24default, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkNotNullParameter($this$readText_u24default, "<this>");
        Intrinsics.checkNotNullParameter(charset, "charset");
        return new String(readBytes($this$readText_u24default), charset);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0021, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001d, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001e, code lost:
        kotlin.io.CloseableKt.closeFinally(r0, r1);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final byte[] readBytes(java.net.URL r4) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            java.io.InputStream r0 = r4.openStream()
            java.io.Closeable r0 = (java.io.Closeable) r0
            r1 = r0
            java.io.InputStream r1 = (java.io.InputStream) r1     // Catch:{ all -> 0x001b }
            r2 = 0
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)     // Catch:{ all -> 0x001b }
            byte[] r3 = kotlin.io.ByteStreamsKt.readBytes(r1)     // Catch:{ all -> 0x001b }
            r1 = 0
            kotlin.io.CloseableKt.closeFinally(r0, r1)
            return r3
        L_0x001b:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x001d }
        L_0x001d:
            r2 = move-exception
            kotlin.io.CloseableKt.closeFinally(r0, r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.io.TextStreamsKt.readBytes(java.net.URL):byte[]");
    }
}
