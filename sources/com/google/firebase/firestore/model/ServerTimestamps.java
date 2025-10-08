package com.google.firebase.firestore.model;

import com.google.firebase.Timestamp;
import com.google.firestore.v1.MapValue;
import com.google.firestore.v1.Value;

public final class ServerTimestamps {
    private static final String LOCAL_WRITE_TIME_KEY = "__local_write_time__";
    private static final String PREVIOUS_VALUE_KEY = "__previous_value__";
    private static final String SERVER_TIMESTAMP_SENTINEL = "server_timestamp";
    private static final String TYPE_KEY = "__type__";

    private ServerTimestamps() {
    }

    public static boolean isServerTimestamp(Value value) {
        Value type = null;
        if (value != null) {
            type = value.getMapValue().getFieldsOrDefault("__type__", (Value) null);
        }
        return type != null && SERVER_TIMESTAMP_SENTINEL.equals(type.getStringValue());
    }

    public static Value valueOf(Timestamp localWriteTime, Value previousValue) {
        MapValue.Builder mapRepresentation = MapValue.newBuilder().putFields("__type__", (Value) Value.newBuilder().setStringValue(SERVER_TIMESTAMP_SENTINEL).build()).putFields(LOCAL_WRITE_TIME_KEY, (Value) Value.newBuilder().setTimestampValue(com.google.protobuf.Timestamp.newBuilder().setSeconds(localWriteTime.getSeconds()).setNanos(localWriteTime.getNanoseconds())).build());
        Value previousValue2 = isServerTimestamp(previousValue) ? getPreviousValue(previousValue) : previousValue;
        if (previousValue2 != null) {
            mapRepresentation.putFields(PREVIOUS_VALUE_KEY, previousValue2);
        }
        return (Value) Value.newBuilder().setMapValue(mapRepresentation).build();
    }

    public static Value getPreviousValue(Value serverTimestampValue) {
        Value previousValue = serverTimestampValue.getMapValue().getFieldsOrDefault(PREVIOUS_VALUE_KEY, (Value) null);
        if (isServerTimestamp(previousValue)) {
            return getPreviousValue(previousValue);
        }
        return previousValue;
    }

    public static com.google.protobuf.Timestamp getLocalWriteTime(Value serverTimestampValue) {
        return serverTimestampValue.getMapValue().getFieldsOrThrow(LOCAL_WRITE_TIME_KEY).getTimestampValue();
    }
}
