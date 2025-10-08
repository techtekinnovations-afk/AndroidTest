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

public final class DocumentChange extends GeneratedMessageLite<DocumentChange, Builder> implements DocumentChangeOrBuilder {
    /* access modifiers changed from: private */
    public static final DocumentChange DEFAULT_INSTANCE;
    public static final int DOCUMENT_FIELD_NUMBER = 1;
    private static volatile Parser<DocumentChange> PARSER = null;
    public static final int REMOVED_TARGET_IDS_FIELD_NUMBER = 6;
    public static final int TARGET_IDS_FIELD_NUMBER = 5;
    private int bitField0_;
    private Document document_;
    private int removedTargetIdsMemoizedSerializedSize = -1;
    private Internal.IntList removedTargetIds_ = emptyIntList();
    private int targetIdsMemoizedSerializedSize = -1;
    private Internal.IntList targetIds_ = emptyIntList();

    private DocumentChange() {
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

    public List<Integer> getTargetIdsList() {
        return this.targetIds_;
    }

    public int getTargetIdsCount() {
        return this.targetIds_.size();
    }

    public int getTargetIds(int index) {
        return this.targetIds_.getInt(index);
    }

    private void ensureTargetIdsIsMutable() {
        Internal.IntList tmp = this.targetIds_;
        if (!tmp.isModifiable()) {
            this.targetIds_ = GeneratedMessageLite.mutableCopy(tmp);
        }
    }

    /* access modifiers changed from: private */
    public void setTargetIds(int index, int value) {
        ensureTargetIdsIsMutable();
        this.targetIds_.setInt(index, value);
    }

    /* access modifiers changed from: private */
    public void addTargetIds(int value) {
        ensureTargetIdsIsMutable();
        this.targetIds_.addInt(value);
    }

    /* access modifiers changed from: private */
    public void addAllTargetIds(Iterable<? extends Integer> values) {
        ensureTargetIdsIsMutable();
        AbstractMessageLite.addAll(values, this.targetIds_);
    }

    /* access modifiers changed from: private */
    public void clearTargetIds() {
        this.targetIds_ = emptyIntList();
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

    public static DocumentChange parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (DocumentChange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static DocumentChange parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (DocumentChange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static DocumentChange parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (DocumentChange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static DocumentChange parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (DocumentChange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static DocumentChange parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (DocumentChange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static DocumentChange parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (DocumentChange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static DocumentChange parseFrom(InputStream input) throws IOException {
        return (DocumentChange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static DocumentChange parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (DocumentChange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static DocumentChange parseDelimitedFrom(InputStream input) throws IOException {
        return (DocumentChange) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static DocumentChange parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (DocumentChange) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static DocumentChange parseFrom(CodedInputStream input) throws IOException {
        return (DocumentChange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static DocumentChange parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (DocumentChange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(DocumentChange prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<DocumentChange, Builder> implements DocumentChangeOrBuilder {
        private Builder() {
            super(DocumentChange.DEFAULT_INSTANCE);
        }

        public boolean hasDocument() {
            return ((DocumentChange) this.instance).hasDocument();
        }

        public Document getDocument() {
            return ((DocumentChange) this.instance).getDocument();
        }

        public Builder setDocument(Document value) {
            copyOnWrite();
            ((DocumentChange) this.instance).setDocument(value);
            return this;
        }

        public Builder setDocument(Document.Builder builderForValue) {
            copyOnWrite();
            ((DocumentChange) this.instance).setDocument((Document) builderForValue.build());
            return this;
        }

        public Builder mergeDocument(Document value) {
            copyOnWrite();
            ((DocumentChange) this.instance).mergeDocument(value);
            return this;
        }

        public Builder clearDocument() {
            copyOnWrite();
            ((DocumentChange) this.instance).clearDocument();
            return this;
        }

        public List<Integer> getTargetIdsList() {
            return Collections.unmodifiableList(((DocumentChange) this.instance).getTargetIdsList());
        }

        public int getTargetIdsCount() {
            return ((DocumentChange) this.instance).getTargetIdsCount();
        }

        public int getTargetIds(int index) {
            return ((DocumentChange) this.instance).getTargetIds(index);
        }

        public Builder setTargetIds(int index, int value) {
            copyOnWrite();
            ((DocumentChange) this.instance).setTargetIds(index, value);
            return this;
        }

        public Builder addTargetIds(int value) {
            copyOnWrite();
            ((DocumentChange) this.instance).addTargetIds(value);
            return this;
        }

        public Builder addAllTargetIds(Iterable<? extends Integer> values) {
            copyOnWrite();
            ((DocumentChange) this.instance).addAllTargetIds(values);
            return this;
        }

        public Builder clearTargetIds() {
            copyOnWrite();
            ((DocumentChange) this.instance).clearTargetIds();
            return this;
        }

        public List<Integer> getRemovedTargetIdsList() {
            return Collections.unmodifiableList(((DocumentChange) this.instance).getRemovedTargetIdsList());
        }

        public int getRemovedTargetIdsCount() {
            return ((DocumentChange) this.instance).getRemovedTargetIdsCount();
        }

        public int getRemovedTargetIds(int index) {
            return ((DocumentChange) this.instance).getRemovedTargetIds(index);
        }

        public Builder setRemovedTargetIds(int index, int value) {
            copyOnWrite();
            ((DocumentChange) this.instance).setRemovedTargetIds(index, value);
            return this;
        }

        public Builder addRemovedTargetIds(int value) {
            copyOnWrite();
            ((DocumentChange) this.instance).addRemovedTargetIds(value);
            return this;
        }

        public Builder addAllRemovedTargetIds(Iterable<? extends Integer> values) {
            copyOnWrite();
            ((DocumentChange) this.instance).addAllRemovedTargetIds(values);
            return this;
        }

        public Builder clearRemovedTargetIds() {
            copyOnWrite();
            ((DocumentChange) this.instance).clearRemovedTargetIds();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new DocumentChange();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0003\u0000\u0001\u0001\u0006\u0003\u0000\u0002\u0000\u0001ဉ\u0000\u0005'\u0006'", new Object[]{"bitField0_", "document_", "targetIds_", "removedTargetIds_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<DocumentChange> parser = PARSER;
                if (parser == null) {
                    synchronized (DocumentChange.class) {
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
        DocumentChange defaultInstance = new DocumentChange();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(DocumentChange.class, defaultInstance);
    }

    public static DocumentChange getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<DocumentChange> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
