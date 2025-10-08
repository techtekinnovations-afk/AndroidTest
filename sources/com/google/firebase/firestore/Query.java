package com.google.firebase.firestore;

import android.app.Activity;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.core.ActivityScope;
import com.google.firebase.firestore.core.AsyncEventListener;
import com.google.firebase.firestore.core.Bound;
import com.google.firebase.firestore.core.CompositeFilter;
import com.google.firebase.firestore.core.EventManager;
import com.google.firebase.firestore.core.FieldFilter;
import com.google.firebase.firestore.core.Filter;
import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firebase.firestore.core.OrderBy;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.core.QueryListener;
import com.google.firebase.firestore.core.ViewSnapshot;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.model.ServerTimestamps;
import com.google.firebase.firestore.model.Values;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Executors;
import com.google.firebase.firestore.util.Preconditions;
import com.google.firebase.firestore.util.Util;
import com.google.firestore.v1.Value;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class Query {
    final FirebaseFirestore firestore;
    final com.google.firebase.firestore.core.Query query;

    public enum Direction {
        ASCENDING,
        DESCENDING
    }

    Query(com.google.firebase.firestore.core.Query query2, FirebaseFirestore firestore2) {
        this.query = (com.google.firebase.firestore.core.Query) Preconditions.checkNotNull(query2);
        this.firestore = (FirebaseFirestore) Preconditions.checkNotNull(firestore2);
    }

    public FirebaseFirestore getFirestore() {
        return this.firestore;
    }

    public Query whereEqualTo(String field, Object value) {
        return where(Filter.equalTo(field, value));
    }

    public Query whereEqualTo(FieldPath fieldPath, Object value) {
        return where(Filter.equalTo(fieldPath, value));
    }

    public Query whereNotEqualTo(String field, Object value) {
        return where(Filter.notEqualTo(field, value));
    }

    public Query whereNotEqualTo(FieldPath fieldPath, Object value) {
        return where(Filter.notEqualTo(fieldPath, value));
    }

    public Query whereLessThan(String field, Object value) {
        return where(Filter.lessThan(field, value));
    }

    public Query whereLessThan(FieldPath fieldPath, Object value) {
        return where(Filter.lessThan(fieldPath, value));
    }

    public Query whereLessThanOrEqualTo(String field, Object value) {
        return where(Filter.lessThanOrEqualTo(field, value));
    }

    public Query whereLessThanOrEqualTo(FieldPath fieldPath, Object value) {
        return where(Filter.lessThanOrEqualTo(fieldPath, value));
    }

    public Query whereGreaterThan(String field, Object value) {
        return where(Filter.greaterThan(field, value));
    }

    public Query whereGreaterThan(FieldPath fieldPath, Object value) {
        return where(Filter.greaterThan(fieldPath, value));
    }

    public Query whereGreaterThanOrEqualTo(String field, Object value) {
        return where(Filter.greaterThanOrEqualTo(field, value));
    }

    public Query whereGreaterThanOrEqualTo(FieldPath fieldPath, Object value) {
        return where(Filter.greaterThanOrEqualTo(fieldPath, value));
    }

    public Query whereArrayContains(String field, Object value) {
        return where(Filter.arrayContains(field, value));
    }

    public Query whereArrayContains(FieldPath fieldPath, Object value) {
        return where(Filter.arrayContains(fieldPath, value));
    }

    public Query whereArrayContainsAny(String field, List<? extends Object> values) {
        return where(Filter.arrayContainsAny(field, values));
    }

    public Query whereArrayContainsAny(FieldPath fieldPath, List<? extends Object> values) {
        return where(Filter.arrayContainsAny(fieldPath, values));
    }

    public Query whereIn(String field, List<? extends Object> values) {
        return where(Filter.inArray(field, values));
    }

    public Query whereIn(FieldPath fieldPath, List<? extends Object> values) {
        return where(Filter.inArray(fieldPath, values));
    }

    public Query whereNotIn(String field, List<? extends Object> values) {
        return where(Filter.notInArray(field, values));
    }

    public Query whereNotIn(FieldPath fieldPath, List<? extends Object> values) {
        return where(Filter.notInArray(fieldPath, values));
    }

    public Query where(Filter filter) {
        Filter parsedFilter = parseFilter(filter);
        if (parsedFilter.getFilters().isEmpty()) {
            return this;
        }
        validateNewFilter(parsedFilter);
        return new Query(this.query.filter(parsedFilter), this.firestore);
    }

    /* JADX WARNING: type inference failed for: r5v17, types: [com.google.protobuf.GeneratedMessageLite] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.google.firebase.firestore.core.FieldFilter parseFieldFilter(com.google.firebase.firestore.Filter.UnaryFilter r9) {
        /*
            r8 = this;
            com.google.firebase.firestore.FieldPath r0 = r9.getField()
            com.google.firebase.firestore.core.FieldFilter$Operator r1 = r9.getOperator()
            java.lang.Object r2 = r9.getValue()
            java.lang.String r3 = "Provided field path must not be null."
            com.google.firebase.firestore.util.Preconditions.checkNotNull(r0, r3)
            java.lang.String r3 = "Provided op must not be null."
            com.google.firebase.firestore.util.Preconditions.checkNotNull(r1, r3)
            com.google.firebase.firestore.model.FieldPath r3 = r0.getInternalPath()
            boolean r4 = r3.isKeyField()
            if (r4 == 0) goto L_0x008a
            com.google.firebase.firestore.core.FieldFilter$Operator r4 = com.google.firebase.firestore.core.FieldFilter.Operator.ARRAY_CONTAINS
            if (r1 == r4) goto L_0x0067
            com.google.firebase.firestore.core.FieldFilter$Operator r4 = com.google.firebase.firestore.core.FieldFilter.Operator.ARRAY_CONTAINS_ANY
            if (r1 == r4) goto L_0x0067
            com.google.firebase.firestore.core.FieldFilter$Operator r4 = com.google.firebase.firestore.core.FieldFilter.Operator.IN
            if (r1 == r4) goto L_0x0037
            com.google.firebase.firestore.core.FieldFilter$Operator r4 = com.google.firebase.firestore.core.FieldFilter.Operator.NOT_IN
            if (r1 != r4) goto L_0x0031
            goto L_0x0037
        L_0x0031:
            com.google.firestore.v1.Value r4 = r8.parseDocumentIdValue(r2)
            goto L_0x00af
        L_0x0037:
            r8.validateDisjunctiveFilterElements(r2, r1)
            com.google.firestore.v1.ArrayValue$Builder r4 = com.google.firestore.v1.ArrayValue.newBuilder()
            r5 = r2
            java.util.List r5 = (java.util.List) r5
            java.util.Iterator r5 = r5.iterator()
        L_0x0045:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x0057
            java.lang.Object r6 = r5.next()
            com.google.firestore.v1.Value r7 = r8.parseDocumentIdValue(r6)
            r4.addValues((com.google.firestore.v1.Value) r7)
            goto L_0x0045
        L_0x0057:
            com.google.firestore.v1.Value$Builder r5 = com.google.firestore.v1.Value.newBuilder()
            com.google.firestore.v1.Value$Builder r5 = r5.setArrayValue((com.google.firestore.v1.ArrayValue.Builder) r4)
            com.google.protobuf.GeneratedMessageLite r5 = r5.build()
            r4 = r5
            com.google.firestore.v1.Value r4 = (com.google.firestore.v1.Value) r4
            goto L_0x00af
        L_0x0067:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Invalid query. You can't perform '"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = r1.toString()
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = "' queries on FieldPath.documentId()."
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L_0x008a:
            com.google.firebase.firestore.core.FieldFilter$Operator r4 = com.google.firebase.firestore.core.FieldFilter.Operator.IN
            if (r1 == r4) goto L_0x0096
            com.google.firebase.firestore.core.FieldFilter$Operator r4 = com.google.firebase.firestore.core.FieldFilter.Operator.NOT_IN
            if (r1 == r4) goto L_0x0096
            com.google.firebase.firestore.core.FieldFilter$Operator r4 = com.google.firebase.firestore.core.FieldFilter.Operator.ARRAY_CONTAINS_ANY
            if (r1 != r4) goto L_0x0099
        L_0x0096:
            r8.validateDisjunctiveFilterElements(r2, r1)
        L_0x0099:
            com.google.firebase.firestore.FirebaseFirestore r4 = r8.firestore
            com.google.firebase.firestore.UserDataReader r4 = r4.getUserDataReader()
            com.google.firebase.firestore.core.FieldFilter$Operator r5 = com.google.firebase.firestore.core.FieldFilter.Operator.IN
            if (r1 == r5) goto L_0x00aa
            com.google.firebase.firestore.core.FieldFilter$Operator r5 = com.google.firebase.firestore.core.FieldFilter.Operator.NOT_IN
            if (r1 != r5) goto L_0x00a8
            goto L_0x00aa
        L_0x00a8:
            r5 = 0
            goto L_0x00ab
        L_0x00aa:
            r5 = 1
        L_0x00ab:
            com.google.firestore.v1.Value r4 = r4.parseQueryValue(r2, r5)
        L_0x00af:
            com.google.firebase.firestore.model.FieldPath r5 = r0.getInternalPath()
            com.google.firebase.firestore.core.FieldFilter r5 = com.google.firebase.firestore.core.FieldFilter.create(r5, r1, r4)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.Query.parseFieldFilter(com.google.firebase.firestore.Filter$UnaryFilter):com.google.firebase.firestore.core.FieldFilter");
    }

    private Filter parseCompositeFilter(Filter.CompositeFilter compositeFilterData) {
        List<com.google.firebase.firestore.core.Filter> parsedFilters = new ArrayList<>();
        for (Filter filter : compositeFilterData.getFilters()) {
            com.google.firebase.firestore.core.Filter parsedFilter = parseFilter(filter);
            if (!parsedFilter.getFilters().isEmpty()) {
                parsedFilters.add(parsedFilter);
            }
        }
        if (parsedFilters.size() == 1) {
            return parsedFilters.get(0);
        }
        return new CompositeFilter(parsedFilters, compositeFilterData.getOperator());
    }

    private com.google.firebase.firestore.core.Filter parseFilter(Filter filter) {
        Assert.hardAssert((filter instanceof Filter.UnaryFilter) || (filter instanceof Filter.CompositeFilter), "Parsing is only supported for Filter.UnaryFilter and Filter.CompositeFilter.", new Object[0]);
        if (filter instanceof Filter.UnaryFilter) {
            return parseFieldFilter((Filter.UnaryFilter) filter);
        }
        return parseCompositeFilter((Filter.CompositeFilter) filter);
    }

    private Value parseDocumentIdValue(Object documentIdValue) {
        if (documentIdValue instanceof String) {
            String documentId = (String) documentIdValue;
            if (documentId.isEmpty()) {
                throw new IllegalArgumentException("Invalid query. When querying with FieldPath.documentId() you must provide a valid document ID, but it was an empty string.");
            } else if (this.query.isCollectionGroupQuery() || !documentId.contains("/")) {
                ResourcePath path = (ResourcePath) this.query.getPath().append(ResourcePath.fromString(documentId));
                if (DocumentKey.isDocumentKey(path)) {
                    return Values.refValue(getFirestore().getDatabaseId(), DocumentKey.fromPath(path));
                }
                throw new IllegalArgumentException("Invalid query. When querying a collection group by FieldPath.documentId(), the value provided must result in a valid document path, but '" + path + "' is not because it has an odd number of segments (" + path.length() + ").");
            } else {
                throw new IllegalArgumentException("Invalid query. When querying a collection by FieldPath.documentId() you must provide a plain document ID, but '" + documentId + "' contains a '/' character.");
            }
        } else if (documentIdValue instanceof DocumentReference) {
            return Values.refValue(getFirestore().getDatabaseId(), ((DocumentReference) documentIdValue).getKey());
        } else {
            throw new IllegalArgumentException("Invalid query. When querying with FieldPath.documentId() you must provide a valid String or DocumentReference, but it was of type: " + Util.typeName(documentIdValue));
        }
    }

    private void validateDisjunctiveFilterElements(Object value, FieldFilter.Operator op) {
        if (!(value instanceof List) || ((List) value).size() == 0) {
            throw new IllegalArgumentException("Invalid Query. A non-empty array is required for '" + op.toString() + "' filters.");
        }
    }

    private List<FieldFilter.Operator> conflictingOps(FieldFilter.Operator op) {
        switch (op) {
            case NOT_EQUAL:
                return Arrays.asList(new FieldFilter.Operator[]{FieldFilter.Operator.NOT_EQUAL, FieldFilter.Operator.NOT_IN});
            case ARRAY_CONTAINS_ANY:
            case IN:
                return Arrays.asList(new FieldFilter.Operator[]{FieldFilter.Operator.NOT_IN});
            case NOT_IN:
                return Arrays.asList(new FieldFilter.Operator[]{FieldFilter.Operator.ARRAY_CONTAINS_ANY, FieldFilter.Operator.IN, FieldFilter.Operator.NOT_IN, FieldFilter.Operator.NOT_EQUAL});
            default:
                return new ArrayList();
        }
    }

    private void validateNewFieldFilter(com.google.firebase.firestore.core.Query query2, FieldFilter fieldFilter) {
        FieldFilter.Operator filterOp = fieldFilter.getOperator();
        FieldFilter.Operator conflictingOp = findOpInsideFilters(query2.getFilters(), conflictingOps(filterOp));
        if (conflictingOp == null) {
            return;
        }
        if (conflictingOp == filterOp) {
            throw new IllegalArgumentException("Invalid Query. You cannot use more than one '" + filterOp.toString() + "' filter.");
        }
        throw new IllegalArgumentException("Invalid Query. You cannot use '" + filterOp.toString() + "' filters with '" + conflictingOp.toString() + "' filters.");
    }

    private void validateNewFilter(com.google.firebase.firestore.core.Filter filter) {
        com.google.firebase.firestore.core.Query testQuery = this.query;
        for (FieldFilter subfilter : filter.getFlattenedFilters()) {
            validateNewFieldFilter(testQuery, subfilter);
            testQuery = testQuery.filter(subfilter);
        }
    }

    private FieldFilter.Operator findOpInsideFilters(List<com.google.firebase.firestore.core.Filter> filters, List<FieldFilter.Operator> operators) {
        for (com.google.firebase.firestore.core.Filter filter : filters) {
            Iterator<FieldFilter> it = filter.getFlattenedFilters().iterator();
            while (true) {
                if (it.hasNext()) {
                    FieldFilter fieldFilter = it.next();
                    if (operators.contains(fieldFilter.getOperator())) {
                        return fieldFilter.getOperator();
                    }
                }
            }
        }
        return null;
    }

    public Query orderBy(String field) {
        return orderBy(FieldPath.fromDotSeparatedPath(field), Direction.ASCENDING);
    }

    public Query orderBy(FieldPath fieldPath) {
        Preconditions.checkNotNull(fieldPath, "Provided field path must not be null.");
        return orderBy(fieldPath.getInternalPath(), Direction.ASCENDING);
    }

    public Query orderBy(String field, Direction direction) {
        return orderBy(FieldPath.fromDotSeparatedPath(field), direction);
    }

    public Query orderBy(FieldPath fieldPath, Direction direction) {
        Preconditions.checkNotNull(fieldPath, "Provided field path must not be null.");
        return orderBy(fieldPath.getInternalPath(), direction);
    }

    private Query orderBy(FieldPath fieldPath, Direction direction) {
        OrderBy.Direction dir;
        Preconditions.checkNotNull(direction, "Provided direction must not be null.");
        if (this.query.getStartAt() != null) {
            throw new IllegalArgumentException("Invalid query. You must not call Query.startAt() or Query.startAfter() before calling Query.orderBy().");
        } else if (this.query.getEndAt() == null) {
            if (direction == Direction.ASCENDING) {
                dir = OrderBy.Direction.ASCENDING;
            } else {
                dir = OrderBy.Direction.DESCENDING;
            }
            return new Query(this.query.orderBy(OrderBy.getInstance(dir, fieldPath)), this.firestore);
        } else {
            throw new IllegalArgumentException("Invalid query. You must not call Query.endAt() or Query.endBefore() before calling Query.orderBy().");
        }
    }

    public Query limit(long limit) {
        if (limit > 0) {
            return new Query(this.query.limitToFirst(limit), this.firestore);
        }
        throw new IllegalArgumentException("Invalid Query. Query limit (" + limit + ") is invalid. Limit must be positive.");
    }

    public Query limitToLast(long limit) {
        if (limit > 0) {
            return new Query(this.query.limitToLast(limit), this.firestore);
        }
        throw new IllegalArgumentException("Invalid Query. Query limitToLast (" + limit + ") is invalid. Limit must be positive.");
    }

    public Query startAt(DocumentSnapshot snapshot) {
        return new Query(this.query.startAt(boundFromDocumentSnapshot("startAt", snapshot, true)), this.firestore);
    }

    public Query startAt(Object... fieldValues) {
        return new Query(this.query.startAt(boundFromFields("startAt", fieldValues, true)), this.firestore);
    }

    public Query startAfter(DocumentSnapshot snapshot) {
        return new Query(this.query.startAt(boundFromDocumentSnapshot("startAfter", snapshot, false)), this.firestore);
    }

    public Query startAfter(Object... fieldValues) {
        return new Query(this.query.startAt(boundFromFields("startAfter", fieldValues, false)), this.firestore);
    }

    public Query endBefore(DocumentSnapshot snapshot) {
        return new Query(this.query.endAt(boundFromDocumentSnapshot("endBefore", snapshot, false)), this.firestore);
    }

    public Query endBefore(Object... fieldValues) {
        return new Query(this.query.endAt(boundFromFields("endBefore", fieldValues, false)), this.firestore);
    }

    public Query endAt(DocumentSnapshot snapshot) {
        return new Query(this.query.endAt(boundFromDocumentSnapshot("endAt", snapshot, true)), this.firestore);
    }

    public Query endAt(Object... fieldValues) {
        return new Query(this.query.endAt(boundFromFields("endAt", fieldValues, true)), this.firestore);
    }

    private Bound boundFromDocumentSnapshot(String methodName, DocumentSnapshot snapshot, boolean inclusive) {
        Preconditions.checkNotNull(snapshot, "Provided snapshot must not be null.");
        if (snapshot.exists()) {
            Document document = snapshot.getDocument();
            List<Value> components = new ArrayList<>();
            for (OrderBy orderBy : this.query.getNormalizedOrderBy()) {
                if (orderBy.getField().equals(FieldPath.KEY_PATH)) {
                    components.add(Values.refValue(this.firestore.getDatabaseId(), document.getKey()));
                } else {
                    Value value = document.getField(orderBy.getField());
                    if (ServerTimestamps.isServerTimestamp(value)) {
                        throw new IllegalArgumentException("Invalid query. You are trying to start or end a query using a document for which the field '" + orderBy.getField() + "' is an uncommitted server timestamp. (Since the value of this field is unknown, you cannot start/end a query with it.)");
                    } else if (value != null) {
                        components.add(value);
                    } else {
                        throw new IllegalArgumentException("Invalid query. You are trying to start or end a query using a document for which the field '" + orderBy.getField() + "' (used as the orderBy) does not exist.");
                    }
                }
            }
            return new Bound(components, inclusive);
        }
        throw new IllegalArgumentException("Can't use a DocumentSnapshot for a document that doesn't exist for " + methodName + "().");
    }

    private Bound boundFromFields(String methodName, Object[] values, boolean inclusive) {
        List<OrderBy> explicitOrderBy = this.query.getExplicitOrderBy();
        if (values.length <= explicitOrderBy.size()) {
            List<Value> components = new ArrayList<>();
            for (int i = 0; i < values.length; i++) {
                String str = values[i];
                if (!explicitOrderBy.get(i).getField().equals(FieldPath.KEY_PATH)) {
                    components.add(this.firestore.getUserDataReader().parseQueryValue(str));
                } else if (str instanceof String) {
                    String documentId = str;
                    if (this.query.isCollectionGroupQuery() || !documentId.contains("/")) {
                        ResourcePath path = (ResourcePath) this.query.getPath().append(ResourcePath.fromString(documentId));
                        if (DocumentKey.isDocumentKey(path)) {
                            components.add(Values.refValue(this.firestore.getDatabaseId(), DocumentKey.fromPath(path)));
                        } else {
                            throw new IllegalArgumentException("Invalid query. When querying a collection group and ordering by FieldPath.documentId(), the value passed to " + methodName + "() must result in a valid document path, but '" + path + "' is not because it contains an odd number of segments.");
                        }
                    } else {
                        throw new IllegalArgumentException("Invalid query. When querying a collection and ordering by FieldPath.documentId(), the value passed to " + methodName + "() must be a plain document ID, but '" + documentId + "' contains a slash.");
                    }
                } else {
                    throw new IllegalArgumentException("Invalid query. Expected a string for document ID in " + methodName + "(), but got " + str + ".");
                }
            }
            return new Bound(components, inclusive);
        }
        throw new IllegalArgumentException("Too many arguments provided to " + methodName + "(). The number of arguments must be less than or equal to the number of orderBy() clauses.");
    }

    public Task<QuerySnapshot> get() {
        return get(Source.DEFAULT);
    }

    public Task<QuerySnapshot> get(Source source) {
        validateHasExplicitOrderByForLimitToLast();
        if (source == Source.CACHE) {
            return ((Task) this.firestore.callClient(new Query$$ExternalSyntheticLambda3(this))).continueWith(Executors.DIRECT_EXECUTOR, new Query$$ExternalSyntheticLambda4(this));
        }
        return getViaSnapshotListener(source);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$get$0$com-google-firebase-firestore-Query  reason: not valid java name */
    public /* synthetic */ Task m1803lambda$get$0$comgooglefirebasefirestoreQuery(FirestoreClient client) {
        return client.getDocumentsFromLocalCache(this.query);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$get$1$com-google-firebase-firestore-Query  reason: not valid java name */
    public /* synthetic */ QuerySnapshot m1804lambda$get$1$comgooglefirebasefirestoreQuery(Task viewSnap) throws Exception {
        return new QuerySnapshot(new Query(this.query, this.firestore), (ViewSnapshot) viewSnap.getResult(), this.firestore);
    }

    private Task<QuerySnapshot> getViaSnapshotListener(Source source) {
        TaskCompletionSource<QuerySnapshot> res = new TaskCompletionSource<>();
        TaskCompletionSource<ListenerRegistration> registration = new TaskCompletionSource<>();
        EventManager.ListenOptions options = new EventManager.ListenOptions();
        options.includeDocumentMetadataChanges = true;
        options.includeQueryMetadataChanges = true;
        options.waitForSyncWhenOnline = true;
        registration.setResult(addSnapshotListenerInternal(Executors.DIRECT_EXECUTOR, options, (Activity) null, new Query$$ExternalSyntheticLambda2(res, registration, source)));
        return res.getTask();
    }

    static /* synthetic */ void lambda$getViaSnapshotListener$2(TaskCompletionSource res, TaskCompletionSource registration, Source source, QuerySnapshot snapshot, FirebaseFirestoreException error) {
        if (error != null) {
            res.setException(error);
            return;
        }
        try {
            ((ListenerRegistration) Tasks.await(registration.getTask())).remove();
            if (!snapshot.getMetadata().isFromCache() || source != Source.SERVER) {
                res.setResult(snapshot);
            } else {
                res.setException(new FirebaseFirestoreException("Failed to get documents from server. (However, these documents may exist in the local cache. Run again without setting source to SERVER to retrieve the cached documents.)", FirebaseFirestoreException.Code.UNAVAILABLE));
            }
        } catch (ExecutionException e) {
            throw Assert.fail(e, "Failed to register a listener for a query result", new Object[0]);
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            throw Assert.fail(e2, "Failed to register a listener for a query result", new Object[0]);
        }
    }

    public ListenerRegistration addSnapshotListener(EventListener<QuerySnapshot> listener) {
        return addSnapshotListener(MetadataChanges.EXCLUDE, listener);
    }

    public ListenerRegistration addSnapshotListener(Executor executor, EventListener<QuerySnapshot> listener) {
        return addSnapshotListener(executor, MetadataChanges.EXCLUDE, listener);
    }

    public ListenerRegistration addSnapshotListener(Activity activity, EventListener<QuerySnapshot> listener) {
        return addSnapshotListener(activity, MetadataChanges.EXCLUDE, listener);
    }

    public ListenerRegistration addSnapshotListener(MetadataChanges metadataChanges, EventListener<QuerySnapshot> listener) {
        return addSnapshotListener(Executors.DEFAULT_CALLBACK_EXECUTOR, metadataChanges, listener);
    }

    public ListenerRegistration addSnapshotListener(Executor executor, MetadataChanges metadataChanges, EventListener<QuerySnapshot> listener) {
        Preconditions.checkNotNull(executor, "Provided executor must not be null.");
        Preconditions.checkNotNull(metadataChanges, "Provided MetadataChanges value must not be null.");
        Preconditions.checkNotNull(listener, "Provided EventListener must not be null.");
        return addSnapshotListenerInternal(executor, internalOptions(metadataChanges), (Activity) null, listener);
    }

    public ListenerRegistration addSnapshotListener(Activity activity, MetadataChanges metadataChanges, EventListener<QuerySnapshot> listener) {
        Preconditions.checkNotNull(activity, "Provided activity must not be null.");
        Preconditions.checkNotNull(metadataChanges, "Provided MetadataChanges value must not be null.");
        Preconditions.checkNotNull(listener, "Provided EventListener must not be null.");
        return addSnapshotListenerInternal(Executors.DEFAULT_CALLBACK_EXECUTOR, internalOptions(metadataChanges), activity, listener);
    }

    public ListenerRegistration addSnapshotListener(SnapshotListenOptions options, EventListener<QuerySnapshot> listener) {
        Preconditions.checkNotNull(options, "Provided options value must not be null.");
        Preconditions.checkNotNull(listener, "Provided EventListener must not be null.");
        return addSnapshotListenerInternal(options.getExecutor(), internalOptions(options.getMetadataChanges(), options.getSource()), options.getActivity(), listener);
    }

    private ListenerRegistration addSnapshotListenerInternal(Executor executor, EventManager.ListenOptions options, Activity activity, EventListener<QuerySnapshot> userListener) {
        validateHasExplicitOrderByForLimitToLast();
        return (ListenerRegistration) this.firestore.callClient(new Query$$ExternalSyntheticLambda1(this, options, new AsyncEventListener<>(executor, new Query$$ExternalSyntheticLambda0(this, userListener)), activity));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$addSnapshotListenerInternal$3$com-google-firebase-firestore-Query  reason: not valid java name */
    public /* synthetic */ void m1801lambda$addSnapshotListenerInternal$3$comgooglefirebasefirestoreQuery(EventListener userListener, ViewSnapshot snapshot, FirebaseFirestoreException error) {
        if (error != null) {
            userListener.onEvent(null, error);
            return;
        }
        Assert.hardAssert(snapshot != null, "Got event without value or error set", new Object[0]);
        userListener.onEvent(new QuerySnapshot(this, snapshot, this.firestore), (FirebaseFirestoreException) null);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$addSnapshotListenerInternal$5$com-google-firebase-firestore-Query  reason: not valid java name */
    public /* synthetic */ ListenerRegistration m1802lambda$addSnapshotListenerInternal$5$comgooglefirebasefirestoreQuery(EventManager.ListenOptions options, AsyncEventListener asyncListener, Activity activity, FirestoreClient client) {
        return ActivityScope.bind(activity, new Query$$ExternalSyntheticLambda5(asyncListener, client, client.listen(this.query, options, asyncListener)));
    }

    static /* synthetic */ void lambda$addSnapshotListenerInternal$4(AsyncEventListener asyncListener, FirestoreClient client, QueryListener queryListener) {
        asyncListener.mute();
        client.stopListening(queryListener);
    }

    private void validateHasExplicitOrderByForLimitToLast() {
        if (this.query.getLimitType().equals(Query.LimitType.LIMIT_TO_LAST) && this.query.getExplicitOrderBy().isEmpty()) {
            throw new IllegalStateException("limitToLast() queries require specifying at least one orderBy() clause");
        }
    }

    public AggregateQuery count() {
        return new AggregateQuery(this, Collections.singletonList(AggregateField.count()));
    }

    public AggregateQuery aggregate(final AggregateField aggregateField, AggregateField... aggregateFields) {
        List<AggregateField> fields = new ArrayList<AggregateField>() {
            {
                add(aggregateField);
            }
        };
        fields.addAll(Arrays.asList(aggregateFields));
        return new AggregateQuery(this, fields);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Query)) {
            return false;
        }
        Query that = (Query) o;
        if (!this.query.equals(that.query) || !this.firestore.equals(that.firestore)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (this.query.hashCode() * 31) + this.firestore.hashCode();
    }

    private static EventManager.ListenOptions internalOptions(MetadataChanges metadataChanges) {
        return internalOptions(metadataChanges, ListenSource.DEFAULT);
    }

    private static EventManager.ListenOptions internalOptions(MetadataChanges metadataChanges, ListenSource source) {
        EventManager.ListenOptions internalOptions = new EventManager.ListenOptions();
        boolean z = true;
        internalOptions.includeDocumentMetadataChanges = metadataChanges == MetadataChanges.INCLUDE;
        if (metadataChanges != MetadataChanges.INCLUDE) {
            z = false;
        }
        internalOptions.includeQueryMetadataChanges = z;
        internalOptions.waitForSyncWhenOnline = false;
        internalOptions.source = source;
        return internalOptions;
    }
}
