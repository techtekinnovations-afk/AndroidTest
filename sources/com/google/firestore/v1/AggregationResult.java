package com.google.firestore.v1;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MapEntryLite;
import com.google.protobuf.MapFieldLite;
import com.google.protobuf.Parser;
import com.google.protobuf.WireFormat;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Map;

public final class AggregationResult extends GeneratedMessageLite<AggregationResult, Builder> implements AggregationResultOrBuilder {
    public static final int AGGREGATE_FIELDS_FIELD_NUMBER = 2;
    /* access modifiers changed from: private */
    public static final AggregationResult DEFAULT_INSTANCE;
    private static volatile Parser<AggregationResult> PARSER;
    private MapFieldLite<String, Value> aggregateFields_ = MapFieldLite.emptyMapField();

    private AggregationResult() {
    }

    private static final class AggregateFieldsDefaultEntryHolder {
        static final MapEntryLite<String, Value> defaultEntry = MapEntryLite.newDefaultInstance(WireFormat.FieldType.STRING, "", WireFormat.FieldType.MESSAGE, Value.getDefaultInstance());

        private AggregateFieldsDefaultEntryHolder() {
        }
    }

    private MapFieldLite<String, Value> internalGetAggregateFields() {
        return this.aggregateFields_;
    }

    private MapFieldLite<String, Value> internalGetMutableAggregateFields() {
        if (!this.aggregateFields_.isMutable()) {
            this.aggregateFields_ = this.aggregateFields_.mutableCopy();
        }
        return this.aggregateFields_;
    }

    public int getAggregateFieldsCount() {
        return internalGetAggregateFields().size();
    }

    public boolean containsAggregateFields(String key) {
        Class<?> cls = key.getClass();
        return internalGetAggregateFields().containsKey(key);
    }

    @Deprecated
    public Map<String, Value> getAggregateFields() {
        return getAggregateFieldsMap();
    }

    public Map<String, Value> getAggregateFieldsMap() {
        return Collections.unmodifiableMap(internalGetAggregateFields());
    }

    public Value getAggregateFieldsOrDefault(String key, Value defaultValue) {
        Class<?> cls = key.getClass();
        Map<String, Value> map = internalGetAggregateFields();
        return map.containsKey(key) ? map.get(key) : defaultValue;
    }

    public Value getAggregateFieldsOrThrow(String key) {
        Class<?> cls = key.getClass();
        Map<String, Value> map = internalGetAggregateFields();
        if (map.containsKey(key)) {
            return map.get(key);
        }
        throw new IllegalArgumentException();
    }

    /* access modifiers changed from: private */
    public Map<String, Value> getMutableAggregateFieldsMap() {
        return internalGetMutableAggregateFields();
    }

    public static AggregationResult parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (AggregationResult) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AggregationResult parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AggregationResult) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AggregationResult parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (AggregationResult) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AggregationResult parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AggregationResult) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AggregationResult parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (AggregationResult) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static AggregationResult parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (AggregationResult) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static AggregationResult parseFrom(InputStream input) throws IOException {
        return (AggregationResult) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AggregationResult parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AggregationResult) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AggregationResult parseDelimitedFrom(InputStream input) throws IOException {
        return (AggregationResult) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static AggregationResult parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AggregationResult) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static AggregationResult parseFrom(CodedInputStream input) throws IOException {
        return (AggregationResult) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static AggregationResult parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (AggregationResult) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(AggregationResult prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<AggregationResult, Builder> implements AggregationResultOrBuilder {
        private Builder() {
            super(AggregationResult.DEFAULT_INSTANCE);
        }

        public int getAggregateFieldsCount() {
            return ((AggregationResult) this.instance).getAggregateFieldsMap().size();
        }

        public boolean containsAggregateFields(String key) {
            Class<?> cls = key.getClass();
            return ((AggregationResult) this.instance).getAggregateFieldsMap().containsKey(key);
        }

        public Builder clearAggregateFields() {
            copyOnWrite();
            ((AggregationResult) this.instance).getMutableAggregateFieldsMap().clear();
            return this;
        }

        public Builder removeAggregateFields(String key) {
            Class<?> cls = key.getClass();
            copyOnWrite();
            ((AggregationResult) this.instance).getMutableAggregateFieldsMap().remove(key);
            return this;
        }

        @Deprecated
        public Map<String, Value> getAggregateFields() {
            return getAggregateFieldsMap();
        }

        public Map<String, Value> getAggregateFieldsMap() {
            return Collections.unmodifiableMap(((AggregationResult) this.instance).getAggregateFieldsMap());
        }

        public Value getAggregateFieldsOrDefault(String key, Value defaultValue) {
            Class<?> cls = key.getClass();
            Map<String, Value> map = ((AggregationResult) this.instance).getAggregateFieldsMap();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        public Value getAggregateFieldsOrThrow(String key) {
            Class<?> cls = key.getClass();
            Map<String, Value> map = ((AggregationResult) this.instance).getAggregateFieldsMap();
            if (map.containsKey(key)) {
                return map.get(key);
            }
            throw new IllegalArgumentException();
        }

        public Builder putAggregateFields(String key, Value value) {
            Class<?> cls = key.getClass();
            Class<?> cls2 = value.getClass();
            copyOnWrite();
            ((AggregationResult) this.instance).getMutableAggregateFieldsMap().put(key, value);
            return this;
        }

        public Builder putAllAggregateFields(Map<String, Value> values) {
            copyOnWrite();
            ((AggregationResult) this.instance).getMutableAggregateFieldsMap().putAll(values);
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new AggregationResult();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0001\u0000\u0000\u0002\u0002\u0001\u0001\u0000\u0000\u00022", new Object[]{"aggregateFields_", AggregateFieldsDefaultEntryHolder.defaultEntry});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<AggregationResult> parser = PARSER;
                if (parser == null) {
                    synchronized (AggregationResult.class) {
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
        AggregationResult defaultInstance = new AggregationResult();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(AggregationResult.class, defaultInstance);
    }

    public static AggregationResult getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<AggregationResult> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
