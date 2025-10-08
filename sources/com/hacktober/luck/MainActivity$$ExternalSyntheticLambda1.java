package com.hacktober.luck;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class MainActivity$$ExternalSyntheticLambda1 implements OnSuccessListener {
    public final /* synthetic */ MainActivity f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ MainActivity$$ExternalSyntheticLambda1(MainActivity mainActivity, int i) {
        this.f$0 = mainActivity;
        this.f$1 = i;
    }

    public final void onSuccess(Object obj) {
        this.f$0.m0lambda$play$1$comhacktoberluckMainActivity(this.f$1, (DocumentSnapshot) obj);
    }
}
