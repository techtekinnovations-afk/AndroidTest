package com.google.firestore.v1;

import com.google.firestore.v1.DocumentMask;
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

public final class GetDocumentRequest extends GeneratedMessageLite<GetDocumentRequest, Builder> implements GetDocumentRequestOrBuilder {
    /* access modifiers changed from: private */
    public static final GetDocumentRequest DEFAULT_INSTANCE;
    public static final int MASK_FIELD_NUMBER = 2;
    public static final int NAME_FIELD_NUMBER = 1;
    private static volatile Parser<GetDocumentRequest> PARSER = null;
    public static final int READ_TIME_FIELD_NUMBER = 5;
    public static final int TRANSACTION_FIELD_NUMBER = 3;
    private int bitField0_;
    private int consistencySelectorCase_ = 0;
    private Object consistencySelector_;
    private DocumentMask mask_;
    private String name_ = "";

    private GetDocumentRequest() {
    }

    public enum ConsistencySelectorCase {
        TRANSACTION(3),
        READ_TIME(5),
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
                case 3:
                    return TRANSACTION;
                case 5:
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

    public String getName() {
        return this.name_;
    }

    public ByteString getNameBytes() {
        return ByteString.copyFromUtf8(this.name_);
    }

    /* access modifiers changed from: private */
    public void setName(String value) {
        Class<?> cls = value.getClass();
        this.name_ = value;
    }

    /* access modifiers changed from: private */
    public void clearName() {
        this.name_ = getDefaultInstance().getName();
    }

    /* access modifiers changed from: private */
    public void setNameBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.name_ = value.toStringUtf8();
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
        return this.consistencySelectorCase_ == 3;
    }

    public ByteString getTransaction() {
        if (this.consistencySelectorCase_ == 3) {
            return (ByteString) this.consistencySelector_;
        }
        return ByteString.EMPTY;
    }

    /* access modifiers changed from: private */
    public void setTransaction(ByteString value) {
        Class<?> cls = value.getClass();
        this.consistencySelectorCase_ = 3;
        this.consistencySelector_ = value;
    }

    /* access modifiers changed from: private */
    public void clearTransaction() {
        if (this.consistencySelectorCase_ == 3) {
            this.consistencySelectorCase_ = 0;
            this.consistencySelector_ = null;
        }
    }

    public boolean hasReadTime() {
        return this.consistencySelectorCase_ == 5;
    }

    public Timestamp getReadTime() {
        if (this.consistencySelectorCase_ == 5) {
            return (Timestamp) this.consistencySelector_;
        }
        return Timestamp.getDefaultInstance();
    }

    /* access modifiers changed from: private */
    public void setReadTime(Timestamp value) {
        value.getClass();
        this.consistencySelector_ = value;
        this.consistencySelectorCase_ = 5;
    }

    /* access modifiers changed from: private */
    public void mergeReadTime(Timestamp value) {
        value.getClass();
        if (this.consistencySelectorCase_ != 5 || this.consistencySelector_ == Timestamp.getDefaultInstance()) {
            this.consistencySelector_ = value;
        } else {
            this.consistencySelector_ = ((Timestamp.Builder) Timestamp.newBuilder((Timestamp) this.consistencySelector_).mergeFrom(value)).buildPartial();
        }
        this.consistencySelectorCase_ = 5;
    }

    /* access modifiers changed from: private */
    public void clearReadTime() {
        if (this.consistencySelectorCase_ == 5) {
            this.consistencySelectorCase_ = 0;
            this.consistencySelector_ = null;
        }
    }

    public static GetDocumentRequest parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (GetDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static GetDocumentRequest parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (GetDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static GetDocumentRequest parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (GetDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static GetDocumentRequest parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (GetDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static GetDocumentRequest parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (GetDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static GetDocumentRequest parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (GetDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static GetDocumentRequest parseFrom(InputStream input) throws IOException {
        return (GetDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static GetDocumentRequest parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (GetDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static GetDocumentRequest parseDelimitedFrom(InputStream input) throws IOException {
        return (GetDocumentRequest) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static GetDocumentRequest parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (GetDocumentRequest) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static GetDocumentRequest parseFrom(CodedInputStream input) throws IOException {
        return (GetDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static GetDocumentRequest parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (GetDocumentRequest) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(GetDocumentRequest prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<GetDocumentRequest, Builder> implements GetDocumentRequestOrBuilder {
        private Builder() {
            super(GetDocumentRequest.DEFAULT_INSTANCE);
        }

        public ConsistencySelectorCase getConsistencySelectorCase() {
            return ((GetDocumentRequest) this.instance).getConsistencySelectorCase();
        }

        public Builder clearConsistencySelector() {
            copyOnWrite();
            ((GetDocumentRequest) this.instance).clearConsistencySelector();
            return this;
        }

        public String getName() {
            return ((GetDocumentRequest) this.instance).getName();
        }

        public ByteString getNameBytes() {
            return ((GetDocumentRequest) this.instance).getNameBytes();
        }

        public Builder setName(String value) {
            copyOnWrite();
            ((GetDocumentRequest) this.instance).setName(value);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((GetDocumentRequest) this.instance).clearName();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            copyOnWrite();
            ((GetDocumentRequest) this.instance).setNameBytes(value);
            return this;
        }

        public boolean hasMask() {
            return ((GetDocumentRequest) this.instance).hasMask();
        }

        public DocumentMask getMask() {
            return ((GetDocumentRequest) this.instance).getMask();
        }

        public Builder setMask(DocumentMask value) {
            copyOnWrite();
            ((GetDocumentRequest) this.instance).setMask(value);
            return this;
        }

        public Builder setMask(DocumentMask.Builder builderForValue) {
            copyOnWrite();
            ((GetDocumentRequest) this.instance).setMask((DocumentMask) builderForValue.build());
            return this;
        }

        public Builder mergeMask(DocumentMask value) {
            copyOnWrite();
            ((GetDocumentRequest) this.instance).mergeMask(value);
            return this;
        }

        public Builder clearMask() {
            copyOnWrite();
            ((GetDocumentRequest) this.instance).clearMask();
            return this;
        }

        public boolean hasTransaction() {
            return ((GetDocumentRequest) this.instance).hasTransaction();
        }

        public ByteString getTransaction() {
            return ((GetDocumentRequest) this.instance).getTransaction();
        }

        public Builder setTransaction(ByteString value) {
            copyOnWrite();
            ((GetDocumentRequest) this.instance).setTransaction(value);
            return this;
        }

        public Builder clearTransaction() {
            copyOnWrite();
            ((GetDocumentRequest) this.instance).clearTransaction();
            return this;
        }

        public boolean hasReadTime() {
            return ((GetDocumentRequest) this.instance).hasReadTime();
        }

        public Timestamp getReadTime() {
            return ((GetDocumentRequest) this.instance).getReadTime();
        }

        public Builder setReadTime(Timestamp value) {
            copyOnWrite();
            ((GetDocumentRequest) this.instance).setReadTime(value);
            return this;
        }

        public Builder setReadTime(Timestamp.Builder builderForValue) {
            copyOnWrite();
            ((GetDocumentRequest) this.instance).setReadTime((Timestamp) builderForValue.build());
            return this;
        }

        public Builder mergeReadTime(Timestamp value) {
            copyOnWrite();
            ((GetDocumentRequest) this.instance).mergeReadTime(value);
            return this;
        }

        public Builder clearReadTime() {
            copyOnWrite();
            ((GetDocumentRequest) this.instance).clearReadTime();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new GetDocumentRequest();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0004\u0001\u0001\u0001\u0005\u0004\u0000\u0000\u0000\u0001Ȉ\u0002ဉ\u0000\u0003=\u0000\u0005<\u0000", new Object[]{"consistencySelector_", "consistencySelectorCase_", "bitField0_", "name_", "mask_", Timestamp.class});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<GetDocumentRequest> parser = PARSER;
                if (parser == null) {
                    synchronized (GetDocumentRequest.class) {
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
        GetDocumentRequest defaultInstance = new GetDocumentRequest();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(GetDocumentRequest.class, defaultInstance);
    }

    public static GetDocumentRequest getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<GetDocumentRequest> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
