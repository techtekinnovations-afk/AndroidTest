package com.google.firebase.firestore;

import androidx.core.util.Consumer;
import com.google.firebase.firestore.core.FirestoreClient;
import java.io.InputStream;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FirebaseFirestore$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ InputStream f$0;
    public final /* synthetic */ LoadBundleTask f$1;

    public /* synthetic */ FirebaseFirestore$$ExternalSyntheticLambda1(InputStream inputStream, LoadBundleTask loadBundleTask) {
        this.f$0 = inputStream;
        this.f$1 = loadBundleTask;
    }

    public final void accept(Object obj) {
        ((FirestoreClient) obj).loadBundle(this.f$0, this.f$1);
    }
}
