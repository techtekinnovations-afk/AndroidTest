package androidx.core.location;

import android.os.CancellationSignal;
import androidx.core.location.LocationManagerCompat;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class LocationManagerCompat$$ExternalSyntheticLambda1 implements CancellationSignal.OnCancelListener {
    public final /* synthetic */ LocationManagerCompat.CancellableLocationListener f$0;

    public /* synthetic */ LocationManagerCompat$$ExternalSyntheticLambda1(LocationManagerCompat.CancellableLocationListener cancellableLocationListener) {
        this.f$0 = cancellableLocationListener;
    }

    public final void onCancel() {
        this.f$0.cancel();
    }
}
