package com.google.firebase.database;

import com.google.firebase.database.ChildEvent;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.channels.ChannelsKt;
import kotlinx.coroutines.channels.ProducerScope;

@Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J\u001a\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u001a\u0010\n\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J\u0010\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u0016¨\u0006\u000e"}, d2 = {"com/google/firebase/database/DatabaseKt$childEvents$1$listener$1", "Lcom/google/firebase/database/ChildEventListener;", "onChildAdded", "", "snapshot", "Lcom/google/firebase/database/DataSnapshot;", "previousChildName", "", "onChildChanged", "onChildRemoved", "onChildMoved", "onCancelled", "error", "Lcom/google/firebase/database/DatabaseError;", "com.google.firebase-firebase-database"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* compiled from: Database.kt */
public final class DatabaseKt$childEvents$1$listener$1 implements ChildEventListener {
    final /* synthetic */ ProducerScope<ChildEvent> $$this$callbackFlow;
    final /* synthetic */ Query $this_childEvents;

    DatabaseKt$childEvents$1$listener$1(Query $receiver, ProducerScope<? super ChildEvent> $$this$callbackFlow2) {
        this.$this_childEvents = $receiver;
        this.$$this$callbackFlow = $$this$callbackFlow2;
    }

    /* access modifiers changed from: private */
    public static final void onChildAdded$lambda$0(ProducerScope $$this$callbackFlow2, DataSnapshot $snapshot, String $previousChildName) {
        ChannelsKt.trySendBlocking($$this$callbackFlow2, new ChildEvent.Added($snapshot, $previousChildName));
    }

    public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
        Intrinsics.checkNotNullParameter(snapshot, "snapshot");
        this.$this_childEvents.repo.scheduleNow(new DatabaseKt$childEvents$1$listener$1$$ExternalSyntheticLambda0(this.$$this$callbackFlow, snapshot, previousChildName));
    }

    /* access modifiers changed from: private */
    public static final void onChildChanged$lambda$1(ProducerScope $$this$callbackFlow2, DataSnapshot $snapshot, String $previousChildName) {
        ChannelsKt.trySendBlocking($$this$callbackFlow2, new ChildEvent.Changed($snapshot, $previousChildName));
    }

    public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
        Intrinsics.checkNotNullParameter(snapshot, "snapshot");
        this.$this_childEvents.repo.scheduleNow(new DatabaseKt$childEvents$1$listener$1$$ExternalSyntheticLambda3(this.$$this$callbackFlow, snapshot, previousChildName));
    }

    /* access modifiers changed from: private */
    public static final void onChildRemoved$lambda$2(ProducerScope $$this$callbackFlow2, DataSnapshot $snapshot) {
        ChannelsKt.trySendBlocking($$this$callbackFlow2, new ChildEvent.Removed($snapshot));
    }

    public void onChildRemoved(DataSnapshot snapshot) {
        Intrinsics.checkNotNullParameter(snapshot, "snapshot");
        this.$this_childEvents.repo.scheduleNow(new DatabaseKt$childEvents$1$listener$1$$ExternalSyntheticLambda1(this.$$this$callbackFlow, snapshot));
    }

    /* access modifiers changed from: private */
    public static final void onChildMoved$lambda$3(ProducerScope $$this$callbackFlow2, DataSnapshot $snapshot, String $previousChildName) {
        ChannelsKt.trySendBlocking($$this$callbackFlow2, new ChildEvent.Moved($snapshot, $previousChildName));
    }

    public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
        Intrinsics.checkNotNullParameter(snapshot, "snapshot");
        this.$this_childEvents.repo.scheduleNow(new DatabaseKt$childEvents$1$listener$1$$ExternalSyntheticLambda2(this.$$this$callbackFlow, snapshot, previousChildName));
    }

    public void onCancelled(DatabaseError error) {
        Intrinsics.checkNotNullParameter(error, "error");
        CoroutineScopeKt.cancel(this.$$this$callbackFlow, "Error getting Query childEvent", error.toException());
    }
}
