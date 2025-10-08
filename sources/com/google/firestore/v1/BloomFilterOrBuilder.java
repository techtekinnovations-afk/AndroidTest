package com.google.firestore.v1;

import com.google.protobuf.MessageLiteOrBuilder;

public interface BloomFilterOrBuilder extends MessageLiteOrBuilder {
    BitSequence getBits();

    int getHashCount();

    boolean hasBits();
}
