package com.google.firestore.v1;

import com.google.firestore.v1.Document;
import com.google.firestore.v1.DocumentMask;
import com.google.firestore.v1.Precondition;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class UpdateDocumentRequest extends GeneratedMessageLite<UpdateDocumentRequest, Builder> implements UpdateDocumentRequestOrBuilder {
    public static final int CURRENT_DOCUMENT_FIELD_NUMBER = 4;
    /* access modifiers changed from: private */
    public static final UpdateDocumentRequest DEFAULT_INSTANCE;
    public static final int DOCUMENT_FIELD_NUMBER = 1;
    public static final int MASK_FIELD_NUMBER = 3;
    private static volatile Parser<UpdateDocumentRequest> PARSER = null;
    public static final int UPDATE_MASK_FIELD_NUMBER = 2;
    private int bitField0_;
    private Precondition currentDocument_;
    private Document document_;
    private DocumentMask mask_;
    private DocumentMask updateMask_;

    private UpdateDocumentRequest() {
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

    public boolean hasUpdateMask() {
        return (this.bitField0_ & 2) != 0;
    }

    public DocumentMask getUpdateMask() {
        return this.updateMask_ == null ? DocumentMask.getDefaultInstance() : this.updateMask_;
    }

    /* access modifiers changed from: private */
    public void setUpdateMask(DocumentMask value) {
        value.getClass();
        this.updateMask_ = value;
        this.bitField0_ |= 2;
    }

    /* access modifiers changed from: private */
    public void mergeUpdateMask(DocumentMask value) {
        value.getClass();
        if (this.updateMask_ == null || this.updateMask_ == DocumentMask.getDefaultInstance()) {
            this.updateMask_ = value;
        } else {
            this.updateMask_ = (DocumentMask) ((DocumentMask.Builder) DocumentMask.newBuilder(this.updateMask_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 2;
    }

    /* access modifiers changed from: private */
    public void clearUpdateMask() {
        this.updateMask_ = null;
        this.bitField0_ &= -3;
    }

    public boolean hasMask() {
        return (this.bitField0_ & 4) != 0;
    }

    public DocumentMask getMask() {
        return this.mask_ == null ? DocumentMask.getDefaultInstance() : this.mask_;
    }

    /* access modifiers changed from: private */
    public void setMask(DocumentMask value) {
        value.getClass();
        this.mask_ = value;
        this.bitField0_ |= 4;
    }

    /* access modifiers changed from: private */
    public void mergeMask(DocumentMask value) {
        value.getClass();
        if (this.mask_ == null || this.mask_ == DocumentMask.getDefaultInstance()) {
            this.mask_ = value;
        } else {
            this.mask_ = (DocumentMask) ((DocumentMask.Builder) DocumentMask.newBuilder(this.mask_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 4;
    }

    /* access modifiers changed from: private */
    public void clearMask() {
        this.mask_ = null;
        this.bitField0_ &= -5;
    }

    public boolean hasCurrentDocument() {
        return (this.bitField0_ & 8) != 0;
    }

    public Precondition getCurrentDocument() {
        return this.currentDocument_ == null ? Precondition.getDefaultInstance() : this.currentDocument_;
    }

    /* access modifiers changed from: private */
    public void setCurrentDocument(Precondition value) {
        value.getClass();
        this.currentDocument_ = value;
        this.bitField0_ |= 8;
    }

    /* access modifiers changed from: private */
    public void mergeCurrentDocument(Precondition value) {
        value.getClass();
        if (this.currentDocument_ == null || this.currentDocument_ == Precondition.getDefaultInstance()) {
            this.currentDocument_ = value;
        } else {
            this.currentDocument_ = (Precondition) ((Precondition.Builder) Precondition.newBuilder(this.currentDocument_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 8;
    }

    /* access modifiers changed from: private */
    public void clearCurrentDocument() {
        this.currentDocument_ = null;
        this.bitField0_ &= -9;
    }

    public static UpdateDocumentRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (UpdateDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static UpdateDocumentRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (UpdateDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static UpdateDocumentRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (UpdateDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static UpdateDocumentRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (UpdateDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static UpdateDocumentRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (UpdateDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static UpdateDocumentRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (UpdateDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static UpdateDocumentRequest parseFrom(InputStream input) throws IOException {
        return (UpdateDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static UpdateDocumentRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (UpdateDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static UpdateDocumentRequest parseDelimitedFrom(InputStream input) throws IOException {
        return (UpdateDocumentRequest) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static UpdateDocumentRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (UpdateDocumentRequest) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static UpdateDocumentRequest parseFrom(CodedInputStream input) throws IOException {
        return (UpdateDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static UpdateDocumentRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (UpdateDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(UpdateDocumentRequest prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<UpdateDocumentRequest, Builder> implements UpdateDocumentRequestOrBuilder {
        private Builder() {
            super(UpdateDocumentRequest.DEFAULT_INSTANCE);
        }

        public boolean hasDocument() {
            return ((UpdateDocumentRequest) this.instance).hasDocument();
        }

        public Document getDocument() {
            return ((UpdateDocumentRequest) this.instance).getDocument();
        }

        public Builder setDocument(Document value) {
            copyOnWrite();
            ((UpdateDocumentRequest) this.instance).setDocument(value);
            return this;
        }

        public Builder setDocument(Document.Builder builderForValue) {
            copyOnWrite();
            ((UpdateDocumentRequest) this.instance).setDocument((Document) builderForValue.build());
            return this;
        }

        public Builder mergeDocument(Document value) {
            copyOnWrite();
            ((UpdateDocumentRequest) this.instance).mergeDocument(value);
            return this;
        }

        public Builder clearDocument() {
            copyOnWrite();
            ((UpdateDocumentRequest) this.instance).clearDocument();
            return this;
        }

        public boolean hasUpdateMask() {
            return ((UpdateDocumentRequest) this.instance).hasUpdateMask();
        }

        public DocumentMask getUpdateMask() {
            return ((UpdateDocumentRequest) this.instance).getUpdateMask();
        }

        public Builder setUpdateMask(DocumentMask value) {
            copyOnWrite();
            ((UpdateDocumentRequest) this.instance).setUpdateMask(value);
            return this;
        }

        public Builder setUpdateMask(DocumentMask.Builder builderForValue) {
            copyOnWrite();
            ((UpdateDocumentRequest) this.instance).setUpdateMask((DocumentMask) builderForValue.build());
            return this;
        }

        public Builder mergeUpdateMask(DocumentMask value) {
            copyOnWrite();
            ((UpdateDocumentRequest) this.instance).mergeUpdateMask(value);
            return this;
        }

        public Builder clearUpdateMask() {
            copyOnWrite();
            ((UpdateDocumentRequest) this.instance).clearUpdateMask();
            return this;
        }

        public boolean hasMask() {
            return ((UpdateDocumentRequest) this.instance).hasMask();
        }

        public DocumentMask getMask() {
            return ((UpdateDocumentRequest) this.instance).getMask();
        }

        public Builder setMask(DocumentMask value) {
            copyOnWrite();
            ((UpdateDocumentRequest) this.instance).setMask(value);
            return this;
        }

        public Builder setMask(DocumentMask.Builder builderForValue) {
            copyOnWrite();
            ((UpdateDocumentRequest) this.instance).setMask((DocumentMask) builderForValue.build());
            return this;
        }

        public Builder mergeMask(DocumentMask value) {
            copyOnWrite();
            ((UpdateDocumentRequest) this.instance).mergeMask(value);
            return this;
        }

        public Builder clearMask() {
            copyOnWrite();
            ((UpdateDocumentRequest) this.instance).clearMask();
            return this;
        }

        public boolean hasCurrentDocument() {
            return ((UpdateDocumentRequest) this.instance).hasCurrentDocument();
        }

        public Precondition getCurrentDocument() {
            return ((UpdateDocumentRequest) this.instance).getCurrentDocument();
        }

        public Builder setCurrentDocument(Precondition value) {
            copyOnWrite();
            ((UpdateDocumentRequest) this.instance).setCurrentDocument(value);
            return this;
        }

        public Builder setCurrentDocument(Precondition.Builder builderForValue) {
            copyOnWrite();
            ((UpdateDocumentRequest) this.instance).setCurrentDocument((Precondition) builderForValue.build());
            return this;
        }

        public Builder mergeCurrentDocument(Precondition value) {
            copyOnWrite();
            ((UpdateDocumentRequest) this.instance).mergeCurrentDocument(value);
            return this;
        }

        public Builder clearCurrentDocument() {
            copyOnWrite();
            ((UpdateDocumentRequest) this.instance).clearCurrentDocument();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new UpdateDocumentRequest();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002\u0004ဉ\u0003", new Object[]{"bitField0_", "document_", "updateMask_", "mask_", "currentDocument_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<UpdateDocumentRequest> parser = PARSER;
                if (parser == null) {
                    synchronized (UpdateDocumentRequest.class) {
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
        UpdateDocumentRequest defaultInstance = new UpdateDocumentRequest();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(UpdateDocumentRequest.class, defaultInstance);
    }

    public static UpdateDocumentRequest getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<UpdateDocumentRequest> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
