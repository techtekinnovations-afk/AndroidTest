package com.google.longrunning;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class OperationInfo extends GeneratedMessageLite<OperationInfo, Builder> implements OperationInfoOrBuilder {
    /* access modifiers changed from: private */
    public static final OperationInfo DEFAULT_INSTANCE;
    public static final int METADATA_TYPE_FIELD_NUMBER = 2;
    private static volatile Parser<OperationInfo> PARSER = null;
    public static final int RESPONSE_TYPE_FIELD_NUMBER = 1;
    private String metadataType_ = "";
    private String responseType_ = "";

    private OperationInfo() {
    }

    public String getResponseType() {
        return this.responseType_;
    }

    public ByteString getResponseTypeBytes() {
        return ByteString.copyFromUtf8(this.responseType_);
    }

    /* access modifiers changed from: private */
    public void setResponseType(String value) {
        Class<?> cls = value.getClass();
        this.responseType_ = value;
    }

    /* access modifiers changed from: private */
    public void clearResponseType() {
        this.responseType_ = getDefaultInstance().getResponseType();
    }

    /* access modifiers changed from: private */
    public void setResponseTypeBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.responseType_ = value.toStringUtf8();
    }

    public String getMetadataType() {
        return this.metadataType_;
    }

    public ByteString getMetadataTypeBytes() {
        return ByteString.copyFromUtf8(this.metadataType_);
    }

    /* access modifiers changed from: private */
    public void setMetadataType(String value) {
        Class<?> cls = value.getClass();
        this.metadataType_ = value;
    }

    /* access modifiers changed from: private */
    public void clearMetadataType() {
        this.metadataType_ = getDefaultInstance().getMetadataType();
    }

    /* access modifiers changed from: private */
    public void setMetadataTypeBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.metadataType_ = value.toStringUtf8();
    }

    public static OperationInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (OperationInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static OperationInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (OperationInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static OperationInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (OperationInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static OperationInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (OperationInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static OperationInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (OperationInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static OperationInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (OperationInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static OperationInfo parseFrom(InputStream input) throws IOException {
        return (OperationInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static OperationInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (OperationInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static OperationInfo parseDelimitedFrom(InputStream input) throws IOException {
        return (OperationInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static OperationInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (OperationInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static OperationInfo parseFrom(CodedInputStream input) throws IOException {
        return (OperationInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static OperationInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (OperationInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(OperationInfo prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<OperationInfo, Builder> implements OperationInfoOrBuilder {
        private Builder() {
            super(OperationInfo.DEFAULT_INSTANCE);
        }

        public String getResponseType() {
            return ((OperationInfo) this.instance).getResponseType();
        }

        public ByteString getResponseTypeBytes() {
            return ((OperationInfo) this.instance).getResponseTypeBytes();
        }

        public Builder setResponseType(String value) {
            copyOnWrite();
            ((OperationInfo) this.instance).setResponseType(value);
            return this;
        }

        public Builder clearResponseType() {
            copyOnWrite();
            ((OperationInfo) this.instance).clearResponseType();
            return this;
        }

        public Builder setResponseTypeBytes(ByteString value) {
            copyOnWrite();
            ((OperationInfo) this.instance).setResponseTypeBytes(value);
            return this;
        }

        public String getMetadataType() {
            return ((OperationInfo) this.instance).getMetadataType();
        }

        public ByteString getMetadataTypeBytes() {
            return ((OperationInfo) this.instance).getMetadataTypeBytes();
        }

        public Builder setMetadataType(String value) {
            copyOnWrite();
            ((OperationInfo) this.instance).setMetadataType(value);
            return this;
        }

        public Builder clearMetadataType() {
            copyOnWrite();
            ((OperationInfo) this.instance).clearMetadataType();
            return this;
        }

        public Builder setMetadataTypeBytes(ByteString value) {
            copyOnWrite();
            ((OperationInfo) this.instance).setMetadataTypeBytes(value);
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new OperationInfo();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ", new Object[]{"responseType_", "metadataType_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<OperationInfo> parser = PARSER;
                if (parser == null) {
                    synchronized (OperationInfo.class) {
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
        OperationInfo defaultInstance = new OperationInfo();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(OperationInfo.class, defaultInstance);
    }

    public static OperationInfo getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<OperationInfo> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
