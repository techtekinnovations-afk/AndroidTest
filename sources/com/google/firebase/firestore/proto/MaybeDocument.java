package com.google.firebase.firestore.proto;

import com.google.firebase.firestore.proto.NoDocument;
import com.google.firebase.firestore.proto.UnknownDocument;
import com.google.firestore.v1.Document;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class MaybeDocument extends GeneratedMessageLite<MaybeDocument, Builder> implements MaybeDocumentOrBuilder {
    /* access modifiers changed from: private */
    public static final MaybeDocument DEFAULT_INSTANCE;
    public static final int DOCUMENT_FIELD_NUMBER = 2;
    public static final int HAS_COMMITTED_MUTATIONS_FIELD_NUMBER = 4;
    public static final int NO_DOCUMENT_FIELD_NUMBER = 1;
    private static volatile Parser<MaybeDocument> PARSER = null;
    public static final int UNKNOWN_DOCUMENT_FIELD_NUMBER = 3;
    private int documentTypeCase_ = 0;
    private Object documentType_;
    private boolean hasCommittedMutations_;

    private MaybeDocument() {
    }

    public enum DocumentTypeCase {
        NO_DOCUMENT(1),
        DOCUMENT(2),
        UNKNOWN_DOCUMENT(3),
        DOCUMENTTYPE_NOT_SET(0);
        
        private final int value;

        private DocumentTypeCase(int value2) {
            this.value = value2;
        }

        @Deprecated
        public static DocumentTypeCase valueOf(int value2) {
            return forNumber(value2);
        }

        public static DocumentTypeCase forNumber(int value2) {
            switch (value2) {
                case 0:
                    return DOCUMENTTYPE_NOT_SET;
                case 1:
                    return NO_DOCUMENT;
                case 2:
                    return DOCUMENT;
                case 3:
                    return UNKNOWN_DOCUMENT;
                default:
                    return null;
            }
        }

        public int getNumber() {
            return this.value;
        }
    }

    public DocumentTypeCase getDocumentTypeCase() {
        return DocumentTypeCase.forNumber(this.documentTypeCase_);
    }

    /* access modifiers changed from: private */
    public void clearDocumentType() {
        this.documentTypeCase_ = 0;
        this.documentType_ = null;
    }

    public boolean hasNoDocument() {
        return this.documentTypeCase_ == 1;
    }

    public NoDocument getNoDocument() {
        if (this.documentTypeCase_ == 1) {
            return (NoDocument) this.documentType_;
        }
        return NoDocument.getDefaultInstance();
    }

    /* access modifiers changed from: private */
    public void setNoDocument(NoDocument value) {
        value.getClass();
        this.documentType_ = value;
        this.documentTypeCase_ = 1;
    }

    /* access modifiers changed from: private */
    public void mergeNoDocument(NoDocument value) {
        value.getClass();
        if (this.documentTypeCase_ != 1 || this.documentType_ == NoDocument.getDefaultInstance()) {
            this.documentType_ = value;
        } else {
            this.documentType_ = ((NoDocument.Builder) NoDocument.newBuilder((NoDocument) this.documentType_).mergeFrom(value)).buildPartial();
        }
        this.documentTypeCase_ = 1;
    }

    /* access modifiers changed from: private */
    public void clearNoDocument() {
        if (this.documentTypeCase_ == 1) {
            this.documentTypeCase_ = 0;
            this.documentType_ = null;
        }
    }

    public boolean hasDocument() {
        return this.documentTypeCase_ == 2;
    }

    public Document getDocument() {
        if (this.documentTypeCase_ == 2) {
            return (Document) this.documentType_;
        }
        return Document.getDefaultInstance();
    }

    /* access modifiers changed from: private */
    public void setDocument(Document value) {
        value.getClass();
        this.documentType_ = value;
        this.documentTypeCase_ = 2;
    }

    /* access modifiers changed from: private */
    public void mergeDocument(Document value) {
        value.getClass();
        if (this.documentTypeCase_ != 2 || this.documentType_ == Document.getDefaultInstance()) {
            this.documentType_ = value;
        } else {
            this.documentType_ = ((Document.Builder) Document.newBuilder((Document) this.documentType_).mergeFrom(value)).buildPartial();
        }
        this.documentTypeCase_ = 2;
    }

    /* access modifiers changed from: private */
    public void clearDocument() {
        if (this.documentTypeCase_ == 2) {
            this.documentTypeCase_ = 0;
            this.documentType_ = null;
        }
    }

    public boolean hasUnknownDocument() {
        return this.documentTypeCase_ == 3;
    }

    public UnknownDocument getUnknownDocument() {
        if (this.documentTypeCase_ == 3) {
            return (UnknownDocument) this.documentType_;
        }
        return UnknownDocument.getDefaultInstance();
    }

    /* access modifiers changed from: private */
    public void setUnknownDocument(UnknownDocument value) {
        value.getClass();
        this.documentType_ = value;
        this.documentTypeCase_ = 3;
    }

    /* access modifiers changed from: private */
    public void mergeUnknownDocument(UnknownDocument value) {
        value.getClass();
        if (this.documentTypeCase_ != 3 || this.documentType_ == UnknownDocument.getDefaultInstance()) {
            this.documentType_ = value;
        } else {
            this.documentType_ = ((UnknownDocument.Builder) UnknownDocument.newBuilder((UnknownDocument) this.documentType_).mergeFrom(value)).buildPartial();
        }
        this.documentTypeCase_ = 3;
    }

    /* access modifiers changed from: private */
    public void clearUnknownDocument() {
        if (this.documentTypeCase_ == 3) {
            this.documentTypeCase_ = 0;
            this.documentType_ = null;
        }
    }

    public boolean getHasCommittedMutations() {
        return this.hasCommittedMutations_;
    }

    /* access modifiers changed from: private */
    public void setHasCommittedMutations(boolean value) {
        this.hasCommittedMutations_ = value;
    }

    /* access modifiers changed from: private */
    public void clearHasCommittedMutations() {
        this.hasCommittedMutations_ = false;
    }

    public static MaybeDocument parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (MaybeDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static MaybeDocument parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (MaybeDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static MaybeDocument parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (MaybeDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static MaybeDocument parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (MaybeDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static MaybeDocument parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (MaybeDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static MaybeDocument parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (MaybeDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static MaybeDocument parseFrom(InputStream input) throws IOException {
        return (MaybeDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static MaybeDocument parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (MaybeDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static MaybeDocument parseDelimitedFrom(InputStream input) throws IOException {
        return (MaybeDocument) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static MaybeDocument parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (MaybeDocument) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static MaybeDocument parseFrom(CodedInputStream input) throws IOException {
        return (MaybeDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static MaybeDocument parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (MaybeDocument) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(MaybeDocument prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<MaybeDocument, Builder> implements MaybeDocumentOrBuilder {
        private Builder() {
            super(MaybeDocument.DEFAULT_INSTANCE);
        }

        public DocumentTypeCase getDocumentTypeCase() {
            return ((MaybeDocument) this.instance).getDocumentTypeCase();
        }

        public Builder clearDocumentType() {
            copyOnWrite();
            ((MaybeDocument) this.instance).clearDocumentType();
            return this;
        }

        public boolean hasNoDocument() {
            return ((MaybeDocument) this.instance).hasNoDocument();
        }

        public NoDocument getNoDocument() {
            return ((MaybeDocument) this.instance).getNoDocument();
        }

        public Builder setNoDocument(NoDocument value) {
            copyOnWrite();
            ((MaybeDocument) this.instance).setNoDocument(value);
            return this;
        }

        public Builder setNoDocument(NoDocument.Builder builderForValue) {
            copyOnWrite();
            ((MaybeDocument) this.instance).setNoDocument((NoDocument) builderForValue.build());
            return this;
        }

        public Builder mergeNoDocument(NoDocument value) {
            copyOnWrite();
            ((MaybeDocument) this.instance).mergeNoDocument(value);
            return this;
        }

        public Builder clearNoDocument() {
            copyOnWrite();
            ((MaybeDocument) this.instance).clearNoDocument();
            return this;
        }

        public boolean hasDocument() {
            return ((MaybeDocument) this.instance).hasDocument();
        }

        public Document getDocument() {
            return ((MaybeDocument) this.instance).getDocument();
        }

        public Builder setDocument(Document value) {
            copyOnWrite();
            ((MaybeDocument) this.instance).setDocument(value);
            return this;
        }

        public Builder setDocument(Document.Builder builderForValue) {
            copyOnWrite();
            ((MaybeDocument) this.instance).setDocument((Document) builderForValue.build());
            return this;
        }

        public Builder mergeDocument(Document value) {
            copyOnWrite();
            ((MaybeDocument) this.instance).mergeDocument(value);
            return this;
        }

        public Builder clearDocument() {
            copyOnWrite();
            ((MaybeDocument) this.instance).clearDocument();
            return this;
        }

        public boolean hasUnknownDocument() {
            return ((MaybeDocument) this.instance).hasUnknownDocument();
        }

        public UnknownDocument getUnknownDocument() {
            return ((MaybeDocument) this.instance).getUnknownDocument();
        }

        public Builder setUnknownDocument(UnknownDocument value) {
            copyOnWrite();
            ((MaybeDocument) this.instance).setUnknownDocument(value);
            return this;
        }

        public Builder setUnknownDocument(UnknownDocument.Builder builderForValue) {
            copyOnWrite();
            ((MaybeDocument) this.instance).setUnknownDocument((UnknownDocument) builderForValue.build());
            return this;
        }

        public Builder mergeUnknownDocument(UnknownDocument value) {
            copyOnWrite();
            ((MaybeDocument) this.instance).mergeUnknownDocument(value);
            return this;
        }

        public Builder clearUnknownDocument() {
            copyOnWrite();
            ((MaybeDocument) this.instance).clearUnknownDocument();
            return this;
        }

        public boolean getHasCommittedMutations() {
            return ((MaybeDocument) this.instance).getHasCommittedMutations();
        }

        public Builder setHasCommittedMutations(boolean value) {
            copyOnWrite();
            ((MaybeDocument) this.instance).setHasCommittedMutations(value);
            return this;
        }

        public Builder clearHasCommittedMutations() {
            copyOnWrite();
            ((MaybeDocument) this.instance).clearHasCommittedMutations();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new MaybeDocument();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0004\u0001\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001<\u0000\u0002<\u0000\u0003<\u0000\u0004\u0007", new Object[]{"documentType_", "documentTypeCase_", NoDocument.class, Document.class, UnknownDocument.class, "hasCommittedMutations_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<MaybeDocument> parser = PARSER;
                if (parser == null) {
                    synchronized (MaybeDocument.class) {
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
        MaybeDocument defaultInstance = new MaybeDocument();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(MaybeDocument.class, defaultInstance);
    }

    public static MaybeDocument getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<MaybeDocument> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
