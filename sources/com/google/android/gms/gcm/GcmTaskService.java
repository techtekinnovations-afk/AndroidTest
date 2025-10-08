package com.google.android.gms.gcm;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.util.UidVerifier;
import com.google.android.gms.internal.gcm.zzg;
import com.google.android.gms.internal.gcm.zzj;
import com.google.android.gms.internal.gcm.zzl;
import com.google.android.gms.internal.gcm.zzm;
import com.google.android.gms.internal.gcm.zzp;
import com.google.android.gms.internal.gcm.zzq;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

public abstract class GcmTaskService extends Service {
    public static final String SERVICE_ACTION_EXECUTE_TASK = "com.google.android.gms.gcm.ACTION_TASK_READY";
    public static final String SERVICE_ACTION_INITIALIZE = "com.google.android.gms.gcm.SERVICE_ACTION_INITIALIZE";
    public static final String SERVICE_PERMISSION = "com.google.android.gms.permission.BIND_NETWORK_TASK_SERVICE";
    /* access modifiers changed from: private */
    public ComponentName componentName;
    /* access modifiers changed from: private */
    public final Object lock = new Object();
    /* access modifiers changed from: private */
    public zzl zzg;
    /* access modifiers changed from: private */
    public int zzu;
    private ExecutorService zzv;
    private Messenger zzw;
    /* access modifiers changed from: private */
    public GcmNetworkManager zzx;

    public abstract int onRunTask(TaskParams taskParams);

    class zzd extends zzj {
        zzd(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            Messenger messenger;
            if (!UidVerifier.uidHasPackageName(GcmTaskService.this, message.sendingUid, "com.google.android.gms")) {
                Log.e("GcmTaskService", "unable to verify presence of Google Play Services");
                return;
            }
            switch (message.what) {
                case 1:
                    Bundle data = message.getData();
                    if (!data.isEmpty() && (messenger = message.replyTo) != null) {
                        String string = data.getString("tag");
                        ArrayList parcelableArrayList = data.getParcelableArrayList("triggered_uris");
                        long j = data.getLong("max_exec_duration", 180);
                        if (!GcmTaskService.this.zzg(string)) {
                            GcmTaskService.this.zzd(new zze(string, messenger, data.getBundle("extras"), j, (List<Uri>) parcelableArrayList));
                            return;
                        }
                        return;
                    }
                    return;
                case 2:
                    if (Log.isLoggable("GcmTaskService", 3)) {
                        String valueOf = String.valueOf(message);
                        Log.d("GcmTaskService", new StringBuilder(String.valueOf(valueOf).length() + 45).append("ignoring unimplemented stop message for now: ").append(valueOf).toString());
                        return;
                    }
                    return;
                case 4:
                    GcmTaskService.this.onInitializeTasks();
                    return;
                default:
                    String valueOf2 = String.valueOf(message);
                    Log.e("GcmTaskService", new StringBuilder(String.valueOf(valueOf2).length() + 31).append("Unrecognized message received: ").append(valueOf2).toString());
                    return;
            }
        }
    }

    public void onCreate() {
        super.onCreate();
        this.zzx = GcmNetworkManager.getInstance(this);
        this.zzv = zzg.zzaa().zzd(10, new zze(this), 10);
        this.zzw = new Messenger(new zzd(Looper.getMainLooper()));
        this.componentName = new ComponentName(this, getClass());
        zzm.zzab();
        getClass();
        this.zzg = zzm.zzdk;
    }

    class zze implements Runnable {
        private final Bundle extras;
        private final String tag;
        private final List<Uri> zzaa;
        private final long zzab;
        private final zzg zzac;
        private final Messenger zzad;

        zze(String str, IBinder iBinder, Bundle bundle, long j, List<Uri> list) {
            zzg zzg;
            this.tag = str;
            if (iBinder == null) {
                zzg = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.gcm.INetworkTaskCallback");
                if (queryLocalInterface instanceof zzg) {
                    zzg = (zzg) queryLocalInterface;
                } else {
                    zzg = new zzh(iBinder);
                }
            }
            this.zzac = zzg;
            this.extras = bundle;
            this.zzab = j;
            this.zzaa = list;
            this.zzad = null;
        }

        zze(String str, Messenger messenger, Bundle bundle, long j, List<Uri> list) {
            this.tag = str;
            this.zzad = messenger;
            this.extras = bundle;
            this.zzab = j;
            this.zzaa = list;
            this.zzac = null;
        }

        public final void run() {
            String valueOf = String.valueOf("nts:client:onRunTask:");
            String valueOf2 = String.valueOf(this.tag);
            zzp zzp = new zzp(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
            try {
                TaskParams taskParams = new TaskParams(this.tag, this.extras, this.zzab, this.zzaa);
                GcmTaskService.this.zzg.zzd("onRunTask", zzp.zzdo);
                zze(GcmTaskService.this.onRunTask(taskParams));
                zzd((Throwable) null, zzp);
            } catch (Throwable th) {
                Throwable th2 = th;
                try {
                    throw th2;
                } catch (Throwable th3) {
                    zzd(th2, zzp);
                    throw th3;
                }
            }
        }

        /* access modifiers changed from: private */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x005c, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void zze(int r6) {
            /*
                r5 = this;
                com.google.android.gms.gcm.GcmTaskService r0 = com.google.android.gms.gcm.GcmTaskService.this
                java.lang.Object r0 = r0.lock
                monitor-enter(r0)
                com.google.android.gms.gcm.GcmTaskService r1 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ RemoteException -> 0x00d1 }
                com.google.android.gms.gcm.GcmNetworkManager r1 = r1.zzx     // Catch:{ RemoteException -> 0x00d1 }
                java.lang.String r2 = r5.tag     // Catch:{ RemoteException -> 0x00d1 }
                com.google.android.gms.gcm.GcmTaskService r3 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ RemoteException -> 0x00d1 }
                android.content.ComponentName r3 = r3.componentName     // Catch:{ RemoteException -> 0x00d1 }
                java.lang.String r3 = r3.getClassName()     // Catch:{ RemoteException -> 0x00d1 }
                boolean r1 = r1.zzf(r2, r3)     // Catch:{ RemoteException -> 0x00d1 }
                if (r1 == 0) goto L_0x005d
                com.google.android.gms.gcm.GcmTaskService r6 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                com.google.android.gms.gcm.GcmNetworkManager r6 = r6.zzx     // Catch:{ all -> 0x016b }
                java.lang.String r1 = r5.tag     // Catch:{ all -> 0x016b }
                com.google.android.gms.gcm.GcmTaskService r2 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                android.content.ComponentName r2 = r2.componentName     // Catch:{ all -> 0x016b }
                java.lang.String r2 = r2.getClassName()     // Catch:{ all -> 0x016b }
                r6.zze(r1, r2)     // Catch:{ all -> 0x016b }
                boolean r6 = r5.zzg()     // Catch:{ all -> 0x016b }
                if (r6 != 0) goto L_0x005b
                com.google.android.gms.gcm.GcmTaskService r6 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                com.google.android.gms.gcm.GcmNetworkManager r6 = r6.zzx     // Catch:{ all -> 0x016b }
                com.google.android.gms.gcm.GcmTaskService r1 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                android.content.ComponentName r1 = r1.componentName     // Catch:{ all -> 0x016b }
                java.lang.String r1 = r1.getClassName()     // Catch:{ all -> 0x016b }
                boolean r6 = r6.zzf(r1)     // Catch:{ all -> 0x016b }
                if (r6 != 0) goto L_0x005b
                com.google.android.gms.gcm.GcmTaskService r6 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                com.google.android.gms.gcm.GcmTaskService r1 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                int r1 = r1.zzu     // Catch:{ all -> 0x016b }
                r6.stopSelf(r1)     // Catch:{ all -> 0x016b }
            L_0x005b:
                monitor-exit(r0)     // Catch:{ all -> 0x016b }
                return
            L_0x005d:
                boolean r1 = r5.zzg()     // Catch:{ RemoteException -> 0x00d1 }
                if (r1 == 0) goto L_0x008d
                android.os.Messenger r1 = r5.zzad     // Catch:{ RemoteException -> 0x00d1 }
                android.os.Message r2 = android.os.Message.obtain()     // Catch:{ RemoteException -> 0x00d1 }
                r3 = 3
                r2.what = r3     // Catch:{ RemoteException -> 0x00d1 }
                r2.arg1 = r6     // Catch:{ RemoteException -> 0x00d1 }
                android.os.Bundle r6 = new android.os.Bundle     // Catch:{ RemoteException -> 0x00d1 }
                r6.<init>()     // Catch:{ RemoteException -> 0x00d1 }
                java.lang.String r3 = "component"
                com.google.android.gms.gcm.GcmTaskService r4 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ RemoteException -> 0x00d1 }
                android.content.ComponentName r4 = r4.componentName     // Catch:{ RemoteException -> 0x00d1 }
                r6.putParcelable(r3, r4)     // Catch:{ RemoteException -> 0x00d1 }
                java.lang.String r3 = "tag"
                java.lang.String r4 = r5.tag     // Catch:{ RemoteException -> 0x00d1 }
                r6.putString(r3, r4)     // Catch:{ RemoteException -> 0x00d1 }
                r2.setData(r6)     // Catch:{ RemoteException -> 0x00d1 }
                r1.send(r2)     // Catch:{ RemoteException -> 0x00d1 }
                goto L_0x0092
            L_0x008d:
                com.google.android.gms.gcm.zzg r1 = r5.zzac     // Catch:{ RemoteException -> 0x00d1 }
                r1.zzf(r6)     // Catch:{ RemoteException -> 0x00d1 }
            L_0x0092:
                com.google.android.gms.gcm.GcmTaskService r6 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                com.google.android.gms.gcm.GcmNetworkManager r6 = r6.zzx     // Catch:{ all -> 0x016b }
                java.lang.String r1 = r5.tag     // Catch:{ all -> 0x016b }
                com.google.android.gms.gcm.GcmTaskService r2 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                android.content.ComponentName r2 = r2.componentName     // Catch:{ all -> 0x016b }
                java.lang.String r2 = r2.getClassName()     // Catch:{ all -> 0x016b }
                r6.zze(r1, r2)     // Catch:{ all -> 0x016b }
                boolean r6 = r5.zzg()     // Catch:{ all -> 0x016b }
                if (r6 != 0) goto L_0x012c
                com.google.android.gms.gcm.GcmTaskService r6 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                com.google.android.gms.gcm.GcmNetworkManager r6 = r6.zzx     // Catch:{ all -> 0x016b }
                com.google.android.gms.gcm.GcmTaskService r1 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                android.content.ComponentName r1 = r1.componentName     // Catch:{ all -> 0x016b }
                java.lang.String r1 = r1.getClassName()     // Catch:{ all -> 0x016b }
                boolean r6 = r6.zzf(r1)     // Catch:{ all -> 0x016b }
                if (r6 != 0) goto L_0x012c
                com.google.android.gms.gcm.GcmTaskService r6 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                com.google.android.gms.gcm.GcmTaskService r1 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                int r1 = r1.zzu     // Catch:{ all -> 0x016b }
                r6.stopSelf(r1)     // Catch:{ all -> 0x016b }
                goto L_0x012c
            L_0x00cf:
                r6 = move-exception
                goto L_0x012e
            L_0x00d1:
                r6 = move-exception
                java.lang.String r6 = "GcmTaskService"
                java.lang.String r1 = "Error reporting result of operation to scheduler for "
                java.lang.String r2 = r5.tag     // Catch:{ all -> 0x00cf }
                java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x00cf }
                int r3 = r2.length()     // Catch:{ all -> 0x00cf }
                if (r3 == 0) goto L_0x00e7
                java.lang.String r1 = r1.concat(r2)     // Catch:{ all -> 0x00cf }
                goto L_0x00ed
            L_0x00e7:
                java.lang.String r2 = new java.lang.String     // Catch:{ all -> 0x00cf }
                r2.<init>(r1)     // Catch:{ all -> 0x00cf }
                r1 = r2
            L_0x00ed:
                android.util.Log.e(r6, r1)     // Catch:{ all -> 0x00cf }
                com.google.android.gms.gcm.GcmTaskService r6 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                com.google.android.gms.gcm.GcmNetworkManager r6 = r6.zzx     // Catch:{ all -> 0x016b }
                java.lang.String r1 = r5.tag     // Catch:{ all -> 0x016b }
                com.google.android.gms.gcm.GcmTaskService r2 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                android.content.ComponentName r2 = r2.componentName     // Catch:{ all -> 0x016b }
                java.lang.String r2 = r2.getClassName()     // Catch:{ all -> 0x016b }
                r6.zze(r1, r2)     // Catch:{ all -> 0x016b }
                boolean r6 = r5.zzg()     // Catch:{ all -> 0x016b }
                if (r6 != 0) goto L_0x012c
                com.google.android.gms.gcm.GcmTaskService r6 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                com.google.android.gms.gcm.GcmNetworkManager r6 = r6.zzx     // Catch:{ all -> 0x016b }
                com.google.android.gms.gcm.GcmTaskService r1 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                android.content.ComponentName r1 = r1.componentName     // Catch:{ all -> 0x016b }
                java.lang.String r1 = r1.getClassName()     // Catch:{ all -> 0x016b }
                boolean r6 = r6.zzf(r1)     // Catch:{ all -> 0x016b }
                if (r6 != 0) goto L_0x012c
                com.google.android.gms.gcm.GcmTaskService r6 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                com.google.android.gms.gcm.GcmTaskService r1 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                int r1 = r1.zzu     // Catch:{ all -> 0x016b }
                r6.stopSelf(r1)     // Catch:{ all -> 0x016b }
            L_0x012c:
                monitor-exit(r0)     // Catch:{ all -> 0x016b }
                return
            L_0x012e:
                com.google.android.gms.gcm.GcmTaskService r1 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                com.google.android.gms.gcm.GcmNetworkManager r1 = r1.zzx     // Catch:{ all -> 0x016b }
                java.lang.String r2 = r5.tag     // Catch:{ all -> 0x016b }
                com.google.android.gms.gcm.GcmTaskService r3 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                android.content.ComponentName r3 = r3.componentName     // Catch:{ all -> 0x016b }
                java.lang.String r3 = r3.getClassName()     // Catch:{ all -> 0x016b }
                r1.zze(r2, r3)     // Catch:{ all -> 0x016b }
                boolean r1 = r5.zzg()     // Catch:{ all -> 0x016b }
                if (r1 != 0) goto L_0x016a
                com.google.android.gms.gcm.GcmTaskService r1 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                com.google.android.gms.gcm.GcmNetworkManager r1 = r1.zzx     // Catch:{ all -> 0x016b }
                com.google.android.gms.gcm.GcmTaskService r2 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                android.content.ComponentName r2 = r2.componentName     // Catch:{ all -> 0x016b }
                java.lang.String r2 = r2.getClassName()     // Catch:{ all -> 0x016b }
                boolean r1 = r1.zzf(r2)     // Catch:{ all -> 0x016b }
                if (r1 != 0) goto L_0x016a
                com.google.android.gms.gcm.GcmTaskService r1 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                com.google.android.gms.gcm.GcmTaskService r2 = com.google.android.gms.gcm.GcmTaskService.this     // Catch:{ all -> 0x016b }
                int r2 = r2.zzu     // Catch:{ all -> 0x016b }
                r1.stopSelf(r2)     // Catch:{ all -> 0x016b }
            L_0x016a:
                throw r6     // Catch:{ all -> 0x016b }
            L_0x016b:
                r6 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x016b }
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.gcm.GcmTaskService.zze.zze(int):void");
        }

        private final boolean zzg() {
            return this.zzad != null;
        }

        private static /* synthetic */ void zzd(Throwable th, zzp zzp) {
            if (th != null) {
                try {
                    zzp.close();
                } catch (Throwable th2) {
                    zzq.zzd(th, th2);
                }
            } else {
                zzp.close();
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        List<Runnable> shutdownNow = this.zzv.shutdownNow();
        if (!shutdownNow.isEmpty()) {
            Log.e("GcmTaskService", new StringBuilder(79).append("Shutting down, but not all tasks are finished executing. Remaining: ").append(shutdownNow.size()).toString());
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        Throwable th;
        if (intent == null) {
            zzd(i2);
            return 2;
        }
        try {
            intent.setExtrasClassLoader(PendingCallback.class.getClassLoader());
            String action = intent.getAction();
            if (SERVICE_ACTION_EXECUTE_TASK.equals(action)) {
                String stringExtra = intent.getStringExtra("tag");
                Parcelable parcelableExtra = intent.getParcelableExtra("callback");
                Bundle bundleExtra = intent.getBundleExtra("extras");
                ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("triggered_uris");
                long longExtra = intent.getLongExtra("max_exec_duration", 180);
                if (!(parcelableExtra instanceof PendingCallback)) {
                    try {
                        String packageName = getPackageName();
                        Log.e("GcmTaskService", new StringBuilder(String.valueOf(packageName).length() + 47 + String.valueOf(stringExtra).length()).append(packageName).append(" ").append(stringExtra).append(": Could not process request, invalid callback.").toString());
                        zzd(i2);
                        return 2;
                    } catch (Throwable th2) {
                        th = th2;
                        zzd(i2);
                        throw th;
                    }
                } else if (zzg(stringExtra)) {
                    zzd(i2);
                    return 2;
                } else {
                    try {
                        zzd(new zze(stringExtra, ((PendingCallback) parcelableExtra).zzan, bundleExtra, longExtra, (List<Uri>) parcelableArrayListExtra));
                    } catch (Throwable th3) {
                        th = th3;
                        th = th;
                        zzd(i2);
                        throw th;
                    }
                }
            } else {
                if (SERVICE_ACTION_INITIALIZE.equals(action)) {
                    onInitializeTasks();
                } else {
                    Log.e("GcmTaskService", new StringBuilder(String.valueOf(action).length() + 37).append("Unknown action received ").append(action).append(", terminating").toString());
                }
            }
            zzd(i2);
            return 2;
        } catch (Throwable th4) {
            th = th4;
            th = th;
            zzd(i2);
            throw th;
        }
    }

    /* access modifiers changed from: private */
    public final boolean zzg(String str) {
        boolean z;
        synchronized (this.lock) {
            boolean zzd2 = this.zzx.zzd(str, this.componentName.getClassName());
            z = !zzd2;
            if (!zzd2) {
                String packageName = getPackageName();
                Log.w("GcmTaskService", new StringBuilder(String.valueOf(packageName).length() + 44 + String.valueOf(str).length()).append(packageName).append(" ").append(str).append(": Task already running, won't start another").toString());
            }
        }
        return z;
    }

    private final void zzd(int i) {
        synchronized (this.lock) {
            this.zzu = i;
            if (!this.zzx.zzf(this.componentName.getClassName())) {
                stopSelf(this.zzu);
            }
        }
    }

    public IBinder onBind(Intent intent) {
        if (intent == null || !PlatformVersion.isAtLeastLollipop() || !SERVICE_ACTION_EXECUTE_TASK.equals(intent.getAction())) {
            return null;
        }
        return this.zzw.getBinder();
    }

    /* access modifiers changed from: private */
    public final void zzd(zze zze2) {
        try {
            this.zzv.execute(zze2);
        } catch (RejectedExecutionException e) {
            Log.e("GcmTaskService", "Executor is shutdown. onDestroy was called but main looper had an unprocessed start task message. The task will be retried with backoff delay.", e);
            zze2.zze(1);
        }
    }

    public void onInitializeTasks() {
    }
}
