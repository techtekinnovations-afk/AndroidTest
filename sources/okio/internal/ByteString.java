package okio.internal;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okio.Base64;
import okio.Buffer;
import okio.SegmentedByteString;
import okio._JvmPlatformKt;

@Metadata(d1 = {"\u0000R\n\u0000\n\u0002\u0010\u0019\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0007H\u0002\u001a\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\tH\b\u001a\u0010\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0010H\u0002\u001a\r\u0010\u0011\u001a\u00020\u0012*\u00020\fH\b\u001a\r\u0010\u0013\u001a\u00020\u0012*\u00020\fH\b\u001a\u0015\u0010\u0014\u001a\u00020\u0007*\u00020\f2\u0006\u0010\u0015\u001a\u00020\fH\b\u001a-\u0010\u0016\u001a\u00020\u0017*\u00020\f2\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u0007H\b\u001a\u000f\u0010\u001c\u001a\u0004\u0018\u00010\f*\u00020\u0012H\b\u001a\r\u0010\u001d\u001a\u00020\f*\u00020\u0012H\b\u001a\r\u0010\u001e\u001a\u00020\f*\u00020\u0012H\b\u001a\u0015\u0010\u001f\u001a\u00020 *\u00020\f2\u0006\u0010!\u001a\u00020\tH\b\u001a\u0015\u0010\u001f\u001a\u00020 *\u00020\f2\u0006\u0010!\u001a\u00020\fH\b\u001a\u0017\u0010\"\u001a\u00020 *\u00020\f2\b\u0010\u0015\u001a\u0004\u0018\u00010#H\b\u001a\u0015\u0010$\u001a\u00020%*\u00020\f2\u0006\u0010&\u001a\u00020\u0007H\b\u001a\r\u0010'\u001a\u00020\u0007*\u00020\fH\b\u001a\r\u0010(\u001a\u00020\u0007*\u00020\fH\b\u001a\r\u0010)\u001a\u00020\u0012*\u00020\fH\b\u001a\u001d\u0010*\u001a\u00020\u0007*\u00020\f2\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010+\u001a\u00020\u0007H\b\u001a\r\u0010,\u001a\u00020\t*\u00020\fH\b\u001a\u001d\u0010-\u001a\u00020\u0007*\u00020\f2\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010+\u001a\u00020\u0007H\b\u001a\u001d\u0010-\u001a\u00020\u0007*\u00020\f2\u0006\u0010\u0015\u001a\u00020\f2\u0006\u0010+\u001a\u00020\u0007H\b\u001a-\u0010.\u001a\u00020 *\u00020\f2\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010/\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u0007H\b\u001a-\u0010.\u001a\u00020 *\u00020\f2\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\f2\u0006\u0010/\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u0007H\b\u001a\u0015\u00100\u001a\u00020 *\u00020\f2\u0006\u00101\u001a\u00020\tH\b\u001a\u0015\u00100\u001a\u00020 *\u00020\f2\u0006\u00101\u001a\u00020\fH\b\u001a\u001d\u00102\u001a\u00020\f*\u00020\f2\u0006\u00103\u001a\u00020\u00072\u0006\u00104\u001a\u00020\u0007H\b\u001a\r\u00105\u001a\u00020\f*\u00020\fH\b\u001a\r\u00106\u001a\u00020\f*\u00020\fH\b\u001a\r\u00107\u001a\u00020\t*\u00020\fH\b\u001a\u001d\u00108\u001a\u00020\f*\u00020\t2\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u0007H\b\u001a\r\u00109\u001a\u00020\u0012*\u00020\fH\b\u001a\r\u0010:\u001a\u00020\u0012*\u00020\fH\b\u001a$\u0010;\u001a\u00020\u0017*\u00020\f2\u0006\u0010<\u001a\u00020=2\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u0007H\u0000\"\u001c\u0010\u0000\u001a\u00020\u00018\u0000X\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005¨\u0006>"}, d2 = {"HEX_DIGIT_CHARS", "", "getHEX_DIGIT_CHARS$annotations", "()V", "getHEX_DIGIT_CHARS", "()[C", "codePointIndexToCharIndex", "", "s", "", "codePointCount", "commonOf", "Lokio/ByteString;", "data", "decodeHexDigit", "c", "", "commonBase64", "", "commonBase64Url", "commonCompareTo", "other", "commonCopyInto", "", "offset", "target", "targetOffset", "byteCount", "commonDecodeBase64", "commonDecodeHex", "commonEncodeUtf8", "commonEndsWith", "", "suffix", "commonEquals", "", "commonGetByte", "", "pos", "commonGetSize", "commonHashCode", "commonHex", "commonIndexOf", "fromIndex", "commonInternalArray", "commonLastIndexOf", "commonRangeEquals", "otherOffset", "commonStartsWith", "prefix", "commonSubstring", "beginIndex", "endIndex", "commonToAsciiLowercase", "commonToAsciiUppercase", "commonToByteArray", "commonToByteString", "commonToString", "commonUtf8", "commonWrite", "buffer", "Lokio/Buffer;", "okio"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* renamed from: okio.internal.-ByteString  reason: invalid class name */
/* compiled from: ByteString.kt */
public final class ByteString {
    private static final char[] HEX_DIGIT_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static /* synthetic */ void getHEX_DIGIT_CHARS$annotations() {
    }

    public static final String commonUtf8(okio.ByteString $this$commonUtf8) {
        Intrinsics.checkNotNullParameter($this$commonUtf8, "<this>");
        String result = $this$commonUtf8.getUtf8$okio();
        if (result != null) {
            return result;
        }
        String result2 = _JvmPlatformKt.toUtf8String($this$commonUtf8.internalArray$okio());
        $this$commonUtf8.setUtf8$okio(result2);
        return result2;
    }

    public static final String commonBase64(okio.ByteString $this$commonBase64) {
        Intrinsics.checkNotNullParameter($this$commonBase64, "<this>");
        return Base64.encodeBase64$default($this$commonBase64.getData$okio(), (byte[]) null, 1, (Object) null);
    }

    public static final String commonBase64Url(okio.ByteString $this$commonBase64Url) {
        Intrinsics.checkNotNullParameter($this$commonBase64Url, "<this>");
        return Base64.encodeBase64($this$commonBase64Url.getData$okio(), Base64.getBASE64_URL_SAFE());
    }

    public static final char[] getHEX_DIGIT_CHARS() {
        return HEX_DIGIT_CHARS;
    }

    public static final String commonHex(okio.ByteString $this$commonHex) {
        Intrinsics.checkNotNullParameter($this$commonHex, "<this>");
        char[] result = new char[($this$commonHex.getData$okio().length * 2)];
        int c = 0;
        for (int $this$shr$iv : $this$commonHex.getData$okio()) {
            int c2 = c + 1;
            result[c] = getHEX_DIGIT_CHARS()[($this$shr$iv >> 4) & 15];
            c = c2 + 1;
            result[c2] = getHEX_DIGIT_CHARS()[15 & $this$shr$iv];
        }
        return StringsKt.concatToString(result);
    }

    public static final okio.ByteString commonToAsciiLowercase(okio.ByteString $this$commonToAsciiLowercase) {
        Intrinsics.checkNotNullParameter($this$commonToAsciiLowercase, "<this>");
        int i = 0;
        while (i < $this$commonToAsciiLowercase.getData$okio().length) {
            byte c = $this$commonToAsciiLowercase.getData$okio()[i];
            if (c < 65 || c > 90) {
                i++;
            } else {
                byte[] data$okio = $this$commonToAsciiLowercase.getData$okio();
                byte[] lowercase = Arrays.copyOf(data$okio, data$okio.length);
                Intrinsics.checkNotNullExpressionValue(lowercase, "copyOf(this, size)");
                int i2 = i + 1;
                lowercase[i] = (byte) (c + 32);
                while (i2 < lowercase.length) {
                    byte c2 = lowercase[i2];
                    if (c2 < 65 || c2 > 90) {
                        i2++;
                    } else {
                        lowercase[i2] = (byte) (c2 + 32);
                        i2++;
                    }
                }
                return new okio.ByteString(lowercase);
            }
        }
        return $this$commonToAsciiLowercase;
    }

    public static final okio.ByteString commonToAsciiUppercase(okio.ByteString $this$commonToAsciiUppercase) {
        Intrinsics.checkNotNullParameter($this$commonToAsciiUppercase, "<this>");
        int i = 0;
        while (i < $this$commonToAsciiUppercase.getData$okio().length) {
            byte c = $this$commonToAsciiUppercase.getData$okio()[i];
            if (c < 97 || c > 122) {
                i++;
            } else {
                byte[] data$okio = $this$commonToAsciiUppercase.getData$okio();
                byte[] lowercase = Arrays.copyOf(data$okio, data$okio.length);
                Intrinsics.checkNotNullExpressionValue(lowercase, "copyOf(this, size)");
                int i2 = i + 1;
                lowercase[i] = (byte) (c - 32);
                while (i2 < lowercase.length) {
                    byte c2 = lowercase[i2];
                    if (c2 < 97 || c2 > 122) {
                        i2++;
                    } else {
                        lowercase[i2] = (byte) (c2 - 32);
                        i2++;
                    }
                }
                return new okio.ByteString(lowercase);
            }
        }
        return $this$commonToAsciiUppercase;
    }

    public static final okio.ByteString commonSubstring(okio.ByteString $this$commonSubstring, int beginIndex, int endIndex) {
        Intrinsics.checkNotNullParameter($this$commonSubstring, "<this>");
        int endIndex2 = SegmentedByteString.resolveDefaultParameter($this$commonSubstring, endIndex);
        boolean z = true;
        if (beginIndex >= 0) {
            if (endIndex2 <= $this$commonSubstring.getData$okio().length) {
                if (endIndex2 - beginIndex < 0) {
                    z = false;
                }
                if (!z) {
                    throw new IllegalArgumentException("endIndex < beginIndex".toString());
                } else if (beginIndex == 0 && endIndex2 == $this$commonSubstring.getData$okio().length) {
                    return $this$commonSubstring;
                } else {
                    return new okio.ByteString(ArraysKt.copyOfRange($this$commonSubstring.getData$okio(), beginIndex, endIndex2));
                }
            } else {
                throw new IllegalArgumentException(("endIndex > length(" + $this$commonSubstring.getData$okio().length + ')').toString());
            }
        } else {
            throw new IllegalArgumentException("beginIndex < 0".toString());
        }
    }

    public static final byte commonGetByte(okio.ByteString $this$commonGetByte, int pos) {
        Intrinsics.checkNotNullParameter($this$commonGetByte, "<this>");
        return $this$commonGetByte.getData$okio()[pos];
    }

    public static final int commonGetSize(okio.ByteString $this$commonGetSize) {
        Intrinsics.checkNotNullParameter($this$commonGetSize, "<this>");
        return $this$commonGetSize.getData$okio().length;
    }

    public static final byte[] commonToByteArray(okio.ByteString $this$commonToByteArray) {
        Intrinsics.checkNotNullParameter($this$commonToByteArray, "<this>");
        byte[] data$okio = $this$commonToByteArray.getData$okio();
        byte[] copyOf = Arrays.copyOf(data$okio, data$okio.length);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, size)");
        return copyOf;
    }

    public static final byte[] commonInternalArray(okio.ByteString $this$commonInternalArray) {
        Intrinsics.checkNotNullParameter($this$commonInternalArray, "<this>");
        return $this$commonInternalArray.getData$okio();
    }

    public static final boolean commonRangeEquals(okio.ByteString $this$commonRangeEquals, int offset, okio.ByteString other, int otherOffset, int byteCount) {
        Intrinsics.checkNotNullParameter($this$commonRangeEquals, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return other.rangeEquals(otherOffset, $this$commonRangeEquals.getData$okio(), offset, byteCount);
    }

    public static final boolean commonRangeEquals(okio.ByteString $this$commonRangeEquals, int offset, byte[] other, int otherOffset, int byteCount) {
        Intrinsics.checkNotNullParameter($this$commonRangeEquals, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return offset >= 0 && offset <= $this$commonRangeEquals.getData$okio().length - byteCount && otherOffset >= 0 && otherOffset <= other.length - byteCount && SegmentedByteString.arrayRangeEquals($this$commonRangeEquals.getData$okio(), offset, other, otherOffset, byteCount);
    }

    public static final void commonCopyInto(okio.ByteString $this$commonCopyInto, int offset, byte[] target, int targetOffset, int byteCount) {
        Intrinsics.checkNotNullParameter($this$commonCopyInto, "<this>");
        Intrinsics.checkNotNullParameter(target, TypedValues.AttributesType.S_TARGET);
        ArraysKt.copyInto($this$commonCopyInto.getData$okio(), target, targetOffset, offset, offset + byteCount);
    }

    public static final boolean commonStartsWith(okio.ByteString $this$commonStartsWith, okio.ByteString prefix) {
        Intrinsics.checkNotNullParameter($this$commonStartsWith, "<this>");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        return $this$commonStartsWith.rangeEquals(0, prefix, 0, prefix.size());
    }

    public static final boolean commonStartsWith(okio.ByteString $this$commonStartsWith, byte[] prefix) {
        Intrinsics.checkNotNullParameter($this$commonStartsWith, "<this>");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        return $this$commonStartsWith.rangeEquals(0, prefix, 0, prefix.length);
    }

    public static final boolean commonEndsWith(okio.ByteString $this$commonEndsWith, okio.ByteString suffix) {
        Intrinsics.checkNotNullParameter($this$commonEndsWith, "<this>");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        return $this$commonEndsWith.rangeEquals($this$commonEndsWith.size() - suffix.size(), suffix, 0, suffix.size());
    }

    public static final boolean commonEndsWith(okio.ByteString $this$commonEndsWith, byte[] suffix) {
        Intrinsics.checkNotNullParameter($this$commonEndsWith, "<this>");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        return $this$commonEndsWith.rangeEquals($this$commonEndsWith.size() - suffix.length, suffix, 0, suffix.length);
    }

    public static final int commonIndexOf(okio.ByteString $this$commonIndexOf, byte[] other, int fromIndex) {
        Intrinsics.checkNotNullParameter($this$commonIndexOf, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        int limit = $this$commonIndexOf.getData$okio().length - other.length;
        int i = Math.max(fromIndex, 0);
        if (i > limit) {
            return -1;
        }
        while (!SegmentedByteString.arrayRangeEquals($this$commonIndexOf.getData$okio(), i, other, 0, other.length)) {
            if (i == limit) {
                return -1;
            }
            i++;
        }
        return i;
    }

    public static final int commonLastIndexOf(okio.ByteString $this$commonLastIndexOf, okio.ByteString other, int fromIndex) {
        Intrinsics.checkNotNullParameter($this$commonLastIndexOf, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        return $this$commonLastIndexOf.lastIndexOf(other.internalArray$okio(), fromIndex);
    }

    public static final int commonLastIndexOf(okio.ByteString $this$commonLastIndexOf, byte[] other, int fromIndex) {
        Intrinsics.checkNotNullParameter($this$commonLastIndexOf, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        for (int i = Math.min(SegmentedByteString.resolveDefaultParameter($this$commonLastIndexOf, fromIndex), $this$commonLastIndexOf.getData$okio().length - other.length); -1 < i; i--) {
            if (SegmentedByteString.arrayRangeEquals($this$commonLastIndexOf.getData$okio(), i, other, 0, other.length)) {
                return i;
            }
        }
        return -1;
    }

    public static final boolean commonEquals(okio.ByteString $this$commonEquals, Object other) {
        Intrinsics.checkNotNullParameter($this$commonEquals, "<this>");
        if (other == $this$commonEquals) {
            return true;
        }
        if (!(other instanceof okio.ByteString)) {
            return false;
        }
        if (((okio.ByteString) other).size() != $this$commonEquals.getData$okio().length || !((okio.ByteString) other).rangeEquals(0, $this$commonEquals.getData$okio(), 0, $this$commonEquals.getData$okio().length)) {
            return false;
        }
        return true;
    }

    public static final int commonHashCode(okio.ByteString $this$commonHashCode) {
        Intrinsics.checkNotNullParameter($this$commonHashCode, "<this>");
        int result = $this$commonHashCode.getHashCode$okio();
        if (result != 0) {
            return result;
        }
        int it = Arrays.hashCode($this$commonHashCode.getData$okio());
        $this$commonHashCode.setHashCode$okio(it);
        return it;
    }

    public static final int commonCompareTo(okio.ByteString $this$commonCompareTo, okio.ByteString other) {
        Intrinsics.checkNotNullParameter($this$commonCompareTo, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        int sizeA = $this$commonCompareTo.size();
        int sizeB = other.size();
        int i = 0;
        int size = Math.min(sizeA, sizeB);
        while (i < size) {
            int byteA = $this$commonCompareTo.getByte(i) & 255;
            int byteB = other.getByte(i) & 255;
            if (byteA == byteB) {
                i++;
            } else if (byteA < byteB) {
                return -1;
            } else {
                return 1;
            }
        }
        if (sizeA == sizeB) {
            return 0;
        }
        if (sizeA < sizeB) {
            return -1;
        }
        return 1;
    }

    public static final okio.ByteString commonOf(byte[] data) {
        Intrinsics.checkNotNullParameter(data, "data");
        byte[] copyOf = Arrays.copyOf(data, data.length);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, size)");
        return new okio.ByteString(copyOf);
    }

    public static final okio.ByteString commonToByteString(byte[] $this$commonToByteString, int offset, int byteCount) {
        Intrinsics.checkNotNullParameter($this$commonToByteString, "<this>");
        int byteCount2 = SegmentedByteString.resolveDefaultParameter($this$commonToByteString, byteCount);
        SegmentedByteString.checkOffsetAndCount((long) $this$commonToByteString.length, (long) offset, (long) byteCount2);
        return new okio.ByteString(ArraysKt.copyOfRange($this$commonToByteString, offset, offset + byteCount2));
    }

    public static final okio.ByteString commonEncodeUtf8(String $this$commonEncodeUtf8) {
        Intrinsics.checkNotNullParameter($this$commonEncodeUtf8, "<this>");
        okio.ByteString byteString = new okio.ByteString(_JvmPlatformKt.asUtf8ToByteArray($this$commonEncodeUtf8));
        byteString.setUtf8$okio($this$commonEncodeUtf8);
        return byteString;
    }

    public static final okio.ByteString commonDecodeBase64(String $this$commonDecodeBase64) {
        Intrinsics.checkNotNullParameter($this$commonDecodeBase64, "<this>");
        byte[] decoded = Base64.decodeBase64ToArray($this$commonDecodeBase64);
        if (decoded != null) {
            return new okio.ByteString(decoded);
        }
        return null;
    }

    public static final okio.ByteString commonDecodeHex(String $this$commonDecodeHex) {
        Intrinsics.checkNotNullParameter($this$commonDecodeHex, "<this>");
        if ($this$commonDecodeHex.length() % 2 == 0) {
            byte[] result = new byte[($this$commonDecodeHex.length() / 2)];
            int length = result.length;
            for (int i = 0; i < length; i++) {
                result[i] = (byte) ((decodeHexDigit($this$commonDecodeHex.charAt(i * 2)) << 4) + decodeHexDigit($this$commonDecodeHex.charAt((i * 2) + 1)));
            }
            return new okio.ByteString(result);
        }
        throw new IllegalArgumentException(("Unexpected hex string: " + $this$commonDecodeHex).toString());
    }

    public static final void commonWrite(okio.ByteString $this$commonWrite, Buffer buffer, int offset, int byteCount) {
        Intrinsics.checkNotNullParameter($this$commonWrite, "<this>");
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        buffer.write($this$commonWrite.getData$okio(), offset, byteCount);
    }

    /* access modifiers changed from: private */
    public static final int decodeHexDigit(char c) {
        boolean z = true;
        if ('0' <= c && c < ':') {
            return c - '0';
        }
        if ('a' <= c && c < 'g') {
            return (c - 'a') + 10;
        }
        if ('A' > c || c >= 'G') {
            z = false;
        }
        if (z) {
            return (c - 'A') + 10;
        }
        throw new IllegalArgumentException("Unexpected hex digit: " + c);
    }

    public static final String commonToString(okio.ByteString $this$commonToString) {
        okio.ByteString byteString = $this$commonToString;
        Intrinsics.checkNotNullParameter(byteString, "<this>");
        boolean z = true;
        if (byteString.getData$okio().length == 0) {
            return "[size=0]";
        }
        int i = codePointIndexToCharIndex(byteString.getData$okio(), 64);
        if (i != -1) {
            String text = byteString.utf8();
            String substring = text.substring(0, i);
            Intrinsics.checkNotNullExpressionValue(substring, "this as java.lang.String…ing(startIndex, endIndex)");
            String safeText = StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(substring, "\\", "\\\\", false, 4, (Object) null), "\n", "\\n", false, 4, (Object) null), "\r", "\\r", false, 4, (Object) null);
            if (i < text.length()) {
                return "[size=" + byteString.getData$okio().length + " text=" + safeText + "…]";
            }
            return "[text=" + safeText + ']';
        } else if (byteString.getData$okio().length <= 64) {
            return "[hex=" + byteString.hex() + ']';
        } else {
            StringBuilder append = new StringBuilder().append("[size=").append(byteString.getData$okio().length).append(" hex=");
            okio.ByteString $this$commonSubstring$iv = $this$commonToString;
            int endIndex$iv = SegmentedByteString.resolveDefaultParameter($this$commonSubstring$iv, 64);
            if (endIndex$iv <= $this$commonSubstring$iv.getData$okio().length) {
                if (endIndex$iv - 0 < 0) {
                    z = false;
                }
                if (z) {
                    if (endIndex$iv != $this$commonSubstring$iv.getData$okio().length) {
                        $this$commonSubstring$iv = new okio.ByteString(ArraysKt.copyOfRange($this$commonSubstring$iv.getData$okio(), 0, endIndex$iv));
                    }
                    return append.append($this$commonSubstring$iv.hex()).append("…]").toString();
                }
                throw new IllegalArgumentException("endIndex < beginIndex".toString());
            }
            throw new IllegalArgumentException(("endIndex > length(" + $this$commonSubstring$iv.getData$okio().length + ')').toString());
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x0163, code lost:
        if ((127(0x7f, float:1.78E-43) <= 65533(0xfffd, float:9.1831E-41) && 65533(0xfffd, float:9.1831E-41) < 160(0xa0, float:2.24E-43)) != false) goto L_0x0165;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x01c8, code lost:
        if ((127(0x7f, float:1.78E-43) <= 65533(0xfffd, float:9.1831E-41) && 65533(0xfffd, float:9.1831E-41) < 160(0xa0, float:2.24E-43)) != false) goto L_0x01ca;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:0x0214, code lost:
        if ((127 <= r14 && r14 < 160) != false) goto L_0x0216;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:267:0x030b, code lost:
        if ((127(0x7f, float:1.78E-43) <= 65533(0xfffd, float:9.1831E-41) && 65533(0xfffd, float:9.1831E-41) < 160(0xa0, float:2.24E-43)) != false) goto L_0x030d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:304:0x0377, code lost:
        if ((127(0x7f, float:1.78E-43) <= 65533(0xfffd, float:9.1831E-41) && 65533(0xfffd, float:9.1831E-41) < 160(0xa0, float:2.24E-43)) != false) goto L_0x0379;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:338:0x03de, code lost:
        if ((127(0x7f, float:1.78E-43) <= 65533(0xfffd, float:9.1831E-41) && 65533(0xfffd, float:9.1831E-41) < 160(0xa0, float:2.24E-43)) != false) goto L_0x03e0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:375:0x043a, code lost:
        if ((127(0x7f, float:1.78E-43) <= 65533(0xfffd, float:9.1831E-41) && 65533(0xfffd, float:9.1831E-41) < 160(0xa0, float:2.24E-43)) != false) goto L_0x043c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:406:0x0483, code lost:
        if ((127 <= r12 && r12 < 160) != false) goto L_0x0485;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:497:0x0591, code lost:
        if ((127(0x7f, float:1.78E-43) <= 65533(0xfffd, float:9.1831E-41) && 65533(0xfffd, float:9.1831E-41) < 160(0xa0, float:2.24E-43)) != false) goto L_0x0593;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:534:0x05fd, code lost:
        if ((127(0x7f, float:1.78E-43) <= 65533(0xfffd, float:9.1831E-41) && 65533(0xfffd, float:9.1831E-41) < 160(0xa0, float:2.24E-43)) != false) goto L_0x05ff;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:571:0x0668, code lost:
        if ((127(0x7f, float:1.78E-43) <= 65533(0xfffd, float:9.1831E-41) && 65533(0xfffd, float:9.1831E-41) < 160(0xa0, float:2.24E-43)) != false) goto L_0x066a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:605:0x06d4, code lost:
        if ((127(0x7f, float:1.78E-43) <= 65533(0xfffd, float:9.1831E-41) && 65533(0xfffd, float:9.1831E-41) < 160(0xa0, float:2.24E-43)) != false) goto L_0x06d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:642:0x0730, code lost:
        if ((127(0x7f, float:1.78E-43) <= 65533(0xfffd, float:9.1831E-41) && 65533(0xfffd, float:9.1831E-41) < 160(0xa0, float:2.24E-43)) != false) goto L_0x0732;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:675:0x0782, code lost:
        if ((127(0x7f, float:1.78E-43) <= 65533(0xfffd, float:9.1831E-41) && 65533(0xfffd, float:9.1831E-41) < 160(0xa0, float:2.24E-43)) != false) goto L_0x0784;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:706:0x07cb, code lost:
        if ((127 <= r13 && r13 < 160) != false) goto L_0x07cd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:738:0x0819, code lost:
        if ((127(0x7f, float:1.78E-43) <= 65533(0xfffd, float:9.1831E-41) && 65533(0xfffd, float:9.1831E-41) < 160(0xa0, float:2.24E-43)) != false) goto L_0x081b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x00f1, code lost:
        if ((127(0x7f, float:1.78E-43) <= 65533(0xfffd, float:9.1831E-41) && 65533(0xfffd, float:9.1831E-41) < 160(0xa0, float:2.24E-43)) != false) goto L_0x00f3;
     */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x0156  */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x01bb  */
    /* JADX WARNING: Removed duplicated region for block: B:177:0x0207  */
    /* JADX WARNING: Removed duplicated region for block: B:215:0x026e  */
    /* JADX WARNING: Removed duplicated region for block: B:261:0x02fe  */
    /* JADX WARNING: Removed duplicated region for block: B:298:0x036a  */
    /* JADX WARNING: Removed duplicated region for block: B:332:0x03d1  */
    /* JADX WARNING: Removed duplicated region for block: B:369:0x042d  */
    /* JADX WARNING: Removed duplicated region for block: B:437:0x04d7  */
    /* JADX WARNING: Removed duplicated region for block: B:491:0x0584  */
    /* JADX WARNING: Removed duplicated region for block: B:528:0x05f0  */
    /* JADX WARNING: Removed duplicated region for block: B:565:0x065b  */
    /* JADX WARNING: Removed duplicated region for block: B:599:0x06c7  */
    /* JADX WARNING: Removed duplicated region for block: B:636:0x0723  */
    /* JADX WARNING: Removed duplicated region for block: B:669:0x0775  */
    /* JADX WARNING: Removed duplicated region for block: B:756:0x0448 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:758:0x005b A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:764:0x00a5 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:768:0x04f6 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:773:0x016e A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:776:0x059f A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:779:0x01d3 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:783:0x060b A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:785:0x0222 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:789:0x0676 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:791:0x028d A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:794:0x06df A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:798:0x0319 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:800:0x073b A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:804:0x0385 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:806:0x0790 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:812:0x03e9 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final int codePointIndexToCharIndex(byte[] r32, int r33) {
        /*
            r0 = r33
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = r32
            int r5 = r4.length
            r6 = r32
            r7 = 0
            r8 = r3
        L_0x000c:
            if (r8 >= r5) goto L_0x0835
            byte r9 = r6[r8]
            r10 = 160(0xa0, float:2.24E-43)
            r11 = 127(0x7f, float:1.78E-43)
            r12 = 32
            r14 = 13
            r16 = -1
            r13 = 10
            r15 = 65536(0x10000, float:9.18355E-41)
            r17 = 2
            r18 = 0
            r19 = 1
            if (r9 < 0) goto L_0x00b5
            r20 = r9
            r21 = 0
            int r22 = r2 + 1
            if (r2 != r0) goto L_0x0030
            return r1
        L_0x0030:
            r2 = r20
            if (r2 == r13) goto L_0x0056
            if (r2 == r14) goto L_0x0056
            r20 = 0
            if (r2 < 0) goto L_0x003f
            if (r2 >= r12) goto L_0x003f
            r23 = r19
            goto L_0x0041
        L_0x003f:
            r23 = r18
        L_0x0041:
            if (r23 != 0) goto L_0x0052
            if (r11 > r2) goto L_0x004a
            if (r2 >= r10) goto L_0x004a
            r23 = r19
            goto L_0x004c
        L_0x004a:
            r23 = r18
        L_0x004c:
            if (r23 == 0) goto L_0x004f
            goto L_0x0052
        L_0x004f:
            r20 = r18
            goto L_0x0054
        L_0x0052:
            r20 = r19
        L_0x0054:
            if (r20 != 0) goto L_0x005b
        L_0x0056:
            r10 = 65533(0xfffd, float:9.1831E-41)
            if (r2 != r10) goto L_0x005c
        L_0x005b:
            return r16
        L_0x005c:
            if (r2 >= r15) goto L_0x0061
            r10 = r19
            goto L_0x0063
        L_0x0061:
            r10 = r17
        L_0x0063:
            int r1 = r1 + r10
            int r8 = r8 + 1
            r2 = r22
        L_0x006a:
            if (r8 >= r5) goto L_0x000c
            byte r10 = r6[r8]
            if (r10 < 0) goto L_0x000c
            int r10 = r8 + 1
            byte r8 = r6[r8]
            r21 = 0
            int r22 = r2 + 1
            if (r2 != r0) goto L_0x007b
            return r1
        L_0x007b:
            if (r8 == r13) goto L_0x00a0
            if (r8 == r14) goto L_0x00a0
            r2 = 0
            if (r8 < 0) goto L_0x0087
            if (r8 >= r12) goto L_0x0087
            r23 = r19
            goto L_0x0089
        L_0x0087:
            r23 = r18
        L_0x0089:
            if (r23 != 0) goto L_0x009c
            if (r11 > r8) goto L_0x0094
            r11 = 160(0xa0, float:2.24E-43)
            if (r8 >= r11) goto L_0x0094
            r11 = r19
            goto L_0x0096
        L_0x0094:
            r11 = r18
        L_0x0096:
            if (r11 == 0) goto L_0x0099
            goto L_0x009c
        L_0x0099:
            r2 = r18
            goto L_0x009e
        L_0x009c:
            r2 = r19
        L_0x009e:
            if (r2 != 0) goto L_0x00a5
        L_0x00a0:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r8 != r2) goto L_0x00a6
        L_0x00a5:
            return r16
        L_0x00a6:
            if (r8 >= r15) goto L_0x00ab
            r2 = r19
            goto L_0x00ad
        L_0x00ab:
            r2 = r17
        L_0x00ad:
            int r1 = r1 + r2
            r8 = r10
            r11 = 127(0x7f, float:1.78E-43)
            r2 = r22
            goto L_0x006a
        L_0x00b5:
            r10 = 5
            r11 = r9
            r21 = 0
            int r10 = r11 >> r10
            r11 = -2
            if (r10 != r11) goto L_0x0237
            r10 = r6
            r11 = 0
            int r15 = r8 + 1
            if (r5 > r15) goto L_0x0110
            r15 = 65533(0xfffd, float:9.1831E-41)
            r22 = 0
            r24 = r15
            r25 = 0
            int r26 = r2 + 1
            if (r2 != r0) goto L_0x00d2
            return r1
        L_0x00d2:
            r2 = r24
            if (r2 == r13) goto L_0x00f7
            if (r2 == r14) goto L_0x00f7
            r13 = 0
            if (r2 < 0) goto L_0x00e0
            if (r2 >= r12) goto L_0x00e0
            r12 = r19
            goto L_0x00e2
        L_0x00e0:
            r12 = r18
        L_0x00e2:
            if (r12 != 0) goto L_0x00f3
            r12 = 127(0x7f, float:1.78E-43)
            if (r12 > r2) goto L_0x00ef
            r12 = 160(0xa0, float:2.24E-43)
            if (r2 >= r12) goto L_0x00ef
            r12 = r19
            goto L_0x00f1
        L_0x00ef:
            r12 = r18
        L_0x00f1:
            if (r12 == 0) goto L_0x00f5
        L_0x00f3:
            r18 = r19
        L_0x00f5:
            if (r18 != 0) goto L_0x00fc
        L_0x00f7:
            r12 = 65533(0xfffd, float:9.1831E-41)
            if (r2 != r12) goto L_0x00fd
        L_0x00fc:
            return r16
        L_0x00fd:
            r12 = 65536(0x10000, float:9.18355E-41)
            if (r2 >= r12) goto L_0x0103
            r17 = r19
        L_0x0103:
            int r1 = r1 + r17
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            r17 = r19
            r2 = r26
            goto L_0x0233
        L_0x0110:
            byte r15 = r10[r8]
            int r24 = r8 + 1
            byte r12 = r10[r24]
            r24 = 0
            r26 = 192(0xc0, float:2.69E-43)
            r27 = r12
            r28 = 0
            r14 = r27 & r26
            r13 = 128(0x80, float:1.794E-43)
            if (r14 != r13) goto L_0x0127
            r13 = r19
            goto L_0x0129
        L_0x0127:
            r13 = r18
        L_0x0129:
            if (r13 != 0) goto L_0x0182
            r13 = 65533(0xfffd, float:9.1831E-41)
            r14 = 0
            r22 = r13
            r24 = 0
            int r27 = r2 + 1
            if (r2 != r0) goto L_0x0138
            return r1
        L_0x0138:
            r28 = r1
            r2 = r22
            r1 = 10
            if (r2 == r1) goto L_0x0169
            r1 = 13
            if (r2 == r1) goto L_0x0169
            r1 = 0
            if (r2 < 0) goto L_0x0150
            r22 = r1
            r1 = 32
            if (r2 >= r1) goto L_0x0152
            r1 = r19
            goto L_0x0154
        L_0x0150:
            r22 = r1
        L_0x0152:
            r1 = r18
        L_0x0154:
            if (r1 != 0) goto L_0x0165
            r1 = 127(0x7f, float:1.78E-43)
            if (r1 > r2) goto L_0x0161
            r1 = 160(0xa0, float:2.24E-43)
            if (r2 >= r1) goto L_0x0161
            r1 = r19
            goto L_0x0163
        L_0x0161:
            r1 = r18
        L_0x0163:
            if (r1 == 0) goto L_0x0167
        L_0x0165:
            r18 = r19
        L_0x0167:
            if (r18 != 0) goto L_0x016e
        L_0x0169:
            r1 = 65533(0xfffd, float:9.1831E-41)
            if (r2 != r1) goto L_0x016f
        L_0x016e:
            return r16
        L_0x016f:
            r1 = 65536(0x10000, float:9.18355E-41)
            if (r2 >= r1) goto L_0x0175
            r17 = r19
        L_0x0175:
            int r1 = r28 + r17
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            r17 = r19
            r2 = r27
            goto L_0x0233
        L_0x0182:
            r28 = r1
            r1 = r12 ^ 3968(0xf80, float:5.56E-42)
            int r13 = r15 << 6
            r1 = r1 ^ r13
            r13 = 128(0x80, float:1.794E-43)
            if (r1 >= r13) goto L_0x01e2
            r13 = 65533(0xfffd, float:9.1831E-41)
            r14 = 0
            r22 = r13
            r24 = 0
            int r27 = r2 + 1
            if (r2 != r0) goto L_0x019d
            return r28
        L_0x019d:
            r2 = r22
            r22 = r1
            r1 = 10
            if (r2 == r1) goto L_0x01ce
            r1 = 13
            if (r2 == r1) goto L_0x01ce
            r1 = 0
            if (r2 < 0) goto L_0x01b5
            r26 = r1
            r1 = 32
            if (r2 >= r1) goto L_0x01b7
            r1 = r19
            goto L_0x01b9
        L_0x01b5:
            r26 = r1
        L_0x01b7:
            r1 = r18
        L_0x01b9:
            if (r1 != 0) goto L_0x01ca
            r1 = 127(0x7f, float:1.78E-43)
            if (r1 > r2) goto L_0x01c6
            r1 = 160(0xa0, float:2.24E-43)
            if (r2 >= r1) goto L_0x01c6
            r1 = r19
            goto L_0x01c8
        L_0x01c6:
            r1 = r18
        L_0x01c8:
            if (r1 == 0) goto L_0x01cc
        L_0x01ca:
            r18 = r19
        L_0x01cc:
            if (r18 != 0) goto L_0x01d3
        L_0x01ce:
            r1 = 65533(0xfffd, float:9.1831E-41)
            if (r2 != r1) goto L_0x01d4
        L_0x01d3:
            return r16
        L_0x01d4:
            r1 = 65536(0x10000, float:9.18355E-41)
            if (r2 >= r1) goto L_0x01d9
            goto L_0x01db
        L_0x01d9:
            r19 = r17
        L_0x01db:
            int r1 = r28 + r19
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            goto L_0x0231
        L_0x01e2:
            r22 = r1
            r13 = 0
            r14 = r1
            r24 = 0
            int r27 = r2 + 1
            if (r2 != r0) goto L_0x01ed
            return r28
        L_0x01ed:
            r2 = 10
            if (r14 == r2) goto L_0x021b
            r2 = 13
            if (r14 == r2) goto L_0x021b
            r2 = 0
            if (r14 < 0) goto L_0x0201
            r26 = r1
            r1 = 32
            if (r14 >= r1) goto L_0x0203
            r1 = r19
            goto L_0x0205
        L_0x0201:
            r26 = r1
        L_0x0203:
            r1 = r18
        L_0x0205:
            if (r1 != 0) goto L_0x0216
            r1 = 127(0x7f, float:1.78E-43)
            if (r1 > r14) goto L_0x0212
            r1 = 160(0xa0, float:2.24E-43)
            if (r14 >= r1) goto L_0x0212
            r1 = r19
            goto L_0x0214
        L_0x0212:
            r1 = r18
        L_0x0214:
            if (r1 == 0) goto L_0x0218
        L_0x0216:
            r18 = r19
        L_0x0218:
            if (r18 != 0) goto L_0x0222
            goto L_0x021d
        L_0x021b:
            r26 = r1
        L_0x021d:
            r1 = 65533(0xfffd, float:9.1831E-41)
            if (r14 != r1) goto L_0x0223
        L_0x0222:
            return r16
        L_0x0223:
            r1 = 65536(0x10000, float:9.18355E-41)
            if (r14 >= r1) goto L_0x0228
            goto L_0x022a
        L_0x0228:
            r19 = r17
        L_0x022a:
            int r1 = r28 + r19
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
        L_0x0231:
            r2 = r27
        L_0x0233:
            int r8 = r8 + r17
            goto L_0x000c
        L_0x0237:
            r28 = r1
            r1 = 4
            r10 = r9
            r12 = 0
            int r1 = r10 >> r1
            r12 = 55296(0xd800, float:7.7486E-41)
            if (r1 != r11) goto L_0x04a5
            r1 = r6
            r11 = 0
            int r14 = r8 + 2
            if (r5 > r14) goto L_0x02bd
            r10 = 65533(0xfffd, float:9.1831E-41)
            r12 = 0
            r13 = r10
            r14 = 0
            int r15 = r2 + 1
            if (r2 != r0) goto L_0x0254
            return r28
        L_0x0254:
            r2 = 10
            if (r13 == r2) goto L_0x0286
            r2 = 13
            if (r13 == r2) goto L_0x0286
            r2 = 0
            if (r13 < 0) goto L_0x0268
            r24 = r1
            r1 = 32
            if (r13 >= r1) goto L_0x026a
            r1 = r19
            goto L_0x026c
        L_0x0268:
            r24 = r1
        L_0x026a:
            r1 = r18
        L_0x026c:
            if (r1 != 0) goto L_0x0281
            r1 = 127(0x7f, float:1.78E-43)
            if (r1 > r13) goto L_0x0279
            r1 = 160(0xa0, float:2.24E-43)
            if (r13 >= r1) goto L_0x0279
            r1 = r19
            goto L_0x027b
        L_0x0279:
            r1 = r18
        L_0x027b:
            if (r1 == 0) goto L_0x027e
            goto L_0x0281
        L_0x027e:
            r1 = r18
            goto L_0x0283
        L_0x0281:
            r1 = r19
        L_0x0283:
            if (r1 != 0) goto L_0x028d
            goto L_0x0288
        L_0x0286:
            r24 = r1
        L_0x0288:
            r1 = 65533(0xfffd, float:9.1831E-41)
            if (r13 != r1) goto L_0x028e
        L_0x028d:
            return r16
        L_0x028e:
            r1 = 65536(0x10000, float:9.18355E-41)
            if (r13 >= r1) goto L_0x0295
            r1 = r19
            goto L_0x0297
        L_0x0295:
            r1 = r17
        L_0x0297:
            int r1 = r28 + r1
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            int r2 = r8 + 1
            if (r5 <= r2) goto L_0x02b8
            int r2 = r8 + 1
            byte r2 = r24[r2]
            r10 = 0
            r12 = 192(0xc0, float:2.69E-43)
            r13 = r2
            r14 = 0
            r12 = r12 & r13
            r13 = 128(0x80, float:1.794E-43)
            if (r12 != r13) goto L_0x02b2
            r18 = r19
        L_0x02b2:
            if (r18 != 0) goto L_0x02b5
            goto L_0x02b8
        L_0x02b5:
            r2 = r15
            goto L_0x04a1
        L_0x02b8:
            r2 = r15
            r17 = r19
            goto L_0x04a1
        L_0x02bd:
            r24 = r1
            byte r1 = r24[r8]
            int r14 = r8 + 1
            byte r14 = r24[r14]
            r15 = 0
            r27 = 192(0xc0, float:2.69E-43)
            r29 = r14
            r30 = 0
            r13 = r29 & r27
            r10 = 128(0x80, float:1.794E-43)
            if (r13 != r10) goto L_0x02d5
            r10 = r19
            goto L_0x02d7
        L_0x02d5:
            r10 = r18
        L_0x02d7:
            if (r10 != 0) goto L_0x032d
            r10 = 65533(0xfffd, float:9.1831E-41)
            r12 = 0
            r13 = r10
            r15 = 0
            int r22 = r2 + 1
            if (r2 != r0) goto L_0x02e4
            return r28
        L_0x02e4:
            r2 = 10
            if (r13 == r2) goto L_0x0312
            r2 = 13
            if (r13 == r2) goto L_0x0312
            r2 = 0
            if (r13 < 0) goto L_0x02f8
            r29 = r1
            r1 = 32
            if (r13 >= r1) goto L_0x02fa
            r1 = r19
            goto L_0x02fc
        L_0x02f8:
            r29 = r1
        L_0x02fa:
            r1 = r18
        L_0x02fc:
            if (r1 != 0) goto L_0x030d
            r1 = 127(0x7f, float:1.78E-43)
            if (r1 > r13) goto L_0x0309
            r1 = 160(0xa0, float:2.24E-43)
            if (r13 >= r1) goto L_0x0309
            r1 = r19
            goto L_0x030b
        L_0x0309:
            r1 = r18
        L_0x030b:
            if (r1 == 0) goto L_0x030f
        L_0x030d:
            r18 = r19
        L_0x030f:
            if (r18 != 0) goto L_0x0319
            goto L_0x0314
        L_0x0312:
            r29 = r1
        L_0x0314:
            r1 = 65533(0xfffd, float:9.1831E-41)
            if (r13 != r1) goto L_0x031a
        L_0x0319:
            return r16
        L_0x031a:
            r1 = 65536(0x10000, float:9.18355E-41)
            if (r13 >= r1) goto L_0x0320
            r17 = r19
        L_0x0320:
            int r1 = r28 + r17
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            r17 = r19
            r2 = r22
            goto L_0x04a1
        L_0x032d:
            r29 = r1
            int r1 = r8 + 2
            byte r1 = r24[r1]
            r10 = 0
            r13 = 192(0xc0, float:2.69E-43)
            r15 = r1
            r30 = 0
            r13 = r13 & r15
            r15 = 128(0x80, float:1.794E-43)
            if (r13 != r15) goto L_0x0341
            r10 = r19
            goto L_0x0343
        L_0x0341:
            r10 = r18
        L_0x0343:
            if (r10 != 0) goto L_0x0398
            r10 = 65533(0xfffd, float:9.1831E-41)
            r12 = 0
            r13 = r10
            r15 = 0
            int r22 = r2 + 1
            if (r2 != r0) goto L_0x0350
            return r28
        L_0x0350:
            r2 = 10
            if (r13 == r2) goto L_0x037e
            r2 = 13
            if (r13 == r2) goto L_0x037e
            r2 = 0
            if (r13 < 0) goto L_0x0364
            r30 = r1
            r1 = 32
            if (r13 >= r1) goto L_0x0366
            r1 = r19
            goto L_0x0368
        L_0x0364:
            r30 = r1
        L_0x0366:
            r1 = r18
        L_0x0368:
            if (r1 != 0) goto L_0x0379
            r1 = 127(0x7f, float:1.78E-43)
            if (r1 > r13) goto L_0x0375
            r1 = 160(0xa0, float:2.24E-43)
            if (r13 >= r1) goto L_0x0375
            r1 = r19
            goto L_0x0377
        L_0x0375:
            r1 = r18
        L_0x0377:
            if (r1 == 0) goto L_0x037b
        L_0x0379:
            r18 = r19
        L_0x037b:
            if (r18 != 0) goto L_0x0385
            goto L_0x0380
        L_0x037e:
            r30 = r1
        L_0x0380:
            r1 = 65533(0xfffd, float:9.1831E-41)
            if (r13 != r1) goto L_0x0386
        L_0x0385:
            return r16
        L_0x0386:
            r1 = 65536(0x10000, float:9.18355E-41)
            if (r13 >= r1) goto L_0x038b
            goto L_0x038d
        L_0x038b:
            r19 = r17
        L_0x038d:
            int r1 = r28 + r19
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            r2 = r22
            goto L_0x04a1
        L_0x0398:
            r30 = r1
            r1 = -123008(0xfffffffffffe1f80, float:NaN)
            r1 = r30 ^ r1
            int r10 = r14 << 6
            r1 = r1 ^ r10
            int r10 = r29 << 12
            r1 = r1 ^ r10
            r10 = 2048(0x800, float:2.87E-42)
            if (r1 >= r10) goto L_0x03fa
            r10 = 65533(0xfffd, float:9.1831E-41)
            r12 = 0
            r13 = r10
            r15 = 0
            int r22 = r2 + 1
            if (r2 != r0) goto L_0x03b7
            return r28
        L_0x03b7:
            r2 = 10
            if (r13 == r2) goto L_0x03e4
            r2 = 13
            if (r13 == r2) goto L_0x03e4
            r2 = 0
            if (r13 < 0) goto L_0x03cb
            r26 = r2
            r2 = 32
            if (r13 >= r2) goto L_0x03cd
            r2 = r19
            goto L_0x03cf
        L_0x03cb:
            r26 = r2
        L_0x03cd:
            r2 = r18
        L_0x03cf:
            if (r2 != 0) goto L_0x03e0
            r2 = 127(0x7f, float:1.78E-43)
            if (r2 > r13) goto L_0x03dc
            r2 = 160(0xa0, float:2.24E-43)
            if (r13 >= r2) goto L_0x03dc
            r2 = r19
            goto L_0x03de
        L_0x03dc:
            r2 = r18
        L_0x03de:
            if (r2 == 0) goto L_0x03e2
        L_0x03e0:
            r18 = r19
        L_0x03e2:
            if (r18 != 0) goto L_0x03e9
        L_0x03e4:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r13 != r2) goto L_0x03ea
        L_0x03e9:
            return r16
        L_0x03ea:
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r13 >= r2) goto L_0x03f0
            r17 = r19
        L_0x03f0:
            int r2 = r28 + r17
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            r27 = r1
            goto L_0x049c
        L_0x03fa:
            if (r12 > r1) goto L_0x0404
            r10 = 57344(0xe000, float:8.0356E-41)
            if (r1 >= r10) goto L_0x0404
            r10 = r19
            goto L_0x0406
        L_0x0404:
            r10 = r18
        L_0x0406:
            if (r10 == 0) goto L_0x0456
            r10 = 65533(0xfffd, float:9.1831E-41)
            r12 = 0
            r13 = r10
            r15 = 0
            int r22 = r2 + 1
            if (r2 != r0) goto L_0x0413
            return r28
        L_0x0413:
            r2 = 10
            if (r13 == r2) goto L_0x0441
            r2 = 13
            if (r13 == r2) goto L_0x0441
            r2 = 0
            if (r13 < 0) goto L_0x0427
            r27 = r1
            r1 = 32
            if (r13 >= r1) goto L_0x0429
            r1 = r19
            goto L_0x042b
        L_0x0427:
            r27 = r1
        L_0x0429:
            r1 = r18
        L_0x042b:
            if (r1 != 0) goto L_0x043c
            r1 = 127(0x7f, float:1.78E-43)
            if (r1 > r13) goto L_0x0438
            r1 = 160(0xa0, float:2.24E-43)
            if (r13 >= r1) goto L_0x0438
            r1 = r19
            goto L_0x043a
        L_0x0438:
            r1 = r18
        L_0x043a:
            if (r1 == 0) goto L_0x043e
        L_0x043c:
            r18 = r19
        L_0x043e:
            if (r18 != 0) goto L_0x0448
            goto L_0x0443
        L_0x0441:
            r27 = r1
        L_0x0443:
            r1 = 65533(0xfffd, float:9.1831E-41)
            if (r13 != r1) goto L_0x0449
        L_0x0448:
            return r16
        L_0x0449:
            r1 = 65536(0x10000, float:9.18355E-41)
            if (r13 >= r1) goto L_0x044f
            r17 = r19
        L_0x044f:
            int r2 = r28 + r17
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            goto L_0x049c
        L_0x0456:
            r27 = r1
            r10 = 0
            r12 = r1
            r13 = 0
            int r22 = r2 + 1
            if (r2 != r0) goto L_0x0460
            return r28
        L_0x0460:
            r2 = 10
            if (r12 == r2) goto L_0x0489
            r2 = 13
            if (r12 == r2) goto L_0x0489
            r2 = 0
            if (r12 < 0) goto L_0x0472
            r15 = 32
            if (r12 >= r15) goto L_0x0472
            r15 = r19
            goto L_0x0474
        L_0x0472:
            r15 = r18
        L_0x0474:
            if (r15 != 0) goto L_0x0485
            r15 = 127(0x7f, float:1.78E-43)
            if (r15 > r12) goto L_0x0481
            r15 = 160(0xa0, float:2.24E-43)
            if (r12 >= r15) goto L_0x0481
            r15 = r19
            goto L_0x0483
        L_0x0481:
            r15 = r18
        L_0x0483:
            if (r15 == 0) goto L_0x0487
        L_0x0485:
            r18 = r19
        L_0x0487:
            if (r18 != 0) goto L_0x048e
        L_0x0489:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r12 != r2) goto L_0x048f
        L_0x048e:
            return r16
        L_0x048f:
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r12 >= r2) goto L_0x0495
            r17 = r19
        L_0x0495:
            int r2 = r28 + r17
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
        L_0x049c:
            r1 = r2
            r2 = r22
            r17 = 3
        L_0x04a1:
            int r8 = r8 + r17
            goto L_0x000c
        L_0x04a5:
            r1 = 3
            r10 = r9
            r13 = 0
            int r1 = r10 >> r1
            if (r1 != r11) goto L_0x07ed
            r1 = r6
            r10 = 0
            int r11 = r8 + 3
            if (r5 > r11) goto L_0x0545
            r11 = 65533(0xfffd, float:9.1831E-41)
            r12 = 0
            r13 = r11
            r14 = 0
            int r15 = r2 + 1
            if (r2 != r0) goto L_0x04bd
            return r28
        L_0x04bd:
            r2 = 10
            if (r13 == r2) goto L_0x04ef
            r2 = 13
            if (r13 == r2) goto L_0x04ef
            r2 = 0
            if (r13 < 0) goto L_0x04d1
            r24 = r1
            r1 = 32
            if (r13 >= r1) goto L_0x04d3
            r1 = r19
            goto L_0x04d5
        L_0x04d1:
            r24 = r1
        L_0x04d3:
            r1 = r18
        L_0x04d5:
            if (r1 != 0) goto L_0x04ea
            r1 = 127(0x7f, float:1.78E-43)
            if (r1 > r13) goto L_0x04e2
            r1 = 160(0xa0, float:2.24E-43)
            if (r13 >= r1) goto L_0x04e2
            r1 = r19
            goto L_0x04e4
        L_0x04e2:
            r1 = r18
        L_0x04e4:
            if (r1 == 0) goto L_0x04e7
            goto L_0x04ea
        L_0x04e7:
            r1 = r18
            goto L_0x04ec
        L_0x04ea:
            r1 = r19
        L_0x04ec:
            if (r1 != 0) goto L_0x04f6
            goto L_0x04f1
        L_0x04ef:
            r24 = r1
        L_0x04f1:
            r1 = 65533(0xfffd, float:9.1831E-41)
            if (r13 != r1) goto L_0x04f7
        L_0x04f6:
            return r16
        L_0x04f7:
            r1 = 65536(0x10000, float:9.18355E-41)
            if (r13 >= r1) goto L_0x04fe
            r1 = r19
            goto L_0x0500
        L_0x04fe:
            r1 = r17
        L_0x0500:
            int r1 = r28 + r1
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            int r2 = r8 + 1
            if (r5 <= r2) goto L_0x0540
            int r2 = r8 + 1
            byte r2 = r24[r2]
            r11 = 0
            r12 = 192(0xc0, float:2.69E-43)
            r13 = r2
            r14 = 0
            r12 = r12 & r13
            r13 = 128(0x80, float:1.794E-43)
            if (r12 != r13) goto L_0x051c
            r2 = r19
            goto L_0x051e
        L_0x051c:
            r2 = r18
        L_0x051e:
            if (r2 != 0) goto L_0x0521
            goto L_0x0540
        L_0x0521:
            int r2 = r8 + 2
            if (r5 <= r2) goto L_0x053d
            int r2 = r8 + 2
            byte r2 = r24[r2]
            r11 = 0
            r12 = 192(0xc0, float:2.69E-43)
            r13 = r2
            r14 = 0
            r12 = r12 & r13
            r13 = 128(0x80, float:1.794E-43)
            if (r12 != r13) goto L_0x0535
            r18 = r19
        L_0x0535:
            if (r18 != 0) goto L_0x0538
            goto L_0x053d
        L_0x0538:
            r2 = r15
            r17 = 3
            goto L_0x07e9
        L_0x053d:
            r2 = r15
            goto L_0x07e9
        L_0x0540:
            r2 = r15
            r17 = r19
            goto L_0x07e9
        L_0x0545:
            r24 = r1
            byte r1 = r24[r8]
            int r11 = r8 + 1
            byte r11 = r24[r11]
            r13 = 0
            r14 = 192(0xc0, float:2.69E-43)
            r15 = r11
            r29 = 0
            r14 = r14 & r15
            r15 = 128(0x80, float:1.794E-43)
            if (r14 != r15) goto L_0x055b
            r13 = r19
            goto L_0x055d
        L_0x055b:
            r13 = r18
        L_0x055d:
            if (r13 != 0) goto L_0x05b3
            r12 = 65533(0xfffd, float:9.1831E-41)
            r13 = 0
            r14 = r12
            r15 = 0
            int r22 = r2 + 1
            if (r2 != r0) goto L_0x056a
            return r28
        L_0x056a:
            r2 = 10
            if (r14 == r2) goto L_0x0598
            r2 = 13
            if (r14 == r2) goto L_0x0598
            r2 = 0
            if (r14 < 0) goto L_0x057e
            r29 = r1
            r1 = 32
            if (r14 >= r1) goto L_0x0580
            r1 = r19
            goto L_0x0582
        L_0x057e:
            r29 = r1
        L_0x0580:
            r1 = r18
        L_0x0582:
            if (r1 != 0) goto L_0x0593
            r1 = 127(0x7f, float:1.78E-43)
            if (r1 > r14) goto L_0x058f
            r1 = 160(0xa0, float:2.24E-43)
            if (r14 >= r1) goto L_0x058f
            r1 = r19
            goto L_0x0591
        L_0x058f:
            r1 = r18
        L_0x0591:
            if (r1 == 0) goto L_0x0595
        L_0x0593:
            r18 = r19
        L_0x0595:
            if (r18 != 0) goto L_0x059f
            goto L_0x059a
        L_0x0598:
            r29 = r1
        L_0x059a:
            r1 = 65533(0xfffd, float:9.1831E-41)
            if (r14 != r1) goto L_0x05a0
        L_0x059f:
            return r16
        L_0x05a0:
            r1 = 65536(0x10000, float:9.18355E-41)
            if (r14 >= r1) goto L_0x05a6
            r17 = r19
        L_0x05a6:
            int r1 = r28 + r17
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            r17 = r19
            r2 = r22
            goto L_0x07e9
        L_0x05b3:
            r29 = r1
            int r1 = r8 + 2
            byte r1 = r24[r1]
            r13 = 0
            r14 = 192(0xc0, float:2.69E-43)
            r15 = r1
            r30 = 0
            r14 = r14 & r15
            r15 = 128(0x80, float:1.794E-43)
            if (r14 != r15) goto L_0x05c7
            r13 = r19
            goto L_0x05c9
        L_0x05c7:
            r13 = r18
        L_0x05c9:
            if (r13 != 0) goto L_0x061e
            r12 = 65533(0xfffd, float:9.1831E-41)
            r13 = 0
            r14 = r12
            r15 = 0
            int r22 = r2 + 1
            if (r2 != r0) goto L_0x05d6
            return r28
        L_0x05d6:
            r2 = 10
            if (r14 == r2) goto L_0x0604
            r2 = 13
            if (r14 == r2) goto L_0x0604
            r2 = 0
            if (r14 < 0) goto L_0x05ea
            r30 = r1
            r1 = 32
            if (r14 >= r1) goto L_0x05ec
            r1 = r19
            goto L_0x05ee
        L_0x05ea:
            r30 = r1
        L_0x05ec:
            r1 = r18
        L_0x05ee:
            if (r1 != 0) goto L_0x05ff
            r1 = 127(0x7f, float:1.78E-43)
            if (r1 > r14) goto L_0x05fb
            r1 = 160(0xa0, float:2.24E-43)
            if (r14 >= r1) goto L_0x05fb
            r1 = r19
            goto L_0x05fd
        L_0x05fb:
            r1 = r18
        L_0x05fd:
            if (r1 == 0) goto L_0x0601
        L_0x05ff:
            r18 = r19
        L_0x0601:
            if (r18 != 0) goto L_0x060b
            goto L_0x0606
        L_0x0604:
            r30 = r1
        L_0x0606:
            r1 = 65533(0xfffd, float:9.1831E-41)
            if (r14 != r1) goto L_0x060c
        L_0x060b:
            return r16
        L_0x060c:
            r1 = 65536(0x10000, float:9.18355E-41)
            if (r14 >= r1) goto L_0x0611
            goto L_0x0613
        L_0x0611:
            r19 = r17
        L_0x0613:
            int r1 = r28 + r19
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            r2 = r22
            goto L_0x07e9
        L_0x061e:
            r30 = r1
            int r1 = r8 + 3
            byte r1 = r24[r1]
            r13 = 0
            r14 = 192(0xc0, float:2.69E-43)
            r15 = r1
            r31 = 0
            r14 = r14 & r15
            r15 = 128(0x80, float:1.794E-43)
            if (r14 != r15) goto L_0x0632
            r13 = r19
            goto L_0x0634
        L_0x0632:
            r13 = r18
        L_0x0634:
            if (r13 != 0) goto L_0x068a
            r12 = 65533(0xfffd, float:9.1831E-41)
            r13 = 0
            r14 = r12
            r15 = 0
            int r22 = r2 + 1
            if (r2 != r0) goto L_0x0641
            return r28
        L_0x0641:
            r2 = 10
            if (r14 == r2) goto L_0x066f
            r2 = 13
            if (r14 == r2) goto L_0x066f
            r2 = 0
            if (r14 < 0) goto L_0x0655
            r31 = r1
            r1 = 32
            if (r14 >= r1) goto L_0x0657
            r1 = r19
            goto L_0x0659
        L_0x0655:
            r31 = r1
        L_0x0657:
            r1 = r18
        L_0x0659:
            if (r1 != 0) goto L_0x066a
            r1 = 127(0x7f, float:1.78E-43)
            if (r1 > r14) goto L_0x0666
            r1 = 160(0xa0, float:2.24E-43)
            if (r14 >= r1) goto L_0x0666
            r1 = r19
            goto L_0x0668
        L_0x0666:
            r1 = r18
        L_0x0668:
            if (r1 == 0) goto L_0x066c
        L_0x066a:
            r18 = r19
        L_0x066c:
            if (r18 != 0) goto L_0x0676
            goto L_0x0671
        L_0x066f:
            r31 = r1
        L_0x0671:
            r1 = 65533(0xfffd, float:9.1831E-41)
            if (r14 != r1) goto L_0x0677
        L_0x0676:
            return r16
        L_0x0677:
            r1 = 65536(0x10000, float:9.18355E-41)
            if (r14 >= r1) goto L_0x067d
            r17 = r19
        L_0x067d:
            int r1 = r28 + r17
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            r2 = r22
            r17 = 3
            goto L_0x07e9
        L_0x068a:
            r31 = r1
            r1 = 3678080(0x381f80, float:5.154088E-39)
            r1 = r31 ^ r1
            int r13 = r30 << 6
            r1 = r1 ^ r13
            int r13 = r11 << 12
            r1 = r1 ^ r13
            int r13 = r29 << 18
            r1 = r1 ^ r13
            r13 = 1114111(0x10ffff, float:1.561202E-39)
            if (r1 <= r13) goto L_0x06f0
            r12 = 65533(0xfffd, float:9.1831E-41)
            r13 = 0
            r14 = r12
            r15 = 0
            int r22 = r2 + 1
            if (r2 != r0) goto L_0x06ad
            return r28
        L_0x06ad:
            r2 = 10
            if (r14 == r2) goto L_0x06da
            r2 = 13
            if (r14 == r2) goto L_0x06da
            r2 = 0
            if (r14 < 0) goto L_0x06c1
            r26 = r2
            r2 = 32
            if (r14 >= r2) goto L_0x06c3
            r2 = r19
            goto L_0x06c5
        L_0x06c1:
            r26 = r2
        L_0x06c3:
            r2 = r18
        L_0x06c5:
            if (r2 != 0) goto L_0x06d6
            r2 = 127(0x7f, float:1.78E-43)
            if (r2 > r14) goto L_0x06d2
            r2 = 160(0xa0, float:2.24E-43)
            if (r14 >= r2) goto L_0x06d2
            r2 = r19
            goto L_0x06d4
        L_0x06d2:
            r2 = r18
        L_0x06d4:
            if (r2 == 0) goto L_0x06d8
        L_0x06d6:
            r18 = r19
        L_0x06d8:
            if (r18 != 0) goto L_0x06df
        L_0x06da:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r14 != r2) goto L_0x06e0
        L_0x06df:
            return r16
        L_0x06e0:
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r14 >= r2) goto L_0x06e6
            r17 = r19
        L_0x06e6:
            int r2 = r28 + r17
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            r27 = r1
            goto L_0x07e4
        L_0x06f0:
            if (r12 > r1) goto L_0x06fa
            r12 = 57344(0xe000, float:8.0356E-41)
            if (r1 >= r12) goto L_0x06fa
            r12 = r19
            goto L_0x06fc
        L_0x06fa:
            r12 = r18
        L_0x06fc:
            if (r12 == 0) goto L_0x074c
            r12 = 65533(0xfffd, float:9.1831E-41)
            r13 = 0
            r14 = r12
            r15 = 0
            int r22 = r2 + 1
            if (r2 != r0) goto L_0x0709
            return r28
        L_0x0709:
            r2 = 10
            if (r14 == r2) goto L_0x0736
            r2 = 13
            if (r14 == r2) goto L_0x0736
            r2 = 0
            if (r14 < 0) goto L_0x071d
            r26 = r2
            r2 = 32
            if (r14 >= r2) goto L_0x071f
            r2 = r19
            goto L_0x0721
        L_0x071d:
            r26 = r2
        L_0x071f:
            r2 = r18
        L_0x0721:
            if (r2 != 0) goto L_0x0732
            r2 = 127(0x7f, float:1.78E-43)
            if (r2 > r14) goto L_0x072e
            r2 = 160(0xa0, float:2.24E-43)
            if (r14 >= r2) goto L_0x072e
            r2 = r19
            goto L_0x0730
        L_0x072e:
            r2 = r18
        L_0x0730:
            if (r2 == 0) goto L_0x0734
        L_0x0732:
            r18 = r19
        L_0x0734:
            if (r18 != 0) goto L_0x073b
        L_0x0736:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r14 != r2) goto L_0x073c
        L_0x073b:
            return r16
        L_0x073c:
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r14 >= r2) goto L_0x0742
            r17 = r19
        L_0x0742:
            int r2 = r28 + r17
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            r27 = r1
            goto L_0x07e4
        L_0x074c:
            r12 = 65536(0x10000, float:9.18355E-41)
            if (r1 >= r12) goto L_0x079e
            r12 = 65533(0xfffd, float:9.1831E-41)
            r13 = 0
            r14 = r12
            r15 = 0
            int r22 = r2 + 1
            if (r2 != r0) goto L_0x075b
            return r28
        L_0x075b:
            r2 = 10
            if (r14 == r2) goto L_0x0789
            r2 = 13
            if (r14 == r2) goto L_0x0789
            r2 = 0
            if (r14 < 0) goto L_0x076f
            r27 = r1
            r1 = 32
            if (r14 >= r1) goto L_0x0771
            r1 = r19
            goto L_0x0773
        L_0x076f:
            r27 = r1
        L_0x0771:
            r1 = r18
        L_0x0773:
            if (r1 != 0) goto L_0x0784
            r1 = 127(0x7f, float:1.78E-43)
            if (r1 > r14) goto L_0x0780
            r1 = 160(0xa0, float:2.24E-43)
            if (r14 >= r1) goto L_0x0780
            r1 = r19
            goto L_0x0782
        L_0x0780:
            r1 = r18
        L_0x0782:
            if (r1 == 0) goto L_0x0786
        L_0x0784:
            r18 = r19
        L_0x0786:
            if (r18 != 0) goto L_0x0790
            goto L_0x078b
        L_0x0789:
            r27 = r1
        L_0x078b:
            r1 = 65533(0xfffd, float:9.1831E-41)
            if (r14 != r1) goto L_0x0791
        L_0x0790:
            return r16
        L_0x0791:
            r1 = 65536(0x10000, float:9.18355E-41)
            if (r14 >= r1) goto L_0x0797
            r17 = r19
        L_0x0797:
            int r2 = r28 + r17
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            goto L_0x07e4
        L_0x079e:
            r27 = r1
            r12 = 0
            r13 = r1
            r14 = 0
            int r22 = r2 + 1
            if (r2 != r0) goto L_0x07a8
            return r28
        L_0x07a8:
            r2 = 10
            if (r13 == r2) goto L_0x07d1
            r2 = 13
            if (r13 == r2) goto L_0x07d1
            r2 = 0
            if (r13 < 0) goto L_0x07ba
            r15 = 32
            if (r13 >= r15) goto L_0x07ba
            r15 = r19
            goto L_0x07bc
        L_0x07ba:
            r15 = r18
        L_0x07bc:
            if (r15 != 0) goto L_0x07cd
            r15 = 127(0x7f, float:1.78E-43)
            if (r15 > r13) goto L_0x07c9
            r15 = 160(0xa0, float:2.24E-43)
            if (r13 >= r15) goto L_0x07c9
            r15 = r19
            goto L_0x07cb
        L_0x07c9:
            r15 = r18
        L_0x07cb:
            if (r15 == 0) goto L_0x07cf
        L_0x07cd:
            r18 = r19
        L_0x07cf:
            if (r18 != 0) goto L_0x07d6
        L_0x07d1:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r13 != r2) goto L_0x07d7
        L_0x07d6:
            return r16
        L_0x07d7:
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r13 >= r2) goto L_0x07dd
            r17 = r19
        L_0x07dd:
            int r2 = r28 + r17
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
        L_0x07e4:
            r17 = 4
            r1 = r2
            r2 = r22
        L_0x07e9:
            int r8 = r8 + r17
            goto L_0x000c
        L_0x07ed:
            r1 = 65533(0xfffd, float:9.1831E-41)
            r10 = 0
            int r11 = r2 + 1
            if (r2 != r0) goto L_0x07f6
            return r28
        L_0x07f6:
            r2 = 10
            if (r1 == r2) goto L_0x081f
            r2 = 13
            if (r1 == r2) goto L_0x081f
            r2 = 0
            if (r1 < 0) goto L_0x0808
            r15 = 32
            if (r1 >= r15) goto L_0x0808
            r12 = r19
            goto L_0x080a
        L_0x0808:
            r12 = r18
        L_0x080a:
            if (r12 != 0) goto L_0x081b
            r15 = 127(0x7f, float:1.78E-43)
            if (r15 > r1) goto L_0x0817
            r15 = 160(0xa0, float:2.24E-43)
            if (r1 >= r15) goto L_0x0817
            r12 = r19
            goto L_0x0819
        L_0x0817:
            r12 = r18
        L_0x0819:
            if (r12 == 0) goto L_0x081d
        L_0x081b:
            r18 = r19
        L_0x081d:
            if (r18 != 0) goto L_0x0824
        L_0x081f:
            r2 = 65533(0xfffd, float:9.1831E-41)
            if (r1 != r2) goto L_0x0825
        L_0x0824:
            return r16
        L_0x0825:
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r1 >= r2) goto L_0x082b
            r17 = r19
        L_0x082b:
            int r2 = r28 + r17
            int r8 = r8 + 1
            r1 = r2
            r2 = r11
            goto L_0x000c
        L_0x0835:
            r28 = r1
            return r28
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.internal.ByteString.codePointIndexToCharIndex(byte[], int):int");
    }
}
