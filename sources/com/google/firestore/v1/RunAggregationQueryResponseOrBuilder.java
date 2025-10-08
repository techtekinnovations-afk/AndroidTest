package com.google.firestore.v1;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Timestamp;

public interface RunAggregationQueryResponseOrBuilder extends MessageLiteOrBuilder {
    Timestamp getReadTime();

    AggregationResult getResult();

    ByteString getTransaction();

    boolean hasReadTime();

    boolean hasResult();
}
