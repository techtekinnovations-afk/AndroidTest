package com.google.firebase.heartbeatinfo;

import androidx.datastore.preferences.core.MutablePreferences;
import kotlin.jvm.functions.Function1;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class HeartBeatInfoStorage$$ExternalSyntheticLambda5 implements Function1 {
    public final /* synthetic */ long f$0;

    public /* synthetic */ HeartBeatInfoStorage$$ExternalSyntheticLambda5(long j) {
        this.f$0 = j;
    }

    public final Object invoke(Object obj) {
        return ((MutablePreferences) obj).set(HeartBeatInfoStorage.GLOBAL, Long.valueOf(this.f$0));
    }
}
