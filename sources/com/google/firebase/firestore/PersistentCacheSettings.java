package com.google.firebase.firestore;

public final class PersistentCacheSettings implements LocalCacheSettings {
    private final long sizeBytes;

    public static Builder newBuilder() {
        return new Builder();
    }

    private PersistentCacheSettings(long sizeBytes2) {
        this.sizeBytes = sizeBytes2;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (this.sizeBytes == ((PersistentCacheSettings) o).sizeBytes) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (int) (this.sizeBytes ^ (this.sizeBytes >>> 32));
    }

    public String toString() {
        return "PersistentCacheSettings{sizeBytes=" + this.sizeBytes + '}';
    }

    public long getSizeBytes() {
        return this.sizeBytes;
    }

    public static class Builder {
        private long sizeBytes;

        private Builder() {
            this.sizeBytes = 104857600;
        }

        public Builder setSizeBytes(long sizeBytes2) {
            this.sizeBytes = sizeBytes2;
            return this;
        }

        public PersistentCacheSettings build() {
            return new PersistentCacheSettings(this.sizeBytes);
        }
    }
}
