package kotlin.io;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0004H\u0002J\u0010\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0010H\u0002J\u0018\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u0004H\u0002J\u0018\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fJ\b\u0010 \u001a\u00020!H\u0002J\b\u0010\"\u001a\u00020!H\u0002J\u0010\u0010#\u001a\u00020!2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0011\u001a\u00060\u0012j\u0002`\u0013X\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lkotlin/io/LineReader;", "", "()V", "BUFFER_SIZE", "", "byteBuf", "Ljava/nio/ByteBuffer;", "bytes", "", "charBuf", "Ljava/nio/CharBuffer;", "chars", "", "decoder", "Ljava/nio/charset/CharsetDecoder;", "directEOL", "", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "compactBytes", "decode", "endOfInput", "decodeEndOfInput", "nBytes", "nChars", "readLine", "", "inputStream", "Ljava/io/InputStream;", "charset", "Ljava/nio/charset/Charset;", "resetAll", "", "trimStringBuilder", "updateCharset", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* compiled from: Console.kt */
public final class LineReader {
    private static final int BUFFER_SIZE = 32;
    public static final LineReader INSTANCE = new LineReader();
    private static final ByteBuffer byteBuf;
    private static final byte[] bytes = new byte[32];
    private static final CharBuffer charBuf;
    private static final char[] chars = new char[32];
    private static CharsetDecoder decoder;
    private static boolean directEOL;
    private static final StringBuilder sb = new StringBuilder();

    private LineReader() {
    }

    static {
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        Intrinsics.checkNotNullExpressionValue(wrap, "wrap(...)");
        byteBuf = wrap;
        CharBuffer wrap2 = CharBuffer.wrap(chars);
        Intrinsics.checkNotNullExpressionValue(wrap2, "wrap(...)");
        charBuf = wrap2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003f, code lost:
        if (sb.length() != 0) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0041, code lost:
        r6 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0043, code lost:
        r6 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0044, code lost:
        if (r6 == false) goto L_0x004c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0046, code lost:
        if (r0 != 0) goto L_0x004c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0048, code lost:
        if (r2 != 0) goto L_0x004c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004b, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r1 = decodeEndOfInput(r0, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0022, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r0.charset(), (java.lang.Object) r13) == false) goto L_0x0024;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized java.lang.String readLine(java.io.InputStream r12, java.nio.charset.Charset r13) {
        /*
            r11 = this;
            monitor-enter(r11)
            java.lang.String r0 = "inputStream"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r12, r0)     // Catch:{ all -> 0x00de }
            java.lang.String r0 = "charset"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r13, r0)     // Catch:{ all -> 0x00de }
            java.nio.charset.CharsetDecoder r0 = decoder     // Catch:{ all -> 0x00de }
            r1 = 0
            if (r0 == 0) goto L_0x0024
            java.nio.charset.CharsetDecoder r0 = decoder     // Catch:{ all -> 0x00de }
            if (r0 != 0) goto L_0x001a
            java.lang.String r0 = "decoder"
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r0)     // Catch:{ all -> 0x00de }
            r0 = r1
        L_0x001a:
            java.nio.charset.Charset r0 = r0.charset()     // Catch:{ all -> 0x00de }
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r0, (java.lang.Object) r13)     // Catch:{ all -> 0x00de }
            if (r0 != 0) goto L_0x0027
        L_0x0024:
            r11.updateCharset(r13)     // Catch:{ all -> 0x00de }
        L_0x0027:
            r0 = 0
            r2 = 0
        L_0x0029:
            int r3 = r12.read()     // Catch:{ all -> 0x00de }
            r4 = 32
            r5 = 10
            r6 = -1
            r7 = 1
            r8 = 0
            if (r3 != r6) goto L_0x0051
            java.lang.StringBuilder r6 = sb     // Catch:{ all -> 0x00de }
            java.lang.CharSequence r6 = (java.lang.CharSequence) r6     // Catch:{ all -> 0x00de }
            int r6 = r6.length()     // Catch:{ all -> 0x00de }
            if (r6 != 0) goto L_0x0043
            r6 = r7
            goto L_0x0044
        L_0x0043:
            r6 = r8
        L_0x0044:
            if (r6 == 0) goto L_0x004c
            if (r0 != 0) goto L_0x004c
            if (r2 != 0) goto L_0x004c
            monitor-exit(r11)
            return r1
        L_0x004c:
            int r1 = r11.decodeEndOfInput(r0, r2)     // Catch:{ all -> 0x00de }
            goto L_0x0083
        L_0x0051:
            byte[] r6 = bytes     // Catch:{ all -> 0x00de }
            int r9 = r0 + 1
            byte r10 = (byte) r3     // Catch:{ all -> 0x00de }
            r6[r0] = r10     // Catch:{ all -> 0x00de }
            if (r3 == r5) goto L_0x0063
            if (r9 == r4) goto L_0x0063
            boolean r0 = directEOL     // Catch:{ all -> 0x00de }
            if (r0 != 0) goto L_0x0061
            goto L_0x0063
        L_0x0061:
            r0 = r9
            goto L_0x0029
        L_0x0063:
            java.nio.ByteBuffer r0 = byteBuf     // Catch:{ all -> 0x00de }
            r0.limit(r9)     // Catch:{ all -> 0x00de }
            java.nio.CharBuffer r0 = charBuf     // Catch:{ all -> 0x00de }
            r0.position(r2)     // Catch:{ all -> 0x00de }
            int r0 = r11.decode(r8)     // Catch:{ all -> 0x00de }
            r2 = r0
            if (r2 <= 0) goto L_0x00d8
            char[] r0 = chars     // Catch:{ all -> 0x00de }
            int r6 = r2 + -1
            char r0 = r0[r6]     // Catch:{ all -> 0x00de }
            if (r0 != r5) goto L_0x00d8
            java.nio.ByteBuffer r0 = byteBuf     // Catch:{ all -> 0x00de }
            r0.position(r8)     // Catch:{ all -> 0x00de }
            r1 = r2
            r0 = r9
        L_0x0083:
            if (r1 <= 0) goto L_0x009d
            char[] r2 = chars     // Catch:{ all -> 0x00de }
            int r3 = r1 + -1
            char r2 = r2[r3]     // Catch:{ all -> 0x00de }
            if (r2 != r5) goto L_0x009d
            int r1 = r1 + -1
            if (r1 <= 0) goto L_0x009d
            char[] r2 = chars     // Catch:{ all -> 0x00de }
            int r3 = r1 + -1
            char r2 = r2[r3]     // Catch:{ all -> 0x00de }
            r3 = 13
            if (r2 != r3) goto L_0x009d
            int r1 = r1 + -1
        L_0x009d:
            java.lang.StringBuilder r2 = sb     // Catch:{ all -> 0x00de }
            java.lang.CharSequence r2 = (java.lang.CharSequence) r2     // Catch:{ all -> 0x00de }
            int r2 = r2.length()     // Catch:{ all -> 0x00de }
            if (r2 != 0) goto L_0x00a8
            goto L_0x00a9
        L_0x00a8:
            r7 = r8
        L_0x00a9:
            if (r7 == 0) goto L_0x00b4
            java.lang.String r2 = new java.lang.String     // Catch:{ all -> 0x00de }
            char[] r3 = chars     // Catch:{ all -> 0x00de }
            r2.<init>(r3, r8, r1)     // Catch:{ all -> 0x00de }
            monitor-exit(r11)
            return r2
        L_0x00b4:
            java.lang.StringBuilder r2 = sb     // Catch:{ all -> 0x00de }
            char[] r3 = chars     // Catch:{ all -> 0x00de }
            r2.append(r3, r8, r1)     // Catch:{ all -> 0x00de }
            java.lang.StringBuilder r2 = sb     // Catch:{ all -> 0x00de }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00de }
            java.lang.String r3 = "toString(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r3)     // Catch:{ all -> 0x00de }
            java.lang.StringBuilder r3 = sb     // Catch:{ all -> 0x00de }
            int r3 = r3.length()     // Catch:{ all -> 0x00de }
            if (r3 <= r4) goto L_0x00d1
            r11.trimStringBuilder()     // Catch:{ all -> 0x00de }
        L_0x00d1:
            java.lang.StringBuilder r3 = sb     // Catch:{ all -> 0x00de }
            r3.setLength(r8)     // Catch:{ all -> 0x00de }
            monitor-exit(r11)
            return r2
        L_0x00d8:
            int r0 = r11.compactBytes()     // Catch:{ all -> 0x00de }
            goto L_0x0029
        L_0x00de:
            r12 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x00de }
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.io.LineReader.readLine(java.io.InputStream, java.nio.charset.Charset):java.lang.String");
    }

    private final int decode(boolean endOfInput) {
        while (true) {
            CharsetDecoder charsetDecoder = decoder;
            if (charsetDecoder == null) {
                Intrinsics.throwUninitializedPropertyAccessException("decoder");
                charsetDecoder = null;
            }
            CoderResult coderResult = charsetDecoder.decode(byteBuf, charBuf, endOfInput);
            Intrinsics.checkNotNullExpressionValue(coderResult, "decode(...)");
            if (coderResult.isError()) {
                resetAll();
                coderResult.throwException();
            }
            int nChars = charBuf.position();
            if (!coderResult.isOverflow()) {
                return nChars;
            }
            sb.append(chars, 0, nChars - 1);
            charBuf.position(0);
            charBuf.limit(32);
            charBuf.put(chars[nChars - 1]);
        }
    }

    private final int compactBytes() {
        ByteBuffer $this$compactBytes_u24lambda_u241 = byteBuf;
        $this$compactBytes_u24lambda_u241.compact();
        int position = $this$compactBytes_u24lambda_u241.position();
        int i = position;
        $this$compactBytes_u24lambda_u241.position(0);
        return position;
    }

    private final int decodeEndOfInput(int nBytes, int nChars) {
        byteBuf.limit(nBytes);
        charBuf.position(nChars);
        int decode = decode(true);
        int i = decode;
        CharsetDecoder charsetDecoder = decoder;
        if (charsetDecoder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("decoder");
            charsetDecoder = null;
        }
        charsetDecoder.reset();
        byteBuf.position(0);
        return decode;
    }

    private final void updateCharset(Charset charset) {
        CharsetDecoder newDecoder = charset.newDecoder();
        Intrinsics.checkNotNullExpressionValue(newDecoder, "newDecoder(...)");
        decoder = newDecoder;
        byteBuf.clear();
        charBuf.clear();
        byteBuf.put((byte) 10);
        byteBuf.flip();
        CharsetDecoder charsetDecoder = decoder;
        if (charsetDecoder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("decoder");
            charsetDecoder = null;
        }
        boolean z = false;
        charsetDecoder.decode(byteBuf, charBuf, false);
        if (charBuf.position() == 1 && charBuf.get(0) == 10) {
            z = true;
        }
        directEOL = z;
        resetAll();
    }

    private final void resetAll() {
        CharsetDecoder charsetDecoder = decoder;
        if (charsetDecoder == null) {
            Intrinsics.throwUninitializedPropertyAccessException("decoder");
            charsetDecoder = null;
        }
        charsetDecoder.reset();
        byteBuf.position(0);
        sb.setLength(0);
    }

    private final void trimStringBuilder() {
        sb.setLength(32);
        sb.trimToSize();
    }
}
