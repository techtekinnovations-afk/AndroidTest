package com.google.firebase.firestore.core;

import com.google.firebase.firestore.core.OrderBy;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.util.Assert;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public final class Query {
    private static final OrderBy KEY_ORDERING_ASC = OrderBy.getInstance(OrderBy.Direction.ASCENDING, FieldPath.KEY_PATH);
    private static final OrderBy KEY_ORDERING_DESC = OrderBy.getInstance(OrderBy.Direction.DESCENDING, FieldPath.KEY_PATH);
    private final String collectionGroup;
    private final Bound endAt;
    private final List<OrderBy> explicitSortOrder;
    private final List<Filter> filters;
    private final long limit;
    private final LimitType limitType;
    private Target memoizedAggregateTarget;
    private List<OrderBy> memoizedNormalizedOrderBys;
    private Target memoizedTarget;
    private final ResourcePath path;
    private final Bound startAt;

    public enum LimitType {
        LIMIT_TO_FIRST,
        LIMIT_TO_LAST
    }

    public static Query atPath(ResourcePath path2) {
        return new Query(path2, (String) null);
    }

    public Query(ResourcePath path2, String collectionGroup2, List<Filter> filters2, List<OrderBy> explicitSortOrder2, long limit2, LimitType limitType2, Bound startAt2, Bound endAt2) {
        this.path = path2;
        this.collectionGroup = collectionGroup2;
        this.explicitSortOrder = explicitSortOrder2;
        this.filters = filters2;
        this.limit = limit2;
        this.limitType = limitType2;
        this.startAt = startAt2;
        this.endAt = endAt2;
    }

    public Query(ResourcePath path2, String collectionGroup2) {
        this(path2, collectionGroup2, Collections.emptyList(), Collections.emptyList(), -1, LimitType.LIMIT_TO_FIRST, (Bound) null, (Bound) null);
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

    public boolean isCollectionGroupQuery() {
        return this.collectionGroup != null;
    }

    public boolean matchesAllDocuments() {
        if (this.filters.isEmpty() && this.limit == -1 && this.startAt == null && this.endAt == null) {
            return getExplicitOrderBy().isEmpty() || (getExplicitOrderBy().size() == 1 && getExplicitOrderBy().get(0).field.isKeyField());
        }
        return false;
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

    public LimitType getLimitType() {
        return this.limitType;
    }

    public Bound getStartAt() {
        return this.startAt;
    }

    public Bound getEndAt() {
        return this.endAt;
    }

    public SortedSet<FieldPath> getInequalityFilterFields() {
        SortedSet<FieldPath> result = new TreeSet<>();
        for (Filter filter : getFilters()) {
            for (FieldFilter subFilter : filter.getFlattenedFilters()) {
                if (subFilter.isInequality()) {
                    result.add(subFilter.getField());
                }
            }
        }
        return result;
    }

    public Query filter(Filter filter) {
        Assert.hardAssert(!isDocumentQuery(), "No filter is allowed for document query", new Object[0]);
        List<Filter> updatedFilter = new ArrayList<>(this.filters);
        updatedFilter.add(filter);
        return new Query(this.path, this.collectionGroup, updatedFilter, this.explicitSortOrder, this.limit, this.limitType, this.startAt, this.endAt);
    }

    public Query orderBy(OrderBy order) {
        Assert.hardAssert(!isDocumentQuery(), "No ordering is allowed for document query", new Object[0]);
        List<OrderBy> updatedSortOrder = new ArrayList<>(this.explicitSortOrder);
        updatedSortOrder.add(order);
        return new Query(this.path, this.collectionGroup, this.filters, updatedSortOrder, this.limit, this.limitType, this.startAt, this.endAt);
    }

    public Query limitToFirst(long limit2) {
        return new Query(this.path, this.collectionGroup, this.filters, this.explicitSortOrder, limit2, LimitType.LIMIT_TO_FIRST, this.startAt, this.endAt);
    }

    public Query limitToLast(long limit2) {
        return new Query(this.path, this.collectionGroup, this.filters, this.explicitSortOrder, limit2, LimitType.LIMIT_TO_LAST, this.startAt, this.endAt);
    }

    public Query startAt(Bound bound) {
        return new Query(this.path, this.collectionGroup, this.filters, this.explicitSortOrder, this.limit, this.limitType, bound, this.endAt);
    }

    public Query endAt(Bound bound) {
        return new Query(this.path, this.collectionGroup, this.filters, this.explicitSortOrder, this.limit, this.limitType, this.startAt, bound);
    }

    public Query asCollectionQueryAtPath(ResourcePath path2) {
        return new Query(path2, (String) null, this.filters, this.explicitSortOrder, this.limit, this.limitType, this.startAt, this.endAt);
    }

    public List<OrderBy> getExplicitOrderBy() {
        return this.explicitSortOrder;
    }

    public synchronized List<OrderBy> getNormalizedOrderBy() {
        OrderBy.Direction lastDirection;
        if (this.memoizedNormalizedOrderBys == null) {
            List<OrderBy> res = new ArrayList<>();
            HashSet<String> fieldsNormalized = new HashSet<>();
            for (OrderBy explicit : this.explicitSortOrder) {
                res.add(explicit);
                fieldsNormalized.add(explicit.field.canonicalString());
            }
            if (this.explicitSortOrder.size() > 0) {
                lastDirection = this.explicitSortOrder.get(this.explicitSortOrder.size() - 1).getDirection();
            } else {
                lastDirection = OrderBy.Direction.ASCENDING;
            }
            for (FieldPath field : getInequalityFilterFields()) {
                if (!fieldsNormalized.contains(field.canonicalString()) && !field.isKeyField()) {
                    res.add(OrderBy.getInstance(lastDirection, field));
                }
            }
            if (!fieldsNormalized.contains(FieldPath.KEY_PATH.canonicalString())) {
                res.add(lastDirection.equals(OrderBy.Direction.ASCENDING) ? KEY_ORDERING_ASC : KEY_ORDERING_DESC);
            }
            this.memoizedNormalizedOrderBys = Collections.unmodifiableList(res);
        }
        return this.memoizedNormalizedOrderBys;
    }

    private boolean matchesPathAndCollectionGroup(Document doc) {
        ResourcePath docPath = doc.getKey().getPath();
        if (this.collectionGroup != null) {
            if (!doc.getKey().hasCollectionId(this.collectionGroup) || !this.path.isPrefixOf(docPath)) {
                return false;
            }
            return true;
        } else if (DocumentKey.isDocumentKey(this.path)) {
            return this.path.equals(docPath);
        } else {
            if (!this.path.isPrefixOf(docPath) || this.path.length() != docPath.length() - 1) {
                return false;
            }
            return true;
        }
    }

    private boolean matchesFilters(Document doc) {
        for (Filter filter : this.filters) {
            if (!filter.matches(doc)) {
                return false;
            }
        }
        return true;
    }

    private boolean matchesOrderBy(Document doc) {
        for (OrderBy order : getNormalizedOrderBy()) {
            if (!order.getField().equals(FieldPath.KEY_PATH) && doc.getField(order.field) == null) {
                return false;
            }
        }
        return true;
    }

    private boolean matchesBounds(Document doc) {
        if (this.startAt != null && !this.startAt.sortsBeforeDocument(getNormalizedOrderBy(), doc)) {
            return false;
        }
        if (this.endAt == null || this.endAt.sortsAfterDocument(getNormalizedOrderBy(), doc)) {
            return true;
        }
        return false;
    }

    public boolean matches(Document doc) {
        return doc.isFoundDocument() && matchesPathAndCollectionGroup(doc) && matchesOrderBy(doc) && matchesFilters(doc) && matchesBounds(doc);
    }

    public Comparator<Document> comparator() {
        return new QueryComparator(getNormalizedOrderBy());
    }

    private static class QueryComparator implements Comparator<Document> {
        private final List<OrderBy> sortOrder;

        QueryComparator(List<OrderBy> order) {
            boolean hasKeyOrdering = false;
            for (OrderBy orderBy : order) {
                hasKeyOrdering = hasKeyOrdering || orderBy.getField().equals(FieldPath.KEY_PATH);
            }
            if (hasKeyOrdering) {
                this.sortOrder = order;
                return;
            }
            throw new IllegalArgumentException("QueryComparator needs to have a key ordering");
        }

        public int compare(Document doc1, Document doc2) {
            for (OrderBy order : this.sortOrder) {
                int comp = order.compare(doc1, doc2);
                if (comp != 0) {
                    return comp;
                }
            }
            return 0;
        }
    }

    public synchronized Target toTarget() {
        if (this.memoizedTarget == null) {
            this.memoizedTarget = toTarget(getNormalizedOrderBy());
        }
        return this.memoizedTarget;
    }

    /* JADX INFO: finally extract failed */
    private synchronized Target toTarget(List<OrderBy> orderBys) {
        Bound newEndAt;
        OrderBy.Direction dir;
        try {
            if (this.limitType == LimitType.LIMIT_TO_FIRST) {
                return new Target(getPath(), getCollectionGroup(), getFilters(), orderBys, this.limit, getStartAt(), getEndAt());
            }
            ArrayList<OrderBy> newOrderBy = new ArrayList<>();
            for (OrderBy orderBy : orderBys) {
                if (orderBy.getDirection() == OrderBy.Direction.DESCENDING) {
                    dir = OrderBy.Direction.ASCENDING;
                } else {
                    dir = OrderBy.Direction.DESCENDING;
                }
                newOrderBy.add(OrderBy.getInstance(dir, orderBy.getField()));
            }
            Bound newStartAt = this.endAt != null ? new Bound(this.endAt.getPosition(), this.endAt.isInclusive()) : null;
            if (this.startAt != null) {
                newEndAt = new Bound(this.startAt.getPosition(), this.startAt.isInclusive());
            } else {
                newEndAt = null;
            }
            return new Target(getPath(), getCollectionGroup(), getFilters(), newOrderBy, this.limit, newStartAt, newEndAt);
        } catch (Throwable th) {
            while (true) {
                throw th;
            }
        }
    }

    public synchronized Target toAggregateTarget() {
        if (this.memoizedAggregateTarget == null) {
            this.memoizedAggregateTarget = toTarget(this.explicitSortOrder);
        }
        return this.memoizedAggregateTarget;
    }

    public String getCanonicalId() {
        return toTarget().getCanonicalId() + "|lt:" + this.limitType;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Query query = (Query) o;
        if (this.limitType != query.limitType) {
            return false;
        }
        return toTarget().equals(query.toTarget());
    }

    public int hashCode() {
        return (toTarget().hashCode() * 31) + this.limitType.hashCode();
    }

    public String toString() {
        return "Query(target=" + toTarget().toString() + ";limitType=" + this.limitType.toString() + ")";
    }
}
