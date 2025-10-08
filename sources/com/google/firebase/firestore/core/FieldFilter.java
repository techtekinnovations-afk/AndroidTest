package com.google.firebase.firestore.core;

import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.Values;
import com.google.firebase.firestore.util.Assert;
import com.google.firestore.v1.Value;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FieldFilter extends Filter {
    private final FieldPath field;
    private final Operator operator;
    private final Value value;

    public enum Operator {
        LESS_THAN("<"),
        LESS_THAN_OR_EQUAL("<="),
        EQUAL("=="),
        NOT_EQUAL("!="),
        GREATER_THAN(">"),
        GREATER_THAN_OR_EQUAL(">="),
        ARRAY_CONTAINS("array_contains"),
        ARRAY_CONTAINS_ANY("array_contains_any"),
        IN("in"),
        NOT_IN("not_in");
        
        private final String text;

        private Operator(String text2) {
            this.text = text2;
        }

        public String toString() {
            return this.text;
        }
    }

    protected FieldFilter(FieldPath field2, Operator operator2, Value value2) {
        this.field = field2;
        this.operator = operator2;
        this.value = value2;
    }

    public Operator getOperator() {
        return this.operator;
    }

    public FieldPath getField() {
        return this.field;
    }

    public Value getValue() {
        return this.value;
    }

    public static FieldFilter create(FieldPath path, Operator operator2, Value value2) {
        if (path.isKeyField()) {
            if (operator2 == Operator.IN) {
                return new KeyFieldInFilter(path, value2);
            }
            if (operator2 == Operator.NOT_IN) {
                return new KeyFieldNotInFilter(path, value2);
            }
            Assert.hardAssert((operator2 == Operator.ARRAY_CONTAINS || operator2 == Operator.ARRAY_CONTAINS_ANY) ? false : true, operator2.toString() + "queries don't make sense on document keys", new Object[0]);
            return new KeyFieldFilter(path, operator2, value2);
        } else if (operator2 == Operator.ARRAY_CONTAINS) {
            return new ArrayContainsFilter(path, value2);
        } else {
            if (operator2 == Operator.IN) {
                return new InFilter(path, value2);
            }
            if (operator2 == Operator.ARRAY_CONTAINS_ANY) {
                return new ArrayContainsAnyFilter(path, value2);
            }
            if (operator2 == Operator.NOT_IN) {
                return new NotInFilter(path, value2);
            }
            return new FieldFilter(path, operator2, value2);
        }
    }

    public boolean matches(Document doc) {
        Value other = doc.getField(this.field);
        if (this.operator == Operator.NOT_EQUAL) {
            if (other == null || other.hasNullValue() || !matchesComparison(Values.compare(other, this.value))) {
                return false;
            }
            return true;
        } else if (other == null || Values.typeOrder(other) != Values.typeOrder(this.value) || !matchesComparison(Values.compare(other, this.value))) {
            return false;
        } else {
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public boolean matchesComparison(int comp) {
        switch (this.operator) {
            case LESS_THAN:
                if (comp < 0) {
                    return true;
                }
                return false;
            case LESS_THAN_OR_EQUAL:
                if (comp <= 0) {
                    return true;
                }
                return false;
            case EQUAL:
                if (comp == 0) {
                    return true;
                }
                return false;
            case NOT_EQUAL:
                if (comp != 0) {
                    return true;
                }
                return false;
            case GREATER_THAN:
                if (comp > 0) {
                    return true;
                }
                return false;
            case GREATER_THAN_OR_EQUAL:
                if (comp >= 0) {
                    return true;
                }
                return false;
            default:
                throw Assert.fail("Unknown FieldFilter operator: %s", this.operator);
        }
    }

    public boolean isInequality() {
        return Arrays.asList(new Operator[]{Operator.LESS_THAN, Operator.LESS_THAN_OR_EQUAL, Operator.GREATER_THAN, Operator.GREATER_THAN_OR_EQUAL, Operator.NOT_EQUAL, Operator.NOT_IN}).contains(this.operator);
    }

    public String getCanonicalId() {
        return getField().canonicalString() + getOperator().toString() + Values.canonicalId(getValue());
    }

    public List<FieldFilter> getFlattenedFilters() {
        return Collections.singletonList(this);
    }

    public List<Filter> getFilters() {
        return Collections.singletonList(this);
    }

    public String toString() {
        return getCanonicalId();
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof FieldFilter)) {
            return false;
        }
        FieldFilter other = (FieldFilter) o;
        if (this.operator != other.operator || !this.field.equals(other.field) || !this.value.equals(other.value)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((37 * 31) + this.operator.hashCode()) * 31) + this.field.hashCode()) * 31) + this.value.hashCode();
    }
}
