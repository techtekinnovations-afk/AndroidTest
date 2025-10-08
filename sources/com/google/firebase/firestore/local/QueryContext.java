package com.google.firebase.firestore.local;

public class QueryContext {
    private int documentReadCount = 0;

    public int getDocumentReadCount() {
        return this.documentReadCount;
    }

    public void incrementDocumentReadCount() {
        this.documentReadCount++;
    }
}
