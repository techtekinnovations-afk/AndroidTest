package okio;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u000eH\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lokio/PeekSource;", "Lokio/Source;", "upstream", "Lokio/BufferedSource;", "(Lokio/BufferedSource;)V", "buffer", "Lokio/Buffer;", "closed", "", "expectedPos", "", "expectedSegment", "Lokio/Segment;", "pos", "", "close", "", "read", "sink", "byteCount", "timeout", "Lokio/Timeout;", "okio"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* compiled from: PeekSource.kt */
public final class PeekSource implements Source {
    private final Buffer buffer = this.upstream.getBuffer();
    private boolean closed;
    private int expectedPos;
    private Segment expectedSegment = this.buffer.head;
    private long pos;
    private final BufferedSource upstream;

    public PeekSource(BufferedSource upstream2) {
        Intrinsics.checkNotNullParameter(upstream2, "upstream");
        this.upstream = upstream2;
        Segment segment = this.buffer.head;
        this.expectedPos = segment != null ? segment.pos : -1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002d, code lost:
        if (r2 == r5.pos) goto L_0x0033;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long read(okio.Buffer r9, long r10) {
        /*
            r8 = this;
            java.lang.String r0 = "sink"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r9, r0)
            r0 = 0
            int r2 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            r3 = 1
            r4 = 0
            if (r2 < 0) goto L_0x000f
            r2 = r3
            goto L_0x0010
        L_0x000f:
            r2 = r4
        L_0x0010:
            if (r2 == 0) goto L_0x009e
            boolean r2 = r8.closed
            if (r2 != 0) goto L_0x008f
            okio.Segment r2 = r8.expectedSegment
            if (r2 == 0) goto L_0x0032
            okio.Segment r2 = r8.expectedSegment
            okio.Buffer r5 = r8.buffer
            okio.Segment r5 = r5.head
            if (r2 != r5) goto L_0x0030
            int r2 = r8.expectedPos
            okio.Buffer r5 = r8.buffer
            okio.Segment r5 = r5.head
            kotlin.jvm.internal.Intrinsics.checkNotNull(r5)
            int r5 = r5.pos
            if (r2 != r5) goto L_0x0030
            goto L_0x0032
        L_0x0030:
            r3 = r4
            goto L_0x0033
        L_0x0032:
        L_0x0033:
            if (r3 == 0) goto L_0x0080
            int r2 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r2 != 0) goto L_0x003a
            return r0
        L_0x003a:
            okio.BufferedSource r0 = r8.upstream
            long r1 = r8.pos
            r3 = 1
            long r1 = r1 + r3
            boolean r0 = r0.request(r1)
            if (r0 != 0) goto L_0x004a
            r0 = -1
            return r0
        L_0x004a:
            okio.Segment r0 = r8.expectedSegment
            if (r0 != 0) goto L_0x0065
            okio.Buffer r0 = r8.buffer
            okio.Segment r0 = r0.head
            if (r0 == 0) goto L_0x0065
            okio.Buffer r0 = r8.buffer
            okio.Segment r0 = r0.head
            r8.expectedSegment = r0
            okio.Buffer r0 = r8.buffer
            okio.Segment r0 = r0.head
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
            int r0 = r0.pos
            r8.expectedPos = r0
        L_0x0065:
            okio.Buffer r0 = r8.buffer
            long r0 = r0.size()
            long r2 = r8.pos
            long r0 = r0 - r2
            long r6 = java.lang.Math.min(r10, r0)
            okio.Buffer r2 = r8.buffer
            long r4 = r8.pos
            r3 = r9
            r2.copyTo((okio.Buffer) r3, (long) r4, (long) r6)
            long r0 = r8.pos
            long r0 = r0 + r6
            r8.pos = r0
            return r6
        L_0x0080:
            r3 = r9
            r9 = 0
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "Peek source is invalid because upstream source was used"
            java.lang.String r0 = r0.toString()
            r9.<init>(r0)
            throw r9
        L_0x008f:
            r3 = r9
            r9 = 0
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r0 = "closed"
            java.lang.String r0 = r0.toString()
            r9.<init>(r0)
            throw r9
        L_0x009e:
            r3 = r9
            r9 = 0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "byteCount < 0: "
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.StringBuilder r0 = r0.append(r10)
            java.lang.String r9 = r0.toString()
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r9 = r9.toString()
            r0.<init>(r9)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.PeekSource.read(okio.Buffer, long):long");
    }

    public Timeout timeout() {
        return this.upstream.timeout();
    }

    public void close() {
        this.closed = true;
    }
}
