package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.GeneratedMessageLite;
import androidx.datastore.preferences.protobuf.Internal;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class DescriptorProtos {

    public interface DescriptorProtoOrBuilder extends MessageLiteOrBuilder {
        EnumDescriptorProto getEnumType(int i);

        int getEnumTypeCount();

        List<EnumDescriptorProto> getEnumTypeList();

        FieldDescriptorProto getExtension(int i);

        int getExtensionCount();

        List<FieldDescriptorProto> getExtensionList();

        DescriptorProto.ExtensionRange getExtensionRange(int i);

        int getExtensionRangeCount();

        List<DescriptorProto.ExtensionRange> getExtensionRangeList();

        FieldDescriptorProto getField(int i);

        int getFieldCount();

        List<FieldDescriptorProto> getFieldList();

        String getName();

        ByteString getNameBytes();

        DescriptorProto getNestedType(int i);

        int getNestedTypeCount();

        List<DescriptorProto> getNestedTypeList();

        OneofDescriptorProto getOneofDecl(int i);

        int getOneofDeclCount();

        List<OneofDescriptorProto> getOneofDeclList();

        MessageOptions getOptions();

        String getReservedName(int i);

        ByteString getReservedNameBytes(int i);

        int getReservedNameCount();

        List<String> getReservedNameList();

        DescriptorProto.ReservedRange getReservedRange(int i);

        int getReservedRangeCount();

        List<DescriptorProto.ReservedRange> getReservedRangeList();

        boolean hasName();

        boolean hasOptions();
    }

    public interface EnumDescriptorProtoOrBuilder extends MessageLiteOrBuilder {
        String getName();

        ByteString getNameBytes();

        EnumOptions getOptions();

        String getReservedName(int i);

        ByteString getReservedNameBytes(int i);

        int getReservedNameCount();

        List<String> getReservedNameList();

        EnumDescriptorProto.EnumReservedRange getReservedRange(int i);

        int getReservedRangeCount();

        List<EnumDescriptorProto.EnumReservedRange> getReservedRangeList();

        EnumValueDescriptorProto getValue(int i);

        int getValueCount();

        List<EnumValueDescriptorProto> getValueList();

        boolean hasName();

        boolean hasOptions();
    }

    public interface EnumOptionsOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<EnumOptions, EnumOptions.Builder> {
        boolean getAllowAlias();

        boolean getDeprecated();

        @Deprecated
        boolean getDeprecatedLegacyJsonFieldConflicts();

        FeatureSet getFeatures();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        boolean hasAllowAlias();

        boolean hasDeprecated();

        @Deprecated
        boolean hasDeprecatedLegacyJsonFieldConflicts();

        boolean hasFeatures();
    }

    public interface EnumValueDescriptorProtoOrBuilder extends MessageLiteOrBuilder {
        String getName();

        ByteString getNameBytes();

        int getNumber();

        EnumValueOptions getOptions();

        boolean hasName();

        boolean hasNumber();

        boolean hasOptions();
    }

    public interface EnumValueOptionsOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<EnumValueOptions, EnumValueOptions.Builder> {
        boolean getDebugRedact();

        boolean getDeprecated();

        FieldOptions.FeatureSupport getFeatureSupport();

        FeatureSet getFeatures();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        boolean hasDebugRedact();

        boolean hasDeprecated();

        boolean hasFeatureSupport();

        boolean hasFeatures();
    }

    public interface ExtensionRangeOptionsOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<ExtensionRangeOptions, ExtensionRangeOptions.Builder> {
        ExtensionRangeOptions.Declaration getDeclaration(int i);

        int getDeclarationCount();

        List<ExtensionRangeOptions.Declaration> getDeclarationList();

        FeatureSet getFeatures();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        ExtensionRangeOptions.VerificationState getVerification();

        boolean hasFeatures();

        boolean hasVerification();
    }

    public interface FeatureSetDefaultsOrBuilder extends MessageLiteOrBuilder {
        FeatureSetDefaults.FeatureSetEditionDefault getDefaults(int i);

        int getDefaultsCount();

        List<FeatureSetDefaults.FeatureSetEditionDefault> getDefaultsList();

        Edition getMaximumEdition();

        Edition getMinimumEdition();

        boolean hasMaximumEdition();

        boolean hasMinimumEdition();
    }

    public interface FeatureSetOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<FeatureSet, FeatureSet.Builder> {
        FeatureSet.EnumType getEnumType();

        FeatureSet.FieldPresence getFieldPresence();

        FeatureSet.JsonFormat getJsonFormat();

        FeatureSet.MessageEncoding getMessageEncoding();

        FeatureSet.RepeatedFieldEncoding getRepeatedFieldEncoding();

        FeatureSet.Utf8Validation getUtf8Validation();

        boolean hasEnumType();

        boolean hasFieldPresence();

        boolean hasJsonFormat();

        boolean hasMessageEncoding();

        boolean hasRepeatedFieldEncoding();

        boolean hasUtf8Validation();
    }

    public interface FieldDescriptorProtoOrBuilder extends MessageLiteOrBuilder {
        String getDefaultValue();

        ByteString getDefaultValueBytes();

        String getExtendee();

        ByteString getExtendeeBytes();

        String getJsonName();

        ByteString getJsonNameBytes();

        FieldDescriptorProto.Label getLabel();

        String getName();

        ByteString getNameBytes();

        int getNumber();

        int getOneofIndex();

        FieldOptions getOptions();

        boolean getProto3Optional();

        FieldDescriptorProto.Type getType();

        String getTypeName();

        ByteString getTypeNameBytes();

        boolean hasDefaultValue();

        boolean hasExtendee();

        boolean hasJsonName();

        boolean hasLabel();

        boolean hasName();

        boolean hasNumber();

        boolean hasOneofIndex();

        boolean hasOptions();

        boolean hasProto3Optional();

        boolean hasType();

        boolean hasTypeName();
    }

    public interface FieldOptionsOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<FieldOptions, FieldOptions.Builder> {
        FieldOptions.CType getCtype();

        boolean getDebugRedact();

        boolean getDeprecated();

        FieldOptions.EditionDefault getEditionDefaults(int i);

        int getEditionDefaultsCount();

        List<FieldOptions.EditionDefault> getEditionDefaultsList();

        FieldOptions.FeatureSupport getFeatureSupport();

        FeatureSet getFeatures();

        FieldOptions.JSType getJstype();

        boolean getLazy();

        boolean getPacked();

        FieldOptions.OptionRetention getRetention();

        FieldOptions.OptionTargetType getTargets(int i);

        int getTargetsCount();

        List<FieldOptions.OptionTargetType> getTargetsList();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        boolean getUnverifiedLazy();

        boolean getWeak();

        boolean hasCtype();

        boolean hasDebugRedact();

        boolean hasDeprecated();

        boolean hasFeatureSupport();

        boolean hasFeatures();

        boolean hasJstype();

        boolean hasLazy();

        boolean hasPacked();

        boolean hasRetention();

        boolean hasUnverifiedLazy();

        boolean hasWeak();
    }

    public interface FileDescriptorProtoOrBuilder extends MessageLiteOrBuilder {
        String getDependency(int i);

        ByteString getDependencyBytes(int i);

        int getDependencyCount();

        List<String> getDependencyList();

        Edition getEdition();

        EnumDescriptorProto getEnumType(int i);

        int getEnumTypeCount();

        List<EnumDescriptorProto> getEnumTypeList();

        FieldDescriptorProto getExtension(int i);

        int getExtensionCount();

        List<FieldDescriptorProto> getExtensionList();

        DescriptorProto getMessageType(int i);

        int getMessageTypeCount();

        List<DescriptorProto> getMessageTypeList();

        String getName();

        ByteString getNameBytes();

        FileOptions getOptions();

        String getPackage();

        ByteString getPackageBytes();

        int getPublicDependency(int i);

        int getPublicDependencyCount();

        List<Integer> getPublicDependencyList();

        ServiceDescriptorProto getService(int i);

        int getServiceCount();

        List<ServiceDescriptorProto> getServiceList();

        SourceCodeInfo getSourceCodeInfo();

        String getSyntax();

        ByteString getSyntaxBytes();

        int getWeakDependency(int i);

        int getWeakDependencyCount();

        List<Integer> getWeakDependencyList();

        boolean hasEdition();

        boolean hasName();

        boolean hasOptions();

        boolean hasPackage();

        boolean hasSourceCodeInfo();

        boolean hasSyntax();
    }

    public interface FileDescriptorSetOrBuilder extends MessageLiteOrBuilder {
        FileDescriptorProto getFile(int i);

        int getFileCount();

        List<FileDescriptorProto> getFileList();
    }

    public interface FileOptionsOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<FileOptions, FileOptions.Builder> {
        boolean getCcEnableArenas();

        boolean getCcGenericServices();

        String getCsharpNamespace();

        ByteString getCsharpNamespaceBytes();

        boolean getDeprecated();

        FeatureSet getFeatures();

        String getGoPackage();

        ByteString getGoPackageBytes();

        @Deprecated
        boolean getJavaGenerateEqualsAndHash();

        boolean getJavaGenericServices();

        boolean getJavaMultipleFiles();

        String getJavaOuterClassname();

        ByteString getJavaOuterClassnameBytes();

        String getJavaPackage();

        ByteString getJavaPackageBytes();

        boolean getJavaStringCheckUtf8();

        String getObjcClassPrefix();

        ByteString getObjcClassPrefixBytes();

        FileOptions.OptimizeMode getOptimizeFor();

        String getPhpClassPrefix();

        ByteString getPhpClassPrefixBytes();

        String getPhpMetadataNamespace();

        ByteString getPhpMetadataNamespaceBytes();

        String getPhpNamespace();

        ByteString getPhpNamespaceBytes();

        boolean getPyGenericServices();

        String getRubyPackage();

        ByteString getRubyPackageBytes();

        String getSwiftPrefix();

        ByteString getSwiftPrefixBytes();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        boolean hasCcEnableArenas();

        boolean hasCcGenericServices();

        boolean hasCsharpNamespace();

        boolean hasDeprecated();

        boolean hasFeatures();

        boolean hasGoPackage();

        @Deprecated
        boolean hasJavaGenerateEqualsAndHash();

        boolean hasJavaGenericServices();

        boolean hasJavaMultipleFiles();

        boolean hasJavaOuterClassname();

        boolean hasJavaPackage();

        boolean hasJavaStringCheckUtf8();

        boolean hasObjcClassPrefix();

        boolean hasOptimizeFor();

        boolean hasPhpClassPrefix();

        boolean hasPhpMetadataNamespace();

        boolean hasPhpNamespace();

        boolean hasPyGenericServices();

        boolean hasRubyPackage();

        boolean hasSwiftPrefix();
    }

    public interface GeneratedCodeInfoOrBuilder extends MessageLiteOrBuilder {
        GeneratedCodeInfo.Annotation getAnnotation(int i);

        int getAnnotationCount();

        List<GeneratedCodeInfo.Annotation> getAnnotationList();
    }

    public interface MessageOptionsOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<MessageOptions, MessageOptions.Builder> {
        boolean getDeprecated();

        @Deprecated
        boolean getDeprecatedLegacyJsonFieldConflicts();

        FeatureSet getFeatures();

        boolean getMapEntry();

        boolean getMessageSetWireFormat();

        boolean getNoStandardDescriptorAccessor();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        boolean hasDeprecated();

        @Deprecated
        boolean hasDeprecatedLegacyJsonFieldConflicts();

        boolean hasFeatures();

        boolean hasMapEntry();

        boolean hasMessageSetWireFormat();

        boolean hasNoStandardDescriptorAccessor();
    }

    public interface MethodDescriptorProtoOrBuilder extends MessageLiteOrBuilder {
        boolean getClientStreaming();

        String getInputType();

        ByteString getInputTypeBytes();

        String getName();

        ByteString getNameBytes();

        MethodOptions getOptions();

        String getOutputType();

        ByteString getOutputTypeBytes();

        boolean getServerStreaming();

        boolean hasClientStreaming();

        boolean hasInputType();

        boolean hasName();

        boolean hasOptions();

        boolean hasOutputType();

        boolean hasServerStreaming();
    }

    public interface MethodOptionsOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<MethodOptions, MethodOptions.Builder> {
        boolean getDeprecated();

        FeatureSet getFeatures();

        MethodOptions.IdempotencyLevel getIdempotencyLevel();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        boolean hasDeprecated();

        boolean hasFeatures();

        boolean hasIdempotencyLevel();
    }

    public interface OneofDescriptorProtoOrBuilder extends MessageLiteOrBuilder {
        String getName();

        ByteString getNameBytes();

        OneofOptions getOptions();

        boolean hasName();

        boolean hasOptions();
    }

    public interface OneofOptionsOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<OneofOptions, OneofOptions.Builder> {
        FeatureSet getFeatures();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        boolean hasFeatures();
    }

    public interface ServiceDescriptorProtoOrBuilder extends MessageLiteOrBuilder {
        MethodDescriptorProto getMethod(int i);

        int getMethodCount();

        List<MethodDescriptorProto> getMethodList();

        String getName();

        ByteString getNameBytes();

        ServiceOptions getOptions();

        boolean hasName();

        boolean hasOptions();
    }

    public interface ServiceOptionsOrBuilder extends GeneratedMessageLite.ExtendableMessageOrBuilder<ServiceOptions, ServiceOptions.Builder> {
        boolean getDeprecated();

        FeatureSet getFeatures();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        boolean hasDeprecated();

        boolean hasFeatures();
    }

    public interface SourceCodeInfoOrBuilder extends MessageLiteOrBuilder {
        SourceCodeInfo.Location getLocation(int i);

        int getLocationCount();

        List<SourceCodeInfo.Location> getLocationList();
    }

    public interface UninterpretedOptionOrBuilder extends MessageLiteOrBuilder {
        String getAggregateValue();

        ByteString getAggregateValueBytes();

        double getDoubleValue();

        String getIdentifierValue();

        ByteString getIdentifierValueBytes();

        UninterpretedOption.NamePart getName(int i);

        int getNameCount();

        List<UninterpretedOption.NamePart> getNameList();

        long getNegativeIntValue();

        long getPositiveIntValue();

        ByteString getStringValue();

        boolean hasAggregateValue();

        boolean hasDoubleValue();

        boolean hasIdentifierValue();

        boolean hasNegativeIntValue();

        boolean hasPositiveIntValue();

        boolean hasStringValue();
    }

    private DescriptorProtos() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public enum Edition implements Internal.EnumLite {
        EDITION_UNKNOWN(0),
        EDITION_LEGACY(900),
        EDITION_PROTO2(EDITION_PROTO2_VALUE),
        EDITION_PROTO3(999),
        EDITION_2023(1000),
        EDITION_2024(1001),
        EDITION_1_TEST_ONLY(1),
        EDITION_2_TEST_ONLY(2),
        EDITION_99997_TEST_ONLY(EDITION_99997_TEST_ONLY_VALUE),
        EDITION_99998_TEST_ONLY(EDITION_99998_TEST_ONLY_VALUE),
        EDITION_99999_TEST_ONLY(EDITION_99999_TEST_ONLY_VALUE),
        EDITION_MAX(Integer.MAX_VALUE);
        
        public static final int EDITION_1_TEST_ONLY_VALUE = 1;
        public static final int EDITION_2023_VALUE = 1000;
        public static final int EDITION_2024_VALUE = 1001;
        public static final int EDITION_2_TEST_ONLY_VALUE = 2;
        public static final int EDITION_99997_TEST_ONLY_VALUE = 99997;
        public static final int EDITION_99998_TEST_ONLY_VALUE = 99998;
        public static final int EDITION_99999_TEST_ONLY_VALUE = 99999;
        public static final int EDITION_LEGACY_VALUE = 900;
        public static final int EDITION_MAX_VALUE = Integer.MAX_VALUE;
        public static final int EDITION_PROTO2_VALUE = 998;
        public static final int EDITION_PROTO3_VALUE = 999;
        public static final int EDITION_UNKNOWN_VALUE = 0;
        private static final Internal.EnumLiteMap<Edition> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new Internal.EnumLiteMap<Edition>() {
                public Edition findValueByNumber(int number) {
                    return Edition.forNumber(number);
                }
            };
        }

        public final int getNumber() {
            return this.value;
        }

        @Deprecated
        public static Edition valueOf(int value2) {
            return forNumber(value2);
        }

        public static Edition forNumber(int value2) {
            switch (value2) {
                case 0:
                    return EDITION_UNKNOWN;
                case 1:
                    return EDITION_1_TEST_ONLY;
                case 2:
                    return EDITION_2_TEST_ONLY;
                case 900:
                    return EDITION_LEGACY;
                case EDITION_PROTO2_VALUE:
                    return EDITION_PROTO2;
                case 999:
                    return EDITION_PROTO3;
                case 1000:
                    return EDITION_2023;
                case 1001:
                    return EDITION_2024;
                case EDITION_99997_TEST_ONLY_VALUE:
                    return EDITION_99997_TEST_ONLY;
                case EDITION_99998_TEST_ONLY_VALUE:
                    return EDITION_99998_TEST_ONLY;
                case EDITION_99999_TEST_ONLY_VALUE:
                    return EDITION_99999_TEST_ONLY;
                case Integer.MAX_VALUE:
                    return EDITION_MAX;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<Edition> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return EditionVerifier.INSTANCE;
        }

        private static final class EditionVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = null;

            private EditionVerifier() {
            }

            static {
                INSTANCE = new EditionVerifier();
            }

            public boolean isInRange(int number) {
                return Edition.forNumber(number) != null;
            }
        }

        private Edition(int value2) {
            this.value = value2;
        }
    }

    public static final class FileDescriptorSet extends GeneratedMessageLite<FileDescriptorSet, Builder> implements FileDescriptorSetOrBuilder {
        /* access modifiers changed from: private */
        public static final FileDescriptorSet DEFAULT_INSTANCE;
        public static final int FILE_FIELD_NUMBER = 1;
        private static volatile Parser<FileDescriptorSet> PARSER;
        private Internal.ProtobufList<FileDescriptorProto> file_ = emptyProtobufList();
        private byte memoizedIsInitialized = 2;

        private FileDescriptorSet() {
        }

        public List<FileDescriptorProto> getFileList() {
            return this.file_;
        }

        public List<? extends FileDescriptorProtoOrBuilder> getFileOrBuilderList() {
            return this.file_;
        }

        public int getFileCount() {
            return this.file_.size();
        }

        public FileDescriptorProto getFile(int index) {
            return (FileDescriptorProto) this.file_.get(index);
        }

        public FileDescriptorProtoOrBuilder getFileOrBuilder(int index) {
            return (FileDescriptorProtoOrBuilder) this.file_.get(index);
        }

        private void ensureFileIsMutable() {
            Internal.ProtobufList<FileDescriptorProto> tmp = this.file_;
            if (!tmp.isModifiable()) {
                this.file_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setFile(int index, FileDescriptorProto value) {
            value.getClass();
            ensureFileIsMutable();
            this.file_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addFile(FileDescriptorProto value) {
            value.getClass();
            ensureFileIsMutable();
            this.file_.add(value);
        }

        /* access modifiers changed from: private */
        public void addFile(int index, FileDescriptorProto value) {
            value.getClass();
            ensureFileIsMutable();
            this.file_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllFile(Iterable<? extends FileDescriptorProto> values) {
            ensureFileIsMutable();
            AbstractMessageLite.addAll(values, this.file_);
        }

        /* access modifiers changed from: private */
        public void clearFile() {
            this.file_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeFile(int index) {
            ensureFileIsMutable();
            this.file_.remove(index);
        }

        public static FileDescriptorSet parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (FileDescriptorSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FileDescriptorSet parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileDescriptorSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FileDescriptorSet parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FileDescriptorSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FileDescriptorSet parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileDescriptorSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FileDescriptorSet parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FileDescriptorSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FileDescriptorSet parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileDescriptorSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FileDescriptorSet parseFrom(InputStream input) throws IOException {
            return (FileDescriptorSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FileDescriptorSet parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileDescriptorSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FileDescriptorSet parseDelimitedFrom(InputStream input) throws IOException {
            return (FileDescriptorSet) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static FileDescriptorSet parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileDescriptorSet) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FileDescriptorSet parseFrom(CodedInputStream input) throws IOException {
            return (FileDescriptorSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FileDescriptorSet parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileDescriptorSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(FileDescriptorSet prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<FileDescriptorSet, Builder> implements FileDescriptorSetOrBuilder {
            private Builder() {
                super(FileDescriptorSet.DEFAULT_INSTANCE);
            }

            public List<FileDescriptorProto> getFileList() {
                return Collections.unmodifiableList(((FileDescriptorSet) this.instance).getFileList());
            }

            public int getFileCount() {
                return ((FileDescriptorSet) this.instance).getFileCount();
            }

            public FileDescriptorProto getFile(int index) {
                return ((FileDescriptorSet) this.instance).getFile(index);
            }

            public Builder setFile(int index, FileDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorSet) this.instance).setFile(index, value);
                return this;
            }

            public Builder setFile(int index, FileDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorSet) this.instance).setFile(index, (FileDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addFile(FileDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorSet) this.instance).addFile(value);
                return this;
            }

            public Builder addFile(int index, FileDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorSet) this.instance).addFile(index, value);
                return this;
            }

            public Builder addFile(FileDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorSet) this.instance).addFile((FileDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addFile(int index, FileDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorSet) this.instance).addFile(index, (FileDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addAllFile(Iterable<? extends FileDescriptorProto> values) {
                copyOnWrite();
                ((FileDescriptorSet) this.instance).addAllFile(values);
                return this;
            }

            public Builder clearFile() {
                copyOnWrite();
                ((FileDescriptorSet) this.instance).clearFile();
                return this;
            }

            public Builder removeFile(int index) {
                copyOnWrite();
                ((FileDescriptorSet) this.instance).removeFile(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new FileDescriptorSet();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0001\u0001Л", new Object[]{"file_", FileDescriptorProto.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<FileDescriptorSet> parser = PARSER;
                    if (parser == null) {
                        synchronized (FileDescriptorSet.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            FileDescriptorSet defaultInstance = new FileDescriptorSet();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(FileDescriptorSet.class, defaultInstance);
        }

        public static FileDescriptorSet getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FileDescriptorSet> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class FileDescriptorProto extends GeneratedMessageLite<FileDescriptorProto, Builder> implements FileDescriptorProtoOrBuilder {
        /* access modifiers changed from: private */
        public static final FileDescriptorProto DEFAULT_INSTANCE;
        public static final int DEPENDENCY_FIELD_NUMBER = 3;
        public static final int EDITION_FIELD_NUMBER = 14;
        public static final int ENUM_TYPE_FIELD_NUMBER = 5;
        public static final int EXTENSION_FIELD_NUMBER = 7;
        public static final int MESSAGE_TYPE_FIELD_NUMBER = 4;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int OPTIONS_FIELD_NUMBER = 8;
        public static final int PACKAGE_FIELD_NUMBER = 2;
        private static volatile Parser<FileDescriptorProto> PARSER = null;
        public static final int PUBLIC_DEPENDENCY_FIELD_NUMBER = 10;
        public static final int SERVICE_FIELD_NUMBER = 6;
        public static final int SOURCE_CODE_INFO_FIELD_NUMBER = 9;
        public static final int SYNTAX_FIELD_NUMBER = 12;
        public static final int WEAK_DEPENDENCY_FIELD_NUMBER = 11;
        private int bitField0_;
        private Internal.ProtobufList<String> dependency_ = GeneratedMessageLite.emptyProtobufList();
        private int edition_;
        private Internal.ProtobufList<EnumDescriptorProto> enumType_ = emptyProtobufList();
        private Internal.ProtobufList<FieldDescriptorProto> extension_ = emptyProtobufList();
        private byte memoizedIsInitialized = 2;
        private Internal.ProtobufList<DescriptorProto> messageType_ = emptyProtobufList();
        private String name_ = "";
        private FileOptions options_;
        private String package_ = "";
        private Internal.IntList publicDependency_ = emptyIntList();
        private Internal.ProtobufList<ServiceDescriptorProto> service_ = emptyProtobufList();
        private SourceCodeInfo sourceCodeInfo_;
        private String syntax_ = "";
        private Internal.IntList weakDependency_ = emptyIntList();

        private FileDescriptorProto() {
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 1;
            this.name_ = value;
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.bitField0_ &= -2;
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            this.name_ = value.toStringUtf8();
            this.bitField0_ |= 1;
        }

        public boolean hasPackage() {
            return (this.bitField0_ & 2) != 0;
        }

        public String getPackage() {
            return this.package_;
        }

        public ByteString getPackageBytes() {
            return ByteString.copyFromUtf8(this.package_);
        }

        /* access modifiers changed from: private */
        public void setPackage(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 2;
            this.package_ = value;
        }

        /* access modifiers changed from: private */
        public void clearPackage() {
            this.bitField0_ &= -3;
            this.package_ = getDefaultInstance().getPackage();
        }

        /* access modifiers changed from: private */
        public void setPackageBytes(ByteString value) {
            this.package_ = value.toStringUtf8();
            this.bitField0_ |= 2;
        }

        public List<String> getDependencyList() {
            return this.dependency_;
        }

        public int getDependencyCount() {
            return this.dependency_.size();
        }

        public String getDependency(int index) {
            return (String) this.dependency_.get(index);
        }

        public ByteString getDependencyBytes(int index) {
            return ByteString.copyFromUtf8((String) this.dependency_.get(index));
        }

        private void ensureDependencyIsMutable() {
            Internal.ProtobufList<String> tmp = this.dependency_;
            if (!tmp.isModifiable()) {
                this.dependency_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setDependency(int index, String value) {
            Class<?> cls = value.getClass();
            ensureDependencyIsMutable();
            this.dependency_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addDependency(String value) {
            Class<?> cls = value.getClass();
            ensureDependencyIsMutable();
            this.dependency_.add(value);
        }

        /* access modifiers changed from: private */
        public void addAllDependency(Iterable<String> values) {
            ensureDependencyIsMutable();
            AbstractMessageLite.addAll(values, this.dependency_);
        }

        /* access modifiers changed from: private */
        public void clearDependency() {
            this.dependency_ = GeneratedMessageLite.emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void addDependencyBytes(ByteString value) {
            ensureDependencyIsMutable();
            this.dependency_.add(value.toStringUtf8());
        }

        public List<Integer> getPublicDependencyList() {
            return this.publicDependency_;
        }

        public int getPublicDependencyCount() {
            return this.publicDependency_.size();
        }

        public int getPublicDependency(int index) {
            return this.publicDependency_.getInt(index);
        }

        private void ensurePublicDependencyIsMutable() {
            Internal.IntList tmp = this.publicDependency_;
            if (!tmp.isModifiable()) {
                this.publicDependency_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setPublicDependency(int index, int value) {
            ensurePublicDependencyIsMutable();
            this.publicDependency_.setInt(index, value);
        }

        /* access modifiers changed from: private */
        public void addPublicDependency(int value) {
            ensurePublicDependencyIsMutable();
            this.publicDependency_.addInt(value);
        }

        /* access modifiers changed from: private */
        public void addAllPublicDependency(Iterable<? extends Integer> values) {
            ensurePublicDependencyIsMutable();
            AbstractMessageLite.addAll(values, this.publicDependency_);
        }

        /* access modifiers changed from: private */
        public void clearPublicDependency() {
            this.publicDependency_ = emptyIntList();
        }

        public List<Integer> getWeakDependencyList() {
            return this.weakDependency_;
        }

        public int getWeakDependencyCount() {
            return this.weakDependency_.size();
        }

        public int getWeakDependency(int index) {
            return this.weakDependency_.getInt(index);
        }

        private void ensureWeakDependencyIsMutable() {
            Internal.IntList tmp = this.weakDependency_;
            if (!tmp.isModifiable()) {
                this.weakDependency_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setWeakDependency(int index, int value) {
            ensureWeakDependencyIsMutable();
            this.weakDependency_.setInt(index, value);
        }

        /* access modifiers changed from: private */
        public void addWeakDependency(int value) {
            ensureWeakDependencyIsMutable();
            this.weakDependency_.addInt(value);
        }

        /* access modifiers changed from: private */
        public void addAllWeakDependency(Iterable<? extends Integer> values) {
            ensureWeakDependencyIsMutable();
            AbstractMessageLite.addAll(values, this.weakDependency_);
        }

        /* access modifiers changed from: private */
        public void clearWeakDependency() {
            this.weakDependency_ = emptyIntList();
        }

        public List<DescriptorProto> getMessageTypeList() {
            return this.messageType_;
        }

        public List<? extends DescriptorProtoOrBuilder> getMessageTypeOrBuilderList() {
            return this.messageType_;
        }

        public int getMessageTypeCount() {
            return this.messageType_.size();
        }

        public DescriptorProto getMessageType(int index) {
            return (DescriptorProto) this.messageType_.get(index);
        }

        public DescriptorProtoOrBuilder getMessageTypeOrBuilder(int index) {
            return (DescriptorProtoOrBuilder) this.messageType_.get(index);
        }

        private void ensureMessageTypeIsMutable() {
            Internal.ProtobufList<DescriptorProto> tmp = this.messageType_;
            if (!tmp.isModifiable()) {
                this.messageType_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setMessageType(int index, DescriptorProto value) {
            value.getClass();
            ensureMessageTypeIsMutable();
            this.messageType_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addMessageType(DescriptorProto value) {
            value.getClass();
            ensureMessageTypeIsMutable();
            this.messageType_.add(value);
        }

        /* access modifiers changed from: private */
        public void addMessageType(int index, DescriptorProto value) {
            value.getClass();
            ensureMessageTypeIsMutable();
            this.messageType_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllMessageType(Iterable<? extends DescriptorProto> values) {
            ensureMessageTypeIsMutable();
            AbstractMessageLite.addAll(values, this.messageType_);
        }

        /* access modifiers changed from: private */
        public void clearMessageType() {
            this.messageType_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeMessageType(int index) {
            ensureMessageTypeIsMutable();
            this.messageType_.remove(index);
        }

        public List<EnumDescriptorProto> getEnumTypeList() {
            return this.enumType_;
        }

        public List<? extends EnumDescriptorProtoOrBuilder> getEnumTypeOrBuilderList() {
            return this.enumType_;
        }

        public int getEnumTypeCount() {
            return this.enumType_.size();
        }

        public EnumDescriptorProto getEnumType(int index) {
            return (EnumDescriptorProto) this.enumType_.get(index);
        }

        public EnumDescriptorProtoOrBuilder getEnumTypeOrBuilder(int index) {
            return (EnumDescriptorProtoOrBuilder) this.enumType_.get(index);
        }

        private void ensureEnumTypeIsMutable() {
            Internal.ProtobufList<EnumDescriptorProto> tmp = this.enumType_;
            if (!tmp.isModifiable()) {
                this.enumType_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setEnumType(int index, EnumDescriptorProto value) {
            value.getClass();
            ensureEnumTypeIsMutable();
            this.enumType_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addEnumType(EnumDescriptorProto value) {
            value.getClass();
            ensureEnumTypeIsMutable();
            this.enumType_.add(value);
        }

        /* access modifiers changed from: private */
        public void addEnumType(int index, EnumDescriptorProto value) {
            value.getClass();
            ensureEnumTypeIsMutable();
            this.enumType_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllEnumType(Iterable<? extends EnumDescriptorProto> values) {
            ensureEnumTypeIsMutable();
            AbstractMessageLite.addAll(values, this.enumType_);
        }

        /* access modifiers changed from: private */
        public void clearEnumType() {
            this.enumType_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeEnumType(int index) {
            ensureEnumTypeIsMutable();
            this.enumType_.remove(index);
        }

        public List<ServiceDescriptorProto> getServiceList() {
            return this.service_;
        }

        public List<? extends ServiceDescriptorProtoOrBuilder> getServiceOrBuilderList() {
            return this.service_;
        }

        public int getServiceCount() {
            return this.service_.size();
        }

        public ServiceDescriptorProto getService(int index) {
            return (ServiceDescriptorProto) this.service_.get(index);
        }

        public ServiceDescriptorProtoOrBuilder getServiceOrBuilder(int index) {
            return (ServiceDescriptorProtoOrBuilder) this.service_.get(index);
        }

        private void ensureServiceIsMutable() {
            Internal.ProtobufList<ServiceDescriptorProto> tmp = this.service_;
            if (!tmp.isModifiable()) {
                this.service_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setService(int index, ServiceDescriptorProto value) {
            value.getClass();
            ensureServiceIsMutable();
            this.service_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addService(ServiceDescriptorProto value) {
            value.getClass();
            ensureServiceIsMutable();
            this.service_.add(value);
        }

        /* access modifiers changed from: private */
        public void addService(int index, ServiceDescriptorProto value) {
            value.getClass();
            ensureServiceIsMutable();
            this.service_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllService(Iterable<? extends ServiceDescriptorProto> values) {
            ensureServiceIsMutable();
            AbstractMessageLite.addAll(values, this.service_);
        }

        /* access modifiers changed from: private */
        public void clearService() {
            this.service_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeService(int index) {
            ensureServiceIsMutable();
            this.service_.remove(index);
        }

        public List<FieldDescriptorProto> getExtensionList() {
            return this.extension_;
        }

        public List<? extends FieldDescriptorProtoOrBuilder> getExtensionOrBuilderList() {
            return this.extension_;
        }

        public int getExtensionCount() {
            return this.extension_.size();
        }

        public FieldDescriptorProto getExtension(int index) {
            return (FieldDescriptorProto) this.extension_.get(index);
        }

        public FieldDescriptorProtoOrBuilder getExtensionOrBuilder(int index) {
            return (FieldDescriptorProtoOrBuilder) this.extension_.get(index);
        }

        private void ensureExtensionIsMutable() {
            Internal.ProtobufList<FieldDescriptorProto> tmp = this.extension_;
            if (!tmp.isModifiable()) {
                this.extension_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setExtension(int index, FieldDescriptorProto value) {
            value.getClass();
            ensureExtensionIsMutable();
            this.extension_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addExtension(FieldDescriptorProto value) {
            value.getClass();
            ensureExtensionIsMutable();
            this.extension_.add(value);
        }

        /* access modifiers changed from: private */
        public void addExtension(int index, FieldDescriptorProto value) {
            value.getClass();
            ensureExtensionIsMutable();
            this.extension_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllExtension(Iterable<? extends FieldDescriptorProto> values) {
            ensureExtensionIsMutable();
            AbstractMessageLite.addAll(values, this.extension_);
        }

        /* access modifiers changed from: private */
        public void clearExtension() {
            this.extension_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeExtension(int index) {
            ensureExtensionIsMutable();
            this.extension_.remove(index);
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 4) != 0;
        }

        public FileOptions getOptions() {
            return this.options_ == null ? FileOptions.getDefaultInstance() : this.options_;
        }

        /* access modifiers changed from: private */
        public void setOptions(FileOptions value) {
            value.getClass();
            this.options_ = value;
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void mergeOptions(FileOptions value) {
            value.getClass();
            if (this.options_ == null || this.options_ == FileOptions.getDefaultInstance()) {
                this.options_ = value;
            } else {
                this.options_ = (FileOptions) ((FileOptions.Builder) FileOptions.newBuilder(this.options_).mergeFrom(value)).buildPartial();
            }
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void clearOptions() {
            this.options_ = null;
            this.bitField0_ &= -5;
        }

        public boolean hasSourceCodeInfo() {
            return (this.bitField0_ & 8) != 0;
        }

        public SourceCodeInfo getSourceCodeInfo() {
            return this.sourceCodeInfo_ == null ? SourceCodeInfo.getDefaultInstance() : this.sourceCodeInfo_;
        }

        /* access modifiers changed from: private */
        public void setSourceCodeInfo(SourceCodeInfo value) {
            value.getClass();
            this.sourceCodeInfo_ = value;
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void mergeSourceCodeInfo(SourceCodeInfo value) {
            value.getClass();
            if (this.sourceCodeInfo_ == null || this.sourceCodeInfo_ == SourceCodeInfo.getDefaultInstance()) {
                this.sourceCodeInfo_ = value;
            } else {
                this.sourceCodeInfo_ = (SourceCodeInfo) ((SourceCodeInfo.Builder) SourceCodeInfo.newBuilder(this.sourceCodeInfo_).mergeFrom(value)).buildPartial();
            }
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void clearSourceCodeInfo() {
            this.sourceCodeInfo_ = null;
            this.bitField0_ &= -9;
        }

        public boolean hasSyntax() {
            return (this.bitField0_ & 16) != 0;
        }

        public String getSyntax() {
            return this.syntax_;
        }

        public ByteString getSyntaxBytes() {
            return ByteString.copyFromUtf8(this.syntax_);
        }

        /* access modifiers changed from: private */
        public void setSyntax(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 16;
            this.syntax_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSyntax() {
            this.bitField0_ &= -17;
            this.syntax_ = getDefaultInstance().getSyntax();
        }

        /* access modifiers changed from: private */
        public void setSyntaxBytes(ByteString value) {
            this.syntax_ = value.toStringUtf8();
            this.bitField0_ |= 16;
        }

        public boolean hasEdition() {
            return (this.bitField0_ & 32) != 0;
        }

        public Edition getEdition() {
            Edition result = Edition.forNumber(this.edition_);
            return result == null ? Edition.EDITION_UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setEdition(Edition value) {
            this.edition_ = value.getNumber();
            this.bitField0_ |= 32;
        }

        /* access modifiers changed from: private */
        public void clearEdition() {
            this.bitField0_ &= -33;
            this.edition_ = 0;
        }

        public static FileDescriptorProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (FileDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FileDescriptorProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FileDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FileDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FileDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FileDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FileDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FileDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FileDescriptorProto parseFrom(InputStream input) throws IOException {
            return (FileDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FileDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FileDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (FileDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static FileDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FileDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (FileDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FileDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(FileDescriptorProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<FileDescriptorProto, Builder> implements FileDescriptorProtoOrBuilder {
            private Builder() {
                super(FileDescriptorProto.DEFAULT_INSTANCE);
            }

            public boolean hasName() {
                return ((FileDescriptorProto) this.instance).hasName();
            }

            public String getName() {
                return ((FileDescriptorProto) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((FileDescriptorProto) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setNameBytes(value);
                return this;
            }

            public boolean hasPackage() {
                return ((FileDescriptorProto) this.instance).hasPackage();
            }

            public String getPackage() {
                return ((FileDescriptorProto) this.instance).getPackage();
            }

            public ByteString getPackageBytes() {
                return ((FileDescriptorProto) this.instance).getPackageBytes();
            }

            public Builder setPackage(String value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setPackage(value);
                return this;
            }

            public Builder clearPackage() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearPackage();
                return this;
            }

            public Builder setPackageBytes(ByteString value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setPackageBytes(value);
                return this;
            }

            public List<String> getDependencyList() {
                return Collections.unmodifiableList(((FileDescriptorProto) this.instance).getDependencyList());
            }

            public int getDependencyCount() {
                return ((FileDescriptorProto) this.instance).getDependencyCount();
            }

            public String getDependency(int index) {
                return ((FileDescriptorProto) this.instance).getDependency(index);
            }

            public ByteString getDependencyBytes(int index) {
                return ((FileDescriptorProto) this.instance).getDependencyBytes(index);
            }

            public Builder setDependency(int index, String value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setDependency(index, value);
                return this;
            }

            public Builder addDependency(String value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addDependency(value);
                return this;
            }

            public Builder addAllDependency(Iterable<String> values) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addAllDependency(values);
                return this;
            }

            public Builder clearDependency() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearDependency();
                return this;
            }

            public Builder addDependencyBytes(ByteString value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addDependencyBytes(value);
                return this;
            }

            public List<Integer> getPublicDependencyList() {
                return Collections.unmodifiableList(((FileDescriptorProto) this.instance).getPublicDependencyList());
            }

            public int getPublicDependencyCount() {
                return ((FileDescriptorProto) this.instance).getPublicDependencyCount();
            }

            public int getPublicDependency(int index) {
                return ((FileDescriptorProto) this.instance).getPublicDependency(index);
            }

            public Builder setPublicDependency(int index, int value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setPublicDependency(index, value);
                return this;
            }

            public Builder addPublicDependency(int value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addPublicDependency(value);
                return this;
            }

            public Builder addAllPublicDependency(Iterable<? extends Integer> values) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addAllPublicDependency(values);
                return this;
            }

            public Builder clearPublicDependency() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearPublicDependency();
                return this;
            }

            public List<Integer> getWeakDependencyList() {
                return Collections.unmodifiableList(((FileDescriptorProto) this.instance).getWeakDependencyList());
            }

            public int getWeakDependencyCount() {
                return ((FileDescriptorProto) this.instance).getWeakDependencyCount();
            }

            public int getWeakDependency(int index) {
                return ((FileDescriptorProto) this.instance).getWeakDependency(index);
            }

            public Builder setWeakDependency(int index, int value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setWeakDependency(index, value);
                return this;
            }

            public Builder addWeakDependency(int value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addWeakDependency(value);
                return this;
            }

            public Builder addAllWeakDependency(Iterable<? extends Integer> values) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addAllWeakDependency(values);
                return this;
            }

            public Builder clearWeakDependency() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearWeakDependency();
                return this;
            }

            public List<DescriptorProto> getMessageTypeList() {
                return Collections.unmodifiableList(((FileDescriptorProto) this.instance).getMessageTypeList());
            }

            public int getMessageTypeCount() {
                return ((FileDescriptorProto) this.instance).getMessageTypeCount();
            }

            public DescriptorProto getMessageType(int index) {
                return ((FileDescriptorProto) this.instance).getMessageType(index);
            }

            public Builder setMessageType(int index, DescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setMessageType(index, value);
                return this;
            }

            public Builder setMessageType(int index, DescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setMessageType(index, (DescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addMessageType(DescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addMessageType(value);
                return this;
            }

            public Builder addMessageType(int index, DescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addMessageType(index, value);
                return this;
            }

            public Builder addMessageType(DescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addMessageType((DescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addMessageType(int index, DescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addMessageType(index, (DescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addAllMessageType(Iterable<? extends DescriptorProto> values) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addAllMessageType(values);
                return this;
            }

            public Builder clearMessageType() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearMessageType();
                return this;
            }

            public Builder removeMessageType(int index) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).removeMessageType(index);
                return this;
            }

            public List<EnumDescriptorProto> getEnumTypeList() {
                return Collections.unmodifiableList(((FileDescriptorProto) this.instance).getEnumTypeList());
            }

            public int getEnumTypeCount() {
                return ((FileDescriptorProto) this.instance).getEnumTypeCount();
            }

            public EnumDescriptorProto getEnumType(int index) {
                return ((FileDescriptorProto) this.instance).getEnumType(index);
            }

            public Builder setEnumType(int index, EnumDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setEnumType(index, value);
                return this;
            }

            public Builder setEnumType(int index, EnumDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setEnumType(index, (EnumDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addEnumType(EnumDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addEnumType(value);
                return this;
            }

            public Builder addEnumType(int index, EnumDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addEnumType(index, value);
                return this;
            }

            public Builder addEnumType(EnumDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addEnumType((EnumDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addEnumType(int index, EnumDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addEnumType(index, (EnumDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addAllEnumType(Iterable<? extends EnumDescriptorProto> values) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addAllEnumType(values);
                return this;
            }

            public Builder clearEnumType() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearEnumType();
                return this;
            }

            public Builder removeEnumType(int index) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).removeEnumType(index);
                return this;
            }

            public List<ServiceDescriptorProto> getServiceList() {
                return Collections.unmodifiableList(((FileDescriptorProto) this.instance).getServiceList());
            }

            public int getServiceCount() {
                return ((FileDescriptorProto) this.instance).getServiceCount();
            }

            public ServiceDescriptorProto getService(int index) {
                return ((FileDescriptorProto) this.instance).getService(index);
            }

            public Builder setService(int index, ServiceDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setService(index, value);
                return this;
            }

            public Builder setService(int index, ServiceDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setService(index, (ServiceDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addService(ServiceDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addService(value);
                return this;
            }

            public Builder addService(int index, ServiceDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addService(index, value);
                return this;
            }

            public Builder addService(ServiceDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addService((ServiceDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addService(int index, ServiceDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addService(index, (ServiceDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addAllService(Iterable<? extends ServiceDescriptorProto> values) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addAllService(values);
                return this;
            }

            public Builder clearService() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearService();
                return this;
            }

            public Builder removeService(int index) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).removeService(index);
                return this;
            }

            public List<FieldDescriptorProto> getExtensionList() {
                return Collections.unmodifiableList(((FileDescriptorProto) this.instance).getExtensionList());
            }

            public int getExtensionCount() {
                return ((FileDescriptorProto) this.instance).getExtensionCount();
            }

            public FieldDescriptorProto getExtension(int index) {
                return ((FileDescriptorProto) this.instance).getExtension(index);
            }

            public Builder setExtension(int index, FieldDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setExtension(index, value);
                return this;
            }

            public Builder setExtension(int index, FieldDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setExtension(index, (FieldDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addExtension(FieldDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addExtension(value);
                return this;
            }

            public Builder addExtension(int index, FieldDescriptorProto value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addExtension(index, value);
                return this;
            }

            public Builder addExtension(FieldDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addExtension((FieldDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addExtension(int index, FieldDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addExtension(index, (FieldDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addAllExtension(Iterable<? extends FieldDescriptorProto> values) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).addAllExtension(values);
                return this;
            }

            public Builder clearExtension() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearExtension();
                return this;
            }

            public Builder removeExtension(int index) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).removeExtension(index);
                return this;
            }

            public boolean hasOptions() {
                return ((FileDescriptorProto) this.instance).hasOptions();
            }

            public FileOptions getOptions() {
                return ((FileDescriptorProto) this.instance).getOptions();
            }

            public Builder setOptions(FileOptions value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setOptions(value);
                return this;
            }

            public Builder setOptions(FileOptions.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setOptions((FileOptions) builderForValue.build());
                return this;
            }

            public Builder mergeOptions(FileOptions value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).mergeOptions(value);
                return this;
            }

            public Builder clearOptions() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearOptions();
                return this;
            }

            public boolean hasSourceCodeInfo() {
                return ((FileDescriptorProto) this.instance).hasSourceCodeInfo();
            }

            public SourceCodeInfo getSourceCodeInfo() {
                return ((FileDescriptorProto) this.instance).getSourceCodeInfo();
            }

            public Builder setSourceCodeInfo(SourceCodeInfo value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setSourceCodeInfo(value);
                return this;
            }

            public Builder setSourceCodeInfo(SourceCodeInfo.Builder builderForValue) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setSourceCodeInfo((SourceCodeInfo) builderForValue.build());
                return this;
            }

            public Builder mergeSourceCodeInfo(SourceCodeInfo value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).mergeSourceCodeInfo(value);
                return this;
            }

            public Builder clearSourceCodeInfo() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearSourceCodeInfo();
                return this;
            }

            public boolean hasSyntax() {
                return ((FileDescriptorProto) this.instance).hasSyntax();
            }

            public String getSyntax() {
                return ((FileDescriptorProto) this.instance).getSyntax();
            }

            public ByteString getSyntaxBytes() {
                return ((FileDescriptorProto) this.instance).getSyntaxBytes();
            }

            public Builder setSyntax(String value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setSyntax(value);
                return this;
            }

            public Builder clearSyntax() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearSyntax();
                return this;
            }

            public Builder setSyntaxBytes(ByteString value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setSyntaxBytes(value);
                return this;
            }

            public boolean hasEdition() {
                return ((FileDescriptorProto) this.instance).hasEdition();
            }

            public Edition getEdition() {
                return ((FileDescriptorProto) this.instance).getEdition();
            }

            public Builder setEdition(Edition value) {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).setEdition(value);
                return this;
            }

            public Builder clearEdition() {
                copyOnWrite();
                ((FileDescriptorProto) this.instance).clearEdition();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new FileDescriptorProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\r\u0000\u0001\u0001\u000e\r\u0000\u0007\u0005\u0001ဈ\u0000\u0002ဈ\u0001\u0003\u001a\u0004Л\u0005Л\u0006Л\u0007Л\bᐉ\u0002\tဉ\u0003\n\u0016\u000b\u0016\fဈ\u0004\u000e᠌\u0005", new Object[]{"bitField0_", "name_", "package_", "dependency_", "messageType_", DescriptorProto.class, "enumType_", EnumDescriptorProto.class, "service_", ServiceDescriptorProto.class, "extension_", FieldDescriptorProto.class, "options_", "sourceCodeInfo_", "publicDependency_", "weakDependency_", "syntax_", "edition_", Edition.internalGetVerifier()});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<FileDescriptorProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (FileDescriptorProto.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            FileDescriptorProto defaultInstance = new FileDescriptorProto();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(FileDescriptorProto.class, defaultInstance);
        }

        public static FileDescriptorProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FileDescriptorProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class DescriptorProto extends GeneratedMessageLite<DescriptorProto, Builder> implements DescriptorProtoOrBuilder {
        /* access modifiers changed from: private */
        public static final DescriptorProto DEFAULT_INSTANCE;
        public static final int ENUM_TYPE_FIELD_NUMBER = 4;
        public static final int EXTENSION_FIELD_NUMBER = 6;
        public static final int EXTENSION_RANGE_FIELD_NUMBER = 5;
        public static final int FIELD_FIELD_NUMBER = 2;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int NESTED_TYPE_FIELD_NUMBER = 3;
        public static final int ONEOF_DECL_FIELD_NUMBER = 8;
        public static final int OPTIONS_FIELD_NUMBER = 7;
        private static volatile Parser<DescriptorProto> PARSER = null;
        public static final int RESERVED_NAME_FIELD_NUMBER = 10;
        public static final int RESERVED_RANGE_FIELD_NUMBER = 9;
        private int bitField0_;
        private Internal.ProtobufList<EnumDescriptorProto> enumType_ = emptyProtobufList();
        private Internal.ProtobufList<ExtensionRange> extensionRange_ = emptyProtobufList();
        private Internal.ProtobufList<FieldDescriptorProto> extension_ = emptyProtobufList();
        private Internal.ProtobufList<FieldDescriptorProto> field_ = emptyProtobufList();
        private byte memoizedIsInitialized = 2;
        private String name_ = "";
        private Internal.ProtobufList<DescriptorProto> nestedType_ = emptyProtobufList();
        private Internal.ProtobufList<OneofDescriptorProto> oneofDecl_ = emptyProtobufList();
        private MessageOptions options_;
        private Internal.ProtobufList<String> reservedName_ = GeneratedMessageLite.emptyProtobufList();
        private Internal.ProtobufList<ReservedRange> reservedRange_ = emptyProtobufList();

        public interface ExtensionRangeOrBuilder extends MessageLiteOrBuilder {
            int getEnd();

            ExtensionRangeOptions getOptions();

            int getStart();

            boolean hasEnd();

            boolean hasOptions();

            boolean hasStart();
        }

        public interface ReservedRangeOrBuilder extends MessageLiteOrBuilder {
            int getEnd();

            int getStart();

            boolean hasEnd();

            boolean hasStart();
        }

        private DescriptorProto() {
        }

        public static final class ExtensionRange extends GeneratedMessageLite<ExtensionRange, Builder> implements ExtensionRangeOrBuilder {
            /* access modifiers changed from: private */
            public static final ExtensionRange DEFAULT_INSTANCE;
            public static final int END_FIELD_NUMBER = 2;
            public static final int OPTIONS_FIELD_NUMBER = 3;
            private static volatile Parser<ExtensionRange> PARSER = null;
            public static final int START_FIELD_NUMBER = 1;
            private int bitField0_;
            private int end_;
            private byte memoizedIsInitialized = 2;
            private ExtensionRangeOptions options_;
            private int start_;

            private ExtensionRange() {
            }

            public boolean hasStart() {
                return (this.bitField0_ & 1) != 0;
            }

            public int getStart() {
                return this.start_;
            }

            /* access modifiers changed from: private */
            public void setStart(int value) {
                this.bitField0_ |= 1;
                this.start_ = value;
            }

            /* access modifiers changed from: private */
            public void clearStart() {
                this.bitField0_ &= -2;
                this.start_ = 0;
            }

            public boolean hasEnd() {
                return (this.bitField0_ & 2) != 0;
            }

            public int getEnd() {
                return this.end_;
            }

            /* access modifiers changed from: private */
            public void setEnd(int value) {
                this.bitField0_ |= 2;
                this.end_ = value;
            }

            /* access modifiers changed from: private */
            public void clearEnd() {
                this.bitField0_ &= -3;
                this.end_ = 0;
            }

            public boolean hasOptions() {
                return (this.bitField0_ & 4) != 0;
            }

            public ExtensionRangeOptions getOptions() {
                return this.options_ == null ? ExtensionRangeOptions.getDefaultInstance() : this.options_;
            }

            /* access modifiers changed from: private */
            public void setOptions(ExtensionRangeOptions value) {
                value.getClass();
                this.options_ = value;
                this.bitField0_ |= 4;
            }

            /* access modifiers changed from: private */
            public void mergeOptions(ExtensionRangeOptions value) {
                value.getClass();
                if (this.options_ == null || this.options_ == ExtensionRangeOptions.getDefaultInstance()) {
                    this.options_ = value;
                } else {
                    this.options_ = (ExtensionRangeOptions) ((ExtensionRangeOptions.Builder) ExtensionRangeOptions.newBuilder(this.options_).mergeFrom(value)).buildPartial();
                }
                this.bitField0_ |= 4;
            }

            /* access modifiers changed from: private */
            public void clearOptions() {
                this.options_ = null;
                this.bitField0_ &= -5;
            }

            public static ExtensionRange parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (ExtensionRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static ExtensionRange parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ExtensionRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static ExtensionRange parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (ExtensionRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static ExtensionRange parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ExtensionRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static ExtensionRange parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (ExtensionRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static ExtensionRange parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ExtensionRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static ExtensionRange parseFrom(InputStream input) throws IOException {
                return (ExtensionRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static ExtensionRange parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ExtensionRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static ExtensionRange parseDelimitedFrom(InputStream input) throws IOException {
                return (ExtensionRange) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static ExtensionRange parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ExtensionRange) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static ExtensionRange parseFrom(CodedInputStream input) throws IOException {
                return (ExtensionRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static ExtensionRange parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ExtensionRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(ExtensionRange prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<ExtensionRange, Builder> implements ExtensionRangeOrBuilder {
                private Builder() {
                    super(ExtensionRange.DEFAULT_INSTANCE);
                }

                public boolean hasStart() {
                    return ((ExtensionRange) this.instance).hasStart();
                }

                public int getStart() {
                    return ((ExtensionRange) this.instance).getStart();
                }

                public Builder setStart(int value) {
                    copyOnWrite();
                    ((ExtensionRange) this.instance).setStart(value);
                    return this;
                }

                public Builder clearStart() {
                    copyOnWrite();
                    ((ExtensionRange) this.instance).clearStart();
                    return this;
                }

                public boolean hasEnd() {
                    return ((ExtensionRange) this.instance).hasEnd();
                }

                public int getEnd() {
                    return ((ExtensionRange) this.instance).getEnd();
                }

                public Builder setEnd(int value) {
                    copyOnWrite();
                    ((ExtensionRange) this.instance).setEnd(value);
                    return this;
                }

                public Builder clearEnd() {
                    copyOnWrite();
                    ((ExtensionRange) this.instance).clearEnd();
                    return this;
                }

                public boolean hasOptions() {
                    return ((ExtensionRange) this.instance).hasOptions();
                }

                public ExtensionRangeOptions getOptions() {
                    return ((ExtensionRange) this.instance).getOptions();
                }

                public Builder setOptions(ExtensionRangeOptions value) {
                    copyOnWrite();
                    ((ExtensionRange) this.instance).setOptions(value);
                    return this;
                }

                public Builder setOptions(ExtensionRangeOptions.Builder builderForValue) {
                    copyOnWrite();
                    ((ExtensionRange) this.instance).setOptions((ExtensionRangeOptions) builderForValue.build());
                    return this;
                }

                public Builder mergeOptions(ExtensionRangeOptions value) {
                    copyOnWrite();
                    ((ExtensionRange) this.instance).mergeOptions(value);
                    return this;
                }

                public Builder clearOptions() {
                    copyOnWrite();
                    ((ExtensionRange) this.instance).clearOptions();
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new ExtensionRange();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0001\u0001င\u0000\u0002င\u0001\u0003ᐉ\u0002", new Object[]{"bitField0_", "start_", "end_", "options_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<ExtensionRange> parser = PARSER;
                        if (parser == null) {
                            synchronized (ExtensionRange.class) {
                                parser = PARSER;
                                if (parser == null) {
                                    parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = parser;
                                }
                            }
                        }
                        return parser;
                    case GET_MEMOIZED_IS_INITIALIZED:
                        return Byte.valueOf(this.memoizedIsInitialized);
                    case SET_MEMOIZED_IS_INITIALIZED:
                        this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                        return null;
                    default:
                        throw new UnsupportedOperationException();
                }
            }

            static {
                ExtensionRange defaultInstance = new ExtensionRange();
                DEFAULT_INSTANCE = defaultInstance;
                GeneratedMessageLite.registerDefaultInstance(ExtensionRange.class, defaultInstance);
            }

            public static ExtensionRange getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<ExtensionRange> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public static final class ReservedRange extends GeneratedMessageLite<ReservedRange, Builder> implements ReservedRangeOrBuilder {
            /* access modifiers changed from: private */
            public static final ReservedRange DEFAULT_INSTANCE;
            public static final int END_FIELD_NUMBER = 2;
            private static volatile Parser<ReservedRange> PARSER = null;
            public static final int START_FIELD_NUMBER = 1;
            private int bitField0_;
            private int end_;
            private int start_;

            private ReservedRange() {
            }

            public boolean hasStart() {
                return (this.bitField0_ & 1) != 0;
            }

            public int getStart() {
                return this.start_;
            }

            /* access modifiers changed from: private */
            public void setStart(int value) {
                this.bitField0_ |= 1;
                this.start_ = value;
            }

            /* access modifiers changed from: private */
            public void clearStart() {
                this.bitField0_ &= -2;
                this.start_ = 0;
            }

            public boolean hasEnd() {
                return (this.bitField0_ & 2) != 0;
            }

            public int getEnd() {
                return this.end_;
            }

            /* access modifiers changed from: private */
            public void setEnd(int value) {
                this.bitField0_ |= 2;
                this.end_ = value;
            }

            /* access modifiers changed from: private */
            public void clearEnd() {
                this.bitField0_ &= -3;
                this.end_ = 0;
            }

            public static ReservedRange parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (ReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static ReservedRange parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static ReservedRange parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (ReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static ReservedRange parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static ReservedRange parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (ReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static ReservedRange parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static ReservedRange parseFrom(InputStream input) throws IOException {
                return (ReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static ReservedRange parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static ReservedRange parseDelimitedFrom(InputStream input) throws IOException {
                return (ReservedRange) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static ReservedRange parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ReservedRange) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static ReservedRange parseFrom(CodedInputStream input) throws IOException {
                return (ReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static ReservedRange parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(ReservedRange prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<ReservedRange, Builder> implements ReservedRangeOrBuilder {
                private Builder() {
                    super(ReservedRange.DEFAULT_INSTANCE);
                }

                public boolean hasStart() {
                    return ((ReservedRange) this.instance).hasStart();
                }

                public int getStart() {
                    return ((ReservedRange) this.instance).getStart();
                }

                public Builder setStart(int value) {
                    copyOnWrite();
                    ((ReservedRange) this.instance).setStart(value);
                    return this;
                }

                public Builder clearStart() {
                    copyOnWrite();
                    ((ReservedRange) this.instance).clearStart();
                    return this;
                }

                public boolean hasEnd() {
                    return ((ReservedRange) this.instance).hasEnd();
                }

                public int getEnd() {
                    return ((ReservedRange) this.instance).getEnd();
                }

                public Builder setEnd(int value) {
                    copyOnWrite();
                    ((ReservedRange) this.instance).setEnd(value);
                    return this;
                }

                public Builder clearEnd() {
                    copyOnWrite();
                    ((ReservedRange) this.instance).clearEnd();
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new ReservedRange();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001င\u0000\u0002င\u0001", new Object[]{"bitField0_", "start_", "end_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<ReservedRange> parser = PARSER;
                        if (parser == null) {
                            synchronized (ReservedRange.class) {
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
                ReservedRange defaultInstance = new ReservedRange();
                DEFAULT_INSTANCE = defaultInstance;
                GeneratedMessageLite.registerDefaultInstance(ReservedRange.class, defaultInstance);
            }

            public static ReservedRange getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<ReservedRange> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 1;
            this.name_ = value;
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.bitField0_ &= -2;
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            this.name_ = value.toStringUtf8();
            this.bitField0_ |= 1;
        }

        public List<FieldDescriptorProto> getFieldList() {
            return this.field_;
        }

        public List<? extends FieldDescriptorProtoOrBuilder> getFieldOrBuilderList() {
            return this.field_;
        }

        public int getFieldCount() {
            return this.field_.size();
        }

        public FieldDescriptorProto getField(int index) {
            return (FieldDescriptorProto) this.field_.get(index);
        }

        public FieldDescriptorProtoOrBuilder getFieldOrBuilder(int index) {
            return (FieldDescriptorProtoOrBuilder) this.field_.get(index);
        }

        private void ensureFieldIsMutable() {
            Internal.ProtobufList<FieldDescriptorProto> tmp = this.field_;
            if (!tmp.isModifiable()) {
                this.field_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setField(int index, FieldDescriptorProto value) {
            value.getClass();
            ensureFieldIsMutable();
            this.field_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addField(FieldDescriptorProto value) {
            value.getClass();
            ensureFieldIsMutable();
            this.field_.add(value);
        }

        /* access modifiers changed from: private */
        public void addField(int index, FieldDescriptorProto value) {
            value.getClass();
            ensureFieldIsMutable();
            this.field_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllField(Iterable<? extends FieldDescriptorProto> values) {
            ensureFieldIsMutable();
            AbstractMessageLite.addAll(values, this.field_);
        }

        /* access modifiers changed from: private */
        public void clearField() {
            this.field_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeField(int index) {
            ensureFieldIsMutable();
            this.field_.remove(index);
        }

        public List<FieldDescriptorProto> getExtensionList() {
            return this.extension_;
        }

        public List<? extends FieldDescriptorProtoOrBuilder> getExtensionOrBuilderList() {
            return this.extension_;
        }

        public int getExtensionCount() {
            return this.extension_.size();
        }

        public FieldDescriptorProto getExtension(int index) {
            return (FieldDescriptorProto) this.extension_.get(index);
        }

        public FieldDescriptorProtoOrBuilder getExtensionOrBuilder(int index) {
            return (FieldDescriptorProtoOrBuilder) this.extension_.get(index);
        }

        private void ensureExtensionIsMutable() {
            Internal.ProtobufList<FieldDescriptorProto> tmp = this.extension_;
            if (!tmp.isModifiable()) {
                this.extension_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setExtension(int index, FieldDescriptorProto value) {
            value.getClass();
            ensureExtensionIsMutable();
            this.extension_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addExtension(FieldDescriptorProto value) {
            value.getClass();
            ensureExtensionIsMutable();
            this.extension_.add(value);
        }

        /* access modifiers changed from: private */
        public void addExtension(int index, FieldDescriptorProto value) {
            value.getClass();
            ensureExtensionIsMutable();
            this.extension_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllExtension(Iterable<? extends FieldDescriptorProto> values) {
            ensureExtensionIsMutable();
            AbstractMessageLite.addAll(values, this.extension_);
        }

        /* access modifiers changed from: private */
        public void clearExtension() {
            this.extension_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeExtension(int index) {
            ensureExtensionIsMutable();
            this.extension_.remove(index);
        }

        public List<DescriptorProto> getNestedTypeList() {
            return this.nestedType_;
        }

        public List<? extends DescriptorProtoOrBuilder> getNestedTypeOrBuilderList() {
            return this.nestedType_;
        }

        public int getNestedTypeCount() {
            return this.nestedType_.size();
        }

        public DescriptorProto getNestedType(int index) {
            return (DescriptorProto) this.nestedType_.get(index);
        }

        public DescriptorProtoOrBuilder getNestedTypeOrBuilder(int index) {
            return (DescriptorProtoOrBuilder) this.nestedType_.get(index);
        }

        private void ensureNestedTypeIsMutable() {
            Internal.ProtobufList<DescriptorProto> tmp = this.nestedType_;
            if (!tmp.isModifiable()) {
                this.nestedType_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setNestedType(int index, DescriptorProto value) {
            value.getClass();
            ensureNestedTypeIsMutable();
            this.nestedType_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addNestedType(DescriptorProto value) {
            value.getClass();
            ensureNestedTypeIsMutable();
            this.nestedType_.add(value);
        }

        /* access modifiers changed from: private */
        public void addNestedType(int index, DescriptorProto value) {
            value.getClass();
            ensureNestedTypeIsMutable();
            this.nestedType_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllNestedType(Iterable<? extends DescriptorProto> values) {
            ensureNestedTypeIsMutable();
            AbstractMessageLite.addAll(values, this.nestedType_);
        }

        /* access modifiers changed from: private */
        public void clearNestedType() {
            this.nestedType_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeNestedType(int index) {
            ensureNestedTypeIsMutable();
            this.nestedType_.remove(index);
        }

        public List<EnumDescriptorProto> getEnumTypeList() {
            return this.enumType_;
        }

        public List<? extends EnumDescriptorProtoOrBuilder> getEnumTypeOrBuilderList() {
            return this.enumType_;
        }

        public int getEnumTypeCount() {
            return this.enumType_.size();
        }

        public EnumDescriptorProto getEnumType(int index) {
            return (EnumDescriptorProto) this.enumType_.get(index);
        }

        public EnumDescriptorProtoOrBuilder getEnumTypeOrBuilder(int index) {
            return (EnumDescriptorProtoOrBuilder) this.enumType_.get(index);
        }

        private void ensureEnumTypeIsMutable() {
            Internal.ProtobufList<EnumDescriptorProto> tmp = this.enumType_;
            if (!tmp.isModifiable()) {
                this.enumType_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setEnumType(int index, EnumDescriptorProto value) {
            value.getClass();
            ensureEnumTypeIsMutable();
            this.enumType_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addEnumType(EnumDescriptorProto value) {
            value.getClass();
            ensureEnumTypeIsMutable();
            this.enumType_.add(value);
        }

        /* access modifiers changed from: private */
        public void addEnumType(int index, EnumDescriptorProto value) {
            value.getClass();
            ensureEnumTypeIsMutable();
            this.enumType_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllEnumType(Iterable<? extends EnumDescriptorProto> values) {
            ensureEnumTypeIsMutable();
            AbstractMessageLite.addAll(values, this.enumType_);
        }

        /* access modifiers changed from: private */
        public void clearEnumType() {
            this.enumType_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeEnumType(int index) {
            ensureEnumTypeIsMutable();
            this.enumType_.remove(index);
        }

        public List<ExtensionRange> getExtensionRangeList() {
            return this.extensionRange_;
        }

        public List<? extends ExtensionRangeOrBuilder> getExtensionRangeOrBuilderList() {
            return this.extensionRange_;
        }

        public int getExtensionRangeCount() {
            return this.extensionRange_.size();
        }

        public ExtensionRange getExtensionRange(int index) {
            return (ExtensionRange) this.extensionRange_.get(index);
        }

        public ExtensionRangeOrBuilder getExtensionRangeOrBuilder(int index) {
            return (ExtensionRangeOrBuilder) this.extensionRange_.get(index);
        }

        private void ensureExtensionRangeIsMutable() {
            Internal.ProtobufList<ExtensionRange> tmp = this.extensionRange_;
            if (!tmp.isModifiable()) {
                this.extensionRange_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setExtensionRange(int index, ExtensionRange value) {
            value.getClass();
            ensureExtensionRangeIsMutable();
            this.extensionRange_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addExtensionRange(ExtensionRange value) {
            value.getClass();
            ensureExtensionRangeIsMutable();
            this.extensionRange_.add(value);
        }

        /* access modifiers changed from: private */
        public void addExtensionRange(int index, ExtensionRange value) {
            value.getClass();
            ensureExtensionRangeIsMutable();
            this.extensionRange_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllExtensionRange(Iterable<? extends ExtensionRange> values) {
            ensureExtensionRangeIsMutable();
            AbstractMessageLite.addAll(values, this.extensionRange_);
        }

        /* access modifiers changed from: private */
        public void clearExtensionRange() {
            this.extensionRange_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeExtensionRange(int index) {
            ensureExtensionRangeIsMutable();
            this.extensionRange_.remove(index);
        }

        public List<OneofDescriptorProto> getOneofDeclList() {
            return this.oneofDecl_;
        }

        public List<? extends OneofDescriptorProtoOrBuilder> getOneofDeclOrBuilderList() {
            return this.oneofDecl_;
        }

        public int getOneofDeclCount() {
            return this.oneofDecl_.size();
        }

        public OneofDescriptorProto getOneofDecl(int index) {
            return (OneofDescriptorProto) this.oneofDecl_.get(index);
        }

        public OneofDescriptorProtoOrBuilder getOneofDeclOrBuilder(int index) {
            return (OneofDescriptorProtoOrBuilder) this.oneofDecl_.get(index);
        }

        private void ensureOneofDeclIsMutable() {
            Internal.ProtobufList<OneofDescriptorProto> tmp = this.oneofDecl_;
            if (!tmp.isModifiable()) {
                this.oneofDecl_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setOneofDecl(int index, OneofDescriptorProto value) {
            value.getClass();
            ensureOneofDeclIsMutable();
            this.oneofDecl_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addOneofDecl(OneofDescriptorProto value) {
            value.getClass();
            ensureOneofDeclIsMutable();
            this.oneofDecl_.add(value);
        }

        /* access modifiers changed from: private */
        public void addOneofDecl(int index, OneofDescriptorProto value) {
            value.getClass();
            ensureOneofDeclIsMutable();
            this.oneofDecl_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllOneofDecl(Iterable<? extends OneofDescriptorProto> values) {
            ensureOneofDeclIsMutable();
            AbstractMessageLite.addAll(values, this.oneofDecl_);
        }

        /* access modifiers changed from: private */
        public void clearOneofDecl() {
            this.oneofDecl_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeOneofDecl(int index) {
            ensureOneofDeclIsMutable();
            this.oneofDecl_.remove(index);
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 2) != 0;
        }

        public MessageOptions getOptions() {
            return this.options_ == null ? MessageOptions.getDefaultInstance() : this.options_;
        }

        /* access modifiers changed from: private */
        public void setOptions(MessageOptions value) {
            value.getClass();
            this.options_ = value;
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeOptions(MessageOptions value) {
            value.getClass();
            if (this.options_ == null || this.options_ == MessageOptions.getDefaultInstance()) {
                this.options_ = value;
            } else {
                this.options_ = (MessageOptions) ((MessageOptions.Builder) MessageOptions.newBuilder(this.options_).mergeFrom(value)).buildPartial();
            }
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void clearOptions() {
            this.options_ = null;
            this.bitField0_ &= -3;
        }

        public List<ReservedRange> getReservedRangeList() {
            return this.reservedRange_;
        }

        public List<? extends ReservedRangeOrBuilder> getReservedRangeOrBuilderList() {
            return this.reservedRange_;
        }

        public int getReservedRangeCount() {
            return this.reservedRange_.size();
        }

        public ReservedRange getReservedRange(int index) {
            return (ReservedRange) this.reservedRange_.get(index);
        }

        public ReservedRangeOrBuilder getReservedRangeOrBuilder(int index) {
            return (ReservedRangeOrBuilder) this.reservedRange_.get(index);
        }

        private void ensureReservedRangeIsMutable() {
            Internal.ProtobufList<ReservedRange> tmp = this.reservedRange_;
            if (!tmp.isModifiable()) {
                this.reservedRange_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setReservedRange(int index, ReservedRange value) {
            value.getClass();
            ensureReservedRangeIsMutable();
            this.reservedRange_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addReservedRange(ReservedRange value) {
            value.getClass();
            ensureReservedRangeIsMutable();
            this.reservedRange_.add(value);
        }

        /* access modifiers changed from: private */
        public void addReservedRange(int index, ReservedRange value) {
            value.getClass();
            ensureReservedRangeIsMutable();
            this.reservedRange_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllReservedRange(Iterable<? extends ReservedRange> values) {
            ensureReservedRangeIsMutable();
            AbstractMessageLite.addAll(values, this.reservedRange_);
        }

        /* access modifiers changed from: private */
        public void clearReservedRange() {
            this.reservedRange_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeReservedRange(int index) {
            ensureReservedRangeIsMutable();
            this.reservedRange_.remove(index);
        }

        public List<String> getReservedNameList() {
            return this.reservedName_;
        }

        public int getReservedNameCount() {
            return this.reservedName_.size();
        }

        public String getReservedName(int index) {
            return (String) this.reservedName_.get(index);
        }

        public ByteString getReservedNameBytes(int index) {
            return ByteString.copyFromUtf8((String) this.reservedName_.get(index));
        }

        private void ensureReservedNameIsMutable() {
            Internal.ProtobufList<String> tmp = this.reservedName_;
            if (!tmp.isModifiable()) {
                this.reservedName_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setReservedName(int index, String value) {
            Class<?> cls = value.getClass();
            ensureReservedNameIsMutable();
            this.reservedName_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addReservedName(String value) {
            Class<?> cls = value.getClass();
            ensureReservedNameIsMutable();
            this.reservedName_.add(value);
        }

        /* access modifiers changed from: private */
        public void addAllReservedName(Iterable<String> values) {
            ensureReservedNameIsMutable();
            AbstractMessageLite.addAll(values, this.reservedName_);
        }

        /* access modifiers changed from: private */
        public void clearReservedName() {
            this.reservedName_ = GeneratedMessageLite.emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void addReservedNameBytes(ByteString value) {
            ensureReservedNameIsMutable();
            this.reservedName_.add(value.toStringUtf8());
        }

        public static DescriptorProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (DescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DescriptorProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DescriptorProto parseFrom(InputStream input) throws IOException {
            return (DescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (DescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static DescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (DescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(DescriptorProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<DescriptorProto, Builder> implements DescriptorProtoOrBuilder {
            private Builder() {
                super(DescriptorProto.DEFAULT_INSTANCE);
            }

            public boolean hasName() {
                return ((DescriptorProto) this.instance).hasName();
            }

            public String getName() {
                return ((DescriptorProto) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((DescriptorProto) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((DescriptorProto) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setNameBytes(value);
                return this;
            }

            public List<FieldDescriptorProto> getFieldList() {
                return Collections.unmodifiableList(((DescriptorProto) this.instance).getFieldList());
            }

            public int getFieldCount() {
                return ((DescriptorProto) this.instance).getFieldCount();
            }

            public FieldDescriptorProto getField(int index) {
                return ((DescriptorProto) this.instance).getField(index);
            }

            public Builder setField(int index, FieldDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setField(index, value);
                return this;
            }

            public Builder setField(int index, FieldDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setField(index, (FieldDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addField(FieldDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addField(value);
                return this;
            }

            public Builder addField(int index, FieldDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addField(index, value);
                return this;
            }

            public Builder addField(FieldDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addField((FieldDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addField(int index, FieldDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addField(index, (FieldDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addAllField(Iterable<? extends FieldDescriptorProto> values) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addAllField(values);
                return this;
            }

            public Builder clearField() {
                copyOnWrite();
                ((DescriptorProto) this.instance).clearField();
                return this;
            }

            public Builder removeField(int index) {
                copyOnWrite();
                ((DescriptorProto) this.instance).removeField(index);
                return this;
            }

            public List<FieldDescriptorProto> getExtensionList() {
                return Collections.unmodifiableList(((DescriptorProto) this.instance).getExtensionList());
            }

            public int getExtensionCount() {
                return ((DescriptorProto) this.instance).getExtensionCount();
            }

            public FieldDescriptorProto getExtension(int index) {
                return ((DescriptorProto) this.instance).getExtension(index);
            }

            public Builder setExtension(int index, FieldDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setExtension(index, value);
                return this;
            }

            public Builder setExtension(int index, FieldDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setExtension(index, (FieldDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addExtension(FieldDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addExtension(value);
                return this;
            }

            public Builder addExtension(int index, FieldDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addExtension(index, value);
                return this;
            }

            public Builder addExtension(FieldDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addExtension((FieldDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addExtension(int index, FieldDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addExtension(index, (FieldDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addAllExtension(Iterable<? extends FieldDescriptorProto> values) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addAllExtension(values);
                return this;
            }

            public Builder clearExtension() {
                copyOnWrite();
                ((DescriptorProto) this.instance).clearExtension();
                return this;
            }

            public Builder removeExtension(int index) {
                copyOnWrite();
                ((DescriptorProto) this.instance).removeExtension(index);
                return this;
            }

            public List<DescriptorProto> getNestedTypeList() {
                return Collections.unmodifiableList(((DescriptorProto) this.instance).getNestedTypeList());
            }

            public int getNestedTypeCount() {
                return ((DescriptorProto) this.instance).getNestedTypeCount();
            }

            public DescriptorProto getNestedType(int index) {
                return ((DescriptorProto) this.instance).getNestedType(index);
            }

            public Builder setNestedType(int index, DescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setNestedType(index, value);
                return this;
            }

            public Builder setNestedType(int index, Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setNestedType(index, (DescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addNestedType(DescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addNestedType(value);
                return this;
            }

            public Builder addNestedType(int index, DescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addNestedType(index, value);
                return this;
            }

            public Builder addNestedType(Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addNestedType((DescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addNestedType(int index, Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addNestedType(index, (DescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addAllNestedType(Iterable<? extends DescriptorProto> values) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addAllNestedType(values);
                return this;
            }

            public Builder clearNestedType() {
                copyOnWrite();
                ((DescriptorProto) this.instance).clearNestedType();
                return this;
            }

            public Builder removeNestedType(int index) {
                copyOnWrite();
                ((DescriptorProto) this.instance).removeNestedType(index);
                return this;
            }

            public List<EnumDescriptorProto> getEnumTypeList() {
                return Collections.unmodifiableList(((DescriptorProto) this.instance).getEnumTypeList());
            }

            public int getEnumTypeCount() {
                return ((DescriptorProto) this.instance).getEnumTypeCount();
            }

            public EnumDescriptorProto getEnumType(int index) {
                return ((DescriptorProto) this.instance).getEnumType(index);
            }

            public Builder setEnumType(int index, EnumDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setEnumType(index, value);
                return this;
            }

            public Builder setEnumType(int index, EnumDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setEnumType(index, (EnumDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addEnumType(EnumDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addEnumType(value);
                return this;
            }

            public Builder addEnumType(int index, EnumDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addEnumType(index, value);
                return this;
            }

            public Builder addEnumType(EnumDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addEnumType((EnumDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addEnumType(int index, EnumDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addEnumType(index, (EnumDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addAllEnumType(Iterable<? extends EnumDescriptorProto> values) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addAllEnumType(values);
                return this;
            }

            public Builder clearEnumType() {
                copyOnWrite();
                ((DescriptorProto) this.instance).clearEnumType();
                return this;
            }

            public Builder removeEnumType(int index) {
                copyOnWrite();
                ((DescriptorProto) this.instance).removeEnumType(index);
                return this;
            }

            public List<ExtensionRange> getExtensionRangeList() {
                return Collections.unmodifiableList(((DescriptorProto) this.instance).getExtensionRangeList());
            }

            public int getExtensionRangeCount() {
                return ((DescriptorProto) this.instance).getExtensionRangeCount();
            }

            public ExtensionRange getExtensionRange(int index) {
                return ((DescriptorProto) this.instance).getExtensionRange(index);
            }

            public Builder setExtensionRange(int index, ExtensionRange value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setExtensionRange(index, value);
                return this;
            }

            public Builder setExtensionRange(int index, ExtensionRange.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setExtensionRange(index, (ExtensionRange) builderForValue.build());
                return this;
            }

            public Builder addExtensionRange(ExtensionRange value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addExtensionRange(value);
                return this;
            }

            public Builder addExtensionRange(int index, ExtensionRange value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addExtensionRange(index, value);
                return this;
            }

            public Builder addExtensionRange(ExtensionRange.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addExtensionRange((ExtensionRange) builderForValue.build());
                return this;
            }

            public Builder addExtensionRange(int index, ExtensionRange.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addExtensionRange(index, (ExtensionRange) builderForValue.build());
                return this;
            }

            public Builder addAllExtensionRange(Iterable<? extends ExtensionRange> values) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addAllExtensionRange(values);
                return this;
            }

            public Builder clearExtensionRange() {
                copyOnWrite();
                ((DescriptorProto) this.instance).clearExtensionRange();
                return this;
            }

            public Builder removeExtensionRange(int index) {
                copyOnWrite();
                ((DescriptorProto) this.instance).removeExtensionRange(index);
                return this;
            }

            public List<OneofDescriptorProto> getOneofDeclList() {
                return Collections.unmodifiableList(((DescriptorProto) this.instance).getOneofDeclList());
            }

            public int getOneofDeclCount() {
                return ((DescriptorProto) this.instance).getOneofDeclCount();
            }

            public OneofDescriptorProto getOneofDecl(int index) {
                return ((DescriptorProto) this.instance).getOneofDecl(index);
            }

            public Builder setOneofDecl(int index, OneofDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setOneofDecl(index, value);
                return this;
            }

            public Builder setOneofDecl(int index, OneofDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setOneofDecl(index, (OneofDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addOneofDecl(OneofDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addOneofDecl(value);
                return this;
            }

            public Builder addOneofDecl(int index, OneofDescriptorProto value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addOneofDecl(index, value);
                return this;
            }

            public Builder addOneofDecl(OneofDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addOneofDecl((OneofDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addOneofDecl(int index, OneofDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addOneofDecl(index, (OneofDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addAllOneofDecl(Iterable<? extends OneofDescriptorProto> values) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addAllOneofDecl(values);
                return this;
            }

            public Builder clearOneofDecl() {
                copyOnWrite();
                ((DescriptorProto) this.instance).clearOneofDecl();
                return this;
            }

            public Builder removeOneofDecl(int index) {
                copyOnWrite();
                ((DescriptorProto) this.instance).removeOneofDecl(index);
                return this;
            }

            public boolean hasOptions() {
                return ((DescriptorProto) this.instance).hasOptions();
            }

            public MessageOptions getOptions() {
                return ((DescriptorProto) this.instance).getOptions();
            }

            public Builder setOptions(MessageOptions value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setOptions(value);
                return this;
            }

            public Builder setOptions(MessageOptions.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setOptions((MessageOptions) builderForValue.build());
                return this;
            }

            public Builder mergeOptions(MessageOptions value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).mergeOptions(value);
                return this;
            }

            public Builder clearOptions() {
                copyOnWrite();
                ((DescriptorProto) this.instance).clearOptions();
                return this;
            }

            public List<ReservedRange> getReservedRangeList() {
                return Collections.unmodifiableList(((DescriptorProto) this.instance).getReservedRangeList());
            }

            public int getReservedRangeCount() {
                return ((DescriptorProto) this.instance).getReservedRangeCount();
            }

            public ReservedRange getReservedRange(int index) {
                return ((DescriptorProto) this.instance).getReservedRange(index);
            }

            public Builder setReservedRange(int index, ReservedRange value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setReservedRange(index, value);
                return this;
            }

            public Builder setReservedRange(int index, ReservedRange.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setReservedRange(index, (ReservedRange) builderForValue.build());
                return this;
            }

            public Builder addReservedRange(ReservedRange value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addReservedRange(value);
                return this;
            }

            public Builder addReservedRange(int index, ReservedRange value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addReservedRange(index, value);
                return this;
            }

            public Builder addReservedRange(ReservedRange.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addReservedRange((ReservedRange) builderForValue.build());
                return this;
            }

            public Builder addReservedRange(int index, ReservedRange.Builder builderForValue) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addReservedRange(index, (ReservedRange) builderForValue.build());
                return this;
            }

            public Builder addAllReservedRange(Iterable<? extends ReservedRange> values) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addAllReservedRange(values);
                return this;
            }

            public Builder clearReservedRange() {
                copyOnWrite();
                ((DescriptorProto) this.instance).clearReservedRange();
                return this;
            }

            public Builder removeReservedRange(int index) {
                copyOnWrite();
                ((DescriptorProto) this.instance).removeReservedRange(index);
                return this;
            }

            public List<String> getReservedNameList() {
                return Collections.unmodifiableList(((DescriptorProto) this.instance).getReservedNameList());
            }

            public int getReservedNameCount() {
                return ((DescriptorProto) this.instance).getReservedNameCount();
            }

            public String getReservedName(int index) {
                return ((DescriptorProto) this.instance).getReservedName(index);
            }

            public ByteString getReservedNameBytes(int index) {
                return ((DescriptorProto) this.instance).getReservedNameBytes(index);
            }

            public Builder setReservedName(int index, String value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).setReservedName(index, value);
                return this;
            }

            public Builder addReservedName(String value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addReservedName(value);
                return this;
            }

            public Builder addAllReservedName(Iterable<String> values) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addAllReservedName(values);
                return this;
            }

            public Builder clearReservedName() {
                copyOnWrite();
                ((DescriptorProto) this.instance).clearReservedName();
                return this;
            }

            public Builder addReservedNameBytes(ByteString value) {
                copyOnWrite();
                ((DescriptorProto) this.instance).addReservedNameBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new DescriptorProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\n\u0000\u0001\u0001\n\n\u0000\b\u0007\u0001ဈ\u0000\u0002Л\u0003Л\u0004Л\u0005Л\u0006Л\u0007ᐉ\u0001\bЛ\t\u001b\n\u001a", new Object[]{"bitField0_", "name_", "field_", FieldDescriptorProto.class, "nestedType_", DescriptorProto.class, "enumType_", EnumDescriptorProto.class, "extensionRange_", ExtensionRange.class, "extension_", FieldDescriptorProto.class, "options_", "oneofDecl_", OneofDescriptorProto.class, "reservedRange_", ReservedRange.class, "reservedName_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<DescriptorProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (DescriptorProto.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            DescriptorProto defaultInstance = new DescriptorProto();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(DescriptorProto.class, defaultInstance);
        }

        public static DescriptorProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DescriptorProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class ExtensionRangeOptions extends GeneratedMessageLite.ExtendableMessage<ExtensionRangeOptions, Builder> implements ExtensionRangeOptionsOrBuilder {
        public static final int DECLARATION_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final ExtensionRangeOptions DEFAULT_INSTANCE;
        public static final int FEATURES_FIELD_NUMBER = 50;
        private static volatile Parser<ExtensionRangeOptions> PARSER = null;
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        public static final int VERIFICATION_FIELD_NUMBER = 3;
        private int bitField0_;
        private Internal.ProtobufList<Declaration> declaration_ = emptyProtobufList();
        private FeatureSet features_;
        private byte memoizedIsInitialized = 2;
        private Internal.ProtobufList<UninterpretedOption> uninterpretedOption_ = emptyProtobufList();
        private int verification_ = 1;

        public interface DeclarationOrBuilder extends MessageLiteOrBuilder {
            String getFullName();

            ByteString getFullNameBytes();

            int getNumber();

            boolean getRepeated();

            boolean getReserved();

            String getType();

            ByteString getTypeBytes();

            boolean hasFullName();

            boolean hasNumber();

            boolean hasRepeated();

            boolean hasReserved();

            boolean hasType();
        }

        private ExtensionRangeOptions() {
        }

        public enum VerificationState implements Internal.EnumLite {
            DECLARATION(0),
            UNVERIFIED(1);
            
            public static final int DECLARATION_VALUE = 0;
            public static final int UNVERIFIED_VALUE = 1;
            private static final Internal.EnumLiteMap<VerificationState> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new Internal.EnumLiteMap<VerificationState>() {
                    public VerificationState findValueByNumber(int number) {
                        return VerificationState.forNumber(number);
                    }
                };
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static VerificationState valueOf(int value2) {
                return forNumber(value2);
            }

            public static VerificationState forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return DECLARATION;
                    case 1:
                        return UNVERIFIED;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<VerificationState> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return VerificationStateVerifier.INSTANCE;
            }

            private static final class VerificationStateVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = null;

                private VerificationStateVerifier() {
                }

                static {
                    INSTANCE = new VerificationStateVerifier();
                }

                public boolean isInRange(int number) {
                    return VerificationState.forNumber(number) != null;
                }
            }

            private VerificationState(int value2) {
                this.value = value2;
            }
        }

        public static final class Declaration extends GeneratedMessageLite<Declaration, Builder> implements DeclarationOrBuilder {
            /* access modifiers changed from: private */
            public static final Declaration DEFAULT_INSTANCE;
            public static final int FULL_NAME_FIELD_NUMBER = 2;
            public static final int NUMBER_FIELD_NUMBER = 1;
            private static volatile Parser<Declaration> PARSER = null;
            public static final int REPEATED_FIELD_NUMBER = 6;
            public static final int RESERVED_FIELD_NUMBER = 5;
            public static final int TYPE_FIELD_NUMBER = 3;
            private int bitField0_;
            private String fullName_ = "";
            private int number_;
            private boolean repeated_;
            private boolean reserved_;
            private String type_ = "";

            private Declaration() {
            }

            public boolean hasNumber() {
                return (this.bitField0_ & 1) != 0;
            }

            public int getNumber() {
                return this.number_;
            }

            /* access modifiers changed from: private */
            public void setNumber(int value) {
                this.bitField0_ |= 1;
                this.number_ = value;
            }

            /* access modifiers changed from: private */
            public void clearNumber() {
                this.bitField0_ &= -2;
                this.number_ = 0;
            }

            public boolean hasFullName() {
                return (this.bitField0_ & 2) != 0;
            }

            public String getFullName() {
                return this.fullName_;
            }

            public ByteString getFullNameBytes() {
                return ByteString.copyFromUtf8(this.fullName_);
            }

            /* access modifiers changed from: private */
            public void setFullName(String value) {
                Class<?> cls = value.getClass();
                this.bitField0_ |= 2;
                this.fullName_ = value;
            }

            /* access modifiers changed from: private */
            public void clearFullName() {
                this.bitField0_ &= -3;
                this.fullName_ = getDefaultInstance().getFullName();
            }

            /* access modifiers changed from: private */
            public void setFullNameBytes(ByteString value) {
                this.fullName_ = value.toStringUtf8();
                this.bitField0_ |= 2;
            }

            public boolean hasType() {
                return (this.bitField0_ & 4) != 0;
            }

            public String getType() {
                return this.type_;
            }

            public ByteString getTypeBytes() {
                return ByteString.copyFromUtf8(this.type_);
            }

            /* access modifiers changed from: private */
            public void setType(String value) {
                Class<?> cls = value.getClass();
                this.bitField0_ |= 4;
                this.type_ = value;
            }

            /* access modifiers changed from: private */
            public void clearType() {
                this.bitField0_ &= -5;
                this.type_ = getDefaultInstance().getType();
            }

            /* access modifiers changed from: private */
            public void setTypeBytes(ByteString value) {
                this.type_ = value.toStringUtf8();
                this.bitField0_ |= 4;
            }

            public boolean hasReserved() {
                return (this.bitField0_ & 8) != 0;
            }

            public boolean getReserved() {
                return this.reserved_;
            }

            /* access modifiers changed from: private */
            public void setReserved(boolean value) {
                this.bitField0_ |= 8;
                this.reserved_ = value;
            }

            /* access modifiers changed from: private */
            public void clearReserved() {
                this.bitField0_ &= -9;
                this.reserved_ = false;
            }

            public boolean hasRepeated() {
                return (this.bitField0_ & 16) != 0;
            }

            public boolean getRepeated() {
                return this.repeated_;
            }

            /* access modifiers changed from: private */
            public void setRepeated(boolean value) {
                this.bitField0_ |= 16;
                this.repeated_ = value;
            }

            /* access modifiers changed from: private */
            public void clearRepeated() {
                this.bitField0_ &= -17;
                this.repeated_ = false;
            }

            public static Declaration parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (Declaration) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Declaration parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Declaration) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Declaration parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (Declaration) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Declaration parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Declaration) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Declaration parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (Declaration) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Declaration parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Declaration) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Declaration parseFrom(InputStream input) throws IOException {
                return (Declaration) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static Declaration parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Declaration) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Declaration parseDelimitedFrom(InputStream input) throws IOException {
                return (Declaration) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static Declaration parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Declaration) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Declaration parseFrom(CodedInputStream input) throws IOException {
                return (Declaration) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static Declaration parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Declaration) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(Declaration prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<Declaration, Builder> implements DeclarationOrBuilder {
                private Builder() {
                    super(Declaration.DEFAULT_INSTANCE);
                }

                public boolean hasNumber() {
                    return ((Declaration) this.instance).hasNumber();
                }

                public int getNumber() {
                    return ((Declaration) this.instance).getNumber();
                }

                public Builder setNumber(int value) {
                    copyOnWrite();
                    ((Declaration) this.instance).setNumber(value);
                    return this;
                }

                public Builder clearNumber() {
                    copyOnWrite();
                    ((Declaration) this.instance).clearNumber();
                    return this;
                }

                public boolean hasFullName() {
                    return ((Declaration) this.instance).hasFullName();
                }

                public String getFullName() {
                    return ((Declaration) this.instance).getFullName();
                }

                public ByteString getFullNameBytes() {
                    return ((Declaration) this.instance).getFullNameBytes();
                }

                public Builder setFullName(String value) {
                    copyOnWrite();
                    ((Declaration) this.instance).setFullName(value);
                    return this;
                }

                public Builder clearFullName() {
                    copyOnWrite();
                    ((Declaration) this.instance).clearFullName();
                    return this;
                }

                public Builder setFullNameBytes(ByteString value) {
                    copyOnWrite();
                    ((Declaration) this.instance).setFullNameBytes(value);
                    return this;
                }

                public boolean hasType() {
                    return ((Declaration) this.instance).hasType();
                }

                public String getType() {
                    return ((Declaration) this.instance).getType();
                }

                public ByteString getTypeBytes() {
                    return ((Declaration) this.instance).getTypeBytes();
                }

                public Builder setType(String value) {
                    copyOnWrite();
                    ((Declaration) this.instance).setType(value);
                    return this;
                }

                public Builder clearType() {
                    copyOnWrite();
                    ((Declaration) this.instance).clearType();
                    return this;
                }

                public Builder setTypeBytes(ByteString value) {
                    copyOnWrite();
                    ((Declaration) this.instance).setTypeBytes(value);
                    return this;
                }

                public boolean hasReserved() {
                    return ((Declaration) this.instance).hasReserved();
                }

                public boolean getReserved() {
                    return ((Declaration) this.instance).getReserved();
                }

                public Builder setReserved(boolean value) {
                    copyOnWrite();
                    ((Declaration) this.instance).setReserved(value);
                    return this;
                }

                public Builder clearReserved() {
                    copyOnWrite();
                    ((Declaration) this.instance).clearReserved();
                    return this;
                }

                public boolean hasRepeated() {
                    return ((Declaration) this.instance).hasRepeated();
                }

                public boolean getRepeated() {
                    return ((Declaration) this.instance).getRepeated();
                }

                public Builder setRepeated(boolean value) {
                    copyOnWrite();
                    ((Declaration) this.instance).setRepeated(value);
                    return this;
                }

                public Builder clearRepeated() {
                    copyOnWrite();
                    ((Declaration) this.instance).clearRepeated();
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new Declaration();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0005\u0000\u0001\u0001\u0006\u0005\u0000\u0000\u0000\u0001င\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0005ဇ\u0003\u0006ဇ\u0004", new Object[]{"bitField0_", "number_", "fullName_", "type_", "reserved_", "repeated_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<Declaration> parser = PARSER;
                        if (parser == null) {
                            synchronized (Declaration.class) {
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
                Declaration defaultInstance = new Declaration();
                DEFAULT_INSTANCE = defaultInstance;
                GeneratedMessageLite.registerDefaultInstance(Declaration.class, defaultInstance);
            }

            public static Declaration getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<Declaration> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return (UninterpretedOption) this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
        }

        private void ensureUninterpretedOptionIsMutable() {
            Internal.ProtobufList<UninterpretedOption> tmp = this.uninterpretedOption_;
            if (!tmp.isModifiable()) {
                this.uninterpretedOption_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(value);
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
            ensureUninterpretedOptionIsMutable();
            AbstractMessageLite.addAll(values, this.uninterpretedOption_);
        }

        /* access modifiers changed from: private */
        public void clearUninterpretedOption() {
            this.uninterpretedOption_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeUninterpretedOption(int index) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.remove(index);
        }

        public List<Declaration> getDeclarationList() {
            return this.declaration_;
        }

        public List<? extends DeclarationOrBuilder> getDeclarationOrBuilderList() {
            return this.declaration_;
        }

        public int getDeclarationCount() {
            return this.declaration_.size();
        }

        public Declaration getDeclaration(int index) {
            return (Declaration) this.declaration_.get(index);
        }

        public DeclarationOrBuilder getDeclarationOrBuilder(int index) {
            return (DeclarationOrBuilder) this.declaration_.get(index);
        }

        private void ensureDeclarationIsMutable() {
            Internal.ProtobufList<Declaration> tmp = this.declaration_;
            if (!tmp.isModifiable()) {
                this.declaration_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setDeclaration(int index, Declaration value) {
            value.getClass();
            ensureDeclarationIsMutable();
            this.declaration_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addDeclaration(Declaration value) {
            value.getClass();
            ensureDeclarationIsMutable();
            this.declaration_.add(value);
        }

        /* access modifiers changed from: private */
        public void addDeclaration(int index, Declaration value) {
            value.getClass();
            ensureDeclarationIsMutable();
            this.declaration_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllDeclaration(Iterable<? extends Declaration> values) {
            ensureDeclarationIsMutable();
            AbstractMessageLite.addAll(values, this.declaration_);
        }

        /* access modifiers changed from: private */
        public void clearDeclaration() {
            this.declaration_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeDeclaration(int index) {
            ensureDeclarationIsMutable();
            this.declaration_.remove(index);
        }

        public boolean hasFeatures() {
            return (this.bitField0_ & 1) != 0;
        }

        public FeatureSet getFeatures() {
            return this.features_ == null ? FeatureSet.getDefaultInstance() : this.features_;
        }

        /* access modifiers changed from: private */
        public void setFeatures(FeatureSet value) {
            value.getClass();
            this.features_ = value;
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeFeatures(FeatureSet value) {
            value.getClass();
            if (this.features_ == null || this.features_ == FeatureSet.getDefaultInstance()) {
                this.features_ = value;
            } else {
                this.features_ = (FeatureSet) ((FeatureSet.Builder) FeatureSet.newBuilder(this.features_).mergeFrom(value)).buildPartial();
            }
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void clearFeatures() {
            this.features_ = null;
            this.bitField0_ &= -2;
        }

        public boolean hasVerification() {
            return (this.bitField0_ & 2) != 0;
        }

        public VerificationState getVerification() {
            VerificationState result = VerificationState.forNumber(this.verification_);
            return result == null ? VerificationState.UNVERIFIED : result;
        }

        /* access modifiers changed from: private */
        public void setVerification(VerificationState value) {
            this.verification_ = value.getNumber();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void clearVerification() {
            this.bitField0_ &= -3;
            this.verification_ = 1;
        }

        public static ExtensionRangeOptions parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ExtensionRangeOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExtensionRangeOptions parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExtensionRangeOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExtensionRangeOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ExtensionRangeOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExtensionRangeOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExtensionRangeOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExtensionRangeOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ExtensionRangeOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ExtensionRangeOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ExtensionRangeOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ExtensionRangeOptions parseFrom(InputStream input) throws IOException {
            return (ExtensionRangeOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ExtensionRangeOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExtensionRangeOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ExtensionRangeOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (ExtensionRangeOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ExtensionRangeOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExtensionRangeOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ExtensionRangeOptions parseFrom(CodedInputStream input) throws IOException {
            return (ExtensionRangeOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ExtensionRangeOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ExtensionRangeOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ExtensionRangeOptions prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<ExtensionRangeOptions, Builder> implements ExtensionRangeOptionsOrBuilder {
            private Builder() {
                super(ExtensionRangeOptions.DEFAULT_INSTANCE);
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                return Collections.unmodifiableList(((ExtensionRangeOptions) this.instance).getUninterpretedOptionList());
            }

            public int getUninterpretedOptionCount() {
                return ((ExtensionRangeOptions) this.instance).getUninterpretedOptionCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                return ((ExtensionRangeOptions) this.instance).getUninterpretedOption(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).setUninterpretedOption(index, value);
                return this;
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).setUninterpretedOption(index, (UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).addUninterpretedOption(value);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).addUninterpretedOption(index, value);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).addUninterpretedOption((UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).addUninterpretedOption(index, (UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).addAllUninterpretedOption(values);
                return this;
            }

            public Builder clearUninterpretedOption() {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).clearUninterpretedOption();
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).removeUninterpretedOption(index);
                return this;
            }

            public List<Declaration> getDeclarationList() {
                return Collections.unmodifiableList(((ExtensionRangeOptions) this.instance).getDeclarationList());
            }

            public int getDeclarationCount() {
                return ((ExtensionRangeOptions) this.instance).getDeclarationCount();
            }

            public Declaration getDeclaration(int index) {
                return ((ExtensionRangeOptions) this.instance).getDeclaration(index);
            }

            public Builder setDeclaration(int index, Declaration value) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).setDeclaration(index, value);
                return this;
            }

            public Builder setDeclaration(int index, Declaration.Builder builderForValue) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).setDeclaration(index, (Declaration) builderForValue.build());
                return this;
            }

            public Builder addDeclaration(Declaration value) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).addDeclaration(value);
                return this;
            }

            public Builder addDeclaration(int index, Declaration value) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).addDeclaration(index, value);
                return this;
            }

            public Builder addDeclaration(Declaration.Builder builderForValue) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).addDeclaration((Declaration) builderForValue.build());
                return this;
            }

            public Builder addDeclaration(int index, Declaration.Builder builderForValue) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).addDeclaration(index, (Declaration) builderForValue.build());
                return this;
            }

            public Builder addAllDeclaration(Iterable<? extends Declaration> values) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).addAllDeclaration(values);
                return this;
            }

            public Builder clearDeclaration() {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).clearDeclaration();
                return this;
            }

            public Builder removeDeclaration(int index) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).removeDeclaration(index);
                return this;
            }

            public boolean hasFeatures() {
                return ((ExtensionRangeOptions) this.instance).hasFeatures();
            }

            public FeatureSet getFeatures() {
                return ((ExtensionRangeOptions) this.instance).getFeatures();
            }

            public Builder setFeatures(FeatureSet value) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).setFeatures(value);
                return this;
            }

            public Builder setFeatures(FeatureSet.Builder builderForValue) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).setFeatures((FeatureSet) builderForValue.build());
                return this;
            }

            public Builder mergeFeatures(FeatureSet value) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).mergeFeatures(value);
                return this;
            }

            public Builder clearFeatures() {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).clearFeatures();
                return this;
            }

            public boolean hasVerification() {
                return ((ExtensionRangeOptions) this.instance).hasVerification();
            }

            public VerificationState getVerification() {
                return ((ExtensionRangeOptions) this.instance).getVerification();
            }

            public Builder setVerification(VerificationState value) {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).setVerification(value);
                return this;
            }

            public Builder clearVerification() {
                copyOnWrite();
                ((ExtensionRangeOptions) this.instance).clearVerification();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new ExtensionRangeOptions();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0002ϧ\u0004\u0000\u0002\u0002\u0002\u001b\u0003᠌\u00012ᐉ\u0000ϧЛ", new Object[]{"bitField0_", "declaration_", Declaration.class, "verification_", VerificationState.internalGetVerifier(), "features_", "uninterpretedOption_", UninterpretedOption.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<ExtensionRangeOptions> parser = PARSER;
                    if (parser == null) {
                        synchronized (ExtensionRangeOptions.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            ExtensionRangeOptions defaultInstance = new ExtensionRangeOptions();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(ExtensionRangeOptions.class, defaultInstance);
        }

        public static ExtensionRangeOptions getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ExtensionRangeOptions> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class FieldDescriptorProto extends GeneratedMessageLite<FieldDescriptorProto, Builder> implements FieldDescriptorProtoOrBuilder {
        /* access modifiers changed from: private */
        public static final FieldDescriptorProto DEFAULT_INSTANCE;
        public static final int DEFAULT_VALUE_FIELD_NUMBER = 7;
        public static final int EXTENDEE_FIELD_NUMBER = 2;
        public static final int JSON_NAME_FIELD_NUMBER = 10;
        public static final int LABEL_FIELD_NUMBER = 4;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int NUMBER_FIELD_NUMBER = 3;
        public static final int ONEOF_INDEX_FIELD_NUMBER = 9;
        public static final int OPTIONS_FIELD_NUMBER = 8;
        private static volatile Parser<FieldDescriptorProto> PARSER = null;
        public static final int PROTO3_OPTIONAL_FIELD_NUMBER = 17;
        public static final int TYPE_FIELD_NUMBER = 5;
        public static final int TYPE_NAME_FIELD_NUMBER = 6;
        private int bitField0_;
        private String defaultValue_ = "";
        private String extendee_ = "";
        private String jsonName_ = "";
        private int label_ = 1;
        private byte memoizedIsInitialized = 2;
        private String name_ = "";
        private int number_;
        private int oneofIndex_;
        private FieldOptions options_;
        private boolean proto3Optional_;
        private String typeName_ = "";
        private int type_ = 1;

        private FieldDescriptorProto() {
        }

        public enum Type implements Internal.EnumLite {
            TYPE_DOUBLE(1),
            TYPE_FLOAT(2),
            TYPE_INT64(3),
            TYPE_UINT64(4),
            TYPE_INT32(5),
            TYPE_FIXED64(6),
            TYPE_FIXED32(7),
            TYPE_BOOL(8),
            TYPE_STRING(9),
            TYPE_GROUP(10),
            TYPE_MESSAGE(11),
            TYPE_BYTES(12),
            TYPE_UINT32(13),
            TYPE_ENUM(14),
            TYPE_SFIXED32(15),
            TYPE_SFIXED64(16),
            TYPE_SINT32(17),
            TYPE_SINT64(18);
            
            public static final int TYPE_BOOL_VALUE = 8;
            public static final int TYPE_BYTES_VALUE = 12;
            public static final int TYPE_DOUBLE_VALUE = 1;
            public static final int TYPE_ENUM_VALUE = 14;
            public static final int TYPE_FIXED32_VALUE = 7;
            public static final int TYPE_FIXED64_VALUE = 6;
            public static final int TYPE_FLOAT_VALUE = 2;
            public static final int TYPE_GROUP_VALUE = 10;
            public static final int TYPE_INT32_VALUE = 5;
            public static final int TYPE_INT64_VALUE = 3;
            public static final int TYPE_MESSAGE_VALUE = 11;
            public static final int TYPE_SFIXED32_VALUE = 15;
            public static final int TYPE_SFIXED64_VALUE = 16;
            public static final int TYPE_SINT32_VALUE = 17;
            public static final int TYPE_SINT64_VALUE = 18;
            public static final int TYPE_STRING_VALUE = 9;
            public static final int TYPE_UINT32_VALUE = 13;
            public static final int TYPE_UINT64_VALUE = 4;
            private static final Internal.EnumLiteMap<Type> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new Internal.EnumLiteMap<Type>() {
                    public Type findValueByNumber(int number) {
                        return Type.forNumber(number);
                    }
                };
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static Type valueOf(int value2) {
                return forNumber(value2);
            }

            public static Type forNumber(int value2) {
                switch (value2) {
                    case 1:
                        return TYPE_DOUBLE;
                    case 2:
                        return TYPE_FLOAT;
                    case 3:
                        return TYPE_INT64;
                    case 4:
                        return TYPE_UINT64;
                    case 5:
                        return TYPE_INT32;
                    case 6:
                        return TYPE_FIXED64;
                    case 7:
                        return TYPE_FIXED32;
                    case 8:
                        return TYPE_BOOL;
                    case 9:
                        return TYPE_STRING;
                    case 10:
                        return TYPE_GROUP;
                    case 11:
                        return TYPE_MESSAGE;
                    case 12:
                        return TYPE_BYTES;
                    case 13:
                        return TYPE_UINT32;
                    case 14:
                        return TYPE_ENUM;
                    case 15:
                        return TYPE_SFIXED32;
                    case 16:
                        return TYPE_SFIXED64;
                    case 17:
                        return TYPE_SINT32;
                    case 18:
                        return TYPE_SINT64;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<Type> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return TypeVerifier.INSTANCE;
            }

            private static final class TypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = null;

                private TypeVerifier() {
                }

                static {
                    INSTANCE = new TypeVerifier();
                }

                public boolean isInRange(int number) {
                    return Type.forNumber(number) != null;
                }
            }

            private Type(int value2) {
                this.value = value2;
            }
        }

        public enum Label implements Internal.EnumLite {
            LABEL_OPTIONAL(1),
            LABEL_REPEATED(3),
            LABEL_REQUIRED(2);
            
            public static final int LABEL_OPTIONAL_VALUE = 1;
            public static final int LABEL_REPEATED_VALUE = 3;
            public static final int LABEL_REQUIRED_VALUE = 2;
            private static final Internal.EnumLiteMap<Label> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new Internal.EnumLiteMap<Label>() {
                    public Label findValueByNumber(int number) {
                        return Label.forNumber(number);
                    }
                };
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static Label valueOf(int value2) {
                return forNumber(value2);
            }

            public static Label forNumber(int value2) {
                switch (value2) {
                    case 1:
                        return LABEL_OPTIONAL;
                    case 2:
                        return LABEL_REQUIRED;
                    case 3:
                        return LABEL_REPEATED;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<Label> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return LabelVerifier.INSTANCE;
            }

            private static final class LabelVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = null;

                private LabelVerifier() {
                }

                static {
                    INSTANCE = new LabelVerifier();
                }

                public boolean isInRange(int number) {
                    return Label.forNumber(number) != null;
                }
            }

            private Label(int value2) {
                this.value = value2;
            }
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 1;
            this.name_ = value;
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.bitField0_ &= -2;
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            this.name_ = value.toStringUtf8();
            this.bitField0_ |= 1;
        }

        public boolean hasNumber() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getNumber() {
            return this.number_;
        }

        /* access modifiers changed from: private */
        public void setNumber(int value) {
            this.bitField0_ |= 2;
            this.number_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNumber() {
            this.bitField0_ &= -3;
            this.number_ = 0;
        }

        public boolean hasLabel() {
            return (this.bitField0_ & 4) != 0;
        }

        public Label getLabel() {
            Label result = Label.forNumber(this.label_);
            return result == null ? Label.LABEL_OPTIONAL : result;
        }

        /* access modifiers changed from: private */
        public void setLabel(Label value) {
            this.label_ = value.getNumber();
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void clearLabel() {
            this.bitField0_ &= -5;
            this.label_ = 1;
        }

        public boolean hasType() {
            return (this.bitField0_ & 8) != 0;
        }

        public Type getType() {
            Type result = Type.forNumber(this.type_);
            return result == null ? Type.TYPE_DOUBLE : result;
        }

        /* access modifiers changed from: private */
        public void setType(Type value) {
            this.type_ = value.getNumber();
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void clearType() {
            this.bitField0_ &= -9;
            this.type_ = 1;
        }

        public boolean hasTypeName() {
            return (this.bitField0_ & 16) != 0;
        }

        public String getTypeName() {
            return this.typeName_;
        }

        public ByteString getTypeNameBytes() {
            return ByteString.copyFromUtf8(this.typeName_);
        }

        /* access modifiers changed from: private */
        public void setTypeName(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 16;
            this.typeName_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTypeName() {
            this.bitField0_ &= -17;
            this.typeName_ = getDefaultInstance().getTypeName();
        }

        /* access modifiers changed from: private */
        public void setTypeNameBytes(ByteString value) {
            this.typeName_ = value.toStringUtf8();
            this.bitField0_ |= 16;
        }

        public boolean hasExtendee() {
            return (this.bitField0_ & 32) != 0;
        }

        public String getExtendee() {
            return this.extendee_;
        }

        public ByteString getExtendeeBytes() {
            return ByteString.copyFromUtf8(this.extendee_);
        }

        /* access modifiers changed from: private */
        public void setExtendee(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 32;
            this.extendee_ = value;
        }

        /* access modifiers changed from: private */
        public void clearExtendee() {
            this.bitField0_ &= -33;
            this.extendee_ = getDefaultInstance().getExtendee();
        }

        /* access modifiers changed from: private */
        public void setExtendeeBytes(ByteString value) {
            this.extendee_ = value.toStringUtf8();
            this.bitField0_ |= 32;
        }

        public boolean hasDefaultValue() {
            return (this.bitField0_ & 64) != 0;
        }

        public String getDefaultValue() {
            return this.defaultValue_;
        }

        public ByteString getDefaultValueBytes() {
            return ByteString.copyFromUtf8(this.defaultValue_);
        }

        /* access modifiers changed from: private */
        public void setDefaultValue(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 64;
            this.defaultValue_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDefaultValue() {
            this.bitField0_ &= -65;
            this.defaultValue_ = getDefaultInstance().getDefaultValue();
        }

        /* access modifiers changed from: private */
        public void setDefaultValueBytes(ByteString value) {
            this.defaultValue_ = value.toStringUtf8();
            this.bitField0_ |= 64;
        }

        public boolean hasOneofIndex() {
            return (this.bitField0_ & 128) != 0;
        }

        public int getOneofIndex() {
            return this.oneofIndex_;
        }

        /* access modifiers changed from: private */
        public void setOneofIndex(int value) {
            this.bitField0_ |= 128;
            this.oneofIndex_ = value;
        }

        /* access modifiers changed from: private */
        public void clearOneofIndex() {
            this.bitField0_ &= -129;
            this.oneofIndex_ = 0;
        }

        public boolean hasJsonName() {
            return (this.bitField0_ & 256) != 0;
        }

        public String getJsonName() {
            return this.jsonName_;
        }

        public ByteString getJsonNameBytes() {
            return ByteString.copyFromUtf8(this.jsonName_);
        }

        /* access modifiers changed from: private */
        public void setJsonName(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 256;
            this.jsonName_ = value;
        }

        /* access modifiers changed from: private */
        public void clearJsonName() {
            this.bitField0_ &= -257;
            this.jsonName_ = getDefaultInstance().getJsonName();
        }

        /* access modifiers changed from: private */
        public void setJsonNameBytes(ByteString value) {
            this.jsonName_ = value.toStringUtf8();
            this.bitField0_ |= 256;
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 512) != 0;
        }

        public FieldOptions getOptions() {
            return this.options_ == null ? FieldOptions.getDefaultInstance() : this.options_;
        }

        /* access modifiers changed from: private */
        public void setOptions(FieldOptions value) {
            value.getClass();
            this.options_ = value;
            this.bitField0_ |= 512;
        }

        /* access modifiers changed from: private */
        public void mergeOptions(FieldOptions value) {
            value.getClass();
            if (this.options_ == null || this.options_ == FieldOptions.getDefaultInstance()) {
                this.options_ = value;
            } else {
                this.options_ = (FieldOptions) ((FieldOptions.Builder) FieldOptions.newBuilder(this.options_).mergeFrom(value)).buildPartial();
            }
            this.bitField0_ |= 512;
        }

        /* access modifiers changed from: private */
        public void clearOptions() {
            this.options_ = null;
            this.bitField0_ &= -513;
        }

        public boolean hasProto3Optional() {
            return (this.bitField0_ & 1024) != 0;
        }

        public boolean getProto3Optional() {
            return this.proto3Optional_;
        }

        /* access modifiers changed from: private */
        public void setProto3Optional(boolean value) {
            this.bitField0_ |= 1024;
            this.proto3Optional_ = value;
        }

        /* access modifiers changed from: private */
        public void clearProto3Optional() {
            this.bitField0_ &= -1025;
            this.proto3Optional_ = false;
        }

        public static FieldDescriptorProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (FieldDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FieldDescriptorProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FieldDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FieldDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FieldDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FieldDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FieldDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FieldDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FieldDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FieldDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FieldDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FieldDescriptorProto parseFrom(InputStream input) throws IOException {
            return (FieldDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FieldDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FieldDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FieldDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (FieldDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static FieldDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FieldDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FieldDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (FieldDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FieldDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FieldDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(FieldDescriptorProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<FieldDescriptorProto, Builder> implements FieldDescriptorProtoOrBuilder {
            private Builder() {
                super(FieldDescriptorProto.DEFAULT_INSTANCE);
            }

            public boolean hasName() {
                return ((FieldDescriptorProto) this.instance).hasName();
            }

            public String getName() {
                return ((FieldDescriptorProto) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((FieldDescriptorProto) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setNameBytes(value);
                return this;
            }

            public boolean hasNumber() {
                return ((FieldDescriptorProto) this.instance).hasNumber();
            }

            public int getNumber() {
                return ((FieldDescriptorProto) this.instance).getNumber();
            }

            public Builder setNumber(int value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setNumber(value);
                return this;
            }

            public Builder clearNumber() {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).clearNumber();
                return this;
            }

            public boolean hasLabel() {
                return ((FieldDescriptorProto) this.instance).hasLabel();
            }

            public Label getLabel() {
                return ((FieldDescriptorProto) this.instance).getLabel();
            }

            public Builder setLabel(Label value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setLabel(value);
                return this;
            }

            public Builder clearLabel() {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).clearLabel();
                return this;
            }

            public boolean hasType() {
                return ((FieldDescriptorProto) this.instance).hasType();
            }

            public Type getType() {
                return ((FieldDescriptorProto) this.instance).getType();
            }

            public Builder setType(Type value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setType(value);
                return this;
            }

            public Builder clearType() {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).clearType();
                return this;
            }

            public boolean hasTypeName() {
                return ((FieldDescriptorProto) this.instance).hasTypeName();
            }

            public String getTypeName() {
                return ((FieldDescriptorProto) this.instance).getTypeName();
            }

            public ByteString getTypeNameBytes() {
                return ((FieldDescriptorProto) this.instance).getTypeNameBytes();
            }

            public Builder setTypeName(String value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setTypeName(value);
                return this;
            }

            public Builder clearTypeName() {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).clearTypeName();
                return this;
            }

            public Builder setTypeNameBytes(ByteString value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setTypeNameBytes(value);
                return this;
            }

            public boolean hasExtendee() {
                return ((FieldDescriptorProto) this.instance).hasExtendee();
            }

            public String getExtendee() {
                return ((FieldDescriptorProto) this.instance).getExtendee();
            }

            public ByteString getExtendeeBytes() {
                return ((FieldDescriptorProto) this.instance).getExtendeeBytes();
            }

            public Builder setExtendee(String value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setExtendee(value);
                return this;
            }

            public Builder clearExtendee() {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).clearExtendee();
                return this;
            }

            public Builder setExtendeeBytes(ByteString value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setExtendeeBytes(value);
                return this;
            }

            public boolean hasDefaultValue() {
                return ((FieldDescriptorProto) this.instance).hasDefaultValue();
            }

            public String getDefaultValue() {
                return ((FieldDescriptorProto) this.instance).getDefaultValue();
            }

            public ByteString getDefaultValueBytes() {
                return ((FieldDescriptorProto) this.instance).getDefaultValueBytes();
            }

            public Builder setDefaultValue(String value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setDefaultValue(value);
                return this;
            }

            public Builder clearDefaultValue() {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).clearDefaultValue();
                return this;
            }

            public Builder setDefaultValueBytes(ByteString value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setDefaultValueBytes(value);
                return this;
            }

            public boolean hasOneofIndex() {
                return ((FieldDescriptorProto) this.instance).hasOneofIndex();
            }

            public int getOneofIndex() {
                return ((FieldDescriptorProto) this.instance).getOneofIndex();
            }

            public Builder setOneofIndex(int value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setOneofIndex(value);
                return this;
            }

            public Builder clearOneofIndex() {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).clearOneofIndex();
                return this;
            }

            public boolean hasJsonName() {
                return ((FieldDescriptorProto) this.instance).hasJsonName();
            }

            public String getJsonName() {
                return ((FieldDescriptorProto) this.instance).getJsonName();
            }

            public ByteString getJsonNameBytes() {
                return ((FieldDescriptorProto) this.instance).getJsonNameBytes();
            }

            public Builder setJsonName(String value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setJsonName(value);
                return this;
            }

            public Builder clearJsonName() {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).clearJsonName();
                return this;
            }

            public Builder setJsonNameBytes(ByteString value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setJsonNameBytes(value);
                return this;
            }

            public boolean hasOptions() {
                return ((FieldDescriptorProto) this.instance).hasOptions();
            }

            public FieldOptions getOptions() {
                return ((FieldDescriptorProto) this.instance).getOptions();
            }

            public Builder setOptions(FieldOptions value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setOptions(value);
                return this;
            }

            public Builder setOptions(FieldOptions.Builder builderForValue) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setOptions((FieldOptions) builderForValue.build());
                return this;
            }

            public Builder mergeOptions(FieldOptions value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).mergeOptions(value);
                return this;
            }

            public Builder clearOptions() {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).clearOptions();
                return this;
            }

            public boolean hasProto3Optional() {
                return ((FieldDescriptorProto) this.instance).hasProto3Optional();
            }

            public boolean getProto3Optional() {
                return ((FieldDescriptorProto) this.instance).getProto3Optional();
            }

            public Builder setProto3Optional(boolean value) {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).setProto3Optional(value);
                return this;
            }

            public Builder clearProto3Optional() {
                copyOnWrite();
                ((FieldDescriptorProto) this.instance).clearProto3Optional();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new FieldDescriptorProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u000b\u0000\u0001\u0001\u0011\u000b\u0000\u0000\u0001\u0001ဈ\u0000\u0002ဈ\u0005\u0003င\u0001\u0004᠌\u0002\u0005᠌\u0003\u0006ဈ\u0004\u0007ဈ\u0006\bᐉ\t\tင\u0007\nဈ\b\u0011ဇ\n", new Object[]{"bitField0_", "name_", "extendee_", "number_", "label_", Label.internalGetVerifier(), "type_", Type.internalGetVerifier(), "typeName_", "defaultValue_", "options_", "oneofIndex_", "jsonName_", "proto3Optional_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<FieldDescriptorProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (FieldDescriptorProto.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            FieldDescriptorProto defaultInstance = new FieldDescriptorProto();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(FieldDescriptorProto.class, defaultInstance);
        }

        public static FieldDescriptorProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FieldDescriptorProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class OneofDescriptorProto extends GeneratedMessageLite<OneofDescriptorProto, Builder> implements OneofDescriptorProtoOrBuilder {
        /* access modifiers changed from: private */
        public static final OneofDescriptorProto DEFAULT_INSTANCE;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int OPTIONS_FIELD_NUMBER = 2;
        private static volatile Parser<OneofDescriptorProto> PARSER;
        private int bitField0_;
        private byte memoizedIsInitialized = 2;
        private String name_ = "";
        private OneofOptions options_;

        private OneofDescriptorProto() {
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 1;
            this.name_ = value;
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.bitField0_ &= -2;
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            this.name_ = value.toStringUtf8();
            this.bitField0_ |= 1;
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 2) != 0;
        }

        public OneofOptions getOptions() {
            return this.options_ == null ? OneofOptions.getDefaultInstance() : this.options_;
        }

        /* access modifiers changed from: private */
        public void setOptions(OneofOptions value) {
            value.getClass();
            this.options_ = value;
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeOptions(OneofOptions value) {
            value.getClass();
            if (this.options_ == null || this.options_ == OneofOptions.getDefaultInstance()) {
                this.options_ = value;
            } else {
                this.options_ = (OneofOptions) ((OneofOptions.Builder) OneofOptions.newBuilder(this.options_).mergeFrom(value)).buildPartial();
            }
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void clearOptions() {
            this.options_ = null;
            this.bitField0_ &= -3;
        }

        public static OneofDescriptorProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (OneofDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static OneofDescriptorProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OneofDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static OneofDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (OneofDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static OneofDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OneofDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static OneofDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (OneofDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static OneofDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OneofDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static OneofDescriptorProto parseFrom(InputStream input) throws IOException {
            return (OneofDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static OneofDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OneofDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static OneofDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (OneofDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static OneofDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OneofDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static OneofDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (OneofDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static OneofDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OneofDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(OneofDescriptorProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<OneofDescriptorProto, Builder> implements OneofDescriptorProtoOrBuilder {
            private Builder() {
                super(OneofDescriptorProto.DEFAULT_INSTANCE);
            }

            public boolean hasName() {
                return ((OneofDescriptorProto) this.instance).hasName();
            }

            public String getName() {
                return ((OneofDescriptorProto) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((OneofDescriptorProto) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((OneofDescriptorProto) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((OneofDescriptorProto) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((OneofDescriptorProto) this.instance).setNameBytes(value);
                return this;
            }

            public boolean hasOptions() {
                return ((OneofDescriptorProto) this.instance).hasOptions();
            }

            public OneofOptions getOptions() {
                return ((OneofDescriptorProto) this.instance).getOptions();
            }

            public Builder setOptions(OneofOptions value) {
                copyOnWrite();
                ((OneofDescriptorProto) this.instance).setOptions(value);
                return this;
            }

            public Builder setOptions(OneofOptions.Builder builderForValue) {
                copyOnWrite();
                ((OneofDescriptorProto) this.instance).setOptions((OneofOptions) builderForValue.build());
                return this;
            }

            public Builder mergeOptions(OneofOptions value) {
                copyOnWrite();
                ((OneofDescriptorProto) this.instance).mergeOptions(value);
                return this;
            }

            public Builder clearOptions() {
                copyOnWrite();
                ((OneofDescriptorProto) this.instance).clearOptions();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new OneofDescriptorProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0001\u0001ဈ\u0000\u0002ᐉ\u0001", new Object[]{"bitField0_", "name_", "options_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<OneofDescriptorProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (OneofDescriptorProto.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            OneofDescriptorProto defaultInstance = new OneofDescriptorProto();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(OneofDescriptorProto.class, defaultInstance);
        }

        public static OneofDescriptorProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<OneofDescriptorProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class EnumDescriptorProto extends GeneratedMessageLite<EnumDescriptorProto, Builder> implements EnumDescriptorProtoOrBuilder {
        /* access modifiers changed from: private */
        public static final EnumDescriptorProto DEFAULT_INSTANCE;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int OPTIONS_FIELD_NUMBER = 3;
        private static volatile Parser<EnumDescriptorProto> PARSER = null;
        public static final int RESERVED_NAME_FIELD_NUMBER = 5;
        public static final int RESERVED_RANGE_FIELD_NUMBER = 4;
        public static final int VALUE_FIELD_NUMBER = 2;
        private int bitField0_;
        private byte memoizedIsInitialized = 2;
        private String name_ = "";
        private EnumOptions options_;
        private Internal.ProtobufList<String> reservedName_ = GeneratedMessageLite.emptyProtobufList();
        private Internal.ProtobufList<EnumReservedRange> reservedRange_ = emptyProtobufList();
        private Internal.ProtobufList<EnumValueDescriptorProto> value_ = emptyProtobufList();

        public interface EnumReservedRangeOrBuilder extends MessageLiteOrBuilder {
            int getEnd();

            int getStart();

            boolean hasEnd();

            boolean hasStart();
        }

        private EnumDescriptorProto() {
        }

        public static final class EnumReservedRange extends GeneratedMessageLite<EnumReservedRange, Builder> implements EnumReservedRangeOrBuilder {
            /* access modifiers changed from: private */
            public static final EnumReservedRange DEFAULT_INSTANCE;
            public static final int END_FIELD_NUMBER = 2;
            private static volatile Parser<EnumReservedRange> PARSER = null;
            public static final int START_FIELD_NUMBER = 1;
            private int bitField0_;
            private int end_;
            private int start_;

            private EnumReservedRange() {
            }

            public boolean hasStart() {
                return (this.bitField0_ & 1) != 0;
            }

            public int getStart() {
                return this.start_;
            }

            /* access modifiers changed from: private */
            public void setStart(int value) {
                this.bitField0_ |= 1;
                this.start_ = value;
            }

            /* access modifiers changed from: private */
            public void clearStart() {
                this.bitField0_ &= -2;
                this.start_ = 0;
            }

            public boolean hasEnd() {
                return (this.bitField0_ & 2) != 0;
            }

            public int getEnd() {
                return this.end_;
            }

            /* access modifiers changed from: private */
            public void setEnd(int value) {
                this.bitField0_ |= 2;
                this.end_ = value;
            }

            /* access modifiers changed from: private */
            public void clearEnd() {
                this.bitField0_ &= -3;
                this.end_ = 0;
            }

            public static EnumReservedRange parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (EnumReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static EnumReservedRange parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (EnumReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static EnumReservedRange parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (EnumReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static EnumReservedRange parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (EnumReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static EnumReservedRange parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (EnumReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static EnumReservedRange parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (EnumReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static EnumReservedRange parseFrom(InputStream input) throws IOException {
                return (EnumReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static EnumReservedRange parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (EnumReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static EnumReservedRange parseDelimitedFrom(InputStream input) throws IOException {
                return (EnumReservedRange) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static EnumReservedRange parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (EnumReservedRange) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static EnumReservedRange parseFrom(CodedInputStream input) throws IOException {
                return (EnumReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static EnumReservedRange parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (EnumReservedRange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(EnumReservedRange prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<EnumReservedRange, Builder> implements EnumReservedRangeOrBuilder {
                private Builder() {
                    super(EnumReservedRange.DEFAULT_INSTANCE);
                }

                public boolean hasStart() {
                    return ((EnumReservedRange) this.instance).hasStart();
                }

                public int getStart() {
                    return ((EnumReservedRange) this.instance).getStart();
                }

                public Builder setStart(int value) {
                    copyOnWrite();
                    ((EnumReservedRange) this.instance).setStart(value);
                    return this;
                }

                public Builder clearStart() {
                    copyOnWrite();
                    ((EnumReservedRange) this.instance).clearStart();
                    return this;
                }

                public boolean hasEnd() {
                    return ((EnumReservedRange) this.instance).hasEnd();
                }

                public int getEnd() {
                    return ((EnumReservedRange) this.instance).getEnd();
                }

                public Builder setEnd(int value) {
                    copyOnWrite();
                    ((EnumReservedRange) this.instance).setEnd(value);
                    return this;
                }

                public Builder clearEnd() {
                    copyOnWrite();
                    ((EnumReservedRange) this.instance).clearEnd();
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new EnumReservedRange();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001င\u0000\u0002င\u0001", new Object[]{"bitField0_", "start_", "end_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<EnumReservedRange> parser = PARSER;
                        if (parser == null) {
                            synchronized (EnumReservedRange.class) {
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
                EnumReservedRange defaultInstance = new EnumReservedRange();
                DEFAULT_INSTANCE = defaultInstance;
                GeneratedMessageLite.registerDefaultInstance(EnumReservedRange.class, defaultInstance);
            }

            public static EnumReservedRange getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<EnumReservedRange> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 1;
            this.name_ = value;
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.bitField0_ &= -2;
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            this.name_ = value.toStringUtf8();
            this.bitField0_ |= 1;
        }

        public List<EnumValueDescriptorProto> getValueList() {
            return this.value_;
        }

        public List<? extends EnumValueDescriptorProtoOrBuilder> getValueOrBuilderList() {
            return this.value_;
        }

        public int getValueCount() {
            return this.value_.size();
        }

        public EnumValueDescriptorProto getValue(int index) {
            return (EnumValueDescriptorProto) this.value_.get(index);
        }

        public EnumValueDescriptorProtoOrBuilder getValueOrBuilder(int index) {
            return (EnumValueDescriptorProtoOrBuilder) this.value_.get(index);
        }

        private void ensureValueIsMutable() {
            Internal.ProtobufList<EnumValueDescriptorProto> tmp = this.value_;
            if (!tmp.isModifiable()) {
                this.value_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setValue(int index, EnumValueDescriptorProto value) {
            value.getClass();
            ensureValueIsMutable();
            this.value_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addValue(EnumValueDescriptorProto value) {
            value.getClass();
            ensureValueIsMutable();
            this.value_.add(value);
        }

        /* access modifiers changed from: private */
        public void addValue(int index, EnumValueDescriptorProto value) {
            value.getClass();
            ensureValueIsMutable();
            this.value_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllValue(Iterable<? extends EnumValueDescriptorProto> values) {
            ensureValueIsMutable();
            AbstractMessageLite.addAll(values, this.value_);
        }

        /* access modifiers changed from: private */
        public void clearValue() {
            this.value_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeValue(int index) {
            ensureValueIsMutable();
            this.value_.remove(index);
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 2) != 0;
        }

        public EnumOptions getOptions() {
            return this.options_ == null ? EnumOptions.getDefaultInstance() : this.options_;
        }

        /* access modifiers changed from: private */
        public void setOptions(EnumOptions value) {
            value.getClass();
            this.options_ = value;
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeOptions(EnumOptions value) {
            value.getClass();
            if (this.options_ == null || this.options_ == EnumOptions.getDefaultInstance()) {
                this.options_ = value;
            } else {
                this.options_ = (EnumOptions) ((EnumOptions.Builder) EnumOptions.newBuilder(this.options_).mergeFrom(value)).buildPartial();
            }
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void clearOptions() {
            this.options_ = null;
            this.bitField0_ &= -3;
        }

        public List<EnumReservedRange> getReservedRangeList() {
            return this.reservedRange_;
        }

        public List<? extends EnumReservedRangeOrBuilder> getReservedRangeOrBuilderList() {
            return this.reservedRange_;
        }

        public int getReservedRangeCount() {
            return this.reservedRange_.size();
        }

        public EnumReservedRange getReservedRange(int index) {
            return (EnumReservedRange) this.reservedRange_.get(index);
        }

        public EnumReservedRangeOrBuilder getReservedRangeOrBuilder(int index) {
            return (EnumReservedRangeOrBuilder) this.reservedRange_.get(index);
        }

        private void ensureReservedRangeIsMutable() {
            Internal.ProtobufList<EnumReservedRange> tmp = this.reservedRange_;
            if (!tmp.isModifiable()) {
                this.reservedRange_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setReservedRange(int index, EnumReservedRange value) {
            value.getClass();
            ensureReservedRangeIsMutable();
            this.reservedRange_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addReservedRange(EnumReservedRange value) {
            value.getClass();
            ensureReservedRangeIsMutable();
            this.reservedRange_.add(value);
        }

        /* access modifiers changed from: private */
        public void addReservedRange(int index, EnumReservedRange value) {
            value.getClass();
            ensureReservedRangeIsMutable();
            this.reservedRange_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllReservedRange(Iterable<? extends EnumReservedRange> values) {
            ensureReservedRangeIsMutable();
            AbstractMessageLite.addAll(values, this.reservedRange_);
        }

        /* access modifiers changed from: private */
        public void clearReservedRange() {
            this.reservedRange_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeReservedRange(int index) {
            ensureReservedRangeIsMutable();
            this.reservedRange_.remove(index);
        }

        public List<String> getReservedNameList() {
            return this.reservedName_;
        }

        public int getReservedNameCount() {
            return this.reservedName_.size();
        }

        public String getReservedName(int index) {
            return (String) this.reservedName_.get(index);
        }

        public ByteString getReservedNameBytes(int index) {
            return ByteString.copyFromUtf8((String) this.reservedName_.get(index));
        }

        private void ensureReservedNameIsMutable() {
            Internal.ProtobufList<String> tmp = this.reservedName_;
            if (!tmp.isModifiable()) {
                this.reservedName_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setReservedName(int index, String value) {
            Class<?> cls = value.getClass();
            ensureReservedNameIsMutable();
            this.reservedName_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addReservedName(String value) {
            Class<?> cls = value.getClass();
            ensureReservedNameIsMutable();
            this.reservedName_.add(value);
        }

        /* access modifiers changed from: private */
        public void addAllReservedName(Iterable<String> values) {
            ensureReservedNameIsMutable();
            AbstractMessageLite.addAll(values, this.reservedName_);
        }

        /* access modifiers changed from: private */
        public void clearReservedName() {
            this.reservedName_ = GeneratedMessageLite.emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void addReservedNameBytes(ByteString value) {
            ensureReservedNameIsMutable();
            this.reservedName_.add(value.toStringUtf8());
        }

        public static EnumDescriptorProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (EnumDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumDescriptorProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (EnumDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (EnumDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumDescriptorProto parseFrom(InputStream input) throws IOException {
            return (EnumDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static EnumDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (EnumDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static EnumDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (EnumDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(EnumDescriptorProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<EnumDescriptorProto, Builder> implements EnumDescriptorProtoOrBuilder {
            private Builder() {
                super(EnumDescriptorProto.DEFAULT_INSTANCE);
            }

            public boolean hasName() {
                return ((EnumDescriptorProto) this.instance).hasName();
            }

            public String getName() {
                return ((EnumDescriptorProto) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((EnumDescriptorProto) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).setNameBytes(value);
                return this;
            }

            public List<EnumValueDescriptorProto> getValueList() {
                return Collections.unmodifiableList(((EnumDescriptorProto) this.instance).getValueList());
            }

            public int getValueCount() {
                return ((EnumDescriptorProto) this.instance).getValueCount();
            }

            public EnumValueDescriptorProto getValue(int index) {
                return ((EnumDescriptorProto) this.instance).getValue(index);
            }

            public Builder setValue(int index, EnumValueDescriptorProto value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).setValue(index, value);
                return this;
            }

            public Builder setValue(int index, EnumValueDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).setValue(index, (EnumValueDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addValue(EnumValueDescriptorProto value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addValue(value);
                return this;
            }

            public Builder addValue(int index, EnumValueDescriptorProto value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addValue(index, value);
                return this;
            }

            public Builder addValue(EnumValueDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addValue((EnumValueDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addValue(int index, EnumValueDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addValue(index, (EnumValueDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addAllValue(Iterable<? extends EnumValueDescriptorProto> values) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addAllValue(values);
                return this;
            }

            public Builder clearValue() {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).clearValue();
                return this;
            }

            public Builder removeValue(int index) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).removeValue(index);
                return this;
            }

            public boolean hasOptions() {
                return ((EnumDescriptorProto) this.instance).hasOptions();
            }

            public EnumOptions getOptions() {
                return ((EnumDescriptorProto) this.instance).getOptions();
            }

            public Builder setOptions(EnumOptions value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).setOptions(value);
                return this;
            }

            public Builder setOptions(EnumOptions.Builder builderForValue) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).setOptions((EnumOptions) builderForValue.build());
                return this;
            }

            public Builder mergeOptions(EnumOptions value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).mergeOptions(value);
                return this;
            }

            public Builder clearOptions() {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).clearOptions();
                return this;
            }

            public List<EnumReservedRange> getReservedRangeList() {
                return Collections.unmodifiableList(((EnumDescriptorProto) this.instance).getReservedRangeList());
            }

            public int getReservedRangeCount() {
                return ((EnumDescriptorProto) this.instance).getReservedRangeCount();
            }

            public EnumReservedRange getReservedRange(int index) {
                return ((EnumDescriptorProto) this.instance).getReservedRange(index);
            }

            public Builder setReservedRange(int index, EnumReservedRange value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).setReservedRange(index, value);
                return this;
            }

            public Builder setReservedRange(int index, EnumReservedRange.Builder builderForValue) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).setReservedRange(index, (EnumReservedRange) builderForValue.build());
                return this;
            }

            public Builder addReservedRange(EnumReservedRange value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addReservedRange(value);
                return this;
            }

            public Builder addReservedRange(int index, EnumReservedRange value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addReservedRange(index, value);
                return this;
            }

            public Builder addReservedRange(EnumReservedRange.Builder builderForValue) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addReservedRange((EnumReservedRange) builderForValue.build());
                return this;
            }

            public Builder addReservedRange(int index, EnumReservedRange.Builder builderForValue) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addReservedRange(index, (EnumReservedRange) builderForValue.build());
                return this;
            }

            public Builder addAllReservedRange(Iterable<? extends EnumReservedRange> values) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addAllReservedRange(values);
                return this;
            }

            public Builder clearReservedRange() {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).clearReservedRange();
                return this;
            }

            public Builder removeReservedRange(int index) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).removeReservedRange(index);
                return this;
            }

            public List<String> getReservedNameList() {
                return Collections.unmodifiableList(((EnumDescriptorProto) this.instance).getReservedNameList());
            }

            public int getReservedNameCount() {
                return ((EnumDescriptorProto) this.instance).getReservedNameCount();
            }

            public String getReservedName(int index) {
                return ((EnumDescriptorProto) this.instance).getReservedName(index);
            }

            public ByteString getReservedNameBytes(int index) {
                return ((EnumDescriptorProto) this.instance).getReservedNameBytes(index);
            }

            public Builder setReservedName(int index, String value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).setReservedName(index, value);
                return this;
            }

            public Builder addReservedName(String value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addReservedName(value);
                return this;
            }

            public Builder addAllReservedName(Iterable<String> values) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addAllReservedName(values);
                return this;
            }

            public Builder clearReservedName() {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).clearReservedName();
                return this;
            }

            public Builder addReservedNameBytes(ByteString value) {
                copyOnWrite();
                ((EnumDescriptorProto) this.instance).addReservedNameBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new EnumDescriptorProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0003\u0002\u0001ဈ\u0000\u0002Л\u0003ᐉ\u0001\u0004\u001b\u0005\u001a", new Object[]{"bitField0_", "name_", "value_", EnumValueDescriptorProto.class, "options_", "reservedRange_", EnumReservedRange.class, "reservedName_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<EnumDescriptorProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (EnumDescriptorProto.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            EnumDescriptorProto defaultInstance = new EnumDescriptorProto();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(EnumDescriptorProto.class, defaultInstance);
        }

        public static EnumDescriptorProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<EnumDescriptorProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class EnumValueDescriptorProto extends GeneratedMessageLite<EnumValueDescriptorProto, Builder> implements EnumValueDescriptorProtoOrBuilder {
        /* access modifiers changed from: private */
        public static final EnumValueDescriptorProto DEFAULT_INSTANCE;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int NUMBER_FIELD_NUMBER = 2;
        public static final int OPTIONS_FIELD_NUMBER = 3;
        private static volatile Parser<EnumValueDescriptorProto> PARSER;
        private int bitField0_;
        private byte memoizedIsInitialized = 2;
        private String name_ = "";
        private int number_;
        private EnumValueOptions options_;

        private EnumValueDescriptorProto() {
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 1;
            this.name_ = value;
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.bitField0_ &= -2;
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            this.name_ = value.toStringUtf8();
            this.bitField0_ |= 1;
        }

        public boolean hasNumber() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getNumber() {
            return this.number_;
        }

        /* access modifiers changed from: private */
        public void setNumber(int value) {
            this.bitField0_ |= 2;
            this.number_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNumber() {
            this.bitField0_ &= -3;
            this.number_ = 0;
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 4) != 0;
        }

        public EnumValueOptions getOptions() {
            return this.options_ == null ? EnumValueOptions.getDefaultInstance() : this.options_;
        }

        /* access modifiers changed from: private */
        public void setOptions(EnumValueOptions value) {
            value.getClass();
            this.options_ = value;
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void mergeOptions(EnumValueOptions value) {
            value.getClass();
            if (this.options_ == null || this.options_ == EnumValueOptions.getDefaultInstance()) {
                this.options_ = value;
            } else {
                this.options_ = (EnumValueOptions) ((EnumValueOptions.Builder) EnumValueOptions.newBuilder(this.options_).mergeFrom(value)).buildPartial();
            }
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void clearOptions() {
            this.options_ = null;
            this.bitField0_ &= -5;
        }

        public static EnumValueDescriptorProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (EnumValueDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumValueDescriptorProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumValueDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumValueDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (EnumValueDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumValueDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumValueDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumValueDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (EnumValueDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumValueDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumValueDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumValueDescriptorProto parseFrom(InputStream input) throws IOException {
            return (EnumValueDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumValueDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumValueDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static EnumValueDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (EnumValueDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumValueDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumValueDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static EnumValueDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (EnumValueDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumValueDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumValueDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(EnumValueDescriptorProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<EnumValueDescriptorProto, Builder> implements EnumValueDescriptorProtoOrBuilder {
            private Builder() {
                super(EnumValueDescriptorProto.DEFAULT_INSTANCE);
            }

            public boolean hasName() {
                return ((EnumValueDescriptorProto) this.instance).hasName();
            }

            public String getName() {
                return ((EnumValueDescriptorProto) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((EnumValueDescriptorProto) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((EnumValueDescriptorProto) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((EnumValueDescriptorProto) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((EnumValueDescriptorProto) this.instance).setNameBytes(value);
                return this;
            }

            public boolean hasNumber() {
                return ((EnumValueDescriptorProto) this.instance).hasNumber();
            }

            public int getNumber() {
                return ((EnumValueDescriptorProto) this.instance).getNumber();
            }

            public Builder setNumber(int value) {
                copyOnWrite();
                ((EnumValueDescriptorProto) this.instance).setNumber(value);
                return this;
            }

            public Builder clearNumber() {
                copyOnWrite();
                ((EnumValueDescriptorProto) this.instance).clearNumber();
                return this;
            }

            public boolean hasOptions() {
                return ((EnumValueDescriptorProto) this.instance).hasOptions();
            }

            public EnumValueOptions getOptions() {
                return ((EnumValueDescriptorProto) this.instance).getOptions();
            }

            public Builder setOptions(EnumValueOptions value) {
                copyOnWrite();
                ((EnumValueDescriptorProto) this.instance).setOptions(value);
                return this;
            }

            public Builder setOptions(EnumValueOptions.Builder builderForValue) {
                copyOnWrite();
                ((EnumValueDescriptorProto) this.instance).setOptions((EnumValueOptions) builderForValue.build());
                return this;
            }

            public Builder mergeOptions(EnumValueOptions value) {
                copyOnWrite();
                ((EnumValueDescriptorProto) this.instance).mergeOptions(value);
                return this;
            }

            public Builder clearOptions() {
                copyOnWrite();
                ((EnumValueDescriptorProto) this.instance).clearOptions();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new EnumValueDescriptorProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0001\u0001ဈ\u0000\u0002င\u0001\u0003ᐉ\u0002", new Object[]{"bitField0_", "name_", "number_", "options_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<EnumValueDescriptorProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (EnumValueDescriptorProto.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            EnumValueDescriptorProto defaultInstance = new EnumValueDescriptorProto();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(EnumValueDescriptorProto.class, defaultInstance);
        }

        public static EnumValueDescriptorProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<EnumValueDescriptorProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class ServiceDescriptorProto extends GeneratedMessageLite<ServiceDescriptorProto, Builder> implements ServiceDescriptorProtoOrBuilder {
        /* access modifiers changed from: private */
        public static final ServiceDescriptorProto DEFAULT_INSTANCE;
        public static final int METHOD_FIELD_NUMBER = 2;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int OPTIONS_FIELD_NUMBER = 3;
        private static volatile Parser<ServiceDescriptorProto> PARSER;
        private int bitField0_;
        private byte memoizedIsInitialized = 2;
        private Internal.ProtobufList<MethodDescriptorProto> method_ = emptyProtobufList();
        private String name_ = "";
        private ServiceOptions options_;

        private ServiceDescriptorProto() {
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 1;
            this.name_ = value;
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.bitField0_ &= -2;
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            this.name_ = value.toStringUtf8();
            this.bitField0_ |= 1;
        }

        public List<MethodDescriptorProto> getMethodList() {
            return this.method_;
        }

        public List<? extends MethodDescriptorProtoOrBuilder> getMethodOrBuilderList() {
            return this.method_;
        }

        public int getMethodCount() {
            return this.method_.size();
        }

        public MethodDescriptorProto getMethod(int index) {
            return (MethodDescriptorProto) this.method_.get(index);
        }

        public MethodDescriptorProtoOrBuilder getMethodOrBuilder(int index) {
            return (MethodDescriptorProtoOrBuilder) this.method_.get(index);
        }

        private void ensureMethodIsMutable() {
            Internal.ProtobufList<MethodDescriptorProto> tmp = this.method_;
            if (!tmp.isModifiable()) {
                this.method_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setMethod(int index, MethodDescriptorProto value) {
            value.getClass();
            ensureMethodIsMutable();
            this.method_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addMethod(MethodDescriptorProto value) {
            value.getClass();
            ensureMethodIsMutable();
            this.method_.add(value);
        }

        /* access modifiers changed from: private */
        public void addMethod(int index, MethodDescriptorProto value) {
            value.getClass();
            ensureMethodIsMutable();
            this.method_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllMethod(Iterable<? extends MethodDescriptorProto> values) {
            ensureMethodIsMutable();
            AbstractMessageLite.addAll(values, this.method_);
        }

        /* access modifiers changed from: private */
        public void clearMethod() {
            this.method_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeMethod(int index) {
            ensureMethodIsMutable();
            this.method_.remove(index);
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 2) != 0;
        }

        public ServiceOptions getOptions() {
            return this.options_ == null ? ServiceOptions.getDefaultInstance() : this.options_;
        }

        /* access modifiers changed from: private */
        public void setOptions(ServiceOptions value) {
            value.getClass();
            this.options_ = value;
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeOptions(ServiceOptions value) {
            value.getClass();
            if (this.options_ == null || this.options_ == ServiceOptions.getDefaultInstance()) {
                this.options_ = value;
            } else {
                this.options_ = (ServiceOptions) ((ServiceOptions.Builder) ServiceOptions.newBuilder(this.options_).mergeFrom(value)).buildPartial();
            }
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void clearOptions() {
            this.options_ = null;
            this.bitField0_ &= -3;
        }

        public static ServiceDescriptorProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ServiceDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ServiceDescriptorProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServiceDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ServiceDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ServiceDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ServiceDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServiceDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ServiceDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ServiceDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ServiceDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServiceDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ServiceDescriptorProto parseFrom(InputStream input) throws IOException {
            return (ServiceDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ServiceDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServiceDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ServiceDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (ServiceDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ServiceDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServiceDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ServiceDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (ServiceDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ServiceDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServiceDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ServiceDescriptorProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<ServiceDescriptorProto, Builder> implements ServiceDescriptorProtoOrBuilder {
            private Builder() {
                super(ServiceDescriptorProto.DEFAULT_INSTANCE);
            }

            public boolean hasName() {
                return ((ServiceDescriptorProto) this.instance).hasName();
            }

            public String getName() {
                return ((ServiceDescriptorProto) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((ServiceDescriptorProto) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).setNameBytes(value);
                return this;
            }

            public List<MethodDescriptorProto> getMethodList() {
                return Collections.unmodifiableList(((ServiceDescriptorProto) this.instance).getMethodList());
            }

            public int getMethodCount() {
                return ((ServiceDescriptorProto) this.instance).getMethodCount();
            }

            public MethodDescriptorProto getMethod(int index) {
                return ((ServiceDescriptorProto) this.instance).getMethod(index);
            }

            public Builder setMethod(int index, MethodDescriptorProto value) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).setMethod(index, value);
                return this;
            }

            public Builder setMethod(int index, MethodDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).setMethod(index, (MethodDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addMethod(MethodDescriptorProto value) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).addMethod(value);
                return this;
            }

            public Builder addMethod(int index, MethodDescriptorProto value) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).addMethod(index, value);
                return this;
            }

            public Builder addMethod(MethodDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).addMethod((MethodDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addMethod(int index, MethodDescriptorProto.Builder builderForValue) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).addMethod(index, (MethodDescriptorProto) builderForValue.build());
                return this;
            }

            public Builder addAllMethod(Iterable<? extends MethodDescriptorProto> values) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).addAllMethod(values);
                return this;
            }

            public Builder clearMethod() {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).clearMethod();
                return this;
            }

            public Builder removeMethod(int index) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).removeMethod(index);
                return this;
            }

            public boolean hasOptions() {
                return ((ServiceDescriptorProto) this.instance).hasOptions();
            }

            public ServiceOptions getOptions() {
                return ((ServiceDescriptorProto) this.instance).getOptions();
            }

            public Builder setOptions(ServiceOptions value) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).setOptions(value);
                return this;
            }

            public Builder setOptions(ServiceOptions.Builder builderForValue) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).setOptions((ServiceOptions) builderForValue.build());
                return this;
            }

            public Builder mergeOptions(ServiceOptions value) {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).mergeOptions(value);
                return this;
            }

            public Builder clearOptions() {
                copyOnWrite();
                ((ServiceDescriptorProto) this.instance).clearOptions();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new ServiceDescriptorProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0002\u0001ဈ\u0000\u0002Л\u0003ᐉ\u0001", new Object[]{"bitField0_", "name_", "method_", MethodDescriptorProto.class, "options_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<ServiceDescriptorProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (ServiceDescriptorProto.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            ServiceDescriptorProto defaultInstance = new ServiceDescriptorProto();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(ServiceDescriptorProto.class, defaultInstance);
        }

        public static ServiceDescriptorProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ServiceDescriptorProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class MethodDescriptorProto extends GeneratedMessageLite<MethodDescriptorProto, Builder> implements MethodDescriptorProtoOrBuilder {
        public static final int CLIENT_STREAMING_FIELD_NUMBER = 5;
        /* access modifiers changed from: private */
        public static final MethodDescriptorProto DEFAULT_INSTANCE;
        public static final int INPUT_TYPE_FIELD_NUMBER = 2;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int OPTIONS_FIELD_NUMBER = 4;
        public static final int OUTPUT_TYPE_FIELD_NUMBER = 3;
        private static volatile Parser<MethodDescriptorProto> PARSER = null;
        public static final int SERVER_STREAMING_FIELD_NUMBER = 6;
        private int bitField0_;
        private boolean clientStreaming_;
        private String inputType_ = "";
        private byte memoizedIsInitialized = 2;
        private String name_ = "";
        private MethodOptions options_;
        private String outputType_ = "";
        private boolean serverStreaming_;

        private MethodDescriptorProto() {
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getName() {
            return this.name_;
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 1;
            this.name_ = value;
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.bitField0_ &= -2;
            this.name_ = getDefaultInstance().getName();
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            this.name_ = value.toStringUtf8();
            this.bitField0_ |= 1;
        }

        public boolean hasInputType() {
            return (this.bitField0_ & 2) != 0;
        }

        public String getInputType() {
            return this.inputType_;
        }

        public ByteString getInputTypeBytes() {
            return ByteString.copyFromUtf8(this.inputType_);
        }

        /* access modifiers changed from: private */
        public void setInputType(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 2;
            this.inputType_ = value;
        }

        /* access modifiers changed from: private */
        public void clearInputType() {
            this.bitField0_ &= -3;
            this.inputType_ = getDefaultInstance().getInputType();
        }

        /* access modifiers changed from: private */
        public void setInputTypeBytes(ByteString value) {
            this.inputType_ = value.toStringUtf8();
            this.bitField0_ |= 2;
        }

        public boolean hasOutputType() {
            return (this.bitField0_ & 4) != 0;
        }

        public String getOutputType() {
            return this.outputType_;
        }

        public ByteString getOutputTypeBytes() {
            return ByteString.copyFromUtf8(this.outputType_);
        }

        /* access modifiers changed from: private */
        public void setOutputType(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 4;
            this.outputType_ = value;
        }

        /* access modifiers changed from: private */
        public void clearOutputType() {
            this.bitField0_ &= -5;
            this.outputType_ = getDefaultInstance().getOutputType();
        }

        /* access modifiers changed from: private */
        public void setOutputTypeBytes(ByteString value) {
            this.outputType_ = value.toStringUtf8();
            this.bitField0_ |= 4;
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 8) != 0;
        }

        public MethodOptions getOptions() {
            return this.options_ == null ? MethodOptions.getDefaultInstance() : this.options_;
        }

        /* access modifiers changed from: private */
        public void setOptions(MethodOptions value) {
            value.getClass();
            this.options_ = value;
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void mergeOptions(MethodOptions value) {
            value.getClass();
            if (this.options_ == null || this.options_ == MethodOptions.getDefaultInstance()) {
                this.options_ = value;
            } else {
                this.options_ = (MethodOptions) ((MethodOptions.Builder) MethodOptions.newBuilder(this.options_).mergeFrom(value)).buildPartial();
            }
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void clearOptions() {
            this.options_ = null;
            this.bitField0_ &= -9;
        }

        public boolean hasClientStreaming() {
            return (this.bitField0_ & 16) != 0;
        }

        public boolean getClientStreaming() {
            return this.clientStreaming_;
        }

        /* access modifiers changed from: private */
        public void setClientStreaming(boolean value) {
            this.bitField0_ |= 16;
            this.clientStreaming_ = value;
        }

        /* access modifiers changed from: private */
        public void clearClientStreaming() {
            this.bitField0_ &= -17;
            this.clientStreaming_ = false;
        }

        public boolean hasServerStreaming() {
            return (this.bitField0_ & 32) != 0;
        }

        public boolean getServerStreaming() {
            return this.serverStreaming_;
        }

        /* access modifiers changed from: private */
        public void setServerStreaming(boolean value) {
            this.bitField0_ |= 32;
            this.serverStreaming_ = value;
        }

        /* access modifiers changed from: private */
        public void clearServerStreaming() {
            this.bitField0_ &= -33;
            this.serverStreaming_ = false;
        }

        public static MethodDescriptorProto parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (MethodDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MethodDescriptorProto parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MethodDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MethodDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MethodDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MethodDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MethodDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MethodDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MethodDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MethodDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MethodDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MethodDescriptorProto parseFrom(InputStream input) throws IOException {
            return (MethodDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MethodDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MethodDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MethodDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (MethodDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static MethodDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MethodDescriptorProto) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MethodDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (MethodDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MethodDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MethodDescriptorProto) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(MethodDescriptorProto prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<MethodDescriptorProto, Builder> implements MethodDescriptorProtoOrBuilder {
            private Builder() {
                super(MethodDescriptorProto.DEFAULT_INSTANCE);
            }

            public boolean hasName() {
                return ((MethodDescriptorProto) this.instance).hasName();
            }

            public String getName() {
                return ((MethodDescriptorProto) this.instance).getName();
            }

            public ByteString getNameBytes() {
                return ((MethodDescriptorProto) this.instance).getNameBytes();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).setName(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).clearName();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).setNameBytes(value);
                return this;
            }

            public boolean hasInputType() {
                return ((MethodDescriptorProto) this.instance).hasInputType();
            }

            public String getInputType() {
                return ((MethodDescriptorProto) this.instance).getInputType();
            }

            public ByteString getInputTypeBytes() {
                return ((MethodDescriptorProto) this.instance).getInputTypeBytes();
            }

            public Builder setInputType(String value) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).setInputType(value);
                return this;
            }

            public Builder clearInputType() {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).clearInputType();
                return this;
            }

            public Builder setInputTypeBytes(ByteString value) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).setInputTypeBytes(value);
                return this;
            }

            public boolean hasOutputType() {
                return ((MethodDescriptorProto) this.instance).hasOutputType();
            }

            public String getOutputType() {
                return ((MethodDescriptorProto) this.instance).getOutputType();
            }

            public ByteString getOutputTypeBytes() {
                return ((MethodDescriptorProto) this.instance).getOutputTypeBytes();
            }

            public Builder setOutputType(String value) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).setOutputType(value);
                return this;
            }

            public Builder clearOutputType() {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).clearOutputType();
                return this;
            }

            public Builder setOutputTypeBytes(ByteString value) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).setOutputTypeBytes(value);
                return this;
            }

            public boolean hasOptions() {
                return ((MethodDescriptorProto) this.instance).hasOptions();
            }

            public MethodOptions getOptions() {
                return ((MethodDescriptorProto) this.instance).getOptions();
            }

            public Builder setOptions(MethodOptions value) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).setOptions(value);
                return this;
            }

            public Builder setOptions(MethodOptions.Builder builderForValue) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).setOptions((MethodOptions) builderForValue.build());
                return this;
            }

            public Builder mergeOptions(MethodOptions value) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).mergeOptions(value);
                return this;
            }

            public Builder clearOptions() {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).clearOptions();
                return this;
            }

            public boolean hasClientStreaming() {
                return ((MethodDescriptorProto) this.instance).hasClientStreaming();
            }

            public boolean getClientStreaming() {
                return ((MethodDescriptorProto) this.instance).getClientStreaming();
            }

            public Builder setClientStreaming(boolean value) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).setClientStreaming(value);
                return this;
            }

            public Builder clearClientStreaming() {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).clearClientStreaming();
                return this;
            }

            public boolean hasServerStreaming() {
                return ((MethodDescriptorProto) this.instance).hasServerStreaming();
            }

            public boolean getServerStreaming() {
                return ((MethodDescriptorProto) this.instance).getServerStreaming();
            }

            public Builder setServerStreaming(boolean value) {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).setServerStreaming(value);
                return this;
            }

            public Builder clearServerStreaming() {
                copyOnWrite();
                ((MethodDescriptorProto) this.instance).clearServerStreaming();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new MethodDescriptorProto();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0001\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ᐉ\u0003\u0005ဇ\u0004\u0006ဇ\u0005", new Object[]{"bitField0_", "name_", "inputType_", "outputType_", "options_", "clientStreaming_", "serverStreaming_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<MethodDescriptorProto> parser = PARSER;
                    if (parser == null) {
                        synchronized (MethodDescriptorProto.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            MethodDescriptorProto defaultInstance = new MethodDescriptorProto();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(MethodDescriptorProto.class, defaultInstance);
        }

        public static MethodDescriptorProto getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MethodDescriptorProto> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class FileOptions extends GeneratedMessageLite.ExtendableMessage<FileOptions, Builder> implements FileOptionsOrBuilder {
        public static final int CC_ENABLE_ARENAS_FIELD_NUMBER = 31;
        public static final int CC_GENERIC_SERVICES_FIELD_NUMBER = 16;
        public static final int CSHARP_NAMESPACE_FIELD_NUMBER = 37;
        /* access modifiers changed from: private */
        public static final FileOptions DEFAULT_INSTANCE;
        public static final int DEPRECATED_FIELD_NUMBER = 23;
        public static final int FEATURES_FIELD_NUMBER = 50;
        public static final int GO_PACKAGE_FIELD_NUMBER = 11;
        public static final int JAVA_GENERATE_EQUALS_AND_HASH_FIELD_NUMBER = 20;
        public static final int JAVA_GENERIC_SERVICES_FIELD_NUMBER = 17;
        public static final int JAVA_MULTIPLE_FILES_FIELD_NUMBER = 10;
        public static final int JAVA_OUTER_CLASSNAME_FIELD_NUMBER = 8;
        public static final int JAVA_PACKAGE_FIELD_NUMBER = 1;
        public static final int JAVA_STRING_CHECK_UTF8_FIELD_NUMBER = 27;
        public static final int OBJC_CLASS_PREFIX_FIELD_NUMBER = 36;
        public static final int OPTIMIZE_FOR_FIELD_NUMBER = 9;
        private static volatile Parser<FileOptions> PARSER = null;
        public static final int PHP_CLASS_PREFIX_FIELD_NUMBER = 40;
        public static final int PHP_METADATA_NAMESPACE_FIELD_NUMBER = 44;
        public static final int PHP_NAMESPACE_FIELD_NUMBER = 41;
        public static final int PY_GENERIC_SERVICES_FIELD_NUMBER = 18;
        public static final int RUBY_PACKAGE_FIELD_NUMBER = 45;
        public static final int SWIFT_PREFIX_FIELD_NUMBER = 39;
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        private int bitField0_;
        private boolean ccEnableArenas_ = true;
        private boolean ccGenericServices_;
        private String csharpNamespace_ = "";
        private boolean deprecated_;
        private FeatureSet features_;
        private String goPackage_ = "";
        private boolean javaGenerateEqualsAndHash_;
        private boolean javaGenericServices_;
        private boolean javaMultipleFiles_;
        private String javaOuterClassname_ = "";
        private String javaPackage_ = "";
        private boolean javaStringCheckUtf8_;
        private byte memoizedIsInitialized = 2;
        private String objcClassPrefix_ = "";
        private int optimizeFor_ = 1;
        private String phpClassPrefix_ = "";
        private String phpMetadataNamespace_ = "";
        private String phpNamespace_ = "";
        private boolean pyGenericServices_;
        private String rubyPackage_ = "";
        private String swiftPrefix_ = "";
        private Internal.ProtobufList<UninterpretedOption> uninterpretedOption_ = emptyProtobufList();

        private FileOptions() {
        }

        public enum OptimizeMode implements Internal.EnumLite {
            SPEED(1),
            CODE_SIZE(2),
            LITE_RUNTIME(3);
            
            public static final int CODE_SIZE_VALUE = 2;
            public static final int LITE_RUNTIME_VALUE = 3;
            public static final int SPEED_VALUE = 1;
            private static final Internal.EnumLiteMap<OptimizeMode> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new Internal.EnumLiteMap<OptimizeMode>() {
                    public OptimizeMode findValueByNumber(int number) {
                        return OptimizeMode.forNumber(number);
                    }
                };
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static OptimizeMode valueOf(int value2) {
                return forNumber(value2);
            }

            public static OptimizeMode forNumber(int value2) {
                switch (value2) {
                    case 1:
                        return SPEED;
                    case 2:
                        return CODE_SIZE;
                    case 3:
                        return LITE_RUNTIME;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<OptimizeMode> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return OptimizeModeVerifier.INSTANCE;
            }

            private static final class OptimizeModeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = null;

                private OptimizeModeVerifier() {
                }

                static {
                    INSTANCE = new OptimizeModeVerifier();
                }

                public boolean isInRange(int number) {
                    return OptimizeMode.forNumber(number) != null;
                }
            }

            private OptimizeMode(int value2) {
                this.value = value2;
            }
        }

        public boolean hasJavaPackage() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getJavaPackage() {
            return this.javaPackage_;
        }

        public ByteString getJavaPackageBytes() {
            return ByteString.copyFromUtf8(this.javaPackage_);
        }

        /* access modifiers changed from: private */
        public void setJavaPackage(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 1;
            this.javaPackage_ = value;
        }

        /* access modifiers changed from: private */
        public void clearJavaPackage() {
            this.bitField0_ &= -2;
            this.javaPackage_ = getDefaultInstance().getJavaPackage();
        }

        /* access modifiers changed from: private */
        public void setJavaPackageBytes(ByteString value) {
            this.javaPackage_ = value.toStringUtf8();
            this.bitField0_ |= 1;
        }

        public boolean hasJavaOuterClassname() {
            return (this.bitField0_ & 2) != 0;
        }

        public String getJavaOuterClassname() {
            return this.javaOuterClassname_;
        }

        public ByteString getJavaOuterClassnameBytes() {
            return ByteString.copyFromUtf8(this.javaOuterClassname_);
        }

        /* access modifiers changed from: private */
        public void setJavaOuterClassname(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 2;
            this.javaOuterClassname_ = value;
        }

        /* access modifiers changed from: private */
        public void clearJavaOuterClassname() {
            this.bitField0_ &= -3;
            this.javaOuterClassname_ = getDefaultInstance().getJavaOuterClassname();
        }

        /* access modifiers changed from: private */
        public void setJavaOuterClassnameBytes(ByteString value) {
            this.javaOuterClassname_ = value.toStringUtf8();
            this.bitField0_ |= 2;
        }

        public boolean hasJavaMultipleFiles() {
            return (this.bitField0_ & 4) != 0;
        }

        public boolean getJavaMultipleFiles() {
            return this.javaMultipleFiles_;
        }

        /* access modifiers changed from: private */
        public void setJavaMultipleFiles(boolean value) {
            this.bitField0_ |= 4;
            this.javaMultipleFiles_ = value;
        }

        /* access modifiers changed from: private */
        public void clearJavaMultipleFiles() {
            this.bitField0_ &= -5;
            this.javaMultipleFiles_ = false;
        }

        @Deprecated
        public boolean hasJavaGenerateEqualsAndHash() {
            return (this.bitField0_ & 8) != 0;
        }

        @Deprecated
        public boolean getJavaGenerateEqualsAndHash() {
            return this.javaGenerateEqualsAndHash_;
        }

        /* access modifiers changed from: private */
        public void setJavaGenerateEqualsAndHash(boolean value) {
            this.bitField0_ |= 8;
            this.javaGenerateEqualsAndHash_ = value;
        }

        /* access modifiers changed from: private */
        public void clearJavaGenerateEqualsAndHash() {
            this.bitField0_ &= -9;
            this.javaGenerateEqualsAndHash_ = false;
        }

        public boolean hasJavaStringCheckUtf8() {
            return (this.bitField0_ & 16) != 0;
        }

        public boolean getJavaStringCheckUtf8() {
            return this.javaStringCheckUtf8_;
        }

        /* access modifiers changed from: private */
        public void setJavaStringCheckUtf8(boolean value) {
            this.bitField0_ |= 16;
            this.javaStringCheckUtf8_ = value;
        }

        /* access modifiers changed from: private */
        public void clearJavaStringCheckUtf8() {
            this.bitField0_ &= -17;
            this.javaStringCheckUtf8_ = false;
        }

        public boolean hasOptimizeFor() {
            return (this.bitField0_ & 32) != 0;
        }

        public OptimizeMode getOptimizeFor() {
            OptimizeMode result = OptimizeMode.forNumber(this.optimizeFor_);
            return result == null ? OptimizeMode.SPEED : result;
        }

        /* access modifiers changed from: private */
        public void setOptimizeFor(OptimizeMode value) {
            this.optimizeFor_ = value.getNumber();
            this.bitField0_ |= 32;
        }

        /* access modifiers changed from: private */
        public void clearOptimizeFor() {
            this.bitField0_ &= -33;
            this.optimizeFor_ = 1;
        }

        public boolean hasGoPackage() {
            return (this.bitField0_ & 64) != 0;
        }

        public String getGoPackage() {
            return this.goPackage_;
        }

        public ByteString getGoPackageBytes() {
            return ByteString.copyFromUtf8(this.goPackage_);
        }

        /* access modifiers changed from: private */
        public void setGoPackage(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 64;
            this.goPackage_ = value;
        }

        /* access modifiers changed from: private */
        public void clearGoPackage() {
            this.bitField0_ &= -65;
            this.goPackage_ = getDefaultInstance().getGoPackage();
        }

        /* access modifiers changed from: private */
        public void setGoPackageBytes(ByteString value) {
            this.goPackage_ = value.toStringUtf8();
            this.bitField0_ |= 64;
        }

        public boolean hasCcGenericServices() {
            return (this.bitField0_ & 128) != 0;
        }

        public boolean getCcGenericServices() {
            return this.ccGenericServices_;
        }

        /* access modifiers changed from: private */
        public void setCcGenericServices(boolean value) {
            this.bitField0_ |= 128;
            this.ccGenericServices_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCcGenericServices() {
            this.bitField0_ &= -129;
            this.ccGenericServices_ = false;
        }

        public boolean hasJavaGenericServices() {
            return (this.bitField0_ & 256) != 0;
        }

        public boolean getJavaGenericServices() {
            return this.javaGenericServices_;
        }

        /* access modifiers changed from: private */
        public void setJavaGenericServices(boolean value) {
            this.bitField0_ |= 256;
            this.javaGenericServices_ = value;
        }

        /* access modifiers changed from: private */
        public void clearJavaGenericServices() {
            this.bitField0_ &= -257;
            this.javaGenericServices_ = false;
        }

        public boolean hasPyGenericServices() {
            return (this.bitField0_ & 512) != 0;
        }

        public boolean getPyGenericServices() {
            return this.pyGenericServices_;
        }

        /* access modifiers changed from: private */
        public void setPyGenericServices(boolean value) {
            this.bitField0_ |= 512;
            this.pyGenericServices_ = value;
        }

        /* access modifiers changed from: private */
        public void clearPyGenericServices() {
            this.bitField0_ &= -513;
            this.pyGenericServices_ = false;
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 1024) != 0;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        /* access modifiers changed from: private */
        public void setDeprecated(boolean value) {
            this.bitField0_ |= 1024;
            this.deprecated_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeprecated() {
            this.bitField0_ &= -1025;
            this.deprecated_ = false;
        }

        public boolean hasCcEnableArenas() {
            return (this.bitField0_ & 2048) != 0;
        }

        public boolean getCcEnableArenas() {
            return this.ccEnableArenas_;
        }

        /* access modifiers changed from: private */
        public void setCcEnableArenas(boolean value) {
            this.bitField0_ |= 2048;
            this.ccEnableArenas_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCcEnableArenas() {
            this.bitField0_ &= -2049;
            this.ccEnableArenas_ = true;
        }

        public boolean hasObjcClassPrefix() {
            return (this.bitField0_ & 4096) != 0;
        }

        public String getObjcClassPrefix() {
            return this.objcClassPrefix_;
        }

        public ByteString getObjcClassPrefixBytes() {
            return ByteString.copyFromUtf8(this.objcClassPrefix_);
        }

        /* access modifiers changed from: private */
        public void setObjcClassPrefix(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 4096;
            this.objcClassPrefix_ = value;
        }

        /* access modifiers changed from: private */
        public void clearObjcClassPrefix() {
            this.bitField0_ &= -4097;
            this.objcClassPrefix_ = getDefaultInstance().getObjcClassPrefix();
        }

        /* access modifiers changed from: private */
        public void setObjcClassPrefixBytes(ByteString value) {
            this.objcClassPrefix_ = value.toStringUtf8();
            this.bitField0_ |= 4096;
        }

        public boolean hasCsharpNamespace() {
            return (this.bitField0_ & 8192) != 0;
        }

        public String getCsharpNamespace() {
            return this.csharpNamespace_;
        }

        public ByteString getCsharpNamespaceBytes() {
            return ByteString.copyFromUtf8(this.csharpNamespace_);
        }

        /* access modifiers changed from: private */
        public void setCsharpNamespace(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 8192;
            this.csharpNamespace_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCsharpNamespace() {
            this.bitField0_ &= -8193;
            this.csharpNamespace_ = getDefaultInstance().getCsharpNamespace();
        }

        /* access modifiers changed from: private */
        public void setCsharpNamespaceBytes(ByteString value) {
            this.csharpNamespace_ = value.toStringUtf8();
            this.bitField0_ |= 8192;
        }

        public boolean hasSwiftPrefix() {
            return (this.bitField0_ & 16384) != 0;
        }

        public String getSwiftPrefix() {
            return this.swiftPrefix_;
        }

        public ByteString getSwiftPrefixBytes() {
            return ByteString.copyFromUtf8(this.swiftPrefix_);
        }

        /* access modifiers changed from: private */
        public void setSwiftPrefix(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 16384;
            this.swiftPrefix_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSwiftPrefix() {
            this.bitField0_ &= -16385;
            this.swiftPrefix_ = getDefaultInstance().getSwiftPrefix();
        }

        /* access modifiers changed from: private */
        public void setSwiftPrefixBytes(ByteString value) {
            this.swiftPrefix_ = value.toStringUtf8();
            this.bitField0_ |= 16384;
        }

        public boolean hasPhpClassPrefix() {
            return (this.bitField0_ & 32768) != 0;
        }

        public String getPhpClassPrefix() {
            return this.phpClassPrefix_;
        }

        public ByteString getPhpClassPrefixBytes() {
            return ByteString.copyFromUtf8(this.phpClassPrefix_);
        }

        /* access modifiers changed from: private */
        public void setPhpClassPrefix(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 32768;
            this.phpClassPrefix_ = value;
        }

        /* access modifiers changed from: private */
        public void clearPhpClassPrefix() {
            this.bitField0_ &= -32769;
            this.phpClassPrefix_ = getDefaultInstance().getPhpClassPrefix();
        }

        /* access modifiers changed from: private */
        public void setPhpClassPrefixBytes(ByteString value) {
            this.phpClassPrefix_ = value.toStringUtf8();
            this.bitField0_ |= 32768;
        }

        public boolean hasPhpNamespace() {
            return (this.bitField0_ & 65536) != 0;
        }

        public String getPhpNamespace() {
            return this.phpNamespace_;
        }

        public ByteString getPhpNamespaceBytes() {
            return ByteString.copyFromUtf8(this.phpNamespace_);
        }

        /* access modifiers changed from: private */
        public void setPhpNamespace(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 65536;
            this.phpNamespace_ = value;
        }

        /* access modifiers changed from: private */
        public void clearPhpNamespace() {
            this.bitField0_ &= -65537;
            this.phpNamespace_ = getDefaultInstance().getPhpNamespace();
        }

        /* access modifiers changed from: private */
        public void setPhpNamespaceBytes(ByteString value) {
            this.phpNamespace_ = value.toStringUtf8();
            this.bitField0_ |= 65536;
        }

        public boolean hasPhpMetadataNamespace() {
            return (this.bitField0_ & 131072) != 0;
        }

        public String getPhpMetadataNamespace() {
            return this.phpMetadataNamespace_;
        }

        public ByteString getPhpMetadataNamespaceBytes() {
            return ByteString.copyFromUtf8(this.phpMetadataNamespace_);
        }

        /* access modifiers changed from: private */
        public void setPhpMetadataNamespace(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 131072;
            this.phpMetadataNamespace_ = value;
        }

        /* access modifiers changed from: private */
        public void clearPhpMetadataNamespace() {
            this.bitField0_ &= -131073;
            this.phpMetadataNamespace_ = getDefaultInstance().getPhpMetadataNamespace();
        }

        /* access modifiers changed from: private */
        public void setPhpMetadataNamespaceBytes(ByteString value) {
            this.phpMetadataNamespace_ = value.toStringUtf8();
            this.bitField0_ |= 131072;
        }

        public boolean hasRubyPackage() {
            return (this.bitField0_ & 262144) != 0;
        }

        public String getRubyPackage() {
            return this.rubyPackage_;
        }

        public ByteString getRubyPackageBytes() {
            return ByteString.copyFromUtf8(this.rubyPackage_);
        }

        /* access modifiers changed from: private */
        public void setRubyPackage(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 262144;
            this.rubyPackage_ = value;
        }

        /* access modifiers changed from: private */
        public void clearRubyPackage() {
            this.bitField0_ &= -262145;
            this.rubyPackage_ = getDefaultInstance().getRubyPackage();
        }

        /* access modifiers changed from: private */
        public void setRubyPackageBytes(ByteString value) {
            this.rubyPackage_ = value.toStringUtf8();
            this.bitField0_ |= 262144;
        }

        public boolean hasFeatures() {
            return (this.bitField0_ & 524288) != 0;
        }

        public FeatureSet getFeatures() {
            return this.features_ == null ? FeatureSet.getDefaultInstance() : this.features_;
        }

        /* access modifiers changed from: private */
        public void setFeatures(FeatureSet value) {
            value.getClass();
            this.features_ = value;
            this.bitField0_ |= 524288;
        }

        /* access modifiers changed from: private */
        public void mergeFeatures(FeatureSet value) {
            value.getClass();
            if (this.features_ == null || this.features_ == FeatureSet.getDefaultInstance()) {
                this.features_ = value;
            } else {
                this.features_ = (FeatureSet) ((FeatureSet.Builder) FeatureSet.newBuilder(this.features_).mergeFrom(value)).buildPartial();
            }
            this.bitField0_ |= 524288;
        }

        /* access modifiers changed from: private */
        public void clearFeatures() {
            this.features_ = null;
            this.bitField0_ &= -524289;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return (UninterpretedOption) this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
        }

        private void ensureUninterpretedOptionIsMutable() {
            Internal.ProtobufList<UninterpretedOption> tmp = this.uninterpretedOption_;
            if (!tmp.isModifiable()) {
                this.uninterpretedOption_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(value);
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
            ensureUninterpretedOptionIsMutable();
            AbstractMessageLite.addAll(values, this.uninterpretedOption_);
        }

        /* access modifiers changed from: private */
        public void clearUninterpretedOption() {
            this.uninterpretedOption_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeUninterpretedOption(int index) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.remove(index);
        }

        public static FileOptions parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (FileOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FileOptions parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FileOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FileOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FileOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FileOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FileOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FileOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FileOptions parseFrom(InputStream input) throws IOException {
            return (FileOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FileOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FileOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (FileOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static FileOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FileOptions parseFrom(CodedInputStream input) throws IOException {
            return (FileOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FileOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(FileOptions prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<FileOptions, Builder> implements FileOptionsOrBuilder {
            private Builder() {
                super(FileOptions.DEFAULT_INSTANCE);
            }

            public boolean hasJavaPackage() {
                return ((FileOptions) this.instance).hasJavaPackage();
            }

            public String getJavaPackage() {
                return ((FileOptions) this.instance).getJavaPackage();
            }

            public ByteString getJavaPackageBytes() {
                return ((FileOptions) this.instance).getJavaPackageBytes();
            }

            public Builder setJavaPackage(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaPackage(value);
                return this;
            }

            public Builder clearJavaPackage() {
                copyOnWrite();
                ((FileOptions) this.instance).clearJavaPackage();
                return this;
            }

            public Builder setJavaPackageBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaPackageBytes(value);
                return this;
            }

            public boolean hasJavaOuterClassname() {
                return ((FileOptions) this.instance).hasJavaOuterClassname();
            }

            public String getJavaOuterClassname() {
                return ((FileOptions) this.instance).getJavaOuterClassname();
            }

            public ByteString getJavaOuterClassnameBytes() {
                return ((FileOptions) this.instance).getJavaOuterClassnameBytes();
            }

            public Builder setJavaOuterClassname(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaOuterClassname(value);
                return this;
            }

            public Builder clearJavaOuterClassname() {
                copyOnWrite();
                ((FileOptions) this.instance).clearJavaOuterClassname();
                return this;
            }

            public Builder setJavaOuterClassnameBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaOuterClassnameBytes(value);
                return this;
            }

            public boolean hasJavaMultipleFiles() {
                return ((FileOptions) this.instance).hasJavaMultipleFiles();
            }

            public boolean getJavaMultipleFiles() {
                return ((FileOptions) this.instance).getJavaMultipleFiles();
            }

            public Builder setJavaMultipleFiles(boolean value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaMultipleFiles(value);
                return this;
            }

            public Builder clearJavaMultipleFiles() {
                copyOnWrite();
                ((FileOptions) this.instance).clearJavaMultipleFiles();
                return this;
            }

            @Deprecated
            public boolean hasJavaGenerateEqualsAndHash() {
                return ((FileOptions) this.instance).hasJavaGenerateEqualsAndHash();
            }

            @Deprecated
            public boolean getJavaGenerateEqualsAndHash() {
                return ((FileOptions) this.instance).getJavaGenerateEqualsAndHash();
            }

            @Deprecated
            public Builder setJavaGenerateEqualsAndHash(boolean value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaGenerateEqualsAndHash(value);
                return this;
            }

            @Deprecated
            public Builder clearJavaGenerateEqualsAndHash() {
                copyOnWrite();
                ((FileOptions) this.instance).clearJavaGenerateEqualsAndHash();
                return this;
            }

            public boolean hasJavaStringCheckUtf8() {
                return ((FileOptions) this.instance).hasJavaStringCheckUtf8();
            }

            public boolean getJavaStringCheckUtf8() {
                return ((FileOptions) this.instance).getJavaStringCheckUtf8();
            }

            public Builder setJavaStringCheckUtf8(boolean value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaStringCheckUtf8(value);
                return this;
            }

            public Builder clearJavaStringCheckUtf8() {
                copyOnWrite();
                ((FileOptions) this.instance).clearJavaStringCheckUtf8();
                return this;
            }

            public boolean hasOptimizeFor() {
                return ((FileOptions) this.instance).hasOptimizeFor();
            }

            public OptimizeMode getOptimizeFor() {
                return ((FileOptions) this.instance).getOptimizeFor();
            }

            public Builder setOptimizeFor(OptimizeMode value) {
                copyOnWrite();
                ((FileOptions) this.instance).setOptimizeFor(value);
                return this;
            }

            public Builder clearOptimizeFor() {
                copyOnWrite();
                ((FileOptions) this.instance).clearOptimizeFor();
                return this;
            }

            public boolean hasGoPackage() {
                return ((FileOptions) this.instance).hasGoPackage();
            }

            public String getGoPackage() {
                return ((FileOptions) this.instance).getGoPackage();
            }

            public ByteString getGoPackageBytes() {
                return ((FileOptions) this.instance).getGoPackageBytes();
            }

            public Builder setGoPackage(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setGoPackage(value);
                return this;
            }

            public Builder clearGoPackage() {
                copyOnWrite();
                ((FileOptions) this.instance).clearGoPackage();
                return this;
            }

            public Builder setGoPackageBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setGoPackageBytes(value);
                return this;
            }

            public boolean hasCcGenericServices() {
                return ((FileOptions) this.instance).hasCcGenericServices();
            }

            public boolean getCcGenericServices() {
                return ((FileOptions) this.instance).getCcGenericServices();
            }

            public Builder setCcGenericServices(boolean value) {
                copyOnWrite();
                ((FileOptions) this.instance).setCcGenericServices(value);
                return this;
            }

            public Builder clearCcGenericServices() {
                copyOnWrite();
                ((FileOptions) this.instance).clearCcGenericServices();
                return this;
            }

            public boolean hasJavaGenericServices() {
                return ((FileOptions) this.instance).hasJavaGenericServices();
            }

            public boolean getJavaGenericServices() {
                return ((FileOptions) this.instance).getJavaGenericServices();
            }

            public Builder setJavaGenericServices(boolean value) {
                copyOnWrite();
                ((FileOptions) this.instance).setJavaGenericServices(value);
                return this;
            }

            public Builder clearJavaGenericServices() {
                copyOnWrite();
                ((FileOptions) this.instance).clearJavaGenericServices();
                return this;
            }

            public boolean hasPyGenericServices() {
                return ((FileOptions) this.instance).hasPyGenericServices();
            }

            public boolean getPyGenericServices() {
                return ((FileOptions) this.instance).getPyGenericServices();
            }

            public Builder setPyGenericServices(boolean value) {
                copyOnWrite();
                ((FileOptions) this.instance).setPyGenericServices(value);
                return this;
            }

            public Builder clearPyGenericServices() {
                copyOnWrite();
                ((FileOptions) this.instance).clearPyGenericServices();
                return this;
            }

            public boolean hasDeprecated() {
                return ((FileOptions) this.instance).hasDeprecated();
            }

            public boolean getDeprecated() {
                return ((FileOptions) this.instance).getDeprecated();
            }

            public Builder setDeprecated(boolean value) {
                copyOnWrite();
                ((FileOptions) this.instance).setDeprecated(value);
                return this;
            }

            public Builder clearDeprecated() {
                copyOnWrite();
                ((FileOptions) this.instance).clearDeprecated();
                return this;
            }

            public boolean hasCcEnableArenas() {
                return ((FileOptions) this.instance).hasCcEnableArenas();
            }

            public boolean getCcEnableArenas() {
                return ((FileOptions) this.instance).getCcEnableArenas();
            }

            public Builder setCcEnableArenas(boolean value) {
                copyOnWrite();
                ((FileOptions) this.instance).setCcEnableArenas(value);
                return this;
            }

            public Builder clearCcEnableArenas() {
                copyOnWrite();
                ((FileOptions) this.instance).clearCcEnableArenas();
                return this;
            }

            public boolean hasObjcClassPrefix() {
                return ((FileOptions) this.instance).hasObjcClassPrefix();
            }

            public String getObjcClassPrefix() {
                return ((FileOptions) this.instance).getObjcClassPrefix();
            }

            public ByteString getObjcClassPrefixBytes() {
                return ((FileOptions) this.instance).getObjcClassPrefixBytes();
            }

            public Builder setObjcClassPrefix(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setObjcClassPrefix(value);
                return this;
            }

            public Builder clearObjcClassPrefix() {
                copyOnWrite();
                ((FileOptions) this.instance).clearObjcClassPrefix();
                return this;
            }

            public Builder setObjcClassPrefixBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setObjcClassPrefixBytes(value);
                return this;
            }

            public boolean hasCsharpNamespace() {
                return ((FileOptions) this.instance).hasCsharpNamespace();
            }

            public String getCsharpNamespace() {
                return ((FileOptions) this.instance).getCsharpNamespace();
            }

            public ByteString getCsharpNamespaceBytes() {
                return ((FileOptions) this.instance).getCsharpNamespaceBytes();
            }

            public Builder setCsharpNamespace(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setCsharpNamespace(value);
                return this;
            }

            public Builder clearCsharpNamespace() {
                copyOnWrite();
                ((FileOptions) this.instance).clearCsharpNamespace();
                return this;
            }

            public Builder setCsharpNamespaceBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setCsharpNamespaceBytes(value);
                return this;
            }

            public boolean hasSwiftPrefix() {
                return ((FileOptions) this.instance).hasSwiftPrefix();
            }

            public String getSwiftPrefix() {
                return ((FileOptions) this.instance).getSwiftPrefix();
            }

            public ByteString getSwiftPrefixBytes() {
                return ((FileOptions) this.instance).getSwiftPrefixBytes();
            }

            public Builder setSwiftPrefix(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setSwiftPrefix(value);
                return this;
            }

            public Builder clearSwiftPrefix() {
                copyOnWrite();
                ((FileOptions) this.instance).clearSwiftPrefix();
                return this;
            }

            public Builder setSwiftPrefixBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setSwiftPrefixBytes(value);
                return this;
            }

            public boolean hasPhpClassPrefix() {
                return ((FileOptions) this.instance).hasPhpClassPrefix();
            }

            public String getPhpClassPrefix() {
                return ((FileOptions) this.instance).getPhpClassPrefix();
            }

            public ByteString getPhpClassPrefixBytes() {
                return ((FileOptions) this.instance).getPhpClassPrefixBytes();
            }

            public Builder setPhpClassPrefix(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setPhpClassPrefix(value);
                return this;
            }

            public Builder clearPhpClassPrefix() {
                copyOnWrite();
                ((FileOptions) this.instance).clearPhpClassPrefix();
                return this;
            }

            public Builder setPhpClassPrefixBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setPhpClassPrefixBytes(value);
                return this;
            }

            public boolean hasPhpNamespace() {
                return ((FileOptions) this.instance).hasPhpNamespace();
            }

            public String getPhpNamespace() {
                return ((FileOptions) this.instance).getPhpNamespace();
            }

            public ByteString getPhpNamespaceBytes() {
                return ((FileOptions) this.instance).getPhpNamespaceBytes();
            }

            public Builder setPhpNamespace(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setPhpNamespace(value);
                return this;
            }

            public Builder clearPhpNamespace() {
                copyOnWrite();
                ((FileOptions) this.instance).clearPhpNamespace();
                return this;
            }

            public Builder setPhpNamespaceBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setPhpNamespaceBytes(value);
                return this;
            }

            public boolean hasPhpMetadataNamespace() {
                return ((FileOptions) this.instance).hasPhpMetadataNamespace();
            }

            public String getPhpMetadataNamespace() {
                return ((FileOptions) this.instance).getPhpMetadataNamespace();
            }

            public ByteString getPhpMetadataNamespaceBytes() {
                return ((FileOptions) this.instance).getPhpMetadataNamespaceBytes();
            }

            public Builder setPhpMetadataNamespace(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setPhpMetadataNamespace(value);
                return this;
            }

            public Builder clearPhpMetadataNamespace() {
                copyOnWrite();
                ((FileOptions) this.instance).clearPhpMetadataNamespace();
                return this;
            }

            public Builder setPhpMetadataNamespaceBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setPhpMetadataNamespaceBytes(value);
                return this;
            }

            public boolean hasRubyPackage() {
                return ((FileOptions) this.instance).hasRubyPackage();
            }

            public String getRubyPackage() {
                return ((FileOptions) this.instance).getRubyPackage();
            }

            public ByteString getRubyPackageBytes() {
                return ((FileOptions) this.instance).getRubyPackageBytes();
            }

            public Builder setRubyPackage(String value) {
                copyOnWrite();
                ((FileOptions) this.instance).setRubyPackage(value);
                return this;
            }

            public Builder clearRubyPackage() {
                copyOnWrite();
                ((FileOptions) this.instance).clearRubyPackage();
                return this;
            }

            public Builder setRubyPackageBytes(ByteString value) {
                copyOnWrite();
                ((FileOptions) this.instance).setRubyPackageBytes(value);
                return this;
            }

            public boolean hasFeatures() {
                return ((FileOptions) this.instance).hasFeatures();
            }

            public FeatureSet getFeatures() {
                return ((FileOptions) this.instance).getFeatures();
            }

            public Builder setFeatures(FeatureSet value) {
                copyOnWrite();
                ((FileOptions) this.instance).setFeatures(value);
                return this;
            }

            public Builder setFeatures(FeatureSet.Builder builderForValue) {
                copyOnWrite();
                ((FileOptions) this.instance).setFeatures((FeatureSet) builderForValue.build());
                return this;
            }

            public Builder mergeFeatures(FeatureSet value) {
                copyOnWrite();
                ((FileOptions) this.instance).mergeFeatures(value);
                return this;
            }

            public Builder clearFeatures() {
                copyOnWrite();
                ((FileOptions) this.instance).clearFeatures();
                return this;
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                return Collections.unmodifiableList(((FileOptions) this.instance).getUninterpretedOptionList());
            }

            public int getUninterpretedOptionCount() {
                return ((FileOptions) this.instance).getUninterpretedOptionCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                return ((FileOptions) this.instance).getUninterpretedOption(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((FileOptions) this.instance).setUninterpretedOption(index, value);
                return this;
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((FileOptions) this.instance).setUninterpretedOption(index, (UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                copyOnWrite();
                ((FileOptions) this.instance).addUninterpretedOption(value);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((FileOptions) this.instance).addUninterpretedOption(index, value);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((FileOptions) this.instance).addUninterpretedOption((UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((FileOptions) this.instance).addUninterpretedOption(index, (UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                copyOnWrite();
                ((FileOptions) this.instance).addAllUninterpretedOption(values);
                return this;
            }

            public Builder clearUninterpretedOption() {
                copyOnWrite();
                ((FileOptions) this.instance).clearUninterpretedOption();
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                copyOnWrite();
                ((FileOptions) this.instance).removeUninterpretedOption(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new FileOptions();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0015\u0000\u0001\u0001ϧ\u0015\u0000\u0001\u0002\u0001ဈ\u0000\bဈ\u0001\t᠌\u0005\nဇ\u0002\u000bဈ\u0006\u0010ဇ\u0007\u0011ဇ\b\u0012ဇ\t\u0014ဇ\u0003\u0017ဇ\n\u001bဇ\u0004\u001fဇ\u000b$ဈ\f%ဈ\r'ဈ\u000e(ဈ\u000f)ဈ\u0010,ဈ\u0011-ဈ\u00122ᐉ\u0013ϧЛ", new Object[]{"bitField0_", "javaPackage_", "javaOuterClassname_", "optimizeFor_", OptimizeMode.internalGetVerifier(), "javaMultipleFiles_", "goPackage_", "ccGenericServices_", "javaGenericServices_", "pyGenericServices_", "javaGenerateEqualsAndHash_", "deprecated_", "javaStringCheckUtf8_", "ccEnableArenas_", "objcClassPrefix_", "csharpNamespace_", "swiftPrefix_", "phpClassPrefix_", "phpNamespace_", "phpMetadataNamespace_", "rubyPackage_", "features_", "uninterpretedOption_", UninterpretedOption.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<FileOptions> parser = PARSER;
                    if (parser == null) {
                        synchronized (FileOptions.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            FileOptions defaultInstance = new FileOptions();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(FileOptions.class, defaultInstance);
        }

        public static FileOptions getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FileOptions> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class MessageOptions extends GeneratedMessageLite.ExtendableMessage<MessageOptions, Builder> implements MessageOptionsOrBuilder {
        /* access modifiers changed from: private */
        public static final MessageOptions DEFAULT_INSTANCE;
        public static final int DEPRECATED_FIELD_NUMBER = 3;
        public static final int DEPRECATED_LEGACY_JSON_FIELD_CONFLICTS_FIELD_NUMBER = 11;
        public static final int FEATURES_FIELD_NUMBER = 12;
        public static final int MAP_ENTRY_FIELD_NUMBER = 7;
        public static final int MESSAGE_SET_WIRE_FORMAT_FIELD_NUMBER = 1;
        public static final int NO_STANDARD_DESCRIPTOR_ACCESSOR_FIELD_NUMBER = 2;
        private static volatile Parser<MessageOptions> PARSER = null;
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        private int bitField0_;
        private boolean deprecatedLegacyJsonFieldConflicts_;
        private boolean deprecated_;
        private FeatureSet features_;
        private boolean mapEntry_;
        private byte memoizedIsInitialized = 2;
        private boolean messageSetWireFormat_;
        private boolean noStandardDescriptorAccessor_;
        private Internal.ProtobufList<UninterpretedOption> uninterpretedOption_ = emptyProtobufList();

        private MessageOptions() {
        }

        public boolean hasMessageSetWireFormat() {
            return (this.bitField0_ & 1) != 0;
        }

        public boolean getMessageSetWireFormat() {
            return this.messageSetWireFormat_;
        }

        /* access modifiers changed from: private */
        public void setMessageSetWireFormat(boolean value) {
            this.bitField0_ |= 1;
            this.messageSetWireFormat_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMessageSetWireFormat() {
            this.bitField0_ &= -2;
            this.messageSetWireFormat_ = false;
        }

        public boolean hasNoStandardDescriptorAccessor() {
            return (this.bitField0_ & 2) != 0;
        }

        public boolean getNoStandardDescriptorAccessor() {
            return this.noStandardDescriptorAccessor_;
        }

        /* access modifiers changed from: private */
        public void setNoStandardDescriptorAccessor(boolean value) {
            this.bitField0_ |= 2;
            this.noStandardDescriptorAccessor_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNoStandardDescriptorAccessor() {
            this.bitField0_ &= -3;
            this.noStandardDescriptorAccessor_ = false;
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 4) != 0;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        /* access modifiers changed from: private */
        public void setDeprecated(boolean value) {
            this.bitField0_ |= 4;
            this.deprecated_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeprecated() {
            this.bitField0_ &= -5;
            this.deprecated_ = false;
        }

        public boolean hasMapEntry() {
            return (this.bitField0_ & 8) != 0;
        }

        public boolean getMapEntry() {
            return this.mapEntry_;
        }

        /* access modifiers changed from: private */
        public void setMapEntry(boolean value) {
            this.bitField0_ |= 8;
            this.mapEntry_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMapEntry() {
            this.bitField0_ &= -9;
            this.mapEntry_ = false;
        }

        @Deprecated
        public boolean hasDeprecatedLegacyJsonFieldConflicts() {
            return (this.bitField0_ & 16) != 0;
        }

        @Deprecated
        public boolean getDeprecatedLegacyJsonFieldConflicts() {
            return this.deprecatedLegacyJsonFieldConflicts_;
        }

        /* access modifiers changed from: private */
        public void setDeprecatedLegacyJsonFieldConflicts(boolean value) {
            this.bitField0_ |= 16;
            this.deprecatedLegacyJsonFieldConflicts_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeprecatedLegacyJsonFieldConflicts() {
            this.bitField0_ &= -17;
            this.deprecatedLegacyJsonFieldConflicts_ = false;
        }

        public boolean hasFeatures() {
            return (this.bitField0_ & 32) != 0;
        }

        public FeatureSet getFeatures() {
            return this.features_ == null ? FeatureSet.getDefaultInstance() : this.features_;
        }

        /* access modifiers changed from: private */
        public void setFeatures(FeatureSet value) {
            value.getClass();
            this.features_ = value;
            this.bitField0_ |= 32;
        }

        /* access modifiers changed from: private */
        public void mergeFeatures(FeatureSet value) {
            value.getClass();
            if (this.features_ == null || this.features_ == FeatureSet.getDefaultInstance()) {
                this.features_ = value;
            } else {
                this.features_ = (FeatureSet) ((FeatureSet.Builder) FeatureSet.newBuilder(this.features_).mergeFrom(value)).buildPartial();
            }
            this.bitField0_ |= 32;
        }

        /* access modifiers changed from: private */
        public void clearFeatures() {
            this.features_ = null;
            this.bitField0_ &= -33;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return (UninterpretedOption) this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
        }

        private void ensureUninterpretedOptionIsMutable() {
            Internal.ProtobufList<UninterpretedOption> tmp = this.uninterpretedOption_;
            if (!tmp.isModifiable()) {
                this.uninterpretedOption_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(value);
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
            ensureUninterpretedOptionIsMutable();
            AbstractMessageLite.addAll(values, this.uninterpretedOption_);
        }

        /* access modifiers changed from: private */
        public void clearUninterpretedOption() {
            this.uninterpretedOption_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeUninterpretedOption(int index) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.remove(index);
        }

        public static MessageOptions parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (MessageOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MessageOptions parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MessageOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MessageOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MessageOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MessageOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MessageOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MessageOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MessageOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MessageOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MessageOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MessageOptions parseFrom(InputStream input) throws IOException {
            return (MessageOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MessageOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MessageOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MessageOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (MessageOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static MessageOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MessageOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MessageOptions parseFrom(CodedInputStream input) throws IOException {
            return (MessageOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MessageOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MessageOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(MessageOptions prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<MessageOptions, Builder> implements MessageOptionsOrBuilder {
            private Builder() {
                super(MessageOptions.DEFAULT_INSTANCE);
            }

            public boolean hasMessageSetWireFormat() {
                return ((MessageOptions) this.instance).hasMessageSetWireFormat();
            }

            public boolean getMessageSetWireFormat() {
                return ((MessageOptions) this.instance).getMessageSetWireFormat();
            }

            public Builder setMessageSetWireFormat(boolean value) {
                copyOnWrite();
                ((MessageOptions) this.instance).setMessageSetWireFormat(value);
                return this;
            }

            public Builder clearMessageSetWireFormat() {
                copyOnWrite();
                ((MessageOptions) this.instance).clearMessageSetWireFormat();
                return this;
            }

            public boolean hasNoStandardDescriptorAccessor() {
                return ((MessageOptions) this.instance).hasNoStandardDescriptorAccessor();
            }

            public boolean getNoStandardDescriptorAccessor() {
                return ((MessageOptions) this.instance).getNoStandardDescriptorAccessor();
            }

            public Builder setNoStandardDescriptorAccessor(boolean value) {
                copyOnWrite();
                ((MessageOptions) this.instance).setNoStandardDescriptorAccessor(value);
                return this;
            }

            public Builder clearNoStandardDescriptorAccessor() {
                copyOnWrite();
                ((MessageOptions) this.instance).clearNoStandardDescriptorAccessor();
                return this;
            }

            public boolean hasDeprecated() {
                return ((MessageOptions) this.instance).hasDeprecated();
            }

            public boolean getDeprecated() {
                return ((MessageOptions) this.instance).getDeprecated();
            }

            public Builder setDeprecated(boolean value) {
                copyOnWrite();
                ((MessageOptions) this.instance).setDeprecated(value);
                return this;
            }

            public Builder clearDeprecated() {
                copyOnWrite();
                ((MessageOptions) this.instance).clearDeprecated();
                return this;
            }

            public boolean hasMapEntry() {
                return ((MessageOptions) this.instance).hasMapEntry();
            }

            public boolean getMapEntry() {
                return ((MessageOptions) this.instance).getMapEntry();
            }

            public Builder setMapEntry(boolean value) {
                copyOnWrite();
                ((MessageOptions) this.instance).setMapEntry(value);
                return this;
            }

            public Builder clearMapEntry() {
                copyOnWrite();
                ((MessageOptions) this.instance).clearMapEntry();
                return this;
            }

            @Deprecated
            public boolean hasDeprecatedLegacyJsonFieldConflicts() {
                return ((MessageOptions) this.instance).hasDeprecatedLegacyJsonFieldConflicts();
            }

            @Deprecated
            public boolean getDeprecatedLegacyJsonFieldConflicts() {
                return ((MessageOptions) this.instance).getDeprecatedLegacyJsonFieldConflicts();
            }

            @Deprecated
            public Builder setDeprecatedLegacyJsonFieldConflicts(boolean value) {
                copyOnWrite();
                ((MessageOptions) this.instance).setDeprecatedLegacyJsonFieldConflicts(value);
                return this;
            }

            @Deprecated
            public Builder clearDeprecatedLegacyJsonFieldConflicts() {
                copyOnWrite();
                ((MessageOptions) this.instance).clearDeprecatedLegacyJsonFieldConflicts();
                return this;
            }

            public boolean hasFeatures() {
                return ((MessageOptions) this.instance).hasFeatures();
            }

            public FeatureSet getFeatures() {
                return ((MessageOptions) this.instance).getFeatures();
            }

            public Builder setFeatures(FeatureSet value) {
                copyOnWrite();
                ((MessageOptions) this.instance).setFeatures(value);
                return this;
            }

            public Builder setFeatures(FeatureSet.Builder builderForValue) {
                copyOnWrite();
                ((MessageOptions) this.instance).setFeatures((FeatureSet) builderForValue.build());
                return this;
            }

            public Builder mergeFeatures(FeatureSet value) {
                copyOnWrite();
                ((MessageOptions) this.instance).mergeFeatures(value);
                return this;
            }

            public Builder clearFeatures() {
                copyOnWrite();
                ((MessageOptions) this.instance).clearFeatures();
                return this;
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                return Collections.unmodifiableList(((MessageOptions) this.instance).getUninterpretedOptionList());
            }

            public int getUninterpretedOptionCount() {
                return ((MessageOptions) this.instance).getUninterpretedOptionCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                return ((MessageOptions) this.instance).getUninterpretedOption(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((MessageOptions) this.instance).setUninterpretedOption(index, value);
                return this;
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((MessageOptions) this.instance).setUninterpretedOption(index, (UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                copyOnWrite();
                ((MessageOptions) this.instance).addUninterpretedOption(value);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((MessageOptions) this.instance).addUninterpretedOption(index, value);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((MessageOptions) this.instance).addUninterpretedOption((UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((MessageOptions) this.instance).addUninterpretedOption(index, (UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                copyOnWrite();
                ((MessageOptions) this.instance).addAllUninterpretedOption(values);
                return this;
            }

            public Builder clearUninterpretedOption() {
                copyOnWrite();
                ((MessageOptions) this.instance).clearUninterpretedOption();
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                copyOnWrite();
                ((MessageOptions) this.instance).removeUninterpretedOption(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new MessageOptions();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0007\u0000\u0001\u0001ϧ\u0007\u0000\u0001\u0002\u0001ဇ\u0000\u0002ဇ\u0001\u0003ဇ\u0002\u0007ဇ\u0003\u000bဇ\u0004\fᐉ\u0005ϧЛ", new Object[]{"bitField0_", "messageSetWireFormat_", "noStandardDescriptorAccessor_", "deprecated_", "mapEntry_", "deprecatedLegacyJsonFieldConflicts_", "features_", "uninterpretedOption_", UninterpretedOption.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<MessageOptions> parser = PARSER;
                    if (parser == null) {
                        synchronized (MessageOptions.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            MessageOptions defaultInstance = new MessageOptions();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(MessageOptions.class, defaultInstance);
        }

        public static MessageOptions getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MessageOptions> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class FieldOptions extends GeneratedMessageLite.ExtendableMessage<FieldOptions, Builder> implements FieldOptionsOrBuilder {
        public static final int CTYPE_FIELD_NUMBER = 1;
        public static final int DEBUG_REDACT_FIELD_NUMBER = 16;
        /* access modifiers changed from: private */
        public static final FieldOptions DEFAULT_INSTANCE;
        public static final int DEPRECATED_FIELD_NUMBER = 3;
        public static final int EDITION_DEFAULTS_FIELD_NUMBER = 20;
        public static final int FEATURES_FIELD_NUMBER = 21;
        public static final int FEATURE_SUPPORT_FIELD_NUMBER = 22;
        public static final int JSTYPE_FIELD_NUMBER = 6;
        public static final int LAZY_FIELD_NUMBER = 5;
        public static final int PACKED_FIELD_NUMBER = 2;
        private static volatile Parser<FieldOptions> PARSER = null;
        public static final int RETENTION_FIELD_NUMBER = 17;
        public static final int TARGETS_FIELD_NUMBER = 19;
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        public static final int UNVERIFIED_LAZY_FIELD_NUMBER = 15;
        public static final int WEAK_FIELD_NUMBER = 10;
        private static final Internal.IntListAdapter.IntConverter<OptionTargetType> targets_converter_ = new Internal.IntListAdapter.IntConverter<OptionTargetType>() {
            public OptionTargetType convert(int from) {
                OptionTargetType result = OptionTargetType.forNumber(from);
                return result == null ? OptionTargetType.TARGET_TYPE_UNKNOWN : result;
            }
        };
        private int bitField0_;
        private int ctype_;
        private boolean debugRedact_;
        private boolean deprecated_;
        private Internal.ProtobufList<EditionDefault> editionDefaults_ = emptyProtobufList();
        private FeatureSupport featureSupport_;
        private FeatureSet features_;
        private int jstype_;
        private boolean lazy_;
        private byte memoizedIsInitialized = 2;
        private boolean packed_;
        private int retention_;
        private Internal.IntList targets_ = emptyIntList();
        private Internal.ProtobufList<UninterpretedOption> uninterpretedOption_ = emptyProtobufList();
        private boolean unverifiedLazy_;
        private boolean weak_;

        public interface EditionDefaultOrBuilder extends MessageLiteOrBuilder {
            Edition getEdition();

            String getValue();

            ByteString getValueBytes();

            boolean hasEdition();

            boolean hasValue();
        }

        public interface FeatureSupportOrBuilder extends MessageLiteOrBuilder {
            String getDeprecationWarning();

            ByteString getDeprecationWarningBytes();

            Edition getEditionDeprecated();

            Edition getEditionIntroduced();

            Edition getEditionRemoved();

            boolean hasDeprecationWarning();

            boolean hasEditionDeprecated();

            boolean hasEditionIntroduced();

            boolean hasEditionRemoved();
        }

        private FieldOptions() {
        }

        public enum CType implements Internal.EnumLite {
            STRING(0),
            CORD(1),
            STRING_PIECE(2);
            
            public static final int CORD_VALUE = 1;
            public static final int STRING_PIECE_VALUE = 2;
            public static final int STRING_VALUE = 0;
            private static final Internal.EnumLiteMap<CType> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new Internal.EnumLiteMap<CType>() {
                    public CType findValueByNumber(int number) {
                        return CType.forNumber(number);
                    }
                };
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static CType valueOf(int value2) {
                return forNumber(value2);
            }

            public static CType forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return STRING;
                    case 1:
                        return CORD;
                    case 2:
                        return STRING_PIECE;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<CType> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return CTypeVerifier.INSTANCE;
            }

            private static final class CTypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = null;

                private CTypeVerifier() {
                }

                static {
                    INSTANCE = new CTypeVerifier();
                }

                public boolean isInRange(int number) {
                    return CType.forNumber(number) != null;
                }
            }

            private CType(int value2) {
                this.value = value2;
            }
        }

        public enum JSType implements Internal.EnumLite {
            JS_NORMAL(0),
            JS_STRING(1),
            JS_NUMBER(2);
            
            public static final int JS_NORMAL_VALUE = 0;
            public static final int JS_NUMBER_VALUE = 2;
            public static final int JS_STRING_VALUE = 1;
            private static final Internal.EnumLiteMap<JSType> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new Internal.EnumLiteMap<JSType>() {
                    public JSType findValueByNumber(int number) {
                        return JSType.forNumber(number);
                    }
                };
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static JSType valueOf(int value2) {
                return forNumber(value2);
            }

            public static JSType forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return JS_NORMAL;
                    case 1:
                        return JS_STRING;
                    case 2:
                        return JS_NUMBER;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<JSType> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return JSTypeVerifier.INSTANCE;
            }

            private static final class JSTypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = null;

                private JSTypeVerifier() {
                }

                static {
                    INSTANCE = new JSTypeVerifier();
                }

                public boolean isInRange(int number) {
                    return JSType.forNumber(number) != null;
                }
            }

            private JSType(int value2) {
                this.value = value2;
            }
        }

        public enum OptionRetention implements Internal.EnumLite {
            RETENTION_UNKNOWN(0),
            RETENTION_RUNTIME(1),
            RETENTION_SOURCE(2);
            
            public static final int RETENTION_RUNTIME_VALUE = 1;
            public static final int RETENTION_SOURCE_VALUE = 2;
            public static final int RETENTION_UNKNOWN_VALUE = 0;
            private static final Internal.EnumLiteMap<OptionRetention> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new Internal.EnumLiteMap<OptionRetention>() {
                    public OptionRetention findValueByNumber(int number) {
                        return OptionRetention.forNumber(number);
                    }
                };
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static OptionRetention valueOf(int value2) {
                return forNumber(value2);
            }

            public static OptionRetention forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return RETENTION_UNKNOWN;
                    case 1:
                        return RETENTION_RUNTIME;
                    case 2:
                        return RETENTION_SOURCE;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<OptionRetention> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return OptionRetentionVerifier.INSTANCE;
            }

            private static final class OptionRetentionVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = null;

                private OptionRetentionVerifier() {
                }

                static {
                    INSTANCE = new OptionRetentionVerifier();
                }

                public boolean isInRange(int number) {
                    return OptionRetention.forNumber(number) != null;
                }
            }

            private OptionRetention(int value2) {
                this.value = value2;
            }
        }

        public enum OptionTargetType implements Internal.EnumLite {
            TARGET_TYPE_UNKNOWN(0),
            TARGET_TYPE_FILE(1),
            TARGET_TYPE_EXTENSION_RANGE(2),
            TARGET_TYPE_MESSAGE(3),
            TARGET_TYPE_FIELD(4),
            TARGET_TYPE_ONEOF(5),
            TARGET_TYPE_ENUM(6),
            TARGET_TYPE_ENUM_ENTRY(7),
            TARGET_TYPE_SERVICE(8),
            TARGET_TYPE_METHOD(9);
            
            public static final int TARGET_TYPE_ENUM_ENTRY_VALUE = 7;
            public static final int TARGET_TYPE_ENUM_VALUE = 6;
            public static final int TARGET_TYPE_EXTENSION_RANGE_VALUE = 2;
            public static final int TARGET_TYPE_FIELD_VALUE = 4;
            public static final int TARGET_TYPE_FILE_VALUE = 1;
            public static final int TARGET_TYPE_MESSAGE_VALUE = 3;
            public static final int TARGET_TYPE_METHOD_VALUE = 9;
            public static final int TARGET_TYPE_ONEOF_VALUE = 5;
            public static final int TARGET_TYPE_SERVICE_VALUE = 8;
            public static final int TARGET_TYPE_UNKNOWN_VALUE = 0;
            private static final Internal.EnumLiteMap<OptionTargetType> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new Internal.EnumLiteMap<OptionTargetType>() {
                    public OptionTargetType findValueByNumber(int number) {
                        return OptionTargetType.forNumber(number);
                    }
                };
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static OptionTargetType valueOf(int value2) {
                return forNumber(value2);
            }

            public static OptionTargetType forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return TARGET_TYPE_UNKNOWN;
                    case 1:
                        return TARGET_TYPE_FILE;
                    case 2:
                        return TARGET_TYPE_EXTENSION_RANGE;
                    case 3:
                        return TARGET_TYPE_MESSAGE;
                    case 4:
                        return TARGET_TYPE_FIELD;
                    case 5:
                        return TARGET_TYPE_ONEOF;
                    case 6:
                        return TARGET_TYPE_ENUM;
                    case 7:
                        return TARGET_TYPE_ENUM_ENTRY;
                    case 8:
                        return TARGET_TYPE_SERVICE;
                    case 9:
                        return TARGET_TYPE_METHOD;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<OptionTargetType> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return OptionTargetTypeVerifier.INSTANCE;
            }

            private static final class OptionTargetTypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = null;

                private OptionTargetTypeVerifier() {
                }

                static {
                    INSTANCE = new OptionTargetTypeVerifier();
                }

                public boolean isInRange(int number) {
                    return OptionTargetType.forNumber(number) != null;
                }
            }

            private OptionTargetType(int value2) {
                this.value = value2;
            }
        }

        public static final class EditionDefault extends GeneratedMessageLite<EditionDefault, Builder> implements EditionDefaultOrBuilder {
            /* access modifiers changed from: private */
            public static final EditionDefault DEFAULT_INSTANCE;
            public static final int EDITION_FIELD_NUMBER = 3;
            private static volatile Parser<EditionDefault> PARSER = null;
            public static final int VALUE_FIELD_NUMBER = 2;
            private int bitField0_;
            private int edition_;
            private String value_ = "";

            private EditionDefault() {
            }

            public boolean hasEdition() {
                return (this.bitField0_ & 1) != 0;
            }

            public Edition getEdition() {
                Edition result = Edition.forNumber(this.edition_);
                return result == null ? Edition.EDITION_UNKNOWN : result;
            }

            /* access modifiers changed from: private */
            public void setEdition(Edition value) {
                this.edition_ = value.getNumber();
                this.bitField0_ |= 1;
            }

            /* access modifiers changed from: private */
            public void clearEdition() {
                this.bitField0_ &= -2;
                this.edition_ = 0;
            }

            public boolean hasValue() {
                return (this.bitField0_ & 2) != 0;
            }

            public String getValue() {
                return this.value_;
            }

            public ByteString getValueBytes() {
                return ByteString.copyFromUtf8(this.value_);
            }

            /* access modifiers changed from: private */
            public void setValue(String value) {
                Class<?> cls = value.getClass();
                this.bitField0_ |= 2;
                this.value_ = value;
            }

            /* access modifiers changed from: private */
            public void clearValue() {
                this.bitField0_ &= -3;
                this.value_ = getDefaultInstance().getValue();
            }

            /* access modifiers changed from: private */
            public void setValueBytes(ByteString value) {
                this.value_ = value.toStringUtf8();
                this.bitField0_ |= 2;
            }

            public static EditionDefault parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (EditionDefault) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static EditionDefault parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (EditionDefault) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static EditionDefault parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (EditionDefault) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static EditionDefault parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (EditionDefault) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static EditionDefault parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (EditionDefault) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static EditionDefault parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (EditionDefault) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static EditionDefault parseFrom(InputStream input) throws IOException {
                return (EditionDefault) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static EditionDefault parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (EditionDefault) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static EditionDefault parseDelimitedFrom(InputStream input) throws IOException {
                return (EditionDefault) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static EditionDefault parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (EditionDefault) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static EditionDefault parseFrom(CodedInputStream input) throws IOException {
                return (EditionDefault) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static EditionDefault parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (EditionDefault) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(EditionDefault prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<EditionDefault, Builder> implements EditionDefaultOrBuilder {
                private Builder() {
                    super(EditionDefault.DEFAULT_INSTANCE);
                }

                public boolean hasEdition() {
                    return ((EditionDefault) this.instance).hasEdition();
                }

                public Edition getEdition() {
                    return ((EditionDefault) this.instance).getEdition();
                }

                public Builder setEdition(Edition value) {
                    copyOnWrite();
                    ((EditionDefault) this.instance).setEdition(value);
                    return this;
                }

                public Builder clearEdition() {
                    copyOnWrite();
                    ((EditionDefault) this.instance).clearEdition();
                    return this;
                }

                public boolean hasValue() {
                    return ((EditionDefault) this.instance).hasValue();
                }

                public String getValue() {
                    return ((EditionDefault) this.instance).getValue();
                }

                public ByteString getValueBytes() {
                    return ((EditionDefault) this.instance).getValueBytes();
                }

                public Builder setValue(String value) {
                    copyOnWrite();
                    ((EditionDefault) this.instance).setValue(value);
                    return this;
                }

                public Builder clearValue() {
                    copyOnWrite();
                    ((EditionDefault) this.instance).clearValue();
                    return this;
                }

                public Builder setValueBytes(ByteString value) {
                    copyOnWrite();
                    ((EditionDefault) this.instance).setValueBytes(value);
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new EditionDefault();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0002\u0003\u0002\u0000\u0000\u0000\u0002ဈ\u0001\u0003᠌\u0000", new Object[]{"bitField0_", "value_", "edition_", Edition.internalGetVerifier()});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<EditionDefault> parser = PARSER;
                        if (parser == null) {
                            synchronized (EditionDefault.class) {
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
                EditionDefault defaultInstance = new EditionDefault();
                DEFAULT_INSTANCE = defaultInstance;
                GeneratedMessageLite.registerDefaultInstance(EditionDefault.class, defaultInstance);
            }

            public static EditionDefault getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<EditionDefault> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public static final class FeatureSupport extends GeneratedMessageLite<FeatureSupport, Builder> implements FeatureSupportOrBuilder {
            /* access modifiers changed from: private */
            public static final FeatureSupport DEFAULT_INSTANCE;
            public static final int DEPRECATION_WARNING_FIELD_NUMBER = 3;
            public static final int EDITION_DEPRECATED_FIELD_NUMBER = 2;
            public static final int EDITION_INTRODUCED_FIELD_NUMBER = 1;
            public static final int EDITION_REMOVED_FIELD_NUMBER = 4;
            private static volatile Parser<FeatureSupport> PARSER;
            private int bitField0_;
            private String deprecationWarning_ = "";
            private int editionDeprecated_;
            private int editionIntroduced_;
            private int editionRemoved_;

            private FeatureSupport() {
            }

            public boolean hasEditionIntroduced() {
                return (this.bitField0_ & 1) != 0;
            }

            public Edition getEditionIntroduced() {
                Edition result = Edition.forNumber(this.editionIntroduced_);
                return result == null ? Edition.EDITION_UNKNOWN : result;
            }

            /* access modifiers changed from: private */
            public void setEditionIntroduced(Edition value) {
                this.editionIntroduced_ = value.getNumber();
                this.bitField0_ |= 1;
            }

            /* access modifiers changed from: private */
            public void clearEditionIntroduced() {
                this.bitField0_ &= -2;
                this.editionIntroduced_ = 0;
            }

            public boolean hasEditionDeprecated() {
                return (this.bitField0_ & 2) != 0;
            }

            public Edition getEditionDeprecated() {
                Edition result = Edition.forNumber(this.editionDeprecated_);
                return result == null ? Edition.EDITION_UNKNOWN : result;
            }

            /* access modifiers changed from: private */
            public void setEditionDeprecated(Edition value) {
                this.editionDeprecated_ = value.getNumber();
                this.bitField0_ |= 2;
            }

            /* access modifiers changed from: private */
            public void clearEditionDeprecated() {
                this.bitField0_ &= -3;
                this.editionDeprecated_ = 0;
            }

            public boolean hasDeprecationWarning() {
                return (this.bitField0_ & 4) != 0;
            }

            public String getDeprecationWarning() {
                return this.deprecationWarning_;
            }

            public ByteString getDeprecationWarningBytes() {
                return ByteString.copyFromUtf8(this.deprecationWarning_);
            }

            /* access modifiers changed from: private */
            public void setDeprecationWarning(String value) {
                Class<?> cls = value.getClass();
                this.bitField0_ |= 4;
                this.deprecationWarning_ = value;
            }

            /* access modifiers changed from: private */
            public void clearDeprecationWarning() {
                this.bitField0_ &= -5;
                this.deprecationWarning_ = getDefaultInstance().getDeprecationWarning();
            }

            /* access modifiers changed from: private */
            public void setDeprecationWarningBytes(ByteString value) {
                this.deprecationWarning_ = value.toStringUtf8();
                this.bitField0_ |= 4;
            }

            public boolean hasEditionRemoved() {
                return (this.bitField0_ & 8) != 0;
            }

            public Edition getEditionRemoved() {
                Edition result = Edition.forNumber(this.editionRemoved_);
                return result == null ? Edition.EDITION_UNKNOWN : result;
            }

            /* access modifiers changed from: private */
            public void setEditionRemoved(Edition value) {
                this.editionRemoved_ = value.getNumber();
                this.bitField0_ |= 8;
            }

            /* access modifiers changed from: private */
            public void clearEditionRemoved() {
                this.bitField0_ &= -9;
                this.editionRemoved_ = 0;
            }

            public static FeatureSupport parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (FeatureSupport) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static FeatureSupport parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (FeatureSupport) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static FeatureSupport parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (FeatureSupport) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static FeatureSupport parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (FeatureSupport) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static FeatureSupport parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (FeatureSupport) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static FeatureSupport parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (FeatureSupport) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static FeatureSupport parseFrom(InputStream input) throws IOException {
                return (FeatureSupport) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static FeatureSupport parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (FeatureSupport) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static FeatureSupport parseDelimitedFrom(InputStream input) throws IOException {
                return (FeatureSupport) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static FeatureSupport parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (FeatureSupport) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static FeatureSupport parseFrom(CodedInputStream input) throws IOException {
                return (FeatureSupport) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static FeatureSupport parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (FeatureSupport) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(FeatureSupport prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<FeatureSupport, Builder> implements FeatureSupportOrBuilder {
                private Builder() {
                    super(FeatureSupport.DEFAULT_INSTANCE);
                }

                public boolean hasEditionIntroduced() {
                    return ((FeatureSupport) this.instance).hasEditionIntroduced();
                }

                public Edition getEditionIntroduced() {
                    return ((FeatureSupport) this.instance).getEditionIntroduced();
                }

                public Builder setEditionIntroduced(Edition value) {
                    copyOnWrite();
                    ((FeatureSupport) this.instance).setEditionIntroduced(value);
                    return this;
                }

                public Builder clearEditionIntroduced() {
                    copyOnWrite();
                    ((FeatureSupport) this.instance).clearEditionIntroduced();
                    return this;
                }

                public boolean hasEditionDeprecated() {
                    return ((FeatureSupport) this.instance).hasEditionDeprecated();
                }

                public Edition getEditionDeprecated() {
                    return ((FeatureSupport) this.instance).getEditionDeprecated();
                }

                public Builder setEditionDeprecated(Edition value) {
                    copyOnWrite();
                    ((FeatureSupport) this.instance).setEditionDeprecated(value);
                    return this;
                }

                public Builder clearEditionDeprecated() {
                    copyOnWrite();
                    ((FeatureSupport) this.instance).clearEditionDeprecated();
                    return this;
                }

                public boolean hasDeprecationWarning() {
                    return ((FeatureSupport) this.instance).hasDeprecationWarning();
                }

                public String getDeprecationWarning() {
                    return ((FeatureSupport) this.instance).getDeprecationWarning();
                }

                public ByteString getDeprecationWarningBytes() {
                    return ((FeatureSupport) this.instance).getDeprecationWarningBytes();
                }

                public Builder setDeprecationWarning(String value) {
                    copyOnWrite();
                    ((FeatureSupport) this.instance).setDeprecationWarning(value);
                    return this;
                }

                public Builder clearDeprecationWarning() {
                    copyOnWrite();
                    ((FeatureSupport) this.instance).clearDeprecationWarning();
                    return this;
                }

                public Builder setDeprecationWarningBytes(ByteString value) {
                    copyOnWrite();
                    ((FeatureSupport) this.instance).setDeprecationWarningBytes(value);
                    return this;
                }

                public boolean hasEditionRemoved() {
                    return ((FeatureSupport) this.instance).hasEditionRemoved();
                }

                public Edition getEditionRemoved() {
                    return ((FeatureSupport) this.instance).getEditionRemoved();
                }

                public Builder setEditionRemoved(Edition value) {
                    copyOnWrite();
                    ((FeatureSupport) this.instance).setEditionRemoved(value);
                    return this;
                }

                public Builder clearEditionRemoved() {
                    copyOnWrite();
                    ((FeatureSupport) this.instance).clearEditionRemoved();
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new FeatureSupport();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001᠌\u0000\u0002᠌\u0001\u0003ဈ\u0002\u0004᠌\u0003", new Object[]{"bitField0_", "editionIntroduced_", Edition.internalGetVerifier(), "editionDeprecated_", Edition.internalGetVerifier(), "deprecationWarning_", "editionRemoved_", Edition.internalGetVerifier()});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<FeatureSupport> parser = PARSER;
                        if (parser == null) {
                            synchronized (FeatureSupport.class) {
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
                FeatureSupport defaultInstance = new FeatureSupport();
                DEFAULT_INSTANCE = defaultInstance;
                GeneratedMessageLite.registerDefaultInstance(FeatureSupport.class, defaultInstance);
            }

            public static FeatureSupport getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<FeatureSupport> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public boolean hasCtype() {
            return (this.bitField0_ & 1) != 0;
        }

        public CType getCtype() {
            CType result = CType.forNumber(this.ctype_);
            return result == null ? CType.STRING : result;
        }

        /* access modifiers changed from: private */
        public void setCtype(CType value) {
            this.ctype_ = value.getNumber();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void clearCtype() {
            this.bitField0_ &= -2;
            this.ctype_ = 0;
        }

        public boolean hasPacked() {
            return (this.bitField0_ & 2) != 0;
        }

        public boolean getPacked() {
            return this.packed_;
        }

        /* access modifiers changed from: private */
        public void setPacked(boolean value) {
            this.bitField0_ |= 2;
            this.packed_ = value;
        }

        /* access modifiers changed from: private */
        public void clearPacked() {
            this.bitField0_ &= -3;
            this.packed_ = false;
        }

        public boolean hasJstype() {
            return (this.bitField0_ & 4) != 0;
        }

        public JSType getJstype() {
            JSType result = JSType.forNumber(this.jstype_);
            return result == null ? JSType.JS_NORMAL : result;
        }

        /* access modifiers changed from: private */
        public void setJstype(JSType value) {
            this.jstype_ = value.getNumber();
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void clearJstype() {
            this.bitField0_ &= -5;
            this.jstype_ = 0;
        }

        public boolean hasLazy() {
            return (this.bitField0_ & 8) != 0;
        }

        public boolean getLazy() {
            return this.lazy_;
        }

        /* access modifiers changed from: private */
        public void setLazy(boolean value) {
            this.bitField0_ |= 8;
            this.lazy_ = value;
        }

        /* access modifiers changed from: private */
        public void clearLazy() {
            this.bitField0_ &= -9;
            this.lazy_ = false;
        }

        public boolean hasUnverifiedLazy() {
            return (this.bitField0_ & 16) != 0;
        }

        public boolean getUnverifiedLazy() {
            return this.unverifiedLazy_;
        }

        /* access modifiers changed from: private */
        public void setUnverifiedLazy(boolean value) {
            this.bitField0_ |= 16;
            this.unverifiedLazy_ = value;
        }

        /* access modifiers changed from: private */
        public void clearUnverifiedLazy() {
            this.bitField0_ &= -17;
            this.unverifiedLazy_ = false;
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 32) != 0;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        /* access modifiers changed from: private */
        public void setDeprecated(boolean value) {
            this.bitField0_ |= 32;
            this.deprecated_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeprecated() {
            this.bitField0_ &= -33;
            this.deprecated_ = false;
        }

        public boolean hasWeak() {
            return (this.bitField0_ & 64) != 0;
        }

        public boolean getWeak() {
            return this.weak_;
        }

        /* access modifiers changed from: private */
        public void setWeak(boolean value) {
            this.bitField0_ |= 64;
            this.weak_ = value;
        }

        /* access modifiers changed from: private */
        public void clearWeak() {
            this.bitField0_ &= -65;
            this.weak_ = false;
        }

        public boolean hasDebugRedact() {
            return (this.bitField0_ & 128) != 0;
        }

        public boolean getDebugRedact() {
            return this.debugRedact_;
        }

        /* access modifiers changed from: private */
        public void setDebugRedact(boolean value) {
            this.bitField0_ |= 128;
            this.debugRedact_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDebugRedact() {
            this.bitField0_ &= -129;
            this.debugRedact_ = false;
        }

        public boolean hasRetention() {
            return (this.bitField0_ & 256) != 0;
        }

        public OptionRetention getRetention() {
            OptionRetention result = OptionRetention.forNumber(this.retention_);
            return result == null ? OptionRetention.RETENTION_UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setRetention(OptionRetention value) {
            this.retention_ = value.getNumber();
            this.bitField0_ |= 256;
        }

        /* access modifiers changed from: private */
        public void clearRetention() {
            this.bitField0_ &= -257;
            this.retention_ = 0;
        }

        static {
            FieldOptions defaultInstance = new FieldOptions();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(FieldOptions.class, defaultInstance);
        }

        public List<OptionTargetType> getTargetsList() {
            return new Internal.IntListAdapter(this.targets_, targets_converter_);
        }

        public int getTargetsCount() {
            return this.targets_.size();
        }

        public OptionTargetType getTargets(int index) {
            OptionTargetType result = OptionTargetType.forNumber(this.targets_.getInt(index));
            return result == null ? OptionTargetType.TARGET_TYPE_UNKNOWN : result;
        }

        private void ensureTargetsIsMutable() {
            Internal.IntList tmp = this.targets_;
            if (!tmp.isModifiable()) {
                this.targets_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setTargets(int index, OptionTargetType value) {
            value.getClass();
            ensureTargetsIsMutable();
            this.targets_.setInt(index, value.getNumber());
        }

        /* access modifiers changed from: private */
        public void addTargets(OptionTargetType value) {
            value.getClass();
            ensureTargetsIsMutable();
            this.targets_.addInt(value.getNumber());
        }

        /* access modifiers changed from: private */
        public void addAllTargets(Iterable<? extends OptionTargetType> values) {
            ensureTargetsIsMutable();
            for (OptionTargetType value : values) {
                this.targets_.addInt(value.getNumber());
            }
        }

        /* access modifiers changed from: private */
        public void clearTargets() {
            this.targets_ = emptyIntList();
        }

        public List<EditionDefault> getEditionDefaultsList() {
            return this.editionDefaults_;
        }

        public List<? extends EditionDefaultOrBuilder> getEditionDefaultsOrBuilderList() {
            return this.editionDefaults_;
        }

        public int getEditionDefaultsCount() {
            return this.editionDefaults_.size();
        }

        public EditionDefault getEditionDefaults(int index) {
            return (EditionDefault) this.editionDefaults_.get(index);
        }

        public EditionDefaultOrBuilder getEditionDefaultsOrBuilder(int index) {
            return (EditionDefaultOrBuilder) this.editionDefaults_.get(index);
        }

        private void ensureEditionDefaultsIsMutable() {
            Internal.ProtobufList<EditionDefault> tmp = this.editionDefaults_;
            if (!tmp.isModifiable()) {
                this.editionDefaults_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setEditionDefaults(int index, EditionDefault value) {
            value.getClass();
            ensureEditionDefaultsIsMutable();
            this.editionDefaults_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addEditionDefaults(EditionDefault value) {
            value.getClass();
            ensureEditionDefaultsIsMutable();
            this.editionDefaults_.add(value);
        }

        /* access modifiers changed from: private */
        public void addEditionDefaults(int index, EditionDefault value) {
            value.getClass();
            ensureEditionDefaultsIsMutable();
            this.editionDefaults_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllEditionDefaults(Iterable<? extends EditionDefault> values) {
            ensureEditionDefaultsIsMutable();
            AbstractMessageLite.addAll(values, this.editionDefaults_);
        }

        /* access modifiers changed from: private */
        public void clearEditionDefaults() {
            this.editionDefaults_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeEditionDefaults(int index) {
            ensureEditionDefaultsIsMutable();
            this.editionDefaults_.remove(index);
        }

        public boolean hasFeatures() {
            return (this.bitField0_ & 512) != 0;
        }

        public FeatureSet getFeatures() {
            return this.features_ == null ? FeatureSet.getDefaultInstance() : this.features_;
        }

        /* access modifiers changed from: private */
        public void setFeatures(FeatureSet value) {
            value.getClass();
            this.features_ = value;
            this.bitField0_ |= 512;
        }

        /* access modifiers changed from: private */
        public void mergeFeatures(FeatureSet value) {
            value.getClass();
            if (this.features_ == null || this.features_ == FeatureSet.getDefaultInstance()) {
                this.features_ = value;
            } else {
                this.features_ = (FeatureSet) ((FeatureSet.Builder) FeatureSet.newBuilder(this.features_).mergeFrom(value)).buildPartial();
            }
            this.bitField0_ |= 512;
        }

        /* access modifiers changed from: private */
        public void clearFeatures() {
            this.features_ = null;
            this.bitField0_ &= -513;
        }

        public boolean hasFeatureSupport() {
            return (this.bitField0_ & 1024) != 0;
        }

        public FeatureSupport getFeatureSupport() {
            return this.featureSupport_ == null ? FeatureSupport.getDefaultInstance() : this.featureSupport_;
        }

        /* access modifiers changed from: private */
        public void setFeatureSupport(FeatureSupport value) {
            value.getClass();
            this.featureSupport_ = value;
            this.bitField0_ |= 1024;
        }

        /* access modifiers changed from: private */
        public void mergeFeatureSupport(FeatureSupport value) {
            value.getClass();
            if (this.featureSupport_ == null || this.featureSupport_ == FeatureSupport.getDefaultInstance()) {
                this.featureSupport_ = value;
            } else {
                this.featureSupport_ = (FeatureSupport) ((FeatureSupport.Builder) FeatureSupport.newBuilder(this.featureSupport_).mergeFrom(value)).buildPartial();
            }
            this.bitField0_ |= 1024;
        }

        /* access modifiers changed from: private */
        public void clearFeatureSupport() {
            this.featureSupport_ = null;
            this.bitField0_ &= -1025;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return (UninterpretedOption) this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
        }

        private void ensureUninterpretedOptionIsMutable() {
            Internal.ProtobufList<UninterpretedOption> tmp = this.uninterpretedOption_;
            if (!tmp.isModifiable()) {
                this.uninterpretedOption_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(value);
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
            ensureUninterpretedOptionIsMutable();
            AbstractMessageLite.addAll(values, this.uninterpretedOption_);
        }

        /* access modifiers changed from: private */
        public void clearUninterpretedOption() {
            this.uninterpretedOption_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeUninterpretedOption(int index) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.remove(index);
        }

        public static FieldOptions parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (FieldOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FieldOptions parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FieldOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FieldOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FieldOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FieldOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FieldOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FieldOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FieldOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FieldOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FieldOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FieldOptions parseFrom(InputStream input) throws IOException {
            return (FieldOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FieldOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FieldOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FieldOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (FieldOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static FieldOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FieldOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FieldOptions parseFrom(CodedInputStream input) throws IOException {
            return (FieldOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FieldOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FieldOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(FieldOptions prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<FieldOptions, Builder> implements FieldOptionsOrBuilder {
            private Builder() {
                super(FieldOptions.DEFAULT_INSTANCE);
            }

            public boolean hasCtype() {
                return ((FieldOptions) this.instance).hasCtype();
            }

            public CType getCtype() {
                return ((FieldOptions) this.instance).getCtype();
            }

            public Builder setCtype(CType value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setCtype(value);
                return this;
            }

            public Builder clearCtype() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearCtype();
                return this;
            }

            public boolean hasPacked() {
                return ((FieldOptions) this.instance).hasPacked();
            }

            public boolean getPacked() {
                return ((FieldOptions) this.instance).getPacked();
            }

            public Builder setPacked(boolean value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setPacked(value);
                return this;
            }

            public Builder clearPacked() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearPacked();
                return this;
            }

            public boolean hasJstype() {
                return ((FieldOptions) this.instance).hasJstype();
            }

            public JSType getJstype() {
                return ((FieldOptions) this.instance).getJstype();
            }

            public Builder setJstype(JSType value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setJstype(value);
                return this;
            }

            public Builder clearJstype() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearJstype();
                return this;
            }

            public boolean hasLazy() {
                return ((FieldOptions) this.instance).hasLazy();
            }

            public boolean getLazy() {
                return ((FieldOptions) this.instance).getLazy();
            }

            public Builder setLazy(boolean value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setLazy(value);
                return this;
            }

            public Builder clearLazy() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearLazy();
                return this;
            }

            public boolean hasUnverifiedLazy() {
                return ((FieldOptions) this.instance).hasUnverifiedLazy();
            }

            public boolean getUnverifiedLazy() {
                return ((FieldOptions) this.instance).getUnverifiedLazy();
            }

            public Builder setUnverifiedLazy(boolean value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setUnverifiedLazy(value);
                return this;
            }

            public Builder clearUnverifiedLazy() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearUnverifiedLazy();
                return this;
            }

            public boolean hasDeprecated() {
                return ((FieldOptions) this.instance).hasDeprecated();
            }

            public boolean getDeprecated() {
                return ((FieldOptions) this.instance).getDeprecated();
            }

            public Builder setDeprecated(boolean value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setDeprecated(value);
                return this;
            }

            public Builder clearDeprecated() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearDeprecated();
                return this;
            }

            public boolean hasWeak() {
                return ((FieldOptions) this.instance).hasWeak();
            }

            public boolean getWeak() {
                return ((FieldOptions) this.instance).getWeak();
            }

            public Builder setWeak(boolean value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setWeak(value);
                return this;
            }

            public Builder clearWeak() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearWeak();
                return this;
            }

            public boolean hasDebugRedact() {
                return ((FieldOptions) this.instance).hasDebugRedact();
            }

            public boolean getDebugRedact() {
                return ((FieldOptions) this.instance).getDebugRedact();
            }

            public Builder setDebugRedact(boolean value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setDebugRedact(value);
                return this;
            }

            public Builder clearDebugRedact() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearDebugRedact();
                return this;
            }

            public boolean hasRetention() {
                return ((FieldOptions) this.instance).hasRetention();
            }

            public OptionRetention getRetention() {
                return ((FieldOptions) this.instance).getRetention();
            }

            public Builder setRetention(OptionRetention value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setRetention(value);
                return this;
            }

            public Builder clearRetention() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearRetention();
                return this;
            }

            public List<OptionTargetType> getTargetsList() {
                return ((FieldOptions) this.instance).getTargetsList();
            }

            public int getTargetsCount() {
                return ((FieldOptions) this.instance).getTargetsCount();
            }

            public OptionTargetType getTargets(int index) {
                return ((FieldOptions) this.instance).getTargets(index);
            }

            public Builder setTargets(int index, OptionTargetType value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setTargets(index, value);
                return this;
            }

            public Builder addTargets(OptionTargetType value) {
                copyOnWrite();
                ((FieldOptions) this.instance).addTargets(value);
                return this;
            }

            public Builder addAllTargets(Iterable<? extends OptionTargetType> values) {
                copyOnWrite();
                ((FieldOptions) this.instance).addAllTargets(values);
                return this;
            }

            public Builder clearTargets() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearTargets();
                return this;
            }

            public List<EditionDefault> getEditionDefaultsList() {
                return Collections.unmodifiableList(((FieldOptions) this.instance).getEditionDefaultsList());
            }

            public int getEditionDefaultsCount() {
                return ((FieldOptions) this.instance).getEditionDefaultsCount();
            }

            public EditionDefault getEditionDefaults(int index) {
                return ((FieldOptions) this.instance).getEditionDefaults(index);
            }

            public Builder setEditionDefaults(int index, EditionDefault value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setEditionDefaults(index, value);
                return this;
            }

            public Builder setEditionDefaults(int index, EditionDefault.Builder builderForValue) {
                copyOnWrite();
                ((FieldOptions) this.instance).setEditionDefaults(index, (EditionDefault) builderForValue.build());
                return this;
            }

            public Builder addEditionDefaults(EditionDefault value) {
                copyOnWrite();
                ((FieldOptions) this.instance).addEditionDefaults(value);
                return this;
            }

            public Builder addEditionDefaults(int index, EditionDefault value) {
                copyOnWrite();
                ((FieldOptions) this.instance).addEditionDefaults(index, value);
                return this;
            }

            public Builder addEditionDefaults(EditionDefault.Builder builderForValue) {
                copyOnWrite();
                ((FieldOptions) this.instance).addEditionDefaults((EditionDefault) builderForValue.build());
                return this;
            }

            public Builder addEditionDefaults(int index, EditionDefault.Builder builderForValue) {
                copyOnWrite();
                ((FieldOptions) this.instance).addEditionDefaults(index, (EditionDefault) builderForValue.build());
                return this;
            }

            public Builder addAllEditionDefaults(Iterable<? extends EditionDefault> values) {
                copyOnWrite();
                ((FieldOptions) this.instance).addAllEditionDefaults(values);
                return this;
            }

            public Builder clearEditionDefaults() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearEditionDefaults();
                return this;
            }

            public Builder removeEditionDefaults(int index) {
                copyOnWrite();
                ((FieldOptions) this.instance).removeEditionDefaults(index);
                return this;
            }

            public boolean hasFeatures() {
                return ((FieldOptions) this.instance).hasFeatures();
            }

            public FeatureSet getFeatures() {
                return ((FieldOptions) this.instance).getFeatures();
            }

            public Builder setFeatures(FeatureSet value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setFeatures(value);
                return this;
            }

            public Builder setFeatures(FeatureSet.Builder builderForValue) {
                copyOnWrite();
                ((FieldOptions) this.instance).setFeatures((FeatureSet) builderForValue.build());
                return this;
            }

            public Builder mergeFeatures(FeatureSet value) {
                copyOnWrite();
                ((FieldOptions) this.instance).mergeFeatures(value);
                return this;
            }

            public Builder clearFeatures() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearFeatures();
                return this;
            }

            public boolean hasFeatureSupport() {
                return ((FieldOptions) this.instance).hasFeatureSupport();
            }

            public FeatureSupport getFeatureSupport() {
                return ((FieldOptions) this.instance).getFeatureSupport();
            }

            public Builder setFeatureSupport(FeatureSupport value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setFeatureSupport(value);
                return this;
            }

            public Builder setFeatureSupport(FeatureSupport.Builder builderForValue) {
                copyOnWrite();
                ((FieldOptions) this.instance).setFeatureSupport((FeatureSupport) builderForValue.build());
                return this;
            }

            public Builder mergeFeatureSupport(FeatureSupport value) {
                copyOnWrite();
                ((FieldOptions) this.instance).mergeFeatureSupport(value);
                return this;
            }

            public Builder clearFeatureSupport() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearFeatureSupport();
                return this;
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                return Collections.unmodifiableList(((FieldOptions) this.instance).getUninterpretedOptionList());
            }

            public int getUninterpretedOptionCount() {
                return ((FieldOptions) this.instance).getUninterpretedOptionCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                return ((FieldOptions) this.instance).getUninterpretedOption(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((FieldOptions) this.instance).setUninterpretedOption(index, value);
                return this;
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((FieldOptions) this.instance).setUninterpretedOption(index, (UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                copyOnWrite();
                ((FieldOptions) this.instance).addUninterpretedOption(value);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((FieldOptions) this.instance).addUninterpretedOption(index, value);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((FieldOptions) this.instance).addUninterpretedOption((UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((FieldOptions) this.instance).addUninterpretedOption(index, (UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                copyOnWrite();
                ((FieldOptions) this.instance).addAllUninterpretedOption(values);
                return this;
            }

            public Builder clearUninterpretedOption() {
                copyOnWrite();
                ((FieldOptions) this.instance).clearUninterpretedOption();
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                copyOnWrite();
                ((FieldOptions) this.instance).removeUninterpretedOption(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new FieldOptions();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u000e\u0000\u0001\u0001ϧ\u000e\u0000\u0003\u0002\u0001᠌\u0000\u0002ဇ\u0001\u0003ဇ\u0005\u0005ဇ\u0003\u0006᠌\u0002\nဇ\u0006\u000fဇ\u0004\u0010ဇ\u0007\u0011᠌\b\u0013ࠞ\u0014\u001b\u0015ᐉ\t\u0016ဉ\nϧЛ", new Object[]{"bitField0_", "ctype_", CType.internalGetVerifier(), "packed_", "deprecated_", "lazy_", "jstype_", JSType.internalGetVerifier(), "weak_", "unverifiedLazy_", "debugRedact_", "retention_", OptionRetention.internalGetVerifier(), "targets_", OptionTargetType.internalGetVerifier(), "editionDefaults_", EditionDefault.class, "features_", "featureSupport_", "uninterpretedOption_", UninterpretedOption.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<FieldOptions> parser = PARSER;
                    if (parser == null) {
                        synchronized (FieldOptions.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        public static FieldOptions getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FieldOptions> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class OneofOptions extends GeneratedMessageLite.ExtendableMessage<OneofOptions, Builder> implements OneofOptionsOrBuilder {
        /* access modifiers changed from: private */
        public static final OneofOptions DEFAULT_INSTANCE;
        public static final int FEATURES_FIELD_NUMBER = 1;
        private static volatile Parser<OneofOptions> PARSER = null;
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        private int bitField0_;
        private FeatureSet features_;
        private byte memoizedIsInitialized = 2;
        private Internal.ProtobufList<UninterpretedOption> uninterpretedOption_ = emptyProtobufList();

        private OneofOptions() {
        }

        public boolean hasFeatures() {
            return (this.bitField0_ & 1) != 0;
        }

        public FeatureSet getFeatures() {
            return this.features_ == null ? FeatureSet.getDefaultInstance() : this.features_;
        }

        /* access modifiers changed from: private */
        public void setFeatures(FeatureSet value) {
            value.getClass();
            this.features_ = value;
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeFeatures(FeatureSet value) {
            value.getClass();
            if (this.features_ == null || this.features_ == FeatureSet.getDefaultInstance()) {
                this.features_ = value;
            } else {
                this.features_ = (FeatureSet) ((FeatureSet.Builder) FeatureSet.newBuilder(this.features_).mergeFrom(value)).buildPartial();
            }
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void clearFeatures() {
            this.features_ = null;
            this.bitField0_ &= -2;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return (UninterpretedOption) this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
        }

        private void ensureUninterpretedOptionIsMutable() {
            Internal.ProtobufList<UninterpretedOption> tmp = this.uninterpretedOption_;
            if (!tmp.isModifiable()) {
                this.uninterpretedOption_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(value);
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
            ensureUninterpretedOptionIsMutable();
            AbstractMessageLite.addAll(values, this.uninterpretedOption_);
        }

        /* access modifiers changed from: private */
        public void clearUninterpretedOption() {
            this.uninterpretedOption_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeUninterpretedOption(int index) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.remove(index);
        }

        public static OneofOptions parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (OneofOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static OneofOptions parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OneofOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static OneofOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (OneofOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static OneofOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OneofOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static OneofOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (OneofOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static OneofOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OneofOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static OneofOptions parseFrom(InputStream input) throws IOException {
            return (OneofOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static OneofOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OneofOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static OneofOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (OneofOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static OneofOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OneofOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static OneofOptions parseFrom(CodedInputStream input) throws IOException {
            return (OneofOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static OneofOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OneofOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(OneofOptions prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<OneofOptions, Builder> implements OneofOptionsOrBuilder {
            private Builder() {
                super(OneofOptions.DEFAULT_INSTANCE);
            }

            public boolean hasFeatures() {
                return ((OneofOptions) this.instance).hasFeatures();
            }

            public FeatureSet getFeatures() {
                return ((OneofOptions) this.instance).getFeatures();
            }

            public Builder setFeatures(FeatureSet value) {
                copyOnWrite();
                ((OneofOptions) this.instance).setFeatures(value);
                return this;
            }

            public Builder setFeatures(FeatureSet.Builder builderForValue) {
                copyOnWrite();
                ((OneofOptions) this.instance).setFeatures((FeatureSet) builderForValue.build());
                return this;
            }

            public Builder mergeFeatures(FeatureSet value) {
                copyOnWrite();
                ((OneofOptions) this.instance).mergeFeatures(value);
                return this;
            }

            public Builder clearFeatures() {
                copyOnWrite();
                ((OneofOptions) this.instance).clearFeatures();
                return this;
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                return Collections.unmodifiableList(((OneofOptions) this.instance).getUninterpretedOptionList());
            }

            public int getUninterpretedOptionCount() {
                return ((OneofOptions) this.instance).getUninterpretedOptionCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                return ((OneofOptions) this.instance).getUninterpretedOption(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((OneofOptions) this.instance).setUninterpretedOption(index, value);
                return this;
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((OneofOptions) this.instance).setUninterpretedOption(index, (UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                copyOnWrite();
                ((OneofOptions) this.instance).addUninterpretedOption(value);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((OneofOptions) this.instance).addUninterpretedOption(index, value);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((OneofOptions) this.instance).addUninterpretedOption((UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((OneofOptions) this.instance).addUninterpretedOption(index, (UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                copyOnWrite();
                ((OneofOptions) this.instance).addAllUninterpretedOption(values);
                return this;
            }

            public Builder clearUninterpretedOption() {
                copyOnWrite();
                ((OneofOptions) this.instance).clearUninterpretedOption();
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                copyOnWrite();
                ((OneofOptions) this.instance).removeUninterpretedOption(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new OneofOptions();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001ϧ\u0002\u0000\u0001\u0002\u0001ᐉ\u0000ϧЛ", new Object[]{"bitField0_", "features_", "uninterpretedOption_", UninterpretedOption.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<OneofOptions> parser = PARSER;
                    if (parser == null) {
                        synchronized (OneofOptions.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            OneofOptions defaultInstance = new OneofOptions();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(OneofOptions.class, defaultInstance);
        }

        public static OneofOptions getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<OneofOptions> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class EnumOptions extends GeneratedMessageLite.ExtendableMessage<EnumOptions, Builder> implements EnumOptionsOrBuilder {
        public static final int ALLOW_ALIAS_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final EnumOptions DEFAULT_INSTANCE;
        public static final int DEPRECATED_FIELD_NUMBER = 3;
        public static final int DEPRECATED_LEGACY_JSON_FIELD_CONFLICTS_FIELD_NUMBER = 6;
        public static final int FEATURES_FIELD_NUMBER = 7;
        private static volatile Parser<EnumOptions> PARSER = null;
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        private boolean allowAlias_;
        private int bitField0_;
        private boolean deprecatedLegacyJsonFieldConflicts_;
        private boolean deprecated_;
        private FeatureSet features_;
        private byte memoizedIsInitialized = 2;
        private Internal.ProtobufList<UninterpretedOption> uninterpretedOption_ = emptyProtobufList();

        private EnumOptions() {
        }

        public boolean hasAllowAlias() {
            return (this.bitField0_ & 1) != 0;
        }

        public boolean getAllowAlias() {
            return this.allowAlias_;
        }

        /* access modifiers changed from: private */
        public void setAllowAlias(boolean value) {
            this.bitField0_ |= 1;
            this.allowAlias_ = value;
        }

        /* access modifiers changed from: private */
        public void clearAllowAlias() {
            this.bitField0_ &= -2;
            this.allowAlias_ = false;
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 2) != 0;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        /* access modifiers changed from: private */
        public void setDeprecated(boolean value) {
            this.bitField0_ |= 2;
            this.deprecated_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeprecated() {
            this.bitField0_ &= -3;
            this.deprecated_ = false;
        }

        @Deprecated
        public boolean hasDeprecatedLegacyJsonFieldConflicts() {
            return (this.bitField0_ & 4) != 0;
        }

        @Deprecated
        public boolean getDeprecatedLegacyJsonFieldConflicts() {
            return this.deprecatedLegacyJsonFieldConflicts_;
        }

        /* access modifiers changed from: private */
        public void setDeprecatedLegacyJsonFieldConflicts(boolean value) {
            this.bitField0_ |= 4;
            this.deprecatedLegacyJsonFieldConflicts_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeprecatedLegacyJsonFieldConflicts() {
            this.bitField0_ &= -5;
            this.deprecatedLegacyJsonFieldConflicts_ = false;
        }

        public boolean hasFeatures() {
            return (this.bitField0_ & 8) != 0;
        }

        public FeatureSet getFeatures() {
            return this.features_ == null ? FeatureSet.getDefaultInstance() : this.features_;
        }

        /* access modifiers changed from: private */
        public void setFeatures(FeatureSet value) {
            value.getClass();
            this.features_ = value;
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void mergeFeatures(FeatureSet value) {
            value.getClass();
            if (this.features_ == null || this.features_ == FeatureSet.getDefaultInstance()) {
                this.features_ = value;
            } else {
                this.features_ = (FeatureSet) ((FeatureSet.Builder) FeatureSet.newBuilder(this.features_).mergeFrom(value)).buildPartial();
            }
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void clearFeatures() {
            this.features_ = null;
            this.bitField0_ &= -9;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return (UninterpretedOption) this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
        }

        private void ensureUninterpretedOptionIsMutable() {
            Internal.ProtobufList<UninterpretedOption> tmp = this.uninterpretedOption_;
            if (!tmp.isModifiable()) {
                this.uninterpretedOption_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(value);
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
            ensureUninterpretedOptionIsMutable();
            AbstractMessageLite.addAll(values, this.uninterpretedOption_);
        }

        /* access modifiers changed from: private */
        public void clearUninterpretedOption() {
            this.uninterpretedOption_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeUninterpretedOption(int index) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.remove(index);
        }

        public static EnumOptions parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (EnumOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumOptions parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (EnumOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (EnumOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumOptions parseFrom(InputStream input) throws IOException {
            return (EnumOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static EnumOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (EnumOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static EnumOptions parseFrom(CodedInputStream input) throws IOException {
            return (EnumOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(EnumOptions prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<EnumOptions, Builder> implements EnumOptionsOrBuilder {
            private Builder() {
                super(EnumOptions.DEFAULT_INSTANCE);
            }

            public boolean hasAllowAlias() {
                return ((EnumOptions) this.instance).hasAllowAlias();
            }

            public boolean getAllowAlias() {
                return ((EnumOptions) this.instance).getAllowAlias();
            }

            public Builder setAllowAlias(boolean value) {
                copyOnWrite();
                ((EnumOptions) this.instance).setAllowAlias(value);
                return this;
            }

            public Builder clearAllowAlias() {
                copyOnWrite();
                ((EnumOptions) this.instance).clearAllowAlias();
                return this;
            }

            public boolean hasDeprecated() {
                return ((EnumOptions) this.instance).hasDeprecated();
            }

            public boolean getDeprecated() {
                return ((EnumOptions) this.instance).getDeprecated();
            }

            public Builder setDeprecated(boolean value) {
                copyOnWrite();
                ((EnumOptions) this.instance).setDeprecated(value);
                return this;
            }

            public Builder clearDeprecated() {
                copyOnWrite();
                ((EnumOptions) this.instance).clearDeprecated();
                return this;
            }

            @Deprecated
            public boolean hasDeprecatedLegacyJsonFieldConflicts() {
                return ((EnumOptions) this.instance).hasDeprecatedLegacyJsonFieldConflicts();
            }

            @Deprecated
            public boolean getDeprecatedLegacyJsonFieldConflicts() {
                return ((EnumOptions) this.instance).getDeprecatedLegacyJsonFieldConflicts();
            }

            @Deprecated
            public Builder setDeprecatedLegacyJsonFieldConflicts(boolean value) {
                copyOnWrite();
                ((EnumOptions) this.instance).setDeprecatedLegacyJsonFieldConflicts(value);
                return this;
            }

            @Deprecated
            public Builder clearDeprecatedLegacyJsonFieldConflicts() {
                copyOnWrite();
                ((EnumOptions) this.instance).clearDeprecatedLegacyJsonFieldConflicts();
                return this;
            }

            public boolean hasFeatures() {
                return ((EnumOptions) this.instance).hasFeatures();
            }

            public FeatureSet getFeatures() {
                return ((EnumOptions) this.instance).getFeatures();
            }

            public Builder setFeatures(FeatureSet value) {
                copyOnWrite();
                ((EnumOptions) this.instance).setFeatures(value);
                return this;
            }

            public Builder setFeatures(FeatureSet.Builder builderForValue) {
                copyOnWrite();
                ((EnumOptions) this.instance).setFeatures((FeatureSet) builderForValue.build());
                return this;
            }

            public Builder mergeFeatures(FeatureSet value) {
                copyOnWrite();
                ((EnumOptions) this.instance).mergeFeatures(value);
                return this;
            }

            public Builder clearFeatures() {
                copyOnWrite();
                ((EnumOptions) this.instance).clearFeatures();
                return this;
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                return Collections.unmodifiableList(((EnumOptions) this.instance).getUninterpretedOptionList());
            }

            public int getUninterpretedOptionCount() {
                return ((EnumOptions) this.instance).getUninterpretedOptionCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                return ((EnumOptions) this.instance).getUninterpretedOption(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((EnumOptions) this.instance).setUninterpretedOption(index, value);
                return this;
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((EnumOptions) this.instance).setUninterpretedOption(index, (UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                copyOnWrite();
                ((EnumOptions) this.instance).addUninterpretedOption(value);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((EnumOptions) this.instance).addUninterpretedOption(index, value);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((EnumOptions) this.instance).addUninterpretedOption((UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((EnumOptions) this.instance).addUninterpretedOption(index, (UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                copyOnWrite();
                ((EnumOptions) this.instance).addAllUninterpretedOption(values);
                return this;
            }

            public Builder clearUninterpretedOption() {
                copyOnWrite();
                ((EnumOptions) this.instance).clearUninterpretedOption();
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                copyOnWrite();
                ((EnumOptions) this.instance).removeUninterpretedOption(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new EnumOptions();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0005\u0000\u0001\u0002ϧ\u0005\u0000\u0001\u0002\u0002ဇ\u0000\u0003ဇ\u0001\u0006ဇ\u0002\u0007ᐉ\u0003ϧЛ", new Object[]{"bitField0_", "allowAlias_", "deprecated_", "deprecatedLegacyJsonFieldConflicts_", "features_", "uninterpretedOption_", UninterpretedOption.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<EnumOptions> parser = PARSER;
                    if (parser == null) {
                        synchronized (EnumOptions.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            EnumOptions defaultInstance = new EnumOptions();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(EnumOptions.class, defaultInstance);
        }

        public static EnumOptions getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<EnumOptions> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class EnumValueOptions extends GeneratedMessageLite.ExtendableMessage<EnumValueOptions, Builder> implements EnumValueOptionsOrBuilder {
        public static final int DEBUG_REDACT_FIELD_NUMBER = 3;
        /* access modifiers changed from: private */
        public static final EnumValueOptions DEFAULT_INSTANCE;
        public static final int DEPRECATED_FIELD_NUMBER = 1;
        public static final int FEATURES_FIELD_NUMBER = 2;
        public static final int FEATURE_SUPPORT_FIELD_NUMBER = 4;
        private static volatile Parser<EnumValueOptions> PARSER = null;
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        private int bitField0_;
        private boolean debugRedact_;
        private boolean deprecated_;
        private FieldOptions.FeatureSupport featureSupport_;
        private FeatureSet features_;
        private byte memoizedIsInitialized = 2;
        private Internal.ProtobufList<UninterpretedOption> uninterpretedOption_ = emptyProtobufList();

        private EnumValueOptions() {
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 1) != 0;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        /* access modifiers changed from: private */
        public void setDeprecated(boolean value) {
            this.bitField0_ |= 1;
            this.deprecated_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeprecated() {
            this.bitField0_ &= -2;
            this.deprecated_ = false;
        }

        public boolean hasFeatures() {
            return (this.bitField0_ & 2) != 0;
        }

        public FeatureSet getFeatures() {
            return this.features_ == null ? FeatureSet.getDefaultInstance() : this.features_;
        }

        /* access modifiers changed from: private */
        public void setFeatures(FeatureSet value) {
            value.getClass();
            this.features_ = value;
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeFeatures(FeatureSet value) {
            value.getClass();
            if (this.features_ == null || this.features_ == FeatureSet.getDefaultInstance()) {
                this.features_ = value;
            } else {
                this.features_ = (FeatureSet) ((FeatureSet.Builder) FeatureSet.newBuilder(this.features_).mergeFrom(value)).buildPartial();
            }
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void clearFeatures() {
            this.features_ = null;
            this.bitField0_ &= -3;
        }

        public boolean hasDebugRedact() {
            return (this.bitField0_ & 4) != 0;
        }

        public boolean getDebugRedact() {
            return this.debugRedact_;
        }

        /* access modifiers changed from: private */
        public void setDebugRedact(boolean value) {
            this.bitField0_ |= 4;
            this.debugRedact_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDebugRedact() {
            this.bitField0_ &= -5;
            this.debugRedact_ = false;
        }

        public boolean hasFeatureSupport() {
            return (this.bitField0_ & 8) != 0;
        }

        public FieldOptions.FeatureSupport getFeatureSupport() {
            return this.featureSupport_ == null ? FieldOptions.FeatureSupport.getDefaultInstance() : this.featureSupport_;
        }

        /* access modifiers changed from: private */
        public void setFeatureSupport(FieldOptions.FeatureSupport value) {
            value.getClass();
            this.featureSupport_ = value;
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void mergeFeatureSupport(FieldOptions.FeatureSupport value) {
            value.getClass();
            if (this.featureSupport_ == null || this.featureSupport_ == FieldOptions.FeatureSupport.getDefaultInstance()) {
                this.featureSupport_ = value;
            } else {
                this.featureSupport_ = (FieldOptions.FeatureSupport) ((FieldOptions.FeatureSupport.Builder) FieldOptions.FeatureSupport.newBuilder(this.featureSupport_).mergeFrom(value)).buildPartial();
            }
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void clearFeatureSupport() {
            this.featureSupport_ = null;
            this.bitField0_ &= -9;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return (UninterpretedOption) this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
        }

        private void ensureUninterpretedOptionIsMutable() {
            Internal.ProtobufList<UninterpretedOption> tmp = this.uninterpretedOption_;
            if (!tmp.isModifiable()) {
                this.uninterpretedOption_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(value);
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
            ensureUninterpretedOptionIsMutable();
            AbstractMessageLite.addAll(values, this.uninterpretedOption_);
        }

        /* access modifiers changed from: private */
        public void clearUninterpretedOption() {
            this.uninterpretedOption_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeUninterpretedOption(int index) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.remove(index);
        }

        public static EnumValueOptions parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (EnumValueOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumValueOptions parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumValueOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumValueOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (EnumValueOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumValueOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumValueOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumValueOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (EnumValueOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static EnumValueOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumValueOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static EnumValueOptions parseFrom(InputStream input) throws IOException {
            return (EnumValueOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumValueOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumValueOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static EnumValueOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (EnumValueOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumValueOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumValueOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static EnumValueOptions parseFrom(CodedInputStream input) throws IOException {
            return (EnumValueOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static EnumValueOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumValueOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(EnumValueOptions prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<EnumValueOptions, Builder> implements EnumValueOptionsOrBuilder {
            private Builder() {
                super(EnumValueOptions.DEFAULT_INSTANCE);
            }

            public boolean hasDeprecated() {
                return ((EnumValueOptions) this.instance).hasDeprecated();
            }

            public boolean getDeprecated() {
                return ((EnumValueOptions) this.instance).getDeprecated();
            }

            public Builder setDeprecated(boolean value) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).setDeprecated(value);
                return this;
            }

            public Builder clearDeprecated() {
                copyOnWrite();
                ((EnumValueOptions) this.instance).clearDeprecated();
                return this;
            }

            public boolean hasFeatures() {
                return ((EnumValueOptions) this.instance).hasFeatures();
            }

            public FeatureSet getFeatures() {
                return ((EnumValueOptions) this.instance).getFeatures();
            }

            public Builder setFeatures(FeatureSet value) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).setFeatures(value);
                return this;
            }

            public Builder setFeatures(FeatureSet.Builder builderForValue) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).setFeatures((FeatureSet) builderForValue.build());
                return this;
            }

            public Builder mergeFeatures(FeatureSet value) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).mergeFeatures(value);
                return this;
            }

            public Builder clearFeatures() {
                copyOnWrite();
                ((EnumValueOptions) this.instance).clearFeatures();
                return this;
            }

            public boolean hasDebugRedact() {
                return ((EnumValueOptions) this.instance).hasDebugRedact();
            }

            public boolean getDebugRedact() {
                return ((EnumValueOptions) this.instance).getDebugRedact();
            }

            public Builder setDebugRedact(boolean value) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).setDebugRedact(value);
                return this;
            }

            public Builder clearDebugRedact() {
                copyOnWrite();
                ((EnumValueOptions) this.instance).clearDebugRedact();
                return this;
            }

            public boolean hasFeatureSupport() {
                return ((EnumValueOptions) this.instance).hasFeatureSupport();
            }

            public FieldOptions.FeatureSupport getFeatureSupport() {
                return ((EnumValueOptions) this.instance).getFeatureSupport();
            }

            public Builder setFeatureSupport(FieldOptions.FeatureSupport value) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).setFeatureSupport(value);
                return this;
            }

            public Builder setFeatureSupport(FieldOptions.FeatureSupport.Builder builderForValue) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).setFeatureSupport((FieldOptions.FeatureSupport) builderForValue.build());
                return this;
            }

            public Builder mergeFeatureSupport(FieldOptions.FeatureSupport value) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).mergeFeatureSupport(value);
                return this;
            }

            public Builder clearFeatureSupport() {
                copyOnWrite();
                ((EnumValueOptions) this.instance).clearFeatureSupport();
                return this;
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                return Collections.unmodifiableList(((EnumValueOptions) this.instance).getUninterpretedOptionList());
            }

            public int getUninterpretedOptionCount() {
                return ((EnumValueOptions) this.instance).getUninterpretedOptionCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                return ((EnumValueOptions) this.instance).getUninterpretedOption(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).setUninterpretedOption(index, value);
                return this;
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).setUninterpretedOption(index, (UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).addUninterpretedOption(value);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).addUninterpretedOption(index, value);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).addUninterpretedOption((UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).addUninterpretedOption(index, (UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).addAllUninterpretedOption(values);
                return this;
            }

            public Builder clearUninterpretedOption() {
                copyOnWrite();
                ((EnumValueOptions) this.instance).clearUninterpretedOption();
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                copyOnWrite();
                ((EnumValueOptions) this.instance).removeUninterpretedOption(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new EnumValueOptions();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0005\u0000\u0001\u0001ϧ\u0005\u0000\u0001\u0002\u0001ဇ\u0000\u0002ᐉ\u0001\u0003ဇ\u0002\u0004ဉ\u0003ϧЛ", new Object[]{"bitField0_", "deprecated_", "features_", "debugRedact_", "featureSupport_", "uninterpretedOption_", UninterpretedOption.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<EnumValueOptions> parser = PARSER;
                    if (parser == null) {
                        synchronized (EnumValueOptions.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            EnumValueOptions defaultInstance = new EnumValueOptions();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(EnumValueOptions.class, defaultInstance);
        }

        public static EnumValueOptions getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<EnumValueOptions> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class ServiceOptions extends GeneratedMessageLite.ExtendableMessage<ServiceOptions, Builder> implements ServiceOptionsOrBuilder {
        /* access modifiers changed from: private */
        public static final ServiceOptions DEFAULT_INSTANCE;
        public static final int DEPRECATED_FIELD_NUMBER = 33;
        public static final int FEATURES_FIELD_NUMBER = 34;
        private static volatile Parser<ServiceOptions> PARSER = null;
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        private int bitField0_;
        private boolean deprecated_;
        private FeatureSet features_;
        private byte memoizedIsInitialized = 2;
        private Internal.ProtobufList<UninterpretedOption> uninterpretedOption_ = emptyProtobufList();

        private ServiceOptions() {
        }

        public boolean hasFeatures() {
            return (this.bitField0_ & 1) != 0;
        }

        public FeatureSet getFeatures() {
            return this.features_ == null ? FeatureSet.getDefaultInstance() : this.features_;
        }

        /* access modifiers changed from: private */
        public void setFeatures(FeatureSet value) {
            value.getClass();
            this.features_ = value;
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeFeatures(FeatureSet value) {
            value.getClass();
            if (this.features_ == null || this.features_ == FeatureSet.getDefaultInstance()) {
                this.features_ = value;
            } else {
                this.features_ = (FeatureSet) ((FeatureSet.Builder) FeatureSet.newBuilder(this.features_).mergeFrom(value)).buildPartial();
            }
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void clearFeatures() {
            this.features_ = null;
            this.bitField0_ &= -2;
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 2) != 0;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        /* access modifiers changed from: private */
        public void setDeprecated(boolean value) {
            this.bitField0_ |= 2;
            this.deprecated_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeprecated() {
            this.bitField0_ &= -3;
            this.deprecated_ = false;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return (UninterpretedOption) this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
        }

        private void ensureUninterpretedOptionIsMutable() {
            Internal.ProtobufList<UninterpretedOption> tmp = this.uninterpretedOption_;
            if (!tmp.isModifiable()) {
                this.uninterpretedOption_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(value);
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
            ensureUninterpretedOptionIsMutable();
            AbstractMessageLite.addAll(values, this.uninterpretedOption_);
        }

        /* access modifiers changed from: private */
        public void clearUninterpretedOption() {
            this.uninterpretedOption_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeUninterpretedOption(int index) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.remove(index);
        }

        public static ServiceOptions parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ServiceOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ServiceOptions parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServiceOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ServiceOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ServiceOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ServiceOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServiceOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ServiceOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ServiceOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ServiceOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServiceOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ServiceOptions parseFrom(InputStream input) throws IOException {
            return (ServiceOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ServiceOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServiceOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ServiceOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (ServiceOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ServiceOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServiceOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ServiceOptions parseFrom(CodedInputStream input) throws IOException {
            return (ServiceOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ServiceOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServiceOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ServiceOptions prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<ServiceOptions, Builder> implements ServiceOptionsOrBuilder {
            private Builder() {
                super(ServiceOptions.DEFAULT_INSTANCE);
            }

            public boolean hasFeatures() {
                return ((ServiceOptions) this.instance).hasFeatures();
            }

            public FeatureSet getFeatures() {
                return ((ServiceOptions) this.instance).getFeatures();
            }

            public Builder setFeatures(FeatureSet value) {
                copyOnWrite();
                ((ServiceOptions) this.instance).setFeatures(value);
                return this;
            }

            public Builder setFeatures(FeatureSet.Builder builderForValue) {
                copyOnWrite();
                ((ServiceOptions) this.instance).setFeatures((FeatureSet) builderForValue.build());
                return this;
            }

            public Builder mergeFeatures(FeatureSet value) {
                copyOnWrite();
                ((ServiceOptions) this.instance).mergeFeatures(value);
                return this;
            }

            public Builder clearFeatures() {
                copyOnWrite();
                ((ServiceOptions) this.instance).clearFeatures();
                return this;
            }

            public boolean hasDeprecated() {
                return ((ServiceOptions) this.instance).hasDeprecated();
            }

            public boolean getDeprecated() {
                return ((ServiceOptions) this.instance).getDeprecated();
            }

            public Builder setDeprecated(boolean value) {
                copyOnWrite();
                ((ServiceOptions) this.instance).setDeprecated(value);
                return this;
            }

            public Builder clearDeprecated() {
                copyOnWrite();
                ((ServiceOptions) this.instance).clearDeprecated();
                return this;
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                return Collections.unmodifiableList(((ServiceOptions) this.instance).getUninterpretedOptionList());
            }

            public int getUninterpretedOptionCount() {
                return ((ServiceOptions) this.instance).getUninterpretedOptionCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                return ((ServiceOptions) this.instance).getUninterpretedOption(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((ServiceOptions) this.instance).setUninterpretedOption(index, value);
                return this;
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((ServiceOptions) this.instance).setUninterpretedOption(index, (UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                copyOnWrite();
                ((ServiceOptions) this.instance).addUninterpretedOption(value);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((ServiceOptions) this.instance).addUninterpretedOption(index, value);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((ServiceOptions) this.instance).addUninterpretedOption((UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((ServiceOptions) this.instance).addUninterpretedOption(index, (UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                copyOnWrite();
                ((ServiceOptions) this.instance).addAllUninterpretedOption(values);
                return this;
            }

            public Builder clearUninterpretedOption() {
                copyOnWrite();
                ((ServiceOptions) this.instance).clearUninterpretedOption();
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                copyOnWrite();
                ((ServiceOptions) this.instance).removeUninterpretedOption(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new ServiceOptions();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001!ϧ\u0003\u0000\u0001\u0002!ဇ\u0001\"ᐉ\u0000ϧЛ", new Object[]{"bitField0_", "deprecated_", "features_", "uninterpretedOption_", UninterpretedOption.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<ServiceOptions> parser = PARSER;
                    if (parser == null) {
                        synchronized (ServiceOptions.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            ServiceOptions defaultInstance = new ServiceOptions();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(ServiceOptions.class, defaultInstance);
        }

        public static ServiceOptions getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ServiceOptions> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class MethodOptions extends GeneratedMessageLite.ExtendableMessage<MethodOptions, Builder> implements MethodOptionsOrBuilder {
        /* access modifiers changed from: private */
        public static final MethodOptions DEFAULT_INSTANCE;
        public static final int DEPRECATED_FIELD_NUMBER = 33;
        public static final int FEATURES_FIELD_NUMBER = 35;
        public static final int IDEMPOTENCY_LEVEL_FIELD_NUMBER = 34;
        private static volatile Parser<MethodOptions> PARSER = null;
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        private int bitField0_;
        private boolean deprecated_;
        private FeatureSet features_;
        private int idempotencyLevel_;
        private byte memoizedIsInitialized = 2;
        private Internal.ProtobufList<UninterpretedOption> uninterpretedOption_ = emptyProtobufList();

        private MethodOptions() {
        }

        public enum IdempotencyLevel implements Internal.EnumLite {
            IDEMPOTENCY_UNKNOWN(0),
            NO_SIDE_EFFECTS(1),
            IDEMPOTENT(2);
            
            public static final int IDEMPOTENCY_UNKNOWN_VALUE = 0;
            public static final int IDEMPOTENT_VALUE = 2;
            public static final int NO_SIDE_EFFECTS_VALUE = 1;
            private static final Internal.EnumLiteMap<IdempotencyLevel> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new Internal.EnumLiteMap<IdempotencyLevel>() {
                    public IdempotencyLevel findValueByNumber(int number) {
                        return IdempotencyLevel.forNumber(number);
                    }
                };
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static IdempotencyLevel valueOf(int value2) {
                return forNumber(value2);
            }

            public static IdempotencyLevel forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return IDEMPOTENCY_UNKNOWN;
                    case 1:
                        return NO_SIDE_EFFECTS;
                    case 2:
                        return IDEMPOTENT;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<IdempotencyLevel> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return IdempotencyLevelVerifier.INSTANCE;
            }

            private static final class IdempotencyLevelVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = null;

                private IdempotencyLevelVerifier() {
                }

                static {
                    INSTANCE = new IdempotencyLevelVerifier();
                }

                public boolean isInRange(int number) {
                    return IdempotencyLevel.forNumber(number) != null;
                }
            }

            private IdempotencyLevel(int value2) {
                this.value = value2;
            }
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 1) != 0;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        /* access modifiers changed from: private */
        public void setDeprecated(boolean value) {
            this.bitField0_ |= 1;
            this.deprecated_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDeprecated() {
            this.bitField0_ &= -2;
            this.deprecated_ = false;
        }

        public boolean hasIdempotencyLevel() {
            return (this.bitField0_ & 2) != 0;
        }

        public IdempotencyLevel getIdempotencyLevel() {
            IdempotencyLevel result = IdempotencyLevel.forNumber(this.idempotencyLevel_);
            return result == null ? IdempotencyLevel.IDEMPOTENCY_UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setIdempotencyLevel(IdempotencyLevel value) {
            this.idempotencyLevel_ = value.getNumber();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void clearIdempotencyLevel() {
            this.bitField0_ &= -3;
            this.idempotencyLevel_ = 0;
        }

        public boolean hasFeatures() {
            return (this.bitField0_ & 4) != 0;
        }

        public FeatureSet getFeatures() {
            return this.features_ == null ? FeatureSet.getDefaultInstance() : this.features_;
        }

        /* access modifiers changed from: private */
        public void setFeatures(FeatureSet value) {
            value.getClass();
            this.features_ = value;
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void mergeFeatures(FeatureSet value) {
            value.getClass();
            if (this.features_ == null || this.features_ == FeatureSet.getDefaultInstance()) {
                this.features_ = value;
            } else {
                this.features_ = (FeatureSet) ((FeatureSet.Builder) FeatureSet.newBuilder(this.features_).mergeFrom(value)).buildPartial();
            }
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void clearFeatures() {
            this.features_ = null;
            this.bitField0_ &= -5;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return (UninterpretedOption) this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
        }

        private void ensureUninterpretedOptionIsMutable() {
            Internal.ProtobufList<UninterpretedOption> tmp = this.uninterpretedOption_;
            if (!tmp.isModifiable()) {
                this.uninterpretedOption_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setUninterpretedOption(int index, UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(value);
        }

        /* access modifiers changed from: private */
        public void addUninterpretedOption(int index, UninterpretedOption value) {
            value.getClass();
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
            ensureUninterpretedOptionIsMutable();
            AbstractMessageLite.addAll(values, this.uninterpretedOption_);
        }

        /* access modifiers changed from: private */
        public void clearUninterpretedOption() {
            this.uninterpretedOption_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeUninterpretedOption(int index) {
            ensureUninterpretedOptionIsMutable();
            this.uninterpretedOption_.remove(index);
        }

        public static MethodOptions parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (MethodOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MethodOptions parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MethodOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MethodOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MethodOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MethodOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MethodOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MethodOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MethodOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MethodOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MethodOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MethodOptions parseFrom(InputStream input) throws IOException {
            return (MethodOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MethodOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MethodOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MethodOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (MethodOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static MethodOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MethodOptions) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MethodOptions parseFrom(CodedInputStream input) throws IOException {
            return (MethodOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MethodOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MethodOptions) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(MethodOptions prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<MethodOptions, Builder> implements MethodOptionsOrBuilder {
            private Builder() {
                super(MethodOptions.DEFAULT_INSTANCE);
            }

            public boolean hasDeprecated() {
                return ((MethodOptions) this.instance).hasDeprecated();
            }

            public boolean getDeprecated() {
                return ((MethodOptions) this.instance).getDeprecated();
            }

            public Builder setDeprecated(boolean value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setDeprecated(value);
                return this;
            }

            public Builder clearDeprecated() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearDeprecated();
                return this;
            }

            public boolean hasIdempotencyLevel() {
                return ((MethodOptions) this.instance).hasIdempotencyLevel();
            }

            public IdempotencyLevel getIdempotencyLevel() {
                return ((MethodOptions) this.instance).getIdempotencyLevel();
            }

            public Builder setIdempotencyLevel(IdempotencyLevel value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setIdempotencyLevel(value);
                return this;
            }

            public Builder clearIdempotencyLevel() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearIdempotencyLevel();
                return this;
            }

            public boolean hasFeatures() {
                return ((MethodOptions) this.instance).hasFeatures();
            }

            public FeatureSet getFeatures() {
                return ((MethodOptions) this.instance).getFeatures();
            }

            public Builder setFeatures(FeatureSet value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setFeatures(value);
                return this;
            }

            public Builder setFeatures(FeatureSet.Builder builderForValue) {
                copyOnWrite();
                ((MethodOptions) this.instance).setFeatures((FeatureSet) builderForValue.build());
                return this;
            }

            public Builder mergeFeatures(FeatureSet value) {
                copyOnWrite();
                ((MethodOptions) this.instance).mergeFeatures(value);
                return this;
            }

            public Builder clearFeatures() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearFeatures();
                return this;
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                return Collections.unmodifiableList(((MethodOptions) this.instance).getUninterpretedOptionList());
            }

            public int getUninterpretedOptionCount() {
                return ((MethodOptions) this.instance).getUninterpretedOptionCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                return ((MethodOptions) this.instance).getUninterpretedOption(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((MethodOptions) this.instance).setUninterpretedOption(index, value);
                return this;
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((MethodOptions) this.instance).setUninterpretedOption(index, (UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                copyOnWrite();
                ((MethodOptions) this.instance).addUninterpretedOption(value);
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                copyOnWrite();
                ((MethodOptions) this.instance).addUninterpretedOption(index, value);
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((MethodOptions) this.instance).addUninterpretedOption((UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption.Builder builderForValue) {
                copyOnWrite();
                ((MethodOptions) this.instance).addUninterpretedOption(index, (UninterpretedOption) builderForValue.build());
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                copyOnWrite();
                ((MethodOptions) this.instance).addAllUninterpretedOption(values);
                return this;
            }

            public Builder clearUninterpretedOption() {
                copyOnWrite();
                ((MethodOptions) this.instance).clearUninterpretedOption();
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                copyOnWrite();
                ((MethodOptions) this.instance).removeUninterpretedOption(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new MethodOptions();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001!ϧ\u0004\u0000\u0001\u0002!ဇ\u0000\"᠌\u0001#ᐉ\u0002ϧЛ", new Object[]{"bitField0_", "deprecated_", "idempotencyLevel_", IdempotencyLevel.internalGetVerifier(), "features_", "uninterpretedOption_", UninterpretedOption.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<MethodOptions> parser = PARSER;
                    if (parser == null) {
                        synchronized (MethodOptions.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            MethodOptions defaultInstance = new MethodOptions();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(MethodOptions.class, defaultInstance);
        }

        public static MethodOptions getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MethodOptions> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class UninterpretedOption extends GeneratedMessageLite<UninterpretedOption, Builder> implements UninterpretedOptionOrBuilder {
        public static final int AGGREGATE_VALUE_FIELD_NUMBER = 8;
        /* access modifiers changed from: private */
        public static final UninterpretedOption DEFAULT_INSTANCE;
        public static final int DOUBLE_VALUE_FIELD_NUMBER = 6;
        public static final int IDENTIFIER_VALUE_FIELD_NUMBER = 3;
        public static final int NAME_FIELD_NUMBER = 2;
        public static final int NEGATIVE_INT_VALUE_FIELD_NUMBER = 5;
        private static volatile Parser<UninterpretedOption> PARSER = null;
        public static final int POSITIVE_INT_VALUE_FIELD_NUMBER = 4;
        public static final int STRING_VALUE_FIELD_NUMBER = 7;
        private String aggregateValue_ = "";
        private int bitField0_;
        private double doubleValue_;
        private String identifierValue_ = "";
        private byte memoizedIsInitialized = 2;
        private Internal.ProtobufList<NamePart> name_ = emptyProtobufList();
        private long negativeIntValue_;
        private long positiveIntValue_;
        private ByteString stringValue_ = ByteString.EMPTY;

        public interface NamePartOrBuilder extends MessageLiteOrBuilder {
            boolean getIsExtension();

            String getNamePart();

            ByteString getNamePartBytes();

            boolean hasIsExtension();

            boolean hasNamePart();
        }

        private UninterpretedOption() {
        }

        public static final class NamePart extends GeneratedMessageLite<NamePart, Builder> implements NamePartOrBuilder {
            /* access modifiers changed from: private */
            public static final NamePart DEFAULT_INSTANCE;
            public static final int IS_EXTENSION_FIELD_NUMBER = 2;
            public static final int NAME_PART_FIELD_NUMBER = 1;
            private static volatile Parser<NamePart> PARSER;
            private int bitField0_;
            private boolean isExtension_;
            private byte memoizedIsInitialized = 2;
            private String namePart_ = "";

            private NamePart() {
            }

            public boolean hasNamePart() {
                return (this.bitField0_ & 1) != 0;
            }

            public String getNamePart() {
                return this.namePart_;
            }

            public ByteString getNamePartBytes() {
                return ByteString.copyFromUtf8(this.namePart_);
            }

            /* access modifiers changed from: private */
            public void setNamePart(String value) {
                Class<?> cls = value.getClass();
                this.bitField0_ |= 1;
                this.namePart_ = value;
            }

            /* access modifiers changed from: private */
            public void clearNamePart() {
                this.bitField0_ &= -2;
                this.namePart_ = getDefaultInstance().getNamePart();
            }

            /* access modifiers changed from: private */
            public void setNamePartBytes(ByteString value) {
                this.namePart_ = value.toStringUtf8();
                this.bitField0_ |= 1;
            }

            public boolean hasIsExtension() {
                return (this.bitField0_ & 2) != 0;
            }

            public boolean getIsExtension() {
                return this.isExtension_;
            }

            /* access modifiers changed from: private */
            public void setIsExtension(boolean value) {
                this.bitField0_ |= 2;
                this.isExtension_ = value;
            }

            /* access modifiers changed from: private */
            public void clearIsExtension() {
                this.bitField0_ &= -3;
                this.isExtension_ = false;
            }

            public static NamePart parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (NamePart) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static NamePart parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (NamePart) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static NamePart parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (NamePart) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static NamePart parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (NamePart) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static NamePart parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (NamePart) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static NamePart parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (NamePart) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static NamePart parseFrom(InputStream input) throws IOException {
                return (NamePart) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static NamePart parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (NamePart) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static NamePart parseDelimitedFrom(InputStream input) throws IOException {
                return (NamePart) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static NamePart parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (NamePart) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static NamePart parseFrom(CodedInputStream input) throws IOException {
                return (NamePart) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static NamePart parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (NamePart) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(NamePart prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<NamePart, Builder> implements NamePartOrBuilder {
                private Builder() {
                    super(NamePart.DEFAULT_INSTANCE);
                }

                public boolean hasNamePart() {
                    return ((NamePart) this.instance).hasNamePart();
                }

                public String getNamePart() {
                    return ((NamePart) this.instance).getNamePart();
                }

                public ByteString getNamePartBytes() {
                    return ((NamePart) this.instance).getNamePartBytes();
                }

                public Builder setNamePart(String value) {
                    copyOnWrite();
                    ((NamePart) this.instance).setNamePart(value);
                    return this;
                }

                public Builder clearNamePart() {
                    copyOnWrite();
                    ((NamePart) this.instance).clearNamePart();
                    return this;
                }

                public Builder setNamePartBytes(ByteString value) {
                    copyOnWrite();
                    ((NamePart) this.instance).setNamePartBytes(value);
                    return this;
                }

                public boolean hasIsExtension() {
                    return ((NamePart) this.instance).hasIsExtension();
                }

                public boolean getIsExtension() {
                    return ((NamePart) this.instance).getIsExtension();
                }

                public Builder setIsExtension(boolean value) {
                    copyOnWrite();
                    ((NamePart) this.instance).setIsExtension(value);
                    return this;
                }

                public Builder clearIsExtension() {
                    copyOnWrite();
                    ((NamePart) this.instance).clearIsExtension();
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new NamePart();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0002\u0001ᔈ\u0000\u0002ᔇ\u0001", new Object[]{"bitField0_", "namePart_", "isExtension_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<NamePart> parser = PARSER;
                        if (parser == null) {
                            synchronized (NamePart.class) {
                                parser = PARSER;
                                if (parser == null) {
                                    parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = parser;
                                }
                            }
                        }
                        return parser;
                    case GET_MEMOIZED_IS_INITIALIZED:
                        return Byte.valueOf(this.memoizedIsInitialized);
                    case SET_MEMOIZED_IS_INITIALIZED:
                        this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                        return null;
                    default:
                        throw new UnsupportedOperationException();
                }
            }

            static {
                NamePart defaultInstance = new NamePart();
                DEFAULT_INSTANCE = defaultInstance;
                GeneratedMessageLite.registerDefaultInstance(NamePart.class, defaultInstance);
            }

            public static NamePart getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<NamePart> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public List<NamePart> getNameList() {
            return this.name_;
        }

        public List<? extends NamePartOrBuilder> getNameOrBuilderList() {
            return this.name_;
        }

        public int getNameCount() {
            return this.name_.size();
        }

        public NamePart getName(int index) {
            return (NamePart) this.name_.get(index);
        }

        public NamePartOrBuilder getNameOrBuilder(int index) {
            return (NamePartOrBuilder) this.name_.get(index);
        }

        private void ensureNameIsMutable() {
            Internal.ProtobufList<NamePart> tmp = this.name_;
            if (!tmp.isModifiable()) {
                this.name_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setName(int index, NamePart value) {
            value.getClass();
            ensureNameIsMutable();
            this.name_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addName(NamePart value) {
            value.getClass();
            ensureNameIsMutable();
            this.name_.add(value);
        }

        /* access modifiers changed from: private */
        public void addName(int index, NamePart value) {
            value.getClass();
            ensureNameIsMutable();
            this.name_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllName(Iterable<? extends NamePart> values) {
            ensureNameIsMutable();
            AbstractMessageLite.addAll(values, this.name_);
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.name_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeName(int index) {
            ensureNameIsMutable();
            this.name_.remove(index);
        }

        public boolean hasIdentifierValue() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getIdentifierValue() {
            return this.identifierValue_;
        }

        public ByteString getIdentifierValueBytes() {
            return ByteString.copyFromUtf8(this.identifierValue_);
        }

        /* access modifiers changed from: private */
        public void setIdentifierValue(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 1;
            this.identifierValue_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIdentifierValue() {
            this.bitField0_ &= -2;
            this.identifierValue_ = getDefaultInstance().getIdentifierValue();
        }

        /* access modifiers changed from: private */
        public void setIdentifierValueBytes(ByteString value) {
            this.identifierValue_ = value.toStringUtf8();
            this.bitField0_ |= 1;
        }

        public boolean hasPositiveIntValue() {
            return (this.bitField0_ & 2) != 0;
        }

        public long getPositiveIntValue() {
            return this.positiveIntValue_;
        }

        /* access modifiers changed from: private */
        public void setPositiveIntValue(long value) {
            this.bitField0_ |= 2;
            this.positiveIntValue_ = value;
        }

        /* access modifiers changed from: private */
        public void clearPositiveIntValue() {
            this.bitField0_ &= -3;
            this.positiveIntValue_ = 0;
        }

        public boolean hasNegativeIntValue() {
            return (this.bitField0_ & 4) != 0;
        }

        public long getNegativeIntValue() {
            return this.negativeIntValue_;
        }

        /* access modifiers changed from: private */
        public void setNegativeIntValue(long value) {
            this.bitField0_ |= 4;
            this.negativeIntValue_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNegativeIntValue() {
            this.bitField0_ &= -5;
            this.negativeIntValue_ = 0;
        }

        public boolean hasDoubleValue() {
            return (this.bitField0_ & 8) != 0;
        }

        public double getDoubleValue() {
            return this.doubleValue_;
        }

        /* access modifiers changed from: private */
        public void setDoubleValue(double value) {
            this.bitField0_ |= 8;
            this.doubleValue_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDoubleValue() {
            this.bitField0_ &= -9;
            this.doubleValue_ = 0.0d;
        }

        public boolean hasStringValue() {
            return (this.bitField0_ & 16) != 0;
        }

        public ByteString getStringValue() {
            return this.stringValue_;
        }

        /* access modifiers changed from: private */
        public void setStringValue(ByteString value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 16;
            this.stringValue_ = value;
        }

        /* access modifiers changed from: private */
        public void clearStringValue() {
            this.bitField0_ &= -17;
            this.stringValue_ = getDefaultInstance().getStringValue();
        }

        public boolean hasAggregateValue() {
            return (this.bitField0_ & 32) != 0;
        }

        public String getAggregateValue() {
            return this.aggregateValue_;
        }

        public ByteString getAggregateValueBytes() {
            return ByteString.copyFromUtf8(this.aggregateValue_);
        }

        /* access modifiers changed from: private */
        public void setAggregateValue(String value) {
            Class<?> cls = value.getClass();
            this.bitField0_ |= 32;
            this.aggregateValue_ = value;
        }

        /* access modifiers changed from: private */
        public void clearAggregateValue() {
            this.bitField0_ &= -33;
            this.aggregateValue_ = getDefaultInstance().getAggregateValue();
        }

        /* access modifiers changed from: private */
        public void setAggregateValueBytes(ByteString value) {
            this.aggregateValue_ = value.toStringUtf8();
            this.bitField0_ |= 32;
        }

        public static UninterpretedOption parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (UninterpretedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static UninterpretedOption parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (UninterpretedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static UninterpretedOption parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (UninterpretedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static UninterpretedOption parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (UninterpretedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static UninterpretedOption parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (UninterpretedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static UninterpretedOption parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (UninterpretedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static UninterpretedOption parseFrom(InputStream input) throws IOException {
            return (UninterpretedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static UninterpretedOption parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (UninterpretedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static UninterpretedOption parseDelimitedFrom(InputStream input) throws IOException {
            return (UninterpretedOption) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static UninterpretedOption parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (UninterpretedOption) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static UninterpretedOption parseFrom(CodedInputStream input) throws IOException {
            return (UninterpretedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static UninterpretedOption parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (UninterpretedOption) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(UninterpretedOption prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<UninterpretedOption, Builder> implements UninterpretedOptionOrBuilder {
            private Builder() {
                super(UninterpretedOption.DEFAULT_INSTANCE);
            }

            public List<NamePart> getNameList() {
                return Collections.unmodifiableList(((UninterpretedOption) this.instance).getNameList());
            }

            public int getNameCount() {
                return ((UninterpretedOption) this.instance).getNameCount();
            }

            public NamePart getName(int index) {
                return ((UninterpretedOption) this.instance).getName(index);
            }

            public Builder setName(int index, NamePart value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).setName(index, value);
                return this;
            }

            public Builder setName(int index, NamePart.Builder builderForValue) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).setName(index, (NamePart) builderForValue.build());
                return this;
            }

            public Builder addName(NamePart value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).addName(value);
                return this;
            }

            public Builder addName(int index, NamePart value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).addName(index, value);
                return this;
            }

            public Builder addName(NamePart.Builder builderForValue) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).addName((NamePart) builderForValue.build());
                return this;
            }

            public Builder addName(int index, NamePart.Builder builderForValue) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).addName(index, (NamePart) builderForValue.build());
                return this;
            }

            public Builder addAllName(Iterable<? extends NamePart> values) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).addAllName(values);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((UninterpretedOption) this.instance).clearName();
                return this;
            }

            public Builder removeName(int index) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).removeName(index);
                return this;
            }

            public boolean hasIdentifierValue() {
                return ((UninterpretedOption) this.instance).hasIdentifierValue();
            }

            public String getIdentifierValue() {
                return ((UninterpretedOption) this.instance).getIdentifierValue();
            }

            public ByteString getIdentifierValueBytes() {
                return ((UninterpretedOption) this.instance).getIdentifierValueBytes();
            }

            public Builder setIdentifierValue(String value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).setIdentifierValue(value);
                return this;
            }

            public Builder clearIdentifierValue() {
                copyOnWrite();
                ((UninterpretedOption) this.instance).clearIdentifierValue();
                return this;
            }

            public Builder setIdentifierValueBytes(ByteString value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).setIdentifierValueBytes(value);
                return this;
            }

            public boolean hasPositiveIntValue() {
                return ((UninterpretedOption) this.instance).hasPositiveIntValue();
            }

            public long getPositiveIntValue() {
                return ((UninterpretedOption) this.instance).getPositiveIntValue();
            }

            public Builder setPositiveIntValue(long value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).setPositiveIntValue(value);
                return this;
            }

            public Builder clearPositiveIntValue() {
                copyOnWrite();
                ((UninterpretedOption) this.instance).clearPositiveIntValue();
                return this;
            }

            public boolean hasNegativeIntValue() {
                return ((UninterpretedOption) this.instance).hasNegativeIntValue();
            }

            public long getNegativeIntValue() {
                return ((UninterpretedOption) this.instance).getNegativeIntValue();
            }

            public Builder setNegativeIntValue(long value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).setNegativeIntValue(value);
                return this;
            }

            public Builder clearNegativeIntValue() {
                copyOnWrite();
                ((UninterpretedOption) this.instance).clearNegativeIntValue();
                return this;
            }

            public boolean hasDoubleValue() {
                return ((UninterpretedOption) this.instance).hasDoubleValue();
            }

            public double getDoubleValue() {
                return ((UninterpretedOption) this.instance).getDoubleValue();
            }

            public Builder setDoubleValue(double value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).setDoubleValue(value);
                return this;
            }

            public Builder clearDoubleValue() {
                copyOnWrite();
                ((UninterpretedOption) this.instance).clearDoubleValue();
                return this;
            }

            public boolean hasStringValue() {
                return ((UninterpretedOption) this.instance).hasStringValue();
            }

            public ByteString getStringValue() {
                return ((UninterpretedOption) this.instance).getStringValue();
            }

            public Builder setStringValue(ByteString value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).setStringValue(value);
                return this;
            }

            public Builder clearStringValue() {
                copyOnWrite();
                ((UninterpretedOption) this.instance).clearStringValue();
                return this;
            }

            public boolean hasAggregateValue() {
                return ((UninterpretedOption) this.instance).hasAggregateValue();
            }

            public String getAggregateValue() {
                return ((UninterpretedOption) this.instance).getAggregateValue();
            }

            public ByteString getAggregateValueBytes() {
                return ((UninterpretedOption) this.instance).getAggregateValueBytes();
            }

            public Builder setAggregateValue(String value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).setAggregateValue(value);
                return this;
            }

            public Builder clearAggregateValue() {
                copyOnWrite();
                ((UninterpretedOption) this.instance).clearAggregateValue();
                return this;
            }

            public Builder setAggregateValueBytes(ByteString value) {
                copyOnWrite();
                ((UninterpretedOption) this.instance).setAggregateValueBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new UninterpretedOption();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0007\u0000\u0001\u0002\b\u0007\u0000\u0001\u0001\u0002Л\u0003ဈ\u0000\u0004ဃ\u0001\u0005ဂ\u0002\u0006က\u0003\u0007ည\u0004\bဈ\u0005", new Object[]{"bitField0_", "name_", NamePart.class, "identifierValue_", "positiveIntValue_", "negativeIntValue_", "doubleValue_", "stringValue_", "aggregateValue_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<UninterpretedOption> parser = PARSER;
                    if (parser == null) {
                        synchronized (UninterpretedOption.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            UninterpretedOption defaultInstance = new UninterpretedOption();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(UninterpretedOption.class, defaultInstance);
        }

        public static UninterpretedOption getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<UninterpretedOption> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class FeatureSet extends GeneratedMessageLite.ExtendableMessage<FeatureSet, Builder> implements FeatureSetOrBuilder {
        /* access modifiers changed from: private */
        public static final FeatureSet DEFAULT_INSTANCE;
        public static final int ENUM_TYPE_FIELD_NUMBER = 2;
        public static final int FIELD_PRESENCE_FIELD_NUMBER = 1;
        public static final int JSON_FORMAT_FIELD_NUMBER = 6;
        public static final int MESSAGE_ENCODING_FIELD_NUMBER = 5;
        private static volatile Parser<FeatureSet> PARSER = null;
        public static final int REPEATED_FIELD_ENCODING_FIELD_NUMBER = 3;
        public static final int UTF8_VALIDATION_FIELD_NUMBER = 4;
        private int bitField0_;
        private int enumType_;
        private int fieldPresence_;
        private int jsonFormat_;
        private byte memoizedIsInitialized = 2;
        private int messageEncoding_;
        private int repeatedFieldEncoding_;
        private int utf8Validation_;

        private FeatureSet() {
        }

        public enum FieldPresence implements Internal.EnumLite {
            FIELD_PRESENCE_UNKNOWN(0),
            EXPLICIT(1),
            IMPLICIT(2),
            LEGACY_REQUIRED(3);
            
            public static final int EXPLICIT_VALUE = 1;
            public static final int FIELD_PRESENCE_UNKNOWN_VALUE = 0;
            public static final int IMPLICIT_VALUE = 2;
            public static final int LEGACY_REQUIRED_VALUE = 3;
            private static final Internal.EnumLiteMap<FieldPresence> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new Internal.EnumLiteMap<FieldPresence>() {
                    public FieldPresence findValueByNumber(int number) {
                        return FieldPresence.forNumber(number);
                    }
                };
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static FieldPresence valueOf(int value2) {
                return forNumber(value2);
            }

            public static FieldPresence forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return FIELD_PRESENCE_UNKNOWN;
                    case 1:
                        return EXPLICIT;
                    case 2:
                        return IMPLICIT;
                    case 3:
                        return LEGACY_REQUIRED;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<FieldPresence> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return FieldPresenceVerifier.INSTANCE;
            }

            private static final class FieldPresenceVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = null;

                private FieldPresenceVerifier() {
                }

                static {
                    INSTANCE = new FieldPresenceVerifier();
                }

                public boolean isInRange(int number) {
                    return FieldPresence.forNumber(number) != null;
                }
            }

            private FieldPresence(int value2) {
                this.value = value2;
            }
        }

        public enum EnumType implements Internal.EnumLite {
            ENUM_TYPE_UNKNOWN(0),
            OPEN(1),
            CLOSED(2);
            
            public static final int CLOSED_VALUE = 2;
            public static final int ENUM_TYPE_UNKNOWN_VALUE = 0;
            public static final int OPEN_VALUE = 1;
            private static final Internal.EnumLiteMap<EnumType> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new Internal.EnumLiteMap<EnumType>() {
                    public EnumType findValueByNumber(int number) {
                        return EnumType.forNumber(number);
                    }
                };
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static EnumType valueOf(int value2) {
                return forNumber(value2);
            }

            public static EnumType forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return ENUM_TYPE_UNKNOWN;
                    case 1:
                        return OPEN;
                    case 2:
                        return CLOSED;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<EnumType> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return EnumTypeVerifier.INSTANCE;
            }

            private static final class EnumTypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = null;

                private EnumTypeVerifier() {
                }

                static {
                    INSTANCE = new EnumTypeVerifier();
                }

                public boolean isInRange(int number) {
                    return EnumType.forNumber(number) != null;
                }
            }

            private EnumType(int value2) {
                this.value = value2;
            }
        }

        public enum RepeatedFieldEncoding implements Internal.EnumLite {
            REPEATED_FIELD_ENCODING_UNKNOWN(0),
            PACKED(1),
            EXPANDED(2);
            
            public static final int EXPANDED_VALUE = 2;
            public static final int PACKED_VALUE = 1;
            public static final int REPEATED_FIELD_ENCODING_UNKNOWN_VALUE = 0;
            private static final Internal.EnumLiteMap<RepeatedFieldEncoding> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new Internal.EnumLiteMap<RepeatedFieldEncoding>() {
                    public RepeatedFieldEncoding findValueByNumber(int number) {
                        return RepeatedFieldEncoding.forNumber(number);
                    }
                };
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static RepeatedFieldEncoding valueOf(int value2) {
                return forNumber(value2);
            }

            public static RepeatedFieldEncoding forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return REPEATED_FIELD_ENCODING_UNKNOWN;
                    case 1:
                        return PACKED;
                    case 2:
                        return EXPANDED;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<RepeatedFieldEncoding> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return RepeatedFieldEncodingVerifier.INSTANCE;
            }

            private static final class RepeatedFieldEncodingVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = null;

                private RepeatedFieldEncodingVerifier() {
                }

                static {
                    INSTANCE = new RepeatedFieldEncodingVerifier();
                }

                public boolean isInRange(int number) {
                    return RepeatedFieldEncoding.forNumber(number) != null;
                }
            }

            private RepeatedFieldEncoding(int value2) {
                this.value = value2;
            }
        }

        public enum Utf8Validation implements Internal.EnumLite {
            UTF8_VALIDATION_UNKNOWN(0),
            VERIFY(2),
            NONE(3);
            
            public static final int NONE_VALUE = 3;
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
                    case 2:
                        return VERIFY;
                    case 3:
                        return NONE;
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

        public enum MessageEncoding implements Internal.EnumLite {
            MESSAGE_ENCODING_UNKNOWN(0),
            LENGTH_PREFIXED(1),
            DELIMITED(2);
            
            public static final int DELIMITED_VALUE = 2;
            public static final int LENGTH_PREFIXED_VALUE = 1;
            public static final int MESSAGE_ENCODING_UNKNOWN_VALUE = 0;
            private static final Internal.EnumLiteMap<MessageEncoding> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new Internal.EnumLiteMap<MessageEncoding>() {
                    public MessageEncoding findValueByNumber(int number) {
                        return MessageEncoding.forNumber(number);
                    }
                };
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static MessageEncoding valueOf(int value2) {
                return forNumber(value2);
            }

            public static MessageEncoding forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return MESSAGE_ENCODING_UNKNOWN;
                    case 1:
                        return LENGTH_PREFIXED;
                    case 2:
                        return DELIMITED;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<MessageEncoding> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return MessageEncodingVerifier.INSTANCE;
            }

            private static final class MessageEncodingVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = null;

                private MessageEncodingVerifier() {
                }

                static {
                    INSTANCE = new MessageEncodingVerifier();
                }

                public boolean isInRange(int number) {
                    return MessageEncoding.forNumber(number) != null;
                }
            }

            private MessageEncoding(int value2) {
                this.value = value2;
            }
        }

        public enum JsonFormat implements Internal.EnumLite {
            JSON_FORMAT_UNKNOWN(0),
            ALLOW(1),
            LEGACY_BEST_EFFORT(2);
            
            public static final int ALLOW_VALUE = 1;
            public static final int JSON_FORMAT_UNKNOWN_VALUE = 0;
            public static final int LEGACY_BEST_EFFORT_VALUE = 2;
            private static final Internal.EnumLiteMap<JsonFormat> internalValueMap = null;
            private final int value;

            static {
                internalValueMap = new Internal.EnumLiteMap<JsonFormat>() {
                    public JsonFormat findValueByNumber(int number) {
                        return JsonFormat.forNumber(number);
                    }
                };
            }

            public final int getNumber() {
                return this.value;
            }

            @Deprecated
            public static JsonFormat valueOf(int value2) {
                return forNumber(value2);
            }

            public static JsonFormat forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return JSON_FORMAT_UNKNOWN;
                    case 1:
                        return ALLOW;
                    case 2:
                        return LEGACY_BEST_EFFORT;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<JsonFormat> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return JsonFormatVerifier.INSTANCE;
            }

            private static final class JsonFormatVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = null;

                private JsonFormatVerifier() {
                }

                static {
                    INSTANCE = new JsonFormatVerifier();
                }

                public boolean isInRange(int number) {
                    return JsonFormat.forNumber(number) != null;
                }
            }

            private JsonFormat(int value2) {
                this.value = value2;
            }
        }

        public boolean hasFieldPresence() {
            return (this.bitField0_ & 1) != 0;
        }

        public FieldPresence getFieldPresence() {
            FieldPresence result = FieldPresence.forNumber(this.fieldPresence_);
            return result == null ? FieldPresence.FIELD_PRESENCE_UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setFieldPresence(FieldPresence value) {
            this.fieldPresence_ = value.getNumber();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void clearFieldPresence() {
            this.bitField0_ &= -2;
            this.fieldPresence_ = 0;
        }

        public boolean hasEnumType() {
            return (this.bitField0_ & 2) != 0;
        }

        public EnumType getEnumType() {
            EnumType result = EnumType.forNumber(this.enumType_);
            return result == null ? EnumType.ENUM_TYPE_UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setEnumType(EnumType value) {
            this.enumType_ = value.getNumber();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void clearEnumType() {
            this.bitField0_ &= -3;
            this.enumType_ = 0;
        }

        public boolean hasRepeatedFieldEncoding() {
            return (this.bitField0_ & 4) != 0;
        }

        public RepeatedFieldEncoding getRepeatedFieldEncoding() {
            RepeatedFieldEncoding result = RepeatedFieldEncoding.forNumber(this.repeatedFieldEncoding_);
            return result == null ? RepeatedFieldEncoding.REPEATED_FIELD_ENCODING_UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setRepeatedFieldEncoding(RepeatedFieldEncoding value) {
            this.repeatedFieldEncoding_ = value.getNumber();
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void clearRepeatedFieldEncoding() {
            this.bitField0_ &= -5;
            this.repeatedFieldEncoding_ = 0;
        }

        public boolean hasUtf8Validation() {
            return (this.bitField0_ & 8) != 0;
        }

        public Utf8Validation getUtf8Validation() {
            Utf8Validation result = Utf8Validation.forNumber(this.utf8Validation_);
            return result == null ? Utf8Validation.UTF8_VALIDATION_UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setUtf8Validation(Utf8Validation value) {
            this.utf8Validation_ = value.getNumber();
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void clearUtf8Validation() {
            this.bitField0_ &= -9;
            this.utf8Validation_ = 0;
        }

        public boolean hasMessageEncoding() {
            return (this.bitField0_ & 16) != 0;
        }

        public MessageEncoding getMessageEncoding() {
            MessageEncoding result = MessageEncoding.forNumber(this.messageEncoding_);
            return result == null ? MessageEncoding.MESSAGE_ENCODING_UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setMessageEncoding(MessageEncoding value) {
            this.messageEncoding_ = value.getNumber();
            this.bitField0_ |= 16;
        }

        /* access modifiers changed from: private */
        public void clearMessageEncoding() {
            this.bitField0_ &= -17;
            this.messageEncoding_ = 0;
        }

        public boolean hasJsonFormat() {
            return (this.bitField0_ & 32) != 0;
        }

        public JsonFormat getJsonFormat() {
            JsonFormat result = JsonFormat.forNumber(this.jsonFormat_);
            return result == null ? JsonFormat.JSON_FORMAT_UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setJsonFormat(JsonFormat value) {
            this.jsonFormat_ = value.getNumber();
            this.bitField0_ |= 32;
        }

        /* access modifiers changed from: private */
        public void clearJsonFormat() {
            this.bitField0_ &= -33;
            this.jsonFormat_ = 0;
        }

        public static FeatureSet parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (FeatureSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FeatureSet parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FeatureSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FeatureSet parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FeatureSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FeatureSet parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FeatureSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FeatureSet parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FeatureSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FeatureSet parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FeatureSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FeatureSet parseFrom(InputStream input) throws IOException {
            return (FeatureSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FeatureSet parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FeatureSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FeatureSet parseDelimitedFrom(InputStream input) throws IOException {
            return (FeatureSet) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static FeatureSet parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FeatureSet) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FeatureSet parseFrom(CodedInputStream input) throws IOException {
            return (FeatureSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FeatureSet parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FeatureSet) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(FeatureSet prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.ExtendableBuilder<FeatureSet, Builder> implements FeatureSetOrBuilder {
            private Builder() {
                super(FeatureSet.DEFAULT_INSTANCE);
            }

            public boolean hasFieldPresence() {
                return ((FeatureSet) this.instance).hasFieldPresence();
            }

            public FieldPresence getFieldPresence() {
                return ((FeatureSet) this.instance).getFieldPresence();
            }

            public Builder setFieldPresence(FieldPresence value) {
                copyOnWrite();
                ((FeatureSet) this.instance).setFieldPresence(value);
                return this;
            }

            public Builder clearFieldPresence() {
                copyOnWrite();
                ((FeatureSet) this.instance).clearFieldPresence();
                return this;
            }

            public boolean hasEnumType() {
                return ((FeatureSet) this.instance).hasEnumType();
            }

            public EnumType getEnumType() {
                return ((FeatureSet) this.instance).getEnumType();
            }

            public Builder setEnumType(EnumType value) {
                copyOnWrite();
                ((FeatureSet) this.instance).setEnumType(value);
                return this;
            }

            public Builder clearEnumType() {
                copyOnWrite();
                ((FeatureSet) this.instance).clearEnumType();
                return this;
            }

            public boolean hasRepeatedFieldEncoding() {
                return ((FeatureSet) this.instance).hasRepeatedFieldEncoding();
            }

            public RepeatedFieldEncoding getRepeatedFieldEncoding() {
                return ((FeatureSet) this.instance).getRepeatedFieldEncoding();
            }

            public Builder setRepeatedFieldEncoding(RepeatedFieldEncoding value) {
                copyOnWrite();
                ((FeatureSet) this.instance).setRepeatedFieldEncoding(value);
                return this;
            }

            public Builder clearRepeatedFieldEncoding() {
                copyOnWrite();
                ((FeatureSet) this.instance).clearRepeatedFieldEncoding();
                return this;
            }

            public boolean hasUtf8Validation() {
                return ((FeatureSet) this.instance).hasUtf8Validation();
            }

            public Utf8Validation getUtf8Validation() {
                return ((FeatureSet) this.instance).getUtf8Validation();
            }

            public Builder setUtf8Validation(Utf8Validation value) {
                copyOnWrite();
                ((FeatureSet) this.instance).setUtf8Validation(value);
                return this;
            }

            public Builder clearUtf8Validation() {
                copyOnWrite();
                ((FeatureSet) this.instance).clearUtf8Validation();
                return this;
            }

            public boolean hasMessageEncoding() {
                return ((FeatureSet) this.instance).hasMessageEncoding();
            }

            public MessageEncoding getMessageEncoding() {
                return ((FeatureSet) this.instance).getMessageEncoding();
            }

            public Builder setMessageEncoding(MessageEncoding value) {
                copyOnWrite();
                ((FeatureSet) this.instance).setMessageEncoding(value);
                return this;
            }

            public Builder clearMessageEncoding() {
                copyOnWrite();
                ((FeatureSet) this.instance).clearMessageEncoding();
                return this;
            }

            public boolean hasJsonFormat() {
                return ((FeatureSet) this.instance).hasJsonFormat();
            }

            public JsonFormat getJsonFormat() {
                return ((FeatureSet) this.instance).getJsonFormat();
            }

            public Builder setJsonFormat(JsonFormat value) {
                copyOnWrite();
                ((FeatureSet) this.instance).setJsonFormat(value);
                return this;
            }

            public Builder clearJsonFormat() {
                copyOnWrite();
                ((FeatureSet) this.instance).clearJsonFormat();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new FeatureSet();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001᠌\u0000\u0002᠌\u0001\u0003᠌\u0002\u0004᠌\u0003\u0005᠌\u0004\u0006᠌\u0005", new Object[]{"bitField0_", "fieldPresence_", FieldPresence.internalGetVerifier(), "enumType_", EnumType.internalGetVerifier(), "repeatedFieldEncoding_", RepeatedFieldEncoding.internalGetVerifier(), "utf8Validation_", Utf8Validation.internalGetVerifier(), "messageEncoding_", MessageEncoding.internalGetVerifier(), "jsonFormat_", JsonFormat.internalGetVerifier()});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<FeatureSet> parser = PARSER;
                    if (parser == null) {
                        synchronized (FeatureSet.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            FeatureSet defaultInstance = new FeatureSet();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(FeatureSet.class, defaultInstance);
        }

        public static FeatureSet getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FeatureSet> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class FeatureSetDefaults extends GeneratedMessageLite<FeatureSetDefaults, Builder> implements FeatureSetDefaultsOrBuilder {
        public static final int DEFAULTS_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final FeatureSetDefaults DEFAULT_INSTANCE;
        public static final int MAXIMUM_EDITION_FIELD_NUMBER = 5;
        public static final int MINIMUM_EDITION_FIELD_NUMBER = 4;
        private static volatile Parser<FeatureSetDefaults> PARSER;
        private int bitField0_;
        private Internal.ProtobufList<FeatureSetEditionDefault> defaults_ = emptyProtobufList();
        private int maximumEdition_;
        private byte memoizedIsInitialized = 2;
        private int minimumEdition_;

        public interface FeatureSetEditionDefaultOrBuilder extends MessageLiteOrBuilder {
            Edition getEdition();

            FeatureSet getFixedFeatures();

            FeatureSet getOverridableFeatures();

            boolean hasEdition();

            boolean hasFixedFeatures();

            boolean hasOverridableFeatures();
        }

        private FeatureSetDefaults() {
        }

        public static final class FeatureSetEditionDefault extends GeneratedMessageLite<FeatureSetEditionDefault, Builder> implements FeatureSetEditionDefaultOrBuilder {
            /* access modifiers changed from: private */
            public static final FeatureSetEditionDefault DEFAULT_INSTANCE;
            public static final int EDITION_FIELD_NUMBER = 3;
            public static final int FIXED_FEATURES_FIELD_NUMBER = 5;
            public static final int OVERRIDABLE_FEATURES_FIELD_NUMBER = 4;
            private static volatile Parser<FeatureSetEditionDefault> PARSER;
            private int bitField0_;
            private int edition_;
            private FeatureSet fixedFeatures_;
            private byte memoizedIsInitialized = 2;
            private FeatureSet overridableFeatures_;

            private FeatureSetEditionDefault() {
            }

            public boolean hasEdition() {
                return (this.bitField0_ & 1) != 0;
            }

            public Edition getEdition() {
                Edition result = Edition.forNumber(this.edition_);
                return result == null ? Edition.EDITION_UNKNOWN : result;
            }

            /* access modifiers changed from: private */
            public void setEdition(Edition value) {
                this.edition_ = value.getNumber();
                this.bitField0_ |= 1;
            }

            /* access modifiers changed from: private */
            public void clearEdition() {
                this.bitField0_ &= -2;
                this.edition_ = 0;
            }

            public boolean hasOverridableFeatures() {
                return (this.bitField0_ & 2) != 0;
            }

            public FeatureSet getOverridableFeatures() {
                return this.overridableFeatures_ == null ? FeatureSet.getDefaultInstance() : this.overridableFeatures_;
            }

            /* access modifiers changed from: private */
            public void setOverridableFeatures(FeatureSet value) {
                value.getClass();
                this.overridableFeatures_ = value;
                this.bitField0_ |= 2;
            }

            /* access modifiers changed from: private */
            public void mergeOverridableFeatures(FeatureSet value) {
                value.getClass();
                if (this.overridableFeatures_ == null || this.overridableFeatures_ == FeatureSet.getDefaultInstance()) {
                    this.overridableFeatures_ = value;
                } else {
                    this.overridableFeatures_ = (FeatureSet) ((FeatureSet.Builder) FeatureSet.newBuilder(this.overridableFeatures_).mergeFrom(value)).buildPartial();
                }
                this.bitField0_ |= 2;
            }

            /* access modifiers changed from: private */
            public void clearOverridableFeatures() {
                this.overridableFeatures_ = null;
                this.bitField0_ &= -3;
            }

            public boolean hasFixedFeatures() {
                return (this.bitField0_ & 4) != 0;
            }

            public FeatureSet getFixedFeatures() {
                return this.fixedFeatures_ == null ? FeatureSet.getDefaultInstance() : this.fixedFeatures_;
            }

            /* access modifiers changed from: private */
            public void setFixedFeatures(FeatureSet value) {
                value.getClass();
                this.fixedFeatures_ = value;
                this.bitField0_ |= 4;
            }

            /* access modifiers changed from: private */
            public void mergeFixedFeatures(FeatureSet value) {
                value.getClass();
                if (this.fixedFeatures_ == null || this.fixedFeatures_ == FeatureSet.getDefaultInstance()) {
                    this.fixedFeatures_ = value;
                } else {
                    this.fixedFeatures_ = (FeatureSet) ((FeatureSet.Builder) FeatureSet.newBuilder(this.fixedFeatures_).mergeFrom(value)).buildPartial();
                }
                this.bitField0_ |= 4;
            }

            /* access modifiers changed from: private */
            public void clearFixedFeatures() {
                this.fixedFeatures_ = null;
                this.bitField0_ &= -5;
            }

            public static FeatureSetEditionDefault parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (FeatureSetEditionDefault) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static FeatureSetEditionDefault parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (FeatureSetEditionDefault) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static FeatureSetEditionDefault parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (FeatureSetEditionDefault) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static FeatureSetEditionDefault parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (FeatureSetEditionDefault) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static FeatureSetEditionDefault parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (FeatureSetEditionDefault) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static FeatureSetEditionDefault parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (FeatureSetEditionDefault) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static FeatureSetEditionDefault parseFrom(InputStream input) throws IOException {
                return (FeatureSetEditionDefault) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static FeatureSetEditionDefault parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (FeatureSetEditionDefault) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static FeatureSetEditionDefault parseDelimitedFrom(InputStream input) throws IOException {
                return (FeatureSetEditionDefault) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static FeatureSetEditionDefault parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (FeatureSetEditionDefault) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static FeatureSetEditionDefault parseFrom(CodedInputStream input) throws IOException {
                return (FeatureSetEditionDefault) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static FeatureSetEditionDefault parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (FeatureSetEditionDefault) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(FeatureSetEditionDefault prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<FeatureSetEditionDefault, Builder> implements FeatureSetEditionDefaultOrBuilder {
                private Builder() {
                    super(FeatureSetEditionDefault.DEFAULT_INSTANCE);
                }

                public boolean hasEdition() {
                    return ((FeatureSetEditionDefault) this.instance).hasEdition();
                }

                public Edition getEdition() {
                    return ((FeatureSetEditionDefault) this.instance).getEdition();
                }

                public Builder setEdition(Edition value) {
                    copyOnWrite();
                    ((FeatureSetEditionDefault) this.instance).setEdition(value);
                    return this;
                }

                public Builder clearEdition() {
                    copyOnWrite();
                    ((FeatureSetEditionDefault) this.instance).clearEdition();
                    return this;
                }

                public boolean hasOverridableFeatures() {
                    return ((FeatureSetEditionDefault) this.instance).hasOverridableFeatures();
                }

                public FeatureSet getOverridableFeatures() {
                    return ((FeatureSetEditionDefault) this.instance).getOverridableFeatures();
                }

                public Builder setOverridableFeatures(FeatureSet value) {
                    copyOnWrite();
                    ((FeatureSetEditionDefault) this.instance).setOverridableFeatures(value);
                    return this;
                }

                public Builder setOverridableFeatures(FeatureSet.Builder builderForValue) {
                    copyOnWrite();
                    ((FeatureSetEditionDefault) this.instance).setOverridableFeatures((FeatureSet) builderForValue.build());
                    return this;
                }

                public Builder mergeOverridableFeatures(FeatureSet value) {
                    copyOnWrite();
                    ((FeatureSetEditionDefault) this.instance).mergeOverridableFeatures(value);
                    return this;
                }

                public Builder clearOverridableFeatures() {
                    copyOnWrite();
                    ((FeatureSetEditionDefault) this.instance).clearOverridableFeatures();
                    return this;
                }

                public boolean hasFixedFeatures() {
                    return ((FeatureSetEditionDefault) this.instance).hasFixedFeatures();
                }

                public FeatureSet getFixedFeatures() {
                    return ((FeatureSetEditionDefault) this.instance).getFixedFeatures();
                }

                public Builder setFixedFeatures(FeatureSet value) {
                    copyOnWrite();
                    ((FeatureSetEditionDefault) this.instance).setFixedFeatures(value);
                    return this;
                }

                public Builder setFixedFeatures(FeatureSet.Builder builderForValue) {
                    copyOnWrite();
                    ((FeatureSetEditionDefault) this.instance).setFixedFeatures((FeatureSet) builderForValue.build());
                    return this;
                }

                public Builder mergeFixedFeatures(FeatureSet value) {
                    copyOnWrite();
                    ((FeatureSetEditionDefault) this.instance).mergeFixedFeatures(value);
                    return this;
                }

                public Builder clearFixedFeatures() {
                    copyOnWrite();
                    ((FeatureSetEditionDefault) this.instance).clearFixedFeatures();
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new FeatureSetEditionDefault();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0003\u0005\u0003\u0000\u0000\u0002\u0003᠌\u0000\u0004ᐉ\u0001\u0005ᐉ\u0002", new Object[]{"bitField0_", "edition_", Edition.internalGetVerifier(), "overridableFeatures_", "fixedFeatures_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<FeatureSetEditionDefault> parser = PARSER;
                        if (parser == null) {
                            synchronized (FeatureSetEditionDefault.class) {
                                parser = PARSER;
                                if (parser == null) {
                                    parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = parser;
                                }
                            }
                        }
                        return parser;
                    case GET_MEMOIZED_IS_INITIALIZED:
                        return Byte.valueOf(this.memoizedIsInitialized);
                    case SET_MEMOIZED_IS_INITIALIZED:
                        this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                        return null;
                    default:
                        throw new UnsupportedOperationException();
                }
            }

            static {
                FeatureSetEditionDefault defaultInstance = new FeatureSetEditionDefault();
                DEFAULT_INSTANCE = defaultInstance;
                GeneratedMessageLite.registerDefaultInstance(FeatureSetEditionDefault.class, defaultInstance);
            }

            public static FeatureSetEditionDefault getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<FeatureSetEditionDefault> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public List<FeatureSetEditionDefault> getDefaultsList() {
            return this.defaults_;
        }

        public List<? extends FeatureSetEditionDefaultOrBuilder> getDefaultsOrBuilderList() {
            return this.defaults_;
        }

        public int getDefaultsCount() {
            return this.defaults_.size();
        }

        public FeatureSetEditionDefault getDefaults(int index) {
            return (FeatureSetEditionDefault) this.defaults_.get(index);
        }

        public FeatureSetEditionDefaultOrBuilder getDefaultsOrBuilder(int index) {
            return (FeatureSetEditionDefaultOrBuilder) this.defaults_.get(index);
        }

        private void ensureDefaultsIsMutable() {
            Internal.ProtobufList<FeatureSetEditionDefault> tmp = this.defaults_;
            if (!tmp.isModifiable()) {
                this.defaults_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setDefaults(int index, FeatureSetEditionDefault value) {
            value.getClass();
            ensureDefaultsIsMutable();
            this.defaults_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addDefaults(FeatureSetEditionDefault value) {
            value.getClass();
            ensureDefaultsIsMutable();
            this.defaults_.add(value);
        }

        /* access modifiers changed from: private */
        public void addDefaults(int index, FeatureSetEditionDefault value) {
            value.getClass();
            ensureDefaultsIsMutable();
            this.defaults_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllDefaults(Iterable<? extends FeatureSetEditionDefault> values) {
            ensureDefaultsIsMutable();
            AbstractMessageLite.addAll(values, this.defaults_);
        }

        /* access modifiers changed from: private */
        public void clearDefaults() {
            this.defaults_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeDefaults(int index) {
            ensureDefaultsIsMutable();
            this.defaults_.remove(index);
        }

        public boolean hasMinimumEdition() {
            return (this.bitField0_ & 1) != 0;
        }

        public Edition getMinimumEdition() {
            Edition result = Edition.forNumber(this.minimumEdition_);
            return result == null ? Edition.EDITION_UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setMinimumEdition(Edition value) {
            this.minimumEdition_ = value.getNumber();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void clearMinimumEdition() {
            this.bitField0_ &= -2;
            this.minimumEdition_ = 0;
        }

        public boolean hasMaximumEdition() {
            return (this.bitField0_ & 2) != 0;
        }

        public Edition getMaximumEdition() {
            Edition result = Edition.forNumber(this.maximumEdition_);
            return result == null ? Edition.EDITION_UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setMaximumEdition(Edition value) {
            this.maximumEdition_ = value.getNumber();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void clearMaximumEdition() {
            this.bitField0_ &= -3;
            this.maximumEdition_ = 0;
        }

        public static FeatureSetDefaults parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (FeatureSetDefaults) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FeatureSetDefaults parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FeatureSetDefaults) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FeatureSetDefaults parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FeatureSetDefaults) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FeatureSetDefaults parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FeatureSetDefaults) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FeatureSetDefaults parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FeatureSetDefaults) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FeatureSetDefaults parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FeatureSetDefaults) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FeatureSetDefaults parseFrom(InputStream input) throws IOException {
            return (FeatureSetDefaults) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FeatureSetDefaults parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FeatureSetDefaults) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FeatureSetDefaults parseDelimitedFrom(InputStream input) throws IOException {
            return (FeatureSetDefaults) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static FeatureSetDefaults parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FeatureSetDefaults) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FeatureSetDefaults parseFrom(CodedInputStream input) throws IOException {
            return (FeatureSetDefaults) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FeatureSetDefaults parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FeatureSetDefaults) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(FeatureSetDefaults prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<FeatureSetDefaults, Builder> implements FeatureSetDefaultsOrBuilder {
            private Builder() {
                super(FeatureSetDefaults.DEFAULT_INSTANCE);
            }

            public List<FeatureSetEditionDefault> getDefaultsList() {
                return Collections.unmodifiableList(((FeatureSetDefaults) this.instance).getDefaultsList());
            }

            public int getDefaultsCount() {
                return ((FeatureSetDefaults) this.instance).getDefaultsCount();
            }

            public FeatureSetEditionDefault getDefaults(int index) {
                return ((FeatureSetDefaults) this.instance).getDefaults(index);
            }

            public Builder setDefaults(int index, FeatureSetEditionDefault value) {
                copyOnWrite();
                ((FeatureSetDefaults) this.instance).setDefaults(index, value);
                return this;
            }

            public Builder setDefaults(int index, FeatureSetEditionDefault.Builder builderForValue) {
                copyOnWrite();
                ((FeatureSetDefaults) this.instance).setDefaults(index, (FeatureSetEditionDefault) builderForValue.build());
                return this;
            }

            public Builder addDefaults(FeatureSetEditionDefault value) {
                copyOnWrite();
                ((FeatureSetDefaults) this.instance).addDefaults(value);
                return this;
            }

            public Builder addDefaults(int index, FeatureSetEditionDefault value) {
                copyOnWrite();
                ((FeatureSetDefaults) this.instance).addDefaults(index, value);
                return this;
            }

            public Builder addDefaults(FeatureSetEditionDefault.Builder builderForValue) {
                copyOnWrite();
                ((FeatureSetDefaults) this.instance).addDefaults((FeatureSetEditionDefault) builderForValue.build());
                return this;
            }

            public Builder addDefaults(int index, FeatureSetEditionDefault.Builder builderForValue) {
                copyOnWrite();
                ((FeatureSetDefaults) this.instance).addDefaults(index, (FeatureSetEditionDefault) builderForValue.build());
                return this;
            }

            public Builder addAllDefaults(Iterable<? extends FeatureSetEditionDefault> values) {
                copyOnWrite();
                ((FeatureSetDefaults) this.instance).addAllDefaults(values);
                return this;
            }

            public Builder clearDefaults() {
                copyOnWrite();
                ((FeatureSetDefaults) this.instance).clearDefaults();
                return this;
            }

            public Builder removeDefaults(int index) {
                copyOnWrite();
                ((FeatureSetDefaults) this.instance).removeDefaults(index);
                return this;
            }

            public boolean hasMinimumEdition() {
                return ((FeatureSetDefaults) this.instance).hasMinimumEdition();
            }

            public Edition getMinimumEdition() {
                return ((FeatureSetDefaults) this.instance).getMinimumEdition();
            }

            public Builder setMinimumEdition(Edition value) {
                copyOnWrite();
                ((FeatureSetDefaults) this.instance).setMinimumEdition(value);
                return this;
            }

            public Builder clearMinimumEdition() {
                copyOnWrite();
                ((FeatureSetDefaults) this.instance).clearMinimumEdition();
                return this;
            }

            public boolean hasMaximumEdition() {
                return ((FeatureSetDefaults) this.instance).hasMaximumEdition();
            }

            public Edition getMaximumEdition() {
                return ((FeatureSetDefaults) this.instance).getMaximumEdition();
            }

            public Builder setMaximumEdition(Edition value) {
                copyOnWrite();
                ((FeatureSetDefaults) this.instance).setMaximumEdition(value);
                return this;
            }

            public Builder clearMaximumEdition() {
                copyOnWrite();
                ((FeatureSetDefaults) this.instance).clearMaximumEdition();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new FeatureSetDefaults();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0005\u0003\u0000\u0001\u0001\u0001Л\u0004᠌\u0000\u0005᠌\u0001", new Object[]{"bitField0_", "defaults_", FeatureSetEditionDefault.class, "minimumEdition_", Edition.internalGetVerifier(), "maximumEdition_", Edition.internalGetVerifier()});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<FeatureSetDefaults> parser = PARSER;
                    if (parser == null) {
                        synchronized (FeatureSetDefaults.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return Byte.valueOf(this.memoizedIsInitialized);
                case SET_MEMOIZED_IS_INITIALIZED:
                    this.memoizedIsInitialized = (byte) (arg0 == null ? 0 : 1);
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            FeatureSetDefaults defaultInstance = new FeatureSetDefaults();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(FeatureSetDefaults.class, defaultInstance);
        }

        public static FeatureSetDefaults getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FeatureSetDefaults> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class SourceCodeInfo extends GeneratedMessageLite<SourceCodeInfo, Builder> implements SourceCodeInfoOrBuilder {
        /* access modifiers changed from: private */
        public static final SourceCodeInfo DEFAULT_INSTANCE;
        public static final int LOCATION_FIELD_NUMBER = 1;
        private static volatile Parser<SourceCodeInfo> PARSER;
        private Internal.ProtobufList<Location> location_ = emptyProtobufList();

        public interface LocationOrBuilder extends MessageLiteOrBuilder {
            String getLeadingComments();

            ByteString getLeadingCommentsBytes();

            String getLeadingDetachedComments(int i);

            ByteString getLeadingDetachedCommentsBytes(int i);

            int getLeadingDetachedCommentsCount();

            List<String> getLeadingDetachedCommentsList();

            int getPath(int i);

            int getPathCount();

            List<Integer> getPathList();

            int getSpan(int i);

            int getSpanCount();

            List<Integer> getSpanList();

            String getTrailingComments();

            ByteString getTrailingCommentsBytes();

            boolean hasLeadingComments();

            boolean hasTrailingComments();
        }

        private SourceCodeInfo() {
        }

        public static final class Location extends GeneratedMessageLite<Location, Builder> implements LocationOrBuilder {
            /* access modifiers changed from: private */
            public static final Location DEFAULT_INSTANCE;
            public static final int LEADING_COMMENTS_FIELD_NUMBER = 3;
            public static final int LEADING_DETACHED_COMMENTS_FIELD_NUMBER = 6;
            private static volatile Parser<Location> PARSER = null;
            public static final int PATH_FIELD_NUMBER = 1;
            public static final int SPAN_FIELD_NUMBER = 2;
            public static final int TRAILING_COMMENTS_FIELD_NUMBER = 4;
            private int bitField0_;
            private String leadingComments_ = "";
            private Internal.ProtobufList<String> leadingDetachedComments_ = GeneratedMessageLite.emptyProtobufList();
            private int pathMemoizedSerializedSize = -1;
            private Internal.IntList path_ = emptyIntList();
            private int spanMemoizedSerializedSize = -1;
            private Internal.IntList span_ = emptyIntList();
            private String trailingComments_ = "";

            private Location() {
            }

            public List<Integer> getPathList() {
                return this.path_;
            }

            public int getPathCount() {
                return this.path_.size();
            }

            public int getPath(int index) {
                return this.path_.getInt(index);
            }

            private void ensurePathIsMutable() {
                Internal.IntList tmp = this.path_;
                if (!tmp.isModifiable()) {
                    this.path_ = GeneratedMessageLite.mutableCopy(tmp);
                }
            }

            /* access modifiers changed from: private */
            public void setPath(int index, int value) {
                ensurePathIsMutable();
                this.path_.setInt(index, value);
            }

            /* access modifiers changed from: private */
            public void addPath(int value) {
                ensurePathIsMutable();
                this.path_.addInt(value);
            }

            /* access modifiers changed from: private */
            public void addAllPath(Iterable<? extends Integer> values) {
                ensurePathIsMutable();
                AbstractMessageLite.addAll(values, this.path_);
            }

            /* access modifiers changed from: private */
            public void clearPath() {
                this.path_ = emptyIntList();
            }

            public List<Integer> getSpanList() {
                return this.span_;
            }

            public int getSpanCount() {
                return this.span_.size();
            }

            public int getSpan(int index) {
                return this.span_.getInt(index);
            }

            private void ensureSpanIsMutable() {
                Internal.IntList tmp = this.span_;
                if (!tmp.isModifiable()) {
                    this.span_ = GeneratedMessageLite.mutableCopy(tmp);
                }
            }

            /* access modifiers changed from: private */
            public void setSpan(int index, int value) {
                ensureSpanIsMutable();
                this.span_.setInt(index, value);
            }

            /* access modifiers changed from: private */
            public void addSpan(int value) {
                ensureSpanIsMutable();
                this.span_.addInt(value);
            }

            /* access modifiers changed from: private */
            public void addAllSpan(Iterable<? extends Integer> values) {
                ensureSpanIsMutable();
                AbstractMessageLite.addAll(values, this.span_);
            }

            /* access modifiers changed from: private */
            public void clearSpan() {
                this.span_ = emptyIntList();
            }

            public boolean hasLeadingComments() {
                return (this.bitField0_ & 1) != 0;
            }

            public String getLeadingComments() {
                return this.leadingComments_;
            }

            public ByteString getLeadingCommentsBytes() {
                return ByteString.copyFromUtf8(this.leadingComments_);
            }

            /* access modifiers changed from: private */
            public void setLeadingComments(String value) {
                Class<?> cls = value.getClass();
                this.bitField0_ |= 1;
                this.leadingComments_ = value;
            }

            /* access modifiers changed from: private */
            public void clearLeadingComments() {
                this.bitField0_ &= -2;
                this.leadingComments_ = getDefaultInstance().getLeadingComments();
            }

            /* access modifiers changed from: private */
            public void setLeadingCommentsBytes(ByteString value) {
                this.leadingComments_ = value.toStringUtf8();
                this.bitField0_ |= 1;
            }

            public boolean hasTrailingComments() {
                return (this.bitField0_ & 2) != 0;
            }

            public String getTrailingComments() {
                return this.trailingComments_;
            }

            public ByteString getTrailingCommentsBytes() {
                return ByteString.copyFromUtf8(this.trailingComments_);
            }

            /* access modifiers changed from: private */
            public void setTrailingComments(String value) {
                Class<?> cls = value.getClass();
                this.bitField0_ |= 2;
                this.trailingComments_ = value;
            }

            /* access modifiers changed from: private */
            public void clearTrailingComments() {
                this.bitField0_ &= -3;
                this.trailingComments_ = getDefaultInstance().getTrailingComments();
            }

            /* access modifiers changed from: private */
            public void setTrailingCommentsBytes(ByteString value) {
                this.trailingComments_ = value.toStringUtf8();
                this.bitField0_ |= 2;
            }

            public List<String> getLeadingDetachedCommentsList() {
                return this.leadingDetachedComments_;
            }

            public int getLeadingDetachedCommentsCount() {
                return this.leadingDetachedComments_.size();
            }

            public String getLeadingDetachedComments(int index) {
                return (String) this.leadingDetachedComments_.get(index);
            }

            public ByteString getLeadingDetachedCommentsBytes(int index) {
                return ByteString.copyFromUtf8((String) this.leadingDetachedComments_.get(index));
            }

            private void ensureLeadingDetachedCommentsIsMutable() {
                Internal.ProtobufList<String> tmp = this.leadingDetachedComments_;
                if (!tmp.isModifiable()) {
                    this.leadingDetachedComments_ = GeneratedMessageLite.mutableCopy(tmp);
                }
            }

            /* access modifiers changed from: private */
            public void setLeadingDetachedComments(int index, String value) {
                Class<?> cls = value.getClass();
                ensureLeadingDetachedCommentsIsMutable();
                this.leadingDetachedComments_.set(index, value);
            }

            /* access modifiers changed from: private */
            public void addLeadingDetachedComments(String value) {
                Class<?> cls = value.getClass();
                ensureLeadingDetachedCommentsIsMutable();
                this.leadingDetachedComments_.add(value);
            }

            /* access modifiers changed from: private */
            public void addAllLeadingDetachedComments(Iterable<String> values) {
                ensureLeadingDetachedCommentsIsMutable();
                AbstractMessageLite.addAll(values, this.leadingDetachedComments_);
            }

            /* access modifiers changed from: private */
            public void clearLeadingDetachedComments() {
                this.leadingDetachedComments_ = GeneratedMessageLite.emptyProtobufList();
            }

            /* access modifiers changed from: private */
            public void addLeadingDetachedCommentsBytes(ByteString value) {
                ensureLeadingDetachedCommentsIsMutable();
                this.leadingDetachedComments_.add(value.toStringUtf8());
            }

            public static Location parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (Location) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Location parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Location) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Location parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (Location) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Location parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Location) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Location parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (Location) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Location parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Location) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Location parseFrom(InputStream input) throws IOException {
                return (Location) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static Location parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Location) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Location parseDelimitedFrom(InputStream input) throws IOException {
                return (Location) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static Location parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Location) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Location parseFrom(CodedInputStream input) throws IOException {
                return (Location) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static Location parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Location) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(Location prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<Location, Builder> implements LocationOrBuilder {
                private Builder() {
                    super(Location.DEFAULT_INSTANCE);
                }

                public List<Integer> getPathList() {
                    return Collections.unmodifiableList(((Location) this.instance).getPathList());
                }

                public int getPathCount() {
                    return ((Location) this.instance).getPathCount();
                }

                public int getPath(int index) {
                    return ((Location) this.instance).getPath(index);
                }

                public Builder setPath(int index, int value) {
                    copyOnWrite();
                    ((Location) this.instance).setPath(index, value);
                    return this;
                }

                public Builder addPath(int value) {
                    copyOnWrite();
                    ((Location) this.instance).addPath(value);
                    return this;
                }

                public Builder addAllPath(Iterable<? extends Integer> values) {
                    copyOnWrite();
                    ((Location) this.instance).addAllPath(values);
                    return this;
                }

                public Builder clearPath() {
                    copyOnWrite();
                    ((Location) this.instance).clearPath();
                    return this;
                }

                public List<Integer> getSpanList() {
                    return Collections.unmodifiableList(((Location) this.instance).getSpanList());
                }

                public int getSpanCount() {
                    return ((Location) this.instance).getSpanCount();
                }

                public int getSpan(int index) {
                    return ((Location) this.instance).getSpan(index);
                }

                public Builder setSpan(int index, int value) {
                    copyOnWrite();
                    ((Location) this.instance).setSpan(index, value);
                    return this;
                }

                public Builder addSpan(int value) {
                    copyOnWrite();
                    ((Location) this.instance).addSpan(value);
                    return this;
                }

                public Builder addAllSpan(Iterable<? extends Integer> values) {
                    copyOnWrite();
                    ((Location) this.instance).addAllSpan(values);
                    return this;
                }

                public Builder clearSpan() {
                    copyOnWrite();
                    ((Location) this.instance).clearSpan();
                    return this;
                }

                public boolean hasLeadingComments() {
                    return ((Location) this.instance).hasLeadingComments();
                }

                public String getLeadingComments() {
                    return ((Location) this.instance).getLeadingComments();
                }

                public ByteString getLeadingCommentsBytes() {
                    return ((Location) this.instance).getLeadingCommentsBytes();
                }

                public Builder setLeadingComments(String value) {
                    copyOnWrite();
                    ((Location) this.instance).setLeadingComments(value);
                    return this;
                }

                public Builder clearLeadingComments() {
                    copyOnWrite();
                    ((Location) this.instance).clearLeadingComments();
                    return this;
                }

                public Builder setLeadingCommentsBytes(ByteString value) {
                    copyOnWrite();
                    ((Location) this.instance).setLeadingCommentsBytes(value);
                    return this;
                }

                public boolean hasTrailingComments() {
                    return ((Location) this.instance).hasTrailingComments();
                }

                public String getTrailingComments() {
                    return ((Location) this.instance).getTrailingComments();
                }

                public ByteString getTrailingCommentsBytes() {
                    return ((Location) this.instance).getTrailingCommentsBytes();
                }

                public Builder setTrailingComments(String value) {
                    copyOnWrite();
                    ((Location) this.instance).setTrailingComments(value);
                    return this;
                }

                public Builder clearTrailingComments() {
                    copyOnWrite();
                    ((Location) this.instance).clearTrailingComments();
                    return this;
                }

                public Builder setTrailingCommentsBytes(ByteString value) {
                    copyOnWrite();
                    ((Location) this.instance).setTrailingCommentsBytes(value);
                    return this;
                }

                public List<String> getLeadingDetachedCommentsList() {
                    return Collections.unmodifiableList(((Location) this.instance).getLeadingDetachedCommentsList());
                }

                public int getLeadingDetachedCommentsCount() {
                    return ((Location) this.instance).getLeadingDetachedCommentsCount();
                }

                public String getLeadingDetachedComments(int index) {
                    return ((Location) this.instance).getLeadingDetachedComments(index);
                }

                public ByteString getLeadingDetachedCommentsBytes(int index) {
                    return ((Location) this.instance).getLeadingDetachedCommentsBytes(index);
                }

                public Builder setLeadingDetachedComments(int index, String value) {
                    copyOnWrite();
                    ((Location) this.instance).setLeadingDetachedComments(index, value);
                    return this;
                }

                public Builder addLeadingDetachedComments(String value) {
                    copyOnWrite();
                    ((Location) this.instance).addLeadingDetachedComments(value);
                    return this;
                }

                public Builder addAllLeadingDetachedComments(Iterable<String> values) {
                    copyOnWrite();
                    ((Location) this.instance).addAllLeadingDetachedComments(values);
                    return this;
                }

                public Builder clearLeadingDetachedComments() {
                    copyOnWrite();
                    ((Location) this.instance).clearLeadingDetachedComments();
                    return this;
                }

                public Builder addLeadingDetachedCommentsBytes(ByteString value) {
                    copyOnWrite();
                    ((Location) this.instance).addLeadingDetachedCommentsBytes(value);
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new Location();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0005\u0000\u0001\u0001\u0006\u0005\u0000\u0003\u0000\u0001'\u0002'\u0003ဈ\u0000\u0004ဈ\u0001\u0006\u001a", new Object[]{"bitField0_", "path_", "span_", "leadingComments_", "trailingComments_", "leadingDetachedComments_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<Location> parser = PARSER;
                        if (parser == null) {
                            synchronized (Location.class) {
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
                Location defaultInstance = new Location();
                DEFAULT_INSTANCE = defaultInstance;
                GeneratedMessageLite.registerDefaultInstance(Location.class, defaultInstance);
            }

            public static Location getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<Location> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public List<Location> getLocationList() {
            return this.location_;
        }

        public List<? extends LocationOrBuilder> getLocationOrBuilderList() {
            return this.location_;
        }

        public int getLocationCount() {
            return this.location_.size();
        }

        public Location getLocation(int index) {
            return (Location) this.location_.get(index);
        }

        public LocationOrBuilder getLocationOrBuilder(int index) {
            return (LocationOrBuilder) this.location_.get(index);
        }

        private void ensureLocationIsMutable() {
            Internal.ProtobufList<Location> tmp = this.location_;
            if (!tmp.isModifiable()) {
                this.location_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setLocation(int index, Location value) {
            value.getClass();
            ensureLocationIsMutable();
            this.location_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addLocation(Location value) {
            value.getClass();
            ensureLocationIsMutable();
            this.location_.add(value);
        }

        /* access modifiers changed from: private */
        public void addLocation(int index, Location value) {
            value.getClass();
            ensureLocationIsMutable();
            this.location_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllLocation(Iterable<? extends Location> values) {
            ensureLocationIsMutable();
            AbstractMessageLite.addAll(values, this.location_);
        }

        /* access modifiers changed from: private */
        public void clearLocation() {
            this.location_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeLocation(int index) {
            ensureLocationIsMutable();
            this.location_.remove(index);
        }

        public static SourceCodeInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (SourceCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SourceCodeInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SourceCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SourceCodeInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (SourceCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SourceCodeInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SourceCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SourceCodeInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (SourceCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SourceCodeInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SourceCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SourceCodeInfo parseFrom(InputStream input) throws IOException {
            return (SourceCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SourceCodeInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SourceCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SourceCodeInfo parseDelimitedFrom(InputStream input) throws IOException {
            return (SourceCodeInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static SourceCodeInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SourceCodeInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SourceCodeInfo parseFrom(CodedInputStream input) throws IOException {
            return (SourceCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SourceCodeInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SourceCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(SourceCodeInfo prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<SourceCodeInfo, Builder> implements SourceCodeInfoOrBuilder {
            private Builder() {
                super(SourceCodeInfo.DEFAULT_INSTANCE);
            }

            public List<Location> getLocationList() {
                return Collections.unmodifiableList(((SourceCodeInfo) this.instance).getLocationList());
            }

            public int getLocationCount() {
                return ((SourceCodeInfo) this.instance).getLocationCount();
            }

            public Location getLocation(int index) {
                return ((SourceCodeInfo) this.instance).getLocation(index);
            }

            public Builder setLocation(int index, Location value) {
                copyOnWrite();
                ((SourceCodeInfo) this.instance).setLocation(index, value);
                return this;
            }

            public Builder setLocation(int index, Location.Builder builderForValue) {
                copyOnWrite();
                ((SourceCodeInfo) this.instance).setLocation(index, (Location) builderForValue.build());
                return this;
            }

            public Builder addLocation(Location value) {
                copyOnWrite();
                ((SourceCodeInfo) this.instance).addLocation(value);
                return this;
            }

            public Builder addLocation(int index, Location value) {
                copyOnWrite();
                ((SourceCodeInfo) this.instance).addLocation(index, value);
                return this;
            }

            public Builder addLocation(Location.Builder builderForValue) {
                copyOnWrite();
                ((SourceCodeInfo) this.instance).addLocation((Location) builderForValue.build());
                return this;
            }

            public Builder addLocation(int index, Location.Builder builderForValue) {
                copyOnWrite();
                ((SourceCodeInfo) this.instance).addLocation(index, (Location) builderForValue.build());
                return this;
            }

            public Builder addAllLocation(Iterable<? extends Location> values) {
                copyOnWrite();
                ((SourceCodeInfo) this.instance).addAllLocation(values);
                return this;
            }

            public Builder clearLocation() {
                copyOnWrite();
                ((SourceCodeInfo) this.instance).clearLocation();
                return this;
            }

            public Builder removeLocation(int index) {
                copyOnWrite();
                ((SourceCodeInfo) this.instance).removeLocation(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new SourceCodeInfo();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"location_", Location.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<SourceCodeInfo> parser = PARSER;
                    if (parser == null) {
                        synchronized (SourceCodeInfo.class) {
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
            SourceCodeInfo defaultInstance = new SourceCodeInfo();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(SourceCodeInfo.class, defaultInstance);
        }

        public static SourceCodeInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SourceCodeInfo> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class GeneratedCodeInfo extends GeneratedMessageLite<GeneratedCodeInfo, Builder> implements GeneratedCodeInfoOrBuilder {
        public static final int ANNOTATION_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final GeneratedCodeInfo DEFAULT_INSTANCE;
        private static volatile Parser<GeneratedCodeInfo> PARSER;
        private Internal.ProtobufList<Annotation> annotation_ = emptyProtobufList();

        public interface AnnotationOrBuilder extends MessageLiteOrBuilder {
            int getBegin();

            int getEnd();

            int getPath(int i);

            int getPathCount();

            List<Integer> getPathList();

            Annotation.Semantic getSemantic();

            String getSourceFile();

            ByteString getSourceFileBytes();

            boolean hasBegin();

            boolean hasEnd();

            boolean hasSemantic();

            boolean hasSourceFile();
        }

        private GeneratedCodeInfo() {
        }

        public static final class Annotation extends GeneratedMessageLite<Annotation, Builder> implements AnnotationOrBuilder {
            public static final int BEGIN_FIELD_NUMBER = 3;
            /* access modifiers changed from: private */
            public static final Annotation DEFAULT_INSTANCE;
            public static final int END_FIELD_NUMBER = 4;
            private static volatile Parser<Annotation> PARSER = null;
            public static final int PATH_FIELD_NUMBER = 1;
            public static final int SEMANTIC_FIELD_NUMBER = 5;
            public static final int SOURCE_FILE_FIELD_NUMBER = 2;
            private int begin_;
            private int bitField0_;
            private int end_;
            private int pathMemoizedSerializedSize = -1;
            private Internal.IntList path_ = emptyIntList();
            private int semantic_;
            private String sourceFile_ = "";

            private Annotation() {
            }

            public enum Semantic implements Internal.EnumLite {
                NONE(0),
                SET(1),
                ALIAS(2);
                
                public static final int ALIAS_VALUE = 2;
                public static final int NONE_VALUE = 0;
                public static final int SET_VALUE = 1;
                private static final Internal.EnumLiteMap<Semantic> internalValueMap = null;
                private final int value;

                static {
                    internalValueMap = new Internal.EnumLiteMap<Semantic>() {
                        public Semantic findValueByNumber(int number) {
                            return Semantic.forNumber(number);
                        }
                    };
                }

                public final int getNumber() {
                    return this.value;
                }

                @Deprecated
                public static Semantic valueOf(int value2) {
                    return forNumber(value2);
                }

                public static Semantic forNumber(int value2) {
                    switch (value2) {
                        case 0:
                            return NONE;
                        case 1:
                            return SET;
                        case 2:
                            return ALIAS;
                        default:
                            return null;
                    }
                }

                public static Internal.EnumLiteMap<Semantic> internalGetValueMap() {
                    return internalValueMap;
                }

                public static Internal.EnumVerifier internalGetVerifier() {
                    return SemanticVerifier.INSTANCE;
                }

                private static final class SemanticVerifier implements Internal.EnumVerifier {
                    static final Internal.EnumVerifier INSTANCE = null;

                    private SemanticVerifier() {
                    }

                    static {
                        INSTANCE = new SemanticVerifier();
                    }

                    public boolean isInRange(int number) {
                        return Semantic.forNumber(number) != null;
                    }
                }

                private Semantic(int value2) {
                    this.value = value2;
                }
            }

            public List<Integer> getPathList() {
                return this.path_;
            }

            public int getPathCount() {
                return this.path_.size();
            }

            public int getPath(int index) {
                return this.path_.getInt(index);
            }

            private void ensurePathIsMutable() {
                Internal.IntList tmp = this.path_;
                if (!tmp.isModifiable()) {
                    this.path_ = GeneratedMessageLite.mutableCopy(tmp);
                }
            }

            /* access modifiers changed from: private */
            public void setPath(int index, int value) {
                ensurePathIsMutable();
                this.path_.setInt(index, value);
            }

            /* access modifiers changed from: private */
            public void addPath(int value) {
                ensurePathIsMutable();
                this.path_.addInt(value);
            }

            /* access modifiers changed from: private */
            public void addAllPath(Iterable<? extends Integer> values) {
                ensurePathIsMutable();
                AbstractMessageLite.addAll(values, this.path_);
            }

            /* access modifiers changed from: private */
            public void clearPath() {
                this.path_ = emptyIntList();
            }

            public boolean hasSourceFile() {
                return (this.bitField0_ & 1) != 0;
            }

            public String getSourceFile() {
                return this.sourceFile_;
            }

            public ByteString getSourceFileBytes() {
                return ByteString.copyFromUtf8(this.sourceFile_);
            }

            /* access modifiers changed from: private */
            public void setSourceFile(String value) {
                Class<?> cls = value.getClass();
                this.bitField0_ |= 1;
                this.sourceFile_ = value;
            }

            /* access modifiers changed from: private */
            public void clearSourceFile() {
                this.bitField0_ &= -2;
                this.sourceFile_ = getDefaultInstance().getSourceFile();
            }

            /* access modifiers changed from: private */
            public void setSourceFileBytes(ByteString value) {
                this.sourceFile_ = value.toStringUtf8();
                this.bitField0_ |= 1;
            }

            public boolean hasBegin() {
                return (this.bitField0_ & 2) != 0;
            }

            public int getBegin() {
                return this.begin_;
            }

            /* access modifiers changed from: private */
            public void setBegin(int value) {
                this.bitField0_ |= 2;
                this.begin_ = value;
            }

            /* access modifiers changed from: private */
            public void clearBegin() {
                this.bitField0_ &= -3;
                this.begin_ = 0;
            }

            public boolean hasEnd() {
                return (this.bitField0_ & 4) != 0;
            }

            public int getEnd() {
                return this.end_;
            }

            /* access modifiers changed from: private */
            public void setEnd(int value) {
                this.bitField0_ |= 4;
                this.end_ = value;
            }

            /* access modifiers changed from: private */
            public void clearEnd() {
                this.bitField0_ &= -5;
                this.end_ = 0;
            }

            public boolean hasSemantic() {
                return (this.bitField0_ & 8) != 0;
            }

            public Semantic getSemantic() {
                Semantic result = Semantic.forNumber(this.semantic_);
                return result == null ? Semantic.NONE : result;
            }

            /* access modifiers changed from: private */
            public void setSemantic(Semantic value) {
                this.semantic_ = value.getNumber();
                this.bitField0_ |= 8;
            }

            /* access modifiers changed from: private */
            public void clearSemantic() {
                this.bitField0_ &= -9;
                this.semantic_ = 0;
            }

            public static Annotation parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (Annotation) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Annotation parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Annotation) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Annotation parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (Annotation) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Annotation parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Annotation) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Annotation parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (Annotation) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Annotation parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Annotation) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Annotation parseFrom(InputStream input) throws IOException {
                return (Annotation) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static Annotation parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Annotation) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Annotation parseDelimitedFrom(InputStream input) throws IOException {
                return (Annotation) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static Annotation parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Annotation) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Annotation parseFrom(CodedInputStream input) throws IOException {
                return (Annotation) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static Annotation parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Annotation) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(Annotation prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<Annotation, Builder> implements AnnotationOrBuilder {
                private Builder() {
                    super(Annotation.DEFAULT_INSTANCE);
                }

                public List<Integer> getPathList() {
                    return Collections.unmodifiableList(((Annotation) this.instance).getPathList());
                }

                public int getPathCount() {
                    return ((Annotation) this.instance).getPathCount();
                }

                public int getPath(int index) {
                    return ((Annotation) this.instance).getPath(index);
                }

                public Builder setPath(int index, int value) {
                    copyOnWrite();
                    ((Annotation) this.instance).setPath(index, value);
                    return this;
                }

                public Builder addPath(int value) {
                    copyOnWrite();
                    ((Annotation) this.instance).addPath(value);
                    return this;
                }

                public Builder addAllPath(Iterable<? extends Integer> values) {
                    copyOnWrite();
                    ((Annotation) this.instance).addAllPath(values);
                    return this;
                }

                public Builder clearPath() {
                    copyOnWrite();
                    ((Annotation) this.instance).clearPath();
                    return this;
                }

                public boolean hasSourceFile() {
                    return ((Annotation) this.instance).hasSourceFile();
                }

                public String getSourceFile() {
                    return ((Annotation) this.instance).getSourceFile();
                }

                public ByteString getSourceFileBytes() {
                    return ((Annotation) this.instance).getSourceFileBytes();
                }

                public Builder setSourceFile(String value) {
                    copyOnWrite();
                    ((Annotation) this.instance).setSourceFile(value);
                    return this;
                }

                public Builder clearSourceFile() {
                    copyOnWrite();
                    ((Annotation) this.instance).clearSourceFile();
                    return this;
                }

                public Builder setSourceFileBytes(ByteString value) {
                    copyOnWrite();
                    ((Annotation) this.instance).setSourceFileBytes(value);
                    return this;
                }

                public boolean hasBegin() {
                    return ((Annotation) this.instance).hasBegin();
                }

                public int getBegin() {
                    return ((Annotation) this.instance).getBegin();
                }

                public Builder setBegin(int value) {
                    copyOnWrite();
                    ((Annotation) this.instance).setBegin(value);
                    return this;
                }

                public Builder clearBegin() {
                    copyOnWrite();
                    ((Annotation) this.instance).clearBegin();
                    return this;
                }

                public boolean hasEnd() {
                    return ((Annotation) this.instance).hasEnd();
                }

                public int getEnd() {
                    return ((Annotation) this.instance).getEnd();
                }

                public Builder setEnd(int value) {
                    copyOnWrite();
                    ((Annotation) this.instance).setEnd(value);
                    return this;
                }

                public Builder clearEnd() {
                    copyOnWrite();
                    ((Annotation) this.instance).clearEnd();
                    return this;
                }

                public boolean hasSemantic() {
                    return ((Annotation) this.instance).hasSemantic();
                }

                public Semantic getSemantic() {
                    return ((Annotation) this.instance).getSemantic();
                }

                public Builder setSemantic(Semantic value) {
                    copyOnWrite();
                    ((Annotation) this.instance).setSemantic(value);
                    return this;
                }

                public Builder clearSemantic() {
                    copyOnWrite();
                    ((Annotation) this.instance).clearSemantic();
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new Annotation();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0001\u0000\u0001'\u0002ဈ\u0000\u0003င\u0001\u0004င\u0002\u0005᠌\u0003", new Object[]{"bitField0_", "path_", "sourceFile_", "begin_", "end_", "semantic_", Semantic.internalGetVerifier()});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<Annotation> parser = PARSER;
                        if (parser == null) {
                            synchronized (Annotation.class) {
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
                Annotation defaultInstance = new Annotation();
                DEFAULT_INSTANCE = defaultInstance;
                GeneratedMessageLite.registerDefaultInstance(Annotation.class, defaultInstance);
            }

            public static Annotation getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<Annotation> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public List<Annotation> getAnnotationList() {
            return this.annotation_;
        }

        public List<? extends AnnotationOrBuilder> getAnnotationOrBuilderList() {
            return this.annotation_;
        }

        public int getAnnotationCount() {
            return this.annotation_.size();
        }

        public Annotation getAnnotation(int index) {
            return (Annotation) this.annotation_.get(index);
        }

        public AnnotationOrBuilder getAnnotationOrBuilder(int index) {
            return (AnnotationOrBuilder) this.annotation_.get(index);
        }

        private void ensureAnnotationIsMutable() {
            Internal.ProtobufList<Annotation> tmp = this.annotation_;
            if (!tmp.isModifiable()) {
                this.annotation_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setAnnotation(int index, Annotation value) {
            value.getClass();
            ensureAnnotationIsMutable();
            this.annotation_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addAnnotation(Annotation value) {
            value.getClass();
            ensureAnnotationIsMutable();
            this.annotation_.add(value);
        }

        /* access modifiers changed from: private */
        public void addAnnotation(int index, Annotation value) {
            value.getClass();
            ensureAnnotationIsMutable();
            this.annotation_.add(index, value);
        }

        /* access modifiers changed from: private */
        public void addAllAnnotation(Iterable<? extends Annotation> values) {
            ensureAnnotationIsMutable();
            AbstractMessageLite.addAll(values, this.annotation_);
        }

        /* access modifiers changed from: private */
        public void clearAnnotation() {
            this.annotation_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeAnnotation(int index) {
            ensureAnnotationIsMutable();
            this.annotation_.remove(index);
        }

        public static GeneratedCodeInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (GeneratedCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GeneratedCodeInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GeneratedCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GeneratedCodeInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (GeneratedCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GeneratedCodeInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GeneratedCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GeneratedCodeInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (GeneratedCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static GeneratedCodeInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (GeneratedCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static GeneratedCodeInfo parseFrom(InputStream input) throws IOException {
            return (GeneratedCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static GeneratedCodeInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GeneratedCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static GeneratedCodeInfo parseDelimitedFrom(InputStream input) throws IOException {
            return (GeneratedCodeInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static GeneratedCodeInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GeneratedCodeInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static GeneratedCodeInfo parseFrom(CodedInputStream input) throws IOException {
            return (GeneratedCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static GeneratedCodeInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (GeneratedCodeInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(GeneratedCodeInfo prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<GeneratedCodeInfo, Builder> implements GeneratedCodeInfoOrBuilder {
            private Builder() {
                super(GeneratedCodeInfo.DEFAULT_INSTANCE);
            }

            public List<Annotation> getAnnotationList() {
                return Collections.unmodifiableList(((GeneratedCodeInfo) this.instance).getAnnotationList());
            }

            public int getAnnotationCount() {
                return ((GeneratedCodeInfo) this.instance).getAnnotationCount();
            }

            public Annotation getAnnotation(int index) {
                return ((GeneratedCodeInfo) this.instance).getAnnotation(index);
            }

            public Builder setAnnotation(int index, Annotation value) {
                copyOnWrite();
                ((GeneratedCodeInfo) this.instance).setAnnotation(index, value);
                return this;
            }

            public Builder setAnnotation(int index, Annotation.Builder builderForValue) {
                copyOnWrite();
                ((GeneratedCodeInfo) this.instance).setAnnotation(index, (Annotation) builderForValue.build());
                return this;
            }

            public Builder addAnnotation(Annotation value) {
                copyOnWrite();
                ((GeneratedCodeInfo) this.instance).addAnnotation(value);
                return this;
            }

            public Builder addAnnotation(int index, Annotation value) {
                copyOnWrite();
                ((GeneratedCodeInfo) this.instance).addAnnotation(index, value);
                return this;
            }

            public Builder addAnnotation(Annotation.Builder builderForValue) {
                copyOnWrite();
                ((GeneratedCodeInfo) this.instance).addAnnotation((Annotation) builderForValue.build());
                return this;
            }

            public Builder addAnnotation(int index, Annotation.Builder builderForValue) {
                copyOnWrite();
                ((GeneratedCodeInfo) this.instance).addAnnotation(index, (Annotation) builderForValue.build());
                return this;
            }

            public Builder addAllAnnotation(Iterable<? extends Annotation> values) {
                copyOnWrite();
                ((GeneratedCodeInfo) this.instance).addAllAnnotation(values);
                return this;
            }

            public Builder clearAnnotation() {
                copyOnWrite();
                ((GeneratedCodeInfo) this.instance).clearAnnotation();
                return this;
            }

            public Builder removeAnnotation(int index) {
                copyOnWrite();
                ((GeneratedCodeInfo) this.instance).removeAnnotation(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new GeneratedCodeInfo();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"annotation_", Annotation.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<GeneratedCodeInfo> parser = PARSER;
                    if (parser == null) {
                        synchronized (GeneratedCodeInfo.class) {
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
            GeneratedCodeInfo defaultInstance = new GeneratedCodeInfo();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(GeneratedCodeInfo.class, defaultInstance);
        }

        public static GeneratedCodeInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<GeneratedCodeInfo> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
