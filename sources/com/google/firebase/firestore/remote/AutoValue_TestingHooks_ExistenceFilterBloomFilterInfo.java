package com.google.firebase.firestore.remote;

import com.google.firebase.firestore.remote.TestingHooks;

final class AutoValue_TestingHooks_ExistenceFilterBloomFilterInfo extends TestingHooks.ExistenceFilterBloomFilterInfo {
    private final boolean applied;
    private final int bitmapLength;
    private final BloomFilter bloomFilter;
    private final int hashCount;
    private final int padding;

    AutoValue_TestingHooks_ExistenceFilterBloomFilterInfo(BloomFilter bloomFilter2, boolean applied2, int hashCount2, int bitmapLength2, int padding2) {
        this.bloomFilter = bloomFilter2;
        this.applied = applied2;
        this.hashCount = hashCount2;
        this.bitmapLength = bitmapLength2;
        this.padding = padding2;
    }

    /* access modifiers changed from: package-private */
    public BloomFilter bloomFilter() {
        return this.bloomFilter;
    }

    /* access modifiers changed from: package-private */
    public boolean applied() {
        return this.applied;
    }

    /* access modifiers changed from: package-private */
    public int hashCount() {
        return this.hashCount;
    }

    /* access modifiers changed from: package-private */
    public int bitmapLength() {
        return this.bitmapLength;
    }

    /* access modifiers changed from: package-private */
    public int padding() {
        return this.padding;
    }

    public String toString() {
        return "ExistenceFilterBloomFilterInfo{bloomFilter=" + this.bloomFilter + ", applied=" + this.applied + ", hashCount=" + this.hashCount + ", bitmapLength=" + this.bitmapLength + ", padding=" + this.padding + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TestingHooks.ExistenceFilterBloomFilterInfo)) {
            return false;
        }
        TestingHooks.ExistenceFilterBloomFilterInfo that = (TestingHooks.ExistenceFilterBloomFilterInfo) o;
        if (this.bloomFilter != null ? this.bloomFilter.equals(that.bloomFilter()) : that.bloomFilter() == null) {
            if (this.applied == that.applied() && this.hashCount == that.hashCount() && this.bitmapLength == that.bitmapLength() && this.padding == that.padding()) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((((((((1 * 1000003) ^ (this.bloomFilter == null ? 0 : this.bloomFilter.hashCode())) * 1000003) ^ (this.applied ? 1231 : 1237)) * 1000003) ^ this.hashCount) * 1000003) ^ this.bitmapLength) * 1000003) ^ this.padding;
    }
}
