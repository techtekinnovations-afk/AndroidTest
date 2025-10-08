package com.google.protobuf;

import java.io.IOException;

@CheckReturnValue
abstract class UnknownFieldSchema<T, B> {
    static final int DEFAULT_RECURSION_LIMIT = 100;
    private static volatile int recursionLimit = 100;

    /* access modifiers changed from: package-private */
    public abstract void addFixed32(B b, int i, int i2);

    /* access modifiers changed from: package-private */
    public abstract void addFixed64(B b, int i, long j);

    /* access modifiers changed from: package-private */
    public abstract void addGroup(B b, int i, T t);

    /* access modifiers changed from: package-private */
    public abstract void addLengthDelimited(B b, int i, ByteString byteString);

    /* access modifiers changed from: package-private */
    public abstract void addVarint(B b, int i, long j);

    /* access modifiers changed from: package-private */
    public abstract B getBuilderFromMessage(Object obj);

    /* access modifiers changed from: package-private */
    public abstract T getFromMessage(Object obj);

    /* access modifiers changed from: package-private */
    public abstract int getSerializedSize(T t);

    /* access modifiers changed from: package-private */
    public abstract int getSerializedSizeAsMessageSet(T t);

    /* access modifiers changed from: package-private */
    public abstract void makeImmutable(Object obj);

    /* access modifiers changed from: package-private */
    public abstract T merge(T t, T t2);

    /* access modifiers changed from: package-private */
    public abstract B newBuilder();

    /* access modifiers changed from: package-private */
    public abstract void setBuilderToMessage(Object obj, B b);

    /* access modifiers changed from: package-private */
    public abstract void setToMessage(Object obj, T t);

    /* access modifiers changed from: package-private */
    public abstract boolean shouldDiscardUnknownFields(Reader reader);

    /* access modifiers changed from: package-private */
    public abstract T toImmutable(B b);

    /* access modifiers changed from: package-private */
    public abstract void writeAsMessageSetTo(T t, Writer writer) throws IOException;

    /* access modifiers changed from: package-private */
    public abstract void writeTo(T t, Writer writer) throws IOException;

    UnknownFieldSchema() {
    }

    /* access modifiers changed from: package-private */
    public final boolean mergeOneFieldFrom(B unknownFields, Reader reader, int currentDepth) throws IOException {
        int tag = reader.getTag();
        int fieldNumber = WireFormat.getTagFieldNumber(tag);
        switch (WireFormat.getTagWireType(tag)) {
            case 0:
                addVarint(unknownFields, fieldNumber, reader.readInt64());
                return true;
            case 1:
                addFixed64(unknownFields, fieldNumber, reader.readFixed64());
                return true;
            case 2:
                addLengthDelimited(unknownFields, fieldNumber, reader.readBytes());
                return true;
            case 3:
                B subFields = newBuilder();
                int endGroupTag = WireFormat.makeTag(fieldNumber, 4);
                int currentDepth2 = currentDepth + 1;
                if (currentDepth2 < recursionLimit) {
                    mergeFrom(subFields, reader, currentDepth2);
                    int currentDepth3 = currentDepth2 - 1;
                    if (endGroupTag == reader.getTag()) {
                        addGroup(unknownFields, fieldNumber, toImmutable(subFields));
                        return true;
                    }
                    throw InvalidProtocolBufferException.invalidEndTag();
                }
                throw InvalidProtocolBufferException.recursionLimitExceeded();
            case 4:
                return false;
            case 5:
                addFixed32(unknownFields, fieldNumber, reader.readFixed32());
                return true;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:1:0x0001 A[LOOP:0: B:1:0x0001->B:4:0x000e, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void mergeFrom(B r3, com.google.protobuf.Reader r4, int r5) throws java.io.IOException {
        /*
            r2 = this;
        L_0x0001:
            int r0 = r4.getFieldNumber()
            r1 = 2147483647(0x7fffffff, float:NaN)
            if (r0 == r1) goto L_0x0011
            boolean r0 = r2.mergeOneFieldFrom(r3, r4, r5)
            if (r0 != 0) goto L_0x0001
        L_0x0011:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.UnknownFieldSchema.mergeFrom(java.lang.Object, com.google.protobuf.Reader, int):void");
    }

    public void setRecursionLimit(int limit) {
        recursionLimit = limit;
    }
}
