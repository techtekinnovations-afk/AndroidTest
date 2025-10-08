package com.google.api;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class UsageRule extends GeneratedMessageLite<UsageRule, Builder> implements UsageRuleOrBuilder {
    public static final int ALLOW_UNREGISTERED_CALLS_FIELD_NUMBER = 2;
    /* access modifiers changed from: private */
    public static final UsageRule DEFAULT_INSTANCE;
    private static volatile Parser<UsageRule> PARSER = null;
    public static final int SELECTOR_FIELD_NUMBER = 1;
    public static final int SKIP_SERVICE_CONTROL_FIELD_NUMBER = 3;
    private boolean allowUnregisteredCalls_;
    private String selector_ = "";
    private boolean skipServiceControl_;

    private UsageRule() {
    }

    public String getSelector() {
        return this.selector_;
    }

    public ByteString getSelectorBytes() {
        return ByteString.copyFromUtf8(this.selector_);
    }

    /* access modifiers changed from: private */
    public void setSelector(String value) {
        Class<?> cls = value.getClass();
        this.selector_ = value;
    }

    /* access modifiers changed from: private */
    public void clearSelector() {
        this.selector_ = getDefaultInstance().getSelector();
    }

    /* access modifiers changed from: private */
    public void setSelectorBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.selector_ = value.toStringUtf8();
    }

    public boolean getAllowUnregisteredCalls() {
        return this.allowUnregisteredCalls_;
    }

    /* access modifiers changed from: private */
    public void setAllowUnregisteredCalls(boolean value) {
        this.allowUnregisteredCalls_ = value;
    }

    /* access modifiers changed from: private */
    public void clearAllowUnregisteredCalls() {
        this.allowUnregisteredCalls_ = false;
    }

    public boolean getSkipServiceControl() {
        return this.skipServiceControl_;
    }

    /* access modifiers changed from: private */
    public void setSkipServiceControl(boolean value) {
        this.skipServiceControl_ = value;
    }

    /* access modifiers changed from: private */
    public void clearSkipServiceControl() {
        this.skipServiceControl_ = false;
    }

    public static UsageRule parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (UsageRule) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static UsageRule parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (UsageRule) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static UsageRule parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (UsageRule) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static UsageRule parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (UsageRule) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static UsageRule parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (UsageRule) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static UsageRule parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (UsageRule) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static UsageRule parseFrom(InputStream input) throws IOException {
        return (UsageRule) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static UsageRule parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (UsageRule) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static UsageRule parseDelimitedFrom(InputStream input) throws IOException {
        return (UsageRule) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static UsageRule parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (UsageRule) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static UsageRule parseFrom(CodedInputStream input) throws IOException {
        return (UsageRule) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static UsageRule parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (UsageRule) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(UsageRule prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<UsageRule, Builder> implements UsageRuleOrBuilder {
        private Builder() {
            super(UsageRule.DEFAULT_INSTANCE);
        }

        public String getSelector() {
            return ((UsageRule) this.instance).getSelector();
        }

        public ByteString getSelectorBytes() {
            return ((UsageRule) this.instance).getSelectorBytes();
        }

        public Builder setSelector(String value) {
            copyOnWrite();
            ((UsageRule) this.instance).setSelector(value);
            return this;
        }

        public Builder clearSelector() {
            copyOnWrite();
            ((UsageRule) this.instance).clearSelector();
            return this;
        }

        public Builder setSelectorBytes(ByteString value) {
            copyOnWrite();
            ((UsageRule) this.instance).setSelectorBytes(value);
            return this;
        }

        public boolean getAllowUnregisteredCalls() {
            return ((UsageRule) this.instance).getAllowUnregisteredCalls();
        }

        public Builder setAllowUnregisteredCalls(boolean value) {
            copyOnWrite();
            ((UsageRule) this.instance).setAllowUnregisteredCalls(value);
            return this;
        }

        public Builder clearAllowUnregisteredCalls() {
            copyOnWrite();
            ((UsageRule) this.instance).clearAllowUnregisteredCalls();
            return this;
        }

        public boolean getSkipServiceControl() {
            return ((UsageRule) this.instance).getSkipServiceControl();
        }

        public Builder setSkipServiceControl(boolean value) {
            copyOnWrite();
            ((UsageRule) this.instance).setSkipServiceControl(value);
            return this;
        }

        public Builder clearSkipServiceControl() {
            copyOnWrite();
            ((UsageRule) this.instance).clearSkipServiceControl();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new UsageRule();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001Ȉ\u0002\u0007\u0003\u0007", new Object[]{"selector_", "allowUnregisteredCalls_", "skipServiceControl_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<UsageRule> parser = PARSER;
                if (parser == null) {
                    synchronized (UsageRule.class) {
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
        UsageRule defaultInstance = new UsageRule();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(UsageRule.class, defaultInstance);
    }

    public static UsageRule getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<UsageRule> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
