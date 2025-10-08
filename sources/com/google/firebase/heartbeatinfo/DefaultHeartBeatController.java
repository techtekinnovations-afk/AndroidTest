package com.google.firebase.heartbeatinfo;

import android.content.Context;
import android.util.Base64OutputStream;
import androidx.core.os.UserManagerCompat;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.annotations.concurrent.Background;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.Dependency;
import com.google.firebase.components.Qualified;
import com.google.firebase.heartbeatinfo.HeartBeatInfo;
import com.google.firebase.inject.Provider;
import com.google.firebase.platforminfo.UserAgentPublisher;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.zip.GZIPOutputStream;
import org.json.JSONArray;
import org.json.JSONObject;

public class DefaultHeartBeatController implements HeartBeatController, HeartBeatInfo {
    private final Context applicationContext;
    private final Executor backgroundExecutor;
    private final Set<HeartBeatConsumer> consumers;
    private final Provider<HeartBeatInfoStorage> storageProvider;
    private final Provider<UserAgentPublisher> userAgentProvider;

    public Task<Void> registerHeartBeat() {
        if (this.consumers.size() <= 0) {
            return Tasks.forResult(null);
        }
        if (!UserManagerCompat.isUserUnlocked(this.applicationContext)) {
            return Tasks.forResult(null);
        }
        return Tasks.call(this.backgroundExecutor, new DefaultHeartBeatController$$ExternalSyntheticLambda3(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$registerHeartBeat$0$com-google-firebase-heartbeatinfo-DefaultHeartBeatController  reason: not valid java name */
    public /* synthetic */ Void m1931lambda$registerHeartBeat$0$comgooglefirebaseheartbeatinfoDefaultHeartBeatController() throws Exception {
        synchronized (this) {
            this.storageProvider.get().storeHeartBeat(System.currentTimeMillis(), this.userAgentProvider.get().getUserAgent());
        }
        return null;
    }

    public Task<String> getHeartBeatsHeader() {
        if (!UserManagerCompat.isUserUnlocked(this.applicationContext)) {
            return Tasks.forResult("");
        }
        return Tasks.call(this.backgroundExecutor, new DefaultHeartBeatController$$ExternalSyntheticLambda1(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$getHeartBeatsHeader$1$com-google-firebase-heartbeatinfo-DefaultHeartBeatController  reason: not valid java name */
    public /* synthetic */ String m1930lambda$getHeartBeatsHeader$1$comgooglefirebaseheartbeatinfoDefaultHeartBeatController() throws Exception {
        GZIPOutputStream gzip;
        String byteArrayOutputStream;
        synchronized (this) {
            HeartBeatInfoStorage storage = this.storageProvider.get();
            List<HeartBeatResult> allHeartBeats = storage.getAllHeartBeats();
            storage.deleteAllHeartBeats();
            JSONArray array = new JSONArray();
            for (int i = 0; i < allHeartBeats.size(); i++) {
                HeartBeatResult result = allHeartBeats.get(i);
                JSONObject obj = new JSONObject();
                obj.put("agent", result.getUserAgent());
                obj.put("dates", new JSONArray(result.getUsedDates()));
                array.put(obj);
            }
            JSONObject output = new JSONObject();
            output.put("heartbeats", array);
            output.put("version", "2");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Base64OutputStream b64os = new Base64OutputStream(out, 11);
            try {
                gzip = new GZIPOutputStream(b64os);
                gzip.write(output.toString().getBytes("UTF-8"));
                gzip.close();
                b64os.close();
                byteArrayOutputStream = out.toString("UTF-8");
            } catch (Throwable th) {
                try {
                    b64os.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        return byteArrayOutputStream;
        throw th;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private DefaultHeartBeatController(android.content.Context r7, java.lang.String r8, java.util.Set<com.google.firebase.heartbeatinfo.HeartBeatConsumer> r9, com.google.firebase.inject.Provider<com.google.firebase.platforminfo.UserAgentPublisher> r10, java.util.concurrent.Executor r11) {
        /*
            r6 = this;
            com.google.firebase.components.Lazy r1 = new com.google.firebase.components.Lazy
            com.google.firebase.heartbeatinfo.DefaultHeartBeatController$$ExternalSyntheticLambda0 r0 = new com.google.firebase.heartbeatinfo.DefaultHeartBeatController$$ExternalSyntheticLambda0
            r0.<init>(r7, r8)
            r1.<init>(r0)
            r0 = r6
            r5 = r7
            r2 = r9
            r4 = r10
            r3 = r11
            r0.<init>((com.google.firebase.inject.Provider<com.google.firebase.heartbeatinfo.HeartBeatInfoStorage>) r1, (java.util.Set<com.google.firebase.heartbeatinfo.HeartBeatConsumer>) r2, (java.util.concurrent.Executor) r3, (com.google.firebase.inject.Provider<com.google.firebase.platforminfo.UserAgentPublisher>) r4, (android.content.Context) r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.heartbeatinfo.DefaultHeartBeatController.<init>(android.content.Context, java.lang.String, java.util.Set, com.google.firebase.inject.Provider, java.util.concurrent.Executor):void");
    }

    static /* synthetic */ HeartBeatInfoStorage lambda$new$2(Context context, String persistenceKey) {
        return new HeartBeatInfoStorage(context, persistenceKey);
    }

    DefaultHeartBeatController(Provider<HeartBeatInfoStorage> testStorage, Set<HeartBeatConsumer> consumers2, Executor executor, Provider<UserAgentPublisher> userAgentProvider2, Context context) {
        this.storageProvider = testStorage;
        this.consumers = consumers2;
        this.backgroundExecutor = executor;
        this.userAgentProvider = userAgentProvider2;
        this.applicationContext = context;
    }

    public static Component<DefaultHeartBeatController> component() {
        Qualified<Executor> backgroundExecutor2 = Qualified.qualified(Background.class, Executor.class);
        return Component.builder(DefaultHeartBeatController.class, (Class<? super T>[]) new Class[]{HeartBeatController.class, HeartBeatInfo.class}).add(Dependency.required((Class<?>) Context.class)).add(Dependency.required((Class<?>) FirebaseApp.class)).add(Dependency.setOf((Class<?>) HeartBeatConsumer.class)).add(Dependency.requiredProvider((Class<?>) UserAgentPublisher.class)).add(Dependency.required((Qualified<?>) backgroundExecutor2)).factory(new DefaultHeartBeatController$$ExternalSyntheticLambda2(backgroundExecutor2)).build();
    }

    static /* synthetic */ DefaultHeartBeatController lambda$component$3(Qualified backgroundExecutor2, ComponentContainer c) {
        return new DefaultHeartBeatController((Context) c.get(Context.class), ((FirebaseApp) c.get(FirebaseApp.class)).getPersistenceKey(), c.setOf(HeartBeatConsumer.class), c.getProvider(UserAgentPublisher.class), (Executor) c.get(backgroundExecutor2));
    }

    public synchronized HeartBeatInfo.HeartBeat getHeartBeatCode(String heartBeatTag) {
        long presentTime = System.currentTimeMillis();
        HeartBeatInfoStorage storage = this.storageProvider.get();
        if (storage.shouldSendGlobalHeartBeat(presentTime)) {
            storage.postHeartBeatCleanUp();
            return HeartBeatInfo.HeartBeat.GLOBAL;
        }
        return HeartBeatInfo.HeartBeat.NONE;
    }
}
