package com.google.api;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class SystemParameter extends GeneratedMessageLite<SystemParameter, Builder> implements SystemParameterOrBuilder {
    /* access modifiers changed from: private */
    public static final SystemParameter DEFAULT_INSTANCE;
    public static final int HTTP_HEADER_FIELD_NUMBER = 2;
    public static final int NAME_FIELD_NUMBER = 1;
    private static volatile Parser<SystemParameter> PARSER = null;
    public static final int URL_QUERY_PARAMETER_FIELD_NUMBER = 3;
    private String httpHeader_ = "";
    private String name_ = "";
    private String urlQueryParameter_ = "";

    private SystemParameter() {
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

    public String getHttpHeader() {
        return this.httpHeader_;
    }

    public ByteString getHttpHeaderBytes() {
        return ByteString.copyFromUtf8(this.httpHeader_);
    }

    /* access modifiers changed from: private */
    public void setHttpHeader(String value) {
        Class<?> cls = value.getClass();
        this.httpHeader_ = value;
    }

    /* access modifiers changed from: private */
    public void clearHttpHeader() {
        this.httpHeader_ = getDefaultInstance().getHttpHeader();
    }

    /* access modifiers changed from: private */
    public void setHttpHeaderBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.httpHeader_ = value.toStringUtf8();
    }

    public String getUrlQueryParameter() {
        return this.urlQueryParameter_;
    }

    public ByteString getUrlQueryParameterBytes() {
        return ByteString.copyFromUtf8(this.urlQueryParameter_);
    }

    /* access modifiers changed from: private */
    public void setUrlQueryParameter(String value) {
        Class<?> cls = value.getClass();
        this.urlQueryParameter_ = value;
    }

    /* access modifiers changed from: private */
    public void clearUrlQueryParameter() {
        this.urlQueryParameter_ = getDefaultInstance().getUrlQueryParameter();
    }

    /* access modifiers changed from: private */
    public void setUrlQueryParameterBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.urlQueryParameter_ = value.toStringUtf8();
    }

    public static SystemParameter parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (SystemParameter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static SystemParameter parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (SystemParameter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static SystemParameter parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (SystemParameter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static SystemParameter parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (SystemParameter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static SystemParameter parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (SystemParameter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static SystemParameter parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (SystemParameter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static SystemParameter parseFrom(InputStream input) throws IOException {
        return (SystemParameter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static SystemParameter parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (SystemParameter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static SystemParameter parseDelimitedFrom(InputStream input) throws IOException {
        return (SystemParameter) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static SystemParameter parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (SystemParameter) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static SystemParameter parseFrom(CodedInputStream input) throws IOException {
        return (SystemParameter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static SystemParameter parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (SystemParameter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(SystemParameter prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<SystemParameter, Builder> implements SystemParameterOrBuilder {
        private Builder() {
            super(SystemParameter.DEFAULT_INSTANCE);
        }

        public String getName() {
            return ((SystemParameter) this.instance).getName();
        }

        public ByteString getNameBytes() {
            return ((SystemParameter) this.instance).getNameBytes();
        }

        public Builder setName(String value) {
            copyOnWrite();
            ((SystemParameter) this.instance).setName(value);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((SystemParameter) this.instance).clearName();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            copyOnWrite();
            ((SystemParameter) this.instance).setNameBytes(value);
            return this;
        }

        public String getHttpHeader() {
            return ((SystemParameter) this.instance).getHttpHeader();
        }

        public ByteString getHttpHeaderBytes() {
            return ((SystemParameter) this.instance).getHttpHeaderBytes();
        }

        public Builder setHttpHeader(String value) {
            copyOnWrite();
            ((SystemParameter) this.instance).setHttpHeader(value);
            return this;
        }

        public Builder clearHttpHeader() {
            copyOnWrite();
            ((SystemParameter) this.instance).clearHttpHeader();
            return this;
        }

        public Builder setHttpHeaderBytes(ByteString value) {
            copyOnWrite();
            ((SystemParameter) this.instance).setHttpHeaderBytes(value);
            return this;
        }

        public String getUrlQueryParameter() {
            return ((SystemParameter) this.instance).getUrlQueryParameter();
        }

        public ByteString getUrlQueryParameterBytes() {
            return ((SystemParameter) this.instance).getUrlQueryParameterBytes();
        }

        public Builder setUrlQueryParameter(String value) {
            copyOnWrite();
            ((SystemParameter) this.instance).setUrlQueryParameter(value);
            return this;
        }

        public Builder clearUrlQueryParameter() {
            copyOnWrite();
            ((SystemParameter) this.instance).clearUrlQueryParameter();
            return this;
        }

        public Builder setUrlQueryParameterBytes(ByteString value) {
            copyOnWrite();
            ((SystemParameter) this.instance).setUrlQueryParameterBytes(value);
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new SystemParameter();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ\u0003Ȉ", new Object[]{"name_", "httpHeader_", "urlQueryParameter_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<SystemParameter> parser = PARSER;
                if (parser == null) {
                    synchronized (SystemParameter.class) {
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
        SystemParameter defaultInstance = new SystemParameter();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(SystemParameter.class, defaultInstance);
    }

    public static SystemParameter getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<SystemParameter> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
