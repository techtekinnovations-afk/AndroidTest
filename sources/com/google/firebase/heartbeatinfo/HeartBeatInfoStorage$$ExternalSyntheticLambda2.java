package com.google.firebase.heartbeatinfo;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import kotlin.jvm.functions.Function1;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class HeartBeatInfoStorage$$ExternalSyntheticLambda2 implements Function1 {
    public final /* synthetic */ HeartBeatInfoStorage f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ Preferences.Key f$3;

    public /* synthetic */ HeartBeatInfoStorage$$ExternalSyntheticLambda2(HeartBeatInfoStorage heartBeatInfoStorage, String str, String str2, Preferences.Key key) {
        this.f$0 = heartBeatInfoStorage;
        this.f$1 = str;
        this.f$2 = str2;
        this.f$3 = key;
    }

    public final Object invoke(Object obj) {
        return this.f$0.m1934lambda$storeHeartBeat$2$comgooglefirebaseheartbeatinfoHeartBeatInfoStorage(this.f$1, this.f$2, this.f$3, (MutablePreferences) obj);
    }
}
