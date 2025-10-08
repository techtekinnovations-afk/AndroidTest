package com.google.firebase.firestore.remote;

import android.content.Context;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.BuildConfig;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.auth.CredentialsProvider;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.core.DatabaseInfo;
import com.google.firebase.firestore.model.DatabaseId;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.Util;
import io.grpc.ClientCall;
import io.grpc.ForwardingClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;

public class FirestoreChannel {
    private static final Metadata.Key<String> RESOURCE_PREFIX_HEADER = Metadata.Key.of("google-cloud-resource-prefix", Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> X_GOOG_API_CLIENT_HEADER = Metadata.Key.of("x-goog-api-client", Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> X_GOOG_REQUEST_PARAMS_HEADER = Metadata.Key.of("x-goog-request-params", Metadata.ASCII_STRING_MARSHALLER);
    private static volatile String clientLanguage = "gl-java/";
    private final CredentialsProvider<String> appCheckProvider;
    /* access modifiers changed from: private */
    public final AsyncQueue asyncQueue;
    private final CredentialsProvider<User> authProvider;
    private final GrpcCallProvider callProvider;
    private final GrpcMetadataProvider metadataProvider;
    private final String resourcePrefixValue;

    public static abstract class StreamingListener<T> {
        public void onMessage(T t) {
        }

        public void onClose(Status status) {
        }
    }

    FirestoreChannel(AsyncQueue asyncQueue2, Context context, CredentialsProvider<User> authProvider2, CredentialsProvider<String> appCheckProvider2, DatabaseInfo databaseInfo, GrpcMetadataProvider metadataProvider2) {
        this(asyncQueue2, authProvider2, appCheckProvider2, databaseInfo.getDatabaseId(), metadataProvider2, getGrpcCallProvider(asyncQueue2, context, authProvider2, appCheckProvider2, databaseInfo));
    }

    FirestoreChannel(AsyncQueue asyncQueue2, CredentialsProvider<User> authProvider2, CredentialsProvider<String> appCheckProvider2, DatabaseId databaseId, GrpcMetadataProvider metadataProvider2, GrpcCallProvider grpcCallProvider) {
        this.asyncQueue = asyncQueue2;
        this.metadataProvider = metadataProvider2;
        this.authProvider = authProvider2;
        this.appCheckProvider = appCheckProvider2;
        this.callProvider = grpcCallProvider;
        this.resourcePrefixValue = String.format("projects/%s/databases/%s", new Object[]{databaseId.getProjectId(), databaseId.getDatabaseId()});
    }

    private static GrpcCallProvider getGrpcCallProvider(AsyncQueue asyncQueue2, Context context, CredentialsProvider<User> authProvider2, CredentialsProvider<String> appCheckProvider2, DatabaseInfo databaseInfo) {
        return new GrpcCallProvider(asyncQueue2, context, databaseInfo, new FirestoreCallCredentials(authProvider2, appCheckProvider2));
    }

    public void shutdown() {
        this.callProvider.shutdown();
    }

    /* access modifiers changed from: package-private */
    public <ReqT, RespT> ClientCall<ReqT, RespT> runBidiStreamingRpc(MethodDescriptor<ReqT, RespT> method, IncomingStreamObserver<RespT> observer) {
        final ClientCall<ReqT, RespT>[] call = {null};
        final Task<ClientCall<ReqT, RespT>> clientCall = this.callProvider.createClientCall(method);
        clientCall.addOnCompleteListener(this.asyncQueue.getExecutor(), (OnCompleteListener<ClientCall<ReqT, RespT>>) new FirestoreChannel$$ExternalSyntheticLambda0(this, call, observer));
        return new ForwardingClientCall<ReqT, RespT>() {
            /* access modifiers changed from: protected */
            public ClientCall<ReqT, RespT> delegate() {
                Assert.hardAssert(call[0] != null, "ClientCall used before onOpen() callback", new Object[0]);
                return call[0];
            }

            public void halfClose() {
                if (call[0] == null) {
                    clientCall.addOnSuccessListener(FirestoreChannel.this.asyncQueue.getExecutor(), new FirestoreChannel$2$$ExternalSyntheticLambda0());
                } else {
                    super.halfClose();
                }
            }
        };
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$runBidiStreamingRpc$0$com-google-firebase-firestore-remote-FirestoreChannel  reason: not valid java name */
    public /* synthetic */ void m1911lambda$runBidiStreamingRpc$0$comgooglefirebasefirestoreremoteFirestoreChannel(final ClientCall[] call, final IncomingStreamObserver observer, Task result) {
        call[0] = (ClientCall) result.getResult();
        call[0].start(new ClientCall.Listener<RespT>() {
            public void onHeaders(Metadata headers) {
                try {
                    observer.onHeaders(headers);
                } catch (Throwable t) {
                    FirestoreChannel.this.asyncQueue.panic(t);
                }
            }

            public void onMessage(RespT message) {
                try {
                    observer.onNext(message);
                    call[0].request(1);
                } catch (Throwable t) {
                    FirestoreChannel.this.asyncQueue.panic(t);
                }
            }

            public void onClose(Status status, Metadata trailers) {
                try {
                    observer.onClose(status);
                } catch (Throwable t) {
                    FirestoreChannel.this.asyncQueue.panic(t);
                }
            }

            public void onReady() {
            }
        }, requestHeaders());
        observer.onOpen();
        call[0].request(1);
    }

    /* access modifiers changed from: package-private */
    public <ReqT, RespT> void runStreamingResponseRpc(MethodDescriptor<ReqT, RespT> method, ReqT request, StreamingListener<RespT> callback) {
        this.callProvider.createClientCall(method).addOnCompleteListener(this.asyncQueue.getExecutor(), new FirestoreChannel$$ExternalSyntheticLambda2(this, callback, request));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$runStreamingResponseRpc$1$com-google-firebase-firestore-remote-FirestoreChannel  reason: not valid java name */
    public /* synthetic */ void m1913lambda$runStreamingResponseRpc$1$comgooglefirebasefirestoreremoteFirestoreChannel(final StreamingListener callback, Object request, Task result) {
        final ClientCall<ReqT, RespT> call = (ClientCall) result.getResult();
        call.start(new ClientCall.Listener<RespT>() {
            public void onMessage(RespT message) {
                callback.onMessage(message);
                call.request(1);
            }

            public void onClose(Status status, Metadata trailers) {
                callback.onClose(status);
            }
        }, requestHeaders());
        call.request(1);
        call.sendMessage(request);
        call.halfClose();
    }

    /* access modifiers changed from: package-private */
    public <ReqT, RespT> Task<RespT> runRpc(MethodDescriptor<ReqT, RespT> method, ReqT request) {
        TaskCompletionSource<RespT> tcs = new TaskCompletionSource<>();
        this.callProvider.createClientCall(method).addOnCompleteListener(this.asyncQueue.getExecutor(), new FirestoreChannel$$ExternalSyntheticLambda1(this, tcs, request));
        return tcs.getTask();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$runRpc$2$com-google-firebase-firestore-remote-FirestoreChannel  reason: not valid java name */
    public /* synthetic */ void m1912lambda$runRpc$2$comgooglefirebasefirestoreremoteFirestoreChannel(final TaskCompletionSource tcs, Object request, Task result) {
        ClientCall<ReqT, RespT> call = (ClientCall) result.getResult();
        call.start(new ClientCall.Listener<RespT>() {
            public void onMessage(RespT message) {
                tcs.setResult(message);
            }

            public void onClose(Status status, Metadata trailers) {
                if (!status.isOk()) {
                    tcs.setException(FirestoreChannel.this.exceptionFromStatus(status));
                } else if (!tcs.getTask().isComplete()) {
                    tcs.setException(new FirebaseFirestoreException("Received onClose with status OK, but no message.", FirebaseFirestoreException.Code.INTERNAL));
                }
            }
        }, requestHeaders());
        call.request(2);
        call.sendMessage(request);
        call.halfClose();
    }

    /* access modifiers changed from: private */
    public FirebaseFirestoreException exceptionFromStatus(Status status) {
        if (Datastore.isMissingSslCiphers(status)) {
            return new FirebaseFirestoreException("The Cloud Firestore client failed to establish a secure connection. This is likely a problem with your app, rather than with Cloud Firestore itself. See https://bit.ly/2XFpdma for instructions on how to enable TLS on Android 4.x devices.", FirebaseFirestoreException.Code.fromValue(status.getCode().value()), status.getCause());
        }
        return Util.exceptionFromStatus(status);
    }

    public void invalidateToken() {
        this.authProvider.invalidateToken();
        this.appCheckProvider.invalidateToken();
    }

    public static void setClientLanguage(String languageToken) {
        clientLanguage = languageToken;
    }

    private String getGoogApiClientValue() {
        return String.format("%s fire/%s grpc/", new Object[]{clientLanguage, BuildConfig.VERSION_NAME});
    }

    private Metadata requestHeaders() {
        Metadata headers = new Metadata();
        headers.put(X_GOOG_API_CLIENT_HEADER, getGoogApiClientValue());
        headers.put(RESOURCE_PREFIX_HEADER, this.resourcePrefixValue);
        headers.put(X_GOOG_REQUEST_PARAMS_HEADER, this.resourcePrefixValue);
        if (this.metadataProvider != null) {
            this.metadataProvider.updateMetadata(headers);
        }
        return headers;
    }
}
