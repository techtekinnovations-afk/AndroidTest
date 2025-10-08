package com.google.firestore.v1;

import com.google.firestore.v1.Document;
import com.google.firestore.v1.DocumentMask;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class CreateDocumentRequest extends GeneratedMessageLite<CreateDocumentRequest, Builder> implements CreateDocumentRequestOrBuilder {
    public static final int COLLECTION_ID_FIELD_NUMBER = 2;
    /* access modifiers changed from: private */
    public static final CreateDocumentRequest DEFAULT_INSTANCE;
    public static final int DOCUMENT_FIELD_NUMBER = 4;
    public static final int DOCUMENT_ID_FIELD_NUMBER = 3;
    public static final int MASK_FIELD_NUMBER = 5;
    public static final int PARENT_FIELD_NUMBER = 1;
    private static volatile Parser<CreateDocumentRequest> PARSER;
    private int bitField0_;
    private String collectionId_ = "";
    private String documentId_ = "";
    private Document document_;
    private DocumentMask mask_;
    private String parent_ = "";

    private CreateDocumentRequest() {
    }

    public String getParent() {
        return this.parent_;
    }

    public ByteString getParentBytes() {
        return ByteString.copyFromUtf8(this.parent_);
    }

    /* access modifiers changed from: private */
    public void setParent(String value) {
        Class<?> cls = value.getClass();
        this.parent_ = value;
    }

    /* access modifiers changed from: private */
    public void clearParent() {
        this.parent_ = getDefaultInstance().getParent();
    }

    /* access modifiers changed from: private */
    public void setParentBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.parent_ = value.toStringUtf8();
    }

    public String getCollectionId() {
        return this.collectionId_;
    }

    public ByteString getCollectionIdBytes() {
        return ByteString.copyFromUtf8(this.collectionId_);
    }

    /* access modifiers changed from: private */
    public void setCollectionId(String value) {
        Class<?> cls = value.getClass();
        this.collectionId_ = value;
    }

    /* access modifiers changed from: private */
    public void clearCollectionId() {
        this.collectionId_ = getDefaultInstance().getCollectionId();
    }

    /* access modifiers changed from: private */
    public void setCollectionIdBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.collectionId_ = value.toStringUtf8();
    }

    public String getDocumentId() {
        return this.documentId_;
    }

    public ByteString getDocumentIdBytes() {
        return ByteString.copyFromUtf8(this.documentId_);
    }

    /* access modifiers changed from: private */
    public void setDocumentId(String value) {
        Class<?> cls = value.getClass();
        this.documentId_ = value;
    }

    /* access modifiers changed from: private */
    public void clearDocumentId() {
        this.documentId_ = getDefaultInstance().getDocumentId();
    }

    /* access modifiers changed from: private */
    public void setDocumentIdBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.documentId_ = value.toStringUtf8();
    }

    public boolean hasDocument() {
        return (this.bitField0_ & 1) != 0;
    }

    public Document getDocument() {
        return this.document_ == null ? Document.getDefaultInstance() : this.document_;
    }

    /* access modifiers changed from: private */
    public void setDocument(Document value) {
        value.getClass();
        this.document_ = value;
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void mergeDocument(Document value) {
        value.getClass();
        if (this.document_ == null || this.document_ == Document.getDefaultInstance()) {
            this.document_ = value;
        } else {
            this.document_ = (Document) ((Document.Builder) Document.newBuilder(this.document_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void clearDocument() {
        this.document_ = null;
        this.bitField0_ &= -2;
    }

    public boolean hasMask() {
        return (this.bitField0_ & 2) != 0;
    }

    public DocumentMask getMask() {
        return this.mask_ == null ? DocumentMask.getDefaultInstance() : this.mask_;
    }

    /* access modifiers changed from: private */
    public void setMask(DocumentMask value) {
        value.getClass();
        this.mask_ = value;
        this.bitField0_ |= 2;
    }

    /* access modifiers changed from: private */
    public void mergeMask(DocumentMask value) {
        value.getClass();
        if (this.mask_ == null || this.mask_ == DocumentMask.getDefaultInstance()) {
            this.mask_ = value;
        } else {
            this.mask_ = (DocumentMask) ((DocumentMask.Builder) DocumentMask.newBuilder(this.mask_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 2;
    }

    /* access modifiers changed from: private */
    public void clearMask() {
        this.mask_ = null;
        this.bitField0_ &= -3;
    }

    public static CreateDocumentRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (CreateDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static CreateDocumentRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (CreateDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static CreateDocumentRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (CreateDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static CreateDocumentRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (CreateDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static CreateDocumentRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (CreateDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static CreateDocumentRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (CreateDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static CreateDocumentRequest parseFrom(InputStream input) throws IOException {
        return (CreateDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static CreateDocumentRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (CreateDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static CreateDocumentRequest parseDelimitedFrom(InputStream input) throws IOException {
        return (CreateDocumentRequest) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static CreateDocumentRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (CreateDocumentRequest) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static CreateDocumentRequest parseFrom(CodedInputStream input) throws IOException {
        return (CreateDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static CreateDocumentRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (CreateDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(CreateDocumentRequest prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<CreateDocumentRequest, Builder> implements CreateDocumentRequestOrBuilder {
        private Builder() {
            super(CreateDocumentRequest.DEFAULT_INSTANCE);
        }

        public String getParent() {
            return ((CreateDocumentRequest) this.instance).getParent();
        }

        public ByteString getParentBytes() {
            return ((CreateDocumentRequest) this.instance).getParentBytes();
        }

        public Builder setParent(String value) {
            copyOnWrite();
            ((CreateDocumentRequest) this.instance).setParent(value);
            return this;
        }

        public Builder clearParent() {
            copyOnWrite();
            ((CreateDocumentRequest) this.instance).clearParent();
            return this;
        }

        public Builder setParentBytes(ByteString value) {
            copyOnWrite();
            ((CreateDocumentRequest) this.instance).setParentBytes(value);
            return this;
        }

        public String getCollectionId() {
            return ((CreateDocumentRequest) this.instance).getCollectionId();
        }

        public ByteString getCollectionIdBytes() {
            return ((CreateDocumentRequest) this.instance).getCollectionIdBytes();
        }

        public Builder setCollectionId(String value) {
            copyOnWrite();
            ((CreateDocumentRequest) this.instance).setCollectionId(value);
            return this;
        }

        public Builder clearCollectionId() {
            copyOnWrite();
            ((CreateDocumentRequest) this.instance).clearCollectionId();
            return this;
        }

        public Builder setCollectionIdBytes(ByteString value) {
            copyOnWrite();
            ((CreateDocumentRequest) this.instance).setCollectionIdBytes(value);
            return this;
        }

        public String getDocumentId() {
            return ((CreateDocumentRequest) this.instance).getDocumentId();
        }

        public ByteString getDocumentIdBytes() {
            return ((CreateDocumentRequest) this.instance).getDocumentIdBytes();
        }

        public Builder setDocumentId(String value) {
            copyOnWrite();
            ((CreateDocumentRequest) this.instance).setDocumentId(value);
            return this;
        }

        public Builder clearDocumentId() {
            copyOnWrite();
            ((CreateDocumentRequest) this.instance).clearDocumentId();
            return this;
        }

        public Builder setDocumentIdBytes(ByteString value) {
            copyOnWrite();
            ((CreateDocumentRequest) this.instance).setDocumentIdBytes(value);
            return this;
        }

        public boolean hasDocument() {
            return ((CreateDocumentRequest) this.instance).hasDocument();
        }

        public Document getDocument() {
            return ((CreateDocumentRequest) this.instance).getDocument();
        }

        public Builder setDocument(Document value) {
            copyOnWrite();
            ((CreateDocumentRequest) this.instance).setDocument(value);
            return this;
        }

        public Builder setDocument(Document.Builder builderForValue) {
            copyOnWrite();
            ((CreateDocumentRequest) this.instance).setDocument((Document) builderForValue.build());
            return this;
        }

        public Builder mergeDocument(Document value) {
            copyOnWrite();
            ((CreateDocumentRequest) this.instance).mergeDocument(value);
            return this;
        }

        public Builder clearDocument() {
            copyOnWrite();
            ((CreateDocumentRequest) this.instance).clearDocument();
            return this;
        }

        public boolean hasMask() {
            return ((CreateDocumentRequest) this.instance).hasMask();
        }

        public DocumentMask getMask() {
            return ((CreateDocumentRequest) this.instance).getMask();
        }

        public Builder setMask(DocumentMask value) {
            copyOnWrite();
            ((CreateDocumentRequest) this.instance).setMask(value);
            return this;
        }

        public Builder setMask(DocumentMask.Builder builderForValue) {
            copyOnWrite();
            ((CreateDocumentRequest) this.instance).setMask((DocumentMask) builderForValue.build());
            return this;
        }

        public Builder mergeMask(DocumentMask value) {
            copyOnWrite();
            ((CreateDocumentRequest) this.instance).mergeMask(value);
            return this;
        }

        public Builder clearMask() {
            copyOnWrite();
            ((CreateDocumentRequest) this.instance).clearMask();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new CreateDocumentRequest();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ\u0003Ȉ\u0004ဉ\u0000\u0005ဉ\u0001", new Object[]{"bitField0_", "parent_", "collectionId_", "documentId_", "document_", "mask_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<CreateDocumentRequest> parser = PARSER;
                if (parser == null) {
                    synchronized (CreateDocumentRequest.class) {
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
        CreateDocumentRequest defaultInstance = new CreateDocumentRequest();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(CreateDocumentRequest.class, defaultInstance);
    }

    public static CreateDocumentRequest getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<CreateDocumentRequest> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
