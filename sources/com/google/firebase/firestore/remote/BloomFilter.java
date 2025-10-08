package com.google.firebase.firestore.remote;

import android.util.Base64;
import com.google.protobuf.ByteString;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class BloomFilter {
    private final int bitCount;
    private final ByteString bitmap;
    private final int hashCount;
    private final MessageDigest md5HashMessageDigest;

    public BloomFilter(ByteString bitmap2, int padding, int hashCount2) {
        if (padding < 0 || padding >= 8) {
            throw new IllegalArgumentException("Invalid padding: " + padding);
        } else if (hashCount2 < 0) {
            throw new IllegalArgumentException("Invalid hash count: " + hashCount2);
        } else if (bitmap2.size() > 0 && hashCount2 == 0) {
            throw new IllegalArgumentException("Invalid hash count: " + hashCount2);
        } else if (bitmap2.size() != 0 || padding == 0) {
            this.bitmap = bitmap2;
            this.hashCount = hashCount2;
            this.bitCount = (bitmap2.size() * 8) - padding;
            this.md5HashMessageDigest = createMd5HashMessageDigest();
        } else {
            throw new IllegalArgumentException("Expected padding of 0 when bitmap length is 0, but got " + padding);
        }
    }

    public static BloomFilter create(ByteString bitmap2, int padding, int hashCount2) throws BloomFilterCreateException {
        if (padding < 0 || padding >= 8) {
            throw new BloomFilterCreateException("Invalid padding: " + padding);
        } else if (hashCount2 < 0) {
            throw new BloomFilterCreateException("Invalid hash count: " + hashCount2);
        } else if (bitmap2.size() > 0 && hashCount2 == 0) {
            throw new BloomFilterCreateException("Invalid hash count: " + hashCount2);
        } else if (bitmap2.size() != 0 || padding == 0) {
            return new BloomFilter(bitmap2, padding, hashCount2);
        } else {
            throw new BloomFilterCreateException("Expected padding of 0 when bitmap length is 0, but got " + padding);
        }
    }

    public static final class BloomFilterCreateException extends Exception {
        public BloomFilterCreateException(String message) {
            super(message);
        }
    }

    /* access modifiers changed from: package-private */
    public int getBitCount() {
        return this.bitCount;
    }

    public boolean mightContain(String value) {
        if (this.bitCount == 0) {
            return false;
        }
        byte[] hashedValue = md5HashDigest(value);
        if (hashedValue.length == 16) {
            long hash1 = getLongLittleEndian(hashedValue, 0);
            long hash2 = getLongLittleEndian(hashedValue, 8);
            for (int i = 0; i < this.hashCount; i++) {
                if (!isBitSet(getBitIndex(hash1, hash2, i))) {
                    return false;
                }
            }
            return true;
        }
        throw new RuntimeException("Invalid md5 hash array length: " + hashedValue.length + " (expected 16)");
    }

    private byte[] md5HashDigest(String value) {
        return this.md5HashMessageDigest.digest(value.getBytes(StandardCharsets.UTF_8));
    }

    private static MessageDigest createMd5HashMessageDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Missing MD5 MessageDigest provider: ", e);
        }
    }

    private static long getLongLittleEndian(byte[] bytes, int offset) {
        long result = 0;
        for (int i = 0; i < 8; i++) {
            result |= (((long) bytes[offset + i]) & 255) << (i * 8);
        }
        return result;
    }

    private int getBitIndex(long hash1, long hash2, int hashIndex) {
        return (int) unsignedRemainder((((long) hashIndex) * hash2) + hash1, (long) this.bitCount);
    }

    private static long unsignedRemainder(long dividend, long divisor) {
        long remainder = dividend - ((((dividend >>> 1) / divisor) << 1) * divisor);
        return remainder - (remainder >= divisor ? divisor : 0);
    }

    private boolean isBitSet(int index) {
        return ((1 << (index % 8)) & this.bitmap.byteAt(index / 8)) != 0;
    }

    public String toString() {
        return "BloomFilter{hashCount=" + this.hashCount + ", size=" + this.bitCount + ", bitmap=\"" + Base64.encodeToString(this.bitmap.toByteArray(), 2) + "\"}";
    }
}
