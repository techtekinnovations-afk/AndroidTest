package com.google.firebase.firestore.local;

import android.database.Cursor;
import com.google.firebase.firestore.util.Consumer;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SQLiteLruReferenceDelegate$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ Consumer f$0;

    public /* synthetic */ SQLiteLruReferenceDelegate$$ExternalSyntheticLambda0(Consumer consumer) {
        this.f$0 = consumer;
    }

    public final void accept(Object obj) {
        this.f$0.accept(Long.valueOf(((Cursor) obj).getLong(0)));
    }
}
