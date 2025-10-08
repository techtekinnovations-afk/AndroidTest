package com.google.firestore.v1;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class ListCollectionIdsResponse extends GeneratedMessageLite<ListCollectionIdsResponse, Builder> implements ListCollectionIdsResponseOrBuilder {
    public static final int COLLECTION_IDS_FIELD_NUMBER = 1;
    /* access modifiers changed from: private */
    public static final ListCollectionIdsResponse DEFAULT_INSTANCE;
    public static final int NEXT_PAGE_TOKEN_FIELD_NUMBER = 2;
    private static volatile Parser<ListCollectionIdsResponse> PARSER;
    private Internal.ProtobufList<String> collectionIds_ = GeneratedMessageLite.emptyProtobufList();
    private String nextPageToken_ = "";

    private ListCollectionIdsResponse() {
    }

    public List<String> getCollectionIdsList() {
        return this.collectionIds_;
    }

    public int getCollectionIdsCount() {
        return this.collectionIds_.size();
    }

    public String getCollectionIds(int index) {
        return (String) this.collectionIds_.get(index);
    }

    public ByteString getCollectionIdsBytes(int index) {
        return ByteString.copyFromUtf8((String) this.collectionIds_.get(index));
    }

    private void ensureCollectionIdsIsMutable() {
        Internal.ProtobufList<String> tmp = this.collectionIds_;
        if (!tmp.isModifiable()) {
            this.collectionIds_ = GeneratedMessageLite.mutableCopy(tmp);
        }
    }

    /* access modifiers changed from: private */
    public void setCollectionIds(int index, String value) {
        Class<?> cls = value.getClass();
        ensureCollectionIdsIsMutable();
        this.collectionIds_.set(index, value);
    }

    /* access modifiers changed from: private */
    public void addCollectionIds(String value) {
        Class<?> cls = value.getClass();
        ensureCollectionIdsIsMutable();
        this.collectionIds_.add(value);
    }

    /* access modifiers changed from: private */
    public void addAllCollectionIds(Iterable<String> values) {
        ensureCollectionIdsIsMutable();
        AbstractMessageLite.addAll(values, this.collectionIds_);
    }

    /* access modifiers changed from: private */
    public void clearCollectionIds() {
        this.collectionIds_ = GeneratedMessageLite.emptyProtobufList();
    }

    /* access modifiers changed from: private */
    public void addCollectionIdsBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        ensureCollectionIdsIsMutable();
        this.collectionIds_.add(value.toStringUtf8());
    }

    public String getNextPageToken() {
        return this.nextPageToken_;
    }

    public ByteString getNextPageTokenBytes() {
        return ByteString.copyFromUtf8(this.nextPageToken_);
    }

    /* access modifiers changed from: private */
    public void setNextPageToken(String value) {
        Class<?> cls = value.getClass();
        this.nextPageToken_ = value;
    }

    /* access modifiers changed from: private */
    public void clearNextPageToken() {
        this.nextPageToken_ = getDefaultInstance().getNextPageToken();
    }

    /* access modifiers changed from: private */
    public void setNextPageTokenBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.nextPageToken_ = value.toStringUtf8();
    }

    public static ListCollectionIdsResponse parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (ListCollectionIdsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static ListCollectionIdsResponse parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (ListCollectionIdsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static ListCollectionIdsResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (ListCollectionIdsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static ListCollectionIdsResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (ListCollectionIdsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static ListCollectionIdsResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (ListCollectionIdsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static ListCollectionIdsResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (ListCollectionIdsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static ListCollectionIdsResponse parseFrom(InputStream input) throws IOException {
        return (ListCollectionIdsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static ListCollectionIdsResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (ListCollectionIdsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static ListCollectionIdsResponse parseDelimitedFrom(InputStream input) throws IOException {
        return (ListCollectionIdsResponse) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static ListCollectionIdsResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (ListCollectionIdsResponse) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static ListCollectionIdsResponse parseFrom(CodedInputStream input) throws IOException {
        return (ListCollectionIdsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static ListCollectionIdsResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (ListCollectionIdsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(ListCollectionIdsResponse prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<ListCollectionIdsResponse, Builder> implements ListCollectionIdsResponseOrBuilder {
        private Builder() {
            super(ListCollectionIdsResponse.DEFAULT_INSTANCE);
        }

        public List<String> getCollectionIdsList() {
            return Collections.unmodifiableList(((ListCollectionIdsResponse) this.instance).getCollectionIdsList());
        }

        public int getCollectionIdsCount() {
            return ((ListCollectionIdsResponse) this.instance).getCollectionIdsCount();
        }

        public String getCollectionIds(int index) {
            return ((ListCollectionIdsResponse) this.instance).getCollectionIds(index);
        }

        public ByteString getCollectionIdsBytes(int index) {
            return ((ListCollectionIdsResponse) this.instance).getCollectionIdsBytes(index);
        }

        public Builder setCollectionIds(int index, String value) {
            copyOnWrite();
            ((ListCollectionIdsResponse) this.instance).setCollectionIds(index, value);
            return this;
        }

        public Builder addCollectionIds(String value) {
            copyOnWrite();
            ((ListCollectionIdsResponse) this.instance).addCollectionIds(value);
            return this;
        }

        public Builder addAllCollectionIds(Iterable<String> values) {
            copyOnWrite();
            ((ListCollectionIdsResponse) this.instance).addAllCollectionIds(values);
            return this;
        }

        public Builder clearCollectionIds() {
            copyOnWrite();
            ((ListCollectionIdsResponse) this.instance).clearCollectionIds();
            return this;
        }

        public Builder addCollectionIdsBytes(ByteString value) {
            copyOnWrite();
            ((ListCollectionIdsResponse) this.instance).addCollectionIdsBytes(value);
            return this;
        }

        public String getNextPageToken() {
            return ((ListCollectionIdsResponse) this.instance).getNextPageToken();
        }

        public ByteString getNextPageTokenBytes() {
            return ((ListCollectionIdsResponse) this.instance).getNextPageTokenBytes();
        }

        public Builder setNextPageToken(String value) {
            copyOnWrite();
            ((ListCollectionIdsResponse) this.instance).setNextPageToken(value);
            return this;
        }

        public Builder clearNextPageToken() {
            copyOnWrite();
            ((ListCollectionIdsResponse) this.instance).clearNextPageToken();
            return this;
        }

        public Builder setNextPageTokenBytes(ByteString value) {
            copyOnWrite();
            ((ListCollectionIdsResponse) this.instance).setNextPageTokenBytes(value);
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new ListCollectionIdsResponse();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0001\u0000\u0001Ț\u0002Ȉ", new Object[]{"collectionIds_", "nextPageToken_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<ListCollectionIdsResponse> parser = PARSER;
                if (parser == null) {
                    synchronized (ListCollectionIdsResponse.class) {
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
        ListCollectionIdsResponse defaultInstance = new ListCollectionIdsResponse();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(ListCollectionIdsResponse.class, defaultInstance);
    }

    public static ListCollectionIdsResponse getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<ListCollectionIdsResponse> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
