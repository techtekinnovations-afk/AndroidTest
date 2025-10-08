package com.google.firestore.v1;

import com.google.firestore.v1.Value;
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

public final class WriteResult extends GeneratedMessageLite<WriteResult, Builder> implements WriteResultOrBuilder {
    /* access modifiers changed from: private */
    public static final WriteResult DEFAULT_INSTANCE;
    private static volatile Parser<WriteResult> PARSER = null;
    public static final int TRANSFORM_RESULTS_FIELD_NUMBER = 2;
    public static final int UPDATE_TIME_FIELD_NUMBER = 1;
    private int bitField0_;
    private Internal.ProtobufList<Value> transformResults_ = emptyProtobufList();
    private Timestamp updateTime_;

    private WriteResult() {
    }

    public boolean hasUpdateTime() {
        return (this.bitField0_ & 1) != 0;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime_ == null ? Timestamp.getDefaultInstance() : this.updateTime_;
    }

    /* access modifiers changed from: private */
    public void setUpdateTime(Timestamp value) {
        value.getClass();
        this.updateTime_ = value;
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void mergeUpdateTime(Timestamp value) {
        value.getClass();
        if (this.updateTime_ == null || this.updateTime_ == Timestamp.getDefaultInstance()) {
            this.updateTime_ = value;
        } else {
            this.updateTime_ = (Timestamp) ((Timestamp.Builder) Timestamp.newBuilder(this.updateTime_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void clearUpdateTime() {
        this.updateTime_ = null;
        this.bitField0_ &= -2;
    }

    public List<Value> getTransformResultsList() {
        return this.transformResults_;
    }

    public List<? extends ValueOrBuilder> getTransformResultsOrBuilderList() {
        return this.transformResults_;
    }

    public int getTransformResultsCount() {
        return this.transformResults_.size();
    }

    public Value getTransformResults(int index) {
        return (Value) this.transformResults_.get(index);
    }

    public ValueOrBuilder getTransformResultsOrBuilder(int index) {
        return (ValueOrBuilder) this.transformResults_.get(index);
    }

    private void ensureTransformResultsIsMutable() {
        Internal.ProtobufList<Value> tmp = this.transformResults_;
        if (!tmp.isModifiable()) {
            this.transformResults_ = GeneratedMessageLite.mutableCopy(tmp);
        }
    }

    /* access modifiers changed from: private */
    public void setTransformResults(int index, Value value) {
        value.getClass();
        ensureTransformResultsIsMutable();
        this.transformResults_.set(index, value);
    }

    /* access modifiers changed from: private */
    public void addTransformResults(Value value) {
        value.getClass();
        ensureTransformResultsIsMutable();
        this.transformResults_.add(value);
    }

    /* access modifiers changed from: private */
    public void addTransformResults(int index, Value value) {
        value.getClass();
        ensureTransformResultsIsMutable();
        this.transformResults_.add(index, value);
    }

    /* access modifiers changed from: private */
    public void addAllTransformResults(Iterable<? extends Value> values) {
        ensureTransformResultsIsMutable();
        AbstractMessageLite.addAll(values, this.transformResults_);
    }

    /* access modifiers changed from: private */
    public void clearTransformResults() {
        this.transformResults_ = emptyProtobufList();
    }

    /* access modifiers changed from: private */
    public void removeTransformResults(int index) {
        ensureTransformResultsIsMutable();
        this.transformResults_.remove(index);
    }

    public static WriteResult parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (WriteResult) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static WriteResult parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (WriteResult) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static WriteResult parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (WriteResult) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static WriteResult parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (WriteResult) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static WriteResult parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (WriteResult) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static WriteResult parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (WriteResult) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static WriteResult parseFrom(InputStream input) throws IOException {
        return (WriteResult) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static WriteResult parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (WriteResult) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static WriteResult parseDelimitedFrom(InputStream input) throws IOException {
        return (WriteResult) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static WriteResult parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (WriteResult) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static WriteResult parseFrom(CodedInputStream input) throws IOException {
        return (WriteResult) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static WriteResult parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (WriteResult) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(WriteResult prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<WriteResult, Builder> implements WriteResultOrBuilder {
        private Builder() {
            super(WriteResult.DEFAULT_INSTANCE);
        }

        public boolean hasUpdateTime() {
            return ((WriteResult) this.instance).hasUpdateTime();
        }

        public Timestamp getUpdateTime() {
            return ((WriteResult) this.instance).getUpdateTime();
        }

        public Builder setUpdateTime(Timestamp value) {
            copyOnWrite();
            ((WriteResult) this.instance).setUpdateTime(value);
            return this;
        }

        public Builder setUpdateTime(Timestamp.Builder builderForValue) {
            copyOnWrite();
            ((WriteResult) this.instance).setUpdateTime((Timestamp) builderForValue.build());
            return this;
        }

        public Builder mergeUpdateTime(Timestamp value) {
            copyOnWrite();
            ((WriteResult) this.instance).mergeUpdateTime(value);
            return this;
        }

        public Builder clearUpdateTime() {
            copyOnWrite();
            ((WriteResult) this.instance).clearUpdateTime();
            return this;
        }

        public List<Value> getTransformResultsList() {
            return Collections.unmodifiableList(((WriteResult) this.instance).getTransformResultsList());
        }

        public int getTransformResultsCount() {
            return ((WriteResult) this.instance).getTransformResultsCount();
        }

        public Value getTransformResults(int index) {
            return ((WriteResult) this.instance).getTransformResults(index);
        }

        public Builder setTransformResults(int index, Value value) {
            copyOnWrite();
            ((WriteResult) this.instance).setTransformResults(index, value);
            return this;
        }

        public Builder setTransformResults(int index, Value.Builder builderForValue) {
            copyOnWrite();
            ((WriteResult) this.instance).setTransformResults(index, (Value) builderForValue.build());
            return this;
        }

        public Builder addTransformResults(Value value) {
            copyOnWrite();
            ((WriteResult) this.instance).addTransformResults(value);
            return this;
        }

        public Builder addTransformResults(int index, Value value) {
            copyOnWrite();
            ((WriteResult) this.instance).addTransformResults(index, value);
            return this;
        }

        public Builder addTransformResults(Value.Builder builderForValue) {
            copyOnWrite();
            ((WriteResult) this.instance).addTransformResults((Value) builderForValue.build());
            return this;
        }

        public Builder addTransformResults(int index, Value.Builder builderForValue) {
            copyOnWrite();
            ((WriteResult) this.instance).addTransformResults(index, (Value) builderForValue.build());
            return this;
        }

        public Builder addAllTransformResults(Iterable<? extends Value> values) {
            copyOnWrite();
            ((WriteResult) this.instance).addAllTransformResults(values);
            return this;
        }

        public Builder clearTransformResults() {
            copyOnWrite();
            ((WriteResult) this.instance).clearTransformResults();
            return this;
        }

        public Builder removeTransformResults(int index) {
            copyOnWrite();
            ((WriteResult) this.instance).removeTransformResults(index);
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new WriteResult();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001ဉ\u0000\u0002\u001b", new Object[]{"bitField0_", "updateTime_", "transformResults_", Value.class});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<WriteResult> parser = PARSER;
                if (parser == null) {
                    synchronized (WriteResult.class) {
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
        WriteResult defaultInstance = new WriteResult();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(WriteResult.class, defaultInstance);
    }

    public static WriteResult getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<WriteResult> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
