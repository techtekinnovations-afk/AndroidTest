package com.google.firebase.firestore.remote;

import com.google.firebase.firestore.remote.TestingHooks;

final class AutoValue_TestingHooks_ExistenceFilterMismatchInfo extends TestingHooks.ExistenceFilterMismatchInfo {
    private final TestingHooks.ExistenceFilterBloomFilterInfo bloomFilter;
    private final String databaseId;
    private final int existenceFilterCount;
    private final int localCacheCount;
    private final String projectId;

    AutoValue_TestingHooks_ExistenceFilterMismatchInfo(int localCacheCount2, int existenceFilterCount2, String projectId2, String databaseId2, TestingHooks.ExistenceFilterBloomFilterInfo bloomFilter2) {
        this.localCacheCount = localCacheCount2;
        this.existenceFilterCount = existenceFilterCount2;
        if (projectId2 != null) {
            this.projectId = projectId2;
            if (databaseId2 != null) {
                this.databaseId = databaseId2;
                this.bloomFilter = bloomFilter2;
                return;
            }
            throw new NullPointerException("Null databaseId");
        }
        throw new NullPointerException("Null projectId");
    }

    /* access modifiers changed from: package-private */
    public int localCacheCount() {
        return this.localCacheCount;
    }

    /* access modifiers changed from: package-private */
    public int existenceFilterCount() {
        return this.existenceFilterCount;
    }

    /* access modifiers changed from: package-private */
    public String projectId() {
        return this.projectId;
    }

    /* access modifiers changed from: package-private */
    public String databaseId() {
        return this.databaseId;
    }

    /* access modifiers changed from: package-private */
    public TestingHooks.ExistenceFilterBloomFilterInfo bloomFilter() {
        return this.bloomFilter;
    }

    public String toString() {
        return "ExistenceFilterMismatchInfo{localCacheCount=" + this.localCacheCount + ", existenceFilterCount=" + this.existenceFilterCount + ", projectId=" + this.projectId + ", databaseId=" + this.databaseId + ", bloomFilter=" + this.bloomFilter + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TestingHooks.ExistenceFilterMismatchInfo)) {
            return false;
        }
        TestingHooks.ExistenceFilterMismatchInfo that = (TestingHooks.ExistenceFilterMismatchInfo) o;
        if (this.localCacheCount == that.localCacheCount() && this.existenceFilterCount == that.existenceFilterCount() && this.projectId.equals(that.projectId()) && this.databaseId.equals(that.databaseId())) {
            if (this.bloomFilter == null) {
                if (that.bloomFilter() == null) {
                    return true;
                }
            } else if (this.bloomFilter.equals(that.bloomFilter())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((((((((1 * 1000003) ^ this.localCacheCount) * 1000003) ^ this.existenceFilterCount) * 1000003) ^ this.projectId.hashCode()) * 1000003) ^ this.databaseId.hashCode()) * 1000003) ^ (this.bloomFilter == null ? 0 : this.bloomFilter.hashCode());
    }
}
