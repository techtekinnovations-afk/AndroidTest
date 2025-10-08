package com.google.firebase.heartbeatinfo;

import androidx.datastore.preferences.core.MutablePreferences;
import kotlin.jvm.functions.Function1;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class HeartBeatInfoStorage$$ExternalSyntheticLambda4 implements Function1 {
    public final /* synthetic */ HeartBeatInfoStorage f$0;
    public final /* synthetic */ String f$1;

    public /* synthetic */ HeartBeatInfoStorage$$ExternalSyntheticLambda4(HeartBeatInfoStorage heartBeatInfoStorage, String str) {
        this.f$0 = heartBeatInfoStorage;
        this.f$1 = str;
    }

    public final Object invoke(Object obj) {
        return this.f$0.m1933lambda$postHeartBeatCleanUp$1$comgooglefirebaseheartbeatinfoHeartBeatInfoStorage(this.f$1, (MutablePreferences) obj);
    }
}
