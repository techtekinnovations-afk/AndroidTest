package com.google.firebase.database;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0004\u0005\u0006\u0007B\t\b\u0004¢\u0006\u0004\b\u0002\u0010\u0003\u0001\u0004\b\t\n\u000b¨\u0006\f"}, d2 = {"Lcom/google/firebase/database/ChildEvent;", "", "<init>", "()V", "Added", "Changed", "Removed", "Moved", "Lcom/google/firebase/database/ChildEvent$Added;", "Lcom/google/firebase/database/ChildEvent$Changed;", "Lcom/google/firebase/database/ChildEvent$Moved;", "Lcom/google/firebase/database/ChildEvent$Removed;", "com.google.firebase-firebase-database"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: ChildEvent.kt */
public abstract class ChildEvent {
    public /* synthetic */ ChildEvent(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private ChildEvent() {
    }

    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lcom/google/firebase/database/ChildEvent$Added;", "Lcom/google/firebase/database/ChildEvent;", "snapshot", "Lcom/google/firebase/database/DataSnapshot;", "previousChildName", "", "<init>", "(Lcom/google/firebase/database/DataSnapshot;Ljava/lang/String;)V", "getSnapshot", "()Lcom/google/firebase/database/DataSnapshot;", "getPreviousChildName", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "com.google.firebase-firebase-database"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: ChildEvent.kt */
    public static final class Added extends ChildEvent {
        private final String previousChildName;
        private final DataSnapshot snapshot;

        public static /* synthetic */ Added copy$default(Added added, DataSnapshot dataSnapshot, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                dataSnapshot = added.snapshot;
            }
            if ((i & 2) != 0) {
                str = added.previousChildName;
            }
            return added.copy(dataSnapshot, str);
        }

        public final DataSnapshot component1() {
            return this.snapshot;
        }

        public final String component2() {
            return this.previousChildName;
        }

        public final Added copy(DataSnapshot dataSnapshot, String str) {
            Intrinsics.checkNotNullParameter(dataSnapshot, "snapshot");
            return new Added(dataSnapshot, str);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Added)) {
                return false;
            }
            Added added = (Added) obj;
            return Intrinsics.areEqual((Object) this.snapshot, (Object) added.snapshot) && Intrinsics.areEqual((Object) this.previousChildName, (Object) added.previousChildName);
        }

        public int hashCode() {
            return (this.snapshot.hashCode() * 31) + (this.previousChildName == null ? 0 : this.previousChildName.hashCode());
        }

        public String toString() {
            return "Added(snapshot=" + this.snapshot + ", previousChildName=" + this.previousChildName + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Added(DataSnapshot snapshot2, String previousChildName2) {
            super((DefaultConstructorMarker) null);
            Intrinsics.checkNotNullParameter(snapshot2, "snapshot");
            this.snapshot = snapshot2;
            this.previousChildName = previousChildName2;
        }

        public final String getPreviousChildName() {
            return this.previousChildName;
        }

        public final DataSnapshot getSnapshot() {
            return this.snapshot;
        }
    }

    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lcom/google/firebase/database/ChildEvent$Changed;", "Lcom/google/firebase/database/ChildEvent;", "snapshot", "Lcom/google/firebase/database/DataSnapshot;", "previousChildName", "", "<init>", "(Lcom/google/firebase/database/DataSnapshot;Ljava/lang/String;)V", "getSnapshot", "()Lcom/google/firebase/database/DataSnapshot;", "getPreviousChildName", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "com.google.firebase-firebase-database"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: ChildEvent.kt */
    public static final class Changed extends ChildEvent {
        private final String previousChildName;
        private final DataSnapshot snapshot;

        public static /* synthetic */ Changed copy$default(Changed changed, DataSnapshot dataSnapshot, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                dataSnapshot = changed.snapshot;
            }
            if ((i & 2) != 0) {
                str = changed.previousChildName;
            }
            return changed.copy(dataSnapshot, str);
        }

        public final DataSnapshot component1() {
            return this.snapshot;
        }

        public final String component2() {
            return this.previousChildName;
        }

        public final Changed copy(DataSnapshot dataSnapshot, String str) {
            Intrinsics.checkNotNullParameter(dataSnapshot, "snapshot");
            return new Changed(dataSnapshot, str);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Changed)) {
                return false;
            }
            Changed changed = (Changed) obj;
            return Intrinsics.areEqual((Object) this.snapshot, (Object) changed.snapshot) && Intrinsics.areEqual((Object) this.previousChildName, (Object) changed.previousChildName);
        }

        public int hashCode() {
            return (this.snapshot.hashCode() * 31) + (this.previousChildName == null ? 0 : this.previousChildName.hashCode());
        }

        public String toString() {
            return "Changed(snapshot=" + this.snapshot + ", previousChildName=" + this.previousChildName + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Changed(DataSnapshot snapshot2, String previousChildName2) {
            super((DefaultConstructorMarker) null);
            Intrinsics.checkNotNullParameter(snapshot2, "snapshot");
            this.snapshot = snapshot2;
            this.previousChildName = previousChildName2;
        }

        public final String getPreviousChildName() {
            return this.previousChildName;
        }

        public final DataSnapshot getSnapshot() {
            return this.snapshot;
        }
    }

    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\t\u0010\b\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\t\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rHÖ\u0003J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0012"}, d2 = {"Lcom/google/firebase/database/ChildEvent$Removed;", "Lcom/google/firebase/database/ChildEvent;", "snapshot", "Lcom/google/firebase/database/DataSnapshot;", "<init>", "(Lcom/google/firebase/database/DataSnapshot;)V", "getSnapshot", "()Lcom/google/firebase/database/DataSnapshot;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "com.google.firebase-firebase-database"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: ChildEvent.kt */
    public static final class Removed extends ChildEvent {
        private final DataSnapshot snapshot;

        public static /* synthetic */ Removed copy$default(Removed removed, DataSnapshot dataSnapshot, int i, Object obj) {
            if ((i & 1) != 0) {
                dataSnapshot = removed.snapshot;
            }
            return removed.copy(dataSnapshot);
        }

        public final DataSnapshot component1() {
            return this.snapshot;
        }

        public final Removed copy(DataSnapshot dataSnapshot) {
            Intrinsics.checkNotNullParameter(dataSnapshot, "snapshot");
            return new Removed(dataSnapshot);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Removed) && Intrinsics.areEqual((Object) this.snapshot, (Object) ((Removed) obj).snapshot);
        }

        public int hashCode() {
            return this.snapshot.hashCode();
        }

        public String toString() {
            return "Removed(snapshot=" + this.snapshot + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Removed(DataSnapshot snapshot2) {
            super((DefaultConstructorMarker) null);
            Intrinsics.checkNotNullParameter(snapshot2, "snapshot");
            this.snapshot = snapshot2;
        }

        public final DataSnapshot getSnapshot() {
            return this.snapshot;
        }
    }

    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lcom/google/firebase/database/ChildEvent$Moved;", "Lcom/google/firebase/database/ChildEvent;", "snapshot", "Lcom/google/firebase/database/DataSnapshot;", "previousChildName", "", "<init>", "(Lcom/google/firebase/database/DataSnapshot;Ljava/lang/String;)V", "getSnapshot", "()Lcom/google/firebase/database/DataSnapshot;", "getPreviousChildName", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "com.google.firebase-firebase-database"}, k = 1, mv = {2, 0, 0}, xi = 48)
    /* compiled from: ChildEvent.kt */
    public static final class Moved extends ChildEvent {
        private final String previousChildName;
        private final DataSnapshot snapshot;

        public static /* synthetic */ Moved copy$default(Moved moved, DataSnapshot dataSnapshot, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                dataSnapshot = moved.snapshot;
            }
            if ((i & 2) != 0) {
                str = moved.previousChildName;
            }
            return moved.copy(dataSnapshot, str);
        }

        public final DataSnapshot component1() {
            return this.snapshot;
        }

        public final String component2() {
            return this.previousChildName;
        }

        public final Moved copy(DataSnapshot dataSnapshot, String str) {
            Intrinsics.checkNotNullParameter(dataSnapshot, "snapshot");
            return new Moved(dataSnapshot, str);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Moved)) {
                return false;
            }
            Moved moved = (Moved) obj;
            return Intrinsics.areEqual((Object) this.snapshot, (Object) moved.snapshot) && Intrinsics.areEqual((Object) this.previousChildName, (Object) moved.previousChildName);
        }

        public int hashCode() {
            return (this.snapshot.hashCode() * 31) + (this.previousChildName == null ? 0 : this.previousChildName.hashCode());
        }

        public String toString() {
            return "Moved(snapshot=" + this.snapshot + ", previousChildName=" + this.previousChildName + ')';
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Moved(DataSnapshot snapshot2, String previousChildName2) {
            super((DefaultConstructorMarker) null);
            Intrinsics.checkNotNullParameter(snapshot2, "snapshot");
            this.snapshot = snapshot2;
            this.previousChildName = previousChildName2;
        }

        public final String getPreviousChildName() {
            return this.previousChildName;
        }

        public final DataSnapshot getSnapshot() {
            return this.snapshot;
        }
    }
}
