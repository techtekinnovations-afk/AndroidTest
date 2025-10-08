package com.google.firebase.datastorage;

import androidx.datastore.core.CorruptionException;
import kotlin.jvm.functions.Function1;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class JavaDataStorage$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ JavaDataStorage f$0;

    public /* synthetic */ JavaDataStorage$$ExternalSyntheticLambda0(JavaDataStorage javaDataStorage) {
        this.f$0 = javaDataStorage;
    }

    public final Object invoke(Object obj) {
        return JavaDataStorage.dataStore_delegate$lambda$0(this.f$0, (CorruptionException) obj);
    }
}
