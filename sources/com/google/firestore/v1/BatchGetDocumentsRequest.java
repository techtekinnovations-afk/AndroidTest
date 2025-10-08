package com.google.firestore.v1;

import com.google.firestore.v1.DocumentMask;
import com.google.firestore.v1.TransactionOptions;
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

public final class BatchGetDocumentsRequest extends GeneratedMessageLite<BatchGetDocumentsRequest, Builder> implements BatchGetDocumentsRequestOrBuilder {
    public static final int DATABASE_FIELD_NUMBER = 1;
    /* access modifiers changed from: private */
    public static final BatchGetDocumentsRequest DEFAULT_INSTANCE;
    public static final int DOCUMENTS_FIELD_NUMBER = 2;
    public static final int MASK_FIELD_NUMBER = 3;
    public static final int NEW_TRANSACTION_FIELD_NUMBER = 5;
    private static volatile Parser<BatchGetDocumentsRequest> PARSER = null;
    public static final int READ_TIME_FIELD_NUMBER = 7;
    public static final int TRANSACTION_FIELD_NUMBER = 4;
    private int bitField0_;
    private int consistencySelectorCase_ = 0;
    private Object consistencySelector_;
    private String database_ = "";
    private Internal.ProtobufList<String> documents_ = GeneratedMessageLite.emptyProtobufList();
    private DocumentMask mask_;

    private BatchGetDocumentsRequest() {
    }

    public enum ConsistencySelectorCase {
        TRANSACTION(4),
        NEW_TRANSACTION(5),
        READ_TIME(7),
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
                case 7:
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

    public String getDatabase() {
        return this.database_;
    }

    public ByteString getDatabaseBytes() {
        return ByteString.copyFromUtf8(this.database_);
    }

    /* access modifiers changed from: private */
    public void setDatabase(String value) {
        Class<?> cls = value.getClass();
        this.database_ = value;
    }

    /* access modifiers changed from: private */
    public void clearDatabase() {
        this.database_ = getDefaultInstance().getDatabase();
    }

    /* access modifiers changed from: private */
    public void setDatabaseBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.database_ = value.toStringUtf8();
    }

    public List<String> getDocumentsList() {
        return this.documents_;
    }

    public int getDocumentsCount() {
        return this.documents_.size();
    }

    public String getDocuments(int index) {
        return (String) this.documents_.get(index);
    }

    public ByteString getDocumentsBytes(int index) {
        return ByteString.copyFromUtf8((String) this.documents_.get(index));
    }

    private void ensureDocumentsIsMutable() {
        Internal.ProtobufList<String> tmp = this.documents_;
        if (!tmp.isModifiable()) {
            this.documents_ = GeneratedMessageLite.mutableCopy(tmp);
        }
    }

    /* access modifiers changed from: private */
    public void setDocuments(int index, String value) {
        Class<?> cls = value.getClass();
        ensureDocumentsIsMutable();
        this.documents_.set(index, value);
    }

    /* access modifiers changed from: private */
    public void addDocuments(String value) {
        Class<?> cls = value.getClass();
        ensureDocumentsIsMutable();
        this.documents_.add(value);
    }

    /* access modifiers changed from: private */
    public void addAllDocuments(Iterable<String> values) {
        ensureDocumentsIsMutable();
        AbstractMessageLite.addAll(values, this.documents_);
    }

    /* access modifiers changed from: private */
    public void clearDocuments() {
        this.documents_ = GeneratedMessageLite.emptyProtobufList();
    }

    /* access modifiers changed from: private */
    public void addDocumentsBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        ensureDocumentsIsMutable();
        this.documents_.add(value.toStringUtf8());
    }

    public boolean hasMask() {
        return (this.bitField0_ & 1) != 0;
    }

    public DocumentMask getMask() {
        return this.mask_ == null ? DocumentMask.getDefaultInstance() : this.mask_;
    }

    /* access modifiers changed from: private */
    public void setMask(DocumentMask value) {
        value.getClass();
        this.mask_ = value;
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void mergeMask(DocumentMask value) {
        value.getClass();
        if (this.mask_ == null || this.mask_ == DocumentMask.getDefaultInstance()) {
            this.mask_ = value;
        } else {
            this.mask_ = (DocumentMask) ((DocumentMask.Builder) DocumentMask.newBuilder(this.mask_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void clearMask() {
        this.mask_ = null;
        this.bitField0_ &= -2;
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
        return this.consistencySelectorCase_ == 7;
    }

    public Timestamp getReadTime() {
        if (this.consistencySelectorCase_ == 7) {
            return (Timestamp) this.consistencySelector_;
        }
        return Timestamp.getDefaultInstance();
    }

    /* access modifiers changed from: private */
    public void setReadTime(Timestamp value) {
        value.getClass();
        this.consistencySelector_ = value;
        this.consistencySelectorCase_ = 7;
    }

    /* access modifiers changed from: private */
    public void mergeReadTime(Timestamp value) {
        value.getClass();
        if (this.consistencySelectorCase_ != 7 || this.consistencySelector_ == Timestamp.getDefaultInstance()) {
            this.consistencySelector_ = value;
        } else {
            this.consistencySelector_ = ((Timestamp.Builder) Timestamp.newBuilder((Timestamp) this.consistencySelector_).mergeFrom(value)).buildPartial();
        }
        this.consistencySelectorCase_ = 7;
    }

    /* access modifiers changed from: private */
    public void clearReadTime() {
        if (this.consistencySelectorCase_ == 7) {
            this.consistencySelectorCase_ = 0;
            this.consistencySelector_ = null;
        }
    }

    public static BatchGetDocumentsRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (BatchGetDocumentsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static BatchGetDocumentsRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (BatchGetDocumentsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static BatchGetDocumentsRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (BatchGetDocumentsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static BatchGetDocumentsRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (BatchGetDocumentsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static BatchGetDocumentsRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (BatchGetDocumentsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static BatchGetDocumentsRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (BatchGetDocumentsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static BatchGetDocumentsRequest parseFrom(InputStream input) throws IOException {
        return (BatchGetDocumentsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static BatchGetDocumentsRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (BatchGetDocumentsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static BatchGetDocumentsRequest parseDelimitedFrom(InputStream input) throws IOException {
        return (BatchGetDocumentsRequest) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static BatchGetDocumentsRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (BatchGetDocumentsRequest) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static BatchGetDocumentsRequest parseFrom(CodedInputStream input) throws IOException {
        return (BatchGetDocumentsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static BatchGetDocumentsRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (BatchGetDocumentsRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(BatchGetDocumentsRequest prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<BatchGetDocumentsRequest, Builder> implements BatchGetDocumentsRequestOrBuilder {
        private Builder() {
            super(BatchGetDocumentsRequest.DEFAULT_INSTANCE);
        }

        public ConsistencySelectorCase getConsistencySelectorCase() {
            return ((BatchGetDocumentsRequest) this.instance).getConsistencySelectorCase();
        }

        public Builder clearConsistencySelector() {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).clearConsistencySelector();
            return this;
        }

        public String getDatabase() {
            return ((BatchGetDocumentsRequest) this.instance).getDatabase();
        }

        public ByteString getDatabaseBytes() {
            return ((BatchGetDocumentsRequest) this.instance).getDatabaseBytes();
        }

        public Builder setDatabase(String value) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).setDatabase(value);
            return this;
        }

        public Builder clearDatabase() {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).clearDatabase();
            return this;
        }

        public Builder setDatabaseBytes(ByteString value) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).setDatabaseBytes(value);
            return this;
        }

        public List<String> getDocumentsList() {
            return Collections.unmodifiableList(((BatchGetDocumentsRequest) this.instance).getDocumentsList());
        }

        public int getDocumentsCount() {
            return ((BatchGetDocumentsRequest) this.instance).getDocumentsCount();
        }

        public String getDocuments(int index) {
            return ((BatchGetDocumentsRequest) this.instance).getDocuments(index);
        }

        public ByteString getDocumentsBytes(int index) {
            return ((BatchGetDocumentsRequest) this.instance).getDocumentsBytes(index);
        }

        public Builder setDocuments(int index, String value) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).setDocuments(index, value);
            return this;
        }

        public Builder addDocuments(String value) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).addDocuments(value);
            return this;
        }

        public Builder addAllDocuments(Iterable<String> values) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).addAllDocuments(values);
            return this;
        }

        public Builder clearDocuments() {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).clearDocuments();
            return this;
        }

        public Builder addDocumentsBytes(ByteString value) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).addDocumentsBytes(value);
            return this;
        }

        public boolean hasMask() {
            return ((BatchGetDocumentsRequest) this.instance).hasMask();
        }

        public DocumentMask getMask() {
            return ((BatchGetDocumentsRequest) this.instance).getMask();
        }

        public Builder setMask(DocumentMask value) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).setMask(value);
            return this;
        }

        public Builder setMask(DocumentMask.Builder builderForValue) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).setMask((DocumentMask) builderForValue.build());
            return this;
        }

        public Builder mergeMask(DocumentMask value) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).mergeMask(value);
            return this;
        }

        public Builder clearMask() {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).clearMask();
            return this;
        }

        public boolean hasTransaction() {
            return ((BatchGetDocumentsRequest) this.instance).hasTransaction();
        }

        public ByteString getTransaction() {
            return ((BatchGetDocumentsRequest) this.instance).getTransaction();
        }

        public Builder setTransaction(ByteString value) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).setTransaction(value);
            return this;
        }

        public Builder clearTransaction() {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).clearTransaction();
            return this;
        }

        public boolean hasNewTransaction() {
            return ((BatchGetDocumentsRequest) this.instance).hasNewTransaction();
        }

        public TransactionOptions getNewTransaction() {
            return ((BatchGetDocumentsRequest) this.instance).getNewTransaction();
        }

        public Builder setNewTransaction(TransactionOptions value) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).setNewTransaction(value);
            return this;
        }

        public Builder setNewTransaction(TransactionOptions.Builder builderForValue) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).setNewTransaction((TransactionOptions) builderForValue.build());
            return this;
        }

        public Builder mergeNewTransaction(TransactionOptions value) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).mergeNewTransaction(value);
            return this;
        }

        public Builder clearNewTransaction() {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).clearNewTransaction();
            return this;
        }

        public boolean hasReadTime() {
            return ((BatchGetDocumentsRequest) this.instance).hasReadTime();
        }

        public Timestamp getReadTime() {
            return ((BatchGetDocumentsRequest) this.instance).getReadTime();
        }

        public Builder setReadTime(Timestamp value) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).setReadTime(value);
            return this;
        }

        public Builder setReadTime(Timestamp.Builder builderForValue) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).setReadTime((Timestamp) builderForValue.build());
            return this;
        }

        public Builder mergeReadTime(Timestamp value) {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).mergeReadTime(value);
            return this;
        }

        public Builder clearReadTime() {
            copyOnWrite();
            ((BatchGetDocumentsRequest) this.instance).clearReadTime();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new BatchGetDocumentsRequest();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0006\u0001\u0001\u0001\u0007\u0006\u0000\u0001\u0000\u0001Ȉ\u0002Ț\u0003ဉ\u0000\u0004=\u0000\u0005<\u0000\u0007<\u0000", new Object[]{"consistencySelector_", "consistencySelectorCase_", "bitField0_", "database_", "documents_", "mask_", TransactionOptions.class, Timestamp.class});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<BatchGetDocumentsRequest> parser = PARSER;
                if (parser == null) {
                    synchronized (BatchGetDocumentsRequest.class) {
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
        BatchGetDocumentsRequest defaultInstance = new BatchGetDocumentsRequest();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(BatchGetDocumentsRequest.class, defaultInstance);
    }

    public static BatchGetDocumentsRequest getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<BatchGetDocumentsRequest> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
