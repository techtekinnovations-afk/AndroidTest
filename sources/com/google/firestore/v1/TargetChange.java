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
import com.google.rpc.Status;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class TargetChange extends GeneratedMessageLite<TargetChange, Builder> implements TargetChangeOrBuilder {
    public static final int CAUSE_FIELD_NUMBER = 3;
    /* access modifiers changed from: private */
    public static final TargetChange DEFAULT_INSTANCE;
    private static volatile Parser<TargetChange> PARSER = null;
    public static final int READ_TIME_FIELD_NUMBER = 6;
    public static final int RESUME_TOKEN_FIELD_NUMBER = 4;
    public static final int TARGET_CHANGE_TYPE_FIELD_NUMBER = 1;
    public static final int TARGET_IDS_FIELD_NUMBER = 2;
    private int bitField0_;
    private Status cause_;
    private Timestamp readTime_;
    private ByteString resumeToken_ = ByteString.EMPTY;
    private int targetChangeType_;
    private int targetIdsMemoizedSerializedSize = -1;
    private Internal.IntList targetIds_ = emptyIntList();

    private TargetChange() {
    }

    public enum TargetChangeType implements Internal.EnumLite {
        NO_CHANGE(0),
        ADD(1),
        REMOVE(2),
        CURRENT(3),
        RESET(4),
        UNRECOGNIZED(-1);
        
        public static final int ADD_VALUE = 1;
        public static final int CURRENT_VALUE = 3;
        public static final int NO_CHANGE_VALUE = 0;
        public static final int REMOVE_VALUE = 2;
        public static final int RESET_VALUE = 4;
        private static final Internal.EnumLiteMap<TargetChangeType> internalValueMap = null;
        private final int value;

        static {
            internalValueMap = new Internal.EnumLiteMap<TargetChangeType>() {
                public TargetChangeType findValueByNumber(int number) {
                    return TargetChangeType.forNumber(number);
                }
            };
        }

        public final int getNumber() {
            if (this != UNRECOGNIZED) {
                return this.value;
            }
            throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
        }

        @Deprecated
        public static TargetChangeType valueOf(int value2) {
            return forNumber(value2);
        }

        public static TargetChangeType forNumber(int value2) {
            switch (value2) {
                case 0:
                    return NO_CHANGE;
                case 1:
                    return ADD;
                case 2:
                    return REMOVE;
                case 3:
                    return CURRENT;
                case 4:
                    return RESET;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<TargetChangeType> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return TargetChangeTypeVerifier.INSTANCE;
        }

        private static final class TargetChangeTypeVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = null;

            private TargetChangeTypeVerifier() {
            }

            static {
                INSTANCE = new TargetChangeTypeVerifier();
            }

            public boolean isInRange(int number) {
                return TargetChangeType.forNumber(number) != null;
            }
        }

        private TargetChangeType(int value2) {
            this.value = value2;
        }
    }

    public int getTargetChangeTypeValue() {
        return this.targetChangeType_;
    }

    public TargetChangeType getTargetChangeType() {
        TargetChangeType result = TargetChangeType.forNumber(this.targetChangeType_);
        return result == null ? TargetChangeType.UNRECOGNIZED : result;
    }

    /* access modifiers changed from: private */
    public void setTargetChangeTypeValue(int value) {
        this.targetChangeType_ = value;
    }

    /* access modifiers changed from: private */
    public void setTargetChangeType(TargetChangeType value) {
        this.targetChangeType_ = value.getNumber();
    }

    /* access modifiers changed from: private */
    public void clearTargetChangeType() {
        this.targetChangeType_ = 0;
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

    public boolean hasCause() {
        return (this.bitField0_ & 1) != 0;
    }

    public Status getCause() {
        return this.cause_ == null ? Status.getDefaultInstance() : this.cause_;
    }

    /* access modifiers changed from: private */
    public void setCause(Status value) {
        value.getClass();
        this.cause_ = value;
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void mergeCause(Status value) {
        value.getClass();
        if (this.cause_ == null || this.cause_ == Status.getDefaultInstance()) {
            this.cause_ = value;
        } else {
            this.cause_ = (Status) ((Status.Builder) Status.newBuilder(this.cause_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void clearCause() {
        this.cause_ = null;
        this.bitField0_ &= -2;
    }

    public ByteString getResumeToken() {
        return this.resumeToken_;
    }

    /* access modifiers changed from: private */
    public void setResumeToken(ByteString value) {
        Class<?> cls = value.getClass();
        this.resumeToken_ = value;
    }

    /* access modifiers changed from: private */
    public void clearResumeToken() {
        this.resumeToken_ = getDefaultInstance().getResumeToken();
    }

    public boolean hasReadTime() {
        return (this.bitField0_ & 2) != 0;
    }

    public Timestamp getReadTime() {
        return this.readTime_ == null ? Timestamp.getDefaultInstance() : this.readTime_;
    }

    /* access modifiers changed from: private */
    public void setReadTime(Timestamp value) {
        value.getClass();
        this.readTime_ = value;
        this.bitField0_ |= 2;
    }

    /* access modifiers changed from: private */
    public void mergeReadTime(Timestamp value) {
        value.getClass();
        if (this.readTime_ == null || this.readTime_ == Timestamp.getDefaultInstance()) {
            this.readTime_ = value;
        } else {
            this.readTime_ = (Timestamp) ((Timestamp.Builder) Timestamp.newBuilder(this.readTime_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 2;
    }

    /* access modifiers changed from: private */
    public void clearReadTime() {
        this.readTime_ = null;
        this.bitField0_ &= -3;
    }

    public static TargetChange parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (TargetChange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static TargetChange parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (TargetChange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static TargetChange parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (TargetChange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static TargetChange parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (TargetChange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static TargetChange parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (TargetChange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static TargetChange parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (TargetChange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static TargetChange parseFrom(InputStream input) throws IOException {
        return (TargetChange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static TargetChange parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (TargetChange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static TargetChange parseDelimitedFrom(InputStream input) throws IOException {
        return (TargetChange) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static TargetChange parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (TargetChange) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static TargetChange parseFrom(CodedInputStream input) throws IOException {
        return (TargetChange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static TargetChange parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (TargetChange) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(TargetChange prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<TargetChange, Builder> implements TargetChangeOrBuilder {
        private Builder() {
            super(TargetChange.DEFAULT_INSTANCE);
        }

        public int getTargetChangeTypeValue() {
            return ((TargetChange) this.instance).getTargetChangeTypeValue();
        }

        public Builder setTargetChangeTypeValue(int value) {
            copyOnWrite();
            ((TargetChange) this.instance).setTargetChangeTypeValue(value);
            return this;
        }

        public TargetChangeType getTargetChangeType() {
            return ((TargetChange) this.instance).getTargetChangeType();
        }

        public Builder setTargetChangeType(TargetChangeType value) {
            copyOnWrite();
            ((TargetChange) this.instance).setTargetChangeType(value);
            return this;
        }

        public Builder clearTargetChangeType() {
            copyOnWrite();
            ((TargetChange) this.instance).clearTargetChangeType();
            return this;
        }

        public List<Integer> getTargetIdsList() {
            return Collections.unmodifiableList(((TargetChange) this.instance).getTargetIdsList());
        }

        public int getTargetIdsCount() {
            return ((TargetChange) this.instance).getTargetIdsCount();
        }

        public int getTargetIds(int index) {
            return ((TargetChange) this.instance).getTargetIds(index);
        }

        public Builder setTargetIds(int index, int value) {
            copyOnWrite();
            ((TargetChange) this.instance).setTargetIds(index, value);
            return this;
        }

        public Builder addTargetIds(int value) {
            copyOnWrite();
            ((TargetChange) this.instance).addTargetIds(value);
            return this;
        }

        public Builder addAllTargetIds(Iterable<? extends Integer> values) {
            copyOnWrite();
            ((TargetChange) this.instance).addAllTargetIds(values);
            return this;
        }

        public Builder clearTargetIds() {
            copyOnWrite();
            ((TargetChange) this.instance).clearTargetIds();
            return this;
        }

        public boolean hasCause() {
            return ((TargetChange) this.instance).hasCause();
        }

        public Status getCause() {
            return ((TargetChange) this.instance).getCause();
        }

        public Builder setCause(Status value) {
            copyOnWrite();
            ((TargetChange) this.instance).setCause(value);
            return this;
        }

        public Builder setCause(Status.Builder builderForValue) {
            copyOnWrite();
            ((TargetChange) this.instance).setCause((Status) builderForValue.build());
            return this;
        }

        public Builder mergeCause(Status value) {
            copyOnWrite();
            ((TargetChange) this.instance).mergeCause(value);
            return this;
        }

        public Builder clearCause() {
            copyOnWrite();
            ((TargetChange) this.instance).clearCause();
            return this;
        }

        public ByteString getResumeToken() {
            return ((TargetChange) this.instance).getResumeToken();
        }

        public Builder setResumeToken(ByteString value) {
            copyOnWrite();
            ((TargetChange) this.instance).setResumeToken(value);
            return this;
        }

        public Builder clearResumeToken() {
            copyOnWrite();
            ((TargetChange) this.instance).clearResumeToken();
            return this;
        }

        public boolean hasReadTime() {
            return ((TargetChange) this.instance).hasReadTime();
        }

        public Timestamp getReadTime() {
            return ((TargetChange) this.instance).getReadTime();
        }

        public Builder setReadTime(Timestamp value) {
            copyOnWrite();
            ((TargetChange) this.instance).setReadTime(value);
            return this;
        }

        public Builder setReadTime(Timestamp.Builder builderForValue) {
            copyOnWrite();
            ((TargetChange) this.instance).setReadTime((Timestamp) builderForValue.build());
            return this;
        }

        public Builder mergeReadTime(Timestamp value) {
            copyOnWrite();
            ((TargetChange) this.instance).mergeReadTime(value);
            return this;
        }

        public Builder clearReadTime() {
            copyOnWrite();
            ((TargetChange) this.instance).clearReadTime();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new TargetChange();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0005\u0000\u0001\u0001\u0006\u0005\u0000\u0001\u0000\u0001\f\u0002'\u0003ဉ\u0000\u0004\n\u0006ဉ\u0001", new Object[]{"bitField0_", "targetChangeType_", "targetIds_", "cause_", "resumeToken_", "readTime_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<TargetChange> parser = PARSER;
                if (parser == null) {
                    synchronized (TargetChange.class) {
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
        TargetChange defaultInstance = new TargetChange();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(TargetChange.class, defaultInstance);
    }

    public static TargetChange getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<TargetChange> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
