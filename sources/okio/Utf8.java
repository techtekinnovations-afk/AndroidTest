package okio;

import com.google.common.base.Ascii;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\u001a\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0001H\b\u001a\u0011\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0007H\b\u001a4\u0010\u0010\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\bø\u0001\u0000\u001a4\u0010\u0017\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\bø\u0001\u0000\u001a4\u0010\u0018\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\bø\u0001\u0000\u001a4\u0010\u0019\u001a\u00020\u0016*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00160\u0015H\bø\u0001\u0000\u001a4\u0010\u001a\u001a\u00020\u0016*\u00020\u001b2\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00160\u0015H\bø\u0001\u0000\u001a4\u0010\u001c\u001a\u00020\u0016*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00012\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00160\u0015H\bø\u0001\u0000\u001a%\u0010\u001d\u001a\u00020\u001e*\u00020\u001b2\b\b\u0002\u0010\u0012\u001a\u00020\u00012\b\b\u0002\u0010\u0013\u001a\u00020\u0001H\u0007¢\u0006\u0002\b\u001f\"\u000e\u0010\u0000\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0007XT¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\tXT¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0001XT¢\u0006\u0002\n\u0000\u0002\u0007\n\u0005\b20\u0001¨\u0006 "}, d2 = {"HIGH_SURROGATE_HEADER", "", "LOG_SURROGATE_HEADER", "MASK_2BYTES", "MASK_3BYTES", "MASK_4BYTES", "REPLACEMENT_BYTE", "", "REPLACEMENT_CHARACTER", "", "REPLACEMENT_CODE_POINT", "isIsoControl", "", "codePoint", "isUtf8Continuation", "byte", "process2Utf8Bytes", "", "beginIndex", "endIndex", "yield", "Lkotlin/Function1;", "", "process3Utf8Bytes", "process4Utf8Bytes", "processUtf16Chars", "processUtf8Bytes", "", "processUtf8CodePoints", "utf8Size", "", "size", "okio"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* compiled from: Utf8.kt */
public final class Utf8 {
    public static final int HIGH_SURROGATE_HEADER = 55232;
    public static final int LOG_SURROGATE_HEADER = 56320;
    public static final int MASK_2BYTES = 3968;
    public static final int MASK_3BYTES = -123008;
    public static final int MASK_4BYTES = 3678080;
    public static final byte REPLACEMENT_BYTE = 63;
    public static final char REPLACEMENT_CHARACTER = '�';
    public static final int REPLACEMENT_CODE_POINT = 65533;

    public static final long size(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return size$default(str, 0, 0, 3, (Object) null);
    }

    public static final long size(String str, int i) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return size$default(str, i, 0, 2, (Object) null);
    }

    public static /* synthetic */ long size$default(String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = str.length();
        }
        return size(str, i, i2);
    }

    public static final long size(String $this$utf8Size, int beginIndex, int endIndex) {
        Intrinsics.checkNotNullParameter($this$utf8Size, "<this>");
        boolean z = true;
        if (beginIndex >= 0) {
            if (endIndex >= beginIndex) {
                if (endIndex > $this$utf8Size.length()) {
                    z = false;
                }
                if (z) {
                    long result = 0;
                    int i = beginIndex;
                    while (i < endIndex) {
                        int c = $this$utf8Size.charAt(i);
                        if (c < 128) {
                            result++;
                            i++;
                        } else if (c < 2048) {
                            result += (long) 2;
                            i++;
                        } else if (c < 55296 || c > 57343) {
                            result += (long) 3;
                            i++;
                        } else {
                            int low = i + 1 < endIndex ? $this$utf8Size.charAt(i + 1) : 0;
                            if (c > 56319 || low < 56320 || low > 57343) {
                                result++;
                                i++;
                            } else {
                                result += (long) 4;
                                i += 2;
                            }
                        }
                    }
                    return result;
                }
                throw new IllegalArgumentException(("endIndex > string.length: " + endIndex + " > " + $this$utf8Size.length()).toString());
            }
            throw new IllegalArgumentException(("endIndex < beginIndex: " + endIndex + " < " + beginIndex).toString());
        }
        throw new IllegalArgumentException(("beginIndex < 0: " + beginIndex).toString());
    }

    public static final boolean isIsoControl(int codePoint) {
        if (codePoint >= 0 && codePoint < 32) {
            return true;
        }
        return 127 <= codePoint && codePoint < 160;
    }

    public static final boolean isUtf8Continuation(byte $this$and$iv) {
        return (192 & $this$and$iv) == 128;
    }

    public static final void processUtf8Bytes(String $this$processUtf8Bytes, int beginIndex, int endIndex, Function1<? super Byte, Unit> yield) {
        Intrinsics.checkNotNullParameter($this$processUtf8Bytes, "<this>");
        Intrinsics.checkNotNullParameter(yield, "yield");
        int index = beginIndex;
        while (index < endIndex) {
            char c = $this$processUtf8Bytes.charAt(index);
            if (Intrinsics.compare((int) c, 128) < 0) {
                yield.invoke(Byte.valueOf((byte) c));
                index++;
                while (index < endIndex && Intrinsics.compare((int) $this$processUtf8Bytes.charAt(index), 128) < 0) {
                    yield.invoke(Byte.valueOf((byte) $this$processUtf8Bytes.charAt(index)));
                    index++;
                }
            } else if (Intrinsics.compare((int) c, 2048) < 0) {
                yield.invoke(Byte.valueOf((byte) ((c >> 6) | 192)));
                yield.invoke(Byte.valueOf((byte) (128 | (c & '?'))));
                index++;
            } else {
                boolean z = false;
                if (!(55296 <= c && c < 57344)) {
                    yield.invoke(Byte.valueOf((byte) ((c >> 12) | 224)));
                    yield.invoke(Byte.valueOf((byte) (((c >> 6) & 63) | 128)));
                    yield.invoke(Byte.valueOf((byte) (128 | (c & '?'))));
                    index++;
                } else {
                    if (Intrinsics.compare((int) c, 56319) <= 0 && endIndex > index + 1) {
                        char charAt = $this$processUtf8Bytes.charAt(index + 1);
                        if (56320 <= charAt && charAt < 57344) {
                            z = true;
                        }
                        if (z) {
                            int codePoint = ((c << 10) + $this$processUtf8Bytes.charAt(index + 1)) - 56613888;
                            yield.invoke(Byte.valueOf((byte) ((codePoint >> 18) | 240)));
                            yield.invoke(Byte.valueOf((byte) (((codePoint >> 12) & 63) | 128)));
                            yield.invoke(Byte.valueOf((byte) (((codePoint >> 6) & 63) | 128)));
                            yield.invoke(Byte.valueOf((byte) (128 | (codePoint & 63))));
                            index += 2;
                        }
                    }
                    yield.invoke(Byte.valueOf(REPLACEMENT_BYTE));
                    index++;
                }
            }
        }
    }

    public static final void processUtf8CodePoints(byte[] $this$processUtf8CodePoints, int beginIndex, int endIndex, Function1<? super Integer, Unit> yield) {
        byte b0$iv;
        byte b2$iv;
        int i;
        byte[] bArr = $this$processUtf8CodePoints;
        int i2 = endIndex;
        Function1<? super Integer, Unit> function1 = yield;
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        Intrinsics.checkNotNullParameter(function1, "yield");
        int index = beginIndex;
        while (index < i2) {
            byte b0 = bArr[index];
            if (b0 >= 0) {
                function1.invoke(Integer.valueOf(b0));
                index++;
                while (index < i2 && bArr[index] >= 0) {
                    function1.invoke(Integer.valueOf(bArr[index]));
                    index++;
                }
            } else if ((b0 >> 5) == -2) {
                byte[] $this$process2Utf8Bytes$iv = $this$processUtf8CodePoints;
                if (i2 <= index + 1) {
                    function1.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                    Unit unit = Unit.INSTANCE;
                    i = 1;
                } else {
                    byte b0$iv2 = $this$process2Utf8Bytes$iv[index];
                    byte b1$iv = $this$process2Utf8Bytes$iv[index + 1];
                    if (!((b1$iv & 192) == 128)) {
                        function1.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                        Unit unit2 = Unit.INSTANCE;
                        i = 1;
                    } else {
                        int codePoint$iv = (b1$iv ^ MASK_2BYTES) ^ (b0$iv2 << 6);
                        if (codePoint$iv < 128) {
                            function1.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                            Unit unit3 = Unit.INSTANCE;
                        } else {
                            function1.invoke(Integer.valueOf(codePoint$iv));
                            Unit unit4 = Unit.INSTANCE;
                        }
                        i = 2;
                    }
                }
                index += i;
            } else if ((b0 >> 4) == -2) {
                byte[] $this$process3Utf8Bytes$iv = $this$processUtf8CodePoints;
                if (i2 <= index + 2) {
                    function1.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                    Unit unit5 = Unit.INSTANCE;
                    if (i2 > index + 1) {
                        if ((192 & $this$process3Utf8Bytes$iv[index + 1]) == 128) {
                            b2$iv = 2;
                        }
                    }
                    b2$iv = 1;
                } else {
                    byte b0$iv3 = $this$process3Utf8Bytes$iv[index];
                    byte b1$iv2 = $this$process3Utf8Bytes$iv[index + 1];
                    if (!((b1$iv2 & 192) == 128)) {
                        function1.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                        Unit unit6 = Unit.INSTANCE;
                        b2$iv = 1;
                    } else {
                        byte b2$iv2 = $this$process3Utf8Bytes$iv[index + 2];
                        if (!((b2$iv2 & 192) == 128)) {
                            function1.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                            Unit unit7 = Unit.INSTANCE;
                            b2$iv = 2;
                        } else {
                            int codePoint$iv2 = ((-123008 ^ b2$iv2) ^ (b1$iv2 << 6)) ^ (b0$iv3 << Ascii.FF);
                            if (codePoint$iv2 < 2048) {
                                function1.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                                Unit unit8 = Unit.INSTANCE;
                            } else {
                                if (55296 <= codePoint$iv2 && codePoint$iv2 < 57344) {
                                    function1.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                                    Unit unit9 = Unit.INSTANCE;
                                } else {
                                    function1.invoke(Integer.valueOf(codePoint$iv2));
                                    Unit unit10 = Unit.INSTANCE;
                                }
                            }
                            b2$iv = 3;
                        }
                    }
                }
                index += b2$iv;
            } else if ((b0 >> 3) == -2) {
                byte[] $this$process4Utf8Bytes$iv = $this$processUtf8CodePoints;
                if (i2 <= index + 3) {
                    function1.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                    Unit unit11 = Unit.INSTANCE;
                    if (i2 > index + 1) {
                        if (((192 & $this$process4Utf8Bytes$iv[index + 1]) == 128 ? (byte) 1 : 0) != 0) {
                            if (i2 > index + 2) {
                                if (((192 & $this$process4Utf8Bytes$iv[index + 2]) == 128 ? 1 : 0) != 0) {
                                    b0$iv = 3;
                                }
                            }
                            b0$iv = 2;
                        }
                    }
                    b0$iv = 1;
                } else {
                    byte b0$iv4 = $this$process4Utf8Bytes$iv[index];
                    byte b1$iv3 = $this$process4Utf8Bytes$iv[index + 1];
                    if (!((b1$iv3 & 192) == 128)) {
                        function1.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                        Unit unit12 = Unit.INSTANCE;
                        b0$iv = 1;
                    } else {
                        byte b2$iv3 = $this$process4Utf8Bytes$iv[index + 2];
                        if (!((b2$iv3 & 192) == 128)) {
                            function1.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                            Unit unit13 = Unit.INSTANCE;
                            b0$iv = 2;
                        } else {
                            byte b3$iv = $this$process4Utf8Bytes$iv[index + 3];
                            if (!((b3$iv & 192) == 128)) {
                                function1.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                                Unit unit14 = Unit.INSTANCE;
                                b0$iv = 3;
                            } else {
                                int codePoint$iv3 = (((3678080 ^ b3$iv) ^ (b2$iv3 << 6)) ^ (b1$iv3 << Ascii.FF)) ^ (b0$iv4 << Ascii.DC2);
                                if (codePoint$iv3 > 1114111) {
                                    function1.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                                    Unit unit15 = Unit.INSTANCE;
                                } else {
                                    if (55296 <= codePoint$iv3 && codePoint$iv3 < 57344) {
                                        function1.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                                        Unit unit16 = Unit.INSTANCE;
                                    } else if (codePoint$iv3 < 65536) {
                                        function1.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                                        Unit unit17 = Unit.INSTANCE;
                                    } else {
                                        function1.invoke(Integer.valueOf(codePoint$iv3));
                                        Unit unit18 = Unit.INSTANCE;
                                    }
                                }
                                b0$iv = 4;
                            }
                        }
                    }
                }
                index += b0$iv;
            } else {
                function1.invoke(Integer.valueOf(REPLACEMENT_CODE_POINT));
                index++;
            }
        }
    }

    public static final void processUtf16Chars(byte[] $this$processUtf16Chars, int beginIndex, int endIndex, Function1<? super Character, Unit> yield) {
        int i;
        byte b2$iv;
        int i2;
        byte[] bArr = $this$processUtf16Chars;
        int i3 = endIndex;
        Function1<? super Character, Unit> function1 = yield;
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        Intrinsics.checkNotNullParameter(function1, "yield");
        int index = beginIndex;
        while (index < i3) {
            byte b0 = bArr[index];
            if (b0 >= 0) {
                function1.invoke(Character.valueOf((char) b0));
                index++;
                while (index < i3 && bArr[index] >= 0) {
                    function1.invoke(Character.valueOf((char) bArr[index]));
                    index++;
                }
            } else if ((b0 >> 5) == -2) {
                byte[] $this$process2Utf8Bytes$iv = $this$processUtf16Chars;
                if (i3 <= index + 1) {
                    function1.invoke(Character.valueOf((char) REPLACEMENT_CODE_POINT));
                    Unit unit = Unit.INSTANCE;
                    i2 = 1;
                } else {
                    byte b0$iv = $this$process2Utf8Bytes$iv[index];
                    byte b1$iv = $this$process2Utf8Bytes$iv[index + 1];
                    if (!((b1$iv & 192) == 128)) {
                        function1.invoke(Character.valueOf((char) REPLACEMENT_CODE_POINT));
                        Unit unit2 = Unit.INSTANCE;
                        i2 = 1;
                    } else {
                        int codePoint$iv = (b1$iv ^ MASK_2BYTES) ^ (b0$iv << 6);
                        if (codePoint$iv < 128) {
                            function1.invoke(Character.valueOf((char) REPLACEMENT_CODE_POINT));
                            Unit unit3 = Unit.INSTANCE;
                        } else {
                            function1.invoke(Character.valueOf((char) codePoint$iv));
                            Unit unit4 = Unit.INSTANCE;
                        }
                        i2 = 2;
                    }
                }
                index += i2;
            } else if ((b0 >> 4) == -2) {
                byte[] $this$process3Utf8Bytes$iv = $this$processUtf16Chars;
                if (i3 <= index + 2) {
                    function1.invoke(Character.valueOf((char) REPLACEMENT_CODE_POINT));
                    Unit unit5 = Unit.INSTANCE;
                    if (i3 > index + 1) {
                        if ((192 & $this$process3Utf8Bytes$iv[index + 1]) == 128) {
                            b2$iv = 2;
                        }
                    }
                    b2$iv = 1;
                } else {
                    byte b0$iv2 = $this$process3Utf8Bytes$iv[index];
                    byte b1$iv2 = $this$process3Utf8Bytes$iv[index + 1];
                    if (!((b1$iv2 & 192) == 128)) {
                        function1.invoke(Character.valueOf((char) REPLACEMENT_CODE_POINT));
                        Unit unit6 = Unit.INSTANCE;
                        b2$iv = 1;
                    } else {
                        byte b2$iv2 = $this$process3Utf8Bytes$iv[index + 2];
                        if (!((b2$iv2 & 192) == 128)) {
                            function1.invoke(Character.valueOf((char) REPLACEMENT_CODE_POINT));
                            Unit unit7 = Unit.INSTANCE;
                            b2$iv = 2;
                        } else {
                            int codePoint$iv2 = ((-123008 ^ b2$iv2) ^ (b1$iv2 << 6)) ^ (b0$iv2 << Ascii.FF);
                            if (codePoint$iv2 < 2048) {
                                function1.invoke(Character.valueOf((char) REPLACEMENT_CODE_POINT));
                                Unit unit8 = Unit.INSTANCE;
                            } else {
                                if (55296 <= codePoint$iv2 && codePoint$iv2 < 57344) {
                                    function1.invoke(Character.valueOf((char) REPLACEMENT_CODE_POINT));
                                    Unit unit9 = Unit.INSTANCE;
                                } else {
                                    function1.invoke(Character.valueOf((char) codePoint$iv2));
                                    Unit unit10 = Unit.INSTANCE;
                                }
                            }
                            b2$iv = 3;
                        }
                    }
                }
                index += b2$iv;
            } else if ((b0 >> 3) == -2) {
                byte[] $this$process4Utf8Bytes$iv = $this$processUtf16Chars;
                if (i3 <= index + 3) {
                    if (65533 != 65533) {
                        function1.invoke(Character.valueOf((char) ((REPLACEMENT_CODE_POINT >>> 10) + HIGH_SURROGATE_HEADER)));
                        function1.invoke(Character.valueOf((char) ((65533 & 1023) + LOG_SURROGATE_HEADER)));
                    } else {
                        function1.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                    }
                    Unit unit11 = Unit.INSTANCE;
                    if (i3 > index + 1) {
                        if (((192 & $this$process4Utf8Bytes$iv[index + 1]) == 128 ? (byte) 1 : 0) != 0) {
                            if (i3 > index + 2) {
                                if (((192 & $this$process4Utf8Bytes$iv[index + 2]) == 128 ? 1 : 0) != 0) {
                                    i = 3;
                                }
                            }
                            i = 2;
                        }
                    }
                    i = 1;
                } else {
                    byte b0$iv3 = $this$process4Utf8Bytes$iv[index];
                    byte b1$iv3 = $this$process4Utf8Bytes$iv[index + 1];
                    if (!((b1$iv3 & 192) == 128)) {
                        if (65533 != 65533) {
                            function1.invoke(Character.valueOf((char) ((REPLACEMENT_CODE_POINT >>> 10) + HIGH_SURROGATE_HEADER)));
                            function1.invoke(Character.valueOf((char) ((65533 & 1023) + LOG_SURROGATE_HEADER)));
                        } else {
                            function1.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                        }
                        Unit unit12 = Unit.INSTANCE;
                        i = 1;
                    } else {
                        byte b2$iv3 = $this$process4Utf8Bytes$iv[index + 2];
                        if (!((b2$iv3 & 192) == 128)) {
                            if (65533 != 65533) {
                                function1.invoke(Character.valueOf((char) ((REPLACEMENT_CODE_POINT >>> 10) + HIGH_SURROGATE_HEADER)));
                                function1.invoke(Character.valueOf((char) ((65533 & 1023) + LOG_SURROGATE_HEADER)));
                            } else {
                                function1.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                            }
                            Unit unit13 = Unit.INSTANCE;
                            i = 2;
                        } else {
                            byte b3$iv = $this$process4Utf8Bytes$iv[index + 3];
                            if (!((b3$iv & 192) == 128)) {
                                if (65533 != 65533) {
                                    function1.invoke(Character.valueOf((char) ((REPLACEMENT_CODE_POINT >>> 10) + HIGH_SURROGATE_HEADER)));
                                    function1.invoke(Character.valueOf((char) ((65533 & 1023) + LOG_SURROGATE_HEADER)));
                                } else {
                                    function1.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                                }
                                Unit unit14 = Unit.INSTANCE;
                                i = 3;
                            } else {
                                int codePoint$iv3 = (((3678080 ^ b3$iv) ^ (b2$iv3 << 6)) ^ (b1$iv3 << Ascii.FF)) ^ (b0$iv3 << Ascii.DC2);
                                if (codePoint$iv3 > 1114111) {
                                    if (65533 != 65533) {
                                        function1.invoke(Character.valueOf((char) ((REPLACEMENT_CODE_POINT >>> 10) + HIGH_SURROGATE_HEADER)));
                                        function1.invoke(Character.valueOf((char) ((65533 & 1023) + LOG_SURROGATE_HEADER)));
                                    } else {
                                        function1.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                                    }
                                    Unit unit15 = Unit.INSTANCE;
                                } else {
                                    if (55296 <= codePoint$iv3 && codePoint$iv3 < 57344) {
                                        if (65533 != 65533) {
                                            function1.invoke(Character.valueOf((char) ((REPLACEMENT_CODE_POINT >>> 10) + HIGH_SURROGATE_HEADER)));
                                            function1.invoke(Character.valueOf((char) ((65533 & 1023) + LOG_SURROGATE_HEADER)));
                                        } else {
                                            function1.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                                        }
                                        Unit unit16 = Unit.INSTANCE;
                                    } else if (codePoint$iv3 < 65536) {
                                        if (65533 != 65533) {
                                            function1.invoke(Character.valueOf((char) ((REPLACEMENT_CODE_POINT >>> 10) + HIGH_SURROGATE_HEADER)));
                                            function1.invoke(Character.valueOf((char) ((65533 & 1023) + LOG_SURROGATE_HEADER)));
                                        } else {
                                            function1.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                                        }
                                        Unit unit17 = Unit.INSTANCE;
                                    } else {
                                        int codePoint = codePoint$iv3;
                                        if (codePoint != 65533) {
                                            function1.invoke(Character.valueOf((char) ((codePoint >>> 10) + HIGH_SURROGATE_HEADER)));
                                            function1.invoke(Character.valueOf((char) ((codePoint & 1023) + LOG_SURROGATE_HEADER)));
                                        } else {
                                            function1.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                                        }
                                        Unit unit18 = Unit.INSTANCE;
                                    }
                                }
                                i = 4;
                            }
                        }
                    }
                }
                index += i;
            } else {
                function1.invoke(Character.valueOf(REPLACEMENT_CHARACTER));
                index++;
            }
        }
    }

    public static final int process2Utf8Bytes(byte[] $this$process2Utf8Bytes, int beginIndex, int endIndex, Function1<? super Integer, Unit> yield) {
        Intrinsics.checkNotNullParameter($this$process2Utf8Bytes, "<this>");
        Intrinsics.checkNotNullParameter(yield, "yield");
        int i = beginIndex + 1;
        Integer valueOf = Integer.valueOf(REPLACEMENT_CODE_POINT);
        if (endIndex <= i) {
            yield.invoke(valueOf);
            return 1;
        }
        byte b0 = $this$process2Utf8Bytes[beginIndex];
        byte b1 = $this$process2Utf8Bytes[beginIndex + 1];
        if (!((192 & b1) == 128)) {
            yield.invoke(valueOf);
            return 1;
        }
        int codePoint = (b1 ^ MASK_2BYTES) ^ (b0 << 6);
        if (codePoint < 128) {
            yield.invoke(valueOf);
            return 2;
        }
        yield.invoke(Integer.valueOf(codePoint));
        return 2;
    }

    public static final int process3Utf8Bytes(byte[] $this$process3Utf8Bytes, int beginIndex, int endIndex, Function1<? super Integer, Unit> yield) {
        byte[] bArr = $this$process3Utf8Bytes;
        int i = endIndex;
        Function1<? super Integer, Unit> function1 = yield;
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        Intrinsics.checkNotNullParameter(function1, "yield");
        int i2 = beginIndex + 2;
        boolean z = false;
        Integer valueOf = Integer.valueOf(REPLACEMENT_CODE_POINT);
        if (i <= i2) {
            function1.invoke(valueOf);
            if (i > beginIndex + 1) {
                if ((192 & bArr[beginIndex + 1]) == 128) {
                    z = true;
                }
                return !z ? 1 : 2;
            }
        }
        byte b0 = bArr[beginIndex];
        byte b1 = bArr[beginIndex + 1];
        if (((192 & b1) == 128 ? 1 : 0) == 0) {
            function1.invoke(valueOf);
            return 1;
        }
        byte b2 = bArr[beginIndex + 2];
        if (!((192 & b2) == 128)) {
            function1.invoke(valueOf);
            return 2;
        }
        int codePoint = ((-123008 ^ b2) ^ (b1 << 6)) ^ (b0 << Ascii.FF);
        if (codePoint < 2048) {
            function1.invoke(valueOf);
            return 3;
        }
        if (55296 <= codePoint && codePoint < 57344) {
            z = true;
        }
        if (z) {
            function1.invoke(valueOf);
            return 3;
        }
        function1.invoke(Integer.valueOf(codePoint));
        return 3;
    }

    public static final int process4Utf8Bytes(byte[] $this$process4Utf8Bytes, int beginIndex, int endIndex, Function1<? super Integer, Unit> yield) {
        byte[] bArr = $this$process4Utf8Bytes;
        int i = endIndex;
        Function1<? super Integer, Unit> function1 = yield;
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        Intrinsics.checkNotNullParameter(function1, "yield");
        int i2 = beginIndex + 3;
        boolean z = false;
        Integer valueOf = Integer.valueOf(REPLACEMENT_CODE_POINT);
        if (i <= i2) {
            function1.invoke(valueOf);
            if (i > beginIndex + 1) {
                if (((192 & bArr[beginIndex + 1]) == 128 ? (byte) 1 : 0) != 0) {
                    if (i > beginIndex + 2) {
                        if ((192 & bArr[beginIndex + 2]) == 128) {
                            z = true;
                        }
                        return !z ? 2 : 3;
                    }
                }
            }
            return 1;
        }
        byte b0 = bArr[beginIndex];
        byte b1 = bArr[beginIndex + 1];
        if (((192 & b1) == 128 ? 1 : 0) == 0) {
            function1.invoke(valueOf);
            return 1;
        }
        byte b2 = bArr[beginIndex + 2];
        if (((192 & b2) == 128 ? 1 : 0) == 0) {
            function1.invoke(valueOf);
            return 2;
        }
        byte b3 = bArr[beginIndex + 3];
        if (!((192 & b3) == 128)) {
            function1.invoke(valueOf);
            return 3;
        }
        int codePoint = (((3678080 ^ b3) ^ (b2 << 6)) ^ (b1 << Ascii.FF)) ^ (b0 << Ascii.DC2);
        if (codePoint > 1114111) {
            function1.invoke(valueOf);
            return 4;
        }
        if (55296 <= codePoint && codePoint < 57344) {
            z = true;
        }
        if (z) {
            function1.invoke(valueOf);
            return 4;
        } else if (codePoint < 65536) {
            function1.invoke(valueOf);
            return 4;
        } else {
            function1.invoke(Integer.valueOf(codePoint));
            return 4;
        }
    }
}
