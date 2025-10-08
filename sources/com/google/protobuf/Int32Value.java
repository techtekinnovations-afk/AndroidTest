package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Int32Value extends GeneratedMessageLite<Int32Value, Builder> implements Int32ValueOrBuilder {
    /* access modifiers changed from: private */
    public static final Int32Value DEFAULT_INSTANCE;
    private static volatile Parser<Int32Value> PARSER = null;
    public static final int VALUE_FIELD_NUMBER = 1;
    private int value_;

    private Int32Value() {
    }

    public int getValue() {
        return this.value_;
    }

    /* access modifiers changed from: private */
    public void setValue(int value) {
        this.value_ = value;
    }

    /* access modifiers changed from: private */
    public void clearValue() {
        this.value_ = 0;
    }

    public static Int32Value parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (Int32Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Int32Value parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Int32Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Int32Value parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (Int32Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Int32Value parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Int32Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Int32Value parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (Int32Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Int32Value parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Int32Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Int32Value parseFrom(InputStream input) throws IOException {
        return (Int32Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static Int32Value parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Int32Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Int32Value parseDelimitedFrom(InputStream input) throws IOException {
        return (Int32Value) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static Int32Value parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Int32Value) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Int32Value parseFrom(CodedInputStream input) throws IOException {
        return (Int32Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static Int32Value parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Int32Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(Int32Value prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Int32Value, Builder> implements Int32ValueOrBuilder {
        private Builder() {
            super(Int32Value.DEFAULT_INSTANCE);
        }

        public int getValue() {
            return ((Int32Value) this.instance).getValue();
        }

        public Builder setValue(int value) {
            copyOnWrite();
            ((Int32Value) this.instance).setValue(value);
            return this;
        }

        public Builder clearValue() {
            copyOnWrite();
            ((Int32Value) this.instance).clearValue();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new Int32Value();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0004", new Object[]{"value_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<Int32Value> parser = PARSER;
                if (parser == null) {
                    synchronized (Int32Value.class) {
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
        Int32Value defaultInstance = new Int32Value();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(Int32Value.class, defaultInstance);
    }

    public static Int32Value getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Int32Value of(int value) {
        return (Int32Value) newBuilder().setValue(value).build();
    }

    public static Parser<Int32Value> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
