package com.google.android.gms.iid;

final /* synthetic */ class zzw implements Runnable {
    private final zzt zzcm;

    zzw(zzt zzt) {
        this.zzcm = zzt;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0041, code lost:
        if (android.util.Log.isLoggable("MessengerIpcClient", 3) == false) goto L_0x0069;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0043, code lost:
        r4 = java.lang.String.valueOf(r1);
        android.util.Log.d("MessengerIpcClient", new java.lang.StringBuilder(java.lang.String.valueOf(r4).length() + 8).append("Sending ").append(r4).toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0069, code lost:
        r3 = r0.zzcl.zzl;
        r4 = r0.zzch;
        r5 = android.os.Message.obtain();
        r5.what = r1.what;
        r5.arg1 = r1.zzcp;
        r5.replyTo = r4;
        r4 = new android.os.Bundle();
        r4.putBoolean("oneWay", r1.zzw());
        r4.putString("pkg", r3.getPackageName());
        r4.putBundle("data", r1.zzcr);
        r5.setData(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r1 = r0.zzci;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00a6, code lost:
        if (r1.zzad == null) goto L_0x00af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00a8, code lost:
        r1.zzad.send(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00b1, code lost:
        if (r1.zzco == null) goto L_0x00ba;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00b3, code lost:
        r1.zzco.send(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00c1, code lost:
        throw new java.lang.IllegalStateException("Both messengers are null");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00c2, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00c3, code lost:
        r0.zzd(2, r1.getMessage());
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r8 = this;
            com.google.android.gms.iid.zzt r0 = r8.zzcm
        L_0x0002:
            monitor-enter(r0)
            int r1 = r0.state     // Catch:{ all -> 0x00cc }
            r2 = 2
            if (r1 == r2) goto L_0x000a
            monitor-exit(r0)     // Catch:{ all -> 0x00cc }
            return
        L_0x000a:
            java.util.Queue<com.google.android.gms.iid.zzz<?>> r1 = r0.zzcj     // Catch:{ all -> 0x00cc }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x00cc }
            if (r1 == 0) goto L_0x0017
            r0.zzu()     // Catch:{ all -> 0x00cc }
            monitor-exit(r0)     // Catch:{ all -> 0x00cc }
            return
        L_0x0017:
            java.util.Queue<com.google.android.gms.iid.zzz<?>> r1 = r0.zzcj     // Catch:{ all -> 0x00cc }
            java.lang.Object r1 = r1.poll()     // Catch:{ all -> 0x00cc }
            com.google.android.gms.iid.zzz r1 = (com.google.android.gms.iid.zzz) r1     // Catch:{ all -> 0x00cc }
            android.util.SparseArray<com.google.android.gms.iid.zzz<?>> r3 = r0.zzck     // Catch:{ all -> 0x00cc }
            int r4 = r1.zzcp     // Catch:{ all -> 0x00cc }
            r3.put(r4, r1)     // Catch:{ all -> 0x00cc }
            com.google.android.gms.iid.zzr r3 = r0.zzcl     // Catch:{ all -> 0x00cc }
            java.util.concurrent.ScheduledExecutorService r3 = r3.zzce     // Catch:{ all -> 0x00cc }
            com.google.android.gms.iid.zzx r4 = new com.google.android.gms.iid.zzx     // Catch:{ all -> 0x00cc }
            r4.<init>(r0, r1)     // Catch:{ all -> 0x00cc }
            java.util.concurrent.TimeUnit r5 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ all -> 0x00cc }
            r6 = 30
            r3.schedule(r4, r6, r5)     // Catch:{ all -> 0x00cc }
            monitor-exit(r0)     // Catch:{ all -> 0x00cc }
            java.lang.String r3 = "MessengerIpcClient"
            r4 = 3
            boolean r3 = android.util.Log.isLoggable(r3, r4)
            if (r3 == 0) goto L_0x0069
            java.lang.String r3 = "MessengerIpcClient"
            java.lang.String r4 = java.lang.String.valueOf(r1)
            java.lang.String r5 = java.lang.String.valueOf(r4)
            int r5 = r5.length()
            int r5 = r5 + 8
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>(r5)
            java.lang.String r5 = "Sending "
            java.lang.StringBuilder r5 = r6.append(r5)
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.String r4 = r4.toString()
            android.util.Log.d(r3, r4)
        L_0x0069:
            com.google.android.gms.iid.zzr r3 = r0.zzcl
            android.content.Context r3 = r3.zzl
            android.os.Messenger r4 = r0.zzch
            android.os.Message r5 = android.os.Message.obtain()
            int r6 = r1.what
            r5.what = r6
            int r6 = r1.zzcp
            r5.arg1 = r6
            r5.replyTo = r4
            android.os.Bundle r4 = new android.os.Bundle
            r4.<init>()
            java.lang.String r6 = "oneWay"
            boolean r7 = r1.zzw()
            r4.putBoolean(r6, r7)
            java.lang.String r6 = "pkg"
            java.lang.String r3 = r3.getPackageName()
            r4.putString(r6, r3)
            java.lang.String r3 = "data"
            android.os.Bundle r1 = r1.zzcr
            r4.putBundle(r3, r1)
            r5.setData(r4)
            com.google.android.gms.iid.zzy r1 = r0.zzci     // Catch:{ RemoteException -> 0x00c2 }
            android.os.Messenger r3 = r1.zzad     // Catch:{ RemoteException -> 0x00c2 }
            if (r3 == 0) goto L_0x00af
            android.os.Messenger r1 = r1.zzad     // Catch:{ RemoteException -> 0x00c2 }
            r1.send(r5)     // Catch:{ RemoteException -> 0x00c2 }
            goto L_0x0002
        L_0x00af:
            com.google.android.gms.iid.MessengerCompat r3 = r1.zzco     // Catch:{ RemoteException -> 0x00c2 }
            if (r3 == 0) goto L_0x00ba
            com.google.android.gms.iid.MessengerCompat r1 = r1.zzco     // Catch:{ RemoteException -> 0x00c2 }
            r1.send(r5)     // Catch:{ RemoteException -> 0x00c2 }
            goto L_0x0002
        L_0x00ba:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException     // Catch:{ RemoteException -> 0x00c2 }
            java.lang.String r3 = "Both messengers are null"
            r1.<init>(r3)     // Catch:{ RemoteException -> 0x00c2 }
            throw r1     // Catch:{ RemoteException -> 0x00c2 }
        L_0x00c2:
            r1 = move-exception
            java.lang.String r1 = r1.getMessage()
            r0.zzd(r2, r1)
            goto L_0x0002
        L_0x00cc:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00cc }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.iid.zzw.run():void");
    }
}
