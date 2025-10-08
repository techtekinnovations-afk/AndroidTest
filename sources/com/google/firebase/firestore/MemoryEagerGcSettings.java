package com.google.firebase.firestore;

public final class MemoryEagerGcSettings implements MemoryGarbageCollectorSettings {
    private MemoryEagerGcSettings() {
    }

    public static class Builder {
        private Builder() {
        }

        public MemoryEagerGcSettings build() {
            return new MemoryEagerGcSettings();
        }
    }

    public int hashCode() {
        return super.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "MemoryEagerGcSettings{}";
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
