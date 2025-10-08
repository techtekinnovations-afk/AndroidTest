package com.google.firestore.v1;

import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class DocumentMask extends GeneratedMessageLite<DocumentMask, Builder> implements DocumentMaskOrBuilder {
    /* access modifiers changed from: private */
    public static final DocumentMask DEFAULT_INSTANCE;
    public static final int FIELD_PATHS_FIELD_NUMBER = 1;
    private static volatile Parser<DocumentMask> PARSER;
    private Internal.ProtobufList<String> fieldPaths_ = GeneratedMessageLite.emptyProtobufList();

    private DocumentMask() {
    }

    public List<String> getFieldPathsList() {
        return this.fieldPaths_;
    }

    public int getFieldPathsCount() {
        return this.fieldPaths_.size();
    }

    public String getFieldPaths(int index) {
        return (String) this.fieldPaths_.get(index);
    }

    public ByteString getFieldPathsBytes(int index) {
        return ByteString.copyFromUtf8((String) this.fieldPaths_.get(index));
    }

    private void ensureFieldPathsIsMutable() {
        Internal.ProtobufList<String> tmp = this.fieldPaths_;
        if (!tmp.isModifiable()) {
            this.fieldPaths_ = GeneratedMessageLite.mutableCopy(tmp);
        }
    }

    /* access modifiers changed from: private */
    public void setFieldPaths(int index, String value) {
        Class<?> cls = value.getClass();
        ensureFieldPathsIsMutable();
        this.fieldPaths_.set(index, value);
    }

    /* access modifiers changed from: private */
    public void addFieldPaths(String value) {
        Class<?> cls = value.getClass();
        ensureFieldPathsIsMutable();
        this.fieldPaths_.add(value);
    }

    /* access modifiers changed from: private */
    public void addAllFieldPaths(Iterable<String> values) {
        ensureFieldPathsIsMutable();
        AbstractMessageLite.addAll(values, this.fieldPaths_);
    }

    /* access modifiers changed from: private */
    public void clearFieldPaths() {
        this.fieldPaths_ = GeneratedMessageLite.emptyProtobufList();
    }

    /* access modifiers changed from: private */
    public void addFieldPathsBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        ensureFieldPathsIsMutable();
        this.fieldPaths_.add(value.toStringUtf8());
    }

    public static DocumentMask parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (DocumentMask) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static DocumentMask parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (DocumentMask) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static DocumentMask parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (DocumentMask) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static DocumentMask parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (DocumentMask) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static DocumentMask parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (DocumentMask) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static DocumentMask parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (DocumentMask) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static DocumentMask parseFrom(InputStream input) throws IOException {
        return (DocumentMask) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static DocumentMask parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (DocumentMask) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static DocumentMask parseDelimitedFrom(InputStream input) throws IOException {
        return (DocumentMask) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static DocumentMask parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (DocumentMask) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static DocumentMask parseFrom(CodedInputStream input) throws IOException {
        return (DocumentMask) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static DocumentMask parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (DocumentMask) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(DocumentMask prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<DocumentMask, Builder> implements DocumentMaskOrBuilder {
        private Builder() {
            super(DocumentMask.DEFAULT_INSTANCE);
        }

        public List<String> getFieldPathsList() {
            return Collections.unmodifiableList(((DocumentMask) this.instance).getFieldPathsList());
        }

        public int getFieldPathsCount() {
            return ((DocumentMask) this.instance).getFieldPathsCount();
        }

        public String getFieldPaths(int index) {
            return ((DocumentMask) this.instance).getFieldPaths(index);
        }

        public ByteString getFieldPathsBytes(int index) {
            return ((DocumentMask) this.instance).getFieldPathsBytes(index);
        }

        public Builder setFieldPaths(int index, String value) {
            copyOnWrite();
            ((DocumentMask) this.instance).setFieldPaths(index, value);
            return this;
        }

        public Builder addFieldPaths(String value) {
            copyOnWrite();
            ((DocumentMask) this.instance).addFieldPaths(value);
            return this;
        }

        public Builder addAllFieldPaths(Iterable<String> values) {
            copyOnWrite();
            ((DocumentMask) this.instance).addAllFieldPaths(values);
            return this;
        }

        public Builder clearFieldPaths() {
            copyOnWrite();
            ((DocumentMask) this.instance).clearFieldPaths();
            return this;
        }

        public Builder addFieldPathsBytes(ByteString value) {
            copyOnWrite();
            ((DocumentMask) this.instance).addFieldPathsBytes(value);
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new DocumentMask();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001Ț", new Object[]{"fieldPaths_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<DocumentMask> parser = PARSER;
                if (parser == null) {
                    synchronized (DocumentMask.class) {
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
        DocumentMask defaultInstance = new DocumentMask();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(DocumentMask.class, defaultInstance);
    }

    public static DocumentMask getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<DocumentMask> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
