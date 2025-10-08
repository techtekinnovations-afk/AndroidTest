package com.google.android.gms.gcm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.zzaf;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Deprecated
public class GoogleCloudMessaging {
    public static final String ERROR_MAIN_THREAD = "MAIN_THREAD";
    public static final String ERROR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
    public static final String INSTANCE_ID_SCOPE = "GCM";
    @Deprecated
    public static final String MESSAGE_TYPE_DELETED = "deleted_messages";
    @Deprecated
    public static final String MESSAGE_TYPE_MESSAGE = "gcm";
    @Deprecated
    public static final String MESSAGE_TYPE_SEND_ERROR = "send_error";
    @Deprecated
    public static final String MESSAGE_TYPE_SEND_EVENT = "send_event";
    private static GoogleCloudMessaging zzae;
    private static final AtomicInteger zzah = new AtomicInteger(1);
    private PendingIntent zzaf;
    private final Map<String, Handler> zzag = Collections.synchronizedMap(new ArrayMap());
    /* access modifiers changed from: private */
    public final BlockingQueue<Intent> zzai = new LinkedBlockingQueue();
    private final Messenger zzaj = new Messenger(new zzf(this, Looper.getMainLooper()));
    /* access modifiers changed from: private */
    public Context zzl;

    @Deprecated
    public static synchronized GoogleCloudMessaging getInstance(Context context) {
        GoogleCloudMessaging googleCloudMessaging;
        synchronized (GoogleCloudMessaging.class) {
            if (zzae == null) {
                zze(context);
                GoogleCloudMessaging googleCloudMessaging2 = new GoogleCloudMessaging();
                zzae = googleCloudMessaging2;
                googleCloudMessaging2.zzl = context.getApplicationContext();
            }
            googleCloudMessaging = zzae;
        }
        return googleCloudMessaging;
    }

    static void zze(Context context) {
        String packageName = context.getPackageName();
        Log.w("GCM", new StringBuilder(String.valueOf(packageName).length() + 48).append("GCM SDK is deprecated, ").append(packageName).append(" should update to use FCM").toString());
    }

    @Deprecated
    public void close() {
        zzae = null;
        zzd.zzk = null;
        zzh();
    }

    @Deprecated
    public void send(String str, String str2, Bundle bundle) throws IOException {
        send(str, str2, -1, bundle);
    }

    @Deprecated
    public void send(String str, String str2, long j, Bundle bundle) throws IOException {
        String str3;
        if (str != null) {
            String zzl2 = zzaf.zzl(this.zzl);
            if (zzl2 != null) {
                Intent intent = new Intent("com.google.android.gcm.intent.SEND");
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
                zze(intent);
                intent.setPackage(zzl2);
                intent.putExtra("google.to", str);
                intent.putExtra("google.message_id", str2);
                intent.putExtra("google.ttl", Long.toString(j));
                int indexOf = str.indexOf(64);
                if (indexOf > 0) {
                    str3 = str.substring(0, indexOf);
                } else {
                    str3 = str;
                }
                InstanceID.getInstance(this.zzl);
                intent.putExtra("google.from", InstanceID.zzp().zzf("", str3, "GCM"));
                if (zzl2.contains(".gsf")) {
                    Bundle bundle2 = new Bundle();
                    for (String str4 : bundle.keySet()) {
                        Object obj = bundle.get(str4);
                        if (obj instanceof String) {
                            String valueOf = String.valueOf(str4);
                            bundle2.putString(valueOf.length() != 0 ? "gcm.".concat(valueOf) : new String("gcm."), (String) obj);
                        }
                    }
                    bundle2.putString("google.to", str);
                    bundle2.putString("google.message_id", str2);
                    InstanceID.getInstance(this.zzl).zze("GCM", "upstream", bundle2);
                    return;
                }
                this.zzl.sendOrderedBroadcast(intent, "com.google.android.gtalkservice.permission.GTALK_SERVICE");
                return;
            }
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        throw new IllegalArgumentException("Missing 'to'");
    }

    /* access modifiers changed from: private */
    public final boolean zzd(Intent intent) {
        Handler remove;
        String stringExtra = intent.getStringExtra("In-Reply-To");
        if (stringExtra == null && intent.hasExtra("error")) {
            stringExtra = intent.getStringExtra("google.message_id");
        }
        if (stringExtra == null || (remove = this.zzag.remove(stringExtra)) == null) {
            return false;
        }
        Message obtain = Message.obtain();
        obtain.obj = intent;
        return remove.sendMessage(obtain);
    }

    @Deprecated
    public synchronized void unregister() throws IOException {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            InstanceID.getInstance(this.zzl).deleteInstanceID();
        } else {
            throw new IOException("MAIN_THREAD");
        }
    }

    @Deprecated
    public synchronized String register(String... strArr) throws IOException {
        return zzd(zzaf.zzk(this.zzl), strArr);
    }

    @Deprecated
    private final synchronized String zzd(boolean z, String... strArr) throws IOException {
        String zzl2 = zzaf.zzl(this.zzl);
        if (zzl2 == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        } else if (strArr == null || strArr.length == 0) {
            throw new IllegalArgumentException("No senderIds");
        } else {
            StringBuilder sb = new StringBuilder(strArr[0]);
            for (int i = 1; i < strArr.length; i++) {
                sb.append(',').append(strArr[i]);
            }
            String sb2 = sb.toString();
            Bundle bundle = new Bundle();
            if (zzl2.contains(".gsf")) {
                bundle.putString("legacy.sender", sb2);
                return InstanceID.getInstance(this.zzl).getToken(sb2, "GCM", bundle);
            }
            bundle.putString("sender", sb2);
            Intent zzd = zzd(bundle, z);
            if (zzd != null) {
                String stringExtra = zzd.getStringExtra("registration_id");
                if (stringExtra != null) {
                    return stringExtra;
                }
                String stringExtra2 = zzd.getStringExtra("error");
                if (stringExtra2 != null) {
                    throw new IOException(stringExtra2);
                }
                throw new IOException("SERVICE_NOT_AVAILABLE");
            }
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
    }

    @Deprecated
    private final Intent zzd(Bundle bundle, boolean z) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        } else if (zzf(this.zzl) >= 0) {
            Intent intent = new Intent(z ? "com.google.iid.TOKEN_REQUEST" : "com.google.android.c2dm.intent.REGISTER");
            intent.setPackage(zzaf.zzl(this.zzl));
            zze(intent);
            intent.putExtra("google.message_id", new StringBuilder(21).append("google.rpc").append(zzah.getAndIncrement()).toString());
            intent.putExtras(bundle);
            intent.putExtra("google.messenger", this.zzaj);
            if (z) {
                this.zzl.sendBroadcast(intent);
            } else {
                this.zzl.startService(intent);
            }
            try {
                return this.zzai.poll(30000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new IOException(e.getMessage());
            }
        } else {
            throw new IOException("Google Play Services missing");
        }
    }

    @Deprecated
    public String getMessageType(Intent intent) {
        if (!"com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())) {
            return null;
        }
        String stringExtra = intent.getStringExtra("message_type");
        if (stringExtra != null) {
            return stringExtra;
        }
        return MESSAGE_TYPE_MESSAGE;
    }

    private final synchronized void zze(Intent intent) {
        if (this.zzaf == null) {
            Intent intent2 = new Intent();
            intent2.setPackage("com.google.example.invalidpackage");
            this.zzaf = PendingIntent.getBroadcast(this.zzl, 0, intent2, 0);
        }
        intent.putExtra("app", this.zzaf);
    }

    private final synchronized void zzh() {
        if (this.zzaf != null) {
            this.zzaf.cancel();
            this.zzaf = null;
        }
    }

    public static int zzf(Context context) {
        String zzl2 = zzaf.zzl(context);
        if (zzl2 == null) {
            return -1;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(zzl2, 0);
            if (packageInfo != null) {
                return packageInfo.versionCode;
            }
            return -1;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }
}
