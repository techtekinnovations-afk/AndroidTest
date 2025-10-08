package com.google.android.gms.internal.gcm;

public final class zzq {
    private static final zzr zzdq;
    private static final int zzdr;

    static final class zzd extends zzr {
        zzd() {
        }

        public final void zzd(Throwable th, Throwable th2) {
        }
    }

    public static void zzd(Throwable th, Throwable th2) {
        zzdq.zzd(th, th2);
    }

    private static Integer zzac() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get((Object) null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x006b  */
    static {
        /*
            java.lang.Integer r0 = zzac()     // Catch:{ all -> 0x002b }
            if (r0 == 0) goto L_0x0015
            int r1 = r0.intValue()     // Catch:{ all -> 0x0029 }
            r2 = 19
            if (r1 < r2) goto L_0x0015
            com.google.android.gms.internal.gcm.zzv r1 = new com.google.android.gms.internal.gcm.zzv     // Catch:{ all -> 0x0029 }
            r1.<init>()     // Catch:{ all -> 0x0029 }
            goto L_0x0065
        L_0x0015:
            java.lang.String r1 = "com.google.devtools.build.android.desugar.runtime.twr_disable_mimic"
            boolean r1 = java.lang.Boolean.getBoolean(r1)     // Catch:{ all -> 0x0029 }
            if (r1 != 0) goto L_0x0023
            com.google.android.gms.internal.gcm.zzu r1 = new com.google.android.gms.internal.gcm.zzu     // Catch:{ all -> 0x0029 }
            r1.<init>()     // Catch:{ all -> 0x0029 }
            goto L_0x0065
        L_0x0023:
            com.google.android.gms.internal.gcm.zzq$zzd r1 = new com.google.android.gms.internal.gcm.zzq$zzd     // Catch:{ all -> 0x0029 }
            r1.<init>()     // Catch:{ all -> 0x0029 }
            goto L_0x0065
        L_0x0029:
            r1 = move-exception
            goto L_0x002d
        L_0x002b:
            r1 = move-exception
            r0 = 0
        L_0x002d:
            java.io.PrintStream r2 = java.lang.System.err
            java.lang.Class<com.google.android.gms.internal.gcm.zzq$zzd> r3 = com.google.android.gms.internal.gcm.zzq.zzd.class
            java.lang.String r3 = r3.getName()
            java.lang.String r4 = java.lang.String.valueOf(r3)
            int r4 = r4.length()
            int r4 = r4 + 133
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r4 = "An error has occurred when initializing the try-with-resources desuguring strategy. The default strategy "
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.StringBuilder r3 = r4.append(r3)
            java.lang.String r4 = "will be used. The error is: "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.println(r3)
            java.io.PrintStream r2 = java.lang.System.err
            r1.printStackTrace(r2)
            com.google.android.gms.internal.gcm.zzq$zzd r1 = new com.google.android.gms.internal.gcm.zzq$zzd
            r1.<init>()
        L_0x0065:
            zzdq = r1
            if (r0 != 0) goto L_0x006b
            r0 = 1
            goto L_0x006f
        L_0x006b:
            int r0 = r0.intValue()
        L_0x006f:
            zzdr = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gcm.zzq.<clinit>():void");
    }
}
