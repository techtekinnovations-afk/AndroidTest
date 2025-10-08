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

public final class MapValue extends GeneratedMessageLite<MapValue, Builder> implements MapValueOrBuilder {
    /* access modifiers changed from: private */
    public static final MapValue DEFAULT_INSTANCE;
    public static final int FIELDS_FIELD_NUMBER = 1;
    private static volatile Parser<MapValue> PARSER;
    private MapFieldLite<String, Value> fields_ = MapFieldLite.emptyMapField();

    private MapValue() {
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

    public static MapValue parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (MapValue) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static MapValue parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (MapValue) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static MapValue parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (MapValue) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static MapValue parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (MapValue) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static MapValue parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (MapValue) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static MapValue parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (MapValue) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static MapValue parseFrom(InputStream input) throws IOException {
        return (MapValue) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static MapValue parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (MapValue) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static MapValue parseDelimitedFrom(InputStream input) throws IOException {
        return (MapValue) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static MapValue parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (MapValue) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static MapValue parseFrom(CodedInputStream input) throws IOException {
        return (MapValue) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static MapValue parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (MapValue) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(MapValue prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<MapValue, Builder> implements MapValueOrBuilder {
        private Builder() {
            super(MapValue.DEFAULT_INSTANCE);
        }

        public int getFieldsCount() {
            return ((MapValue) this.instance).getFieldsMap().size();
        }

        public boolean containsFields(String key) {
            Class<?> cls = key.getClass();
            return ((MapValue) this.instance).getFieldsMap().containsKey(key);
        }

        public Builder clearFields() {
            copyOnWrite();
            ((MapValue) this.instance).getMutableFieldsMap().clear();
            return this;
        }

        public Builder removeFields(String key) {
            Class<?> cls = key.getClass();
            copyOnWrite();
            ((MapValue) this.instance).getMutableFieldsMap().remove(key);
            return this;
        }

        @Deprecated
        public Map<String, Value> getFields() {
            return getFieldsMap();
        }

        public Map<String, Value> getFieldsMap() {
            return Collections.unmodifiableMap(((MapValue) this.instance).getFieldsMap());
        }

        public Value getFieldsOrDefault(String key, Value defaultValue) {
            Class<?> cls = key.getClass();
            Map<String, Value> map = ((MapValue) this.instance).getFieldsMap();
            return map.containsKey(key) ? map.get(key) : defaultValue;
        }

        public Value getFieldsOrThrow(String key) {
            Class<?> cls = key.getClass();
            Map<String, Value> map = ((MapValue) this.instance).getFieldsMap();
            if (map.containsKey(key)) {
                return map.get(key);
            }
            throw new IllegalArgumentException();
        }

        public Builder putFields(String key, Value value) {
            Class<?> cls = key.getClass();
            Class<?> cls2 = value.getClass();
            copyOnWrite();
            ((MapValue) this.instance).getMutableFieldsMap().put(key, value);
            return this;
        }

        public Builder putAllFields(Map<String, Value> values) {
            copyOnWrite();
            ((MapValue) this.instance).getMutableFieldsMap().putAll(values);
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new MapValue();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u00012", new Object[]{"fields_", FieldsDefaultEntryHolder.defaultEntry});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<MapValue> parser = PARSER;
                if (parser == null) {
                    synchronized (MapValue.class) {
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
        MapValue defaultInstance = new MapValue();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(MapValue.class, defaultInstance);
    }

    public static MapValue getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<MapValue> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
