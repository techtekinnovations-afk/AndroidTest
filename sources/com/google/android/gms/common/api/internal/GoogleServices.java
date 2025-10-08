package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.android.gms.common.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.StringResourceValueReader;
import com.google.android.gms.common.internal.zzah;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;

@Deprecated
/* compiled from: com.google.android.gms:play-services-basement@@18.3.0 */
public final class GoogleServices {
    private static final Object zza = new Object();
    private static GoogleServices zzb;
    private final String zzc;
    private final Status zzd;
    private final boolean zze;
    private final boolean zzf;

    GoogleServices(Context context) {
        boolean z;
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("google_app_measurement_enable", TypedValues.Custom.S_INT, resources.getResourcePackageName(R.string.common_google_play_services_unknown_issue));
        boolean z2 = true;
        if (identifier != 0) {
            int integer = resources.getInteger(identifier);
            if (integer != 0) {
                z = false;
            } else {
                z = true;
            }
            z2 = integer == 0 ? false : z2;
            this.zzf = z;
        } else {
            this.zzf = false;
        }
        this.zze = z2;
        String zzb2 = zzah.zzb(context);
        zzb2 = zzb2 == null ? new StringResourceValueReader(context).getString("google_app_id") : zzb2;
        if (TextUtils.isEmpty(zzb2)) {
            this.zzd = new Status(10, "Missing google app id value from from string resources with name google_app_id.");
            this.zzc = null;
            return;
        }
        this.zzc = zzb2;
        this.zzd = Status.RESULT_SUCCESS;
    }

    GoogleServices(String str, boolean z) {
        this.zzc = str;
        this.zzd = Status.RESULT_SUCCESS;
        this.zze = z;
        this.zzf = !z;
    }

    private static GoogleServices checkInitialized(String methodName) {
        GoogleServices googleServices;
        synchronized (zza) {
            googleServices = zzb;
            if (googleServices == null) {
                throw new IllegalStateException("Initialize must be called before " + methodName + ".");
            }
        }
        return googleServices;
    }

    static void clearInstanceForTest() {
        synchronized (zza) {
            zzb = null;
        }
    }

    public static String getGoogleAppId() {
        return checkInitialized("getGoogleAppId").zzc;
    }

    public static Status initialize(Context context) {
        Status status;
        Preconditions.checkNotNull(context, "Context must not be null.");
        synchronized (zza) {
            if (zzb == null) {
                zzb = new GoogleServices(context);
            }
            status = zzb.zzd;
        }
        return status;
    }

    public static boolean isMeasurementEnabled() {
        GoogleServices checkInitialized = checkInitialized("isMeasurementEnabled");
        return checkInitialized.zzd.isSuccess() && checkInitialized.zze;
    }

    public static boolean isMeasurementExplicitlyDisabled() {
        return checkInitialized("isMeasurementExplicitlyDisabled").zzf;
    }

    /* access modifiers changed from: package-private */
    public Status checkGoogleAppId(String appId) {
        String str = this.zzc;
        if (str == null || str.equals(appId)) {
            return Status.RESULT_SUCCESS;
        }
        String str2 = this.zzc;
        return new Status(10, "Initialize was called with two different Google App IDs.  Only the first app ID will be used: '" + str2 + "'.");
    }

    @ResultIgnorabilityUnspecified
    public static Status initialize(Context context, String appId, boolean isMeasurementEnabled) {
        Preconditions.checkNotNull(context, "Context must not be null.");
        Preconditions.checkNotEmpty(appId, "App ID must be nonempty.");
        synchronized (zza) {
            GoogleServices googleServices = zzb;
            if (googleServices != null) {
                Status checkGoogleAppId = googleServices.checkGoogleAppId(appId);
                return checkGoogleAppId;
            }
            zzb = new GoogleServices(appId, isMeasurementEnabled);
            Status status = zzb.zzd;
            return status;
        }
    }
}
