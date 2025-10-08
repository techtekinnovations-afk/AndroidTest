package com.google.firestore.v1;

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

public final class DocumentRemove extends GeneratedMessageLite<DocumentRemove, Builder> implements DocumentRemoveOrBuilder {
    /* access modifiers changed from: private */
    public static final DocumentRemove DEFAULT_INSTANCE;
    public static final int DOCUMENT_FIELD_NUMBER = 1;
    private static volatile Parser<DocumentRemove> PARSER = null;
    public static final int READ_TIME_FIELD_NUMBER = 4;
    public static final int REMOVED_TARGET_IDS_FIELD_NUMBER = 2;
    private int bitField0_;
    private String document_ = "";
    private Timestamp readTime_;
    private int removedTargetIdsMemoizedSerializedSize = -1;
    private Internal.IntList removedTargetIds_ = emptyIntList();

    private DocumentRemove() {
    }

    public String getDocument() {
        return this.document_;
    }

    public ByteString getDocumentBytes() {
        return ByteString.copyFromUtf8(this.document_);
    }

    /* access modifiers changed from: private */
    public void setDocument(String value) {
        Class<?> cls = value.getClass();
        this.document_ = value;
    }

    /* access modifiers changed from: private */
    public void clearDocument() {
        this.document_ = getDefaultInstance().getDocument();
    }

    /* access modifiers changed from: private */
    public void setDocumentBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.document_ = value.toStringUtf8();
    }

    public List<Integer> getRemovedTargetIdsList() {
        return this.removedTargetIds_;
    }

    public int getRemovedTargetIdsCount() {
        return this.removedTargetIds_.size();
    }

    public int getRemovedTargetIds(int index) {
        return this.removedTargetIds_.getInt(index);
    }

    private void ensureRemovedTargetIdsIsMutable() {
        Internal.IntList tmp = this.removedTargetIds_;
        if (!tmp.isModifiable()) {
            this.removedTargetIds_ = GeneratedMessageLite.mutableCopy(tmp);
        }
    }

    /* access modifiers changed from: private */
    public void setRemovedTargetIds(int index, int value) {
        ensureRemovedTargetIdsIsMutable();
        this.removedTargetIds_.setInt(index, value);
    }

    /* access modifiers changed from: private */
    public void addRemovedTargetIds(int value) {
        ensureRemovedTargetIdsIsMutable();
        this.removedTargetIds_.addInt(value);
    }

    /* access modifiers changed from: private */
    public void addAllRemovedTargetIds(Iterable<? extends Integer> values) {
        ensureRemovedTargetIdsIsMutable();
        AbstractMessageLite.addAll(values, this.removedTargetIds_);
    }

    /* access modifiers changed from: private */
    public void clearRemovedTargetIds() {
        this.removedTargetIds_ = emptyIntList();
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

    public static DocumentRemove parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (DocumentRemove) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static DocumentRemove parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (DocumentRemove) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static DocumentRemove parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (DocumentRemove) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static DocumentRemove parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (DocumentRemove) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static DocumentRemove parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (DocumentRemove) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static DocumentRemove parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (DocumentRemove) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static DocumentRemove parseFrom(InputStream input) throws IOException {
        return (DocumentRemove) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static DocumentRemove parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (DocumentRemove) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static DocumentRemove parseDelimitedFrom(InputStream input) throws IOException {
        return (DocumentRemove) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static DocumentRemove parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (DocumentRemove) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static DocumentRemove parseFrom(CodedInputStream input) throws IOException {
        return (DocumentRemove) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static DocumentRemove parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (DocumentRemove) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(DocumentRemove prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<DocumentRemove, Builder> implements DocumentRemoveOrBuilder {
        private Builder() {
            super(DocumentRemove.DEFAULT_INSTANCE);
        }

        public String getDocument() {
            return ((DocumentRemove) this.instance).getDocument();
        }

        public ByteString getDocumentBytes() {
            return ((DocumentRemove) this.instance).getDocumentBytes();
        }

        public Builder setDocument(String value) {
            copyOnWrite();
            ((DocumentRemove) this.instance).setDocument(value);
            return this;
        }

        public Builder clearDocument() {
            copyOnWrite();
            ((DocumentRemove) this.instance).clearDocument();
            return this;
        }

        public Builder setDocumentBytes(ByteString value) {
            copyOnWrite();
            ((DocumentRemove) this.instance).setDocumentBytes(value);
            return this;
        }

        public List<Integer> getRemovedTargetIdsList() {
            return Collections.unmodifiableList(((DocumentRemove) this.instance).getRemovedTargetIdsList());
        }

        public int getRemovedTargetIdsCount() {
            return ((DocumentRemove) this.instance).getRemovedTargetIdsCount();
        }

        public int getRemovedTargetIds(int index) {
            return ((DocumentRemove) this.instance).getRemovedTargetIds(index);
        }

        public Builder setRemovedTargetIds(int index, int value) {
            copyOnWrite();
            ((DocumentRemove) this.instance).setRemovedTargetIds(index, value);
            return this;
        }

        public Builder addRemovedTargetIds(int value) {
            copyOnWrite();
            ((DocumentRemove) this.instance).addRemovedTargetIds(value);
            return this;
        }

        public Builder addAllRemovedTargetIds(Iterable<? extends Integer> values) {
            copyOnWrite();
            ((DocumentRemove) this.instance).addAllRemovedTargetIds(values);
            return this;
        }

        public Builder clearRemovedTargetIds() {
            copyOnWrite();
            ((DocumentRemove) this.instance).clearRemovedTargetIds();
            return this;
        }

        public boolean hasReadTime() {
            return ((DocumentRemove) this.instance).hasReadTime();
        }

        public Timestamp getReadTime() {
            return ((DocumentRemove) this.instance).getReadTime();
        }

        public Builder setReadTime(Timestamp value) {
            copyOnWrite();
            ((DocumentRemove) this.instance).setReadTime(value);
            return this;
        }

        public Builder setReadTime(Timestamp.Builder builderForValue) {
            copyOnWrite();
            ((DocumentRemove) this.instance).setReadTime((Timestamp) builderForValue.build());
            return this;
        }

        public Builder mergeReadTime(Timestamp value) {
            copyOnWrite();
            ((DocumentRemove) this.instance).mergeReadTime(value);
            return this;
        }

        public Builder clearReadTime() {
            copyOnWrite();
            ((DocumentRemove) this.instance).clearReadTime();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new DocumentRemove();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0003\u0000\u0001\u0001\u0004\u0003\u0000\u0001\u0000\u0001Ȉ\u0002'\u0004ဉ\u0000", new Object[]{"bitField0_", "document_", "removedTargetIds_", "readTime_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<DocumentRemove> parser = PARSER;
                if (parser == null) {
                    synchronized (DocumentRemove.class) {
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
        DocumentRemove defaultInstance = new DocumentRemove();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(DocumentRemove.class, defaultInstance);
    }

    public static DocumentRemove getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<DocumentRemove> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
