package com.google.firebase.firestore.remote;

import com.google.firebase.firestore.AggregateField;
import com.google.firebase.firestore.core.CompositeFilter;
import com.google.firebase.firestore.core.FieldFilter;
import com.google.firebase.firestore.core.Filter;
import com.google.firebase.firestore.core.OrderBy;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.local.QueryPurpose;
import com.google.firebase.firestore.local.TargetData;
import com.google.firebase.firestore.model.DatabaseId;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.MutableDocument;
import com.google.firebase.firestore.model.ObjectValue;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.model.Values;
import com.google.firebase.firestore.model.mutation.ArrayTransformOperation;
import com.google.firebase.firestore.model.mutation.DeleteMutation;
import com.google.firebase.firestore.model.mutation.FieldMask;
import com.google.firebase.firestore.model.mutation.FieldTransform;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.MutationResult;
import com.google.firebase.firestore.model.mutation.NumericIncrementTransformOperation;
import com.google.firebase.firestore.model.mutation.PatchMutation;
import com.google.firebase.firestore.model.mutation.Precondition;
import com.google.firebase.firestore.model.mutation.ServerTimestampOperation;
import com.google.firebase.firestore.model.mutation.SetMutation;
import com.google.firebase.firestore.model.mutation.TransformOperation;
import com.google.firebase.firestore.model.mutation.VerifyMutation;
import com.google.firebase.firestore.remote.WatchChange;
import com.google.firebase.firestore.util.Assert;
import com.google.firestore.v1.ArrayValue;
import com.google.firestore.v1.BatchGetDocumentsResponse;
import com.google.firestore.v1.Cursor;
import com.google.firestore.v1.Document;
import com.google.firestore.v1.DocumentChange;
import com.google.firestore.v1.DocumentDelete;
import com.google.firestore.v1.DocumentMask;
import com.google.firestore.v1.DocumentRemove;
import com.google.firestore.v1.DocumentTransform;
import com.google.firestore.v1.ExistenceFilter;
import com.google.firestore.v1.ListenResponse;
import com.google.firestore.v1.Precondition;
import com.google.firestore.v1.StructuredAggregationQuery;
import com.google.firestore.v1.StructuredQuery;
import com.google.firestore.v1.Target;
import com.google.firestore.v1.TargetChange;
import com.google.firestore.v1.Value;
import com.google.firestore.v1.Write;
import com.google.firestore.v1.WriteResult;
import com.google.protobuf.Int32Value;
import com.google.protobuf.Timestamp;
import io.grpc.Status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class RemoteSerializer {
    private final DatabaseId databaseId;
    private final String databaseName;

    public RemoteSerializer(DatabaseId databaseId2) {
        this.databaseId = databaseId2;
        this.databaseName = encodedDatabaseId(databaseId2).canonicalString();
    }

    public Timestamp encodeTimestamp(com.google.firebase.Timestamp timestamp) {
        Timestamp.Builder builder = Timestamp.newBuilder();
        builder.setSeconds(timestamp.getSeconds());
        builder.setNanos(timestamp.getNanoseconds());
        return (Timestamp) builder.build();
    }

    public com.google.firebase.Timestamp decodeTimestamp(Timestamp proto) {
        return new com.google.firebase.Timestamp(proto.getSeconds(), proto.getNanos());
    }

    public Timestamp encodeVersion(SnapshotVersion version) {
        return encodeTimestamp(version.getTimestamp());
    }

    public SnapshotVersion decodeVersion(Timestamp proto) {
        if (proto.getSeconds() == 0 && proto.getNanos() == 0) {
            return SnapshotVersion.NONE;
        }
        return new SnapshotVersion(decodeTimestamp(proto));
    }

    public String encodeKey(DocumentKey key) {
        return encodeResourceName(this.databaseId, key.getPath());
    }

    public DocumentKey decodeKey(String name) {
        ResourcePath resource = decodeResourceName(name);
        Assert.hardAssert(resource.getSegment(1).equals(this.databaseId.getProjectId()), "Tried to deserialize key from different project.", new Object[0]);
        Assert.hardAssert(resource.getSegment(3).equals(this.databaseId.getDatabaseId()), "Tried to deserialize key from different database.", new Object[0]);
        return DocumentKey.fromPath(extractLocalPathFromResourceName(resource));
    }

    private String encodeQueryPath(ResourcePath path) {
        return encodeResourceName(this.databaseId, path);
    }

    private ResourcePath decodeQueryPath(String name) {
        ResourcePath resource = decodeResourceName(name);
        if (resource.length() == 4) {
            return ResourcePath.EMPTY;
        }
        return extractLocalPathFromResourceName(resource);
    }

    private String encodeResourceName(DatabaseId databaseId2, ResourcePath path) {
        return ((ResourcePath) ((ResourcePath) encodedDatabaseId(databaseId2).append("documents")).append(path)).canonicalString();
    }

    private ResourcePath decodeResourceName(String encoded) {
        ResourcePath resource = ResourcePath.fromString(encoded);
        Assert.hardAssert(isValidResourceName(resource), "Tried to deserialize invalid key %s", resource);
        return resource;
    }

    private static ResourcePath encodedDatabaseId(DatabaseId databaseId2) {
        return ResourcePath.fromSegments(Arrays.asList(new String[]{"projects", databaseId2.getProjectId(), "databases", databaseId2.getDatabaseId()}));
    }

    private static ResourcePath extractLocalPathFromResourceName(ResourcePath resourceName) {
        Assert.hardAssert(resourceName.length() > 4 && resourceName.getSegment(4).equals("documents"), "Tried to deserialize invalid key %s", resourceName);
        return (ResourcePath) resourceName.popFirst(5);
    }

    private static boolean isValidResourceName(ResourcePath path) {
        if (path.length() < 4 || !path.getSegment(0).equals("projects") || !path.getSegment(2).equals("databases")) {
            return false;
        }
        return true;
    }

    public boolean isLocalResourceName(ResourcePath path) {
        if (!isValidResourceName(path) || !path.getSegment(1).equals(this.databaseId.getProjectId()) || !path.getSegment(3).equals(this.databaseId.getDatabaseId())) {
            return false;
        }
        return true;
    }

    public String databaseName() {
        return this.databaseName;
    }

    public Document encodeDocument(DocumentKey key, ObjectValue value) {
        Document.Builder builder = Document.newBuilder();
        builder.setName(encodeKey(key));
        builder.putAllFields(value.getFieldsMap());
        return (Document) builder.build();
    }

    public MutableDocument decodeMaybeDocument(BatchGetDocumentsResponse response) {
        if (response.getResultCase().equals(BatchGetDocumentsResponse.ResultCase.FOUND)) {
            return decodeFoundDocument(response);
        }
        if (response.getResultCase().equals(BatchGetDocumentsResponse.ResultCase.MISSING)) {
            return decodeMissingDocument(response);
        }
        throw new IllegalArgumentException("Unknown result case: " + response.getResultCase());
    }

    private MutableDocument decodeFoundDocument(BatchGetDocumentsResponse response) {
        Assert.hardAssert(response.getResultCase().equals(BatchGetDocumentsResponse.ResultCase.FOUND), "Tried to deserialize a found document from a missing document.", new Object[0]);
        DocumentKey key = decodeKey(response.getFound().getName());
        ObjectValue value = ObjectValue.fromMap(response.getFound().getFieldsMap());
        SnapshotVersion version = decodeVersion(response.getFound().getUpdateTime());
        Assert.hardAssert(!version.equals(SnapshotVersion.NONE), "Got a document response with no snapshot version", new Object[0]);
        return MutableDocument.newFoundDocument(key, version, value);
    }

    private MutableDocument decodeMissingDocument(BatchGetDocumentsResponse response) {
        Assert.hardAssert(response.getResultCase().equals(BatchGetDocumentsResponse.ResultCase.MISSING), "Tried to deserialize a missing document from a found document.", new Object[0]);
        DocumentKey key = decodeKey(response.getMissing());
        SnapshotVersion version = decodeVersion(response.getReadTime());
        Assert.hardAssert(!version.equals(SnapshotVersion.NONE), "Got a no document response with no snapshot version", new Object[0]);
        return MutableDocument.newNoDocument(key, version);
    }

    public Write encodeMutation(Mutation mutation) {
        Write.Builder builder = Write.newBuilder();
        if (mutation instanceof SetMutation) {
            builder.setUpdate(encodeDocument(mutation.getKey(), ((SetMutation) mutation).getValue()));
        } else if (mutation instanceof PatchMutation) {
            builder.setUpdate(encodeDocument(mutation.getKey(), ((PatchMutation) mutation).getValue()));
            builder.setUpdateMask(encodeDocumentMask(mutation.getFieldMask()));
        } else if (mutation instanceof DeleteMutation) {
            builder.setDelete(encodeKey(mutation.getKey()));
        } else if (mutation instanceof VerifyMutation) {
            builder.setVerify(encodeKey(mutation.getKey()));
        } else {
            throw Assert.fail("unknown mutation type %s", mutation.getClass());
        }
        for (FieldTransform fieldTransform : mutation.getFieldTransforms()) {
            builder.addUpdateTransforms(encodeFieldTransform(fieldTransform));
        }
        if (!mutation.getPrecondition().isNone()) {
            builder.setCurrentDocument(encodePrecondition(mutation.getPrecondition()));
        }
        return (Write) builder.build();
    }

    public Mutation decodeMutation(Write mutation) {
        Precondition precondition;
        if (mutation.hasCurrentDocument()) {
            precondition = decodePrecondition(mutation.getCurrentDocument());
        } else {
            precondition = Precondition.NONE;
        }
        Precondition precondition2 = precondition;
        List<FieldTransform> fieldTransforms = new ArrayList<>();
        for (DocumentTransform.FieldTransform fieldTransform : mutation.getUpdateTransformsList()) {
            fieldTransforms.add(decodeFieldTransform(fieldTransform));
        }
        switch (mutation.getOperationCase()) {
            case UPDATE:
                if (mutation.hasUpdateMask()) {
                    return new PatchMutation(decodeKey(mutation.getUpdate().getName()), ObjectValue.fromMap(mutation.getUpdate().getFieldsMap()), decodeDocumentMask(mutation.getUpdateMask()), precondition2, fieldTransforms);
                }
                return new SetMutation(decodeKey(mutation.getUpdate().getName()), ObjectValue.fromMap(mutation.getUpdate().getFieldsMap()), precondition2, fieldTransforms);
            case DELETE:
                return new DeleteMutation(decodeKey(mutation.getDelete()), precondition2);
            case VERIFY:
                return new VerifyMutation(decodeKey(mutation.getVerify()), precondition2);
            default:
                throw Assert.fail("Unknown mutation operation: %d", mutation.getOperationCase());
        }
    }

    private com.google.firestore.v1.Precondition encodePrecondition(Precondition precondition) {
        Assert.hardAssert(!precondition.isNone(), "Can't serialize an empty precondition", new Object[0]);
        Precondition.Builder builder = com.google.firestore.v1.Precondition.newBuilder();
        if (precondition.getUpdateTime() != null) {
            return (com.google.firestore.v1.Precondition) builder.setUpdateTime(encodeVersion(precondition.getUpdateTime())).build();
        }
        if (precondition.getExists() != null) {
            return (com.google.firestore.v1.Precondition) builder.setExists(precondition.getExists().booleanValue()).build();
        }
        throw Assert.fail("Unknown Precondition", new Object[0]);
    }

    private com.google.firebase.firestore.model.mutation.Precondition decodePrecondition(com.google.firestore.v1.Precondition precondition) {
        switch (precondition.getConditionTypeCase()) {
            case UPDATE_TIME:
                return com.google.firebase.firestore.model.mutation.Precondition.updateTime(decodeVersion(precondition.getUpdateTime()));
            case EXISTS:
                return com.google.firebase.firestore.model.mutation.Precondition.exists(precondition.getExists());
            case CONDITIONTYPE_NOT_SET:
                return com.google.firebase.firestore.model.mutation.Precondition.NONE;
            default:
                throw Assert.fail("Unknown precondition", new Object[0]);
        }
    }

    private DocumentMask encodeDocumentMask(FieldMask mask) {
        DocumentMask.Builder builder = DocumentMask.newBuilder();
        for (FieldPath path : mask.getMask()) {
            builder.addFieldPaths(path.canonicalString());
        }
        return (DocumentMask) builder.build();
    }

    private FieldMask decodeDocumentMask(DocumentMask mask) {
        int count = mask.getFieldPathsCount();
        Set<FieldPath> paths = new HashSet<>(count);
        for (int i = 0; i < count; i++) {
            paths.add(FieldPath.fromServerFormat(mask.getFieldPaths(i)));
        }
        return FieldMask.fromSet(paths);
    }

    private DocumentTransform.FieldTransform encodeFieldTransform(FieldTransform fieldTransform) {
        TransformOperation transform = fieldTransform.getOperation();
        if (transform instanceof ServerTimestampOperation) {
            return (DocumentTransform.FieldTransform) DocumentTransform.FieldTransform.newBuilder().setFieldPath(fieldTransform.getFieldPath().canonicalString()).setSetToServerValue(DocumentTransform.FieldTransform.ServerValue.REQUEST_TIME).build();
        }
        if (transform instanceof ArrayTransformOperation.Union) {
            return (DocumentTransform.FieldTransform) DocumentTransform.FieldTransform.newBuilder().setFieldPath(fieldTransform.getFieldPath().canonicalString()).setAppendMissingElements(ArrayValue.newBuilder().addAllValues(((ArrayTransformOperation.Union) transform).getElements())).build();
        }
        if (transform instanceof ArrayTransformOperation.Remove) {
            return (DocumentTransform.FieldTransform) DocumentTransform.FieldTransform.newBuilder().setFieldPath(fieldTransform.getFieldPath().canonicalString()).setRemoveAllFromArray(ArrayValue.newBuilder().addAllValues(((ArrayTransformOperation.Remove) transform).getElements())).build();
        }
        if (transform instanceof NumericIncrementTransformOperation) {
            return (DocumentTransform.FieldTransform) DocumentTransform.FieldTransform.newBuilder().setFieldPath(fieldTransform.getFieldPath().canonicalString()).setIncrement(((NumericIncrementTransformOperation) transform).getOperand()).build();
        }
        throw Assert.fail("Unknown transform: %s", transform);
    }

    private FieldTransform decodeFieldTransform(DocumentTransform.FieldTransform fieldTransform) {
        switch (fieldTransform.getTransformTypeCase()) {
            case SET_TO_SERVER_VALUE:
                Assert.hardAssert(fieldTransform.getSetToServerValue() == DocumentTransform.FieldTransform.ServerValue.REQUEST_TIME, "Unknown transform setToServerValue: %s", fieldTransform.getSetToServerValue());
                return new FieldTransform(FieldPath.fromServerFormat(fieldTransform.getFieldPath()), ServerTimestampOperation.getInstance());
            case APPEND_MISSING_ELEMENTS:
                return new FieldTransform(FieldPath.fromServerFormat(fieldTransform.getFieldPath()), new ArrayTransformOperation.Union(fieldTransform.getAppendMissingElements().getValuesList()));
            case REMOVE_ALL_FROM_ARRAY:
                return new FieldTransform(FieldPath.fromServerFormat(fieldTransform.getFieldPath()), new ArrayTransformOperation.Remove(fieldTransform.getRemoveAllFromArray().getValuesList()));
            case INCREMENT:
                return new FieldTransform(FieldPath.fromServerFormat(fieldTransform.getFieldPath()), new NumericIncrementTransformOperation(fieldTransform.getIncrement()));
            default:
                throw Assert.fail("Unknown FieldTransform proto: %s", fieldTransform);
        }
    }

    public MutationResult decodeMutationResult(WriteResult proto, SnapshotVersion commitVersion) {
        SnapshotVersion version = decodeVersion(proto.getUpdateTime());
        if (SnapshotVersion.NONE.equals(version)) {
            version = commitVersion;
        }
        int transformResultsCount = proto.getTransformResultsCount();
        List<Value> transformResults = new ArrayList<>(transformResultsCount);
        for (int i = 0; i < transformResultsCount; i++) {
            transformResults.add(proto.getTransformResults(i));
        }
        return new MutationResult(version, transformResults);
    }

    public Map<String, String> encodeListenRequestLabels(TargetData targetData) {
        String value = encodeLabel(targetData.getPurpose());
        if (value == null) {
            return null;
        }
        Map<String, String> result = new HashMap<>(1);
        result.put("goog-listen-tags", value);
        return result;
    }

    private String encodeLabel(QueryPurpose purpose) {
        switch (purpose) {
            case LISTEN:
                return null;
            case EXISTENCE_FILTER_MISMATCH:
                return "existence-filter-mismatch";
            case EXISTENCE_FILTER_MISMATCH_BLOOM:
                return "existence-filter-mismatch-bloom";
            case LIMBO_RESOLUTION:
                return "limbo-document";
            default:
                throw Assert.fail("Unrecognized query purpose: %s", purpose);
        }
    }

    public Target encodeTarget(TargetData targetData) {
        Target.Builder builder = Target.newBuilder();
        com.google.firebase.firestore.core.Target target = targetData.getTarget();
        if (target.isDocumentQuery()) {
            builder.setDocuments(encodeDocumentsTarget(target));
        } else {
            builder.setQuery(encodeQueryTarget(target));
        }
        builder.setTargetId(targetData.getTargetId());
        if (!targetData.getResumeToken().isEmpty() || targetData.getSnapshotVersion().compareTo(SnapshotVersion.NONE) <= 0) {
            builder.setResumeToken(targetData.getResumeToken());
        } else {
            builder.setReadTime(encodeTimestamp(targetData.getSnapshotVersion().getTimestamp()));
        }
        if (targetData.getExpectedCount() != null && (!targetData.getResumeToken().isEmpty() || targetData.getSnapshotVersion().compareTo(SnapshotVersion.NONE) > 0)) {
            builder.setExpectedCount(Int32Value.newBuilder().setValue(targetData.getExpectedCount().intValue()));
        }
        return (Target) builder.build();
    }

    public Target.DocumentsTarget encodeDocumentsTarget(com.google.firebase.firestore.core.Target target) {
        Target.DocumentsTarget.Builder builder = Target.DocumentsTarget.newBuilder();
        builder.addDocuments(encodeQueryPath(target.getPath()));
        return (Target.DocumentsTarget) builder.build();
    }

    public com.google.firebase.firestore.core.Target decodeDocumentsTarget(Target.DocumentsTarget target) {
        int count = target.getDocumentsCount();
        boolean z = true;
        if (count != 1) {
            z = false;
        }
        Assert.hardAssert(z, "DocumentsTarget contained other than 1 document %d", Integer.valueOf(count));
        return Query.atPath(decodeQueryPath(target.getDocuments(0))).toTarget();
    }

    public Target.QueryTarget encodeQueryTarget(com.google.firebase.firestore.core.Target target) {
        Target.QueryTarget.Builder builder = Target.QueryTarget.newBuilder();
        StructuredQuery.Builder structuredQueryBuilder = StructuredQuery.newBuilder();
        ResourcePath path = target.getPath();
        if (target.getCollectionGroup() != null) {
            Assert.hardAssert(path.length() % 2 == 0, "Collection Group queries should be within a document path or root.", new Object[0]);
            builder.setParent(encodeQueryPath(path));
            StructuredQuery.CollectionSelector.Builder from = StructuredQuery.CollectionSelector.newBuilder();
            from.setCollectionId(target.getCollectionGroup());
            from.setAllDescendants(true);
            structuredQueryBuilder.addFrom(from);
        } else {
            Assert.hardAssert(path.length() % 2 != 0, "Document queries with filters are not supported.", new Object[0]);
            builder.setParent(encodeQueryPath((ResourcePath) path.popLast()));
            StructuredQuery.CollectionSelector.Builder from2 = StructuredQuery.CollectionSelector.newBuilder();
            from2.setCollectionId(path.getLastSegment());
            structuredQueryBuilder.addFrom(from2);
        }
        if (target.getFilters().size() > 0) {
            structuredQueryBuilder.setWhere(encodeFilters(target.getFilters()));
        }
        for (OrderBy orderBy : target.getOrderBy()) {
            structuredQueryBuilder.addOrderBy(encodeOrderBy(orderBy));
        }
        if (target.hasLimit()) {
            structuredQueryBuilder.setLimit(Int32Value.newBuilder().setValue((int) target.getLimit()));
        }
        if (target.getStartAt() != null) {
            Cursor.Builder cursor = Cursor.newBuilder();
            cursor.addAllValues(target.getStartAt().getPosition());
            cursor.setBefore(target.getStartAt().isInclusive());
            structuredQueryBuilder.setStartAt(cursor);
        }
        if (target.getEndAt() != null) {
            Cursor.Builder cursor2 = Cursor.newBuilder();
            cursor2.addAllValues(target.getEndAt().getPosition());
            cursor2.setBefore(true ^ target.getEndAt().isInclusive());
            structuredQueryBuilder.setEndAt(cursor2);
        }
        builder.setStructuredQuery(structuredQueryBuilder);
        return (Target.QueryTarget) builder.build();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v2, resolved type: java.util.ArrayList} */
    /* JADX WARNING: type inference failed for: r7v10, types: [com.google.firebase.firestore.model.BasePath] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.firebase.firestore.core.Target decodeQueryTarget(java.lang.String r17, com.google.firestore.v1.StructuredQuery r18) {
        /*
            r16 = this;
            r0 = r16
            r1 = r18
            com.google.firebase.firestore.model.ResourcePath r2 = r16.decodeQueryPath(r17)
            r3 = 0
            int r4 = r1.getFromCount()
            r5 = 1
            if (r4 <= 0) goto L_0x003c
            r6 = 0
            if (r4 != r5) goto L_0x0015
            r7 = r5
            goto L_0x0016
        L_0x0015:
            r7 = r6
        L_0x0016:
            java.lang.String r8 = "StructuredQuery.from with more than one collection is not supported."
            java.lang.Object[] r9 = new java.lang.Object[r6]
            com.google.firebase.firestore.util.Assert.hardAssert(r7, r8, r9)
            com.google.firestore.v1.StructuredQuery$CollectionSelector r6 = r1.getFrom(r6)
            boolean r7 = r6.getAllDescendants()
            if (r7 == 0) goto L_0x002e
            java.lang.String r3 = r6.getCollectionId()
            r8 = r2
            r9 = r3
            goto L_0x003e
        L_0x002e:
            java.lang.String r7 = r6.getCollectionId()
            com.google.firebase.firestore.model.BasePath r7 = r2.append((java.lang.String) r7)
            r2 = r7
            com.google.firebase.firestore.model.ResourcePath r2 = (com.google.firebase.firestore.model.ResourcePath) r2
            r8 = r2
            r9 = r3
            goto L_0x003e
        L_0x003c:
            r8 = r2
            r9 = r3
        L_0x003e:
            boolean r2 = r1.hasWhere()
            if (r2 == 0) goto L_0x004e
            com.google.firestore.v1.StructuredQuery$Filter r2 = r1.getWhere()
            java.util.List r2 = r0.decodeFilters(r2)
            r10 = r2
            goto L_0x0053
        L_0x004e:
            java.util.List r2 = java.util.Collections.emptyList()
            r10 = r2
        L_0x0053:
            int r2 = r1.getOrderByCount()
            if (r2 <= 0) goto L_0x0071
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>(r2)
            r6 = 0
        L_0x005f:
            if (r6 >= r2) goto L_0x006f
            com.google.firestore.v1.StructuredQuery$Order r7 = r1.getOrderBy(r6)
            com.google.firebase.firestore.core.OrderBy r7 = r0.decodeOrderBy(r7)
            r3.add(r7)
            int r6 = r6 + 1
            goto L_0x005f
        L_0x006f:
            r11 = r3
            goto L_0x0076
        L_0x0071:
            java.util.List r3 = java.util.Collections.emptyList()
            r11 = r3
        L_0x0076:
            r6 = -1
            boolean r3 = r1.hasLimit()
            if (r3 == 0) goto L_0x0089
            com.google.protobuf.Int32Value r3 = r1.getLimit()
            int r3 = r3.getValue()
            long r6 = (long) r3
            r12 = r6
            goto L_0x008a
        L_0x0089:
            r12 = r6
        L_0x008a:
            r3 = 0
            boolean r6 = r1.hasStartAt()
            if (r6 == 0) goto L_0x00a9
            com.google.firebase.firestore.core.Bound r6 = new com.google.firebase.firestore.core.Bound
            com.google.firestore.v1.Cursor r7 = r1.getStartAt()
            java.util.List r7 = r7.getValuesList()
            com.google.firestore.v1.Cursor r14 = r1.getStartAt()
            boolean r14 = r14.getBefore()
            r6.<init>(r7, r14)
            r3 = r6
            r14 = r3
            goto L_0x00aa
        L_0x00a9:
            r14 = r3
        L_0x00aa:
            r3 = 0
            boolean r6 = r1.hasEndAt()
            if (r6 == 0) goto L_0x00ca
            com.google.firebase.firestore.core.Bound r6 = new com.google.firebase.firestore.core.Bound
            com.google.firestore.v1.Cursor r7 = r1.getEndAt()
            java.util.List r7 = r7.getValuesList()
            com.google.firestore.v1.Cursor r15 = r1.getEndAt()
            boolean r15 = r15.getBefore()
            r5 = r5 ^ r15
            r6.<init>(r7, r5)
            r3 = r6
            r15 = r3
            goto L_0x00cb
        L_0x00ca:
            r15 = r3
        L_0x00cb:
            com.google.firebase.firestore.core.Target r7 = new com.google.firebase.firestore.core.Target
            r7.<init>(r8, r9, r10, r11, r12, r14, r15)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.remote.RemoteSerializer.decodeQueryTarget(java.lang.String, com.google.firestore.v1.StructuredQuery):com.google.firebase.firestore.core.Target");
    }

    public com.google.firebase.firestore.core.Target decodeQueryTarget(Target.QueryTarget target) {
        return decodeQueryTarget(target.getParent(), target.getStructuredQuery());
    }

    /* access modifiers changed from: package-private */
    public StructuredAggregationQuery encodeStructuredAggregationQuery(Target.QueryTarget encodedQueryTarget, List<AggregateField> aggregateFields, HashMap<String, String> aliasMap) {
        StructuredAggregationQuery.Builder structuredAggregationQuery = StructuredAggregationQuery.newBuilder();
        structuredAggregationQuery.setStructuredQuery(encodedQueryTarget.getStructuredQuery());
        List<StructuredAggregationQuery.Aggregation> aggregations = new ArrayList<>();
        HashSet<String> uniqueFields = new HashSet<>();
        int aliasID = 1;
        for (AggregateField aggregateField : aggregateFields) {
            if (!uniqueFields.contains(aggregateField.getAlias())) {
                uniqueFields.add(aggregateField.getAlias());
                int aliasID2 = aliasID + 1;
                String serverAlias = "aggregate_" + aliasID;
                aliasMap.put(serverAlias, aggregateField.getAlias());
                StructuredAggregationQuery.Aggregation.Builder aggregation = StructuredAggregationQuery.Aggregation.newBuilder();
                StructuredQuery.FieldReference fieldPath = (StructuredQuery.FieldReference) StructuredQuery.FieldReference.newBuilder().setFieldPath(aggregateField.getFieldPath()).build();
                if (aggregateField instanceof AggregateField.CountAggregateField) {
                    aggregation.setCount(StructuredAggregationQuery.Aggregation.Count.getDefaultInstance());
                } else if (aggregateField instanceof AggregateField.SumAggregateField) {
                    aggregation.setSum((StructuredAggregationQuery.Aggregation.Sum) StructuredAggregationQuery.Aggregation.Sum.newBuilder().setField(fieldPath).build());
                } else if (aggregateField instanceof AggregateField.AverageAggregateField) {
                    aggregation.setAvg((StructuredAggregationQuery.Aggregation.Avg) StructuredAggregationQuery.Aggregation.Avg.newBuilder().setField(fieldPath).build());
                } else {
                    throw new RuntimeException("Unsupported aggregation");
                }
                aggregation.setAlias(serverAlias);
                aggregations.add((StructuredAggregationQuery.Aggregation) aggregation.build());
                aliasID = aliasID2;
            }
        }
        structuredAggregationQuery.addAllAggregations(aggregations);
        return (StructuredAggregationQuery) structuredAggregationQuery.build();
    }

    private StructuredQuery.Filter encodeFilters(List<Filter> filters) {
        return encodeFilter(new CompositeFilter(filters, CompositeFilter.Operator.AND));
    }

    private List<Filter> decodeFilters(StructuredQuery.Filter proto) {
        Filter result = decodeFilter(proto);
        if (result instanceof CompositeFilter) {
            CompositeFilter compositeFilter = (CompositeFilter) result;
            if (compositeFilter.isFlatConjunction()) {
                return compositeFilter.getFilters();
            }
        }
        return Collections.singletonList(result);
    }

    /* access modifiers changed from: package-private */
    public StructuredQuery.Filter encodeFilter(Filter filter) {
        if (filter instanceof FieldFilter) {
            return encodeUnaryOrFieldFilter((FieldFilter) filter);
        }
        if (filter instanceof CompositeFilter) {
            return encodeCompositeFilter((CompositeFilter) filter);
        }
        throw Assert.fail("Unrecognized filter type %s", filter.toString());
    }

    /* access modifiers changed from: package-private */
    public StructuredQuery.Filter encodeUnaryOrFieldFilter(FieldFilter filter) {
        StructuredQuery.UnaryFilter.Operator operator;
        StructuredQuery.UnaryFilter.Operator operator2;
        if (filter.getOperator() == FieldFilter.Operator.EQUAL || filter.getOperator() == FieldFilter.Operator.NOT_EQUAL) {
            StructuredQuery.UnaryFilter.Builder unaryProto = StructuredQuery.UnaryFilter.newBuilder();
            unaryProto.setField(encodeFieldPath(filter.getField()));
            if (Values.isNanValue(filter.getValue())) {
                if (filter.getOperator() == FieldFilter.Operator.EQUAL) {
                    operator2 = StructuredQuery.UnaryFilter.Operator.IS_NAN;
                } else {
                    operator2 = StructuredQuery.UnaryFilter.Operator.IS_NOT_NAN;
                }
                unaryProto.setOp(operator2);
                return (StructuredQuery.Filter) StructuredQuery.Filter.newBuilder().setUnaryFilter(unaryProto).build();
            } else if (Values.isNullValue(filter.getValue())) {
                if (filter.getOperator() == FieldFilter.Operator.EQUAL) {
                    operator = StructuredQuery.UnaryFilter.Operator.IS_NULL;
                } else {
                    operator = StructuredQuery.UnaryFilter.Operator.IS_NOT_NULL;
                }
                unaryProto.setOp(operator);
                return (StructuredQuery.Filter) StructuredQuery.Filter.newBuilder().setUnaryFilter(unaryProto).build();
            }
        }
        StructuredQuery.FieldFilter.Builder proto = StructuredQuery.FieldFilter.newBuilder();
        proto.setField(encodeFieldPath(filter.getField()));
        proto.setOp(encodeFieldFilterOperator(filter.getOperator()));
        proto.setValue(filter.getValue());
        return (StructuredQuery.Filter) StructuredQuery.Filter.newBuilder().setFieldFilter(proto).build();
    }

    /* access modifiers changed from: package-private */
    public StructuredQuery.CompositeFilter.Operator encodeCompositeFilterOperator(CompositeFilter.Operator op) {
        switch (op) {
            case AND:
                return StructuredQuery.CompositeFilter.Operator.AND;
            case OR:
                return StructuredQuery.CompositeFilter.Operator.OR;
            default:
                throw Assert.fail("Unrecognized composite filter type.", new Object[0]);
        }
    }

    /* access modifiers changed from: package-private */
    public CompositeFilter.Operator decodeCompositeFilterOperator(StructuredQuery.CompositeFilter.Operator op) {
        switch (op) {
            case AND:
                return CompositeFilter.Operator.AND;
            case OR:
                return CompositeFilter.Operator.OR;
            default:
                throw Assert.fail("Only AND and OR composite filter types are supported.", new Object[0]);
        }
    }

    /* access modifiers changed from: package-private */
    public StructuredQuery.Filter encodeCompositeFilter(CompositeFilter compositeFilter) {
        List<StructuredQuery.Filter> protos = new ArrayList<>(compositeFilter.getFilters().size());
        for (Filter filter : compositeFilter.getFilters()) {
            protos.add(encodeFilter(filter));
        }
        if (protos.size() == 1) {
            return protos.get(0);
        }
        StructuredQuery.CompositeFilter.Builder composite = StructuredQuery.CompositeFilter.newBuilder();
        composite.setOp(encodeCompositeFilterOperator(compositeFilter.getOperator()));
        composite.addAllFilters(protos);
        return (StructuredQuery.Filter) StructuredQuery.Filter.newBuilder().setCompositeFilter(composite).build();
    }

    /* access modifiers changed from: package-private */
    public Filter decodeFilter(StructuredQuery.Filter proto) {
        switch (proto.getFilterTypeCase()) {
            case COMPOSITE_FILTER:
                return decodeCompositeFilter(proto.getCompositeFilter());
            case FIELD_FILTER:
                return decodeFieldFilter(proto.getFieldFilter());
            case UNARY_FILTER:
                return decodeUnaryFilter(proto.getUnaryFilter());
            default:
                throw Assert.fail("Unrecognized Filter.filterType %d", proto.getFilterTypeCase());
        }
    }

    /* access modifiers changed from: package-private */
    public FieldFilter decodeFieldFilter(StructuredQuery.FieldFilter proto) {
        return FieldFilter.create(FieldPath.fromServerFormat(proto.getField().getFieldPath()), decodeFieldFilterOperator(proto.getOp()), proto.getValue());
    }

    private Filter decodeUnaryFilter(StructuredQuery.UnaryFilter proto) {
        FieldPath fieldPath = FieldPath.fromServerFormat(proto.getField().getFieldPath());
        switch (proto.getOp()) {
            case IS_NAN:
                return FieldFilter.create(fieldPath, FieldFilter.Operator.EQUAL, Values.NAN_VALUE);
            case IS_NULL:
                return FieldFilter.create(fieldPath, FieldFilter.Operator.EQUAL, Values.NULL_VALUE);
            case IS_NOT_NAN:
                return FieldFilter.create(fieldPath, FieldFilter.Operator.NOT_EQUAL, Values.NAN_VALUE);
            case IS_NOT_NULL:
                return FieldFilter.create(fieldPath, FieldFilter.Operator.NOT_EQUAL, Values.NULL_VALUE);
            default:
                throw Assert.fail("Unrecognized UnaryFilter.operator %d", proto.getOp());
        }
    }

    /* access modifiers changed from: package-private */
    public CompositeFilter decodeCompositeFilter(StructuredQuery.CompositeFilter compositeFilter) {
        List<Filter> filters = new ArrayList<>();
        for (StructuredQuery.Filter filter : compositeFilter.getFiltersList()) {
            filters.add(decodeFilter(filter));
        }
        return new CompositeFilter(filters, decodeCompositeFilterOperator(compositeFilter.getOp()));
    }

    private StructuredQuery.FieldReference encodeFieldPath(FieldPath field) {
        return (StructuredQuery.FieldReference) StructuredQuery.FieldReference.newBuilder().setFieldPath(field.canonicalString()).build();
    }

    private StructuredQuery.FieldFilter.Operator encodeFieldFilterOperator(FieldFilter.Operator operator) {
        switch (operator) {
            case LESS_THAN:
                return StructuredQuery.FieldFilter.Operator.LESS_THAN;
            case LESS_THAN_OR_EQUAL:
                return StructuredQuery.FieldFilter.Operator.LESS_THAN_OR_EQUAL;
            case EQUAL:
                return StructuredQuery.FieldFilter.Operator.EQUAL;
            case NOT_EQUAL:
                return StructuredQuery.FieldFilter.Operator.NOT_EQUAL;
            case GREATER_THAN:
                return StructuredQuery.FieldFilter.Operator.GREATER_THAN;
            case GREATER_THAN_OR_EQUAL:
                return StructuredQuery.FieldFilter.Operator.GREATER_THAN_OR_EQUAL;
            case ARRAY_CONTAINS:
                return StructuredQuery.FieldFilter.Operator.ARRAY_CONTAINS;
            case IN:
                return StructuredQuery.FieldFilter.Operator.IN;
            case ARRAY_CONTAINS_ANY:
                return StructuredQuery.FieldFilter.Operator.ARRAY_CONTAINS_ANY;
            case NOT_IN:
                return StructuredQuery.FieldFilter.Operator.NOT_IN;
            default:
                throw Assert.fail("Unknown operator %d", operator);
        }
    }

    private FieldFilter.Operator decodeFieldFilterOperator(StructuredQuery.FieldFilter.Operator operator) {
        switch (operator) {
            case LESS_THAN:
                return FieldFilter.Operator.LESS_THAN;
            case LESS_THAN_OR_EQUAL:
                return FieldFilter.Operator.LESS_THAN_OR_EQUAL;
            case EQUAL:
                return FieldFilter.Operator.EQUAL;
            case NOT_EQUAL:
                return FieldFilter.Operator.NOT_EQUAL;
            case GREATER_THAN_OR_EQUAL:
                return FieldFilter.Operator.GREATER_THAN_OR_EQUAL;
            case GREATER_THAN:
                return FieldFilter.Operator.GREATER_THAN;
            case ARRAY_CONTAINS:
                return FieldFilter.Operator.ARRAY_CONTAINS;
            case IN:
                return FieldFilter.Operator.IN;
            case ARRAY_CONTAINS_ANY:
                return FieldFilter.Operator.ARRAY_CONTAINS_ANY;
            case NOT_IN:
                return FieldFilter.Operator.NOT_IN;
            default:
                throw Assert.fail("Unhandled FieldFilter.operator %d", operator);
        }
    }

    private StructuredQuery.Order encodeOrderBy(OrderBy orderBy) {
        StructuredQuery.Order.Builder proto = StructuredQuery.Order.newBuilder();
        if (orderBy.getDirection().equals(OrderBy.Direction.ASCENDING)) {
            proto.setDirection(StructuredQuery.Direction.ASCENDING);
        } else {
            proto.setDirection(StructuredQuery.Direction.DESCENDING);
        }
        proto.setField(encodeFieldPath(orderBy.getField()));
        return (StructuredQuery.Order) proto.build();
    }

    private OrderBy decodeOrderBy(StructuredQuery.Order proto) {
        OrderBy.Direction direction;
        FieldPath fieldPath = FieldPath.fromServerFormat(proto.getField().getFieldPath());
        switch (proto.getDirection()) {
            case ASCENDING:
                direction = OrderBy.Direction.ASCENDING;
                break;
            case DESCENDING:
                direction = OrderBy.Direction.DESCENDING;
                break;
            default:
                throw Assert.fail("Unrecognized direction %d", proto.getDirection());
        }
        return OrderBy.getInstance(direction, fieldPath);
    }

    public WatchChange decodeWatchChange(ListenResponse protoChange) {
        WatchChange.WatchTargetChangeType changeType;
        switch (protoChange.getResponseTypeCase()) {
            case TARGET_CHANGE:
                TargetChange targetChange = protoChange.getTargetChange();
                Status cause = null;
                switch (targetChange.getTargetChangeType()) {
                    case NO_CHANGE:
                        changeType = WatchChange.WatchTargetChangeType.NoChange;
                        break;
                    case ADD:
                        changeType = WatchChange.WatchTargetChangeType.Added;
                        break;
                    case REMOVE:
                        changeType = WatchChange.WatchTargetChangeType.Removed;
                        cause = fromStatus(targetChange.getCause());
                        break;
                    case CURRENT:
                        changeType = WatchChange.WatchTargetChangeType.Current;
                        break;
                    case RESET:
                        changeType = WatchChange.WatchTargetChangeType.Reset;
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown target change type");
                }
                return new WatchChange.WatchTargetChange(changeType, targetChange.getTargetIdsList(), targetChange.getResumeToken(), cause);
            case DOCUMENT_CHANGE:
                DocumentChange docChange = protoChange.getDocumentChange();
                List<Integer> added = docChange.getTargetIdsList();
                List<Integer> removed = docChange.getRemovedTargetIdsList();
                DocumentKey key = decodeKey(docChange.getDocument().getName());
                SnapshotVersion version = decodeVersion(docChange.getDocument().getUpdateTime());
                Assert.hardAssert(!version.equals(SnapshotVersion.NONE), "Got a document change without an update time", new Object[0]);
                MutableDocument document = MutableDocument.newFoundDocument(key, version, ObjectValue.fromMap(docChange.getDocument().getFieldsMap()));
                return new WatchChange.DocumentChange(added, removed, document.getKey(), document);
            case DOCUMENT_DELETE:
                DocumentDelete docDelete = protoChange.getDocumentDelete();
                List<Integer> removed2 = docDelete.getRemovedTargetIdsList();
                MutableDocument doc = MutableDocument.newNoDocument(decodeKey(docDelete.getDocument()), decodeVersion(docDelete.getReadTime()));
                return new WatchChange.DocumentChange(Collections.emptyList(), removed2, doc.getKey(), doc);
            case DOCUMENT_REMOVE:
                DocumentRemove docRemove = protoChange.getDocumentRemove();
                return new WatchChange.DocumentChange(Collections.emptyList(), docRemove.getRemovedTargetIdsList(), decodeKey(docRemove.getDocument()), (MutableDocument) null);
            case FILTER:
                ExistenceFilter protoFilter = protoChange.getFilter();
                return new WatchChange.ExistenceFilterWatchChange(protoFilter.getTargetId(), new ExistenceFilter(protoFilter.getCount(), protoFilter.getUnchangedNames()));
            default:
                throw new IllegalArgumentException("Unknown change type set");
        }
    }

    public SnapshotVersion decodeVersionFromListenResponse(ListenResponse watchChange) {
        if (watchChange.getResponseTypeCase() != ListenResponse.ResponseTypeCase.TARGET_CHANGE) {
            return SnapshotVersion.NONE;
        }
        if (watchChange.getTargetChange().getTargetIdsCount() != 0) {
            return SnapshotVersion.NONE;
        }
        return decodeVersion(watchChange.getTargetChange().getReadTime());
    }

    private Status fromStatus(com.google.rpc.Status status) {
        return Status.fromCodeValue(status.getCode()).withDescription(status.getMessage());
    }
}
