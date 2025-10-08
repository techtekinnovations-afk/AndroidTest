package com.google.firestore.v1;

import com.google.firestore.v1.Document;
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

public final class ListDocumentsResponse extends GeneratedMessageLite<ListDocumentsResponse, Builder> implements ListDocumentsResponseOrBuilder {
    /* access modifiers changed from: private */
    public static final ListDocumentsResponse DEFAULT_INSTANCE;
    public static final int DOCUMENTS_FIELD_NUMBER = 1;
    public static final int NEXT_PAGE_TOKEN_FIELD_NUMBER = 2;
    private static volatile Parser<ListDocumentsResponse> PARSER;
    private Internal.ProtobufList<Document> documents_ = emptyProtobufList();
    private String nextPageToken_ = "";

    private ListDocumentsResponse() {
    }

    public List<Document> getDocumentsList() {
        return this.documents_;
    }

    public List<? extends DocumentOrBuilder> getDocumentsOrBuilderList() {
        return this.documents_;
    }

    public int getDocumentsCount() {
        return this.documents_.size();
    }

    public Document getDocuments(int index) {
        return (Document) this.documents_.get(index);
    }

    public DocumentOrBuilder getDocumentsOrBuilder(int index) {
        return (DocumentOrBuilder) this.documents_.get(index);
    }

    private void ensureDocumentsIsMutable() {
        Internal.ProtobufList<Document> tmp = this.documents_;
        if (!tmp.isModifiable()) {
            this.documents_ = GeneratedMessageLite.mutableCopy(tmp);
        }
    }

    /* access modifiers changed from: private */
    public void setDocuments(int index, Document value) {
        value.getClass();
        ensureDocumentsIsMutable();
        this.documents_.set(index, value);
    }

    /* access modifiers changed from: private */
    public void addDocuments(Document value) {
        value.getClass();
        ensureDocumentsIsMutable();
        this.documents_.add(value);
    }

    /* access modifiers changed from: private */
    public void addDocuments(int index, Document value) {
        value.getClass();
        ensureDocumentsIsMutable();
        this.documents_.add(index, value);
    }

    /* access modifiers changed from: private */
    public void addAllDocuments(Iterable<? extends Document> values) {
        ensureDocumentsIsMutable();
        AbstractMessageLite.addAll(values, this.documents_);
    }

    /* access modifiers changed from: private */
    public void clearDocuments() {
        this.documents_ = emptyProtobufList();
    }

    /* access modifiers changed from: private */
    public void removeDocuments(int index) {
        ensureDocumentsIsMutable();
        this.documents_.remove(index);
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

    public static ListDocumentsResponse parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (ListDocumentsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static ListDocumentsResponse parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (ListDocumentsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static ListDocumentsResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (ListDocumentsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static ListDocumentsResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (ListDocumentsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static ListDocumentsResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (ListDocumentsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static ListDocumentsResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (ListDocumentsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static ListDocumentsResponse parseFrom(InputStream input) throws IOException {
        return (ListDocumentsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static ListDocumentsResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (ListDocumentsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static ListDocumentsResponse parseDelimitedFrom(InputStream input) throws IOException {
        return (ListDocumentsResponse) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static ListDocumentsResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (ListDocumentsResponse) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static ListDocumentsResponse parseFrom(CodedInputStream input) throws IOException {
        return (ListDocumentsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static ListDocumentsResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (ListDocumentsResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(ListDocumentsResponse prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<ListDocumentsResponse, Builder> implements ListDocumentsResponseOrBuilder {
        private Builder() {
            super(ListDocumentsResponse.DEFAULT_INSTANCE);
        }

        public List<Document> getDocumentsList() {
            return Collections.unmodifiableList(((ListDocumentsResponse) this.instance).getDocumentsList());
        }

        public int getDocumentsCount() {
            return ((ListDocumentsResponse) this.instance).getDocumentsCount();
        }

        public Document getDocuments(int index) {
            return ((ListDocumentsResponse) this.instance).getDocuments(index);
        }

        public Builder setDocuments(int index, Document value) {
            copyOnWrite();
            ((ListDocumentsResponse) this.instance).setDocuments(index, value);
            return this;
        }

        public Builder setDocuments(int index, Document.Builder builderForValue) {
            copyOnWrite();
            ((ListDocumentsResponse) this.instance).setDocuments(index, (Document) builderForValue.build());
            return this;
        }

        public Builder addDocuments(Document value) {
            copyOnWrite();
            ((ListDocumentsResponse) this.instance).addDocuments(value);
            return this;
        }

        public Builder addDocuments(int index, Document value) {
            copyOnWrite();
            ((ListDocumentsResponse) this.instance).addDocuments(index, value);
            return this;
        }

        public Builder addDocuments(Document.Builder builderForValue) {
            copyOnWrite();
            ((ListDocumentsResponse) this.instance).addDocuments((Document) builderForValue.build());
            return this;
        }

        public Builder addDocuments(int index, Document.Builder builderForValue) {
            copyOnWrite();
            ((ListDocumentsResponse) this.instance).addDocuments(index, (Document) builderForValue.build());
            return this;
        }

        public Builder addAllDocuments(Iterable<? extends Document> values) {
            copyOnWrite();
            ((ListDocumentsResponse) this.instance).addAllDocuments(values);
            return this;
        }

        public Builder clearDocuments() {
            copyOnWrite();
            ((ListDocumentsResponse) this.instance).clearDocuments();
            return this;
        }

        public Builder removeDocuments(int index) {
            copyOnWrite();
            ((ListDocumentsResponse) this.instance).removeDocuments(index);
            return this;
        }

        public String getNextPageToken() {
            return ((ListDocumentsResponse) this.instance).getNextPageToken();
        }

        public ByteString getNextPageTokenBytes() {
            return ((ListDocumentsResponse) this.instance).getNextPageTokenBytes();
        }

        public Builder setNextPageToken(String value) {
            copyOnWrite();
            ((ListDocumentsResponse) this.instance).setNextPageToken(value);
            return this;
        }

        public Builder clearNextPageToken() {
            copyOnWrite();
            ((ListDocumentsResponse) this.instance).clearNextPageToken();
            return this;
        }

        public Builder setNextPageTokenBytes(ByteString value) {
            copyOnWrite();
            ((ListDocumentsResponse) this.instance).setNextPageTokenBytes(value);
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new ListDocumentsResponse();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u001b\u0002Ȉ", new Object[]{"documents_", Document.class, "nextPageToken_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<ListDocumentsResponse> parser = PARSER;
                if (parser == null) {
                    synchronized (ListDocumentsResponse.class) {
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
        ListDocumentsResponse defaultInstance = new ListDocumentsResponse();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(ListDocumentsResponse.class, defaultInstance);
    }

    public static ListDocumentsResponse getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<ListDocumentsResponse> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
