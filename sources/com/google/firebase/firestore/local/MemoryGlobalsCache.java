package com.google.firebase.firestore.local;

import com.google.protobuf.ByteString;

final class MemoryGlobalsCache implements GlobalsCache {
    private ByteString sessionToken = ByteString.EMPTY;

    MemoryGlobalsCache() {
    }

    public ByteString getSessionsToken() {
        return this.sessionToken;
    }

    public void setSessionToken(ByteString value) {
        this.sessionToken = value;
    }
}
