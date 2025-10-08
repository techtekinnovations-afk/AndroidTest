package com.google.firestore.v1;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class BitSequence extends GeneratedMessageLite<BitSequence, Builder> implements BitSequenceOrBuilder {
    public static final int BITMAP_FIELD_NUMBER = 1;
    /* access modifiers changed from: private */
    public static final BitSequence DEFAULT_INSTANCE;
    public static final int PADDING_FIELD_NUMBER = 2;
    private static volatile Parser<BitSequence> PARSER;
    private ByteString bitmap_ = ByteString.EMPTY;
    private int padding_;

    private BitSequence() {
    }

    public ByteString getBitmap() {
        return this.bitmap_;
    }

    /* access modifiers changed from: private */
    public void setBitmap(ByteString value) {
        Class<?> cls = value.getClass();
        this.bitmap_ = value;
    }

    /* access modifiers changed from: private */
    public void clearBitmap() {
        this.bitmap_ = getDefaultInstance().getBitmap();
    }

    public int getPadding() {
        return this.padding_;
    }

    /* access modifiers changed from: private */
    public void setPadding(int value) {
        this.padding_ = value;
    }

    /* access modifiers changed from: private */
    public void clearPadding() {
        this.padding_ = 0;
    }

    public static BitSequence parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (BitSequence) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static BitSequence parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (BitSequence) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static BitSequence parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (BitSequence) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static BitSequence parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (BitSequence) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static BitSequence parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (BitSequence) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static BitSequence parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (BitSequence) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static BitSequence parseFrom(InputStream input) throws IOException {
        return (BitSequence) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static BitSequence parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (BitSequence) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static BitSequence parseDelimitedFrom(InputStream input) throws IOException {
        return (BitSequence) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static BitSequence parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (BitSequence) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static BitSequence parseFrom(CodedInputStream input) throws IOException {
        return (BitSequence) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static BitSequence parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (BitSequence) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(BitSequence prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<BitSequence, Builder> implements BitSequenceOrBuilder {
        private Builder() {
            super(BitSequence.DEFAULT_INSTANCE);
        }

        public ByteString getBitmap() {
            return ((BitSequence) this.instance).getBitmap();
        }

        public Builder setBitmap(ByteString value) {
            copyOnWrite();
            ((BitSequence) this.instance).setBitmap(value);
            return this;
        }

        public Builder clearBitmap() {
            copyOnWrite();
            ((BitSequence) this.instance).clearBitmap();
            return this;
        }

        public int getPadding() {
            return ((BitSequence) this.instance).getPadding();
        }

        public Builder setPadding(int value) {
            copyOnWrite();
            ((BitSequence) this.instance).setPadding(value);
            return this;
        }

        public Builder clearPadding() {
            copyOnWrite();
            ((BitSequence) this.instance).clearPadding();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new BitSequence();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\n\u0002\u0004", new Object[]{"bitmap_", "padding_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<BitSequence> parser = PARSER;
                if (parser == null) {
                    synchronized (BitSequence.class) {
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
        BitSequence defaultInstance = new BitSequence();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(BitSequence.class, defaultInstance);
    }

    public static BitSequence getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<BitSequence> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
