package com.google.firebase.firestore.proto;

import com.google.firestore.v1.Target;
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

public final class Target extends GeneratedMessageLite<Target, Builder> implements TargetOrBuilder {
    /* access modifiers changed from: private */
    public static final Target DEFAULT_INSTANCE;
    public static final int DOCUMENTS_FIELD_NUMBER = 6;
    public static final int LAST_LIMBO_FREE_SNAPSHOT_VERSION_FIELD_NUMBER = 7;
    public static final int LAST_LISTEN_SEQUENCE_NUMBER_FIELD_NUMBER = 4;
    private static volatile Parser<Target> PARSER = null;
    public static final int QUERY_FIELD_NUMBER = 5;
    public static final int RESUME_TOKEN_FIELD_NUMBER = 3;
    public static final int SNAPSHOT_VERSION_FIELD_NUMBER = 2;
    public static final int TARGET_ID_FIELD_NUMBER = 1;
    private int bitField0_;
    private Timestamp lastLimboFreeSnapshotVersion_;
    private long lastListenSequenceNumber_;
    private ByteString resumeToken_ = ByteString.EMPTY;
    private Timestamp snapshotVersion_;
    private int targetId_;
    private int targetTypeCase_ = 0;
    private Object targetType_;

    private Target() {
    }

    public enum TargetTypeCase {
        QUERY(5),
        DOCUMENTS(6),
        TARGETTYPE_NOT_SET(0);
        
        private final int value;

        private TargetTypeCase(int value2) {
            this.value = value2;
        }

        @Deprecated
        public static TargetTypeCase valueOf(int value2) {
            return forNumber(value2);
        }

        public static TargetTypeCase forNumber(int value2) {
            switch (value2) {
                case 0:
                    return TARGETTYPE_NOT_SET;
                case 5:
                    return QUERY;
                case 6:
                    return DOCUMENTS;
                default:
                    return null;
            }
        }

        public int getNumber() {
            return this.value;
        }
    }

    public TargetTypeCase getTargetTypeCase() {
        return TargetTypeCase.forNumber(this.targetTypeCase_);
    }

    /* access modifiers changed from: private */
    public void clearTargetType() {
        this.targetTypeCase_ = 0;
        this.targetType_ = null;
    }

    public int getTargetId() {
        return this.targetId_;
    }

    /* access modifiers changed from: private */
    public void setTargetId(int value) {
        this.targetId_ = value;
    }

    /* access modifiers changed from: private */
    public void clearTargetId() {
        this.targetId_ = 0;
    }

    public boolean hasSnapshotVersion() {
        return (this.bitField0_ & 1) != 0;
    }

    public Timestamp getSnapshotVersion() {
        return this.snapshotVersion_ == null ? Timestamp.getDefaultInstance() : this.snapshotVersion_;
    }

    /* access modifiers changed from: private */
    public void setSnapshotVersion(Timestamp value) {
        value.getClass();
        this.snapshotVersion_ = value;
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void mergeSnapshotVersion(Timestamp value) {
        value.getClass();
        if (this.snapshotVersion_ == null || this.snapshotVersion_ == Timestamp.getDefaultInstance()) {
            this.snapshotVersion_ = value;
        } else {
            this.snapshotVersion_ = (Timestamp) ((Timestamp.Builder) Timestamp.newBuilder(this.snapshotVersion_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void clearSnapshotVersion() {
        this.snapshotVersion_ = null;
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

    public long getLastListenSequenceNumber() {
        return this.lastListenSequenceNumber_;
    }

    /* access modifiers changed from: private */
    public void setLastListenSequenceNumber(long value) {
        this.lastListenSequenceNumber_ = value;
    }

    /* access modifiers changed from: private */
    public void clearLastListenSequenceNumber() {
        this.lastListenSequenceNumber_ = 0;
    }

    public boolean hasQuery() {
        return this.targetTypeCase_ == 5;
    }

    public Target.QueryTarget getQuery() {
        if (this.targetTypeCase_ == 5) {
            return (Target.QueryTarget) this.targetType_;
        }
        return Target.QueryTarget.getDefaultInstance();
    }

    /* access modifiers changed from: private */
    public void setQuery(Target.QueryTarget value) {
        value.getClass();
        this.targetType_ = value;
        this.targetTypeCase_ = 5;
    }

    /* access modifiers changed from: private */
    public void mergeQuery(Target.QueryTarget value) {
        value.getClass();
        if (this.targetTypeCase_ != 5 || this.targetType_ == Target.QueryTarget.getDefaultInstance()) {
            this.targetType_ = value;
        } else {
            this.targetType_ = ((Target.QueryTarget.Builder) Target.QueryTarget.newBuilder((Target.QueryTarget) this.targetType_).mergeFrom(value)).buildPartial();
        }
        this.targetTypeCase_ = 5;
    }

    /* access modifiers changed from: private */
    public void clearQuery() {
        if (this.targetTypeCase_ == 5) {
            this.targetTypeCase_ = 0;
            this.targetType_ = null;
        }
    }

    public boolean hasDocuments() {
        return this.targetTypeCase_ == 6;
    }

    public Target.DocumentsTarget getDocuments() {
        if (this.targetTypeCase_ == 6) {
            return (Target.DocumentsTarget) this.targetType_;
        }
        return Target.DocumentsTarget.getDefaultInstance();
    }

    /* access modifiers changed from: private */
    public void setDocuments(Target.DocumentsTarget value) {
        value.getClass();
        this.targetType_ = value;
        this.targetTypeCase_ = 6;
    }

    /* access modifiers changed from: private */
    public void mergeDocuments(Target.DocumentsTarget value) {
        value.getClass();
        if (this.targetTypeCase_ != 6 || this.targetType_ == Target.DocumentsTarget.getDefaultInstance()) {
            this.targetType_ = value;
        } else {
            this.targetType_ = ((Target.DocumentsTarget.Builder) Target.DocumentsTarget.newBuilder((Target.DocumentsTarget) this.targetType_).mergeFrom(value)).buildPartial();
        }
        this.targetTypeCase_ = 6;
    }

    /* access modifiers changed from: private */
    public void clearDocuments() {
        if (this.targetTypeCase_ == 6) {
            this.targetTypeCase_ = 0;
            this.targetType_ = null;
        }
    }

    public boolean hasLastLimboFreeSnapshotVersion() {
        return (this.bitField0_ & 2) != 0;
    }

    public Timestamp getLastLimboFreeSnapshotVersion() {
        return this.lastLimboFreeSnapshotVersion_ == null ? Timestamp.getDefaultInstance() : this.lastLimboFreeSnapshotVersion_;
    }

    /* access modifiers changed from: private */
    public void setLastLimboFreeSnapshotVersion(Timestamp value) {
        value.getClass();
        this.lastLimboFreeSnapshotVersion_ = value;
        this.bitField0_ |= 2;
    }

    /* access modifiers changed from: private */
    public void mergeLastLimboFreeSnapshotVersion(Timestamp value) {
        value.getClass();
        if (this.lastLimboFreeSnapshotVersion_ == null || this.lastLimboFreeSnapshotVersion_ == Timestamp.getDefaultInstance()) {
            this.lastLimboFreeSnapshotVersion_ = value;
        } else {
            this.lastLimboFreeSnapshotVersion_ = (Timestamp) ((Timestamp.Builder) Timestamp.newBuilder(this.lastLimboFreeSnapshotVersion_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 2;
    }

    /* access modifiers changed from: private */
    public void clearLastLimboFreeSnapshotVersion() {
        this.lastLimboFreeSnapshotVersion_ = null;
        this.bitField0_ &= -3;
    }

    public static Target parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Target parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Target parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Target parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Target parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Target parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Target parseFrom(InputStream input) throws IOException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static Target parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Target parseDelimitedFrom(InputStream input) throws IOException {
        return (Target) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static Target parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Target) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Target parseFrom(CodedInputStream input) throws IOException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static Target parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(Target prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Target, Builder> implements TargetOrBuilder {
        private Builder() {
            super(Target.DEFAULT_INSTANCE);
        }

        public TargetTypeCase getTargetTypeCase() {
            return ((Target) this.instance).getTargetTypeCase();
        }

        public Builder clearTargetType() {
            copyOnWrite();
            ((Target) this.instance).clearTargetType();
            return this;
        }

        public int getTargetId() {
            return ((Target) this.instance).getTargetId();
        }

        public Builder setTargetId(int value) {
            copyOnWrite();
            ((Target) this.instance).setTargetId(value);
            return this;
        }

        public Builder clearTargetId() {
            copyOnWrite();
            ((Target) this.instance).clearTargetId();
            return this;
        }

        public boolean hasSnapshotVersion() {
            return ((Target) this.instance).hasSnapshotVersion();
        }

        public Timestamp getSnapshotVersion() {
            return ((Target) this.instance).getSnapshotVersion();
        }

        public Builder setSnapshotVersion(Timestamp value) {
            copyOnWrite();
            ((Target) this.instance).setSnapshotVersion(value);
            return this;
        }

        public Builder setSnapshotVersion(Timestamp.Builder builderForValue) {
            copyOnWrite();
            ((Target) this.instance).setSnapshotVersion((Timestamp) builderForValue.build());
            return this;
        }

        public Builder mergeSnapshotVersion(Timestamp value) {
            copyOnWrite();
            ((Target) this.instance).mergeSnapshotVersion(value);
            return this;
        }

        public Builder clearSnapshotVersion() {
            copyOnWrite();
            ((Target) this.instance).clearSnapshotVersion();
            return this;
        }

        public ByteString getResumeToken() {
            return ((Target) this.instance).getResumeToken();
        }

        public Builder setResumeToken(ByteString value) {
            copyOnWrite();
            ((Target) this.instance).setResumeToken(value);
            return this;
        }

        public Builder clearResumeToken() {
            copyOnWrite();
            ((Target) this.instance).clearResumeToken();
            return this;
        }

        public long getLastListenSequenceNumber() {
            return ((Target) this.instance).getLastListenSequenceNumber();
        }

        public Builder setLastListenSequenceNumber(long value) {
            copyOnWrite();
            ((Target) this.instance).setLastListenSequenceNumber(value);
            return this;
        }

        public Builder clearLastListenSequenceNumber() {
            copyOnWrite();
            ((Target) this.instance).clearLastListenSequenceNumber();
            return this;
        }

        public boolean hasQuery() {
            return ((Target) this.instance).hasQuery();
        }

        public Target.QueryTarget getQuery() {
            return ((Target) this.instance).getQuery();
        }

        public Builder setQuery(Target.QueryTarget value) {
            copyOnWrite();
            ((Target) this.instance).setQuery(value);
            return this;
        }

        public Builder setQuery(Target.QueryTarget.Builder builderForValue) {
            copyOnWrite();
            ((Target) this.instance).setQuery((Target.QueryTarget) builderForValue.build());
            return this;
        }

        public Builder mergeQuery(Target.QueryTarget value) {
            copyOnWrite();
            ((Target) this.instance).mergeQuery(value);
            return this;
        }

        public Builder clearQuery() {
            copyOnWrite();
            ((Target) this.instance).clearQuery();
            return this;
        }

        public boolean hasDocuments() {
            return ((Target) this.instance).hasDocuments();
        }

        public Target.DocumentsTarget getDocuments() {
            return ((Target) this.instance).getDocuments();
        }

        public Builder setDocuments(Target.DocumentsTarget value) {
            copyOnWrite();
            ((Target) this.instance).setDocuments(value);
            return this;
        }

        public Builder setDocuments(Target.DocumentsTarget.Builder builderForValue) {
            copyOnWrite();
            ((Target) this.instance).setDocuments((Target.DocumentsTarget) builderForValue.build());
            return this;
        }

        public Builder mergeDocuments(Target.DocumentsTarget value) {
            copyOnWrite();
            ((Target) this.instance).mergeDocuments(value);
            return this;
        }

        public Builder clearDocuments() {
            copyOnWrite();
            ((Target) this.instance).clearDocuments();
            return this;
        }

        public boolean hasLastLimboFreeSnapshotVersion() {
            return ((Target) this.instance).hasLastLimboFreeSnapshotVersion();
        }

        public Timestamp getLastLimboFreeSnapshotVersion() {
            return ((Target) this.instance).getLastLimboFreeSnapshotVersion();
        }

        public Builder setLastLimboFreeSnapshotVersion(Timestamp value) {
            copyOnWrite();
            ((Target) this.instance).setLastLimboFreeSnapshotVersion(value);
            return this;
        }

        public Builder setLastLimboFreeSnapshotVersion(Timestamp.Builder builderForValue) {
            copyOnWrite();
            ((Target) this.instance).setLastLimboFreeSnapshotVersion((Timestamp) builderForValue.build());
            return this;
        }

        public Builder mergeLastLimboFreeSnapshotVersion(Timestamp value) {
            copyOnWrite();
            ((Target) this.instance).mergeLastLimboFreeSnapshotVersion(value);
            return this;
        }

        public Builder clearLastLimboFreeSnapshotVersion() {
            copyOnWrite();
            ((Target) this.instance).clearLastLimboFreeSnapshotVersion();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new Target();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0007\u0001\u0001\u0001\u0007\u0007\u0000\u0000\u0000\u0001\u0004\u0002ဉ\u0000\u0003\n\u0004\u0002\u0005<\u0000\u0006<\u0000\u0007ဉ\u0001", new Object[]{"targetType_", "targetTypeCase_", "bitField0_", "targetId_", "snapshotVersion_", "resumeToken_", "lastListenSequenceNumber_", Target.QueryTarget.class, Target.DocumentsTarget.class, "lastLimboFreeSnapshotVersion_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<Target> parser = PARSER;
                if (parser == null) {
                    synchronized (Target.class) {
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
        Target defaultInstance = new Target();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(Target.class, defaultInstance);
    }

    public static Target getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Target> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
