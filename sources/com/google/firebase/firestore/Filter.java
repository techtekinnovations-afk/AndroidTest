package com.google.firebase.firestore;

import com.google.firebase.firestore.core.CompositeFilter;
import com.google.firebase.firestore.core.FieldFilter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Filter {

    static class UnaryFilter extends Filter {
        private final FieldPath field;
        private final FieldFilter.Operator operator;
        private final Object value;

        public UnaryFilter(FieldPath field2, FieldFilter.Operator operator2, Object value2) {
            this.field = field2;
            this.operator = operator2;
            this.value = value2;
        }

        public FieldPath getField() {
            return this.field;
        }

        public FieldFilter.Operator getOperator() {
            return this.operator;
        }

        public Object getValue() {
            return this.value;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            UnaryFilter that = (UnaryFilter) o;
            if (this.operator != that.operator || !Objects.equals(this.field, that.field) || !Objects.equals(this.value, that.value)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int i = 0;
            int result = (((this.field != null ? this.field.hashCode() : 0) * 31) + (this.operator != null ? this.operator.hashCode() : 0)) * 31;
            if (this.value != null) {
                i = this.value.hashCode();
            }
            return result + i;
        }
    }

    static class CompositeFilter extends Filter {
        private final List<Filter> filters;
        private final CompositeFilter.Operator operator;

        public CompositeFilter(List<Filter> filters2, CompositeFilter.Operator operator2) {
            this.filters = filters2;
            this.operator = operator2;
        }

        public List<Filter> getFilters() {
            return this.filters;
        }

        public CompositeFilter.Operator getOperator() {
            return this.operator;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CompositeFilter that = (CompositeFilter) o;
            if (this.operator != that.operator || !Objects.equals(this.filters, that.filters)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = (this.filters != null ? this.filters.hashCode() : 0) * 31;
            if (this.operator != null) {
                i = this.operator.hashCode();
            }
            return hashCode + i;
        }
    }

    public static Filter equalTo(String field, Object value) {
        return equalTo(FieldPath.fromDotSeparatedPath(field), value);
    }

    public static Filter equalTo(FieldPath fieldPath, Object value) {
        return new UnaryFilter(fieldPath, FieldFilter.Operator.EQUAL, value);
    }

    public static Filter notEqualTo(String field, Object value) {
        return notEqualTo(FieldPath.fromDotSeparatedPath(field), value);
    }

    public static Filter notEqualTo(FieldPath fieldPath, Object value) {
        return new UnaryFilter(fieldPath, FieldFilter.Operator.NOT_EQUAL, value);
    }

    public static Filter greaterThan(String field, Object value) {
        return greaterThan(FieldPath.fromDotSeparatedPath(field), value);
    }

    public static Filter greaterThan(FieldPath fieldPath, Object value) {
        return new UnaryFilter(fieldPath, FieldFilter.Operator.GREATER_THAN, value);
    }

    public static Filter greaterThanOrEqualTo(String field, Object value) {
        return greaterThanOrEqualTo(FieldPath.fromDotSeparatedPath(field), value);
    }

    public static Filter greaterThanOrEqualTo(FieldPath fieldPath, Object value) {
        return new UnaryFilter(fieldPath, FieldFilter.Operator.GREATER_THAN_OR_EQUAL, value);
    }

    public static Filter lessThan(String field, Object value) {
        return lessThan(FieldPath.fromDotSeparatedPath(field), value);
    }

    public static Filter lessThan(FieldPath fieldPath, Object value) {
        return new UnaryFilter(fieldPath, FieldFilter.Operator.LESS_THAN, value);
    }

    public static Filter lessThanOrEqualTo(String field, Object value) {
        return lessThanOrEqualTo(FieldPath.fromDotSeparatedPath(field), value);
    }

    public static Filter lessThanOrEqualTo(FieldPath fieldPath, Object value) {
        return new UnaryFilter(fieldPath, FieldFilter.Operator.LESS_THAN_OR_EQUAL, value);
    }

    public static Filter arrayContains(String field, Object value) {
        return arrayContains(FieldPath.fromDotSeparatedPath(field), value);
    }

    public static Filter arrayContains(FieldPath fieldPath, Object value) {
        return new UnaryFilter(fieldPath, FieldFilter.Operator.ARRAY_CONTAINS, value);
    }

    public static Filter arrayContainsAny(String field, List<? extends Object> values) {
        return arrayContainsAny(FieldPath.fromDotSeparatedPath(field), values);
    }

    public static Filter arrayContainsAny(FieldPath fieldPath, List<? extends Object> values) {
        return new UnaryFilter(fieldPath, FieldFilter.Operator.ARRAY_CONTAINS_ANY, values);
    }

    public static Filter inArray(String field, List<? extends Object> values) {
        return inArray(FieldPath.fromDotSeparatedPath(field), values);
    }

    public static Filter inArray(FieldPath fieldPath, List<? extends Object> values) {
        return new UnaryFilter(fieldPath, FieldFilter.Operator.IN, values);
    }

    public static Filter notInArray(String field, List<? extends Object> values) {
        return notInArray(FieldPath.fromDotSeparatedPath(field), values);
    }

    public static Filter notInArray(FieldPath fieldPath, List<? extends Object> values) {
        return new UnaryFilter(fieldPath, FieldFilter.Operator.NOT_IN, values);
    }

    public static Filter or(Filter... filters) {
        return new CompositeFilter(Arrays.asList(filters), CompositeFilter.Operator.OR);
    }

    public static Filter and(Filter... filters) {
        return new CompositeFilter(Arrays.asList(filters), CompositeFilter.Operator.AND);
    }
}
