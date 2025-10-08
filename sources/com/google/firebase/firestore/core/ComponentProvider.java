package com.google.firebase.firestore.core;

import android.content.Context;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.auth.CredentialsProvider;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.local.IndexBackfiller;
import com.google.firebase.firestore.local.LocalStore;
import com.google.firebase.firestore.local.Persistence;
import com.google.firebase.firestore.local.Scheduler;
import com.google.firebase.firestore.remote.ConnectivityMonitor;
import com.google.firebase.firestore.remote.Datastore;
import com.google.firebase.firestore.remote.GrpcMetadataProvider;
import com.google.firebase.firestore.remote.RemoteComponenetProvider;
import com.google.firebase.firestore.remote.RemoteSerializer;
import com.google.firebase.firestore.remote.RemoteStore;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.AsyncQueue;

public abstract class ComponentProvider {
    private EventManager eventManager;
    private Scheduler garbageCollectionScheduler;
    private IndexBackfiller indexBackfiller;
    private LocalStore localStore;
    private Persistence persistence;
    private RemoteComponenetProvider remoteProvider = new RemoteComponenetProvider();
    private RemoteStore remoteStore;
    protected final FirebaseFirestoreSettings settings;
    private SyncEngine syncEngine;

    /* access modifiers changed from: protected */
    public abstract EventManager createEventManager(Configuration configuration);

    /* access modifiers changed from: protected */
    public abstract Scheduler createGarbageCollectionScheduler(Configuration configuration);

    /* access modifiers changed from: protected */
    public abstract IndexBackfiller createIndexBackfiller(Configuration configuration);

    /* access modifiers changed from: protected */
    public abstract LocalStore createLocalStore(Configuration configuration);

    /* access modifiers changed from: protected */
    public abstract Persistence createPersistence(Configuration configuration);

    /* access modifiers changed from: protected */
    public abstract RemoteStore createRemoteStore(Configuration configuration);

    /* access modifiers changed from: protected */
    public abstract SyncEngine createSyncEngine(Configuration configuration);

    public ComponentProvider(FirebaseFirestoreSettings settings2) {
        this.settings = settings2;
    }

    public static ComponentProvider defaultFactory(FirebaseFirestoreSettings settings2) {
        if (settings2.isPersistenceEnabled()) {
            return new SQLiteComponentProvider(settings2);
        }
        return new MemoryComponentProvider(settings2);
    }

    public static final class Configuration {
        public final CredentialsProvider<String> appCheckProvider;
        public final AsyncQueue asyncQueue;
        public final CredentialsProvider<User> authProvider;
        public final Context context;
        public final DatabaseInfo databaseInfo;
        public final User initialUser;
        public final int maxConcurrentLimboResolutions;
        public final GrpcMetadataProvider metadataProvider;

        public Configuration(Context context2, AsyncQueue asyncQueue2, DatabaseInfo databaseInfo2, User initialUser2, int maxConcurrentLimboResolutions2, CredentialsProvider<User> authProvider2, CredentialsProvider<String> appCheckProvider2, GrpcMetadataProvider metadataProvider2) {
            this.context = context2;
            this.asyncQueue = asyncQueue2;
            this.databaseInfo = databaseInfo2;
            this.initialUser = initialUser2;
            this.maxConcurrentLimboResolutions = maxConcurrentLimboResolutions2;
            this.authProvider = authProvider2;
            this.appCheckProvider = appCheckProvider2;
            this.metadataProvider = metadataProvider2;
        }
    }

    public void setRemoteProvider(RemoteComponenetProvider remoteProvider2) {
        Assert.hardAssert(this.remoteStore == null, "cannot set remoteProvider after initialize", new Object[0]);
        this.remoteProvider = remoteProvider2;
    }

    public RemoteSerializer getRemoteSerializer() {
        return this.remoteProvider.getRemoteSerializer();
    }

    public Datastore getDatastore() {
        return this.remoteProvider.getDatastore();
    }

    public Persistence getPersistence() {
        return (Persistence) Assert.hardAssertNonNull(this.persistence, "persistence not initialized yet", new Object[0]);
    }

    public Scheduler getGarbageCollectionScheduler() {
        return this.garbageCollectionScheduler;
    }

    public IndexBackfiller getIndexBackfiller() {
        return this.indexBackfiller;
    }

    public LocalStore getLocalStore() {
        return (LocalStore) Assert.hardAssertNonNull(this.localStore, "localStore not initialized yet", new Object[0]);
    }

    public SyncEngine getSyncEngine() {
        return (SyncEngine) Assert.hardAssertNonNull(this.syncEngine, "syncEngine not initialized yet", new Object[0]);
    }

    public RemoteStore getRemoteStore() {
        return (RemoteStore) Assert.hardAssertNonNull(this.remoteStore, "remoteStore not initialized yet", new Object[0]);
    }

    public EventManager getEventManager() {
        return (EventManager) Assert.hardAssertNonNull(this.eventManager, "eventManager not initialized yet", new Object[0]);
    }

    /* access modifiers changed from: protected */
    public ConnectivityMonitor getConnectivityMonitor() {
        return this.remoteProvider.getConnectivityMonitor();
    }

    public void initialize(Configuration configuration) {
        this.remoteProvider.initialize(configuration);
        this.persistence = createPersistence(configuration);
        this.persistence.start();
        this.localStore = createLocalStore(configuration);
        this.remoteStore = createRemoteStore(configuration);
        this.syncEngine = createSyncEngine(configuration);
        this.eventManager = createEventManager(configuration);
        this.localStore.start();
        this.remoteStore.start();
        this.garbageCollectionScheduler = createGarbageCollectionScheduler(configuration);
        this.indexBackfiller = createIndexBackfiller(configuration);
    }
}
