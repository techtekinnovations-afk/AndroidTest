package kotlin.text;

import com.google.firebase.firestore.model.Values;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0007\u0018\u0000 \u00132\u00020\u0001:\u0004\u0011\u0012\u0013\u0014B\u001f\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0015"}, d2 = {"Lkotlin/text/HexFormat;", "", "upperCase", "", "bytes", "Lkotlin/text/HexFormat$BytesHexFormat;", "number", "Lkotlin/text/HexFormat$NumberHexFormat;", "(ZLkotlin/text/HexFormat$BytesHexFormat;Lkotlin/text/HexFormat$NumberHexFormat;)V", "getBytes", "()Lkotlin/text/HexFormat$BytesHexFormat;", "getNumber", "()Lkotlin/text/HexFormat$NumberHexFormat;", "getUpperCase", "()Z", "toString", "", "Builder", "BytesHexFormat", "Companion", "NumberHexFormat", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* compiled from: HexFormat.kt */
public final class HexFormat {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static final HexFormat Default = new HexFormat(false, BytesHexFormat.Companion.getDefault$kotlin_stdlib(), NumberHexFormat.Companion.getDefault$kotlin_stdlib());
    /* access modifiers changed from: private */
    public static final HexFormat UpperCase = new HexFormat(true, BytesHexFormat.Companion.getDefault$kotlin_stdlib(), NumberHexFormat.Companion.getDefault$kotlin_stdlib());
    private final BytesHexFormat bytes;
    private final NumberHexFormat number;
    private final boolean upperCase;

    public HexFormat(boolean upperCase2, BytesHexFormat bytes2, NumberHexFormat number2) {
        Intrinsics.checkNotNullParameter(bytes2, "bytes");
        Intrinsics.checkNotNullParameter(number2, "number");
        this.upperCase = upperCase2;
        this.bytes = bytes2;
        this.number = number2;
    }

    public final boolean getUpperCase() {
        return this.upperCase;
    }

    public final BytesHexFormat getBytes() {
        return this.bytes;
    }

    public final NumberHexFormat getNumber() {
        return this.number;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$toString_u24lambda_u240 = sb;
        StringBuilder append = $this$toString_u24lambda_u240.append("HexFormat(");
        Intrinsics.checkNotNullExpressionValue(append, "append(...)");
        Intrinsics.checkNotNullExpressionValue(append.append(10), "append(...)");
        StringBuilder append2 = $this$toString_u24lambda_u240.append("    upperCase = ").append(this.upperCase);
        Intrinsics.checkNotNullExpressionValue(append2, "append(...)");
        StringBuilder append3 = append2.append(",");
        Intrinsics.checkNotNullExpressionValue(append3, "append(...)");
        Intrinsics.checkNotNullExpressionValue(append3.append(10), "append(...)");
        StringBuilder append4 = $this$toString_u24lambda_u240.append("    bytes = BytesHexFormat(");
        Intrinsics.checkNotNullExpressionValue(append4, "append(...)");
        Intrinsics.checkNotNullExpressionValue(append4.append(10), "append(...)");
        Intrinsics.checkNotNullExpressionValue(this.bytes.appendOptionsTo$kotlin_stdlib($this$toString_u24lambda_u240, "        ").append(10), "append(...)");
        StringBuilder append5 = $this$toString_u24lambda_u240.append("    ),");
        Intrinsics.checkNotNullExpressionValue(append5, "append(...)");
        Intrinsics.checkNotNullExpressionValue(append5.append(10), "append(...)");
        StringBuilder append6 = $this$toString_u24lambda_u240.append("    number = NumberHexFormat(");
        Intrinsics.checkNotNullExpressionValue(append6, "append(...)");
        Intrinsics.checkNotNullExpressionValue(append6.append(10), "append(...)");
        Intrinsics.checkNotNullExpressionValue(this.number.appendOptionsTo$kotlin_stdlib($this$toString_u24lambda_u240, "        ").append(10), "append(...)");
        StringBuilder append7 = $this$toString_u24lambda_u240.append("    )");
        Intrinsics.checkNotNullExpressionValue(append7, "append(...)");
        Intrinsics.checkNotNullExpressionValue(append7.append(10), "append(...)");
        $this$toString_u24lambda_u240.append(")");
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
        return sb2;
    }

    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 #2\u00020\u0001:\u0002\"#B7\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\u0006¢\u0006\u0002\u0010\nJ%\u0010\u001b\u001a\u00060\u001cj\u0002`\u001d2\n\u0010\u001e\u001a\u00060\u001cj\u0002`\u001d2\u0006\u0010\u001f\u001a\u00020\u0006H\u0000¢\u0006\u0002\b J\b\u0010!\u001a\u00020\u0006H\u0016R\u0011\u0010\b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\fR\u0014\u0010\u0013\u001a\u00020\u0014X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u0014X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016R\u0014\u0010\u0019\u001a\u00020\u0014X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016¨\u0006$"}, d2 = {"Lkotlin/text/HexFormat$BytesHexFormat;", "", "bytesPerLine", "", "bytesPerGroup", "groupSeparator", "", "byteSeparator", "bytePrefix", "byteSuffix", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getBytePrefix", "()Ljava/lang/String;", "getByteSeparator", "getByteSuffix", "getBytesPerGroup", "()I", "getBytesPerLine", "getGroupSeparator", "ignoreCase", "", "getIgnoreCase$kotlin_stdlib", "()Z", "noLineAndGroupSeparator", "getNoLineAndGroupSeparator$kotlin_stdlib", "shortByteSeparatorNoPrefixAndSuffix", "getShortByteSeparatorNoPrefixAndSuffix$kotlin_stdlib", "appendOptionsTo", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "sb", "indent", "appendOptionsTo$kotlin_stdlib", "toString", "Builder", "Companion", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* compiled from: HexFormat.kt */
    public static final class BytesHexFormat {
        public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
        /* access modifiers changed from: private */
        public static final BytesHexFormat Default = new BytesHexFormat(Integer.MAX_VALUE, Integer.MAX_VALUE, "  ", "", "", "");
        private final String bytePrefix;
        private final String byteSeparator;
        private final String byteSuffix;
        private final int bytesPerGroup;
        private final int bytesPerLine;
        private final String groupSeparator;
        private final boolean ignoreCase;
        private final boolean noLineAndGroupSeparator;
        private final boolean shortByteSeparatorNoPrefixAndSuffix;

        public BytesHexFormat(int bytesPerLine2, int bytesPerGroup2, String groupSeparator2, String byteSeparator2, String bytePrefix2, String byteSuffix2) {
            boolean z;
            Intrinsics.checkNotNullParameter(groupSeparator2, "groupSeparator");
            Intrinsics.checkNotNullParameter(byteSeparator2, "byteSeparator");
            Intrinsics.checkNotNullParameter(bytePrefix2, "bytePrefix");
            Intrinsics.checkNotNullParameter(byteSuffix2, "byteSuffix");
            this.bytesPerLine = bytesPerLine2;
            this.bytesPerGroup = bytesPerGroup2;
            this.groupSeparator = groupSeparator2;
            this.byteSeparator = byteSeparator2;
            this.bytePrefix = bytePrefix2;
            this.byteSuffix = byteSuffix2;
            boolean z2 = false;
            this.noLineAndGroupSeparator = this.bytesPerLine == Integer.MAX_VALUE && this.bytesPerGroup == Integer.MAX_VALUE;
            if (this.bytePrefix.length() == 0) {
                if ((this.byteSuffix.length() == 0) && this.byteSeparator.length() <= 1) {
                    z = true;
                    this.shortByteSeparatorNoPrefixAndSuffix = z;
                    this.ignoreCase = (HexFormatKt.isCaseSensitive(this.groupSeparator) || HexFormatKt.isCaseSensitive(this.byteSeparator) || HexFormatKt.isCaseSensitive(this.bytePrefix) || HexFormatKt.isCaseSensitive(this.byteSuffix)) ? true : z2;
                }
            }
            z = false;
            this.shortByteSeparatorNoPrefixAndSuffix = z;
            this.ignoreCase = (HexFormatKt.isCaseSensitive(this.groupSeparator) || HexFormatKt.isCaseSensitive(this.byteSeparator) || HexFormatKt.isCaseSensitive(this.bytePrefix) || HexFormatKt.isCaseSensitive(this.byteSuffix)) ? true : z2;
        }

        public final int getBytesPerLine() {
            return this.bytesPerLine;
        }

        public final int getBytesPerGroup() {
            return this.bytesPerGroup;
        }

        public final String getGroupSeparator() {
            return this.groupSeparator;
        }

        public final String getByteSeparator() {
            return this.byteSeparator;
        }

        public final String getBytePrefix() {
            return this.bytePrefix;
        }

        public final String getByteSuffix() {
            return this.byteSuffix;
        }

        public final boolean getNoLineAndGroupSeparator$kotlin_stdlib() {
            return this.noLineAndGroupSeparator;
        }

        public final boolean getShortByteSeparatorNoPrefixAndSuffix$kotlin_stdlib() {
            return this.shortByteSeparatorNoPrefixAndSuffix;
        }

        public final boolean getIgnoreCase$kotlin_stdlib() {
            return this.ignoreCase;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            StringBuilder $this$toString_u24lambda_u240 = sb;
            StringBuilder append = $this$toString_u24lambda_u240.append("BytesHexFormat(");
            Intrinsics.checkNotNullExpressionValue(append, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append.append(10), "append(...)");
            Intrinsics.checkNotNullExpressionValue(appendOptionsTo$kotlin_stdlib($this$toString_u24lambda_u240, "    ").append(10), "append(...)");
            $this$toString_u24lambda_u240.append(")");
            String sb2 = sb.toString();
            Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
            return sb2;
        }

        public final StringBuilder appendOptionsTo$kotlin_stdlib(StringBuilder sb, String indent) {
            Intrinsics.checkNotNullParameter(sb, "sb");
            Intrinsics.checkNotNullParameter(indent, "indent");
            StringBuilder append = sb.append(indent).append("bytesPerLine = ").append(this.bytesPerLine);
            Intrinsics.checkNotNullExpressionValue(append, "append(...)");
            StringBuilder append2 = append.append(",");
            Intrinsics.checkNotNullExpressionValue(append2, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append2.append(10), "append(...)");
            StringBuilder append3 = sb.append(indent).append("bytesPerGroup = ").append(this.bytesPerGroup);
            Intrinsics.checkNotNullExpressionValue(append3, "append(...)");
            StringBuilder append4 = append3.append(",");
            Intrinsics.checkNotNullExpressionValue(append4, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append4.append(10), "append(...)");
            StringBuilder append5 = sb.append(indent).append("groupSeparator = \"").append(this.groupSeparator);
            Intrinsics.checkNotNullExpressionValue(append5, "append(...)");
            StringBuilder append6 = append5.append("\",");
            Intrinsics.checkNotNullExpressionValue(append6, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append6.append(10), "append(...)");
            StringBuilder append7 = sb.append(indent).append("byteSeparator = \"").append(this.byteSeparator);
            Intrinsics.checkNotNullExpressionValue(append7, "append(...)");
            StringBuilder append8 = append7.append("\",");
            Intrinsics.checkNotNullExpressionValue(append8, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append8.append(10), "append(...)");
            StringBuilder append9 = sb.append(indent).append("bytePrefix = \"").append(this.bytePrefix);
            Intrinsics.checkNotNullExpressionValue(append9, "append(...)");
            StringBuilder append10 = append9.append("\",");
            Intrinsics.checkNotNullExpressionValue(append10, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append10.append(10), "append(...)");
            sb.append(indent).append("byteSuffix = \"").append(this.byteSuffix).append("\"");
            return sb;
        }

        @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0000¢\u0006\u0002\u0010\u0002J\r\u0010\u001c\u001a\u00020\u001dH\u0000¢\u0006\u0002\b\u001eR$\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@FX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR$\u0010\n\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@FX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR$\u0010\r\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@FX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u0007\"\u0004\b\u000f\u0010\tR$\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0003\u001a\u00020\u0010@FX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R$\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0003\u001a\u00020\u0010@FX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0013\"\u0004\b\u0018\u0010\u0015R\u001a\u0010\u0019\u001a\u00020\u0004X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0007\"\u0004\b\u001b\u0010\t¨\u0006\u001f"}, d2 = {"Lkotlin/text/HexFormat$BytesHexFormat$Builder;", "", "()V", "value", "", "bytePrefix", "getBytePrefix", "()Ljava/lang/String;", "setBytePrefix", "(Ljava/lang/String;)V", "byteSeparator", "getByteSeparator", "setByteSeparator", "byteSuffix", "getByteSuffix", "setByteSuffix", "", "bytesPerGroup", "getBytesPerGroup", "()I", "setBytesPerGroup", "(I)V", "bytesPerLine", "getBytesPerLine", "setBytesPerLine", "groupSeparator", "getGroupSeparator", "setGroupSeparator", "build", "Lkotlin/text/HexFormat$BytesHexFormat;", "build$kotlin_stdlib", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
        /* compiled from: HexFormat.kt */
        public static final class Builder {
            private String bytePrefix = BytesHexFormat.Companion.getDefault$kotlin_stdlib().getBytePrefix();
            private String byteSeparator = BytesHexFormat.Companion.getDefault$kotlin_stdlib().getByteSeparator();
            private String byteSuffix = BytesHexFormat.Companion.getDefault$kotlin_stdlib().getByteSuffix();
            private int bytesPerGroup = BytesHexFormat.Companion.getDefault$kotlin_stdlib().getBytesPerGroup();
            private int bytesPerLine = BytesHexFormat.Companion.getDefault$kotlin_stdlib().getBytesPerLine();
            private String groupSeparator = BytesHexFormat.Companion.getDefault$kotlin_stdlib().getGroupSeparator();

            public final int getBytesPerLine() {
                return this.bytesPerLine;
            }

            public final void setBytesPerLine(int value) {
                if (value > 0) {
                    this.bytesPerLine = value;
                    return;
                }
                throw new IllegalArgumentException("Non-positive values are prohibited for bytesPerLine, but was " + value);
            }

            public final int getBytesPerGroup() {
                return this.bytesPerGroup;
            }

            public final void setBytesPerGroup(int value) {
                if (value > 0) {
                    this.bytesPerGroup = value;
                    return;
                }
                throw new IllegalArgumentException("Non-positive values are prohibited for bytesPerGroup, but was " + value);
            }

            public final String getGroupSeparator() {
                return this.groupSeparator;
            }

            public final void setGroupSeparator(String str) {
                Intrinsics.checkNotNullParameter(str, "<set-?>");
                this.groupSeparator = str;
            }

            public final String getByteSeparator() {
                return this.byteSeparator;
            }

            public final void setByteSeparator(String value) {
                Intrinsics.checkNotNullParameter(value, Values.VECTOR_MAP_VECTORS_KEY);
                if (StringsKt.contains$default((CharSequence) value, 10, false, 2, (Object) null) || StringsKt.contains$default((CharSequence) value, 13, false, 2, (Object) null)) {
                    throw new IllegalArgumentException("LF and CR characters are prohibited in byteSeparator, but was " + value);
                }
                this.byteSeparator = value;
            }

            public final String getBytePrefix() {
                return this.bytePrefix;
            }

            public final void setBytePrefix(String value) {
                Intrinsics.checkNotNullParameter(value, Values.VECTOR_MAP_VECTORS_KEY);
                if (StringsKt.contains$default((CharSequence) value, 10, false, 2, (Object) null) || StringsKt.contains$default((CharSequence) value, 13, false, 2, (Object) null)) {
                    throw new IllegalArgumentException("LF and CR characters are prohibited in bytePrefix, but was " + value);
                }
                this.bytePrefix = value;
            }

            public final String getByteSuffix() {
                return this.byteSuffix;
            }

            public final void setByteSuffix(String value) {
                Intrinsics.checkNotNullParameter(value, Values.VECTOR_MAP_VECTORS_KEY);
                if (StringsKt.contains$default((CharSequence) value, 10, false, 2, (Object) null) || StringsKt.contains$default((CharSequence) value, 13, false, 2, (Object) null)) {
                    throw new IllegalArgumentException("LF and CR characters are prohibited in byteSuffix, but was " + value);
                }
                this.byteSuffix = value;
            }

            public final BytesHexFormat build$kotlin_stdlib() {
                return new BytesHexFormat(this.bytesPerLine, this.bytesPerGroup, this.groupSeparator, this.byteSeparator, this.bytePrefix, this.byteSuffix);
            }
        }

        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lkotlin/text/HexFormat$BytesHexFormat$Companion;", "", "()V", "Default", "Lkotlin/text/HexFormat$BytesHexFormat;", "getDefault$kotlin_stdlib", "()Lkotlin/text/HexFormat$BytesHexFormat;", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
        /* compiled from: HexFormat.kt */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final BytesHexFormat getDefault$kotlin_stdlib() {
                return BytesHexFormat.Default;
            }
        }
    }

    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 !2\u00020\u0001:\u0002 !B'\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ%\u0010\u0019\u001a\u00060\u001aj\u0002`\u001b2\n\u0010\u001c\u001a\u00060\u001aj\u0002`\u001b2\u0006\u0010\u001d\u001a\u00020\u0003H\u0000¢\u0006\u0002\b\u001eJ\b\u0010\u001f\u001a\u00020\u0003H\u0016R\u0014\u0010\n\u001a\u00020\u0006X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u0006X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0014\u0010\u000f\u001a\u00020\u0006X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\fR\u001c\u0010\u0007\u001a\u00020\b8\u0006X\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0016¨\u0006\""}, d2 = {"Lkotlin/text/HexFormat$NumberHexFormat;", "", "prefix", "", "suffix", "removeLeadingZeros", "", "minLength", "", "(Ljava/lang/String;Ljava/lang/String;ZI)V", "ignoreCase", "getIgnoreCase$kotlin_stdlib", "()Z", "isDigitsOnly", "isDigitsOnly$kotlin_stdlib", "isDigitsOnlyAndNoPadding", "isDigitsOnlyAndNoPadding$kotlin_stdlib", "getMinLength$annotations", "()V", "getMinLength", "()I", "getPrefix", "()Ljava/lang/String;", "getRemoveLeadingZeros", "getSuffix", "appendOptionsTo", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "sb", "indent", "appendOptionsTo$kotlin_stdlib", "toString", "Builder", "Companion", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* compiled from: HexFormat.kt */
    public static final class NumberHexFormat {
        public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
        /* access modifiers changed from: private */
        public static final NumberHexFormat Default = new NumberHexFormat("", "", false, 1);
        private final boolean ignoreCase;
        private final boolean isDigitsOnly;
        private final boolean isDigitsOnlyAndNoPadding;
        private final int minLength;
        private final String prefix;
        private final boolean removeLeadingZeros;
        private final String suffix;

        public static /* synthetic */ void getMinLength$annotations() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:16:0x0042  */
        /* JADX WARNING: Removed duplicated region for block: B:17:0x0044  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public NumberHexFormat(java.lang.String r4, java.lang.String r5, boolean r6, int r7) {
            /*
                r3 = this;
                java.lang.String r0 = "prefix"
                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
                java.lang.String r0 = "suffix"
                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r5, r0)
                r3.<init>()
                r3.prefix = r4
                r3.suffix = r5
                r3.removeLeadingZeros = r6
                r3.minLength = r7
                java.lang.String r0 = r3.prefix
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
                int r0 = r0.length()
                r1 = 0
                r2 = 1
                if (r0 != 0) goto L_0x0023
                r0 = r2
                goto L_0x0024
            L_0x0023:
                r0 = r1
            L_0x0024:
                if (r0 == 0) goto L_0x0037
                java.lang.String r0 = r3.suffix
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
                int r0 = r0.length()
                if (r0 != 0) goto L_0x0032
                r0 = r2
                goto L_0x0033
            L_0x0032:
                r0 = r1
            L_0x0033:
                if (r0 == 0) goto L_0x0037
                r0 = r2
                goto L_0x0038
            L_0x0037:
                r0 = r1
            L_0x0038:
                r3.isDigitsOnly = r0
                boolean r0 = r3.isDigitsOnly
                if (r0 == 0) goto L_0x0044
                int r0 = r3.minLength
                if (r0 != r2) goto L_0x0044
                r0 = r2
                goto L_0x0045
            L_0x0044:
                r0 = r1
            L_0x0045:
                r3.isDigitsOnlyAndNoPadding = r0
                java.lang.String r0 = r3.prefix
                boolean r0 = kotlin.text.HexFormatKt.isCaseSensitive(r0)
                if (r0 != 0) goto L_0x0057
                java.lang.String r0 = r3.suffix
                boolean r0 = kotlin.text.HexFormatKt.isCaseSensitive(r0)
                if (r0 == 0) goto L_0x0058
            L_0x0057:
                r1 = r2
            L_0x0058:
                r3.ignoreCase = r1
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.text.HexFormat.NumberHexFormat.<init>(java.lang.String, java.lang.String, boolean, int):void");
        }

        public final String getPrefix() {
            return this.prefix;
        }

        public final String getSuffix() {
            return this.suffix;
        }

        public final boolean getRemoveLeadingZeros() {
            return this.removeLeadingZeros;
        }

        public final int getMinLength() {
            return this.minLength;
        }

        public final boolean isDigitsOnly$kotlin_stdlib() {
            return this.isDigitsOnly;
        }

        public final boolean isDigitsOnlyAndNoPadding$kotlin_stdlib() {
            return this.isDigitsOnlyAndNoPadding;
        }

        public final boolean getIgnoreCase$kotlin_stdlib() {
            return this.ignoreCase;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            StringBuilder $this$toString_u24lambda_u240 = sb;
            StringBuilder append = $this$toString_u24lambda_u240.append("NumberHexFormat(");
            Intrinsics.checkNotNullExpressionValue(append, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append.append(10), "append(...)");
            Intrinsics.checkNotNullExpressionValue(appendOptionsTo$kotlin_stdlib($this$toString_u24lambda_u240, "    ").append(10), "append(...)");
            $this$toString_u24lambda_u240.append(")");
            String sb2 = sb.toString();
            Intrinsics.checkNotNullExpressionValue(sb2, "toString(...)");
            return sb2;
        }

        public final StringBuilder appendOptionsTo$kotlin_stdlib(StringBuilder sb, String indent) {
            Intrinsics.checkNotNullParameter(sb, "sb");
            Intrinsics.checkNotNullParameter(indent, "indent");
            StringBuilder append = sb.append(indent).append("prefix = \"").append(this.prefix);
            Intrinsics.checkNotNullExpressionValue(append, "append(...)");
            StringBuilder append2 = append.append("\",");
            Intrinsics.checkNotNullExpressionValue(append2, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append2.append(10), "append(...)");
            StringBuilder append3 = sb.append(indent).append("suffix = \"").append(this.suffix);
            Intrinsics.checkNotNullExpressionValue(append3, "append(...)");
            StringBuilder append4 = append3.append("\",");
            Intrinsics.checkNotNullExpressionValue(append4, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append4.append(10), "append(...)");
            StringBuilder append5 = sb.append(indent).append("removeLeadingZeros = ").append(this.removeLeadingZeros);
            Intrinsics.checkNotNullExpressionValue(append5, "append(...)");
            StringBuilder append6 = append5.append(',');
            Intrinsics.checkNotNullExpressionValue(append6, "append(...)");
            Intrinsics.checkNotNullExpressionValue(append6.append(10), "append(...)");
            sb.append(indent).append("minLength = ").append(this.minLength);
            return sb;
        }

        @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0000¢\u0006\u0002\u0010\u0002J\r\u0010\u001a\u001a\u00020\u001bH\u0000¢\u0006\u0002\b\u001cR,\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0006\u0010\u0002\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR$\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u000b@FX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R$\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u000b@FX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u000e\"\u0004\b\u0019\u0010\u0010¨\u0006\u001d"}, d2 = {"Lkotlin/text/HexFormat$NumberHexFormat$Builder;", "", "()V", "value", "", "minLength", "getMinLength$annotations", "getMinLength", "()I", "setMinLength", "(I)V", "", "prefix", "getPrefix", "()Ljava/lang/String;", "setPrefix", "(Ljava/lang/String;)V", "removeLeadingZeros", "", "getRemoveLeadingZeros", "()Z", "setRemoveLeadingZeros", "(Z)V", "suffix", "getSuffix", "setSuffix", "build", "Lkotlin/text/HexFormat$NumberHexFormat;", "build$kotlin_stdlib", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
        /* compiled from: HexFormat.kt */
        public static final class Builder {
            private int minLength = NumberHexFormat.Companion.getDefault$kotlin_stdlib().getMinLength();
            private String prefix = NumberHexFormat.Companion.getDefault$kotlin_stdlib().getPrefix();
            private boolean removeLeadingZeros = NumberHexFormat.Companion.getDefault$kotlin_stdlib().getRemoveLeadingZeros();
            private String suffix = NumberHexFormat.Companion.getDefault$kotlin_stdlib().getSuffix();

            public static /* synthetic */ void getMinLength$annotations() {
            }

            public final String getPrefix() {
                return this.prefix;
            }

            public final void setPrefix(String value) {
                Intrinsics.checkNotNullParameter(value, Values.VECTOR_MAP_VECTORS_KEY);
                if (StringsKt.contains$default((CharSequence) value, 10, false, 2, (Object) null) || StringsKt.contains$default((CharSequence) value, 13, false, 2, (Object) null)) {
                    throw new IllegalArgumentException("LF and CR characters are prohibited in prefix, but was " + value);
                }
                this.prefix = value;
            }

            public final String getSuffix() {
                return this.suffix;
            }

            public final void setSuffix(String value) {
                Intrinsics.checkNotNullParameter(value, Values.VECTOR_MAP_VECTORS_KEY);
                if (StringsKt.contains$default((CharSequence) value, 10, false, 2, (Object) null) || StringsKt.contains$default((CharSequence) value, 13, false, 2, (Object) null)) {
                    throw new IllegalArgumentException("LF and CR characters are prohibited in suffix, but was " + value);
                }
                this.suffix = value;
            }

            public final boolean getRemoveLeadingZeros() {
                return this.removeLeadingZeros;
            }

            public final void setRemoveLeadingZeros(boolean z) {
                this.removeLeadingZeros = z;
            }

            public final int getMinLength() {
                return this.minLength;
            }

            public final void setMinLength(int value) {
                if (value > 0) {
                    this.minLength = value;
                    return;
                }
                throw new IllegalArgumentException(("Non-positive values are prohibited for minLength, but was " + value).toString());
            }

            public final NumberHexFormat build$kotlin_stdlib() {
                return new NumberHexFormat(this.prefix, this.suffix, this.removeLeadingZeros, this.minLength);
            }
        }

        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lkotlin/text/HexFormat$NumberHexFormat$Companion;", "", "()V", "Default", "Lkotlin/text/HexFormat$NumberHexFormat;", "getDefault$kotlin_stdlib", "()Lkotlin/text/HexFormat$NumberHexFormat;", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
        /* compiled from: HexFormat.kt */
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final NumberHexFormat getDefault$kotlin_stdlib() {
                return NumberHexFormat.Default;
            }
        }
    }

    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007\b\u0001¢\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0001J%\u0010\u0007\u001a\u00020\u00152\u0017\u0010\u0016\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00150\u0017¢\u0006\u0002\b\u0018H\bø\u0001\u0000J%\u0010\n\u001a\u00020\u00152\u0017\u0010\u0016\u001a\u0013\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00150\u0017¢\u0006\u0002\b\u0018H\bø\u0001\u0000R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012\u0002\u0007\n\u0005\b20\u0001¨\u0006\u0019"}, d2 = {"Lkotlin/text/HexFormat$Builder;", "", "()V", "_bytes", "Lkotlin/text/HexFormat$BytesHexFormat$Builder;", "_number", "Lkotlin/text/HexFormat$NumberHexFormat$Builder;", "bytes", "getBytes", "()Lkotlin/text/HexFormat$BytesHexFormat$Builder;", "number", "getNumber", "()Lkotlin/text/HexFormat$NumberHexFormat$Builder;", "upperCase", "", "getUpperCase", "()Z", "setUpperCase", "(Z)V", "build", "Lkotlin/text/HexFormat;", "", "builderAction", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* compiled from: HexFormat.kt */
    public static final class Builder {
        private BytesHexFormat.Builder _bytes;
        private NumberHexFormat.Builder _number;
        private boolean upperCase = HexFormat.Companion.getDefault().getUpperCase();

        public final boolean getUpperCase() {
            return this.upperCase;
        }

        public final void setUpperCase(boolean z) {
            this.upperCase = z;
        }

        public final BytesHexFormat.Builder getBytes() {
            if (this._bytes == null) {
                this._bytes = new BytesHexFormat.Builder();
            }
            BytesHexFormat.Builder builder = this._bytes;
            Intrinsics.checkNotNull(builder);
            return builder;
        }

        public final NumberHexFormat.Builder getNumber() {
            if (this._number == null) {
                this._number = new NumberHexFormat.Builder();
            }
            NumberHexFormat.Builder builder = this._number;
            Intrinsics.checkNotNull(builder);
            return builder;
        }

        private final void bytes(Function1<? super BytesHexFormat.Builder, Unit> builderAction) {
            Intrinsics.checkNotNullParameter(builderAction, "builderAction");
            builderAction.invoke(getBytes());
        }

        private final void number(Function1<? super NumberHexFormat.Builder, Unit> builderAction) {
            Intrinsics.checkNotNullParameter(builderAction, "builderAction");
            builderAction.invoke(getNumber());
        }

        public final HexFormat build() {
            BytesHexFormat bytesHexFormat;
            NumberHexFormat numberHexFormat;
            boolean z = this.upperCase;
            BytesHexFormat.Builder builder = this._bytes;
            if (builder == null || (bytesHexFormat = builder.build$kotlin_stdlib()) == null) {
                bytesHexFormat = BytesHexFormat.Companion.getDefault$kotlin_stdlib();
            }
            NumberHexFormat.Builder builder2 = this._number;
            if (builder2 == null || (numberHexFormat = builder2.build$kotlin_stdlib()) == null) {
                numberHexFormat = NumberHexFormat.Companion.getDefault$kotlin_stdlib();
            }
            return new HexFormat(z, bytesHexFormat, numberHexFormat);
        }
    }

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006¨\u0006\t"}, d2 = {"Lkotlin/text/HexFormat$Companion;", "", "()V", "Default", "Lkotlin/text/HexFormat;", "getDefault", "()Lkotlin/text/HexFormat;", "UpperCase", "getUpperCase", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = 48)
    /* compiled from: HexFormat.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final HexFormat getDefault() {
            return HexFormat.Default;
        }

        public final HexFormat getUpperCase() {
            return HexFormat.UpperCase;
        }
    }
}
