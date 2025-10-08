package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.DescriptorProtos;
import androidx.datastore.preferences.protobuf.GeneratedMessageLite;
import androidx.datastore.preferences.protobuf.Internal;
import androidx.datastore.preferences.protobuf.WireFormat;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class JavaFeaturesProto {
    public static final int JAVA_FIELD_NUMBER = 1001;
    public static final GeneratedMessageLite.GeneratedExtension<DescriptorProtos.FeatureSet, JavaFeatures> java_ = GeneratedMessageLite.newSingularGeneratedExtension(DescriptorProtos.FeatureSet.getDefaultInstance(), JavaFeatures.getDefaultInstance(), JavaFeatures.getDefaultInstance(), (Internal.EnumLiteMap<?>) null, 1001, WireFormat.FieldType.MESSAGE, JavaFeatures.class);

    public interface JavaFeaturesOrBuilder extends MessageLiteOrBuilder {
        boolean getLegacyClosedEnum();

        JavaFeatures.Utf8Validation getUtf8Validation();

        boolean hasLegacyClosedEnum();

        boolean hasUtf8Validation();
    }

    private JavaFeaturesProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
        registry.add((GeneratedMessageLite.GeneratedExtension<?, ?>) java_);
    }

    public static final class JavaFeatures extends GeneratedMessageLite<JavaFeatures, Builder> implements JavaFeaturesOrBuilder {
        /* access modifiers changed from: private */
        public static final JavaFeatures DEFAULT_INSTANCE;
        public static final int LEGACY_CLOSED_ENUM_FIELD_NUMBER = 1;
        private static volatile Parser<JavaFeatures> PARSER = null;
        public static final int UTF8_VALIDATION_FIELD_NUMBER = 2;
        private int bitField0_;
        private boolean legacyClosedEnum_;
        private int utf8Validation_;

        private JavaFeatures() {
        }

        public enum Utf8Validation implements Internal.EnumLite {
            UTF8_VALIDATION_UNKNOWN(0),
            DEFAULT(1),
            VERIFY(2);
            
            public static final int DEFAULT_VALUE = 1;
            public static final int UTF8_VALIDATION_UNKNOWN_VALUE = 0;
            public static final int VERIFY_VALUE = 2;
            private static final Internal.EnumLiteMap<Utf8Validation> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new Internal.EnumLiteMap<Utf8Validation>() {
                    public Utf8Validation findValueByNumber(int number) {
                        return Utf8Validation.forNumber(number);
                    }
                };
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static Utf8Validation valueOf(int value2) {
                return forNumber(value2);
            }

            public static Utf8Validation forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return UTF8_VALIDATION_UNKNOWN;
                    case 1:
                        return DEFAULT;
                    case 2:
                        return VERIFY;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<Utf8Validation> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return Utf8ValidationVerifier.INSTANCE;
            }

            private static final class Utf8ValidationVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = null;

                private Utf8ValidationVerifier() {
                }

                static {
                    INSTANCE = new Utf8ValidationVerifier();
                }

                public boolean isInRange(int number) {
                    return Utf8Validation.forNumber(number) != null;
                }
            }

            private Utf8Validation(int value2) {
                this.value = value2;
            }
        }

        public boolean hasLegacyClosedEnum() {
            return (this.bitField0_ & 1) != 0;
        }

        public boolean getLegacyClosedEnum() {
            return this.legacyClosedEnum_;
        }

        /* access modifiers changed from: private */
        public void setLegacyClosedEnum(boolean value) {
            this.bitField0_ |= 1;
            this.legacyClosedEnum_ = value;
        }

        /* access modifiers changed from: private */
        public void clearLegacyClosedEnum() {
            this.bitField0_ &= -2;
            this.legacyClosedEnum_ = false;
        }

        public boolean hasUtf8Validation() {
            return (this.bitField0_ & 2) != 0;
        }

        public Utf8Validation getUtf8Validation() {
            Utf8Validation result = Utf8Validation.forNumber(this.utf8Validation_);
            return result == null ? Utf8Validation.UTF8_VALIDATION_UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setUtf8Validation(Utf8Validation value) {
            this.utf8Validation_ = value.getNumber();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void clearUtf8Validation() {
            this.bitField0_ &= -3;
            this.utf8Validation_ = 0;
        }

        public static JavaFeatures parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (JavaFeatures) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static JavaFeatures parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (JavaFeatures) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static JavaFeatures parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (JavaFeatures) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static JavaFeatures parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (JavaFeatures) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static JavaFeatures parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (JavaFeatures) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static JavaFeatures parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (JavaFeatures) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static JavaFeatures parseFrom(InputStream input) throws IOException {
            return (JavaFeatures) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static JavaFeatures parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (JavaFeatures) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static JavaFeatures parseDelimitedFrom(InputStream input) throws IOException {
            return (JavaFeatures) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static JavaFeatures parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (JavaFeatures) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static JavaFeatures parseFrom(CodedInputStream input) throws IOException {
            return (JavaFeatures) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static JavaFeatures parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (JavaFeatures) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(JavaFeatures prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<JavaFeatures, Builder> implements JavaFeaturesOrBuilder {
            private Builder() {
                super(JavaFeatures.DEFAULT_INSTANCE);
            }

            public boolean hasLegacyClosedEnum() {
                return ((JavaFeatures) this.instance).hasLegacyClosedEnum();
            }

            public boolean getLegacyClosedEnum() {
                return ((JavaFeatures) this.instance).getLegacyClosedEnum();
            }

            public Builder setLegacyClosedEnum(boolean value) {
                copyOnWrite();
                ((JavaFeatures) this.instance).setLegacyClosedEnum(value);
                return this;
            }

            public Builder clearLegacyClosedEnum() {
                copyOnWrite();
                ((JavaFeatures) this.instance).clearLegacyClosedEnum();
                return this;
            }

            public boolean hasUtf8Validation() {
                return ((JavaFeatures) this.instance).hasUtf8Validation();
            }

            public Utf8Validation getUtf8Validation() {
                return ((JavaFeatures) this.instance).getUtf8Validation();
            }

            public Builder setUtf8Validation(Utf8Validation value) {
                copyOnWrite();
                ((JavaFeatures) this.instance).setUtf8Validation(value);
                return this;
            }

            public Builder clearUtf8Validation() {
                copyOnWrite();
                ((JavaFeatures) this.instance).clearUtf8Validation();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new JavaFeatures();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဇ\u0000\u0002᠌\u0001", new Object[]{"bitField0_", "legacyClosedEnum_", "utf8Validation_", Utf8Validation.internalGetVerifier()});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<JavaFeatures> parser = PARSER;
                    if (parser == null) {
                        synchronized (JavaFeatures.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            JavaFeatures defaultInstance = new JavaFeatures();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(JavaFeatures.class, defaultInstance);
        }

        public static JavaFeatures getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<JavaFeatures> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
