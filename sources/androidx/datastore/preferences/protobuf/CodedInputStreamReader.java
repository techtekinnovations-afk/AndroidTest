package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException;
import androidx.datastore.preferences.protobuf.MapEntryLite;
import androidx.datastore.preferences.protobuf.WireFormat;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@CheckReturnValue
final class CodedInputStreamReader implements Reader {
    private static final int FIXED32_MULTIPLE_MASK = 3;
    private static final int FIXED64_MULTIPLE_MASK = 7;
    private static final int NEXT_TAG_UNSET = 0;
    private int endGroupTag;
    private final CodedInputStream input;
    private int nextTag = 0;
    private int tag;

    public static CodedInputStreamReader forCodedInput(CodedInputStream input2) {
        if (input2.wrapper != null) {
            return input2.wrapper;
        }
        return new CodedInputStreamReader(input2);
    }

    private CodedInputStreamReader(CodedInputStream input2) {
        this.input = (CodedInputStream) Internal.checkNotNull(input2, "input");
        this.input.wrapper = this;
    }

    public boolean shouldDiscardUnknownFields() {
        return this.input.shouldDiscardUnknownFields();
    }

    public int getFieldNumber() throws IOException {
        if (this.nextTag != 0) {
            this.tag = this.nextTag;
            this.nextTag = 0;
        } else {
            this.tag = this.input.readTag();
        }
        if (this.tag == 0 || this.tag == this.endGroupTag) {
            return Integer.MAX_VALUE;
        }
        return WireFormat.getTagFieldNumber(this.tag);
    }

    public int getTag() {
        return this.tag;
    }

    public boolean skipField() throws IOException {
        if (this.input.isAtEnd() || this.tag == this.endGroupTag) {
            return false;
        }
        return this.input.skipField(this.tag);
    }

    private void requireWireType(int requiredWireType) throws IOException {
        if (WireFormat.getTagWireType(this.tag) != requiredWireType) {
            throw InvalidProtocolBufferException.invalidWireType();
        }
    }

    public double readDouble() throws IOException {
        requireWireType(1);
        return this.input.readDouble();
    }

    public float readFloat() throws IOException {
        requireWireType(5);
        return this.input.readFloat();
    }

    public long readUInt64() throws IOException {
        requireWireType(0);
        return this.input.readUInt64();
    }

    public long readInt64() throws IOException {
        requireWireType(0);
        return this.input.readInt64();
    }

    public int readInt32() throws IOException {
        requireWireType(0);
        return this.input.readInt32();
    }

    public long readFixed64() throws IOException {
        requireWireType(1);
        return this.input.readFixed64();
    }

    public int readFixed32() throws IOException {
        requireWireType(5);
        return this.input.readFixed32();
    }

    public boolean readBool() throws IOException {
        requireWireType(0);
        return this.input.readBool();
    }

    public String readString() throws IOException {
        requireWireType(2);
        return this.input.readString();
    }

    public String readStringRequireUtf8() throws IOException {
        requireWireType(2);
        return this.input.readStringRequireUtf8();
    }

    public <T> T readMessage(Class<T> clazz, ExtensionRegistryLite extensionRegistry) throws IOException {
        requireWireType(2);
        return readMessage(Protobuf.getInstance().schemaFor(clazz), extensionRegistry);
    }

    public <T> T readMessageBySchemaWithCheck(Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
        requireWireType(2);
        return readMessage(schema, extensionRegistry);
    }

    @Deprecated
    public <T> T readGroup(Class<T> clazz, ExtensionRegistryLite extensionRegistry) throws IOException {
        requireWireType(3);
        return readGroup(Protobuf.getInstance().schemaFor(clazz), extensionRegistry);
    }

    @Deprecated
    public <T> T readGroupBySchemaWithCheck(Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
        requireWireType(3);
        return readGroup(schema, extensionRegistry);
    }

    public <T> void mergeMessageField(T target, Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
        requireWireType(2);
        mergeMessageFieldInternal(target, schema, extensionRegistry);
    }

    private <T> void mergeMessageFieldInternal(T target, Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
        int size = this.input.readUInt32();
        if (this.input.recursionDepth < this.input.recursionLimit) {
            int prevLimit = this.input.pushLimit(size);
            this.input.recursionDepth++;
            schema.mergeFrom(target, this, extensionRegistry);
            this.input.checkLastTagWas(0);
            CodedInputStream codedInputStream = this.input;
            codedInputStream.recursionDepth--;
            this.input.popLimit(prevLimit);
            return;
        }
        throw InvalidProtocolBufferException.recursionLimitExceeded();
    }

    private <T> T readMessage(Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
        T newInstance = schema.newInstance();
        mergeMessageFieldInternal(newInstance, schema, extensionRegistry);
        schema.makeImmutable(newInstance);
        return newInstance;
    }

    public <T> void mergeGroupField(T target, Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
        requireWireType(3);
        mergeGroupFieldInternal(target, schema, extensionRegistry);
    }

    private <T> void mergeGroupFieldInternal(T target, Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
        int prevEndGroupTag = this.endGroupTag;
        this.endGroupTag = WireFormat.makeTag(WireFormat.getTagFieldNumber(this.tag), 4);
        try {
            schema.mergeFrom(target, this, extensionRegistry);
            if (this.tag != this.endGroupTag) {
                throw InvalidProtocolBufferException.parseFailure();
            }
        } finally {
            this.endGroupTag = prevEndGroupTag;
        }
    }

    private <T> T readGroup(Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
        T newInstance = schema.newInstance();
        mergeGroupFieldInternal(newInstance, schema, extensionRegistry);
        schema.makeImmutable(newInstance);
        return newInstance;
    }

    public ByteString readBytes() throws IOException {
        requireWireType(2);
        return this.input.readBytes();
    }

    public int readUInt32() throws IOException {
        requireWireType(0);
        return this.input.readUInt32();
    }

    public int readEnum() throws IOException {
        requireWireType(0);
        return this.input.readEnum();
    }

    public int readSFixed32() throws IOException {
        requireWireType(5);
        return this.input.readSFixed32();
    }

    public long readSFixed64() throws IOException {
        requireWireType(1);
        return this.input.readSFixed64();
    }

    public int readSInt32() throws IOException {
        requireWireType(0);
        return this.input.readSInt32();
    }

    public long readSInt64() throws IOException {
        requireWireType(0);
        return this.input.readSInt64();
    }

    public void readDoubleList(List<Double> target) throws IOException {
        int nextTag2;
        int nextTag3;
        if (target instanceof DoubleArrayList) {
            DoubleArrayList plist = (DoubleArrayList) target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 1:
                    break;
                case 2:
                    int bytes = this.input.readUInt32();
                    verifyPackedFixed64Length(bytes);
                    int endPos = this.input.getTotalBytesRead() + bytes;
                    do {
                        plist.addDouble(this.input.readDouble());
                    } while (this.input.getTotalBytesRead() < endPos);
                    return;
                default:
                    throw InvalidProtocolBufferException.invalidWireType();
            }
            do {
                plist.addDouble(this.input.readDouble());
                if (!this.input.isAtEnd()) {
                    nextTag3 = this.input.readTag();
                } else {
                    return;
                }
            } while (nextTag3 == this.tag);
            this.nextTag = nextTag3;
            return;
        }
        switch (WireFormat.getTagWireType(this.tag)) {
            case 1:
                break;
            case 2:
                int bytes2 = this.input.readUInt32();
                verifyPackedFixed64Length(bytes2);
                int endPos2 = this.input.getTotalBytesRead() + bytes2;
                do {
                    target.add(Double.valueOf(this.input.readDouble()));
                } while (this.input.getTotalBytesRead() < endPos2);
                return;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            target.add(Double.valueOf(this.input.readDouble()));
            if (!this.input.isAtEnd()) {
                nextTag2 = this.input.readTag();
            } else {
                return;
            }
        } while (nextTag2 == this.tag);
        this.nextTag = nextTag2;
    }

    public void readFloatList(List<Float> target) throws IOException {
        int nextTag2;
        int nextTag3;
        if (target instanceof FloatArrayList) {
            FloatArrayList plist = (FloatArrayList) target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2:
                    int bytes = this.input.readUInt32();
                    verifyPackedFixed32Length(bytes);
                    int endPos = this.input.getTotalBytesRead() + bytes;
                    do {
                        plist.addFloat(this.input.readFloat());
                    } while (this.input.getTotalBytesRead() < endPos);
                    return;
                case 5:
                    break;
                default:
                    throw InvalidProtocolBufferException.invalidWireType();
            }
            do {
                plist.addFloat(this.input.readFloat());
                if (!this.input.isAtEnd()) {
                    nextTag3 = this.input.readTag();
                } else {
                    return;
                }
            } while (nextTag3 == this.tag);
            this.nextTag = nextTag3;
            return;
        }
        switch (WireFormat.getTagWireType(this.tag)) {
            case 2:
                int bytes2 = this.input.readUInt32();
                verifyPackedFixed32Length(bytes2);
                int endPos2 = this.input.getTotalBytesRead() + bytes2;
                do {
                    target.add(Float.valueOf(this.input.readFloat()));
                } while (this.input.getTotalBytesRead() < endPos2);
                return;
            case 5:
                break;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            target.add(Float.valueOf(this.input.readFloat()));
            if (!this.input.isAtEnd()) {
                nextTag2 = this.input.readTag();
            } else {
                return;
            }
        } while (nextTag2 == this.tag);
        this.nextTag = nextTag2;
    }

    public void readUInt64List(List<Long> target) throws IOException {
        int nextTag2;
        int nextTag3;
        if (target instanceof LongArrayList) {
            LongArrayList plist = (LongArrayList) target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 2:
                    int endPos = this.input.getTotalBytesRead() + this.input.readUInt32();
                    do {
                        plist.addLong(this.input.readUInt64());
                    } while (this.input.getTotalBytesRead() < endPos);
                    requirePosition(endPos);
                    return;
                default:
                    throw InvalidProtocolBufferException.invalidWireType();
            }
            do {
                plist.addLong(this.input.readUInt64());
                if (!this.input.isAtEnd()) {
                    nextTag3 = this.input.readTag();
                } else {
                    return;
                }
            } while (nextTag3 == this.tag);
            this.nextTag = nextTag3;
            return;
        }
        switch (WireFormat.getTagWireType(this.tag)) {
            case 0:
                break;
            case 2:
                int endPos2 = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    target.add(Long.valueOf(this.input.readUInt64()));
                } while (this.input.getTotalBytesRead() < endPos2);
                requirePosition(endPos2);
                return;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            target.add(Long.valueOf(this.input.readUInt64()));
            if (!this.input.isAtEnd()) {
                nextTag2 = this.input.readTag();
            } else {
                return;
            }
        } while (nextTag2 == this.tag);
        this.nextTag = nextTag2;
    }

    public void readInt64List(List<Long> target) throws IOException {
        int nextTag2;
        int nextTag3;
        if (target instanceof LongArrayList) {
            LongArrayList plist = (LongArrayList) target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 2:
                    int endPos = this.input.getTotalBytesRead() + this.input.readUInt32();
                    do {
                        plist.addLong(this.input.readInt64());
                    } while (this.input.getTotalBytesRead() < endPos);
                    requirePosition(endPos);
                    return;
                default:
                    throw InvalidProtocolBufferException.invalidWireType();
            }
            do {
                plist.addLong(this.input.readInt64());
                if (!this.input.isAtEnd()) {
                    nextTag3 = this.input.readTag();
                } else {
                    return;
                }
            } while (nextTag3 == this.tag);
            this.nextTag = nextTag3;
            return;
        }
        switch (WireFormat.getTagWireType(this.tag)) {
            case 0:
                break;
            case 2:
                int endPos2 = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    target.add(Long.valueOf(this.input.readInt64()));
                } while (this.input.getTotalBytesRead() < endPos2);
                requirePosition(endPos2);
                return;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            target.add(Long.valueOf(this.input.readInt64()));
            if (!this.input.isAtEnd()) {
                nextTag2 = this.input.readTag();
            } else {
                return;
            }
        } while (nextTag2 == this.tag);
        this.nextTag = nextTag2;
    }

    public void readInt32List(List<Integer> target) throws IOException {
        int nextTag2;
        int nextTag3;
        if (target instanceof IntArrayList) {
            IntArrayList plist = (IntArrayList) target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 2:
                    int endPos = this.input.getTotalBytesRead() + this.input.readUInt32();
                    do {
                        plist.addInt(this.input.readInt32());
                    } while (this.input.getTotalBytesRead() < endPos);
                    requirePosition(endPos);
                    return;
                default:
                    throw InvalidProtocolBufferException.invalidWireType();
            }
            do {
                plist.addInt(this.input.readInt32());
                if (!this.input.isAtEnd()) {
                    nextTag3 = this.input.readTag();
                } else {
                    return;
                }
            } while (nextTag3 == this.tag);
            this.nextTag = nextTag3;
            return;
        }
        switch (WireFormat.getTagWireType(this.tag)) {
            case 0:
                break;
            case 2:
                int endPos2 = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    target.add(Integer.valueOf(this.input.readInt32()));
                } while (this.input.getTotalBytesRead() < endPos2);
                requirePosition(endPos2);
                return;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            target.add(Integer.valueOf(this.input.readInt32()));
            if (!this.input.isAtEnd()) {
                nextTag2 = this.input.readTag();
            } else {
                return;
            }
        } while (nextTag2 == this.tag);
        this.nextTag = nextTag2;
    }

    public void readFixed64List(List<Long> target) throws IOException {
        int nextTag2;
        int nextTag3;
        if (target instanceof LongArrayList) {
            LongArrayList plist = (LongArrayList) target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 1:
                    break;
                case 2:
                    int bytes = this.input.readUInt32();
                    verifyPackedFixed64Length(bytes);
                    int endPos = this.input.getTotalBytesRead() + bytes;
                    do {
                        plist.addLong(this.input.readFixed64());
                    } while (this.input.getTotalBytesRead() < endPos);
                    return;
                default:
                    throw InvalidProtocolBufferException.invalidWireType();
            }
            do {
                plist.addLong(this.input.readFixed64());
                if (!this.input.isAtEnd()) {
                    nextTag3 = this.input.readTag();
                } else {
                    return;
                }
            } while (nextTag3 == this.tag);
            this.nextTag = nextTag3;
            return;
        }
        switch (WireFormat.getTagWireType(this.tag)) {
            case 1:
                break;
            case 2:
                int bytes2 = this.input.readUInt32();
                verifyPackedFixed64Length(bytes2);
                int endPos2 = this.input.getTotalBytesRead() + bytes2;
                do {
                    target.add(Long.valueOf(this.input.readFixed64()));
                } while (this.input.getTotalBytesRead() < endPos2);
                return;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            target.add(Long.valueOf(this.input.readFixed64()));
            if (!this.input.isAtEnd()) {
                nextTag2 = this.input.readTag();
            } else {
                return;
            }
        } while (nextTag2 == this.tag);
        this.nextTag = nextTag2;
    }

    public void readFixed32List(List<Integer> target) throws IOException {
        int nextTag2;
        int nextTag3;
        if (target instanceof IntArrayList) {
            IntArrayList plist = (IntArrayList) target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2:
                    int bytes = this.input.readUInt32();
                    verifyPackedFixed32Length(bytes);
                    int endPos = this.input.getTotalBytesRead() + bytes;
                    do {
                        plist.addInt(this.input.readFixed32());
                    } while (this.input.getTotalBytesRead() < endPos);
                    return;
                case 5:
                    break;
                default:
                    throw InvalidProtocolBufferException.invalidWireType();
            }
            do {
                plist.addInt(this.input.readFixed32());
                if (!this.input.isAtEnd()) {
                    nextTag3 = this.input.readTag();
                } else {
                    return;
                }
            } while (nextTag3 == this.tag);
            this.nextTag = nextTag3;
            return;
        }
        switch (WireFormat.getTagWireType(this.tag)) {
            case 2:
                int bytes2 = this.input.readUInt32();
                verifyPackedFixed32Length(bytes2);
                int endPos2 = this.input.getTotalBytesRead() + bytes2;
                do {
                    target.add(Integer.valueOf(this.input.readFixed32()));
                } while (this.input.getTotalBytesRead() < endPos2);
                return;
            case 5:
                break;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            target.add(Integer.valueOf(this.input.readFixed32()));
            if (!this.input.isAtEnd()) {
                nextTag2 = this.input.readTag();
            } else {
                return;
            }
        } while (nextTag2 == this.tag);
        this.nextTag = nextTag2;
    }

    public void readBoolList(List<Boolean> target) throws IOException {
        int nextTag2;
        int nextTag3;
        if (target instanceof BooleanArrayList) {
            BooleanArrayList plist = (BooleanArrayList) target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 2:
                    int endPos = this.input.getTotalBytesRead() + this.input.readUInt32();
                    do {
                        plist.addBoolean(this.input.readBool());
                    } while (this.input.getTotalBytesRead() < endPos);
                    requirePosition(endPos);
                    return;
                default:
                    throw InvalidProtocolBufferException.invalidWireType();
            }
            do {
                plist.addBoolean(this.input.readBool());
                if (!this.input.isAtEnd()) {
                    nextTag3 = this.input.readTag();
                } else {
                    return;
                }
            } while (nextTag3 == this.tag);
            this.nextTag = nextTag3;
            return;
        }
        switch (WireFormat.getTagWireType(this.tag)) {
            case 0:
                break;
            case 2:
                int endPos2 = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    target.add(Boolean.valueOf(this.input.readBool()));
                } while (this.input.getTotalBytesRead() < endPos2);
                requirePosition(endPos2);
                return;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            target.add(Boolean.valueOf(this.input.readBool()));
            if (!this.input.isAtEnd()) {
                nextTag2 = this.input.readTag();
            } else {
                return;
            }
        } while (nextTag2 == this.tag);
        this.nextTag = nextTag2;
    }

    public void readStringList(List<String> target) throws IOException {
        readStringListInternal(target, false);
    }

    public void readStringListRequireUtf8(List<String> target) throws IOException {
        readStringListInternal(target, true);
    }

    public void readStringListInternal(List<String> target, boolean requireUtf8) throws IOException {
        int nextTag2;
        int nextTag3;
        if (WireFormat.getTagWireType(this.tag) != 2) {
            throw InvalidProtocolBufferException.invalidWireType();
        } else if (!(target instanceof LazyStringList) || requireUtf8) {
            do {
                target.add(requireUtf8 ? readStringRequireUtf8() : readString());
                if (!this.input.isAtEnd()) {
                    nextTag2 = this.input.readTag();
                } else {
                    return;
                }
            } while (nextTag2 == this.tag);
            this.nextTag = nextTag2;
        } else {
            LazyStringList lazyList = (LazyStringList) target;
            do {
                lazyList.add(readBytes());
                if (!this.input.isAtEnd()) {
                    nextTag3 = this.input.readTag();
                } else {
                    return;
                }
            } while (nextTag3 == this.tag);
            this.nextTag = nextTag3;
        }
    }

    public <T> void readMessageList(List<T> target, Class<T> targetType, ExtensionRegistryLite extensionRegistry) throws IOException {
        readMessageList(target, Protobuf.getInstance().schemaFor(targetType), extensionRegistry);
    }

    public <T> void readMessageList(List<T> target, Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
        int nextTag2;
        if (WireFormat.getTagWireType(this.tag) == 2) {
            int listTag = this.tag;
            do {
                target.add(readMessage(schema, extensionRegistry));
                if (!this.input.isAtEnd() && this.nextTag == 0) {
                    nextTag2 = this.input.readTag();
                } else {
                    return;
                }
            } while (nextTag2 == listTag);
            this.nextTag = nextTag2;
            return;
        }
        throw InvalidProtocolBufferException.invalidWireType();
    }

    @Deprecated
    public <T> void readGroupList(List<T> target, Class<T> targetType, ExtensionRegistryLite extensionRegistry) throws IOException {
        readGroupList(target, Protobuf.getInstance().schemaFor(targetType), extensionRegistry);
    }

    @Deprecated
    public <T> void readGroupList(List<T> target, Schema<T> schema, ExtensionRegistryLite extensionRegistry) throws IOException {
        int nextTag2;
        if (WireFormat.getTagWireType(this.tag) == 3) {
            int listTag = this.tag;
            do {
                target.add(readGroup(schema, extensionRegistry));
                if (!this.input.isAtEnd() && this.nextTag == 0) {
                    nextTag2 = this.input.readTag();
                } else {
                    return;
                }
            } while (nextTag2 == listTag);
            this.nextTag = nextTag2;
            return;
        }
        throw InvalidProtocolBufferException.invalidWireType();
    }

    public void readBytesList(List<ByteString> target) throws IOException {
        int nextTag2;
        if (WireFormat.getTagWireType(this.tag) == 2) {
            do {
                target.add(readBytes());
                if (!this.input.isAtEnd()) {
                    nextTag2 = this.input.readTag();
                } else {
                    return;
                }
            } while (nextTag2 == this.tag);
            this.nextTag = nextTag2;
            return;
        }
        throw InvalidProtocolBufferException.invalidWireType();
    }

    public void readUInt32List(List<Integer> target) throws IOException {
        int nextTag2;
        int nextTag3;
        if (target instanceof IntArrayList) {
            IntArrayList plist = (IntArrayList) target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 2:
                    int endPos = this.input.getTotalBytesRead() + this.input.readUInt32();
                    do {
                        plist.addInt(this.input.readUInt32());
                    } while (this.input.getTotalBytesRead() < endPos);
                    requirePosition(endPos);
                    return;
                default:
                    throw InvalidProtocolBufferException.invalidWireType();
            }
            do {
                plist.addInt(this.input.readUInt32());
                if (!this.input.isAtEnd()) {
                    nextTag3 = this.input.readTag();
                } else {
                    return;
                }
            } while (nextTag3 == this.tag);
            this.nextTag = nextTag3;
            return;
        }
        switch (WireFormat.getTagWireType(this.tag)) {
            case 0:
                break;
            case 2:
                int endPos2 = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    target.add(Integer.valueOf(this.input.readUInt32()));
                } while (this.input.getTotalBytesRead() < endPos2);
                requirePosition(endPos2);
                return;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            target.add(Integer.valueOf(this.input.readUInt32()));
            if (!this.input.isAtEnd()) {
                nextTag2 = this.input.readTag();
            } else {
                return;
            }
        } while (nextTag2 == this.tag);
        this.nextTag = nextTag2;
    }

    public void readEnumList(List<Integer> target) throws IOException {
        int nextTag2;
        int nextTag3;
        if (target instanceof IntArrayList) {
            IntArrayList plist = (IntArrayList) target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 2:
                    int endPos = this.input.getTotalBytesRead() + this.input.readUInt32();
                    do {
                        plist.addInt(this.input.readEnum());
                    } while (this.input.getTotalBytesRead() < endPos);
                    requirePosition(endPos);
                    return;
                default:
                    throw InvalidProtocolBufferException.invalidWireType();
            }
            do {
                plist.addInt(this.input.readEnum());
                if (!this.input.isAtEnd()) {
                    nextTag3 = this.input.readTag();
                } else {
                    return;
                }
            } while (nextTag3 == this.tag);
            this.nextTag = nextTag3;
            return;
        }
        switch (WireFormat.getTagWireType(this.tag)) {
            case 0:
                break;
            case 2:
                int endPos2 = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    target.add(Integer.valueOf(this.input.readEnum()));
                } while (this.input.getTotalBytesRead() < endPos2);
                requirePosition(endPos2);
                return;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            target.add(Integer.valueOf(this.input.readEnum()));
            if (!this.input.isAtEnd()) {
                nextTag2 = this.input.readTag();
            } else {
                return;
            }
        } while (nextTag2 == this.tag);
        this.nextTag = nextTag2;
    }

    public void readSFixed32List(List<Integer> target) throws IOException {
        int nextTag2;
        int nextTag3;
        if (target instanceof IntArrayList) {
            IntArrayList plist = (IntArrayList) target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 2:
                    int bytes = this.input.readUInt32();
                    verifyPackedFixed32Length(bytes);
                    int endPos = this.input.getTotalBytesRead() + bytes;
                    do {
                        plist.addInt(this.input.readSFixed32());
                    } while (this.input.getTotalBytesRead() < endPos);
                    return;
                case 5:
                    break;
                default:
                    throw InvalidProtocolBufferException.invalidWireType();
            }
            do {
                plist.addInt(this.input.readSFixed32());
                if (!this.input.isAtEnd()) {
                    nextTag3 = this.input.readTag();
                } else {
                    return;
                }
            } while (nextTag3 == this.tag);
            this.nextTag = nextTag3;
            return;
        }
        switch (WireFormat.getTagWireType(this.tag)) {
            case 2:
                int bytes2 = this.input.readUInt32();
                verifyPackedFixed32Length(bytes2);
                int endPos2 = this.input.getTotalBytesRead() + bytes2;
                do {
                    target.add(Integer.valueOf(this.input.readSFixed32()));
                } while (this.input.getTotalBytesRead() < endPos2);
                return;
            case 5:
                break;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            target.add(Integer.valueOf(this.input.readSFixed32()));
            if (!this.input.isAtEnd()) {
                nextTag2 = this.input.readTag();
            } else {
                return;
            }
        } while (nextTag2 == this.tag);
        this.nextTag = nextTag2;
    }

    public void readSFixed64List(List<Long> target) throws IOException {
        int nextTag2;
        int nextTag3;
        if (target instanceof LongArrayList) {
            LongArrayList plist = (LongArrayList) target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 1:
                    break;
                case 2:
                    int bytes = this.input.readUInt32();
                    verifyPackedFixed64Length(bytes);
                    int endPos = this.input.getTotalBytesRead() + bytes;
                    do {
                        plist.addLong(this.input.readSFixed64());
                    } while (this.input.getTotalBytesRead() < endPos);
                    return;
                default:
                    throw InvalidProtocolBufferException.invalidWireType();
            }
            do {
                plist.addLong(this.input.readSFixed64());
                if (!this.input.isAtEnd()) {
                    nextTag3 = this.input.readTag();
                } else {
                    return;
                }
            } while (nextTag3 == this.tag);
            this.nextTag = nextTag3;
            return;
        }
        switch (WireFormat.getTagWireType(this.tag)) {
            case 1:
                break;
            case 2:
                int bytes2 = this.input.readUInt32();
                verifyPackedFixed64Length(bytes2);
                int endPos2 = this.input.getTotalBytesRead() + bytes2;
                do {
                    target.add(Long.valueOf(this.input.readSFixed64()));
                } while (this.input.getTotalBytesRead() < endPos2);
                return;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            target.add(Long.valueOf(this.input.readSFixed64()));
            if (!this.input.isAtEnd()) {
                nextTag2 = this.input.readTag();
            } else {
                return;
            }
        } while (nextTag2 == this.tag);
        this.nextTag = nextTag2;
    }

    public void readSInt32List(List<Integer> target) throws IOException {
        int nextTag2;
        int nextTag3;
        if (target instanceof IntArrayList) {
            IntArrayList plist = (IntArrayList) target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 2:
                    int endPos = this.input.getTotalBytesRead() + this.input.readUInt32();
                    do {
                        plist.addInt(this.input.readSInt32());
                    } while (this.input.getTotalBytesRead() < endPos);
                    requirePosition(endPos);
                    return;
                default:
                    throw InvalidProtocolBufferException.invalidWireType();
            }
            do {
                plist.addInt(this.input.readSInt32());
                if (!this.input.isAtEnd()) {
                    nextTag3 = this.input.readTag();
                } else {
                    return;
                }
            } while (nextTag3 == this.tag);
            this.nextTag = nextTag3;
            return;
        }
        switch (WireFormat.getTagWireType(this.tag)) {
            case 0:
                break;
            case 2:
                int endPos2 = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    target.add(Integer.valueOf(this.input.readSInt32()));
                } while (this.input.getTotalBytesRead() < endPos2);
                requirePosition(endPos2);
                return;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            target.add(Integer.valueOf(this.input.readSInt32()));
            if (!this.input.isAtEnd()) {
                nextTag2 = this.input.readTag();
            } else {
                return;
            }
        } while (nextTag2 == this.tag);
        this.nextTag = nextTag2;
    }

    public void readSInt64List(List<Long> target) throws IOException {
        int nextTag2;
        int nextTag3;
        if (target instanceof LongArrayList) {
            LongArrayList plist = (LongArrayList) target;
            switch (WireFormat.getTagWireType(this.tag)) {
                case 0:
                    break;
                case 2:
                    int endPos = this.input.getTotalBytesRead() + this.input.readUInt32();
                    do {
                        plist.addLong(this.input.readSInt64());
                    } while (this.input.getTotalBytesRead() < endPos);
                    requirePosition(endPos);
                    return;
                default:
                    throw InvalidProtocolBufferException.invalidWireType();
            }
            do {
                plist.addLong(this.input.readSInt64());
                if (!this.input.isAtEnd()) {
                    nextTag3 = this.input.readTag();
                } else {
                    return;
                }
            } while (nextTag3 == this.tag);
            this.nextTag = nextTag3;
            return;
        }
        switch (WireFormat.getTagWireType(this.tag)) {
            case 0:
                break;
            case 2:
                int endPos2 = this.input.getTotalBytesRead() + this.input.readUInt32();
                do {
                    target.add(Long.valueOf(this.input.readSInt64()));
                } while (this.input.getTotalBytesRead() < endPos2);
                requirePosition(endPos2);
                return;
            default:
                throw InvalidProtocolBufferException.invalidWireType();
        }
        do {
            target.add(Long.valueOf(this.input.readSInt64()));
            if (!this.input.isAtEnd()) {
                nextTag2 = this.input.readTag();
            } else {
                return;
            }
        } while (nextTag2 == this.tag);
        this.nextTag = nextTag2;
    }

    private void verifyPackedFixed64Length(int bytes) throws IOException {
        if ((bytes & 7) != 0) {
            throw InvalidProtocolBufferException.parseFailure();
        }
    }

    public <K, V> void readMap(Map<K, V> target, MapEntryLite.Metadata<K, V> metadata, ExtensionRegistryLite extensionRegistry) throws IOException {
        requireWireType(2);
        int prevLimit = this.input.pushLimit(this.input.readUInt32());
        K key = metadata.defaultKey;
        V value = metadata.defaultValue;
        while (true) {
            try {
                int number = getFieldNumber();
                if (number != Integer.MAX_VALUE && !this.input.isAtEnd()) {
                    switch (number) {
                        case 1:
                            key = readField(metadata.keyType, (Class<?>) null, (ExtensionRegistryLite) null);
                            break;
                        case 2:
                            value = readField(metadata.valueType, metadata.defaultValue.getClass(), extensionRegistry);
                            break;
                        default:
                            if (!skipField()) {
                                throw new InvalidProtocolBufferException("Unable to parse map entry.");
                            }
                            break;
                    }
                } else {
                    target.put(key, value);
                }
            } catch (InvalidProtocolBufferException.InvalidWireTypeException e) {
                if (!skipField()) {
                    throw new InvalidProtocolBufferException("Unable to parse map entry.");
                }
            } catch (Throwable th) {
                this.input.popLimit(prevLimit);
                throw th;
            }
        }
        target.put(key, value);
        this.input.popLimit(prevLimit);
    }

    private Object readField(WireFormat.FieldType fieldType, Class<?> messageType, ExtensionRegistryLite extensionRegistry) throws IOException {
        switch (fieldType) {
            case BOOL:
                return Boolean.valueOf(readBool());
            case BYTES:
                return readBytes();
            case DOUBLE:
                return Double.valueOf(readDouble());
            case ENUM:
                return Integer.valueOf(readEnum());
            case FIXED32:
                return Integer.valueOf(readFixed32());
            case FIXED64:
                return Long.valueOf(readFixed64());
            case FLOAT:
                return Float.valueOf(readFloat());
            case INT32:
                return Integer.valueOf(readInt32());
            case INT64:
                return Long.valueOf(readInt64());
            case MESSAGE:
                return readMessage(messageType, extensionRegistry);
            case SFIXED32:
                return Integer.valueOf(readSFixed32());
            case SFIXED64:
                return Long.valueOf(readSFixed64());
            case SINT32:
                return Integer.valueOf(readSInt32());
            case SINT64:
                return Long.valueOf(readSInt64());
            case STRING:
                return readStringRequireUtf8();
            case UINT32:
                return Integer.valueOf(readUInt32());
            case UINT64:
                return Long.valueOf(readUInt64());
            default:
                throw new IllegalArgumentException("unsupported field type.");
        }
    }

    private void verifyPackedFixed32Length(int bytes) throws IOException {
        if ((bytes & 3) != 0) {
            throw InvalidProtocolBufferException.parseFailure();
        }
    }

    private void requirePosition(int expectedPosition) throws IOException {
        if (this.input.getTotalBytesRead() != expectedPosition) {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
    }
}
