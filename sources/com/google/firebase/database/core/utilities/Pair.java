package com.google.firebase.database.core.utilities;

public final class Pair<T, U> {
    private final T first;
    private final U second;

    public Pair(T first2, U second2) {
        this.first = first2;
        this.second = second2;
    }

    public T getFirst() {
        return this.first;
    }

    public U getSecond() {
        return this.second;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pair pair = (Pair) o;
        if (this.first == null ? pair.first != null : !this.first.equals(pair.first)) {
            return false;
        }
        if (this.second == null ? pair.second == null : this.second.equals(pair.second)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (this.first != null ? this.first.hashCode() : 0) * 31;
        if (this.second != null) {
            i = this.second.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        return "Pair(" + this.first + "," + this.second + ")";
    }
}
