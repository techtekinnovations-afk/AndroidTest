package com.google.firebase.firestore;

import android.content.Context;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseAppLifecycleListener;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.appcheck.interop.InteropAppCheckTokenProvider;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.remote.GrpcMetadataProvider;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.inject.Deferred;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class FirestoreMultiDbComponent implements FirebaseAppLifecycleListener, FirebaseFirestore.InstanceRegistry {
    private final FirebaseApp app;
    private final Deferred<InteropAppCheckTokenProvider> appCheckProvider;
    private final Deferred<InternalAuthProvider> authProvider;
    private final Context context;
    private final Map<String, FirebaseFirestore> instances = new HashMap();
    private final GrpcMetadataProvider metadataProvider;

    FirestoreMultiDbComponent(Context context2, FirebaseApp app2, Deferred<InternalAuthProvider> authProvider2, Deferred<InteropAppCheckTokenProvider> appCheckProvider2, GrpcMetadataProvider metadataProvider2) {
        this.context = context2;
        this.app = app2;
        this.authProvider = authProvider2;
        this.appCheckProvider = appCheckProvider2;
        this.metadataProvider = metadataProvider2;
        this.app.addLifecycleEventListener(this);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0025, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.google.firebase.firestore.FirebaseFirestore get(java.lang.String r9) {
        /*
            r8 = this;
            monitor-enter(r8)
            java.util.Map<java.lang.String, com.google.firebase.firestore.FirebaseFirestore> r0 = r8.instances     // Catch:{ all -> 0x0026 }
            java.lang.Object r0 = r0.get(r9)     // Catch:{ all -> 0x0026 }
            com.google.firebase.firestore.FirebaseFirestore r0 = (com.google.firebase.firestore.FirebaseFirestore) r0     // Catch:{ all -> 0x0026 }
            if (r0 != 0) goto L_0x0022
            android.content.Context r1 = r8.context     // Catch:{ all -> 0x0026 }
            com.google.firebase.FirebaseApp r2 = r8.app     // Catch:{ all -> 0x0026 }
            com.google.firebase.inject.Deferred<com.google.firebase.auth.internal.InternalAuthProvider> r3 = r8.authProvider     // Catch:{ all -> 0x0026 }
            com.google.firebase.inject.Deferred<com.google.firebase.appcheck.interop.InteropAppCheckTokenProvider> r4 = r8.appCheckProvider     // Catch:{ all -> 0x0026 }
            com.google.firebase.firestore.remote.GrpcMetadataProvider r7 = r8.metadataProvider     // Catch:{ all -> 0x0026 }
            r6 = r8
            r5 = r9
            com.google.firebase.firestore.FirebaseFirestore r9 = com.google.firebase.firestore.FirebaseFirestore.newInstance(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x002b }
            r0 = r9
            java.util.Map<java.lang.String, com.google.firebase.firestore.FirebaseFirestore> r9 = r6.instances     // Catch:{ all -> 0x002b }
            r9.put(r5, r0)     // Catch:{ all -> 0x002b }
            goto L_0x0024
        L_0x0022:
            r6 = r8
            r5 = r9
        L_0x0024:
            monitor-exit(r8)
            return r0
        L_0x0026:
            r0 = move-exception
            r6 = r8
        L_0x0028:
            r9 = r0
            monitor-exit(r8)     // Catch:{ all -> 0x002b }
            throw r9
        L_0x002b:
            r0 = move-exception
            goto L_0x0028
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.FirestoreMultiDbComponent.get(java.lang.String):com.google.firebase.firestore.FirebaseFirestore");
    }

    public synchronized void remove(String databaseId) {
        this.instances.remove(databaseId);
    }

    public synchronized void onDeleted(String firebaseAppName, FirebaseOptions options) {
        Iterator it = new ArrayList(this.instances.entrySet()).iterator();
        while (it.hasNext()) {
            Map.Entry<String, FirebaseFirestore> entry = (Map.Entry) it.next();
            entry.getValue().terminate();
            Assert.hardAssert(!this.instances.containsKey(entry.getKey()), "terminate() should have removed its entry from `instances` for key: %s", entry.getKey());
        }
    }
}
