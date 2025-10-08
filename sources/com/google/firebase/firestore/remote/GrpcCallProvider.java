package com.google.firebase.firestore.remote;

import android.content.Context;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.core.DatabaseInfo;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.Executors;
import com.google.firebase.firestore.util.Logger;
import com.google.firebase.firestore.util.Supplier;
import com.google.firestore.v1.FirestoreGrpc;
import io.grpc.CallCredentials;
import io.grpc.CallOptions;
import io.grpc.ClientCall;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.MethodDescriptor;
import io.grpc.android.AndroidChannelBuilder;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GrpcCallProvider {
    private static final int CONNECTIVITY_ATTEMPT_TIMEOUT_MS = 15000;
    private static final String LOG_TAG = "GrpcCallProvider";
    private static Supplier<ManagedChannelBuilder<?>> overrideChannelBuilderSupplier;
    private final AsyncQueue asyncQueue;
    private CallOptions callOptions;
    private Task<ManagedChannel> channelTask;
    private AsyncQueue.DelayedTask connectivityAttemptTimer;
    private final Context context;
    private final DatabaseInfo databaseInfo;
    private final CallCredentials firestoreHeaders;

    GrpcCallProvider(AsyncQueue asyncQueue2, Context context2, DatabaseInfo databaseInfo2, CallCredentials firestoreHeaders2) {
        this.asyncQueue = asyncQueue2;
        this.context = context2;
        this.databaseInfo = databaseInfo2;
        this.firestoreHeaders = firestoreHeaders2;
        initChannelTask();
    }

    private ManagedChannel initChannel(Context context2, DatabaseInfo databaseInfo2) {
        ManagedChannelBuilder<?> channelBuilder;
        try {
            ProviderInstaller.installIfNeeded(context2);
        } catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException | IllegalStateException e) {
            Logger.warn(LOG_TAG, "Failed to update ssl context: %s", e);
        }
        if (overrideChannelBuilderSupplier != null) {
            channelBuilder = overrideChannelBuilderSupplier.get();
        } else {
            channelBuilder = ManagedChannelBuilder.forTarget(databaseInfo2.getHost());
            if (!databaseInfo2.isSslEnabled()) {
                channelBuilder.usePlaintext();
            }
        }
        channelBuilder.keepAliveTime(30, TimeUnit.SECONDS);
        return AndroidChannelBuilder.usingBuilder(channelBuilder).context(context2).build();
    }

    public <ReqT, RespT> Task<ClientCall<ReqT, RespT>> createClientCall(MethodDescriptor<ReqT, RespT> methodDescriptor) {
        return this.channelTask.continueWithTask(this.asyncQueue.getExecutor(), new GrpcCallProvider$$ExternalSyntheticLambda3(this, methodDescriptor));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$createClientCall$0$com-google-firebase-firestore-remote-GrpcCallProvider  reason: not valid java name */
    public /* synthetic */ Task m1914lambda$createClientCall$0$comgooglefirebasefirestoreremoteGrpcCallProvider(MethodDescriptor methodDescriptor, Task task) throws Exception {
        return Tasks.forResult(((ManagedChannel) task.getResult()).newCall(methodDescriptor, this.callOptions));
    }

    public void shutdown() {
        try {
            ManagedChannel channel = (ManagedChannel) Tasks.await(this.channelTask);
            channel.shutdown();
            try {
                if (!channel.awaitTermination(1, TimeUnit.SECONDS)) {
                    Logger.debug(FirestoreChannel.class.getSimpleName(), "Unable to gracefully shutdown the gRPC ManagedChannel. Will attempt an immediate shutdown.", new Object[0]);
                    channel.shutdownNow();
                    if (!channel.awaitTermination(60, TimeUnit.SECONDS)) {
                        Logger.warn(FirestoreChannel.class.getSimpleName(), "Unable to forcefully shutdown the gRPC ManagedChannel.", new Object[0]);
                    }
                }
            } catch (InterruptedException e) {
                channel.shutdownNow();
                Logger.warn(FirestoreChannel.class.getSimpleName(), "Interrupted while shutting down the gRPC Managed Channel", new Object[0]);
                Thread.currentThread().interrupt();
            }
        } catch (ExecutionException e2) {
            Logger.warn(FirestoreChannel.class.getSimpleName(), "Channel is not initialized, shutdown will just do nothing. Channel initializing run into exception: %s", e2);
        } catch (InterruptedException e3) {
            Logger.warn(FirestoreChannel.class.getSimpleName(), "Interrupted while retrieving the gRPC Managed Channel", new Object[0]);
            Thread.currentThread().interrupt();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: onConnectivityStateChange */
    public void m1918lambda$onConnectivityStateChange$2$comgooglefirebasefirestoreremoteGrpcCallProvider(ManagedChannel channel) {
        ConnectivityState newState = channel.getState(true);
        Logger.debug(LOG_TAG, "Current gRPC connectivity state: " + newState, new Object[0]);
        clearConnectivityAttemptTimer();
        if (newState == ConnectivityState.CONNECTING) {
            Logger.debug(LOG_TAG, "Setting the connectivityAttemptTimer", new Object[0]);
            this.connectivityAttemptTimer = this.asyncQueue.enqueueAfterDelay(AsyncQueue.TimerId.CONNECTIVITY_ATTEMPT_TIMER, 15000, new GrpcCallProvider$$ExternalSyntheticLambda0(this, channel));
        }
        channel.notifyWhenStateChanged(newState, new GrpcCallProvider$$ExternalSyntheticLambda1(this, channel));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$onConnectivityStateChange$1$com-google-firebase-firestore-remote-GrpcCallProvider  reason: not valid java name */
    public /* synthetic */ void m1917lambda$onConnectivityStateChange$1$comgooglefirebasefirestoreremoteGrpcCallProvider(ManagedChannel channel) {
        Logger.debug(LOG_TAG, "connectivityAttemptTimer elapsed. Resetting the channel.", new Object[0]);
        clearConnectivityAttemptTimer();
        resetChannel(channel);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$onConnectivityStateChange$3$com-google-firebase-firestore-remote-GrpcCallProvider  reason: not valid java name */
    public /* synthetic */ void m1919lambda$onConnectivityStateChange$3$comgooglefirebasefirestoreremoteGrpcCallProvider(ManagedChannel channel) {
        this.asyncQueue.enqueueAndForget(new GrpcCallProvider$$ExternalSyntheticLambda2(this, channel));
    }

    private void resetChannel(ManagedChannel channel) {
        this.asyncQueue.enqueueAndForget(new GrpcCallProvider$$ExternalSyntheticLambda5(this, channel));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$resetChannel$4$com-google-firebase-firestore-remote-GrpcCallProvider  reason: not valid java name */
    public /* synthetic */ void m1920lambda$resetChannel$4$comgooglefirebasefirestoreremoteGrpcCallProvider(ManagedChannel channel) {
        channel.shutdownNow();
        initChannelTask();
    }

    private void initChannelTask() {
        this.channelTask = Tasks.call(Executors.BACKGROUND_EXECUTOR, new GrpcCallProvider$$ExternalSyntheticLambda4(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$initChannelTask$6$com-google-firebase-firestore-remote-GrpcCallProvider  reason: not valid java name */
    public /* synthetic */ ManagedChannel m1916lambda$initChannelTask$6$comgooglefirebasefirestoreremoteGrpcCallProvider() throws Exception {
        ManagedChannel channel = initChannel(this.context, this.databaseInfo);
        this.asyncQueue.enqueueAndForget(new GrpcCallProvider$$ExternalSyntheticLambda6(this, channel));
        this.callOptions = ((FirestoreGrpc.FirestoreStub) ((FirestoreGrpc.FirestoreStub) FirestoreGrpc.newStub(channel).withCallCredentials(this.firestoreHeaders)).withExecutor(this.asyncQueue.getExecutor())).getCallOptions();
        Logger.debug(LOG_TAG, "Channel successfully reset.", new Object[0]);
        return channel;
    }

    private void clearConnectivityAttemptTimer() {
        if (this.connectivityAttemptTimer != null) {
            Logger.debug(LOG_TAG, "Clearing the connectivityAttemptTimer", new Object[0]);
            this.connectivityAttemptTimer.cancel();
            this.connectivityAttemptTimer = null;
        }
    }
}
