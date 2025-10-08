package com.google.firebase.firestore.local;

import com.google.protobuf.ByteString;

interface GlobalsCache {
    ByteString getSessionsToken();

    void setSessionToken(ByteString byteString);
}
