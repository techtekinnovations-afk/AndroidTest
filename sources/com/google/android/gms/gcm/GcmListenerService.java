package com.google.android.gms.gcm;

import android.os.Bundle;
import com.google.android.gms.iid.zze;
import com.google.android.gms.internal.gcm.zzl;
import com.google.android.gms.internal.gcm.zzm;
import java.util.Iterator;

@Deprecated
public class GcmListenerService extends zze {
    private zzl zzg = zzm.zzdk;

    public void onMessageReceived(String str, Bundle bundle) {
    }

    public void onDeletedMessages() {
    }

    public void onMessageSent(String str) {
    }

    public void onSendError(String str, String str2) {
    }

    public void onCreate() {
        super.onCreate();
        zzm.zzab();
        getClass();
        this.zzg = zzm.zzdk;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void handleIntent(android.content.Intent r9) {
        /*
            r8 = this;
            java.lang.String r0 = r9.getAction()
            java.lang.String r1 = "com.google.android.c2dm.intent.RECEIVE"
            boolean r0 = r1.equals(r0)
            java.lang.String r1 = "GcmListenerService"
            if (r0 != 0) goto L_0x002c
            java.lang.String r9 = r9.getAction()
            java.lang.String r9 = java.lang.String.valueOf(r9)
            int r0 = r9.length()
            java.lang.String r2 = "Unknown intent action: "
            if (r0 == 0) goto L_0x0023
            java.lang.String r9 = r2.concat(r9)
            goto L_0x0028
        L_0x0023:
            java.lang.String r9 = new java.lang.String
            r9.<init>(r2)
        L_0x0028:
            android.util.Log.w(r1, r9)
            return
        L_0x002c:
            java.lang.String r0 = "message_type"
            java.lang.String r2 = r9.getStringExtra(r0)
            java.lang.String r3 = "gcm"
            if (r2 != 0) goto L_0x0037
            r2 = r3
        L_0x0037:
            int r4 = r2.hashCode()
            r5 = 1
            r6 = 0
            switch(r4) {
                case -2062414158: goto L_0x005d;
                case 102161: goto L_0x0055;
                case 814694033: goto L_0x004b;
                case 814800675: goto L_0x0041;
                default: goto L_0x0040;
            }
        L_0x0040:
            goto L_0x0067
        L_0x0041:
            java.lang.String r3 = "send_event"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x0040
            r3 = 2
            goto L_0x0068
        L_0x004b:
            java.lang.String r3 = "send_error"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x0040
            r3 = 3
            goto L_0x0068
        L_0x0055:
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x0040
            r3 = r6
            goto L_0x0068
        L_0x005d:
            java.lang.String r3 = "deleted_messages"
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x0040
            r3 = r5
            goto L_0x0068
        L_0x0067:
            r3 = -1
        L_0x0068:
            java.lang.String r4 = "google.message_id"
            switch(r3) {
                case 0: goto L_0x00a4;
                case 1: goto L_0x00a0;
                case 2: goto L_0x0098;
                case 3: goto L_0x007f;
                default: goto L_0x006d;
            }
        L_0x006d:
            java.lang.String r9 = java.lang.String.valueOf(r2)
            int r0 = r9.length()
            java.lang.String r2 = "Received message with unknown type: "
            if (r0 == 0) goto L_0x0193
            java.lang.String r9 = r2.concat(r9)
            goto L_0x0198
        L_0x007f:
            java.lang.String r0 = r9.getStringExtra(r4)
            if (r0 != 0) goto L_0x008d
            java.lang.String r0 = "message_id"
            java.lang.String r0 = r9.getStringExtra(r0)
        L_0x008d:
            java.lang.String r1 = "error"
            java.lang.String r9 = r9.getStringExtra(r1)
            r8.onSendError(r0, r9)
            return
        L_0x0098:
            java.lang.String r9 = r9.getStringExtra(r4)
            r8.onMessageSent(r9)
            return
        L_0x00a0:
            r8.onDeletedMessages()
            return
        L_0x00a4:
            android.os.Bundle r9 = r9.getExtras()
            r9.remove(r0)
            java.lang.String r0 = "android.support.content.wakelockid"
            r9.remove(r0)
            java.lang.String r0 = "1"
            java.lang.String r1 = "gcm.n.e"
            java.lang.String r2 = com.google.android.gms.gcm.zzd.zzd(r9, r1)
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L_0x00cb
            java.lang.String r0 = "gcm.n.icon"
            java.lang.String r0 = com.google.android.gms.gcm.zzd.zzd(r9, r0)
            if (r0 == 0) goto L_0x00c9
            goto L_0x00cb
        L_0x00c9:
            r0 = r6
            goto L_0x00cc
        L_0x00cb:
            r0 = r5
        L_0x00cc:
            if (r0 == 0) goto L_0x0178
            java.lang.String r0 = "keyguard"
            java.lang.Object r0 = r8.getSystemService(r0)
            android.app.KeyguardManager r0 = (android.app.KeyguardManager) r0
            boolean r0 = r0.inKeyguardRestrictedInputMode()
            if (r0 != 0) goto L_0x010e
            int r0 = android.os.Process.myPid()
            java.lang.String r2 = "activity"
            java.lang.Object r2 = r8.getSystemService(r2)
            android.app.ActivityManager r2 = (android.app.ActivityManager) r2
            java.util.List r2 = r2.getRunningAppProcesses()
            if (r2 == 0) goto L_0x010e
            java.util.Iterator r2 = r2.iterator()
        L_0x00f4:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x010e
            java.lang.Object r3 = r2.next()
            android.app.ActivityManager$RunningAppProcessInfo r3 = (android.app.ActivityManager.RunningAppProcessInfo) r3
            int r4 = r3.pid
            if (r4 != r0) goto L_0x010d
            int r0 = r3.importance
            r2 = 100
            if (r0 != r2) goto L_0x010b
            goto L_0x010f
        L_0x010b:
            r5 = r6
            goto L_0x010f
        L_0x010d:
            goto L_0x00f4
        L_0x010e:
            r5 = r6
        L_0x010f:
            if (r5 != 0) goto L_0x0119
            com.google.android.gms.gcm.zzd r0 = com.google.android.gms.gcm.zzd.zzd(r8)
            r0.zze(r9)
            return
        L_0x0119:
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            java.util.Set r2 = r9.keySet()
            java.util.Iterator r2 = r2.iterator()
        L_0x0127:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x015d
            java.lang.Object r3 = r2.next()
            java.lang.String r3 = (java.lang.String) r3
            java.lang.String r4 = r9.getString(r3)
            java.lang.String r5 = "gcm.notification."
            boolean r6 = r3.startsWith(r5)
            java.lang.String r7 = "gcm.n."
            if (r6 == 0) goto L_0x0145
            java.lang.String r3 = r3.replace(r5, r7)
        L_0x0145:
            boolean r5 = r3.startsWith(r7)
            if (r5 == 0) goto L_0x015c
            boolean r5 = r1.equals(r3)
            if (r5 != 0) goto L_0x0159
            r5 = 6
            java.lang.String r3 = r3.substring(r5)
            r0.putString(r3, r4)
        L_0x0159:
            r2.remove()
        L_0x015c:
            goto L_0x0127
        L_0x015d:
            java.lang.String r1 = "sound2"
            java.lang.String r2 = r0.getString(r1)
            if (r2 == 0) goto L_0x016d
            r0.remove(r1)
            java.lang.String r1 = "sound"
            r0.putString(r1, r2)
        L_0x016d:
            boolean r1 = r0.isEmpty()
            if (r1 != 0) goto L_0x0178
            java.lang.String r1 = "notification"
            r9.putBundle(r1, r0)
        L_0x0178:
            java.lang.String r0 = "from"
            java.lang.String r1 = r9.getString(r0)
            r9.remove(r0)
            zzd(r9)
            com.google.android.gms.internal.gcm.zzl r0 = r8.zzg
            java.lang.String r2 = "onMessageReceived"
            r0.zzl(r2)
            r8.onMessageReceived(r1, r9)     // Catch:{ all -> 0x018f }
            return
        L_0x018f:
            r9 = move-exception
            throw r9     // Catch:{ all -> 0x0191 }
        L_0x0191:
            r9 = move-exception
            throw r9
        L_0x0193:
            java.lang.String r9 = new java.lang.String
            r9.<init>(r2)
        L_0x0198:
            android.util.Log.w(r1, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.gcm.GcmListenerService.handleIntent(android.content.Intent):void");
    }

    static void zzd(Bundle bundle) {
        Iterator it = bundle.keySet().iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str != null && str.startsWith("google.c.")) {
                it.remove();
            }
        }
    }
}
