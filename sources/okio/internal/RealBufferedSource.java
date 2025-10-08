package okio.internal;

import java.io.EOFException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Typography;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Options;
import okio.PeekSource;
import okio.SegmentedByteString;
import okio.Sink;
import okio.Source;
import okio.Timeout;

@Metadata(d1 = {"\u0000j\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\r\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\b\u001a\r\u0010\u0003\u001a\u00020\u0004*\u00020\u0002H\b\u001a%\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006H\b\u001a\u001d\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\u0006H\b\u001a\u001d\u0010\r\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\u0006H\b\u001a\r\u0010\u000f\u001a\u00020\u0010*\u00020\u0002H\b\u001a-\u0010\u0011\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\b\u001a%\u0010\u0016\u001a\u00020\u0014*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0012\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\b\u001a\u001d\u0010\u0016\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00192\u0006\u0010\u0015\u001a\u00020\u0006H\b\u001a\u0015\u0010\u001a\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u001bH\b\u001a\r\u0010\u001c\u001a\u00020\b*\u00020\u0002H\b\u001a\r\u0010\u001d\u001a\u00020\u0018*\u00020\u0002H\b\u001a\u0015\u0010\u001d\u001a\u00020\u0018*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\b\u001a\r\u0010\u001e\u001a\u00020\f*\u00020\u0002H\b\u001a\u0015\u0010\u001e\u001a\u00020\f*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\b\u001a\r\u0010\u001f\u001a\u00020\u0006*\u00020\u0002H\b\u001a\u0015\u0010 \u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u0018H\b\u001a\u001d\u0010 \u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00192\u0006\u0010\u0015\u001a\u00020\u0006H\b\u001a\r\u0010!\u001a\u00020\u0006*\u00020\u0002H\b\u001a\r\u0010\"\u001a\u00020\u0014*\u00020\u0002H\b\u001a\r\u0010#\u001a\u00020\u0014*\u00020\u0002H\b\u001a\r\u0010$\u001a\u00020\u0006*\u00020\u0002H\b\u001a\r\u0010%\u001a\u00020\u0006*\u00020\u0002H\b\u001a\r\u0010&\u001a\u00020'*\u00020\u0002H\b\u001a\r\u0010(\u001a\u00020'*\u00020\u0002H\b\u001a\r\u0010)\u001a\u00020**\u00020\u0002H\b\u001a\u0015\u0010)\u001a\u00020**\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\b\u001a\r\u0010+\u001a\u00020\u0014*\u00020\u0002H\b\u001a\u000f\u0010,\u001a\u0004\u0018\u00010**\u00020\u0002H\b\u001a\u0015\u0010-\u001a\u00020**\u00020\u00022\u0006\u0010.\u001a\u00020\u0006H\b\u001a\u0015\u0010/\u001a\u00020\u0004*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\b\u001a\u0015\u00100\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\b\u001a\u0015\u00101\u001a\u00020\u0014*\u00020\u00022\u0006\u00102\u001a\u000203H\b\u001a\u0015\u00104\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0006H\b\u001a\r\u00105\u001a\u000206*\u00020\u0002H\b\u001a\r\u00107\u001a\u00020**\u00020\u0002H\b¨\u00068"}, d2 = {"commonClose", "", "Lokio/RealBufferedSource;", "commonExhausted", "", "commonIndexOf", "", "b", "", "fromIndex", "toIndex", "bytes", "Lokio/ByteString;", "commonIndexOfElement", "targetBytes", "commonPeek", "Lokio/BufferedSource;", "commonRangeEquals", "offset", "bytesOffset", "", "byteCount", "commonRead", "sink", "", "Lokio/Buffer;", "commonReadAll", "Lokio/Sink;", "commonReadByte", "commonReadByteArray", "commonReadByteString", "commonReadDecimalLong", "commonReadFully", "commonReadHexadecimalUnsignedLong", "commonReadInt", "commonReadIntLe", "commonReadLong", "commonReadLongLe", "commonReadShort", "", "commonReadShortLe", "commonReadUtf8", "", "commonReadUtf8CodePoint", "commonReadUtf8Line", "commonReadUtf8LineStrict", "limit", "commonRequest", "commonRequire", "commonSelect", "options", "Lokio/Options;", "commonSkip", "commonTimeout", "Lokio/Timeout;", "commonToString", "okio"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* renamed from: okio.internal.-RealBufferedSource  reason: invalid class name */
/* compiled from: RealBufferedSource.kt */
public final class RealBufferedSource {
    public static final long commonRead(okio.RealBufferedSource $this$commonRead, Buffer sink, long byteCount) {
        Intrinsics.checkNotNullParameter($this$commonRead, "<this>");
        Intrinsics.checkNotNullParameter(sink, "sink");
        if (!(byteCount >= 0)) {
            throw new IllegalArgumentException(("byteCount < 0: " + byteCount).toString());
        } else if ($this$commonRead.closed) {
            throw new IllegalStateException("closed".toString());
        } else if ($this$commonRead.bufferField.size() == 0 && $this$commonRead.source.read($this$commonRead.bufferField, 8192) == -1) {
            return -1;
        } else {
            return $this$commonRead.bufferField.read(sink, Math.min(byteCount, $this$commonRead.bufferField.size()));
        }
    }

    public static final boolean commonExhausted(okio.RealBufferedSource $this$commonExhausted) {
        Intrinsics.checkNotNullParameter($this$commonExhausted, "<this>");
        if (!$this$commonExhausted.closed) {
            return $this$commonExhausted.bufferField.exhausted() && $this$commonExhausted.source.read($this$commonExhausted.bufferField, 8192) == -1;
        }
        throw new IllegalStateException("closed".toString());
    }

    public static final void commonRequire(okio.RealBufferedSource $this$commonRequire, long byteCount) {
        Intrinsics.checkNotNullParameter($this$commonRequire, "<this>");
        if (!$this$commonRequire.request(byteCount)) {
            throw new EOFException();
        }
    }

    public static final boolean commonRequest(okio.RealBufferedSource $this$commonRequest, long byteCount) {
        Intrinsics.checkNotNullParameter($this$commonRequest, "<this>");
        if (!(byteCount >= 0)) {
            throw new IllegalArgumentException(("byteCount < 0: " + byteCount).toString());
        } else if (!$this$commonRequest.closed) {
            while ($this$commonRequest.bufferField.size() < byteCount) {
                if ($this$commonRequest.source.read($this$commonRequest.bufferField, 8192) == -1) {
                    return false;
                }
            }
            return true;
        } else {
            throw new IllegalStateException("closed".toString());
        }
    }

    public static final byte commonReadByte(okio.RealBufferedSource $this$commonReadByte) {
        Intrinsics.checkNotNullParameter($this$commonReadByte, "<this>");
        $this$commonReadByte.require(1);
        return $this$commonReadByte.bufferField.readByte();
    }

    public static final ByteString commonReadByteString(okio.RealBufferedSource $this$commonReadByteString) {
        Intrinsics.checkNotNullParameter($this$commonReadByteString, "<this>");
        $this$commonReadByteString.bufferField.writeAll($this$commonReadByteString.source);
        return $this$commonReadByteString.bufferField.readByteString();
    }

    public static final ByteString commonReadByteString(okio.RealBufferedSource $this$commonReadByteString, long byteCount) {
        Intrinsics.checkNotNullParameter($this$commonReadByteString, "<this>");
        $this$commonReadByteString.require(byteCount);
        return $this$commonReadByteString.bufferField.readByteString(byteCount);
    }

    public static final int commonSelect(okio.RealBufferedSource $this$commonSelect, Options options) {
        Intrinsics.checkNotNullParameter($this$commonSelect, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        if (!$this$commonSelect.closed) {
            do {
                int index = Buffer.selectPrefix($this$commonSelect.bufferField, options, true);
                switch (index) {
                    case -2:
                        break;
                    case -1:
                        return -1;
                    default:
                        $this$commonSelect.bufferField.skip((long) options.getByteStrings$okio()[index].size());
                        return index;
                }
            } while ($this$commonSelect.source.read($this$commonSelect.bufferField, 8192) != -1);
            return -1;
        }
        throw new IllegalStateException("closed".toString());
    }

    public static final byte[] commonReadByteArray(okio.RealBufferedSource $this$commonReadByteArray) {
        Intrinsics.checkNotNullParameter($this$commonReadByteArray, "<this>");
        $this$commonReadByteArray.bufferField.writeAll($this$commonReadByteArray.source);
        return $this$commonReadByteArray.bufferField.readByteArray();
    }

    public static final byte[] commonReadByteArray(okio.RealBufferedSource $this$commonReadByteArray, long byteCount) {
        Intrinsics.checkNotNullParameter($this$commonReadByteArray, "<this>");
        $this$commonReadByteArray.require(byteCount);
        return $this$commonReadByteArray.bufferField.readByteArray(byteCount);
    }

    public static final void commonReadFully(okio.RealBufferedSource $this$commonReadFully, byte[] sink) {
        Intrinsics.checkNotNullParameter($this$commonReadFully, "<this>");
        Intrinsics.checkNotNullParameter(sink, "sink");
        try {
            $this$commonReadFully.require((long) sink.length);
            $this$commonReadFully.bufferField.readFully(sink);
        } catch (EOFException e) {
            int offset = 0;
            while ($this$commonReadFully.bufferField.size() > 0) {
                int read = $this$commonReadFully.bufferField.read(sink, offset, (int) $this$commonReadFully.bufferField.size());
                if (read != -1) {
                    offset += read;
                } else {
                    throw new AssertionError();
                }
            }
            throw e;
        }
    }

    public static final int commonRead(okio.RealBufferedSource $this$commonRead, byte[] sink, int offset, int byteCount) {
        Intrinsics.checkNotNullParameter($this$commonRead, "<this>");
        Intrinsics.checkNotNullParameter(sink, "sink");
        SegmentedByteString.checkOffsetAndCount((long) sink.length, (long) offset, (long) byteCount);
        if ($this$commonRead.bufferField.size() == 0 && $this$commonRead.source.read($this$commonRead.bufferField, 8192) == -1) {
            return -1;
        }
        long j = (long) byteCount;
        return $this$commonRead.bufferField.read(sink, offset, (int) Math.min(j, $this$commonRead.bufferField.size()));
    }

    public static final void commonReadFully(okio.RealBufferedSource $this$commonReadFully, Buffer sink, long byteCount) {
        Intrinsics.checkNotNullParameter($this$commonReadFully, "<this>");
        Intrinsics.checkNotNullParameter(sink, "sink");
        try {
            $this$commonReadFully.require(byteCount);
            $this$commonReadFully.bufferField.readFully(sink, byteCount);
        } catch (EOFException e) {
            sink.writeAll($this$commonReadFully.bufferField);
            throw e;
        }
    }

    public static final long commonReadAll(okio.RealBufferedSource $this$commonReadAll, Sink sink) {
        Intrinsics.checkNotNullParameter($this$commonReadAll, "<this>");
        Intrinsics.checkNotNullParameter(sink, "sink");
        long totalBytesWritten = 0;
        while ($this$commonReadAll.source.read($this$commonReadAll.bufferField, 8192) != -1) {
            long emitByteCount = $this$commonReadAll.bufferField.completeSegmentByteCount();
            if (emitByteCount > 0) {
                totalBytesWritten += emitByteCount;
                sink.write($this$commonReadAll.bufferField, emitByteCount);
            }
        }
        if ($this$commonReadAll.bufferField.size() <= 0) {
            return totalBytesWritten;
        }
        long totalBytesWritten2 = totalBytesWritten + $this$commonReadAll.bufferField.size();
        sink.write($this$commonReadAll.bufferField, $this$commonReadAll.bufferField.size());
        return totalBytesWritten2;
    }

    public static final String commonReadUtf8(okio.RealBufferedSource $this$commonReadUtf8) {
        Intrinsics.checkNotNullParameter($this$commonReadUtf8, "<this>");
        $this$commonReadUtf8.bufferField.writeAll($this$commonReadUtf8.source);
        return $this$commonReadUtf8.bufferField.readUtf8();
    }

    public static final String commonReadUtf8(okio.RealBufferedSource $this$commonReadUtf8, long byteCount) {
        Intrinsics.checkNotNullParameter($this$commonReadUtf8, "<this>");
        $this$commonReadUtf8.require(byteCount);
        return $this$commonReadUtf8.bufferField.readUtf8(byteCount);
    }

    public static final String commonReadUtf8Line(okio.RealBufferedSource $this$commonReadUtf8Line) {
        Intrinsics.checkNotNullParameter($this$commonReadUtf8Line, "<this>");
        long newline = $this$commonReadUtf8Line.indexOf((byte) 10);
        if (newline != -1) {
            return Buffer.readUtf8Line($this$commonReadUtf8Line.bufferField, newline);
        }
        if ($this$commonReadUtf8Line.bufferField.size() != 0) {
            return $this$commonReadUtf8Line.readUtf8($this$commonReadUtf8Line.bufferField.size());
        }
        return null;
    }

    public static final String commonReadUtf8LineStrict(okio.RealBufferedSource $this$commonReadUtf8LineStrict, long limit) {
        okio.RealBufferedSource realBufferedSource = $this$commonReadUtf8LineStrict;
        long j = limit;
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        if (j >= 0) {
            long scanLength = j == Long.MAX_VALUE ? Long.MAX_VALUE : j + 1;
            long newline = realBufferedSource.indexOf((byte) 10, 0, scanLength);
            if (newline != -1) {
                return Buffer.readUtf8Line($this$commonReadUtf8LineStrict.bufferField, newline);
            }
            if (scanLength < Long.MAX_VALUE && realBufferedSource.request(scanLength) && $this$commonReadUtf8LineStrict.bufferField.getByte(scanLength - 1) == 13 && realBufferedSource.request(scanLength + 1) && $this$commonReadUtf8LineStrict.bufferField.getByte(scanLength) == 10) {
                return Buffer.readUtf8Line($this$commonReadUtf8LineStrict.bufferField, scanLength);
            }
            Buffer data = new Buffer();
            okio.RealBufferedSource this_$iv = $this$commonReadUtf8LineStrict;
            this_$iv.bufferField.copyTo(data, 0, Math.min((long) 32, this_$iv.bufferField.size()));
            throw new EOFException("\\n not found: limit=" + Math.min($this$commonReadUtf8LineStrict.bufferField.size(), j) + " content=" + data.readByteString().hex() + Typography.ellipsis);
        }
        throw new IllegalArgumentException(("limit < 0: " + j).toString());
    }

    public static final int commonReadUtf8CodePoint(okio.RealBufferedSource $this$commonReadUtf8CodePoint) {
        Intrinsics.checkNotNullParameter($this$commonReadUtf8CodePoint, "<this>");
        $this$commonReadUtf8CodePoint.require(1);
        int b0 = $this$commonReadUtf8CodePoint.bufferField.getByte(0);
        if ((b0 & 224) == 192) {
            $this$commonReadUtf8CodePoint.require(2);
        } else if ((b0 & 240) == 224) {
            $this$commonReadUtf8CodePoint.require(3);
        } else if ((b0 & 248) == 240) {
            $this$commonReadUtf8CodePoint.require(4);
        }
        return $this$commonReadUtf8CodePoint.bufferField.readUtf8CodePoint();
    }

    public static final short commonReadShort(okio.RealBufferedSource $this$commonReadShort) {
        Intrinsics.checkNotNullParameter($this$commonReadShort, "<this>");
        $this$commonReadShort.require(2);
        return $this$commonReadShort.bufferField.readShort();
    }

    public static final short commonReadShortLe(okio.RealBufferedSource $this$commonReadShortLe) {
        Intrinsics.checkNotNullParameter($this$commonReadShortLe, "<this>");
        $this$commonReadShortLe.require(2);
        return $this$commonReadShortLe.bufferField.readShortLe();
    }

    public static final int commonReadInt(okio.RealBufferedSource $this$commonReadInt) {
        Intrinsics.checkNotNullParameter($this$commonReadInt, "<this>");
        $this$commonReadInt.require(4);
        return $this$commonReadInt.bufferField.readInt();
    }

    public static final int commonReadIntLe(okio.RealBufferedSource $this$commonReadIntLe) {
        Intrinsics.checkNotNullParameter($this$commonReadIntLe, "<this>");
        $this$commonReadIntLe.require(4);
        return $this$commonReadIntLe.bufferField.readIntLe();
    }

    public static final long commonReadLong(okio.RealBufferedSource $this$commonReadLong) {
        Intrinsics.checkNotNullParameter($this$commonReadLong, "<this>");
        $this$commonReadLong.require(8);
        return $this$commonReadLong.bufferField.readLong();
    }

    public static final long commonReadLongLe(okio.RealBufferedSource $this$commonReadLongLe) {
        Intrinsics.checkNotNullParameter($this$commonReadLongLe, "<this>");
        $this$commonReadLongLe.require(8);
        return $this$commonReadLongLe.bufferField.readLongLe();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0034, code lost:
        if (r3 == 0) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0037, code lost:
        r2 = new java.lang.StringBuilder().append("Expected a digit or '-' but was 0x");
        r6 = java.lang.Integer.toString(r5, kotlin.text.CharsKt.checkRadix(kotlin.text.CharsKt.checkRadix(16)));
        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r6, "toString(this, checkRadix(radix))");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0062, code lost:
        throw new java.lang.NumberFormatException(r2.append(r6).toString());
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final long commonReadDecimalLong(okio.RealBufferedSource r9) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r9, r0)
            r0 = 0
            r1 = 1
            r9.require(r1)
            r3 = 0
        L_0x000d:
            long r5 = r3 + r1
            boolean r5 = r9.request(r5)
            if (r5 == 0) goto L_0x0063
            r5 = r9
            r6 = 0
            okio.Buffer r5 = r5.bufferField
            byte r5 = r5.getByte(r3)
            r6 = 48
            if (r5 < r6) goto L_0x0025
            r6 = 57
            if (r5 <= r6) goto L_0x0030
        L_0x0025:
            r6 = 0
            int r8 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r8 != 0) goto L_0x0032
            r8 = 45
            if (r5 == r8) goto L_0x0030
            goto L_0x0032
        L_0x0030:
            long r3 = r3 + r1
            goto L_0x000d
        L_0x0032:
            int r1 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r1 == 0) goto L_0x0037
            goto L_0x0063
        L_0x0037:
            java.lang.NumberFormatException r1 = new java.lang.NumberFormatException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r6 = "Expected a digit or '-' but was 0x"
            java.lang.StringBuilder r2 = r2.append(r6)
            r6 = 16
            int r6 = kotlin.text.CharsKt.checkRadix(r6)
            int r6 = kotlin.text.CharsKt.checkRadix(r6)
            java.lang.String r6 = java.lang.Integer.toString(r5, r6)
            java.lang.String r7 = "toString(this, checkRadix(radix))"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r6, r7)
            java.lang.StringBuilder r2 = r2.append(r6)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        L_0x0063:
            r1 = r9
            r2 = 0
            okio.Buffer r1 = r1.bufferField
            long r1 = r1.readDecimalLong()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal.RealBufferedSource.commonReadDecimalLong(okio.RealBufferedSource):long");
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x003e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final long commonReadHexadecimalUnsignedLong(okio.RealBufferedSource r7) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r0)
            r0 = 0
            r1 = 1
            r7.require(r1)
            r1 = 0
        L_0x000c:
            int r2 = r1 + 1
            long r2 = (long) r2
            boolean r2 = r7.request(r2)
            if (r2 == 0) goto L_0x006a
            r2 = r7
            r3 = 0
            okio.Buffer r2 = r2.bufferField
            long r3 = (long) r1
            byte r2 = r2.getByte(r3)
            r3 = 48
            if (r2 < r3) goto L_0x0026
            r3 = 57
            if (r2 <= r3) goto L_0x0037
        L_0x0026:
            r3 = 97
            if (r2 < r3) goto L_0x002e
            r3 = 102(0x66, float:1.43E-43)
            if (r2 <= r3) goto L_0x0037
        L_0x002e:
            r3 = 65
            if (r2 < r3) goto L_0x003b
            r3 = 70
            if (r2 <= r3) goto L_0x0037
            goto L_0x003b
        L_0x0037:
            int r1 = r1 + 1
            goto L_0x000c
        L_0x003b:
            if (r1 == 0) goto L_0x003e
            goto L_0x006a
        L_0x003e:
            java.lang.NumberFormatException r3 = new java.lang.NumberFormatException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Expected leading [0-9a-fA-F] character but was 0x"
            java.lang.StringBuilder r4 = r4.append(r5)
            r5 = 16
            int r5 = kotlin.text.CharsKt.checkRadix(r5)
            int r5 = kotlin.text.CharsKt.checkRadix(r5)
            java.lang.String r5 = java.lang.Integer.toString(r2, r5)
            java.lang.String r6 = "toString(this, checkRadix(radix))"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r5, r6)
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        L_0x006a:
            r2 = r7
            r3 = 0
            okio.Buffer r2 = r2.bufferField
            long r2 = r2.readHexadecimalUnsignedLong()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal.RealBufferedSource.commonReadHexadecimalUnsignedLong(okio.RealBufferedSource):long");
    }

    public static final void commonSkip(okio.RealBufferedSource $this$commonSkip, long byteCount) {
        Intrinsics.checkNotNullParameter($this$commonSkip, "<this>");
        long byteCount2 = byteCount;
        if (!$this$commonSkip.closed) {
            while (byteCount2 > 0) {
                if ($this$commonSkip.bufferField.size() == 0 && $this$commonSkip.source.read($this$commonSkip.bufferField, 8192) == -1) {
                    throw new EOFException();
                }
                long toSkip = Math.min(byteCount2, $this$commonSkip.bufferField.size());
                $this$commonSkip.bufferField.skip(toSkip);
                byteCount2 -= toSkip;
            }
            return;
        }
        throw new IllegalStateException("closed".toString());
    }

    public static final long commonIndexOf(okio.RealBufferedSource $this$commonIndexOf, byte b, long fromIndex, long toIndex) {
        okio.RealBufferedSource realBufferedSource = $this$commonIndexOf;
        long j = fromIndex;
        Intrinsics.checkNotNullParameter(realBufferedSource, "<this>");
        if (!realBufferedSource.closed) {
            boolean z = false;
            if (0 <= j && j <= toIndex) {
                z = true;
            }
            if (z) {
                long fromIndex2 = j;
                while (fromIndex2 < toIndex) {
                    long j2 = toIndex;
                    long result = $this$commonIndexOf.bufferField.indexOf(b, fromIndex2, j2);
                    if (result != -1) {
                        return result;
                    }
                    long lastBufferSize = $this$commonIndexOf.bufferField.size();
                    if (lastBufferSize >= j2) {
                        return -1;
                    }
                    if (realBufferedSource.source.read($this$commonIndexOf.bufferField, 8192) == -1) {
                        return -1;
                    }
                    fromIndex2 = Math.max(fromIndex2, lastBufferSize);
                }
                long j3 = toIndex;
                return -1;
            }
            throw new IllegalArgumentException(("fromIndex=" + j + " toIndex=" + toIndex).toString());
        }
        long j4 = toIndex;
        throw new IllegalStateException("closed".toString());
    }

    public static final long commonIndexOf(okio.RealBufferedSource $this$commonIndexOf, ByteString bytes, long fromIndex) {
        Intrinsics.checkNotNullParameter($this$commonIndexOf, "<this>");
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        long fromIndex2 = fromIndex;
        if (!$this$commonIndexOf.closed) {
            while (true) {
                long result = $this$commonIndexOf.bufferField.indexOf(bytes, fromIndex2);
                if (result != -1) {
                    return result;
                }
                long lastBufferSize = $this$commonIndexOf.bufferField.size();
                if ($this$commonIndexOf.source.read($this$commonIndexOf.bufferField, 8192) == -1) {
                    return -1;
                }
                fromIndex2 = Math.max(fromIndex2, (lastBufferSize - ((long) bytes.size())) + 1);
            }
        } else {
            throw new IllegalStateException("closed".toString());
        }
    }

    public static final long commonIndexOfElement(okio.RealBufferedSource $this$commonIndexOfElement, ByteString targetBytes, long fromIndex) {
        Intrinsics.checkNotNullParameter($this$commonIndexOfElement, "<this>");
        Intrinsics.checkNotNullParameter(targetBytes, "targetBytes");
        long fromIndex2 = fromIndex;
        if (!$this$commonIndexOfElement.closed) {
            while (true) {
                long result = $this$commonIndexOfElement.bufferField.indexOfElement(targetBytes, fromIndex2);
                if (result != -1) {
                    return result;
                }
                long lastBufferSize = $this$commonIndexOfElement.bufferField.size();
                if ($this$commonIndexOfElement.source.read($this$commonIndexOfElement.bufferField, 8192) == -1) {
                    return -1;
                }
                fromIndex2 = Math.max(fromIndex2, lastBufferSize);
            }
        } else {
            throw new IllegalStateException("closed".toString());
        }
    }

    public static final boolean commonRangeEquals(okio.RealBufferedSource $this$commonRangeEquals, long offset, ByteString bytes, int bytesOffset, int byteCount) {
        Intrinsics.checkNotNullParameter($this$commonRangeEquals, "<this>");
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        if ($this$commonRangeEquals.closed) {
            throw new IllegalStateException("closed".toString());
        } else if (offset < 0 || bytesOffset < 0 || byteCount < 0 || bytes.size() - bytesOffset < byteCount) {
            return false;
        } else {
            for (int i = 0; i < byteCount; i++) {
                long bufferOffset = ((long) i) + offset;
                if (!$this$commonRangeEquals.request(1 + bufferOffset) || $this$commonRangeEquals.bufferField.getByte(bufferOffset) != bytes.getByte(bytesOffset + i)) {
                    return false;
                }
            }
            return true;
        }
    }

    public static final BufferedSource commonPeek(okio.RealBufferedSource $this$commonPeek) {
        Intrinsics.checkNotNullParameter($this$commonPeek, "<this>");
        return Okio.buffer((Source) new PeekSource($this$commonPeek));
    }

    public static final void commonClose(okio.RealBufferedSource $this$commonClose) {
        Intrinsics.checkNotNullParameter($this$commonClose, "<this>");
        if (!$this$commonClose.closed) {
            $this$commonClose.closed = true;
            $this$commonClose.source.close();
            $this$commonClose.bufferField.clear();
        }
    }

    public static final Timeout commonTimeout(okio.RealBufferedSource $this$commonTimeout) {
        Intrinsics.checkNotNullParameter($this$commonTimeout, "<this>");
        return $this$commonTimeout.source.timeout();
    }

    public static final String commonToString(okio.RealBufferedSource $this$commonToString) {
        Intrinsics.checkNotNullParameter($this$commonToString, "<this>");
        return "buffer(" + $this$commonToString.source + ')';
    }
}
