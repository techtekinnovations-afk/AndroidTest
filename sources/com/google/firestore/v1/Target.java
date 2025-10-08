package com.google.firestore.v1;

import com.google.firestore.v1.StructuredQuery;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Int32Value;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.Timestamp;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class Target extends GeneratedMessageLite<Target, Builder> implements TargetOrBuilder {
    /* access modifiers changed from: private */
    public static final Target DEFAULT_INSTANCE;
    public static final int DOCUMENTS_FIELD_NUMBER = 3;
    public static final int EXPECTED_COUNT_FIELD_NUMBER = 12;
    public static final int ONCE_FIELD_NUMBER = 6;
    private static volatile Parser<Target> PARSER = null;
    public static final int QUERY_FIELD_NUMBER = 2;
    public static final int READ_TIME_FIELD_NUMBER = 11;
    public static final int RESUME_TOKEN_FIELD_NUMBER = 4;
    public static final int TARGET_ID_FIELD_NUMBER = 5;
    private int bitField0_;
    private Int32Value expectedCount_;
    private boolean once_;
    private int resumeTypeCase_ = 0;
    private Object resumeType_;
    private int targetId_;
    private int targetTypeCase_ = 0;
    private Object targetType_;

    public interface DocumentsTargetOrBuilder extends MessageLiteOrBuilder {
        String getDocuments(int i);

        ByteString getDocumentsBytes(int i);

        int getDocumentsCount();

        List<String> getDocumentsList();
    }

    public interface QueryTargetOrBuilder extends MessageLiteOrBuilder {
        String getParent();

        ByteString getParentBytes();

        QueryTarget.QueryTypeCase getQueryTypeCase();

        StructuredQuery getStructuredQuery();

        boolean hasStructuredQuery();
    }

    private Target() {
    }

    public static final class DocumentsTarget extends GeneratedMessageLite<DocumentsTarget, Builder> implements DocumentsTargetOrBuilder {
        /* access modifiers changed from: private */
        public static final DocumentsTarget DEFAULT_INSTANCE;
        public static final int DOCUMENTS_FIELD_NUMBER = 2;
        private static volatile Parser<DocumentsTarget> PARSER;
        private Internal.ProtobufList<String> documents_ = GeneratedMessageLite.emptyProtobufList();

        private DocumentsTarget() {
        }

        public List<String> getDocumentsList() {
            return this.documents_;
        }

        public int getDocumentsCount() {
            return this.documents_.size();
        }

        public String getDocuments(int index) {
            return (String) this.documents_.get(index);
        }

        public ByteString getDocumentsBytes(int index) {
            return ByteString.copyFromUtf8((String) this.documents_.get(index));
        }

        private void ensureDocumentsIsMutable() {
            Internal.ProtobufList<String> tmp = this.documents_;
            if (!tmp.isModifiable()) {
                this.documents_ = GeneratedMessageLite.mutableCopy(tmp);
            }
        }

        /* access modifiers changed from: private */
        public void setDocuments(int index, String value) {
            Class<?> cls = value.getClass();
            ensureDocumentsIsMutable();
            this.documents_.set(index, value);
        }

        /* access modifiers changed from: private */
        public void addDocuments(String value) {
            Class<?> cls = value.getClass();
            ensureDocumentsIsMutable();
            this.documents_.add(value);
        }

        /* access modifiers changed from: private */
        public void addAllDocuments(Iterable<String> values) {
            ensureDocumentsIsMutable();
            AbstractMessageLite.addAll(values, this.documents_);
        }

        /* access modifiers changed from: private */
        public void clearDocuments() {
            this.documents_ = GeneratedMessageLite.emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void addDocumentsBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            ensureDocumentsIsMutable();
            this.documents_.add(value.toStringUtf8());
        }

        public static DocumentsTarget parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (DocumentsTarget) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DocumentsTarget parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DocumentsTarget) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DocumentsTarget parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DocumentsTarget) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DocumentsTarget parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DocumentsTarget) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DocumentsTarget parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DocumentsTarget) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DocumentsTarget parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DocumentsTarget) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DocumentsTarget parseFrom(InputStream input) throws IOException {
            return (DocumentsTarget) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DocumentsTarget parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DocumentsTarget) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DocumentsTarget parseDelimitedFrom(InputStream input) throws IOException {
            return (DocumentsTarget) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static DocumentsTarget parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DocumentsTarget) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DocumentsTarget parseFrom(CodedInputStream input) throws IOException {
            return (DocumentsTarget) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DocumentsTarget parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DocumentsTarget) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(DocumentsTarget prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<DocumentsTarget, Builder> implements DocumentsTargetOrBuilder {
            private Builder() {
                super(DocumentsTarget.DEFAULT_INSTANCE);
            }

            public List<String> getDocumentsList() {
                return Collections.unmodifiableList(((DocumentsTarget) this.instance).getDocumentsList());
            }

            public int getDocumentsCount() {
                return ((DocumentsTarget) this.instance).getDocumentsCount();
            }

            public String getDocuments(int index) {
                return ((DocumentsTarget) this.instance).getDocuments(index);
            }

            public ByteString getDocumentsBytes(int index) {
                return ((DocumentsTarget) this.instance).getDocumentsBytes(index);
            }

            public Builder setDocuments(int index, String value) {
                copyOnWrite();
                ((DocumentsTarget) this.instance).setDocuments(index, value);
                return this;
            }

            public Builder addDocuments(String value) {
                copyOnWrite();
                ((DocumentsTarget) this.instance).addDocuments(value);
                return this;
            }

            public Builder addAllDocuments(Iterable<String> values) {
                copyOnWrite();
                ((DocumentsTarget) this.instance).addAllDocuments(values);
                return this;
            }

            public Builder clearDocuments() {
                copyOnWrite();
                ((DocumentsTarget) this.instance).clearDocuments();
                return this;
            }

            public Builder addDocumentsBytes(ByteString value) {
                copyOnWrite();
                ((DocumentsTarget) this.instance).addDocumentsBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new DocumentsTarget();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0001\u0000\u0000\u0002\u0002\u0001\u0000\u0001\u0000\u0002Ț", new Object[]{"documents_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<DocumentsTarget> parser = PARSER;
                    if (parser == null) {
                        synchronized (DocumentsTarget.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            DocumentsTarget defaultInstance = new DocumentsTarget();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(DocumentsTarget.class, defaultInstance);
        }

        public static DocumentsTarget getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DocumentsTarget> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class QueryTarget extends GeneratedMessageLite<QueryTarget, Builder> implements QueryTargetOrBuilder {
        /* access modifiers changed from: private */
        public static final QueryTarget DEFAULT_INSTANCE;
        public static final int PARENT_FIELD_NUMBER = 1;
        private static volatile Parser<QueryTarget> PARSER = null;
        public static final int STRUCTURED_QUERY_FIELD_NUMBER = 2;
        private String parent_ = "";
        private int queryTypeCase_ = 0;
        private Object queryType_;

        private QueryTarget() {
        }

        public enum QueryTypeCase {
            STRUCTURED_QUERY(2),
            QUERYTYPE_NOT_SET(0);
            
            private final int value;

            private QueryTypeCase(int value2) {
                this.value = value2;
            }

            @Deprecated
            public static QueryTypeCase valueOf(int value2) {
                return forNumber(value2);
            }

            public static QueryTypeCase forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return QUERYTYPE_NOT_SET;
                    case 2:
                        return STRUCTURED_QUERY;
                    default:
                        return null;
                }
            }

            public int getNumber() {
                return this.value;
            }
        }

        public QueryTypeCase getQueryTypeCase() {
            return QueryTypeCase.forNumber(this.queryTypeCase_);
        }

        /* access modifiers changed from: private */
        public void clearQueryType() {
            this.queryTypeCase_ = 0;
            this.queryType_ = null;
        }

        public String getParent() {
            return this.parent_;
        }

        public ByteString getParentBytes() {
            return ByteString.copyFromUtf8(this.parent_);
        }

        /* access modifiers changed from: private */
        public void setParent(String value) {
            Class<?> cls = value.getClass();
            this.parent_ = value;
        }

        /* access modifiers changed from: private */
        public void clearParent() {
            this.parent_ = getDefaultInstance().getParent();
        }

        /* access modifiers changed from: private */
        public void setParentBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.parent_ = value.toStringUtf8();
        }

        public boolean hasStructuredQuery() {
            return this.queryTypeCase_ == 2;
        }

        public StructuredQuery getStructuredQuery() {
            if (this.queryTypeCase_ == 2) {
                return (StructuredQuery) this.queryType_;
            }
            return StructuredQuery.getDefaultInstance();
        }

        /* access modifiers changed from: private */
        public void setStructuredQuery(StructuredQuery value) {
            value.getClass();
            this.queryType_ = value;
            this.queryTypeCase_ = 2;
        }

        /* access modifiers changed from: private */
        public void mergeStructuredQuery(StructuredQuery value) {
            value.getClass();
            if (this.queryTypeCase_ != 2 || this.queryType_ == StructuredQuery.getDefaultInstance()) {
                this.queryType_ = value;
            } else {
                this.queryType_ = ((StructuredQuery.Builder) StructuredQuery.newBuilder((StructuredQuery) this.queryType_).mergeFrom(value)).buildPartial();
            }
            this.queryTypeCase_ = 2;
        }

        /* access modifiers changed from: private */
        public void clearStructuredQuery() {
            if (this.queryTypeCase_ == 2) {
                this.queryTypeCase_ = 0;
                this.queryType_ = null;
            }
        }

        public static QueryTarget parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (QueryTarget) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static QueryTarget parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (QueryTarget) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static QueryTarget parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (QueryTarget) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static QueryTarget parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (QueryTarget) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static QueryTarget parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (QueryTarget) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static QueryTarget parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (QueryTarget) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static QueryTarget parseFrom(InputStream input) throws IOException {
            return (QueryTarget) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static QueryTarget parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (QueryTarget) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static QueryTarget parseDelimitedFrom(InputStream input) throws IOException {
            return (QueryTarget) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static QueryTarget parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (QueryTarget) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static QueryTarget parseFrom(CodedInputStream input) throws IOException {
            return (QueryTarget) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static QueryTarget parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (QueryTarget) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(QueryTarget prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<QueryTarget, Builder> implements QueryTargetOrBuilder {
            private Builder() {
                super(QueryTarget.DEFAULT_INSTANCE);
            }

            public QueryTypeCase getQueryTypeCase() {
                return ((QueryTarget) this.instance).getQueryTypeCase();
            }

            public Builder clearQueryType() {
                copyOnWrite();
                ((QueryTarget) this.instance).clearQueryType();
                return this;
            }

            public String getParent() {
                return ((QueryTarget) this.instance).getParent();
            }

            public ByteString getParentBytes() {
                return ((QueryTarget) this.instance).getParentBytes();
            }

            public Builder setParent(String value) {
                copyOnWrite();
                ((QueryTarget) this.instance).setParent(value);
                return this;
            }

            public Builder clearParent() {
                copyOnWrite();
                ((QueryTarget) this.instance).clearParent();
                return this;
            }

            public Builder setParentBytes(ByteString value) {
                copyOnWrite();
                ((QueryTarget) this.instance).setParentBytes(value);
                return this;
            }

            public boolean hasStructuredQuery() {
                return ((QueryTarget) this.instance).hasStructuredQuery();
            }

            public StructuredQuery getStructuredQuery() {
                return ((QueryTarget) this.instance).getStructuredQuery();
            }

            public Builder setStructuredQuery(StructuredQuery value) {
                copyOnWrite();
                ((QueryTarget) this.instance).setStructuredQuery(value);
                return this;
            }

            public Builder setStructuredQuery(StructuredQuery.Builder builderForValue) {
                copyOnWrite();
                ((QueryTarget) this.instance).setStructuredQuery((StructuredQuery) builderForValue.build());
                return this;
            }

            public Builder mergeStructuredQuery(StructuredQuery value) {
                copyOnWrite();
                ((QueryTarget) this.instance).mergeStructuredQuery(value);
                return this;
            }

            public Builder clearStructuredQuery() {
                copyOnWrite();
                ((QueryTarget) this.instance).clearStructuredQuery();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new QueryTarget();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0001\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002<\u0000", new Object[]{"queryType_", "queryTypeCase_", "parent_", StructuredQuery.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<QueryTarget> parser = PARSER;
                    if (parser == null) {
                        synchronized (QueryTarget.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            QueryTarget defaultInstance = new QueryTarget();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(QueryTarget.class, defaultInstance);
        }

        public static QueryTarget getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<QueryTarget> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public enum TargetTypeCase {
        QUERY(2),
        DOCUMENTS(3),
        TARGETTYPE_NOT_SET(0);
        
        private final int value;

        private TargetTypeCase(int value2) {
            this.value = value2;
        }

        @Deprecated
        public static TargetTypeCase valueOf(int value2) {
            return forNumber(value2);
        }

        public static TargetTypeCase forNumber(int value2) {
            switch (value2) {
                case 0:
                    return TARGETTYPE_NOT_SET;
                case 2:
                    return QUERY;
                case 3:
                    return DOCUMENTS;
                default:
                    return null;
            }
        }

        public int getNumber() {
            return this.value;
        }
    }

    public TargetTypeCase getTargetTypeCase() {
        return TargetTypeCase.forNumber(this.targetTypeCase_);
    }

    /* access modifiers changed from: private */
    public void clearTargetType() {
        this.targetTypeCase_ = 0;
        this.targetType_ = null;
    }

    public enum ResumeTypeCase {
        RESUME_TOKEN(4),
        READ_TIME(11),
        RESUMETYPE_NOT_SET(0);
        
        private final int value;

        private ResumeTypeCase(int value2) {
            this.value = value2;
        }

        @Deprecated
        public static ResumeTypeCase valueOf(int value2) {
            return forNumber(value2);
        }

        public static ResumeTypeCase forNumber(int value2) {
            switch (value2) {
                case 0:
                    return RESUMETYPE_NOT_SET;
                case 4:
                    return RESUME_TOKEN;
                case 11:
                    return READ_TIME;
                default:
                    return null;
            }
        }

        public int getNumber() {
            return this.value;
        }
    }

    public ResumeTypeCase getResumeTypeCase() {
        return ResumeTypeCase.forNumber(this.resumeTypeCase_);
    }

    /* access modifiers changed from: private */
    public void clearResumeType() {
        this.resumeTypeCase_ = 0;
        this.resumeType_ = null;
    }

    public boolean hasQuery() {
        return this.targetTypeCase_ == 2;
    }

    public QueryTarget getQuery() {
        if (this.targetTypeCase_ == 2) {
            return (QueryTarget) this.targetType_;
        }
        return QueryTarget.getDefaultInstance();
    }

    /* access modifiers changed from: private */
    public void setQuery(QueryTarget value) {
        value.getClass();
        this.targetType_ = value;
        this.targetTypeCase_ = 2;
    }

    /* access modifiers changed from: private */
    public void mergeQuery(QueryTarget value) {
        value.getClass();
        if (this.targetTypeCase_ != 2 || this.targetType_ == QueryTarget.getDefaultInstance()) {
            this.targetType_ = value;
        } else {
            this.targetType_ = ((QueryTarget.Builder) QueryTarget.newBuilder((QueryTarget) this.targetType_).mergeFrom(value)).buildPartial();
        }
        this.targetTypeCase_ = 2;
    }

    /* access modifiers changed from: private */
    public void clearQuery() {
        if (this.targetTypeCase_ == 2) {
            this.targetTypeCase_ = 0;
            this.targetType_ = null;
        }
    }

    public boolean hasDocuments() {
        return this.targetTypeCase_ == 3;
    }

    public DocumentsTarget getDocuments() {
        if (this.targetTypeCase_ == 3) {
            return (DocumentsTarget) this.targetType_;
        }
        return DocumentsTarget.getDefaultInstance();
    }

    /* access modifiers changed from: private */
    public void setDocuments(DocumentsTarget value) {
        value.getClass();
        this.targetType_ = value;
        this.targetTypeCase_ = 3;
    }

    /* access modifiers changed from: private */
    public void mergeDocuments(DocumentsTarget value) {
        value.getClass();
        if (this.targetTypeCase_ != 3 || this.targetType_ == DocumentsTarget.getDefaultInstance()) {
            this.targetType_ = value;
        } else {
            this.targetType_ = ((DocumentsTarget.Builder) DocumentsTarget.newBuilder((DocumentsTarget) this.targetType_).mergeFrom(value)).buildPartial();
        }
        this.targetTypeCase_ = 3;
    }

    /* access modifiers changed from: private */
    public void clearDocuments() {
        if (this.targetTypeCase_ == 3) {
            this.targetTypeCase_ = 0;
            this.targetType_ = null;
        }
    }

    public boolean hasResumeToken() {
        return this.resumeTypeCase_ == 4;
    }

    public ByteString getResumeToken() {
        if (this.resumeTypeCase_ == 4) {
            return (ByteString) this.resumeType_;
        }
        return ByteString.EMPTY;
    }

    /* access modifiers changed from: private */
    public void setResumeToken(ByteString value) {
        Class<?> cls = value.getClass();
        this.resumeTypeCase_ = 4;
        this.resumeType_ = value;
    }

    /* access modifiers changed from: private */
    public void clearResumeToken() {
        if (this.resumeTypeCase_ == 4) {
            this.resumeTypeCase_ = 0;
            this.resumeType_ = null;
        }
    }

    public boolean hasReadTime() {
        return this.resumeTypeCase_ == 11;
    }

    public Timestamp getReadTime() {
        if (this.resumeTypeCase_ == 11) {
            return (Timestamp) this.resumeType_;
        }
        return Timestamp.getDefaultInstance();
    }

    /* access modifiers changed from: private */
    public void setReadTime(Timestamp value) {
        value.getClass();
        this.resumeType_ = value;
        this.resumeTypeCase_ = 11;
    }

    /* access modifiers changed from: private */
    public void mergeReadTime(Timestamp value) {
        value.getClass();
        if (this.resumeTypeCase_ != 11 || this.resumeType_ == Timestamp.getDefaultInstance()) {
            this.resumeType_ = value;
        } else {
            this.resumeType_ = ((Timestamp.Builder) Timestamp.newBuilder((Timestamp) this.resumeType_).mergeFrom(value)).buildPartial();
        }
        this.resumeTypeCase_ = 11;
    }

    /* access modifiers changed from: private */
    public void clearReadTime() {
        if (this.resumeTypeCase_ == 11) {
            this.resumeTypeCase_ = 0;
            this.resumeType_ = null;
        }
    }

    public int getTargetId() {
        return this.targetId_;
    }

    /* access modifiers changed from: private */
    public void setTargetId(int value) {
        this.targetId_ = value;
    }

    /* access modifiers changed from: private */
    public void clearTargetId() {
        this.targetId_ = 0;
    }

    public boolean getOnce() {
        return this.once_;
    }

    /* access modifiers changed from: private */
    public void setOnce(boolean value) {
        this.once_ = value;
    }

    /* access modifiers changed from: private */
    public void clearOnce() {
        this.once_ = false;
    }

    public boolean hasExpectedCount() {
        return (this.bitField0_ & 1) != 0;
    }

    public Int32Value getExpectedCount() {
        return this.expectedCount_ == null ? Int32Value.getDefaultInstance() : this.expectedCount_;
    }

    /* access modifiers changed from: private */
    public void setExpectedCount(Int32Value value) {
        value.getClass();
        this.expectedCount_ = value;
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void mergeExpectedCount(Int32Value value) {
        value.getClass();
        if (this.expectedCount_ == null || this.expectedCount_ == Int32Value.getDefaultInstance()) {
            this.expectedCount_ = value;
        } else {
            this.expectedCount_ = (Int32Value) ((Int32Value.Builder) Int32Value.newBuilder(this.expectedCount_).mergeFrom(value)).buildPartial();
        }
        this.bitField0_ |= 1;
    }

    /* access modifiers changed from: private */
    public void clearExpectedCount() {
        this.expectedCount_ = null;
        this.bitField0_ &= -2;
    }

    public static Target parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Target parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Target parseFrom(ByteString data) throws InvalidProtocolBufferException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Target parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Target parseFrom(byte[] data) throws InvalidProtocolBufferException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
    }

    public static Target parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
    }

    public static Target parseFrom(InputStream input) throws IOException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static Target parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Target parseDelimitedFrom(InputStream input) throws IOException {
        return (Target) parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }

    public static Target parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Target) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Target parseFrom(CodedInputStream input) throws IOException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
    }

    public static Target parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
        return (Target) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    public static Builder newBuilder(Target prototype) {
        return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Target, Builder> implements TargetOrBuilder {
        private Builder() {
            super(Target.DEFAULT_INSTANCE);
        }

        public TargetTypeCase getTargetTypeCase() {
            return ((Target) this.instance).getTargetTypeCase();
        }

        public Builder clearTargetType() {
            copyOnWrite();
            ((Target) this.instance).clearTargetType();
            return this;
        }

        public ResumeTypeCase getResumeTypeCase() {
            return ((Target) this.instance).getResumeTypeCase();
        }

        public Builder clearResumeType() {
            copyOnWrite();
            ((Target) this.instance).clearResumeType();
            return this;
        }

        public boolean hasQuery() {
            return ((Target) this.instance).hasQuery();
        }

        public QueryTarget getQuery() {
            return ((Target) this.instance).getQuery();
        }

        public Builder setQuery(QueryTarget value) {
            copyOnWrite();
            ((Target) this.instance).setQuery(value);
            return this;
        }

        public Builder setQuery(QueryTarget.Builder builderForValue) {
            copyOnWrite();
            ((Target) this.instance).setQuery((QueryTarget) builderForValue.build());
            return this;
        }

        public Builder mergeQuery(QueryTarget value) {
            copyOnWrite();
            ((Target) this.instance).mergeQuery(value);
            return this;
        }

        public Builder clearQuery() {
            copyOnWrite();
            ((Target) this.instance).clearQuery();
            return this;
        }

        public boolean hasDocuments() {
            return ((Target) this.instance).hasDocuments();
        }

        public DocumentsTarget getDocuments() {
            return ((Target) this.instance).getDocuments();
        }

        public Builder setDocuments(DocumentsTarget value) {
            copyOnWrite();
            ((Target) this.instance).setDocuments(value);
            return this;
        }

        public Builder setDocuments(DocumentsTarget.Builder builderForValue) {
            copyOnWrite();
            ((Target) this.instance).setDocuments((DocumentsTarget) builderForValue.build());
            return this;
        }

        public Builder mergeDocuments(DocumentsTarget value) {
            copyOnWrite();
            ((Target) this.instance).mergeDocuments(value);
            return this;
        }

        public Builder clearDocuments() {
            copyOnWrite();
            ((Target) this.instance).clearDocuments();
            return this;
        }

        public boolean hasResumeToken() {
            return ((Target) this.instance).hasResumeToken();
        }

        public ByteString getResumeToken() {
            return ((Target) this.instance).getResumeToken();
        }

        public Builder setResumeToken(ByteString value) {
            copyOnWrite();
            ((Target) this.instance).setResumeToken(value);
            return this;
        }

        public Builder clearResumeToken() {
            copyOnWrite();
            ((Target) this.instance).clearResumeToken();
            return this;
        }

        public boolean hasReadTime() {
            return ((Target) this.instance).hasReadTime();
        }

        public Timestamp getReadTime() {
            return ((Target) this.instance).getReadTime();
        }

        public Builder setReadTime(Timestamp value) {
            copyOnWrite();
            ((Target) this.instance).setReadTime(value);
            return this;
        }

        public Builder setReadTime(Timestamp.Builder builderForValue) {
            copyOnWrite();
            ((Target) this.instance).setReadTime((Timestamp) builderForValue.build());
            return this;
        }

        public Builder mergeReadTime(Timestamp value) {
            copyOnWrite();
            ((Target) this.instance).mergeReadTime(value);
            return this;
        }

        public Builder clearReadTime() {
            copyOnWrite();
            ((Target) this.instance).clearReadTime();
            return this;
        }

        public int getTargetId() {
            return ((Target) this.instance).getTargetId();
        }

        public Builder setTargetId(int value) {
            copyOnWrite();
            ((Target) this.instance).setTargetId(value);
            return this;
        }

        public Builder clearTargetId() {
            copyOnWrite();
            ((Target) this.instance).clearTargetId();
            return this;
        }

        public boolean getOnce() {
            return ((Target) this.instance).getOnce();
        }

        public Builder setOnce(boolean value) {
            copyOnWrite();
            ((Target) this.instance).setOnce(value);
            return this;
        }

        public Builder clearOnce() {
            copyOnWrite();
            ((Target) this.instance).clearOnce();
            return this;
        }

        public boolean hasExpectedCount() {
            return ((Target) this.instance).hasExpectedCount();
        }

        public Int32Value getExpectedCount() {
            return ((Target) this.instance).getExpectedCount();
        }

        public Builder setExpectedCount(Int32Value value) {
            copyOnWrite();
            ((Target) this.instance).setExpectedCount(value);
            return this;
        }

        public Builder setExpectedCount(Int32Value.Builder builderForValue) {
            copyOnWrite();
            ((Target) this.instance).setExpectedCount((Int32Value) builderForValue.build());
            return this;
        }

        public Builder mergeExpectedCount(Int32Value value) {
            copyOnWrite();
            ((Target) this.instance).mergeExpectedCount(value);
            return this;
        }

        public Builder clearExpectedCount() {
            copyOnWrite();
            ((Target) this.instance).clearExpectedCount();
            return this;
        }
    }

    /* access modifiers changed from: protected */
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
        switch (method) {
            case NEW_MUTABLE_INSTANCE:
                return new Target();
            case NEW_BUILDER:
                return new Builder();
            case BUILD_MESSAGE_INFO:
                return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0007\u0002\u0001\u0002\f\u0007\u0000\u0000\u0000\u0002<\u0000\u0003<\u0000\u0004=\u0001\u0005\u0004\u0006\u0007\u000b<\u0001\fဉ\u0000", new Object[]{"targetType_", "targetTypeCase_", "resumeType_", "resumeTypeCase_", "bitField0_", QueryTarget.class, DocumentsTarget.class, "targetId_", "once_", Timestamp.class, "expectedCount_"});
            case GET_DEFAULT_INSTANCE:
                return DEFAULT_INSTANCE;
            case GET_PARSER:
                Parser<Target> parser = PARSER;
                if (parser == null) {
                    synchronized (Target.class) {
                        parser = PARSER;
                        if (parser == null) {
                            parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                            PARSER = parser;
                        }
                    }
                }
                return parser;
            case GET_MEMOIZED_IS_INITIALIZED:
                return (byte) 1;
            case SET_MEMOIZED_IS_INITIALIZED:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    static {
        Target defaultInstance = new Target();
        DEFAULT_INSTANCE = defaultInstance;
        GeneratedMessageLite.registerDefaultInstance(Target.class, defaultInstance);
    }

    public static Target getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }

    public static Parser<Target> parser() {
        return DEFAULT_INSTANCE.getParserForType();
    }
}
