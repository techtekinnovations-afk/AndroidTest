package com.google.firebase.firestore.remote;

import com.google.firestore.v1.BloomFilter;

public final class ExistenceFilter {
    private final int count;
    private BloomFilter unchangedNames;

    public ExistenceFilter(int count2) {
        this.count = count2;
    }

    public ExistenceFilter(int count2, BloomFilter unchangedNames2) {
        this.count = count2;
        this.unchangedNames = unchangedNames2;
    }

    public int getCount() {
        return this.count;
    }

    public BloomFilter getUnchangedNames() {
        return this.unchangedNames;
    }

    public String toString() {
        return "ExistenceFilter{count=" + this.count + ", unchangedNames=" + this.unchangedNames + '}';
    }
}
