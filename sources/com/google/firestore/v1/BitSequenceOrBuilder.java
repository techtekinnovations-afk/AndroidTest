package com.google.firestore.v1;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;

public interface BitSequenceOrBuilder extends MessageLiteOrBuilder {
    ByteString getBitmap();

    int getPadding();
}
