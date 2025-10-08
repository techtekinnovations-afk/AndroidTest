package kotlin.io.encoding;

import com.google.common.base.Ascii;
import com.google.firebase.database.DatabaseError;
import java.nio.charset.Charset;
import kotlin.Metadata;
import kotlin.collections.AbstractList;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.Charsets;
import okio.Utf8;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\b\u0017\u0018\u0000 <2\u00020\u0001:\u0002<=B\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0015\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0000¢\u0006\u0002\b\u0011J%\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0000¢\u0006\u0002\b\u0017J \u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u0015H\u0002J\u0010\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u0015H\u0002J%\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0000¢\u0006\u0002\b!J\"\u0010\"\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0015J\"\u0010\"\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0015J0\u0010#\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010$\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0002J4\u0010%\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010$\u001a\u00020\u00102\b\b\u0002\u0010\u001b\u001a\u00020\u00152\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0015J4\u0010%\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00132\u0006\u0010$\u001a\u00020\u00102\b\b\u0002\u0010\u001b\u001a\u00020\u00152\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0015J%\u0010&\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0000¢\u0006\u0002\b'J\"\u0010(\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0015J4\u0010)\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010$\u001a\u00020\u00102\b\b\u0002\u0010\u001b\u001a\u00020\u00152\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0015J5\u0010*\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010$\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0000¢\u0006\u0002\b+J\u0015\u0010,\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\u0015H\u0000¢\u0006\u0002\b-J=\u0010.\u001a\u0002H/\"\f\b\u0000\u0010/*\u000600j\u0002`12\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010$\u001a\u0002H/2\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0015¢\u0006\u0002\u00102J\"\u00103\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u0015J%\u00104\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0000¢\u0006\u0002\b5J(\u00106\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u00107\u001a\u00020\u0015H\u0002J\b\u00108\u001a\u00020\u0003H\u0002J \u00109\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0002J\u0010\u0010:\u001a\u00020\u00002\u0006\u0010;\u001a\u00020\u0006H\u0007R\u0014\u0010\u0004\u001a\u00020\u0003X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0014\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006>"}, d2 = {"Lkotlin/io/encoding/Base64;", "", "isUrlSafe", "", "isMimeScheme", "paddingOption", "Lkotlin/io/encoding/Base64$PaddingOption;", "(ZZLkotlin/io/encoding/Base64$PaddingOption;)V", "isMimeScheme$kotlin_stdlib", "()Z", "isUrlSafe$kotlin_stdlib", "getPaddingOption$kotlin_stdlib", "()Lkotlin/io/encoding/Base64$PaddingOption;", "bytesToStringImpl", "", "source", "", "bytesToStringImpl$kotlin_stdlib", "charsToBytesImpl", "", "startIndex", "", "endIndex", "charsToBytesImpl$kotlin_stdlib", "checkDestinationBounds", "", "destinationSize", "destinationOffset", "capacityNeeded", "checkPaddingIsAllowed", "padIndex", "checkSourceBounds", "sourceSize", "checkSourceBounds$kotlin_stdlib", "decode", "decodeImpl", "destination", "decodeIntoByteArray", "decodeSize", "decodeSize$kotlin_stdlib", "encode", "encodeIntoByteArray", "encodeIntoByteArrayImpl", "encodeIntoByteArrayImpl$kotlin_stdlib", "encodeSize", "encodeSize$kotlin_stdlib", "encodeToAppendable", "A", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "([BLjava/lang/Appendable;II)Ljava/lang/Appendable;", "encodeToByteArray", "encodeToByteArrayImpl", "encodeToByteArrayImpl$kotlin_stdlib", "handlePaddingSymbol", "byteStart", "shouldPadOnEncode", "skipIllegalSymbolsIfMime", "withPadding", "option", "Default", "PaddingOption", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* compiled from: Base64.kt */
public class Base64 {
    public static final Default Default = new Default((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static final Base64 Mime = new Base64(false, true, PaddingOption.PRESENT);
    /* access modifiers changed from: private */
    public static final Base64 UrlSafe = new Base64(true, false, PaddingOption.PRESENT);
    private static final int bitsPerByte = 8;
    private static final int bitsPerSymbol = 6;
    public static final int bytesPerGroup = 3;
    private static final int mimeGroupsPerLine = 19;
    public static final int mimeLineLength = 76;
    /* access modifiers changed from: private */
    public static final byte[] mimeLineSeparatorSymbols = {Ascii.CR, 10};
    public static final byte padSymbol = 61;
    public static final int symbolsPerGroup = 4;
    private final boolean isMimeScheme;
    private final boolean isUrlSafe;
    private final PaddingOption paddingOption;

    public /* synthetic */ Base64(boolean z, boolean z2, PaddingOption paddingOption2, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, z2, paddingOption2);
    }

    private Base64(boolean isUrlSafe2, boolean isMimeScheme2, PaddingOption paddingOption2) {
        this.isUrlSafe = isUrlSafe2;
        this.isMimeScheme = isMimeScheme2;
        this.paddingOption = paddingOption2;
        if (!(!this.isUrlSafe || !this.isMimeScheme)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    public final boolean isUrlSafe$kotlin_stdlib() {
        return this.isUrlSafe;
    }

    public final boolean isMimeScheme$kotlin_stdlib() {
        return this.isMimeScheme;
    }

    public final PaddingOption getPaddingOption$kotlin_stdlib() {
        return this.paddingOption;
    }

    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lkotlin/io/encoding/Base64$PaddingOption;", "", "(Ljava/lang/String;I)V", "PRESENT", "ABSENT", "PRESENT_OPTIONAL", "ABSENT_OPTIONAL", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* compiled from: Base64.kt */
    public enum PaddingOption {
        PRESENT,
        ABSENT,
        PRESENT_OPTIONAL,
        ABSENT_OPTIONAL;

        public static EnumEntries<PaddingOption> getEntries() {
            return $ENTRIES;
        }

        static {
            $ENTRIES = EnumEntriesKt.enumEntries((E[]) (Enum[]) $VALUES);
        }
    }

    public final Base64 withPadding(PaddingOption option) {
        Intrinsics.checkNotNullParameter(option, "option");
        if (this.paddingOption == option) {
            return this;
        }
        return new Base64(this.isUrlSafe, this.isMimeScheme, option);
    }

    public static /* synthetic */ byte[] encodeToByteArray$default(Base64 base64, byte[] bArr, int i, int i2, int i3, Object obj) {
        if (obj == null) {
            if ((i3 & 2) != 0) {
                i = 0;
            }
            if ((i3 & 4) != 0) {
                i2 = bArr.length;
            }
            return base64.encodeToByteArray(bArr, i, i2);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: encodeToByteArray");
    }

    public final byte[] encodeToByteArray(byte[] source, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter(source, "source");
        return encodeToByteArrayImpl$kotlin_stdlib(source, startIndex, endIndex);
    }

    public static /* synthetic */ int encodeIntoByteArray$default(Base64 base64, byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4, Object obj) {
        if (obj == null) {
            if ((i4 & 4) != 0) {
                i = 0;
            }
            if ((i4 & 8) != 0) {
                i2 = 0;
            }
            if ((i4 & 16) != 0) {
                i3 = bArr.length;
            }
            return base64.encodeIntoByteArray(bArr, bArr2, i, i2, i3);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: encodeIntoByteArray");
    }

    public final int encodeIntoByteArray(byte[] source, byte[] destination, int destinationOffset, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(destination, "destination");
        return encodeIntoByteArrayImpl$kotlin_stdlib(source, destination, destinationOffset, startIndex, endIndex);
    }

    public static /* synthetic */ String encode$default(Base64 base64, byte[] bArr, int i, int i2, int i3, Object obj) {
        if (obj == null) {
            if ((i3 & 2) != 0) {
                i = 0;
            }
            if ((i3 & 4) != 0) {
                i2 = bArr.length;
            }
            return base64.encode(bArr, i, i2);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: encode");
    }

    public final String encode(byte[] source, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter(source, "source");
        return new String(encodeToByteArrayImpl$kotlin_stdlib(source, startIndex, endIndex), Charsets.ISO_8859_1);
    }

    public static /* synthetic */ Appendable encodeToAppendable$default(Base64 base64, byte[] bArr, Appendable appendable, int i, int i2, int i3, Object obj) {
        if (obj == null) {
            if ((i3 & 4) != 0) {
                i = 0;
            }
            if ((i3 & 8) != 0) {
                i2 = bArr.length;
            }
            return base64.encodeToAppendable(bArr, appendable, i, i2);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: encodeToAppendable");
    }

    public final <A extends Appendable> A encodeToAppendable(byte[] source, A destination, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(destination, "destination");
        destination.append(new String(encodeToByteArrayImpl$kotlin_stdlib(source, startIndex, endIndex), Charsets.ISO_8859_1));
        return destination;
    }

    public static /* synthetic */ byte[] decode$default(Base64 base64, byte[] bArr, int i, int i2, int i3, Object obj) {
        if (obj == null) {
            if ((i3 & 2) != 0) {
                i = 0;
            }
            if ((i3 & 4) != 0) {
                i2 = bArr.length;
            }
            return base64.decode(bArr, i, i2);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: decode");
    }

    public final byte[] decode(byte[] source, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter(source, "source");
        checkSourceBounds$kotlin_stdlib(source.length, startIndex, endIndex);
        byte[] destination = new byte[decodeSize$kotlin_stdlib(source, startIndex, endIndex)];
        if (decodeImpl(source, destination, 0, startIndex, endIndex) == destination.length) {
            return destination;
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    public static /* synthetic */ int decodeIntoByteArray$default(Base64 base64, byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4, Object obj) {
        if (obj == null) {
            if ((i4 & 4) != 0) {
                i = 0;
            }
            if ((i4 & 8) != 0) {
                i2 = 0;
            }
            if ((i4 & 16) != 0) {
                i3 = bArr.length;
            }
            return base64.decodeIntoByteArray(bArr, bArr2, i, i2, i3);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: decodeIntoByteArray");
    }

    public final int decodeIntoByteArray(byte[] source, byte[] destination, int destinationOffset, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(destination, "destination");
        checkSourceBounds$kotlin_stdlib(source.length, startIndex, endIndex);
        checkDestinationBounds(destination.length, destinationOffset, decodeSize$kotlin_stdlib(source, startIndex, endIndex));
        return decodeImpl(source, destination, destinationOffset, startIndex, endIndex);
    }

    public static /* synthetic */ byte[] decode$default(Base64 base64, CharSequence charSequence, int i, int i2, int i3, Object obj) {
        if (obj == null) {
            if ((i3 & 2) != 0) {
                i = 0;
            }
            if ((i3 & 4) != 0) {
                i2 = charSequence.length();
            }
            return base64.decode(charSequence, i, i2);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: decode");
    }

    public final byte[] decode(CharSequence source, int startIndex, int endIndex) {
        byte[] byteSource;
        Intrinsics.checkNotNullParameter(source, "source");
        if (source instanceof String) {
            checkSourceBounds$kotlin_stdlib(source.length(), startIndex, endIndex);
            String substring = ((String) source).substring(startIndex, endIndex);
            Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
            Charset charset = Charsets.ISO_8859_1;
            Intrinsics.checkNotNull(substring, "null cannot be cast to non-null type java.lang.String");
            byteSource = substring.getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(byteSource, "getBytes(...)");
        } else {
            byteSource = charsToBytesImpl$kotlin_stdlib(source, startIndex, endIndex);
        }
        return decode$default(this, byteSource, 0, 0, 6, (Object) null);
    }

    public static /* synthetic */ int decodeIntoByteArray$default(Base64 base64, CharSequence charSequence, byte[] bArr, int i, int i2, int i3, int i4, Object obj) {
        if (obj == null) {
            if ((i4 & 4) != 0) {
                i = 0;
            }
            if ((i4 & 8) != 0) {
                i2 = 0;
            }
            if ((i4 & 16) != 0) {
                i3 = charSequence.length();
            }
            return base64.decodeIntoByteArray(charSequence, bArr, i, i2, i3);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: decodeIntoByteArray");
    }

    public final int decodeIntoByteArray(CharSequence source, byte[] destination, int destinationOffset, int startIndex, int endIndex) {
        byte[] byteSource;
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(destination, "destination");
        if (source instanceof String) {
            checkSourceBounds$kotlin_stdlib(source.length(), startIndex, endIndex);
            String substring = ((String) source).substring(startIndex, endIndex);
            Intrinsics.checkNotNullExpressionValue(substring, "substring(...)");
            Charset charset = Charsets.ISO_8859_1;
            Intrinsics.checkNotNull(substring, "null cannot be cast to non-null type java.lang.String");
            byteSource = substring.getBytes(charset);
            Intrinsics.checkNotNullExpressionValue(byteSource, "getBytes(...)");
        } else {
            byteSource = charsToBytesImpl$kotlin_stdlib(source, startIndex, endIndex);
        }
        return decodeIntoByteArray$default(this, byteSource, destination, destinationOffset, 0, 0, 24, (Object) null);
    }

    public final byte[] encodeToByteArrayImpl$kotlin_stdlib(byte[] source, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter(source, "source");
        checkSourceBounds$kotlin_stdlib(source.length, startIndex, endIndex);
        byte[] destination = new byte[encodeSize$kotlin_stdlib(endIndex - startIndex)];
        encodeIntoByteArrayImpl$kotlin_stdlib(source, destination, 0, startIndex, endIndex);
        return destination;
    }

    public final int encodeIntoByteArrayImpl$kotlin_stdlib(byte[] source, byte[] destination, int destinationOffset, int startIndex, int endIndex) {
        boolean z;
        byte[] bArr = source;
        byte[] bArr2 = destination;
        int i = destinationOffset;
        int i2 = startIndex;
        int i3 = endIndex;
        Intrinsics.checkNotNullParameter(bArr, "source");
        Intrinsics.checkNotNullParameter(bArr2, "destination");
        checkSourceBounds$kotlin_stdlib(bArr.length, i2, i3);
        checkDestinationBounds(bArr2.length, i, encodeSize$kotlin_stdlib(i3 - i2));
        byte[] encodeMap = this.isUrlSafe ? Base64Kt.base64UrlEncodeMap : Base64Kt.base64EncodeMap;
        int sourceIndex = startIndex;
        int destinationIndex = destinationOffset;
        int groupsPerLine = this.isMimeScheme ? 19 : Integer.MAX_VALUE;
        while (true) {
            z = false;
            if (sourceIndex + 2 >= i3) {
                break;
            }
            int groups = Math.min((i3 - sourceIndex) / 3, groupsPerLine);
            int i4 = 0;
            while (i4 < groups) {
                int sourceIndex2 = sourceIndex + 1;
                int sourceIndex3 = sourceIndex2 + 1;
                int bits = ((bArr[sourceIndex] & 255) << 16) | ((bArr[sourceIndex2] & 255) << 8) | (bArr[sourceIndex3] & 255);
                int destinationIndex2 = destinationIndex + 1;
                bArr2[destinationIndex] = encodeMap[bits >>> 18];
                int destinationIndex3 = destinationIndex2 + 1;
                bArr2[destinationIndex2] = encodeMap[(bits >>> 12) & 63];
                int destinationIndex4 = destinationIndex3 + 1;
                bArr2[destinationIndex3] = encodeMap[(bits >>> 6) & 63];
                destinationIndex = destinationIndex4 + 1;
                bArr2[destinationIndex4] = encodeMap[bits & 63];
                i4++;
                sourceIndex = sourceIndex3 + 1;
            }
            if (groups == groupsPerLine && sourceIndex != i3) {
                int destinationIndex5 = destinationIndex + 1;
                bArr2[destinationIndex] = mimeLineSeparatorSymbols[0];
                destinationIndex = destinationIndex5 + 1;
                bArr2[destinationIndex5] = mimeLineSeparatorSymbols[1];
            }
        }
        switch (i3 - sourceIndex) {
            case 1:
                int sourceIndex4 = sourceIndex + 1;
                int bits2 = (bArr[sourceIndex] & 255) << 4;
                int destinationIndex6 = destinationIndex + 1;
                bArr2[destinationIndex] = encodeMap[bits2 >>> 6];
                destinationIndex = destinationIndex6 + 1;
                bArr2[destinationIndex6] = encodeMap[bits2 & 63];
                if (!shouldPadOnEncode()) {
                    sourceIndex = sourceIndex4;
                    break;
                } else {
                    int destinationIndex7 = destinationIndex + 1;
                    bArr2[destinationIndex] = padSymbol;
                    destinationIndex = destinationIndex7 + 1;
                    bArr2[destinationIndex7] = padSymbol;
                    sourceIndex = sourceIndex4;
                    break;
                }
            case 2:
                int sourceIndex5 = sourceIndex + 1;
                int sourceIndex6 = sourceIndex5 + 1;
                int bits3 = ((bArr[sourceIndex] & 255) << 10) | ((bArr[sourceIndex5] & 255) << 2);
                int destinationIndex8 = destinationIndex + 1;
                bArr2[destinationIndex] = encodeMap[bits3 >>> 12];
                int destinationIndex9 = destinationIndex8 + 1;
                bArr2[destinationIndex8] = encodeMap[(bits3 >>> 6) & 63];
                int destinationIndex10 = destinationIndex9 + 1;
                bArr2[destinationIndex9] = encodeMap[bits3 & 63];
                if (!shouldPadOnEncode()) {
                    sourceIndex = sourceIndex6;
                    destinationIndex = destinationIndex10;
                    break;
                } else {
                    destinationIndex = destinationIndex10 + 1;
                    bArr2[destinationIndex10] = padSymbol;
                    sourceIndex = sourceIndex6;
                    break;
                }
        }
        if (sourceIndex == i3) {
            z = true;
        }
        if (z) {
            return destinationIndex - i;
        }
        throw new IllegalStateException("Check failed.".toString());
    }

    public final int encodeSize$kotlin_stdlib(int sourceSize) {
        int trailingBytes = sourceSize % 3;
        int size = (sourceSize / 3) * 4;
        if (trailingBytes != 0) {
            size += shouldPadOnEncode() ? 4 : trailingBytes + 1;
        }
        if (this.isMimeScheme) {
            size += ((size - 1) / 76) * 2;
        }
        if (size >= 0) {
            return size;
        }
        throw new IllegalArgumentException("Input is too big");
    }

    private final boolean shouldPadOnEncode() {
        return this.paddingOption == PaddingOption.PRESENT || this.paddingOption == PaddingOption.PRESENT_OPTIONAL;
    }

    private final int decodeImpl(byte[] source, byte[] destination, int destinationOffset, int startIndex, int endIndex) {
        int i;
        byte[] bArr = source;
        int i2 = endIndex;
        int[] decodeMap = this.isUrlSafe ? Base64Kt.base64UrlDecodeMap : Base64Kt.base64DecodeMap;
        int payload = 0;
        int byteStart = -8;
        int symbol1 = startIndex;
        int destinationIndex = destinationOffset;
        boolean hasPadding = false;
        while (true) {
            if (symbol1 >= i2) {
                i = 8;
                break;
            }
            if (byteStart != -8 || symbol1 + 3 >= i2) {
                i = 8;
            } else {
                int sourceIndex = symbol1 + 1;
                int symbol12 = decodeMap[bArr[symbol1] & 255];
                int sourceIndex2 = sourceIndex + 1;
                int symbol2 = decodeMap[bArr[sourceIndex] & 255];
                int sourceIndex3 = sourceIndex2 + 1;
                i = 8;
                int symbol3 = decodeMap[bArr[sourceIndex2] & 255];
                int sourceIndex4 = sourceIndex3 + 1;
                int bits = (symbol12 << 18) | (symbol2 << 12) | (symbol3 << 6) | decodeMap[bArr[sourceIndex3] & 255];
                if (bits >= 0) {
                    int destinationIndex2 = destinationIndex + 1;
                    destination[destinationIndex] = (byte) (bits >> 16);
                    int destinationIndex3 = destinationIndex2 + 1;
                    destination[destinationIndex2] = (byte) (bits >> 8);
                    destination[destinationIndex3] = (byte) bits;
                    destinationIndex = destinationIndex3 + 1;
                    symbol1 = sourceIndex4;
                } else {
                    symbol1 = sourceIndex4 - 4;
                }
            }
            int symbol = bArr[symbol1] & 255;
            int symbolBits = decodeMap[symbol];
            if (symbolBits >= 0) {
                int[] decodeMap2 = decodeMap;
                symbol1++;
                payload = (payload << 6) | symbolBits;
                byteStart += 6;
                if (byteStart >= 0) {
                    destination[destinationIndex] = (byte) (payload >>> byteStart);
                    payload &= (1 << byteStart) - 1;
                    byteStart -= 8;
                    destinationIndex++;
                    decodeMap = decodeMap2;
                } else {
                    decodeMap = decodeMap2;
                }
            } else if (symbolBits == -2) {
                hasPadding = true;
                symbol1 = handlePaddingSymbol(bArr, symbol1, i2, byteStart);
                int[] iArr = decodeMap;
                break;
            } else if (this.isMimeScheme) {
                symbol1++;
            } else {
                int[] iArr2 = decodeMap;
                StringBuilder append = new StringBuilder().append("Invalid symbol '").append((char) symbol).append("'(");
                String num = Integer.toString(symbol, CharsKt.checkRadix(i));
                Intrinsics.checkNotNullExpressionValue(num, "toString(...)");
                throw new IllegalArgumentException(append.append(num).append(") at index ").append(symbol1).toString());
            }
        }
        if (byteStart == -2) {
            throw new IllegalArgumentException("The last unit of input does not have enough bits");
        } else if (byteStart != -8 && !hasPadding && this.paddingOption == PaddingOption.PRESENT) {
            throw new IllegalArgumentException("The padding option is set to PRESENT, but the input is not properly padded");
        } else if (payload == 0) {
            int sourceIndex5 = skipIllegalSymbolsIfMime(bArr, symbol1, i2);
            if (sourceIndex5 >= i2) {
                return destinationIndex - destinationOffset;
            }
            int symbol4 = bArr[sourceIndex5] & 255;
            StringBuilder append2 = new StringBuilder().append("Symbol '").append((char) symbol4).append("'(");
            String num2 = Integer.toString(symbol4, CharsKt.checkRadix(i));
            Intrinsics.checkNotNullExpressionValue(num2, "toString(...)");
            throw new IllegalArgumentException(append2.append(num2).append(") at index ").append(sourceIndex5 - 1).append(" is prohibited after the pad character").toString());
        } else {
            throw new IllegalArgumentException("The pad bits must be zeros");
        }
    }

    public final int decodeSize$kotlin_stdlib(byte[] source, int startIndex, int endIndex) {
        Intrinsics.checkNotNullParameter(source, "source");
        int symbols = endIndex - startIndex;
        if (symbols == 0) {
            return 0;
        }
        if (symbols != 1) {
            if (this.isMimeScheme) {
                int index = startIndex;
                while (true) {
                    if (index >= endIndex) {
                        break;
                    }
                    int symbolBits = Base64Kt.base64DecodeMap[source[index] & 255];
                    if (symbolBits < 0) {
                        if (symbolBits == -2) {
                            symbols -= endIndex - index;
                            break;
                        }
                        symbols--;
                    }
                    index++;
                }
            } else if (source[endIndex - 1] == 61) {
                symbols--;
                if (source[endIndex - 2] == 61) {
                    symbols--;
                }
            }
            return (int) ((((long) symbols) * ((long) 6)) / ((long) 8));
        }
        throw new IllegalArgumentException("Input should have at least 2 symbols for Base64 decoding, startIndex: " + startIndex + ", endIndex: " + endIndex);
    }

    public final byte[] charsToBytesImpl$kotlin_stdlib(CharSequence source, int startIndex, int endIndex) {
        int length;
        Intrinsics.checkNotNullParameter(source, "source");
        checkSourceBounds$kotlin_stdlib(source.length(), startIndex, endIndex);
        byte[] byteArray = new byte[(endIndex - startIndex)];
        int length2 = 0;
        for (int index = startIndex; index < endIndex; index++) {
            int symbol = source.charAt(index);
            if (symbol <= 255) {
                length = length2 + 1;
                byteArray[length2] = (byte) symbol;
            } else {
                length = length2 + 1;
                byteArray[length2] = Utf8.REPLACEMENT_BYTE;
            }
            length2 = length;
        }
        return byteArray;
    }

    public final String bytesToStringImpl$kotlin_stdlib(byte[] source) {
        Intrinsics.checkNotNullParameter(source, "source");
        StringBuilder stringBuilder = new StringBuilder(source.length);
        for (byte b : source) {
            stringBuilder.append((char) b);
        }
        String sb = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(sb, "toString(...)");
        return sb;
    }

    private final int handlePaddingSymbol(byte[] source, int padIndex, int endIndex, int byteStart) {
        switch (byteStart) {
            case DatabaseError.MAX_RETRIES /*-8*/:
                throw new IllegalArgumentException("Redundant pad character at index " + padIndex);
            case DatabaseError.EXPIRED_TOKEN /*-6*/:
                checkPaddingIsAllowed(padIndex);
                return padIndex + 1;
            case -4:
                checkPaddingIsAllowed(padIndex);
                int secondPadIndex = skipIllegalSymbolsIfMime(source, padIndex + 1, endIndex);
                if (secondPadIndex != endIndex && source[secondPadIndex] == 61) {
                    return secondPadIndex + 1;
                }
                throw new IllegalArgumentException("Missing one pad character at index " + secondPadIndex);
            case -2:
                return padIndex + 1;
            default:
                throw new IllegalStateException("Unreachable".toString());
        }
    }

    private final void checkPaddingIsAllowed(int padIndex) {
        if (this.paddingOption == PaddingOption.ABSENT) {
            throw new IllegalArgumentException("The padding option is set to ABSENT, but the input has a pad character at index " + padIndex);
        }
    }

    private final int skipIllegalSymbolsIfMime(byte[] source, int startIndex, int endIndex) {
        if (!this.isMimeScheme) {
            return startIndex;
        }
        int sourceIndex = startIndex;
        while (sourceIndex < endIndex) {
            if (Base64Kt.base64DecodeMap[source[sourceIndex] & 255] != -1) {
                return sourceIndex;
            }
            sourceIndex++;
        }
        return sourceIndex;
    }

    public final void checkSourceBounds$kotlin_stdlib(int sourceSize, int startIndex, int endIndex) {
        AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(startIndex, endIndex, sourceSize);
    }

    private final void checkDestinationBounds(int destinationSize, int destinationOffset, int capacityNeeded) {
        if (destinationOffset < 0 || destinationOffset > destinationSize) {
            throw new IndexOutOfBoundsException("destination offset: " + destinationOffset + ", destination size: " + destinationSize);
        }
        int destinationEndIndex = destinationOffset + capacityNeeded;
        if (destinationEndIndex < 0 || destinationEndIndex > destinationSize) {
            throw new IndexOutOfBoundsException("The destination array does not have enough capacity, destination offset: " + destinationOffset + ", destination size: " + destinationSize + ", capacity needed: " + capacityNeeded);
        }
    }

    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\u0005\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005R\u0011\u0010\u0006\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0005R\u000e\u0010\b\u001a\u00020\tXT¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tXT¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tXT¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tXT¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\tXT¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\u000fX\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\tXT¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lkotlin/io/encoding/Base64$Default;", "Lkotlin/io/encoding/Base64;", "()V", "Mime", "getMime", "()Lkotlin/io/encoding/Base64;", "UrlSafe", "getUrlSafe", "bitsPerByte", "", "bitsPerSymbol", "bytesPerGroup", "mimeGroupsPerLine", "mimeLineLength", "mimeLineSeparatorSymbols", "", "getMimeLineSeparatorSymbols$kotlin_stdlib", "()[B", "padSymbol", "", "symbolsPerGroup", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* compiled from: Base64.kt */
    public static final class Default extends Base64 {
        public /* synthetic */ Default(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Default() {
            super(false, false, PaddingOption.PRESENT, (DefaultConstructorMarker) null);
        }

        public final byte[] getMimeLineSeparatorSymbols$kotlin_stdlib() {
            return Base64.mimeLineSeparatorSymbols;
        }

        public final Base64 getUrlSafe() {
            return Base64.UrlSafe;
        }

        public final Base64 getMime() {
            return Base64.Mime;
        }
    }
}
