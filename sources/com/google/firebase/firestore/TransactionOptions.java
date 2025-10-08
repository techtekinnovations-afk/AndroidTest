package com.google.firebase.firestore;

public final class TransactionOptions {
    static final TransactionOptions DEFAULT = new Builder().build();
    static final int DEFAULT_MAX_ATTEMPTS_COUNT = 5;
    /* access modifiers changed from: private */
    public final int maxAttempts;

    private TransactionOptions(int maxAttempts2) {
        this.maxAttempts = maxAttempts2;
    }

    public static final class Builder {
        private int maxAttempts = 5;

        public Builder() {
        }

        public Builder(TransactionOptions options) {
            this.maxAttempts = options.maxAttempts;
        }

        public Builder setMaxAttempts(int maxAttempts2) {
            if (maxAttempts2 >= 1) {
                this.maxAttempts = maxAttempts2;
                return this;
            }
            throw new IllegalArgumentException("Max attempts must be at least 1");
        }

        public TransactionOptions build() {
            return new TransactionOptions(this.maxAttempts);
        }
    }

    public int getMaxAttempts() {
        return this.maxAttempts;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (this.maxAttempts == ((TransactionOptions) o).maxAttempts) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.maxAttempts;
    }

    public String toString() {
        return "TransactionOptions{maxAttempts=" + this.maxAttempts + '}';
    }
}
