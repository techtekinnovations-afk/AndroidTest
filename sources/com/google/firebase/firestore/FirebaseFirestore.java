package com.google.firebase.firestore;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.interop.InteropAppCheckTokenProvider;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.emulators.EmulatedServiceSettings;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.firestore.auth.CredentialsProvider;
import com.google.firebase.firestore.auth.FirebaseAppCheckTokenProvider;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.core.AsyncEventListener;
import com.google.firebase.firestore.core.ComponentProvider;
import com.google.firebase.firestore.core.DatabaseInfo;
import com.google.firebase.firestore.core.FirestoreClient;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.local.SQLitePersistence;
import com.google.firebase.firestore.model.DatabaseId;
import com.google.firebase.firestore.model.FieldIndex;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.remote.FirestoreChannel;
import com.google.firebase.firestore.remote.GrpcMetadataProvider;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.ByteBufferInputStream;
import com.google.firebase.firestore.util.Executors;
import com.google.firebase.firestore.util.Function;
import com.google.firebase.firestore.util.Logger;
import com.google.firebase.firestore.util.Preconditions;
import com.google.firebase.inject.Deferred;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FirebaseFirestore {
    private static final String TAG = "FirebaseFirestore";
    private final CredentialsProvider<String> appCheckProvider;
    private final CredentialsProvider<User> authProvider;
    final FirestoreClientProvider clientProvider = new FirestoreClientProvider(new FirebaseFirestore$$ExternalSyntheticLambda5(this));
    private final Function<FirebaseFirestoreSettings, ComponentProvider> componentProviderFactory;
    private final Context context;
    private final DatabaseId databaseId;
    private EmulatedServiceSettings emulatorSettings;
    private final FirebaseApp firebaseApp;
    private final InstanceRegistry instanceRegistry;
    private final GrpcMetadataProvider metadataProvider;
    private final String persistenceKey;
    private PersistentCacheIndexManager persistentCacheIndexManager;
    private FirebaseFirestoreSettings settings;
    private final UserDataReader userDataReader;

    public interface InstanceRegistry {
        void remove(String str);
    }

    private static FirebaseApp getDefaultFirebaseApp() {
        FirebaseApp app = FirebaseApp.getInstance();
        if (app != null) {
            return app;
        }
        throw new IllegalStateException("You must call FirebaseApp.initializeApp first.");
    }

    public static FirebaseFirestore getInstance() {
        return getInstance(getDefaultFirebaseApp(), "(default)");
    }

    public static FirebaseFirestore getInstance(FirebaseApp app) {
        return getInstance(app, "(default)");
    }

    public static FirebaseFirestore getInstance(String database) {
        return getInstance(getDefaultFirebaseApp(), database);
    }

    public static FirebaseFirestore getInstance(FirebaseApp app, String database) {
        Preconditions.checkNotNull(app, "Provided FirebaseApp must not be null.");
        Preconditions.checkNotNull(database, "Provided database name must not be null.");
        FirestoreMultiDbComponent component = (FirestoreMultiDbComponent) app.get(FirestoreMultiDbComponent.class);
        Preconditions.checkNotNull(component, "Firestore component is not present.");
        return component.get(database);
    }

    static FirebaseFirestore newInstance(Context context2, FirebaseApp app, Deferred<InternalAuthProvider> deferredAuthProvider, Deferred<InteropAppCheckTokenProvider> deferredAppCheckTokenProvider, String database, InstanceRegistry instanceRegistry2, GrpcMetadataProvider metadataProvider2) {
        String projectId = app.getOptions().getProjectId();
        if (projectId != null) {
            return new FirebaseFirestore(context2, DatabaseId.forDatabase(projectId, database), app.getName(), new FirebaseAuthCredentialsProvider(deferredAuthProvider), new FirebaseAppCheckTokenProvider(deferredAppCheckTokenProvider), new FirebaseFirestore$$ExternalSyntheticLambda7(), app, instanceRegistry2, metadataProvider2);
        }
        Deferred<InternalAuthProvider> deferred = deferredAuthProvider;
        Deferred<InteropAppCheckTokenProvider> deferred2 = deferredAppCheckTokenProvider;
        String str = database;
        throw new IllegalArgumentException("FirebaseOptions.getProjectId() cannot be null");
    }

    /* JADX WARNING: type inference failed for: r6v0, types: [java.lang.Object, com.google.firebase.firestore.auth.CredentialsProvider<com.google.firebase.firestore.auth.User>] */
    /* JADX WARNING: type inference failed for: r7v0, types: [com.google.firebase.firestore.auth.CredentialsProvider<java.lang.String>, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r8v0, types: [com.google.firebase.firestore.util.Function<com.google.firebase.firestore.FirebaseFirestoreSettings, com.google.firebase.firestore.core.ComponentProvider>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    FirebaseFirestore(android.content.Context r3, com.google.firebase.firestore.model.DatabaseId r4, java.lang.String r5, com.google.firebase.firestore.auth.CredentialsProvider<com.google.firebase.firestore.auth.User> r6, com.google.firebase.firestore.auth.CredentialsProvider<java.lang.String> r7, com.google.firebase.firestore.util.Function<com.google.firebase.firestore.FirebaseFirestoreSettings, com.google.firebase.firestore.core.ComponentProvider> r8, com.google.firebase.FirebaseApp r9, com.google.firebase.firestore.FirebaseFirestore.InstanceRegistry r10, com.google.firebase.firestore.remote.GrpcMetadataProvider r11) {
        /*
            r2 = this;
            r2.<init>()
            java.lang.Object r0 = com.google.firebase.firestore.util.Preconditions.checkNotNull(r3)
            android.content.Context r0 = (android.content.Context) r0
            r2.context = r0
            java.lang.Object r0 = com.google.firebase.firestore.util.Preconditions.checkNotNull(r4)
            com.google.firebase.firestore.model.DatabaseId r0 = (com.google.firebase.firestore.model.DatabaseId) r0
            java.lang.Object r0 = com.google.firebase.firestore.util.Preconditions.checkNotNull(r0)
            com.google.firebase.firestore.model.DatabaseId r0 = (com.google.firebase.firestore.model.DatabaseId) r0
            r2.databaseId = r0
            com.google.firebase.firestore.UserDataReader r0 = new com.google.firebase.firestore.UserDataReader
            r0.<init>(r4)
            r2.userDataReader = r0
            java.lang.Object r0 = com.google.firebase.firestore.util.Preconditions.checkNotNull(r5)
            java.lang.String r0 = (java.lang.String) r0
            r2.persistenceKey = r0
            java.lang.Object r0 = com.google.firebase.firestore.util.Preconditions.checkNotNull(r6)
            com.google.firebase.firestore.auth.CredentialsProvider r0 = (com.google.firebase.firestore.auth.CredentialsProvider) r0
            r2.authProvider = r0
            java.lang.Object r0 = com.google.firebase.firestore.util.Preconditions.checkNotNull(r7)
            com.google.firebase.firestore.auth.CredentialsProvider r0 = (com.google.firebase.firestore.auth.CredentialsProvider) r0
            r2.appCheckProvider = r0
            java.lang.Object r0 = com.google.firebase.firestore.util.Preconditions.checkNotNull(r8)
            com.google.firebase.firestore.util.Function r0 = (com.google.firebase.firestore.util.Function) r0
            r2.componentProviderFactory = r0
            com.google.firebase.firestore.FirestoreClientProvider r0 = new com.google.firebase.firestore.FirestoreClientProvider
            com.google.firebase.firestore.FirebaseFirestore$$ExternalSyntheticLambda5 r1 = new com.google.firebase.firestore.FirebaseFirestore$$ExternalSyntheticLambda5
            r1.<init>(r2)
            r0.<init>(r1)
            r2.clientProvider = r0
            r2.firebaseApp = r9
            r2.instanceRegistry = r10
            r2.metadataProvider = r11
            com.google.firebase.firestore.FirebaseFirestoreSettings$Builder r0 = new com.google.firebase.firestore.FirebaseFirestoreSettings$Builder
            r0.<init>()
            com.google.firebase.firestore.FirebaseFirestoreSettings r0 = r0.build()
            r2.settings = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.FirebaseFirestore.<init>(android.content.Context, com.google.firebase.firestore.model.DatabaseId, java.lang.String, com.google.firebase.firestore.auth.CredentialsProvider, com.google.firebase.firestore.auth.CredentialsProvider, com.google.firebase.firestore.util.Function, com.google.firebase.FirebaseApp, com.google.firebase.firestore.FirebaseFirestore$InstanceRegistry, com.google.firebase.firestore.remote.GrpcMetadataProvider):void");
    }

    public FirebaseFirestoreSettings getFirestoreSettings() {
        return this.settings;
    }

    public void setFirestoreSettings(FirebaseFirestoreSettings settings2) {
        Preconditions.checkNotNull(settings2, "Provided settings must not be null.");
        synchronized (this.databaseId) {
            FirebaseFirestoreSettings settings3 = mergeEmulatorSettings(settings2, this.emulatorSettings);
            if (this.clientProvider.isConfigured()) {
                if (!this.settings.equals(settings3)) {
                    throw new IllegalStateException("FirebaseFirestore has already been started and its settings can no longer be changed. You can only call setFirestoreSettings() before calling any other methods on a FirebaseFirestore object.");
                }
            }
            this.settings = settings3;
        }
    }

    public void useEmulator(String host, int port) {
        synchronized (this.clientProvider) {
            if (!this.clientProvider.isConfigured()) {
                this.emulatorSettings = new EmulatedServiceSettings(host, port);
                this.settings = mergeEmulatorSettings(this.settings, this.emulatorSettings);
            } else {
                throw new IllegalStateException("Cannot call useEmulator() after instance has already been initialized.");
            }
        }
    }

    /* access modifiers changed from: private */
    public FirestoreClient newClient(AsyncQueue asyncQueue) {
        Throwable th;
        synchronized (this.clientProvider) {
            try {
                FirestoreClient firestoreClient = new FirestoreClient(this.context, new DatabaseInfo(this.databaseId, this.persistenceKey, this.settings.getHost(), this.settings.isSslEnabled()), this.authProvider, this.appCheckProvider, asyncQueue, this.metadataProvider, this.componentProviderFactory.apply(this.settings));
                return firestoreClient;
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    private FirebaseFirestoreSettings mergeEmulatorSettings(FirebaseFirestoreSettings settings2, EmulatedServiceSettings emulatorSettings2) {
        if (emulatorSettings2 == null) {
            return settings2;
        }
        if (!FirebaseFirestoreSettings.DEFAULT_HOST.equals(settings2.getHost())) {
            Logger.warn(TAG, "Host has been set in FirebaseFirestoreSettings and useEmulator, emulator host will be used.", new Object[0]);
        }
        return new FirebaseFirestoreSettings.Builder(settings2).setHost(emulatorSettings2.getHost() + ":" + emulatorSettings2.getPort()).setSslEnabled(false).build();
    }

    public FirebaseApp getApp() {
        return this.firebaseApp;
    }

    @Deprecated
    public Task<Void> setIndexConfiguration(String json) {
        this.clientProvider.ensureConfigured();
        Preconditions.checkState(this.settings.isPersistenceEnabled(), "Cannot enable indexes when persistence is disabled");
        List<FieldIndex> parsedIndexes = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.has("indexes")) {
                JSONArray indexes = jsonObject.getJSONArray("indexes");
                for (int i = 0; i < indexes.length(); i++) {
                    JSONObject definition = indexes.getJSONObject(i);
                    String collectionGroup = definition.getString("collectionGroup");
                    List<FieldIndex.Segment> segments = new ArrayList<>();
                    JSONArray fields = definition.optJSONArray("fields");
                    int f = 0;
                    while (fields != null && f < fields.length()) {
                        JSONObject field = fields.getJSONObject(f);
                        FieldPath fieldPath = FieldPath.fromServerFormat(field.getString("fieldPath"));
                        if ("CONTAINS".equals(field.optString("arrayConfig"))) {
                            segments.add(FieldIndex.Segment.create(fieldPath, FieldIndex.Segment.Kind.CONTAINS));
                        } else if ("ASCENDING".equals(field.optString("order"))) {
                            segments.add(FieldIndex.Segment.create(fieldPath, FieldIndex.Segment.Kind.ASCENDING));
                        } else {
                            segments.add(FieldIndex.Segment.create(fieldPath, FieldIndex.Segment.Kind.DESCENDING));
                        }
                        f++;
                    }
                    parsedIndexes.add(FieldIndex.create(-1, collectionGroup, segments, FieldIndex.INITIAL_STATE));
                }
            }
            return (Task) this.clientProvider.call(new FirebaseFirestore$$ExternalSyntheticLambda17(parsedIndexes));
        } catch (JSONException e) {
            throw new IllegalArgumentException("Failed to parse index configuration", e);
        }
    }

    public PersistentCacheIndexManager getPersistentCacheIndexManager() {
        this.clientProvider.ensureConfigured();
        if (this.persistentCacheIndexManager == null && (this.settings.isPersistenceEnabled() || (this.settings.getCacheSettings() instanceof PersistentCacheSettings))) {
            this.persistentCacheIndexManager = new PersistentCacheIndexManager(this.clientProvider);
        }
        return this.persistentCacheIndexManager;
    }

    public CollectionReference collection(String collectionPath) {
        Preconditions.checkNotNull(collectionPath, "Provided collection path must not be null.");
        this.clientProvider.ensureConfigured();
        return new CollectionReference(ResourcePath.fromString(collectionPath), this);
    }

    public DocumentReference document(String documentPath) {
        Preconditions.checkNotNull(documentPath, "Provided document path must not be null.");
        this.clientProvider.ensureConfigured();
        return DocumentReference.forPath(ResourcePath.fromString(documentPath), this);
    }

    public Query collectionGroup(String collectionId) {
        Preconditions.checkNotNull(collectionId, "Provided collection ID must not be null.");
        if (!collectionId.contains("/")) {
            this.clientProvider.ensureConfigured();
            return new Query(new Query(ResourcePath.EMPTY, collectionId), this);
        }
        throw new IllegalArgumentException(String.format("Invalid collectionId '%s'. Collection IDs must not contain '/'.", new Object[]{collectionId}));
    }

    private <ResultT> Task<ResultT> runTransaction(TransactionOptions options, Transaction.Function<ResultT> updateFunction, Executor executor) {
        this.clientProvider.ensureConfigured();
        return (Task) this.clientProvider.call(new FirebaseFirestore$$ExternalSyntheticLambda14(options, new FirebaseFirestore$$ExternalSyntheticLambda13(this, executor, updateFunction)));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$runTransaction$2$com-google-firebase-firestore-FirebaseFirestore  reason: not valid java name */
    public /* synthetic */ Task m1796lambda$runTransaction$2$comgooglefirebasefirestoreFirebaseFirestore(Executor executor, Transaction.Function updateFunction, com.google.firebase.firestore.core.Transaction internalTransaction) {
        return Tasks.call(executor, new FirebaseFirestore$$ExternalSyntheticLambda4(this, updateFunction, internalTransaction));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$runTransaction$1$com-google-firebase-firestore-FirebaseFirestore  reason: not valid java name */
    public /* synthetic */ Object m1795lambda$runTransaction$1$comgooglefirebasefirestoreFirebaseFirestore(Transaction.Function updateFunction, com.google.firebase.firestore.core.Transaction internalTransaction) throws Exception {
        return updateFunction.apply(new Transaction(internalTransaction, this));
    }

    public <TResult> Task<TResult> runTransaction(Transaction.Function<TResult> updateFunction) {
        return runTransaction(TransactionOptions.DEFAULT, updateFunction);
    }

    public <TResult> Task<TResult> runTransaction(TransactionOptions options, Transaction.Function<TResult> updateFunction) {
        Preconditions.checkNotNull(updateFunction, "Provided transaction update function must not be null.");
        return runTransaction(options, updateFunction, com.google.firebase.firestore.core.Transaction.getDefaultExecutor());
    }

    public WriteBatch batch() {
        this.clientProvider.ensureConfigured();
        return new WriteBatch(this);
    }

    public Task<Void> runBatch(WriteBatch.Function batchFunction) {
        WriteBatch batch = batch();
        batchFunction.apply(batch);
        return batch.commit();
    }

    public Task<Void> terminate() {
        this.instanceRegistry.remove(getDatabaseId().getDatabaseId());
        return this.clientProvider.terminate();
    }

    public Task<Void> waitForPendingWrites() {
        return (Task) this.clientProvider.call(new FirebaseFirestore$$ExternalSyntheticLambda0());
    }

    public Task<Void> enableNetwork() {
        return (Task) this.clientProvider.call(new FirebaseFirestore$$ExternalSyntheticLambda2());
    }

    public Task<Void> disableNetwork() {
        return (Task) this.clientProvider.call(new FirebaseFirestore$$ExternalSyntheticLambda6());
    }

    public static void setLoggingEnabled(boolean loggingEnabled) {
        if (loggingEnabled) {
            Logger.setLogLevel(Logger.Level.DEBUG);
        } else {
            Logger.setLogLevel(Logger.Level.WARN);
        }
    }

    public Task<Void> clearPersistence() {
        return (Task) this.clientProvider.executeIfShutdown(new FirebaseFirestore$$ExternalSyntheticLambda11(this), new FirebaseFirestore$$ExternalSyntheticLambda12());
    }

    /* access modifiers changed from: private */
    public Task<Void> clearPersistence(Executor executor) {
        TaskCompletionSource<Void> source = new TaskCompletionSource<>();
        executor.execute(new FirebaseFirestore$$ExternalSyntheticLambda3(this, source));
        return source.getTask();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$clearPersistence$5$com-google-firebase-firestore-FirebaseFirestore  reason: not valid java name */
    public /* synthetic */ void m1793lambda$clearPersistence$5$comgooglefirebasefirestoreFirebaseFirestore(TaskCompletionSource source) {
        try {
            SQLitePersistence.clearPersistence(this.context, this.databaseId, this.persistenceKey);
            source.setResult(null);
        } catch (FirebaseFirestoreException e) {
            source.setException(e);
        }
    }

    public ListenerRegistration addSnapshotsInSyncListener(Runnable runnable) {
        return addSnapshotsInSyncListener(Executors.DEFAULT_CALLBACK_EXECUTOR, runnable);
    }

    public ListenerRegistration addSnapshotsInSyncListener(Activity activity, Runnable runnable) {
        return addSnapshotsInSyncListener(Executors.DEFAULT_CALLBACK_EXECUTOR, activity, runnable);
    }

    public ListenerRegistration addSnapshotsInSyncListener(Executor executor, Runnable runnable) {
        return addSnapshotsInSyncListener(executor, (Activity) null, runnable);
    }

    public LoadBundleTask loadBundle(InputStream bundleData) {
        LoadBundleTask resultTask = new LoadBundleTask();
        this.clientProvider.procedure(new FirebaseFirestore$$ExternalSyntheticLambda1(bundleData, resultTask));
        return resultTask;
    }

    public LoadBundleTask loadBundle(byte[] bundleData) {
        return loadBundle((InputStream) new ByteArrayInputStream(bundleData));
    }

    public LoadBundleTask loadBundle(ByteBuffer bundleData) {
        return loadBundle((InputStream) new ByteBufferInputStream(bundleData));
    }

    public Task<Query> getNamedQuery(String name) {
        return ((Task) this.clientProvider.call(new FirebaseFirestore$$ExternalSyntheticLambda9(name))).continueWith(new FirebaseFirestore$$ExternalSyntheticLambda10(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$getNamedQuery$8$com-google-firebase-firestore-FirebaseFirestore  reason: not valid java name */
    public /* synthetic */ Query m1794lambda$getNamedQuery$8$comgooglefirebasefirestoreFirebaseFirestore(Task task) throws Exception {
        Query query = (Query) task.getResult();
        if (query != null) {
            return new Query(query, this);
        }
        return null;
    }

    private ListenerRegistration addSnapshotsInSyncListener(Executor userExecutor, Activity activity, Runnable runnable) {
        return (ListenerRegistration) this.clientProvider.call(new FirebaseFirestore$$ExternalSyntheticLambda16(new AsyncEventListener<>(userExecutor, new FirebaseFirestore$$ExternalSyntheticLambda15(runnable)), activity));
    }

    static /* synthetic */ void lambda$addSnapshotsInSyncListener$9(Runnable runnable, Void v, FirebaseFirestoreException error) {
        Assert.hardAssert(error == null, "snapshots-in-sync listeners should never get errors.", new Object[0]);
        runnable.run();
    }

    static /* synthetic */ void lambda$addSnapshotsInSyncListener$10(AsyncEventListener asyncListener, FirestoreClient client) {
        asyncListener.mute();
        client.removeSnapshotsInSyncListener(asyncListener);
    }

    /* access modifiers changed from: package-private */
    public <T> T callClient(Function<FirestoreClient, T> call) {
        return this.clientProvider.call(call);
    }

    /* access modifiers changed from: package-private */
    public DatabaseId getDatabaseId() {
        return this.databaseId;
    }

    /* access modifiers changed from: package-private */
    public UserDataReader getUserDataReader() {
        return this.userDataReader;
    }

    /* access modifiers changed from: package-private */
    public void validateReference(DocumentReference docRef) {
        Preconditions.checkNotNull(docRef, "Provided DocumentReference must not be null.");
        if (docRef.getFirestore() != this) {
            throw new IllegalArgumentException("Provided document reference is from a different Cloud Firestore instance.");
        }
    }

    static void setClientLanguage(String languageToken) {
        FirestoreChannel.setClientLanguage(languageToken);
    }
}
