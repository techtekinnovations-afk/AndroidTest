package com.google.firebase.firestore.proto;

import com.google.firestore.v1.Write;
import com.google.firestore.v1.WriteOrBuilder;
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

public final class WriteBatch extends GeneratedMessageLite<WriteBatch, Builder> implements WriteBatchOrBuilder {
    public static final int BASE_WRITES_FIELD_NUMBER = 4;
    public static final int BATCH_ID_FIELD_NUMBER = 1;
    /* access modifiers changed from: private */
    public static final WriteBatch DEFAULT_INSTANCE;
    public static final int LOCAL_WRITE_TIME_FIELD_NUMBER = 3;
    private static volatile Parser<WriteBatch> PARSER = null;
    public static final int WRITES_FIELD_NUMBER = 2;
    private Internal.ProtobufList<Write> baseWrites_ = emptyProtobufList();
    private int batchId_;
    private int bitField0_;
    private Timestamp localWriteTime_;
    private Internal.ProtobufList<Write> writes_ = emptyProtobufList();

    private WriteBatch() {
    }

    public int getBatchId() {
        return this.batchId_;
    }

    /* access modifiers changed from: private */
    public void setBatchId(int value) {
        this.batchId_ = value;
    }

    /* access modifiers changed from: private */
    public void clearBatchId() {
        this.batchId_ = 0;
    }

    public List<Write> getWritesList() {
        return this.writes_;
    }

    public List<? extends WriteOrBuilder> getWritesOrBuilderList() {
        return this.writes_;
    }

    public int getWritesCount() {
        return this.writes_.size();
    }

    public Write getWrites(int index) {
        return (Write) this.writes_.get(index);
    }

    public WriteOrBuilder getWritesOrBuilder(int index) {
        return (WriteOrBuilder) this.writes_.get(index);
    }

    private void ensureWritesIsMutable() {
        Internal.ProtobufList<Write> tmp = this.writes_;
        if (!tmp.isModifiable()) {
            this.writes_ = GeneratedMessageLite.mutableCopy(tmp);
        }
    }

    /* access modifiers changed from: private */
    public void setWrites(int index, Write value) {
        value.getClass();
        ensureWritesIsMutable();
        this.writes_.set(index, value);
    }

    /* access modifiers changed from: private */
    public void addWrites(Write value) {
        value.getClass();
        ensureWritesIsMutable();
        this.writes_.add(value);
    }

    /* access modifiers changed from: private */
    public void addWrites(int index, Write value) {
        value.getClass();
        ensureWritesIsMutable();
        this.writes_.add(index, value);
    }

    /* access modifiers changed from: private */
    public void addAllWrites(Iterable<? extends Write> values) {
        ensureWritesIsMutable();
        AbstractMessageLite.addAll(values, this.writes_);
    }

    /* access modifiers changed from: private */
    public void clearWrites() {
        this.writes_ = emptyProtobufList();
    }

    /* access modifiers changed from: private */
    public void removeWrites(int index) {
        ensureWritesIsMutable();
        this.writes_.remove(index);
    }

    public boolean hasLocalWriteTime() {
        return (this.bitField0_ & 1) != 0;
    }

    public Timestamp getLocalWriteTime() {
        return this.localWriteTime_ == null ? Timestamp.getDefaultInstance() : this.localWriteTime_;
    }

    /* access modifiers changed from: private */
    public void setLocalWriteTime(Timestamp value) {
        value.getClass();
        this.localWriteTime_ = value;
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void mergeLocalWriteTime(Timestamp value) {
        value.getClass();
        if (this.localWriteTime_ == null || this.localWriteTime_ == Timestamp.getDefaultInstance()) {
            this.localWriteTime_ = value;
        } else {
            this.localWriteTime_ = (Timestamp) ((Timestamp.Builder) Timestamp.newBuilder(this.localWriteTime_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void clearLocalWriteTime() {
        this.localWriteTime_ = null;
        this.bitField0_ &= -2;
    }

    public List<Write> getBaseWritesList() {
        return this.baseWrites_;
    }

    public List<? extends WriteOrBuilder> getBaseWritesOrBuilderList() {
        return this.baseWrites_;
    }

    public int getBaseWritesCount() {
        return this.baseWrites_.size();
    }

    public Write getBaseWrites(int index) {
        return (Write) this.baseWrites_.get(index);
    }

    public WriteOrBuilder getBaseWritesOrBuilder(int index) {
        return (WriteOrBuilder) this.baseWrites_.get(index);
    }

    private void ensureBaseWritesIsMutable() {
        Internal.ProtobufList<Write> tmp = this.baseWrites_;
        if (!tmp.isModifiable()) {
            this.baseWrites_ = GeneratedMessageLite.mutableCopy(tmp);
        }
    }

    /* access modifiers changed from: private */
    public void setBaseWrites(int index, Write value) {
        value.getClass();
        ensureBaseWritesIsMutable();
        this.baseWrites_.set(index, value);
    }

    /* access modifiers changed from: private */
    public void addBaseWrites(Write value) {
        value.getClass();
        ensureBaseWritesIsMutable();
        this.baseWrites_.add(value);
    }

    /* access modifiers changed from: private */
    public void addBaseWrites(int index, Write value) {
        value.getClass();
        ensureBaseWritesIsMutable();
        this.baseWrites_.add(index, value);
    }

    /* access modifiers changed from: private */
    public void addAllBaseWrites(Iterable<? extends Write> values) {
        ensureBaseWritesIsMutable();
        AbstractMessageLite.addAll(values, this.baseWrites_);
    }

    /* access modifiers changed from: private */
    public void clearBaseWrites() {
        this.baseWrites_ = emptyProtobufList();
    }

    /* access modifiers changed from: private */
    public void removeBaseWrites(int index) {
        ensureBaseWritesIsMutable();
        this.baseWrites_.remove(index);
    }

    public static WriteBatch parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (WriteBatch) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static WriteBatch parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (WriteBatch) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static WriteBatch parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (WriteBatch) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static WriteBatch parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (WriteBatch) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static WriteBatch parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (WriteBatch) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static WriteBatch parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (WriteBatch) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static WriteBatch parseFrom(InputStream input) throws IOException {
        return (WriteBatch) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static WriteBatch parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (WriteBatch) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static WriteBatch parseDelimitedFrom(InputStream input) throws IOException {
        return (WriteBatch) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static WriteBatch parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (WriteBatch) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static WriteBatch parseFrom(CodedInputStream input) throws IOException {
        return (WriteBatch) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static WriteBatch parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (WriteBatch) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(WriteBatch prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<WriteBatch, Builder> implements WriteBatchOrBuilder {
        private Builder() {
            super(WriteBatch.DEFAULT_INSTANCE);
        }

        public int getBatchId() {
            return ((WriteBatch) this.instance).getBatchId();
        }

        public Builder setBatchId(int value) {
            copyOnWrite();
            ((WriteBatch) this.instance).setBatchId(value);
            return this;
        }

        public Builder clearBatchId() {
            copyOnWrite();
            ((WriteBatch) this.instance).clearBatchId();
            return this;
        }

        public List<Write> getWritesList() {
            return Collections.unmodifiableList(((WriteBatch) this.instance).getWritesList());
        }

        public int getWritesCount() {
            return ((WriteBatch) this.instance).getWritesCount();
        }

        public Write getWrites(int index) {
            return ((WriteBatch) this.instance).getWrites(index);
        }

        public Builder setWrites(int index, Write value) {
            copyOnWrite();
            ((WriteBatch) this.instance).setWrites(index, value);
            return this;
        }

        public Builder setWrites(int index, Write.Builder builderForValue) {
            copyOnWrite();
            ((WriteBatch) this.instance).setWrites(index, (Write) builderForValue.build());
            return this;
        }

        public Builder addWrites(Write value) {
            copyOnWrite();
            ((WriteBatch) this.instance).addWrites(value);
            return this;
        }

        public Builder addWrites(int index, Write value) {
            copyOnWrite();
            ((WriteBatch) this.instance).addWrites(index, value);
            return this;
        }

        public Builder addWrites(Write.Builder builderForValue) {
            copyOnWrite();
            ((WriteBatch) this.instance).addWrites((Write) builderForValue.build());
            return this;
        }

        public Builder addWrites(int index, Write.Builder builderForValue) {
            copyOnWrite();
            ((WriteBatch) this.instance).addWrites(index, (Write) builderForValue.build());
            return this;
        }

        public Builder addAllWrites(Iterable<? extends Write> values) {
            copyOnWrite();
            ((WriteBatch) this.instance).addAllWrites(values);
            return this;
        }

        public Builder clearWrites() {
            copyOnWrite();
            ((WriteBatch) this.instance).clearWrites();
            return this;
        }

        public Builder removeWrites(int index) {
            copyOnWrite();
            ((WriteBatch) this.instance).removeWrites(index);
            return this;
        }

        public boolean hasLocalWriteTime() {
            return ((WriteBatch) this.instance).hasLocalWriteTime();
        }

        public Timestamp getLocalWriteTime() {
            return ((WriteBatch) this.instance).getLocalWriteTime();
        }

        public Builder setLocalWriteTime(Timestamp value) {
            copyOnWrite();
            ((WriteBatch) this.instance).setLocalWriteTime(value);
            return this;
        }

        public Builder setLocalWriteTime(Timestamp.Builder builderForValue) {
            copyOnWrite();
            ((WriteBatch) this.instance).setLocalWriteTime((Timestamp) builderForValue.build());
            return this;
        }

        public Builder mergeLocalWriteTime(Timestamp value) {
            copyOnWrite();
            ((WriteBatch) this.instance).mergeLocalWriteTime(value);
            return this;
        }

        public Builder clearLocalWriteTime() {
            copyOnWrite();
            ((WriteBatch) this.instance).clearLocalWriteTime();
            return this;
        }

        public List<Write> getBaseWritesList() {
            return Collections.unmodifiableList(((WriteBatch) this.instance).getBaseWritesList());
        }

        public int getBaseWritesCount() {
            return ((WriteBatch) this.instance).getBaseWritesCount();
        }

        public Write getBaseWrites(int index) {
            return ((WriteBatch) this.instance).getBaseWrites(index);
        }

        public Builder setBaseWrites(int index, Write value) {
            copyOnWrite();
            ((WriteBatch) this.instance).setBaseWrites(index, value);
            return this;
        }

        public Builder setBaseWrites(int index, Write.Builder builderForValue) {
            copyOnWrite();
            ((WriteBatch) this.instance).setBaseWrites(index, (Write) builderForValue.build());
            return this;
        }

        public Builder addBaseWrites(Write value) {
            copyOnWrite();
            ((WriteBatch) this.instance).addBaseWrites(value);
            return this;
        }

        public Builder addBaseWrites(int index, Write value) {
            copyOnWrite();
            ((WriteBatch) this.instance).addBaseWrites(index, value);
            return this;
        }

        public Builder addBaseWrites(Write.Builder builderForValue) {
            copyOnWrite();
            ((WriteBatch) this.instance).addBaseWrites((Write) builderForValue.build());
            return this;
        }

        public Builder addBaseWrites(int index, Write.Builder builderForValue) {
            copyOnWrite();
            ((WriteBatch) this.instance).addBaseWrites(index, (Write) builderForValue.build());
            return this;
        }

        public Builder addAllBaseWrites(Iterable<? extends Write> values) {
            copyOnWrite();
            ((WriteBatch) this.instance).addAllBaseWrites(values);
            return this;
        }

        public Builder clearBaseWrites() {
            copyOnWrite();
            ((WriteBatch) this.instance).clearBaseWrites();
            return this;
        }

        public Builder removeBaseWrites(int index) {
            copyOnWrite();
            ((WriteBatch) this.instance).removeBaseWrites(index);
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new WriteBatch();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0002\u0000\u0001\u0004\u0002\u001b\u0003ဉ\u0000\u0004\u001b", new Object[]{"bitField0_", "batchId_", "writes_", Write.class, "localWriteTime_", "baseWrites_", Write.class});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<WriteBatch> parser = PARSER;
                if (parser == null) {
                    synchronized (WriteBatch.class) {
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
        WriteBatch defaultInstance = new WriteBatch();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(WriteBatch.class, defaultInstance);
    }

    public static WriteBatch getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<WriteBatch> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
