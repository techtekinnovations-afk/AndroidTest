package com.google.firebase.firestore;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firebase.firestore.util.Executors;
import com.google.firebase.firestore.util.Preconditions;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AggregateQuery {
    private final List<AggregateField> aggregateFieldList;
    private final Query query;

    AggregateQuery(Query query2, List<AggregateField> aggregateFieldList2) {
        this.query = query2;
        this.aggregateFieldList = aggregateFieldList2;
    }

    public Query getQuery() {
        return this.query;
    }

    public List<AggregateField> getAggregateFields() {
        return this.aggregateFieldList;
    }

    public Task<AggregateQuerySnapshot> get(AggregateSource source) {
        Preconditions.checkNotNull(source, "AggregateSource must not be null");
        TaskCompletionSource<AggregateQuerySnapshot> tcs = new TaskCompletionSource<>();
        ((Task) this.query.firestore.callClient(new AggregateQuery$$ExternalSyntheticLambda0(this))).continueWith(Executors.DIRECT_EXECUTOR, new AggregateQuery$$ExternalSyntheticLambda1(this, tcs));
        return tcs.getTask();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$get$0$com-google-firebase-firestore-AggregateQuery  reason: not valid java name */
    public /* synthetic */ Task m1788lambda$get$0$comgooglefirebasefirestoreAggregateQuery(FirestoreClient client) {
        return client.runAggregateQuery(this.query.query, this.aggregateFieldList);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$get$1$com-google-firebase-firestore-AggregateQuery  reason: not valid java name */
    public /* synthetic */ Object m1789lambda$get$1$comgooglefirebasefirestoreAggregateQuery(TaskCompletionSource tcs, Task task) throws Exception {
        if (task.isSuccessful()) {
            tcs.setResult(new AggregateQuerySnapshot(this, (Map) task.getResult()));
            return null;
        }
        tcs.setException(task.getException());
        return null;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof AggregateQuery)) {
            return false;
        }
        AggregateQuery other = (AggregateQuery) object;
        if (!this.query.equals(other.query) || !this.aggregateFieldList.equals(other.aggregateFieldList)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.query, this.aggregateFieldList});
    }
}
