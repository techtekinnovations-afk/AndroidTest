package okio;

import com.google.common.base.Ascii;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0012\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0003\u001a\u000e\u0010\t\u001a\u0004\u0018\u00010\u0001*\u00020\nH\u0000\u001a\u0016\u0010\u000b\u001a\u00020\n*\u00020\u00012\b\b\u0002\u0010\f\u001a\u00020\u0001H\u0000\"\u001c\u0010\u0000\u001a\u00020\u00018\u0000X\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\"\u001c\u0010\u0006\u001a\u00020\u00018\u0000X\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0007\u0010\u0003\u001a\u0004\b\b\u0010\u0005¨\u0006\r"}, d2 = {"BASE64", "", "getBASE64$annotations", "()V", "getBASE64", "()[B", "BASE64_URL_SAFE", "getBASE64_URL_SAFE$annotations", "getBASE64_URL_SAFE", "decodeBase64ToArray", "", "encodeBase64", "map", "okio"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* renamed from: okio.-Base64  reason: invalid class name */
/* compiled from: Base64.kt */
public final class Base64 {
    private static final byte[] BASE64 = ByteString.Companion.encodeUtf8("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/").getData$okio();
    private static final byte[] BASE64_URL_SAFE = ByteString.Companion.encodeUtf8("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_").getData$okio();

    public static /* synthetic */ void getBASE64$annotations() {
    }

    public static /* synthetic */ void getBASE64_URL_SAFE$annotations() {
    }

    public static final byte[] getBASE64() {
        return BASE64;
    }

    public static final byte[] getBASE64_URL_SAFE() {
        return BASE64_URL_SAFE;
    }

    public static final byte[] decodeBase64ToArray(String $this$decodeBase64ToArray) {
        int bits;
        String str = $this$decodeBase64ToArray;
        Intrinsics.checkNotNullParameter(str, "<this>");
        int limit = str.length();
        while (limit > 0 && ((c = str.charAt(limit - 1)) == '=' || c == 10 || c == 13 || c == ' ' || c == 9)) {
            limit--;
        }
        byte[] out = new byte[((int) ((((long) limit) * 6) / 8))];
        int outCount = 0;
        int inCount = 0;
        int word = 0;
        for (int pos = 0; pos < limit; pos++) {
            char c = str.charAt(pos);
            boolean z = false;
            if ('A' <= c && c < '[') {
                bits = c - 'A';
            } else {
                if ('a' <= c && c < '{') {
                    bits = c - 'G';
                } else {
                    if ('0' <= c && c < ':') {
                        z = true;
                    }
                    if (z) {
                        bits = c + 4;
                    } else if (c == '+' || c == '-') {
                        bits = 62;
                    } else if (c == '/' || c == '_') {
                        bits = 63;
                    } else {
                        if (!(c == 10 || c == 13 || c == ' ' || c == 9)) {
                            return null;
                        }
                    }
                }
            }
            word = (word << 6) | bits;
            inCount++;
            if (inCount % 4 == 0) {
                int outCount2 = outCount + 1;
                out[outCount] = (byte) (word >> 16);
                int outCount3 = outCount2 + 1;
                out[outCount2] = (byte) (word >> 8);
                out[outCount3] = (byte) word;
                outCount = outCount3 + 1;
            }
        }
        switch (inCount % 4) {
            case 1:
                return null;
            case 2:
                out[outCount] = (byte) ((word << 12) >> 16);
                outCount++;
                break;
            case 3:
                int word2 = word << 6;
                int outCount4 = outCount + 1;
                out[outCount] = (byte) (word2 >> 16);
                outCount = outCount4 + 1;
                out[outCount4] = (byte) (word2 >> 8);
                break;
        }
        if (outCount == out.length) {
            return out;
        }
        byte[] copyOf = Arrays.copyOf(out, outCount);
        Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, newSize)");
        return copyOf;
    }

    public static /* synthetic */ String encodeBase64$default(byte[] bArr, byte[] bArr2, int i, Object obj) {
        if ((i & 1) != 0) {
            bArr2 = BASE64;
        }
        return encodeBase64(bArr, bArr2);
    }

    public static final String encodeBase64(byte[] $this$encodeBase64, byte[] map) {
        Intrinsics.checkNotNullParameter($this$encodeBase64, "<this>");
        Intrinsics.checkNotNullParameter(map, "map");
        byte[] out = new byte[((($this$encodeBase64.length + 2) / 3) * 4)];
        int index = 0;
        int end = $this$encodeBase64.length - ($this$encodeBase64.length % 3);
        int b0 = 0;
        while (b0 < end) {
            int i = b0 + 1;
            byte b02 = $this$encodeBase64[b0];
            int i2 = i + 1;
            byte b1 = $this$encodeBase64[i];
            int i3 = i2 + 1;
            byte i4 = $this$encodeBase64[i2];
            int index2 = index + 1;
            out[index] = map[(b02 & 255) >> 2];
            int index3 = index2 + 1;
            out[index2] = map[((b02 & 3) << 4) | ((b1 & 255) >> 4)];
            int index4 = index3 + 1;
            out[index3] = map[((b1 & Ascii.SI) << 2) | ((i4 & 255) >> 6)];
            index = index4 + 1;
            out[index4] = map[i4 & Utf8.REPLACEMENT_BYTE];
            b0 = i3;
        }
        switch ($this$encodeBase64.length - end) {
            case 1:
                byte b03 = $this$encodeBase64[b0];
                int index5 = index + 1;
                out[index] = map[(b03 & 255) >> 2];
                int index6 = index5 + 1;
                out[index5] = map[(b03 & 3) << 4];
                int index7 = index6 + 1;
                out[index6] = kotlin.io.encoding.Base64.padSymbol;
                out[index7] = kotlin.io.encoding.Base64.padSymbol;
                int i5 = index7;
                break;
            case 2:
                int i6 = b0 + 1;
                byte b04 = $this$encodeBase64[b0];
                byte b12 = $this$encodeBase64[i6];
                int index8 = index + 1;
                out[index] = map[(b04 & 255) >> 2];
                int index9 = index8 + 1;
                out[index8] = map[((b04 & 3) << 4) | ((b12 & 255) >> 4)];
                int index10 = index9 + 1;
                out[index9] = map[(b12 & Ascii.SI) << 2];
                out[index10] = kotlin.io.encoding.Base64.padSymbol;
                int b05 = i6;
                int i7 = index10;
                break;
        }
        return _JvmPlatformKt.toUtf8String(out);
    }
}
