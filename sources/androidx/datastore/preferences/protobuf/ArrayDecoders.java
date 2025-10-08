package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.GeneratedMessageLite;
import androidx.datastore.preferences.protobuf.Internal;
import androidx.datastore.preferences.protobuf.WireFormat;
import com.google.common.base.Ascii;
import java.io.IOException;
import java.util.List;
import kotlinx.coroutines.scheduling.WorkQueueKt;

@CheckReturnValue
final class ArrayDecoders {
    static final int DEFAULT_RECURSION_LIMIT = 100;
    private static volatile int recursionLimit = 100;

    private ArrayDecoders() {
    }

    static final class Registers {
        public final ExtensionRegistryLite extensionRegistry;
        public int int1;
        public long long1;
        public Object object1;
        public int recursionDepth;

        Registers() {
            this.extensionRegistry = ExtensionRegistryLite.getEmptyRegistry();
        }

        Registers(ExtensionRegistryLite extensionRegistry2) {
            if (extensionRegistry2 != null) {
                this.extensionRegistry = extensionRegistry2;
                return;
            }
            throw new NullPointerException();
        }
    }

    static int decodeVarint32(byte[] data, int position, Registers registers) {
        int position2 = position + 1;
        byte position3 = data[position];
        if (position3 < 0) {
            return decodeVarint32(position3, data, position2, registers);
        }
        registers.int1 = position3;
        return position2;
    }

    static int decodeVarint32(int firstByte, byte[] data, int position, Registers registers) {
        int value = firstByte & WorkQueueKt.MASK;
        int position2 = position + 1;
        byte b2 = data[position];
        if (b2 >= 0) {
            registers.int1 = (b2 << 7) | value;
            return position2;
        }
        int value2 = value | ((b2 & Byte.MAX_VALUE) << 7);
        int position3 = position2 + 1;
        byte b3 = data[position2];
        if (b3 >= 0) {
            registers.int1 = (b3 << Ascii.SO) | value2;
            return position3;
        }
        int value3 = value2 | ((b3 & Byte.MAX_VALUE) << Ascii.SO);
        int position4 = position3 + 1;
        byte position5 = data[position3];
        if (position5 >= 0) {
            registers.int1 = (position5 << Ascii.NAK) | value3;
            return position4;
        }
        int value4 = value3 | ((position5 & Byte.MAX_VALUE) << Ascii.NAK);
        int position6 = position4 + 1;
        byte position7 = data[position4];
        if (position7 >= 0) {
            registers.int1 = (position7 << Ascii.FS) | value4;
            return position6;
        }
        int value5 = value4 | ((position7 & Byte.MAX_VALUE) << Ascii.FS);
        while (true) {
            int position8 = position6 + 1;
            if (data[position6] < 0) {
                position6 = position8;
            } else {
                registers.int1 = value5;
                return position8;
            }
        }
    }

    static int decodeVarint64(byte[] data, int position, Registers registers) {
        int position2 = position + 1;
        long value = (long) data[position];
        if (value < 0) {
            return decodeVarint64(value, data, position2, registers);
        }
        registers.long1 = value;
        return position2;
    }

    static int decodeVarint64(long firstByte, byte[] data, int position, Registers registers) {
        int position2 = position + 1;
        byte next = data[position];
        int shift = 7;
        long value = (127 & firstByte) | (((long) (next & Byte.MAX_VALUE)) << 7);
        while (next < 0) {
            next = data[position2];
            shift += 7;
            value |= ((long) (next & Byte.MAX_VALUE)) << shift;
            position2++;
        }
        registers.long1 = value;
        return position2;
    }

    static int decodeFixed32(byte[] data, int position) {
        return (data[position] & 255) | ((data[position + 1] & 255) << 8) | ((data[position + 2] & 255) << Ascii.DLE) | ((data[position + 3] & 255) << Ascii.CAN);
    }

    static long decodeFixed64(byte[] data, int position) {
        return (((long) data[position]) & 255) | ((((long) data[position + 1]) & 255) << 8) | ((((long) data[position + 2]) & 255) << 16) | ((((long) data[position + 3]) & 255) << 24) | ((((long) data[position + 4]) & 255) << 32) | ((((long) data[position + 5]) & 255) << 40) | ((((long) data[position + 6]) & 255) << 48) | ((255 & ((long) data[position + 7])) << 56);
    }

    static double decodeDouble(byte[] data, int position) {
        return Double.longBitsToDouble(decodeFixed64(data, position));
    }

    static float decodeFloat(byte[] data, int position) {
        return Float.intBitsToFloat(decodeFixed32(data, position));
    }

    static int decodeString(byte[] data, int position, Registers registers) throws InvalidProtocolBufferException {
        int position2 = decodeVarint32(data, position, registers);
        int length = registers.int1;
        if (length < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        } else if (length == 0) {
            registers.object1 = "";
            return position2;
        } else {
            registers.object1 = new String(data, position2, length, Internal.UTF_8);
            return position2 + length;
        }
    }

    static int decodeStringRequireUtf8(byte[] data, int position, Registers registers) throws InvalidProtocolBufferException {
        int position2 = decodeVarint32(data, position, registers);
        int length = registers.int1;
        if (length < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        } else if (length == 0) {
            registers.object1 = "";
            return position2;
        } else {
            registers.object1 = Utf8.decodeUtf8(data, position2, length);
            return position2 + length;
        }
    }

    static int decodeBytes(byte[] data, int position, Registers registers) throws InvalidProtocolBufferException {
        int position2 = decodeVarint32(data, position, registers);
        int length = registers.int1;
        if (length < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        } else if (length > data.length - position2) {
            throw InvalidProtocolBufferException.truncatedMessage();
        } else if (length == 0) {
            registers.object1 = ByteString.EMPTY;
            return position2;
        } else {
            registers.object1 = ByteString.copyFrom(data, position2, length);
            return position2 + length;
        }
    }

    static int decodeMessageField(Schema schema, byte[] data, int position, int limit, Registers registers) throws IOException {
        Object msg = schema.newInstance();
        Schema schema2 = schema;
        Registers registers2 = registers;
        int offset = mergeMessageField(msg, schema2, data, position, limit, registers2);
        schema2.makeImmutable(msg);
        registers2.object1 = msg;
        return offset;
    }

    static int decodeGroupField(Schema schema, byte[] data, int position, int limit, int endGroup, Registers registers) throws IOException {
        Object msg = schema.newInstance();
        Schema schema2 = schema;
        Registers registers2 = registers;
        int offset = mergeGroupField(msg, schema2, data, position, limit, endGroup, registers2);
        schema2.makeImmutable(msg);
        registers2.object1 = msg;
        return offset;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v4, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v5, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int mergeMessageField(java.lang.Object r6, androidx.datastore.preferences.protobuf.Schema r7, byte[] r8, int r9, int r10, androidx.datastore.preferences.protobuf.ArrayDecoders.Registers r11) throws java.io.IOException {
        /*
            int r0 = r9 + 1
            byte r9 = r8[r9]
            if (r9 >= 0) goto L_0x000e
            int r0 = decodeVarint32(r9, r8, r0, r11)
            int r9 = r11.int1
            r3 = r0
            goto L_0x000f
        L_0x000e:
            r3 = r0
        L_0x000f:
            if (r9 < 0) goto L_0x0034
            int r0 = r10 - r3
            if (r9 > r0) goto L_0x0034
            int r0 = r11.recursionDepth
            int r0 = r0 + 1
            r11.recursionDepth = r0
            int r0 = r11.recursionDepth
            checkRecursionLimit(r0)
            int r4 = r3 + r9
            r1 = r6
            r0 = r7
            r2 = r8
            r5 = r11
            r0.mergeFrom(r1, r2, r3, r4, r5)
            int r6 = r5.recursionDepth
            int r6 = r6 + -1
            r5.recursionDepth = r6
            r5.object1 = r1
            int r6 = r3 + r9
            return r6
        L_0x0034:
            r1 = r6
            r0 = r7
            r2 = r8
            r5 = r11
            androidx.datastore.preferences.protobuf.InvalidProtocolBufferException r6 = androidx.datastore.preferences.protobuf.InvalidProtocolBufferException.truncatedMessage()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.preferences.protobuf.ArrayDecoders.mergeMessageField(java.lang.Object, androidx.datastore.preferences.protobuf.Schema, byte[], int, int, androidx.datastore.preferences.protobuf.ArrayDecoders$Registers):int");
    }

    static int mergeGroupField(Object msg, Schema schema, byte[] data, int position, int limit, int endGroup, Registers registers) throws IOException {
        registers.recursionDepth++;
        checkRecursionLimit(registers.recursionDepth);
        Object msg2 = msg;
        Registers registers2 = registers;
        int endPosition = ((MessageSchema) schema).parseMessage(msg2, data, position, limit, endGroup, registers2);
        registers2.recursionDepth--;
        registers2.object1 = msg2;
        return endPosition;
    }

    static int decodeVarint32List(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
        IntArrayList output = (IntArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        output.addInt(registers.int1);
        while (position2 < limit) {
            int nextPosition = decodeVarint32(data, position2, registers);
            if (tag != registers.int1) {
                break;
            }
            position2 = decodeVarint32(data, nextPosition, registers);
            output.addInt(registers.int1);
        }
        return position2;
    }

    static int decodeVarint64List(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
        LongArrayList output = (LongArrayList) list;
        int position2 = decodeVarint64(data, position, registers);
        output.addLong(registers.long1);
        while (position2 < limit) {
            int nextPosition = decodeVarint32(data, position2, registers);
            if (tag != registers.int1) {
                break;
            }
            position2 = decodeVarint64(data, nextPosition, registers);
            output.addLong(registers.long1);
        }
        return position2;
    }

    static int decodeFixed32List(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
        IntArrayList output = (IntArrayList) list;
        output.addInt(decodeFixed32(data, position));
        int position2 = position + 4;
        while (position2 < limit) {
            int nextPosition = decodeVarint32(data, position2, registers);
            if (tag != registers.int1) {
                break;
            }
            output.addInt(decodeFixed32(data, nextPosition));
            position2 = nextPosition + 4;
        }
        return position2;
    }

    static int decodeFixed64List(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
        LongArrayList output = (LongArrayList) list;
        output.addLong(decodeFixed64(data, position));
        int position2 = position + 8;
        while (position2 < limit) {
            int nextPosition = decodeVarint32(data, position2, registers);
            if (tag != registers.int1) {
                break;
            }
            output.addLong(decodeFixed64(data, nextPosition));
            position2 = nextPosition + 8;
        }
        return position2;
    }

    static int decodeFloatList(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
        FloatArrayList output = (FloatArrayList) list;
        output.addFloat(decodeFloat(data, position));
        int position2 = position + 4;
        while (position2 < limit) {
            int nextPosition = decodeVarint32(data, position2, registers);
            if (tag != registers.int1) {
                break;
            }
            output.addFloat(decodeFloat(data, nextPosition));
            position2 = nextPosition + 4;
        }
        return position2;
    }

    static int decodeDoubleList(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
        DoubleArrayList output = (DoubleArrayList) list;
        output.addDouble(decodeDouble(data, position));
        int position2 = position + 8;
        while (position2 < limit) {
            int nextPosition = decodeVarint32(data, position2, registers);
            if (tag != registers.int1) {
                break;
            }
            output.addDouble(decodeDouble(data, nextPosition));
            position2 = nextPosition + 8;
        }
        return position2;
    }

    static int decodeBoolList(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
        BooleanArrayList output = (BooleanArrayList) list;
        int position2 = decodeVarint64(data, position, registers);
        output.addBoolean(registers.long1 != 0);
        while (position2 < limit) {
            int nextPosition = decodeVarint32(data, position2, registers);
            if (tag != registers.int1) {
                break;
            }
            position2 = decodeVarint64(data, nextPosition, registers);
            output.addBoolean(registers.long1 != 0);
        }
        return position2;
    }

    static int decodeSInt32List(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
        IntArrayList output = (IntArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        output.addInt(CodedInputStream.decodeZigZag32(registers.int1));
        while (position2 < limit) {
            int nextPosition = decodeVarint32(data, position2, registers);
            if (tag != registers.int1) {
                break;
            }
            position2 = decodeVarint32(data, nextPosition, registers);
            output.addInt(CodedInputStream.decodeZigZag32(registers.int1));
        }
        return position2;
    }

    static int decodeSInt64List(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) {
        LongArrayList output = (LongArrayList) list;
        int position2 = decodeVarint64(data, position, registers);
        output.addLong(CodedInputStream.decodeZigZag64(registers.long1));
        while (position2 < limit) {
            int nextPosition = decodeVarint32(data, position2, registers);
            if (tag != registers.int1) {
                break;
            }
            position2 = decodeVarint64(data, nextPosition, registers);
            output.addLong(CodedInputStream.decodeZigZag64(registers.long1));
        }
        return position2;
    }

    static int decodePackedVarint32List(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        IntArrayList output = (IntArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        int fieldLimit = registers.int1 + position2;
        while (position2 < fieldLimit) {
            position2 = decodeVarint32(data, position2, registers);
            output.addInt(registers.int1);
        }
        if (position2 == fieldLimit) {
            return position2;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedVarint64List(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        LongArrayList output = (LongArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        int fieldLimit = registers.int1 + position2;
        while (position2 < fieldLimit) {
            position2 = decodeVarint64(data, position2, registers);
            output.addLong(registers.long1);
        }
        if (position2 == fieldLimit) {
            return position2;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedFixed32List(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        IntArrayList output = (IntArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        int fieldLimit = registers.int1 + position2;
        while (position2 < fieldLimit) {
            output.addInt(decodeFixed32(data, position2));
            position2 += 4;
        }
        if (position2 == fieldLimit) {
            return position2;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedFixed64List(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        LongArrayList output = (LongArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        int fieldLimit = registers.int1 + position2;
        while (position2 < fieldLimit) {
            output.addLong(decodeFixed64(data, position2));
            position2 += 8;
        }
        if (position2 == fieldLimit) {
            return position2;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedFloatList(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        FloatArrayList output = (FloatArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        int fieldLimit = registers.int1 + position2;
        while (position2 < fieldLimit) {
            output.addFloat(decodeFloat(data, position2));
            position2 += 4;
        }
        if (position2 == fieldLimit) {
            return position2;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedDoubleList(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        DoubleArrayList output = (DoubleArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        int fieldLimit = registers.int1 + position2;
        while (position2 < fieldLimit) {
            output.addDouble(decodeDouble(data, position2));
            position2 += 8;
        }
        if (position2 == fieldLimit) {
            return position2;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedBoolList(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        BooleanArrayList output = (BooleanArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        int fieldLimit = registers.int1 + position2;
        while (position2 < fieldLimit) {
            position2 = decodeVarint64(data, position2, registers);
            output.addBoolean(registers.long1 != 0);
        }
        if (position2 == fieldLimit) {
            return position2;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedSInt32List(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        IntArrayList output = (IntArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        int fieldLimit = registers.int1 + position2;
        while (position2 < fieldLimit) {
            position2 = decodeVarint32(data, position2, registers);
            output.addInt(CodedInputStream.decodeZigZag32(registers.int1));
        }
        if (position2 == fieldLimit) {
            return position2;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodePackedSInt64List(byte[] data, int position, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        LongArrayList output = (LongArrayList) list;
        int position2 = decodeVarint32(data, position, registers);
        int fieldLimit = registers.int1 + position2;
        while (position2 < fieldLimit) {
            position2 = decodeVarint64(data, position2, registers);
            output.addLong(CodedInputStream.decodeZigZag64(registers.long1));
        }
        if (position2 == fieldLimit) {
            return position2;
        }
        throw InvalidProtocolBufferException.truncatedMessage();
    }

    static int decodeStringList(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) throws InvalidProtocolBufferException {
        int position2;
        Internal.ProtobufList<?> protobufList = list;
        int position3 = decodeVarint32(data, position, registers);
        int length = registers.int1;
        if (length >= 0) {
            if (length == 0) {
                protobufList.add("");
            } else {
                protobufList.add(new String(data, position3, length, Internal.UTF_8));
                position3 += length;
            }
            while (position2 < limit) {
                int nextPosition = decodeVarint32(data, position2, registers);
                if (tag != registers.int1) {
                    break;
                }
                position2 = decodeVarint32(data, nextPosition, registers);
                int nextLength = registers.int1;
                if (nextLength < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                } else if (nextLength == 0) {
                    protobufList.add("");
                } else {
                    protobufList.add(new String(data, position2, nextLength, Internal.UTF_8));
                    position2 += nextLength;
                }
            }
            return position2;
        }
        throw InvalidProtocolBufferException.negativeSize();
    }

    static int decodeStringListRequireUtf8(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) throws InvalidProtocolBufferException {
        int position2;
        Internal.ProtobufList<?> protobufList = list;
        int position3 = decodeVarint32(data, position, registers);
        int length = registers.int1;
        if (length >= 0) {
            if (length == 0) {
                protobufList.add("");
            } else if (Utf8.isValidUtf8(data, position3, position3 + length)) {
                protobufList.add(new String(data, position3, length, Internal.UTF_8));
                position3 += length;
            } else {
                throw InvalidProtocolBufferException.invalidUtf8();
            }
            while (position2 < limit) {
                int nextPosition = decodeVarint32(data, position2, registers);
                if (tag != registers.int1) {
                    break;
                }
                position2 = decodeVarint32(data, nextPosition, registers);
                int nextLength = registers.int1;
                if (nextLength < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                } else if (nextLength == 0) {
                    protobufList.add("");
                } else if (Utf8.isValidUtf8(data, position2, position2 + nextLength)) {
                    protobufList.add(new String(data, position2, nextLength, Internal.UTF_8));
                    position2 += nextLength;
                } else {
                    throw InvalidProtocolBufferException.invalidUtf8();
                }
            }
            return position2;
        }
        throw InvalidProtocolBufferException.negativeSize();
    }

    static int decodeBytesList(int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) throws InvalidProtocolBufferException {
        int position2;
        Internal.ProtobufList<?> protobufList = list;
        int position3 = decodeVarint32(data, position, registers);
        int length = registers.int1;
        if (length < 0) {
            throw InvalidProtocolBufferException.negativeSize();
        } else if (length <= data.length - position3) {
            if (length == 0) {
                protobufList.add(ByteString.EMPTY);
            } else {
                protobufList.add(ByteString.copyFrom(data, position3, length));
                position3 += length;
            }
            while (position2 < limit) {
                int nextPosition = decodeVarint32(data, position2, registers);
                if (tag != registers.int1) {
                    break;
                }
                position2 = decodeVarint32(data, nextPosition, registers);
                int nextLength = registers.int1;
                if (nextLength < 0) {
                    throw InvalidProtocolBufferException.negativeSize();
                } else if (nextLength > data.length - position2) {
                    throw InvalidProtocolBufferException.truncatedMessage();
                } else if (nextLength == 0) {
                    protobufList.add(ByteString.EMPTY);
                } else {
                    protobufList.add(ByteString.copyFrom(data, position2, nextLength));
                    position2 += nextLength;
                }
            }
            return position2;
        } else {
            throw InvalidProtocolBufferException.truncatedMessage();
        }
    }

    static int decodeMessageList(Schema<?> schema, int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        Internal.ProtobufList<?> protobufList = list;
        int position2 = decodeMessageField(schema, data, position, limit, registers);
        protobufList.add(registers.object1);
        while (position2 < limit) {
            int nextPosition = decodeVarint32(data, position2, registers);
            if (tag != registers.int1) {
                break;
            }
            position2 = decodeMessageField(schema, data, nextPosition, limit, registers);
            protobufList.add(registers.object1);
        }
        return position2;
    }

    static int decodeGroupList(Schema schema, int tag, byte[] data, int position, int limit, Internal.ProtobufList<?> list, Registers registers) throws IOException {
        Internal.ProtobufList<?> protobufList = list;
        int endgroup = (tag & -8) | 4;
        Schema schema2 = schema;
        byte[] data2 = data;
        int limit2 = limit;
        Registers registers2 = registers;
        int position2 = decodeGroupField(schema2, data2, position, limit2, endgroup, registers2);
        protobufList.add(registers2.object1);
        while (position2 < limit2) {
            int nextPosition = decodeVarint32(data2, position2, registers2);
            if (tag != registers2.int1) {
                break;
            }
            position2 = decodeGroupField(schema2, data2, nextPosition, limit2, endgroup, registers2);
            protobufList.add(registers2.object1);
        }
        return position2;
    }

    static int decodeExtensionOrUnknownField(int tag, byte[] data, int position, int limit, Object message, MessageLite defaultInstance, UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema, Registers registers) throws IOException {
        Registers registers2 = registers;
        GeneratedMessageLite.GeneratedExtension extension = registers2.extensionRegistry.findLiteExtensionByNumber(defaultInstance, tag >>> 3);
        if (extension == null) {
            return decodeUnknownField(tag, data, position, limit, MessageSchema.getMutableUnknownFields(message), registers2);
        }
        FieldSet<GeneratedMessageLite.ExtensionDescriptor> ensureExtensionsAreMutable = ((GeneratedMessageLite.ExtendableMessage) message).ensureExtensionsAreMutable();
        return decodeExtension(tag, data, position, limit, (GeneratedMessageLite.ExtendableMessage) message, extension, unknownFieldSchema, registers);
    }

    static int decodeExtension(int tag, byte[] data, int position, int limit, GeneratedMessageLite.ExtendableMessage<?, ?> message, GeneratedMessageLite.GeneratedExtension<?, ?> extension, UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema, Registers registers) throws IOException {
        int position2;
        byte[] bArr = data;
        int i = position;
        GeneratedMessageLite.ExtendableMessage<?, ?> extendableMessage = message;
        GeneratedMessageLite.GeneratedExtension<?, ?> generatedExtension = extension;
        Registers registers2 = registers;
        FieldSet<GeneratedMessageLite.ExtensionDescriptor> extensions = extendableMessage.extensions;
        int fieldNumber = tag >>> 3;
        if (!generatedExtension.descriptor.isRepeated() || !generatedExtension.descriptor.isPacked()) {
            GeneratedMessageLite.ExtendableMessage<?, ?> extendableMessage2 = extendableMessage;
            int fieldNumber2 = fieldNumber;
            Object value = null;
            if (generatedExtension.getLiteType() != WireFormat.FieldType.ENUM) {
                UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema2 = unknownFieldSchema;
                switch (generatedExtension.getLiteType()) {
                    case DOUBLE:
                        Registers registers3 = registers2;
                        value = Double.valueOf(decodeDouble(data, position));
                        position2 = i + 8;
                        break;
                    case FLOAT:
                        Registers registers4 = registers2;
                        value = Float.valueOf(decodeFloat(data, position));
                        position2 = i + 4;
                        break;
                    case INT64:
                    case UINT64:
                        Registers registers5 = registers2;
                        position2 = decodeVarint64(bArr, i, registers5);
                        value = Long.valueOf(registers5.long1);
                        break;
                    case INT32:
                    case UINT32:
                        Registers registers6 = registers2;
                        position2 = decodeVarint32(bArr, i, registers6);
                        value = Integer.valueOf(registers6.int1);
                        break;
                    case FIXED64:
                    case SFIXED64:
                        Registers registers7 = registers2;
                        value = Long.valueOf(decodeFixed64(data, position));
                        position2 = i + 8;
                        break;
                    case FIXED32:
                    case SFIXED32:
                        Registers registers8 = registers2;
                        value = Integer.valueOf(decodeFixed32(data, position));
                        position2 = i + 4;
                        break;
                    case BOOL:
                        Registers registers9 = registers2;
                        position2 = decodeVarint64(bArr, i, registers9);
                        value = Boolean.valueOf(registers9.long1 != 0);
                        break;
                    case SINT32:
                        Registers registers10 = registers2;
                        position2 = decodeVarint32(bArr, i, registers10);
                        value = Integer.valueOf(CodedInputStream.decodeZigZag32(registers10.int1));
                        break;
                    case SINT64:
                        Registers registers11 = registers2;
                        position2 = decodeVarint64(bArr, i, registers11);
                        value = Long.valueOf(CodedInputStream.decodeZigZag64(registers11.long1));
                        break;
                    case ENUM:
                        Registers registers12 = registers2;
                        throw new IllegalStateException("Shouldn't reach here.");
                    case BYTES:
                        Registers registers13 = registers2;
                        position2 = decodeBytes(bArr, i, registers13);
                        value = registers13.object1;
                        break;
                    case STRING:
                        Registers registers14 = registers2;
                        position2 = decodeString(bArr, i, registers14);
                        value = registers14.object1;
                        break;
                    case GROUP:
                        int endTag = (fieldNumber2 << 3) | 4;
                        Schema<?> schemaFor = Protobuf.getInstance().schemaFor(generatedExtension.getMessageDefaultInstance().getClass());
                        if (generatedExtension.isRepeated()) {
                            Registers registers15 = registers;
                            int position3 = decodeGroupField(schemaFor, data, position, limit, endTag, registers15);
                            extensions.addRepeatedField(generatedExtension.descriptor, registers15.object1);
                            int position4 = position3;
                            byte[] bArr2 = data;
                            return position4;
                        }
                        Registers registers16 = registers;
                        Object field = extensions.getField(generatedExtension.descriptor);
                        if (field == null) {
                            field = schemaFor.newInstance();
                            extensions.setField(generatedExtension.descriptor, field);
                        }
                        Object obj = field;
                        Schema<?> schema = schemaFor;
                        Object obj2 = obj;
                        Registers registers17 = registers16;
                        int endTag2 = endTag;
                        int position5 = mergeGroupField(obj2, schema, data, position, limit, endTag2, registers17);
                        Object obj3 = schema;
                        Object obj4 = obj2;
                        Object oldValue = obj3;
                        int i2 = endTag2;
                        Registers registers18 = registers17;
                        return position5;
                    case MESSAGE:
                        Schema fieldSchema = Protobuf.getInstance().schemaFor(generatedExtension.getMessageDefaultInstance().getClass());
                        if (generatedExtension.isRepeated()) {
                            int position6 = decodeMessageField(fieldSchema, bArr, i, limit, registers2);
                            extensions.addRepeatedField(generatedExtension.descriptor, registers2.object1);
                            Schema<?> schema2 = fieldSchema;
                            return position6;
                        }
                        int i3 = limit;
                        Object oldValue2 = extensions.getField(generatedExtension.descriptor);
                        if (oldValue2 == null) {
                            oldValue2 = fieldSchema.newInstance();
                            extensions.setField(generatedExtension.descriptor, oldValue2);
                        }
                        return mergeMessageField(oldValue2, fieldSchema, bArr, i, i3, registers2);
                    default:
                        Registers registers19 = registers2;
                        position2 = i;
                        break;
                }
            } else {
                position2 = decodeVarint32(bArr, i, registers2);
                if (generatedExtension.descriptor.getEnumType().findValueByNumber(registers2.int1) == null) {
                    SchemaUtil.storeUnknownEnum(extendableMessage2, fieldNumber2, registers2.int1, null, unknownFieldSchema);
                    return position2;
                }
                UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema3 = unknownFieldSchema;
                value = Integer.valueOf(registers2.int1);
                Registers registers20 = registers2;
            }
            if (generatedExtension.isRepeated()) {
                extensions.addRepeatedField(generatedExtension.descriptor, value);
            } else {
                extensions.setField(generatedExtension.descriptor, value);
            }
            return position2;
        }
        switch (generatedExtension.getLiteType()) {
            case DOUBLE:
                GeneratedMessageLite.ExtendableMessage<?, ?> extendableMessage3 = extendableMessage;
                int i4 = fieldNumber;
                DoubleArrayList list = new DoubleArrayList();
                int position7 = decodePackedDoubleList(bArr, i, list, registers2);
                extensions.setField(generatedExtension.descriptor, list);
                UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema4 = unknownFieldSchema;
                Registers registers21 = registers2;
                return position7;
            case FLOAT:
                GeneratedMessageLite.ExtendableMessage<?, ?> extendableMessage4 = extendableMessage;
                int i5 = fieldNumber;
                FloatArrayList list2 = new FloatArrayList();
                int position8 = decodePackedFloatList(bArr, i, list2, registers2);
                extensions.setField(generatedExtension.descriptor, list2);
                UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema5 = unknownFieldSchema;
                Registers registers22 = registers2;
                return position8;
            case INT64:
            case UINT64:
                GeneratedMessageLite.ExtendableMessage<?, ?> extendableMessage5 = extendableMessage;
                int i6 = fieldNumber;
                LongArrayList list3 = new LongArrayList();
                int position9 = decodePackedVarint64List(bArr, i, list3, registers2);
                extensions.setField(generatedExtension.descriptor, list3);
                UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema6 = unknownFieldSchema;
                Registers registers23 = registers2;
                return position9;
            case INT32:
            case UINT32:
                GeneratedMessageLite.ExtendableMessage<?, ?> extendableMessage6 = extendableMessage;
                int i7 = fieldNumber;
                IntArrayList list4 = new IntArrayList();
                int position10 = decodePackedVarint32List(bArr, i, list4, registers2);
                extensions.setField(generatedExtension.descriptor, list4);
                UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema7 = unknownFieldSchema;
                Registers registers24 = registers2;
                return position10;
            case FIXED64:
            case SFIXED64:
                GeneratedMessageLite.ExtendableMessage<?, ?> extendableMessage7 = extendableMessage;
                int i8 = fieldNumber;
                LongArrayList list5 = new LongArrayList();
                int position11 = decodePackedFixed64List(bArr, i, list5, registers2);
                extensions.setField(generatedExtension.descriptor, list5);
                UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema8 = unknownFieldSchema;
                Registers registers25 = registers2;
                return position11;
            case FIXED32:
            case SFIXED32:
                GeneratedMessageLite.ExtendableMessage<?, ?> extendableMessage8 = extendableMessage;
                int i9 = fieldNumber;
                IntArrayList list6 = new IntArrayList();
                int position12 = decodePackedFixed32List(bArr, i, list6, registers2);
                extensions.setField(generatedExtension.descriptor, list6);
                UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema9 = unknownFieldSchema;
                Registers registers26 = registers2;
                return position12;
            case BOOL:
                GeneratedMessageLite.ExtendableMessage<?, ?> extendableMessage9 = extendableMessage;
                int i10 = fieldNumber;
                BooleanArrayList list7 = new BooleanArrayList();
                int position13 = decodePackedBoolList(bArr, i, list7, registers2);
                extensions.setField(generatedExtension.descriptor, list7);
                UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema10 = unknownFieldSchema;
                Registers registers27 = registers2;
                return position13;
            case SINT32:
                GeneratedMessageLite.ExtendableMessage<?, ?> extendableMessage10 = extendableMessage;
                int i11 = fieldNumber;
                IntArrayList list8 = new IntArrayList();
                int position14 = decodePackedSInt32List(bArr, i, list8, registers2);
                extensions.setField(generatedExtension.descriptor, list8);
                UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema11 = unknownFieldSchema;
                Registers registers28 = registers2;
                return position14;
            case SINT64:
                GeneratedMessageLite.ExtendableMessage<?, ?> extendableMessage11 = extendableMessage;
                int i12 = fieldNumber;
                LongArrayList list9 = new LongArrayList();
                int position15 = decodePackedSInt64List(bArr, i, list9, registers2);
                extensions.setField(generatedExtension.descriptor, list9);
                UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema12 = unknownFieldSchema;
                Registers registers29 = registers2;
                return position15;
            case ENUM:
                IntArrayList list10 = new IntArrayList();
                int position16 = decodePackedVarint32List(bArr, i, list10, registers2);
                GeneratedMessageLite.ExtendableMessage<?, ?> extendableMessage12 = extendableMessage;
                SchemaUtil.filterUnknownEnumList((Object) extendableMessage12, fieldNumber, (List<Integer>) list10, generatedExtension.descriptor.getEnumType(), null, unknownFieldSchema);
                GeneratedMessageLite.ExtendableMessage<?, ?> extendableMessage13 = extendableMessage12;
                int i13 = fieldNumber;
                extensions.setField(generatedExtension.descriptor, list10);
                UnknownFieldSchema<UnknownFieldSetLite, UnknownFieldSetLite> unknownFieldSchema13 = unknownFieldSchema;
                Registers registers30 = registers2;
                return position16;
            default:
                GeneratedMessageLite.ExtendableMessage<?, ?> extendableMessage14 = extendableMessage;
                int i14 = fieldNumber;
                throw new IllegalStateException("Type cannot be packed: " + generatedExtension.descriptor.getLiteType());
        }
    }

    static int decodeUnknownField(int tag, byte[] data, int position, int limit, UnknownFieldSetLite unknownFields, Registers registers) throws InvalidProtocolBufferException {
        int limit2;
        Registers registers2;
        if (WireFormat.getTagFieldNumber(tag) != 0) {
            switch (WireFormat.getTagWireType(tag)) {
                case 0:
                    Registers registers3 = registers;
                    int position2 = decodeVarint64(data, position, registers3);
                    unknownFields.storeField(tag, Long.valueOf(registers3.long1));
                    return position2;
                case 1:
                    unknownFields.storeField(tag, Long.valueOf(decodeFixed64(data, position)));
                    return position + 8;
                case 2:
                    byte[] data2 = data;
                    int i = limit;
                    Registers registers4 = registers;
                    int position3 = decodeVarint32(data2, position, registers4);
                    int position4 = registers4.int1;
                    if (position4 < 0) {
                        throw InvalidProtocolBufferException.negativeSize();
                    } else if (position4 <= data2.length - position3) {
                        if (position4 == 0) {
                            unknownFields.storeField(tag, ByteString.EMPTY);
                        } else {
                            unknownFields.storeField(tag, ByteString.copyFrom(data2, position3, position4));
                        }
                        return position3 + position4;
                    } else {
                        throw InvalidProtocolBufferException.truncatedMessage();
                    }
                case 3:
                    UnknownFieldSetLite child = UnknownFieldSetLite.newInstance();
                    int endGroup = (tag & -8) | 4;
                    int lastTag = 0;
                    registers.recursionDepth++;
                    checkRecursionLimit(registers.recursionDepth);
                    while (true) {
                        if (position < limit) {
                            int position5 = decodeVarint32(data, position, registers);
                            lastTag = registers.int1;
                            if (lastTag == endGroup) {
                                byte[] bArr = data;
                                limit2 = limit;
                                registers2 = registers;
                                position = position5;
                            } else {
                                position = decodeUnknownField(lastTag, data, position5, limit, child, registers);
                            }
                        } else {
                            limit2 = limit;
                            registers2 = registers;
                        }
                    }
                    registers2.recursionDepth--;
                    if (position > limit2 || lastTag != endGroup) {
                        throw InvalidProtocolBufferException.parseFailure();
                    }
                    unknownFields.storeField(tag, child);
                    return position;
                case 5:
                    unknownFields.storeField(tag, Integer.valueOf(decodeFixed32(data, position)));
                    return position + 4;
                default:
                    byte[] bArr2 = data;
                    throw InvalidProtocolBufferException.invalidTag();
            }
        } else {
            throw InvalidProtocolBufferException.invalidTag();
        }
    }

    static int skipField(int tag, byte[] data, int position, int limit, Registers registers) throws InvalidProtocolBufferException {
        if (WireFormat.getTagFieldNumber(tag) != 0) {
            switch (WireFormat.getTagWireType(tag)) {
                case 0:
                    return decodeVarint64(data, position, registers);
                case 1:
                    return position + 8;
                case 2:
                    return registers.int1 + decodeVarint32(data, position, registers);
                case 3:
                    int endGroup = (tag & -8) | 4;
                    int lastTag = 0;
                    while (position < limit) {
                        position = decodeVarint32(data, position, registers);
                        lastTag = registers.int1;
                        if (lastTag != endGroup) {
                            position = skipField(lastTag, data, position, limit, registers);
                        } else if (position > limit && lastTag == endGroup) {
                            return position;
                        } else {
                            throw InvalidProtocolBufferException.parseFailure();
                        }
                    }
                    if (position > limit) {
                    }
                    throw InvalidProtocolBufferException.parseFailure();
                case 5:
                    return position + 4;
                default:
                    throw InvalidProtocolBufferException.invalidTag();
            }
        } else {
            throw InvalidProtocolBufferException.invalidTag();
        }
    }

    public static void setRecursionLimit(int limit) {
        recursionLimit = limit;
    }

    private static void checkRecursionLimit(int depth) throws InvalidProtocolBufferException {
        if (depth >= recursionLimit) {
            throw InvalidProtocolBufferException.recursionLimitExceeded();
        }
    }
}
