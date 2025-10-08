package com.google.firestore.bundle;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.google.protobuf.Timestamp;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class BundledDocumentMetadata extends GeneratedMessageLite<BundledDocumentMetadata, Builder> implements BundledDocumentMetadataOrBuilder {
    /* access modifiers changed from: private */
    public static final BundledDocumentMetadata DEFAULT_INSTANCE;
    public static final int EXISTS_FIELD_NUMBER = 3;
    public static final int NAME_FIELD_NUMBER = 1;
    private static volatile Parser<BundledDocumentMetadata> PARSER = null;
    public static final int QUERIES_FIELD_NUMBER = 4;
    public static final int READ_TIME_FIELD_NUMBER = 2;
    private int bitField0_;
    private boolean exists_;
    private String name_ = "";
    private Internal.ProtobufList<String> queries_ = GeneratedMessageLite.emptyProtobufList();
    private Timestamp readTime_;

    private BundledDocumentMetadata() {
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

    public boolean hasReadTime() {
        return (this.bitField0_ & 1) != 0;
    }

    public Timestamp getReadTime() {
        return this.readTime_ == null ? Timestamp.getDefaultInstance() : this.readTime_;
    }

    /* access modifiers changed from: private */
    public void setReadTime(Timestamp value) {
        value.getClass();
        this.readTime_ = value;
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void mergeReadTime(Timestamp value) {
        value.getClass();
        if (this.readTime_ == null || this.readTime_ == Timestamp.getDefaultInstance()) {
            this.readTime_ = value;
        } else {
            this.readTime_ = (Timestamp) ((Timestamp.Builder) Timestamp.newBuilder(this.readTime_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void clearReadTime() {
        this.readTime_ = null;
        this.bitField0_ &= -2;
    }

    public boolean getExists() {
        return this.exists_;
    }

    /* access modifiers changed from: private */
    public void setExists(boolean value) {
        this.exists_ = value;
    }

    /* access modifiers changed from: private */
    public void clearExists() {
        this.exists_ = false;
    }

    public List<String> getQueriesList() {
        return this.queries_;
    }

    public int getQueriesCount() {
        return this.queries_.size();
    }

    public String getQueries(int index) {
        return (String) this.queries_.get(index);
    }

    public ByteString getQueriesBytes(int index) {
        return ByteString.copyFromUtf8((String) this.queries_.get(index));
    }

    private void ensureQueriesIsMutable() {
        Internal.ProtobufList<String> tmp = this.queries_;
        if (!tmp.isModifiable()) {
            this.queries_ = GeneratedMessageLite.mutableCopy(tmp);
        }
    }

    /* access modifiers changed from: private */
    public void setQueries(int index, String value) {
        Class<?> cls = value.getClass();
        ensureQueriesIsMutable();
        this.queries_.set(index, value);
    }

    /* access modifiers changed from: private */
    public void addQueries(String value) {
        Class<?> cls = value.getClass();
        ensureQueriesIsMutable();
        this.queries_.add(value);
    }

    /* access modifiers changed from: private */
    public void addAllQueries(Iterable<String> values) {
        ensureQueriesIsMutable();
        AbstractMessageLite.addAll(values, this.queries_);
    }

    /* access modifiers changed from: private */
    public void clearQueries() {
        this.queries_ = GeneratedMessageLite.emptyProtobufList();
    }

    /* access modifiers changed from: private */
    public void addQueriesBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        ensureQueriesIsMutable();
        this.queries_.add(value.toStringUtf8());
    }

    public static BundledDocumentMetadata parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (BundledDocumentMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static BundledDocumentMetadata parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (BundledDocumentMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static BundledDocumentMetadata parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (BundledDocumentMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static BundledDocumentMetadata parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (BundledDocumentMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static BundledDocumentMetadata parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (BundledDocumentMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static BundledDocumentMetadata parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (BundledDocumentMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static BundledDocumentMetadata parseFrom(InputStream input) throws IOException {
        return (BundledDocumentMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static BundledDocumentMetadata parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (BundledDocumentMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static BundledDocumentMetadata parseDelimitedFrom(InputStream input) throws IOException {
        return (BundledDocumentMetadata) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static BundledDocumentMetadata parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (BundledDocumentMetadata) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static BundledDocumentMetadata parseFrom(CodedInputStream input) throws IOException {
        return (BundledDocumentMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static BundledDocumentMetadata parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (BundledDocumentMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(BundledDocumentMetadata prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<BundledDocumentMetadata, Builder> implements BundledDocumentMetadataOrBuilder {
        private Builder() {
            super(BundledDocumentMetadata.DEFAULT_INSTANCE);
        }

        public String getName() {
            return ((BundledDocumentMetadata) this.instance).getName();
        }

        public ByteString getNameBytes() {
            return ((BundledDocumentMetadata) this.instance).getNameBytes();
        }

        public Builder setName(String value) {
            copyOnWrite();
            ((BundledDocumentMetadata) this.instance).setName(value);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((BundledDocumentMetadata) this.instance).clearName();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            copyOnWrite();
            ((BundledDocumentMetadata) this.instance).setNameBytes(value);
            return this;
        }

        public boolean hasReadTime() {
            return ((BundledDocumentMetadata) this.instance).hasReadTime();
        }

        public Timestamp getReadTime() {
            return ((BundledDocumentMetadata) this.instance).getReadTime();
        }

        public Builder setReadTime(Timestamp value) {
            copyOnWrite();
            ((BundledDocumentMetadata) this.instance).setReadTime(value);
            return this;
        }

        public Builder setReadTime(Timestamp.Builder builderForValue) {
            copyOnWrite();
            ((BundledDocumentMetadata) this.instance).setReadTime((Timestamp) builderForValue.build());
            return this;
        }

        public Builder mergeReadTime(Timestamp value) {
            copyOnWrite();
            ((BundledDocumentMetadata) this.instance).mergeReadTime(value);
            return this;
        }

        public Builder clearReadTime() {
            copyOnWrite();
            ((BundledDocumentMetadata) this.instance).clearReadTime();
            return this;
        }

        public boolean getExists() {
            return ((BundledDocumentMetadata) this.instance).getExists();
        }

        public Builder setExists(boolean value) {
            copyOnWrite();
            ((BundledDocumentMetadata) this.instance).setExists(value);
            return this;
        }

        public Builder clearExists() {
            copyOnWrite();
            ((BundledDocumentMetadata) this.instance).clearExists();
            return this;
        }

        public List<String> getQueriesList() {
            return Collections.unmodifiableList(((BundledDocumentMetadata) this.instance).getQueriesList());
        }

        public int getQueriesCount() {
            return ((BundledDocumentMetadata) this.instance).getQueriesCount();
        }

        public String getQueries(int index) {
            return ((BundledDocumentMetadata) this.instance).getQueries(index);
        }

        public ByteString getQueriesBytes(int index) {
            return ((BundledDocumentMetadata) this.instance).getQueriesBytes(index);
        }

        public Builder setQueries(int index, String value) {
            copyOnWrite();
            ((BundledDocumentMetadata) this.instance).setQueries(index, value);
            return this;
        }

        public Builder addQueries(String value) {
            copyOnWrite();
            ((BundledDocumentMetadata) this.instance).addQueries(value);
            return this;
        }

        public Builder addAllQueries(Iterable<String> values) {
            copyOnWrite();
            ((BundledDocumentMetadata) this.instance).addAllQueries(values);
            return this;
        }

        public Builder clearQueries() {
            copyOnWrite();
            ((BundledDocumentMetadata) this.instance).clearQueries();
            return this;
        }

        public Builder addQueriesBytes(ByteString value) {
            copyOnWrite();
            ((BundledDocumentMetadata) this.instance).addQueriesBytes(value);
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new BundledDocumentMetadata();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0001\u0000\u0001Ȉ\u0002ဉ\u0000\u0003\u0007\u0004Ț", new Object[]{"bitField0_", "name_", "readTime_", "exists_", "queries_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<BundledDocumentMetadata> parser = PARSER;
                if (parser == null) {
                    synchronized (BundledDocumentMetadata.class) {
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
        BundledDocumentMetadata defaultInstance = new BundledDocumentMetadata();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(BundledDocumentMetadata.class, defaultInstance);
    }

    public static BundledDocumentMetadata getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<BundledDocumentMetadata> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
