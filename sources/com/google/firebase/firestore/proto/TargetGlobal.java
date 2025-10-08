package com.google.firebase.firestore.proto;

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

public final class TargetGlobal extends GeneratedMessageLite<TargetGlobal, Builder> implements TargetGlobalOrBuilder {
    /* access modifiers changed from: private */
    public static final TargetGlobal DEFAULT_INSTANCE;
    public static final int HIGHEST_LISTEN_SEQUENCE_NUMBER_FIELD_NUMBER = 2;
    public static final int HIGHEST_TARGET_ID_FIELD_NUMBER = 1;
    public static final int LAST_REMOTE_SNAPSHOT_VERSION_FIELD_NUMBER = 3;
    private static volatile Parser<TargetGlobal> PARSER = null;
    public static final int TARGET_COUNT_FIELD_NUMBER = 4;
    private int bitField0_;
    private long highestListenSequenceNumber_;
    private int highestTargetId_;
    private Timestamp lastRemoteSnapshotVersion_;
    private int targetCount_;

    private TargetGlobal() {
    }

    public int getHighestTargetId() {
        return this.highestTargetId_;
    }

    /* access modifiers changed from: private */
    public void setHighestTargetId(int value) {
        this.highestTargetId_ = value;
    }

    /* access modifiers changed from: private */
    public void clearHighestTargetId() {
        this.highestTargetId_ = 0;
    }

    public long getHighestListenSequenceNumber() {
        return this.highestListenSequenceNumber_;
    }

    /* access modifiers changed from: private */
    public void setHighestListenSequenceNumber(long value) {
        this.highestListenSequenceNumber_ = value;
    }

    /* access modifiers changed from: private */
    public void clearHighestListenSequenceNumber() {
        this.highestListenSequenceNumber_ = 0;
    }

    public boolean hasLastRemoteSnapshotVersion() {
        return (this.bitField0_ & 1) != 0;
    }

    public Timestamp getLastRemoteSnapshotVersion() {
        return this.lastRemoteSnapshotVersion_ == null ? Timestamp.getDefaultInstance() : this.lastRemoteSnapshotVersion_;
    }

    /* access modifiers changed from: private */
    public void setLastRemoteSnapshotVersion(Timestamp value) {
        value.getClass();
        this.lastRemoteSnapshotVersion_ = value;
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void mergeLastRemoteSnapshotVersion(Timestamp value) {
        value.getClass();
        if (this.lastRemoteSnapshotVersion_ == null || this.lastRemoteSnapshotVersion_ == Timestamp.getDefaultInstance()) {
            this.lastRemoteSnapshotVersion_ = value;
        } else {
            this.lastRemoteSnapshotVersion_ = (Timestamp) ((Timestamp.Builder) Timestamp.newBuilder(this.lastRemoteSnapshotVersion_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void clearLastRemoteSnapshotVersion() {
        this.lastRemoteSnapshotVersion_ = null;
        this.bitField0_ &= -2;
    }

    public int getTargetCount() {
        return this.targetCount_;
    }

    /* access modifiers changed from: private */
    public void setTargetCount(int value) {
        this.targetCount_ = value;
    }

    /* access modifiers changed from: private */
    public void clearTargetCount() {
        this.targetCount_ = 0;
    }

    public static TargetGlobal parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (TargetGlobal) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static TargetGlobal parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (TargetGlobal) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static TargetGlobal parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (TargetGlobal) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static TargetGlobal parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (TargetGlobal) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static TargetGlobal parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (TargetGlobal) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static TargetGlobal parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (TargetGlobal) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static TargetGlobal parseFrom(InputStream input) throws IOException {
        return (TargetGlobal) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static TargetGlobal parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (TargetGlobal) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static TargetGlobal parseDelimitedFrom(InputStream input) throws IOException {
        return (TargetGlobal) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static TargetGlobal parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (TargetGlobal) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static TargetGlobal parseFrom(CodedInputStream input) throws IOException {
        return (TargetGlobal) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static TargetGlobal parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (TargetGlobal) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(TargetGlobal prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<TargetGlobal, Builder> implements TargetGlobalOrBuilder {
        private Builder() {
            super(TargetGlobal.DEFAULT_INSTANCE);
        }

        public int getHighestTargetId() {
            return ((TargetGlobal) this.instance).getHighestTargetId();
        }

        public Builder setHighestTargetId(int value) {
            copyOnWrite();
            ((TargetGlobal) this.instance).setHighestTargetId(value);
            return this;
        }

        public Builder clearHighestTargetId() {
            copyOnWrite();
            ((TargetGlobal) this.instance).clearHighestTargetId();
            return this;
        }

        public long getHighestListenSequenceNumber() {
            return ((TargetGlobal) this.instance).getHighestListenSequenceNumber();
        }

        public Builder setHighestListenSequenceNumber(long value) {
            copyOnWrite();
            ((TargetGlobal) this.instance).setHighestListenSequenceNumber(value);
            return this;
        }

        public Builder clearHighestListenSequenceNumber() {
            copyOnWrite();
            ((TargetGlobal) this.instance).clearHighestListenSequenceNumber();
            return this;
        }

        public boolean hasLastRemoteSnapshotVersion() {
            return ((TargetGlobal) this.instance).hasLastRemoteSnapshotVersion();
        }

        public Timestamp getLastRemoteSnapshotVersion() {
            return ((TargetGlobal) this.instance).getLastRemoteSnapshotVersion();
        }

        public Builder setLastRemoteSnapshotVersion(Timestamp value) {
            copyOnWrite();
            ((TargetGlobal) this.instance).setLastRemoteSnapshotVersion(value);
            return this;
        }

        public Builder setLastRemoteSnapshotVersion(Timestamp.Builder builderForValue) {
            copyOnWrite();
            ((TargetGlobal) this.instance).setLastRemoteSnapshotVersion((Timestamp) builderForValue.build());
            return this;
        }

        public Builder mergeLastRemoteSnapshotVersion(Timestamp value) {
            copyOnWrite();
            ((TargetGlobal) this.instance).mergeLastRemoteSnapshotVersion(value);
            return this;
        }

        public Builder clearLastRemoteSnapshotVersion() {
            copyOnWrite();
            ((TargetGlobal) this.instance).clearLastRemoteSnapshotVersion();
            return this;
        }

        public int getTargetCount() {
            return ((TargetGlobal) this.instance).getTargetCount();
        }

        public Builder setTargetCount(int value) {
            copyOnWrite();
            ((TargetGlobal) this.instance).setTargetCount(value);
            return this;
        }

        public Builder clearTargetCount() {
            copyOnWrite();
            ((TargetGlobal) this.instance).clearTargetCount();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new TargetGlobal();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0004\u0002\u0002\u0003ဉ\u0000\u0004\u0004", new Object[]{"bitField0_", "highestTargetId_", "highestListenSequenceNumber_", "lastRemoteSnapshotVersion_", "targetCount_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<TargetGlobal> parser = PARSER;
                if (parser == null) {
                    synchronized (TargetGlobal.class) {
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
        TargetGlobal defaultInstance = new TargetGlobal();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(TargetGlobal.class, defaultInstance);
    }

    public static TargetGlobal getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<TargetGlobal> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
