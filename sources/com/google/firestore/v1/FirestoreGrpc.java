package com.google.firestore.v1;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.protobuf.Empty;
import io.grpc.BindableService;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.MethodDescriptor;
import io.grpc.ServerServiceDefinition;
import io.grpc.ServiceDescriptor;
import io.grpc.protobuf.lite.ProtoLiteUtils;
import io.grpc.stub.AbstractAsyncStub;
import io.grpc.stub.AbstractBlockingStub;
import io.grpc.stub.AbstractFutureStub;
import io.grpc.stub.AbstractStub;
import io.grpc.stub.ClientCalls;
import io.grpc.stub.ServerCalls;
import io.grpc.stub.StreamObserver;
import java.util.Iterator;

public final class FirestoreGrpc {
    private static final int METHODID_BATCH_GET_DOCUMENTS = 4;
    private static final int METHODID_BEGIN_TRANSACTION = 5;
    private static final int METHODID_COMMIT = 6;
    private static final int METHODID_CREATE_DOCUMENT = 11;
    private static final int METHODID_DELETE_DOCUMENT = 3;
    private static final int METHODID_GET_DOCUMENT = 0;
    private static final int METHODID_LISTEN = 13;
    private static final int METHODID_LIST_COLLECTION_IDS = 10;
    private static final int METHODID_LIST_DOCUMENTS = 1;
    private static final int METHODID_ROLLBACK = 7;
    private static final int METHODID_RUN_AGGREGATION_QUERY = 9;
    private static final int METHODID_RUN_QUERY = 8;
    private static final int METHODID_UPDATE_DOCUMENT = 2;
    private static final int METHODID_WRITE = 12;
    public static final String SERVICE_NAME = "google.firestore.v1.Firestore";
    private static volatile MethodDescriptor<BatchGetDocumentsRequest, BatchGetDocumentsResponse> getBatchGetDocumentsMethod;
    private static volatile MethodDescriptor<BeginTransactionRequest, BeginTransactionResponse> getBeginTransactionMethod;
    private static volatile MethodDescriptor<CommitRequest, CommitResponse> getCommitMethod;
    private static volatile MethodDescriptor<CreateDocumentRequest, Document> getCreateDocumentMethod;
    private static volatile MethodDescriptor<DeleteDocumentRequest, Empty> getDeleteDocumentMethod;
    private static volatile MethodDescriptor<GetDocumentRequest, Document> getGetDocumentMethod;
    private static volatile MethodDescriptor<ListCollectionIdsRequest, ListCollectionIdsResponse> getListCollectionIdsMethod;
    private static volatile MethodDescriptor<ListDocumentsRequest, ListDocumentsResponse> getListDocumentsMethod;
    private static volatile MethodDescriptor<ListenRequest, ListenResponse> getListenMethod;
    private static volatile MethodDescriptor<RollbackRequest, Empty> getRollbackMethod;
    private static volatile MethodDescriptor<RunAggregationQueryRequest, RunAggregationQueryResponse> getRunAggregationQueryMethod;
    private static volatile MethodDescriptor<RunQueryRequest, RunQueryResponse> getRunQueryMethod;
    private static volatile MethodDescriptor<UpdateDocumentRequest, Document> getUpdateDocumentMethod;
    private static volatile MethodDescriptor<WriteRequest, WriteResponse> getWriteMethod;
    private static volatile ServiceDescriptor serviceDescriptor;

    private FirestoreGrpc() {
    }

    public static MethodDescriptor<GetDocumentRequest, Document> getGetDocumentMethod() {
        MethodDescriptor<GetDocumentRequest, Document> methodDescriptor = getGetDocumentMethod;
        MethodDescriptor<GetDocumentRequest, Document> getGetDocumentMethod2 = methodDescriptor;
        if (methodDescriptor == null) {
            synchronized (FirestoreGrpc.class) {
                MethodDescriptor<GetDocumentRequest, Document> methodDescriptor2 = getGetDocumentMethod;
                getGetDocumentMethod2 = methodDescriptor2;
                if (methodDescriptor2 == null) {
                    MethodDescriptor<GetDocumentRequest, Document> build = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "GetDocument")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoLiteUtils.marshaller(GetDocumentRequest.getDefaultInstance())).setResponseMarshaller(ProtoLiteUtils.marshaller(Document.getDefaultInstance())).build();
                    getGetDocumentMethod2 = build;
                    getGetDocumentMethod = build;
                }
            }
        }
        return getGetDocumentMethod2;
    }

    public static MethodDescriptor<ListDocumentsRequest, ListDocumentsResponse> getListDocumentsMethod() {
        MethodDescriptor<ListDocumentsRequest, ListDocumentsResponse> methodDescriptor = getListDocumentsMethod;
        MethodDescriptor<ListDocumentsRequest, ListDocumentsResponse> getListDocumentsMethod2 = methodDescriptor;
        if (methodDescriptor == null) {
            synchronized (FirestoreGrpc.class) {
                MethodDescriptor<ListDocumentsRequest, ListDocumentsResponse> methodDescriptor2 = getListDocumentsMethod;
                getListDocumentsMethod2 = methodDescriptor2;
                if (methodDescriptor2 == null) {
                    MethodDescriptor<ListDocumentsRequest, ListDocumentsResponse> build = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "ListDocuments")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoLiteUtils.marshaller(ListDocumentsRequest.getDefaultInstance())).setResponseMarshaller(ProtoLiteUtils.marshaller(ListDocumentsResponse.getDefaultInstance())).build();
                    getListDocumentsMethod2 = build;
                    getListDocumentsMethod = build;
                }
            }
        }
        return getListDocumentsMethod2;
    }

    public static MethodDescriptor<UpdateDocumentRequest, Document> getUpdateDocumentMethod() {
        MethodDescriptor<UpdateDocumentRequest, Document> methodDescriptor = getUpdateDocumentMethod;
        MethodDescriptor<UpdateDocumentRequest, Document> getUpdateDocumentMethod2 = methodDescriptor;
        if (methodDescriptor == null) {
            synchronized (FirestoreGrpc.class) {
                MethodDescriptor<UpdateDocumentRequest, Document> methodDescriptor2 = getUpdateDocumentMethod;
                getUpdateDocumentMethod2 = methodDescriptor2;
                if (methodDescriptor2 == null) {
                    MethodDescriptor<UpdateDocumentRequest, Document> build = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "UpdateDocument")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoLiteUtils.marshaller(UpdateDocumentRequest.getDefaultInstance())).setResponseMarshaller(ProtoLiteUtils.marshaller(Document.getDefaultInstance())).build();
                    getUpdateDocumentMethod2 = build;
                    getUpdateDocumentMethod = build;
                }
            }
        }
        return getUpdateDocumentMethod2;
    }

    public static MethodDescriptor<DeleteDocumentRequest, Empty> getDeleteDocumentMethod() {
        MethodDescriptor<DeleteDocumentRequest, Empty> methodDescriptor = getDeleteDocumentMethod;
        MethodDescriptor<DeleteDocumentRequest, Empty> getDeleteDocumentMethod2 = methodDescriptor;
        if (methodDescriptor == null) {
            synchronized (FirestoreGrpc.class) {
                MethodDescriptor<DeleteDocumentRequest, Empty> methodDescriptor2 = getDeleteDocumentMethod;
                getDeleteDocumentMethod2 = methodDescriptor2;
                if (methodDescriptor2 == null) {
                    MethodDescriptor<DeleteDocumentRequest, Empty> build = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "DeleteDocument")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoLiteUtils.marshaller(DeleteDocumentRequest.getDefaultInstance())).setResponseMarshaller(ProtoLiteUtils.marshaller(Empty.getDefaultInstance())).build();
                    getDeleteDocumentMethod2 = build;
                    getDeleteDocumentMethod = build;
                }
            }
        }
        return getDeleteDocumentMethod2;
    }

    public static MethodDescriptor<BatchGetDocumentsRequest, BatchGetDocumentsResponse> getBatchGetDocumentsMethod() {
        MethodDescriptor<BatchGetDocumentsRequest, BatchGetDocumentsResponse> methodDescriptor = getBatchGetDocumentsMethod;
        MethodDescriptor<BatchGetDocumentsRequest, BatchGetDocumentsResponse> getBatchGetDocumentsMethod2 = methodDescriptor;
        if (methodDescriptor == null) {
            synchronized (FirestoreGrpc.class) {
                MethodDescriptor<BatchGetDocumentsRequest, BatchGetDocumentsResponse> methodDescriptor2 = getBatchGetDocumentsMethod;
                getBatchGetDocumentsMethod2 = methodDescriptor2;
                if (methodDescriptor2 == null) {
                    MethodDescriptor<BatchGetDocumentsRequest, BatchGetDocumentsResponse> build = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.SERVER_STREAMING).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "BatchGetDocuments")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoLiteUtils.marshaller(BatchGetDocumentsRequest.getDefaultInstance())).setResponseMarshaller(ProtoLiteUtils.marshaller(BatchGetDocumentsResponse.getDefaultInstance())).build();
                    getBatchGetDocumentsMethod2 = build;
                    getBatchGetDocumentsMethod = build;
                }
            }
        }
        return getBatchGetDocumentsMethod2;
    }

    public static MethodDescriptor<BeginTransactionRequest, BeginTransactionResponse> getBeginTransactionMethod() {
        MethodDescriptor<BeginTransactionRequest, BeginTransactionResponse> methodDescriptor = getBeginTransactionMethod;
        MethodDescriptor<BeginTransactionRequest, BeginTransactionResponse> getBeginTransactionMethod2 = methodDescriptor;
        if (methodDescriptor == null) {
            synchronized (FirestoreGrpc.class) {
                MethodDescriptor<BeginTransactionRequest, BeginTransactionResponse> methodDescriptor2 = getBeginTransactionMethod;
                getBeginTransactionMethod2 = methodDescriptor2;
                if (methodDescriptor2 == null) {
                    MethodDescriptor<BeginTransactionRequest, BeginTransactionResponse> build = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "BeginTransaction")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoLiteUtils.marshaller(BeginTransactionRequest.getDefaultInstance())).setResponseMarshaller(ProtoLiteUtils.marshaller(BeginTransactionResponse.getDefaultInstance())).build();
                    getBeginTransactionMethod2 = build;
                    getBeginTransactionMethod = build;
                }
            }
        }
        return getBeginTransactionMethod2;
    }

    public static MethodDescriptor<CommitRequest, CommitResponse> getCommitMethod() {
        MethodDescriptor<CommitRequest, CommitResponse> methodDescriptor = getCommitMethod;
        MethodDescriptor<CommitRequest, CommitResponse> getCommitMethod2 = methodDescriptor;
        if (methodDescriptor == null) {
            synchronized (FirestoreGrpc.class) {
                MethodDescriptor<CommitRequest, CommitResponse> methodDescriptor2 = getCommitMethod;
                getCommitMethod2 = methodDescriptor2;
                if (methodDescriptor2 == null) {
                    MethodDescriptor<CommitRequest, CommitResponse> build = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Commit")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoLiteUtils.marshaller(CommitRequest.getDefaultInstance())).setResponseMarshaller(ProtoLiteUtils.marshaller(CommitResponse.getDefaultInstance())).build();
                    getCommitMethod2 = build;
                    getCommitMethod = build;
                }
            }
        }
        return getCommitMethod2;
    }

    public static MethodDescriptor<RollbackRequest, Empty> getRollbackMethod() {
        MethodDescriptor<RollbackRequest, Empty> methodDescriptor = getRollbackMethod;
        MethodDescriptor<RollbackRequest, Empty> getRollbackMethod2 = methodDescriptor;
        if (methodDescriptor == null) {
            synchronized (FirestoreGrpc.class) {
                MethodDescriptor<RollbackRequest, Empty> methodDescriptor2 = getRollbackMethod;
                getRollbackMethod2 = methodDescriptor2;
                if (methodDescriptor2 == null) {
                    MethodDescriptor<RollbackRequest, Empty> build = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Rollback")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoLiteUtils.marshaller(RollbackRequest.getDefaultInstance())).setResponseMarshaller(ProtoLiteUtils.marshaller(Empty.getDefaultInstance())).build();
                    getRollbackMethod2 = build;
                    getRollbackMethod = build;
                }
            }
        }
        return getRollbackMethod2;
    }

    public static MethodDescriptor<RunQueryRequest, RunQueryResponse> getRunQueryMethod() {
        MethodDescriptor<RunQueryRequest, RunQueryResponse> methodDescriptor = getRunQueryMethod;
        MethodDescriptor<RunQueryRequest, RunQueryResponse> getRunQueryMethod2 = methodDescriptor;
        if (methodDescriptor == null) {
            synchronized (FirestoreGrpc.class) {
                MethodDescriptor<RunQueryRequest, RunQueryResponse> methodDescriptor2 = getRunQueryMethod;
                getRunQueryMethod2 = methodDescriptor2;
                if (methodDescriptor2 == null) {
                    MethodDescriptor<RunQueryRequest, RunQueryResponse> build = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.SERVER_STREAMING).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "RunQuery")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoLiteUtils.marshaller(RunQueryRequest.getDefaultInstance())).setResponseMarshaller(ProtoLiteUtils.marshaller(RunQueryResponse.getDefaultInstance())).build();
                    getRunQueryMethod2 = build;
                    getRunQueryMethod = build;
                }
            }
        }
        return getRunQueryMethod2;
    }

    public static MethodDescriptor<RunAggregationQueryRequest, RunAggregationQueryResponse> getRunAggregationQueryMethod() {
        MethodDescriptor<RunAggregationQueryRequest, RunAggregationQueryResponse> methodDescriptor = getRunAggregationQueryMethod;
        MethodDescriptor<RunAggregationQueryRequest, RunAggregationQueryResponse> getRunAggregationQueryMethod2 = methodDescriptor;
        if (methodDescriptor == null) {
            synchronized (FirestoreGrpc.class) {
                MethodDescriptor<RunAggregationQueryRequest, RunAggregationQueryResponse> methodDescriptor2 = getRunAggregationQueryMethod;
                getRunAggregationQueryMethod2 = methodDescriptor2;
                if (methodDescriptor2 == null) {
                    MethodDescriptor<RunAggregationQueryRequest, RunAggregationQueryResponse> build = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.SERVER_STREAMING).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "RunAggregationQuery")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoLiteUtils.marshaller(RunAggregationQueryRequest.getDefaultInstance())).setResponseMarshaller(ProtoLiteUtils.marshaller(RunAggregationQueryResponse.getDefaultInstance())).build();
                    getRunAggregationQueryMethod2 = build;
                    getRunAggregationQueryMethod = build;
                }
            }
        }
        return getRunAggregationQueryMethod2;
    }

    public static MethodDescriptor<WriteRequest, WriteResponse> getWriteMethod() {
        MethodDescriptor<WriteRequest, WriteResponse> methodDescriptor = getWriteMethod;
        MethodDescriptor<WriteRequest, WriteResponse> getWriteMethod2 = methodDescriptor;
        if (methodDescriptor == null) {
            synchronized (FirestoreGrpc.class) {
                MethodDescriptor<WriteRequest, WriteResponse> methodDescriptor2 = getWriteMethod;
                getWriteMethod2 = methodDescriptor2;
                if (methodDescriptor2 == null) {
                    MethodDescriptor<WriteRequest, WriteResponse> build = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.BIDI_STREAMING).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Write")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoLiteUtils.marshaller(WriteRequest.getDefaultInstance())).setResponseMarshaller(ProtoLiteUtils.marshaller(WriteResponse.getDefaultInstance())).build();
                    getWriteMethod2 = build;
                    getWriteMethod = build;
                }
            }
        }
        return getWriteMethod2;
    }

    public static MethodDescriptor<ListenRequest, ListenResponse> getListenMethod() {
        MethodDescriptor<ListenRequest, ListenResponse> methodDescriptor = getListenMethod;
        MethodDescriptor<ListenRequest, ListenResponse> getListenMethod2 = methodDescriptor;
        if (methodDescriptor == null) {
            synchronized (FirestoreGrpc.class) {
                MethodDescriptor<ListenRequest, ListenResponse> methodDescriptor2 = getListenMethod;
                getListenMethod2 = methodDescriptor2;
                if (methodDescriptor2 == null) {
                    MethodDescriptor<ListenRequest, ListenResponse> build = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.BIDI_STREAMING).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "Listen")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoLiteUtils.marshaller(ListenRequest.getDefaultInstance())).setResponseMarshaller(ProtoLiteUtils.marshaller(ListenResponse.getDefaultInstance())).build();
                    getListenMethod2 = build;
                    getListenMethod = build;
                }
            }
        }
        return getListenMethod2;
    }

    public static MethodDescriptor<ListCollectionIdsRequest, ListCollectionIdsResponse> getListCollectionIdsMethod() {
        MethodDescriptor<ListCollectionIdsRequest, ListCollectionIdsResponse> methodDescriptor = getListCollectionIdsMethod;
        MethodDescriptor<ListCollectionIdsRequest, ListCollectionIdsResponse> getListCollectionIdsMethod2 = methodDescriptor;
        if (methodDescriptor == null) {
            synchronized (FirestoreGrpc.class) {
                MethodDescriptor<ListCollectionIdsRequest, ListCollectionIdsResponse> methodDescriptor2 = getListCollectionIdsMethod;
                getListCollectionIdsMethod2 = methodDescriptor2;
                if (methodDescriptor2 == null) {
                    MethodDescriptor<ListCollectionIdsRequest, ListCollectionIdsResponse> build = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "ListCollectionIds")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoLiteUtils.marshaller(ListCollectionIdsRequest.getDefaultInstance())).setResponseMarshaller(ProtoLiteUtils.marshaller(ListCollectionIdsResponse.getDefaultInstance())).build();
                    getListCollectionIdsMethod2 = build;
                    getListCollectionIdsMethod = build;
                }
            }
        }
        return getListCollectionIdsMethod2;
    }

    public static MethodDescriptor<CreateDocumentRequest, Document> getCreateDocumentMethod() {
        MethodDescriptor<CreateDocumentRequest, Document> methodDescriptor = getCreateDocumentMethod;
        MethodDescriptor<CreateDocumentRequest, Document> getCreateDocumentMethod2 = methodDescriptor;
        if (methodDescriptor == null) {
            synchronized (FirestoreGrpc.class) {
                MethodDescriptor<CreateDocumentRequest, Document> methodDescriptor2 = getCreateDocumentMethod;
                getCreateDocumentMethod2 = methodDescriptor2;
                if (methodDescriptor2 == null) {
                    MethodDescriptor<CreateDocumentRequest, Document> build = MethodDescriptor.newBuilder().setType(MethodDescriptor.MethodType.UNARY).setFullMethodName(MethodDescriptor.generateFullMethodName(SERVICE_NAME, "CreateDocument")).setSampledToLocalTracing(true).setRequestMarshaller(ProtoLiteUtils.marshaller(CreateDocumentRequest.getDefaultInstance())).setResponseMarshaller(ProtoLiteUtils.marshaller(Document.getDefaultInstance())).build();
                    getCreateDocumentMethod2 = build;
                    getCreateDocumentMethod = build;
                }
            }
        }
        return getCreateDocumentMethod2;
    }

    public static FirestoreStub newStub(Channel channel) {
        return (FirestoreStub) FirestoreStub.newStub(new AbstractStub.StubFactory<FirestoreStub>() {
            public FirestoreStub newStub(Channel channel, CallOptions callOptions) {
                return new FirestoreStub(channel, callOptions);
            }
        }, channel);
    }

    public static FirestoreBlockingStub newBlockingStub(Channel channel) {
        return (FirestoreBlockingStub) FirestoreBlockingStub.newStub(new AbstractStub.StubFactory<FirestoreBlockingStub>() {
            public FirestoreBlockingStub newStub(Channel channel, CallOptions callOptions) {
                return new FirestoreBlockingStub(channel, callOptions);
            }
        }, channel);
    }

    public static FirestoreFutureStub newFutureStub(Channel channel) {
        return (FirestoreFutureStub) FirestoreFutureStub.newStub(new AbstractStub.StubFactory<FirestoreFutureStub>() {
            public FirestoreFutureStub newStub(Channel channel, CallOptions callOptions) {
                return new FirestoreFutureStub(channel, callOptions);
            }
        }, channel);
    }

    public interface AsyncService {
        void getDocument(GetDocumentRequest request, StreamObserver<Document> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(FirestoreGrpc.getGetDocumentMethod(), responseObserver);
        }

        void listDocuments(ListDocumentsRequest request, StreamObserver<ListDocumentsResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(FirestoreGrpc.getListDocumentsMethod(), responseObserver);
        }

        void updateDocument(UpdateDocumentRequest request, StreamObserver<Document> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(FirestoreGrpc.getUpdateDocumentMethod(), responseObserver);
        }

        void deleteDocument(DeleteDocumentRequest request, StreamObserver<Empty> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(FirestoreGrpc.getDeleteDocumentMethod(), responseObserver);
        }

        void batchGetDocuments(BatchGetDocumentsRequest request, StreamObserver<BatchGetDocumentsResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(FirestoreGrpc.getBatchGetDocumentsMethod(), responseObserver);
        }

        void beginTransaction(BeginTransactionRequest request, StreamObserver<BeginTransactionResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(FirestoreGrpc.getBeginTransactionMethod(), responseObserver);
        }

        void commit(CommitRequest request, StreamObserver<CommitResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(FirestoreGrpc.getCommitMethod(), responseObserver);
        }

        void rollback(RollbackRequest request, StreamObserver<Empty> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(FirestoreGrpc.getRollbackMethod(), responseObserver);
        }

        void runQuery(RunQueryRequest request, StreamObserver<RunQueryResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(FirestoreGrpc.getRunQueryMethod(), responseObserver);
        }

        void runAggregationQuery(RunAggregationQueryRequest request, StreamObserver<RunAggregationQueryResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(FirestoreGrpc.getRunAggregationQueryMethod(), responseObserver);
        }

        StreamObserver<WriteRequest> write(StreamObserver<WriteResponse> responseObserver) {
            return ServerCalls.asyncUnimplementedStreamingCall(FirestoreGrpc.getWriteMethod(), responseObserver);
        }

        StreamObserver<ListenRequest> listen(StreamObserver<ListenResponse> responseObserver) {
            return ServerCalls.asyncUnimplementedStreamingCall(FirestoreGrpc.getListenMethod(), responseObserver);
        }

        void listCollectionIds(ListCollectionIdsRequest request, StreamObserver<ListCollectionIdsResponse> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(FirestoreGrpc.getListCollectionIdsMethod(), responseObserver);
        }

        void createDocument(CreateDocumentRequest request, StreamObserver<Document> responseObserver) {
            ServerCalls.asyncUnimplementedUnaryCall(FirestoreGrpc.getCreateDocumentMethod(), responseObserver);
        }
    }

    public static abstract class FirestoreImplBase implements BindableService, AsyncService {
        public final ServerServiceDefinition bindService() {
            return FirestoreGrpc.bindService(this);
        }
    }

    public static final class FirestoreStub extends AbstractAsyncStub<FirestoreStub> {
        private FirestoreStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        /* access modifiers changed from: protected */
        public FirestoreStub build(Channel channel, CallOptions callOptions) {
            return new FirestoreStub(channel, callOptions);
        }

        public void getDocument(GetDocumentRequest request, StreamObserver<Document> responseObserver) {
            ClientCalls.asyncUnaryCall(getChannel().newCall(FirestoreGrpc.getGetDocumentMethod(), getCallOptions()), request, responseObserver);
        }

        public void listDocuments(ListDocumentsRequest request, StreamObserver<ListDocumentsResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(getChannel().newCall(FirestoreGrpc.getListDocumentsMethod(), getCallOptions()), request, responseObserver);
        }

        public void updateDocument(UpdateDocumentRequest request, StreamObserver<Document> responseObserver) {
            ClientCalls.asyncUnaryCall(getChannel().newCall(FirestoreGrpc.getUpdateDocumentMethod(), getCallOptions()), request, responseObserver);
        }

        public void deleteDocument(DeleteDocumentRequest request, StreamObserver<Empty> responseObserver) {
            ClientCalls.asyncUnaryCall(getChannel().newCall(FirestoreGrpc.getDeleteDocumentMethod(), getCallOptions()), request, responseObserver);
        }

        public void batchGetDocuments(BatchGetDocumentsRequest request, StreamObserver<BatchGetDocumentsResponse> responseObserver) {
            ClientCalls.asyncServerStreamingCall(getChannel().newCall(FirestoreGrpc.getBatchGetDocumentsMethod(), getCallOptions()), request, responseObserver);
        }

        public void beginTransaction(BeginTransactionRequest request, StreamObserver<BeginTransactionResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(getChannel().newCall(FirestoreGrpc.getBeginTransactionMethod(), getCallOptions()), request, responseObserver);
        }

        public void commit(CommitRequest request, StreamObserver<CommitResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(getChannel().newCall(FirestoreGrpc.getCommitMethod(), getCallOptions()), request, responseObserver);
        }

        public void rollback(RollbackRequest request, StreamObserver<Empty> responseObserver) {
            ClientCalls.asyncUnaryCall(getChannel().newCall(FirestoreGrpc.getRollbackMethod(), getCallOptions()), request, responseObserver);
        }

        public void runQuery(RunQueryRequest request, StreamObserver<RunQueryResponse> responseObserver) {
            ClientCalls.asyncServerStreamingCall(getChannel().newCall(FirestoreGrpc.getRunQueryMethod(), getCallOptions()), request, responseObserver);
        }

        public void runAggregationQuery(RunAggregationQueryRequest request, StreamObserver<RunAggregationQueryResponse> responseObserver) {
            ClientCalls.asyncServerStreamingCall(getChannel().newCall(FirestoreGrpc.getRunAggregationQueryMethod(), getCallOptions()), request, responseObserver);
        }

        public StreamObserver<WriteRequest> write(StreamObserver<WriteResponse> responseObserver) {
            return ClientCalls.asyncBidiStreamingCall(getChannel().newCall(FirestoreGrpc.getWriteMethod(), getCallOptions()), responseObserver);
        }

        public StreamObserver<ListenRequest> listen(StreamObserver<ListenResponse> responseObserver) {
            return ClientCalls.asyncBidiStreamingCall(getChannel().newCall(FirestoreGrpc.getListenMethod(), getCallOptions()), responseObserver);
        }

        public void listCollectionIds(ListCollectionIdsRequest request, StreamObserver<ListCollectionIdsResponse> responseObserver) {
            ClientCalls.asyncUnaryCall(getChannel().newCall(FirestoreGrpc.getListCollectionIdsMethod(), getCallOptions()), request, responseObserver);
        }

        public void createDocument(CreateDocumentRequest request, StreamObserver<Document> responseObserver) {
            ClientCalls.asyncUnaryCall(getChannel().newCall(FirestoreGrpc.getCreateDocumentMethod(), getCallOptions()), request, responseObserver);
        }
    }

    public static final class FirestoreBlockingStub extends AbstractBlockingStub<FirestoreBlockingStub> {
        private FirestoreBlockingStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        /* access modifiers changed from: protected */
        public FirestoreBlockingStub build(Channel channel, CallOptions callOptions) {
            return new FirestoreBlockingStub(channel, callOptions);
        }

        public Document getDocument(GetDocumentRequest request) {
            return (Document) ClientCalls.blockingUnaryCall(getChannel(), FirestoreGrpc.getGetDocumentMethod(), getCallOptions(), request);
        }

        public ListDocumentsResponse listDocuments(ListDocumentsRequest request) {
            return (ListDocumentsResponse) ClientCalls.blockingUnaryCall(getChannel(), FirestoreGrpc.getListDocumentsMethod(), getCallOptions(), request);
        }

        public Document updateDocument(UpdateDocumentRequest request) {
            return (Document) ClientCalls.blockingUnaryCall(getChannel(), FirestoreGrpc.getUpdateDocumentMethod(), getCallOptions(), request);
        }

        public Empty deleteDocument(DeleteDocumentRequest request) {
            return (Empty) ClientCalls.blockingUnaryCall(getChannel(), FirestoreGrpc.getDeleteDocumentMethod(), getCallOptions(), request);
        }

        public Iterator<BatchGetDocumentsResponse> batchGetDocuments(BatchGetDocumentsRequest request) {
            return ClientCalls.blockingServerStreamingCall(getChannel(), FirestoreGrpc.getBatchGetDocumentsMethod(), getCallOptions(), request);
        }

        public BeginTransactionResponse beginTransaction(BeginTransactionRequest request) {
            return (BeginTransactionResponse) ClientCalls.blockingUnaryCall(getChannel(), FirestoreGrpc.getBeginTransactionMethod(), getCallOptions(), request);
        }

        public CommitResponse commit(CommitRequest request) {
            return (CommitResponse) ClientCalls.blockingUnaryCall(getChannel(), FirestoreGrpc.getCommitMethod(), getCallOptions(), request);
        }

        public Empty rollback(RollbackRequest request) {
            return (Empty) ClientCalls.blockingUnaryCall(getChannel(), FirestoreGrpc.getRollbackMethod(), getCallOptions(), request);
        }

        public Iterator<RunQueryResponse> runQuery(RunQueryRequest request) {
            return ClientCalls.blockingServerStreamingCall(getChannel(), FirestoreGrpc.getRunQueryMethod(), getCallOptions(), request);
        }

        public Iterator<RunAggregationQueryResponse> runAggregationQuery(RunAggregationQueryRequest request) {
            return ClientCalls.blockingServerStreamingCall(getChannel(), FirestoreGrpc.getRunAggregationQueryMethod(), getCallOptions(), request);
        }

        public ListCollectionIdsResponse listCollectionIds(ListCollectionIdsRequest request) {
            return (ListCollectionIdsResponse) ClientCalls.blockingUnaryCall(getChannel(), FirestoreGrpc.getListCollectionIdsMethod(), getCallOptions(), request);
        }

        public Document createDocument(CreateDocumentRequest request) {
            return (Document) ClientCalls.blockingUnaryCall(getChannel(), FirestoreGrpc.getCreateDocumentMethod(), getCallOptions(), request);
        }
    }

    public static final class FirestoreFutureStub extends AbstractFutureStub<FirestoreFutureStub> {
        private FirestoreFutureStub(Channel channel, CallOptions callOptions) {
            super(channel, callOptions);
        }

        /* access modifiers changed from: protected */
        public FirestoreFutureStub build(Channel channel, CallOptions callOptions) {
            return new FirestoreFutureStub(channel, callOptions);
        }

        public ListenableFuture<Document> getDocument(GetDocumentRequest request) {
            return ClientCalls.futureUnaryCall(getChannel().newCall(FirestoreGrpc.getGetDocumentMethod(), getCallOptions()), request);
        }

        public ListenableFuture<ListDocumentsResponse> listDocuments(ListDocumentsRequest request) {
            return ClientCalls.futureUnaryCall(getChannel().newCall(FirestoreGrpc.getListDocumentsMethod(), getCallOptions()), request);
        }

        public ListenableFuture<Document> updateDocument(UpdateDocumentRequest request) {
            return ClientCalls.futureUnaryCall(getChannel().newCall(FirestoreGrpc.getUpdateDocumentMethod(), getCallOptions()), request);
        }

        public ListenableFuture<Empty> deleteDocument(DeleteDocumentRequest request) {
            return ClientCalls.futureUnaryCall(getChannel().newCall(FirestoreGrpc.getDeleteDocumentMethod(), getCallOptions()), request);
        }

        public ListenableFuture<BeginTransactionResponse> beginTransaction(BeginTransactionRequest request) {
            return ClientCalls.futureUnaryCall(getChannel().newCall(FirestoreGrpc.getBeginTransactionMethod(), getCallOptions()), request);
        }

        public ListenableFuture<CommitResponse> commit(CommitRequest request) {
            return ClientCalls.futureUnaryCall(getChannel().newCall(FirestoreGrpc.getCommitMethod(), getCallOptions()), request);
        }

        public ListenableFuture<Empty> rollback(RollbackRequest request) {
            return ClientCalls.futureUnaryCall(getChannel().newCall(FirestoreGrpc.getRollbackMethod(), getCallOptions()), request);
        }

        public ListenableFuture<ListCollectionIdsResponse> listCollectionIds(ListCollectionIdsRequest request) {
            return ClientCalls.futureUnaryCall(getChannel().newCall(FirestoreGrpc.getListCollectionIdsMethod(), getCallOptions()), request);
        }

        public ListenableFuture<Document> createDocument(CreateDocumentRequest request) {
            return ClientCalls.futureUnaryCall(getChannel().newCall(FirestoreGrpc.getCreateDocumentMethod(), getCallOptions()), request);
        }
    }

    private static final class MethodHandlers<Req, Resp> implements ServerCalls.UnaryMethod<Req, Resp>, ServerCalls.ServerStreamingMethod<Req, Resp>, ServerCalls.ClientStreamingMethod<Req, Resp>, ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final int methodId;
        private final AsyncService serviceImpl;

        MethodHandlers(AsyncService serviceImpl2, int methodId2) {
            this.serviceImpl = serviceImpl2;
            this.methodId = methodId2;
        }

        public void invoke(Req request, StreamObserver<Resp> responseObserver) {
            switch (this.methodId) {
                case 0:
                    this.serviceImpl.getDocument((GetDocumentRequest) request, responseObserver);
                    return;
                case 1:
                    this.serviceImpl.listDocuments((ListDocumentsRequest) request, responseObserver);
                    return;
                case 2:
                    this.serviceImpl.updateDocument((UpdateDocumentRequest) request, responseObserver);
                    return;
                case 3:
                    this.serviceImpl.deleteDocument((DeleteDocumentRequest) request, responseObserver);
                    return;
                case 4:
                    this.serviceImpl.batchGetDocuments((BatchGetDocumentsRequest) request, responseObserver);
                    return;
                case 5:
                    this.serviceImpl.beginTransaction((BeginTransactionRequest) request, responseObserver);
                    return;
                case 6:
                    this.serviceImpl.commit((CommitRequest) request, responseObserver);
                    return;
                case 7:
                    this.serviceImpl.rollback((RollbackRequest) request, responseObserver);
                    return;
                case 8:
                    this.serviceImpl.runQuery((RunQueryRequest) request, responseObserver);
                    return;
                case 9:
                    this.serviceImpl.runAggregationQuery((RunAggregationQueryRequest) request, responseObserver);
                    return;
                case 10:
                    this.serviceImpl.listCollectionIds((ListCollectionIdsRequest) request, responseObserver);
                    return;
                case 11:
                    this.serviceImpl.createDocument((CreateDocumentRequest) request, responseObserver);
                    return;
                default:
                    throw new AssertionError();
            }
        }

        public StreamObserver<Req> invoke(StreamObserver<Resp> responseObserver) {
            switch (this.methodId) {
                case 12:
                    return this.serviceImpl.write(responseObserver);
                case 13:
                    return this.serviceImpl.listen(responseObserver);
                default:
                    throw new AssertionError();
            }
        }
    }

    public static final ServerServiceDefinition bindService(AsyncService service) {
        return ServerServiceDefinition.builder(getServiceDescriptor()).addMethod(getGetDocumentMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service, 0))).addMethod(getListDocumentsMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service, 1))).addMethod(getUpdateDocumentMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service, 2))).addMethod(getDeleteDocumentMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service, 3))).addMethod(getBatchGetDocumentsMethod(), ServerCalls.asyncServerStreamingCall(new MethodHandlers(service, 4))).addMethod(getBeginTransactionMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service, 5))).addMethod(getCommitMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service, 6))).addMethod(getRollbackMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service, 7))).addMethod(getRunQueryMethod(), ServerCalls.asyncServerStreamingCall(new MethodHandlers(service, 8))).addMethod(getRunAggregationQueryMethod(), ServerCalls.asyncServerStreamingCall(new MethodHandlers(service, 9))).addMethod(getWriteMethod(), ServerCalls.asyncBidiStreamingCall(new MethodHandlers(service, 12))).addMethod(getListenMethod(), ServerCalls.asyncBidiStreamingCall(new MethodHandlers(service, 13))).addMethod(getListCollectionIdsMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service, 10))).addMethod(getCreateDocumentMethod(), ServerCalls.asyncUnaryCall(new MethodHandlers(service, 11))).build();
    }

    public static ServiceDescriptor getServiceDescriptor() {
        ServiceDescriptor result = serviceDescriptor;
        if (result == null) {
            synchronized (FirestoreGrpc.class) {
                result = serviceDescriptor;
                if (result == null) {
                    ServiceDescriptor build = ServiceDescriptor.newBuilder(SERVICE_NAME).addMethod(getGetDocumentMethod()).addMethod(getListDocumentsMethod()).addMethod(getUpdateDocumentMethod()).addMethod(getDeleteDocumentMethod()).addMethod(getBatchGetDocumentsMethod()).addMethod(getBeginTransactionMethod()).addMethod(getCommitMethod()).addMethod(getRollbackMethod()).addMethod(getRunQueryMethod()).addMethod(getRunAggregationQueryMethod()).addMethod(getWriteMethod()).addMethod(getListenMethod()).addMethod(getListCollectionIdsMethod()).addMethod(getCreateDocumentMethod()).build();
                    result = build;
                    serviceDescriptor = build;
                }
            }
        }
        return result;
    }
}
