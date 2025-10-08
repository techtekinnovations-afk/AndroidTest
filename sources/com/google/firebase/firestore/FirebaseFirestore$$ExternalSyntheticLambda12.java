package com.google.firebase.firestore;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.util.Function;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class FirebaseFirestore$$ExternalSyntheticLambda12 implements Function {
    public final Object apply(Object obj) {
        return Tasks.forException(new FirebaseFirestoreException("Persistence cannot be cleared while the firestore instance is running.", FirebaseFirestoreException.Code.FAILED_PRECONDITION));
    }
}
