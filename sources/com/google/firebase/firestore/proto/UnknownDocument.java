package com.google.firebase.firestore.proto;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.google.protobuf.Timestamp;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class UnknownDocument extends GeneratedMessageLite<UnknownDocument, Builder> implements UnknownDocumentOrBuilder {
    /* access modifiers changed from: private */
    public static final UnknownDocument DEFAULT_INSTANCE;
    public static final int NAME_FIELD_NUMBER = 1;
    private static volatile Parser<UnknownDocument> PARSER = null;
    public static final int VERSION_FIELD_NUMBER = 2;
    private int bitField0_;
    private String name_ = "";
    private Timestamp version_;

    private UnknownDocument() {
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

    public boolean hasVersion() {
        return (this.bitField0_ & 1) != 0;
    }

    public Timestamp getVersion() {
        return this.version_ == null ? Timestamp.getDefaultInstance() : this.version_;
    }

    /* access modifiers changed from: private */
    public void setVersion(Timestamp value) {
        value.getClass();
        this.version_ = value;
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void mergeVersion(Timestamp value) {
        value.getClass();
        if (this.version_ == null || this.version_ == Timestamp.getDefaultInstance()) {
            this.version_ = value;
        } else {
            this.version_ = (Timestamp) ((Timestamp.Builder) Timestamp.newBuilder(this.version_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void clearVersion() {
        this.version_ = null;
        this.bitField0_ &= -2;
    }

    public static UnknownDocument parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (UnknownDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static UnknownDocument parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (UnknownDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static UnknownDocument parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (UnknownDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static UnknownDocument parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (UnknownDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static UnknownDocument parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (UnknownDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static UnknownDocument parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (UnknownDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static UnknownDocument parseFrom(InputStream input) throws IOException {
        return (UnknownDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static UnknownDocument parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (UnknownDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static UnknownDocument parseDelimitedFrom(InputStream input) throws IOException {
        return (UnknownDocument) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static UnknownDocument parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (UnknownDocument) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static UnknownDocument parseFrom(CodedInputStream input) throws IOException {
        return (UnknownDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static UnknownDocument parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (UnknownDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(UnknownDocument prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<UnknownDocument, Builder> implements UnknownDocumentOrBuilder {
        private Builder() {
            super(UnknownDocument.DEFAULT_INSTANCE);
        }

        public String getName() {
            return ((UnknownDocument) this.instance).getName();
        }

        public ByteString getNameBytes() {
            return ((UnknownDocument) this.instance).getNameBytes();
        }

        public Builder setName(String value) {
            copyOnWrite();
            ((UnknownDocument) this.instance).setName(value);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((UnknownDocument) this.instance).clearName();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            copyOnWrite();
            ((UnknownDocument) this.instance).setNameBytes(value);
            return this;
        }

        public boolean hasVersion() {
            return ((UnknownDocument) this.instance).hasVersion();
        }

        public Timestamp getVersion() {
            return ((UnknownDocument) this.instance).getVersion();
        }

        public Builder setVersion(Timestamp value) {
            copyOnWrite();
            ((UnknownDocument) this.instance).setVersion(value);
            return this;
        }

        public Builder setVersion(Timestamp.Builder builderForValue) {
            copyOnWrite();
            ((UnknownDocument) this.instance).setVersion((Timestamp) builderForValue.build());
            return this;
        }

        public Builder mergeVersion(Timestamp value) {
            copyOnWrite();
            ((UnknownDocument) this.instance).mergeVersion(value);
            return this;
        }

        public Builder clearVersion() {
            copyOnWrite();
            ((UnknownDocument) this.instance).clearVersion();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new UnknownDocument();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002ဉ\u0000", new Object[]{"bitField0_", "name_", "version_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<UnknownDocument> parser = PARSER;
                if (parser == null) {
                    synchronized (UnknownDocument.class) {
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
        UnknownDocument defaultInstance = new UnknownDocument();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(UnknownDocument.class, defaultInstance);
    }

    public static UnknownDocument getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<UnknownDocument> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
