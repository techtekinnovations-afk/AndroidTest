package com.google.firebase.firestore;

import android.app.Activity;
import com.google.firebase.firestore.core.ActivityScope;
import com.google.firebase.firestore.core.AsyncEventListener;
import com.google.firebase.firestore.core.EventManager;
import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.util.Function;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class DocumentReference$$ExternalSyntheticLambda6 implements Function {
    public final /* synthetic */ Query f$0;
    public final /* synthetic */ EventManager.ListenOptions f$1;
    public final /* synthetic */ AsyncEventListener f$2;
    public final /* synthetic */ Activity f$3;

    public /* synthetic */ DocumentReference$$ExternalSyntheticLambda6(Query query, EventManager.ListenOptions listenOptions, AsyncEventListener asyncEventListener, Activity activity) {
        this.f$0 = query;
        this.f$1 = listenOptions;
        this.f$2 = asyncEventListener;
        this.f$3 = activity;
    }

    public final Object apply(Object obj) {
        return ActivityScope.bind(this.f$3, new DocumentReference$$ExternalSyntheticLambda8(this.f$2, (FirestoreClient) obj, ((FirestoreClient) obj).listen(this.f$0, this.f$1, this.f$2)));
    }
}
