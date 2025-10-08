package com.google.android.gms.dynamite;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/* compiled from: com.google.android.gms:play-services-basement@@18.3.0 */
public final class DynamiteModule {
    public static final int LOCAL = -1;
    public static final int NONE = 0;
    public static final int NO_SELECTION = 0;
    public static final VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION = new zzi();
    public static final VersionPolicy PREFER_HIGHEST_OR_LOCAL_VERSION_NO_FORCE_STAGING = new zzj();
    public static final VersionPolicy PREFER_HIGHEST_OR_REMOTE_VERSION = new zzk();
    public static final VersionPolicy PREFER_LOCAL = new zzg();
    public static final VersionPolicy PREFER_REMOTE = new zzf();
    public static final VersionPolicy PREFER_REMOTE_VERSION_NO_FORCE_STAGING = new zzh();
    public static final int REMOTE = 1;
    public static final VersionPolicy zza = new zzl();
    private static Boolean zzb;
    private static String zzc;
    private static boolean zzd;
    private static int zze = -1;
    private static Boolean zzf = null;
    private static final ThreadLocal zzg = new ThreadLocal();
    private static final ThreadLocal zzh = new zzd();
    private static final VersionPolicy.IVersions zzi = new zze();
    private static zzq zzk;
    private static zzr zzl;
    private final Context zzj;

    /* compiled from: com.google.android.gms:play-services-basement@@18.3.0 */
    public static class DynamiteLoaderClassLoader {
        public static ClassLoader sClassLoader;
    }

    /* compiled from: com.google.android.gms:play-services-basement@@18.3.0 */
    public static class LoadingException extends Exception {
        /* synthetic */ LoadingException(String str, zzp zzp) {
            super(str);
        }

        /* synthetic */ LoadingException(String str, Throwable th, zzp zzp) {
            super(str, th);
        }
    }

    /* compiled from: com.google.android.gms:play-services-basement@@18.3.0 */
    public interface VersionPolicy {

        /* compiled from: com.google.android.gms:play-services-basement@@18.3.0 */
        public interface IVersions {
            int zza(Context context, String str);

            int zzb(Context context, String str, boolean z) throws LoadingException;
        }

        /* compiled from: com.google.android.gms:play-services-basement@@18.3.0 */
        public static class SelectionResult {
            public int localVersion = 0;
            public int remoteVersion = 0;
            public int selection = 0;
        }

        SelectionResult selectModule(Context context, String str, IVersions iVersions) throws LoadingException;
    }

    private DynamiteModule(Context context) {
        Preconditions.checkNotNull(context);
        this.zzj = context;
    }

    public static int getLocalVersion(Context context, String moduleId) {
        try {
            ClassLoader classLoader = context.getApplicationContext().getClassLoader();
            Class<?> loadClass = classLoader.loadClass("com.google.android.gms.dynamite.descriptors." + moduleId + ".ModuleDescriptor");
            Field declaredField = loadClass.getDeclaredField("MODULE_ID");
            Field declaredField2 = loadClass.getDeclaredField("MODULE_VERSION");
            if (Objects.equal(declaredField.get((Object) null), moduleId)) {
                return declaredField2.getInt((Object) null);
            }
            String valueOf = String.valueOf(declaredField.get((Object) null));
            Log.e("DynamiteModule", "Module descriptor id '" + valueOf + "' didn't match expected id '" + moduleId + "'");
            return 0;
        } catch (ClassNotFoundException e) {
            Log.w("DynamiteModule", "Local module descriptor class for " + moduleId + " not found.");
            return 0;
        } catch (Exception e2) {
            Log.e("DynamiteModule", "Failed to load module descriptor class: ".concat(String.valueOf(e2.getMessage())));
            return 0;
        }
    }

    public static int getRemoteVersion(Context context, String moduleId) {
        return zza(context, moduleId, false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:145:0x02b0  */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x02b6  */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x02c3  */
    @com.google.errorprone.annotations.ResultIgnorabilityUnspecified
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.dynamite.DynamiteModule load(android.content.Context r20, com.google.android.gms.dynamite.DynamiteModule.VersionPolicy r21, java.lang.String r22) throws com.google.android.gms.dynamite.DynamiteModule.LoadingException {
        /*
            r1 = r20
            r2 = r21
            r3 = r22
            android.content.Context r4 = r1.getApplicationContext()
            r5 = 0
            if (r4 == 0) goto L_0x02cc
            java.lang.ThreadLocal r0 = zzg
            java.lang.Object r0 = r0.get()
            r6 = r0
            com.google.android.gms.dynamite.zzn r6 = (com.google.android.gms.dynamite.zzn) r6
            com.google.android.gms.dynamite.zzn r7 = new com.google.android.gms.dynamite.zzn
            r7.<init>(r5)
            java.lang.ThreadLocal r0 = zzg
            r0.set(r7)
            java.lang.ThreadLocal r0 = zzh
            java.lang.Object r0 = r0.get()
            java.lang.Long r0 = (java.lang.Long) r0
            long r8 = r0.longValue()
            java.lang.ThreadLocal r0 = zzh     // Catch:{ all -> 0x02a9 }
            long r12 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x02a9 }
            java.lang.Long r12 = java.lang.Long.valueOf(r12)     // Catch:{ all -> 0x02a9 }
            r0.set(r12)     // Catch:{ all -> 0x02a9 }
            com.google.android.gms.dynamite.DynamiteModule$VersionPolicy$IVersions r0 = zzi     // Catch:{ all -> 0x02a9 }
            com.google.android.gms.dynamite.DynamiteModule$VersionPolicy$SelectionResult r12 = r2.selectModule(r1, r3, r0)     // Catch:{ all -> 0x02a9 }
            java.lang.String r0 = "DynamiteModule"
            int r13 = r12.localVersion     // Catch:{ all -> 0x02a9 }
            int r14 = r12.remoteVersion     // Catch:{ all -> 0x02a9 }
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ all -> 0x02a9 }
            r15.<init>()     // Catch:{ all -> 0x02a9 }
            r16 = 0
            java.lang.String r10 = "Considering local module "
            r15.append(r10)     // Catch:{ all -> 0x02a7 }
            r15.append(r3)     // Catch:{ all -> 0x02a7 }
            java.lang.String r10 = ":"
            r15.append(r10)     // Catch:{ all -> 0x02a7 }
            r15.append(r13)     // Catch:{ all -> 0x02a7 }
            java.lang.String r10 = " and remote module "
            r15.append(r10)     // Catch:{ all -> 0x02a7 }
            r15.append(r3)     // Catch:{ all -> 0x02a7 }
            java.lang.String r10 = ":"
            r15.append(r10)     // Catch:{ all -> 0x02a7 }
            r15.append(r14)     // Catch:{ all -> 0x02a7 }
            java.lang.String r10 = r15.toString()     // Catch:{ all -> 0x02a7 }
            android.util.Log.i(r0, r10)     // Catch:{ all -> 0x02a7 }
            int r0 = r12.selection     // Catch:{ all -> 0x02a7 }
            if (r0 == 0) goto L_0x0277
            r10 = -1
            if (r0 != r10) goto L_0x007f
            int r0 = r12.localVersion     // Catch:{ all -> 0x02a7 }
            if (r0 == 0) goto L_0x0277
            r0 = r10
        L_0x007f:
            r11 = 1
            if (r0 != r11) goto L_0x0086
            int r13 = r12.remoteVersion     // Catch:{ all -> 0x02a7 }
            if (r13 == 0) goto L_0x0277
        L_0x0086:
            if (r0 != r10) goto L_0x008e
            com.google.android.gms.dynamite.DynamiteModule r0 = zzc(r4, r3)     // Catch:{ all -> 0x02a7 }
            goto L_0x0238
        L_0x008e:
            if (r0 != r11) goto L_0x0260
            int r0 = r12.remoteVersion     // Catch:{ LoadingException -> 0x0206 }
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule> r14 = com.google.android.gms.dynamite.DynamiteModule.class
            monitor-enter(r14)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            boolean r15 = zzf(r1)     // Catch:{ all -> 0x01ec }
            if (r15 == 0) goto L_0x01e4
            java.lang.Boolean r15 = zzb     // Catch:{ all -> 0x01ec }
            monitor-exit(r14)     // Catch:{ all -> 0x01ec }
            if (r15 == 0) goto L_0x01dc
            boolean r14 = r15.booleanValue()     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            r15 = 2
            if (r14 == 0) goto L_0x0150
            java.lang.String r14 = "DynamiteModule"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            r11.<init>()     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            java.lang.String r10 = "Selected remote version of "
            r11.append(r10)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            r11.append(r3)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            java.lang.String r10 = ", version >= "
            r11.append(r10)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            r11.append(r0)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            java.lang.String r10 = r11.toString()     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            android.util.Log.i(r14, r10)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule> r10 = com.google.android.gms.dynamite.DynamiteModule.class
            monitor-enter(r10)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            com.google.android.gms.dynamite.zzr r11 = zzl     // Catch:{ all -> 0x014d }
            monitor-exit(r10)     // Catch:{ all -> 0x014d }
            if (r11 == 0) goto L_0x0145
            java.lang.ThreadLocal r10 = zzg     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            java.lang.Object r10 = r10.get()     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            com.google.android.gms.dynamite.zzn r10 = (com.google.android.gms.dynamite.zzn) r10     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            if (r10 == 0) goto L_0x013d
            android.database.Cursor r14 = r10.zza     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            if (r14 == 0) goto L_0x013d
            android.content.Context r14 = r1.getApplicationContext()     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            android.database.Cursor r10 = r10.zza     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            com.google.android.gms.dynamic.ObjectWrapper.wrap(r5)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule> r19 = com.google.android.gms.dynamite.DynamiteModule.class
            monitor-enter(r19)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            int r13 = zze     // Catch:{ all -> 0x013a }
            if (r13 < r15) goto L_0x00ee
            r18 = 1
            goto L_0x00f0
        L_0x00ee:
            r18 = 0
        L_0x00f0:
            java.lang.Boolean r13 = java.lang.Boolean.valueOf(r18)     // Catch:{ all -> 0x013a }
            monitor-exit(r19)     // Catch:{ all -> 0x013a }
            boolean r13 = r13.booleanValue()     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            if (r13 == 0) goto L_0x010f
            java.lang.String r13 = "DynamiteModule"
            java.lang.String r15 = "Dynamite loader version >= 2, using loadModule2NoCrashUtils"
            android.util.Log.v(r13, r15)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            com.google.android.gms.dynamic.IObjectWrapper r13 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r14)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            com.google.android.gms.dynamic.IObjectWrapper r10 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r10)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            com.google.android.gms.dynamic.IObjectWrapper r0 = r11.zzf(r13, r3, r0, r10)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            goto L_0x0122
        L_0x010f:
            java.lang.String r13 = "DynamiteModule"
            java.lang.String r15 = "Dynamite loader version < 2, falling back to loadModule2"
            android.util.Log.w(r13, r15)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            com.google.android.gms.dynamic.IObjectWrapper r13 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r14)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            com.google.android.gms.dynamic.IObjectWrapper r10 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r10)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            com.google.android.gms.dynamic.IObjectWrapper r0 = r11.zze(r13, r3, r0, r10)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
        L_0x0122:
            java.lang.Object r0 = com.google.android.gms.dynamic.ObjectWrapper.unwrap(r0)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            android.content.Context r0 = (android.content.Context) r0     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            if (r0 == 0) goto L_0x0132
            com.google.android.gms.dynamite.DynamiteModule r10 = new com.google.android.gms.dynamite.DynamiteModule     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            r10.<init>(r0)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            r0 = r10
            goto L_0x0238
        L_0x0132:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r0 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            java.lang.String r10 = "Failed to get module context"
            r0.<init>(r10, r5)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            throw r0     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
        L_0x013a:
            r0 = move-exception
            monitor-exit(r19)     // Catch:{ all -> 0x013a }
            throw r0     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
        L_0x013d:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r0 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            java.lang.String r10 = "No result cursor"
            r0.<init>(r10, r5)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            throw r0     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
        L_0x0145:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r0 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            java.lang.String r10 = "DynamiteLoaderV2 was not cached."
            r0.<init>(r10, r5)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            throw r0     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
        L_0x014d:
            r0 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x014d }
            throw r0     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
        L_0x0150:
            java.lang.String r10 = "DynamiteModule"
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            r11.<init>()     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            java.lang.String r13 = "Selected remote version of "
            r11.append(r13)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            r11.append(r3)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            java.lang.String r13 = ", version >= "
            r11.append(r13)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            r11.append(r0)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            java.lang.String r11 = r11.toString()     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            android.util.Log.i(r10, r11)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            com.google.android.gms.dynamite.zzq r10 = zzg(r1)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            if (r10 == 0) goto L_0x01d4
            int r11 = r10.zze()     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            r13 = 3
            if (r11 < r13) goto L_0x019c
            java.lang.ThreadLocal r11 = zzg     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            java.lang.Object r11 = r11.get()     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            com.google.android.gms.dynamite.zzn r11 = (com.google.android.gms.dynamite.zzn) r11     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            if (r11 == 0) goto L_0x0194
            com.google.android.gms.dynamic.IObjectWrapper r13 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r1)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            android.database.Cursor r11 = r11.zza     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            com.google.android.gms.dynamic.IObjectWrapper r11 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r11)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            com.google.android.gms.dynamic.IObjectWrapper r0 = r10.zzi(r13, r3, r0, r11)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            goto L_0x01bd
        L_0x0194:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r0 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            java.lang.String r10 = "No cached result cursor holder"
            r0.<init>(r10, r5)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            throw r0     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
        L_0x019c:
            if (r11 != r15) goto L_0x01ae
            java.lang.String r11 = "DynamiteModule"
            java.lang.String r13 = "IDynamite loader version = 2"
            android.util.Log.w(r11, r13)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            com.google.android.gms.dynamic.IObjectWrapper r11 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r1)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            com.google.android.gms.dynamic.IObjectWrapper r0 = r10.zzj(r11, r3, r0)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            goto L_0x01bd
        L_0x01ae:
            java.lang.String r11 = "DynamiteModule"
            java.lang.String r13 = "Dynamite loader version < 2, falling back to createModuleContext"
            android.util.Log.w(r11, r13)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            com.google.android.gms.dynamic.IObjectWrapper r11 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r1)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            com.google.android.gms.dynamic.IObjectWrapper r0 = r10.zzh(r11, r3, r0)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
        L_0x01bd:
            java.lang.Object r0 = com.google.android.gms.dynamic.ObjectWrapper.unwrap(r0)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            if (r0 == 0) goto L_0x01cc
            com.google.android.gms.dynamite.DynamiteModule r10 = new com.google.android.gms.dynamite.DynamiteModule     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            android.content.Context r0 = (android.content.Context) r0     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            r10.<init>(r0)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            r0 = r10
            goto L_0x0238
        L_0x01cc:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r0 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            java.lang.String r10 = "Failed to load remote module."
            r0.<init>(r10, r5)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            throw r0     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
        L_0x01d4:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r0 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            java.lang.String r10 = "Failed to create IDynamiteLoader."
            r0.<init>(r10, r5)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            throw r0     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
        L_0x01dc:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r0 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            java.lang.String r10 = "Failed to determine which loading route to use."
            r0.<init>(r10, r5)     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
            throw r0     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
        L_0x01e4:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r0 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ all -> 0x01ec }
            java.lang.String r10 = "Remote loading disabled"
            r0.<init>(r10, r5)     // Catch:{ all -> 0x01ec }
            throw r0     // Catch:{ all -> 0x01ec }
        L_0x01ec:
            r0 = move-exception
            monitor-exit(r14)     // Catch:{ all -> 0x01ec }
            throw r0     // Catch:{ RemoteException -> 0x01fd, LoadingException -> 0x01fb, all -> 0x01ef }
        L_0x01ef:
            r0 = move-exception
            com.google.android.gms.common.util.CrashUtils.addDynamiteErrorToDropBox(r1, r0)     // Catch:{ LoadingException -> 0x0206 }
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r10 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ LoadingException -> 0x0206 }
            java.lang.String r11 = "Failed to load remote module."
            r10.<init>(r11, r0, r5)     // Catch:{ LoadingException -> 0x0206 }
            throw r10     // Catch:{ LoadingException -> 0x0206 }
        L_0x01fb:
            r0 = move-exception
            throw r0     // Catch:{ LoadingException -> 0x0206 }
        L_0x01fd:
            r0 = move-exception
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r10 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ LoadingException -> 0x0206 }
            java.lang.String r11 = "Failed to load remote module."
            r10.<init>(r11, r0, r5)     // Catch:{ LoadingException -> 0x0206 }
            throw r10     // Catch:{ LoadingException -> 0x0206 }
        L_0x0206:
            r0 = move-exception
            java.lang.String r10 = "DynamiteModule"
            java.lang.String r11 = r0.getMessage()     // Catch:{ all -> 0x02a7 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x02a7 }
            r13.<init>()     // Catch:{ all -> 0x02a7 }
            java.lang.String r14 = "Failed to load remote module: "
            r13.append(r14)     // Catch:{ all -> 0x02a7 }
            r13.append(r11)     // Catch:{ all -> 0x02a7 }
            java.lang.String r11 = r13.toString()     // Catch:{ all -> 0x02a7 }
            android.util.Log.w(r10, r11)     // Catch:{ all -> 0x02a7 }
            int r10 = r12.localVersion     // Catch:{ all -> 0x02a7 }
            if (r10 == 0) goto L_0x0258
            com.google.android.gms.dynamite.zzo r11 = new com.google.android.gms.dynamite.zzo     // Catch:{ all -> 0x02a7 }
            r12 = 0
            r11.<init>(r10, r12)     // Catch:{ all -> 0x02a7 }
            com.google.android.gms.dynamite.DynamiteModule$VersionPolicy$SelectionResult r1 = r2.selectModule(r1, r3, r11)     // Catch:{ all -> 0x02a7 }
            int r1 = r1.selection     // Catch:{ all -> 0x02a7 }
            r2 = -1
            if (r1 != r2) goto L_0x0258
            com.google.android.gms.dynamite.DynamiteModule r0 = zzc(r4, r3)     // Catch:{ all -> 0x02a7 }
        L_0x0238:
            int r1 = (r8 > r16 ? 1 : (r8 == r16 ? 0 : -1))
            if (r1 != 0) goto L_0x0242
            java.lang.ThreadLocal r1 = zzh
            r1.remove()
            goto L_0x024b
        L_0x0242:
            java.lang.ThreadLocal r1 = zzh
            java.lang.Long r2 = java.lang.Long.valueOf(r8)
            r1.set(r2)
        L_0x024b:
            android.database.Cursor r1 = r7.zza
            if (r1 == 0) goto L_0x0252
            r1.close()
        L_0x0252:
            java.lang.ThreadLocal r1 = zzg
            r1.set(r6)
            return r0
        L_0x0258:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r1 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ all -> 0x02a7 }
            java.lang.String r2 = "Remote load failed. No local fallback found."
            r1.<init>(r2, r0, r5)     // Catch:{ all -> 0x02a7 }
            throw r1     // Catch:{ all -> 0x02a7 }
        L_0x0260:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r1 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ all -> 0x02a7 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x02a7 }
            r2.<init>()     // Catch:{ all -> 0x02a7 }
            java.lang.String r3 = "VersionPolicy returned invalid code:"
            r2.append(r3)     // Catch:{ all -> 0x02a7 }
            r2.append(r0)     // Catch:{ all -> 0x02a7 }
            java.lang.String r0 = r2.toString()     // Catch:{ all -> 0x02a7 }
            r1.<init>(r0, r5)     // Catch:{ all -> 0x02a7 }
            throw r1     // Catch:{ all -> 0x02a7 }
        L_0x0277:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r0 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ all -> 0x02a7 }
            int r1 = r12.localVersion     // Catch:{ all -> 0x02a7 }
            int r2 = r12.remoteVersion     // Catch:{ all -> 0x02a7 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x02a7 }
            r4.<init>()     // Catch:{ all -> 0x02a7 }
            java.lang.String r10 = "No acceptable module "
            r4.append(r10)     // Catch:{ all -> 0x02a7 }
            r4.append(r3)     // Catch:{ all -> 0x02a7 }
            java.lang.String r3 = " found. Local version is "
            r4.append(r3)     // Catch:{ all -> 0x02a7 }
            r4.append(r1)     // Catch:{ all -> 0x02a7 }
            java.lang.String r1 = " and remote version is "
            r4.append(r1)     // Catch:{ all -> 0x02a7 }
            r4.append(r2)     // Catch:{ all -> 0x02a7 }
            java.lang.String r1 = "."
            r4.append(r1)     // Catch:{ all -> 0x02a7 }
            java.lang.String r1 = r4.toString()     // Catch:{ all -> 0x02a7 }
            r0.<init>(r1, r5)     // Catch:{ all -> 0x02a7 }
            throw r0     // Catch:{ all -> 0x02a7 }
        L_0x02a7:
            r0 = move-exception
            goto L_0x02ac
        L_0x02a9:
            r0 = move-exception
            r16 = 0
        L_0x02ac:
            int r1 = (r8 > r16 ? 1 : (r8 == r16 ? 0 : -1))
            if (r1 != 0) goto L_0x02b6
            java.lang.ThreadLocal r1 = zzh
            r1.remove()
            goto L_0x02bf
        L_0x02b6:
            java.lang.ThreadLocal r1 = zzh
            java.lang.Long r2 = java.lang.Long.valueOf(r8)
            r1.set(r2)
        L_0x02bf:
            android.database.Cursor r1 = r7.zza
            if (r1 == 0) goto L_0x02c6
            r1.close()
        L_0x02c6:
            java.lang.ThreadLocal r1 = zzg
            r1.set(r6)
            throw r0
        L_0x02cc:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r0 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException
            java.lang.String r1 = "null application Context"
            r0.<init>(r1, r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.load(android.content.Context, com.google.android.gms.dynamite.DynamiteModule$VersionPolicy, java.lang.String):com.google.android.gms.dynamite.DynamiteModule");
    }

    /* JADX INFO: finally extract failed */
    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:659)
        	at java.util.ArrayList.get(ArrayList.java:435)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:18:0x003d=Splitter:B:18:0x003d, B:51:0x009f=Splitter:B:51:0x009f} */
    public static int zza(android.content.Context r10, java.lang.String r11, boolean r12) {
        /*
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule> r1 = com.google.android.gms.dynamite.DynamiteModule.class
            monitor-enter(r1)     // Catch:{ all -> 0x01d8 }
            java.lang.Boolean r0 = zzb     // Catch:{ all -> 0x01d4 }
            r2 = 0
            r3 = 0
            if (r0 != 0) goto L_0x00df
            android.content.Context r0 = r10.getApplicationContext()     // Catch:{ ClassNotFoundException -> 0x00c0, IllegalAccessException -> 0x00be, NoSuchFieldException -> 0x00bc }
            java.lang.ClassLoader r0 = r0.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x00c0, IllegalAccessException -> 0x00be, NoSuchFieldException -> 0x00bc }
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule$DynamiteLoaderClassLoader> r4 = com.google.android.gms.dynamite.DynamiteModule.DynamiteLoaderClassLoader.class
            java.lang.String r4 = r4.getName()     // Catch:{ ClassNotFoundException -> 0x00c0, IllegalAccessException -> 0x00be, NoSuchFieldException -> 0x00bc }
            java.lang.Class r0 = r0.loadClass(r4)     // Catch:{ ClassNotFoundException -> 0x00c0, IllegalAccessException -> 0x00be, NoSuchFieldException -> 0x00bc }
            java.lang.String r4 = "sClassLoader"
            java.lang.reflect.Field r4 = r0.getDeclaredField(r4)     // Catch:{ ClassNotFoundException -> 0x00c0, IllegalAccessException -> 0x00be, NoSuchFieldException -> 0x00bc }
            java.lang.Class r5 = r4.getDeclaringClass()     // Catch:{ ClassNotFoundException -> 0x00c0, IllegalAccessException -> 0x00be, NoSuchFieldException -> 0x00bc }
            monitor-enter(r5)     // Catch:{ ClassNotFoundException -> 0x00c0, IllegalAccessException -> 0x00be, NoSuchFieldException -> 0x00bc }
            java.lang.Object r0 = r4.get(r2)     // Catch:{ all -> 0x00b9 }
            java.lang.ClassLoader r0 = (java.lang.ClassLoader) r0     // Catch:{ all -> 0x00b9 }
            java.lang.ClassLoader r6 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ all -> 0x00b9 }
            if (r0 != r6) goto L_0x0036
            java.lang.Boolean r0 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x00b9 }
            goto L_0x00b7
        L_0x0036:
            if (r0 == 0) goto L_0x0041
            zzd(r0)     // Catch:{ LoadingException -> 0x003c }
            goto L_0x003d
        L_0x003c:
            r0 = move-exception
        L_0x003d:
            java.lang.Boolean r0 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x00b9 }
            goto L_0x00b7
        L_0x0041:
            boolean r0 = zzf(r10)     // Catch:{ all -> 0x00b9 }
            if (r0 != 0) goto L_0x004a
            monitor-exit(r5)     // Catch:{ all -> 0x00b9 }
            monitor-exit(r1)     // Catch:{ all -> 0x01d4 }
            return r3
        L_0x004a:
            boolean r0 = zzd     // Catch:{ all -> 0x00b9 }
            if (r0 != 0) goto L_0x00ad
            java.lang.Boolean r0 = java.lang.Boolean.TRUE     // Catch:{ all -> 0x00b9 }
            boolean r0 = r0.equals(r2)     // Catch:{ all -> 0x00b9 }
            if (r0 == 0) goto L_0x0057
            goto L_0x00ad
        L_0x0057:
            r0 = 1
            int r0 = zzb(r10, r11, r12, r0)     // Catch:{ LoadingException -> 0x00a2 }
            java.lang.String r6 = zzc     // Catch:{ LoadingException -> 0x00a2 }
            if (r6 == 0) goto L_0x009f
            boolean r6 = r6.isEmpty()     // Catch:{ LoadingException -> 0x00a2 }
            if (r6 == 0) goto L_0x0068
            goto L_0x009f
        L_0x0068:
            java.lang.ClassLoader r6 = com.google.android.gms.dynamite.zzb.zza()     // Catch:{ LoadingException -> 0x00a2 }
            if (r6 == 0) goto L_0x006f
            goto L_0x0092
        L_0x006f:
            int r6 = android.os.Build.VERSION.SDK_INT     // Catch:{ LoadingException -> 0x00a2 }
            r7 = 29
            if (r6 < r7) goto L_0x0084
            dalvik.system.DelegateLastClassLoader r6 = new dalvik.system.DelegateLastClassLoader     // Catch:{ LoadingException -> 0x00a2 }
            java.lang.String r7 = zzc     // Catch:{ LoadingException -> 0x00a2 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r7)     // Catch:{ LoadingException -> 0x00a2 }
            java.lang.ClassLoader r8 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ LoadingException -> 0x00a2 }
            r6.<init>(r7, r8)     // Catch:{ LoadingException -> 0x00a2 }
            goto L_0x0092
        L_0x0084:
            com.google.android.gms.dynamite.zzc r6 = new com.google.android.gms.dynamite.zzc     // Catch:{ LoadingException -> 0x00a2 }
            java.lang.String r7 = zzc     // Catch:{ LoadingException -> 0x00a2 }
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r7)     // Catch:{ LoadingException -> 0x00a2 }
            java.lang.ClassLoader r8 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ LoadingException -> 0x00a2 }
            r6.<init>(r7, r8)     // Catch:{ LoadingException -> 0x00a2 }
        L_0x0092:
            zzd(r6)     // Catch:{ LoadingException -> 0x00a2 }
            r4.set(r2, r6)     // Catch:{ LoadingException -> 0x00a2 }
            java.lang.Boolean r6 = java.lang.Boolean.TRUE     // Catch:{ LoadingException -> 0x00a2 }
            zzb = r6     // Catch:{ LoadingException -> 0x00a2 }
            monitor-exit(r5)     // Catch:{ all -> 0x00b9 }
            monitor-exit(r1)     // Catch:{ all -> 0x01d4 }
            return r0
        L_0x009f:
            monitor-exit(r5)     // Catch:{ all -> 0x00b9 }
            monitor-exit(r1)     // Catch:{ all -> 0x01d4 }
            return r0
        L_0x00a2:
            r0 = move-exception
            java.lang.ClassLoader r0 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ all -> 0x00b9 }
            r4.set(r2, r0)     // Catch:{ all -> 0x00b9 }
            java.lang.Boolean r0 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x00b9 }
            goto L_0x00b7
        L_0x00ad:
            java.lang.ClassLoader r0 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ all -> 0x00b9 }
            r4.set(r2, r0)     // Catch:{ all -> 0x00b9 }
            java.lang.Boolean r0 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x00b9 }
        L_0x00b7:
            monitor-exit(r5)     // Catch:{ all -> 0x00b9 }
            goto L_0x00dd
        L_0x00b9:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x00b9 }
            throw r0     // Catch:{ ClassNotFoundException -> 0x00c0, IllegalAccessException -> 0x00be, NoSuchFieldException -> 0x00bc }
        L_0x00bc:
            r0 = move-exception
            goto L_0x00c1
        L_0x00be:
            r0 = move-exception
            goto L_0x00c1
        L_0x00c0:
            r0 = move-exception
        L_0x00c1:
            java.lang.String r4 = "DynamiteModule"
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x01d4 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x01d4 }
            r5.<init>()     // Catch:{ all -> 0x01d4 }
            java.lang.String r6 = "Failed to load module via V2: "
            r5.append(r6)     // Catch:{ all -> 0x01d4 }
            r5.append(r0)     // Catch:{ all -> 0x01d4 }
            java.lang.String r0 = r5.toString()     // Catch:{ all -> 0x01d4 }
            android.util.Log.w(r4, r0)     // Catch:{ all -> 0x01d4 }
            java.lang.Boolean r0 = java.lang.Boolean.FALSE     // Catch:{ all -> 0x01d4 }
        L_0x00dd:
            zzb = r0     // Catch:{ all -> 0x01d4 }
        L_0x00df:
            monitor-exit(r1)     // Catch:{ all -> 0x01d4 }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x01d8 }
            if (r0 == 0) goto L_0x0108
            int r10 = zzb(r10, r11, r12, r3)     // Catch:{ LoadingException -> 0x00eb }
            return r10
        L_0x00eb:
            r0 = move-exception
            r11 = r0
            java.lang.String r12 = "DynamiteModule"
            java.lang.String r11 = r11.getMessage()     // Catch:{ all -> 0x01d8 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x01d8 }
            r0.<init>()     // Catch:{ all -> 0x01d8 }
            java.lang.String r1 = "Failed to retrieve remote module version: "
            r0.append(r1)     // Catch:{ all -> 0x01d8 }
            r0.append(r11)     // Catch:{ all -> 0x01d8 }
            java.lang.String r11 = r0.toString()     // Catch:{ all -> 0x01d8 }
            android.util.Log.w(r12, r11)     // Catch:{ all -> 0x01d8 }
            return r3
        L_0x0108:
            com.google.android.gms.dynamite.zzq r4 = zzg(r10)     // Catch:{ all -> 0x01d8 }
            if (r4 != 0) goto L_0x0110
            goto L_0x01ca
        L_0x0110:
            int r0 = r4.zze()     // Catch:{ RemoteException -> 0x01a6, all -> 0x01a2 }
            r1 = 3
            if (r0 < r1) goto L_0x017d
            java.lang.ThreadLocal r0 = zzg     // Catch:{ RemoteException -> 0x01a6, all -> 0x01a2 }
            java.lang.Object r0 = r0.get()     // Catch:{ RemoteException -> 0x01a6, all -> 0x01a2 }
            com.google.android.gms.dynamite.zzn r0 = (com.google.android.gms.dynamite.zzn) r0     // Catch:{ RemoteException -> 0x01a6, all -> 0x01a2 }
            if (r0 == 0) goto L_0x012b
            android.database.Cursor r0 = r0.zza     // Catch:{ RemoteException -> 0x01a6, all -> 0x01a2 }
            if (r0 == 0) goto L_0x012b
            int r3 = r0.getInt(r3)     // Catch:{ RemoteException -> 0x01a6, all -> 0x01a2 }
            goto L_0x01ca
        L_0x012b:
            com.google.android.gms.dynamic.IObjectWrapper r5 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r10)     // Catch:{ RemoteException -> 0x01a6, all -> 0x01a2 }
            java.lang.ThreadLocal r0 = zzh     // Catch:{ RemoteException -> 0x01a6, all -> 0x01a2 }
            java.lang.Object r0 = r0.get()     // Catch:{ RemoteException -> 0x01a6, all -> 0x01a2 }
            java.lang.Long r0 = (java.lang.Long) r0     // Catch:{ RemoteException -> 0x01a6, all -> 0x01a2 }
            long r8 = r0.longValue()     // Catch:{ RemoteException -> 0x01a6, all -> 0x01a2 }
            r6 = r11
            r7 = r12
            com.google.android.gms.dynamic.IObjectWrapper r11 = r4.zzk(r5, r6, r7, r8)     // Catch:{ RemoteException -> 0x01a6, all -> 0x01a2 }
            java.lang.Object r11 = com.google.android.gms.dynamic.ObjectWrapper.unwrap(r11)     // Catch:{ RemoteException -> 0x01a6, all -> 0x01a2 }
            android.database.Cursor r11 = (android.database.Cursor) r11     // Catch:{ RemoteException -> 0x01a6, all -> 0x01a2 }
            if (r11 == 0) goto L_0x0168
            boolean r12 = r11.moveToFirst()     // Catch:{ RemoteException -> 0x0179, all -> 0x0175 }
            if (r12 != 0) goto L_0x0150
            goto L_0x0168
        L_0x0150:
            int r12 = r11.getInt(r3)     // Catch:{ RemoteException -> 0x0179, all -> 0x0175 }
            if (r12 <= 0) goto L_0x015e
            boolean r0 = zze(r11)     // Catch:{ RemoteException -> 0x0179, all -> 0x0175 }
            if (r0 == 0) goto L_0x015e
            goto L_0x015f
        L_0x015e:
            r2 = r11
        L_0x015f:
            if (r2 == 0) goto L_0x0164
            r2.close()     // Catch:{ all -> 0x01d8 }
        L_0x0164:
            r3 = r12
            goto L_0x01ca
        L_0x0168:
            java.lang.String r12 = "DynamiteModule"
            java.lang.String r0 = "Failed to retrieve remote module version."
            android.util.Log.w(r12, r0)     // Catch:{ RemoteException -> 0x0179, all -> 0x0175 }
            if (r11 == 0) goto L_0x01c8
            r11.close()     // Catch:{ all -> 0x01d8 }
            goto L_0x01c8
        L_0x0175:
            r0 = move-exception
            r12 = r0
            r2 = r11
            goto L_0x01ce
        L_0x0179:
            r0 = move-exception
            r12 = r0
            r2 = r11
            goto L_0x01a9
        L_0x017d:
            r6 = r11
            r7 = r12
            r11 = 2
            if (r0 != r11) goto L_0x0192
            java.lang.String r11 = "DynamiteModule"
            java.lang.String r12 = "IDynamite loader version = 2, no high precision latency measurement."
            android.util.Log.w(r11, r12)     // Catch:{ RemoteException -> 0x01a6, all -> 0x01a2 }
            com.google.android.gms.dynamic.IObjectWrapper r11 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r10)     // Catch:{ RemoteException -> 0x01a6, all -> 0x01a2 }
            int r3 = r4.zzg(r11, r6, r7)     // Catch:{ RemoteException -> 0x01a6, all -> 0x01a2 }
            goto L_0x01ca
        L_0x0192:
            java.lang.String r11 = "DynamiteModule"
            java.lang.String r12 = "IDynamite loader version < 2, falling back to getModuleVersion2"
            android.util.Log.w(r11, r12)     // Catch:{ RemoteException -> 0x01a6, all -> 0x01a2 }
            com.google.android.gms.dynamic.IObjectWrapper r11 = com.google.android.gms.dynamic.ObjectWrapper.wrap(r10)     // Catch:{ RemoteException -> 0x01a6, all -> 0x01a2 }
            int r3 = r4.zzf(r11, r6, r7)     // Catch:{ RemoteException -> 0x01a6, all -> 0x01a2 }
            goto L_0x01ca
        L_0x01a2:
            r0 = move-exception
            r11 = r0
            r12 = r11
            goto L_0x01ce
        L_0x01a6:
            r0 = move-exception
            r11 = r0
            r12 = r11
        L_0x01a9:
            java.lang.String r11 = "DynamiteModule"
            java.lang.String r12 = r12.getMessage()     // Catch:{ all -> 0x01cb }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x01cb }
            r0.<init>()     // Catch:{ all -> 0x01cb }
            java.lang.String r1 = "Failed to retrieve remote module version: "
            r0.append(r1)     // Catch:{ all -> 0x01cb }
            r0.append(r12)     // Catch:{ all -> 0x01cb }
            java.lang.String r12 = r0.toString()     // Catch:{ all -> 0x01cb }
            android.util.Log.w(r11, r12)     // Catch:{ all -> 0x01cb }
            if (r2 == 0) goto L_0x01c9
            r2.close()     // Catch:{ all -> 0x01d8 }
        L_0x01c8:
            goto L_0x01ca
        L_0x01c9:
        L_0x01ca:
            return r3
        L_0x01cb:
            r0 = move-exception
            r11 = r0
            r12 = r11
        L_0x01ce:
            if (r2 == 0) goto L_0x01d3
            r2.close()     // Catch:{ all -> 0x01d8 }
        L_0x01d3:
            throw r12     // Catch:{ all -> 0x01d8 }
        L_0x01d4:
            r0 = move-exception
            r11 = r0
            monitor-exit(r1)     // Catch:{ all -> 0x01d4 }
            throw r11     // Catch:{ all -> 0x01d8 }
        L_0x01d8:
            r0 = move-exception
            r11 = r0
            com.google.android.gms.common.util.CrashUtils.addDynamiteErrorToDropBox(r10, r11)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zza(android.content.Context, java.lang.String, boolean):int");
    }

    /* JADX WARNING: Removed duplicated region for block: B:64:0x00cf A[Catch:{ all -> 0x00eb }] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00d0 A[Catch:{ all -> 0x00eb }] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x00f0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int zzb(android.content.Context r9, java.lang.String r10, boolean r11, boolean r12) throws com.google.android.gms.dynamite.DynamiteModule.LoadingException {
        /*
            r1 = 0
            android.content.ContentResolver r2 = r9.getContentResolver()     // Catch:{ Exception -> 0x00c7, all -> 0x00c3 }
            java.lang.ThreadLocal r9 = zzh     // Catch:{ Exception -> 0x00c7, all -> 0x00c3 }
            java.lang.Object r9 = r9.get()     // Catch:{ Exception -> 0x00c7, all -> 0x00c3 }
            java.lang.Long r9 = (java.lang.Long) r9     // Catch:{ Exception -> 0x00c7, all -> 0x00c3 }
            long r3 = r9.longValue()     // Catch:{ Exception -> 0x00c7, all -> 0x00c3 }
            java.lang.String r9 = "api_force_staging"
            java.lang.String r0 = "api"
            r8 = 1
            if (r8 == r11) goto L_0x0019
            r9 = r0
        L_0x0019:
            android.net.Uri$Builder r11 = new android.net.Uri$Builder     // Catch:{ Exception -> 0x00c7, all -> 0x00c3 }
            r11.<init>()     // Catch:{ Exception -> 0x00c7, all -> 0x00c3 }
            java.lang.String r0 = "content"
            android.net.Uri$Builder r11 = r11.scheme(r0)     // Catch:{ Exception -> 0x00c7, all -> 0x00c3 }
            java.lang.String r0 = "com.google.android.gms.chimera"
            android.net.Uri$Builder r11 = r11.authority(r0)     // Catch:{ Exception -> 0x00c7, all -> 0x00c3 }
            android.net.Uri$Builder r9 = r11.path(r9)     // Catch:{ Exception -> 0x00c7, all -> 0x00c3 }
            android.net.Uri$Builder r9 = r9.appendPath(r10)     // Catch:{ Exception -> 0x00c7, all -> 0x00c3 }
            java.lang.String r10 = "requestStartTime"
            java.lang.String r11 = java.lang.String.valueOf(r3)     // Catch:{ Exception -> 0x00c7, all -> 0x00c3 }
            android.net.Uri$Builder r9 = r9.appendQueryParameter(r10, r11)     // Catch:{ Exception -> 0x00c7, all -> 0x00c3 }
            android.net.Uri r3 = r9.build()     // Catch:{ Exception -> 0x00c7, all -> 0x00c3 }
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r9 = r2.query(r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x00c7, all -> 0x00c3 }
            if (r9 == 0) goto L_0x00ad
            boolean r10 = r9.moveToFirst()     // Catch:{ Exception -> 0x00c0, all -> 0x00bc }
            if (r10 == 0) goto L_0x00ad
            r10 = 0
            int r11 = r9.getInt(r10)     // Catch:{ Exception -> 0x00c0, all -> 0x00bc }
            if (r11 <= 0) goto L_0x0092
            java.lang.Class<com.google.android.gms.dynamite.DynamiteModule> r2 = com.google.android.gms.dynamite.DynamiteModule.class
            monitor-enter(r2)     // Catch:{ Exception -> 0x00c0, all -> 0x00bc }
            r0 = 2
            java.lang.String r0 = r9.getString(r0)     // Catch:{ all -> 0x008e }
            zzc = r0     // Catch:{ all -> 0x008e }
            java.lang.String r0 = "loaderVersion"
            int r0 = r9.getColumnIndex(r0)     // Catch:{ all -> 0x008e }
            if (r0 < 0) goto L_0x0070
            int r0 = r9.getInt(r0)     // Catch:{ all -> 0x008e }
            zze = r0     // Catch:{ all -> 0x008e }
        L_0x0070:
            java.lang.String r0 = "disableStandaloneDynamiteLoader2"
            int r0 = r9.getColumnIndex(r0)     // Catch:{ all -> 0x008e }
            if (r0 < 0) goto L_0x0084
            int r0 = r9.getInt(r0)     // Catch:{ all -> 0x008e }
            if (r0 == 0) goto L_0x007f
            goto L_0x0080
        L_0x007f:
            r8 = r10
        L_0x0080:
            zzd = r8     // Catch:{ all -> 0x008e }
            r10 = r8
            goto L_0x0085
        L_0x0084:
        L_0x0085:
            monitor-exit(r2)     // Catch:{ all -> 0x008e }
            boolean r0 = zze(r9)     // Catch:{ Exception -> 0x00c0, all -> 0x00bc }
            if (r0 == 0) goto L_0x0093
            r9 = r1
            goto L_0x0093
        L_0x008e:
            r0 = move-exception
            r10 = r0
            monitor-exit(r2)     // Catch:{ all -> 0x008e }
            throw r10     // Catch:{ Exception -> 0x00c0, all -> 0x00bc }
        L_0x0092:
        L_0x0093:
            if (r12 == 0) goto L_0x00a7
            if (r10 != 0) goto L_0x0098
            goto L_0x00a7
        L_0x0098:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r10 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ Exception -> 0x00a4, all -> 0x00a0 }
            java.lang.String r11 = "forcing fallback to container DynamiteLoader impl"
            r10.<init>(r11, r1)     // Catch:{ Exception -> 0x00a4, all -> 0x00a0 }
            throw r10     // Catch:{ Exception -> 0x00a4, all -> 0x00a0 }
        L_0x00a0:
            r0 = move-exception
            r10 = r0
            r1 = r9
            goto L_0x00ee
        L_0x00a4:
            r0 = move-exception
            r10 = r0
            goto L_0x00cb
        L_0x00a7:
            if (r9 == 0) goto L_0x00ac
            r9.close()
        L_0x00ac:
            return r11
        L_0x00ad:
            java.lang.String r10 = "DynamiteModule"
            java.lang.String r11 = "Failed to retrieve remote module version."
            android.util.Log.w(r10, r11)     // Catch:{ Exception -> 0x00c0, all -> 0x00bc }
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r10 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ Exception -> 0x00c0, all -> 0x00bc }
            java.lang.String r11 = "Failed to connect to dynamite module ContentResolver."
            r10.<init>(r11, r1)     // Catch:{ Exception -> 0x00c0, all -> 0x00bc }
            throw r10     // Catch:{ Exception -> 0x00c0, all -> 0x00bc }
        L_0x00bc:
            r0 = move-exception
            r10 = r0
            r1 = r9
            goto L_0x00ee
        L_0x00c0:
            r0 = move-exception
            r10 = r0
            goto L_0x00cb
        L_0x00c3:
            r0 = move-exception
            r9 = r0
            r10 = r9
            goto L_0x00ee
        L_0x00c7:
            r0 = move-exception
            r9 = r0
            r10 = r9
            r9 = r1
        L_0x00cb:
            boolean r11 = r10 instanceof com.google.android.gms.dynamite.DynamiteModule.LoadingException     // Catch:{ all -> 0x00eb }
            if (r11 == 0) goto L_0x00d0
            throw r10     // Catch:{ all -> 0x00eb }
        L_0x00d0:
            com.google.android.gms.dynamite.DynamiteModule$LoadingException r11 = new com.google.android.gms.dynamite.DynamiteModule$LoadingException     // Catch:{ all -> 0x00eb }
            java.lang.String r12 = r10.getMessage()     // Catch:{ all -> 0x00eb }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00eb }
            r0.<init>()     // Catch:{ all -> 0x00eb }
            java.lang.String r2 = "V2 version check failed: "
            r0.append(r2)     // Catch:{ all -> 0x00eb }
            r0.append(r12)     // Catch:{ all -> 0x00eb }
            java.lang.String r12 = r0.toString()     // Catch:{ all -> 0x00eb }
            r11.<init>(r12, r10, r1)     // Catch:{ all -> 0x00eb }
            throw r11     // Catch:{ all -> 0x00eb }
        L_0x00eb:
            r0 = move-exception
            r10 = r0
            r1 = r9
        L_0x00ee:
            if (r1 == 0) goto L_0x00f3
            r1.close()
        L_0x00f3:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zzb(android.content.Context, java.lang.String, boolean, boolean):int");
    }

    private static DynamiteModule zzc(Context context, String str) {
        Log.i("DynamiteModule", "Selected local version of ".concat(String.valueOf(str)));
        return new DynamiteModule(context);
    }

    private static void zzd(ClassLoader classLoader) throws LoadingException {
        zzr zzr;
        try {
            IBinder iBinder = (IBinder) classLoader.loadClass("com.google.android.gms.dynamiteloader.DynamiteLoaderV2").getConstructor(new Class[0]).newInstance(new Object[0]);
            if (iBinder == null) {
                zzr = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoaderV2");
                if (queryLocalInterface instanceof zzr) {
                    zzr = (zzr) queryLocalInterface;
                } else {
                    zzr = new zzr(iBinder);
                }
            }
            zzl = zzr;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new LoadingException("Failed to instantiate dynamite loader", e, (zzp) null);
        }
    }

    private static boolean zze(Cursor cursor) {
        zzn zzn = (zzn) zzg.get();
        if (zzn == null || zzn.zza != null) {
            return false;
        }
        zzn.zza = cursor;
        return true;
    }

    private static boolean zzf(Context context) {
        if (Boolean.TRUE.equals((Object) null) || Boolean.TRUE.equals(zzf)) {
            return true;
        }
        boolean z = false;
        if (zzf == null) {
            ProviderInfo resolveContentProvider = context.getPackageManager().resolveContentProvider("com.google.android.gms.chimera", 0);
            if (GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(context, 10000000) == 0 && resolveContentProvider != null && "com.google.android.gms".equals(resolveContentProvider.packageName)) {
                z = true;
            }
            Boolean valueOf = Boolean.valueOf(z);
            zzf = valueOf;
            z = valueOf.booleanValue();
            if (z && resolveContentProvider.applicationInfo != null && (resolveContentProvider.applicationInfo.flags & 129) == 0) {
                Log.i("DynamiteModule", "Non-system-image GmsCore APK, forcing V1");
                zzd = true;
            }
        }
        if (!z) {
            Log.e("DynamiteModule", "Invalid GmsCore APK, remote loading disabled.");
        }
        return z;
    }

    private static zzq zzg(Context context) {
        zzq zzq;
        synchronized (DynamiteModule.class) {
            zzq zzq2 = zzk;
            if (zzq2 != null) {
                return zzq2;
            }
            try {
                IBinder iBinder = (IBinder) context.createPackageContext("com.google.android.gms", 3).getClassLoader().loadClass("com.google.android.gms.chimera.container.DynamiteLoaderImpl").newInstance();
                if (iBinder == null) {
                    zzq = null;
                } else {
                    IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoader");
                    zzq = queryLocalInterface instanceof zzq ? (zzq) queryLocalInterface : new zzq(iBinder);
                }
                if (zzq != null) {
                    zzk = zzq;
                    return zzq;
                }
            } catch (Exception e) {
                Log.e("DynamiteModule", "Failed to load IDynamiteLoader from GmsCore: " + e.getMessage());
            }
        }
        return null;
    }

    @ResultIgnorabilityUnspecified
    public Context getModuleContext() {
        return this.zzj;
    }

    public IBinder instantiate(String className) throws LoadingException {
        try {
            return (IBinder) this.zzj.getClassLoader().loadClass(className).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new LoadingException("Failed to instantiate module class: ".concat(String.valueOf(className)), e, (zzp) null);
        }
    }
}
