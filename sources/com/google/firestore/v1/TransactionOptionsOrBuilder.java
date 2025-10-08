package com.google.firestore.v1;

import com.google.firestore.v1.TransactionOptions;
import com.google.protobuf.MessageLiteOrBuilder;

public interface TransactionOptionsOrBuilder extends MessageLiteOrBuilder {
    TransactionOptions.ModeCase getModeCase();

    TransactionOptions.ReadOnly getReadOnly();

    TransactionOptions.ReadWrite getReadWrite();

    boolean hasReadOnly();

    boolean hasReadWrite();
}
