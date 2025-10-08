package okio.internal;

import com.google.common.base.Ascii;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okio.Utf8;

@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0012\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\u001e\u0010\u0003\u001a\u00020\u0002*\u00020\u00012\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005¨\u0006\u0007"}, d2 = {"commonAsUtf8ToByteArray", "", "", "commonToUtf8String", "beginIndex", "", "endIndex", "okio"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* compiled from: -Utf8.kt */
public final class _Utf8Kt {
    public static /* synthetic */ String commonToUtf8String$default(byte[] bArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = bArr.length;
        }
        return commonToUtf8String(bArr, i, i2);
    }

    public static final String commonToUtf8String(byte[] $this$commonToUtf8String, int beginIndex, int endIndex) {
        int i;
        int length;
        int length2;
        int length3;
        int length4;
        int length5;
        int length6;
        int length7;
        int length8;
        int length9;
        int i2;
        int length10;
        int length11;
        byte[] $this$processUtf16Chars$iv;
        byte b1$iv$iv;
        int length12;
        int length13;
        byte[] bArr = $this$commonToUtf8String;
        int i3 = beginIndex;
        int i4 = endIndex;
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        if (i3 < 0 || i4 > bArr.length || i3 > i4) {
            throw new ArrayIndexOutOfBoundsException("size=" + bArr.length + " beginIndex=" + i3 + " endIndex=" + i4);
        }
        char[] chars = new char[(i4 - i3)];
        int length14 = 0;
        byte[] $this$processUtf16Chars$iv2 = $this$commonToUtf8String;
        int index$iv = beginIndex;
        while (index$iv < i4) {
            byte b0$iv = $this$processUtf16Chars$iv2[index$iv];
            if (b0$iv >= 0) {
                chars[length14] = (char) b0$iv;
                index$iv++;
                length14++;
                while (index$iv < i4 && $this$processUtf16Chars$iv2[index$iv] >= 0) {
                    chars[length14] = (char) $this$processUtf16Chars$iv2[index$iv];
                    index$iv++;
                    length14++;
                }
            } else if ((b0$iv >> 5) == -2) {
                byte[] $this$process2Utf8Bytes$iv$iv = $this$processUtf16Chars$iv2;
                if (i4 <= index$iv + 1) {
                    int length15 = length14 + 1;
                    chars[length14] = (char) Utf8.REPLACEMENT_CODE_POINT;
                    Unit unit = Unit.INSTANCE;
                    length13 = length15;
                    b1$iv$iv = 1;
                    $this$processUtf16Chars$iv = $this$processUtf16Chars$iv2;
                } else {
                    byte b0$iv$iv = $this$process2Utf8Bytes$iv$iv[index$iv];
                    byte b1$iv$iv2 = $this$process2Utf8Bytes$iv$iv[index$iv + 1];
                    if (!((b1$iv$iv2 & 192) == 128)) {
                        int length16 = length14 + 1;
                        chars[length14] = (char) Utf8.REPLACEMENT_CODE_POINT;
                        Unit unit2 = Unit.INSTANCE;
                        $this$processUtf16Chars$iv = $this$processUtf16Chars$iv2;
                        length13 = length16;
                        b1$iv$iv = 1;
                    } else {
                        int codePoint$iv$iv = (b1$iv$iv2 ^ Utf8.MASK_2BYTES) ^ (b0$iv$iv << 6);
                        if (codePoint$iv$iv < 128) {
                            $this$processUtf16Chars$iv = $this$processUtf16Chars$iv2;
                            length12 = length14 + 1;
                            chars[length14] = (char) Utf8.REPLACEMENT_CODE_POINT;
                            Unit unit3 = Unit.INSTANCE;
                        } else {
                            $this$processUtf16Chars$iv = $this$processUtf16Chars$iv2;
                            length12 = length14 + 1;
                            chars[length14] = (char) codePoint$iv$iv;
                            Unit unit4 = Unit.INSTANCE;
                        }
                        length13 = length12;
                        b1$iv$iv = 2;
                    }
                }
                index$iv += b1$iv$iv;
                $this$processUtf16Chars$iv2 = $this$processUtf16Chars$iv;
            } else {
                byte[] $this$processUtf16Chars$iv3 = $this$processUtf16Chars$iv2;
                if ((b0$iv >> 4) == -2) {
                    byte[] $this$process3Utf8Bytes$iv$iv = $this$processUtf16Chars$iv3;
                    if (i4 <= index$iv + 2) {
                        int length17 = length14 + 1;
                        chars[length14] = (char) Utf8.REPLACEMENT_CODE_POINT;
                        Unit unit5 = Unit.INSTANCE;
                        if (i4 > index$iv + 1) {
                            if ((192 & $this$process3Utf8Bytes$iv$iv[index$iv + 1]) == 128) {
                                length11 = length17;
                                i2 = 2;
                            }
                        }
                        length11 = length17;
                        i2 = 1;
                    } else {
                        byte b0$iv$iv2 = $this$process3Utf8Bytes$iv$iv[index$iv];
                        byte b1$iv$iv3 = $this$process3Utf8Bytes$iv$iv[index$iv + 1];
                        if (!((b1$iv$iv3 & 192) == 128)) {
                            int length18 = length14 + 1;
                            chars[length14] = (char) Utf8.REPLACEMENT_CODE_POINT;
                            Unit unit6 = Unit.INSTANCE;
                            length11 = length18;
                            i2 = 1;
                        } else {
                            byte b2$iv$iv = $this$process3Utf8Bytes$iv$iv[index$iv + 2];
                            if (!((b2$iv$iv & 192) == 128)) {
                                int length19 = length14 + 1;
                                chars[length14] = (char) Utf8.REPLACEMENT_CODE_POINT;
                                Unit unit7 = Unit.INSTANCE;
                                length11 = length19;
                                i2 = 2;
                            } else {
                                int codePoint$iv$iv2 = ((-123008 ^ b2$iv$iv) ^ (b1$iv$iv3 << 6)) ^ (b0$iv$iv2 << Ascii.FF);
                                if (codePoint$iv$iv2 < 2048) {
                                    length10 = length14 + 1;
                                    chars[length14] = (char) Utf8.REPLACEMENT_CODE_POINT;
                                    Unit unit8 = Unit.INSTANCE;
                                } else {
                                    if (55296 <= codePoint$iv$iv2 && codePoint$iv$iv2 < 57344) {
                                        length10 = length14 + 1;
                                        chars[length14] = (char) Utf8.REPLACEMENT_CODE_POINT;
                                        Unit unit9 = Unit.INSTANCE;
                                    } else {
                                        length10 = length14 + 1;
                                        chars[length14] = (char) codePoint$iv$iv2;
                                        Unit unit10 = Unit.INSTANCE;
                                    }
                                }
                                length11 = length10;
                                i2 = 3;
                            }
                        }
                    }
                    index$iv += i2;
                    $this$processUtf16Chars$iv2 = $this$processUtf16Chars$iv3;
                } else if ((b0$iv >> 3) == -2) {
                    byte[] $this$process4Utf8Bytes$iv$iv = $this$processUtf16Chars$iv3;
                    if (i4 <= index$iv + 3) {
                        if (65533 != 65533) {
                            int length20 = length14 + 1;
                            chars[length14] = (char) ((Utf8.REPLACEMENT_CODE_POINT >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                            length9 = length20 + 1;
                            chars[length20] = (char) ((65533 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                        } else {
                            chars[length14] = Utf8.REPLACEMENT_CHARACTER;
                            length9 = length14 + 1;
                        }
                        Unit unit11 = Unit.INSTANCE;
                        if (i4 > index$iv + 1) {
                            if (((192 & $this$process4Utf8Bytes$iv$iv[index$iv + 1]) == 128 ? (byte) 1 : 0) != 0) {
                                if (i4 > index$iv + 2) {
                                    if ((192 & $this$process4Utf8Bytes$iv$iv[index$iv + 2]) == 128) {
                                        length2 = length9;
                                        i = 3;
                                    }
                                }
                                length2 = length9;
                                i = 2;
                            }
                        }
                        length2 = length9;
                        i = 1;
                    } else {
                        byte b0$iv$iv3 = $this$process4Utf8Bytes$iv$iv[index$iv];
                        byte b1$iv$iv4 = $this$process4Utf8Bytes$iv$iv[index$iv + 1];
                        if (!((b1$iv$iv4 & 192) == 128)) {
                            if (65533 != 65533) {
                                int length21 = length14 + 1;
                                chars[length14] = (char) ((Utf8.REPLACEMENT_CODE_POINT >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                length8 = length21 + 1;
                                chars[length21] = (char) ((65533 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                            } else {
                                chars[length14] = Utf8.REPLACEMENT_CHARACTER;
                                length8 = length14 + 1;
                            }
                            Unit unit12 = Unit.INSTANCE;
                            length2 = length8;
                            i = 1;
                        } else {
                            byte b2$iv$iv2 = $this$process4Utf8Bytes$iv$iv[index$iv + 2];
                            if (!((b2$iv$iv2 & 192) == 128)) {
                                if (65533 != 65533) {
                                    int length22 = length14 + 1;
                                    chars[length14] = (char) ((Utf8.REPLACEMENT_CODE_POINT >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                    length7 = length22 + 1;
                                    chars[length22] = (char) ((65533 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                                } else {
                                    chars[length14] = Utf8.REPLACEMENT_CHARACTER;
                                    length7 = length14 + 1;
                                }
                                Unit unit13 = Unit.INSTANCE;
                                length2 = length7;
                                i = 2;
                            } else {
                                byte b3$iv$iv = $this$process4Utf8Bytes$iv$iv[index$iv + 3];
                                if (!((b3$iv$iv & 192) == 128)) {
                                    if (65533 != 65533) {
                                        int length23 = length14 + 1;
                                        chars[length14] = (char) ((Utf8.REPLACEMENT_CODE_POINT >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                        length6 = length23 + 1;
                                        chars[length23] = (char) ((65533 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                                    } else {
                                        chars[length14] = Utf8.REPLACEMENT_CHARACTER;
                                        length6 = length14 + 1;
                                    }
                                    Unit unit14 = Unit.INSTANCE;
                                    length2 = length6;
                                    i = 3;
                                } else {
                                    int codePoint$iv$iv3 = (((3678080 ^ b3$iv$iv) ^ (b2$iv$iv2 << 6)) ^ (b1$iv$iv4 << Ascii.FF)) ^ (b0$iv$iv3 << Ascii.DC2);
                                    if (codePoint$iv$iv3 > 1114111) {
                                        if (65533 != 65533) {
                                            int length24 = length14 + 1;
                                            chars[length14] = (char) ((Utf8.REPLACEMENT_CODE_POINT >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                            length = length24 + 1;
                                            chars[length24] = (char) ((65533 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                                        } else {
                                            chars[length14] = Utf8.REPLACEMENT_CHARACTER;
                                            length = length14 + 1;
                                        }
                                        Unit unit15 = Unit.INSTANCE;
                                    } else {
                                        if (55296 <= codePoint$iv$iv3 && codePoint$iv$iv3 < 57344) {
                                            if (65533 != 65533) {
                                                int length25 = length14 + 1;
                                                chars[length14] = (char) ((Utf8.REPLACEMENT_CODE_POINT >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                                length5 = length25 + 1;
                                                chars[length25] = (char) ((65533 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                                            } else {
                                                chars[length14] = Utf8.REPLACEMENT_CHARACTER;
                                                length5 = length14 + 1;
                                            }
                                            Unit unit16 = Unit.INSTANCE;
                                        } else if (codePoint$iv$iv3 < 65536) {
                                            if (65533 != 65533) {
                                                int length26 = length14 + 1;
                                                chars[length14] = (char) ((Utf8.REPLACEMENT_CODE_POINT >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                                length4 = length26 + 1;
                                                chars[length26] = (char) ((65533 & 1023) + Utf8.LOG_SURROGATE_HEADER);
                                            } else {
                                                chars[length14] = Utf8.REPLACEMENT_CHARACTER;
                                                length4 = length14 + 1;
                                            }
                                            Unit unit17 = Unit.INSTANCE;
                                        } else {
                                            int codePoint$iv = codePoint$iv$iv3;
                                            if (codePoint$iv != 65533) {
                                                int length27 = length14 + 1;
                                                chars[length14] = (char) ((codePoint$iv >>> 10) + Utf8.HIGH_SURROGATE_HEADER);
                                                length3 = length27 + 1;
                                                chars[length27] = (char) ((codePoint$iv & 1023) + Utf8.LOG_SURROGATE_HEADER);
                                            } else {
                                                chars[length14] = Utf8.REPLACEMENT_CHARACTER;
                                                length3 = length14 + 1;
                                            }
                                            Unit unit18 = Unit.INSTANCE;
                                        }
                                    }
                                    i = 4;
                                    length2 = length;
                                }
                            }
                        }
                    }
                    index$iv += i;
                    $this$processUtf16Chars$iv2 = $this$processUtf16Chars$iv3;
                } else {
                    chars[length14] = Utf8.REPLACEMENT_CHARACTER;
                    index$iv++;
                    length14++;
                    $this$processUtf16Chars$iv2 = $this$processUtf16Chars$iv3;
                }
            }
        }
        return StringsKt.concatToString(chars, 0, length14);
    }

    public static final byte[] commonAsUtf8ToByteArray(String $this$commonAsUtf8ToByteArray) {
        String str = $this$commonAsUtf8ToByteArray;
        Intrinsics.checkNotNullParameter(str, "<this>");
        byte[] bytes = new byte[(str.length() * 4)];
        int length = str.length();
        for (int index = 0; index < length; index++) {
            char b0 = str.charAt(index);
            if (Intrinsics.compare((int) b0, 128) >= 0) {
                int size = index;
                int endIndex$iv = str.length();
                String $this$processUtf8Bytes$iv = $this$commonAsUtf8ToByteArray;
                int index$iv = index;
                while (index$iv < endIndex$iv) {
                    char c$iv = $this$processUtf8Bytes$iv.charAt(index$iv);
                    if (Intrinsics.compare((int) c$iv, 128) < 0) {
                        bytes[size] = (byte) c$iv;
                        index$iv++;
                        size++;
                        while (index$iv < endIndex$iv && Intrinsics.compare((int) $this$processUtf8Bytes$iv.charAt(index$iv), 128) < 0) {
                            bytes[size] = (byte) $this$processUtf8Bytes$iv.charAt(index$iv);
                            index$iv++;
                            size++;
                        }
                    } else if (Intrinsics.compare((int) c$iv, 2048) < 0) {
                        int size2 = size + 1;
                        bytes[size] = (byte) ((c$iv >> 6) | 192);
                        bytes[size2] = (byte) ((c$iv & '?') | 128);
                        index$iv++;
                        size = size2 + 1;
                    } else {
                        if (!(55296 <= c$iv && c$iv < 57344)) {
                            int size3 = size + 1;
                            bytes[size] = (byte) ((c$iv >> 12) | 224);
                            int size4 = size3 + 1;
                            bytes[size3] = (byte) (((c$iv >> 6) & 63) | 128);
                            bytes[size4] = (byte) ((c$iv & '?') | 128);
                            index$iv++;
                            size = size4 + 1;
                        } else {
                            if (Intrinsics.compare((int) c$iv, 56319) <= 0 && endIndex$iv > index$iv + 1) {
                                char charAt = $this$processUtf8Bytes$iv.charAt(index$iv + 1);
                                if (56320 <= charAt && charAt < 57344) {
                                    int codePoint$iv = ((c$iv << 10) + $this$processUtf8Bytes$iv.charAt(index$iv + 1)) - 56613888;
                                    int size5 = size + 1;
                                    bytes[size] = (byte) ((codePoint$iv >> 18) | 240);
                                    int size6 = size5 + 1;
                                    bytes[size5] = (byte) (((codePoint$iv >> 12) & 63) | 128);
                                    int size7 = size6 + 1;
                                    bytes[size6] = (byte) (((codePoint$iv >> 6) & 63) | 128);
                                    bytes[size7] = (byte) ((codePoint$iv & 63) | 128);
                                    index$iv += 2;
                                    size = size7 + 1;
                                }
                            }
                            bytes[size] = Utf8.REPLACEMENT_BYTE;
                            index$iv++;
                            size++;
                        }
                    }
                }
                byte[] copyOf = Arrays.copyOf(bytes, size);
                Intrinsics.checkNotNullExpressionValue(copyOf, "copyOf(this, newSize)");
                return copyOf;
            }
            bytes[index] = (byte) b0;
        }
        byte[] copyOf2 = Arrays.copyOf(bytes, str.length());
        Intrinsics.checkNotNullExpressionValue(copyOf2, "copyOf(this, newSize)");
        return copyOf2;
    }
}
