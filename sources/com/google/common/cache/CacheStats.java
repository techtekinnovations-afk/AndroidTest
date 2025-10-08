package com.google.common.cache;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.LongMath;
import javax.annotation.CheckForNull;

@ElementTypesAreNonnullByDefault
public final class CacheStats {
    private final long evictionCount;
    private final long hitCount;
    private final long loadExceptionCount;
    private final long loadSuccessCount;
    private final long missCount;
    private final long totalLoadTime;

    public CacheStats(long hitCount2, long missCount2, long loadSuccessCount2, long loadExceptionCount2, long totalLoadTime2, long evictionCount2) {
        long j = hitCount2;
        long j2 = missCount2;
        long j3 = loadSuccessCount2;
        long j4 = loadExceptionCount2;
        long j5 = totalLoadTime2;
        long j6 = evictionCount2;
        boolean z = true;
        Preconditions.checkArgument(j >= 0);
        Preconditions.checkArgument(j2 >= 0);
        Preconditions.checkArgument(j3 >= 0);
        Preconditions.checkArgument(j4 >= 0);
        Preconditions.checkArgument(j5 >= 0);
        Preconditions.checkArgument(j6 < 0 ? false : z);
        this.hitCount = j;
        this.missCount = j2;
        this.loadSuccessCount = j3;
        this.loadExceptionCount = j4;
        this.totalLoadTime = j5;
        this.evictionCount = j6;
    }

    public long requestCount() {
        return LongMath.saturatedAdd(this.hitCount, this.missCount);
    }

    public long hitCount() {
        return this.hitCount;
    }

    public double hitRate() {
        long requestCount = requestCount();
        if (requestCount == 0) {
            return 1.0d;
        }
        return ((double) this.hitCount) / ((double) requestCount);
    }

    public long missCount() {
        return this.missCount;
    }

    public double missRate() {
        long requestCount = requestCount();
        if (requestCount == 0) {
            return 0.0d;
        }
        return ((double) this.missCount) / ((double) requestCount);
    }

    public long loadCount() {
        return LongMath.saturatedAdd(this.loadSuccessCount, this.loadExceptionCount);
    }

    public long loadSuccessCount() {
        return this.loadSuccessCount;
    }

    public long loadExceptionCount() {
        return this.loadExceptionCount;
    }

    public double loadExceptionRate() {
        long totalLoadCount = LongMath.saturatedAdd(this.loadSuccessCount, this.loadExceptionCount);
        if (totalLoadCount == 0) {
            return 0.0d;
        }
        return ((double) this.loadExceptionCount) / ((double) totalLoadCount);
    }

    public long totalLoadTime() {
        return this.totalLoadTime;
    }

    public double averageLoadPenalty() {
        long totalLoadCount = LongMath.saturatedAdd(this.loadSuccessCount, this.loadExceptionCount);
        if (totalLoadCount == 0) {
            return 0.0d;
        }
        return ((double) this.totalLoadTime) / ((double) totalLoadCount);
    }

    public long evictionCount() {
        return this.evictionCount;
    }

    public CacheStats minus(CacheStats other) {
        CacheStats cacheStats;
        CacheStats cacheStats2 = other;
        long max = Math.max(0, LongMath.saturatedSubtract(this.hitCount, cacheStats2.hitCount));
        long max2 = Math.max(0, LongMath.saturatedSubtract(this.missCount, cacheStats2.missCount));
        long max3 = Math.max(0, LongMath.saturatedSubtract(this.loadSuccessCount, cacheStats2.loadSuccessCount));
        long max4 = Math.max(0, LongMath.saturatedSubtract(this.loadExceptionCount, cacheStats2.loadExceptionCount));
        long max5 = Math.max(0, LongMath.saturatedSubtract(this.totalLoadTime, cacheStats2.totalLoadTime));
        long j = max4;
        long j2 = max5;
        long j3 = max;
        new CacheStats(j3, max2, max3, j, j2, Math.max(0, LongMath.saturatedSubtract(this.evictionCount, cacheStats2.evictionCount)));
        return cacheStats;
    }

    public CacheStats plus(CacheStats other) {
        CacheStats cacheStats;
        CacheStats cacheStats2 = other;
        new CacheStats(LongMath.saturatedAdd(this.hitCount, cacheStats2.hitCount), LongMath.saturatedAdd(this.missCount, cacheStats2.missCount), LongMath.saturatedAdd(this.loadSuccessCount, cacheStats2.loadSuccessCount), LongMath.saturatedAdd(this.loadExceptionCount, cacheStats2.loadExceptionCount), LongMath.saturatedAdd(this.totalLoadTime, cacheStats2.totalLoadTime), LongMath.saturatedAdd(this.evictionCount, cacheStats2.evictionCount));
        return cacheStats;
    }

    public int hashCode() {
        return Objects.hashCode(Long.valueOf(this.hitCount), Long.valueOf(this.missCount), Long.valueOf(this.loadSuccessCount), Long.valueOf(this.loadExceptionCount), Long.valueOf(this.totalLoadTime), Long.valueOf(this.evictionCount));
    }

    public boolean equals(@CheckForNull Object object) {
        if (!(object instanceof CacheStats)) {
            return false;
        }
        CacheStats other = (CacheStats) object;
        if (this.hitCount == other.hitCount && this.missCount == other.missCount && this.loadSuccessCount == other.loadSuccessCount && this.loadExceptionCount == other.loadExceptionCount && this.totalLoadTime == other.totalLoadTime && this.evictionCount == other.evictionCount) {
            return true;
        }
        return false;
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("hitCount", this.hitCount).add("missCount", this.missCount).add("loadSuccessCount", this.loadSuccessCount).add("loadExceptionCount", this.loadExceptionCount).add("totalLoadTime", this.totalLoadTime).add("evictionCount", this.evictionCount).toString();
    }
}
