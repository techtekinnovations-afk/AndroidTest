package com.google.firebase.firestore.core;

import android.util.Pair;
import com.google.firebase.firestore.core.FieldFilter;
import com.google.firebase.firestore.core.OrderBy;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldIndex;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.model.Values;
import com.google.firestore.v1.Value;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public final class Target {
    public static final long NO_LIMIT = -1;
    private final String collectionGroup;
    private final Bound endAt;
    private final List<Filter> filters;
    private final long limit;
    private String memoizedCanonicalId;
    private final List<OrderBy> orderBys;
    private final ResourcePath path;
    private final Bound startAt;

    public Target(ResourcePath path2, String collectionGroup2, List<Filter> filters2, List<OrderBy> orderBys2, long limit2, Bound startAt2, Bound endAt2) {
        this.path = path2;
        this.collectionGroup = collectionGroup2;
        this.orderBys = orderBys2;
        this.filters = filters2;
        this.limit = limit2;
        this.startAt = startAt2;
        this.endAt = endAt2;
    }

    public ResourcePath getPath() {
        return this.path;
    }

    public String getCollectionGroup() {
        return this.collectionGroup;
    }

    public boolean isDocumentQuery() {
        return DocumentKey.isDocumentKey(this.path) && this.collectionGroup == null && this.filters.isEmpty();
    }

    public List<Filter> getFilters() {
        return this.filters;
    }

    public long getLimit() {
        return this.limit;
    }

    public boolean hasLimit() {
        return this.limit != -1;
    }

    public Bound getStartAt() {
        return this.startAt;
    }

    public Bound getEndAt() {
        return this.endAt;
    }

    private List<FieldFilter> getFieldFiltersForPath(FieldPath path2) {
        List<FieldFilter> result = new ArrayList<>();
        for (Filter filter : this.filters) {
            if ((filter instanceof FieldFilter) && ((FieldFilter) filter).getField().equals(path2)) {
                result.add((FieldFilter) filter);
            }
        }
        return result;
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:0x001a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.google.firestore.v1.Value> getArrayValues(com.google.firebase.firestore.model.FieldIndex r7) {
        /*
            r6 = this;
            com.google.firebase.firestore.model.FieldIndex$Segment r0 = r7.getArraySegment()
            r1 = 0
            if (r0 != 0) goto L_0x0008
            return r1
        L_0x0008:
            com.google.firebase.firestore.model.FieldPath r2 = r0.getFieldPath()
            java.util.List r2 = r6.getFieldFiltersForPath(r2)
            java.util.Iterator r2 = r2.iterator()
        L_0x0014:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0046
            java.lang.Object r3 = r2.next()
            com.google.firebase.firestore.core.FieldFilter r3 = (com.google.firebase.firestore.core.FieldFilter) r3
            int[] r4 = com.google.firebase.firestore.core.Target.AnonymousClass1.$SwitchMap$com$google$firebase$firestore$core$FieldFilter$Operator
            com.google.firebase.firestore.core.FieldFilter$Operator r5 = r3.getOperator()
            int r5 = r5.ordinal()
            r4 = r4[r5]
            switch(r4) {
                case 1: goto L_0x0039;
                case 2: goto L_0x0030;
                default: goto L_0x002f;
            }
        L_0x002f:
            goto L_0x0014
        L_0x0030:
            com.google.firestore.v1.Value r1 = r3.getValue()
            java.util.List r1 = java.util.Collections.singletonList(r1)
            return r1
        L_0x0039:
            com.google.firestore.v1.Value r1 = r3.getValue()
            com.google.firestore.v1.ArrayValue r1 = r1.getArrayValue()
            java.util.List r1 = r1.getValuesList()
            return r1
        L_0x0046:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.core.Target.getArrayValues(com.google.firebase.firestore.model.FieldIndex):java.util.List");
    }

    public Collection<Value> getNotInValues(FieldIndex fieldIndex) {
        LinkedHashMap<FieldPath, Value> values = new LinkedHashMap<>();
        for (FieldIndex.Segment segment : fieldIndex.getDirectionalSegments()) {
            Iterator<FieldFilter> it = getFieldFiltersForPath(segment.getFieldPath()).iterator();
            while (true) {
                if (it.hasNext()) {
                    FieldFilter fieldFilter = it.next();
                    switch (fieldFilter.getOperator()) {
                        case EQUAL:
                        case IN:
                            values.put(segment.getFieldPath(), fieldFilter.getValue());
                            break;
                        case NOT_IN:
                        case NOT_EQUAL:
                            values.put(segment.getFieldPath(), fieldFilter.getValue());
                            return values.values();
                    }
                }
            }
        }
        return null;
    }

    public Bound getLowerBound(FieldIndex fieldIndex) {
        Pair<Value, Boolean> segmentBound;
        List<Value> values = new ArrayList<>();
        boolean inclusive = true;
        for (FieldIndex.Segment segment : fieldIndex.getDirectionalSegments()) {
            if (segment.getKind().equals(FieldIndex.Segment.Kind.ASCENDING)) {
                segmentBound = getAscendingBound(segment, this.startAt);
            } else {
                segmentBound = getDescendingBound(segment, this.startAt);
            }
            values.add((Value) segmentBound.first);
            inclusive &= ((Boolean) segmentBound.second).booleanValue();
        }
        return new Bound(values, inclusive);
    }

    public Bound getUpperBound(FieldIndex fieldIndex) {
        Pair<Value, Boolean> segmentBound;
        List<Value> values = new ArrayList<>();
        boolean inclusive = true;
        for (FieldIndex.Segment segment : fieldIndex.getDirectionalSegments()) {
            if (segment.getKind().equals(FieldIndex.Segment.Kind.ASCENDING)) {
                segmentBound = getDescendingBound(segment, this.endAt);
            } else {
                segmentBound = getAscendingBound(segment, this.endAt);
            }
            values.add((Value) segmentBound.first);
            inclusive &= ((Boolean) segmentBound.second).booleanValue();
        }
        return new Bound(values, inclusive);
    }

    private Pair<Value, Boolean> getAscendingBound(FieldIndex.Segment segment, Bound bound) {
        Value segmentValue = Values.MIN_VALUE;
        boolean segmentInclusive = true;
        for (FieldFilter fieldFilter : getFieldFiltersForPath(segment.getFieldPath())) {
            Value filterValue = Values.MIN_VALUE;
            boolean filterInclusive = true;
            switch (fieldFilter.getOperator()) {
                case EQUAL:
                case IN:
                case GREATER_THAN_OR_EQUAL:
                    filterValue = fieldFilter.getValue();
                    break;
                case NOT_IN:
                case NOT_EQUAL:
                    filterValue = Values.MIN_VALUE;
                    break;
                case LESS_THAN:
                case LESS_THAN_OR_EQUAL:
                    filterValue = Values.getLowerBound(fieldFilter.getValue());
                    break;
                case GREATER_THAN:
                    filterValue = fieldFilter.getValue();
                    filterInclusive = false;
                    break;
            }
            if (Values.lowerBoundCompare(segmentValue, segmentInclusive, filterValue, filterInclusive) < 0) {
                segmentValue = filterValue;
                segmentInclusive = filterInclusive;
            }
        }
        if (bound != null) {
            int i = 0;
            while (true) {
                if (i < this.orderBys.size()) {
                    if (this.orderBys.get(i).getField().equals(segment.getFieldPath())) {
                        Value cursorValue = bound.getPosition().get(i);
                        if (Values.lowerBoundCompare(segmentValue, segmentInclusive, cursorValue, bound.isInclusive()) < 0) {
                            segmentValue = cursorValue;
                            segmentInclusive = bound.isInclusive();
                        }
                    } else {
                        i++;
                    }
                }
            }
        }
        return new Pair<>(segmentValue, Boolean.valueOf(segmentInclusive));
    }

    private Pair<Value, Boolean> getDescendingBound(FieldIndex.Segment segment, Bound bound) {
        Value segmentValue = Values.MAX_VALUE;
        boolean segmentInclusive = true;
        for (FieldFilter fieldFilter : getFieldFiltersForPath(segment.getFieldPath())) {
            Value filterValue = Values.MAX_VALUE;
            boolean filterInclusive = true;
            switch (fieldFilter.getOperator()) {
                case EQUAL:
                case IN:
                case LESS_THAN_OR_EQUAL:
                    filterValue = fieldFilter.getValue();
                    break;
                case NOT_IN:
                case NOT_EQUAL:
                    filterValue = Values.MAX_VALUE;
                    break;
                case LESS_THAN:
                    filterValue = fieldFilter.getValue();
                    filterInclusive = false;
                    break;
                case GREATER_THAN_OR_EQUAL:
                case GREATER_THAN:
                    filterValue = Values.getUpperBound(fieldFilter.getValue());
                    filterInclusive = false;
                    break;
            }
            if (Values.upperBoundCompare(segmentValue, segmentInclusive, filterValue, filterInclusive) > 0) {
                segmentValue = filterValue;
                segmentInclusive = filterInclusive;
            }
        }
        if (bound != null) {
            int i = 0;
            while (true) {
                if (i < this.orderBys.size()) {
                    if (this.orderBys.get(i).getField().equals(segment.getFieldPath())) {
                        Value cursorValue = bound.getPosition().get(i);
                        if (Values.upperBoundCompare(segmentValue, segmentInclusive, cursorValue, bound.isInclusive()) > 0) {
                            segmentValue = cursorValue;
                            segmentInclusive = bound.isInclusive();
                        }
                    } else {
                        i++;
                    }
                }
            }
        }
        return new Pair<>(segmentValue, Boolean.valueOf(segmentInclusive));
    }

    public List<OrderBy> getOrderBy() {
        return this.orderBys;
    }

    public OrderBy.Direction getKeyOrder() {
        return this.orderBys.get(this.orderBys.size() - 1).getDirection();
    }

    public int getSegmentCount() {
        Set<FieldPath> fields = new HashSet<>();
        boolean hasArraySegment = false;
        for (Filter filter : this.filters) {
            for (FieldFilter subFilter : filter.getFlattenedFilters()) {
                if (!subFilter.getField().isKeyField()) {
                    if (subFilter.getOperator().equals(FieldFilter.Operator.ARRAY_CONTAINS) || subFilter.getOperator().equals(FieldFilter.Operator.ARRAY_CONTAINS_ANY)) {
                        hasArraySegment = true;
                    } else {
                        fields.add(subFilter.getField());
                    }
                }
            }
        }
        for (OrderBy orderBy : this.orderBys) {
            if (!orderBy.getField().isKeyField()) {
                fields.add(orderBy.getField());
            }
        }
        return fields.size() + hasArraySegment;
    }

    public String getCanonicalId() {
        String str;
        if (this.memoizedCanonicalId != null) {
            return this.memoizedCanonicalId;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(getPath().canonicalString());
        if (this.collectionGroup != null) {
            builder.append("|cg:");
            builder.append(this.collectionGroup);
        }
        builder.append("|f:");
        for (Filter filter : getFilters()) {
            builder.append(filter.getCanonicalId());
        }
        builder.append("|ob:");
        for (OrderBy orderBy : getOrderBy()) {
            builder.append(orderBy.getField().canonicalString());
            builder.append(orderBy.getDirection().equals(OrderBy.Direction.ASCENDING) ? "asc" : "desc");
        }
        if (hasLimit()) {
            builder.append("|l:");
            builder.append(getLimit());
        }
        String str2 = "b:";
        if (this.startAt != null) {
            builder.append("|lb:");
            if (this.startAt.isInclusive()) {
                str = str2;
            } else {
                str = "a:";
            }
            builder.append(str);
            builder.append(this.startAt.positionString());
        }
        if (this.endAt != null) {
            builder.append("|ub:");
            if (this.endAt.isInclusive()) {
                str2 = "a:";
            }
            builder.append(str2);
            builder.append(this.endAt.positionString());
        }
        this.memoizedCanonicalId = builder.toString();
        return this.memoizedCanonicalId;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Target target = (Target) o;
        if (this.collectionGroup == null ? target.collectionGroup != null : !this.collectionGroup.equals(target.collectionGroup)) {
            return false;
        }
        if (this.limit != target.limit || !this.orderBys.equals(target.orderBys) || !this.filters.equals(target.filters) || !this.path.equals(target.path)) {
            return false;
        }
        if (this.startAt == null ? target.startAt != null : !this.startAt.equals(target.startAt)) {
            return false;
        }
        if (this.endAt != null) {
            return this.endAt.equals(target.endAt);
        }
        if (target.endAt == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 0;
        int result = ((((((((((this.orderBys.hashCode() * 31) + (this.collectionGroup != null ? this.collectionGroup.hashCode() : 0)) * 31) + this.filters.hashCode()) * 31) + this.path.hashCode()) * 31) + ((int) (this.limit ^ (this.limit >>> 32)))) * 31) + (this.startAt != null ? this.startAt.hashCode() : 0)) * 31;
        if (this.endAt != null) {
            i = this.endAt.hashCode();
        }
        return result + i;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Query(");
        builder.append(this.path.canonicalString());
        if (this.collectionGroup != null) {
            builder.append(" collectionGroup=");
            builder.append(this.collectionGroup);
        }
        if (!this.filters.isEmpty()) {
            builder.append(" where ");
            for (int i = 0; i < this.filters.size(); i++) {
                if (i > 0) {
                    builder.append(" and ");
                }
                builder.append(this.filters.get(i));
            }
        }
        if (!this.orderBys.isEmpty()) {
            builder.append(" order by ");
            for (int i2 = 0; i2 < this.orderBys.size(); i2++) {
                if (i2 > 0) {
                    builder.append(", ");
                }
                builder.append(this.orderBys.get(i2));
            }
        }
        builder.append(")");
        return builder.toString();
    }
}
