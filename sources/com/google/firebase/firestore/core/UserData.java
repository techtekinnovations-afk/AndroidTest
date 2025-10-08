package com.google.firebase.firestore.core;

import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.ObjectValue;
import com.google.firebase.firestore.model.mutation.FieldMask;
import com.google.firebase.firestore.model.mutation.FieldTransform;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.PatchMutation;
import com.google.firebase.firestore.model.mutation.Precondition;
import com.google.firebase.firestore.model.mutation.SetMutation;
import com.google.firebase.firestore.model.mutation.TransformOperation;
import com.google.firebase.firestore.util.Assert;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class UserData {

    public enum Source {
        Set,
        MergeSet,
        Update,
        Argument,
        ArrayArgument
    }

    private UserData() {
    }

    public static class ParseAccumulator {
        /* access modifiers changed from: private */
        public final Source dataSource;
        private final Set<FieldPath> fieldMask = new HashSet();
        private final ArrayList<FieldTransform> fieldTransforms = new ArrayList<>();

        public ParseAccumulator(Source dataSource2) {
            this.dataSource = dataSource2;
        }

        public Source getDataSource() {
            return this.dataSource;
        }

        public List<FieldTransform> getFieldTransforms() {
            return this.fieldTransforms;
        }

        public ParseContext rootContext() {
            return new ParseContext(this, FieldPath.EMPTY_PATH, false);
        }

        public boolean contains(FieldPath fieldPath) {
            for (FieldPath field : this.fieldMask) {
                if (fieldPath.isPrefixOf(field)) {
                    return true;
                }
            }
            Iterator<FieldTransform> it = this.fieldTransforms.iterator();
            while (it.hasNext()) {
                if (fieldPath.isPrefixOf(it.next().getFieldPath())) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: package-private */
        public void addToFieldMask(FieldPath fieldPath) {
            this.fieldMask.add(fieldPath);
        }

        /* access modifiers changed from: package-private */
        public void addToFieldTransforms(FieldPath fieldPath, TransformOperation transformOperation) {
            this.fieldTransforms.add(new FieldTransform(fieldPath, transformOperation));
        }

        public ParsedSetData toMergeData(ObjectValue data) {
            return new ParsedSetData(data, FieldMask.fromSet(this.fieldMask), Collections.unmodifiableList(this.fieldTransforms));
        }

        public ParsedSetData toMergeData(ObjectValue data, FieldMask userFieldMask) {
            ArrayList<FieldTransform> coveredFieldTransforms = new ArrayList<>();
            Iterator<FieldTransform> it = this.fieldTransforms.iterator();
            while (it.hasNext()) {
                FieldTransform parsedTransform = it.next();
                if (userFieldMask.covers(parsedTransform.getFieldPath())) {
                    coveredFieldTransforms.add(parsedTransform);
                }
            }
            return new ParsedSetData(data, userFieldMask, Collections.unmodifiableList(coveredFieldTransforms));
        }

        public ParsedSetData toSetData(ObjectValue data) {
            return new ParsedSetData(data, (FieldMask) null, Collections.unmodifiableList(this.fieldTransforms));
        }

        public ParsedUpdateData toUpdateData(ObjectValue data) {
            return new ParsedUpdateData(data, FieldMask.fromSet(this.fieldMask), Collections.unmodifiableList(this.fieldTransforms));
        }
    }

    public static class ParseContext {
        private static final String RESERVED_FIELD_DESIGNATOR = "__";
        private final ParseAccumulator accumulator;
        private final boolean arrayElement;
        private final FieldPath path;

        private ParseContext(ParseAccumulator accumulator2, FieldPath path2, boolean arrayElement2) {
            this.accumulator = accumulator2;
            this.path = path2;
            this.arrayElement = arrayElement2;
        }

        public boolean isArrayElement() {
            return this.arrayElement;
        }

        public Source getDataSource() {
            return this.accumulator.dataSource;
        }

        public FieldPath getPath() {
            return this.path;
        }

        public boolean isWrite() {
            switch (this.accumulator.dataSource) {
                case Set:
                case MergeSet:
                case Update:
                    return true;
                case Argument:
                case ArrayArgument:
                    return false;
                default:
                    throw Assert.fail("Unexpected case for UserDataSource: %s", this.accumulator.dataSource.name());
            }
        }

        public ParseContext childContext(String fieldName) {
            ParseContext context = new ParseContext(this.accumulator, this.path == null ? null : (FieldPath) this.path.append(fieldName), false);
            context.validatePathSegment(fieldName);
            return context;
        }

        public ParseContext childContext(FieldPath fieldPath) {
            ParseContext context = new ParseContext(this.accumulator, this.path == null ? null : (FieldPath) this.path.append(fieldPath), false);
            context.validatePath();
            return context;
        }

        public ParseContext childContext(int arrayIndex) {
            return new ParseContext(this.accumulator, (FieldPath) null, true);
        }

        public void addToFieldMask(FieldPath fieldPath) {
            this.accumulator.addToFieldMask(fieldPath);
        }

        public void addToFieldTransforms(FieldPath fieldPath, TransformOperation transformOperation) {
            this.accumulator.addToFieldTransforms(fieldPath, transformOperation);
        }

        public RuntimeException createError(String reason) {
            String fieldDescription;
            if (this.path == null || this.path.isEmpty()) {
                fieldDescription = "";
            } else {
                fieldDescription = " (found in field " + this.path.toString() + ")";
            }
            return new IllegalArgumentException("Invalid data. " + reason + fieldDescription);
        }

        private void validatePath() {
            if (this.path != null) {
                for (int i = 0; i < this.path.length(); i++) {
                    validatePathSegment(this.path.getSegment(i));
                }
            }
        }

        private void validatePathSegment(String segment) {
            if (segment.isEmpty()) {
                throw createError("Document fields must not be empty");
            } else if (isWrite() && segment.startsWith(RESERVED_FIELD_DESIGNATOR) && segment.endsWith(RESERVED_FIELD_DESIGNATOR)) {
                throw createError("Document fields cannot begin and end with \"__\"");
            }
        }
    }

    public static class ParsedSetData {
        private final ObjectValue data;
        private final FieldMask fieldMask;
        private final List<FieldTransform> fieldTransforms;

        ParsedSetData(ObjectValue data2, FieldMask fieldMask2, List<FieldTransform> fieldTransforms2) {
            this.data = data2;
            this.fieldMask = fieldMask2;
            this.fieldTransforms = fieldTransforms2;
        }

        public ObjectValue getData() {
            return this.data;
        }

        public FieldMask getFieldMask() {
            return this.fieldMask;
        }

        public List<FieldTransform> getFieldTransforms() {
            return this.fieldTransforms;
        }

        public Mutation toMutation(DocumentKey key, Precondition precondition) {
            if (this.fieldMask != null) {
                return new PatchMutation(key, this.data, this.fieldMask, precondition, this.fieldTransforms);
            }
            return new SetMutation(key, this.data, precondition, this.fieldTransforms);
        }
    }

    public static class ParsedUpdateData {
        private final ObjectValue data;
        private final FieldMask fieldMask;
        private final List<FieldTransform> fieldTransforms;

        ParsedUpdateData(ObjectValue data2, FieldMask fieldMask2, List<FieldTransform> fieldTransforms2) {
            this.data = data2;
            this.fieldMask = fieldMask2;
            this.fieldTransforms = fieldTransforms2;
        }

        public ObjectValue getData() {
            return this.data;
        }

        public FieldMask getFieldMask() {
            return this.fieldMask;
        }

        public List<FieldTransform> getFieldTransforms() {
            return this.fieldTransforms;
        }

        public Mutation toMutation(DocumentKey key, Precondition precondition) {
            return new PatchMutation(key, this.data, this.fieldMask, precondition, this.fieldTransforms);
        }
    }
}
