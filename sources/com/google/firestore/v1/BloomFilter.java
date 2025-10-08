package com.google.firestore.v1;

import com.google.firestore.v1.BitSequence;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class BloomFilter extends GeneratedMessageLite<BloomFilter, Builder> implements BloomFilterOrBuilder {
    public static final int BITS_FIELD_NUMBER = 1;
    /* access modifiers changed from: private */
    public static final BloomFilter DEFAULT_INSTANCE;
    public static final int HASH_COUNT_FIELD_NUMBER = 2;
    private static volatile Parser<BloomFilter> PARSER;
    private int bitField0_;
    private BitSequence bits_;
    private int hashCount_;

    private BloomFilter() {
    }

    public boolean hasBits() {
        return (this.bitField0_ & 1) != 0;
    }

    public BitSequence getBits() {
        return this.bits_ == null ? BitSequence.getDefaultInstance() : this.bits_;
    }

    /* access modifiers changed from: private */
    public void setBits(BitSequence value) {
        value.getClass();
        this.bits_ = value;
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void mergeBits(BitSequence value) {
        value.getClass();
        if (this.bits_ == null || this.bits_ == BitSequence.getDefaultInstance()) {
            this.bits_ = value;
        } else {
            this.bits_ = (BitSequence) ((BitSequence.Builder) BitSequence.newBuilder(this.bits_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void clearBits() {
        this.bits_ = null;
        this.bitField0_ &= -2;
    }

    public int getHashCount() {
        return this.hashCount_;
    }

    /* access modifiers changed from: private */
    public void setHashCount(int value) {
        this.hashCount_ = value;
    }

    /* access modifiers changed from: private */
    public void clearHashCount() {
        this.hashCount_ = 0;
    }

    public static BloomFilter parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (BloomFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static BloomFilter parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (BloomFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static BloomFilter parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (BloomFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static BloomFilter parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (BloomFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static BloomFilter parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (BloomFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static BloomFilter parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (BloomFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static BloomFilter parseFrom(InputStream input) throws IOException {
        return (BloomFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static BloomFilter parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (BloomFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static BloomFilter parseDelimitedFrom(InputStream input) throws IOException {
        return (BloomFilter) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static BloomFilter parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (BloomFilter) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static BloomFilter parseFrom(CodedInputStream input) throws IOException {
        return (BloomFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static BloomFilter parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (BloomFilter) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(BloomFilter prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<BloomFilter, Builder> implements BloomFilterOrBuilder {
        private Builder() {
            super(BloomFilter.DEFAULT_INSTANCE);
        }

        public boolean hasBits() {
            return ((BloomFilter) this.instance).hasBits();
        }

        public BitSequence getBits() {
            return ((BloomFilter) this.instance).getBits();
        }

        public Builder setBits(BitSequence value) {
            copyOnWrite();
            ((BloomFilter) this.instance).setBits(value);
            return this;
        }

        public Builder setBits(BitSequence.Builder builderForValue) {
            copyOnWrite();
            ((BloomFilter) this.instance).setBits((BitSequence) builderForValue.build());
            return this;
        }

        public Builder mergeBits(BitSequence value) {
            copyOnWrite();
            ((BloomFilter) this.instance).mergeBits(value);
            return this;
        }

        public Builder clearBits() {
            copyOnWrite();
            ((BloomFilter) this.instance).clearBits();
            return this;
        }

        public int getHashCount() {
            return ((BloomFilter) this.instance).getHashCount();
        }

        public Builder setHashCount(int value) {
            copyOnWrite();
            ((BloomFilter) this.instance).setHashCount(value);
            return this;
        }

        public Builder clearHashCount() {
            copyOnWrite();
            ((BloomFilter) this.instance).clearHashCount();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new BloomFilter();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဉ\u0000\u0002\u0004", new Object[]{"bitField0_", "bits_", "hashCount_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<BloomFilter> parser = PARSER;
                if (parser == null) {
                    synchronized (BloomFilter.class) {
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
        BloomFilter defaultInstance = new BloomFilter();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(BloomFilter.class, defaultInstance);
    }

    public static BloomFilter getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<BloomFilter> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
