package com.google.firestore.v1;

import com.google.firestore.v1.ArrayValue;
import com.google.firestore.v1.MapValue;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.NullValue;
import com.google.protobuf.Parser;
import com.google.protobuf.Timestamp;
import com.google.type.LatLng;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Value extends GeneratedMessageLite<Value, Builder> implements ValueOrBuilder {
    public static final int ARRAY_VALUE_FIELD_NUMBER = 9;
    public static final int BOOLEAN_VALUE_FIELD_NUMBER = 1;
    public static final int BYTES_VALUE_FIELD_NUMBER = 18;
    /* access modifiers changed from: private */
    public static final Value DEFAULT_INSTANCE;
    public static final int DOUBLE_VALUE_FIELD_NUMBER = 3;
    public static final int GEO_POINT_VALUE_FIELD_NUMBER = 8;
    public static final int INTEGER_VALUE_FIELD_NUMBER = 2;
    public static final int MAP_VALUE_FIELD_NUMBER = 6;
    public static final int NULL_VALUE_FIELD_NUMBER = 11;
    private static volatile Parser<Value> PARSER = null;
    public static final int REFERENCE_VALUE_FIELD_NUMBER = 5;
    public static final int STRING_VALUE_FIELD_NUMBER = 17;
    public static final int TIMESTAMP_VALUE_FIELD_NUMBER = 10;
    private int valueTypeCase_ = 0;
    private Object valueType_;

    private Value() {
    }

    public enum ValueTypeCase {
        NULL_VALUE(11),
        BOOLEAN_VALUE(1),
        INTEGER_VALUE(2),
        DOUBLE_VALUE(3),
        TIMESTAMP_VALUE(10),
        STRING_VALUE(17),
        BYTES_VALUE(18),
        REFERENCE_VALUE(5),
        GEO_POINT_VALUE(8),
        ARRAY_VALUE(9),
        MAP_VALUE(6),
        VALUETYPE_NOT_SET(0);
        
        private final int value;

        private ValueTypeCase(int value2) {
            this.value = value2;
        }

        @Deprecated
        public static ValueTypeCase valueOf(int value2) {
            return forNumber(value2);
        }

        public static ValueTypeCase forNumber(int value2) {
            switch (value2) {
                case 0:
                    return VALUETYPE_NOT_SET;
                case 1:
                    return BOOLEAN_VALUE;
                case 2:
                    return INTEGER_VALUE;
                case 3:
                    return DOUBLE_VALUE;
                case 5:
                    return REFERENCE_VALUE;
                case 6:
                    return MAP_VALUE;
                case 8:
                    return GEO_POINT_VALUE;
                case 9:
                    return ARRAY_VALUE;
                case 10:
                    return TIMESTAMP_VALUE;
                case 11:
                    return NULL_VALUE;
                case 17:
                    return STRING_VALUE;
                case 18:
                    return BYTES_VALUE;
                default:
                    return null;
            }
        }

        public int getNumber() {
            return this.value;
        }
    }

    public ValueTypeCase getValueTypeCase() {
        return ValueTypeCase.forNumber(this.valueTypeCase_);
    }

    /* access modifiers changed from: private */
    public void clearValueType() {
        this.valueTypeCase_ = 0;
        this.valueType_ = null;
    }

    public boolean hasNullValue() {
        return this.valueTypeCase_ == 11;
    }

    public int getNullValueValue() {
        if (this.valueTypeCase_ == 11) {
            return ((Integer) this.valueType_).intValue();
        }
        return 0;
    }

    public NullValue getNullValue() {
        if (this.valueTypeCase_ != 11) {
            return NullValue.NULL_VALUE;
        }
        NullValue result = NullValue.forNumber(((Integer) this.valueType_).intValue());
        return result == null ? NullValue.UNRECOGNIZED : result;
    }

    /* access modifiers changed from: private */
    public void setNullValueValue(int value) {
        this.valueTypeCase_ = 11;
        this.valueType_ = Integer.valueOf(value);
    }

    /* access modifiers changed from: private */
    public void setNullValue(NullValue value) {
        this.valueType_ = Integer.valueOf(value.getNumber());
        this.valueTypeCase_ = 11;
    }

    /* access modifiers changed from: private */
    public void clearNullValue() {
        if (this.valueTypeCase_ == 11) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    public boolean hasBooleanValue() {
        return this.valueTypeCase_ == 1;
    }

    public boolean getBooleanValue() {
        if (this.valueTypeCase_ == 1) {
            return ((Boolean) this.valueType_).booleanValue();
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void setBooleanValue(boolean value) {
        this.valueTypeCase_ = 1;
        this.valueType_ = Boolean.valueOf(value);
    }

    /* access modifiers changed from: private */
    public void clearBooleanValue() {
        if (this.valueTypeCase_ == 1) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    public boolean hasIntegerValue() {
        return this.valueTypeCase_ == 2;
    }

    public long getIntegerValue() {
        if (this.valueTypeCase_ == 2) {
            return ((Long) this.valueType_).longValue();
        }
        return 0;
    }

    /* access modifiers changed from: private */
    public void setIntegerValue(long value) {
        this.valueTypeCase_ = 2;
        this.valueType_ = Long.valueOf(value);
    }

    /* access modifiers changed from: private */
    public void clearIntegerValue() {
        if (this.valueTypeCase_ == 2) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    public boolean hasDoubleValue() {
        return this.valueTypeCase_ == 3;
    }

    public double getDoubleValue() {
        if (this.valueTypeCase_ == 3) {
            return ((Double) this.valueType_).doubleValue();
        }
        return 0.0d;
    }

    /* access modifiers changed from: private */
    public void setDoubleValue(double value) {
        this.valueTypeCase_ = 3;
        this.valueType_ = Double.valueOf(value);
    }

    /* access modifiers changed from: private */
    public void clearDoubleValue() {
        if (this.valueTypeCase_ == 3) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    public boolean hasTimestampValue() {
        return this.valueTypeCase_ == 10;
    }

    public Timestamp getTimestampValue() {
        if (this.valueTypeCase_ == 10) {
            return (Timestamp) this.valueType_;
        }
        return Timestamp.getDefaultInstance();
    }

    /* access modifiers changed from: private */
    public void setTimestampValue(Timestamp value) {
        value.getClass();
        this.valueType_ = value;
        this.valueTypeCase_ = 10;
    }

    /* access modifiers changed from: private */
    public void mergeTimestampValue(Timestamp value) {
        value.getClass();
        if (this.valueTypeCase_ != 10 || this.valueType_ == Timestamp.getDefaultInstance()) {
            this.valueType_ = value;
        } else {
            this.valueType_ = ((Timestamp.Builder) Timestamp.newBuilder((Timestamp) this.valueType_).mergeFrom(value)).buildPartial();
        }
        this.valueTypeCase_ = 10;
    }

    /* access modifiers changed from: private */
    public void clearTimestampValue() {
        if (this.valueTypeCase_ == 10) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    public boolean hasStringValue() {
        return this.valueTypeCase_ == 17;
    }

    public String getStringValue() {
        if (this.valueTypeCase_ == 17) {
            return (String) this.valueType_;
        }
        return "";
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.protobuf.ByteString getStringValueBytes() {
        /*
            r3 = this;
            java.lang.String r0 = ""
            int r1 = r3.valueTypeCase_
            r2 = 17
            if (r1 != r2) goto L_0x000d
            java.lang.Object r1 = r3.valueType_
            r0 = r1
            java.lang.String r0 = (java.lang.String) r0
        L_0x000d:
            com.google.protobuf.ByteString r1 = com.google.protobuf.ByteString.copyFromUtf8(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firestore.v1.Value.getStringValueBytes():com.google.protobuf.ByteString");
    }

    /* access modifiers changed from: private */
    public void setStringValue(String value) {
        Class<?> cls = value.getClass();
        this.valueTypeCase_ = 17;
        this.valueType_ = value;
    }

    /* access modifiers changed from: private */
    public void clearStringValue() {
        if (this.valueTypeCase_ == 17) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    /* access modifiers changed from: private */
    public void setStringValueBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.valueType_ = value.toStringUtf8();
        this.valueTypeCase_ = 17;
    }

    public boolean hasBytesValue() {
        return this.valueTypeCase_ == 18;
    }

    public ByteString getBytesValue() {
        if (this.valueTypeCase_ == 18) {
            return (ByteString) this.valueType_;
        }
        return ByteString.EMPTY;
    }

    /* access modifiers changed from: private */
    public void setBytesValue(ByteString value) {
        Class<?> cls = value.getClass();
        this.valueTypeCase_ = 18;
        this.valueType_ = value;
    }

    /* access modifiers changed from: private */
    public void clearBytesValue() {
        if (this.valueTypeCase_ == 18) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    public boolean hasReferenceValue() {
        return this.valueTypeCase_ == 5;
    }

    public String getReferenceValue() {
        if (this.valueTypeCase_ == 5) {
            return (String) this.valueType_;
        }
        return "";
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.protobuf.ByteString getReferenceValueBytes() {
        /*
            r3 = this;
            java.lang.String r0 = ""
            int r1 = r3.valueTypeCase_
            r2 = 5
            if (r1 != r2) goto L_0x000c
            java.lang.Object r1 = r3.valueType_
            r0 = r1
            java.lang.String r0 = (java.lang.String) r0
        L_0x000c:
            com.google.protobuf.ByteString r1 = com.google.protobuf.ByteString.copyFromUtf8(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firestore.v1.Value.getReferenceValueBytes():com.google.protobuf.ByteString");
    }

    /* access modifiers changed from: private */
    public void setReferenceValue(String value) {
        Class<?> cls = value.getClass();
        this.valueTypeCase_ = 5;
        this.valueType_ = value;
    }

    /* access modifiers changed from: private */
    public void clearReferenceValue() {
        if (this.valueTypeCase_ == 5) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    /* access modifiers changed from: private */
    public void setReferenceValueBytes(ByteString value) {
        checkByteStringIsUtf8(value);
        this.valueType_ = value.toStringUtf8();
        this.valueTypeCase_ = 5;
    }

    public boolean hasGeoPointValue() {
        return this.valueTypeCase_ == 8;
    }

    public LatLng getGeoPointValue() {
        if (this.valueTypeCase_ == 8) {
            return (LatLng) this.valueType_;
        }
        return LatLng.getDefaultInstance();
    }

    /* access modifiers changed from: private */
    public void setGeoPointValue(LatLng value) {
        value.getClass();
        this.valueType_ = value;
        this.valueTypeCase_ = 8;
    }

    /* access modifiers changed from: private */
    public void mergeGeoPointValue(LatLng value) {
        value.getClass();
        if (this.valueTypeCase_ != 8 || this.valueType_ == LatLng.getDefaultInstance()) {
            this.valueType_ = value;
        } else {
            this.valueType_ = ((LatLng.Builder) LatLng.newBuilder((LatLng) this.valueType_).mergeFrom(value)).buildPartial();
        }
        this.valueTypeCase_ = 8;
    }

    /* access modifiers changed from: private */
    public void clearGeoPointValue() {
        if (this.valueTypeCase_ == 8) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    public boolean hasArrayValue() {
        return this.valueTypeCase_ == 9;
    }

    public ArrayValue getArrayValue() {
        if (this.valueTypeCase_ == 9) {
            return (ArrayValue) this.valueType_;
        }
        return ArrayValue.getDefaultInstance();
    }

    /* access modifiers changed from: private */
    public void setArrayValue(ArrayValue value) {
        value.getClass();
        this.valueType_ = value;
        this.valueTypeCase_ = 9;
    }

    /* access modifiers changed from: private */
    public void mergeArrayValue(ArrayValue value) {
        value.getClass();
        if (this.valueTypeCase_ != 9 || this.valueType_ == ArrayValue.getDefaultInstance()) {
            this.valueType_ = value;
        } else {
            this.valueType_ = ((ArrayValue.Builder) ArrayValue.newBuilder((ArrayValue) this.valueType_).mergeFrom(value)).buildPartial();
        }
        this.valueTypeCase_ = 9;
    }

    /* access modifiers changed from: private */
    public void clearArrayValue() {
        if (this.valueTypeCase_ == 9) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    public boolean hasMapValue() {
        return this.valueTypeCase_ == 6;
    }

    public MapValue getMapValue() {
        if (this.valueTypeCase_ == 6) {
            return (MapValue) this.valueType_;
        }
        return MapValue.getDefaultInstance();
    }

    /* access modifiers changed from: private */
    public void setMapValue(MapValue value) {
        value.getClass();
        this.valueType_ = value;
        this.valueTypeCase_ = 6;
    }

    /* access modifiers changed from: private */
    public void mergeMapValue(MapValue value) {
        value.getClass();
        if (this.valueTypeCase_ != 6 || this.valueType_ == MapValue.getDefaultInstance()) {
            this.valueType_ = value;
        } else {
            this.valueType_ = ((MapValue.Builder) MapValue.newBuilder((MapValue) this.valueType_).mergeFrom(value)).buildPartial();
        }
        this.valueTypeCase_ = 6;
    }

    /* access modifiers changed from: private */
    public void clearMapValue() {
        if (this.valueTypeCase_ == 6) {
            this.valueTypeCase_ = 0;
            this.valueType_ = null;
        }
    }

    public static Value parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Value parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Value parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Value parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Value parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Value parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Value parseFrom(InputStream input) throws IOException {
        return (Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static Value parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Value parseDelimitedFrom(InputStream input) throws IOException {
        return (Value) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static Value parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Value) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Value parseFrom(CodedInputStream input) throws IOException {
        return (Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static Value parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Value) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(Value prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Value, Builder> implements ValueOrBuilder {
        private Builder() {
            super(Value.DEFAULT_INSTANCE);
        }

        public ValueTypeCase getValueTypeCase() {
            return ((Value) this.instance).getValueTypeCase();
        }

        public Builder clearValueType() {
            copyOnWrite();
            ((Value) this.instance).clearValueType();
            return this;
        }

        public boolean hasNullValue() {
            return ((Value) this.instance).hasNullValue();
        }

        public int getNullValueValue() {
            return ((Value) this.instance).getNullValueValue();
        }

        public Builder setNullValueValue(int value) {
            copyOnWrite();
            ((Value) this.instance).setNullValueValue(value);
            return this;
        }

        public NullValue getNullValue() {
            return ((Value) this.instance).getNullValue();
        }

        public Builder setNullValue(NullValue value) {
            copyOnWrite();
            ((Value) this.instance).setNullValue(value);
            return this;
        }

        public Builder clearNullValue() {
            copyOnWrite();
            ((Value) this.instance).clearNullValue();
            return this;
        }

        public boolean hasBooleanValue() {
            return ((Value) this.instance).hasBooleanValue();
        }

        public boolean getBooleanValue() {
            return ((Value) this.instance).getBooleanValue();
        }

        public Builder setBooleanValue(boolean value) {
            copyOnWrite();
            ((Value) this.instance).setBooleanValue(value);
            return this;
        }

        public Builder clearBooleanValue() {
            copyOnWrite();
            ((Value) this.instance).clearBooleanValue();
            return this;
        }

        public boolean hasIntegerValue() {
            return ((Value) this.instance).hasIntegerValue();
        }

        public long getIntegerValue() {
            return ((Value) this.instance).getIntegerValue();
        }

        public Builder setIntegerValue(long value) {
            copyOnWrite();
            ((Value) this.instance).setIntegerValue(value);
            return this;
        }

        public Builder clearIntegerValue() {
            copyOnWrite();
            ((Value) this.instance).clearIntegerValue();
            return this;
        }

        public boolean hasDoubleValue() {
            return ((Value) this.instance).hasDoubleValue();
        }

        public double getDoubleValue() {
            return ((Value) this.instance).getDoubleValue();
        }

        public Builder setDoubleValue(double value) {
            copyOnWrite();
            ((Value) this.instance).setDoubleValue(value);
            return this;
        }

        public Builder clearDoubleValue() {
            copyOnWrite();
            ((Value) this.instance).clearDoubleValue();
            return this;
        }

        public boolean hasTimestampValue() {
            return ((Value) this.instance).hasTimestampValue();
        }

        public Timestamp getTimestampValue() {
            return ((Value) this.instance).getTimestampValue();
        }

        public Builder setTimestampValue(Timestamp value) {
            copyOnWrite();
            ((Value) this.instance).setTimestampValue(value);
            return this;
        }

        public Builder setTimestampValue(Timestamp.Builder builderForValue) {
            copyOnWrite();
            ((Value) this.instance).setTimestampValue((Timestamp) builderForValue.build());
            return this;
        }

        public Builder mergeTimestampValue(Timestamp value) {
            copyOnWrite();
            ((Value) this.instance).mergeTimestampValue(value);
            return this;
        }

        public Builder clearTimestampValue() {
            copyOnWrite();
            ((Value) this.instance).clearTimestampValue();
            return this;
        }

        public boolean hasStringValue() {
            return ((Value) this.instance).hasStringValue();
        }

        public String getStringValue() {
            return ((Value) this.instance).getStringValue();
        }

        public ByteString getStringValueBytes() {
            return ((Value) this.instance).getStringValueBytes();
        }

        public Builder setStringValue(String value) {
            copyOnWrite();
            ((Value) this.instance).setStringValue(value);
            return this;
        }

        public Builder clearStringValue() {
            copyOnWrite();
            ((Value) this.instance).clearStringValue();
            return this;
        }

        public Builder setStringValueBytes(ByteString value) {
            copyOnWrite();
            ((Value) this.instance).setStringValueBytes(value);
            return this;
        }

        public boolean hasBytesValue() {
            return ((Value) this.instance).hasBytesValue();
        }

        public ByteString getBytesValue() {
            return ((Value) this.instance).getBytesValue();
        }

        public Builder setBytesValue(ByteString value) {
            copyOnWrite();
            ((Value) this.instance).setBytesValue(value);
            return this;
        }

        public Builder clearBytesValue() {
            copyOnWrite();
            ((Value) this.instance).clearBytesValue();
            return this;
        }

        public boolean hasReferenceValue() {
            return ((Value) this.instance).hasReferenceValue();
        }

        public String getReferenceValue() {
            return ((Value) this.instance).getReferenceValue();
        }

        public ByteString getReferenceValueBytes() {
            return ((Value) this.instance).getReferenceValueBytes();
        }

        public Builder setReferenceValue(String value) {
            copyOnWrite();
            ((Value) this.instance).setReferenceValue(value);
            return this;
        }

        public Builder clearReferenceValue() {
            copyOnWrite();
            ((Value) this.instance).clearReferenceValue();
            return this;
        }

        public Builder setReferenceValueBytes(ByteString value) {
            copyOnWrite();
            ((Value) this.instance).setReferenceValueBytes(value);
            return this;
        }

        public boolean hasGeoPointValue() {
            return ((Value) this.instance).hasGeoPointValue();
        }

        public LatLng getGeoPointValue() {
            return ((Value) this.instance).getGeoPointValue();
        }

        public Builder setGeoPointValue(LatLng value) {
            copyOnWrite();
            ((Value) this.instance).setGeoPointValue(value);
            return this;
        }

        public Builder setGeoPointValue(LatLng.Builder builderForValue) {
            copyOnWrite();
            ((Value) this.instance).setGeoPointValue((LatLng) builderForValue.build());
            return this;
        }

        public Builder mergeGeoPointValue(LatLng value) {
            copyOnWrite();
            ((Value) this.instance).mergeGeoPointValue(value);
            return this;
        }

        public Builder clearGeoPointValue() {
            copyOnWrite();
            ((Value) this.instance).clearGeoPointValue();
            return this;
        }

        public boolean hasArrayValue() {
            return ((Value) this.instance).hasArrayValue();
        }

        public ArrayValue getArrayValue() {
            return ((Value) this.instance).getArrayValue();
        }

        public Builder setArrayValue(ArrayValue value) {
            copyOnWrite();
            ((Value) this.instance).setArrayValue(value);
            return this;
        }

        public Builder setArrayValue(ArrayValue.Builder builderForValue) {
            copyOnWrite();
            ((Value) this.instance).setArrayValue((ArrayValue) builderForValue.build());
            return this;
        }

        public Builder mergeArrayValue(ArrayValue value) {
            copyOnWrite();
            ((Value) this.instance).mergeArrayValue(value);
            return this;
        }

        public Builder clearArrayValue() {
            copyOnWrite();
            ((Value) this.instance).clearArrayValue();
            return this;
        }

        public boolean hasMapValue() {
            return ((Value) this.instance).hasMapValue();
        }

        public MapValue getMapValue() {
            return ((Value) this.instance).getMapValue();
        }

        public Builder setMapValue(MapValue value) {
            copyOnWrite();
            ((Value) this.instance).setMapValue(value);
            return this;
        }

        public Builder setMapValue(MapValue.Builder builderForValue) {
            copyOnWrite();
            ((Value) this.instance).setMapValue((MapValue) builderForValue.build());
            return this;
        }

        public Builder mergeMapValue(MapValue value) {
            copyOnWrite();
            ((Value) this.instance).mergeMapValue(value);
            return this;
        }

        public Builder clearMapValue() {
            copyOnWrite();
            ((Value) this.instance).clearMapValue();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new Value();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u000b\u0001\u0000\u0001\u0012\u000b\u0000\u0000\u0000\u0001:\u0000\u00025\u0000\u00033\u0000\u0005Ȼ\u0000\u0006<\u0000\b<\u0000\t<\u0000\n<\u0000\u000b?\u0000\u0011Ȼ\u0000\u0012=\u0000", new Object[]{"valueType_", "valueTypeCase_", MapValue.class, LatLng.class, ArrayValue.class, Timestamp.class});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<Value> parser = PARSER;
                if (parser == null) {
                    synchronized (Value.class) {
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
        Value defaultInstance = new Value();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(Value.class, defaultInstance);
    }

    public static Value getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Value> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
