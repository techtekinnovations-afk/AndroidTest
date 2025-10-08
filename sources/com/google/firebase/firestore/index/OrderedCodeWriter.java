package com.google.firebase.firestore.index;

import com.google.protobuf.ByteString;
import java.math.RoundingMode;
import java.util.Arrays;

public class OrderedCodeWriter {
    private static final int DEFAULT_BUFFER_SIZE = 1024;
    public static final long DOUBLE_ALL_BITS = -1;
    public static final long DOUBLE_SIGN_MASK = Long.MIN_VALUE;
    public static final byte ESCAPE1 = 0;
    public static final byte ESCAPE2 = -1;
    public static final byte FF_BYTE = 0;
    public static final byte INFINITY = -1;
    private static final byte[][] LENGTH_TO_HEADER_BITS = {new byte[]{0, 0}, new byte[]{Byte.MIN_VALUE, 0}, new byte[]{-64, 0}, new byte[]{-32, 0}, new byte[]{-16, 0}, new byte[]{-8, 0}, new byte[]{-4, 0}, new byte[]{-2, 0}, new byte[]{-1, 0}, new byte[]{-1, Byte.MIN_VALUE}, new byte[]{-1, -64}};
    public static final byte NULL_BYTE = -1;
    public static final byte SEPARATOR = 1;
    private byte[] buffer = new byte[1024];
    private int position = 0;

    public void writeBytesAscending(ByteString value) {
        for (int i = 0; i < value.size(); i++) {
            writeByteAscending(value.byteAt(i));
        }
        writeSeparatorAscending();
    }

    public void writeBytesDescending(ByteString value) {
        for (int i = 0; i < value.size(); i++) {
            writeByteDescending(value.byteAt(i));
        }
        writeSeparatorDescending();
    }

    public void writeUtf8Ascending(CharSequence sequence) {
        int utf16Length = sequence.length();
        int i = 0;
        while (i < utf16Length) {
            char c = sequence.charAt(i);
            if (c < 128) {
                writeByteAscending((byte) c);
            } else if (c < 2048) {
                writeByteAscending((byte) ((c >>> 6) | 960));
                writeByteAscending((byte) (128 | (c & '?')));
            } else if (c < 55296 || 57343 < c) {
                writeByteAscending((byte) ((c >>> 12) | 480));
                writeByteAscending((byte) (((c >>> 6) & 63) | 128));
                writeByteAscending((byte) (128 | (c & '?')));
            } else {
                int codePoint = Character.codePointAt(sequence, i);
                i++;
                writeByteAscending((byte) ((codePoint >>> 18) | 240));
                writeByteAscending((byte) (((codePoint >>> 12) & 63) | 128));
                writeByteAscending((byte) (((codePoint >>> 6) & 63) | 128));
                writeByteAscending((byte) (128 | (codePoint & 63)));
            }
            i++;
        }
        writeSeparatorAscending();
    }

    public void writeUtf8Descending(CharSequence sequence) {
        int utf16Length = sequence.length();
        int i = 0;
        while (i < utf16Length) {
            char c = sequence.charAt(i);
            if (c < 128) {
                writeByteDescending((byte) c);
            } else if (c < 2048) {
                writeByteDescending((byte) ((c >>> 6) | 960));
                writeByteDescending((byte) (128 | (c & '?')));
            } else if (c < 55296 || 57343 < c) {
                writeByteDescending((byte) ((c >>> 12) | 480));
                writeByteDescending((byte) (((c >>> 6) & 63) | 128));
                writeByteDescending((byte) (128 | (c & '?')));
            } else {
                int codePoint = Character.codePointAt(sequence, i);
                i++;
                writeByteDescending((byte) ((codePoint >>> 18) | 240));
                writeByteDescending((byte) (((codePoint >>> 12) & 63) | 128));
                writeByteDescending((byte) (((codePoint >>> 6) & 63) | 128));
                writeByteDescending((byte) (128 | (codePoint & 63)));
            }
            i++;
        }
        writeSeparatorDescending();
    }

    public void writeUnsignedLongAscending(long value) {
        int len = unsignedNumLength(value);
        ensureAvailable(len + 1);
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        bArr[i] = (byte) len;
        int i2 = this.position + len;
        while (true) {
            i2--;
            if (i2 >= this.position) {
                this.buffer[i2] = (byte) ((int) (255 & value));
                value >>>= 8;
            } else {
                this.position += len;
                return;
            }
        }
    }

    public void writeUnsignedLongDescending(long value) {
        int len = unsignedNumLength(value);
        ensureAvailable(len + 1);
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        bArr[i] = (byte) (~len);
        int i2 = this.position + len;
        while (true) {
            i2--;
            if (i2 >= this.position) {
                this.buffer[i2] = (byte) ((int) (~(255 & value)));
                value >>>= 8;
            } else {
                this.position += len;
                return;
            }
        }
    }

    public void writeSignedLongAscending(long value) {
        long val = value < 0 ? ~value : value;
        if (val < 64) {
            ensureAvailable(1);
            byte[] bArr = this.buffer;
            int i = this.position;
            this.position = i + 1;
            bArr[i] = (byte) ((int) (((long) LENGTH_TO_HEADER_BITS[1][0]) ^ value));
            return;
        }
        int len = signedNumLength(val);
        ensureAvailable(len);
        if (len >= 2) {
            byte signByte = value < 0 ? (byte) -1 : 0;
            int startIndex = this.position;
            if (len == 10) {
                startIndex += 2;
                this.buffer[this.position] = signByte;
                this.buffer[this.position + 1] = signByte;
            } else if (len == 9) {
                startIndex++;
                this.buffer[this.position] = signByte;
            }
            long x = value;
            for (int i2 = (len - 1) + this.position; i2 >= startIndex; i2--) {
                this.buffer[i2] = (byte) ((int) (255 & x));
                x >>= 8;
            }
            byte[] bArr2 = this.buffer;
            int i3 = this.position;
            bArr2[i3] = (byte) (LENGTH_TO_HEADER_BITS[len][0] ^ bArr2[i3]);
            byte[] bArr3 = this.buffer;
            int i4 = this.position + 1;
            bArr3[i4] = (byte) (LENGTH_TO_HEADER_BITS[len][1] ^ bArr3[i4]);
            this.position += len;
            return;
        }
        throw new AssertionError(String.format("Invalid length (%d) returned by signedNumLength", new Object[]{Integer.valueOf(len)}));
    }

    public void writeSignedLongDescending(long value) {
        writeSignedLongAscending(~value);
    }

    public void writeDoubleAscending(double val) {
        long v = Double.doubleToLongBits(val);
        writeUnsignedLongAscending(v ^ (v < 0 ? -1 : Long.MIN_VALUE));
    }

    public void writeDoubleDescending(double val) {
        long v = Double.doubleToLongBits(val);
        writeUnsignedLongDescending(v ^ (v < 0 ? -1 : Long.MIN_VALUE));
    }

    public void writeInfinityAscending() {
        writeEscapedByteAscending((byte) -1);
        writeEscapedByteAscending((byte) -1);
    }

    public void writeInfinityDescending() {
        writeEscapedByteDescending((byte) -1);
        writeEscapedByteDescending((byte) -1);
    }

    public void reset() {
        this.position = 0;
    }

    public byte[] encodedBytes() {
        return Arrays.copyOf(this.buffer, this.position);
    }

    private void writeByteAscending(byte b) {
        if (b == 0) {
            writeEscapedByteAscending((byte) 0);
            writeEscapedByteAscending((byte) -1);
        } else if (b == -1) {
            writeEscapedByteAscending((byte) -1);
            writeEscapedByteAscending((byte) 0);
        } else {
            writeEscapedByteAscending(b);
        }
    }

    private void writeByteDescending(byte b) {
        if (b == 0) {
            writeEscapedByteDescending((byte) 0);
            writeEscapedByteDescending((byte) -1);
        } else if (b == -1) {
            writeEscapedByteDescending((byte) -1);
            writeEscapedByteDescending((byte) 0);
        } else {
            writeEscapedByteDescending(b);
        }
    }

    private void writeSeparatorAscending() {
        writeEscapedByteAscending((byte) 0);
        writeEscapedByteAscending((byte) 1);
    }

    private void writeSeparatorDescending() {
        writeEscapedByteDescending((byte) 0);
        writeEscapedByteDescending((byte) 1);
    }

    private void writeEscapedByteAscending(byte b) {
        ensureAvailable(1);
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        bArr[i] = b;
    }

    private void writeEscapedByteDescending(byte b) {
        ensureAvailable(1);
        byte[] bArr = this.buffer;
        int i = this.position;
        this.position = i + 1;
        bArr[i] = (byte) (~b);
    }

    private void ensureAvailable(int bytes) {
        int minCapacity = this.position + bytes;
        if (minCapacity > this.buffer.length) {
            int newLength = this.buffer.length * 2;
            if (newLength < minCapacity) {
                newLength = minCapacity;
            }
            this.buffer = Arrays.copyOf(this.buffer, newLength);
        }
    }

    private int signedNumLength(long n) {
        if (n < 0) {
            n = ~n;
        }
        return IntMath.divide((64 - Long.numberOfLeadingZeros(n)) + 1, 7, RoundingMode.UP);
    }

    private int unsignedNumLength(long value) {
        return IntMath.divide(64 - Long.numberOfLeadingZeros(value), 8, RoundingMode.UP);
    }

    public void seed(byte[] encodedBytes) {
        ensureAvailable(encodedBytes.length);
        for (byte b : encodedBytes) {
            byte[] bArr = this.buffer;
            int i = this.position;
            this.position = i + 1;
            bArr[i] = b;
        }
    }
}
