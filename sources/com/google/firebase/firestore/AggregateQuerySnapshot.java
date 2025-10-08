package com.google.firebase.firestore;

import com.google.firebase.firestore.AggregateField;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.util.Preconditions;
import com.google.firestore.v1.Value;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class AggregateQuerySnapshot {
    private final Map<String, Value> data;
    private final AggregateQuery query;

    AggregateQuerySnapshot(AggregateQuery query2, Map<String, Value> data2) {
        Preconditions.checkNotNull(query2);
        this.query = query2;
        this.data = data2;
    }

    static AggregateQuerySnapshot createWithCount(AggregateQuery query2, long count) {
        return new AggregateQuerySnapshot(query2, Collections.singletonMap(AggregateField.count().getAlias(), (Value) Value.newBuilder().setIntegerValue(count).build()));
    }

    public AggregateQuery getQuery() {
        return this.query;
    }

    public long getCount() {
        return get(AggregateField.count());
    }

    public Object get(AggregateField aggregateField) {
        return getInternal(aggregateField);
    }

    public long get(AggregateField.CountAggregateField countAggregateField) {
        Long value = getLong(countAggregateField);
        if (value != null) {
            return value.longValue();
        }
        throw new IllegalArgumentException("RunAggregationQueryResponse alias " + countAggregateField.getAlias() + " is null");
    }

    public Double get(AggregateField.AverageAggregateField averageAggregateField) {
        return getDouble(averageAggregateField);
    }

    public Double getDouble(AggregateField aggregateField) {
        Number val = (Number) getTypedValue(aggregateField, Number.class);
        if (val != null) {
            return Double.valueOf(val.doubleValue());
        }
        return null;
    }

    public Long getLong(AggregateField aggregateField) {
        Number val = (Number) getTypedValue(aggregateField, Number.class);
        if (val != null) {
            return Long.valueOf(val.longValue());
        }
        return null;
    }

    private Object getInternal(AggregateField aggregateField) {
        if (this.data.containsKey(aggregateField.getAlias())) {
            return new UserDataWriter(this.query.getQuery().firestore, DocumentSnapshot.ServerTimestampBehavior.DEFAULT).convertValue(this.data.get(aggregateField.getAlias()));
        }
        throw new IllegalArgumentException("'" + aggregateField.getOperator() + "(" + aggregateField.getFieldPath() + ")' was not requested in the aggregation query.");
    }

    private <T> T getTypedValue(AggregateField aggregateField, Class<T> clazz) {
        return castTypedValue(getInternal(aggregateField), aggregateField, clazz);
    }

    private <T> T castTypedValue(Object value, AggregateField aggregateField, Class<T> clazz) {
        if (value == null) {
            return null;
        }
        if (clazz.isInstance(value)) {
            return clazz.cast(value);
        }
        throw new RuntimeException("AggregateField '" + aggregateField.getAlias() + "' is not a " + clazz.getName());
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof AggregateQuerySnapshot)) {
            return false;
        }
        AggregateQuerySnapshot other = (AggregateQuerySnapshot) object;
        if (!this.query.equals(other.query) || !this.data.equals(other.data)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.query, this.data});
    }
}
