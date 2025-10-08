package com.google.protobuf;

import com.google.protobuf.EnumValue;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.Option;
import com.google.protobuf.SourceContext;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class Enum extends GeneratedMessageLite<Enum, Builder> implements EnumOrBuilder {
    /* access modifiers changed from: private */
    public static final Enum DEFAULT_INSTANCE;
    public static final int EDITION_FIELD_NUMBER = 6;
    public static final int ENUMVALUE_FIELD_NUMBER = 2;
    public static final int NAME_FIELD_NUMBER = 1;
    public static final int OPTIONS_FIELD_NUMBER = 3;
    private static volatile Parser<Enum> PARSER = null;
    public static final int SOURCE_CONTEXT_FIELD_NUMBER = 4;
    public static final int SYNTAX_FIELD_NUMBER = 5;
    private int bitField0_;
    private String edition_ = "";
    private Internal.ProtobufList<EnumValue> enumvalue_ = emptyProtobufList();
    private String name_ = "";
    private Internal.ProtobufList<Option> options_ = emptyProtobufList();
    private SourceContext sourceContext_;
    private int syntax_;

    private Enum() {
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
        this.name_ = value;
    }

    /* access modifiers changed from: private */
    public void clearName() {
        this.name_ = getDefaultInstance().getName();
    }

    /* access modifiers changed from: private */
    public void setNameBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.name_ = value.toStringUtf8();
    }

    public List<EnumValue> getEnumvalueList() {
        return this.enumvalue_;
    }

    public List<? extends EnumValueOrBuilder> getEnumvalueOrBuilderList() {
        return this.enumvalue_;
    }

    public int getEnumvalueCount() {
        return this.enumvalue_.size();
    }

    public EnumValue getEnumvalue(int index) {
        return (EnumValue) this.enumvalue_.get(index);
    }

    public EnumValueOrBuilder getEnumvalueOrBuilder(int index) {
        return (EnumValueOrBuilder) this.enumvalue_.get(index);
    }

    private void ensureEnumvalueIsMutable() {
        Internal.ProtobufList<EnumValue> tmp = this.enumvalue_;
        if (!tmp.isModifiable()) {
            this.enumvalue_ = GeneratedMessageLite.mutableCopy(tmp);
        }
    }

    /* access modifiers changed from: private */
    public void setEnumvalue(int index, EnumValue value) {
        value.getClass();
        ensureEnumvalueIsMutable();
        this.enumvalue_.set(index, value);
    }

    /* access modifiers changed from: private */
    public void addEnumvalue(EnumValue value) {
        value.getClass();
        ensureEnumvalueIsMutable();
        this.enumvalue_.add(value);
    }

    /* access modifiers changed from: private */
    public void addEnumvalue(int index, EnumValue value) {
        value.getClass();
        ensureEnumvalueIsMutable();
        this.enumvalue_.add(index, value);
    }

    /* access modifiers changed from: private */
    public void addAllEnumvalue(Iterable<? extends EnumValue> values) {
        ensureEnumvalueIsMutable();
        AbstractMessageLite.addAll(values, this.enumvalue_);
    }

    /* access modifiers changed from: private */
    public void clearEnumvalue() {
        this.enumvalue_ = emptyProtobufList();
    }

    /* access modifiers changed from: private */
    public void removeEnumvalue(int index) {
        ensureEnumvalueIsMutable();
        this.enumvalue_.remove(index);
    }

    public List<Option> getOptionsList() {
        return this.options_;
    }

    public List<? extends OptionOrBuilder> getOptionsOrBuilderList() {
        return this.options_;
    }

    public int getOptionsCount() {
        return this.options_.size();
    }

    public Option getOptions(int index) {
        return (Option) this.options_.get(index);
    }

    public OptionOrBuilder getOptionsOrBuilder(int index) {
        return (OptionOrBuilder) this.options_.get(index);
    }

    private void ensureOptionsIsMutable() {
        Internal.ProtobufList<Option> tmp = this.options_;
        if (!tmp.isModifiable()) {
            this.options_ = GeneratedMessageLite.mutableCopy(tmp);
        }
    }

    /* access modifiers changed from: private */
    public void setOptions(int index, Option value) {
        value.getClass();
        ensureOptionsIsMutable();
        this.options_.set(index, value);
    }

    /* access modifiers changed from: private */
    public void addOptions(Option value) {
        value.getClass();
        ensureOptionsIsMutable();
        this.options_.add(value);
    }

    /* access modifiers changed from: private */
    public void addOptions(int index, Option value) {
        value.getClass();
        ensureOptionsIsMutable();
        this.options_.add(index, value);
    }

    /* access modifiers changed from: private */
    public void addAllOptions(Iterable<? extends Option> values) {
        ensureOptionsIsMutable();
        AbstractMessageLite.addAll(values, this.options_);
    }

    /* access modifiers changed from: private */
    public void clearOptions() {
        this.options_ = emptyProtobufList();
    }

    /* access modifiers changed from: private */
    public void removeOptions(int index) {
        ensureOptionsIsMutable();
        this.options_.remove(index);
    }

    public boolean hasSourceContext() {
        return (this.bitField0_ & 1) != 0;
    }

    public SourceContext getSourceContext() {
        return this.sourceContext_ == null ? SourceContext.getDefaultInstance() : this.sourceContext_;
    }

    /* access modifiers changed from: private */
    public void setSourceContext(SourceContext value) {
        value.getClass();
        this.sourceContext_ = value;
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void mergeSourceContext(SourceContext value) {
        value.getClass();
        if (this.sourceContext_ == null || this.sourceContext_ == SourceContext.getDefaultInstance()) {
            this.sourceContext_ = value;
        } else {
            this.sourceContext_ = (SourceContext) ((SourceContext.Builder) SourceContext.newBuilder(this.sourceContext_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void clearSourceContext() {
        this.sourceContext_ = null;
        this.bitField0_ &= -2;
    }

    public int getSyntaxValue() {
        return this.syntax_;
    }

    public Syntax getSyntax() {
        Syntax result = Syntax.forNumber(this.syntax_);
        return result == null ? Syntax.UNRECOGNIZED : result;
    }

    /* access modifiers changed from: private */
    public void setSyntaxValue(int value) {
        this.syntax_ = value;
    }

    /* access modifiers changed from: private */
    public void setSyntax(Syntax value) {
        this.syntax_ = value.getNumber();
    }

    /* access modifiers changed from: private */
    public void clearSyntax() {
        this.syntax_ = 0;
    }

    public String getEdition() {
        return this.edition_;
    }

    public ByteString getEditionBytes() {
        return ByteString.copyFromUtf8(this.edition_);
    }

    /* access modifiers changed from: private */
    public void setEdition(String value) {
        Class<?> cls = value.getClass();
        this.edition_ = value;
    }

    /* access modifiers changed from: private */
    public void clearEdition() {
        this.edition_ = getDefaultInstance().getEdition();
    }

    /* access modifiers changed from: private */
    public void setEditionBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.edition_ = value.toStringUtf8();
    }

    public static Enum parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Enum parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Enum parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Enum parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Enum parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Enum parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Enum) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Enum parseFrom(InputStream input) throws IOException {
        return (Enum) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static Enum parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Enum) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Enum parseDelimitedFrom(InputStream input) throws IOException {
        return (Enum) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static Enum parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Enum) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Enum parseFrom(CodedInputStream input) throws IOException {
        return (Enum) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static Enum parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Enum) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(Enum prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Enum, Builder> implements EnumOrBuilder {
        private Builder() {
            super(Enum.DEFAULT_INSTANCE);
        }

        public String getName() {
            return ((Enum) this.instance).getName();
        }

        public ByteString getNameBytes() {
            return ((Enum) this.instance).getNameBytes();
        }

        public Builder setName(String value) {
            copyOnWrite();
            ((Enum) this.instance).setName(value);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((Enum) this.instance).clearName();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            copyOnWrite();
            ((Enum) this.instance).setNameBytes(value);
            return this;
        }

        public List<EnumValue> getEnumvalueList() {
            return Collections.unmodifiableList(((Enum) this.instance).getEnumvalueList());
        }

        public int getEnumvalueCount() {
            return ((Enum) this.instance).getEnumvalueCount();
        }

        public EnumValue getEnumvalue(int index) {
            return ((Enum) this.instance).getEnumvalue(index);
        }

        public Builder setEnumvalue(int index, EnumValue value) {
            copyOnWrite();
            ((Enum) this.instance).setEnumvalue(index, value);
            return this;
        }

        public Builder setEnumvalue(int index, EnumValue.Builder builderForValue) {
            copyOnWrite();
            ((Enum) this.instance).setEnumvalue(index, (EnumValue) builderForValue.build());
            return this;
        }

        public Builder addEnumvalue(EnumValue value) {
            copyOnWrite();
            ((Enum) this.instance).addEnumvalue(value);
            return this;
        }

        public Builder addEnumvalue(int index, EnumValue value) {
            copyOnWrite();
            ((Enum) this.instance).addEnumvalue(index, value);
            return this;
        }

        public Builder addEnumvalue(EnumValue.Builder builderForValue) {
            copyOnWrite();
            ((Enum) this.instance).addEnumvalue((EnumValue) builderForValue.build());
            return this;
        }

        public Builder addEnumvalue(int index, EnumValue.Builder builderForValue) {
            copyOnWrite();
            ((Enum) this.instance).addEnumvalue(index, (EnumValue) builderForValue.build());
            return this;
        }

        public Builder addAllEnumvalue(Iterable<? extends EnumValue> values) {
            copyOnWrite();
            ((Enum) this.instance).addAllEnumvalue(values);
            return this;
        }

        public Builder clearEnumvalue() {
            copyOnWrite();
            ((Enum) this.instance).clearEnumvalue();
            return this;
        }

        public Builder removeEnumvalue(int index) {
            copyOnWrite();
            ((Enum) this.instance).removeEnumvalue(index);
            return this;
        }

        public List<Option> getOptionsList() {
            return Collections.unmodifiableList(((Enum) this.instance).getOptionsList());
        }

        public int getOptionsCount() {
            return ((Enum) this.instance).getOptionsCount();
        }

        public Option getOptions(int index) {
            return ((Enum) this.instance).getOptions(index);
        }

        public Builder setOptions(int index, Option value) {
            copyOnWrite();
            ((Enum) this.instance).setOptions(index, value);
            return this;
        }

        public Builder setOptions(int index, Option.Builder builderForValue) {
            copyOnWrite();
            ((Enum) this.instance).setOptions(index, (Option) builderForValue.build());
            return this;
        }

        public Builder addOptions(Option value) {
            copyOnWrite();
            ((Enum) this.instance).addOptions(value);
            return this;
        }

        public Builder addOptions(int index, Option value) {
            copyOnWrite();
            ((Enum) this.instance).addOptions(index, value);
            return this;
        }

        public Builder addOptions(Option.Builder builderForValue) {
            copyOnWrite();
            ((Enum) this.instance).addOptions((Option) builderForValue.build());
            return this;
        }

        public Builder addOptions(int index, Option.Builder builderForValue) {
            copyOnWrite();
            ((Enum) this.instance).addOptions(index, (Option) builderForValue.build());
            return this;
        }

        public Builder addAllOptions(Iterable<? extends Option> values) {
            copyOnWrite();
            ((Enum) this.instance).addAllOptions(values);
            return this;
        }

        public Builder clearOptions() {
            copyOnWrite();
            ((Enum) this.instance).clearOptions();
            return this;
        }

        public Builder removeOptions(int index) {
            copyOnWrite();
            ((Enum) this.instance).removeOptions(index);
            return this;
        }

        public boolean hasSourceContext() {
            return ((Enum) this.instance).hasSourceContext();
        }

        public SourceContext getSourceContext() {
            return ((Enum) this.instance).getSourceContext();
        }

        public Builder setSourceContext(SourceContext value) {
            copyOnWrite();
            ((Enum) this.instance).setSourceContext(value);
            return this;
        }

        public Builder setSourceContext(SourceContext.Builder builderForValue) {
            copyOnWrite();
            ((Enum) this.instance).setSourceContext((SourceContext) builderForValue.build());
            return this;
        }

        public Builder mergeSourceContext(SourceContext value) {
            copyOnWrite();
            ((Enum) this.instance).mergeSourceContext(value);
            return this;
        }

        public Builder clearSourceContext() {
            copyOnWrite();
            ((Enum) this.instance).clearSourceContext();
            return this;
        }

        public int getSyntaxValue() {
            return ((Enum) this.instance).getSyntaxValue();
        }

        public Builder setSyntaxValue(int value) {
            copyOnWrite();
            ((Enum) this.instance).setSyntaxValue(value);
            return this;
        }

        public Syntax getSyntax() {
            return ((Enum) this.instance).getSyntax();
        }

        public Builder setSyntax(Syntax value) {
            copyOnWrite();
            ((Enum) this.instance).setSyntax(value);
            return this;
        }

        public Builder clearSyntax() {
            copyOnWrite();
            ((Enum) this.instance).clearSyntax();
            return this;
        }

        public String getEdition() {
            return ((Enum) this.instance).getEdition();
        }

        public ByteString getEditionBytes() {
            return ((Enum) this.instance).getEditionBytes();
        }

        public Builder setEdition(String value) {
            copyOnWrite();
            ((Enum) this.instance).setEdition(value);
            return this;
        }

        public Builder clearEdition() {
            copyOnWrite();
            ((Enum) this.instance).clearEdition();
            return this;
        }

        public Builder setEditionBytes(ByteString value) {
            copyOnWrite();
            ((Enum) this.instance).setEditionBytes(value);
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new Enum();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0002\u0000\u0001Ȉ\u0002\u001b\u0003\u001b\u0004ဉ\u0000\u0005\f\u0006Ȉ", new Object[]{"bitField0_", "name_", "enumvalue_", EnumValue.class, "options_", Option.class, "sourceContext_", "syntax_", "edition_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<Enum> parser = PARSER;
                if (parser == null) {
                    synchronized (Enum.class) {
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
        Enum defaultInstance = new Enum();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(Enum.class, defaultInstance);
    }

    public static Enum getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Enum> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
