package com.google.firestore.v1;

import com.google.firestore.v1.WriteResult;
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

public final class CommitResponse extends GeneratedMessageLite<CommitResponse, Builder> implements CommitResponseOrBuilder {
    public static final int COMMIT_TIME_FIELD_NUMBER = 2;
    /* access modifiers changed from: private */
    public static final CommitResponse DEFAULT_INSTANCE;
    private static volatile Parser<CommitResponse> PARSER = null;
    public static final int WRITE_RESULTS_FIELD_NUMBER = 1;
    private int bitField0_;
    private Timestamp commitTime_;
    private Internal.ProtobufList<WriteResult> writeResults_ = emptyProtobufList();

    private CommitResponse() {
    }

    public List<WriteResult> getWriteResultsList() {
        return this.writeResults_;
    }

    public List<? extends WriteResultOrBuilder> getWriteResultsOrBuilderList() {
        return this.writeResults_;
    }

    public int getWriteResultsCount() {
        return this.writeResults_.size();
    }

    public WriteResult getWriteResults(int index) {
        return (WriteResult) this.writeResults_.get(index);
    }

    public WriteResultOrBuilder getWriteResultsOrBuilder(int index) {
        return (WriteResultOrBuilder) this.writeResults_.get(index);
    }

    private void ensureWriteResultsIsMutable() {
        Internal.ProtobufList<WriteResult> tmp = this.writeResults_;
        if (!tmp.isModifiable()) {
            this.writeResults_ = GeneratedMessageLite.mutableCopy(tmp);
        }
    }

    /* access modifiers changed from: private */
    public void setWriteResults(int index, WriteResult value) {
        value.getClass();
        ensureWriteResultsIsMutable();
        this.writeResults_.set(index, value);
    }

    /* access modifiers changed from: private */
    public void addWriteResults(WriteResult value) {
        value.getClass();
        ensureWriteResultsIsMutable();
        this.writeResults_.add(value);
    }

    /* access modifiers changed from: private */
    public void addWriteResults(int index, WriteResult value) {
        value.getClass();
        ensureWriteResultsIsMutable();
        this.writeResults_.add(index, value);
    }

    /* access modifiers changed from: private */
    public void addAllWriteResults(Iterable<? extends WriteResult> values) {
        ensureWriteResultsIsMutable();
        AbstractMessageLite.addAll(values, this.writeResults_);
    }

    /* access modifiers changed from: private */
    public void clearWriteResults() {
        this.writeResults_ = emptyProtobufList();
    }

    /* access modifiers changed from: private */
    public void removeWriteResults(int index) {
        ensureWriteResultsIsMutable();
        this.writeResults_.remove(index);
    }

    public boolean hasCommitTime() {
        return (this.bitField0_ & 1) != 0;
    }

    public Timestamp getCommitTime() {
        return this.commitTime_ == null ? Timestamp.getDefaultInstance() : this.commitTime_;
    }

    /* access modifiers changed from: private */
    public void setCommitTime(Timestamp value) {
        value.getClass();
        this.commitTime_ = value;
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void mergeCommitTime(Timestamp value) {
        value.getClass();
        if (this.commitTime_ == null || this.commitTime_ == Timestamp.getDefaultInstance()) {
            this.commitTime_ = value;
        } else {
            this.commitTime_ = (Timestamp) ((Timestamp.Builder) Timestamp.newBuilder(this.commitTime_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void clearCommitTime() {
        this.commitTime_ = null;
        this.bitField0_ &= -2;
    }

    public static CommitResponse parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (CommitResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static CommitResponse parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (CommitResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static CommitResponse parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (CommitResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static CommitResponse parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (CommitResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static CommitResponse parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (CommitResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static CommitResponse parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (CommitResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static CommitResponse parseFrom(InputStream input) throws IOException {
        return (CommitResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static CommitResponse parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (CommitResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static CommitResponse parseDelimitedFrom(InputStream input) throws IOException {
        return (CommitResponse) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static CommitResponse parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (CommitResponse) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static CommitResponse parseFrom(CodedInputStream input) throws IOException {
        return (CommitResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static CommitResponse parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (CommitResponse) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(CommitResponse prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<CommitResponse, Builder> implements CommitResponseOrBuilder {
        private Builder() {
            super(CommitResponse.DEFAULT_INSTANCE);
        }

        public List<WriteResult> getWriteResultsList() {
            return Collections.unmodifiableList(((CommitResponse) this.instance).getWriteResultsList());
        }

        public int getWriteResultsCount() {
            return ((CommitResponse) this.instance).getWriteResultsCount();
        }

        public WriteResult getWriteResults(int index) {
            return ((CommitResponse) this.instance).getWriteResults(index);
        }

        public Builder setWriteResults(int index, WriteResult value) {
            copyOnWrite();
            ((CommitResponse) this.instance).setWriteResults(index, value);
            return this;
        }

        public Builder setWriteResults(int index, WriteResult.Builder builderForValue) {
            copyOnWrite();
            ((CommitResponse) this.instance).setWriteResults(index, (WriteResult) builderForValue.build());
            return this;
        }

        public Builder addWriteResults(WriteResult value) {
            copyOnWrite();
            ((CommitResponse) this.instance).addWriteResults(value);
            return this;
        }

        public Builder addWriteResults(int index, WriteResult value) {
            copyOnWrite();
            ((CommitResponse) this.instance).addWriteResults(index, value);
            return this;
        }

        public Builder addWriteResults(WriteResult.Builder builderForValue) {
            copyOnWrite();
            ((CommitResponse) this.instance).addWriteResults((WriteResult) builderForValue.build());
            return this;
        }

        public Builder addWriteResults(int index, WriteResult.Builder builderForValue) {
            copyOnWrite();
            ((CommitResponse) this.instance).addWriteResults(index, (WriteResult) builderForValue.build());
            return this;
        }

        public Builder addAllWriteResults(Iterable<? extends WriteResult> values) {
            copyOnWrite();
            ((CommitResponse) this.instance).addAllWriteResults(values);
            return this;
        }

        public Builder clearWriteResults() {
            copyOnWrite();
            ((CommitResponse) this.instance).clearWriteResults();
            return this;
        }

        public Builder removeWriteResults(int index) {
            copyOnWrite();
            ((CommitResponse) this.instance).removeWriteResults(index);
            return this;
        }

        public boolean hasCommitTime() {
            return ((CommitResponse) this.instance).hasCommitTime();
        }

        public Timestamp getCommitTime() {
            return ((CommitResponse) this.instance).getCommitTime();
        }

        public Builder setCommitTime(Timestamp value) {
            copyOnWrite();
            ((CommitResponse) this.instance).setCommitTime(value);
            return this;
        }

        public Builder setCommitTime(Timestamp.Builder builderForValue) {
            copyOnWrite();
            ((CommitResponse) this.instance).setCommitTime((Timestamp) builderForValue.build());
            return this;
        }

        public Builder mergeCommitTime(Timestamp value) {
            copyOnWrite();
            ((CommitResponse) this.instance).mergeCommitTime(value);
            return this;
        }

        public Builder clearCommitTime() {
            copyOnWrite();
            ((CommitResponse) this.instance).clearCommitTime();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new CommitResponse();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u001b\u0002ဉ\u0000", new Object[]{"bitField0_", "writeResults_", WriteResult.class, "commitTime_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<CommitResponse> parser = PARSER;
                if (parser == null) {
                    synchronized (CommitResponse.class) {
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
        CommitResponse defaultInstance = new CommitResponse();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(CommitResponse.class, defaultInstance);
    }

    public static CommitResponse getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<CommitResponse> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
