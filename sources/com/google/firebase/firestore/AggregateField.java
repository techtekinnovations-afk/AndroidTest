package com.google.firebase.firestore;

import java.util.Objects;

public abstract class AggregateField {
    private final String alias;
    private final FieldPath fieldPath;
    private final String operator;

    private AggregateField(FieldPath fieldPath2, String operator2) {
        this.fieldPath = fieldPath2;
        this.operator = operator2;
        this.alias = operator2 + (fieldPath2 == null ? "" : "_" + fieldPath2);
    }

    public String getFieldPath() {
        return this.fieldPath == null ? "" : this.fieldPath.toString();
    }

    public String getAlias() {
        return this.alias;
    }

    public String getOperator() {
        return this.operator;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AggregateField)) {
            return false;
        }
        AggregateField otherAggregateField = (AggregateField) other;
        if (this.fieldPath == null || otherAggregateField.fieldPath == null) {
            if (this.fieldPath == null && otherAggregateField.fieldPath == null) {
                return true;
            }
            return false;
        } else if (!this.operator.equals(otherAggregateField.getOperator()) || !getFieldPath().equals(otherAggregateField.getFieldPath())) {
            return false;
        } else {
            return true;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{getOperator(), getFieldPath()});
    }

    public static CountAggregateField count() {
        return new CountAggregateField();
    }

    public static SumAggregateField sum(String field) {
        return new SumAggregateField(FieldPath.fromDotSeparatedPath(field));
    }

    public static SumAggregateField sum(FieldPath fieldPath2) {
        return new SumAggregateField(fieldPath2);
    }

    public static AverageAggregateField average(String field) {
        return new AverageAggregateField(FieldPath.fromDotSeparatedPath(field));
    }

    public static AverageAggregateField average(FieldPath fieldPath2) {
        return new AverageAggregateField(fieldPath2);
    }

    public static class CountAggregateField extends AggregateField {
        private CountAggregateField() {
            super((FieldPath) null, "count");
        }
    }

    public static class SumAggregateField extends AggregateField {
        private SumAggregateField(FieldPath fieldPath) {
            super(fieldPath, "sum");
        }
    }

    public static class AverageAggregateField extends AggregateField {
        private AverageAggregateField(FieldPath fieldPath) {
            super(fieldPath, "average");
        }
    }
}
