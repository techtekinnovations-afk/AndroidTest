package com.google.firebase.database;

import com.google.android.gms.common.internal.ImagesContract;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u001c\u0010\t\u001a\u0004\u0018\u0001H\n\"\u0006\b\u0000\u0010\n\u0018\u0001*\u00020\u000bH\b¢\u0006\u0002\u0010\f\u001a\u001c\u0010\t\u001a\u0004\u0018\u0001H\n\"\u0006\b\u0000\u0010\n\u0018\u0001*\u00020\rH\b¢\u0006\u0002\u0010\u000e\u001a!\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\n0\u0010\"\n\b\u0000\u0010\n\u0018\u0001*\u00020\u0018*\u00020\u0011H\b\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u001b\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0010*\u00020\u00118F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013\"\u001b\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0010*\u00020\u00118F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0013¨\u0006\u0019"}, d2 = {"database", "Lcom/google/firebase/database/FirebaseDatabase;", "Lcom/google/firebase/Firebase;", "getDatabase", "(Lcom/google/firebase/Firebase;)Lcom/google/firebase/database/FirebaseDatabase;", "url", "", "app", "Lcom/google/firebase/FirebaseApp;", "getValue", "T", "Lcom/google/firebase/database/DataSnapshot;", "(Lcom/google/firebase/database/DataSnapshot;)Ljava/lang/Object;", "Lcom/google/firebase/database/MutableData;", "(Lcom/google/firebase/database/MutableData;)Ljava/lang/Object;", "snapshots", "Lkotlinx/coroutines/flow/Flow;", "Lcom/google/firebase/database/Query;", "getSnapshots", "(Lcom/google/firebase/database/Query;)Lkotlinx/coroutines/flow/Flow;", "childEvents", "Lcom/google/firebase/database/ChildEvent;", "getChildEvents", "values", "", "com.google.firebase-firebase-database"}, k = 2, mv = {2, 0, 0}, xi = 48)
/* compiled from: Database.kt */
public final class DatabaseKt {
    public static final FirebaseDatabase getDatabase(Firebase $this$database) {
        Intrinsics.checkNotNullParameter($this$database, "<this>");
        FirebaseDatabase instance = FirebaseDatabase.getInstance();
        Intrinsics.checkNotNullExpressionValue(instance, "getInstance(...)");
        return instance;
    }

    public static final FirebaseDatabase database(Firebase $this$database, String url) {
        Intrinsics.checkNotNullParameter($this$database, "<this>");
        Intrinsics.checkNotNullParameter(url, ImagesContract.URL);
        FirebaseDatabase instance = FirebaseDatabase.getInstance(url);
        Intrinsics.checkNotNullExpressionValue(instance, "getInstance(...)");
        return instance;
    }

    public static final FirebaseDatabase database(Firebase $this$database, FirebaseApp app) {
        Intrinsics.checkNotNullParameter($this$database, "<this>");
        Intrinsics.checkNotNullParameter(app, "app");
        FirebaseDatabase instance = FirebaseDatabase.getInstance(app);
        Intrinsics.checkNotNullExpressionValue(instance, "getInstance(...)");
        return instance;
    }

    public static final FirebaseDatabase database(Firebase $this$database, FirebaseApp app, String url) {
        Intrinsics.checkNotNullParameter($this$database, "<this>");
        Intrinsics.checkNotNullParameter(app, "app");
        Intrinsics.checkNotNullParameter(url, ImagesContract.URL);
        FirebaseDatabase instance = FirebaseDatabase.getInstance(app, url);
        Intrinsics.checkNotNullExpressionValue(instance, "getInstance(...)");
        return instance;
    }

    public static final /* synthetic */ <T> T getValue(DataSnapshot $this$getValue) {
        Intrinsics.checkNotNullParameter($this$getValue, "<this>");
        Intrinsics.needClassReification();
        return $this$getValue.getValue(new DatabaseKt$getValue$1());
    }

    public static final /* synthetic */ <T> T getValue(MutableData $this$getValue) {
        Intrinsics.checkNotNullParameter($this$getValue, "<this>");
        Intrinsics.needClassReification();
        return $this$getValue.getValue(new DatabaseKt$getValue$2());
    }

    public static final Flow<DataSnapshot> getSnapshots(Query $this$snapshots) {
        Intrinsics.checkNotNullParameter($this$snapshots, "<this>");
        return FlowKt.callbackFlow(new DatabaseKt$snapshots$1($this$snapshots, (Continuation<? super DatabaseKt$snapshots$1>) null));
    }

    public static final Flow<ChildEvent> getChildEvents(Query $this$childEvents) {
        Intrinsics.checkNotNullParameter($this$childEvents, "<this>");
        return FlowKt.callbackFlow(new DatabaseKt$childEvents$1($this$childEvents, (Continuation<? super DatabaseKt$childEvents$1>) null));
    }

    public static final /* synthetic */ <T> Flow<T> values(Query $this$values) {
        Intrinsics.checkNotNullParameter($this$values, "<this>");
        Intrinsics.needClassReification();
        return new DatabaseKt$values$$inlined$map$1(getSnapshots($this$values));
    }
}
