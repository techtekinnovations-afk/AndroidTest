package com.google.common.hash;

import com.google.common.base.Preconditions;

@ElementTypesAreNonnullByDefault
final class FarmHashFingerprint64 extends AbstractNonStreamingHashFunction {
    static final HashFunction FARMHASH_FINGERPRINT_64 = new FarmHashFingerprint64();
    private static final long K0 = -4348849565147123417L;
    private static final long K1 = -5435081209227447693L;
    private static final long K2 = -7286425919675154353L;

    FarmHashFingerprint64() {
    }

    public HashCode hashBytes(byte[] input, int off, int len) {
        Preconditions.checkPositionIndexes(off, off + len, input.length);
        return HashCode.fromLong(fingerprint(input, off, len));
    }

    public int bits() {
        return 64;
    }

    public String toString() {
        return "Hashing.farmHashFingerprint64()";
    }

    static long fingerprint(byte[] bytes, int offset, int length) {
        if (length <= 32) {
            if (length <= 16) {
                return hashLength0to16(bytes, offset, length);
            }
            return hashLength17to32(bytes, offset, length);
        } else if (length <= 64) {
            return hashLength33To64(bytes, offset, length);
        } else {
            return hashLength65Plus(bytes, offset, length);
        }
    }

    private static long shiftMix(long val) {
        return (val >>> 47) ^ val;
    }

    private static long hashLength16(long u, long v, long mul) {
        long a = (u ^ v) * mul;
        long b = (v ^ (a ^ (a >>> 47))) * mul;
        return (b ^ (b >>> 47)) * mul;
    }

    private static void weakHashLength32WithSeeds(byte[] bytes, int offset, long seedA, long seedB, long[] output) {
        byte[] bArr = bytes;
        long part1 = LittleEndianByteArray.load64(bytes, offset);
        long part2 = LittleEndianByteArray.load64(bArr, offset + 8);
        long part3 = LittleEndianByteArray.load64(bArr, offset + 16);
        long part4 = LittleEndianByteArray.load64(bArr, offset + 24);
        long seedA2 = seedA + part1;
        long c = seedA2;
        long seedA3 = seedA2 + part2 + part3;
        output[0] = seedA3 + part4;
        output[1] = Long.rotateRight(seedB + seedA2 + part4, 21) + Long.rotateRight(seedA3, 44) + c;
    }

    private static long hashLength0to16(byte[] bytes, int offset, int length) {
        if (length >= 8) {
            long mul = (((long) length) * 2) + K2;
            long a = LittleEndianByteArray.load64(bytes, offset) + K2;
            long b = LittleEndianByteArray.load64(bytes, (offset + length) - 8);
            return hashLength16((Long.rotateRight(b, 37) * mul) + a, (Long.rotateRight(a, 25) + b) * mul, mul);
        } else if (length >= 4) {
            return hashLength16(((long) length) + ((((long) LittleEndianByteArray.load32(bytes, offset)) & 4294967295L) << 3), ((long) LittleEndianByteArray.load32(bytes, (offset + length) - 4)) & 4294967295L, ((long) (length * 2)) + K2);
        } else if (length <= 0) {
            return K2;
        } else {
            return shiftMix((((long) ((bytes[offset] & 255) + ((bytes[(length >> 1) + offset] & 255) << 8))) * K2) ^ (((long) (((bytes[(length - 1) + offset] & 255) << 2) + length)) * K0)) * K2;
        }
    }

    private static long hashLength17to32(byte[] bytes, int offset, int length) {
        byte[] bArr = bytes;
        int i = length;
        long mul = (((long) i) * 2) + K2;
        long a = LittleEndianByteArray.load64(bytes, offset) * K1;
        long b = LittleEndianByteArray.load64(bArr, offset + 8);
        long c = LittleEndianByteArray.load64(bArr, (offset + i) - 8) * mul;
        return hashLength16(Long.rotateRight(a + b, 43) + Long.rotateRight(c, 30) + (LittleEndianByteArray.load64(bArr, (offset + i) - 16) * K2), Long.rotateRight(K2 + b, 18) + a + c, mul);
    }

    private static long hashLength33To64(byte[] bytes, int offset, int length) {
        byte[] bArr = bytes;
        int i = length;
        long mul = (((long) i) * 2) + K2;
        long a = LittleEndianByteArray.load64(bytes, offset) * K2;
        long b = LittleEndianByteArray.load64(bArr, offset + 8);
        long c = LittleEndianByteArray.load64(bArr, (offset + i) - 8) * mul;
        long y = Long.rotateRight(a + b, 43) + Long.rotateRight(c, 30) + (LittleEndianByteArray.load64(bArr, (offset + i) - 16) * K2);
        long z = hashLength16(y, Long.rotateRight(K2 + b, 18) + a + c, mul);
        long e = LittleEndianByteArray.load64(bArr, offset + 16) * mul;
        long f = LittleEndianByteArray.load64(bArr, offset + 24);
        long g = (y + LittleEndianByteArray.load64(bArr, (offset + length) - 32)) * mul;
        long j = g;
        return hashLength16(Long.rotateRight(e + f, 43) + Long.rotateRight(g, 30) + ((z + LittleEndianByteArray.load64(bArr, (offset + length) - 24)) * mul), e + Long.rotateRight(f + a, 18) + g, mul);
    }

    private static long hashLength65Plus(byte[] bytes, int offset, int length) {
        byte[] bArr = bytes;
        long j = K1;
        long x = (((long) 81) * K1) + 113;
        long z = shiftMix((x * K2) + 113) * K2;
        long[] w = new long[2];
        long[] w2 = new long[2];
        long x2 = (K2 * ((long) 81)) + LittleEndianByteArray.load64(bytes, offset);
        int end = offset + (((length - 1) / 64) * 64);
        int last64offset = (((length - 1) & 63) + end) - 63;
        int offset2 = offset;
        while (true) {
            long j2 = j;
            long j3 = x;
            long x3 = (Long.rotateRight(((x2 + x) + w[0]) + LittleEndianByteArray.load64(bArr, offset2 + 8), 37) * j2) ^ w2[1];
            long y = (Long.rotateRight(x + w[1] + LittleEndianByteArray.load64(bArr, offset2 + 48), 42) * j2) + w[0] + LittleEndianByteArray.load64(bArr, offset2 + 40);
            long z2 = Long.rotateRight(w2[0] + z, 33) * j2;
            weakHashLength32WithSeeds(bArr, offset2, w[1] * j2, w2[0] + x3, w);
            int offset3 = offset2;
            long[] v = w;
            weakHashLength32WithSeeds(bArr, offset3 + 32, w2[1] + z2, y + LittleEndianByteArray.load64(bArr, offset3 + 16), w2);
            long x4 = z2;
            z = x3;
            offset2 = offset3 + 64;
            if (offset2 == end) {
                long mul = ((255 & z) << 1) + j2;
                int offset4 = last64offset;
                w2[0] = w2[0] + ((long) ((length - 1) & 63));
                v[0] = v[0] + w2[0];
                w2[0] = w2[0] + v[0];
                long x5 = (w2[1] * 9) ^ (Long.rotateRight(((x4 + y) + v[0]) + LittleEndianByteArray.load64(bArr, offset4 + 8), 37) * mul);
                long y2 = (Long.rotateRight(y + v[1] + LittleEndianByteArray.load64(bArr, offset4 + 48), 42) * mul) + (v[0] * 9) + LittleEndianByteArray.load64(bArr, offset4 + 40);
                long z3 = Long.rotateRight(w2[0] + z, 33) * mul;
                weakHashLength32WithSeeds(bArr, offset4, v[1] * mul, w2[0] + x5, v);
                int offset5 = offset4;
                long[] w3 = w2;
                weakHashLength32WithSeeds(bArr, offset5 + 32, w2[1] + z3, LittleEndianByteArray.load64(bArr, offset5 + 16) + y2, w3);
                return hashLength16(hashLength16(v[0], w3[0], mul) + (shiftMix(y2) * K0) + x5, hashLength16(v[1], w3[1], mul) + z3, mul);
            }
            bArr = bytes;
            x2 = x4;
            x = y;
            j = j2;
            w = v;
        }
    }
}
