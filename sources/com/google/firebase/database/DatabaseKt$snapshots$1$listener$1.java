package com.google.firebase.database;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.channels.ChannelsKt;
import kotlinx.coroutines.channels.ProducerScope;

@Metadata(d1 = {"\u0000\u001f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"com/google/firebase/database/DatabaseKt$snapshots$1$listener$1", "Lcom/google/firebase/database/ValueEventListener;", "onDataChange", "", "snapshot", "Lcom/google/firebase/database/DataSnapshot;", "onCancelled", "error", "Lcom/google/firebase/database/DatabaseError;", "com.google.firebase-firebase-database"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: Database.kt */
public final class DatabaseKt$snapshots$1$listener$1 implements ValueEventListener {
    final /* synthetic */ ProducerScope<DataSnapshot> $$this$callbackFlow;
    final /* synthetic */ Query $this_snapshots;

    DatabaseKt$snapshots$1$listener$1(Query $receiver, ProducerScope<? super DataSnapshot> $$this$callbackFlow2) {
        this.$this_snapshots = $receiver;
        this.$$this$callbackFlow = $$this$callbackFlow2;
    }

    /* access modifiers changed from: private */
    public static final void onDataChange$lambda$0(ProducerScope $$this$callbackFlow2, DataSnapshot $snapshot) {
        ChannelsKt.trySendBlocking($$this$callbackFlow2, $snapshot);
    }

    public void onDataChange(DataSnapshot snapshot) {
        Intrinsics.checkNotNullParameter(snapshot, "snapshot");
        this.$this_snapshots.repo.scheduleNow(new DatabaseKt$snapshots$1$listener$1$$ExternalSyntheticLambda0(this.$$this$callbackFlow, snapshot));
    }

    public void onCancelled(DatabaseError error) {
        Intrinsics.checkNotNullParameter(error, "error");
        CoroutineScopeKt.cancel(this.$$this$callbackFlow, "Error getting Query snapshot", error.toException());
    }
}
