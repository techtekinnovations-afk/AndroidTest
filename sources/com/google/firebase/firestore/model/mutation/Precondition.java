package com.google.firebase.firestore.model.mutation;

import com.google.firebase.firestore.model.MutableDocument;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.util.Assert;

public final class Precondition {
    public static final Precondition NONE = new Precondition((SnapshotVersion) null, (Boolean) null);
    private final Boolean exists;
    private final SnapshotVersion updateTime;

    private Precondition(SnapshotVersion updateTime2, Boolean exists2) {
        Assert.hardAssert(updateTime2 == null || exists2 == null, "Precondition can specify \"exists\" or \"updateTime\" but not both", new Object[0]);
        this.updateTime = updateTime2;
        this.exists = exists2;
    }

    public static Precondition exists(boolean exists2) {
        return new Precondition((SnapshotVersion) null, Boolean.valueOf(exists2));
    }

    public static Precondition updateTime(SnapshotVersion version) {
        return new Precondition(version, (Boolean) null);
    }

    public boolean isNone() {
        return this.updateTime == null && this.exists == null;
    }

    public SnapshotVersion getUpdateTime() {
        return this.updateTime;
    }

    public Boolean getExists() {
        return this.exists;
    }

    public boolean isValidFor(MutableDocument doc) {
        if (this.updateTime != null) {
            if (!doc.isFoundDocument() || !doc.getVersion().equals(this.updateTime)) {
                return false;
            }
            return true;
        } else if (this.exists == null) {
            Assert.hardAssert(isNone(), "Precondition should be empty", new Object[0]);
            return true;
        } else if (this.exists.booleanValue() == doc.isFoundDocument()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Precondition that = (Precondition) o;
        if (this.updateTime == null ? that.updateTime != null : !this.updateTime.equals(that.updateTime)) {
            return false;
        }
        if (this.exists != null) {
            return this.exists.equals(that.exists);
        }
        if (that.exists == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (this.updateTime != null ? this.updateTime.hashCode() : 0) * 31;
        if (this.exists != null) {
            i = this.exists.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        if (isNone()) {
            return "Precondition{<none>}";
        }
        if (this.updateTime != null) {
            return "Precondition{updateTime=" + this.updateTime + "}";
        }
        if (this.exists != null) {
            return "Precondition{exists=" + this.exists + "}";
        }
        throw Assert.fail("Invalid Precondition", new Object[0]);
    }
}
