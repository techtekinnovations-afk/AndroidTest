package com.google.firestore.v1;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.MapFieldLite;
import com.google.protobuf.Parser;
import com.google.protobuf.Timestamp;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Map;

public final class Document extends GeneratedMessageLite<Document, Builder> implements DocumentOrBuilder {
    public static final int CREATE_TIME_FIELD_NUMBER = 3;
    /* access modifiers changed from: private */
    public static final Document DEFAULT_INSTANCE;
    public static final int FIELDS_FIELD_NUMBER = 2;
    public static final int NAME_FIELD_NUMBER = 1;
    private static volatile Parser<Document> PARSER = null;
    public static final int UPDATE_TIME_FIELD_NUMBER = 4;
    private int bitField0_;
    private Timestamp createTime_;
    private MapFieldLite<String, Value> fields_ = MapFieldLite.emptyMapField();
    private String name_ = "";
    private Timestamp updateTime_;

    private Document() {
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

    private static final class FieldsDefaultEntryHolder {
        static final MapEntryLite<String, Value> defaultEntry = MapEntryLite.newDefaultInstance(WireFormat.FieldType.STRING, "", WireFormat.FieldType.MESSAGE, Value.getDefaultInstance());

        private FieldsDefaultEntryHolder() {
        }
    }

    private MapFieldLite<String, Value> internalGetFields() {
        return this.fields_;
    }

    private MapFieldLite<String, Value> internalGetMutableFields() {
        if (!this.fields_.isMutable()) {
            this.fields_ = this.fields_.mutableCopy();
        }
        return this.fields_;
    }

    public int getFieldsCount() {
        return internalGetFields().size();
    }

    public boolean containsFields(String key) {
        Class<?> cls = key.getClass();
        return internalGetFields().containsKey(key);
    }

    @Deprecated
    public Map<String, Value> getFields() {
        return getFieldsMap();
    }

    public Map<String, Value> getFieldsMap() {
        return Collections.unmodifiableMap(internalGetFields());
    }

    public Value getFieldsOrDefault(String key, Value defaultValue) {
        Class<?> cls = key.getClass();
        Map<String, Value> map = internalGetFields();
        return map.containsKey(key) ? map.get(key) : defaultValue;
    }

    public Value getFieldsOrThrow(String key) {
        Class<?> cls = key.getClass();
        Map<String, Value> map = internalGetFields();
        if (map.containsKey(key)) {
            return map.get(key);
        }
        throw new IllegalArgumentException();
    }

    /* access modifiers changed from: private */
    public Map<String, Value> getMutableFieldsMap() {
        return internalGetMutableFields();
    }

    public boolean hasCreateTime() {
        return (this.bitField0_ & 1) != 0;
    }

    public Timestamp getCreateTime() {
        return this.createTime_ == null ? Timestamp.getDefaultInstance() : this.createTime_;
    }

    /* access modifiers changed from: private */
    public void setCreateTime(Timestamp value) {
        value.getClass();
        this.createTime_ = value;
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void mergeCreateTime(Timestamp value) {
        value.getClass();
        if (this.createTime_ == null || this.createTime_ == Timestamp.getDefaultInstance()) {
            this.createTime_ = value;
        } else {
            this.createTime_ = (Timestamp) ((Timestamp.Builder) Timestamp.newBuilder(this.createTime_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void clearCreateTime() {
        this.createTime_ = null;
        this.bitField0_ &= -2;
    }

    public boolean hasUpdateTime() {
        return (this.bitField0_ & 2) != 0;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime_ == null ? Timestamp.getDefaultInstance() : this.updateTime_;
    }

    /* access modifiers changed from: private */
    public void setUpdateTime(Timestamp value) {
        value.getClass();
        this.updateTime_ = value;
        this.bitField0_ |= 2;
    }

    /* access modifiers changed from: private */
    public void mergeUpdateTime(Timestamp value) {
        value.getClass();
        if (this.updateTime_ == null || this.updateTime_ == Timestamp.getDefaultInstance()) {
            this.updateTime_ = value;
        } else {
            this.updateTime_ = (Timestamp) ((Timestamp.Builder) Timestamp.newBuilder(this.updateTime_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 2;
    }

    /* access modifiers changed from: private */
    public void clearUpdateTime() {
        this.updateTime_ = null;
        this.bitField0_ &= -3;
    }

    public static Document parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (Document) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Document parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Document) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Document parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (Document) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Document parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Document) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Document parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (Document) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Document parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Document) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Document parseFrom(InputStream input) throws IOException {
        return (Document) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static Document parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Document) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Document parseDelimitedFrom(InputStream input) throws IOException {
        return (Document) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static Document parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Document) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Document parseFrom(CodedInputStream input) throws IOException {
        return (Document) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static Document parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Document) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(Document prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Document, Builder> implements DocumentOrBuilder {
        private Builder() {
            super(Document.DEFAULT_INSTANCE);
        }

        public String getName() {
            return ((Document) this.instance).getName();
        }

        public ByteString getNameBytes() {
            return ((Document) this.instance).getNameBytes();
        }

        public Builder setName(String value) {
            copyOnWrite();
            ((Document) this.instance).setName(value);
            return this;
        }

        public Builder clearName() {
            copyOnWrite();
            ((Document) this.instance).clearName();
            return this;
        }

        public Builder setNameBytes(ByteString value) {
            copyOnWrite();
            ((Document) this.instance).setNameBytes(value);
            return this;
        }

        public int getFieldsCount() {
            return ((Document) this.instance).getFieldsMap().size();
        }

        public boolean containsFields(String key) {
            Class<?> cls = key.getClass();
            return ((Document) this.instance).getFieldsMap().containsKey(key);
        }

        public Builder clearFields() {
            copyOnWrite();
            ((Document) this.instance).getMutableFieldsMap().clear();
            return this;
        }

        public Builder removeFields(String key) {
            Class<?> cls = key.getClass();
            copyOnWrite();
            ((Document) this.instance).getMutableFieldsMap().remove(key);
            return this;
        }

        @Deprecated
        public Map<String, Value> getFields() {
            return getFieldsMap();
        }

        public Map<String, Value> getFieldsMap() {
            return Collections.unmodifiableMap(((Document) this.instance).getFieldsMap());
        }

        public Value getFieldsOrDefault(String key, Value defaultValue) {
            Class<?> cls = key.getClass();
            Map<String, Value> map = ((Document) this.instance).getFieldsMap();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        public Value getFieldsOrThrow(String key) {
            Class<?> cls = key.getClass();
            Map<String, Value> map = ((Document) this.instance).getFieldsMap();
            if (map.containsKey(key)) {
                return map.get(key);
            }
            throw new IllegalArgumentException();
        }

        public Builder putFields(String key, Value value) {
            Class<?> cls = key.getClass();
            Class<?> cls2 = value.getClass();
            copyOnWrite();
            ((Document) this.instance).getMutableFieldsMap().put(key, value);
            return this;
        }

        public Builder putAllFields(Map<String, Value> values) {
            copyOnWrite();
            ((Document) this.instance).getMutableFieldsMap().putAll(values);
            return this;
        }

        public boolean hasCreateTime() {
            return ((Document) this.instance).hasCreateTime();
        }

        public Timestamp getCreateTime() {
            return ((Document) this.instance).getCreateTime();
        }

        public Builder setCreateTime(Timestamp value) {
            copyOnWrite();
            ((Document) this.instance).setCreateTime(value);
            return this;
        }

        public Builder setCreateTime(Timestamp.Builder builderForValue) {
            copyOnWrite();
            ((Document) this.instance).setCreateTime((Timestamp) builderForValue.build());
            return this;
        }

        public Builder mergeCreateTime(Timestamp value) {
            copyOnWrite();
            ((Document) this.instance).mergeCreateTime(value);
            return this;
        }

        public Builder clearCreateTime() {
            copyOnWrite();
            ((Document) this.instance).clearCreateTime();
            return this;
        }

        public boolean hasUpdateTime() {
            return ((Document) this.instance).hasUpdateTime();
        }

        public Timestamp getUpdateTime() {
            return ((Document) this.instance).getUpdateTime();
        }

        public Builder setUpdateTime(Timestamp value) {
            copyOnWrite();
            ((Document) this.instance).setUpdateTime(value);
            return this;
        }

        public Builder setUpdateTime(Timestamp.Builder builderForValue) {
            copyOnWrite();
            ((Document) this.instance).setUpdateTime((Timestamp) builderForValue.build());
            return this;
        }

        public Builder mergeUpdateTime(Timestamp value) {
            copyOnWrite();
            ((Document) this.instance).mergeUpdateTime(value);
            return this;
        }

        public Builder clearUpdateTime() {
            copyOnWrite();
            ((Document) this.instance).clearUpdateTime();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new Document();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0004\u0000\u0001\u0001\u0004\u0004\u0001\u0000\u0000\u0001Ȉ\u00022\u0003ဉ\u0000\u0004ဉ\u0001", new Object[]{"bitField0_", "name_", "fields_", FieldsDefaultEntryHolder.defaultEntry, "createTime_", "updateTime_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<Document> parser = PARSER;
                if (parser == null) {
                    synchronized (Document.class) {
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
        Document defaultInstance = new Document();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(Document.class, defaultInstance);
    }

    public static Document getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Document> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
