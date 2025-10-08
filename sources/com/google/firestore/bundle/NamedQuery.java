package com.google.firestore.bundle;

import com.google.firestore.bundle.BundledQuery;
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

public final class NamedQuery extends GeneratedMessageLite<NamedQuery, Builder> implements NamedQueryOrBuilder {
    public static final int BUNDLED_QUERY_FIELD_NUMBER = 2;
    /* access modifiers changed from: private */
    public static final NamedQuery DEFAULT_INSTANCE;
    public static final int NAME_FIELD_NUMBER = 1;
    private static volatile Parser<NamedQuery> PARSER = null;
    public static final int READ_TIME_FIELD_NUMBER = 3;
    private int bitField0_;
    private BundledQuery bundledQuery_;
    private String name_ = "";
    private Timestamp readTime_;

    private NamedQuery() {
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

    public boolean hasBundledQuery() {
        return (this.bitField0_ & 1) != 0;
    }

    public BundledQuery getBundledQuery() {
        return this.bundledQuery_ == null ? BundledQuery.getDefaultInstance() : this.bundledQuery_;
    }

    /* access modifiers changed from: private */
    public void setBundledQuery(BundledQuery value) {
        value.getClass();
        this.bundledQuery_ = value;
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void mergeBundledQuery(BundledQuery value) {
        value.getClass();
        if (this.bundledQuery_ == null || this.bundledQuery_ == BundledQuery.getDefaultInstance()) {
            this.bundledQuery_ = value;
        } else {
            this.bundledQuery_ = (BundledQuery) ((BundledQuery.Builder) BundledQuery.newBuilder(this.bundledQuery_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void clearBundledQuery() {
        this.bundledQuery_ = null;
        this.bitField0_ &= -2;
    }

    public boolean hasReadTime() {
        return (this.bitField0_ & 2) != 0;
    }

    public Timestamp getReadTime() {
        return this.readTime_ == null ? Timestamp.getDefaultInstance() : this.readTime_;
    }

    /* access modifiers changed from: private */
    public void setReadTime(Timestamp value) {
        value.getClass();
        this.readTime_ = value;
        this.bitField0_ |= 2;
    }

    /* access modifiers changed from: private */
    public void mergeReadTime(Timestamp value) {
        value.getClass();
        if (this.readTime_ == null || this.readTime_ == Timestamp.getDefaultInstance()) {
            this.readTime_ = value;
        } else {
            this.readTime_ = (Timestamp) ((Timestamp.Builder) Timestamp.newBuilder(this.readTime_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 2;
    }

    /* access modifiers changed from: private */
    public void clearReadTime() {
        this.readTime_ = null;
        this.bitField0_ &= -3;
    }

    public static NamedQuery parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (NamedQuery) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static NamedQuery parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (NamedQuery) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static NamedQuery parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (NamedQuery) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static NamedQuery parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (NamedQuery) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static NamedQuery parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (NamedQuery) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static NamedQuery parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (NamedQuery) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static NamedQuery parseFrom(InputStream input) throws IOException {
        return (NamedQuery) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static NamedQuery parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (NamedQuery) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static NamedQuery parseDelimitedFrom(InputStream input) throws IOException {
        return (NamedQuery) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static NamedQuery parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (NamedQuery) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static NamedQuery parseFrom(CodedInputStream input) throws IOException {
        return (NamedQuery) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static NamedQuery parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (NamedQuery) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(NamedQuery prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<NamedQuery, Builder> implements NamedQueryOrBuilder {
        private Builder() {
            super(NamedQuery.DEFAULT_INSTANCE);
        }

        public String getName() {
            return ((NamedQuery) this.instance).getName();
        }

        public ByteString getNameBytes() {
            return ((NamedQuery) this.instance).getNameBytes();
        }

        public Builder setName(String value) {
            copyOnWrite();
            ((NamedQuery) this.instance).setName(value);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((NamedQuery) this.instance).clearName();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            copyOnWrite();
            ((NamedQuery) this.instance).setNameBytes(value);
            return this;
        }

        public boolean hasBundledQuery() {
            return ((NamedQuery) this.instance).hasBundledQuery();
        }

        public BundledQuery getBundledQuery() {
            return ((NamedQuery) this.instance).getBundledQuery();
        }

        public Builder setBundledQuery(BundledQuery value) {
            copyOnWrite();
            ((NamedQuery) this.instance).setBundledQuery(value);
            return this;
        }

        public Builder setBundledQuery(BundledQuery.Builder builderForValue) {
            copyOnWrite();
            ((NamedQuery) this.instance).setBundledQuery((BundledQuery) builderForValue.build());
            return this;
        }

        public Builder mergeBundledQuery(BundledQuery value) {
            copyOnWrite();
            ((NamedQuery) this.instance).mergeBundledQuery(value);
            return this;
        }

        public Builder clearBundledQuery() {
            copyOnWrite();
            ((NamedQuery) this.instance).clearBundledQuery();
            return this;
        }

        public boolean hasReadTime() {
            return ((NamedQuery) this.instance).hasReadTime();
        }

        public Timestamp getReadTime() {
            return ((NamedQuery) this.instance).getReadTime();
        }

        public Builder setReadTime(Timestamp value) {
            copyOnWrite();
            ((NamedQuery) this.instance).setReadTime(value);
            return this;
        }

        public Builder setReadTime(Timestamp.Builder builderForValue) {
            copyOnWrite();
            ((NamedQuery) this.instance).setReadTime((Timestamp) builderForValue.build());
            return this;
        }

        public Builder mergeReadTime(Timestamp value) {
            copyOnWrite();
            ((NamedQuery) this.instance).mergeReadTime(value);
            return this;
        }

        public Builder clearReadTime() {
            copyOnWrite();
            ((NamedQuery) this.instance).clearReadTime();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new NamedQuery();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001Ȉ\u0002ဉ\u0000\u0003ဉ\u0001", new Object[]{"bitField0_", "name_", "bundledQuery_", "readTime_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<NamedQuery> parser = PARSER;
                if (parser == null) {
                    synchronized (NamedQuery.class) {
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
        NamedQuery defaultInstance = new NamedQuery();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(NamedQuery.class, defaultInstance);
    }

    public static NamedQuery getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<NamedQuery> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
