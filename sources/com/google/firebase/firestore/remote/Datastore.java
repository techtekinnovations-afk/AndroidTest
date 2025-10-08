package com.google.firebase.firestore.remote;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.AggregateField;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MutableDocument;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.MutationResult;
import com.google.firebase.firestore.remote.FirestoreChannel;
import com.google.firebase.firestore.remote.WatchStream;
import com.google.firebase.firestore.remote.WriteStream;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.Util;
import com.google.firestore.v1.BatchGetDocumentsRequest;
import com.google.firestore.v1.BatchGetDocumentsResponse;
import com.google.firestore.v1.CommitRequest;
import com.google.firestore.v1.CommitResponse;
import com.google.firestore.v1.FirestoreGrpc;
import com.google.firestore.v1.RunAggregationQueryRequest;
import com.google.firestore.v1.RunAggregationQueryResponse;
import com.google.firestore.v1.StructuredAggregationQuery;
import com.google.firestore.v1.Target;
import com.google.firestore.v1.Value;
import io.grpc.Status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.SSLHandshakeException;

public class Datastore {
    static final String SSL_DEPENDENCY_ERROR_MESSAGE = "The Cloud Firestore client failed to establish a secure connection. This is likely a problem with your app, rather than with Cloud Firestore itself. See https://bit.ly/2XFpdma for instructions on how to enable TLS on Android 4.x devices.";
    static final Set<String> WHITE_LISTED_HEADERS = new HashSet(Arrays.asList(new String[]{"date", "x-google-backends", "x-google-netmon-label", "x-google-service", "x-google-gfe-request-trace"}));
    /* access modifiers changed from: private */
    public final FirestoreChannel channel;
    protected final RemoteSerializer serializer;
    private final AsyncQueue workerQueue;

    Datastore(AsyncQueue workerQueue2, RemoteSerializer serializer2, FirestoreChannel channel2) {
        this.workerQueue = workerQueue2;
        this.serializer = serializer2;
        this.channel = channel2;
    }

    /* access modifiers changed from: package-private */
    public void shutdown() {
        this.channel.shutdown();
    }

    /* access modifiers changed from: package-private */
    public AsyncQueue getWorkerQueue() {
        return this.workerQueue;
    }

    /* access modifiers changed from: package-private */
    public WatchStream createWatchStream(WatchStream.Callback listener) {
        return new WatchStream(this.channel, this.workerQueue, this.serializer, listener);
    }

    /* access modifiers changed from: package-private */
    public WriteStream createWriteStream(WriteStream.Callback listener) {
        return new WriteStream(this.channel, this.workerQueue, this.serializer, listener);
    }

    public Task<List<MutationResult>> commit(List<Mutation> mutations) {
        CommitRequest.Builder builder = CommitRequest.newBuilder();
        builder.setDatabase(this.serializer.databaseName());
        for (Mutation mutation : mutations) {
            builder.addWrites(this.serializer.encodeMutation(mutation));
        }
        return this.channel.runRpc(FirestoreGrpc.getCommitMethod(), (CommitRequest) builder.build()).continueWith(this.workerQueue.getExecutor(), new Datastore$$ExternalSyntheticLambda1(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$commit$0$com-google-firebase-firestore-remote-Datastore  reason: not valid java name */
    public /* synthetic */ List m1909lambda$commit$0$comgooglefirebasefirestoreremoteDatastore(Task task) throws Exception {
        if (!task.isSuccessful()) {
            if ((task.getException() instanceof FirebaseFirestoreException) && ((FirebaseFirestoreException) task.getException()).getCode() == FirebaseFirestoreException.Code.UNAUTHENTICATED) {
                this.channel.invalidateToken();
            }
            throw task.getException();
        }
        CommitResponse response = (CommitResponse) task.getResult();
        SnapshotVersion commitVersion = this.serializer.decodeVersion(response.getCommitTime());
        int count = response.getWriteResultsCount();
        ArrayList<MutationResult> results = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            results.add(this.serializer.decodeMutationResult(response.getWriteResults(i), commitVersion));
        }
        return results;
    }

    public Task<List<MutableDocument>> lookup(final List<DocumentKey> keys) {
        BatchGetDocumentsRequest.Builder builder = BatchGetDocumentsRequest.newBuilder();
        builder.setDatabase(this.serializer.databaseName());
        for (DocumentKey key : keys) {
            builder.addDocuments(this.serializer.encodeKey(key));
        }
        final List<BatchGetDocumentsResponse> responses = new ArrayList<>();
        final TaskCompletionSource<List<MutableDocument>> completionSource = new TaskCompletionSource<>();
        this.channel.runStreamingResponseRpc(FirestoreGrpc.getBatchGetDocumentsMethod(), (BatchGetDocumentsRequest) builder.build(), new FirestoreChannel.StreamingListener<BatchGetDocumentsResponse>() {
            public void onMessage(BatchGetDocumentsResponse message) {
                responses.add(message);
                if (responses.size() == keys.size()) {
                    Map<DocumentKey, MutableDocument> resultMap = new HashMap<>();
                    for (BatchGetDocumentsResponse response : responses) {
                        MutableDocument doc = Datastore.this.serializer.decodeMaybeDocument(response);
                        resultMap.put(doc.getKey(), doc);
                    }
                    List<MutableDocument> results = new ArrayList<>();
                    for (DocumentKey key : keys) {
                        results.add(resultMap.get(key));
                    }
                    completionSource.trySetResult(results);
                }
            }

            public void onClose(Status status) {
                if (status.isOk()) {
                    completionSource.trySetResult(Collections.emptyList());
                    return;
                }
                FirebaseFirestoreException exception = Util.exceptionFromStatus(status);
                if (exception.getCode() == FirebaseFirestoreException.Code.UNAUTHENTICATED) {
                    Datastore.this.channel.invalidateToken();
                }
                completionSource.trySetException(exception);
            }
        });
        return completionSource.getTask();
    }

    public Task<Map<String, Value>> runAggregateQuery(Query query, List<AggregateField> aggregateFields) {
        Target.QueryTarget encodedQueryTarget = this.serializer.encodeQueryTarget(query.toAggregateTarget());
        HashMap<String, String> aliasMap = new HashMap<>();
        StructuredAggregationQuery structuredAggregationQuery = this.serializer.encodeStructuredAggregationQuery(encodedQueryTarget, aggregateFields, aliasMap);
        RunAggregationQueryRequest.Builder request = RunAggregationQueryRequest.newBuilder();
        request.setParent(encodedQueryTarget.getParent());
        request.setStructuredAggregationQuery(structuredAggregationQuery);
        return this.channel.runRpc(FirestoreGrpc.getRunAggregationQueryMethod(), (RunAggregationQueryRequest) request.build()).continueWith(this.workerQueue.getExecutor(), new Datastore$$ExternalSyntheticLambda0(this, aliasMap));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$runAggregateQuery$1$com-google-firebase-firestore-remote-Datastore  reason: not valid java name */
    public /* synthetic */ Map m1910lambda$runAggregateQuery$1$comgooglefirebasefirestoreremoteDatastore(HashMap aliasMap, Task task) throws Exception {
        if (!task.isSuccessful()) {
            if ((task.getException() instanceof FirebaseFirestoreException) && ((FirebaseFirestoreException) task.getException()).getCode() == FirebaseFirestoreException.Code.UNAUTHENTICATED) {
                this.channel.invalidateToken();
            }
            throw task.getException();
        }
        Map<String, Value> result = new HashMap<>();
        for (Map.Entry<String, Value> entry : ((RunAggregationQueryResponse) task.getResult()).getResult().getAggregateFieldsMap().entrySet()) {
            Assert.hardAssert(aliasMap.containsKey(entry.getKey()), "%s not present in aliasMap", entry.getKey());
            result.put((String) aliasMap.get(entry.getKey()), entry.getValue());
        }
        return result;
    }

    public static boolean isPermanentError(Status status) {
        return isPermanentError(FirebaseFirestoreException.Code.fromValue(status.getCode().value()));
    }

    public static boolean isPermanentError(FirebaseFirestoreException.Code code) {
        switch (code) {
            case OK:
                throw new IllegalArgumentException("Treated status OK as error");
            case CANCELLED:
            case UNKNOWN:
            case DEADLINE_EXCEEDED:
            case RESOURCE_EXHAUSTED:
            case INTERNAL:
            case UNAVAILABLE:
            case UNAUTHENTICATED:
                return false;
            case INVALID_ARGUMENT:
            case NOT_FOUND:
            case ALREADY_EXISTS:
            case PERMISSION_DENIED:
            case FAILED_PRECONDITION:
            case ABORTED:
            case OUT_OF_RANGE:
            case UNIMPLEMENTED:
            case DATA_LOSS:
                return true;
            default:
                throw new IllegalArgumentException("Unknown gRPC status code: " + code);
        }
    }

    public static boolean isMissingSslCiphers(Status status) {
        Status.Code code = status.getCode();
        Throwable t = status.getCause();
        return (!(t instanceof SSLHandshakeException) || !t.getMessage().contains("no ciphers available")) ? false : false;
    }

    public static boolean isPermanentWriteError(Status status) {
        return isPermanentError(status) && !status.getCode().equals(Status.Code.ABORTED);
    }
}
