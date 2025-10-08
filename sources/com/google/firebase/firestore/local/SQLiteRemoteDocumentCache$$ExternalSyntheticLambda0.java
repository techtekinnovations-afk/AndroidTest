package com.google.firebase.firestore.local;

import com.google.firebase.firestore.util.Function;
import java.util.Map;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SQLiteRemoteDocumentCache$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ SQLiteRemoteDocumentCache f$0;
    public final /* synthetic */ byte[] f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ boolean f$4;
    public final /* synthetic */ String f$5;
    public final /* synthetic */ Function f$6;
    public final /* synthetic */ Map f$7;

    public /* synthetic */ SQLiteRemoteDocumentCache$$ExternalSyntheticLambda0(SQLiteRemoteDocumentCache sQLiteRemoteDocumentCache, byte[] bArr, int i, int i2, boolean z, String str, Function function, Map map) {
        this.f$0 = sQLiteRemoteDocumentCache;
        this.f$1 = bArr;
        this.f$2 = i;
        this.f$3 = i2;
        this.f$4 = z;
        this.f$5 = str;
        this.f$6 = function;
        this.f$7 = map;
    }

    public final void run() {
        this.f$0.m1883lambda$processRowInBackground$2$comgooglefirebasefirestorelocalSQLiteRemoteDocumentCache(this.f$1, this.f$2, this.f$3, this.f$4, this.f$5, this.f$6, this.f$7);
    }
}
