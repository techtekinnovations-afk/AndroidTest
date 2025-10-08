package com.google.firebase.firestore.model;

import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Util;
import com.google.firestore.v1.ArrayValue;
import com.google.firestore.v1.ArrayValueOrBuilder;
import com.google.firestore.v1.MapValue;
import com.google.firestore.v1.Value;
import com.google.protobuf.ByteString;
import com.google.protobuf.NullValue;
import com.google.protobuf.Timestamp;
import com.google.type.LatLng;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Values {
    public static final Value MAX_VALUE = ((Value) Value.newBuilder().setMapValue(MapValue.newBuilder().putFields(TYPE_KEY, MAX_VALUE_TYPE)).build());
    public static final Value MAX_VALUE_TYPE = ((Value) Value.newBuilder().setStringValue("__max__").build());
    public static Value MIN_ARRAY = ((Value) Value.newBuilder().setArrayValue(ArrayValue.getDefaultInstance()).build());
    public static Value MIN_BOOLEAN = ((Value) Value.newBuilder().setBooleanValue(false).build());
    public static Value MIN_BYTES = ((Value) Value.newBuilder().setBytesValue(ByteString.EMPTY).build());
    public static Value MIN_GEO_POINT = ((Value) Value.newBuilder().setGeoPointValue(LatLng.newBuilder().setLatitude(-90.0d).setLongitude(-180.0d)).build());
    public static Value MIN_MAP = ((Value) Value.newBuilder().setMapValue(MapValue.getDefaultInstance()).build());
    public static Value MIN_NUMBER = ((Value) Value.newBuilder().setDoubleValue(Double.NaN).build());
    public static Value MIN_REFERENCE = refValue(DatabaseId.EMPTY, DocumentKey.empty());
    public static Value MIN_STRING = ((Value) Value.newBuilder().setStringValue("").build());
    public static Value MIN_TIMESTAMP = ((Value) Value.newBuilder().setTimestampValue(Timestamp.newBuilder().setSeconds(Long.MIN_VALUE)).build());
    public static final Value MIN_VALUE = NULL_VALUE;
    private static final Value MIN_VECTOR_VALUE = ((Value) Value.newBuilder().setMapValue(MapValue.newBuilder().putFields(TYPE_KEY, VECTOR_VALUE_TYPE).putFields(VECTOR_MAP_VECTORS_KEY, (Value) Value.newBuilder().setArrayValue(ArrayValue.newBuilder()).build())).build());
    public static final Value NAN_VALUE = ((Value) Value.newBuilder().setDoubleValue(Double.NaN).build());
    public static final Value NULL_VALUE = ((Value) Value.newBuilder().setNullValue(NullValue.NULL_VALUE).build());
    public static final String TYPE_KEY = "__type__";
    public static final int TYPE_ORDER_ARRAY = 9;
    public static final int TYPE_ORDER_BLOB = 6;
    public static final int TYPE_ORDER_BOOLEAN = 1;
    public static final int TYPE_ORDER_GEOPOINT = 8;
    public static final int TYPE_ORDER_MAP = 11;
    public static final int TYPE_ORDER_MAX_VALUE = Integer.MAX_VALUE;
    public static final int TYPE_ORDER_NULL = 0;
    public static final int TYPE_ORDER_NUMBER = 2;
    public static final int TYPE_ORDER_REFERENCE = 7;
    public static final int TYPE_ORDER_SERVER_TIMESTAMP = 4;
    public static final int TYPE_ORDER_STRING = 5;
    public static final int TYPE_ORDER_TIMESTAMP = 3;
    public static final int TYPE_ORDER_VECTOR = 10;
    public static final String VECTOR_MAP_VECTORS_KEY = "value";
    public static final Value VECTOR_VALUE_TYPE = ((Value) Value.newBuilder().setStringValue("__vector__").build());

    public static int typeOrder(Value value) {
        switch (value.getValueTypeCase()) {
            case NULL_VALUE:
                return 0;
            case BOOLEAN_VALUE:
                return 1;
            case INTEGER_VALUE:
                return 2;
            case DOUBLE_VALUE:
                return 2;
            case TIMESTAMP_VALUE:
                return 3;
            case STRING_VALUE:
                return 5;
            case BYTES_VALUE:
                return 6;
            case REFERENCE_VALUE:
                return 7;
            case GEO_POINT_VALUE:
                return 8;
            case ARRAY_VALUE:
                return 9;
            case MAP_VALUE:
                if (ServerTimestamps.isServerTimestamp(value)) {
                    return 4;
                }
                if (isMaxValue(value)) {
                    return Integer.MAX_VALUE;
                }
                if (isVectorValue(value)) {
                    return 10;
                }
                return 11;
            default:
                throw Assert.fail("Invalid value type: " + value.getValueTypeCase(), new Object[0]);
        }
    }

    public static boolean equals(Value left, Value right) {
        int leftType;
        if (left == right) {
            return true;
        }
        if (left == null || right == null || (leftType = typeOrder(left)) != typeOrder(right)) {
            return false;
        }
        switch (leftType) {
            case 2:
                return numberEquals(left, right);
            case 4:
                return ServerTimestamps.getLocalWriteTime(left).equals(ServerTimestamps.getLocalWriteTime(right));
            case 9:
                return arrayEquals(left, right);
            case 10:
            case 11:
                return objectEquals(left, right);
            case Integer.MAX_VALUE:
                return true;
            default:
                return left.equals(right);
        }
    }

    private static boolean numberEquals(Value left, Value right) {
        if (left.getValueTypeCase() == Value.ValueTypeCase.INTEGER_VALUE && right.getValueTypeCase() == Value.ValueTypeCase.INTEGER_VALUE) {
            if (left.getIntegerValue() == right.getIntegerValue()) {
                return true;
            }
            return false;
        } else if (left.getValueTypeCase() != Value.ValueTypeCase.DOUBLE_VALUE || right.getValueTypeCase() != Value.ValueTypeCase.DOUBLE_VALUE) {
            return false;
        } else {
            if (Double.doubleToLongBits(left.getDoubleValue()) == Double.doubleToLongBits(right.getDoubleValue())) {
                return true;
            }
            return false;
        }
    }

    private static boolean arrayEquals(Value left, Value right) {
        ArrayValue leftArray = left.getArrayValue();
        ArrayValue rightArray = right.getArrayValue();
        if (leftArray.getValuesCount() != rightArray.getValuesCount()) {
            return false;
        }
        for (int i = 0; i < leftArray.getValuesCount(); i++) {
            if (!equals(leftArray.getValues(i), rightArray.getValues(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean objectEquals(Value left, Value right) {
        MapValue leftMap = left.getMapValue();
        MapValue rightMap = right.getMapValue();
        if (leftMap.getFieldsCount() != rightMap.getFieldsCount()) {
            return false;
        }
        for (Map.Entry<String, Value> entry : leftMap.getFieldsMap().entrySet()) {
            if (!equals(entry.getValue(), rightMap.getFieldsMap().get(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }

    public static boolean contains(ArrayValueOrBuilder haystack, Value needle) {
        for (Value haystackElement : haystack.getValuesList()) {
            if (equals(haystackElement, needle)) {
                return true;
            }
        }
        return false;
    }

    public static int compare(Value left, Value right) {
        int leftType = typeOrder(left);
        int rightType = typeOrder(right);
        if (leftType != rightType) {
            return Integer.compare(leftType, rightType);
        }
        switch (leftType) {
            case 0:
            case Integer.MAX_VALUE:
                return 0;
            case 1:
                return Util.compareBooleans(left.getBooleanValue(), right.getBooleanValue());
            case 2:
                return compareNumbers(left, right);
            case 3:
                return compareTimestamps(left.getTimestampValue(), right.getTimestampValue());
            case 4:
                return compareTimestamps(ServerTimestamps.getLocalWriteTime(left), ServerTimestamps.getLocalWriteTime(right));
            case 5:
                return Util.compareUtf8Strings(left.getStringValue(), right.getStringValue());
            case 6:
                return Util.compareByteStrings(left.getBytesValue(), right.getBytesValue());
            case 7:
                return compareReferences(left.getReferenceValue(), right.getReferenceValue());
            case 8:
                return compareGeoPoints(left.getGeoPointValue(), right.getGeoPointValue());
            case 9:
                return compareArrays(left.getArrayValue(), right.getArrayValue());
            case 10:
                return compareVectors(left.getMapValue(), right.getMapValue());
            case 11:
                return compareMaps(left.getMapValue(), right.getMapValue());
            default:
                throw Assert.fail("Invalid value type: " + leftType, new Object[0]);
        }
    }

    public static int lowerBoundCompare(Value left, boolean leftInclusive, Value right, boolean rightInclusive) {
        int cmp = compare(left, right);
        if (cmp != 0) {
            return cmp;
        }
        if (leftInclusive && !rightInclusive) {
            return -1;
        }
        if (leftInclusive || !rightInclusive) {
            return 0;
        }
        return 1;
    }

    public static int upperBoundCompare(Value left, boolean leftInclusive, Value right, boolean rightInclusive) {
        int cmp = compare(left, right);
        if (cmp != 0) {
            return cmp;
        }
        if (leftInclusive && !rightInclusive) {
            return 1;
        }
        if (leftInclusive || !rightInclusive) {
            return 0;
        }
        return -1;
    }

    private static int compareNumbers(Value left, Value right) {
        if (left.getValueTypeCase() == Value.ValueTypeCase.DOUBLE_VALUE) {
            double leftDouble = left.getDoubleValue();
            if (right.getValueTypeCase() == Value.ValueTypeCase.DOUBLE_VALUE) {
                return Util.compareDoubles(leftDouble, right.getDoubleValue());
            }
            if (right.getValueTypeCase() == Value.ValueTypeCase.INTEGER_VALUE) {
                return Util.compareMixed(leftDouble, right.getIntegerValue());
            }
        } else if (left.getValueTypeCase() == Value.ValueTypeCase.INTEGER_VALUE) {
            long leftLong = left.getIntegerValue();
            if (right.getValueTypeCase() == Value.ValueTypeCase.INTEGER_VALUE) {
                return Long.compare(leftLong, right.getIntegerValue());
            }
            if (right.getValueTypeCase() == Value.ValueTypeCase.DOUBLE_VALUE) {
                return Util.compareMixed(right.getDoubleValue(), leftLong) * -1;
            }
        }
        throw Assert.fail("Unexpected values: %s vs %s", left, right);
    }

    private static int compareTimestamps(Timestamp left, Timestamp right) {
        int cmp = Long.compare(left.getSeconds(), right.getSeconds());
        if (cmp != 0) {
            return cmp;
        }
        return Integer.compare(left.getNanos(), right.getNanos());
    }

    private static int compareReferences(String leftPath, String rightPath) {
        String[] leftSegments = leftPath.split("/", -1);
        String[] rightSegments = rightPath.split("/", -1);
        int minLength = Math.min(leftSegments.length, rightSegments.length);
        for (int i = 0; i < minLength; i++) {
            int cmp = leftSegments[i].compareTo(rightSegments[i]);
            if (cmp != 0) {
                return cmp;
            }
        }
        return Integer.compare(leftSegments.length, rightSegments.length);
    }

    private static int compareGeoPoints(LatLng left, LatLng right) {
        int comparison = Util.compareDoubles(left.getLatitude(), right.getLatitude());
        if (comparison == 0) {
            return Util.compareDoubles(left.getLongitude(), right.getLongitude());
        }
        return comparison;
    }

    private static int compareArrays(ArrayValue left, ArrayValue right) {
        int minLength = Math.min(left.getValuesCount(), right.getValuesCount());
        for (int i = 0; i < minLength; i++) {
            int cmp = compare(left.getValues(i), right.getValues(i));
            if (cmp != 0) {
                return cmp;
            }
        }
        return Integer.compare(left.getValuesCount(), right.getValuesCount());
    }

    private static int compareMaps(MapValue left, MapValue right) {
        Iterator<Map.Entry<String, Value>> iterator1 = new TreeMap(left.getFieldsMap()).entrySet().iterator();
        Iterator<Map.Entry<String, Value>> iterator2 = new TreeMap(right.getFieldsMap()).entrySet().iterator();
        while (iterator1.hasNext() && iterator2.hasNext()) {
            Map.Entry<String, Value> entry1 = iterator1.next();
            Map.Entry<String, Value> entry2 = iterator2.next();
            int keyCompare = Util.compareUtf8Strings(entry1.getKey(), entry2.getKey());
            if (keyCompare != 0) {
                return keyCompare;
            }
            int valueCompare = compare(entry1.getValue(), entry2.getValue());
            if (valueCompare != 0) {
                return valueCompare;
            }
        }
        return Util.compareBooleans(iterator1.hasNext(), iterator2.hasNext());
    }

    private static int compareVectors(MapValue left, MapValue right) {
        Map<String, Value> leftMap = left.getFieldsMap();
        Map<String, Value> rightMap = right.getFieldsMap();
        ArrayValue leftArrayValue = leftMap.get(VECTOR_MAP_VECTORS_KEY).getArrayValue();
        ArrayValue rightArrayValue = rightMap.get(VECTOR_MAP_VECTORS_KEY).getArrayValue();
        int lengthCompare = Integer.compare(leftArrayValue.getValuesCount(), rightArrayValue.getValuesCount());
        if (lengthCompare != 0) {
            return lengthCompare;
        }
        return compareArrays(leftArrayValue, rightArrayValue);
    }

    public static String canonicalId(Value value) {
        StringBuilder builder = new StringBuilder();
        canonifyValue(builder, value);
        return builder.toString();
    }

    private static void canonifyValue(StringBuilder builder, Value value) {
        switch (value.getValueTypeCase()) {
            case NULL_VALUE:
                builder.append("null");
                return;
            case BOOLEAN_VALUE:
                builder.append(value.getBooleanValue());
                return;
            case INTEGER_VALUE:
                builder.append(value.getIntegerValue());
                return;
            case DOUBLE_VALUE:
                builder.append(value.getDoubleValue());
                return;
            case TIMESTAMP_VALUE:
                canonifyTimestamp(builder, value.getTimestampValue());
                return;
            case STRING_VALUE:
                builder.append(value.getStringValue());
                return;
            case BYTES_VALUE:
                builder.append(Util.toDebugString(value.getBytesValue()));
                return;
            case REFERENCE_VALUE:
                canonifyReference(builder, value);
                return;
            case GEO_POINT_VALUE:
                canonifyGeoPoint(builder, value.getGeoPointValue());
                return;
            case ARRAY_VALUE:
                canonifyArray(builder, value.getArrayValue());
                return;
            case MAP_VALUE:
                canonifyObject(builder, value.getMapValue());
                return;
            default:
                throw Assert.fail("Invalid value type: " + value.getValueTypeCase(), new Object[0]);
        }
    }

    private static void canonifyTimestamp(StringBuilder builder, Timestamp timestamp) {
        builder.append(String.format("time(%s,%s)", new Object[]{Long.valueOf(timestamp.getSeconds()), Integer.valueOf(timestamp.getNanos())}));
    }

    private static void canonifyGeoPoint(StringBuilder builder, LatLng latLng) {
        builder.append(String.format("geo(%s,%s)", new Object[]{Double.valueOf(latLng.getLatitude()), Double.valueOf(latLng.getLongitude())}));
    }

    private static void canonifyReference(StringBuilder builder, Value value) {
        Assert.hardAssert(isReferenceValue(value), "Value should be a ReferenceValue", new Object[0]);
        builder.append(DocumentKey.fromName(value.getReferenceValue()));
    }

    private static void canonifyObject(StringBuilder builder, MapValue mapValue) {
        List<String> keys = new ArrayList<>(mapValue.getFieldsMap().keySet());
        Collections.sort(keys);
        builder.append("{");
        boolean first = true;
        for (String key : keys) {
            if (!first) {
                builder.append(",");
            } else {
                first = false;
            }
            builder.append(key).append(":");
            canonifyValue(builder, mapValue.getFieldsOrThrow(key));
        }
        builder.append("}");
    }

    private static void canonifyArray(StringBuilder builder, ArrayValue arrayValue) {
        builder.append("[");
        for (int i = 0; i < arrayValue.getValuesCount(); i++) {
            canonifyValue(builder, arrayValue.getValues(i));
            if (i != arrayValue.getValuesCount() - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
    }

    public static boolean isInteger(Value value) {
        return value != null && value.getValueTypeCase() == Value.ValueTypeCase.INTEGER_VALUE;
    }

    public static boolean isDouble(Value value) {
        return value != null && value.getValueTypeCase() == Value.ValueTypeCase.DOUBLE_VALUE;
    }

    public static boolean isNumber(Value value) {
        return isInteger(value) || isDouble(value);
    }

    public static boolean isArray(Value value) {
        return value != null && value.getValueTypeCase() == Value.ValueTypeCase.ARRAY_VALUE;
    }

    public static boolean isReferenceValue(Value value) {
        return value != null && value.getValueTypeCase() == Value.ValueTypeCase.REFERENCE_VALUE;
    }

    public static boolean isNullValue(Value value) {
        return value != null && value.getValueTypeCase() == Value.ValueTypeCase.NULL_VALUE;
    }

    public static boolean isNanValue(Value value) {
        return value != null && Double.isNaN(value.getDoubleValue());
    }

    public static boolean isMapValue(Value value) {
        return value != null && value.getValueTypeCase() == Value.ValueTypeCase.MAP_VALUE;
    }

    public static Value refValue(DatabaseId databaseId, DocumentKey key) {
        return (Value) Value.newBuilder().setReferenceValue(String.format("projects/%s/databases/%s/documents/%s", new Object[]{databaseId.getProjectId(), databaseId.getDatabaseId(), key.toString()})).build();
    }

    public static Value getLowerBound(Value value) {
        switch (value.getValueTypeCase()) {
            case NULL_VALUE:
                return NULL_VALUE;
            case BOOLEAN_VALUE:
                return MIN_BOOLEAN;
            case INTEGER_VALUE:
            case DOUBLE_VALUE:
                return MIN_NUMBER;
            case TIMESTAMP_VALUE:
                return MIN_TIMESTAMP;
            case STRING_VALUE:
                return MIN_STRING;
            case BYTES_VALUE:
                return MIN_BYTES;
            case REFERENCE_VALUE:
                return MIN_REFERENCE;
            case GEO_POINT_VALUE:
                return MIN_GEO_POINT;
            case ARRAY_VALUE:
                return MIN_ARRAY;
            case MAP_VALUE:
                if (isVectorValue(value)) {
                    return MIN_VECTOR_VALUE;
                }
                return MIN_MAP;
            default:
                throw new IllegalArgumentException("Unknown value type: " + value.getValueTypeCase());
        }
    }

    public static Value getUpperBound(Value value) {
        switch (value.getValueTypeCase()) {
            case NULL_VALUE:
                return MIN_BOOLEAN;
            case BOOLEAN_VALUE:
                return MIN_NUMBER;
            case INTEGER_VALUE:
            case DOUBLE_VALUE:
                return MIN_TIMESTAMP;
            case TIMESTAMP_VALUE:
                return MIN_STRING;
            case STRING_VALUE:
                return MIN_BYTES;
            case BYTES_VALUE:
                return MIN_REFERENCE;
            case REFERENCE_VALUE:
                return MIN_GEO_POINT;
            case GEO_POINT_VALUE:
                return MIN_ARRAY;
            case ARRAY_VALUE:
                return MIN_VECTOR_VALUE;
            case MAP_VALUE:
                if (isVectorValue(value)) {
                    return MIN_MAP;
                }
                return MAX_VALUE;
            default:
                throw new IllegalArgumentException("Unknown value type: " + value.getValueTypeCase());
        }
    }

    public static boolean isMaxValue(Value value) {
        return MAX_VALUE_TYPE.equals(value.getMapValue().getFieldsMap().get(TYPE_KEY));
    }

    public static boolean isVectorValue(Value value) {
        return VECTOR_VALUE_TYPE.equals(value.getMapValue().getFieldsMap().get(TYPE_KEY));
    }
}
