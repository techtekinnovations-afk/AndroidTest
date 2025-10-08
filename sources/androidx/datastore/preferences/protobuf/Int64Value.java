package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.GeneratedMessageLite;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Int64Value extends GeneratedMessageLite<Int64Value, Builder> implements Int64ValueOrBuilder {
    /* access modifiers changed from: private */
    public static final Int64Value DEFAULT_INSTANCE;
    private static volatile Parser<Int64Value> PARSER = null;
    public static final int VALUE_FIELD_NUMBER = 1;
    private long value_;

    private Int64Value() {
    }

    public long getValue() {
        return this.value_;
    }

    /* access modifiers changed from: private */
    public void setValue(long value) {
        this.value_ = value;
    }

    /* access modifiers changed from: private */
    public void clearValue() {
        this.value_ = 0;
    }

    public static Int64Value parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (Int64Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Int64Value parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Int64Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Int64Value parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (Int64Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Int64Value parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Int64Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Int64Value parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (Int64Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Int64Value parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Int64Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Int64Value parseFrom(InputStream input) throws IOException {
        return (Int64Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static Int64Value parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Int64Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Int64Value parseDelimitedFrom(InputStream input) throws IOException {
        return (Int64Value) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static Int64Value parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Int64Value) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Int64Value parseFrom(CodedInputStream input) throws IOException {
        return (Int64Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static Int64Value parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Int64Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(Int64Value prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Int64Value, Builder> implements Int64ValueOrBuilder {
        private Builder() {
            super(Int64Value.DEFAULT_INSTANCE);
        }

        public long getValue() {
            return ((Int64Value) this.instance).getValue();
        }

        public Builder setValue(long value) {
            copyOnWrite();
            ((Int64Value) this.instance).setValue(value);
            return this;
        }

        public Builder clearValue() {
            copyOnWrite();
            ((Int64Value) this.instance).clearValue();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new Int64Value();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0002", new Object[]{"value_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<Int64Value> parser = PARSER;
                if (parser == null) {
                    synchronized (Int64Value.class) {
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
        Int64Value defaultInstance = new Int64Value();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(Int64Value.class, defaultInstance);
    }

    public static Int64Value getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Int64Value of(long value) {
        return (Int64Value) newBuilder().setValue(value).build();
    }

    public static Parser<Int64Value> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
