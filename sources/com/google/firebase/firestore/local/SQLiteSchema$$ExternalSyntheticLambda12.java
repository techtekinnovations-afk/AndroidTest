package com.google.firebase.firestore.local;

import android.database.Cursor;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.util.Consumer;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SQLiteSchema$$ExternalSyntheticLambda12 implements Consumer {
    public final /* synthetic */ Consumer f$0;

    public /* synthetic */ SQLiteSchema$$ExternalSyntheticLambda12(Consumer consumer) {
        this.f$0 = consumer;
    }

    public final void accept(Object obj) {
        this.f$0.accept((ResourcePath) EncodedPath.decodeResourcePath(((Cursor) obj).getString(0)).popLast());
    }
}
