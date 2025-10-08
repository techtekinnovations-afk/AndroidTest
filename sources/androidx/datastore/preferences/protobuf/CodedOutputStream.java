package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.Utf8;
import com.google.firebase.firestore.model.Values;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class CodedOutputStream extends ByteOutput {
    public static final int DEFAULT_BUFFER_SIZE = 4096;
    /* access modifiers changed from: private */
    public static final boolean HAS_UNSAFE_ARRAY_OPERATIONS = UnsafeUtil.hasUnsafeArrayOperations();
    @Deprecated
    public static final int LITTLE_ENDIAN_32_SIZE = 4;
    private static final Logger logger = Logger.getLogger(CodedOutputStream.class.getName());
    private boolean serializationDeterministic;
    CodedOutputStreamWriter wrapper;

    public abstract void flush() throws IOException;

    public abstract int getTotalBytesWritten();

    public abstract int spaceLeft();

    public abstract void write(byte b) throws IOException;

    public abstract void write(ByteBuffer byteBuffer) throws IOException;

    public abstract void write(byte[] bArr, int i, int i2) throws IOException;

    public abstract void writeBool(int i, boolean z) throws IOException;

    public abstract void writeByteArray(int i, byte[] bArr) throws IOException;

    public abstract void writeByteArray(int i, byte[] bArr, int i2, int i3) throws IOException;

    /* access modifiers changed from: package-private */
    public abstract void writeByteArrayNoTag(byte[] bArr, int i, int i2) throws IOException;

    public abstract void writeByteBuffer(int i, ByteBuffer byteBuffer) throws IOException;

    public abstract void writeBytes(int i, ByteString byteString) throws IOException;

    public abstract void writeBytesNoTag(ByteString byteString) throws IOException;

    public abstract void writeFixed32(int i, int i2) throws IOException;

    public abstract void writeFixed32NoTag(int i) throws IOException;

    public abstract void writeFixed64(int i, long j) throws IOException;

    public abstract void writeFixed64NoTag(long j) throws IOException;

    public abstract void writeInt32(int i, int i2) throws IOException;

    public abstract void writeInt32NoTag(int i) throws IOException;

    public abstract void writeLazy(ByteBuffer byteBuffer) throws IOException;

    public abstract void writeLazy(byte[] bArr, int i, int i2) throws IOException;

    public abstract void writeMessage(int i, MessageLite messageLite) throws IOException;

    /* access modifiers changed from: package-private */
    public abstract void writeMessage(int i, MessageLite messageLite, Schema schema) throws IOException;

    public abstract void writeMessageNoTag(MessageLite messageLite) throws IOException;

    /* access modifiers changed from: package-private */
    public abstract void writeMessageNoTag(MessageLite messageLite, Schema schema) throws IOException;

    public abstract void writeMessageSetExtension(int i, MessageLite messageLite) throws IOException;

    public abstract void writeRawBytes(ByteBuffer byteBuffer) throws IOException;

    public abstract void writeRawMessageSetExtension(int i, ByteString byteString) throws IOException;

    public abstract void writeString(int i, String str) throws IOException;

    public abstract void writeStringNoTag(String str) throws IOException;

    public abstract void writeTag(int i, int i2) throws IOException;

    public abstract void writeUInt32(int i, int i2) throws IOException;

    public abstract void writeUInt32NoTag(int i) throws IOException;

    public abstract void writeUInt64(int i, long j) throws IOException;

    public abstract void writeUInt64NoTag(long j) throws IOException;

    static int computePreferredBufferSize(int dataLength) {
        if (dataLength > 4096) {
            return 4096;
        }
        return dataLength;
    }

    public static CodedOutputStream newInstance(OutputStream output) {
        return newInstance(output, 4096);
    }

    public static CodedOutputStream newInstance(OutputStream output, int bufferSize) {
        return new OutputStreamEncoder(output, bufferSize);
    }

    public static CodedOutputStream newInstance(byte[] flatArray) {
        return newInstance(flatArray, 0, flatArray.length);
    }

    public static CodedOutputStream newInstance(byte[] flatArray, int offset, int length) {
        return new ArrayEncoder(flatArray, offset, length);
    }

    public static CodedOutputStream newInstance(ByteBuffer buffer) {
        if (buffer.hasArray()) {
            return new HeapNioEncoder(buffer);
        }
        if (!buffer.isDirect() || buffer.isReadOnly()) {
            throw new IllegalArgumentException("ByteBuffer is read-only");
        } else if (UnsafeDirectNioEncoder.isSupported()) {
            return newUnsafeInstance(buffer);
        } else {
            return newSafeInstance(buffer);
        }
    }

    static CodedOutputStream newUnsafeInstance(ByteBuffer buffer) {
        return new UnsafeDirectNioEncoder(buffer);
    }

    static CodedOutputStream newSafeInstance(ByteBuffer buffer) {
        return new SafeDirectNioEncoder(buffer);
    }

    public void useDeterministicSerialization() {
        this.serializationDeterministic = true;
    }

    /* access modifiers changed from: package-private */
    public boolean isSerializationDeterministic() {
        return this.serializationDeterministic;
    }

    @Deprecated
    public static CodedOutputStream newInstance(ByteBuffer byteBuffer, int unused) {
        return newInstance(byteBuffer);
    }

    static CodedOutputStream newInstance(ByteOutput byteOutput, int bufferSize) {
        if (bufferSize >= 0) {
            return new ByteOutputEncoder(byteOutput, bufferSize);
        }
        throw new IllegalArgumentException("bufferSize must be positive");
    }

    private CodedOutputStream() {
    }

    public final void writeSInt32(int fieldNumber, int value) throws IOException {
        writeUInt32(fieldNumber, encodeZigZag32(value));
    }

    public final void writeSFixed32(int fieldNumber, int value) throws IOException {
        writeFixed32(fieldNumber, value);
    }

    public final void writeInt64(int fieldNumber, long value) throws IOException {
        writeUInt64(fieldNumber, value);
    }

    public final void writeSInt64(int fieldNumber, long value) throws IOException {
        writeUInt64(fieldNumber, encodeZigZag64(value));
    }

    public final void writeSFixed64(int fieldNumber, long value) throws IOException {
        writeFixed64(fieldNumber, value);
    }

    public final void writeFloat(int fieldNumber, float value) throws IOException {
        writeFixed32(fieldNumber, Float.floatToRawIntBits(value));
    }

    public final void writeDouble(int fieldNumber, double value) throws IOException {
        writeFixed64(fieldNumber, Double.doubleToRawLongBits(value));
    }

    public final void writeEnum(int fieldNumber, int value) throws IOException {
        writeInt32(fieldNumber, value);
    }

    public final void writeRawByte(byte value) throws IOException {
        write(value);
    }

    public final void writeRawByte(int value) throws IOException {
        write((byte) value);
    }

    public final void writeRawBytes(byte[] value) throws IOException {
        write(value, 0, value.length);
    }

    public final void writeRawBytes(byte[] value, int offset, int length) throws IOException {
        write(value, offset, length);
    }

    public final void writeRawBytes(ByteString value) throws IOException {
        value.writeTo((ByteOutput) this);
    }

    public final void writeSInt32NoTag(int value) throws IOException {
        writeUInt32NoTag(encodeZigZag32(value));
    }

    public final void writeSFixed32NoTag(int value) throws IOException {
        writeFixed32NoTag(value);
    }

    public final void writeInt64NoTag(long value) throws IOException {
        writeUInt64NoTag(value);
    }

    public final void writeSInt64NoTag(long value) throws IOException {
        writeUInt64NoTag(encodeZigZag64(value));
    }

    public final void writeSFixed64NoTag(long value) throws IOException {
        writeFixed64NoTag(value);
    }

    public final void writeFloatNoTag(float value) throws IOException {
        writeFixed32NoTag(Float.floatToRawIntBits(value));
    }

    public final void writeDoubleNoTag(double value) throws IOException {
        writeFixed64NoTag(Double.doubleToRawLongBits(value));
    }

    public final void writeBoolNoTag(boolean value) throws IOException {
        write(value ? (byte) 1 : 0);
    }

    public final void writeEnumNoTag(int value) throws IOException {
        writeInt32NoTag(value);
    }

    public final void writeByteArrayNoTag(byte[] value) throws IOException {
        writeByteArrayNoTag(value, 0, value.length);
    }

    public static int computeInt32Size(int fieldNumber, int value) {
        return computeTagSize(fieldNumber) + computeInt32SizeNoTag(value);
    }

    public static int computeUInt32Size(int fieldNumber, int value) {
        return computeTagSize(fieldNumber) + computeUInt32SizeNoTag(value);
    }

    public static int computeSInt32Size(int fieldNumber, int value) {
        return computeTagSize(fieldNumber) + computeSInt32SizeNoTag(value);
    }

    public static int computeFixed32Size(int fieldNumber, int value) {
        return computeTagSize(fieldNumber) + computeFixed32SizeNoTag(value);
    }

    public static int computeSFixed32Size(int fieldNumber, int value) {
        return computeTagSize(fieldNumber) + computeSFixed32SizeNoTag(value);
    }

    public static int computeInt64Size(int fieldNumber, long value) {
        return computeTagSize(fieldNumber) + computeInt64SizeNoTag(value);
    }

    public static int computeUInt64Size(int fieldNumber, long value) {
        return computeTagSize(fieldNumber) + computeUInt64SizeNoTag(value);
    }

    public static int computeSInt64Size(int fieldNumber, long value) {
        return computeTagSize(fieldNumber) + computeSInt64SizeNoTag(value);
    }

    public static int computeFixed64Size(int fieldNumber, long value) {
        return computeTagSize(fieldNumber) + computeFixed64SizeNoTag(value);
    }

    public static int computeSFixed64Size(int fieldNumber, long value) {
        return computeTagSize(fieldNumber) + computeSFixed64SizeNoTag(value);
    }

    public static int computeFloatSize(int fieldNumber, float value) {
        return computeTagSize(fieldNumber) + computeFloatSizeNoTag(value);
    }

    public static int computeDoubleSize(int fieldNumber, double value) {
        return computeTagSize(fieldNumber) + computeDoubleSizeNoTag(value);
    }

    public static int computeBoolSize(int fieldNumber, boolean value) {
        return computeTagSize(fieldNumber) + computeBoolSizeNoTag(value);
    }

    public static int computeEnumSize(int fieldNumber, int value) {
        return computeTagSize(fieldNumber) + computeEnumSizeNoTag(value);
    }

    public static int computeStringSize(int fieldNumber, String value) {
        return computeTagSize(fieldNumber) + computeStringSizeNoTag(value);
    }

    public static int computeBytesSize(int fieldNumber, ByteString value) {
        return computeTagSize(fieldNumber) + computeBytesSizeNoTag(value);
    }

    public static int computeByteArraySize(int fieldNumber, byte[] value) {
        return computeTagSize(fieldNumber) + computeByteArraySizeNoTag(value);
    }

    public static int computeByteBufferSize(int fieldNumber, ByteBuffer value) {
        return computeTagSize(fieldNumber) + computeByteBufferSizeNoTag(value);
    }

    public static int computeLazyFieldSize(int fieldNumber, LazyFieldLite value) {
        return computeTagSize(fieldNumber) + computeLazyFieldSizeNoTag(value);
    }

    public static int computeMessageSize(int fieldNumber, MessageLite value) {
        return computeTagSize(fieldNumber) + computeMessageSizeNoTag(value);
    }

    static int computeMessageSize(int fieldNumber, MessageLite value, Schema schema) {
        return computeTagSize(fieldNumber) + computeMessageSizeNoTag(value, schema);
    }

    public static int computeMessageSetExtensionSize(int fieldNumber, MessageLite value) {
        return (computeTagSize(1) * 2) + computeUInt32Size(2, fieldNumber) + computeMessageSize(3, value);
    }

    public static int computeRawMessageSetExtensionSize(int fieldNumber, ByteString value) {
        return (computeTagSize(1) * 2) + computeUInt32Size(2, fieldNumber) + computeBytesSize(3, value);
    }

    public static int computeLazyFieldMessageSetExtensionSize(int fieldNumber, LazyFieldLite value) {
        return (computeTagSize(1) * 2) + computeUInt32Size(2, fieldNumber) + computeLazyFieldSize(3, value);
    }

    public static int computeTagSize(int fieldNumber) {
        return computeUInt32SizeNoTag(WireFormat.makeTag(fieldNumber, 0));
    }

    public static int computeInt32SizeNoTag(int value) {
        return computeUInt64SizeNoTag((long) value);
    }

    public static int computeUInt32SizeNoTag(int value) {
        return (352 - (Integer.numberOfLeadingZeros(value) * 9)) >>> 6;
    }

    public static int computeSInt32SizeNoTag(int value) {
        return computeUInt32SizeNoTag(encodeZigZag32(value));
    }

    public static int computeFixed32SizeNoTag(int unused) {
        return 4;
    }

    public static int computeSFixed32SizeNoTag(int unused) {
        return 4;
    }

    public static int computeInt64SizeNoTag(long value) {
        return computeUInt64SizeNoTag(value);
    }

    public static int computeUInt64SizeNoTag(long value) {
        return (640 - (Long.numberOfLeadingZeros(value) * 9)) >>> 6;
    }

    public static int computeSInt64SizeNoTag(long value) {
        return computeUInt64SizeNoTag(encodeZigZag64(value));
    }

    public static int computeFixed64SizeNoTag(long unused) {
        return 8;
    }

    public static int computeSFixed64SizeNoTag(long unused) {
        return 8;
    }

    public static int computeFloatSizeNoTag(float unused) {
        return 4;
    }

    public static int computeDoubleSizeNoTag(double unused) {
        return 8;
    }

    public static int computeBoolSizeNoTag(boolean unused) {
        return 1;
    }

    public static int computeEnumSizeNoTag(int value) {
        return computeInt32SizeNoTag(value);
    }

    public static int computeStringSizeNoTag(String value) {
        int length;
        try {
            length = Utf8.encodedLength(value);
        } catch (Utf8.UnpairedSurrogateException e) {
            length = value.getBytes(Internal.UTF_8).length;
        }
        return computeLengthDelimitedFieldSize(length);
    }

    public static int computeLazyFieldSizeNoTag(LazyFieldLite value) {
        return computeLengthDelimitedFieldSize(value.getSerializedSize());
    }

    public static int computeBytesSizeNoTag(ByteString value) {
        return computeLengthDelimitedFieldSize(value.size());
    }

    public static int computeByteArraySizeNoTag(byte[] value) {
        return computeLengthDelimitedFieldSize(value.length);
    }

    public static int computeByteBufferSizeNoTag(ByteBuffer value) {
        return computeLengthDelimitedFieldSize(value.capacity());
    }

    public static int computeMessageSizeNoTag(MessageLite value) {
        return computeLengthDelimitedFieldSize(value.getSerializedSize());
    }

    static int computeMessageSizeNoTag(MessageLite value, Schema schema) {
        return computeLengthDelimitedFieldSize(((AbstractMessageLite) value).getSerializedSize(schema));
    }

    static int computeLengthDelimitedFieldSize(int fieldLength) {
        return computeUInt32SizeNoTag(fieldLength) + fieldLength;
    }

    public static int encodeZigZag32(int n) {
        return (n << 1) ^ (n >> 31);
    }

    public static long encodeZigZag64(long n) {
        return (n << 1) ^ (n >> 63);
    }

    public final void checkNoSpaceLeft() {
        if (spaceLeft() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    public static class OutOfSpaceException extends IOException {
        private static final String MESSAGE = "CodedOutputStream was writing to a flat byte array and ran out of space.";
        private static final long serialVersionUID = -6947486886997889499L;

        OutOfSpaceException() {
            super(MESSAGE);
        }

        OutOfSpaceException(String explanationMessage) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.: " + explanationMessage);
        }

        OutOfSpaceException(Throwable cause) {
            super(MESSAGE, cause);
        }

        OutOfSpaceException(String explanationMessage, Throwable cause) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.: " + explanationMessage, cause);
        }
    }

    /* access modifiers changed from: package-private */
    public final void inefficientWriteStringNoTag(String value, Utf8.UnpairedSurrogateException cause) throws IOException {
        logger.log(Level.WARNING, "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", cause);
        byte[] bytes = value.getBytes(Internal.UTF_8);
        try {
            writeUInt32NoTag(bytes.length);
            writeLazy(bytes, 0, bytes.length);
        } catch (IndexOutOfBoundsException e) {
            throw new OutOfSpaceException((Throwable) e);
        }
    }

    @Deprecated
    public final void writeGroup(int fieldNumber, MessageLite value) throws IOException {
        writeTag(fieldNumber, 3);
        writeGroupNoTag(value);
        writeTag(fieldNumber, 4);
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public final void writeGroup(int fieldNumber, MessageLite value, Schema schema) throws IOException {
        writeTag(fieldNumber, 3);
        writeGroupNoTag(value, schema);
        writeTag(fieldNumber, 4);
    }

    @Deprecated
    public final void writeGroupNoTag(MessageLite value) throws IOException {
        value.writeTo(this);
    }

    /* access modifiers changed from: package-private */
    @Deprecated
    public final void writeGroupNoTag(MessageLite value, Schema schema) throws IOException {
        schema.writeTo(value, this.wrapper);
    }

    @Deprecated
    public static int computeGroupSize(int fieldNumber, MessageLite value) {
        return (computeTagSize(fieldNumber) * 2) + value.getSerializedSize();
    }

    @Deprecated
    static int computeGroupSize(int fieldNumber, MessageLite value, Schema schema) {
        return (computeTagSize(fieldNumber) * 2) + computeGroupSizeNoTag(value, schema);
    }

    @Deprecated
    public static int computeGroupSizeNoTag(MessageLite value) {
        return value.getSerializedSize();
    }

    @Deprecated
    static int computeGroupSizeNoTag(MessageLite value, Schema schema) {
        return ((AbstractMessageLite) value).getSerializedSize(schema);
    }

    @Deprecated
    public final void writeRawVarint32(int value) throws IOException {
        writeUInt32NoTag(value);
    }

    @Deprecated
    public final void writeRawVarint64(long value) throws IOException {
        writeUInt64NoTag(value);
    }

    @Deprecated
    public static int computeRawVarint32Size(int value) {
        return computeUInt32SizeNoTag(value);
    }

    @Deprecated
    public static int computeRawVarint64Size(long value) {
        return computeUInt64SizeNoTag(value);
    }

    @Deprecated
    public final void writeRawLittleEndian32(int value) throws IOException {
        writeFixed32NoTag(value);
    }

    @Deprecated
    public final void writeRawLittleEndian64(long value) throws IOException {
        writeFixed64NoTag(value);
    }

    private static class ArrayEncoder extends CodedOutputStream {
        private final byte[] buffer;
        private final int limit;
        private final int offset;
        private int position;

        ArrayEncoder(byte[] buffer2, int offset2, int length) {
            super();
            if (buffer2 == null) {
                throw new NullPointerException("buffer");
            } else if ((offset2 | length | (buffer2.length - (offset2 + length))) >= 0) {
                this.buffer = buffer2;
                this.offset = offset2;
                this.position = offset2;
                this.limit = offset2 + length;
            } else {
                throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", new Object[]{Integer.valueOf(buffer2.length), Integer.valueOf(offset2), Integer.valueOf(length)}));
            }
        }

        public final void writeTag(int fieldNumber, int wireType) throws IOException {
            writeUInt32NoTag(WireFormat.makeTag(fieldNumber, wireType));
        }

        public final void writeInt32(int fieldNumber, int value) throws IOException {
            writeTag(fieldNumber, 0);
            writeInt32NoTag(value);
        }

        public final void writeUInt32(int fieldNumber, int value) throws IOException {
            writeTag(fieldNumber, 0);
            writeUInt32NoTag(value);
        }

        public final void writeFixed32(int fieldNumber, int value) throws IOException {
            writeTag(fieldNumber, 5);
            writeFixed32NoTag(value);
        }

        public final void writeUInt64(int fieldNumber, long value) throws IOException {
            writeTag(fieldNumber, 0);
            writeUInt64NoTag(value);
        }

        public final void writeFixed64(int fieldNumber, long value) throws IOException {
            writeTag(fieldNumber, 1);
            writeFixed64NoTag(value);
        }

        public final void writeBool(int fieldNumber, boolean value) throws IOException {
            writeTag(fieldNumber, 0);
            write(value ? (byte) 1 : 0);
        }

        public final void writeString(int fieldNumber, String value) throws IOException {
            writeTag(fieldNumber, 2);
            writeStringNoTag(value);
        }

        public final void writeBytes(int fieldNumber, ByteString value) throws IOException {
            writeTag(fieldNumber, 2);
            writeBytesNoTag(value);
        }

        public final void writeByteArray(int fieldNumber, byte[] value) throws IOException {
            writeByteArray(fieldNumber, value, 0, value.length);
        }

        public final void writeByteArray(int fieldNumber, byte[] value, int offset2, int length) throws IOException {
            writeTag(fieldNumber, 2);
            writeByteArrayNoTag(value, offset2, length);
        }

        public final void writeByteBuffer(int fieldNumber, ByteBuffer value) throws IOException {
            writeTag(fieldNumber, 2);
            writeUInt32NoTag(value.capacity());
            writeRawBytes(value);
        }

        public final void writeBytesNoTag(ByteString value) throws IOException {
            writeUInt32NoTag(value.size());
            value.writeTo((ByteOutput) this);
        }

        public final void writeByteArrayNoTag(byte[] value, int offset2, int length) throws IOException {
            writeUInt32NoTag(length);
            write(value, offset2, length);
        }

        public final void writeRawBytes(ByteBuffer value) throws IOException {
            if (value.hasArray()) {
                write(value.array(), value.arrayOffset(), value.capacity());
                return;
            }
            ByteBuffer duplicated = value.duplicate();
            Java8Compatibility.clear(duplicated);
            write(duplicated);
        }

        public final void writeMessage(int fieldNumber, MessageLite value) throws IOException {
            writeTag(fieldNumber, 2);
            writeMessageNoTag(value);
        }

        /* access modifiers changed from: package-private */
        public final void writeMessage(int fieldNumber, MessageLite value, Schema schema) throws IOException {
            writeTag(fieldNumber, 2);
            writeUInt32NoTag(((AbstractMessageLite) value).getSerializedSize(schema));
            schema.writeTo(value, this.wrapper);
        }

        public final void writeMessageSetExtension(int fieldNumber, MessageLite value) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, fieldNumber);
            writeMessage(3, value);
            writeTag(1, 4);
        }

        public final void writeRawMessageSetExtension(int fieldNumber, ByteString value) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, fieldNumber);
            writeBytes(3, value);
            writeTag(1, 4);
        }

        public final void writeMessageNoTag(MessageLite value) throws IOException {
            writeUInt32NoTag(value.getSerializedSize());
            value.writeTo((CodedOutputStream) this);
        }

        /* access modifiers changed from: package-private */
        public final void writeMessageNoTag(MessageLite value, Schema schema) throws IOException {
            writeUInt32NoTag(((AbstractMessageLite) value).getSerializedSize(schema));
            schema.writeTo(value, this.wrapper);
        }

        public final void write(byte value) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = value;
            } catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), 1}), e);
            }
        }

        public final void writeInt32NoTag(int value) throws IOException {
            if (value >= 0) {
                writeUInt32NoTag(value);
            } else {
                writeUInt64NoTag((long) value);
            }
        }

        public final void writeUInt32NoTag(int value) throws IOException {
            while ((value & -128) != 0) {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) ((value | 128) & 255);
                value >>>= 7;
            }
            try {
                byte[] bArr2 = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr2[i2] = (byte) value;
            } catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), 1}), e);
            }
        }

        public final void writeFixed32NoTag(int value) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) (value & 255);
                byte[] bArr2 = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr2[i2] = (byte) ((value >> 8) & 255);
                byte[] bArr3 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                bArr3[i3] = (byte) ((value >> 16) & 255);
                byte[] bArr4 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                bArr4[i4] = (byte) ((value >> 24) & 255);
            } catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), 1}), e);
            }
        }

        public final void writeUInt64NoTag(long value) throws IOException {
            if (!CodedOutputStream.HAS_UNSAFE_ARRAY_OPERATIONS || spaceLeft() < 10) {
                while ((value & -128) != 0) {
                    byte[] bArr = this.buffer;
                    int i = this.position;
                    this.position = i + 1;
                    bArr[i] = (byte) ((((int) value) | 128) & 255);
                    value >>>= 7;
                }
                try {
                    byte[] bArr2 = this.buffer;
                    int i2 = this.position;
                    this.position = i2 + 1;
                    bArr2[i2] = (byte) ((int) value);
                } catch (IndexOutOfBoundsException e) {
                    throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), 1}), e);
                }
            } else {
                while ((value & -128) != 0) {
                    byte[] bArr3 = this.buffer;
                    int i3 = this.position;
                    this.position = i3 + 1;
                    UnsafeUtil.putByte(bArr3, (long) i3, (byte) ((((int) value) | 128) & 255));
                    value >>>= 7;
                }
                byte[] bArr4 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                UnsafeUtil.putByte(bArr4, (long) i4, (byte) ((int) value));
            }
        }

        public final void writeFixed64NoTag(long value) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i = this.position;
                this.position = i + 1;
                bArr[i] = (byte) (((int) value) & 255);
                byte[] bArr2 = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr2[i2] = (byte) (((int) (value >> 8)) & 255);
                byte[] bArr3 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                bArr3[i3] = (byte) (((int) (value >> 16)) & 255);
                byte[] bArr4 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                bArr4[i4] = (byte) (((int) (value >> 24)) & 255);
                byte[] bArr5 = this.buffer;
                int i5 = this.position;
                this.position = i5 + 1;
                bArr5[i5] = (byte) (((int) (value >> 32)) & 255);
                byte[] bArr6 = this.buffer;
                int i6 = this.position;
                this.position = i6 + 1;
                bArr6[i6] = (byte) (((int) (value >> 40)) & 255);
                byte[] bArr7 = this.buffer;
                int i7 = this.position;
                this.position = i7 + 1;
                bArr7[i7] = (byte) (((int) (value >> 48)) & 255);
                byte[] bArr8 = this.buffer;
                int i8 = this.position;
                this.position = i8 + 1;
                bArr8[i8] = (byte) (((int) (value >> 56)) & 255);
            } catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), 1}), e);
            }
        }

        public final void write(byte[] value, int offset2, int length) throws IOException {
            try {
                System.arraycopy(value, offset2, this.buffer, this.position, length);
                this.position += length;
            } catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(length)}), e);
            }
        }

        public final void writeLazy(byte[] value, int offset2, int length) throws IOException {
            write(value, offset2, length);
        }

        public final void write(ByteBuffer value) throws IOException {
            int length = value.remaining();
            try {
                value.get(this.buffer, this.position, length);
                this.position += length;
            } catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(length)}), e);
            }
        }

        public final void writeLazy(ByteBuffer value) throws IOException {
            write(value);
        }

        public final void writeStringNoTag(String value) throws IOException {
            int oldPosition = this.position;
            try {
                int maxLengthVarIntSize = computeUInt32SizeNoTag(value.length() * 3);
                int minLengthVarIntSize = computeUInt32SizeNoTag(value.length());
                if (minLengthVarIntSize == maxLengthVarIntSize) {
                    this.position = oldPosition + minLengthVarIntSize;
                    int newPosition = Utf8.encode(value, this.buffer, this.position, spaceLeft());
                    this.position = oldPosition;
                    writeUInt32NoTag((newPosition - oldPosition) - minLengthVarIntSize);
                    this.position = newPosition;
                    return;
                }
                writeUInt32NoTag(Utf8.encodedLength(value));
                this.position = Utf8.encode(value, this.buffer, this.position, spaceLeft());
            } catch (Utf8.UnpairedSurrogateException e) {
                this.position = oldPosition;
                inefficientWriteStringNoTag(value, e);
            } catch (IndexOutOfBoundsException e2) {
                throw new OutOfSpaceException((Throwable) e2);
            }
        }

        public void flush() {
        }

        public final int spaceLeft() {
            return this.limit - this.position;
        }

        public final int getTotalBytesWritten() {
            return this.position - this.offset;
        }
    }

    private static final class HeapNioEncoder extends ArrayEncoder {
        private final ByteBuffer byteBuffer;
        private int initialPosition;

        HeapNioEncoder(ByteBuffer byteBuffer2) {
            super(byteBuffer2.array(), byteBuffer2.arrayOffset() + byteBuffer2.position(), byteBuffer2.remaining());
            this.byteBuffer = byteBuffer2;
            this.initialPosition = byteBuffer2.position();
        }

        public void flush() {
            Java8Compatibility.position(this.byteBuffer, this.initialPosition + getTotalBytesWritten());
        }
    }

    private static final class SafeDirectNioEncoder extends CodedOutputStream {
        private final ByteBuffer buffer;
        private final int initialPosition;
        private final ByteBuffer originalBuffer;

        SafeDirectNioEncoder(ByteBuffer buffer2) {
            super();
            this.originalBuffer = buffer2;
            this.buffer = buffer2.duplicate().order(ByteOrder.LITTLE_ENDIAN);
            this.initialPosition = buffer2.position();
        }

        public void writeTag(int fieldNumber, int wireType) throws IOException {
            writeUInt32NoTag(WireFormat.makeTag(fieldNumber, wireType));
        }

        public void writeInt32(int fieldNumber, int value) throws IOException {
            writeTag(fieldNumber, 0);
            writeInt32NoTag(value);
        }

        public void writeUInt32(int fieldNumber, int value) throws IOException {
            writeTag(fieldNumber, 0);
            writeUInt32NoTag(value);
        }

        public void writeFixed32(int fieldNumber, int value) throws IOException {
            writeTag(fieldNumber, 5);
            writeFixed32NoTag(value);
        }

        public void writeUInt64(int fieldNumber, long value) throws IOException {
            writeTag(fieldNumber, 0);
            writeUInt64NoTag(value);
        }

        public void writeFixed64(int fieldNumber, long value) throws IOException {
            writeTag(fieldNumber, 1);
            writeFixed64NoTag(value);
        }

        public void writeBool(int fieldNumber, boolean value) throws IOException {
            writeTag(fieldNumber, 0);
            write(value ? (byte) 1 : 0);
        }

        public void writeString(int fieldNumber, String value) throws IOException {
            writeTag(fieldNumber, 2);
            writeStringNoTag(value);
        }

        public void writeBytes(int fieldNumber, ByteString value) throws IOException {
            writeTag(fieldNumber, 2);
            writeBytesNoTag(value);
        }

        public void writeByteArray(int fieldNumber, byte[] value) throws IOException {
            writeByteArray(fieldNumber, value, 0, value.length);
        }

        public void writeByteArray(int fieldNumber, byte[] value, int offset, int length) throws IOException {
            writeTag(fieldNumber, 2);
            writeByteArrayNoTag(value, offset, length);
        }

        public void writeByteBuffer(int fieldNumber, ByteBuffer value) throws IOException {
            writeTag(fieldNumber, 2);
            writeUInt32NoTag(value.capacity());
            writeRawBytes(value);
        }

        public void writeMessage(int fieldNumber, MessageLite value) throws IOException {
            writeTag(fieldNumber, 2);
            writeMessageNoTag(value);
        }

        /* access modifiers changed from: package-private */
        public void writeMessage(int fieldNumber, MessageLite value, Schema schema) throws IOException {
            writeTag(fieldNumber, 2);
            writeMessageNoTag(value, schema);
        }

        public void writeMessageSetExtension(int fieldNumber, MessageLite value) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, fieldNumber);
            writeMessage(3, value);
            writeTag(1, 4);
        }

        public void writeRawMessageSetExtension(int fieldNumber, ByteString value) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, fieldNumber);
            writeBytes(3, value);
            writeTag(1, 4);
        }

        public void writeMessageNoTag(MessageLite value) throws IOException {
            writeUInt32NoTag(value.getSerializedSize());
            value.writeTo((CodedOutputStream) this);
        }

        /* access modifiers changed from: package-private */
        public void writeMessageNoTag(MessageLite value, Schema schema) throws IOException {
            writeUInt32NoTag(((AbstractMessageLite) value).getSerializedSize(schema));
            schema.writeTo(value, this.wrapper);
        }

        public void write(byte value) throws IOException {
            try {
                this.buffer.put(value);
            } catch (BufferOverflowException e) {
                throw new OutOfSpaceException((Throwable) e);
            }
        }

        public void writeBytesNoTag(ByteString value) throws IOException {
            writeUInt32NoTag(value.size());
            value.writeTo((ByteOutput) this);
        }

        public void writeByteArrayNoTag(byte[] value, int offset, int length) throws IOException {
            writeUInt32NoTag(length);
            write(value, offset, length);
        }

        public void writeRawBytes(ByteBuffer value) throws IOException {
            if (value.hasArray()) {
                write(value.array(), value.arrayOffset(), value.capacity());
                return;
            }
            ByteBuffer duplicated = value.duplicate();
            Java8Compatibility.clear(duplicated);
            write(duplicated);
        }

        public void writeInt32NoTag(int value) throws IOException {
            if (value >= 0) {
                writeUInt32NoTag(value);
            } else {
                writeUInt64NoTag((long) value);
            }
        }

        public void writeUInt32NoTag(int value) throws IOException {
            while ((value & -128) != 0) {
                this.buffer.put((byte) ((value | 128) & 255));
                value >>>= 7;
            }
            try {
                this.buffer.put((byte) value);
            } catch (BufferOverflowException e) {
                throw new OutOfSpaceException((Throwable) e);
            }
        }

        public void writeFixed32NoTag(int value) throws IOException {
            try {
                this.buffer.putInt(value);
            } catch (BufferOverflowException e) {
                throw new OutOfSpaceException((Throwable) e);
            }
        }

        public void writeUInt64NoTag(long value) throws IOException {
            while ((-128 & value) != 0) {
                this.buffer.put((byte) ((((int) value) | 128) & 255));
                value >>>= 7;
            }
            try {
                this.buffer.put((byte) ((int) value));
            } catch (BufferOverflowException e) {
                throw new OutOfSpaceException((Throwable) e);
            }
        }

        public void writeFixed64NoTag(long value) throws IOException {
            try {
                this.buffer.putLong(value);
            } catch (BufferOverflowException e) {
                throw new OutOfSpaceException((Throwable) e);
            }
        }

        public void write(byte[] value, int offset, int length) throws IOException {
            try {
                this.buffer.put(value, offset, length);
            } catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException((Throwable) e);
            } catch (BufferOverflowException e2) {
                throw new OutOfSpaceException((Throwable) e2);
            }
        }

        public void writeLazy(byte[] value, int offset, int length) throws IOException {
            write(value, offset, length);
        }

        public void write(ByteBuffer value) throws IOException {
            try {
                this.buffer.put(value);
            } catch (BufferOverflowException e) {
                throw new OutOfSpaceException((Throwable) e);
            }
        }

        public void writeLazy(ByteBuffer value) throws IOException {
            write(value);
        }

        public void writeStringNoTag(String value) throws IOException {
            int startPos = this.buffer.position();
            try {
                int maxLengthVarIntSize = computeUInt32SizeNoTag(value.length() * 3);
                int minLengthVarIntSize = computeUInt32SizeNoTag(value.length());
                if (minLengthVarIntSize == maxLengthVarIntSize) {
                    int startOfBytes = this.buffer.position() + minLengthVarIntSize;
                    Java8Compatibility.position(this.buffer, startOfBytes);
                    encode(value);
                    int endOfBytes = this.buffer.position();
                    Java8Compatibility.position(this.buffer, startPos);
                    writeUInt32NoTag(endOfBytes - startOfBytes);
                    Java8Compatibility.position(this.buffer, endOfBytes);
                    return;
                }
                writeUInt32NoTag(Utf8.encodedLength(value));
                encode(value);
            } catch (Utf8.UnpairedSurrogateException e) {
                Java8Compatibility.position(this.buffer, startPos);
                inefficientWriteStringNoTag(value, e);
            } catch (IllegalArgumentException e2) {
                throw new OutOfSpaceException((Throwable) e2);
            }
        }

        public void flush() {
            Java8Compatibility.position(this.originalBuffer, this.buffer.position());
        }

        public int spaceLeft() {
            return this.buffer.remaining();
        }

        public int getTotalBytesWritten() {
            return this.buffer.position() - this.initialPosition;
        }

        private void encode(String value) throws IOException {
            try {
                Utf8.encodeUtf8(value, this.buffer);
            } catch (IndexOutOfBoundsException e) {
                throw new OutOfSpaceException((Throwable) e);
            }
        }
    }

    private static final class UnsafeDirectNioEncoder extends CodedOutputStream {
        private final long address;
        private final ByteBuffer buffer;
        private final long initialPosition;
        private final long limit;
        private final long oneVarintLimit = (this.limit - 10);
        private final ByteBuffer originalBuffer;
        private long position = this.initialPosition;

        UnsafeDirectNioEncoder(ByteBuffer buffer2) {
            super();
            this.originalBuffer = buffer2;
            this.buffer = buffer2.duplicate().order(ByteOrder.LITTLE_ENDIAN);
            this.address = UnsafeUtil.addressOffset(buffer2);
            this.initialPosition = this.address + ((long) buffer2.position());
            this.limit = this.address + ((long) buffer2.limit());
        }

        static boolean isSupported() {
            return UnsafeUtil.hasUnsafeByteBufferOperations();
        }

        public void writeTag(int fieldNumber, int wireType) throws IOException {
            writeUInt32NoTag(WireFormat.makeTag(fieldNumber, wireType));
        }

        public void writeInt32(int fieldNumber, int value) throws IOException {
            writeTag(fieldNumber, 0);
            writeInt32NoTag(value);
        }

        public void writeUInt32(int fieldNumber, int value) throws IOException {
            writeTag(fieldNumber, 0);
            writeUInt32NoTag(value);
        }

        public void writeFixed32(int fieldNumber, int value) throws IOException {
            writeTag(fieldNumber, 5);
            writeFixed32NoTag(value);
        }

        public void writeUInt64(int fieldNumber, long value) throws IOException {
            writeTag(fieldNumber, 0);
            writeUInt64NoTag(value);
        }

        public void writeFixed64(int fieldNumber, long value) throws IOException {
            writeTag(fieldNumber, 1);
            writeFixed64NoTag(value);
        }

        public void writeBool(int fieldNumber, boolean value) throws IOException {
            writeTag(fieldNumber, 0);
            write(value ? (byte) 1 : 0);
        }

        public void writeString(int fieldNumber, String value) throws IOException {
            writeTag(fieldNumber, 2);
            writeStringNoTag(value);
        }

        public void writeBytes(int fieldNumber, ByteString value) throws IOException {
            writeTag(fieldNumber, 2);
            writeBytesNoTag(value);
        }

        public void writeByteArray(int fieldNumber, byte[] value) throws IOException {
            writeByteArray(fieldNumber, value, 0, value.length);
        }

        public void writeByteArray(int fieldNumber, byte[] value, int offset, int length) throws IOException {
            writeTag(fieldNumber, 2);
            writeByteArrayNoTag(value, offset, length);
        }

        public void writeByteBuffer(int fieldNumber, ByteBuffer value) throws IOException {
            writeTag(fieldNumber, 2);
            writeUInt32NoTag(value.capacity());
            writeRawBytes(value);
        }

        public void writeMessage(int fieldNumber, MessageLite value) throws IOException {
            writeTag(fieldNumber, 2);
            writeMessageNoTag(value);
        }

        /* access modifiers changed from: package-private */
        public void writeMessage(int fieldNumber, MessageLite value, Schema schema) throws IOException {
            writeTag(fieldNumber, 2);
            writeMessageNoTag(value, schema);
        }

        public void writeMessageSetExtension(int fieldNumber, MessageLite value) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, fieldNumber);
            writeMessage(3, value);
            writeTag(1, 4);
        }

        public void writeRawMessageSetExtension(int fieldNumber, ByteString value) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, fieldNumber);
            writeBytes(3, value);
            writeTag(1, 4);
        }

        public void writeMessageNoTag(MessageLite value) throws IOException {
            writeUInt32NoTag(value.getSerializedSize());
            value.writeTo((CodedOutputStream) this);
        }

        /* access modifiers changed from: package-private */
        public void writeMessageNoTag(MessageLite value, Schema schema) throws IOException {
            writeUInt32NoTag(((AbstractMessageLite) value).getSerializedSize(schema));
            schema.writeTo(value, this.wrapper);
        }

        public void write(byte value) throws IOException {
            if (this.position < this.limit) {
                long j = this.position;
                this.position = 1 + j;
                UnsafeUtil.putByte(j, value);
                return;
            }
            throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Long.valueOf(this.position), Long.valueOf(this.limit), 1}));
        }

        public void writeBytesNoTag(ByteString value) throws IOException {
            writeUInt32NoTag(value.size());
            value.writeTo((ByteOutput) this);
        }

        public void writeByteArrayNoTag(byte[] value, int offset, int length) throws IOException {
            writeUInt32NoTag(length);
            write(value, offset, length);
        }

        public void writeRawBytes(ByteBuffer value) throws IOException {
            if (value.hasArray()) {
                write(value.array(), value.arrayOffset(), value.capacity());
                return;
            }
            ByteBuffer duplicated = value.duplicate();
            Java8Compatibility.clear(duplicated);
            write(duplicated);
        }

        public void writeInt32NoTag(int value) throws IOException {
            if (value >= 0) {
                writeUInt32NoTag(value);
            } else {
                writeUInt64NoTag((long) value);
            }
        }

        public void writeUInt32NoTag(int value) throws IOException {
            if (this.position <= this.oneVarintLimit) {
                while ((value & -128) != 0) {
                    long j = this.position;
                    this.position = j + 1;
                    UnsafeUtil.putByte(j, (byte) ((value | 128) & 255));
                    value >>>= 7;
                }
                long j2 = this.position;
                this.position = 1 + j2;
                UnsafeUtil.putByte(j2, (byte) value);
                return;
            }
            while (this.position < this.limit) {
                if ((value & -128) == 0) {
                    long j3 = this.position;
                    this.position = 1 + j3;
                    UnsafeUtil.putByte(j3, (byte) value);
                    return;
                }
                long j4 = this.position;
                this.position = j4 + 1;
                UnsafeUtil.putByte(j4, (byte) ((value | 128) & 255));
                value >>>= 7;
            }
            throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Long.valueOf(this.position), Long.valueOf(this.limit), 1}));
        }

        public void writeFixed32NoTag(int value) throws IOException {
            this.buffer.putInt(bufferPos(this.position), value);
            this.position += 4;
        }

        public void writeUInt64NoTag(long value) throws IOException {
            if (this.position <= this.oneVarintLimit) {
                while ((value & -128) != 0) {
                    long j = this.position;
                    this.position = j + 1;
                    UnsafeUtil.putByte(j, (byte) ((((int) value) | 128) & 255));
                    value >>>= 7;
                }
                long j2 = this.position;
                this.position = 1 + j2;
                UnsafeUtil.putByte(j2, (byte) ((int) value));
                return;
            }
            while (this.position < this.limit) {
                if ((value & -128) == 0) {
                    long j3 = this.position;
                    this.position = 1 + j3;
                    UnsafeUtil.putByte(j3, (byte) ((int) value));
                    return;
                }
                long j4 = this.position;
                this.position = j4 + 1;
                UnsafeUtil.putByte(j4, (byte) ((((int) value) | 128) & 255));
                value >>>= 7;
            }
            throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Long.valueOf(this.position), Long.valueOf(this.limit), 1}));
        }

        public void writeFixed64NoTag(long value) throws IOException {
            this.buffer.putLong(bufferPos(this.position), value);
            this.position += 8;
        }

        public void write(byte[] value, int offset, int length) throws IOException {
            byte[] value2;
            if (value == null || offset < 0 || length < 0 || value.length - length < offset) {
                value2 = value;
            } else if (this.limit - ((long) length) < this.position) {
                value2 = value;
            } else {
                UnsafeUtil.copyMemory(value, (long) offset, this.position, (long) length);
                this.position += (long) length;
                return;
            }
            if (value2 == null) {
                throw new NullPointerException(Values.VECTOR_MAP_VECTORS_KEY);
            }
            throw new OutOfSpaceException(String.format("Pos: %d, limit: %d, len: %d", new Object[]{Long.valueOf(this.position), Long.valueOf(this.limit), Integer.valueOf(length)}));
        }

        public void writeLazy(byte[] value, int offset, int length) throws IOException {
            write(value, offset, length);
        }

        public void write(ByteBuffer value) throws IOException {
            try {
                int length = value.remaining();
                repositionBuffer(this.position);
                this.buffer.put(value);
                this.position += (long) length;
            } catch (BufferOverflowException e) {
                throw new OutOfSpaceException((Throwable) e);
            }
        }

        public void writeLazy(ByteBuffer value) throws IOException {
            write(value);
        }

        public void writeStringNoTag(String value) throws IOException {
            long prevPos = this.position;
            try {
                int maxLengthVarIntSize = computeUInt32SizeNoTag(value.length() * 3);
                int minLengthVarIntSize = computeUInt32SizeNoTag(value.length());
                if (minLengthVarIntSize == maxLengthVarIntSize) {
                    int stringStart = bufferPos(this.position) + minLengthVarIntSize;
                    Java8Compatibility.position(this.buffer, stringStart);
                    Utf8.encodeUtf8(value, this.buffer);
                    int length = this.buffer.position() - stringStart;
                    writeUInt32NoTag(length);
                    this.position += (long) length;
                    return;
                }
                int length2 = Utf8.encodedLength(value);
                writeUInt32NoTag(length2);
                repositionBuffer(this.position);
                Utf8.encodeUtf8(value, this.buffer);
                this.position += (long) length2;
            } catch (Utf8.UnpairedSurrogateException e) {
                this.position = prevPos;
                repositionBuffer(this.position);
                inefficientWriteStringNoTag(value, e);
            } catch (IllegalArgumentException e2) {
                throw new OutOfSpaceException((Throwable) e2);
            } catch (IndexOutOfBoundsException e3) {
                throw new OutOfSpaceException((Throwable) e3);
            }
        }

        public void flush() {
            Java8Compatibility.position(this.originalBuffer, bufferPos(this.position));
        }

        public int spaceLeft() {
            return (int) (this.limit - this.position);
        }

        public int getTotalBytesWritten() {
            return (int) (this.position - this.initialPosition);
        }

        private void repositionBuffer(long pos) {
            Java8Compatibility.position(this.buffer, bufferPos(pos));
        }

        private int bufferPos(long pos) {
            return (int) (pos - this.address);
        }
    }

    private static abstract class AbstractBufferedEncoder extends CodedOutputStream {
        final byte[] buffer;
        final int limit;
        int position;
        int totalBytesWritten;

        AbstractBufferedEncoder(int bufferSize) {
            super();
            if (bufferSize >= 0) {
                this.buffer = new byte[Math.max(bufferSize, 20)];
                this.limit = this.buffer.length;
                return;
            }
            throw new IllegalArgumentException("bufferSize must be >= 0");
        }

        public final int spaceLeft() {
            throw new UnsupportedOperationException("spaceLeft() can only be called on CodedOutputStreams that are writing to a flat array or ByteBuffer.");
        }

        public final int getTotalBytesWritten() {
            return this.totalBytesWritten;
        }

        /* access modifiers changed from: package-private */
        public final void buffer(byte value) {
            byte[] bArr = this.buffer;
            int i = this.position;
            this.position = i + 1;
            bArr[i] = value;
            this.totalBytesWritten++;
        }

        /* access modifiers changed from: package-private */
        public final void bufferTag(int fieldNumber, int wireType) {
            bufferUInt32NoTag(WireFormat.makeTag(fieldNumber, wireType));
        }

        /* access modifiers changed from: package-private */
        public final void bufferInt32NoTag(int value) {
            if (value >= 0) {
                bufferUInt32NoTag(value);
            } else {
                bufferUInt64NoTag((long) value);
            }
        }

        /* access modifiers changed from: package-private */
        public final void bufferUInt32NoTag(int value) {
            if (CodedOutputStream.HAS_UNSAFE_ARRAY_OPERATIONS) {
                long originalPos = (long) this.position;
                while ((value & -128) != 0) {
                    byte[] bArr = this.buffer;
                    int i = this.position;
                    this.position = i + 1;
                    UnsafeUtil.putByte(bArr, (long) i, (byte) ((value | 128) & 255));
                    value >>>= 7;
                }
                byte[] bArr2 = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                UnsafeUtil.putByte(bArr2, (long) i2, (byte) value);
                this.totalBytesWritten += (int) (((long) this.position) - originalPos);
                return;
            }
            while ((value & -128) != 0) {
                byte[] bArr3 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                bArr3[i3] = (byte) ((value | 128) & 255);
                this.totalBytesWritten++;
                value >>>= 7;
            }
            byte[] bArr4 = this.buffer;
            int i4 = this.position;
            this.position = i4 + 1;
            bArr4[i4] = (byte) value;
            this.totalBytesWritten++;
        }

        /* access modifiers changed from: package-private */
        public final void bufferUInt64NoTag(long value) {
            if (CodedOutputStream.HAS_UNSAFE_ARRAY_OPERATIONS) {
                long originalPos = (long) this.position;
                while ((value & -128) != 0) {
                    byte[] bArr = this.buffer;
                    int i = this.position;
                    this.position = i + 1;
                    UnsafeUtil.putByte(bArr, (long) i, (byte) ((((int) value) | 128) & 255));
                    value >>>= 7;
                }
                byte[] bArr2 = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                UnsafeUtil.putByte(bArr2, (long) i2, (byte) ((int) value));
                this.totalBytesWritten += (int) (((long) this.position) - originalPos);
                return;
            }
            while ((value & -128) != 0) {
                byte[] bArr3 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                bArr3[i3] = (byte) ((((int) value) | 128) & 255);
                this.totalBytesWritten++;
                value >>>= 7;
            }
            byte[] bArr4 = this.buffer;
            int i4 = this.position;
            this.position = i4 + 1;
            bArr4[i4] = (byte) ((int) value);
            this.totalBytesWritten++;
        }

        /* access modifiers changed from: package-private */
        public final void bufferFixed32NoTag(int value) {
            byte[] bArr = this.buffer;
            int i = this.position;
            this.position = i + 1;
            bArr[i] = (byte) (value & 255);
            byte[] bArr2 = this.buffer;
            int i2 = this.position;
            this.position = i2 + 1;
            bArr2[i2] = (byte) ((value >> 8) & 255);
            byte[] bArr3 = this.buffer;
            int i3 = this.position;
            this.position = i3 + 1;
            bArr3[i3] = (byte) ((value >> 16) & 255);
            byte[] bArr4 = this.buffer;
            int i4 = this.position;
            this.position = i4 + 1;
            bArr4[i4] = (byte) ((value >> 24) & 255);
            this.totalBytesWritten += 4;
        }

        /* access modifiers changed from: package-private */
        public final void bufferFixed64NoTag(long value) {
            byte[] bArr = this.buffer;
            int i = this.position;
            this.position = i + 1;
            bArr[i] = (byte) ((int) (value & 255));
            byte[] bArr2 = this.buffer;
            int i2 = this.position;
            this.position = i2 + 1;
            bArr2[i2] = (byte) ((int) ((value >> 8) & 255));
            byte[] bArr3 = this.buffer;
            int i3 = this.position;
            this.position = i3 + 1;
            bArr3[i3] = (byte) ((int) ((value >> 16) & 255));
            byte[] bArr4 = this.buffer;
            int i4 = this.position;
            this.position = i4 + 1;
            bArr4[i4] = (byte) ((int) (255 & (value >> 24)));
            byte[] bArr5 = this.buffer;
            int i5 = this.position;
            this.position = i5 + 1;
            bArr5[i5] = (byte) (((int) (value >> 32)) & 255);
            byte[] bArr6 = this.buffer;
            int i6 = this.position;
            this.position = i6 + 1;
            bArr6[i6] = (byte) (((int) (value >> 40)) & 255);
            byte[] bArr7 = this.buffer;
            int i7 = this.position;
            this.position = i7 + 1;
            bArr7[i7] = (byte) (((int) (value >> 48)) & 255);
            byte[] bArr8 = this.buffer;
            int i8 = this.position;
            this.position = i8 + 1;
            bArr8[i8] = (byte) (((int) (value >> 56)) & 255);
            this.totalBytesWritten += 8;
        }
    }

    private static final class ByteOutputEncoder extends AbstractBufferedEncoder {
        private final ByteOutput out;

        ByteOutputEncoder(ByteOutput out2, int bufferSize) {
            super(bufferSize);
            if (out2 != null) {
                this.out = out2;
                return;
            }
            throw new NullPointerException("out");
        }

        public void writeTag(int fieldNumber, int wireType) throws IOException {
            writeUInt32NoTag(WireFormat.makeTag(fieldNumber, wireType));
        }

        public void writeInt32(int fieldNumber, int value) throws IOException {
            flushIfNotAvailable(20);
            bufferTag(fieldNumber, 0);
            bufferInt32NoTag(value);
        }

        public void writeUInt32(int fieldNumber, int value) throws IOException {
            flushIfNotAvailable(20);
            bufferTag(fieldNumber, 0);
            bufferUInt32NoTag(value);
        }

        public void writeFixed32(int fieldNumber, int value) throws IOException {
            flushIfNotAvailable(14);
            bufferTag(fieldNumber, 5);
            bufferFixed32NoTag(value);
        }

        public void writeUInt64(int fieldNumber, long value) throws IOException {
            flushIfNotAvailable(20);
            bufferTag(fieldNumber, 0);
            bufferUInt64NoTag(value);
        }

        public void writeFixed64(int fieldNumber, long value) throws IOException {
            flushIfNotAvailable(18);
            bufferTag(fieldNumber, 1);
            bufferFixed64NoTag(value);
        }

        public void writeBool(int fieldNumber, boolean value) throws IOException {
            flushIfNotAvailable(11);
            bufferTag(fieldNumber, 0);
            buffer(value ? (byte) 1 : 0);
        }

        public void writeString(int fieldNumber, String value) throws IOException {
            writeTag(fieldNumber, 2);
            writeStringNoTag(value);
        }

        public void writeBytes(int fieldNumber, ByteString value) throws IOException {
            writeTag(fieldNumber, 2);
            writeBytesNoTag(value);
        }

        public void writeByteArray(int fieldNumber, byte[] value) throws IOException {
            writeByteArray(fieldNumber, value, 0, value.length);
        }

        public void writeByteArray(int fieldNumber, byte[] value, int offset, int length) throws IOException {
            writeTag(fieldNumber, 2);
            writeByteArrayNoTag(value, offset, length);
        }

        public void writeByteBuffer(int fieldNumber, ByteBuffer value) throws IOException {
            writeTag(fieldNumber, 2);
            writeUInt32NoTag(value.capacity());
            writeRawBytes(value);
        }

        public void writeBytesNoTag(ByteString value) throws IOException {
            writeUInt32NoTag(value.size());
            value.writeTo((ByteOutput) this);
        }

        public void writeByteArrayNoTag(byte[] value, int offset, int length) throws IOException {
            writeUInt32NoTag(length);
            write(value, offset, length);
        }

        public void writeRawBytes(ByteBuffer value) throws IOException {
            if (value.hasArray()) {
                write(value.array(), value.arrayOffset(), value.capacity());
                return;
            }
            ByteBuffer duplicated = value.duplicate();
            Java8Compatibility.clear(duplicated);
            write(duplicated);
        }

        public void writeMessage(int fieldNumber, MessageLite value) throws IOException {
            writeTag(fieldNumber, 2);
            writeMessageNoTag(value);
        }

        /* access modifiers changed from: package-private */
        public void writeMessage(int fieldNumber, MessageLite value, Schema schema) throws IOException {
            writeTag(fieldNumber, 2);
            writeMessageNoTag(value, schema);
        }

        public void writeMessageSetExtension(int fieldNumber, MessageLite value) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, fieldNumber);
            writeMessage(3, value);
            writeTag(1, 4);
        }

        public void writeRawMessageSetExtension(int fieldNumber, ByteString value) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, fieldNumber);
            writeBytes(3, value);
            writeTag(1, 4);
        }

        public void writeMessageNoTag(MessageLite value) throws IOException {
            writeUInt32NoTag(value.getSerializedSize());
            value.writeTo((CodedOutputStream) this);
        }

        /* access modifiers changed from: package-private */
        public void writeMessageNoTag(MessageLite value, Schema schema) throws IOException {
            writeUInt32NoTag(((AbstractMessageLite) value).getSerializedSize(schema));
            schema.writeTo(value, this.wrapper);
        }

        public void write(byte value) throws IOException {
            if (this.position == this.limit) {
                doFlush();
            }
            buffer(value);
        }

        public void writeInt32NoTag(int value) throws IOException {
            if (value >= 0) {
                writeUInt32NoTag(value);
            } else {
                writeUInt64NoTag((long) value);
            }
        }

        public void writeUInt32NoTag(int value) throws IOException {
            flushIfNotAvailable(5);
            bufferUInt32NoTag(value);
        }

        public void writeFixed32NoTag(int value) throws IOException {
            flushIfNotAvailable(4);
            bufferFixed32NoTag(value);
        }

        public void writeUInt64NoTag(long value) throws IOException {
            flushIfNotAvailable(10);
            bufferUInt64NoTag(value);
        }

        public void writeFixed64NoTag(long value) throws IOException {
            flushIfNotAvailable(8);
            bufferFixed64NoTag(value);
        }

        public void writeStringNoTag(String value) throws IOException {
            int maxLength = value.length() * 3;
            int maxLengthVarIntSize = computeUInt32SizeNoTag(maxLength);
            if (maxLengthVarIntSize + maxLength > this.limit) {
                byte[] encodedBytes = new byte[maxLength];
                int actualLength = Utf8.encode(value, encodedBytes, 0, maxLength);
                writeUInt32NoTag(actualLength);
                writeLazy(encodedBytes, 0, actualLength);
                return;
            }
            if (maxLengthVarIntSize + maxLength > this.limit - this.position) {
                doFlush();
            }
            int oldPosition = this.position;
            try {
                int minLengthVarIntSize = computeUInt32SizeNoTag(value.length());
                if (minLengthVarIntSize == maxLengthVarIntSize) {
                    this.position = oldPosition + minLengthVarIntSize;
                    int newPosition = Utf8.encode(value, this.buffer, this.position, this.limit - this.position);
                    this.position = oldPosition;
                    int length = (newPosition - oldPosition) - minLengthVarIntSize;
                    bufferUInt32NoTag(length);
                    this.position = newPosition;
                    this.totalBytesWritten += length;
                    return;
                }
                int length2 = Utf8.encodedLength(value);
                bufferUInt32NoTag(length2);
                this.position = Utf8.encode(value, this.buffer, this.position, length2);
                this.totalBytesWritten += length2;
            } catch (Utf8.UnpairedSurrogateException e) {
                this.totalBytesWritten -= this.position - oldPosition;
                this.position = oldPosition;
                inefficientWriteStringNoTag(value, e);
            } catch (IndexOutOfBoundsException e2) {
                throw new OutOfSpaceException((Throwable) e2);
            }
        }

        public void flush() throws IOException {
            if (this.position > 0) {
                doFlush();
            }
        }

        public void write(byte[] value, int offset, int length) throws IOException {
            flush();
            this.out.write(value, offset, length);
            this.totalBytesWritten += length;
        }

        public void writeLazy(byte[] value, int offset, int length) throws IOException {
            flush();
            this.out.writeLazy(value, offset, length);
            this.totalBytesWritten += length;
        }

        public void write(ByteBuffer value) throws IOException {
            flush();
            int length = value.remaining();
            this.out.write(value);
            this.totalBytesWritten += length;
        }

        public void writeLazy(ByteBuffer value) throws IOException {
            flush();
            int length = value.remaining();
            this.out.writeLazy(value);
            this.totalBytesWritten += length;
        }

        private void flushIfNotAvailable(int requiredSize) throws IOException {
            if (this.limit - this.position < requiredSize) {
                doFlush();
            }
        }

        private void doFlush() throws IOException {
            this.out.write(this.buffer, 0, this.position);
            this.position = 0;
        }
    }

    private static final class OutputStreamEncoder extends AbstractBufferedEncoder {
        private final OutputStream out;

        OutputStreamEncoder(OutputStream out2, int bufferSize) {
            super(bufferSize);
            if (out2 != null) {
                this.out = out2;
                return;
            }
            throw new NullPointerException("out");
        }

        public void writeTag(int fieldNumber, int wireType) throws IOException {
            writeUInt32NoTag(WireFormat.makeTag(fieldNumber, wireType));
        }

        public void writeInt32(int fieldNumber, int value) throws IOException {
            flushIfNotAvailable(20);
            bufferTag(fieldNumber, 0);
            bufferInt32NoTag(value);
        }

        public void writeUInt32(int fieldNumber, int value) throws IOException {
            flushIfNotAvailable(20);
            bufferTag(fieldNumber, 0);
            bufferUInt32NoTag(value);
        }

        public void writeFixed32(int fieldNumber, int value) throws IOException {
            flushIfNotAvailable(14);
            bufferTag(fieldNumber, 5);
            bufferFixed32NoTag(value);
        }

        public void writeUInt64(int fieldNumber, long value) throws IOException {
            flushIfNotAvailable(20);
            bufferTag(fieldNumber, 0);
            bufferUInt64NoTag(value);
        }

        public void writeFixed64(int fieldNumber, long value) throws IOException {
            flushIfNotAvailable(18);
            bufferTag(fieldNumber, 1);
            bufferFixed64NoTag(value);
        }

        public void writeBool(int fieldNumber, boolean value) throws IOException {
            flushIfNotAvailable(11);
            bufferTag(fieldNumber, 0);
            buffer(value ? (byte) 1 : 0);
        }

        public void writeString(int fieldNumber, String value) throws IOException {
            writeTag(fieldNumber, 2);
            writeStringNoTag(value);
        }

        public void writeBytes(int fieldNumber, ByteString value) throws IOException {
            writeTag(fieldNumber, 2);
            writeBytesNoTag(value);
        }

        public void writeByteArray(int fieldNumber, byte[] value) throws IOException {
            writeByteArray(fieldNumber, value, 0, value.length);
        }

        public void writeByteArray(int fieldNumber, byte[] value, int offset, int length) throws IOException {
            writeTag(fieldNumber, 2);
            writeByteArrayNoTag(value, offset, length);
        }

        public void writeByteBuffer(int fieldNumber, ByteBuffer value) throws IOException {
            writeTag(fieldNumber, 2);
            writeUInt32NoTag(value.capacity());
            writeRawBytes(value);
        }

        public void writeBytesNoTag(ByteString value) throws IOException {
            writeUInt32NoTag(value.size());
            value.writeTo((ByteOutput) this);
        }

        public void writeByteArrayNoTag(byte[] value, int offset, int length) throws IOException {
            writeUInt32NoTag(length);
            write(value, offset, length);
        }

        public void writeRawBytes(ByteBuffer value) throws IOException {
            if (value.hasArray()) {
                write(value.array(), value.arrayOffset(), value.capacity());
                return;
            }
            ByteBuffer duplicated = value.duplicate();
            Java8Compatibility.clear(duplicated);
            write(duplicated);
        }

        public void writeMessage(int fieldNumber, MessageLite value) throws IOException {
            writeTag(fieldNumber, 2);
            writeMessageNoTag(value);
        }

        /* access modifiers changed from: package-private */
        public void writeMessage(int fieldNumber, MessageLite value, Schema schema) throws IOException {
            writeTag(fieldNumber, 2);
            writeMessageNoTag(value, schema);
        }

        public void writeMessageSetExtension(int fieldNumber, MessageLite value) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, fieldNumber);
            writeMessage(3, value);
            writeTag(1, 4);
        }

        public void writeRawMessageSetExtension(int fieldNumber, ByteString value) throws IOException {
            writeTag(1, 3);
            writeUInt32(2, fieldNumber);
            writeBytes(3, value);
            writeTag(1, 4);
        }

        public void writeMessageNoTag(MessageLite value) throws IOException {
            writeUInt32NoTag(value.getSerializedSize());
            value.writeTo((CodedOutputStream) this);
        }

        /* access modifiers changed from: package-private */
        public void writeMessageNoTag(MessageLite value, Schema schema) throws IOException {
            writeUInt32NoTag(((AbstractMessageLite) value).getSerializedSize(schema));
            schema.writeTo(value, this.wrapper);
        }

        public void write(byte value) throws IOException {
            if (this.position == this.limit) {
                doFlush();
            }
            buffer(value);
        }

        public void writeInt32NoTag(int value) throws IOException {
            if (value >= 0) {
                writeUInt32NoTag(value);
            } else {
                writeUInt64NoTag((long) value);
            }
        }

        public void writeUInt32NoTag(int value) throws IOException {
            flushIfNotAvailable(5);
            bufferUInt32NoTag(value);
        }

        public void writeFixed32NoTag(int value) throws IOException {
            flushIfNotAvailable(4);
            bufferFixed32NoTag(value);
        }

        public void writeUInt64NoTag(long value) throws IOException {
            flushIfNotAvailable(10);
            bufferUInt64NoTag(value);
        }

        public void writeFixed64NoTag(long value) throws IOException {
            flushIfNotAvailable(8);
            bufferFixed64NoTag(value);
        }

        public void writeStringNoTag(String value) throws IOException {
            int oldPosition;
            int length;
            try {
                int maxLength = value.length() * 3;
                int maxLengthVarIntSize = computeUInt32SizeNoTag(maxLength);
                if (maxLengthVarIntSize + maxLength > this.limit) {
                    byte[] encodedBytes = new byte[maxLength];
                    int actualLength = Utf8.encode(value, encodedBytes, 0, maxLength);
                    writeUInt32NoTag(actualLength);
                    writeLazy(encodedBytes, 0, actualLength);
                    return;
                }
                if (maxLengthVarIntSize + maxLength > this.limit - this.position) {
                    doFlush();
                }
                int minLengthVarIntSize = computeUInt32SizeNoTag(value.length());
                oldPosition = this.position;
                if (minLengthVarIntSize == maxLengthVarIntSize) {
                    this.position = oldPosition + minLengthVarIntSize;
                    int newPosition = Utf8.encode(value, this.buffer, this.position, this.limit - this.position);
                    this.position = oldPosition;
                    length = (newPosition - oldPosition) - minLengthVarIntSize;
                    bufferUInt32NoTag(length);
                    this.position = newPosition;
                } else {
                    length = Utf8.encodedLength(value);
                    bufferUInt32NoTag(length);
                    this.position = Utf8.encode(value, this.buffer, this.position, length);
                }
                this.totalBytesWritten += length;
            } catch (Utf8.UnpairedSurrogateException e) {
                this.totalBytesWritten -= this.position - oldPosition;
                this.position = oldPosition;
                throw e;
            } catch (ArrayIndexOutOfBoundsException e2) {
                throw new OutOfSpaceException((Throwable) e2);
            } catch (Utf8.UnpairedSurrogateException e3) {
                inefficientWriteStringNoTag(value, e3);
            }
        }

        public void flush() throws IOException {
            if (this.position > 0) {
                doFlush();
            }
        }

        public void write(byte[] value, int offset, int length) throws IOException {
            if (this.limit - this.position >= length) {
                System.arraycopy(value, offset, this.buffer, this.position, length);
                this.position += length;
                this.totalBytesWritten += length;
                return;
            }
            int bytesWritten = this.limit - this.position;
            System.arraycopy(value, offset, this.buffer, this.position, bytesWritten);
            int offset2 = offset + bytesWritten;
            int length2 = length - bytesWritten;
            this.position = this.limit;
            this.totalBytesWritten += bytesWritten;
            doFlush();
            if (length2 <= this.limit) {
                System.arraycopy(value, offset2, this.buffer, 0, length2);
                this.position = length2;
            } else {
                this.out.write(value, offset2, length2);
            }
            this.totalBytesWritten += length2;
        }

        public void writeLazy(byte[] value, int offset, int length) throws IOException {
            write(value, offset, length);
        }

        public void write(ByteBuffer value) throws IOException {
            int length = value.remaining();
            if (this.limit - this.position >= length) {
                value.get(this.buffer, this.position, length);
                this.position += length;
                this.totalBytesWritten += length;
                return;
            }
            int bytesWritten = this.limit - this.position;
            value.get(this.buffer, this.position, bytesWritten);
            int length2 = length - bytesWritten;
            this.position = this.limit;
            this.totalBytesWritten += bytesWritten;
            doFlush();
            while (length2 > this.limit) {
                value.get(this.buffer, 0, this.limit);
                this.out.write(this.buffer, 0, this.limit);
                length2 -= this.limit;
                this.totalBytesWritten += this.limit;
            }
            value.get(this.buffer, 0, length2);
            this.position = length2;
            this.totalBytesWritten += length2;
        }

        public void writeLazy(ByteBuffer value) throws IOException {
            write(value);
        }

        private void flushIfNotAvailable(int requiredSize) throws IOException {
            if (this.limit - this.position < requiredSize) {
                doFlush();
            }
        }

        private void doFlush() throws IOException {
            this.out.write(this.buffer, 0, this.position);
            this.position = 0;
        }
    }
}
