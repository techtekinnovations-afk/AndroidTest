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

public final class CancelOperationRequest extends GeneratedMessageLite<CancelOperationRequest, Builder> implements CancelOperationRequestOrBuilder {
    /* access modifiers changed from: private */
    public static final CancelOperationRequest DEFAULT_INSTANCE;
    public static final int NAME_FIELD_NUMBER = 1;
    private static volatile Parser<CancelOperationRequest> PARSER;
    private String name_ = "";

    private CancelOperationRequest() {
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

    public static CancelOperationRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (CancelOperationRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static CancelOperationRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (CancelOperationRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static CancelOperationRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (CancelOperationRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static CancelOperationRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (CancelOperationRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static CancelOperationRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (CancelOperationRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static CancelOperationRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (CancelOperationRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static CancelOperationRequest parseFrom(InputStream input) throws IOException {
        return (CancelOperationRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static CancelOperationRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (CancelOperationRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static CancelOperationRequest parseDelimitedFrom(InputStream input) throws IOException {
        return (CancelOperationRequest) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static CancelOperationRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (CancelOperationRequest) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static CancelOperationRequest parseFrom(CodedInputStream input) throws IOException {
        return (CancelOperationRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static CancelOperationRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (CancelOperationRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(CancelOperationRequest prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<CancelOperationRequest, Builder> implements CancelOperationRequestOrBuilder {
        private Builder() {
            super(CancelOperationRequest.DEFAULT_INSTANCE);
        }

        public String getName() {
            return ((CancelOperationRequest) this.instance).getName();
        }

        public ByteString getNameBytes() {
            return ((CancelOperationRequest) this.instance).getNameBytes();
        }

        public Builder setName(String value) {
            copyOnWrite();
            ((CancelOperationRequest) this.instance).setName(value);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((CancelOperationRequest) this.instance).clearName();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            copyOnWrite();
            ((CancelOperationRequest) this.instance).setNameBytes(value);
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new CancelOperationRequest();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001Ȉ", new Object[]{"name_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<CancelOperationRequest> parser = PARSER;
                if (parser == null) {
                    synchronized (CancelOperationRequest.class) {
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
        CancelOperationRequest defaultInstance = new CancelOperationRequest();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(CancelOperationRequest.class, defaultInstance);
    }

    public static CancelOperationRequest getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<CancelOperationRequest> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
