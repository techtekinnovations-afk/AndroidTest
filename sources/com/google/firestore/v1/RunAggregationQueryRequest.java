package com.google.firestore.v1;

import com.google.firestore.v1.StructuredAggregationQuery;
import com.google.firestore.v1.TransactionOptions;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import com.google.protobuf.Timestamp;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class RunAggregationQueryRequest extends GeneratedMessageLite<RunAggregationQueryRequest, Builder> implements RunAggregationQueryRequestOrBuilder {
    /* access modifiers changed from: private */
    public static final RunAggregationQueryRequest DEFAULT_INSTANCE;
    public static final int NEW_TRANSACTION_FIELD_NUMBER = 5;
    public static final int PARENT_FIELD_NUMBER = 1;
    private static volatile Parser<RunAggregationQueryRequest> PARSER = null;
    public static final int READ_TIME_FIELD_NUMBER = 6;
    public static final int STRUCTURED_AGGREGATION_QUERY_FIELD_NUMBER = 2;
    public static final int TRANSACTION_FIELD_NUMBER = 4;
    private int consistencySelectorCase_ = 0;
    private Object consistencySelector_;
    private String parent_ = "";
    private int queryTypeCase_ = 0;
    private Object queryType_;

    private RunAggregationQueryRequest() {
    }

    public enum QueryTypeCase {
        STRUCTURED_AGGREGATION_QUERY(2),
        QUERYTYPE_NOT_SET(0);
        
        private final int value;

        private QueryTypeCase(int value2) {
            this.value = value2;
        }

        @Deprecated
        public static QueryTypeCase valueOf(int value2) {
            return forNumber(value2);
        }

        public static QueryTypeCase forNumber(int value2) {
            switch (value2) {
                case 0:
                    return QUERYTYPE_NOT_SET;
                case 2:
                    return STRUCTURED_AGGREGATION_QUERY;
                default:
                    return null;
            }
        }

        public int getNumber() {
            return this.value;
        }
    }

    public QueryTypeCase getQueryTypeCase() {
        return QueryTypeCase.forNumber(this.queryTypeCase_);
    }

    /* access modifiers changed from: private */
    public void clearQueryType() {
        this.queryTypeCase_ = 0;
        this.queryType_ = null;
    }

    public enum ConsistencySelectorCase {
        TRANSACTION(4),
        NEW_TRANSACTION(5),
        READ_TIME(6),
        CONSISTENCYSELECTOR_NOT_SET(0);
        
        private final int value;

        private ConsistencySelectorCase(int value2) {
            this.value = value2;
        }

        @Deprecated
        public static ConsistencySelectorCase valueOf(int value2) {
            return forNumber(value2);
        }

        public static ConsistencySelectorCase forNumber(int value2) {
            switch (value2) {
                case 0:
                    return CONSISTENCYSELECTOR_NOT_SET;
                case 4:
                    return TRANSACTION;
                case 5:
                    return NEW_TRANSACTION;
                case 6:
                    return READ_TIME;
                default:
                    return null;
            }
        }

        public int getNumber() {
            return this.value;
        }
    }

    public ConsistencySelectorCase getConsistencySelectorCase() {
        return ConsistencySelectorCase.forNumber(this.consistencySelectorCase_);
    }

    /* access modifiers changed from: private */
    public void clearConsistencySelector() {
        this.consistencySelectorCase_ = 0;
        this.consistencySelector_ = null;
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

    public boolean hasStructuredAggregationQuery() {
        return this.queryTypeCase_ == 2;
    }

    public StructuredAggregationQuery getStructuredAggregationQuery() {
        if (this.queryTypeCase_ == 2) {
            return (StructuredAggregationQuery) this.queryType_;
        }
        return StructuredAggregationQuery.getDefaultInstance();
    }

    /* access modifiers changed from: private */
    public void setStructuredAggregationQuery(StructuredAggregationQuery value) {
        value.getClass();
        this.queryType_ = value;
        this.queryTypeCase_ = 2;
    }

    /* access modifiers changed from: private */
    public void mergeStructuredAggregationQuery(StructuredAggregationQuery value) {
        value.getClass();
        if (this.queryTypeCase_ != 2 || this.queryType_ == StructuredAggregationQuery.getDefaultInstance()) {
            this.queryType_ = value;
        } else {
            this.queryType_ = ((StructuredAggregationQuery.Builder) StructuredAggregationQuery.newBuilder((StructuredAggregationQuery) this.queryType_).mergeFrom(value)).buildPartial();
        }
        this.queryTypeCase_ = 2;
    }

    /* access modifiers changed from: private */
    public void clearStructuredAggregationQuery() {
        if (this.queryTypeCase_ == 2) {
            this.queryTypeCase_ = 0;
            this.queryType_ = null;
        }
    }

    public boolean hasTransaction() {
        return this.consistencySelectorCase_ == 4;
    }

    public ByteString getTransaction() {
        if (this.consistencySelectorCase_ == 4) {
            return (ByteString) this.consistencySelector_;
        }
        return ByteString.EMPTY;
    }

    /* access modifiers changed from: private */
    public void setTransaction(ByteString value) {
        Class<?> cls = value.getClass();
        this.consistencySelectorCase_ = 4;
        this.consistencySelector_ = value;
    }

    /* access modifiers changed from: private */
    public void clearTransaction() {
        if (this.consistencySelectorCase_ == 4) {
            this.consistencySelectorCase_ = 0;
            this.consistencySelector_ = null;
        }
    }

    public boolean hasNewTransaction() {
        return this.consistencySelectorCase_ == 5;
    }

    public TransactionOptions getNewTransaction() {
        if (this.consistencySelectorCase_ == 5) {
            return (TransactionOptions) this.consistencySelector_;
        }
        return TransactionOptions.getDefaultInstance();
    }

    /* access modifiers changed from: private */
    public void setNewTransaction(TransactionOptions value) {
        value.getClass();
        this.consistencySelector_ = value;
        this.consistencySelectorCase_ = 5;
    }

    /* access modifiers changed from: private */
    public void mergeNewTransaction(TransactionOptions value) {
        value.getClass();
        if (this.consistencySelectorCase_ != 5 || this.consistencySelector_ == TransactionOptions.getDefaultInstance()) {
            this.consistencySelector_ = value;
        } else {
            this.consistencySelector_ = ((TransactionOptions.Builder) TransactionOptions.newBuilder((TransactionOptions) this.consistencySelector_).mergeFrom(value)).buildPartial();
        }
        this.consistencySelectorCase_ = 5;
    }

    /* access modifiers changed from: private */
    public void clearNewTransaction() {
        if (this.consistencySelectorCase_ == 5) {
            this.consistencySelectorCase_ = 0;
            this.consistencySelector_ = null;
        }
    }

    public boolean hasReadTime() {
        return this.consistencySelectorCase_ == 6;
    }

    public Timestamp getReadTime() {
        if (this.consistencySelectorCase_ == 6) {
            return (Timestamp) this.consistencySelector_;
        }
        return Timestamp.getDefaultInstance();
    }

    /* access modifiers changed from: private */
    public void setReadTime(Timestamp value) {
        value.getClass();
        this.consistencySelector_ = value;
        this.consistencySelectorCase_ = 6;
    }

    /* access modifiers changed from: private */
    public void mergeReadTime(Timestamp value) {
        value.getClass();
        if (this.consistencySelectorCase_ != 6 || this.consistencySelector_ == Timestamp.getDefaultInstance()) {
            this.consistencySelector_ = value;
        } else {
            this.consistencySelector_ = ((Timestamp.Builder) Timestamp.newBuilder((Timestamp) this.consistencySelector_).mergeFrom(value)).buildPartial();
        }
        this.consistencySelectorCase_ = 6;
    }

    /* access modifiers changed from: private */
    public void clearReadTime() {
        if (this.consistencySelectorCase_ == 6) {
            this.consistencySelectorCase_ = 0;
            this.consistencySelector_ = null;
        }
    }

    public static RunAggregationQueryRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (RunAggregationQueryRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static RunAggregationQueryRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (RunAggregationQueryRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static RunAggregationQueryRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (RunAggregationQueryRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static RunAggregationQueryRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (RunAggregationQueryRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static RunAggregationQueryRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (RunAggregationQueryRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static RunAggregationQueryRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (RunAggregationQueryRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static RunAggregationQueryRequest parseFrom(InputStream input) throws IOException {
        return (RunAggregationQueryRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static RunAggregationQueryRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (RunAggregationQueryRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static RunAggregationQueryRequest parseDelimitedFrom(InputStream input) throws IOException {
        return (RunAggregationQueryRequest) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static RunAggregationQueryRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (RunAggregationQueryRequest) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static RunAggregationQueryRequest parseFrom(CodedInputStream input) throws IOException {
        return (RunAggregationQueryRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static RunAggregationQueryRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (RunAggregationQueryRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(RunAggregationQueryRequest prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<RunAggregationQueryRequest, Builder> implements RunAggregationQueryRequestOrBuilder {
        private Builder() {
            super(RunAggregationQueryRequest.DEFAULT_INSTANCE);
        }

        public QueryTypeCase getQueryTypeCase() {
            return ((RunAggregationQueryRequest) this.instance).getQueryTypeCase();
        }

        public Builder clearQueryType() {
            copyOnWrite();
            ((RunAggregationQueryRequest) this.instance).clearQueryType();
            return this;
        }

        public ConsistencySelectorCase getConsistencySelectorCase() {
            return ((RunAggregationQueryRequest) this.instance).getConsistencySelectorCase();
        }

        public Builder clearConsistencySelector() {
            copyOnWrite();
            ((RunAggregationQueryRequest) this.instance).clearConsistencySelector();
            return this;
        }

        public String getParent() {
            return ((RunAggregationQueryRequest) this.instance).getParent();
        }

        public ByteString getParentBytes() {
            return ((RunAggregationQueryRequest) this.instance).getParentBytes();
        }

        public Builder setParent(String value) {
            copyOnWrite();
            ((RunAggregationQueryRequest) this.instance).setParent(value);
            return this;
        }

        public Builder clearParent() {
            copyOnWrite();
            ((RunAggregationQueryRequest) this.instance).clearParent();
            return this;
        }

        public Builder setParentBytes(ByteString value) {
            copyOnWrite();
            ((RunAggregationQueryRequest) this.instance).setParentBytes(value);
            return this;
        }

        public boolean hasStructuredAggregationQuery() {
            return ((RunAggregationQueryRequest) this.instance).hasStructuredAggregationQuery();
        }

        public StructuredAggregationQuery getStructuredAggregationQuery() {
            return ((RunAggregationQueryRequest) this.instance).getStructuredAggregationQuery();
        }

        public Builder setStructuredAggregationQuery(StructuredAggregationQuery value) {
            copyOnWrite();
            ((RunAggregationQueryRequest) this.instance).setStructuredAggregationQuery(value);
            return this;
        }

        public Builder setStructuredAggregationQuery(StructuredAggregationQuery.Builder builderForValue) {
            copyOnWrite();
            ((RunAggregationQueryRequest) this.instance).setStructuredAggregationQuery((StructuredAggregationQuery) builderForValue.build());
            return this;
        }

        public Builder mergeStructuredAggregationQuery(StructuredAggregationQuery value) {
            copyOnWrite();
            ((RunAggregationQueryRequest) this.instance).mergeStructuredAggregationQuery(value);
            return this;
        }

        public Builder clearStructuredAggregationQuery() {
            copyOnWrite();
            ((RunAggregationQueryRequest) this.instance).clearStructuredAggregationQuery();
            return this;
        }

        public boolean hasTransaction() {
            return ((RunAggregationQueryRequest) this.instance).hasTransaction();
        }

        public ByteString getTransaction() {
            return ((RunAggregationQueryRequest) this.instance).getTransaction();
        }

        public Builder setTransaction(ByteString value) {
            copyOnWrite();
            ((RunAggregationQueryRequest) this.instance).setTransaction(value);
            return this;
        }

        public Builder clearTransaction() {
            copyOnWrite();
            ((RunAggregationQueryRequest) this.instance).clearTransaction();
            return this;
        }

        public boolean hasNewTransaction() {
            return ((RunAggregationQueryRequest) this.instance).hasNewTransaction();
        }

        public TransactionOptions getNewTransaction() {
            return ((RunAggregationQueryRequest) this.instance).getNewTransaction();
        }

        public Builder setNewTransaction(TransactionOptions value) {
            copyOnWrite();
            ((RunAggregationQueryRequest) this.instance).setNewTransaction(value);
            return this;
        }

        public Builder setNewTransaction(TransactionOptions.Builder builderForValue) {
            copyOnWrite();
            ((RunAggregationQueryRequest) this.instance).setNewTransaction((TransactionOptions) builderForValue.build());
            return this;
        }

        public Builder mergeNewTransaction(TransactionOptions value) {
            copyOnWrite();
            ((RunAggregationQueryRequest) this.instance).mergeNewTransaction(value);
            return this;
        }

        public Builder clearNewTransaction() {
            copyOnWrite();
            ((RunAggregationQueryRequest) this.instance).clearNewTransaction();
            return this;
        }

        public boolean hasReadTime() {
            return ((RunAggregationQueryRequest) this.instance).hasReadTime();
        }

        public Timestamp getReadTime() {
            return ((RunAggregationQueryRequest) this.instance).getReadTime();
        }

        public Builder setReadTime(Timestamp value) {
            copyOnWrite();
            ((RunAggregationQueryRequest) this.instance).setReadTime(value);
            return this;
        }

        public Builder setReadTime(Timestamp.Builder builderForValue) {
            copyOnWrite();
            ((RunAggregationQueryRequest) this.instance).setReadTime((Timestamp) builderForValue.build());
            return this;
        }

        public Builder mergeReadTime(Timestamp value) {
            copyOnWrite();
            ((RunAggregationQueryRequest) this.instance).mergeReadTime(value);
            return this;
        }

        public Builder clearReadTime() {
            copyOnWrite();
            ((RunAggregationQueryRequest) this.instance).clearReadTime();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new RunAggregationQueryRequest();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0005\u0002\u0000\u0001\u0006\u0005\u0000\u0000\u0000\u0001Ȉ\u0002<\u0000\u0004=\u0001\u0005<\u0001\u0006<\u0001", new Object[]{"queryType_", "queryTypeCase_", "consistencySelector_", "consistencySelectorCase_", "parent_", StructuredAggregationQuery.class, TransactionOptions.class, Timestamp.class});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<RunAggregationQueryRequest> parser = PARSER;
                if (parser == null) {
                    synchronized (RunAggregationQueryRequest.class) {
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
        RunAggregationQueryRequest defaultInstance = new RunAggregationQueryRequest();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(RunAggregationQueryRequest.class, defaultInstance);
    }

    public static RunAggregationQueryRequest getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<RunAggregationQueryRequest> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
