package com.google.firebase.heartbeatinfo;

import android.content.Context;
import android.os.Build;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import com.google.firebase.datastorage.JavaDataStorage;
import com.google.firebase.datastorage.JavaDataStorageKt;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;

class HeartBeatInfoStorage {
    private static final Preferences.Key<Long> GLOBAL = PreferencesKeys.longKey("fire-global");
    private static final String HEARTBEAT_PREFERENCES_NAME = "FirebaseHeartBeat";
    private static final int HEART_BEAT_COUNT_LIMIT = 30;
    private static final Preferences.Key<Long> HEART_BEAT_COUNT_TAG = PreferencesKeys.longKey("fire-count");
    private static final Preferences.Key<String> LAST_STORED_DATE = PreferencesKeys.stringKey("last-used-date");
    private static final String PREFERENCES_NAME = "FirebaseAppHeartBeat";
    private static HeartBeatInfoStorage instance = null;
    private final JavaDataStorage firebaseDataStore;

    public HeartBeatInfoStorage(Context applicationContext, String persistenceKey) {
        this.firebaseDataStore = new JavaDataStorage(applicationContext, HEARTBEAT_PREFERENCES_NAME + persistenceKey);
    }

    HeartBeatInfoStorage(JavaDataStorage javaDataStorage) {
        this.firebaseDataStore = javaDataStorage;
    }

    /* access modifiers changed from: package-private */
    public int getHeartBeatCount() {
        return ((Long) this.firebaseDataStore.getSync(HEART_BEAT_COUNT_TAG, 0L)).intValue();
    }

    /* access modifiers changed from: package-private */
    public synchronized void deleteAllHeartBeats() {
        this.firebaseDataStore.editSync(new HeartBeatInfoStorage$$ExternalSyntheticLambda3(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$deleteAllHeartBeats$0$com-google-firebase-heartbeatinfo-HeartBeatInfoStorage  reason: not valid java name */
    public /* synthetic */ Unit m1932lambda$deleteAllHeartBeats$0$comgooglefirebaseheartbeatinfoHeartBeatInfoStorage(MutablePreferences pref) {
        long counter = 0;
        for (Map.Entry<Preferences.Key<?>, Object> entry : pref.asMap().entrySet()) {
            if (entry.getValue() instanceof Set) {
                Preferences.Key<Set<String>> key = entry.getKey();
                String today = getFormattedDate(System.currentTimeMillis());
                if (((Set) entry.getValue()).contains(today)) {
                    pref.set(key, HeartBeatInfoStorage$$ExternalSyntheticBackport1.m(new Object[]{today}));
                    counter++;
                } else {
                    pref.remove(key);
                }
            }
        }
        if (counter == 0) {
            pref.remove(HEART_BEAT_COUNT_TAG);
            return null;
        }
        pref.set(HEART_BEAT_COUNT_TAG, Long.valueOf(counter));
        return null;
    }

    /* access modifiers changed from: package-private */
    public synchronized List<HeartBeatResult> getAllHeartBeats() {
        ArrayList<HeartBeatResult> heartBeatResults;
        heartBeatResults = new ArrayList<>();
        String today = getFormattedDate(System.currentTimeMillis());
        for (Map.Entry<Preferences.Key<?>, Object> entry : this.firebaseDataStore.getAllSync().entrySet()) {
            if (entry.getValue() instanceof Set) {
                Set<String> dates = new HashSet<>((Set) entry.getValue());
                dates.remove(today);
                if (!dates.isEmpty()) {
                    heartBeatResults.add(HeartBeatResult.create(entry.getKey().getName(), new ArrayList(dates)));
                }
            }
        }
        updateGlobalHeartBeat(System.currentTimeMillis());
        return heartBeatResults;
    }

    private synchronized Preferences.Key<Set<String>> getStoredUserAgentString(MutablePreferences preferences, String dateString) {
        for (Map.Entry<Preferences.Key<?>, Object> entry : preferences.asMap().entrySet()) {
            if (entry.getValue() instanceof Set) {
                for (String date : (Set) entry.getValue()) {
                    if (dateString.equals(date)) {
                        return PreferencesKeys.stringSetKey(entry.getKey().getName());
                    }
                }
                continue;
            }
        }
        return null;
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [androidx.datastore.preferences.core.Preferences$Key, androidx.datastore.preferences.core.Preferences$Key<java.util.Set<java.lang.String>>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void updateStoredUserAgent(androidx.datastore.preferences.core.MutablePreferences r3, androidx.datastore.preferences.core.Preferences.Key<java.util.Set<java.lang.String>> r4, java.lang.String r5) {
        /*
            r2 = this;
            monitor-enter(r2)
            r2.removeStoredDate(r3, r5)     // Catch:{ all -> 0x001c }
            java.util.HashSet r0 = new java.util.HashSet     // Catch:{ all -> 0x001c }
            java.util.HashSet r1 = new java.util.HashSet     // Catch:{ all -> 0x001c }
            r1.<init>()     // Catch:{ all -> 0x001c }
            java.lang.Object r1 = com.google.firebase.datastorage.JavaDataStorageKt.getOrDefault(r3, r4, r1)     // Catch:{ all -> 0x001c }
            java.util.Collection r1 = (java.util.Collection) r1     // Catch:{ all -> 0x001c }
            r0.<init>(r1)     // Catch:{ all -> 0x001c }
            r0.add(r5)     // Catch:{ all -> 0x001c }
            r3.set(r4, r0)     // Catch:{ all -> 0x001c }
            monitor-exit(r2)
            return
        L_0x001c:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x001c }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.heartbeatinfo.HeartBeatInfoStorage.updateStoredUserAgent(androidx.datastore.preferences.core.MutablePreferences, androidx.datastore.preferences.core.Preferences$Key, java.lang.String):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void removeStoredDate(androidx.datastore.preferences.core.MutablePreferences r4, java.lang.String r5) {
        /*
            r3 = this;
            monitor-enter(r3)
            androidx.datastore.preferences.core.Preferences$Key r0 = r3.getStoredUserAgentString(r4, r5)     // Catch:{ all -> 0x002b }
            if (r0 != 0) goto L_0x0009
            monitor-exit(r3)
            return
        L_0x0009:
            java.util.HashSet r1 = new java.util.HashSet     // Catch:{ all -> 0x002b }
            java.util.HashSet r2 = new java.util.HashSet     // Catch:{ all -> 0x002b }
            r2.<init>()     // Catch:{ all -> 0x002b }
            java.lang.Object r2 = com.google.firebase.datastorage.JavaDataStorageKt.getOrDefault(r4, r0, r2)     // Catch:{ all -> 0x002b }
            java.util.Collection r2 = (java.util.Collection) r2     // Catch:{ all -> 0x002b }
            r1.<init>(r2)     // Catch:{ all -> 0x002b }
            r1.remove(r5)     // Catch:{ all -> 0x002b }
            boolean r2 = r1.isEmpty()     // Catch:{ all -> 0x002b }
            if (r2 == 0) goto L_0x0026
            r4.remove(r0)     // Catch:{ all -> 0x002b }
            goto L_0x0029
        L_0x0026:
            r4.set(r0, r1)     // Catch:{ all -> 0x002b }
        L_0x0029:
            monitor-exit(r3)
            return
        L_0x002b:
            r4 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x002b }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.heartbeatinfo.HeartBeatInfoStorage.removeStoredDate(androidx.datastore.preferences.core.MutablePreferences, java.lang.String):void");
    }

    /* access modifiers changed from: package-private */
    public synchronized void postHeartBeatCleanUp() {
        this.firebaseDataStore.editSync(new HeartBeatInfoStorage$$ExternalSyntheticLambda4(this, getFormattedDate(System.currentTimeMillis())));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$postHeartBeatCleanUp$1$com-google-firebase-heartbeatinfo-HeartBeatInfoStorage  reason: not valid java name */
    public /* synthetic */ Unit m1933lambda$postHeartBeatCleanUp$1$comgooglefirebaseheartbeatinfoHeartBeatInfoStorage(String dateString, MutablePreferences pref) {
        pref.set(LAST_STORED_DATE, dateString);
        removeStoredDate(pref, dateString);
        return null;
    }

    private synchronized String getFormattedDate(long millis) {
        if (Build.VERSION.SDK_INT >= 26) {
            return new Date(millis).toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
        return new SimpleDateFormat("yyyy-MM-dd", Locale.UK).format(new Date(millis));
    }

    /* access modifiers changed from: package-private */
    public synchronized void storeHeartBeat(long millis, String userAgentString) {
        this.firebaseDataStore.editSync(new HeartBeatInfoStorage$$ExternalSyntheticLambda2(this, getFormattedDate(millis), userAgentString, PreferencesKeys.stringSetKey(userAgentString)));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$storeHeartBeat$2$com-google-firebase-heartbeatinfo-HeartBeatInfoStorage  reason: not valid java name */
    public /* synthetic */ Unit m1934lambda$storeHeartBeat$2$comgooglefirebaseheartbeatinfoHeartBeatInfoStorage(String dateString, String userAgentString, Preferences.Key userAgent, MutablePreferences pref) {
        if (((String) JavaDataStorageKt.getOrDefault(pref, LAST_STORED_DATE, "")).equals(dateString)) {
            Preferences.Key<Set<String>> storedUserAgent = getStoredUserAgentString(pref, dateString);
            if (storedUserAgent == null || storedUserAgent.getName().equals(userAgentString)) {
                return null;
            }
            updateStoredUserAgent(pref, userAgent, dateString);
            return null;
        }
        long heartBeatCount = ((Long) JavaDataStorageKt.getOrDefault(pref, HEART_BEAT_COUNT_TAG, 0L)).longValue();
        if (heartBeatCount + 1 == 30) {
            heartBeatCount = cleanUpStoredHeartBeats(pref);
        }
        Set<String> userAgentDateSet = new HashSet<>((Collection) JavaDataStorageKt.getOrDefault(pref, userAgent, new HashSet()));
        userAgentDateSet.add(dateString);
        pref.set(userAgent, userAgentDateSet);
        pref.set(HEART_BEAT_COUNT_TAG, Long.valueOf(heartBeatCount + 1));
        pref.set(LAST_STORED_DATE, dateString);
        return null;
    }

    private synchronized long cleanUpStoredHeartBeats(MutablePreferences preferences) {
        long heartBeatCount;
        heartBeatCount = ((Long) JavaDataStorageKt.getOrDefault(preferences, HEART_BEAT_COUNT_TAG, 0L)).longValue();
        String lowestDate = null;
        String userAgentString = "";
        Set<String> userAgentDateSet = new HashSet<>();
        for (Map.Entry<Preferences.Key<?>, Object> entry : preferences.asMap().entrySet()) {
            if (entry.getValue() instanceof Set) {
                Set<String> dateSet = (Set) entry.getValue();
                for (String date : dateSet) {
                    if (lowestDate == null || lowestDate.compareTo(date) > 0) {
                        userAgentDateSet = dateSet;
                        lowestDate = date;
                        userAgentString = entry.getKey().getName();
                    }
                }
            }
        }
        Set<String> userAgentDateSet2 = new HashSet<>(userAgentDateSet);
        userAgentDateSet2.remove(lowestDate);
        preferences.set(PreferencesKeys.stringSetKey(userAgentString), userAgentDateSet2);
        preferences.set(HEART_BEAT_COUNT_TAG, Long.valueOf(heartBeatCount - 1));
        return heartBeatCount - 1;
    }

    /* access modifiers changed from: package-private */
    public synchronized long getLastGlobalHeartBeat() {
        return ((Long) this.firebaseDataStore.getSync(GLOBAL, -1L)).longValue();
    }

    /* access modifiers changed from: package-private */
    public synchronized void updateGlobalHeartBeat(long millis) {
        this.firebaseDataStore.editSync(new HeartBeatInfoStorage$$ExternalSyntheticLambda5(millis));
    }

    /* access modifiers changed from: package-private */
    public synchronized boolean isSameDateUtc(long base, long target) {
        return getFormattedDate(base).equals(getFormattedDate(target));
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [androidx.datastore.preferences.core.Preferences$Key, androidx.datastore.preferences.core.Preferences$Key<java.lang.Long>] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean shouldSendSdkHeartBeat(androidx.datastore.preferences.core.Preferences.Key<java.lang.Long> r4, long r5) {
        /*
            r3 = this;
            monitor-enter(r3)
            com.google.firebase.datastorage.JavaDataStorage r0 = r3.firebaseDataStore     // Catch:{ all -> 0x0028 }
            r1 = -1
            java.lang.Long r1 = java.lang.Long.valueOf(r1)     // Catch:{ all -> 0x0028 }
            java.lang.Object r0 = r0.getSync(r4, r1)     // Catch:{ all -> 0x0028 }
            java.lang.Long r0 = (java.lang.Long) r0     // Catch:{ all -> 0x0028 }
            long r0 = r0.longValue()     // Catch:{ all -> 0x0028 }
            boolean r0 = r3.isSameDateUtc(r0, r5)     // Catch:{ all -> 0x0028 }
            if (r0 == 0) goto L_0x001c
            monitor-exit(r3)
            r0 = 0
            return r0
        L_0x001c:
            com.google.firebase.datastorage.JavaDataStorage r0 = r3.firebaseDataStore     // Catch:{ all -> 0x0028 }
            java.lang.Long r1 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x0028 }
            r0.putSync(r4, r1)     // Catch:{ all -> 0x0028 }
            monitor-exit(r3)
            r0 = 1
            return r0
        L_0x0028:
            r4 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0028 }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.heartbeatinfo.HeartBeatInfoStorage.shouldSendSdkHeartBeat(androidx.datastore.preferences.core.Preferences$Key, long):boolean");
    }

    /* access modifiers changed from: package-private */
    public synchronized boolean shouldSendGlobalHeartBeat(long millis) {
        return shouldSendSdkHeartBeat(GLOBAL, millis);
    }
}
