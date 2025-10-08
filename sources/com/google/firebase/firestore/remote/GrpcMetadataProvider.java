package com.google.firebase.firestore.remote;

import io.grpc.Metadata;

public interface GrpcMetadataProvider {
    void updateMetadata(Metadata metadata);
}
