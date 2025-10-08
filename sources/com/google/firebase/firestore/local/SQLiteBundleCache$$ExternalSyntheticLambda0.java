package com.google.firebase.firestore.local;

import android.database.Cursor;
import com.google.firebase.firestore.util.Function;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SQLiteBundleCache$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ String f$0;

    public /* synthetic */ SQLiteBundleCache$$ExternalSyntheticLambda0(String str) {
        this.f$0 = str;
    }

    public final Object apply(Object obj) {
        return SQLiteBundleCache.lambda$getBundleMetadata$0(this.f$0, (Cursor) obj);
    }
}
