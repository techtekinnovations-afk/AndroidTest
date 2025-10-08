package com.google.firebase.firestore;

public final class PersistentCacheIndexManager {
    private FirestoreClientProvider client;

    PersistentCacheIndexManager(FirestoreClientProvider client2) {
        this.client = client2;
    }

    public void enableIndexAutoCreation() {
        this.client.procedure(new PersistentCacheIndexManager$$ExternalSyntheticLambda0());
    }

    public void disableIndexAutoCreation() {
        this.client.procedure(new PersistentCacheIndexManager$$ExternalSyntheticLambda1());
    }

    public void deleteAllIndexes() {
        this.client.procedure(new PersistentCacheIndexManager$$ExternalSyntheticLambda2());
    }
}
