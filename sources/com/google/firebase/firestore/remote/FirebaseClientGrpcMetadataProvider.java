package com.google.firebase.firestore.remote;

import com.google.firebase.FirebaseOptions;
import com.google.firebase.heartbeatinfo.HeartBeatInfo;
import com.google.firebase.inject.Provider;
import com.google.firebase.platforminfo.UserAgentPublisher;
import io.grpc.Metadata;

public class FirebaseClientGrpcMetadataProvider implements GrpcMetadataProvider {
    private static final Metadata.Key<String> GMP_APP_ID_HEADER = Metadata.Key.of("x-firebase-gmpid", Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> HEART_BEAT_HEADER = Metadata.Key.of("x-firebase-client-log-type", Metadata.ASCII_STRING_MARSHALLER);
    private static final String HEART_BEAT_TAG = "fire-fst";
    private static final Metadata.Key<String> USER_AGENT_HEADER = Metadata.Key.of("x-firebase-client", Metadata.ASCII_STRING_MARSHALLER);
    private final FirebaseOptions firebaseOptions;
    private final Provider<HeartBeatInfo> heartBeatInfoProvider;
    private final Provider<UserAgentPublisher> userAgentPublisherProvider;

    public FirebaseClientGrpcMetadataProvider(Provider<UserAgentPublisher> userAgentPublisherProvider2, Provider<HeartBeatInfo> heartBeatInfoProvider2, FirebaseOptions firebaseOptions2) {
        this.userAgentPublisherProvider = userAgentPublisherProvider2;
        this.heartBeatInfoProvider = heartBeatInfoProvider2;
        this.firebaseOptions = firebaseOptions2;
    }

    public void updateMetadata(Metadata metadata) {
        if (this.heartBeatInfoProvider.get() != null && this.userAgentPublisherProvider.get() != null) {
            int heartBeatCode = this.heartBeatInfoProvider.get().getHeartBeatCode(HEART_BEAT_TAG).getCode();
            if (heartBeatCode != 0) {
                metadata.put(HEART_BEAT_HEADER, Integer.toString(heartBeatCode));
            }
            metadata.put(USER_AGENT_HEADER, this.userAgentPublisherProvider.get().getUserAgent());
            maybeAddGmpAppId(metadata);
        }
    }

    private void maybeAddGmpAppId(Metadata metadata) {
        if (this.firebaseOptions != null) {
            String gmpAppId = this.firebaseOptions.getApplicationId();
            if (gmpAppId.length() != 0) {
                metadata.put(GMP_APP_ID_HEADER, gmpAppId);
            }
        }
    }
}
