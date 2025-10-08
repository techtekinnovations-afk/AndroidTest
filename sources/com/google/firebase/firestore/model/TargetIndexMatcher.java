package com.google.firebase.firestore.model;

import com.google.firebase.firestore.core.FieldFilter;
import com.google.firebase.firestore.core.Filter;
import com.google.firebase.firestore.core.OrderBy;
import com.google.firebase.firestore.core.Target;
import com.google.firebase.firestore.model.FieldIndex;
import com.google.firebase.firestore.util.Assert;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class TargetIndexMatcher {
    private final String collectionId;
    private final List<FieldFilter> equalityFilters;
    private final SortedSet<FieldFilter> inequalityFilters;
    private final List<OrderBy> orderBys;

    public TargetIndexMatcher(Target target) {
        String str;
        if (target.getCollectionGroup() != null) {
            str = target.getCollectionGroup();
        } else {
            str = target.getPath().getLastSegment();
        }
        this.collectionId = str;
        this.orderBys = target.getOrderBy();
        this.inequalityFilters = new TreeSet(new TargetIndexMatcher$$ExternalSyntheticLambda0());
        this.equalityFilters = new ArrayList();
        Iterator<Filter> it = target.getFilters().iterator();
        while (it.hasNext()) {
            FieldFilter fieldFilter = (FieldFilter) it.next();
            if (fieldFilter.isInequality()) {
                this.inequalityFilters.add(fieldFilter);
            } else {
                this.equalityFilters.add(fieldFilter);
            }
        }
    }

    public boolean hasMultipleInequality() {
        return this.inequalityFilters.size() > 1;
    }

    public boolean servedByIndex(FieldIndex index) {
        Assert.hardAssert(index.getCollectionGroup().equals(this.collectionId), "Collection IDs do not match", new Object[0]);
        if (hasMultipleInequality()) {
            return false;
        }
        FieldIndex.Segment arraySegment = index.getArraySegment();
        if (arraySegment != null && !hasMatchingEqualityFilter(arraySegment)) {
            return false;
        }
        Iterator<OrderBy> orderBys2 = this.orderBys.iterator();
        List<FieldIndex.Segment> segments = index.getDirectionalSegments();
        int segmentIndex = 0;
        Set<String> equalitySegments = new HashSet<>();
        while (segmentIndex < segments.size() && hasMatchingEqualityFilter(segments.get(segmentIndex))) {
            equalitySegments.add(segments.get(segmentIndex).getFieldPath().canonicalString());
            segmentIndex++;
        }
        if (segmentIndex == segments.size()) {
            return true;
        }
        if (this.inequalityFilters.size() > 0) {
            FieldFilter inequalityFilter = this.inequalityFilters.first();
            if (!equalitySegments.contains(inequalityFilter.getField().canonicalString())) {
                FieldIndex.Segment segment = segments.get(segmentIndex);
                if (!matchesFilter(inequalityFilter, segment) || !matchesOrderBy(orderBys2.next(), segment)) {
                    return false;
                }
            }
            segmentIndex++;
        }
        while (segmentIndex < segments.size()) {
            FieldIndex.Segment segment2 = segments.get(segmentIndex);
            if (!orderBys2.hasNext() || !matchesOrderBy(orderBys2.next(), segment2)) {
                return false;
            }
            segmentIndex++;
        }
        return true;
    }

    public FieldIndex buildTargetIndex() {
        FieldIndex.Segment.Kind kind;
        if (hasMultipleInequality()) {
            return null;
        }
        Set<FieldPath> uniqueFields = new HashSet<>();
        List<FieldIndex.Segment> segments = new ArrayList<>();
        for (FieldFilter filter : this.equalityFilters) {
            if (!filter.getField().isKeyField()) {
                if (filter.getOperator().equals(FieldFilter.Operator.ARRAY_CONTAINS) || filter.getOperator().equals(FieldFilter.Operator.ARRAY_CONTAINS_ANY)) {
                    segments.add(FieldIndex.Segment.create(filter.getField(), FieldIndex.Segment.Kind.CONTAINS));
                } else if (!uniqueFields.contains(filter.getField())) {
                    uniqueFields.add(filter.getField());
                    segments.add(FieldIndex.Segment.create(filter.getField(), FieldIndex.Segment.Kind.ASCENDING));
                }
            }
        }
        for (OrderBy orderBy : this.orderBys) {
            if (!orderBy.getField().isKeyField() && !uniqueFields.contains(orderBy.getField())) {
                uniqueFields.add(orderBy.getField());
                FieldPath field = orderBy.getField();
                if (orderBy.getDirection() == OrderBy.Direction.ASCENDING) {
                    kind = FieldIndex.Segment.Kind.ASCENDING;
                } else {
                    kind = FieldIndex.Segment.Kind.DESCENDING;
                }
                segments.add(FieldIndex.Segment.create(field, kind));
            }
        }
        return FieldIndex.create(-1, this.collectionId, segments, FieldIndex.INITIAL_STATE);
    }

    private boolean hasMatchingEqualityFilter(FieldIndex.Segment segment) {
        for (FieldFilter filter : this.equalityFilters) {
            if (matchesFilter(filter, segment)) {
                return true;
            }
        }
        return false;
    }

    private boolean matchesFilter(FieldFilter filter, FieldIndex.Segment segment) {
        if (filter == null || !filter.getField().equals(segment.getFieldPath())) {
            return false;
        }
        if (segment.getKind().equals(FieldIndex.Segment.Kind.CONTAINS) == (filter.getOperator().equals(FieldFilter.Operator.ARRAY_CONTAINS) || filter.getOperator().equals(FieldFilter.Operator.ARRAY_CONTAINS_ANY))) {
            return true;
        }
        return false;
    }

    private boolean matchesOrderBy(OrderBy orderBy, FieldIndex.Segment segment) {
        if (!orderBy.getField().equals(segment.getFieldPath())) {
            return false;
        }
        if ((!segment.getKind().equals(FieldIndex.Segment.Kind.ASCENDING) || !orderBy.getDirection().equals(OrderBy.Direction.ASCENDING)) && (!segment.getKind().equals(FieldIndex.Segment.Kind.DESCENDING) || !orderBy.getDirection().equals(OrderBy.Direction.DESCENDING))) {
            return false;
        }
        return true;
    }
}
