package com.google.firebase.firestore;

import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Preconditions;
import java.util.Objects;
import javax.annotation.Nullable;

public final class FirebaseFirestoreSettings {
    public static final long CACHE_SIZE_UNLIMITED = -1;
    static final long DEFAULT_CACHE_SIZE_BYTES = 104857600;
    public static final String DEFAULT_HOST = "firestore.googleapis.com";
    static final long MINIMUM_CACHE_BYTES = 1048576;
    /* access modifiers changed from: private */
    public LocalCacheSettings cacheSettings;
    /* access modifiers changed from: private */
    public final long cacheSizeBytes;
    /* access modifiers changed from: private */
    public final String host;
    /* access modifiers changed from: private */
    public final boolean persistenceEnabled;
    /* access modifiers changed from: private */
    public final boolean sslEnabled;

    public static final class Builder {
        /* access modifiers changed from: private */
        public LocalCacheSettings cacheSettings;
        /* access modifiers changed from: private */
        public long cacheSizeBytes;
        /* access modifiers changed from: private */
        public String host;
        /* access modifiers changed from: private */
        public boolean persistenceEnabled;
        /* access modifiers changed from: private */
        public boolean sslEnabled;
        private boolean usedLegacyCacheSettings;

        public Builder() {
            this.usedLegacyCacheSettings = false;
            this.host = FirebaseFirestoreSettings.DEFAULT_HOST;
            this.sslEnabled = true;
            this.persistenceEnabled = true;
            this.cacheSizeBytes = FirebaseFirestoreSettings.DEFAULT_CACHE_SIZE_BYTES;
        }

        public Builder(FirebaseFirestoreSettings settings) {
            this.usedLegacyCacheSettings = false;
            Preconditions.checkNotNull(settings, "Provided settings must not be null.");
            this.host = settings.host;
            this.sslEnabled = settings.sslEnabled;
            this.persistenceEnabled = settings.persistenceEnabled;
            this.cacheSizeBytes = settings.cacheSizeBytes;
            boolean z = true;
            if (!this.persistenceEnabled || this.cacheSizeBytes != FirebaseFirestoreSettings.DEFAULT_CACHE_SIZE_BYTES) {
                this.usedLegacyCacheSettings = true;
            }
            if (!this.usedLegacyCacheSettings) {
                this.cacheSettings = settings.cacheSettings;
            } else {
                Assert.hardAssert(settings.cacheSettings != null ? false : z, "Given settings object mixes both cache config APIs, which is impossible.", new Object[0]);
            }
        }

        public Builder setHost(String host2) {
            this.host = (String) Preconditions.checkNotNull(host2, "Provided host must not be null.");
            return this;
        }

        public Builder setSslEnabled(boolean value) {
            this.sslEnabled = value;
            return this;
        }

        @Deprecated
        public Builder setPersistenceEnabled(boolean value) {
            if (this.cacheSettings == null) {
                this.persistenceEnabled = value;
                this.usedLegacyCacheSettings = true;
                return this;
            }
            throw new IllegalStateException("New cache config API setLocalCacheSettings() is already used.");
        }

        @Deprecated
        public Builder setCacheSizeBytes(long value) {
            if (this.cacheSettings != null) {
                throw new IllegalStateException("New cache config API setLocalCacheSettings() is already used.");
            } else if (value == -1 || value >= FirebaseFirestoreSettings.MINIMUM_CACHE_BYTES) {
                this.cacheSizeBytes = value;
                this.usedLegacyCacheSettings = true;
                return this;
            } else {
                throw new IllegalArgumentException("Cache size must be set to at least 1048576 bytes");
            }
        }

        public Builder setLocalCacheSettings(LocalCacheSettings settings) {
            if (this.usedLegacyCacheSettings) {
                throw new IllegalStateException("Deprecated setPersistenceEnabled() or setCacheSizeBytes() is already used, remove those first.");
            } else if ((settings instanceof MemoryCacheSettings) || (settings instanceof PersistentCacheSettings)) {
                this.cacheSettings = settings;
                return this;
            } else {
                throw new IllegalArgumentException("Only MemoryCacheSettings and PersistentCacheSettings are accepted");
            }
        }

        public String getHost() {
            return this.host;
        }

        public boolean isSslEnabled() {
            return this.sslEnabled;
        }

        @Deprecated
        public boolean isPersistenceEnabled() {
            return this.persistenceEnabled;
        }

        @Deprecated
        public long getCacheSizeBytes() {
            return this.cacheSizeBytes;
        }

        public FirebaseFirestoreSettings build() {
            if (this.sslEnabled || !this.host.equals(FirebaseFirestoreSettings.DEFAULT_HOST)) {
                return new FirebaseFirestoreSettings(this);
            }
            throw new IllegalStateException("You can't set the 'sslEnabled' setting unless you also set a non-default 'host'.");
        }
    }

    private FirebaseFirestoreSettings(Builder builder) {
        this.host = builder.host;
        this.sslEnabled = builder.sslEnabled;
        this.persistenceEnabled = builder.persistenceEnabled;
        this.cacheSizeBytes = builder.cacheSizeBytes;
        this.cacheSettings = builder.cacheSettings;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FirebaseFirestoreSettings that = (FirebaseFirestoreSettings) o;
        if (this.sslEnabled == that.sslEnabled && this.persistenceEnabled == that.persistenceEnabled && this.cacheSizeBytes == that.cacheSizeBytes && this.host.equals(that.host)) {
            return Objects.equals(this.cacheSettings, that.cacheSettings);
        }
        return false;
    }

    public int hashCode() {
        return (((((((this.host.hashCode() * 31) + (this.sslEnabled ? 1 : 0)) * 31) + (this.persistenceEnabled ? 1 : 0)) * 31) + ((int) (this.cacheSizeBytes ^ (this.cacheSizeBytes >>> 32)))) * 31) + (this.cacheSettings != null ? this.cacheSettings.hashCode() : 0);
    }

    public String toString() {
        if (("FirebaseFirestoreSettings{host=" + this.host + ", sslEnabled=" + this.sslEnabled + ", persistenceEnabled=" + this.persistenceEnabled + ", cacheSizeBytes=" + this.cacheSizeBytes + ", cacheSettings=" + this.cacheSettings) == null) {
            return "null";
        }
        return this.cacheSettings.toString() + "}";
    }

    public String getHost() {
        return this.host;
    }

    public boolean isSslEnabled() {
        return this.sslEnabled;
    }

    @Deprecated
    public boolean isPersistenceEnabled() {
        if (this.cacheSettings != null) {
            return this.cacheSettings instanceof PersistentCacheSettings;
        }
        return this.persistenceEnabled;
    }

    @Deprecated
    public long getCacheSizeBytes() {
        if (this.cacheSettings == null) {
            return this.cacheSizeBytes;
        }
        if (this.cacheSettings instanceof PersistentCacheSettings) {
            return ((PersistentCacheSettings) this.cacheSettings).getSizeBytes();
        }
        MemoryCacheSettings memorySettings = (MemoryCacheSettings) this.cacheSettings;
        if (memorySettings.getGarbageCollectorSettings() instanceof MemoryLruGcSettings) {
            return ((MemoryLruGcSettings) memorySettings.getGarbageCollectorSettings()).getSizeBytes();
        }
        return -1;
    }

    @Nullable
    public LocalCacheSettings getCacheSettings() {
        return this.cacheSettings;
    }
}
